package com.starcor.xul.Render.Effect;

import com.starcor.xul.Render.Drawer.IXulAnimation;
import com.starcor.xul.Render.Transform.ITransformer;
import com.starcor.xul.Render.XulViewRender;
import java.util.ArrayList;
import java.util.Arrays;

public class TransformAnimation
  implements IXulAnimation
{
  ITransformer _aniTransformer;
  long _begin;
  long _duration;
  long _progress;
  XulViewRender _render;
  boolean _running = false;
  private ArrayList<TransformValues> _values;

  public TransformAnimation(XulViewRender paramXulViewRender)
  {
    this(paramXulViewRender, null);
  }

  public TransformAnimation(XulViewRender paramXulViewRender, ITransformer paramITransformer)
  {
    this._aniTransformer = paramITransformer;
    this._render = paramXulViewRender;
  }

  public void addTransformValues(TransformValues[] paramArrayOfTransformValues)
  {
    if ((paramArrayOfTransformValues == null) || (paramArrayOfTransformValues.length <= 0))
      return;
    if (this._values == null)
      this._values = new ArrayList();
    this._values.addAll(Arrays.asList(paramArrayOfTransformValues));
  }

  public ITransformer getTransformer()
  {
    return this._aniTransformer;
  }

  public boolean identicalValues()
  {
    if (this._values == null);
    while (true)
    {
      return true;
      int i = 0;
      int j = this._values.size();
      while (i < j)
      {
        if (!((TransformValues)this._values.get(i)).identicalValue())
          return false;
        i++;
      }
    }
  }

  public boolean isRunning()
  {
    return this._running;
  }

  public void restoreSrcValue()
  {
    updateValues(this._begin);
    restoreValue();
  }

  public void restoreValue()
  {
    if (this._values == null);
    while (true)
    {
      return;
      int i = 0;
      int j = this._values.size();
      while (i < j)
      {
        ((TransformValues)this._values.get(i)).restoreValue();
        i++;
      }
    }
  }

  public void setDuration(long paramLong)
  {
    this._duration = paramLong;
    this._begin = this._render.animationTimestamp();
  }

  public void setTransformer(ITransformer paramITransformer)
  {
    this._aniTransformer = paramITransformer;
  }

  public void startAnimation(long paramLong, boolean paramBoolean)
  {
    if ((!this._running) || (!paramBoolean))
    {
      this._running = true;
      setDuration(paramLong);
      storeSrc();
      return;
    }
    long l = ()(paramLong * (1.0D - this._progress / this._duration));
    if (l < paramLong)
      setDuration(paramLong - l);
    while (true)
    {
      storeSrc();
      return;
      setDuration(1L);
    }
  }

  public void storeDest()
  {
    if (this._values == null);
    while (true)
    {
      return;
      int i = 0;
      int j = this._values.size();
      while (i < j)
      {
        ((TransformValues)this._values.get(i)).storeDest();
        i++;
      }
    }
  }

  public void storeSrc()
  {
    if (this._values == null);
    while (true)
    {
      return;
      int i = 0;
      int j = this._values.size();
      while (i < j)
      {
        ((TransformValues)this._values.get(i)).storeSrc();
        i++;
      }
    }
  }

  public boolean updateAnimation(long paramLong)
  {
    boolean bool1 = true;
    if (!this._running)
      return false;
    if (this._values == null)
    {
      this._render.onAnimationFinished(false);
      return false;
    }
    boolean bool2 = updateValues(paramLong);
    restoreValue();
    this._render.markDirtyView();
    boolean bool3;
    if (!bool2)
    {
      bool3 = bool1;
      this._running = bool3;
      if (!this._running)
        this._render.onAnimationFinished(bool1);
      if (bool2)
        break label88;
    }
    while (true)
    {
      return bool1;
      bool3 = false;
      break;
      label88: bool1 = false;
    }
  }

  public boolean updateValues(long paramLong)
  {
    long l = paramLong - this._begin;
    if (l < 0L)
      l = 0L;
    if ((l > this._duration) || (this._values == null) || (this._values.isEmpty()))
      l = this._duration;
    this._progress = l;
    float f;
    boolean bool;
    label88: int i;
    int j;
    label110: TransformValues localTransformValues;
    if (this._aniTransformer != null)
    {
      f = this._aniTransformer.transform((float)l, (float)this._duration, 0.0F, 0.0F);
      if (f < 1.0F)
        break label176;
      bool = true;
      if (this._values == null)
        break label212;
      i = 0;
      j = 0;
      int k = this._values.size();
      if (j >= k)
        break label199;
      localTransformValues = (TransformValues)this._values.get(j);
      if (!localTransformValues.customTransform())
        break label188;
      if ((!localTransformValues.updateValue(f)) && (i == 0))
        break label182;
      i = 1;
    }
    while (true)
    {
      j++;
      break label110;
      f = (float)l / (float)this._duration;
      break;
      label176: bool = false;
      break label88;
      label182: i = 0;
      continue;
      label188: localTransformValues.updateValue(f);
    }
    label199: if ((bool) && (i == 0))
    {
      bool = true;
      label212: return bool;
    }
    return false;
  }

  public static abstract class TransformValues
  {
    protected float _destVal;
    protected float _srcVal;
    protected float _val;

    public boolean customTransform()
    {
      return false;
    }

    public boolean identicalValue()
    {
      return this._srcVal == this._destVal;
    }

    public abstract void restoreValue();

    public abstract void storeDest();

    public abstract void storeSrc();

    public boolean updateValue(float paramFloat)
    {
      float f = this._destVal - this._srcVal;
      this._val = this._srcVal;
      if (f == 0.0F)
        return false;
      this._val += f * paramFloat;
      return true;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Effect.TransformAnimation
 * JD-Core Version:    0.6.2
 */