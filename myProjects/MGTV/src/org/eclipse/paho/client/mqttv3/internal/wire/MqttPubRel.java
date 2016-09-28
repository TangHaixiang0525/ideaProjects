package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttPubRel extends MqttPersistableWireMessage
{
  public MqttPubRel(byte paramByte, byte[] paramArrayOfByte)
    throws IOException
  {
    super((byte)6);
    DataInputStream localDataInputStream = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    this.msgId = localDataInputStream.readUnsignedShort();
    localDataInputStream.close();
  }

  public MqttPubRel(MqttPubRec paramMqttPubRec)
  {
    super((byte)6);
    setMessageId(paramMqttPubRec.getMessageId());
  }

  protected byte getMessageInfo()
  {
    if (this.duplicate);
    for (int i = 8; ; i = 0)
      return (byte)(i | 0x2);
  }

  protected byte[] getVariableHeader()
    throws MqttException
  {
    return encodeMessageId();
  }

  public String toString()
  {
    return super.toString() + " msgId " + this.msgId;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MqttPubRel
 * JD-Core Version:    0.6.2
 */