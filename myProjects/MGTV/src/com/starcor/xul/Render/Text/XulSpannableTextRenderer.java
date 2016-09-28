package com.starcor.xul.Render.Text;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Graphics.XulDrawable;
import com.starcor.xul.Render.Drawer.XulDrawer;
import com.starcor.xul.Render.XulViewRender;
import com.starcor.xul.Utils.XulCachedHashMap;
import com.starcor.xul.Utils.XulRenderDrawableItem;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulWorker.DrawableItem;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class XulSpannableTextRenderer extends XulBasicTextRenderer
{
  private XulDC _drawingDC;
  private SpannedTextEditor _editor;
  private XulCachedHashMap<String, SpannedLabelImage> _imageCache;
  private volatile boolean _imageCacheChanged = false;
  private Html.ImageGetter _imageGetter;
  private Spanned _spannedText;
  private Layout _textLayout;

  public XulSpannableTextRenderer(XulViewRender paramXulViewRender)
  {
    super(paramXulViewRender);
  }

  private Html.ImageGetter obtainImageGetter()
  {
    if (this._imageGetter != null)
      return this._imageGetter;
    this._imageGetter = new Html.ImageGetter()
    {
      public Drawable getDrawable(String paramAnonymousString)
      {
        if (XulSpannableTextRenderer.this._imageCache == null)
          XulSpannableTextRenderer.access$002(XulSpannableTextRenderer.this, new XulCachedHashMap());
        XulSpannableTextRenderer.SpannedLabelImage localSpannedLabelImage = (XulSpannableTextRenderer.SpannedLabelImage)XulSpannableTextRenderer.this._imageCache.get(paramAnonymousString);
        if (localSpannedLabelImage == null)
        {
          localSpannedLabelImage = new XulSpannableTextRenderer.SpannedLabelImage(XulSpannableTextRenderer.this, null);
          localSpannedLabelImage._source = paramAnonymousString;
          XulSpannableTextRenderer.this._imageCache.put(paramAnonymousString, localSpannedLabelImage);
          XulSpannableTextRenderer.access$202(XulSpannableTextRenderer.this, true);
        }
        if (localSpannedLabelImage._drawable == null)
          return null;
        return localSpannedLabelImage._drawable;
      }
    };
    return this._imageGetter;
  }

  private void refreshTextLayout()
  {
    if (this._textLayout != null);
    Rect localRect1;
    do
    {
      return;
      localRect1 = this._render.getDrawingRect();
    }
    while (localRect1 == null);
    Rect localRect2 = this._render.getPadding();
    updateTextLayout(XulUtils.calRectWidth(localRect1) - localRect2.left - localRect2.right);
  }

  private void updateTextLayout(int paramInt)
  {
    if (this._textLayout != null)
      return;
    if (this._spannedText == null)
    {
      this._textWidth = 0.0F;
      this._textHeight = 0.0F;
      return;
    }
    if (!isMultiline());
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

  public void drawText(XulDC paramXulDC, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (this._textLayout == null)
      return;
    this._drawingDC = paramXulDC;
    paramXulDC.translate(paramFloat1, paramFloat2);
    float f1 = this._superResample;
    int i;
    float f2;
    float f3;
    if (Math.abs(f1 - 1.0F) > 0.01F)
    {
      i = 1;
      if (!isMultiline())
      {
        f2 = (paramFloat3 - this._textWidth) * this._fontAlignX;
        f3 = (paramFloat4 - this._textHeight) * this._fontAlignY;
        if (!this._render.isRTL())
          break label167;
        if (this._textWidth >= paramFloat3)
          f2 = paramFloat3 - this._textWidth;
      }
    }
    while (true)
    {
      paramXulDC.translate(f2, f3);
      if (i != 0)
        paramXulDC.scale(1.0F / f1, 1.0F / f1);
      this._textLayout.getPaint().setColor(this._fontColor);
      this._textLayout.draw(paramXulDC.getCanvas());
      this._drawingDC = null;
      return;
      i = 0;
      break;
      label167: if (this._textWidth >= paramFloat3)
        f2 = 0.0F;
    }
  }

  public XulTextRenderer.XulTextEditor edit()
  {
    if (this._editor == null)
      this._editor = new SpannedTextEditor();
    while (true)
    {
      return this._editor;
      this._editor.reset();
    }
  }

  public void stopAnimation()
  {
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
      XulSpannableTextRenderer.this._text = "";
      this._drawable = new Drawable()
      {
        public void draw(Canvas paramAnonymousCanvas)
        {
          if (XulSpannableTextRenderer.this._drawingDC == null)
            return;
          XulSpannableTextRenderer.SpannedLabelImage.this._xulDrawer.draw(XulSpannableTextRenderer.this._drawingDC, XulSpannableTextRenderer.SpannedLabelImage.this._xulDrawable, getBounds(), XulRenderContext.getDefPicPaint());
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
      this._xulDrawer = XulDrawer.create(this._xulDrawable, XulSpannableTextRenderer.this._render.getView(), XulSpannableTextRenderer.this._render.getRenderContext());
      this._drawable.setBounds(0, 0, paramXulDrawable.getWidth(), paramXulDrawable.getHeight());
      XulSpannableTextRenderer.this._render.setUpdateLayout();
    }
  }

  public class SpannedTextEditor extends XulBasicTextRenderer.BasicTextEditor
  {
    public SpannedTextEditor()
    {
      super();
    }

    public boolean defAutoWrap()
    {
      return true;
    }

    public boolean defMultiline()
    {
      return true;
    }

    public void finish(boolean paramBoolean)
    {
      Paint.FontMetrics localFontMetrics = XulSpannableTextRenderer.this._getTextPaint().getFontMetrics();
      XulSpannableTextRenderer.this._textLineHeight = XulUtils.ceilToInt((localFontMetrics.bottom - localFontMetrics.top) * XulSpannableTextRenderer.this._lineHeightScalar);
      if (this._testAndSetAnyChanged)
        XulSpannableTextRenderer.access$502(XulSpannableTextRenderer.this, null);
      super.finish(paramBoolean);
      XulSpannableTextRenderer.this.refreshTextLayout();
    }

    public XulTextRenderer.XulTextEditor setText(String paramString)
    {
      if (TextUtils.isEmpty(paramString))
      {
        XulSpannableTextRenderer.access$402(XulSpannableTextRenderer.this, null);
        XulSpannableTextRenderer.access$502(XulSpannableTextRenderer.this, null);
      }
      while (paramString.equals(XulSpannableTextRenderer.this._text))
        return this;
      super.setText(paramString);
      XulSpannableTextRenderer.access$402(XulSpannableTextRenderer.this, Html.fromHtml(paramString, XulSpannableTextRenderer.this.obtainImageGetter(), null));
      XulSpannableTextRenderer.access$502(XulSpannableTextRenderer.this, null);
      return this;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Text.XulSpannableTextRenderer
 * JD-Core Version:    0.6.2
 */