package com.sohu.app.ads.sdk.e;

import android.content.Context;
import com.sohu.app.ads.sdk.model.AdsResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class c
  implements Runnable
{
  private final String a = "SOHUSDK";
  private com.sohu.app.ads.sdk.a.c b = null;
  private com.sohu.app.ads.sdk.a.d c = null;
  private File d = null;
  private File e = null;
  private String f;
  private String g;
  private String h;

  public c(Context paramContext)
  {
    this.d = paramContext.getApplicationContext().getExternalFilesDir("LOCALCACHE");
    this.e = paramContext.getApplicationContext().getExternalFilesDir("PAUSECACHE");
    this.b = new com.sohu.app.ads.sdk.a.c(paramContext);
    this.c = new com.sohu.app.ads.sdk.a.d(paramContext);
    try
    {
      com.sohu.app.ads.sdk.f.d.a(this.d, 30);
      Iterator localIterator = this.c.a().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        a(this.c, str);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void a(com.sohu.app.ads.sdk.a.c paramc, String paramString)
  {
    Iterator localIterator = paramc.b(paramString).iterator();
    while (localIterator.hasNext())
    {
      String str = ((AdsResponse)localIterator.next()).getMediaFile();
      if (paramc.a(str, paramString) > 0)
      {
        com.sohu.app.ads.sdk.c.a.b("SOHUSDK", "can't delete OAD file , beause it use by other :" + str);
      }
      else
      {
        com.sohu.app.ads.sdk.c.a.b("SOHUSDK", "delte local file :" + str);
        new File(str).delete();
      }
    }
    paramc.a(paramString);
  }

  private void a(com.sohu.app.ads.sdk.a.d paramd, String paramString)
  {
    com.sohu.app.ads.sdk.model.a locala = paramd.b(paramString);
    if ((locala != null) && (com.sohu.app.ads.sdk.f.d.a(locala.e())))
    {
      if (paramd.a(locala.e(), paramString) <= 0)
        break label65;
      com.sohu.app.ads.sdk.c.a.b("SOHUSDK", "can't delete PAUSE file , beause it use by other :" + locala.e());
    }
    while (true)
    {
      paramd.a(paramString);
      return;
      label65: com.sohu.app.ads.sdk.c.a.b("SOHUSDK", "delte local file :" + locala.e());
      new File(locala.e()).delete();
    }
  }

  private void a(String paramString1, String paramString2)
  {
    if (this.e != null)
    {
      com.sohu.app.ads.sdk.model.a locala = com.sohu.app.ads.sdk.f.b.a().b(paramString2, null);
      if (locala == null)
        break label143;
      com.sohu.app.ads.sdk.c.a.a("SOHUSDK", "get pause ad vast data complete");
      this.c.a(paramString1, locala);
      String str1 = locala.e();
      if (com.sohu.app.ads.sdk.f.d.a(str1))
      {
        com.sohu.app.ads.sdk.c.a.a("SOHUSDK", "pause ad png Url=" + str1);
        String str2 = com.sohu.app.ads.sdk.f.d.c(str1);
        if (com.sohu.app.ads.sdk.f.a.a().a(str1, this.e, str2))
        {
          String str3 = this.e.getPath() + "/" + str2;
          this.c.b(str1, str3);
        }
      }
    }
    return;
    label143: com.sohu.app.ads.sdk.c.a.a("SOHUSDK", "get pause ad vast data == nulll");
  }

  private void b(String paramString1, String paramString2)
  {
    if (this.d != null)
    {
      com.sohu.mobile.a.a.b.a().a("offlineOadReqTimes");
      ArrayList localArrayList = com.sohu.app.ads.sdk.f.b.a().a(paramString2, null);
      Iterator localIterator;
      if ((localArrayList != null) && (localArrayList.size() > 0))
      {
        com.sohu.mobile.a.a.b.a().a("offlineOadReqSucessTimes");
        com.sohu.app.ads.sdk.c.a.a("SOHUSDK", "get OAD ad vast data != nulll");
        localIterator = localArrayList.iterator();
      }
      while (localIterator.hasNext())
      {
        AdsResponse localAdsResponse = (AdsResponse)localIterator.next();
        if (localAdsResponse != null)
        {
          com.sohu.app.ads.sdk.c.a.a("SOHUSDK", "save adsResponse data");
          this.b.a(paramString1, localAdsResponse);
        }
        String str1 = localAdsResponse.getMediaFile();
        if (com.sohu.app.ads.sdk.f.d.a(str1))
        {
          String str2 = com.sohu.app.ads.sdk.f.d.b(str1);
          if (com.sohu.app.ads.sdk.f.a.a().a(str1, this.d, str2))
          {
            String str3 = this.d.getPath() + "/" + str2;
            com.sohu.app.ads.sdk.c.a.a("SOHUSDK", "download [" + str2 + "] complete");
            this.b.b(str3, localAdsResponse.getMediaFile());
          }
          else
          {
            com.sohu.mobile.a.a.b.a().a("offlineOadDownloadErrorTimes");
            continue;
            com.sohu.mobile.a.a.b.a().a("offlineOadReqFailedTimes");
            com.sohu.app.ads.sdk.c.a.a("SOHUSDK", "get OAD ad vast data == nulll");
          }
        }
      }
    }
  }

  public void a(String paramString)
  {
    this.f = paramString;
  }

  public void b(String paramString)
  {
    this.g = paramString;
  }

  public void c(String paramString)
  {
    this.h = paramString;
  }

  public void d(String paramString)
  {
    if (!com.sohu.app.ads.sdk.f.d.a(paramString))
      return;
    a(this.b, paramString);
    a(this.c, paramString);
  }

  public void run()
  {
    try
    {
      try
      {
        com.sohu.app.ads.sdk.c.a.b("SOHUSDK", "Start Thread Name=" + Thread.currentThread().getName());
        com.sohu.app.ads.sdk.c.a.a("SOHUSDK", "vid=" + this.h);
        if (com.sohu.app.ads.sdk.f.d.a(this.f))
        {
          com.sohu.app.ads.sdk.c.a.a("SOHUSDK", "prepare request oad");
          b(this.h, this.f);
          com.sohu.app.ads.sdk.c.a.a("SOHUSDK", "requestBannerAd() OK");
        }
        if (com.sohu.app.ads.sdk.f.d.a(this.g))
        {
          com.sohu.app.ads.sdk.c.a.a("SOHUSDK", "prepare request pad");
          a(this.h, this.g);
          com.sohu.app.ads.sdk.c.a.a("SOHUSDK", "requestPauseAd() OK");
        }
        com.sohu.app.ads.sdk.c.a.b("SOHUSDK", "End Thread Name=" + Thread.currentThread().getName());
        return;
      }
      finally
      {
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.e.c
 * JD-Core Version:    0.6.2
 */