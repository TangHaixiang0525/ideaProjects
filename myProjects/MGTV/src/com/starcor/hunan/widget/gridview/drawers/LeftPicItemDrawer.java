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

public class LeftPicItemDrawer extends ItemDrawer
{
  Paint backgroundPaint;
  Paint picPaint;
  Paint titlePaint = new Paint();

  public LeftPicItemDrawer()
  {
    this.titlePaint.setAntiAlias(true);
    this.titlePaint.setSubpixelText(true);
    this.backgroundPaint = new Paint();
    this.picPaint = new Paint();
  }

  public void drawItem(Canvas paramCanvas, ItemData paramItemData, Rect paramRect, Paint.Align paramAlign, int paramInt1, int paramInt2, Typeface paramTypeface1, int paramInt3, int paramInt4, Typeface paramTypeface2, Bitmap paramBitmap1, Bitmap paramBitmap2, Properties.OnItemDrawingListener paramOnItemDrawingListener, ViewPainter.MarqueeTextOffsetListener paramMarqueeTextOffsetListener, Bitmap paramBitmap3, Bitmap paramBitmap4)
  {
    this.titlePaint.setTextSize(paramInt1);
    this.titlePaint.setColor(paramInt2);
    Rect localRect1;
    SpannableStringBuilder localSpannableStringBuilder;
    boolean bool;
    if (paramTypeface1.isBold())
    {
      this.titlePaint.setFakeBoldText(true);
      localRect1 = new Rect(0, 0, getTextWidth(this.titlePaint, paramItemData.getTitle()), getTextHeight(this.titlePaint));
      int i = paramRect.centerY() - paramItemData.getPicture().getHeight() / 2;
      Rect localRect2 = new Rect(paramRect.left, i, paramRect.left + paramItemData.getPicture().getWidth(), i + paramItemData.getPicture().getHeight());
      determineTextRectForAlign(paramAlign, new Rect(localRect2.right, paramRect.top, paramRect.right, paramRect.bottom), localRect1);
      Rect localRect3 = new Rect(paramRect);
      localSpannableStringBuilder = new SpannableStringBuilder(paramItemData.getTitle());
      if (paramItemData.getPicture() == null)
        break label552;
      bool = true;
      label184: Properties.BooleanHolder localBooleanHolder = new Properties.BooleanHolder(bool);
      Properties.BitmapHolder localBitmapHolder = new Properties.BitmapHolder(paramBitmap2);
      if (paramOnItemDrawingListener != null)
      {
        paramOnItemDrawingListener.onItemDraw(paramRect, null, localRect1, null, localRect3, paramAlign, paramInt1, paramInt2, paramInt3, paramInt4, localBitmapHolder, null, localSpannableStringBuilder, null, paramItemData.getPageIndex(), localBooleanHolder, null, paramItemData.getData());
        paramBitmap2 = localBitmapHolder.getValue();
      }
      paramCanvas.save();
      paramCanvas.clipRect(paramRect);
      if (paramBitmap2 != null)
      {
        Paint localPaint = this.backgroundPaint;
        paramCanvas.drawBitmap(paramBitmap2, null, localRect3, localPaint);
      }
      if ((paramItemData.getPicture() != null) && (localBooleanHolder.getValue()))
      {
        localRect2.offset(paramItemData.getPicture().getWidth() / 2, 0);
        paramCanvas.drawBitmap(paramItemData.getPicture(), null, localRect2, this.picPaint);
      }
      if (!paramItemData.isFocused())
        break label594;
      if ((localRect1.right <= paramRect.right) || (paramMarqueeTextOffsetListener == null))
        break label558;
      String str1 = paramItemData.getTitle() + "        ";
      int j = getTextWidth(this.titlePaint, str1);
      paramMarqueeTextOffsetListener.startMarquee(2147483647, j, paramItemData);
      String str2 = str1 + paramItemData.getTitle();
      localRect1.right = (j + localRect1.width() + localRect1.left);
      drawMarqueeText(paramCanvas, str2, 0, str2.length(), localRect1, paramMarqueeTextOffsetListener.getCurrentOffset(), this.titlePaint);
    }
    while (true)
    {
      if (paramBitmap3 != null)
        paramCanvas.drawBitmap(paramBitmap3, paramRect.left, paramRect.bottom - paramBitmap3.getHeight(), this.backgroundPaint);
      if (paramBitmap4 != null)
        paramCanvas.drawBitmap(paramBitmap4, paramRect.right - paramBitmap4.getWidth(), paramRect.top, this.backgroundPaint);
      paramCanvas.restore();
      return;
      this.titlePaint.setFakeBoldText(false);
      break;
      label552: bool = false;
      break label184;
      label558: if (paramMarqueeTextOffsetListener != null)
        paramMarqueeTextOffsetListener.stopMarquee();
      drawText(paramCanvas, paramItemData.getTitle(), 0, paramItemData.getTitle().length(), localRect1, this.titlePaint);
      continue;
      label594: drawColorfulSpannableString(paramCanvas, this.titlePaint, localSpannableStringBuilder, paramInt2, localRect1);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.gridview.drawers.LeftPicItemDrawer
 * JD-Core Version:    0.6.2
 */