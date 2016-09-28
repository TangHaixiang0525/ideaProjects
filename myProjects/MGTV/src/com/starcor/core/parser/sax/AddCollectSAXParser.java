package com.starcor.core.parser.sax;

import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.sax.AddCollectHander;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class AddCollectSAXParser<Result>
  implements IXmlParser<Result>
{
  public Result parser(InputStream paramInputStream)
  {
    Object localObject;
    if (paramInputStream == null)
      localObject = "-1";
    while (true)
    {
      return localObject;
      InputSource localInputSource = new InputSource(paramInputStream);
      SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
      AddCollectHander localAddCollectHander = new AddCollectHander();
      try
      {
        XMLReader localXMLReader = localSAXParserFactory.newSAXParser().getXMLReader();
        localXMLReader.setContentHandler(localAddCollectHander);
        localXMLReader.setErrorHandler(localAddCollectHander);
        localXMLReader.parse(localInputSource);
        String str = localAddCollectHander.getCollectId();
        localObject = str;
      }
      catch (Exception localException)
      {
      }
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.sax.AddCollectSAXParser
 * JD-Core Version:    0.6.2
 */