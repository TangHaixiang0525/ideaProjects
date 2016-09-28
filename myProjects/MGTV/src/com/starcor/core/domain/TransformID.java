package com.starcor.core.domain;

import java.io.Serializable;
import java.util.List;

public class TransformID
  implements Serializable
{
  public String Count;
  public List<ResultList> ResultList;

  public String toString()
  {
    return "TransformID{Count='" + this.Count + '\'' + ", ResultList=" + this.ResultList + '}';
  }

  public static class ResultList
  {
    public String ContentCode;
    public String ContentID;
    public String ContentType;
    public String Description;
    public String ReturnCode;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.TransformID
 * JD-Core Version:    0.6.2
 */