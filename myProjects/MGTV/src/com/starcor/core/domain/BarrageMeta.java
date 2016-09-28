package com.starcor.core.domain;

public class BarrageMeta
{
  public String avatar = "";
  public String content = "";
  public String createTime = "";
  public int fontColor;
  public int fontSize;
  public int interval;
  public String nickName = "";
  public int pos;
  public long rcvTime;
  public String uuid = "";

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("BarrageMeta [");
    localStringBuffer.append("uuid: " + this.uuid);
    localStringBuffer.append(", nickName: " + this.nickName);
    localStringBuffer.append(", createTime: " + this.createTime);
    localStringBuffer.append(", pos: " + this.pos);
    localStringBuffer.append(", content: " + this.content);
    localStringBuffer.append(", interval: " + this.interval);
    localStringBuffer.append(", avatar: " + this.avatar);
    localStringBuffer.append(", fontSize: " + this.fontSize);
    localStringBuffer.append(", fontColor: " + this.fontColor);
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.BarrageMeta
 * JD-Core Version:    0.6.2
 */