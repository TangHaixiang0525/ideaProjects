package com.starcor.hunan;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.domain.AppVersion;
import com.starcor.core.domain.MetadataGoup;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.domain.Version;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.ads.UrlTools;
import com.starcor.hunan.opendownload.drm.MarlinDrmManager;
import com.starcor.hunan.service.CollectAndPlayListLogic;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.hunan.uilogic.CommonRecevier;
import com.starcor.hunan.uilogic.InitApiData.onApiOKListener;
import com.starcor.hunan.widget.ExitDialog;
import com.starcor.jump.bussines.BussinesMessage;
import com.starcor.message.MessageHandler;
import com.starcor.report.Column.Column;
import com.starcor.report.ReportMessage;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.service.CallBack;
import com.starcor.service.ErrorCode;
import com.starcor.service.InitService;
import com.starcor.service.InitService.Error;
import com.starcor.service.InitService.LocalBinder;
import com.starcor.xul.IXulExternalView;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulRenderContext.IXulRenderHandler;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;

public class SplashActivity extends DialogActivity
  implements CallBack
{
  private static final String HOME_PRESS_ACTION = "android.intent.action.ENTER_HOME";
  private static final int MESSAGE_START_NEXT_ACTIVITY = -90001;
  private static final String TAG = "SplashActivity";
  static XulRenderContext delayLoadPage = null;
  static XulRenderContext preloadMainPage = null;
  static XulRenderContext preloadUserCenterPage = null;
  private static XulRenderContext.IXulRenderHandler xulCommonHandler = new XulRenderContext.IXulRenderHandler()
  {
    Handler _handler = new Handler();

    public IXulExternalView createExternalView(String paramAnonymousString, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, XulView paramAnonymousXulView)
    {
      return null;
    }

    public InputStream getAppData(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
    {
      return null;
    }

    public InputStream getAssets(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
    {
      return null;
    }

    public void invalidate(Rect paramAnonymousRect)
    {
    }

    public void onDoAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
    {
    }

    public void onRenderEvent(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, Object paramAnonymousObject)
    {
    }

    public void onRenderIsReady()
    {
    }

    public String resolve(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
    {
      return null;
    }

    public void uiRun(Runnable paramAnonymousRunnable)
    {
      this._handler.post(paramAnonymousRunnable);
    }

    public void uiRun(Runnable paramAnonymousRunnable, int paramAnonymousInt)
    {
      this._handler.postDelayed(paramAnonymousRunnable, paramAnonymousInt);
    }
  };
  private long bootAdStartTime;
  private CollectAndPlayListLogic collectLogic = new CollectAndPlayListLogic();
  private ServiceConnection conn = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      SplashActivity.access$202(SplashActivity.this, ((InitService.LocalBinder)paramAnonymousIBinder).getService());
      SplashActivity.this.iService.uiIsStarted(SplashActivity.this);
    }

    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      SplashActivity.access$202(SplashActivity.this, null);
    }
  };
  private boolean flagToUnregisterService = true;
  private boolean foreground = false;
  private BroadcastReceiver homePressReceiver = null;
  private InitService iService = null;
  UserInfo info;
  boolean isBind = false;
  boolean isFromOut = false;
  Handler logicTimerHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if ((SplashActivity.this.runState()) && (!SplashActivity.this.isFinishing()))
        SplashActivity.this.logicTimerHandler.sendEmptyMessageDelayed(1, 100L);
    }
  };
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case -90001:
      }
      SplashActivity.this.startNextActivity();
    }
  };
  ArrayList<MetadataGoup> metadataInfos;
  private boolean needToRefreshToken = true;
  StartState state;
  long stateSwitchMs;
  private long tickAfterCreate;

  private void delayFinish()
  {
    waitActivityStartAndFinishFor(5000);
  }

  private void delayFinish(int paramInt)
  {
    if (paramInt <= 0)
    {
      waitActivityStartAndFinishFor(0);
      return;
    }
    waitActivityStartAndFinishFor(paramInt);
  }

  private void doWaitService()
  {
    Logger.i("SplashActivity", "doWaitService");
    if (App.getInstance().isServiceComplete())
    {
      if (App.getInstance().getTaskService() != null)
        break label71;
      Logger.e("SplashActivity", "doWaitService getTaskService() == null");
      this.state = StartState.SS_ERROR;
      Bundle localBundle = new Bundle();
      localBundle.putString("Message", "找不到关键组件:TaskService！");
      if (!isFinishing())
        showDialog(10, localBundle);
    }
    return;
    label71: Logger.i("SplashActivity", "doWaitService switch to SS_WAIT_NET");
    this.state = StartState.SS_WAIT_NET;
    this.stateSwitchMs = System.currentTimeMillis();
  }

  static void init()
  {
  }

  private static void initDelayLoadPage()
  {
    if (preloadUserCenterPage == null)
      preloadUserCenterPage = XulManager.createXulRender("UserCenterPreloadPage", xulCommonHandler, App.SCREEN_WIDTH, App.SCREEN_HEIGHT, false, true);
    if (delayLoadPage == null)
      delayLoadPage = XulManager.createXulRender("DelayLoadPage", xulCommonHandler, App.SCREEN_WIDTH, App.SCREEN_HEIGHT, false, true);
  }

  public static void initPreLoadMainPage()
  {
    if (preloadMainPage == null)
      xulCommonHandler.uiRun(new Runnable()
      {
        public void run()
        {
          if (SplashActivity.preloadMainPage == null)
          {
            DialogActivity.initPreXulManager();
            SplashActivity.preloadMainPage = XulManager.createXulRender("PreLoadPage", SplashActivity.xulCommonHandler, App.SCREEN_WIDTH, App.SCREEN_HEIGHT, false, true);
            SplashActivity.initXul();
          }
        }
      });
  }

  public static void initXul()
  {
    MainActivityV2.initMainActivity();
    initPreLoadMainPage();
  }

  private boolean isForeground()
  {
    return this.foreground;
  }

  private void processImpression(ArrayList<String> paramArrayList)
  {
    if ((paramArrayList == null) || (paramArrayList.size() <= 0));
    while (true)
    {
      return;
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        String str2 = str1;
        if (str2.contains("?"))
        {
          String str3 = UrlTools.getPath(str1);
          String str4 = UrlTools.getUrlParamsEncoded(str1);
          str2 = str3 + "?" + str4;
        }
        ServerApiManager.i().APIReportAdImpression(str2);
      }
    }
  }

  public static void reportStartApp(boolean paramBoolean)
  {
    ReportMessage localReportMessage = new ReportMessage();
    JSONObject localJSONObject = new JSONObject();
    localReportMessage.setDelayReportEnable(paramBoolean);
    try
    {
      localJSONObject.put("act", "start");
      localJSONObject.put("bid", "3.4.0");
      localReportMessage.mExtData = new Column().buildJsonData(localJSONObject);
      localReportMessage.setDesc("启动上报");
      MessageHandler.instance().doNotify(localReportMessage);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Logger.w("ReportService", "json exception!", localException);
    }
  }

  private boolean runState()
  {
    this.tickAfterCreate = (1L + this.tickAfterCreate);
    int i = 12.$SwitchMap$com$starcor$hunan$SplashActivity$StartState[this.state.ordinal()];
    boolean bool = false;
    switch (i)
    {
    case 3:
    case 4:
    case 5:
    default:
    case 6:
    case 7:
    case 1:
    case 2:
    }
    while (true)
    {
      bool = true;
      return bool;
      this.state = StartState.SS_WAIT_SERVICE_INIT;
      continue;
      doWaitService();
    }
  }

  private void showUpgradeChoiceDialog(final Version paramVersion)
  {
    this.state = StartState.SS_NX_WAIT_CHOICE_UPDATE;
    ExitDialog localExitDialog = new ExitDialog(this, 2131296258);
    localExitDialog.setMessage("已有新版本，是否立即升级?");
    localExitDialog.setPositiveButtonListener(new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        Logger.i("SplashActivity", "showUpgradeChoiceDialog.setOnKeyListener back");
        if (!SplashActivity.this.startUpgradeActivity(paramVersion.appVersion.url))
        {
          paramAnonymousDialogInterface.dismiss();
          Logger.e("SplashActivity", "showUpgradeChoiceDialog.onClick startUpgradeActivity");
          SplashActivity.this.iService.skipThisUpgrade();
          return;
        }
        Logger.i("SplashActivity", "showUpgradeChoiceDialog.onClick startUpgradeActivity");
        SplashActivity.this.finish();
      }
    });
    localExitDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if ((paramAnonymousKeyEvent.getAction() == 0) && (paramAnonymousInt == 4))
        {
          paramAnonymousDialogInterface.dismiss();
          Logger.i("SplashActivity", "showUpgradeChoiceDialog.setOnKeyListener  back");
          SplashActivity.this.iService.skipThisUpgrade();
          return true;
        }
        return false;
      }
    });
    localExitDialog.show();
  }

  private boolean startLogicTimer()
  {
    Logger.i("SplashActivity", "startLogicTimer");
    if (!this.logicTimerHandler.sendEmptyMessageDelayed(1, 100L))
      Logger.e("SplashActivity", "startLogicTimer sendEmptyMessageDelayed");
    return true;
  }

  private void startMainActivity()
  {
    if (!isForeground())
    {
      delayFinish();
      return;
    }
    Logger.i("SplashActivity", "splash startMainActivity:" + isForeground());
    Intent localIntent1 = getIntent();
    Intent localIntent2 = new Intent();
    localIntent2.setClass(this, ActivityAdapter.getInstance().getMainActivity());
    localIntent2.putExtra("MetaDataInfo", GlobalLogic.getInstance().getN3A2MetaGroups());
    if (localIntent1 != null)
      localIntent2.putExtras(localIntent1);
    Logger.i("SplashActivity", "startMainActivity");
    new GoToActivity(localIntent2).run();
    delayFinish();
  }

  private void startNextActivity()
  {
    long l1 = System.currentTimeMillis();
    long l2 = GlobalEnv.getInstance().getBootImageAdDuration();
    long l3 = l1 - this.bootAdStartTime;
    Logger.i("SplashActivity", "currentTime = " + l1 + " , bootAdDuration = " + l2 + ", expiredTime = " + l3);
    if (l3 < l2)
    {
      this.mHandler.sendEmptyMessageDelayed(-90001, l2 - l3);
      return;
    }
    Logger.d("SplashActivity", "ExternalRequest,SplashActivity startNextActivity isForeground:" + isForeground());
    if (!isForeground())
    {
      delayFinish();
      return;
    }
    Logger.i("SplashActivity", "intent.getBooleanExtra(ExtWebView.ACTION_FROM_MGTV, false)=" + getIntent().getBooleanExtra("flag_action_from_mgtv", false) + ",AppFuncCfg.FUNCTION_ENABLE_EXTERNAL_CONTROL=" + AppFuncCfg.FUNCTION_ENABLE_EXTERNAL_CONTROL);
    reportStartApp(false);
    startMainActivity();
    Logger.i("SplashActivity", "Intent:" + getIntent().toString() + ", this.getIntent().getAction()=" + getIntent().getAction());
  }

  // ERROR //
  private boolean startUpgradeActivity(String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 539	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   4: ifeq +13 -> 17
    //   7: ldc 16
    //   9: ldc_w 541
    //   12: invokestatic 162	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   15: iconst_0
    //   16: ireturn
    //   17: aload_0
    //   18: invokespecial 143	com/starcor/hunan/SplashActivity:isForeground	()Z
    //   21: ifne +9 -> 30
    //   24: aload_0
    //   25: invokevirtual 544	com/starcor/hunan/SplashActivity:finish	()V
    //   28: iconst_0
    //   29: ireturn
    //   30: ldc 16
    //   32: new 292	java/lang/StringBuilder
    //   35: dup
    //   36: invokespecial 293	java/lang/StringBuilder:<init>	()V
    //   39: ldc_w 546
    //   42: invokevirtual 297	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: aload_1
    //   46: invokevirtual 297	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: invokevirtual 301	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   52: invokestatic 162	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   55: new 443	android/content/Intent
    //   58: dup
    //   59: aload_0
    //   60: ldc_w 548
    //   63: invokespecial 551	android/content/Intent:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
    //   66: astore_2
    //   67: ldc 16
    //   69: ldc_w 553
    //   72: invokestatic 162	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   75: aload_2
    //   76: ldc_w 555
    //   79: iconst_1
    //   80: anewarray 275	java/lang/String
    //   83: dup
    //   84: iconst_0
    //   85: aload_1
    //   86: aastore
    //   87: invokevirtual 558	android/content/Intent:putExtra	(Ljava/lang/String;[Ljava/lang/String;)Landroid/content/Intent;
    //   90: pop
    //   91: aload_2
    //   92: ldc_w 559
    //   95: invokevirtual 563	android/content/Intent:setFlags	(I)Landroid/content/Intent;
    //   98: pop
    //   99: aload_2
    //   100: ldc_w 565
    //   103: iconst_1
    //   104: invokevirtual 568	android/content/Intent:putExtra	(Ljava/lang/String;Z)Landroid/content/Intent;
    //   107: pop
    //   108: aload_2
    //   109: ldc_w 570
    //   112: aload_0
    //   113: invokevirtual 573	com/starcor/hunan/SplashActivity:getPackageName	()Ljava/lang/String;
    //   116: invokevirtual 576	android/content/Intent:putExtra	(Ljava/lang/String;Ljava/lang/String;)Landroid/content/Intent;
    //   119: pop
    //   120: aload_0
    //   121: getfield 580	com/starcor/hunan/SplashActivity:context	Landroid/app/Activity;
    //   124: aload_2
    //   125: invokevirtual 586	android/app/Activity:startActivity	(Landroid/content/Intent;)V
    //   128: iconst_1
    //   129: ireturn
    //   130: astore_3
    //   131: aload_3
    //   132: invokevirtual 589	java/lang/Exception:printStackTrace	()V
    //   135: ldc 16
    //   137: ldc_w 591
    //   140: invokestatic 180	com/starcor/core/utils/Logger:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   143: iconst_0
    //   144: ireturn
    //   145: astore_3
    //   146: goto -15 -> 131
    //
    // Exception table:
    //   from	to	target	type
    //   55	67	130	java/lang/Exception
    //   67	128	145	java/lang/Exception
  }

  private void switchToAdMode(XulView paramXulView)
  {
    XulView localXulView1 = ((XulPage)paramXulView).findItemById("logo-area");
    XulView localXulView2 = ((XulPage)paramXulView).findItemById("ad-area");
    if (localXulView1 != null)
    {
      localXulView1.setStyle("display", "none");
      localXulView1.resetRender();
    }
    if (localXulView2 != null)
    {
      localXulView2.setStyle("display", "block");
      localXulView2.resetRender();
    }
    processImpression(GlobalEnv.getInstance().getBootImageAdImpression());
  }

  private void switchToLogoMode(XulView paramXulView)
  {
    XulView localXulView1 = ((XulPage)paramXulView).findItemById("logo-area");
    XulView localXulView2 = ((XulPage)paramXulView).findItemById("ad-area");
    if (localXulView2 != null)
    {
      localXulView2.setStyle("display", "none");
      localXulView2.resetRender();
    }
    if (localXulView1 != null)
    {
      localXulView1.setStyle("display", "block");
      localXulView1.resetRender();
    }
  }

  private void waitActivityStartAndFinishFor(final int paramInt)
  {
    new Handler(getMainLooper()).postDelayed(new Runnable()
    {
      int closeDelay = -1;
      int waitTime = paramInt;

      void finishActivity()
      {
        this.closeDelay = 2000;
        run();
      }

      public void run()
      {
        if (this.closeDelay > 0)
        {
          this.closeDelay = (-100 + this.closeDelay);
          if (this.closeDelay <= 0)
          {
            Logger.i("SplashActivity", "waitActivityStartAndFinishFor ? finish!!");
            SplashActivity.this.finish();
            return;
          }
          new Handler(SplashActivity.this.getMainLooper()).postDelayed(this, Math.min(this.closeDelay, 100));
          return;
        }
        if (!SplashActivity.this.isForeground())
        {
          Logger.i("SplashActivity", "waitActivityStartAndFinishFor ? terminated!!");
          finishActivity();
          return;
        }
        this.waitTime = (-100 + this.waitTime);
        if (this.waitTime <= 0)
        {
          Logger.i("SplashActivity", "waitActivityStartAndFinishFor ? timeout!!");
          finishActivity();
          return;
        }
        if (BaseActivity.activityStackDepth() < 0)
        {
          new Handler(SplashActivity.this.getMainLooper()).postDelayed(this, Math.min(this.waitTime, 100));
          return;
        }
        Logger.i("SplashActivity", "waitActivityStartAndFinishFor ? success!!");
        finishActivity();
      }
    }
    , 100L);
  }

  public void doErrorLogic(ErrorCode paramErrorCode)
  {
    Logger.i("SplashActivity", "错误：" + paramErrorCode.getErrorMsg());
    this.mHandler.post(new ShowDialog(paramErrorCode));
  }

  public void doTheNextThings()
  {
    startNextActivity();
    MarlinDrmManager.getInstance().init();
  }

  public void doUpgrade(final Version paramVersion)
  {
    if (paramVersion == null)
    {
      this.iService.skipThisUpgrade();
      return;
    }
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        if ("force".equals(paramVersion.appVersion.type))
        {
          Logger.i("SplashActivity", "mVersion.appVersion.type ==" + paramVersion.appVersion.type);
          if (!SplashActivity.this.startUpgradeActivity(paramVersion.appVersion.url))
          {
            SplashActivity.this.iService.skipThisUpgrade();
            return;
          }
          SplashActivity.this.finish();
          return;
        }
        SplashActivity.this.showUpgradeChoiceDialog(paramVersion);
      }
    });
  }

  public void finish()
  {
    super.finish();
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    Logger.i("SplashActivity", "onActivityResult");
    if (this.state == StartState.SS_WAIT_CONFIG)
    {
      Logger.i("SplashActivity", "Start NX");
      this.tickAfterCreate = 0L;
      this.state = StartState.SS_NX;
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Intent localIntent = getIntent();
    String str = localIntent.getAction();
    if ((!TextUtils.isEmpty(str)) && (str.startsWith(getPackageName())))
      this.isFromOut = true;
    Logger.i("SplashActivity", "onCreate intent.getExtras()=" + localIntent.getExtras() + "," + localIntent.getClass());
    if (this.isFromOut)
    {
      initReportPageInfo("");
      GlobalLogic.getInstance().setIsFromOut(true);
      if (GlobalLogic.getInstance().isProcessIntent(localIntent))
      {
        BussinesMessage localBussinesMessage = new BussinesMessage(this);
        localBussinesMessage.setIntent(localIntent);
        MessageHandler.instance().doNotify(localBussinesMessage);
        CommonRecevier.processReceiveIntentReport(localIntent, this);
      }
    }
    while (true)
    {
      reportLoad(true, null);
      return;
      GlobalLogic.getInstance().setIsFromOut(false);
      initXul("SplashPage");
      xulCommonHandler.uiRun(new Runnable()
      {
        public void run()
        {
          SplashActivity.access$500();
        }
      }
      , 15000);
    }
  }

  protected void onDestroy()
  {
    this.foreground = false;
    Logger.i("Application", "SplashActivity onDestroy");
    if (this.flagToUnregisterService)
    {
      if ((this.iService != null) && (this.conn != null))
      {
        unbindService(this.conn);
        this.iService = null;
      }
      stopService(new Intent(this, InitService.class));
    }
    this.hasDialog = false;
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      Logger.i("SplashActivity", "splash onKeyDown");
      finish();
      System.exit(0);
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  protected void onPause()
  {
    super.onPause();
    if (this.homePressReceiver != null)
    {
      unregisterReceiver(this.homePressReceiver);
      this.homePressReceiver = null;
    }
  }

  protected void onRestart()
  {
    super.onRestart();
    reportLoad(true, null);
  }

  protected void onResume()
  {
    this.needRegReservationReceiver = false;
    this.needAddObserver = false;
    Logger.d("SplashActivity", "ExternalRequest,Spalash onResume() needRegReservationReceiver=false,isForeground:" + isForeground());
    super.onResume();
    this.foreground = true;
    IntentFilter localIntentFilter = new IntentFilter("android.intent.action.ENTER_HOME");
    if (this.homePressReceiver == null)
      this.homePressReceiver = new HomePressBroadcastReceiver(null);
    registerReceiver(this.homePressReceiver, localIntentFilter);
  }

  protected void onStop()
  {
    super.onStop();
    reportExit(true, null);
  }

  protected void refreshViews()
  {
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    if ("load".equals(paramString2))
    {
      if (!this.isFromOut)
      {
        if (!GlobalEnv.getInstance().isBootAdEnabled())
          break label90;
        if (!GeneralUtils.isBootAdImageAvailable())
          break label74;
        this.bootAdStartTime = System.currentTimeMillis();
        Logger.i("SplashActivity", "boot ad start showing. bootAdStartTime = " + this.bootAdStartTime);
        switchToAdMode(paramXulView);
      }
      while (true)
      {
        return true;
        label74: Logger.w("SplashActivity", "bootadimage not available, so we goto show logo.");
        switchToLogoMode(paramXulView);
        continue;
        label90: switchToLogoMode(paramXulView);
      }
    }
    return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
  }

  protected void xulOnRenderIsReady()
  {
    super.xulOnRenderIsReady();
    if ((App.isInitCompleted()) && (!App.isNeedRequestEpg()))
    {
      this.foreground = true;
      doTheNextThings();
      this.flagToUnregisterService = false;
      return;
    }
    xulCommonHandler.uiRun(new Runnable()
    {
      public void run()
      {
        Intent localIntent = new Intent(SplashActivity.this, InitService.class);
        SplashActivity.this.bindService(localIntent, SplashActivity.this.conn, 1);
        SplashActivity.this.startService(localIntent);
      }
    }
    , 50);
  }

  public void xulOnRenderShow()
  {
    XulView localXulView = xulFindViewById("wave-layer");
    if (localXulView != null)
    {
      localXulView.addClass("wave-anim");
      localXulView.resetRender();
    }
    super.xulOnRenderShow();
  }

  private final class GoToActivity
    implements Runnable
  {
    private Intent intent;

    public GoToActivity(Intent arg2)
    {
      Object localObject;
      if (SplashActivity.this.getIntent().hasExtra("cmd_is_from_out"))
        localObject.putExtra("cmd_is_from_out", "cmd_is_from_out");
      if (SplashActivity.this.getIntent().hasExtra("flag_action_from_mgtv"))
        localObject.removeExtra("cmd_is_from_out");
      this.intent = localObject;
    }

    public void run()
    {
      if ((this.intent != null) && (SplashActivity.this.isForeground()))
      {
        Logger.d("SplashActivity", "ExternalRequest,Runable startActivity");
        this.intent.addFlags(8388608);
        SplashActivity.this.startActivity(this.intent);
        return;
      }
      Logger.d("SplashActivity", "ExternalRequest,Runnable isForeground=false cancel startActivity");
    }
  }

  private final class HomePressBroadcastReceiver extends BroadcastReceiver
  {
    private HomePressBroadcastReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      SplashActivity.access$1102(SplashActivity.this, false);
      Logger.d("SplashActivity", "ExternalRequest,HomePressBroadcastReceiver onReceive foreground:" + SplashActivity.this.foreground);
    }
  }

  class OnApiOk
    implements InitApiData.onApiOKListener
  {
    OnApiOk()
    {
    }

    public void onApiOk(int paramInt)
    {
      SplashActivity.this.startMainActivity();
    }

    public void onGetApiDataError()
    {
    }

    public void onNeedFinishActivity()
    {
    }
  }

  private final class ShowDialog
    implements Runnable
  {
    private ErrorCode errorCode;

    public ShowDialog(ErrorCode arg2)
    {
      Object localObject;
      this.errorCode = localObject;
    }

    public void run()
    {
      if (this.errorCode != null)
      {
        if (InitService.Error.ERROR_SETTING_SERVICE.getErrorCode() != this.errorCode.getErrorCode())
          break label61;
        Bundle localBundle1 = new Bundle();
        localBundle1.putString("Message", "找不到关键组件:SettingService！");
        if (!SplashActivity.this.isFinishing())
          SplashActivity.this.showDialog(10, localBundle1);
      }
      label61: 
      do
      {
        do
        {
          do
          {
            Bundle localBundle2;
            do
            {
              return;
              if (InitService.Error.ERROR_TASK_SERVICE.getErrorCode() != this.errorCode.getErrorCode())
                break;
              localBundle2 = new Bundle();
              localBundle2.putString("Message", "找不到关键组件:TaskService！");
            }
            while (SplashActivity.this.isFinishing());
            SplashActivity.this.showDialog(10, localBundle2);
            return;
            if (InitService.Error.ERROR_NETSTATE.getErrorCode() != this.errorCode.getErrorCode())
              break;
          }
          while (!AppFuncCfg.FUNCTION_MAIN_SETTING_PAGE);
          Logger.i("SplashActivity", "start --- NetworkErrorActivity");
          Intent localIntent7 = new Intent();
          localIntent7.setClass(SplashActivity.this, NetworkErrorActivity.class);
          localIntent7.putExtra("page_type", NetworkErrorActivity.PageType.NETWORK_ERROR.ordinal());
          SplashActivity.this.startActivityForResult(localIntent7, 1);
          SplashActivity.this.finish();
          return;
          if (InitService.Error.ERROR_CONNECT_FAILED.getErrorCode() != this.errorCode.getErrorCode())
            break;
        }
        while (!GlobalEnv.getInstance().isShowSettingPage());
        if (AppFuncCfg.FUNCTION_MAIN_SETTING_PAGE)
        {
          Logger.e("SplashActivity", "processN1A1Msg show main config and wait config");
          Intent localIntent6 = new Intent();
          localIntent6.setClass(SplashActivity.this, NetworkErrorActivity.class);
          localIntent6.putExtra("page_type", NetworkErrorActivity.PageType.NETWORK_ERROR.ordinal());
          localIntent6.setFlags(8388608);
          SplashActivity.this.startActivityForResult(localIntent6, 0);
          SplashActivity.this.finish();
          return;
        }
        Logger.e("SplashActivity", "processN1A1Msg connect epg server error");
        SplashActivity.this.showErrorDialogWithReport(10, "1002005", "errorCode: " + InitService.Error.ERROR_CONNECT_FAILED.getErrorCode() + ", errorMsg: " + InitService.Error.ERROR_CONNECT_FAILED.getErrorMsg(), "");
        return;
        if (InitService.Error.ERROR_MAC.getErrorCode() == this.errorCode.getErrorCode())
        {
          Intent localIntent1 = new Intent();
          localIntent1.setClass(SplashActivity.this, NetworkErrorActivity.class);
          localIntent1.putExtra("page_type", NetworkErrorActivity.PageType.MAC_ERROR.ordinal());
          localIntent1.setFlags(8388608);
          SplashActivity.this.mHandler.post(new SplashActivity.GoToActivity(SplashActivity.this, localIntent1));
          SplashActivity.this.finish();
          return;
        }
        if (InitService.Error.ERROR_LICENSE.getErrorCode() == this.errorCode.getErrorCode())
        {
          Intent localIntent2 = new Intent();
          localIntent2.setClass(SplashActivity.this, NetworkErrorActivity.class);
          localIntent2.putExtra("page_type", NetworkErrorActivity.PageType.LICENSE_ERROR.ordinal());
          localIntent2.setFlags(8388608);
          SplashActivity.this.mHandler.post(new SplashActivity.GoToActivity(SplashActivity.this, localIntent2));
          SplashActivity.this.finish();
          return;
        }
        if (InitService.Error.ERROR_IP_LIMITED.getErrorCode() == this.errorCode.getErrorCode())
        {
          Intent localIntent3 = new Intent();
          localIntent3.setClass(SplashActivity.this, NetworkErrorActivity.class);
          localIntent3.putExtra("page_type", NetworkErrorActivity.PageType.IP_LIMIT.ordinal());
          localIntent3.setFlags(8388608);
          SplashActivity.this.mHandler.post(new SplashActivity.GoToActivity(SplashActivity.this, localIntent3));
          SplashActivity.this.finish();
          return;
        }
        if (InitService.Error.ERROR_GET_USERINFO.getErrorCode() == this.errorCode.getErrorCode())
        {
          if (AppFuncCfg.FUNCTION_MAIN_SETTING_PAGE)
          {
            Intent localIntent5 = new Intent();
            localIntent5.setClass(SplashActivity.this, NetworkErrorActivity.class);
            localIntent5.putExtra("page_type", NetworkErrorActivity.PageType.NETWORK_ERROR.ordinal());
            localIntent5.setFlags(8388608);
            SplashActivity.this.startActivityForResult(localIntent5, 1);
            SplashActivity.this.finish();
            return;
          }
          SplashActivity.this.showErrorDialogWithReport(10, "1002005", "errorCode: " + InitService.Error.ERROR_GET_USERINFO.getErrorCode() + ", errorMsg: " + InitService.Error.ERROR_GET_USERINFO.getErrorMsg(), "");
          return;
        }
        if (InitService.Error.ERROR_GET_EPGDATA.getErrorCode() == this.errorCode.getErrorCode())
        {
          SplashActivity.this.showErrorDialogWithReport(10, "1002005", "errorCode: " + InitService.Error.ERROR_GET_EPGDATA.getErrorCode() + ", errorMsg: " + InitService.Error.ERROR_GET_EPGDATA.getErrorMsg(), "");
          return;
        }
        if (InitService.Error.ERROR_HANDLE_OLDVERSION.getErrorCode() == this.errorCode.getErrorCode())
        {
          Intent localIntent4 = new Intent();
          localIntent4.setClass(SplashActivity.this, NetworkErrorActivity.class);
          localIntent4.putExtra("page_type", NetworkErrorActivity.PageType.MAC_ERROR.ordinal());
          localIntent4.setFlags(8388608);
          SplashActivity.this.mHandler.post(new SplashActivity.GoToActivity(SplashActivity.this, localIntent4));
          SplashActivity.this.finish();
          return;
        }
      }
      while (InitService.Error.OTHER_ERROR.getErrorCode() != this.errorCode.getErrorCode());
      SplashActivity.this.showErrorDialogWithReport(10, "1001001", "errorCode: " + InitService.Error.OTHER_ERROR.getErrorCode() + ", errorMsg: " + InitService.Error.OTHER_ERROR.getErrorMsg(), "");
    }
  }

  public static enum StartState
  {
    static
    {
      SS_WAIT_NET = new StartState("SS_WAIT_NET", 2);
      SS_WAIT_CONFIG = new StartState("SS_WAIT_CONFIG", 3);
      SS_NX = new StartState("SS_NX", 4);
      SS_NX_RUN = new StartState("SS_NX_RUN", 5);
      SS_NX_WAIT_CHOICE_UPDATE = new StartState("SS_NX_WAIT_CHOICE_UPDATE", 6);
      SS_ERROR = new StartState("SS_ERROR", 7);
      SS_FINISH = new StartState("SS_FINISH", 8);
      StartState[] arrayOfStartState = new StartState[9];
      arrayOfStartState[0] = SS_START;
      arrayOfStartState[1] = SS_WAIT_SERVICE_INIT;
      arrayOfStartState[2] = SS_WAIT_NET;
      arrayOfStartState[3] = SS_WAIT_CONFIG;
      arrayOfStartState[4] = SS_NX;
      arrayOfStartState[5] = SS_NX_RUN;
      arrayOfStartState[6] = SS_NX_WAIT_CHOICE_UPDATE;
      arrayOfStartState[7] = SS_ERROR;
      arrayOfStartState[8] = SS_FINISH;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.SplashActivity
 * JD-Core Version:    0.6.2
 */