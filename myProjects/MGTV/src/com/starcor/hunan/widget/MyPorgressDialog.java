package com.starcor.hunan.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.http.BitmapCache;
import com.starcor.core.service.BitmapService;
import com.starcor.hunan.App;
import com.starcor.remote_key.AirControlHost;
import com.starcor.remote_key.IAirCommandListener;
import com.starcor.remote_key.IAirCommandListener.Stub;
import com.starcor.ui.UITools;

public class MyPorgressDialog extends ProgressDialog
{
  private IAirCommandListener _airCommandListener;
  private ImageView iv_progress;
  private Context mContext;
  private RotateAnimation rotateAnimation;
  private LightTextView tv_message;

  public MyPorgressDialog(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }

  public MyPorgressDialog(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
    this.mContext = paramContext;
  }

  private boolean onAirControlKeyEvent(KeyEvent paramKeyEvent)
  {
    if (dispatchKeyEvent(paramKeyEvent));
    label55: 
    do
    {
      return true;
      View localView1 = getCurrentFocus();
      while (true)
      {
        if (localView1 == null)
          break label55;
        if (localView1.dispatchKeyEvent(paramKeyEvent))
          break;
        ViewParent localViewParent2 = localView1.getParent();
        if ((localViewParent2 instanceof View))
          localView1 = (View)localViewParent2;
        else
          localView1 = null;
      }
    }
    while (paramKeyEvent.getAction() != 0);
    int i = -1;
    label100: View localView2;
    Rect localRect;
    switch (paramKeyEvent.getKeyCode())
    {
    default:
      if (i != -1)
      {
        localView2 = getCurrentFocus();
        localRect = new Rect();
        if (localView2 != null)
          localView2.getFocusedRect(localRect);
      }
      break;
    case 21:
    case 22:
    case 19:
    case 20:
    }
    while (localView2 != null)
    {
      View localView3 = localView2.focusSearch(i);
      if ((localView3 != null) && (localView3.requestFocus(i, localRect)))
        break;
      ViewParent localViewParent1 = localView2.getParent();
      if ((localViewParent1 instanceof View))
      {
        localView2 = (View)localViewParent1;
        continue;
        i = 17;
        break label100;
        i = 66;
        break label100;
        i = 33;
        break label100;
        i = 130;
        break label100;
        break;
      }
      localView2 = null;
    }
  }

  private void setupViews(Bundle paramBundle)
  {
    this.iv_progress = ((ImageView)findViewById(2131165330));
    this.tv_message = ((LightTextView)findViewById(2131165331));
    this.iv_progress.getLayoutParams().width = App.OperationHeight(100);
    this.iv_progress.getLayoutParams().height = App.OperationHeight(100);
    UITools.setViewMargin(this.iv_progress, 0, App.Operation(20.0F), 0, 0);
    this.iv_progress.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    this.rotateAnimation = new RotateAnimation(-359.0F, 0.0F, 1, 0.5F, 1, 0.5F);
    this.rotateAnimation.setInterpolator(new LinearInterpolator());
    this.rotateAnimation.setDuration(700L);
    this.rotateAnimation.setRepeatCount(-1);
    Bitmap localBitmap = BitmapCache.getInstance(this.mContext).getBitmapFromCache("Loading_new.png");
    this.iv_progress.setBackgroundDrawable(new BitmapDrawable(this.mContext.getResources(), localBitmap));
    this.iv_progress.startAnimation(this.rotateAnimation);
    this.tv_message.setText("加载中...");
    this.tv_message.setTextSize(0, App.Operation(34.0F));
  }

  public void dismiss()
  {
    if (isShowing())
      super.dismiss();
    this.iv_progress.clearAnimation();
  }

  public AnimationDrawable getAnimationDrawable()
  {
    AnimationDrawable localAnimationDrawable = new AnimationDrawable();
    for (int i = 1; i <= 12; i++)
      localAnimationDrawable.addFrame(new BitmapDrawable(this.mContext.getResources(), BitmapService.getInstance(this.mContext.getApplicationContext()).getBitmap("Loading" + i + ".png")), 100);
    return localAnimationDrawable;
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (AppFuncCfg.FUNCTION_ENABLE_AIR_CONTROL)
    {
      this._airCommandListener = new IAirCommandListener.Stub()
      {
        public boolean onKeyEvent(KeyEvent paramAnonymousKeyEvent)
          throws RemoteException
        {
          Message localMessage = this.val$handler.obtainMessage(0);
          localMessage.obj = paramAnonymousKeyEvent;
          this.val$handler.sendMessage(localMessage);
          return true;
        }

        public boolean onMessage(String paramAnonymousString, Bundle paramAnonymousBundle)
          throws RemoteException
        {
          return false;
        }

        public boolean onTextInput(String paramAnonymousString)
          throws RemoteException
        {
          return false;
        }
      };
      AirControlHost.registerAirCommandListener(this._airCommandListener);
    }
    setContentView(2130903076);
    WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
    localLayoutParams.width = App.OperationHeight(200);
    localLayoutParams.height = App.OperationHeight(200);
    getWindow().setAttributes(localLayoutParams);
    setupViews(paramBundle);
  }

  protected void onStop()
  {
    if (this._airCommandListener != null)
      AirControlHost.unregisterAirCommandListener(this._airCommandListener);
    super.onStop();
  }

  public void show()
  {
    if (!isShowing())
      super.show();
    start();
  }

  public void start()
  {
    this.iv_progress.startAnimation(this.rotateAnimation);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.MyPorgressDialog
 * JD-Core Version:    0.6.2
 */