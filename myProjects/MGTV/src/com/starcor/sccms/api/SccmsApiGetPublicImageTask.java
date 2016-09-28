package com.starcor.sccms.api;

import com.starcor.core.domain.PublicImage;
import com.starcor.core.epgapi.params.GetPublicImageParams;
import com.starcor.core.parser.json.GetPublicImageSaxParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.List;

public class SccmsApiGetPublicImageTask extends ServerApiTask
{
  ISccmsApiGetPublicImageTaskListener lsr = null;
  String type = "";

  public SccmsApiGetPublicImageTask(String paramString)
  {
    this.type = paramString;
  }

  public String getTaskName()
  {
    return "N36_A_3";
  }

  public String getUrl()
  {
    GetPublicImageParams localGetPublicImageParams = new GetPublicImageParams(this.type);
    localGetPublicImageParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localGetPublicImageParams.toString(), localGetPublicImageParams.getApiName());
    Logger.i(str);
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetPublicImageSaxParserJson localGetPublicImageSaxParserJson = new GetPublicImageSaxParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      List localList = (List)localGetPublicImageSaxParserJson.parser(paramSCResponse.getContents());
      Logger.i("SccmsApiGetPublicImageTask", "  result:" + localList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetPublicImageTaskListener paramISccmsApiGetPublicImageTaskListener)
  {
    this.lsr = paramISccmsApiGetPublicImageTaskListener;
  }

  public static abstract interface ISccmsApiGetPublicImageTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, List<PublicImage> paramList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetPublicImageTask
 * JD-Core Version:    0.6.2
 */