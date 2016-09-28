package com.starcor.xul.Wrapper;

import com.starcor.xul.Render.XulLabelRender;
import com.starcor.xul.XulView;

public class XulLabelItemWrapper extends XulViewWrapper
{
  XulLabelItemWrapper(XulView paramXulView)
  {
    super(paramXulView);
  }

  public static XulLabelItemWrapper fromXulView(XulView paramXulView)
  {
    if (paramXulView == null);
    while (!(paramXulView.getRender() instanceof XulLabelRender))
      return null;
    return new XulLabelItemWrapper(paramXulView);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Wrapper.XulLabelItemWrapper
 * JD-Core Version:    0.6.2
 */