package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.starcor.hunan.StarDetailedActivity;

public class OpenStarDetailPageHelper extends MediaAssetHelperBase
{
  public OpenStarDetailPageHelper()
  {
    super(OpenStarDetailPageHelper.class.getSimpleName());
  }

  public OpenStarDetailPageHelper(Context paramContext)
  {
    super(paramContext, OpenStarDetailPageHelper.class.getSimpleName());
  }

  protected void startSubAction()
  {
    Intent localIntent = new Intent();
    localIntent.setClass(this.mContext, StarDetailedActivity.class);
    localIntent.putExtra("action", "m_open_star_detail_page");
    localIntent.putExtra("actor", this.mParamsBundle.getString("name"));
    localIntent.putExtra("actorID", this.mParamsBundle.getString("star_id"));
    localIntent.putExtra("labelID", this.mParamsBundle.getString("label_id"));
    this.mContext.startActivity(localIntent);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenStarDetailPageHelper
 * JD-Core Version:    0.6.2
 */