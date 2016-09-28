package com.starcor.core.sax;

import com.starcor.core.domain.UiLayoutItemData;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetUiLayoutHandler extends DefaultHandler
{
  private String str;
  private UiLayoutItemData uiLayout;
  private ArrayList<UiLayoutItemData> uiList;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if (this.uiLayout != null)
      if (this.str != null)
        break label36;
    label36: for (this.str = new String(paramArrayOfChar, paramInt1, paramInt2); ; this.str += new String(paramArrayOfChar, paramInt1, paramInt2))
    {
      super.characters(paramArrayOfChar, paramInt1, paramInt2);
      return;
    }
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    if ("ui_layout".equalsIgnoreCase(paramString2))
    {
      this.uiLayout.xmlData = this.str;
      this.uiList.add(this.uiLayout);
      this.str = null;
      this.uiLayout = null;
    }
  }

  public ArrayList<UiLayoutItemData> getUiLayoutList()
  {
    return this.uiList;
  }

  public void startDocument()
    throws SAXException
  {
    if (this.uiList == null)
    {
      this.uiList = new ArrayList();
      return;
    }
    this.uiList.clear();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if ("ui_layout".equalsIgnoreCase(paramString2))
      this.uiLayout = new UiLayoutItemData();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetUiLayoutHandler
 * JD-Core Version:    0.6.2
 */