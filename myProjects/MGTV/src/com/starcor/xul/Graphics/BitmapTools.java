package com.starcor.xul.Graphics;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;
import android.util.Log;
import android.util.Pair;
import com.starcor.xul.Utils.XulBufferedInputStream;
import com.starcor.xul.XulUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class BitmapTools
{
  public static final int BITMAP_LIFETIME = 5000;
  public static final int MINIMUM_API_LEVEL = 19;
  private static final String TAG = "BitmapTools";
  public static final String TAG_RECYCLER = "Xul BMP Recycler";
  private static ThreadLocal<XulBufferedInputStream> _localBufferedInputStream;
  private static ThreadLocal<byte[]> _local_buf;
  private static final int _maxBitmapCacheSize = 25165824;
  private static final int _maximumSameDimension = 72;
  private static final ArrayList<Pair<Long, Bitmap>> _recycledBitmapQueue;
  private static int _totalBitmapCacheSize;
  private static final Bitmap.Config defaultConfig = Bitmap.Config.ARGB_8888;
  private static Method getAllocationByteCountMethod;
  private static Method reconfigMethod;

  static
  {
    _local_buf = new ThreadLocal();
    _localBufferedInputStream = new ThreadLocal();
    _recycledBitmapQueue = new ArrayList();
    _totalBitmapCacheSize = 0;
    getAllocationByteCountMethod = null;
    reconfigMethod = null;
    if (Build.VERSION.SDK_INT >= 19);
    try
    {
      getAllocationByteCountMethod = Bitmap.class.getMethod("getAllocationByteCount", new Class[0]);
    }
    catch (Exception localException1)
    {
      try
      {
        while (true)
        {
          Class[] arrayOfClass = new Class[3];
          arrayOfClass[0] = Integer.TYPE;
          arrayOfClass[1] = Integer.TYPE;
          arrayOfClass[2] = Bitmap.Config.class;
          reconfigMethod = Bitmap.class.getMethod("reconfigure", arrayOfClass);
          return;
          localException1 = localException1;
          localException1.printStackTrace();
        }
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    }
  }

  public static int calBitmapByteSize(Bitmap paramBitmap)
  {
    if (getAllocationByteCountMethod != null)
      try
      {
        int i = ((Integer)getAllocationByteCountMethod.invoke(paramBitmap, new Object[0])).intValue();
        return i;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    return paramBitmap.getByteCount();
  }

  private static int calBitmapPixelSize(Bitmap paramBitmap)
  {
    Bitmap.Config localConfig = paramBitmap.getConfig();
    int i = 4;
    if (Bitmap.Config.ARGB_8888.equals(localConfig))
      i = 4;
    do
    {
      return i;
      if (Bitmap.Config.RGB_565.equals(localConfig))
        return 2;
    }
    while (!Bitmap.Config.ARGB_4444.equals(localConfig));
    return 2;
  }

  public static int calBitmapPixelsCount(Bitmap paramBitmap)
  {
    return paramBitmap.getWidth() * paramBitmap.getHeight();
  }

  public static void cleanRecycledBitmaps(long paramLong)
  {
    while (true)
    {
      int i;
      synchronized (_recycledBitmapQueue)
      {
        i = -1 + _recycledBitmapQueue.size();
        if (i >= 0)
        {
          Pair localPair = (Pair)_recycledBitmapQueue.get(i);
          if (((Long)localPair.first).longValue() <= paramLong)
          {
            Bitmap localBitmap = (Bitmap)localPair.second;
            calBitmapByteSize(localBitmap);
            int j = calBitmapPixelsCount(localBitmap);
            _totalBitmapCacheSize -= j;
            _recycledBitmapQueue.remove(i);
          }
        }
        else
        {
          return;
        }
      }
      i--;
    }
  }

  public static Bitmap createBitmapFromRecycledBitmaps(int paramInt1, int paramInt2)
  {
    ArrayList localArrayList = _recycledBitmapQueue;
    int i = 4 * (paramInt1 * paramInt2);
    int j = 2147483647;
    int k = -1;
    for (int m = 0; ; m++)
    {
      try
      {
        int n = _recycledBitmapQueue.size();
        if (m < n)
        {
          Bitmap localBitmap1 = (Bitmap)((Pair)_recycledBitmapQueue.get(m)).second;
          if (Build.VERSION.SDK_INT >= 19)
          {
            int i3 = calBitmapByteSize(localBitmap1);
            int i4 = (int)(1024.0F + 1.1F * i);
            if ((i > i3) || (i3 > i4))
              continue;
            int i5 = i3 - i;
            if (i5 > j)
              continue;
            j = i5;
            k = m;
            continue;
          }
          if ((localBitmap1.getWidth() != paramInt1) || (localBitmap1.getHeight() != paramInt2) || (!Bitmap.Config.ARGB_8888.equals(localBitmap1.getConfig())))
            continue;
          k = m;
        }
        if (k >= 0)
        {
          Bitmap localBitmap2 = (Bitmap)((Pair)_recycledBitmapQueue.remove(k)).second;
          int i1 = calBitmapPixelsCount(localBitmap2);
          int i2 = calBitmapByteSize(localBitmap2);
          _totalBitmapCacheSize -= i1;
          localBitmap2.eraseColor(0);
          Method localMethod1 = reconfigMethod;
          if (localMethod1 != null);
          try
          {
            Method localMethod2 = reconfigMethod;
            Object[] arrayOfObject = new Object[3];
            arrayOfObject[0] = Integer.valueOf(paramInt1);
            arrayOfObject[1] = Integer.valueOf(paramInt2);
            arrayOfObject[2] = Bitmap.Config.ARGB_8888;
            localMethod2.invoke(localBitmap2, arrayOfObject);
            return localBitmap2;
          }
          catch (Exception localException)
          {
            while (true)
              Log.e("Xul BMP Recycler", "reconfigure bitmap failed(reuse phase)!!! " + i2);
          }
        }
      }
      finally
      {
      }
      return Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
    }
  }

  public static Bitmap decodeFile(String paramString, Bitmap.Config paramConfig, int paramInt1, int paramInt2)
  {
    try
    {
      Bitmap localBitmap = decodeStream(new FileInputStream(paramString), paramConfig, paramInt1, paramInt2, 0, 0);
      return localBitmap;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static Bitmap decodeStream(InputStream paramInputStream, Bitmap.Config paramConfig, int paramInt1, int paramInt2)
  {
    return decodeStream(paramInputStream, paramConfig, paramInt1, paramInt2, 0, 0);
  }

  public static Bitmap decodeStream(InputStream paramInputStream, Bitmap.Config paramConfig, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    byte[] arrayOfByte = (byte[])_local_buf.get();
    if (arrayOfByte == null)
    {
      arrayOfByte = new byte[65536];
      _local_buf.set(arrayOfByte);
    }
    while (true)
    {
      XulBufferedInputStream localXulBufferedInputStream;
      try
      {
        if (!paramInputStream.markSupported())
        {
          localXulBufferedInputStream = (XulBufferedInputStream)_localBufferedInputStream.get();
          if (localXulBufferedInputStream == null)
          {
            localXulBufferedInputStream = new XulBufferedInputStream(paramInputStream, 65536);
            break label582;
          }
        }
        else
        {
          localOptions = new BitmapFactory.Options();
          localOptions.inTempStorage = arrayOfByte;
          localOptions.inPreferredConfig = paramConfig;
          localOptions.inJustDecodeBounds = true;
          paramInputStream.mark(65536);
          BitmapFactory.decodeStream(paramInputStream, null, localOptions);
        }
      }
      catch (Exception localIOException3)
      {
        try
        {
          paramInputStream.reset();
          if (((paramInt1 > 0) && (paramInt2 >= 0)) || ((paramInt1 >= 0) && (paramInt2 > 0)) || ((paramInt3 > 0) && (paramInt4 >= 0)) || ((paramInt3 >= 0) && (paramInt4 > 0)))
          {
            localOptions.inScaled = true;
            localOptions.inTargetDensity = 160;
            if ((paramInt3 > 0) || (paramInt4 > 0))
            {
              if ((paramInt2 == 0) && (paramInt1 == 0))
              {
                paramInt1 = Math.min(localOptions.outWidth, paramInt3);
                paramInt2 = Math.min(localOptions.outHeight, paramInt4);
              }
            }
            else
            {
              if (paramInt1 != 0)
                continue;
              i = localOptions.inTargetDensity * localOptions.outHeight / paramInt2;
              j = i;
              localOptions.inDensity = Math.min(j, i);
              if (localOptions.inDensity != localOptions.inTargetDensity)
                continue;
              localOptions.inScaled = false;
              localOptions.inJustDecodeBounds = false;
              localOptions.inPurgeable = false;
              localOptions.inMutable = true;
              if (!localOptions.inScaled)
                localOptions.inBitmap = createBitmapFromRecycledBitmaps(localOptions.outWidth, localOptions.outHeight);
              localOptions.inSampleSize = 1;
              localBitmap = BitmapFactory.decodeStream(paramInputStream, null, localOptions);
            }
          }
        }
        catch (IOException localIOException3)
        {
          try
          {
            paramInputStream.close();
            return localBitmap;
            localXulBufferedInputStream.resetInputStream(paramInputStream);
            break label582;
            localException = localException;
            localException.printStackTrace();
            try
            {
              paramInputStream.close();
              return null;
            }
            catch (IOException localIOException2)
            {
              localIOException2.printStackTrace();
              return null;
            }
            localIOException3 = localIOException3;
            localIOException3.printStackTrace();
            paramInputStream.close();
            try
            {
              paramInputStream.close();
              return null;
            }
            catch (IOException localIOException4)
            {
              localIOException4.printStackTrace();
              return null;
            }
            if ((paramInt1 > 0) && (paramInt3 > 0))
              paramInt1 = Math.min(paramInt1, paramInt3);
            if ((paramInt2 <= 0) || (paramInt4 <= 0))
              continue;
            paramInt2 = Math.min(paramInt2, paramInt4);
            continue;
            if (paramInt2 == 0)
            {
              i = localOptions.inTargetDensity * localOptions.outWidth / paramInt1;
              j = i;
              continue;
            }
            int j = localOptions.inTargetDensity * localOptions.outWidth / paramInt1;
            int i = localOptions.inTargetDensity * localOptions.outHeight / paramInt2;
            continue;
            XulUtils.roundToInt(localOptions.outWidth * localOptions.inTargetDensity / localOptions.inDensity);
            XulUtils.roundToInt(localOptions.outHeight * localOptions.inTargetDensity / localOptions.inDensity);
            continue;
          }
          catch (IOException localIOException5)
          {
            BitmapFactory.Options localOptions;
            Bitmap localBitmap;
            localIOException5.printStackTrace();
            return localBitmap;
          }
        }
      }
      finally
      {
      }
      try
      {
        paramInputStream.close();
        throw localObject;
      }
      catch (IOException localIOException1)
      {
        while (true)
          localIOException1.printStackTrace();
      }
      label582: paramInputStream = localXulBufferedInputStream;
    }
  }

  public static void recycleBitmap(Bitmap paramBitmap)
  {
    if (paramBitmap == null);
    int i;
    do
    {
      do
        return;
      while ((!paramBitmap.isMutable()) || (paramBitmap.isRecycled()));
      i = calBitmapPixelsCount(paramBitmap);
    }
    while ((i + _totalBitmapCacheSize >= 25165824) || (calBitmapByteSize(paramBitmap) <= 0));
    ArrayList localArrayList = _recycledBitmapQueue;
    int j = 0;
    for (int k = 0; ; k++)
    {
      try
      {
        int m = _recycledBitmapQueue.size();
        if ((k < m) && (j < 72))
        {
          if (i != calBitmapPixelsCount((Bitmap)((Pair)_recycledBitmapQueue.get(k)).second))
            continue;
          j++;
          continue;
        }
        if (j >= 72)
          return;
      }
      finally
      {
      }
      _recycledBitmapQueue.add(Pair.create(Long.valueOf(5000L + XulUtils.timestamp()), paramBitmap));
      _totalBitmapCacheSize = i + _totalBitmapCacheSize;
      return;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Graphics.BitmapTools
 * JD-Core Version:    0.6.2
 */