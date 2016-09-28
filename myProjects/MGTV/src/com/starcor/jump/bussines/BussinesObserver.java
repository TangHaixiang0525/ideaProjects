package com.starcor.jump.bussines;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import com.starcor.bussines.manager.BussinesManager;
import com.starcor.jump.bussines.behavior.Behavior;
import com.starcor.jump.bussines.data.CommonData;
import com.starcor.message.Message;
import com.starcor.message.MessageObserver;

public class BussinesObserver extends MessageObserver
{
  private BussinesManager mManager;

  public void doNotify(Message paramMessage)
  {
    BussinesMessage localBussinesMessage = (BussinesMessage)paramMessage;
    if (this.mManager == null)
      this.mManager = new BussinesManager();
    CommonData localCommonData = localBussinesMessage.mData;
    if (localCommonData == null)
      localCommonData = new CommonData();
    if ((localBussinesMessage.mSrouceObj instanceof Activity))
    {
      localCommonData.setActivity((Activity)localBussinesMessage.mSrouceObj);
      localCommonData.setInitForType(2);
      if (localBussinesMessage.mIntent != null)
        localCommonData.setIntent(localBussinesMessage.mIntent);
      localCommonData.start();
      if (localBussinesMessage.mBussines == null)
        localBussinesMessage.mBussines = new Behavior(localCommonData).getBussines();
      if ((localBussinesMessage.mCallback == null) || (!(localBussinesMessage.mBussines instanceof ShowGetBussinesErrorBussines)))
        break label211;
      localBussinesMessage.mCallback.onError("no bussines found");
    }
    label211: 
    do
    {
      return;
      if ((localBussinesMessage.mSrouceObj instanceof Service))
      {
        localCommonData.setService((Service)localBussinesMessage.mSrouceObj);
        localCommonData.setInitForType(3);
        break;
      }
      localCommonData.setContext(localBussinesMessage.mContext);
      if (!(localBussinesMessage.mSrouceObj instanceof BroadcastReceiver))
        break;
      localCommonData.setReceiver((BroadcastReceiver)localBussinesMessage.mSrouceObj);
      localCommonData.setInitForType(1);
      break;
      this.mManager.start(localBussinesMessage.mBussines, localCommonData);
      this.mManager.excute();
    }
    while (localBussinesMessage.mCallback == null);
    localBussinesMessage.mCallback.onSuccess();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.BussinesObserver
 * JD-Core Version:    0.6.2
 */