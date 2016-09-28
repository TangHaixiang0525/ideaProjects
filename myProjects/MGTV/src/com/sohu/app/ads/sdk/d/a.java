package com.sohu.app.ads.sdk.d;

import android.os.Handler;

public class a
{
  private f a;
  private Handler b = new b(this);

  public void a(String paramString1, String paramString2, f paramf, int paramInt)
  {
    this.a = paramf;
    switch (paramInt)
    {
    case 3:
    case 4:
    case 5:
    default:
      return;
    case 1:
      new Thread(new c(this, paramString1, paramString2)).start();
      return;
    case 2:
      com.sohu.app.ads.sdk.c.a.a("开始请求暂停广告");
      new Thread(new d(this, paramString1, paramString2)).start();
      return;
    case 6:
    }
    com.sohu.app.ads.sdk.c.a.a("开始请求系统开机广告");
    new Thread(new e(this, paramString1, paramString2)).start();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.d.a
 * JD-Core Version:    0.6.2
 */