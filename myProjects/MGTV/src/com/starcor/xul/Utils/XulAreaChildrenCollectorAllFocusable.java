package com.starcor.xul.Utils;

import com.starcor.xul.XulArea;
import com.starcor.xul.XulArea.XulAreaIterator;
import com.starcor.xul.XulItem;
import com.starcor.xul.XulView;

public class XulAreaChildrenCollectorAllFocusable extends XulArea.XulAreaIterator
{
  XulArrayList<XulView> _result = new XulArrayList();

  private void doCollect(XulView paramXulView)
  {
    if ((paramXulView.isEnabled()) && (paramXulView.isVisible()) && (paramXulView.focusable()))
      this._result.add(paramXulView);
  }

  public void begin()
  {
    this._result.clear();
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
 * Qualified Name:     com.starcor.xul.Utils.XulAreaChildrenCollectorAllFocusable
 * JD-Core Version:    0.6.2
 */