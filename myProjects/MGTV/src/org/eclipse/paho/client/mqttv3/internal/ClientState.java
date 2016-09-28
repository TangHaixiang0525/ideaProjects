package org.eclipse.paho.client.mqttv3.internal;

import java.io.EOFException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnack;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPingReq;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPingResp;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubAck;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubComp;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubRec;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPubRel;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class ClientState
{
  private static final int MAX_MSG_ID = 65535;
  private static final int MIN_MSG_ID = 1;
  private static final String PERSISTENCE_CONFIRMED_PREFIX = "sc-";
  private static final String PERSISTENCE_RECEIVED_PREFIX = "r-";
  private static final String PERSISTENCE_SENT_PREFIX = "s-";
  private static final String className = ClientState.class.getName();
  private int actualInFlight = 0;
  private CommsCallback callback = null;
  private boolean cleanSession;
  private ClientComms clientComms = null;
  private boolean connected = false;
  private int inFlightPubRels = 0;
  private Hashtable inUseMsgIds;
  private Hashtable inboundQoS2 = null;
  private long keepAlive;
  private long lastInboundActivity = 0L;
  private long lastOutboundActivity = 0L;
  private long lastPing = 0L;
  private Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", className);
  private int maxInflight = 10;
  private int nextMsgId = 0;
  private Hashtable outboundQoS1 = null;
  private Hashtable outboundQoS2 = null;
  private volatile Vector pendingFlows;
  private volatile Vector pendingMessages;
  private MqttClientPersistence persistence;
  private MqttWireMessage pingCommand;
  private boolean pingOutstanding = false;
  private Object queueLock = new Object();
  private Object quiesceLock = new Object();
  private boolean quiescing = false;
  private CommsTokenStore tokenStore;

  protected ClientState(MqttClientPersistence paramMqttClientPersistence, CommsTokenStore paramCommsTokenStore, CommsCallback paramCommsCallback, ClientComms paramClientComms)
    throws MqttException
  {
    this.log.setResourceName(paramClientComms.getClient().getClientId());
    this.log.finer(className, "<Init>", "");
    this.inUseMsgIds = new Hashtable();
    this.pendingMessages = new Vector(this.maxInflight);
    this.pendingFlows = new Vector();
    this.outboundQoS2 = new Hashtable();
    this.outboundQoS1 = new Hashtable();
    this.inboundQoS2 = new Hashtable();
    this.pingCommand = new MqttPingReq();
    this.inFlightPubRels = 0;
    this.actualInFlight = 0;
    this.persistence = paramMqttClientPersistence;
    this.callback = paramCommsCallback;
    this.tokenStore = paramCommsTokenStore;
    this.clientComms = paramClientComms;
    restoreState();
  }

  private void checkForActivity()
    throws MqttException
  {
    long l;
    if ((this.connected) && (this.keepAlive > 0L))
    {
      l = System.currentTimeMillis();
      if (this.pingOutstanding)
        break label189;
      if ((l - this.lastOutboundActivity >= this.keepAlive) || (l - this.lastInboundActivity >= this.keepAlive))
      {
        localLogger2 = this.log;
        str2 = className;
        arrayOfObject2 = new Object[3];
        arrayOfObject2[0] = new Long(this.keepAlive);
        arrayOfObject2[1] = new Long(this.lastOutboundActivity);
        arrayOfObject2[2] = new Long(this.lastInboundActivity);
        localLogger2.fine(str2, "checkForActivity", "620", arrayOfObject2);
        this.pingOutstanding = true;
        this.lastPing = l;
        localMqttToken = new MqttToken(this.clientComms.getClient().getClientId());
        this.tokenStore.saveToken(localMqttToken, this.pingCommand);
        this.pendingFlows.insertElementAt(this.pingCommand, 0);
      }
    }
    label189: 
    while (l - this.lastPing < this.keepAlive)
    {
      Logger localLogger2;
      String str2;
      Object[] arrayOfObject2;
      MqttToken localMqttToken;
      return;
    }
    Logger localLogger1 = this.log;
    String str1 = className;
    Object[] arrayOfObject1 = new Object[3];
    arrayOfObject1[0] = new Long(this.keepAlive);
    arrayOfObject1[1] = new Long(this.lastOutboundActivity);
    arrayOfObject1[2] = new Long(this.lastInboundActivity);
    localLogger1.severe(str1, "checkForActivity", "619", arrayOfObject1);
    throw ExceptionHelper.createMqttException(32000);
  }

  private void decrementInFlight()
  {
    synchronized (this.queueLock)
    {
      this.actualInFlight = (-1 + this.actualInFlight);
      Logger localLogger = this.log;
      String str = className;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = new Integer(this.actualInFlight);
      localLogger.fine(str, "decrementInFlight", "646", arrayOfObject);
      if (!checkQuiesceLock())
        this.queueLock.notifyAll();
      return;
    }
  }

  private int getNextMessageId()
    throws MqttException
  {
    do
      try
      {
        int i = this.nextMsgId;
        int j = 0;
        this.nextMsgId = (1 + this.nextMsgId);
        if (this.nextMsgId > 65535)
          this.nextMsgId = 1;
        if (this.nextMsgId == i)
        {
          j++;
          if (j == 2)
            throw ExceptionHelper.createMqttException(32001);
        }
      }
      finally
      {
      }
    while (this.inUseMsgIds.containsKey(new Integer(this.nextMsgId)));
    Integer localInteger = new Integer(this.nextMsgId);
    this.inUseMsgIds.put(localInteger, localInteger);
    int k = this.nextMsgId;
    return k;
  }

  private String getReceivedPersistenceKey(MqttWireMessage paramMqttWireMessage)
  {
    return "r-" + paramMqttWireMessage.getMessageId();
  }

  private String getSendConfirmPersistenceKey(MqttWireMessage paramMqttWireMessage)
  {
    return "sc-" + paramMqttWireMessage.getMessageId();
  }

  private String getSendPersistenceKey(MqttWireMessage paramMqttWireMessage)
  {
    return "s-" + paramMqttWireMessage.getMessageId();
  }

  private void insertInOrder(Vector paramVector, MqttWireMessage paramMqttWireMessage)
  {
    int i = paramMqttWireMessage.getMessageId();
    for (int j = 0; j < paramVector.size(); j++)
      if (((MqttWireMessage)paramVector.elementAt(j)).getMessageId() > i)
      {
        paramVector.insertElementAt(paramMqttWireMessage, j);
        return;
      }
    paramVector.addElement(paramMqttWireMessage);
  }

  private Vector reOrder(Vector paramVector)
  {
    Vector localVector = new Vector();
    if (paramVector.size() == 0);
    while (true)
    {
      return localVector;
      int i = 0;
      int j = 0;
      int k = 0;
      for (int m = 0; m < paramVector.size(); m++)
      {
        int i2 = ((MqttWireMessage)paramVector.elementAt(m)).getMessageId();
        if (i2 - i > j)
        {
          j = i2 - i;
          k = m;
        }
        i = i2;
      }
      if (((MqttWireMessage)paramVector.elementAt(0)).getMessageId() + (65535 - i) > j)
        k = 0;
      for (int n = k; n < paramVector.size(); n++)
        localVector.addElement(paramVector.elementAt(n));
      for (int i1 = 0; i1 < k; i1++)
        localVector.addElement(paramVector.elementAt(i1));
    }
  }

  private void releaseMessageId(int paramInt)
  {
    try
    {
      this.inUseMsgIds.remove(new Integer(paramInt));
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private void restoreInflightMessages()
  {
    this.pendingMessages = new Vector(this.maxInflight);
    this.pendingFlows = new Vector();
    Enumeration localEnumeration1 = this.outboundQoS2.keys();
    while (localEnumeration1.hasMoreElements())
    {
      Object localObject2 = localEnumeration1.nextElement();
      Object localObject3 = this.outboundQoS2.get(localObject2);
      if ((localObject3 instanceof MqttPublish))
      {
        this.log.fine(className, "restoreInflightMessages", "610", new Object[] { localObject2 });
        insertInOrder(this.pendingMessages, (MqttPublish)localObject3);
      }
      else if ((localObject3 instanceof MqttPubRel))
      {
        this.log.fine(className, "restoreInflightMessages", "611", new Object[] { localObject2 });
        insertInOrder(this.pendingFlows, (MqttPubRel)localObject3);
      }
    }
    Enumeration localEnumeration2 = this.outboundQoS1.keys();
    while (localEnumeration2.hasMoreElements())
    {
      Object localObject1 = localEnumeration2.nextElement();
      MqttPublish localMqttPublish = (MqttPublish)this.outboundQoS1.get(localObject1);
      this.log.fine(className, "restoreInflightMessages", "612", new Object[] { localObject1 });
      insertInOrder(this.pendingMessages, localMqttPublish);
    }
    this.pendingFlows = reOrder(this.pendingFlows);
    this.pendingMessages = reOrder(this.pendingMessages);
  }

  private MqttWireMessage restoreMessage(String paramString, MqttPersistable paramMqttPersistable)
    throws MqttException
  {
    try
    {
      MqttWireMessage localMqttWireMessage2 = MqttWireMessage.createWireMessage(paramMqttPersistable);
      localMqttWireMessage1 = localMqttWireMessage2;
      this.log.fine(className, "restoreMessage", "601", new Object[] { paramString, localMqttWireMessage1 });
      return localMqttWireMessage1;
    }
    catch (MqttException localMqttException)
    {
      while (true)
      {
        this.log.fine(className, "restoreMessage", "602", new Object[] { paramString }, localMqttException);
        if (!(localMqttException.getCause() instanceof EOFException))
          break;
        MqttWireMessage localMqttWireMessage1 = null;
        if (paramString != null)
        {
          this.persistence.remove(paramString);
          localMqttWireMessage1 = null;
        }
      }
      throw localMqttException;
    }
  }

  protected boolean checkQuiesceLock()
  {
    int i = this.tokenStore.count();
    if ((this.quiescing) && (i == 0) && (this.pendingFlows.size() == 0) && (this.callback.isQuiesced()))
    {
      Logger localLogger = this.log;
      String str = className;
      Object[] arrayOfObject = new Object[6];
      arrayOfObject[0] = new Boolean(this.quiescing);
      arrayOfObject[1] = new Integer(this.actualInFlight);
      arrayOfObject[2] = new Integer(this.pendingFlows.size());
      arrayOfObject[3] = new Integer(this.inFlightPubRels);
      arrayOfObject[4] = new Boolean(this.callback.isQuiesced());
      arrayOfObject[5] = new Integer(i);
      localLogger.fine(str, "checkQuiesceLock", "626", arrayOfObject);
      synchronized (this.quiesceLock)
      {
        this.quiesceLock.notifyAll();
        return true;
      }
    }
    return false;
  }

  protected void clearState()
    throws MqttException
  {
    this.log.fine(className, "clearState", ">");
    this.persistence.clear();
    this.inUseMsgIds.clear();
    this.pendingMessages.clear();
    this.pendingFlows.clear();
    this.outboundQoS2.clear();
    this.outboundQoS1.clear();
    this.inboundQoS2.clear();
    this.tokenStore.clear();
  }

  protected void close()
  {
    this.inUseMsgIds.clear();
    this.pendingMessages.clear();
    this.pendingFlows.clear();
    this.outboundQoS2.clear();
    this.outboundQoS1.clear();
    this.inboundQoS2.clear();
    this.tokenStore.clear();
    this.inUseMsgIds = null;
    this.pendingMessages = null;
    this.pendingFlows = null;
    this.outboundQoS2 = null;
    this.outboundQoS1 = null;
    this.inboundQoS2 = null;
    this.tokenStore = null;
    this.callback = null;
    this.clientComms = null;
    this.persistence = null;
    this.pingCommand = null;
  }

  public void connected()
  {
    this.log.fine(className, "connected", "631");
    this.connected = true;
  }

  protected void deliveryComplete(MqttPublish paramMqttPublish)
    throws MqttPersistenceException
  {
    Logger localLogger = this.log;
    String str = className;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = new Integer(paramMqttPublish.getMessageId());
    localLogger.fine(str, "deliveryComplete", "641", arrayOfObject);
    this.persistence.remove(getReceivedPersistenceKey(paramMqttPublish));
    this.inboundQoS2.remove(new Integer(paramMqttPublish.getMessageId()));
  }

  public void disconnected(MqttException paramMqttException)
  {
    this.log.fine(className, "disconnected", "633", new Object[] { paramMqttException });
    this.connected = false;
    try
    {
      if (this.cleanSession)
        clearState();
      this.pendingMessages.clear();
      this.pendingFlows.clear();
      this.pingOutstanding = false;
      return;
    }
    catch (MqttException localMqttException)
    {
    }
  }

  // ERROR //
  protected MqttWireMessage get()
    throws MqttException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: getfield 87	org/eclipse/paho/client/mqttv3/internal/ClientState:queueLock	Ljava/lang/Object;
    //   6: astore_2
    //   7: aload_2
    //   8: monitorenter
    //   9: aload_1
    //   10: ifnonnull +409 -> 419
    //   13: aload_0
    //   14: getfield 154	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingMessages	Ljava/util/Vector;
    //   17: invokevirtual 394	java/util/Vector:isEmpty	()Z
    //   20: ifeq +13 -> 33
    //   23: aload_0
    //   24: getfield 157	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingFlows	Ljava/util/Vector;
    //   27: invokevirtual 394	java/util/Vector:isEmpty	()Z
    //   30: ifne +32 -> 62
    //   33: aload_0
    //   34: getfield 157	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingFlows	Ljava/util/Vector;
    //   37: invokevirtual 394	java/util/Vector:isEmpty	()Z
    //   40: ifeq +86 -> 126
    //   43: aload_0
    //   44: getfield 83	org/eclipse/paho/client/mqttv3/internal/ClientState:actualInFlight	I
    //   47: istore 11
    //   49: aload_0
    //   50: getfield 81	org/eclipse/paho/client/mqttv3/internal/ClientState:maxInflight	I
    //   53: istore 12
    //   55: iload 11
    //   57: iload 12
    //   59: if_icmplt +67 -> 126
    //   62: aload_0
    //   63: invokevirtual 397	org/eclipse/paho/client/mqttv3/internal/ClientState:getTimeUntilPing	()J
    //   66: lstore 14
    //   68: aload_0
    //   69: getfield 117	org/eclipse/paho/client/mqttv3/internal/ClientState:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   72: astore 16
    //   74: getstatic 67	org/eclipse/paho/client/mqttv3/internal/ClientState:className	Ljava/lang/String;
    //   77: astore 17
    //   79: iconst_1
    //   80: anewarray 4	java/lang/Object
    //   83: astore 18
    //   85: aload 18
    //   87: iconst_0
    //   88: new 180	java/lang/Long
    //   91: dup
    //   92: lload 14
    //   94: invokespecial 183	java/lang/Long:<init>	(J)V
    //   97: aastore
    //   98: aload 16
    //   100: aload 17
    //   102: ldc_w 398
    //   105: ldc_w 400
    //   108: aload 18
    //   110: invokeinterface 190 5 0
    //   115: aload_0
    //   116: getfield 87	org/eclipse/paho/client/mqttv3/internal/ClientState:queueLock	Ljava/lang/Object;
    //   119: aload_0
    //   120: invokevirtual 397	org/eclipse/paho/client/mqttv3/internal/ClientState:getTimeUntilPing	()J
    //   123: invokevirtual 403	java/lang/Object:wait	(J)V
    //   126: aload_0
    //   127: getfield 101	org/eclipse/paho/client/mqttv3/internal/ClientState:connected	Z
    //   130: ifne +52 -> 182
    //   133: aload_0
    //   134: getfield 157	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingFlows	Ljava/util/Vector;
    //   137: invokevirtual 394	java/util/Vector:isEmpty	()Z
    //   140: ifne +20 -> 160
    //   143: aload_0
    //   144: getfield 157	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingFlows	Ljava/util/Vector;
    //   147: iconst_0
    //   148: invokevirtual 270	java/util/Vector:elementAt	(I)Ljava/lang/Object;
    //   151: checkcast 250	org/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage
    //   154: instanceof 405
    //   157: ifne +25 -> 182
    //   160: aload_0
    //   161: getfield 117	org/eclipse/paho/client/mqttv3/internal/ClientState:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   164: getstatic 67	org/eclipse/paho/client/mqttv3/internal/ClientState:className	Ljava/lang/String;
    //   167: ldc_w 398
    //   170: ldc_w 407
    //   173: invokeinterface 359 4 0
    //   178: aload_2
    //   179: monitorexit
    //   180: aconst_null
    //   181: areturn
    //   182: aload_0
    //   183: invokespecial 409	org/eclipse/paho/client/mqttv3/internal/ClientState:checkForActivity	()V
    //   186: aload_0
    //   187: getfield 157	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingFlows	Ljava/util/Vector;
    //   190: invokevirtual 394	java/util/Vector:isEmpty	()Z
    //   193: ifne +102 -> 295
    //   196: aload_0
    //   197: getfield 157	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingFlows	Ljava/util/Vector;
    //   200: iconst_0
    //   201: invokevirtual 270	java/util/Vector:elementAt	(I)Ljava/lang/Object;
    //   204: checkcast 250	org/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage
    //   207: astore_1
    //   208: aload_0
    //   209: getfield 157	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingFlows	Ljava/util/Vector;
    //   212: iconst_0
    //   213: invokevirtual 412	java/util/Vector:removeElementAt	(I)V
    //   216: aload_1
    //   217: instanceof 307
    //   220: ifeq +62 -> 282
    //   223: aload_0
    //   224: iconst_1
    //   225: aload_0
    //   226: getfield 85	org/eclipse/paho/client/mqttv3/internal/ClientState:inFlightPubRels	I
    //   229: iadd
    //   230: putfield 85	org/eclipse/paho/client/mqttv3/internal/ClientState:inFlightPubRels	I
    //   233: aload_0
    //   234: getfield 117	org/eclipse/paho/client/mqttv3/internal/ClientState:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   237: astore 8
    //   239: getstatic 67	org/eclipse/paho/client/mqttv3/internal/ClientState:className	Ljava/lang/String;
    //   242: astore 9
    //   244: iconst_1
    //   245: anewarray 4	java/lang/Object
    //   248: astore 10
    //   250: aload 10
    //   252: iconst_0
    //   253: new 218	java/lang/Integer
    //   256: dup
    //   257: aload_0
    //   258: getfield 85	org/eclipse/paho/client/mqttv3/internal/ClientState:inFlightPubRels	I
    //   261: invokespecial 219	java/lang/Integer:<init>	(I)V
    //   264: aastore
    //   265: aload 8
    //   267: aload 9
    //   269: ldc_w 398
    //   272: ldc_w 414
    //   275: aload 10
    //   277: invokeinterface 190 5 0
    //   282: aload_0
    //   283: invokevirtual 226	org/eclipse/paho/client/mqttv3/internal/ClientState:checkQuiesceLock	()Z
    //   286: pop
    //   287: goto -278 -> 9
    //   290: astore_3
    //   291: aload_2
    //   292: monitorexit
    //   293: aload_3
    //   294: athrow
    //   295: aload_0
    //   296: getfield 154	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingMessages	Ljava/util/Vector;
    //   299: invokevirtual 394	java/util/Vector:isEmpty	()Z
    //   302: ifne -293 -> 9
    //   305: aload_0
    //   306: getfield 83	org/eclipse/paho/client/mqttv3/internal/ClientState:actualInFlight	I
    //   309: aload_0
    //   310: getfield 81	org/eclipse/paho/client/mqttv3/internal/ClientState:maxInflight	I
    //   313: if_icmpge +85 -> 398
    //   316: aload_0
    //   317: getfield 154	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingMessages	Ljava/util/Vector;
    //   320: iconst_0
    //   321: invokevirtual 270	java/util/Vector:elementAt	(I)Ljava/lang/Object;
    //   324: checkcast 250	org/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage
    //   327: astore_1
    //   328: aload_0
    //   329: getfield 154	org/eclipse/paho/client/mqttv3/internal/ClientState:pendingMessages	Ljava/util/Vector;
    //   332: iconst_0
    //   333: invokevirtual 412	java/util/Vector:removeElementAt	(I)V
    //   336: aload_0
    //   337: iconst_1
    //   338: aload_0
    //   339: getfield 83	org/eclipse/paho/client/mqttv3/internal/ClientState:actualInFlight	I
    //   342: iadd
    //   343: putfield 83	org/eclipse/paho/client/mqttv3/internal/ClientState:actualInFlight	I
    //   346: aload_0
    //   347: getfield 117	org/eclipse/paho/client/mqttv3/internal/ClientState:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   350: astore 4
    //   352: getstatic 67	org/eclipse/paho/client/mqttv3/internal/ClientState:className	Ljava/lang/String;
    //   355: astore 5
    //   357: iconst_1
    //   358: anewarray 4	java/lang/Object
    //   361: astore 6
    //   363: aload 6
    //   365: iconst_0
    //   366: new 218	java/lang/Integer
    //   369: dup
    //   370: aload_0
    //   371: getfield 83	org/eclipse/paho/client/mqttv3/internal/ClientState:actualInFlight	I
    //   374: invokespecial 219	java/lang/Integer:<init>	(I)V
    //   377: aastore
    //   378: aload 4
    //   380: aload 5
    //   382: ldc_w 398
    //   385: ldc_w 416
    //   388: aload 6
    //   390: invokeinterface 190 5 0
    //   395: goto -386 -> 9
    //   398: aload_0
    //   399: getfield 117	org/eclipse/paho/client/mqttv3/internal/ClientState:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
    //   402: getstatic 67	org/eclipse/paho/client/mqttv3/internal/ClientState:className	Ljava/lang/String;
    //   405: ldc_w 398
    //   408: ldc_w 418
    //   411: invokeinterface 359 4 0
    //   416: goto -407 -> 9
    //   419: aload_2
    //   420: monitorexit
    //   421: aload_1
    //   422: areturn
    //   423: astore 13
    //   425: goto -299 -> 126
    //
    // Exception table:
    //   from	to	target	type
    //   13	33	290	finally
    //   33	55	290	finally
    //   62	126	290	finally
    //   126	160	290	finally
    //   160	180	290	finally
    //   182	282	290	finally
    //   282	287	290	finally
    //   291	293	290	finally
    //   295	395	290	finally
    //   398	416	290	finally
    //   419	421	290	finally
    //   62	126	423	java/lang/InterruptedException
  }

  public Properties getDebug()
  {
    Properties localProperties = new Properties();
    localProperties.put("In use msgids", this.inUseMsgIds);
    localProperties.put("pendingMessages", this.pendingMessages);
    localProperties.put("pendingFlows", this.pendingFlows);
    localProperties.put("maxInflight", new Integer(this.maxInflight));
    localProperties.put("nextMsgID", new Integer(this.nextMsgId));
    localProperties.put("actualInFlight", new Integer(this.actualInFlight));
    localProperties.put("inFlightPubRels", new Integer(this.inFlightPubRels));
    localProperties.put("quiescing", new Boolean(this.quiescing));
    localProperties.put("pingoutstanding", new Boolean(this.pingOutstanding));
    localProperties.put("lastOutboundActivity", new Long(this.lastOutboundActivity));
    localProperties.put("lastInboundActivity", new Long(this.lastInboundActivity));
    localProperties.put("outboundQoS2", this.outboundQoS2);
    localProperties.put("outboundQoS1", this.outboundQoS1);
    localProperties.put("inboundQoS2", this.inboundQoS2);
    localProperties.put("tokens", this.tokenStore);
    return localProperties;
  }

  protected long getKeepAlive()
  {
    return this.keepAlive;
  }

  long getTimeUntilPing()
  {
    long l1 = getKeepAlive();
    long l3;
    long l4;
    if ((this.connected) && (getKeepAlive() > 0L) && (!this.pingOutstanding))
    {
      long l2 = System.currentTimeMillis();
      l3 = l2 - this.lastOutboundActivity;
      l4 = l2 - this.lastInboundActivity;
      if (l3 <= l4)
        break label76;
    }
    label76: for (l1 = getKeepAlive() - l3; ; l1 = getKeepAlive() - l4)
    {
      if (l1 <= 0L)
        l1 = 10L;
      return l1;
    }
  }

  protected void notifyComplete(MqttToken paramMqttToken)
    throws MqttException
  {
    MqttWireMessage localMqttWireMessage = paramMqttToken.internalTok.getWireMessage();
    MqttAck localMqttAck;
    if ((localMqttWireMessage != null) && ((localMqttWireMessage instanceof MqttAck)))
    {
      Logger localLogger1 = this.log;
      String str1 = className;
      Object[] arrayOfObject1 = new Object[3];
      arrayOfObject1[0] = new Integer(localMqttWireMessage.getMessageId());
      arrayOfObject1[1] = paramMqttToken;
      arrayOfObject1[2] = localMqttWireMessage;
      localLogger1.fine(str1, "notifyComplete", "629", arrayOfObject1);
      localMqttAck = (MqttAck)localMqttWireMessage;
      if (!(localMqttAck instanceof MqttPubAck))
        break label201;
      this.persistence.remove(getSendPersistenceKey(localMqttWireMessage));
      this.outboundQoS1.remove(new Integer(localMqttAck.getMessageId()));
      decrementInFlight();
      releaseMessageId(localMqttWireMessage.getMessageId());
      this.tokenStore.removeToken(localMqttWireMessage);
      Logger localLogger3 = this.log;
      String str3 = className;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = new Integer(localMqttAck.getMessageId());
      localLogger3.fine(str3, "notifyComplete", "650", arrayOfObject3);
    }
    while (true)
    {
      checkQuiesceLock();
      return;
      label201: if ((localMqttAck instanceof MqttPubComp))
      {
        this.persistence.remove(getSendPersistenceKey(localMqttWireMessage));
        this.persistence.remove(getSendConfirmPersistenceKey(localMqttWireMessage));
        this.outboundQoS2.remove(new Integer(localMqttAck.getMessageId()));
        this.inFlightPubRels = (-1 + this.inFlightPubRels);
        decrementInFlight();
        releaseMessageId(localMqttWireMessage.getMessageId());
        this.tokenStore.removeToken(localMqttWireMessage);
        Logger localLogger2 = this.log;
        String str2 = className;
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = new Integer(localMqttAck.getMessageId());
        arrayOfObject2[1] = new Integer(this.inFlightPubRels);
        localLogger2.fine(str2, "notifyComplete", "645", arrayOfObject2);
      }
    }
  }

  protected void notifyQueueLock()
  {
    synchronized (this.queueLock)
    {
      this.log.fine(className, "notifyQueueLock", "638");
      this.queueLock.notifyAll();
      return;
    }
  }

  protected void notifyReceivedAck(MqttAck paramMqttAck)
    throws MqttException
  {
    this.lastInboundActivity = System.currentTimeMillis();
    Logger localLogger = this.log;
    String str = className;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = new Integer(paramMqttAck.getMessageId());
    arrayOfObject[1] = paramMqttAck;
    localLogger.fine(str, "notifyReceivedAck", "627", arrayOfObject);
    MqttToken localMqttToken = this.tokenStore.getToken(paramMqttAck);
    if ((paramMqttAck instanceof MqttPubRec))
      send(new MqttPubRel((MqttPubRec)paramMqttAck), localMqttToken);
    while (true)
    {
      checkQuiesceLock();
      return;
      if (((paramMqttAck instanceof MqttPubAck)) || ((paramMqttAck instanceof MqttPubComp)))
      {
        notifyResult(paramMqttAck, localMqttToken, null);
      }
      else if ((paramMqttAck instanceof MqttPingResp))
      {
        this.pingOutstanding = false;
        notifyResult(paramMqttAck, localMqttToken, null);
        this.tokenStore.removeToken(paramMqttAck);
      }
      else
      {
        if ((paramMqttAck instanceof MqttConnack))
        {
          int i = ((MqttConnack)paramMqttAck).getReturnCode();
          if (i == 0)
            synchronized (this.queueLock)
            {
              if (this.cleanSession)
              {
                clearState();
                this.tokenStore.saveToken(localMqttToken, paramMqttAck);
              }
              this.inFlightPubRels = 0;
              this.actualInFlight = 0;
              restoreInflightMessages();
              connected();
              this.clientComms.connectComplete((MqttConnack)paramMqttAck, null);
              notifyResult(paramMqttAck, localMqttToken, null);
              this.tokenStore.removeToken(paramMqttAck);
              synchronized (this.queueLock)
              {
                this.queueLock.notifyAll();
              }
            }
          throw ExceptionHelper.createMqttException(i);
        }
        notifyResult(paramMqttAck, localMqttToken, null);
        releaseMessageId(paramMqttAck.getMessageId());
        this.tokenStore.removeToken(paramMqttAck);
      }
    }
  }

  protected void notifyReceivedMsg(MqttWireMessage paramMqttWireMessage)
    throws MqttException
  {
    this.lastInboundActivity = System.currentTimeMillis();
    Logger localLogger = this.log;
    String str = className;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = new Integer(paramMqttWireMessage.getMessageId());
    arrayOfObject[1] = paramMqttWireMessage;
    localLogger.fine(str, "notifyReceivedMsg", "651", arrayOfObject);
    MqttPublish localMqttPublish2;
    if (!this.quiescing)
    {
      if (!(paramMqttWireMessage instanceof MqttPublish))
        break label185;
      localMqttPublish2 = (MqttPublish)paramMqttWireMessage;
      switch (localMqttPublish2.getMessage().getQos())
      {
      default:
      case 0:
      case 1:
      case 2:
      }
    }
    label185: MqttPublish localMqttPublish1;
    do
    {
      do
      {
        do
          return;
        while (this.callback == null);
        this.callback.messageArrived(localMqttPublish2);
        return;
        this.persistence.put(getReceivedPersistenceKey(paramMqttWireMessage), (MqttPublish)paramMqttWireMessage);
        this.inboundQoS2.put(new Integer(localMqttPublish2.getMessageId()), localMqttPublish2);
        send(new MqttPubRec(localMqttPublish2), null);
        return;
      }
      while (!(paramMqttWireMessage instanceof MqttPubRel));
      localMqttPublish1 = (MqttPublish)this.inboundQoS2.get(new Integer(paramMqttWireMessage.getMessageId()));
      if (localMqttPublish1 == null)
        break;
    }
    while (this.callback == null);
    this.callback.messageArrived(localMqttPublish1);
    return;
    send(new MqttPubComp(paramMqttWireMessage.getMessageId()), null);
  }

  protected void notifyResult(MqttWireMessage paramMqttWireMessage, MqttToken paramMqttToken, MqttException paramMqttException)
  {
    paramMqttToken.internalTok.markComplete(paramMqttWireMessage, paramMqttException);
    if ((paramMqttWireMessage != null) && ((paramMqttWireMessage instanceof MqttAck)) && (!(paramMqttWireMessage instanceof MqttPubRec)))
    {
      Logger localLogger2 = this.log;
      String str2 = className;
      Object[] arrayOfObject2 = new Object[3];
      arrayOfObject2[0] = paramMqttToken.internalTok.getKey();
      arrayOfObject2[1] = paramMqttWireMessage;
      arrayOfObject2[2] = paramMqttException;
      localLogger2.fine(str2, "notifyResult", "648", arrayOfObject2);
      this.callback.asyncOperationComplete(paramMqttToken);
    }
    if (paramMqttWireMessage == null)
    {
      Logger localLogger1 = this.log;
      String str1 = className;
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = paramMqttToken.internalTok.getKey();
      arrayOfObject1[1] = paramMqttException;
      localLogger1.fine(str1, "notifyResult", "649", arrayOfObject1);
      this.callback.asyncOperationComplete(paramMqttToken);
    }
  }

  protected void notifySent(MqttWireMessage paramMqttWireMessage)
  {
    this.lastOutboundActivity = System.currentTimeMillis();
    Logger localLogger = this.log;
    String str = className;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramMqttWireMessage.getKey();
    localLogger.fine(str, "notifySent", "625", arrayOfObject);
    MqttToken localMqttToken = this.tokenStore.getToken(paramMqttWireMessage);
    localMqttToken.internalTok.notifySent();
    if (((paramMqttWireMessage instanceof MqttPublish)) && (((MqttPublish)paramMqttWireMessage).getMessage().getQos() == 0))
    {
      localMqttToken.internalTok.markComplete(null, null);
      this.callback.asyncOperationComplete(localMqttToken);
      decrementInFlight();
      releaseMessageId(paramMqttWireMessage.getMessageId());
      this.tokenStore.removeToken(paramMqttWireMessage);
      checkQuiesceLock();
    }
  }

  public void quiesce(long paramLong)
  {
    if (paramLong > 0L)
    {
      Logger localLogger1 = this.log;
      String str1 = className;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = new Long(paramLong);
      localLogger1.fine(str1, "quiesce", "637", arrayOfObject1);
      synchronized (this.queueLock)
      {
        this.quiescing = true;
        this.callback.quiesce();
        notifyQueueLock();
      }
    }
    try
    {
      label224: synchronized (this.quiesceLock)
      {
        int i = this.tokenStore.count();
        if ((i > 0) || (this.pendingFlows.size() > 0) || (!this.callback.isQuiesced()))
        {
          Logger localLogger2 = this.log;
          String str2 = className;
          Object[] arrayOfObject2 = new Object[4];
          arrayOfObject2[0] = new Integer(this.actualInFlight);
          arrayOfObject2[1] = new Integer(this.pendingFlows.size());
          arrayOfObject2[2] = new Integer(this.inFlightPubRels);
          arrayOfObject2[3] = new Integer(i);
          localLogger2.fine(str2, "quiesce", "639", arrayOfObject2);
          this.quiesceLock.wait(paramLong);
        }
      }
      synchronized (this.queueLock)
      {
        this.pendingMessages.clear();
        this.pendingFlows.clear();
        this.quiescing = false;
        this.actualInFlight = 0;
        this.log.fine(className, "quiesce", "640");
        return;
        localObject2 = finally;
        throw localObject2;
        localObject4 = finally;
        throw localObject4;
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      break label224;
    }
  }

  public Vector resolveOldTokens(MqttException paramMqttException)
  {
    this.log.fine(className, "resolveOldTokens", "632", new Object[] { paramMqttException });
    MqttException localMqttException = paramMqttException;
    if (paramMqttException == null)
      localMqttException = new MqttException(32102);
    Vector localVector = this.tokenStore.getOutstandingTokens();
    Enumeration localEnumeration = localVector.elements();
    while (localEnumeration.hasMoreElements())
      synchronized ((MqttToken)localEnumeration.nextElement())
      {
        if ((!???.isComplete()) && (!???.internalTok.isCompletePending()) && (???.getException() == null))
          ???.internalTok.setException(localMqttException);
        if (!(??? instanceof MqttDeliveryToken))
          this.tokenStore.removeToken(???.internalTok.getKey());
      }
    return localVector;
  }

  protected void restoreState()
    throws MqttException
  {
    Enumeration localEnumeration1 = this.persistence.keys();
    int i = this.nextMsgId;
    Vector localVector = new Vector();
    this.log.fine(className, "restoreState", "600");
    while (localEnumeration1.hasMoreElements())
    {
      String str2 = (String)localEnumeration1.nextElement();
      MqttWireMessage localMqttWireMessage = restoreMessage(str2, this.persistence.get(str2));
      if (localMqttWireMessage != null)
        if (str2.startsWith("r-"))
        {
          this.log.fine(className, "restoreState", "604", new Object[] { str2, localMqttWireMessage });
          this.inboundQoS2.put(new Integer(localMqttWireMessage.getMessageId()), localMqttWireMessage);
        }
        else
        {
          if (str2.startsWith("s-"))
          {
            MqttPublish localMqttPublish = (MqttPublish)localMqttWireMessage;
            i = Math.max(localMqttPublish.getMessageId(), i);
            if (this.persistence.containsKey(getSendConfirmPersistenceKey(localMqttPublish)))
            {
              MqttPubRel localMqttPubRel2 = (MqttPubRel)restoreMessage(str2, this.persistence.get(getSendConfirmPersistenceKey(localMqttPublish)));
              if (localMqttPubRel2 != null)
              {
                localMqttPubRel2.setDuplicate(true);
                this.log.fine(className, "restoreState", "605", new Object[] { str2, localMqttWireMessage });
                this.outboundQoS2.put(new Integer(localMqttPubRel2.getMessageId()), localMqttPubRel2);
              }
            }
            while (true)
            {
              this.tokenStore.restoreToken(localMqttPublish).internalTok.setClient(this.clientComms.getClient());
              this.inUseMsgIds.put(new Integer(localMqttPublish.getMessageId()), new Integer(localMqttPublish.getMessageId()));
              break;
              this.log.fine(className, "restoreState", "606", new Object[] { str2, localMqttWireMessage });
              continue;
              localMqttPublish.setDuplicate(true);
              if (localMqttPublish.getMessage().getQos() == 2)
              {
                this.log.fine(className, "restoreState", "607", new Object[] { str2, localMqttWireMessage });
                this.outboundQoS2.put(new Integer(localMqttPublish.getMessageId()), localMqttPublish);
              }
              else
              {
                this.log.fine(className, "restoreState", "608", new Object[] { str2, localMqttWireMessage });
                this.outboundQoS1.put(new Integer(localMqttPublish.getMessageId()), localMqttPublish);
              }
            }
          }
          if (str2.startsWith("sc-"))
          {
            MqttPubRel localMqttPubRel1 = (MqttPubRel)localMqttWireMessage;
            if (!this.persistence.containsKey(getSendPersistenceKey(localMqttPubRel1)))
              localVector.addElement(str2);
          }
        }
    }
    Enumeration localEnumeration2 = localVector.elements();
    while (localEnumeration2.hasMoreElements())
    {
      String str1 = (String)localEnumeration2.nextElement();
      this.log.fine(className, "restoreState", "609", new Object[] { str1 });
      this.persistence.remove(str1);
    }
    this.nextMsgId = i;
  }

  public void send(MqttWireMessage paramMqttWireMessage, MqttToken paramMqttToken)
    throws MqttException
  {
    if ((paramMqttWireMessage.isMessageIdRequired()) && (paramMqttWireMessage.getMessageId() == 0))
      paramMqttWireMessage.setMessageId(getNextMessageId());
    if (paramMqttToken != null);
    try
    {
      paramMqttToken.internalTok.setMessageID(paramMqttWireMessage.getMessageId());
      label37: if ((paramMqttWireMessage instanceof MqttPublish))
      {
        synchronized (this.queueLock)
        {
          if (this.actualInFlight >= this.maxInflight)
          {
            Logger localLogger3 = this.log;
            String str3 = className;
            Object[] arrayOfObject3 = new Object[1];
            arrayOfObject3[0] = new Integer(this.actualInFlight);
            localLogger3.fine(str3, "send", "613", arrayOfObject3);
            throw new MqttException(32202);
          }
        }
        MqttMessage localMqttMessage = ((MqttPublish)paramMqttWireMessage).getMessage();
        Logger localLogger2 = this.log;
        String str2 = className;
        Object[] arrayOfObject2 = new Object[3];
        arrayOfObject2[0] = new Integer(paramMqttWireMessage.getMessageId());
        arrayOfObject2[1] = new Integer(localMqttMessage.getQos());
        arrayOfObject2[2] = paramMqttWireMessage;
        localLogger2.fine(str2, "send", "628", arrayOfObject2);
        switch (localMqttMessage.getQos())
        {
        default:
        case 2:
        case 1:
        }
        while (true)
        {
          this.tokenStore.saveToken(paramMqttToken, paramMqttWireMessage);
          this.pendingMessages.addElement(paramMqttWireMessage);
          this.queueLock.notifyAll();
          return;
          this.outboundQoS2.put(new Integer(paramMqttWireMessage.getMessageId()), paramMqttWireMessage);
          this.persistence.put(getSendPersistenceKey(paramMqttWireMessage), (MqttPublish)paramMqttWireMessage);
          continue;
          this.outboundQoS1.put(new Integer(paramMqttWireMessage.getMessageId()), paramMqttWireMessage);
          this.persistence.put(getSendPersistenceKey(paramMqttWireMessage), (MqttPublish)paramMqttWireMessage);
        }
      }
      Logger localLogger1 = this.log;
      String str1 = className;
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = new Integer(paramMqttWireMessage.getMessageId());
      arrayOfObject1[1] = paramMqttWireMessage;
      localLogger1.fine(str1, "send", "615", arrayOfObject1);
      if ((paramMqttWireMessage instanceof MqttConnect))
        synchronized (this.queueLock)
        {
          this.tokenStore.saveToken(paramMqttToken, paramMqttWireMessage);
          this.pendingFlows.insertElementAt(paramMqttWireMessage, 0);
          this.queueLock.notifyAll();
          return;
        }
      if ((paramMqttWireMessage instanceof MqttPingReq))
        this.pingCommand = paramMqttWireMessage;
      while (true)
      {
        synchronized (this.queueLock)
        {
          if (!(paramMqttWireMessage instanceof MqttAck))
            this.tokenStore.saveToken(paramMqttToken, paramMqttWireMessage);
          this.pendingFlows.addElement(paramMqttWireMessage);
          this.queueLock.notifyAll();
          return;
        }
        if ((paramMqttWireMessage instanceof MqttPubRel))
        {
          this.outboundQoS2.put(new Integer(paramMqttWireMessage.getMessageId()), paramMqttWireMessage);
          this.persistence.put(getSendConfirmPersistenceKey(paramMqttWireMessage), (MqttPubRel)paramMqttWireMessage);
        }
        else if ((paramMqttWireMessage instanceof MqttPubComp))
        {
          this.persistence.remove(getReceivedPersistenceKey(paramMqttWireMessage));
        }
      }
    }
    catch (Exception localException)
    {
      break label37;
    }
  }

  protected void setCleanSession(boolean paramBoolean)
  {
    this.cleanSession = paramBoolean;
  }

  public void setKeepAliveInterval(long paramLong)
  {
    this.keepAlive = paramLong;
  }

  protected void setKeepAliveSecs(long paramLong)
  {
    this.keepAlive = (1000L * paramLong);
  }

  protected void undo(MqttPublish paramMqttPublish)
    throws MqttPersistenceException
  {
    synchronized (this.queueLock)
    {
      Logger localLogger = this.log;
      String str = className;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = new Integer(paramMqttPublish.getMessageId());
      arrayOfObject[1] = new Integer(paramMqttPublish.getMessage().getQos());
      localLogger.fine(str, "undo", "618", arrayOfObject);
      if (paramMqttPublish.getMessage().getQos() == 1)
      {
        this.outboundQoS1.remove(new Integer(paramMqttPublish.getMessageId()));
        this.pendingMessages.removeElement(paramMqttPublish);
        this.persistence.remove(getSendPersistenceKey(paramMqttPublish));
        this.tokenStore.removeToken(paramMqttPublish);
        checkQuiesceLock();
        return;
      }
      this.outboundQoS2.remove(new Integer(paramMqttPublish.getMessageId()));
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.ClientState
 * JD-Core Version:    0.6.2
 */