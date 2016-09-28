package com.sohu.logger.bean;

import com.sohu.logger.util.LoggerUtil;
import java.util.Map;

public class UserActionLogItem extends LogItem
{
  private static final long serialVersionUID = -6826197618305974764L;
  private long startTamp;

  public void fillParams()
  {
    String str = getParamsMapValue("time");
    if (((Integer.toString(1002).equals(getParamsMapValue("url"))) && ("1".equals(getParamsMapValue("memo"))) && (this.startTamp > 0L)) || ((Integer.toString(2001).equals(getParamsMapValue("url"))) && (this.startTamp > 0L)) || ((Integer.toString(2004).equals(getParamsMapValue("url"))) && (this.startTamp > 0L)))
      this.paramsMap.put("time", str + "_" + this.startTamp);
    while (true)
    {
      this.paramsMap.put("enterid", LoggerUtil.getEnterId());
      return;
      this.paramsMap.put("time", getParamsMapValue("time"));
    }
  }

  public void initParams()
  {
    this.mItemType = 0;
    this.paramsMap.put("type", String.valueOf(1));
  }

  public boolean needSendByHeartbeat()
  {
    return true;
  }

  public boolean needSendRealtime()
  {
    String str = getParamsMapValue("url");
    return (String.valueOf(1002).equals(str)) || (String.valueOf(2002).equals(str)) || (String.valueOf(2003).equals(str)) || (String.valueOf(2004).equals(str)) || (String.valueOf(2001).equals(str));
  }

  public void setStartStamp(long paramLong)
  {
    this.startTamp = paramLong;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.bean.UserActionLogItem
 * JD-Core Version:    0.6.2
 */