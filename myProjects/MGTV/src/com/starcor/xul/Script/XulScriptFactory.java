package com.starcor.xul.Script;

import android.text.TextUtils;
import com.starcor.xul.Utils.XulCachedHashMap;
import java.util.List;

public class XulScriptFactory
{
  private static XulCachedHashMap<String, IScriptFactory> _factories = new XulCachedHashMap();

  public static IScriptContext createScriptContext(String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    IScriptFactory localIScriptFactory;
    do
    {
      return null;
      localIScriptFactory = (IScriptFactory)_factories.get(paramString);
    }
    while (localIScriptFactory == null);
    return localIScriptFactory.createContext();
  }

  public static boolean registerFactory(IScriptFactory paramIScriptFactory)
  {
    List localList = paramIScriptFactory.getSupportScriptTypes();
    if (localList == null)
      return false;
    for (int i = 0; i < localList.size(); i++)
    {
      String str = (String)localList.get(i);
      _factories.put(str, paramIScriptFactory);
    }
    return true;
  }

  public static abstract interface IScriptFactory
  {
    public abstract IScriptContext createContext();

    public abstract List<String> getSupportScriptTypes();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Script.XulScriptFactory
 * JD-Core Version:    0.6.2
 */