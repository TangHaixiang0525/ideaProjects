package com.sohutv.tv.player.entity;

import java.io.Serializable;
import java.util.List;
import org.apache.http.HttpStatus;

public class ResponsePlayInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String area_id;
  private int end_time;
  private int fee;
  private int free_play_time;
  private HttpStatus httpStatus;
  private int isPlay;
  private String is_sohu;
  private List<PlayUrl> playinfos;
  private String site_name;
  private int start_time;
  private String sub_code;
  private int time_length;
  private String tip;
  private String tv_application_time;
  private long tv_id;
  private String tv_name;
  private String video_hor_pic;
  private String video_ver_pic;

  public String getArea_id()
  {
    return this.area_id;
  }

  public int getEnd_time()
  {
    return this.end_time;
  }

  public int getFee()
  {
    return this.fee;
  }

  public int getFree_play_time()
  {
    return this.free_play_time;
  }

  public HttpStatus getHttpStatus()
  {
    return this.httpStatus;
  }

  public int getIsPlay()
  {
    return this.isPlay;
  }

  public String getIs_sohu()
  {
    return this.is_sohu;
  }

  public List<PlayUrl> getPlayinfos()
  {
    return this.playinfos;
  }

  public String getSite_name()
  {
    return this.site_name;
  }

  public int getStart_time()
  {
    return this.start_time;
  }

  public String getSub_code()
  {
    return this.sub_code;
  }

  public int getTime_length()
  {
    return this.time_length;
  }

  public String getTip()
  {
    return this.tip;
  }

  public String getTv_application_time()
  {
    return this.tv_application_time;
  }

  public long getTv_id()
  {
    return this.tv_id;
  }

  public String getTv_name()
  {
    return this.tv_name;
  }

  public String getVideo_hor_pic()
  {
    return this.video_hor_pic;
  }

  public String getVideo_ver_pic()
  {
    return this.video_ver_pic;
  }

  public void setArea_id(String paramString)
  {
    this.area_id = paramString;
  }

  public void setEnd_time(int paramInt)
  {
    this.end_time = paramInt;
  }

  public void setFee(int paramInt)
  {
    this.fee = paramInt;
  }

  public void setFree_play_time(int paramInt)
  {
    this.free_play_time = paramInt;
  }

  public void setHttpStatus(HttpStatus paramHttpStatus)
  {
    this.httpStatus = paramHttpStatus;
  }

  public void setIsPlay(int paramInt)
  {
    this.isPlay = paramInt;
  }

  public void setIs_sohu(String paramString)
  {
    this.is_sohu = paramString;
  }

  public void setPlayinfos(List<PlayUrl> paramList)
  {
    this.playinfos = paramList;
  }

  public void setSite_name(String paramString)
  {
    this.site_name = paramString;
  }

  public void setStart_time(int paramInt)
  {
    this.start_time = paramInt;
  }

  public void setSub_code(String paramString)
  {
    this.sub_code = paramString;
  }

  public void setTip(String paramString)
  {
    this.tip = paramString;
  }

  public void setTv_application_time(String paramString)
  {
    this.tv_application_time = paramString;
  }

  public void setTv_id(long paramLong)
  {
    this.tv_id = paramLong;
  }

  public void setTv_name(String paramString)
  {
    this.tv_name = paramString;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.entity.ResponsePlayInfo
 * JD-Core Version:    0.6.2
 */