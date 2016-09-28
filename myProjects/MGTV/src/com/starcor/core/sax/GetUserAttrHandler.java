package com.starcor.core.sax;

import com.starcor.core.domain.UserAttr;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetUserAttrHandler extends DefaultHandler
{
  private StringBuilder sb = new StringBuilder();
  private String tagName;
  private UserAttr userAttr;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
    String str = this.sb.toString();
    if ("state".equals(this.tagName))
      this.userAttr.state = str;
    do
    {
      return;
      if ("reason".equals(this.tagName))
      {
        this.userAttr.reason = str;
        return;
      }
      if ("type".equals(this.tagName))
      {
        this.userAttr.areaCode = str;
        return;
      }
    }
    while (!"attr".equals(this.tagName));
    this.userAttr.attr = str;
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
  }

  public UserAttr getUserAttr()
  {
    return this.userAttr;
  }

  public void startDocument()
    throws SAXException
  {
    this.userAttr = new UserAttr();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
    this.sb.setLength(0);
    this.tagName = paramString2;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetUserAttrHandler
 * JD-Core Version:    0.6.2
 */