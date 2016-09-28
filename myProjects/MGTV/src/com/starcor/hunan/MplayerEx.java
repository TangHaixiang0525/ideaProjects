package com.starcor.hunan;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.starcor.core.domain.SpecialCategoryContent;
import com.starcor.core.domain.SpecialPlayerData;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.service.BitmapService;
import com.starcor.core.utils.Logger;
import com.starcor.media.player.MediaPlayerCore;
import com.starcor.media.player.MplayerAdPlayerView;
import com.starcor.media.player.MplayerLoadingView;
import com.starcor.media.player.MplayerPayTipsView;
import com.starcor.media.player.MplayerPreLoadingView;
import com.starcor.media.player.MplayerVODViewSpecial;
import com.starcor.xul.IXulExternalView;
import java.util.List;

public class MplayerEx extends RelativeLayout
  implements IXulExternalView
{
  private static final int DESIGN_HEIGHT_FULL_SCREEN = 720;
  private static final int DESIGN_HEIGHT_HALF_SCREEN = 367;
  private static final int DESIGN_WIDTH_FULL_SCREEN = 1280;
  private static final int DESIGN_WIDTH_HALF_SCREEN = 655;
  private static final String TAG = MplayerEx.class.getSimpleName();
  public static final String VIDEO_ID_HOST = "e2755013f2df9b3b862a7451a90688ac";
  public static final String VIDEO_ID_PREVIEW = "b2d6c11025566c27f04ccf846e2a2f57";
  public static final String VIDEO_ID_PREVIEW2 = "6113f4c0b0723ded2b0c9f5a568e2524";
  public static final String VIDEO_ID_SHAME_ME = "c75fc9dd5274766acb26ce963fdd0a9c";
  public static final String VIDEO_ID_VIP = "d0c8623d8e11f9a2bd816ad46a934647";
  private Context mContext;
  private TextView mDownloadSpeedTv;
  private Animation mLoadingAnim;
  private ImageView mLoadingIv;
  private MplayerLoadingView mLoadingPageView;
  private MediaPlayerCore mMediaPlayerCore;
  private OnScreenModeChangeListener mOnScreenModeChangeListener;
  private Rect mOrigRect;
  private MplayerPayTipsView mPayTipsView;
  private IMplayerOutListener mPlayerOutListener;
  private MplayerPreLoadingView mPreLoadingView;
  private ScreenMode mScreenMode;
  private MplayerVODViewSpecial mVodViewSpecial;

  public MplayerEx(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.mScreenMode = ScreenMode.HALF_SCREEN;
    this.mOrigRect = new Rect();
    initView();
  }

  private void displayLoading(boolean paramBoolean)
  {
    if (paramBoolean)
      if (this.mLoadingIv.getVisibility() != 0)
      {
        this.mLoadingIv.startAnimation(this.mLoadingAnim);
        this.mLoadingIv.bringToFront();
        this.mLoadingIv.setVisibility(0);
        Logger.d(TAG, "show loading view");
      }
    while (this.mLoadingIv.getVisibility() != 0)
      return;
    this.mLoadingIv.clearAnimation();
    this.mLoadingIv.setVisibility(4);
    Logger.d(TAG, "hide loading view");
  }

  private void displayLoadingPage(boolean paramBoolean)
  {
    if (paramBoolean)
      if (this.mLoadingPageView.getVisibility() != 0)
      {
        Logger.d(TAG, "displayLoadingPage true");
        this.mLoadingPageView.setVisibility(0);
        this.mLoadingPageView.bringToFront();
      }
    while (this.mLoadingPageView.getVisibility() != 0)
      return;
    Logger.d(TAG, "displayLoadingPage false");
    this.mLoadingPageView.setVisibility(4);
  }

  private void displayPreLoadingView(boolean paramBoolean, String paramString)
  {
    if (paramBoolean)
      if (this.mPreLoadingView.getVisibility() != 0)
      {
        Logger.d(TAG, "displayPreLoadingView true: " + paramString);
        this.mPreLoadingView.setVideoName(paramString);
        this.mPreLoadingView.setVisibility(0);
        this.mPreLoadingView.bringToFront();
      }
    while (this.mPreLoadingView.getVisibility() != 0)
    {
      return;
      this.mPreLoadingView.setVideoName(paramString);
      return;
    }
    Logger.d(TAG, "displayPreLoadingView false");
    this.mPreLoadingView.setVideoName(paramString);
    this.mPreLoadingView.setVisibility(8);
  }

  private void displaySpeed(boolean paramBoolean, String paramString)
  {
    if (paramBoolean)
    {
      if (this.mDownloadSpeedTv.getVisibility() != 0)
      {
        Logger.d(TAG, "displaySpeed true: " + paramString);
        this.mDownloadSpeedTv.setText(paramString);
        this.mDownloadSpeedTv.setVisibility(0);
        this.mDownloadSpeedTv.bringToFront();
        return;
      }
      this.mDownloadSpeedTv.setText(paramString);
      return;
    }
    if (this.mDownloadSpeedTv.getVisibility() == 0)
    {
      Logger.d(TAG, "displaySpeed false");
      this.mDownloadSpeedTv.setVisibility(4);
    }
    this.mDownloadSpeedTv.setText(paramString);
  }

  public static int getActualPixels(ScreenMode paramScreenMode, int paramInt)
  {
    float f = paramInt;
    if (paramScreenMode == ScreenMode.HALF_SCREEN)
      f = getHalfScreenLen(paramInt);
    return App.Operation(f);
  }

  private static float getHalfScreenLen(int paramInt)
  {
    return 0.5F + paramInt * 367 / 720;
  }

  private void initView()
  {
    setBackgroundColor(Color.parseColor("#FF000000"));
    this.mMediaPlayerCore = new MediaPlayerCore(this.mContext);
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(-1, -1);
    addView(this.mMediaPlayerCore, 0, localLayoutParams1);
    this.mVodViewSpecial = new MplayerVODViewSpecial(this.mContext, new MplayerListener(null));
    RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-1, -1);
    localLayoutParams2.addRule(10);
    addView(this.mVodViewSpecial, localLayoutParams2);
    this.mVodViewSpecial.bringToFront();
    this.mVodViewSpecial.bindMediaPlayerCore(this.mMediaPlayerCore);
    this.mLoadingPageView = new MplayerLoadingView(this.mContext, this.mScreenMode);
    RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(-1, -1);
    addView(this.mLoadingPageView, localLayoutParams3);
    this.mLoadingPageView.setVisibility(4);
    this.mPreLoadingView = new MplayerPreLoadingView(this.mContext, this.mScreenMode);
    RelativeLayout.LayoutParams localLayoutParams4 = new RelativeLayout.LayoutParams(-1, -1);
    addView(this.mPreLoadingView, localLayoutParams4);
    this.mPreLoadingView.setVisibility(4);
    this.mPayTipsView = new MplayerPayTipsView(this.mContext);
    RelativeLayout.LayoutParams localLayoutParams5 = new RelativeLayout.LayoutParams(-1, -1);
    addView(this.mPayTipsView, localLayoutParams5);
    this.mPayTipsView.setVisibility(4);
    this.mLoadingIv = new ImageView(this.mContext);
    this.mLoadingIv.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapService.getInstance(this.mContext).getBitmap("Loading_new.png")));
    this.mLoadingIv.setFocusable(false);
    this.mLoadingAnim = AnimationUtils.loadAnimation(this.mContext, 2130968577);
    this.mLoadingAnim.setInterpolator(new LinearInterpolator());
    addView(this.mLoadingIv);
    this.mLoadingIv.setVisibility(4);
    this.mDownloadSpeedTv = new TextView(this.mContext);
    this.mDownloadSpeedTv.setGravity(1);
    this.mDownloadSpeedTv.setSingleLine();
    addView(this.mDownloadSpeedTv);
    layoutOnModeChange(this.mScreenMode);
  }

  private void layoutOnModeChange(ScreenMode paramScreenMode)
  {
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(getActualPixels(paramScreenMode, 108), getActualPixels(paramScreenMode, 108));
    localLayoutParams1.addRule(13);
    this.mLoadingIv.setId(2131165203);
    this.mLoadingIv.setLayoutParams(localLayoutParams1);
    if (this.mLoadingIv.isShown())
    {
      this.mLoadingIv.clearAnimation();
      this.mLoadingIv.startAnimation(this.mLoadingAnim);
    }
    RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(getActualPixels(paramScreenMode, 150), -2);
    localLayoutParams2.addRule(13);
    localLayoutParams2.addRule(3, 2131165203);
    localLayoutParams2.topMargin = getActualPixels(paramScreenMode, 15);
    this.mDownloadSpeedTv.setTextSize(0, getActualPixels(paramScreenMode, 24));
    this.mDownloadSpeedTv.setLayoutParams(localLayoutParams2);
  }

  public void bindData(List<SpecialPlayerData> paramList)
  {
    this.mVodViewSpecial.bindData(paramList);
  }

  public void changeScreenMode(ScreenMode paramScreenMode)
  {
    if (this.mScreenMode != paramScreenMode)
    {
      if (this.mOnScreenModeChangeListener != null)
        this.mOnScreenModeChangeListener.onScreenModeChange(paramScreenMode);
      this.mScreenMode = paramScreenMode;
      layoutOnModeChange(this.mScreenMode);
      if (paramScreenMode != ScreenMode.FULL_SCREEN)
        break label92;
      extMoveTo(0, 0, App.SCREEN_WIDTH, App.SCREEN_HEIGHT);
    }
    while (true)
    {
      this.mLoadingPageView.changeMode(paramScreenMode);
      this.mPreLoadingView.changeMode(paramScreenMode);
      if (this.mVodViewSpecial != null)
        this.mVodViewSpecial.setScreenMode(this.mScreenMode);
      return;
      label92: extMoveTo(this.mOrigRect.left, this.mOrigRect.top, this.mOrigRect.width(), this.mOrigRect.height());
    }
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    Logger.d(TAG, "dispatchKeyEvent event: " + paramKeyEvent);
    if (this.mVodViewSpecial != null)
      return this.mVodViewSpecial.dispatchKeyEvent(paramKeyEvent);
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public void displayPayTip(boolean paramBoolean)
  {
    if (paramBoolean)
      if (this.mPayTipsView.getVisibility() != 0)
      {
        this.mPayTipsView.setVisibility(0);
        this.mPayTipsView.bringToFront();
        Logger.d(TAG, "show PayTip view");
      }
    while (this.mPayTipsView.getVisibility() != 0)
      return;
    this.mPayTipsView.setVisibility(4);
    Logger.d(TAG, "hide PayTip view");
  }

  public void extDestroy()
  {
    Logger.d(TAG, "extDestroy...");
  }

  public void extHide()
  {
    setVisibility(8);
  }

  public void extMoveTo(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
    localLayoutParams.leftMargin = paramInt1;
    localLayoutParams.topMargin = paramInt2;
    localLayoutParams.width = paramInt3;
    localLayoutParams.height = paramInt4;
    requestLayout();
  }

  public void extMoveTo(Rect paramRect)
  {
    this.mOrigRect.set(paramRect);
    extMoveTo(paramRect.left, paramRect.top, paramRect.width(), paramRect.height());
  }

  public void extOnBlur()
  {
    clearFocus();
  }

  public void extOnFocus()
  {
    requestFocus();
  }

  public boolean extOnKeyEvent(KeyEvent paramKeyEvent)
  {
    Logger.d(TAG, "extOnKeyEvent event: " + paramKeyEvent);
    return false;
  }

  public void extShow()
  {
    setVisibility(0);
  }

  public void extSyncData()
  {
  }

  public String getAttr(String paramString1, String paramString2)
  {
    return null;
  }

  public boolean isFullScreen()
  {
    return this.mScreenMode == ScreenMode.FULL_SCREEN;
  }

  public boolean isPayTipVisible()
  {
    if (this.mPayTipsView == null);
    while (this.mPayTipsView.getVisibility() != 0)
      return false;
    return true;
  }

  public boolean setAttr(String paramString1, String paramString2)
  {
    return false;
  }

  public void setFullScreen()
  {
    changeScreenMode(ScreenMode.FULL_SCREEN);
  }

  public void setHalfScreen()
  {
    changeScreenMode(ScreenMode.HALF_SCREEN);
  }

  public void setMPlayerOutListener(IMplayerOutListener paramIMplayerOutListener)
  {
    this.mPlayerOutListener = paramIMplayerOutListener;
  }

  public void setOnScreenModeChangeListener(OnScreenModeChangeListener paramOnScreenModeChangeListener)
  {
    this.mOnScreenModeChangeListener = paramOnScreenModeChangeListener;
  }

  public void start(SpecialCategoryContent paramSpecialCategoryContent)
  {
    this.mVodViewSpecial.reportPlayState(8);
    if ((this.mVodViewSpecial.getAdPlayerView() != null) && (this.mVodViewSpecial.getAdPlayerView().getVisibility() == 0))
      this.mVodViewSpecial.getAdPlayerView().reportAdExit();
    this.mVodViewSpecial.mIsAutoPlay = false;
    this.mVodViewSpecial.start(paramSpecialCategoryContent);
  }

  public void start(SpecialCategoryContent paramSpecialCategoryContent, long paramLong)
  {
    this.mVodViewSpecial.reportPlayState(8);
    if ((this.mVodViewSpecial.getAdPlayerView() != null) && (this.mVodViewSpecial.getAdPlayerView().getVisibility() == 0))
      this.mVodViewSpecial.getAdPlayerView().reportAdExit();
    this.mVodViewSpecial.mIsAutoPlay = false;
    this.mVodViewSpecial.start(paramSpecialCategoryContent, paramLong);
  }

  public void stop()
  {
    if (this.mVodViewSpecial != null)
    {
      this.mVodViewSpecial.reportPlayState(8);
      this.mVodViewSpecial.stopPlayer();
      this.mVodViewSpecial.onDestroy();
    }
  }

  public static abstract interface IMplayerListener
  {
    public abstract void onAuthFail(SpecialCategoryContent paramSpecialCategoryContent, VideoInfo paramVideoInfo, int paramInt);

    public abstract void onDisplayError(boolean paramBoolean, String paramString);

    public abstract void onDisplayLoading(boolean paramBoolean);

    public abstract void onDisplayLoadingPage(boolean paramBoolean);

    public abstract void onDisplayPreLoadingView(boolean paramBoolean, String paramString);

    public abstract void onDisplaySpeed(boolean paramBoolean, String paramString);

    public abstract void onPreviewComplete(SpecialCategoryContent paramSpecialCategoryContent, VideoInfo paramVideoInfo, int paramInt);

    public abstract void onStartPlay(SpecialCategoryContent paramSpecialCategoryContent);
  }

  public static abstract interface IMplayerOutListener
  {
    public abstract void onAuthFail(SpecialCategoryContent paramSpecialCategoryContent, VideoInfo paramVideoInfo, int paramInt);

    public abstract void onDisplayError(boolean paramBoolean, String paramString);

    public abstract void onPreviewComplete(SpecialCategoryContent paramSpecialCategoryContent, VideoInfo paramVideoInfo, int paramInt);

    public abstract void onStartPlay(SpecialCategoryContent paramSpecialCategoryContent);
  }

  private class MplayerListener
    implements MplayerEx.IMplayerListener
  {
    private MplayerListener()
    {
    }

    public void onAuthFail(SpecialCategoryContent paramSpecialCategoryContent, VideoInfo paramVideoInfo, int paramInt)
    {
      if (MplayerEx.this.mPlayerOutListener != null)
        MplayerEx.this.mPlayerOutListener.onAuthFail(paramSpecialCategoryContent, paramVideoInfo, paramInt);
    }

    public void onDisplayError(boolean paramBoolean, String paramString)
    {
      if (MplayerEx.this.mPlayerOutListener != null)
        MplayerEx.this.mPlayerOutListener.onDisplayError(paramBoolean, paramString);
    }

    public void onDisplayLoading(boolean paramBoolean)
    {
      MplayerEx.this.displayLoading(paramBoolean);
    }

    public void onDisplayLoadingPage(boolean paramBoolean)
    {
      MplayerEx.this.displayLoadingPage(paramBoolean);
    }

    public void onDisplayPreLoadingView(boolean paramBoolean, String paramString)
    {
      MplayerEx.this.displayPreLoadingView(paramBoolean, paramString);
    }

    public void onDisplaySpeed(boolean paramBoolean, String paramString)
    {
      MplayerEx.this.displaySpeed(paramBoolean, paramString);
    }

    public void onPreviewComplete(SpecialCategoryContent paramSpecialCategoryContent, VideoInfo paramVideoInfo, int paramInt)
    {
      if (MplayerEx.this.mPlayerOutListener != null)
        MplayerEx.this.mPlayerOutListener.onPreviewComplete(paramSpecialCategoryContent, paramVideoInfo, paramInt);
    }

    public void onStartPlay(SpecialCategoryContent paramSpecialCategoryContent)
    {
      if (MplayerEx.this.mPlayerOutListener != null)
        MplayerEx.this.mPlayerOutListener.onStartPlay(paramSpecialCategoryContent);
    }
  }

  public static abstract interface OnScreenModeChangeListener
  {
    public abstract void onScreenModeChange(MplayerEx.ScreenMode paramScreenMode);
  }

  public static enum ScreenMode
  {
    static
    {
      FULL_SCREEN = new ScreenMode("FULL_SCREEN", 1);
      ScreenMode[] arrayOfScreenMode = new ScreenMode[2];
      arrayOfScreenMode[0] = HALF_SCREEN;
      arrayOfScreenMode[1] = FULL_SCREEN;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.MplayerEx
 * JD-Core Version:    0.6.2
 */