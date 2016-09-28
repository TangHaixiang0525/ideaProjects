package android.net.pppoe;

import android.content.Context;
import android.net.LinkCapabilities;
import android.net.LinkProperties;
import android.net.NetworkInfo;
import android.net.NetworkStateTracker;
import android.os.Handler;

public class PppoeStateTracker extends Handler
  implements NetworkStateTracker
{
  public static PppoeStateTracker sInstance;

  public static PppoeStateTracker getInstance()
  {
    try
    {
      throw new RuntimeException("stub");
    }
    finally
    {
    }
  }

  public void captivePortalCheckComplete()
  {
    throw new RuntimeException("stub");
  }

  public void defaultRouteSet(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public LinkCapabilities getLinkCapabilities()
  {
    throw new RuntimeException("stub");
  }

  public LinkProperties getLinkProperties()
  {
    throw new RuntimeException("stub");
  }

  public NetworkInfo getNetworkInfo()
  {
    throw new RuntimeException("stub");
  }

  public String getTcpBufferSizesPropName()
  {
    throw new RuntimeException("stub");
  }

  public boolean isAvailable()
  {
    throw new RuntimeException("stub");
  }

  public boolean isDefaultRouteSet()
  {
    throw new RuntimeException("stub");
  }

  public boolean isPrivateDnsRouteSet()
  {
    throw new RuntimeException("stub");
  }

  public boolean isTeardownRequested()
  {
    throw new RuntimeException("stub");
  }

  public void privateDnsRouteSet(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public boolean reconnect()
  {
    throw new RuntimeException("stub");
  }

  public void setDependencyMet(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public void setPolicyDataEnable(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public boolean setRadio(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public void setTeardownRequested(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public void setUserDataEnable(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public void startMonitoring(Context paramContext, Handler paramHandler)
  {
    throw new RuntimeException("stub");
  }

  public boolean teardown()
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.pppoe.PppoeStateTracker
 * JD-Core Version:    0.6.2
 */