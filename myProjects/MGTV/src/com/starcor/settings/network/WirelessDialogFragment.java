package com.starcor.settings.network;

import android.app.Dialog;
import android.content.res.Resources;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import com.starcor.settings.R.color;
import com.starcor.settings.R.id;
import com.starcor.settings.R.layout;
import com.starcor.settings.R.style;
import com.starcor.settings.utils.ToastUtil;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public class WirelessDialogFragment extends DialogFragment
{
  private Button back;
  private View base;
  private Button confirm;
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
  private NetworkConfigFragment.ConnectMode mConnectMode = NetworkConfigFragment.ConnectMode.DHCP;
  private CheckBox mDisplayPasswordBox;
  private WirelessConfigFragment.WifiItemInfo mInfo;
  private WifiHelper mWifiHelper;
  private EditText passwrodEdit;
  private TextView securityText;
  private TextView signalStrengthText;
  private Button staticIpBtn;
  private TextView subnetMask;
  private TextView subnetMaskContent;
  private EditText subnetMaskEdit;
  private TextView title;
  private int whiteColor;

  private WifiConfiguration createWifiConfig()
  {
    WifiConfiguration localWifiConfiguration = new WifiConfiguration();
    localWifiConfiguration.SSID = ("\"" + this.mInfo.name + "\"");
    localWifiConfiguration.BSSID = this.mInfo.BSSID;
    String str1 = this.mWifiHelper.getEncryptionType(this.mInfo.capabilities);
    String str2;
    if ((str1.equals("WPA/WPA2-PSK")) || (str1.equals("WPA2-PSK")))
    {
      localWifiConfiguration.allowedKeyManagement.set(1);
      if (this.passwrodEdit.length() != 0)
      {
        str2 = this.passwrodEdit.getText().toString();
        if (str2.matches("[0-9A-Fa-f]{64}"))
          localWifiConfiguration.preSharedKey = str2;
      }
      else
      {
        localWifiConfiguration.status = 2;
      }
    }
    do
    {
      return localWifiConfiguration;
      localWifiConfiguration.preSharedKey = ("\"" + str2 + "\"");
      break;
      if (!str1.equals("WPA-PSK"))
        break label253;
      localWifiConfiguration.allowedKeyManagement.set(1);
    }
    while (this.passwrodEdit.length() == 0);
    String str4 = this.passwrodEdit.getText().toString();
    if (str4.matches("[0-9A-Fa-f]{64}"))
    {
      localWifiConfiguration.preSharedKey = str4;
      return localWifiConfiguration;
    }
    localWifiConfiguration.preSharedKey = ('"' + str4 + '"');
    return localWifiConfiguration;
    label253: if (str1.equals("WEP"))
    {
      localWifiConfiguration.allowedKeyManagement.set(0);
      localWifiConfiguration.allowedAuthAlgorithms.set(1);
      if (this.passwrodEdit.length() >= 5)
      {
        int i = this.passwrodEdit.length();
        String str3 = this.passwrodEdit.getText().toString();
        if (((i == 10) || (i == 26) || (i == 58)) && (str3.matches("[0-9A-Fa-f]*")))
        {
          localWifiConfiguration.wepKeys[0] = str3;
          return localWifiConfiguration;
        }
        localWifiConfiguration.wepKeys[0] = ('"' + str3 + '"');
        return localWifiConfiguration;
      }
      ToastUtil.showToast(getActivity(), "瀵嗙爜闀垮害鑷冲皯涓�5浣�");
      this.passwrodEdit.requestFocus();
      return localWifiConfiguration;
    }
    localWifiConfiguration.allowedKeyManagement.set(0);
    return localWifiConfiguration;
  }

  private void getStaticConfig(WifiConfiguration paramWifiConfiguration)
  {
  }

  private void initWifiNetwork()
  {
    this.title.setText(this.mInfo.name);
    this.signalStrengthText.setText(this.mWifiHelper.getSignalStrengthString(this.mInfo.level));
    this.securityText.setText(this.mWifiHelper.getEncryptionType(this.mInfo.capabilities));
    if (this.mWifiHelper.getBSSID() == null)
      this.dhcpBtn.setSelected(true);
    while (true)
    {
      return;
      if (!this.mWifiHelper.getBSSID().equals(this.mInfo.BSSID))
        break;
      List localList = this.mWifiHelper.getConfiguration();
      if ((localList != null) && (localList.size() > 0))
      {
        Iterator localIterator = localList.iterator();
        while (localIterator.hasNext())
          ((WifiConfiguration)localIterator.next());
      }
    }
    this.dhcpBtn.setSelected(true);
  }

  private void setDhcpMode()
  {
    WifiConfiguration localWifiConfiguration = createWifiConfig();
    localWifiConfiguration.hiddenSSID = true;
    boolean bool = this.mWifiHelper.connectNetwork(localWifiConfiguration);
    Log.w("lx", "dhcp connect network---" + bool);
    switchEditMode(false);
    dismiss();
  }

  // ERROR //
  private void setStaticMode()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 304	com/starcor/settings/network/WirelessDialogFragment:ipAddressEdit	Landroid/widget/EditText;
    //   4: invokevirtual 155	android/widget/EditText:getText	()Landroid/text/Editable;
    //   7: invokeinterface 158 1 0
    //   12: astore_1
    //   13: aload_0
    //   14: getfield 306	com/starcor/settings/network/WirelessDialogFragment:subnetMaskEdit	Landroid/widget/EditText;
    //   17: invokevirtual 155	android/widget/EditText:getText	()Landroid/text/Editable;
    //   20: invokeinterface 158 1 0
    //   25: astore_2
    //   26: aload_0
    //   27: getfield 308	com/starcor/settings/network/WirelessDialogFragment:dnsAddressEdit	Landroid/widget/EditText;
    //   30: invokevirtual 155	android/widget/EditText:getText	()Landroid/text/Editable;
    //   33: invokeinterface 158 1 0
    //   38: astore_3
    //   39: aload_0
    //   40: getfield 310	com/starcor/settings/network/WirelessDialogFragment:defaultGatewayEdit	Landroid/widget/EditText;
    //   43: invokevirtual 155	android/widget/EditText:getText	()Landroid/text/Editable;
    //   46: invokeinterface 158 1 0
    //   51: astore 4
    //   53: aload_0
    //   54: invokevirtual 194	com/starcor/settings/network/WirelessDialogFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   57: aload_1
    //   58: ldc_w 312
    //   61: ldc_w 314
    //   64: invokestatic 320	com/starcor/settings/utils/Tools:isLegitimateIP	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
    //   67: ifne +4 -> 71
    //   70: return
    //   71: aload_0
    //   72: invokevirtual 194	com/starcor/settings/network/WirelessDialogFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   75: aload_2
    //   76: ldc_w 322
    //   79: ldc_w 324
    //   82: invokestatic 320	com/starcor/settings/utils/Tools:isLegitimateIP	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
    //   85: ifeq -15 -> 70
    //   88: aload_0
    //   89: invokevirtual 194	com/starcor/settings/network/WirelessDialogFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   92: aload_3
    //   93: ldc_w 326
    //   96: ldc_w 328
    //   99: invokestatic 320	com/starcor/settings/utils/Tools:isLegitimateIP	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
    //   102: ifeq -32 -> 70
    //   105: aload_0
    //   106: invokevirtual 194	com/starcor/settings/network/WirelessDialogFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   109: aload 4
    //   111: ldc_w 330
    //   114: ldc_w 332
    //   117: invokestatic 320	com/starcor/settings/utils/Tools:isLegitimateIP	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
    //   120: ifeq -50 -> 70
    //   123: aload_2
    //   124: invokestatic 336	com/starcor/settings/utils/Tools:maskStringToLength	(Ljava/lang/String;)I
    //   127: istore 6
    //   129: aload_0
    //   130: invokespecial 260	com/starcor/settings/network/WirelessDialogFragment:createWifiConfig	()Landroid/net/wifi/WifiConfiguration;
    //   133: astore 7
    //   135: aload_1
    //   136: invokestatic 342	java/net/InetAddress:getByName	(Ljava/lang/String;)Ljava/net/InetAddress;
    //   139: iload 6
    //   141: aload 7
    //   143: invokestatic 346	com/starcor/settings/network/WifiHelper:setIpAddress	(Ljava/net/InetAddress;ILandroid/net/wifi/WifiConfiguration;)V
    //   146: aload 4
    //   148: invokestatic 342	java/net/InetAddress:getByName	(Ljava/lang/String;)Ljava/net/InetAddress;
    //   151: aload 7
    //   153: invokestatic 350	com/starcor/settings/network/WifiHelper:setGateway	(Ljava/net/InetAddress;Landroid/net/wifi/WifiConfiguration;)V
    //   156: aload_3
    //   157: invokestatic 342	java/net/InetAddress:getByName	(Ljava/lang/String;)Ljava/net/InetAddress;
    //   160: aload 7
    //   162: invokestatic 353	com/starcor/settings/network/WifiHelper:setDNS	(Ljava/net/InetAddress;Landroid/net/wifi/WifiConfiguration;)V
    //   165: aload 7
    //   167: iconst_1
    //   168: putfield 264	android/net/wifi/WifiConfiguration:hiddenSSID	Z
    //   171: aload_0
    //   172: getfield 116	com/starcor/settings/network/WirelessDialogFragment:mWifiHelper	Lcom/starcor/settings/network/WifiHelper;
    //   175: aload 7
    //   177: invokevirtual 268	com/starcor/settings/network/WifiHelper:connectNetwork	(Landroid/net/wifi/WifiConfiguration;)Z
    //   180: istore 9
    //   182: ldc_w 270
    //   185: new 86	java/lang/StringBuilder
    //   188: dup
    //   189: ldc_w 355
    //   192: invokespecial 91	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   195: iload 9
    //   197: invokevirtual 275	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   200: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   203: invokestatic 281	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   206: pop
    //   207: aload_0
    //   208: iconst_0
    //   209: invokespecial 68	com/starcor/settings/network/WirelessDialogFragment:switchEditMode	(Z)V
    //   212: aload_0
    //   213: invokevirtual 284	com/starcor/settings/network/WirelessDialogFragment:dismiss	()V
    //   216: return
    //   217: astore 5
    //   219: aload_0
    //   220: invokevirtual 194	com/starcor/settings/network/WirelessDialogFragment:getActivity	()Landroid/support/v4/app/FragmentActivity;
    //   223: aload 5
    //   225: invokevirtual 358	java/lang/IllegalArgumentException:getMessage	()Ljava/lang/String;
    //   228: invokestatic 202	com/starcor/settings/utils/ToastUtil:showToast	(Landroid/content/Context;Ljava/lang/CharSequence;)V
    //   231: return
    //   232: astore 18
    //   234: aload 18
    //   236: invokevirtual 361	java/lang/SecurityException:printStackTrace	()V
    //   239: goto -74 -> 165
    //   242: astore 17
    //   244: aload 17
    //   246: invokevirtual 362	java/lang/IllegalArgumentException:printStackTrace	()V
    //   249: goto -84 -> 165
    //   252: astore 16
    //   254: aload 16
    //   256: invokevirtual 363	java/net/UnknownHostException:printStackTrace	()V
    //   259: goto -94 -> 165
    //   262: astore 15
    //   264: aload 15
    //   266: invokevirtual 364	java/lang/NoSuchFieldException:printStackTrace	()V
    //   269: goto -104 -> 165
    //   272: astore 14
    //   274: aload 14
    //   276: invokevirtual 365	java/lang/IllegalAccessException:printStackTrace	()V
    //   279: goto -114 -> 165
    //   282: astore 13
    //   284: aload 13
    //   286: invokevirtual 366	java/lang/NoSuchMethodException:printStackTrace	()V
    //   289: goto -124 -> 165
    //   292: astore 12
    //   294: aload 12
    //   296: invokevirtual 367	java/lang/ClassNotFoundException:printStackTrace	()V
    //   299: goto -134 -> 165
    //   302: astore 11
    //   304: aload 11
    //   306: invokevirtual 368	java/lang/InstantiationException:printStackTrace	()V
    //   309: goto -144 -> 165
    //   312: astore 8
    //   314: aload 8
    //   316: invokevirtual 369	java/lang/reflect/InvocationTargetException:printStackTrace	()V
    //   319: goto -154 -> 165
    //
    // Exception table:
    //   from	to	target	type
    //   123	129	217	java/lang/IllegalArgumentException
    //   135	165	232	java/lang/SecurityException
    //   135	165	242	java/lang/IllegalArgumentException
    //   135	165	252	java/net/UnknownHostException
    //   135	165	262	java/lang/NoSuchFieldException
    //   135	165	272	java/lang/IllegalAccessException
    //   135	165	282	java/lang/NoSuchMethodException
    //   135	165	292	java/lang/ClassNotFoundException
    //   135	165	302	java/lang/InstantiationException
    //   135	165	312	java/lang/reflect/InvocationTargetException
  }

  private void setWifiInfo(String paramString1, String paramString2, String paramString3, String paramString4)
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

  public Dialog onCreateDialog(Bundle paramBundle)
  {
    setCancelable(true);
    this.base = View.inflate(getActivity(), R.layout.fragment_wireless_dialog, null);
    Dialog localDialog = new Dialog(getActivity(), R.style.custom_dialog);
    localDialog.setContentView(this.base);
    localDialog.setCanceledOnTouchOutside(true);
    return localDialog;
  }

  public void onStart()
  {
    super.onStart();
    this.mWifiHelper = new WifiHelper(getActivity());
    this.title = ((TextView)this.base.findViewById(R.id.dialog_title));
    this.signalStrengthText = ((TextView)this.base.findViewById(R.id.signal_strength));
    this.securityText = ((TextView)this.base.findViewById(R.id.security));
    this.passwrodEdit = ((EditText)this.base.findViewById(R.id.password_edit));
    this.ipAddress = ((TextView)this.base.findViewById(R.id.ip_address));
    this.subnetMask = ((TextView)this.base.findViewById(R.id.subnet_mask));
    this.dnsAddress = ((TextView)this.base.findViewById(R.id.dns_address));
    this.defaultGateway = ((TextView)this.base.findViewById(R.id.default_gateway));
    this.grayColor = getResources().getColor(R.color.dark_gray);
    this.whiteColor = getResources().getColor(17170443);
    this.dhcpBtn = ((Button)this.base.findViewById(R.id.dhcp_btn));
    this.staticIpBtn = ((Button)this.base.findViewById(R.id.static_ip_btn));
    this.mDisplayPasswordBox = ((CheckBox)this.base.findViewById(R.id.display_check_box));
    this.mDisplayPasswordBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
      {
        int i = WirelessDialogFragment.this.passwrodEdit.getSelectionStart();
        int j = WirelessDialogFragment.this.passwrodEdit.getSelectionEnd();
        EditText localEditText = WirelessDialogFragment.this.passwrodEdit;
        if (paramAnonymousBoolean);
        for (Object localObject = HideReturnsTransformationMethod.getInstance(); ; localObject = PasswordTransformationMethod.getInstance())
        {
          localEditText.setTransformationMethod((TransformationMethod)localObject);
          WirelessDialogFragment.this.passwrodEdit.setSelection(i, j);
          return;
        }
      }
    });
    this.dhcpBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        WirelessDialogFragment.this.dhcpBtn.setSelected(true);
        WirelessDialogFragment.this.staticIpBtn.setSelected(false);
        WirelessDialogFragment.this.switchEditMode(false);
      }
    });
    this.staticIpBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        WirelessDialogFragment.this.dhcpBtn.setSelected(false);
        WirelessDialogFragment.this.staticIpBtn.setSelected(true);
        WirelessDialogFragment.this.switchEditMode(true);
      }
    });
    this.ipAddressContent = ((TextView)this.base.findViewById(R.id.ip_address_content));
    this.subnetMaskContent = ((TextView)this.base.findViewById(R.id.subnet_mask_content));
    this.dnsAddressContent = ((TextView)this.base.findViewById(R.id.dns_address_content));
    this.defaultGatewayContent = ((TextView)this.base.findViewById(R.id.default_gateway_content));
    this.ipAddressEdit = ((EditText)this.base.findViewById(R.id.ip_address_edit));
    this.subnetMaskEdit = ((EditText)this.base.findViewById(R.id.subnet_mask_edit));
    this.dnsAddressEdit = ((EditText)this.base.findViewById(R.id.dns_address_edit));
    this.defaultGatewayEdit = ((EditText)this.base.findViewById(R.id.default_gateway_edit));
    this.confirm = ((Button)this.base.findViewById(R.id.confirm));
    this.confirm.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        switch ($SWITCH_TABLE$com$starcor$settings$network$NetworkConfigFragment$ConnectMode()[WirelessDialogFragment.this.mConnectMode.ordinal()])
        {
        default:
          return;
        case 1:
          WirelessDialogFragment.this.setStaticMode();
          return;
        case 2:
        }
        WirelessDialogFragment.this.setDhcpMode();
      }
    });
    this.back = ((Button)this.base.findViewById(R.id.back));
    this.back.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        WirelessDialogFragment.this.dismiss();
      }
    });
    initWifiNetwork();
  }

  public void setWifiInfo(WirelessConfigFragment.WifiItemInfo paramWifiItemInfo)
  {
    this.mInfo = paramWifiItemInfo;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.network.WirelessDialogFragment
 * JD-Core Version:    0.6.2
 */