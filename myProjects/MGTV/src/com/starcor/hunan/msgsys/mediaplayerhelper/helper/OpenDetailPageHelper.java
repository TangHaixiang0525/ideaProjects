package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.starcor.core.domain.CommonVideoIDInfo;
import com.starcor.core.domain.MovieData;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.DetailPageActivity;
import com.starcor.hunan.domain.ReportData;
import com.starcor.hunan.widget.CommDialog;
import com.starcor.sccms.api.SccmsApiGetCommonVideoIdTask.ISccmsApiGetCommonVideoIdTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.XulUtils;

public class OpenDetailPageHelper extends MediaAssetHelperBase
{
  public OpenDetailPageHelper()
  {
    super(OpenDetailPageHelper.class.getSimpleName());
  }

  public OpenDetailPageHelper(Context paramContext)
  {
    super(paramContext, OpenDetailPageHelper.class.getSimpleName());
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
      if (!TextUtils.isEmpty(this.mAction))
      {
        if ("detail".equals(this.mAction))
        {
          String str4 = this.mParamsBundle.getString("asset_id");
          String str5 = this.mParamsBundle.getString("assets_category");
          Logger.i(this.TAG, "assetId=" + str4 + " categoryId=" + str5);
          ServerApiManager.i().APIGetCommonVideoId(str4, null, null, null, new GetCommonVideoIdCallback(null));
          return;
        }
        if ("m_open_detail_page".equals(this.mAction))
          if (this.mParamsBundle.containsKey("video_id"))
          {
            String str1 = this.mParamsBundle.getString("video_id");
            String str2 = this.mParamsBundle.getString("video_type");
            String str3 = this.mParamsBundle.getString("film_name");
            if (TextUtils.isEmpty(str2))
              str2 = "0";
            MovieData localMovieData = new MovieData();
            localMovieData.videoId = str1;
            localMovieData.videoType = XulUtils.tryParseInt(str2);
            localMovieData.name = str3;
            localMovieData.updateIndex = true;
            ReportData localReportData = new ReportData();
            Intent localIntent = new Intent(this.mContext, DetailPageActivity.class);
            localIntent.putExtra("xulPageId", "DetailPage");
            localIntent.putExtra("xulData", "");
            localIntent.putExtra("movieData", localMovieData);
            localIntent.putExtra("reportData", localReportData);
            localIntent.addFlags(8388608);
            this.mContext.startActivity(localIntent);
            return;
          }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      if (this.mMediaAssetHelperListener != null)
      {
        Logger.i(this.TAG, "exception in parsing");
        this.mMediaAssetHelperListener.onError(this.mAction, "很抱歉，该影片资源已被删除或不存在");
        return;
        Logger.i(this.TAG, "no video id");
        this.mMediaAssetHelperListener.onError(this.mAction, "很抱歉，该影片资源已被删除或不存在");
        return;
        Logger.i(this.TAG, "wrong action");
        this.mMediaAssetHelperListener.onError(this.mAction, "很抱歉，该影片资源已被删除或不存在");
      }
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
      Logger.e(OpenDetailPageHelper.this.TAG, "GetCommonVideoIdCallback onError:");
      if (paramServerApiCommonError != null)
        Logger.e(OpenDetailPageHelper.this.TAG, paramServerApiCommonError.toString());
      if (OpenDetailPageHelper.this.mMediaAssetHelperListener != null)
        OpenDetailPageHelper.this.mMediaAssetHelperListener.onError(OpenDetailPageHelper.this.mAction, "很抱歉，该影片资源已被删除或不存在");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, CommonVideoIDInfo paramCommonVideoIDInfo)
    {
      Logger.i(OpenDetailPageHelper.this.TAG, "GetCommonVideoIdCallback onSuccess:");
      if (paramCommonVideoIDInfo == null)
        if (OpenDetailPageHelper.this.mMediaAssetHelperListener != null)
          OpenDetailPageHelper.this.mMediaAssetHelperListener.onError(OpenDetailPageHelper.this.mAction, "很抱歉，该影片资源已被删除或不存在");
      String str1;
      String str2;
      do
      {
        return;
        if (OpenDetailPageHelper.this.mMediaAssetHelperListener != null)
          OpenDetailPageHelper.this.mMediaAssetHelperListener.onSuccess(OpenDetailPageHelper.this.mAction, OpenDetailPageHelper.this.mParamsBundle);
        Logger.i(OpenDetailPageHelper.this.TAG, "GetCommonVideoIdCallback.onSuccess(), result:" + paramCommonVideoIDInfo.toString());
        str1 = paramCommonVideoIDInfo.mAssetId;
        str2 = "0";
        if (OpenDetailPageHelper.this.mParamsBundle.containsKey("video_type"))
          str2 = OpenDetailPageHelper.this.mParamsBundle.getString("video_type");
        Logger.i(OpenDetailPageHelper.this.TAG, "video id = " + str1 + " video type = " + str2);
        if (!TextUtils.isEmpty(str1))
          break;
      }
      while (OpenDetailPageHelper.this.mMediaAssetHelperListener == null);
      OpenDetailPageHelper.this.mMediaAssetHelperListener.onError(OpenDetailPageHelper.this.mAction, "很抱歉，该影片资源已被删除或不存在");
      return;
      String str3 = OpenDetailPageHelper.this.mParamsBundle.getString("film_name");
      Intent localIntent = new Intent();
      localIntent.putExtra("xulPageId", "DetailPage");
      localIntent.putExtra("xulData", "");
      MovieData localMovieData = new MovieData();
      localMovieData.videoId = str1;
      localMovieData.videoType = XulUtils.tryParseInt(str2);
      localMovieData.updateIndex = true;
      localMovieData.name = str3;
      localIntent.putExtra("movieData", localMovieData);
      localIntent.setClass(OpenDetailPageHelper.this.mContext, DetailPageActivity.class);
      localIntent.addFlags(8388608);
      OpenDetailPageHelper.this.mContext.startActivity(localIntent);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenDetailPageHelper
 * JD-Core Version:    0.6.2
 */