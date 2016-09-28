package com.starcor.xul.Render.Text;

import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.text.TextUtils;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Render.XulViewRender;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulWorker.DrawableItem;
import java.util.ArrayList;
import java.util.HashSet;

public class XulSimpleTextRenderer extends XulBasicTextRenderer
{
  private static final String TAG = XulSimpleTextRenderer.class.getSimpleName();
  private static HashSet<Character> _chSetA;
  private static HashSet<Character> _chSetB;
  private static Paint.FontMetrics _tmpFontMetrics;
  private static float[] tmpWidthArray = new float[16];
  private SimpleTextEditor _editor;
  ArrayList<_lineInfo> _lines = null;
  float[] _textWidths;

  static
  {
    _tmpFontMetrics = new Paint.FontMetrics();
    _chSetA = new HashSet();
    _chSetA.add(Character.valueOf(','));
    _chSetA.add(Character.valueOf('.'));
    _chSetA.add(Character.valueOf('`'));
    _chSetA.add(Character.valueOf('!'));
    _chSetA.add(Character.valueOf('?'));
    _chSetA.add(Character.valueOf(')'));
    _chSetA.add(Character.valueOf('}'));
    _chSetA.add(Character.valueOf(']'));
    _chSetA.add(Character.valueOf(':'));
    _chSetA.add(Character.valueOf(';'));
    _chSetA.add(Character.valueOf('>'));
    _chSetA.add(Character.valueOf('。'));
    _chSetA.add(Character.valueOf(65292));
    _chSetA.add(Character.valueOf(65311));
    _chSetA.add(Character.valueOf('”'));
    _chSetA.add(Character.valueOf(65307));
    _chSetA.add(Character.valueOf(65306));
    _chSetA.add(Character.valueOf('》'));
    _chSetA.add(Character.valueOf(65289));
    _chSetA.add(Character.valueOf('、'));
    _chSetA.add(Character.valueOf(65310));
    _chSetA.add(Character.valueOf(65341));
    _chSetA.add(Character.valueOf(65294));
    _chSetA.add(Character.valueOf(65292));
    _chSetA.add(Character.valueOf(65287));
    _chSetA.add(Character.valueOf(65106));
    _chSetA.add(Character.valueOf(65116));
    _chSetA.add(Character.valueOf(65118));
    _chSetA.add(Character.valueOf('′'));
    _chSetA.add(Character.valueOf('•'));
    _chSetA.add(Character.valueOf(65373));
    _chSetA.add(Character.valueOf('’'));
    _chSetA.add(Character.valueOf('…'));
    _chSetA.add(Character.valueOf('〉'));
    _chSetA.add(Character.valueOf('〉'));
    _chSetA.add(Character.valueOf('》'));
    _chSetA.add(Character.valueOf('」'));
    _chSetA.add(Character.valueOf('』'));
    _chSetA.add(Character.valueOf('】'));
    _chSetA.add(Character.valueOf('〕'));
    _chSetA.add(Character.valueOf('〗'));
    _chSetA.add(Character.valueOf('〞'));
    _chSetB = new HashSet();
    _chSetB.add(Character.valueOf('('));
    _chSetB.add(Character.valueOf('<'));
    _chSetB.add(Character.valueOf('['));
    _chSetB.add(Character.valueOf('{'));
    _chSetB.add(Character.valueOf('±'));
    _chSetB.add(Character.valueOf('‘'));
    _chSetB.add(Character.valueOf('“'));
    _chSetB.add(Character.valueOf('‹'));
    _chSetB.add(Character.valueOf('∈'));
    _chSetB.add(Character.valueOf('∏'));
    _chSetB.add(Character.valueOf('∑'));
    _chSetB.add(Character.valueOf('∕'));
    _chSetB.add(Character.valueOf('∠'));
    _chSetB.add(Character.valueOf('∧'));
    _chSetB.add(Character.valueOf('∨'));
    _chSetB.add(Character.valueOf('∩'));
    _chSetB.add(Character.valueOf('∪'));
    _chSetB.add(Character.valueOf('∫'));
    _chSetB.add(Character.valueOf('∫'));
    _chSetB.add(Character.valueOf('∮'));
    _chSetB.add(Character.valueOf('∴'));
    _chSetB.add(Character.valueOf('∵'));
    _chSetB.add(Character.valueOf('≤'));
    _chSetB.add(Character.valueOf('≥'));
    _chSetB.add(Character.valueOf('≦'));
    _chSetB.add(Character.valueOf('≧'));
    _chSetB.add(Character.valueOf('≮'));
    _chSetB.add(Character.valueOf('≯'));
    _chSetB.add(Character.valueOf('∽'));
    _chSetB.add(Character.valueOf('≈'));
    _chSetB.add(Character.valueOf('≌'));
    _chSetB.add(Character.valueOf('≠'));
    _chSetB.add(Character.valueOf('≡'));
    _chSetB.add(Character.valueOf('⊙'));
    _chSetB.add(Character.valueOf('⊿'));
    _chSetB.add(Character.valueOf('⊥'));
    _chSetB.add(Character.valueOf('〈'));
    _chSetB.add(Character.valueOf('《'));
    _chSetB.add(Character.valueOf('「'));
    _chSetB.add(Character.valueOf('『'));
    _chSetB.add(Character.valueOf('【'));
    _chSetB.add(Character.valueOf('〔'));
    _chSetB.add(Character.valueOf('〖'));
    _chSetB.add(Character.valueOf('〝'));
    _chSetB.add(Character.valueOf(65113));
    _chSetB.add(Character.valueOf(65115));
    _chSetB.add(Character.valueOf(65117));
    _chSetB.add(Character.valueOf(65124));
    _chSetB.add(Character.valueOf(65288));
    _chSetB.add(Character.valueOf(65308));
    _chSetB.add(Character.valueOf(65309));
    _chSetB.add(Character.valueOf(65310));
    _chSetB.add(Character.valueOf(65339));
    _chSetB.add(Character.valueOf(65371));
  }

  public XulSimpleTextRenderer(XulViewRender paramXulViewRender)
  {
    super(paramXulViewRender);
  }

  private static boolean isRTLChar(char paramChar)
  {
    return Character.UnicodeBlock.of(paramChar) == Character.UnicodeBlock.ARABIC;
  }

  private void multilineLayout(Paint paramPaint, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    if (this._lines != null)
      return;
    this._lines = new ArrayList();
    float f1 = 0.0F;
    float f2 = -this._textBaseLineTop;
    this._textWidth = 0.0F;
    int i = 0;
    char c1 = '\000';
    int j = this._text.length();
    float f3 = 0.0F;
    if ((this._autoWrap) && (paramBoolean))
      while (true)
      {
        f1 = 0.0F;
        int i1;
        float f6;
        if (i < j)
        {
          i1 = i + 1;
          int i4;
          do
          {
            if (i1 >= j)
              break;
            i4 = this._text.charAt(i1);
            i1++;
          }
          while (i4 != 10);
          f6 = paramFloat1;
          if ((i != 0) || (this._startIndent <= 0.5F))
            break label258;
          f6 -= this._startIndent;
        }
        int i2;
        label258: for (f3 = this._startIndent; ; f3 = 0.0F)
        {
          i2 = paramPaint.breakText(this._text, i, i1, true, f6, tmpWidthArray);
          f1 = 0.0F;
          if (i2 > 0)
            break label264;
          this._textHeight = XulUtils.ceilToInt(f2 + this._textBaseLineTop);
          if (j > i)
          {
            _lineInfo local_lineInfo3 = new _lineInfo(i, j, 0.0F + f3, f2 + this._textBaseLineTop);
            local_lineInfo3.lineWidth = f1;
            this._textHeight += this._textLineHeight;
            this._lines.add(local_lineInfo3);
          }
          if (!this._autoWrap)
            break;
          this._textWidth = paramFloat1;
          return;
        }
        label264: int i3 = i + i2;
        float f7 = tmpWidthArray[0];
        _lineInfo local_lineInfo4 = new _lineInfo(i, i3, 0.0F + f3, f2 + this._textBaseLineTop);
        local_lineInfo4.lineWidth = f7;
        this._lines.add(local_lineInfo4);
        f2 += this._textLineHeight;
        i = i3;
      }
    boolean bool1 = this._startIndent < 0.5F;
    f3 = 0.0F;
    if (bool1)
      f3 = this._startIndent;
    int k = 0;
    label361: char c2;
    if ((1 != 0) && (k < j))
    {
      c2 = this._text.charAt(k);
      if (c2 != '\r')
        break label399;
      c1 = '\000';
    }
    while (true)
    {
      k++;
      break label361;
      break;
      label399: if (c2 == '\n')
      {
        _lineInfo local_lineInfo1 = new _lineInfo(i, k, 0.0F + f3, f2 + this._textBaseLineTop);
        local_lineInfo1.lineWidth = f1;
        this._lines.add(local_lineInfo1);
        f2 += this._textLineHeight;
        i = k + 1;
        f1 = 0.0F;
        c1 = '\000';
        f3 = 0.0F;
      }
      else
      {
        f1 += this._textWidths[k];
        if ((f1 + (0.0F + f3) >= paramFloat1) && (this._autoWrap))
        {
          int m = k;
          float f4 = f1;
          int n = k - i;
          float f5 = 0.0F;
          if (n > 1)
          {
            if (!_chSetA.contains(Character.valueOf(c2)))
              break label669;
            f5 = 0.0F;
            if (c1 != 0)
            {
              boolean bool2 = _chSetA.contains(Character.valueOf(c1));
              f5 = 0.0F;
              if (!bool2)
              {
                m = k - 1;
                f5 = this._textWidths[m];
                f4 -= f5;
              }
            }
          }
          while (true)
          {
            _lineInfo local_lineInfo2 = new _lineInfo(i, m, 0.0F + f3, f2 + this._textBaseLineTop);
            local_lineInfo2.lineWidth = f4;
            f1 = f5 + this._textWidths[k];
            this._lines.add(local_lineInfo2);
            f2 += this._textLineHeight;
            i = m;
            c1 = '\000';
            f3 = 0.0F;
            break;
            label669: f5 = 0.0F;
            if (c1 != 0)
            {
              boolean bool3 = _chSetB.contains(Character.valueOf(c1));
              f5 = 0.0F;
              if (bool3)
              {
                m = k - 1;
                f5 = this._textWidths[m];
                f4 -= f5;
              }
            }
          }
        }
        c1 = c2;
      }
    }
  }

  public XulWorker.DrawableItem collectPendingImageItem()
  {
    return null;
  }

  public void drawText(XulDC paramXulDC, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    if (TextUtils.isEmpty(this._text))
      return;
    if (this._multiline)
    {
      drawTextMultiLine(paramXulDC, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
      return;
    }
    drawTextSingleLine(paramXulDC, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }

  public void drawTextMultiLine(XulDC paramXulDC, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    boolean bool1 = this._render.isRTL();
    Paint localPaint = _getTextPaint();
    multilineLayout(localPaint, paramFloat3, paramFloat4, bool1);
    float f1 = this._superResample;
    float f2 = f1 - 1.0F;
    int i;
    int j;
    int k;
    label97: _lineInfo local_lineInfo;
    float f3;
    if (Math.abs(f2) > 0.01F)
    {
      i = 1;
      if (i != 0)
      {
        localPaint = _getTextPaint(f1);
        paramXulDC.scale(1.0F / f1, 1.0F / f1, paramFloat1, paramFloat2 - this._textBaseLineTop);
      }
      j = this._lines.size();
      k = 0;
      if (k >= j)
        break label159;
      local_lineInfo = (_lineInfo)this._lines.get(k);
      f3 = paramFloat2 + local_lineInfo.yOffset;
      if (f3 + this._textLineHeight >= 0.0F)
        break label151;
    }
    while (true)
    {
      k++;
      break label97;
      i = 0;
      break;
      label151: if (f3 > paramFloat4)
        label159: return;
      float f4 = paramFloat1 + local_lineInfo.xOffset;
      int m;
      float f6;
      float f7;
      float f10;
      int n;
      int i2;
      label321: float f12;
      label366: int i1;
      if (f3 + this._textLineHeight - this._textBaseLineTop > paramFloat4)
      {
        m = 1;
        float f5 = 0.0F;
        if (m != 0)
        {
          boolean bool4 = this._endIndent < 0.0F;
          f5 = 0.0F;
          if (bool4)
            f5 = this._endIndent;
        }
        f6 = f2 * local_lineInfo.xOffset;
        f7 = f3 - this._textBaseLineTop + f2 * local_lineInfo.yOffset;
        if ((!this._drawEllipsis) || ((this._autoWrap) && (m == 0)))
          break label655;
        f10 = 0.0F;
        n = local_lineInfo.tail;
        if (f5 + (f4 + local_lineInfo.lineWidth + this._ellipsisWidth) <= paramFloat3)
          break label561;
        float f11 = paramFloat3 - this._ellipsisWidth - f4 - f5;
        i2 = local_lineInfo.head;
        int i3 = local_lineInfo.tail;
        if (i2 < i3)
        {
          f12 = this._textWidths[i2];
          if ((f10 <= 0.0F) || (f10 + f12 < f11))
            break label548;
          n = i2;
        }
        if ((n >= local_lineInfo.tail) && ((!this._autoWrap) || (j <= k + 1)))
          break label628;
        if ((!bool1) && (!isRTLChar(this._text.charAt(n - 1))))
          break label571;
        i1 = 1;
        label417: if (i1 == 0)
          break label577;
        Rect localRect = XulDC._tmpRc0;
        localPaint.getTextBounds(this._text, local_lineInfo.head, n, localRect);
        if ((f5 + (XulUtils.calRectWidth(localRect) + this._ellipsisWidth) > f10) && (n > local_lineInfo.head))
          n--;
        paramXulDC.drawText(this._text, local_lineInfo.head, n, f4 + f6 + f1 * this._ellipsisWidth, f7, localPaint);
        paramXulDC.drawText("…", 0, 1, f4, f7, localPaint);
      }
      while (true)
      {
        if ((!this._autoWrap) || (j <= k + 1))
          break label653;
        return;
        m = 0;
        break;
        label548: f10 += f12;
        i2++;
        break label321;
        label561: f10 = local_lineInfo.lineWidth;
        break label366;
        label571: i1 = 0;
        break label417;
        label577: paramXulDC.drawText(this._text, local_lineInfo.head, n, f4 + f6, f7, localPaint);
        paramXulDC.drawText("…", 0, 1, f4 + f6 + f10 * f1, f7, localPaint);
        continue;
        label628: paramXulDC.drawText(this._text, local_lineInfo.head, n, f4 + f6, f7, localPaint);
      }
      label653: continue;
      label655: boolean bool2 = local_lineInfo.lineWidth < paramFloat3;
      float f8 = 0.0F;
      if (bool2)
      {
        boolean bool3 = this._fontAlignX < 0.0F;
        f8 = 0.0F;
        if (bool3)
          f8 = this._fontAlignX * (paramFloat3 - local_lineInfo.xOffset - local_lineInfo.lineWidth);
      }
      float f9 = f4 + f6 + f8 * f1;
      paramXulDC.drawText(this._text, local_lineInfo.head, local_lineInfo.tail, f9, f7, localPaint);
    }
  }

  public void drawTextSingleLine(XulDC paramXulDC, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    boolean bool1 = this._render.isRTL();
    Paint localPaint = _getTextPaint();
    float f1 = this._textWidth;
    int i = this._text.length();
    if (((this._fixHalfChar) || (this._drawEllipsis)) && (this._textWidth > paramFloat3))
    {
      float f7 = 0.0F;
      boolean bool2 = this._drawEllipsis;
      float f8 = 0.0F;
      if (bool2)
        f8 = this._ellipsisWidth;
      for (int k = 0; ; k++)
      {
        int m = this._text.length();
        if (k >= m)
          break;
        f7 += this._textWidths[k];
        if (f7 + f8 > paramFloat3)
          break;
        f1 = XulUtils.ceilToInt(f7);
        i = k + 1;
      }
    }
    float f2 = (paramFloat3 - f1) * this._fontAlignX;
    float f3 = (paramFloat4 - this._textHeight) * this._fontAlignY;
    float f4;
    float f5;
    float f6;
    if (bool1)
    {
      if (f1 >= paramFloat3)
        f2 = paramFloat3 - f1;
      f4 = f2 + paramFloat1;
      f5 = f3 - this._textBaseLineTop;
      f6 = this._superResample;
      if (Math.abs(f6 - 1.0F) <= 0.01F)
        break label331;
    }
    label331: for (int j = 1; ; j = 0)
    {
      if (j != 0)
      {
        localPaint = _getTextPaint(f6);
        paramXulDC.scale(1.0F / f6, 1.0F / f6, f4, f5);
      }
      if (this._textWidth < paramFloat3)
        break label337;
      paramXulDC.drawText(this._text, 0, i, f4, f5, localPaint);
      if ((this._textWidth > paramFloat3) && (this._drawEllipsis))
        paramXulDC.drawText("…", 0, 1, f4 + f1 * f6, f5, localPaint);
      return;
      if (f1 < paramFloat3)
        break;
      f2 = 0.0F;
      break;
    }
    label337: paramXulDC.drawText(this._text, 0, this._text.length(), f4 * f6, f5, localPaint);
  }

  public XulTextRenderer.XulTextEditor edit()
  {
    if (this._editor == null)
      this._editor = new SimpleTextEditor();
    while (true)
    {
      return this._editor;
      this._editor.reset();
    }
  }

  public void stopAnimation()
  {
  }

  public class SimpleTextEditor extends XulBasicTextRenderer.BasicTextEditor
  {
    static
    {
      if (!XulSimpleTextRenderer.class.desiredAssertionStatus());
      for (boolean bool = true; ; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }

    public SimpleTextEditor()
    {
      super();
    }

    public void finish(boolean paramBoolean)
    {
      boolean bool = XulSimpleTextRenderer.this._render.isRTL();
      Paint localPaint;
      int m;
      float f2;
      int i2;
      int i3;
      int i6;
      if ((this._testAndSetAnyChanged) || (XulSimpleTextRenderer.this._textWidths == null) || ((XulSimpleTextRenderer.this._multiline) && (XulSimpleTextRenderer.this._autoWrap) && (paramBoolean)))
      {
        Rect localRect1 = XulSimpleTextRenderer.this._render.getDrawingRect();
        Rect localRect2 = XulSimpleTextRenderer.this._render.getPadding();
        int i = XulUtils.calRectWidth(localRect1);
        if (localRect2 == null);
        int k;
        for (int j = 0; ; j = localRect2.left + localRect2.right)
        {
          k = i - j;
          localPaint = XulSimpleTextRenderer.this._getTextPaint();
          localPaint.getFontMetrics(XulSimpleTextRenderer._tmpFontMetrics);
          XulSimpleTextRenderer.this._textBaseLineTop = XulSimpleTextRenderer._tmpFontMetrics.top;
          XulSimpleTextRenderer.this._textLineHeight = XulUtils.ceilToInt(XulSimpleTextRenderer._tmpFontMetrics.bottom - XulSimpleTextRenderer._tmpFontMetrics.top);
          XulSimpleTextRenderer localXulSimpleTextRenderer1 = XulSimpleTextRenderer.this;
          localXulSimpleTextRenderer1._textBaseLineTop -= XulSimpleTextRenderer.this._textLineHeight * (XulSimpleTextRenderer.this._lineHeightScalar - 1.0F) / 2.0F;
          XulSimpleTextRenderer localXulSimpleTextRenderer2 = XulSimpleTextRenderer.this;
          localXulSimpleTextRenderer2._textLineHeight *= XulSimpleTextRenderer.this._lineHeightScalar;
          XulSimpleTextRenderer.this._lines = null;
          if (TextUtils.isEmpty(XulSimpleTextRenderer.this._text))
            break label527;
          m = XulSimpleTextRenderer.this._text.length();
          if ((XulSimpleTextRenderer.this._textWidths == null) || (XulSimpleTextRenderer.this._textWidths.length < m))
            XulSimpleTextRenderer.this._textWidths = new float[m];
          localPaint.getTextWidths("…", XulSimpleTextRenderer.this._textWidths);
          XulSimpleTextRenderer.this._ellipsisWidth = XulSimpleTextRenderer.this._textWidths[0];
          int n = localPaint.getTextWidths(XulSimpleTextRenderer.this._text, XulSimpleTextRenderer.this._textWidths);
          if (($assertionsDisabled) || (n == m))
            break;
          throw new AssertionError();
        }
        if (!XulSimpleTextRenderer.this._multiline)
          break label696;
        f2 = 0.0F;
        i2 = 1;
        XulSimpleTextRenderer.this._textWidth = 0.0F;
        if (!XulSimpleTextRenderer.this._autoWrap)
          break label533;
        i3 = k;
        if (!bool)
          break label560;
        i6 = 0;
      }
      while (true)
      {
        int i8;
        if (i6 < m)
        {
          int i7 = i6 + 1;
          int i9;
          do
          {
            if (i7 >= m)
              break;
            i9 = XulSimpleTextRenderer.this._text.charAt(i7);
            i7++;
          }
          while (i9 != 10);
          i8 = localPaint.breakText(XulSimpleTextRenderer.this._text, i6, i7, true, i3, XulSimpleTextRenderer.tmpWidthArray);
          if (i8 > 0);
        }
        else
        {
          XulSimpleTextRenderer.this._textWidth = Math.max(XulSimpleTextRenderer.this._textWidth, XulUtils.ceilToInt(f2));
          XulSimpleTextRenderer.this._textHeight = (i2 * XulSimpleTextRenderer.this._textLineHeight);
          label527: super.finish(paramBoolean);
          return;
          label533: i3 = 2147483647;
          break;
        }
        f2 = XulSimpleTextRenderer.tmpWidthArray[0];
        i6 += i8;
        i2++;
      }
      label560: int i4 = 0;
      label563: int i5;
      if (i4 < m)
      {
        i5 = XulSimpleTextRenderer.this._text.charAt(i4);
        if (i5 != 13)
          break label597;
      }
      while (true)
      {
        i4++;
        break label563;
        break;
        label597: if (i5 == 10)
        {
          XulSimpleTextRenderer.this._textWidth = Math.max(XulSimpleTextRenderer.this._textWidth, XulUtils.ceilToInt(f2));
          f2 = 0.0F;
          i2++;
        }
        float f3 = XulSimpleTextRenderer.this._textWidths[i4];
        if (f2 + f3 > i3)
        {
          XulSimpleTextRenderer.this._textWidth = Math.max(XulSimpleTextRenderer.this._textWidth, XulUtils.ceilToInt(f2));
          f2 = 0.0F;
          i2++;
        }
        f2 += f3;
      }
      label696: float f1 = 0.0F;
      if (bool)
      {
        Rect localRect3 = XulDC._tmpRc0;
        localPaint.getTextBounds(XulSimpleTextRenderer.this._text, 0, XulSimpleTextRenderer.this._text.length(), localRect3);
        f1 = XulUtils.calRectWidth(localRect3);
      }
      while (true)
      {
        XulSimpleTextRenderer.this._textWidth = XulUtils.ceilToInt(f1);
        XulSimpleTextRenderer.this._textHeight = XulSimpleTextRenderer.this._textLineHeight;
        break;
        for (int i1 = 0; i1 < m; i1++)
          f1 += XulSimpleTextRenderer.this._textWidths[i1];
      }
    }
  }

  private static class _lineInfo
  {
    int head = 0;
    float lineWidth = 0.0F;
    int tail = 0;
    float xOffset = 0.0F;
    float yOffset = 0.0F;

    public _lineInfo(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
    {
      this.head = paramInt1;
      this.tail = paramInt2;
      this.xOffset = paramFloat1;
      this.yOffset = paramFloat2;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Text.XulSimpleTextRenderer
 * JD-Core Version:    0.6.2
 */