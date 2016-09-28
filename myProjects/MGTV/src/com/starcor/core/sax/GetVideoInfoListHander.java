package com.starcor.core.sax;

import android.text.TextUtils;
import com.starcor.core.domain.VideoInfoListItem;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetVideoInfoListHander extends DefaultHandler
{
  private ArrayList<VideoInfoListItem> list = new ArrayList();
  private String name;
  private VideoInfoListItem result;
  private String value = "";
  private StringBuilder valueBuffer = new StringBuilder();

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    if (paramInt2 > 0)
    {
      String str = new String(paramArrayOfChar, 0, paramInt2);
      if ((!TextUtils.isEmpty(str)) && (paramArrayOfChar[0] != '\n'))
        this.valueBuffer.append(str);
    }
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    this.value = this.valueBuffer.toString();
    if (paramString3.equals("state"))
      this.result.state = this.value;
    if (paramString3.equals("import_id"))
      this.result.import_id = this.value;
    if (paramString3.equals("series_id"))
      this.result.serial_id = this.value;
    if (paramString3.equals("estimateId"))
      this.result.estimate_id = this.value;
    if (paramString3.equals("artithmeticId"))
      this.result.artithmetic_id = this.value;
    if (paramString3.equals("reason"))
      this.result.reason = this.value;
    if (paramString3.equals("type"))
      this.result.type = this.value;
    if (paramString3.equals("id"))
      this.result.id = this.value;
    if (paramString3.equals("name"))
      this.result.name = this.value;
    if (paramString3.equals("alias_name"))
      this.result.alias_name = this.value;
    if (paramString3.equals("img_h"))
      this.result.img_h = this.value;
    if (paramString3.equals("img_s"))
      this.result.img_s = this.value;
    if (paramString3.equals("img_v"))
      this.result.img_v = this.value;
    if (paramString3.equals("info"))
      this.result.info = this.value;
    if (paramString3.equals("kind"))
      this.result.kind = this.value;
    if (paramString3.equals("mark"))
      this.result.corner_mark = this.value;
    if (paramString3.equals("corner_mark_img"))
      this.result.corner_mark_img = this.value;
    if (paramString3.equals("abstract"))
      this.result.float_info = this.value;
    if (paramString3.equals("type"))
      this.list.add(this.result);
    if (this.valueBuffer.length() > 0)
      this.valueBuffer.delete(0, this.valueBuffer.length());
    super.endElement(paramString1, paramString2, paramString3);
  }

  public ArrayList<VideoInfoListItem> getList()
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
    if (paramString3.equals("type"))
      this.result = new VideoInfoListItem();
    this.name = paramString3;
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetVideoInfoListHander
 * JD-Core Version:    0.6.2
 */