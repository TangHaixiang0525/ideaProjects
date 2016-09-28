package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class WeatherList
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String current_day = "";
  public String current_week = "";
  public String dress = "";
  public ArrayList<WeatherItem> listWeatherIndex;
  public String name = "";
  public String uvi = "";
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.WeatherList
 * JD-Core Version:    0.6.2
 */