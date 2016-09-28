package com.sohu.app.ads.sdk.d;

import android.os.Handler;
import android.os.Message;

class b extends Handler
{
  b(a parama)
  {
  }

  public void dispatchMessage(Message paramMessage)
  {
    boolean bool = true;
    switch (paramMessage.what)
    {
    case 4:
    case 6:
    default:
      return;
    case 5:
      Object localObject2 = paramMessage.obj;
      if (localObject2 != null)
      {
        com.sohu.app.ads.sdk.model.a locala2 = (com.sohu.app.ads.sdk.model.a)localObject2;
        StringBuilder localStringBuilder2 = new StringBuilder().append("adCommon is null:");
        if (locala2 == null);
        while (true)
        {
          com.sohu.app.ads.sdk.c.a.c(bool);
          a.a(this.a).a(2, locala2);
          return;
          bool = false;
        }
      }
      a.a(this.a).a(2, null);
      return;
    case 7:
    }
    Object localObject1 = paramMessage.obj;
    if (localObject1 != null)
    {
      com.sohu.app.ads.sdk.model.a locala1 = (com.sohu.app.ads.sdk.model.a)localObject1;
      StringBuilder localStringBuilder1 = new StringBuilder().append("adCommon is null:");
      if (locala1 == null);
      while (true)
      {
        com.sohu.app.ads.sdk.c.a.c(bool);
        a.a(this.a).a(6, locala1);
        return;
        bool = false;
      }
    }
    a.a(this.a).a(6, null);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.d.b
 * JD-Core Version:    0.6.2
 */