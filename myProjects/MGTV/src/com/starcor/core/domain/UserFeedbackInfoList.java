package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class UserFeedbackInfoList
  implements Serializable
{
  public int count = 0;
  public ArrayList<UserFeedbackInfo> lists = new ArrayList();
  public String reason = "";
  public String state = "";

  public class UserFeedbackInfo
    implements Serializable
  {
    public String content = "";
    public String id = "";
    public String title = "";

    public UserFeedbackInfo()
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.UserFeedbackInfoList
 * JD-Core Version:    0.6.2
 */