package com.starcor.hunan.widget.gridview.drawers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import com.starcor.hunan.App;
import com.starcor.hunan.widget.gridview.ItemData;
import com.starcor.hunan.widget.gridview.Properties.OnItemDrawingListener;
import com.starcor.hunan.widget.gridview.ViewPainter.MarqueeTextOffsetListener;

public class ScrollItemDrawer extends TitleItemDrawer
{
  private Bitmap leftArrow;
  private Bitmap rightArrow;

  public void drawItem(Canvas paramCanvas, ItemData paramItemData, Rect paramRect, Paint.Align paramAlign, int paramInt1, int paramInt2, Typeface paramTypeface1, int paramInt3, int paramInt4, Typeface paramTypeface2, Bitmap paramBitmap1, Bitmap paramBitmap2, Properties.OnItemDrawingListener paramOnItemDrawingListener, ViewPainter.MarqueeTextOffsetListener paramMarqueeTextOffsetListener, Bitmap paramBitmap3, Bitmap paramBitmap4)
  {
    super.drawItem(paramCanvas, paramItemData, paramRect, paramAlign, paramInt1, paramInt2, paramTypeface1, paramInt3, paramInt4, paramTypeface2, paramBitmap1, paramBitmap2, paramOnItemDrawingListener, paramMarqueeTextOffsetListener, paramBitmap3, paramBitmap4);
    paramCanvas.save();
    if ((this.leftArrow != null) && (this.rightArrow != null))
    {
      paramCanvas.drawBitmap(this.leftArrow, App.Operation(10.0F), paramRect.centerY() - this.leftArrow.getHeight() / 2, this.backgroundPaint);
      paramCanvas.drawBitmap(this.rightArrow, paramRect.right - this.rightArrow.getWidth() - App.Operation(10.0F), paramRect.centerY() - this.rightArrow.getHeight() / 2, this.backgroundPaint);
    }
    paramCanvas.restore();
  }

  public void setArrows(Bitmap paramBitmap1, Bitmap paramBitmap2)
  {
    this.leftArrow = paramBitmap1;
    this.rightArrow = paramBitmap2;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.gridview.drawers.ScrollItemDrawer
 * JD-Core Version:    0.6.2
 */