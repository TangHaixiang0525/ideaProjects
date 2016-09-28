package org.eclipse.paho.client.mqttv3.internal.wire;

public abstract class MqttAck extends MqttWireMessage
{
  public MqttAck(byte paramByte)
  {
    super(paramByte);
  }

  protected byte getMessageInfo()
  {
    return 0;
  }

  public String toString()
  {
    return super.toString() + " msgId " + this.msgId;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MqttAck
 * JD-Core Version:    0.6.2
 */