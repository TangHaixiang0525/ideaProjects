package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.starcor.hunan.MorePageActivity;

public class OpenAssetPageHelper extends MediaAssetHelperBase
{
  public OpenAssetPageHelper()
  {
    super(OpenAssetPageHelper.class.getSimpleName());
  }

  public OpenAssetPageHelper(Context paramContext)
  {
    super(paramContext, OpenAssetPageHelper.class.getSimpleName());
  }

  protected void startSubAction()
  {
    try
    {
      String str1 = this.mParamsBundle.getString("media_asset_id");
      String str2 = this.mParamsBundle.getString("category_id");
      String str3 = this.mParamsBundle.getString("name");
      Intent localIntent = new Intent(this.mContext, MorePageActivity.class);
      localIntent.putExtra("packageId", str1);
      localIntent.putExtra("categoryId", str2);
      localIntent.putExtra("categoryName", str3);
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
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenAssetPageHelper
 * JD-Core Version:    0.6.2
 */