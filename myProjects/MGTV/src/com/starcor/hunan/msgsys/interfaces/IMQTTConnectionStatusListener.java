package com.starcor.hunan.msgsys.interfaces;

import org.eclipse.paho.client.mqttv3.MqttCallback;

public abstract interface IMQTTConnectionStatusListener extends MqttCallback
{
  public abstract void onConnectionStatusChange(String paramString, int paramInt);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.interfaces.IMQTTConnectionStatusListener
 * JD-Core Version:    0.6.2
 */