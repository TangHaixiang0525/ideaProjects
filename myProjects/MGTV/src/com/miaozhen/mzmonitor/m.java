package com.miaozhen.mzmonitor;

class m extends Thread
{
  m(k paramk)
  {
  }

  public void run()
  {
    try
    {
      Thread.sleep(1000 * MZSdkProfile.getLocationServiceTimeout(k.b(this.a)));
      k.a(this.a);
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.m
 * JD-Core Version:    0.6.2
 */