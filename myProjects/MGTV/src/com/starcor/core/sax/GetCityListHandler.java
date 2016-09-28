package com.starcor.core.sax;

import com.starcor.core.domain.CityItem;
import com.starcor.core.domain.CityStruct;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetCityListHandler extends DefaultHandler
{
  ArrayList<CityItem> arrCityItem;
  private CityItem cityItem;
  private CityStruct cityStruct;

  public void endDocument()
    throws SAXException
  {
    this.cityStruct.setCity_item(this.arrCityItem);
    super.endDocument();
  }

  public CityStruct getCityStruct()
  {
    return this.cityStruct;
  }

  public void startDocument()
    throws SAXException
  {
    this.arrCityItem = new ArrayList();
    super.startDocument();
    this.cityStruct = new CityStruct();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("citylist"))
      this.cityStruct.setCity_type(paramAttributes.getValue("city_type"));
    try
    {
      this.cityStruct.setCount(Integer.valueOf(paramAttributes.getValue("count")).intValue());
      if (paramString2.equalsIgnoreCase("list"))
      {
        this.cityItem = new CityItem();
        this.cityItem.setId(paramAttributes.getValue("id"));
        this.cityItem.setName(paramAttributes.getValue("name"));
        this.cityItem.setPid(paramAttributes.getValue("pid"));
        this.cityItem.setPinyin_pre(paramAttributes.getValue("pinyin_pre"));
        this.cityItem.setType(paramAttributes.getValue("type"));
        this.arrCityItem.add(this.cityItem);
      }
      super.startElement(paramString1, paramString2, paramString3, paramAttributes);
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      while (true)
        this.cityStruct.setCount(0);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetCityListHandler
 * JD-Core Version:    0.6.2
 */