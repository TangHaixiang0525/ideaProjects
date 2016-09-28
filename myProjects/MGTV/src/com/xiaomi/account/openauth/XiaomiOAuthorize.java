package com.xiaomi.account.openauth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.account.IXiaomiAuthResponse;
import com.xiaomi.account.IXiaomiAuthResponse.Stub;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class XiaomiOAuthorize
{
  private static final String ACCOUNT_TYPE = "com.xiaomi";
  private static final String TAG = "XiaomiOAuthorize";
  private static final String TYPE_CODE = "code";
  private static final String TYPE_TOKEN = "token";
  private Long mAppId = null;
  private boolean mNotUseMiui = false;
  private String mRedirectUrl = null;
  private int[] mScopes = null;
  private Boolean mSkipConfirm = null;
  private String mState = null;

  private static Bundle getAccessTokenFromMiui(Context paramContext, Account paramAccount, Bundle paramBundle)
    throws ExecutionException, InterruptedException
  {
    return (Bundle)new MiuiAuthServiceRunnable(paramContext, paramAccount, paramBundle)
    {
      protected Bundle talkWithMiuiV5(miui.net.IXiaomiAuthService paramAnonymousIXiaomiAuthService)
        throws RemoteException
      {
        paramAnonymousIXiaomiAuthService.invalidateAccessToken(this.account, this.options);
        return paramAnonymousIXiaomiAuthService.getMiCloudAccessToken(this.account, this.options);
      }

      protected Bundle talkWithMiuiV6(com.xiaomi.account.IXiaomiAuthService paramAnonymousIXiaomiAuthService)
        throws RemoteException
      {
        return paramAnonymousIXiaomiAuthService.getMiCloudAccessToken(this.account, this.options);
      }
    }
    .start().get();
  }

  private static Bundle getAccessTokenFromMiuiInResponseWay(Context paramContext, Bundle paramBundle, final IXiaomiAuthResponse paramIXiaomiAuthResponse)
    throws ExecutionException, InterruptedException
  {
    return (Bundle)new MiuiAuthServiceRunnable(paramContext, null, paramBundle)
    {
      protected Bundle talkWithMiuiV5(miui.net.IXiaomiAuthService paramAnonymousIXiaomiAuthService)
        throws RemoteException
      {
        throw new IllegalStateException("should not be here");
      }

      protected Bundle talkWithMiuiV6(com.xiaomi.account.IXiaomiAuthService paramAnonymousIXiaomiAuthService)
        throws RemoteException
      {
        paramAnonymousIXiaomiAuthService.getAccessTokenInResponse(paramIXiaomiAuthResponse, this.options, 1, 0);
        return null;
      }
    }
    .start().get();
  }

  private Account getXiaomiAccount(Context paramContext)
  {
    Account[] arrayOfAccount = AccountManager.get(paramContext).getAccountsByType("com.xiaomi");
    if (arrayOfAccount.length == 0)
      return null;
    return arrayOfAccount[0];
  }

  private static boolean isMiui(Context paramContext)
  {
    try
    {
      boolean bool = ((Boolean)new MiuiAuthServiceRunnable(paramContext, null, null)
      {
        protected Boolean talkWithMiuiV5(miui.net.IXiaomiAuthService paramAnonymousIXiaomiAuthService)
          throws RemoteException
        {
          return Boolean.valueOf(true);
        }

        protected Boolean talkWithMiuiV6(com.xiaomi.account.IXiaomiAuthService paramAnonymousIXiaomiAuthService)
          throws RemoteException
        {
          return Boolean.valueOf(true);
        }
      }
      .start().get()).booleanValue();
      return bool;
    }
    catch (InterruptedException localInterruptedException)
    {
      return false;
    }
    catch (ExecutionException localExecutionException)
    {
    }
    return false;
  }

  private static String makeScopeString(int[] paramArrayOfInt)
  {
    if ((paramArrayOfInt != null) && (paramArrayOfInt.length > 0))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      int i = paramArrayOfInt.length;
      int j = 0;
      int n;
      for (int k = 0; j < i; k = n)
      {
        int m = paramArrayOfInt[j];
        n = k + 1;
        if (k > 0)
          localStringBuilder.append(' ');
        localStringBuilder.append(m);
        j++;
      }
      return localStringBuilder.toString();
    }
    return null;
  }

  private XiaomiOAuthFutureImpl<XiaomiOAuthResults> oauth(final Activity paramActivity, final String paramString)
  {
    if ((paramActivity == null) || (paramActivity.isFinishing()))
      throw new IllegalArgumentException("activity should not be null and should be running");
    if ((this.mAppId == null) || (this.mAppId.longValue() <= 0L))
      throw new IllegalArgumentException("client id is error!!!");
    if (TextUtils.isEmpty(this.mRedirectUrl))
      throw new IllegalArgumentException("redirect url is empty!!!");
    if (TextUtils.isEmpty(paramString))
      throw new IllegalArgumentException("responseType is empty!!!");
    return new XiaomiOAuthRunnable()
    {
      private Bundle makeOptions()
      {
        Bundle localBundle = new Bundle();
        localBundle.putString("extra_client_id", String.valueOf(XiaomiOAuthorize.this.mAppId));
        localBundle.putString("extra_redirect_uri", XiaomiOAuthorize.this.mRedirectUrl);
        localBundle.putString("extra_response_type", paramString);
        if (XiaomiOAuthorize.this.mSkipConfirm != null)
          localBundle.putBoolean("extra_skip_confirm", XiaomiOAuthorize.this.mSkipConfirm.booleanValue());
        if (!TextUtils.isEmpty(XiaomiOAuthorize.this.mState))
          localBundle.putString("extra_state", XiaomiOAuthorize.this.mState);
        String str = XiaomiOAuthorize.makeScopeString(XiaomiOAuthorize.this.mScopes);
        if (!TextUtils.isEmpty(str))
          localBundle.putString("extra_scope", str);
        return localBundle;
      }

      private void run(XiaomiOAuthorize.XiaomiTokenFuture paramAnonymousXiaomiTokenFuture)
        throws ExecutionException, InterruptedException, IOException, OperationCanceledException
      {
        XiaomiOAuthorize.OAuthStage localOAuthStage = XiaomiOAuthorize.OAuthStage.INIT;
        while (true)
          switch (XiaomiOAuthorize.8.$SwitchMap$com$xiaomi$account$openauth$XiaomiOAuthorize$OAuthStage[localOAuthStage.ordinal()])
          {
          default:
            break;
          case 1:
            if ((XiaomiOAuthorize.this.mNotUseMiui) || (!XiaomiOAuthorize.isMiui(paramActivity)))
              localOAuthStage = XiaomiOAuthorize.OAuthStage.OAUTH_FROM_3RD_PARTY;
            else if (XiaomiOAuthorize.this.supportResponseWayWithMiui(paramActivity))
              localOAuthStage = XiaomiOAuthorize.OAuthStage.OAUTH_FROM_MIUI_WITH_RESPONSE;
            else if (XiaomiOAuthorize.this.getXiaomiAccount(paramActivity) != null)
              localOAuthStage = XiaomiOAuthorize.OAuthStage.OAUTH_FROM_MIUI;
            else
              localOAuthStage = XiaomiOAuthorize.OAuthStage.ADD_SYSTEM_ACCOUNT;
            break;
          case 2:
            try
            {
              Bundle localBundle = (Bundle)AccountManager.get(paramActivity).addAccount("com.xiaomi", null, null, null, paramActivity, null, null).getResult();
              if ((localBundle != null) && (localBundle.containsKey("authAccount")))
              {
                localOAuthStage = XiaomiOAuthorize.OAuthStage.OAUTH_FROM_MIUI;
              }
              else
              {
                paramAnonymousXiaomiTokenFuture.setException(new Exception("fail to add account"));
                return;
              }
            }
            catch (SecurityException localSecurityException)
            {
              localOAuthStage = XiaomiOAuthorize.OAuthStage.OAUTH_FROM_3RD_PARTY;
            }
            catch (AuthenticatorException localAuthenticatorException)
            {
              localOAuthStage = XiaomiOAuthorize.OAuthStage.OAUTH_FROM_3RD_PARTY;
            }
          case 3:
          case 4:
          case 5:
          }
        paramAnonymousXiaomiTokenFuture.set(XiaomiOAuthorize.getAccessTokenFromMiui(paramActivity, XiaomiOAuthorize.access$300(XiaomiOAuthorize.this, paramActivity), makeOptions()));
        return;
        XiaomiOAuthorize.getAccessTokenFromMiuiInResponseWay(paramActivity, makeOptions(), XiaomiOAuthorize.XiaomiTokenFuture.access$500(paramAnonymousXiaomiTokenFuture));
        return;
        paramAnonymousXiaomiTokenFuture.handleIntentResult(AuthorizeActivity.getIntent(paramActivity, String.valueOf(XiaomiOAuthorize.this.mAppId), XiaomiOAuthorize.this.mRedirectUrl, paramString, XiaomiOAuthorize.makeScopeString(XiaomiOAuthorize.this.mScopes), XiaomiOAuthorize.this.mState, XiaomiOAuthorize.this.mSkipConfirm, XiaomiOAuthorize.XiaomiTokenFuture.access$500(paramAnonymousXiaomiTokenFuture)));
      }

      public void doRun()
      {
        XiaomiOAuthorize.XiaomiTokenFuture localXiaomiTokenFuture = new XiaomiOAuthorize.XiaomiTokenFuture(paramActivity, this.mFuture);
        try
        {
          run(localXiaomiTokenFuture);
          return;
        }
        catch (ExecutionException localExecutionException)
        {
          this.mFuture.setException(localExecutionException.getCause());
          return;
        }
        catch (InterruptedException localInterruptedException)
        {
          this.mFuture.setException(localInterruptedException);
          return;
        }
        catch (OperationCanceledException localOperationCanceledException)
        {
          this.mFuture.setException(localOperationCanceledException);
          return;
        }
        catch (IOException localIOException)
        {
          this.mFuture.setException(localIOException);
        }
      }
    }
    .start();
  }

  @Deprecated
  private static int[] scopeStringToIntArray(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    String[] arrayOfString;
    int j;
    int k;
    if (paramString != null)
    {
      arrayOfString = paramString.split(" ");
      j = arrayOfString.length;
      k = 0;
    }
    while (true)
    {
      String str;
      if (k < j)
        str = arrayOfString[k];
      try
      {
        localArrayList.add(Integer.valueOf(str));
        label52: k++;
        continue;
        int[] arrayOfInt = new int[localArrayList.size()];
        for (int i = 0; i < arrayOfInt.length; i++)
          arrayOfInt[i] = ((Integer)localArrayList.get(i)).intValue();
        return arrayOfInt;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        break label52;
      }
    }
  }

  @Deprecated
  public static void startGetAccessToken(Activity paramActivity, long paramLong, String paramString, Bundle paramBundle, int paramInt)
  {
    Log.w("XiaomiOAuthorize", "you are calling startGetAccessToken(). Is still works but it is deprecated. Instead please use \n                XiaomiOAuthFuture<XiaomiOAuthResults> future = new XiaomiOAuthorize()\n                        .setAppId(appId)\n                        .setRedirectUrl(redirectUri)\n                        .setScope(scope)\n                        .setAllowSwitchAccount(true)\n                        .startGetAccessToken(acitivity);\n                XiaomiOAuthResults results = future.getResult();//call on background thread.\nIt provides better user experience! Checkout the Demo codes!");
    startGetOAuthorize(paramActivity, paramLong, paramString, "token", paramBundle, paramInt);
  }

  @Deprecated
  public static void startGetOAuthCode(Activity paramActivity, long paramLong, String paramString, Bundle paramBundle, int paramInt)
  {
    Log.w("XiaomiOAuthorize", "you are calling startGetOAuthCode(). Is still works but it is deprecated. Instead please use \n                XiaomiOAuthFuture<XiaomiOAuthResults> future = new XiaomiOAuthorize()\n                        .setAppId(appId)\n                        .setRedirectUrl(redirectUri)\n                        .setScope(scope)\n                        .setAllowSwitchAccount(true)\n                        .startGetOAuthCode(acitivity);\n                XiaomiOAuthResults results = future.getResult();//call on background thread.\nIt provides better user experience! Checkout the Demo codes!");
    startGetOAuthorize(paramActivity, paramLong, paramString, "code", paramBundle, paramInt);
  }

  @Deprecated
  private static void startGetOAuthorize(final Activity paramActivity, long paramLong, String paramString1, final String paramString2, Bundle paramBundle, final int paramInt)
  {
    if (paramBundle == null)
      paramBundle = new Bundle();
    int[] arrayOfInt = scopeStringToIntArray(paramBundle.getString("extra_scope"));
    String str = paramBundle.getString("extra_state");
    XiaomiOAuthorize localXiaomiOAuthorize = new XiaomiOAuthorize().setAppId(paramLong).setRedirectUrl(paramString1).setScope(arrayOfInt).setState(str);
    if (paramBundle.containsKey("extra_skip_confirm"))
      localXiaomiOAuthorize.setSkipConfirm(paramBundle.getBoolean("extra_skip_confirm"));
    if ("code".equalsIgnoreCase(paramString2));
    for (XiaomiOAuthFuture localXiaomiOAuthFuture = localXiaomiOAuthorize.startGetOAuthCode(paramActivity); ; localXiaomiOAuthFuture = localXiaomiOAuthorize.startGetAccessToken(paramActivity))
    {
      new AsyncTask()
      {
        Exception e;

        protected XiaomiOAuthResults doInBackground(Void[] paramAnonymousArrayOfVoid)
        {
          try
          {
            XiaomiOAuthResults localXiaomiOAuthResults = (XiaomiOAuthResults)this.val$y.getResult();
            return localXiaomiOAuthResults;
          }
          catch (OperationCanceledException localOperationCanceledException)
          {
            localOperationCanceledException.printStackTrace();
            return null;
          }
          catch (IOException localIOException)
          {
            while (true)
              this.e = localIOException;
          }
          catch (XMAuthericationException localXMAuthericationException)
          {
            while (true)
              this.e = localXMAuthericationException;
          }
        }

        protected void onPostExecute(XiaomiOAuthResults paramAnonymousXiaomiOAuthResults)
        {
          Bundle localBundle = new Bundle();
          int i;
          if (paramAnonymousXiaomiOAuthResults == null)
            if (this.e == null)
            {
              i = AuthorizeActivity.RESULT_CANCEL;
              localBundle.putInt("error", AuthorizeActivity.RESULT_CANCEL);
              localBundle.putString("error_description", "canceled");
            }
          while (true)
          {
            paramActivity.startActivityForResult(AuthorizeActivity.asMiddleActivity(paramActivity, i, localBundle), paramInt);
            return;
            i = AuthorizeActivity.RESULT_FAIL;
            localBundle.putInt("error", AuthorizeActivity.RESULT_FAIL);
            localBundle.putString("error_description", this.e.getMessage());
            continue;
            if (paramAnonymousXiaomiOAuthResults.errorResult != null)
            {
              i = AuthorizeActivity.RESULT_FAIL;
              localBundle.putInt("error", paramAnonymousXiaomiOAuthResults.errorResult.errorCode);
              localBundle.putString("error_description", paramAnonymousXiaomiOAuthResults.errorResult.errorMessage);
            }
            else
            {
              i = AuthorizeActivity.RESULT_SUCCESS;
              if ("code".equalsIgnoreCase(paramString2))
              {
                localBundle.putString("code", paramAnonymousXiaomiOAuthResults.successResult.code);
                localBundle.putString("state", paramAnonymousXiaomiOAuthResults.successResult.state);
                localBundle.putString("token_type", paramAnonymousXiaomiOAuthResults.successResult.tokenType);
                localBundle.putString("mac_key", paramAnonymousXiaomiOAuthResults.successResult.macKey);
                localBundle.putString("mac_algorithm", paramAnonymousXiaomiOAuthResults.successResult.macAlgorithm);
              }
              else
              {
                localBundle.putString("access_token", paramAnonymousXiaomiOAuthResults.successResult.accessToken);
                localBundle.putString("expires_in", paramAnonymousXiaomiOAuthResults.successResult.expiresIn);
                localBundle.putString("scope", paramAnonymousXiaomiOAuthResults.successResult.scopes);
                localBundle.putString("state", paramAnonymousXiaomiOAuthResults.successResult.state);
                localBundle.putString("token_type", paramAnonymousXiaomiOAuthResults.successResult.tokenType);
                localBundle.putString("mac_key", paramAnonymousXiaomiOAuthResults.successResult.macKey);
                localBundle.putString("mac_algorithm", paramAnonymousXiaomiOAuthResults.successResult.macAlgorithm);
              }
            }
          }
        }
      }
      .execute(new Void[0]);
      return;
    }
  }

  private boolean supportResponseWayWithMiui(Context paramContext)
  {
    try
    {
      boolean bool = ((Boolean)new MiuiAuthServiceRunnable(paramContext, null, null)
      {
        protected Boolean talkWithMiuiV5(miui.net.IXiaomiAuthService paramAnonymousIXiaomiAuthService)
          throws RemoteException
        {
          return Boolean.valueOf(false);
        }

        protected Boolean talkWithMiuiV6(com.xiaomi.account.IXiaomiAuthService paramAnonymousIXiaomiAuthService)
          throws RemoteException
        {
          return Boolean.valueOf(paramAnonymousIXiaomiAuthService.supportResponseWay());
        }
      }
      .start().get()).booleanValue();
      return bool;
    }
    catch (InterruptedException localInterruptedException)
    {
      return false;
    }
    catch (ExecutionException localExecutionException)
    {
    }
    return false;
  }

  public XiaomiOAuthFuture<String> callOpenApi(final Context paramContext, final long paramLong, final String paramString1, String paramString2, final String paramString3, final String paramString4)
  {
    return new XiaomiOAuthRunnable()
    {
      public void doRun()
      {
        try
        {
          String str = AuthorizeApi.doHttpGet(paramContext, paramString1, paramLong, paramString3, paramString4, this.val$macAlgorithm);
          this.mFuture.set(str);
          return;
        }
        catch (XMAuthericationException localXMAuthericationException)
        {
          this.mFuture.setException(localXMAuthericationException);
        }
      }
    }
    .start();
  }

  public XiaomiOAuthorize setAppId(long paramLong)
  {
    this.mAppId = Long.valueOf(paramLong);
    return this;
  }

  public XiaomiOAuthorize setNoMiui(boolean paramBoolean)
  {
    this.mNotUseMiui = paramBoolean;
    return this;
  }

  public XiaomiOAuthorize setRedirectUrl(String paramString)
  {
    this.mRedirectUrl = paramString;
    return this;
  }

  public XiaomiOAuthorize setScope(int[] paramArrayOfInt)
  {
    this.mScopes = paramArrayOfInt;
    return this;
  }

  public XiaomiOAuthorize setSkipConfirm(boolean paramBoolean)
  {
    this.mSkipConfirm = Boolean.valueOf(paramBoolean);
    return this;
  }

  public XiaomiOAuthorize setState(String paramString)
  {
    this.mState = paramString;
    return this;
  }

  public XiaomiOAuthFuture<XiaomiOAuthResults> startGetAccessToken(Activity paramActivity)
  {
    return oauth(paramActivity, "token");
  }

  public XiaomiOAuthFuture<XiaomiOAuthResults> startGetOAuthCode(Activity paramActivity)
  {
    return oauth(paramActivity, "code");
  }

  private static enum OAuthStage
  {
    static
    {
      ADD_SYSTEM_ACCOUNT = new OAuthStage("ADD_SYSTEM_ACCOUNT", 1);
      OAUTH_FROM_MIUI = new OAuthStage("OAUTH_FROM_MIUI", 2);
      OAUTH_FROM_MIUI_WITH_RESPONSE = new OAuthStage("OAUTH_FROM_MIUI_WITH_RESPONSE", 3);
      OAUTH_FROM_3RD_PARTY = new OAuthStage("OAUTH_FROM_3RD_PARTY", 4);
      OAuthStage[] arrayOfOAuthStage = new OAuthStage[5];
      arrayOfOAuthStage[0] = INIT;
      arrayOfOAuthStage[1] = ADD_SYSTEM_ACCOUNT;
      arrayOfOAuthStage[2] = OAUTH_FROM_MIUI;
      arrayOfOAuthStage[3] = OAUTH_FROM_MIUI_WITH_RESPONSE;
      arrayOfOAuthStage[4] = OAUTH_FROM_3RD_PARTY;
    }
  }

  private static class XiaomiTokenFuture extends FutureTask<Bundle>
  {
    private final Activity mActivity;
    private final XiaomiOAuthFutureImpl<XiaomiOAuthResults> mRealFuture;

    public XiaomiTokenFuture(Activity paramActivity, XiaomiOAuthFutureImpl<XiaomiOAuthResults> paramXiaomiOAuthFutureImpl)
    {
      super();
      this.mActivity = paramActivity;
      this.mRealFuture = paramXiaomiOAuthFutureImpl;
    }

    private IXiaomiAuthResponse wrapWithinResponse()
    {
      return new IXiaomiAuthResponse.Stub()
      {
        public void onCancel()
          throws RemoteException
        {
          XiaomiOAuthorize.XiaomiTokenFuture.this.setException(new OperationCanceledException());
        }

        public void onResult(Bundle paramAnonymousBundle)
          throws RemoteException
        {
          XiaomiOAuthorize.XiaomiTokenFuture.this.set(paramAnonymousBundle);
        }
      };
    }

    public Bundle get()
    {
      throw new IllegalStateException("this should never be called");
    }

    public Bundle get(long paramLong, TimeUnit paramTimeUnit)
    {
      throw new IllegalStateException("this should never be called");
    }

    public boolean handleIntentResult(Intent paramIntent)
    {
      if (paramIntent == null)
        return false;
      Bundle localBundle = paramIntent.getExtras();
      localBundle.setClassLoader(getClass().getClassLoader());
      if (!localBundle.containsKey("extra_response"))
        paramIntent = AuthorizeActivity.asMiddleActivity(this.mActivity, paramIntent, wrapWithinResponse());
      this.mActivity.startActivity(paramIntent);
      return true;
    }

    public void set(Bundle paramBundle)
    {
      if ((paramBundle != null) && (paramBundle.containsKey("extra_intent")))
      {
        handleIntentResult((Intent)paramBundle.getParcelable("extra_intent"));
        return;
      }
      XiaomiOAuthResults localXiaomiOAuthResults = XiaomiOAuthResults.parseBundle(paramBundle);
      this.mRealFuture.set(localXiaomiOAuthResults);
    }

    protected void setException(Throwable paramThrowable)
    {
      this.mRealFuture.setException(paramThrowable);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.xiaomi.account.openauth.XiaomiOAuthorize
 * JD-Core Version:    0.6.2
 */