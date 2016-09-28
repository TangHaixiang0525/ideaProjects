package com.starcor.hunan.interfaces;

public abstract interface SuccessCallBack<Result>
{
  public abstract void getDataError(String paramString, int paramInt);

  public abstract void getDataSuccess(Result paramResult);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.interfaces.SuccessCallBack
 * JD-Core Version:    0.6.2
 */