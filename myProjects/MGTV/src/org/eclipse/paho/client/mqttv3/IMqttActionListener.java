package org.eclipse.paho.client.mqttv3;

public abstract interface IMqttActionListener
{
  public abstract void onFailure(IMqttToken paramIMqttToken, Throwable paramThrowable);

  public abstract void onSuccess(IMqttToken paramIMqttToken);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.IMqttActionListener
 * JD-Core Version:    0.6.2
 */