package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.starcor.core.domain.PurchaseParam;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.hunan.XULActivity;

public class OpenExchangeCardPageHelper extends MediaAssetHelperBase
{
  public OpenExchangeCardPageHelper()
  {
    super(OpenExchangeCardPageHelper.class.getSimpleName());
  }

  public OpenExchangeCardPageHelper(Context paramContext)
  {
    super(paramContext, OpenExchangeCardPageHelper.class.getSimpleName());
  }

  protected void startSubAction()
  {
    Intent localIntent = new Intent(this.mContext, XULActivity.class);
    if (TextUtils.isEmpty(GlobalLogic.getInstance().getWebToken()))
    {
      localIntent.putExtra("xulPageId", "LoginAndRegistration");
      localIntent.putExtra("xulIsClose", "false");
      GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(true, "", ""));
      GlobalLogic.getInstance().setAutoJumpToDetailedPage(false);
    }
    while (true)
    {
      this.mContext.startActivity(localIntent);
      return;
      localIntent.putExtra("xulPageId", "CouponCard");
      GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
      GlobalLogic.getInstance().setAutoJumpToDetailedPage(false);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenExchangeCardPageHelper
 * JD-Core Version:    0.6.2
 */