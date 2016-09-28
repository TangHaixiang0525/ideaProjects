package com.starcor.hunan.ads;

import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;

public class GetAdInfoTask extends ServerApiTask
{
  private HttpEntity entity;
  private IGetAdInfoListener lsnr;
  private String params;
  private String url;

  public GetAdInfoTask(String paramString1, String paramString2)
  {
    this.url = paramString1;
    this.params = paramString2;
  }

  public int getConnectTimeOutMs()
  {
    return 3000;
  }

  public HttpEntity getPostBody()
  {
    try
    {
      this.entity = new ByteArrayEntity(this.params.getBytes(Charset.forName("utf-8")));
      return this.entity;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        this.entity = null;
      }
    }
  }

  public List<Header> getReqHeaders()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(ServerApiTools.buildHttpHeader("m", "1"));
    return localArrayList;
  }

  public int getRequestMethod()
  {
    return 1;
  }

  public int getRetryTimes()
  {
    return 1;
  }

  public String getTaskName()
  {
    return "getAdInfo";
  }

  public String getUrl()
  {
    return this.url;
  }

  public void perform(SCResponse paramSCResponse)
  {
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsnr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      Logger.i("GetAdInfoTask", "rep --->" + new String(paramSCResponse.getContents()));
      MgAdInfo localMgAdInfo = AdInfoParser.parseAdInfo(new String(paramSCResponse.getContents()));
      Logger.i("GetAdInfoTask", "info --->" + localMgAdInfo.toString());
      this.lsnr.onSuccess(new ServerApiTaskInfo(getTaskId(), paramSCResponse), localMgAdInfo);
      return;
    }
    catch (Exception localException)
    {
      this.lsnr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
    }
  }

  public void setListener(IGetAdInfoListener paramIGetAdInfoListener)
  {
    this.lsnr = paramIGetAdInfoListener;
  }

  public static abstract interface IGetAdInfoListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, MgAdInfo paramMgAdInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.ads.GetAdInfoTask
 * JD-Core Version:    0.6.2
 */