package com.starcor.hunan.msgsys.data.mqtt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class MQTTConnectionParams
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String mBrokerUri;
  private boolean mCleanSession;
  private String mClientId;
  private int mKeepAlive;
  private char[] mPassword;
  private int[] mQos;
  private int mTimeOut;
  private List mTopics;
  private String mUserName;

  public MQTTConnectionParams(String paramString, char[] paramArrayOfChar, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this.mUserName = paramString;
    this.mPassword = paramArrayOfChar;
    this.mKeepAlive = paramInt1;
    this.mTimeOut = paramInt2;
    this.mCleanSession = paramBoolean;
    this.mTopics = new ArrayList();
  }

  public void addTopic(String paramString)
  {
    this.mTopics.add(paramString);
  }

  public String getBrokerUri()
  {
    return this.mBrokerUri;
  }

  public String getClientId()
  {
    return this.mClientId;
  }

  public MqttConnectOptions getConnectOptions()
  {
    MqttConnectOptions localMqttConnectOptions = new MqttConnectOptions();
    localMqttConnectOptions.setUserName(this.mUserName);
    localMqttConnectOptions.setPassword(this.mPassword);
    localMqttConnectOptions.setKeepAliveInterval(this.mKeepAlive);
    localMqttConnectOptions.setConnectionTimeout(this.mTimeOut);
    localMqttConnectOptions.setCleanSession(this.mCleanSession);
    return localMqttConnectOptions;
  }

  public int[] getQos()
  {
    return this.mQos;
  }

  public String[] getTopics()
  {
    int i = this.mTopics.size();
    String[] arrayOfString = null;
    if (i > 0)
    {
      arrayOfString = new String[i];
      for (int j = 0; j < i; j++)
        arrayOfString[j] = ((String)this.mTopics.get(j));
    }
    return arrayOfString;
  }

  public void setBrokerUri(String paramString)
  {
    this.mBrokerUri = paramString;
  }

  public void setClientId(String paramString)
  {
    this.mClientId = paramString;
  }

  public void setQos(int[] paramArrayOfInt)
  {
    this.mQos = paramArrayOfInt;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.data.mqtt.MQTTConnectionParams
 * JD-Core Version:    0.6.2
 */