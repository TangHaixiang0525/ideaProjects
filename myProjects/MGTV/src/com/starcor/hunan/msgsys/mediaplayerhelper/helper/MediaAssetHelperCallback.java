package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.os.Bundle;

public abstract interface MediaAssetHelperCallback
{
  public abstract void onError(String paramString1, String paramString2);

  public abstract void onSuccess(String paramString, Bundle paramBundle);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.MediaAssetHelperCallback
 * JD-Core Version:    0.6.2
 */