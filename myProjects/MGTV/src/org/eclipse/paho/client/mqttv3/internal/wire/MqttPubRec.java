package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttPubRec extends MqttAck
{
  public MqttPubRec(byte paramByte, byte[] paramArrayOfByte)
    throws IOException
  {
    super((byte)5);
    DataInputStream localDataInputStream = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    this.msgId = localDataInputStream.readUnsignedShort();
    localDataInputStream.close();
  }

  public MqttPubRec(MqttPublish paramMqttPublish)
  {
    super((byte)5);
    this.msgId = paramMqttPublish.getMessageId();
  }

  protected byte[] getVariableHeader()
    throws MqttException
  {
    return encodeMessageId();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MqttPubRec
 * JD-Core Version:    0.6.2
 */