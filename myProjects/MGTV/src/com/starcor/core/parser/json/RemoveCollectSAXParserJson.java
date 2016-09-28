package com.starcor.core.parser.json;

import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;

public class RemoveCollectSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "RemoveCollectSAXParserJson";

  public Result parser(InputStream paramInputStream)
  {
    Integer.valueOf(0);
    if (paramInputStream == null);
    for (Integer localInteger = Integer.valueOf(-1); ; localInteger = Integer.valueOf(1))
    {
      Logger.i("RemoveCollectSAXParserJson", "RemoveCollectSAXParserJson解析器解析得到的对象：i=" + localInteger);
      return localInteger;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.RemoveCollectSAXParserJson
 * JD-Core Version:    0.6.2
 */