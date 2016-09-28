package com.starcor.service.helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.starcor.hunan.App;
import com.starcor.service.DownloadManagerService;
import com.starcor.service.DownloadTaskInfo;
import com.starcor.service.IDownloadEventListener;
import com.starcor.service.IDownloadEventListener.Stub;
import com.starcor.service.IDownloadManager;
import com.starcor.service.IDownloadManager.Stub;

public class DownloadManager
{
  IDownloadManager _mgr;
  private IDownloadEventListener chainListener = null;
  IDownloadEventListener listener = new IDownloadEventListener.Stub()
  {
    public int onEvent(String paramAnonymousString, int paramAnonymousInt, DownloadTaskInfo paramAnonymousDownloadTaskInfo)
      throws RemoteException
    {
      if (DownloadManager.this.chainListener == null)
        return 0;
      DownloadManager.this.chainListener.onEvent(paramAnonymousString, paramAnonymousInt, paramAnonymousDownloadTaskInfo);
      return 0;
    }
  };
  ServiceConnection serviceConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      if (paramAnonymousIBinder == null)
        return;
      DownloadManager.this._mgr = IDownloadManager.Stub.asInterface(paramAnonymousIBinder);
      try
      {
        DownloadManager.this._mgr.setEventListener(DownloadManager.this.listener);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        DownloadManager.this._mgr = null;
      }
    }

    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      DownloadManager.this._mgr = null;
    }
  };

  public DownloadManager()
  {
    init(App.getAppContext());
  }

  private void init(Context paramContext)
  {
    paramContext.bindService(new Intent(paramContext, DownloadManagerService.class), this.serviceConnection, 1);
  }

  public int cancelTask(int paramInt)
  {
    if (this._mgr == null)
      return -1;
    try
    {
      int i = this._mgr.cancelTask(paramInt);
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return -1;
  }

  public void clear()
  {
    if (this.serviceConnection == null)
      return;
    this._mgr = null;
    App.getAppContext().unbindService(this.serviceConnection);
    this.serviceConnection = null;
  }

  public int createTask(String paramString1, String paramString2, String paramString3)
  {
    if (this._mgr == null)
      return -1;
    try
    {
      int i = this._mgr.createTask(paramString1, paramString2, paramString3);
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return -1;
  }

  public DownloadTaskInfo getTaskInfo(int paramInt)
  {
    if (this._mgr == null)
      return null;
    try
    {
      DownloadTaskInfo localDownloadTaskInfo = this._mgr.getTaskInfo(paramInt);
      return localDownloadTaskInfo;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return null;
  }

  public int[] getTaskList(String paramString)
  {
    if (this._mgr == null)
      return null;
    try
    {
      int[] arrayOfInt = this._mgr.getTaskList(paramString);
      return arrayOfInt;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return null;
  }

  public int pauseTask(int paramInt)
  {
    if (this._mgr == null)
      return -1;
    try
    {
      int i = this._mgr.pauseTask(paramInt);
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return -1;
  }

  public int removeTask(int paramInt)
  {
    if (this._mgr == null)
      return -1;
    try
    {
      int i = this._mgr.removeTask(paramInt);
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return -1;
  }

  public int resumeTask(int paramInt)
  {
    if (this._mgr == null)
      return -1;
    try
    {
      int i = this._mgr.resumeTask(paramInt);
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return -1;
  }

  public int setEventListener(IDownloadEventListener paramIDownloadEventListener)
  {
    this.chainListener = paramIDownloadEventListener;
    return 0;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.service.helper.DownloadManager
 * JD-Core Version:    0.6.2
 */