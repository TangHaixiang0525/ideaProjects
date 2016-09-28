package com.sohutv.tv.player.util;

import android.content.Context;
import android.text.TextUtils;
import com.sohutv.tv.player.play.a;
import com.sohutv.tv.player.play.b;
import com.sohutv.tv.player.play.c;

public class k
{
  public static a a(Context paramContext, String paramString)
  {
    if ((TextUtils.isEmpty(paramString)) || (paramContext == null))
      return null;
    if (paramString.equals("888"))
      return new c(paramContext);
    return new b(paramContext);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.util.k
 * JD-Core Version:    0.6.2
 */