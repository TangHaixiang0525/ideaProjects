package com.starcor.xul.Script.V8;

import com.starcor.xul.Script.IScript;
import com.starcor.xul.Script.IScriptArray;
import com.starcor.xul.Script.IScriptContext;
import com.starcor.xul.Script.IScriptMap;
import com.starcor.xul.Script.IScriptableClass;
import com.starcor.xul.Script.IScriptableObject;
import com.starcor.xul.Script.XulScriptFactory;
import com.starcor.xul.Script.XulScriptFactory.IScriptFactory;
import com.starcor.xul.Script.XulScriptableObject;
import com.starcor.xul.Script.XulScriptableObject.XulScriptMethodInvoker;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.Utils.XulCachedHashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class V8ScriptContext
  implements IScriptContext
{
  public static final String DEFAULT_SCRIPT_TYPE = "javascript";
  public static final String TAG = V8ScriptContext.class.getSimpleName();
  private static ArrayList<String> _supportedScriptTypes = new ArrayList();
  private static XulCachedHashMap<String, V8ScriptObject> scriptableClassCache = new XulCachedHashMap();
  long _nativeId = V8Engine.v8CreateScriptContext(this);

  static
  {
    _supportedScriptTypes.add("javascript");
  }

  static V8ScriptObject getScriptableClass(V8ScriptContext paramV8ScriptContext, XulScriptableObject paramXulScriptableObject)
  {
    IScriptableClass localIScriptableClass = paramXulScriptableObject.getScriptClass();
    String str1 = localIScriptableClass.getClassName();
    V8ScriptObject localV8ScriptObject1 = (V8ScriptObject)scriptableClassCache.get(str1);
    if (localV8ScriptObject1 != null)
      return localV8ScriptObject1;
    V8ScriptObject localV8ScriptObject2 = new V8ScriptObject(paramV8ScriptContext);
    scriptableClassCache.put(str1, localV8ScriptObject2);
    String[] arrayOfString1 = localIScriptableClass.getMethods();
    for (int i = 0; i < arrayOfString1.length; i++)
    {
      String str3 = arrayOfString1[i];
      localV8ScriptObject2.addMethod(str3, new V8MethodCallback()
      {
        XulScriptableObject.XulScriptMethodInvoker _methodInvoker = this.val$methodInvoker;

        public boolean invoke(V8ScriptObject paramAnonymousV8ScriptObject, V8Arguments paramAnonymousV8Arguments)
        {
          return this._methodInvoker.invoke(paramAnonymousV8ScriptObject.getObjectValue(), paramAnonymousV8ScriptObject.getContext(), paramAnonymousV8Arguments);
        }
      });
    }
    String[] arrayOfString2 = localIScriptableClass.getProperties();
    for (int j = 0; j < arrayOfString2.length; j++)
    {
      String str2 = arrayOfString2[j];
      localV8ScriptObject2.addProperty(str2, new V8MethodCallback()
      {
        public boolean get(V8ScriptObject paramAnonymousV8ScriptObject, V8Arguments paramAnonymousV8Arguments)
        {
          Object localObject = paramAnonymousV8ScriptObject.getObjectValue().getProperty(paramAnonymousV8ScriptObject.getContext(), this.val$property);
          if (localObject == null)
            return true;
          if ((localObject instanceof V8ScriptObject))
          {
            paramAnonymousV8Arguments.setResult((V8ScriptObject)localObject);
            return true;
          }
          if ((localObject instanceof Integer))
          {
            paramAnonymousV8Arguments.setResult(((Integer)localObject).intValue());
            return true;
          }
          if ((localObject instanceof String))
          {
            paramAnonymousV8Arguments.setResult((String)localObject);
            return true;
          }
          if ((localObject instanceof Boolean))
          {
            paramAnonymousV8Arguments.setResult(((Boolean)localObject).booleanValue());
            return true;
          }
          if ((localObject instanceof Double))
          {
            paramAnonymousV8Arguments.setResult(((Double)localObject).doubleValue());
            return true;
          }
          if ((localObject instanceof Float))
          {
            paramAnonymousV8Arguments.setResult(((Float)localObject).floatValue());
            return true;
          }
          if ((localObject instanceof Long))
          {
            paramAnonymousV8Arguments.setResult(((Long)localObject).longValue());
            return true;
          }
          if ((localObject instanceof XulArrayList))
          {
            paramAnonymousV8Arguments.setResult((XulArrayList)localObject);
            return true;
          }
          if ((localObject instanceof Object[]))
          {
            paramAnonymousV8Arguments.setResult((Object[])localObject);
            return true;
          }
          if ((localObject instanceof ArrayList))
          {
            paramAnonymousV8Arguments.setResult((ArrayList)localObject);
            return true;
          }
          new Exception("unsupported result type! " + localObject.getClass().getSimpleName()).printStackTrace();
          return false;
        }

        public boolean set(V8ScriptObject paramAnonymousV8ScriptObject, V8Arguments paramAnonymousV8Arguments)
        {
          V8ScriptObject localV8ScriptObject = paramAnonymousV8Arguments.getScriptObject(0);
          if (localV8ScriptObject != null)
          {
            paramAnonymousV8ScriptObject.getObjectValue().putProperty(paramAnonymousV8ScriptObject.getContext(), this.val$property, localV8ScriptObject);
            return true;
          }
          paramAnonymousV8ScriptObject.getObjectValue().putProperty(paramAnonymousV8ScriptObject.getContext(), this.val$property, paramAnonymousV8Arguments.getString(0));
          return true;
        }
      });
    }
    return localV8ScriptObject2;
  }

  public static V8ScriptContext obtainScriptContext()
  {
    return new V8ScriptContext();
  }

  static String readStreamText(InputStream paramInputStream)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
      while (true)
      {
        String str = localBufferedReader.readLine();
        if (str == null)
          return localStringBuilder.toString();
        localStringBuilder.append(str);
        localStringBuilder.append("\n");
      }
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  public static void register()
  {
    XulScriptFactory.registerFactory(new XulScriptFactory.IScriptFactory()
    {
      public IScriptContext createContext()
      {
        return new V8ScriptContext();
      }

      public List<String> getSupportScriptTypes()
      {
        return V8ScriptContext._supportedScriptTypes;
      }
    });
  }

  public boolean addIndexedString(String paramString, int paramInt)
  {
    return V8Engine.v8ContextAddIndexedString(this._nativeId, paramString, paramInt);
  }

  public IScript compileFunction(InputStream paramInputStream, String paramString, int paramInt)
  {
    return v8CompileFunction(readStreamText(paramInputStream), paramString, paramInt);
  }

  public IScript compileFunction(String paramString1, String paramString2, int paramInt)
  {
    return v8CompileFunction(paramString1, paramString2, paramInt);
  }

  public IScript compileScript(InputStream paramInputStream, String paramString, int paramInt)
  {
    return v8CompileScript(readStreamText(paramInputStream), paramString, paramInt);
  }

  public IScript compileScript(String paramString1, String paramString2, int paramInt)
  {
    return v8CompileScript(paramString1, paramString2, paramInt);
  }

  V8ScriptArray createArray(long paramLong)
  {
    return V8ScriptArray.wrapNativeArray(this, paramLong);
  }

  public IScriptArray createScriptArray()
  {
    return new V8ScriptArray(this);
  }

  public IScriptMap createScriptMap()
  {
    return null;
  }

  public IScriptableObject createScriptObject(XulScriptableObject paramXulScriptableObject)
  {
    V8ScriptObject localV8ScriptObject = new V8ScriptObject(getScriptableClass(this, paramXulScriptableObject));
    localV8ScriptObject._extScriptableObject = paramXulScriptableObject;
    return localV8ScriptObject;
  }

  public boolean delIndexedString(String paramString)
  {
    return V8Engine.v8ContextRemoveIndexedString(this._nativeId, paramString);
  }

  public void destroy()
  {
  }

  protected void finalize()
    throws Throwable
  {
    V8Engine.v8DestroyScriptContext(this._nativeId);
    super.finalize();
  }

  public IScript getFunction(Object paramObject, String paramString)
  {
    return getFunction(paramString);
  }

  public V8ScriptFunction getFunction(String paramString)
  {
    return V8ScriptFunction.wrapNativeFunction(this, V8Engine.v8GetFunction(this._nativeId, paramString));
  }

  public String getScriptType()
  {
    return "javascript";
  }

  public void init()
  {
  }

  public V8ScriptFunction v8CompileFunction(String paramString1, String paramString2, int paramInt)
  {
    return V8ScriptFunction.wrapNativeFunction(this, V8Engine.v8CompileFunction(this._nativeId, paramString1 + "\n", paramString2, paramInt - 1));
  }

  public V8Script v8CompileScript(String paramString1, String paramString2, int paramInt)
  {
    return V8Script.wrapNativeScript(this, V8Engine.v8Compile(this._nativeId, paramString1, paramString2, paramInt - 1));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Script.V8.V8ScriptContext
 * JD-Core Version:    0.6.2
 */