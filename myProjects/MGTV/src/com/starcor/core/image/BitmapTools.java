package com.starcor.core.image;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.starcor.core.utils.Logger;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class BitmapTools
{
  private static final String TAG = "BitmapTools";
  private static ThreadLocal<byte[]> _local_buf = new ThreadLocal();
  private static final Bitmap.Config defaultConfig = Bitmap.Config.ARGB_8888;

  public static Bitmap decodeFile(String paramString)
  {
    return decodeFile(paramString, defaultConfig, 0, 0);
  }

  public static Bitmap decodeFile(String paramString, Bitmap.Config paramConfig)
  {
    return decodeFile(paramString, paramConfig, 0, 0);
  }

  public static Bitmap decodeFile(String paramString, Bitmap.Config paramConfig, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = (byte[])_local_buf.get();
    if (arrayOfByte == null)
    {
      arrayOfByte = new byte[65536];
      _local_buf.set(arrayOfByte);
    }
    Logger.i("BitmapTools", "decodeFile path:" + paramString);
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inTempStorage = arrayOfByte;
    localOptions.inPreferredConfig = paramConfig;
    try
    {
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(new FileInputStream(paramString), 65536);
      if ((paramInt1 > 0) && (paramInt2 > 0))
      {
        localOptions.inJustDecodeBounds = true;
        localBufferedInputStream.mark(65536);
        BitmapFactory.decodeStream(localBufferedInputStream, null, localOptions);
      }
      try
      {
        localBufferedInputStream.reset();
        Logger.i("BitmapTools", "\twidth " + localOptions.outWidth + " height " + localOptions.outHeight);
        localOptions.inScaled = true;
        localOptions.inTargetDensity = 160;
        localOptions.inDensity = Math.min(localOptions.inTargetDensity * localOptions.outWidth / paramInt1, localOptions.inTargetDensity * localOptions.outHeight / paramInt2);
        if (localOptions.inDensity == localOptions.inTargetDensity)
          localOptions.inScaled = false;
        localOptions.inJustDecodeBounds = false;
        localOptions.inPurgeable = false;
        Bitmap localBitmap = BitmapFactory.decodeStream(localBufferedInputStream, null, localOptions);
        localBufferedInputStream.close();
        return localBitmap;
      }
      catch (IOException localIOException2)
      {
        while (true)
        {
          localBufferedInputStream.close();
          localBufferedInputStream = new BufferedInputStream(new FileInputStream(paramString), 65536);
        }
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
      return null;
    }
    catch (IOException localIOException1)
    {
      while (true)
        localIOException1.printStackTrace();
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public static Bitmap decodeStream(InputStream paramInputStream, Bitmap.Config paramConfig, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte = (byte[])_local_buf.get();
    if (arrayOfByte == null)
    {
      arrayOfByte = new byte[65536];
      _local_buf.set(arrayOfByte);
    }
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inTempStorage = arrayOfByte;
    localOptions.inPreferredConfig = paramConfig;
    try
    {
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(paramInputStream, 65536);
      if ((paramInt1 > 0) && (paramInt2 > 0))
      {
        localOptions.inJustDecodeBounds = true;
        localBufferedInputStream.mark(65536);
        BitmapFactory.decodeStream(localBufferedInputStream, null, localOptions);
      }
      try
      {
        localBufferedInputStream.reset();
        Logger.i("BitmapTools", "\twidth " + localOptions.outWidth + " height " + localOptions.outHeight);
        localOptions.inScaled = true;
        localOptions.inTargetDensity = 160;
        localOptions.inDensity = Math.min(localOptions.inTargetDensity * localOptions.outWidth / paramInt1, localOptions.inTargetDensity * localOptions.outHeight / paramInt2);
        if (localOptions.inDensity == localOptions.inTargetDensity)
          localOptions.inScaled = false;
        localOptions.inJustDecodeBounds = false;
        localOptions.inPurgeable = false;
        Bitmap localBitmap = BitmapFactory.decodeStream(localBufferedInputStream, null, localOptions);
        localBufferedInputStream.close();
        return localBitmap;
      }
      catch (IOException localIOException2)
      {
        localIOException2.printStackTrace();
        localBufferedInputStream.close();
        return null;
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
      return null;
    }
    catch (IOException localIOException1)
    {
      while (true)
        localIOException1.printStackTrace();
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.image.BitmapTools
 * JD-Core Version:    0.6.2
 */