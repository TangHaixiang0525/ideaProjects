package com.intertrust.wasabi.drm;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public final class DateTime
{
  private int day;
  private int hours;
  private int milliseconds;
  private int minutes;
  private int month;
  private int seconds;
  private int year;

  DateTime()
  {
  }

  DateTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    this.year = paramInt1;
    this.month = paramInt2;
    this.day = paramInt3;
    this.hours = paramInt4;
    this.minutes = paramInt5;
    this.seconds = paramInt6;
    this.milliseconds = paramInt7;
  }

  public int getDay()
  {
    return this.day;
  }

  public int getHours()
  {
    return this.hours;
  }

  public int getMilliseconds()
  {
    return this.milliseconds;
  }

  public int getMinutes()
  {
    return this.minutes;
  }

  public int getMonth()
  {
    return this.month;
  }

  public int getSeconds()
  {
    return this.seconds;
  }

  public int getYear()
  {
    return this.year;
  }

  public Calendar toCalendar()
  {
    GregorianCalendar localGregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
    localGregorianCalendar.set(this.year, -1 + this.month, this.day, this.hours, this.minutes, this.seconds);
    localGregorianCalendar.set(14, this.milliseconds);
    return localGregorianCalendar;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.drm.DateTime
 * JD-Core Version:    0.6.2
 */