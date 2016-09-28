package com.starcor.core.parser.sax;

import com.starcor.core.domain.UiLayoutItem;
import com.starcor.core.domain.UiPackage;
import com.starcor.core.interfaces.IXmlParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class ScUiPackageParser<Result>
  implements IXmlParser<Result>
{
  public Result parser(InputStream paramInputStream)
  {
    Object localObject;
    if (paramInputStream == null)
    {
      localObject = null;
      return localObject;
    }
    while (true)
    {
      XmlPullParser localXmlPullParser;
      UiLayoutItem localUiLayoutItem;
      try
      {
        XmlPullParserFactory localXmlPullParserFactory = XmlPullParserFactory.newInstance();
        localXmlPullParserFactory.setNamespaceAware(true);
        localXmlPullParser = localXmlPullParserFactory.newPullParser();
        localXmlPullParser.setInput(paramInputStream, "UTF-8");
        localObject = new UiPackage();
        localUiLayoutItem = null;
        int i = localXmlPullParser.nextToken();
        if (i == 1)
          break;
        switch (i)
        {
        case 3:
        case 4:
        case 9:
        case 2:
          if ("ui_package".equals(localXmlPullParser.getName()))
          {
            ((UiPackage)localObject).ui_version = localXmlPullParser.getAttributeValue("", "ui_version");
            ((UiPackage)localObject).modify_time = localXmlPullParser.getAttributeValue("", "modify_time");
            continue;
          }
          break;
        case 5:
        case 6:
        case 7:
        case 8:
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      if ("ui_layout_list".equals(localXmlPullParser.getName()))
      {
        ((UiPackage)localObject).ui_layout_list = new ArrayList();
      }
      else if ("ui_layout".equals(localXmlPullParser.getName()))
      {
        localUiLayoutItem = new UiLayoutItem();
        ((UiPackage)localObject).ui_layout_list.add(localUiLayoutItem);
        localUiLayoutItem.id = localXmlPullParser.getAttributeValue("", "id");
        localUiLayoutItem.name = localXmlPullParser.getAttributeValue("", "name");
        localUiLayoutItem.modify_time = localXmlPullParser.getAttributeValue("", "modify_time");
        localUiLayoutItem.flag = localXmlPullParser.getAttributeValue("", "flag");
        localUiLayoutItem.type = localXmlPullParser.getAttributeValue("", "type");
        localUiLayoutItem.ex_url = localXmlPullParser.getAttributeValue("", "ex_url");
        localUiLayoutItem.format = localXmlPullParser.getAttributeValue("", "format");
        localUiLayoutItem.md5 = localXmlPullParser.getAttributeValue("", "md5");
        localUiLayoutItem.local_url = localXmlPullParser.getAttributeValue("", "local_url");
        continue;
        localUiLayoutItem.CDATA = localXmlPullParser.getText();
      }
    }
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    return parser(new ByteArrayInputStream(paramArrayOfByte));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.sax.ScUiPackageParser
 * JD-Core Version:    0.6.2
 */