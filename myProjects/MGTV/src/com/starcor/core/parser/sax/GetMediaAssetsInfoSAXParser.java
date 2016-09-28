package com.starcor.core.parser.sax;

import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.sax.GetCategoryItemHander;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class GetMediaAssetsInfoSAXParser<Result>
  implements IXmlParser<Result>
{
  private String package_id;

  public GetMediaAssetsInfoSAXParser(String paramString)
  {
    this.package_id = paramString;
  }

  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    InputSource localInputSource = new InputSource(paramInputStream);
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    GetCategoryItemHander localGetCategoryItemHander = new GetCategoryItemHander(this.package_id);
    try
    {
      XMLReader localXMLReader = localSAXParserFactory.newSAXParser().getXMLReader();
      localXMLReader.setContentHandler(localGetCategoryItemHander);
      localXMLReader.setErrorHandler(localGetCategoryItemHander);
      localXMLReader.parse(localInputSource);
      return localGetCategoryItemHander.getcInfo();
    }
    catch (Exception localException)
    {
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.sax.GetMediaAssetsInfoSAXParser
 * JD-Core Version:    0.6.2
 */