package com.starcor.core.epgapi.params;

import android.text.TextUtils;
import com.starcor.config.MgtvUrl;
import com.starcor.config.MgtvUrl.ServerPlatform;
import com.starcor.core.epgapi.StringParams;

public class UserFeedbackParams
{
  public static String urlUserFeedbackReportUrl;
  private StringParams nns_entry_number;
  private StringParams nns_mac;
  private StringParams nns_net_ip = new StringParams("nns_net_ip");
  private StringParams nns_user_feedback_id;
  private String prefix = "http://i5.hunantv.com/s1/2014/hfimg/2016/web/support/";

  public UserFeedbackParams(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.nns_net_ip.setValue(paramString1);
    this.nns_mac = new StringParams("nns_mac");
    this.nns_mac.setValue(paramString3);
    this.nns_user_feedback_id = new StringParams("nns_user_feedback_id");
    this.nns_user_feedback_id.setValue(paramString2);
    this.nns_entry_number = new StringParams("nns_entry_number");
    this.nns_entry_number.setValue(paramString4);
  }

  public static String getUserFeedbackReportUrl()
  {
    if (MgtvUrl.getServerPlatform() == MgtvUrl.ServerPlatform.Release)
      if (!TextUtils.isEmpty(urlUserFeedbackReportUrl));
    for (urlUserFeedbackReportUrl = "http://support.api.hunantv.com/OTT/report"; ; urlUserFeedbackReportUrl = "http://supporttest.api.hunantv.com/OTT/report")
      do
        return urlUserFeedbackReportUrl;
      while ((MgtvUrl.getServerPlatform() != MgtvUrl.ServerPlatform.Test) || (!TextUtils.isEmpty(urlUserFeedbackReportUrl)));
  }

  public static void setUserFeedbackReportUrl(String paramString)
  {
    urlUserFeedbackReportUrl = paramString;
  }

  public String buildQrCodeInfo()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_net_ip + this.nns_mac + this.nns_user_feedback_id + this.nns_entry_number;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.UserFeedbackParams
 * JD-Core Version:    0.6.2
 */