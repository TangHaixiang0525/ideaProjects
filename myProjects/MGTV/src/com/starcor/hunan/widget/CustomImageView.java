package com.starcor.hunan.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;

public class CustomImageView extends View
{
  private static final String TAG = CustomImageView.class.getSimpleName();
  private Bitmap flagBitmap;
  private Rect flagSrcRect;
  private RectF flagTargetRect;
  private Bitmap imageDrawable;
  private Rect imageSrc = new Rect();
  private Rect imageSrcRect;
  private RectF imageTargetRect;
  private Bitmap maskDrawable;
  private Rect maskSrcRect;
  private RectF maskTargetRect;
  private String name;
  private Paint paint = new Paint();
  private Paint paint2 = new Paint();
  private int position;
  private RectF roundRect = new RectF();
  private boolean selected;
  private Bitmap selectedDrawable;
  private Rect selectedSrcRect;
  private RectF selectedTargetRect;

  public CustomImageView(Context paramContext)
  {
    super(paramContext);
    initialize();
  }

  public CustomImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initialize();
  }

  public CustomImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initialize();
  }

  private Bitmap getRoundedCornerBitmap(Bitmap paramBitmap, float paramFloat, int paramInt1, int paramInt2)
  {
    Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    Paint localPaint = new Paint();
    Rect localRect = new Rect(0, 0, paramInt1, paramInt2);
    RectF localRectF = new RectF(localRect);
    localPaint.setAntiAlias(true);
    localCanvas.drawARGB(0, 0, 0, 0);
    localPaint.setColor(-12434878);
    localCanvas.drawRoundRect(localRectF, paramFloat, paramFloat, localPaint);
    localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    localCanvas.drawBitmap(paramBitmap, localRect, localRect, localPaint);
    return localBitmap;
  }

  private void initialize()
  {
    this.paint.setAntiAlias(true);
    this.paint.setColor(-12434878);
    this.paint2.setAntiAlias(true);
    this.paint2.setColor(-12434878);
    this.paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
  }

  public Bitmap getImageDrawable()
  {
    return this.imageDrawable;
  }

  public Bitmap getMaskDrawable()
  {
    return this.maskDrawable;
  }

  public Bitmap getSelectedDrawable()
  {
    return this.selectedDrawable;
  }

  @SuppressLint({"DrawAllocation"})
  protected void onDraw(Canvas paramCanvas)
  {
    if ((this.imageDrawable != null) && (this.imageDrawable.getHeight() * this.imageDrawable.getWidth() != 0))
    {
      Logger.i("customImageView", "position:" + this.position + ",name:" + this.name + "," + this.imageDrawable.getHeight() + "," + this.imageDrawable.getWidth());
      int i = getWidth();
      int j = getHeight();
      Logger.i(TAG, "viewWidth=" + i + " viewHeight=" + j);
      int k = Math.round(this.imageTargetRect.width());
      int m = Math.round(this.imageTargetRect.height());
      int n = Math.round(this.imageSrcRect.width());
      int i1 = Math.round(this.imageSrcRect.height());
      Logger.i(TAG, "before dstWidth=" + k + " dstHeight=" + m + " imageDrawable.getWidth()=" + this.imageDrawable.getWidth() + " imageDrawable.getHeight()=" + this.imageDrawable.getHeight() + " srcWidth=" + n + " srcHeight=" + i1);
      int i2 = Math.min(k, n);
      int i3 = Math.min(m, i1);
      Logger.i(TAG, "after dstWidth=" + i2 + " dstHeight=" + i3);
      RectF localRectF1 = new RectF(this.imageTargetRect);
      Bitmap localBitmap = this.imageDrawable;
      localRectF1.right += App.OperationFolat(4.0F);
      localRectF1.bottom += App.OperationFolat(4.0F);
      localRectF1.offset(App.OperationFolat(-3.0F), App.OperationFolat(-2.0F));
      Logger.i(TAG, "dstRect.right=" + localRectF1.right + " dstRect.bottom=" + localRectF1.bottom + " dstRect.width()=" + localRectF1.width() + " dstRect.height()=" + localRectF1.height());
      paramCanvas.drawBitmap(getRoundedCornerBitmap(localBitmap, App.OperationFolat(6.5F), i2, i3), null, localRectF1, this.paint);
      if (this.flagBitmap != null)
      {
        if (this.flagTargetRect == null)
        {
          this.flagTargetRect = new RectF(this.imageTargetRect);
          RectF localRectF2 = this.flagTargetRect;
          localRectF2.left -= App.OperationFolat(3.0F);
          RectF localRectF3 = this.flagTargetRect;
          localRectF3.top -= App.OperationFolat(1.0F);
          this.flagTargetRect.right = (this.flagBitmap.getWidth() + this.flagTargetRect.left);
          this.flagTargetRect.bottom = (this.flagBitmap.getWidth() + this.flagTargetRect.top);
        }
        paramCanvas.drawBitmap(this.flagBitmap, this.flagSrcRect, this.flagTargetRect, this.paint);
      }
    }
    if ((this.selected) && (this.selectedDrawable != null))
    {
      paramCanvas.drawBitmap(this.selectedDrawable, this.selectedSrcRect, this.selectedTargetRect, this.paint);
      return;
    }
    Logger.i("playBill", "selected=" + this.selected + ",drawable=" + this.selectedDrawable);
  }

  public void recycle()
  {
    if (this.imageDrawable != null)
    {
      this.imageDrawable.recycle();
      this.imageDrawable = null;
    }
  }

  public void setFlagBitmap(Bitmap paramBitmap)
  {
    this.flagBitmap = paramBitmap;
    if (paramBitmap == null)
      return;
    this.flagSrcRect = new Rect();
    this.flagSrcRect.right = paramBitmap.getWidth();
    this.flagSrcRect.bottom = paramBitmap.getHeight();
  }

  public void setImageDrawable(Bitmap paramBitmap, Rect paramRect, RectF paramRectF)
  {
    if ((paramBitmap != null) && (paramRect != null) && (paramRectF != null))
      Logger.i(TAG, "setImageDrawable imageDrawable.width=" + paramBitmap.getWidth() + "imageDrawable.height=" + paramBitmap.getHeight() + "scr.width=" + paramRect.width() + "scr.height=" + paramRect.height() + "dst.width=" + paramRectF.width() + "dst.height=" + paramRectF.height());
    this.imageDrawable = paramBitmap;
    this.imageSrcRect = paramRect;
    this.imageTargetRect = paramRectF;
    if (paramRectF != null)
    {
      this.roundRect.right = paramRectF.width();
      this.roundRect.bottom = paramRectF.height();
      this.imageSrc.right = ((int)paramRectF.width());
      this.imageSrc.bottom = ((int)paramRectF.height());
    }
  }

  public void setMaskDrawable(Bitmap paramBitmap, Rect paramRect, RectF paramRectF)
  {
    this.maskDrawable = paramBitmap;
    this.maskSrcRect = paramRect;
    this.maskTargetRect = paramRectF;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setPosition(int paramInt)
  {
    this.position = paramInt;
  }

  public void setSelected(boolean paramBoolean)
  {
    this.selected = paramBoolean;
    super.setSelected(paramBoolean);
  }

  public void setSelectedDrawable(Bitmap paramBitmap, Rect paramRect, RectF paramRectF)
  {
    this.selectedDrawable = paramBitmap;
    this.selectedSrcRect = paramRect;
    this.selectedTargetRect = paramRectF;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.CustomImageView
 * JD-Core Version:    0.6.2
 */