package com.starcor.core.parser.sax;

import com.starcor.core.domain.GetPlaybillSelectedListInfo;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.sax.GetPlaybillSelectedListHandler;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class GetPlaybillSelectedListSAXParser
  implements IXmlParser<GetPlaybillSelectedListInfo>
{
  public GetPlaybillSelectedListInfo parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    InputSource localInputSource = new InputSource(paramInputStream);
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    GetPlaybillSelectedListHandler localGetPlaybillSelectedListHandler = new GetPlaybillSelectedListHandler();
    try
    {
      XMLReader localXMLReader = localSAXParserFactory.newSAXParser().getXMLReader();
      localXMLReader.setContentHandler(localGetPlaybillSelectedListHandler);
      localXMLReader.setErrorHandler(localGetPlaybillSelectedListHandler);
      localXMLReader.parse(localInputSource);
      return localGetPlaybillSelectedListHandler.getGetPlaybillSelectedListInfo();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.sax.GetPlaybillSelectedListSAXParser
 * JD-Core Version:    0.6.2
 */