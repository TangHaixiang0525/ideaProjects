package com.starcor.jump.bussines.simple;

import com.starcor.jump.bussines.CommonBussines;

public class StartAppByParamsBussines extends CommonBussines
{
  private static final String TAG = StartAppByParamsBussines.class.getSimpleName();

  protected void commonStart()
  {
    putExtra("Cmd", "com.starcor.hunan.cmd.start_app_by_params");
    processForMainActivity();
  }

  protected void startFromActivity()
  {
  }

  protected void startFromReciever()
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.simple.StartAppByParamsBussines
 * JD-Core Version:    0.6.2
 */