package com.starcor.hunan.uilogic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.starcor.core.domain.VideoInfo;
import org.json.JSONException;
import org.json.JSONObject;

public class HMDSender
{
  private static final String ACTION_ADD_COLLECT = "com.himedia.video.action.ACTION_ADD_FAVRECORD";
  private static final String ACTION_ADD_PALYLIST = "com.himedia.playrecord.action";
  private static final String ACTION_CLEAR_COLLECT = "com.himedia.video.action.ACTION_CLEAR_FAVRECORD";
  private static final String ACTION_DELETE_COLLECT = "com.himedia.video.action.ACTION_DELETE_FAVRECORD";
  private static final String TAG = HMDSender.class.getSimpleName();

  private static String buildPlayJson(String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3, String paramString3, int paramInt4, int paramInt5, int paramInt6)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("video_id", paramString1);
      localJSONObject.put("video_type", paramInt1);
      localJSONObject.put("video_begin_time", paramString2);
      localJSONObject.put("video_duration", paramInt2);
      localJSONObject.put("video_index", paramInt3);
      localJSONObject.put("video_index_name", paramString3);
      localJSONObject.put("video_index_count", paramInt4);
      localJSONObject.put("video_ui_style", paramInt5);
      localJSONObject.put("current_position", paramInt6);
      localJSONObject.put("package_name", "com.starcor.hunan");
      String str = localJSONObject.toString();
      return str;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return null;
  }

  private static String getValue(VideoInfo paramVideoInfo)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("video_id", paramVideoInfo.videoId);
      localJSONObject.put("video_type", paramVideoInfo.videoType);
      localJSONObject.put("video_ui_style", paramVideoInfo.uiStyle);
      String str = localJSONObject.toString();
      return str;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return null;
  }

  public static void sendAddCollectBroadcast(Context paramContext, VideoInfo paramVideoInfo)
  {
    Intent localIntent = new Intent();
    localIntent.setAction("com.himedia.video.action.ACTION_ADD_FAVRECORD");
    localIntent.addFlags(32);
    Bundle localBundle = new Bundle();
    localBundle.putString("vid", paramVideoInfo.videoId);
    localBundle.putString("videoName", paramVideoInfo.name);
    localBundle.putString("videoImgUrl", paramVideoInfo.getImgUrl());
    localBundle.putString("videoSource", "mgtv");
    localBundle.putString("videoAction", "com.starcor.hunan.hmd.record");
    localBundle.putString("videoCallback", "sendBroadcast");
    localBundle.putString("key", "cmd_ex");
    localBundle.putString("value", getValue(paramVideoInfo));
    localIntent.putExtras(localBundle);
    paramContext.sendBroadcast(localIntent);
  }

  public static void sendAddPlayListBroadcast(Context paramContext, String paramString1, int paramInt1, String paramString2, String paramString3, String paramString4, int paramInt2, String paramString5, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    if (TextUtils.isEmpty(paramString1));
    while (TextUtils.isEmpty(paramString3))
      return;
    Intent localIntent = new Intent();
    localIntent.setAction("com.himedia.playrecord.action");
    localIntent.addFlags(32);
    Bundle localBundle = new Bundle();
    localBundle.putString("vid", paramString1);
    localBundle.putString("videoAction", "com.starcor.hunan.hmd.playvideo");
    localBundle.putString("videoName", paramString3);
    localBundle.putString("videoImgUrl", paramString4);
    localBundle.putString("cmd", buildPlayJson(paramString1, paramInt1, paramString2, paramInt6, paramInt2, paramString5, paramInt3, paramInt4, paramInt5));
    localBundle.putString("videoSource", "mgtv");
    localBundle.putString("videoCallback", "sendBroadcast");
    localIntent.putExtras(localBundle);
    paramContext.sendBroadcast(localIntent);
  }

  public static void sendClearCollectBroadcast(Context paramContext)
  {
    Intent localIntent = new Intent();
    localIntent.setAction("com.himedia.video.action.ACTION_CLEAR_FAVRECORD");
    localIntent.addFlags(32);
    Bundle localBundle = new Bundle();
    localBundle.putString("videoSource", "mgtv");
    localIntent.putExtras(localBundle);
    paramContext.sendBroadcast(localIntent);
  }

  public static void sendClearPlayListBroadcast()
  {
  }

  public static void sendDelSingleCollectBroadcast(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent();
    localIntent.setAction("com.himedia.video.action.ACTION_DELETE_FAVRECORD");
    localIntent.addFlags(32);
    Bundle localBundle = new Bundle();
    localBundle.putString("vid", paramString);
    localBundle.putString("videoSource", "mgtv");
    localIntent.putExtras(localBundle);
    paramContext.sendBroadcast(localIntent);
  }

  public static void sendDelSinglePlayListBroadcast()
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.uilogic.HMDSender
 * JD-Core Version:    0.6.2
 */