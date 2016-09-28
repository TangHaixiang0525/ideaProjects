package com.starcor.xul.Render.Transform.Algorithm;

public class ShakingTransform extends BasicTransformAlgorithmImpl
{
  public String name()
  {
    return "shaking";
  }

  public float transform(float[] paramArrayOfFloat, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f = paramFloat1 / paramFloat2;
    if (f > 1.0F)
      f = 1.0F;
    double d1 = paramArrayOfFloat[0];
    double d2 = paramArrayOfFloat[1];
    double d3 = paramArrayOfFloat[2];
    double d4 = paramArrayOfFloat[3];
    double d5 = paramArrayOfFloat[4];
    double d6 = f;
    return (float)((1.0D * d5 + (1.0D - d5) * (1.0D + Math.cos(3.141592653589793D + 3.141592653589793D * (2.0D * d6))) / 2.0D) * (d4 * ((d3 + Math.cos(3.141592653589793D * (4.0D * (d6 * d1)) + d2 * 3.141592653589793D)) / 2.0D)));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Transform.Algorithm.ShakingTransform
 * JD-Core Version:    0.6.2
 */