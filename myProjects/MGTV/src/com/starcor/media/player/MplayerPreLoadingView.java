package com.starcor.media.player;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.service.BitmapService;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.NetSpeed;
import com.starcor.hunan.App;
import com.starcor.hunan.MplayerEx;
import com.starcor.hunan.MplayerEx.ScreenMode;
import java.text.DecimalFormat;

public class MplayerPreLoadingView extends RelativeLayout
{
  private static final String TAG = MplayerPreLoadingView.class.getSimpleName();
  private Context mContext;
  private Drawable mLoadingDrawable;
  private ImageView mLoadingIv;
  private NetSpeed mNetSpeed;
  private LinearLayout mRootView;
  private Animation mRotateAnimation;
  private MplayerEx.ScreenMode mScreenMode;
  private TextView mSpeedTv;
  private int mTipImageOrigHeight = 0;
  private int mTipImageOrigWidth = 0;
  private ImageView mTipIv;
  private boolean mUpdateSpeed;
  private TextView mVideoNameTv;

  public MplayerPreLoadingView(Context paramContext, MplayerEx.ScreenMode paramScreenMode)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.mScreenMode = paramScreenMode;
    initView();
  }

  private void initView()
  {
    LayoutInflater.from(this.mContext).inflate(2130903075, this);
    this.mRootView = ((LinearLayout)findViewById(2131165326));
    this.mLoadingIv = ((ImageView)findViewById(2131165325));
    this.mVideoNameTv = ((TextView)findViewById(2131165327));
    this.mSpeedTv = ((TextView)findViewById(2131165328));
    this.mTipIv = ((ImageView)findViewById(2131165329));
    this.mRootView.setBackgroundDrawable(new BitmapDrawable(GlobalEnv.getInstance().getPreLoadingBkg(this.mContext)));
    this.mRotateAnimation = AnimationUtils.loadAnimation(this.mContext, 2130968577);
    this.mRotateAnimation.setInterpolator(new LinearInterpolator());
    this.mLoadingDrawable = new BitmapDrawable(getResources(), BitmapService.getInstance(this.mContext).getBitmap("Loading_new.png"));
    this.mLoadingIv.setBackgroundDrawable(this.mLoadingDrawable);
    Drawable localDrawable = GlobalLogic.getInstance().getRandomLoadingImage();
    if (localDrawable != null)
    {
      this.mTipIv.setImageDrawable(localDrawable);
      this.mTipImageOrigWidth = localDrawable.getIntrinsicWidth();
      this.mTipImageOrigHeight = localDrawable.getIntrinsicHeight();
    }
    layoutOnModeChanged(this.mScreenMode);
  }

  private void layoutOnModeChanged(MplayerEx.ScreenMode paramScreenMode)
  {
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(getActualPixels(paramScreenMode, 108), getActualPixels(paramScreenMode, 108));
    localLayoutParams1.topMargin = getActualPixels(paramScreenMode, 215);
    this.mLoadingIv.setLayoutParams(localLayoutParams1);
    this.mLoadingIv.clearAnimation();
    this.mLoadingIv.startAnimation(this.mRotateAnimation);
    LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(-2, -2);
    localLayoutParams2.topMargin = getActualPixels(paramScreenMode, 20);
    localLayoutParams2.leftMargin = getActualPixels(paramScreenMode, 60);
    localLayoutParams2.rightMargin = getActualPixels(paramScreenMode, 60);
    this.mVideoNameTv.setLayoutParams(localLayoutParams2);
    this.mVideoNameTv.setTextSize(0, getActualPixels(paramScreenMode, 31));
    this.mVideoNameTv.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
    LinearLayout.LayoutParams localLayoutParams3 = new LinearLayout.LayoutParams(-2, -2);
    localLayoutParams3.topMargin = getActualPixels(paramScreenMode, 8);
    this.mSpeedTv.setLayoutParams(localLayoutParams3);
    this.mSpeedTv.setTextSize(0, getActualPixels(paramScreenMode, 28));
    if (this.mTipImageOrigWidth != 0)
    {
      LinearLayout.LayoutParams localLayoutParams4 = new LinearLayout.LayoutParams(getActualPixels(paramScreenMode, this.mTipImageOrigWidth), getActualPixels(paramScreenMode, this.mTipImageOrigHeight));
      localLayoutParams4.topMargin = getActualPixels(paramScreenMode, 105);
      this.mTipIv.setLayoutParams(localLayoutParams4);
    }
  }

  private void setSpeedText(String paramString)
  {
    this.mSpeedTv.setText(paramString);
  }

  private void stopUpdateSpeed()
  {
    if (this.mNetSpeed != null)
      this.mNetSpeed.stopCalculateNetSpeed();
    this.mUpdateSpeed = false;
  }

  private void updateSpeed()
  {
    if (this.mNetSpeed != null)
    {
      if (!this.mUpdateSpeed)
      {
        this.mNetSpeed.startCalculateNetSpeed();
        this.mUpdateSpeed = true;
      }
      return;
    }
    Handler local1 = new Handler(Looper.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what == 1)
        {
          float f1 = 1.0F * paramAnonymousMessage.arg1;
          if (f1 > 1024.0F)
          {
            float f2 = f1 / 1024.0F;
            DecimalFormat localDecimalFormat = new DecimalFormat("#.00");
            localDecimalFormat.format(f2);
            MplayerPreLoadingView.this.setSpeedText(localDecimalFormat.format(f2) + " MB/S");
          }
        }
        else
        {
          return;
        }
        MplayerPreLoadingView.this.setSpeedText(paramAnonymousMessage.arg1 + " KB/S");
      }
    };
    this.mNetSpeed = NetSpeed.getInstant(App.getAppContext(), local1);
    this.mNetSpeed.startCalculateNetSpeed();
    this.mUpdateSpeed = true;
  }

  public void changeMode(MplayerEx.ScreenMode paramScreenMode)
  {
    if (this.mScreenMode == paramScreenMode)
      return;
    this.mScreenMode = paramScreenMode;
    layoutOnModeChanged(this.mScreenMode);
  }

  public int getActualPixels(MplayerEx.ScreenMode paramScreenMode, int paramInt)
  {
    return MplayerEx.getActualPixels(paramScreenMode, paramInt);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    Logger.d(TAG, "onLayout changed: " + paramBoolean + ", left: " + paramInt1 + ", top: " + paramInt2 + ", right: " + paramInt3 + "bottom: " + paramInt4);
  }

  public void setVideoName(String paramString)
  {
    this.mVideoNameTv.setText(paramString);
  }

  public void setVisibility(int paramInt)
  {
    if (paramInt == 0)
      updateSpeed();
    while (true)
    {
      super.setVisibility(paramInt);
      return;
      stopUpdateSpeed();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerPreLoadingView
 * JD-Core Version:    0.6.2
 */