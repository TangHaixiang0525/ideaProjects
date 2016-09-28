package com.starcor.xul.Script;

import com.starcor.xul.Utils.XulCachedHashMap;

public class XulScriptableClass
  implements IScriptableClass
{
  static XulCachedHashMap<String, XulScriptableClass> classCache = new XulCachedHashMap();
  private final String[] _clsMethods;
  String _clsName;
  private final String[] _clsProps;

  private XulScriptableClass(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    this._clsName = paramString;
    this._clsProps = paramArrayOfString1;
    this._clsMethods = paramArrayOfString2;
  }

  public static IScriptableClass createScriptableClass(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2)
  {
    XulScriptableClass localXulScriptableClass = (XulScriptableClass)classCache.get(paramString);
    if (localXulScriptableClass == null)
    {
      localXulScriptableClass = new XulScriptableClass(paramString, paramArrayOfString1, paramArrayOfString2);
      classCache.put(paramString, localXulScriptableClass);
    }
    return localXulScriptableClass;
  }

  public String getClassName()
  {
    return this._clsName;
  }

  public String[] getMethods()
  {
    return this._clsMethods;
  }

  public String[] getProperties()
  {
    return this._clsProps;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Script.XulScriptableClass
 * JD-Core Version:    0.6.2
 */