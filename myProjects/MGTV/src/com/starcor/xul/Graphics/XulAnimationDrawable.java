package com.starcor.xul.Graphics;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.starcor.xul.Utils.XulCachedHashMap;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import pl.droidsonroids.gif.GifDrawable;

public abstract class XulAnimationDrawable extends XulDrawable
{
  public static XulDrawable buildAnimation(InputStream paramInputStream, String paramString1, String paramString2)
  {
    Object localObject;
    if (paramInputStream == null)
      localObject = null;
    do
    {
      return localObject;
      localObject = XulFrameAnimationDrawable.buildAnimation(new AnimationPackage(paramInputStream));
    }
    while (localObject == null);
    ((XulFrameAnimationDrawable)localObject)._url = paramString1;
    ((XulFrameAnimationDrawable)localObject)._key = paramString2;
    return localObject;
  }

  public static XulDrawable buildAnimation(GifDrawable paramGifDrawable, String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    Object localObject;
    if (paramGifDrawable == null)
      localObject = null;
    do
    {
      return localObject;
      localObject = XulGifAnimationDrawable.buildAnimation(paramGifDrawable, paramInt1, paramInt2);
    }
    while (localObject == null);
    ((XulGifAnimationDrawable)localObject)._url = paramString1;
    ((XulGifAnimationDrawable)localObject)._key = paramString2;
    return localObject;
  }

  public abstract AnimationDrawingContext createDrawingCtx();

  public abstract boolean drawAnimation(AnimationDrawingContext paramAnimationDrawingContext, XulDC paramXulDC, Rect paramRect, Paint paramPaint);

  public abstract boolean drawAnimation(AnimationDrawingContext paramAnimationDrawingContext, XulDC paramXulDC, RectF paramRectF, Paint paramPaint);

  public static abstract class AnimationDrawingContext
  {
    public abstract boolean isAnimationFinished();

    public abstract void reset();

    public abstract boolean updateAnimation(long paramLong);
  }

  static class AnimationPackage
  {
    ArrayList<String> _aniDesc = new ArrayList();
    XulCachedHashMap<String, Object> _contents = new XulCachedHashMap();

    public AnimationPackage(InputStream paramInputStream)
    {
      ZipInputStream localZipInputStream = new ZipInputStream(paramInputStream);
      byte[] arrayOfByte1 = new byte[1024];
      try
      {
        localZipEntry = localZipInputStream.getNextEntry();
        if (localZipEntry != null)
        {
          int i = (int)localZipEntry.getSize();
          if (i <= 0)
            break label184;
          localByteArrayOutputStream = new ByteArrayOutputStream(i);
          while (true)
          {
            label76: int j = localZipInputStream.read(arrayOfByte1);
            if (j <= 0)
              break;
            localByteArrayOutputStream.write(arrayOfByte1, 0, j);
          }
        }
      }
      catch (Exception localException1)
      {
        while (true)
        {
          ZipEntry localZipEntry;
          ByteArrayOutputStream localByteArrayOutputStream;
          localException1.printStackTrace();
          try
          {
            localZipInputStream.close();
            label111: arrayOfByte2 = (byte[])this._contents.get("ani.txt");
            if (arrayOfByte2 == null);
          }
          catch (Exception localException2)
          {
            try
            {
              byte[] arrayOfByte2;
              BufferedReader localBufferedReader = new BufferedReader(new StringReader(new String(arrayOfByte2)));
              while (true)
              {
                String str1;
                if (localBufferedReader.ready())
                {
                  str1 = localBufferedReader.readLine();
                  if (str1 != null);
                }
                else
                {
                  localBufferedReader.close();
                  return;
                  label184: localByteArrayOutputStream = new ByteArrayOutputStream();
                  break label76;
                  this._contents.put(localZipEntry.getName(), localByteArrayOutputStream.toByteArray());
                  break;
                  localException2 = localException2;
                  localException2.printStackTrace();
                  break label111;
                }
                String str2 = str1.trim();
                if ((!str2.startsWith(";")) && (!str2.isEmpty()))
                  this._aniDesc.add(str2);
              }
            }
            catch (Exception localException3)
            {
              localException3.printStackTrace();
            }
          }
        }
      }
    }

    public ArrayList<String> getAniDesc()
    {
      return this._aniDesc;
    }

    public Bitmap loadFrameImage(String paramString)
    {
      byte[] arrayOfByte = (byte[])this._contents.get(paramString);
      try
      {
        Bitmap localBitmap = BitmapTools.decodeStream(new ByteArrayInputStream(arrayOfByte), Bitmap.Config.ARGB_8888, 0, 0);
        return localBitmap;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return null;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Graphics.XulAnimationDrawable
 * JD-Core Version:    0.6.2
 */