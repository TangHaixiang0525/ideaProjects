package com.sohutv.tv.player.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import org.apache.http.Header;

public abstract class c
{
  private Looper a;
  private a b;

  public c()
  {
    this(null);
  }

  public c(Looper paramLooper)
  {
    if (paramLooper == null)
      paramLooper = Looper.getMainLooper();
    this.a = paramLooper;
    if (this.b == null)
      this.b = new a(this, this.a);
  }

  public abstract void a(int paramInt, Header[] paramArrayOfHeader, String paramString);

  public abstract void a(int paramInt, Header[] paramArrayOfHeader, String paramString, Throwable paramThrowable);

  public void a(Object[] paramArrayOfObject)
  {
    Message localMessage = new Message();
    localMessage.what = 0;
    localMessage.obj = paramArrayOfObject;
    this.b.sendMessage(localMessage);
  }

  public void b(Object[] paramArrayOfObject)
  {
    Message localMessage = new Message();
    localMessage.what = 1;
    localMessage.obj = paramArrayOfObject;
    this.b.sendMessage(localMessage);
  }

  public class a extends Handler
  {
    private c b;

    public a(c paramLooper, Looper arg3)
    {
      super();
      this.b = paramLooper;
    }

    public void handleMessage(Message paramMessage)
    {
      Object[] arrayOfObject = (Object[])paramMessage.obj;
      switch (paramMessage.what)
      {
      default:
        return;
      case 0:
        this.b.a(((Integer)arrayOfObject[0]).intValue(), (Header[])arrayOfObject[1], (String)arrayOfObject[2], (Throwable)arrayOfObject[3]);
        return;
      case 1:
      }
      this.b.a(((Integer)arrayOfObject[0]).intValue(), (Header[])arrayOfObject[1], (String)arrayOfObject[2]);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.util.c
 * JD-Core Version:    0.6.2
 */