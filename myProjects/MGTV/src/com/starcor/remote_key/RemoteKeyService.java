package com.starcor.remote_key;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class RemoteKeyService extends Service
{
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onCreate()
  {
    super.onCreate();
  }

  public void onStart(Intent paramIntent, int paramInt)
  {
    super.onStart(paramIntent, paramInt);
    startAirControlHost(paramIntent);
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    startAirControlHost(paramIntent);
    return super.onStartCommand(paramIntent, paramInt1, paramInt2);
  }

  void startAirControlHost(Intent paramIntent)
  {
    AirControlHost.InitParams localInitParams = new AirControlHost.InitParams();
    localInitParams.deviceId = "00:00:00:00:00:00";
    localInitParams.deviceName = "TestDevice";
    localInitParams.mac = "00:00:00:00:00:00";
    localInitParams.url = "http://42.121.112.87/nn_cms_weixin/nn_cms_view/mgtv/n31_a.php";
    String str1 = paramIntent.getStringExtra("device_id");
    String str2 = paramIntent.getStringExtra("device_name");
    String str3 = paramIntent.getStringExtra("mac");
    String str4 = paramIntent.getStringExtra("url");
    boolean bool = paramIntent.getBooleanExtra("allowMultiIp", true);
    Bundle localBundle = paramIntent.getBundleExtra("extConfig");
    if (!TextUtils.isEmpty(str1))
      localInitParams.deviceId = str1;
    if (!TextUtils.isEmpty(str2))
      localInitParams.deviceName = str2;
    if (!TextUtils.isEmpty(str3))
      localInitParams.mac = str3;
    if (!TextUtils.isEmpty(str4))
      localInitParams.url = str4;
    localInitParams.allowMultiIp = bool;
    if (localBundle != null)
    {
      localInitParams.extConfig = new ArrayList();
      Iterator localIterator = localBundle.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str5 = (String)localIterator.next();
        String str6 = localBundle.getString(str5);
        localInitParams.extConfig.add(Pair.create(str5, str6));
      }
    }
    AirControlHost.startUp(this, localInitParams);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.remote_key.RemoteKeyService
 * JD-Core Version:    0.6.2
 */