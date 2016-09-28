package u.aly;

import android.content.Context;
import android.content.res.Resources;

public class bs
{
  private static final String a = bs.class.getName();
  private static bs b = null;
  private Resources c;
  private final String d;
  private final String e = "drawable";
  private final String f = "id";
  private final String g = "layout";
  private final String h = "anim";
  private final String i = "style";
  private final String j = "string";
  private final String k = "array";

  private bs(Context paramContext)
  {
    this.c = paramContext.getResources();
    this.d = paramContext.getPackageName();
  }

  private int a(String paramString1, String paramString2)
  {
    int m = this.c.getIdentifier(paramString1, paramString2, this.d);
    if (m == 0)
    {
      br.b(a, "getRes(" + paramString2 + "/ " + paramString1 + ")");
      br.b(a, "Error getting resource. Make sure you have copied all resources (res/) from SDK to your project. ");
      m = 0;
    }
    return m;
  }

  public static bs a(Context paramContext)
  {
    try
    {
      if (b == null)
        b = new bs(paramContext.getApplicationContext());
      bs localbs = b;
      return localbs;
    }
    finally
    {
    }
  }

  public int a(String paramString)
  {
    return a(paramString, "anim");
  }

  public int b(String paramString)
  {
    return a(paramString, "id");
  }

  public int c(String paramString)
  {
    return a(paramString, "drawable");
  }

  public int d(String paramString)
  {
    return a(paramString, "layout");
  }

  public int e(String paramString)
  {
    return a(paramString, "style");
  }

  public int f(String paramString)
  {
    return a(paramString, "string");
  }

  public int g(String paramString)
  {
    return a(paramString, "array");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.bs
 * JD-Core Version:    0.6.2
 */