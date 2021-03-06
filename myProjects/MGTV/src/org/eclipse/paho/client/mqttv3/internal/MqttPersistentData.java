package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.MqttPersistable;

public class MqttPersistentData
  implements MqttPersistable
{
  private int hLength = 0;
  private int hOffset = 0;
  private byte[] header = null;
  private String key = null;
  private int pLength = 0;
  private int pOffset = 0;
  private byte[] payload = null;

  public MqttPersistentData(String paramString, byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4)
  {
    this.key = paramString;
    this.header = paramArrayOfByte1;
    this.hOffset = paramInt1;
    this.hLength = paramInt2;
    this.payload = paramArrayOfByte2;
    this.pOffset = paramInt3;
    this.pLength = paramInt4;
  }

  public byte[] getHeaderBytes()
  {
    return this.header;
  }

  public int getHeaderLength()
  {
    return this.hLength;
  }

  public int getHeaderOffset()
  {
    return this.hOffset;
  }

  public String getKey()
  {
    return this.key;
  }

  public byte[] getPayloadBytes()
  {
    return this.payload;
  }

  public int getPayloadLength()
  {
    if (this.payload == null)
      return 0;
    return this.pLength;
  }

  public int getPayloadOffset()
  {
    return this.pOffset;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.MqttPersistentData
 * JD-Core Version:    0.6.2
 */