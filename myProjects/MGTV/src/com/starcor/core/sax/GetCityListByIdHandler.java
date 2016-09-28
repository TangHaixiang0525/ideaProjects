package com.starcor.core.sax;

import com.starcor.core.domain.CityInfoById;
import com.starcor.core.domain.CityItemById;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetCityListByIdHandler extends DefaultHandler
{
  ArrayList<CityItemById> arrCityItem;
  private CityInfoById cityInfo;
  private CityItemById cityItem;

  public void endDocument()
    throws SAXException
  {
    this.cityInfo.setCityItems(this.arrCityItem);
    super.endDocument();
  }

  public CityInfoById getCityStruct()
  {
    return this.cityInfo;
  }

  public void startDocument()
    throws SAXException
  {
    this.arrCityItem = new ArrayList();
    this.cityInfo = new CityInfoById();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("cityinfo"));
    try
    {
      this.cityInfo.setCount(Integer.valueOf(paramAttributes.getValue("count")).intValue());
      if (paramString2.equalsIgnoreCase("list"))
      {
        this.cityItem = new CityItemById();
        this.cityItem.setLevel(paramAttributes.getValue("level"));
        this.cityItem.setId(paramAttributes.getValue("id"));
        this.cityItem.setName(paramAttributes.getValue("name"));
        this.cityItem.setType(paramAttributes.getValue("type"));
        this.arrCityItem.add(this.cityItem);
      }
      super.startElement(paramString1, paramString2, paramString3, paramAttributes);
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      while (true)
        this.cityInfo.setCount(0);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetCityListByIdHandler
 * JD-Core Version:    0.6.2
 */