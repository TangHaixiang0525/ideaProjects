package com.umeng.analytics.onlineconfig;

import java.util.Locale;
import org.json.JSONObject;
import u.aly.br;
import u.aly.by;

public class b extends by
{
  public JSONObject a = null;
  boolean b = false;
  int c = -1;
  int d = -1;
  String e;
  private final String f = "config_update";
  private final String g = "report_policy";
  private final String h = "online_params";
  private final String i = "last_config_time";
  private final String j = "report_interval";

  public b(JSONObject paramJSONObject)
  {
    super(paramJSONObject);
    if (paramJSONObject == null)
      return;
    a(paramJSONObject);
    a();
  }

  private void a()
  {
    if ((this.c < 0) || (this.c > 6))
      this.c = 1;
  }

  private void a(JSONObject paramJSONObject)
  {
    while (true)
    {
      try
      {
        if (!paramJSONObject.has("config_update"))
          break;
        if (paramJSONObject.getString("config_update").toLowerCase(Locale.US).equals("no"))
          return;
        if (paramJSONObject.has("report_policy"))
        {
          this.c = paramJSONObject.getInt("report_policy");
          this.d = (1000 * paramJSONObject.optInt("report_interval"));
          this.e = paramJSONObject.optString("last_config_time");
          this.a = paramJSONObject.optJSONObject("online_params");
          this.b = true;
          return;
        }
      }
      catch (Exception localException)
      {
        br.e("MobclickAgent", "fail to parce online config response", localException);
        return;
      }
      br.e("MobclickAgent", " online config fetch no report policy");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.onlineconfig.b
 * JD-Core Version:    0.6.2
 */