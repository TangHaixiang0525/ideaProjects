package org.eclipse.paho.client.mqttv3;

import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.internal.Token;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;

public class MqttTopic
{
  private ClientComms comms;
  private String name;

  public MqttTopic(String paramString, ClientComms paramClientComms)
  {
    this.comms = paramClientComms;
    this.name = paramString;
  }

  private MqttPublish createPublish(MqttMessage paramMqttMessage)
  {
    return new MqttPublish(getName(), paramMqttMessage);
  }

  public String getName()
  {
    return this.name;
  }

  public MqttDeliveryToken publish(MqttMessage paramMqttMessage)
    throws MqttException, MqttPersistenceException
  {
    MqttDeliveryToken localMqttDeliveryToken = new MqttDeliveryToken(this.comms.getClient().getClientId());
    localMqttDeliveryToken.setMessage(paramMqttMessage);
    this.comms.sendNoWait(createPublish(paramMqttMessage), localMqttDeliveryToken);
    localMqttDeliveryToken.internalTok.waitUntilSent();
    return localMqttDeliveryToken;
  }

  public MqttDeliveryToken publish(byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
    throws MqttException, MqttPersistenceException
  {
    MqttMessage localMqttMessage = new MqttMessage(paramArrayOfByte);
    localMqttMessage.setQos(paramInt);
    localMqttMessage.setRetained(paramBoolean);
    return publish(localMqttMessage);
  }

  public String toString()
  {
    return getName();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.MqttTopic
 * JD-Core Version:    0.6.2
 */