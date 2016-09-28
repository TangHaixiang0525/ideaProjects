package com.starcor.hunan.msgsys.mqtt.impl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.msgsys.data.mqtt.MQTTConnectionParams;
import com.starcor.hunan.msgsys.interfaces.IMQTTConnect;
import com.starcor.hunan.msgsys.interfaces.IMQTTConnectionStatusListener;
import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPingSender;

public class MQTTConnect
  implements IMQTTConnect
{
  private static final String TAG = MQTTConnect.class.getSimpleName();
  private Context mContext;
  private int mCurrentReconTimes = 0;
  private IMQTTConnectionStatusListener mListener = null;
  private MqttClient mMqttClient = null;
  private MQTTConnectionParams mParams;
  private PingSchduleTask mPingSchduleTask = null;
  private MyPingSender mPingSender = null;
  private Timer mPingTimer = null;
  private Timer mReconTimer = null;
  private ReconnectionTask mReconnectionTask = null;

  public MQTTConnect(Context paramContext, MQTTConnectionParams paramMQTTConnectionParams, IMQTTConnectionStatusListener paramIMQTTConnectionStatusListener)
  {
    this.mContext = paramContext;
    this.mParams = paramMQTTConnectionParams;
    this.mListener = paramIMQTTConnectionStatusListener;
    init();
    setupMqttClient();
  }

  private boolean checkNetworkStatus()
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)this.mContext.getSystemService("connectivity");
    return (localConnectivityManager.getActiveNetworkInfo() != null) && (localConnectivityManager.getActiveNetworkInfo().isAvailable()) && (localConnectivityManager.getActiveNetworkInfo().isConnected());
  }

  private void init()
  {
    this.mReconTimer = new Timer();
    this.mPingTimer = new Timer();
    this.mPingSender = new MyPingSender();
  }

  private void setupMqttClient()
  {
    try
    {
      this.mMqttClient = new MqttClient(this.mParams.getBrokerUri(), this.mParams.getClientId(), null);
      this.mMqttClient.setCallback(this.mListener);
      return;
    }
    catch (Exception localException)
    {
      Logger.i(TAG, "create mqtt client exception!");
      localException.printStackTrace();
      this.mListener.onConnectionStatusChange(localException.getLocalizedMessage(), 32103);
    }
  }

  private void subscribeToTopic()
  {
    try
    {
      this.mMqttClient.subscribe(this.mParams.getTopics(), this.mParams.getQos());
      return;
    }
    catch (Exception localException)
    {
      Logger.e(TAG, "subscribeToTopic的错误信息：" + localException.getLocalizedMessage());
      this.mListener.onConnectionStatusChange(localException.getLocalizedMessage(), 32103);
    }
  }

  public void connectAndSubscribe()
  {
    if (!isAlreadyConnected())
    {
      if (!checkNetworkStatus())
        break label26;
      if (connectToBroker())
        subscribeToTopic();
    }
    return;
    label26: this.mListener.onConnectionStatusChange("本地网络连接已断开！", 5001);
  }

  public boolean connectToBroker()
  {
    if ((this.mMqttClient == null) || (this.mParams == null) || (isAlreadyConnected()))
    {
      Logger.e(TAG, "connectToBroker params null");
      return false;
    }
    try
    {
      this.mMqttClient.connect(this.mParams.getConnectOptions());
      bool = true;
      return bool;
    }
    catch (MqttException localMqttException)
    {
      while (true)
      {
        int i = localMqttException.getReasonCode();
        Logger.e(TAG, "connectToBroker reasonCode=" + i);
        String str = localMqttException.getLocalizedMessage();
        if (str != null)
          str = str.trim();
        Logger.e(TAG, "connectToBroker exception=", localMqttException);
        this.mListener.onConnectionStatusChange(str, i);
        boolean bool = false;
        if (i == 32100)
          bool = true;
      }
    }
  }

  public void disconnectFromBroker()
  {
    Logger.i(TAG, "正在从 " + this.mParams.getBrokerUri() + "断开连接！");
    new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          if (MQTTConnect.this.mMqttClient != null)
          {
            MQTTConnect.this.mMqttClient.disconnect();
            MQTTConnect.this.mMqttClient.close();
            Logger.i(MQTTConnect.TAG, "isAlreadyConnected?" + MQTTConnect.this.isAlreadyConnected());
            Logger.i(MQTTConnect.TAG, "已经从服务器断开连接!");
          }
          return;
        }
        catch (Exception localException)
        {
          Logger.e(MQTTConnect.TAG, "exception", localException);
        }
      }
    }).start();
    if (this.mReconTimer != null)
      if (this.mReconnectionTask != null)
      {
        this.mReconnectionTask.cancel();
        this.mReconnectionTask = null;
      }
    synchronized (this.mReconTimer)
    {
      this.mReconTimer.cancel();
      this.mReconTimer.purge();
      this.mReconTimer = null;
      Logger.i(TAG, "完成取消以及回收重连任务和调度者");
      if (this.mPingTimer != null)
      {
        if (this.mReconnectionTask != null)
        {
          this.mReconnectionTask.cancel();
          this.mReconnectionTask = null;
        }
        this.mPingTimer.cancel();
        this.mPingTimer.purge();
        this.mPingTimer = null;
        Logger.i(TAG, "完成取消和回收ping任务和调度者");
      }
      return;
    }
  }

  public boolean isAlreadyConnected()
  {
    return (this.mMqttClient != null) && (this.mMqttClient.isConnected() == true);
  }

  public boolean publishTopicMessage(String paramString1, String paramString2)
  {
    if (isAlreadyConnected())
      try
      {
        Logger.i(TAG, "开始发布主题" + paramString1 + "的消息为" + paramString2);
        this.mMqttClient.publish(paramString1, paramString2.getBytes(), 1, false);
        return true;
      }
      catch (Exception localException)
      {
        this.mListener.onConnectionStatusChange(localException.getLocalizedMessage(), 32103);
      }
    return false;
  }

  public void resetReconnectionTimes()
  {
    Logger.i(TAG, "before resetReconnectionTimes currentReconTimes=" + this.mCurrentReconTimes);
    this.mCurrentReconTimes = 0;
  }

  public void scheduleNextPing()
  {
    if (this.mPingTimer != null)
    {
      if (this.mPingSchduleTask != null)
      {
        this.mPingSchduleTask.cancel();
        this.mPingSchduleTask = null;
      }
      Logger.i(TAG, "开始下一轮的ping");
      this.mPingSchduleTask = new PingSchduleTask(null);
      this.mPingTimer.schedule(this.mPingSchduleTask, 300000L);
    }
  }

  public void subscribeSingleTopic(String paramString, int paramInt)
  {
    try
    {
      this.mMqttClient.subscribe(paramString, paramInt);
      return;
    }
    catch (Exception localException)
    {
      Logger.e(TAG, "subscribeToSingleTopic：" + localException.getLocalizedMessage());
      this.mListener.onConnectionStatusChange(localException.getLocalizedMessage(), 5004);
    }
  }

  public void tryToReconnect()
  {
    if (this.mReconTimer == null)
      this.mReconTimer = new Timer();
    if (this.mReconnectionTask != null)
    {
      Logger.i(TAG, "cancel the old timer task");
      this.mReconnectionTask.cancel();
      this.mReconnectionTask = null;
    }
    if (isAlreadyConnected())
      Logger.i(TAG, "客户端已经和服务器取得连接！");
    do
    {
      return;
      this.mReconnectionTask = new ReconnectionTask(null);
    }
    while (this.mReconTimer == null);
    try
    {
      synchronized (this.mReconTimer)
      {
        if (this.mReconTimer != null)
          this.mReconTimer.schedule(this.mReconnectionTask, 8000);
        return;
      }
    }
    catch (Exception localException)
    {
      while (true)
        Logger.e(TAG, "timer exception", localException);
    }
  }

  public void unsubscribeSingleTopic(String paramString)
  {
    try
    {
      this.mMqttClient.unsubscribe(paramString);
      return;
    }
    catch (Exception localException)
    {
      Logger.e(TAG, "unsubscribeSingleTopic：" + localException.getLocalizedMessage());
      this.mListener.onConnectionStatusChange(localException.getLocalizedMessage(), 5005);
    }
  }

  private class MyPingSender
  {
    private MqttPingSender mPingSender = null;

    public MyPingSender()
    {
      try
      {
        this.mPingSender = new MqttPingSender(MQTTConnect.this.mParams.getBrokerUri(), MQTTConnect.this.mParams.getClientId(), null);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }

    public void ping()
      throws MqttException
    {
      if (this.mPingSender != null)
      {
        this.mPingSender.ping();
        return;
      }
      Logger.i(MQTTConnect.TAG, "ping mPingSender is null");
    }

    public void ping(MqttClient paramMqttClient)
      throws MqttException
    {
      if (this.mPingSender != null)
      {
        this.mPingSender.ping(paramMqttClient);
        return;
      }
      Logger.i(MQTTConnect.TAG, "ping mPingSender is null!");
    }
  }

  private class PingSchduleTask extends TimerTask
  {
    private PingSchduleTask()
    {
    }

    public void run()
    {
      if (MQTTConnect.this.mPingSender == null)
        MQTTConnect.access$802(MQTTConnect.this, new MQTTConnect.MyPingSender(MQTTConnect.this));
      Logger.i(MQTTConnect.TAG, "开始ping!");
      try
      {
        if (MQTTConnect.this.mMqttClient != null)
        {
          MQTTConnect.this.mPingSender.ping(MQTTConnect.this.mMqttClient);
          return;
        }
        MQTTConnect.this.mPingSender.ping();
        return;
      }
      catch (Exception localException)
      {
        Logger.e(MQTTConnect.TAG, "发送ping消息到服务端时候出错, 消息为msg = " + localException.getMessage() + ", 错误码为code = " + localException.toString());
      }
    }
  }

  private class ReconnectionTask extends TimerTask
  {
    private ReconnectionTask()
    {
    }

    public void run()
    {
      if (MQTTConnect.this.isAlreadyConnected())
      {
        Logger.i(MQTTConnect.TAG, "在重连任务中检测到客户端已经和服务端取得连接！");
        return;
      }
      Logger.i(MQTTConnect.TAG, "try to reconnect to the broker!");
      if ((MQTTConnect.this.checkNetworkStatus()) && (MQTTConnect.this.connectToBroker()))
      {
        MQTTConnect.this.subscribeToTopic();
        MQTTConnect.this.mListener.onConnectionStatusChange("已连接客户端", 32100);
        Logger.i(MQTTConnect.TAG, "reconnect success!");
      }
      while (MQTTConnect.this.mCurrentReconTimes >= 5)
      {
        MQTTConnect.this.resetReconnectionTimes();
        return;
      }
      Logger.i(MQTTConnect.TAG, "current reconnect times = " + MQTTConnect.this.mCurrentReconTimes);
      MQTTConnect.access$708(MQTTConnect.this);
      MQTTConnect.this.tryToReconnect();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mqtt.impl.MQTTConnect
 * JD-Core Version:    0.6.2
 */