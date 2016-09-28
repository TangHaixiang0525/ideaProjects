package com.starcor.hunan.tcl;

import com.starcor.core.logic.GlobalLogic;

public class TCLTools
{
  public static String getUserKey()
  {
    String str1 = "V1|" + GlobalLogic.getInstance().getUserId();
    String str2 = str1 + "|";
    return str2 + GlobalLogic.getInstance().getWebToken();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.tcl.TCLTools
 * JD-Core Version:    0.6.2
 */