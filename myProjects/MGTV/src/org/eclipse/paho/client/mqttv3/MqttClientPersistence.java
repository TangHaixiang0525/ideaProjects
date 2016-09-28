package org.eclipse.paho.client.mqttv3;

import java.util.Enumeration;

public abstract interface MqttClientPersistence
{
  public abstract void clear()
    throws MqttPersistenceException;

  public abstract void close()
    throws MqttPersistenceException;

  public abstract boolean containsKey(String paramString)
    throws MqttPersistenceException;

  public abstract MqttPersistable get(String paramString)
    throws MqttPersistenceException;

  public abstract Enumeration keys()
    throws MqttPersistenceException;

  public abstract void open(String paramString1, String paramString2)
    throws MqttPersistenceException;

  public abstract void put(String paramString, MqttPersistable paramMqttPersistable)
    throws MqttPersistenceException;

  public abstract void remove(String paramString)
    throws MqttPersistenceException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.MqttClientPersistence
 * JD-Core Version:    0.6.2
 */