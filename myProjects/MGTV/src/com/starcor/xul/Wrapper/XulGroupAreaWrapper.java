package com.starcor.xul.Wrapper;

import com.starcor.xul.Render.XulGroupRender;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulView;
import java.util.ArrayList;

public class XulGroupAreaWrapper extends XulViewWrapper
{
  XulArea _area;

  XulGroupAreaWrapper(XulArea paramXulArea)
  {
    super(paramXulArea);
    this._area = paramXulArea;
  }

  public static XulGroupAreaWrapper fromXulView(XulView paramXulView)
  {
    if (paramXulView == null);
    while (!(paramXulView.getRender() instanceof XulGroupRender))
      return null;
    return new XulGroupAreaWrapper((XulArea)paramXulView);
  }

  public ArrayList<ArrayList<XulView>> getAllCheckedGroups()
  {
    XulGroupRender localXulGroupRender = (XulGroupRender)this._area.getRender();
    if (localXulGroupRender == null)
      return null;
    return localXulGroupRender.getAllCheckedGroups();
  }

  public ArrayList<XulView> getAllCheckedItems()
  {
    XulGroupRender localXulGroupRender = (XulGroupRender)this._area.getRender();
    if (localXulGroupRender == null)
      return null;
    return localXulGroupRender.getAllCheckedItems();
  }

  public ArrayList<XulView> getAllGroupItems()
  {
    XulGroupRender localXulGroupRender = (XulGroupRender)this._area.getRender();
    if (localXulGroupRender == null)
      return null;
    return localXulGroupRender.getAllGroupItems();
  }

  public ArrayList<ArrayList<XulView>> getAllGroups()
  {
    XulGroupRender localXulGroupRender = (XulGroupRender)this._area.getRender();
    if (localXulGroupRender == null)
      return null;
    return localXulGroupRender.getAllGroups();
  }

  public ArrayList<XulView> getGroupByItem(XulView paramXulView)
  {
    XulGroupRender localXulGroupRender = (XulGroupRender)this._area.getRender();
    if (localXulGroupRender == null)
      return null;
    return localXulGroupRender.getGroupByItem(paramXulView);
  }

  public boolean isChecked(XulView paramXulView)
  {
    XulGroupRender localXulGroupRender = (XulGroupRender)this._area.getRender();
    if (localXulGroupRender == null)
      return false;
    return localXulGroupRender.isChecked(paramXulView);
  }

  public void setAllChecked()
  {
    XulGroupRender localXulGroupRender = (XulGroupRender)this._area.getRender();
    if (localXulGroupRender == null)
      return;
    localXulGroupRender.setAllChecked();
  }

  public void setAllUnchecked()
  {
    XulGroupRender localXulGroupRender = (XulGroupRender)this._area.getRender();
    if (localXulGroupRender == null)
      return;
    localXulGroupRender.setAllUnchecked();
  }

  public void setChecked(XulView paramXulView)
  {
    XulGroupRender localXulGroupRender = (XulGroupRender)this._area.getRender();
    if (localXulGroupRender == null)
      return;
    localXulGroupRender.setChecked(paramXulView);
  }

  public void setUnchecked(XulView paramXulView)
  {
    XulGroupRender localXulGroupRender = (XulGroupRender)this._area.getRender();
    if (localXulGroupRender == null)
      return;
    localXulGroupRender.setUnchecked(paramXulView);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Wrapper.XulGroupAreaWrapper
 * JD-Core Version:    0.6.2
 */