package com.starcor.xul.Render.Transform.Algorithm;

public abstract class BasicTransformAlgorithmImpl
  implements ITransformAlgorithm
{
  public static ITransformAlgorithm.UpdateResult updateResult = new ITransformAlgorithm.UpdateResult();

  public static ITransformAlgorithm.UpdateResult commonUpdateAnimation(long paramLong1, long paramLong2, long paramLong3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    int i;
    if ((paramFloat3 - paramFloat1) * (paramFloat4 - paramFloat1) >= 0.0F)
    {
      i = 1;
      updateResult.newDest = paramFloat4;
      updateResult.newSrc = paramFloat1;
      if (i == 0)
        break label60;
    }
    label60: for (updateResult.newBegin = (paramLong1 + paramLong3); ; updateResult.newBegin = (paramLong1 + 2L * paramLong3 - paramLong2))
    {
      return updateResult;
      i = 0;
      break;
    }
  }

  public ITransformAlgorithm.UpdateResult update(float[] paramArrayOfFloat, long paramLong1, long paramLong2, long paramLong3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    return commonUpdateAnimation(paramLong1, paramLong2, paramLong3, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Transform.Algorithm.BasicTransformAlgorithmImpl
 * JD-Core Version:    0.6.2
 */