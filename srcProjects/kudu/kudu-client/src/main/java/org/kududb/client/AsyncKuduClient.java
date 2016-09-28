/*
 * Copyright (C) 2010-2012  The Async HBase Authors.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the StumbleUpon nor the names of its contributors
 *     may be used to endorse or promote products derived from this software
 *     without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.kududb.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.net.HostAndPort;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.protobuf.Message;
import com.stumbleupon.async.Callback;
import com.stumbleupon.async.Deferred;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.socket.nio.NioWorkerPool;
import org.kududb.Common;
import org.kududb.Schema;
import org.kududb.annotations.InterfaceAudience;
import org.kududb.annotations.InterfaceStability;
import org.kududb.consensus.Metadata;
import org.kududb.master.Master;
import org.kududb.master.Master.GetTableLocationsResponsePB;
import org.kududb.util.AsyncUtil;
import org.kududb.util.NetUtil;
import org.kududb.util.Pair;
import org.kududb.util.Slice;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.DefaultChannelPipeline;
import org.jboss.netty.channel.socket.ClientSocketChannelFactory;
import org.jboss.netty.channel.socket.SocketChannel;
import org.jboss.netty.channel.socket.SocketChannelConfig;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.timeout.ReadTimeoutHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timeout;
import org.jboss.netty.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.GuardedBy;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.kududb.client.ExternalConsistencyMode.CLIENT_PROPAGATED;

/**
 * A fully asynchronous and thread-safe client for Kudu.
 * <p>
 * This client should be
 * instantiated only once. You can use it with any number of tables at the
 * same time. The only case where you should have multiple instances is when
 * you want to use multiple different clusters at the same time.
 * <p>
 * If you play by the rules, this client is completely
 * thread-safe. Read the documentation carefully to know what the requirements
 * are for this guarantee to apply.
 * <p>
 * This client is fully non-blocking, any blocking operation will return a
 * {@link Deferred} instance to which you can attach a {@link Callback} chain
 * that will execute when the asynchronous operation completes.
 *
 * <h1>Note regarding {@code KuduRpc} instances passed to this class</h1>
 * Every {@link KuduRpc} passed to a method of this class should not be
 * changed or re-used until the {@code Deferred} returned by that method
 * calls you back.  <strong>Changing or re-using any {@link KuduRpc} for
 * an RPC in flight will lead to <em>unpredictable</em> results and voids
 * your warranty</strong>.
 *
 * <h1>{@code throws} clauses</h1>
 * None of the asynchronous methods in this API are expected to throw an
 * exception.  But the {@link Deferred} object they return to you can carry an
 * exception that you should handle (using "errbacks", see the javadoc of
 * {@link Deferred}).  In order to be able to do proper asynchronous error
 * handling, you need to know what types of exceptions you're expected to face
 * in your errbacks.  In order to document that, the methods of this API use
 * javadoc's {@code @throws} to spell out the exception types you should
 * handle in your errback.  Asynchronous exceptions will be indicated as such
 * in the javadoc with "(deferred)".
 */
@InterfaceAudience.Public
@InterfaceStability.Unstable
public class AsyncKuduClient implements AutoCloseable {

  public static final Logger LOG = LoggerFactory.getLogger(AsyncKuduClient.class);
  public static final int SLEEP_TIME = 500;
  public static final byte[] EMPTY_ARRAY = new byte[0];
  public static final long NO_TIMESTAMP = -1;
  public static final long DEFAULT_OPERATION_TIMEOUT_MS = 10000;
  public static final long DEFAULT_SOCKET_READ_TIMEOUT_MS = 5000;

  private final ClientSocketChannelFactory channelFactory;

  /**
   * This map and the next 2 maps contain the same data, but indexed
   * differently. There is no consistency guarantee across the maps.
   * They are not updated all at the same time atomically.  This map
   * is always the first to be updated, because that's the map from
   * which all the lookups are done in the fast-path of the requests
   * that need to locate a tablet. The second map to be updated is
   * tablet2client, because it comes second in the fast-path
   * of every requests that need to locate a tablet. The third map
   * is only used to handle TabletServer disconnections gracefully.
   *
   * This map is keyed by table ID.
   */
  private final ConcurrentHashMap<String, ConcurrentSkipListMap<byte[],
      RemoteTablet>> tabletsCache = new ConcurrentHashMap<>();

  /**
   * Maps a tablet ID to the RemoteTablet that knows where all the replicas are served.
   */
  private final ConcurrentHashMap<Slice, RemoteTablet> tablet2client = new ConcurrentHashMap<>();

  /**
   * Maps a client connected to a TabletServer to the list of tablets we know
   * it's serving so far.
   */
  private final ConcurrentHashMap<TabletClient, ArrayList<RemoteTablet>> client2tablets =
      new ConcurrentHashMap<>();

  /**
   * Cache that maps a TabletServer address ("ip:port") to the clients
   * connected to it.
   * <p>
   * Access to this map must be synchronized by locking its monitor.
   * Lock ordering: when locking both this map and a TabletClient, the
   * TabletClient must always be locked first to avoid deadlocks.  Logging
   * the contents of this map (or calling toString) requires copying it first.
   * <p>
   * This isn't a {@link ConcurrentHashMap} because we don't use it frequently
   * (just when connecting to / disconnecting from TabletClients) and when we
   * add something to it, we want to do an atomic get-and-put, but
   * {@code putIfAbsent} isn't a good fit for us since it requires to create
   * an object that may be "wasted" in case another thread wins the insertion
   * race, and we don't want to create unnecessary connections.
   * <p>
   * Upon disconnection, clients are automatically removed from this map.
   * We don't use a {@code ChannelGroup} because a {@code ChannelGroup} does
   * the clean-up on the {@code channelClosed} event, which is actually the
   * 3rd and last event to be fired when a channel gets disconnected.  The
   * first one to get fired is, {@code channelDisconnected}.  This matters to
   * us because we want to purge disconnected clients from the cache as
   * quickly as possible after the disconnection, to avoid handing out clients
   * that are going to cause unnecessary errors.
   * @see TabletClientPipeline#handleDisconnect
   */
  private final HashMap<String, TabletClient> ip2client =
      new HashMap<String, TabletClient>();

  @GuardedBy("sessions")
  private final Set<AsyncKuduSession> sessions = new HashSet<AsyncKuduSession>();

  // Since the masters also go through TabletClient, we need to treat them as if they were a normal
  // table. We'll use the following fake table name to identify places where we need special
  // handling.
  static final String MASTER_TABLE_NAME_PLACEHOLDER =  "Kudu Master";
  final KuduTable masterTable;
  private final List<HostAndPort> masterAddresses;

  private final HashedWheelTimer timer;

  /**
   * Timestamp required for HybridTime external consistency through timestamp
   * propagation.
   * @see src/kudu/common/common.proto
   */
  private long lastPropagatedTimestamp = NO_TIMESTAMP;

  // A table is considered not served when we get an empty list of locations but know
  // that a tablet exists. This is currently only used for new tables. The objects stored are
  // table IDs.
  private final Set<String> tablesNotServed = Collections.newSetFromMap(new
      ConcurrentHashMap<String, Boolean>());

  /**
   * Semaphore used to rate-limit master lookups
   * Once we have more than this number of concurrent master lookups, we'll
   * start to throttle ourselves slightly.
   * @see #acquireMasterLookupPermit
   */
  private final Semaphore masterLookups = new Semaphore(50);

  private final Random sleepRandomizer = new Random();

  private final long defaultOperationTimeoutMs;

  private final long defaultAdminOperationTimeoutMs;

  private final long defaultSocketReadTimeoutMs;

  private volatile boolean closed;

  private AsyncKuduClient(AsyncKuduClientBuilder b) {
    this.channelFactory = b.createChannelFactory();
    this.masterAddresses = b.masterAddresses;
    this.masterTable = new KuduTable(this, MASTER_TABLE_NAME_PLACEHOLDER,
        MASTER_TABLE_NAME_PLACEHOLDER, null, null);
    this.defaultOperationTimeoutMs = b.defaultOperationTimeoutMs;
    this.defaultAdminOperationTimeoutMs = b.defaultAdminOperationTimeoutMs;
    this.defaultSocketReadTimeoutMs = b.defaultSocketReadTimeoutMs;
    this.timer = b.timer;
  }

  /**
   * Updates the last timestamp received from a server. Used for CLIENT_PROPAGATED
   * external consistency. This is only publicly visible so that it can be set
   * on tests, users should generally disregard this method.
   *
   * @param lastPropagatedTimestamp the last timestamp received from a server
   */
  @VisibleForTesting
  public synchronized void updateLastPropagatedTimestamp(long lastPropagatedTimestamp) {
    if (this.lastPropagatedTimestamp == -1 ||
      this.lastPropagatedTimestamp < lastPropagatedTimestamp) {
      this.lastPropagatedTimestamp = lastPropagatedTimestamp;
    }
  }

  @VisibleForTesting
  public synchronized long getLastPropagatedTimestamp() {
    return lastPropagatedTimestamp;
  }

  /**
   * Create a table on the cluster with the specified name and schema. Default table
   * configurations are used, mainly the table will have one tablet.
   * @param name the table's name
   * @param schema the table's schema
   * @return a deferred object to track the progress of the createTable command that gives
   * an object to communicate with the created table
   */
  public Deferred<KuduTable> createTable(String name, Schema schema) {
    return this.createTable(name, schema, new CreateTableOptions());
  }

  /**
   * Create a table on the cluster with the specified name, schema, and table configurations.
   * @param name the table's name
   * @param schema the table's schema
   * @param builder a builder containing the table's configurations
   * @return a deferred object to track the progress of the createTable command that gives
   * an object to communicate with the created table
   */
  public Deferred<KuduTable> createTable(final String name, Schema schema,
                                         CreateTableOptions builder) {
    checkIsClosed();
    if (builder == null) {
      builder = new CreateTableOptions();
    }
    CreateTableRequest create = new CreateTableRequest(this.masterTable, name, schema,
        builder);
    create.setTimeoutMillis(defaultAdminOperationTimeoutMs);
    return sendRpcToTablet(create).addCallbackDeferring(
        new Callback<Deferred<KuduTable>, CreateTableResponse>() {
      @Override
      public Deferred<KuduTable> call(CreateTableResponse createTableResponse) throws Exception {
        return openTable(name);
      }
    });
  }

  /**
   * Delete a table on the cluster with the specified name.
   * @param name the table's name
   * @return a deferred object to track the progress of the deleteTable command
   */
  public Deferred<DeleteTableResponse> deleteTable(String name) {
    checkIsClosed();
    DeleteTableRequest delete = new DeleteTableRequest(this.masterTable, name);
    delete.setTimeoutMillis(defaultAdminOperationTimeoutMs);
    return sendRpcToTablet(delete);
  }

  /**
   * Alter a table on the cluster as specified by the builder.
   *
   * When the returned deferred completes it only indicates that the master accepted the alter
   * command, use {@link AsyncKuduClient#isAlterTableDone(String)} to know when the alter finishes.
   * @param name the table's name, if this is a table rename then the old table name must be passed
   * @param ato the alter table builder
   * @return a deferred object to track the progress of the alter command
   */
  public Deferred<AlterTableResponse> alterTable(String name, AlterTableOptions ato) {
    checkIsClosed();
    AlterTableRequest alter = new AlterTableRequest(this.masterTable, name, ato);
    alter.setTimeoutMillis(defaultAdminOperationTimeoutMs);
    return sendRpcToTablet(alter);
  }

  /**
   * Helper method that checks and waits until the completion of an alter command.
   * It will block until the alter command is done or the deadline is reached.
   * @param name the table's name, if the table was renamed then that name must be checked against
   * @return a deferred object to track the progress of the isAlterTableDone command
   */
  public Deferred<IsAlterTableDoneResponse> isAlterTableDone(String name) throws Exception {
    checkIsClosed();
    IsAlterTableDoneRequest request = new IsAlterTableDoneRequest(this.masterTable, name);
    request.setTimeoutMillis(defaultAdminOperationTimeoutMs);
    return sendRpcToTablet(request);
  }

  /**
   * Get the list of running tablet servers.
   * @return a deferred object that yields a list of tablet servers
   */
  public Deferred<ListTabletServersResponse> listTabletServers() {
    checkIsClosed();
    ListTabletServersRequest rpc = new ListTabletServersRequest(this.masterTable);
    rpc.setTimeoutMillis(defaultAdminOperationTimeoutMs);
    return sendRpcToTablet(rpc);
  }

  Deferred<GetTableSchemaResponse> getTableSchema(String name) {
    GetTableSchemaRequest rpc = new GetTableSchemaRequest(this.masterTable, name);
    rpc.setTimeoutMillis(defaultAdminOperationTimeoutMs);
    return sendRpcToTablet(rpc);
  }

  /**
   * Get the list of all the tables.
   * @return a deferred object that yields a list of all the tables
   */
  public Deferred<ListTablesResponse> getTablesList() {
    return getTablesList(null);
  }

  /**
   * Get a list of table names. Passing a null filter returns all the tables. When a filter is
   * specified, it only returns tables that satisfy a substring match.
   * @param nameFilter an optional table name filter
   * @return a deferred that yields the list of table names
   */
  public Deferred<ListTablesResponse> getTablesList(String nameFilter) {
    ListTablesRequest rpc = new ListTablesRequest(this.masterTable, nameFilter);
    rpc.setTimeoutMillis(defaultAdminOperationTimeoutMs);
    return sendRpcToTablet(rpc);
  }

  /**
   * Test if a table exists.
   * @param name a non-null table name
   * @return true if the table exists, else false
   */
  public Deferred<Boolean> tableExists(final String name) {
    if (name == null) {
      throw new IllegalArgumentException("The table name cannot be null");
    }
    return getTablesList().addCallbackDeferring(new Callback<Deferred<Boolean>,
        ListTablesResponse>() {
      @Override
      public Deferred<Boolean> call(ListTablesResponse listTablesResponse) throws Exception {
        for (String tableName : listTablesResponse.getTablesList()) {
          if (name.equals(tableName)) {
            return Deferred.fromResult(true);
          }
        }
        return Deferred.fromResult(false);
      }
    });
  }

  /**
   * Open the table with the given name. If the table was just created, the Deferred will only get
   * called back when all the tablets have been successfully created.
   * @param name table to open
   * @return a KuduTable if the table exists, else a MasterErrorException
   */
  public Deferred<KuduTable> openTable(final String name) {
    checkIsClosed();

    // We create an RPC that we're never going to send, and will instead use it to keep track of
    // timeouts and use its Deferred.
    final KuduRpc<KuduTable> fakeRpc = new KuduRpc<KuduTable>(null) {
      @Override
      ChannelBuffer serialize(Message header) { return null; }

      @Override
      String serviceName() { return null; }

      @Override
      String method() {
        return "IsCreateTableDone";
      }

      @Override
      Pair<KuduTable, Object> deserialize(CallResponse callResponse, String tsUUID)
          throws Exception { return null; }
    };
    fakeRpc.setTimeoutMillis(defaultAdminOperationTimeoutMs);

    return getTableSchema(name).addCallbackDeferring(new Callback<Deferred<KuduTable>,
        GetTableSchemaResponse>() {
      @Override
      public Deferred<KuduTable> call(GetTableSchemaResponse response) throws Exception {
        KuduTable table = new KuduTable(AsyncKuduClient.this,
            name,
            response.getTableId(),
            response.getSchema(),
            response.getPartitionSchema());
        // We grab the Deferred first because calling callback on the RPC will reset it and we'd
        // return a different, non-triggered Deferred.
        Deferred<KuduTable> d = fakeRpc.getDeferred();
        if (response.isCreateTableDone()) {
          LOG.debug("Opened table {}", name);
          fakeRpc.callback(table);
        } else {
          LOG.debug("Delaying opening table {}, its tablets aren't fully created", name);
          fakeRpc.attempt++;
          delayedIsCreateTableDone(
              table,
              fakeRpc,
              getOpenTableCB(fakeRpc, table),
              getDelayedIsCreateTableDoneErrback(fakeRpc));
        }
        return d;
      }
    });
  }

  /**
   * This callback will be repeatadly used when opening a table until it is done being created.
   */
  Callback<Deferred<KuduTable>, Master.IsCreateTableDoneResponsePB> getOpenTableCB(
      final KuduRpc<KuduTable> rpc, final KuduTable table) {
    return new Callback<Deferred<KuduTable>, Master.IsCreateTableDoneResponsePB>() {
      @Override
      public Deferred<KuduTable> call(
          Master.IsCreateTableDoneResponsePB isCreateTableDoneResponsePB) throws Exception {
        String tableName = table.getName();
        Deferred<KuduTable> d = rpc.getDeferred();
        if (isCreateTableDoneResponsePB.getDone()) {
          LOG.debug("Table {}'s tablets are now created", tableName);
          rpc.callback(table);
        } else {
          rpc.attempt++;
          LOG.debug("Table {}'s tablets are still not created, further delaying opening it",
              tableName);

          delayedIsCreateTableDone(
              table,
              rpc,
              getOpenTableCB(rpc, table),
              getDelayedIsCreateTableDoneErrback(rpc));
        }
        return d;
      }
    };
  }

  /**
   * Get the timeout used for operations on sessions and scanners.
   * @return a timeout in milliseconds
   */
  public long getDefaultOperationTimeoutMs() {
    return defaultOperationTimeoutMs;
  }

  /**
   * Get the timeout used for admin operations.
   * @return a timeout in milliseconds
   */
  public long getDefaultAdminOperationTimeoutMs() {
    return defaultAdminOperationTimeoutMs;
  }

  /**
   * Get the timeout used when waiting to read data from a socket. Will be triggered when nothing
   * has been read on a socket connected to a tablet server for {@code timeout} milliseconds.
   * @return a timeout in milliseconds
   */
  public long getDefaultSocketReadTimeoutMs() {
    return defaultSocketReadTimeoutMs;
  }

  /**
   * Creates a new {@link AsyncKuduScanner.AsyncKuduScannerBuilder} for a particular table.
   * @param table the name of the table you intend to scan.
   * The string is assumed to use the platform's default charset.
   * @return a new scanner builder for this table
   */
  public AsyncKuduScanner.AsyncKuduScannerBuilder newScannerBuilder(KuduTable table) {
    checkIsClosed();
    return new AsyncKuduScanner.AsyncKuduScannerBuilder(this, table);
  }

  /**
   * Package-private access point for {@link AsyncKuduScanner}s to open themselves.
   * @param scanner The scanner to open.
   * @return A deferred {@link AsyncKuduScanner.Response}
   */
  Deferred<AsyncKuduScanner.Response> openScanner(final AsyncKuduScanner scanner) {
    return sendRpcToTablet(scanner.getOpenRequest()).addErrback(
        new Callback<Exception, Exception>() {
          public Exception call(final Exception e) {
            String message = "Cannot openScanner because: ";
            LOG.warn(message, e);
            // Don't let the scanner think it's opened on this tablet.
            scanner.invalidate();
            return e;  // Let the error propagate.
          }
          public String toString() {
            return "openScanner errback";
          }
        });
  }

  /**
   * Create a new session for interacting with the cluster.
   * User is responsible for destroying the session object.
   * This is a fully local operation (no RPCs or blocking).
   * @return a new AsyncKuduSession
   */
  public AsyncKuduSession newSession() {
    checkIsClosed();
    AsyncKuduSession session = new AsyncKuduSession(this);
    synchronized (sessions) {
      sessions.add(session);
    }
    return session;
  }

  /**
   * This method is for KuduSessions so that they can remove themselves as part of closing down.
   * @param session Session to remove
   */
  void removeSession(AsyncKuduSession session) {
    synchronized (sessions) {
      boolean removed = sessions.remove(session);
      assert removed == true;
    }
  }

  /**
   * Package-private access point for {@link AsyncKuduScanner}s to scan more rows.
   * @param scanner The scanner to use.
   * @return A deferred row.
   */
  Deferred<AsyncKuduScanner.Response> scanNextRows(final AsyncKuduScanner scanner) {
    final RemoteTablet tablet = scanner.currentTablet();
    final TabletClient client = clientFor(tablet);
    final KuduRpc<AsyncKuduScanner.Response> next_request = scanner.getNextRowsRequest();
    final Deferred<AsyncKuduScanner.Response> d = next_request.getDeferred();
    if (client == null) {
      // Oops, we no longer know anything about this client or tabletSlice.  Our
      // cache was probably invalidated while the client was scanning.  This
      // means that we lost the connection to that TabletServer, so we have to
      // try to re-connect and check if the scanner is still good.
      return sendRpcToTablet(next_request);
    }
    next_request.attempt++;
    client.sendRpc(next_request);
    return d;
  }

  /**
   * Package-private access point for {@link AsyncKuduScanner}s to close themselves.
   * @param scanner The scanner to close.
   * @return A deferred object that indicates the completion of the request.
   * The {@link AsyncKuduScanner.Response} can contain rows that were left to scan.
   */
  Deferred<AsyncKuduScanner.Response> closeScanner(final AsyncKuduScanner scanner) {
    final RemoteTablet tablet = scanner.currentTablet();
    // Getting a null tablet here without being in a closed state means we were in between tablets.
    if (tablet == null) {
      return Deferred.fromResult(null);
    }

    final TabletClient client = clientFor(tablet);
    if (client == null) {
      // Oops, we no longer know anything about this client or tabletSlice.  Our
      // cache was probably invalidated while the client was scanning.  So
      // we can't close this scanner properly.
      LOG.warn("Cannot close " + scanner + " properly, no connection open for "
          + (tablet == null ? null : tablet));
      return Deferred.fromResult(null);
    }
    final KuduRpc<AsyncKuduScanner.Response>  close_request = scanner.getCloseRequest();
    final Deferred<AsyncKuduScanner.Response> d = close_request.getDeferred();
    close_request.attempt++;
    client.sendRpc(close_request);
    return d;
  }

  <R> Deferred<R> sendRpcToTablet(final KuduRpc<R> request) {
    if (cannotRetryRequest(request)) {
      return tooManyAttemptsOrTimeout(request, null);
    }
    request.attempt++;
    final String tableId = request.getTable().getTableId();
    byte[] partitionKey = null;
    if (request instanceof KuduRpc.HasKey) {
       partitionKey = ((KuduRpc.HasKey)request).partitionKey();
    }
    final RemoteTablet tablet = getTablet(tableId, partitionKey);

    // Set the propagated timestamp so that the next time we send a message to
    // the server the message includes the last propagated timestamp.
    long lastPropagatedTs = getLastPropagatedTimestamp();
    if (request.getExternalConsistencyMode() == CLIENT_PROPAGATED &&
      lastPropagatedTs != NO_TIMESTAMP) {
      request.setPropagatedTimestamp(lastPropagatedTs);
    }

    if (tablet != null) {
      TabletClient tabletClient = clientFor(tablet);
      if (tabletClient != null) {
        request.setTablet(tablet);
        final Deferred<R> d = request.getDeferred();
        tabletClient.sendRpc(request);
        return d;
      }
    }

    // Right after creating a table a request will fall into locateTablet since we don't know yet
    // if the table is ready or not. If discoverTablets() didn't get any tablets back,
    // then on retry we'll fall into the following block. It will sleep, then call the master to
    // see if the table was created. We'll spin like this until the table is created and then
    // we'll try to locate the tablet again.
    if (tablesNotServed.contains(tableId)) {
      return delayedIsCreateTableDone(request.getTable(), request,
          new RetryRpcCB<R, Master.IsCreateTableDoneResponsePB>(request),
          getDelayedIsCreateTableDoneErrback(request));
    }
    Callback<Deferred<R>, Master.GetTableLocationsResponsePB> cb = new RetryRpcCB<>(request);
    Callback<Deferred<R>, Exception> eb = new RetryRpcErrback<>(request);
    Deferred<Master.GetTableLocationsResponsePB> returnedD =
        locateTablet(request.getTable(), partitionKey);
    return AsyncUtil.addCallbacksDeferring(returnedD, cb, eb);
  }

  /**
   * Callback used to retry a RPC after another query finished, like looking up where that RPC
   * should go.
   * <p>
   * Use {@code AsyncUtil.addCallbacksDeferring} to add this as the callback and
   * {@link AsyncKuduClient.RetryRpcErrback} as the "errback" to the {@code Deferred}
   * returned by {@link #locateTablet(String, byte[])}.
   * @param <R> RPC's return type.
   * @param <D> Previous query's return type, which we don't use, but need to specify in order to
   *           tie it all together.
   */
  final class RetryRpcCB<R, D> implements Callback<Deferred<R>, D> {
    private final KuduRpc<R> request;
    RetryRpcCB(KuduRpc<R> request) {
      this.request = request;
    }
    public Deferred<R> call(final D arg) {
      return sendRpcToTablet(request);  // Retry the RPC.
    }
    public String toString() {
      return "retry RPC";
    }
  }

  /**
   * "Errback" used to delayed-retry a RPC if it fails due to no leader master being found.
   * Other exceptions are passed through to be handled by the caller.
   * <p>
   * Use {@code AsyncUtil.addCallbacksDeferring} to add this as the "errback" and
   * {@link RetryRpcCB} as the callback to the {@code Deferred} returned by
   * {@link #locateTablet(String, byte[])}.
   * @see #delayedSendRpcToTablet(KuduRpc, KuduException)
   * @param <R> The type of the original RPC.
   */
  final class RetryRpcErrback<R> implements Callback<Deferred<R>, Exception> {
    private final KuduRpc<R> request;

    public RetryRpcErrback(KuduRpc<R> request) {
      this.request = request;
    }

    @Override
    public Deferred<R> call(Exception arg) {
      if (arg instanceof NoLeaderMasterFoundException) {
        // If we could not find the leader master, try looking up the leader master
        // again.
        Deferred<R> d = request.getDeferred();
        // TODO: Handle the situation when multiple in-flight RPCs are queued waiting
        // for the leader master to be determine (either after a failure or at initialization
        // time). This could re-use some of the existing piping in place for non-master tablets.
        delayedSendRpcToTablet(request, (NoLeaderMasterFoundException) arg);
        return d;
      }
      // Pass all other exceptions through.
      return Deferred.fromError(arg);
    }

    @Override
    public String toString() {
      return "retry RPC after error";
    }
  }

  /**
   * This errback ensures that if the delayed call to IsCreateTableDone throws an Exception that
   * it will be propagated back to the user.
   * @param request Request to errback if there's a problem with the delayed call.
   * @param <R> Request's return type.
   * @return An errback.
   */
  <R> Callback<Exception, Exception> getDelayedIsCreateTableDoneErrback(final KuduRpc<R> request) {
    return new Callback<Exception, Exception>() {
      @Override
      public Exception call(Exception e) throws Exception {
        // TODO maybe we can retry it?
        request.errback(e);
        return e;
      }
    };
  }

  /**
   * This method will call IsCreateTableDone on the master after sleeping for
   * getSleepTimeForRpc() based on the provided KuduRpc's number of attempts. Once this is done,
   * the provided callback will be called.
   * @param table the table to lookup
   * @param rpc the original KuduRpc that needs to access the table
   * @param retryCB the callback to call on completion
   * @param errback the errback to call if something goes wrong when calling IsCreateTableDone
   * @return Deferred used to track the provided KuduRpc
   */
  <R> Deferred<R> delayedIsCreateTableDone(final KuduTable table, final KuduRpc<R> rpc,
                                           final Callback<Deferred<R>,
                                               Master.IsCreateTableDoneResponsePB> retryCB,
                                           final Callback<Exception, Exception> errback) {

    final class RetryTimer implements TimerTask {
      public void run(final Timeout timeout) {
        String tableId = table.getTableId();
        final boolean has_permit = acquireMasterLookupPermit();
        if (!has_permit) {
          // If we failed to acquire a permit, it's worth checking if someone
          // looked up the tablet we're interested in.  Every once in a while
          // this will save us a Master lookup.
          if (!tablesNotServed.contains(tableId)) {
            try {
              retryCB.call(null);
              return;
            } catch (Exception e) {
              // we're calling RetryRpcCB which doesn't throw exceptions, ignore
            }
          }
        }
        IsCreateTableDoneRequest rpc = new IsCreateTableDoneRequest(masterTable, tableId);
        rpc.setTimeoutMillis(defaultAdminOperationTimeoutMs);
        final Deferred<Master.IsCreateTableDoneResponsePB> d =
            sendRpcToTablet(rpc).addCallback(new IsCreateTableDoneCB(tableId));
        if (has_permit) {
          // The errback is needed here to release the lookup permit
          d.addCallbacks(new ReleaseMasterLookupPermit<Master.IsCreateTableDoneResponsePB>(),
              new ReleaseMasterLookupPermit<Exception>());
        }
        d.addCallbacks(retryCB, errback);
      }
    }
    long sleepTime = getSleepTimeForRpc(rpc);
    if (rpc.deadlineTracker.wouldSleepingTimeout(sleepTime)) {
      return tooManyAttemptsOrTimeout(rpc, null);
    }

    newTimeout(new RetryTimer(), sleepTime);
    return rpc.getDeferred();
  }

  private final class ReleaseMasterLookupPermit<T> implements Callback<T, T> {
    public T call(final T arg) {
      releaseMasterLookupPermit();
      return arg;
    }
    public String toString() {
      return "release master lookup permit";
    }
  }

  /** Callback executed when IsCreateTableDone completes.  */
  private final class IsCreateTableDoneCB implements Callback<Master.IsCreateTableDoneResponsePB,
      Master.IsCreateTableDoneResponsePB> {
    final String tableName;
    IsCreateTableDoneCB(String tableName) {
      this.tableName = tableName;
    }
    public Master.IsCreateTableDoneResponsePB call(final Master.IsCreateTableDoneResponsePB response) {
      if (response.getDone()) {
        LOG.debug("Table {} was created", tableName);
        tablesNotServed.remove(tableName);
      } else {
        LOG.debug("Table {} is still being created", tableName);
      }
      return response;
    }
    public String toString() {
      return "ask the master if " + tableName + " was created";
    }
  }

  boolean isTableNotServed(String tableId) {
    return tablesNotServed.contains(tableId);
  }


  long getSleepTimeForRpc(KuduRpc<?> rpc) {
    byte attemptCount = rpc.attempt;
    assert (attemptCount > 0);
    if (attemptCount == 0) {
      LOG.warn("Possible bug: attempting to retry an RPC with no attempts. RPC: " + rpc,
          new Exception("Exception created to collect stack trace"));
      attemptCount = 1;
    }
    // TODO backoffs? Sleep in increments of 500 ms, plus some random time up to 50
    long sleepTime = (attemptCount * SLEEP_TIME) + sleepRandomizer.nextInt(50);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Going to sleep for " + sleepTime + " at retry " + rpc.attempt);
    }
    return sleepTime;
  }

  /**
   * Modifying the list returned by this method won't change how AsyncKuduClient behaves,
   * but calling certain methods on the returned TabletClients can. For example,
   * it's possible to forcefully shutdown a connection to a tablet server by calling {@link
   * TabletClient#shutdown()}.
   * @return Copy of the current TabletClients list
   */
  @VisibleForTesting
  List<TabletClient> getTableClients() {
    synchronized (ip2client) {
      return new ArrayList<TabletClient>(ip2client.values());
    }
  }

  /**
   * This method first clears tabletsCache and then tablet2client without any regards for
   * calls to {@link #discoverTablets}. Call only when AsyncKuduClient is in a steady state.
   * @param tableId table for which we remove all the RemoteTablet entries
   */
  @VisibleForTesting
  void emptyTabletsCacheForTable(String tableId) {
    tabletsCache.remove(tableId);
    Set<Map.Entry<Slice, RemoteTablet>> tablets = tablet2client.entrySet();
    for (Map.Entry<Slice, RemoteTablet> entry : tablets) {
      if (entry.getValue().getTableId().equals(tableId)) {
        tablets.remove(entry);
      }
    }
  }

  TabletClient clientFor(RemoteTablet tablet) {
    if (tablet == null) {
      return null;
    }

    synchronized (tablet.tabletServers) {
      if (tablet.tabletServers.isEmpty()) {
        return null;
      }
      if (tablet.leaderIndex == RemoteTablet.NO_LEADER_INDEX) {
        // TODO we don't know where the leader is, either because one wasn't provided or because
        // we couldn't resolve its IP. We'll just send the client back so it retries and probably
        // dies after too many attempts.
        return null;
      } else {
        // TODO we currently always hit the leader, we probably don't need to except for writes
        // and some reads.
        return tablet.tabletServers.get(tablet.leaderIndex);
      }
    }
  }

  /**
   * Checks whether or not an RPC can be retried once more.
   * @param rpc The RPC we're going to attempt to execute.
   * @return {@code true} if this RPC already had too many attempts,
   * {@code false} otherwise (in which case it's OK to retry once more).
   * @throws NonRecoverableException if the request has had too many attempts
   * already.
   */
  static boolean cannotRetryRequest(final KuduRpc<?> rpc) {
    return rpc.deadlineTracker.timedOut() || rpc.attempt > 100;  // TODO Don't hardcode.
  }

  /**
   * Returns a {@link Deferred} containing an exception when an RPC couldn't
   * succeed after too many attempts or if it already timed out.
   * @param request The RPC that was retried too many times or timed out.
   * @param cause What was cause of the last failed attempt, if known.
   * You can pass {@code null} if the cause is unknown.
   */
  static <R> Deferred<R> tooManyAttemptsOrTimeout(final KuduRpc<R> request,
                                                  final KuduException cause) {
    String message;
    if (request.deadlineTracker.timedOut()) {
      message = "Time out: ";
    } else {
      message = "Too many attempts: ";
    }
    final Exception e = new NonRecoverableException(message + request, cause);
    request.errback(e);
    return Deferred.fromError(e);
  }

  /**
   * Sends a getTableLocations RPC to the master to find the table's tablets.
   * @param table table to lookup
   * @param partitionKey can be null, if not we'll find the exact tablet that contains it
   * @return Deferred to track the progress
   */
  Deferred<Master.GetTableLocationsResponsePB> locateTablet(KuduTable table, byte[] partitionKey) {
    final boolean has_permit = acquireMasterLookupPermit();
    String tableId = table.getTableId();
    if (!has_permit) {
      // If we failed to acquire a permit, it's worth checking if someone
      // looked up the tablet we're interested in.  Every once in a while
      // this will save us a Master lookup.
      RemoteTablet tablet = getTablet(tableId, partitionKey);
      if (tablet != null && clientFor(tablet) != null) {
        return Deferred.fromResult(null);  // Looks like no lookup needed.
      }
    }
    GetTableLocationsRequest rpc =
        new GetTableLocationsRequest(masterTable, partitionKey, partitionKey, tableId);
    rpc.setTimeoutMillis(defaultAdminOperationTimeoutMs);
    final Deferred<Master.GetTableLocationsResponsePB> d;

    // If we know this is going to the master, check the master consensus configuration (as specified by
    // 'masterAddresses' field) to determine and cache the current leader.
    if (isMasterTable(tableId)) {
      d = getMasterTableLocationsPB();
    } else {
      d = sendRpcToTablet(rpc);
    }
    d.addCallback(new MasterLookupCB(table));
    if (has_permit) {
      d.addBoth(new ReleaseMasterLookupPermit<Master.GetTableLocationsResponsePB>());
    }
    return d;
  }

  /**
   * Update the master config: send RPCs to all config members, use the returned data to
   * fill a {@link Master.GetTabletLocationsResponsePB} object.
   * @return An initialized Deferred object to hold the response.
   */
  Deferred<Master.GetTableLocationsResponsePB> getMasterTableLocationsPB() {
    final Deferred<Master.GetTableLocationsResponsePB> responseD =
        new Deferred<Master.GetTableLocationsResponsePB>();
    final GetMasterRegistrationReceived received =
        new GetMasterRegistrationReceived(masterAddresses, responseD);
    for (HostAndPort hostAndPort : masterAddresses) {
      Deferred<GetMasterRegistrationResponse> d;
      // Note: we need to create a client for that host first, as there's a
      // chicken and egg problem: since there is no source of truth beyond
      // the master, the only way to get information about a master host is
      // by making an RPC to that host.
      TabletClient clientForHostAndPort = newMasterClient(hostAndPort);
      if (clientForHostAndPort == null) {
        String message = "Couldn't resolve this master's address " + hostAndPort.toString();
        LOG.warn(message);
        d = Deferred.fromError(new NonRecoverableException(message));
      } else {
        d = getMasterRegistration(clientForHostAndPort);
      }
      d.addCallbacks(received.callbackForNode(hostAndPort), received.errbackForNode(hostAndPort));
    }
    return responseD;
  }


  /**
   * Get all or some tablets for a given table. This may query the master multiple times if there
   * are a lot of tablets.
   * This method blocks until it gets all the tablets.
   * @param tableId the table to locate tablets from
   * @param startPartitionKey where to start in the table, pass null to start at the beginning
   * @param endPartitionKey where to stop in the table, pass null to get all the tablets until the
   *                        end of the table
   * @param deadline deadline in milliseconds for this method to finish
   * @return a list of the tablets in the table, which can be queried for metadata about
   *         each tablet
   * @throws Exception MasterErrorException if the table doesn't exist
   */
  List<LocatedTablet> syncLocateTable(String tableId,
                                      byte[] startPartitionKey,
                                      byte[] endPartitionKey,
                                      long deadline) throws Exception {
    return locateTable(tableId, startPartitionKey, endPartitionKey, deadline).join();
  }

  private Deferred<List<LocatedTablet>> loopLocateTable(final String tableId,
      final byte[] startPartitionKey, final byte[] endPartitionKey, final List<LocatedTablet> ret,
      final DeadlineTracker deadlineTracker) {
    if (deadlineTracker.timedOut()) {
      return Deferred.fromError(new NonRecoverableException(
          "Took too long getting the list of tablets, " + deadlineTracker));
    }
    GetTableLocationsRequest rpc = new GetTableLocationsRequest(masterTable, startPartitionKey,
        endPartitionKey, tableId);
    rpc.setTimeoutMillis(defaultAdminOperationTimeoutMs);
    final Deferred<Master.GetTableLocationsResponsePB> d = sendRpcToTablet(rpc);
    return d.addCallbackDeferring(
        new Callback<Deferred<List<LocatedTablet>>, Master.GetTableLocationsResponsePB>() {
          @Override
          public Deferred<List<LocatedTablet>> call(GetTableLocationsResponsePB response) {
            // Table doesn't exist or is being created.
            if (response.getTabletLocationsCount() == 0) {
              Deferred.fromResult(ret);
            }
            byte[] lastEndPartition = startPartitionKey;
            for (Master.TabletLocationsPB tabletPb : response.getTabletLocationsList()) {
              LocatedTablet locs = new LocatedTablet(tabletPb);
              ret.add(locs);
              Partition partition = locs.getPartition();
              if (lastEndPartition != null && !partition.isEndPartition()
                  && Bytes.memcmp(partition.getPartitionKeyEnd(), lastEndPartition) < 0) {
                return Deferred.fromError(new IllegalStateException(
                    "Server returned tablets out of order: " + "end partition key '"
                        + Bytes.pretty(partition.getPartitionKeyEnd()) + "' followed "
                        + "end partition key '" + Bytes.pretty(lastEndPartition) + "'"));
              }
              lastEndPartition = partition.getPartitionKeyEnd();
            }
            // If true, we're done, else we have to go back to the master with the last end key
            if (lastEndPartition.length == 0
                || (endPartitionKey != null && Bytes.memcmp(lastEndPartition, endPartitionKey) > 0)) {
              return Deferred.fromResult(ret);
            } else {
              return loopLocateTable(tableId, lastEndPartition, endPartitionKey, ret,
                  deadlineTracker);
            }
          }
        });
  }

  /**
   * Get all or some tablets for a given table. This may query the master multiple times if there
   * are a lot of tablets.
   * @param tableId the table to locate tablets from
   * @param startPartitionKey where to start in the table, pass null to start at the beginning
   * @param endPartitionKey where to stop in the table, pass null to get all the tablets until the
   *                        end of the table
   * @param deadline max time spent in milliseconds for the deferred result of this method to
   *         get called back, if deadline is reached, the deferred result will get erred back
   * @return a deferred object that yields a list of the tablets in the table, which can be queried
   *         for metadata about each tablet
   * @throws Exception MasterErrorException if the table doesn't exist
   */
  Deferred<List<LocatedTablet>> locateTable(final String tableId,
      final byte[] startPartitionKey, final byte[] endPartitionKey, long deadline) {
    final List<LocatedTablet> ret = Lists.newArrayList();
    final DeadlineTracker deadlineTracker = new DeadlineTracker();
    deadlineTracker.setDeadline(deadline);
    return loopLocateTable(tableId, startPartitionKey, endPartitionKey, ret, deadlineTracker);
  }

  /**
   * We're handling a tablet server that's telling us it doesn't have the tablet we're asking for.
   * We're in the context of decode() meaning we need to either callback or retry later.
   */
  <R> void handleTabletNotFound(final KuduRpc<R> rpc, KuduException ex, TabletClient server) {
    invalidateTabletCache(rpc.getTablet(), server);
    handleRetryableError(rpc, ex);
  }

  /**
   * A tablet server is letting us know that it isn't the specified tablet's leader in response
   * a RPC, so we need to demote it and retry.
   */
  <R> void handleNotLeader(final KuduRpc<R> rpc, KuduException ex, TabletClient server) {
    rpc.getTablet().demoteLeader(server);
    handleRetryableError(rpc, ex);
  }

  <R> void handleRetryableError(final KuduRpc<R> rpc, KuduException ex) {
    // TODO we don't always need to sleep, maybe another replica can serve this RPC.
    delayedSendRpcToTablet(rpc, ex);
  }

  private <R> void delayedSendRpcToTablet(final KuduRpc<R> rpc, KuduException ex) {
    // Here we simply retry the RPC later. We might be doing this along with a lot of other RPCs
    // in parallel. Asynchbase does some hacking with a "probe" RPC while putting the other ones
    // on hold but we won't be doing this for the moment. Regions in HBase can move a lot,
    // we're not expecting this in Kudu.
    final class RetryTimer implements TimerTask {
      public void run(final Timeout timeout) {
        sendRpcToTablet(rpc);
      }
    }
    long sleepTime = getSleepTimeForRpc(rpc);
    if (cannotRetryRequest(rpc) || rpc.deadlineTracker.wouldSleepingTimeout(sleepTime)) {
      tooManyAttemptsOrTimeout(rpc, ex);
      // Don't let it retry.
      return;
    }
    newTimeout(new RetryTimer(), sleepTime);
  }

  /**
   * Remove the tablet server from the RemoteTablet's locations. Right now nothing is removing
   * the tablet itself from the caches.
   */
  private void invalidateTabletCache(RemoteTablet tablet, TabletClient server) {
    LOG.info("Removing server " + server.getUuid() + " from this tablet's cache " +
        tablet.getTabletIdAsString());
    tablet.removeTabletServer(server);
  }

  /** Callback executed when a master lookup completes.  */
  private final class MasterLookupCB implements Callback<Object,
      Master.GetTableLocationsResponsePB> {
    final KuduTable table;
    MasterLookupCB(KuduTable table) {
      this.table = table;
    }
    public Object call(final Master.GetTableLocationsResponsePB arg) {
      try {
        discoverTablets(table, arg);
      } catch (NonRecoverableException e) {
        // Returning the exception means we early out and errback to the user.
        return e;
      }
      return null;
    }
    public String toString() {
      return "get tablet locations from the master for table " + table.getName();
    }
  };

  boolean acquireMasterLookupPermit() {
    try {
      // With such a low timeout, the JVM may chose to spin-wait instead of
      // de-scheduling the thread (and causing context switches and whatnot).
      return masterLookups.tryAcquire(5, MILLISECONDS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();  // Make this someone else's problem.
      return false;
    }
  }

  /**
   * Releases a master lookup permit that was acquired.
   * @see #acquireMasterLookupPermit
   */
  void releaseMasterLookupPermit() {
    masterLookups.release();
  }

  @VisibleForTesting
  void discoverTablets(KuduTable table, Master.GetTableLocationsResponsePB response)
      throws NonRecoverableException {
    String tableId = table.getTableId();
    String tableName = table.getName();
    if (response.getTabletLocationsCount() == 0) {
      // Keep a note that the table exists but it's not served yet, we'll retry.
      if (LOG.isDebugEnabled()) {
        LOG.debug("Table {} has not been created yet", tableName);
      }
      tablesNotServed.add(tableId);
      return;
    }
    // Doing a get first instead of putIfAbsent to avoid creating unnecessary CSLMs because in
    // the most common case the table should already be present
    ConcurrentSkipListMap<byte[], RemoteTablet> tablets = tabletsCache.get(tableId);
    if (tablets == null) {
      tablets = new ConcurrentSkipListMap<>(Bytes.MEMCMP);
      ConcurrentSkipListMap<byte[], RemoteTablet> oldTablets = tabletsCache.putIfAbsent
          (tableId, tablets);
      if (oldTablets != null) {
        tablets = oldTablets;
      }
    }

    for (Master.TabletLocationsPB tabletPb : response.getTabletLocationsList()) {
      // Early creating the tablet so that it parses out the pb
      RemoteTablet rt = createTabletFromPb(tableId, tabletPb);
      Slice tabletId = rt.tabletId;

      // If we already know about this one, just refresh the locations
      RemoteTablet currentTablet = tablet2client.get(tabletId);
      if (currentTablet != null) {
        currentTablet.refreshServers(tabletPb);
        continue;
      }

      // Putting it here first doesn't make it visible because tabletsCache is always looked up
      // first.
      RemoteTablet oldRt = tablet2client.putIfAbsent(tabletId, rt);
      if (oldRt != null) {
        // someone beat us to it
        continue;
      }
      LOG.info("Discovered tablet {} for table {} with partition {}",
               tabletId.toString(Charset.defaultCharset()), tableName, rt.getPartition());
      rt.refreshServers(tabletPb);
      // This is making this tablet available
      // Even if two clients were racing in this method they are putting the same RemoteTablet
      // with the same start key in the CSLM in the end
      tablets.put(rt.getPartition().getPartitionKeyStart(), rt);
    }
  }

  RemoteTablet createTabletFromPb(String tableId, Master.TabletLocationsPB tabletPb) {
    Partition partition = ProtobufHelper.pbToPartition(tabletPb.getPartition());
    Slice tabletId = new Slice(tabletPb.getTabletId().toByteArray());
    return new RemoteTablet(tableId, tabletId, partition);
  }

  /**
   * Gives the tablet's ID for the table ID and partition key.
   * In the future there will be multiple tablets and this method will find the right one.
   * @param tableId table to find the tablet for
   * @return a tablet ID as a slice or null if not found
   */
  RemoteTablet getTablet(String tableId, byte[] partitionKey) {
    ConcurrentSkipListMap<byte[], RemoteTablet> tablets = tabletsCache.get(tableId);

    if (tablets == null) {
      return null;
    }

    // We currently only have one master tablet.
    if (isMasterTable(tableId)) {
      if (tablets.firstEntry() == null) {
        return null;
      }
      return tablets.firstEntry().getValue();
    }

    Map.Entry<byte[], RemoteTablet> tabletPair = tablets.floorEntry(partitionKey);

    if (tabletPair == null) {
      return null;
    }

    Partition partition = tabletPair.getValue().getPartition();

    // If the partition is not the end partition, but it doesn't include the key
    // we are looking for, then we have not yet found the correct tablet.
    if (!partition.isEndPartition()
        && Bytes.memcmp(partitionKey, partition.getPartitionKeyEnd()) >= 0) {
      return null;
    }

    return tabletPair.getValue();
  }

  /**
   * Retrieve the master registration (see {@link GetMasterRegistrationResponse}
   * for a replica.
   * @param masterClient An initialized client for the master replica.
   * @return A Deferred object for the master replica's current registration.
   */
  Deferred<GetMasterRegistrationResponse> getMasterRegistration(TabletClient masterClient) {
    GetMasterRegistrationRequest rpc = new GetMasterRegistrationRequest(masterTable);
    rpc.setTimeoutMillis(defaultAdminOperationTimeoutMs);
    Deferred<GetMasterRegistrationResponse> d = rpc.getDeferred();
    rpc.attempt++;
    masterClient.sendRpc(rpc);
    return d;
  }

  /**
   * If a live client already exists for the specified master server, returns that client;
   * otherwise, creates a new client for the specified master server.
   * @param masterHostPort The RPC host and port for the master server.
   * @return A live and initialized client for the specified master server.
   */
  TabletClient newMasterClient(HostAndPort masterHostPort) {
    String ip = getIP(masterHostPort.getHostText());
    if (ip == null) {
      return null;
    }
    // We should pass a UUID here but we have a chicken and egg problem, we first need to
    // communicate with the masters to find out about them, and that's what we're trying to do.
    // The UUID is used for logging, so instead we're passing the "master table name" followed by
    // host and port which is enough to identify the node we're connecting to.
    return newClient(MASTER_TABLE_NAME_PLACEHOLDER + " - " + masterHostPort.toString(),
        ip, masterHostPort.getPort());
  }

  TabletClient newClient(String uuid, final String host, final int port) {
    final String hostport = host + ':' + port;
    TabletClient client;
    SocketChannel chan;
    synchronized (ip2client) {
      client = ip2client.get(hostport);
      if (client != null && client.isAlive()) {
        return client;
      }
      final TabletClientPipeline pipeline = new TabletClientPipeline();
      client = pipeline.init(uuid);
      chan = channelFactory.newChannel(pipeline);
      ip2client.put(hostport, client);  // This is guaranteed to return null.
    }
    this.client2tablets.put(client, new ArrayList<RemoteTablet>());
    final SocketChannelConfig config = chan.getConfig();
    config.setConnectTimeoutMillis(5000);
    config.setTcpNoDelay(true);
    // Unfortunately there is no way to override the keep-alive timeout in
    // Java since the JRE doesn't expose any way to call setsockopt() with
    // TCP_KEEPIDLE.  And of course the default timeout is >2h. Sigh.
    config.setKeepAlive(true);
    chan.connect(new InetSocketAddress(host, port));  // Won't block.
    return client;
  }

  /**
   * Invokes {@link #shutdown()} and waits for the configured admin timeout. This method returns
   * void, so consider invoking shutdown directly if there's a need to handle dangling RPCs.
   * @throws Exception if an error happens while closing the connections
   */
  @Override
  public void close() throws Exception {
    shutdown().join(defaultAdminOperationTimeoutMs);
  }

  /**
   * Performs a graceful shutdown of this instance.
   * <p>
   * <ul>
   *   <li>{@link AsyncKuduSession#flush Flushes} all buffered edits.</li>
   *   <li>Cancels all the other requests.</li>
   *   <li>Terminates all connections.</li>
   *   <li>Releases all other resources.</li>
   * </ul>
   * <strong>Not calling this method before losing the last reference to this
   * instance may result in data loss and other unwanted side effects</strong>
   * @return A {@link Deferred}, whose callback chain will be invoked once all
   * of the above have been done. If this callback chain doesn't fail, then
   * the clean shutdown will be successful, and all the data will be safe on
   * the Kudu side. In case of a failure (the "errback" is invoked) you will have
   * to open a new AsyncKuduClient if you want to retry those operations.
   * The Deferred doesn't actually hold any content.
   */
  public Deferred<ArrayList<Void>> shutdown() {
    checkIsClosed();
    closed = true;
    // This is part of step 3.  We need to execute this in its own thread
    // because Netty gets stuck in an infinite loop if you try to shut it
    // down from within a thread of its own thread pool.  They don't want
    // to fix this so as a workaround we always shut Netty's thread pool
    // down from another thread.
    final class ShutdownThread extends Thread {
      ShutdownThread() {
        super("AsyncKuduClient@" + AsyncKuduClient.super.hashCode() + " shutdown");
      }
      public void run() {
        // This terminates the Executor.
        channelFactory.releaseExternalResources();
      }
    }

    // 3. Release all other resources.
    final class ReleaseResourcesCB implements Callback<ArrayList<Void>, ArrayList<Void>> {
      public ArrayList<Void> call(final ArrayList<Void> arg) {
        LOG.debug("Releasing all remaining resources");
        timer.stop();
        new ShutdownThread().start();
        return arg;
      }
      public String toString() {
        return "release resources callback";
      }
    }

    // 2. Terminate all connections.
    final class DisconnectCB implements Callback<Deferred<ArrayList<Void>>,
        ArrayList<List<OperationResponse>>> {
      public Deferred<ArrayList<Void>> call(final ArrayList<List<OperationResponse>> arg) {
        return disconnectEverything().addCallback(new ReleaseResourcesCB());
      }
      public String toString() {
        return "disconnect callback";
      }
    }
    // 1. Flush everything.
    return closeAllSessions().addBothDeferring(new DisconnectCB());
  }

  private void checkIsClosed() {
    if (closed) {
      throw new IllegalStateException("Cannot proceed, the client has already been closed");
    }
  }

  private Deferred<ArrayList<List<OperationResponse>>> closeAllSessions() {
    // We create a copy because AsyncKuduSession.close will call removeSession which would get us a
    // concurrent modification during the iteration.
    Set<AsyncKuduSession> copyOfSessions;
    synchronized (sessions) {
      copyOfSessions = new HashSet<AsyncKuduSession>(sessions);
    }
    if (sessions.isEmpty()) {
      return Deferred.fromResult(null);
    }
    // Guaranteed that we'll have at least one session to close.
    List<Deferred<List<OperationResponse>>> deferreds = new ArrayList<>(copyOfSessions.size());
    for (AsyncKuduSession session : copyOfSessions ) {
      deferreds.add(session.close());
    }

    return Deferred.group(deferreds);
  }

  /**
   * Closes every socket, which will also cancel all the RPCs in flight.
   */
  private Deferred<ArrayList<Void>> disconnectEverything() {
    ArrayList<Deferred<Void>> deferreds =
        new ArrayList<Deferred<Void>>(2);
    HashMap<String, TabletClient> ip2client_copy;
    synchronized (ip2client) {
      // Make a local copy so we can shutdown every Tablet Server clients
      // without hold the lock while we iterate over the data structure.
      ip2client_copy = new HashMap<String, TabletClient>(ip2client);
    }

    for (TabletClient ts : ip2client_copy.values()) {
      deferreds.add(ts.shutdown());
    }
    final int size = deferreds.size();
    return Deferred.group(deferreds).addCallback(
        new Callback<ArrayList<Void>, ArrayList<Void>>() {
          public ArrayList<Void> call(final ArrayList<Void> arg) {
            // Normally, now that we've shutdown() every client, all our caches should
            // be empty since each shutdown() generates a DISCONNECTED event, which
            // causes TabletClientPipeline to call removeClientFromCache().
            HashMap<String, TabletClient> logme = null;
            synchronized (ip2client) {
              if (!ip2client.isEmpty()) {
                logme = new HashMap<String, TabletClient>(ip2client);
              }
            }
            if (logme != null) {
              // Putting this logging statement inside the synchronized block
              // can lead to a deadlock, since HashMap.toString() is going to
              // call TabletClient.toString() on each entry, and this locks the
              // client briefly.  Other parts of the code lock clients first and
              // the ip2client HashMap second, so this can easily deadlock.
              LOG.error("Some clients are left in the client cache and haven't"
                  + " been cleaned up: " + logme);
            }
            return arg;
          }

          public String toString() {
            return "wait " + size + " TabletClient.shutdown()";
          }
        });
  }

  /**
   * Blocking call.
   * Performs a slow search of the IP used by the given client.
   * <p>
   * This is needed when we're trying to find the IP of the client before its
   * channel has successfully connected, because Netty's API offers no way of
   * retrieving the IP of the remote peer until we're connected to it.
   * @param client The client we want the IP of.
   * @return The IP of the client, or {@code null} if we couldn't find it.
   */
  private InetSocketAddress slowSearchClientIP(final TabletClient client) {
    String hostport = null;
    synchronized (ip2client) {
      for (final Map.Entry<String, TabletClient> e : ip2client.entrySet()) {
        if (e.getValue() == client) {
          hostport = e.getKey();
          break;
        }
      }
    }

    if (hostport == null) {
      HashMap<String, TabletClient> copy;
      synchronized (ip2client) {
        copy = new HashMap<String, TabletClient>(ip2client);
      }
      LOG.error("WTF?  Should never happen!  Couldn't find " + client
          + " in " + copy);
      return null;
    }
    final int colon = hostport.indexOf(':', 1);
    if (colon < 1) {
      LOG.error("WTF?  Should never happen!  No `:' found in " + hostport);
      return null;
    }
    final String host = getIP(hostport.substring(0, colon));
    if (host == null) {
      // getIP will print the reason why, there's nothing else we can do.
      return null;
    }

    int port;
    try {
      port = parsePortNumber(hostport.substring(colon + 1,
          hostport.length()));
    } catch (NumberFormatException e) {
      LOG.error("WTF?  Should never happen!  Bad port in " + hostport, e);
      return null;
    }
    return new InetSocketAddress(host, port);
  }

  /**
   * Removes all the cache entries referred to the given client.
   * @param client The client for which we must invalidate everything.
   * @param remote The address of the remote peer, if known, or null.
   */
  private void removeClientFromCache(final TabletClient client,
                                     final SocketAddress remote) {

    if (remote == null) {
      return;  // Can't continue without knowing the remote address.
    }

    String hostport;
    if (remote instanceof InetSocketAddress) {
      final InetSocketAddress sock = (InetSocketAddress) remote;
      final InetAddress addr = sock.getAddress();
      if (addr == null) {
        LOG.error("WTF?  Unresolved IP for " + remote
            + ".  This shouldn't happen.");
        return;
      } else {
        hostport = addr.getHostAddress() + ':' + sock.getPort();
      }
    } else {
      LOG.error("WTF?  Found a non-InetSocketAddress remote: " + remote
          + ".  This shouldn't happen.");
      return;
    }

    TabletClient old;
    synchronized (ip2client) {
      old = ip2client.remove(hostport);
    }
    LOG.debug("Removed from IP cache: {" + hostport + "} -> {" + client + "}");
    if (old == null) {
      // Currently we're seeing this message when masters are disconnected and the hostport we got
      // above is different than the one the user passes (that we use to populate ip2client). At
      // worst this doubles the entries for masters, which has an insignificant impact.
      // TODO When fixed, make this a WARN again.
      LOG.trace("When expiring " + client + " from the client cache (host:port="
          + hostport + "), it was found that there was no entry"
          + " corresponding to " + remote + ".  This shouldn't happen.");
    }

    ArrayList<RemoteTablet> tablets = client2tablets.remove(client);
    if (tablets != null) {
      // Make a copy so we don't need to synchronize on it while iterating.
      RemoteTablet[] tablets_copy;
      synchronized (tablets) {
        tablets_copy = tablets.toArray(new RemoteTablet[tablets.size()]);
        tablets = null;
        // If any other thread still has a reference to `tablets', their
        // updates will be lost (and we don't care).
      }
      for (final RemoteTablet remoteTablet : tablets_copy) {
        remoteTablet.removeTabletServer(client);
      }
    }
  }

  private boolean isMasterTable(String tableId) {
    // Checking that it's the same instance so there's absolutely no chance of confusing the master
    // 'table' for a user one.
    return MASTER_TABLE_NAME_PLACEHOLDER == tableId;
  }

  private final class TabletClientPipeline extends DefaultChannelPipeline {

    private final Logger log = LoggerFactory.getLogger(TabletClientPipeline.class);
    /**
     * Have we already disconnected?.
     * We use this to avoid doing the cleanup work for the same client more
     * than once, even if we get multiple events indicating that the client
     * is no longer connected to the TabletServer (e.g. DISCONNECTED, CLOSED).
     * No synchronization needed as this is always accessed from only one
     * thread at a time (equivalent to a non-shared state in a Netty handler).
     */
    private boolean disconnected = false;

    TabletClient init(String uuid) {
      final TabletClient client = new TabletClient(AsyncKuduClient.this, uuid);
      if (defaultSocketReadTimeoutMs > 0) {
        super.addLast("timeout-handler",
            new ReadTimeoutHandler(timer,
                defaultSocketReadTimeoutMs,
                TimeUnit.MILLISECONDS));
      }
      super.addLast("kudu-handler", client);

      return client;
    }

    @Override
    public void sendDownstream(final ChannelEvent event) {
      if (event instanceof ChannelStateEvent) {
        handleDisconnect((ChannelStateEvent) event);
      }
      super.sendDownstream(event);
    }

    @Override
    public void sendUpstream(final ChannelEvent event) {
      if (event instanceof ChannelStateEvent) {
        handleDisconnect((ChannelStateEvent) event);
      }
      super.sendUpstream(event);
    }

    private void handleDisconnect(final ChannelStateEvent state_event) {
      if (disconnected) {
        return;
      }
      switch (state_event.getState()) {
        case OPEN:
          if (state_event.getValue() == Boolean.FALSE) {
            break;  // CLOSED
          }
          return;
        case CONNECTED:
          if (state_event.getValue() == null) {
            break;  // DISCONNECTED
          }
          return;
        default:
          return;  // Not an event we're interested in, ignore it.
      }

      disconnected = true;  // So we don't clean up the same client twice.
      try {
        final TabletClient client = super.get(TabletClient.class);
        SocketAddress remote = super.getChannel().getRemoteAddress();
        // At this point Netty gives us no easy way to access the
        // SocketAddress of the peer we tried to connect to. This
        // kinda sucks but I couldn't find an easier way.
        if (remote == null) {
          remote = slowSearchClientIP(client);
        }

        // Prevent the client from buffering requests while we invalidate
        // everything we have about it.
        synchronized (client) {
          removeClientFromCache(client, remote);
        }
      } catch (Exception e) {
        log.error("Uncaught exception when handling a disconnection of " + getChannel(), e);
      }
    }

  }

  /**
   * Gets a hostname or an IP address and returns the textual representation
   * of the IP address.
   * <p>
   * <strong>This method can block</strong> as there is no API for
   * asynchronous DNS resolution in the JDK.
   * @param host The hostname to resolve.
   * @return The IP address associated with the given hostname,
   * or {@code null} if the address couldn't be resolved.
   */
  private static String getIP(final String host) {
    final long start = System.nanoTime();
    try {
      final String ip = InetAddress.getByName(host).getHostAddress();
      final long latency = System.nanoTime() - start;
      if (latency > 500000/*ns*/ && LOG.isDebugEnabled()) {
        LOG.debug("Resolved IP of `" + host + "' to "
            + ip + " in " + latency + "ns");
      } else if (latency >= 3000000/*ns*/) {
        LOG.warn("Slow DNS lookup!  Resolved IP of `" + host + "' to "
            + ip + " in " + latency + "ns");
      }
      return ip;
    } catch (UnknownHostException e) {
      LOG.error("Failed to resolve the IP of `" + host + "' in "
          + (System.nanoTime() - start) + "ns");
      return null;
    }
  }

  /**
   * Parses a TCP port number from a string.
   * @param portnum The string to parse.
   * @return A strictly positive, validated port number.
   * @throws NumberFormatException if the string couldn't be parsed as an
   * integer or if the value was outside of the range allowed for TCP ports.
   */
  private static int parsePortNumber(final String portnum)
      throws NumberFormatException {
    final int port = Integer.parseInt(portnum);
    if (port <= 0 || port > 65535) {
      throw new NumberFormatException(port == 0 ? "port is zero" :
          (port < 0 ? "port is negative: "
              : "port is too large: ") + port);
    }
    return port;
  }

  void newTimeout(final TimerTask task, final long timeout_ms) {
    try {
      timer.newTimeout(task, timeout_ms, MILLISECONDS);
    } catch (IllegalStateException e) {
      // This can happen if the timer fires just before shutdown()
      // is called from another thread, and due to how threads get
      // scheduled we tried to call newTimeout() after timer.stop().
      LOG.warn("Failed to schedule timer."
          + "  Ignore this if we're shutting down.", e);
    }
  }

  /**
   * This class encapsulates the information regarding a tablet and its locations.
   *
   * Leader failover mechanism:
   * When we get a complete peer list from the master, we place the leader in the first
   * position of the tabletServers array. When we detect that it isn't the leader anymore (in
   * TabletClient), we demote it and set the next TS in the array as the leader. When the RPC
   * gets retried, it will use that TS since we always pick the leader.
   *
   * If that TS turns out to not be the leader, we will demote it and promote the next one, retry.
   * When we hit the end of the list, we set the leaderIndex to NO_LEADER_INDEX which forces us
   * to fetch the tablet locations from the master. We'll repeat this whole process until a RPC
   * succeeds.
   *
   * Subtleties:
   * We don't keep track of a TS after it disconnects (via removeTabletServer), so if we
   * haven't contacted one for 10 seconds (socket timeout), it will be removed from the list of
   * tabletServers. This means that if the leader fails, we only have one other TS to "promote"
   * or maybe none at all. This is partly why we then set leaderIndex to NO_LEADER_INDEX.
   *
   * The effect of treating a TS as the new leader means that the Scanner will also try to hit it
   * with requests. It's currently unclear if that's a good or a bad thing.
   *
   * Unlike the C++ client, we don't short-circuit the call to the master if it isn't available.
   * This means that after trying all the peers to find the leader, we might get stuck waiting on
   * a reachable master.
   */
  public class RemoteTablet implements Comparable<RemoteTablet> {

    private static final int NO_LEADER_INDEX = -1;
    private final String tableId;
    private final Slice tabletId;
    private final ArrayList<TabletClient> tabletServers = new ArrayList<TabletClient>();
    private final Partition partition;
    private int leaderIndex = NO_LEADER_INDEX;

    RemoteTablet(String tableId, Slice tabletId, Partition partition) {
      this.tabletId = tabletId;
      this.tableId = tableId;
      this.partition = partition;
    }

    void refreshServers(Master.TabletLocationsPB tabletLocations) throws NonRecoverableException {

      synchronized (tabletServers) { // TODO not a fat lock with IP resolving in it
        tabletServers.clear();
        leaderIndex = NO_LEADER_INDEX;
        List<UnknownHostException> lookupExceptions =
            new ArrayList<>(tabletLocations.getReplicasCount());
        for (Master.TabletLocationsPB.ReplicaPB replica : tabletLocations.getReplicasList()) {

          List<Common.HostPortPB> addresses = replica.getTsInfo().getRpcAddressesList();
          if (addresses.isEmpty()) {
            LOG.warn("Tablet server for tablet " + getTabletIdAsString() + " doesn't have any " +
                "address");
            continue;
          }
          byte[] buf = Bytes.get(replica.getTsInfo().getPermanentUuid());
          String uuid = Bytes.getString(buf);
          // from meta_cache.cc
          // TODO: if the TS advertises multiple host/ports, pick the right one
          // based on some kind of policy. For now just use the first always.
          try {
            addTabletClient(uuid, addresses.get(0).getHost(), addresses.get(0).getPort(),
                replica.getRole().equals(Metadata.RaftPeerPB.Role.LEADER));
          } catch (UnknownHostException ex) {
            lookupExceptions.add(ex);
          }
        }
        leaderIndex = 0;
        if (leaderIndex == NO_LEADER_INDEX) {
          LOG.warn("No leader provided for tablet " + getTabletIdAsString());
        }

        // If we found a tablet that doesn't contain a single location that we can resolve, there's
        // no point in retrying.
        if (!lookupExceptions.isEmpty() &&
            lookupExceptions.size() == tabletLocations.getReplicasCount()) {
          throw new NonRecoverableException("Couldn't find any valid locations, exceptions: " +
              lookupExceptions);
        }
      }
    }

    // Must be called with tabletServers synchronized
    void addTabletClient(String uuid, String host, int port, boolean isLeader)
        throws UnknownHostException {
      String ip = getIP(host);
      if (ip == null) {
        throw new UnknownHostException("Failed to resolve the IP of `" + host + "'");
      }
      TabletClient client = newClient(uuid, ip, port);

      final ArrayList<RemoteTablet> tablets = client2tablets.get(client);

      if (tablets == null) {
        // We raced with removeClientFromCache and lost. The client we got was just disconnected.
        // Reconnect.
        addTabletClient(uuid, host, port, isLeader);
      } else {
        synchronized (tablets) {
          if (isLeader) {
            tabletServers.add(0, client);
          } else {
            tabletServers.add(client);
          }
          tablets.add(this);
        }
      }
    }

    @Override
    public String toString() {
      return getTabletIdAsString();
    }

    /**
     * Removes the passed TabletClient from this tablet's list of tablet servers. If it was the
     * leader, then we "promote" the next one unless it was the last one in the list.
     * @param ts A TabletClient that was disconnected.
     * @return True if this method removed ts from the list, else false.
     */
    boolean removeTabletServer(TabletClient ts) {
      synchronized (tabletServers) {
        // TODO unit test for this once we have the infra
        int index = tabletServers.indexOf(ts);
        if (index == -1) {
          return false; // we removed it already
        }

        tabletServers.remove(index);
        if (leaderIndex == index && leaderIndex == tabletServers.size()) {
          leaderIndex = NO_LEADER_INDEX;
        } else if (leaderIndex > index) {
          leaderIndex--; // leader moved down the list
        }

        return true;
        // TODO if we reach 0 TS, maybe we should remove ourselves?
      }
    }

    /**
     * If the passed TabletClient is the current leader, then the next one in the list will be
     * "promoted" unless we're at the end of the list, in which case we set the leaderIndex to
     * NO_LEADER_INDEX which will force a call to the master.
     * @param ts A TabletClient that gave a sign that it isn't this tablet's leader.
     */
    void demoteLeader(TabletClient ts) {
      synchronized (tabletServers) {
        int index = tabletServers.indexOf(ts);
        // If this TS was removed or we're already forcing a call to the master (meaning someone
        // else beat us to it), then we just noop.
        if (index == -1 || leaderIndex == NO_LEADER_INDEX) {
          return;
        }

        if (leaderIndex == index) {
          if (leaderIndex + 1 == tabletServers.size()) {
            leaderIndex = NO_LEADER_INDEX;
          } else {
            leaderIndex++;
          }
        }
      }
    }

    public String getTableId() {
      return tableId;
    }

    Slice getTabletId() {
      return tabletId;
    }

    public Partition getPartition() {
      return partition;
    }

    byte[] getTabletIdAsBytes() {
      return tabletId.getBytes();
    }

    String getTabletIdAsString() {
      return tabletId.toString(Charset.defaultCharset());
    }

    List<Common.HostPortPB> getAddressesFromPb(Master.TabletLocationsPB tabletLocations) {
      List<Common.HostPortPB> addresses = new ArrayList<Common.HostPortPB>(tabletLocations
          .getReplicasCount());
      for (Master.TabletLocationsPB.ReplicaPB replica : tabletLocations.getReplicasList()) {
        addresses.add(replica.getTsInfo().getRpcAddresses(0));
      }
      return addresses;
    }

    @Override
    public int compareTo(RemoteTablet remoteTablet) {
      if (remoteTablet == null) {
        return 1;
      }

      return ComparisonChain.start()
          .compare(this.tableId, remoteTablet.tableId)
          .compare(this.partition, remoteTablet.partition).result();
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      RemoteTablet that = (RemoteTablet) o;

      return this.compareTo(that) == 0;
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(tableId, partition);
    }
  }

  /**
   * Builder class to use in order to connect to Kudu.
   * All the parameters beyond those in the constructors are optional.
   */
  public final static class AsyncKuduClientBuilder {
    private static final int DEFAULT_MASTER_PORT = 7051;
    private static final int DEFAULT_BOSS_COUNT = 1;
    private static final int DEFAULT_WORKER_COUNT = 2 * Runtime.getRuntime().availableProcessors();

    private final List<HostAndPort> masterAddresses;
    private long defaultAdminOperationTimeoutMs = DEFAULT_OPERATION_TIMEOUT_MS;
    private long defaultOperationTimeoutMs = DEFAULT_OPERATION_TIMEOUT_MS;
    private long defaultSocketReadTimeoutMs = DEFAULT_SOCKET_READ_TIMEOUT_MS;

    private final HashedWheelTimer timer =
        new HashedWheelTimer(new ThreadFactoryBuilder().setDaemon(true).build(), 20, MILLISECONDS);
    private Executor bossExecutor;
    private Executor workerExecutor;
    private int bossCount = DEFAULT_BOSS_COUNT;
    private int workerCount = DEFAULT_WORKER_COUNT;

    /**
     * Creates a new builder for a client that will connect to the specified masters.
     * @param masterAddresses comma-separated list of "host:port" pairs of the masters
     */
    public AsyncKuduClientBuilder(String masterAddresses) {
      this.masterAddresses =
          NetUtil.parseStrings(masterAddresses, DEFAULT_MASTER_PORT);
    }

    /**
     * Creates a new builder for a client that will connect to the specified masters.
     *
     * <p>Here are some examples of recognized formats:
     * <ul>
     *   <li>example.com
     *   <li>example.com:80
     *   <li>192.0.2.1
     *   <li>192.0.2.1:80
     *   <li>[2001:db8::1]
     *   <li>[2001:db8::1]:80
     *   <li>2001:db8::1
     * </ul>
     *
     * @param masterAddresses list of master addresses
     */
    public AsyncKuduClientBuilder(List<String> masterAddresses) {
      this.masterAddresses =
          Lists.newArrayListWithCapacity(masterAddresses.size());
      for (String address : masterAddresses) {
        this.masterAddresses.add(
            NetUtil.parseString(address, DEFAULT_MASTER_PORT));
      }
    }

    /**
     * Sets the default timeout used for administrative operations (e.g. createTable, deleteTable,
     * etc).
     * Optional.
     * If not provided, defaults to 10s.
     * A value of 0 disables the timeout.
     * @param timeoutMs a timeout in milliseconds
     * @return this builder
     */
    public AsyncKuduClientBuilder defaultAdminOperationTimeoutMs(long timeoutMs) {
      this.defaultAdminOperationTimeoutMs = timeoutMs;
      return this;
    }

    /**
     * Sets the default timeout used for user operations (using sessions and scanners).
     * Optional.
     * If not provided, defaults to 10s.
     * A value of 0 disables the timeout.
     * @param timeoutMs a timeout in milliseconds
     * @return this builder
     */
    public AsyncKuduClientBuilder defaultOperationTimeoutMs(long timeoutMs) {
      this.defaultOperationTimeoutMs = timeoutMs;
      return this;
    }

    /**
     * Sets the default timeout to use when waiting on data from a socket.
     * Optional.
     * If not provided, defaults to 5s.
     * A value of 0 disables the timeout.
     * @param timeoutMs a timeout in milliseconds
     * @return this builder
     */
    public AsyncKuduClientBuilder defaultSocketReadTimeoutMs(long timeoutMs) {
      this.defaultSocketReadTimeoutMs = timeoutMs;
      return this;
    }

    /**
     * Set the executors which will be used for the embedded Netty boss and workers.
     * Optional.
     * If not provided, uses a simple cached threadpool. If either argument is null,
     * then such a thread pool will be used in place of that argument.
     * Note: executor's max thread number must be greater or equal to corresponding
     * worker count, or netty cannot start enough threads, and client will get stuck.
     * If not sure, please just use CachedThreadPool.
     */
    public AsyncKuduClientBuilder nioExecutors(Executor bossExecutor, Executor workerExecutor) {
      this.bossExecutor = bossExecutor;
      this.workerExecutor = workerExecutor;
      return this;
    }

    /**
     * Set the maximum number of boss threads.
     * Optional.
     * If not provided, 1 is used.
     */
    public AsyncKuduClientBuilder bossCount(int bossCount) {
      Preconditions.checkArgument(bossCount > 0, "bossCount should be greater than 0");
      this.bossCount = bossCount;
      return this;
    }

    /**
     * Set the maximum number of worker threads.
     * Optional.
     * If not provided, (2 * the number of available processors) is used.
     */
    public AsyncKuduClientBuilder workerCount(int workerCount) {
      Preconditions.checkArgument(workerCount > 0, "workerCount should be greater than 0");
      this.workerCount = workerCount;
      return this;
    }

    /**
     * Creates the channel factory for Netty. The user can specify the executors, but
     * if they don't, we'll use a simple thread pool.
     */
    private NioClientSocketChannelFactory createChannelFactory() {
      Executor boss = bossExecutor;
      Executor worker = workerExecutor;
      if (boss == null || worker == null) {
        Executor defaultExec = Executors.newCachedThreadPool(
            new ThreadFactoryBuilder()
                .setNameFormat("kudu-nio-%d")
                .setDaemon(true)
                .build());
        if (boss == null) boss = defaultExec;
        if (worker == null) worker = defaultExec;
      }
      // Share the timer with the socket channel factory so that it does not
      // create an internal timer with a non-daemon thread.
      return new NioClientSocketChannelFactory(boss,
                                               bossCount,
                                               new NioWorkerPool(worker, workerCount),
                                               timer);
    }

    /**
     * Creates a new client that connects to the masters.
     * Doesn't block and won't throw an exception if the masters don't exist.
     * @return a new asynchronous Kudu client
     */
    public AsyncKuduClient build() {
      return new AsyncKuduClient(this);
    }
  }
}
