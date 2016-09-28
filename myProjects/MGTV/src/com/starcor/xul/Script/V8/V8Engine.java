package com.starcor.xul.Script.V8;

import android.util.Log;

public class V8Engine
{
  public static final int ArgumentIndexLast = -1;
  public static final int ArgumentIndexResult = -2;
  public static final int V8TypeIdScriptArguments = 6;
  public static final int V8TypeIdScriptArray = 3;
  public static final int V8TypeIdScriptContext = 1;
  public static final int V8TypeIdScriptFunction = 4;
  public static final int V8TypeIdScriptObject = 2;
  public static final int V8TypeIdScriptProgram = 5;

  static
  {
    System.loadLibrary("starcor_xul-v8");
  }

  public static void getV8Statistics()
  {
    Object[] arrayOfObject = new Object[6];
    arrayOfObject[0] = Integer.valueOf(v8EngineStatistics(1));
    arrayOfObject[1] = Integer.valueOf(v8EngineStatistics(2));
    arrayOfObject[2] = Integer.valueOf(v8EngineStatistics(3));
    arrayOfObject[3] = Integer.valueOf(v8EngineStatistics(4));
    arrayOfObject[4] = Integer.valueOf(v8EngineStatistics(5));
    arrayOfObject[5] = Integer.valueOf(v8EngineStatistics(6));
    Log.d("V8Engine/Statistics", String.format("ctx:%d, obj:%d, array:%d, func:%d, prog:%d, args:%d", arrayOfObject));
  }

  public static native int v8ArgumentsClear(long paramLong1, long paramLong2);

  public static native V8ScriptArray v8ArgumentsGetArray(long paramLong1, long paramLong2, int paramInt);

  public static native boolean v8ArgumentsGetBoolean(long paramLong1, long paramLong2, int paramInt);

  public static native double v8ArgumentsGetDouble(long paramLong1, long paramLong2, int paramInt);

  public static native float v8ArgumentsGetFloat(long paramLong1, long paramLong2, int paramInt);

  public static native int v8ArgumentsGetInt(long paramLong1, long paramLong2, int paramInt);

  public static native long v8ArgumentsGetLong(long paramLong1, long paramLong2, int paramInt);

  public static native V8ScriptObject v8ArgumentsGetScriptObject(long paramLong1, long paramLong2, int paramInt);

  public static native String v8ArgumentsGetString(long paramLong1, long paramLong2, int paramInt);

  public static native int v8ArgumentsGetStringId(long paramLong1, long paramLong2, int paramInt);

  public static native int v8ArgumentsLength(long paramLong1, long paramLong2);

  public static native int v8ArgumentsSetArray(long paramLong1, long paramLong2, int paramInt, long paramLong3);

  public static native int v8ArgumentsSetBoolean(long paramLong1, long paramLong2, int paramInt, boolean paramBoolean);

  public static native int v8ArgumentsSetDouble(long paramLong1, long paramLong2, int paramInt, double paramDouble);

  public static native int v8ArgumentsSetFloat(long paramLong1, long paramLong2, int paramInt, float paramFloat);

  public static native int v8ArgumentsSetInt(long paramLong1, long paramLong2, int paramInt1, int paramInt2);

  public static native int v8ArgumentsSetLong(long paramLong1, long paramLong2, int paramInt, long paramLong3);

  public static native int v8ArgumentsSetScriptObject(long paramLong1, long paramLong2, int paramInt, long paramLong3);

  public static native int v8ArgumentsSetString(long paramLong1, long paramLong2, int paramInt, String paramString);

  public static native V8ScriptArray v8ArrayGetArray(long paramLong1, long paramLong2, int paramInt);

  public static native boolean v8ArrayGetBoolean(long paramLong1, long paramLong2, int paramInt);

  public static native double v8ArrayGetDouble(long paramLong1, long paramLong2, int paramInt);

  public static native float v8ArrayGetFloat(long paramLong1, long paramLong2, int paramInt);

  public static native int v8ArrayGetInt(long paramLong1, long paramLong2, int paramInt);

  public static native long v8ArrayGetLong(long paramLong1, long paramLong2, int paramInt);

  public static native V8ScriptObject v8ArrayGetScriptObject(long paramLong1, long paramLong2, int paramInt);

  public static native String v8ArrayGetString(long paramLong1, long paramLong2, int paramInt);

  public static native int v8ArrayGetStringId(long paramLong1, long paramLong2, int paramInt);

  public static native int v8ArrayLength(long paramLong1, long paramLong2);

  public static native int v8ArraySetArray(long paramLong1, long paramLong2, int paramInt, long paramLong3);

  public static native int v8ArraySetBoolean(long paramLong1, long paramLong2, int paramInt, boolean paramBoolean);

  public static native int v8ArraySetDouble(long paramLong1, long paramLong2, int paramInt, double paramDouble);

  public static native int v8ArraySetFloat(long paramLong1, long paramLong2, int paramInt, float paramFloat);

  public static native int v8ArraySetInt(long paramLong1, long paramLong2, int paramInt1, int paramInt2);

  public static native int v8ArraySetLong(long paramLong1, long paramLong2, int paramInt, long paramLong3);

  public static native int v8ArraySetScriptObject(long paramLong1, long paramLong2, int paramInt, long paramLong3);

  public static native int v8ArraySetString(long paramLong1, long paramLong2, int paramInt, String paramString);

  public static native boolean v8CallFunction(long paramLong1, long paramLong2, long paramLong3, long paramLong4);

  public static native long v8Compile(long paramLong, String paramString1, String paramString2, int paramInt);

  public static native long v8CompileFunction(long paramLong, String paramString1, String paramString2, int paramInt);

  public static native boolean v8ContextAddIndexedString(long paramLong, String paramString, int paramInt);

  public static native void v8ContextOnIdle(long paramLong, int paramInt);

  public static native boolean v8ContextRemoveIndexedString(long paramLong, String paramString);

  public static native long v8CreateArguments(long paramLong);

  public static native long v8CreateArray(long paramLong, Object paramObject);

  public static native long v8CreateScriptContext(Object paramObject);

  public static native long v8CreateScriptObject(long paramLong, Object paramObject);

  public static native void v8DestroyArguments(long paramLong1, long paramLong2);

  public static native void v8DestroyArray(long paramLong1, long paramLong2);

  public static native void v8DestroyFunction(long paramLong1, long paramLong2);

  public static native void v8DestroyScript(long paramLong1, long paramLong2);

  public static native void v8DestroyScriptContext(long paramLong);

  public static native void v8DestroyScriptObject(long paramLong1, long paramLong2);

  public static native int v8EngineStatistics(int paramInt);

  public static native long v8GetFunction(long paramLong, String paramString);

  public static native int v8ObjectAddMethod(long paramLong1, long paramLong2, String paramString);

  public static native int v8ObjectAddProperty(long paramLong1, long paramLong2, String paramString, boolean paramBoolean1, boolean paramBoolean2);

  public static native long v8ObjectGetFunction(long paramLong1, long paramLong2, String paramString);

  public static native void v8ObjectSetPrototype(long paramLong1, long paramLong2, long paramLong3);

  public static native void v8RunScript(long paramLong1, long paramLong2);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Script.V8.V8Engine
 * JD-Core Version:    0.6.2
 */