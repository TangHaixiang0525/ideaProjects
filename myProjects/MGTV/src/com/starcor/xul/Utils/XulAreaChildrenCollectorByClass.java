package com.starcor.xul.Utils;

import com.starcor.xul.XulArea;
import com.starcor.xul.XulArea.XulAreaIterator;
import com.starcor.xul.XulItem;
import com.starcor.xul.XulView;
import java.util.ArrayList;

public class XulAreaChildrenCollectorByClass extends XulArea.XulAreaIterator
{
  ArrayList<String> _classSet = new ArrayList();
  boolean _isAny = true;
  XulArrayList<XulView> _result = new XulArrayList();

  private void doCollect(XulView paramXulView)
  {
    if (this._isAny)
      if (testAny(paramXulView))
        this._result.add(paramXulView);
    while (!testAll(paramXulView))
      return;
    this._result.add(paramXulView);
  }

  private boolean testAll(XulView paramXulView)
  {
    for (int i = 0; i < this._classSet.size(); i++)
      if (!paramXulView.hasClass((String)this._classSet.get(i)))
        return false;
    return true;
  }

  private boolean testAny(XulView paramXulView)
  {
    for (int i = 0; i < this._classSet.size(); i++)
      if (paramXulView.hasClass((String)this._classSet.get(i)))
        return true;
    return false;
  }

  public void begin(String paramString)
  {
    begin(true, new String[] { paramString });
  }

  public void begin(boolean paramBoolean, String[] paramArrayOfString)
  {
    this._classSet.clear();
    this._isAny = paramBoolean;
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      String str = paramArrayOfString[j];
      this._classSet.add(str);
    }
    this._result.clear();
  }

  public void begin(String[] paramArrayOfString)
  {
    begin(true, paramArrayOfString);
  }

  public void clear()
  {
    this._result.clear();
  }

  public XulArrayList<XulView> end()
  {
    return this._result;
  }

  public boolean onXulArea(int paramInt, XulArea paramXulArea)
  {
    doCollect(paramXulArea);
    paramXulArea.eachChild(this);
    return true;
  }

  public boolean onXulItem(int paramInt, XulItem paramXulItem)
  {
    doCollect(paramXulItem);
    return true;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Utils.XulAreaChildrenCollectorByClass
 * JD-Core Version:    0.6.2
 */