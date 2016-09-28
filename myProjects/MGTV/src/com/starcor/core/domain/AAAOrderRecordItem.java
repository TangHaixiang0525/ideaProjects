package com.starcor.core.domain;

import java.io.Serializable;

public class AAAOrderRecordItem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String beginDate = "";
  public String categoryStr = "";
  public String channelName = "";
  public int count = -1;
  public String endDate = "";
  public String payTime = "";
  public float price = 0.0F;
  public String productName = "";

  public String toString()
  {
    return "AAAOrderRecordItem{productName='" + this.productName + '\'' + ", channelName='" + this.channelName + '\'' + ", categoryStr='" + this.categoryStr + '\'' + ", payTime='" + this.payTime + '\'' + ", beginDate=" + this.beginDate + ", endDate=" + this.endDate + ", price=" + this.price + ", count=" + this.count + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAOrderRecordItem
 * JD-Core Version:    0.6.2
 */