package com.starcor.media.player;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
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
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.DialogActivity;
import com.starcor.hunan.msgsys.data.reservetopic.InteractiveMessage;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.hunan.widget.ExtWebView;
import com.starcor.hunan.widget.ExtWebView.JSCallback;
import com.starcor.sccms.api.SccmsApiCheckValidByWebTokenTask.ISccmsApiCheckValidByWebTokenTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.IXulExternalView;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulRenderContext.IXulRenderHandler;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class MplayerInteractiveWebView extends RelativeLayout
{
  private static final int DISPLAY_ERROR = 4;
  private static final int DISPLAY_LOADING = 1;
  private static final int DISPLAY_QUIT = 3;
  private static final int DISPLAY_WEB = 2;
  private static final int MESSAGE_DISMISS_DIALOG = 1000;
  private static final int MESSAGE_GET_VIDEO_ID = 1;
  private static final int MESSAGE_GET_VIDEO_INFO = 2;
  private static final String TAG = MplayerInteractiveWebView.class.getSimpleName();
  private String failUrl = "";
  private boolean hasError;
  private List<InteractiveMessage> interactiveMessages = new ArrayList();
  private boolean isBackDown = false;
  private boolean isFinished = false;
  private boolean isLoadInteractiveMsg = false;
  private int loadUrlCount = 0;
  private XulView loadingImageView;
  private XulView loadingView;
  private WebViewClient mClient = new WebViewClient()
  {
    private void getVideoInfoById(String paramAnonymousString)
    {
      String str1 = subData(paramAnonymousString, "asset_id");
      String str2 = subData(paramAnonymousString, "index");
      Logger.i(MplayerInteractiveWebView.TAG, "getVideoInfoById  asset_id:" + str1 + "  index_id:" + str2);
      GetFilmIdParams localGetFilmIdParams = new GetFilmIdParams(str1, Integer.parseInt(str2));
      GetFilmIdSAXParser localGetFilmIdSAXParser = new GetFilmIdSAXParser();
      ApiTask localApiTask = new ApiTask();
      localApiTask.setApi(localGetFilmIdParams);
      localApiTask.setParser(localGetFilmIdSAXParser);
      localApiTask.setHandler(MplayerInteractiveWebView.this.mHandler);
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
      Intent localIntent = new Intent(MplayerInteractiveWebView.this.getContext(), ActivityAdapter.getInstance().getVideoListActivity());
      localIntent.putExtra("Type", 0);
      localIntent.putExtra("MetaDataInfo", localMetadataInfo);
      localIntent.setFlags(8388608);
      localIntent.setClass(MplayerInteractiveWebView.this.getContext(), ActivityAdapter.getInstance().getVideoListActivity());
      localIntent.addFlags(8388608);
      MplayerInteractiveWebView.this.getContext().startActivity(localIntent);
      return true;
    }

    public void onLoadResource(WebView paramAnonymousWebView, String paramAnonymousString)
    {
      Logger.i(MplayerInteractiveWebView.TAG, "onLoadResource 加载了url" + paramAnonymousString);
    }

    public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
    {
      if ((MplayerInteractiveWebView.this.isLoadInteractiveMsg) || (1 == MplayerInteractiveWebView.this.loadUrlCount) || (MplayerInteractiveWebView.this.loadUrlCount == 0))
      {
        Logger.d(MplayerInteractiveWebView.TAG, "onPageFinished webView clear history");
        MplayerInteractiveWebView.this.webView.clearHistory();
      }
      Logger.i(MplayerInteractiveWebView.TAG, "Interactive onPageFinished 加载了url" + paramAnonymousString + ", count: " + MplayerInteractiveWebView.this.loadUrlCount);
      MplayerInteractiveWebView.access$1502(MplayerInteractiveWebView.this, true);
      if ((MplayerInteractiveWebView.this.hasError) && (MplayerInteractiveWebView.this.failUrl.equals(paramAnonymousString)))
      {
        MplayerInteractiveWebView.this.displayView(4);
        return;
      }
      MplayerInteractiveWebView.this.reportInteractiveMsgLoad(MplayerInteractiveWebView.access$1800(MplayerInteractiveWebView.this, paramAnonymousString), paramAnonymousString);
      MplayerInteractiveWebView.this.mHandler.sendEmptyMessageDelayed(1000, 1000L);
      super.onPageFinished(paramAnonymousWebView, paramAnonymousString);
    }

    public void onPageStarted(WebView paramAnonymousWebView, String paramAnonymousString, Bitmap paramAnonymousBitmap)
    {
      super.onPageStarted(paramAnonymousWebView, paramAnonymousString, paramAnonymousBitmap);
      Logger.i(MplayerInteractiveWebView.TAG, "Interactive onPageStarted : " + paramAnonymousString);
      MplayerInteractiveWebView.access$1502(MplayerInteractiveWebView.this, false);
      MplayerInteractiveWebView.access$1408(MplayerInteractiveWebView.this);
      MplayerInteractiveWebView.this.displayView(1);
      MplayerInteractiveWebView.access$2002(MplayerInteractiveWebView.this, System.currentTimeMillis());
    }

    public void onReceivedError(WebView paramAnonymousWebView, int paramAnonymousInt, String paramAnonymousString1, String paramAnonymousString2)
    {
      Logger.i(MplayerInteractiveWebView.TAG, "Interactive onReceivedError: " + paramAnonymousString2 + ", errorCode: " + paramAnonymousInt + ", desc: " + paramAnonymousString1);
      MplayerInteractiveWebView.access$1502(MplayerInteractiveWebView.this, true);
      MplayerInteractiveWebView.access$1602(MplayerInteractiveWebView.this, true);
      MplayerInteractiveWebView.access$1702(MplayerInteractiveWebView.this, paramAnonymousString2);
      MplayerInteractiveWebView.this.displayView(4);
      MplayerInteractiveWebView.this.reportInteractiveMsgLoad(false, paramAnonymousString2);
    }

    public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
    {
      Logger.i(MplayerInteractiveWebView.TAG, "shouldOverrideUrlLoading overload=" + paramAnonymousString);
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
  private Handler mHandler = new Handler()
  {
    private void getVideoInfo(Message paramAnonymousMessage)
    {
      VideoInfo localVideoInfo = (VideoInfo)paramAnonymousMessage.obj;
      Logger.i(MplayerInteractiveWebView.TAG + "getVideoInfo  info" + localVideoInfo.toString());
      GetFilmInfoParams localGetFilmInfoParams = new GetFilmInfoParams(localVideoInfo.videoId, localVideoInfo.videoType);
      GetFilmInfoSAXParser localGetFilmInfoSAXParser = new GetFilmInfoSAXParser();
      ApiTask localApiTask = new ApiTask();
      localApiTask.setApi(localGetFilmInfoParams);
      localApiTask.setParser(localGetFilmInfoSAXParser);
      localApiTask.setHandler(MplayerInteractiveWebView.this.mHandler);
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
      Logger.i(MplayerInteractiveWebView.TAG, "playVideo nns_index:" + localPlayerIntentParams.nns_index + "  playerInfo.nns_videoInfo" + localPlayerIntentParams.nns_videoInfo.toString());
      Intent localIntent = new Intent().setClass(MplayerInteractiveWebView.this.getContext(), ActivityAdapter.getInstance().getMPlayer());
      localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
      localIntent.addFlags(8388608);
      MplayerInteractiveWebView.this.getContext().startActivity(localIntent);
    }

    public void handleMessage(Message paramAnonymousMessage)
    {
      if ((paramAnonymousMessage.what == 1) && (paramAnonymousMessage.obj != null))
        getVideoInfo(paramAnonymousMessage);
      if ((paramAnonymousMessage.what == 2) && (paramAnonymousMessage.obj != null))
        playVideo(paramAnonymousMessage);
      if (paramAnonymousMessage.what == 1000)
      {
        MplayerInteractiveWebView.access$002(MplayerInteractiveWebView.this, false);
        MplayerInteractiveWebView.this.displayView(2);
        if (MplayerInteractiveWebView.this.webView != null)
          MplayerInteractiveWebView.this.webView.postInvalidate();
      }
    }
  };
  private boolean needDrawWebViewBg;
  private OnCloseWebListener onCloseWebListener;
  private ViewParent parentView;
  private XulView quitView;
  private XulView quitYesBtn;
  private Rect rect;
  private BroadcastReceiver remoteDataReceiver = null;
  private XulRenderContext renderContext;
  private XulView retryBtn;
  private XulView retryPage;
  private long startLoadTime = 0L;
  private String url = "";
  private ExtWebView webView;

  public MplayerInteractiveWebView(Context paramContext)
  {
    super(paramContext);
  }

  public MplayerInteractiveWebView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public MplayerInteractiveWebView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private boolean checkUrl(String paramString)
  {
    Pattern localPattern = Pattern.compile("^http(s)?://[^\\s]*", 2);
    return (!TextUtils.isEmpty(paramString)) && (localPattern.matcher(paramString).find());
  }

  private void checkValidByWebToken(String paramString)
  {
    GlobalLogic.getInstance().userWebLogined(paramString);
    ServerApiManager.i().APICheckValidByWebToken(new SccmsApiCheckValidByWebTokenTask.ISccmsApiCheckValidByWebTokenTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e(MplayerInteractiveWebView.TAG, "登陆失败");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserInfo paramAnonymousUserInfo)
      {
        if (paramAnonymousUserInfo != null)
        {
          Logger.d(MplayerInteractiveWebView.TAG, "reason=" + paramAnonymousUserInfo.reason);
          GlobalLogic.getInstance().userLogin(paramAnonymousUserInfo);
        }
      }
    });
  }

  private void closeWeb()
  {
    if (this.webView.canGoBack())
    {
      this.webView.goBack();
      this.hasError = false;
    }
    do
    {
      return;
      setVisibility(4);
    }
    while (this.onCloseWebListener == null);
    this.onCloseWebListener.onCloseWeb();
  }

  private void displayErrorPage(boolean paramBoolean)
  {
    XulView localXulView;
    if (this.retryPage != null)
    {
      localXulView = this.retryPage;
      if (!paramBoolean)
        break label68;
    }
    label68: for (String str = "block"; ; str = "none")
    {
      localXulView.setStyle("display", str);
      this.retryPage.resetRender();
      if ((paramBoolean) && (this.retryBtn != null) && (this.renderContext != null))
        this.renderContext.getLayout().requestFocus(this.retryBtn);
      return;
    }
  }

  private void displayLoadingView(boolean paramBoolean)
  {
    XulView localXulView;
    if ((this.loadingView != null) && (this.loadingImageView != null) && (this.renderContext != null))
    {
      if (!paramBoolean)
        break label81;
      if (this.loadingImageView.addClass("rotate_ani"))
        this.loadingImageView.resetRender();
      this.renderContext.doLayout();
      localXulView = this.loadingView;
      if (!paramBoolean)
        break label104;
    }
    label81: label104: for (String str = "block"; ; str = "none")
    {
      localXulView.setStyle("display", str);
      this.loadingView.resetRender();
      return;
      if (!this.loadingImageView.removeClass("rotate_ani"))
        break;
      this.loadingImageView.resetRender();
      break;
    }
  }

  private void displayQuitPanel(boolean paramBoolean)
  {
    XulView localXulView;
    if (this.quitView != null)
    {
      localXulView = this.quitView;
      if (!paramBoolean)
        break label68;
    }
    label68: for (String str = "block"; ; str = "none")
    {
      localXulView.setStyle("display", str);
      this.quitView.resetRender();
      if ((paramBoolean) && (this.renderContext != null) && (this.quitYesBtn != null))
        this.renderContext.getLayout().requestFocus(this.quitYesBtn);
      return;
    }
  }

  private void displayView(int paramInt)
  {
    switch (paramInt)
    {
    default:
      displayLoadingView(false);
      displayQuitPanel(false);
      displayWeb(false);
      displayErrorPage(false);
      return;
    case 1:
      displayLoadingView(true);
      displayQuitPanel(false);
      displayErrorPage(false);
      displayWeb(false);
      return;
    case 3:
      displayLoadingView(false);
      displayErrorPage(false);
      displayWeb(false);
      displayQuitPanel(true);
      return;
    case 2:
      displayLoadingView(false);
      displayQuitPanel(false);
      displayErrorPage(false);
      displayWeb(true);
      return;
    case 4:
    }
    displayLoadingView(false);
    displayQuitPanel(false);
    displayWeb(false);
    displayErrorPage(true);
  }

  private void displayWeb(boolean paramBoolean)
  {
    ExtWebView localExtWebView = this.webView;
    if (paramBoolean);
    for (int i = 0; ; i = 4)
    {
      localExtWebView.setVisibility(i);
      if (!paramBoolean)
        break;
      this.webView.requestFocus();
      return;
    }
    this.webView.clearFocus();
  }

  private String getKeyAction(int paramInt)
  {
    String str = "";
    if (paramInt == 0)
      str = "DOWN";
    while (paramInt != 1)
      return str;
    return "UP";
  }

  private String getMsgListV2()
  {
    JSONStringer localJSONStringer = new JSONStringer();
    try
    {
      localJSONStringer.object();
      localJSONStringer.key("list");
      localJSONStringer.array();
      Iterator localIterator = this.interactiveMessages.iterator();
      while (localIterator.hasNext())
      {
        InteractiveMessage localInteractiveMessage = (InteractiveMessage)localIterator.next();
        if (localInteractiveMessage != null)
        {
          localJSONStringer.object();
          localJSONStringer.key("image");
          localJSONStringer.value(localInteractiveMessage.image);
          localJSONStringer.key("title");
          localJSONStringer.value(localInteractiveMessage.title);
          localJSONStringer.key("url");
          localJSONStringer.value(localInteractiveMessage.webUrl);
          localJSONStringer.endObject();
        }
      }
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    while (true)
    {
      String str = localJSONStringer.toString();
      Logger.d(TAG, "getHistoryMsg:" + str);
      return str;
      localJSONStringer.endArray();
      localJSONStringer.endObject();
    }
  }

  private boolean handleKey(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 0)
    {
      Logger.d(TAG, "keymove-->" + paramInt);
      this.webView.loadUrl("javascript:keymove('" + paramInt + "')");
      return true;
    }
    return false;
  }

  private void initView()
  {
    this.renderContext = XulManager.createXulRender("IntractiveWebView", new XulRenderContext.IXulRenderHandler()
    {
      private Handler _mHandler = new Handler();

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
        if (paramAnonymousRect == null)
        {
          MplayerInteractiveWebView.this.invalidate();
          return;
        }
        MplayerInteractiveWebView.this.invalidate(paramAnonymousRect);
      }

      public void onDoAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
      {
        Logger.d(MplayerInteractiveWebView.TAG, "onDoAction action: " + paramAnonymousString1 + ", type: " + paramAnonymousString2);
        if ("retry".equals(paramAnonymousString2))
          MplayerInteractiveWebView.this.loadWebView(MplayerInteractiveWebView.this.url);
        do
        {
          return;
          if (("close".equals(paramAnonymousString2)) || ("quit_yes".equals(paramAnonymousString2)))
          {
            MplayerInteractiveWebView.this.closeWeb();
            return;
          }
        }
        while (!"quit_no".equals(paramAnonymousString2));
        MplayerInteractiveWebView.this.showQuitOrContent(false);
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
        this._mHandler.post(paramAnonymousRunnable);
      }

      public void uiRun(Runnable paramAnonymousRunnable, int paramAnonymousInt)
      {
        this._mHandler.postDelayed(paramAnonymousRunnable, paramAnonymousInt);
      }
    });
    if (this.renderContext != null)
    {
      this.retryPage = this.renderContext.findItemById("retryPage");
      this.retryBtn = this.renderContext.findItemById("retry");
      this.quitView = this.renderContext.findItemById("quit_area");
      this.quitYesBtn = this.renderContext.findItemById("yes_btn");
      this.loadingView = this.renderContext.findItemById("loadingView");
      this.loadingImageView = this.renderContext.findItemById("loading_img_view");
    }
    this.webView = new ExtWebView(getContext())
    {
      public boolean dispatchKeyEvent(KeyEvent paramAnonymousKeyEvent)
      {
        boolean bool = MplayerInteractiveWebView.this.handleKey(paramAnonymousKeyEvent.getKeyCode(), paramAnonymousKeyEvent);
        return (super.handleKey(paramAnonymousKeyEvent)) || (bool);
      }

      protected String getHistoryInteractiveMsgList()
      {
        return MplayerInteractiveWebView.this.getMsgListV2();
      }

      public void onCloseBrowser(String paramAnonymousString)
      {
        Logger.i(MplayerInteractiveWebView.TAG, "close web reason :" + paramAnonymousString);
        MplayerInteractiveWebView.this.closeWeb();
      }

      protected void onDraw(Canvas paramAnonymousCanvas)
      {
        super.onDraw(paramAnonymousCanvas);
        if (MplayerInteractiveWebView.this.needDrawWebViewBg);
      }

      public void onMoveBrowser(double paramAnonymousDouble1, double paramAnonymousDouble2)
      {
      }

      public void onMoveBrowser(double paramAnonymousDouble1, double paramAnonymousDouble2, double paramAnonymousDouble3, double paramAnonymousDouble4)
      {
      }

      public void onReceiveMessage(String paramAnonymousString, Object paramAnonymousObject)
      {
        Logger.d(MplayerInteractiveWebView.TAG, paramAnonymousString + ":" + paramAnonymousObject.toString());
        if (paramAnonymousString.equals("onLogin"))
          if ((paramAnonymousObject instanceof JSONObject))
          {
            String str = ((JSONObject)paramAnonymousObject).optString("web_token");
            Logger.d("得到登录后的web_token=" + str);
            MplayerInteractiveWebView.this.checkValidByWebToken(str);
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
      }

      public void onSetHandler(String paramAnonymousString, final ExtWebView.JSCallback paramAnonymousJSCallback)
      {
        if ("Broadcast".equals(paramAnonymousString))
          MplayerInteractiveWebView.access$1102(MplayerInteractiveWebView.this, new BroadcastReceiver()
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
          getContext().registerReceiver(MplayerInteractiveWebView.this.remoteDataReceiver, localIntentFilter);
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
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(-1, -1);
    localLayoutParams1.addRule(11);
    addView(this.webView, localLayoutParams1);
    this.webView.setWebViewClient(this.mClient);
    this.webView.getSettings().setJavaScriptEnabled(true);
    this.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    this.webView.setVisibility(4);
    XulPage localXulPage = this.renderContext.getPage();
    int i = localXulPage.getX();
    int j = localXulPage.getY();
    int k = localXulPage.getWidth();
    int m = localXulPage.getHeight();
    if (i >= 2147483547)
      i = 0;
    if (j >= 2147483547)
      j = 0;
    if (k >= 2147483547)
      k = localXulPage.getPageWidth() - i;
    if (m >= 2147483547)
      m = localXulPage.getPageHeight() - j;
    ((int)(i * localXulPage.getXScalar()));
    ((int)(j * localXulPage.getYScalar()));
    RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams((int)(k * localXulPage.getXScalar()), (int)(m * localXulPage.getYScalar()));
    localLayoutParams2.addRule(11);
    setLayoutParams(localLayoutParams2);
    this.parentView = getParent();
  }

  private void loadWebView(String paramString)
  {
    Logger.d(TAG, "loadWebView: " + paramString);
    this.url = paramString;
    this.hasError = false;
    this.isFinished = false;
    this.loadUrlCount = 0;
    this.webView.clearView();
    this.webView.loadUrl(paramString);
  }

  private void reportInteractiveMsgLoad(boolean paramBoolean, String paramString)
  {
    if (this.isLoadInteractiveMsg)
    {
      Context localContext = getContext();
      if ((localContext instanceof DialogActivity))
      {
        Logger.d(TAG, "report msg load: " + paramBoolean + ", url: " + paramString);
        ((DialogActivity)localContext).reportFuncLoad(4, "", paramString, System.currentTimeMillis() - this.startLoadTime, paramBoolean, 1, "");
      }
    }
  }

  private void showQuitOrContent(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      displayView(3);
      return;
    }
    if (this.isFinished)
    {
      if (this.hasError)
      {
        displayView(4);
        return;
      }
      displayView(2);
      return;
    }
    displayView(1);
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    Logger.i(TAG, "InteractiveWebView dispatchKeyEvent KeyCode--->" + paramKeyEvent.getKeyCode() + ", action--->" + getKeyAction(paramKeyEvent.getAction()));
    if (paramKeyEvent.getKeyCode() == 4)
    {
      if (paramKeyEvent.getAction() == 0)
        this.isBackDown = true;
      do
      {
        do
          return true;
        while ((paramKeyEvent.getAction() != 1) || (!this.isBackDown));
        Logger.d(TAG, "on back key up...");
        this.isBackDown = false;
      }
      while (this.quitView == null);
      if (this.quitView.isVisible())
      {
        Logger.d(TAG, "quitDialog already show. hide it when pressed back key.");
        showQuitOrContent(false);
        return true;
      }
      Logger.d(TAG, "show quitDialog.");
      if ((this.webView.getVisibility() == 0) && (this.webView.canGoBack()))
      {
        Logger.d(TAG, "WebView can go back. just let it go.");
        this.webView.goBack();
        return true;
      }
      showQuitOrContent(true);
      return true;
    }
    if ((this.webView.getVisibility() != 0) && (this.loadingView != null) && (!this.loadingView.isVisible()) && (this.renderContext != null) && (this.renderContext.onKeyEvent(paramKeyEvent)))
    {
      Logger.i(TAG, "InteractiveWebView dispatchKeyEvent 按键已处理");
      return true;
    }
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public void loadHistoryInteractiveMsges(String paramString, List<InteractiveMessage> paramList)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      this.interactiveMessages.clear();
      if ((paramList != null) && (paramList.size() > 0))
        this.interactiveMessages.addAll(paramList);
      this.isLoadInteractiveMsg = false;
      loadWebView(paramString);
    }
  }

  public void loadInteractiveMsg(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
    {
      Logger.e(TAG, "Interactive 传入的web url为空，加载空页面");
      paramString = "about:blank";
    }
    if (this.renderContext == null)
    {
      Logger.e(TAG, "Interactive renderContext为空");
      return;
    }
    this.isLoadInteractiveMsg = true;
    loadWebView(paramString);
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    initView();
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (this.renderContext != null)
      this.renderContext.destroy();
    this.renderContext = null;
    try
    {
      if (this.remoteDataReceiver != null)
        getContext().unregisterReceiver(this.remoteDataReceiver);
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
    }
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.renderContext != null)
    {
      if (this.rect != null)
        break label63;
      this.rect = new Rect(0, 0, getWidth(), getHeight());
    }
    while (true)
    {
      if (this.renderContext.beginDraw(paramCanvas, this.rect))
        this.renderContext.endDraw();
      super.onDraw(paramCanvas);
      return;
      label63: this.rect.set(0, 0, getWidth(), getHeight());
    }
  }

  public void setOnCloseWebListener(OnCloseWebListener paramOnCloseWebListener)
  {
    this.onCloseWebListener = paramOnCloseWebListener;
  }

  public static abstract interface OnCloseWebListener
  {
    public abstract void onCloseWeb();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerInteractiveWebView
 * JD-Core Version:    0.6.2
 */