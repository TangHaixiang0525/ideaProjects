package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayBillStruct
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public ArrayList<PlayBillItem> arrPlayBill;
  public String day = "";
  public int tsDefaultPos = 0;
  public int tsLimitMax = 0;
  public int tsLimitMin = 0;

  public String toString()
  {
    return "PlayBillStruct [day=" + this.day + ", tsLimitMin=" + this.tsLimitMin + ", tsLimitMax=" + this.tsLimitMax + ", tsDefaultPos=" + this.tsDefaultPos + ", arrPlayBill=" + this.arrPlayBill + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.PlayBillStruct
 * JD-Core Version:    0.6.2
 */