package com.starcor.core.domain;

import java.io.Serializable;

public class WeatherItem
  implements Serializable
{
  private static final long serialVersionUID = 2L;
  public String desc = "";
  public String img_url = "";
  public int index;
  public int temp_current;
  public int temp_high;
  public int temp_low;
  public String wind = "";
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.WeatherItem
 * JD-Core Version:    0.6.2
 */