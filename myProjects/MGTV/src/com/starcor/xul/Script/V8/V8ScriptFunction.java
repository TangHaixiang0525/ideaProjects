package com.starcor.xul.Script.V8;

import com.starcor.xul.Script.IScript;
import com.starcor.xul.Script.IScriptContext;
import com.starcor.xul.Script.IScriptableObject;

public class V8ScriptFunction
  implements IScript
{
  public static final String TAG = V8ScriptFunction.class.getSimpleName();
  private static V8Arguments cachedArgument;
  V8ScriptContext _ctx;
  long _nativeId;

  private V8ScriptFunction(V8ScriptContext paramV8ScriptContext, long paramLong)
  {
    this._ctx = paramV8ScriptContext;
    this._nativeId = paramLong;
  }

  static V8ScriptFunction wrapNativeFunction(V8ScriptContext paramV8ScriptContext, long paramLong)
  {
    if (paramLong == 0L)
      return null;
    return new V8ScriptFunction(paramV8ScriptContext, paramLong);
  }

  public boolean call(V8ScriptObject paramV8ScriptObject, V8Arguments paramV8Arguments)
  {
    long l1 = 0L;
    long l2 = this._ctx._nativeId;
    long l3 = this._nativeId;
    long l4;
    if (paramV8ScriptObject == null)
    {
      l4 = l1;
      if (paramV8Arguments != null)
        break label48;
    }
    while (true)
    {
      return V8Engine.v8CallFunction(l2, l3, l4, l1);
      l4 = paramV8ScriptObject._nativeId;
      break;
      label48: l1 = paramV8Arguments._nativeId;
    }
  }

  protected void finalize()
    throws Throwable
  {
    V8Engine.v8DestroyFunction(this._ctx._nativeId, this._nativeId);
    super.finalize();
  }

  public String getScriptType()
  {
    return "javascript";
  }

  public Object run(IScriptContext paramIScriptContext, IScriptableObject paramIScriptableObject)
  {
    call((V8ScriptObject)paramIScriptableObject, null);
    return null;
  }

  public Object run(IScriptContext paramIScriptContext, IScriptableObject paramIScriptableObject, Object[] paramArrayOfObject)
  {
    if (cachedArgument == null)
    {
      cachedArgument = V8Arguments.from((V8ScriptContext)paramIScriptContext, paramArrayOfObject);
      call((V8ScriptObject)paramIScriptableObject, cachedArgument);
      if (cachedArgument != null)
        cachedArgument.reset();
      return null;
    }
    if ((paramArrayOfObject != null) && (paramArrayOfObject.length > 0))
    {
      cachedArgument.reset();
      cachedArgument.addAll(paramArrayOfObject);
      call((V8ScriptObject)paramIScriptableObject, cachedArgument);
      cachedArgument.reset();
      return null;
    }
    call((V8ScriptObject)paramIScriptableObject, null);
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Script.V8.V8ScriptFunction
 * JD-Core Version:    0.6.2
 */