package com.sohu.logger.bean;

import java.util.Map;

public class RecommendedShowLogItem extends LogItem
{
  private static final long serialVersionUID = 6197264112007254116L;

  public void fillParams()
  {
    if (this.paramsMap != null)
      this.paramsMap.put("msg", "impression");
  }

  public void initParams()
  {
    this.mItemType = 5;
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
 * Qualified Name:     com.sohu.logger.bean.RecommendedShowLogItem
 * JD-Core Version:    0.6.2
 */