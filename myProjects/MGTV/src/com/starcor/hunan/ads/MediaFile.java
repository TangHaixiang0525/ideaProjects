package com.starcor.hunan.ads;

import android.text.TextUtils;

public class MediaFile
{
  int event;
  int h;
  String type;
  String url;
  String value;
  int w;

  public boolean equals(Object paramObject)
  {
    if (paramObject == null);
    MediaFile localMediaFile;
    do
    {
      return false;
      localMediaFile = (MediaFile)paramObject;
    }
    while ((this.event != localMediaFile.event) || (!this.type.equals(localMediaFile.type)) || (this.h != localMediaFile.h) || (this.w != localMediaFile.w) || (!this.url.equals(localMediaFile.url)) || (!this.value.equals(localMediaFile.value)));
    return true;
  }

  public int getEvent()
  {
    return this.event;
  }

  public int getH()
  {
    return this.h;
  }

  public String getType()
  {
    return this.type;
  }

  public String getUrl()
  {
    return this.url;
  }

  public String getValue()
  {
    return this.value;
  }

  public int getW()
  {
    return this.w;
  }

  public void setEvent(int paramInt)
  {
    this.event = paramInt;
  }

  public void setH(int paramInt)
  {
    this.h = paramInt;
  }

  public void setType(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      paramString = "";
    this.type = paramString;
  }

  public void setUrl(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      paramString = "";
    this.url = paramString;
  }

  public void setValue(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      paramString = "";
    this.value = paramString;
  }

  public void setW(int paramInt)
  {
    this.w = paramInt;
  }

  public String toString()
  {
    String str1 = "MediaFile:[" + "type:" + this.type;
    String str2 = str1 + ",w:" + this.w;
    String str3 = str2 + ",h:" + this.h;
    String str4 = str3 + ",url:" + this.url;
    String str5 = str4 + ",event:" + this.event;
    String str6 = str5 + ",value:" + this.value;
    return str6 + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.ads.MediaFile
 * JD-Core Version:    0.6.2
 */