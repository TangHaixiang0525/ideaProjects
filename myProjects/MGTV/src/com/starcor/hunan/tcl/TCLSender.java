package com.starcor.hunan.tcl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.starcor.config.DeviceInfo;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import java.util.Set;

public class TCLSender
{
  private static final String ACTION_ADD_COLLECTION = "com.tv.collection";
  private static final String ACTION_ADD_COLLECTION_2995 = "com.tv.favorite.add";
  private static final String ACTION_ADD_PALY_LIST_2995 = "com.tv.history.add";
  private static final String ACTION_ADD_PALY_STOP = "com.tv.playstop";
  private static final String ACTION_DEL_ALL_COLLECTION = "com.tv.totcl.delall";
  private static final String ACTION_DEL_COLLECTION_2995 = "com.tv.favorite.del.tolauncher";
  private static final String ACTION_DEL_PALY_LIST_2995 = "com.tv.history.del.tolauncher";
  private static final String ACTION_DEL_SINGLE_COLLECTION = "com.tv.totcl.delsingle";
  private static final String SRC_APP = "com.starcor.hunan";
  private static final String SRC_APP_ACTION_TCL2995 = App.getAppContext().getPackageName();
  private static final String TAG = "TCLSender";
  private static final String TCL_CMD_STRING_PLAYVIDEO = "playvideo";
  private static final String TCL_CMD_STRING_SHOWVIDEODETAIL = "showvideodetail";

  private static String buildIntentExtraToString(Intent paramIntent)
  {
    Bundle localBundle = paramIntent.getExtras();
    Object[] arrayOfObject = localBundle.keySet().toArray();
    String str = "";
    for (int i = 0; i < arrayOfObject.length; i++)
      str = str + arrayOfObject[i] + "=" + localBundle.get(new StringBuilder().append(arrayOfObject[i]).append("").toString()) + "---";
    return str;
  }

  private static boolean isInTCL2995AndTcLAllBill()
  {
    return DeviceInfo.isTCL_RT2995();
  }

  public static void sendAddCollectBroadcast(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, int paramInt3, String paramString6, String paramString7)
  {
    if (TextUtils.isEmpty(paramString1))
      return;
    Logger.i("TCLSender", "sendAddCollectBroadcast--videoId:" + paramString1 + "--videoName:" + paramString2 + " duration:" + paramInt3);
    Intent localIntent = new Intent();
    localIntent.addFlags(32);
    if (isInTCL2995AndTcLAllBill())
    {
      localIntent.setAction("com.tv.favorite.add");
      localIntent.putExtra("srcApp", SRC_APP_ACTION_TCL2995);
      localIntent.putExtra("cmdstring", "showvideodetail");
      if ((paramString4 != null) && (!TextUtils.isEmpty(paramString4)))
        localIntent.putExtra("episodeId", 1 + Integer.parseInt(paramString4) + "");
    }
    while (true)
    {
      localIntent.putExtra("videoId", paramString1);
      localIntent.putExtra("videoName", paramString2);
      localIntent.putExtra("videoImgUrl", paramString3);
      localIntent.putExtra("episodeName", paramString5);
      localIntent.putExtra("episodeCount", paramInt1);
      localIntent.putExtra("duration", paramInt3);
      localIntent.putExtra("definition", paramString6);
      localIntent.putExtra("currentPosition", paramInt2);
      localIntent.putExtra("cmdinfo", paramString7);
      localIntent.putExtra("userkey", TCLTools.getUserKey());
      paramContext.sendBroadcast(localIntent);
      Logger.i("TCLSender", "sendAddCollectBroadcast data:" + buildIntentExtraToString(localIntent));
      return;
      localIntent.setAction("com.tv.collection");
      localIntent.putExtra("srcApp", "com.starcor.hunan");
      localIntent.putExtra("episodeId", paramString4);
    }
  }

  public static void sendAddPlayListBroadcast(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, int paramInt3, String paramString6, String paramString7)
  {
    if (TextUtils.isEmpty(paramString1));
    while (TextUtils.isEmpty(paramString2))
      return;
    Logger.i("TCLSender", "sendAddPlayListBroadcast--videoId:" + paramString1 + "--videoName:" + paramString2 + "  episodeId:" + paramString4 + "  episodeCount" + "duration :" + paramInt3 + "definition :" + paramString6);
    Intent localIntent = new Intent();
    localIntent.addFlags(32);
    if (isInTCL2995AndTcLAllBill())
    {
      localIntent.setAction("com.tv.history.add");
      localIntent.putExtra("srcApp", SRC_APP_ACTION_TCL2995);
      localIntent.putExtra("cmdstring", "playvideo");
      if ((paramString4 != null) && (!TextUtils.isEmpty(paramString4)))
        localIntent.putExtra("episodeId", 1 + Integer.parseInt(paramString4) + "");
    }
    while (true)
    {
      localIntent.putExtra("videoId", paramString1);
      localIntent.putExtra("videoName", paramString2);
      localIntent.putExtra("videoImgUrl", paramString3);
      localIntent.putExtra("episodeName", paramString5);
      localIntent.putExtra("episodeCount", paramInt1);
      localIntent.putExtra("duration", paramInt3);
      localIntent.putExtra("definition", paramString6);
      localIntent.putExtra("currentPosition", paramInt2);
      localIntent.putExtra("cmdinfo", paramString7);
      localIntent.putExtra("userkey", TCLTools.getUserKey());
      GeneralUtils.printIntentExtras("TCLSender", localIntent);
      paramContext.sendBroadcast(localIntent);
      return;
      localIntent.setAction("com.tv.playstop");
      localIntent.putExtra("srcApp", "com.starcor.hunan");
      localIntent.putExtra("episodeId", paramString4);
    }
  }

  public static void sendDelAllCollecttionBroadcast(Context paramContext)
  {
    Logger.i("TCLSender", "sendDelAllCollecttionBroadcast");
    Intent localIntent = new Intent();
    localIntent.addFlags(32);
    if (isInTCL2995AndTcLAllBill())
    {
      localIntent.setAction("com.tv.favorite.del.tolauncher");
      localIntent.putExtra("srcApp", SRC_APP_ACTION_TCL2995);
    }
    while (true)
    {
      localIntent.putExtra("identifierType", 1);
      localIntent.putExtra("userkey", TCLTools.getUserKey());
      paramContext.sendBroadcast(localIntent);
      return;
      localIntent.setAction("com.tv.totcl.delall");
      localIntent.putExtra("srcApp", "com.starcor.hunan");
    }
  }

  public static void sendDelAllPlayListBroadcast(Context paramContext)
  {
    Logger.i("TCLSender", "sendDelAllPlayListBroadcast");
    Intent localIntent = new Intent();
    localIntent.addFlags(32);
    if (isInTCL2995AndTcLAllBill())
    {
      localIntent.setAction("com.tv.history.del.tolauncher");
      localIntent.putExtra("srcApp", SRC_APP_ACTION_TCL2995);
    }
    while (true)
    {
      localIntent.putExtra("identifierType", 0);
      localIntent.putExtra("userkey", TCLTools.getUserKey());
      paramContext.sendBroadcast(localIntent);
      return;
      localIntent.setAction("com.tv.totcl.delall");
      localIntent.putExtra("srcApp", "com.starcor.hunan");
    }
  }

  public static void sendDelSingleCollectBroadcast(Context paramContext, String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1))
      return;
    Logger.i("TCLSender", "sendDelCollectBroadcast  videoId:" + paramString1);
    Intent localIntent = new Intent();
    localIntent.addFlags(32);
    if (isInTCL2995AndTcLAllBill())
    {
      localIntent.setAction("com.tv.favorite.del.tolauncher");
      localIntent.putExtra("srcApp", SRC_APP_ACTION_TCL2995);
    }
    while (true)
    {
      localIntent.putExtra("videoId", paramString1);
      localIntent.putExtra("identifierType", "1");
      localIntent.putExtra("cmdinfo", paramString2);
      localIntent.putExtra("userkey", TCLTools.getUserKey());
      paramContext.sendBroadcast(localIntent);
      return;
      localIntent.setAction("com.tv.totcl.delsingle");
      localIntent.putExtra("srcApp", "com.starcor.hunan");
    }
  }

  public static void sendDelSinglePlayListBroadcast(Context paramContext, String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1))
      return;
    Logger.i("TCLSender", "sendDelSinglePlayListBroadcast  videoId:" + paramString1 + "  ");
    Intent localIntent = new Intent();
    localIntent.addFlags(32);
    if (isInTCL2995AndTcLAllBill())
    {
      localIntent.setAction("com.tv.history.del.tolauncher");
      localIntent.putExtra("srcApp", SRC_APP_ACTION_TCL2995);
    }
    while (true)
    {
      localIntent.putExtra("videoId", paramString1);
      localIntent.putExtra("identifierType", "0");
      localIntent.putExtra("cmdinfo", paramString2);
      localIntent.putExtra("userkey", TCLTools.getUserKey());
      paramContext.sendBroadcast(localIntent);
      return;
      localIntent.setAction("com.tv.totcl.delsingle");
      localIntent.putExtra("srcApp", "com.starcor.hunan");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.tcl.TCLSender
 * JD-Core Version:    0.6.2
 */