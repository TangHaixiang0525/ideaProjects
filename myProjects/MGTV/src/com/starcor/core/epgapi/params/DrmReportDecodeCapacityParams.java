package com.starcor.core.epgapi.params;

import android.text.TextUtils;
import com.starcor.config.MgtvUrl;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.hunan.opendownload.drm.DrmAdapter;

public class DrmReportDecodeCapacityParams extends Api
{
  private StringParams nns_decode_capacity;

  public DrmReportDecodeCapacityParams()
  {
    if (TextUtils.isEmpty(AppInfo.URL_n41_a));
    for (String str = MgtvUrl.getN41SecretKeysUrl(); ; str = AppInfo.URL_n41_a)
    {
      this.prefix = str;
      this.nns_func.setValue("report_decode_capacity");
      this.nns_decode_capacity = new StringParams("nns_decode_capacity");
      this.nns_decode_capacity.setValue(DrmAdapter.getDrmCapacity());
      this.cacheValidTime = 0L;
      setResultFormat(0);
      return;
    }
  }

  public String getApiName()
  {
    return "N42_A_1";
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_decode_capacity + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.DrmReportDecodeCapacityParams
 * JD-Core Version:    0.6.2
 */