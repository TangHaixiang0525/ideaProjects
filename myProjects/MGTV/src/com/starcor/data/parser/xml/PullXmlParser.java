package com.starcor.data.parser.xml;

import android.text.TextUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class PullXmlParser
  implements IXmlParser
{
  protected static final String TAG = PullXmlParser.class.getSimpleName();
  public static final String XML_PATH_SUFFIX = "/";
  private XmlNode buildNode;
  private boolean firstTag;
  private XmlPullParser mParser;

  private void init()
  {
    this.firstTag = true;
  }

  private void startTag()
  {
    this.buildNode = new XmlNode();
    if (this.firstTag)
    {
      this.buildNode.isRoot = true;
      this.firstTag = false;
    }
    this.buildNode.name = this.mParser.getName();
    for (int i = 0; ; i++)
    {
      if (i >= this.mParser.getAttributeCount())
        return;
      String str1 = this.mParser.getAttributeName(i);
      String str2 = this.mParser.getAttributeValue(i);
      this.buildNode.addAttr(str1, str2);
    }
  }

  private void startText()
  {
    String str1 = this.mParser.getText();
    if (TextUtils.isEmpty(str1));
    String str2;
    do
    {
      return;
      if (str1.contains("\n|\r|\t"))
        str1 = str1.replace(str1, "");
      str2 = str1.trim();
    }
    while (TextUtils.isEmpty(str2));
    this.buildNode.text = str2;
  }

  public List<XmlNode> parse(InputStream paramInputStream)
  {
    ArrayList localArrayList = new ArrayList();
    TagStack localTagStack = new TagStack();
    try
    {
      this.mParser = XmlPullParserFactory.newInstance().newPullParser();
      this.mParser.setInput(paramInputStream, "UTF-8");
      int i = this.mParser.getEventType();
      init();
      if (i == 1)
      {
        if (!localTagStack.isEmpty())
          throw new IllegalAccessError("xml tag not end completely!");
      }
      else
        switch (i)
        {
        default:
        case 2:
          while (true)
          {
            i = this.mParser.next();
            break;
            localTagStack.put(this.mParser.getName());
            startTag();
          }
        case 4:
        case 5:
        case 3:
        }
    }
    catch (XmlPullParserException localXmlPullParserException)
    {
      while (true)
      {
        localXmlPullParserException.printStackTrace();
        continue;
        startText();
      }
    }
    catch (IOException localIOException)
    {
      while (true)
      {
        localIOException.printStackTrace();
        continue;
        String str = this.mParser.getName();
        if (!localTagStack.pop().equals(str))
          throw new IllegalAccessError("xml tag end error!");
        if (!TextUtils.isEmpty(this.buildNode.text))
        {
          this.buildNode.path = localTagStack.getPath();
          localArrayList.add(this.buildNode);
        }
      }
    }
    return localArrayList;
  }

  private class TagStack
  {
    List<String> stack = new ArrayList();

    public TagStack()
    {
    }

    public String getPath()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      Iterator localIterator = this.stack.iterator();
      while (true)
      {
        if (!localIterator.hasNext())
          return localStringBuilder.toString();
        String str = (String)localIterator.next();
        localStringBuilder.append("/").append(str);
      }
    }

    public boolean isEmpty()
    {
      return this.stack.isEmpty();
    }

    public String pop()
    {
      return (String)this.stack.remove(-1 + this.stack.size());
    }

    public void put(String paramString)
    {
      this.stack.add(paramString);
    }

    public String top()
    {
      return (String)this.stack.get(-1 + this.stack.size());
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.data.parser.xml.PullXmlParser
 * JD-Core Version:    0.6.2
 */