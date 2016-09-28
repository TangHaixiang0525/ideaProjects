package org.eclipse.paho.client.mqttv3.internal;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnack;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttDisconnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class ClientComms
{
  public static String BUILD_LEVEL = "L20130821-0933";
  static final byte CLOSED = 4;
  static final byte CONNECTED = 0;
  static final byte CONNECTING = 1;
  static final byte DISCONNECTED = 3;
  static final byte DISCONNECTING = 2;
  public static String VERSION = "0.4.0";
  static final String className = ClientComms.class.getName();
  CommsCallback callback;
  private IMqttAsyncClient client;
  ClientState clientState;
  private boolean closePending = false;
  Object conLock = new Object();
  MqttConnectOptions conOptions;
  private byte conState = 3;
  Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", className);
  private int networkModuleIndex;
  private NetworkModule[] networkModules;
  private MqttClientPersistence persistence;
  CommsReceiver receiver;
  CommsSender sender;
  boolean stoppingComms = false;
  CommsTokenStore tokenStore;

  public ClientComms(IMqttAsyncClient paramIMqttAsyncClient, MqttClientPersistence paramMqttClientPersistence)
    throws MqttException
  {
    this.client = paramIMqttAsyncClient;
    this.persistence = paramMqttClientPersistence;
    this.tokenStore = new CommsTokenStore(getClient().getClientId());
    this.callback = new CommsCallback(this);
    this.clientState = new ClientState(paramMqttClientPersistence, this.tokenStore, this.callback, this);
    this.callback.setClientState(this.clientState);
    this.log.setResourceName(getClient().getClientId());
  }

  private MqttToken handleOldTokens(MqttToken paramMqttToken, MqttException paramMqttException)
  {
    this.log.fine(className, "handleOldTokens", "222");
    Object localObject = null;
    if (paramMqttToken != null);
    while (true)
    {
      MqttToken localMqttToken1;
      try
      {
        MqttToken localMqttToken2 = this.tokenStore.getToken(paramMqttToken.internalTok.getKey());
        localObject = null;
        if (localMqttToken2 == null)
          this.tokenStore.saveToken(paramMqttToken, paramMqttToken.internalTok.getKey());
        Enumeration localEnumeration = this.clientState.resolveOldTokens(paramMqttException).elements();
        if (localEnumeration.hasMoreElements())
        {
          localMqttToken1 = (MqttToken)localEnumeration.nextElement();
          if ((localMqttToken1.internalTok.getKey().equals(MqttDisconnect.KEY)) || (localMqttToken1.internalTok.getKey().equals(MqttConnect.KEY)))
            break label148;
          this.callback.asyncOperationComplete(localMqttToken1);
          continue;
        }
      }
      catch (Exception localException)
      {
      }
      return localObject;
      label148: localObject = localMqttToken1;
    }
  }

  public void close()
    throws MqttException
  {
    synchronized (this.conLock)
    {
      if (isClosed())
        break label141;
      if (isDisconnected())
        break label89;
      this.log.fine(className, "close", "224");
      if (isConnecting())
        throw new MqttException(32110);
    }
    if (isConnected())
      throw ExceptionHelper.createMqttException(32100);
    if (isDisconnecting())
    {
      this.closePending = true;
      return;
    }
    label89: this.conState = 4;
    this.clientState.close();
    this.clientState = null;
    this.callback = null;
    this.persistence = null;
    this.sender = null;
    this.receiver = null;
    this.networkModules = null;
    this.conOptions = null;
    this.tokenStore = null;
    label141:
  }

  public void connect(MqttConnectOptions paramMqttConnectOptions, MqttToken paramMqttToken)
    throws MqttException
  {
    synchronized (this.conLock)
    {
      if ((isDisconnected()) && (!this.closePending))
      {
        this.log.fine(className, "connect", "214");
        this.conState = 1;
        this.conOptions = paramMqttConnectOptions;
        MqttConnect localMqttConnect = new MqttConnect(this.client.getClientId(), paramMqttConnectOptions.isCleanSession(), paramMqttConnectOptions.getKeepAliveInterval(), paramMqttConnectOptions.getUserName(), paramMqttConnectOptions.getPassword(), paramMqttConnectOptions.getWillMessage(), paramMqttConnectOptions.getWillDestination());
        this.clientState.setKeepAliveSecs(paramMqttConnectOptions.getKeepAliveInterval());
        this.clientState.setCleanSession(paramMqttConnectOptions.isCleanSession());
        this.tokenStore.open();
        new ConnectBG(this, paramMqttToken, localMqttConnect).start();
        return;
      }
      Logger localLogger = this.log;
      String str = className;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = new Byte(this.conState);
      localLogger.fine(str, "connect", "207", arrayOfObject);
      if ((isClosed()) || (this.closePending))
        throw new MqttException(32111);
    }
    if (isConnecting())
      throw new MqttException(32110);
    if (isDisconnecting())
      throw new MqttException(32102);
    throw ExceptionHelper.createMqttException(32100);
  }

  public void connectComplete(MqttConnack paramMqttConnack, MqttException paramMqttException)
    throws MqttException
  {
    int i = paramMqttConnack.getReturnCode();
    Object localObject1 = this.conLock;
    if (i == 0);
    try
    {
      this.log.fine(className, "connectComplete", "215");
      this.conState = 0;
      return;
      Logger localLogger = this.log;
      String str = className;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = new Integer(i);
      localLogger.fine(str, "connectComplete", "204", arrayOfObject);
      throw paramMqttException;
    }
    finally
    {
    }
  }

  protected void deliveryComplete(MqttPublish paramMqttPublish)
    throws MqttPersistenceException
  {
    this.clientState.deliveryComplete(paramMqttPublish);
  }

  public void disconnect(MqttDisconnect paramMqttDisconnect, long paramLong, MqttToken paramMqttToken)
    throws MqttException
  {
    synchronized (this.conLock)
    {
      if (isClosed())
      {
        this.log.fine(className, "disconnect", "223");
        throw ExceptionHelper.createMqttException(32111);
      }
    }
    if (isDisconnected())
    {
      this.log.fine(className, "disconnect", "211");
      throw ExceptionHelper.createMqttException(32101);
    }
    if (isDisconnecting())
    {
      this.log.fine(className, "disconnect", "219");
      throw ExceptionHelper.createMqttException(32102);
    }
    if (Thread.currentThread() == this.callback.getThread())
    {
      this.log.fine(className, "disconnect", "210");
      throw ExceptionHelper.createMqttException(32107);
    }
    this.log.fine(className, "disconnect", "218");
    this.conState = 2;
    new DisconnectBG(paramMqttDisconnect, paramLong, paramMqttToken).start();
  }

  public IMqttAsyncClient getClient()
  {
    return this.client;
  }

  public ClientState getClientState()
  {
    return this.clientState;
  }

  public MqttConnectOptions getConOptions()
  {
    return this.conOptions;
  }

  public Properties getDebug()
  {
    Properties localProperties = new Properties();
    localProperties.put("conState", new Integer(this.conState));
    localProperties.put("serverURI", getClient().getServerURI());
    localProperties.put("callback", this.callback);
    localProperties.put("stoppingComms", new Boolean(this.stoppingComms));
    return localProperties;
  }

  public long getKeepAlive()
  {
    return this.clientState.getKeepAlive();
  }

  public int getNetworkModuleIndex()
  {
    return this.networkModuleIndex;
  }

  public NetworkModule[] getNetworkModules()
  {
    return this.networkModules;
  }

  public MqttDeliveryToken[] getPendingDeliveryTokens()
  {
    return this.tokenStore.getOutstandingDelTokens();
  }

  protected MqttTopic getTopic(String paramString)
  {
    return new MqttTopic(paramString, this);
  }

  void internalSend(MqttWireMessage paramMqttWireMessage, MqttToken paramMqttToken)
    throws MqttException
  {
    Logger localLogger1 = this.log;
    String str1 = className;
    Object[] arrayOfObject1 = new Object[3];
    arrayOfObject1[0] = paramMqttWireMessage.getKey();
    arrayOfObject1[1] = paramMqttWireMessage;
    arrayOfObject1[2] = paramMqttToken;
    localLogger1.fine(str1, "internalSend", "200", arrayOfObject1);
    if (paramMqttToken.getClient() == null)
      paramMqttToken.internalTok.setClient(getClient());
    try
    {
      this.clientState.send(paramMqttWireMessage, paramMqttToken);
      return;
      Logger localLogger2 = this.log;
      String str2 = className;
      Object[] arrayOfObject2 = new Object[3];
      arrayOfObject2[0] = paramMqttWireMessage.getKey();
      arrayOfObject2[1] = paramMqttWireMessage;
      arrayOfObject2[2] = paramMqttToken;
      localLogger2.fine(str2, "internalSend", "213", arrayOfObject2);
      throw new MqttException(32201);
    }
    catch (MqttException localMqttException)
    {
      if ((paramMqttWireMessage instanceof MqttPublish))
        this.clientState.undo((MqttPublish)paramMqttWireMessage);
      throw localMqttException;
    }
  }

  public boolean isClosed()
  {
    return this.conState == 4;
  }

  public boolean isConnected()
  {
    return this.conState == 0;
  }

  public boolean isConnecting()
  {
    return this.conState == 1;
  }

  public boolean isDisconnected()
  {
    return this.conState == 3;
  }

  public boolean isDisconnecting()
  {
    return this.conState == 2;
  }

  public void sendNoWait(MqttWireMessage paramMqttWireMessage, MqttToken paramMqttToken)
    throws MqttException
  {
    if ((isConnected()) || ((!isConnected()) && ((paramMqttWireMessage instanceof MqttConnect))) || ((isDisconnecting()) && ((paramMqttWireMessage instanceof MqttDisconnect))))
    {
      internalSend(paramMqttWireMessage, paramMqttToken);
      return;
    }
    this.log.fine(className, "sendNoWait", "208");
    throw ExceptionHelper.createMqttException(32104);
  }

  public void setCallback(MqttCallback paramMqttCallback)
  {
    this.callback.setCallback(paramMqttCallback);
  }

  public void setNetworkModuleIndex(int paramInt)
  {
    this.networkModuleIndex = paramInt;
  }

  public void setNetworkModules(NetworkModule[] paramArrayOfNetworkModule)
  {
    this.networkModules = paramArrayOfNetworkModule;
  }

  public void shutdownConnection(MqttToken paramMqttToken, MqttException paramMqttException)
  {
    int i = 1;
    while (true)
    {
      synchronized (this.conLock)
      {
        if ((this.stoppingComms) || (this.closePending))
          return;
        this.stoppingComms = true;
        this.log.fine(className, "shutdownConnection", "216");
        if (isConnected())
          break label397;
        if (isDisconnecting())
        {
          break label397;
          this.conState = 2;
          if ((paramMqttToken != null) && (!paramMqttToken.isComplete()))
            paramMqttToken.internalTok.setException(paramMqttException);
          if (this.callback != null)
            this.callback.stop();
        }
      }
      try
      {
        if (this.networkModules != null)
        {
          NetworkModule localNetworkModule = this.networkModules[this.networkModuleIndex];
          if (localNetworkModule != null)
            localNetworkModule.stop();
        }
        label140: if (this.receiver != null)
          this.receiver.stop();
        this.tokenStore.quiesce(new MqttException(32102));
        MqttToken localMqttToken = handleOldTokens(paramMqttToken, paramMqttException);
        try
        {
          this.clientState.disconnected(paramMqttException);
          label187: if (this.sender != null)
            this.sender.stop();
          try
          {
            if (this.persistence != null)
              this.persistence.close();
            label217: int k;
            synchronized (this.conLock)
            {
              this.log.fine(className, "shutdownConnection", "217");
              this.conState = 3;
              this.stoppingComms = false;
              if (localMqttToken != null)
              {
                k = i;
                label265: if (this.callback == null)
                  break label372;
                if ((k & i) != 0)
                  this.callback.asyncOperationComplete(localMqttToken);
                if ((j != 0) && (this.callback != null))
                  this.callback.connectionLost(paramMqttException);
                synchronized (this.conLock)
                {
                  boolean bool = this.closePending;
                  if (!bool);
                }
              }
            }
            try
            {
              close();
              label332: return;
              localObject6 = finally;
              throw localObject6;
              j = 0;
              continue;
              localObject2 = finally;
              throw localObject2;
              localObject4 = finally;
              throw localObject4;
              k = 0;
              break label265;
              label372: i = 0;
            }
            catch (Exception localException4)
            {
              break label332;
            }
          }
          catch (Exception localException3)
          {
            break label217;
          }
        }
        catch (Exception localException2)
        {
          break label187;
        }
      }
      catch (Exception localException1)
      {
        break label140;
      }
      label397: int j = i;
    }
  }

  private class ConnectBG
    implements Runnable
  {
    Thread cBg = null;
    ClientComms clientComms = null;
    MqttConnect conPacket;
    MqttToken conToken;

    ConnectBG(ClientComms paramMqttToken, MqttToken paramMqttConnect, MqttConnect arg4)
    {
      this.clientComms = paramMqttToken;
      this.conToken = paramMqttConnect;
      Object localObject;
      this.conPacket = localObject;
      this.cBg = new Thread(this, "MQTT Con: " + ClientComms.this.getClient().getClientId());
    }

    public void run()
    {
      Object localObject = null;
      ClientComms.this.log.fine(ClientComms.className, "connectBG:run", "220");
      try
      {
        MqttDeliveryToken[] arrayOfMqttDeliveryToken = ClientComms.this.tokenStore.getOutstandingDelTokens();
        for (int i = 0; i < arrayOfMqttDeliveryToken.length; i++)
          arrayOfMqttDeliveryToken[i].internalTok.setException(null);
        ClientComms.this.tokenStore.saveToken(this.conToken, this.conPacket);
        NetworkModule localNetworkModule = ClientComms.this.networkModules[ClientComms.this.networkModuleIndex];
        localNetworkModule.start();
        ClientComms.this.receiver = new CommsReceiver(this.clientComms, ClientComms.this.clientState, ClientComms.this.tokenStore, localNetworkModule.getInputStream());
        ClientComms.this.receiver.start("MQTT Rec: " + ClientComms.this.getClient().getClientId());
        ClientComms.this.sender = new CommsSender(this.clientComms, ClientComms.this.clientState, ClientComms.this.tokenStore, localNetworkModule.getOutputStream());
        ClientComms.this.sender.start("MQTT Snd: " + ClientComms.this.getClient().getClientId());
        ClientComms.this.callback.start("MQTT Call: " + ClientComms.this.getClient().getClientId());
        ClientComms.this.internalSend(this.conPacket, this.conToken);
        if (localObject != null)
          ClientComms.this.shutdownConnection(this.conToken, (MqttException)localObject);
        return;
      }
      catch (MqttException localMqttException)
      {
        while (true)
        {
          ClientComms.this.log.fine(ClientComms.className, "connectBG:run", "212", null, localMqttException);
          localObject = localMqttException;
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          ClientComms.this.log.fine(ClientComms.className, "connectBG:run", "209", null, localException);
          localObject = ExceptionHelper.createMqttException(localException);
        }
      }
    }

    void start()
    {
      this.cBg.start();
    }
  }

  private class DisconnectBG
    implements Runnable
  {
    Thread dBg = null;
    MqttDisconnect disconnect;
    long quiesceTimeout;
    MqttToken token;

    DisconnectBG(MqttDisconnect paramLong, long arg3, MqttToken arg5)
    {
      this.disconnect = paramLong;
      this.quiesceTimeout = ???;
      Object localObject;
      this.token = localObject;
    }

    // ERROR //
    public void run()
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 20	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:this$0	Lorg/eclipse/paho/client/mqttv3/internal/ClientComms;
      //   4: getfield 40	org/eclipse/paho/client/mqttv3/internal/ClientComms:log	Lorg/eclipse/paho/client/mqttv3/logging/Logger;
      //   7: getstatic 44	org/eclipse/paho/client/mqttv3/internal/ClientComms:className	Ljava/lang/String;
      //   10: ldc 46
      //   12: ldc 48
      //   14: invokeinterface 54 4 0
      //   19: aload_0
      //   20: getfield 20	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:this$0	Lorg/eclipse/paho/client/mqttv3/internal/ClientComms;
      //   23: getfield 58	org/eclipse/paho/client/mqttv3/internal/ClientComms:clientState	Lorg/eclipse/paho/client/mqttv3/internal/ClientState;
      //   26: aload_0
      //   27: getfield 29	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:quiesceTimeout	J
      //   30: invokevirtual 64	org/eclipse/paho/client/mqttv3/internal/ClientState:quiesce	(J)V
      //   33: aload_0
      //   34: getfield 20	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:this$0	Lorg/eclipse/paho/client/mqttv3/internal/ClientComms;
      //   37: aload_0
      //   38: getfield 27	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:disconnect	Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttDisconnect;
      //   41: aload_0
      //   42: getfield 31	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:token	Lorg/eclipse/paho/client/mqttv3/MqttToken;
      //   45: invokevirtual 68	org/eclipse/paho/client/mqttv3/internal/ClientComms:internalSend	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;Lorg/eclipse/paho/client/mqttv3/MqttToken;)V
      //   48: aload_0
      //   49: getfield 31	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:token	Lorg/eclipse/paho/client/mqttv3/MqttToken;
      //   52: getfield 74	org/eclipse/paho/client/mqttv3/MqttToken:internalTok	Lorg/eclipse/paho/client/mqttv3/internal/Token;
      //   55: invokevirtual 79	org/eclipse/paho/client/mqttv3/internal/Token:waitUntilSent	()V
      //   58: aload_0
      //   59: getfield 31	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:token	Lorg/eclipse/paho/client/mqttv3/MqttToken;
      //   62: getfield 74	org/eclipse/paho/client/mqttv3/MqttToken:internalTok	Lorg/eclipse/paho/client/mqttv3/internal/Token;
      //   65: aconst_null
      //   66: aconst_null
      //   67: invokevirtual 83	org/eclipse/paho/client/mqttv3/internal/Token:markComplete	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;Lorg/eclipse/paho/client/mqttv3/MqttException;)V
      //   70: aload_0
      //   71: getfield 20	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:this$0	Lorg/eclipse/paho/client/mqttv3/internal/ClientComms;
      //   74: aload_0
      //   75: getfield 31	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:token	Lorg/eclipse/paho/client/mqttv3/MqttToken;
      //   78: aconst_null
      //   79: invokevirtual 87	org/eclipse/paho/client/mqttv3/internal/ClientComms:shutdownConnection	(Lorg/eclipse/paho/client/mqttv3/MqttToken;Lorg/eclipse/paho/client/mqttv3/MqttException;)V
      //   82: return
      //   83: astore_2
      //   84: aload_0
      //   85: getfield 31	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:token	Lorg/eclipse/paho/client/mqttv3/MqttToken;
      //   88: getfield 74	org/eclipse/paho/client/mqttv3/MqttToken:internalTok	Lorg/eclipse/paho/client/mqttv3/internal/Token;
      //   91: aconst_null
      //   92: aconst_null
      //   93: invokevirtual 83	org/eclipse/paho/client/mqttv3/internal/Token:markComplete	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;Lorg/eclipse/paho/client/mqttv3/MqttException;)V
      //   96: aload_0
      //   97: getfield 20	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:this$0	Lorg/eclipse/paho/client/mqttv3/internal/ClientComms;
      //   100: aload_0
      //   101: getfield 31	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:token	Lorg/eclipse/paho/client/mqttv3/MqttToken;
      //   104: aconst_null
      //   105: invokevirtual 87	org/eclipse/paho/client/mqttv3/internal/ClientComms:shutdownConnection	(Lorg/eclipse/paho/client/mqttv3/MqttToken;Lorg/eclipse/paho/client/mqttv3/MqttException;)V
      //   108: return
      //   109: astore_1
      //   110: aload_0
      //   111: getfield 31	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:token	Lorg/eclipse/paho/client/mqttv3/MqttToken;
      //   114: getfield 74	org/eclipse/paho/client/mqttv3/MqttToken:internalTok	Lorg/eclipse/paho/client/mqttv3/internal/Token;
      //   117: aconst_null
      //   118: aconst_null
      //   119: invokevirtual 83	org/eclipse/paho/client/mqttv3/internal/Token:markComplete	(Lorg/eclipse/paho/client/mqttv3/internal/wire/MqttWireMessage;Lorg/eclipse/paho/client/mqttv3/MqttException;)V
      //   122: aload_0
      //   123: getfield 20	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:this$0	Lorg/eclipse/paho/client/mqttv3/internal/ClientComms;
      //   126: aload_0
      //   127: getfield 31	org/eclipse/paho/client/mqttv3/internal/ClientComms$DisconnectBG:token	Lorg/eclipse/paho/client/mqttv3/MqttToken;
      //   130: aconst_null
      //   131: invokevirtual 87	org/eclipse/paho/client/mqttv3/internal/ClientComms:shutdownConnection	(Lorg/eclipse/paho/client/mqttv3/MqttToken;Lorg/eclipse/paho/client/mqttv3/MqttException;)V
      //   134: aload_1
      //   135: athrow
      //
      // Exception table:
      //   from	to	target	type
      //   33	58	83	org/eclipse/paho/client/mqttv3/MqttException
      //   33	58	109	finally
    }

    void start()
    {
      this.dBg = new Thread(this, "MQTT Disc: " + ClientComms.this.getClient().getClientId());
      this.dBg.start();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.ClientComms
 * JD-Core Version:    0.6.2
 */