package com.sohu.logger.bean;

import java.util.Map;

public class PlayQualityLogItem extends LogItem
{
  private static final long serialVersionUID = -8515515333801342368L;

  public void fillParams()
  {
    if (this.paramsMap != null)
    {
      this.paramsMap.put("isp2p", "0");
      this.paramsMap.put("buffernm", "0");
    }
  }

  public void initParams()
  {
    this.mItemType = 2;
  }

  public boolean needSendByHeartbeat()
  {
    return false;
  }

  public boolean needSendRealtime()
  {
    return true;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.bean.PlayQualityLogItem
 * JD-Core Version:    0.6.2
 */