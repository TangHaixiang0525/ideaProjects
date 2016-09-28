package com.sohu.app.ads.sdk.iterface;

import android.content.Context;
import java.util.HashMap;

public abstract interface IDisplayLoader
{
  public abstract void destory();

  public abstract void requestSystemImage(Context paramContext, String paramString, HashMap<String, String> paramHashMap, ImageDownloadCallback paramImageDownloadCallback);

  public abstract void uploadSystemImageData(Context paramContext);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.iterface.IDisplayLoader
 * JD-Core Version:    0.6.2
 */