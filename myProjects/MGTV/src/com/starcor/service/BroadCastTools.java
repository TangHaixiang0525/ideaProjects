package com.starcor.service;

import android.content.Context;
import android.content.Intent;

public class BroadCastTools
{
  public static final int STATE_CHECK_NET = 2;
  public static final int STATE_CHECK_UPDATE = 4;
  public static final int STATE_CHECK_USERINFO = 5;
  public static final int STATE_COMPLETED = 10;
  public static final int STATE_DECODE_IMG = 9;
  public static final int STATE_GET_CATEGORY_ICON = 7;
  public static final int STATE_GET_EPGDATA = 6;
  public static final int STATE_GET_POSTER = 8;
  public static final int STATE_REQUEST_URLS = 3;
  public static final int STATE_RUNNING = 100;
  public static final int STATE_START = 0;
  public static final int STATE_WAIT_SERVICE = 1;

  public static void sendBroadcast(Context paramContext, int paramInt)
  {
    if (InitService.isSendBroadcast)
    {
      Intent localIntent = new Intent();
      localIntent.setAction("com.starcor.hunan.startservice");
      localIntent.addFlags(32);
      localIntent.putExtra("state", paramInt);
      paramContext.sendBroadcast(localIntent);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.service.BroadCastTools
 * JD-Core Version:    0.6.2
 */