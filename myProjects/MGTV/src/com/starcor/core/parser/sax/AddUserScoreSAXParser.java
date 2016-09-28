package com.starcor.core.parser.sax;

import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.sax.AddUserScoreHander;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class AddUserScoreSAXParser<Result>
  implements IXmlParser<Result>
{
  public Result parser(InputStream paramInputStream)
  {
    String str = "0";
    if (paramInputStream == null)
      str = "-1";
    while (true)
    {
      return str;
      InputSource localInputSource = new InputSource(paramInputStream);
      SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
      AddUserScoreHander localAddUserScoreHander = new AddUserScoreHander();
      try
      {
        XMLReader localXMLReader = localSAXParserFactory.newSAXParser().getXMLReader();
        localXMLReader.setContentHandler(localAddUserScoreHander);
        localXMLReader.setErrorHandler(localAddUserScoreHander);
        localXMLReader.parse(localInputSource);
      }
      catch (Exception localException)
      {
      }
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.sax.AddUserScoreSAXParser
 * JD-Core Version:    0.6.2
 */