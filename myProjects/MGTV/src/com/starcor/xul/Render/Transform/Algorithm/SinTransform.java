package com.starcor.xul.Render.Transform.Algorithm;

public class SinTransform extends BasicTransformAlgorithmImpl
{
  public String name()
  {
    return "sin";
  }

  public float transform(float[] paramArrayOfFloat, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f = paramFloat1 / paramFloat2;
    if (f > 1.0F)
      f = 1.0F;
    double d = paramArrayOfFloat[0];
    return (float)((1.0D + Math.sin(3.141592653589793D * (Math.pow(f, 2.0D / d) - 0.5D))) / 2.0D);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Transform.Algorithm.SinTransform
 * JD-Core Version:    0.6.2
 */