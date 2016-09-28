package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttUnsubscribe extends MqttWireMessage
{
  private int count;
  private String[] names;

  public MqttUnsubscribe(byte paramByte, byte[] paramArrayOfByte)
    throws IOException
  {
    super((byte)10);
    DataInputStream localDataInputStream = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    this.msgId = localDataInputStream.readUnsignedShort();
    this.count = 0;
    this.names = new String[10];
    int i = 0;
    while (i == 0)
      try
      {
        this.names[this.count] = decodeUTF8(localDataInputStream);
      }
      catch (Exception localException)
      {
        i = 1;
      }
    localDataInputStream.close();
  }

  public MqttUnsubscribe(String[] paramArrayOfString)
  {
    super((byte)10);
    this.names = paramArrayOfString;
  }

  protected byte getMessageInfo()
  {
    if (this.duplicate);
    for (int i = 8; ; i = 0)
      return (byte)(i | 0x2);
  }

  public byte[] getPayload()
    throws MqttException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    for (int i = 0; i < this.names.length; i++)
      encodeUTF8(localDataOutputStream, this.names[i]);
    return localByteArrayOutputStream.toByteArray();
  }

  protected byte[] getVariableHeader()
    throws MqttException
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
      localDataOutputStream.writeShort(this.msgId);
      localDataOutputStream.flush();
      byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new MqttException(localIOException);
    }
  }

  public boolean isRetryable()
  {
    return true;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(super.toString());
    localStringBuffer.append(" names:[");
    for (int i = 0; i < this.count; i++)
    {
      if (i > 0)
        localStringBuffer.append(", ");
      localStringBuffer.append("\"" + this.names[i] + "\"");
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MqttUnsubscribe
 * JD-Core Version:    0.6.2
 */