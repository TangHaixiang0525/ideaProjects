package com.starcor.hunan;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.config.MgtvUrl;
import com.starcor.config.MgtvUrl.ServerPlatform;
import com.starcor.config.MgtvVersion;
import com.starcor.config.MgtvVersion.ReleaseType;
import com.starcor.core.utils.AppKiller;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.service.SystemTimeManager;
import com.starcor.hunan.widget.XULDialog;
import com.starcor.hunan.widget.uilog.DispatcherEventLayout;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import java.util.Date;
import java.util.Iterator;
import org.json.JSONObject;

public class NetworkErrorActivity extends DialogActivity
{
  private static final long IntervalOfUpdateTime = 500L;
  public static final String PAGE_TYPE = "page_type";
  public static final String TAG = "NetworkErrorActivity";
  private XULDialog _ExitDialog = null;
  private XulView _connectingInfo;
  private XulView _extTitleInfoTime;
  private XulView _extTitleInfoTime_unit;
  private XulView _ext_title_info_time_min;
  private XulView _ipLimitedInfo;
  long _lastUpdateTime = 0L;
  private XulView _licenseErrorInfo;
  private XulView _macLimitedInfo;
  private XulView _netErrorInfo;
  private DispatcherEventLayout dispatcherEventLayout;
  boolean isNetConfigCalled = false;
  PageType mPageType = PageType.NETWORK_ERROR;
  private boolean openSplashPage = false;

  private void initExitDialog()
  {
    Logger.i("NetworkErrorActivity", "initExitDialog ");
    this._ExitDialog = new XULDialog(this.context, "ExitDialog", null, 2131296259)
    {
      protected boolean xulDoAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
      {
        if (paramAnonymousString2.equals("sure"))
        {
          Logger.i("NetworkErrorActivity", "_ExitDialog->xulDoAction->killApp");
          AppKiller.getInstance().killApp();
        }
        while (true)
        {
          return false;
          if (paramAnonymousString2.equals("cancel"))
            dismiss();
        }
      }
    };
  }

  private void initVersionInfo()
  {
    XulView localXulView1 = xulFindViewById("main_page_version_info");
    if (localXulView1 == null)
      return;
    if ((MgtvVersion.getReleaseType() == MgtvVersion.ReleaseType.Release) || (MgtvVersion.getReleaseType() == MgtvVersion.ReleaseType.NC_Release) || (MgtvVersion.getReleaseType() == MgtvVersion.ReleaseType.Demo))
    {
      localXulView1.setStyle("display", "none");
      localXulView1.resetRender();
      return;
    }
    XulArrayList localXulArrayList = xulFindViewsByClass("logo_item");
    if (localXulArrayList != null)
    {
      Iterator localIterator = localXulArrayList.iterator();
      while (localIterator.hasNext())
      {
        XulView localXulView2 = (XulView)localIterator.next();
        localXulView2.setStyle("display", "none");
        localXulView2.resetRender();
      }
    }
    String str1;
    String str2;
    if ((MgtvVersion.getReleaseType() == MgtvVersion.ReleaseType.NC_Release) || (MgtvVersion.getReleaseType() == MgtvVersion.ReleaseType.NC_Beta) || (MgtvVersion.getReleaseType() == MgtvVersion.ReleaseType.NC_Debug))
    {
      str1 = "内部测试版";
      if (MgtvUrl.getServerPlatform() != MgtvUrl.ServerPlatform.Release)
        break label200;
      str2 = "正式平台";
    }
    while (true)
    {
      localXulView1.setAttr("text", str2 + " " + str1);
      localXulView1.setStyle("display", "block");
      localXulView1.resetRender();
      return;
      str1 = "测试版";
      break;
      label200: if (MgtvUrl.getServerPlatform() == MgtvUrl.ServerPlatform.Test)
        str2 = "测试平台";
      else
        str2 = "开发平台";
    }
  }

  private void openNetSettings()
  {
    this.isNetConfigCalled = true;
    try
    {
      if (DeviceInfo.isTCL_RT2995())
      {
        Intent localIntent1 = new Intent();
        localIntent1.setClassName("com.tcl.settings", "com.tcl.settings.MainActivity");
        startActivity(localIntent1);
        return;
      }
      if (DeviceInfo.isXiaoMi())
      {
        Intent localIntent2 = new Intent();
        localIntent2.setComponent(new ComponentName("com.xiaomi.mitv.settings", "com.xiaomi.mitv.settings.entry.MainActivity"));
        startActivity(localIntent2);
        return;
      }
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
      localActivityNotFoundException.printStackTrace();
      return;
    }
    Intent localIntent3 = new Intent();
    localIntent3.setAction("android.settings.SETTINGS");
    startActivity(localIntent3);
  }

  private void setPageType(PageType paramPageType)
  {
    this.mPageType = paramPageType;
    if (this._ipLimitedInfo != null)
    {
      this._ipLimitedInfo.setStyle("display", "none");
      this._ipLimitedInfo.resetRender();
    }
    if (this._connectingInfo != null)
    {
      this._connectingInfo.setStyle("display", "none");
      this._connectingInfo.resetRender();
    }
    if (this._macLimitedInfo != null)
    {
      this._macLimitedInfo.setStyle("display", "none");
      this._macLimitedInfo.resetRender();
    }
    if (this._licenseErrorInfo != null)
    {
      this._licenseErrorInfo.setStyle("display", "none");
      this._licenseErrorInfo.resetRender();
    }
    if (this._netErrorInfo != null)
    {
      this._netErrorInfo.setStyle("display", "none");
      this._netErrorInfo.resetRender();
    }
    xulClearFocus();
    switch (3.$SwitchMap$com$starcor$hunan$NetworkErrorActivity$PageType[paramPageType.ordinal()])
    {
    default:
      throw new IllegalStateException("error status");
    case 1:
      if (this._connectingInfo != null)
      {
        this._connectingInfo.setStyle("display", "block");
        this._connectingInfo.resetRender();
      }
      break;
    case 2:
    case 3:
    case 4:
    case 5:
    }
    do
    {
      do
      {
        do
        {
          do
            return;
          while (this._netErrorInfo == null);
          this._netErrorInfo.setStyle("display", "block");
          this._netErrorInfo.resetRender();
          xulRequestFocus(xulFindViewById("net_error_setup_button"));
          return;
        }
        while (this._macLimitedInfo == null);
        this._macLimitedInfo.setStyle("display", "block");
        this._macLimitedInfo.resetRender();
        return;
      }
      while (this._ipLimitedInfo == null);
      this._ipLimitedInfo.setStyle("display", "block");
      this._ipLimitedInfo.resetRender();
      return;
    }
    while (this._licenseErrorInfo == null);
    this._licenseErrorInfo.setStyle("display", "block");
    this._licenseErrorInfo.resetRender();
  }

  private void updateTime(long paramLong)
  {
    if (this._lastUpdateTime == -1L)
      this._lastUpdateTime = paramLong;
    do
    {
      do
        return;
      while (paramLong - this._lastUpdateTime < 500L);
      this._lastUpdateTime = paramLong;
      if (this._extTitleInfoTime == null)
        this._extTitleInfoTime = xulFindViewById("ext_title_info_time");
    }
    while (this._extTitleInfoTime == null);
    String str1 = this._extTitleInfoTime.getAttrString("text");
    Date localDate = new Date(SystemTimeManager.getCurrentServerTime());
    String str2 = localDate.getHours() + "";
    String str3 = localDate.getMinutes() + "";
    if (localDate.getMinutes() < 10)
      str3 = "0" + str3;
    if (this._extTitleInfoTime_unit == null)
      this._extTitleInfoTime_unit = xulFindViewById("ext_title_info_time_unit");
    if (this._ext_title_info_time_min == null)
      this._ext_title_info_time_min = xulFindViewById("ext_title_info_time_min");
    if (this._extTitleInfoTime_unit != null)
    {
      if (localDate.getHours() <= 12)
        break label321;
      this._extTitleInfoTime_unit.setAttr("text", "PM");
    }
    while (true)
    {
      this._extTitleInfoTime_unit.resetRender();
      if (!str1.equals(str2))
      {
        this._extTitleInfoTime.setAttr("text", str2 + ":");
        this._extTitleInfoTime.resetRender();
      }
      if (this._ext_title_info_time_min.getAttrString("text").equals(str2))
        break;
      this._ext_title_info_time_min.setAttr("text", str3);
      this._ext_title_info_time_min.resetRender();
      return;
      label321: this._extTitleInfoTime_unit.setAttr("text", "AM");
    }
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if (this.dispatcherEventLayout != null)
      this.dispatcherEventLayout.dispatchKeyEvent(paramKeyEvent);
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public void gotoSplashActivity()
  {
    startActivity(new Intent(this, SplashActivity.class));
  }

  public void onBackPressed()
  {
    if (AppFuncCfg.FUNCTION_SHOW_EXIT_APP_DIALOG)
    {
      initExitDialog();
      this._ExitDialog.show();
      return;
    }
    Logger.i("NetworkErrorActivity", "exit NetworkErrorActivity!");
    AppKiller.getInstance().killApp();
  }

  protected void onCreate(Bundle paramBundle)
  {
    Logger.i("NetworkErrorActivity", "onCreate");
    super.onCreate(paramBundle);
    initXul("ErrorPage", true);
    final Handler localHandler = new Handler();
    localHandler.postDelayed(new Runnable()
    {
      public void run()
      {
        if (NetworkErrorActivity.this.isFinishing())
          return;
        if (NetworkErrorActivity.this.isRunning())
        {
          long l = XulUtils.timestamp();
          NetworkErrorActivity.this.updateTime(l);
        }
        localHandler.postDelayed(this, 200L);
      }
    }
    , 500L);
    this.dispatcherEventLayout = new DispatcherEventLayout(this);
    reportLoad(true, null);
  }

  protected void onDestroy()
  {
    Logger.i("NetworkErrorActivity", "onDestroy");
    Logger.i("Application", "NetworkErrorActivity" + " onDestroy");
    super.onDestroy();
  }

  protected void onRestart()
  {
    super.onRestart();
    reportLoad(true, null);
  }

  protected void onResume()
  {
    Logger.i("NetworkErrorActivity", "onResume");
    if (this.openSplashPage)
    {
      this.openSplashPage = false;
      gotoSplashActivity();
      finish();
    }
    super.onResume();
  }

  protected void onStop()
  {
    Logger.i("NetworkErrorActivity", "onStop");
    super.onStop();
    reportExit(true, null);
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    if ("android_open_net_settings".equals(paramString3))
    {
      openNetSettings();
      return true;
    }
    if ("network_retry".equals(paramString3))
    {
      Logger.i("NetworkErrorActivity", "network_retry");
      XulManager.clear();
      gotoSplashActivity();
      finish();
    }
    return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
  }

  protected void xulOnRenderIsReady()
  {
    this._ipLimitedInfo = xulFindViewById("ip_limited_info");
    this._macLimitedInfo = xulFindViewById("mac_limited_info");
    this._licenseErrorInfo = xulFindViewById("license_error_info");
    this._netErrorInfo = xulFindViewById("net_error_info");
    this._connectingInfo = xulFindViewById("connecting_info");
    if (getIntent() != null)
    {
      int i = getIntent().getIntExtra("page_type", 0);
      setPageType(PageType.values()[i]);
    }
    while (true)
    {
      initVersionInfo();
      initExitDialog();
      super.xulOnRenderIsReady();
      return;
      setPageType(this.mPageType);
    }
  }

  public static enum PageType
  {
    static
    {
      MAC_ERROR = new PageType("MAC_ERROR", 2);
      IP_LIMIT = new PageType("IP_LIMIT", 3);
      LICENSE_ERROR = new PageType("LICENSE_ERROR", 4);
      PageType[] arrayOfPageType = new PageType[5];
      arrayOfPageType[0] = CONNECTING;
      arrayOfPageType[1] = NETWORK_ERROR;
      arrayOfPageType[2] = MAC_ERROR;
      arrayOfPageType[3] = IP_LIMIT;
      arrayOfPageType[4] = LICENSE_ERROR;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.NetworkErrorActivity
 * JD-Core Version:    0.6.2
 */