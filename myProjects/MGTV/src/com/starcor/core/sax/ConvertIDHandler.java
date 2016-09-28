package com.starcor.core.sax;

import com.starcor.core.domain.InjectingID;
import com.starcor.core.domain.InjectingID.Item;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ConvertIDHandler extends DefaultHandler
{
  private InjectingID injectingID;
  private InjectingID.Item item;
  private StringBuilder sb = new StringBuilder();
  private String tagName;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
    String str = this.sb.toString();
    if ("state".equals(this.tagName))
      this.injectingID.state = str;
    do
    {
      return;
      if ("reason".equals(this.tagName))
      {
        this.injectingID.reason = str;
        return;
      }
      if ("type".equals(this.tagName))
      {
        this.item.type = str;
        return;
      }
      if ("id".equals(this.tagName))
      {
        this.item.id = str;
        return;
      }
      if ("name".equals(this.tagName))
      {
        this.item.name = str;
        return;
      }
      if ("import_id".equals(this.tagName))
      {
        this.item.importId = str;
        return;
      }
      if ("original_id".equals(this.tagName))
      {
        this.item.originalId = str;
        return;
      }
    }
    while (!"import_source".equals(this.tagName));
    this.item.importSource = str;
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
    if (paramString2.equalsIgnoreCase("i"))
      this.injectingID.items.add(this.item);
  }

  public InjectingID getInjectingID()
  {
    return this.injectingID;
  }

  public void startDocument()
    throws SAXException
  {
    this.injectingID = new InjectingID();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    this.sb.setLength(0);
    this.tagName = paramString2;
    if (paramString2.equalsIgnoreCase("i"))
    {
      InjectingID localInjectingID = this.injectingID;
      localInjectingID.getClass();
      this.item = new InjectingID.Item(localInjectingID);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.ConvertIDHandler
 * JD-Core Version:    0.6.2
 */