package com.starcor.xul.Graphics;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulUtils.ticketMarker;
import java.io.InputStream;

public class XulSVGDrawable extends XulDrawable
{
  private Bitmap _cachedBmp;
  private SVG _svg;

  public static XulDrawable buildSVGDrawable(InputStream paramInputStream, String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    if (paramInputStream == null)
      return null;
    XulSVGDrawable localXulSVGDrawable = new XulSVGDrawable();
    float f1;
    float f2;
    float f4;
    int i;
    int j;
    while (true)
    {
      try
      {
        localXulSVGDrawable._svg = SVG.getFromInputStream(paramInputStream);
        f1 = 0.0F;
        f2 = 0.0F;
        f3 = 1.0F;
        f4 = 1.0F;
        RectF localRectF = localXulSVGDrawable._svg.getDocumentViewBox();
        if (localRectF == null)
        {
          float f7 = localXulSVGDrawable._svg.getDocumentWidth();
          float f8 = localXulSVGDrawable._svg.getDocumentHeight();
          if ((f7 <= 0.0F) && (f8 <= 0.0F))
          {
            f7 = 256.0F;
            f8 = 256.0F;
            localXulSVGDrawable._svg.setDocumentWidth(f7);
            localXulSVGDrawable._svg.setDocumentHeight(f8);
          }
          localXulSVGDrawable._svg.setDocumentViewBox(0.0F, 0.0F, f7, f8);
          localRectF = localXulSVGDrawable._svg.getDocumentViewBox();
        }
        i = XulUtils.roundToInt(localRectF.width());
        j = XulUtils.roundToInt(localRectF.height());
        if ((paramInt1 == 0) && (paramInt2 == 0))
        {
          paramInt1 = i;
          paramInt2 = j;
          XulUtils.ticketMarker localticketMarker = new XulUtils.ticketMarker("BENCH!!!", true);
          localticketMarker.mark();
          Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
          localXulSVGDrawable._cachedBmp = Bitmap.createBitmap(paramInt1, paramInt2, localConfig);
          Canvas localCanvas = new Canvas(localXulSVGDrawable._cachedBmp);
          localCanvas.translate(-f1, -f2);
          localCanvas.scale(f3, f4);
          localXulSVGDrawable._svg.renderToCanvas(localCanvas, localRectF);
          localticketMarker.mark("svg");
          Log.d("BENCH", localticketMarker.toString());
          localXulSVGDrawable._url = paramString1;
          localXulSVGDrawable._key = paramString2;
          return localXulSVGDrawable;
        }
      }
      catch (SVGParseException localSVGParseException)
      {
        localSVGParseException.printStackTrace();
        return null;
      }
      if (paramInt1 == 0)
      {
        f4 = paramInt2 / j;
        f3 = f4;
        paramInt1 = XulUtils.roundToInt(f3 * i);
        f1 = 0.0F;
        f2 = 0.0F;
      }
      else
      {
        if (paramInt2 != 0)
          break;
        f4 = paramInt1 / i;
        f3 = f4;
        paramInt2 = XulUtils.roundToInt(f3 * j);
        f1 = 0.0F;
        f2 = 0.0F;
      }
    }
    float f5 = paramInt1 / i;
    float f6 = paramInt2 / j;
    int k;
    int m;
    if (f5 > f6)
    {
      k = (int)(f5 * i);
      m = (int)(f5 * j);
      f4 = f5;
    }
    for (float f3 = f5; ; f3 = f6)
    {
      f1 = (k - paramInt1) / 2.0F;
      f2 = (m - paramInt2) / 2.0F;
      break;
      k = (int)(f6 * i);
      m = (int)(f6 * j);
      f4 = f6;
    }
  }

  public boolean draw(Canvas paramCanvas, Rect paramRect1, Rect paramRect2, Paint paramPaint)
  {
    paramCanvas.drawBitmap(this._cachedBmp, paramRect1, paramRect2, paramPaint);
    return true;
  }

  public boolean draw(Canvas paramCanvas, Rect paramRect, RectF paramRectF, Paint paramPaint)
  {
    paramCanvas.drawBitmap(this._cachedBmp, paramRect, paramRectF, paramPaint);
    return true;
  }

  public int getHeight()
  {
    return this._cachedBmp.getHeight();
  }

  public int getWidth()
  {
    return this._cachedBmp.getWidth();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Graphics.XulSVGDrawable
 * JD-Core Version:    0.6.2
 */