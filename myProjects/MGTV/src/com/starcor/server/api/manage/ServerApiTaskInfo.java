package com.starcor.server.api.manage;

import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.hunan.service.SystemTimeManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.apache.http.Header;

public class ServerApiTaskInfo
{
  private String httpRequestHeader = "";
  private String httpRequestUrl = "";
  private String httpResponseHeader = "";
  private String serverDate = null;
  private int taskId;
  private String taskName = "";

  public ServerApiTaskInfo(int paramInt, SCResponse paramSCResponse)
  {
    this.taskId = paramInt;
    this.serverDate = paramSCResponse.getHeaderValue("Date");
    this.taskName = paramSCResponse.getTaskName();
    if (paramSCResponse.getHeaders() == null)
    {
      this.httpResponseHeader = "";
      if (paramSCResponse.getRequestHeaders() != null)
        break label137;
    }
    label137: for (this.httpRequestHeader = ""; ; this.httpRequestHeader = getHeaderString(paramSCResponse.getRequestHeaders()))
    {
      if ((!TextUtils.isEmpty(this.serverDate)) && (!SystemTimeManager.getInstance().isInit()))
      {
        String str = formatTimeString(this.serverDate);
        if (str != null)
          SystemTimeManager.getInstance().synchronizedTime(str);
      }
      return;
      this.httpResponseHeader = getHeaderString(paramSCResponse.getHeaders());
      break;
    }
  }

  public ServerApiTaskInfo(int paramInt, String paramString, SCResponse paramSCResponse)
  {
    this.taskId = paramInt;
    this.httpRequestUrl = paramString;
    this.serverDate = paramSCResponse.getHeaderValue("Date");
    this.taskName = paramSCResponse.getTaskName();
    if (paramSCResponse.getHeaders() == null)
    {
      this.httpResponseHeader = "";
      if (paramSCResponse.getRequestHeaders() != null)
        break label145;
    }
    label145: for (this.httpRequestHeader = ""; ; this.httpRequestHeader = getHeaderString(paramSCResponse.getRequestHeaders()))
    {
      if ((!TextUtils.isEmpty(this.serverDate)) && (!SystemTimeManager.getInstance().isInit()))
      {
        String str = formatTimeString(this.serverDate);
        if (str != null)
          SystemTimeManager.getInstance().synchronizedTime(str);
      }
      return;
      this.httpResponseHeader = getHeaderString(paramSCResponse.getHeaders());
      break;
    }
  }

  private String formatTimeString(String paramString)
  {
    SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");
    Logger.i("ApiTaskInfo", "Time:" + paramString);
    try
    {
      String str = localSimpleDateFormat2.format(localSimpleDateFormat1.parse(paramString));
      return str;
    }
    catch (ParseException localParseException)
    {
      localParseException.printStackTrace();
    }
    return null;
  }

  private String getHeaderString(Header[] paramArrayOfHeader)
  {
    String str1 = "";
    if ((paramArrayOfHeader != null) && (paramArrayOfHeader.length > 0))
    {
      int i = paramArrayOfHeader.length;
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("\n");
      for (int j = 0; j < i; j++)
      {
        String str2 = paramArrayOfHeader[j].getName();
        String str3 = str2 + ":";
        localStringBuffer.append(str3 + paramArrayOfHeader[j].getValue());
        if (j < i - 1)
          localStringBuffer.append("\n");
      }
      str1 = localStringBuffer.toString();
    }
    return str1;
  }

  public String getHttpRequestHeader()
  {
    return this.httpRequestHeader;
  }

  public String getHttpRequestUrl()
  {
    return this.httpRequestUrl;
  }

  public String getHttpResponseHeader()
  {
    return this.httpResponseHeader;
  }

  public String getServerDate()
  {
    return this.serverDate;
  }

  public int getTaskId()
  {
    return this.taskId;
  }

  public String getTaskName()
  {
    return this.taskName;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.server.api.manage.ServerApiTaskInfo
 * JD-Core Version:    0.6.2
 */