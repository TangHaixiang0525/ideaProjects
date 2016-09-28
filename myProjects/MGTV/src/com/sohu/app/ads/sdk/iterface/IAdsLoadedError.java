package com.sohu.app.ads.sdk.iterface;

import com.sohu.app.ads.sdk.model.emu.ErrorType;

public abstract interface IAdsLoadedError
{
  public abstract String getErrorMessage();

  public abstract ErrorType getErrorType();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.iterface.IAdsLoadedError
 * JD-Core Version:    0.6.2
 */