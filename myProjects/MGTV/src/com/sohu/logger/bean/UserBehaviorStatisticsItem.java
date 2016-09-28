package com.sohu.logger.bean;

import java.util.Map;

public class UserBehaviorStatisticsItem extends LogItem
{
  private static final long serialVersionUID = -3180065276739725344L;

  public void fillParams()
  {
  }

  public void initParams()
  {
    this.mItemType = 3;
    this.paramsMap.put("ltype", "0");
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
 * Qualified Name:     com.sohu.logger.bean.UserBehaviorStatisticsItem
 * JD-Core Version:    0.6.2
 */