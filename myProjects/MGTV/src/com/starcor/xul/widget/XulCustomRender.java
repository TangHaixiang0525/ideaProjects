package com.starcor.xul.widget;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Render.Drawer.IXulAnimation;
import com.starcor.xul.Render.XulImageRender;
import com.starcor.xul.Render.XulImageRender.LayoutElement;
import com.starcor.xul.Render.XulRenderFactory;
import com.starcor.xul.Render.XulRenderFactory.RenderBuilder;
import com.starcor.xul.Render.XulViewRender;
import com.starcor.xul.Utils.XulLayoutHelper.ILayoutElement;
import com.starcor.xul.XulItem;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;

public class XulCustomRender extends XulImageRender
{
  private static final String CUSTOM_ATTR_ANI_COUNT = "indicator-animation-count";
  private static final String CUSTOM_ATTR_ANI_DURATION = "indicator-animation-duration";
  private static final String CUSTOM_RENDER_TYPE = "custom_render";
  private static final float SCALE_RATIO = 0.5F;
  private IndicatorScaleAnimation mIndicatorAnimation = null;

  public XulCustomRender(XulRenderContext paramXulRenderContext, XulItem paramXulItem)
  {
    super(paramXulRenderContext, paramXulItem);
  }

  public static void register()
  {
    XulRenderFactory.registerBuilder("item", "custom_render", new XulRenderFactory.RenderBuilder()
    {
      static
      {
        if (!XulCustomRender.class.desiredAssertionStatus());
        for (boolean bool = true; ; bool = false)
        {
          $assertionsDisabled = bool;
          return;
        }
      }

      protected XulViewRender createRender(XulRenderContext paramAnonymousXulRenderContext, XulView paramAnonymousXulView)
      {
        assert ((paramAnonymousXulView instanceof XulItem));
        return new XulCustomRender(paramAnonymousXulRenderContext, (XulItem)paramAnonymousXulView);
      }
    });
  }

  protected XulLayoutHelper.ILayoutElement createElement()
  {
    return new LayoutElement();
  }

  public void drawBackground(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    if ((this.mIndicatorAnimation != null) && (this.mIndicatorAnimation.hasAnimation()))
    {
      RectF localRectF = getAnimRect();
      XulUtils.offsetRect(localRectF, paramInt1 + this._screenX, paramInt2 + this._screenY);
      Paint localPaint = XulRenderContext.getDefSolidPaint();
      float f1 = this.mIndicatorAnimation.getAnimationProgress();
      int i = 136;
      if (f1 > 0.5D)
        i = (int)((1.0F - f1) / 0.5F * i);
      localPaint.setColor(0 + 16777216 * i);
      float f2 = localRectF.width() * (1.0F + 0.5F * f1) / 2.0F;
      paramXulDC.drawCircle(localRectF.centerX(), localRectF.centerY(), f2, localPaint);
    }
    super.drawBackground(paramXulDC, paramRect, paramInt1, paramInt2);
  }

  public void drawImages(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    if ((this.mIndicatorAnimation != null) && (this.mIndicatorAnimation.hasAnimation()))
    {
      float f = 1.0F - 0.2F * this.mIndicatorAnimation.getAnimationProgress();
      paramXulDC.save();
      RectF localRectF = getAnimRect();
      XulUtils.offsetRect(localRectF, paramInt1 + this._screenX, paramInt2 + this._screenY);
      paramXulDC.scale(f, f, localRectF.centerX(), localRectF.centerY());
      super.drawImages(paramXulDC, paramRect, paramInt1, paramInt2);
      paramXulDC.restore();
      return;
    }
    super.drawImages(paramXulDC, paramRect, paramInt1, paramInt2);
  }

  public void syncData()
  {
    if (!_isDataChanged());
    do
    {
      do
      {
        return;
        super.syncData();
        if (!_hasAnimation())
          break;
      }
      while (this.mIndicatorAnimation != null);
      int i = XulUtils.tryParseInt(this._view.getAttrString("indicator-animation-duration"));
      int j = XulUtils.tryParseInt(this._view.getAttrString("indicator-animation-count"));
      this.mIndicatorAnimation = new IndicatorScaleAnimation();
      this.mIndicatorAnimation.prepareAnimation(i, j);
      addAnimation(this.mIndicatorAnimation);
      return;
    }
    while (this.mIndicatorAnimation == null);
    removeAnimation(this.mIndicatorAnimation);
    this.mIndicatorAnimation = null;
  }

  class IndicatorScaleAnimation
    implements IXulAnimation
  {
    private static final int DEF_ANIMATION_COUNT = 2;
    private static final int DEF_ANIMATION_DURATION = 1000;
    private int mAnimationCount = 0;
    private int mAnimationDuration = 0;
    private float mAnimationProgress = 0.0F;
    private long mBeginTime = 0L;

    public IndicatorScaleAnimation()
    {
    }

    public void animationFinished()
    {
      this.mAnimationProgress = 1.0F;
      XulCustomRender.this.markDirtyView();
      XulCustomRender.this._view.getOwnerPage();
      XulPage.invokeActionNoPopup(XulCustomRender.this._view, "animationFinished");
    }

    public float getAnimationProgress()
    {
      return this.mAnimationProgress;
    }

    public boolean hasAnimation()
    {
      return this.mAnimationCount > 0;
    }

    public void prepareAnimation(int paramInt1, int paramInt2)
    {
      this.mBeginTime = 0L;
      if (paramInt1 > 0)
      {
        this.mAnimationDuration = paramInt1;
        if (paramInt2 <= 0)
          break label31;
      }
      while (true)
      {
        this.mAnimationCount = paramInt2;
        return;
        paramInt1 = 1000;
        break;
        label31: paramInt2 = 2;
      }
    }

    public boolean updateAnimation(long paramLong)
    {
      if (!XulCustomRender.this._hasAnimation())
        return false;
      if (this.mBeginTime == 0L)
      {
        this.mBeginTime = XulCustomRender.this.animationTimestamp();
        return true;
      }
      long l = paramLong - this.mBeginTime;
      if (l > this.mAnimationDuration)
      {
        this.mAnimationCount = (-1 + this.mAnimationCount);
        if (this.mAnimationCount > 0)
        {
          this.mBeginTime = 0L;
          return true;
        }
        animationFinished();
        return false;
      }
      this.mAnimationProgress = (1.0F * (float)l / this.mAnimationDuration);
      XulCustomRender.this.markDirtyView();
      return true;
    }
  }

  protected class LayoutElement extends XulImageRender.LayoutElement
  {
    protected LayoutElement()
    {
      super();
    }

    public int getContentWidth()
    {
      int i = XulCustomRender.this._padding.left + XulCustomRender.this._padding.right;
      return (int)Math.min(590.0D * XulCustomRender.this.getXScalar() - i, super.getContentWidth());
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.widget.XulCustomRender
 * JD-Core Version:    0.6.2
 */