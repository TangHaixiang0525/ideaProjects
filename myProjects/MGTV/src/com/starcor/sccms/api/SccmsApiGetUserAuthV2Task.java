package com.starcor.sccms.api;

import com.starcor.core.domain.UserAuthV2;
import com.starcor.core.epgapi.params.GetUserAuthV2Params;
import com.starcor.core.parser.sax.GetUserAuthV2SAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetUserAuthV2Task extends ServerApiCachedTask
{
  private static String TAG = "SccmsApiGetUserAuthV2Task";
  ISccmsApiGetUserAuthV2TaskListener lsr;
  GetUserAuthV2Params mParams;
  String nns_package_id = null;

  public SccmsApiGetUserAuthV2Task(GetUserAuthV2Params paramGetUserAuthV2Params)
  {
    this.mParams = paramGetUserAuthV2Params;
  }

  public String getTaskName()
  {
    return "N50_A_1";
  }

  public String getUrl()
  {
    if (this.mParams == null)
    {
      Logger.e(TAG, "mParams is null");
      return "";
    }
    this.mParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(this.mParams.toString(), this.mParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetUserAuthV2SAXParser localGetUserAuthV2SAXParser = new GetUserAuthV2SAXParser();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      UserAuthV2 localUserAuthV2 = (UserAuthV2)localGetUserAuthV2SAXParser.parser(paramSCResponse.getContents());
      if (localUserAuthV2 != null)
      {
        Logger.i(TAG, "GetUserAuthV2SAXParser result:" + localUserAuthV2.toString());
        this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localUserAuthV2);
      }
      while (true)
      {
        super.perform(paramSCResponse);
        return;
        this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      }
    }
    catch (Exception localException)
    {
      while (true)
        this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetUserAuthV2TaskListener paramISccmsApiGetUserAuthV2TaskListener)
  {
    this.lsr = paramISccmsApiGetUserAuthV2TaskListener;
  }

  public static abstract interface ISccmsApiGetUserAuthV2TaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserAuthV2 paramUserAuthV2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetUserAuthV2Task
 * JD-Core Version:    0.6.2
 */