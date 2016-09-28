package com.starcor.core.parser.sax;

import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.sax.GetFilmInfoHander;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class GetFilmInfoSAXParser<Result>
  implements IXmlParser<Result>
{
  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    InputSource localInputSource = new InputSource(paramInputStream);
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    GetFilmInfoHander localGetFilmInfoHander = new GetFilmInfoHander();
    try
    {
      XMLReader localXMLReader = localSAXParserFactory.newSAXParser().getXMLReader();
      localXMLReader.setContentHandler(localGetFilmInfoHander);
      localXMLReader.setErrorHandler(localGetFilmInfoHander);
      localXMLReader.parse(localInputSource);
      return localGetFilmInfoHander.getVideoInfo();
    }
    catch (Exception localException)
    {
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.sax.GetFilmInfoSAXParser
 * JD-Core Version:    0.6.2
 */