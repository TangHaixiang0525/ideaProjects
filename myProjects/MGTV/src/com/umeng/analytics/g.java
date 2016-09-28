package com.umeng.analytics;

public abstract class g
  implements Runnable
{
  public abstract void a();

  public void run()
  {
    try
    {
      a();
      return;
    }
    catch (Throwable localThrowable)
    {
      while (localThrowable == null);
      localThrowable.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.g
 * JD-Core Version:    0.6.2
 */