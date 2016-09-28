package com.starcor.xul.Render.Text;

import android.graphics.Paint;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.XulWorker.DrawableItem;

public abstract class XulTextRenderer
{
  public abstract XulWorker.DrawableItem collectPendingImageItem();

  public abstract void drawText(XulDC paramXulDC, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public abstract XulTextEditor edit();

  public abstract float getHeight();

  public abstract float getLineHeight();

  public abstract Paint getTextPaint();

  public abstract float getWidth();

  public abstract boolean isAutoWrap();

  public abstract boolean isDrawingEllipsis();

  public abstract boolean isEmpty();

  public abstract boolean isMultiline();

  public abstract void stopAnimation();

  public abstract class XulTextEditor
  {
    public XulTextEditor()
    {
    }

    public abstract boolean defAutoWrap();

    public abstract boolean defDrawEllipsis();

    public abstract boolean defMultiline();

    public abstract void finish(boolean paramBoolean);

    public abstract XulTextEditor fontScaleX(float paramFloat);

    public abstract XulTextEditor setAutoWrap(boolean paramBoolean);

    public abstract XulTextEditor setDrawEllipsis(boolean paramBoolean);

    public abstract XulTextEditor setEndIndent(float paramFloat);

    public abstract XulTextEditor setFixHalfChar(boolean paramBoolean);

    public abstract XulTextEditor setFontAlignment(float paramFloat1, float paramFloat2);

    public abstract XulTextEditor setFontColor(int paramInt);

    public abstract XulTextEditor setFontFace(String paramString);

    public abstract XulTextEditor setFontShadow(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt);

    public abstract XulTextEditor setFontSize(float paramFloat);

    public abstract XulTextEditor setFontStrikeThrough(boolean paramBoolean);

    public abstract XulTextEditor setFontWeight(float paramFloat);

    public abstract XulTextEditor setItalic(boolean paramBoolean);

    public abstract XulTextEditor setLineHeightScalar(float paramFloat);

    public abstract XulTextEditor setMultiline(boolean paramBoolean);

    public abstract XulTextEditor setStartIndent(float paramFloat);

    public abstract XulTextEditor setSuperResample(float paramFloat);

    public abstract XulTextEditor setText(String paramString);

    public abstract XulTextEditor setUnderline(boolean paramBoolean);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Text.XulTextRenderer
 * JD-Core Version:    0.6.2
 */