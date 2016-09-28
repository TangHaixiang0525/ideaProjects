package com.starcor.hunan.widget.gridview.drawers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import com.starcor.hunan.App;
import com.starcor.hunan.widget.gridview.ItemData;
import com.starcor.hunan.widget.gridview.Properties.OnItemDrawingListener;
import com.starcor.hunan.widget.gridview.ViewPainter.MarqueeTextOffsetListener;

public abstract class ItemDrawer
{
  void determineTextRectForAlign(Paint.Align paramAlign, Rect paramRect1, Rect paramRect2)
  {
    int i;
    if (paramAlign == Paint.Align.LEFT)
      i = paramRect1.left + App.Operation(20.0F);
    while (true)
    {
      paramRect2.offsetTo(i, paramRect1.centerY() - paramRect2.height() / 2);
      return;
      if (paramAlign == Paint.Align.CENTER)
        i = paramRect1.left + (paramRect1.width() - paramRect2.width()) / 2;
      else
        i = paramRect1.right - paramRect2.width() - App.Operation(5.0F);
    }
  }

  protected void drawColorfulSpannableString(Canvas paramCanvas, Paint paramPaint, SpannableStringBuilder paramSpannableStringBuilder, int paramInt, Rect paramRect)
  {
    Object[] arrayOfObject = paramSpannableStringBuilder.getSpans(0, paramSpannableStringBuilder.length(), ForegroundColorSpan.class);
    int i = 0;
    if (arrayOfObject != null)
    {
      int k = arrayOfObject.length;
      for (int m = 0; m < k; m++)
      {
        Object localObject = arrayOfObject[m];
        if ((localObject instanceof ForegroundColorSpan))
        {
          ForegroundColorSpan localForegroundColorSpan = (ForegroundColorSpan)localObject;
          int n = paramSpannableStringBuilder.getSpanStart(localForegroundColorSpan);
          i = paramSpannableStringBuilder.getSpanEnd(localForegroundColorSpan);
          paramPaint.setColor(localForegroundColorSpan.getForegroundColor());
          drawText(paramCanvas, paramSpannableStringBuilder, n, i, paramRect, paramPaint);
          paramRect.offset((int)Math.ceil(paramPaint.measureText(paramSpannableStringBuilder, n, i)), 0);
        }
      }
    }
    paramPaint.setColor(paramInt);
    int j = paramSpannableStringBuilder.length();
    drawText(paramCanvas, paramSpannableStringBuilder, i, j, paramRect, paramPaint);
  }

  public abstract void drawItem(Canvas paramCanvas, ItemData paramItemData, Rect paramRect, Paint.Align paramAlign, int paramInt1, int paramInt2, Typeface paramTypeface1, int paramInt3, int paramInt4, Typeface paramTypeface2, Bitmap paramBitmap1, Bitmap paramBitmap2, Properties.OnItemDrawingListener paramOnItemDrawingListener, ViewPainter.MarqueeTextOffsetListener paramMarqueeTextOffsetListener, Bitmap paramBitmap3, Bitmap paramBitmap4);

  public void drawItem(Canvas paramCanvas, ItemData paramItemData, Rect paramRect, Paint.Align paramAlign, int paramInt1, int paramInt2, Typeface paramTypeface, Bitmap paramBitmap, Properties.OnItemDrawingListener paramOnItemDrawingListener)
  {
    drawItem(paramCanvas, paramItemData, paramRect, paramAlign, paramInt1, paramInt2, paramTypeface, 0, 0, Typeface.DEFAULT, null, paramBitmap, paramOnItemDrawingListener, null, null, null);
  }

  protected void drawMarqueeText(Canvas paramCanvas, CharSequence paramCharSequence, int paramInt1, int paramInt2, Rect paramRect, int paramInt3, Paint paramPaint)
  {
    paramCanvas.save();
    paramCanvas.clipRect(paramRect);
    paramCanvas.translate(paramInt3, 0.0F);
    drawText(paramCanvas, paramCharSequence, paramInt1, paramInt2, paramRect, paramPaint);
    paramCanvas.restore();
  }

  protected void drawMarqueeTextV2(Canvas paramCanvas, CharSequence paramCharSequence, int paramInt1, int paramInt2, Rect paramRect, int paramInt3, Paint paramPaint)
  {
    paramCanvas.save();
    paramCanvas.clipRect(paramRect);
    Paint.FontMetrics localFontMetrics = paramPaint.getFontMetrics();
    int i = paramRect.bottom - (int)localFontMetrics.bottom;
    paramCanvas.drawText(paramCharSequence, paramInt1, paramInt2, paramInt3, i, paramPaint);
    paramCanvas.restore();
  }

  protected void drawText(Canvas paramCanvas, CharSequence paramCharSequence, int paramInt1, int paramInt2, Rect paramRect, Paint paramPaint)
  {
    paramCanvas.save();
    paramCanvas.clipRect(paramRect);
    Paint.FontMetrics localFontMetrics = paramPaint.getFontMetrics();
    int i = paramRect.left;
    int j = paramRect.bottom - (int)localFontMetrics.bottom;
    paramCanvas.drawText(paramCharSequence, paramInt1, paramInt2, i, j, paramPaint);
    paramCanvas.restore();
  }

  protected int getTextHeight(Paint paramPaint)
  {
    Paint.FontMetrics localFontMetrics = paramPaint.getFontMetrics();
    return (int)(localFontMetrics.bottom - localFontMetrics.top);
  }

  protected int getTextWidth(Paint paramPaint, String paramString)
  {
    return (int)Math.ceil(paramPaint.measureText(paramString));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.gridview.drawers.ItemDrawer
 * JD-Core Version:    0.6.2
 */