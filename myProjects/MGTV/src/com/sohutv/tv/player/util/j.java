package com.sohutv.tv.player.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class j
{
  private static ScheduledExecutorService a = Executors.newSingleThreadScheduledExecutor();
  private static ExecutorService b = Executors.newCachedThreadPool();

  public static ExecutorService a()
  {
    return b;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.util.j
 * JD-Core Version:    0.6.2
 */