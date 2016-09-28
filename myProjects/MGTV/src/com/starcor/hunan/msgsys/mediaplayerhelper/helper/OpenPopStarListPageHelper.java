package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.content.Intent;
import com.starcor.hunan.StarActivity;

public class OpenPopStarListPageHelper extends MediaAssetHelperBase
{
  public OpenPopStarListPageHelper()
  {
    super(OpenPopStarListPageHelper.class.getSimpleName());
  }

  public OpenPopStarListPageHelper(Context paramContext)
  {
    super(paramContext, OpenPopStarListPageHelper.class.getSimpleName());
  }

  protected void startSubAction()
  {
    try
    {
      Intent localIntent = new Intent(this.mContext, StarActivity.class);
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
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenPopStarListPageHelper
 * JD-Core Version:    0.6.2
 */