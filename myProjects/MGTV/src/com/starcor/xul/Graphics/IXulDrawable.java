package com.starcor.xul.Graphics;

import android.graphics.Rect;
import android.graphics.RectF;

public abstract interface IXulDrawable
{
  public abstract boolean draw(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2);

  public abstract boolean draw(XulDC paramXulDC, RectF paramRectF, float paramFloat1, float paramFloat2);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Graphics.IXulDrawable
 * JD-Core Version:    0.6.2
 */