package com.starcor.core.sax;

import com.starcor.core.domain.UserAuth;
import com.starcor.core.utils.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetUserAuthHander extends DefaultHandler
{
  private UserAuth userAuth;

  public UserAuth getVideoInfo()
  {
    return this.userAuth;
  }

  public void startDocument()
    throws SAXException
  {
    super.startDocument();
    this.userAuth = new UserAuth();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("authorize"));
    try
    {
      this.userAuth.setState(Integer.parseInt(paramAttributes.getValue("state")));
      this.userAuth.setReason(paramAttributes.getValue("reason"));
      this.userAuth.setOrder_url(paramAttributes.getValue("order_url"));
      return;
    }
    catch (Exception localException)
    {
      while (true)
      {
        Logger.e("获取权限失败");
        this.userAuth.setState(0);
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetUserAuthHander
 * JD-Core Version:    0.6.2
 */