package com.starcor.core.sax;

import android.text.TextUtils;
import com.starcor.core.domain.AuthState;
import com.starcor.core.domain.UserAuthV2;
import com.starcor.core.domain.UserKey;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetUserAuthV2Handler extends DefaultHandler
{
  AuthState state;
  UserAuthV2 userAuth;
  UserKey userKey;
  List<UserKey> userKeyList = new ArrayList();
  private String value;
  private StringBuilder valueBuffer = new StringBuilder();

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if (paramInt2 > 0)
    {
      String str = new String(paramArrayOfChar, 0, paramInt2);
      if ((!TextUtils.isEmpty(str)) && (paramArrayOfChar[0] != '\n'))
      {
        this.value = str;
        this.valueBuffer.append(str);
      }
    }
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    this.value = this.valueBuffer.toString();
    if (paramString2.equals("k"))
    {
      this.userKey = new UserKey();
      this.userKey.key = this.value;
    }
    if (paramString2.equals("v"))
    {
      this.userKey.value = this.value;
      this.userKeyList.add(this.userKey);
    }
    if (paramString2.equals("arg"))
      this.userAuth.list.addAll(this.userKeyList);
    if (this.valueBuffer.length() > 0)
      this.valueBuffer.delete(0, this.valueBuffer.length());
    super.endElement(paramString1, paramString2, paramString3);
  }

  public UserAuthV2 getUserAuthInfo()
  {
    return this.userAuth;
  }

  public void startDocument()
    throws SAXException
  {
    this.userAuth = new UserAuthV2();
    this.userAuth.list = new ArrayList();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equals("result"))
      this.state = new AuthState();
    try
    {
      this.state.state = Integer.parseInt(paramAttributes.getValue("state"));
      this.state.reason = paramAttributes.getValue("reason");
      this.state.order_url = paramAttributes.getValue("order_url");
    }
    catch (Exception localException1)
    {
      try
      {
        if (!TextUtils.isEmpty(paramAttributes.getValue("is_url_jump")));
        for (this.state.is_url_jump = Integer.parseInt(paramAttributes.getValue("is_url_jump")); ; this.state.is_url_jump = 0)
        {
          this.userAuth.state = this.state;
          super.startElement(paramString1, paramString2, paramString3, paramAttributes);
          return;
          localException1 = localException1;
          this.state.state = 0;
          break;
        }
      }
      catch (Exception localException2)
      {
        while (true)
          this.state.is_url_jump = 0;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetUserAuthV2Handler
 * JD-Core Version:    0.6.2
 */