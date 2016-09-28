package com.starcor.xul.Render.Transform.Algorithm;

public class PowTransform extends BasicTransformAlgorithmImpl
{
  public String name()
  {
    return "pow";
  }

  public float transform(float[] paramArrayOfFloat, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f = paramFloat1 / paramFloat2;
    if (f > 1.0F)
      f = 1.0F;
    double d = 2.0D * paramArrayOfFloat[0];
    return (float)Math.pow(f, d);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Transform.Algorithm.PowTransform
 * JD-Core Version:    0.6.2
 */