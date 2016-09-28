package com.starcor.hunan.widget.uilog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import com.starcor.core.utils.Logger;

public class DispatcherEventLayout extends RelativeLayout
{
  private UILogConfigView mConfigView;

  public DispatcherEventLayout(Context paramContext)
  {
    super(paramContext);
    initViews(paramContext);
  }

  public DispatcherEventLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initViews(paramContext);
  }

  public DispatcherEventLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initViews(paramContext);
  }

  private void initViews(Context paramContext)
  {
    this.mConfigView = new UILogConfigView(paramContext.getApplicationContext());
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 0)
    {
      Logger.i("dispathcherEvent", "keycode=" + paramKeyEvent.getKeyCode());
      this.mConfigView.customOnKeyDown(getContext(), paramKeyEvent.getKeyCode());
    }
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.mConfigView.forceHideView(getContext().getApplicationContext());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.uilog.DispatcherEventLayout
 * JD-Core Version:    0.6.2
 */