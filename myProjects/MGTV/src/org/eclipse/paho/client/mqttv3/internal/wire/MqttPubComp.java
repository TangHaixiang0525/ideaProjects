package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttPubComp extends MqttAck
{
  public MqttPubComp(byte paramByte, byte[] paramArrayOfByte)
    throws IOException
  {
    super((byte)7);
    DataInputStream localDataInputStream = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    this.msgId = localDataInputStream.readUnsignedShort();
    localDataInputStream.close();
  }

  public MqttPubComp(int paramInt)
  {
    super((byte)7);
    this.msgId = paramInt;
  }

  public MqttPubComp(MqttPublish paramMqttPublish)
  {
    super((byte)7);
    this.msgId = paramMqttPublish.getMessageId();
  }

  protected byte[] getVariableHeader()
    throws MqttException
  {
    return encodeMessageId();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MqttPubComp
 * JD-Core Version:    0.6.2
 */