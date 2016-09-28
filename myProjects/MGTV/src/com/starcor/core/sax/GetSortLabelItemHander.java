package com.starcor.core.sax;

import com.starcor.core.domain.LabelSortItem;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetSortLabelItemHander extends DefaultHandler
{
  private LabelSortItem sortItem;
  private ArrayList<LabelSortItem> sortItemList;

  public ArrayList<LabelSortItem> getSortItemList()
  {
    return this.sortItemList;
  }

  public void startDocument()
    throws SAXException
  {
    this.sortItemList = new ArrayList();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("video_label"))
    {
      this.sortItem = new LabelSortItem();
      this.sortItem.id = paramAttributes.getValue("id");
      this.sortItem.name = paramAttributes.getValue("name");
    }
    try
    {
      this.sortItem.click_count = Integer.valueOf(paramAttributes.getValue("click")).intValue();
      this.sortItem.source = paramAttributes.getValue("source");
      this.sortItemList.add(this.sortItem);
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      while (true)
        this.sortItem.click_count = 0;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetSortLabelItemHander
 * JD-Core Version:    0.6.2
 */