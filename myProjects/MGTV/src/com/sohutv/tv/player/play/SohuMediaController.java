package com.sohutv.tv.player.play;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.sohutv.tv.player.interfaces.ISohuMediaControllerCallback;

public class SohuMediaController extends FrameLayout
{
  private static final int sDefaultTimeout = 10000;
  protected View mAnchor;
  protected Context mContext;
  private FrameLayout mDecor;
  private WindowManager.LayoutParams mDecorLayoutParams;
  protected ISohuMediaControllerCallback mPlayer;
  protected View mRoot;
  private boolean mShowing;
  private int mTimeOut;
  private WindowManager mWindowManager;

  public SohuMediaController(Context paramContext)
  {
    this(paramContext, true);
    this.mContext = paramContext;
  }

  public SohuMediaController(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mRoot = this;
    this.mContext = paramContext;
  }

  public SohuMediaController(Context paramContext, boolean paramBoolean)
  {
    super(paramContext);
    this.mContext = paramContext;
    initFloatingWindowLayout();
    initFloatingWindow();
  }

  private void initFloatingWindow()
  {
    this.mWindowManager = ((WindowManager)this.mContext.getSystemService("window"));
    this.mDecor = new FrameLayout(this.mContext);
    this.mDecor.addView(this);
    this.mDecor.setBackgroundResource(17170445);
    setFocusable(true);
    setFocusableInTouchMode(true);
    setDescendantFocusability(262144);
    requestFocus();
  }

  private void initFloatingWindowLayout()
  {
    this.mDecorLayoutParams = new WindowManager.LayoutParams();
    WindowManager.LayoutParams localLayoutParams = this.mDecorLayoutParams;
    localLayoutParams.gravity = 48;
    localLayoutParams.height = -1;
    localLayoutParams.x = 0;
    localLayoutParams.format = -3;
    localLayoutParams.type = 1000;
    localLayoutParams.flags = (0x820020 | localLayoutParams.flags);
    localLayoutParams.token = null;
    localLayoutParams.windowAnimations = 0;
  }

  private void updateFloatingWindowLayout()
  {
    int[] arrayOfInt = new int[2];
    this.mAnchor.getLocationOnScreen(arrayOfInt);
    WindowManager.LayoutParams localLayoutParams = this.mDecorLayoutParams;
    localLayoutParams.width = this.mAnchor.getWidth();
    localLayoutParams.y = 0;
  }

  protected void doWhenHide()
  {
  }

  public int getTimeOut()
  {
    return this.mTimeOut;
  }

  public void hide()
  {
    if (this.mAnchor == null);
    while (!this.mShowing)
      return;
    try
    {
      doWhenHide();
      removeControllerView();
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      Log.w("MediaController", "already removed");
    }
  }

  public boolean isShowing()
  {
    return this.mShowing;
  }

  protected View makeControllerView()
  {
    return this.mRoot;
  }

  protected void removeControllerView()
  {
    this.mWindowManager.removeView(this.mDecor);
    this.mShowing = false;
  }

  protected void removeControllerView2()
  {
    this.mWindowManager.removeView(this.mDecor);
    this.mShowing = false;
  }

  public void setAnchorView(View paramView)
  {
    this.mAnchor = paramView;
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, -1);
    removeAllViews();
    addView(makeControllerView(), localLayoutParams);
  }

  public void setMediaPlayer(ISohuMediaControllerCallback paramISohuMediaControllerCallback)
  {
    this.mPlayer = paramISohuMediaControllerCallback;
  }

  public void show()
  {
    show(10000);
  }

  public void show(int paramInt)
  {
    this.mTimeOut = paramInt;
    if ((!this.mShowing) && (this.mAnchor != null))
    {
      updateFloatingWindowLayout();
      this.mWindowManager.addView(this.mDecor, this.mDecorLayoutParams);
      this.mShowing = true;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.play.SohuMediaController
 * JD-Core Version:    0.6.2
 */