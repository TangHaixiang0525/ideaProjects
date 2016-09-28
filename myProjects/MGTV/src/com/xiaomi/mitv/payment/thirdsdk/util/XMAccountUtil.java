package com.xiaomi.mitv.payment.thirdsdk.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import java.io.IOException;

public class XMAccountUtil
{
  public static final String ACCOUNT_TYPE = "com.xiaomi";
  public static final int ERROR_CODE_AUTHENTICATION_ERROR = 105;
  public static final int ERROR_CODE_CANCELED = 104;
  public static final int ERROR_CODE_INVALID_ACCOUNT = 106;
  public static final int ERROR_CODE_NETWORK_ERROR = 101;
  private static final String TAG = "XMAccountUtil";

  public static String getUserId(Context paramContext)
  {
    Account[] arrayOfAccount = AccountManager.get(paramContext).getAccountsByType("com.xiaomi");
    if ((arrayOfAccount != null) && (arrayOfAccount.length > 0))
      return arrayOfAccount[0].name;
    return null;
  }

  public static boolean hasAccount(Context paramContext)
  {
    Account[] arrayOfAccount = AccountManager.get(paramContext).getAccountsByType("com.xiaomi");
    return (arrayOfAccount != null) && (arrayOfAccount.length > 0);
  }

  public static void login(Activity paramActivity, LoginCallback paramLoginCallback)
  {
    Log.d("XMAccountUtil", "login called ");
    AccountCallback local1 = new AccountCallback(paramLoginCallback)
    {
      public void onFailed(int paramAnonymousInt, String paramAnonymousString)
      {
        if (XMAccountUtil.this != null)
          XMAccountUtil.this.onFailed(paramAnonymousInt, paramAnonymousString);
      }

      public void onSuccess()
      {
        if (XMAccountUtil.this != null)
          XMAccountUtil.this.onSuccess();
      }
    };
    AccountManager.get(paramActivity).addAccount("com.xiaomi", null, null, new Bundle(), paramActivity, local1, null);
  }

  private static abstract class AccountCallback
    implements AccountManagerCallback<Bundle>, XMAccountUtil.LoginCallback
  {
    public void run(AccountManagerFuture<Bundle> paramAccountManagerFuture)
    {
      if (paramAccountManagerFuture.isDone())
      {
        try
        {
          Bundle localBundle = (Bundle)paramAccountManagerFuture.getResult();
          if (localBundle == null)
          {
            Log.e("XMAccountUtil", "login failed : authentication failed");
            onFailed(105, "");
            return;
          }
        }
        catch (OperationCanceledException localOperationCanceledException)
        {
          Log.e("XMAccountUtil", "login failed : user canceled " + localOperationCanceledException);
          onFailed(104, "User canceled.");
          return;
        }
        catch (AuthenticatorException localAuthenticatorException)
        {
          Log.e("XMAccountUtil", "login failed : authenticator exception " + localAuthenticatorException);
          onFailed(105, "Authentication failure.");
          return;
        }
        catch (IOException localIOException)
        {
          Log.e("XMAccountUtil", "login failed : io exception " + localIOException);
          onFailed(101, "IO exception");
          return;
        }
        onSuccess();
        return;
      }
      Log.i("XMAccountUtil", "future.isDone is false");
      onFailed(104, "canceled.");
    }
  }

  public static abstract interface LoginCallback
  {
    public abstract void onFailed(int paramInt, String paramString);

    public abstract void onSuccess();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.xiaomi.mitv.payment.thirdsdk.util.XMAccountUtil
 * JD-Core Version:    0.6.2
 */