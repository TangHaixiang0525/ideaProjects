package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class SpecialTopicPkgCntLstInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String count_num = "";
  public ArrayList<SpecialTopicPkgCntLstStruct> sTopicPkgCntLstStructs;

  public String toString()
  {
    return "SpecialTopicPkgCntLstInfo{count_num='" + this.count_num + '\'' + ", sTopicPkgCntLstStructs=" + this.sTopicPkgCntLstStructs + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.SpecialTopicPkgCntLstInfo
 * JD-Core Version:    0.6.2
 */