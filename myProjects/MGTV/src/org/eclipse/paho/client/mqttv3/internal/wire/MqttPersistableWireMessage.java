package org.eclipse.paho.client.mqttv3.internal.wire;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public abstract class MqttPersistableWireMessage extends MqttWireMessage
  implements MqttPersistable
{
  public MqttPersistableWireMessage(byte paramByte)
  {
    super(paramByte);
  }

  public byte[] getHeaderBytes()
    throws MqttPersistenceException
  {
    try
    {
      byte[] arrayOfByte = getHeader();
      return arrayOfByte;
    }
    catch (MqttException localMqttException)
    {
      throw new MqttPersistenceException(localMqttException.getCause());
    }
  }

  public int getHeaderLength()
    throws MqttPersistenceException
  {
    return getHeaderBytes().length;
  }

  public int getHeaderOffset()
    throws MqttPersistenceException
  {
    return 0;
  }

  public byte[] getPayloadBytes()
    throws MqttPersistenceException
  {
    try
    {
      byte[] arrayOfByte = getPayload();
      return arrayOfByte;
    }
    catch (MqttException localMqttException)
    {
      throw new MqttPersistenceException(localMqttException.getCause());
    }
  }

  public int getPayloadLength()
    throws MqttPersistenceException
  {
    return 0;
  }

  public int getPayloadOffset()
    throws MqttPersistenceException
  {
    return 0;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.wire.MqttPersistableWireMessage
 * JD-Core Version:    0.6.2
 */