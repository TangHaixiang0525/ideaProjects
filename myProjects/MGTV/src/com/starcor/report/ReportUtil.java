package com.starcor.report;

import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.report.cdn.ReportParamPair;
import com.starcor.server.api.manage.ServerApiCommonError;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public final class ReportUtil
{
  private static final String TAG = ReportUtil.class.getSimpleName();

  public static String formatParam(List<ReportParamPair> paramList)
  {
    String str = "";
    if (paramList != null)
    {
      int i = 1;
      StringBuilder localStringBuilder = new StringBuilder();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        ReportParamPair localReportParamPair = (ReportParamPair)localIterator.next();
        if ((localReportParamPair != null) && (!TextUtils.isEmpty(localReportParamPair.getKey())))
        {
          if (i == 0)
            localStringBuilder.append("&");
          while (true)
          {
            localStringBuilder.append(localReportParamPair.getKey());
            localStringBuilder.append("=");
            if (TextUtils.isEmpty(localReportParamPair.getValue()))
              break;
            localStringBuilder.append(localReportParamPair.getValue());
            break;
            i = 0;
          }
        }
      }
      str = localStringBuilder.toString();
    }
    return str;
  }

  public static String formatParam(JSONObject paramJSONObject)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (paramJSONObject != null)
    {
      int i = 1;
      Iterator localIterator = paramJSONObject.keys();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        if (i == 0)
          localStringBuilder.append("&");
        String str2;
        while (true)
        {
          while (true)
          {
            localStringBuilder.append(str1);
            localStringBuilder.append("=");
            try
            {
              str2 = paramJSONObject.getString(str1);
              if (!TextUtils.isEmpty(str2))
                break label110;
              localStringBuilder.append("null");
            }
            catch (Exception localException)
            {
              Logger.w(TAG, "formatParam Json failed", localException);
            }
          }
          break;
          i = 0;
        }
        label110: localStringBuilder.append(str2);
      }
    }
    return localStringBuilder.toString();
  }

  public static String formatUrl(String paramString, List<ReportParamPair> paramList)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      String str = paramString;
      if (!str.contains("?"))
        str = str + "?";
      return str + formatParam(paramList);
    }
    return "";
  }

  public static String formatUrl(String paramString, JSONObject paramJSONObject)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      String str = paramString;
      if (!str.contains("?"))
        str = str + "?";
      return str + formatParam(paramJSONObject);
    }
    return "";
  }

  public static String getHost(String paramString)
  {
    Object localObject = paramString;
    try
    {
      URL localURL = new URL(paramString);
      localObject = localURL.getHost();
      int i = localURL.getPort();
      if ((i != -1) && (i != 80))
      {
        String str = (String)localObject + ":" + String.valueOf(i);
        localObject = str;
      }
      Logger.d(TAG, "getHost url: " + paramString + ", host: " + (String)localObject);
      return localObject;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      while (true)
        localMalformedURLException.printStackTrace();
    }
  }

  public static String parseApiErrorCode(ServerApiCommonError paramServerApiCommonError)
  {
    String str = "";
    if (paramServerApiCommonError != null)
    {
      if (paramServerApiCommonError.isParseError())
        str = "2010205";
    }
    else
      return str;
    int i = paramServerApiCommonError.getRunState();
    if ((i == 3) || (i == 4))
      return "2010203";
    return "2010204";
  }

  public static String parseCDNErrorCode(ServerApiCommonError paramServerApiCommonError)
  {
    if (paramServerApiCommonError != null)
    {
      Logger.d(TAG, "getCDNErrorCode error: " + paramServerApiCommonError);
      if (paramServerApiCommonError.isParseError())
        return "102000";
      int i = paramServerApiCommonError.getRunState();
      if ((i == 3) || (i == 4))
        return "103000";
      if (i == 1)
        return "101" + String.valueOf(paramServerApiCommonError.getHttpCode());
    }
    return "104000";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.ReportUtil
 * JD-Core Version:    0.6.2
 */