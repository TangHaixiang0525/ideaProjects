package com.sohu.app.ads.sdk.iterface;

import android.content.Context;
import android.view.ViewGroup;
import com.sohu.app.ads.sdk.model.RequestComponent;
import java.util.HashMap;

public abstract interface ILoader
{
  public abstract void OpenShareUrl(String paramString);

  public abstract void addAdErrorListener(IAdErrorEventListener paramIAdErrorEventListener);

  public abstract void addAdsLoadedListener(IAdsLoadedListener paramIAdsLoadedListener);

  public abstract void destory();

  public abstract void onDownloadTaskDeleted(String paramString);

  public abstract void onDownloadTaskStarted(HashMap<String, String> paramHashMap);

  public abstract void removePauseAd();

  public abstract void requestAds(RequestComponent paramRequestComponent, HashMap<String, String> paramHashMap);

  public abstract void requestPauseAd(Context paramContext, ViewGroup paramViewGroup, HashMap<String, String> paramHashMap, PopWindowCallback paramPopWindowCallback, int paramInt);

  public abstract void requestStartPicture(Context paramContext, HashMap<String, String> paramHashMap, StartPictureCallBack paramStartPictureCallBack);

  public abstract void setDeviceType(int paramInt);

  public abstract void setTimeOut(int paramInt);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.iterface.ILoader
 * JD-Core Version:    0.6.2
 */