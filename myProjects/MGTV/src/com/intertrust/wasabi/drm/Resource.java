package com.intertrust.wasabi.drm;

public final class Resource
{
  private String language;
  private String mimeType;
  private byte[] payload;

  Resource(String paramString1, String paramString2, byte[] paramArrayOfByte)
  {
    this.mimeType = paramString1;
    this.language = paramString2;
    this.payload = paramArrayOfByte;
  }

  public String getLanguage()
  {
    return this.language;
  }

  public String getMimeType()
  {
    return this.mimeType;
  }

  public byte[] getPayload()
  {
    return this.payload;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.drm.Resource
 * JD-Core Version:    0.6.2
 */