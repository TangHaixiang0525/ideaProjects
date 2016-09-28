package com.starcor.hunan.opendownload.drm;

import android.util.Log;
import com.intertrust.wasabi.ErrorCodeException;
import com.intertrust.wasabi.drm.TransactionListener;
import com.intertrust.wasabi.drm.TransactionType;
import com.intertrust.wasabi.licensestore.LicenseStore;

public class MarlinTransactionListener
  implements TransactionListener
{
  static final String TAG = MarlinTransactionListener.class.getSimpleName();

  public void onLicenseDataReceived(byte[] paramArrayOfByte)
  {
    try
    {
      LicenseStore localLicenseStore = new LicenseStore();
      localLicenseStore.addLicense(new String(paramArrayOfByte), "");
      localLicenseStore.close();
      return;
    }
    catch (ErrorCodeException localErrorCodeException)
    {
      Log.e(TAG, "Failed to create License store: " + localErrorCodeException.getLocalizedMessage());
    }
  }

  public void onTransactionBegin(TransactionType paramTransactionType)
  {
    Log.i(TAG, "BEGIN transaction of type " + paramTransactionType);
  }

  public void onTransactionEnd(TransactionType paramTransactionType, int paramInt, String paramString1, String paramString2)
  {
    Log.i(TAG, "END transaction of type " + paramTransactionType + "\tresult code" + paramInt + "\tresult string " + paramString1);
  }

  public void onTransactionProgress(TransactionType paramTransactionType, int paramInt1, int paramInt2)
  {
    Log.i(TAG, "PROGRESS (notification of) transaction of type " + paramTransactionType);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.opendownload.drm.MarlinTransactionListener
 * JD-Core Version:    0.6.2
 */