package com.starcor.settings.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.starcor.settings.R.id;
import com.starcor.settings.R.layout;
import com.starcor.settings.utils.Tools;

public class NetworkConfigFragment extends Fragment
{
  public static final String TAG = NetworkConfigFragment.class.getSimpleName();
  View base;
  private TextView broadbandDialUp;
  private View broadbandDialUpImage;
  private NetworkType mNetworkType = NetworkType.WIRED;
  private TextView wired;
  private View wiredImage;
  private TextView wireless;
  private View wirelessImage;

  private void initNetwork()
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)getActivity().getSystemService("connectivity")).getActiveNetworkInfo();
    if (localNetworkInfo != null)
    {
      setNetworkType(getNetworkType(localNetworkInfo.getType()));
      return;
    }
    setNetworkType(this.mNetworkType);
  }

  private void setNetworkType(NetworkType paramNetworkType)
  {
    int i = 4;
    this.mNetworkType = paramNetworkType;
    View localView1 = this.wiredImage;
    int j;
    label22: View localView2;
    if (paramNetworkType == NetworkType.WIRED)
    {
      j = 0;
      localView1.setVisibility(j);
      localView2 = this.wirelessImage;
      if (paramNetworkType != NetworkType.WIRELESS)
        break label115;
    }
    label115: for (int k = 0; ; k = i)
    {
      localView2.setVisibility(k);
      View localView3 = this.broadbandDialUpImage;
      if (paramNetworkType == NetworkType.BROADBAND_DIAL_UP)
        i = 0;
      localView3.setVisibility(i);
      switch ($SWITCH_TABLE$com$starcor$settings$network$NetworkConfigFragment$NetworkType()[paramNetworkType.ordinal()])
      {
      default:
        return;
        j = i;
        break label22;
      case 1:
      case 2:
      case 3:
      }
    }
    this.wired.setSelected(true);
    this.wireless.setSelected(false);
    this.broadbandDialUp.setSelected(false);
    Tools.showFragment(getFragmentManager(), WiredConfigFragment.class);
    return;
    this.wired.setSelected(false);
    this.wireless.setSelected(true);
    this.broadbandDialUp.setSelected(false);
    Tools.showFragment(getFragmentManager(), WirelessConfigFragment.class);
    return;
    this.wired.setSelected(false);
    this.wireless.setSelected(false);
    this.broadbandDialUp.setSelected(true);
    Tools.showFragment(getFragmentManager(), BroadbandDialUpConfigFragment.class);
  }

  public static void showAdd(FragmentManager paramFragmentManager)
  {
    NetworkConfigFragment localNetworkConfigFragment = new NetworkConfigFragment();
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    localFragmentTransaction.add(R.id.fragment_container, localNetworkConfigFragment, TAG);
    localFragmentTransaction.addToBackStack(null);
    localFragmentTransaction.commit();
  }

  public static void showReplace(FragmentManager paramFragmentManager)
  {
    NetworkConfigFragment localNetworkConfigFragment = new NetworkConfigFragment();
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    localFragmentTransaction.replace(R.id.fragment_container, localNetworkConfigFragment, TAG);
    localFragmentTransaction.addToBackStack(null);
    localFragmentTransaction.commit();
  }

  public NetworkType getNetworkType(int paramInt)
  {
    switch (paramInt)
    {
    default:
      throw new IllegalArgumentException("error type, the type is: " + paramInt);
    case 9:
      return NetworkType.WIRED;
    case 1:
      return NetworkType.WIRELESS;
    case 14:
    }
    return NetworkType.BROADBAND_DIAL_UP;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.base = paramLayoutInflater.inflate(R.layout.fragment_network_config, paramViewGroup, false);
    this.wired = ((TextView)this.base.findViewById(R.id.wired));
    this.wired.setNextFocusDownId(R.id.dhcp_btn);
    this.wireless = ((TextView)this.base.findViewById(R.id.wireless));
    this.broadbandDialUp = ((TextView)this.base.findViewById(R.id.broadband_dial_up));
    this.wiredImage = this.base.findViewById(R.id.wired_image);
    this.wirelessImage = this.base.findViewById(R.id.wireless_image);
    this.broadbandDialUpImage = this.base.findViewById(R.id.broadband_dial_up_image);
    this.wired.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
          NetworkConfigFragment.this.setNetworkType(NetworkConfigFragment.NetworkType.WIRED);
      }
    });
    this.wireless.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
          NetworkConfigFragment.this.setNetworkType(NetworkConfigFragment.NetworkType.WIRELESS);
      }
    });
    this.broadbandDialUp.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
          NetworkConfigFragment.this.setNetworkType(NetworkConfigFragment.NetworkType.BROADBAND_DIAL_UP);
      }
    });
    this.wired.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        NetworkConfigFragment.this.setNetworkType(NetworkConfigFragment.NetworkType.WIRED);
      }
    });
    this.wireless.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        NetworkConfigFragment.this.setNetworkType(NetworkConfigFragment.NetworkType.WIRELESS);
      }
    });
    this.broadbandDialUp.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        NetworkConfigFragment.this.setNetworkType(NetworkConfigFragment.NetworkType.BROADBAND_DIAL_UP);
      }
    });
    initNetwork();
    return this.base;
  }

  static enum ConnectMode
  {
    static
    {
      DHCP = new ConnectMode("DHCP", 1);
      ConnectMode[] arrayOfConnectMode = new ConnectMode[2];
      arrayOfConnectMode[0] = STATIC;
      arrayOfConnectMode[1] = DHCP;
    }
  }

  public static enum NetworkType
  {
    static
    {
      BROADBAND_DIAL_UP = new NetworkType("BROADBAND_DIAL_UP", 2);
      NetworkType[] arrayOfNetworkType = new NetworkType[3];
      arrayOfNetworkType[0] = WIRED;
      arrayOfNetworkType[1] = WIRELESS;
      arrayOfNetworkType[2] = BROADBAND_DIAL_UP;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.network.NetworkConfigFragment
 * JD-Core Version:    0.6.2
 */