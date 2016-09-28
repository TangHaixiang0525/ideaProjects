package com.starcor.xul.Wrapper;

import com.starcor.xul.Render.XulLayerRender;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulView;

public class XulLayerAreaWrapper extends XulViewWrapper
{
  XulLayerAreaWrapper(XulArea paramXulArea)
  {
    super(paramXulArea);
  }

  public static XulLayerAreaWrapper fromXulView(XulView paramXulView)
  {
    if (paramXulView == null);
    while (!(paramXulView.getRender() instanceof XulLayerRender))
      return null;
    return new XulLayerAreaWrapper((XulArea)paramXulView);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Wrapper.XulLayerAreaWrapper
 * JD-Core Version:    0.6.2
 */