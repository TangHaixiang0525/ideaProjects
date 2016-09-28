package com.sohu.upload.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.sohu.upload.a.a;
import com.sohu.upload.a.b;
import com.sohu.upload.service.CountService;

public class BootBroadcastReceiver extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if ("android.intent.action.BOOT_COMPLETED".equals(paramIntent.getAction()))
    {
      b.a(paramContext);
      a.a("���������㲥...");
      paramContext.startService(new Intent(paramContext, CountService.class));
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.upload.receiver.BootBroadcastReceiver
 * JD-Core Version:    0.6.2
 */