package com.starcor.hunan.msgsys.interfaces;

import android.content.Context;
import com.starcor.hunan.msgsys.data.mqtt.MQTTConnectionParams;

public abstract interface IMQTTConnectWrapper
{
  public abstract void createConnection(Context paramContext, MQTTConnectionParams paramMQTTConnectionParams, IMQTTConnectionStatusListener paramIMQTTConnectionStatusListener);

  public abstract void disconnect();

  public abstract boolean isAlreadyConnected();

  public abstract void keepAlive();

  public abstract boolean publishTopicMessage(String paramString1, String paramString2);

  public abstract void reconnect();

  public abstract void startToConnect();

  public abstract void subscribeSingleTopic(String paramString, int paramInt);

  public abstract void unsubscribeSingleTopic(String paramString);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.interfaces.IMQTTConnectWrapper
 * JD-Core Version:    0.6.2
 */