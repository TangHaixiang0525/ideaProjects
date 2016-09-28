package com.starcor.hunan.msgsys.mediaplayerhelper;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.MediaAssetHelperCallback;
import com.starcor.hunan.msgsys.mediaplayerhelper.proxy.MediaAssetHelperProxy;

public class MediaPlayerHelper
{
  private static final String TAG = MediaPlayerHelper.class.getSimpleName();
  private Context mContext = null;
  private MediaAssetHelperCallback mListener = null;
  private String mMsgBtnAction = "";
  private Bundle mMsgBtnArgBundle = null;

  public MediaPlayerHelper(Context paramContext, String paramString, Bundle paramBundle)
  {
    this.mMsgBtnAction = paramString;
    this.mContext = paramContext;
    this.mMsgBtnArgBundle = paramBundle;
  }

  private String getOrderState(String paramString)
  {
    String str = "no_order";
    if (!TextUtils.isEmpty(paramString))
    {
      if ((!"back_play".equals(paramString)) && (!"chase_drama".equals(paramString)) && (!"live_show".equals(paramString)) && (!"wish_order".equals(paramString)) && (!"turn_play".equals(paramString)))
        break label60;
      str = "order";
    }
    label60: 
    while ((!"back_play_cancel".equals(paramString)) && (!"chase_drama_cancel".equals(paramString)) && (!"live_show_cancel".equals(paramString)) && (!"wish_order_cancel".equals(paramString)) && (!"turn_play_cancel".equals(paramString)))
      return str;
    return "cancel_order";
  }

  public MediaAssetHelperCallback getListener()
  {
    return this.mListener;
  }

  public void setListener(MediaAssetHelperCallback paramMediaAssetHelperCallback)
  {
    this.mListener = paramMediaAssetHelperCallback;
  }

  public void startExecuteAction()
  {
    Logger.i(TAG, "startExecuteAction action=" + this.mMsgBtnAction + " bundle=" + this.mMsgBtnArgBundle);
    if ((TextUtils.isEmpty(this.mMsgBtnAction)) || (this.mMsgBtnArgBundle == null))
      Logger.i(TAG, "TextUtils.isEmpty(mMsgBtnAction) || null == mMsgBtnArgBundle");
    while (true)
    {
      return;
      try
      {
        MediaAssetHelperProxy localMediaAssetHelperProxy = MediaAssetHelperProxy.getInstance();
        if (localMediaAssetHelperProxy != null)
        {
          localMediaAssetHelperProxy.init(this.mContext);
          if (this.mListener == null)
          {
            Logger.i(TAG, "init mListener");
            this.mListener = new MediaAssetHelperListener(null);
          }
          localMediaAssetHelperProxy.startExecuteAction(this.mMsgBtnAction, this.mMsgBtnArgBundle, this.mListener);
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  private class MediaAssetHelperListener
    implements MediaAssetHelperCallback
  {
    private MediaAssetHelperListener()
    {
    }

    public void onError(String paramString1, String paramString2)
    {
      Logger.i(MediaPlayerHelper.TAG, "MediaAssetHelperListener onError action=" + paramString1 + " errMsg=" + paramString2);
      if ((!"reservetopic".equals(paramString1)) && ("reserveturnplay".equals(paramString1)));
    }

    public void onSuccess(String paramString, Bundle paramBundle)
    {
      Logger.i(MediaPlayerHelper.TAG, "MediaAssetHelperListener onSuccess action=" + paramString + " params=" + paramBundle);
      if ((!"reservetopic".equals(paramString)) && ("reserveturnplay".equals(paramString)));
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.MediaPlayerHelper
 * JD-Core Version:    0.6.2
 */