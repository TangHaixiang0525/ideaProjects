package com.starcor.core.epgapi.params;

import android.text.TextUtils;
import com.starcor.core.epgapi.Api;

public class GetFilmListItemAPI
{
  public static Api getFilmListItemParams(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    return new GetProductItemListParams(paramString, paramInt1, paramInt2, paramInt3);
  }

  public static Api getFilmListItemParams(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
  {
    if (TextUtils.isEmpty(paramString3))
      return new GetFilmItemParams(paramString1, paramString2, paramInt1, paramInt2);
    return new GetFilmLableListParams(paramString1, paramString2, paramInt1, paramInt2, paramString3);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.GetFilmListItemAPI
 * JD-Core Version:    0.6.2
 */