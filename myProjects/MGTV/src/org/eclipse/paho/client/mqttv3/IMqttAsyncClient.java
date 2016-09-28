package org.eclipse.paho.client.mqttv3;

public abstract interface IMqttAsyncClient
{
  public abstract void close()
    throws MqttException;

  public abstract IMqttToken connect()
    throws MqttException, MqttSecurityException;

  public abstract IMqttToken connect(Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException, MqttSecurityException;

  public abstract IMqttToken connect(MqttConnectOptions paramMqttConnectOptions)
    throws MqttException, MqttSecurityException;

  public abstract IMqttToken connect(MqttConnectOptions paramMqttConnectOptions, Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException, MqttSecurityException;

  public abstract IMqttToken disconnect()
    throws MqttException;

  public abstract IMqttToken disconnect(long paramLong)
    throws MqttException;

  public abstract IMqttToken disconnect(long paramLong, Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException;

  public abstract IMqttToken disconnect(Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException;

  public abstract String getClientId();

  public abstract IMqttDeliveryToken[] getPendingDeliveryTokens();

  public abstract String getServerURI();

  public abstract boolean isConnected();

  public abstract IMqttDeliveryToken publish(String paramString, MqttMessage paramMqttMessage)
    throws MqttException, MqttPersistenceException;

  public abstract IMqttDeliveryToken publish(String paramString, MqttMessage paramMqttMessage, Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException, MqttPersistenceException;

  public abstract IMqttDeliveryToken publish(String paramString, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
    throws MqttException, MqttPersistenceException;

  public abstract IMqttDeliveryToken publish(String paramString, byte[] paramArrayOfByte, int paramInt, boolean paramBoolean, Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException, MqttPersistenceException;

  public abstract void setCallback(MqttCallback paramMqttCallback);

  public abstract IMqttToken subscribe(String paramString, int paramInt)
    throws MqttException;

  public abstract IMqttToken subscribe(String paramString, int paramInt, Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException;

  public abstract IMqttToken subscribe(String[] paramArrayOfString, int[] paramArrayOfInt)
    throws MqttException;

  public abstract IMqttToken subscribe(String[] paramArrayOfString, int[] paramArrayOfInt, Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException;

  public abstract IMqttToken unsubscribe(String paramString)
    throws MqttException;

  public abstract IMqttToken unsubscribe(String paramString, Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException;

  public abstract IMqttToken unsubscribe(String[] paramArrayOfString)
    throws MqttException;

  public abstract IMqttToken unsubscribe(String[] paramArrayOfString, Object paramObject, IMqttActionListener paramIMqttActionListener)
    throws MqttException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.IMqttAsyncClient
 * JD-Core Version:    0.6.2
 */