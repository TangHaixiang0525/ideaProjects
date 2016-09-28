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

public class LeftPicWithDetailOnLeftItemDrawer extends ItemDrawer
{
  Paint backgroundPaint;
  Paint detailPaint;
  Paint picPaint;
  Paint titlePaint = new Paint();

  public LeftPicWithDetailOnLeftItemDrawer()
  {
    this.titlePaint.setAntiAlias(true);
    this.titlePaint.setSubpixelText(true);
    this.backgroundPaint = new Paint();
    this.detailPaint = new Paint();
    this.detailPaint.setAntiAlias(true);
    this.titlePaint.setSubpixelText(true);
    this.picPaint = new Paint();
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
    boolean bool;
    if (paramTypeface1.isBold())
    {
      this.titlePaint.setFakeBoldText(true);
      if (!paramTypeface2.isBold())
        break label792;
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
      int i = paramRect.centerY() - paramItemData.getPicture().getHeight() / 2;
      Rect localRect4 = new Rect(paramRect.left + App.Operation(10.0F), i, paramRect.left + paramItemData.getPicture().getWidth() + App.Operation(10.0F), i + paramItemData.getPicture().getHeight());
      determineTextRectForAlign(paramAlign, paramRect, localRect1);
      determineTextRectForAlign(paramAlign, paramRect, localRect2);
      localRect2.offsetTo(localRect4.right + App.Operation(10.0F), localRect2.top);
      localRect1.offsetTo(localRect2.right + App.Operation(10.0F), localRect1.top);
      Rect localRect5 = new Rect(localRect2);
      localSpannableStringBuilder1 = new SpannableStringBuilder(paramItemData.getTitle());
      localSpannableStringBuilder2 = new SpannableStringBuilder(paramItemData.getDetail());
      if (paramItemData.getPicture() == null)
        break label803;
      bool = true;
      label354: Properties.BooleanHolder localBooleanHolder = new Properties.BooleanHolder(bool);
      Properties.BitmapHolder localBitmapHolder = new Properties.BitmapHolder(paramBitmap2);
      if (paramOnItemDrawingListener != null)
      {
        paramOnItemDrawingListener.onItemDraw(paramRect, localRect4, localRect1, localRect5, localRect3, paramAlign, paramInt1, paramInt2, paramInt3, paramInt4, localBitmapHolder, paramItemData.getPicture(), localSpannableStringBuilder1, localSpannableStringBuilder2, paramItemData.getPageIndex(), localBooleanHolder, null, paramItemData.getData());
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
        paramCanvas.drawBitmap(paramItemData.getPicture(), null, localRect4, this.picPaint);
      if (paramBitmap1 != null)
        paramCanvas.drawBitmap(paramBitmap1, localRect5.centerX() - paramBitmap1.getWidth() / 2, localRect5.centerY() - paramBitmap1.getHeight() / 2, this.backgroundPaint);
      if (!paramItemData.isFocused())
        break label845;
      if (paramRect.width() - localRect5.width() - localRect4.width() - App.Operation(30.0F) >= localRect1.width())
        break label809;
      String str1 = paramItemData.getTitle() + "        ";
      int j = getTextWidth(this.titlePaint, str1);
      paramMarqueeTextOffsetListener.startMarquee(2147483647, j, paramItemData);
      String str2 = str1 + paramItemData.getTitle();
      localRect1.right = (j + localRect1.width() + localRect1.left);
      drawMarqueeText(paramCanvas, str2, 0, str2.length(), localRect1, paramMarqueeTextOffsetListener.getCurrentOffset(), this.titlePaint);
      label691: drawText(paramCanvas, paramItemData.getDetail(), 0, paramItemData.getDetail().length(), localRect2, this.detailPaint);
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
      label792: this.detailPaint.setFakeBoldText(false);
      break label70;
      label803: bool = false;
      break label354;
      label809: if (paramMarqueeTextOffsetListener != null)
        paramMarqueeTextOffsetListener.stopMarquee();
      drawText(paramCanvas, paramItemData.getTitle(), 0, paramItemData.getTitle().length(), localRect1, this.titlePaint);
      break label691;
      label845: drawColorfulSpannableString(paramCanvas, this.titlePaint, localSpannableStringBuilder1, paramInt2, localRect1);
      drawColorfulSpannableString(paramCanvas, this.detailPaint, localSpannableStringBuilder2, paramInt4, localRect2);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.gridview.drawers.LeftPicWithDetailOnLeftItemDrawer
 * JD-Core Version:    0.6.2
 */