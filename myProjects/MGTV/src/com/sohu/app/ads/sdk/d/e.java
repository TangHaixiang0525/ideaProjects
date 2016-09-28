package com.sohu.app.ads.sdk.d;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.sohu.app.ads.sdk.f.b;
import com.sohu.app.ads.sdk.f.d;
import java.io.File;

class e
  implements Runnable
{
  e(a parama, String paramString1, String paramString2)
  {
  }

  public void run()
  {
    try
    {
      com.sohu.app.ads.sdk.model.a locala = b.a().b(this.a, this.b);
      com.sohu.app.ads.sdk.c.a.a("系统开机广告数据请求完成");
      if (locala != null)
      {
        String str = locala.e();
        if (d.a(str))
        {
          com.sohu.app.ads.sdk.c.a.a("开始下载系统开机物料");
          File localFile = Environment.getExternalStoragePublicDirectory("systemimage");
          if (!localFile.exists())
            localFile.mkdir();
          com.sohu.app.ads.sdk.c.a.a("dir=" + localFile.getAbsolutePath());
          if (com.sohu.app.ads.sdk.f.a.a().a(str, localFile, d.c(str)))
          {
            a.b(this.c).obtainMessage(7, locala).sendToTarget();
            return;
          }
        }
        a.b(this.c).obtainMessage(7, locala).sendToTarget();
        return;
      }
    }
    catch (Exception localException)
    {
      a.b(this.c).obtainMessage(7, null).sendToTarget();
      localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.d.e
 * JD-Core Version:    0.6.2
 */