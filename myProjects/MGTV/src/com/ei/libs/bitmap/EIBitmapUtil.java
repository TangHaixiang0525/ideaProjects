package com.ei.libs.bitmap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.starcor.core.utils.Logger;

public class EIBitmapUtil
{
  private static final String TAG = "EIBitmapUtil";
  private static Bitmap.Config defaultConfig = Bitmap.Config.ARGB_8888;
  private static Bitmap.Config noAlphaConfig = Bitmap.Config.RGB_565;

  static
  {
    Logger.i("EIBitmapUtil", "加载库文件bitmaps");
    System.loadLibrary("_All_imgoTV_bitmaps");
  }

  private static void checkWidthHeight(int paramInt1, int paramInt2)
  {
    if (paramInt1 <= 0)
      throw new IllegalArgumentException("width must be > 0");
    if (paramInt2 <= 0)
      throw new IllegalArgumentException("height must be > 0");
  }

  private static void checkXYSign(int paramInt1, int paramInt2)
  {
    if (paramInt1 < 0)
      throw new IllegalArgumentException("x must be >= 0");
    if (paramInt2 < 0)
      throw new IllegalArgumentException("y must be >= 0");
  }

  public static Bitmap createBitmap(int paramInt1, int paramInt2)
  {
    return createBitmap(paramInt1, paramInt2, defaultConfig);
  }

  public static Bitmap createBitmap(int paramInt1, int paramInt2, Bitmap.Config paramConfig)
  {
    Bitmap localBitmap = nativeCreateBitmap(paramInt1, paramInt2, paramConfig, true);
    localBitmap.eraseColor(0);
    return localBitmap;
  }

  public static Bitmap createBitmap(Bitmap paramBitmap)
  {
    Bitmap localBitmap = createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), paramBitmap.getConfig());
    new Canvas(localBitmap).drawBitmap(paramBitmap, new Matrix(), null);
    return localBitmap;
  }

  public static Bitmap createBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Matrix paramMatrix, boolean paramBoolean)
  {
    checkXYSign(paramInt1, paramInt2);
    checkWidthHeight(paramInt3, paramInt4);
    if (paramInt1 + paramInt3 > paramBitmap.getWidth())
      throw new IllegalArgumentException("x + width must be <= bitmap.width()");
    if (paramInt2 + paramInt4 > paramBitmap.getHeight())
      throw new IllegalArgumentException("y + height must be <= bitmap.height()");
    if ((!paramBitmap.isMutable()) && (paramInt1 == 0) && (paramInt2 == 0) && (paramInt3 == paramBitmap.getWidth()) && (paramInt4 == paramBitmap.getHeight()) && ((paramMatrix == null) || (paramMatrix.isIdentity())))
      return paramBitmap;
    Canvas localCanvas = new Canvas();
    Rect localRect = new Rect(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    RectF localRectF1 = new RectF(0.0F, 0.0F, paramInt3, paramInt4);
    Bitmap localBitmap;
    Paint localPaint;
    if ((paramMatrix == null) || (paramMatrix.isIdentity()))
    {
      if (paramBitmap.hasAlpha());
      for (Bitmap.Config localConfig1 = Bitmap.Config.ARGB_8888; ; localConfig1 = Bitmap.Config.RGB_565)
      {
        localBitmap = createBitmap(paramInt3, paramInt4, localConfig1);
        localPaint = null;
        localBitmap.setDensity(paramBitmap.getDensity());
        localCanvas.setBitmap(localBitmap);
        localCanvas.drawBitmap(paramBitmap, localRect, localRectF1, localPaint);
        return localBitmap;
      }
    }
    int i;
    label237: RectF localRectF2;
    int j;
    int k;
    if ((paramBitmap.hasAlpha()) || (!paramMatrix.rectStaysRect()))
    {
      i = 1;
      localRectF2 = new RectF();
      paramMatrix.mapRect(localRectF2, localRectF1);
      j = Math.round(localRectF2.width());
      k = Math.round(localRectF2.height());
      if (i == 0)
        break label371;
    }
    label371: for (Bitmap.Config localConfig2 = Bitmap.Config.ARGB_8888; ; localConfig2 = Bitmap.Config.RGB_565)
    {
      localBitmap = createBitmap(j, k, localConfig2);
      if (i != 0)
        localBitmap.eraseColor(0);
      localCanvas.translate(-localRectF2.left, -localRectF2.top);
      localCanvas.concat(paramMatrix);
      localPaint = new Paint();
      localPaint.setFilterBitmap(paramBoolean);
      if (paramMatrix.rectStaysRect())
        break;
      localPaint.setAntiAlias(true);
      break;
      i = 0;
      break label237;
    }
  }

  public static Bitmap createBitmap(Bitmap paramBitmap, Bitmap.Config paramConfig)
  {
    Bitmap localBitmap = createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), paramConfig);
    new Canvas(localBitmap).drawBitmap(paramBitmap, new Matrix(), null);
    return localBitmap;
  }

  public static Bitmap decodeFile(String paramString)
  {
    Logger.i("EIBitmapUtil", "decodeFile path:" + paramString);
    return decodeFile(paramString, defaultConfig);
  }

  public static Bitmap decodeFile(String paramString, Bitmap.Config paramConfig)
  {
    return nativeDecodeFile(paramString, paramConfig, true);
  }

  public static Bitmap decodeFile(String paramString, Bitmap.Config paramConfig, int paramInt1, int paramInt2)
  {
    Logger.i("EIBitmapUtil", "decodeFile path:" + paramString);
    return nativeDecodeFile(paramString, paramConfig, true);
  }

  public static native Bitmap nativeCreateBitmap(int paramInt1, int paramInt2, Bitmap.Config paramConfig, boolean paramBoolean);

  public static native Bitmap nativeDecodeFile(String paramString, Bitmap.Config paramConfig, boolean paramBoolean);

  public static void setDefaultConfig(Bitmap.Config paramConfig)
  {
    defaultConfig = paramConfig;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.ei.libs.bitmap.EIBitmapUtil
 * JD-Core Version:    0.6.2
 */