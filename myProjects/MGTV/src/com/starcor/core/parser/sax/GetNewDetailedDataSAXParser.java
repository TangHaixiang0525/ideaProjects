package com.starcor.core.parser.sax;

import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.sax.GetNewDetailedDataHandler;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class GetNewDetailedDataSAXParser<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = GetNewDetailedDataSAXParser.class.getSimpleName();

  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    InputSource localInputSource = new InputSource(paramInputStream);
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    GetNewDetailedDataHandler localGetNewDetailedDataHandler = new GetNewDetailedDataHandler();
    try
    {
      XMLReader localXMLReader = localSAXParserFactory.newSAXParser().getXMLReader();
      localXMLReader.setContentHandler(localGetNewDetailedDataHandler);
      localXMLReader.setErrorHandler(localGetNewDetailedDataHandler);
      localXMLReader.parse(localInputSource);
      return localGetNewDetailedDataHandler.getmNewDetailedFilmData();
    }
    catch (Exception localException)
    {
      Logger.i(TAG, "exception==>" + localException.getLocalizedMessage());
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.sax.GetNewDetailedDataSAXParser
 * JD-Core Version:    0.6.2
 */