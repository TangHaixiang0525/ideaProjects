package com.sohu.app.ads.sdk.d;

import android.os.Handler;
import android.os.Message;
import com.sohu.app.ads.sdk.f.b;

class d
  implements Runnable
{
  d(a parama, String paramString1, String paramString2)
  {
  }

  public void run()
  {
    try
    {
      com.sohu.app.ads.sdk.model.a locala = b.a().b(this.a, this.b);
      com.sohu.app.ads.sdk.c.a.a("暂停广告数据请求完成");
      if (locala != null)
      {
        String str = locala.e();
        if (!"".equals(str))
        {
          com.sohu.app.ads.sdk.c.a.a("开始下载暂停物料");
          com.sohu.app.ads.sdk.f.a.a().a(str, com.sohu.app.ads.sdk.f.d.h(), com.sohu.app.ads.sdk.f.d.c(str));
          com.sohu.app.ads.sdk.c.a.a("暂停物料下载完成");
        }
        a.b(this.c).obtainMessage(5, locala).sendToTarget();
      }
      return;
    }
    catch (Exception localException)
    {
      a.b(this.c).obtainMessage(5, null).sendToTarget();
      localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.d.d
 * JD-Core Version:    0.6.2
 */