package com.starcor.xul.Render;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Graphics.XulDrawable;
import com.starcor.xul.Prop.XulAttr;
import com.starcor.xul.Prop.XulPropNameCache.TagId;
import com.starcor.xul.Render.Effect.FlipAnimation;
import com.starcor.xul.Render.Effect.SimpleTransformAnimation;
import com.starcor.xul.Render.Transform.ITransformer;
import com.starcor.xul.Utils.XulAreaChildrenVisibleChangeNotifier;
import com.starcor.xul.Utils.XulLayoutHelper.ILayoutElement;
import com.starcor.xul.Utils.XulPropParser.xulParsedAttr_Direction;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_FloatArray;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_booleanValue;
import com.starcor.xul.Utils.XulRenderDrawableItem;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulArea.XulAreaIterator;
import com.starcor.xul.XulArea.bottomFocusFilter;
import com.starcor.xul.XulArea.leftFocusFilter;
import com.starcor.xul.XulArea.rightFocusFilter;
import com.starcor.xul.XulArea.topFocusFilter;
import com.starcor.xul.XulItem;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulLayout.FocusDirection;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulTaskCollector;
import com.starcor.xul.XulTemplate;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker;
import com.starcor.xul.XulWorker.DrawableItem;
import java.util.ArrayList;

public class XulPageSliderAreaRender extends XulViewContainerBaseRender
{
  public static final String TAG;
  int _animationSpeed = 400;
  boolean _autoImageGC = true;
  boolean _clipChildren = true;
  boolean _clipFocus = false;
  _PageSliderChildrenCollector _contentCollector = new _PageSliderChildrenCollector(null);
  ArrayList<XulView> _contents = new ArrayList();
  int _curPage;
  private FlipAnimation _flipAnimation;
  indicatorInfo _indicator;
  boolean _isLoopMode = true;
  boolean _isPreloadEnabled = true;
  boolean _isVertical = false;
  Runnable _pageChangedNotifier;
  int _pageOffsetX;
  int _pageOffsetY;
  boolean _peekViewNextPage = false;
  SimpleTransformAnimation _sliderAnimation;
  private boolean _switchingByFocus = true;

  static
  {
    if (!XulPageSliderAreaRender.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      TAG = XulPageSliderAreaRender.class.getSimpleName();
      return;
    }
  }

  public XulPageSliderAreaRender(XulRenderContext paramXulRenderContext, XulArea paramXulArea)
  {
    super(paramXulRenderContext, paramXulArea);
    init();
  }

  private void _updatePageOffsetX(float paramFloat)
  {
    if (this._pageOffsetX == paramFloat)
      return;
    if (this._contents.size() > 1)
      XulRenderContext.suspendDrawableWorker();
    this._pageOffsetX = XulUtils.roundToInt(paramFloat);
  }

  private void _updatePageOffsetY(float paramFloat)
  {
    if (this._pageOffsetY == paramFloat)
      return;
    if (this._contents.size() > 1)
      XulRenderContext.suspendDrawableWorker();
    this._pageOffsetY = XulUtils.roundToInt(paramFloat);
  }

  private void _updateSlideAnimation()
  {
    int i = -2;
    if (!_hasAnimation())
    {
      _updatePageOffsetX(0.0F);
      _updatePageOffsetY(0.0F);
    }
    while ((this._sliderAnimation != null) && (this._sliderAnimation.isRunning()))
      return;
    if (this._aniTransformer != null)
    {
      if (this._sliderAnimation == null)
        this._sliderAnimation = new SimpleTransformAnimation(this, this._aniTransformer)
        {
          float offsetX;
          float offsetY;

          public void restoreValue()
          {
            XulPageSliderAreaRender.this._pageOffsetX = ((int)(this.offsetX * this._val));
            XulPageSliderAreaRender.this._pageOffsetY = ((int)(this.offsetY * this._val));
          }

          public void storeDest()
          {
            this._destVal = 0.0F;
          }

          public void storeSrc()
          {
            this._srcVal = 1.0F;
            this.offsetX = XulPageSliderAreaRender.this._pageOffsetX;
            this.offsetY = XulPageSliderAreaRender.this._pageOffsetY;
          }
        };
      addAnimation(this._sliderAnimation);
      this._sliderAnimation.prepareAnimation(this._aniTransformDuration);
      this._sliderAnimation.startAnimation();
      return;
    }
    float f = (float)(0.8500000238418579D * Math.sqrt(this._animationSpeed / 500.0F));
    if (f >= 0.99F)
      f = 0.99F;
    if (Math.abs(this._pageOffsetX) * (1.0F - f) < 2.0F)
      if (Math.abs(this._pageOffsetX) < 2)
      {
        _updatePageOffsetX(0.0F);
        if (Math.abs(this._pageOffsetY) * (1.0F - f) >= 2.0F)
          break label274;
        if (Math.abs(this._pageOffsetY) >= 2)
          break label246;
        _updatePageOffsetY(0.0F);
      }
    while (true)
    {
      markDirtyView();
      return;
      int k = this._pageOffsetX;
      if (this._pageOffsetX < 0);
      for (int m = i; ; m = 2)
      {
        _updatePageOffsetX(k - m);
        break;
      }
      _updatePageOffsetX((int)(f * this._pageOffsetX));
      break;
      label246: int j = this._pageOffsetY;
      if (this._pageOffsetY < 0);
      while (true)
      {
        _updatePageOffsetY(j - i);
        break;
        i = 2;
      }
      label274: _updatePageOffsetY((int)(f * this._pageOffsetY));
    }
  }

  private void cleanPageCache()
  {
    if (this._autoImageGC)
      imageGC(2);
  }

  private void clearFlipAnimation()
  {
    if (this._flipAnimation != null)
    {
      removeAnimation(this._flipAnimation);
      this._flipAnimation = null;
    }
  }

  private void collectContent()
  {
    this._contents.clear();
    this._area.eachChild(this._contentCollector);
    if (this._curPage >= this._contents.size())
    {
      this._curPage = 0;
      notifyPageChanged(this._curPage);
    }
    int i = 0;
    if (i < this._contents.size())
    {
      XulView localXulView = (XulView)this._contents.get(i);
      if (i == this._curPage)
        localXulView.setEnabled(true);
      while (true)
      {
        i++;
        break;
        localXulView.setEnabled(false);
      }
    }
  }

  private void drawFlipAnimation(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2, int paramInt3, XulView paramXulView, RectF paramRectF1, RectF paramRectF2)
  {
    this._flipAnimation.preDraw(paramXulDC, paramInt1 + this._screenX + paramRectF2.left, paramInt2 + this._screenY + paramRectF2.top, XulUtils.calRectWidth(paramRectF2), XulUtils.calRectHeight(paramRectF2));
    int i;
    XulView localXulView1;
    if ((this._pageOffsetX > 0) || (this._pageOffsetY > 0))
    {
      ArrayList localArrayList1 = this._contents;
      if (this._curPage == 0)
      {
        i = paramInt3 - 1;
        localXulView1 = (XulView)localArrayList1.get(i);
        if (this._flipAnimation.getAngle() >= 90.0F)
          break label180;
        paramXulView.draw(paramXulDC, paramRect, paramInt1, paramInt2);
      }
    }
    while (true)
    {
      paramXulDC.doPostDraw(0, this._area);
      this._flipAnimation.postDraw(paramXulDC, paramInt1 + this._screenX + paramRectF2.left, paramInt2 + this._screenY + paramRectF2.top, XulUtils.calRectWidth(paramRectF2), XulUtils.calRectHeight(paramRectF2));
      return;
      i = -1 + this._curPage;
      break;
      label180: localXulView1.draw(paramXulDC, paramRect, paramInt1, paramInt2);
      continue;
      ArrayList localArrayList2 = this._contents;
      if (1 + this._curPage == paramInt3);
      XulView localXulView2;
      for (int j = 0; ; j = 1 + this._curPage)
      {
        localXulView2 = (XulView)localArrayList2.get(j);
        if ((this._flipAnimation.getAngle() <= -90.0F) || (this._flipAnimation.getAngle() >= 90.0F))
          break label278;
        paramXulView.draw(paramXulDC, paramRect, paramInt1, paramInt2);
        break;
      }
      label278: localXulView2.draw(paramXulDC, paramRect, paramInt1, paramInt2);
    }
  }

  private void drawIndicator(XulDC paramXulDC, int paramInt1, int paramInt2)
  {
    if (this._indicator == null)
      return;
    int i = this._contents.size();
    paramXulDC.save();
    paramXulDC.translate(paramInt1 + this._screenX, paramInt2 + this._screenY);
    RectF localRectF = getAnimRect();
    if (this._isVertical)
    {
      int i6 = this._indicator.gap;
      int i7 = (int)Math.max(this._indicator.focusCx, this._indicator.normalCx);
      int i8 = (int)((i - 1) * (i6 + this._indicator.normalCy + 2.0F * this._indicator.normalPadding) + (this._indicator.focusCy + 2.0F * this._indicator.focusPadding));
      int i9 = (int)((XulUtils.calRectHeight(localRectF) - i8 - this._padding.top - this._padding.bottom) * this._indicator.yAlign + localRectF.top + this._padding.top);
      int i10 = (int)((XulUtils.calRectWidth(localRectF) - i7 - this._padding.left - this._padding.right) * this._indicator.xAlign + localRectF.left + this._padding.left);
      Paint localPaint3 = XulRenderContext.getDefSolidPaint();
      int i11 = 0;
      int i12 = this._contents.size();
      if (i11 < i12)
      {
        int i13 = this._curPage;
        indicatorStyle localindicatorStyle5;
        float f9;
        float f10;
        float f11;
        IndicatorDrawableInfo localIndicatorDrawableInfo2;
        int i14;
        if (i11 == i13)
        {
          localPaint3.setColor(this._indicator.focusColor);
          localindicatorStyle5 = this._indicator.focusStyle;
          f9 = this._indicator.focusCx;
          f10 = this._indicator.focusCy;
          f11 = this._indicator.focusPadding;
          localIndicatorDrawableInfo2 = this._indicator.focusImage;
          i14 = this._indicator.focusColor;
          label343: indicatorStyle localindicatorStyle6 = indicatorStyle.STYLE_DOT;
          if (localindicatorStyle5 != localindicatorStyle6)
            break label490;
          localPaint3.setColor(i14);
          paramXulDC.drawCircle(i10 + i7 * this._indicator.indicatorAlign, f11 + (i9 + f10 / 2.0F), f9 / 2.0F, localPaint3);
        }
        while (true)
        {
          i9 = i6 + (int)(i9 + (f10 + 2.0F * f11));
          i11++;
          break;
          localPaint3.setColor(this._indicator.normalColor);
          localindicatorStyle5 = this._indicator.normalStyle;
          f9 = this._indicator.normalCx;
          f10 = this._indicator.normalCy;
          f11 = this._indicator.normalPadding;
          localIndicatorDrawableInfo2 = this._indicator.normalImage;
          i14 = this._indicator.normalColor;
          break label343;
          label490: indicatorStyle localindicatorStyle7 = indicatorStyle.STYLE_DASH;
          if (localindicatorStyle5 == localindicatorStyle7)
          {
            float f14 = i10 + (i7 - f9) * this._indicator.indicatorAlign;
            float f15 = f11 + i9;
            float f16 = Math.min(f9 / 2.0F, f10 / 2.0F);
            localPaint3.setColor(i14);
            paramXulDC.drawRoundRect(f14, f15, f9, f10, f16, f16, localPaint3);
          }
          else
          {
            indicatorStyle localindicatorStyle8 = indicatorStyle.STYLE_IMAGE;
            if ((localindicatorStyle5 == localindicatorStyle8) && (localIndicatorDrawableInfo2 != null) && (localIndicatorDrawableInfo2._bmp != null))
            {
              float f12 = i10 + (i7 - f9) * this._indicator.indicatorAlign;
              float f13 = f11 + i9;
              XulDrawable localXulDrawable2 = localIndicatorDrawableInfo2._bmp;
              Paint localPaint4 = XulRenderContext.getDefPicPaint();
              paramXulDC.drawBitmap(localXulDrawable2, f12, f13, f9, f10, localPaint4);
            }
          }
        }
      }
    }
    else
    {
      int j = this._indicator.gap;
      int k = (int)Math.max(this._indicator.focusCy, this._indicator.normalCy);
      int m = (int)((i - 1) * (j + this._indicator.normalCx + 2.0F * this._indicator.normalPadding) + (this._indicator.focusCx + 2.0F * this._indicator.focusPadding));
      int n = (int)((XulUtils.calRectWidth(localRectF) - m - this._padding.left - this._padding.right) * this._indicator.xAlign + localRectF.left + this._padding.left);
      int i1 = (int)((XulUtils.calRectHeight(localRectF) - k - this._padding.top - this._padding.bottom) * this._indicator.yAlign + localRectF.top + this._padding.top);
      Paint localPaint1 = XulRenderContext.getDefSolidPaint();
      int i2 = 0;
      int i3 = this._contents.size();
      if (i2 < i3)
      {
        int i4 = this._curPage;
        indicatorStyle localindicatorStyle1;
        float f1;
        float f2;
        float f3;
        IndicatorDrawableInfo localIndicatorDrawableInfo1;
        int i5;
        if (i2 == i4)
        {
          localPaint1.setColor(this._indicator.focusColor);
          localindicatorStyle1 = this._indicator.focusStyle;
          f1 = this._indicator.focusCx;
          f2 = this._indicator.focusCy;
          f3 = this._indicator.focusPadding;
          localIndicatorDrawableInfo1 = this._indicator.focusImage;
          i5 = this._indicator.focusColor;
          label951: indicatorStyle localindicatorStyle2 = indicatorStyle.STYLE_DOT;
          if (localindicatorStyle1 != localindicatorStyle2)
            break label1098;
          localPaint1.setColor(i5);
          paramXulDC.drawCircle(f3 + (n + f1 / 2.0F), i1 + k * this._indicator.indicatorAlign, f1 / 2.0F, localPaint1);
        }
        while (true)
        {
          n = j + (int)(n + (f1 + 2.0F * f3));
          i2++;
          break;
          localPaint1.setColor(this._indicator.normalColor);
          localindicatorStyle1 = this._indicator.normalStyle;
          f1 = this._indicator.normalCx;
          f2 = this._indicator.normalCy;
          f3 = this._indicator.normalPadding;
          localIndicatorDrawableInfo1 = this._indicator.normalImage;
          i5 = this._indicator.normalColor;
          break label951;
          label1098: indicatorStyle localindicatorStyle3 = indicatorStyle.STYLE_DASH;
          if (localindicatorStyle1 == localindicatorStyle3)
          {
            float f6 = f3 + n;
            float f7 = i1 + (k - f2) * this._indicator.indicatorAlign;
            float f8 = Math.min(f1 / 2.0F, f2 / 2.0F);
            localPaint1.setColor(i5);
            paramXulDC.drawRoundRect(f6, f7, f1, f2, f8, f8, localPaint1);
          }
          else
          {
            indicatorStyle localindicatorStyle4 = indicatorStyle.STYLE_IMAGE;
            if ((localindicatorStyle1 == localindicatorStyle4) && (localIndicatorDrawableInfo1 != null) && (localIndicatorDrawableInfo1._bmp != null))
            {
              float f4 = f3 + n;
              float f5 = i1 + (k - f2) * this._indicator.indicatorAlign;
              XulDrawable localXulDrawable1 = localIndicatorDrawableInfo1._bmp;
              Paint localPaint2 = XulRenderContext.getDefPicPaint();
              paramXulDC.drawBitmap(localXulDrawable1, f4, f5, f1, f2, localPaint2);
            }
          }
        }
      }
    }
    paramXulDC.restore();
  }

  private void drawPage(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    super.draw(paramXulDC, paramRect, paramInt1, paramInt2);
    int i = this._contents.size();
    assert (this._curPage < i);
    RectF localRectF1 = getAnimRect();
    XulView localXulView1 = (XulView)this._contents.get(this._curPage);
    paramXulDC.save();
    XulUtils.offsetRect(localRectF1, paramInt1 + this._screenX, paramInt2 + this._screenY);
    if (this._clipChildren)
      paramXulDC.clipRect(localRectF1);
    XulUtils.offsetRect(localRectF1, -(paramInt1 + this._screenX), -(paramInt2 + this._screenY));
    RectF localRectF2 = localXulView1.getFocusRc();
    if ((i > 1) && ((this._pageOffsetX != 0) || (this._pageOffsetY != 0)) && (this._animationSpeed > 100))
      if (this._flipAnimation != null)
        drawFlipAnimation(paramXulDC, paramRect, paramInt1, paramInt2, i, localXulView1, localRectF2, localRectF1);
    while (true)
    {
      if ((this._clipChildren) && (this._clipFocus))
        paramXulDC.doPostDraw(0, this._area);
      paramXulDC.restore();
      return;
      drawSlideAnimation(paramXulDC, paramRect, paramInt1, paramInt2, i, localXulView1, localRectF2);
      continue;
      if (localXulView1.getRender().needPostDraw())
        paramXulDC.postDraw(localXulView1, paramRect, paramInt1, paramInt2, localXulView1.getRender()._zIndex);
      XulView localXulView2;
      while (true)
      {
        if ((!this._peekViewNextPage) || (i <= 1))
          break label356;
        int j = 1 + this._curPage;
        if ((j >= i) && (!this._isLoopMode))
          break;
        int k = j % i;
        localXulView2 = (XulView)this._contents.get(k);
        if (!this._isVertical)
          break label358;
        localXulView2.draw(paramXulDC, paramRect, paramInt1, XulUtils.roundToInt(paramInt2 + XulUtils.calRectHeight(localRectF2)));
        break;
        localXulView1.draw(paramXulDC, paramRect, paramInt1, paramInt2);
      }
      label356: continue;
      label358: localXulView2.draw(paramXulDC, paramRect, XulUtils.roundToInt(paramInt1 + XulUtils.calRectWidth(localRectF2)), paramInt2);
    }
  }

  private void drawSlideAnimation(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2, int paramInt3, XulView paramXulView, RectF paramRectF)
  {
    if ((this._pageOffsetX > 0) || (this._pageOffsetY > 0))
    {
      ArrayList localArrayList1 = this._contents;
      int i;
      XulView localXulView1;
      float f1;
      if (this._curPage == 0)
      {
        i = paramInt3 - 1;
        localXulView1 = (XulView)localArrayList1.get(i);
        if (this._pageOffsetX == 0)
          break label147;
        f1 = this._pageOffsetX - XulUtils.calRectWidth(paramRectF);
        label65: if (this._pageOffsetY == 0)
          break label153;
      }
      label147: label153: for (float f2 = this._pageOffsetY - XulUtils.calRectHeight(paramRectF); ; f2 = 0.0F)
      {
        localXulView1.draw(paramXulDC, paramRect, XulUtils.roundToInt(f1 + paramInt1), XulUtils.roundToInt(f2 + paramInt2));
        paramXulView.draw(paramXulDC, paramRect, paramInt1 + this._pageOffsetX, paramInt2 + this._pageOffsetY);
        _updateSlideAnimation();
        return;
        i = -1 + this._curPage;
        break;
        f1 = 0.0F;
        break label65;
      }
    }
    ArrayList localArrayList2 = this._contents;
    int j;
    label179: XulView localXulView2;
    float f3;
    if (1 + this._curPage == paramInt3)
    {
      j = 0;
      localXulView2 = (XulView)localArrayList2.get(j);
      if (this._pageOffsetX == 0)
        break label291;
      f3 = XulUtils.calRectWidth(paramRectF) + this._pageOffsetX;
      label211: if (this._pageOffsetY == 0)
        break label297;
    }
    label291: label297: for (float f4 = XulUtils.calRectHeight(paramRectF) + this._pageOffsetY; ; f4 = 0.0F)
    {
      localXulView2.draw(paramXulDC, paramRect, XulUtils.roundToInt(f3 + paramInt1), XulUtils.roundToInt(f4 + paramInt2));
      paramXulView.draw(paramXulDC, paramRect, paramInt1 + this._pageOffsetX, paramInt2 + this._pageOffsetY);
      break;
      j = 1 + this._curPage;
      break label179;
      f3 = 0.0F;
      break label211;
    }
  }

  private void init()
  {
    this._curPage = 0;
    if (this._view != null)
    {
      XulAttr localXulAttr = this._view.getAttr(XulPropNameCache.TagId.INIT_PAGE);
      if (localXulAttr != null)
        this._curPage = XulUtils.tryParseInt(localXulAttr.getStringValue(), 0);
    }
    collectContent();
    notifyPageChanged(this._curPage);
  }

  private void initFlipAnimation()
  {
    if (this._flipAnimation != null)
      return;
    this._flipAnimation = new FlipAnimation(this)
    {
      float _stepX = 0.0F;
      float _stepY = 0.0F;

      public boolean updateAnimation(long paramAnonymousLong)
      {
        int i = 2;
        if ((XulPageSliderAreaRender.this._pageOffsetX == 0) && (XulPageSliderAreaRender.this._pageOffsetY == 0))
        {
          this._stepX = 0.0F;
          this._stepY = 0.0F;
          return true;
        }
        float f1 = (float)(0.8500000238418579D * Math.sqrt(XulPageSliderAreaRender.this._animationSpeed / 500.0F));
        if (f1 >= 0.99F)
          f1 = 0.99F;
        if ((XulPageSliderAreaRender.this._pageOffsetX != 0) && (this._stepX == 0.0F))
          this._stepX = (180.0F / XulPageSliderAreaRender.this._pageOffsetX);
        if ((XulPageSliderAreaRender.this._pageOffsetY != 0) && (this._stepY == 0.0F))
          this._stepY = (180.0F / XulPageSliderAreaRender.this._pageOffsetY);
        label222: float f2;
        if (Math.abs(XulPageSliderAreaRender.this._pageOffsetX) * (1.0F - f1) < 2.0F)
          if (Math.abs(XulPageSliderAreaRender.this._pageOffsetX) < i)
          {
            XulPageSliderAreaRender.this._updatePageOffsetX(0.0F);
            if (Math.abs(XulPageSliderAreaRender.this._pageOffsetY) * (1.0F - f1) >= 2.0F)
              break label384;
            if (Math.abs(XulPageSliderAreaRender.this._pageOffsetY) >= i)
              break label343;
            XulPageSliderAreaRender.this._updatePageOffsetY(0.0F);
            f2 = this._stepX * Math.abs(XulPageSliderAreaRender.this._pageOffsetX);
            if ((f2 < -90.0F) || (f2 >= 90.0F))
              break label407;
            setAngle(f2);
          }
        while (true)
        {
          XulPageSliderAreaRender.this.markDirtyView();
          return true;
          XulPageSliderAreaRender localXulPageSliderAreaRender2 = XulPageSliderAreaRender.this;
          int k = XulPageSliderAreaRender.this._pageOffsetX;
          if (XulPageSliderAreaRender.this._pageOffsetX < 0);
          for (int m = -2; ; m = i)
          {
            localXulPageSliderAreaRender2._updatePageOffsetX(k - m);
            break;
          }
          XulPageSliderAreaRender.this._updatePageOffsetX((int)(f1 * XulPageSliderAreaRender.this._pageOffsetX));
          break;
          label343: XulPageSliderAreaRender localXulPageSliderAreaRender1 = XulPageSliderAreaRender.this;
          int j = XulPageSliderAreaRender.this._pageOffsetY;
          if (XulPageSliderAreaRender.this._pageOffsetY < 0)
            i = -2;
          localXulPageSliderAreaRender1._updatePageOffsetY(j - i);
          break label222;
          label384: XulPageSliderAreaRender.this._updatePageOffsetY((int)(f1 * XulPageSliderAreaRender.this._pageOffsetY));
          break label222;
          label407: setAngle(900.0F + f2);
        }
      }
    };
  }

  private void notifyPageChanged(int paramInt)
  {
    if (this._contents.isEmpty())
      return;
    if (this._pageChangedNotifier == null)
      this._pageChangedNotifier = new Runnable()
      {
        public void run()
        {
          XulPage.invokeActionNoPopup(XulPageSliderAreaRender.this._view, "pageChanged");
        }
      };
    getRenderContext().uiRun(this._pageChangedNotifier);
  }

  public static void register()
  {
    XulRenderFactory.registerBuilder("area", "page_slider", new XulRenderFactory.RenderBuilder()
    {
      static
      {
        if (!XulPageSliderAreaRender.class.desiredAssertionStatus());
        for (boolean bool = true; ; bool = false)
        {
          $assertionsDisabled = bool;
          return;
        }
      }

      protected XulViewRender createRender(XulRenderContext paramAnonymousXulRenderContext, XulView paramAnonymousXulView)
      {
        assert ((paramAnonymousXulView instanceof XulArea));
        return new XulPageSliderAreaRender(paramAnonymousXulRenderContext, (XulArea)paramAnonymousXulView);
      }
    });
  }

  private XulView slideDown(RectF paramRectF, XulView paramXulView)
  {
    int i = this._curPage;
    this._curPage = (1 + this._curPage);
    XulView localXulView1;
    XulView localXulView2;
    XulView localXulView3;
    if (this._curPage >= this._contents.size())
    {
      if (this._isLoopMode)
        this._curPage = 0;
    }
    else
    {
      RectF localRectF1 = new RectF(paramRectF);
      localXulView1 = (XulView)this._contents.get(i);
      localXulView2 = (XulView)this._contents.get(this._curPage);
      if (!(localXulView2 instanceof XulArea))
        break label225;
      RectF localRectF3 = localXulView2.getFocusRc();
      localRectF1.offsetTo(localRectF1.left, localRectF3.top - XulUtils.calRectHeight(localRectF1));
      _updatePageOffsetY(XulUtils.calRectHeight(localRectF3));
      XulArea.bottomFocusFilter localbottomFocusFilter = new XulArea.bottomFocusFilter(paramXulView, localRectF1);
      localXulView1.setEnabled(false);
      localXulView2.setEnabled(true);
      localXulView3 = XulArea.findSubFocusByFilter((XulArea)localXulView2, localbottomFocusFilter);
      if (localXulView3 == null)
      {
        localRectF1.left = localRectF3.left;
        localRectF1.right = localRectF3.right;
        localXulView3 = XulArea.findSubFocusByFilter((XulArea)localXulView2, localbottomFocusFilter);
      }
    }
    while (true)
    {
      cleanPageCache();
      notifyPageChanged(this._curPage);
      return localXulView3;
      this._curPage = (-1 + this._curPage);
      return null;
      label225: RectF localRectF2 = localXulView2.getFocusRc();
      localXulView3 = localXulView2;
      localXulView1.setEnabled(false);
      localXulView2.setEnabled(true);
      _updatePageOffsetY(XulUtils.calRectHeight(localRectF2));
    }
  }

  private XulView slideLeft(RectF paramRectF, XulView paramXulView)
  {
    int i = this._curPage;
    this._curPage = (-1 + this._curPage);
    XulView localXulView1;
    XulView localXulView2;
    XulView localXulView3;
    if (this._curPage < 0)
    {
      if (this._isLoopMode)
        this._curPage += this._contents.size();
    }
    else
    {
      RectF localRectF1 = new RectF(paramRectF);
      localXulView1 = (XulView)this._contents.get(i);
      localXulView2 = (XulView)this._contents.get(this._curPage);
      if (!(localXulView2 instanceof XulArea))
        break label219;
      RectF localRectF3 = localXulView2.getFocusRc();
      localRectF1.offsetTo(localRectF3.right, localRectF1.top);
      _updatePageOffsetX(-XulUtils.calRectWidth(localRectF3));
      XulArea.leftFocusFilter localleftFocusFilter = new XulArea.leftFocusFilter(paramXulView, localRectF1);
      localXulView1.setEnabled(false);
      localXulView2.setEnabled(true);
      localXulView3 = XulArea.findSubFocusByFilter((XulArea)localXulView2, localleftFocusFilter);
      if (localXulView3 == null)
      {
        localRectF1.top = localRectF3.top;
        localRectF1.bottom = localRectF3.bottom;
        localXulView3 = XulArea.findSubFocusByFilter((XulArea)localXulView2, localleftFocusFilter);
      }
    }
    while (true)
    {
      cleanPageCache();
      notifyPageChanged(this._curPage);
      return localXulView3;
      this._curPage = 0;
      return null;
      label219: RectF localRectF2 = localXulView2.getFocusRc();
      localXulView3 = localXulView2;
      _updatePageOffsetX(-XulUtils.calRectWidth(localRectF2));
      localXulView1.setEnabled(false);
      localXulView2.setEnabled(true);
    }
  }

  private XulView slideRight(RectF paramRectF, XulView paramXulView)
  {
    int i = this._curPage;
    this._curPage = (1 + this._curPage);
    XulView localXulView1;
    XulView localXulView2;
    XulView localXulView3;
    if (this._curPage >= this._contents.size())
    {
      if (this._isLoopMode)
        this._curPage = 0;
    }
    else
    {
      RectF localRectF1 = new RectF(paramRectF);
      localXulView1 = (XulView)this._contents.get(i);
      localXulView2 = (XulView)this._contents.get(this._curPage);
      if (!(localXulView2 instanceof XulArea))
        break label225;
      RectF localRectF3 = localXulView2.getFocusRc();
      localRectF1.offsetTo(localRectF3.left - XulUtils.calRectWidth(localRectF1), localRectF1.top);
      _updatePageOffsetX(XulUtils.calRectWidth(localRectF3));
      XulArea.rightFocusFilter localrightFocusFilter = new XulArea.rightFocusFilter(paramXulView, localRectF1);
      localXulView1.setEnabled(false);
      localXulView2.setEnabled(true);
      localXulView3 = XulArea.findSubFocusByFilter((XulArea)localXulView2, localrightFocusFilter);
      if (localXulView3 == null)
      {
        localRectF1.top = localRectF3.top;
        localRectF1.bottom = localRectF3.bottom;
        localXulView3 = XulArea.findSubFocusByFilter((XulArea)localXulView2, localrightFocusFilter);
      }
    }
    while (true)
    {
      cleanPageCache();
      notifyPageChanged(this._curPage);
      return localXulView3;
      this._curPage = (-1 + this._curPage);
      return null;
      label225: RectF localRectF2 = localXulView2.getFocusRc();
      localXulView3 = localXulView2;
      _updatePageOffsetX(XulUtils.calRectWidth(localRectF2));
      localXulView1.setEnabled(false);
      localXulView2.setEnabled(true);
    }
  }

  private XulView slideUp(RectF paramRectF, XulView paramXulView)
  {
    int i = this._curPage;
    this._curPage = (-1 + this._curPage);
    XulView localXulView1;
    XulView localXulView2;
    XulView localXulView3;
    if (this._curPage < 0)
    {
      if (this._isLoopMode)
        this._curPage += this._contents.size();
    }
    else
    {
      RectF localRectF1 = new RectF(paramRectF);
      localXulView1 = (XulView)this._contents.get(i);
      localXulView2 = (XulView)this._contents.get(this._curPage);
      if (!(localXulView2 instanceof XulArea))
        break label219;
      RectF localRectF3 = localXulView2.getFocusRc();
      localRectF1.offsetTo(localRectF1.left, localRectF3.bottom);
      _updatePageOffsetY(-XulUtils.calRectHeight(localRectF3));
      XulArea.topFocusFilter localtopFocusFilter = new XulArea.topFocusFilter(paramXulView, localRectF1);
      localXulView1.setEnabled(false);
      localXulView2.setEnabled(true);
      localXulView3 = XulArea.findSubFocusByFilter((XulArea)localXulView2, localtopFocusFilter);
      if (localXulView3 == null)
      {
        localRectF1.left = localRectF3.left;
        localRectF1.right = localRectF3.right;
        localXulView3 = XulArea.findSubFocusByFilter((XulArea)localXulView2, localtopFocusFilter);
      }
    }
    while (true)
    {
      cleanPageCache();
      notifyPageChanged(this._curPage);
      return localXulView3;
      this._curPage = 0;
      return null;
      label219: RectF localRectF2 = localXulView2.getFocusRc();
      localXulView3 = localXulView2;
      localXulView1.setEnabled(false);
      localXulView2.setEnabled(true);
      _updatePageOffsetY(-XulUtils.calRectHeight(localRectF2));
    }
  }

  private void syncDirection()
  {
    if (!_isViewChanged());
    XulAttr localXulAttr;
    do
    {
      return;
      localXulAttr = this._area.getAttr(XulPropNameCache.TagId.DIRECTION);
    }
    while (localXulAttr == null);
    this._isVertical = ((XulPropParser.xulParsedAttr_Direction)localXulAttr.getParsedValue()).vertical;
  }

  public XulWorker.DrawableItem collectPendingImageItem()
  {
    XulWorker.DrawableItem localDrawableItem1 = super.collectPendingImageItem();
    int i = this._contents.size();
    XulWorker.DrawableItem localDrawableItem2;
    if ((localDrawableItem1 != null) || (this._contents == null) || (this._contents.isEmpty()) || (this._curPage >= i))
      localDrawableItem2 = localDrawableItem1;
    do
    {
      return localDrawableItem2;
      localDrawableItem2 = getIndicatorsImageItem();
    }
    while (localDrawableItem2 != null);
    return null;
  }

  public boolean collectPendingItems(XulTaskCollector paramXulTaskCollector)
  {
    if ((_isViewChanged()) || (this._rect == null));
    int j;
    int k;
    do
    {
      do
      {
        do
          return true;
        while ((this._contents == null) || (this._contents.isEmpty()));
        paramXulTaskCollector.addPendingItem((XulView)this._contents.get(this._curPage));
      }
      while (!this._isPreloadEnabled);
      int i = this._contents.size();
      j = (i + (-1 + this._curPage)) % i;
      k = (1 + this._curPage) % i;
      paramXulTaskCollector.addPendingItem((XulView)this._contents.get(k));
    }
    while (k == j);
    paramXulTaskCollector.addPendingItem((XulView)this._contents.get(j));
    return true;
  }

  protected XulLayoutHelper.ILayoutElement createElement()
  {
    return new LayoutContainer();
  }

  public void destroy()
  {
    clearFlipAnimation();
    super.destroy();
  }

  public void draw(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    if (_isInvisible());
    while (this._contents.isEmpty())
      return;
    drawPage(paramXulDC, paramRect, paramInt1, paramInt2);
    drawIndicator(paramXulDC, paramInt1, paramInt2);
  }

  public int getCurrentPage()
  {
    return this._curPage;
  }

  public int getDefaultFocusMode()
  {
    return 37;
  }

  XulWorker.DrawableItem getIndicatorsImageItem()
  {
    if (this._indicator == null)
      return null;
    long l = XulUtils.timestamp();
    IndicatorDrawableInfo localIndicatorDrawableInfo1 = this._indicator.normalImage;
    IndicatorDrawableInfo localIndicatorDrawableInfo2 = this._indicator.focusImage;
    if ((localIndicatorDrawableInfo1 == localIndicatorDrawableInfo2) || (localIndicatorDrawableInfo1 == null) || (localIndicatorDrawableInfo1._isLoading))
      if ((localIndicatorDrawableInfo2 != null) && (!localIndicatorDrawableInfo2._isLoading))
        break label144;
    while (true)
    {
      return null;
      if ((localIndicatorDrawableInfo1._bmp != null) || (TextUtils.isEmpty(localIndicatorDrawableInfo1._url)))
        break;
      if (localIndicatorDrawableInfo1._loadFailedCounter < 3);
      for (int j = 5000; l - localIndicatorDrawableInfo1._lastLoadFailedTime >= j; j = 1800000)
      {
        localIndicatorDrawableInfo1.width = localIndicatorDrawableInfo1._width;
        localIndicatorDrawableInfo1.height = localIndicatorDrawableInfo1._height;
        localIndicatorDrawableInfo1._isLoading = true;
        localIndicatorDrawableInfo1.url = localIndicatorDrawableInfo1._url;
        return localIndicatorDrawableInfo1;
      }
      break;
      label144: if ((localIndicatorDrawableInfo2._bmp == null) && (!TextUtils.isEmpty(localIndicatorDrawableInfo2._url)))
      {
        if (localIndicatorDrawableInfo2._loadFailedCounter < 3);
        for (int i = 5000; l - localIndicatorDrawableInfo2._lastLoadFailedTime >= i; i = 1800000)
        {
          localIndicatorDrawableInfo2.width = localIndicatorDrawableInfo2._width;
          localIndicatorDrawableInfo2.height = localIndicatorDrawableInfo2._height;
          localIndicatorDrawableInfo2._isLoading = true;
          localIndicatorDrawableInfo2.url = localIndicatorDrawableInfo2._url;
          return localIndicatorDrawableInfo2;
        }
      }
    }
  }

  public int getPageCount()
  {
    return this._contents.size();
  }

  public boolean handleScrollEvent(float paramFloat1, float paramFloat2)
  {
    float f;
    if (this._isVertical)
    {
      f = paramFloat2;
      if (f == 0.0F)
        f = paramFloat1;
      if (f != 0.0F)
        break label38;
    }
    label38: 
    do
    {
      return false;
      f = paramFloat2;
      if (f != 0.0F)
        break;
      f = paramFloat2;
      break;
      if (f <= 0.0F)
        break label64;
    }
    while ((!this._isLoopMode) && (this._curPage <= 0));
    slidePrev();
    while (true)
    {
      return true;
      label64: if ((!this._isLoopMode) && (1 + this._curPage >= this._contents.size()))
        break;
      slideNext();
    }
  }

  public boolean hitTest(int paramInt, float paramFloat1, float paramFloat2)
  {
    if (paramInt == 8)
      return super.hitTest(-1, paramFloat1, paramFloat2);
    return super.hitTest(paramInt, paramFloat1, paramFloat2);
  }

  public void imageGC(int paramInt)
  {
    if ((this._contents == null) || (this._contents.isEmpty()) || (this._curPage >= this._contents.size()))
      return;
    int i = this._contents.size();
    int j;
    int k;
    if (paramInt <= 0)
    {
      j = 0;
      k = i;
    }
    while (true)
    {
      int m = j % i;
      if (m == k)
        break;
      ((XulView)this._contents.get(m)).cleanImageItems();
      j = m + 1;
      if (j == k)
      {
        return;
        if (paramInt > i / 2)
          paramInt = i / 2;
        j = paramInt + this._curPage;
        k = this._curPage - paramInt;
        while (k < 0)
          k += i;
      }
    }
  }

  public void onVisibilityChanged(boolean paramBoolean, XulView paramXulView)
  {
    super.onVisibilityChanged(paramBoolean, paramXulView);
    XulAreaChildrenVisibleChangeNotifier localXulAreaChildrenVisibleChangeNotifier = XulAreaChildrenVisibleChangeNotifier.getNotifier();
    localXulAreaChildrenVisibleChangeNotifier.begin(paramBoolean, (XulArea)paramXulView);
    this._area.eachChild(localXulAreaChildrenVisibleChangeNotifier);
    localXulAreaChildrenVisibleChangeNotifier.end();
  }

  public XulView postFindFocus(XulLayout.FocusDirection paramFocusDirection, RectF paramRectF, XulView paramXulView)
  {
    if (!this._switchingByFocus);
    do
    {
      do
      {
        do
          return null;
        while (this._contents.size() <= 1);
        assert (this._area.findChildPos(paramXulView) >= 0);
        if (this._isVertical)
          break;
        if (paramFocusDirection == XulLayout.FocusDirection.MOVE_RIGHT)
          return slideRight(paramRectF, paramXulView);
      }
      while (paramFocusDirection != XulLayout.FocusDirection.MOVE_LEFT);
      return slideLeft(paramRectF, paramXulView);
      if (paramFocusDirection == XulLayout.FocusDirection.MOVE_UP)
        return slideUp(paramRectF, paramXulView);
    }
    while (paramFocusDirection != XulLayout.FocusDirection.MOVE_DOWN);
    return slideDown(paramRectF, paramXulView);
  }

  public boolean setCurrentPage(int paramInt)
  {
    if (this._curPage == paramInt)
      return true;
    if (paramInt >= getPageCount())
      return false;
    XulView localXulView1 = (XulView)this._contents.get(this._curPage);
    XulView localXulView2 = (XulView)this._contents.get(paramInt);
    localXulView1.setEnabled(false);
    localXulView2.setEnabled(true);
    if (localXulView1.hasFocus())
      localXulView1.getRootLayout().killFocus();
    this._curPage = paramInt;
    this._pageOffsetY = 0;
    this._pageOffsetX = 0;
    cleanPageCache();
    notifyPageChanged(this._curPage);
    return true;
  }

  public void slideDown()
  {
    if (this._contents.size() <= 1)
      return;
    int i = this._curPage;
    XulView localXulView1 = (XulView)this._contents.get(i);
    if ((localXulView1 instanceof XulArea))
    {
      XulView localXulView3 = this._area.getRootLayout().getFocus();
      if ((localXulView1 == localXulView3) || (((XulArea)localXulView1).hasChild(localXulView3)))
        slideDown(localXulView3.getFocusRc(), localXulView3);
    }
    else if (localXulView1.isFocused())
    {
      slideDown(localXulView1.getFocusRc(), localXulView1);
      return;
    }
    this._curPage = (1 + this._curPage);
    if (this._curPage >= this._contents.size())
      this._curPage = 0;
    XulView localXulView2 = (XulView)this._contents.get(this._curPage);
    _updatePageOffsetY(XulUtils.calRectHeight(localXulView2.getFocusRc()));
    localXulView1.setEnabled(false);
    localXulView2.setEnabled(true);
    cleanPageCache();
    notifyPageChanged(this._curPage);
  }

  public void slideLeft()
  {
    if (this._contents.size() <= 1)
      return;
    int i = this._curPage;
    XulView localXulView1 = (XulView)this._contents.get(i);
    if ((localXulView1 instanceof XulArea))
    {
      XulView localXulView3 = this._area.getRootLayout().getFocus();
      if ((localXulView1 == localXulView3) || (((XulArea)localXulView1).hasChild(localXulView3)))
        slideLeft(localXulView3.getFocusRc(), localXulView3);
    }
    else if (localXulView1.isFocused())
    {
      slideLeft(localXulView1.getFocusRc(), localXulView1);
      return;
    }
    this._curPage = (-1 + this._curPage);
    if (this._curPage < 0)
      this._curPage += this._contents.size();
    XulView localXulView2 = (XulView)this._contents.get(this._curPage);
    _updatePageOffsetX(-XulUtils.calRectWidth(localXulView2.getFocusRc()));
    localXulView1.setEnabled(false);
    localXulView2.setEnabled(true);
    cleanPageCache();
    notifyPageChanged(this._curPage);
  }

  public void slideNext()
  {
    if (this._isVertical)
    {
      slideDown();
      return;
    }
    slideRight();
  }

  public void slidePrev()
  {
    if (this._isVertical)
    {
      slideUp();
      return;
    }
    slideLeft();
  }

  public void slideRight()
  {
    if (this._contents.size() <= 1)
      return;
    int i = this._curPage;
    XulView localXulView1 = (XulView)this._contents.get(i);
    if ((localXulView1 instanceof XulArea))
    {
      XulView localXulView3 = this._area.getRootLayout().getFocus();
      if ((localXulView1 == localXulView3) || (((XulArea)localXulView1).hasChild(localXulView3)))
        slideRight(localXulView3.getFocusRc(), localXulView3);
    }
    else if (localXulView1.isFocused())
    {
      slideRight(localXulView1.getFocusRc(), localXulView1);
      return;
    }
    this._curPage = (1 + this._curPage);
    if (this._curPage >= this._contents.size())
      this._curPage = 0;
    XulView localXulView2 = (XulView)this._contents.get(this._curPage);
    _updatePageOffsetX(XulUtils.calRectWidth(localXulView2.getFocusRc()));
    localXulView1.setEnabled(false);
    localXulView2.setEnabled(true);
    cleanPageCache();
    notifyPageChanged(this._curPage);
  }

  public void slideUp()
  {
    if (this._contents.size() <= 1)
      return;
    int i = this._curPage;
    XulView localXulView1 = (XulView)this._contents.get(i);
    if ((localXulView1 instanceof XulArea))
    {
      XulView localXulView3 = this._area.getRootLayout().getFocus();
      if ((localXulView1 == localXulView3) || (((XulArea)localXulView1).hasChild(localXulView3)))
        slideUp(localXulView3.getFocusRc(), localXulView3);
    }
    else if (localXulView1.isFocused())
    {
      slideUp(localXulView1.getFocusRc(), localXulView1);
      return;
    }
    this._curPage = (-1 + this._curPage);
    if (this._curPage < 0)
      this._curPage += this._contents.size();
    XulView localXulView2 = (XulView)this._contents.get(this._curPage);
    _updatePageOffsetY(-XulUtils.calRectHeight(localXulView2.getFocusRc()));
    localXulView1.setEnabled(false);
    localXulView2.setEnabled(true);
    cleanPageCache();
    notifyPageChanged(this._curPage);
  }

  public void syncData()
  {
    if (!_isDataChanged())
      return;
    super.syncData();
    XulAttr localXulAttr1 = this._view.getAttr(XulPropNameCache.TagId.ANIMATION_SPEED);
    if (localXulAttr1 == null)
      localXulAttr1 = this._view.getAttr("slide_speed");
    String str1;
    label89: boolean bool2;
    label138: label144: String str28;
    label189: label240: double d1;
    label276: label312: label336: double d2;
    XulAttr localXulAttr9;
    XulAttr localXulAttr11;
    Object localObject;
    label442: String[] arrayOfString;
    int j;
    String str3;
    label542: String str24;
    label613: String str25;
    label653: String str26;
    label691: label830: String str8;
    label859: String str17;
    label909: String str18;
    label949: String str19;
    label982: String str2;
    label1049: label1067: float f1;
    if (localXulAttr1 == null)
    {
      str1 = "400";
      this._animationSpeed = XulUtils.tryParseInt(str1, 400);
      XulAttr localXulAttr2 = this._view.getAttr(XulPropNameCache.TagId.ANIMATION_TYPE);
      if (localXulAttr2 == null)
        break label1170;
      if (!"flip".equals(localXulAttr2.getStringValue()))
        break label1163;
      initFlipAnimation();
      XulAttr localXulAttr3 = this._view.getAttr(XulPropNameCache.TagId.PEEK_NEXT_PAGE);
      if (localXulAttr3 == null)
        break label1183;
      String str29 = localXulAttr3.getStringValue();
      if ((!"enabled".equals(str29)) && (!"true".equals(str29)))
        break label1177;
      bool2 = true;
      this._peekViewNextPage = bool2;
      XulAttr localXulAttr4 = this._view.getAttr(XulPropNameCache.TagId.SWITCHING_MODE);
      this._switchingByFocus = true;
      if (localXulAttr4 != null)
      {
        str28 = localXulAttr4.getStringValue();
        if (!"none".equals(str28))
          break label1191;
        this._switchingByFocus = false;
      }
      XulAttr localXulAttr5 = this._view.getAttr(XulPropNameCache.TagId.LOOP);
      if (localXulAttr5 == null)
        break label1224;
      String str27 = localXulAttr5.getStringValue();
      if ((!"true".equals(str27)) && (!"enabled".equals(str27)))
        break label1216;
      this._isLoopMode = true;
      XulAttr localXulAttr6 = this._view.getAttr(XulPropNameCache.TagId.IMAGE_GC);
      if (localXulAttr6 == null)
        break label1240;
      if (!"auto".equals(localXulAttr6.getStringValue()))
        break label1232;
      this._autoImageGC = true;
      XulAttr localXulAttr7 = this._view.getAttr(XulPropNameCache.TagId.PRELOAD_PAGE);
      if (localXulAttr7 == null)
        break label1256;
      if (!"disabled".equals(localXulAttr7.getStringValue()))
        break label1248;
      this._isPreloadEnabled = false;
      if (!"false".equals(this._view.getStyleString(XulPropNameCache.TagId.CLIP_CHILDREN)))
        break label1264;
      this._clipChildren = false;
      d1 = this._ctx.getXScalar();
      d2 = this._ctx.getYScalar();
      XulAttr localXulAttr8 = this._view.getAttr(XulPropNameCache.TagId.INDICATOR);
      if ((localXulAttr8 == null) || (!((XulPropParser.xulParsedProp_booleanValue)localXulAttr8.getParsedValue()).val))
        break label2810;
      this._indicator = new indicatorInfo();
      localXulAttr9 = this._view.getAttr(XulPropNameCache.TagId.INDICATOR_STYLE);
      XulAttr localXulAttr10 = this._view.getAttr(XulPropNameCache.TagId.INDICATOR_GAP);
      localXulAttr11 = this._view.getAttr(XulPropNameCache.TagId.INDICATOR_ALIGN);
      if (localXulAttr9 != null)
        break label1304;
      localObject = null;
      if (!TextUtils.isEmpty((CharSequence)localObject))
      {
        arrayOfString = ((String)localObject).split(",");
        int i = arrayOfString.length;
        j = 0;
        if (i > 0)
        {
          float f9 = XulUtils.tryParseFloat(arrayOfString[0], -1.0F);
          boolean bool1 = f9 < 0.0F;
          j = 0;
          if (!bool1)
          {
            this._indicator.indicatorAlign = f9;
            j = 0 + 1;
          }
        }
        int k = arrayOfString.length;
        if (j >= k)
          break label1314;
        int i46 = j + 1;
        str3 = arrayOfString[j];
        j = i46;
        if (!"dot".equals(str3))
          break label1344;
        indicatorInfo localindicatorInfo18 = this._indicator;
        indicatorInfo localindicatorInfo19 = this._indicator;
        indicatorStyle localindicatorStyle4 = indicatorStyle.STYLE_DOT;
        localindicatorInfo19.focusStyle = localindicatorStyle4;
        localindicatorInfo18.normalStyle = localindicatorStyle4;
        int i39 = arrayOfString.length;
        if (j >= i39)
          break label1320;
        int i45 = j + 1;
        str24 = arrayOfString[j];
        j = i45;
        double d17 = XulUtils.tryParseFloat(str24, 4.0F);
        int i40 = arrayOfString.length;
        if (j >= i40)
          break label1328;
        int i44 = j + 1;
        str25 = arrayOfString[j];
        j = i44;
        double d18 = XulUtils.tryParseFloat(str25, 0.0F);
        int i41 = arrayOfString.length;
        if (j >= i41)
          break label1336;
        int i43 = j + 1;
        str26 = arrayOfString[j];
        j = i43;
        int i42 = (int)XulUtils.tryParseHex(str26, -1L);
        indicatorInfo localindicatorInfo20 = this._indicator;
        indicatorInfo localindicatorInfo21 = this._indicator;
        float f6 = (float)(d17 * d1);
        localindicatorInfo21.focusCy = f6;
        localindicatorInfo20.focusCx = f6;
        indicatorInfo localindicatorInfo22 = this._indicator;
        indicatorInfo localindicatorInfo23 = this._indicator;
        float f7 = (float)(0.6000000238418579D * (d17 * d2));
        localindicatorInfo23.normalCy = f7;
        localindicatorInfo22.normalCx = f7;
        indicatorInfo localindicatorInfo24 = this._indicator;
        indicatorInfo localindicatorInfo25 = this._indicator;
        float f8 = (float)(d18 * d1);
        localindicatorInfo25.focusPadding = f8;
        localindicatorInfo24.normalPadding = f8;
        indicatorInfo localindicatorInfo26 = this._indicator;
        this._indicator.focusColor = i42;
        localindicatorInfo26.normalColor = i42;
        int i3 = arrayOfString.length;
        if (j >= i3)
          break label2144;
        int i25 = j + 1;
        str8 = arrayOfString[j];
        j = i25;
        if (!"dot".equals(str8))
          break label2174;
        this._indicator.normalStyle = indicatorStyle.STYLE_DOT;
        int i19 = arrayOfString.length;
        if (j >= i19)
          break label2150;
        int i24 = j + 1;
        str17 = arrayOfString[j];
        j = i24;
        double d12 = XulUtils.tryParseFloat(str17, 4.0F);
        int i20 = arrayOfString.length;
        if (j >= i20)
          break label2158;
        int i23 = j + 1;
        str18 = arrayOfString[j];
        j = i23;
        double d13 = XulUtils.tryParseFloat(str18, 0.0F);
        int i21 = arrayOfString.length;
        if (j >= i21)
          break label2166;
        (j + 1);
        str19 = arrayOfString[j];
        int i22 = (int)XulUtils.tryParseHex(str19, -1L);
        indicatorInfo localindicatorInfo10 = this._indicator;
        indicatorInfo localindicatorInfo11 = this._indicator;
        float f4 = (float)(d12 * d1);
        localindicatorInfo11.normalCy = f4;
        localindicatorInfo10.normalCx = f4;
        this._indicator.normalPadding = ((float)(d13 * d1));
        this._indicator.normalColor = i22;
      }
      indicatorInfo localindicatorInfo1 = this._indicator;
      if (localXulAttr10 == null)
        break label2791;
      str2 = localXulAttr10.getStringValue();
      localindicatorInfo1.gap = XulUtils.roundToInt(d1 * XulUtils.tryParseInt(str2, 12));
      if (!this._isVertical)
        break label2799;
      f1 = 0.0F;
    }
    for (float f2 = 0.5F; ; f2 = 1.0F)
    {
      if (localXulAttr11 != null)
      {
        XulPropParser.xulParsedProp_FloatArray localxulParsedProp_FloatArray = (XulPropParser.xulParsedProp_FloatArray)localXulAttr11.getParsedValue();
        f1 = localxulParsedProp_FloatArray.tryGetVal(0, f1);
        f2 = localxulParsedProp_FloatArray.tryGetVal(1, f2);
      }
      this._indicator.xAlign = f1;
      this._indicator.yAlign = f2;
      return;
      str1 = localXulAttr1.getStringValue();
      break;
      label1163: clearFlipAnimation();
      break label89;
      label1170: clearFlipAnimation();
      break label89;
      label1177: bool2 = false;
      break label138;
      label1183: this._peekViewNextPage = false;
      break label144;
      label1191: if (("focus".equals(str28)) || (!"auto".equals(str28)))
        break label189;
      break label189;
      label1216: this._isLoopMode = false;
      break label240;
      label1224: this._isLoopMode = true;
      break label240;
      label1232: this._autoImageGC = false;
      break label276;
      label1240: this._autoImageGC = true;
      break label276;
      label1248: this._isPreloadEnabled = true;
      break label312;
      label1256: this._isPreloadEnabled = true;
      break label312;
      label1264: this._clipChildren = true;
      if ("true".equals(this._area.getStyleString(XulPropNameCache.TagId.CLIP_FOCUS)))
      {
        this._clipFocus = true;
        break label336;
      }
      this._clipFocus = false;
      break label336;
      label1304: localObject = localXulAttr9.getStringValue();
      break label442;
      label1314: str3 = null;
      break label542;
      label1320: str24 = "4";
      break label613;
      label1328: str25 = "0";
      break label653;
      label1336: str26 = "FFFFFFFF";
      break label691;
      label1344: if ("dash".equals(str3))
      {
        indicatorInfo localindicatorInfo13 = this._indicator;
        indicatorInfo localindicatorInfo14 = this._indicator;
        indicatorStyle localindicatorStyle3 = indicatorStyle.STYLE_DASH;
        localindicatorInfo14.focusStyle = localindicatorStyle3;
        localindicatorInfo13.normalStyle = localindicatorStyle3;
        int i30 = arrayOfString.length;
        String str20;
        double d14;
        String str21;
        double d15;
        String str22;
        label1495: double d16;
        String str23;
        if (j < i30)
        {
          int i38 = j + 1;
          str20 = arrayOfString[j];
          j = i38;
          d14 = XulUtils.tryParseFloat(str20, 4.0F);
          int i31 = arrayOfString.length;
          if (j >= i31)
            break label1671;
          int i37 = j + 1;
          str21 = arrayOfString[j];
          j = i37;
          d15 = XulUtils.tryParseFloat(str21, 4.0F);
          int i32 = arrayOfString.length;
          if (j >= i32)
            break label1679;
          int i36 = j + 1;
          str22 = arrayOfString[j];
          j = i36;
          d16 = XulUtils.tryParseFloat(str22, 0.0F);
          int i33 = arrayOfString.length;
          if (j >= i33)
            break label1687;
          int i35 = j + 1;
          str23 = arrayOfString[j];
          j = i35;
        }
        while (true)
        {
          int i34 = (int)XulUtils.tryParseHex(str23, -1L);
          this._indicator.focusCx = ((float)(d14 * d1));
          this._indicator.focusCy = ((float)(d15 * d2));
          this._indicator.normalCx = (0.8F * (float)(d14 * d1));
          this._indicator.normalCy = (0.6F * (float)(d15 * d2));
          indicatorInfo localindicatorInfo15 = this._indicator;
          indicatorInfo localindicatorInfo16 = this._indicator;
          float f5 = (float)(d16 * d1);
          localindicatorInfo16.focusPadding = f5;
          localindicatorInfo15.normalPadding = f5;
          indicatorInfo localindicatorInfo17 = this._indicator;
          this._indicator.focusColor = i34;
          localindicatorInfo17.normalColor = i34;
          break;
          str20 = "4";
          break label1415;
          str21 = "4";
          break label1455;
          str22 = "0";
          break label1495;
          str23 = "FFFFFFFF";
        }
      }
      label1415: label1455: if (!"image".equals(str3))
        break label830;
      label1671: label1679: label1687: indicatorInfo localindicatorInfo2 = this._indicator;
      indicatorInfo localindicatorInfo3 = this._indicator;
      indicatorStyle localindicatorStyle1 = indicatorStyle.STYLE_IMAGE;
      localindicatorInfo3.focusStyle = localindicatorStyle1;
      localindicatorInfo2.normalStyle = localindicatorStyle1;
      int m = arrayOfString.length;
      String str4;
      label1766: double d3;
      String str5;
      label1806: double d4;
      String str6;
      label1846: double d5;
      String str7;
      if (j < m)
      {
        int i29 = j + 1;
        str4 = arrayOfString[j];
        j = i29;
        d3 = XulUtils.tryParseFloat(str4, 16.0F);
        int n = arrayOfString.length;
        if (j >= n)
          break label2029;
        int i28 = j + 1;
        str5 = arrayOfString[j];
        j = i28;
        d4 = XulUtils.tryParseFloat(str5, 16.0F);
        int i1 = arrayOfString.length;
        if (j >= i1)
          break label2037;
        int i27 = j + 1;
        str6 = arrayOfString[j];
        j = i27;
        d5 = XulUtils.tryParseFloat(str6, 0.0F);
        int i2 = arrayOfString.length;
        if (j >= i2)
          break label2045;
        int i26 = j + 1;
        str7 = arrayOfString[j];
        j = i26;
      }
      while (true)
      {
        this._indicator.focusCx = ((float)(d3 * d1));
        this._indicator.focusCy = ((float)(d4 * d2));
        this._indicator.normalCx = ((float)(d3 * d1));
        this._indicator.normalCy = ((float)(d4 * d2));
        indicatorInfo localindicatorInfo4 = this._indicator;
        indicatorInfo localindicatorInfo5 = this._indicator;
        float f3 = (float)(d5 * d1);
        localindicatorInfo5.focusPadding = f3;
        localindicatorInfo4.normalPadding = f3;
        indicatorInfo localindicatorInfo6 = this._indicator;
        this._indicator.focusColor = -1;
        localindicatorInfo6.normalColor = -1;
        if (!TextUtils.isEmpty(str7))
          break label2051;
        indicatorInfo localindicatorInfo12 = this._indicator;
        this._indicator.focusImage = null;
        localindicatorInfo12.normalImage = null;
        break;
        str4 = "4";
        break label1766;
        label2029: str5 = "4";
        break label1806;
        label2037: str6 = "0";
        break label1846;
        label2045: str7 = null;
      }
      label2051: IndicatorDrawableInfo localIndicatorDrawableInfo1 = new IndicatorDrawableInfo();
      localIndicatorDrawableInfo1._url = str7;
      localIndicatorDrawableInfo1._bmp = XulWorker.loadDrawableFromCache(localIndicatorDrawableInfo1._url);
      if (localIndicatorDrawableInfo1._bmp != null)
        markDirtyView();
      localIndicatorDrawableInfo1._height = XulUtils.roundToInt(d4 * d2);
      localIndicatorDrawableInfo1._width = XulUtils.roundToInt(d3 * d1);
      indicatorInfo localindicatorInfo7 = this._indicator;
      this._indicator.focusImage = localIndicatorDrawableInfo1;
      localindicatorInfo7.normalImage = localIndicatorDrawableInfo1;
      break label830;
      label2144: str8 = null;
      break label859;
      label2150: str17 = "4";
      break label909;
      label2158: str18 = "0";
      break label949;
      label2166: str19 = "FFFFFFFF";
      break label982;
      label2174: if ("dash".equals(str8))
      {
        this._indicator.normalStyle = indicatorStyle.STYLE_DASH;
        int i11 = arrayOfString.length;
        String str13;
        double d9;
        String str14;
        double d10;
        String str15;
        double d11;
        if (j < i11)
        {
          int i18 = j + 1;
          str13 = arrayOfString[j];
          j = i18;
          d9 = XulUtils.tryParseFloat(str13, 4.0F);
          int i12 = arrayOfString.length;
          if (j >= i12)
            break label2407;
          int i17 = j + 1;
          str14 = arrayOfString[j];
          j = i17;
          d10 = XulUtils.tryParseFloat(str14, 4.0F);
          int i13 = arrayOfString.length;
          if (j >= i13)
            break label2415;
          int i16 = j + 1;
          str15 = arrayOfString[j];
          j = i16;
          d11 = XulUtils.tryParseFloat(str15, 0.0F);
          int i14 = arrayOfString.length;
          if (j >= i14)
            break label2423;
          (j + 1);
        }
        for (String str16 = arrayOfString[j]; ; str16 = "FFFFFFFF")
        {
          int i15 = (int)XulUtils.tryParseHex(str16, -1L);
          this._indicator.normalCx = ((float)(d9 * d1));
          this._indicator.normalCy = ((float)(d10 * d2));
          this._indicator.normalPadding = ((float)(d11 * d1));
          this._indicator.normalColor = i15;
          break;
          str13 = "4";
          break label2224;
          str14 = "4";
          break label2264;
          str15 = "0";
          break label2304;
        }
      }
      label2224: label2264: label2407: label2415: label2423: if (!"image".equals(str8))
        break label1049;
      label2304: indicatorInfo localindicatorInfo8 = this._indicator;
      indicatorInfo localindicatorInfo9 = this._indicator;
      indicatorStyle localindicatorStyle2 = indicatorStyle.STYLE_IMAGE;
      localindicatorInfo9.focusStyle = localindicatorStyle2;
      localindicatorInfo8.normalStyle = localindicatorStyle2;
      int i4 = arrayOfString.length;
      String str9;
      label2502: double d6;
      String str10;
      label2542: double d7;
      String str11;
      label2582: double d8;
      if (j < i4)
      {
        int i10 = j + 1;
        str9 = arrayOfString[j];
        j = i10;
        d6 = XulUtils.tryParseFloat(str9, 16.0F);
        int i5 = arrayOfString.length;
        if (j >= i5)
          break label2689;
        int i9 = j + 1;
        str10 = arrayOfString[j];
        j = i9;
        d7 = XulUtils.tryParseFloat(str10, 16.0F);
        int i6 = arrayOfString.length;
        if (j >= i6)
          break label2697;
        int i8 = j + 1;
        str11 = arrayOfString[j];
        j = i8;
        d8 = XulUtils.tryParseFloat(str11, 0.0F);
        int i7 = arrayOfString.length;
        if (j >= i7)
          break label2705;
        (j + 1);
      }
      label2689: label2697: label2705: for (String str12 = arrayOfString[j]; ; str12 = null)
      {
        this._indicator.normalCx = ((float)(d6 * d1));
        this._indicator.normalCy = ((float)(d7 * d2));
        this._indicator.normalPadding = ((float)(d8 * d1));
        this._indicator.normalColor = -1;
        if (!TextUtils.isEmpty(str12))
          break label2711;
        this._indicator.normalImage = null;
        break;
        str9 = "4";
        break label2502;
        str10 = "4";
        break label2542;
        str11 = "0";
        break label2582;
      }
      label2711: IndicatorDrawableInfo localIndicatorDrawableInfo2 = new IndicatorDrawableInfo();
      localIndicatorDrawableInfo2._url = str12;
      localIndicatorDrawableInfo2._bmp = XulWorker.loadDrawableFromCache(localIndicatorDrawableInfo2._url);
      if (localIndicatorDrawableInfo2._bmp != null)
        markDirtyView();
      localIndicatorDrawableInfo2._height = XulUtils.roundToInt(d7 * d2);
      localIndicatorDrawableInfo2._width = XulUtils.roundToInt(d6 * d1);
      this._indicator.normalImage = localIndicatorDrawableInfo2;
      break label1049;
      label2791: str2 = "12";
      break label1067;
      label2799: f1 = 0.5F;
    }
    label2810: this._indicator = null;
  }

  public void syncPages()
  {
    collectContent();
  }

  class IndicatorDrawableInfo extends XulRenderDrawableItem
  {
    XulDrawable _bmp = null;
    int _height = 0;
    volatile boolean _isLoading = false;
    volatile boolean _isRecycled = false;
    volatile long _lastLoadFailedTime = 0L;
    volatile int _loadFailedCounter = 0;
    String _url;
    int _width = 0;

    IndicatorDrawableInfo()
    {
    }

    public void onImageReady(XulDrawable paramXulDrawable)
    {
      this._isLoading = false;
      if (this._isRecycled)
        return;
      if (paramXulDrawable == null)
        this._lastLoadFailedTime = XulUtils.timestamp();
      for (this._loadFailedCounter = (1 + this._loadFailedCounter); ; this._loadFailedCounter = 0)
      {
        XulPageSliderAreaRender.this.markDirtyView();
        return;
        this._bmp = paramXulDrawable;
        this._lastLoadFailedTime = 0L;
      }
    }
  }

  protected class LayoutContainer extends XulViewContainerBaseRender.LayoutContainer
  {
    protected LayoutContainer()
    {
      super();
    }

    public int prepare()
    {
      super.prepare();
      if (!XulPageSliderAreaRender.this._isLayoutChangedByChild())
        XulPageSliderAreaRender.this.syncDirection();
      XulPageSliderAreaRender.this.collectContent();
      return 0;
    }
  }

  private class _PageSliderChildrenCollector extends XulArea.XulAreaIterator
  {
    private _PageSliderChildrenCollector()
    {
    }

    private void collectChild(XulView paramXulView)
    {
      if ("none".equals(paramXulView.getStyleString(XulPropNameCache.TagId.DISPLAY)))
        return;
      XulPageSliderAreaRender.this._contents.add(paramXulView);
    }

    public boolean onXulArea(int paramInt, XulArea paramXulArea)
    {
      collectChild(paramXulArea);
      return true;
    }

    public boolean onXulItem(int paramInt, XulItem paramXulItem)
    {
      collectChild(paramXulItem);
      return true;
    }

    public boolean onXulTemplate(int paramInt, XulTemplate paramXulTemplate)
    {
      return true;
    }
  }

  static class indicatorInfo
  {
    int focusColor = -1;
    float focusCx = 10.0F;
    float focusCy = 10.0F;
    XulPageSliderAreaRender.IndicatorDrawableInfo focusImage;
    float focusPadding = 0.0F;
    XulPageSliderAreaRender.indicatorStyle focusStyle = XulPageSliderAreaRender.indicatorStyle.STYLE_DOT;
    int gap = 12;
    float indicatorAlign = 0.5F;
    int normalColor = -1;
    float normalCx = 7.0F;
    float normalCy = 7.0F;
    XulPageSliderAreaRender.IndicatorDrawableInfo normalImage;
    float normalPadding = 0.0F;
    XulPageSliderAreaRender.indicatorStyle normalStyle = XulPageSliderAreaRender.indicatorStyle.STYLE_DOT;
    float xAlign;
    float yAlign;
  }

  static enum indicatorStyle
  {
    static
    {
      STYLE_DASH = new indicatorStyle("STYLE_DASH", 1);
      STYLE_IMAGE = new indicatorStyle("STYLE_IMAGE", 2);
      indicatorStyle[] arrayOfindicatorStyle = new indicatorStyle[3];
      arrayOfindicatorStyle[0] = STYLE_DOT;
      arrayOfindicatorStyle[1] = STYLE_DASH;
      arrayOfindicatorStyle[2] = STYLE_IMAGE;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.XulPageSliderAreaRender
 * JD-Core Version:    0.6.2
 */