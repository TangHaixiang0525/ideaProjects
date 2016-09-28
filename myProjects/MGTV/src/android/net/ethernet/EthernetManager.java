package android.net.ethernet;

public class EthernetManager
{
  public static final int ETHERNET_DEVICE_SCAN_RESULT_READY = 0;
  public static final String ETHERNET_INTERFACE_CONF_CHANGED = "android.net.ethernet.ETHERNET_INTERFACE_CONF_CHANGED";
  public static final String ETHERNET_STATE_CHANGED_ACTION = "android.net.ethernet.ETHERNET_STATE_CHANGED";
  public static final int ETHERNET_STATE_DISABLED = 1;
  public static final int ETHERNET_STATE_ENABLED = 2;
  public static final int ETHERNET_STATE_UNKNOWN = 0;
  public static final String EXTRA_ETHERNET_STATE = "ETHERNET_state";
  public static final String EXTRA_LINK_CAPABILITIES = "linkCapabilities";
  public static final String EXTRA_LINK_PROPERTIES = "linkProperties";
  public static final String EXTRA_NETWORK_INFO = "networkInfo";
  public static final String EXTRA_PREVIOUS_ETHERNET_STATE = "previous_ETHERNET_state";
  public static final String NETWORK_STATE_CHANGED_ACTION = "android.net.ethernet.STATE_CHANGE";
  public static final String TAG = "EthernetManager";

  public static EthernetManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public String[] getDeviceNameList()
  {
    throw new RuntimeException("stub");
  }

  public EthernetDevInfo getSavedConfig()
  {
    throw new RuntimeException("stub");
  }

  public int getState()
  {
    throw new RuntimeException("stub");
  }

  public int getTotalInterface()
  {
    throw new RuntimeException("stub");
  }

  public boolean isCableConnected()
  {
    throw new RuntimeException("stub");
  }

  public boolean isConfigured()
  {
    throw new RuntimeException("stub");
  }

  public boolean isNetworkConnected()
  {
    throw new RuntimeException("stub");
  }

  public void setDefaultConf()
  {
    throw new RuntimeException("stub");
  }

  public void setEnabled(boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }

  public void updateDevInfo(EthernetDevInfo paramEthernetDevInfo)
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.ethernet.EthernetManager
 * JD-Core Version:    0.6.2
 */