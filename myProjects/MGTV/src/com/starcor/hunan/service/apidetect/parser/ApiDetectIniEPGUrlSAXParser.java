package com.starcor.hunan.service.apidetect.parser;

import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class ApiDetectIniEPGUrlSAXParser<Result>
  implements IXmlParser<Result>
{
  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    InputSource localInputSource = new InputSource(paramInputStream);
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    ApiDetectIniEPGURL_Handler localApiDetectIniEPGURL_Handler = new ApiDetectIniEPGURL_Handler();
    try
    {
      XMLReader localXMLReader = localSAXParserFactory.newSAXParser().getXMLReader();
      localXMLReader.setContentHandler(localApiDetectIniEPGURL_Handler);
      localXMLReader.setErrorHandler(localApiDetectIniEPGURL_Handler);
      localXMLReader.parse(localInputSource);
      return localApiDetectIniEPGURL_Handler.getEpgMetadataGroup();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.apidetect.parser.ApiDetectIniEPGUrlSAXParser
 * JD-Core Version:    0.6.2
 */