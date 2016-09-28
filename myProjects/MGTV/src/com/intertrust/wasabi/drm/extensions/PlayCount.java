package com.intertrust.wasabi.drm.extensions;

import com.intertrust.wasabi.ErrorCodeException;

public final class PlayCount
{
  private long jniPeer;

  public PlayCount()
    throws ErrorCodeException
  {
    long[] arrayOfLong = new long[1];
    ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.extensions.PlayCount.open(arrayOfLong));
    this.jniPeer = arrayOfLong[0];
  }

  private void destroy()
    throws ErrorCodeException
  {
    if (this.jniPeer != 0L)
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.extensions.PlayCount.close(this.jniPeer));
      this.jniPeer = 0L;
    }
  }

  public void addEvent(String paramString1, String paramString2)
    throws ErrorCodeException, NullPointerException
  {
    if ((paramString1 == null) || (paramString2 == null))
      throw new NullPointerException("parameters cannot be null");
    ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.extensions.PlayCount.addEvent(this.jniPeer, paramString1, paramString2));
  }

  public void uploadEvents(String paramString)
    throws ErrorCodeException, NullPointerException
  {
    if (paramString == null)
      throw new NullPointerException("serviceId parameter cannot be null");
    ErrorCodeException.checkResult(com.intertrust.wasabi.drm.jni.extensions.PlayCount.uploadEvents(this.jniPeer, paramString));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.drm.extensions.PlayCount
 * JD-Core Version:    0.6.2
 */