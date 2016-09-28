package com.starcor.core.sax;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UpdataServerTimeHander extends DefaultHandler
{
  private String str;
  public String time;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if (this.str != null)
    {
      this.str += new String(paramArrayOfChar, paramInt1, paramInt2);
      return;
    }
    this.str = new String(paramArrayOfChar, paramInt1, paramInt2);
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("time"))
      this.time = this.str.trim();
  }

  public String getTime()
  {
    return this.time;
  }

  public void startDocument()
    throws SAXException
  {
    this.time = "";
    this.str = "";
    super.startDocument();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.UpdataServerTimeHander
 * JD-Core Version:    0.6.2
 */