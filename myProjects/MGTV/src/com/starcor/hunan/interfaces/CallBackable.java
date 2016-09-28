package com.starcor.hunan.interfaces;

public abstract interface CallBackable<Result>
{
  public abstract void getDataFailed(String paramString, int paramInt);

  public abstract void getDataSuccess(Result paramResult);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.interfaces.CallBackable
 * JD-Core Version:    0.6.2
 */