package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttUnsubAck extends MqttAck
{
  public MqttUnsubAck(byte paramByte, byte[] paramArrayOfByte)
    throws IOException
  {
    super((byte)11);
    DataInputStream localDataInputStream = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    this.msgId = localDataInputStream.readUnsignedShort();
    localDataInputStream.close();
  }

  protected byte[] getVariableHeader()
    throws MqttException
  {
    return new byte[0];
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MqttUnsubAck
 * JD-Core Version:    0.6.2
 */