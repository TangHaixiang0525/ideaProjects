package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchStruct
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public AssetCategoryCountStruct asset_category_count;
  public int asset_pages_count;
  public int cur_page;
  public int item_num;
  public ArrayList<SearchListItem> sItemList;

  public String toString()
  {
    return "item_num=" + this.item_num + " asset_pages_count=" + this.asset_pages_count + " asset_category_count=" + this.asset_category_count + "sItemList=" + this.sItemList.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.SearchStruct
 * JD-Core Version:    0.6.2
 */