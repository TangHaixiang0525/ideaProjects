package com.starcor.core.domain;

import java.io.Serializable;

public class GetUserFeedbackInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String msg;
  public ResultDataInfo result_data;
  public int state;

  public String toString()
  {
    return "state=" + this.state + " msg=" + this.msg + " result_data=" + this.result_data;
  }

  public class ResultDataInfo
    implements Serializable
  {
    public String deviceId = "";
    public String id = "";

    public ResultDataInfo()
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.GetUserFeedbackInfo
 * JD-Core Version:    0.6.2
 */