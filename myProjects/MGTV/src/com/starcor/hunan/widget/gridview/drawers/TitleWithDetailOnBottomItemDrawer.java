package com.starcor.hunan.widget.gridview.drawers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import com.starcor.hunan.App;
import com.starcor.hunan.widget.gridview.ItemData;
import com.starcor.hunan.widget.gridview.Properties.BitmapHolder;
import com.starcor.hunan.widget.gridview.Properties.OnItemDrawingListener;
import com.starcor.hunan.widget.gridview.ViewPainter.MarqueeTextOffsetListener;

public class TitleWithDetailOnBottomItemDrawer extends ItemDrawer
{
  Paint backgroundPaint;
  Paint detailPaint;
  Paint titlePaint = new Paint();

  public TitleWithDetailOnBottomItemDrawer()
  {
    this.titlePaint.setAntiAlias(true);
    this.titlePaint.setSubpixelText(true);
    this.backgroundPaint = new Paint();
    this.detailPaint = new Paint();
    this.detailPaint.setAntiAlias(true);
    this.titlePaint.setSubpixelText(true);
  }

  public void drawItem(Canvas paramCanvas, ItemData paramItemData, Rect paramRect, Paint.Align paramAlign, int paramInt1, int paramInt2, Typeface paramTypeface1, int paramInt3, int paramInt4, Typeface paramTypeface2, Bitmap paramBitmap1, Bitmap paramBitmap2, Properties.OnItemDrawingListener paramOnItemDrawingListener, ViewPainter.MarqueeTextOffsetListener paramMarqueeTextOffsetListener, Bitmap paramBitmap3, Bitmap paramBitmap4)
  {
    this.titlePaint.setTextSize(paramInt1);
    this.titlePaint.setColor(paramInt2);
    this.detailPaint.setTextSize(paramInt3);
    this.detailPaint.setColor(paramInt4);
    label70: Rect localRect1;
    Rect localRect2;
    SpannableStringBuilder localSpannableStringBuilder1;
    SpannableStringBuilder localSpannableStringBuilder2;
    if (paramTypeface1.isBold())
    {
      this.titlePaint.setFakeBoldText(true);
      if (!paramTypeface2.isBold())
        break label622;
      this.detailPaint.setFakeBoldText(true);
      localRect1 = new Rect();
      localRect2 = new Rect();
      localRect2.top = 0;
      localRect1.top = 0;
      localRect2.left = 0;
      localRect1.left = 0;
      localRect1.right = getTextWidth(this.titlePaint, paramItemData.getTitle());
      localRect1.bottom = getTextHeight(this.titlePaint);
      localRect2.right = getTextWidth(this.detailPaint, paramItemData.getDetail());
      localRect2.bottom = getTextHeight(this.detailPaint);
      determineTextRectForAlign(paramAlign, paramRect, localRect1);
      determineTextRectForAlign(paramAlign, paramRect, localRect2);
      localRect1.offsetTo(localRect1.left, paramRect.centerY() - (localRect1.height() + localRect2.height()) / 2);
      localRect2.offsetTo(localRect2.left, localRect1.bottom + App.Operation(5.0F));
      Rect localRect3 = new Rect(paramRect);
      localSpannableStringBuilder1 = new SpannableStringBuilder(paramItemData.getTitle());
      localSpannableStringBuilder2 = new SpannableStringBuilder(paramItemData.getDetail());
      Properties.BitmapHolder localBitmapHolder = new Properties.BitmapHolder(paramBitmap2);
      if (paramOnItemDrawingListener != null)
      {
        paramOnItemDrawingListener.onItemDraw(paramRect, null, localRect1, localRect2, localRect3, paramAlign, paramInt1, paramInt2, paramInt3, paramInt4, localBitmapHolder, null, localSpannableStringBuilder1, localSpannableStringBuilder2, paramItemData.getPageIndex(), null, null, paramItemData.getData());
        paramBitmap2 = localBitmapHolder.getValue();
      }
      paramCanvas.save();
      paramCanvas.clipRect(paramRect);
      if (paramBitmap2 != null)
      {
        Paint localPaint = this.backgroundPaint;
        paramCanvas.drawBitmap(paramBitmap2, null, localRect3, localPaint);
      }
      if (paramBitmap1 != null)
        paramCanvas.drawBitmap(paramBitmap1, null, localRect2, this.backgroundPaint);
      if (!paramItemData.isFocused())
        break label661;
      if (paramRect.width() - App.Operation(10.0F) >= localRect1.width())
        break label633;
      String str1 = paramItemData.getTitle() + "        ";
      int i = getTextWidth(this.titlePaint, str1);
      paramMarqueeTextOffsetListener.startMarquee(2147483647, i, paramItemData);
      String str2 = str1 + paramItemData.getTitle();
      localRect1.right = (i + localRect1.width() + localRect1.left);
      drawMarqueeText(paramCanvas, str2, 0, str2.length(), localRect1, paramMarqueeTextOffsetListener.getCurrentOffset(), this.titlePaint);
      label529: drawColorfulSpannableString(paramCanvas, this.detailPaint, localSpannableStringBuilder2, paramInt4, localRect2);
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
      label622: this.detailPaint.setFakeBoldText(false);
      break label70;
      label633: if (paramMarqueeTextOffsetListener != null)
        paramMarqueeTextOffsetListener.stopMarquee();
      drawColorfulSpannableString(paramCanvas, this.titlePaint, localSpannableStringBuilder1, paramInt2, localRect1);
      break label529;
      label661: if (paramItemData.isSelected())
      {
        drawColorfulSpannableString(paramCanvas, this.titlePaint, localSpannableStringBuilder1, paramInt2, localRect1);
        drawColorfulSpannableString(paramCanvas, this.detailPaint, localSpannableStringBuilder2, paramInt4, localRect2);
      }
      else
      {
        drawColorfulSpannableString(paramCanvas, this.titlePaint, localSpannableStringBuilder1, paramInt2, localRect1);
        drawColorfulSpannableString(paramCanvas, this.detailPaint, localSpannableStringBuilder2, paramInt4, localRect2);
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.gridview.drawers.TitleWithDetailOnBottomItemDrawer
 * JD-Core Version:    0.6.2
 */