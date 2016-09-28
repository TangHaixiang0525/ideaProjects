package com.sohu.app.ads.sdk.core;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.sohu.mobile.tracing.plugin.b;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAction;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAdBoby;
import com.sohu.mobile.tracing.plugin.expose.Plugin_VastTag;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class n extends RelativeLayout
  implements View.OnTouchListener
{
  private Bitmap a = null;
  private ImageView b = null;
  private ImageButton c = null;
  private com.sohu.app.ads.sdk.model.a d = null;
  private q e = null;
  private Handler f = new o(this, Looper.getMainLooper());

  public n(Context paramContext, AttributeSet paramAttributeSet, View.OnClickListener paramOnClickListener)
  {
    super(paramContext, paramAttributeSet);
    RelativeLayout localRelativeLayout = new RelativeLayout(paramContext);
    localRelativeLayout.setGravity(17);
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(-1, -1);
    localLayoutParams1.addRule(13);
    localRelativeLayout.setLayoutParams(localLayoutParams1);
    this.b = new ImageView(paramContext);
    this.b.setVerticalScrollBarEnabled(false);
    this.b.setOnTouchListener(this);
    this.b.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
    localRelativeLayout.addView(this.b);
    this.c = new ImageButton(paramContext);
    this.c.setVisibility(8);
    this.c.setId(1001);
    this.c.setOnClickListener(paramOnClickListener);
    this.c.setBackgroundDrawable(com.sohu.app.ads.sdk.a.a().a("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"));
    RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams2.addRule(10);
    localLayoutParams2.addRule(11);
    this.c.setLayoutParams(localLayoutParams2);
    localRelativeLayout.addView(this.c);
    addView(localRelativeLayout);
  }

  public void a()
  {
    com.sohu.app.ads.sdk.c.a.a("Pause View destory()");
    if (this.b != null)
    {
      this.b.setBackgroundDrawable(null);
      this.b = null;
    }
    if ((this.a != null) && (!this.a.isRecycled()))
    {
      this.a.recycle();
      this.a = null;
    }
    this.e = null;
    this.d = null;
    this.c = null;
  }

  public void a(com.sohu.app.ads.sdk.model.a parama, q paramq)
  {
    this.e = paramq;
    this.d = parama;
    new Thread(new p(this, parama)).start();
  }

  public void b(com.sohu.app.ads.sdk.model.a parama, q paramq)
  {
    this.e = paramq;
    this.d = parama;
    if ((parama != null) && (com.sohu.app.ads.sdk.f.d.a(parama.e())))
    {
      String str = parama.e();
      if (new File(str).exists())
      {
        this.a = Bitmap.createBitmap(BitmapFactory.decodeFile(str));
        BitmapDrawable localBitmapDrawable = new BitmapDrawable(this.a);
        this.f.obtainMessage(3, localBitmapDrawable).sendToTarget();
      }
    }
    else
    {
      return;
    }
    this.f.obtainMessage(3, null).sendToTarget();
  }

  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    if (this.d == null);
    do
    {
      return false;
      try
      {
        ArrayList localArrayList = this.d.c();
        if ((localArrayList != null) && (localArrayList.size() > 0))
        {
          Iterator localIterator = localArrayList.iterator();
          while (localIterator.hasNext())
          {
            com.sohu.app.ads.sdk.model.d locald = (com.sohu.app.ads.sdk.model.d)localIterator.next();
            if (locald != null)
            {
              String str1 = locald.a();
              String str2 = locald.b();
              if ("miaozhen".equalsIgnoreCase(str1))
              {
                com.sohu.app.ads.sdk.c.a.c("pauseview秒针展示曝光");
                b.b().a(Plugin_ExposeAdBoby.PAD, str2, Plugin_VastTag.MIAOZHEN, Plugin_ExposeAction.EXPOSE_CLICK);
              }
              if ("admaster".equalsIgnoreCase(str1))
                b.b().a(Plugin_ExposeAdBoby.PAD, str2, Plugin_VastTag.ADMASTER, Plugin_ExposeAction.EXPOSE_CLICK);
            }
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return false;
      }
    }
    while ((!com.sohu.app.ads.sdk.f.d.a()) || (!com.sohu.app.ads.sdk.f.d.a(this.d.d())));
    Intent localIntent = new Intent(getContext(), PadDetailsActivity.class);
    localIntent.addFlags(268435456);
    localIntent.putExtra("url", this.d.d());
    getContext().startActivity(localIntent);
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.core.n
 * JD-Core Version:    0.6.2
 */