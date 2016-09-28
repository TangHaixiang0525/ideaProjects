package com.starcor.xul.Script;

import com.starcor.xul.Utils.XulArrayList;
import java.util.Collection;

public abstract interface IScriptArguments
{
  public abstract boolean getBoolean(int paramInt);

  public abstract double getDouble(int paramInt);

  public abstract float getFloat(int paramInt);

  public abstract int getInteger(int paramInt);

  public abstract long getLong(int paramInt);

  public abstract IScriptableObject getScriptableObject(int paramInt);

  public abstract String getString(int paramInt);

  public abstract int getStringId(int paramInt);

  public abstract void setResult(double paramDouble);

  public abstract void setResult(float paramFloat);

  public abstract void setResult(int paramInt);

  public abstract void setResult(long paramLong);

  public abstract void setResult(IScriptArray paramIScriptArray);

  public abstract void setResult(IScriptableObject paramIScriptableObject);

  public abstract void setResult(XulArrayList paramXulArrayList);

  public abstract void setResult(String paramString);

  public abstract void setResult(Collection paramCollection);

  public abstract void setResult(boolean paramBoolean);

  public abstract void setResult(Object[] paramArrayOfObject);

  public abstract int size();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Script.IScriptArguments
 * JD-Core Version:    0.6.2
 */