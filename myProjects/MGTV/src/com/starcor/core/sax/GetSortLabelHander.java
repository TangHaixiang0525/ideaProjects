package com.starcor.core.sax;

import com.starcor.core.domain.LabelSort;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetSortLabelHander extends DefaultHandler
{
  private LabelSort labelSort;
  private ArrayList<LabelSort> labelSortList;

  public ArrayList<LabelSort> getLabelSortList()
  {
    return this.labelSortList;
  }

  public void startDocument()
    throws SAXException
  {
    this.labelSortList = new ArrayList();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("video_label_type"))
    {
      this.labelSort = new LabelSort();
      this.labelSort.type_name = paramAttributes.getValue("type_name");
      this.labelSort.label_id = paramAttributes.getValue("type_id");
    }
    try
    {
      this.labelSort.label_count = Integer.valueOf(paramAttributes.getValue("video_label_count")).intValue();
      this.labelSortList.add(this.labelSort);
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      while (true)
        this.labelSort.label_count = 0;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetSortLabelHander
 * JD-Core Version:    0.6.2
 */