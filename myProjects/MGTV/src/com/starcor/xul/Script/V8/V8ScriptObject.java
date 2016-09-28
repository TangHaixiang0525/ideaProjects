package com.starcor.xul.Script.V8;

import com.starcor.xul.Script.IScriptableObject;
import com.starcor.xul.Script.XulScriptableObject;

public class V8ScriptObject
  implements IScriptableObject
{
  public static final String TAG = V8ScriptObject.class.getSimpleName();
  V8ScriptContext _ctx;
  XulScriptableObject _extScriptableObject;
  V8MethodCallback[] _methodSlots;
  long _nativeId;
  V8ScriptObject _prototype;

  public V8ScriptObject(V8ScriptContext paramV8ScriptContext)
  {
    this._ctx = paramV8ScriptContext;
    this._nativeId = V8Engine.v8CreateScriptObject(this._ctx._nativeId, this);
  }

  public V8ScriptObject(V8ScriptObject paramV8ScriptObject)
  {
    this(paramV8ScriptObject._ctx);
    this._prototype = paramV8ScriptObject;
    V8Engine.v8ObjectSetPrototype(this._ctx._nativeId, this._nativeId, this._prototype._nativeId);
  }

  static boolean getProperty(V8ScriptObject paramV8ScriptObject1, V8ScriptObject paramV8ScriptObject2, int paramInt, long paramLong)
  {
    if (paramV8ScriptObject1._methodSlots == null)
      return false;
    V8MethodCallback localV8MethodCallback = paramV8ScriptObject1._methodSlots[paramInt];
    V8Arguments localV8Arguments = V8Arguments.wrapTempNativeArguments(paramV8ScriptObject1._ctx, paramLong);
    try
    {
      boolean bool = localV8MethodCallback.get(paramV8ScriptObject2, localV8Arguments);
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  private boolean internalAddPropertyGetter(String paramString, V8MethodCallback paramV8MethodCallback, boolean paramBoolean1, boolean paramBoolean2)
  {
    writeMethodSlot(paramV8MethodCallback, V8Engine.v8ObjectAddProperty(this._ctx._nativeId, this._nativeId, paramString, paramBoolean1, paramBoolean2));
    return true;
  }

  static boolean invokeMethod(V8ScriptObject paramV8ScriptObject1, V8ScriptObject paramV8ScriptObject2, int paramInt, long paramLong)
  {
    if (paramV8ScriptObject1._methodSlots == null)
      return false;
    V8MethodCallback localV8MethodCallback = paramV8ScriptObject1._methodSlots[paramInt];
    V8Arguments localV8Arguments = V8Arguments.wrapTempNativeArguments(paramV8ScriptObject1._ctx, paramLong);
    try
    {
      boolean bool = localV8MethodCallback.invoke(paramV8ScriptObject2, localV8Arguments);
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  static boolean setProperty(V8ScriptObject paramV8ScriptObject1, V8ScriptObject paramV8ScriptObject2, int paramInt, long paramLong)
  {
    if (paramV8ScriptObject1._methodSlots == null)
      return false;
    V8MethodCallback localV8MethodCallback = paramV8ScriptObject1._methodSlots[paramInt];
    V8Arguments localV8Arguments = V8Arguments.wrapTempNativeArguments(paramV8ScriptObject1._ctx, paramLong);
    try
    {
      boolean bool = localV8MethodCallback.set(paramV8ScriptObject2, localV8Arguments);
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  private void writeMethodSlot(V8MethodCallback paramV8MethodCallback, int paramInt)
  {
    if (this._methodSlots == null)
      this._methodSlots = new V8MethodCallback[0xFFFFFFE0 & paramInt + 32];
    while (true)
    {
      this._methodSlots[paramInt] = paramV8MethodCallback;
      return;
      if (this._methodSlots.length <= paramInt)
      {
        V8MethodCallback[] arrayOfV8MethodCallback = new V8MethodCallback[0xFFFFFFE0 & paramInt + 32];
        System.arraycopy(this._methodSlots, 0, arrayOfV8MethodCallback, 0, this._methodSlots.length);
        this._methodSlots = arrayOfV8MethodCallback;
      }
    }
  }

  public boolean addMethod(String paramString, V8MethodCallback paramV8MethodCallback)
  {
    writeMethodSlot(paramV8MethodCallback, V8Engine.v8ObjectAddMethod(this._ctx._nativeId, this._nativeId, paramString));
    return true;
  }

  public boolean addProperty(String paramString, V8MethodCallback paramV8MethodCallback)
  {
    return internalAddPropertyGetter(paramString, paramV8MethodCallback, true, true);
  }

  public boolean addPropertyGetter(String paramString, V8MethodCallback paramV8MethodCallback)
  {
    return internalAddPropertyGetter(paramString, paramV8MethodCallback, true, false);
  }

  public boolean addPropertySetter(String paramString, V8MethodCallback paramV8MethodCallback)
  {
    return internalAddPropertyGetter(paramString, paramV8MethodCallback, false, true);
  }

  protected void finalize()
    throws Throwable
  {
    V8Engine.v8DestroyScriptObject(this._ctx._nativeId, this._nativeId);
    super.finalize();
  }

  public V8ScriptContext getContext()
  {
    return this._ctx;
  }

  public V8ScriptFunction getMethod(String paramString)
  {
    long l = V8Engine.v8ObjectGetFunction(this._ctx._nativeId, this._nativeId, paramString);
    return V8ScriptFunction.wrapNativeFunction(this._ctx, l);
  }

  public XulScriptableObject getObjectValue()
  {
    return this._extScriptableObject;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Script.V8.V8ScriptObject
 * JD-Core Version:    0.6.2
 */