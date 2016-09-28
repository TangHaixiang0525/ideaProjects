package com.starcor.settings;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ResolveInfo.DisplayNameComparator;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.starcor.setting.service.ISettingService;
import com.starcor.setting.service.ISettingService.Stub;
import com.starcor.setting.service.NetworkStateInfo;
import com.starcor.setting.service.NetworkStateInfo.NetworkState;
import com.starcor.settings.date.DateConfigFragment;
import com.starcor.settings.network.NetworkConfigFragment;
import com.starcor.settings.network.NetworkConfigFragment.NetworkType;
import com.starcor.settings.network.WifiHelper;
import com.starcor.settings.network.WifiHelper.WifiReceiverCallback;
import com.starcor.settings.personal.PersonalFragment;
import com.starcor.settings.redisplayrate.RedisplayConfigFragment;
import com.starcor.settings.upgrade.UpgradeFragment;
import com.starcor.settings.utils.Debug;
import com.starcor.settings.utils.Tools;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

public class SettingActivity extends FragmentActivity
{
  private ServiceConnection connection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      SettingActivity.this.settingService = ISettingService.Stub.asInterface(paramAnonymousIBinder);
      try
      {
        System.out.println(SettingActivity.this.settingService.getNetworkStateInfo().state.toString() + "----Bind Success:" + SettingActivity.this.settingService);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        System.out.println("RemoteException---" + localRemoteException.getMessage());
        localRemoteException.printStackTrace();
      }
    }

    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
    }
  };
  private TextView displayConfig;
  private TextView fileManager;
  WifiHelper mWifiHelper;
  private TextView networkConfig;
  private TextView personal;
  private ISettingService settingService = null;
  private TextView systemSetting;
  private TextView timeDate;
  private TextView upgrade;
  private ImageView wifiState;

  private void initView()
  {
    this.wifiState = ((ImageView)findViewById(R.id.wifi_state));
    this.networkConfig = ((TextView)findViewById(R.id.network_config));
    this.displayConfig = ((TextView)findViewById(R.id.display_config));
    this.timeDate = ((TextView)findViewById(R.id.time_date));
    this.personal = ((TextView)findViewById(R.id.personal));
    this.upgrade = ((TextView)findViewById(R.id.upgrade));
    this.systemSetting = ((TextView)findViewById(R.id.network_setting));
    this.fileManager = ((TextView)findViewById(R.id.file_manager));
    ((TextView)findViewById(R.id.set_cn)).setTextSize(0, Tools.OperationHeight(30));
    ((TextView)findViewById(R.id.set_en)).setTextSize(0, Tools.OperationHeight(20));
    this.networkConfig.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        SettingActivity.this.setTextSelected(paramAnonymousView);
        Tools.showFragment(SettingActivity.this, NetworkConfigFragment.class);
      }
    });
    this.displayConfig.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        SettingActivity.this.setTextSelected(paramAnonymousView);
        Tools.showFragment(SettingActivity.this, RedisplayConfigFragment.class);
      }
    });
    this.timeDate.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        SettingActivity.this.setTextSelected(paramAnonymousView);
        Tools.showFragment(SettingActivity.this, DateConfigFragment.class);
      }
    });
    this.personal.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        SettingActivity.this.setTextSelected(paramAnonymousView);
        Tools.showFragment(SettingActivity.this, PersonalFragment.class);
      }
    });
    this.upgrade.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        SettingActivity.this.setTextSelected(paramAnonymousView);
        Tools.showFragment(SettingActivity.this, UpgradeFragment.class);
      }
    });
    this.systemSetting.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        try
        {
          SettingActivity.this.settingService.showExternalNetworkSetting();
          return;
        }
        catch (RemoteException localRemoteException)
        {
          localRemoteException.printStackTrace();
        }
      }
    });
    this.fileManager.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Intent localIntent = new Intent();
        localIntent.setClassName("com.fb.FileBrower", "com.fb.FileBrower.FileBrower");
        try
        {
          SettingActivity.this.startActivity(localIntent);
          return;
        }
        catch (ActivityNotFoundException localActivityNotFoundException1)
        {
          localIntent.setClassName("com.estrongs.android.pop", "com.estrongs.android.pop.view.FileExplorerActivity");
          try
          {
            SettingActivity.this.startActivity(localIntent);
            return;
          }
          catch (ActivityNotFoundException localActivityNotFoundException2)
          {
            Debug.w("Setting", localActivityNotFoundException1.getMessage(), localActivityNotFoundException1);
          }
        }
      }
    });
    this.networkConfig.setSelected(true);
    Tools.showFragment(this, RedisplayConfigFragment.class);
    this.mWifiHelper = new WifiHelper(this);
    this.mWifiHelper.startScanWifi();
    this.mWifiHelper.setWifiReceiverCallback(new WifiHelper.WifiReceiverCallback()
    {
      public void onNetworkStateChanged(NetworkInfo paramAnonymousNetworkInfo)
      {
        int i = WifiManager.calculateSignalLevel(SettingActivity.this.mWifiHelper.getWifiInfo().getRssi(), 5);
        SettingActivity.this.setWifiState(i);
      }

      public void onRssiChanged(int paramAnonymousInt)
      {
        SettingActivity.this.setWifiState(paramAnonymousInt);
      }

      public void onScanOver()
      {
      }

      public void onWifiStateChanged(int paramAnonymousInt1, int paramAnonymousInt2)
      {
      }
    });
    setWifiState(WifiManager.calculateSignalLevel(this.mWifiHelper.getWifiInfo().getRssi(), 5));
  }

  private boolean isWirelessConnected()
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)getSystemService("connectivity")).getActiveNetworkInfo();
    if (localNetworkInfo == null);
    while (getNetworkType(localNetworkInfo.getType()) != NetworkConfigFragment.NetworkType.WIRELESS)
      return false;
    return true;
  }

  private void loadApplications()
  {
    PackageManager localPackageManager = getPackageManager();
    Intent localIntent = new Intent("android.intent.action.MAIN", null);
    localIntent.addCategory("android.intent.category.LAUNCHER");
    List localList = localPackageManager.queryIntentActivities(localIntent, 0);
    Collections.sort(localList, new ResolveInfo.DisplayNameComparator(localPackageManager));
    int i;
    if (localList != null)
      i = localList.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return;
      AppInfo localAppInfo = new AppInfo();
      ResolveInfo localResolveInfo = (ResolveInfo)localList.get(j);
      localAppInfo.title = localResolveInfo.loadLabel(localPackageManager);
      localAppInfo.setActivity(new ComponentName(localResolveInfo.activityInfo.applicationInfo.packageName, localResolveInfo.activityInfo.name), 270532608);
      localAppInfo.flags = localResolveInfo.activityInfo.applicationInfo.flags;
      localAppInfo.icon = localResolveInfo.activityInfo.loadIcon(localPackageManager);
      localAppInfo.packageName = localResolveInfo.activityInfo.applicationInfo.packageName;
      if (localAppInfo.title.equals("搴旂敤瀹夎"))
        Log.v("lx", "搴旂敤瀹夎--packageName=" + localAppInfo.packageName + ", name=" + localResolveInfo.activityInfo.name);
    }
  }

  private void setTextSelected(View paramView)
  {
    this.networkConfig.setSelected(false);
    this.displayConfig.setSelected(false);
    this.timeDate.setSelected(false);
    this.personal.setSelected(false);
    this.upgrade.setSelected(false);
    paramView.setSelected(true);
  }

  private void setWifiState(int paramInt)
  {
    if (isWirelessConnected())
    {
      this.wifiState.setVisibility(0);
      this.wifiState.setImageResource(paramInt + R.drawable.wifi0);
      return;
    }
    this.wifiState.setVisibility(4);
  }

  public NetworkConfigFragment.NetworkType getNetworkType(int paramInt)
  {
    switch (paramInt)
    {
    default:
      throw new IllegalArgumentException("error type, the type is: " + paramInt);
    case 9:
      return NetworkConfigFragment.NetworkType.WIRED;
    case 1:
      return NetworkConfigFragment.NetworkType.WIRELESS;
    case 14:
    }
    return NetworkConfigFragment.NetworkType.BROADBAND_DIAL_UP;
  }

  public ISettingService getSettingService()
  {
    return this.settingService;
  }

  public void onBackPressed()
  {
    DateConfigFragment localDateConfigFragment = (DateConfigFragment)getSupportFragmentManager().findFragmentByTag(DateConfigFragment.TAG);
    if (localDateConfigFragment == null)
      finish();
    while (!localDateConfigFragment.onBackPressed())
      return;
    finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_setting);
    initView();
    bindService(new Intent(ISettingService.class.getName()), this.connection, 1);
  }

  protected void onDestroy()
  {
    this.mWifiHelper.stopScanWifi();
    unbindService(this.connection);
    super.onDestroy();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.SettingActivity
 * JD-Core Version:    0.6.2
 */