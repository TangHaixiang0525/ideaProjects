package com.xiaomi.mitv.payment.thirdsdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.xiaomi.mitv.payment.thirdsdk.aidl.IMiTVPaymentThirdManagerService;
import com.xiaomi.mitv.payment.thirdsdk.aidl.IMiTVPaymentThirdManagerService.Stub;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class MiTVPaymentThirdManager
{
  private static final Map<String, PayToken> mPayTokenCache = new HashMap();
  private _MiTVPaymentThirdManager payManager;

  public MiTVPaymentThirdManager(Context paramContext)
  {
    this.payManager = new _MiTVPaymentThirdManager(paramContext);
  }

  public String CreateOrderAndPay(String paramString1, String paramString2, long paramLong1, String paramString3, String paramString4, long paramLong2, String paramString5, String paramString6, CallBackListener paramCallBackListener)
    throws RemoteException
  {
    return this.payManager.CreateOrderAndPay(paramString1, paramString2, paramLong1, paramString3, paramString4, paramLong2, paramString5, paramString6, paramCallBackListener);
  }

  public static abstract interface CallBackListener
  {
    public abstract void HandleResult(String paramString);
  }

  class PayToken
  {
    private String mPaymentToken;
    private long mValidTime;

    public PayToken(String paramLong, long arg3)
    {
      this.mPaymentToken = paramLong;
      Object localObject;
      this.mValidTime = localObject;
    }

    public String getPaymentToken()
    {
      return this.mPaymentToken;
    }

    public long getValidTime()
    {
      return this.mValidTime;
    }
  }

  private class _MiTVPaymentThirdManager
    implements ServiceConnection
  {
    private static final String TAG = "_MiTVPaymentThirdManager";
    private String mAppId;
    private MiTVPaymentThirdManager.CallBackListener mCallBack;
    private Context mContext;
    private String mCustomerOrderId;
    private String mExtraData;
    private String mMacKey;
    private String mOAuthToken;
    private String mOrderDesc;
    private String mPayToken;
    private long mPrice;
    private String mProductName;
    private IMiTVPaymentThirdManagerService mService;

    public _MiTVPaymentThirdManager(Context arg2)
    {
      Object localObject;
      this.mContext = localObject;
    }

    public String CreateOrderAndPay(String paramString1, String paramString2, long paramLong1, String paramString3, String paramString4, long paramLong2, String paramString5, String paramString6, MiTVPaymentThirdManager.CallBackListener paramCallBackListener)
      throws RemoteException
    {
      Log.e("_MiTVPaymentThirdManager", "CreateOrderAndPay enter");
      this.mOAuthToken = paramString3;
      this.mMacKey = paramString4;
      this.mAppId = paramLong2;
      this.mCustomerOrderId = paramString1;
      this.mProductName = paramString2;
      this.mPrice = paramLong1;
      this.mOrderDesc = paramString5;
      this.mExtraData = paramString6;
      this.mCallBack = paramCallBackListener;
      bind();
      Log.e("_MiTVPaymentThirdManager", "CreateOrderAndPay end");
      return null;
    }

    protected void bind()
    {
      Log.i("_MiTVPaymentThirdManager", "bind Service begin");
      Intent localIntent = new Intent("com.xiaomi.xmsf.action.PAYMENT_THIRDPARTY");
      this.mContext.bindService(localIntent, this, 1);
      Log.i("_MiTVPaymentThirdManager", "bind service end");
    }

    protected IMiTVPaymentThirdManagerService getService()
    {
      return this.mService;
    }

    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      Log.i("_MiTVPaymentThirdManager", "ThirdParty onServiceConnected");
      this.mService = IMiTVPaymentThirdManagerService.Stub.asInterface(paramIBinder);
      try
      {
        this.mPayToken = "";
        if ((MiTVPaymentThirdManager.mPayTokenCache != null) && (MiTVPaymentThirdManager.mPayTokenCache.size() > 0) && (MiTVPaymentThirdManager.mPayTokenCache.containsKey(this.mOAuthToken)))
        {
          MiTVPaymentThirdManager.PayToken localPayToken2 = (MiTVPaymentThirdManager.PayToken)MiTVPaymentThirdManager.mPayTokenCache.get(this.mOAuthToken);
          long l2 = localPayToken2.getValidTime();
          if (System.currentTimeMillis() >= l2)
            break label315;
          this.mPayToken = localPayToken2.getPaymentToken();
        }
        while (true)
        {
          String str1 = getService().CreateOrderAndPay(this.mCustomerOrderId, this.mProductName, this.mPrice, this.mOAuthToken, this.mMacKey, this.mAppId, this.mPayToken, this.mOrderDesc, this.mExtraData);
          Log.i("_MiTVPaymentThirdManager", "result: " + str1);
          if ((MiTVPaymentThirdManager.mPayTokenCache == null) || (MiTVPaymentThirdManager.mPayTokenCache.size() <= 0) || (!MiTVPaymentThirdManager.mPayTokenCache.containsKey(this.mOAuthToken)))
          {
            JSONObject localJSONObject1 = new JSONObject(str1);
            if (localJSONObject1.has("accessToken"))
            {
              JSONObject localJSONObject2 = localJSONObject1.getJSONObject("accessToken");
              if ((localJSONObject2 != null) && (localJSONObject2.length() > 0))
              {
                Log.i("_MiTVPaymentThirdManager", "new Token Cache");
                String str2 = localJSONObject2.getString("payAccessToken");
                long l1 = localJSONObject2.getLong("expireDate");
                MiTVPaymentThirdManager.PayToken localPayToken1 = new MiTVPaymentThirdManager.PayToken(MiTVPaymentThirdManager.this, str2, l1);
                MiTVPaymentThirdManager.mPayTokenCache.put(this.mOAuthToken, localPayToken1);
              }
            }
          }
          this.mCallBack.HandleResult(str1);
          unBind();
          return;
          label315: MiTVPaymentThirdManager.mPayTokenCache.remove(this.mOAuthToken);
        }
      }
      catch (RemoteException localRemoteException)
      {
        localRemoteException.printStackTrace();
        unBind();
        return;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        unBind();
      }
    }

    public void onServiceDisconnected(ComponentName paramComponentName)
    {
      this.mService = null;
    }

    protected void unBind()
    {
      this.mContext.unbindService(this);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.xiaomi.mitv.payment.thirdsdk.MiTVPaymentThirdManager
 * JD-Core Version:    0.6.2
 */