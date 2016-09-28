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
import com.starcor.hunan.widget.gridview.Properties.BooleanHolder;
import com.starcor.hunan.widget.gridview.Properties.OnItemDrawingListener;
import com.starcor.hunan.widget.gridview.ViewPainter.MarqueeTextOffsetListener;

public class TitleWithDetailOnLeftItemDrawer extends ItemDrawer
{
  Paint backgroundPaint;
  Paint detailPaint;
  Paint titlePaint = new Paint();

  public TitleWithDetailOnLeftItemDrawer()
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
    String str2;
    if (paramTypeface1.isBold())
    {
      this.titlePaint.setFakeBoldText(true);
      if (!paramTypeface2.isBold())
        break label739;
      this.detailPaint.setFakeBoldText(true);
      localRect1 = new Rect();
      localRect2 = new Rect();
      Rect localRect3 = new Rect(paramRect);
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
      localRect2.offsetTo(localRect2.left, localRect2.top);
      Rect localRect4 = new Rect(localRect2);
      localSpannableStringBuilder1 = new SpannableStringBuilder(paramItemData.getTitle());
      localSpannableStringBuilder2 = new SpannableStringBuilder(paramItemData.getDetail());
      Properties.BitmapHolder localBitmapHolder = new Properties.BitmapHolder(paramBitmap2);
      Properties.BooleanHolder localBooleanHolder = new Properties.BooleanHolder(false);
      if (paramOnItemDrawingListener != null)
      {
        paramOnItemDrawingListener.onItemDraw(paramRect, null, localRect1, localRect4, localRect3, paramAlign, paramInt1, paramInt2, paramInt3, paramInt4, localBitmapHolder, null, localSpannableStringBuilder1, localSpannableStringBuilder2, paramItemData.getPageIndex(), null, localBooleanHolder, paramItemData.getData());
        paramBitmap2 = localBitmapHolder.getValue();
      }
      localRect2.right = (localRect2.left + getTextWidth(this.detailPaint, localSpannableStringBuilder2.toString()));
      localRect2.offsetTo(localRect4.centerX() - localRect2.width() / 2, localRect2.top);
      paramCanvas.save();
      paramCanvas.clipRect(paramRect);
      if (paramBitmap2 != null)
      {
        Paint localPaint = this.backgroundPaint;
        paramCanvas.drawBitmap(paramBitmap2, null, localRect3, localPaint);
      }
      if (paramBitmap1 != null)
        paramCanvas.drawBitmap(paramBitmap1, localRect4.centerX() - paramBitmap1.getWidth() / 2, localRect4.centerY() - paramBitmap1.getHeight() / 2, this.backgroundPaint);
      if (!paramItemData.isFocused())
        break label809;
      if ((paramRect.width() - localRect4.right >= localRect1.width() + App.Operation(4.0F)) && (!localBooleanHolder.getValue()))
        break label777;
      String str1 = paramItemData.getTitle() + "        ";
      int i = getTextWidth(this.titlePaint, str1);
      paramMarqueeTextOffsetListener.startMarquee(2147483647, i, paramItemData);
      str2 = str1 + paramItemData.getTitle();
      localRect1.right = (i + localRect1.width() + localRect1.left);
      if (!localBooleanHolder.getValue())
        break label750;
      int j = App.OperationHeight(45);
      paramRect.right -= j;
      drawMarqueeTextV2(paramCanvas, str2, 0, str2.length(), localRect1, paramMarqueeTextOffsetListener.getCurrentOffset() + localRect1.width() - App.Operation(10.0F), this.titlePaint);
      label642: drawText(paramCanvas, localSpannableStringBuilder2, 0, localSpannableStringBuilder2.length(), localRect2, this.detailPaint);
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
      label739: this.detailPaint.setFakeBoldText(false);
      break label70;
      label750: drawMarqueeText(paramCanvas, str2, 0, str2.length(), localRect1, paramMarqueeTextOffsetListener.getCurrentOffset(), this.titlePaint);
      break label642;
      label777: if (paramMarqueeTextOffsetListener != null)
        paramMarqueeTextOffsetListener.stopMarquee();
      drawText(paramCanvas, localSpannableStringBuilder1, 0, localSpannableStringBuilder1.length(), localRect1, this.titlePaint);
      break label642;
      label809: drawColorfulSpannableString(paramCanvas, this.titlePaint, localSpannableStringBuilder1, paramInt2, localRect1);
      drawColorfulSpannableString(paramCanvas, this.detailPaint, localSpannableStringBuilder2, paramInt4, localRect2);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.gridview.drawers.TitleWithDetailOnLeftItemDrawer
 * JD-Core Version:    0.6.2
 */