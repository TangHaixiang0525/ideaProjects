package org.eclipse.paho.client.mqttv3;

public abstract interface MqttCallback
{
  public abstract void connectionLost(Throwable paramThrowable);

  public abstract void deliveryComplete(IMqttDeliveryToken paramIMqttDeliveryToken);

  public abstract void messageArrived(String paramString, MqttMessage paramMqttMessage)
    throws Exception;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.MqttCallback
 * JD-Core Version:    0.6.2
 */