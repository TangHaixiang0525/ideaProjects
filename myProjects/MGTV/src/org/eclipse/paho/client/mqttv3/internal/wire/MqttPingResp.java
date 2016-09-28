package org.eclipse.paho.client.mqttv3.internal.wire;

import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttPingResp extends MqttAck
{
  public MqttPingResp(byte paramByte, byte[] paramArrayOfByte)
  {
    super((byte)13);
  }

  public String getKey()
  {
    return new String("Ping");
  }

  protected byte[] getVariableHeader()
    throws MqttException
  {
    return new byte[0];
  }

  public boolean isMessageIdRequired()
  {
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MqttPingResp
 * JD-Core Version:    0.6.2
 */