package com.starcor.core.sax;

import com.starcor.core.domain.PlayInfo;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetPlayInfoHander extends DefaultHandler
{
  private PlayInfo playInfo;

  public PlayInfo getPlayInfo()
  {
    return this.playInfo;
  }

  public void startDocument()
    throws SAXException
  {
    this.playInfo = new PlayInfo();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("media"))
      this.playInfo.playUrl = paramAttributes.getValue("url");
    try
    {
      this.playInfo.state = Integer.valueOf(paramAttributes.getValue("state")).intValue();
      this.playInfo.reason = paramAttributes.getValue("reason");
      this.playInfo.begin_time = paramAttributes.getValue("begin_time");
      this.playInfo.time_len = paramAttributes.getValue("time_len");
      this.playInfo.fileId = paramAttributes.getValue("fileId");
    }
    catch (NumberFormatException localNumberFormatException2)
    {
      try
      {
        this.playInfo.dimensions = Integer.valueOf(paramAttributes.getValue("dimensions")).intValue();
        this.playInfo.quality = paramAttributes.getValue("quality");
      }
      catch (Exception localNumberFormatException2)
      {
        try
        {
          this.playInfo.IsOtherCdn = Integer.valueOf(paramAttributes.getValue("IsOtherCdn")).intValue();
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          try
          {
            while (true)
            {
              this.playInfo.dimensions = Integer.valueOf(paramAttributes.getValue("dimensions")).intValue();
              return;
              localNumberFormatException1 = localNumberFormatException1;
              this.playInfo.state = -1;
              continue;
              localException = localException;
              localException.printStackTrace();
              this.playInfo.dimensions = 0;
              continue;
              localNumberFormatException2 = localNumberFormatException2;
              this.playInfo.IsOtherCdn = -1;
            }
          }
          catch (NumberFormatException localNumberFormatException3)
          {
            this.playInfo.dimensions = -1;
          }
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetPlayInfoHander
 * JD-Core Version:    0.6.2
 */