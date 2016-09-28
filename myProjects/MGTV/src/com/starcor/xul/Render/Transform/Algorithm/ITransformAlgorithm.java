package com.starcor.xul.Render.Transform.Algorithm;

public abstract interface ITransformAlgorithm
{
  public abstract String name();

  public abstract float transform(float[] paramArrayOfFloat, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public abstract UpdateResult update(float[] paramArrayOfFloat, long paramLong1, long paramLong2, long paramLong3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public static class UpdateResult
  {
    public long newBegin;
    public float newDest;
    public float newSrc;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Transform.Algorithm.ITransformAlgorithm
 * JD-Core Version:    0.6.2
 */