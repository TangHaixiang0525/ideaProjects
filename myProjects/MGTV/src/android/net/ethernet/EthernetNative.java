package android.net.ethernet;

public class EthernetNative
{
  public static native int getInterfaceCnt();

  public static native String getInterfaceName(int paramInt);

  public static native String getMacAddress();

  public static native int initEthernetNative();

  public static native int isPppoeUp();

  public static native String waitForEvent();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.ethernet.EthernetNative
 * JD-Core Version:    0.6.2
 */