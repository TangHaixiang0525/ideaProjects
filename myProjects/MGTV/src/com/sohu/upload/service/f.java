package com.sohu.upload.service;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

class f
  implements LocationListener
{
  f(CountService paramCountService)
  {
  }

  public void onLocationChanged(Location paramLocation)
  {
    if (paramLocation != null)
      CountService.a(this.a, paramLocation);
  }

  public void onProviderDisabled(String paramString)
  {
  }

  public void onProviderEnabled(String paramString)
  {
    if (CountService.j(this.a) != null)
    {
      Location localLocation = CountService.j(this.a).getLastKnownLocation(paramString);
      if (localLocation != null)
        CountService.a(this.a, localLocation);
    }
  }

  public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.upload.service.f
 * JD-Core Version:    0.6.2
 */