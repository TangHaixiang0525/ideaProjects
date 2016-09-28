package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttConnack extends MqttAck
{
  private int returnCode;

  public MqttConnack(byte paramByte, byte[] paramArrayOfByte)
    throws IOException
  {
    super((byte)2);
    DataInputStream localDataInputStream = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    localDataInputStream.readByte();
    this.returnCode = localDataInputStream.readUnsignedByte();
    localDataInputStream.close();
  }

  public String getKey()
  {
    return new String("Con");
  }

  public int getReturnCode()
  {
    return this.returnCode;
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

  public String toString()
  {
    return super.toString() + " return code: " + this.returnCode;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MqttConnack
 * JD-Core Version:    0.6.2
 */