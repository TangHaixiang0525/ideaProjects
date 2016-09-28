package com.sohu.mma.tracking.a;

import android.content.Context;
import android.content.SharedPreferences;
import com.sohu.mma.tracking.c.j;
import java.util.Map;

public class f
{
  private static g a;
  private static g b;
  private static Context c;
  private static f d = new f();

  public static f a(Context paramContext)
  {
    c = paramContext;
    return d;
  }

  public void a()
  {
    com.sohu.mma.tracking.c.g.a("thread_sendNormalList");
    if ((a != null) && ((a.getState() == Thread.State.NEW) || (a.isAlive())))
    {
      a.interrupt();
      a = null;
    }
    SharedPreferences localSharedPreferences = j.a(c, "cn.com.mma.mobile.tracking.normal");
    if ((localSharedPreferences == null) || (localSharedPreferences.getAll().isEmpty()))
      return;
    a = new g("cn.com.mma.mobile.tracking.normal", c, true);
    a.start();
  }

  public void b()
  {
    if ((b != null) && ((b.getState() == Thread.State.NEW) || (b.isAlive())))
    {
      b.interrupt();
      b = null;
    }
    SharedPreferences localSharedPreferences = j.a(c, "cn.com.mma.mobile.tracking.falied");
    if ((localSharedPreferences == null) || (localSharedPreferences.getAll().isEmpty()))
      return;
    b = new g("cn.com.mma.mobile.tracking.falied", c, false);
    b.start();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mma.tracking.a.f
 * JD-Core Version:    0.6.2
 */