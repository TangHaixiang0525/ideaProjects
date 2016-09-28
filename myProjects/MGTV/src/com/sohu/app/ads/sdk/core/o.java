package com.sohu.app.ads.sdk.core;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

class o extends Handler
{
  o(n paramn, Looper paramLooper)
  {
    super(paramLooper);
  }

  public void dispatchMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
    case 3:
    }
    do
    {
      do
        return;
      while (paramMessage.obj == null);
      Drawable localDrawable = (Drawable)paramMessage.obj;
      if (n.a(this.a) != null)
        n.a(this.a).setBackgroundDrawable(localDrawable);
    }
    while (n.b(this.a) == null);
    n.b(this.a).a();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.core.o
 * JD-Core Version:    0.6.2
 */