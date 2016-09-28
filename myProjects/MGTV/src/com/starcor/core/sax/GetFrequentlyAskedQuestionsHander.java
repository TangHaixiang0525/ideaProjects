package com.starcor.core.sax;

import com.starcor.core.domain.FAQItem;
import com.starcor.core.domain.FAQStruct;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetFrequentlyAskedQuestionsHander extends DefaultHandler
{
  private FAQItem mFaqItem;
  private List<FAQItem> mFaqItems;
  FAQStruct mFaqStruct;
  private List<FAQStruct> mFaqStructs;
  private String value;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if (this.value != null)
    {
      this.value += new String(paramArrayOfChar, paramInt1, paramInt2);
      return;
    }
    this.value = new String(paramArrayOfChar, paramInt1, paramInt2);
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("section"))
      if (this.mFaqStructs != null);
    do
    {
      do
      {
        return;
        this.mFaqStructs.add(this.mFaqStruct);
        return;
        if (!paramString2.equalsIgnoreCase("item"))
          break;
      }
      while ((this.mFaqStruct == null) || (this.mFaqStruct.mItems == null));
      this.mFaqStruct.mItems.add(this.mFaqItem);
      return;
      if (paramString2.equalsIgnoreCase("question"))
      {
        this.mFaqItem.question = this.value;
        return;
      }
    }
    while (!paramString2.equalsIgnoreCase("answer"));
    this.mFaqItem.answer = this.value;
  }

  public List<FAQStruct> getmFaqStructs()
  {
    return this.mFaqStructs;
  }

  public void startDocument()
    throws SAXException
  {
    this.mFaqStructs = new ArrayList();
    this.mFaqItems = new ArrayList();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("section"))
      this.mFaqStruct = new FAQStruct();
    do
    {
      try
      {
        this.mFaqStruct.type = Integer.valueOf(paramAttributes.getValue("type")).intValue();
        this.mFaqStruct.name = paramAttributes.getValue("name");
        this.mFaqStruct.mItems = new ArrayList();
        return;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        while (true)
          this.mFaqStruct.type = -1;
      }
      if (paramString2.equalsIgnoreCase("item"))
      {
        this.mFaqItem = new FAQItem();
        return;
      }
      if (paramString2.equalsIgnoreCase("question"))
      {
        this.value = "";
        return;
      }
    }
    while (!paramString2.equalsIgnoreCase("answer"));
    this.value = "";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetFrequentlyAskedQuestionsHander
 * JD-Core Version:    0.6.2
 */