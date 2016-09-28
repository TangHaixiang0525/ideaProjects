package com.starcor.core.parser.sax;

import com.starcor.core.domain.UserInfo;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.sax.CheckUserInfoHander;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class CheckUserInfoSAXParser<Result>
  implements IXmlParser<Result>
{
  private String device_id;
  private String mac_id;

  public CheckUserInfoSAXParser(String paramString1, String paramString2)
  {
    this.device_id = paramString1;
    this.mac_id = paramString2;
  }

  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    InputSource localInputSource = new InputSource(paramInputStream);
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    CheckUserInfoHander localCheckUserInfoHander = new CheckUserInfoHander(this.device_id, this.mac_id);
    try
    {
      XMLReader localXMLReader = localSAXParserFactory.newSAXParser().getXMLReader();
      localXMLReader.setContentHandler(localCheckUserInfoHander);
      localXMLReader.setErrorHandler(localCheckUserInfoHander);
      localXMLReader.parse(localInputSource);
      Logger.d("userinfo=" + localCheckUserInfoHander.getInfo().toString());
      return localCheckUserInfoHander.getInfo();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Logger.d("userinfo=" + localCheckUserInfoHander.getInfo().toString());
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.sax.CheckUserInfoSAXParser
 * JD-Core Version:    0.6.2
 */