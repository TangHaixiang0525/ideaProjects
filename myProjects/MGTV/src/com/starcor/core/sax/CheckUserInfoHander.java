package com.starcor.core.sax;

import com.starcor.core.domain.UserInfo;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CheckUserInfoHander extends DefaultHandler
{
  private String device_id;
  private String mac_id;
  private UserInfo uInfo;

  public CheckUserInfoHander(String paramString1, String paramString2)
  {
    this.device_id = paramString1;
    this.mac_id = paramString2;
  }

  public UserInfo getInfo()
  {
    return this.uInfo;
  }

  public void startDocument()
    throws SAXException
  {
    this.uInfo = new UserInfo();
    this.uInfo.device_id = this.device_id;
    this.uInfo.mac_id = this.mac_id;
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("user"))
    {
      this.uInfo.user_id = paramAttributes.getValue("id");
      this.uInfo.state = paramAttributes.getValue("state");
      this.uInfo.name = paramAttributes.getValue("name");
      this.uInfo.net_id = paramAttributes.getValue("net_id");
      this.uInfo.valid_time = paramAttributes.getValue("valid_time");
      this.uInfo.nn_token = paramAttributes.getValue("token");
      this.uInfo.ex_data = paramAttributes.getValue("ex_data");
      this.uInfo.reason = paramAttributes.getValue("reason");
      this.uInfo.status = paramAttributes.getValue("status");
      this.uInfo.email = paramAttributes.getValue("login_id");
    }
    try
    {
      this.uInfo.expires_in = Integer.parseInt(paramAttributes.getValue("expires_in"));
      this.uInfo.web_token = paramAttributes.getValue("web_token");
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.CheckUserInfoHander
 * JD-Core Version:    0.6.2
 */