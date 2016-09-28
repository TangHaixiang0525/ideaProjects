package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class CityStruct
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private ArrayList<CityItem> city_item;
  private String city_type = "";
  private int count;

  public ArrayList<CityItem> getCity_item()
  {
    return this.city_item;
  }

  public String getCity_type()
  {
    return this.city_type;
  }

  public int getCount()
  {
    return this.count;
  }

  public void setCity_item(ArrayList<CityItem> paramArrayList)
  {
    this.city_item = paramArrayList;
  }

  public void setCity_type(String paramString)
  {
    this.city_type = paramString;
  }

  public void setCount(int paramInt)
  {
    this.count = paramInt;
  }

  public String toString()
  {
    return "CityStruct [count=" + this.count + ",  city_type=" + this.city_type + ", city_item=" + this.city_item.toString() + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.CityStruct
 * JD-Core Version:    0.6.2
 */