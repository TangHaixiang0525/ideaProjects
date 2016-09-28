package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttSubscribe extends MqttWireMessage
{
  private int count;
  private String[] names;
  private int[] qos;

  public MqttSubscribe(byte paramByte, byte[] paramArrayOfByte)
    throws IOException
  {
    super((byte)8);
    DataInputStream localDataInputStream = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    this.msgId = localDataInputStream.readUnsignedShort();
    this.count = 0;
    this.names = new String[10];
    this.qos = new int[10];
    int i = 0;
    while (i == 0)
      try
      {
        this.names[this.count] = decodeUTF8(localDataInputStream);
        int[] arrayOfInt = this.qos;
        int j = this.count;
        this.count = (j + 1);
        arrayOfInt[j] = localDataInputStream.readByte();
      }
      catch (Exception localException)
      {
        i = 1;
      }
    localDataInputStream.close();
  }

  public MqttSubscribe(String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    super((byte)8);
    this.names = paramArrayOfString;
    this.qos = paramArrayOfInt;
    if (paramArrayOfString.length != paramArrayOfInt.length)
      throw new IllegalArgumentException();
    for (int i = 0; i < paramArrayOfInt.length; i++)
      MqttMessage.validateQos(paramArrayOfInt[i]);
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
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
      for (int i = 0; i < this.names.length; i++)
      {
        encodeUTF8(localDataOutputStream, this.names[i]);
        localDataOutputStream.writeByte(this.qos[i]);
      }
      byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new MqttException(localIOException);
    }
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
    localStringBuffer.append("] qos:[");
    for (int j = 0; j < this.count; j++)
    {
      if (j > 0)
        localStringBuffer.append(", ");
      localStringBuffer.append(this.qos[j]);
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MqttSubscribe
 * JD-Core Version:    0.6.2
 */