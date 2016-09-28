package com.caverock.androidsvg;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.util.Log;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SimpleAssetResolver extends SVGExternalFileResolver
{
  private static final String TAG = SimpleAssetResolver.class.getSimpleName();
  private static final Set<String> supportedFormats = new HashSet(8);
  private AssetManager assetManager;

  public SimpleAssetResolver(AssetManager paramAssetManager)
  {
    supportedFormats.add("image/svg+xml");
    supportedFormats.add("image/jpeg");
    supportedFormats.add("image/png");
    supportedFormats.add("image/pjpeg");
    supportedFormats.add("image/gif");
    supportedFormats.add("image/bmp");
    supportedFormats.add("image/x-windows-bmp");
    if (Build.VERSION.SDK_INT >= 14)
      supportedFormats.add("image/webp");
    this.assetManager = paramAssetManager;
  }

  public boolean isFormatSupported(String paramString)
  {
    return supportedFormats.contains(paramString);
  }

  public Typeface resolveFont(String paramString1, int paramInt, String paramString2)
  {
    Log.i(TAG, "resolveFont(" + paramString1 + "," + paramInt + "," + paramString2 + ")");
    try
    {
      Typeface localTypeface2 = Typeface.createFromAsset(this.assetManager, paramString1 + ".ttf");
      return localTypeface2;
    }
    catch (Exception localException1)
    {
      try
      {
        Typeface localTypeface1 = Typeface.createFromAsset(this.assetManager, paramString1 + ".otf");
        return localTypeface1;
      }
      catch (Exception localException2)
      {
      }
    }
    return null;
  }

  public Bitmap resolveImage(String paramString)
  {
    Log.i(TAG, "resolveImage(" + paramString + ")");
    try
    {
      Bitmap localBitmap = BitmapFactory.decodeStream(this.assetManager.open(paramString));
      return localBitmap;
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.caverock.androidsvg.SimpleAssetResolver
 * JD-Core Version:    0.6.2
 */