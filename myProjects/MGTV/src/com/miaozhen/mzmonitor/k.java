package com.miaozhen.mzmonitor;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

class k
{
  private static k d;
  private Context a;
  private boolean b;
  private LocationManager c;
  private final LocationListener e = new l(this);

  private k(Context paramContext)
  {
    this.a = paramContext;
    this.b = false;
  }

  public static k a(Context paramContext)
  {
    try
    {
      if (d == null)
        d = new k(paramContext);
      k localk = d;
      return localk;
    }
    finally
    {
    }
  }

  private void a()
  {
    if (this.c != null)
    {
      this.c.removeUpdates(this.e);
      this.c = null;
      this.b = false;
    }
  }

  private void a(Location paramLocation)
  {
    Log.d("MZSDK:20131001", paramLocation.toString());
    MZSdkProfile.updateLocation(this.a, b(paramLocation), c(paramLocation));
    a();
  }

  private String b(Location paramLocation)
  {
    if (!paramLocation.hasAccuracy())
      return "[UNKNOWN]";
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = Double.valueOf(paramLocation.getLatitude());
    arrayOfObject[1] = Double.valueOf(paramLocation.getLongitude());
    arrayOfObject[2] = Float.valueOf(paramLocation.getAccuracy());
    arrayOfObject[3] = Long.valueOf(MZUtility.currentUnixTimestamp());
    return i.a(String.format("%.7f,%.7f,%f,%d", arrayOfObject));
  }

  private String c(Location paramLocation)
  {
    if (!paramLocation.hasAccuracy())
      return "[UNKNOWN]";
    String str = paramLocation.getAccuracy();
    if (str.length() >= 10)
      str = str.substring(0, 10);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Double.valueOf(paramLocation.getLatitude());
    arrayOfObject[1] = Double.valueOf(paramLocation.getLongitude());
    return String.format("%.6fx%.6fx", arrayOfObject) + str;
  }

  public void b(Context paramContext)
  {
    if (!this.b)
    {
      this.c = ((LocationManager)paramContext.getSystemService("location"));
      Criteria localCriteria = new Criteria();
      localCriteria.setAltitudeRequired(false);
      localCriteria.setBearingRequired(false);
      localCriteria.setSpeedRequired(false);
      localCriteria.setCostAllowed(false);
      String str = this.c.getBestProvider(localCriteria, true);
      if (str == null)
        return;
      this.b = true;
      this.c.requestLocationUpdates(str, 500L, 0.0F, this.e);
      new m(this).start();
      return;
    }
    Log.d("MZSDK:20131001", "MZLocationManager is still running...");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.k
 * JD-Core Version:    0.6.2
 */