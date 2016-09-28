package com.ssww.p2p;

public class SSWW
{
  public static final String P2P_OPTION_MEMORY_LIMIT = "memoryLimit";
  private static SSWW sSSWWInstance = new SSWW();

  static
  {
    System.loadLibrary("p2p");
  }

  private SSWW()
  {
    initP2P();
  }

  public static SSWW getInstance()
  {
    return sSSWWInstance;
  }

  private native void initP2P();

  public native void clearVodStream();

  public native void disableP2P();

  public native void enableP2P();

  public native double[] getP2PStats();

  public native int getVersion();

  public native String prepareVodStream(String paramString1, String paramString2, String paramString3, String paramString4);

  public native void setP2POption(String paramString, int paramInt);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.ssww.p2p.SSWW
 * JD-Core Version:    0.6.2
 */