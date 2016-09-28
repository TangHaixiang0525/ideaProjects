package com.starcor.core.sax;

import com.starcor.core.domain.AdPosByPageIdInfo;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetAdPosByPageIdHandler extends DefaultHandler
{
  private ArrayList<AdPosByPageIdInfo> adPosList;

  public ArrayList<AdPosByPageIdInfo> getAdPosByPageIdInfo()
  {
    return this.adPosList;
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
    if ("ad_pos_list".equalsIgnoreCase(paramString2))
      this.adPosList = new ArrayList();
    AdPosByPageIdInfo localAdPosByPageIdInfo;
    if ("ad_pos".equalsIgnoreCase(paramString2))
    {
      localAdPosByPageIdInfo = new AdPosByPageIdInfo();
      localAdPosByPageIdInfo.setId(paramAttributes.getValue("id"));
      localAdPosByPageIdInfo.setName(paramAttributes.getValue("name"));
      localAdPosByPageIdInfo.setEvent(paramAttributes.getValue("event"));
      localAdPosByPageIdInfo.setSharpType(paramAttributes.getValue("sharp_type"));
    }
    try
    {
      localAdPosByPageIdInfo.setxPercent(Float.valueOf(paramAttributes.getValue("x_percent")).floatValue());
    }
    catch (NumberFormatException localNumberFormatException7)
    {
      try
      {
        localAdPosByPageIdInfo.setyPercent(Float.valueOf(paramAttributes.getValue("y_percent")).floatValue());
      }
      catch (NumberFormatException localNumberFormatException7)
      {
        try
        {
          localAdPosByPageIdInfo.setwPercent(Float.valueOf(paramAttributes.getValue("w_percent")).floatValue());
        }
        catch (NumberFormatException localNumberFormatException7)
        {
          try
          {
            localAdPosByPageIdInfo.sethPercent(Float.valueOf(paramAttributes.getValue("h_percent")).floatValue());
          }
          catch (NumberFormatException localNumberFormatException7)
          {
            try
            {
              localAdPosByPageIdInfo.setxPosition(Integer.valueOf(paramAttributes.getValue("x")).intValue());
            }
            catch (NumberFormatException localNumberFormatException7)
            {
              try
              {
                localAdPosByPageIdInfo.setyPosition(Integer.valueOf(paramAttributes.getValue("y")).intValue());
              }
              catch (NumberFormatException localNumberFormatException7)
              {
                try
                {
                  localAdPosByPageIdInfo.setwPosition(Integer.valueOf(paramAttributes.getValue("w")).intValue());
                }
                catch (NumberFormatException localNumberFormatException7)
                {
                  try
                  {
                    while (true)
                    {
                      localAdPosByPageIdInfo.sethPosition(Integer.valueOf(paramAttributes.getValue("h")).intValue());
                      this.adPosList.add(localAdPosByPageIdInfo);
                      return;
                      localNumberFormatException1 = localNumberFormatException1;
                      localAdPosByPageIdInfo.setxPercent(0.0F);
                      continue;
                      localNumberFormatException2 = localNumberFormatException2;
                      localAdPosByPageIdInfo.setyPercent(0.0F);
                      continue;
                      localNumberFormatException3 = localNumberFormatException3;
                      localAdPosByPageIdInfo.setwPercent(0.0F);
                      continue;
                      localNumberFormatException4 = localNumberFormatException4;
                      localAdPosByPageIdInfo.sethPercent(0.0F);
                      continue;
                      localNumberFormatException5 = localNumberFormatException5;
                      localAdPosByPageIdInfo.setxPosition(0);
                      continue;
                      localNumberFormatException6 = localNumberFormatException6;
                      localAdPosByPageIdInfo.setyPosition(0);
                      continue;
                      localNumberFormatException7 = localNumberFormatException7;
                      localAdPosByPageIdInfo.setwPosition(0);
                    }
                  }
                  catch (NumberFormatException localNumberFormatException8)
                  {
                    while (true)
                      localAdPosByPageIdInfo.sethPosition(0);
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
 * Qualified Name:     com.starcor.core.sax.GetAdPosByPageIdHandler
 * JD-Core Version:    0.6.2
 */