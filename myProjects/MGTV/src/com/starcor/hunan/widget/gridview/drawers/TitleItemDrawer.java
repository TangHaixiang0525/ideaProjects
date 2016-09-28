package com.starcor.hunan.widget.gridview.drawers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import com.starcor.hunan.widget.gridview.ItemData;
import com.starcor.hunan.widget.gridview.Properties.BitmapHolder;
import com.starcor.hunan.widget.gridview.Properties.BooleanHolder;
import com.starcor.hunan.widget.gridview.Properties.OnItemDrawingListener;
import com.starcor.hunan.widget.gridview.ViewPainter.MarqueeTextOffsetListener;

public class TitleItemDrawer extends ItemDrawer
{
  Paint backgroundPaint;
  Paint titlePaint = new Paint();

  public TitleItemDrawer()
  {
    this.titlePaint.setAntiAlias(true);
    this.titlePaint.setSubpixelText(true);
    this.backgroundPaint = new Paint();
  }

  public void drawItem(Canvas paramCanvas, ItemData paramItemData, Rect paramRect, Paint.Align paramAlign, int paramInt1, int paramInt2, Typeface paramTypeface1, int paramInt3, int paramInt4, Typeface paramTypeface2, Bitmap paramBitmap1, Bitmap paramBitmap2, Properties.OnItemDrawingListener paramOnItemDrawingListener, ViewPainter.MarqueeTextOffsetListener paramMarqueeTextOffsetListener, Bitmap paramBitmap3, Bitmap paramBitmap4)
  {
    this.titlePaint.setTextSize(paramInt1);
    this.titlePaint.setColor(paramInt2);
    Rect localRect1;
    SpannableStringBuilder localSpannableStringBuilder;
    if (paramTypeface1.isBold())
    {
      this.titlePaint.setFakeBoldText(true);
      localRect1 = new Rect(0, 0, getTextWidth(this.titlePaint, paramItemData.getTitle()), getTextHeight(this.titlePaint));
      determineTextRectForAlign(paramAlign, paramRect, localRect1);
      Rect localRect2 = new Rect(paramRect);
      localSpannableStringBuilder = new SpannableStringBuilder(paramItemData.getTitle());
      Properties.BooleanHolder localBooleanHolder = new Properties.BooleanHolder(false);
      Properties.BitmapHolder localBitmapHolder = new Properties.BitmapHolder(paramBitmap2);
      if (paramOnItemDrawingListener != null)
      {
        paramOnItemDrawingListener.onItemDraw(paramRect, null, localRect1, null, localRect2, paramAlign, paramInt1, paramInt2, paramInt3, paramInt4, localBitmapHolder, null, localSpannableStringBuilder, null, paramItemData.getPageIndex(), null, localBooleanHolder, paramItemData.getData());
        paramBitmap2 = localBitmapHolder.getValue();
      }
      if (paramBitmap2 != null)
      {
        Paint localPaint = this.backgroundPaint;
        paramCanvas.drawBitmap(paramBitmap2, null, localRect2, localPaint);
      }
      paramCanvas.save();
      paramCanvas.clipRect(paramRect);
      if (!paramItemData.isFocused())
        break label479;
      if (((localRect1.right <= paramRect.right) && (!localBooleanHolder.getValue())) || (paramMarqueeTextOffsetListener == null))
        break label443;
      String str1 = paramItemData.getTitle() + "        ";
      int i = getTextWidth(this.titlePaint, str1);
      paramMarqueeTextOffsetListener.startMarquee(2147483647, i, paramItemData);
      String str2 = str1 + paramItemData.getTitle();
      localRect1.right = (i + localRect1.width() + localRect1.left);
      drawMarqueeText(paramCanvas, str2, 0, str2.length(), localRect1, paramMarqueeTextOffsetListener.getCurrentOffset(), this.titlePaint);
    }
    while (true)
    {
      paramCanvas.restore();
      paramCanvas.save();
      paramCanvas.clipRect(paramRect);
      if (paramBitmap3 != null)
        paramCanvas.drawBitmap(paramBitmap3, paramRect.left, paramRect.bottom - paramBitmap3.getHeight(), this.backgroundPaint);
      if (paramBitmap4 != null)
        paramCanvas.drawBitmap(paramBitmap4, paramRect.right - paramBitmap4.getWidth(), paramRect.top, this.backgroundPaint);
      paramCanvas.restore();
      return;
      this.titlePaint.setFakeBoldText(false);
      break;
      label443: if (paramMarqueeTextOffsetListener != null)
        paramMarqueeTextOffsetListener.stopMarquee();
      drawText(paramCanvas, paramItemData.getTitle(), 0, paramItemData.getTitle().length(), localRect1, this.titlePaint);
      continue;
      label479: drawColorfulSpannableString(paramCanvas, this.titlePaint, localSpannableStringBuilder, paramInt2, localRect1);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.gridview.drawers.TitleItemDrawer
 * JD-Core Version:    0.6.2
 */