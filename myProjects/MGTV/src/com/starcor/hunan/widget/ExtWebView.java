package com.starcor.hunan.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.starcor.apitask.IApiTaskListener;
import com.starcor.apitask.info.LiveShowSpecialInfo;
import com.starcor.apitask.info.LiveShowSpecialInfo.SpecialItem;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.MovieData;
import com.starcor.core.domain.PurchaseParam;
import com.starcor.core.domain.ReserveListItem;
import com.starcor.core.domain.SpecialTopicPkgCntLstInfo;
import com.starcor.core.domain.SpecialTopicPkgCntLstStruct;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.DialogActivity;
import com.starcor.hunan.domain.MergeLocalReserveAndUpload;
import com.starcor.hunan.domain.Reservation;
import com.starcor.hunan.service.CollectAndPlayListLogic;
import com.starcor.hunan.service.ReservationService;
import com.starcor.hunan.service.SystemTimeManager;
import com.starcor.hunan.xiaomi.XiaoMiOAuthManager;
import com.starcor.sccms.api.SccmsApiGetSpecialTopicPkgContentLstTask.ISccmsApiGetSearchSpecialTopicPkgLstTaskListener;
import com.starcor.sccms.api.SccmsApiLiveShowReserveTask.ISccmsApiLiveShowReserveTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.ui.UITools;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public abstract class ExtWebView extends WebView
{
  public static final String ACTION_FROM_MGTV = "flag_action_from_mgtv";
  public static final String GET_USER_INFO_SUCCEED = "get_user_info_success";
  public static final String LOGIN_SUCCEED = "login_in_succeed";
  private static final String TAG = ExtWebView.class.getSimpleName();
  static final KeyCharacterMap charMap = KeyCharacterMap.load(0);
  private JSCallback _broadcastCallback;
  private jsExtObject _extObj;
  private Class<?> _extObjClass;
  private boolean _hasFocus = false;
  private JSCallback _keyCallback;
  private KeyEvent _lastEatEvent;
  private JSCallback _msgCallback;
  private Rect _rect = new Rect();
  jsExposeObjectWrapper _wrapper = new jsExposeObjectWrapper(null);
  private ExtWebView childWeb;
  private ExtWebDialog childWebViewDialog;
  private CollectAndPlayListLogic collectLogic = new CollectAndPlayListLogic();
  private final AtomicInteger evalJsIndex = new AtomicInteger(0);
  private String globalLiveShowId = "";
  private boolean isChild;
  private String jsExposeName = "starcorExt";
  private final Map<Integer, String> jsReturnValues = new HashMap();
  private Context mContext;
  private ExtWebView parentWeb;
  BroadcastReceiver receiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      Logger.d(ExtWebView.TAG, "intent action=" + paramAnonymousIntent.getAction());
      if ("com.starcor.xul.ACTION_BROADCAST".equals(paramAnonymousIntent.getAction()))
        if ("closePageOnPurchaseSuccess".equals(paramAnonymousIntent.getExtras().getString("name")))
        {
          Object[] arrayOfObject3 = new Object[3];
          arrayOfObject3[0] = ExtWebView.this.jsExposeName;
          arrayOfObject3[1] = "on_purchase_vip_success";
          arrayOfObject3[2] = "";
          String str5 = String.format("javascript:%s.%s(%s)", arrayOfObject3);
          ExtWebView.this.loadUrl(str5);
          Logger.d(ExtWebView.TAG, "invoke js callback: " + str5);
        }
      do
      {
        return;
        if ("login_in_succeed".equals(paramAnonymousIntent.getAction()))
        {
          String str2 = paramAnonymousIntent.getStringExtra("loginMode");
          String str3 = "'" + str2 + "'";
          Object[] arrayOfObject2 = new Object[3];
          arrayOfObject2[0] = ExtWebView.this.jsExposeName;
          arrayOfObject2[1] = "on_login_success";
          arrayOfObject2[2] = str3;
          String str4 = String.format("javascript:%s.%s(%s)", arrayOfObject2);
          ExtWebView.this.loadUrl(str4);
          Logger.d(ExtWebView.TAG, "invoke js callback: " + str4);
          return;
        }
      }
      while (!"get_user_info_success".equals(paramAnonymousIntent.getAction()));
      Object[] arrayOfObject1 = new Object[3];
      arrayOfObject1[0] = ExtWebView.this.jsExposeName;
      arrayOfObject1[1] = "on_get_user_info_success";
      arrayOfObject1[2] = "";
      String str1 = String.format("javascript:%s.%s(%s)", arrayOfObject1);
      ExtWebView.this.loadUrl(str1);
      Logger.d(ExtWebView.TAG, "invoke js callback: " + str1);
    }
  };
  private VideoInfo videoinfo = new VideoInfo();
  private Handler viewHandler = new Handler();

  public ExtWebView(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    init();
  }

  public ExtWebView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    init();
  }

  public ExtWebView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mContext = paramContext;
    init();
  }

  private void _internalOnReceiveMessage(String paramString, Object paramObject)
  {
    onReceiveMessage(paramString, paramObject);
  }

  private String _waitForJsReturnValue(int paramInt1, int paramInt2)
  {
    try
    {
      synchronized (this.jsReturnValues)
      {
        String str = (String)this.jsReturnValues.remove(Integer.valueOf(paramInt1));
        return str;
      }
    }
    catch (Exception localException)
    {
      Log.e(TAG, "Giving up; waited " + paramInt2 / 1000 + "sec for return value " + paramInt1);
      return "";
    }
    finally
    {
    }
  }

  private void bindExtObject(jsExtObject paramjsExtObject)
  {
    this._extObj = paramjsExtObject;
    if (this._extObj != null)
    {
      this._extObjClass = this._extObj.getClass();
      return;
    }
    this._extObjClass = null;
  }

  private int convertString2Flag(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if ("NEW_TASK".equals(paramString))
        return 268435456;
      if ("SINGLE_TOP".equals(paramString))
        return 536870912;
    }
    return -1;
  }

  private void init()
  {
    setScaleX(1.0F);
    setScaleY(1.0F);
    getSettings().setLoadWithOverviewMode(false);
    setInitialScale(App.OperationHeight(100));
    getSettings().setJavaScriptEnabled(true);
    setWebChromeClient(new WebChromeClient());
    addJavascriptInterface(this._wrapper, this.jsExposeName);
    this._wrapper.bind(new jsExposeObjectImpl(null));
    bindExtObject(createExtObject());
    onInit();
  }

  protected jsExtObject createExtObject()
  {
    return new jsExtObject();
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    return (handleKey(paramKeyEvent)) || (super.dispatchKeyEvent(paramKeyEvent));
  }

  public ExtWebView getChildWeb()
  {
    return this.childWeb;
  }

  protected String getHistoryInteractiveMsgList()
  {
    Logger.d(TAG, "getHistoryInteractiveMsgList...");
    return "";
  }

  public ExtWebView getParentWeb()
  {
    return this.parentWeb;
  }

  public boolean handleKey(KeyEvent paramKeyEvent)
  {
    if ((this._keyCallback != null) && (this._hasFocus))
    {
      if (paramKeyEvent.getAction() == 1)
      {
        if ((this._lastEatEvent != null) && (this._lastEatEvent.getKeyCode() == paramKeyEvent.getKeyCode()))
        {
          this._lastEatEvent = null;
          return true;
        }
        this._lastEatEvent = null;
        return false;
      }
      String str1;
      JSONArray localJSONArray;
      String str2;
      switch (paramKeyEvent.getAction())
      {
      default:
        return false;
      case 0:
        str1 = "keyDown";
        localJSONArray = new JSONArray();
        if ((0x2 & paramKeyEvent.getMetaState()) != 0)
          localJSONArray.put("alt");
        if ((0x1 & paramKeyEvent.getMetaState()) != 0)
          localJSONArray.put("shift");
        str2 = "";
        switch (paramKeyEvent.getKeyCode())
        {
        default:
          int i = charMap.get(paramKeyEvent.getKeyCode(), paramKeyEvent.getMetaState());
          if (i != 0)
            str2 = Character.valueOf((char)i).toString();
          break;
        case 21:
        case 19:
        case 20:
        case 22:
        case 23:
        case 66:
        case 4:
        case 67:
        case 90:
        case 89:
        case 87:
        case 88:
        case 85:
        case 86:
        case 82:
        }
        break;
      case 1:
      }
      while (true)
      {
        JSCallback localJSCallback = this._keyCallback;
        Object[] arrayOfObject = new Object[4];
        arrayOfObject[0] = str1;
        arrayOfObject[1] = Integer.valueOf(paramKeyEvent.getKeyCode());
        arrayOfObject[2] = str2;
        arrayOfObject[3] = localJSONArray.toString();
        if (!"true".equals(localJSCallback.run(arrayOfObject)))
          break label505;
        this._lastEatEvent = paramKeyEvent;
        return true;
        str1 = "keyUp";
        break;
        str2 = "LEFT";
        continue;
        str2 = "UP";
        continue;
        str2 = "DOWN";
        continue;
        str2 = "RIGHT";
        continue;
        str2 = "ENTER";
        continue;
        str2 = "BACK";
        continue;
        if ((0x1 & paramKeyEvent.getMetaState()) != 0)
        {
          str2 = "DELETE";
        }
        else
        {
          str2 = "BACKSPACE";
          continue;
          str2 = "MEDIA_FAST_FORWARD";
          continue;
          str2 = "MEDIA_REWIND";
          continue;
          str2 = "MEDIA_NEXT";
          continue;
          str2 = "MEDIA_PREVIOUS";
          continue;
          str2 = "MEDIA_PLAY_PAUSE";
          continue;
          str2 = "MEDIA_STOP";
          continue;
          str2 = "MENU";
        }
      }
      label505: this._lastEatEvent = null;
      return true;
    }
    return false;
  }

  public boolean isChild()
  {
    return this.isChild;
  }

  protected void onAttachedToWindow()
  {
    Logger.d(TAG, "onAttachedToWindow");
    if ((this._wrapper == null) || (this._wrapper.isNull()))
      init();
    super.onAttachedToWindow();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.starcor.xul.ACTION_BROADCAST");
    localIntentFilter.addAction("login_in_succeed");
    localIntentFilter.addAction("get_user_info_success");
    getContext().registerReceiver(this.receiver, localIntentFilter);
  }

  public abstract void onCloseBrowser(String paramString);

  protected void onDetachedFromWindow()
  {
    Logger.d(TAG, "onDetachedFromWindow");
    try
    {
      removeJavascriptInterface(this.jsExposeName);
      label17: this.jsReturnValues.clear();
      this._wrapper.bind(null);
      super.onDetachedFromWindow();
      getContext().unregisterReceiver(this.receiver);
      return;
    }
    catch (Exception localException)
    {
      break label17;
    }
  }

  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
    this._hasFocus = paramBoolean;
  }

  public void onInit()
  {
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this._rect.left = paramInt1;
    this._rect.right = paramInt3;
    this._rect.top = paramInt2;
    this._rect.bottom = paramInt4;
  }

  public abstract void onMoveBrowser(double paramDouble1, double paramDouble2);

  public abstract void onMoveBrowser(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public abstract void onReceiveMessage(String paramString, Object paramObject);

  public abstract void onResizeBrowser(double paramDouble1, double paramDouble2);

  public abstract void onSetHandler(String paramString, JSCallback paramJSCallback);

  public abstract void onShowBrowser(double paramDouble1, double paramDouble2);

  public boolean orgDispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public void sendMessage(String paramString1, String paramString2)
  {
    if (this._msgCallback != null)
      this._msgCallback.run(new Object[] { paramString1, paramString2 });
  }

  public void sendMessage(String paramString, JSONObject paramJSONObject)
  {
    if (this._msgCallback != null)
      this._msgCallback.run(new Object[] { paramString, paramJSONObject });
  }

  public void setAsChild()
  {
    this.isChild = true;
  }

  public void setChildWeb(ExtWebView paramExtWebView)
  {
    this.childWeb = paramExtWebView;
  }

  public void setParentWeb(ExtWebView paramExtWebView)
  {
    this.parentWeb = paramExtWebView;
  }

  public class JSCallback
  {
    String methodName;

    public JSCallback()
    {
    }

    private String buildJson(Pair<String, Object>[] paramArrayOfPair)
    {
      JSONStringer localJSONStringer = new JSONStringer();
      try
      {
        localJSONStringer.array();
        int i = paramArrayOfPair.length;
        for (int j = 0; j < i; j++)
        {
          Pair<String, Object> localPair = paramArrayOfPair[j];
          localJSONStringer.object().key("type").value(localPair.first).key("value").value(localPair.second).endObject();
        }
        localJSONStringer.endArray();
        label78: return localJSONStringer.toString();
      }
      catch (JSONException localJSONException)
      {
        break label78;
      }
    }

    private String invokeJSCallback(String paramString1, String paramString2)
    {
      int i = ExtWebView.this.evalJsIndex.incrementAndGet();
      synchronized (ExtWebView.this.jsReturnValues)
      {
        ExtWebView.this.jsReturnValues.put(Integer.valueOf(i), null);
        Object[] arrayOfObject = new Object[4];
        arrayOfObject[0] = ExtWebView.this.jsExposeName;
        arrayOfObject[1] = paramString1;
        arrayOfObject[2] = paramString2;
        arrayOfObject[3] = Integer.valueOf(i);
        String str = String.format("javascript:%s._invokeCallback(\"%s\", %s, %d)", arrayOfObject);
        ExtWebView.this.loadUrl(str);
        return ExtWebView.this._waitForJsReturnValue(i, 1000);
      }
    }

    public String run(Object[] paramArrayOfObject)
    {
      if (Looper.getMainLooper().getThread().getId() != Thread.currentThread().getId())
        throw new IllegalThreadStateException("JSCallback must invoked in UI Thread!!!");
      ArrayList localArrayList = new ArrayList();
      int i = paramArrayOfObject.length;
      int j = 0;
      if (j < i)
      {
        Object localObject = paramArrayOfObject[j];
        if (((localObject instanceof Integer)) || ((localObject instanceof Double)) || ((localObject instanceof Long)) || ((localObject instanceof Float)) || ((localObject instanceof Short)) || ((localObject instanceof Byte)))
          localArrayList.add(Pair.create("number", localObject));
        while (true)
        {
          j++;
          break;
          if ((localObject instanceof Boolean))
            localArrayList.add(Pair.create("boolean", localObject));
          else if (((localObject instanceof CharSequence)) || ((localObject instanceof Character)))
            localArrayList.add(Pair.create("string", localObject));
          else if ((localObject instanceof JSONObject))
            localArrayList.add(Pair.create("object", localObject));
        }
      }
      String str = buildJson((Pair[])localArrayList.toArray(new Pair[localArrayList.size()]));
      Log.d(ExtWebView.TAG, "invokeCallback argsJson= " + str);
      return invokeJSCallback(this.methodName, str);
    }
  }

  private class jsExposeObjectImpl
  {
    private jsExposeObjectImpl()
    {
    }

    public Object _execAndroidFunc(String paramString1, String paramString2)
    {
      if (ExtWebView.this._extObjClass == null)
        return null;
      while (true)
      {
        ArrayList localArrayList1;
        ArrayList localArrayList2;
        int i;
        JSONObject localJSONObject;
        String str1;
        String str2;
        try
        {
          localArrayList1 = new ArrayList();
          localArrayList2 = new ArrayList();
          JSONArray localJSONArray = new JSONArray(paramString2);
          i = 0;
          if (i >= localJSONArray.length())
            break label295;
          localJSONObject = localJSONArray.getJSONObject(i);
          str1 = localJSONObject.getString("type");
          str2 = localJSONObject.getString("value");
          if ("boolean".equalsIgnoreCase(str1))
          {
            localArrayList1.add(Boolean.TYPE);
            localArrayList2.add(Boolean.valueOf(Boolean.parseBoolean(str2)));
          }
          else if ("number".equalsIgnoreCase(str1))
          {
            localArrayList1.add(Double.TYPE);
            localArrayList2.add(Double.valueOf(Double.parseDouble(str2)));
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          return null;
        }
        if ("string".equalsIgnoreCase(str1))
        {
          localArrayList1.add(String.class);
          localArrayList2.add(str2);
        }
        else if ("callback".equalsIgnoreCase(str1))
        {
          ExtWebView.JSCallback localJSCallback = new ExtWebView.JSCallback(ExtWebView.this);
          localJSCallback.methodName = str2;
          localArrayList1.add(ExtWebView.JSCallback.class);
          localArrayList2.add(localJSCallback);
        }
        else if ("object".equalsIgnoreCase(str1))
        {
          localArrayList1.add(JSONObject.class);
          localArrayList2.add(localJSONObject.getJSONObject("value"));
        }
        else
        {
          throw new IllegalArgumentException("error type : " + str1);
          label295: Log.d(ExtWebView.TAG, String.format("name=%s, type=%s, val=%s", new Object[] { paramString1, localArrayList1, localArrayList2 }));
          Class[] arrayOfClass = (Class[])localArrayList1.toArray(new Class[localArrayList1.size()]);
          Object localObject = ExtWebView.this._extObjClass.getMethod(paramString1, arrayOfClass).invoke(ExtWebView.this._extObj, localArrayList2.toArray());
          return localObject;
        }
        i++;
      }
    }

    public void _setCallbackResult(int paramInt, String paramString)
    {
      synchronized (ExtWebView.this)
      {
        synchronized (ExtWebView.this.jsReturnValues)
        {
          if (ExtWebView.this.jsReturnValues.containsKey(Integer.valueOf(paramInt)))
            ExtWebView.this.jsReturnValues.put(Integer.valueOf(paramInt), paramString);
          ExtWebView.this.notifyAll();
          return;
        }
      }
    }
  }

  private static class jsExposeObjectWrapper
  {
    private ExtWebView.jsExposeObjectImpl _impl;

    public Object _execAndroidFunc(String paramString1, String paramString2)
    {
      if (this._impl == null)
        return null;
      return this._impl._execAndroidFunc(paramString1, paramString2);
    }

    public void _setCallbackResult(int paramInt, String paramString)
    {
      if (this._impl == null)
        return;
      this._impl._setCallbackResult(paramInt, paramString);
    }

    public void bind(ExtWebView.jsExposeObjectImpl paramjsExposeObjectImpl)
    {
      this._impl = paramjsExposeObjectImpl;
    }

    public boolean isNull()
    {
      return this._impl == null;
    }
  }

  protected class jsExtObject
  {
    public static final String TAG = "JSExtObject";

    protected jsExtObject()
    {
    }

    private int getJsonInt(JSONObject paramJSONObject, String paramString)
    {
      try
      {
        String str2 = paramJSONObject.getString(paramString);
        str1 = str2;
      }
      catch (JSONException localJSONException)
      {
        try
        {
          while (true)
          {
            int j = Integer.parseInt(str1);
            i = j;
            Logger.d("JSExtObject", "getJsonInt, key=" + paramString + ", value=" + str1);
            return i;
            localJSONException = localJSONException;
            localJSONException.printStackTrace();
            String str1 = "-1";
          }
        }
        catch (Exception localException)
        {
          while (true)
          {
            localException.printStackTrace();
            int i = -1;
          }
        }
      }
    }

    private String getJsonStr(JSONObject paramJSONObject, String paramString)
    {
      try
      {
        String str2 = paramJSONObject.getString(paramString);
        str1 = str2;
        Logger.d("JSExtObject", "getJsonArgs, key=" + paramString + ", value=" + str1);
        return str1;
      }
      catch (JSONException localJSONException)
      {
        while (true)
        {
          localJSONException.printStackTrace();
          String str1 = "";
        }
      }
    }

    public void closeBrowser(final String paramString)
    {
      ExtWebView.this.viewHandler.post(new Runnable()
      {
        public void run()
        {
          ExtWebView.this.onCloseBrowser(paramString);
        }
      });
    }

    public String getBrowserPosition()
    {
      Rect localRect = new Rect();
      ExtWebView.this.getGlobalVisibleRect(localRect);
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = Integer.valueOf(localRect.left);
      arrayOfObject[1] = Integer.valueOf(localRect.top);
      arrayOfObject[2] = Integer.valueOf(localRect.width());
      arrayOfObject[3] = Integer.valueOf(localRect.height());
      return String.format("%d,%d,%d,%d", arrayOfObject);
    }

    public String getHistoryInteractiveMsgList()
    {
      return ExtWebView.this.getHistoryInteractiveMsgList();
    }

    public void getLiveShowInfo(final String paramString, final ExtWebView.JSCallback paramJSCallback)
    {
      ExtWebView.access$1802(ExtWebView.this, paramString);
      final JSONObject localJSONObject = new JSONObject();
      ServerApiManager.i().APIGetSpecialInfoByIds(new IApiTaskListener()
      {
        private void onApiError(JSONObject paramAnonymousJSONObject, ServerApiCommonError paramAnonymousServerApiCommonError, ExtWebView.JSCallback paramAnonymousJSCallback)
        {
          try
          {
            paramAnonymousJSONObject.put("api_status", 0);
            paramAnonymousJSONObject.put("error_reason", paramAnonymousServerApiCommonError.getRunReason());
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = paramAnonymousJSONObject.toString();
            paramAnonymousJSCallback.run(arrayOfObject);
            return;
          }
          catch (JSONException localJSONException)
          {
            while (true)
              localJSONException.printStackTrace();
          }
        }

        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          Logger.e("JSExtObject", "APIGetSpecialInfoByIds failed!! " + paramAnonymousServerApiCommonError.toString());
          onApiError(localJSONObject, paramAnonymousServerApiCommonError, paramJSCallback);
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, LiveShowSpecialInfo paramAnonymousLiveShowSpecialInfo)
        {
          Logger.d("JSExtObject", "APIGetSpecialInfoByIds success!! ");
          LiveShowSpecialInfo.SpecialItem localSpecialItem = (LiveShowSpecialInfo.SpecialItem)paramAnonymousLiveShowSpecialInfo.specialItems.get(0);
          String str1 = localSpecialItem.special_id;
          long l1 = ReservationService.time2Reservation(localSpecialItem.beginTime.replace("-", "").replace(":", "").replace(" ", ""));
          boolean bool;
          if (ReservationService.getinstance().findReservation(l1, paramString) != null)
            bool = true;
          while (true)
          {
            long l2 = SystemTimeManager.getCurrentServerTime();
            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setTimeInMillis(l2);
            String str2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localCalendar.getTime());
            try
            {
              localJSONObject.put("live_show_name", localSpecialItem.name);
              localJSONObject.put("live_show_reserved", bool);
              localJSONObject.put("begin_time", localSpecialItem.beginTime);
              localJSONObject.put("end_time", localSpecialItem.endTime);
              localJSONObject.put("server_time", str2);
              ServerApiManager.i().APIGetSpecialTopicPkgContent(str1, new SccmsApiGetSpecialTopicPkgContentLstTask.ISccmsApiGetSearchSpecialTopicPkgLstTaskListener()
              {
                public void onError(ServerApiTaskInfo paramAnonymous2ServerApiTaskInfo, ServerApiCommonError paramAnonymous2ServerApiCommonError)
                {
                  Logger.e("JSExtObject", "APIGetSpecialTopicPkgContent failed!! " + paramAnonymous2ServerApiCommonError.toString());
                  ExtWebView.jsExtObject.11.this.onApiError(ExtWebView.jsExtObject.11.this.val$liveShowArgs, paramAnonymous2ServerApiCommonError, ExtWebView.jsExtObject.11.this.val$jsCallback);
                }

                public void onSuccess(ServerApiTaskInfo paramAnonymous2ServerApiTaskInfo, SpecialTopicPkgCntLstInfo paramAnonymous2SpecialTopicPkgCntLstInfo)
                {
                  Logger.d("JSExtObject", "APIGetSpecialTopicPkgContent success!! ");
                  if ((paramAnonymous2SpecialTopicPkgCntLstInfo == null) || (paramAnonymous2SpecialTopicPkgCntLstInfo.sTopicPkgCntLstStructs == null))
                    return;
                  SpecialTopicPkgCntLstStruct localSpecialTopicPkgCntLstStruct = (SpecialTopicPkgCntLstStruct)paramAnonymous2SpecialTopicPkgCntLstInfo.sTopicPkgCntLstStructs.get(0);
                  String str1 = localSpecialTopicPkgCntLstStruct.video_id;
                  String str2 = localSpecialTopicPkgCntLstStruct.video_type;
                  String str3 = String.valueOf(localSpecialTopicPkgCntLstStruct.ui_style);
                  String str4 = localSpecialTopicPkgCntLstStruct.packet_id;
                  String str5 = localSpecialTopicPkgCntLstStruct.packet_category_id;
                  ExtWebView.this.videoinfo.videoId = str1;
                  ExtWebView.this.videoinfo.packageId = str4;
                  ExtWebView.this.videoinfo.categoryId = str5;
                  ExtWebView.this.videoinfo.uiStyle = Integer.valueOf(str3).intValue();
                  ExtWebView.this.videoinfo.videoType = Integer.valueOf(str2).intValue();
                  try
                  {
                    ExtWebView.jsExtObject.11.this.val$liveShowArgs.put("video_id", str1);
                    ExtWebView.jsExtObject.11.this.val$liveShowArgs.put("video_type", str2);
                    ExtWebView.jsExtObject.11.this.val$liveShowArgs.put("ui_style", str3);
                    ExtWebView.jsExtObject.11.this.val$liveShowArgs.put("packet_id", str4);
                    ExtWebView.jsExtObject.11.this.val$liveShowArgs.put("category_id", str5);
                    ExtWebView.jsExtObject.11.this.val$liveShowArgs.put("api_status", 1);
                    ExtWebView.JSCallback localJSCallback = ExtWebView.jsExtObject.11.this.val$jsCallback;
                    Object[] arrayOfObject = new Object[1];
                    arrayOfObject[0] = ExtWebView.jsExtObject.11.this.val$liveShowArgs.toString();
                    localJSCallback.run(arrayOfObject);
                    return;
                  }
                  catch (JSONException localJSONException)
                  {
                    while (true)
                      localJSONException.printStackTrace();
                  }
                }
              });
              return;
              bool = false;
            }
            catch (JSONException localJSONException)
            {
              while (true)
                localJSONException.printStackTrace();
            }
          }
        }
      }
      , new String[] { paramString });
    }

    public String getScreenSize()
    {
      return App.SCREEN_WIDTH + "," + App.SCREEN_HEIGHT;
    }

    public String getVersion()
    {
      return DeviceInfo.getMGTVVersion();
    }

    public boolean hasFocus()
    {
      return ExtWebView.this._hasFocus;
    }

    public void log(String paramString1, String paramString2)
    {
      Log.i(paramString1, paramString2);
    }

    public void mangoLiveShowReserveReport(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
    {
      ExtWebView.this.collectLogic.addLiveShowReserve("", paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, new SccmsApiLiveShowReserveTask.ISccmsApiLiveShowReserveTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ReserveListItem paramAnonymousReserveListItem)
        {
        }
      });
    }

    public void moveBrowser(final double paramDouble1, double paramDouble2)
    {
      ExtWebView.this.viewHandler.post(new Runnable()
      {
        public void run()
        {
          ExtWebView.this.onMoveBrowser(paramDouble1, this.val$y);
        }
      });
    }

    public void moveBrowserEx(final double paramDouble1, double paramDouble2, final double paramDouble3, double paramDouble4)
    {
      ExtWebView.this.viewHandler.post(new Runnable()
      {
        public void run()
        {
          ExtWebView.this.onMoveBrowser(paramDouble1, paramDouble3, this.val$width, this.val$height);
        }
      });
    }

    public void openBrowser(String paramString)
    {
      openBrowser(paramString, 500.0D, 400.0D);
    }

    public void openBrowser(final String paramString, final double paramDouble1, double paramDouble2)
    {
      if (ExtWebView.this.isChild)
      {
        Log.v("JSExtObject", "child view cannot open another child view");
        return;
      }
      ExtWebView.this.viewHandler.post(new Runnable()
      {
        public void run()
        {
          DialogActivity localDialogActivity = (DialogActivity)ExtWebView.this.mContext;
          ExtWebView.access$1602(ExtWebView.this, (ExtWebDialog)localDialogActivity.showWebDialog(paramString, new WebDialogBase.onQuitWebListener()
          {
            public void QuitWeb()
            {
            }
          }));
          ExtWebView.access$1702(ExtWebView.this, ExtWebView.this.childWebViewDialog.getWebView());
          ExtWebView.this.childWeb.onResizeBrowser(paramDouble1, this.val$height);
          ExtWebView.this.childWeb.setParentWeb(ExtWebView.this);
        }
      });
    }

    public String readSystemProp(String paramString)
    {
      if ("user.name".equals(paramString))
      {
        UserInfo localUserInfo4 = GlobalLogic.getInstance().getUserInfo();
        if (localUserInfo4 == null)
          return "";
        return localUserInfo4.name;
      }
      if ("user.token".equals(paramString))
      {
        UserInfo localUserInfo3 = GlobalLogic.getInstance().getUserInfo();
        if (localUserInfo3 == null)
          return "";
        return localUserInfo3.web_token;
      }
      if ("user.id".equals(paramString))
      {
        UserInfo localUserInfo2 = GlobalLogic.getInstance().getUserInfo();
        if (localUserInfo2 == null)
          return "";
        return localUserInfo2.user_id;
      }
      if ("app.name".equals(paramString))
        return App.getInstance().getPackageName();
      if ("app.version".equals(paramString))
        return DeviceInfo.getMGTVVersion();
      if ("device.mac".equals(paramString))
        return DeviceInfo.getMac();
      if ("user.vip".equals(paramString))
      {
        if (GlobalLogic.getInstance().getUserInfo() == null)
          return "";
        if (GlobalLogic.getInstance().getUserInfo().vip_power > 0);
        for (boolean bool = true; ; bool = false)
          return String.valueOf(bool);
      }
      if ("user.vip.days".equals(paramString))
      {
        UserInfo localUserInfo1 = GlobalLogic.getInstance().getUserInfo();
        if (localUserInfo1 == null)
          return "";
        return localUserInfo1.vip_days;
      }
      if ("app.package.name".equals(paramString))
        return App.getAppContext().getPackageName();
      return "";
    }

    public void releaseFocus(String paramString)
    {
      ExtWebView.this.viewHandler.post(new Runnable()
      {
        public void run()
        {
          ExtWebView.this.clearFocus();
        }
      });
    }

    public void requestFocus()
    {
      ExtWebView.this.viewHandler.post(new Runnable()
      {
        public void run()
        {
          ExtWebView.this.setFocusable(true);
          ExtWebView.this.requestFocus();
        }
      });
    }

    public void reservation(JSONObject paramJSONObject)
    {
      String str1 = getJsonStr(paramJSONObject, "reservation_type");
      if ("player".equals(str1))
      {
        Logger.i("JSExtObject", "player");
        getJsonStr(paramJSONObject, "special_id");
        String str7 = getJsonStr(paramJSONObject, "video_id");
        String str8 = getJsonStr(paramJSONObject, "media_asset_id");
        String str9 = getJsonStr(paramJSONObject, "category_id");
        String str10 = getJsonStr(paramJSONObject, "channel_name");
        String str11 = getJsonStr(paramJSONObject, "film_name");
        int j = getJsonInt(paramJSONObject, "film_type");
        int k = getJsonInt(paramJSONObject, "film_index");
        String str12 = getJsonStr(paramJSONObject, "begin_day");
        String str13 = getJsonStr(paramJSONObject, "begin_time");
        int m = getJsonInt(paramJSONObject, "time_len");
        int n = getJsonInt(paramJSONObject, "max_back_time_len");
        int i1 = getJsonInt(paramJSONObject, "min_back_time_len");
        int i2 = getJsonInt(paramJSONObject, "default_back_pos");
        String str14 = getJsonStr(paramJSONObject, "huawei_cid");
        Reservation localReservation2 = new Reservation();
        localReservation2.setVideoId(str7);
        localReservation2.setDay(str12);
        localReservation2.setBeginTime(str13);
        localReservation2.setFilm_type(j);
        localReservation2.setName(str11);
        localReservation2.setCategoryId(str9);
        localReservation2.setPackageId(str8);
        localReservation2.setChannel(str10);
        localReservation2.setTimeLen(String.valueOf(m));
        localReservation2.setNns_index(k);
        localReservation2.setReal_max_back_time_len(n);
        localReservation2.setReal_min_back_time_len(i1);
        localReservation2.setReal_default_back_pos(i2);
        localReservation2.setHuawei_cid(str14);
        ReservationService.getinstance().addReservation(localReservation2);
        UITools.ShowCustomToast(ExtWebView.this.mContext, "预约成功");
        return;
      }
      if ("web".equals(str1))
      {
        Logger.i("JSExtObject", "web");
        String str2 = getJsonStr(paramJSONObject, "film_name");
        getJsonStr(paramJSONObject, "video_id");
        String str3 = getJsonStr(paramJSONObject, "live_show_id");
        String str4 = getJsonStr(paramJSONObject, "live_show_web_url");
        String str5 = getJsonStr(paramJSONObject, "begin_day");
        String str6 = getJsonStr(paramJSONObject, "begin_time");
        Reservation localReservation1 = new Reservation();
        localReservation1.setReservationType("web");
        localReservation1.setName(str2);
        localReservation1.setVideoId(str3);
        localReservation1.setLiveShowId(str3);
        localReservation1.setLiveShowUrl(str4);
        localReservation1.setDay(str5);
        localReservation1.setBeginTime(str6);
        localReservation1.setSpecial_id("");
        int i = (int)GlobalEnv.getInstance().getReservationDelayNotifyTime();
        localReservation1.setTimeLen("0");
        if (GlobalLogic.getInstance().isUserLogined())
          mangoLiveShowReserveReport(str3, str2, str3, str4, str5, str6);
        while (true)
        {
          UITools.ShowCustomToast(ExtWebView.this.mContext, "预约成功");
          return;
          GlobalEnv.getInstance().setReservationDelayNotifyTime(0);
          ReservationService.getinstance().addReservation(localReservation1);
          MergeLocalReserveAndUpload.getInstance().addLocalLiveReserve(str3, localReservation1);
          GlobalEnv.getInstance().setReservationDelayNotifyTime(i);
        }
      }
      UITools.ShowCustomToast(ExtWebView.this.mContext, "error reservation_type:" + str1);
    }

    public void resizeBrowser(final double paramDouble1, double paramDouble2)
    {
      ExtWebView.this.viewHandler.post(new Runnable()
      {
        public void run()
        {
          ExtWebView.this.onResizeBrowser(paramDouble1, this.val$height);
        }
      });
    }

    public void sendIntent(String paramString, JSONObject paramJSONObject)
    {
      if (TextUtils.isEmpty(paramString))
        Logger.w("JSExtObject", "sendIntent mode is null!!!");
      Intent localIntent;
      label244: 
      do
      {
        return;
        String str1 = paramJSONObject.optString("package");
        if (!TextUtils.isEmpty(str1))
        {
          String str13 = App.getAppContext().getPackageName();
          if (!"com.starcor.hunan".equals(str13))
            str1 = str1.replace("com.starcor.hunan", str13);
        }
        String str2 = paramJSONObject.optString("action");
        String str3 = paramJSONObject.optString("data");
        JSONObject localJSONObject = paramJSONObject.optJSONObject("extras");
        JSONArray localJSONArray = paramJSONObject.optJSONArray("flags");
        localIntent = new Intent();
        int n;
        String str10;
        Object localObject;
        if (!TextUtils.isEmpty(str1))
        {
          int i1 = str1.indexOf("/");
          if (i1 > 0)
          {
            String str11 = str1.substring(0, i1);
            int i2 = i1 + 1;
            int i3 = str1.length();
            String str12 = str1.substring(i2, i3);
            if (str12.startsWith("."))
              str12 = "com.starcor.hunan" + str12;
            localIntent.setClassName(str11, str12);
          }
        }
        else
        {
          if (!TextUtils.isEmpty(str2))
            localIntent.setAction(str2);
          if (!TextUtils.isEmpty(str3))
            localIntent.setData(Uri.parse(str3));
          if (localJSONObject == null)
            break label432;
          n = 0;
          if (n >= localJSONObject.length())
            break label432;
          str10 = localJSONObject.names().optString(n);
          localObject = localJSONObject.opt(str10);
          if (!(localObject instanceof Integer))
            break label312;
          localIntent.putExtra(str10, (Integer)localObject);
        }
        while (true)
        {
          n++;
          break label244;
          localIntent.setPackage(str1);
          break;
          if ((localObject instanceof Long))
            localIntent.putExtra(str10, (Long)localObject);
          else if ((localObject instanceof String))
            localIntent.putExtra(str10, (String)localObject);
          else if ((localObject instanceof Double))
            localIntent.putExtra(str10, (Double)localObject);
          else if ((localObject instanceof Float))
            localIntent.putExtra(str10, (Float)localObject);
          else if ((localObject instanceof Boolean))
            localIntent.putExtra(str10, (Boolean)localObject);
        }
        localIntent.putExtra("flag_action_from_mgtv", true);
        if (localJSONArray != null)
          for (int k = 0; k < localJSONArray.length(); k++)
          {
            String str9 = localJSONArray.optString(0);
            int m = ExtWebView.this.convertString2Flag(str9);
            if (m != -1)
              localIntent.addFlags(m);
          }
        if (str1.contains("DetailPageActivity"))
        {
          String str6 = localIntent.getStringExtra("videoId");
          int i = localIntent.getIntExtra("videoType", 0);
          String str7 = localIntent.getStringExtra("package_id");
          String str8 = localIntent.getStringExtra("category_id");
          int j = localIntent.getIntExtra("viewType", -1);
          MovieData localMovieData = new MovieData();
          localMovieData.categoryId = str8;
          localMovieData.packageId = str7;
          localMovieData.videoId = str6;
          localMovieData.videoType = i;
          localMovieData.viewType = j;
          localIntent.putExtra("movieData", localMovieData);
          localIntent.putExtra("xulPageId", "DetailPage");
          Logger.i("JSExtObject", "webview DetailPageActivity movieData=" + localMovieData.toString());
        }
        if (str1.contains("SpecialPlayerActivity"))
          localIntent.putExtra("special_binding_id", localIntent.getStringExtra("special_id"));
        String str4 = App.getAppContext().getPackageName();
        if (((str4 + "/.XULActivity").equals(str1)) || ((str4 + "/" + str4 + ".XULActivity").equals(str1)))
        {
          String str5 = localIntent.getStringExtra("xulPageId");
          if (("LoginAndRegistration".equals(str5)) || ("PurchaseVIP".equals(str5)))
          {
            GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
            if ((DeviceInfo.isXiaoMi()) && (TextUtils.isEmpty(GlobalLogic.getInstance().getWebToken())))
            {
              if ("LoginAndRegistration".equals(str5))
              {
                XiaoMiOAuthManager.getInstance().startLogin(ExtWebView.this.mContext, "XiaoMiUserCenter");
                return;
              }
              if ("PurchaseVIP".equals(str5))
              {
                XiaoMiOAuthManager.getInstance().startLogin(ExtWebView.this.mContext, "PurchaseVIP");
                return;
              }
            }
          }
        }
        if ("sendBroadcast".equals(paramString))
        {
          ExtWebView.this.mContext.sendBroadcast(localIntent);
          return;
        }
        if ("startActivity".equals(paramString))
        {
          ExtWebView.this.mContext.startActivity(localIntent);
          return;
        }
      }
      while (!"startService".equals(paramString));
      label312: ExtWebView.this.mContext.startService(localIntent);
      label432:
    }

    public void sendMessage(final String paramString1, final String paramString2)
    {
      ExtWebView.this.viewHandler.post(new Runnable()
      {
        public void run()
        {
          ExtWebView.this._internalOnReceiveMessage(paramString1, paramString2);
        }
      });
    }

    public void sendMessage(final String paramString, final JSONObject paramJSONObject)
    {
      ExtWebView.this.viewHandler.post(new Runnable()
      {
        public void run()
        {
          ExtWebView.this._internalOnReceiveMessage(paramString, paramJSONObject);
        }
      });
    }

    public void setHandler(String paramString, ExtWebView.JSCallback paramJSCallback)
    {
      if ("KeyEvent".equals(paramString))
      {
        ExtWebView.access$702(ExtWebView.this, paramJSCallback);
        return;
      }
      if ("Message".equals(paramString))
      {
        ExtWebView.access$802(ExtWebView.this, paramJSCallback);
        return;
      }
      if ("Broadcast".equals(paramString))
        ExtWebView.access$902(ExtWebView.this, paramJSCallback);
      ExtWebView.this.onSetHandler(paramString, paramJSCallback);
    }

    public void showBrowser(final double paramDouble1, double paramDouble2)
    {
      ExtWebView.this.viewHandler.post(new Runnable()
      {
        public void run()
        {
          ExtWebView.this.onShowBrowser(paramDouble1, this.val$height);
        }
      });
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.ExtWebView
 * JD-Core Version:    0.6.2
 */