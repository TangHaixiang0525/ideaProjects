package com.starcor.xul.Render.Components;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Render.Drawer.IXulAnimation;
import com.starcor.xul.Render.XulViewRender;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulUtils;

public class SimpleScrollBar extends BaseScrollBar
  implements IXulAnimation
{
  int _bkgColor = -12566464;
  float _bkgCornerRadius = 0.0F;
  float _bkgPaddingBottom = 5.0F;
  float _bkgPaddingLeft = 3.0F;
  float _bkgPaddingRight = 3.0F;
  float _bkgPaddingTop = 5.0F;
  int _color = -855638017;
  int _delay = 3000;
  boolean _isVisible = false;
  float _pos = 1.0F;
  float _radius = 2.0F;
  XulViewRender _render;
  long _resetTime = -1L;
  String _scrollBarDesc;
  int _scrollRangeColor = -871362544;
  int _shadowColor = -16777216;
  float _shadowSize = 0.0F;
  float _size = 4.0F;

  public SimpleScrollBar(float paramFloat1, int paramInt1, float paramFloat2, float paramFloat3, int paramInt2, float paramFloat4, int paramInt3, int paramInt4, float paramFloat5, int paramInt5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, BaseScrollBar.ScrollBarHelper paramScrollBarHelper, XulViewRender paramXulViewRender)
  {
    super(paramScrollBarHelper);
    this._render = paramXulViewRender;
    update(paramFloat1, paramInt1, paramFloat2, paramFloat3, paramInt2, paramFloat4, paramInt3, paramInt4, paramFloat5, paramInt5, paramFloat6, paramFloat7, paramFloat8, paramFloat9);
  }

  public static SimpleScrollBar buildScrollBar(BaseScrollBar paramBaseScrollBar, String paramString, BaseScrollBar.ScrollBarHelper paramScrollBarHelper, XulViewRender paramXulViewRender)
  {
    if (paramBaseScrollBar != null)
    {
      if (TextUtils.isEmpty(paramString))
      {
        paramBaseScrollBar.recycle();
        return null;
      }
      if (((paramBaseScrollBar instanceof SimpleScrollBar)) && (!((SimpleScrollBar)paramBaseScrollBar).isDescChanged(paramString)))
        return (SimpleScrollBar)paramBaseScrollBar;
    }
    String[] arrayOfString = paramString.split(",");
    SimpleScrollBar localSimpleScrollBar;
    if (arrayOfString.length == 0)
    {
      if (paramBaseScrollBar != null)
        paramBaseScrollBar.recycle();
      localSimpleScrollBar = null;
      return localSimpleScrollBar;
    }
    int i = 0 + 1;
    float f1;
    float f2;
    float f3;
    label110: float f4;
    int j;
    float f5;
    float f6;
    int k;
    float f7;
    String str1;
    int i2;
    label156: String str2;
    label181: String str3;
    label206: String str4;
    label234: String str5;
    label262: String str6;
    label293: float f8;
    String str7;
    label320: int m;
    String str8;
    label350: int n;
    String str9;
    label396: int i1;
    label410: String str11;
    label434: float f14;
    String str12;
    label458: float f13;
    String str13;
    label482: float f15;
    String str14;
    label506: float f16;
    label514: float f9;
    float f11;
    float f10;
    if ("simple".equals(arrayOfString[0]))
    {
      f1 = (float)paramXulViewRender.getXScalar();
      f2 = (float)paramXulViewRender.getYScalar();
      if (paramScrollBarHelper.isVertical())
      {
        f3 = f2;
        f4 = 1.0F;
        j = 3000;
        f5 = 4.0F;
        f6 = 2.0F;
        k = -855638017;
        f7 = 0.0F;
        if (arrayOfString.length < 8)
          break label1162;
        if (i < arrayOfString.length)
          break label613;
        str1 = "";
        i2 = i;
        f4 = XulUtils.tryParseFloat(str1, f4);
        int i3 = arrayOfString.length;
        if (i2 < i3)
          break label629;
        str2 = "";
        j = XulUtils.tryParseInt(str2, j);
        int i5 = arrayOfString.length;
        if (i2 < i5)
          break label649;
        str3 = "";
        f5 = f3 * XulUtils.tryParseFloat(str3, f5);
        int i7 = arrayOfString.length;
        if (i2 < i7)
          break label669;
        str4 = "";
        f6 = f3 * XulUtils.tryParseFloat(str4, f6);
        int i9 = arrayOfString.length;
        if (i2 < i9)
          break label689;
        str5 = "";
        long l1 = k;
        k = (int)XulUtils.tryParseHex(str5, l1);
        int i11 = arrayOfString.length;
        if (i2 < i11)
          break label709;
        str6 = "";
        f8 = f3 * XulUtils.tryParseFloat(str6, 0.0F);
        int i13 = arrayOfString.length;
        if (i2 < i13)
          break label729;
        str7 = "";
        long l2 = 0;
        m = (int)XulUtils.tryParseHex(str7, l2);
        int i15 = arrayOfString.length;
        if (i2 < i15)
          break label749;
        str8 = "";
        long l3 = 0;
        n = (int)XulUtils.tryParseHex(str8, l3);
        if ((arrayOfString.length != 14) && (arrayOfString.length != 11))
          break label789;
        int i17 = arrayOfString.length;
        if (i2 < i17)
          break label769;
        str9 = "";
        long l4 = 0;
        i1 = (int)XulUtils.tryParseHex(str9, l4);
        if (arrayOfString.length < 13)
          break label990;
        int i21 = arrayOfString.length;
        if (i2 < i21)
          break label915;
        str11 = "";
        f14 = XulUtils.tryParseFloat(str11, 0.0F);
        int i23 = arrayOfString.length;
        if (i2 < i23)
          break label935;
        str12 = "";
        f13 = XulUtils.tryParseFloat(str12, 0.0F);
        int i25 = arrayOfString.length;
        if (i2 < i25)
          break label955;
        str13 = "";
        f15 = XulUtils.tryParseFloat(str13, 0.0F);
        int i27 = arrayOfString.length;
        if (i2 < i27)
          break label975;
        str14 = "";
        f16 = XulUtils.tryParseFloat(str14, 0.0F);
        f9 = f13 * f1;
        f11 = f15 * f1;
        f10 = f14 * f2;
      }
    }
    for (float f12 = f16 * f2; ; f12 = 0.0F)
    {
      if (paramBaseScrollBar != null)
        if ((paramBaseScrollBar instanceof SimpleScrollBar))
        {
          localSimpleScrollBar = (SimpleScrollBar)paramBaseScrollBar;
          localSimpleScrollBar.update(f4, j, f5, f6, k, f8, m, n, f7, i1, f9, f10, f11, f12);
        }
      while (true)
      {
        localSimpleScrollBar.reset();
        localSimpleScrollBar.saveDesc(paramString);
        break;
        f3 = f1;
        break label110;
        label613: i2 = i + 1;
        str1 = arrayOfString[i];
        break label156;
        label629: int i4 = i2 + 1;
        str2 = arrayOfString[i2];
        i2 = i4;
        break label181;
        label649: int i6 = i2 + 1;
        str3 = arrayOfString[i2];
        i2 = i6;
        break label206;
        label669: int i8 = i2 + 1;
        str4 = arrayOfString[i2];
        i2 = i8;
        break label234;
        label689: int i10 = i2 + 1;
        str5 = arrayOfString[i2];
        i2 = i10;
        break label262;
        label709: int i12 = i2 + 1;
        str6 = arrayOfString[i2];
        i2 = i12;
        break label293;
        label729: int i14 = i2 + 1;
        str7 = arrayOfString[i2];
        i2 = i14;
        break label320;
        label749: int i16 = i2 + 1;
        str8 = arrayOfString[i2];
        i2 = i16;
        break label350;
        label769: int i18 = i2 + 1;
        str9 = arrayOfString[i2];
        i2 = i18;
        break label396;
        label789: if (arrayOfString.length != 15)
        {
          int i32 = arrayOfString.length;
          f7 = 0.0F;
          i1 = 0;
          if (i32 != 12)
            break label410;
        }
        int i28 = arrayOfString.length;
        String str15;
        label831: String str16;
        if (i2 >= i28)
        {
          str15 = "";
          long l5 = 0;
          i1 = (int)XulUtils.tryParseHex(str15, l5);
          int i30 = arrayOfString.length;
          if (i2 < i30)
            break label895;
          str16 = "";
        }
        while (true)
        {
          f7 = f3 * XulUtils.tryParseFloat(str16, 0.0F);
          break;
          int i29 = i2 + 1;
          str15 = arrayOfString[i2];
          i2 = i29;
          break label831;
          label895: int i31 = i2 + 1;
          str16 = arrayOfString[i2];
          i2 = i31;
        }
        label915: int i22 = i2 + 1;
        str11 = arrayOfString[i2];
        i2 = i22;
        break label434;
        label935: int i24 = i2 + 1;
        str12 = arrayOfString[i2];
        i2 = i24;
        break label458;
        label955: int i26 = i2 + 1;
        str13 = arrayOfString[i2];
        i2 = i26;
        break label482;
        label975: (i2 + 1);
        str14 = arrayOfString[i2];
        break label506;
        label990: int i19 = arrayOfString.length;
        f13 = 0.0F;
        f14 = 0.0F;
        f15 = 0.0F;
        f16 = 0.0F;
        if (i19 < 10)
          break label514;
        int i20 = arrayOfString.length;
        if (i2 >= i20);
        for (String str10 = ""; ; str10 = arrayOfString[i2])
        {
          f16 = XulUtils.tryParseFloat(str10, 0.0F);
          f15 = f16;
          f14 = f16;
          f13 = f16;
          break;
          (i2 + 1);
        }
        paramBaseScrollBar.recycle();
        localSimpleScrollBar = new SimpleScrollBar(f4, j, f5, f6, k, f8, m, n, f7, i1, f9, f10, f11, f12, paramScrollBarHelper, paramXulViewRender);
        continue;
        localSimpleScrollBar = new SimpleScrollBar(f4, j, f5, f6, k, f8, m, n, f7, i1, f9, f10, f11, f12, paramScrollBarHelper, paramXulViewRender);
      }
      localSimpleScrollBar = null;
      break;
      label1162: f8 = 0.0F;
      m = 0;
      n = 0;
      f7 = 0.0F;
      i1 = 0;
      f9 = 0.0F;
      f10 = 0.0F;
      f11 = 0.0F;
    }
  }

  private boolean draw(XulDC paramXulDC, float paramFloat1, float paramFloat2)
  {
    if (!this._isVisible)
      return true;
    Rect localRect = this._render.getPadding();
    if (isVertical())
    {
      int k = getContentHeight();
      int m = this._render.getHeight() - localRect.top - localRect.bottom;
      float f6 = m - this._bkgPaddingTop - this._bkgPaddingBottom;
      if (k <= m)
        return true;
      float f7 = (float)this._render.getXScalar();
      float f8 = f7 * this._size;
      float f9 = (this._render.getWidth() - localRect.left - localRect.right - f8 - (this._bkgPaddingLeft + this._bkgPaddingRight)) * this._pos + this._bkgPaddingLeft;
      float f10 = Math.max(f6 * m / k, 2.0F * f8);
      drawScrollBar(localRect, paramXulDC, paramFloat1, paramFloat2, f7, f9, Math.abs((f6 - f10) * getScrollPos() / (k - m)) + this._bkgPaddingTop, f8, f10);
    }
    while (true)
    {
      return true;
      int i = getContentWidth();
      int j = this._render.getWidth() - localRect.left - localRect.right;
      float f1 = j - this._bkgPaddingLeft - this._bkgPaddingRight;
      if (i <= j)
        return true;
      float f2 = (float)this._render.getYScalar();
      float f3 = f2 * this._size;
      float f4 = (this._render.getHeight() - localRect.top - localRect.bottom - f3 - (this._bkgPaddingTop + this._bkgPaddingBottom)) * this._pos + this._bkgPaddingTop;
      float f5 = Math.max(f1 * j / i, 2.0F * f3);
      drawScrollBar(localRect, paramXulDC, paramFloat1, paramFloat2, f2, Math.abs((f1 - f5) * getScrollPos() / (i - j)) + this._bkgPaddingLeft, f4, f5, f3);
    }
  }

  private void drawScrollBar(Rect paramRect, XulDC paramXulDC, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7)
  {
    float f1 = paramFloat1 + (paramRect.left + this._render.getScreenX());
    float f2 = paramFloat2 + (paramRect.top + this._render.getScreenY());
    int i = XulUtils.roundToInt(f1 + paramFloat4);
    int j = XulUtils.roundToInt(f2 + paramFloat5);
    int k = XulUtils.roundToInt(paramFloat6);
    int m = XulUtils.roundToInt(paramFloat7);
    float f7;
    float f8;
    float f9;
    float f10;
    float f3;
    float f4;
    float f5;
    float f6;
    label257: Paint localPaint1;
    if ((0xFF000000 & this._bkgColor) != 0)
    {
      Paint localPaint3 = XulRenderContext.getDefSolidPaint();
      localPaint3.setColor(this._bkgColor);
      if (isVertical())
      {
        f7 = i - this._bkgPaddingLeft;
        f8 = k + this._bkgPaddingLeft + this._bkgPaddingRight;
        f9 = f2;
        f10 = this._render.getHeight() - paramRect.top - paramRect.bottom;
        paramXulDC.drawRoundRect(f7, f9, f8, f10, paramFloat3 * this._bkgCornerRadius, paramFloat3 * this._bkgCornerRadius, localPaint3);
      }
    }
    else
    {
      if ((0xFF000000 & this._scrollRangeColor) != 0)
      {
        Paint localPaint2 = XulRenderContext.getDefSolidPaint();
        localPaint2.setColor(this._scrollRangeColor);
        if (!isVertical())
          break label421;
        f3 = i;
        f4 = k;
        f5 = f2 + this._bkgPaddingTop;
        f6 = this._render.getHeight() - paramRect.top - paramRect.bottom - this._bkgPaddingTop - this._bkgPaddingBottom;
        paramXulDC.drawRoundRect(f3, f5, f4, f6, paramFloat3 * this._radius, paramFloat3 * this._radius, localPaint2);
      }
      if ((this._shadowSize <= 0.5D) || ((0xFF000000 & this._shadowColor) == 0))
        break label473;
      localPaint1 = XulRenderContext.getDefSolidShadowPaint();
      localPaint1.setShadowLayer(this._shadowSize, 0.0F, 0.0F, this._shadowColor);
    }
    while (true)
    {
      localPaint1.setColor(this._color);
      paramXulDC.drawRoundRect(i, j, k, m, paramFloat3 * this._radius, paramFloat3 * this._radius, localPaint1);
      return;
      f7 = f1;
      f8 = this._render.getWidth() - paramRect.left - paramRect.right;
      f9 = j - this._bkgPaddingTop;
      f10 = m + this._bkgPaddingTop + this._bkgPaddingBottom;
      break;
      label421: f3 = f1 + this._bkgPaddingLeft;
      f4 = this._render.getWidth() - paramRect.left - paramRect.right - this._bkgPaddingLeft - this._bkgPaddingRight;
      f5 = j;
      f6 = m;
      break label257;
      label473: localPaint1 = XulRenderContext.getDefSolidPaint();
    }
  }

  private boolean isDescChanged(String paramString)
  {
    return !paramString.equals(this._scrollBarDesc);
  }

  private void saveDesc(String paramString)
  {
    this._scrollBarDesc = paramString;
  }

  public boolean draw(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    return draw(paramXulDC, paramInt1, paramInt2);
  }

  public boolean draw(XulDC paramXulDC, RectF paramRectF, float paramFloat1, float paramFloat2)
  {
    return draw(paramXulDC, paramFloat1, paramFloat2);
  }

  public void recycle()
  {
    this._resetTime = 0L;
    this._isVisible = false;
    this._delay = 0;
  }

  public void reset()
  {
    if (!this._isVisible)
    {
      this._isVisible = true;
      if (this._delay >= 0)
        this._render.addAnimation(this);
      this._render.markDirtyView();
    }
    this._resetTime = XulUtils.timestamp();
  }

  public void update(float paramFloat1, int paramInt1, float paramFloat2, float paramFloat3, int paramInt2, float paramFloat4, int paramInt3, int paramInt4, float paramFloat5, int paramInt5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    this._pos = paramFloat1;
    this._delay = paramInt1;
    this._size = paramFloat2;
    this._color = paramInt2;
    this._shadowColor = paramInt3;
    this._shadowSize = paramFloat4;
    this._radius = paramFloat3;
    this._bkgColor = paramInt4;
    this._bkgCornerRadius = paramFloat5;
    this._scrollRangeColor = paramInt5;
    this._bkgPaddingLeft = paramFloat6;
    this._bkgPaddingTop = paramFloat7;
    this._bkgPaddingRight = paramFloat8;
    this._bkgPaddingBottom = paramFloat9;
  }

  public boolean updateAnimation(long paramLong)
  {
    if ((this._isVisible) && (this._resetTime > 0L) && (paramLong - this._resetTime > this._delay))
    {
      this._isVisible = false;
      this._resetTime = -1L;
      this._render.markDirtyView();
      return false;
    }
    return true;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Components.SimpleScrollBar
 * JD-Core Version:    0.6.2
 */