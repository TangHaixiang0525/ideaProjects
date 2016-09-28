package org.eclipse.paho.client.mqttv3;

public class MqttMessage
{
  private boolean dup = false;
  private boolean mutable = true;
  private byte[] payload;
  private int qos = 1;
  private boolean retained = false;

  public MqttMessage()
  {
    setPayload(new byte[0]);
  }

  public MqttMessage(byte[] paramArrayOfByte)
  {
    setPayload(paramArrayOfByte);
  }

  public static void validateQos(int paramInt)
  {
    if ((paramInt < 0) || (paramInt > 2))
      throw new IllegalArgumentException();
  }

  protected void checkMutable()
    throws IllegalStateException
  {
    if (!this.mutable)
      throw new IllegalStateException();
  }

  public void clearPayload()
  {
    checkMutable();
    this.payload = new byte[0];
  }

  public byte[] getPayload()
  {
    return this.payload;
  }

  public int getQos()
  {
    return this.qos;
  }

  public boolean isDuplicate()
  {
    return this.dup;
  }

  public boolean isRetained()
  {
    return this.retained;
  }

  protected void setDuplicate(boolean paramBoolean)
  {
    this.dup = paramBoolean;
  }

  protected void setMutable(boolean paramBoolean)
  {
    this.mutable = paramBoolean;
  }

  public void setPayload(byte[] paramArrayOfByte)
  {
    checkMutable();
    if (paramArrayOfByte == null)
      throw new NullPointerException();
    this.payload = paramArrayOfByte;
  }

  public void setQos(int paramInt)
  {
    checkMutable();
    validateQos(paramInt);
    this.qos = paramInt;
  }

  public void setRetained(boolean paramBoolean)
  {
    checkMutable();
    this.retained = paramBoolean;
  }

  public String toString()
  {
    return new String(this.payload);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.MqttMessage
 * JD-Core Version:    0.6.2
 */