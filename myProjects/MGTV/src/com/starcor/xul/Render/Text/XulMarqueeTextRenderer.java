package com.starcor.xul.Render.Text;

import android.graphics.Paint;
import android.graphics.Rect;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Render.Drawer.IXulAnimation;
import com.starcor.xul.Render.Transform.ITransformer;
import com.starcor.xul.Render.XulViewRender;
import com.starcor.xul.Utils.XulPropParser.xulParsedAttr_Text_Marquee;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulWorker.DrawableItem;

public class XulMarqueeTextRenderer extends XulTextRenderer
{
  private XulMarqueeTextEditor _editor;
  TextMarqueeAnimation _marqueeAnimation;
  int _marqueeDirection = 1;
  float _marqueePosition = -1.0F;
  final XulViewRender _render;
  XulTextRenderer _textRenderer;

  public XulMarqueeTextRenderer(XulViewRender paramXulViewRender, XulTextRenderer paramXulTextRenderer)
  {
    this._render = paramXulViewRender;
    this._textRenderer = paramXulTextRenderer;
  }

  public XulWorker.DrawableItem collectPendingImageItem()
  {
    return this._textRenderer.collectPendingImageItem();
  }

  public void drawText(XulDC paramXulDC, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if ((this._marqueeAnimation == null) || (!this._marqueeAnimation._running) || (isMultiline()) || (isAutoWrap()) || (getWidth() <= paramFloat3))
    {
      stopMarqueeAnimation();
      this._textRenderer.drawText(paramXulDC, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
      return;
    }
    int i = this._marqueeAnimation._marqueeSpace;
    if (i > 0);
    for (float f1 = i; ; f1 = -paramFloat3 * i / 100.0F)
    {
      float f2 = 0.0F - this._marqueePosition * this._marqueeDirection;
      float f3 = getWidth();
      if ((f2 < paramFloat3) || (f2 + f3 > 0.0F))
      {
        paramXulDC.save(1);
        this._textRenderer.drawText(paramXulDC, f2 + paramFloat1, paramFloat2, f3, paramFloat4);
        paramXulDC.restore();
      }
      float f4 = f2 + (f3 + f1) * this._marqueeDirection;
      if ((f4 >= paramFloat3) && (f4 + f3 <= 0.0F))
        break;
      this._textRenderer.drawText(paramXulDC, f4, paramFloat2, f3, paramFloat4);
      return;
    }
  }

  public XulMarqueeTextEditor edit()
  {
    if (this._editor == null)
      this._editor = new XulMarqueeTextEditor(this._textRenderer.edit());
    while (true)
    {
      return this._editor;
      this._editor.update(this._textRenderer.edit());
    }
  }

  public float getHeight()
  {
    return this._textRenderer.getHeight();
  }

  public float getLineHeight()
  {
    return this._textRenderer.getLineHeight();
  }

  public Paint getTextPaint()
  {
    return this._textRenderer.getTextPaint();
  }

  public float getWidth()
  {
    return this._textRenderer.getWidth();
  }

  public boolean isAutoWrap()
  {
    return this._textRenderer.isAutoWrap();
  }

  public boolean isDrawingEllipsis()
  {
    return this._textRenderer.isDrawingEllipsis();
  }

  public boolean isEmpty()
  {
    return this._textRenderer.isEmpty();
  }

  public boolean isMultiline()
  {
    return this._textRenderer.isMultiline();
  }

  public void stopAnimation()
  {
    this._textRenderer.stopAnimation();
    stopMarqueeAnimation();
  }

  protected void stopMarqueeAnimation()
  {
    if (this._marqueeAnimation == null);
    do
    {
      return;
      this._marqueeAnimation.stop();
    }
    while (this._marqueePosition < 0.0F);
    this._render.markDirtyView();
    this._marqueePosition = -1.0F;
  }

  public void updateBaseTextRender(XulTextRenderer paramXulTextRenderer)
  {
    this._textRenderer = paramXulTextRenderer;
  }

  class TextMarqueeAnimation
    implements IXulAnimation
  {
    long _beginTime;
    long _intervalBeginTime;
    int _marqueeDelay = 500;
    int _marqueeInterval = 500;
    int _marqueeSpace = 60;
    int _marqueeSpeed = 0;
    XulViewRender _render;
    boolean _running = false;

    public TextMarqueeAnimation(XulViewRender arg2)
    {
      Object localObject;
      this._render = localObject;
    }

    public TextMarqueeAnimation(XulViewRender paramITransformer, ITransformer arg3)
    {
      this._render = paramITransformer;
    }

    public void prepareMarqueeAnimation(XulPropParser.xulParsedAttr_Text_Marquee paramxulParsedAttr_Text_Marquee)
    {
      this._marqueeDelay = paramxulParsedAttr_Text_Marquee.delay;
      this._marqueeInterval = paramxulParsedAttr_Text_Marquee.interval;
      this._marqueeSpace = paramxulParsedAttr_Text_Marquee.space;
      this._marqueeSpeed = paramxulParsedAttr_Text_Marquee.speed;
      XulMarqueeTextRenderer localXulMarqueeTextRenderer = XulMarqueeTextRenderer.this;
      localXulMarqueeTextRenderer._marqueeDirection *= paramxulParsedAttr_Text_Marquee.direction;
      this._running = true;
      if ((paramxulParsedAttr_Text_Marquee.speed != this._marqueeSpeed) || (XulMarqueeTextRenderer.this._marqueePosition < 0.0F))
      {
        long l = this._render.animationTimestamp();
        this._beginTime = l;
        this._intervalBeginTime = l;
        this._marqueeSpeed = paramxulParsedAttr_Text_Marquee.speed;
        if (this._marqueeSpeed == 0)
          XulMarqueeTextRenderer.this._marqueePosition = -1.0F;
      }
      else
      {
        return;
      }
      XulMarqueeTextRenderer.this._marqueePosition = 0.0F;
    }

    public void stop()
    {
      this._running = false;
    }

    public boolean updateAnimation(long paramLong)
    {
      if ((this._marqueeSpeed <= 0) || (!this._running))
      {
        XulMarqueeTextRenderer.this._marqueePosition = -1.0F;
        return false;
      }
      long l1 = paramLong - this._beginTime;
      if (l1 <= this._marqueeDelay)
        return true;
      if (XulMarqueeTextRenderer.this.isMultiline())
        return false;
      if (this._render.isInvisible())
        return false;
      Rect localRect = this._render.getPadding();
      float f1;
      float f2;
      if (this._intervalBeginTime == this._beginTime)
      {
        long l3 = l1 - this._marqueeDelay;
        XulMarqueeTextRenderer.this._marqueePosition = (XulMarqueeTextRenderer.this.getHeight() / 1.1F * (float)l3 / this._marqueeSpeed);
        f1 = XulMarqueeTextRenderer.this.getWidth();
        if (this._marqueeSpace < 0)
          break label242;
        f2 = f1 + this._marqueeSpace;
        if (XulMarqueeTextRenderer.this._marqueePosition >= f2)
        {
          XulMarqueeTextRenderer.this._marqueePosition = -1.0F;
          this._intervalBeginTime = paramLong;
        }
      }
      if (XulMarqueeTextRenderer.this._marqueePosition < 0.0F)
      {
        long l2 = paramLong - (this._marqueeDelay - this._marqueeInterval);
        this._beginTime = l2;
        this._intervalBeginTime = l2;
      }
      for (XulArea localXulArea = this._render.getParentView(); ; localXulArea = localXulArea.getParent())
      {
        if ((localXulArea instanceof XulLayout))
        {
          this._render.markDirtyView();
          return true;
          label242: int i = this._render.getWidth();
          if (localRect != null)
            i -= localRect.left + localRect.right;
          f2 = f1 + -(i * this._marqueeSpace / 100);
          break;
        }
        if (localXulArea == null)
          return false;
        XulViewRender localXulViewRender = localXulArea.getRender();
        if (localXulViewRender == null)
          return false;
        if (localXulViewRender.isInvisible())
          return false;
      }
    }
  }

  public class XulMarqueeTextEditor extends XulTextRenderer.XulTextEditor
  {
    private XulTextRenderer.XulTextEditor _upperEditor;

    public XulMarqueeTextEditor(XulTextRenderer.XulTextEditor arg2)
    {
      super();
      Object localObject;
      this._upperEditor = localObject;
    }

    public boolean defAutoWrap()
    {
      return this._upperEditor.defAutoWrap();
    }

    public boolean defDrawEllipsis()
    {
      return this._upperEditor.defDrawEllipsis();
    }

    public boolean defMultiline()
    {
      return this._upperEditor.defMultiline();
    }

    public void finish(boolean paramBoolean)
    {
      this._upperEditor.finish(paramBoolean);
      if (XulMarqueeTextRenderer.this._render.isRTL())
        XulMarqueeTextRenderer.this._marqueeDirection = (-XulMarqueeTextRenderer.this._marqueeDirection);
    }

    public XulMarqueeTextEditor fontScaleX(float paramFloat)
    {
      this._upperEditor.fontScaleX(paramFloat);
      return this;
    }

    public XulMarqueeTextEditor setAutoWrap(boolean paramBoolean)
    {
      this._upperEditor.setAutoWrap(paramBoolean);
      return this;
    }

    public XulMarqueeTextEditor setDrawEllipsis(boolean paramBoolean)
    {
      this._upperEditor.setDrawEllipsis(paramBoolean);
      return this;
    }

    public XulMarqueeTextEditor setEndIndent(float paramFloat)
    {
      this._upperEditor.setEndIndent(paramFloat);
      return this;
    }

    public XulMarqueeTextEditor setFixHalfChar(boolean paramBoolean)
    {
      this._upperEditor.setFixHalfChar(paramBoolean);
      return this;
    }

    public XulMarqueeTextEditor setFontAlignment(float paramFloat1, float paramFloat2)
    {
      this._upperEditor.setFontAlignment(paramFloat1, paramFloat2);
      return this;
    }

    public XulMarqueeTextEditor setFontColor(int paramInt)
    {
      this._upperEditor.setFontColor(paramInt);
      return this;
    }

    public XulMarqueeTextEditor setFontFace(String paramString)
    {
      this._upperEditor.setFontFace(paramString);
      return this;
    }

    public XulMarqueeTextEditor setFontShadow(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt)
    {
      this._upperEditor.setFontShadow(paramFloat1, paramFloat2, paramFloat3, paramInt);
      return this;
    }

    public XulMarqueeTextEditor setFontSize(float paramFloat)
    {
      this._upperEditor.setFontSize(paramFloat);
      return this;
    }

    public XulMarqueeTextEditor setFontStrikeThrough(boolean paramBoolean)
    {
      this._upperEditor.setFontStrikeThrough(paramBoolean);
      return this;
    }

    public XulMarqueeTextEditor setFontWeight(float paramFloat)
    {
      this._upperEditor.setFontWeight(paramFloat);
      return this;
    }

    public XulMarqueeTextEditor setItalic(boolean paramBoolean)
    {
      this._upperEditor.setItalic(paramBoolean);
      return this;
    }

    public XulMarqueeTextEditor setLineHeightScalar(float paramFloat)
    {
      this._upperEditor.setLineHeightScalar(paramFloat);
      return this;
    }

    public XulMarqueeTextEditor setMultiline(boolean paramBoolean)
    {
      this._upperEditor.setMultiline(paramBoolean);
      return this;
    }

    public XulMarqueeTextEditor setStartIndent(float paramFloat)
    {
      this._upperEditor.setStartIndent(paramFloat);
      return this;
    }

    public XulMarqueeTextEditor setSuperResample(float paramFloat)
    {
      this._upperEditor.setSuperResample(paramFloat);
      return this;
    }

    public XulMarqueeTextEditor setText(String paramString)
    {
      this._upperEditor.setText(paramString);
      return this;
    }

    public XulMarqueeTextEditor setTextMarquee(XulPropParser.xulParsedAttr_Text_Marquee paramxulParsedAttr_Text_Marquee)
    {
      if (paramxulParsedAttr_Text_Marquee == null)
      {
        XulMarqueeTextRenderer.this.stopAnimation();
        return this;
      }
      if (XulMarqueeTextRenderer.this._marqueeAnimation == null)
        XulMarqueeTextRenderer.this._marqueeAnimation = new XulMarqueeTextRenderer.TextMarqueeAnimation(XulMarqueeTextRenderer.this, XulMarqueeTextRenderer.this._render);
      XulMarqueeTextRenderer.this._marqueeAnimation.prepareMarqueeAnimation(paramxulParsedAttr_Text_Marquee);
      XulMarqueeTextRenderer.this._render.addAnimation(XulMarqueeTextRenderer.this._marqueeAnimation);
      return this;
    }

    public XulMarqueeTextEditor setUnderline(boolean paramBoolean)
    {
      this._upperEditor.setUnderline(paramBoolean);
      return this;
    }

    public void update(XulTextRenderer.XulTextEditor paramXulTextEditor)
    {
      this._upperEditor = paramXulTextEditor;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Text.XulMarqueeTextRenderer
 * JD-Core Version:    0.6.2
 */