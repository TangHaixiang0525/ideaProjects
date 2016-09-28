package com.starcor.core.parser.sax;

import com.starcor.core.domain.DrmReportDecodeCapacityInfo;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.sax.DrmReportDecodeCapacityHandler;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class DrmReportDecodeCapacityParser
  implements IXmlParser<DrmReportDecodeCapacityInfo>
{
  public DrmReportDecodeCapacityInfo parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    InputSource localInputSource = new InputSource(paramInputStream);
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    DrmReportDecodeCapacityHandler localDrmReportDecodeCapacityHandler = new DrmReportDecodeCapacityHandler();
    try
    {
      XMLReader localXMLReader = localSAXParserFactory.newSAXParser().getXMLReader();
      localXMLReader.setContentHandler(localDrmReportDecodeCapacityHandler);
      localXMLReader.setErrorHandler(localDrmReportDecodeCapacityHandler);
      localXMLReader.parse(localInputSource);
      return localDrmReportDecodeCapacityHandler.getDrmReportDecodeCapacityInfo();
    }
    catch (Exception localException)
    {
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.sax.DrmReportDecodeCapacityParser
 * JD-Core Version:    0.6.2
 */