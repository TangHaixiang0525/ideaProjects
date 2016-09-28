package com.xiaomi.account.openauth;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

abstract class XiaomiOAuthRunnable<V>
  implements Runnable
{
  private static ExecutorService sTaskExecutor = Executors.newCachedThreadPool();
  protected XiaomiOAuthFutureImpl<V> mFuture = new XiaomiOAuthFutureImpl();

  protected abstract void doRun();

  public final void run()
  {
    doRun();
  }

  XiaomiOAuthFutureImpl<V> start()
  {
    sTaskExecutor.execute(this);
    return this.mFuture;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.xiaomi.account.openauth.XiaomiOAuthRunnable
 * JD-Core Version:    0.6.2
 */