package com.starcor.hunan.msgsys.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.List;

public class SharedPreferencesUtil
{
  public static final String DEFAULT_STRING_VALUE = "SP_ITEM_IS_EMTPY";

  public static boolean getBoolean(Context paramContext, String paramString1, String paramString2)
  {
    return paramContext.getSharedPreferences(paramString1, 0).getBoolean(paramString2, true);
  }

  public static String getString(Context paramContext, String paramString1, String paramString2)
  {
    return paramContext.getSharedPreferences(paramString1, 0).getString(paramString2, "SP_ITEM_IS_EMTPY");
  }

  public static boolean setBoolean(Context paramContext, String paramString1, String paramString2, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(paramString1, 0).edit();
    localEditor.putBoolean(paramString2, paramBoolean);
    return localEditor.commit();
  }

  public static boolean setStrings(Context paramContext, String paramString, List<String> paramList1, List<String> paramList2)
  {
    if (paramList1.size() != paramList2.size())
      return false;
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(paramString, 0).edit();
    for (int i = 0; i < paramList1.size(); i++)
      localEditor.putString((String)paramList1.get(i), (String)paramList2.get(i));
    return localEditor.commit();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.utils.SharedPreferencesUtil
 * JD-Core Version:    0.6.2
 */