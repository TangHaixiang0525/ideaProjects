package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.internal.ExceptionHelper;

public abstract class MqttWireMessage
{
  public static final byte MESSAGE_TYPE_CONNACK = 2;
  public static final byte MESSAGE_TYPE_CONNECT = 1;
  public static final byte MESSAGE_TYPE_DISCONNECT = 14;
  public static final byte MESSAGE_TYPE_PINGREQ = 12;
  public static final byte MESSAGE_TYPE_PINGRESP = 13;
  public static final byte MESSAGE_TYPE_PUBACK = 4;
  public static final byte MESSAGE_TYPE_PUBCOMP = 7;
  public static final byte MESSAGE_TYPE_PUBLISH = 3;
  public static final byte MESSAGE_TYPE_PUBREC = 5;
  public static final byte MESSAGE_TYPE_PUBREL = 6;
  public static final byte MESSAGE_TYPE_SUBACK = 9;
  public static final byte MESSAGE_TYPE_SUBSCRIBE = 8;
  public static final byte MESSAGE_TYPE_UNSUBACK = 11;
  public static final byte MESSAGE_TYPE_UNSUBSCRIBE = 10;
  protected static final String STRING_ENCODING = "UTF-8";
  protected boolean duplicate = false;
  private byte[] encodedHeader = null;
  protected int msgId;
  String[] packet_names = { "reserved", "CONNECT", "CONNACK", "PUBLISH", "PUBACK", "PUBREC", "PUBREL", "PUBCOMP", "SUBSCRIBE", "SUBACK", "UNSUBSCRIBE", "UNSUBACK", "PINGREQ", "PINGRESP", "DISCONNECT" };
  private byte type;

  public MqttWireMessage(byte paramByte)
  {
    this.type = paramByte;
    this.msgId = 0;
  }

  private static MqttWireMessage createWireMessage(InputStream paramInputStream)
    throws MqttException
  {
    try
    {
      CountingInputStream localCountingInputStream = new CountingInputStream(paramInputStream);
      DataInputStream localDataInputStream = new DataInputStream(localCountingInputStream);
      int i = localDataInputStream.readUnsignedByte();
      int j = (byte)(i >> 4);
      byte b = (byte)(i & 0xF);
      long l = readMBI(localDataInputStream).getValue() + localCountingInputStream.getCounter() - localCountingInputStream.getCounter();
      byte[] arrayOfByte = new byte[0];
      if (l > 0L)
      {
        arrayOfByte = new byte[(int)l];
        localDataInputStream.readFully(arrayOfByte, 0, arrayOfByte.length);
      }
      if (j == 1)
        return new MqttConnect(b, arrayOfByte);
      if (j == 3)
        return new MqttPublish(b, arrayOfByte);
      if (j == 4)
        return new MqttPubAck(b, arrayOfByte);
      if (j == 7)
        return new MqttPubComp(b, arrayOfByte);
      if (j == 2)
        return new MqttConnack(b, arrayOfByte);
      if (j == 12)
        return new MqttPingReq(b, arrayOfByte);
      if (j == 13)
        return new MqttPingResp(b, arrayOfByte);
      if (j == 8)
        return new MqttSubscribe(b, arrayOfByte);
      if (j == 9)
        return new MqttSuback(b, arrayOfByte);
      if (j == 10)
        return new MqttUnsubscribe(b, arrayOfByte);
      if (j == 11)
        return new MqttUnsubAck(b, arrayOfByte);
      if (j == 6)
        return new MqttPubRel(b, arrayOfByte);
      if (j == 5)
        return new MqttPubRec(b, arrayOfByte);
      if (j == 14)
        return new MqttDisconnect(b, arrayOfByte);
      throw ExceptionHelper.createMqttException(6);
    }
    catch (IOException localIOException)
    {
      MqttException localMqttException = new MqttException(localIOException);
      throw localMqttException;
    }
  }

  public static MqttWireMessage createWireMessage(MqttPersistable paramMqttPersistable)
    throws MqttException
  {
    byte[] arrayOfByte = paramMqttPersistable.getPayloadBytes();
    if (arrayOfByte == null)
      arrayOfByte = new byte[0];
    return createWireMessage(new MultiByteArrayInputStream(paramMqttPersistable.getHeaderBytes(), paramMqttPersistable.getHeaderOffset(), paramMqttPersistable.getHeaderLength(), arrayOfByte, paramMqttPersistable.getPayloadOffset(), paramMqttPersistable.getPayloadLength()));
  }

  public static MqttWireMessage createWireMessage(byte[] paramArrayOfByte)
    throws MqttException
  {
    return createWireMessage(new ByteArrayInputStream(paramArrayOfByte));
  }

  protected static byte[] encodeMBI(long paramLong)
  {
    int i = 0;
    long l = paramLong;
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    do
    {
      int j = (byte)(int)(l % 128L);
      l /= 128L;
      if (l > 0L)
        j = (byte)(j | 0x80);
      localByteArrayOutputStream.write(j);
      i++;
    }
    while ((l > 0L) && (i < 4));
    return localByteArrayOutputStream.toByteArray();
  }

  protected static MultiByteInteger readMBI(DataInputStream paramDataInputStream)
    throws IOException
  {
    long l = 0L;
    int i = 1;
    int j = 0;
    int k;
    do
    {
      k = paramDataInputStream.readByte();
      j++;
      l += i * (k & 0x7F);
      i *= 128;
    }
    while ((k & 0x80) != 0);
    return new MultiByteInteger(l, j);
  }

  protected String decodeUTF8(DataInputStream paramDataInputStream)
    throws MqttException
  {
    try
    {
      byte[] arrayOfByte = new byte[paramDataInputStream.readUnsignedShort()];
      paramDataInputStream.readFully(arrayOfByte);
      String str = new String(arrayOfByte, "UTF-8");
      return str;
    }
    catch (IOException localIOException)
    {
      throw new MqttException(localIOException);
    }
  }

  protected byte[] encodeMessageId()
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

  protected void encodeUTF8(DataOutputStream paramDataOutputStream, String paramString)
    throws MqttException
  {
    try
    {
      byte[] arrayOfByte = paramString.getBytes("UTF-8");
      int i = (byte)(0xFF & arrayOfByte.length >>> 8);
      int j = (byte)(0xFF & arrayOfByte.length >>> 0);
      paramDataOutputStream.write(i);
      paramDataOutputStream.write(j);
      paramDataOutputStream.write(arrayOfByte);
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new MqttException(localUnsupportedEncodingException);
    }
    catch (IOException localIOException)
    {
      throw new MqttException(localIOException);
    }
  }

  public byte[] getHeader()
    throws MqttException
  {
    if (this.encodedHeader == null);
    try
    {
      int i = (0xF & getType()) << 4 ^ 0xF & getMessageInfo();
      byte[] arrayOfByte = getVariableHeader();
      int j = arrayOfByte.length + getPayload().length;
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
      localDataOutputStream.writeByte(i);
      localDataOutputStream.write(encodeMBI(j));
      localDataOutputStream.write(arrayOfByte);
      localDataOutputStream.flush();
      this.encodedHeader = localByteArrayOutputStream.toByteArray();
      return this.encodedHeader;
    }
    catch (IOException localIOException)
    {
      throw new MqttException(localIOException);
    }
  }

  public String getKey()
  {
    return new Integer(getMessageId()).toString();
  }

  public int getMessageId()
  {
    return this.msgId;
  }

  protected abstract byte getMessageInfo();

  public byte[] getPayload()
    throws MqttException
  {
    return new byte[0];
  }

  public byte getType()
  {
    return this.type;
  }

  protected abstract byte[] getVariableHeader()
    throws MqttException;

  public boolean isMessageIdRequired()
  {
    return true;
  }

  public boolean isRetryable()
  {
    return false;
  }

  public void setDuplicate(boolean paramBoolean)
  {
    this.duplicate = paramBoolean;
  }

  public void setMessageId(int paramInt)
  {
    this.msgId = paramInt;
  }

  public String toString()
  {
    return this.packet_names[this.type];
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage
 * JD-Core Version:    0.6.2
 */