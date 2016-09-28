package com.sohu.mobile.tracing.plugin;

import android.os.Handler;
import android.os.Message;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAction;
import com.sohu.mobile.tracing.plugin.expose.Plugin_VastTag;
import java.util.ArrayList;

class e
  implements Runnable
{
  private ArrayList<com.sohu.mobile.tracing.plugin.b.a> b;

  e(ArrayList<com.sohu.mobile.tracing.plugin.b.a> paramArrayList)
  {
    Object localObject;
    this.b = localObject;
  }

  public void run()
  {
    com.sohu.mobile.tracing.plugin.d.b.b("开始离线上报...");
    for (int i = 0; ; i++)
    {
      if (i < this.b.size())
        while (true)
        {
          int j;
          String str;
          Plugin_ExposeAction localPlugin_ExposeAction;
          try
          {
            j = ((com.sohu.mobile.tracing.plugin.b.a)this.b.get(i)).a();
            str = ((com.sohu.mobile.tracing.plugin.b.a)this.b.get(i)).c();
            Plugin_VastTag localPlugin_VastTag = ((com.sohu.mobile.tracing.plugin.b.a)this.b.get(i)).e();
            localPlugin_ExposeAction = ((com.sohu.mobile.tracing.plugin.b.a)this.b.get(i)).f();
            switch (d.a[localPlugin_VastTag.ordinal()])
            {
            default:
              if (!com.sohu.mobile.tracing.plugin.c.a.a().a((com.sohu.mobile.tracing.plugin.b.a)this.b.get(i)))
                break;
              com.sohu.mobile.tracing.plugin.d.b.a("<离线>成功曝光Url=" + str);
              b.f().b(j);
              Thread.sleep(500L);
              break;
            case 1:
              com.sohu.mobile.tracing.plugin.d.b.a("<离线>Admaster曝光Url=" + str);
              try
              {
                if (localPlugin_ExposeAction != Plugin_ExposeAction.EXPOSE_SHOW)
                  break label241;
                com.sohu.mma.tracking.a.b.a().b(str.trim());
                b.f().a(j);
              }
              catch (Exception localException3)
              {
                localException3.printStackTrace();
              }
              continue;
            case 2:
            }
          }
          catch (Exception localException1)
          {
            localException1.printStackTrace();
          }
          label241: if (localPlugin_ExposeAction == Plugin_ExposeAction.EXPOSE_CLICK)
          {
            com.sohu.mma.tracking.a.b.a().a(str.trim());
            continue;
            try
            {
              com.sohu.mobile.tracing.plugin.d.b.a("<离线>秒针上报Url=" + str);
              if (b.e() == null)
                continue;
              b.e().obtainMessage(10, str.trim()).sendToTarget();
              b.f().a(j);
            }
            catch (Exception localException2)
            {
              localException2.printStackTrace();
            }
            continue;
            com.sohu.mobile.tracing.plugin.d.b.b("<离线>曝光失败Url=" + str);
          }
        }
      com.sohu.mobile.tracing.plugin.d.b.b("结束离线上报...");
      return;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mobile.tracing.plugin.e
 * JD-Core Version:    0.6.2
 */