package com.starcor.xul.Render.Transform;

import com.starcor.xul.Render.Transform.Algorithm.BouncingTransform;
import com.starcor.xul.Render.Transform.Algorithm.ConstantTransform;
import com.starcor.xul.Render.Transform.Algorithm.ITransformAlgorithm;
import com.starcor.xul.Render.Transform.Algorithm.ITransformAlgorithm.UpdateResult;
import com.starcor.xul.Render.Transform.Algorithm.LinearTransform;
import com.starcor.xul.Render.Transform.Algorithm.PowTransform;
import com.starcor.xul.Render.Transform.Algorithm.ShakingTransform;
import com.starcor.xul.Render.Transform.Algorithm.SinTransform;
import com.starcor.xul.Utils.XulCachedHashMap;

public class TransformerFactory
{
  public static final String ALGORITHM_BOUNCING = "bouncing";
  public static final String ALGORITHM_CONSTANT = "constant";
  public static final String ALGORITHM_LINEAR = "linear";
  public static final String ALGORITHM_POW = "pow";
  public static final String ALGORITHM_SHAKING = "shaking";
  public static final String ALGORITHM_SIN = "sin";
  static Class<? extends ITransformAlgorithm>[] _algorithmMap = { BouncingTransform.class, LinearTransform.class, PowTransform.class, ShakingTransform.class, SinTransform.class, ConstantTransform.class };
  static XulCachedHashMap<String, ITransformAlgorithm> _algorithms = new XulCachedHashMap();

  static
  {
    Class[] arrayOfClass = _algorithmMap;
    int i = arrayOfClass.length;
    int j = 0;
    while (true)
      if (j < i)
      {
        Class localClass = arrayOfClass[j];
        try
        {
          ITransformAlgorithm localITransformAlgorithm = (ITransformAlgorithm)localClass.newInstance();
          _algorithms.put(localITransformAlgorithm.name(), localITransformAlgorithm);
          j++;
        }
        catch (InstantiationException localInstantiationException)
        {
          while (true)
            localInstantiationException.printStackTrace();
        }
        catch (IllegalAccessException localIllegalAccessException)
        {
          while (true)
            localIllegalAccessException.printStackTrace();
        }
      }
  }

  public static ITransformer createTransformer(String paramString, float[] paramArrayOfFloat)
  {
    ITransformAlgorithm localITransformAlgorithm = (ITransformAlgorithm)_algorithms.get(paramString);
    if (localITransformAlgorithm == null)
      return null;
    return new TransformerImpl(localITransformAlgorithm, paramArrayOfFloat);
  }

  private static class TransformerImpl
    implements ITransformer
  {
    ITransformAlgorithm _algo;
    float[] _params;

    public TransformerImpl(ITransformAlgorithm paramITransformAlgorithm, float[] paramArrayOfFloat)
    {
      this._algo = paramITransformAlgorithm;
      this._params = paramArrayOfFloat;
    }

    public boolean switchAlgorithm(String paramString)
    {
      ITransformAlgorithm localITransformAlgorithm = (ITransformAlgorithm)TransformerFactory._algorithms.get(paramString);
      if (localITransformAlgorithm == null)
        return false;
      this._algo = localITransformAlgorithm;
      return true;
    }

    public boolean switchParams(float[] paramArrayOfFloat)
    {
      this._params = paramArrayOfFloat;
      return true;
    }

    public float transform(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      return this._algo.transform(this._params, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    }

    public ITransformAlgorithm.UpdateResult updateAnimation(long paramLong1, long paramLong2, long paramLong3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      return this._algo.update(this._params, paramLong1, paramLong2, paramLong3, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Transform.TransformerFactory
 * JD-Core Version:    0.6.2
 */