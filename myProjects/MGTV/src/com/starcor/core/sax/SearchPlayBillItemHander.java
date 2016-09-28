package com.starcor.core.sax;

import com.starcor.core.domain.SearchLiveListItem;
import com.starcor.core.domain.SearchPlayStruct;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SearchPlayBillItemHander extends DefaultHandler
{
  private SearchLiveListItem item;
  private SearchPlayStruct searchLiveStruct;

  public SearchPlayStruct getSearchLiveStruct()
  {
    return this.searchLiveStruct;
  }

  public void startDocument()
    throws SAXException
  {
    this.searchLiveStruct = new SearchPlayStruct();
    this.searchLiveStruct.searchItems = new ArrayList();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("item_list"));
    while (!paramString2.equalsIgnoreCase("item"))
      try
      {
        this.searchLiveStruct.item_num = Integer.valueOf(paramAttributes.getValue("count_num")).intValue();
        return;
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        this.searchLiveStruct.item_num = 0;
        return;
      }
    this.item = new SearchLiveListItem();
    this.item.video_name = paramAttributes.getValue("video_name");
    this.item.desc = paramAttributes.getValue("text");
    this.item.video_id = paramAttributes.getValue("video_id");
    try
    {
      this.item.video_type = Integer.valueOf(paramAttributes.getValue("video_type")).intValue();
      this.item.day = paramAttributes.getValue("day");
      this.item.time_begin = paramAttributes.getValue("begin");
      this.item.time_len = paramAttributes.getValue("time_len");
      this.searchLiveStruct.searchItems.add(this.item);
      return;
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      while (true)
        this.item.video_type = 2;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.SearchPlayBillItemHander
 * JD-Core Version:    0.6.2
 */