package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class AAAProductList
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int err = -1;
  public ArrayList<AAAProductItem> productList = null;
  public String reason = "";
  public String state = "";
  public String status = "";
  public String type = "";
  public String vipEndDate = "";

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("AAAProductList{state='").append(this.state).append('\'').append(", reason='").append(this.reason).append('\'').append(", err=").append(this.err).append(", status='").append(this.status).append('\'').append(", type='").append(this.type).append('\'').append(", vipEndDate='").append(this.vipEndDate).append('\'').append(", productList=");
    if (this.productList == null);
    for (String str = "null"; ; str = this.productList.toString())
      return str + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AAAProductList
 * JD-Core Version:    0.6.2
 */