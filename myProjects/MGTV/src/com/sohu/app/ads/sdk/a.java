package com.sohu.app.ads.sdk;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.sohu.app.ads.sdkcodec.Base64;

public class a
{
  private static boolean a = true;
  private static a b;

  public static a a()
  {
    if (b == null)
      b = new a();
    return b;
  }

  public Drawable a(String paramString)
  {
    try
    {
      byte[] arrayOfByte = Base64.a(paramString.getBytes());
      BitmapDrawable localBitmapDrawable = new BitmapDrawable(BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length));
      return localBitmapDrawable;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.a
 * JD-Core Version:    0.6.2
 */