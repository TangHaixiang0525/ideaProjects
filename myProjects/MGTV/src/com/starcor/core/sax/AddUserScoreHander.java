package com.starcor.core.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AddUserScoreHander extends DefaultHandler
{
  private int state;

  public int getState()
  {
    return this.state;
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
    if (paramString2.equalsIgnoreCase("state"));
    try
    {
      this.state = Integer.valueOf(paramAttributes.getValue("id")).intValue();
      return;
    }
    catch (Exception localException)
    {
      this.state = 3;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.AddUserScoreHander
 * JD-Core Version:    0.6.2
 */