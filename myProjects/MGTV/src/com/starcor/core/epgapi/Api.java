package com.starcor.core.epgapi;

public abstract class Api
{
  public static final int CONNECTION_LOST = 601;
  public static final int CONNECT_FAILED = 600;
  public static final int FORMAT_JSON = 1;
  public static final int FORMAT_XML = 0;
  public static final int LOCAL_COMM_ERROR = 603;
  public static final int SERVER_DATA_ERROR = 602;
  public static int TIMEZONE_PLUS_EIGHT = 8;
  public static int TIMEZONE_PLUS_SEVEN = 7;
  protected long cacheValidTime = 0L;
  protected StringParams nns_func = new StringParams("nns_func");
  protected String prefix = "";
  protected int retryNum = 3;
  protected String suffix = "&nns_user_agent=nn_player%2Fstd%2F1.0.0";
  protected int taksCategory;

  public abstract String getApiName();

  public long getCacheValidTime()
  {
    return this.cacheValidTime;
  }

  public int getExpirationtime()
  {
    return 0;
  }

  public String getPrefix()
  {
    return this.prefix;
  }

  public int getRetryNum()
  {
    return this.retryNum;
  }

  public int getTaksCategory()
  {
    return this.taksCategory;
  }

  public void setCacheValidTime(long paramLong)
  {
    this.cacheValidTime = paramLong;
  }

  public void setExpirationtime(int paramInt)
  {
    this.cacheValidTime = paramInt;
  }

  public void setResultFormat(int paramInt)
  {
    if (paramInt == 0)
      this.suffix = "&nns_user_agent=nn_player%2Fstd%2F1.0.0";
    while (1 != paramInt)
      return;
    this.suffix = "&nns_output_type=json&nns_user_agent=nn_player%2Fstd%2F1.0.0";
  }

  public void setRetryNum(int paramInt)
  {
    this.retryNum = paramInt;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.Api
 * JD-Core Version:    0.6.2
 */