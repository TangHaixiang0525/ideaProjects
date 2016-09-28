package com.xiaomi.account.openauth;

import android.accounts.OperationCanceledException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public abstract interface XiaomiOAuthFuture<V>
{
  public abstract V getResult()
    throws OperationCanceledException, IOException, XMAuthericationException;

  public abstract V getResult(long paramLong, TimeUnit paramTimeUnit)
    throws OperationCanceledException, IOException, XMAuthericationException;

  public abstract void set(V paramV);

  public abstract void setException(Throwable paramThrowable);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.xiaomi.account.openauth.XiaomiOAuthFuture
 * JD-Core Version:    0.6.2
 */