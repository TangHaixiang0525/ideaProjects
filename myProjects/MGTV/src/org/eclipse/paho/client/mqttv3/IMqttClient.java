package org.eclipse.paho.client.mqttv3;

public abstract interface IMqttClient
{
  public abstract void close()
    throws MqttException;

  public abstract void connect()
    throws MqttSecurityException, MqttException;

  public abstract void connect(MqttConnectOptions paramMqttConnectOptions)
    throws MqttSecurityException, MqttException;

  public abstract void disconnect()
    throws MqttException;

  public abstract void disconnect(long paramLong)
    throws MqttException;

  public abstract String getClientId();

  public abstract IMqttDeliveryToken[] getPendingDeliveryTokens();

  public abstract String getServerURI();

  public abstract MqttTopic getTopic(String paramString);

  public abstract boolean isConnected();

  public abstract void publish(String paramString, MqttMessage paramMqttMessage)
    throws MqttException, MqttPersistenceException;

  public abstract void publish(String paramString, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
    throws MqttException, MqttPersistenceException;

  public abstract void setCallback(MqttCallback paramMqttCallback);

  public abstract void subscribe(String paramString)
    throws MqttException, MqttSecurityException;

  public abstract void subscribe(String paramString, int paramInt)
    throws MqttException;

  public abstract void subscribe(String[] paramArrayOfString)
    throws MqttException;

  public abstract void subscribe(String[] paramArrayOfString, int[] paramArrayOfInt)
    throws MqttException;

  public abstract void unsubscribe(String paramString)
    throws MqttException;

  public abstract void unsubscribe(String[] paramArrayOfString)
    throws MqttException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.IMqttClient
 * JD-Core Version:    0.6.2
 */