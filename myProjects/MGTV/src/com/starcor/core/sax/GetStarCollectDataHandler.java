package com.starcor.core.sax;

import com.starcor.core.domain.StarInfo;
import com.starcor.core.domain.StarInfoCollect;
import com.starcor.core.utils.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetStarCollectDataHandler extends DefaultHandler
{
  private static final String TAG = GetStarCollectDataHandler.class.getSimpleName();
  private StarInfo mStarInfo = null;
  private StarInfoCollect mStarInfoCollect = null;
  private String mTmpLocalName = "";
  private StringBuilder sb = new StringBuilder();

  public GetStarCollectDataHandler()
  {
    Logger.i(TAG, "初始化");
    this.mStarInfoCollect = new StarInfoCollect();
  }

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
    String str = this.sb.toString();
    if ("type".equals(this.mTmpLocalName))
      this.mStarInfo.setType(str);
    do
    {
      return;
      if ("id".equals(this.mTmpLocalName))
      {
        this.mStarInfo.setId(str);
        return;
      }
      if ("name".equals(this.mTmpLocalName))
      {
        this.mStarInfo.setName(str);
        return;
      }
      if ("alias_name".equals(this.mTmpLocalName))
      {
        this.mStarInfo.setAliasName(str);
        return;
      }
      if ("img_h".equals(this.mTmpLocalName))
      {
        this.mStarInfo.setImgH(str);
        return;
      }
      if ("img_v".equals(this.mTmpLocalName))
      {
        this.mStarInfo.setImgV(str);
        return;
      }
      if ("img_s".equals(this.mTmpLocalName))
      {
        this.mStarInfo.setImgS(str);
        return;
      }
      if ("info".equals(this.mTmpLocalName))
      {
        this.mStarInfo.setInfo(str);
        return;
      }
      if ("label_id".equals(this.mTmpLocalName))
      {
        this.mStarInfo.setLabelId(str);
        return;
      }
      if ("sex".equals(this.mTmpLocalName))
      {
        this.mStarInfo.setSex(str);
        return;
      }
      if ("old_name".equals(this.mTmpLocalName))
      {
        this.mStarInfo.setOldName(str);
        return;
      }
      if ("en_name".equals(this.mTmpLocalName))
      {
        this.mStarInfo.setEnName(str);
        return;
      }
      if ("state".equals(this.mTmpLocalName))
      {
        this.mStarInfoCollect.setState(str);
        return;
      }
      if ("reason".equals(this.mTmpLocalName))
      {
        this.mStarInfoCollect.setReason(str);
        return;
      }
      if ("star_type".equals(this.mTmpLocalName))
      {
        this.mStarInfo.setStarType(str);
        return;
      }
    }
    while (!"label_id_v2".equals(this.mTmpLocalName));
    this.mStarInfo.setLabelIdV2(str);
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    this.mTmpLocalName = "";
    if ((paramString2.equalsIgnoreCase("i")) && (this.mStarInfo != null))
    {
      this.mStarInfoCollect.addStarInfo(this.mStarInfo);
      this.mStarInfo = null;
    }
  }

  public StarInfoCollect getStarCollectData()
  {
    if ((this.mStarInfoCollect != null) && (this.mStarInfoCollect.getStarInfo() != null));
    return this.mStarInfoCollect;
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
    this.mTmpLocalName = paramString2;
    if (paramString2.equalsIgnoreCase("i"))
      this.mStarInfo = new StarInfo();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetStarCollectDataHandler
 * JD-Core Version:    0.6.2
 */