package com.sohu.app.ads.sdk.b;

import com.sohu.app.ads.sdk.iterface.IAdsLoadedError;
import com.sohu.app.ads.sdk.model.emu.ErrorType;

public class b
  implements IAdsLoadedError
{
  private ErrorType a;
  private String b;

  public b(ErrorType paramErrorType, String paramString)
  {
    this.a = paramErrorType;
    this.b = paramString;
  }

  public String getErrorMessage()
  {
    return this.b;
  }

  public ErrorType getErrorType()
  {
    return this.a;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.b.b
 * JD-Core Version:    0.6.2
 */