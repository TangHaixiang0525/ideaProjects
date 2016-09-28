package com.starcor.hunan.util;

import android.view.KeyEvent;
import com.starcor.core.utils.Logger;
import java.util.ArrayList;
import java.util.List;

public class SpecialKeyOperation
{
  private static final String TAG = "SpecialKeyOperation";
  private int currentIndex;
  private List<Integer> key = new ArrayList();
  private OnKeySequenceMatchingListener mListener;

  public void addKeySequence(int paramInt)
  {
    this.key.add(Integer.valueOf(paramInt));
  }

  public void clear()
  {
    this.key.clear();
  }

  public void dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() != 0);
    while (true)
    {
      return;
      if (this.key.size() == 0)
      {
        Logger.i("SpecialKeyOperation", "dispatchKeyEvent keys is empty!");
        return;
      }
      if (this.currentIndex >= this.key.size())
        this.currentIndex = 0;
      if (((Integer)this.key.get(this.currentIndex)).intValue() == paramKeyEvent.getKeyCode())
      {
        this.currentIndex = (1 + this.currentIndex);
        if (this.mListener != null)
          this.mListener.onKeySequenceMatching(this.currentIndex);
      }
      while ((this.currentIndex == this.key.size()) && (this.mListener != null))
      {
        this.mListener.onKeySequenceMatchingSuccess();
        return;
        this.currentIndex = 0;
        if (this.mListener != null)
          this.mListener.onKeySequenceMatchingFail(this.currentIndex);
      }
    }
  }

  public void setOnKeySequenceMatchingListener(OnKeySequenceMatchingListener paramOnKeySequenceMatchingListener)
  {
    this.mListener = paramOnKeySequenceMatchingListener;
  }

  public static class BaseMatchingListener extends SpecialKeyOperation.OnKeySequenceMatchingListener
  {
    public void onKeySequenceMatching(int paramInt)
    {
    }

    public void onKeySequenceMatchingFail(int paramInt)
    {
    }

    public void onKeySequenceMatchingSuccess()
    {
    }
  }

  public static abstract class OnKeySequenceMatchingListener
  {
    public abstract void onKeySequenceMatching(int paramInt);

    public abstract void onKeySequenceMatchingFail(int paramInt);

    public abstract void onKeySequenceMatchingSuccess();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.util.SpecialKeyOperation
 * JD-Core Version:    0.6.2
 */