package com.starcor.media.player;

import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.RectF;
import com.starcor.hunan.App;

public class MplayerTools
{
  public static float getTextDrawingBaseline(Paint paramPaint, RectF paramRectF)
  {
    if ((paramPaint == null) || (paramRectF == null))
      return 0.0F;
    Paint.FontMetricsInt localFontMetricsInt = paramPaint.getFontMetricsInt();
    return paramRectF.top + (paramRectF.bottom - paramRectF.top - localFontMetricsInt.bottom + localFontMetricsInt.top) / 2.0F - localFontMetricsInt.top;
  }

  public static int getTextDrawingBaseline(Paint paramPaint, Rect paramRect)
  {
    if ((paramPaint == null) || (paramRect == null))
      return 0;
    Paint.FontMetricsInt localFontMetricsInt = paramPaint.getFontMetricsInt();
    return paramRect.top + (paramRect.bottom - paramRect.top - localFontMetricsInt.bottom + localFontMetricsInt.top) / 2 - localFontMetricsInt.top;
  }

  public static char[] getTextWidthPos(Paint paramPaint, String paramString, int paramInt)
  {
    char[] arrayOfChar = null;
    int j;
    float[] arrayOfFloat;
    float f;
    if (paramString != null)
    {
      int i = paramString.length();
      arrayOfChar = null;
      if (i > 0)
      {
        j = paramString.length();
        arrayOfFloat = new float[j];
        paramPaint.getTextWidths(paramString, arrayOfFloat);
        Rect localRect = new Rect();
        paramPaint.getTextBounds("...", 0, 3, localRect);
        f = localRect.width();
      }
    }
    for (int k = 0; ; k++)
    {
      if ((k >= j) || (f + arrayOfFloat[k] >= paramInt))
      {
        arrayOfChar = new char[k + 3];
        paramString.getChars(0, k, arrayOfChar, 0);
        arrayOfChar[(k + 0)] = '.';
        arrayOfChar[(k + 1)] = '.';
        arrayOfChar[(k + 2)] = '.';
        return arrayOfChar;
      }
      f += arrayOfFloat[k];
    }
  }

  public static String matchTextByParams(String paramString, int paramInt, float paramFloat, boolean paramBoolean)
  {
    Paint localPaint = new Paint();
    Rect localRect = new Rect();
    localPaint.setTextSize(paramFloat);
    localPaint.getTextBounds(paramString, 0, paramString.length(), localRect);
    if (localRect.width() > paramInt - App.Operation(10.0F))
      return matchTextByParams(paramString.substring(0, -1 + paramString.length()), paramInt, paramFloat, true);
    StringBuilder localStringBuilder = new StringBuilder().append(paramString);
    if (paramBoolean);
    for (String str = "..."; ; str = "")
      return str;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerTools
 * JD-Core Version:    0.6.2
 */