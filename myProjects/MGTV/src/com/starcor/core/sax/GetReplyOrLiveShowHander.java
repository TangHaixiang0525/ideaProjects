package com.starcor.core.sax;

import android.text.TextUtils;
import com.starcor.core.domain.ReserveListItem;
import com.starcor.core.utils.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetReplyOrLiveShowHander extends DefaultHandler
{
  private int isCornerMark = 0;
  private String name;
  private ReserveListItem reserve = null;
  private ReserveListItem reserveResult = null;
  private String value = "";
  private StringBuilder valueBuffer = new StringBuilder();

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    this.valueBuffer.append(paramArrayOfChar, paramInt1, paramInt2);
    if (paramInt2 > 0)
    {
      String str = this.valueBuffer.toString();
      if ((!TextUtils.isEmpty(str)) && (paramArrayOfChar[0] != '\n'))
        this.value = str;
    }
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    Logger.i("GetReplyOrLiveShowHander", "qName=" + paramString3 + ",value=" + this.value + ",localName=" + paramString2);
    if ((paramString2.equals("state")) && (this.reserve != null))
      this.reserve.state = this.value;
    if ((paramString2.equals("reason")) && (this.reserve != null))
      this.reserve.reason = this.value;
    super.endElement(paramString1, paramString2, paramString3);
  }

  public ReserveListItem getCollectList()
  {
    return this.reserve;
  }

  public void startDocument()
    throws SAXException
  {
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    this.valueBuffer.setLength(0);
    if (paramString2.equals("result"))
      this.reserve = new ReserveListItem();
    this.name = paramString3;
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetReplyOrLiveShowHander
 * JD-Core Version:    0.6.2
 */