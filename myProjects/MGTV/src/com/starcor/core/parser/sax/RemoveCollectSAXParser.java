package com.starcor.core.parser.sax;

import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;

public class RemoveCollectSAXParser<Result>
  implements IXmlParser<Result>
{
  public Result parser(InputStream paramInputStream)
  {
    Integer.valueOf(0);
    if (paramInputStream == null)
      return Integer.valueOf(-1);
    return Integer.valueOf(1);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.sax.RemoveCollectSAXParser
 * JD-Core Version:    0.6.2
 */