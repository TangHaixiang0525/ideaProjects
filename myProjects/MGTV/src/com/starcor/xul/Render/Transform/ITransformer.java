package com.starcor.xul.Render.Transform;

import com.starcor.xul.Render.Transform.Algorithm.ITransformAlgorithm.UpdateResult;

public abstract interface ITransformer
{
  public abstract boolean switchAlgorithm(String paramString);

  public abstract boolean switchParams(float[] paramArrayOfFloat);

  public abstract float transform(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public abstract ITransformAlgorithm.UpdateResult updateAnimation(long paramLong1, long paramLong2, long paramLong3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Transform.ITransformer
 * JD-Core Version:    0.6.2
 */