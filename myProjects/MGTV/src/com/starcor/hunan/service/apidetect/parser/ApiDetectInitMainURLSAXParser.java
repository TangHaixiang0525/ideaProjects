package com.starcor.hunan.service.apidetect.parser;

import android.text.TextUtils;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.hunan.service.apidetect.data.ApiDetectAppInfo;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class ApiDetectInitMainURLSAXParser<Result>
  implements IXmlParser<Result>
{
  public Result parser(InputStream paramInputStream)
  {
    Integer localInteger1 = Integer.valueOf(-1);
    if (paramInputStream == null)
      return localInteger1;
    InputSource localInputSource = new InputSource(paramInputStream);
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    ApiDetectInitMainURL_Handler localApiDetectInitMainURL_Handler = new ApiDetectInitMainURL_Handler();
    Object localObject;
    try
    {
      XMLReader localXMLReader = localSAXParserFactory.newSAXParser().getXMLReader();
      localXMLReader.setContentHandler(localApiDetectInitMainURL_Handler);
      localXMLReader.setErrorHandler(localApiDetectInitMainURL_Handler);
      localXMLReader.parse(localInputSource);
      if (TextUtils.isEmpty(ApiDetectAppInfo.URL_n3_a))
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
 * Qualified Name:     com.starcor.hunan.service.apidetect.parser.ApiDetectInitMainURLSAXParser
 * JD-Core Version:    0.6.2
 */