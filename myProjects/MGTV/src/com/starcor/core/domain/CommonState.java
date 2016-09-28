package com.starcor.core.domain;

public class CommonState
{
  public Result result;
  public String total;

  public String toString()
  {
    return "CommonState{result=" + this.result + ", total=" + this.total + '}';
  }

  public static class Result
  {
    public String reason;
    public String state;

    public String toString()
    {
      return "result{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + '}';
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.CommonState
 * JD-Core Version:    0.6.2
 */