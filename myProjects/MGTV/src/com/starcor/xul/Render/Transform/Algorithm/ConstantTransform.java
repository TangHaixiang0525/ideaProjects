package com.starcor.xul.Render.Transform.Algorithm;

public class ConstantTransform extends BasicTransformAlgorithmImpl
{
  public String name()
  {
    return "constant";
  }

  public float transform(float[] paramArrayOfFloat, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f1 = paramArrayOfFloat[0];
    float f2 = paramArrayOfFloat[1];
    float f3 = paramArrayOfFloat[2];
    if (f1 <= 0.0F)
      return 1.0F;
    float f4 = f1 / paramFloat2;
    float f5 = Math.abs(paramFloat4 - paramFloat3);
    if (f5 <= 0.01F)
      return 1.0F;
    float f6 = f2 * f4 / 2.0F;
    float f7 = f3 * f4 / 2.0F;
    float f8 = f5 - f6 - f7;
    if (f8 <= 0.0F)
    {
      f8 = 0.0F;
      f6 = f5 * f6 / (f2 + f3);
      f7 = f5 - f6;
    }
    float f9 = f8 / f4;
    float f10;
    if (paramFloat1 <= f2)
      if (f2 > 0.0F)
        f10 = (float)(Math.pow(paramFloat1 / f2, 2.0D) * f6);
    while (true)
    {
      if (f10 >= f5)
        f10 = f5;
      return f10 / f5;
      f10 = 0.0F;
      continue;
      if (paramFloat1 > f2 + f9)
      {
        if (f3 > 0.0F)
        {
          float f11 = (paramFloat1 - f2 - f9) / f3;
          if (f11 > 1.0F);
          for (float f12 = 0.0F; ; f12 = 1.0F - f11)
          {
            f10 = f5 - f7 * (f12 * f12);
            break;
          }
        }
        f10 = f5;
      }
      else if (f9 > 0.0F)
      {
        f10 = f6 + f8 * ((paramFloat1 - f2) / f9);
      }
      else
      {
        f10 = f6 + f8;
      }
    }
  }

  public ITransformAlgorithm.UpdateResult update(float[] paramArrayOfFloat, long paramLong1, long paramLong2, long paramLong3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    int i;
    if ((paramFloat3 - paramFloat1) * (paramFloat4 - paramFloat1) >= 0.0F)
    {
      i = 1;
      float f1 = paramArrayOfFloat[1];
      updateResult.newDest = paramFloat4;
      updateResult.newSrc = paramFloat1;
      if (i == 0)
        break label150;
      if (f1 <= 0.0F)
        break label137;
      if ((float)paramLong3 < f1)
        break label119;
      updateResult.newBegin = (()((float)(paramLong1 + paramLong3) - f1));
      float f2 = transform(paramArrayOfFloat, f1, (float)paramLong2, paramFloat2, paramFloat4) * (paramFloat4 - paramFloat2);
      updateResult.newSrc = (paramFloat1 - f2);
    }
    while (true)
    {
      return updateResult;
      i = 0;
      break;
      label119: updateResult.newBegin = paramLong1;
      updateResult.newSrc = paramFloat2;
      continue;
      label137: updateResult.newBegin = (paramLong1 + paramLong3);
      continue;
      label150: updateResult.newBegin = (paramLong1 + 2L * paramLong3 - paramLong2);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Transform.Algorithm.ConstantTransform
 * JD-Core Version:    0.6.2
 */