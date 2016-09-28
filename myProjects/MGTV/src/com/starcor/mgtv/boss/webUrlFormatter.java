package com.starcor.mgtv.boss;

import android.text.TextUtils;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.domain.AppInfo;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.opendownload.encrypt.EncryptLogic;
import java.util.HashMap;
import java.util.HashSet;

public class webUrlFormatter
{
  private static webUrlFormatter instance;
  private HashSet<String> mgtvCDNSkipArgs = new HashSet();

  webUrlFormatter()
  {
    this.mgtvCDNSkipArgs.clear();
    this.mgtvCDNSkipArgs.add("nns_token");
    this.mgtvCDNSkipArgs.add("nns_user_id");
    this.mgtvCDNSkipArgs.add("nns_mac_id");
    this.mgtvCDNSkipArgs.add("nns_mac");
    this.mgtvCDNSkipArgs.add("nns_smart_card_id");
    this.mgtvCDNSkipArgs.add("nns_device_id");
    this.mgtvCDNSkipArgs.add("nns_user_password");
    this.mgtvCDNSkipArgs.add("nns_webtoken");
    this.mgtvCDNSkipArgs.add("nns_epg_server");
    this.mgtvCDNSkipArgs.add("nns_net_id");
    this.mgtvCDNSkipArgs.add("nns_login_id");
    this.mgtvCDNSkipArgs.add("nns_ex_data");
  }

  private String buildDomainUrl(String paramString1, String paramString2)
  {
    String str1 = "api_host_" + paramString2.toLowerCase();
    HashMap localHashMap = AppInfo.domainUrl;
    if ((localHashMap.containsKey(str1)) && (!TextUtils.isEmpty((CharSequence)localHashMap.get(str1))))
    {
      String str2 = (String)localHashMap.get(str1);
      if (!TextUtils.isEmpty(str2))
      {
        paramString1 = paramString1.replaceAll("^http[s]?://[^\\s]*?/", str2);
        Logger.i("Api", "srcApiName:" + str1 + ", 替换后的url=\n" + paramString1);
      }
    }
    else
    {
      return paramString1;
    }
    Logger.i("Api", "srcApiName:" + str1 + ", 未替换，url=\n" + paramString1);
    return paramString1;
  }

  private String buildUrlCDNShtml(String paramString1, String paramString2)
  {
    String[] arrayOfString1 = paramString1.replaceAll("^http[s]?://[^\\s]*?/", paramString2).split("\\?");
    String str1 = arrayOfString1[0];
    String[] arrayOfString2 = arrayOfString1[1].split("&");
    HashSet localHashSet = this.mgtvCDNSkipArgs;
    String str2 = "";
    String str3 = "";
    String str4 = null;
    int i = 0;
    int j = 0;
    if (j < arrayOfString2.length)
    {
      String[] arrayOfString3 = arrayOfString2[j].split("=");
      String str5 = changeMgtvName(arrayOfString3[0]);
      if (arrayOfString3.length > 1)
      {
        if (!str5.equals("Func"))
          break label114;
        str4 = changeMgtvName(arrayOfString3[1]);
      }
      while (true)
      {
        j++;
        break;
        label114: if (!localHashSet.contains(arrayOfString3[0]))
          if ((i < 5) && (!str5.equals("Pack")))
          {
            i++;
            str2 = str2 + "--" + str5 + "__" + arrayOfString3[1];
          }
          else
          {
            i++;
            if (TextUtils.isEmpty(str3))
              str3 = str3 + str5 + "=" + arrayOfString3[1];
            else
              str3 = str3 + "&" + str5 + "=" + arrayOfString3[1];
          }
      }
    }
    if (str4 != null)
      str1 = str1 + "/" + str4;
    if (TextUtils.isEmpty(str3))
      return str1 + str2 + ".shtml";
    return str1 + str2 + ".shtml?" + str3;
  }

  private String buildUrlDy(String paramString)
  {
    String str1 = "";
    String[] arrayOfString1 = paramString.split("\\?");
    int i = arrayOfString1.length;
    String str2 = null;
    if (i > 1)
    {
      String[] arrayOfString2 = arrayOfString1[1].split("&");
      int j = 0;
      if (j < arrayOfString2.length)
      {
        String[] arrayOfString3 = arrayOfString2[j].split("=");
        String str3 = changeMgtvName(arrayOfString3[0]);
        if ((arrayOfString3 != null) && (arrayOfString3.length > 1))
        {
          if (!str3.equals("Func"))
            break label104;
          str2 = changeMgtvName(arrayOfString3[1]);
        }
        while (true)
        {
          j++;
          break;
          label104: if (TextUtils.isEmpty(str1))
            str1 = str1 + str3 + "=" + arrayOfString3[1];
          else
            str1 = str1 + "&" + str3 + "=" + arrayOfString3[1];
        }
      }
    }
    if (str2 != null)
      arrayOfString1[0] = (arrayOfString1[0] + "/" + str2);
    return arrayOfString1[0] + "?" + str1;
  }

  private String changeMgtvName(String paramString)
  {
    String str1 = "";
    if (paramString.contains("_"))
    {
      paramString.indexOf("_");
      String[] arrayOfString = paramString.split("_");
      int i = 0;
      if (i < arrayOfString.length)
      {
        String str2 = arrayOfString[i];
        if ("nns".equals(str2));
        while (true)
        {
          i++;
          break;
          String str3 = str2.charAt(0) + "";
          str1 = str1 + str2.replaceFirst(str3, str3.toUpperCase());
        }
      }
      paramString = str1;
    }
    return paramString;
  }

  public static webUrlFormatter i()
  {
    if (instance == null)
      instance = new webUrlFormatter();
    return instance;
  }

  public String formatUrl(String paramString1, String paramString2)
  {
    String str1 = WebUrlBuilder.addMgtvAgsToEPGUrl(paramString1);
    String str2 = paramString2;
    if (str2 != null)
      str2 = str2.replaceAll("-", "_").toLowerCase();
    if (AppFuncCfg.FUNCTION_ENABLE_DOMAIN_REPLACE)
      str1 = buildDomainUrl(str1, str2);
    if (AppFuncCfg.FUNCTION_DISABLE_API_URL_TRANSFORM)
    {
      Logger.i("Api", "srcApiName:" + paramString2 + ", raw api url=" + str1);
      return str1;
    }
    HashMap localHashMap = AppInfo.cdnUrl;
    String str3;
    if ((AppFuncCfg.FUNCTION_MGTV_CDN) && (localHashMap.containsKey(str2)) && (!TextUtils.isEmpty((CharSequence)localHashMap.get(str2))))
    {
      String str4 = (String)localHashMap.get(str2);
      Logger.i("Api", "srcApiName:" + paramString2 + ", cdn前的url=" + str1);
      if (AppFuncCfg.FUNCTION_USE_URL_ENCRYPT)
        str1 = EncryptLogic.encode(str1, paramString2);
      str3 = buildUrlCDNShtml(str1, str4);
      Logger.i("Api", "srcApiName:" + paramString2 + ", 转换后的url=\n" + str3);
    }
    while (true)
    {
      return str3;
      if (AppFuncCfg.FUNCTION_USE_URL_ENCRYPT)
        str1 = EncryptLogic.encode(str1, paramString2);
      str3 = buildUrlDy(str1);
      Logger.i("Api", "srcApiName:" + paramString2 + ", 转换后的url=\n" + str3);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.mgtv.boss.webUrlFormatter
 * JD-Core Version:    0.6.2
 */