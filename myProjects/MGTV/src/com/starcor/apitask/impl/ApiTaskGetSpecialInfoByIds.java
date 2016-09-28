package com.starcor.apitask.impl;

import com.starcor.apitask.ParserHandler;
import com.starcor.apitask.ServerApiTask2;
import com.starcor.apitask.info.LiveShowSpecialInfo;
import com.starcor.apitask.info.LiveShowSpecialInfo.SpecialItem;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.StringParams;
import com.starcor.mgtv.boss.webUrlFormatter;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ApiTaskGetSpecialInfoByIds extends ServerApiTask2
{
  StringParams nns_special_ids = new StringParams("nns_special_ids");

  public ApiTaskGetSpecialInfoByIds(String[] paramArrayOfString)
  {
    this.prefix = AppInfo.URL_n24_a;
    this.nns_func.setValue("get_special_info_by_ids");
    StringBuilder localStringBuilder = new StringBuilder();
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
      localStringBuilder.append(paramArrayOfString[j]).append("|");
    if (localStringBuilder.length() > 0)
      localStringBuilder.deleteCharAt(-1 + localStringBuilder.length());
    this.nns_special_ids.setValue(localStringBuilder.toString());
  }

  public ParserHandler getDefaultHandler()
  {
    return new ParserHandler()
    {
      String curTagName;
      LiveShowSpecialInfo liveShowSpecialInfo = new LiveShowSpecialInfo();
      StringBuilder sb = new StringBuilder();
      LiveShowSpecialInfo.SpecialItem specialItem;

      public void characters(char[] paramAnonymousArrayOfChar, int paramAnonymousInt1, int paramAnonymousInt2)
        throws SAXException
      {
        super.characters(paramAnonymousArrayOfChar, paramAnonymousInt1, paramAnonymousInt2);
        this.sb.append(paramAnonymousArrayOfChar, paramAnonymousInt1, paramAnonymousInt2);
        String str = this.sb.toString();
        if ("special_id".equals(this.curTagName))
          this.specialItem.special_id = str;
        do
        {
          return;
          if ("name".equals(this.curTagName))
          {
            this.specialItem.name = str;
            return;
          }
          if ("link_url".equals(this.curTagName))
          {
            this.specialItem.linkUrl = str;
            return;
          }
          if ("begin_time".equals(this.curTagName))
          {
            this.specialItem.beginTime = str;
            return;
          }
        }
        while (!"end_time".equals(this.curTagName));
        this.specialItem.endTime = str;
      }

      public void endDocument()
        throws SAXException
      {
        super.endDocument();
        setResult(this.liveShowSpecialInfo);
      }

      public void endElement(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3)
        throws SAXException
      {
        super.endElement(paramAnonymousString1, paramAnonymousString2, paramAnonymousString3);
        if ("i".equals(paramAnonymousString2))
          this.specialItem = null;
        this.curTagName = null;
      }

      public void startElement(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Attributes paramAnonymousAttributes)
        throws SAXException
      {
        super.startElement(paramAnonymousString1, paramAnonymousString2, paramAnonymousString3, paramAnonymousAttributes);
        this.sb.setLength(0);
        this.curTagName = paramAnonymousString2;
        if ("i".equals(paramAnonymousString2))
        {
          this.specialItem = new LiveShowSpecialInfo.SpecialItem();
          this.liveShowSpecialInfo.specialItems.add(this.specialItem);
        }
      }
    };
  }

  public String getTaskName()
  {
    return "N24_A_5";
  }

  public String getUrl()
  {
    String str = buildUrlPrefix() + this.nns_func + this.nns_special_ids + this.suffix;
    return webUrlFormatter.i().formatUrl(str, getTaskName());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.apitask.impl.ApiTaskGetSpecialInfoByIds
 * JD-Core Version:    0.6.2
 */