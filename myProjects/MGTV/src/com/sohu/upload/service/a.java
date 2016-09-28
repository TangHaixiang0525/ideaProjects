package com.sohu.upload.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class a extends BroadcastReceiver
{
  a(CountService paramCountService)
  {
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE"))
      CountService.a(this.a);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.upload.service.a
 * JD-Core Version:    0.6.2
 */