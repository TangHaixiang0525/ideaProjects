package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.content.Intent;
import com.starcor.hunan.ServiceActivity;

public class OpenAboutPage extends MediaAssetHelperBase
{
  public OpenAboutPage()
  {
    super(OpenAboutPage.class.getSimpleName());
  }

  public OpenAboutPage(Context paramContext)
  {
    super(paramContext, OpenAboutPage.class.getSimpleName());
  }

  protected void startSubAction()
  {
    try
    {
      Intent localIntent = new Intent(this.mContext, ServiceActivity.class);
      localIntent.putExtra("action", this.mAction);
      localIntent.putExtra("action_args", "isFromMsgSys");
      localIntent.addFlags(8388608);
      this.mContext.startActivity(localIntent);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenAboutPage
 * JD-Core Version:    0.6.2
 */