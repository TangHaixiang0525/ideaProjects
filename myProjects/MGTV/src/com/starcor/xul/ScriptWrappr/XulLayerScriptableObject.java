package com.starcor.xul.ScriptWrappr;

import com.starcor.xul.Wrapper.XulLayerAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulView;

public class XulLayerScriptableObject extends XulAreaScriptableObject
{
  XulLayerAreaWrapper _wrapper;

  public XulLayerScriptableObject(XulArea paramXulArea)
  {
    super(paramXulArea);
  }

  private boolean _initWrapper()
  {
    if (this._wrapper != null)
      return true;
    this._wrapper = XulLayerAreaWrapper.fromXulView((XulView)this._xulItem);
    if (this._wrapper != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.ScriptWrappr.XulLayerScriptableObject
 * JD-Core Version:    0.6.2
 */