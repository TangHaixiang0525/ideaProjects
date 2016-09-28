package com.starcor.hunan.uilogic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.starcor.config.DeviceInfo;
import com.starcor.config.MgtvVersion;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.SplashActivity;
import com.starcor.jump.bussines.BussinesMessage;
import com.starcor.message.MessageHandler;
import com.starcor.report.Column.Column;
import com.starcor.report.ReportMessage;
import com.starcor.report.ReportService;
import com.starcor.report.ReportService.OnReportServiceOkListener;
import java.util.Set;
import org.json.JSONObject;

public class CommonRecevier extends BroadcastReceiver
{
  public static final String CMD_PLAY_TIMESHIFT_VIDEO = "show_timeshift_list";
  public static final String CMD_PLAY_VIDEO = "play_video";
  public static final String CMD_SHOW_APPS_LIST = "show_apps_list";
  public static final String CMD_SHOW_CATEGORY = "show_category";
  public static final String CMD_SHOW_DETAIL = "show_video_detail";
  public static final String CMD_SHOW_FAVORITE = "show_favorite";
  public static final String CMD_SHOW_PLAY_LIST = "show_play_list";
  public static final String CMD_SHOW_SEARCH = "show_search";
  public static final String CMD_SHOW_SPECIAL_PAGE = "show_special_subject";
  public static final String CMD_START_APP_BY_PARAMS = "start_app_by_params";
  public static final String RECEIVE_BROADCAST_ACTION = "com.starcor.hunan.hmd.playvideo";
  public static final String RECEIVE_HMD_RECORD = "com.starcor.hunan.hmd.record";
  private static final String TAG = "CommonRecevier";
  private static ReportService.OnReportServiceOkListener listener = new ReportService.OnReportServiceOkListener()
  {
    public void onReportServiceOk()
    {
      if (!CommonRecevier.mIntent.getBooleanExtra("flag_action_from_mgtv", false))
        SplashActivity.reportStartApp(true);
    }
  };
  private static Intent mIntent = null;

  public static String buildIntentExtraToString(Intent paramIntent)
  {
    String str;
    if (paramIntent == null)
      str = "intent is null";
    while (true)
    {
      return str;
      Bundle localBundle = paramIntent.getExtras();
      if (localBundle == null)
        return "bundle is null";
      Set localSet = localBundle.keySet();
      if (localSet == null)
        return "bundle.keySet is null";
      Object[] arrayOfObject = localSet.toArray();
      str = "";
      for (int i = 0; i < arrayOfObject.length; i++)
        str = str + arrayOfObject[i] + "=" + localBundle.get(new StringBuilder().append(arrayOfObject[i]).append("").toString()) + "---";
    }
  }

  public static void processReceiveIntentReport(Intent paramIntent, Context paramContext)
  {
    Logger.i("CommonRecevier", "onReceive reportEnterCall");
    mIntent = paramIntent;
    if (!ReportService.isAlive)
    {
      ReportService.setReportListener(listener);
      Logger.i("ReportService", "ReportService is not alive, start Service");
      paramContext.startService(new Intent(paramContext, ReportService.class));
    }
    do
    {
      return;
      Logger.i("ReportService", "ReportService is alive");
    }
    while (mIntent.getBooleanExtra("flag_action_from_mgtv", false));
    SplashActivity.reportStartApp(true);
    GlobalLogic.getInstance().setIsFromOut(true);
  }

  public static void reportEnterCall(Intent paramIntent)
  {
    Logger.i("start reportEnterCall " + paramIntent.getBooleanExtra("flag_action_from_mgtv", false));
    String str1;
    if (paramIntent.getBooleanExtra("isFromWeiXin", false))
      str1 = "IMGO";
    while (true)
    {
      ReportMessage localReportMessage = new ReportMessage();
      localReportMessage.setDelayReportEnable(true);
      JSONObject localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("act", "enter_call");
        localJSONObject.put("bid", "3.6.0");
        localJSONObject.put("aid", str1);
        localJSONObject.put("params", paramIntent.toString());
        localReportMessage.mExtData = new Column().buildJsonData(localJSONObject);
        localReportMessage.setDesc("入口调用上报事件");
        MessageHandler.instance().doNotify(localReportMessage);
        Logger.i("reportEnterCall doNotify");
        return;
        if (paramIntent.getBooleanExtra("flag_action_from_mgtv", false))
        {
          str1 = "APK";
          continue;
        }
        GlobalLogic.getInstance().setIsFromOut(true);
        str1 = MgtvVersion.getFactory();
        String str2 = paramIntent.getStringExtra("cmd_ex");
        if (DeviceInfo.isHMD())
          str2 = paramIntent.getAction();
        boolean bool1 = DeviceInfo.isTCL_RT2995();
        int i = 0;
        String str3;
        if (bool1)
        {
          str3 = paramIntent.getStringExtra("cmd");
          boolean bool2 = TextUtils.isEmpty(str3);
          i = 0;
          if (bool2);
        }
        try
        {
          int j = new JSONObject(str3).getInt("play_video_direct");
          i = j;
          if (("show_video_detail".equals(str2)) || ("com.starcor.hunan.hmd.record".equals(str2)))
            str1 = str1 + "-1";
        }
        catch (Exception localException2)
        {
          while (true)
            i = 0;
        }
        if ((!"play_video".equals(str2)) && (!"com.starcor.hunan.hmd.playvideo".equals(str2)))
          continue;
        if (DeviceInfo.isHMD())
        {
          str1 = str1 + "-1";
          continue;
        }
        if (DeviceInfo.isTCL_RT2995())
        {
          if (i == 0)
          {
            str1 = str1 + "-1";
            continue;
          }
          str1 = str1 + "-2";
          continue;
        }
        str1 = str1 + "-2";
      }
      catch (Exception localException1)
      {
        while (true)
          Logger.w("ReportService", "json exception!", localException1);
      }
    }
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Logger.i("CommonRecevier", "CommonBroadcastCommandBuilder common onReceive" + paramIntent.toString() + " data:" + buildIntentExtraToString(paramIntent) + ",action=" + paramIntent.getAction());
    if (CommonSender.isSendFromInner(paramIntent));
    while (!GlobalLogic.getInstance().isProcessIntent(paramIntent))
      return;
    BussinesMessage localBussinesMessage = new BussinesMessage(this, paramContext);
    localBussinesMessage.setIntent(paramIntent);
    MessageHandler.instance().doNotify(localBussinesMessage);
    processReceiveIntentReport(paramIntent, paramContext);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.uilogic.CommonRecevier
 * JD-Core Version:    0.6.2
 */