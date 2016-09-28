package com.starcor.xul.Render;

import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Prop.XulAttr;
import com.starcor.xul.Prop.XulPropNameCache.TagId;
import com.starcor.xul.Prop.XulStyle;
import com.starcor.xul.Render.Components.BaseScrollBar.ScrollBarHelper;
import com.starcor.xul.Render.Components.SimpleScrollBar;
import com.starcor.xul.Render.Text.XulMarqueeTextRenderer;
import com.starcor.xul.Render.Text.XulMarqueeTextRenderer.XulMarqueeTextEditor;
import com.starcor.xul.Render.Text.XulSimpleTextRenderer;
import com.starcor.xul.Render.Text.XulSpannableTextRenderer;
import com.starcor.xul.Render.Text.XulTextRenderer;
import com.starcor.xul.Utils.XulLayoutHelper.ILayoutElement;
import com.starcor.xul.Utils.XulPropParser.xulParsedAttr_Text_Marquee;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_Float;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_booleanValue;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FixHalfChar;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontAlign;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontColor;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontScaleX;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontShadow;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontSize;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontStyleStrike;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontWeight;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_LineHeight;
import com.starcor.xul.XulItem;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;

public class XulLabelRender extends XulViewRender
{
  private static final String TAG = XulLabelRender.class.getSimpleName();
  protected XulTextRenderer _baseTextRenderer;
  private SimpleScrollBar _scrollBar;
  private BaseScrollBar.ScrollBarHelper _scrollBarHelper;
  int _scrollTargetY = 0;
  int _scrollX = 0;
  int _scrollY = 0;
  protected XulMarqueeTextRenderer _textRenderer;

  public XulLabelRender(XulRenderContext paramXulRenderContext, XulView paramXulView)
  {
    super(paramXulRenderContext, paramXulView);
  }

  private BaseScrollBar.ScrollBarHelper _getScrollBarHelper()
  {
    if (this._scrollBarHelper == null)
      this._scrollBarHelper = new BaseScrollBar.ScrollBarHelper()
      {
        public int getContentHeight()
        {
          return (int)XulLabelRender.this._textRenderer.getHeight();
        }

        public int getContentWidth()
        {
          return (int)XulLabelRender.this._textRenderer.getWidth();
        }

        public int getScrollPos()
        {
          return XulLabelRender.this._scrollY;
        }

        public boolean isVertical()
        {
          return true;
        }
      };
    return this._scrollBarHelper;
  }

  public static void register()
  {
    XulRenderFactory.registerBuilder("item", "label", new XulRenderFactory.RenderBuilder()
    {
      static
      {
        if (!XulLabelRender.class.desiredAssertionStatus());
        for (boolean bool = true; ; bool = false)
        {
          $assertionsDisabled = bool;
          return;
        }
      }

      protected XulViewRender createRender(XulRenderContext paramAnonymousXulRenderContext, XulView paramAnonymousXulView)
      {
        assert ((paramAnonymousXulView instanceof XulItem));
        return new XulLabelRender(paramAnonymousXulRenderContext, paramAnonymousXulView);
      }
    });
  }

  protected XulLayoutHelper.ILayoutElement createElement()
  {
    return new LayoutElement();
  }

  public void destroy()
  {
    XulMarqueeTextRenderer localXulMarqueeTextRenderer = this._textRenderer;
    this._baseTextRenderer = null;
    this._textRenderer = null;
    if (localXulMarqueeTextRenderer != null)
      localXulMarqueeTextRenderer.stopAnimation();
    super.destroy();
  }

  public void draw(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    if (_isInvisible())
      return;
    super.drawBackground(paramXulDC, paramRect, paramInt1, paramInt2);
    drawText(paramXulDC, paramRect, paramInt1, paramInt2);
    super.drawBorder(paramXulDC, paramRect, paramInt1, paramInt2);
  }

  public void drawText(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    if (_isInvisible());
    XulMarqueeTextRenderer localXulMarqueeTextRenderer;
    Rect localRect;
    int i;
    int j;
    int k;
    int m;
    do
    {
      do
      {
        return;
        localXulMarqueeTextRenderer = this._textRenderer;
      }
      while ((localXulMarqueeTextRenderer == null) || (localXulMarqueeTextRenderer.isEmpty()));
      localRect = this._rect;
      i = XulUtils.calRectHeight(localRect);
      j = XulUtils.calRectWidth(localRect);
      k = j - this._padding.left - this._padding.right;
      m = i - this._padding.top - this._padding.bottom;
    }
    while ((k <= 0) || (m <= 0));
    int n = paramInt1 + this._screenX + localRect.left;
    int i1 = paramInt2 + this._screenY + localRect.top;
    int i2 = n + this._padding.left;
    int i3 = i1 + this._padding.top;
    paramXulDC.save();
    if ((Math.abs(this._scalarX - 1.0F) > 0.001F) || (Math.abs(this._scalarY - 1.0F) > 0.001F))
    {
      float f1 = j * this._scalarXAlign;
      float f2 = i * this._scalarYAlign;
      paramXulDC.scale(this._scalarX, this._scalarY, f1 + n, f2 + i1);
    }
    SimpleScrollBar localSimpleScrollBar = this._scrollBar;
    if (localSimpleScrollBar != null)
      paramXulDC.save();
    paramXulDC.translate(i2, i3);
    if ((getWidth() >= k) || (getHeight() >= m))
      paramXulDC.clipRect(0, 0, k, m);
    localXulMarqueeTextRenderer.drawText(paramXulDC, this._scrollX, this._scrollY, k, m);
    if (localSimpleScrollBar != null)
    {
      paramXulDC.restore();
      localSimpleScrollBar.draw(paramXulDC, paramRect, XulUtils.roundToInt(paramInt1 + localRect.left), XulUtils.roundToInt(paramInt2 + localRect.top));
    }
    paramXulDC.restore();
    label375: int i4;
    int i5;
    if (this._scrollTargetY == this._scrollY)
    {
      if (localXulMarqueeTextRenderer.getHeight() <= m)
        this._scrollTargetY = 0;
    }
    else
    {
      if (this._scrollTargetY == this._scrollY)
        break label461;
      i4 = this._scrollY - this._scrollTargetY;
      if (Math.abs(i4) <= 4)
        break label463;
      i5 = i4 / 2;
    }
    while (true)
    {
      this._scrollY -= i5;
      markDirtyView();
      return;
      if (this._scrollY + localXulMarqueeTextRenderer.getHeight() >= m)
        break label375;
      this._scrollTargetY = ((int)(m - localXulMarqueeTextRenderer.getHeight()));
      break label375;
      label461: break;
      label463: if (i4 > 0)
        i5 = Math.min(i4, 2);
      else
        i5 = Math.max(i4, -2);
    }
  }

  public int getDefaultFocusMode()
  {
    return 4;
  }

  protected Paint getTextPaint()
  {
    return this._textRenderer.getTextPaint();
  }

  protected XulTextRenderer getTextRenderer()
  {
    return this._textRenderer;
  }

  public boolean onKeyEvent(KeyEvent paramKeyEvent)
  {
    XulMarqueeTextRenderer localXulMarqueeTextRenderer = this._textRenderer;
    if (localXulMarqueeTextRenderer == null);
    float f3;
    int m;
    do
    {
      int j;
      float f1;
      float f2;
      do
      {
        int i;
        do
        {
          do
            return false;
          while ((!localXulMarqueeTextRenderer.isMultiline()) || ((localXulMarqueeTextRenderer.isAutoWrap()) && (localXulMarqueeTextRenderer.isDrawingEllipsis())));
          i = 3;
          j = XulUtils.calRectHeight(this._rect) - this._padding.top - this._padding.bottom;
          f1 = localXulMarqueeTextRenderer.getHeight();
          f2 = localXulMarqueeTextRenderer.getLineHeight();
        }
        while ((f1 <= j) || (paramKeyEvent.getAction() != 0));
        int k = (int)(j / f2 - 1.0F);
        if (k <= 0)
          k = 1;
        if (i > k)
          i = k;
        f3 = f2 * i;
        switch (paramKeyEvent.getKeyCode())
        {
        default:
          return false;
        case 19:
        case 20:
        }
      }
      while (this._scrollTargetY >= 0);
      this._scrollTargetY = ((int)(f3 + this._scrollTargetY));
      this._scrollTargetY = ((int)(this._scrollTargetY - this._scrollTargetY % f2));
      if (this._scrollTargetY > 0)
        this._scrollTargetY = 0;
      if (this._scrollBar != null)
        this._scrollBar.reset();
      markDirtyView();
      return true;
      m = (int)(j - f1);
    }
    while (this._scrollTargetY <= m);
    this._scrollTargetY = ((int)(this._scrollTargetY - f3));
    if (this._scrollTargetY < m)
      this._scrollTargetY = m;
    if (this._scrollBar != null)
      this._scrollBar.reset();
    markDirtyView();
    return true;
  }

  public void onVisibilityChanged(boolean paramBoolean, XulView paramXulView)
  {
    if ((!paramBoolean) && (this._textRenderer != null))
      this._textRenderer.stopAnimation();
    super.onVisibilityChanged(paramBoolean, paramXulView);
  }

  protected void prepareRenderer()
  {
    if ("spannable".equals(this._view.getStyleString(XulPropNameCache.TagId.FONT_RENDER)))
      if (!(this._baseTextRenderer instanceof XulSpannableTextRenderer))
        this._baseTextRenderer = new XulSpannableTextRenderer(this);
    while (this._textRenderer == null)
    {
      this._textRenderer = new XulMarqueeTextRenderer(this, this._baseTextRenderer);
      return;
      if (!(this._baseTextRenderer instanceof XulSimpleTextRenderer))
        this._baseTextRenderer = new XulSimpleTextRenderer(this);
    }
    this._textRenderer.updateBaseTextRender(this._baseTextRenderer);
  }

  public void syncData()
  {
    if (!_isDataChanged())
      return;
    super.syncData();
    XulAttr localXulAttr = this._view.getAttr(XulPropNameCache.TagId.SCROLLBAR);
    if ((localXulAttr == null) || (localXulAttr.getValue() == null))
    {
      XulMarqueeTextRenderer localXulMarqueeTextRenderer = this._textRenderer;
      if ((localXulMarqueeTextRenderer != null) && (localXulMarqueeTextRenderer.isMultiline()) && ((!localXulMarqueeTextRenderer.isAutoWrap()) || (!localXulMarqueeTextRenderer.isDrawingEllipsis())))
      {
        this._scrollBar = SimpleScrollBar.buildScrollBar(this._scrollBar, "simple", _getScrollBarHelper(), this);
        return;
      }
      this._scrollBar = null;
      return;
    }
    this._scrollBar = SimpleScrollBar.buildScrollBar(this._scrollBar, localXulAttr.getStringValue(), _getScrollBarHelper(), this);
  }

  protected void syncTextInfo()
  {
    syncTextInfo(false);
  }

  protected void syncTextInfo(boolean paramBoolean)
  {
    if (!_isViewChanged())
      return;
    XulMarqueeTextRenderer.XulMarqueeTextEditor localXulMarqueeTextEditor = this._textRenderer.edit();
    XulAttr localXulAttr1 = this._view.getAttr(XulPropNameCache.TagId.TEXT);
    label248: double d1;
    label269: label290: label305: label326: label347: label368: label500: String str1;
    label389: label427: label453: label479: label611: String str2;
    label526: label581: String str3;
    label640: label669: label698: XulAttr localXulAttr2;
    if ((localXulAttr1 != null) && (localXulAttr1.getValue() != null))
    {
      localXulMarqueeTextEditor.setText(localXulAttr1.getStringValue());
      XulStyle localXulStyle1 = this._view.getStyle(XulPropNameCache.TagId.STYLE_FIX_HALF_CHAR);
      XulStyle localXulStyle2 = this._view.getStyle(XulPropNameCache.TagId.FONT_FACE);
      XulStyle localXulStyle3 = this._view.getStyle(XulPropNameCache.TagId.FONT_SIZE);
      XulStyle localXulStyle4 = this._view.getStyle(XulPropNameCache.TagId.FONT_COLOR);
      XulStyle localXulStyle5 = this._view.getStyle(XulPropNameCache.TagId.FONT_WEIGHT);
      XulStyle localXulStyle6 = this._view.getStyle(XulPropNameCache.TagId.FONT_SHADOW);
      XulStyle localXulStyle7 = this._view.getStyle(XulPropNameCache.TagId.FONT_ALIGN);
      XulStyle localXulStyle8 = this._view.getStyle(XulPropNameCache.TagId.FONT_STYLE_UNDERLINE);
      XulStyle localXulStyle9 = this._view.getStyle(XulPropNameCache.TagId.FONT_STYLE_ITALIC);
      XulStyle localXulStyle10 = this._view.getStyle(XulPropNameCache.TagId.LINE_HEIGHT);
      XulStyle localXulStyle11 = this._view.getStyle(XulPropNameCache.TagId.FONT_SCALE_X);
      XulStyle localXulStyle12 = this._view.getStyle(XulPropNameCache.TagId.FONT_STYLE_STRIKE);
      XulStyle localXulStyle13 = this._view.getStyle(XulPropNameCache.TagId.START_INDENT);
      XulStyle localXulStyle14 = this._view.getStyle(XulPropNameCache.TagId.END_INDENT);
      XulStyle localXulStyle15 = this._view.getStyle(XulPropNameCache.TagId.FONT_RESAMPLE);
      if (localXulStyle15 == null)
        break label738;
      localXulMarqueeTextEditor.setSuperResample(((XulPropParser.xulParsedProp_Float)localXulStyle15.getParsedValue()).val);
      if (localXulStyle11 == null)
        break label747;
      localXulMarqueeTextEditor.fontScaleX(((XulPropParser.xulParsedStyle_FontScaleX)localXulStyle11.getParsedValue()).val);
      if (localXulStyle12 == null)
        break label756;
      localXulMarqueeTextEditor.setFontStrikeThrough(((XulPropParser.xulParsedStyle_FontStyleStrike)localXulStyle12.getParsedValue()).val);
      if (localXulStyle2 == null)
        break label765;
      localXulMarqueeTextEditor.setFontFace(localXulStyle2.getStringValue());
      if (localXulStyle10 == null)
        break label774;
      localXulMarqueeTextEditor.setLineHeightScalar(((XulPropParser.xulParsedStyle_LineHeight)localXulStyle10.getParsedValue()).val);
      if (localXulStyle8 == null)
        break label783;
      localXulMarqueeTextEditor.setUnderline(((XulPropParser.xulParsedProp_booleanValue)localXulStyle8.getParsedValue()).val);
      if (localXulStyle9 == null)
        break label792;
      localXulMarqueeTextEditor.setItalic(((XulPropParser.xulParsedProp_booleanValue)localXulStyle9.getParsedValue()).val);
      if (localXulStyle1 == null)
        break label801;
      localXulMarqueeTextEditor.setFixHalfChar(((XulPropParser.xulParsedStyle_FixHalfChar)localXulStyle1.getParsedValue()).val);
      d1 = getXScalar();
      double d2 = getYScalar();
      if (localXulStyle3 == null)
        break label810;
      localXulMarqueeTextEditor.setFontSize((float)(d1 * ((XulPropParser.xulParsedStyle_FontSize)localXulStyle3.getParsedValue()).val));
      if (localXulStyle13 == null)
        break label825;
      localXulMarqueeTextEditor.setStartIndent((float)(d1 * ((XulPropParser.xulParsedProp_Float)localXulStyle13.getParsedValue()).val));
      if (localXulStyle14 == null)
        break label834;
      localXulMarqueeTextEditor.setEndIndent((float)(d1 * ((XulPropParser.xulParsedProp_Float)localXulStyle14.getParsedValue()).val));
      if (localXulStyle4 == null)
        break label843;
      localXulMarqueeTextEditor.setFontColor(((XulPropParser.xulParsedStyle_FontColor)localXulStyle4.getParsedValue()).val);
      if (localXulStyle5 == null)
        break label854;
      localXulMarqueeTextEditor.setFontWeight((float)(d1 * ((XulPropParser.xulParsedStyle_FontWeight)localXulStyle5.getParsedValue()).val));
      if (localXulStyle6 == null)
        break label867;
      XulPropParser.xulParsedStyle_FontShadow localxulParsedStyle_FontShadow = (XulPropParser.xulParsedStyle_FontShadow)localXulStyle6.getParsedValue();
      localXulMarqueeTextEditor.setFontShadow((float)(d1 * localxulParsedStyle_FontShadow.xOffset), (float)(d2 * localxulParsedStyle_FontShadow.yOffset), (float)(d1 * localxulParsedStyle_FontShadow.size), localxulParsedStyle_FontShadow.color);
      if (localXulStyle7 == null)
        break label879;
      XulPropParser.xulParsedStyle_FontAlign localxulParsedStyle_FontAlign = (XulPropParser.xulParsedStyle_FontAlign)localXulStyle7.getParsedValue();
      localXulMarqueeTextEditor.setFontAlignment(localxulParsedStyle_FontAlign.xAlign, localxulParsedStyle_FontAlign.yAlign);
      str1 = this._view.getAttrString(XulPropNameCache.TagId.MULTI_LINE);
      if (!TextUtils.isEmpty(str1))
        break label889;
      localXulMarqueeTextEditor.setMultiline(localXulMarqueeTextEditor.defMultiline());
      str2 = this._view.getAttrString(XulPropNameCache.TagId.AUTO_WRAP);
      if (!TextUtils.isEmpty(str2))
        break label918;
      localXulMarqueeTextEditor.setAutoWrap(localXulMarqueeTextEditor.defAutoWrap());
      str3 = this._view.getAttrString(XulPropNameCache.TagId.ELLIPSIS);
      if (!TextUtils.isEmpty(str3))
        break label947;
      localXulMarqueeTextEditor.setDrawEllipsis(localXulMarqueeTextEditor.defDrawEllipsis());
      localXulAttr2 = this._view.getAttr(XulPropNameCache.TagId.MARQUEE);
      if (localXulAttr2 != null)
        break label976;
      localXulMarqueeTextEditor.setTextMarquee(null);
    }
    while (true)
    {
      localXulMarqueeTextEditor.finish(paramBoolean);
      return;
      localXulMarqueeTextEditor.setText("");
      break;
      label738: localXulMarqueeTextEditor.setSuperResample(1.0F);
      break label248;
      label747: localXulMarqueeTextEditor.fontScaleX(1.0F);
      break label269;
      label756: localXulMarqueeTextEditor.setFontStrikeThrough(false);
      break label290;
      label765: localXulMarqueeTextEditor.setFontFace(null);
      break label305;
      label774: localXulMarqueeTextEditor.setLineHeightScalar(1.0F);
      break label326;
      label783: localXulMarqueeTextEditor.setUnderline(false);
      break label347;
      label792: localXulMarqueeTextEditor.setItalic(false);
      break label368;
      label801: localXulMarqueeTextEditor.setFixHalfChar(false);
      break label389;
      label810: localXulMarqueeTextEditor.setFontSize((float)(12.0D * d1));
      break label427;
      label825: localXulMarqueeTextEditor.setStartIndent(0.0F);
      break label453;
      label834: localXulMarqueeTextEditor.setEndIndent(0.0F);
      break label479;
      label843: localXulMarqueeTextEditor.setFontColor(-16777216);
      break label500;
      label854: localXulMarqueeTextEditor.setFontWeight((float)(1.0D * d1));
      break label526;
      label867: localXulMarqueeTextEditor.setFontShadow(0.0F, 0.0F, 0.0F, 0);
      break label581;
      label879: localXulMarqueeTextEditor.setFontAlignment(0.0F, 0.0F);
      break label611;
      label889: if ("true".equals(str1))
      {
        localXulMarqueeTextEditor.setMultiline(true);
        break label640;
      }
      localXulMarqueeTextEditor.setMultiline(false);
      break label640;
      label918: if ("true".equals(str2))
      {
        localXulMarqueeTextEditor.setAutoWrap(true);
        break label669;
      }
      localXulMarqueeTextEditor.setAutoWrap(false);
      break label669;
      label947: if ("true".equals(str3))
      {
        localXulMarqueeTextEditor.setDrawEllipsis(true);
        break label698;
      }
      localXulMarqueeTextEditor.setDrawEllipsis(false);
      break label698;
      label976: localXulMarqueeTextEditor.setTextMarquee((XulPropParser.xulParsedAttr_Text_Marquee)localXulAttr2.getParsedValue());
    }
  }

  protected class LayoutElement extends XulViewRender.LayoutElement
  {
    boolean _autoHeight = false;
    int _initialWidth = 0;
    boolean _paddingChanged = false;
    boolean _recalAutoWrap = false;

    protected LayoutElement()
    {
      super();
    }

    public int getContentHeight()
    {
      return (int)XulLabelRender.this._textRenderer.getHeight();
    }

    public int getContentWidth()
    {
      return (int)XulLabelRender.this._textRenderer.getWidth();
    }

    public int prepare()
    {
      boolean bool1 = true;
      int i;
      int j;
      label66: int k;
      label104: boolean bool2;
      if (XulLabelRender.this._rect == null)
      {
        i = 0;
        this._initialWidth = i;
        this._recalAutoWrap = false;
        this._paddingChanged = false;
        this._autoHeight = false;
        if (XulLabelRender.this._padding == null)
          break label258;
        j = XulLabelRender.this._padding.left + XulLabelRender.this._padding.right;
        super.prepare();
        if (XulLabelRender.this._padding == null)
          break label263;
        k = XulLabelRender.this._padding.left + XulLabelRender.this._padding.right;
        if (j - k != 0)
          this._paddingChanged = bool1;
        XulLabelRender.this.prepareRenderer();
        if (XulLabelRender.this._isVisible())
          XulLabelRender.this.syncTextInfo();
        if (XulLabelRender.this._rect != null)
        {
          int m = XulUtils.calRectWidth(XulLabelRender.this._rect);
          int n = XulUtils.calRectHeight(XulLabelRender.this._rect);
          if ((n != 2147483645) && (n != 2147483646))
            break label269;
          bool2 = bool1;
          label191: this._autoHeight = bool2;
          if ((!XulLabelRender.this._textRenderer.isMultiline()) || (!XulLabelRender.this._textRenderer.isAutoWrap()) || (!this._autoHeight) || (m != 2147483644))
            break label275;
        }
      }
      while (true)
      {
        this._recalAutoWrap = bool1;
        return 0;
        i = XulUtils.calRectWidth(XulLabelRender.this._rect);
        break;
        label258: j = 0;
        break label66;
        label263: k = 0;
        break label104;
        label269: bool2 = false;
        break label191;
        label275: bool1 = false;
      }
    }

    public boolean setWidth(int paramInt)
    {
      boolean bool;
      if ((this._recalAutoWrap) && (this._initialWidth != paramInt))
      {
        bool = super.setWidth(paramInt);
        XulLabelRender.this.syncTextInfo(true);
        setHeight((int)(XulLabelRender.this._textRenderer.getHeight() + XulLabelRender.this._padding.top + XulLabelRender.this._padding.bottom));
      }
      do
      {
        return bool;
        if ((!XulLabelRender.this._textRenderer.isMultiline()) || (!XulLabelRender.this._textRenderer.isAutoWrap()) || (!this._paddingChanged))
          break;
        bool = super.setWidth(paramInt);
        XulLabelRender.this.syncTextInfo(true);
      }
      while (!this._autoHeight);
      setHeight((int)(XulLabelRender.this._textRenderer.getHeight() + XulLabelRender.this._padding.top + XulLabelRender.this._padding.bottom));
      return bool;
      return super.setWidth(paramInt);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.XulLabelRender
 * JD-Core Version:    0.6.2
 */