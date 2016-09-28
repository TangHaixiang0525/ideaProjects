package com.starcor.xul.Utils;

import android.graphics.Canvas;
import android.graphics.Canvas.EdgeType;
import android.graphics.Rect;
import android.graphics.RectF;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Render.XulViewRender;
import com.starcor.xul.XulArea.XulViewIterator;
import com.starcor.xul.XulView;

public class XulAreaChildrenRender extends XulArea.XulViewIterator
{
  Canvas canvas;
  XulDC dc;
  Rect rect;
  int xBase;
  int yBase;

  private void drawItem(XulView paramXulView)
  {
    if (!paramXulView.isVisible());
    XulViewRender localXulViewRender;
    RectF localRectF;
    do
    {
      return;
      localXulViewRender = paramXulView.getRender();
      localRectF = localXulViewRender.getUpdateRect();
      localRectF.left += this.xBase;
      localRectF.top += this.yBase;
      localRectF.right += this.xBase;
      localRectF.bottom += this.yBase;
    }
    while (this.canvas.quickReject(localRectF.left, localRectF.top, localRectF.right, localRectF.bottom, Canvas.EdgeType.AA));
    if (localXulViewRender.needPostDraw())
    {
      this.dc.postDraw(paramXulView, this.rect, this.xBase, this.yBase, localXulViewRender.getZIndex());
      return;
    }
    paramXulView.draw(this.dc, this.rect, this.xBase, this.yBase);
  }

  public void init(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    this.dc = paramXulDC;
    this.canvas = paramXulDC.getCanvas();
    this.rect = paramRect;
    this.xBase = paramInt1;
    this.yBase = paramInt2;
  }

  public boolean onXulView(int paramInt, XulView paramXulView)
  {
    drawItem(paramXulView);
    return true;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Utils.XulAreaChildrenRender
 * JD-Core Version:    0.6.2
 */