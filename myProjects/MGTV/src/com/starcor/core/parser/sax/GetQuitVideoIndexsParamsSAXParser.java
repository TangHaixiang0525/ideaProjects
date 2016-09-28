package com.starcor.core.parser.sax;

import com.starcor.core.interfaces.IXmlParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class GetQuitVideoIndexsParamsSAXParser<Result>
  implements IXmlParser<Result>
{
  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    InputSource localInputSource = new InputSource(new ByteArrayInputStream(paramArrayOfByte));
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    GetPlayerQuitsParamsHandler localGetPlayerQuitsParamsHandler = new GetPlayerQuitsParamsHandler();
    try
    {
      XMLReader localXMLReader = localSAXParserFactory.newSAXParser().getXMLReader();
      localXMLReader.setContentHandler(localGetPlayerQuitsParamsHandler);
      localXMLReader.setErrorHandler(localGetPlayerQuitsParamsHandler);
      localXMLReader.parse(localInputSource);
      return localGetPlayerQuitsParamsHandler.getParams();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.sax.GetQuitVideoIndexsParamsSAXParser
 * JD-Core Version:    0.6.2
 */