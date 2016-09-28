package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class CityInfoById
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private ArrayList<CityItemById> cityItems;
  private int count;

  public ArrayList<CityItemById> getCityItems()
  {
    return this.cityItems;
  }

  public int getCount()
  {
    return this.count;
  }

  public void setCityItems(ArrayList<CityItemById> paramArrayList)
  {
    this.cityItems = paramArrayList;
  }

  public void setCount(int paramInt)
  {
    this.count = paramInt;
  }

  public String toString()
  {
    return "CityInfoById [count=" + this.count + ", cityItems=" + this.cityItems + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.CityInfoById
 * JD-Core Version:    0.6.2
 */