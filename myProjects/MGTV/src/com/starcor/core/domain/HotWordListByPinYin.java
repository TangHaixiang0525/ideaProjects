package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class HotWordListByPinYin
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int count;
  public ArrayList<HotWord> hotWordList;

  public String toString()
  {
    return "HotWordList [count=" + this.count + ", hotWordList=" + this.hotWordList + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.HotWordListByPinYin
 * JD-Core Version:    0.6.2
 */