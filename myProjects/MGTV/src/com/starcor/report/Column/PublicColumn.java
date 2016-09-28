package com.starcor.report.Column;

import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.starcor.config.DeviceInfo;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Tools;
import com.starcor.hunan.App;
import com.starcor.hunan.service.SystemTimeManager;
import org.json.JSONException;
import org.json.JSONObject;

public class PublicColumn extends JSONObject
{
  protected String aver = "";
  protected String cookie = "";
  protected String did = "";
  protected String guid = "";
  protected String isdebug = "";
  protected String lics = "";
  protected String mf = "";
  protected String mod = "";
  protected String net = "";
  protected String sver = "";
  protected String time = "";
  protected String uuid = "";

  public PublicColumn()
  {
    if (DeviceInfo.getMGTVVersion().contains("Release"));
    for (this.isdebug = "0"; ; this.isdebug = "1")
    {
      this.mf = Build.MANUFACTURER;
      this.mod = Build.DEVICE;
      this.sver = Build.VERSION.RELEASE;
      this.aver = DeviceInfo.getMGTVVersion();
      return;
    }
  }

  public static String getEmptyIfEmpty(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      paramString = "";
    return paramString;
  }

  public static String getNullIfEmpty(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      paramString = "null";
    return paramString;
  }

  public JSONObject addPageName(String paramString)
  {
    try
    {
      put("pagename", getEmptyIfEmpty(paramString));
      return this;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return this;
  }

  protected JSONObject buildJsonData()
  {
    updateColumn();
    try
    {
      put("time", this.time);
      put("guid", this.guid);
      put("did", this.did);
      put("cookie", this.cookie);
      put("uuid", this.uuid);
      put("net", this.net);
      put("isdebug", this.isdebug);
      put("mf", this.mf);
      put("mod", this.mod);
      put("sver", this.sver);
      put("aver", this.aver);
      put("lics", this.lics);
      return this;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return this;
  }

  protected void updateColumn()
  {
    this.time = (SystemTimeManager.getCurrentServerDate() + SystemTimeManager.getCurrentServerTimeHMS());
    if (GlobalLogic.getInstance().getUserId().startsWith("mgtvmac"))
    {
      this.uuid = "";
      if (!Tools.isWifi(App.getAppContext()))
        break label85;
    }
    label85: for (this.net = "1"; ; this.net = "5")
    {
      this.lics = GlobalEnv.getInstance().getAAALicense();
      return;
      this.uuid = GlobalLogic.getInstance().getUserId();
      break;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.Column.PublicColumn
 * JD-Core Version:    0.6.2
 */