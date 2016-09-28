package com.starcor.xul;

import android.graphics.Rect;
import android.view.KeyEvent;

public abstract interface IXulExternalView
{
  public abstract void extDestroy();

  public abstract void extHide();

  public abstract void extMoveTo(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract void extMoveTo(Rect paramRect);

  public abstract void extOnBlur();

  public abstract void extOnFocus();

  public abstract boolean extOnKeyEvent(KeyEvent paramKeyEvent);

  public abstract void extShow();

  public abstract void extSyncData();

  public abstract String getAttr(String paramString1, String paramString2);

  public abstract boolean setAttr(String paramString1, String paramString2);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.IXulExternalView
 * JD-Core Version:    0.6.2
 */