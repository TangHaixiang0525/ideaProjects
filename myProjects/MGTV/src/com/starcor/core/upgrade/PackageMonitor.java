package com.starcor.core.upgrade;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class PackageMonitor extends BroadcastReceiver
{
  public static final int MSG_PACKAGE_INSTALLED = 1;
  static Handler _handler;

  public static void bindHandler(Handler paramHandler)
  {
    try
    {
      _handler = paramHandler;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private static void sendMessage(int paramInt, String paramString)
  {
    try
    {
      Handler localHandler = _handler;
      if (localHandler == null);
      while (true)
      {
        return;
        Message localMessage = new Message();
        localMessage.what = paramInt;
        localMessage.obj = paramString;
        _handler.sendMessage(localMessage);
      }
    }
    finally
    {
    }
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Log.d("package_monitor.onReceive", paramIntent.getAction());
    Log.d("package_monitor.onReceive", paramIntent.getDataString());
    if (paramIntent.getAction() == "android.intent.action.PACKAGE_ADDED")
      sendMessage(1, paramIntent.getDataString());
    while (paramIntent.getAction() != "android.intent.action.PACKAGE_REPLACED")
      return;
    sendMessage(1, paramIntent.getDataString());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.upgrade.PackageMonitor
 * JD-Core Version:    0.6.2
 */