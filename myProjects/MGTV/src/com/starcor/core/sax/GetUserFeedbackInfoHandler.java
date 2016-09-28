package com.starcor.core.sax;

import android.text.TextUtils;
import com.starcor.core.domain.UserFeedbackInfoList;
import com.starcor.core.domain.UserFeedbackInfoList.UserFeedbackInfo;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetUserFeedbackInfoHandler extends DefaultHandler
{
  private UserFeedbackInfoList.UserFeedbackInfo infos;
  private StringBuilder sb = new StringBuilder();
  private String tagName;
  private UserFeedbackInfoList userFeedListInfoList;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
    String str1 = this.sb.toString();
    if ("state".equals(this.tagName))
      this.userFeedListInfoList.state = str1;
    do
    {
      String str2;
      do
      {
        return;
        if ("reason".equals(this.tagName))
        {
          this.userFeedListInfoList.reason = str1;
          return;
        }
        if ("count".equals(this.tagName))
          try
          {
            this.userFeedListInfoList.count = Integer.parseInt(str1);
            return;
          }
          catch (Exception localException)
          {
            this.userFeedListInfoList.count = 0;
            return;
          }
        if ("title".equals(this.tagName))
        {
          this.infos.title = str1;
          return;
        }
        if (!"content".equals(this.tagName))
          break;
        str2 = null;
        if (paramInt2 > 0)
          str2 = new String(paramArrayOfChar, 0, paramInt2);
      }
      while ((TextUtils.isEmpty(str2)) || (paramArrayOfChar[0] == '\n'));
      StringBuilder localStringBuilder = new StringBuilder();
      UserFeedbackInfoList.UserFeedbackInfo localUserFeedbackInfo = this.infos;
      localUserFeedbackInfo.content += str2;
      return;
    }
    while (!"id".equals(this.tagName));
    this.infos.id = str1;
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
    if (paramString2.equalsIgnoreCase("i"))
      this.userFeedListInfoList.lists.add(this.infos);
  }

  public UserFeedbackInfoList getUserFeedbackInfoList()
  {
    return this.userFeedListInfoList;
  }

  public void startDocument()
    throws SAXException
  {
    this.userFeedListInfoList = new UserFeedbackInfoList();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
    this.sb.setLength(0);
    this.tagName = paramString2;
    if (paramString2.equalsIgnoreCase("i"))
    {
      UserFeedbackInfoList localUserFeedbackInfoList = this.userFeedListInfoList;
      localUserFeedbackInfoList.getClass();
      this.infos = new UserFeedbackInfoList.UserFeedbackInfo(localUserFeedbackInfoList);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetUserFeedbackInfoHandler
 * JD-Core Version:    0.6.2
 */