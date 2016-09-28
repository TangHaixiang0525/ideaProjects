package com.sohu.mma.tracking.c;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.sohu.mma.tracking.b.j;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class a
{
  public static String a(String paramString)
  {
    String str = "";
    Matcher localMatcher = Pattern.compile("^([\\w\\d]+):\\/\\/([\\w\\d\\-_]+(?:\\.[\\w\\d\\-_]+)*)").matcher(paramString);
    if (localMatcher.find())
      str = localMatcher.group(0);
    return str;
  }

  public static String a(String paramString, com.sohu.mma.tracking.b.a parama, com.sohu.mma.tracking.b.b paramb)
  {
    String str1;
    label129: label135: label150: 
    do
    {
      while (true)
      {
        try
        {
          if ((!paramb.d.c.containsKey(parama.a)) || (!"md5".equals(paramb.d.c.get(parama.a))))
            break label129;
          if ("MAC".equals(parama.a))
          {
            if (paramString == null)
            {
              str3 = "";
              break label135;
              if (parama.c)
                break label150;
              if (str1 != null)
                break;
              return "";
            }
            str3 = paramString.replaceAll(":", "");
            break label135;
            str1 = b(str3.toUpperCase());
            continue;
            String str2 = URLEncoder.encode(str1, "utf-8");
            return str2;
          }
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          return "";
        }
        String str3 = paramString;
        break label135;
        str1 = paramString;
        continue;
        if (str3 == null)
          str1 = "";
      }
      return str1;
    }
    while (str1 != null);
    return "";
  }

  public static String a(String paramString1, List<String> paramList, String paramString2, String paramString3)
  {
    Iterator localIterator = paramList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return paramString1;
      String str = (String)localIterator.next();
      if (paramString1.contains(paramString2 + str))
      {
        g.a("mma_" + paramString2 + str + paramString3 + "[^" + paramString2 + "]*");
        paramString1 = paramString1.replaceAll(paramString2 + str + paramString3 + "[^" + paramString2 + "]*", "");
      }
    }
  }

  private static List<String> a(List paramList)
  {
    Collections.sort(paramList, new b());
    return paramList;
  }

  public static Map a(String paramString1, List<String> paramList, String paramString2, String paramString3, String paramString4)
  {
    List localList = a(paramList);
    HashMap localHashMap = new HashMap();
    Iterator localIterator = localList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        localHashMap.put("URL", paramString1);
        return localHashMap;
      }
      String str = (String)localIterator.next();
      if (paramString1.contains(paramString2 + str))
      {
        if (str.equals(paramString4))
        {
          Matcher localMatcher = Pattern.compile(paramString2 + str + "[^" + paramString2 + "]*").matcher(paramString1);
          if (localMatcher.find())
            localMatcher.group(0).replace(paramString2 + str, "");
        }
        paramString1 = paramString1.replaceAll(paramString2 + str + paramString3 + "[^" + paramString2 + "]*", "");
      }
    }
  }

  public static boolean a(Context paramContext)
  {
    if (paramContext != null)
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getNetworkInfo(1);
      if (localNetworkInfo != null)
        return localNetworkInfo.isAvailable();
    }
    return false;
  }

  public static String b(String paramString)
  {
    if ((paramString != null) && (!"".equals(paramString)))
      try
      {
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        localMessageDigest.update(paramString.getBytes());
        String str;
        for (paramString = new BigInteger(1, localMessageDigest.digest()).toString(16); ; paramString = str)
        {
          if (paramString.length() >= 32)
            return paramString;
          str = "0" + paramString;
        }
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
      {
        localNoSuchAlgorithmException.printStackTrace();
      }
    return paramString;
  }

  public static boolean b(Context paramContext)
  {
    if (paramContext != null)
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getNetworkInfo(0);
      if (localNetworkInfo != null)
        return localNetworkInfo.isAvailable();
    }
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mma.tracking.c.a
 * JD-Core Version:    0.6.2
 */