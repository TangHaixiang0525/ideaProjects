package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.VideoInfo;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.xul.XulUtils;

public class OpenMediaPlayerHelper extends MediaAssetHelperBase
{
  public OpenMediaPlayerHelper()
  {
    super(OpenMediaPlayerHelper.class.getSimpleName());
  }

  public OpenMediaPlayerHelper(Context paramContext)
  {
    super(paramContext, OpenMediaPlayerHelper.class.getSimpleName());
  }

  protected void startSubAction()
  {
    while (true)
    {
      PlayerIntentParams localPlayerIntentParams;
      try
      {
        String str1 = this.mParamsBundle.getString("media_asset_id");
        String str2 = this.mParamsBundle.getString("category_id");
        String str3 = this.mParamsBundle.getString("video_id");
        String str4 = this.mParamsBundle.getString("video_type");
        String str5 = this.mParamsBundle.getString("video_index");
        String str6 = this.mParamsBundle.getString("begin_time");
        String str7 = this.mParamsBundle.getString("time_len");
        String str8 = this.mParamsBundle.getString("name");
        String str9 = this.mParamsBundle.getString("name");
        String str10 = this.mParamsBundle.getString("channel_index");
        Intent localIntent = new Intent(this.mContext, ActivityAdapter.getInstance().getMPlayer());
        localIntent.putExtra("cmd_is_from_out", false);
        localPlayerIntentParams = new PlayerIntentParams();
        localPlayerIntentParams.nns_index = 0;
        localPlayerIntentParams.nns_videoInfo = new VideoInfo();
        localPlayerIntentParams.nns_videoInfo.videoType = XulUtils.tryParseInt(str4);
        localPlayerIntentParams.nns_videoInfo.videoId = str3;
        localPlayerIntentParams.nns_index = XulUtils.tryParseInt(str5);
        if (!TextUtils.isEmpty(str6))
        {
          localPlayerIntentParams.nns_day = str6.substring(0, 8);
          localPlayerIntentParams.nns_beginTime = str6.substring(8);
        }
        localPlayerIntentParams.nns_timeLen = str7;
        localPlayerIntentParams.nns_videoInfo.packageId = str1;
        localPlayerIntentParams.nns_videoInfo.categoryId = str2;
        localPlayerIntentParams.nns_videoInfo.film_name = str8;
        localPlayerIntentParams.nns_videoInfo.name = str9;
        localPlayerIntentParams.channel_index = str10;
        if (localPlayerIntentParams.nns_videoInfo.videoType == 0)
        {
          localPlayerIntentParams.mode = 2;
          localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
          this.mContext.startActivity(localIntent);
          return;
        }
        if (localPlayerIntentParams.nns_videoInfo.videoType != 1)
          break label367;
        if (TextUtils.isEmpty(str7))
        {
          localPlayerIntentParams.mode = 6;
          continue;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      localPlayerIntentParams.mode = 3;
      continue;
      label367: if (localPlayerIntentParams.nns_videoInfo.videoType == 2)
      {
        localPlayerIntentParams.nns_videoInfo.videoType = 1;
        localPlayerIntentParams.mode = 3;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenMediaPlayerHelper
 * JD-Core Version:    0.6.2
 */