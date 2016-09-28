package com.sohu.mma.tracking.c;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

class f
  implements LocationListener
{
  f(e parame)
  {
  }

  public void onLocationChanged(Location paramLocation)
  {
    double d1 = paramLocation.getLongitude();
    double d2 = paramLocation.getLatitude();
    g.a(d2 + "x" + d1);
    e.a(this.a, e.a(this.a).append(d2).append("x").append(d1));
  }

  public void onProviderDisabled(String paramString)
  {
    g.a("onProviderDisabled:" + paramString);
  }

  public void onProviderEnabled(String paramString)
  {
    g.a("onProviderEnabled:" + paramString);
  }

  public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
  {
    g.a("onStatusChanged:" + paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mma.tracking.c.f
 * JD-Core Version:    0.6.2
 */