package com.starcor.hunan.uilogic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.starcor.core.utils.Logger;
import java.util.Set;

public class AppRecevier extends BroadcastReceiver
{
  public static final String ACTION = "com.starcor.hunan.logic_event.user_cpl";
  public static final String CMD_ADD_COLLECTION = "com.starcor.hunan.cmd.notify_add_collect";
  public static final String CMD_ADD_PLAY_LIST = "com.starcor.hunan.cmd.notify_add_play_record";
  public static final String CMD_ADD_TRACEPLAY = "com.starcor.hunan.cmd.notify_add_traceplay";
  public static final String CMD_DEL_COLLECTION = "com.starcor.hunan.cmd.notify_delete_collect";
  public static final String CMD_DEL_TRACEPLAY = "com.starcor.hunan.cmd.notify_delete_traceplay";
  public static final String CMD_GET_AUTH_SUCCESS = "com.starcor.hunan.cmd.user_auth_success";
  public static final String CMD_SET_SCORE_SUCCESS = "com.starcor.hunan.cmd.set_score_success";
  private static final String TAG = "AppRecevier";
  private OnReceiverBraodCast mOnReceiverBraodCast;

  private String buildIntentExtraToString(Intent paramIntent)
  {
    Bundle localBundle = paramIntent.getExtras();
    Object[] arrayOfObject = localBundle.keySet().toArray();
    String str = "";
    for (int i = 0; i < arrayOfObject.length; i++)
      str = str + arrayOfObject[i] + "=" + localBundle.get(new StringBuilder().append(arrayOfObject[i]).append("").toString()) + "---";
    Logger.i("AppRecevier", "buildIntentExtraToString intent:" + str);
    return str;
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    buildIntentExtraToString(paramIntent);
    String str1 = paramIntent.getStringExtra("Cmd");
    if (str1 == null);
    String str2;
    int i;
    do
    {
      return;
      str2 = paramIntent.getStringExtra("VideoId");
      i = paramIntent.getIntExtra("score", 2);
      if (("com.starcor.hunan.cmd.notify_add_collect".equals(str1)) && (this.mOnReceiverBraodCast != null))
        this.mOnReceiverBraodCast.onAddCollect(str2);
      if (("com.starcor.hunan.cmd.notify_delete_collect".equals(str1)) && (this.mOnReceiverBraodCast != null))
        this.mOnReceiverBraodCast.onDelCollect(str2);
      if (("com.starcor.hunan.cmd.notify_add_traceplay".equals(str1)) && (this.mOnReceiverBraodCast != null))
        this.mOnReceiverBraodCast.onAddTracePlay(str2);
      if (("com.starcor.hunan.cmd.notify_delete_traceplay".equals(str1)) && (this.mOnReceiverBraodCast != null))
        this.mOnReceiverBraodCast.onDelTracePlay(str2);
      if (("com.starcor.hunan.cmd.user_auth_success".equals(str1)) && (this.mOnReceiverBraodCast != null))
        this.mOnReceiverBraodCast.onGetAuthSuccess(str2);
    }
    while ((!"com.starcor.hunan.cmd.set_score_success".equals(str1)) || (this.mOnReceiverBraodCast == null));
    this.mOnReceiverBraodCast.onSetScoreOk(str2, i);
  }

  public void setOnReceiverBraodCast(OnReceiverBraodCast paramOnReceiverBraodCast)
  {
    this.mOnReceiverBraodCast = paramOnReceiverBraodCast;
  }

  public static abstract interface OnReceiverBraodCast
  {
    public abstract void onAddCollect(String paramString);

    public abstract void onAddTracePlay(String paramString);

    public abstract void onDelCollect(String paramString);

    public abstract void onDelTracePlay(String paramString);

    public abstract void onGetAuthSuccess(String paramString);

    public abstract void onSetScoreOk(String paramString, int paramInt);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.uilogic.AppRecevier
 * JD-Core Version:    0.6.2
 */