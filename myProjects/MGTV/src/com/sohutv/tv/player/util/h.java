package com.sohutv.tv.player.util;

import com.sohutv.tv.player.interfaces.PlayerCallback;

public class h
{
  private static h a = new h();
  private g.a b;
  private PlayerCallback c;

  public static h a()
  {
    return a;
  }

  public void a(PlayerCallback paramPlayerCallback)
  {
    this.c = paramPlayerCallback;
  }

  public void a(g.a parama)
  {
    this.b = parama;
  }

  public void a(Throwable paramThrowable)
  {
    if (this.b != null)
      this.b.a(paramThrowable);
    if (this.c != null)
      this.c.throwableCallBack(paramThrowable);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.util.h
 * JD-Core Version:    0.6.2
 */