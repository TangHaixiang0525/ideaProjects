package com.starcor.xul;

import com.starcor.xul.Prop.XulBinding;
import com.starcor.xul.Utils.XulBindingSelector;
import com.starcor.xul.Utils.XulBindingSelector.IXulDataSelectContext;
import java.io.InputStream;
import java.util.ArrayList;

public class XulQueryableData
{
  ArrayList<XulDataNode> _dataSet = new ArrayList();

  public XulQueryableData(XulDataNode paramXulDataNode)
  {
    this._dataSet.add(paramXulDataNode);
  }

  public XulQueryableData(InputStream paramInputStream)
  {
    try
    {
      XulDataNode localXulDataNode = XulDataNode.build(paramInputStream);
      if (localXulDataNode != null)
        this._dataSet.add(localXulDataNode);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public XulQueryableData(ArrayList<XulDataNode> paramArrayList)
  {
    for (int i = 0; i < paramArrayList.size(); i++)
    {
      XulDataNode localXulDataNode = (XulDataNode)paramArrayList.get(i);
      this._dataSet.add(localXulDataNode);
    }
  }

  ArrayList<XulDataNode> query(String paramString)
  {
    return XulBindingSelector.selectData(new XulBindingSelector.IXulDataSelectContext()
    {
      public XulBinding getBindingById(String paramAnonymousString)
      {
        return null;
      }

      public XulBinding getDefaultBinding()
      {
        return null;
      }

      public boolean isEmpty()
      {
        return false;
      }
    }
    , paramString, this._dataSet);
  }

  public String queryString(String paramString)
  {
    ArrayList localArrayList = query(paramString);
    if ((localArrayList == null) || (localArrayList.isEmpty()))
      return null;
    return ((XulDataNode)localArrayList.get(0)).getValue();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.XulQueryableData
 * JD-Core Version:    0.6.2
 */