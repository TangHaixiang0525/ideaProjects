package com.sohutv.tv.player.partner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.sohu.app.ads.sdk.SdkFactory;
import com.sohu.app.ads.sdk.exception.SdkException;
import com.sohu.app.ads.sdk.iterface.IAdErrorEventListener;
import com.sohu.app.ads.sdk.iterface.IAdEvent;
import com.sohu.app.ads.sdk.iterface.IAdEventListener;
import com.sohu.app.ads.sdk.iterface.IAdsLoadedError;
import com.sohu.app.ads.sdk.iterface.IAdsLoadedListener;
import com.sohu.app.ads.sdk.iterface.ILoadedEvent;
import com.sohu.app.ads.sdk.iterface.ILoader;
import com.sohu.app.ads.sdk.iterface.StartPictureCallBack;
import com.sohu.app.ads.sdk.res.AdType;
import com.sohu.logger.log.OutputLog;
import com.sohutv.tv.player.ad.b;
import com.sohutv.tv.player.ad.d;
import com.sohutv.tv.player.ad.d.a;
import com.sohutv.tv.player.entity.SystemConstantEntity;
import com.sohutv.tv.player.interfaces.ISohuStartAdCallback;
import com.sohutv.tv.player.util.HttpAPI;
import com.sohutv.tv.player.util.HttpAPI.ErrorListener;
import com.sohutv.tv.player.util.HttpAPI.SuccessListener;
import com.sohutv.tv.player.util.a.c;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;

public class SohuStartAdView extends FrameLayout
  implements IAdErrorEventListener, IAdEventListener, IAdsLoadedListener
{
  private static final int DEFAULT_OPEN_AD_SHOWING_TIME = 3;
  private static final int DEFAULT_START_EXTRA_TIME_OUT = 3;
  private static final int INDEX_END = 1;
  private static final int INDEX_OPEN_AD = 0;
  private static final int INDEX_START = 0;
  private static final int INDEX_SYSTEM_CONFIG = 1;
  private TextView adTimeTextView;
  private SdkFactory factory;
  private boolean isAlreadyEnded;
  private boolean isStartedCounting = false;
  private ILoader mAdLoader;
  private Bitmap mBgBitmap;
  private Context mContext;
  private a mHandler;
  private int remainTime;
  private SparseArray<Boolean> requestResults;
  private ISohuStartAdCallback startAdCallback;
  private int startTimeoutTime;

  public SohuStartAdView(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }

  public SohuStartAdView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
  }

  public SohuStartAdView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mContext = paramContext;
  }

  public SohuStartAdView(Context paramContext, ISohuStartAdCallback paramISohuStartAdCallback)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.startAdCallback = paramISohuStartAdCallback;
    init(paramContext);
  }

  private void endStartAdCallback()
  {
    endStartAdCallback(true);
  }

  private void endStartAdCallback(boolean paramBoolean)
  {
    if ((paramBoolean) && (!isAllRequestsDone()));
    while (this.isAlreadyEnded)
      return;
    if (this.startAdCallback != null)
      this.startAdCallback.onEnd();
    if (!this.startAdCallback.isDelayedDisappear())
      hideOpenAdView();
    this.mBgBitmap = null;
    this.startAdCallback = null;
    releaseAdLoader();
    this.isAlreadyEnded = true;
  }

  @SuppressLint({"NewApi"})
  private void findView()
  {
    this.adTimeTextView = new TextView(this.mContext);
    int i = com.sohutv.tv.player.util.a.a(this.mContext, 199);
    int j = com.sohutv.tv.player.util.a.a(this.mContext, 55);
    int k = com.sohutv.tv.player.util.a.a(this.mContext, 81);
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(i, j);
    localLayoutParams.gravity = 53;
    localLayoutParams.topMargin = 0;
    localLayoutParams.rightMargin = 0;
    this.adTimeTextView.setLayoutParams(localLayoutParams);
    this.adTimeTextView.setGravity(17);
    this.adTimeTextView.setPadding(k, 0, k, 0);
    this.adTimeTextView.setTextColor(-16777216);
    this.adTimeTextView.setTextSize(1, 30);
    if (0 != 0)
      this.adTimeTextView.setBackgroundDrawable(new BitmapDrawable(getResources(), null));
    addView(this.adTimeTextView);
    this.adTimeTextView.setVisibility(4);
  }

  private d.a getAdUrlParam()
  {
    return new d.a(null, null, "", null, null, null, null, null, null, null);
  }

  @SuppressLint({"UseSparseArrays"})
  private void init(Context paramContext)
  {
    int i = 3;
    this.mContext = paramContext;
    int j;
    if (b.a(this.mContext) == 0)
    {
      j = i;
      this.remainTime = j;
      if (b.a(this.mContext) != 0)
        break label111;
      label34: this.startTimeoutTime = (i + 3);
      this.requestResults = new SparseArray();
      initStartAdLoader();
      findView();
      this.mHandler = new a(this);
      this.mHandler.sendEmptyMessage(1);
      if (!com.sohutv.tv.player.ad.a.a(this.mContext))
        break label122;
      requestAndShowStartAd();
    }
    while (true)
    {
      requestSystemConstant();
      return;
      j = b.a(this.mContext);
      break;
      label111: i = b.a(this.mContext);
      break label34;
      label122: setAdvBackground(null);
    }
  }

  private void initStartAdLoader()
  {
    this.factory = SdkFactory.getInstance();
    try
    {
      this.mAdLoader = this.factory.createAdsLoader(this.mContext);
      this.mAdLoader.setTimeOut(7000);
      this.mAdLoader.setDeviceType(2);
      this.mAdLoader.addAdsLoadedListener(this);
      this.mAdLoader.addAdErrorListener(this);
      return;
    }
    catch (SdkException localSdkException)
    {
      while (true)
        localSdkException.printStackTrace();
    }
  }

  private boolean isAllRequestsDone()
  {
    if (this.requestResults != null)
      for (int i = 0; i <= 1; i++)
      {
        if (this.requestResults.get(i) == null);
        for (boolean bool = false; !bool; bool = ((Boolean)this.requestResults.get(i)).booleanValue())
          return false;
      }
    return true;
  }

  private void releaseAdLoader()
  {
    this.factory = null;
    if (this.mAdLoader != null)
    {
      this.mAdLoader.addAdErrorListener(null);
      this.mAdLoader.addAdsLoadedListener(null);
      this.mAdLoader.destory();
      this.mAdLoader = null;
    }
  }

  private void requestAndShowStartAd()
  {
    d.a locala = getAdUrlParam();
    StartPictureCallBack local1 = new StartPictureCallBack()
    {
      public void callBack(HashMap<String, Object> paramAnonymousHashMap, boolean paramAnonymousBoolean)
      {
        Log.e("OpenAd", "getCallBack");
        if ((paramAnonymousBoolean) && (paramAnonymousHashMap != null))
        {
          String str = paramAnonymousHashMap.get("imageUrl").toString();
          SohuStartAdView.this.setAdvBackground(str);
          return;
        }
        SohuStartAdView.this.setAdvBackground(null);
      }
    };
    try
    {
      this.mAdLoader.requestStartPicture(this.mContext, d.a(this.mContext, AdType.STARTIMG, false, locala), local1);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  @SuppressLint({"UseSparseArrays"})
  private void requestComplete(int paramInt)
  {
    if (this.requestResults == null)
      this.requestResults = new SparseArray();
    this.requestResults.put(paramInt, Boolean.valueOf(true));
    endStartAdCallback();
  }

  private void requestSystemConstant()
  {
    String str = c.a();
    OutputLog.i("sysconstants", "httpurl = " + str);
    HttpAPI.get(str, SystemConstantEntity.class, new HttpAPI.SuccessListener()
    {
      public void a(SystemConstantEntity paramAnonymousSystemConstantEntity)
      {
        if (paramAnonymousSystemConstantEntity != null)
        {
          b.a(SohuStartAdView.this.mContext, paramAnonymousSystemConstantEntity.getOpenAdShowingTime());
          com.sohutv.tv.player.ad.a.a(SohuStartAdView.this.mContext, "0".equals(paramAnonymousSystemConstantEntity.getAd_main_switch()));
          com.sohutv.tv.player.ad.a.b(SohuStartAdView.this.mContext, "1".equals(paramAnonymousSystemConstantEntity.getAd_vip_show_switch()));
          com.sohutv.tv.player.ad.a.c(SohuStartAdView.this.mContext, "1".equals(paramAnonymousSystemConstantEntity.getAd_oad_switch()));
        }
        SohuStartAdView.this.requestComplete(1);
      }
    }
    , new HttpAPI.ErrorListener()
    {
      public void onErrorResponse(int paramAnonymousInt, Throwable paramAnonymousThrowable)
      {
        Log.e("Sohuplayer", "启动广告HTTP请求错误 ");
        if (paramAnonymousThrowable != null)
          paramAnonymousThrowable.printStackTrace();
        SohuStartAdView.this.requestComplete(1);
      }
    });
  }

  private void setAdvBackground(String paramString)
  {
    if ((!com.sohutv.tv.player.util.c.a.a(paramString)) && (new File(paramString).exists()))
    {
      DisplayMetrics localDisplayMetrics = this.mContext.getApplicationContext().getResources().getDisplayMetrics();
      this.mBgBitmap = com.sohutv.tv.player.util.b.a.a(paramString, localDisplayMetrics.widthPixels, localDisplayMetrics.heightPixels);
      if (this.mBgBitmap != null)
        setBackgroundDrawable(new BitmapDrawable(getResources(), this.mBgBitmap));
    }
    while (true)
    {
      if (!this.isStartedCounting)
      {
        this.mHandler.sendEmptyMessage(0);
        this.isStartedCounting = true;
      }
      return;
      if ((this.startAdCallback == null) || (!this.startAdCallback.hasDefaultOpenView()))
        break;
      setBackgroundDrawable(new BitmapDrawable(getResources(), this.startAdCallback.getDefaultOpenBitmap()));
    }
    endStartAdCallback(false);
  }

  public void hideOpenAdView()
  {
    setVisibility(8);
  }

  public void onAdClickEvent(String paramString)
  {
  }

  public void onAdEvent(IAdEvent paramIAdEvent)
  {
  }

  public void onAdPlayTime(int paramInt)
  {
  }

  public void onAdsLoadedError(IAdsLoadedError paramIAdsLoadedError)
  {
  }

  public void onAdsManagerLoaded(ILoadedEvent paramILoadedEvent)
  {
  }

  public void onImpressEvent(boolean paramBoolean, String paramString)
  {
  }

  public void release()
  {
    releaseAdLoader();
    this.mBgBitmap = null;
    this.startAdCallback = null;
    if (this.mHandler != null)
    {
      this.mHandler.removeCallbacksAndMessages(null);
      this.mHandler = null;
    }
    if (this.requestResults != null)
    {
      this.requestResults.clear();
      this.requestResults = null;
    }
  }

  public void setStartAdCallback(ISohuStartAdCallback paramISohuStartAdCallback)
  {
    this.startAdCallback = paramISohuStartAdCallback;
    init(this.mContext);
  }

  public void updateRemainTime(int paramInt)
  {
    if ((this.startAdCallback != null) && (this.startAdCallback.isCustomedTimeView()))
      this.startAdCallback.remainTime(paramInt);
    while (this.adTimeTextView == null)
      return;
    if (this.adTimeTextView.getVisibility() != 0)
      this.adTimeTextView.setVisibility(0);
    this.adTimeTextView.setText(String.valueOf(paramInt));
  }

  public static class TimeTextViewProperty
  {
    public int marginRight;
    public int marginTop;
    public int paddingLeftRight;
    public Bitmap textBackgroundBitmap;
    public int textColor;
    public int textHeight;
    public int textSize;
    public int textWidth;
  }

  public static class a extends Handler
  {
    private WeakReference<SohuStartAdView> a;

    public a(SohuStartAdView paramSohuStartAdView)
    {
      this.a = new WeakReference(paramSohuStartAdView);
    }

    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
      if (this.a == null);
      SohuStartAdView localSohuStartAdView;
      do
      {
        return;
        localSohuStartAdView = (SohuStartAdView)this.a.get();
      }
      while (localSohuStartAdView == null);
      switch (paramMessage.what)
      {
      default:
        return;
      case 0:
        localSohuStartAdView.updateRemainTime(localSohuStartAdView.remainTime);
        if (localSohuStartAdView.remainTime == 0)
        {
          localSohuStartAdView.requestComplete(0);
          return;
        }
        SohuStartAdView.access$110(localSohuStartAdView);
        sendEmptyMessageDelayed(0, 1000L);
        return;
      case 1:
      }
      if (localSohuStartAdView.startTimeoutTime == 0)
      {
        localSohuStartAdView.endStartAdCallback(false);
        return;
      }
      SohuStartAdView.access$310(localSohuStartAdView);
      sendEmptyMessageDelayed(1, 1000L);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.partner.SohuStartAdView
 * JD-Core Version:    0.6.2
 */