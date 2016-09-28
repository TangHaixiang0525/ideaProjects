package com.mstar.android.rtp;

import java.net.InetAddress;
import java.net.SocketException;

public class AudioStream extends RtpStream
{
  public AudioStream(InetAddress paramInetAddress)
    throws SocketException
  {
  }

  public AudioCodec getCodec()
  {
    throw new RuntimeException("stub");
  }

  public int getDtmfType()
  {
    throw new RuntimeException("stub");
  }

  public AudioGroup getGroup()
  {
    throw new RuntimeException("stub");
  }

  public final boolean isBusy()
  {
    throw new RuntimeException("stub");
  }

  public void join(AudioGroup paramAudioGroup)
  {
    throw new RuntimeException("stub");
  }

  public void setCodec(AudioCodec paramAudioCodec)
  {
    throw new RuntimeException("stub");
  }

  public void setDtmfType(int paramInt)
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.rtp.AudioStream
 * JD-Core Version:    0.6.2
 */