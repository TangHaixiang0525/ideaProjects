package com.starcor.xul.Script;

import com.starcor.xul.Utils.XulArrayList;
import java.util.Collection;

public abstract interface IScriptArray
{
  public abstract void add(double paramDouble);

  public abstract void add(float paramFloat);

  public abstract void add(int paramInt);

  public abstract void add(long paramLong);

  public abstract void add(IScriptArray paramIScriptArray);

  public abstract void add(IScriptableObject paramIScriptableObject);

  public abstract void add(String paramString);

  public abstract void add(boolean paramBoolean);

  public abstract void addAll(XulArrayList paramXulArrayList);

  public abstract void addAll(Collection paramCollection);

  public abstract void addAll(Object[] paramArrayOfObject);

  public abstract boolean getBoolean(int paramInt);

  public abstract double getDouble(int paramInt);

  public abstract float getFloat(int paramInt);

  public abstract int getInteger(int paramInt);

  public abstract long getLong(int paramInt);

  public abstract String getString(int paramInt);

  public abstract int getStringId(int paramInt);

  public abstract int size();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Script.IScriptArray
 * JD-Core Version:    0.6.2
 */