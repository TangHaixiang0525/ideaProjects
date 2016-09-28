package com.sohu.app.ads.sdk.e;

import android.content.Context;
import android.text.TextUtils;
import com.sohu.app.ads.sdk.a.b;
import com.sohu.app.ads.sdk.model.emu.DownloadEmue;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class a
  implements e
{
  private static a a = null;
  private static d b = null;
  private static b c = null;
  private static File d = null;

  public static a a()
  {
    if (a == null)
      a = new a();
    return a;
  }

  public void a(Context paramContext)
  {
    while (true)
    {
      try
      {
        a(paramContext, d);
        com.sohu.app.ads.sdk.c.a.d("dt线程的状态:" + Thread.State.valueOf(b.getState().toString()));
        if ((b.b()) && (b.isAlive()))
        {
          com.sohu.app.ads.sdk.c.a.e("线程正忙~~");
          return;
        }
        if (b.getState() == Thread.State.TERMINATED)
        {
          b = new d(d);
          b.a(this);
        }
        ArrayList localArrayList = c.a(DownloadEmue.UNSTART);
        com.sohu.app.ads.sdk.c.a.d("准备开启线程,任务大小==" + localArrayList.size());
        if (localArrayList.size() > 0)
        {
          b.a(localArrayList);
          b.a();
          continue;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        continue;
      }
      finally
      {
      }
      com.sohu.app.ads.sdk.c.a.d("线程已退出,准备清理无效文件");
      b(paramContext);
    }
  }

  public void a(Context paramContext, File paramFile)
  {
    if (d == null)
      d = paramFile;
    if (c == null)
      c = new b(paramContext.getApplicationContext());
    if (b == null)
    {
      b = new d(d);
      b.a(this);
    }
  }

  public void a(Context paramContext, String paramString)
  {
    while (true)
    {
      try
      {
        if ((TextUtils.isEmpty(paramString)) || (!paramString.startsWith("http://")))
        {
          com.sohu.app.ads.sdk.c.a.e("任务为空,不添加到数据库中");
          return;
        }
        try
        {
          a(paramContext, d);
          if (!c.c(paramString))
            break label108;
          if (!new File(d, com.sohu.app.ads.sdk.f.d.b(paramString)).exists())
            break label87;
          com.sohu.app.ads.sdk.c.a.d("文件已存在本地，任务添加失败");
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
        continue;
      }
      finally
      {
      }
      label87: c.b(new com.sohu.app.ads.sdk.model.e(paramString, DownloadEmue.UNSTART, -1));
      continue;
      label108: com.sohu.app.ads.sdk.model.e locale = new com.sohu.app.ads.sdk.model.e();
      locale.a(DownloadEmue.UNSTART);
      locale.a(-1);
      locale.a(paramString);
      c.a(locale);
      com.sohu.app.ads.sdk.c.a.d("成功添加任务:" + locale.toString());
    }
  }

  public void a(DownloadEmue paramDownloadEmue, String paramString, int paramInt)
  {
    switch (b.a[paramDownloadEmue.ordinal()])
    {
    default:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      return;
      c.b(new com.sohu.app.ads.sdk.model.e(paramString, DownloadEmue.DOWNLOADING, -1));
      return;
      c.b(new com.sohu.app.ads.sdk.model.e(paramString, DownloadEmue.FAILED, -1));
      c.a(paramString);
      File localFile = new File(d, com.sohu.app.ads.sdk.f.d.b(paramString));
      if (localFile.exists())
      {
        localFile.delete();
        com.sohu.app.ads.sdk.c.a.e("删除下载失败的临时文件~~");
        return;
        c.b(new com.sohu.app.ads.sdk.model.e(paramString, DownloadEmue.SUCESS, paramInt));
        Iterator localIterator = c.a().iterator();
        while (localIterator.hasNext())
        {
          com.sohu.app.ads.sdk.model.e locale = (com.sohu.app.ads.sdk.model.e)localIterator.next();
          com.sohu.app.ads.sdk.c.a.e("更新后的数据:" + locale.toString());
        }
      }
    }
  }

  public void b(Context paramContext)
  {
    try
    {
      com.sohu.app.ads.sdk.c.a.d("准备清除所有无效文件");
      a(paramContext, d);
      ArrayList localArrayList = c.a(DownloadEmue.FAILED);
      localArrayList.addAll(c.a(DownloadEmue.DOWNLOADING));
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        com.sohu.app.ads.sdk.model.e locale = (com.sohu.app.ads.sdk.model.e)localIterator.next();
        File localFile = new File(paramContext.getExternalFilesDir("OADCACHE"), com.sohu.app.ads.sdk.f.d.b(locale.a()));
        if (localFile.exists())
          localFile.delete();
        c.a(locale.a());
        com.sohu.app.ads.sdk.c.a.d("删除无效文件对应的数据库");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public boolean b(Context paramContext, String paramString)
  {
    if ((TextUtils.isEmpty(paramString)) || (paramContext == null));
    while (true)
    {
      return false;
      try
      {
        a(paramContext, d);
        File localFile = new File(paramContext.getExternalFilesDir("OADCACHE"), com.sohu.app.ads.sdk.f.d.b(paramString));
        if (localFile.exists())
        {
          long l1 = c.b(paramString).c();
          long l2 = localFile.length();
          if (l1 == l2)
            return true;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.e.a
 * JD-Core Version:    0.6.2
 */