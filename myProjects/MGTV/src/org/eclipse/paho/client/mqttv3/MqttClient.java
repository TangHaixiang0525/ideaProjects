package org.eclipse.paho.client.mqttv3;

import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.eclipse.paho.client.mqttv3.util.Debug;

public class MqttClient
  implements IMqttClient
{
  static final String className = MqttClient.class.getName();
  protected MqttAsyncClient aClient = null;
  public Logger log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", className);
  protected long timeToWait = -1L;

  public MqttClient(String paramString1, String paramString2)
    throws MqttException
  {
    this(paramString1, paramString2, new MqttDefaultFilePersistence());
  }

  public MqttClient(String paramString1, String paramString2, MqttClientPersistence paramMqttClientPersistence)
    throws MqttException
  {
    this.aClient = new MqttAsyncClient(paramString1, paramString2, paramMqttClientPersistence);
  }

  public static String generateClientId()
  {
    return MqttAsyncClient.generateClientId();
  }

  public void close()
    throws MqttException
  {
    this.aClient.close();
  }

  public void connect()
    throws MqttSecurityException, MqttException
  {
    connect(new MqttConnectOptions());
  }

  public void connect(MqttConnectOptions paramMqttConnectOptions)
    throws MqttSecurityException, MqttException
  {
    this.aClient.connect(paramMqttConnectOptions, null, null).waitForCompletion(getTimeToWait());
  }

  public void disconnect()
    throws MqttException
  {
    disconnect(30000L);
  }

  public void disconnect(long paramLong)
    throws MqttException
  {
    this.aClient.disconnect(paramLong, null, null).waitForCompletion();
  }

  public String getClientId()
  {
    return this.aClient.getClientId();
  }

  public Debug getDebug()
  {
    return this.aClient.getDebug();
  }

  public IMqttDeliveryToken[] getPendingDeliveryTokens()
  {
    return this.aClient.getPendingDeliveryTokens();
  }

  public String getServerURI()
  {
    return this.aClient.getServerURI();
  }

  public long getTimeToWait()
  {
    return this.timeToWait;
  }

  public MqttTopic getTopic(String paramString)
  {
    return this.aClient.getTopic(paramString);
  }

  public boolean isConnected()
  {
    return this.aClient.isConnected();
  }

  public void publish(String paramString, MqttMessage paramMqttMessage)
    throws MqttException, MqttPersistenceException
  {
    this.aClient.publish(paramString, paramMqttMessage, null, null).waitForCompletion(getTimeToWait());
  }

  public void publish(String paramString, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
    throws MqttException, MqttPersistenceException
  {
    MqttMessage localMqttMessage = new MqttMessage(paramArrayOfByte);
    localMqttMessage.setQos(paramInt);
    localMqttMessage.setRetained(paramBoolean);
    publish(paramString, localMqttMessage);
  }

  public void setCallback(MqttCallback paramMqttCallback)
  {
    this.aClient.setCallback(paramMqttCallback);
  }

  public void setTimeToWait(long paramLong)
    throws IllegalArgumentException
  {
    if (paramLong < -1L)
      throw new IllegalArgumentException();
    this.timeToWait = paramLong;
  }

  public void subscribe(String paramString)
    throws MqttException
  {
    subscribe(new String[] { paramString }, new int[] { 1 });
  }

  public void subscribe(String paramString, int paramInt)
    throws MqttException
  {
    subscribe(new String[] { paramString }, new int[] { paramInt });
  }

  public void subscribe(String[] paramArrayOfString)
    throws MqttException
  {
    int[] arrayOfInt = new int[paramArrayOfString.length];
    for (int i = 0; i < arrayOfInt.length; i++)
      arrayOfInt[i] = 1;
    subscribe(paramArrayOfString, arrayOfInt);
  }

  public void subscribe(String[] paramArrayOfString, int[] paramArrayOfInt)
    throws MqttException
  {
    this.aClient.subscribe(paramArrayOfString, paramArrayOfInt, null, null).waitForCompletion(getTimeToWait());
  }

  public void unsubscribe(String paramString)
    throws MqttException
  {
    unsubscribe(new String[] { paramString });
  }

  public void unsubscribe(String[] paramArrayOfString)
    throws MqttException
  {
    this.aClient.unsubscribe(paramArrayOfString, null, null).waitForCompletion(getTimeToWait());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.MqttClient
 * JD-Core Version:    0.6.2
 */