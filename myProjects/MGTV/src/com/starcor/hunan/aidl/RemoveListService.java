package com.starcor.hunan.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.starcor.core.domain.RecordList;
import com.starcor.core.domain.RecordList.OnRemoveCollectListListener;
import com.starcor.core.domain.RecordList.OnRemovePlayListListener;
import com.starcor.core.domain.RecordList.OnRemoveTracePlayListListener;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.hunan.uilogic.InitApiData;
import com.starcor.hunan.uilogic.InitApiData.onApiOKListener;
import java.util.HashMap;

public class RemoveListService extends Service
{
  private boolean isIniting = false;
  private HashMap<String, INotifyStatus> map = new HashMap();

  public IBinder onBind(Intent paramIntent)
  {
    return new MyService(null);
  }

  private class MyService extends IRemoveListService.Stub
  {
    private InitApiData.onApiOKListener listener = new InitApiData.onApiOKListener()
    {
      public void onApiOk(int paramAnonymousInt)
      {
        RemoveListService.access$102(RemoveListService.this, false);
        if (RemoveListService.this.map != null)
        {
          INotifyStatus localINotifyStatus1 = (INotifyStatus)RemoveListService.this.map.get("ClearPlay");
          if (localINotifyStatus1 != null)
            RemoveListService.MyService.this.removeAllPlayList(localINotifyStatus1);
          INotifyStatus localINotifyStatus2 = (INotifyStatus)RemoveListService.this.map.get("ClearCollect");
          if (localINotifyStatus2 != null)
            RemoveListService.MyService.this.removeAllCollectList(localINotifyStatus2);
          INotifyStatus localINotifyStatus3 = (INotifyStatus)RemoveListService.this.map.get("ClearAfter");
          if (localINotifyStatus3 != null)
            RemoveListService.MyService.this.removeAllTracePlayList(localINotifyStatus3);
        }
      }

      public void onGetApiDataError()
      {
        RemoveListService.access$102(RemoveListService.this, false);
        INotifyStatus localINotifyStatus1;
        if (RemoveListService.this.map != null)
        {
          localINotifyStatus1 = (INotifyStatus)RemoveListService.this.map.get("ClearPlay");
          if (localINotifyStatus1 == null);
        }
        try
        {
          localINotifyStatus1.notify(false);
          localINotifyStatus2 = (INotifyStatus)RemoveListService.this.map.get("ClearCollect");
          if (localINotifyStatus2 == null);
        }
        catch (RemoteException localRemoteException2)
        {
          try
          {
            INotifyStatus localINotifyStatus2;
            localINotifyStatus2.notify(false);
            localINotifyStatus3 = (INotifyStatus)RemoveListService.this.map.get("ClearAfter");
            if (localINotifyStatus3 == null);
          }
          catch (RemoteException localRemoteException2)
          {
            try
            {
              while (true)
              {
                INotifyStatus localINotifyStatus3;
                localINotifyStatus3.notify(false);
                return;
                localRemoteException3 = localRemoteException3;
                localRemoteException3.printStackTrace();
              }
              localRemoteException2 = localRemoteException2;
              localRemoteException2.printStackTrace();
            }
            catch (RemoteException localRemoteException1)
            {
              localRemoteException1.printStackTrace();
            }
          }
        }
      }

      public void onNeedFinishActivity()
      {
      }
    };

    private MyService()
    {
    }

    private void removeAllCollectList(final INotifyStatus paramINotifyStatus)
    {
      RecordList.getInstance().removeAllCollectionListInfo(new RecordList.OnRemoveCollectListListener()
      {
        public void onError()
        {
          try
          {
            paramINotifyStatus.notify(false);
            return;
          }
          catch (RemoteException localRemoteException)
          {
            localRemoteException.printStackTrace();
          }
        }

        public void onSuccess()
        {
          try
          {
            paramINotifyStatus.notify(true);
            return;
          }
          catch (RemoteException localRemoteException)
          {
            localRemoteException.printStackTrace();
          }
        }
      });
    }

    private void removeAllPlayList(final INotifyStatus paramINotifyStatus)
    {
      RecordList.getInstance().removeAllPlayListInfo(new RecordList.OnRemovePlayListListener()
      {
        public void onError()
        {
          try
          {
            paramINotifyStatus.notify(false);
            return;
          }
          catch (RemoteException localRemoteException)
          {
            localRemoteException.printStackTrace();
          }
        }

        public void onSuccess()
        {
          try
          {
            paramINotifyStatus.notify(true);
            return;
          }
          catch (RemoteException localRemoteException)
          {
            localRemoteException.printStackTrace();
          }
        }
      });
    }

    private void removeAllTracePlayList(final INotifyStatus paramINotifyStatus)
    {
      RecordList.getInstance().removeAllTracePlayListInfo(new RecordList.OnRemoveTracePlayListListener()
      {
        public void onError()
        {
          try
          {
            paramINotifyStatus.notify(false);
            return;
          }
          catch (RemoteException localRemoteException)
          {
            localRemoteException.printStackTrace();
          }
        }

        public void onSuccess()
        {
          try
          {
            paramINotifyStatus.notify(true);
            return;
          }
          catch (RemoteException localRemoteException)
          {
            localRemoteException.printStackTrace();
          }
        }
      });
    }

    public void clearCollectList(INotifyStatus paramINotifyStatus)
    {
      if (GlobalLogic.getInstance().isAppInterfaceReady())
      {
        if (paramINotifyStatus != null)
          removeAllCollectList(paramINotifyStatus);
        return;
      }
      if (!RemoveListService.this.isIniting)
      {
        RemoveListService.access$102(RemoveListService.this, true);
        RemoveListService.this.map.put("ClearCollect", paramINotifyStatus);
        new InitApiData(RemoveListService.this, false).setOnApiOkListener(this.listener);
        return;
      }
      RemoveListService.this.map.put("ClearCollect", paramINotifyStatus);
    }

    public void clearPlayList(INotifyStatus paramINotifyStatus)
    {
      if (GlobalLogic.getInstance().isAppInterfaceReady())
      {
        if (paramINotifyStatus != null)
          removeAllPlayList(paramINotifyStatus);
        return;
      }
      if (!RemoveListService.this.isIniting)
      {
        RemoveListService.access$102(RemoveListService.this, true);
        RemoveListService.this.map.put("ClearPlay", paramINotifyStatus);
        new InitApiData(RemoveListService.this, false).setOnApiOkListener(this.listener);
        return;
      }
      RemoveListService.this.map.put("ClearPlay", paramINotifyStatus);
    }

    public void clearTracePlayList(INotifyStatus paramINotifyStatus)
    {
      if (GlobalLogic.getInstance().isAppInterfaceReady())
      {
        if (paramINotifyStatus != null)
          removeAllTracePlayList(paramINotifyStatus);
        return;
      }
      if (!RemoveListService.this.isIniting)
      {
        RemoveListService.access$102(RemoveListService.this, true);
        RemoveListService.this.map.put("ClearAfter", paramINotifyStatus);
        new InitApiData(RemoveListService.this, false).setOnApiOkListener(this.listener);
        return;
      }
      RemoveListService.this.map.put("ClearAfter", paramINotifyStatus);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.aidl.RemoveListService
 * JD-Core Version:    0.6.2
 */