package com.sohu.app.ads.sdk.d;

import android.os.Handler;
import com.sohu.app.ads.sdk.f.b;
import java.util.ArrayList;

class c
  implements Runnable
{
  c(a parama, String paramString1, String paramString2)
  {
  }

  public void run()
  {
    try
    {
      ArrayList localArrayList = b.a().a(this.a, this.b);
      a.b(this.c).obtainMessage(4, localArrayList);
      return;
    }
    catch (Exception localException)
    {
      a.b(this.c).obtainMessage(4, null);
      localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.d.c
 * JD-Core Version:    0.6.2
 */