package android.net.pppoe;

public class PppoeManager
{
  public static final int MSG_PPPOE_AUTH_FAILED = 4;
  public static final int MSG_PPPOE_CONNECT = 0;
  public static final int MSG_PPPOE_CONNECTING = 2;
  public static final int MSG_PPPOE_DISCONNECT = 1;
  public static final int MSG_PPPOE_DISCONNECTING = 3;
  public static final int MSG_PPPOE_FAILED = 6;
  public static final int MSG_PPPOE_TIME_OUT = 5;
  public static final String PPPOE_STATE_ACTION = "android.net.pppoe.PPPOE_STATE_ACTION";
  public static final String PPPOE_STATE_AUTHFAILED = "authfailed";
  public static final String PPPOE_STATE_CONNECT = "connect";
  public static final String PPPOE_STATE_CONNECTING = "connecting";
  public static final String PPPOE_STATE_DISCONNECT = "disconnect";
  public static final String PPPOE_STATE_DISCONNECTING = "disconnecting";
  public static final String PPPOE_STATE_FAILED = "failed";
  public static final String PPPOE_STATE_LINKTIMEOUT = "linktimeout";
  public static final String PPPOE_STATE_STATUE = "PppoeStatus";

  public static PppoeManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public boolean PppoeDialup()
  {
    throw new RuntimeException("stub");
  }

  public String PppoeGetInterface()
  {
    throw new RuntimeException("stub");
  }

  public String PppoeGetPW()
  {
    throw new RuntimeException("stub");
  }

  public PPPOE_STA PppoeGetStatus()
  {
    throw new RuntimeException("stub");
  }

  public String PppoeGetUser()
  {
    throw new RuntimeException("stub");
  }

  public void PppoeHangUp()
  {
    throw new RuntimeException("stub");
  }

  public void PppoeMonitor()
  {
    throw new RuntimeException("stub");
  }

  public boolean PppoeSetInterface(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public boolean PppoeSetPW(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public boolean PppoeSetUser(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public void connectPppoe(String paramString1, String paramString2)
  {
    throw new RuntimeException("stub");
  }

  public void connectPppoe(String paramString1, String paramString2, String paramString3)
  {
    throw new RuntimeException("stub");
  }

  public void disconnectPppoe()
  {
    throw new RuntimeException("stub");
  }

  public String getDns1()
  {
    throw new RuntimeException("stub");
  }

  public String getDns2()
  {
    throw new RuntimeException("stub");
  }

  public String getInterfaceName()
  {
    throw new RuntimeException("stub");
  }

  public String getIpaddr()
  {
    throw new RuntimeException("stub");
  }

  public String getMask()
  {
    throw new RuntimeException("stub");
  }

  public String getPppoeStatus()
  {
    throw new RuntimeException("stub");
  }

  public String getRoute()
  {
    throw new RuntimeException("stub");
  }

  public boolean isPppoeRunning()
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.pppoe.PppoeManager
 * JD-Core Version:    0.6.2
 */