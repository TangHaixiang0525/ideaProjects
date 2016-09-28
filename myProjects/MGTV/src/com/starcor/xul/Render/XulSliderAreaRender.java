package com.starcor.xul.Render;

import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import com.starcor.xul.Events.XulStateChangeEvent;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Graphics.XulDrawable;
import com.starcor.xul.Prop.XulAction;
import com.starcor.xul.Prop.XulAttr;
import com.starcor.xul.Prop.XulPropNameCache.TagId;
import com.starcor.xul.Prop.XulStyle;
import com.starcor.xul.Render.Components.BaseScrollBar;
import com.starcor.xul.Render.Components.BaseScrollBar.ScrollBarHelper;
import com.starcor.xul.Render.Components.SimpleScrollBar;
import com.starcor.xul.Render.Effect.SimpleTransformAnimation;
import com.starcor.xul.Render.Transform.ITransformer;
import com.starcor.xul.Utils.XulAreaChildrenRender;
import com.starcor.xul.Utils.XulAreaChildrenVisibleChangeNotifier;
import com.starcor.xul.Utils.XulLayoutHelper;
import com.starcor.xul.Utils.XulLayoutHelper.ILayoutElement;
import com.starcor.xul.Utils.XulPropParser.xulParsedAttr_Direction;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_FloatArray;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_PaddingMargin;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_booleanValue;
import com.starcor.xul.Utils.XulRenderDrawableItem;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulElement;
import com.starcor.xul.XulItem;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulLayout.FocusDirection;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulTaskCollector;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker;
import com.starcor.xul.XulWorker.DrawableItem;

public class XulSliderAreaRender extends XulViewContainerBaseRender
{
  private static final String TAG = XulSliderAreaRender.class.getSimpleName();
  int _alignXOffset = 0;
  int _alignYOffset = 0;
  boolean _autoScroll = true;
  boolean _bottomIndicatorVisible = false;
  boolean _clipChildren = true;
  boolean _clipFocus = true;
  int _contentMaxHeight = 0;
  int _contentMaxWidth = 0;
  _SliderIndicator _downIndicator;
  int _focusPaddingBottom = 0;
  int _focusPaddingLeft = 0;
  int _focusPaddingRight = 0;
  int _focusPaddingTop = 0;
  boolean _isVertical = false;
  _SliderIndicator _leftIndicator;
  boolean _leftIndicatorVisible = false;
  boolean _lockFocus = false;
  float _lockFocusAlignment = (0.0F / 0.0F);
  float _lockFocusChildAlignment = (0.0F / 0.0F);
  boolean _lockFocusDynamic = false;
  boolean _loopMode = false;
  boolean _reverseLayout = false;
  _SliderIndicator _rightIndicator;
  boolean _rightIndicatorVisible = false;
  private ScrollTransformAnimation _scrollAnimation;
  BaseScrollBar _scrollBar = null;
  BaseScrollBar.ScrollBarHelper _scrollBarHelper;
  int _scrollPos = 0;
  int _scrollTargetPos = 0;
  boolean _topIndicatorVisible = false;
  _SliderIndicator _upIndicator;
  float _xAlign = 0.0F;
  float _yAlign = 0.0F;
  XulAreaChildrenRender childrenRender = new XulAreaChildrenRender();

  public XulSliderAreaRender(XulRenderContext paramXulRenderContext, XulArea paramXulArea)
  {
    super(paramXulRenderContext, paramXulArea);
  }

  private void _adjustFocusAlignment(XulView paramXulView)
  {
    for (XulArea localXulArea = paramXulView.getParent(); localXulArea != this._area; localXulArea = localXulArea.getParent())
    {
      if (localXulArea == null)
        return;
      paramXulView = localXulArea;
    }
    RectF localRectF1 = getFocusRect();
    RectF localRectF2 = paramXulView.getFocusRc();
    if (this._isVertical)
    {
      this._lockFocusAlignment = (((localRectF2.top + localRectF2.bottom) / 2.0F - localRectF1.top - this._scrollPos) / this._contentMaxHeight);
      return;
    }
    this._lockFocusAlignment = (((localRectF2.right + localRectF2.left) / 2.0F - localRectF1.left - this._scrollPos) / this._contentMaxWidth);
  }

  private void _adjustLoopingContent()
  {
    if ((!this._loopMode) || (this._area.getChildNum() < 2))
      return;
    if (this._isVertical)
    {
      _adjustLoopingContentVertical();
      return;
    }
    _adjustLoopingContentHorizontal();
  }

  private void _adjustLoopingContentHorizontal()
  {
    int i = XulUtils.calRectWidth(this._rect);
    if (this._padding != null)
      i -= this._padding.left + this._padding.right;
    int j = this._contentMaxWidth;
    if (j < i);
    while (true)
    {
      return;
      int k = (i - j) / 2;
      XulView localXulView1 = this._area.getLastChild();
      XulViewRender localXulViewRender1 = localXulView1.getRender();
      float f1 = XulUtils.calRectWidth(localXulViewRender1._rect);
      Rect localRect1 = localXulViewRender1._margin;
      if (localRect1 != null)
        f1 += Math.max(localRect1.left, localRect1.right);
      while ((-this._scrollPos <= j) && (this._scrollPos - k > f1))
      {
        int n = (int)-f1;
        this._scrollPos = (n + this._scrollPos);
        this._scrollTargetPos = (n + this._scrollTargetPos);
        if (this._scrollAnimation != null)
          this._scrollAnimation.offsetVal(n);
        this._area.removeChild(localXulView1);
        this._area.addChild(0, localXulView1);
        XulLayoutHelper.offsetBase(localXulView1.getRender().getLayoutElement(), -j, 0);
        localXulView1 = this._area.getLastChild();
        XulViewRender localXulViewRender4 = localXulView1.getRender();
        f1 = XulUtils.calRectWidth(localXulViewRender4._rect);
        Rect localRect4 = localXulViewRender4._margin;
        if (localRect4 != null)
          f1 += Math.max(localRect4.left, localRect4.right);
      }
      XulView localXulView2 = this._area.getFirstChild();
      XulViewRender localXulViewRender2 = localXulView2.getRender();
      float f2 = XulUtils.calRectWidth(localXulViewRender2._rect);
      Rect localRect2 = localXulViewRender2._margin;
      if (localRect2 != null)
        f2 += Math.max(localRect2.left, localRect2.right);
      while ((this._scrollPos <= -f2) && (k - this._scrollPos > f2))
      {
        int m = (int)f2;
        this._scrollPos = (m + this._scrollPos);
        this._scrollTargetPos = (m + this._scrollTargetPos);
        if (this._scrollAnimation != null)
          this._scrollAnimation.offsetVal(m);
        this._area.removeChild(localXulView2);
        this._area.addChild(localXulView2);
        XulLayoutHelper.offsetBase(localXulView2.getRender().getLayoutElement(), j, 0);
        localXulView2 = this._area.getFirstChild();
        XulViewRender localXulViewRender3 = localXulView2.getRender();
        f2 = XulUtils.calRectWidth(localXulViewRender3._rect);
        Rect localRect3 = localXulViewRender3._margin;
        if (localRect3 != null)
          f2 += Math.max(localRect3.left, localRect3.right);
      }
    }
  }

  private void _adjustLoopingContentVertical()
  {
    int i = XulUtils.calRectHeight(this._rect);
    if (this._padding != null)
      i -= this._padding.top + this._padding.bottom;
    int j = this._contentMaxHeight;
    if (j < i);
    while (true)
    {
      return;
      int k = (i - j) / 2;
      XulView localXulView1 = this._area.getLastChild();
      XulViewRender localXulViewRender1 = localXulView1.getRender();
      float f1 = XulUtils.calRectHeight(localXulViewRender1._rect);
      Rect localRect1 = localXulViewRender1._margin;
      if (localRect1 != null)
        f1 += Math.max(localRect1.top, localRect1.bottom);
      while ((-this._scrollPos <= j) && (this._scrollPos - k > f1))
      {
        int n = (int)-f1;
        this._scrollPos = (n + this._scrollPos);
        this._scrollTargetPos = (n + this._scrollTargetPos);
        if (this._scrollAnimation != null)
          this._scrollAnimation.offsetVal(n);
        this._area.removeChild(localXulView1);
        this._area.addChild(0, localXulView1);
        XulLayoutHelper.offsetBase(localXulView1.getRender().getLayoutElement(), 0, -j);
        localXulView1 = this._area.getLastChild();
        XulViewRender localXulViewRender4 = localXulView1.getRender();
        f1 = XulUtils.calRectHeight(localXulViewRender4._rect);
        Rect localRect4 = localXulViewRender4._margin;
        if (localRect4 != null)
          f1 += Math.max(localRect4.top, localRect4.bottom);
      }
      XulView localXulView2 = this._area.getFirstChild();
      XulViewRender localXulViewRender2 = localXulView2.getRender();
      float f2 = XulUtils.calRectHeight(localXulViewRender2._rect);
      Rect localRect2 = localXulViewRender2._margin;
      if (localRect2 != null)
        f2 += Math.max(localRect2.top, localRect2.bottom);
      while ((this._scrollPos <= -f2) && (k - this._scrollPos > f2))
      {
        int m = (int)f2;
        this._scrollPos = (m + this._scrollPos);
        this._scrollTargetPos = (m + this._scrollTargetPos);
        if (this._scrollAnimation != null)
          this._scrollAnimation.offsetVal(m);
        this._area.removeChild(localXulView2);
        this._area.addChild(localXulView2);
        XulLayoutHelper.offsetBase(localXulView2.getRender().getLayoutElement(), 0, j);
        localXulView2 = this._area.getFirstChild();
        XulViewRender localXulViewRender3 = localXulView2.getRender();
        f2 = XulUtils.calRectHeight(localXulViewRender3._rect);
        Rect localRect3 = localXulViewRender3._margin;
        if (localRect3 != null)
          f2 += Math.max(localRect3.top, localRect3.bottom);
      }
    }
  }

  private BaseScrollBar.ScrollBarHelper _getScrollBarHelper()
  {
    if (this._scrollBarHelper == null)
      this._scrollBarHelper = new BaseScrollBar.ScrollBarHelper()
      {
        public int getContentHeight()
        {
          return XulSliderAreaRender.this._contentMaxHeight;
        }

        public int getContentWidth()
        {
          return XulSliderAreaRender.this._contentMaxWidth;
        }

        public int getScrollPos()
        {
          return XulSliderAreaRender.this._scrollPos;
        }

        public boolean isVertical()
        {
          return XulSliderAreaRender.this._isVertical;
        }
      };
    return this._scrollBarHelper;
  }

  private void _notifyScrollStop()
  {
    XulAction localXulAction = this._view.getAction(XulPropNameCache.TagId.ACTION_SCROLL_STOPPED);
    if (localXulAction == null)
      return;
    this._view.getOwnerPage();
    XulView localXulView = this._view;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(getScrollPos());
    arrayOfObject[1] = Integer.valueOf(getScrollRange());
    XulPage.invokeActionNoPopupWithArgs(localXulView, localXulAction, arrayOfObject);
  }

  private boolean _updateAndNotifyIndicatorChanged(boolean paramBoolean1, final boolean paramBoolean2, final String paramString)
  {
    if (paramBoolean1 == paramBoolean2)
      return paramBoolean1;
    if (this._view.getAction("indicatorChanged") != null)
      getRenderContext().uiRun(new Runnable()
      {
        public void run()
        {
          XulSliderAreaRender.this._view.getOwnerPage();
          XulView localXulView = XulSliderAreaRender.this._view;
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = paramString;
          arrayOfObject[1] = Boolean.valueOf(paramBoolean2);
          XulPage.invokeActionNoPopupWithArgs(localXulView, "indicatorChanged", arrayOfObject);
        }
      });
    return paramBoolean2;
  }

  private void initScrollAnimation()
  {
    if (!_hasAnimation())
    {
      if (this._scrollAnimation != null)
        removeAnimation(this._scrollAnimation);
      return;
    }
    if (this._scrollAnimation == null)
      this._scrollAnimation = new ScrollTransformAnimation(this);
    this._scrollAnimation.setTransformer(this._aniTransformer);
  }

  private boolean onChildFocused(XulView paramXulView, boolean paramBoolean)
  {
    if (!this._autoScroll)
      return false;
    if (this._lockFocusDynamic)
      _adjustFocusAlignment(paramXulView);
    if ((this._lockFocus) && (!Float.isNaN(this._lockFocusAlignment)))
    {
      if (Float.isNaN(this._lockFocusChildAlignment))
        return makeChildVisible(paramXulView, this._lockFocusAlignment, paramBoolean);
      return makeChildVisible(paramXulView, this._lockFocusAlignment, this._lockFocusChildAlignment, paramBoolean);
    }
    return makeChildVisible(paramXulView, paramBoolean);
  }

  public static void register()
  {
    XulRenderFactory.registerBuilder("area", "slider", new XulRenderFactory.RenderBuilder()
    {
      static
      {
        if (!XulSliderAreaRender.class.desiredAssertionStatus());
        for (boolean bool = true; ; bool = false)
        {
          $assertionsDisabled = bool;
          return;
        }
      }

      protected XulViewRender createRender(XulRenderContext paramAnonymousXulRenderContext, XulView paramAnonymousXulView)
      {
        assert ((paramAnonymousXulView instanceof XulArea));
        return new XulSliderAreaRender(paramAnonymousXulRenderContext, (XulArea)paramAnonymousXulView);
      }
    });
  }

  private void scrollContentTo(float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    if (this._isVertical);
    for (this._scrollTargetPos = XulUtils.roundToInt(paramFloat2); ; this._scrollTargetPos = XulUtils.roundToInt(paramFloat1))
    {
      if (this._scrollTargetPos != this._scrollPos)
      {
        if (!paramBoolean)
          break;
        setUpdateLayout();
      }
      return;
    }
    int i = this._scrollTargetPos - this._scrollPos;
    this._scrollPos = this._scrollTargetPos;
    if (this._isVertical)
      XulLayoutHelper.offsetChild(getLayoutElement(), 0, i);
    while (true)
    {
      _adjustLoopingContent();
      markDirtyView();
      _notifyScrollStop();
      return;
      XulLayoutHelper.offsetChild(getLayoutElement(), i, 0);
    }
  }

  private void startAnimation()
  {
    if (this._scrollTargetPos == this._scrollPos);
    do
    {
      return;
      if (!_hasAnimation())
      {
        int i = this._scrollTargetPos - this._scrollPos;
        this._scrollPos = (i + this._scrollPos);
        if (this._isVertical)
          XulLayoutHelper.offsetChild(getLayoutElement(), 0, i);
        while (true)
        {
          _adjustLoopingContent();
          markDirtyView();
          return;
          XulLayoutHelper.offsetChild(getLayoutElement(), i, 0);
        }
      }
      initScrollAnimation();
    }
    while (this._scrollAnimation == null);
    this._scrollAnimation.prepareAnimation(this._aniTransformDuration);
    this._scrollAnimation.startAnimation();
    addAnimation(this._scrollAnimation);
  }

  private void syncAlignInfo()
  {
    if (!_isViewChanged());
    XulPropParser.xulParsedProp_FloatArray localxulParsedProp_FloatArray;
    int i;
    do
    {
      XulAttr localXulAttr;
      do
      {
        return;
        localXulAttr = this._area.getAttr(XulPropNameCache.TagId.ALIGN);
        this._xAlign = 0.0F;
        this._yAlign = 0.0F;
      }
      while (localXulAttr == null);
      localxulParsedProp_FloatArray = (XulPropParser.xulParsedProp_FloatArray)localXulAttr.getParsedValue();
      i = localxulParsedProp_FloatArray.getLength();
      if (i == 1)
      {
        float f = localxulParsedProp_FloatArray.tryGetVal(0, 0.0F);
        this._yAlign = f;
        this._xAlign = f;
        return;
      }
    }
    while (i != 2);
    this._xAlign = localxulParsedProp_FloatArray.tryGetVal(0, 0.0F);
    this._yAlign = localxulParsedProp_FloatArray.tryGetVal(1, 0.0F);
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
    XulPropParser.xulParsedAttr_Direction localxulParsedAttr_Direction = (XulPropParser.xulParsedAttr_Direction)localXulAttr.getParsedValue();
    this._isVertical = localxulParsedAttr_Direction.vertical;
    this._reverseLayout = localxulParsedAttr_Direction.reverse;
  }

  private _SliderIndicator tryGetPendingImageItem(_SliderIndicator param_SliderIndicator)
  {
    if (param_SliderIndicator == null)
      return null;
    if (param_SliderIndicator._isLoading)
      return null;
    if (TextUtils.isEmpty(param_SliderIndicator._url))
      return null;
    if ((param_SliderIndicator._bmp != null) && (!param_SliderIndicator._bmp.isRecycled()))
      return null;
    param_SliderIndicator._isLoading = true;
    param_SliderIndicator.url = param_SliderIndicator._url;
    param_SliderIndicator.width = param_SliderIndicator._width;
    param_SliderIndicator.height = param_SliderIndicator._height;
    return param_SliderIndicator;
  }

  public _SliderIndicator _createIndicator(_SliderIndicator param_SliderIndicator, String[] paramArrayOfString, float paramFloat1, float paramFloat2)
  {
    if (paramArrayOfString == null)
      return null;
    if ((paramArrayOfString.length != 1) && (paramArrayOfString.length != 3) && (paramArrayOfString.length != 5))
      return null;
    if (param_SliderIndicator == null)
      return new _SliderIndicator(paramArrayOfString, paramFloat1, paramFloat2);
    param_SliderIndicator.update(paramArrayOfString, paramFloat1, paramFloat2);
    return param_SliderIndicator;
  }

  public void activateScrollBar()
  {
    if (this._scrollBar != null)
      this._scrollBar.reset();
  }

  public XulWorker.DrawableItem collectPendingImageItem()
  {
    _SliderIndicator local_SliderIndicator1 = tryGetPendingImageItem(this._downIndicator);
    if (local_SliderIndicator1 != null)
      return local_SliderIndicator1;
    _SliderIndicator local_SliderIndicator2 = tryGetPendingImageItem(this._upIndicator);
    if (local_SliderIndicator2 != null)
      return local_SliderIndicator2;
    _SliderIndicator local_SliderIndicator3 = tryGetPendingImageItem(this._leftIndicator);
    if (local_SliderIndicator3 != null)
      return local_SliderIndicator3;
    _SliderIndicator local_SliderIndicator4 = tryGetPendingImageItem(this._rightIndicator);
    if (local_SliderIndicator4 != null)
      return local_SliderIndicator4;
    return super.collectPendingImageItem();
  }

  public boolean collectPendingItems(XulTaskCollector paramXulTaskCollector)
  {
    if ((_isViewChanged()) || (this._rect == null));
    while (this._scrollTargetPos != this._scrollPos)
      return true;
    return false;
  }

  protected XulLayoutHelper.ILayoutElement createElement()
  {
    return new LayoutContainer();
  }

  public void draw(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    if (_isInvisible())
      return;
    super.draw(paramXulDC, paramRect, paramInt1, paramInt2);
    paramXulDC.save();
    RectF localRectF1 = getFocusRect();
    localRectF1.left += paramInt1 + this._padding.left;
    localRectF1.top += paramInt2 + this._padding.top;
    localRectF1.right += paramInt1 - this._padding.right;
    localRectF1.bottom += paramInt2 - this._padding.bottom;
    boolean bool1;
    label136: boolean bool2;
    if (XulUtils.calRectHeight(localRectF1) - this._scrollTargetPos < this._contentMaxHeight)
    {
      bool1 = true;
      if (XulUtils.calRectWidth(localRectF1) - this._scrollTargetPos >= this._contentMaxWidth)
        break label402;
      bool2 = true;
      label159: if (this._clipChildren)
        paramXulDC.clipRect(localRectF1);
      this.childrenRender.init(paramXulDC, paramRect, paramInt1, paramInt2);
      this._area.eachView(this.childrenRender);
      if ((this._clipChildren) && (this._clipFocus))
        paramXulDC.doPostDraw(0, this._area);
      paramXulDC.restore();
      if (!this._isVertical)
        break label414;
      if (this._scrollTargetPos == 0)
        break label408;
    }
    label402: label408: for (boolean bool4 = true; ; bool4 = false)
    {
      this._topIndicatorVisible = _updateAndNotifyIndicatorChanged(this._topIndicatorVisible, bool4, "top");
      this._bottomIndicatorVisible = _updateAndNotifyIndicatorChanged(this._bottomIndicatorVisible, bool1, "bottom");
      if ((this._upIndicator != null) && (bool4))
        drawIndicator(this._upIndicator, paramXulDC, paramRect, paramInt1, paramInt2);
      if ((this._downIndicator != null) && (bool1))
        drawIndicator(this._downIndicator, paramXulDC, paramRect, paramInt1, paramInt2);
      if (this._scrollBar != null)
      {
        RectF localRectF2 = getAnimRect();
        this._scrollBar.draw(paramXulDC, paramRect, XulUtils.roundToInt(paramInt1 + localRectF2.left), XulUtils.roundToInt(paramInt2 + localRectF2.top));
      }
      int i = getScrollPos();
      int j = getScrollRange();
      if (i <= j)
        break;
      scrollTo(j, true);
      return;
      bool1 = false;
      break label136;
      bool2 = false;
      break label159;
    }
    label414: if (this._scrollTargetPos != 0);
    for (boolean bool3 = true; ; bool3 = false)
    {
      this._leftIndicatorVisible = _updateAndNotifyIndicatorChanged(this._leftIndicatorVisible, bool3, "left");
      this._rightIndicatorVisible = _updateAndNotifyIndicatorChanged(this._rightIndicatorVisible, bool2, "right");
      if ((this._leftIndicator != null) && (bool3))
        drawIndicator(this._leftIndicator, paramXulDC, paramRect, paramInt1, paramInt2);
      if ((this._rightIndicator == null) || (!bool2))
        break;
      drawIndicator(this._rightIndicator, paramXulDC, paramRect, paramInt1, paramInt2);
      break;
    }
  }

  public void drawIndicator(_SliderIndicator param_SliderIndicator, XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    XulDrawable localXulDrawable = param_SliderIndicator._bmp;
    if ((localXulDrawable == null) || (localXulDrawable.isRecycled()))
      localXulDrawable = XulWorker.loadDrawableFromCache(param_SliderIndicator._url);
    if (localXulDrawable == null)
      return;
    int i = localXulDrawable.getWidth();
    int j = localXulDrawable.getHeight();
    int k = XulUtils.roundToInt((XulUtils.calRectWidth(this._rect) - i) * param_SliderIndicator._xAlign);
    int m = XulUtils.roundToInt((XulUtils.calRectHeight(this._rect) - j) * param_SliderIndicator._yAlign);
    int n = k + (paramInt1 + (this._screenX + this._rect.left));
    int i1 = m + (paramInt2 + (this._screenY + this._rect.top));
    paramXulDC.drawBitmap(localXulDrawable, n, i1, XulRenderContext.getDefPicPaint());
  }

  public int getDefaultFocusMode()
  {
    return 37;
  }

  public int getScrollDelta()
  {
    return -(this._scrollPos - this._scrollTargetPos);
  }

  public int getScrollPos()
  {
    return -this._scrollTargetPos;
  }

  public int getScrollRange()
  {
    if (this._rect == null);
    int i;
    do
    {
      int j;
      do
      {
        return 0;
        if (!this._isVertical)
          break;
        j = XulUtils.calRectHeight(this._rect) - this._padding.top - this._padding.bottom;
      }
      while (this._contentMaxHeight <= j);
      return this._contentMaxHeight - j;
      i = XulUtils.calRectWidth(this._rect) - this._padding.left - this._padding.right;
    }
    while (this._contentMaxWidth <= i);
    return this._contentMaxWidth - i;
  }

  public boolean handleScrollEvent(float paramFloat1, float paramFloat2)
  {
    if (this._lockFocus)
    {
      XulLayout localXulLayout = this._view.getRootLayout();
      XulView localXulView1 = localXulLayout.getFocus();
      if ((localXulView1 != null) && (localXulView1.isChildOf(this._view)))
      {
        RectF localRectF = localXulView1.getFocusRc();
        XulLayout.FocusDirection localFocusDirection;
        if (isVertical())
          if (paramFloat2 > 0.0F)
            localFocusDirection = XulLayout.FocusDirection.MOVE_UP;
        while (true)
        {
          XulView localXulView2 = this._area.findSubFocus(localFocusDirection, localRectF, localXulView1);
          if (localXulView2 != null)
            localXulLayout.requestFocus(localXulView2);
          return true;
          localFocusDirection = XulLayout.FocusDirection.MOVE_DOWN;
          continue;
          if (paramFloat1 > 0.0F)
            localFocusDirection = XulLayout.FocusDirection.MOVE_LEFT;
          else
            localFocusDirection = XulLayout.FocusDirection.MOVE_RIGHT;
        }
      }
    }
    float f3;
    if (isVertical())
    {
      f3 = paramFloat2;
      if (f3 == 0.0F)
        f3 = paramFloat1;
    }
    float f1;
    for (float f2 = f3 * (32.0F * getRenderContext().getYScalar()); ((f2 > 0.0F) && (getScrollPos() == 0)) || ((getScrollPos() >= getScrollRange()) && (f2 < 0.0F)); f2 = f1 * (32.0F * getRenderContext().getXScalar()))
    {
      return false;
      f1 = paramFloat2;
      if (f1 == 0.0F)
        f1 = paramFloat2;
    }
    scrollTo((int)(getScrollPos() - f2), true);
    return true;
  }

  public boolean hitTest(int paramInt, float paramFloat1, float paramFloat2)
  {
    if (paramInt == 8)
      return super.hitTest(-1, paramFloat1, paramFloat2);
    return super.hitTest(paramInt, paramFloat1, paramFloat2);
  }

  public boolean isScrolling()
  {
    return this._scrollTargetPos != this._scrollPos;
  }

  public boolean isVertical()
  {
    return this._isVertical;
  }

  public boolean makeChildVisible(XulView paramXulView, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    XulViewRender localXulViewRender = paramXulView.getRender();
    if ((localXulViewRender == null) || (localXulViewRender._rect == null) || (localXulViewRender._padding == null))
      return false;
    return makeRectVisible(paramXulView.getFocusRc(), paramFloat1, paramFloat2, paramBoolean);
  }

  public boolean makeChildVisible(XulView paramXulView, float paramFloat, boolean paramBoolean)
  {
    return makeChildVisible(paramXulView, paramFloat, (0.0F / 0.0F), paramBoolean);
  }

  public boolean makeChildVisible(XulView paramXulView, boolean paramBoolean)
  {
    XulViewRender localXulViewRender = paramXulView.getRender();
    if ((localXulViewRender == null) || (localXulViewRender._rect == null) || (localXulViewRender._padding == null))
      return false;
    return makeRectVisible(paramXulView.getFocusRc(), paramBoolean);
  }

  public boolean makeRectVisible(RectF paramRectF, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    if (this._scrollBar != null)
      this._scrollBar.reset();
    int i = XulUtils.calRectWidth(this._rect) - this._padding.left - this._padding.right;
    int j = XulUtils.calRectHeight(this._rect) - this._padding.top - this._padding.bottom;
    if ((this._isVertical) && (this._contentMaxHeight <= j));
    while ((!this._isVertical) && (this._contentMaxWidth <= i))
      return false;
    XulUtils.offsetRect(paramRectF, -(this._screenX + this._rect.left + this._padding.left), -(this._screenY + this._rect.top + this._padding.top));
    float f3;
    int m;
    label185: float f4;
    if (paramFloat1 < 0.0F)
    {
      paramFloat1 = 0.0F;
      if (!this._isVertical)
        break label302;
      f3 = XulUtils.calRectHeight(paramRectF);
      if (!Float.isNaN(paramFloat2))
        break label241;
      m = (int)(paramFloat1 * (j - f3));
      if (f3 > j)
        m = 0;
      f4 = this._scrollPos + (m - paramRectF.top);
      if (f4 <= 0.0F)
        break label257;
      scrollContentTo(0.0F, 0.0F, paramBoolean);
    }
    while (true)
    {
      return true;
      if (paramFloat1 <= 1.0F)
        break;
      paramFloat1 = 1.0F;
      break;
      label241: m = (int)(paramFloat1 * j - f3 * paramFloat2);
      break label185;
      label257: if (f4 + this._contentMaxHeight >= j)
      {
        scrollContentTo(0.0F, f4, paramBoolean);
      }
      else
      {
        scrollContentTo(0.0F, j - this._contentMaxHeight, paramBoolean);
        continue;
        label302: float f1 = XulUtils.calRectWidth(paramRectF);
        if (Float.isNaN(paramFloat2));
        float f2;
        for (int k = (int)(paramFloat1 * (i - f1)); ; k = (int)(paramFloat1 * i - f1 * paramFloat2))
        {
          if (f1 > i)
            k = 0;
          f2 = this._scrollPos + (k - paramRectF.left);
          if (f2 <= 0.0F)
            break label388;
          scrollContentTo(0.0F, 0.0F, paramBoolean);
          break;
        }
        label388: if (f2 + this._contentMaxWidth >= i)
          scrollContentTo(f2, 0.0F, paramBoolean);
        else
          scrollContentTo(i - this._contentMaxWidth, 0.0F, paramBoolean);
      }
    }
  }

  public boolean makeRectVisible(RectF paramRectF, boolean paramBoolean)
  {
    if (this._scrollBar != null)
      this._scrollBar.reset();
    int i = XulUtils.calRectWidth(this._rect) - this._padding.left - this._padding.right;
    int j = XulUtils.calRectHeight(this._rect) - this._padding.top - this._padding.bottom;
    if ((this._isVertical) && (this._contentMaxHeight <= j));
    while ((!this._isVertical) && (this._contentMaxWidth <= i))
      return false;
    XulUtils.offsetRect(paramRectF, -(this._screenX + this._rect.left + this._padding.left), -(this._screenY + this._rect.top + this._padding.top));
    float f4;
    if (this._isVertical)
      if (paramRectF.top < this._focusPaddingTop)
      {
        f4 = this._scrollPos + (this._focusPaddingTop - paramRectF.top);
        if (f4 > 0.0F)
          scrollContentTo(0.0F, 0.0F, paramBoolean);
      }
    while (true)
    {
      return true;
      scrollContentTo(0.0F, f4, paramBoolean);
      continue;
      if (paramRectF.bottom <= j - this._focusPaddingBottom)
        break;
      float f3 = this._scrollPos + (j - this._focusPaddingBottom - paramRectF.bottom);
      if (f3 + this._contentMaxHeight >= j)
      {
        scrollContentTo(0.0F, f3, paramBoolean);
      }
      else
      {
        scrollContentTo(0.0F, j - this._contentMaxHeight, paramBoolean);
        continue;
        if (paramRectF.left < this._focusPaddingLeft)
        {
          float f2 = this._scrollPos + (this._focusPaddingLeft - paramRectF.left);
          if (f2 > 0.0F)
            scrollContentTo(0.0F, 0.0F, paramBoolean);
          else
            scrollContentTo(f2, 0.0F, paramBoolean);
        }
        else
        {
          if (paramRectF.right <= i - this._focusPaddingRight)
            break;
          float f1 = this._scrollPos + (i - this._focusPaddingRight - paramRectF.right);
          if (f1 + this._contentMaxWidth >= i)
            scrollContentTo(f1, 0.0F, paramBoolean);
          else
            scrollContentTo(i - this._contentMaxWidth, 0.0F, paramBoolean);
        }
      }
    }
  }

  public boolean onChildStateChanged(XulStateChangeEvent paramXulStateChangeEvent)
  {
    int i = 1;
    if ((this._rect == null) || (this._padding == null))
      i = 0;
    do
    {
      return i;
      if (i != paramXulStateChangeEvent.state)
        break;
    }
    while ((paramXulStateChangeEvent.adjustFocusView) && (onChildFocused(paramXulStateChangeEvent.eventSource, _hasAnimation())));
    while (true)
    {
      return false;
      if ("visibilityChanged".equals(paramXulStateChangeEvent.event))
      {
        _setLayoutChanged();
        updateParentLayout();
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
    if ((!paramBoolean) && (this._scrollAnimation != null))
      removeAnimation(this._scrollAnimation);
  }

  public XulView postFindFocus(XulLayout.FocusDirection paramFocusDirection, RectF paramRectF, XulView paramXulView)
  {
    if (!this._area.hasChild(paramXulView));
    RectF localRectF;
    XulView localXulView1;
    do
    {
      do
      {
        XulView localXulView2;
        do
        {
          XulView localXulView3;
          do
          {
            XulView localXulView4;
            do
            {
              do
                return null;
              while (!this._area.testFocusModeBits(2));
              localRectF = XulDC._tmpFRc0;
              XulUtils.copyRect(paramRectF, localRectF);
              if ((this._isVertical) || (paramFocusDirection != XulLayout.FocusDirection.MOVE_RIGHT))
                break;
              localXulView4 = this._area.getLastFocusableChild();
            }
            while ((localXulView4 != paramXulView) && (!paramXulView.isChildOf(localXulView4)));
            XulUtils.offsetRect(localRectF, -this._contentMaxWidth, 0.0F);
            return this._area.findSubFocusByDirection(paramFocusDirection, localRectF, paramXulView);
            if ((!this._isVertical) || (paramFocusDirection != XulLayout.FocusDirection.MOVE_DOWN))
              break;
            localXulView3 = this._area.getLastFocusableChild();
          }
          while ((localXulView3 != paramXulView) && (!paramXulView.isChildOf(localXulView3)));
          XulUtils.offsetRect(localRectF, 0.0F, -this._contentMaxHeight);
          return this._area.findSubFocusByDirection(paramFocusDirection, localRectF, paramXulView);
          if ((this._isVertical) || (paramFocusDirection != XulLayout.FocusDirection.MOVE_LEFT))
            break;
          localXulView2 = this._area.getFirstFocusableChild();
        }
        while ((localXulView2 != paramXulView) && (!paramXulView.isChildOf(localXulView2)));
        XulUtils.offsetRect(localRectF, this._contentMaxWidth, 0.0F);
        return this._area.findSubFocusByDirection(paramFocusDirection, localRectF, paramXulView);
      }
      while ((!this._isVertical) || (paramFocusDirection != XulLayout.FocusDirection.MOVE_UP));
      localXulView1 = this._area.getFirstFocusableChild();
    }
    while ((localXulView1 != paramXulView) && (!paramXulView.isChildOf(localXulView1)));
    XulUtils.offsetRect(localRectF, 0.0F, this._contentMaxHeight);
    return this._area.findSubFocusByDirection(paramFocusDirection, localRectF, paramXulView);
  }

  public XulView preFindFocus(XulLayout.FocusDirection paramFocusDirection, RectF paramRectF, XulView paramXulView)
  {
    XulView localXulView1;
    if (this._isVertical)
    {
      XulLayout.FocusDirection localFocusDirection3 = XulLayout.FocusDirection.MOVE_LEFT;
      localXulView1 = null;
      if (paramFocusDirection != localFocusDirection3)
      {
        XulLayout.FocusDirection localFocusDirection4 = XulLayout.FocusDirection.MOVE_RIGHT;
        localXulView1 = null;
        if (paramFocusDirection != localFocusDirection4)
          break label66;
      }
    }
    XulLayout.FocusDirection localFocusDirection2;
    do
    {
      XulLayout.FocusDirection localFocusDirection1;
      do
      {
        return localXulView1;
        localFocusDirection1 = XulLayout.FocusDirection.MOVE_UP;
        localXulView1 = null;
      }
      while (paramFocusDirection == localFocusDirection1);
      localFocusDirection2 = XulLayout.FocusDirection.MOVE_DOWN;
      localXulView1 = null;
    }
    while (paramFocusDirection == localFocusDirection2);
    label66: Object localObject = paramXulView;
    XulArea localXulArea1 = ((XulView)localObject).getParent();
    XulArea localXulArea2 = this._area;
    while ((localXulArea1 != localXulArea2) && (localXulArea1 != null))
    {
      localObject = localXulArea1;
      localXulArea1 = ((XulView)localObject).getParent();
    }
    if (localXulArea1 == null)
      return XulItem.NULL_FOCUS;
    int i = localXulArea2.findChildPos((XulElement)localObject);
    int j = localXulArea2.getChildNum();
    int k;
    if ((paramFocusDirection == XulLayout.FocusDirection.MOVE_RIGHT) || (paramFocusDirection == XulLayout.FocusDirection.MOVE_DOWN))
    {
      k = 1;
      if (this._reverseLayout)
        k = -k;
    }
    XulView localXulView2;
    do
    {
      do
      {
        do
        {
          i += k;
          if ((i < 0) || (i >= j))
          {
            return XulItem.NULL_FOCUS;
            k = -1;
            break;
          }
          localXulView1 = localXulArea2.getChild(i);
        }
        while ((localXulView1 == null) || (!localXulView1.isVisible()) || (!localXulView1.isEnabled()));
        if (localXulView1.focusable())
          break;
      }
      while (!(localXulView1 instanceof XulArea));
      localXulView2 = ((XulArea)localXulView1).findSubFocus(paramFocusDirection, paramRectF, paramXulView);
    }
    while (localXulView2 == null);
    return localXulView2;
  }

  public void scrollByPage(int paramInt, boolean paramBoolean)
  {
    if (paramInt == 0)
      return;
    Rect localRect = this._rect;
    if (this._isVertical)
    {
      int i1 = XulUtils.calRectHeight(localRect) - this._padding.top - this._padding.bottom;
      int i2 = i1 * paramInt;
      int i3 = i1 - this._contentMaxHeight;
      int i4 = this._scrollTargetPos - i2;
      label74: XulLayout localXulLayout2;
      XulView localXulView3;
      RectF localRectF3;
      int i5;
      RectF localRectF4;
      XulView localXulView4;
      if (i4 < i3)
      {
        i4 = i3;
        if (i4 == this._scrollTargetPos)
          break label240;
        localXulLayout2 = this._area.getRootLayout();
        localXulView3 = localXulLayout2.getFocus();
        if ((localXulView3 != null) && (localXulView3.isChildOf(this._area)))
        {
          localRectF3 = localXulView3.getFocusRc();
          i5 = this._scrollPos - i4;
          localRectF4 = this._view.getFocusRc();
          if (i5 < 0)
            break label242;
          float f4 = localRectF4.top + i5 - XulUtils.calRectHeight(localRectF3);
          boolean bool4 = f4 < localRectF3.top;
          localXulView4 = null;
          if (bool4)
          {
            localRectF3.offsetTo(localRectF3.left, f4);
            localXulView4 = this._area.findSubFocusByDirection(XulLayout.FocusDirection.MOVE_DOWN, localRectF3, localXulView3);
          }
        }
      }
      while (true)
      {
        if (localXulView4 != null)
          localXulLayout2.requestFocus(localXulView4);
        scrollContentTo(0.0F, i4, paramBoolean);
        return;
        if (i4 <= 0)
          break label74;
        i4 = 0;
        break label74;
        label240: break;
        label242: float f3 = localRectF4.left + i5;
        boolean bool3 = f3 < localRectF3.top;
        localXulView4 = null;
        if (bool3)
        {
          localRectF3.offsetTo(localRectF3.left, f3);
          localXulView4 = this._area.findSubFocusByDirection(XulLayout.FocusDirection.MOVE_UP, localRectF3, localXulView3);
        }
      }
    }
    int i = XulUtils.calRectWidth(localRect) - this._padding.left - this._padding.right;
    int j = i * paramInt;
    int k = i - this._contentMaxWidth;
    int m = this._scrollTargetPos - j;
    label359: XulLayout localXulLayout1;
    XulView localXulView1;
    RectF localRectF1;
    int n;
    RectF localRectF2;
    XulView localXulView2;
    if (m < k)
    {
      m = k;
      if (m == this._scrollTargetPos)
        break label525;
      localXulLayout1 = this._area.getRootLayout();
      localXulView1 = localXulLayout1.getFocus();
      if ((localXulView1 != null) && (localXulView1.isChildOf(this._area)))
      {
        localRectF1 = localXulView1.getFocusRc();
        n = this._scrollPos - m;
        localRectF2 = this._view.getFocusRc();
        if (n < 0)
          break label527;
        float f2 = localRectF2.left + n - XulUtils.calRectWidth(localRectF1);
        boolean bool2 = f2 < localRectF1.left;
        localXulView2 = null;
        if (bool2)
        {
          localRectF1.offsetTo(f2, localRectF1.top);
          localXulView2 = this._area.findSubFocusByDirection(XulLayout.FocusDirection.MOVE_RIGHT, localRectF1, localXulView1);
        }
      }
    }
    while (true)
    {
      if (localXulView2 != null)
        localXulLayout1.requestFocus(localXulView2);
      scrollContentTo(m, 0.0F, paramBoolean);
      return;
      if (m <= 0)
        break label359;
      m = 0;
      break label359;
      label525: break;
      label527: float f1 = localRectF2.left + n;
      boolean bool1 = f1 < localRectF1.left;
      localXulView2 = null;
      if (bool1)
      {
        localRectF1.offsetTo(f1, localRectF1.top);
        localXulView2 = this._area.findSubFocusByDirection(XulLayout.FocusDirection.MOVE_LEFT, localRectF1, localXulView1);
      }
    }
  }

  public void scrollTo(int paramInt, boolean paramBoolean)
  {
    if (paramInt < 0)
      paramInt = 0;
    while (this._isVertical)
    {
      scrollContentTo(0.0F, -paramInt, paramBoolean);
      return;
      int i = getScrollRange();
      if (paramInt > i)
        paramInt = i;
    }
    scrollContentTo(-paramInt, 0.0F, paramBoolean);
  }

  public void syncData()
  {
    if (!_isDataChanged())
      return;
    super.syncData();
    double d1 = this._ctx.getXScalar();
    double d2 = this._ctx.getYScalar();
    XulAttr localXulAttr1 = this._area.getAttr(XulPropNameCache.TagId.AUTO_SCROLL);
    XulPropParser.xulParsedProp_FloatArray localxulParsedProp_FloatArray;
    label124: XulAttr localXulAttr3;
    if ((localXulAttr1 != null) && (!((XulPropParser.xulParsedProp_booleanValue)localXulAttr1.getParsedValue()).val))
    {
      this._autoScroll = false;
      if (!this._autoScroll)
        break label471;
      XulAttr localXulAttr5 = this._area.getAttr(XulPropNameCache.TagId.LOCK_FOCUS);
      if (localXulAttr5 == null)
        break label458;
      localxulParsedProp_FloatArray = (XulPropParser.xulParsedProp_FloatArray)localXulAttr5.getParsedValue();
      if ((localxulParsedProp_FloatArray != null) && (localxulParsedProp_FloatArray.getLength() >= 1))
        break label400;
      this._lockFocus = false;
      this._lockFocusDynamic = false;
      XulAttr localXulAttr2 = this._area.getAttr(XulPropNameCache.TagId.LOOP);
      boolean bool1 = false;
      if (localXulAttr2 != null)
      {
        boolean bool2 = ((XulPropParser.xulParsedProp_booleanValue)localXulAttr2.getParsedValue()).val;
        bool1 = false;
        if (bool2)
          bool1 = true;
      }
      if (this._loopMode != bool1)
      {
        this._loopMode = bool1;
        if (this._loopMode)
          setUpdateLayout();
      }
      localXulAttr3 = this._area.getAttr(XulPropNameCache.TagId.SCROLLBAR);
      if ((localXulAttr3 != null) && (localXulAttr3.getValue() != null))
        break label484;
      this._scrollBar = null;
      label225: XulAttr localXulAttr4 = this._area.getAttr(XulPropNameCache.TagId.INDICATOR);
      if ((localXulAttr4 != null) && (((XulPropParser.xulParsedProp_booleanValue)localXulAttr4.getParsedValue()).val))
        break label508;
      this._leftIndicator = null;
      this._rightIndicator = null;
      this._upIndicator = null;
      this._downIndicator = null;
      label276: XulStyle localXulStyle = this._area.getStyle(XulPropNameCache.TagId.PREFERRED_FOCUS_PADDING);
      if (localXulStyle == null)
        break label655;
      XulPropParser.xulParsedProp_PaddingMargin localxulParsedProp_PaddingMargin = (XulPropParser.xulParsedProp_PaddingMargin)localXulStyle.getParsedValue();
      this._focusPaddingTop = XulUtils.roundToInt(d2 * localxulParsedProp_PaddingMargin.top);
      this._focusPaddingLeft = XulUtils.roundToInt(d1 * localxulParsedProp_PaddingMargin.left);
      this._focusPaddingRight = XulUtils.roundToInt(d1 * localxulParsedProp_PaddingMargin.right);
      this._focusPaddingBottom = XulUtils.roundToInt(d2 * localxulParsedProp_PaddingMargin.bottom);
      label363: if (!"false".equals(this._area.getStyleString(XulPropNameCache.TagId.CLIP_CHILDREN)))
        break label678;
      this._clipChildren = false;
    }
    while (true)
    {
      startAnimation();
      return;
      this._autoScroll = true;
      break;
      label400: this._lockFocus = true;
      this._lockFocusAlignment = localxulParsedProp_FloatArray.tryGetVal(0, (0.0F / 0.0F));
      this._lockFocusChildAlignment = localxulParsedProp_FloatArray.tryGetVal(1, (0.0F / 0.0F));
      if (this._lockFocusAlignment == -1.0F);
      for (boolean bool3 = true; ; bool3 = false)
      {
        this._lockFocusDynamic = bool3;
        break;
      }
      label458: this._lockFocus = false;
      this._lockFocusDynamic = false;
      break label124;
      label471: this._lockFocus = false;
      this._lockFocusDynamic = false;
      break label124;
      label484: this._scrollBar = SimpleScrollBar.buildScrollBar(this._scrollBar, localXulAttr3.getStringValue(), _getScrollBarHelper(), this);
      break label225;
      label508: String[] arrayOfString1 = this._area.getAttrString(XulPropNameCache.TagId.INDICATOR_LEFT).split(",");
      this._leftIndicator = _createIndicator(this._leftIndicator, arrayOfString1, 0.0F, 0.5F);
      String[] arrayOfString2 = this._area.getAttrString(XulPropNameCache.TagId.INDICATOR_RIGHT).split(",");
      this._rightIndicator = _createIndicator(this._rightIndicator, arrayOfString2, 1.0F, 0.5F);
      String[] arrayOfString3 = this._area.getAttrString(XulPropNameCache.TagId.INDICATOR_UP).split(",");
      this._upIndicator = _createIndicator(this._upIndicator, arrayOfString3, 0.5F, 0.0F);
      String[] arrayOfString4 = this._area.getAttrString(XulPropNameCache.TagId.INDICATOR_DOWN).split(",");
      this._downIndicator = _createIndicator(this._downIndicator, arrayOfString4, 0.5F, 1.0F);
      break label276;
      label655: this._focusPaddingTop = 0;
      this._focusPaddingLeft = 0;
      this._focusPaddingRight = 0;
      this._focusPaddingBottom = 0;
      break label363;
      label678: this._clipChildren = true;
      if ("false".equals(this._area.getStyleString(XulPropNameCache.TagId.CLIP_FOCUS)))
        this._clipFocus = false;
      else
        this._clipFocus = true;
    }
  }

  protected class LayoutContainer extends XulViewContainerBaseRender.LayoutContainer
  {
    int _initialContentHeight = 2147483647;
    int _initialContentWidth = 2147483647;
    int _lastScrollTargetPos = 2147483647;
    Runnable _scheduledLockFocus;

    protected LayoutContainer()
    {
      super();
    }

    public int doFinal()
    {
      int i = 1;
      int j = super.doFinal();
      XulSliderAreaRender.this._adjustLoopingContent();
      XulView localXulView1;
      int k;
      XulView localXulView2;
      if (XulSliderAreaRender.this._lockFocus)
      {
        if (!XulSliderAreaRender.this._isVertical)
          break label191;
        if (this._initialContentHeight == XulSliderAreaRender.this._contentMaxHeight)
          break label186;
        localXulView1 = XulSliderAreaRender.this._view.getRootLayout().getFocus();
        if ((localXulView1 != null) && (localXulView1.isChildOf(XulSliderAreaRender.this._view)))
        {
          if (i == 0)
            break label210;
          k = 3;
          localXulView2 = XulSliderAreaRender.this._area.getFirstChild();
        }
      }
      while (true)
      {
        XulSliderAreaRender.this.onChildFocused(localXulView1, false);
        int m = k - 1;
        if ((k <= 0) || (localXulView2 == XulSliderAreaRender.this._area.getFirstChild()))
          while (true)
          {
            if ((this._lastScrollTargetPos != XulSliderAreaRender.this._scrollTargetPos) || (XulSliderAreaRender.this._scrollPos != XulSliderAreaRender.this._scrollTargetPos))
            {
              this._lastScrollTargetPos = XulSliderAreaRender.this._scrollTargetPos;
              XulSliderAreaRender.this.startAnimation();
            }
            return j;
            label186: i = 0;
            break;
            label191: if (this._initialContentWidth != XulSliderAreaRender.this._contentMaxWidth)
              break;
            i = 0;
            break;
            label210: scheduleLockFocus();
          }
        k = m;
      }
    }

    public int getAlignmentOffsetX()
    {
      return XulSliderAreaRender.this._alignXOffset;
    }

    public int getAlignmentOffsetY()
    {
      return XulSliderAreaRender.this._alignYOffset;
    }

    public float getAlignmentX()
    {
      if (XulSliderAreaRender.this._xAlign != 0.0F)
        return XulSliderAreaRender.this._xAlign;
      return super.getAlignmentX();
    }

    public float getAlignmentY()
    {
      if (XulSliderAreaRender.this._yAlign != 0.0F)
        return XulSliderAreaRender.this._yAlign;
      return super.getAlignmentY();
    }

    public int getChildNum()
    {
      return XulSliderAreaRender.this._area.getChildNum();
    }

    public int getContentOffsetX()
    {
      if (!XulSliderAreaRender.this._isVertical)
        return XulSliderAreaRender.this._scrollPos;
      return 0;
    }

    public int getContentOffsetY()
    {
      if (XulSliderAreaRender.this._isVertical)
        return XulSliderAreaRender.this._scrollPos;
      return 0;
    }

    public int getLayoutMode()
    {
      if (XulSliderAreaRender.this._isVertical)
      {
        if (XulSliderAreaRender.this._reverseLayout)
          return 16777218;
        return 2;
      }
      if (XulSliderAreaRender.this._reverseLayout)
        return 16777217;
      return 1;
    }

    public int getOffsetX()
    {
      if (!XulSliderAreaRender.this._isVertical)
        return XulSliderAreaRender.this._scrollPos + XulSliderAreaRender.this._alignXOffset;
      return XulSliderAreaRender.this._alignXOffset;
    }

    public int getOffsetY()
    {
      if (XulSliderAreaRender.this._isVertical)
        return XulSliderAreaRender.this._scrollPos + XulSliderAreaRender.this._alignYOffset;
      return XulSliderAreaRender.this._alignYOffset;
    }

    public int prepare()
    {
      super.prepare();
      this._initialContentWidth = XulSliderAreaRender.this._contentMaxWidth;
      this._initialContentHeight = XulSliderAreaRender.this._contentMaxHeight;
      if (!XulSliderAreaRender.this._isLayoutChangedByChild())
      {
        XulSliderAreaRender.this.syncAlignInfo();
        XulSliderAreaRender.this.syncDirection();
      }
      return 0;
    }

    void scheduleLockFocus()
    {
      if (this._scheduledLockFocus == null)
        this._scheduledLockFocus = new Runnable()
        {
          public void run()
          {
            XulLayout localXulLayout = XulSliderAreaRender.this._view.getRootLayout();
            if (localXulLayout == null);
            XulView localXulView;
            do
            {
              return;
              localXulView = localXulLayout.getFocus();
            }
            while ((localXulView == null) || (!localXulView.isChildOf(XulSliderAreaRender.this._area)));
            XulSliderAreaRender.this.onChildFocused(localXulView, XulSliderAreaRender.this._hasAnimation());
          }
        };
      XulRenderContext localXulRenderContext = XulSliderAreaRender.this.getRenderContext();
      if (localXulRenderContext == null)
        return;
      localXulRenderContext.scheduleLayoutFinishedTask(this._scheduledLockFocus);
    }

    public int setAlignmentOffset(int paramInt1, int paramInt2)
    {
      XulSliderAreaRender.this._alignXOffset = paramInt1;
      XulSliderAreaRender.this._alignYOffset = paramInt2;
      return 0;
    }

    public int setContentSize(int paramInt1, int paramInt2)
    {
      XulSliderAreaRender.this._contentMaxWidth = paramInt1;
      XulSliderAreaRender.this._contentMaxHeight = paramInt2;
      return 0;
    }

    public boolean updateContentSize()
    {
      return true;
    }
  }

  private class ScrollTransformAnimation extends SimpleTransformAnimation
  {
    public ScrollTransformAnimation(XulViewRender arg2)
    {
      super();
    }

    public ScrollTransformAnimation(XulViewRender paramITransformer, ITransformer arg3)
    {
      super(localITransformer);
    }

    private boolean oldUpdateValue()
    {
      float f = XulSliderAreaRender.this._scrollTargetPos - XulSliderAreaRender.this._scrollPos;
      if (f == 0.0F)
        return true;
      if (Math.abs(f) >= 4.0F)
        f /= 2.0F;
      this._val = (f + XulSliderAreaRender.this._scrollPos);
      return false;
    }

    public float getScalar()
    {
      if (XulSliderAreaRender.this._isVertical)
        return (float)XulSliderAreaRender.this.getYScalar();
      return (float)XulSliderAreaRender.this.getXScalar();
    }

    public void offsetVal(float paramFloat)
    {
      this._srcVal = (paramFloat + this._srcVal);
      this._destVal = (paramFloat + this._destVal);
      this._val = (paramFloat + this._val);
    }

    public void onAnimationStop()
    {
      super.onAnimationStop();
      XulSliderAreaRender.this._notifyScrollStop();
    }

    public void restoreValue()
    {
      int i = (int)(this._val - XulSliderAreaRender.this._scrollPos);
      if (i == 0)
        return;
      XulSliderAreaRender localXulSliderAreaRender = XulSliderAreaRender.this;
      localXulSliderAreaRender._scrollPos = (i + localXulSliderAreaRender._scrollPos);
      if (XulSliderAreaRender.this._isVertical)
        XulLayoutHelper.offsetChild(XulSliderAreaRender.this.getLayoutElement(), 0, i);
      while (true)
      {
        XulSliderAreaRender.this._adjustLoopingContent();
        return;
        XulLayoutHelper.offsetChild(XulSliderAreaRender.this.getLayoutElement(), i, 0);
      }
    }

    public void storeDest()
    {
      this._destVal = XulSliderAreaRender.this._scrollTargetPos;
    }

    public void storeSrc()
    {
      this._srcVal = XulSliderAreaRender.this._scrollPos;
    }

    public boolean updateValue(long paramLong)
    {
      if (XulSliderAreaRender.this._aniTransformer == null)
        return oldUpdateValue();
      return super.updateValue(paramLong);
    }
  }

  private class _SliderIndicator extends XulRenderDrawableItem
  {
    XulDrawable _bmp;
    int _height = 0;
    volatile boolean _isLoading = false;
    volatile boolean _isRecycled = false;
    volatile long _lastDownloadFailedTime = 0L;
    String _url;
    int _width = 0;
    float _xAlign;
    float _yAlign;

    public _SliderIndicator(String[] paramFloat1, float paramFloat2, float arg4)
    {
      Object localObject;
      update(paramFloat1, paramFloat2, localObject);
    }

    private void update(String[] paramArrayOfString, float paramFloat1, float paramFloat2)
    {
      this._xAlign = paramFloat1;
      this._yAlign = paramFloat2;
      String str = this._url;
      switch (paramArrayOfString.length)
      {
      case 2:
      case 4:
      default:
      case 1:
      case 3:
      case 5:
      }
      while (true)
      {
        if ((str == null) || (!str.equals(this._url)))
        {
          this._url = str;
          this._bmp = null;
        }
        if (this._width != 0)
          this._width = XulUtils.roundToInt(this._width * XulSliderAreaRender.this._ctx.getXScalar());
        if (this._height != 0)
          this._height = XulUtils.roundToInt(this._height * XulSliderAreaRender.this._ctx.getYScalar());
        return;
        str = paramArrayOfString[0];
        continue;
        this._width = XulUtils.tryParseInt(paramArrayOfString[0]);
        this._height = XulUtils.tryParseInt(paramArrayOfString[1]);
        str = paramArrayOfString[2];
        continue;
        this._width = XulUtils.tryParseInt(paramArrayOfString[0]);
        this._height = XulUtils.tryParseInt(paramArrayOfString[1]);
        this._xAlign = XulUtils.tryParseFloat(paramArrayOfString[2], paramFloat1);
        this._yAlign = XulUtils.tryParseFloat(paramArrayOfString[3], paramFloat2);
        str = paramArrayOfString[4];
      }
    }

    public void onImageReady(XulDrawable paramXulDrawable)
    {
      if (this._isRecycled)
        return;
      this._isLoading = false;
      this._bmp = paramXulDrawable;
      if (paramXulDrawable == null);
      for (this._lastDownloadFailedTime = XulUtils.timestamp(); ; this._lastDownloadFailedTime = 0L)
      {
        XulSliderAreaRender.this.markDirtyView();
        super.onImageReady(paramXulDrawable);
        return;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.XulSliderAreaRender
 * JD-Core Version:    0.6.2
 */