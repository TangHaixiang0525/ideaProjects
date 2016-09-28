package org.eclipse.paho.client.mqttv3;

import org.eclipse.paho.client.mqttv3.internal.Token;

public class MqttDeliveryToken extends MqttToken
  implements IMqttDeliveryToken
{
  public MqttDeliveryToken()
  {
  }

  public MqttDeliveryToken(String paramString)
  {
    super(paramString);
  }

  public MqttMessage getMessage()
    throws MqttException
  {
    return this.internalTok.getMessage();
  }

  protected void setMessage(MqttMessage paramMqttMessage)
  {
    this.internalTok.setMessage(paramMqttMessage);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.MqttDeliveryToken
 * JD-Core Version:    0.6.2
 */