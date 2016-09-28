package com.starcor.hunan.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;
import com.starcor.hunan.App;

public class HighLightTextView extends TextView
{
  private static final int SELECTED_BACKGOURD_COLOR = -13069569;
  private int DEFAULT_COLOR = -2697514;
  private boolean bgSelectedStyle = false;
  private String focusText = null;
  private boolean hightLight = false;
  private boolean isSelected = false;

  public HighLightTextView(Context paramContext)
  {
    this(paramContext, null);
  }

  public HighLightTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }

  public HighLightTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setTextColor(this.DEFAULT_COLOR);
    setTextSize(0, App.OperationHeight(24));
  }

  private void lightFocus()
  {
    String str = getText().toString();
    if ((this.focusText != null) && (str != null))
    {
      SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(str);
      int i = str.indexOf(this.focusText);
      if (i != -1)
      {
        int j = i + this.focusText.length();
        localSpannableStringBuilder.setSpan(new ForegroundColorSpan(-16776961), i, j, 33);
        setText(localSpannableStringBuilder);
      }
    }
  }

  public boolean isBgSelectedStyle()
  {
    return this.bgSelectedStyle;
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.hightLight)
      getPaint().setShadowLayer(3.0F, 1.0F, 1.0F, -16777216);
    while (true)
    {
      getPaint().setFakeBoldText(this.isSelected);
      super.onDraw(paramCanvas);
      return;
      getPaint().setShadowLayer(0.0F, 0.0F, 0.0F, 0);
    }
  }

  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    if (paramBoolean)
      setSelected(true);
    while (true)
    {
      super.onFocusChanged(paramBoolean, paramInt, paramRect);
      return;
      setSelected(false);
    }
  }

  public void setBgSelectedStyle(boolean paramBoolean)
  {
    this.bgSelectedStyle = paramBoolean;
  }

  public void setDefaultColor(int paramInt)
  {
    this.DEFAULT_COLOR = paramInt;
  }

  public void setFocusText(String paramString)
  {
    this.focusText = paramString;
    lightFocus();
  }

  public void setHightLight(boolean paramBoolean)
  {
    if (this.hightLight == paramBoolean);
    do
    {
      return;
      this.hightLight = paramBoolean;
      if ((!this.hightLight) && (!this.isSelected))
        break;
      setTextColor(-1);
    }
    while (this.isSelected);
    setBackgroundResource(2130837663);
    return;
    setTextColor(this.DEFAULT_COLOR);
    setBackgroundDrawable(null);
  }

  public void setSelected(boolean paramBoolean)
  {
    super.setSelected(paramBoolean);
    if (this.isSelected == paramBoolean)
      return;
    this.isSelected = paramBoolean;
    if (this.isSelected)
      setBackgroundResource(2130837664);
    while (true)
    {
      if ((!this.isSelected) && (!this.hightLight))
        break label73;
      setTextColor(-1);
      if (this.isSelected)
        break;
      setBackgroundResource(2130837663);
      return;
      setBackgroundDrawable(null);
    }
    label73: setTextColor(this.DEFAULT_COLOR);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.HighLightTextView
 * JD-Core Version:    0.6.2
 */