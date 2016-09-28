package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class AAAOrderRecordList
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int err = -1;
  public int nowPage = -1;
  public String reason = "";
  public ArrayList<AAAOrderRecordItem> recordList = null;
  public String state = "";
  public String status = "";
  public int total = -1;

  public String toString()
  {
    return "AAAOrderRecordList{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", err=" + this.err + ", status='" + this.status + '\'' + ", total=" + this.total + ", nowPage=" + this.nowPage + ", recordList=" + this.recordList + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAOrderRecordList
 * JD-Core Version:    0.6.2
 */