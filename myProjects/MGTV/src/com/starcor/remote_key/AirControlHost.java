package com.starcor.remote_key;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import com.starcor.ott.AirControl;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class AirControlHost
{
  private static final String TAG = AirControlHost.class.getSimpleName();
  static AirControlHost _inst;
  static ArrayList<IAirCommandListener> _listener = new ArrayList();
  Context _ctx;
  boolean _isRunning = false;
  KeyEventSender _keySender;
  KeyEventSender _keySenderFallBack;
  Thread _worker;

  private AirControlHost(Context paramContext, InitParams paramInitParams)
  {
    Log.d(TAG, "start AirControlHost - " + paramInitParams.toString());
    if (AirControl.init() != true)
    {
      Log.e(TAG, "AirControl.init() failed!!");
      return;
    }
    this._keySenderFallBack = new InputCMDKeyEventSender(paramContext);
    this._keySender = new DirectKeyEventSender(paramContext);
    this._ctx = paramContext;
    AirControl.start(7799, paramContext.getDir("tv_air_control", 0).getAbsolutePath());
    AirControl.setConfig("device_id", paramInitParams.deviceId);
    AirControl.setConfig("device_name", paramInitParams.deviceName);
    AirControl.setConfig("mac", paramInitParams.mac);
    AirControl.setConfig("n31a", paramInitParams.url);
    if (paramInitParams.allowMultiIp);
    for (String str = "1"; ; str = "0")
    {
      AirControl.setConfig("n31a_all_ctrl_ip_report", str);
      if (paramInitParams.extConfig == null)
        break;
      Iterator localIterator = paramInitParams.extConfig.iterator();
      while (localIterator.hasNext())
      {
        Pair localPair = (Pair)localIterator.next();
        if (localPair != null)
          AirControl.setConfig((String)localPair.first, (String)localPair.second);
      }
    }
    AirControl.doAction("start_wx", "");
    this._isRunning = true;
    this._worker = new Thread()
    {
      public void run()
      {
        while (AirControlHost.this._isRunning)
        {
          String str = AirControl.getEvent();
          if (!TextUtils.isEmpty(str))
          {
            Log.d(AirControlHost.TAG, str);
            Event localEvent;
            try
            {
              localEvent = Event.build(str.getBytes());
              switch (AirControlHost.2.$SwitchMap$com$starcor$remote_key$Event$EventType[localEvent.type.ordinal()])
              {
              case 1:
              default:
                if (!localEvent.needReply)
                  continue;
                AirControl.replyEvent(localEvent.id, 10003, "command not supported", "");
              case 2:
              case 3:
              case 4:
              case 5:
              case 6:
              case 7:
              case 8:
              case 9:
              case 10:
              case 11:
              case 12:
              case 13:
              case 14:
              case 15:
              }
            }
            catch (Exception localException)
            {
              localException.printStackTrace();
            }
            continue;
            AirControlHost.this.onKeyClickEvent(localEvent);
            continue;
            AirControlHost.this.onTextEvent(localEvent);
            continue;
            AirControlHost.this.onGetDeviceInfo(localEvent);
            continue;
            AirControlHost.this.onGetUserInfo(localEvent);
            continue;
            AirControlHost.this.onShowTip(localEvent);
            continue;
            AirControlHost.this.onPlayVideo(localEvent);
            continue;
            AirControlHost.this.processCommonEvent(localEvent);
          }
        }
        Log.d(AirControlHost.TAG, "worker terminated!!");
      }
    };
    this._worker.start();
  }

  private boolean _dispatchKeyEventEvent(int paramInt1, int paramInt2)
  {
    int i = paramInt1 & 0xFFFF0000;
    int j = paramInt1 & 0xFFFF;
    System.currentTimeMillis();
    int k = 0x40000 & i;
    int m = 0;
    if (k == 262144)
      m = 0 + 2;
    if ((0x10000 & i) == 65536)
      m++;
    if ((0x10000 & i) == 131072)
      m += 4096;
    KeyEvent localKeyEvent1 = new KeyEvent(0L, 0L, 0, j, 0, m);
    KeyEvent localKeyEvent2 = new KeyEvent(0L, 0L, 1, j, 0, m);
    Iterator localIterator = _listener.iterator();
    while (localIterator.hasNext())
    {
      IAirCommandListener localIAirCommandListener = (IAirCommandListener)localIterator.next();
      switch (paramInt2)
      {
      default:
        break;
      case 0:
      case 2:
      case 1:
        try
        {
          if (localIAirCommandListener.onKeyEvent(localKeyEvent1))
          {
            localIAirCommandListener.onKeyEvent(localKeyEvent2);
            return true;
          }
          if (localIAirCommandListener.onKeyEvent(localKeyEvent2))
            return true;
          boolean bool = localIAirCommandListener.onKeyEvent(localKeyEvent1);
          if (bool)
            return true;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    }
    return false;
  }

  private boolean _dispatchMessage(String paramString, Bundle paramBundle)
  {
    Iterator localIterator = _listener.iterator();
    while (localIterator.hasNext())
    {
      IAirCommandListener localIAirCommandListener = (IAirCommandListener)localIterator.next();
      try
      {
        boolean bool = localIAirCommandListener.onMessage(paramString, paramBundle);
        if (bool)
          return true;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return false;
  }

  private boolean _dispatchTextEvent(String paramString)
  {
    Iterator localIterator = _listener.iterator();
    while (localIterator.hasNext())
    {
      IAirCommandListener localIAirCommandListener = (IAirCommandListener)localIterator.next();
      try
      {
        boolean bool = localIAirCommandListener.onTextInput(paramString);
        if (bool)
          return true;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return false;
  }

  private void _shutdown()
  {
    try
    {
      if (this._isRunning)
      {
        this._isRunning = false;
        AirControl.stop();
        this._worker.join();
      }
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
  }

  private String argListFromBundle(Bundle paramBundle)
  {
    try
    {
      localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startTag(null, "arg_list");
      Iterator localIterator = paramBundle.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        String str3 = paramBundle.getString(str2);
        localXmlSerializer.startTag(null, "arg");
        localXmlSerializer.attribute(null, "name", str2);
        if (str3 == null)
          str3 = "";
        localXmlSerializer.attribute(null, "value", str3);
        localXmlSerializer.endTag(null, "arg");
      }
    }
    catch (XmlPullParserException localXmlPullParserException)
    {
      XmlSerializer localXmlSerializer;
      StringWriter localStringWriter;
      localXmlPullParserException.printStackTrace();
      return null;
      localXmlSerializer.endTag(null, "arg_list");
      localXmlSerializer.flush();
      String str1 = localStringWriter.getBuffer().toString();
      return str1;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      return null;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private void onGetDeviceInfo(Event paramEvent)
  {
    Log.d(TAG, "onGetDeviceInfo");
    Bundle localBundle = new Bundle();
    String str;
    int i;
    if (_dispatchMessage("getDeviceInfo", localBundle))
    {
      str = argListFromBundle(localBundle);
      if (str == null)
      {
        Log.d(TAG, "onGetDeviceInfo serialize device info failed!");
        i = AirControl.replyEvent(paramEvent.id, 10002, "serialize device info failed", "");
      }
    }
    while (true)
    {
      Log.d(TAG, "onGetDeviceInfo replyEvent = " + i);
      return;
      Log.d(TAG, "onGetDeviceInfo ok! " + str);
      i = AirControl.replyEvent(paramEvent.id, 0, "ok", str);
      continue;
      Log.d(TAG, "onGetDeviceInfo device info not found!");
      i = AirControl.replyEvent(paramEvent.id, 10001, "device info not found", "");
    }
  }

  private void onGetUserInfo(Event paramEvent)
  {
    Log.d(TAG, "onGetUserInfo");
    Bundle localBundle = new Bundle();
    String str;
    int i;
    if (_dispatchMessage("getUserInfo", localBundle))
    {
      str = argListFromBundle(localBundle);
      if (str == null)
      {
        Log.d(TAG, "onGetUserInfo serialize user info failed!");
        i = AirControl.replyEvent(paramEvent.id, 10002, "serialize user info failed", "");
      }
    }
    while (true)
    {
      Log.d(TAG, "onGetUserInfo replyEvent = " + i);
      return;
      Log.d(TAG, "onGetUserInfo ok! " + str);
      i = AirControl.replyEvent(paramEvent.id, 0, "ok", str);
      continue;
      Log.d(TAG, "onGetUserInfo user info not found!");
      i = AirControl.replyEvent(paramEvent.id, 10001, "user info not found", "");
    }
  }

  private void onKeyClickEvent(Event paramEvent)
  {
    String str1 = (String)paramEvent.args.get("key");
    if (TextUtils.isEmpty(str1))
      Log.d(TAG, "invalid key - (empty)");
    int i;
    do
    {
      return;
      if (!str1.substring(0, 3).equals("VK_"))
      {
        Log.d(TAG, "invalid key - " + str1);
        return;
      }
      String str2 = str1.substring(3);
      i = KeyMap.getKeyCode(str2);
      if (i < 0)
      {
        Log.d(TAG, "unknown key - " + str2);
        return;
      }
      Log.d(TAG, "send key - " + str2 + " : " + i);
    }
    while ((_dispatchKeyEventEvent(i, 0)) || (this._keySender.sendKey(i, 0)));
    this._keySenderFallBack.sendKey(i, 0);
  }

  private void onPlayVideo(Event paramEvent)
  {
    Log.d(TAG, "onPlayVideo");
    Bundle localBundle = new Bundle();
    Iterator localIterator = paramEvent.args.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localBundle.putString(str, (String)paramEvent.args.get(str));
    }
    if (_dispatchMessage("playVideo", localBundle))
    {
      Log.d(TAG, "onPlayVideo ok!");
      return;
    }
    Log.d(TAG, "onPlayVideo failed!");
  }

  private void onShowTip(Event paramEvent)
  {
    Log.d(TAG, "onShowTip");
    Bundle localBundle = new Bundle();
    Iterator localIterator = paramEvent.args.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localBundle.putString(str, (String)paramEvent.args.get(str));
    }
    if (_dispatchMessage("showTip", localBundle))
    {
      Log.d(TAG, "onShowTip ok! ");
      return;
    }
    Log.d(TAG, "onShowTip failed!!!");
  }

  private void onTextEvent(Event paramEvent)
  {
    String str = (String)paramEvent.args.get("text");
    if (TextUtils.isEmpty(str))
      Log.d(TAG, "invalid text - [empty]");
    do
    {
      return;
      Log.d(TAG, "send text - " + str);
    }
    while ((_dispatchTextEvent(str)) || (this._keySender.sendString(str)));
    this._keySenderFallBack.sendString(str);
  }

  private void processCommonEvent(Event paramEvent)
  {
    Log.d(TAG, "processCommonEvent " + paramEvent.type.name() + " / " + paramEvent.commandStr);
    Bundle localBundle = new Bundle();
    Iterator localIterator = paramEvent.args.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str2 = (String)localIterator.next();
      localBundle.putString(str2, (String)paramEvent.args.get(str2));
    }
    if (!paramEvent.needReply)
    {
      _dispatchMessage(paramEvent.type.name(), localBundle);
      return;
    }
    String str1;
    int i;
    if (_dispatchMessage(paramEvent.type.name(), localBundle))
    {
      str1 = argListFromBundle(localBundle);
      if (str1 == null)
      {
        Log.d(TAG, "processCommonEvent serialize result failed!");
        i = AirControl.replyEvent(paramEvent.id, 10002, "serialize result failed", "");
      }
    }
    while (true)
    {
      Log.d(TAG, "processCommonEvent  " + paramEvent.type.name() + " / " + paramEvent.commandStr + " replyEvent = " + i);
      return;
      Log.d(TAG, "processCommonEvent ok! " + str1);
      i = AirControl.replyEvent(paramEvent.id, 0, "ok", str1);
      continue;
      Log.d(TAG, "processCommonEvent failed!");
      i = AirControl.replyEvent(paramEvent.id, 10001, "failed execution command!", "");
    }
  }

  public static void registerAirCommandListener(IAirCommandListener paramIAirCommandListener)
  {
    _listener.add(0, paramIAirCommandListener);
  }

  public static void shutdown()
  {
    if (_inst == null)
      return;
    _inst._shutdown();
    _inst = null;
  }

  public static void startUp(Context paramContext, InitParams paramInitParams)
  {
    if (_inst == null)
      _inst = new AirControlHost(paramContext, paramInitParams);
  }

  public static void unregisterAirCommandListener(IAirCommandListener paramIAirCommandListener)
  {
    _listener.remove(paramIAirCommandListener);
  }

  public static class InitParams
  {
    public boolean allowMultiIp = true;
    public String deviceId;
    public String deviceName;
    public ArrayList<Pair<String, String>> extConfig;
    public String mac;
    public String url;

    public String toString()
    {
      return "InitParams{deviceId='" + this.deviceId + '\'' + ", deviceName='" + this.deviceName + '\'' + ", mac='" + this.mac + '\'' + ", url='" + this.url + '\'' + ", allowMultiIp=" + this.allowMultiIp + ", extConfig=" + this.extConfig + '}';
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.remote_key.AirControlHost
 * JD-Core Version:    0.6.2
 */