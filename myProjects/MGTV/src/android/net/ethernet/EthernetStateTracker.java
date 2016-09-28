package android.net.ethernet;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.DhcpInfoInternal;
import android.net.LinkCapabilities;
import android.net.LinkProperties;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.NetworkStateTracker;
import android.os.Handler;
import android.os.Message;
import java.net.UnknownHostException;

public class EthernetStateTracker extends Handler
  implements NetworkStateTracker
{
  public static final int EVENT_ADDR_REMOVE = 8;
  public static final int EVENT_DHCP_START = 0;
  public static final int EVENT_HW_CONNECTED = 3;
  public static final int EVENT_HW_DISCONNECTED = 4;
  public static final int EVENT_HW_PHYCONNECTED = 5;
  public static final int EVENT_INTERFACE_CONFIGURATION_FAILED = 2;
  public static final int EVENT_INTERFACE_CONFIGURATION_SUCCEEDED = 1;
  public static final int EVENT_PROXY_CHANGE = 9;
  public static final int EVENT_RESET_INTERFACE = 7;
  public static final int EVENT_STOP_INTERFACE = 6;
  public static EthernetStateTracker sInstance;

  public static EthernetStateTracker getInstance()
  {
    try
    {
      throw new RuntimeException("stub");
    }
    finally
    {
    }
  }

  public void PostLinkageConfig(DhcpInfoInternal paramDhcpInfoInternal)
  {
    throw new RuntimeException("stub");
  }

  public void StartPolling()
  {
    throw new RuntimeException("stub");
  }

  public void captivePortalCheckComplete()
  {
    throw new RuntimeException("stub");
  }

  public boolean configureInterfaceAutoIP()
  {
    throw new RuntimeException("stub");
  }

  public void defaultRouteSet(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public String getEthernetIPAddr()
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

  public void handleMessage(Message paramMessage)
  {
    throw new RuntimeException("stub");
  }

  public boolean isAvailable()
  {
    throw new RuntimeException("stub");
  }

  public boolean isCableConnected()
  {
    throw new RuntimeException("stub");
  }

  public boolean isDefaultRouteSet()
  {
    throw new RuntimeException("stub");
  }

  public boolean isNetworkConnected()
  {
    throw new RuntimeException("stub");
  }

  public int isPppoeUp()
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

  public DhcpInfoInternal makeInfoInternal(DhcpInfo paramDhcpInfo)
  {
    throw new RuntimeException("stub");
  }

  public void notifyAddressRemove(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public void notifyPhyConnected(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public void notifyStateChange(String paramString, NetworkInfo.DetailedState paramDetailedState)
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

  public boolean resetInterface()
    throws UnknownHostException
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

  public boolean stopInterface(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public boolean teardown()
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.ethernet.EthernetStateTracker
 * JD-Core Version:    0.6.2
 */