package com.sohu.app.ads.sdk.d;

import android.app.Dialog;
import com.sohu.app.ads.sdk.f.b;
import com.sohu.app.ads.sdk.iterface.INetCallback;
import com.sohu.app.ads.sdk.model.AdsResponse;
import java.util.ArrayList;

public class h extends g<Object>
{
  protected INetCallback a = null;
  protected int b = -1;

  public h(INetCallback paramINetCallback, int paramInt, Dialog paramDialog, boolean paramBoolean)
  {
    super(paramDialog, paramBoolean);
    this.a = paramINetCallback;
    this.b = paramInt;
  }

  private ArrayList<AdsResponse> a(String paramString1, String paramString2)
  {
    return b.a().a(paramString1, paramString2);
  }

  protected Object a(Object[] paramArrayOfObject)
  {
    switch (this.b)
    {
    default:
      return null;
    case 1000:
    }
    if (paramArrayOfObject[0] != null);
    for (String str1 = (String)paramArrayOfObject[0]; ; str1 = "")
    {
      if (paramArrayOfObject[1] != null);
      for (String str2 = (String)paramArrayOfObject[1]; ; str2 = "")
        return a(str1, str2);
    }
  }

  protected String a(Object paramObject)
  {
    if (this.a != null)
      this.a.netCallback(this.b, paramObject);
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.d.h
 * JD-Core Version:    0.6.2
 */