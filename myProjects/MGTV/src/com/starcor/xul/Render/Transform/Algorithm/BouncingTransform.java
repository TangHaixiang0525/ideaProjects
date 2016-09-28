package com.starcor.xul.Render.Transform.Algorithm;

public class BouncingTransform extends BasicTransformAlgorithmImpl
{
  public String name()
  {
    return "bouncing";
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
    double d5 = Math.pow(f, 2.5D / d3);
    return (float)(Math.pow(d5, 0.15D + 0.1D * d1) + 0.3D * d4 * Math.pow(d5, 0.4D) * Math.sin(8.0D * 3.141592653589793D * Math.pow(d5 * d2, 1.6D)) / Math.tan(d5 * 3.141592653589793D / 2.0D));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Transform.Algorithm.BouncingTransform
 * JD-Core Version:    0.6.2
 */