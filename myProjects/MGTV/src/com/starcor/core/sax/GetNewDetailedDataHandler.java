package com.starcor.core.sax;

import android.text.TextUtils;
import com.starcor.core.domain.NewDetailedFilmData;
import com.starcor.core.utils.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetNewDetailedDataHandler extends DefaultHandler
{
  private static final String TAG = GetNewDetailedDataHandler.class.getSimpleName();
  NewDetailedFilmData mNewDetailedFilmData = null;
  private String mTmpLocalName = "";
  private String mValue = "";

  public GetNewDetailedDataHandler()
  {
    Logger.i(TAG, "初始化");
    this.mNewDetailedFilmData = new NewDetailedFilmData();
  }

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    String str = new String(paramArrayOfChar, paramInt1, paramInt2);
    if (!TextUtils.isEmpty(this.mTmpLocalName))
    {
      if (!TextUtils.isEmpty(this.mValue))
        this.mValue += str;
    }
    else
      return;
    this.mValue = str;
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    if ("type".equals(this.mTmpLocalName))
      this.mNewDetailedFilmData.setType(this.mValue);
    while (true)
    {
      this.mValue = "";
      return;
      if ("id".equals(this.mTmpLocalName))
        this.mNewDetailedFilmData.setVideoId(this.mValue);
      else if ("score".equals(this.mTmpLocalName))
        try
        {
          this.mNewDetailedFilmData.setScore(Math.round(10.0F * Float.valueOf(this.mValue).floatValue()) / 10.0F);
        }
        catch (Exception localException)
        {
          this.mNewDetailedFilmData.setScore(0.0F);
        }
      else if ("good".equals(this.mTmpLocalName))
        this.mNewDetailedFilmData.setGood(this.mValue);
      else if ("bad".equals(this.mTmpLocalName))
        this.mNewDetailedFilmData.setBad(this.mValue);
      else if ("total_click".equals(this.mTmpLocalName))
        this.mNewDetailedFilmData.setTotalClicks(this.mValue);
      else if ("month_click".equals(this.mTmpLocalName))
        this.mNewDetailedFilmData.setMonthClicks(this.mValue);
      else if ("week_click".equals(this.mTmpLocalName))
        this.mNewDetailedFilmData.setWeekClicks(this.mValue);
      else if ("day_click".equals(this.mTmpLocalName))
        this.mNewDetailedFilmData.setDayClicks(this.mValue);
    }
  }

  public NewDetailedFilmData getmNewDetailedFilmData()
  {
    if (this.mNewDetailedFilmData != null)
      Logger.i(TAG, "getmNewDetailedFilmData " + this.mNewDetailedFilmData.toString());
    return this.mNewDetailedFilmData;
  }

  public void startDocument()
    throws SAXException
  {
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    this.mTmpLocalName = paramString2;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetNewDetailedDataHandler
 * JD-Core Version:    0.6.2
 */