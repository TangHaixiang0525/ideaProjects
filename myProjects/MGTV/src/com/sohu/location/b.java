package com.sohu.location;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

class b
  implements LocationListener
{
  b(a parama)
  {
  }

  public void onLocationChanged(Location paramLocation)
  {
    try
    {
      a.a(this.a).a(paramLocation);
      a.b(this.a).removeUpdates(this);
      a.b(this.a).removeUpdates(this.a.b);
      return;
    }
    catch (Exception localException)
    {
      com.sohu.upload.a.a.b("GPS�ص��ӿ��г����쳣:" + localException.getMessage());
    }
  }

  public void onProviderDisabled(String paramString)
  {
  }

  public void onProviderEnabled(String paramString)
  {
  }

  public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.location.b
 * JD-Core Version:    0.6.2
 */