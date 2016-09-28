package com.starcor.xul.Render;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.KeyEvent;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Graphics.XulDrawable;
import com.starcor.xul.Prop.XulAttr;
import com.starcor.xul.Prop.XulPropNameCache.TagId;
import com.starcor.xul.Prop.XulStyle;
import com.starcor.xul.Render.Components.BaseScrollBar.ScrollBarHelper;
import com.starcor.xul.Render.Components.SimpleScrollBar;
import com.starcor.xul.Render.Drawer.IXulAnimation;
import com.starcor.xul.Render.Drawer.XulDrawer;
import com.starcor.xul.Render.Transform.ITransformer;
import com.starcor.xul.Utils.XulCachedHashMap;
import com.starcor.xul.Utils.XulLayoutHelper.ILayoutElement;
import com.starcor.xul.Utils.XulPropParser.xulParsedAttr_Text_Marquee;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_Float;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_booleanValue;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontAlign;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontColor;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontScaleX;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontShadow;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontSize;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontStyleStrike;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_FontWeight;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_LineHeight;
import com.starcor.xul.Utils.XulRenderDrawableItem;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulItem;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DrawableItem;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class XulSpannedLabelRender extends XulViewRender
{
  private static final String TAG = XulSpannedLabelRender.class.getSimpleName();
  boolean _drawEllipsis = false;
  private XulDC _drawingDC;
  float _fontAlignX = 0.0F;
  float _fontAlignY = 0.0F;
  int _fontColor = -16777216;
  String _fontFace = null;
  boolean _fontItalic = false;
  float _fontScaleX = 1.0F;
  int _fontShadowColor = 0;
  float _fontShadowSize = 0.0F;
  float _fontShadowX = 0.0F;
  float _fontShadowY = 0.0F;
  float _fontSize = 12.0F;
  boolean _fontStrikeThrough = false;
  boolean _fontUnderline = false;
  float _fontWeight = 0.0F;
  private XulCachedHashMap<String, SpannedLabelImage> _imageCache;
  private volatile boolean _imageCacheChanged = false;
  private Html.ImageGetter _imageGetter;
  private float _lineHeightScalar = 1.0F;
  TextMarqueeAnimation _marqueeAnimation;
  int _marqueeDirection = 1;
  float _marqueePosition = -1.0F;
  boolean _multiLine = true;
  private SimpleScrollBar _scrollBar;
  private BaseScrollBar.ScrollBarHelper _scrollBarHelper;
  int _scrollTargetY = 0;
  int _scrollX = 0;
  int _scrollY = 0;
  private Spanned _spannedText;
  private float _superResample = 1.0F;
  private Object _textData;
  private int _textHeight;
  private Layout _textLayout;
  private int _textLineHeight;
  private int _textWidth;

  public XulSpannedLabelRender(XulRenderContext paramXulRenderContext, XulView paramXulView)
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
          return XulSpannedLabelRender.this._textHeight;
        }

        public int getContentWidth()
        {
          return XulSpannedLabelRender.this._textWidth;
        }

        public int getScrollPos()
        {
          return XulSpannedLabelRender.this._scrollY;
        }

        public boolean isVertical()
        {
          return true;
        }
      };
    return this._scrollBarHelper;
  }

  private Paint _getTextPaint()
  {
    return _getTextPaint(1.0F);
  }

  private Paint _getTextPaint(float paramFloat)
  {
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
        break label162;
      localPaint.setFakeBoldText(true);
    }
    while (true)
    {
      localPaint.setUnderlineText(this._fontUnderline);
      boolean bool = this._fontItalic;
      float f = 0.0F;
      if (bool)
        f = -0.25F;
      localPaint.setTextSkewX(f);
      localPaint.setTextAlign(Paint.Align.LEFT);
      return localPaint;
      localPaint.setTextSize(this._fontSize);
      break;
      label162: localPaint.setFakeBoldText(false);
    }
  }

  private void _stopMarqueeAnimation()
  {
    if (this._marqueeAnimation == null);
    do
    {
      return;
      this._marqueeAnimation.stop();
    }
    while (this._marqueePosition < 0.0F);
    markDirtyView();
    this._marqueePosition = -1.0F;
  }

  private static <T> T _testAndSet(T paramT1, T paramT2, boolean[] paramArrayOfBoolean)
  {
    if (paramT1 == paramT2)
      return paramT2;
    paramArrayOfBoolean[0] = true;
    return paramT1;
  }

  private static String _testAndSet(String paramString1, String paramString2, boolean[] paramArrayOfBoolean)
  {
    if (paramString1 == paramString2);
    while ((paramString1 != null) && (paramString2 != null) && (paramString1.equals(paramString2)))
      return paramString2;
    paramArrayOfBoolean[0] = true;
    return paramString1;
  }

  private void drawText(XulDC paramXulDC, int paramInt1, int paramInt2)
  {
    if (this._textLayout == null)
      return;
    paramXulDC.save();
    Rect localRect = this._rect;
    int i = XulUtils.calRectHeight(localRect);
    int j = XulUtils.calRectWidth(localRect);
    int k = paramInt1 + this._screenX + localRect.left;
    int m = paramInt2 + this._screenY + localRect.top;
    int n = k + this._padding.left;
    int i1 = m + this._padding.top;
    if ((Math.abs(this._scalarX - 1.0F) > 0.001F) || (Math.abs(this._scalarY - 1.0F) > 0.001F))
    {
      float f1 = j * this._scalarXAlign;
      float f2 = i * this._scalarYAlign;
      paramXulDC.scale(this._scalarX, this._scalarY, f1 + k, f2 + m);
    }
    paramXulDC.translate(n, i1);
    int i2 = j - this._padding.left - this._padding.right;
    int i3 = i - this._padding.top - this._padding.bottom;
    paramXulDC.clipRect(0, 0, i2, i3);
    float f3 = this._superResample;
    int i4;
    float f4;
    if (Math.abs(f3 - 1.0F) > 0.01F)
    {
      i4 = 1;
      if (!this._multiLine)
      {
        f4 = (i2 - this._textWidth) * this._fontAlignX;
        float f5 = (i3 - this._textHeight) * this._fontAlignY;
        if (!_isRTL())
          break label375;
        if (this._textWidth >= i2)
          f4 = i2 - this._textWidth;
        label305: paramXulDC.translate(f4, f5);
        if (this._textWidth > i2)
          break label390;
        _stopMarqueeAnimation();
      }
    }
    label375: 
    while ((this._marqueeAnimation == null) || (this._marqueePosition < 0.0F))
    {
      paramXulDC.translate(0.0F, this._scrollY);
      if (i4 != 0)
        paramXulDC.scale(1.0F / f3, 1.0F / f3);
      this._textLayout.draw(paramXulDC.getCanvas());
      paramXulDC.restore();
      return;
      i4 = 0;
      break;
      if (this._textWidth < i2)
        break label305;
      f4 = 0.0F;
      break label305;
    }
    label390: int i5 = this._marqueeAnimation._marqueeSpace;
    int i6;
    if (i5 > 0)
    {
      i6 = i5;
      label424: float f6 = -1.0F * this._marqueePosition * this._marqueeDirection;
      if ((f6 < i2) || (f6 + this._textWidth > 0.0F))
      {
        paramXulDC.translate(f6, 0.0F);
        if (i4 == 0)
          break label566;
        paramXulDC.save(1);
        paramXulDC.scale(1.0F / f3, 1.0F / f3);
        this._textLayout.draw(paramXulDC.getCanvas());
        paramXulDC.restore();
      }
    }
    while (true)
    {
      float f7 = (i6 + this._textWidth) * this._marqueeDirection;
      if ((f7 >= i2) && (f7 + this._textWidth <= 0.0F))
        break;
      paramXulDC.translate(f7, 0.0F);
      break;
      i6 = i5 * -i2 / 100;
      break label424;
      label566: this._textLayout.draw(paramXulDC.getCanvas());
    }
  }

  private Html.ImageGetter obtainImageGetter()
  {
    if (this._imageGetter != null)
      return this._imageGetter;
    this._imageGetter = new Html.ImageGetter()
    {
      public Drawable getDrawable(String paramAnonymousString)
      {
        if (XulSpannedLabelRender.this._imageCache == null)
          XulSpannedLabelRender.access$402(XulSpannedLabelRender.this, new XulCachedHashMap());
        XulSpannedLabelRender.SpannedLabelImage localSpannedLabelImage = (XulSpannedLabelRender.SpannedLabelImage)XulSpannedLabelRender.this._imageCache.get(paramAnonymousString);
        if (localSpannedLabelImage == null)
        {
          localSpannedLabelImage = new XulSpannedLabelRender.SpannedLabelImage(XulSpannedLabelRender.this, null);
          localSpannedLabelImage._source = paramAnonymousString;
          XulSpannedLabelRender.this._imageCache.put(paramAnonymousString, localSpannedLabelImage);
          XulSpannedLabelRender.access$602(XulSpannedLabelRender.this, true);
        }
        if (localSpannedLabelImage._drawable == null)
          return null;
        return localSpannedLabelImage._drawable;
      }
    };
    return this._imageGetter;
  }

  public static void register()
  {
    XulRenderFactory.registerBuilder("item", "spanned_label", new XulRenderFactory.RenderBuilder()
    {
      static
      {
        if (!XulSpannedLabelRender.class.desiredAssertionStatus());
        for (boolean bool = true; ; bool = false)
        {
          $assertionsDisabled = bool;
          return;
        }
      }

      protected XulViewRender createRender(XulRenderContext paramAnonymousXulRenderContext, XulView paramAnonymousXulView)
      {
        assert ((paramAnonymousXulView instanceof XulItem));
        return new XulSpannedLabelRender(paramAnonymousXulRenderContext, paramAnonymousXulView);
      }
    });
  }

  private void syncTextInfo()
  {
    if (!_isViewChanged());
    label276: label296: label564: label710: label839: label1608: 
    while (true)
    {
      return;
      boolean[] arrayOfBoolean = { false };
      XulStyle localXulStyle1 = this._view.getStyle(XulPropNameCache.TagId.FONT_FACE);
      XulStyle localXulStyle2 = this._view.getStyle(XulPropNameCache.TagId.FONT_SIZE);
      XulStyle localXulStyle3 = this._view.getStyle(XulPropNameCache.TagId.FONT_COLOR);
      XulStyle localXulStyle4 = this._view.getStyle(XulPropNameCache.TagId.FONT_WEIGHT);
      XulStyle localXulStyle5 = this._view.getStyle(XulPropNameCache.TagId.FONT_SHADOW);
      XulStyle localXulStyle6 = this._view.getStyle(XulPropNameCache.TagId.FONT_ALIGN);
      XulStyle localXulStyle7 = this._view.getStyle(XulPropNameCache.TagId.FONT_STYLE_UNDERLINE);
      XulStyle localXulStyle8 = this._view.getStyle(XulPropNameCache.TagId.FONT_STYLE_ITALIC);
      XulStyle localXulStyle9 = this._view.getStyle(XulPropNameCache.TagId.LINE_HEIGHT);
      XulStyle localXulStyle10 = this._view.getStyle(XulPropNameCache.TagId.FONT_SCALE_X);
      XulStyle localXulStyle11 = this._view.getStyle(XulPropNameCache.TagId.FONT_STYLE_STRIKE);
      XulStyle localXulStyle12 = this._view.getStyle(XulPropNameCache.TagId.FONT_RESAMPLE);
      label236: label376: double d1;
      label336: label479: label883: Object localObject;
      if (localXulStyle12 != null)
      {
        this._superResample = ((XulPropParser.xulParsedProp_Float)localXulStyle12.getParsedValue()).val;
        this._superResample = Math.min(Math.max(1.0F, this._superResample), 4.0F);
        if (localXulStyle10 == null)
          break label1026;
        this._fontScaleX = ((Float)_testAndSet(Float.valueOf(((XulPropParser.xulParsedStyle_FontScaleX)localXulStyle10.getParsedValue()).val), Float.valueOf(this._fontScaleX), arrayOfBoolean)).floatValue();
        if (localXulStyle11 == null)
          break label1054;
        this._fontStrikeThrough = ((Boolean)_testAndSet(Boolean.valueOf(((XulPropParser.xulParsedStyle_FontStyleStrike)localXulStyle11.getParsedValue()).val), Boolean.valueOf(this._fontStrikeThrough), arrayOfBoolean)).booleanValue();
        if (localXulStyle1 == null)
          break label1082;
        this._fontFace = _testAndSet(localXulStyle1.getStringValue(), this._fontFace, arrayOfBoolean);
        if (localXulStyle9 == null)
          break label1098;
        this._lineHeightScalar = ((Float)_testAndSet(Float.valueOf(((XulPropParser.xulParsedStyle_LineHeight)localXulStyle9.getParsedValue()).val), Float.valueOf(this._lineHeightScalar), arrayOfBoolean)).floatValue();
        if (localXulStyle7 == null)
          break label1126;
        this._fontUnderline = ((Boolean)_testAndSet(Boolean.valueOf(((XulPropParser.xulParsedProp_booleanValue)localXulStyle7.getParsedValue()).val), Boolean.valueOf(this._fontUnderline), arrayOfBoolean)).booleanValue();
        if (localXulStyle8 == null)
          break label1154;
        this._fontItalic = ((Boolean)_testAndSet(Boolean.valueOf(((XulPropParser.xulParsedProp_booleanValue)localXulStyle8.getParsedValue()).val), Boolean.valueOf(this._fontItalic), arrayOfBoolean)).booleanValue();
        d1 = this._ctx.getXScalar();
        double d2 = this._ctx.getYScalar();
        if (localXulStyle2 == null)
          break label1182;
        this._fontSize = ((Float)_testAndSet(Float.valueOf((float)(d1 * ((XulPropParser.xulParsedStyle_FontSize)localXulStyle2.getParsedValue()).val)), Float.valueOf(this._fontSize), arrayOfBoolean)).floatValue();
        if (localXulStyle3 == null)
          break label1216;
        this._fontColor = ((Integer)_testAndSet(Integer.valueOf(((XulPropParser.xulParsedStyle_FontColor)localXulStyle3.getParsedValue()).val), Integer.valueOf(this._fontColor), arrayOfBoolean)).intValue();
        if (localXulStyle4 == null)
          break label1245;
        this._fontWeight = ((Float)_testAndSet(Float.valueOf((float)(d1 * ((XulPropParser.xulParsedStyle_FontWeight)localXulStyle4.getParsedValue()).val)), Float.valueOf(this._fontWeight), arrayOfBoolean)).floatValue();
        if (localXulStyle5 == null)
          break label1277;
        XulPropParser.xulParsedStyle_FontShadow localxulParsedStyle_FontShadow = (XulPropParser.xulParsedStyle_FontShadow)localXulStyle5.getParsedValue();
        this._fontShadowX = ((Float)_testAndSet(Float.valueOf((float)(d1 * localxulParsedStyle_FontShadow.xOffset)), Float.valueOf(this._fontShadowX), arrayOfBoolean)).floatValue();
        this._fontShadowY = ((Float)_testAndSet(Float.valueOf((float)(d2 * localxulParsedStyle_FontShadow.yOffset)), Float.valueOf(this._fontShadowY), arrayOfBoolean)).floatValue();
        this._fontShadowSize = ((Float)_testAndSet(Float.valueOf((float)(d1 * localxulParsedStyle_FontShadow.size)), Float.valueOf(this._fontShadowSize), arrayOfBoolean)).floatValue();
        this._fontShadowColor = ((Integer)_testAndSet(Integer.valueOf(localxulParsedStyle_FontShadow.color), Integer.valueOf(this._fontShadowColor), arrayOfBoolean)).intValue();
        if (localXulStyle6 == null)
          break label1330;
        XulPropParser.xulParsedStyle_FontAlign localxulParsedStyle_FontAlign = (XulPropParser.xulParsedStyle_FontAlign)localXulStyle6.getParsedValue();
        this._fontAlignX = ((Float)_testAndSet(Float.valueOf(localxulParsedStyle_FontAlign.xAlign), Float.valueOf(this._fontAlignX), arrayOfBoolean)).floatValue();
        this._fontAlignY = ((Float)_testAndSet(Float.valueOf(localxulParsedStyle_FontAlign.yAlign), Float.valueOf(this._fontAlignY), arrayOfBoolean)).floatValue();
        XulAttr localXulAttr1 = this._view.getAttr(XulPropNameCache.TagId.MULTI_LINE);
        if ((localXulAttr1 != null) && (!"true".equals(localXulAttr1.getStringValue())))
          break label1383;
        this._multiLine = ((Boolean)_testAndSet(Boolean.valueOf(true), Boolean.valueOf(this._multiLine), arrayOfBoolean)).booleanValue();
        if (!"true".equals(this._view.getAttrString(XulPropNameCache.TagId.ELLIPSIS)))
          break label1411;
        this._drawEllipsis = ((Boolean)_testAndSet(Boolean.valueOf(true), Boolean.valueOf(this._drawEllipsis), arrayOfBoolean)).booleanValue();
        XulAttr localXulAttr2 = this._view.getAttr(XulPropNameCache.TagId.TEXT);
        if ((localXulAttr2 == null) || (localXulAttr2.getValue() == null))
          break label1504;
        localObject = localXulAttr2.getValue();
        if (localObject != null)
          break label1439;
        this._spannedText = null;
        this._textLayout = null;
        Paint.FontMetrics localFontMetrics = _getTextPaint().getFontMetrics();
        this._textLineHeight = XulUtils.ceilToInt((localFontMetrics.bottom - localFontMetrics.top) * this._lineHeightScalar);
        if (arrayOfBoolean[0] != 0)
          this._textLayout = null;
        if ((!this._multiLine) && (this._spannedText != null))
          break label1517;
        _stopMarqueeAnimation();
      }
      while (true)
      {
        if (!_isRTL())
          break label1608;
        this._fontAlignX = (1.0F - this._fontAlignX);
        this._marqueeDirection = (-this._marqueeDirection);
        return;
        this._superResample = 1.0F;
        break;
        this._fontScaleX = ((Float)_testAndSet(Float.valueOf(1.0F), Float.valueOf(this._fontScaleX), arrayOfBoolean)).floatValue();
        break label236;
        this._fontStrikeThrough = ((Boolean)_testAndSet(Boolean.valueOf(false), Boolean.valueOf(this._fontStrikeThrough), arrayOfBoolean)).booleanValue();
        break label276;
        this._fontFace = _testAndSet(null, this._fontFace, arrayOfBoolean);
        break label296;
        label1098: this._lineHeightScalar = ((Float)_testAndSet(Float.valueOf(1.0F), Float.valueOf(this._lineHeightScalar), arrayOfBoolean)).floatValue();
        break label336;
        label1126: this._fontUnderline = ((Boolean)_testAndSet(Boolean.valueOf(false), Boolean.valueOf(this._fontUnderline), arrayOfBoolean)).booleanValue();
        break label376;
        this._fontItalic = ((Boolean)_testAndSet(Boolean.valueOf(false), Boolean.valueOf(this._fontItalic), arrayOfBoolean)).booleanValue();
        break label416;
        this._fontSize = ((Float)_testAndSet(Float.valueOf((float)(12.0D * d1)), Float.valueOf(this._fontSize), arrayOfBoolean)).floatValue();
        break label479;
        this._fontColor = ((Integer)_testAndSet(Integer.valueOf(-16777216), Integer.valueOf(this._fontColor), arrayOfBoolean)).intValue();
        break label519;
        label1245: this._fontWeight = ((Float)_testAndSet(Float.valueOf((float)(1.0D * d1)), Float.valueOf(this._fontWeight), arrayOfBoolean)).floatValue();
        break label564;
        label1277: this._fontShadowSize = ((Float)_testAndSet(Float.valueOf(0.0F), Float.valueOf(this._fontShadowSize), arrayOfBoolean)).floatValue();
        this._fontShadowColor = ((Integer)_testAndSet(Integer.valueOf(0), Integer.valueOf(this._fontShadowColor), arrayOfBoolean)).intValue();
        break label710;
        this._fontAlignX = ((Float)_testAndSet(Float.valueOf(0.0F), Float.valueOf(this._fontAlignX), arrayOfBoolean)).floatValue();
        this._fontAlignY = ((Float)_testAndSet(Float.valueOf(0.0F), Float.valueOf(this._fontAlignY), arrayOfBoolean)).floatValue();
        break label783;
        label1383: this._multiLine = ((Boolean)_testAndSet(Boolean.valueOf(false), Boolean.valueOf(this._multiLine), arrayOfBoolean)).booleanValue();
        break label839;
        this._drawEllipsis = ((Boolean)_testAndSet(Boolean.valueOf(false), Boolean.valueOf(this._drawEllipsis), arrayOfBoolean)).booleanValue();
        break label883;
        if (this._textData == localObject)
          break label930;
        this._textData = localObject;
        if ((this._textData instanceof String))
        {
          this._spannedText = Html.fromHtml((String)this._textData, obtainImageGetter(), null);
          this._textLayout = null;
          break label930;
        }
        this._spannedText = null;
        this._textLayout = null;
        break label930;
        label1504: this._spannedText = null;
        this._textLayout = null;
        break label930;
        label1517: XulAttr localXulAttr3 = this._view.getAttr(XulPropNameCache.TagId.MARQUEE);
        if (localXulAttr3 == null)
        {
          _stopMarqueeAnimation();
        }
        else
        {
          XulPropParser.xulParsedAttr_Text_Marquee localxulParsedAttr_Text_Marquee = (XulPropParser.xulParsedAttr_Text_Marquee)localXulAttr3.getParsedValue();
          if (localxulParsedAttr_Text_Marquee.speed <= 0)
          {
            _stopMarqueeAnimation();
          }
          else
          {
            if (this._marqueeAnimation == null)
            {
              TextMarqueeAnimation localTextMarqueeAnimation = new TextMarqueeAnimation(this);
              this._marqueeAnimation = localTextMarqueeAnimation;
            }
            this._marqueeAnimation.prepareMarqueeAnimation(localxulParsedAttr_Text_Marquee);
            addAnimation(this._marqueeAnimation);
          }
        }
      }
    }
  }

  private void updateTextLayout(int paramInt)
  {
    if (this._textLayout != null)
      return;
    if (this._spannedText == null)
    {
      this._textWidth = 0;
      this._textHeight = 0;
      return;
    }
    if (!this._multiLine);
    float f2;
    for (int i = 2147483647; ; i = (int)(paramInt * this._superResample))
    {
      TextPaint localTextPaint = new TextPaint(_getTextPaint(this._superResample));
      Spanned localSpanned = this._spannedText;
      Layout.Alignment localAlignment = Layout.Alignment.ALIGN_NORMAL;
      float f1 = this._lineHeightScalar;
      this._textLayout = new StaticLayout(localSpanned, localTextPaint, i, localAlignment, f1, 0.0F, false);
      f2 = 0.0F;
      for (int j = 0; j < this._textLayout.getLineCount(); j++)
      {
        float f3 = this._textLayout.getLineWidth(j);
        if (f2 < f3)
          f2 = f3;
      }
    }
    this._textWidth = XulUtils.roundToInt(XulUtils.ceilToInt(f2) / this._superResample);
    this._textHeight = XulUtils.roundToInt(this._textLayout.getHeight() / this._superResample);
  }

  public XulWorker.DrawableItem collectPendingImageItem()
  {
    XulWorker.DrawableItem localDrawableItem = super.collectPendingImageItem();
    if (localDrawableItem != null)
      return localDrawableItem;
    if ((this._imageCache == null) || (!this._imageCacheChanged))
      return null;
    Iterator localIterator = this._imageCache.entrySet().iterator();
    while (localIterator.hasNext())
    {
      SpannedLabelImage localSpannedLabelImage = (SpannedLabelImage)((Map.Entry)localIterator.next()).getValue();
      if ((!localSpannedLabelImage._isLoading) && (localSpannedLabelImage._drawable == null))
      {
        localSpannedLabelImage._isLoading = true;
        localSpannedLabelImage.url = localSpannedLabelImage._source;
        localSpannedLabelImage.width = 0;
        localSpannedLabelImage.height = 0;
        return localSpannedLabelImage;
      }
    }
    this._imageCacheChanged = false;
    return null;
  }

  protected XulLayoutHelper.ILayoutElement createElement()
  {
    return new LayoutElement();
  }

  public void draw(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    if (_isInvisible())
      return;
    this._drawingDC = paramXulDC;
    super.draw(paramXulDC, paramRect, paramInt1, paramInt2);
    drawText(paramXulDC, paramInt1, paramInt2);
    this._drawingDC = null;
    if (this._scrollBar != null)
    {
      RectF localRectF = getAnimRect();
      this._scrollBar.draw(paramXulDC, paramRect, XulUtils.roundToInt(paramInt1 + localRectF.left), XulUtils.roundToInt(paramInt2 + localRectF.top));
    }
    int k;
    label155: int i;
    int j;
    if (this._scrollTargetY == this._scrollY)
    {
      (XulUtils.calRectWidth(this._rect) - this._padding.left - this._padding.right);
      k = XulUtils.calRectHeight(this._rect) - this._padding.top - this._padding.bottom;
      if (this._textHeight <= k)
        this._scrollTargetY = 0;
    }
    else
    {
      if (this._scrollTargetY == this._scrollY)
        break label234;
      i = this._scrollY - this._scrollTargetY;
      if (Math.abs(i) <= 4)
        break label236;
      j = i / 2;
    }
    while (true)
    {
      this._scrollY -= j;
      markDirtyView();
      return;
      if (this._scrollY + this._textHeight >= k)
        break label155;
      this._scrollTargetY = (k - this._textHeight);
      break label155;
      label234: break;
      label236: if (i > 0)
        j = Math.min(i, 2);
      else
        j = Math.max(i, -2);
    }
  }

  public int getDefaultFocusMode()
  {
    return 4;
  }

  public boolean onKeyEvent(KeyEvent paramKeyEvent)
  {
    int i = XulUtils.calRectHeight(this._rect) - this._padding.top - this._padding.bottom;
    if ((this._textHeight > i) && (paramKeyEvent.getAction() == 0))
      switch (paramKeyEvent.getKeyCode())
      {
      default:
      case 19:
      case 20:
      }
    int j;
    do
    {
      do
        return false;
      while (this._scrollTargetY >= 0);
      this._scrollTargetY += 3 * this._textLineHeight;
      this._scrollTargetY -= this._scrollTargetY % this._textLineHeight;
      if (this._scrollTargetY > 0)
        this._scrollTargetY = 0;
      if (this._scrollBar != null)
        this._scrollBar.reset();
      markDirtyView();
      return true;
      j = i - this._textHeight;
    }
    while (this._scrollTargetY <= j);
    this._scrollTargetY -= 3 * this._textLineHeight;
    if (this._scrollTargetY < j)
      this._scrollTargetY = j;
    if (this._scrollBar != null)
      this._scrollBar.reset();
    markDirtyView();
    return true;
  }

  public void syncData()
  {
    if (!_isDataChanged())
      return;
    super.syncData();
    XulAttr localXulAttr = this._view.getAttr("scrollbar");
    if ((localXulAttr == null) || (localXulAttr.getValue() == null))
    {
      this._scrollBar = null;
      return;
    }
    this._scrollBar = SimpleScrollBar.buildScrollBar(this._scrollBar, localXulAttr.getStringValue(), _getScrollBarHelper(), this);
  }

  protected class LayoutElement extends XulViewRender.LayoutElement
  {
    protected LayoutElement()
    {
      super();
    }

    private void refreshTextLayout()
    {
      if ((XulSpannedLabelRender.this._spannedText != null) && (XulSpannedLabelRender.this._textLayout == null))
        XulSpannedLabelRender.this.updateTextLayout(XulUtils.calRectWidth(XulSpannedLabelRender.this._rect) - XulSpannedLabelRender.this._padding.left - XulSpannedLabelRender.this._padding.right);
    }

    public int doFinal()
    {
      super.doFinal();
      if (XulSpannedLabelRender.this._isInvisible())
        return 0;
      refreshTextLayout();
      return 0;
    }

    public int getContentHeight()
    {
      refreshTextLayout();
      return XulSpannedLabelRender.this._textHeight;
    }

    public int getContentWidth()
    {
      return XulSpannedLabelRender.this._textWidth;
    }

    public int prepare()
    {
      super.prepare();
      if (XulSpannedLabelRender.this._isInvisible())
        return 0;
      XulSpannedLabelRender.this.syncTextInfo();
      switch (getWidth())
      {
      case 2147483644:
      default:
        XulSpannedLabelRender.this.updateTextLayout(XulUtils.calRectWidth(XulSpannedLabelRender.this._rect) - XulSpannedLabelRender.this._padding.left - XulSpannedLabelRender.this._padding.right);
        return 0;
      case 2147483645:
      case 2147483646:
      }
      XulSpannedLabelRender.this.updateTextLayout(2147483647);
      return 0;
    }
  }

  private class SpannedLabelImage extends XulRenderDrawableItem
  {
    Drawable _drawable;
    volatile boolean _isLoading = false;
    String _source;
    XulDrawable _xulDrawable;
    XulDrawer _xulDrawer;

    private SpannedLabelImage()
    {
    }

    public void onImageReady(XulDrawable paramXulDrawable)
    {
      this._xulDrawable = paramXulDrawable;
      this._isLoading = false;
      if (this._xulDrawable == null)
        return;
      XulSpannedLabelRender.access$202(XulSpannedLabelRender.this, "");
      this._drawable = new Drawable()
      {
        public void draw(Canvas paramAnonymousCanvas)
        {
          if (XulSpannedLabelRender.this._drawingDC == null)
            return;
          XulSpannedLabelRender.SpannedLabelImage.this._xulDrawer.draw(XulSpannedLabelRender.this._drawingDC, XulSpannedLabelRender.SpannedLabelImage.this._xulDrawable, getBounds(), XulRenderContext.getDefPicPaint());
        }

        public int getOpacity()
        {
          return 0;
        }

        public void setAlpha(int paramAnonymousInt)
        {
        }

        public void setColorFilter(ColorFilter paramAnonymousColorFilter)
        {
        }
      };
      this._xulDrawer = XulDrawer.create(this._xulDrawable, XulSpannedLabelRender.this._view, XulSpannedLabelRender.this.getRenderContext());
      this._drawable.setBounds(0, 0, paramXulDrawable.getWidth(), paramXulDrawable.getHeight());
      XulSpannedLabelRender.this.setUpdateLayout();
    }
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
      XulSpannedLabelRender localXulSpannedLabelRender = XulSpannedLabelRender.this;
      localXulSpannedLabelRender._marqueeDirection *= paramxulParsedAttr_Text_Marquee.direction;
      this._running = true;
      if ((paramxulParsedAttr_Text_Marquee.speed != this._marqueeSpeed) || (XulSpannedLabelRender.this._marqueePosition < 0.0F))
      {
        long l = XulSpannedLabelRender.this.animationTimestamp();
        this._beginTime = l;
        this._intervalBeginTime = l;
        this._marqueeSpeed = paramxulParsedAttr_Text_Marquee.speed;
        if (this._marqueeSpeed == 0)
          XulSpannedLabelRender.this._marqueePosition = -1.0F;
      }
      else
      {
        return;
      }
      XulSpannedLabelRender.this._marqueePosition = 0.0F;
    }

    public void stop()
    {
      this._running = false;
    }

    public boolean updateAnimation(long paramLong)
    {
      if ((this._marqueeSpeed <= 0) || (!this._running))
      {
        XulSpannedLabelRender.this._marqueePosition = -1.0F;
        return false;
      }
      long l1 = paramLong - this._beginTime;
      if (l1 <= this._marqueeDelay)
        return true;
      if (XulSpannedLabelRender.this._multiLine)
        return false;
      if (XulSpannedLabelRender.this._isInvisible())
        return false;
      long l4;
      long l5;
      if (this._intervalBeginTime == this._beginTime)
      {
        long l3 = l1 - this._marqueeDelay;
        XulSpannedLabelRender.this._marqueePosition = (XulSpannedLabelRender.this._textHeight / 1.1F * (float)l3 / this._marqueeSpeed);
        l4 = XulSpannedLabelRender.this._textWidth;
        if (this._marqueeSpace < 0)
          break label239;
        l5 = l4 + this._marqueeSpace;
        if (XulSpannedLabelRender.this._marqueePosition >= (float)l5)
        {
          XulSpannedLabelRender.this._marqueePosition = -1.0F;
          this._intervalBeginTime = paramLong;
        }
      }
      if (XulSpannedLabelRender.this._marqueePosition < 0.0F)
      {
        long l2 = paramLong - (this._marqueeDelay - this._marqueeInterval);
        this._beginTime = l2;
        this._intervalBeginTime = l2;
      }
      for (XulArea localXulArea = XulSpannedLabelRender.this._view.getParent(); ; localXulArea = localXulArea.getParent())
      {
        if ((localXulArea instanceof XulLayout))
        {
          XulSpannedLabelRender.this.markDirtyView();
          return true;
          label239: int i = XulSpannedLabelRender.this.getWidth();
          if (XulSpannedLabelRender.this._padding != null)
            i -= XulSpannedLabelRender.this._padding.left + XulSpannedLabelRender.this._padding.right;
          l5 = l4 + -(i * this._marqueeSpace / 100);
          break;
        }
        if (localXulArea == null)
          return false;
        XulViewRender localXulViewRender = localXulArea.getRender();
        if (localXulViewRender == null)
          return false;
        if (localXulViewRender._isInvisible())
          return false;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.XulSpannedLabelRender
 * JD-Core Version:    0.6.2
 */