package com.starcor.hunan.widget;

import android.graphics.Canvas;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import com.starcor.core.domain.BarrageMeta;
import com.starcor.core.utils.BarrageTools;
import com.starcor.hunan.App;

public class BarrageMetaView
{
  private static final int RADIUS = App.Operation(20.0F);
  private static final String TAG = BarrageMetaView.class.getSimpleName();
  private static final int TEXT_PADDING_TOP = App.Operation(27.0F);
  private DrawStateCallback mCallback = null;
  private BarrageMeta mData = null;
  private TextPaint mFontPaint = null;
  private PaintFlagsDrawFilter mPaintFlagsDrawFilter = null;
  private RectF mRectF = null;
  private float mXStep = 0.0F;
  private int viewHeight = App.Operation(40.0F);
  private int viewWidth = App.Operation(60.0F);

  public BarrageMetaView(BarrageMeta paramBarrageMeta)
  {
    this.mData = paramBarrageMeta;
    initPaint();
  }

  private void initPaint()
  {
    this.mFontPaint = BarrageTools.getInstance().getTextPaint();
    this.mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, 3);
    this.viewWidth = BarrageTools.getInstance().getViewWidth(this.mData.content);
    this.mXStep = BarrageTools.getInstance().getXStep(this.viewWidth);
    this.mRectF = new RectF();
    this.mRectF.left = BarrageTools.SCREEN_X;
    this.mRectF.right = (this.mRectF.left + this.viewWidth);
    this.mRectF.top = 0.0F;
    this.mRectF.bottom = this.viewHeight;
  }

  public void draw(Canvas paramCanvas)
  {
    if ((this.mData == null) || (TextUtils.isEmpty(this.mData.content)) || (paramCanvas == null));
    do
    {
      return;
      paramCanvas.setDrawFilter(this.mPaintFlagsDrawFilter);
      if (this.mRectF.left == BarrageTools.SCREEN_X)
      {
        RectF localRectF = this.mRectF;
        localRectF.left -= this.mXStep;
      }
      this.mRectF.right = (this.mRectF.left + this.viewWidth);
      this.mRectF.bottom = (this.mRectF.top + this.viewHeight);
      paramCanvas.drawText(this.mData.content, this.mRectF.left, this.mRectF.top + TEXT_PADDING_TOP, BarrageTools.getInstance().getLinePaint());
      paramCanvas.drawText(this.mData.content, this.mRectF.left, this.mRectF.top + TEXT_PADDING_TOP, this.mFontPaint);
    }
    while ((this.mRectF.right >= 0.0F) || (this.mCallback == null));
    this.mCallback.finish(this, this.mRectF);
  }

  public BarrageMeta getData()
  {
    return this.mData;
  }

  public int getLeft()
  {
    return (int)this.mRectF.left;
  }

  public int getRight()
  {
    return (int)this.mRectF.right;
  }

  public float getStep()
  {
    return this.mXStep;
  }

  public int getWidth()
  {
    return (int)(this.mRectF.right - this.mRectF.left);
  }

  public boolean isEqual(BarrageMetaView paramBarrageMetaView)
  {
    return getData().content.equals(paramBarrageMetaView.getData().content);
  }

  public void setDrawCallback(DrawStateCallback paramDrawStateCallback)
  {
    this.mCallback = paramDrawStateCallback;
  }

  public void setItemData(BarrageMeta paramBarrageMeta)
  {
    this.mData = paramBarrageMeta;
  }

  public void setTop(int paramInt)
  {
    this.mRectF.top = paramInt;
  }

  public void updatePos()
  {
    if (this.mRectF.left == BarrageTools.SCREEN_X)
      return;
    RectF localRectF = this.mRectF;
    localRectF.left -= this.mXStep;
  }

  public static abstract interface DrawStateCallback
  {
    public abstract void finish(BarrageMetaView paramBarrageMetaView, RectF paramRectF);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.BarrageMetaView
 * JD-Core Version:    0.6.2
 */