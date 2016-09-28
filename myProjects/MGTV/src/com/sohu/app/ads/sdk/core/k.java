package com.sohu.app.ads.sdk.core;

import android.os.Environment;
import android.util.Log;
import com.sohu.app.ads.sdk.d.f;
import com.sohu.app.ads.sdk.iterface.ImageDownloadCallback;
import com.sohu.app.ads.sdk.model.a;
import java.io.File;

class k
  implements f
{
  k(j paramj, ImageDownloadCallback paramImageDownloadCallback, com.sohu.app.ads.sdk.a.d paramd)
  {
  }

  public void a(int paramInt, Object paramObject)
  {
    if ((paramInt == 6) && (paramObject != null))
    {
      a locala = (a)paramObject;
      if ((locala != null) && (!com.sohu.app.ads.sdk.f.d.a(locala.e())))
      {
        j.a(this.c, locala);
        this.a.downloadStatus(false, null);
        return;
      }
      this.b.a("9999");
      this.b.a("9999", locala);
      File localFile = Environment.getExternalStoragePublicDirectory("systemimage");
      Log.i("SHIXIN", localFile.getAbsolutePath());
      String str = com.sohu.app.ads.sdk.f.d.c(locala.e());
      this.a.downloadStatus(true, new File(localFile, str).getAbsolutePath());
      return;
    }
    this.a.downloadStatus(false, null);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.core.k
 * JD-Core Version:    0.6.2
 */