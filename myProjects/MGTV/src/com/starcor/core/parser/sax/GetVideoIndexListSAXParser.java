package com.starcor.core.parser.sax;

import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.sax.GetFilmItemCountNumHander;
import com.starcor.core.utils.Logger;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class GetVideoIndexListSAXParser<Result>
  implements IXmlParser<Result>
{
  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
    {
      Logger.i("mIndex: input null");
      return null;
    }
    InputSource localInputSource = new InputSource(paramInputStream);
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    GetFilmItemCountNumHander localGetFilmItemCountNumHander = new GetFilmItemCountNumHander();
    try
    {
      XMLReader localXMLReader = localSAXParserFactory.newSAXParser().getXMLReader();
      localXMLReader.setContentHandler(localGetFilmItemCountNumHander);
      localXMLReader.setErrorHandler(localGetFilmItemCountNumHander);
      localXMLReader.parse(localInputSource);
      return localGetFilmItemCountNumHander.getFilmListPageInfo();
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
 * Qualified Name:     com.starcor.core.parser.sax.GetVideoIndexListSAXParser
 * JD-Core Version:    0.6.2
 */