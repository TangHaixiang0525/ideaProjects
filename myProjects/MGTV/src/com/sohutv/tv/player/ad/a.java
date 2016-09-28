package com.sohutv.tv.player.ad;

import android.content.Context;
import com.sohutv.tv.player.util.e;

public class a
{
  public static void a(Context paramContext, boolean paramBoolean)
  {
    e.b(paramContext, "main_switcher", paramBoolean);
  }

  public static boolean a(Context paramContext)
  {
    return e.a(paramContext, "main_switcher", false);
  }

  public static void b(Context paramContext, boolean paramBoolean)
  {
    e.b(paramContext, "vip_switcher", paramBoolean);
  }

  public static boolean b(Context paramContext)
  {
    return e.a(paramContext, "oad_switcher", false);
  }

  public static void c(Context paramContext, boolean paramBoolean)
  {
    e.b(paramContext, "oad_switcher", paramBoolean);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.ad.a
 * JD-Core Version:    0.6.2
 */