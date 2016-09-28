package com.starcor.service.helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.starcor.hunan.App;
import com.starcor.service.AppManagerService;
import com.starcor.service.ApplicationInfo;
import com.starcor.service.IAppEventListener;
import com.starcor.service.IAppEventListener.Stub;
import com.starcor.service.IAppManager;
import com.starcor.service.IAppManager.Stub;

public class AppManager
{
  public static final int ERROR_REMOTE_EXCEPTION = -3;
  public static final int ERROR_SERVICE_DISCONNECTED = -2;
  IAppManager _mgr;
  IAppEventListener listener = new IAppEventListener.Stub()
  {
    public int onEvent(String paramAnonymousString1, String paramAnonymousString2)
      throws RemoteException
    {
      return 0;
    }
  };
  ServiceConnection serviceConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      if (paramAnonymousIBinder == null)
        return;
      AppManager.this._mgr = IAppManager.Stub.asInterface(paramAnonymousIBinder);
      try
      {
        AppManager.this._mgr.setEventListener(AppManager.this.listener);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        localRemoteException.printStackTrace();
        AppManager.this._mgr = null;
      }
    }

    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      AppManager.this._mgr = null;
    }
  };

  public AppManager()
  {
    init(App.getAppContext());
  }

  private void init(Context paramContext)
  {
    paramContext.bindService(new Intent(paramContext, AppManagerService.class), this.serviceConnection, 1);
  }

  public void clear()
  {
    if (this.serviceConnection == null)
      return;
    this._mgr = null;
    App.getAppContext().unbindService(this.serviceConnection);
    this.serviceConnection = null;
  }

  public int forceStopApp(String paramString)
  {
    if (this._mgr == null)
      return -2;
    try
    {
      int i = this._mgr.forceStopApp(paramString);
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return -3;
  }

  public ApplicationInfo getAppInfo(String paramString)
  {
    if (this._mgr == null)
      return null;
    try
    {
      ApplicationInfo localApplicationInfo = this._mgr.getAppInfo(paramString);
      return localApplicationInfo;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return null;
  }

  public String[] getAppList()
  {
    if (this._mgr == null)
      return null;
    try
    {
      String[] arrayOfString = this._mgr.getAppList();
      return arrayOfString;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return null;
  }

  public int installApp(String paramString)
  {
    if (this._mgr == null)
      return -2;
    try
    {
      int i = this._mgr.installApp(paramString);
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return -3;
  }

  public int removeApp(String paramString)
  {
    if (this._mgr == null)
      return -2;
    try
    {
      int i = this._mgr.removeApp(paramString);
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return -3;
  }

  public int resetApp(String paramString)
  {
    if (this._mgr == null)
      return -2;
    try
    {
      int i = this._mgr.resetApp(paramString);
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return -3;
  }

  public int setEventListener(IAppEventListener paramIAppEventListener)
  {
    if (this._mgr == null)
      return -2;
    try
    {
      int i = this._mgr.setEventListener(paramIAppEventListener);
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return -3;
  }

  public int startApp(String paramString)
  {
    if (this._mgr == null)
      return -2;
    try
    {
      int i = this._mgr.startApp(paramString);
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return -3;
  }

  public int stopApp(String paramString)
  {
    if (this._mgr == null)
      return -2;
    try
    {
      int i = this._mgr.stopApp(paramString);
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return -3;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.service.helper.AppManager
 * JD-Core Version:    0.6.2
 */