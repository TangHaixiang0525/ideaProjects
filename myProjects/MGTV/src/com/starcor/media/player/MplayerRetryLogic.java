package com.starcor.media.player;

import android.text.TextUtils;
import com.starcor.utils.Logger;

public class MplayerRetryLogic
{
  public static final int STATE_PLAY = 257;
  public static final int STATE_REQUEST = 256;
  public static final String TAG = MplayerRetryLogic.class.getSimpleName();
  public int PLAY_TIMES = 3;
  public int REQUEST_TIMES = 1;
  public int play_times = 1;
  public int request_times = 1;
  public int state = 256;
  public String svrip = "";

  MplayerRetryLogic(int paramInt1, int paramInt2)
  {
    this.REQUEST_TIMES = paramInt1;
    this.PLAY_TIMES = paramInt2;
    Logger.d("MplayerV2", "REQUEST_TIMES: " + paramInt1 + ", PLAY_TIMES: " + paramInt2);
  }

  public void addPlayRequestCount()
  {
    if (getRetryState() == 257)
    {
      this.play_times = (1 + this.play_times);
      return;
    }
    this.request_times = (1 + this.request_times);
  }

  public int getRetryState()
  {
    return this.state;
  }

  public int getSvipCount()
  {
    if (TextUtils.isEmpty(this.svrip))
      return 0;
    return this.svrip.split(",").length;
  }

  public void initRetryParams()
  {
    this.svrip = "";
    this.request_times = 1;
    this.play_times = 1;
    this.state = 256;
  }

  public boolean isCanRequest()
  {
    return this.request_times < this.REQUEST_TIMES;
  }

  public boolean isCanRetry()
  {
    return this.play_times < this.PLAY_TIMES;
  }

  public void print(String paramString)
  {
    Logger.i(TAG, paramString + "---> request_times = " + this.request_times + ",play_times = " + this.play_times + ",svrip = " + this.svrip);
  }

  public void printError(String paramString)
  {
    Logger.e(TAG, paramString + "---> !");
  }

  public void removePlayRequestCount()
  {
    if (getRetryState() == 257)
    {
      this.play_times = (-1 + this.play_times);
      return;
    }
    this.request_times = (-1 + this.request_times);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerRetryLogic
 * JD-Core Version:    0.6.2
 */