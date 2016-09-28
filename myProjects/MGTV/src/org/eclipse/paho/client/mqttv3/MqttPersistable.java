package org.eclipse.paho.client.mqttv3;

public abstract interface MqttPersistable
{
  public abstract byte[] getHeaderBytes()
    throws MqttPersistenceException;

  public abstract int getHeaderLength()
    throws MqttPersistenceException;

  public abstract int getHeaderOffset()
    throws MqttPersistenceException;

  public abstract byte[] getPayloadBytes()
    throws MqttPersistenceException;

  public abstract int getPayloadLength()
    throws MqttPersistenceException;

  public abstract int getPayloadOffset()
    throws MqttPersistenceException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.MqttPersistable
 * JD-Core Version:    0.6.2
 */