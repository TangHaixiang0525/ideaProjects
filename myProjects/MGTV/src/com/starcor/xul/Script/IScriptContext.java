package com.starcor.xul.Script;

import java.io.InputStream;

public abstract interface IScriptContext
{
  public abstract boolean addIndexedString(String paramString, int paramInt);

  public abstract IScript compileFunction(InputStream paramInputStream, String paramString, int paramInt);

  public abstract IScript compileFunction(String paramString1, String paramString2, int paramInt);

  public abstract IScript compileScript(InputStream paramInputStream, String paramString, int paramInt);

  public abstract IScript compileScript(String paramString1, String paramString2, int paramInt);

  public abstract IScriptArray createScriptArray();

  public abstract IScriptMap createScriptMap();

  public abstract IScriptableObject createScriptObject(XulScriptableObject paramXulScriptableObject);

  public abstract boolean delIndexedString(String paramString);

  public abstract void destroy();

  public abstract IScript getFunction(Object paramObject, String paramString);

  public abstract String getScriptType();

  public abstract void init();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Script.IScriptContext
 * JD-Core Version:    0.6.2
 */