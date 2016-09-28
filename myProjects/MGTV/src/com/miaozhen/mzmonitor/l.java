package com.miaozhen.mzmonitor;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

class l
  implements LocationListener
{
  l(k paramk)
  {
  }

  public void onLocationChanged(Location paramLocation)
  {
    k.a(this.a, paramLocation);
  }

  public void onProviderDisabled(String paramString)
  {
    k.a(this.a);
  }

  public void onProviderEnabled(String paramString)
  {
  }

  public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.l
 * JD-Core Version:    0.6.2
 */