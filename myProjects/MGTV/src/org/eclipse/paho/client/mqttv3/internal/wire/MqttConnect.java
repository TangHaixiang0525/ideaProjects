package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttConnect extends MqttWireMessage
{
  public static String KEY = "Con";
  private boolean cleanSession;
  private String clientId;
  private int keepAliveInterval;
  private char[] password;
  private String userName;
  private String willDestination;
  private MqttMessage willMessage;

  public MqttConnect(byte paramByte, byte[] paramArrayOfByte)
    throws IOException, MqttException
  {
    super((byte)1);
    DataInputStream localDataInputStream = new DataInputStream(new ByteArrayInputStream(paramArrayOfByte));
    decodeUTF8(localDataInputStream);
    localDataInputStream.readByte();
    localDataInputStream.readByte();
    this.keepAliveInterval = localDataInputStream.readUnsignedShort();
    this.clientId = decodeUTF8(localDataInputStream);
    localDataInputStream.close();
  }

  public MqttConnect(String paramString1, boolean paramBoolean, int paramInt, String paramString2, char[] paramArrayOfChar, MqttMessage paramMqttMessage, String paramString3)
  {
    super((byte)1);
    this.clientId = paramString1;
    this.cleanSession = paramBoolean;
    this.keepAliveInterval = paramInt;
    this.userName = paramString2;
    this.password = paramArrayOfChar;
    this.willMessage = paramMqttMessage;
    this.willDestination = paramString3;
  }

  public String getKey()
  {
    return new String(KEY);
  }

  protected byte getMessageInfo()
  {
    return 0;
  }

  public byte[] getPayload()
    throws MqttException
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
      encodeUTF8(localDataOutputStream, this.clientId);
      if (this.willMessage != null)
      {
        encodeUTF8(localDataOutputStream, this.willDestination);
        localDataOutputStream.writeShort(this.willMessage.getPayload().length);
        localDataOutputStream.write(this.willMessage.getPayload());
      }
      if (this.userName != null)
      {
        encodeUTF8(localDataOutputStream, this.userName);
        if (this.password != null)
          encodeUTF8(localDataOutputStream, new String(this.password));
      }
      localDataOutputStream.flush();
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
      encodeUTF8(localDataOutputStream, "MQIsdp");
      localDataOutputStream.write(3);
      boolean bool = this.cleanSession;
      int i = 0;
      if (bool)
        i = 2;
      if (this.willMessage != null)
      {
        i = (byte)((byte)(i | 0x4) | this.willMessage.getQos() << 3);
        if (this.willMessage.isRetained())
          i = (byte)(i | 0x20);
      }
      if (this.userName != null)
      {
        i = (byte)(i | 0x80);
        if (this.password != null)
          i = (byte)(i | 0x40);
      }
      localDataOutputStream.write(i);
      localDataOutputStream.writeShort(this.keepAliveInterval);
      localDataOutputStream.flush();
      byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new MqttException(localIOException);
    }
  }

  public boolean isCleanSession()
  {
    return this.cleanSession;
  }

  public boolean isMessageIdRequired()
  {
    return false;
  }

  public String toString()
  {
    String str = super.toString();
    return str + " clientId " + this.clientId + " keepAliveInterval " + this.keepAliveInterval;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect
 * JD-Core Version:    0.6.2
 */