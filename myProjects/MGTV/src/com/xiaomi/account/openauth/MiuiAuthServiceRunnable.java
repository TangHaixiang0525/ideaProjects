package com.xiaomi.account.openauth;

import android.accounts.Account;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

abstract class MiuiAuthServiceRunnable<V> extends XiaomiOAuthRunnable<V>
  implements ServiceConnection
{
  private static final String ACTION_FOR_AUTH_SERVICE = "android.intent.action.XIAOMI_ACCOUNT_AUTHORIZE";
  private static final String PACKAGE_NAME_FOR_AUTH_SERVICE = "com.xiaomi.account";
  protected final Account account;
  private final Context context;
  protected final Bundle options;

  MiuiAuthServiceRunnable(Context paramContext, Account paramAccount, Bundle paramBundle)
  {
    this.context = paramContext;
    this.account = paramAccount;
    this.options = paramBundle;
  }

  private static Intent getAuthServiceIntent()
  {
    Intent localIntent = new Intent("android.intent.action.XIAOMI_ACCOUNT_AUTHORIZE");
    if (Build.VERSION.SDK_INT >= 21)
      localIntent.setPackage("com.xiaomi.account");
    return localIntent;
  }

  public final void doRun()
  {
    Intent localIntent = getAuthServiceIntent();
    if (!this.context.bindService(localIntent, this, 1))
    {
      this.context.unbindService(this);
      this.mFuture.setException(new NoMiuiAuthServiceException());
    }
  }

  public final void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
  {
    try
    {
      Object localObject3 = talkWithMiuiV6(com.xiaomi.account.IXiaomiAuthService.Stub.asInterface(paramIBinder));
      this.mFuture.set(localObject3);
      return;
    }
    catch (SecurityException localSecurityException1)
    {
      try
      {
        Object localObject2 = talkWithMiuiV5(miui.net.IXiaomiAuthService.Stub.asInterface(paramIBinder));
        this.mFuture.set(localObject2);
        return;
      }
      catch (SecurityException localSecurityException2)
      {
        this.mFuture.setException(new NoMiuiAuthServiceException());
        return;
      }
    }
    catch (RemoteException localRemoteException)
    {
      this.mFuture.setException(localRemoteException);
      return;
    }
    finally
    {
      this.context.unbindService(this);
    }
  }

  public final void onServiceDisconnected(ComponentName paramComponentName)
  {
  }

  protected abstract V talkWithMiuiV5(miui.net.IXiaomiAuthService paramIXiaomiAuthService)
    throws RemoteException;

  protected abstract V talkWithMiuiV6(com.xiaomi.account.IXiaomiAuthService paramIXiaomiAuthService)
    throws RemoteException;

  static class NoMiuiAuthServiceException extends Exception
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.xiaomi.account.openauth.MiuiAuthServiceRunnable
 * JD-Core Version:    0.6.2
 */