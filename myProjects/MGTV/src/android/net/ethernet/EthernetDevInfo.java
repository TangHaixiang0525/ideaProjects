package android.net.ethernet;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class EthernetDevInfo
  implements Parcelable
{
  public static final Parcelable.Creator<EthernetDevInfo> CREATOR;
  public static final String ETHERNET_CONN_MODE_DHCP = "dhcp";
  public static final String ETHERNET_CONN_MODE_MANUAL = "manual";

  public int describeContents()
  {
    throw new RuntimeException("stub");
  }

  public String getConnectMode()
  {
    throw new RuntimeException("stub");
  }

  public String getDns2Addr()
  {
    throw new RuntimeException("stub");
  }

  public String getDnsAddr()
  {
    throw new RuntimeException("stub");
  }

  public String getIfName()
  {
    throw new RuntimeException("stub");
  }

  public String getIpAddress()
  {
    throw new RuntimeException("stub");
  }

  public String getMacAddress()
  {
    throw new RuntimeException("stub");
  }

  public String getNetMask()
  {
    throw new RuntimeException("stub");
  }

  public String getProxyHost()
  {
    throw new RuntimeException("stub");
  }

  public boolean getProxyOn()
  {
    throw new RuntimeException("stub");
  }

  public String getProxyPort()
  {
    throw new RuntimeException("stub");
  }

  public String getRouteAddr()
  {
    throw new RuntimeException("stub");
  }

  public boolean setConnectMode(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public void setDns2Addr(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public void setDnsAddr(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public void setIfName(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public void setIpAddress(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public void setNetMask(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public void setProxyHost(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public void setProxyOn(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public void setProxyPort(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public void setRouteAddr(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.ethernet.EthernetDevInfo
 * JD-Core Version:    0.6.2
 */