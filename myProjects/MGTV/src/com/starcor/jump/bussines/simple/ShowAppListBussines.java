package com.starcor.jump.bussines.simple;

import com.starcor.jump.bussines.CommonBussines;

public class ShowAppListBussines extends CommonBussines
{
  private static final String TAG = ShowAppListBussines.class.getSimpleName();

  protected void commonStart()
  {
    putExtra("Cmd", "com.starcor.hunan.cmd.ctrl_in_current");
    setAction("com.starcor.hunan.logic_event.ctrl_in_current");
    setExcuteType(1);
  }

  protected void startFromActivity()
  {
  }

  protected void startFromReciever()
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.simple.ShowAppListBussines
 * JD-Core Version:    0.6.2
 */