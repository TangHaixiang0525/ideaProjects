package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.service.ReservationService;
import com.starcor.hunan.uilogic.ActivityAdapter;

public class OpenLiveShowDetailPage extends MediaAssetHelperBase
{
  public OpenLiveShowDetailPage()
  {
    super(OpenLiveShowDetailPage.class.getSimpleName());
  }

  public OpenLiveShowDetailPage(Context paramContext)
  {
    super(paramContext, OpenLiveShowDetailPage.class.getSimpleName());
  }

  protected void startSubAction()
  {
    String str1 = "";
    String str2 = "";
    String str3 = "";
    String str4 = "";
    String str5 = "";
    String str6 = "";
    try
    {
      if (this.mParamsBundle.containsKey("live_show_id"))
        str2 = this.mParamsBundle.getString("live_show_id");
      if (this.mParamsBundle.containsKey("special_id"))
        str1 = this.mParamsBundle.getString("special_id");
      if (TextUtils.isEmpty(str2))
      {
        Logger.i(this.TAG, "KEY_LIVE_SHOW_ID has no value, so try KEY_SPECIAL_ID");
        str2 = str1;
      }
      if (this.mParamsBundle.containsKey("live_show_web_url"))
        str3 = this.mParamsBundle.getString("live_show_web_url");
      if (this.mParamsBundle.containsKey("film_name"))
        str4 = this.mParamsBundle.getString("film_name");
      if (this.mParamsBundle.containsKey("begin_time"))
        str6 = this.mParamsBundle.getString("begin_time");
      if (this.mParamsBundle.containsKey("begin_day"))
        str5 = this.mParamsBundle.getString("begin_day");
      Logger.i(this.TAG, "special_id=" + str1 + " live_show_id=" + str2 + " live_show_url=" + str3 + " film_name=" + str4 + " begin_time" + str6 + " begin_day=" + str5);
      if ((TextUtils.isEmpty(str2)) || (TextUtils.isEmpty(str3)) || (TextUtils.isEmpty(str6)))
      {
        if (this.mMediaAssetHelperListener != null)
          this.mMediaAssetHelperListener.onError(this.mAction, "很抱歉，该影片资源已被删除或不存在");
      }
      else
      {
        String str7 = str6.replace("-", "").replace(":", "").replace(" ", "");
        MetadataInfo localMetadataInfo = new MetadataInfo();
        localMetadataInfo.begin_time = str7;
        localMetadataInfo.url = str3;
        long l = ReservationService.time2Reservation(str7);
        if (ReservationService.getinstance().findReservation(l, str2) != null)
        {
          if (localMetadataInfo.url.indexOf('?') < 0)
            localMetadataInfo.url += "?";
          localMetadataInfo.url = (localMetadataInfo.url + "&live_show_id=" + str1);
          localMetadataInfo.action_type = "web";
          Logger.i(this.TAG, "info.url=" + localMetadataInfo.url);
          Intent localIntent = new Intent(this.mContext, ActivityAdapter.getInstance().getWebActivity());
          localIntent.putExtra("MetaDataInfo", localMetadataInfo);
          localIntent.addFlags(8388608);
          this.mContext.startActivity(localIntent);
          return;
        }
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        if (this.mMediaAssetHelperListener == null)
          break;
        this.mMediaAssetHelperListener.onError(this.mAction, "很抱歉，该影片资源已被删除或不存在");
        return;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenLiveShowDetailPage
 * JD-Core Version:    0.6.2
 */