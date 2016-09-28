package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryPosterList
  implements Serializable
{
  public static final String CATEGORY_ID_GUESS = "G000000";
  public static final String CATEGORY_ID_PLAYRECORD = "P000000";
  public String artithmeticId = "";
  public String categoryId = "";
  public String categoryName = "";
  public int categoryType = -1;
  public String estimateId = "";
  public ArrayList<CategoryPoster> posterList = new ArrayList();
  public int specialCount = 0;
  public int videoCount = 0;

  public String toString()
  {
    return "categoryId = " + this.categoryId + ", categoryName = " + this.categoryName + ", videoCount = " + this.videoCount + ", specialCount = " + this.specialCount + ", categoryType = " + this.categoryType;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.CategoryPosterList
 * JD-Core Version:    0.6.2
 */