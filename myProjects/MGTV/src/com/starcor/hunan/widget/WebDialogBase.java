package com.starcor.hunan.widget;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.os.Handler;

public abstract class WebDialogBase extends BaseDialog
{
  private static Handler hdl;

  public WebDialogBase(Context paramContext)
  {
    super(paramContext);
  }

  public WebDialogBase(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
  }

  public WebDialogBase(Context paramContext, boolean paramBoolean, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    super(paramContext, paramBoolean, paramOnCancelListener);
  }

  public static Handler getHandler()
  {
    return hdl;
  }

  public static void setHandler(Handler paramHandler)
  {
    hdl = paramHandler;
  }

  public abstract void loadUrl(String paramString);

  public abstract void onAirControlTextInput(String paramString);

  public abstract void setOnQuitWebListener(onQuitWebListener paramonQuitWebListener);

  public abstract void setURL(String paramString);

  public static abstract interface onQuitWebListener
  {
    public abstract void QuitWeb();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.WebDialogBase
 * JD-Core Version:    0.6.2
 */