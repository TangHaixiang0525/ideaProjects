package com.starcor.jump.bussines.simple;

import com.starcor.core.domain.PurchaseParam;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.hunan.XULActivity;
import com.starcor.jump.bussines.CommonBussines;

public class ShowUserCenterBussines extends CommonBussines
{
  private static final String TAG = ShowUserCenterBussines.class.getSimpleName();

  private void jumpToLogin()
  {
    setClassForActivity(XULActivity.class);
    putExtra("xulPageId", "LoginAndRegistration");
    putExtra("xulIsClose", "false");
    GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
    GlobalLogic.getInstance().setAutoJumpToDetailedPage(false);
  }

  private void jumpToUsercenter()
  {
    setClassForActivity(XULActivity.class);
    putExtra("xulPageId", "NewUserCenter");
    GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
    GlobalLogic.getInstance().setAutoJumpToDetailedPage(false);
  }

  protected void commonStart()
  {
    putExtra("Cmd", "com.starcor.hunan.cmd.show_user_center");
    if (!GlobalLogic.getInstance().isUserLogined())
    {
      jumpToLogin();
      return;
    }
    jumpToUsercenter();
  }

  protected void startFromActivity()
  {
  }

  protected void startFromReciever()
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.simple.ShowUserCenterBussines
 * JD-Core Version:    0.6.2
 */