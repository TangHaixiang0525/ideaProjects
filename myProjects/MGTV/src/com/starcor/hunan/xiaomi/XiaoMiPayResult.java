package com.starcor.hunan.xiaomi;

public class XiaoMiPayResult
{
  public int code;
  public String createTime;
  public String customerOrderId;
  public String dueTime;
  public String expireDate;
  public String orderId;
  public String payAccessToken;
  public int status;
  public String status_msg;
  public String xiaomiId;

  public String toString()
  {
    return "XiaoMiPayResult{dueTime='" + this.dueTime + '\'' + ", createTime='" + this.createTime + '\'' + ", xiaomiId='" + this.xiaomiId + '\'' + ", status=" + this.status + ", status_msg='" + this.status_msg + '\'' + ", code=" + this.code + ", payAccessToken='" + this.payAccessToken + '\'' + ", expireDate='" + this.expireDate + '\'' + ", orderId='" + this.orderId + '\'' + ", customerOrderId='" + this.customerOrderId + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.xiaomi.XiaoMiPayResult
 * JD-Core Version:    0.6.2
 */