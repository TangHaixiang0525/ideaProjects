package com.starcor.settings.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class NoSlideGridView extends GridView
{
  public NoSlideGridView(Context paramContext)
  {
    super(paramContext);
  }

  public NoSlideGridView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public NoSlideGridView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 2)
      return true;
    return super.dispatchTouchEvent(paramMotionEvent);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.utils.NoSlideGridView
 * JD-Core Version:    0.6.2
 */