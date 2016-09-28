package org.eclipse.paho.client.mqttv3;

public abstract interface IMqttDeliveryToken extends IMqttToken
{
  public abstract MqttMessage getMessage()
    throws MqttException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
 * JD-Core Version:    0.6.2
 */