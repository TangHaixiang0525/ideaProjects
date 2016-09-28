package com.starcor.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import java.util.ArrayList;
import java.util.List;

public class BroadCastDispather
{
  private static BroadCastDispather mBroadCastDispather = new BroadCastDispather();
  private List<BroadCastItem> receivers = new ArrayList();

  public static BroadCastDispather getInstance()
  {
    return mBroadCastDispather;
  }

  public void destory()
  {
    this.receivers.clear();
  }

  public void registerReceiver(Context paramContext, Handler paramHandler, BroadcastReceiver paramBroadcastReceiver, IntentFilter paramIntentFilter)
  {
    if ((paramBroadcastReceiver == null) || (paramIntentFilter == null))
      return;
    this.receivers.add(new BroadCastItem(paramBroadcastReceiver, paramIntentFilter, paramContext, paramHandler));
  }

  public void sendBroadcast(final Intent paramIntent)
  {
    String str = paramIntent.getAction();
    for (int i = 0; i < this.receivers.size(); i++)
    {
      final BroadCastItem localBroadCastItem = (BroadCastItem)this.receivers.get(i);
      if (localBroadCastItem.filter.hasAction(str))
        localBroadCastItem.handler.post(new Runnable()
        {
          public void run()
          {
            localBroadCastItem.receiver.onReceive(localBroadCastItem.context, paramIntent);
          }
        });
    }
  }

  public void unregisterReceiver(BroadcastReceiver paramBroadcastReceiver)
  {
    for (int i = -1 + this.receivers.size(); i >= 0; i--)
    {
      BroadCastItem localBroadCastItem = (BroadCastItem)this.receivers.get(i);
      if (localBroadCastItem.receiver == paramBroadcastReceiver)
        this.receivers.remove(localBroadCastItem);
    }
  }

  class BroadCastItem
  {
    Context context;
    IntentFilter filter;
    Handler handler;
    BroadcastReceiver receiver;

    public BroadCastItem(BroadcastReceiver paramIntentFilter, IntentFilter paramContext, Context paramHandler, Handler arg5)
    {
      this.receiver = paramIntentFilter;
      this.filter = paramContext;
      this.context = paramHandler;
      Object localObject;
      this.handler = localObject;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.service.BroadCastDispather
 * JD-Core Version:    0.6.2
 */