package com.starcor.xul.Script.V8;

import com.starcor.xul.Script.IScriptArguments;
import com.starcor.xul.Script.IScriptArray;
import com.starcor.xul.Script.IScriptableObject;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.XulView;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class V8Arguments
  implements IScriptArguments
{
  public static final String TAG = V8Arguments.class.getSimpleName();
  private static V8Arguments tempNativeArguments;
  boolean _autoDestroy = true;
  V8ScriptContext _ctx;
  long _nativeId;

  public V8Arguments(V8ScriptContext paramV8ScriptContext)
  {
    this(paramV8ScriptContext, V8Engine.v8CreateArguments(paramV8ScriptContext._nativeId));
  }

  V8Arguments(V8ScriptContext paramV8ScriptContext, long paramLong)
  {
    this._ctx = paramV8ScriptContext;
    this._nativeId = paramLong;
  }

  private void addObject(Object paramObject)
  {
    if ((paramObject instanceof V8ScriptObject))
    {
      addArg((V8ScriptObject)paramObject);
      return;
    }
    if ((paramObject instanceof XulView))
    {
      addArg((V8ScriptObject)((XulView)paramObject).getScriptableObject("javascript"));
      return;
    }
    if ((paramObject instanceof String))
    {
      addArg((String)paramObject);
      return;
    }
    if ((paramObject instanceof Integer))
    {
      addArg(((Integer)paramObject).intValue());
      return;
    }
    if ((paramObject instanceof Boolean))
    {
      addArg(((Boolean)paramObject).booleanValue());
      return;
    }
    if ((paramObject instanceof Float))
    {
      addArg(((Float)paramObject).floatValue());
      return;
    }
    if ((paramObject instanceof V8ScriptArray))
    {
      addArg((V8ScriptArray)paramObject);
      return;
    }
    if ((paramObject instanceof Object[]))
    {
      V8ScriptArray localV8ScriptArray1 = new V8ScriptArray(this._ctx);
      localV8ScriptArray1.addAll((Object[])paramObject);
      addArg(localV8ScriptArray1);
      return;
    }
    if ((paramObject instanceof Collection))
    {
      V8ScriptArray localV8ScriptArray2 = new V8ScriptArray(this._ctx);
      localV8ScriptArray2.addAll((Collection)paramObject);
      addArg(localV8ScriptArray2);
      return;
    }
    if ((paramObject instanceof Double))
    {
      addArg(((Double)paramObject).doubleValue());
      return;
    }
    if ((paramObject instanceof Long))
    {
      addArg(((Long)paramObject).longValue());
      return;
    }
    addArg(String.valueOf(paramObject));
  }

  public static V8Arguments from(V8ScriptContext paramV8ScriptContext, Object[] paramArrayOfObject)
  {
    if ((paramArrayOfObject == null) || (paramArrayOfObject.length == 0))
      return null;
    V8Arguments localV8Arguments = new V8Arguments(paramV8ScriptContext);
    localV8Arguments.addAll(Arrays.asList(paramArrayOfObject));
    return localV8Arguments;
  }

  static V8Arguments wrapTempNativeArguments(V8ScriptContext paramV8ScriptContext, long paramLong)
  {
    if (paramLong == 0L)
      return null;
    if (tempNativeArguments == null)
    {
      tempNativeArguments = new V8Arguments(paramV8ScriptContext, paramLong);
      tempNativeArguments._autoDestroy = false;
    }
    while (true)
    {
      return tempNativeArguments;
      tempNativeArguments._ctx = paramV8ScriptContext;
      tempNativeArguments._nativeId = paramLong;
    }
  }

  public void addAll(Collection paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
      addObject(localIterator.next());
  }

  public void addAll(Object[] paramArrayOfObject)
  {
    int i = 0;
    int j = paramArrayOfObject.length;
    while (i < j)
    {
      addObject(paramArrayOfObject[i]);
      i++;
    }
  }

  public void addArg(double paramDouble)
  {
    V8Engine.v8ArgumentsSetDouble(this._ctx._nativeId, this._nativeId, -1, paramDouble);
  }

  public void addArg(float paramFloat)
  {
    V8Engine.v8ArgumentsSetFloat(this._ctx._nativeId, this._nativeId, -1, paramFloat);
  }

  public void addArg(int paramInt)
  {
    V8Engine.v8ArgumentsSetInt(this._ctx._nativeId, this._nativeId, -1, paramInt);
  }

  public void addArg(long paramLong)
  {
    V8Engine.v8ArgumentsSetLong(this._ctx._nativeId, this._nativeId, -1, paramLong);
  }

  public void addArg(V8ScriptArray paramV8ScriptArray)
  {
    V8Engine.v8ArgumentsSetArray(this._ctx._nativeId, this._nativeId, -1, paramV8ScriptArray._nativeId);
  }

  public void addArg(V8ScriptObject paramV8ScriptObject)
  {
    V8Engine.v8ArgumentsSetScriptObject(this._ctx._nativeId, this._nativeId, -1, paramV8ScriptObject._nativeId);
  }

  public void addArg(String paramString)
  {
    V8Engine.v8ArgumentsSetString(this._ctx._nativeId, this._nativeId, -1, paramString);
  }

  public void addArg(boolean paramBoolean)
  {
    V8Engine.v8ArgumentsSetBoolean(this._ctx._nativeId, this._nativeId, -1, paramBoolean);
  }

  protected void finalize()
    throws Throwable
  {
    if (this._autoDestroy)
      V8Engine.v8DestroyArguments(this._ctx._nativeId, this._nativeId);
    super.finalize();
  }

  public V8ScriptArray getArray(int paramInt)
  {
    return V8Engine.v8ArgumentsGetArray(this._ctx._nativeId, this._nativeId, paramInt);
  }

  public boolean getBoolean(int paramInt)
  {
    return V8Engine.v8ArgumentsGetBoolean(this._ctx._nativeId, this._nativeId, paramInt);
  }

  public double getDouble(int paramInt)
  {
    return V8Engine.v8ArgumentsGetDouble(this._ctx._nativeId, this._nativeId, paramInt);
  }

  public float getFloat(int paramInt)
  {
    return V8Engine.v8ArgumentsGetFloat(this._ctx._nativeId, this._nativeId, paramInt);
  }

  public int getInteger(int paramInt)
  {
    return V8Engine.v8ArgumentsGetInt(this._ctx._nativeId, this._nativeId, paramInt);
  }

  public long getLong(int paramInt)
  {
    return V8Engine.v8ArgumentsGetLong(this._ctx._nativeId, this._nativeId, paramInt);
  }

  public V8ScriptObject getScriptObject(int paramInt)
  {
    return V8Engine.v8ArgumentsGetScriptObject(this._ctx._nativeId, this._nativeId, paramInt);
  }

  public IScriptableObject getScriptableObject(int paramInt)
  {
    return getScriptObject(paramInt);
  }

  public String getString(int paramInt)
  {
    return V8Engine.v8ArgumentsGetString(this._ctx._nativeId, this._nativeId, paramInt);
  }

  public int getStringId(int paramInt)
  {
    return V8Engine.v8ArgumentsGetStringId(this._ctx._nativeId, this._nativeId, paramInt);
  }

  public void reset()
  {
    V8Engine.v8ArgumentsClear(this._ctx._nativeId, this._nativeId);
  }

  public void setResult(double paramDouble)
  {
    V8Engine.v8ArgumentsSetDouble(this._ctx._nativeId, this._nativeId, -2, paramDouble);
  }

  public void setResult(float paramFloat)
  {
    V8Engine.v8ArgumentsSetFloat(this._ctx._nativeId, this._nativeId, -2, paramFloat);
  }

  public void setResult(int paramInt)
  {
    V8Engine.v8ArgumentsSetInt(this._ctx._nativeId, this._nativeId, -2, paramInt);
  }

  public void setResult(long paramLong)
  {
    V8Engine.v8ArgumentsSetLong(this._ctx._nativeId, this._nativeId, -2, paramLong);
  }

  public void setResult(IScriptArray paramIScriptArray)
  {
    setResult((V8ScriptArray)paramIScriptArray);
  }

  public void setResult(IScriptableObject paramIScriptableObject)
  {
    setResult((V8ScriptObject)paramIScriptableObject);
  }

  public void setResult(V8ScriptArray paramV8ScriptArray)
  {
    V8Engine.v8ArgumentsSetArray(this._ctx._nativeId, this._nativeId, -2, paramV8ScriptArray._nativeId);
  }

  public void setResult(V8ScriptObject paramV8ScriptObject)
  {
    V8Engine.v8ArgumentsSetScriptObject(this._ctx._nativeId, this._nativeId, -2, paramV8ScriptObject._nativeId);
  }

  public void setResult(XulArrayList paramXulArrayList)
  {
    setResult(V8ScriptArray.fromArrayList(this._ctx, paramXulArrayList));
  }

  public void setResult(String paramString)
  {
    V8Engine.v8ArgumentsSetString(this._ctx._nativeId, this._nativeId, -2, paramString);
  }

  public void setResult(Collection paramCollection)
  {
    setResult(V8ScriptArray.from(this._ctx, paramCollection));
  }

  public void setResult(boolean paramBoolean)
  {
    V8Engine.v8ArgumentsSetBoolean(this._ctx._nativeId, this._nativeId, -2, paramBoolean);
  }

  public void setResult(Object[] paramArrayOfObject)
  {
    setResult(V8ScriptArray.from(this._ctx, paramArrayOfObject));
  }

  public int size()
  {
    return V8Engine.v8ArgumentsLength(this._ctx._nativeId, this._nativeId);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Script.V8.V8Arguments
 * JD-Core Version:    0.6.2
 */