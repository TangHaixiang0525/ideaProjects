package com.xiaomi.account.openauth;

import android.accounts.OperationCanceledException;
import android.os.Looper;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

class XiaomiOAuthFutureImpl<V> extends FutureTask<V>
  implements XiaomiOAuthFuture<V>
{
  private static final long DEFAULT_TIMEOUT_MINUTE = 1L;

  XiaomiOAuthFutureImpl()
  {
    super(new Callable()
    {
      public V call()
        throws Exception
      {
        throw new IllegalStateException("this should never be called");
      }
    });
  }

  private void ensureNotOnMainThread()
  {
    Looper localLooper = Looper.myLooper();
    if ((localLooper != null) && (localLooper == Looper.getMainLooper()))
      throw new IllegalStateException("calling this from your main thread can lead to deadlock");
  }

  // ERROR //
  private V internalGetResult(Long paramLong, TimeUnit paramTimeUnit)
    throws OperationCanceledException, IOException, XMAuthericationException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 57	com/xiaomi/account/openauth/XiaomiOAuthFutureImpl:isDone	()Z
    //   4: ifne +7 -> 11
    //   7: aload_0
    //   8: invokespecial 59	com/xiaomi/account/openauth/XiaomiOAuthFutureImpl:ensureNotOnMainThread	()V
    //   11: aload_1
    //   12: ifnonnull +18 -> 30
    //   15: aload_0
    //   16: invokevirtual 63	com/xiaomi/account/openauth/XiaomiOAuthFutureImpl:get	()Ljava/lang/Object;
    //   19: astore 14
    //   21: aload_0
    //   22: iconst_1
    //   23: invokevirtual 67	com/xiaomi/account/openauth/XiaomiOAuthFutureImpl:cancel	(Z)Z
    //   26: pop
    //   27: aload 14
    //   29: areturn
    //   30: aload_0
    //   31: aload_1
    //   32: invokevirtual 73	java/lang/Long:longValue	()J
    //   35: aload_2
    //   36: invokevirtual 76	com/xiaomi/account/openauth/XiaomiOAuthFutureImpl:get	(JLjava/util/concurrent/TimeUnit;)Ljava/lang/Object;
    //   39: astore 12
    //   41: aload_0
    //   42: iconst_1
    //   43: invokevirtual 67	com/xiaomi/account/openauth/XiaomiOAuthFutureImpl:cancel	(Z)Z
    //   46: pop
    //   47: aload 12
    //   49: areturn
    //   50: astore 11
    //   52: new 41	android/accounts/OperationCanceledException
    //   55: dup
    //   56: invokespecial 77	android/accounts/OperationCanceledException:<init>	()V
    //   59: athrow
    //   60: astore 5
    //   62: aload_0
    //   63: iconst_1
    //   64: invokevirtual 67	com/xiaomi/account/openauth/XiaomiOAuthFutureImpl:cancel	(Z)Z
    //   67: pop
    //   68: aload 5
    //   70: athrow
    //   71: astore 9
    //   73: aload_0
    //   74: iconst_1
    //   75: invokevirtual 67	com/xiaomi/account/openauth/XiaomiOAuthFutureImpl:cancel	(Z)Z
    //   78: pop
    //   79: new 41	android/accounts/OperationCanceledException
    //   82: dup
    //   83: invokespecial 77	android/accounts/OperationCanceledException:<init>	()V
    //   86: athrow
    //   87: astore 7
    //   89: aload_0
    //   90: iconst_1
    //   91: invokevirtual 67	com/xiaomi/account/openauth/XiaomiOAuthFutureImpl:cancel	(Z)Z
    //   94: pop
    //   95: goto -16 -> 79
    //   98: astore_3
    //   99: aload_3
    //   100: invokevirtual 81	java/util/concurrent/ExecutionException:getCause	()Ljava/lang/Throwable;
    //   103: astore 4
    //   105: aload 4
    //   107: instanceof 43
    //   110: ifeq +9 -> 119
    //   113: aload 4
    //   115: checkcast 43	java/io/IOException
    //   118: athrow
    //   119: aload 4
    //   121: instanceof 83
    //   124: ifeq +9 -> 133
    //   127: aload 4
    //   129: checkcast 83	java/lang/RuntimeException
    //   132: athrow
    //   133: aload 4
    //   135: instanceof 85
    //   138: ifeq +9 -> 147
    //   141: aload 4
    //   143: checkcast 85	java/lang/Error
    //   146: athrow
    //   147: aload 4
    //   149: instanceof 45
    //   152: ifeq +9 -> 161
    //   155: aload 4
    //   157: checkcast 45	com/xiaomi/account/openauth/XMAuthericationException
    //   160: athrow
    //   161: aload 4
    //   163: instanceof 41
    //   166: ifeq +9 -> 175
    //   169: aload 4
    //   171: checkcast 41	android/accounts/OperationCanceledException
    //   174: athrow
    //   175: new 32	java/lang/IllegalStateException
    //   178: dup
    //   179: aload 4
    //   181: invokespecial 88	java/lang/IllegalStateException:<init>	(Ljava/lang/Throwable;)V
    //   184: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   15	21	50	java/util/concurrent/CancellationException
    //   30	41	50	java/util/concurrent/CancellationException
    //   15	21	60	finally
    //   30	41	60	finally
    //   52	60	60	finally
    //   99	119	60	finally
    //   119	133	60	finally
    //   133	147	60	finally
    //   147	161	60	finally
    //   161	175	60	finally
    //   175	185	60	finally
    //   15	21	71	java/util/concurrent/TimeoutException
    //   30	41	71	java/util/concurrent/TimeoutException
    //   15	21	87	java/lang/InterruptedException
    //   30	41	87	java/lang/InterruptedException
    //   15	21	98	java/util/concurrent/ExecutionException
    //   30	41	98	java/util/concurrent/ExecutionException
  }

  public V getResult()
    throws IOException, OperationCanceledException, XMAuthericationException
  {
    return internalGetResult(Long.valueOf(1L), TimeUnit.MINUTES);
  }

  public V getResult(long paramLong, TimeUnit paramTimeUnit)
    throws IOException, OperationCanceledException, XMAuthericationException
  {
    return internalGetResult(Long.valueOf(paramLong), paramTimeUnit);
  }

  public void set(V paramV)
  {
    super.set(paramV);
  }

  public void setException(Throwable paramThrowable)
  {
    super.setException(paramThrowable);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.xiaomi.account.openauth.XiaomiOAuthFutureImpl
 * JD-Core Version:    0.6.2
 */