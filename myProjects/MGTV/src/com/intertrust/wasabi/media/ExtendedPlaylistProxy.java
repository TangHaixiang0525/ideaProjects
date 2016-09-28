package com.intertrust.wasabi.media;

import com.intertrust.wasabi.ErrorCodeException;

public class ExtendedPlaylistProxy extends PlaylistProxy
{
  static
  {
    com.intertrust.wasabi.media.jni.ExtendedPlaylistProxy.register();
  }

  public ExtendedPlaylistProxy()
    throws ErrorCodeException
  {
  }

  public String rewriteUrl(String paramString)
    throws ErrorCodeException
  {
    try
    {
      String[] arrayOfString = new String[1];
      ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.ExtendedPlaylistProxy.rewriteUrl(this, paramString, null, arrayOfString));
      String str = arrayOfString[0];
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.media.ExtendedPlaylistProxy
 * JD-Core Version:    0.6.2
 */