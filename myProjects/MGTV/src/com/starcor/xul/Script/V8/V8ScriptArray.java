package com.starcor.xul.Script.V8;

import android.util.Log;
import com.starcor.xul.Script.IScriptArray;
import com.starcor.xul.Script.IScriptableObject;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import java.util.Collection;
import java.util.Iterator;

public class V8ScriptArray
  implements IScriptArray
{
  private static V8ScriptArray EMPTY_SCRIPT_ARRAY;
  public static final String TAG = V8ScriptArray.class.getSimpleName();
  V8ScriptContext _ctx;
  long _nativeId;

  public V8ScriptArray(V8ScriptContext paramV8ScriptContext)
  {
    this._ctx = paramV8ScriptContext;
    this._nativeId = V8Engine.v8CreateArray(paramV8ScriptContext._nativeId, this);
  }

  V8ScriptArray(V8ScriptContext paramV8ScriptContext, long paramLong)
  {
    this._ctx = paramV8ScriptContext;
    this._nativeId = paramLong;
  }

  private void addObject(Object paramObject1, Object paramObject2)
  {
    if ((paramObject2 instanceof V8ScriptObject))
    {
      add((V8ScriptObject)paramObject2);
      return;
    }
    if ((paramObject2 instanceof XulView))
    {
      add((V8ScriptObject)((XulView)paramObject2).getScriptableObject("javascript"));
      return;
    }
    if ((paramObject2 instanceof String))
    {
      add((String)paramObject2);
      return;
    }
    if ((paramObject2 instanceof Integer))
    {
      add(((Integer)paramObject2).intValue());
      return;
    }
    if ((paramObject2 instanceof Boolean))
    {
      add(((Boolean)paramObject2).booleanValue());
      return;
    }
    if ((paramObject2 instanceof Float))
    {
      add(((Float)paramObject2).floatValue());
      return;
    }
    if ((paramObject2 instanceof V8ScriptArray))
    {
      add((V8ScriptArray)paramObject2);
      return;
    }
    if ((paramObject2 instanceof Object[]))
    {
      if (paramObject2.equals(paramObject1))
      {
        add(this);
        return;
      }
      V8ScriptArray localV8ScriptArray2 = new V8ScriptArray(this._ctx);
      localV8ScriptArray2.addAll((Object[])paramObject2);
      add(localV8ScriptArray2);
      return;
    }
    if ((paramObject2 instanceof Collection))
    {
      if (paramObject2.equals(paramObject1))
      {
        add(this);
        return;
      }
      V8ScriptArray localV8ScriptArray1 = new V8ScriptArray(this._ctx);
      localV8ScriptArray1.addAll((Collection)paramObject2);
      add(localV8ScriptArray1);
      return;
    }
    if ((paramObject2 instanceof Double))
    {
      add(((Double)paramObject2).doubleValue());
      return;
    }
    if ((paramObject2 instanceof Long))
    {
      add(((Long)paramObject2).longValue());
      return;
    }
    add(String.valueOf(paramObject2));
  }

  public static V8ScriptArray from(V8ScriptContext paramV8ScriptContext, Collection paramCollection)
  {
    if ((paramCollection == null) || (paramCollection.isEmpty()))
      return getEmptyArray(paramV8ScriptContext);
    V8ScriptArray localV8ScriptArray = new V8ScriptArray(paramV8ScriptContext);
    localV8ScriptArray.addAll(paramCollection);
    return localV8ScriptArray;
  }

  public static V8ScriptArray from(V8ScriptContext paramV8ScriptContext, Object[] paramArrayOfObject)
  {
    if ((paramArrayOfObject == null) || (paramArrayOfObject.length == 0))
      return getEmptyArray(paramV8ScriptContext);
    V8ScriptArray localV8ScriptArray = new V8ScriptArray(paramV8ScriptContext);
    localV8ScriptArray.addAll(paramArrayOfObject);
    return localV8ScriptArray;
  }

  public static V8ScriptArray fromArrayList(V8ScriptContext paramV8ScriptContext, XulArrayList paramXulArrayList)
  {
    if ((paramXulArrayList == null) || (paramXulArrayList.isEmpty()))
      return getEmptyArray(paramV8ScriptContext);
    long l1 = XulUtils.timestamp_us();
    V8ScriptArray localV8ScriptArray = new V8ScriptArray(paramV8ScriptContext);
    long l2 = XulUtils.timestamp_us();
    localV8ScriptArray.addAll(paramXulArrayList);
    long l3 = XulUtils.timestamp_us();
    Log.d("BENCH!!!", "setResult " + (l2 - l1) + " " + (l3 - l2));
    return localV8ScriptArray;
  }

  private static V8ScriptArray getEmptyArray(V8ScriptContext paramV8ScriptContext)
  {
    if (EMPTY_SCRIPT_ARRAY == null)
      EMPTY_SCRIPT_ARRAY = new V8ScriptArray(paramV8ScriptContext);
    return EMPTY_SCRIPT_ARRAY;
  }

  public static V8ScriptArray wrapNativeArray(V8ScriptContext paramV8ScriptContext, long paramLong)
  {
    return new V8ScriptArray(paramV8ScriptContext, paramLong);
  }

  public void add(double paramDouble)
  {
    V8Engine.v8ArraySetDouble(this._ctx._nativeId, this._nativeId, -1, paramDouble);
  }

  public void add(float paramFloat)
  {
    V8Engine.v8ArraySetFloat(this._ctx._nativeId, this._nativeId, -1, paramFloat);
  }

  public void add(int paramInt)
  {
    V8Engine.v8ArraySetInt(this._ctx._nativeId, this._nativeId, -1, paramInt);
  }

  public void add(long paramLong)
  {
    V8Engine.v8ArraySetLong(this._ctx._nativeId, this._nativeId, -1, paramLong);
  }

  public void add(IScriptArray paramIScriptArray)
  {
    add((V8ScriptArray)paramIScriptArray);
  }

  public void add(IScriptableObject paramIScriptableObject)
  {
    add((V8ScriptObject)paramIScriptableObject);
  }

  public void add(V8ScriptArray paramV8ScriptArray)
  {
    V8Engine.v8ArraySetArray(this._ctx._nativeId, this._nativeId, -1, paramV8ScriptArray._nativeId);
  }

  public void add(V8ScriptObject paramV8ScriptObject)
  {
    V8Engine.v8ArraySetScriptObject(this._ctx._nativeId, this._nativeId, -1, paramV8ScriptObject._nativeId);
  }

  public void add(String paramString)
  {
    V8Engine.v8ArraySetString(this._ctx._nativeId, this._nativeId, -1, paramString);
  }

  public void add(boolean paramBoolean)
  {
    V8Engine.v8ArraySetBoolean(this._ctx._nativeId, this._nativeId, -1, paramBoolean);
  }

  public void addAll(XulArrayList paramXulArrayList)
  {
    Object[] arrayOfObject = paramXulArrayList.getArray();
    int i = 0;
    int j = paramXulArrayList.size();
    while (i < j)
    {
      addObject(paramXulArrayList, arrayOfObject[i]);
      i++;
    }
  }

  public void addAll(Collection paramCollection)
  {
    if ((paramCollection instanceof XulArrayList))
      addAll((XulArrayList)paramCollection);
    while (true)
    {
      return;
      Iterator localIterator = paramCollection.iterator();
      while (localIterator.hasNext())
        addObject(paramCollection, localIterator.next());
    }
  }

  public void addAll(Object[] paramArrayOfObject)
  {
    int i = 0;
    int j = paramArrayOfObject.length;
    while (i < j)
    {
      addObject(paramArrayOfObject, paramArrayOfObject[i]);
      i++;
    }
  }

  protected void finalize()
    throws Throwable
  {
    V8Engine.v8DestroyArray(this._ctx._nativeId, this._nativeId);
    super.finalize();
  }

  public V8ScriptArray getArray(int paramInt)
  {
    return V8Engine.v8ArrayGetArray(this._ctx._nativeId, this._nativeId, paramInt);
  }

  public boolean getBoolean(int paramInt)
  {
    return V8Engine.v8ArrayGetBoolean(this._ctx._nativeId, this._nativeId, paramInt);
  }

  public double getDouble(int paramInt)
  {
    return V8Engine.v8ArrayGetDouble(this._ctx._nativeId, this._nativeId, paramInt);
  }

  public float getFloat(int paramInt)
  {
    return V8Engine.v8ArrayGetFloat(this._ctx._nativeId, this._nativeId, paramInt);
  }

  public int getInteger(int paramInt)
  {
    return V8Engine.v8ArrayGetInt(this._ctx._nativeId, this._nativeId, paramInt);
  }

  public long getLong(int paramInt)
  {
    return V8Engine.v8ArrayGetLong(this._ctx._nativeId, this._nativeId, paramInt);
  }

  public V8ScriptObject getScriptObject(int paramInt)
  {
    return V8Engine.v8ArrayGetScriptObject(this._ctx._nativeId, this._nativeId, paramInt);
  }

  public String getString(int paramInt)
  {
    return V8Engine.v8ArrayGetString(this._ctx._nativeId, this._nativeId, paramInt);
  }

  public int getStringId(int paramInt)
  {
    return V8Engine.v8ArrayGetStringId(this._ctx._nativeId, this._nativeId, paramInt);
  }

  public int size()
  {
    return V8Engine.v8ArrayLength(this._ctx._nativeId, this._nativeId);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Script.V8.V8ScriptArray
 * JD-Core Version:    0.6.2
 */