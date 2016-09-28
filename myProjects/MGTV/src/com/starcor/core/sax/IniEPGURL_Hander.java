package com.starcor.core.sax;

import com.starcor.core.domain.AppInfo;
import com.starcor.core.domain.EPGMetaInfo;
import com.starcor.core.domain.MetadataGoup;
import com.starcor.core.domain.MetadataGroupPageIndex;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.utils.Logger;
import java.io.Serializable;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class IniEPGURL_Hander extends DefaultHandler
{
  private ArrayList<MetadataInfo> info;
  boolean isData = false;
  private EPGMetaInfo mEpgMetaInfo;
  private MetadataInfo metaInfo;
  private MetadataGoup metadataGoup;
  private MetadataGroupPageIndex metadataGroupPageIndex;

  public void endDocument()
    throws SAXException
  {
    AppInfo.setUserInfo(this.info);
    super.endDocument();
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("group"))
      if (this.mEpgMetaInfo.meta_group_list != null)
        this.mEpgMetaInfo.meta_group_list.add(this.metadataGoup);
    do
    {
      do
      {
        return;
        if (!paramString2.equalsIgnoreCase("page"))
          break;
      }
      while ((this.metadataGoup == null) || (this.metadataGoup.meta_index_list == null));
      this.metadataGoup.meta_index_list.add(this.metadataGroupPageIndex);
      return;
      if (paramString2.equalsIgnoreCase("data"))
      {
        this.isData = false;
        return;
      }
    }
    while ((!paramString2.equalsIgnoreCase("item")) || (!this.isData) || (this.metadataGroupPageIndex == null) || (this.metadataGroupPageIndex.meta_item_list == null));
    this.metadataGroupPageIndex.meta_item_list.add(this.metaInfo);
  }

  public ArrayList<MetadataGoup> getEpgMetadataGroup()
  {
    return this.mEpgMetaInfo.meta_group_list;
  }

  public ArrayList<Serializable> getMetaList()
  {
    Logger.e("meta_group=" + this.mEpgMetaInfo.meta_group_list.toString());
    Logger.e("meta_old_list = " + this.mEpgMetaInfo.metaList.toString());
    return this.mEpgMetaInfo.metaList;
  }

  public EPGMetaInfo getmEpgMetaInfo()
  {
    return this.mEpgMetaInfo;
  }

  public void startDocument()
    throws SAXException
  {
    if (this.mEpgMetaInfo == null)
      this.mEpgMetaInfo = new EPGMetaInfo();
    if (this.info == null)
      this.info = new ArrayList();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("epg"))
      AppInfo.nns_tag = paramAttributes.getValue("nns_tag");
    if (paramString2.equalsIgnoreCase("n3_a_a"))
      AppInfo.URL_n3_a_i[0] = paramAttributes.getValue("url");
    label873: 
    do
    {
      do
      {
        return;
        if (paramString2.equalsIgnoreCase("n3_a_b"))
        {
          AppInfo.URL_n3_a_i[1] = paramAttributes.getValue("url");
          return;
        }
        if (paramString2.equalsIgnoreCase("n3_a_c"))
        {
          AppInfo.URL_n3_a_i[2] = paramAttributes.getValue("url");
          return;
        }
        if (paramString2.equalsIgnoreCase("n3_a_d"))
        {
          AppInfo.URL_n3_a_i[3] = paramAttributes.getValue("url");
          Logger.d(" ---> 初始化      N3_A_D总入口地址获取成功");
          return;
        }
        if (paramString2.equalsIgnoreCase("n3_a_e"))
        {
          AppInfo.URL_n3_a_i[4] = paramAttributes.getValue("url");
          return;
        }
        if (paramString2.equalsIgnoreCase("n3_a_g"))
        {
          AppInfo.URL_n3_a_i[5] = paramAttributes.getValue("url");
          return;
        }
        if (paramString2.equalsIgnoreCase("n3_a_h"))
        {
          AppInfo.URL_n3_a_i[6] = paramAttributes.getValue("url");
          return;
        }
        if (paramString2.equalsIgnoreCase("n7_a"))
        {
          AppInfo.URL_n7_a = paramAttributes.getValue("url");
          return;
        }
        if (!paramString2.equalsIgnoreCase("item"))
          break label873;
        if (this.isData)
          break;
      }
      while ((!"menu".equals(paramAttributes.getValue("group"))) && (!"banner".equals(paramAttributes.getValue("group"))));
      this.metaInfo = new MetadataInfo();
      this.metaInfo.type = paramAttributes.getValue("type");
      this.metaInfo.packet_type = paramAttributes.getValue("packet_type");
      this.metaInfo.packet_id = paramAttributes.getValue("packet_id");
      this.metaInfo.category_id = paramAttributes.getValue("category_id");
      this.metaInfo.action_type = paramAttributes.getValue("action_type");
      this.metaInfo.name = paramAttributes.getValue("name");
      this.metaInfo.img_url = paramAttributes.getValue("image");
      this.metaInfo.img_url1 = paramAttributes.getValue("image1");
      this.metaInfo.img_url2 = paramAttributes.getValue("image2");
      this.metaInfo.group = paramAttributes.getValue("group");
      this.metaInfo.url = paramAttributes.getValue("url");
      this.metaInfo.ad = paramAttributes.getValue("ad");
      Logger.d("url---=:" + this.metaInfo.url);
      this.mEpgMetaInfo.metaList.add(this.metaInfo);
      return;
      this.metaInfo = new MetadataInfo();
      if ("service".equalsIgnoreCase(this.metadataGoup.type))
        this.info.add(this.metaInfo);
      this.metaInfo.img_url = paramAttributes.getValue("img0");
      this.metaInfo.img_url1 = paramAttributes.getValue("img1");
      this.metaInfo.img_url2 = paramAttributes.getValue("img2");
      this.metaInfo.packet_type = paramAttributes.getValue("type");
      this.metaInfo.video_id = paramAttributes.getValue("video_id");
      this.metaInfo.video_type = paramAttributes.getValue("video_type");
      this.metaInfo.video_index = paramAttributes.getValue("video_index");
      this.metaInfo.name = paramAttributes.getValue("name");
      this.metaInfo.info = paramAttributes.getValue("info");
      this.metaInfo.packet_id = paramAttributes.getValue("packet_id");
      this.metaInfo.category_id = paramAttributes.getValue("category_id");
      this.metaInfo.action_type = paramAttributes.getValue("action_type");
      this.metaInfo.url = paramAttributes.getValue("ex_url");
      this.metaInfo.ad = paramAttributes.getValue("ad");
      this.metaInfo.begin_time = paramAttributes.getValue("begin_time");
      this.metaInfo.time_len = paramAttributes.getValue("time_len");
      try
      {
        this.metaInfo.order = Integer.parseInt(paramAttributes.getValue("order"));
        try
        {
          this.metaInfo.uiStyle = Integer.parseInt(paramAttributes.getValue("ui_style"));
          return;
        }
        catch (NumberFormatException localNumberFormatException3)
        {
          this.metaInfo.uiStyle = -1;
          return;
        }
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        while (true)
          this.metaInfo.order = -1;
      }
      if (paramString2.equalsIgnoreCase("group"))
      {
        this.metadataGoup = new MetadataGoup();
        this.metadataGoup.type = paramAttributes.getValue("type");
        this.metadataGoup.meta_index_list = new ArrayList();
        return;
      }
      if (paramString2.equalsIgnoreCase("page"))
      {
        this.metadataGroupPageIndex = new MetadataGroupPageIndex();
        try
        {
          this.metadataGroupPageIndex.page_index = Integer.parseInt(paramAttributes.getValue("page"));
          this.metadataGroupPageIndex.meta_item_list = new ArrayList();
          return;
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          while (true)
            this.metadataGroupPageIndex.page_index = -1;
        }
      }
    }
    while (!paramString2.equalsIgnoreCase("data"));
    this.isData = true;
    Logger.e(this.mEpgMetaInfo.meta_group_list.toString());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.IniEPGURL_Hander
 * JD-Core Version:    0.6.2
 */