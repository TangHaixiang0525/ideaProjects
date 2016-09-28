package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchPlayStruct
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int item_num;
  public ArrayList<SearchLiveListItem> searchItems;

  public String toString()
  {
    return "item_num=" + this.item_num + ",searchItems=" + this.searchItems.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.SearchPlayStruct
 * JD-Core Version:    0.6.2
 */