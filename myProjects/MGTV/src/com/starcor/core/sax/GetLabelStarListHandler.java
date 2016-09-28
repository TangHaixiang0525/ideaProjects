package com.starcor.core.sax;

import android.util.Pair;
import com.starcor.core.domain.LabelStarList;
import com.starcor.core.domain.LabelStarList.LabelStarItemStructure;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetLabelStarListHandler extends DefaultHandler
{
  private LabelStarList.LabelStarItemStructure i;
  private ArrayList<LabelStarList.LabelStarItemStructure> l;
  private LabelStarList labelStarList;
  private StringBuilder sb = new StringBuilder();
  private ArrayList<Pair<ArrayList<LabelStarList.LabelStarItemStructure>, LabelStarList.LabelStarItemStructure>> stack = new ArrayList();
  private String tagName;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
    String str = this.sb.toString();
    if ("state".equals(this.tagName))
      this.labelStarList.state = str;
    do
    {
      return;
      if ("reason".equals(this.tagName))
      {
        this.labelStarList.reason = str;
        return;
      }
      if ("type".equals(this.tagName))
      {
        this.i.type = str;
        return;
      }
      if ("id".equals(this.tagName))
      {
        this.i.id = str;
        return;
      }
    }
    while (!"name".equals(this.tagName));
    this.i.name = str;
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
    if ((!paramString2.equalsIgnoreCase("i")) || ((paramString2.equalsIgnoreCase("l")) && (this.stack.size() > 0)))
    {
      Pair localPair = (Pair)this.stack.remove(-1 + this.stack.size());
      this.l = ((ArrayList)localPair.first);
      this.i = ((LabelStarList.LabelStarItemStructure)localPair.second);
    }
  }

  public LabelStarList getLabelStarList()
  {
    this.labelStarList.l = this.l;
    return this.labelStarList;
  }

  public void startDocument()
    throws SAXException
  {
    super.startDocument();
    this.labelStarList = new LabelStarList();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
    this.sb.setLength(0);
    this.tagName = paramString2;
    if (paramString2.equalsIgnoreCase("l"))
    {
      if (this.l != null)
        this.stack.add(Pair.create(this.l, this.i));
      this.l = new ArrayList();
      if (this.i != null)
        this.i.l = this.l;
    }
    if (paramString2.equalsIgnoreCase("i"))
    {
      LabelStarList localLabelStarList = new LabelStarList();
      localLabelStarList.getClass();
      this.i = new LabelStarList.LabelStarItemStructure(localLabelStarList);
      if (this.l != null)
        this.l.add(this.i);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetLabelStarListHandler
 * JD-Core Version:    0.6.2
 */