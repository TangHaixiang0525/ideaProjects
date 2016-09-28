package com.starcor.core.sax;

import com.starcor.core.domain.ActorStarInfoList;
import com.starcor.core.domain.ActorStarInfoList.ActorStarInfo;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetActorStarInfoHandler extends DefaultHandler
{
  private ActorStarInfoList actorStarInfoList;
  private ActorStarInfoList.ActorStarInfo infos;
  private StringBuilder sb = new StringBuilder();
  private String tagName;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
    String str = this.sb.toString();
    if ("state".equals(this.tagName))
      this.actorStarInfoList.state = str;
    do
    {
      return;
      if ("reason".equals(this.tagName))
      {
        this.actorStarInfoList.reason = str;
        return;
      }
      if ("type".equals(this.tagName))
      {
        this.infos.type = str;
        return;
      }
      if ("id".equals(this.tagName))
      {
        this.infos.id = str;
        return;
      }
      if ("name".equals(this.tagName))
      {
        this.infos.name = str;
        return;
      }
      if ("alias_name".equals(this.tagName))
      {
        this.infos.alias_name = str;
        return;
      }
      if ("img_h".equals(this.tagName))
      {
        this.infos.img_h = str;
        return;
      }
      if ("img_s".equals(this.tagName))
      {
        this.infos.img_s = str;
        return;
      }
      if ("img_v".equals(this.tagName))
      {
        this.infos.img_v = str;
        return;
      }
      if ("info".equals(this.tagName))
      {
        this.infos.info = str;
        return;
      }
      if ("label_id".equals(this.tagName))
      {
        this.infos.label_id = str;
        return;
      }
      if ("sex".equals(this.tagName))
      {
        this.infos.sex = str;
        return;
      }
      if ("old_name".equals(this.tagName))
      {
        this.infos.old_name = str;
        return;
      }
      if ("en_name".equals(this.tagName))
      {
        this.infos.en_name = str;
        return;
      }
      if ("college".equals(this.tagName))
      {
        this.infos.college = str;
        return;
      }
      if ("nation".equals(this.tagName))
      {
        this.infos.nation = str;
        return;
      }
      if ("area".equals(this.tagName))
      {
        this.infos.area = str;
        return;
      }
      if ("birthday".equals(this.tagName))
      {
        this.infos.birthday = str;
        return;
      }
      if ("constellation".equals(this.tagName))
      {
        this.infos.constellation = str;
        return;
      }
      if ("height".equals(this.tagName))
      {
        this.infos.height = str;
        return;
      }
      if ("weight".equals(this.tagName))
      {
        this.infos.weight = str;
        return;
      }
      if ("blood_type".equals(this.tagName))
      {
        this.infos.blood_type = str;
        return;
      }
      if ("story".equals(this.tagName))
      {
        this.infos.story = str;
        return;
      }
      if ("profession".equals(this.tagName))
      {
        this.infos.profession = str;
        return;
      }
    }
    while (!"works".equals(this.tagName));
    this.infos.works = str;
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
    if (paramString2.equalsIgnoreCase("i"))
      this.actorStarInfoList.lists.add(this.infos);
  }

  public ActorStarInfoList getActorStarInfoList()
  {
    return this.actorStarInfoList;
  }

  public void startDocument()
    throws SAXException
  {
    this.actorStarInfoList = new ActorStarInfoList();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
    this.sb.setLength(0);
    this.tagName = paramString2;
    if (paramString2.equalsIgnoreCase("i"))
    {
      ActorStarInfoList localActorStarInfoList = this.actorStarInfoList;
      localActorStarInfoList.getClass();
      this.infos = new ActorStarInfoList.ActorStarInfo(localActorStarInfoList);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetActorStarInfoHandler
 * JD-Core Version:    0.6.2
 */