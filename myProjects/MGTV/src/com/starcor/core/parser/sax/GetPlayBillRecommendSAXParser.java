package com.starcor.core.parser.sax;

import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.sax.GetPlayBillRecommendHandler;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class GetPlayBillRecommendSAXParser<Result>
  implements IXmlParser<Result>
{
  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    InputSource localInputSource = new InputSource(paramInputStream);
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    GetPlayBillRecommendHandler localGetPlayBillRecommendHandler = new GetPlayBillRecommendHandler();
    try
    {
      XMLReader localXMLReader = localSAXParserFactory.newSAXParser().getXMLReader();
      localXMLReader.setContentHandler(localGetPlayBillRecommendHandler);
      localXMLReader.setErrorHandler(localGetPlayBillRecommendHandler);
      localXMLReader.parse(localInputSource);
      return localGetPlayBillRecommendHandler.getPlayBillRecommendList();
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    return parser(new ByteArrayInputStream(paramArrayOfByte));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.sax.GetPlayBillRecommendSAXParser
 * JD-Core Version:    0.6.2
 */