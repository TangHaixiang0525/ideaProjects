package com.starcor.xul.Events;

import com.starcor.xul.XulView;

public class XulStateChangeEvent
{
  public boolean adjustFocusView = true;
  public String event;
  public XulView eventSource;
  public XulView notifySource;
  public int oldState;
  public int state;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Events.XulStateChangeEvent
 * JD-Core Version:    0.6.2
 */