package com.starcor.core.parser.sax;

import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.sax.GetUserAuthV2Handler;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class GetUserAuthV2SAXParser<Result>
  implements IXmlParser<Result>
{
  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    InputSource localInputSource = new InputSource(paramInputStream);
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    GetUserAuthV2Handler localGetUserAuthV2Handler = new GetUserAuthV2Handler();
    try
    {
      XMLReader localXMLReader = localSAXParserFactory.newSAXParser().getXMLReader();
      localXMLReader.setContentHandler(localGetUserAuthV2Handler);
      localXMLReader.setErrorHandler(localGetUserAuthV2Handler);
      localXMLReader.parse(localInputSource);
      return localGetUserAuthV2Handler.getUserAuthInfo();
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
 * Qualified Name:     com.starcor.core.parser.sax.GetUserAuthV2SAXParser
 * JD-Core Version:    0.6.2
 */