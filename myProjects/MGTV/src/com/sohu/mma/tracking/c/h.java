package com.sohu.mma.tracking.c;

import android.content.Context;
import android.content.res.AssetManager;
import com.sohu.mma.tracking.b.f;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class h
{
  private static com.sohu.mma.tracking.b.g a = null;

  public static com.sohu.mma.tracking.b.g a(Context paramContext)
  {
    try
    {
      String str = j.a(paramContext, "cn.com.mma.mobile.tracking.sdkconfig", "trackingConfig");
      if (str != null);
      try
      {
        if (str.length() > 0);
        InputStream localInputStream;
        for (localObject1 = new ByteArrayInputStream(str.getBytes()); ; localObject1 = localInputStream)
        {
          Object localObject2 = null;
          if (localObject1 != null)
          {
            com.sohu.mma.tracking.b.g localg = k.a((InputStream)localObject1);
            localObject2 = localg;
          }
          return localObject2;
          localInputStream = paramContext.getAssets().open("sdksohuconfig.xml");
        }
      }
      catch (Exception localException2)
      {
        while (true)
          Object localObject1 = null;
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    return null;
  }

  public static void a(Context paramContext, String paramString)
  {
    new Thread(new i(paramContext, paramString)).start();
  }

  public static com.sohu.mma.tracking.b.g b(Context paramContext)
  {
    if ((a == null) || (a.b == null))
    {
      a = a(paramContext);
      c(a);
    }
    return a;
  }

  public static com.sohu.mma.tracking.b.g b(Context paramContext, String paramString)
  {
    boolean bool = d.d(paramContext);
    com.sohu.mma.tracking.b.g localg = null;
    if (!bool);
    while (true)
    {
      return localg;
      String str = c.a().a(paramString);
      localg = null;
      if (str != null)
        try
        {
          localg = k.a(new ByteArrayInputStream(str.getBytes("UTF-8")));
          if ((localg != null) && (localg.b != null) && (localg.b.size() > 0))
          {
            j.a(paramContext, "cn.com.mma.mobile.tracking.sdkconfig", "trackingConfig", str);
            j.a(paramContext, "cn.com.mma.mobile.tracking.other", "updateTime", System.currentTimeMillis());
            g.a("mma_网络更新sdkconfig.xml成功");
            return localg;
          }
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          localUnsupportedEncodingException.printStackTrace();
        }
    }
    return localg;
  }

  private static void c(com.sohu.mma.tracking.b.g paramg)
  {
    g.a("mma_setSdk");
    if (paramg != null);
    try
    {
      if ((paramg.a.a != null) && (!"".equals(paramg.a.a)))
        com.sohu.mma.tracking.a.a.a = Integer.parseInt(paramg.a.a);
      if ((paramg.a.b != null) && (!"".equals(paramg.a.b)))
        com.sohu.mma.tracking.a.a.b = Integer.parseInt(paramg.a.b);
      if ((paramg.a.c != null) && (!"".equals(paramg.a.c)))
        com.sohu.mma.tracking.a.a.c = Integer.parseInt(paramg.a.c);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private static boolean c(Context paramContext)
  {
    long l1 = System.currentTimeMillis();
    long l2 = j.b(paramContext, "cn.com.mma.mobile.tracking.other", "updateTime");
    g.a("mma_config lastUpdateTimeStamp:" + l2);
    StringBuilder localStringBuilder1 = new StringBuilder("mma_config wifi:").append(a.a(paramContext)).append(" | ");
    boolean bool1;
    boolean bool2;
    label117: int i;
    if (l1 - l2 >= 86400000L)
    {
      bool1 = true;
      g.a(bool1);
      StringBuilder localStringBuilder2 = new StringBuilder("mma_config mobile:").append(a.b(paramContext)).append(" | ");
      if (l1 - l2 < 259200000L)
        break label215;
      bool2 = true;
      g.a(bool2);
      if ((!a.a(paramContext)) || (l1 - l2 < 86400000L))
        break label221;
      i = 1;
      label150: if ((!a.b(paramContext)) || (l1 - l2 < 259200000L))
        break label227;
    }
    label215: label221: label227: for (int j = 1; ; j = 0)
    {
      boolean bool3;
      if (i == 0)
      {
        bool3 = false;
        if (j == 0);
      }
      else
      {
        bool3 = true;
      }
      g.a("mma_config File need Update：" + bool3);
      return bool3;
      bool1 = false;
      break;
      bool2 = false;
      break label117;
      i = 0;
      break label150;
    }
  }

  private static com.sohu.mma.tracking.b.g d(Context paramContext, String paramString)
  {
    com.sohu.mma.tracking.b.g localg;
    if ((paramString == null) || (paramString.length() <= 0))
      localg = null;
    do
    {
      do
      {
        return localg;
        if (!c(paramContext))
          break;
        localg = b(paramContext, paramString);
      }
      while (localg != null);
      return a(paramContext);
      localg = a(paramContext);
    }
    while (localg != null);
    return b(paramContext, paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mma.tracking.c.h
 * JD-Core Version:    0.6.2
 */