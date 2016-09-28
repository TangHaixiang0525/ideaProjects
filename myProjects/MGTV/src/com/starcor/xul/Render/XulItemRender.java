package com.starcor.xul.Render;

import com.starcor.xul.XulItem;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulView;

public class XulItemRender extends XulViewRender
{
  public XulItemRender(XulRenderContext paramXulRenderContext, XulView paramXulView)
  {
    super(paramXulRenderContext, paramXulView);
  }

  public static void register()
  {
    XulRenderFactory.registerBuilder("item", "*", new XulRenderFactory.RenderBuilder()
    {
      static
      {
        if (!XulItemRender.class.desiredAssertionStatus());
        for (boolean bool = true; ; bool = false)
        {
          $assertionsDisabled = bool;
          return;
        }
      }

      protected XulViewRender createRender(XulRenderContext paramAnonymousXulRenderContext, XulView paramAnonymousXulView)
      {
        assert ((paramAnonymousXulView instanceof XulItem));
        return new XulItemRender(paramAnonymousXulRenderContext, paramAnonymousXulView);
      }
    });
  }

  public int getDefaultFocusMode()
  {
    return 8;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.XulItemRender
 * JD-Core Version:    0.6.2
 */