package com.starcor.core.domain;

import java.io.Serializable;

public class AAAProductItem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String asset_name = "";
  public String beginDate = "";
  public String button_name = "";
  public int count = 0;
  public String desc = "";
  public String endDate = "";
  public String name = "";
  public float price = 0.0F;
  public float price_show = 0.0F;
  public int productId = -1;
  public int product_type = 0;
  public String time = "";
  public int type = -1;

  public String toString()
  {
    return "AAAProductItem{name='" + this.name + '\'' + ", button_name='" + this.button_name + '\'' + ", time='" + this.time + '\'' + ", endDate='" + this.endDate + '\'' + ", beginDate='" + this.beginDate + '\'' + ", productId=" + this.productId + ", price=" + this.price + ", price_show=" + this.price_show + ", type=" + this.type + ", count=" + this.count + ", asset_name='" + this.asset_name + '\'' + ", product_type=" + this.product_type + ", desc='" + this.desc + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAProductItem
 * JD-Core Version:    0.6.2
 */