package com.sohu.logger.bean;

import java.util.HashMap;
import java.util.Map;

public class VideoPlayInfoLogItem extends LogItem
{
  private static final long serialVersionUID = -1864807545889042866L;

  public void fillParams()
  {
    String str1 = getParamsMapValue("os");
    if (!str1.startsWith("Android"))
      this.paramsMap.put("os", "Android" + str1);
    String str2 = getParamsMapValue("type");
    if ((!str2.equalsIgnoreCase("my")) && (!str2.equalsIgnoreCase("ktv")))
    {
      if (this.paramsNotNeedMap == null)
        this.paramsNotNeedMap = new HashMap();
      this.paramsNotNeedMap.put("o", "o");
    }
    if (!str2.equalsIgnoreCase("ktv"))
    {
      if (this.paramsNotNeedMap == null)
        this.paramsNotNeedMap = new HashMap();
      this.paramsNotNeedMap.put("ktvld", "ktvld");
    }
  }

  public void initParams()
  {
    this.mItemType = 4;
    this.paramsMap.put("action", "0");
    this.paramsMap.put("tvid", "0");
    this.paramsMap.put("online", "0");
    this.paramsMap.put("position", "0");
    this.paramsMap.put("duration", "0");
    this.paramsMap.put("plat", "ott");
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
 * Qualified Name:     com.sohu.logger.bean.VideoPlayInfoLogItem
 * JD-Core Version:    0.6.2
 */