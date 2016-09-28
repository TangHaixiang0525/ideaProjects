package com.starcor.media.player;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.starcor.core.service.BitmapService;
import com.starcor.hunan.MplayerEx;
import com.starcor.hunan.MplayerEx.ScreenMode;

public class MplayerLoadingView extends RelativeLayout
{
  private Context mContext;
  private Drawable mLoadingDrawable;
  private ImageView mLoadingIv;
  private Animation mRotateAnimation;
  private MplayerEx.ScreenMode mScreenMode;

  public MplayerLoadingView(Context paramContext)
  {
    super(paramContext);
  }

  public MplayerLoadingView(Context paramContext, MplayerEx.ScreenMode paramScreenMode)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.mScreenMode = paramScreenMode;
    initView();
  }

  private void initView()
  {
    LayoutInflater.from(this.mContext).inflate(2130903074, this);
    this.mLoadingIv = ((ImageView)findViewById(2131165325));
    this.mRotateAnimation = AnimationUtils.loadAnimation(this.mContext, 2130968577);
    this.mRotateAnimation.setInterpolator(new LinearInterpolator());
    this.mLoadingDrawable = new BitmapDrawable(getResources(), BitmapService.getInstance(this.mContext).getBitmap("Loading_new.png"));
    this.mLoadingIv.setBackgroundDrawable(this.mLoadingDrawable);
    layoutOnModeChanged(this.mScreenMode);
  }

  private void layoutOnModeChanged(MplayerEx.ScreenMode paramScreenMode)
  {
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(getActualPixels(paramScreenMode, 108), getActualPixels(paramScreenMode, 108));
    localLayoutParams.addRule(13);
    this.mLoadingIv.setLayoutParams(localLayoutParams);
    this.mLoadingIv.clearAnimation();
    this.mLoadingIv.startAnimation(this.mRotateAnimation);
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
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerLoadingView
 * JD-Core Version:    0.6.2
 */