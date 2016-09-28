package com.starcor.hunan.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;
import com.starcor.config.AppFuncCfg;
import com.starcor.remote_key.AirControlHost;
import com.starcor.remote_key.IAirCommandListener;
import com.starcor.remote_key.IAirCommandListener.Stub;

public class BaseDialog extends Dialog
{
  private IAirCommandListener _airCommandListener;

  public BaseDialog(Context paramContext)
  {
    super(paramContext);
  }

  public BaseDialog(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
  }

  public BaseDialog(Context paramContext, boolean paramBoolean, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    super(paramContext, paramBoolean, paramOnCancelListener);
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

  public void dismiss()
  {
    try
    {
      super.dismiss();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
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
  }

  protected void onStop()
  {
    if (this._airCommandListener != null)
      AirControlHost.unregisterAirCommandListener(this._airCommandListener);
    super.onStop();
  }

  public void show()
  {
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
 * Qualified Name:     com.starcor.hunan.widget.BaseDialog
 * JD-Core Version:    0.6.2
 */