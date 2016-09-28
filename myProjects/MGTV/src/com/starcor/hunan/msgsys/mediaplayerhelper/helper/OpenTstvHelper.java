package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.VideoInfo;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.xul.XulUtils;

public class OpenTstvHelper extends MediaAssetHelperBase
{
  public OpenTstvHelper()
  {
    super(OpenTstvHelper.class.getSimpleName());
  }

  public OpenTstvHelper(Context paramContext)
  {
    super(paramContext, OpenTstvHelper.class.getSimpleName());
  }

  protected void startSubAction()
  {
    try
    {
      String str1 = this.mParamsBundle.getString("category_id");
      String str2 = this.mParamsBundle.getString("name");
      String str3 = this.mParamsBundle.getString("media_asset_id");
      String str4 = this.mParamsBundle.getString("ui_style");
      String str5 = this.mParamsBundle.getString("video_id");
      String str6 = this.mParamsBundle.getString("video_type");
      Intent localIntent = new Intent(this.mContext, ActivityAdapter.getInstance().getMPlayer());
      localIntent.putExtra("cmd_is_from_out", "");
      PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
      localPlayerIntentParams.nns_index = 0;
      localPlayerIntentParams.nns_videoInfo = new VideoInfo();
      localPlayerIntentParams.nns_videoInfo.videoType = 1;
      localPlayerIntentParams.nns_videoInfo.packageId = str3;
      localPlayerIntentParams.nns_videoInfo.categoryId = str1;
      localPlayerIntentParams.mode = 6;
      MetadataInfo localMetadataInfo = new MetadataInfo();
      localMetadataInfo.video_id = str5;
      localMetadataInfo.video_type = str6;
      localMetadataInfo.category_id = str1;
      localMetadataInfo.packet_id = str3;
      localMetadataInfo.name = str2;
      localMetadataInfo.uiStyle = XulUtils.tryParseInt(str4);
      localIntent.putExtra("MetaDataInfo", localMetadataInfo);
      localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
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
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenTstvHelper
 * JD-Core Version:    0.6.2
 */