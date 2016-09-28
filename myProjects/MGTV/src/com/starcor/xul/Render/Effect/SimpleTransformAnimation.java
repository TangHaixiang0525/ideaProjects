package com.starcor.xul.Render.Effect;

import com.starcor.xul.Render.Drawer.IXulAnimation;
import com.starcor.xul.Render.Transform.Algorithm.BasicTransformAlgorithmImpl;
import com.starcor.xul.Render.Transform.Algorithm.ITransformAlgorithm.UpdateResult;
import com.starcor.xul.Render.Transform.ITransformer;
import com.starcor.xul.Render.XulViewRender;

public abstract class SimpleTransformAnimation
  implements IXulAnimation
{
  ITransformer _aniTransformer;
  long _begin;
  protected float _destVal;
  protected long _duration;
  long _progress;
  XulViewRender _render;
  boolean _running = false;
  protected float _srcVal;
  protected float _val;

  public SimpleTransformAnimation(XulViewRender paramXulViewRender)
  {
    this(paramXulViewRender, null);
  }

  public SimpleTransformAnimation(XulViewRender paramXulViewRender, ITransformer paramITransformer)
  {
    this._aniTransformer = paramITransformer;
    this._render = paramXulViewRender;
  }

  public float getScalar()
  {
    return 1.0F;
  }

  public ITransformer getTransformer()
  {
    return this._aniTransformer;
  }

  public boolean isRunning()
  {
    return this._running;
  }

  public void onAnimationStop()
  {
    this._render.onAnimationFinished(true);
  }

  public void prepareAnimation(int paramInt)
  {
    if (!this._running)
    {
      this._duration = paramInt;
      storeSrc();
    }
  }

  public void restoreSrcValue()
  {
    updateValue(this._begin);
    restoreValue();
  }

  public abstract void restoreValue();

  public void setTransformer(ITransformer paramITransformer)
  {
    this._aniTransformer = paramITransformer;
  }

  public void startAnimation()
  {
    if (!this._running)
    {
      storeDest();
      this._begin = this._render.animationTimestamp();
      this._running = true;
    }
    float f1;
    float f2;
    float f3;
    do
    {
      return;
      f1 = this._val;
      f2 = this._destVal;
      storeDest();
      f3 = this._destVal;
    }
    while (Math.abs(f3 - f2) < 0.01F);
    float f4 = getScalar();
    ITransformer localITransformer = this._aniTransformer;
    if (localITransformer != null);
    for (ITransformAlgorithm.UpdateResult localUpdateResult = localITransformer.updateAnimation(this._begin, this._duration, this._progress, f1 / f4, this._srcVal / f4, f2 / f4, f3 / f4); ; localUpdateResult = BasicTransformAlgorithmImpl.commonUpdateAnimation(this._begin, this._duration, this._progress, f1 / f4, this._srcVal / f4, f2 / f4, f3 / f4))
    {
      this._begin = localUpdateResult.newBegin;
      this._srcVal = (f4 * localUpdateResult.newSrc);
      this._destVal = (f4 * localUpdateResult.newDest);
      return;
    }
  }

  public void stopAnimation()
  {
    if (this._running)
    {
      this._running = false;
      onAnimationStop();
    }
  }

  public abstract void storeDest();

  public abstract void storeSrc();

  public boolean updateAnimation(long paramLong)
  {
    if (!this._running)
      return false;
    boolean bool1 = updateValue(paramLong);
    restoreValue();
    this._render.markDirtyView();
    boolean bool2 = false;
    if (!bool1)
      bool2 = true;
    this._running = bool2;
    if (!this._running)
      onAnimationStop();
    return this._running;
  }

  public boolean updateValue(long paramLong)
  {
    long l = paramLong - this._begin;
    if (l < 0L)
      l = 0L;
    this._progress = l;
    float f1;
    if (this._aniTransformer != null)
    {
      float f3 = getScalar();
      f1 = this._aniTransformer.transform((float)l, (float)this._duration, this._srcVal / f3, this._destVal / f3);
      if (f1 >= 1.0F)
        f1 = 1.0F;
      if (f1 < 1.0F)
        break label134;
    }
    label134: for (boolean bool = true; ; bool = false)
    {
      float f2 = this._destVal - this._srcVal;
      this._val = this._srcVal;
      this._val += f2 * f1;
      return bool;
      f1 = (float)l / (float)this._duration;
      break;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Effect.SimpleTransformAnimation
 * JD-Core Version:    0.6.2
 */