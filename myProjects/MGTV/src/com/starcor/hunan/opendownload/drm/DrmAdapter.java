package com.starcor.hunan.opendownload.drm;

import com.starcor.core.domain.PlayInfoV2;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.utils.Logger;

public class DrmAdapter
{
  private static final String MARLIN = "marlin";
  private static final String NONE = "none";
  private static final String TAG = DrmAdapter.class.getSimpleName();

  public static String getDrmCapacity()
  {
    return "marlin";
  }

  public static StringParams getDrmTypeParams()
  {
    StringParams localStringParams = new StringParams("nns_drm_type");
    localStringParams.setValue(getDrmCapacity());
    return localStringParams;
  }

  public static void requestDrmUrl(PlayInfoV2 paramPlayInfoV2, DrmGetUrlCallback paramDrmGetUrlCallback)
  {
    try
    {
      int j = Integer.parseInt(paramPlayInfoV2.getUserValueByKey("drm_flag"));
      i = j;
      Logger.d(TAG, "drmFlag=" + i);
      if (i == 1)
      {
        String str = paramPlayInfoV2.getUserValueByKey("drm_encrypt_solution");
        Logger.d(TAG, "drmEncryptSolution=" + str);
        if ("marlin".equals(str))
        {
          MarlinDrmManager.getInstance().requestPlayUrlByDRM(paramPlayInfoV2, paramDrmGetUrlCallback);
          return;
        }
      }
    }
    catch (Exception localException)
    {
      while (true)
        int i = 0;
      paramDrmGetUrlCallback.noDrm();
      return;
    }
    paramDrmGetUrlCallback.noDrm();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.opendownload.drm.DrmAdapter
 * JD-Core Version:    0.6.2
 */