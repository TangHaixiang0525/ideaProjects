package com.starcor.xul.Render;

import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.starcor.xul.Events.XulActionEvent;
import com.starcor.xul.Events.XulStateChangeEvent;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Graphics.XulDrawable;
import com.starcor.xul.IXulExternalView;
import com.starcor.xul.Prop.XulAction;
import com.starcor.xul.Prop.XulAttr;
import com.starcor.xul.Prop.XulPropNameCache.TagId;
import com.starcor.xul.Prop.XulStyle;
import com.starcor.xul.Render.Drawer.IXulAnimation;
import com.starcor.xul.Render.Effect.QuiverAnimation;
import com.starcor.xul.Render.Effect.SimpleTransformAnimation;
import com.starcor.xul.Render.Effect.TransformAnimation;
import com.starcor.xul.Render.Effect.TransformAnimation.TransformValues;
import com.starcor.xul.Render.Transform.ITransformer;
import com.starcor.xul.Render.Transform.TransformerFactory;
import com.starcor.xul.Utils.XulLayoutHelper;
import com.starcor.xul.Utils.XulLayoutHelper.ILayoutContainer;
import com.starcor.xul.Utils.XulLayoutHelper.ILayoutElement;
import com.starcor.xul.Utils.XulPropParser.xulParsedAttr_AnimationMode;
import com.starcor.xul.Utils.XulPropParser.xulParsedAttr_Enabled;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_FloatArray;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_Integer;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_PaddingMargin;
import com.starcor.xul.Utils.XulPropParser.xulParsedProp_booleanValue;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_BackgroundColor;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_BackgroundImage;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_Border;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_Border_Dash_Pattern;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_PaddingMarginVal;
import com.starcor.xul.Utils.XulPropParser.xulParsedStyle_Scale;
import com.starcor.xul.Utils.XulRenderDrawableItem;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulLayout.FocusDirection;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulTaskCollector;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker;
import com.starcor.xul.XulWorker.DrawableItem;
import java.util.ArrayList;

public abstract class XulViewRender
{
  public static final int FLAGS_ANIMATION = 32768;
  public static final int FLAGS_CHILDREN_LAYOUT_CHANGED = 32;
  public static final int FLAGS_DATA_CHANGED = 64;
  public static final int FLAGS_DATA_INITIALED = 2;
  public static final int FLAGS_ENABLE = 131072;
  public static final int FLAGS_INITIALIZED = 3;
  public static final int FLAGS_INITIAL_PRELOAD = 1024;
  public static final int FLAGS_KEEP_FOCUS_VISIBLE = 512;
  public static final int FLAGS_LAYOUT_CHANGED = 16;
  public static final int FLAGS_LAYOUT_INITIALIZED = 1;
  public static final int FLAGS_MOVING_ANIMATION = 8192;
  public static final int FLAGS_PENDING_ITEMS_LOADED = 4;
  public static final int FLAGS_PRELOAD = 2048;
  public static final int FLAGS_RIGHT_TO_LEFT = 8;
  public static final int FLAGS_SCALED_RECT_CHANGED = 256;
  public static final int FLAGS_SCALE_ANIMATION = 16384;
  public static final int FLAGS_SIZING_ANIMATION = 4096;
  public static final int FLAGS_UPDATE_HEIGHT = 524288;
  public static final int FLAGS_UPDATE_WIDTH = 262144;
  public static final int FLAGS_USER_FLAGS_BASE = 1048576;
  public static final int FLAGS_VIEW_CHANGED_MASK = 240;
  public static final int FLAGS_VISIBLE = 65536;
  private static final String TAG = XulViewRender.class.getSimpleName();
  private static final Rect _DUMMY_PADDING_ = new Rect(0, 0, 0, 0);
  static XulStateChangeEvent _stateChangeEvent = new XulStateChangeEvent();
  protected int _aniTransformDuration;
  protected ITransformer _aniTransformer;
  private RectF _animRect;
  private int _bkgColor = 0;
  private BkgDrawableInfo _bkgInfo;
  private ArrayList<String[]> _blinkClassStack;
  protected int _borderColor = 0;
  protected DashPathEffect _borderEffect = null;
  protected float _borderPos = 0.5F;
  protected float _borderRoundX = 0.0F;
  protected float _borderRoundY = 0.0F;
  protected float _borderSize = 0.0F;
  protected XulRenderContext _ctx;
  long _dirtyTimestamp;
  protected int _flags = 246016;
  private RectF _focusRect;
  private XulLayoutHelper.ILayoutElement _layoutElement;
  protected Rect _margin;
  protected Rect _padding;
  private _QuiverInfo _quiver = null;
  protected Rect _rect;
  protected float _scalarX = 1.0F;
  protected float _scalarXAlign = 0.5F;
  protected float _scalarY = 1.0F;
  protected float _scalarYAlign = 0.5F;
  private RectF _scaledRect;
  protected int _screenX = 0;
  protected int _screenY = 0;
  private SizingMovingAnimation _sizingMovingAnimation;
  protected TransformAnimation _transformAnimation;
  private RectF _updateRect;
  protected XulView _view;
  protected int _zIndex = 0;

  public XulViewRender(XulRenderContext paramXulRenderContext, XulView paramXulView)
  {
    this._ctx = paramXulRenderContext;
    this._view = paramXulView;
  }

  private void _notifyReady()
  {
    final XulAction localXulAction = this._view.getAction(XulPropNameCache.TagId.ACTION_READY);
    if (localXulAction != null)
      getRenderContext().scheduleLayoutFinishedTask(new Runnable()
      {
        public void run()
        {
          XulViewRender.this._view.getOwnerPage();
          XulPage.invokeActionNoPopup(XulViewRender.this._view, localXulAction);
        }
      });
  }

  private void finalizeSizingMovingAnimation()
  {
    if ((!_hasSizingMovingAnimation()) || (this._aniTransformDuration < 1) || (_isInvisible()));
    int i;
    int j;
    do
    {
      do
      {
        do
          return;
        while (this._sizingMovingAnimation == null);
        this._sizingMovingAnimation.startAnimation();
      }
      while (!this._sizingMovingAnimation.isRunning());
      i = this._rect.left;
      j = this._rect.top;
      this._sizingMovingAnimation.restoreValue();
    }
    while ((!(this instanceof XulViewContainerBaseRender)) || ((i == this._rect.left) && (j == this._rect.top)));
    XulLayoutHelper.offsetChild((XulLayoutHelper.ILayoutContainer)getLayoutElement(), this._rect.left - i, this._rect.top - j);
  }

  private void initTransformAnimation(boolean paramBoolean)
  {
    if (this._transformAnimation == null)
    {
      this._transformAnimation = new TransformAnimation(this);
      collectTransformValues(this._transformAnimation);
    }
    this._transformAnimation.setTransformer(this._aniTransformer);
    this._transformAnimation.startAnimation(this._aniTransformDuration, paramBoolean);
  }

  private void prepareSizingMovingAnimation()
  {
    if (!_hasSizingMovingAnimation())
      return;
    if (this._sizingMovingAnimation == null)
      this._sizingMovingAnimation = new SizingMovingAnimation(this);
    this._sizingMovingAnimation.setTransformer(this._aniTransformer);
    this._sizingMovingAnimation.prepareAnimation(this._aniTransformDuration);
  }

  private void syncAnimationInfo()
  {
    if (!_isDataChanged())
      return;
    XulAttr localXulAttr1 = this._view.getAttr(XulPropNameCache.TagId.ANIMATION);
    label70: label99: int i;
    XulPropParser.xulParsedAttr_AnimationMode localxulParsedAttr_AnimationMode2;
    if ((localXulAttr1 != null) && ("disabled".equals(localXulAttr1.getStringValue())))
    {
      _setHasAnimation(false);
      XulAttr localXulAttr2 = this._view.getAttr(XulPropNameCache.TagId.ANIMATION_SIZING);
      if (localXulAttr2 == null)
        break label236;
      _setHasSizingAnimation(((XulPropParser.xulParsedProp_booleanValue)localXulAttr2.getParsedValue()).val);
      XulAttr localXulAttr3 = this._view.getAttr(XulPropNameCache.TagId.ANIMATION_MOVING);
      if (localXulAttr3 == null)
        break label244;
      _setHasMovingAnimation(((XulPropParser.xulParsedProp_booleanValue)localXulAttr3.getParsedValue()).val);
      XulAttr localXulAttr4 = this._view.getAttr(XulPropNameCache.TagId.ANIMATION_DURATION);
      XulAttr localXulAttr5 = this._view.getAttr(XulPropNameCache.TagId.ANIMATION_MODE);
      i = 100;
      if (localXulAttr4 != null)
        i = XulUtils.tryParseInt(localXulAttr4.getStringValue());
      if (!_hasAnimation())
        i = 0;
      if (localXulAttr5 == null)
        break label293;
      localxulParsedAttr_AnimationMode2 = (XulPropParser.xulParsedAttr_AnimationMode)localXulAttr5.getParsedValue();
      if (localxulParsedAttr_AnimationMode2.mode == null)
        break label285;
      if (this._aniTransformer != null)
        break label252;
      this._aniTransformer = TransformerFactory.createTransformer(localxulParsedAttr_AnimationMode2.mode, localxulParsedAttr_AnimationMode2.params);
    }
    XulStyle localXulStyle1;
    while (true)
    {
      this._aniTransformDuration = i;
      localXulStyle1 = this._view.getStyle(XulPropNameCache.TagId.QUIVER);
      if (localXulStyle1 != null)
        break label301;
      this._quiver = null;
      return;
      _setHasAnimation(true);
      break;
      label236: _setHasSizingAnimation(false);
      break label70;
      label244: _setHasMovingAnimation(false);
      break label99;
      label252: this._aniTransformer.switchParams(localxulParsedAttr_AnimationMode2.params);
      this._aniTransformer.switchAlgorithm(localxulParsedAttr_AnimationMode2.mode);
      continue;
      label285: this._aniTransformer = null;
      continue;
      label293: this._aniTransformer = null;
    }
    label301: XulStyle localXulStyle2 = this._view.getStyle(XulPropNameCache.TagId.QUIVER_MODE);
    String str = "pow";
    float[] arrayOfFloat = QuiverAnimation.DEFAULT_PARAMS;
    if (localXulStyle2 != null)
    {
      XulPropParser.xulParsedAttr_AnimationMode localxulParsedAttr_AnimationMode1 = (XulPropParser.xulParsedAttr_AnimationMode)localXulStyle2.getParsedValue();
      str = localxulParsedAttr_AnimationMode1.mode;
      arrayOfFloat = localxulParsedAttr_AnimationMode1.params;
    }
    XulPropParser.xulParsedProp_FloatArray localxulParsedProp_FloatArray = (XulPropParser.xulParsedProp_FloatArray)localXulStyle1.getParsedValue();
    if (localxulParsedProp_FloatArray.getLength() >= 2)
    {
      float f1 = localxulParsedProp_FloatArray.tryGetVal(0, 0.0F);
      float f2 = localxulParsedProp_FloatArray.tryGetVal(1, 0.0F);
      int j = (int)localxulParsedProp_FloatArray.tryGetVal(2, 480.0F);
      if ((Math.abs(f1) < 1.0E-005F) && (Math.abs(f2) < 1.0E-005F))
      {
        this._quiver = null;
        return;
      }
      if (this._quiver == null)
      {
        this._quiver = new _QuiverInfo(f1, f2, j, str, arrayOfFloat);
        addAnimation(new QuiverAnimation(this, f1, f2)
        {
          public boolean doQuiver(long paramAnonymousLong, float paramAnonymousFloat1, float paramAnonymousFloat2)
          {
            if (XulViewRender.this._quiver == null)
              return false;
            if ((XulViewRender.this._quiver._duration > 0) && (XulViewRender.this._quiver._duration < paramAnonymousLong))
            {
              XulViewRender.access$102(XulViewRender.this, null);
              XulViewRender.this.markDirtyView();
              return false;
            }
            int i = XulUtils.calRectWidth(XulViewRender.this._rect);
            int j = XulUtils.calRectHeight(XulViewRender.this._rect);
            float f1 = Math.abs(XulViewRender.this._quiver._quiverX);
            float f2;
            if (f1 > 1.0F)
            {
              XulViewRender.this._quiver._quiverXDelta = ((float)(paramAnonymousFloat1 * XulViewRender.this.getXScalar()));
              f2 = Math.abs(XulViewRender.this._quiver._quiverY);
              if (f2 <= 1.0F)
                break label219;
              XulViewRender.this._quiver._quiverYDelta = ((float)(paramAnonymousFloat2 * XulViewRender.this.getYScalar()));
            }
            while (true)
            {
              XulViewRender.this.markDirtyView();
              return true;
              if (f1 > 1.0E-005F)
              {
                XulViewRender.this._quiver._quiverXDelta = (paramAnonymousFloat1 * i);
                break;
              }
              XulViewRender.this._quiver._quiverXDelta = 0.0F;
              break;
              label219: if (f2 > 1.0E-005F)
                XulViewRender.this._quiver._quiverYDelta = (paramAnonymousFloat2 * j);
              else
                XulViewRender.this._quiver._quiverYDelta = 0.0F;
            }
          }

          public boolean updateAnimation(long paramAnonymousLong)
          {
            if (XulViewRender.this._quiver == null)
            {
              XulViewRender.this.onAnimationFinished(false);
              return false;
            }
            switchMode(XulViewRender.this._quiver._mode, XulViewRender.this._quiver._params);
            updateDuration(XulViewRender.this._quiver._duration);
            return super.updateAnimation(paramAnonymousLong);
          }
        });
        return;
      }
      this._quiver._quiverX = f1;
      this._quiver._quiverY = f2;
      this._quiver._duration = j;
      this._quiver._mode = str;
      this._quiver._params = arrayOfFloat;
      return;
    }
    this._quiver = null;
  }

  private boolean syncLayoutParameters()
  {
    if (!_isLayoutChanged())
      return true;
    if (_isInvisible())
      return true;
    boolean bool1 = _isEnabled();
    boolean bool2 = true;
    XulAttr localXulAttr = this._view.getAttr(XulPropNameCache.TagId.ENABLED);
    if (localXulAttr != null)
      bool2 = ((XulPropParser.xulParsedAttr_Enabled)localXulAttr.getParsedValue()).val;
    if (bool2 != bool1)
    {
      _setEnabled(bool2);
      onUsabilityChanged(bool2, this._view);
    }
    double d1 = this._ctx.getXScalar();
    double d2 = this._ctx.getYScalar();
    XulStyle localXulStyle1 = this._view.getStyle(XulPropNameCache.TagId.PADDING_LEFT);
    XulStyle localXulStyle2 = this._view.getStyle(XulPropNameCache.TagId.PADDING_TOP);
    XulStyle localXulStyle3 = this._view.getStyle(XulPropNameCache.TagId.PADDING_RIGHT);
    XulStyle localXulStyle4 = this._view.getStyle(XulPropNameCache.TagId.PADDING_BOTTOM);
    if (this._padding == null)
      this._padding = new Rect();
    XulStyle localXulStyle5;
    if ((localXulStyle1 != null) && (localXulStyle3 != null) && (localXulStyle2 != null))
    {
      localXulStyle5 = null;
      if (localXulStyle4 != null);
    }
    else
    {
      localXulStyle5 = this._view.getStyle(XulPropNameCache.TagId.PADDING);
    }
    Rect localRect1 = this._padding;
    int i;
    int j;
    label226: int k;
    label247: int m;
    label268: XulStyle localXulStyle6;
    XulStyle localXulStyle7;
    XulStyle localXulStyle8;
    XulStyle localXulStyle9;
    XulStyle localXulStyle10;
    int i4;
    label718: int i5;
    if (localXulStyle1 == null)
    {
      i = 0;
      localRect1.left = i;
      Rect localRect2 = this._padding;
      if (localXulStyle2 != null)
        break label903;
      j = 0;
      localRect2.top = j;
      Rect localRect3 = this._padding;
      if (localXulStyle3 != null)
        break label926;
      k = 0;
      localRect3.right = k;
      Rect localRect4 = this._padding;
      if (localXulStyle4 != null)
        break label949;
      m = 0;
      localRect4.bottom = m;
      if (localXulStyle5 != null)
      {
        XulPropParser.xulParsedProp_PaddingMargin localxulParsedProp_PaddingMargin2 = (XulPropParser.xulParsedProp_PaddingMargin)localXulStyle5.getParsedValue();
        if ((localXulStyle1 == null) || (localXulStyle1.getPriority() < localXulStyle5.getPriority()))
          this._padding.left = XulUtils.roundToInt(d1 * localxulParsedProp_PaddingMargin2.left);
        if ((localXulStyle2 == null) || (localXulStyle2.getPriority() < localXulStyle5.getPriority()))
          this._padding.top = XulUtils.roundToInt(d2 * localxulParsedProp_PaddingMargin2.top);
        if ((localXulStyle3 == null) || (localXulStyle3.getPriority() < localXulStyle5.getPriority()))
          this._padding.right = XulUtils.roundToInt(d1 * localxulParsedProp_PaddingMargin2.right);
        if ((localXulStyle4 == null) || (localXulStyle4.getPriority() < localXulStyle5.getPriority()))
          this._padding.bottom = XulUtils.roundToInt(d2 * localxulParsedProp_PaddingMargin2.bottom);
      }
      localXulStyle6 = this._view.getStyle(XulPropNameCache.TagId.MARGIN_LEFT);
      localXulStyle7 = this._view.getStyle(XulPropNameCache.TagId.MARGIN_TOP);
      localXulStyle8 = this._view.getStyle(XulPropNameCache.TagId.MARGIN_RIGHT);
      localXulStyle9 = this._view.getStyle(XulPropNameCache.TagId.MARGIN_BOTTOM);
      if ((localXulStyle6 != null) && (localXulStyle8 != null) && (localXulStyle7 != null))
      {
        localXulStyle10 = null;
        if (localXulStyle9 != null);
      }
      else
      {
        localXulStyle10 = this._view.getStyle(XulPropNameCache.TagId.MARGIN);
      }
      if ((localXulStyle6 != null) || (localXulStyle8 != null) || (localXulStyle7 != null) || (localXulStyle9 != null) || (localXulStyle10 != null))
        break label972;
      if (this._margin != null)
        this._margin.set(0, 0, 0, 0);
      if (this._rect == null)
        this._rect = new Rect();
      _setScaledRectChanged();
      this._rect.left = this._view.getX();
      this._rect.top = this._view.getY();
      if (this._rect.left > 2147483547)
        this._rect.left = 0;
      if (this._rect.top > 2147483547)
        this._rect.top = 0;
      i4 = this._view.getWidth();
      if (i4 >= 2147483547)
        break label1332;
      this._rect.right = XulUtils.roundToInt(d1 * (i4 + this._rect.left));
      this._rect.left = XulUtils.roundToInt(d1 * this._rect.left);
      i5 = this._view.getHeight();
      if (i5 >= 2147483547)
        break label1373;
      this._rect.bottom = XulUtils.roundToInt(d2 * (i5 + this._rect.top));
      this._rect.top = XulUtils.roundToInt(d2 * this._rect.top);
      label780: if (i4 != 2147483644)
        break label1414;
      _addFlags(262144);
      label794: if (i5 != 2147483644)
        break label1423;
      _addFlags(524288);
      label808: if (!"rtl".equals(this._view.getStyleString(XulPropNameCache.TagId.LAYOUT_MODE)))
        break label1432;
      _setRTL(true);
    }
    while (true)
    {
      if ((_isRTL()) && (this._padding != null))
      {
        int i6 = this._padding.left;
        this._padding.left = this._padding.right;
        this._padding.right = i6;
      }
      return false;
      i = XulUtils.roundToInt(d1 * ((XulPropParser.xulParsedStyle_PaddingMarginVal)localXulStyle1.getParsedValue()).val);
      break;
      label903: j = XulUtils.roundToInt(d2 * ((XulPropParser.xulParsedStyle_PaddingMarginVal)localXulStyle2.getParsedValue()).val);
      break label226;
      label926: k = XulUtils.roundToInt(d1 * ((XulPropParser.xulParsedStyle_PaddingMarginVal)localXulStyle3.getParsedValue()).val);
      break label247;
      label949: m = XulUtils.roundToInt(d2 * ((XulPropParser.xulParsedStyle_PaddingMarginVal)localXulStyle4.getParsedValue()).val);
      break label268;
      label972: if (this._margin == null)
        this._margin = new Rect();
      Rect localRect5 = this._margin;
      int n;
      label1004: int i1;
      label1025: int i2;
      label1046: Rect localRect8;
      if (localXulStyle6 == null)
      {
        n = 0;
        localRect5.left = n;
        Rect localRect6 = this._margin;
        if (localXulStyle7 != null)
          break label1263;
        i1 = 0;
        localRect6.top = i1;
        Rect localRect7 = this._margin;
        if (localXulStyle8 != null)
          break label1286;
        i2 = 0;
        localRect7.right = i2;
        localRect8 = this._margin;
        if (localXulStyle9 != null)
          break label1309;
      }
      label1286: label1309: for (int i3 = 0; ; i3 = XulUtils.roundToInt(d2 * ((XulPropParser.xulParsedStyle_PaddingMarginVal)localXulStyle9.getParsedValue()).val))
      {
        localRect8.bottom = i3;
        if (localXulStyle10 == null)
          break;
        XulPropParser.xulParsedProp_PaddingMargin localxulParsedProp_PaddingMargin1 = (XulPropParser.xulParsedProp_PaddingMargin)localXulStyle10.getParsedValue();
        if ((localXulStyle6 == null) || (localXulStyle6.getPriority() < localXulStyle10.getPriority()))
          this._margin.left = XulUtils.roundToInt(d1 * localxulParsedProp_PaddingMargin1.left);
        if ((localXulStyle7 == null) || (localXulStyle7.getPriority() < localXulStyle10.getPriority()))
          this._margin.top = XulUtils.roundToInt(d2 * localxulParsedProp_PaddingMargin1.top);
        if ((localXulStyle8 == null) || (localXulStyle8.getPriority() < localXulStyle10.getPriority()))
          this._margin.right = XulUtils.roundToInt(d1 * localxulParsedProp_PaddingMargin1.right);
        if ((localXulStyle9 != null) && (localXulStyle9.getPriority() >= localXulStyle10.getPriority()))
          break;
        this._margin.bottom = XulUtils.roundToInt(d2 * localxulParsedProp_PaddingMargin1.bottom);
        break;
        n = XulUtils.roundToInt(d1 * ((XulPropParser.xulParsedStyle_PaddingMarginVal)localXulStyle6.getParsedValue()).val);
        break label1004;
        label1263: i1 = XulUtils.roundToInt(d2 * ((XulPropParser.xulParsedStyle_PaddingMarginVal)localXulStyle7.getParsedValue()).val);
        break label1025;
        i2 = XulUtils.roundToInt(d1 * ((XulPropParser.xulParsedStyle_PaddingMarginVal)localXulStyle8.getParsedValue()).val);
        break label1046;
      }
      label1332: this._rect.left = XulUtils.roundToInt(d1 * this._rect.left);
      this._rect.right = (i4 + this._rect.left);
      break label718;
      label1373: this._rect.top = XulUtils.roundToInt(d2 * this._rect.top);
      this._rect.bottom = (i5 + this._rect.top);
      break label780;
      label1414: _clearFlags(262144);
      break label794;
      label1423: _clearFlags(524288);
      break label808;
      label1432: _setRTL(false);
    }
  }

  private boolean syncLayoutParametersFast()
  {
    if (!_isLayoutChanged())
      return true;
    if (_isInvisible())
      return true;
    double d1 = this._ctx.getXScalar();
    double d2 = this._ctx.getYScalar();
    XulStyle localXulStyle1;
    XulStyle localXulStyle2;
    XulStyle localXulStyle3;
    XulStyle localXulStyle4;
    int k;
    int m;
    label172: int n;
    label193: int i1;
    if (this._padding == null)
    {
      localXulStyle1 = this._view.getStyle(XulPropNameCache.TagId.PADDING_LEFT);
      localXulStyle2 = this._view.getStyle(XulPropNameCache.TagId.PADDING_TOP);
      localXulStyle3 = this._view.getStyle(XulPropNameCache.TagId.PADDING_RIGHT);
      localXulStyle4 = this._view.getStyle(XulPropNameCache.TagId.PADDING_BOTTOM);
      this._padding = new Rect();
      XulStyle localXulStyle5;
      if ((localXulStyle1 != null) && (localXulStyle3 != null) && (localXulStyle2 != null))
      {
        localXulStyle5 = null;
        if (localXulStyle4 != null);
      }
      else
      {
        localXulStyle5 = this._view.getStyle(XulPropNameCache.TagId.PADDING);
      }
      Rect localRect1 = this._padding;
      if (localXulStyle1 != null)
        break label508;
      k = 0;
      localRect1.left = k;
      Rect localRect2 = this._padding;
      if (localXulStyle2 != null)
        break label530;
      m = 0;
      localRect2.top = m;
      Rect localRect3 = this._padding;
      if (localXulStyle3 != null)
        break label552;
      n = 0;
      localRect3.right = n;
      Rect localRect4 = this._padding;
      if (localXulStyle4 != null)
        break label574;
      i1 = 0;
      label214: localRect4.bottom = i1;
      if (localXulStyle5 != null)
      {
        XulPropParser.xulParsedProp_PaddingMargin localxulParsedProp_PaddingMargin = (XulPropParser.xulParsedProp_PaddingMargin)localXulStyle5.getParsedValue();
        if ((localXulStyle1 == null) || (localXulStyle1.getPriority() < localXulStyle5.getPriority()))
          this._padding.left = XulUtils.roundToInt(d1 * localxulParsedProp_PaddingMargin.left);
        if ((localXulStyle2 == null) || (localXulStyle2.getPriority() < localXulStyle5.getPriority()))
          this._padding.top = XulUtils.roundToInt(d2 * localxulParsedProp_PaddingMargin.top);
        if ((localXulStyle3 == null) || (localXulStyle3.getPriority() < localXulStyle5.getPriority()))
          this._padding.right = XulUtils.roundToInt(d1 * localxulParsedProp_PaddingMargin.right);
        if ((localXulStyle4 == null) || (localXulStyle4.getPriority() < localXulStyle5.getPriority()))
          this._padding.bottom = XulUtils.roundToInt(d2 * localxulParsedProp_PaddingMargin.bottom);
      }
    }
    if (this._rect == null)
    {
      this._rect = new Rect();
      _setScaledRectChanged();
    }
    int i = this._view.getWidth();
    if (i >= 2147483547)
    {
      _setScaledRectChanged();
      this._rect.right = (i + this._rect.left);
    }
    int j = this._view.getHeight();
    if (j >= 2147483547)
    {
      _setScaledRectChanged();
      this._rect.bottom = (j + this._rect.top);
    }
    if (i == 2147483644)
    {
      _addFlags(262144);
      label492: if (j != 2147483644)
        break label605;
      _addFlags(524288);
    }
    while (true)
    {
      return false;
      label508: k = XulUtils.roundToInt(d1 * ((XulPropParser.xulParsedStyle_PaddingMarginVal)localXulStyle1.getParsedValue()).val);
      break;
      label530: m = XulUtils.roundToInt(d2 * ((XulPropParser.xulParsedStyle_PaddingMarginVal)localXulStyle2.getParsedValue()).val);
      break label172;
      label552: n = XulUtils.roundToInt(d1 * ((XulPropParser.xulParsedStyle_PaddingMarginVal)localXulStyle3.getParsedValue()).val);
      break label193;
      label574: i1 = XulUtils.roundToInt(d2 * ((XulPropParser.xulParsedStyle_PaddingMarginVal)localXulStyle4.getParsedValue()).val);
      break label214;
      _clearFlags(262144);
      break label492;
      label605: _clearFlags(524288);
    }
  }

  private boolean syncVisibility()
  {
    if (!_isLayoutChanged())
      return true;
    boolean bool1 = _isVisible();
    if ("none".equals(this._view.getStyleString(XulPropNameCache.TagId.DISPLAY)));
    for (boolean bool2 = false; ; bool2 = true)
    {
      if (bool1 != bool2)
      {
        _setVisibility(bool2);
        onVisibilityChanged(bool2, this._view);
      }
      if (!_isVisible())
        break;
      return false;
    }
  }

  private void updateSizingMovingAnimation()
  {
    if ((!_hasSizingMovingAnimation()) || (this._aniTransformDuration < 1));
    do
    {
      do
        return;
      while (this._sizingMovingAnimation == null);
      this._sizingMovingAnimation.startAnimation();
    }
    while (!this._sizingMovingAnimation.isRunning());
    this._sizingMovingAnimation.restoreValue();
  }

  protected void _addFlags(int paramInt)
  {
    this._flags = (paramInt | this._flags);
  }

  protected void _clearFlags(int paramInt)
  {
    this._flags &= (paramInt ^ 0xFFFFFFFF);
  }

  protected boolean _hasAnimation()
  {
    return (0x8000 & this._flags) != 0;
  }

  protected boolean _hasAnyFlag(int paramInt)
  {
    return (paramInt & this._flags) != 0;
  }

  protected boolean _hasFlags(int paramInt)
  {
    return (paramInt & this._flags) == paramInt;
  }

  protected boolean _hasMovingAnimation()
  {
    return (0xA000 & this._flags) == 40960;
  }

  protected boolean _hasScaleAnimation()
  {
    return (0xC000 & this._flags) == 49152;
  }

  protected boolean _hasSizingAnimation()
  {
    return (0x9000 & this._flags) == 36864;
  }

  protected boolean _hasSizingMovingAnimation()
  {
    return (0xB000 & this._flags) > 32768;
  }

  protected boolean _isBooting()
  {
    return (0x3 & this._flags) != 3;
  }

  protected boolean _isDataChanged()
  {
    return (0x40 & this._flags) != 0;
  }

  protected boolean _isEnabled()
  {
    return (0x20000 & this._flags) == 131072;
  }

  protected boolean _isInitialPreload()
  {
    return (0x400 & this._flags) != 0;
  }

  protected boolean _isInvisible()
  {
    return (0x10000 & this._flags) != 65536;
  }

  protected boolean _isKeepFocusVisible()
  {
    return (0x200 & this._flags) != 0;
  }

  protected boolean _isLayoutChanged()
  {
    return (0x30 & this._flags) != 0;
  }

  protected boolean _isLayoutChangedByChild()
  {
    return (0x30 & this._flags) == 32;
  }

  protected boolean _isPreload()
  {
    return (0x800 & this._flags) != 0;
  }

  protected boolean _isRTL()
  {
    return (0x8 & this._flags) == 8;
  }

  protected boolean _isScaledRectUpdated()
  {
    return (0x100 & this._flags) == 0;
  }

  protected boolean _isViewChanged()
  {
    return (0xF0 & this._flags) != 0;
  }

  protected boolean _isVisible()
  {
    return (0x10000 & this._flags) == 65536;
  }

  protected void _modifyFlags(int paramInt1, int paramInt2)
  {
    this._flags = ((paramInt1 | this._flags) & (paramInt2 ^ 0xFFFFFFFF));
  }

  protected void _setDataChanged()
  {
    this._flags = (0x40 | this._flags);
  }

  protected void _setDataUpdated()
  {
    if ((0x3 & this._flags) == 1)
    {
      this._flags = (0xFFFFFFBF & (0x2 | this._flags));
      _notifyReady();
      return;
    }
    this._flags = (0xFFFFFFBF & (0x2 | this._flags));
  }

  protected void _setEnabled(boolean paramBoolean)
  {
    if (paramBoolean);
    for (this._flags = (0x20000 | this._flags); ; this._flags = (0xFFFDFFFF & this._flags))
    {
      this._view.updateEnableState(paramBoolean);
      return;
    }
  }

  protected void _setHasAnimation(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this._flags = (0x8000 | this._flags);
      return;
    }
    this._flags = (0xFFFF7FFF & this._flags);
  }

  protected void _setHasMovingAnimation(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this._flags = (0x2000 | this._flags);
      return;
    }
    this._flags = (0xFFFFDFFF & this._flags);
  }

  protected void _setHasScaleAnimation(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this._flags = (0x4000 | this._flags);
      return;
    }
    this._flags = (0xFFFFBFFF & this._flags);
  }

  protected void _setHasSizingAnimation(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this._flags = (0x1000 | this._flags);
      return;
    }
    this._flags = (0xFFFFEFFF & this._flags);
  }

  protected void _setInitialPreload(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this._flags = (0x400 | this._flags);
      return;
    }
    this._flags = (0xFFFFFBFF & this._flags);
  }

  protected void _setKeepFocusVisible(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this._flags = (0x200 | this._flags);
      return;
    }
    this._flags = (0xFFFFFDFF & this._flags);
  }

  protected void _setLayoutChanged()
  {
    this._flags = (0x10 | this._flags);
  }

  protected void _setLayoutChangedByChildren()
  {
    this._flags = (0x20 | this._flags);
  }

  protected void _setLayoutUpdated()
  {
    if ((0x3 & this._flags) == 2)
    {
      this._flags = (0xFFFFFFCF & (0x1 | this._flags));
      _notifyReady();
      return;
    }
    this._flags = (0xFFFFFFCF & (0x1 | this._flags));
  }

  protected void _setPendingItemsLoaded()
  {
    this._flags = (0x4 | this._flags);
  }

  protected void _setPreload(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this._flags = (0x800 | this._flags);
      return;
    }
    this._flags = (0xFFFFF7FF & this._flags);
  }

  protected void _setRTL(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this._flags = (0x8 | this._flags);
      return;
    }
    this._flags = (0xFFFFFFF7 & this._flags);
  }

  protected void _setScaledRectChanged()
  {
    this._flags = (0x100 | this._flags);
  }

  protected void _setScaledRectUpdated()
  {
    this._flags = (0xFFFFFEFF & this._flags);
  }

  protected void _setVisibility(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this._flags = (0x10000 | this._flags);
      return;
    }
    this._flags = (0xFFFEFFFF & this._flags);
  }

  public void addAnimation(IXulAnimation paramIXulAnimation)
  {
    this._ctx.addAnimation(paramIXulAnimation);
  }

  public long animationTimestamp()
  {
    return this._ctx.animationTimestamp();
  }

  public boolean blinkClass(String[] paramArrayOfString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length <= 0));
    int i;
    do
    {
      return false;
      i = 0;
      int j = 0;
      int k = paramArrayOfString.length;
      if (j < k)
      {
        String str = paramArrayOfString[j];
        if ((this._view.addClass(str)) || (i != 0));
        for (i = 1; ; i = 0)
        {
          j++;
          break;
        }
      }
    }
    while (i == 0);
    if (this._blinkClassStack == null)
      this._blinkClassStack = new ArrayList();
    this._blinkClassStack.add(paramArrayOfString);
    reset();
    return true;
  }

  protected RectF calScaledRect()
  {
    RectF localRectF = this._scaledRect;
    if (localRectF == null)
    {
      localRectF = new RectF();
      this._scaledRect = localRectF;
    }
    while ((Math.abs(this._scalarX - 1.0F) < 0.001F) && (Math.abs(this._scalarY - 1.0F) < 0.001F))
    {
      XulUtils.copyRect(this._rect, localRectF);
      _setScaledRectUpdated();
      return localRectF;
      if (_isScaledRectUpdated())
        return localRectF;
    }
    int i = this._rect.right - this._rect.left;
    int j = this._rect.bottom - this._rect.top;
    localRectF.left = (this._rect.left + (1.0F - this._scalarX) * this._scalarXAlign * i);
    localRectF.top = (this._rect.top + (1.0F - this._scalarY) * this._scalarYAlign * j);
    localRectF.right = (localRectF.left + this._scalarX * i);
    localRectF.bottom = (localRectF.top + this._scalarY * j);
    _setScaledRectUpdated();
    return localRectF;
  }

  public void cleanImageItems()
  {
    if ((this._bkgInfo != null) && (this._bkgInfo._bmp != null))
    {
      this._bkgInfo._bmp = null;
      this._bkgInfo._lastLoadFailedTime = 0L;
    }
  }

  public XulWorker.DrawableItem collectPendingImageItem()
  {
    if (this._bkgInfo == null);
    XulDrawable localXulDrawable;
    do
    {
      do
        return null;
      while (this._bkgInfo._isLoading);
      localXulDrawable = this._bkgInfo._bmp;
    }
    while (((!this._bkgInfo._forceReload) && (localXulDrawable != null) && (!localXulDrawable.isRecycled())) || (TextUtils.isEmpty(this._bkgInfo._url)) || (XulUtils.timestamp() - this._bkgInfo._lastLoadFailedTime < 3000L));
    this._bkgInfo._isLoading = true;
    this._bkgInfo.url = this._bkgInfo._url;
    this._bkgInfo.scalarX = getRenderContext().getXScalar();
    this._bkgInfo.scalarY = getRenderContext().getYScalar();
    this._bkgInfo.target_width = XulUtils.calRectWidth(this._rect);
    this._bkgInfo.target_height = XulUtils.calRectHeight(this._rect);
    return this._bkgInfo;
  }

  public boolean collectPendingItems(XulTaskCollector paramXulTaskCollector)
  {
    return (_isViewChanged()) || (this._rect == null);
  }

  public void collectTransformValues(TransformAnimation paramTransformAnimation)
  {
    TransformAnimation.TransformValues[] arrayOfTransformValues = new TransformAnimation.TransformValues[1];
    arrayOfTransformValues[0] = new TransformAnimation.TransformValues()
    {
      float _destScalarXAlign;
      float _destScalarY;
      float _destScalarYAlign;
      float _scalarXAlignVal;
      float _scalarYAlignVal;
      float _scalarYVal;
      float _srcScalarXAlign;
      float _srcScalarY;
      float _srcScalarYAlign;

      public boolean identicalValue()
      {
        return (super.identicalValue()) && (this._srcScalarY == this._destScalarY) && (this._srcScalarXAlign == this._destScalarXAlign) && (this._srcScalarYAlign == this._destScalarYAlign);
      }

      public void restoreValue()
      {
        XulViewRender.this._setScaledRectChanged();
        XulViewRender.this._scalarX = this._val;
        XulViewRender.this._scalarY = this._scalarYVal;
        XulViewRender.this._scalarXAlign = this._scalarXAlignVal;
        XulViewRender.this._scalarYAlign = this._scalarYAlignVal;
      }

      public void storeDest()
      {
        this._destVal = XulViewRender.this._scalarX;
        this._destScalarY = XulViewRender.this._scalarY;
        this._destScalarXAlign = XulViewRender.this._scalarXAlign;
        this._destScalarYAlign = XulViewRender.this._scalarYAlign;
      }

      public void storeSrc()
      {
        this._srcVal = XulViewRender.this._scalarX;
        this._srcScalarY = XulViewRender.this._scalarY;
        this._srcScalarXAlign = XulViewRender.this._scalarXAlign;
        this._srcScalarYAlign = XulViewRender.this._scalarYAlign;
      }

      public boolean updateValue(float paramAnonymousFloat)
      {
        if (!XulViewRender.this._hasScaleAnimation())
        {
          this._val = this._destVal;
          this._scalarYVal = this._destScalarY;
          this._scalarXAlignVal = this._destScalarXAlign;
          this._scalarYAlignVal = this._destScalarYAlign;
          return false;
        }
        boolean bool1 = updateValueScalarX(paramAnonymousFloat);
        int i;
        int j;
        if ((updateValueScalarY(paramAnonymousFloat)) || (bool1))
        {
          i = 1;
          if ((!updateValueScalarXAlign(paramAnonymousFloat)) && (i == 0))
            break label103;
          j = 1;
          label79: if ((!updateValueScalarYAlign(paramAnonymousFloat)) && (j == 0))
            break label109;
        }
        label103: label109: for (boolean bool2 = true; ; bool2 = false)
        {
          return bool2;
          i = 0;
          break;
          j = 0;
          break label79;
        }
      }

      public boolean updateValueScalarX(float paramAnonymousFloat)
      {
        return super.updateValue(paramAnonymousFloat);
      }

      public boolean updateValueScalarXAlign(float paramAnonymousFloat)
      {
        float f = this._destScalarXAlign - this._srcScalarXAlign;
        this._scalarXAlignVal = this._srcScalarXAlign;
        if (f == 0.0F)
          return false;
        this._scalarXAlignVal += f * paramAnonymousFloat;
        return true;
      }

      public boolean updateValueScalarY(float paramAnonymousFloat)
      {
        float f = this._destScalarY - this._srcScalarY;
        this._scalarYVal = this._srcScalarY;
        if (f == 0.0F)
          return false;
        this._scalarYVal += f * paramAnonymousFloat;
        return true;
      }

      public boolean updateValueScalarYAlign(float paramAnonymousFloat)
      {
        float f = this._destScalarYAlign - this._srcScalarYAlign;
        this._scalarYAlignVal = this._srcScalarYAlign;
        if (f == 0.0F)
          return false;
        this._scalarYAlignVal += f * paramAnonymousFloat;
        return true;
      }
    };
    paramTransformAnimation.addTransformValues(arrayOfTransformValues);
  }

  protected XulLayoutHelper.ILayoutElement createElement()
  {
    return new LayoutElement();
  }

  public void destroy()
  {
    cleanImageItems();
    if (this._transformAnimation != null)
      removeAnimation(this._transformAnimation);
  }

  public boolean doSuspendRecycle(int paramInt)
  {
    return false;
  }

  public void doSyncData()
  {
    float f1;
    float f2;
    float f3;
    float f4;
    if (_isDataChanged())
    {
      f1 = this._scalarX;
      f2 = this._scalarY;
      f3 = this._scalarXAlign;
      f4 = this._scalarYAlign;
      syncAnimationInfo();
      if ((hasAnimation()) && (!_isBooting()))
        initTransformAnimation(true);
      syncData();
      if (!_isBooting())
        break label67;
    }
    while (true)
    {
      _setDataUpdated();
      return;
      label67: if (this._transformAnimation != null)
      {
        this._transformAnimation.storeDest();
        if (!this._transformAnimation.identicalValues())
        {
          this._transformAnimation.restoreSrcValue();
          addAnimation(this._transformAnimation);
        }
      }
      else if ((_hasScaleAnimation()) && (this._aniTransformDuration > 0) && ((Math.abs(this._scalarX - f1) > 0.001F) || (Math.abs(this._scalarY - f2) > 0.001F) || (Math.abs(this._scalarXAlign - f3) > 0.001F) || (Math.abs(this._scalarYAlign - f4) > 0.001F)))
      {
        float f5 = this._scalarX;
        float f6 = this._scalarY;
        float f7 = this._scalarXAlign;
        float f8 = this._scalarYAlign;
        this._scalarX = f1;
        this._scalarY = f2;
        this._scalarXAlign = f3;
        this._scalarYAlign = f4;
        initTransformAnimation(true);
        this._scalarX = f5;
        this._scalarY = f6;
        this._scalarXAlign = f7;
        this._scalarYAlign = f8;
        this._transformAnimation.storeDest();
        this._transformAnimation.restoreSrcValue();
        addAnimation(this._transformAnimation);
      }
    }
  }

  public void draw(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    if (_isInvisible())
      return;
    drawBackground(paramXulDC, paramRect, paramInt1, paramInt2);
    drawBorder(paramXulDC, paramRect, paramInt1, paramInt2);
  }

  public void drawBackground(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    if (_isInvisible())
      return;
    RectF localRectF = getAnimRect();
    XulUtils.offsetRect(localRectF, paramInt1 + this._screenX, paramInt2 + this._screenY);
    internalDrawBackground(paramXulDC, localRectF);
  }

  public void drawBackgroundNoScale(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    if (_isInvisible())
      return;
    RectF localRectF = getFocusRect();
    XulUtils.offsetRect(localRectF, paramInt1, paramInt2);
    internalDrawBackground(paramXulDC, localRectF);
  }

  public void drawBorder(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    if (_isInvisible());
    while (true)
    {
      return;
      if ((this._borderSize > 0.1F) && ((0xFF000000 & this._borderColor) != 0))
      {
        Paint localPaint = XulRenderContext.getDefStrokePaint();
        RectF localRectF = getAnimRect();
        float f1 = this._borderSize * this._scalarX;
        localPaint.setStrokeWidth(f1);
        localPaint.setColor(this._borderColor);
        if (this._borderEffect != null)
          localPaint.setPathEffect(this._borderEffect);
        if ((this._borderRoundX > 0.5D) && (this._borderRoundY > 0.5D))
        {
          float f2 = f1 / 2.0F - f1 * this._borderPos;
          paramXulDC.drawRoundRect(f2 + localRectF.left + this._screenX + paramInt1, f2 + localRectF.top + this._screenY + paramInt2, XulUtils.calRectWidth(localRectF) - 2.0F * f2, XulUtils.calRectHeight(localRectF) - 2.0F * f2, this._borderRoundX * this._scalarX, this._borderRoundY * this._scalarY, localPaint);
        }
        while (this._borderEffect != null)
        {
          localPaint.setPathEffect(null);
          return;
          XulUtils.offsetRect(localRectF, paramInt1 + this._screenX, paramInt2 + this._screenY);
          paramXulDC.drawRect(localRectF, localPaint);
        }
      }
    }
  }

  public RectF getAnimRect()
  {
    RectF localRectF1 = calScaledRect();
    RectF localRectF2 = this._animRect;
    if (localRectF2 == null)
    {
      localRectF2 = new RectF();
      this._animRect = localRectF2;
    }
    localRectF2.left = localRectF1.left;
    localRectF2.top = localRectF1.top;
    localRectF2.right = localRectF1.right;
    localRectF2.bottom = localRectF1.bottom;
    return localRectF2;
  }

  public abstract int getDefaultFocusMode();

  public Rect getDrawingRect()
  {
    return this._rect;
  }

  public IXulExternalView getExternalView()
  {
    return null;
  }

  public RectF getFocusRect()
  {
    RectF localRectF = this._focusRect;
    if (localRectF == null)
    {
      localRectF = new RectF();
      this._focusRect = localRectF;
    }
    if (this._rect != null)
      XulUtils.copyRect(this._rect, localRectF);
    XulUtils.offsetRect(localRectF, this._screenX, this._screenY);
    return localRectF;
  }

  public int getHeight()
  {
    return XulUtils.calRectHeight(this._rect);
  }

  public XulLayoutHelper.ILayoutElement getLayoutElement()
  {
    if (this._layoutElement == null)
      this._layoutElement = createElement();
    return this._layoutElement;
  }

  public Rect getPadding()
  {
    return this._padding;
  }

  public XulArea getParentView()
  {
    return this._view.getParent();
  }

  public XulWorker.DrawableItem getPendingImageItem()
  {
    XulWorker.DrawableItem localDrawableItem = collectPendingImageItem();
    if (localDrawableItem == null)
      _setPendingItemsLoaded();
    return localDrawableItem;
  }

  public XulRenderContext getRenderContext()
  {
    return this._ctx;
  }

  public int getScreenX()
  {
    return this._screenX;
  }

  public int getScreenY()
  {
    return this._screenY;
  }

  public RectF getUpdateRect()
  {
    RectF localRectF1 = this._updateRect;
    RectF localRectF2 = calScaledRect();
    if (localRectF1 == null)
    {
      RectF localRectF3 = new RectF(localRectF2);
      this._updateRect = localRectF3;
      XulUtils.offsetRect(localRectF3, this._screenX, this._screenY);
      return localRectF3;
    }
    localRectF2.left += this._screenX;
    localRectF2.top += this._screenY;
    localRectF2.right += this._screenX;
    localRectF2.bottom += this._screenY;
    return localRectF1;
  }

  public XulView getView()
  {
    return this._view;
  }

  public float getViewXScalar()
  {
    return this._scalarX;
  }

  public float getViewYScalar()
  {
    return this._scalarY;
  }

  public int getWidth()
  {
    return XulUtils.calRectWidth(this._rect);
  }

  public double getXScalar()
  {
    return this._ctx.getXScalar();
  }

  public double getYScalar()
  {
    return this._ctx.getYScalar();
  }

  public int getZIndex()
  {
    return this._zIndex;
  }

  public boolean handleScrollEvent(float paramFloat1, float paramFloat2)
  {
    return false;
  }

  public boolean hasAnimation()
  {
    if ((!_hasAnimation()) || (this._aniTransformDuration <= 0));
    while (this._transformAnimation == null)
      return false;
    return true;
  }

  public boolean hitTest(int paramInt, float paramFloat1, float paramFloat2)
  {
    if (_isInvisible());
    while ((paramInt != 0) && (paramInt != 1) && (paramInt != -1))
      return false;
    float f1 = paramFloat1 - this._screenX;
    float f2 = paramFloat2 - this._screenY;
    return getAnimRect().contains(f1, f2);
  }

  public boolean hitTestTranslate(PointF paramPointF)
  {
    return false;
  }

  protected void internalDrawBackground(XulDC paramXulDC, RectF paramRectF)
  {
    if (this._bkgInfo != null)
    {
      localObject = this._bkgInfo._bmp;
      if (localObject == null)
      {
        localBkgDrawableInfo = this._bkgInfo;
        localObject = XulWorker.loadDrawableFromCache(this._bkgInfo.url);
        localBkgDrawableInfo._bmp = ((XulDrawable)localObject);
      }
    }
    while ((0xFF000000 & this._bkgColor) == 0)
    {
      Object localObject;
      BkgDrawableInfo localBkgDrawableInfo;
      while (localObject != null)
      {
        paramXulDC.drawBitmap((XulDrawable)localObject, paramRectF, XulRenderContext.getDefPicPaint());
        return;
        if (((XulDrawable)localObject).isRecycled())
        {
          XulDrawable localXulDrawable = XulWorker.loadDrawableFromCache(this._bkgInfo.url);
          if (localXulDrawable != null)
          {
            this._bkgInfo._bmp = localXulDrawable;
            localObject = localXulDrawable;
          }
        }
      }
    }
    Paint localPaint = XulRenderContext.getDefSolidPaint();
    localPaint.setColor(this._bkgColor);
    if ((this._borderRoundX > 0.5D) && (this._borderRoundY > 0.5D))
    {
      paramXulDC.drawRoundRect(paramRectF, this._borderRoundX * this._scalarX, this._borderRoundY * this._scalarY, localPaint);
      return;
    }
    paramXulDC.drawRect(paramRectF, localPaint);
  }

  protected boolean internalRejectTest()
  {
    if (this._rect == null);
    RectF localRectF1;
    RectF localRectF2;
    do
    {
      return true;
      localRectF1 = this._view.getRootLayout().getFocusRc();
      localRectF2 = getFocusRect();
      if (XulUtils.intersects(localRectF1, localRectF2))
        return false;
    }
    while ((!localRectF2.isEmpty()) || (!localRectF1.contains(localRectF2.left, localRectF2.top)));
    return false;
  }

  public boolean isEnabled()
  {
    return _isEnabled();
  }

  public boolean isInvisible()
  {
    return _isInvisible();
  }

  public boolean isLayoutChanged()
  {
    return _isLayoutChanged();
  }

  public boolean isRTL()
  {
    return _isRTL();
  }

  public boolean isVisible()
  {
    return _isVisible();
  }

  public boolean keepFocusVisible()
  {
    return _isKeepFocusVisible();
  }

  public void markDataChanged()
  {
    if (_isDataChanged())
      return;
    _setDataChanged();
    this._ctx.markDataChanged(this);
  }

  public void markDirtyView()
  {
    this._ctx.markDirtyView(this._view);
    for (XulArea localXulArea = this._view.getParent(); localXulArea != null; localXulArea = localXulArea.getParent())
    {
      XulViewRender localXulViewRender = localXulArea.getRender();
      if (localXulViewRender != null)
        localXulViewRender.onDirtyChild(this._view);
    }
  }

  public boolean needPostDraw()
  {
    return (this._zIndex > 0) || (this._view.isFocused());
  }

  public void onAnimationFinished(boolean paramBoolean)
  {
    popupBlinkClassStack();
  }

  public boolean onChildDoActionEvent(XulActionEvent paramXulActionEvent)
  {
    return false;
  }

  public boolean onChildStateChanged(XulStateChangeEvent paramXulStateChangeEvent)
  {
    return false;
  }

  public void onDirtyChild(XulView paramXulView)
  {
    this._dirtyTimestamp = getRenderContext().animationTimestamp();
  }

  public boolean onKeyEvent(KeyEvent paramKeyEvent)
  {
    return false;
  }

  public void onUsabilityChanged(boolean paramBoolean, XulView paramXulView)
  {
  }

  public void onVisibilityChanged(boolean paramBoolean, XulView paramXulView)
  {
    int i = 4;
    XulArea localXulArea = this._view.getParent();
    int j;
    XulStateChangeEvent localXulStateChangeEvent2;
    if (localXulArea != null)
    {
      _stateChangeEvent.event = "visibilityChanged";
      _stateChangeEvent.eventSource = paramXulView;
      XulStateChangeEvent localXulStateChangeEvent1 = _stateChangeEvent;
      if (!paramBoolean)
        break label130;
      j = 8;
      localXulStateChangeEvent1.oldState = j;
      localXulStateChangeEvent2 = _stateChangeEvent;
      if (!paramBoolean)
        break label136;
    }
    while (true)
    {
      localXulStateChangeEvent2.state = i;
      _stateChangeEvent.notifySource = this._view;
      _stateChangeEvent.adjustFocusView = false;
      localXulArea.onChildStateChanged(_stateChangeEvent);
      _stateChangeEvent.eventSource = null;
      _stateChangeEvent.notifySource = null;
      if ((paramBoolean) && (paramXulView != this._view) && (_isViewChanged()))
        updateParentLayout();
      return;
      label130: j = i;
      break;
      label136: i = 8;
    }
  }

  public void popupBlinkClassStack()
  {
    ArrayList localArrayList = this._blinkClassStack;
    if (localArrayList == null);
    int i;
    label40: label77: 
    do
    {
      return;
      i = 0;
      if (!localArrayList.isEmpty())
      {
        String[] arrayOfString = (String[])localArrayList.remove(-1 + localArrayList.size());
        int j = 0;
        int k = arrayOfString.length;
        if (j < k)
        {
          String str = arrayOfString[j];
          if ((!this._view.removeClass(str)) && (i == 0))
            break label77;
        }
        for (i = 1; ; i = 0)
        {
          j++;
          break label40;
          break;
        }
      }
    }
    while (i == 0);
    reset();
  }

  public void postDraw(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    if ((this._quiver != null) && (this._quiver.isQuivering()))
      paramXulDC.restore();
  }

  public XulView postFindFocus(XulLayout.FocusDirection paramFocusDirection, RectF paramRectF, XulView paramXulView)
  {
    return null;
  }

  public void preDraw(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    if ((this._quiver != null) && (this._quiver.isQuivering()))
    {
      paramXulDC.save();
      paramXulDC.translate(this._quiver._quiverXDelta, this._quiver._quiverYDelta);
    }
  }

  public XulView preFindFocus(XulLayout.FocusDirection paramFocusDirection, RectF paramRectF, XulView paramXulView)
  {
    return null;
  }

  public boolean rejectTest()
  {
    if (this._rect == null);
    while (true)
    {
      return true;
      if (_isPreload())
        return false;
      RectF localRectF1 = this._view.getRootLayout().getFocusRc();
      RectF localRectF2 = getFocusRect();
      if (XulUtils.intersects(localRectF1, localRectF2))
        return false;
      if ((localRectF2.isEmpty()) && (localRectF1.contains(localRectF2.left, localRectF2.top)))
        return false;
      for (XulArea localXulArea = this._view.getParent(); localXulArea != null; localXulArea = localXulArea.getParent())
        if (localXulArea.getRender()._isPreload())
          return false;
    }
  }

  public void removeAnimation(IXulAnimation paramIXulAnimation)
  {
    this._ctx.removeAnimation(paramIXulAnimation);
  }

  public void reset()
  {
    if (_isViewChanged())
    {
      markDataChanged();
      _setLayoutChanged();
      updateParentLayout();
      markDirtyView();
      return;
    }
    if ((this._bkgInfo != null) && (!this._bkgInfo._isLoading))
    {
      this._bkgInfo._forceReload = true;
      this._bkgInfo._lastLoadFailedTime = 0L;
    }
    setUpdateAll();
    markDirtyView();
  }

  public void resetBinding()
  {
  }

  public void setEnabled(boolean paramBoolean)
  {
    _setEnabled(paramBoolean);
  }

  public void setPreload(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean1);
    for (int i = 1024; ; i = 0)
    {
      int j = 0;
      if (paramBoolean2)
        j = 2048;
      this._flags = (i | j | this._flags);
      return;
    }
  }

  public void setUpdateAll()
  {
    markDataChanged();
    setUpdateLayout();
  }

  public boolean setUpdateLayout()
  {
    return setUpdateLayout(false);
  }

  public boolean setUpdateLayout(boolean paramBoolean)
  {
    boolean bool = _isLayoutChanged();
    if (paramBoolean)
      _setLayoutChangedByChildren();
    while (true)
    {
      if (!bool)
        updateParentLayout();
      return true;
      _setLayoutChanged();
    }
  }

  public void switchState(int paramInt)
  {
    setUpdateAll();
  }

  public void syncData()
  {
    if (!_isDataChanged())
      return;
    double d1 = this._ctx.getXScalar();
    double d2 = this._ctx.getYScalar();
    XulStyle localXulStyle1 = this._view.getStyle(XulPropNameCache.TagId.SCALE);
    XulStyle localXulStyle2 = this._view.getStyle(XulPropNameCache.TagId.ANIMATION_SCALE);
    label102: label121: XulPropParser.xulParsedStyle_BackgroundImage localxulParsedStyle_BackgroundImage;
    label244: BkgDrawableInfo localBkgDrawableInfo;
    label308: label340: XulStyle localXulStyle7;
    if (localXulStyle2 != null)
    {
      _setHasScaleAnimation(((XulPropParser.xulParsedProp_booleanValue)localXulStyle2.getParsedValue()).val);
      XulStyle localXulStyle3 = this._view.getStyle(XulPropNameCache.TagId.PRELOAD);
      if (localXulStyle3 == null)
        break label413;
      _setPreload(((XulPropParser.xulParsedProp_booleanValue)localXulStyle3.getParsedValue()).val);
      _setScaledRectChanged();
      if (localXulStyle1 != null)
        break label424;
      this._scalarY = 1.0F;
      this._scalarX = 1.0F;
      XulStyle localXulStyle4 = this._view.getStyle(XulPropNameCache.TagId.BORDER);
      if (localXulStyle4 == null)
        break label481;
      XulPropParser.xulParsedStyle_Border localxulParsedStyle_Border = (XulPropParser.xulParsedStyle_Border)localXulStyle4.getParsedValue();
      this._borderSize = XulUtils.roundToInt(d1 * localxulParsedStyle_Border.size);
      this._borderColor = localxulParsedStyle_Border.color;
      this._borderRoundX = ((float)(d1 * localxulParsedStyle_Border.xRadius));
      this._borderRoundY = ((float)(d2 * localxulParsedStyle_Border.yRadius));
      this._borderPos = localxulParsedStyle_Border.pos;
      XulStyle localXulStyle9 = this._view.getStyle(XulPropNameCache.TagId.BORDER_DASH_PATTERN);
      if (localXulStyle9 == null)
        break label473;
      this._borderEffect = ((XulPropParser.xulParsedStyle_Border_Dash_Pattern)localXulStyle9.getParsedValue()).getEffectObjectByXYScalar((float)d1, (float)d2);
      XulStyle localXulStyle5 = this._view.getStyle(XulPropNameCache.TagId.BACKGROUND_IMAGE);
      if (localXulStyle5 == null)
        break label634;
      localxulParsedStyle_BackgroundImage = (XulPropParser.xulParsedStyle_BackgroundImage)localXulStyle5.getParsedValue();
      localBkgDrawableInfo = this._bkgInfo;
      if (localBkgDrawableInfo == null)
        localBkgDrawableInfo = new BkgDrawableInfo();
      if (!TextUtils.isEmpty(localxulParsedStyle_BackgroundImage.url))
        break label509;
      this._bkgInfo = null;
      XulStyle localXulStyle6 = this._view.getStyle(XulPropNameCache.TagId.BACKGROUND_COLOR);
      if (localXulStyle6 == null)
        break label642;
      this._bkgColor = ((XulPropParser.xulParsedStyle_BackgroundColor)localXulStyle6.getParsedValue()).val;
      localXulStyle7 = this._view.getStyle(XulPropNameCache.TagId.Z_INDEX);
      if (localXulStyle7 == null)
        break label650;
    }
    label642: label650: for (this._zIndex = ((XulPropParser.xulParsedProp_Integer)localXulStyle7.getParsedValue()).val; ; this._zIndex = 0)
    {
      XulStyle localXulStyle8 = this._view.getStyle(XulPropNameCache.TagId.KEEP_FOCUS_VISIBLE);
      if (localXulStyle8 == null)
        break label658;
      _setKeepFocusVisible(((XulPropParser.xulParsedProp_booleanValue)localXulStyle8.getParsedValue()).val);
      return;
      _setHasScaleAnimation(true);
      break;
      label413: _setPreload(_isInitialPreload());
      break label102;
      label424: XulPropParser.xulParsedStyle_Scale localxulParsedStyle_Scale = (XulPropParser.xulParsedStyle_Scale)localXulStyle1.getParsedValue();
      this._scalarX = localxulParsedStyle_Scale.xScalar;
      this._scalarY = localxulParsedStyle_Scale.yScalar;
      this._scalarXAlign = localxulParsedStyle_Scale.xAlign;
      this._scalarYAlign = localxulParsedStyle_Scale.yAlign;
      break label121;
      label473: this._borderEffect = null;
      break label244;
      label481: this._borderColor = 0;
      this._borderSize = 0.0F;
      this._borderRoundX = 0.0F;
      this._borderRoundY = 0.0F;
      this._borderEffect = null;
      break label244;
      label509: String str = localxulParsedStyle_BackgroundImage.url;
      if (localBkgDrawableInfo._url != str)
      {
        if ((!TextUtils.isEmpty(localBkgDrawableInfo._url)) && (!localBkgDrawableInfo._url.equals(str)))
        {
          XulWorker.removeDrawableCache(this._bkgInfo._url);
          if (localBkgDrawableInfo._isLoading)
          {
            localBkgDrawableInfo._isRecycled = true;
            localBkgDrawableInfo = new BkgDrawableInfo();
          }
          localBkgDrawableInfo._forceReload = true;
          localBkgDrawableInfo._lastLoadFailedTime = 0L;
        }
        localBkgDrawableInfo._url = str;
        XulDrawable localXulDrawable = XulWorker.loadDrawableFromCache(localBkgDrawableInfo._url);
        if (localXulDrawable != null)
          localBkgDrawableInfo._bmp = localXulDrawable;
      }
      this._bkgInfo = localBkgDrawableInfo;
      break label308;
      label634: this._bkgInfo = null;
      break label308;
      this._bkgColor = 0;
      break label340;
    }
    label658: _setKeepFocusVisible(false);
  }

  public void updateParentLayout()
  {
    updateParentLayout(false);
  }

  public void updateParentLayout(boolean paramBoolean)
  {
    for (XulArea localXulArea = this._view.getParent(); ; localXulArea = localXulArea.getParent())
    {
      XulViewRender localXulViewRender;
      if (localXulArea != null)
      {
        localXulViewRender = localXulArea.getRender();
        if (localXulViewRender == null)
          continue;
        if ((paramBoolean) || (!localXulViewRender._isLayoutChanged()));
      }
      else
      {
        return;
      }
      localXulViewRender.setUpdateLayout(true);
    }
  }

  class BkgDrawableInfo extends XulRenderDrawableItem
  {
    XulDrawable _bmp = null;
    volatile boolean _forceReload = false;
    volatile boolean _isLoading = false;
    volatile boolean _isRecycled = false;
    volatile long _lastLoadFailedTime = 0L;
    String _url;

    static
    {
      if (!XulViewRender.class.desiredAssertionStatus());
      for (boolean bool = true; ; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }

    BkgDrawableInfo()
    {
    }

    public void onImageReady(XulDrawable paramXulDrawable)
    {
      this._isLoading = false;
      this._forceReload = false;
      if (this._isRecycled)
        return;
      if (paramXulDrawable == null);
      for (this._lastLoadFailedTime = XulUtils.timestamp(); (!$assertionsDisabled) && (!this._url.equals(this.url)); this._lastLoadFailedTime = 0L)
      {
        throw new AssertionError();
        this._bmp = paramXulDrawable;
      }
      XulViewRender.this.markDirtyView();
    }
  }

  protected class LayoutElement
    implements XulLayoutHelper.ILayoutElement
  {
    private boolean _initialVisibility;

    protected LayoutElement()
    {
    }

    public boolean changed()
    {
      return XulViewRender.this._isLayoutChanged();
    }

    public boolean checkUpdateMatchParent(int paramInt1, int paramInt2)
    {
      if (XulViewRender.this._isLayoutChanged());
      boolean bool1;
      boolean bool2;
      do
      {
        return false;
        bool1 = XulViewRender.this._hasFlags(262144);
        bool2 = XulViewRender.this._hasFlags(524288);
      }
      while (((!bool1) && (!bool2)) || (((!bool1) || (XulViewRender.this._rect.right == paramInt1)) && ((!bool2) || (XulViewRender.this._rect.bottom == paramInt2))));
      XulViewRender.this._setLayoutChanged();
      XulViewRender.this.updateParentLayout();
      return true;
    }

    public int doFinal()
    {
      if ((!XulViewRender.this._isLayoutChangedByChild()) && (this._initialVisibility == XulViewRender.this._isVisible()))
        XulViewRender.this.finalizeSizingMovingAnimation();
      XulViewRender.this._setLayoutUpdated();
      return 0;
    }

    void fastSyncLayout()
    {
      if (XulViewRender.this._isInvisible());
      do
      {
        return;
        XulViewRender.this.syncAnimationInfo();
        if (!XulViewRender.this._hasSizingMovingAnimation())
          break;
      }
      while ((XulViewRender.this._sizingMovingAnimation != null) && (XulViewRender.this._sizingMovingAnimation.isRunning()));
      XulViewRender.this.prepareSizingMovingAnimation();
      while (true)
      {
        XulViewRender.this.syncLayoutParametersFast();
        XulViewRender.this.updateSizingMovingAnimation();
        return;
        if (XulViewRender.this._sizingMovingAnimation != null)
        {
          XulViewRender.this.removeAnimation(XulViewRender.this._sizingMovingAnimation);
          XulViewRender.this._sizingMovingAnimation.stopAnimation();
        }
      }
    }

    public int getBaseX()
    {
      return XulViewRender.this._screenX;
    }

    public int getBaseY()
    {
      return XulViewRender.this._screenY;
    }

    public int getBottom()
    {
      return XulViewRender.this._rect.bottom;
    }

    public int getContentHeight()
    {
      return 0;
    }

    public int getContentWidth()
    {
      return 0;
    }

    public int getHeight()
    {
      return XulUtils.calRectHeight(XulViewRender.this._rect);
    }

    public int getLeft()
    {
      return XulViewRender.this._rect.left;
    }

    public Rect getMargin()
    {
      if (XulViewRender.this._margin == null)
        return XulViewRender._DUMMY_PADDING_;
      return XulViewRender.this._margin;
    }

    public Rect getPadding()
    {
      if (XulViewRender.this._padding == null)
        return XulViewRender._DUMMY_PADDING_;
      return XulViewRender.this._padding;
    }

    public int getRight()
    {
      return XulViewRender.this._rect.right;
    }

    public int getTop()
    {
      return XulViewRender.this._rect.top;
    }

    public int getViewBottom()
    {
      return XulViewRender.this._screenY + XulViewRender.this._rect.bottom;
    }

    public int getViewRight()
    {
      return XulViewRender.this._screenX + XulViewRender.this._rect.right;
    }

    public int getWidth()
    {
      return XulUtils.calRectWidth(XulViewRender.this._rect);
    }

    public boolean isVisible()
    {
      return XulViewRender.this._isVisible();
    }

    public boolean offsetBase(int paramInt1, int paramInt2)
    {
      XulViewRender localXulViewRender1 = XulViewRender.this;
      localXulViewRender1._screenX = (paramInt1 + localXulViewRender1._screenX);
      XulViewRender localXulViewRender2 = XulViewRender.this;
      localXulViewRender2._screenY = (paramInt2 + localXulViewRender2._screenY);
      return true;
    }

    public int prepare()
    {
      if (XulViewRender.this._isLayoutChangedByChild())
        fastSyncLayout();
      while (true)
      {
        return 0;
        syncLayout();
      }
    }

    public boolean setBase(int paramInt1, int paramInt2)
    {
      XulViewRender.this._screenX = paramInt1;
      XulViewRender.this._screenY = paramInt2;
      return true;
    }

    public boolean setHeight(int paramInt)
    {
      XulViewRender.this._rect.bottom = (paramInt + XulViewRender.this._rect.top);
      return true;
    }

    public boolean setWidth(int paramInt)
    {
      XulViewRender.this._rect.right = (paramInt + XulViewRender.this._rect.left);
      return true;
    }

    void syncLayout()
    {
      this._initialVisibility = XulViewRender.this._isVisible();
      if (XulViewRender.this.syncVisibility());
      do
      {
        do
          return;
        while (XulViewRender.this._isInvisible());
        XulViewRender.this.syncAnimationInfo();
        if (this._initialVisibility != XulViewRender.this._isVisible())
          break label146;
        if (!XulViewRender.this._hasSizingMovingAnimation())
          break;
      }
      while ((XulViewRender.this._sizingMovingAnimation != null) && (XulViewRender.this._sizingMovingAnimation.isRunning()));
      XulViewRender.this.prepareSizingMovingAnimation();
      while (true)
      {
        XulViewRender.this.syncLayoutParameters();
        XulViewRender.this.updateSizingMovingAnimation();
        return;
        if (XulViewRender.this._sizingMovingAnimation != null)
        {
          XulViewRender.this.removeAnimation(XulViewRender.this._sizingMovingAnimation);
          XulViewRender.this._sizingMovingAnimation.stopAnimation();
        }
      }
      label146: XulViewRender.this.syncLayoutParameters();
    }
  }

  class SizingMovingAnimation extends SimpleTransformAnimation
  {
    float _dstHeight;
    float _dstLeft;
    float _dstTop;
    float _dstWidth;
    float _srcHeight;
    float _srcLeft;
    float _srcTop;
    float _srcWidth;

    public SizingMovingAnimation(XulViewRender arg2)
    {
      super();
    }

    public SizingMovingAnimation(XulViewRender paramITransformer, ITransformer arg3)
    {
      super(localITransformer);
    }

    public void restoreValue()
    {
      if (XulViewRender.this._rect == null);
      do
      {
        return;
        if (XulViewRender.this._hasMovingAnimation())
        {
          int k = XulUtils.roundToInt(this._srcLeft + (this._dstLeft - this._srcLeft) * this._val / this._destVal);
          int m = XulUtils.roundToInt(this._srcTop + (this._dstTop - this._srcTop) * this._val / this._destVal);
          XulViewRender.this._rect.offsetTo(k, m);
          XulViewRender.this._setScaledRectChanged();
          XulViewRender.this.setUpdateLayout();
        }
      }
      while (!XulViewRender.this._hasSizingAnimation());
      int i = XulUtils.roundToInt(this._srcWidth + (this._dstWidth - this._srcWidth) * this._val / this._destVal);
      int j = XulUtils.roundToInt(this._srcHeight + (this._dstHeight - this._srcHeight) * this._val / this._destVal);
      XulUtils.resizeRect(XulViewRender.this._rect, i, j);
      XulViewRender.this._setScaledRectChanged();
      XulViewRender.this.setUpdateLayout();
    }

    public void startAnimation()
    {
      if ((!XulViewRender.this._hasSizingMovingAnimation()) || (XulViewRender.this._isInvisible()));
      do
      {
        do
        {
          do
            return;
          while (isRunning());
          storeDest();
        }
        while ((this._dstWidth >= 2.147484E+009F) || (this._dstHeight >= 2.147484E+009F));
        this._val = 0.0F;
        if ((XulViewRender.this._hasSizingAnimation()) && (!Float.isNaN(this._srcWidth)) && (!Float.isNaN(this._srcHeight)) && ((!XulUtils.isEqual(this._srcWidth, this._dstWidth)) || (!XulUtils.isEqual(this._srcHeight, this._dstHeight))))
        {
          super.startAnimation();
          XulViewRender.this.addAnimation(XulViewRender.this._sizingMovingAnimation);
          return;
        }
      }
      while ((!XulViewRender.this._hasMovingAnimation()) || (Float.isNaN(this._srcLeft)) || (Float.isNaN(this._srcTop)) || ((XulUtils.isEqual(this._srcLeft, this._dstLeft)) && (XulUtils.isEqual(this._srcTop, this._dstTop))));
      super.startAnimation();
      XulViewRender.this.addAnimation(XulViewRender.this._sizingMovingAnimation);
    }

    public void storeDest()
    {
      this._destVal = 100.0F;
      if (XulViewRender.this._rect != null)
      {
        this._dstLeft = XulViewRender.this._rect.left;
        this._dstTop = XulViewRender.this._rect.top;
        this._dstWidth = XulUtils.calRectWidth(XulViewRender.this._rect);
        this._dstHeight = XulUtils.calRectHeight(XulViewRender.this._rect);
        float f = Math.max(Math.max(Math.abs(this._dstHeight - this._srcHeight), Math.abs(this._dstWidth - this._srcWidth)), Math.max(Math.abs(this._dstLeft - this._srcLeft), Math.abs(this._dstTop - this._srcTop)));
        if ((!Float.isNaN(f)) && (f > 0.0F))
          this._destVal = f;
      }
    }

    public void storeSrc()
    {
      this._srcVal = 0.0F;
      if (XulViewRender.this._rect != null)
      {
        this._srcLeft = XulViewRender.this._rect.left;
        this._srcTop = XulViewRender.this._rect.top;
        this._srcWidth = XulUtils.calRectWidth(XulViewRender.this._rect);
        this._srcHeight = XulUtils.calRectHeight(XulViewRender.this._rect);
        return;
      }
      this._srcLeft = (0.0F / 0.0F);
      this._srcTop = (0.0F / 0.0F);
      this._srcWidth = (0.0F / 0.0F);
      this._srcHeight = (0.0F / 0.0F);
    }

    public boolean updateAnimation(long paramLong)
    {
      if (!isRunning())
        return false;
      if (XulViewRender.this._isInvisible())
      {
        this._val = this._destVal;
        restoreValue();
        stopAnimation();
        return false;
      }
      if (!XulViewRender.this._hasSizingMovingAnimation())
      {
        stopAnimation();
        return false;
      }
      return super.updateAnimation(paramLong);
    }
  }

  static class _QuiverInfo
  {
    static final int DEFAULT_DURATION = 480;
    int _duration = 480;
    String _mode = "linear";
    float[] _params = null;
    float _quiverX = 0.0F;
    float _quiverXDelta = 0.0F;
    float _quiverY = 0.0F;
    float _quiverYDelta = 0.0F;

    public _QuiverInfo(float paramFloat1, float paramFloat2, int paramInt, String paramString, float[] paramArrayOfFloat)
    {
      this._quiverX = paramFloat1;
      this._quiverY = paramFloat2;
      this._duration = paramInt;
      this._mode = paramString;
      this._params = paramArrayOfFloat;
    }

    boolean isQuivering()
    {
      return (Math.abs(this._quiverYDelta) > 0.5F) || (Math.abs(this._quiverXDelta) > 0.5F);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.XulViewRender
 * JD-Core Version:    0.6.2
 */