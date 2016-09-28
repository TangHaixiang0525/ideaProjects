package com.starcor.settings.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.NetworkInfo.State;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.starcor.settings.R.drawable;
import com.starcor.settings.R.id;
import com.starcor.settings.R.layout;
import com.starcor.settings.utils.Debug;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class WirelessConfigFragment extends Fragment
{
  public static final String TAG = WirelessConfigFragment.class.getSimpleName();
  private static ArrayList<String> states = new ArrayList();
  private View base;
  private ArrayList<WifiItemInfo> itemInfos = new ArrayList();
  private String mBSSID = "";
  private ListView mListView;
  private WifiHelper mWifiHelper;
  private TextView scanWireless;

  static
  {
    int i = NetworkInfo.DetailedState.values().length;
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return;
      states.add(NetworkInfo.DetailedState.values()[j].toString());
    }
  }

  private boolean isWiredConnected()
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)getActivity().getSystemService("connectivity");
    NetworkConfigFragment localNetworkConfigFragment = (NetworkConfigFragment)getFragmentManager().findFragmentByTag(NetworkConfigFragment.TAG);
    NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
    if (localNetworkInfo == null);
    while (localNetworkConfigFragment.getNetworkType(localNetworkInfo.getType()) != NetworkConfigFragment.NetworkType.WIRED)
      return false;
    return true;
  }

  private void setListShown(boolean paramBoolean)
  {
    TextView localTextView = this.scanWireless;
    int i;
    ListView localListView;
    int j;
    if (paramBoolean)
    {
      i = 8;
      localTextView.setVisibility(i);
      localListView = this.mListView;
      j = 0;
      if (!paramBoolean)
        break label43;
    }
    while (true)
    {
      localListView.setVisibility(j);
      return;
      i = 0;
      break;
      label43: j = 8;
    }
  }

  public static void showAdd(FragmentManager paramFragmentManager)
  {
    WirelessConfigFragment localWirelessConfigFragment = new WirelessConfigFragment();
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    localFragmentTransaction.add(R.id.network_fragment_container, localWirelessConfigFragment, TAG);
    localFragmentTransaction.addToBackStack(null);
    localFragmentTransaction.commit();
  }

  public static void showReplace(FragmentManager paramFragmentManager)
  {
    WirelessConfigFragment localWirelessConfigFragment = new WirelessConfigFragment();
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    localFragmentTransaction.replace(R.id.network_fragment_container, localWirelessConfigFragment, TAG);
    localFragmentTransaction.addToBackStack(null);
    localFragmentTransaction.commit();
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.base = paramLayoutInflater.inflate(R.layout.fragment_wireless_config, paramViewGroup, false);
    this.scanWireless = ((TextView)this.base.findViewById(R.id.scan_wireless));
    this.mListView = ((ListView)this.base.findViewById(R.id.wireless_network_list));
    this.mListView.setAdapter(new BaseAdapter()
    {
      public int getCount()
      {
        return WirelessConfigFragment.this.itemInfos.size();
      }

      public WirelessConfigFragment.WifiItemInfo getItem(int paramAnonymousInt)
      {
        return (WirelessConfigFragment.WifiItemInfo)WirelessConfigFragment.this.itemInfos.get(paramAnonymousInt);
      }

      public long getItemId(int paramAnonymousInt)
      {
        return paramAnonymousInt;
      }

      public View getView(int paramAnonymousInt, View paramAnonymousView, ViewGroup paramAnonymousViewGroup)
      {
        if (paramAnonymousView == null)
          paramAnonymousView = View.inflate(WirelessConfigFragment.this.getActivity(), R.layout.wireless_list_item, null);
        ImageView localImageView = (ImageView)paramAnonymousView.findViewById(R.id.signal_strength);
        TextView localTextView1 = (TextView)paramAnonymousView.findViewById(R.id.wireless_name);
        TextView localTextView2 = (TextView)paramAnonymousView.findViewById(R.id.wireless_status);
        WirelessConfigFragment.WifiItemInfo localWifiItemInfo = (WirelessConfigFragment.WifiItemInfo)WirelessConfigFragment.this.itemInfos.get(paramAnonymousInt);
        localImageView.setImageResource(R.drawable.wifi5 + localWifiItemInfo.level);
        localTextView1.setText(localWifiItemInfo.name);
        localTextView2.setText(localWifiItemInfo.state);
        return paramAnonymousView;
      }
    });
    this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        WirelessConfigFragment.WifiItemInfo localWifiItemInfo = (WirelessConfigFragment.WifiItemInfo)((BaseAdapter)paramAnonymousAdapterView.getAdapter()).getItem(paramAnonymousInt);
        WirelessDialogFragment localWirelessDialogFragment = new WirelessDialogFragment();
        localWirelessDialogFragment.show(WirelessConfigFragment.this.getFragmentManager(), "dialog");
        localWirelessDialogFragment.setWifiInfo(localWifiItemInfo);
        WirelessConfigFragment.this.mBSSID = localWifiItemInfo.BSSID;
      }
    });
    setListShown(false);
    this.mWifiHelper = new WifiHelper(getActivity());
    this.mWifiHelper.startScanWifi();
    this.mWifiHelper.setWifiReceiverCallback(new WifiHelper.WifiReceiverCallback()
    {
      public void onNetworkStateChanged(NetworkInfo paramAnonymousNetworkInfo)
      {
        Debug.w("luo", "--network info--status:" + paramAnonymousNetworkInfo.getState().toString() + "--\n" + paramAnonymousNetworkInfo.toString());
        ((TextView)((NetworkConfigFragment)WirelessConfigFragment.this.getFragmentManager().findFragmentByTag(NetworkConfigFragment.TAG)).base.findViewById(R.id.wireless));
        String str = new StringBuilder(String.valueOf(paramAnonymousNetworkInfo.getState().toString())).append("-").toString() + paramAnonymousNetworkInfo.getDetailedState().toString();
        Iterator localIterator = WirelessConfigFragment.this.itemInfos.iterator();
        while (true)
        {
          if (!localIterator.hasNext())
          {
            Collections.sort(WirelessConfigFragment.this.itemInfos, new WirelessConfigFragment.WifiItemInfo(WirelessConfigFragment.this));
            ((BaseAdapter)WirelessConfigFragment.this.mListView.getAdapter()).notifyDataSetChanged();
            if (!WirelessConfigFragment.this.isWiredConnected())
              WirelessConfigFragment.this.scanWireless.setText("姝ｅ湪鎵弿鏃犵嚎缃戠粶...");
            return;
          }
          WirelessConfigFragment.WifiItemInfo localWifiItemInfo = (WirelessConfigFragment.WifiItemInfo)localIterator.next();
          if (localWifiItemInfo.BSSID.equals(WirelessConfigFragment.this.mBSSID))
            localWifiItemInfo.state = str;
        }
      }

      public void onRssiChanged(int paramAnonymousInt)
      {
      }

      public void onScanOver()
      {
        List localList = WirelessConfigFragment.this.mWifiHelper.getWifiResultList();
        if ((localList == null) || (localList.size() == 0))
          return;
        WirelessConfigFragment.this.itemInfos.clear();
        Iterator localIterator = localList.iterator();
        while (true)
        {
          if (!localIterator.hasNext())
          {
            Collections.sort(WirelessConfigFragment.this.itemInfos, new WirelessConfigFragment.WifiItemInfo(WirelessConfigFragment.this));
            ((BaseAdapter)WirelessConfigFragment.this.mListView.getAdapter()).notifyDataSetChanged();
            WirelessConfigFragment.this.setListShown(true);
            return;
          }
          ScanResult localScanResult = (ScanResult)localIterator.next();
          int i = WifiManager.calculateSignalLevel(localScanResult.level, 5);
          String str1 = localScanResult.SSID;
          String str2 = localScanResult.BSSID;
          ConnectivityManager localConnectivityManager = (ConnectivityManager)WirelessConfigFragment.this.getActivity().getSystemService("connectivity");
          try
          {
            int k = localConnectivityManager.getActiveNetworkInfo().getType();
            j = k;
            if ((j == 1) && (str2.equals(WirelessConfigFragment.this.mWifiHelper.getBSSID())))
            {
              str3 = "宸茶繛鎺�";
              String str4 = localScanResult.capabilities;
              WirelessConfigFragment.this.itemInfos.add(new WirelessConfigFragment.WifiItemInfo(WirelessConfigFragment.this, i, str1, str2, str3, str4));
            }
          }
          catch (NullPointerException localNullPointerException)
          {
            while (true)
            {
              int j = 0;
              continue;
              String str3 = "鏈繛鎺�";
            }
          }
        }
      }

      public void onWifiStateChanged(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        System.out.println();
        ((TextView)((NetworkConfigFragment)WirelessConfigFragment.this.getFragmentManager().findFragmentByTag(NetworkConfigFragment.TAG)).base.findViewById(R.id.wired));
        switch (paramAnonymousInt1)
        {
        default:
          return;
        case 0:
          new StringBuilder(String.valueOf("")).append("WIFI_STATE_DISABLING").toString();
          return;
        case 1:
          new StringBuilder(String.valueOf("")).append("WIFI_STATE_DISABLED").toString();
          return;
        case 2:
          new StringBuilder(String.valueOf("")).append("WIFI_STATE_ENABLING").toString();
          return;
        case 3:
          new StringBuilder(String.valueOf("")).append("WIFI_STATE_ENABLED").toString();
          return;
        case 4:
        }
        new StringBuilder(String.valueOf("")).append("WIFI_STATE_UNKNOWN").toString();
      }
    });
    if (isWiredConnected())
      this.scanWireless.setText("宸茶繛鎺ユ湁绾跨綉缁�");
    return this.base;
  }

  public void onDestroyView()
  {
    this.mWifiHelper.stopScanWifi();
    super.onDestroyView();
  }

  public class WifiItemInfo
    implements Comparator<WifiItemInfo>
  {
    public String BSSID;
    public String capabilities;
    public int level;
    public String name;
    public String state;

    public WifiItemInfo()
    {
    }

    public WifiItemInfo(int paramString1, String paramString2, String paramString3, String paramString4, String arg6)
    {
      this.level = paramString1;
      this.name = paramString2;
      this.BSSID = paramString3;
      this.state = paramString4;
      Object localObject;
      this.capabilities = localObject;
    }

    public int compare(WifiItemInfo paramWifiItemInfo1, WifiItemInfo paramWifiItemInfo2)
    {
      if (WirelessConfigFragment.states.contains(paramWifiItemInfo1.state))
        return -1;
      if (WirelessConfigFragment.states.contains(paramWifiItemInfo2.state))
        return 1;
      return paramWifiItemInfo2.level - paramWifiItemInfo1.level;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.network.WirelessConfigFragment
 * JD-Core Version:    0.6.2
 */