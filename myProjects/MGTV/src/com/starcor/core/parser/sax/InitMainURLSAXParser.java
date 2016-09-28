package com.starcor.core.parser.sax;

import android.text.TextUtils;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.sax.InitMainURL_Hander;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class InitMainURLSAXParser<Result>
  implements IXmlParser<Result>
{
  public Result parser(InputStream paramInputStream)
  {
    Integer localInteger1 = Integer.valueOf(-1);
    if (paramInputStream == null)
      return localInteger1;
    InputSource localInputSource = new InputSource(paramInputStream);
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    InitMainURL_Hander localInitMainURL_Hander = new InitMainURL_Hander();
    Object localObject;
    try
    {
      XMLReader localXMLReader = localSAXParserFactory.newSAXParser().getXMLReader();
      localXMLReader.setContentHandler(localInitMainURL_Hander);
      localXMLReader.setErrorHandler(localInitMainURL_Hander);
      localXMLReader.parse(localInputSource);
      if (TextUtils.isEmpty(AppInfo.URL_n3_a))
      {
        localObject = Integer.valueOf(-1);
      }
      else
      {
        Integer localInteger2 = Integer.valueOf(1);
        localObject = localInteger2;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return Integer.valueOf(-1);
    }
    return localObject;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.sax.InitMainURLSAXParser
 * JD-Core Version:    0.6.2
 */