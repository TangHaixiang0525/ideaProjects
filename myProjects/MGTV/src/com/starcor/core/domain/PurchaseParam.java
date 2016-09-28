package com.starcor.core.domain;

import com.starcor.core.utils.Logger;

public class PurchaseParam
{
  public static final String TAG = PurchaseParam.class.getSimpleName();
  public int asset_type = -1;
  public boolean autoJump = false;
  public String categoryId = "";
  public String def = "";
  public String import_id = "";
  public String index_import_id = "";
  public String istry = "0";
  public int media_type = 1;
  public int mode = 0;
  public String nns_begin = "";
  public String nns_day = "";
  public String packageId = "";
  private String pagename = "";
  public String serialId = "";
  public String specialId = "";
  public String time_len = "";
  public String videoId = "";
  public int videoIndex = 0;
  public String videoName = "";
  public long videoPlayedTime = 0L;
  public String videoType = "";

  public PurchaseParam(boolean paramBoolean, String paramString1, String paramString2)
  {
    this.autoJump = paramBoolean;
    this.videoId = paramString1;
    this.videoType = paramString2;
  }

  public String getPagename()
  {
    Logger.i(TAG, "getPagename=" + this.pagename);
    return this.pagename;
  }

  public void setAsset_type(int paramInt)
  {
    this.asset_type = paramInt;
  }

  public void setCategoryId(String paramString)
  {
    this.categoryId = paramString;
  }

  public void setImport_id(String paramString)
  {
    this.import_id = paramString;
  }

  public void setIndex(int paramInt)
  {
    this.videoIndex = paramInt;
  }

  public void setMedia_type(int paramInt)
  {
    this.media_type = paramInt;
  }

  public void setMode(int paramInt)
  {
    this.mode = paramInt;
  }

  public void setPackageId(String paramString)
  {
    this.packageId = paramString;
  }

  public void setPagename(String paramString)
  {
    Logger.i(TAG, "setPagename=" + paramString);
    this.pagename = paramString;
  }

  public void setPlaybackParams(String paramString1, String paramString2, String paramString3)
  {
    this.nns_day = paramString1;
    this.nns_begin = paramString2;
    this.time_len = paramString3;
  }

  public void setSerial_id(String paramString)
  {
    this.serialId = paramString;
  }

  public void setVideoName(String paramString)
  {
    this.videoName = paramString;
  }

  public void setVideoPlayedTime(long paramLong)
  {
    this.videoPlayedTime = paramLong;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.PurchaseParam
 * JD-Core Version:    0.6.2
 */