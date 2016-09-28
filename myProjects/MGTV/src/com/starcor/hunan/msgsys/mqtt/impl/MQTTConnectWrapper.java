package com.starcor.hunan.msgsys.mqtt.impl;

import android.content.Context;
import com.starcor.hunan.msgsys.data.mqtt.MQTTConnectionParams;
import com.starcor.hunan.msgsys.interfaces.IMQTTConnect;
import com.starcor.hunan.msgsys.interfaces.IMQTTConnectWrapper;
import com.starcor.hunan.msgsys.interfaces.IMQTTConnectionStatusListener;

public class MQTTConnectWrapper
  implements IMQTTConnectWrapper
{
  private IMQTTConnect mMqttConnect = null;

  public void createConnection(Context paramContext, MQTTConnectionParams paramMQTTConnectionParams, IMQTTConnectionStatusListener paramIMQTTConnectionStatusListener)
  {
    this.mMqttConnect = new MQTTConnect(paramContext, paramMQTTConnectionParams, paramIMQTTConnectionStatusListener);
  }

  public void disconnect()
  {
    if (this.mMqttConnect != null)
      this.mMqttConnect.disconnectFromBroker();
  }

  public boolean isAlreadyConnected()
  {
    if (this.mMqttConnect != null)
      return this.mMqttConnect.isAlreadyConnected();
    return false;
  }

  public void keepAlive()
  {
    if (this.mMqttConnect != null)
      this.mMqttConnect.scheduleNextPing();
  }

  public boolean publishTopicMessage(String paramString1, String paramString2)
  {
    return this.mMqttConnect.publishTopicMessage(paramString1, paramString2);
  }

  public void reconnect()
  {
    if (this.mMqttConnect != null)
      this.mMqttConnect.tryToReconnect();
  }

  public void startToConnect()
  {
    if (this.mMqttConnect != null)
      this.mMqttConnect.connectAndSubscribe();
  }

  public void subscribeSingleTopic(String paramString, int paramInt)
  {
    if (this.mMqttConnect != null)
      this.mMqttConnect.subscribeSingleTopic(paramString, paramInt);
  }

  public void unsubscribeSingleTopic(String paramString)
  {
    if (this.mMqttConnect != null)
      this.mMqttConnect.unsubscribeSingleTopic(paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mqtt.impl.MQTTConnectWrapper
 * JD-Core Version:    0.6.2
 */