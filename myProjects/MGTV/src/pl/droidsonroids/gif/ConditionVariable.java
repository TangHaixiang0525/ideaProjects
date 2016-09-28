package pl.droidsonroids.gif;

class ConditionVariable
{
  private volatile boolean mCondition;

  void block()
    throws InterruptedException
  {
    try
    {
      if (!this.mCondition)
        wait();
    }
    finally
    {
    }
  }

  void close()
  {
    try
    {
      this.mCondition = false;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  void open()
  {
    try
    {
      boolean bool = this.mCondition;
      this.mCondition = true;
      if (!bool)
        notify();
      return;
    }
    finally
    {
    }
  }

  void set(boolean paramBoolean)
  {
    if (paramBoolean);
    try
    {
      open();
      while (true)
      {
        return;
        close();
      }
    }
    finally
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     pl.droidsonroids.gif.ConditionVariable
 * JD-Core Version:    0.6.2
 */