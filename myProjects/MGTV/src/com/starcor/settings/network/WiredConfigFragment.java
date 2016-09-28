package com.starcor.settings.network;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.starcor.settings.R.color;
import com.starcor.settings.R.id;
import com.starcor.settings.R.layout;

public class WiredConfigFragment extends Fragment
{
  public static final String TAG = WiredConfigFragment.class.getSimpleName();
  private static String _ip = "\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}+\\.\\d{1,3}";
  private BroadcastReceiver connectivityReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
    }
  };
  private TextView defaultGateway;
  private TextView defaultGatewayContent;
  private EditText defaultGatewayEdit;
  private Button dhcpBtn;
  private TextView dnsAddress;
  private TextView dnsAddressContent;
  private EditText dnsAddressEdit;
  private int grayColor;
  private TextView ipAddress;
  private TextView ipAddressContent;
  private EditText ipAddressEdit;
  private NetworkConfigFragment.ConnectMode mConnectMode;
  private Button save;
  private Button staticIpBtn;
  private TextView subnetMask;
  private TextView subnetMaskContent;
  private EditText subnetMaskEdit;
  private int whiteColor;
  private TextView wiredStatus;

  private void initWiredNetwork()
  {
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

  private void setDhcpMode()
  {
  }

  private void setStaticMode()
  {
  }

  private void setWiredInfo(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.ipAddressContent.setText(paramString1);
    this.subnetMaskContent.setText(paramString2);
    this.dnsAddressContent.setText(paramString3);
    this.defaultGatewayContent.setText(paramString4);
    this.ipAddressEdit.setText(paramString1);
    this.subnetMaskEdit.setText(paramString2);
    this.dnsAddressEdit.setText(paramString3);
    this.defaultGatewayEdit.setText(paramString4);
  }

  public static void showAdd(FragmentManager paramFragmentManager)
  {
    WiredConfigFragment localWiredConfigFragment = new WiredConfigFragment();
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    localFragmentTransaction.add(R.id.network_fragment_container, localWiredConfigFragment, TAG);
    localFragmentTransaction.addToBackStack(null);
    localFragmentTransaction.commit();
  }

  public static void showReplace(FragmentManager paramFragmentManager)
  {
    WiredConfigFragment localWiredConfigFragment = new WiredConfigFragment();
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    localFragmentTransaction.replace(R.id.network_fragment_container, localWiredConfigFragment, TAG);
    localFragmentTransaction.addToBackStack(null);
    localFragmentTransaction.commit();
  }

  private void switchEditMode(boolean paramBoolean)
  {
    NetworkConfigFragment.ConnectMode localConnectMode;
    int i;
    label28: int j;
    label50: int k;
    label73: int m;
    label96: int n;
    label117: int i1;
    label138: int i2;
    label159: int i3;
    label180: int i4;
    label200: int i5;
    label220: int i6;
    label240: EditText localEditText4;
    int i7;
    if (paramBoolean)
    {
      localConnectMode = NetworkConfigFragment.ConnectMode.STATIC;
      this.mConnectMode = localConnectMode;
      TextView localTextView1 = this.ipAddress;
      if (!paramBoolean)
        break label275;
      i = this.whiteColor;
      localTextView1.setTextColor(i);
      TextView localTextView2 = this.subnetMask;
      if (!paramBoolean)
        break label284;
      j = this.whiteColor;
      localTextView2.setTextColor(j);
      TextView localTextView3 = this.dnsAddress;
      if (!paramBoolean)
        break label293;
      k = this.whiteColor;
      localTextView3.setTextColor(k);
      TextView localTextView4 = this.defaultGateway;
      if (!paramBoolean)
        break label302;
      m = this.whiteColor;
      localTextView4.setTextColor(m);
      TextView localTextView5 = this.ipAddressContent;
      if (!paramBoolean)
        break label311;
      n = 8;
      localTextView5.setVisibility(n);
      TextView localTextView6 = this.subnetMaskContent;
      if (!paramBoolean)
        break label317;
      i1 = 8;
      localTextView6.setVisibility(i1);
      TextView localTextView7 = this.dnsAddressContent;
      if (!paramBoolean)
        break label323;
      i2 = 8;
      localTextView7.setVisibility(i2);
      TextView localTextView8 = this.defaultGatewayContent;
      if (!paramBoolean)
        break label329;
      i3 = 8;
      localTextView8.setVisibility(i3);
      EditText localEditText1 = this.ipAddressEdit;
      if (!paramBoolean)
        break label335;
      i4 = 0;
      localEditText1.setVisibility(i4);
      EditText localEditText2 = this.subnetMaskEdit;
      if (!paramBoolean)
        break label342;
      i5 = 0;
      localEditText2.setVisibility(i5);
      EditText localEditText3 = this.dnsAddressEdit;
      if (!paramBoolean)
        break label349;
      i6 = 0;
      localEditText3.setVisibility(i6);
      localEditText4 = this.defaultGatewayEdit;
      i7 = 0;
      if (!paramBoolean)
        break label356;
    }
    while (true)
    {
      localEditText4.setVisibility(i7);
      return;
      localConnectMode = NetworkConfigFragment.ConnectMode.DHCP;
      break;
      label275: i = this.grayColor;
      break label28;
      label284: j = this.grayColor;
      break label50;
      label293: k = this.grayColor;
      break label73;
      label302: m = this.grayColor;
      break label96;
      label311: n = 0;
      break label117;
      label317: i1 = 0;
      break label138;
      label323: i2 = 0;
      break label159;
      label329: i3 = 0;
      break label180;
      label335: i4 = 8;
      break label200;
      label342: i5 = 8;
      break label220;
      label349: i6 = 8;
      break label240;
      label356: i7 = 8;
    }
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    paramActivity.registerReceiver(this.connectivityReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.fragment_wired_config, paramViewGroup, false);
    this.wiredStatus = ((TextView)localView.findViewById(R.id.wired_status));
    this.ipAddress = ((TextView)localView.findViewById(R.id.ip_address));
    this.subnetMask = ((TextView)localView.findViewById(R.id.subnet_mask));
    this.dnsAddress = ((TextView)localView.findViewById(R.id.dns_address));
    this.defaultGateway = ((TextView)localView.findViewById(R.id.default_gateway));
    this.grayColor = getResources().getColor(R.color.dark_gray);
    this.whiteColor = getResources().getColor(17170443);
    this.dhcpBtn = ((Button)localView.findViewById(R.id.dhcp_btn));
    this.staticIpBtn = ((Button)localView.findViewById(R.id.static_ip_btn));
    this.dhcpBtn.setNextFocusDownId(R.id.static_ip_btn);
    this.dhcpBtn.setNextFocusUpId(R.id.wired);
    this.staticIpBtn.setNextFocusUpId(R.id.wired);
    this.ipAddressContent = ((TextView)localView.findViewById(R.id.ip_address_content));
    this.subnetMaskContent = ((TextView)localView.findViewById(R.id.subnet_mask_content));
    this.dnsAddressContent = ((TextView)localView.findViewById(R.id.dns_address_content));
    this.defaultGatewayContent = ((TextView)localView.findViewById(R.id.default_gateway_content));
    this.ipAddressEdit = ((EditText)localView.findViewById(R.id.ip_address_edit));
    this.subnetMaskEdit = ((EditText)localView.findViewById(R.id.subnet_mask_edit));
    this.dnsAddressEdit = ((EditText)localView.findViewById(R.id.dns_address_edit));
    this.defaultGatewayEdit = ((EditText)localView.findViewById(R.id.default_gateway_edit));
    this.save = ((Button)localView.findViewById(R.id.save));
    this.save.setNextFocusUpId(R.id.static_ip_btn);
    this.save.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        switch ($SWITCH_TABLE$com$starcor$settings$network$NetworkConfigFragment$ConnectMode()[WiredConfigFragment.this.mConnectMode.ordinal()])
        {
        default:
          return;
        case 1:
          WiredConfigFragment.this.setStaticMode();
          return;
        case 2:
        }
        WiredConfigFragment.this.setDhcpMode();
      }
    });
    this.dhcpBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        WiredConfigFragment.this.dhcpBtn.setSelected(true);
        WiredConfigFragment.this.staticIpBtn.setSelected(false);
        WiredConfigFragment.this.switchEditMode(false);
      }
    });
    this.staticIpBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        WiredConfigFragment.this.dhcpBtn.setSelected(false);
        WiredConfigFragment.this.staticIpBtn.setSelected(true);
        WiredConfigFragment.this.switchEditMode(true);
      }
    });
    initWiredNetwork();
    return localView;
  }

  public void onDetach()
  {
    getActivity().unregisterReceiver(this.connectivityReceiver);
    super.onDetach();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.network.WiredConfigFragment
 * JD-Core Version:    0.6.2
 */