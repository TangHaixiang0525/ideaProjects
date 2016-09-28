package com.starcor.xul.Render.Effect;

import com.starcor.xul.Render.Drawer.IXulAnimation;
import com.starcor.xul.Render.Transform.ITransformer;
import com.starcor.xul.Render.Transform.TransformerFactory;
import com.starcor.xul.Render.XulViewRender;

public abstract class QuiverAnimation
  implements IXulAnimation
{
  public static final String DEFAULT_MODE = "pow";
  public static final float[] DEFAULT_PARAMS = { 0.15F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F };
  long _begin;
  long _duration = 120L;
  private final XulViewRender _render;
  ITransformer _transformer;
  float _xStrength;
  float _yStrength;

  public QuiverAnimation(XulViewRender paramXulViewRender, float paramFloat1, float paramFloat2)
  {
    this._render = paramXulViewRender;
    this._xStrength = paramFloat1;
    this._yStrength = paramFloat2;
    this._begin = this._render.animationTimestamp();
    this._transformer = TransformerFactory.createTransformer("pow", DEFAULT_PARAMS);
  }

  public abstract boolean doQuiver(long paramLong, float paramFloat1, float paramFloat2);

  public void switchMode(String paramString, float[] paramArrayOfFloat)
  {
    this._transformer.switchAlgorithm(paramString);
    this._transformer.switchParams(paramArrayOfFloat);
  }

  public boolean updateAnimation(long paramLong)
  {
    float f1 = 4.0F * (float)((paramLong - this._begin) % this._duration) / (float)this._duration;
    float f2;
    if (f1 <= 1.0F)
      f2 = this._transformer.transform(f1 * (float)this._duration, (float)this._duration, 0.0F, 0.0F);
    while (true)
    {
      boolean bool = doQuiver(paramLong - this._begin, f2 * this._xStrength, f2 * this._yStrength);
      if (!bool)
        this._render.onAnimationFinished(true);
      return bool;
      if (f1 <= 2.0F)
        f2 = this._transformer.transform((2.0F - f1) * (float)this._duration, (float)this._duration, 0.0F, 0.0F);
      else if (f1 <= 3.0F)
        f2 = -this._transformer.transform((f1 - 2.0F) * (float)this._duration, (float)this._duration, 0.0F, 0.0F);
      else
        f2 = -this._transformer.transform((4.0F - f1) * (float)this._duration, (float)this._duration, 0.0F, 0.0F);
    }
  }

  public void updateDuration(int paramInt)
  {
    this._duration = paramInt;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Effect.QuiverAnimation
 * JD-Core Version:    0.6.2
 */