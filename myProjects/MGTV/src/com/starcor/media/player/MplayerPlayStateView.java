package com.starcor.media.player;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.starcor.hunan.App;

public class MplayerPlayStateView extends View
{
  public static final int MODE_FAST_FORWARD = 3;
  public static final int MODE_PAUSE = 2;
  public static final int MODE_PLAYING = 1;
  public static final int MODE_REWIND = 4;
  private Bitmap bkg;
  private Rect dstRect;
  private Bitmap fastForwardState;
  private Paint mPaint;
  public int mode = 1;
  private Bitmap pauseState;
  private Bitmap rewindState;

  public MplayerPlayStateView(Context paramContext)
  {
    super(paramContext);
    initView();
  }

  public MplayerPlayStateView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initView();
  }

  public MplayerPlayStateView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initView();
  }

  private Bitmap adjustPhotoRotation(Bitmap paramBitmap, int paramInt)
  {
    Matrix localMatrix = new Matrix();
    localMatrix.setRotate(paramInt, paramBitmap.getWidth() / 2.0F, paramBitmap.getHeight() / 2.0F);
    try
    {
      Bitmap localBitmap = Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight(), localMatrix, true);
      return localBitmap;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
    }
    return null;
  }

  private Bitmap decodeResource(Resources paramResources, int paramInt)
  {
    return BitmapFactory.decodeStream(paramResources.openRawResource(paramInt));
  }

  private Bitmap getStateBitmap()
  {
    switch (this.mode)
    {
    case 1:
    default:
      return null;
    case 2:
      return this.pauseState;
    case 3:
      return this.fastForwardState;
    case 4:
    }
    return this.rewindState;
  }

  private void initView()
  {
    this.bkg = decodeResource(getResources(), 2130837583);
    this.fastForwardState = decodeResource(getResources(), 2130837577);
    this.rewindState = adjustPhotoRotation(this.fastForwardState, 180);
    this.pauseState = decodeResource(getResources(), 2130837582);
    this.dstRect = new Rect(0, 0, App.OperationDiy(this.bkg.getWidth(), 1080), App.OperationDiy(this.bkg.getHeight(), 1080));
    this.mPaint = new Paint();
    this.mPaint.setAntiAlias(true);
    this.mPaint.setAlpha(255);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
    Bitmap localBitmap = getStateBitmap();
    if (localBitmap == null)
      return;
    paramCanvas.drawBitmap(this.bkg, null, this.dstRect, this.mPaint);
    paramCanvas.drawBitmap(localBitmap, null, this.dstRect, this.mPaint);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    setMeasuredDimension(App.OperationDiy(this.bkg.getWidth(), 1080), App.OperationDiy(this.bkg.getHeight(), 1080));
  }

  public void setCurrentMode(int paramInt)
  {
    this.mode = paramInt;
    setVisibility(0);
  }

  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
    if (paramInt != 0)
      this.mode = -1;
    invalidate();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerPlayStateView
 * JD-Core Version:    0.6.2
 */