package com.sohutv.tv.player.util.a;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.sohutv.tv.player.entity.FeeKeyData;
import com.sohutv.tv.player.entity.PlayInfo;
import com.sohutv.tv.player.entity.ResponsePlayInfo;
import java.util.Locale;

public class a
{
  public static int a = 2;
  public static int b = 1;
  public static int c = 0;
  public static int d = 0;
  public static ResponsePlayInfo e = null;
  public static PlayInfo f = null;
  public static FeeKeyData g = null;
  public static Boolean h = Boolean.valueOf(true);
  public static Boolean i = Boolean.valueOf(true);
  public static String j = "";
  public static boolean k = false;

  static
  {
    h = b.b;
    Log.i("Sohuplayer", "showBeforeVideoAD = " + h);
    i = b.c;
    Log.i("Sohuplayer", "showPauseAD = " + i);
    j = Build.MODEL.trim().toLowerCase(Locale.CHINA);
    Log.i("Sohuplayer", "dn=" + j);
  }

  public static String[] a(String paramString, boolean paramBoolean)
  {
    String str1 = "";
    String str2;
    if (!paramBoolean)
      if ((paramString.equals("863")) || (paramString.equals("866")) || (paramString.equals("18340450")))
      {
        str2 = b.d;
        str1 = b.h;
      }
    while (true)
    {
      if (TextUtils.isEmpty(str2))
        str2 = "http://ott.hd.sohu.com";
      if (TextUtils.isEmpty(str1))
        str1 = "http://hot.vrs.sohu.com";
      return new String[] { str2, str1 };
      if (paramString.equals("800"))
      {
        str2 = b.e;
        str1 = b.i;
      }
      else
      {
        str2 = b.f;
        str1 = b.j;
        continue;
        str2 = b.g;
      }
    }
  }

  public static String b(String paramString, boolean paramBoolean)
  {
    if (paramBoolean)
      return "http://60.28.168.195/ott";
    if ((paramString.equals("863")) || (paramString.equals("866")) || (paramString.equals("18340450")))
      return "http://m.aty.ptsh.gitv.tv/ott";
    if (paramString.equals("800"))
      return "http://m.aty.sh.t001.ottcn.com/ott";
    return "http://m.aty.sohu.com/ott";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.util.a.a
 * JD-Core Version:    0.6.2
 */