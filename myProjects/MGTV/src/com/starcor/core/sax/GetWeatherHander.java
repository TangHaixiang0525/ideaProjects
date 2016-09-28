package com.starcor.core.sax;

import com.starcor.core.domain.WeatherItem;
import com.starcor.core.domain.WeatherList;
import com.starcor.core.utils.Logger;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetWeatherHander extends DefaultHandler
{
  private WeatherItem wItem;
  private WeatherList wList;

  public WeatherList getwList()
  {
    return this.wList;
  }

  public void startDocument()
    throws SAXException
  {
    super.startDocument();
    this.wList = new WeatherList();
    this.wList.listWeatherIndex = new ArrayList();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("weather"))
    {
      this.wList.name = paramAttributes.getValue("name");
      Logger.d("City_name=" + this.wList.name);
      this.wList.current_day = paramAttributes.getValue("current_time");
      this.wList.current_week = paramAttributes.getValue("current_week");
      this.wList.dress = paramAttributes.getValue("dress");
      this.wList.uvi = paramAttributes.getValue("zwx");
    }
    while (!paramString2.equalsIgnoreCase("day"))
      return;
    this.wItem = new WeatherItem();
    try
    {
      this.wItem.index = Integer.parseInt(paramAttributes.getValue("index"));
    }
    catch (NumberFormatException localNumberFormatException3)
    {
      try
      {
        this.wItem.temp_low = Integer.parseInt(paramAttributes.getValue("temp_low"));
      }
      catch (NumberFormatException localNumberFormatException3)
      {
        try
        {
          this.wItem.temp_high = Integer.parseInt(paramAttributes.getValue("temp_high"));
        }
        catch (NumberFormatException localNumberFormatException3)
        {
          try
          {
            while (true)
            {
              this.wItem.temp_current = Integer.parseInt(paramAttributes.getValue("temp_current"));
              this.wItem.desc = paramAttributes.getValue("desc");
              this.wItem.wind = paramAttributes.getValue("wind");
              this.wItem.img_url = paramAttributes.getValue("img_url2");
              this.wList.listWeatherIndex.add(this.wItem);
              return;
              localNumberFormatException1 = localNumberFormatException1;
              this.wItem.index = -1;
              continue;
              localNumberFormatException2 = localNumberFormatException2;
              this.wItem.temp_low = -9999;
              continue;
              localNumberFormatException3 = localNumberFormatException3;
              this.wItem.temp_high = -9999;
            }
          }
          catch (NumberFormatException localNumberFormatException4)
          {
            while (true)
              this.wItem.temp_current = -9999;
          }
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetWeatherHander
 * JD-Core Version:    0.6.2
 */