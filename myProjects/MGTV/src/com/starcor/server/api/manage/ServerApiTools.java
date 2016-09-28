package com.starcor.server.api.manage;

import com.starcor.httpapi.core.SCResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.http.Header;
import org.apache.http.HeaderElement;

public class ServerApiTools
{
  public static ServerApiCommonError buildCommonError(SCResponse paramSCResponse)
  {
    return new ServerApiCommonError(paramSCResponse.getRunState(), paramSCResponse.getRunReason(), paramSCResponse.getHttpCode(), paramSCResponse.getHttpReason(), paramSCResponse.getHeaderValue("Location"));
  }

  public static Header buildHttpHeader(String paramString1, final String paramString2)
  {
    return new Header()
    {
      public HeaderElement[] getElements()
        throws org.apache.http.ParseException
      {
        return new HeaderElement[0];
      }

      public String getName()
      {
        return this.val$key;
      }

      public String getValue()
      {
        return paramString2;
      }
    };
  }

  public static ServerApiCommonError buildParseError(SCResponse paramSCResponse, String paramString)
  {
    return new ServerApiCommonError(paramSCResponse.getRunState(), paramSCResponse.getRunReason(), paramSCResponse.getHttpCode(), paramSCResponse.getHttpReason(), true, paramString);
  }

  public static boolean isSCResponseError(SCResponse paramSCResponse)
  {
    if (paramSCResponse == null);
    while ((paramSCResponse.getRunState() != 1) || (paramSCResponse.getHttpCode() < 200) || (paramSCResponse.getHttpCode() >= 300))
      return true;
    return false;
  }

  public static Date parseDateString(String paramString)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    try
    {
      Date localDate = localSimpleDateFormat.parse(paramString);
      return localDate;
    }
    catch (java.text.ParseException localParseException)
    {
      localParseException.printStackTrace();
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.server.api.manage.ServerApiTools
 * JD-Core Version:    0.6.2
 */