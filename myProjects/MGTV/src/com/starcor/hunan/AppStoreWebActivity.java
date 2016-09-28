package com.starcor.hunan;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.widget.ExtWebView;
import com.starcor.hunan.widget.ExtWebView.JSCallback;
import com.starcor.hunan.widget.ExtWebView.jsExtObject;
import com.starcor.mgtv.boss.WebUrlBuilder;
import com.starcor.sccms.api.SccmsApiCheckValidByWebTokenTask.ISccmsApiCheckValidByWebTokenTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.service.ApplicationInfo;
import com.starcor.service.DownloadTaskInfo;
import com.starcor.service.IDownloadEventListener.Stub;
import com.starcor.service.helper.AppManager;
import com.starcor.service.helper.DownloadManager;
import com.starcor.xul.XulUtils;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"SetJavaScriptEnabled"})
public class AppStoreWebActivity extends BaseActivity
{
  public static final String EXTRA_INFO_PACKAGE_NAME = "package_name";
  private static final int MESSAGE_DISMISS_DIALOG = 1000;
  private static final String TAG = AppStoreWebActivity.class.getSimpleName();
  public static final String TASK_TAG = "APP_STORE";
  static IntentFilter appEventFilter;
  static BroadcastReceiver appEventReceiver;
  private static AppManager appMgr = new AppManager();
  private static DownloadManager downloadMgr = new DownloadManager();
  private static HashMap<String, PendingTaskInfo> pendingTasks = new HashMap();
  private MetadataInfo info;
  private WebViewClient mClient = new WebViewClient()
  {
    public void onLoadResource(WebView paramAnonymousWebView, String paramAnonymousString)
    {
    }

    public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
    {
      AppStoreWebActivity.this.mHandler.sendEmptyMessageDelayed(1000, 1000L);
      super.onPageFinished(paramAnonymousWebView, paramAnonymousString);
    }

    public void onReceivedError(WebView paramAnonymousWebView, int paramAnonymousInt, String paramAnonymousString1, String paramAnonymousString2)
    {
      AppStoreWebActivity.this.dismissDialog(5);
    }
  };
  private Context mContext = this;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if ((paramAnonymousMessage.what == 1000) && (AppStoreWebActivity.this.hasDialog))
      {
        AppStoreWebActivity.access$302(AppStoreWebActivity.this, false);
        AppStoreWebActivity.this.dismissDialog(5);
      }
    }
  };
  private ExtWebView mWeb;
  private boolean needDrawWebViewBg;
  private BroadcastReceiver remoteDataReceiver = null;

  static
  {
    appEventReceiver = new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
      {
        String str1 = paramAnonymousIntent.getAction();
        String str2 = paramAnonymousIntent.getDataString();
        Logger.d(AppStoreWebActivity.TAG, "appEventReceiver : " + str1 + " data: " + str2);
        String str3;
        try
        {
          str3 = str2.split(":")[1].trim();
          Logger.d(AppStoreWebActivity.TAG, "    package: " + str3);
          if ("android.intent.action.PACKAGE_ADDED".equals(str1))
          {
            AppStoreWebActivity.removePendingPackage(str3);
            AppStoreWebActivity.removeDownloadTaskByPackage(str3);
            return;
          }
          if ("android.intent.action.PACKAGE_REPLACED".equals(str1))
          {
            AppStoreWebActivity.removePendingPackage(str3);
            AppStoreWebActivity.removeDownloadTaskByPackage(str3);
            return;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          return;
        }
        if ("android.intent.action.PACKAGE_REMOVED".equals(str1))
        {
          AppStoreWebActivity.removePendingPackage(str3);
          AppStoreWebActivity.removeDownloadTaskByPackage(str3);
        }
      }
    };
    appEventFilter = new IntentFilter();
    appEventFilter.addDataScheme("package");
    appEventFilter.addAction("android.intent.action.PACKAGE_ADDED");
    appEventFilter.addAction("android.intent.action.PACKAGE_REPLACED");
    appEventFilter.addAction("android.intent.action.PACKAGE_REMOVED");
    App.getAppContext().registerReceiver(appEventReceiver, appEventFilter);
    downloadMgr.setEventListener(new IDownloadEventListener.Stub()
    {
      public int onEvent(String paramAnonymousString, int paramAnonymousInt, DownloadTaskInfo paramAnonymousDownloadTaskInfo)
        throws RemoteException
      {
        if ("success".equals(paramAnonymousDownloadTaskInfo.getState()));
        while (true)
        {
          try
          {
            AppStoreWebActivity.appendPendingTask(paramAnonymousDownloadTaskInfo.getExtInfo().getString("package_name"), 4097);
            AppStoreWebActivity.appMgr.installApp(paramAnonymousDownloadTaskInfo.getLocalFile());
            return 0;
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
            continue;
          }
          if (!"failed".equals(paramAnonymousDownloadTaskInfo.getState()));
        }
      }
    });
  }

  static ApplicationInfo appInfoFromDownloadTask(ApplicationInfo paramApplicationInfo, String paramString, DownloadTaskInfo paramDownloadTaskInfo)
  {
    paramApplicationInfo.state = paramDownloadTaskInfo.getState();
    paramApplicationInfo.is_installed = false;
    paramApplicationInfo.url = paramDownloadTaskInfo.getUrl();
    if (TextUtils.isEmpty(paramApplicationInfo.file))
      paramApplicationInfo.file = paramDownloadTaskInfo.getLocalFile();
    paramApplicationInfo.pkgName = paramString;
    try
    {
      paramApplicationInfo.ext_info.put("download_size", (int)paramDownloadTaskInfo.getDownloadSize());
      paramApplicationInfo.ext_info.put("total_size", (int)paramDownloadTaskInfo.getTotalSize());
      return paramApplicationInfo;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return paramApplicationInfo;
  }

  static PendingTaskInfo appendPendingTask(String paramString, int paramInt)
  {
    synchronized (pendingTasks)
    {
      PendingTaskInfo localPendingTaskInfo = (PendingTaskInfo)pendingTasks.put(paramString, new PendingTaskInfo(paramString, paramInt));
      return localPendingTaskInfo;
    }
  }

  private void checkValidByWebToken(String paramString)
  {
    GlobalLogic.getInstance().userWebLogined(paramString);
    ServerApiManager.i().APICheckValidByWebToken(new SccmsApiCheckValidByWebTokenTask.ISccmsApiCheckValidByWebTokenTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e(AppStoreWebActivity.TAG, "登陆失败");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserInfo paramAnonymousUserInfo)
      {
        if (paramAnonymousUserInfo != null)
        {
          Logger.d(AppStoreWebActivity.TAG, "reason=" + paramAnonymousUserInfo.reason);
          GlobalLogic.getInstance().userLogin(paramAnonymousUserInfo);
        }
      }
    });
  }

  static DownloadTaskInfo findDownloadTaskByPackageName(String paramString)
  {
    int[] arrayOfInt = downloadMgr.getTaskList("APP_STORE");
    if (arrayOfInt == null)
      return null;
    int i = arrayOfInt.length;
    int j = 0;
    if (j < i)
    {
      int k = arrayOfInt[j];
      DownloadTaskInfo localDownloadTaskInfo = downloadMgr.getTaskInfo(k);
      if (localDownloadTaskInfo == null);
      while (true)
      {
        j++;
        break;
        JSONObject localJSONObject = localDownloadTaskInfo.getExtInfo();
        try
        {
          boolean bool = paramString.equals(localJSONObject.getString("package_name"));
          if (bool)
            return localDownloadTaskInfo;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    }
    return null;
  }

  static PendingTaskInfo getPendingPackageInfo(String paramString)
  {
    synchronized (pendingTasks)
    {
      PendingTaskInfo localPendingTaskInfo = (PendingTaskInfo)pendingTasks.get(paramString);
      return localPendingTaskInfo;
    }
  }

  static boolean isPackagePending(String paramString)
  {
    PendingTaskInfo localPendingTaskInfo;
    synchronized (pendingTasks)
    {
      localPendingTaskInfo = (PendingTaskInfo)pendingTasks.get(paramString);
      if (localPendingTaskInfo == null)
        return false;
      if (localPendingTaskInfo.opType == 4099)
        return false;
    }
    boolean bool1 = localPendingTaskInfo.isExpired();
    boolean bool2 = false;
    if (!bool1)
      bool2 = true;
    return bool2;
  }

  private static boolean removeDownloadTaskByPackage(String paramString)
  {
    DownloadTaskInfo localDownloadTaskInfo = findDownloadTaskByPackageName(paramString);
    if (localDownloadTaskInfo != null)
    {
      downloadMgr.removeTask(localDownloadTaskInfo.getTaskId());
      try
      {
        new File(localDownloadTaskInfo.getLocalFile()).delete();
        return true;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
    return false;
  }

  static void removePendingPackage(String paramString)
  {
    synchronized (pendingTasks)
    {
      pendingTasks.remove(paramString);
      return;
    }
  }

  public void finish()
  {
    try
    {
      if (this.remoteDataReceiver != null)
        this.mContext.unregisterReceiver(this.remoteDataReceiver);
      super.finish();
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      while (true)
        localIllegalArgumentException.printStackTrace();
    }
  }

  public void onBackPressed()
  {
    if (AppFuncCfg.FUNCTION_GOTO_MAIN_IF_FROM_OUT)
      gotoMainActivityIfFromOut(false);
    super.onBackPressed();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    initXul("CommonPage");
    xulHideTitle();
  }

  protected void onDestroy()
  {
    this.hasDialog = false;
    try
    {
      if (this.remoteDataReceiver != null)
        this.mContext.unregisterReceiver(this.remoteDataReceiver);
      super.onDestroy();
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      while (true)
        localIllegalArgumentException.printStackTrace();
    }
  }

  protected void onProgressDialogDismissWithBackPressed()
  {
    onBackPressed();
  }

  protected void xulOnRenderIsReady()
  {
    setContentView(2130903051);
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131165256);
    this.mWeb = new ExtWebView(this.context)
    {
      protected ExtWebView.jsExtObject createExtObject()
      {
        // Byte code:
        //   0: new 22	com/starcor/hunan/AppStoreWebActivity$5$1
        //   3: dup
        //   4: aload_0
        //   5: invokespecial 25	com/starcor/hunan/AppStoreWebActivity$5$1:<init>	(Lcom/starcor/hunan/AppStoreWebActivity$5;)V
        //   8: areturn
      }

      public void onCloseBrowser(String paramAnonymousString)
      {
        Logger.i(AppStoreWebActivity.TAG, "close web reason :" + paramAnonymousString);
        AppStoreWebActivity.this.finish();
      }

      protected void onDraw(Canvas paramAnonymousCanvas)
      {
        super.onDraw(paramAnonymousCanvas);
        if (AppStoreWebActivity.this.needDrawWebViewBg)
          paramAnonymousCanvas.drawColor(-14342875);
      }

      public void onMoveBrowser(double paramAnonymousDouble1, double paramAnonymousDouble2)
      {
        Logger.i(AppStoreWebActivity.TAG, "full web activity cannot move");
      }

      public void onMoveBrowser(double paramAnonymousDouble1, double paramAnonymousDouble2, double paramAnonymousDouble3, double paramAnonymousDouble4)
      {
        Logger.i(AppStoreWebActivity.TAG, "full web activity cannot move");
      }

      public void onReceiveMessage(String paramAnonymousString, Object paramAnonymousObject)
      {
        Logger.d(AppStoreWebActivity.TAG, paramAnonymousString + ":" + paramAnonymousObject.toString());
        if (paramAnonymousString.equals("onLogin"))
          if ((paramAnonymousObject instanceof JSONObject))
          {
            String str = ((JSONObject)paramAnonymousObject).optString("web_token");
            Logger.d("得到登录后的web_token=" + str);
            AppStoreWebActivity.this.checkValidByWebToken(str);
          }
        do
        {
          do
          {
            do
            {
              do
              {
                do
                {
                  return;
                  if (paramAnonymousString.equals("onLogout"))
                  {
                    GlobalLogic.getInstance().userLogout();
                    return;
                  }
                }
                while (paramAnonymousString.equals("onPurchases"));
                if (!paramAnonymousString.equals("parent"))
                  break;
              }
              while ((!isChild()) || (getParentWeb() == null));
              if ((paramAnonymousObject instanceof String))
              {
                getParentWeb().sendMessage("child", (String)paramAnonymousObject);
                return;
              }
            }
            while (!(paramAnonymousObject instanceof JSONObject));
            getParentWeb().sendMessage("child", (JSONObject)paramAnonymousObject);
            return;
          }
          while ((!paramAnonymousString.equals("child")) || (isChild()) || (getChildWeb() == null));
          if ((paramAnonymousObject instanceof String))
          {
            getChildWeb().sendMessage("parent", (String)paramAnonymousObject);
            return;
          }
        }
        while (!(paramAnonymousObject instanceof JSONObject));
        getChildWeb().sendMessage("parent", (JSONObject)paramAnonymousObject);
      }

      public void onResizeBrowser(double paramAnonymousDouble1, double paramAnonymousDouble2)
      {
        Logger.i(AppStoreWebActivity.TAG, "full web activity cannot resize");
      }

      public void onSetHandler(String paramAnonymousString, final ExtWebView.JSCallback paramAnonymousJSCallback)
      {
        if ("Broadcast".equals(paramAnonymousString))
          AppStoreWebActivity.access$602(AppStoreWebActivity.this, new BroadcastReceiver()
          {
            public void onReceive(Context paramAnonymous2Context, Intent paramAnonymous2Intent)
            {
              if (paramAnonymousJSCallback != null)
              {
                String str1 = paramAnonymous2Intent.getStringExtra("key");
                String str2 = paramAnonymous2Intent.getStringExtra("from");
                paramAnonymousJSCallback.run(new Object[] { str2, str1 });
              }
            }
          });
        try
        {
          IntentFilter localIntentFilter = new IntentFilter("com.starcor.remoteDataCallback");
          AppStoreWebActivity.this.mContext.registerReceiver(AppStoreWebActivity.this.remoteDataReceiver, localIntentFilter);
          return;
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          localIllegalArgumentException.printStackTrace();
        }
      }

      public void onShowBrowser(double paramAnonymousDouble1, double paramAnonymousDouble2)
      {
      }
    };
    localLinearLayout.addView(this.mWeb);
    this.info = ((MetadataInfo)getIntent().getSerializableExtra("MetaDataInfo"));
    xulHideTitle();
    xulUpdateTitle(this.info.name, GeneralUtils.conversionFirstLetter(this.info.packet_id));
    WebSettings localWebSettings = this.mWeb.getSettings();
    localWebSettings.setCacheMode(-1);
    String str = WebUrlBuilder.getMainWebUrl(this.info.url, "");
    this.mWeb.loadUrl(str);
    this.mWeb.setWebViewClient(this.mClient);
    if (!isFinishing())
    {
      showDialog(5, null);
      this.hasDialog = true;
    }
    localWebSettings.setJavaScriptEnabled(true);
    localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    localWebSettings.setAllowFileAccess(true);
    this.mWeb.requestFocus();
    this.needDrawWebViewBg = true;
    super.xulOnRenderIsReady();
  }

  static class PendingTaskInfo
  {
    public static final int OP_DOWNLOAD = 4099;
    public static final int OP_INSTALL = 4097;
    public static final int OP_UNINSTALL = 4098;
    public long opTime;
    public int opType = 0;
    public String pkgName;

    public PendingTaskInfo(String paramString, int paramInt)
    {
      this.pkgName = paramString;
      this.opType = paramInt;
      this.opTime = XulUtils.timestamp();
    }

    public boolean isExpired()
    {
      long l = XulUtils.timestamp() - this.opTime;
      if (this.opType == 4099)
        if (l <= 3600000L);
      while (l > 30000L)
      {
        return true;
        return false;
      }
      return false;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.AppStoreWebActivity
 * JD-Core Version:    0.6.2
 */