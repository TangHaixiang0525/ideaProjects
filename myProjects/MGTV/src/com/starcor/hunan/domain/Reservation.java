package com.starcor.hunan.domain;

import android.text.TextUtils;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.hunan.service.ReservationService;
import com.starcor.hunan.service.SystemTimeManager;
import java.io.Serializable;

public class Reservation
  implements Comparable<Reservation>, Serializable
{
  private static final long serialVersionUID = 1L;
  private long _id;
  private String beginTime = "";
  private String categoryId;
  private String channel = "";
  private String day = "";
  private long expiredTime;
  private int film_type;
  private String huawei_cid;
  private String liveShowId;
  private String liveShowUrl;
  private String name;
  private int nns_index;
  private boolean notice = false;
  private String packageId;
  private long real_default_back_pos;
  private long real_max_back_time_len;
  private long real_min_back_time_len;
  private String reservationType = "player";
  private long reservation_time;
  private String special_id;
  private String timeLen = "";
  private String videoId = "";

  public int compareTo(Reservation paramReservation)
  {
    if (paramReservation == null)
      return -1;
    return (int)(GeneralUtils.getTimeInMillis(getDay() + getBeginTime()) - GeneralUtils.getTimeInMillis(paramReservation.getDay() + paramReservation.getBeginTime()));
  }

  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof Reservation;
    boolean bool2 = false;
    if (bool1)
    {
      if ((this.videoId.equals(((Reservation)paramObject).videoId)) && (this.reservation_time == ((Reservation)paramObject).reservation_time))
        bool2 = true;
    }
    else
      return bool2;
    return false;
  }

  public String getBeginTime()
  {
    return this.beginTime;
  }

  public String getCategoryId()
  {
    return this.categoryId;
  }

  public String getChannel()
  {
    return this.channel;
  }

  public String getDay()
  {
    return this.day;
  }

  public long getExpiredTime()
  {
    return 345600000L + this.reservation_time;
  }

  public int getFilm_type()
  {
    return this.film_type;
  }

  public String getHuawei_cid()
  {
    return this.huawei_cid;
  }

  public String getLiveShowId()
  {
    return this.liveShowId;
  }

  public String getLiveShowUrl()
  {
    return this.liveShowUrl;
  }

  public String getName()
  {
    return this.name;
  }

  public int getNns_index()
  {
    return this.nns_index;
  }

  public String getPackageId()
  {
    return this.packageId;
  }

  public long getReal_default_back_pos()
  {
    return this.real_default_back_pos;
  }

  public long getReal_max_back_time_len()
  {
    return this.real_max_back_time_len;
  }

  public long getReal_min_back_time_len()
  {
    return this.real_min_back_time_len;
  }

  public String getReservationType()
  {
    return this.reservationType;
  }

  public long getReservation_time()
  {
    return this.reservation_time;
  }

  public String getSpecial_id()
  {
    return this.special_id;
  }

  public String getTimeLen()
  {
    return this.timeLen;
  }

  public String getVideoId()
  {
    return this.videoId;
  }

  public long get_id()
  {
    return this._id;
  }

  public boolean isNotice()
  {
    return this.notice;
  }

  public void setBeginTime(String paramString)
  {
    this.beginTime = paramString;
    if ((!TextUtils.isEmpty(paramString)) && (!TextUtils.isEmpty(this.day)))
      this.reservation_time = GeneralUtils.time2Long(this.day + paramString);
  }

  public void setCategoryId(String paramString)
  {
    this.categoryId = paramString;
  }

  public void setChannel(String paramString)
  {
    this.channel = paramString;
  }

  public void setDay(String paramString)
  {
    this.day = paramString;
    if ((!TextUtils.isEmpty(this.beginTime)) && (!TextUtils.isEmpty(paramString)))
      this.reservation_time = GeneralUtils.time2Long(paramString + this.beginTime);
  }

  public void setExpiredTime(long paramLong)
  {
    this.expiredTime = paramLong;
  }

  public void setFilm_type(int paramInt)
  {
    this.film_type = paramInt;
  }

  public void setHuawei_cid(String paramString)
  {
    this.huawei_cid = paramString;
  }

  public void setLiveShowId(String paramString)
  {
    this.liveShowId = paramString;
  }

  public void setLiveShowUrl(String paramString)
  {
    this.liveShowUrl = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setNns_index(int paramInt)
  {
    this.nns_index = paramInt;
  }

  public void setNotice(boolean paramBoolean)
  {
    this.notice = paramBoolean;
  }

  public void setPackageId(String paramString)
  {
    this.packageId = paramString;
  }

  public void setReal_default_back_pos(long paramLong)
  {
    this.real_default_back_pos = paramLong;
  }

  public void setReal_max_back_time_len(long paramLong)
  {
    this.real_max_back_time_len = paramLong;
  }

  public void setReal_min_back_time_len(long paramLong)
  {
    this.real_min_back_time_len = paramLong;
  }

  public void setReservationType(String paramString)
  {
    this.reservationType = paramString;
  }

  public void setReservation_time()
  {
    this.reservation_time = ReservationService.time2Reservation(this.day + this.beginTime, Integer.parseInt(this.timeLen) + (int)GlobalEnv.getInstance().getReservationDelayNotifyTime() / 1000);
    if (AppFuncCfg.FUNCTION_MY_ORDER_DEBUG)
      this.reservation_time = (30000L + SystemTimeManager.getCurrentServerTime());
  }

  public void setSpecial_id(String paramString)
  {
    this.special_id = paramString;
  }

  public void setTimeLen(String paramString)
  {
    this.timeLen = paramString;
  }

  public void setVideoId(String paramString)
  {
    this.videoId = paramString;
  }

  public void set_id(long paramLong)
  {
    this._id = paramLong;
  }

  public String toString()
  {
    return "Reservation [_id=" + this._id + ", nns_index=" + this.nns_index + ", film_type=" + this.film_type + ", day=" + this.day + ", beginTime=" + this.beginTime + ", timeLen=" + this.timeLen + ", videoId=" + this.videoId + ", reservation_time=" + this.reservation_time + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.domain.Reservation
 * JD-Core Version:    0.6.2
 */