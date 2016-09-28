package org.eclipse.paho.client.mqttv3;

public abstract interface IMqttToken
{
  public abstract IMqttActionListener getActionCallback();

  public abstract IMqttAsyncClient getClient();

  public abstract MqttException getException();

  public abstract int getMessageId();

  public abstract String[] getTopics();

  public abstract Object getUserContext();

  public abstract boolean isComplete();

  public abstract void setActionCallback(IMqttActionListener paramIMqttActionListener);

  public abstract void setUserContext(Object paramObject);

  public abstract void waitForCompletion()
    throws MqttException;

  public abstract void waitForCompletion(long paramLong)
    throws MqttException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.IMqttToken
 * JD-Core Version:    0.6.2
 */