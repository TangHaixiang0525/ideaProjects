package com.starcor.hunan.widget.gridview;

import android.view.KeyEvent;

public class InputHandler
{
  private int keyHitStrike = 0;
  private ActionPerformer mActionPerformer;

  public InputHandler(ActionPerformer paramActionPerformer)
  {
    this.mActionPerformer = paramActionPerformer;
  }

  private void click()
  {
    this.mActionPerformer.click();
  }

  private boolean cursorDown(boolean paramBoolean)
  {
    return this.mActionPerformer.moveFocus(130, paramBoolean);
  }

  private boolean cursorLeft(boolean paramBoolean)
  {
    return this.mActionPerformer.moveFocus(17, paramBoolean);
  }

  private boolean cursorRight(boolean paramBoolean)
  {
    return this.mActionPerformer.moveFocus(66, paramBoolean);
  }

  private boolean cursorUp(boolean paramBoolean)
  {
    return this.mActionPerformer.moveFocus(33, paramBoolean);
  }

  public boolean handleKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool1 = true;
    this.keyHitStrike = (1 + this.keyHitStrike);
    if (this.keyHitStrike < 2);
    for (boolean bool2 = bool1; ; bool2 = false)
      switch (paramInt)
      {
      default:
        bool1 = false;
      case 23:
      case 66:
        return bool1;
      case 19:
      case 20:
      case 21:
      case 22:
      }
    return cursorUp(bool2);
    return cursorDown(bool2);
    return cursorLeft(bool2);
    return cursorRight(bool2);
  }

  public boolean handleKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    this.keyHitStrike = 0;
    switch (paramInt)
    {
    default:
      return false;
    case 23:
    case 66:
    }
    click();
    return true;
  }

  public static abstract interface ActionPerformer
  {
    public abstract void click();

    public abstract boolean moveFocus(int paramInt, boolean paramBoolean);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.gridview.InputHandler
 * JD-Core Version:    0.6.2
 */