package com.starcor.core.parser.sax;

import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;

public class TotalVisitCountSAXParser<Result>
  implements IXmlParser<Result>
{
  public Result parser(InputStream paramInputStream)
  {
    if (paramInputStream == null);
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.sax.TotalVisitCountSAXParser
 * JD-Core Version:    0.6.2
 */