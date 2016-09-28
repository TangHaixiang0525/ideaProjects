package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.starcor.hunan.uilogic.ActivityAdapter;

public class OpenAssetSearchRangePageHelper extends MediaAssetHelperBase
{
  public OpenAssetSearchRangePageHelper()
  {
    super(OpenAssetSearchRangePageHelper.class.getSimpleName());
  }

  public OpenAssetSearchRangePageHelper(Context paramContext)
  {
    super(paramContext, OpenAssetSearchRangePageHelper.class.getSimpleName());
  }

  protected void startSubAction()
  {
    try
    {
      String str1 = this.mParamsBundle.getString("search_range");
      String str2 = this.mParamsBundle.getString("search_range_name");
      String str3 = this.mParamsBundle.getString("category_id");
      String str4 = this.mParamsBundle.getString("media_asset_id");
      String str5 = this.mParamsBundle.getString("media_asset_name");
      String str6 = this.mParamsBundle.getString("name");
      String str7 = "";
      if (this.mParamsBundle.containsKey("search_key"))
        str7 = this.mParamsBundle.getString("search_key");
      Intent localIntent = new Intent();
      localIntent.putExtra("search_range", str1);
      localIntent.putExtra("search_range_name", str2);
      localIntent.putExtra("actions", "m_open_asset_search_range_page");
      localIntent.putExtra("category_id", str3);
      localIntent.putExtra("media_asset_id", str4);
      localIntent.putExtra("media_asset_name", str5);
      localIntent.putExtra("name", str6);
      localIntent.putExtra("search_key", str7);
      localIntent.setClass(this.mContext, ActivityAdapter.getInstance().getSearchActivity());
      localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.common_to_search");
      localIntent.setFlags(8388608);
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
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenAssetSearchRangePageHelper
 * JD-Core Version:    0.6.2
 */