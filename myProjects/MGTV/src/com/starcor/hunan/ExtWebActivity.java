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
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.ApiTask;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.epgapi.params.GetFilmIdParams;
import com.starcor.core.epgapi.params.GetFilmInfoParams;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.parser.sax.GetFilmIdSAXParser;
import com.starcor.core.parser.sax.GetFilmInfoSAXParser;
import com.starcor.core.service.DownLoadService;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.hunan.widget.ExtWebView;
import com.starcor.hunan.widget.ExtWebView.JSCallback;
import com.starcor.mgtv.boss.WebUrlBuilder;
import com.starcor.report.ReportArea;
import com.starcor.report.ReportInfo;
import com.starcor.sccms.api.SccmsApiCheckValidByWebTokenTask.ISccmsApiCheckValidByWebTokenTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.net.URLDecoder;
import org.json.JSONObject;

@SuppressLint({"SetJavaScriptEnabled"})
public class ExtWebActivity extends BaseActivity
{
  private static final int MESSAGE_DISMISS_DIALOG = 1000;
  private static final int MESSAGE_GET_VIDEO_ID = 1;
  private static final int MESSAGE_GET_VIDEO_INFO = 2;
  private static final String TAG = "ExtWebActivity";
  private MetadataInfo info;
  private boolean isLoadSuccess = false;
  private boolean isNeedReportLoad = false;
  private boolean isOpenPageSuccess = false;
  private boolean isReceivedError = false;
  private WebViewClient mClient = new WebViewClient()
  {
    private void getVideoInfoById(String paramAnonymousString)
    {
      String str1 = subData(paramAnonymousString, "asset_id");
      String str2 = subData(paramAnonymousString, "index");
      Logger.i("ExtWebActivity", "getVideoInfoById  asset_id:" + str1 + "  index_id:" + str2);
      GetFilmIdParams localGetFilmIdParams = new GetFilmIdParams(str1, Integer.parseInt(str2));
      GetFilmIdSAXParser localGetFilmIdSAXParser = new GetFilmIdSAXParser();
      ApiTask localApiTask = new ApiTask();
      localApiTask.setApi(localGetFilmIdParams);
      localApiTask.setParser(localGetFilmIdSAXParser);
      localApiTask.setHandler(ExtWebActivity.this.mHandler);
      localApiTask.setMessageNumber(1);
      App.getInstance().getTaskService().addTask(localApiTask);
    }

    private boolean startVipListActivity(String paramAnonymousString)
    {
      String str1 = subData(paramAnonymousString, "svc_item_id");
      String str2 = subData(paramAnonymousString, "category_id");
      String str3 = URLDecoder.decode(subData(paramAnonymousString, "name"));
      MetadataInfo localMetadataInfo = new MetadataInfo();
      localMetadataInfo.packet_id = str1;
      localMetadataInfo.category_id = str2;
      localMetadataInfo.name = str3;
      Intent localIntent = new Intent(ExtWebActivity.this, ActivityAdapter.getInstance().getVideoListActivity());
      localIntent.putExtra("Type", 0);
      localIntent.putExtra("MetaDataInfo", localMetadataInfo);
      localIntent.setFlags(8388608);
      localIntent.setClass(ExtWebActivity.this, ActivityAdapter.getInstance().getVideoListActivity());
      localIntent.addFlags(8388608);
      ExtWebActivity.this.startActivity(localIntent);
      return true;
    }

    public void onLoadResource(WebView paramAnonymousWebView, String paramAnonymousString)
    {
      Logger.i("onLoadResource 加载了url" + paramAnonymousString);
    }

    public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
    {
      Logger.i("onPageFinished 加载了url" + paramAnonymousString);
      ExtWebActivity.access$302(ExtWebActivity.this, true);
      if (ExtWebActivity.this.isNeedReportLoad)
      {
        ExtWebActivity.access$502(ExtWebActivity.this, true);
        ExtWebActivity.this.reportLoad(ExtWebActivity.this.isLoadSuccess, new String[] { paramAnonymousString });
        ExtWebActivity.access$402(ExtWebActivity.this, false);
      }
      ExtWebActivity.this.mHandler.sendEmptyMessageDelayed(1000, 1000L);
      super.onPageFinished(paramAnonymousWebView, paramAnonymousString);
    }

    public void onReceivedError(WebView paramAnonymousWebView, int paramAnonymousInt, String paramAnonymousString1, String paramAnonymousString2)
    {
      if (ExtWebActivity.this.isNeedReportLoad)
      {
        ExtWebActivity.access$502(ExtWebActivity.this, false);
        ExtWebActivity localExtWebActivity = ExtWebActivity.this;
        boolean bool = ExtWebActivity.this.isLoadSuccess;
        String[] arrayOfString = new String[1];
        arrayOfString[0] = ExtWebActivity.this.url;
        localExtWebActivity.reportLoad(bool, arrayOfString);
        ExtWebActivity.access$402(ExtWebActivity.this, false);
      }
      ExtWebActivity.this.dismissDialog(5);
    }

    public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
    {
      Logger.i("ExtWebActivity", "shouldOverrideUrlLoading overload=" + paramAnonymousString);
      if (paramAnonymousString.startsWith("mangofunc://product_content?"))
        return startVipListActivity(paramAnonymousString);
      if (paramAnonymousString.startsWith("mangofunc://open_video?"))
      {
        getVideoInfoById(paramAnonymousString);
        return true;
      }
      return false;
    }

    public String subData(String paramAnonymousString1, String paramAnonymousString2)
    {
      String str1 = "";
      String str2;
      if (paramAnonymousString1.contains(paramAnonymousString2))
      {
        str2 = paramAnonymousString1.substring(paramAnonymousString1.indexOf(paramAnonymousString2));
        if (str2.contains("&"))
          str1 = str2.split("&")[0].split("=")[1];
      }
      else
      {
        return str1;
      }
      return str2.split("=")[1];
    }
  };
  private Context mContext = this;
  private Handler mHandler = new Handler()
  {
    private void getVideoInfo(Message paramAnonymousMessage)
    {
      VideoInfo localVideoInfo = (VideoInfo)paramAnonymousMessage.obj;
      Logger.i("ExtWebActivitygetVideoInfo  info" + localVideoInfo.toString());
      GetFilmInfoParams localGetFilmInfoParams = new GetFilmInfoParams(localVideoInfo.videoId, localVideoInfo.videoType);
      GetFilmInfoSAXParser localGetFilmInfoSAXParser = new GetFilmInfoSAXParser();
      ApiTask localApiTask = new ApiTask();
      localApiTask.setApi(localGetFilmInfoParams);
      localApiTask.setParser(localGetFilmInfoSAXParser);
      localApiTask.setHandler(ExtWebActivity.this.mHandler);
      localApiTask.setMessageNumber(2);
      App.getInstance().getTaskService().addTask(localApiTask);
    }

    private void playVideo(Message paramAnonymousMessage)
    {
      VideoInfo localVideoInfo = (VideoInfo)paramAnonymousMessage.obj;
      Logger.d("newInfo=" + localVideoInfo.toString());
      PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
      localPlayerIntentParams.nns_index = 0;
      localPlayerIntentParams.nns_videoInfo = localVideoInfo;
      Logger.i("ExtWebActivity", "playVideo nns_index:" + localPlayerIntentParams.nns_index + "  playerInfo.nns_videoInfo" + localPlayerIntentParams.nns_videoInfo.toString());
      Intent localIntent = new Intent().setClass(ExtWebActivity.this, ActivityAdapter.getInstance().getMPlayer());
      localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
      localIntent.addFlags(8388608);
      ExtWebActivity.this.startActivity(localIntent);
    }

    public void handleMessage(Message paramAnonymousMessage)
    {
      if ((paramAnonymousMessage.what == 1) && (paramAnonymousMessage.obj != null))
        getVideoInfo(paramAnonymousMessage);
      if ((paramAnonymousMessage.what == 2) && (paramAnonymousMessage.obj != null))
        playVideo(paramAnonymousMessage);
      if ((paramAnonymousMessage.what == 1000) && (ExtWebActivity.this.hasDialog))
      {
        ExtWebActivity.access$002(ExtWebActivity.this, false);
        ExtWebActivity.this.dismissDialog(5);
        if (ExtWebActivity.this.mWeb != null)
          ExtWebActivity.this.mWeb.postInvalidate();
      }
    }
  };
  private ExtWebView mWeb;
  private boolean needDrawWebViewBg;
  private BroadcastReceiver remoteDataReceiver = null;
  private String url;

  private void checkValidByWebToken(String paramString)
  {
    GlobalLogic.getInstance().userWebLogined(paramString);
    ServerApiManager.i().APICheckValidByWebToken(new SccmsApiCheckValidByWebTokenTask.ISccmsApiCheckValidByWebTokenTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e("ExtWebActivity", "登陆失败");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserInfo paramAnonymousUserInfo)
      {
        if (paramAnonymousUserInfo != null)
        {
          Logger.d("ExtWebActivity", "reason=" + paramAnonymousUserInfo.reason);
          GlobalLogic.getInstance().userLogin(paramAnonymousUserInfo);
        }
      }
    });
  }

  private boolean handleKey(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 0)
    {
      this.mWeb.loadUrl("javascript:keymove('" + paramInt + "')");
      return true;
    }
    return false;
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if ((paramKeyEvent.getAction() == 0) && (paramKeyEvent.getKeyCode() == 4))
    {
      if ((DeviceInfo.isTclMango()) || (!this.isOpenPageSuccess))
        onBackPressed();
      if ((this.mWeb != null) && (!this.mWeb.hasFocus()))
        finish();
    }
    return super.dispatchKeyEvent(paramKeyEvent);
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
    String str = ReportArea.getValue(ExtWebActivity.class.getSimpleName());
    ReportInfo.getInstance().setEntranceSrc(str);
    this.isNeedReportLoad = true;
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

  protected void onRestart()
  {
    super.onRestart();
    boolean bool = this.isLoadSuccess;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.url;
    reportLoad(bool, arrayOfString);
    String str = ReportArea.getValue(ExtWebActivity.class.getSimpleName());
    ReportInfo.getInstance().setEntranceSrc(str);
  }

  protected void onResume()
  {
    super.onResume();
  }

  protected void onStop()
  {
    super.onStop();
    boolean bool = this.isLoadSuccess;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.url;
    reportExit(bool, arrayOfString);
  }

  protected void xulOnRenderIsReady()
  {
    setContentView(2130903051);
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131165256);
    this.mWeb = new ExtWebView(this.context)
    {
      public boolean dispatchKeyEvent(KeyEvent paramAnonymousKeyEvent)
      {
        boolean bool = ExtWebActivity.this.handleKey(paramAnonymousKeyEvent.getKeyCode(), paramAnonymousKeyEvent);
        return (super.handleKey(paramAnonymousKeyEvent)) || (bool);
      }

      public void onCloseBrowser(String paramAnonymousString)
      {
        Logger.i("ExtWebActivity", "close web reason :" + paramAnonymousString);
        ExtWebActivity.this.finish();
      }

      protected void onDraw(Canvas paramAnonymousCanvas)
      {
        super.onDraw(paramAnonymousCanvas);
        if (ExtWebActivity.this.needDrawWebViewBg)
          paramAnonymousCanvas.drawColor(-14342875);
      }

      public void onMoveBrowser(double paramAnonymousDouble1, double paramAnonymousDouble2)
      {
        Logger.i("ExtWebActivity", "full web activity cannot move");
      }

      public void onMoveBrowser(double paramAnonymousDouble1, double paramAnonymousDouble2, double paramAnonymousDouble3, double paramAnonymousDouble4)
      {
        Logger.i("ExtWebActivity", "full web activity cannot move");
      }

      public void onReceiveMessage(String paramAnonymousString, Object paramAnonymousObject)
      {
        Logger.d("ExtWebActivity", paramAnonymousString + ":" + paramAnonymousObject.toString());
        if (paramAnonymousString.equals("onLogin"))
          if ((paramAnonymousObject instanceof JSONObject))
          {
            String str = ((JSONObject)paramAnonymousObject).optString("web_token");
            Logger.d("得到登录后的web_token=" + str);
            ExtWebActivity.this.checkValidByWebToken(str);
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
        Logger.i("ExtWebActivity", "full web activity cannot resize");
      }

      public void onSetHandler(String paramAnonymousString, final ExtWebView.JSCallback paramAnonymousJSCallback)
      {
        if ("Broadcast".equals(paramAnonymousString))
          ExtWebActivity.access$802(ExtWebActivity.this, new BroadcastReceiver()
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
          ExtWebActivity.this.mContext.registerReceiver(ExtWebActivity.this.remoteDataReceiver, localIntentFilter);
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
    this.mWeb.clearFocus();
    this.mWeb.setFocusable(false);
    localLinearLayout.addView(this.mWeb, new LinearLayout.LayoutParams(-1, -1));
    this.info = ((MetadataInfo)getIntent().getSerializableExtra("MetaDataInfo"));
    if (("专题".equals(this.info.name)) || ("专区".equals(this.info.name)))
      xulHideTitle();
    if ("web".equals(this.info.action_type))
      xulHideTitle();
    xulUpdateTitle(this.info.name, GeneralUtils.conversionFirstLetter(this.info.packet_id));
    this.mWeb.getSettings().setCacheMode(0);
    this.url = WebUrlBuilder.getMainWebUrl(this.info.url, "");
    ReportInfo.getInstance().setWebUrl(this.url);
    this.isOpenPageSuccess = false;
    this.mWeb.loadUrl(this.url);
    this.mWeb.setWebViewClient(this.mClient);
    if (!isFinishing())
    {
      showDialog(5, null);
      this.hasDialog = true;
    }
    this.mWeb.getSettings().setJavaScriptEnabled(true);
    this.mWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    this.needDrawWebViewBg = true;
    super.xulOnRenderIsReady();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.ExtWebActivity
 * JD-Core Version:    0.6.2
 */