package com.starcor.settings.download;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import com.starcor.settings.utils.Debug;

public class DownloadReceiver extends BroadcastReceiver
{
  SystemFacade mSystemFacade = null;

  private void pauseAllDownload(Context paramContext)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("status", Integer.valueOf(193));
    String str = "status<'200' AND status!='193' AND " + "deleted != '1'";
    paramContext.getContentResolver().update(Downloads.Item.CONTENT_URI, localContentValues, str, null);
  }

  private void startService(Context paramContext)
  {
    paramContext.startService(new Intent(paramContext, DownloadService.class));
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (this.mSystemFacade == null)
      this.mSystemFacade = new RealSystemFacade(paramContext);
    String str = paramIntent.getAction();
    Debug.e("lx", " actioin-->" + str);
    if (str.equals("android.intent.action.BOOT_COMPLETED"))
      startService(paramContext);
    do
    {
      NetworkInfo localNetworkInfo;
      do
      {
        return;
        if (!str.equals("android.net.conn.CONNECTIVITY_CHANGE"))
          break;
        localNetworkInfo = (NetworkInfo)paramIntent.getParcelableExtra("networkInfo");
      }
      while ((localNetworkInfo == null) || (!localNetworkInfo.isConnected()));
      startService(paramContext);
      return;
      if (str.equals("android.intent.action.DOWNLOAD_WAKEUP"))
      {
        startService(paramContext);
        return;
      }
      if (str.equals("android.intent.action.ACTION_SHUTDOWN"))
      {
        pauseAllDownload(paramContext);
        return;
      }
    }
    while ((!str.equals("android.net.wifi.WIFI_STATE_CHANGED")) || (paramIntent.getIntExtra("wifi_state", 1) != 0));
    pauseAllDownload(paramContext);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.download.DownloadReceiver
 * JD-Core Version:    0.6.2
 */