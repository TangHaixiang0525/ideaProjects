package com.starcor.core.domain;

import java.io.Serializable;

public class ChannelItemInfoV2
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String activity_id = "";
  public String aliasName = "";
  public String barrageCategory = "liveshow";
  public String bullet_screen = "";
  public String cameraposition = "";
  public String capability = "";
  public String categoryId = "";
  public int channelNum = -1;
  public String id = "";
  public String imgH = "";
  public String imgS = "";
  public String imgV = "";
  public String import_id = "";
  public String info = "";
  public String liveMulticastUrl = "";
  public String live_video_type = "";
  public String name = "";
  public String reportway_type = "";
  public int tsLimitMax = -1;
  public int tsLimitMin = -1;
  public int tsLimitPos = -1;
  public String tv_channel = "";
  public String type = "";
  public String type_name = "";

  public String toString()
  {
    return "ChannelItemInfoV2{type=" + this.type + ", id='" + this.id + '\'' + ", categoryId='" + this.categoryId + '\'' + ", channelNum=" + this.channelNum + ", name='" + this.name + '\'' + ", aliasName='" + this.aliasName + '\'' + ", imgH='" + this.imgH + '\'' + ", imgS='" + this.imgS + '\'' + ", imgV='" + this.imgV + '\'' + ", info='" + this.info + '\'' + ", tsLimitMin=" + this.tsLimitMin + ", tsLimitPos=" + this.tsLimitPos + ", tsLimitMax=" + this.tsLimitMax + ", capability='" + this.capability + '\'' + ", liveMulticastUrl='" + this.liveMulticastUrl + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.ChannelItemInfoV2
 * JD-Core Version:    0.6.2
 */