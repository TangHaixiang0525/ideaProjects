package com.starcor.xul.Wrapper;

import com.starcor.xul.Render.XulViewRender;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulView;

public class XulViewWrapper
{
  XulView _view;

  XulViewWrapper(XulView paramXulView)
  {
    this._view = paramXulView;
  }

  public static XulViewWrapper fromXulView(XulView paramXulView)
  {
    return new XulViewWrapper(paramXulView);
  }

  public boolean blinkClass(String[] paramArrayOfString)
  {
    XulViewRender localXulViewRender = this._view.getRender();
    if (localXulViewRender == null)
      return false;
    return localXulViewRender.blinkClass(paramArrayOfString);
  }

  public XulArea getAsArea()
  {
    if ((this._view instanceof XulArea))
      return (XulArea)this._view;
    return null;
  }

  public XulView getAsView()
  {
    return this._view;
  }

  public void requestFocus()
  {
    XulLayout localXulLayout = this._view.getRootLayout();
    if (localXulLayout == null)
      return;
    localXulLayout.requestFocus(this._view);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Wrapper.XulViewWrapper
 * JD-Core Version:    0.6.2
 */