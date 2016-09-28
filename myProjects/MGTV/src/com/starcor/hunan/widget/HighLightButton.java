package com.starcor.hunan.widget;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextPaint;
import android.widget.Button;
import com.starcor.hunan.App;

public class HighLightButton extends Button
{
  private static final int SELECTED_COLOR = -13069569;
  private boolean selectedFalg = false;

  public HighLightButton(Context paramContext)
  {
    super(paramContext);
    init();
  }

  private void init()
  {
    setTextColor(-10197916);
    setBackgroundColor(0);
    setGravity(17);
    setTextSize(0, App.OperationHeight(26));
    getPaint().setFakeBoldText(false);
  }

  public boolean isSelectedFalg()
  {
    return this.selectedFalg;
  }

  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    if (paramBoolean)
    {
      setTextColor(-1);
      setTextSize(0, App.OperationHeight(26));
      getPaint().setFakeBoldText(true);
      getPaint().setShadowLayer(3.0F, 1.0F, 1.0F, -16777216);
      setBackgroundResource(2130837664);
    }
    while (true)
    {
      super.onFocusChanged(paramBoolean, paramInt, paramRect);
      return;
      if (this.selectedFalg)
      {
        setTextColor(-1);
        setTextSize(0, App.OperationHeight(26));
        getPaint().setFakeBoldText(false);
        getPaint().setShadowLayer(3.0F, 1.0F, 1.0F, -16777216);
        setBackgroundResource(2130837663);
      }
      else
      {
        setTextColor(-10066330);
        setBackgroundColor(0);
        setTextSize(0, App.OperationHeight(26));
        getPaint().setFakeBoldText(false);
        setBackgroundDrawable(null);
      }
    }
  }

  public void setSelectedFalg(boolean paramBoolean)
  {
    this.selectedFalg = paramBoolean;
    if (paramBoolean)
    {
      setTextColor(-1);
      setBackgroundResource(2130837664);
      return;
    }
    init();
    setBackgroundDrawable(null);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.HighLightButton
 * JD-Core Version:    0.6.2
 */