package com.mstar.android.sip;

public class SimpleSessionDescription
{
  public SimpleSessionDescription(long paramLong, String paramString)
  {
  }

  public SimpleSessionDescription(String paramString)
  {
  }

  public String encode()
  {
    throw new RuntimeException("stub");
  }

  public String getAddress()
  {
    throw new RuntimeException("stub");
  }

  public String getAttribute(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public String[] getAttributeNames()
  {
    throw new RuntimeException("stub");
  }

  public int getBandwidth(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public String[] getBandwidthTypes()
  {
    throw new RuntimeException("stub");
  }

  public String getEncryptionKey()
  {
    throw new RuntimeException("stub");
  }

  public String getEncryptionMethod()
  {
    throw new RuntimeException("stub");
  }

  public Media[] getMedia()
  {
    throw new RuntimeException("stub");
  }

  public Media newMedia(String paramString1, int paramInt1, int paramInt2, String paramString2)
  {
    throw new RuntimeException("stub");
  }

  public void setAddress(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public void setAttribute(String paramString1, String paramString2)
  {
    throw new RuntimeException("stub");
  }

  public void setBandwidth(String paramString, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public void setEncryption(String paramString1, String paramString2)
  {
    throw new RuntimeException("stub");
  }

  public static class Media extends SimpleSessionDescription.Fields
  {
    public String getFmtp(int paramInt)
    {
      throw new RuntimeException("stub");
    }

    public String getFmtp(String paramString)
    {
      throw new RuntimeException("stub");
    }

    public String[] getFormats()
    {
      throw new RuntimeException("stub");
    }

    public int getPort()
    {
      throw new RuntimeException("stub");
    }

    public int getPortCount()
    {
      throw new RuntimeException("stub");
    }

    public String getProtocol()
    {
      throw new RuntimeException("stub");
    }

    public int[] getRtpPayloadTypes()
    {
      throw new RuntimeException("stub");
    }

    public String getRtpmap(int paramInt)
    {
      throw new RuntimeException("stub");
    }

    public String getType()
    {
      throw new RuntimeException("stub");
    }

    public void removeFormat(String paramString)
    {
      throw new RuntimeException("stub");
    }

    public void removeRtpPayload(int paramInt)
    {
      throw new RuntimeException("stub");
    }

    public void setFormat(String paramString1, String paramString2)
    {
      throw new RuntimeException("stub");
    }

    public void setRtpPayload(int paramInt, String paramString1, String paramString2)
    {
      throw new RuntimeException("stub");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.sip.SimpleSessionDescription
 * JD-Core Version:    0.6.2
 */