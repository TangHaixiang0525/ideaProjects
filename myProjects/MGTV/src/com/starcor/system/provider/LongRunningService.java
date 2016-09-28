package com.starcor.system.provider;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import com.starcor.core.utils.Logger;

public class LongRunningService extends IntentService
{
  protected static final String TAG = LongRunningService.class.getSimpleName();

  public LongRunningService()
  {
    super(TAG);
    Logger.i(TAG, "LongRunningService()");
  }

  public IBinder onBind(Intent paramIntent)
  {
    Logger.i(TAG, "onBind");
    return super.onBind(paramIntent);
  }

  public void onCreate()
  {
    Logger.i(TAG, "onCreate");
    super.onCreate();
  }

  public void onDestroy()
  {
    Logger.i(TAG, "onDestroy");
    super.onDestroy();
  }

  protected void onHandleIntent(Intent paramIntent)
  {
  }

  public void onStart(Intent paramIntent, int paramInt)
  {
    Logger.i(TAG, "onStart");
    super.onStart(paramIntent, paramInt);
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    Logger.i(TAG, "onStartCommand");
    return super.onStartCommand(paramIntent, paramInt1, paramInt2);
  }

  public void setIntentRedelivery(boolean paramBoolean)
  {
    Logger.i(TAG, "setIntentRedelivery");
    super.setIntentRedelivery(paramBoolean);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.system.provider.LongRunningService
 * JD-Core Version:    0.6.2
 */