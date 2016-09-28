package com.starcor.hunan.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

public class LightTextView extends TextView
{
  private static final int DEFAULT_COLOR = -10395295;
  private static final int SELECTED_COLOR = -13069569;
  private boolean flag = false;
  private boolean selected = false;
  private int textSize = 24;

  public LightTextView(Context paramContext)
  {
    super(paramContext);
    setTextColor(-10395295);
    setTextSize(0, this.textSize);
    setGravity(17);
  }

  public LightTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setTextColor(-10395295);
    setTextSize(0, this.textSize);
    setGravity(17);
  }

  public LightTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setTextColor(-10395295);
    setTextSize(0, this.textSize);
    setGravity(17);
  }

  public boolean isFlag()
  {
    return this.flag;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.selected)
    {
      getPaint().setFakeBoldText(true);
      getPaint().setTextSize(1 + this.textSize);
      getPaint().setShadowLayer(3.0F, 1.0F, 1.0F, -16777216);
    }
    while (true)
    {
      super.onDraw(paramCanvas);
      return;
      getPaint().setTextSize(this.textSize);
      getPaint().setFakeBoldText(false);
      getPaint().setShadowLayer(0.0F, 0.0F, 0.0F, 0);
    }
  }

  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
    setSelected(paramBoolean);
  }

  public void setFlag(boolean paramBoolean)
  {
    this.flag = paramBoolean;
    if (paramBoolean)
    {
      if (this.selected)
      {
        setBackgroundResource(2130837664);
        return;
      }
      setTextColor(-1);
      setBackgroundResource(2130837663);
      return;
    }
    setTextColor(-10395295);
    setBackgroundResource(0);
  }

  public void setSelected(boolean paramBoolean)
  {
    super.setSelected(paramBoolean);
    this.selected = paramBoolean;
    if ((this.selected) || (this.flag))
    {
      setTextColor(-1);
      if ((this.flag) && (!this.selected))
      {
        setBackgroundResource(2130837663);
        return;
      }
      setBackgroundResource(2130837664);
      return;
    }
    setTextColor(-10395295);
    setBackgroundDrawable(null);
  }

  public void setTextSize(float paramFloat)
  {
    super.setTextSize(paramFloat);
    this.textSize = ((int)paramFloat);
  }

  public void setTextSize(int paramInt, float paramFloat)
  {
    super.setTextSize(paramInt, paramFloat);
    this.textSize = ((int)paramFloat);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.LightTextView
 * JD-Core Version:    0.6.2
 */