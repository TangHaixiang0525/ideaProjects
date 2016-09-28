package org.eclipse.paho.client.mqttv3;

import java.util.Hashtable;
import java.util.Properties;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.internal.ConnectActionListener;
import org.eclipse.paho.client.mqttv3.internal.ExceptionHelper;
import org.eclipse.paho.client.mqttv3.internal.LocalNetworkModule;
import org.eclipse.paho.client.mqttv3.internal.NetworkModule;
import org.eclipse.paho.client.mqttv3.internal.SSLNetworkModule;
import org.eclipse.paho.client.mqttv3.internal.TCPNetworkModule;
import org.eclipse.paho.client.mqttv3.internal.Token;
import org.eclipse.paho.client.mqttv3.internal.security.SSLSocketFactoryFactory;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttDisconnect;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttSubscribe;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttUnsubscribe;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.eclipse.paho.client.mqttv3.util.Debug;

public class MqttAsyncClient
  implements IMqttAsyncClient
{
  static final String className = MqttAsyncClient.class.getName();
  private String clientId;
  protected ClientComms comms;
  public Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", className);
  private MqttClientPersistence persistence;
  private String serverURI;
  private Hashtable topics;

  public MqttAsyncClient(String paramString1, String paramString2)
    throws MqttException
  {
    this(paramString1, paramString2, new MqttDefaultFilePersistence());
  }

  public MqttAsyncClient(String paramString1, String paramString2, MqttClientPersistence paramMqttClientPersistence)
    throws MqttException
  {
    this.log.setResourceName(paramString2);
    if ((paramString2 == null) || (paramString2.length() == 0))
      throw new IllegalArgumentException("Null or zero length clientId");
    int i = 0;
    for (int j = 0; j < -1 + paramString2.length(); j++)
    {
      if (Character_isHighSurrogate(paramString2.charAt(j)))
        j++;
      i++;
    }
    if (i > 23)
      throw new IllegalArgumentException("ClientId longer than 23 characters");
    MqttConnectOptions.validateURI(paramString1);
    this.serverURI = paramString1;
    this.clientId = paramString2;
    this.persistence = paramMqttClientPersistence;
    if (this.persistence == null)
      this.persistence = new MemoryPersistence();
    this.log.fine(className, "MqttAsyncClient", "101", new Object[] { paramString2, paramString1, paramMqttClientPersistence });
    this.persistence.open(paramString2, paramString1);
    this.comms = new ClientComms(this, this.persistence);
    this.persistence.close();
    this.topics = new Hashtable();
  }

  protected static boolean Character_isHighSurrogate(char paramChar)
  {
    return (paramChar >= 55296) && (paramChar <= 56319);
  }

  private NetworkModule createNetworkModule(String paramString, MqttConnectOptions paramMqttConnectOptions)
    throws MqttException, MqttSecurityException
  {
    this.log.fine(className, "createNetworkModule", "115", new Object[] { paramString });
    Object localObject1 = paramMqttConnectOptions.getSocketFactory();
    Object localObject2;
    switch (MqttConnectOptions.validateURI(paramString))
    {
    default:
      localObject2 = null;
      return localObject2;
    case 0:
      String str3 = paramString.substring(6);
      String str4 = getHostName(str3);
      int j = getPort(str3, 1883);
      if (localObject1 == null)
        localObject1 = SocketFactory.getDefault();
      while (!(localObject1 instanceof SSLSocketFactory))
      {
        TCPNetworkModule localTCPNetworkModule = new TCPNetworkModule((SocketFactory)localObject1, str4, j, this.clientId);
        ((TCPNetworkModule)localTCPNetworkModule).setConnectTimeout(paramMqttConnectOptions.getConnectionTimeout());
        return localTCPNetworkModule;
      }
      throw ExceptionHelper.createMqttException(32105);
    case 1:
      String str1 = paramString.substring(6);
      String str2 = getHostName(str1);
      int i = getPort(str1, 8883);
      SSLSocketFactoryFactory localSSLSocketFactoryFactory;
      if (localObject1 == null)
      {
        localSSLSocketFactoryFactory = new SSLSocketFactoryFactory();
        Properties localProperties = paramMqttConnectOptions.getSSLProperties();
        if (localProperties != null)
          localSSLSocketFactoryFactory.initialize(localProperties, null);
        localObject1 = localSSLSocketFactoryFactory.createSocketFactory(null);
      }
      boolean bool;
      do
      {
        localObject2 = new SSLNetworkModule((SSLSocketFactory)localObject1, str2, i, this.clientId);
        ((SSLNetworkModule)localObject2).setSSLhandshakeTimeout(paramMqttConnectOptions.getConnectionTimeout());
        if (localSSLSocketFactoryFactory == null)
          break;
        String[] arrayOfString = localSSLSocketFactoryFactory.getEnabledCipherSuites(null);
        if (arrayOfString == null)
          break;
        ((SSLNetworkModule)localObject2).setEnabledCiphers(arrayOfString);
        return localObject2;
        bool = localObject1 instanceof SSLSocketFactory;
        localSSLSocketFactoryFactory = null;
      }
      while (bool);
      throw ExceptionHelper.createMqttException(32105);
    case 2:
    }
    return new LocalNetworkModule(paramString.substring(8));
  }

  public static String generateClientId()
  {
    return System.getProperty("user.name") + "." + System.currentTimeMillis();
  }

  private String getHostName(String paramString)
  {
    int i = paramString.lastIndexOf('/');
    int j = paramString.lastIndexOf(':');
    if (j == -1)
      j = paramString.length();
    return paramString.substring(i + 1, j);
  }

  private int getPort(String paramString, int paramInt)
  {
    int i = paramString.lastIndexOf(':');
    if (i == -1)
      return paramInt;
    return Integer.valueOf(paramString.substring(i + 1)).intValue();
  }

  public static void validateTopic(String paramString)
  {
    if ((paramString.indexOf('#') == -1) && (paramString.indexOf('+') == -1))
      return;
    throw new IllegalArgumentException();
  }

  public void close()
    throws MqttException
  {
    this.log.fine(className, "close", "113");
    this.comms.close();
    this.log.fine(className, "close", "114");
  }

  public IMqttToken connect()
    throws MqttException, MqttSecurityException
  {
    return connect(null, null);
  }

  public IMqttToken connect(Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException, MqttSecurityException
  {
    return connect(new MqttConnectOptions(), paramObject, paramIMqttActionListener);
  }

  public IMqttToken connect(MqttConnectOptions paramMqttConnectOptions)
    throws MqttException, MqttSecurityException
  {
    return connect(paramMqttConnectOptions, null, null);
  }

  public IMqttToken connect(MqttConnectOptions paramMqttConnectOptions, Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException, MqttSecurityException
  {
    if (this.comms.isConnected())
      throw ExceptionHelper.createMqttException(32100);
    if (this.comms.isConnecting())
      throw new MqttException(32110);
    if (this.comms.isDisconnecting())
      throw new MqttException(32102);
    if (this.comms.isClosed())
      throw new MqttException(32111);
    Logger localLogger = this.log;
    String str1 = className;
    Object[] arrayOfObject = new Object[8];
    arrayOfObject[0] = new Boolean(paramMqttConnectOptions.isCleanSession());
    arrayOfObject[1] = new Integer(paramMqttConnectOptions.getConnectionTimeout());
    arrayOfObject[2] = new Integer(paramMqttConnectOptions.getKeepAliveInterval());
    arrayOfObject[3] = paramMqttConnectOptions.getUserName();
    String str2;
    if (paramMqttConnectOptions.getPassword() == null)
    {
      str2 = "[null]";
      arrayOfObject[4] = str2;
      if (paramMqttConnectOptions.getWillMessage() != null)
        break label305;
    }
    label305: for (String str3 = "[null]"; ; str3 = "[notnull]")
    {
      arrayOfObject[5] = str3;
      arrayOfObject[6] = paramObject;
      arrayOfObject[7] = paramIMqttActionListener;
      localLogger.fine(str1, "connect", "103", arrayOfObject);
      this.comms.setNetworkModules(createNetworkModules(this.serverURI, paramMqttConnectOptions));
      MqttToken localMqttToken = new MqttToken(getClientId());
      ConnectActionListener localConnectActionListener = new ConnectActionListener(this, this.persistence, this.comms, paramMqttConnectOptions, localMqttToken, paramObject, paramIMqttActionListener);
      localMqttToken.setActionCallback(localConnectActionListener);
      localMqttToken.setUserContext(this);
      this.comms.setNetworkModuleIndex(0);
      localConnectActionListener.connect();
      return localMqttToken;
      str2 = "[notnull]";
      break;
    }
  }

  protected NetworkModule[] createNetworkModules(String paramString, MqttConnectOptions paramMqttConnectOptions)
    throws MqttException, MqttSecurityException
  {
    this.log.fine(className, "createNetworkModules", "116", new Object[] { paramString });
    String[] arrayOfString1 = paramMqttConnectOptions.getServerURIs();
    String[] arrayOfString2;
    if (arrayOfString1 == null)
    {
      arrayOfString2 = new String[1];
      arrayOfString2[0] = paramString;
    }
    NetworkModule[] arrayOfNetworkModule;
    while (true)
    {
      arrayOfNetworkModule = new NetworkModule[arrayOfString2.length];
      for (int i = 0; i < arrayOfString2.length; i++)
        arrayOfNetworkModule[i] = createNetworkModule(arrayOfString2[i], paramMqttConnectOptions);
      if (arrayOfString1.length == 0)
        arrayOfString2 = new String[] { paramString };
      else
        arrayOfString2 = arrayOfString1;
    }
    this.log.fine(className, "createNetworkModules", "108");
    return arrayOfNetworkModule;
  }

  public IMqttToken disconnect()
    throws MqttException
  {
    return disconnect(null, null);
  }

  public IMqttToken disconnect(long paramLong)
    throws MqttException
  {
    return disconnect(paramLong, null, null);
  }

  public IMqttToken disconnect(long paramLong, Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException
  {
    Logger localLogger = this.log;
    String str = className;
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = new Long(paramLong);
    arrayOfObject[1] = paramObject;
    arrayOfObject[2] = paramIMqttActionListener;
    localLogger.fine(str, "disconnect", "104", arrayOfObject);
    MqttToken localMqttToken = new MqttToken(getClientId());
    localMqttToken.setActionCallback(paramIMqttActionListener);
    localMqttToken.setUserContext(paramObject);
    MqttDisconnect localMqttDisconnect = new MqttDisconnect();
    try
    {
      this.comms.disconnect(localMqttDisconnect, paramLong, localMqttToken);
      this.log.fine(className, "disconnect", "108");
      return localMqttToken;
    }
    catch (MqttException localMqttException)
    {
      this.log.fine(className, "disconnect", "105", null, localMqttException);
      throw localMqttException;
    }
  }

  public IMqttToken disconnect(Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException
  {
    return disconnect(30000L, paramObject, paramIMqttActionListener);
  }

  public String getClientId()
  {
    return this.clientId;
  }

  public Debug getDebug()
  {
    return new Debug(this.clientId, this.comms);
  }

  public IMqttDeliveryToken[] getPendingDeliveryTokens()
  {
    return this.comms.getPendingDeliveryTokens();
  }

  public String getServerURI()
  {
    return this.serverURI;
  }

  protected MqttTopic getTopic(String paramString)
  {
    validateTopic(paramString);
    MqttTopic localMqttTopic = (MqttTopic)this.topics.get(paramString);
    if (localMqttTopic == null)
    {
      localMqttTopic = new MqttTopic(paramString, this.comms);
      this.topics.put(paramString, localMqttTopic);
    }
    return localMqttTopic;
  }

  public boolean isConnected()
  {
    return this.comms.isConnected();
  }

  public IMqttDeliveryToken publish(String paramString, MqttMessage paramMqttMessage)
    throws MqttException, MqttPersistenceException
  {
    return publish(paramString, paramMqttMessage, null, null);
  }

  public IMqttDeliveryToken publish(String paramString, MqttMessage paramMqttMessage, Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException, MqttPersistenceException
  {
    this.log.fine(className, "publish", "111", new Object[] { paramString, paramObject, paramIMqttActionListener });
    validateTopic(paramString);
    MqttDeliveryToken localMqttDeliveryToken = new MqttDeliveryToken(getClientId());
    localMqttDeliveryToken.setActionCallback(paramIMqttActionListener);
    localMqttDeliveryToken.setUserContext(paramObject);
    localMqttDeliveryToken.setMessage(paramMqttMessage);
    localMqttDeliveryToken.internalTok.setTopics(new String[] { paramString });
    MqttPublish localMqttPublish = new MqttPublish(paramString, paramMqttMessage);
    this.comms.sendNoWait(localMqttPublish, localMqttDeliveryToken);
    this.log.fine(className, "publish", "112");
    return localMqttDeliveryToken;
  }

  public IMqttDeliveryToken publish(String paramString, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
    throws MqttException, MqttPersistenceException
  {
    return publish(paramString, paramArrayOfByte, paramInt, paramBoolean, null, null);
  }

  public IMqttDeliveryToken publish(String paramString, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean, Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException, MqttPersistenceException
  {
    MqttMessage localMqttMessage = new MqttMessage(paramArrayOfByte);
    localMqttMessage.setQos(paramInt);
    localMqttMessage.setRetained(paramBoolean);
    return publish(paramString, localMqttMessage, paramObject, paramIMqttActionListener);
  }

  public void setCallback(MqttCallback paramMqttCallback)
  {
    this.comms.setCallback(paramMqttCallback);
  }

  public IMqttToken subscribe(String paramString, int paramInt)
    throws MqttException
  {
    return subscribe(new String[] { paramString }, new int[] { paramInt }, null, null);
  }

  public IMqttToken subscribe(String paramString, int paramInt, Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException
  {
    return subscribe(new String[] { paramString }, new int[] { paramInt }, paramObject, paramIMqttActionListener);
  }

  public IMqttToken subscribe(String[] paramArrayOfString, int[] paramArrayOfInt)
    throws MqttException
  {
    return subscribe(paramArrayOfString, paramArrayOfInt, null, null);
  }

  public IMqttToken subscribe(String[] paramArrayOfString, int[] paramArrayOfInt, Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException
  {
    if (paramArrayOfString.length != paramArrayOfInt.length)
      throw new IllegalArgumentException();
    String str = "";
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      if (i > 0)
        str = str + ", ";
      str = str + paramArrayOfString[i] + ":" + paramArrayOfInt[i];
    }
    this.log.fine(className, "subscribe", "106", new Object[] { str, paramObject, paramIMqttActionListener });
    MqttToken localMqttToken = new MqttToken(getClientId());
    localMqttToken.setActionCallback(paramIMqttActionListener);
    localMqttToken.setUserContext(paramObject);
    localMqttToken.internalTok.setTopics(paramArrayOfString);
    MqttSubscribe localMqttSubscribe = new MqttSubscribe(paramArrayOfString, paramArrayOfInt);
    this.comms.sendNoWait(localMqttSubscribe, localMqttToken);
    this.log.fine(className, "subscribe", "109");
    return localMqttToken;
  }

  public IMqttToken unsubscribe(String paramString)
    throws MqttException
  {
    return unsubscribe(new String[] { paramString }, null, null);
  }

  public IMqttToken unsubscribe(String paramString, Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException
  {
    return unsubscribe(new String[] { paramString }, paramObject, paramIMqttActionListener);
  }

  public IMqttToken unsubscribe(String[] paramArrayOfString)
    throws MqttException
  {
    return unsubscribe(paramArrayOfString, null, null);
  }

  public IMqttToken unsubscribe(String[] paramArrayOfString, Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException
  {
    String str = "";
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      if (i > 0)
        str = str + ", ";
      str = str + paramArrayOfString[i];
    }
    this.log.fine(className, "unsubscribe", "107", new Object[] { str, paramObject, paramIMqttActionListener });
    MqttToken localMqttToken = new MqttToken(getClientId());
    localMqttToken.setActionCallback(paramIMqttActionListener);
    localMqttToken.setUserContext(paramObject);
    localMqttToken.internalTok.setTopics(paramArrayOfString);
    MqttUnsubscribe localMqttUnsubscribe = new MqttUnsubscribe(paramArrayOfString);
    this.comms.sendNoWait(localMqttUnsubscribe, localMqttToken);
    this.log.fine(className, "unsubscribe", "110");
    return localMqttToken;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.MqttAsyncClient
 * JD-Core Version:    0.6.2
 */