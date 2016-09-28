package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryItem
  implements Serializable
{
  public static final int CATEGORY_TYPE_GENERAL = 0;
  public static final int CATEGORY_TYPE_GUESS = 8;
  public static final int CATEGORY_TYPE_INDEX = 3;
  public static final int CATEGORY_TYPE_NONE = -1;
  public static final int CATEGORY_TYPE_PLAYBACK = 4;
  public static final int CATEGORY_TYPE_PLAYRECORD = 9;
  public static final int CATEGORY_TYPE_STAR = 10;
  public static final int CATEGORY_TYPE_TOP10 = 6;
  public static final int CATEGORY_TYPE_TOPIC = 2;
  public static final int CATEGORY_TYPE_VIRTUAL = 1;
  private static final long serialVersionUID = 1L;
  public String id = "";
  public String img_url = "";
  public int itemCount = -1;
  public ArrayList<Item> items = new ArrayList();
  public String name = "";
  public String parent = "";
  public int type;
  public int uiStyle = 0;

  public String toString()
  {
    return "CategoryItem [itemCount=" + this.itemCount + ", id=" + this.id + ", name=" + this.name + ", type=" + this.type + ", parent=" + this.parent + "]";
  }

  public class Item
  {
    public String id = "";
    public String name = "";
    public int type;

    public Item()
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.CategoryItem
 * JD-Core Version:    0.6.2
 */