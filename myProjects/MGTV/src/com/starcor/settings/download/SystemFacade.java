package com.starcor.settings.download;

import android.app.Notification;
import android.content.Intent;

abstract interface SystemFacade
{
  public abstract void cancelAllNotifications();

  public abstract void cancelNotification(long paramLong);

  public abstract long currentTimeMillis();

  public abstract Integer getActiveNetworkType();

  public abstract void postNotification(long paramLong, Notification paramNotification);

  public abstract void sendBroadcast(Intent paramIntent);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.download.SystemFacade
 * JD-Core Version:    0.6.2
 */