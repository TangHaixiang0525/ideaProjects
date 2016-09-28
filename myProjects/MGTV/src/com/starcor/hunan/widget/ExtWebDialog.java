package com.starcor.hunan.widget;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.SupperToast;
import com.starcor.hunan.App;
import com.starcor.hunan.DialogActivity;
import com.starcor.mgtv.boss.WebUrlBuilder;
import com.starcor.sccms.api.SccmsApiCheckValidByWebTokenTask.ISccmsApiCheckValidByWebTokenTaskListener;
import com.starcor.sccms.api.SccmsApiCheckWebTokenTask.ISccmsApiCheckWebTokenTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.net.URLDecoder;
import org.json.JSONObject;

@SuppressLint({"SetJavaScriptEnabled"})
public class ExtWebDialog extends WebDialogBase
{
  private static final int DEFAULT_HEIGHT = 0;
  private static final int DEFAULT_WIDTH = 0;
  private static final int DIALOG_TYPE_PROGRESS = 5;
  private static final int MSG_SHOW_WEB = 2;
  private static final int MSG_USER_AUTH = 1;
  private static final String TAG = "ExtWebDialog";
  private static Handler hdl;
  private String URL = "";
  private DialogInterface.OnKeyListener dl = new DialogInterface.OnKeyListener()
  {
    public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
    {
      return false;
    }
  };
  private int height = 200;
  public boolean isFoucsOnWEB = true;
  private boolean isShow = false;
  private long lastKeyDownTime = 0L;
  private WebViewClient mClient = new WebViewClient()
  {
    private Intent buildTHTFPayIntent(String paramAnonymousString, Intent paramAnonymousIntent)
    {
      if (paramAnonymousIntent == null)
      {
        Logger.i("ExtWebDialog", "buildTHTFPayIntent intent is null");
        return null;
      }
      Bundle localBundle = new Bundle();
      String str1 = URLDecoder.decode(paramAnonymousString.replace("mangofunc://start_pay?", "").trim());
      Logger.i("ExtWebDialog", "newpayurl = " + str1);
      String[] arrayOfString1 = str1.split("&");
      int i = 0;
      if (i < arrayOfString1.length)
      {
        String[] arrayOfString2 = arrayOfString1[i].split("=");
        StringBuilder localStringBuilder1 = new StringBuilder().append("keyValue[0] = ");
        String str2;
        label118: StringBuilder localStringBuilder2;
        if (arrayOfString2.length == 0)
        {
          str2 = "";
          localStringBuilder2 = localStringBuilder1.append(str2).append(";").append("keyValue[1] = ");
          if (arrayOfString2.length != 1)
            break label188;
        }
        label188: for (String str3 = ""; ; str3 = arrayOfString2[1])
        {
          Logger.i("ExtWebDialog", str3);
          if (!TextUtils.isEmpty(arrayOfString2[0]))
            break label197;
          i++;
          break;
          str2 = arrayOfString2[0];
          break label118;
        }
        label197: String str4 = arrayOfString2[0];
        if (arrayOfString2.length == 1);
        for (String str5 = ""; ; str5 = arrayOfString2[1])
        {
          localBundle.putString(str4, str5);
          break;
        }
      }
      Logger.i("ExtWebDialog", "最终传递参数：" + localBundle.toString());
      paramAnonymousIntent.putExtras(localBundle);
      return paramAnonymousIntent;
    }

    private boolean processMangoFuncCloseWeb(WebView paramAnonymousWebView, String paramAnonymousString)
    {
      if (ExtWebDialog.this.isShowing())
      {
        if (((DialogActivity)ExtWebDialog.this.mContext).hasDialog == true)
        {
          DialogActivity localDialogActivity = (DialogActivity)ExtWebDialog.this.mContext;
          ((DialogActivity)ExtWebDialog.this.mContext);
          localDialogActivity.dismissDialog(5);
        }
        ExtWebDialog.this.mWeb.clearView();
        ExtWebDialog.this._cleanUpWebView();
        ExtWebDialog.this.isFoucsOnWEB = false;
        ExtWebDialog.this.cancel();
        ExtWebDialog.access$402(ExtWebDialog.this, false);
      }
      return true;
    }

    private boolean processMangoFuncQuitWeb(WebView paramAnonymousWebView, String paramAnonymousString)
    {
      ExtWebDialog.this.dismiss();
      if (ExtWebDialog.this.mWeb != null)
        ExtWebDialog.this.mWeb.clearView();
      ExtWebDialog.this._cleanUpWebView();
      ExtWebDialog.this.mOnQuitWebListener.QuitWeb();
      return true;
    }

    private boolean procsssMangoFuncResponseLogin(WebView paramAnonymousWebView, String paramAnonymousString)
    {
      Logger.i("ExtWebDialog", "收到登陆信息的时间=" + System.currentTimeMillis());
      String str = subData(paramAnonymousString, "ticket");
      if ("0".equals(subData(paramAnonymousString, "ret")))
      {
        Logger.d("得到登录后的web_token=" + str);
        GlobalLogic.getInstance().userWebLogined(str);
        ExtWebDialog.this.checkWebToken(str);
      }
      while (true)
      {
        Logger.i("得到web_token====:" + str);
        return true;
        SupperToast.makeToast(ExtWebDialog.this.mContext, "登陆错误", 1);
      }
    }

    public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
    {
      super.onPageFinished(paramAnonymousWebView, paramAnonymousString);
      Logger.i("ExtWebDialog", "加载完成了" + paramAnonymousString);
      ExtWebDialog.this.isFoucsOnWEB = false;
      if ((!DeviceInfo.isKLWS()) && (!DeviceInfo.isHMD()) && (!DeviceInfo.isQUANZHI()) && (!DeviceInfo.isMAILE()) && (!DeviceInfo.isQUANZHI_A31S()) && (!DeviceInfo.isSWT_V15()) && (!DeviceInfo.isTCLLH_RT2982()) && (!DeviceInfo.isPHILIPMS628()) && (!DeviceInfo.isCHMS628()) && (ExtWebDialog.this.isShow))
        ExtWebDialog.this.mHandler.sendEmptyMessageDelayed(2, 300L);
      ExtWebDialog.access$402(ExtWebDialog.this, true);
      if (DeviceInfo.isBDYB())
        ExtWebDialog.this.mWeb.setFocusable(false);
      while (true)
      {
        ExtWebDialog.this.mWeb.requestFocus();
        return;
        ExtWebDialog.this.mWeb.setFocusable(true);
      }
    }

    public void onReceivedError(WebView paramAnonymousWebView, int paramAnonymousInt, String paramAnonymousString1, String paramAnonymousString2)
    {
      Logger.i("ExtWebDialog", "onReceivedError errorCode=" + paramAnonymousInt + "-->" + "description=" + paramAnonymousString1 + "-->" + "url=" + paramAnonymousString2);
      ExtWebDialog.this.isFoucsOnWEB = false;
    }

    public void onReceivedSslError(WebView paramAnonymousWebView, SslErrorHandler paramAnonymousSslErrorHandler, SslError paramAnonymousSslError)
    {
      Logger.e("ExtWebDialog", "onReceivedSslError " + paramAnonymousSslError.toString());
      paramAnonymousSslErrorHandler.cancel();
      paramAnonymousSslErrorHandler.proceed();
    }

    public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
    {
      Logger.i("ExtWebDialog", "shouldOverrideUrlLoading url:" + paramAnonymousString);
      if (!paramAnonymousString.startsWith("mangofunc://"))
      {
        ExtWebDialog.this.mWeb.loadUrl(paramAnonymousString);
        ExtWebDialog.this.isFoucsOnWEB = true;
        return true;
      }
      if (paramAnonymousString.startsWith("mangofunc://response_login?"))
        return procsssMangoFuncResponseLogin(paramAnonymousWebView, paramAnonymousString);
      if (paramAnonymousString.startsWith("mangofunc://reLogin"))
      {
        paramAnonymousWebView.loadUrl(WebUrlBuilder.getLoginUrl());
        ExtWebDialog.this.isFoucsOnWEB = true;
        return true;
      }
      if (paramAnonymousString.startsWith("mangofunc://quit_web"))
        return processMangoFuncQuitWeb(paramAnonymousWebView, paramAnonymousString);
      if (paramAnonymousString.startsWith("mangofunc://close_web"))
        return processMangoFuncCloseWeb(paramAnonymousWebView, paramAnonymousString);
      if (paramAnonymousString.startsWith("mangofunc://get_focuse"))
      {
        Logger.i("ExtWebDialog", "焦点在WEB上");
        ExtWebDialog.this.isFoucsOnWEB = true;
        return true;
      }
      if (paramAnonymousString.startsWith("mangofunc://return_focuse"))
      {
        Logger.i("ExtWebDialog", "把焦点在apk上");
        ExtWebDialog.this.isFoucsOnWEB = false;
        return true;
      }
      if (paramAnonymousString.startsWith("mangofunc://start_pay?"))
      {
        Logger.i("ExtWebDialog", "mangofunc://start_pay进入没？");
        ComponentName localComponentName = new ComponentName("cn.com.thtf.pay.sdk", "cn.com.thtf.pay.sdk.CheckstandActivity");
        Intent localIntent = new Intent();
        localIntent.setComponent(localComponentName);
        buildTHTFPayIntent(paramAnonymousString, localIntent);
        Logger.i("ExtWebDialog", "mangofunc://start_pay?  ");
        try
        {
          ((DialogActivity)ExtWebDialog.this.mContext).startActivityForResult(localIntent, 100);
          Logger.i("ExtWebDialog", "开始跳转");
          return true;
        }
        catch (Exception localException)
        {
          while (true)
          {
            Logger.i("ExtWebDialog", "start_pay 跳转异常");
            localException.printStackTrace();
          }
        }
      }
      return true;
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
  private Context mContext;
  private Handler mHandler = new Handler()
  {
    private void showWeb()
    {
      WindowManager.LayoutParams localLayoutParams = ExtWebDialog.this.getWindow().getAttributes();
      localLayoutParams.height = App.OperationHeight(ExtWebDialog.this.height);
      localLayoutParams.width = App.OperationHeight(ExtWebDialog.this.width);
      try
      {
        ExtWebDialog.this.getWindow().setAttributes(localLayoutParams);
        Logger.i("ExtWebDialog", "js设置了窗体的大小->" + ExtWebDialog.this.isShow + ",计算之后的大小：newHeight=" + localLayoutParams.height + ", newWidth=" + localLayoutParams.width);
        ExtWebDialog.this.show();
        ExtWebDialog.this.mHandler.sendEmptyMessageDelayed(3, 1000L);
        return;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }

    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == 1)
      {
        if (paramAnonymousMessage.obj != null)
        {
          UserInfo localUserInfo = (UserInfo)paramAnonymousMessage.obj;
          Logger.d("ExtWebDialog", "reason=" + localUserInfo.reason);
          if ("0".equals(localUserInfo.state));
          GlobalLogic.getInstance().userLogin(localUserInfo);
        }
        ExtWebDialog.this.dismiss();
        ExtWebDialog.this.mOnQuitWebListener.QuitWeb();
      }
      if (paramAnonymousMessage.what == 2)
        showWeb();
      if (paramAnonymousMessage.what == 3)
        ExtWebDialog.this._hideProgress();
    }
  };
  private WebDialogBase.onQuitWebListener mOnQuitWebListener;
  private ExtWebView mWeb;
  private BroadcastReceiver remoteDataReceiver;
  private int width = 200;

  public ExtWebDialog(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }

  public ExtWebDialog(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
    this.mContext = paramContext;
  }

  private void _cleanUpWebView()
  {
    try
    {
      if (this.mWeb != null)
      {
        this.mWeb.addJavascriptInterface(new Object(), "jsInterface");
        FrameLayout localFrameLayout = (FrameLayout)findViewById(2131165256);
        if (localFrameLayout != null)
          localFrameLayout.removeView(this.mWeb);
        this.mWeb.destroy();
        this.mWeb = null;
      }
    }
    catch (Exception localException)
    {
      try
      {
        while (true)
        {
          if (this.remoteDataReceiver != null)
            this.mContext.unregisterReceiver(this.remoteDataReceiver);
          return;
          localException = localException;
          localException.printStackTrace();
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        localIllegalArgumentException.printStackTrace();
      }
    }
  }

  private void _hideProgress()
  {
    if (((DialogActivity)this.mContext).hasDialog)
    {
      ((DialogActivity)this.mContext).dismissDialog(5);
      ((DialogActivity)this.mContext).hasDialog = false;
    }
  }

  private void changeLayoutParamsOfWebView(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    reSetWindow(paramInt1, paramInt2);
  }

  private void checkValidByWebToken(String paramString)
  {
    GlobalLogic.getInstance().userWebLogined(paramString);
    ServerApiManager.i().APICheckValidByWebToken(new SccmsApiCheckValidByWebTokenTask.ISccmsApiCheckValidByWebTokenTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Message localMessage = new Message();
        localMessage.what = 1;
        ExtWebDialog.this.mHandler.handleMessage(localMessage);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserInfo paramAnonymousUserInfo)
      {
        Message localMessage = new Message();
        localMessage.obj = paramAnonymousUserInfo;
        localMessage.what = 1;
        ExtWebDialog.this.mHandler.handleMessage(localMessage);
      }
    });
  }

  private void checkWebToken(String paramString)
  {
    GlobalLogic.getInstance().userWebLogined(paramString);
    ServerApiManager.i().APICheckWebToken(new SccmsApiCheckWebTokenTask.ISccmsApiCheckWebTokenTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Message localMessage = new Message();
        localMessage.what = 1;
        ExtWebDialog.this.mHandler.handleMessage(localMessage);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserInfo paramAnonymousUserInfo)
      {
        Message localMessage = new Message();
        localMessage.obj = paramAnonymousUserInfo;
        localMessage.what = 1;
        ExtWebDialog.this.mHandler.handleMessage(localMessage);
      }
    });
  }

  public static Handler getHandler()
  {
    return hdl;
  }

  private boolean handleKey(int paramInt, KeyEvent paramKeyEvent)
  {
    Logger.i("ExtWebDialog", "mWebView onKey action=" + paramKeyEvent.getAction() + "  keycode:" + paramInt + " isfocusOnweb:" + this.isFoucsOnWEB);
    if (DeviceInfo.isKONKA())
    {
      boolean bool = DeviceInfo.isKONKA();
      i = 0;
      if (bool)
      {
        int j = paramKeyEvent.getAction();
        i = 0;
        if (j == 0)
        {
          long l = System.currentTimeMillis();
          if (l - this.lastKeyDownTime <= 400L)
          {
            Logger.i("ExtWebDialog", "两次down事件事件不超过400毫秒，不处理");
            return true;
          }
          this.lastKeyDownTime = l;
          if (this.isFoucsOnWEB)
            this.mWeb.loadUrl("javascript:keymove(" + paramInt + ")");
          return this.isFoucsOnWEB;
        }
      }
    }
    else
    {
      if (paramKeyEvent.getAction() != 0)
        break label219;
    }
    label219: for (int i = 1; ; i = 0)
    {
      if ((i != 0) && (this.isFoucsOnWEB))
        this.mWeb.loadUrl("javascript:keymove(" + paramInt + ")");
      return this.isFoucsOnWEB;
    }
  }

  public static void setHandler(Handler paramHandler)
  {
    hdl = paramHandler;
  }

  public void destroy()
  {
    Logger.i("ExtWebDialog", "destroy webdialog");
    this.mWeb.addJavascriptInterface(new Object(), "jsInterface");
    this.mWeb.destroy();
    this.mWeb = null;
  }

  public void dismiss()
  {
    _cleanUpWebView();
    this.mHandler.removeCallbacksAndMessages(null);
    super.dismiss();
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    Logger.i("mwebview dispatchKeyEvent key=" + paramKeyEvent.getKeyCode());
    if ((DeviceInfo.isKONKA()) && (this.mWeb != null))
    {
      Logger.i("mwebview dispatchKeyEvent isKONKA key=" + paramKeyEvent.getKeyCode());
      return this.mWeb.dispatchKeyEvent(paramKeyEvent);
    }
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public void doCheck(String paramString1, String paramString2)
  {
    Logger.d("调用了token认证的方法");
    ServerApiManager.i().APICheckValidByWebToken(new SccmsApiCheckValidByWebTokenTask.ISccmsApiCheckValidByWebTokenTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Message localMessage = new Message();
        localMessage.what = 1;
        ExtWebDialog.this.mHandler.handleMessage(localMessage);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserInfo paramAnonymousUserInfo)
      {
        Message localMessage = new Message();
        localMessage.obj = paramAnonymousUserInfo;
        localMessage.what = 1;
        ExtWebDialog.this.mHandler.handleMessage(localMessage);
      }
    });
  }

  protected void finalize()
    throws Throwable
  {
    Logger.i("ExtWebDialog", "finalize");
    super.finalize();
  }

  public ExtWebView getWebView()
  {
    return this.mWeb;
  }

  public void loadUrl(String paramString)
  {
    if (this.mWeb == null)
      setupViews(null);
    Logger.i("ExtWebDialog", "loadUrl url:" + paramString);
    this.mWeb.clearView();
    this.mWeb.loadUrl(paramString);
    this.isFoucsOnWEB = true;
    this.mWeb.requestFocus();
    this.isShow = false;
    hide();
    ((DialogActivity)this.mContext).showDialog(5, null);
    ((DialogActivity)this.mContext).hasDialog = true;
  }

  public void onAirControlTextInput(String paramString)
  {
    if (this.isFoucsOnWEB)
    {
      String str = paramString.replaceAll("\"", "\\\"");
      this.mWeb.loadUrl("javascript:setCurrentInputValue(\"" + str + "\")");
      Logger.i("ExtWebDialog", "javascript:setCurrentInputValue(\"" + str + "\")");
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903060);
    setupViews(paramBundle);
  }

  public void reLoad()
  {
    loadUrl(this.URL);
  }

  public void reSetWindow(int paramInt1, int paramInt2)
  {
    Logger.i("ExtWebDialog", "reSetWindow w:" + paramInt1 + " ,h:" + paramInt2);
    if ((paramInt1 > 0) && (paramInt2 > 0))
    {
      this.width = paramInt1;
      this.height = paramInt2;
      this.isShow = true;
      this.mHandler.sendEmptyMessageDelayed(2, 300L);
    }
  }

  public void setOnQuitWebListener(WebDialogBase.onQuitWebListener paramonQuitWebListener)
  {
    this.mOnQuitWebListener = paramonQuitWebListener;
  }

  public void setURL(String paramString)
  {
    this.URL = paramString;
  }

  public void setupViews(Bundle paramBundle)
  {
    FrameLayout localFrameLayout = (FrameLayout)findViewById(2131165256);
    this.mWeb = new ExtWebView(this.mContext)
    {
      public boolean dispatchKeyEvent(KeyEvent paramAnonymousKeyEvent)
      {
        boolean bool = ExtWebDialog.this.handleKey(paramAnonymousKeyEvent.getKeyCode(), paramAnonymousKeyEvent);
        return (super.handleKey(paramAnonymousKeyEvent)) || (bool) || (super.orgDispatchKeyEvent(paramAnonymousKeyEvent));
      }

      public void onCloseBrowser(String paramAnonymousString)
      {
        ExtWebDialog.this.dismiss();
      }

      public void onMoveBrowser(double paramAnonymousDouble1, double paramAnonymousDouble2)
      {
        ExtWebDialog.this.changeLayoutParamsOfWebView(-1, -1, (int)paramAnonymousDouble1, (int)paramAnonymousDouble2, false);
      }

      public void onMoveBrowser(double paramAnonymousDouble1, double paramAnonymousDouble2, double paramAnonymousDouble3, double paramAnonymousDouble4)
      {
        ExtWebDialog.this.changeLayoutParamsOfWebView((int)paramAnonymousDouble3, (int)paramAnonymousDouble4, (int)paramAnonymousDouble1, (int)paramAnonymousDouble2, false);
      }

      public void onReceiveMessage(String paramAnonymousString, Object paramAnonymousObject)
      {
        if (paramAnonymousString.equals("onLogin"))
          if ((paramAnonymousObject instanceof JSONObject))
          {
            String str = ((JSONObject)paramAnonymousObject).optString("web_token");
            Logger.d("得到登录后的web_token=" + str);
            ExtWebDialog.this.checkValidByWebToken(str);
          }
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
                if (paramAnonymousString.equals("onPurchases"))
                {
                  ExtWebDialog.this.dismiss();
                  ExtWebDialog.this.mOnQuitWebListener.QuitWeb();
                  return;
                }
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
        ExtWebDialog.this.changeLayoutParamsOfWebView((int)paramAnonymousDouble1, (int)paramAnonymousDouble2, 0, 0, true);
      }

      public void onSetHandler(String paramAnonymousString, final ExtWebView.JSCallback paramAnonymousJSCallback)
      {
        if ("Broadcast".equals(paramAnonymousString))
        {
          IntentFilter localIntentFilter = new IntentFilter("com.starcor.remoteDataCallback");
          ExtWebDialog.access$1102(ExtWebDialog.this, new BroadcastReceiver()
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
          ExtWebDialog.this.mContext.registerReceiver(ExtWebDialog.this.remoteDataReceiver, localIntentFilter);
        }
      }

      public void onShowBrowser(double paramAnonymousDouble1, double paramAnonymousDouble2)
      {
        ExtWebDialog.this.reSetWindow((int)paramAnonymousDouble1, (int)paramAnonymousDouble2);
      }
    };
    this.mWeb.setAsChild();
    localFrameLayout.addView(this.mWeb);
    this.mWeb.addJavascriptInterface(this, "jsInterface");
    this.mWeb.setWebViewClient(this.mClient);
    this.mWeb.getSettings().setJavaScriptEnabled(true);
    this.mWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    this.mWeb.setScrollBarStyle(33554432);
    this.mWeb.setHorizontalScrollBarEnabled(false);
    this.mWeb.setVerticalScrollBarEnabled(false);
    this.mWeb.getSettings().setCacheMode(2);
    if (DeviceInfo.isBDYB())
    {
      setOnKeyListener(this.dl);
      this.mWeb.setFocusable(false);
      Log.i("ExtWebDialog", "zhixingmei");
    }
    while (true)
    {
      this.isFoucsOnWEB = true;
      this.mWeb.setOnFocusChangeListener(new View.OnFocusChangeListener()
      {
        public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
        {
          if (paramAnonymousBoolean)
          {
            Logger.i("ExtWebDialog", "webView 得到了焦点");
            ExtWebDialog.this.mWeb.loadUrl("javascript:getFocus()");
          }
        }
      });
      this.mWeb.setOnLongClickListener(new View.OnLongClickListener()
      {
        public boolean onLongClick(View paramAnonymousView)
        {
          return true;
        }
      });
      return;
      this.mWeb.requestFocus();
    }
  }

  public void show()
  {
    Logger.i("ExtWebDialog", "show  isShow:" + this.isShow + " hasDialog :" + ((DialogActivity)this.mContext).hasDialog);
    if (this.isShow)
      Logger.e("显示了");
    try
    {
      super.show();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.ExtWebDialog
 * JD-Core Version:    0.6.2
 */