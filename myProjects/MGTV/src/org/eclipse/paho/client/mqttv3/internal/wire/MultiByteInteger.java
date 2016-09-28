package org.eclipse.paho.client.mqttv3.internal.wire;

public class MultiByteInteger
{
  private int length;
  private long value;

  public MultiByteInteger(long paramLong)
  {
    this(paramLong, -1);
  }

  public MultiByteInteger(long paramLong, int paramInt)
  {
    this.value = paramLong;
    this.length = paramInt;
  }

  public int getEncodedLength()
  {
    return this.length;
  }

  public long getValue()
  {
    return this.value;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MultiByteInteger
 * JD-Core Version:    0.6.2
 */