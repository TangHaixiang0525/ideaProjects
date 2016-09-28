package com.intertrust.wasabi.media;

import com.intertrust.wasabi.ErrorCodeException;

public abstract interface MediaStreamInterface
{
  public static final String CONTENT_TYPE_AES128CBC = "application/vnd.intertrust.drm.aes128.cbc";
  public static final String CONTENT_TYPE_DCF = "application/vnd.oma.drm.dcf";

  public abstract void close();

  public abstract String getContentType()
    throws ErrorCodeException;

  public abstract long getSize()
    throws ErrorCodeException;

  public abstract int read(byte[] paramArrayOfByte)
    throws ErrorCodeException;

  public abstract int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws ErrorCodeException;

  public abstract void seek(long paramLong)
    throws ErrorCodeException;

  public abstract long tell()
    throws ErrorCodeException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.media.MediaStreamInterface
 * JD-Core Version:    0.6.2
 */