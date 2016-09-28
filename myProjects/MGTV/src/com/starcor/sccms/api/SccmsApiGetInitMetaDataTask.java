package com.starcor.sccms.api;

import com.starcor.core.epgapi.params.GetInitMetaDataparams;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetInitMetaDataTask extends ServerApiCachedTask
{
  private ISccmsApiGetInitMetaDataTaskListener lsr;

  public String getTaskName()
  {
    return "N36_A_1";
  }

  public String getUrl()
  {
    GetInitMetaDataparams localGetInitMetaDataparams = new GetInitMetaDataparams();
    localGetInitMetaDataparams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localGetInitMetaDataparams.toString(), localGetInitMetaDataparams.getApiName());
  }

  public byte[] localPerform(SCResponse paramSCResponse)
  {
    if (this.lsr == null)
      return null;
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return null;
    }
    try
    {
      byte[] arrayOfByte1 = paramSCResponse.getContents();
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), arrayOfByte1);
      byte[] arrayOfByte2 = paramSCResponse.getContents();
      return arrayOfByte2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
    return null;
  }

  public void perform(SCResponse paramSCResponse)
  {
    byte[] arrayOfByte = localPerform(paramSCResponse);
    if (arrayOfByte != null)
      serializeApiData(arrayOfByte);
  }

  public void setListener(ISccmsApiGetInitMetaDataTaskListener paramISccmsApiGetInitMetaDataTaskListener)
  {
    this.lsr = paramISccmsApiGetInitMetaDataTaskListener;
  }

  public static abstract interface ISccmsApiGetInitMetaDataTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, byte[] paramArrayOfByte);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetInitMetaDataTask
 * JD-Core Version:    0.6.2
 */