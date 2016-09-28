package com.huawei.playerinterface.outputblocking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaRouter;
import android.media.MediaRouter.RouteInfo;
import android.media.MediaRouter.SimpleCallback;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.huawei.dmpbase.DmpLog;
import com.huawei.playerinterface.parameter.HASetParam;

@SuppressLint({"NewApi"})
public class MediaRouterCallback extends MediaRouter.SimpleCallback
{
  private static final int isEnable = 1;
  private String TAG = "MediaRouterCallback";
  private Context context;
  private Handler handler;

  public MediaRouterCallback(Context paramContext, Handler paramHandler)
  {
    this.handler = paramHandler;
    this.context = paramContext;
  }

  private void isOutPutBlocking(Object paramObject)
  {
    if (paramObject.equals(Integer.valueOf(1)))
    {
      if (this.context != null);
      try
      {
        MediaRouter localMediaRouter = (MediaRouter)this.context.getSystemService("media_router");
        localMediaRouter.addCallback(2, this);
        MediaRouter.RouteInfo localRouteInfo = localMediaRouter.getSelectedRoute(2);
        Log.i(this.TAG, "isOutPutBlocking =" + localRouteInfo.getPresentationDisplay());
        if (localRouteInfo.getPresentationDisplay() != null)
          onError(100, 110, 0);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        DmpLog.d(this.TAG, "isOutPutBlocking():MediaRouter excetion!");
        return;
      }
    }
    DmpLog.d(this.TAG, "isOutPutBlocking():media video is allow to out put!");
  }

  private void onError(int paramInt1, int paramInt2, int paramInt3)
  {
    Message localMessage = new Message();
    localMessage.what = paramInt1;
    localMessage.arg1 = paramInt2;
    localMessage.arg1 = paramInt3;
    this.handler.sendMessage(localMessage);
  }

  public void onRoutePresentationDisplayChanged(MediaRouter paramMediaRouter, MediaRouter.RouteInfo paramRouteInfo)
  {
    super.onRoutePresentationDisplayChanged(paramMediaRouter, paramRouteInfo);
    DmpLog.d(this.TAG, "mMediaRouterCallback-----onRoutePresentationDisplayChanged");
    onError(100, 110, 0);
  }

  public int setOutPutProperties(HASetParam paramHASetParam, Object paramObject)
  {
    switch (1.$SwitchMap$com$huawei$playerinterface$parameter$HASetParam[paramHASetParam.ordinal()])
    {
    default:
      return 1;
    case 1:
    }
    if ((paramObject instanceof Integer))
    {
      isOutPutBlocking(paramObject);
      return 0;
    }
    DmpLog.e(this.TAG, " setProperties() ->setProperties: failed,value is not Integer");
    return -1;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.outputblocking.MediaRouterCallback
 * JD-Core Version:    0.6.2
 */