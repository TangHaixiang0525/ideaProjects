package com.intertrust.wasabi.media;

import com.intertrust.wasabi.ErrorCodeException;

public final class MediaStream
  implements MediaStreamInterface
{
  private long handle;

  public MediaStream(MediaStreamInterface paramMediaStreamInterface, FormatInfo paramFormatInfo)
    throws ErrorCodeException, NullPointerException
  {
    long[] arrayOfLong = new long[1];
    ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.MediaStream.open(paramMediaStreamInterface, paramFormatInfo, arrayOfLong));
    this.handle = arrayOfLong[0];
  }

  public MediaStream(String paramString, SourceType paramSourceType, FormatInfo paramFormatInfo)
    throws ErrorCodeException, NullPointerException
  {
    long[] arrayOfLong = new long[1];
    ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.MediaStream.openUrl(paramString, paramSourceType, paramFormatInfo, arrayOfLong));
    this.handle = arrayOfLong[0];
  }

  public void close()
  {
    try
    {
      com.intertrust.wasabi.media.jni.MediaStream.close(this.handle);
      this.handle = 0L;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public String getContentType()
    throws ErrorCodeException
  {
    String[] arrayOfString = new String[1];
    ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.MediaStream.getContentType(this.handle, arrayOfString));
    return arrayOfString[0];
  }

  public Object getKey()
    throws ErrorCodeException
  {
    if (this.handle == 0L)
      ErrorCodeException.checkResult(-100004);
    return this;
  }

  public long getSize()
    throws ErrorCodeException
  {
    long[] arrayOfLong = new long[1];
    ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.MediaStream.getSize(this.handle, arrayOfLong));
    return arrayOfLong[0];
  }

  public int read(byte[] paramArrayOfByte)
    throws ErrorCodeException
  {
    return read(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws ErrorCodeException
  {
    int[] arrayOfInt = new int[1];
    int i = com.intertrust.wasabi.media.jni.MediaStream.read(this.handle, paramArrayOfByte, paramInt1, paramInt2, arrayOfInt);
    if (i == -100019)
      return -1;
    ErrorCodeException.checkResult(i);
    return arrayOfInt[0];
  }

  public void seek(long paramLong)
    throws ErrorCodeException
  {
    ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.MediaStream.seek(this.handle, paramLong));
  }

  public long tell()
    throws ErrorCodeException
  {
    long[] arrayOfLong = new long[1];
    ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.MediaStream.tell(this.handle, arrayOfLong));
    return arrayOfLong[0];
  }

  public static class FormatInfo
  {
  }

  public static class FormatInfoGeneric extends MediaStream.FormatInfo
  {
    public long clear_text_size;
    public String content_id;
    public byte[] iv;
    public Object key;
    public String license_data;
    public String ms3_url;
  }

  public static enum SourceType
  {
    static
    {
      AES128CBC = new SourceType("AES128CBC", 1);
      SourceType[] arrayOfSourceType = new SourceType[2];
      arrayOfSourceType[0] = DCF;
      arrayOfSourceType[1] = AES128CBC;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.media.MediaStream
 * JD-Core Version:    0.6.2
 */