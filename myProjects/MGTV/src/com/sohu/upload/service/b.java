package com.sohu.upload.service;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.sohu.location.a;

class b extends Handler
{
  b(CountService paramCountService, Looper paramLooper)
  {
    super(paramLooper);
  }

  public void dispatchMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
      CountService.h(this.a).sendEmptyMessageDelayed(1, 60000L);
      return;
    case 1:
    }
    c localc = new c(this);
    if (CountService.c(this.a) != null)
      CountService.c(this.a).a(this.a, localc);
    while (true)
    {
      CountService.d(this.a);
      new d(this).start();
      CountService.a(this.a);
      break;
      CountService.a(this.a, new a());
      CountService.c(this.a).a(this.a, localc);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.upload.service.b
 * JD-Core Version:    0.6.2
 */