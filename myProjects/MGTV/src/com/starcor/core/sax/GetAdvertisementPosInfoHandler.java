package com.starcor.core.sax;

import com.starcor.core.domain.AdvertisementPosInfo;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetAdvertisementPosInfoHandler extends DefaultHandler
{
  private ArrayList<AdvertisementPosInfo> adPosList;

  public ArrayList<AdvertisementPosInfo> getAdPosInfo()
  {
    return this.adPosList;
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
    if ("ad_pos_list".equalsIgnoreCase(paramString2))
      this.adPosList = new ArrayList();
    AdvertisementPosInfo localAdvertisementPosInfo;
    if ("ad_pos".equalsIgnoreCase(paramString2))
    {
      localAdvertisementPosInfo = new AdvertisementPosInfo();
      localAdvertisementPosInfo.setId(paramAttributes.getValue("id"));
      localAdvertisementPosInfo.setName(paramAttributes.getValue("name"));
      localAdvertisementPosInfo.setEvent(paramAttributes.getValue("event"));
    }
    try
    {
      localAdvertisementPosInfo.setxPercent(Float.valueOf(paramAttributes.getValue("x_percent")).floatValue());
    }
    catch (NumberFormatException localNumberFormatException9)
    {
      try
      {
        localAdvertisementPosInfo.setyPercent(Float.valueOf(paramAttributes.getValue("y_percent")).floatValue());
      }
      catch (NumberFormatException localNumberFormatException9)
      {
        try
        {
          localAdvertisementPosInfo.setwPercent(Float.valueOf(paramAttributes.getValue("w_percent")).floatValue());
        }
        catch (NumberFormatException localNumberFormatException9)
        {
          try
          {
            localAdvertisementPosInfo.sethPercent(Float.valueOf(paramAttributes.getValue("h_percent")).floatValue());
          }
          catch (NumberFormatException localNumberFormatException9)
          {
            try
            {
              localAdvertisementPosInfo.setxPosition(Integer.valueOf(paramAttributes.getValue("x")).intValue());
            }
            catch (NumberFormatException localNumberFormatException9)
            {
              try
              {
                localAdvertisementPosInfo.setyPosition(Integer.valueOf(paramAttributes.getValue("y")).intValue());
              }
              catch (NumberFormatException localNumberFormatException9)
              {
                try
                {
                  localAdvertisementPosInfo.setwPosition(Integer.valueOf(paramAttributes.getValue("w")).intValue());
                }
                catch (NumberFormatException localNumberFormatException9)
                {
                  try
                  {
                    localAdvertisementPosInfo.sethPosition(Integer.valueOf(paramAttributes.getValue("h")).intValue());
                  }
                  catch (NumberFormatException localNumberFormatException9)
                  {
                    try
                    {
                      localAdvertisementPosInfo.setBeginTime(Integer.valueOf(paramAttributes.getValue("begin_time")).intValue());
                    }
                    catch (NumberFormatException localNumberFormatException9)
                    {
                      try
                      {
                        while (true)
                        {
                          localAdvertisementPosInfo.setAliveTime(Integer.valueOf(paramAttributes.getValue("alive_time")).intValue());
                          this.adPosList.add(localAdvertisementPosInfo);
                          return;
                          localNumberFormatException1 = localNumberFormatException1;
                          localAdvertisementPosInfo.setxPercent(0.0F);
                          continue;
                          localNumberFormatException2 = localNumberFormatException2;
                          localAdvertisementPosInfo.setyPercent(0.0F);
                          continue;
                          localNumberFormatException3 = localNumberFormatException3;
                          localAdvertisementPosInfo.setwPercent(0.0F);
                          continue;
                          localNumberFormatException4 = localNumberFormatException4;
                          localAdvertisementPosInfo.sethPercent(0.0F);
                          continue;
                          localNumberFormatException5 = localNumberFormatException5;
                          localAdvertisementPosInfo.setxPosition(0);
                          continue;
                          localNumberFormatException6 = localNumberFormatException6;
                          localAdvertisementPosInfo.setyPosition(0);
                          continue;
                          localNumberFormatException7 = localNumberFormatException7;
                          localAdvertisementPosInfo.setwPosition(0);
                          continue;
                          localNumberFormatException8 = localNumberFormatException8;
                          localAdvertisementPosInfo.sethPosition(0);
                          continue;
                          localNumberFormatException9 = localNumberFormatException9;
                          localAdvertisementPosInfo.setBeginTime(0);
                        }
                      }
                      catch (NumberFormatException localNumberFormatException10)
                      {
                        while (true)
                          localAdvertisementPosInfo.setAliveTime(0);
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetAdvertisementPosInfoHandler
 * JD-Core Version:    0.6.2
 */