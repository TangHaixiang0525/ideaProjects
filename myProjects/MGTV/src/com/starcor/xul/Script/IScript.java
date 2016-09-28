package com.starcor.xul.Script;

public abstract interface IScript
{
  public abstract String getScriptType();

  public abstract Object run(IScriptContext paramIScriptContext, IScriptableObject paramIScriptableObject);

  public abstract Object run(IScriptContext paramIScriptContext, IScriptableObject paramIScriptableObject, Object[] paramArrayOfObject);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Script.IScript
 * JD-Core Version:    0.6.2
 */