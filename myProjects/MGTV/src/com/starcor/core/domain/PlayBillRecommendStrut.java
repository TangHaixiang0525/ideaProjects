package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayBillRecommendStrut extends PlayBillStruct
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public ArrayList<PlayBillRecommendItem> arrPlayBill;

  public String toString()
  {
    return "PlayBillStruct [day=" + this.day + ", tsLimitMin=" + this.tsLimitMin + ", tsLimitMax=" + this.tsLimitMax + ", tsDefaultPos=" + this.tsDefaultPos + ", arrPlayBill=" + this.arrPlayBill + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.PlayBillRecommendStrut
 * JD-Core Version:    0.6.2
 */