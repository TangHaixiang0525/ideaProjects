package com.starcor.core.sax;

import com.starcor.core.domain.Skin;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetPictureHandler extends DefaultHandler
{
  private boolean isSkin = false;
  private List<Skin> skins = new ArrayList();

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
  }

  public void endDocument()
    throws SAXException
  {
    super.endDocument();
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
  }

  public List<Skin> getSkins()
  {
    return this.skins;
  }

  public void startDocument()
    throws SAXException
  {
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
    if ("skin".equals(paramString3))
      this.isSkin = true;
    if (("item".equals(paramString3)) && (this.isSkin))
    {
      Skin localSkin = new Skin();
      String str1 = paramAttributes.getValue("id");
      String str2 = paramAttributes.getValue("name");
      String str3 = paramAttributes.getValue("url");
      localSkin.setId(str1);
      localSkin.setName(str2);
      localSkin.setUrl(str3);
      this.skins.add(localSkin);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetPictureHandler
 * JD-Core Version:    0.6.2
 */