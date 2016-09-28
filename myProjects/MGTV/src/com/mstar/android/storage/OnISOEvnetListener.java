package com.mstar.android.storage;

public abstract class OnISOEvnetListener
{
  public static final int MOUNTED = 1;
  public static final int UNMOUNTED = 2;

  public void onISOStateChange(String paramString, int paramInt)
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.storage.OnISOEvnetListener
 * JD-Core Version:    0.6.2
 */