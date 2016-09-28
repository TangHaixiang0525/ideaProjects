package com.sohu.app.ads.sdk.model;

import com.sohu.app.ads.sdk.f.d;
import com.sohu.app.ads.sdk.model.emu.DownloadEmue;

public class e
{
  private String a;
  private DownloadEmue b = DownloadEmue.UNSTART;
  private int c = -1;

  public e()
  {
  }

  public e(String paramString, DownloadEmue paramDownloadEmue, int paramInt)
  {
    this.a = paramString;
    this.b = paramDownloadEmue;
    this.c = paramInt;
  }

  public String a()
  {
    return this.a;
  }

  public void a(int paramInt)
  {
    this.c = paramInt;
  }

  public void a(DownloadEmue paramDownloadEmue)
  {
    this.b = paramDownloadEmue;
  }

  public void a(String paramString)
  {
    this.a = paramString;
  }

  public DownloadEmue b()
  {
    return this.b;
  }

  public int c()
  {
    return this.c;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("UrlEntity [url=");
    localStringBuilder.append(this.a + "[" + d.b(this.a) + "]");
    localStringBuilder.append(", downloadEmue=");
    localStringBuilder.append(this.b);
    localStringBuilder.append(", length=");
    localStringBuilder.append(this.c);
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.model.e
 * JD-Core Version:    0.6.2
 */