package com.starcor.xul.ScriptWrappr;

import android.util.Pair;
import com.starcor.xul.Script.IScriptArguments;
import com.starcor.xul.Script.IScriptContext;
import com.starcor.xul.Script.IScriptableClass;
import com.starcor.xul.Script.IScriptableObject;
import com.starcor.xul.Script.XulScriptableClass;
import com.starcor.xul.Script.XulScriptableObject;
import com.starcor.xul.Script.XulScriptableObject.XulScriptMethodInvoker;
import com.starcor.xul.ScriptWrappr.Annotation.ScriptGetter;
import com.starcor.xul.ScriptWrappr.Annotation.ScriptMethod;
import com.starcor.xul.ScriptWrappr.Annotation.ScriptSetter;
import com.starcor.xul.Utils.XulCachedHashMap;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public abstract class XulScriptableObjectWrapper<T> extends XulScriptableObject
{
  static Object[] params1 = new Object[1];
  static Object[] params2 = new Object[2];
  static XulCachedHashMap<String, ScriptableClassDesc> scriptableClassMap = new XulCachedHashMap();
  ScriptableClassDesc _clsDesc;
  T _xulItem = initUnwrappedObject(paramT);

  public XulScriptableObjectWrapper(T paramT)
  {
    _initialize();
  }

  private void _initialize()
  {
    if (this._clsDesc != null);
    String str;
    do
    {
      return;
      str = getClass().getSimpleName();
      this._clsDesc = ((ScriptableClassDesc)scriptableClassMap.get(str));
    }
    while (this._clsDesc != null);
    this._clsDesc = new ScriptableClassDesc(getClass());
    scriptableClassMap.put(str, this._clsDesc);
  }

  private static XulScriptableObject.XulScriptMethodInvoker createMethodInvoker(Method paramMethod)
  {
    if (paramMethod == null)
      return null;
    Class[] arrayOfClass = paramMethod.getParameterTypes();
    if ((arrayOfClass == null) || (arrayOfClass.length == 0))
      return new XulScriptableObject.XulScriptMethodInvoker()
      {
        public boolean invoke(XulScriptableObject paramAnonymousXulScriptableObject, IScriptContext paramAnonymousIScriptContext, IScriptArguments paramAnonymousIScriptArguments)
        {
          try
          {
            Object localObject2 = this.val$method.invoke(paramAnonymousXulScriptableObject, new Object[0]);
            localObject1 = localObject2;
            XulScriptableObjectWrapper.setScriptResult(localObject1, paramAnonymousIScriptArguments);
            return true;
          }
          catch (Exception localException)
          {
            while (true)
            {
              localException.printStackTrace();
              Object localObject1 = null;
            }
          }
        }
      };
    switch (arrayOfClass.length)
    {
    default:
      return new XulScriptableObject.XulScriptMethodInvoker()
      {
        public boolean invoke(XulScriptableObject paramAnonymousXulScriptableObject, IScriptContext paramAnonymousIScriptContext, IScriptArguments paramAnonymousIScriptArguments)
        {
          try
          {
            XulScriptableObjectWrapper.params2[0] = paramAnonymousIScriptContext;
            XulScriptableObjectWrapper.params2[1] = paramAnonymousIScriptArguments;
            this.val$method.invoke(paramAnonymousXulScriptableObject, XulScriptableObjectWrapper.params2);
            return true;
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
            return true;
          }
          finally
          {
            XulScriptableObjectWrapper.params2[0] = null;
            XulScriptableObjectWrapper.params2[1] = null;
          }
        }
      };
    case 1:
    }
    return new XulScriptableObject.XulScriptMethodInvoker()
    {
      public boolean invoke(XulScriptableObject paramAnonymousXulScriptableObject, IScriptContext paramAnonymousIScriptContext, IScriptArguments paramAnonymousIScriptArguments)
      {
        try
        {
          XulScriptableObjectWrapper.params1[0] = paramAnonymousIScriptContext;
          Object localObject3 = this.val$method.invoke(paramAnonymousXulScriptableObject, XulScriptableObjectWrapper.params1);
          localObject2 = localObject3;
          XulScriptableObjectWrapper.params1[0] = null;
          XulScriptableObjectWrapper.setScriptResult(localObject2, paramAnonymousIScriptArguments);
          return true;
        }
        catch (Exception localException)
        {
          while (true)
          {
            localException.printStackTrace();
            XulScriptableObjectWrapper.params1[0] = null;
            Object localObject2 = null;
          }
        }
        finally
        {
          XulScriptableObjectWrapper.params1[0] = null;
        }
      }
    };
  }

  private static void setScriptResult(Object paramObject, IScriptArguments paramIScriptArguments)
  {
    if ((paramObject instanceof IScriptableObject))
      paramIScriptArguments.setResult((IScriptableObject)paramObject);
    do
    {
      return;
      if ((paramObject instanceof Collection))
      {
        paramIScriptArguments.setResult((Collection)paramObject);
        return;
      }
      if ((paramObject instanceof Object[]))
      {
        paramIScriptArguments.setResult((Object[])paramObject);
        return;
      }
      if ((paramObject instanceof Boolean))
      {
        paramIScriptArguments.setResult(((Boolean)paramObject).booleanValue());
        return;
      }
      if ((paramObject instanceof String))
      {
        paramIScriptArguments.setResult((String)paramObject);
        return;
      }
      if ((paramObject instanceof Integer))
      {
        paramIScriptArguments.setResult(((Integer)paramObject).intValue());
        return;
      }
      if ((paramObject instanceof Float))
      {
        paramIScriptArguments.setResult(((Float)paramObject).floatValue());
        return;
      }
      if ((paramObject instanceof Double))
      {
        paramIScriptArguments.setResult(((Double)paramObject).doubleValue());
        return;
      }
    }
    while (!(paramObject instanceof Long));
    paramIScriptArguments.setResult(((Long)paramObject).longValue());
  }

  public XulScriptableObject.XulScriptMethodInvoker createMethodInvoker(int paramInt)
  {
    return createMethodInvoker(this._clsDesc._scriptMethods[paramInt]);
  }

  public XulScriptableObject.XulScriptMethodInvoker createMethodInvoker(String paramString)
  {
    return createMethodInvoker((Method)this._clsDesc._scriptMethodMap.get(paramString));
  }

  public Object getProperty(IScriptContext paramIScriptContext, int paramInt)
  {
    Pair localPair = this._clsDesc._scriptProperties[paramInt];
    if ((localPair == null) || (localPair.first == null))
      return null;
    try
    {
      params1[0] = paramIScriptContext;
      Object localObject2 = ((Method)localPair.first).invoke(this, params1);
      return localObject2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    finally
    {
      params1[0] = null;
    }
  }

  public Object getProperty(IScriptContext paramIScriptContext, String paramString)
  {
    Pair localPair = (Pair)this._clsDesc._scriptPropertyMap.get(paramString);
    if ((localPair == null) || (localPair.first == null))
      return null;
    try
    {
      params1[0] = paramIScriptContext;
      Object localObject2 = ((Method)localPair.first).invoke(this, params1);
      return localObject2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    finally
    {
      params1[0] = null;
    }
  }

  public IScriptableClass getScriptClass()
  {
    return XulScriptableClass.createScriptableClass(this._clsDesc._clsName, this._clsDesc._scriptPropertyNames, this._clsDesc._scriptMethodNames);
  }

  public Object getUnwrappedObject()
  {
    return this._xulItem;
  }

  protected T initUnwrappedObject(T paramT)
  {
    return paramT;
  }

  public Object putProperty(IScriptContext paramIScriptContext, int paramInt, Object paramObject)
  {
    Pair localPair = this._clsDesc._scriptProperties[paramInt];
    if ((localPair == null) || (localPair.second == null))
      return null;
    try
    {
      params2[0] = paramIScriptContext;
      params2[1] = paramObject;
      Object localObject2 = ((Method)localPair.second).invoke(this, params2);
      return localObject2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    finally
    {
      params2[0] = null;
      params2[1] = null;
    }
  }

  public Object putProperty(IScriptContext paramIScriptContext, String paramString, Object paramObject)
  {
    Pair localPair = (Pair)this._clsDesc._scriptPropertyMap.get(paramString);
    if ((localPair == null) || (localPair.second == null))
      return null;
    try
    {
      params2[0] = paramIScriptContext;
      params2[1] = paramObject;
      Object localObject2 = ((Method)localPair.second).invoke(this, params2);
      return localObject2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    finally
    {
      params2[0] = null;
      params2[1] = null;
    }
  }

  static class ScriptableClassDesc
  {
    Class<?> _cls;
    String _clsName;
    XulCachedHashMap<String, Method> _scriptMethodMap;
    String[] _scriptMethodNames;
    Method[] _scriptMethods;
    Pair<Method, Method>[] _scriptProperties;
    XulCachedHashMap<String, Pair<Method, Method>> _scriptPropertyMap;
    String[] _scriptPropertyNames;

    ScriptableClassDesc(Class<?> paramClass)
    {
      this._cls = paramClass;
      this._clsName = this._cls.getSimpleName();
      ArrayList localArrayList = new ArrayList();
      this._scriptPropertyMap = new XulCachedHashMap();
      Method[] arrayOfMethod = paramClass.getMethods();
      int i = 0;
      if (i < arrayOfMethod.length)
      {
        Method localMethod2 = arrayOfMethod[i];
        localMethod2.setAccessible(true);
        if ((ScriptMethod)localMethod2.getAnnotation(ScriptMethod.class) != null)
          localArrayList.add(localMethod2);
        ScriptSetter localScriptSetter;
        do
        {
          i++;
          break;
          ScriptGetter localScriptGetter = (ScriptGetter)localMethod2.getAnnotation(ScriptGetter.class);
          if (localScriptGetter != null)
          {
            String str4 = localScriptGetter.value();
            Pair localPair3 = (Pair)this._scriptPropertyMap.get(str4);
            if (localPair3 == null);
            for (Pair localPair4 = Pair.create(localMethod2, (Method)null); ; localPair4 = Pair.create(localMethod2, localPair3.second))
            {
              this._scriptPropertyMap.put(str4, localPair4);
              break;
            }
          }
          localScriptSetter = (ScriptSetter)localMethod2.getAnnotation(ScriptSetter.class);
        }
        while (localScriptSetter == null);
        String str3 = localScriptSetter.value();
        Pair localPair1 = (Pair)this._scriptPropertyMap.get(str3);
        if (localPair1 == null);
        for (Pair localPair2 = Pair.create((Method)null, localMethod2); ; localPair2 = Pair.create(localPair1.first, localMethod2))
        {
          this._scriptPropertyMap.put(str3, localPair2);
          break;
        }
      }
      this._scriptMethods = new Method[localArrayList.size()];
      this._scriptMethodNames = new String[localArrayList.size()];
      this._scriptMethodMap = new XulCachedHashMap();
      localArrayList.toArray(this._scriptMethods);
      int j = 0;
      int k = this._scriptMethods.length;
      while (j < k)
      {
        Method localMethod1 = this._scriptMethods[j];
        String str2 = ((ScriptMethod)localMethod1.getAnnotation(ScriptMethod.class)).value();
        this._scriptMethodNames[j] = str2;
        this._scriptMethodMap.put(str2, localMethod1);
        j++;
      }
      this._scriptPropertyNames = new String[this._scriptPropertyMap.size()];
      this._scriptPropertyMap.keySet().toArray(this._scriptPropertyNames);
      this._scriptProperties = new Pair[this._scriptPropertyMap.size()];
      for (int m = 0; m < this._scriptPropertyNames.length; m++)
      {
        String str1 = this._scriptPropertyNames[m];
        this._scriptProperties[m] = ((Pair)this._scriptPropertyMap.get(str1));
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.ScriptWrappr.XulScriptableObjectWrapper
 * JD-Core Version:    0.6.2
 */