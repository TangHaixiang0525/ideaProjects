package org.eclipse.paho.client.mqttv3;

public class MqttPersistenceException extends MqttException
{
  public static final short REASON_CODE_PERSISTENCE_IN_USE = 32200;
  private static final long serialVersionUID = 300L;

  public MqttPersistenceException()
  {
    super(0);
  }

  public MqttPersistenceException(int paramInt)
  {
    super(paramInt);
  }

  public MqttPersistenceException(int paramInt, Throwable paramThrowable)
  {
    super(paramInt, paramThrowable);
  }

  public MqttPersistenceException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.MqttPersistenceException
 * JD-Core Version:    0.6.2
 */