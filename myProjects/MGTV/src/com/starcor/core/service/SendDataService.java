package com.starcor.core.service;

import android.content.Context;
import com.starcor.core.epgapi.Api;
import com.starcor.core.http.HttpWrapper;
import com.starcor.core.utils.Logger;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class SendDataService
{
  private static SendDataService sendService;
  private Context mContext;

  private SendDataService(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public static SendDataService getInstance(Context paramContext)
  {
    try
    {
      if (sendService == null)
        sendService = new SendDataService(paramContext);
      SendDataService localSendDataService = sendService;
      return localSendDataService;
    }
    finally
    {
    }
  }

  public int sendBackStatus(Api paramApi)
  {
    try
    {
      Logger.d(" ---> 移出收藏列表  url = " + paramApi.toString());
      int i = HttpWrapper.getInstance(this.mContext).getHttpStatus(paramApi.toString());
      return i;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localUnsupportedEncodingException.printStackTrace();
      return 0;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return 0;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.service.SendDataService
 * JD-Core Version:    0.6.2
 */