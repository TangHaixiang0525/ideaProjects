package com.starcor.xul.Render.Text;

import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.text.TextUtils;
import com.starcor.xul.Render.XulViewRender;
import com.starcor.xul.XulRenderContext;

public abstract class XulBasicTextRenderer extends XulTextRenderer
{
  protected boolean _autoWrap = false;
  protected boolean _drawEllipsis = false;
  protected float _ellipsisWidth;
  protected float _endIndent = 0.0F;
  protected boolean _fixHalfChar = false;
  protected float _fontAlignX = 0.0F;
  protected float _fontAlignY = 0.0F;
  protected int _fontColor = -16777216;
  protected String _fontFace = null;
  protected boolean _fontItalic = false;
  protected float _fontScaleX = 1.0F;
  protected int _fontShadowColor = 0;
  protected float _fontShadowSize = 0.0F;
  protected float _fontShadowX = 0.0F;
  protected float _fontShadowY = 0.0F;
  protected float _fontSize = 12.0F;
  protected boolean _fontStrikeThrough = false;
  protected boolean _fontUnderline = false;
  protected float _fontWeight = 0.0F;
  protected float _lineHeightScalar = 1.0F;
  protected boolean _multiline = false;
  protected final XulViewRender _render;
  protected float _startIndent = 0.0F;
  protected float _superResample = 1.0F;
  protected String _text = "";
  protected float _textBaseLineTop;
  protected float _textHeight;
  protected float _textLineHeight;
  protected float _textWidth;

  public XulBasicTextRenderer(XulViewRender paramXulViewRender)
  {
    this._render = paramXulViewRender;
  }

  protected Paint _getTextPaint()
  {
    return _getTextPaint(1.0F);
  }

  protected Paint _getTextPaint(float paramFloat)
  {
    this._render.getRenderContext();
    Paint localPaint = XulRenderContext.getTextPaintByName(this._fontFace);
    if ((this._fontShadowSize != 0.0F) && ((0xFF000000 & this._fontShadowColor) != 0))
    {
      localPaint = XulRenderContext.getShadowTextPaintByName(this._fontFace);
      localPaint.setShadowLayer(this._fontShadowSize, this._fontShadowX, this._fontShadowY, this._fontShadowColor);
    }
    localPaint.setColor(this._fontColor);
    if (Math.abs(paramFloat - 1.0F) > 0.01F)
    {
      localPaint.setTextSize(paramFloat * this._fontSize);
      if (this._fontWeight <= 1.0D)
        break label176;
      localPaint.setFakeBoldText(true);
    }
    while (true)
    {
      localPaint.setTextScaleX(this._fontScaleX);
      localPaint.setUnderlineText(this._fontUnderline);
      localPaint.setStrikeThruText(this._fontStrikeThrough);
      boolean bool = this._fontItalic;
      float f = 0.0F;
      if (bool)
        f = -0.25F;
      localPaint.setTextSkewX(f);
      localPaint.setTextAlign(Paint.Align.LEFT);
      return localPaint;
      localPaint.setTextSize(this._fontSize);
      break;
      label176: localPaint.setFakeBoldText(false);
    }
  }

  public float getHeight()
  {
    return this._textHeight;
  }

  public float getLineHeight()
  {
    return this._textLineHeight;
  }

  public Paint getTextPaint()
  {
    return _getTextPaint();
  }

  public float getWidth()
  {
    return this._textWidth;
  }

  public boolean isAutoWrap()
  {
    return this._autoWrap;
  }

  public boolean isDrawingEllipsis()
  {
    return this._drawEllipsis;
  }

  public boolean isEmpty()
  {
    return TextUtils.isEmpty(this._text);
  }

  public boolean isMultiline()
  {
    return this._multiline;
  }

  class BasicTextEditor extends XulTextRenderer.XulTextEditor
  {
    protected boolean _testAndSetAnyChanged = false;

    BasicTextEditor()
    {
      super();
    }

    protected float _testAndSet(float paramFloat1, float paramFloat2)
    {
      if (Math.abs(paramFloat1 - paramFloat2) < 0.001F)
        return paramFloat1;
      this._testAndSetAnyChanged = true;
      return paramFloat2;
    }

    protected <T> T _testAndSet(T paramT1, T paramT2)
    {
      if (paramT1 == paramT2)
        return paramT1;
      this._testAndSetAnyChanged = true;
      return paramT2;
    }

    protected String _testAndSet(String paramString1, String paramString2)
    {
      if ((paramString1 == paramString2) || (paramString2.equals(paramString1)))
        return paramString1;
      this._testAndSetAnyChanged = true;
      return paramString2;
    }

    public boolean defAutoWrap()
    {
      return false;
    }

    public boolean defDrawEllipsis()
    {
      return false;
    }

    public boolean defMultiline()
    {
      return false;
    }

    public void finish(boolean paramBoolean)
    {
      if (XulBasicTextRenderer.this._render.isRTL())
        XulBasicTextRenderer.this._fontAlignX = (1.0F - XulBasicTextRenderer.this._fontAlignX);
    }

    public XulTextRenderer.XulTextEditor fontScaleX(float paramFloat)
    {
      XulBasicTextRenderer.this._fontScaleX = _testAndSet(XulBasicTextRenderer.this._fontScaleX, paramFloat);
      return this;
    }

    public void reset()
    {
      this._testAndSetAnyChanged = false;
    }

    public XulTextRenderer.XulTextEditor setAutoWrap(boolean paramBoolean)
    {
      XulBasicTextRenderer.this._autoWrap = ((Boolean)_testAndSet(Boolean.valueOf(XulBasicTextRenderer.this._autoWrap), Boolean.valueOf(paramBoolean))).booleanValue();
      return this;
    }

    public XulTextRenderer.XulTextEditor setDrawEllipsis(boolean paramBoolean)
    {
      XulBasicTextRenderer.this._drawEllipsis = ((Boolean)_testAndSet(Boolean.valueOf(XulBasicTextRenderer.this._drawEllipsis), Boolean.valueOf(paramBoolean))).booleanValue();
      return this;
    }

    public XulTextRenderer.XulTextEditor setEndIndent(float paramFloat)
    {
      XulBasicTextRenderer.this._endIndent = _testAndSet(XulBasicTextRenderer.this._endIndent, paramFloat);
      return this;
    }

    public XulTextRenderer.XulTextEditor setFixHalfChar(boolean paramBoolean)
    {
      XulBasicTextRenderer.this._fixHalfChar = paramBoolean;
      return this;
    }

    public XulTextRenderer.XulTextEditor setFontAlignment(float paramFloat1, float paramFloat2)
    {
      XulBasicTextRenderer.this._fontAlignX = paramFloat1;
      XulBasicTextRenderer.this._fontAlignY = paramFloat2;
      return this;
    }

    public XulTextRenderer.XulTextEditor setFontColor(int paramInt)
    {
      XulBasicTextRenderer.this._fontColor = paramInt;
      return this;
    }

    public XulTextRenderer.XulTextEditor setFontFace(String paramString)
    {
      XulBasicTextRenderer.this._fontFace = _testAndSet(XulBasicTextRenderer.this._fontFace, paramString);
      return this;
    }

    public XulTextRenderer.XulTextEditor setFontShadow(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt)
    {
      XulBasicTextRenderer.this._fontShadowX = paramFloat1;
      XulBasicTextRenderer.this._fontShadowY = paramFloat2;
      XulBasicTextRenderer.this._fontShadowSize = paramFloat3;
      XulBasicTextRenderer.this._fontShadowColor = paramInt;
      return this;
    }

    public XulTextRenderer.XulTextEditor setFontSize(float paramFloat)
    {
      XulBasicTextRenderer.this._fontSize = _testAndSet(XulBasicTextRenderer.this._fontSize, paramFloat);
      return this;
    }

    public XulTextRenderer.XulTextEditor setFontStrikeThrough(boolean paramBoolean)
    {
      XulBasicTextRenderer.this._fontStrikeThrough = paramBoolean;
      return this;
    }

    public XulTextRenderer.XulTextEditor setFontWeight(float paramFloat)
    {
      XulBasicTextRenderer.this._fontWeight = _testAndSet(XulBasicTextRenderer.this._fontWeight, paramFloat);
      return this;
    }

    public XulTextRenderer.XulTextEditor setItalic(boolean paramBoolean)
    {
      XulBasicTextRenderer.this._fontItalic = ((Boolean)_testAndSet(Boolean.valueOf(XulBasicTextRenderer.this._fontItalic), Boolean.valueOf(false))).booleanValue();
      return this;
    }

    public XulTextRenderer.XulTextEditor setLineHeightScalar(float paramFloat)
    {
      XulBasicTextRenderer.this._lineHeightScalar = _testAndSet(XulBasicTextRenderer.this._lineHeightScalar, paramFloat);
      return this;
    }

    public XulTextRenderer.XulTextEditor setMultiline(boolean paramBoolean)
    {
      XulBasicTextRenderer.this._multiline = ((Boolean)_testAndSet(Boolean.valueOf(XulBasicTextRenderer.this._multiline), Boolean.valueOf(paramBoolean))).booleanValue();
      return this;
    }

    public XulTextRenderer.XulTextEditor setStartIndent(float paramFloat)
    {
      XulBasicTextRenderer.this._startIndent = _testAndSet(XulBasicTextRenderer.this._startIndent, paramFloat);
      return this;
    }

    public XulTextRenderer.XulTextEditor setSuperResample(float paramFloat)
    {
      XulBasicTextRenderer.this._superResample = paramFloat;
      return this;
    }

    public XulTextRenderer.XulTextEditor setText(String paramString)
    {
      XulBasicTextRenderer.this._text = _testAndSet(XulBasicTextRenderer.this._text, paramString);
      return this;
    }

    public XulTextRenderer.XulTextEditor setUnderline(boolean paramBoolean)
    {
      XulBasicTextRenderer.this._fontUnderline = paramBoolean;
      return this;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Text.XulBasicTextRenderer
 * JD-Core Version:    0.6.2
 */