package com.sohu.mma.tracking.c;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;

public class e
{
  private static e c = null;
  public LocationListener a = new f(this);
  private LocationManager b;
  private StringBuilder d = new StringBuilder();

  private e(Context paramContext)
  {
    this.b = ((LocationManager)paramContext.getSystemService("location"));
  }

  public static e a(Context paramContext)
  {
    if (c == null)
      c = new e(paramContext);
    return c;
  }

  public String a()
  {
    return this.d.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mma.tracking.c.e
 * JD-Core Version:    0.6.2
 */