package com.starcor.core.parser.sax;

import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.sax.GetFrequentlyAskedQuestionsHander;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class GetFrequentlyAskedQuestionsSAXParser<Result>
  implements IXmlParser<Result>
{
  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    InputSource localInputSource = new InputSource(paramInputStream);
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    GetFrequentlyAskedQuestionsHander localGetFrequentlyAskedQuestionsHander = new GetFrequentlyAskedQuestionsHander();
    try
    {
      XMLReader localXMLReader = localSAXParserFactory.newSAXParser().getXMLReader();
      localXMLReader.setContentHandler(localGetFrequentlyAskedQuestionsHander);
      localXMLReader.setErrorHandler(localGetFrequentlyAskedQuestionsHander);
      localXMLReader.parse(localInputSource);
      return localGetFrequentlyAskedQuestionsHander.getmFaqStructs();
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
 * Qualified Name:     com.starcor.core.parser.sax.GetFrequentlyAskedQuestionsSAXParser
 * JD-Core Version:    0.6.2
 */