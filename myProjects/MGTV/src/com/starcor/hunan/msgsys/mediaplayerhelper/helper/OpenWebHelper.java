package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.xul.XulUtils;

public class OpenWebHelper extends MediaAssetHelperBase
{
  public OpenWebHelper()
  {
    super(OpenWebHelper.class.getSimpleName());
  }

  public OpenWebHelper(Context paramContext)
  {
    super(paramContext, OpenWebHelper.class.getSimpleName());
  }

  protected void startSubAction()
  {
    try
    {
      String str1 = this.mParamsBundle.getString("media_asset_id");
      String str2 = this.mParamsBundle.getString("category_id");
      String str3 = this.mParamsBundle.getString("ui_style");
      String str4 = this.mParamsBundle.getString("name");
      String str5 = this.mParamsBundle.getString("ex_url");
      MetadataInfo localMetadataInfo = new MetadataInfo();
      localMetadataInfo.category_id = str2;
      localMetadataInfo.packet_id = str1;
      localMetadataInfo.uiStyle = XulUtils.tryParseInt(str3);
      localMetadataInfo.url = str5;
      localMetadataInfo.name = str4;
      localMetadataInfo.action_type = "web";
      Intent localIntent = new Intent(this.mContext, ActivityAdapter.getInstance().getWebActivity());
      localIntent.putExtra("MetaDataInfo", localMetadataInfo);
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
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenWebHelper
 * JD-Core Version:    0.6.2
 */