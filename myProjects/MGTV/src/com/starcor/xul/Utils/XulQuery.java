package com.starcor.xul.Utils;

import android.text.TextUtils;
import com.starcor.xul.XulDataNode;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class XulQuery
{
  private static ConcurrentHashMap<String, selectAction> _selectActionCache = new ConcurrentHashMap();

  private static selectAction[] buildSelectActions(String[] paramArrayOfString)
  {
    selectAction[] arrayOfselectAction = new selectAction[paramArrayOfString.length];
    int i = 0;
    int j = paramArrayOfString.length;
    if (i < j)
    {
      String str1 = paramArrayOfString[i];
      if (str1.startsWith(":"))
        if (":parent".equals(str1))
          arrayOfselectAction[i] = saParent.instance;
      while (true)
      {
        i++;
        break;
        if (":prev".equals(str1))
        {
          arrayOfselectAction[i] = saPrev.instance;
        }
        else if (":next".equals(str1))
        {
          arrayOfselectAction[i] = saNext.instance;
        }
        else
        {
          arrayOfselectAction[i] = saNull.instance;
          continue;
          selectAction localselectAction = (selectAction)_selectActionCache.get(str1);
          if (localselectAction == null)
            break label126;
          arrayOfselectAction[i] = localselectAction;
        }
      }
      label126: int k;
      if ((str1.startsWith("[")) && (str1.endsWith("]")))
      {
        k = str1.indexOf('=');
        if (k < 0)
          arrayOfselectAction[i] = new saHasAttr(str1.substring(1, -1 + str1.length()));
      }
      while (true)
      {
        _selectActionCache.put(str1, arrayOfselectAction[i]);
        break;
        String str2 = str1.substring(1, k);
        String str3 = str1.substring(k + 1, -1 + str1.length());
        if (TextUtils.isEmpty(str2))
        {
          arrayOfselectAction[i] = new saGetAttr(str3);
        }
        else
        {
          arrayOfselectAction[i] = new saHasAttrVal(str2, str3);
          continue;
          arrayOfselectAction[i] = new saFindTag(str1);
        }
      }
    }
    return arrayOfselectAction;
  }

  public static XulQueryContext compile(String[] paramArrayOfString)
  {
    if (paramArrayOfString == null);
    while (paramArrayOfString.length <= 0)
      return null;
    return new XulQueryContext(paramArrayOfString);
  }

  private static int execSelect(int paramInt1, int paramInt2, selectAction[] paramArrayOfselectAction, XulDataNode[] paramArrayOfXulDataNode)
  {
    int i = paramInt1;
    while (true)
    {
      selectAction localselectAction;
      XulDataNode localXulDataNode;
      if (i < paramInt2)
      {
        localselectAction = paramArrayOfselectAction[i];
        if (paramArrayOfXulDataNode[(i + 1)] != null)
          break label63;
        int k = i + 1;
        localXulDataNode = localselectAction.selectFirst(paramArrayOfXulDataNode[i]);
        paramArrayOfXulDataNode[k] = localXulDataNode;
      }
      while (true)
      {
        if (localXulDataNode != null)
          break label91;
        i--;
        if (i > 0)
          break;
        return i;
        label63: int j = i + 1;
        localXulDataNode = localselectAction.selectNext(paramArrayOfXulDataNode[(i + 1)]);
        paramArrayOfXulDataNode[j] = localXulDataNode;
      }
      label91: i++;
    }
  }

  public static XulDataNode select(XulDataNode paramXulDataNode, String[] paramArrayOfString)
  {
    if ((paramArrayOfString == null) || (paramXulDataNode == null));
    int i;
    do
    {
      return null;
      i = paramArrayOfString.length;
    }
    while (i <= 0);
    selectAction[] arrayOfselectAction = buildSelectActions(paramArrayOfString);
    XulDataNode[] arrayOfXulDataNode = new XulDataNode[i + 1];
    arrayOfXulDataNode[0] = paramXulDataNode;
    execSelect(0, i, arrayOfselectAction, arrayOfXulDataNode);
    return arrayOfXulDataNode[i];
  }

  public static String selectValue(XulDataNode paramXulDataNode, String[] paramArrayOfString)
  {
    XulDataNode localXulDataNode = select(paramXulDataNode, paramArrayOfString);
    if (localXulDataNode == null)
      return null;
    return localXulDataNode.getValue();
  }

  public static class XulQueryContext
  {
    int _curLevel;
    XulQuery.selectAction[] _selectActions;
    XulDataNode[] _selectStack;

    XulQueryContext(String[] paramArrayOfString)
    {
      this._selectActions = XulQuery.buildSelectActions(paramArrayOfString);
      this._selectStack = new XulDataNode[1 + paramArrayOfString.length];
    }

    public XulDataNode select(XulDataNode paramXulDataNode)
    {
      if (paramXulDataNode == null)
        return null;
      Arrays.fill(this._selectStack, null);
      this._selectStack[0] = paramXulDataNode;
      this._curLevel = 0;
      int i = this._selectActions.length;
      this._curLevel = XulQuery.execSelect(this._curLevel, i, this._selectActions, this._selectStack);
      return this._selectStack[i];
    }

    public XulDataNode selectNext()
    {
      this._curLevel = (-1 + this._curLevel);
      if (this._curLevel <= 0)
        return null;
      int i = this._selectActions.length;
      this._curLevel = XulQuery.execSelect(this._curLevel, i, this._selectActions, this._selectStack);
      return this._selectStack[i];
    }

    public String selectNextValue()
    {
      XulDataNode localXulDataNode = selectNext();
      if (localXulDataNode == null)
        return null;
      return localXulDataNode.getValue();
    }

    public String selectValue(XulDataNode paramXulDataNode)
    {
      XulDataNode localXulDataNode = select(paramXulDataNode);
      if (localXulDataNode == null)
        return null;
      return localXulDataNode.getValue();
    }
  }

  private static class saFindTag extends XulQuery.selectAction
  {
    String _tagName;

    public saFindTag(String paramString)
    {
      super();
      this._tagName = paramString;
    }

    XulDataNode selectFirst(XulDataNode paramXulDataNode)
    {
      return paramXulDataNode.getChildNode(this._tagName);
    }

    XulDataNode selectNext(XulDataNode paramXulDataNode)
    {
      return paramXulDataNode.getNext(this._tagName);
    }
  }

  private static class saGetAttr extends XulQuery.selectAction
  {
    String _key;

    public saGetAttr(String paramString)
    {
      super();
      this._key = paramString;
    }

    XulDataNode selectFirst(XulDataNode paramXulDataNode)
    {
      return paramXulDataNode.getAttribute(this._key);
    }

    XulDataNode selectNext(XulDataNode paramXulDataNode)
    {
      return null;
    }
  }

  private static class saHasAttr extends XulQuery.selectAction
  {
    String _key;

    public saHasAttr(String paramString)
    {
      super();
      this._key = paramString;
    }

    XulDataNode selectFirst(XulDataNode paramXulDataNode)
    {
      if (paramXulDataNode.getAttribute(this._key) != null)
        return paramXulDataNode;
      return null;
    }

    XulDataNode selectNext(XulDataNode paramXulDataNode)
    {
      return null;
    }
  }

  private static class saHasAttrVal extends XulQuery.selectAction
  {
    String _key;
    String _val;

    public saHasAttrVal(String paramString1, String paramString2)
    {
      super();
      this._key = paramString1;
      this._val = paramString2;
    }

    XulDataNode selectFirst(XulDataNode paramXulDataNode)
    {
      if (this._val.equals(paramXulDataNode.getAttributeValue(this._key)))
        return paramXulDataNode;
      return null;
    }

    XulDataNode selectNext(XulDataNode paramXulDataNode)
    {
      return null;
    }
  }

  private static class saNext extends XulQuery.selectAction
  {
    static saNext instance = new saNext();

    private saNext()
    {
      super();
    }

    XulDataNode selectFirst(XulDataNode paramXulDataNode)
    {
      return paramXulDataNode.getNext();
    }

    XulDataNode selectNext(XulDataNode paramXulDataNode)
    {
      return paramXulDataNode.getNext();
    }
  }

  private static class saNull extends XulQuery.selectAction
  {
    static saNull instance = new saNull();

    private saNull()
    {
      super();
    }

    XulDataNode selectFirst(XulDataNode paramXulDataNode)
    {
      return null;
    }

    XulDataNode selectNext(XulDataNode paramXulDataNode)
    {
      return null;
    }
  }

  private static class saParent extends XulQuery.selectAction
  {
    static saParent instance = new saParent();

    private saParent()
    {
      super();
    }

    XulDataNode selectFirst(XulDataNode paramXulDataNode)
    {
      return paramXulDataNode.getParent();
    }

    XulDataNode selectNext(XulDataNode paramXulDataNode)
    {
      return paramXulDataNode.getParent();
    }
  }

  private static class saPrev extends XulQuery.selectAction
  {
    static saPrev instance = new saPrev();

    private saPrev()
    {
      super();
    }

    XulDataNode selectFirst(XulDataNode paramXulDataNode)
    {
      return paramXulDataNode.getPrev();
    }

    XulDataNode selectNext(XulDataNode paramXulDataNode)
    {
      return paramXulDataNode.getPrev();
    }
  }

  private static abstract class selectAction
  {
    abstract XulDataNode selectFirst(XulDataNode paramXulDataNode);

    abstract XulDataNode selectNext(XulDataNode paramXulDataNode);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Utils.XulQuery
 * JD-Core Version:    0.6.2
 */