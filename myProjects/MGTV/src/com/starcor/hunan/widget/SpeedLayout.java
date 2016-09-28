package com.starcor.hunan.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import com.starcor.hunan.interfaces.ServiceSelectBtnCallBack;

public class SpeedLayout extends LinearLayout
  implements ServiceSelectBtnCallBack
{
  public SpeedLayout(Context paramContext)
  {
    super(paramContext);
    init(paramContext);
  }

  public SpeedLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext);
  }

  public void init(Context paramContext)
  {
    inflate(paramContext, 2130903079, this);
    setOrientation(1);
    ((SeekBar)findViewById(2131165338)).setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        return true;
      }
    });
  }

  public void setNextLeftButton(HighLightButton paramHighLightButton)
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.SpeedLayout
 * JD-Core Version:    0.6.2
 */