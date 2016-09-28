package org.eclipse.paho.client.mqttv3;

import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPingReq;

public class MqttPingSender extends MqttClient
{
  public MqttPingSender(String paramString1, String paramString2)
    throws MqttException
  {
    super(paramString1, paramString2);
  }

  public MqttPingSender(String paramString1, String paramString2, MqttClientPersistence paramMqttClientPersistence)
    throws MqttException
  {
    super(paramString1, paramString2, paramMqttClientPersistence);
  }

  public void ping()
    throws MqttException
  {
    MqttDeliveryToken localMqttDeliveryToken = new MqttDeliveryToken(getClientId());
    MqttPingReq localMqttPingReq = new MqttPingReq();
    this.aClient.comms.sendNoWait(localMqttPingReq, localMqttDeliveryToken);
  }

  public void ping(MqttClient paramMqttClient)
    throws MqttException
  {
    MqttDeliveryToken localMqttDeliveryToken = new MqttDeliveryToken(paramMqttClient.getClientId());
    MqttPingReq localMqttPingReq = new MqttPingReq();
    paramMqttClient.aClient.comms.sendNoWait(localMqttPingReq, localMqttDeliveryToken);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.MqttPingSender
 * JD-Core Version:    0.6.2
 */