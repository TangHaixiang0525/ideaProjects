package org.eclipse.paho.client.mqttv3;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import javax.net.SocketFactory;
import org.eclipse.paho.client.mqttv3.util.Debug;

public class MqttConnectOptions
{
  public static final boolean CLEAN_SESSION_DEFAULT = true;
  public static final int CONNECTION_TIMEOUT_DEFAULT = 30;
  public static final int KEEP_ALIVE_INTERVAL_DEFAULT = 60;
  protected static final int URI_TYPE_LOCAL = 2;
  protected static final int URI_TYPE_SSL = 1;
  protected static final int URI_TYPE_TCP;
  private boolean cleanSession = true;
  private int connectionTimeout = 30;
  private int keepAliveInterval = 60;
  private char[] password;
  private String[] serverURIs = null;
  private SocketFactory socketFactory;
  private Properties sslClientProps = null;
  private String userName;
  private String willDestination = null;
  private MqttMessage willMessage = null;

  protected static int validateURI(String paramString)
  {
    URI localURI;
    try
    {
      localURI = new URI(paramString);
      if (!localURI.getPath().equals(""))
        throw new IllegalArgumentException(paramString);
    }
    catch (URISyntaxException localURISyntaxException)
    {
      throw new IllegalArgumentException(paramString);
    }
    if (localURI.getScheme().equals("tcp"))
      return 0;
    if (localURI.getScheme().equals("ssl"))
      return 1;
    if (localURI.getScheme().equals("local"))
      return 2;
    throw new IllegalArgumentException(paramString);
  }

  private void validateWill(String paramString, Object paramObject)
  {
    if ((paramString == null) || (paramObject == null))
      throw new IllegalArgumentException();
    MqttAsyncClient.validateTopic(paramString);
  }

  public int getConnectionTimeout()
  {
    return this.connectionTimeout;
  }

  public Properties getDebug()
  {
    Properties localProperties = new Properties();
    localProperties.put("CleanSession", new Boolean(isCleanSession()));
    localProperties.put("ConTimeout", new Integer(getConnectionTimeout()));
    localProperties.put("KeepAliveInterval", new Integer(getKeepAliveInterval()));
    String str1;
    String str2;
    if (getUserName() == null)
    {
      str1 = "null";
      localProperties.put("UserName", str1);
      if (getWillDestination() != null)
        break label145;
      str2 = "null";
      label93: localProperties.put("WillDestination", str2);
      if (getSocketFactory() != null)
        break label154;
      localProperties.put("SocketFactory", "null");
    }
    while (true)
    {
      if (getSSLProperties() != null)
        break label168;
      localProperties.put("SSLProperties", "null");
      return localProperties;
      str1 = getUserName();
      break;
      label145: str2 = getWillDestination();
      break label93;
      label154: localProperties.put("SocketFactory", getSocketFactory());
    }
    label168: localProperties.put("SSLProperties", getSSLProperties());
    return localProperties;
  }

  public int getKeepAliveInterval()
  {
    return this.keepAliveInterval;
  }

  public char[] getPassword()
  {
    return this.password;
  }

  public Properties getSSLProperties()
  {
    return this.sslClientProps;
  }

  public String[] getServerURIs()
  {
    return this.serverURIs;
  }

  public SocketFactory getSocketFactory()
  {
    return this.socketFactory;
  }

  public String getUserName()
  {
    return this.userName;
  }

  public String getWillDestination()
  {
    return this.willDestination;
  }

  public MqttMessage getWillMessage()
  {
    return this.willMessage;
  }

  public boolean isCleanSession()
  {
    return this.cleanSession;
  }

  public void setCleanSession(boolean paramBoolean)
  {
    this.cleanSession = paramBoolean;
  }

  public void setConnectionTimeout(int paramInt)
  {
    if (paramInt < 0)
      throw new IllegalArgumentException();
    this.connectionTimeout = paramInt;
  }

  public void setKeepAliveInterval(int paramInt)
    throws IllegalArgumentException
  {
    if (paramInt < 0)
      throw new IllegalArgumentException();
    this.keepAliveInterval = paramInt;
  }

  public void setPassword(char[] paramArrayOfChar)
  {
    this.password = paramArrayOfChar;
  }

  public void setSSLProperties(Properties paramProperties)
  {
    this.sslClientProps = paramProperties;
  }

  public void setServerURIs(String[] paramArrayOfString)
  {
    for (int i = 0; i < paramArrayOfString.length; i++)
      validateURI(paramArrayOfString[i]);
    this.serverURIs = paramArrayOfString;
  }

  public void setSocketFactory(SocketFactory paramSocketFactory)
  {
    this.socketFactory = paramSocketFactory;
  }

  public void setUserName(String paramString)
  {
    if ((paramString != null) && (paramString.trim().equals("")))
      throw new IllegalArgumentException();
    this.userName = paramString;
  }

  protected void setWill(String paramString, MqttMessage paramMqttMessage, int paramInt, boolean paramBoolean)
  {
    this.willDestination = paramString;
    this.willMessage = paramMqttMessage;
    this.willMessage.setQos(paramInt);
    this.willMessage.setRetained(paramBoolean);
    this.willMessage.setMutable(false);
  }

  public void setWill(String paramString, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
  {
    validateWill(paramString, paramArrayOfByte);
    setWill(paramString, new MqttMessage(paramArrayOfByte), paramInt, paramBoolean);
  }

  public void setWill(MqttTopic paramMqttTopic, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
  {
    String str = paramMqttTopic.getName();
    validateWill(str, paramArrayOfByte);
    setWill(str, new MqttMessage(paramArrayOfByte), paramInt, paramBoolean);
  }

  public String toString()
  {
    return Debug.dumpProperties(getDebug(), "Connection options");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.MqttConnectOptions
 * JD-Core Version:    0.6.2
 */