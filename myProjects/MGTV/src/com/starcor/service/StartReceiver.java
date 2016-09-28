package com.starcor.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.starcor.core.utils.Logger;

public class StartReceiver extends BroadcastReceiver
{
  private static final String ACTION = "com.starcor.hunan.StartQuickService";
  private static final String BOOT_COMPLETED_ACTION = "android.intent.action.BOOT_COMPLETED";
  private static final String TAG = StartReceiver.class.getSimpleName();

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if ((paramContext != null) && (paramIntent != null) && ("com.starcor.hunan.StartQuickService".equals(paramIntent.getAction())))
    {
      Logger.i(TAG, "收到快速启动服务的广播" + paramIntent.getAction());
      paramContext.startService(new Intent(paramContext, InitService.class));
    }
    while ((paramContext == null) || (paramIntent == null) || (!"android.intent.action.BOOT_COMPLETED".equals(paramIntent.getAction())))
      return;
    Logger.i(TAG, "收到快速启动服务的广播" + paramIntent.getAction());
    paramContext.startService(new Intent(paramContext, InitService.class));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.service.StartReceiver
 * JD-Core Version:    0.6.2
 */