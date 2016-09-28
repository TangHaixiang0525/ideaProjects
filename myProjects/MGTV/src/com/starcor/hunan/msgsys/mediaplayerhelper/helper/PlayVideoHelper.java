package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import com.starcor.core.domain.CommonVideoIDInfo;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.logic.UserCPLLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.hunan.widget.CommDialog;
import com.starcor.sccms.api.SccmsApiGetCommonVideoIdTask.ISccmsApiGetCommonVideoIdTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;

public class PlayVideoHelper extends MediaAssetHelperBase
{
  public PlayVideoHelper()
  {
    super(PlayVideoHelper.class.getSimpleName());
  }

  public PlayVideoHelper(Context paramContext)
  {
    super(paramContext, PlayVideoHelper.class.getSimpleName());
  }

  private void showDialog(String paramString)
  {
    CommDialog localCommDialog = new CommDialog(this.mContext, 2131296258);
    localCommDialog.setMessage(paramString);
    localCommDialog.setType(1);
    localCommDialog.setTitle("提示");
    localCommDialog.setMessage(paramString);
    localCommDialog.setNegativeButton(new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
      }
    });
    localCommDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if ((paramAnonymousKeyEvent.getAction() == 0) && (paramAnonymousInt == 4))
        {
          paramAnonymousDialogInterface.dismiss();
          return true;
        }
        return false;
      }
    });
    localCommDialog.show();
  }

  protected void startSubAction()
  {
    try
    {
      String str1 = this.mParamsBundle.getString("asset_id");
      String str2 = this.mParamsBundle.getString("clip_id");
      String str3 = this.mParamsBundle.getString("file_id");
      Logger.i(this.TAG, "assetId=" + str1 + " clipId=" + str2 + " fileId=" + str3);
      ServerApiManager.i().APIGetCommonVideoId(str1, str2, str3, null, new GetCommonVideoIdCallback(null));
      return;
    }
    catch (Exception localException)
    {
      do
        localException.printStackTrace();
      while (this.mMediaAssetHelperListener == null);
      this.mMediaAssetHelperListener.onError(this.mAction, "该影片资源已被删除或不存在");
    }
  }

  private class GetCommonVideoIdCallback
    implements SccmsApiGetCommonVideoIdTask.ISccmsApiGetCommonVideoIdTaskListener
  {
    private GetCommonVideoIdCallback()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.e(PlayVideoHelper.this.TAG, "GetCommonVideoIdCallback onError:");
      if (paramServerApiCommonError != null)
        Logger.e(PlayVideoHelper.this.TAG, paramServerApiCommonError.toString());
      if (PlayVideoHelper.this.mMediaAssetHelperListener != null)
        PlayVideoHelper.this.mMediaAssetHelperListener.onError(PlayVideoHelper.this.mAction, "很抱歉，该影片资源已被删除或不存在");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, CommonVideoIDInfo paramCommonVideoIDInfo)
    {
      Logger.i(PlayVideoHelper.this.TAG, "GetCommonVideoIdCallback onSuccess:");
      if (paramCommonVideoIDInfo == null)
      {
        if (PlayVideoHelper.this.mMediaAssetHelperListener != null)
          PlayVideoHelper.this.mMediaAssetHelperListener.onError(PlayVideoHelper.this.mAction, "很抱歉，该影片资源已被删除或不存在");
        return;
      }
      if (PlayVideoHelper.this.mMediaAssetHelperListener != null)
        PlayVideoHelper.this.mMediaAssetHelperListener.onSuccess(PlayVideoHelper.this.mAction, PlayVideoHelper.this.mParamsBundle);
      Logger.i(PlayVideoHelper.this.TAG, "GetCommonVideoIdCallback.onSuccess(), result:" + paramCommonVideoIDInfo.toString());
      String str1 = paramCommonVideoIDInfo.mClipVideoId;
      String str2 = "0";
      if (PlayVideoHelper.this.mParamsBundle.containsKey("video_type"))
        str2 = PlayVideoHelper.this.mParamsBundle.getString("video_type");
      Logger.i(PlayVideoHelper.this.TAG, "video id = " + str1 + " video type = " + str2);
      Intent localIntent = new Intent(PlayVideoHelper.this.mContext, ActivityAdapter.getInstance().getMPlayer());
      localIntent.putExtra("cmd_is_from_out", "");
      PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
      int i = UserCPLLogic.getInstance().getLastPlayRecord(str1);
      if (i < 0)
        i = 0;
      Logger.i(PlayVideoHelper.this.TAG, "lastPlayedIndex=" + i);
      localPlayerIntentParams.nns_index = i;
      localPlayerIntentParams.nns_videoInfo = new VideoInfo();
      localPlayerIntentParams.nns_videoInfo.videoType = Integer.valueOf(str2).intValue();
      localPlayerIntentParams.nns_videoInfo.videoId = str1;
      MetadataInfo localMetadataInfo = new MetadataInfo();
      localMetadataInfo.video_id = str1;
      localMetadataInfo.video_type = str2;
      localIntent.putExtra("MetaDataInfo", localMetadataInfo);
      localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
      PlayVideoHelper.this.mContext.startActivity(localIntent);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.PlayVideoHelper
 * JD-Core Version:    0.6.2
 */