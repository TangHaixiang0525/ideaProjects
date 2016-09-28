package com.sohu.logger.bean;

import java.util.Map;

public class VideoPlayLogItem extends LogItem
{
  private static final long serialVersionUID = 7050512717518099294L;

  public void fillParams()
  {
  }

  public void initParams()
  {
    this.mItemType = 1;
    this.paramsMap.put("isedit", "0");
    this.paramsMap.put("enterid", "0");
    this.paramsMap.put("ltype", "0");
    this.paramsMap.put("wtype", "1");
    this.paramsMap.put("playmode", "0");
    this.paramsMap.put("screen", "0");
  }

  public boolean needSendByHeartbeat()
  {
    return true;
  }

  public boolean needSendRealtime()
  {
    return true;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.bean.VideoPlayLogItem
 * JD-Core Version:    0.6.2
 */