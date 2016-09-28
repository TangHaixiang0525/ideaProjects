package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class SystemMessage
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int errorCode = 1;
  public String errorMsg = "";
  public String msgID = "";
  public ArrayList<SystemMessageBody> systemMsgList;

  public String toString()
  {
    String str = "errorCode=" + this.errorCode + ", errorMsg=" + this.errorMsg + ", msgID=" + this.msgID;
    if (this.systemMsgList != null)
      str = str + ", systemMsgList=" + this.systemMsgList.toString();
    return str;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.SystemMessage
 * JD-Core Version:    0.6.2
 */