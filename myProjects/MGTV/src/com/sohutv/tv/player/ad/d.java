package com.sohutv.tv.player.ad;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import com.sohu.app.ads.sdk.res.AdType;
import com.sohu.logger.util.NetUtils;
import com.sohutv.tv.player.util.a.a;
import com.sohutv.tv.player.util.a.c;
import java.util.HashMap;

public class d
{
  private static boolean a = c.b;
  private static String b = "";
  private static String c = "http://60.28.168.195/m";

  private static String a(Context paramContext)
  {
    switch (NetUtils.getNetworkType(paramContext))
    {
    case -1:
    case 0:
    case 4:
    default:
      return "unknow";
    case 1:
      return "wifi";
    case 3:
      return "ethernet";
    case -2:
      return "2g";
    case 2:
      return "3g";
    case 5:
    }
    return "mobile";
  }

  public static HashMap<String, String> a(Context paramContext, AdType paramAdType, boolean paramBoolean, a parama)
  {
    b = a.b(c.l, a);
    HashMap localHashMap = new HashMap();
    localHashMap.put("url", b);
    localHashMap.put("adoriginal", "sohu");
    switch (1.a[paramAdType.ordinal()])
    {
    default:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      if (paramBoolean)
        localHashMap.put("offline", "1");
      a(localHashMap, "c", "tv");
      a(localHashMap, "plat", "ott1");
      a(localHashMap, "sver", c.i);
      a(localHashMap, "partner", c.l);
      a(localHashMap, "sysver", Integer.toString(Build.VERSION.SDK_INT));
      a(localHashMap, "pn", Build.MODEL);
      a(localHashMap, "ag", parama.a());
      a(localHashMap, "st", parama.b());
      a(localHashMap, "vu", parama.c());
      a(localHashMap, "source", parama.d());
      a(localHashMap, "wt", a(paramContext));
      a(localHashMap, "vc", parama.e());
      a(localHashMap, "al", parama.f());
      a(localHashMap, "du", parama.g());
      a(localHashMap, "ar", parama.h());
      a(localHashMap, "vid", parama.i());
      a(localHashMap, "lid", parama.j());
      a(localHashMap, "tuv", c.a(paramContext));
      a(localHashMap, "oprod", c.k);
      a(localHashMap, "oplat", c.h);
      return localHashMap;
      localHashMap.put("pt", "oad");
      continue;
      localHashMap.put("pt", "pad");
      continue;
      localHashMap.put("pt", "open");
      localHashMap.put("poscode", "op_ott_1");
    }
  }

  private static void a(HashMap<String, String> paramHashMap, String paramString1, String paramString2)
  {
    if (paramString2 == null)
      paramString2 = "";
    paramHashMap.put(paramString1, paramString2);
  }

  public static class a
  {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;

    public a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10)
    {
      this.a = paramString1;
      this.b = paramString2;
      this.c = paramString3;
      this.d = paramString4;
      this.e = paramString5;
      this.f = paramString6;
      this.g = paramString7;
      this.h = paramString8;
      this.i = paramString9;
      this.j = paramString10;
    }

    public String a()
    {
      return this.a;
    }

    public String b()
    {
      return this.b;
    }

    public String c()
    {
      return this.c;
    }

    public String d()
    {
      return this.d;
    }

    public String e()
    {
      return this.e;
    }

    public String f()
    {
      return this.f;
    }

    public String g()
    {
      return this.g;
    }

    public String h()
    {
      return this.h;
    }

    public String i()
    {
      return this.i;
    }

    public String j()
    {
      return this.j;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.ad.d
 * JD-Core Version:    0.6.2
 */