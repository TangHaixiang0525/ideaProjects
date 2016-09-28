package com.starcor.xul.Script;

public abstract class XulScriptableObject
{
  public abstract XulScriptMethodInvoker createMethodInvoker(int paramInt);

  public abstract XulScriptMethodInvoker createMethodInvoker(String paramString);

  public abstract Object getProperty(IScriptContext paramIScriptContext, int paramInt);

  public abstract Object getProperty(IScriptContext paramIScriptContext, String paramString);

  public abstract IScriptableClass getScriptClass();

  public abstract Object getUnwrappedObject();

  public abstract Object putProperty(IScriptContext paramIScriptContext, int paramInt, Object paramObject);

  public abstract Object putProperty(IScriptContext paramIScriptContext, String paramString, Object paramObject);

  public static abstract class XulScriptMethodInvoker
  {
    public abstract boolean invoke(XulScriptableObject paramXulScriptableObject, IScriptContext paramIScriptContext, IScriptArguments paramIScriptArguments);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Script.XulScriptableObject
 * JD-Core Version:    0.6.2
 */