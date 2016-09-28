package com.starcor.settings.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.starcor.settings.utils.Debug;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class WifiHelper
{
  static final int SECURITY_EAP = 3;
  static final int SECURITY_NONE = 0;
  static final int SECURITY_PSK = 2;
  static final int SECURITY_WEP = 1;
  public static final String WEP = "WEP";
  public static final String WPA2_PSK = "WPA2-PSK";
  public static final String WPA_PSK = "WPA-PSK";
  public static final String WPA_WPA2_PSK = "WPA/WPA2-PSK";
  private static WifiHelper mInstance;
  boolean isStarted = false;
  private Looper looper;
  private WifiReceiverCallback mCallback;
  private Context mContext;
  private Object mLock = new Object();
  private List<WifiConfiguration> mWifiConfiguration;
  private WifiInfo mWifiInfo;
  private WifiManager.WifiLock mWifiLock;
  private WifiManager mWifiManager;
  private List<ScanResult> mWifiResultList;
  private Runnable scanWifi = new Runnable()
  {
    public void run()
    {
      Debug.d("lx", "wifiManager.startScan()");
      if (WifiHelper.this.mWifiManager.startScan());
      while (true)
      {
        synchronized (WifiHelper.this.mLock)
        {
          try
          {
            WifiHelper.this.mLock.wait(15000L);
            WifiHelper.this.wifiScaner.removeCallbacks(WifiHelper.this.scanWifi);
            if (!WifiHelper.this.stopScan)
              WifiHelper.this.wifiScaner.postDelayed(WifiHelper.this.scanWifi, 1000L);
            return;
          }
          catch (InterruptedException localInterruptedException)
          {
            localInterruptedException.printStackTrace();
            continue;
          }
        }
        Debug.d("lx", "s tartScan fail");
      }
    }
  };
  private boolean stopScan = true;
  private BroadcastReceiver wifiReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (WifiHelper.this.mCallback != null)
      {
        if (!"android.net.wifi.SCAN_RESULTS".equals(paramAnonymousIntent.getAction()))
          break label102;
        Debug.i("lx", "onReceive---onScanOver");
        WifiHelper.this.mWifiResultList = WifiHelper.this.mWifiManager.getScanResults();
        WifiHelper.this.mWifiConfiguration = WifiHelper.this.mWifiManager.getConfiguredNetworks();
        WifiHelper.this.mCallback.onScanOver();
      }
      synchronized (WifiHelper.this.mLock)
      {
        label102: 
        do
          while (true)
          {
            WifiHelper.this.mLock.notify();
            return;
            if ("android.net.wifi.RSSI_CHANGED".equals(paramAnonymousIntent.getAction()))
            {
              int k = WifiManager.calculateSignalLevel(paramAnonymousIntent.getIntExtra("newRssi", -100), 5);
              WifiHelper.this.mCallback.onRssiChanged(k);
              Debug.d("lx", "onReceive---onRssiChanged");
            }
            else
            {
              if (!"android.net.wifi.WIFI_STATE_CHANGED".equals(paramAnonymousIntent.getAction()))
                break;
              int i = paramAnonymousIntent.getIntExtra("wifi_state", 4);
              int j = paramAnonymousIntent.getIntExtra("previous_wifi_state", 4);
              WifiHelper.this.mCallback.onWifiStateChanged(i, j);
              Debug.d("lx", "onReceive---onWifiStatusChange");
            }
          }
        while (!"android.net.wifi.STATE_CHANGE".equals(paramAnonymousIntent.getAction()));
        NetworkInfo localNetworkInfo = (NetworkInfo)paramAnonymousIntent.getParcelableExtra("networkInfo");
        WifiHelper.this.mCallback.onNetworkStateChanged(localNetworkInfo);
        Debug.d("lx", "onReceive---NETWORK_STATE_CHANGED_ACTION---");
      }
    }
  };
  private Handler wifiScaner;

  public WifiHelper(Context paramContext)
  {
    this.mContext = paramContext;
    this.mWifiManager = ((WifiManager)paramContext.getSystemService("wifi"));
    this.mWifiInfo = this.mWifiManager.getConnectionInfo();
  }

  public static Object getDeclaredField(Object paramObject, String paramString)
    throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException
  {
    Field localField = paramObject.getClass().getDeclaredField(paramString);
    localField.setAccessible(true);
    return localField.get(paramObject);
  }

  public static Object getField(Object paramObject, String paramString)
    throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException
  {
    return paramObject.getClass().getField(paramString).get(paramObject);
  }

  public static void setDNS(InetAddress paramInetAddress, WifiConfiguration paramWifiConfiguration)
    throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException
  {
    Object localObject = getField(paramWifiConfiguration, "linkProperties");
    if (localObject == null)
      return;
    ArrayList localArrayList = (ArrayList)getDeclaredField(localObject, "mDnses");
    localArrayList.clear();
    localArrayList.add(paramInetAddress);
  }

  public static void setEnumField(Object paramObject, String paramString1, String paramString2)
    throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException
  {
    Field localField = paramObject.getClass().getField(paramString2);
    localField.set(paramObject, Enum.valueOf(localField.getType(), paramString1));
  }

  public static void setGateway(InetAddress paramInetAddress, WifiConfiguration paramWifiConfiguration)
    throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException
  {
    Object localObject1 = getField(paramWifiConfiguration, "linkProperties");
    if (localObject1 == null)
      return;
    Object localObject2 = Class.forName("android.net.RouteInfo").getConstructor(new Class[] { InetAddress.class }).newInstance(new Object[] { paramInetAddress });
    ArrayList localArrayList = (ArrayList)getDeclaredField(localObject1, "mRoutes");
    localArrayList.clear();
    localArrayList.add(localObject2);
  }

  public static void setGateway0(InetAddress paramInetAddress, WifiConfiguration paramWifiConfiguration)
    throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InstantiationException, InvocationTargetException
  {
    Object localObject = getField(paramWifiConfiguration, "linkProperties");
    if (localObject == null)
      return;
    ArrayList localArrayList = (ArrayList)getDeclaredField(localObject, "mGateways");
    localArrayList.clear();
    localArrayList.add(paramInetAddress);
  }

  public static void setIpAddress(InetAddress paramInetAddress, int paramInt, WifiConfiguration paramWifiConfiguration)
    throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InstantiationException, InvocationTargetException
  {
    Object localObject1 = getField(paramWifiConfiguration, "linkProperties");
    if (localObject1 == null)
      return;
    Class localClass = Class.forName("android.net.LinkAddress");
    Class[] arrayOfClass = new Class[2];
    arrayOfClass[0] = InetAddress.class;
    arrayOfClass[1] = Integer.TYPE;
    Constructor localConstructor = localClass.getConstructor(arrayOfClass);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramInetAddress;
    arrayOfObject[1] = Integer.valueOf(paramInt);
    Object localObject2 = localConstructor.newInstance(arrayOfObject);
    ArrayList localArrayList = (ArrayList)getDeclaredField(localObject1, "mLinkAddresses");
    localArrayList.clear();
    localArrayList.add(localObject2);
  }

  public static void setIpAssignment(String paramString, WifiConfiguration paramWifiConfiguration)
    throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException
  {
    setEnumField(paramWifiConfiguration, paramString, "ipAssignment");
  }

  public int GetNetworkId()
  {
    return this.mWifiManager.getConnectionInfo().getNetworkId();
  }

  public void acquireWifiLock()
  {
    this.mWifiLock.acquire();
  }

  public void closeWifi()
  {
    if (!this.mWifiManager.isWifiEnabled())
      this.mWifiManager.setWifiEnabled(false);
  }

  public void connectConfiguration(int paramInt)
  {
    if (paramInt > this.mWifiConfiguration.size())
      return;
    this.mWifiManager.enableNetwork(((WifiConfiguration)this.mWifiConfiguration.get(paramInt)).networkId, true);
  }

  public boolean connectNetwork(WifiConfiguration paramWifiConfiguration)
  {
    this.mWifiManager.saveConfiguration();
    int i = this.mWifiManager.addNetwork(paramWifiConfiguration);
    return this.mWifiManager.enableNetwork(i, true);
  }

  public void createWifiLock()
  {
    this.mWifiLock = this.mWifiManager.createWifiLock("Wifi_lock");
  }

  public void disconnectWifi(int paramInt)
  {
    this.mWifiManager.disconnect();
    this.mWifiManager.disableNetwork(paramInt);
  }

  public String getBSSID()
  {
    return this.mWifiManager.getConnectionInfo().getBSSID();
  }

  public List<WifiConfiguration> getConfiguration()
  {
    if (this.mWifiConfiguration == null)
      return this.mWifiManager.getConfiguredNetworks();
    return this.mWifiConfiguration;
  }

  public DhcpInfo getDhcpInfo()
  {
    return this.mWifiManager.getDhcpInfo();
  }

  public String getEncryptionType(String paramString)
  {
    String str = "";
    if ((paramString.contains("WPA-PSK")) && (paramString.contains("WPA2-PSK")))
      str = "WPA/WPA2-PSK";
    do
    {
      return str;
      if (paramString.contains("WPA2-PSK"))
        return "WPA2-PSK";
      if (paramString.contains("WPA-PSK"))
        return "WPA-PSK";
    }
    while (!paramString.contains("WEP"));
    return "WEP";
  }

  public int getIPAddress()
  {
    return this.mWifiManager.getConnectionInfo().getIpAddress();
  }

  public String getMacAddress()
  {
    return this.mWifiManager.getConnectionInfo().getMacAddress();
  }

  public String getSignalStrengthString(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return "";
    case 0:
      return "鏃�";
    case 1:
      return "寮�";
    case 2:
      return "杈冨急";
    case 3:
      return "杈冨己";
    case 4:
    }
    return "寮�";
  }

  public WifiInfo getWifiInfo()
  {
    return this.mWifiManager.getConnectionInfo();
  }

  public String getWifiInfoString()
  {
    return this.mWifiManager.getConnectionInfo().toString();
  }

  public List<ScanResult> getWifiResultList()
  {
    return this.mWifiResultList;
  }

  public int getWifiStatus()
  {
    return this.mWifiManager.getWifiState();
  }

  public String lookUpScan()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0; ; i++)
    {
      if (i >= this.mWifiResultList.size())
        return localStringBuilder.toString();
      localStringBuilder.append("Index_" + Integer.valueOf(i + 1).toString() + ":");
      localStringBuilder.append(((ScanResult)this.mWifiResultList.get(i)).toString());
      localStringBuilder.append("\n");
    }
  }

  public void openWifi()
  {
    if (!this.mWifiManager.isWifiEnabled())
      this.mWifiManager.setWifiEnabled(true);
  }

  public void releaseWifiLock()
  {
    if (this.mWifiLock.isHeld())
      this.mWifiLock.acquire();
  }

  public void setWifiConfigStatic(WifiConfiguration paramWifiConfiguration, String paramString1, String paramString2, String paramString3)
  {
    try
    {
      setIpAssignment("STATIC", paramWifiConfiguration);
      setIpAddress(InetAddress.getByName(paramString1), 24, paramWifiConfiguration);
      setGateway(InetAddress.getByName(paramString2), paramWifiConfiguration);
      setDNS(InetAddress.getByName(paramString3), paramWifiConfiguration);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void setWifiReceiverCallback(WifiReceiverCallback paramWifiReceiverCallback)
  {
    this.mCallback = paramWifiReceiverCallback;
  }

  public void startScanWifi()
  {
    HandlerThread localHandlerThread = new HandlerThread("HandlerThread");
    localHandlerThread.start();
    this.looper = localHandlerThread.getLooper();
    this.wifiScaner = new Handler(this.looper);
    this.wifiScaner.post(this.scanWifi);
    this.stopScan = false;
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
    localIntentFilter.addAction("android.net.wifi.SCAN_RESULTS");
    localIntentFilter.addAction("android.net.wifi.NETWORK_IDS_CHANGED");
    localIntentFilter.addAction("android.net.wifi.supplicant.STATE_CHANGE");
    localIntentFilter.addAction("android.net.wifi.STATE_CHANGE");
    localIntentFilter.addAction("android.net.wifi.RSSI_CHANGED");
    this.mContext.registerReceiver(this.wifiReceiver, localIntentFilter);
  }

  public void stopScanWifi()
  {
    this.stopScan = true;
    this.looper.quit();
    this.wifiScaner.removeCallbacks(this.scanWifi);
    try
    {
      this.mContext.unregisterReceiver(this.wifiReceiver);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public static abstract interface WifiReceiverCallback
  {
    public abstract void onNetworkStateChanged(NetworkInfo paramNetworkInfo);

    public abstract void onRssiChanged(int paramInt);

    public abstract void onScanOver();

    public abstract void onWifiStateChanged(int paramInt1, int paramInt2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.network.WifiHelper
 * JD-Core Version:    0.6.2
 */