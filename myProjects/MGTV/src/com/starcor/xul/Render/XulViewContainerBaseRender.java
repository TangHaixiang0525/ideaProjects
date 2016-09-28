package com.starcor.xul.Render;

import com.starcor.xul.Utils.XulLayoutHelper.ILayoutContainer;
import com.starcor.xul.Utils.XulLayoutHelper.ILayoutElement;
import com.starcor.xul.Utils.XulSimpleArray;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulRenderContext;

public abstract class XulViewContainerBaseRender extends XulViewRender
{
  protected XulArea _area;

  public XulViewContainerBaseRender(XulRenderContext paramXulRenderContext, XulArea paramXulArea)
  {
    super(paramXulRenderContext, paramXulArea);
    this._area = paramXulArea;
  }

  protected XulLayoutHelper.ILayoutElement createElement()
  {
    return new LayoutContainer();
  }

  public XulLayoutHelper.ILayoutContainer getLayoutElement()
  {
    return (XulLayoutHelper.ILayoutContainer)super.getLayoutElement();
  }

  protected class LayoutContainer extends XulViewRender.LayoutElement
    implements XulLayoutHelper.ILayoutContainer
  {
    protected LayoutContainer()
    {
      super();
    }

    public int getAlignmentOffsetX()
    {
      return 0;
    }

    public int getAlignmentOffsetY()
    {
      return 0;
    }

    public float getAlignmentX()
    {
      return (0.0F / 0.0F);
    }

    public float getAlignmentY()
    {
      return (0.0F / 0.0F);
    }

    public int getAllVisibleChildren(XulSimpleArray<XulLayoutHelper.ILayoutElement> paramXulSimpleArray)
    {
      return XulViewContainerBaseRender.this._area.getAllVisibleChildLayoutElement(paramXulSimpleArray);
    }

    public XulLayoutHelper.ILayoutElement getChild(int paramInt)
    {
      return XulViewContainerBaseRender.this._area.getChildLayoutElement(paramInt);
    }

    public int getChildNum()
    {
      return XulViewContainerBaseRender.this._area.getChildNum();
    }

    public int getContentOffsetX()
    {
      return 0;
    }

    public int getContentOffsetY()
    {
      return 0;
    }

    public int getLayoutMode()
    {
      return 0;
    }

    public int getOffsetX()
    {
      return 0;
    }

    public int getOffsetY()
    {
      return 0;
    }

    public XulLayoutHelper.ILayoutElement getVisibleChild(int paramInt)
    {
      return XulViewContainerBaseRender.this._area.getVisibleChildLayoutElement(paramInt);
    }

    public int setAlignmentOffset(int paramInt1, int paramInt2)
    {
      return 0;
    }

    public int setContentSize(int paramInt1, int paramInt2)
    {
      return 0;
    }

    public boolean updateContentSize()
    {
      return false;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.XulViewContainerBaseRender
 * JD-Core Version:    0.6.2
 */