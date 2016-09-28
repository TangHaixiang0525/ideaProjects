package com.starcor.xul.Script.V8;

import com.starcor.xul.Script.IScript;
import com.starcor.xul.Script.IScriptContext;
import com.starcor.xul.Script.IScriptableObject;

public class V8Script
  implements IScript
{
  public static final String TAG = V8Script.class.getSimpleName();
  V8ScriptContext _ctx;
  long _nativeId;

  V8Script(V8ScriptContext paramV8ScriptContext, long paramLong)
  {
    this._ctx = paramV8ScriptContext;
    this._nativeId = paramLong;
  }

  public static V8Script wrapNativeScript(V8ScriptContext paramV8ScriptContext, long paramLong)
  {
    return new V8Script(paramV8ScriptContext, paramLong);
  }

  protected void finalize()
    throws Throwable
  {
    V8Engine.v8DestroyScript(this._ctx._nativeId, this._nativeId);
    super.finalize();
  }

  public String getScriptType()
  {
    return "javascript";
  }

  public Object run(IScriptContext paramIScriptContext, IScriptableObject paramIScriptableObject)
  {
    run();
    return null;
  }

  public Object run(IScriptContext paramIScriptContext, IScriptableObject paramIScriptableObject, Object[] paramArrayOfObject)
  {
    run();
    return null;
  }

  public void run()
  {
    V8Engine.v8RunScript(this._ctx._nativeId, this._nativeId);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Script.V8.V8Script
 * JD-Core Version:    0.6.2
 */