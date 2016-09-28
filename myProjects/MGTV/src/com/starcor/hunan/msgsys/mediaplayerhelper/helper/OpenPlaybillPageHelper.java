package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.xul.XulUtils;

public class OpenPlaybillPageHelper extends MediaAssetHelperBase
{
  public OpenPlaybillPageHelper()
  {
    super(OpenPlaybillPageHelper.class.getSimpleName());
  }

  public OpenPlaybillPageHelper(Context paramContext)
  {
    super(paramContext, OpenPlaybillPageHelper.class.getSimpleName());
  }

  protected void startSubAction()
  {
    try
    {
      String str1 = this.mParamsBundle.getString("media_asset_id");
      String str2 = this.mParamsBundle.getString("category_id");
      String str3 = this.mParamsBundle.getString("ui_style");
      String str4 = this.mParamsBundle.getString("video_id");
      String str5 = this.mParamsBundle.getString("video_type");
      String str6 = this.mParamsBundle.getString("begin_time");
      String str7 = this.mParamsBundle.getString("time_len");
      String str8 = this.mParamsBundle.getString("name");
      Intent localIntent = new Intent(this.mContext, ActivityAdapter.getNewReplayActivity());
      localIntent.putExtra("recommend_type", "1");
      MetadataInfo localMetadataInfo = new MetadataInfo();
      localMetadataInfo.category_id = str2;
      localMetadataInfo.packet_id = str1;
      localMetadataInfo.uiStyle = XulUtils.tryParseInt(str3);
      localMetadataInfo.video_id = str4;
      localMetadataInfo.video_type = str5;
      localMetadataInfo.begin_time = str6;
      localMetadataInfo.time_len = str7;
      localMetadataInfo.name = str8;
      localIntent.putExtra("MetaDataInfo", localMetadataInfo);
      localIntent.putExtra("defaultChannel", str4);
      if (!TextUtils.isEmpty(str6))
      {
        localIntent.putExtra("date", str6.substring(0, 8));
        localIntent.putExtra("begin_time", str6.substring(8));
      }
      localIntent.putExtra("recommend_type", "0,0");
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
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenPlaybillPageHelper
 * JD-Core Version:    0.6.2
 */