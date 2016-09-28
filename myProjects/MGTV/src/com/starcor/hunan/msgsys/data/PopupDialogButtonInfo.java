package com.starcor.hunan.msgsys.data;

import java.util.Hashtable;

public class PopupDialogButtonInfo
{
  private static final String TAG = PopupDialogButtonInfo.class.getSimpleName();
  public String mAction = "";
  public String mButtonTitle = "";
  public String mDefaultIcon = "";
  public String mFocusedIcon = "";
  public Hashtable<String, String> mKeyValueList = new Hashtable();

  public String toString()
  {
    return TAG + "=[" + "buttonTitle=" + this.mButtonTitle + " action=" + this.mAction + " focusedIcon=" + this.mFocusedIcon + " defaultIcon=" + this.mDefaultIcon + " argList=" + this.mKeyValueList + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.data.PopupDialogButtonInfo
 * JD-Core Version:    0.6.2
 */