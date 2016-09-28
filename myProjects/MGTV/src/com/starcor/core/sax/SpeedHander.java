package com.starcor.core.sax;

import com.starcor.core.domain.SpeedStruct;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SpeedHander extends DefaultHandler
{
  ArrayList<SpeedStruct> arrSpeed;
  private SpeedStruct speedStruct;

  public ArrayList<SpeedStruct> getArrSpeed()
  {
    return this.arrSpeed;
  }

  public void startDocument()
    throws SAXException
  {
    this.arrSpeed = new ArrayList();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("Item"))
    {
      this.speedStruct = new SpeedStruct();
      this.speedStruct.name = paramAttributes.getValue("name");
      this.speedStruct.id = paramAttributes.getValue("id");
      this.speedStruct.url = paramAttributes.getValue("url");
    }
    try
    {
      this.speedStruct.time = Integer.valueOf(paramAttributes.getValue("time")).intValue();
      this.arrSpeed.add(this.speedStruct);
      super.startElement(paramString1, paramString2, paramString3, paramAttributes);
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      while (true)
        this.speedStruct.time = 0;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.SpeedHander
 * JD-Core Version:    0.6.2
 */