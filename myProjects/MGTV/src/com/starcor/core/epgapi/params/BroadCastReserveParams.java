package com.starcor.core.epgapi.params;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.epgapi.Api;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BroadCastReserveParams extends Api
{
  private String name = "";
  private StringParams nns_action_params;
  private StringParams nns_mac_id;
  private StringParams nns_output_type;
  private StringParams nns_user_id;
  private StringParams nns_version;
  private VideoInfo videoinfo = null;

  public BroadCastReserveParams(String paramString, int paramInt)
  {
    this.prefix = AppInfo.URL_n40_k;
    if (paramInt == 1)
    {
      this.nns_func.setValue("carousel_requst");
      this.name = "N40_K_1";
    }
    while (true)
    {
      this.nns_action_params = new StringParams("nns_action_params");
      this.nns_output_type = new StringParams("nns_output_type");
      this.nns_output_type.setValue("xml");
      this.nns_mac_id = new StringParams("nns_mac_id");
      this.nns_mac_id.setValue(DeviceInfo.getMac().replace("-", "").trim());
      this.nns_version = new StringParams("nns_version");
      this.nns_version.setValue(DeviceInfo.getMGTVVersion());
      this.nns_user_id = new StringParams("nns_user_id");
      this.nns_user_id.setValue(GlobalLogic.getInstance().getUserId());
      Object localObject = "";
      try
      {
        String str = "channelid=" + URLEncoder.encode(paramString, "utf-8");
        localObject = str;
        this.nns_action_params.setValue((String)localObject);
        return;
        if (paramInt != 2)
          continue;
        this.nns_func.setValue("carousel_cancel");
        this.name = "N40_K_2";
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        while (true)
          localUnsupportedEncodingException.printStackTrace();
      }
    }
  }

  public String getApiName()
  {
    return this.name;
  }

  public String toString()
  {
    String str = this.prefix;
    if (!str.contains("?"))
      str = str + "?";
    return str + this.nns_func + this.nns_user_id + this.nns_mac_id + this.nns_version + this.nns_action_params + this.nns_output_type + this.suffix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.epgapi.params.BroadCastReserveParams
 * JD-Core Version:    0.6.2
 */