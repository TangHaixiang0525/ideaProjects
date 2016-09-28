package com.starcor.core.sax;

import com.starcor.core.domain.Agreements;
import com.starcor.core.domain.Agreements.AgreementInfo;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetAgreementHandler extends DefaultHandler
{
  Agreements agreement;
  private String str;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    if (this.str != null)
    {
      this.str += new String(paramArrayOfChar, paramInt1, paramInt2);
      return;
    }
    this.str = new String(paramArrayOfChar, paramInt1, paramInt2);
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
    if ("state".equals(paramString2))
      this.agreement.state = this.str;
    while (true)
    {
      this.str = null;
      return;
      if ("reason".equals(paramString2))
        this.agreement.reason = this.str;
      else if (("type".equals(paramString2)) && (this.agreement.agreements.size() > 0))
        ((Agreements.AgreementInfo)this.agreement.agreements.get(-1 + this.agreement.agreements.size())).type = this.str;
      else if (("info".equals(paramString2)) && (this.agreement.agreements.size() > 0))
        ((Agreements.AgreementInfo)this.agreement.agreements.get(-1 + this.agreement.agreements.size())).info = this.str;
      else if (("name".equals(paramString2)) && (this.agreement.agreements.size() > 0))
        ((Agreements.AgreementInfo)this.agreement.agreements.get(-1 + this.agreement.agreements.size())).name = this.str;
      else if (("action".equals(paramString2)) && (this.agreement.agreements.size() > 0))
        ((Agreements.AgreementInfo)this.agreement.agreements.get(-1 + this.agreement.agreements.size())).action = this.str;
    }
  }

  public Agreements getAgreements()
  {
    return this.agreement;
  }

  public void startDocument()
    throws SAXException
  {
    this.agreement = new Agreements();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if ("i".equals(paramString2))
      this.agreement.agreements.add(new Agreements.AgreementInfo());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetAgreementHandler
 * JD-Core Version:    0.6.2
 */