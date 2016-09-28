package com.starcor.hunan.ads;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.DisplayMetrics;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.DeviceIdentifier;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.DialogActivity;
import org.json.JSONObject;

public class BuildRequestParams
{
  private static JSONObject buildAdInfo(String paramString1, String paramString2, int paramInt)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("p", paramString1);
      localJSONObject.put("aid", paramString2);
      localJSONObject.put("init", paramInt);
      localJSONObject.put("pu", "");
      return localJSONObject;
    }
    catch (Exception localException)
    {
      Logger.e(localException.getMessage());
    }
    return localJSONObject;
  }

  private static JSONObject buildDeviceInfo()
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("type", 100);
      localJSONObject.put("os", Build.VERSION.RELEASE);
      localJSONObject.put("version", DeviceInfo.getMGTVVersion());
      localJSONObject.put("brand", Build.BRAND);
      localJSONObject.put("operater", "04");
      localJSONObject.put("mcc", "086");
      localJSONObject.put("mn", Build.PRODUCT);
      localJSONObject.put("rs", App.SCREEN_WIDTH + "*" + App.SCREEN_HEIGHT);
      localJSONObject.put("mac", DeviceInfo.getMac());
      localJSONObject.put("idfa", "");
      localJSONObject.put("imei", "");
      localJSONObject.put("anid", Build.ID);
      localJSONObject.put("ctmid", DeviceIdentifier.getAAADeviceId(App.getAppContext()));
      localJSONObject.put("net", "");
      localJSONObject.put("ua", "");
      localJSONObject.put("ip", "");
      localJSONObject.put("insl", "");
      localJSONObject.put("lct", "");
      localJSONObject.put("lt", "2");
      localJSONObject.put("dpi", new DisplayMetrics().densityDpi);
      localJSONObject.put("ts", System.currentTimeMillis());
      localJSONObject.put("mml", 15);
      localJSONObject.put("aw", 0);
      localJSONObject.put("ah", 0);
      localJSONObject.put("pbeg", 0);
      localJSONObject.put("pend", 0);
      return localJSONObject;
    }
    catch (Exception localException)
    {
      Logger.e(localException.getMessage());
    }
    return localJSONObject;
  }

  private static JSONObject buildExtData()
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("extdata", "");
      return localJSONObject;
    }
    catch (Exception localException)
    {
      Logger.e(localException.getMessage());
    }
    return localJSONObject;
  }

  public static String buildPPramasJsonString(String paramString1, String paramString2, int paramInt)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("_v", "1");
      localJSONObject.put("fmt", "json");
      localJSONObject.put("m", buildAdInfo(paramString1, paramString2, paramInt));
      localJSONObject.put("u", buildUserInfo());
      localJSONObject.put("c", buildDeviceInfo());
      localJSONObject.put("ex", buildExtData());
      return localJSONObject.toString();
    }
    catch (Exception localException)
    {
      while (true)
        Logger.e(localException.getMessage());
    }
  }

  private static JSONObject buildUserInfo()
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("id", GlobalLogic.getInstance().getUserId());
      localJSONObject.put("acid", GlobalLogic.getInstance().getUserId());
      localJSONObject.put("nick", GlobalLogic.getInstance().getUserName());
      localJSONObject.put("male", 0);
      localJSONObject.put("vip", getVipValue());
      localJSONObject.put("age", 0);
      localJSONObject.put("edu", "");
      localJSONObject.put("race", "");
      return localJSONObject;
    }
    catch (Exception localException)
    {
      Logger.e(localException.getMessage());
    }
    return localJSONObject;
  }

  public static String buildVideoJsonInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, int paramInt1, int paramInt2, int paramInt3)
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    try
    {
      localJSONObject2.put("id", paramString1);
      localJSONObject2.put("name", paramString2);
      localJSONObject2.put("hid", paramString3);
      localJSONObject2.put("hname", paramString4);
      localJSONObject2.put("rid", "");
      Bundle localBundle = DialogActivity.findMenuItem(null, null, paramString5);
      if (localBundle != null)
        localJSONObject2.put("rname", localBundle.getString("name"));
      localJSONObject2.put("url", paramString6);
      localJSONObject2.put("title", paramString7);
      localJSONObject2.put("keyword", paramString8);
      localJSONObject2.put("on_date", paramString9);
      localJSONObject2.put("sub_type", paramString10);
      localJSONObject2.put("clip_type", paramString11);
      localJSONObject2.put("vtt", paramInt1);
      localJSONObject2.put("ispay", paramInt2);
      localJSONObject2.put("ispreview", paramInt3);
      localJSONObject1.put("v", localJSONObject2);
      return localJSONObject1.toString();
    }
    catch (Exception localException)
    {
      while (true)
        Logger.e("buildVideoJsonInfo buildInfoError");
    }
  }

  private static int getVipValue()
  {
    if (GlobalLogic.getInstance().isVip());
    UserInfo localUserInfo;
    do
    {
      return 1;
      localUserInfo = GlobalLogic.getInstance().getUserInfo();
      if (localUserInfo == null)
        return 0;
    }
    while ("2".equals(localUserInfo.vip_id));
    return 0;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.ads.BuildRequestParams
 * JD-Core Version:    0.6.2
 */