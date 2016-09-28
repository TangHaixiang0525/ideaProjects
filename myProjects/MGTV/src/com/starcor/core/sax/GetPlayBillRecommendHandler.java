package com.starcor.core.sax;

import com.starcor.core.domain.PlayBillRecommendItem;
import com.starcor.core.domain.PlayBillRecommendStrut;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetPlayBillRecommendHandler extends DefaultHandler
{
  private PlayBillRecommendItem pBill;
  private PlayBillRecommendStrut pBillInfo;
  private ArrayList<PlayBillRecommendStrut> playBillList;
  private String tempChannelName;
  private String tempVideoId;
  private int tsDefaultPos = 0;
  private int tsLimitMax = 0;
  private int tsLimitMin = 0;

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("day"))
      this.playBillList.add(this.pBillInfo);
    super.endElement(paramString1, paramString2, paramString3);
  }

  public ArrayList<PlayBillRecommendStrut> getPlayBillRecommendList()
  {
    return this.playBillList;
  }

  public void startDocument()
    throws SAXException
  {
    if (this.playBillList == null)
      this.playBillList = new ArrayList();
    while (true)
    {
      super.startDocument();
      return;
      this.playBillList.clear();
    }
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("day"))
    {
      this.pBillInfo = new PlayBillRecommendStrut();
      this.pBillInfo.day = paramAttributes.getValue("day");
      this.pBillInfo.arrPlayBill = new ArrayList();
    }
    while (true)
    {
      super.startElement(paramString1, paramString2, paramString3, paramAttributes);
      return;
      if (paramString2.equalsIgnoreCase("program"))
      {
        this.tempChannelName = paramAttributes.getValue("video_name");
        this.tempVideoId = paramAttributes.getValue("video_id");
        try
        {
          this.tsLimitMin = Integer.valueOf(paramAttributes.getValue("ts_limit_min")).intValue();
        }
        catch (NumberFormatException localNumberFormatException4)
        {
          try
          {
            this.tsLimitMax = Integer.valueOf(paramAttributes.getValue("ts_limit_max")).intValue();
          }
          catch (NumberFormatException localNumberFormatException4)
          {
            try
            {
              while (true)
              {
                this.tsDefaultPos = Integer.valueOf(paramAttributes.getValue("ts_default_pos")).intValue();
                this.pBillInfo.tsLimitMin = this.tsLimitMin;
                this.pBillInfo.tsLimitMax = this.tsLimitMax;
                this.pBillInfo.tsDefaultPos = this.tsDefaultPos;
                break;
                localNumberFormatException3 = localNumberFormatException3;
                this.tsLimitMin = 0;
                continue;
                localNumberFormatException4 = localNumberFormatException4;
                this.tsLimitMax = 0;
              }
            }
            catch (NumberFormatException localNumberFormatException5)
            {
              while (true)
                this.tsDefaultPos = 0;
            }
          }
        }
      }
      if (!paramString2.equalsIgnoreCase("item"))
        continue;
      this.pBill = new PlayBillRecommendItem();
      this.pBill.desc = paramAttributes.getValue("text");
      try
      {
        this.pBill.timeLen = Integer.valueOf(paramAttributes.getValue("time_len").trim()).intValue();
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        try
        {
          while (true)
          {
            while (true)
            {
              this.pBill.can_play = Integer.valueOf(paramAttributes.getValue("can_play").trim()).intValue();
              this.pBill.huawei_cid = paramAttributes.getValue("huawei_cid");
              this.pBill.img_big = paramAttributes.getValue("img_big");
              this.pBill.img_normal = paramAttributes.getValue("img_normal");
              this.pBill.img_small = paramAttributes.getValue("img_small");
              this.pBill.summary = paramAttributes.getValue("summary");
              this.pBill.begin = paramAttributes.getValue("begin");
              this.pBill.channel_name = this.tempChannelName;
              this.pBill.videoId = this.tempVideoId;
              try
              {
                this.pBillInfo.arrPlayBill.add(this.pBill);
              }
              catch (NullPointerException localNullPointerException)
              {
                this.pBillInfo.arrPlayBill = new ArrayList();
                this.pBillInfo.arrPlayBill.add(this.pBill);
              }
            }
            break;
            localNumberFormatException1 = localNumberFormatException1;
            this.pBill.timeLen = 0;
          }
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          while (true)
            this.pBill.can_play = 0;
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetPlayBillRecommendHandler
 * JD-Core Version:    0.6.2
 */