package com.starcor.core.sax;

import com.starcor.core.domain.UserRecommendItem;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetUserRecommendHander extends DefaultHandler
{
  private UserRecommendItem item;
  private ArrayList<UserRecommendItem> list = new ArrayList();
  private StringBuilder sb = new StringBuilder();
  private String value = "";

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
    this.value = this.sb.toString();
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    if (paramString3.equals("state"))
      this.item.state = this.value;
    if (paramString3.equals("reason"))
      this.item.reason = this.value;
    if (paramString3.equals("type"))
      this.item.type = this.value;
    if (paramString3.equals("id"))
      this.item.id = this.value;
    if (paramString3.equals("name"))
      this.item.name = this.value;
    if (paramString3.equals("img_h"))
      this.item.img_h = this.value;
    if (paramString3.equals("img_s"))
      this.item.img_s = this.value;
    if (paramString3.equals("img_v"))
      this.item.img_v = this.value;
    if (paramString3.equals("info"))
      this.item.info = this.value;
    if (paramString3.equals("user_id"))
      this.item.user_id = this.value;
    if (paramString3.equals("video_type"));
    try
    {
      this.item.video_type = Integer.parseInt(this.value);
      if (paramString3.equals("recom_time"))
        this.item.recom_time = this.value;
      if (paramString3.equals("tstv_begin_time"))
        this.item.tstv_begin_time = this.value;
      if (paramString3.equals("tstv_time_len"))
        this.item.tstv_time_len = this.value;
      if (paramString3.equals("assist_id"))
        this.item.assist_id = this.value;
      if (paramString3.equals("category_id"))
        this.item.category_id = this.value;
      if (paramString3.equals("i"))
        this.list.add(this.item);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        this.item.video_type = 0;
    }
  }

  public ArrayList<UserRecommendItem> getUserRecommendList()
  {
    return this.list;
  }

  public void startDocument()
    throws SAXException
  {
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    this.sb.setLength(0);
    if (paramString2.equals("i"))
      this.item = new UserRecommendItem();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetUserRecommendHander
 * JD-Core Version:    0.6.2
 */