package com.starcor.core.sax;

import com.starcor.core.domain.CategoryItem;
import com.starcor.core.domain.MediaAssetsInfo;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetCategoryItemHander extends DefaultHandler
{
  private MediaAssetsInfo cInfo;
  private ArrayList<CategoryItem> cList;
  private String package_id;
  private String parent = null;
  private CategoryItem pd;

  public GetCategoryItemHander(String paramString)
  {
    this.package_id = paramString;
    this.cInfo = new MediaAssetsInfo();
  }

  public void endDocument()
    throws SAXException
  {
    this.cInfo.package_id = this.package_id;
    this.cInfo.cList = this.cList;
    super.endDocument();
  }

  public MediaAssetsInfo getcInfo()
  {
    return this.cInfo;
  }

  public void startDocument()
    throws SAXException
  {
    if (this.cList == null)
      this.cList = new ArrayList();
    while (true)
    {
      super.startDocument();
      return;
      this.cList.clear();
    }
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("category"))
    {
      this.parent = paramAttributes.getValue("parent");
      if (this.parent == null)
        break label175;
      this.pd = new CategoryItem();
      this.pd.id = paramAttributes.getValue("id");
      this.pd.name = paramAttributes.getValue("name");
      this.pd.type = Integer.parseInt(paramAttributes.getValue("type"));
      this.pd.img_url = paramAttributes.getValue("img2");
    }
    while (true)
    {
      try
      {
        this.pd.uiStyle = Integer.valueOf(paramAttributes.getValue("ui_style")).intValue();
        this.pd.parent = this.parent;
        this.cList.add(this.pd);
        super.startElement(paramString1, paramString2, paramString3, paramAttributes);
        return;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        this.pd.uiStyle = 0;
        continue;
      }
      label175: this.cInfo.category_id = paramAttributes.getValue("id");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetCategoryItemHander
 * JD-Core Version:    0.6.2
 */