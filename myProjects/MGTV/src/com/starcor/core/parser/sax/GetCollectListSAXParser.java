package com.starcor.core.parser.sax;

import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.sax.GetCollectHander;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class GetCollectListSAXParser<Result>
  implements IXmlParser<Result>
{
  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    InputSource localInputSource = new InputSource(paramInputStream);
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    GetCollectHander localGetCollectHander = new GetCollectHander();
    try
    {
      XMLReader localXMLReader = localSAXParserFactory.newSAXParser().getXMLReader();
      localXMLReader.setContentHandler(localGetCollectHander);
      localXMLReader.setErrorHandler(localGetCollectHander);
      localXMLReader.parse(localInputSource);
      return localGetCollectHander.getCollectList();
    }
    catch (Exception localException)
    {
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.sax.GetCollectListSAXParser
 * JD-Core Version:    0.6.2
 */