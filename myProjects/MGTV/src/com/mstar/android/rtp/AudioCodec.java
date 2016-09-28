package com.mstar.android.rtp;

public class AudioCodec
{
  public static final AudioCodec AMR;
  public static final AudioCodec GSM;
  public static final AudioCodec GSM_EFR;
  public static final AudioCodec PCMA;
  public static final AudioCodec PCMU;
  public final String fmtp;
  public final String rtpmap;
  public final int type;

  public static AudioCodec getCodec(int paramInt, String paramString1, String paramString2)
  {
    throw new RuntimeException("stub");
  }

  public static AudioCodec[] getCodecs()
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.rtp.AudioCodec
 * JD-Core Version:    0.6.2
 */