package com.starcor.core.sax;

import com.starcor.core.domain.UiUpdatePackage;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CheckUiPackageUpdatHandler extends DefaultHandler
{
  private UiUpdatePackage updatePkg;

  public UiUpdatePackage getUiUpdatePackage()
  {
    return this.updatePkg;
  }

  public void startDocument()
    throws SAXException
  {
    this.updatePkg = new UiUpdatePackage();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if ("ui_package".equalsIgnoreCase(paramString2))
    {
      this.updatePkg.ui_version = paramAttributes.getValue("ui_version");
      this.updatePkg.modify_time = paramAttributes.getValue("modify_time");
    }
    try
    {
      this.updatePkg.update = Integer.valueOf(paramAttributes.getValue("update")).intValue();
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      try
      {
        while (true)
        {
          this.updatePkg.force_update = Integer.valueOf(paramAttributes.getValue("force_update")).intValue();
          return;
          localNumberFormatException1 = localNumberFormatException1;
          localNumberFormatException1.printStackTrace();
          this.updatePkg.update = 0;
        }
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        localNumberFormatException2.printStackTrace();
        this.updatePkg.force_update = 0;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.CheckUiPackageUpdatHandler
 * JD-Core Version:    0.6.2
 */