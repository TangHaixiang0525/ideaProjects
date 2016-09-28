package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttSuback extends MqttAck
{
  private int[] grantedQos;

  public MqttSuback(byte paramByte, byte[] paramArrayOfByte)
    throws IOException
  {
    super((byte)9);
    DataInputStream localDataInputStream = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    this.msgId = localDataInputStream.readUnsignedShort();
    int i = 0;
    this.grantedQos = new int[-2 + paramArrayOfByte.length];
    for (int j = localDataInputStream.read(); j != -1; j = localDataInputStream.read())
    {
      this.grantedQos[i] = j;
      i++;
    }
    localDataInputStream.close();
  }

  protected byte[] getVariableHeader()
    throws MqttException
  {
    return new byte[0];
  }

  public String toString()
  {
    String str = super.toString() + " granted Qos";
    for (int i = 0; i < this.grantedQos.length; i++)
      str = str + " " + this.grantedQos[i];
    return str;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MqttSuback
 * JD-Core Version:    0.6.2
 */