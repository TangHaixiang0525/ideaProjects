package com.starcor.hunan.msgsys.data;

import java.util.ArrayList;

public class PopupDialogInfo
{
  private static final String TAG = PopupDialogInfo.class.getSimpleName();
  public ArrayList<PopupDialogButtonInfo> mButtonList = new ArrayList();
  public String mDialogDesc = "";
  public String mDialogSubTitle = "";
  public String mDialogTitle = "";
  public String mMessageId = "";
  public String mTopic = "";

  public String toString()
  {
    return TAG + "=[" + "dialogTitle=" + this.mDialogTitle + " dialogSubTitle=" + this.mDialogSubTitle + " messageId=" + this.mMessageId + " topic=" + this.mTopic + " buttonInfo=" + this.mButtonList + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.data.PopupDialogInfo
 * JD-Core Version:    0.6.2
 */