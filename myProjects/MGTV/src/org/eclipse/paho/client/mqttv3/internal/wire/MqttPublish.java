package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttPublish extends MqttPersistableWireMessage
{
  private byte[] encodedPayload = null;
  private MqttMessage message;
  private String topicName;

  public MqttPublish(byte paramByte, byte[] paramArrayOfByte)
    throws MqttException, IOException
  {
    super((byte)3);
    this.message = new MqttReceivedMessage();
    this.message.setQos(0x3 & paramByte >> 1);
    if ((paramByte & 0x1) == 1)
      this.message.setRetained(true);
    if ((paramByte & 0x8) == 8)
      ((MqttReceivedMessage)this.message).setDuplicate(true);
    CountingInputStream localCountingInputStream = new CountingInputStream(new ByteArrayInputStream(paramArrayOfByte));
    DataInputStream localDataInputStream = new DataInputStream(localCountingInputStream);
    this.topicName = decodeUTF8(localDataInputStream);
    if (this.message.getQos() > 0)
      this.msgId = localDataInputStream.readUnsignedShort();
    byte[] arrayOfByte = new byte[paramArrayOfByte.length - localCountingInputStream.getCounter()];
    localDataInputStream.readFully(arrayOfByte);
    localDataInputStream.close();
    this.message.setPayload(arrayOfByte);
  }

  public MqttPublish(String paramString, MqttMessage paramMqttMessage)
  {
    super((byte)3);
    this.topicName = paramString;
    this.message = paramMqttMessage;
  }

  protected static byte[] encodePayload(MqttMessage paramMqttMessage)
  {
    return paramMqttMessage.getPayload();
  }

  public MqttMessage getMessage()
  {
    return this.message;
  }

  protected byte getMessageInfo()
  {
    byte b = (byte)(this.message.getQos() << 1);
    if (this.message.isRetained())
      b = (byte)(b | 0x1);
    if ((this.message.isDuplicate()) || (this.duplicate))
      b = (byte)(b | 0x8);
    return b;
  }

  public byte[] getPayload()
    throws MqttException
  {
    if (this.encodedPayload == null)
      this.encodedPayload = encodePayload(this.message);
    return this.encodedPayload;
  }

  public int getPayloadLength()
  {
    try
    {
      int i = getPayload().length;
      return i;
    }
    catch (MqttException localMqttException)
    {
    }
    return 0;
  }

  public String getTopicName()
  {
    return this.topicName;
  }

  protected byte[] getVariableHeader()
    throws MqttException
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
      encodeUTF8(localDataOutputStream, this.topicName);
      if (this.message.getQos() > 0)
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

  public boolean isMessageIdRequired()
  {
    return true;
  }

  public void setMessageId(int paramInt)
  {
    super.setMessageId(paramInt);
    if ((this.message instanceof MqttReceivedMessage))
      ((MqttReceivedMessage)this.message).setMessageId(paramInt);
  }

  public String toString()
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    byte[] arrayOfByte = this.message.getPayload();
    int i = Math.min(arrayOfByte.length, 20);
    for (int j = 0; j < i; j++)
    {
      String str2 = Integer.toHexString(arrayOfByte[j]);
      if (str2.length() == 1)
        str2 = "0" + str2;
      localStringBuffer1.append(str2);
    }
    try
    {
      str1 = new String(arrayOfByte, 0, i, "UTF-8");
      StringBuffer localStringBuffer2 = new StringBuffer();
      localStringBuffer2.append(super.toString());
      localStringBuffer2.append(" qos:" + this.message.getQos());
      if (this.message.getQos() > 0)
        localStringBuffer2.append(" msgId:" + this.msgId);
      localStringBuffer2.append(" retained:" + this.message.isRetained());
      localStringBuffer2.append(" dup:" + this.duplicate);
      localStringBuffer2.append(" topic:\"" + this.topicName + "\"");
      localStringBuffer2.append(" payload:[hex:" + localStringBuffer1);
      localStringBuffer2.append(" utf8:\"" + str1 + "\"");
      localStringBuffer2.append(" length:" + arrayOfByte.length + "]");
      return localStringBuffer2.toString();
    }
    catch (Exception localException)
    {
      while (true)
        String str1 = "?";
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish
 * JD-Core Version:    0.6.2
 */