package com.starcor.service;

import android.os.Handler;
import java.lang.reflect.Method;

public class DelayInvokeFilter
  implements Runnable
{
  private long delayTime;
  private Handler mHandler = new Handler();
  private final Method method;
  private Object object;
  private Object[] params;
  private boolean run;

  public DelayInvokeFilter(Method paramMethod)
  {
    this.method = paramMethod;
  }

  public void delayAgain()
  {
    if ((this.object == null) || (this.params == null))
      return;
    this.mHandler.removeCallbacks(this);
    this.mHandler.postDelayed(this, this.delayTime);
  }

  public void destory()
  {
    this.mHandler.removeCallbacks(this);
    this.object = null;
    this.params = null;
  }

  public boolean filter(Object paramObject, Object[] paramArrayOfObject)
  {
    if (this.delayTime <= 0L);
    while (this.run)
      return false;
    this.object = paramObject;
    this.params = paramArrayOfObject;
    delayAgain();
    return true;
  }

  public long getDelayTime()
  {
    return this.delayTime;
  }

  public void run()
  {
    this.run = true;
    try
    {
      this.method.setAccessible(true);
      this.method.invoke(this.object, this.params);
      destory();
      this.run = false;
      return;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        destory();
      }
    }
    finally
    {
      destory();
    }
  }

  public void setDelayTime(long paramLong)
  {
    this.delayTime = paramLong;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.service.DelayInvokeFilter
 * JD-Core Version:    0.6.2
 */