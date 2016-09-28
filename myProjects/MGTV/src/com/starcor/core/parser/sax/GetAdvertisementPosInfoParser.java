package com.starcor.core.parser.sax;

import com.starcor.core.interfaces.IXmlParser;
import java.io.ByteArrayInputStream;

public class GetAdvertisementPosInfoParser<Result>
  implements IXmlParser<Result>
{
  // ERROR //
  public Result parser(java.io.InputStream paramInputStream)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +5 -> 6
    //   4: aconst_null
    //   5: areturn
    //   6: new 17	org/xml/sax/InputSource
    //   9: dup
    //   10: aload_1
    //   11: invokespecial 20	org/xml/sax/InputSource:<init>	(Ljava/io/InputStream;)V
    //   14: astore_2
    //   15: invokestatic 26	javax/xml/parsers/SAXParserFactory:newInstance	()Ljavax/xml/parsers/SAXParserFactory;
    //   18: astore_3
    //   19: new 28	com/starcor/core/sax/GetAdvertisementPosInfoHandler
    //   22: dup
    //   23: invokespecial 29	com/starcor/core/sax/GetAdvertisementPosInfoHandler:<init>	()V
    //   26: astore 4
    //   28: aload_3
    //   29: invokevirtual 33	javax/xml/parsers/SAXParserFactory:newSAXParser	()Ljavax/xml/parsers/SAXParser;
    //   32: invokevirtual 39	javax/xml/parsers/SAXParser:getXMLReader	()Lorg/xml/sax/XMLReader;
    //   35: astore 6
    //   37: aload 6
    //   39: aload 4
    //   41: invokeinterface 45 2 0
    //   46: aload 6
    //   48: aload 4
    //   50: invokeinterface 49 2 0
    //   55: aload 6
    //   57: aload_2
    //   58: invokeinterface 53 2 0
    //   63: aload 4
    //   65: invokevirtual 57	com/starcor/core/sax/GetAdvertisementPosInfoHandler:getAdPosInfo	()Ljava/util/ArrayList;
    //   68: astore 8
    //   70: aload 8
    //   72: areturn
    //   73: astore 5
    //   75: aconst_null
    //   76: areturn
    //   77: astore 7
    //   79: aconst_null
    //   80: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   28	63	73	java/lang/Exception
    //   63	70	77	java/lang/Exception
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    return parser(new ByteArrayInputStream(paramArrayOfByte));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.sax.GetAdvertisementPosInfoParser
 * JD-Core Version:    0.6.2
 */