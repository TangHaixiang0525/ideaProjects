package com.starcor.hunan.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import com.starcor.hunan.App;
import com.starcor.xul.IXulExternalView;
import com.starcor.xul.XulView;

public class XulExt_SphereFlowView extends View
  implements IXulExternalView
{
  private Bitmap highlightBitmap;
  private Bitmap mBitmap;
  private Paint mClipPaint = new Paint();
  private Status mFlag = Status.NONE;
  private float mLeft;
  private Paint mPaint = new Paint();
  private float mPercent;
  private int mRepeatCount = 0;
  private int mSpeed = 5;

  public XulExt_SphereFlowView(Context paramContext, XulView paramXulView)
  {
    super(paramContext);
    this.mPaint.setColor(-16711936);
    this.mPaint.setFlags(1);
    this.mClipPaint.setFlags(1);
    this.mClipPaint.setColor(-1);
    this.mClipPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
  }

  public void destroy()
  {
    this.mFlag = Status.NONE;
    if (this.mBitmap != null)
    {
      this.mBitmap.recycle();
      this.mBitmap = null;
    }
  }

  public void extDestroy()
  {
    destroy();
  }

  public void extHide()
  {
    setVisibility(8);
  }

  public void extMoveTo(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
    localLayoutParams.leftMargin = paramInt1;
    localLayoutParams.topMargin = paramInt2;
    localLayoutParams.width = paramInt3;
    localLayoutParams.height = paramInt4;
    requestLayout();
  }

  public void extMoveTo(Rect paramRect)
  {
    extMoveTo(paramRect.left, paramRect.top, paramRect.width(), paramRect.height());
  }

  public void extOnBlur()
  {
    clearFocus();
  }

  public void extOnFocus()
  {
    requestFocus();
  }

  public boolean extOnKeyEvent(KeyEvent paramKeyEvent)
  {
    return dispatchKeyEvent(paramKeyEvent);
  }

  public void extShow()
  {
    setVisibility(0);
  }

  public void extSyncData()
  {
  }

  public String getAttr(String paramString1, String paramString2)
  {
    return paramString2;
  }

  public float getPercent()
  {
    return this.mPercent;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int i = getWidth();
    int j = getHeight();
    int k = App.OperationHeight(20);
    setBackgroundResource(2130837608);
    RectF localRectF = new RectF(k, k, i - k, j - k);
    paramCanvas.saveLayer(localRectF, null, 31);
    paramCanvas.drawRoundRect(localRectF, i / 2 - k, j / 2 - k, this.mPaint);
    paramCanvas.saveLayer(localRectF, this.mClipPaint, 31);
    paramCanvas.drawColor(0);
    if (this.mFlag == Status.RUNNING)
    {
      if (this.mBitmap == null)
        this.mBitmap = BitmapFactory.decodeResource(getContext().getResources(), 2130837677);
      this.mRepeatCount = ((int)Math.ceil(0.5D + getWidth() / this.mBitmap.getWidth()));
      for (int m = 0; m <= this.mRepeatCount; m++)
        paramCanvas.drawBitmap(this.mBitmap, this.mLeft + (m - 1) * this.mBitmap.getWidth() + k, getHeight() - ((getHeight() - k * 2) * this.mPercent + k), null);
      this.mLeft += this.mSpeed;
      if (this.mLeft >= this.mBitmap.getWidth())
        this.mLeft = 0.0F;
      postInvalidateDelayed(100L);
    }
    paramCanvas.restore();
    paramCanvas.restore();
    if (this.highlightBitmap == null)
      this.highlightBitmap = BitmapFactory.decodeResource(getContext().getResources(), 2130837550);
    Rect localRect1 = new Rect(0, 0, this.highlightBitmap.getWidth(), this.highlightBitmap.getHeight());
    Rect localRect2 = new Rect(App.OperationHeight(46), App.OperationHeight(195), App.OperationHeight(248), App.OperationHeight(266));
    paramCanvas.drawBitmap(this.highlightBitmap, localRect1, localRect2, this.mPaint);
  }

  public boolean setAttr(String paramString1, String paramString2)
  {
    return false;
  }

  public void setPercent(float paramFloat, boolean paramBoolean)
  {
    this.mFlag = Status.RUNNING;
    this.mPercent = paramFloat;
    if (paramBoolean)
    {
      if (paramFloat <= 0.5D)
        break label39;
      this.mPaint.setColor(-65536);
    }
    while (true)
    {
      postInvalidate();
      return;
      label39: this.mPaint.setColor(-16711936);
    }
  }

  public static enum Status
  {
    static
    {
      NONE = new Status("NONE", 1);
      Status[] arrayOfStatus = new Status[2];
      arrayOfStatus[0] = RUNNING;
      arrayOfStatus[1] = NONE;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.XulExt_SphereFlowView
 * JD-Core Version:    0.6.2
 */