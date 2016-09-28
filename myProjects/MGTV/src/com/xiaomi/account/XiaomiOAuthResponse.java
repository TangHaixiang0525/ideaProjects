package com.xiaomi.account;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.util.Log;

public class XiaomiOAuthResponse
  implements Parcelable
{
  public static final Parcelable.Creator<XiaomiOAuthResponse> CREATOR = new Parcelable.Creator()
  {
    public XiaomiOAuthResponse createFromParcel(Parcel paramAnonymousParcel)
    {
      return new XiaomiOAuthResponse(paramAnonymousParcel);
    }

    public XiaomiOAuthResponse[] newArray(int paramAnonymousInt)
    {
      return new XiaomiOAuthResponse[paramAnonymousInt];
    }
  };
  private static final String TAG = XiaomiOAuthResponse.class.getName();
  private IXiaomiAuthResponse mResponse;

  public XiaomiOAuthResponse(Parcel paramParcel)
  {
    this.mResponse = IXiaomiAuthResponse.Stub.asInterface(paramParcel.readStrongBinder());
  }

  public XiaomiOAuthResponse(IXiaomiAuthResponse paramIXiaomiAuthResponse)
  {
    this.mResponse = paramIXiaomiAuthResponse;
  }

  public static void setIXiaomiAuthResponseCancel(IXiaomiAuthResponse paramIXiaomiAuthResponse)
  {
    if (paramIXiaomiAuthResponse == null)
      return;
    try
    {
      paramIXiaomiAuthResponse.onCancel();
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      Log.e(TAG, "RuntimeException", localRuntimeException);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      Log.e(TAG, "RemoteException", localRemoteException);
    }
  }

  public static void setIXiaomiAuthResponseResult(IXiaomiAuthResponse paramIXiaomiAuthResponse, Bundle paramBundle)
  {
    if ((paramIXiaomiAuthResponse == null) || (paramBundle == null))
      return;
    try
    {
      paramIXiaomiAuthResponse.onResult(paramBundle);
      return;
    }
    catch (RuntimeException localRuntimeException1)
    {
      Log.e(TAG, "RemoteException", localRuntimeException1);
      Bundle localBundle = new Bundle();
      localBundle.putInt("extra_error_code", -1);
      localBundle.putString("extra_error_description", localRuntimeException1.getMessage());
      try
      {
        paramIXiaomiAuthResponse.onResult(localBundle);
        return;
      }
      catch (RuntimeException localRuntimeException2)
      {
        Log.e(TAG, "RuntimeException", localRuntimeException2);
        return;
      }
      catch (RemoteException localRemoteException2)
      {
        Log.e(TAG, "RemoteException", localRemoteException2);
        return;
      }
    }
    catch (RemoteException localRemoteException1)
    {
      Log.e(TAG, "RemoteException", localRemoteException1);
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public void onCancel()
  {
    setIXiaomiAuthResponseCancel(this.mResponse);
  }

  public void onError(int paramInt, String paramString)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("extra_error_code", paramInt);
    localBundle.putString("extra_error_description", paramString);
    setIXiaomiAuthResponseResult(this.mResponse, localBundle);
  }

  public void onResult(Bundle paramBundle)
  {
    setIXiaomiAuthResponseResult(this.mResponse, paramBundle);
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeStrongBinder(this.mResponse.asBinder());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.xiaomi.account.XiaomiOAuthResponse
 * JD-Core Version:    0.6.2
 */