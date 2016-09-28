package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.starcor.core.domain.PurchaseParam;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.XULActivity;

public class OpenUserManagerPageHelper extends MediaAssetHelperBase
{
  public OpenUserManagerPageHelper()
  {
    super(OpenUserManagerPageHelper.class.getSimpleName());
  }

  public OpenUserManagerPageHelper(Context paramContext)
  {
    super(paramContext, OpenUserManagerPageHelper.class.getSimpleName());
  }

  protected void startSubAction()
  {
    String str = GlobalLogic.getInstance().getWebToken();
    Logger.i(this.TAG, "web_token=" + str);
    Intent localIntent = new Intent(this.mContext, XULActivity.class);
    if (TextUtils.isEmpty(str))
    {
      localIntent.putExtra("xulPageId", "LoginAndRegistration");
      localIntent.putExtra("xulIsClose", "false");
      GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
      GlobalLogic.getInstance().setAutoJumpToDetailedPage(false);
    }
    while (true)
    {
      localIntent.addFlags(8388608);
      this.mContext.startActivity(localIntent);
      return;
      localIntent.putExtra("xulPageId", "NewUserCenter");
      GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
      GlobalLogic.getInstance().setAutoJumpToDetailedPage(false);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenUserManagerPageHelper
 * JD-Core Version:    0.6.2
 */