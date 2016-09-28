package com.sohu.app.ads.sdk.core;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import com.sohu.app.ads.sdk.f.d;
import com.sohu.app.ads.sdk.model.a;
import java.io.File;

class p
  implements Runnable
{
  p(n paramn, a parama)
  {
  }

  public void run()
  {
    String str = d.c(this.a.e());
    File localFile = new File(d.h(), str);
    n.a(this.b, BitmapFactory.decodeFile(localFile.getAbsolutePath()));
    BitmapDrawable localBitmapDrawable = new BitmapDrawable(n.c(this.b));
    n.d(this.b).obtainMessage(3, localBitmapDrawable).sendToTarget();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.core.p
 * JD-Core Version:    0.6.2
 */