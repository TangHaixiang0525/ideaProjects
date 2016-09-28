package com.starcor.core.utils;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class URLAnalysis
{
  public static HashMap<String, String> analysis(String paramString)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.clear();
    if (TextUtils.isEmpty(paramString));
    int i;
    do
    {
      return localHashMap;
      i = paramString.indexOf('?');
    }
    while (i < 0);
    String[] arrayOfString1 = paramString.substring(i + 1).split("&");
    int j = arrayOfString1.length;
    int k = 0;
    while (k < j)
    {
      String[] arrayOfString2 = arrayOfString1[k].split("=");
      String str1 = "";
      String str2 = "";
      try
      {
        str1 = arrayOfString2[0];
        str2 = arrayOfString2[1];
        label90: if (TextUtils.isEmpty(str1));
        while (true)
        {
          k++;
          break;
          localHashMap.put(str1, str2);
        }
      }
      catch (Exception localException)
      {
        break label90;
      }
    }
  }

  public static String getUrlPrefix(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return paramString;
    try
    {
      String str = paramString.substring(0, paramString.indexOf("?"));
      return str;
    }
    catch (Exception localException)
    {
    }
    return paramString;
  }

  public static String paramsToUrlString(HashMap<String, String> paramHashMap)
  {
    String str;
    if ((paramHashMap == null) || (paramHashMap.size() <= 0))
      str = "";
    do
    {
      return str;
      str = "";
      Iterator localIterator = paramHashMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (!TextUtils.isEmpty((CharSequence)localEntry.getKey()))
          str = str + "&" + (String)localEntry.getKey() + "=" + (String)localEntry.getValue();
      }
    }
    while (!str.startsWith("&"));
    return str.substring(1);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.utils.URLAnalysis
 * JD-Core Version:    0.6.2
 */