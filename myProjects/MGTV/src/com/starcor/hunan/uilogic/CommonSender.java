package com.starcor.hunan.uilogic;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;

public class CommonSender
{
  private static final String ACTION_ADD_COLLECT = "";
  private static final String ACTION_ADD_PLAYLIST = "com.starcor.hunan.mgtv";
  private static final String ACTION_ADD_PLAYREPORT = "com.starcor.sender.playing";
  private static final String ACTION_CLEAR_COLLECT = "";
  private static final String ACTION_DELETE_COLLECT = "";
  private static final String SEND_SEPCIAL_TAG = "com.starcor.sender.special.tag";
  private static final String SEND_SEPCIAL_TAG_VALUE = "com.starcor.sender.special.tag.value";
  private static final String TAG = CommonSender.class.getSimpleName();

  public static boolean isSendFromInner(Intent paramIntent)
  {
    return "com.starcor.sender.special.tag.value".equals(paramIntent.getStringExtra("com.starcor.sender.special.tag"));
  }

  public static void sendAddCollectBroadcast(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, int paramInt3, String paramString6, String paramString7)
  {
    if (TextUtils.isEmpty(paramString1))
      return;
    Intent localIntent = new Intent();
    localIntent.setAction(GeneralUtils.getIntentAction(paramContext));
    localIntent.putExtra("flag_action_from_mgtv", true);
    localIntent.addFlags(32);
    localIntent.putExtra("cmd_ex", "add_collection");
    localIntent.putExtra("videoId", paramString1);
    localIntent.putExtra("videoName", paramString2);
    localIntent.putExtra("videoImgUrl", paramString3);
    localIntent.putExtra("episodeId", paramString4);
    localIntent.putExtra("episodeName", paramString5);
    localIntent.putExtra("episodeCount", paramInt1);
    localIntent.putExtra("duration", paramInt3);
    localIntent.putExtra("definition", paramString6);
    localIntent.putExtra("cmdinfo", paramString7);
    localIntent.putExtra("userkey", "");
    if (DeviceInfo.isXiaoMi())
    {
      if (!GlobalLogic.getInstance().isUserLogined())
        break label197;
      localIntent.putExtra("userid", GlobalLogic.getInstance().getUserName());
    }
    while (true)
    {
      localIntent.putExtra("com.starcor.sender.special.tag", "com.starcor.sender.special.tag.value");
      paramContext.sendBroadcast(localIntent);
      return;
      label197: localIntent.putExtra("userid", DeviceInfo.getMac());
    }
  }

  public static void sendAddPlayListBroadcast(Context paramContext, String paramString1, int paramInt1, String paramString2, String paramString3, String paramString4, int paramInt2, String paramString5, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    if (TextUtils.isEmpty(paramString1));
    while (TextUtils.isEmpty(paramString3))
      return;
    Intent localIntent = new Intent();
    localIntent.setAction(GeneralUtils.getIntentAction(paramContext));
    localIntent.putExtra("flag_action_from_mgtv", true);
    localIntent.addFlags(32);
    localIntent.putExtra("cmd_ex", "add_play_record");
    localIntent.putExtra("video_id", paramString1);
    localIntent.putExtra("video_type", paramInt1);
    localIntent.putExtra("video_name", paramString3);
    localIntent.putExtra("video_index", paramInt2);
    localIntent.putExtra("video_index_name", paramString5);
    localIntent.putExtra("video_index_count", paramInt3);
    localIntent.putExtra("ui_style", paramInt4);
    localIntent.putExtra("current_position", paramInt5);
    localIntent.putExtra("video_duration", paramInt6);
    localIntent.putExtra("package_name", "com.statcor.hunan");
    localIntent.putExtra("video_image_url", paramString4);
    if (DeviceInfo.isXiaoMi())
    {
      if (!GlobalLogic.getInstance().isUserLogined())
        break label216;
      localIntent.putExtra("userid", GlobalLogic.getInstance().getUserName());
    }
    while (true)
    {
      localIntent.putExtra("com.starcor.sender.special.tag", "com.starcor.sender.special.tag.value");
      paramContext.sendBroadcast(localIntent);
      return;
      label216: localIntent.putExtra("userid", DeviceInfo.getMac());
    }
  }

  public static void sendClearCollectBroadcast(Context paramContext)
  {
  }

  public static void sendClearPlayListBroadcast(Context paramContext)
  {
  }

  public static void sendDelSingleCollectBroadcast(Context paramContext, String paramString)
  {
  }

  public static void sendDelSingleCollectBroadcast(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if (TextUtils.isEmpty(paramString1))
      return;
    Logger.i(TAG, "sendDelCollectBroadcast  videoId:" + paramString1);
    Intent localIntent = new Intent();
    localIntent.addFlags(32);
    localIntent.setAction(GeneralUtils.getIntentAction(paramContext));
    localIntent.putExtra("flag_action_from_mgtv", true);
    localIntent.putExtra("cmd_ex", "delete_record");
    localIntent.putExtra("videoId", paramString1);
    localIntent.putExtra("identifierType", paramString2);
    localIntent.putExtra("cmdinfo", paramString3);
    localIntent.putExtra("userkey", paramString4);
    if (DeviceInfo.isXiaoMi())
    {
      if (!GlobalLogic.getInstance().isUserLogined())
        break label162;
      localIntent.putExtra("userid", GlobalLogic.getInstance().getUserName());
    }
    while (true)
    {
      localIntent.putExtra("com.starcor.sender.special.tag", "com.starcor.sender.special.tag.value");
      paramContext.sendBroadcast(localIntent);
      return;
      label162: localIntent.putExtra("userid", DeviceInfo.getMac());
    }
  }

  public static void sendDelSinglePlayListBroadcast(Context paramContext, VideoInfo paramVideoInfo)
  {
  }

  public static void sendPlayVideoReportBroadcast(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    Logger.i(TAG, "sendPlayVideoReportBroadcast  name:" + paramString1);
    Intent localIntent = new Intent();
    localIntent.setAction("com.starcor.sender.playing");
    localIntent.addFlags(32);
    localIntent.putExtra("name", paramString1);
    localIntent.putExtra("videoid", paramString2);
    localIntent.putExtra("channel", paramString3);
    localIntent.putExtra("episodename", paramString4);
    localIntent.putExtra("type", paramString5);
    paramContext.sendBroadcast(localIntent);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.uilogic.CommonSender
 * JD-Core Version:    0.6.2
 */