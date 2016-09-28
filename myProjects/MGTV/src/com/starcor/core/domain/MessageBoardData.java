package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageBoardData
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String content = "";
  public ArrayList<MessageBoardDataBody> messageBoardDataList;
  public String size = "";

  public String toString()
  {
    String str = ", size :" + this.size;
    if (this.messageBoardDataList != null)
      str = str + ", messageBoardDataList=" + this.messageBoardDataList.toString();
    return str;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.MessageBoardData
 * JD-Core Version:    0.6.2
 */