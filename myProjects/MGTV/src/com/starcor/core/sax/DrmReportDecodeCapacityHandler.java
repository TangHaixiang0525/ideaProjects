package com.starcor.core.sax;

import android.text.TextUtils;
import com.starcor.core.domain.DrmReportDecodeCapacityInfo;
import java.io.PrintStream;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class DrmReportDecodeCapacityHandler extends DefaultHandler
{
  String curTagName;
  DrmReportDecodeCapacityInfo drmReportDecodeCapacityInfo;
  private String tmpStr;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    String str = new String(paramArrayOfChar, paramInt1, paramInt2);
    if (!TextUtils.isEmpty(this.curTagName))
    {
      if (this.tmpStr != null)
        this.tmpStr += str;
    }
    else
      return;
    this.tmpStr = str;
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
    if (paramString2.equalsIgnoreCase("state"));
    while (true)
    {
      try
      {
        this.drmReportDecodeCapacityInfo.state = Integer.parseInt(this.tmpStr);
        this.curTagName = null;
        this.tmpStr = null;
        return;
      }
      catch (Exception localException)
      {
        System.err.println(localException);
        continue;
      }
      if (paramString2.equalsIgnoreCase("reason"))
        this.drmReportDecodeCapacityInfo.reason = this.tmpStr;
    }
  }

  public DrmReportDecodeCapacityInfo getDrmReportDecodeCapacityInfo()
  {
    return this.drmReportDecodeCapacityInfo;
  }

  public void startDocument()
    throws SAXException
  {
    super.startDocument();
    this.drmReportDecodeCapacityInfo = new DrmReportDecodeCapacityInfo();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
    this.curTagName = paramString2;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.DrmReportDecodeCapacityHandler
 * JD-Core Version:    0.6.2
 */