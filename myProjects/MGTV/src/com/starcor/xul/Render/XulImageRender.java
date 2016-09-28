package com.starcor.xul.Render;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import com.starcor.xul.Graphics.BitmapTools;
import com.starcor.xul.Graphics.XulBitmapDrawable;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Graphics.XulDrawable;
import com.starcor.xul.Prop.XulAttr;
import com.starcor.xul.Prop.XulPropNameCache;
import com.starcor.xul.Prop.XulPropNameCache.TagId;
import com.starcor.xul.Prop.XulStyle;
import com.starcor.xul.Render.Drawer.IXulAnimation;
import com.starcor.xul.Render.Drawer.XulAnimationDrawer;
import com.starcor.xul.Render.Drawer.XulDrawer;
import com.starcor.xul.Utils.XulLayoutHelper.ILayoutElement;
import com.starcor.xul.Utils.XulPropParser.xulParsedAttr_Img_Align;
import com.starcor.xul.Utils.XulPropParser.xulParsedAttr_Img_AutoHide;
import com.starcor.xul.Utils.XulPropParser.xulParsedAttr_Img_FadeIn;
import com.starcor.xul.Utils.XulPropParser.xulParsedAttr_Img_RoundRect;
import com.starcor.xul.Utils.XulPropParser.xulParsedAttr_Img_Shadow;
import com.starcor.xul.Utils.XulPropParser.xulParsedAttr_Img_SizeVal;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_Integer;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_PaddingMargin;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_booleanValue;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_DoNotMatchText;
import com.starcor.xul.Utils.XulRenderDrawableItem;
import com.starcor.xul.Utils.XulSimpleArray;
import com.starcor.xul.XulItem;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker;
import com.starcor.xul.XulWorker.DrawableItem;
import java.util.ArrayList;

public class XulImageRender extends XulLabelRender
{
  private static final String TAG = XulImageRender.class.getSimpleName();
  public static final int _DefImgLayers = 4;
  public static final int _MaxImgLayers = 8;
  public static final int[] _imgXAlign;
  public static final int[] _imgXAutoHide;
  public static final int[] _imgXFadeIn;
  public static final int[] _imgXHeight;
  public static final int[] _imgXMode;
  public static final int[] _imgXName = new int[8];
  public static final int[] _imgXPadding;
  public static final int[] _imgXReuse;
  public static final int[] _imgXRoundRect;
  public static final int[] _imgXShadow;
  public static final int[] _imgXVisible = new int[8];
  public static final int[] _imgXWidth;
  private boolean _doNotMatchTextHeight = false;
  private boolean _doNotMatchTextWidth = false;
  private boolean _imageChanged = true;
  private DrawableArray _images = new DrawableArray(8);
  protected XulItem _item;
  private int _layerCount = 4;
  private boolean _layoutOnImageChanged = true;

  static
  {
    _imgXMode = new int[8];
    _imgXWidth = new int[8];
    _imgXHeight = new int[8];
    _imgXShadow = new int[8];
    _imgXAlign = new int[8];
    _imgXRoundRect = new int[8];
    _imgXAutoHide = new int[8];
    _imgXPadding = new int[8];
    _imgXFadeIn = new int[8];
    _imgXReuse = new int[8];
    for (int i = 0; i < 8; i++)
    {
      int[] arrayOfInt1 = _imgXName;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(i);
      arrayOfInt1[i] = XulPropNameCache.name2Id(String.format("img.%d", arrayOfObject1));
      int[] arrayOfInt2 = _imgXVisible;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(i);
      arrayOfInt2[i] = XulPropNameCache.name2Id(String.format("img.%d.visible", arrayOfObject2));
      int[] arrayOfInt3 = _imgXMode;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Integer.valueOf(i);
      arrayOfInt3[i] = XulPropNameCache.name2Id(String.format("img.%d.mode", arrayOfObject3));
      int[] arrayOfInt4 = _imgXWidth;
      Object[] arrayOfObject4 = new Object[1];
      arrayOfObject4[0] = Integer.valueOf(i);
      arrayOfInt4[i] = XulPropNameCache.name2Id(String.format("img.%d.width", arrayOfObject4));
      int[] arrayOfInt5 = _imgXHeight;
      Object[] arrayOfObject5 = new Object[1];
      arrayOfObject5[0] = Integer.valueOf(i);
      arrayOfInt5[i] = XulPropNameCache.name2Id(String.format("img.%d.height", arrayOfObject5));
      int[] arrayOfInt6 = _imgXRoundRect;
      Object[] arrayOfObject6 = new Object[1];
      arrayOfObject6[0] = Integer.valueOf(i);
      arrayOfInt6[i] = XulPropNameCache.name2Id(String.format("img.%d.round-rect", arrayOfObject6));
      int[] arrayOfInt7 = _imgXShadow;
      Object[] arrayOfObject7 = new Object[1];
      arrayOfObject7[0] = Integer.valueOf(i);
      arrayOfInt7[i] = XulPropNameCache.name2Id(String.format("img.%d.shadow", arrayOfObject7));
      int[] arrayOfInt8 = _imgXAlign;
      Object[] arrayOfObject8 = new Object[1];
      arrayOfObject8[0] = Integer.valueOf(i);
      arrayOfInt8[i] = XulPropNameCache.name2Id(String.format("img.%d.align", arrayOfObject8));
      int[] arrayOfInt9 = _imgXAutoHide;
      Object[] arrayOfObject9 = new Object[1];
      arrayOfObject9[0] = Integer.valueOf(i);
      arrayOfInt9[i] = XulPropNameCache.name2Id(String.format("img.%d.auto-hide", arrayOfObject9));
      int[] arrayOfInt10 = _imgXPadding;
      Object[] arrayOfObject10 = new Object[1];
      arrayOfObject10[0] = Integer.valueOf(i);
      arrayOfInt10[i] = XulPropNameCache.name2Id(String.format("img.%d.padding", arrayOfObject10));
      int[] arrayOfInt11 = _imgXFadeIn;
      Object[] arrayOfObject11 = new Object[1];
      arrayOfObject11[0] = Integer.valueOf(i);
      arrayOfInt11[i] = XulPropNameCache.name2Id(String.format("img.%d.fade-in", arrayOfObject11));
      int[] arrayOfInt12 = _imgXReuse;
      Object[] arrayOfObject12 = new Object[1];
      arrayOfObject12[0] = Integer.valueOf(i);
      arrayOfInt12[i] = XulPropNameCache.name2Id(String.format("img.%d.reuse", arrayOfObject12));
    }
  }

  public XulImageRender(XulRenderContext paramXulRenderContext, XulItem paramXulItem)
  {
    super(paramXulRenderContext, paramXulItem);
    this._item = paramXulItem;
  }

  public static void register()
  {
    XulRenderFactory.registerBuilder("item", "image", new XulRenderFactory.RenderBuilder()
    {
      static
      {
        if (!XulImageRender.class.desiredAssertionStatus());
        for (boolean bool = true; ; bool = false)
        {
          $assertionsDisabled = bool;
          return;
        }
      }

      protected XulViewRender createRender(XulRenderContext paramAnonymousXulRenderContext, XulView paramAnonymousXulView)
      {
        assert ((paramAnonymousXulView instanceof XulItem));
        return new XulImageRender(paramAnonymousXulRenderContext, (XulItem)paramAnonymousXulView);
      }
    });
  }

  private void syncDoNotMatchTextStyle()
  {
    if (!_isViewChanged())
      return;
    XulStyle localXulStyle = this._view.getStyle("do-not-match-text");
    if (localXulStyle == null)
    {
      this._doNotMatchTextWidth = false;
      this._doNotMatchTextHeight = false;
      return;
    }
    XulPropParser.xulParsedStyle_DoNotMatchText localxulParsedStyle_DoNotMatchText = (XulPropParser.xulParsedStyle_DoNotMatchText)localXulStyle.getParsedValue();
    this._doNotMatchTextWidth = localxulParsedStyle_DoNotMatchText.doNotMatchWidth;
    this._doNotMatchTextHeight = localxulParsedStyle_DoNotMatchText.doNotMatchHeight;
  }

  public void cleanImageItems()
  {
    int i = 0;
    if (i < 8)
    {
      DrawableInfo localDrawableInfo = (DrawableInfo)this._images.get(i);
      if (localDrawableInfo == null);
      while (true)
      {
        i++;
        break;
        if (localDrawableInfo._reuse)
        {
          XulDrawable localXulDrawable = localDrawableInfo._bmp;
          if ((localXulDrawable != null) && ((localXulDrawable instanceof XulBitmapDrawable)))
          {
            BitmapTools.recycleBitmap(XulBitmapDrawable.detachBitmap((XulBitmapDrawable)localXulDrawable));
            localXulDrawable.recycle();
          }
        }
        localDrawableInfo._bmp = null;
        localDrawableInfo._fadeInBkg = null;
        localDrawableInfo._drawer = null;
        localDrawableInfo._lastLoadFailedTime = 0L;
        localDrawableInfo._loadFailedCounter = 0;
        if (localDrawableInfo._fadeEffect != null)
          localDrawableInfo._fadeEffect.clear();
        this._imageChanged = true;
      }
    }
    super.cleanImageItems();
  }

  public XulWorker.DrawableItem collectPendingImageItem()
  {
    boolean bool1 = true;
    if (_isDataChanged())
      return super.collectPendingImageItem();
    if (this._imageChanged)
    {
      long l = XulUtils.timestamp();
      int i = 1;
      int j = 0;
      if (j < this._layerCount)
      {
        DrawableInfo localDrawableInfo = (DrawableInfo)this._images.get(j);
        if (localDrawableInfo == null);
        int k;
        Rect localRect;
        do
        {
          boolean bool2;
          do
          {
            do
            {
              j++;
              break;
            }
            while ((localDrawableInfo._isLoading) || ((localDrawableInfo._bmp != null) && (!localDrawableInfo._bmp.isRecycled())) || (TextUtils.isEmpty(localDrawableInfo._url)));
            if (localDrawableInfo._loadFailedCounter >= 3)
              break label369;
            k = 5000;
            bool2 = l - localDrawableInfo._lastLoadFailedTime < k;
            i = 0;
          }
          while (bool2);
          if (!localDrawableInfo._isStretch)
            break label236;
          localRect = this._rect;
          i = 0;
        }
        while (localRect == null);
        localDrawableInfo.target_width = (XulUtils.calRectWidth(this._rect) - (localDrawableInfo._paddingLeft + localDrawableInfo._paddingRight));
        if (localDrawableInfo.target_width < 0)
          localDrawableInfo.target_width = 0;
        localDrawableInfo.target_height = (XulUtils.calRectHeight(this._rect) - (localDrawableInfo._paddingTop + localDrawableInfo._paddingBottom));
        if (localDrawableInfo.target_height < 0)
          localDrawableInfo.target_height = 0;
        label236: if (localDrawableInfo._width <= 2147483547)
        {
          localDrawableInfo.width = localDrawableInfo._width;
          if (localDrawableInfo._height > 2147483547)
            break label386;
        }
        label257: label386: for (localDrawableInfo.height = localDrawableInfo._height; ; localDrawableInfo.height = 0)
        {
          localDrawableInfo.setRoundRect(localDrawableInfo._roundRadius);
          localDrawableInfo.scalarX = getRenderContext().getXScalar();
          localDrawableInfo.scalarY = getRenderContext().getYScalar();
          localDrawableInfo.shadowSize = localDrawableInfo._shadowSize;
          localDrawableInfo.shadowColor = localDrawableInfo._shadowColor;
          localDrawableInfo.reusable = localDrawableInfo._reuse;
          localDrawableInfo._isLoading = bool1;
          localDrawableInfo.url = localDrawableInfo._url;
          localDrawableInfo._loadBeginTime = XulUtils.timestamp();
          return localDrawableInfo;
          label369: k = 1800000;
          break;
          localDrawableInfo.width = 0;
          break label257;
        }
      }
      if (i != 0)
        break label410;
    }
    while (true)
    {
      this._imageChanged = bool1;
      return super.collectPendingImageItem();
      label410: bool1 = false;
    }
  }

  protected XulLayoutHelper.ILayoutElement createElement()
  {
    return new LayoutElement();
  }

  public boolean doSuspendRecycle(int paramInt)
  {
    if ((paramInt <= 0) && (internalRejectTest()))
    {
      int i = 0;
      int j = 0;
      if (j < 8)
      {
        DrawableInfo localDrawableInfo = (DrawableInfo)this._images.get(j);
        if (localDrawableInfo == null);
        while (true)
        {
          j++;
          break;
          if ((localDrawableInfo._reuse) && (localDrawableInfo._bmp != null))
          {
            XulDrawable localXulDrawable = localDrawableInfo._bmp;
            if ((localXulDrawable != null) && ((localXulDrawable instanceof XulBitmapDrawable)))
            {
              BitmapTools.recycleBitmap(XulBitmapDrawable.detachBitmap((XulBitmapDrawable)localXulDrawable));
              localXulDrawable.recycle();
            }
            localDrawableInfo._bmp = null;
            localDrawableInfo._fadeInBkg = null;
            localDrawableInfo._drawer = null;
            localDrawableInfo._lastLoadFailedTime = 0L;
            localDrawableInfo._loadFailedCounter = 0;
            if (localDrawableInfo._fadeEffect != null)
              localDrawableInfo._fadeEffect.clear();
            this._imageChanged = true;
            i = 1;
          }
        }
      }
      if (i != 0)
        return true;
    }
    return super.doSuspendRecycle(paramInt);
  }

  public void draw(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    if (_isInvisible())
      return;
    drawBackground(paramXulDC, paramRect, paramInt1, paramInt2);
    drawImages(paramXulDC, paramRect, paramInt1, paramInt2);
    drawText(paramXulDC, paramRect, paramInt1, paramInt2);
    drawBorder(paramXulDC, paramRect, paramInt1, paramInt2);
  }

  protected void drawImage(XulDC paramXulDC, Paint paramPaint, DrawableInfo paramDrawableInfo, XulDrawable paramXulDrawable, XulDrawer paramXulDrawer, int paramInt1, int paramInt2)
  {
    float f1 = paramXulDrawable.getHeight();
    float f2 = paramXulDrawable.getWidth();
    float f3 = this._scalarY;
    float f4 = this._scalarX;
    float f5 = paramDrawableInfo._opacity;
    int i = XulUtils.roundToInt(f4 * paramDrawableInfo._paddingLeft);
    int j = XulUtils.roundToInt(f4 * paramDrawableInfo._paddingRight);
    int k = XulUtils.roundToInt(f3 * paramDrawableInfo._paddingTop);
    int m = XulUtils.roundToInt(f3 * paramDrawableInfo._paddingBottom);
    float f6 = paramDrawableInfo._alignX;
    float f7 = paramDrawableInfo._alignY;
    if ((f5 != 0.0F) && (f5 != 1.0F))
    {
      paramPaint = XulRenderContext.getDefAlphaPicPaint();
      int i2 = (int)(255.0F * f5);
      paramPaint.setAlpha(i2);
    }
    if (_isRTL())
    {
      int i1 = i;
      i = j;
      j = i1;
      f6 = 1.0F - f6;
    }
    if (paramDrawableInfo._isStretch)
    {
      localRectF2 = getAnimRect();
      if (paramDrawableInfo._shadowSize > 0.5D)
      {
        n = XulUtils.roundToInt(paramDrawableInfo._shadowSize);
        localRectF2.left += i - n;
        localRectF2.top += k - n;
        localRectF2.right -= j - n;
        localRectF2.bottom -= m - n;
        XulUtils.offsetRect(localRectF2, paramInt1, paramInt2);
        paramXulDrawer.draw(paramXulDC, paramXulDrawable, localRectF2, paramPaint);
      }
    }
    while ((f2 <= 0.0F) || (f1 <= 0.0F))
      while (true)
      {
        RectF localRectF2;
        int n;
        return;
        localRectF2.left += i;
        localRectF2.top += k;
        localRectF2.right -= j;
        localRectF2.bottom -= m;
      }
    RectF localRectF1 = getAnimRect();
    float f8 = paramDrawableInfo._width;
    float f9 = paramDrawableInfo._height;
    label399: float f10;
    float f11;
    if (f8 <= 0.0F)
    {
      f8 = 0.0F;
      if (f9 > 0.0F)
        break label619;
      f9 = 0.0F;
      if ((f8 != 0.0F) || (f9 != 0.0F))
        break label649;
      double d = this._ctx.getXScalar();
      f10 = XulUtils.roundToInt(this._ctx.getYScalar() * f1);
      f11 = XulUtils.roundToInt(d * f2);
    }
    while (true)
    {
      float f12 = f10 * f3;
      float f13 = f11 * f4;
      float f14 = f6 * (XulUtils.calRectWidth(localRectF1) - f13 - i - j) + i;
      float f15 = f7 * (XulUtils.calRectHeight(localRectF1) - f12 - k - m) + k;
      localRectF1.left += f14 + paramInt1;
      localRectF1.top += f15 + paramInt2;
      localRectF1.right = (f13 + localRectF1.left);
      localRectF1.bottom = (f12 + localRectF1.top);
      paramXulDrawer.draw(paramXulDC, paramXulDrawable, localRectF1, paramPaint);
      return;
      if (f8 != 2.147484E+009F)
        break;
      f8 = this._rect.width();
      f2 = f8;
      f1 = f9;
      break;
      label619: if (f9 != 2.147484E+009F)
        break label399;
      f9 = this._rect.height();
      f1 = f9;
      f2 = f8;
      break label399;
      label649: if (f9 != 0.0F)
      {
        if (f8 != 0.0F)
        {
          f10 = f9;
          f11 = f8;
        }
        else
        {
          f11 = XulUtils.roundToInt(f9 * f2 / f1);
          f10 = f9;
        }
      }
      else
      {
        f10 = XulUtils.roundToInt(f8 * f1 / f2);
        f11 = f8;
      }
    }
  }

  protected void drawImages(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    Paint localPaint1 = XulRenderContext.getDefPicPaint();
    Paint localPaint2 = XulRenderContext.getDefAlphaPicPaint();
    int i = 0;
    int j = paramInt1 + this._screenX;
    int k = paramInt2 + this._screenY;
    int m = -1 + this._layerCount;
    if (m >= 0)
    {
      DrawableInfo localDrawableInfo2 = (DrawableInfo)this._images.get(m);
      if (localDrawableInfo2 == null);
      while (true)
      {
        m--;
        break;
        if (localDrawableInfo2.prepareBitmap() == null)
        {
          if ((!localDrawableInfo2._isLoading) && (!TextUtils.isEmpty(localDrawableInfo2._url)))
            this._imageChanged = true;
        }
        else if (!localDrawableInfo2.isVisible())
        {
          localDrawableInfo2._skipDraw = true;
        }
        else if ((localDrawableInfo2._autoHide) && (i > 0))
        {
          localDrawableInfo2._skipDraw = true;
        }
        else
        {
          localDrawableInfo2._skipDraw = false;
          if ((localDrawableInfo2._isStretch) && ((localDrawableInfo2._fadeEffect == null) || (localDrawableInfo2._fadeEffect.isEmpty())))
            i++;
        }
      }
    }
    int n = 0;
    int i1 = this._layerCount;
    if (n < i1)
    {
      DrawableInfo localDrawableInfo1 = (DrawableInfo)this._images.get(n);
      if (localDrawableInfo1 == null);
      while (true)
      {
        n++;
        break;
        XulDrawable localXulDrawable = localDrawableInfo1.prepareBitmap();
        if (localXulDrawable == null)
        {
          if ((!localDrawableInfo1._isLoading) && (!TextUtils.isEmpty(localDrawableInfo1._url)))
            this._imageChanged = true;
        }
        else
        {
          XulDrawer localXulDrawer = localDrawableInfo1._drawer;
          ArrayList localArrayList = localDrawableInfo1._fadeEffect;
          if (localDrawableInfo1._skipDraw)
          {
            if (localDrawableInfo1._visibleFadeEffectDrawer == null)
            {
              if (localXulDrawer != null)
                localXulDrawer.reset();
              if (localArrayList != null)
                localArrayList.clear();
            }
          }
          else if ((localArrayList == null) || (localArrayList.isEmpty()))
          {
            drawImage(paramXulDC, localPaint1, localDrawableInfo1, localXulDrawable, localXulDrawer, j, k);
          }
          else
          {
            int i2 = 0;
            int i3 = localArrayList.size();
            while (i2 < i3)
            {
              drawImage(paramXulDC, localPaint2, localDrawableInfo1, localXulDrawable, (ImageEffectDrawer)localArrayList.get(i2), j, k);
              i2++;
            }
          }
        }
      }
    }
  }

  public int getDefaultFocusMode()
  {
    return 8;
  }

  public int getImageHeight(int paramInt)
  {
    DrawableInfo localDrawableInfo = (DrawableInfo)this._images.get(paramInt);
    if (localDrawableInfo == null)
      return -2;
    XulDrawable localXulDrawable = localDrawableInfo._bmp;
    if ((localXulDrawable == null) || (localXulDrawable.isRecycled()))
      return -1;
    return localXulDrawable.getHeight();
  }

  public float getImageOpacity(int paramInt)
  {
    DrawableInfo localDrawableInfo = (DrawableInfo)this._images.get(paramInt);
    if (localDrawableInfo == null)
      return 0.0F;
    return localDrawableInfo._opacity;
  }

  public Rect getImagePadding(int paramInt)
  {
    DrawableInfo localDrawableInfo = (DrawableInfo)this._images.get(paramInt);
    if (localDrawableInfo == null)
      return null;
    return new Rect(localDrawableInfo._paddingLeft, localDrawableInfo._paddingTop, localDrawableInfo._paddingRight, localDrawableInfo._paddingBottom);
  }

  public String getImageResolvedUrl(int paramInt)
  {
    DrawableInfo localDrawableInfo = (DrawableInfo)this._images.get(paramInt);
    if (localDrawableInfo == null)
      return null;
    return localDrawableInfo.getInternalResolvedPath();
  }

  public String getImageUrl(int paramInt)
  {
    DrawableInfo localDrawableInfo = (DrawableInfo)this._images.get(paramInt);
    if (localDrawableInfo == null)
      return null;
    return localDrawableInfo._url;
  }

  public int getImageWidth(int paramInt)
  {
    DrawableInfo localDrawableInfo = (DrawableInfo)this._images.get(paramInt);
    if (localDrawableInfo == null)
      return -2;
    XulDrawable localXulDrawable = localDrawableInfo._bmp;
    if ((localXulDrawable == null) || (localXulDrawable.isRecycled()))
      return -1;
    return localXulDrawable.getWidth();
  }

  public boolean hasImageLayer(int paramInt)
  {
    if (paramInt >= this._images.size());
    while ((DrawableInfo)this._images.get(paramInt) == null)
      return false;
    return true;
  }

  public boolean isImageLoaded(int paramInt)
  {
    DrawableInfo localDrawableInfo = (DrawableInfo)this._images.get(paramInt);
    if (localDrawableInfo == null);
    while (localDrawableInfo._bmp == null)
      return false;
    return true;
  }

  public boolean isImageVisible(int paramInt)
  {
    DrawableInfo localDrawableInfo = (DrawableInfo)this._images.get(paramInt);
    if (localDrawableInfo == null);
    while ((localDrawableInfo._bmp == null) || (!localDrawableInfo.isVisible()))
      return false;
    return true;
  }

  public void onVisibilityChanged(boolean paramBoolean, XulView paramXulView)
  {
    super.onVisibilityChanged(paramBoolean, paramXulView);
    if (!paramBoolean)
    {
      int i = 0;
      if (i < this._layerCount)
      {
        DrawableInfo localDrawableInfo = (DrawableInfo)this._images.get(i);
        if (localDrawableInfo == null);
        while (true)
        {
          i++;
          break;
          XulDrawer localXulDrawer = localDrawableInfo._drawer;
          if (localXulDrawer != null)
            localXulDrawer.reset();
        }
      }
    }
  }

  public boolean reloadImage(int paramInt)
  {
    DrawableInfo localDrawableInfo = (DrawableInfo)this._images.get(paramInt);
    if (localDrawableInfo == null)
      return false;
    if (localDrawableInfo._isLoading)
      return true;
    if (localDrawableInfo._reuse)
    {
      XulDrawable localXulDrawable = localDrawableInfo._bmp;
      if ((localXulDrawable != null) && ((localXulDrawable instanceof XulBitmapDrawable)))
      {
        BitmapTools.recycleBitmap(XulBitmapDrawable.detachBitmap((XulBitmapDrawable)localXulDrawable));
        localXulDrawable.recycle();
      }
    }
    localDrawableInfo._bmp = null;
    localDrawableInfo._loadFailedCounter = 0;
    localDrawableInfo._lastLoadFailedTime = 0L;
    localDrawableInfo._isRecycled = false;
    localDrawableInfo._isLoading = false;
    return true;
  }

  public boolean resetAnimation(int paramInt)
  {
    DrawableInfo localDrawableInfo = (DrawableInfo)this._images.get(paramInt);
    if (localDrawableInfo == null);
    XulDrawer localXulDrawer;
    do
    {
      return false;
      localXulDrawer = localDrawableInfo._drawer;
    }
    while ((localXulDrawer == null) || (!(localXulDrawer instanceof XulAnimationDrawer)));
    localXulDrawer.reset();
    markDirtyView();
    return true;
  }

  public void syncData()
  {
    if (!_isDataChanged())
      return;
    super.syncData();
    double d1 = this._ctx.getXScalar();
    double d2 = this._ctx.getYScalar();
    XulAttr localXulAttr1 = this._view.getAttr(XulPropNameCache.TagId.MAX_LAYERS);
    if (localXulAttr1 == null)
      this._layerCount = 4;
    int i;
    XulAttr localXulAttr2;
    DrawableInfo localDrawableInfo;
    while (true)
    {
      for (i = 0; i < 8; i++)
      {
        int j = this._layerCount;
        int k = i;
        localXulAttr2 = null;
        if (k < j)
          localXulAttr2 = this._view.getAttr(_imgXName[i]);
        localDrawableInfo = (DrawableInfo)this._images.get(i);
        if (localXulAttr2 != null)
          break label221;
        if (localDrawableInfo != null)
        {
          localDrawableInfo._url = null;
          localDrawableInfo._bmp = null;
          localDrawableInfo._fadeInBkg = null;
          localDrawableInfo._drawer = null;
          if (localDrawableInfo._fadeEffect != null)
            localDrawableInfo._fadeEffect.clear();
          localDrawableInfo.setVisible(false);
        }
      }
      break;
      this._layerCount = ((XulPropParser.xulParsedProp_Integer)localXulAttr1.getParsedValue()).val;
      if (this._layerCount <= 0)
        this._layerCount = 4;
      else if (this._layerCount > 8)
        this._layerCount = 8;
    }
    label221: XulAttr localXulAttr3 = this._view.getAttr(_imgXVisible[i]);
    XulAttr localXulAttr4 = this._view.getAttr(_imgXMode[i]);
    XulAttr localXulAttr5 = this._view.getAttr(_imgXWidth[i]);
    XulAttr localXulAttr6 = this._view.getAttr(_imgXHeight[i]);
    XulAttr localXulAttr7 = this._view.getAttr(_imgXRoundRect[i]);
    XulAttr localXulAttr8 = this._view.getAttr(_imgXShadow[i]);
    XulAttr localXulAttr9 = this._view.getAttr(_imgXAlign[i]);
    XulAttr localXulAttr10 = this._view.getAttr(_imgXAutoHide[i]);
    XulAttr localXulAttr11 = this._view.getAttr(_imgXPadding[i]);
    XulAttr localXulAttr12 = this._view.getAttr(_imgXFadeIn[i]);
    XulAttr localXulAttr13 = this._view.getAttr(_imgXReuse[i]);
    if (localDrawableInfo == null)
    {
      localDrawableInfo = new DrawableInfo();
      int m = i;
      localDrawableInfo._idx = m;
      String str1 = localXulAttr2.getName();
      localDrawableInfo._name = str1;
    }
    String str2 = localXulAttr2.getStringValue();
    label616: String str3;
    label677: String str4;
    label728: label745: XulPropParser.xulParsedAttr_Img_SizeVal localxulParsedAttr_Img_SizeVal2;
    label770: int i9;
    label809: label820: XulPropParser.xulParsedAttr_Img_SizeVal localxulParsedAttr_Img_SizeVal1;
    int i7;
    label859: label870: label1023: int i5;
    if (str2 != localDrawableInfo._url)
    {
      if ((!TextUtils.isEmpty(localDrawableInfo._url)) && (!localDrawableInfo._url.equals(str2)))
      {
        XulWorker.removeDrawableCache(localDrawableInfo._url);
        XulDrawable localXulDrawable = localDrawableInfo._fadeInBkg;
        if (localDrawableInfo._isLoading)
        {
          localDrawableInfo._isRecycled = true;
          ArrayList localArrayList2 = localDrawableInfo._fadeEffect;
          XulDrawer localXulDrawer = localDrawableInfo._drawer;
          localDrawableInfo = new DrawableInfo();
          int i12 = i;
          localDrawableInfo._idx = i12;
          String str5 = localXulAttr2.getName();
          localDrawableInfo._name = str5;
          localDrawableInfo._url = str2;
          localDrawableInfo._fadeEffect = localArrayList2;
          localDrawableInfo._drawer = localXulDrawer;
        }
        if (localDrawableInfo._fadeEffect != null)
          localDrawableInfo._fadeInBkg = localXulDrawable;
        localDrawableInfo._bmp = null;
        localDrawableInfo._lastLoadFailedTime = 0L;
        localDrawableInfo._loadFailedCounter = 0;
        localDrawableInfo._loadEndTime = 0L;
        localDrawableInfo._loadBeginTime = 0L;
        this._imageChanged = true;
        localDrawableInfo._url = str2;
      }
    }
    else
    {
      if (localXulAttr10 != null)
      {
        XulPropParser.xulParsedAttr_Img_AutoHide localxulParsedAttr_Img_AutoHide = (XulPropParser.xulParsedAttr_Img_AutoHide)localXulAttr10.getParsedValue();
        boolean bool = localxulParsedAttr_Img_AutoHide.enabled;
        localDrawableInfo._autoHide = bool;
        int i11 = localxulParsedAttr_Img_AutoHide.target;
        localDrawableInfo._autoHideTarget = i11;
      }
      if (localXulAttr12 != null)
        break label1161;
      localDrawableInfo._fadeEffect = null;
      str3 = "true";
      if (localXulAttr3 != null)
        str3 = localXulAttr3.getStringValue();
      str4 = "stretch";
      if (localXulAttr4 != null)
        str4 = localXulAttr4.getStringValue();
      if (!"stretch".equals(str4))
        break label1247;
      localDrawableInfo._isStretch = true;
      if (!"false".equals(str3))
        break label1276;
      localDrawableInfo.setVisible(false);
      if ((localXulAttr13 == null) || (!((XulPropParser.xulParsedProp_booleanValue)localXulAttr13.getParsedValue()).val))
        break label1322;
      localDrawableInfo._reuse = true;
      if (localXulAttr5 == null)
        break label1341;
      localxulParsedAttr_Img_SizeVal2 = (XulPropParser.xulParsedAttr_Img_SizeVal)localXulAttr5.getParsedValue();
      if (localxulParsedAttr_Img_SizeVal2.val > 2147483547)
        break label1331;
      i9 = XulUtils.roundToInt(d1 * localxulParsedAttr_Img_SizeVal2.val);
      int i10 = i9;
      localDrawableInfo._width = i10;
      if (localXulAttr6 == null)
        break label1360;
      localxulParsedAttr_Img_SizeVal1 = (XulPropParser.xulParsedAttr_Img_SizeVal)localXulAttr6.getParsedValue();
      if (localxulParsedAttr_Img_SizeVal1.val >= 2147483547)
        break label1350;
      i7 = XulUtils.roundToInt(d2 * localxulParsedAttr_Img_SizeVal1.val);
      int i8 = i7;
      localDrawableInfo._height = i8;
      if (localXulAttr7 == null)
        break label1369;
      float[] arrayOfFloat = ((XulPropParser.xulParsedAttr_Img_RoundRect)localXulAttr7.getParsedValue()).getRoundRadius(d1, d2);
      localDrawableInfo._roundRadius = arrayOfFloat;
      label897: if (localXulAttr8 == null)
        break label1378;
      XulPropParser.xulParsedAttr_Img_Shadow localxulParsedAttr_Img_Shadow = (XulPropParser.xulParsedAttr_Img_Shadow)localXulAttr8.getParsedValue();
      float f3 = (float)(d1 * localxulParsedAttr_Img_Shadow.size);
      localDrawableInfo._shadowSize = f3;
      float f4 = (float)(d1 * localxulParsedAttr_Img_Shadow.xOffset);
      localDrawableInfo._shadowX = f4;
      float f5 = (float)(d2 * localxulParsedAttr_Img_Shadow.yOffset);
      localDrawableInfo._shadowY = f5;
      int i6 = localxulParsedAttr_Img_Shadow.color;
      localDrawableInfo._shadowColor = i6;
      label980: if (localXulAttr9 == null)
        break label1387;
      XulPropParser.xulParsedAttr_Img_Align localxulParsedAttr_Img_Align = (XulPropParser.xulParsedAttr_Img_Align)localXulAttr9.getParsedValue();
      float f1 = localxulParsedAttr_Img_Align.xAlign;
      localDrawableInfo._alignX = f1;
      float f2 = localxulParsedAttr_Img_Align.yAlign;
      localDrawableInfo._alignY = f2;
      if (localXulAttr11 == null)
        break label1406;
      XulPropParser.xulParsedProp_PaddingMargin localxulParsedProp_PaddingMargin = (XulPropParser.xulParsedProp_PaddingMargin)localXulAttr11.getParsedValue();
      int i2 = XulUtils.roundToInt(d2 * localxulParsedProp_PaddingMargin.top);
      localDrawableInfo._paddingTop = i2;
      int i3 = XulUtils.roundToInt(d2 * localxulParsedProp_PaddingMargin.bottom);
      localDrawableInfo._paddingBottom = i3;
      int i4 = XulUtils.roundToInt(d1 * localxulParsedProp_PaddingMargin.left);
      localDrawableInfo._paddingLeft = i4;
      i5 = XulUtils.roundToInt(d1 * localxulParsedProp_PaddingMargin.right);
    }
    for (localDrawableInfo._paddingRight = i5; ; localDrawableInfo._paddingRight = 0)
    {
      this._images.put(i, localDrawableInfo);
      break;
      if ((TextUtils.isEmpty(str2)) || (str2.equals(localDrawableInfo._url)))
        break label616;
      this._imageChanged = true;
      break label616;
      label1161: XulPropParser.xulParsedAttr_Img_FadeIn localxulParsedAttr_Img_FadeIn = (XulPropParser.xulParsedAttr_Img_FadeIn)localXulAttr12.getParsedValue();
      int n = localxulParsedAttr_Img_FadeIn.duration;
      if (!localxulParsedAttr_Img_FadeIn.enabled)
      {
        localDrawableInfo._fadeEffect = null;
        n = 0;
      }
      int i1 = n;
      localDrawableInfo._fadeInDuration = i1;
      if (n <= 0)
      {
        localDrawableInfo._fadeEffect = null;
        break label677;
      }
      if (localDrawableInfo._fadeEffect != null)
        break label677;
      ArrayList localArrayList1 = new ArrayList();
      localDrawableInfo._fadeEffect = localArrayList1;
      break label677;
      label1247: if ("center".equals(str4))
      {
        localDrawableInfo._isStretch = false;
        break label728;
      }
      localDrawableInfo._isStretch = true;
      break label728;
      label1276: if (("true".equals(str3)) || (TextUtils.isEmpty(str3)))
      {
        localDrawableInfo.setVisible(true);
        break label745;
      }
      float f6 = XulUtils.tryParseFloat(str3, 0.0F);
      localDrawableInfo.setOpacity(f6);
      break label745;
      label1322: localDrawableInfo._reuse = false;
      break label770;
      label1331: i9 = localxulParsedAttr_Img_SizeVal2.val;
      break label809;
      label1341: localDrawableInfo._width = 0;
      break label820;
      label1350: i7 = localxulParsedAttr_Img_SizeVal1.val;
      break label859;
      label1360: localDrawableInfo._height = 0;
      break label870;
      label1369: localDrawableInfo._roundRadius = null;
      break label897;
      label1378: localDrawableInfo._shadowSize = 0.0F;
      break label980;
      label1387: localDrawableInfo._alignX = 0.5F;
      localDrawableInfo._alignY = 0.5F;
      break label1023;
      label1406: localDrawableInfo._paddingTop = 0;
      localDrawableInfo._paddingBottom = 0;
      localDrawableInfo._paddingLeft = 0;
    }
  }

  protected void syncLayoutOnImageChanged(DrawableInfo paramDrawableInfo)
  {
    if (this._layoutOnImageChanged)
      setUpdateLayout();
  }

  private static class DrawableArray extends XulSimpleArray<XulImageRender.DrawableInfo>
  {
    public DrawableArray(int paramInt)
    {
      super();
      resize(paramInt);
    }

    protected XulImageRender.DrawableInfo[] allocArrayBuf(int paramInt)
    {
      return new XulImageRender.DrawableInfo[paramInt];
    }
  }

  protected class DrawableInfo extends XulRenderDrawableItem
  {
    float _alignX = 0.5F;
    float _alignY = 0.5F;
    boolean _autoHide = false;
    int _autoHideTarget = -1;
    XulDrawable _bmp = null;
    XulDrawer _drawer;
    ArrayList<XulImageRender.ImageEffectDrawer> _fadeEffect;
    XulDrawable _fadeInBkg = null;
    int _fadeInDuration = 300;
    int _height = 0;
    int _idx;
    volatile boolean _isLoading = false;
    volatile boolean _isRecycled = false;
    boolean _isStretch = true;
    volatile long _lastLoadFailedTime = 0L;
    long _loadBeginTime = 0L;
    long _loadEndTime = 0L;
    volatile int _loadFailedCounter = 0;
    String _name;
    float _opacity = 1.0F;
    int _paddingBottom = 0;
    int _paddingLeft = 0;
    int _paddingRight = 0;
    int _paddingTop = 0;
    boolean _reuse = false;
    float[] _roundRadius;
    int _shadowColor = -16777216;
    float _shadowSize = 0.0F;
    float _shadowX = 0.0F;
    float _shadowY = 0.0F;
    boolean _skipDraw = false;
    float _targetOpacity = 1.0F;
    String _url;
    XulImageRender.OpacityTransformDrawer _visibleFadeEffectDrawer = null;
    int _width = 0;

    protected DrawableInfo()
    {
    }

    private XulImageRender.FadeEffectDrawer createFadeEffect(XulDrawer paramXulDrawer, XulDrawable paramXulDrawable, int paramInt1, int paramInt2)
    {
      return new XulImageRender.FadeEffectDrawer(XulImageRender.this, paramXulDrawer, paramXulDrawable, paramInt1, paramInt2, this._fadeInDuration)
      {
        public boolean updateAnimation(long paramAnonymousLong)
        {
          boolean bool;
          if ((XulImageRender.DrawableInfo.this._fadeEffect == null) || (XulImageRender.DrawableInfo.this._fadeEffect.isEmpty()))
            bool = false;
          do
          {
            return bool;
            bool = super.updateAnimation(paramAnonymousLong);
          }
          while (bool);
          XulImageRender.DrawableInfo.this._fadeEffect.remove(this);
          return bool;
        }
      };
    }

    private void setBitmap(XulDrawable paramXulDrawable)
    {
      if (this._bmp == paramXulDrawable)
        return;
      if (this._fadeEffect != null)
      {
        if ((this._drawer != null) && (this._fadeInBkg != null))
        {
          XulImageRender.FadeEffectDrawer localFadeEffectDrawer2 = createFadeEffect(this._drawer, this._fadeInBkg, 255, 255);
          this._fadeEffect.add(localFadeEffectDrawer2);
          XulImageRender.this.addAnimation(localFadeEffectDrawer2);
        }
        this._bmp = paramXulDrawable;
        this._fadeInBkg = paramXulDrawable;
        this._drawer = XulDrawer.create(this._bmp, XulImageRender.this._item, XulImageRender.this._ctx);
        XulImageRender.FadeEffectDrawer localFadeEffectDrawer1 = createFadeEffect(this._drawer, this._bmp, 0, 255);
        this._fadeEffect.add(localFadeEffectDrawer1);
        XulImageRender.this.addAnimation(localFadeEffectDrawer1);
        return;
      }
      this._bmp = paramXulDrawable;
      this._fadeInBkg = null;
      this._drawer = XulDrawer.create(this._bmp, XulImageRender.this._item, XulImageRender.this._ctx);
    }

    public int getHeight()
    {
      return this._height;
    }

    public int getIdx()
    {
      return this._idx;
    }

    public int getImageHeight()
    {
      return this._bmp.getHeight();
    }

    public int getImageWidth()
    {
      return this._bmp.getWidth();
    }

    public float getOpacity()
    {
      return this._opacity;
    }

    public int getPaddingBottom()
    {
      return this._paddingBottom;
    }

    public int getPaddingLeft()
    {
      return this._paddingLeft;
    }

    public int getPaddingRight()
    {
      return this._paddingRight;
    }

    public int getPaddingTop()
    {
      return this._paddingTop;
    }

    public int getWidth()
    {
      return this._width;
    }

    public boolean isImageLoaded()
    {
      return (!this._isLoading) && (this._bmp != null);
    }

    public boolean isLoading()
    {
      return this._isLoading;
    }

    public boolean isOpacity()
    {
      return this._opacity >= 1.0F;
    }

    public boolean isVisible()
    {
      return this._opacity >= 0.01F;
    }

    void notifyLoadEvent(boolean paramBoolean)
    {
      int i = (int)(this._loadEndTime - this._loadBeginTime);
      if (!paramBoolean);
      for (String str = "loadImageFailed"; ; str = "loadImageSuccess")
      {
        if (XulImageRender.this._view.getAction(str) != null)
        {
          XulView localXulView = XulImageRender.this._view;
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = Integer.valueOf(this._idx);
          arrayOfObject[1] = Integer.valueOf(i);
          XulPage.invokeActionNoPopupWithArgs(localXulView, str, arrayOfObject);
        }
        return;
      }
    }

    public void onImageReady(XulDrawable paramXulDrawable)
    {
      this._isLoading = false;
      this._loadEndTime = XulUtils.timestamp();
      if ((this._isRecycled) || (this._url == null))
        return;
      if (paramXulDrawable == null)
      {
        this._lastLoadFailedTime = XulUtils.timestamp();
        this._loadFailedCounter = (1 + this._loadFailedCounter);
        notifyLoadEvent(false);
      }
      while (true)
      {
        XulImageRender.this.markDirtyView();
        return;
        if (this._bmp != paramXulDrawable)
        {
          setBitmap(paramXulDrawable);
          this._lastLoadFailedTime = 0L;
          this._loadFailedCounter = 0;
          XulImageRender.this.syncLayoutOnImageChanged(this);
          notifyLoadEvent(true);
        }
      }
    }

    public XulDrawable prepareBitmap()
    {
      XulDrawable localXulDrawable = this._bmp;
      if (TextUtils.isEmpty(this._url));
      do
      {
        return null;
        if ((this._reuse) || ((localXulDrawable != null) && (!localXulDrawable.isRecycled())))
          break label89;
        localXulDrawable = XulWorker.loadDrawableFromCache(this._url);
        if (localXulDrawable != null)
          break;
        XulImageRender.access$002(XulImageRender.this, true);
      }
      while (this._fadeInBkg == null);
      return this._fadeInBkg;
      setBitmap(localXulDrawable);
      if (localXulDrawable.isRecycled())
        XulImageRender.access$002(XulImageRender.this, true);
      while (true)
      {
        label89: return localXulDrawable;
        XulImageRender.this.syncLayoutOnImageChanged(this);
        if (this._isLoading)
          this._loadEndTime = XulUtils.timestamp();
        notifyLoadEvent(true);
      }
    }

    public void setAlign(float paramFloat1, float paramFloat2)
    {
      this._alignX = paramFloat1;
      this._alignY = paramFloat2;
    }

    public void setAlignX(float paramFloat)
    {
      this._alignX = paramFloat;
    }

    public void setAlignY(float paramFloat)
    {
      this._alignY = paramFloat;
    }

    public void setOpacity(float paramFloat)
    {
      if ((this._opacity == this._targetOpacity) && (this._opacity == paramFloat));
      boolean bool;
      do
      {
        return;
        if ((this._fadeEffect != null) && (this._bmp != null) && (this._drawer != null))
        {
          this._targetOpacity = paramFloat;
          if (this._visibleFadeEffectDrawer == null)
          {
            this._visibleFadeEffectDrawer = new XulImageRender.OpacityTransformDrawer(XulImageRender.this, this._drawer, this._bmp, this._opacity, this._targetOpacity, this._fadeInDuration)
            {
              void endUp()
              {
                XulImageRender.DrawableInfo.this._visibleFadeEffectDrawer = null;
                if (XulImageRender.DrawableInfo.this._fadeEffect != null)
                  XulImageRender.DrawableInfo.this._fadeEffect.remove(this);
                XulImageRender.DrawableInfo.this._opacity = XulImageRender.DrawableInfo.this._targetOpacity;
              }

              void updateOpacity(float paramAnonymousFloat)
              {
                boolean bool = XulImageRender.DrawableInfo.this.isVisible();
                XulImageRender.DrawableInfo.this._opacity = paramAnonymousFloat;
                if (bool != XulImageRender.DrawableInfo.this.isVisible())
                  XulImageRender.this.syncLayoutOnImageChanged(XulImageRender.DrawableInfo.this);
              }
            };
            this._fadeEffect.add(this._visibleFadeEffectDrawer);
          }
          while (true)
          {
            XulImageRender.this.addAnimation(this._visibleFadeEffectDrawer);
            return;
            this._visibleFadeEffectDrawer.updateTargetOpacity(this._targetOpacity);
          }
        }
        bool = isVisible();
        this._targetOpacity = paramFloat;
        this._opacity = paramFloat;
      }
      while (bool == isVisible());
      XulImageRender.this.syncLayoutOnImageChanged(this);
    }

    public void setVisible(boolean paramBoolean)
    {
      if (paramBoolean);
      for (float f = 1.0F; ; f = 0.0F)
      {
        setOpacity(f);
        return;
      }
    }
  }

  static class FadeEffectDrawer extends XulImageRender.ImageEffectDrawer
  {
    long _begin;
    XulDrawable _drawable;
    XulDrawer _drawer;
    long _duration = 300L;
    int _from;
    XulViewRender _render;
    int _step;
    int _to;

    public FadeEffectDrawer(XulViewRender paramXulViewRender, XulDrawer paramXulDrawer, XulDrawable paramXulDrawable, int paramInt1, int paramInt2, int paramInt3)
    {
      this._render = paramXulViewRender;
      this._drawer = paramXulDrawer;
      this._drawable = paramXulDrawable;
      this._from = paramInt1;
      this._step = paramInt1;
      this._to = paramInt2;
      this._begin = paramXulViewRender.animationTimestamp();
      this._duration = paramInt3;
    }

    public void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, Rect paramRect, Paint paramPaint)
    {
      paramPaint.setAlpha(this._step);
      this._drawer.draw(paramXulDC, this._drawable, paramRect, paramPaint);
    }

    public void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, Rect paramRect1, Rect paramRect2, Paint paramPaint)
    {
      paramPaint.setAlpha(this._step);
      this._drawer.draw(paramXulDC, this._drawable, paramRect1, paramRect2, paramPaint);
    }

    public void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, Rect paramRect, RectF paramRectF, Paint paramPaint)
    {
      paramPaint.setAlpha(this._step);
      this._drawer.draw(paramXulDC, this._drawable, paramRect, paramRectF, paramPaint);
    }

    public void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, RectF paramRectF, Paint paramPaint)
    {
      paramPaint.setAlpha(this._step);
      this._drawer.draw(paramXulDC, this._drawable, paramRectF, paramPaint);
    }

    public boolean updateAnimation(long paramLong)
    {
      long l = paramLong - this._begin;
      boolean bool = l < this._duration;
      int i = 0;
      if (bool)
      {
        l = this._duration;
        i = 1;
      }
      this._step = ((int)((float)l / (float)this._duration * (this._to - this._from) + this._from));
      this._render.markDirtyView();
      return i == 0;
    }
  }

  static abstract class ImageEffectDrawer extends XulDrawer
    implements IXulAnimation
  {
  }

  protected class LayoutElement extends XulLabelRender.LayoutElement
  {
    int _imgHeight = -1;
    int _imgWidth = -1;

    protected LayoutElement()
    {
      super();
    }

    public int getContentHeight()
    {
      if (XulImageRender.this._doNotMatchTextHeight);
      for (int i = 0; ; i = super.getContentHeight())
      {
        if ((XulImageRender.this._layoutOnImageChanged) && (this._imgHeight > 0))
        {
          int j = XulImageRender.this._padding.top + XulImageRender.this._padding.bottom;
          i = Math.max(this._imgHeight - j, i);
        }
        return i;
      }
    }

    public int getContentWidth()
    {
      if (XulImageRender.this._doNotMatchTextWidth);
      for (int i = 0; ; i = super.getContentWidth())
      {
        if ((XulImageRender.this._layoutOnImageChanged) && (this._imgWidth > 0))
        {
          int j = XulImageRender.this._padding.left + XulImageRender.this._padding.right;
          i = Math.max(this._imgWidth - j, i);
        }
        return i;
      }
    }

    public int prepare()
    {
      if (XulImageRender.this._isVisible())
        XulImageRender.this.syncDoNotMatchTextStyle();
      super.prepare();
      syncImageSize();
      return 0;
    }

    void syncImageSize()
    {
      if (XulImageRender.this._isInvisible());
      while (!XulImageRender.this._layoutOnImageChanged)
        return;
      if ((getWidth() < 2147483547) && (getHeight() < 2147483547))
      {
        XulImageRender.access$202(XulImageRender.this, false);
        return;
      }
      double d1 = XulImageRender.this._ctx.getXScalar();
      double d2 = XulImageRender.this._ctx.getYScalar();
      int i = -1;
      int j = -1;
      int k = 0;
      if (k < XulImageRender.this._layerCount)
      {
        XulImageRender.DrawableInfo localDrawableInfo = (XulImageRender.DrawableInfo)XulImageRender.this._images.get(k);
        if ((localDrawableInfo == null) || (!localDrawableInfo.isVisible()));
        XulDrawable localXulDrawable;
        int m;
        int n;
        do
        {
          do
          {
            k++;
            break;
          }
          while ((localDrawableInfo._bmp == null) || (localDrawableInfo._bmp.isRecycled()));
          localXulDrawable = localDrawableInfo._bmp;
          m = localDrawableInfo._width;
          n = localDrawableInfo._height;
        }
        while ((m > 2147483547) || (n > 2147483547));
        int i1 = localXulDrawable.getHeight();
        int i2 = localXulDrawable.getWidth();
        if (localDrawableInfo._isStretch);
        while (true)
        {
          if ((m <= 2147483547) && (i2 > i))
            i = i2 + localDrawableInfo._paddingLeft + localDrawableInfo._paddingRight;
          if ((n > 2147483547) || (i1 <= j))
            break;
          j = i1 + localDrawableInfo._paddingTop + localDrawableInfo._paddingBottom;
          break;
          if ((i2 > 0) && (i1 > 0))
          {
            int i3 = m;
            int i4 = n;
            if (i3 <= 0)
              i3 = 0;
            if (i4 <= 0)
              i4 = 0;
            if ((i3 == 0) && (i4 == 0))
            {
              i1 = XulUtils.roundToInt(d2 * i1);
              i2 = XulUtils.roundToInt(d1 * i2);
            }
            else if (i4 != 0)
            {
              if (i3 != 0)
              {
                i1 = i4;
                i2 = i3;
              }
              else
              {
                i2 = XulUtils.roundToInt(i4 * i2 / i1);
                i1 = i4;
              }
            }
            else
            {
              i1 = XulUtils.roundToInt(i3 * i1 / i2);
              i2 = i3;
            }
          }
        }
      }
      this._imgWidth = i;
      this._imgHeight = j;
    }
  }

  static abstract class OpacityTransformDrawer extends XulImageRender.ImageEffectDrawer
  {
    long _begin;
    XulDrawable _drawable;
    XulDrawer _drawer;
    long _duration;
    float _from;
    XulViewRender _render;
    float _to;
    float _val;

    public OpacityTransformDrawer(XulViewRender paramXulViewRender, XulDrawer paramXulDrawer, XulDrawable paramXulDrawable, float paramFloat1, float paramFloat2, int paramInt)
    {
      this._render = paramXulViewRender;
      this._drawer = paramXulDrawer;
      this._drawable = paramXulDrawable;
      this._from = paramFloat1;
      this._val = paramFloat1;
      this._to = paramFloat2;
      this._begin = this._render.animationTimestamp();
      this._duration = paramInt;
    }

    public void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, Rect paramRect, Paint paramPaint)
    {
      paramPaint.setAlpha((int)(255.0F * this._val));
      this._drawer.draw(paramXulDC, this._drawable, paramRect, paramPaint);
    }

    public void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, Rect paramRect1, Rect paramRect2, Paint paramPaint)
    {
      paramPaint.setAlpha((int)(255.0F * this._val));
      this._drawer.draw(paramXulDC, this._drawable, paramRect1, paramRect2, paramPaint);
    }

    public void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, Rect paramRect, RectF paramRectF, Paint paramPaint)
    {
      paramPaint.setAlpha((int)(255.0F * this._val));
      this._drawer.draw(paramXulDC, this._drawable, paramRect, paramRectF, paramPaint);
    }

    public void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, RectF paramRectF, Paint paramPaint)
    {
      paramPaint.setAlpha((int)(255.0F * this._val));
      this._drawer.draw(paramXulDC, this._drawable, paramRectF, paramPaint);
    }

    abstract void endUp();

    public boolean updateAnimation(long paramLong)
    {
      long l = paramLong - this._begin;
      boolean bool = l < this._duration;
      int i = 0;
      if (bool)
      {
        l = this._duration;
        i = 1;
      }
      this._val = ((float)l / (float)this._duration * (this._to - this._from) + this._from);
      updateOpacity(this._val);
      this._render.markDirtyView();
      if (i != 0)
        endUp();
      return i == 0;
    }

    abstract void updateOpacity(float paramFloat);

    public void updateTargetOpacity(float paramFloat)
    {
      float f = (this._val - this._from) / (this._to - this._from);
      this._from = this._val;
      this._to = paramFloat;
      this._begin = this._render.animationTimestamp();
      this._begin = (()((float)this._begin - (1.0F - f) * (float)this._duration));
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.XulImageRender
 * JD-Core Version:    0.6.2
 */