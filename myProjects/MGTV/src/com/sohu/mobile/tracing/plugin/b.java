package com.sohu.mobile.tracing.plugin;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAction;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAdBoby;
import com.sohu.mobile.tracing.plugin.expose.Plugin_VastTag;
import com.sohu.upload.UploadPostData;
import java.util.ArrayList;

public class b
  implements a
{
  private static b a;
  private static Context b;
  private static com.sohu.mobile.tracing.plugin.a.b c = null;
  private static long d = 0L;
  private static Handler e = null;

  private void a(com.sohu.mobile.tracing.plugin.b.a parama)
  {
    try
    {
      com.sohu.applist.a.b localb = UploadPostData.getAppListAndGPS(b);
      com.sohu.mobile.tracing.plugin.d.b.a("addTracking2TaskOnline()");
      if (localb.c().length() > 0)
      {
        com.sohu.mobile.tracing.plugin.d.b.a("append url string --->" + localb.c());
        parama.a(localb);
      }
      new Thread(new f(this, parama)).start();
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public static a b()
  {
    if (a == null)
      a = new b();
    if (e == null)
      e = new c();
    return a;
  }

  public void a()
  {
    if (b == null)
      return;
    try
    {
      if (System.currentTimeMillis() - d > 20000L)
      {
        c.a();
        ArrayList localArrayList = c.b();
        com.sohu.mobile.tracing.plugin.d.b.b("需要上报失败的数据list(size)==" + localArrayList.size());
        if ((c()) && (localArrayList != null) && (localArrayList.size() > 0))
          new Thread(new e(this, localArrayList)).start();
        d = System.currentTimeMillis();
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    com.sohu.mobile.tracing.plugin.d.b.b("uploadFaile调用时间太短");
  }

  public void a(Context paramContext)
  {
    if (paramContext == null)
      return;
    b = paramContext;
    c = new com.sohu.mobile.tracing.plugin.a.b(b);
  }

  public void a(Plugin_ExposeAdBoby paramPlugin_ExposeAdBoby, String paramString, Plugin_VastTag paramPlugin_VastTag, Plugin_ExposeAction paramPlugin_ExposeAction)
  {
    while (true)
    {
      try
      {
        if (TextUtils.isEmpty(paramString))
        {
          com.sohu.mobile.tracing.plugin.d.b.a("task==null");
          return;
        }
        if (b == null)
        {
          com.sohu.mobile.tracing.plugin.d.b.b("mContext==null");
          continue;
        }
      }
      finally
      {
      }
      String str = String.valueOf(System.currentTimeMillis() / 1000L);
      if (c())
      {
        switch (d.a[paramPlugin_VastTag.ordinal()])
        {
        default:
          a(new com.sohu.mobile.tracing.plugin.b.a(paramPlugin_ExposeAdBoby, paramString, paramPlugin_VastTag, paramPlugin_ExposeAction, 0));
          break;
        case 1:
          com.sohu.mobile.tracing.plugin.d.b.b("<在线/" + paramPlugin_VastTag + ">调用Admaster曝光,Url=" + paramString);
          Plugin_ExposeAction localPlugin_ExposeAction1 = Plugin_ExposeAction.EXPOSE_SHOW;
          if (paramPlugin_ExposeAction == localPlugin_ExposeAction1);
          try
          {
            com.sohu.mma.tracking.a.b.a().b(paramString.trim());
            Plugin_ExposeAction localPlugin_ExposeAction2 = Plugin_ExposeAction.EXPOSE_CLICK;
            if (paramPlugin_ExposeAction != localPlugin_ExposeAction2)
              continue;
            try
            {
              com.sohu.mma.tracking.a.b.a().a(paramString.trim());
            }
            catch (Exception localException2)
            {
              localException2.printStackTrace();
            }
          }
          catch (Exception localException3)
          {
            while (true)
              localException3.printStackTrace();
          }
        case 2:
          try
          {
            if (e == null)
              continue;
            e.obtainMessage(10, paramString.trim()).sendToTarget();
          }
          catch (Exception localException1)
          {
            localException1.printStackTrace();
          }
          break;
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
          a(new com.sohu.mobile.tracing.plugin.b.a(paramPlugin_ExposeAdBoby, paramString + "&adrealtime=" + str, paramPlugin_VastTag, paramPlugin_ExposeAction, 0));
          break;
        }
      }
      else
      {
        com.sohu.mobile.tracing.plugin.d.b.a("保存曝光<" + paramPlugin_VastTag + ">至本地,Url=" + paramString);
        switch (d.a[paramPlugin_VastTag.ordinal()])
        {
        case 1:
        case 2:
        case 14:
          c.a(new com.sohu.mobile.tracing.plugin.b.a(paramPlugin_ExposeAdBoby, paramString, paramPlugin_VastTag, paramPlugin_ExposeAction, 0));
          break;
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
          c.a(new com.sohu.mobile.tracing.plugin.b.a(paramPlugin_ExposeAdBoby, paramString + "&adrealtime=" + str, paramPlugin_VastTag, paramPlugin_ExposeAction, 0));
        }
      }
    }
  }

  public void a(String paramString)
  {
    com.sohu.mobile.tracing.plugin.d.a.a = paramString;
  }

  public void a(boolean paramBoolean)
  {
    com.sohu.mobile.tracing.plugin.d.b.a = paramBoolean;
  }

  public boolean c()
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)b.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo != null)
      {
        boolean bool = localNetworkInfo.isConnected();
        if (bool)
          return true;
      }
      return false;
    }
    catch (Exception localException)
    {
    }
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mobile.tracing.plugin.b
 * JD-Core Version:    0.6.2
 */