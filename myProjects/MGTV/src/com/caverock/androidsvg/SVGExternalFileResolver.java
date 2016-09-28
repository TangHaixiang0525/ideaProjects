package com.caverock.androidsvg;

import android.graphics.Bitmap;
import android.graphics.Typeface;

public abstract class SVGExternalFileResolver
{
  public boolean isFormatSupported(String paramString)
  {
    return false;
  }

  public Typeface resolveFont(String paramString1, int paramInt, String paramString2)
  {
    return null;
  }

  public Bitmap resolveImage(String paramString)
  {
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.caverock.androidsvg.SVGExternalFileResolver
 * JD-Core Version:    0.6.2
 */