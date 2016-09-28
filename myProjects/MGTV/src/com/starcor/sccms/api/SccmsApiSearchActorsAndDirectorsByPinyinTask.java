package com.starcor.sccms.api;

import com.starcor.core.domain.VideoRoleList;
import com.starcor.core.epgapi.params.SearchActorsAndDirectorsByPinyinParams;
import com.starcor.core.parser.json.SearchActorsAndDirectorsByPinyinSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiSearchActorsAndDirectorsByPinyinTask extends ServerApiTask
{
  ISccmsApiSearchActorsAndDirectorsByPinyinTaskListener lsr = null;
  String nns_pinyin = null;

  public SccmsApiSearchActorsAndDirectorsByPinyinTask(String paramString)
  {
    this.nns_pinyin = paramString;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N3_A_G_5";
  }

  public String getUrl()
  {
    SearchActorsAndDirectorsByPinyinParams localSearchActorsAndDirectorsByPinyinParams = new SearchActorsAndDirectorsByPinyinParams(this.nns_pinyin);
    localSearchActorsAndDirectorsByPinyinParams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localSearchActorsAndDirectorsByPinyinParams.toString(), localSearchActorsAndDirectorsByPinyinParams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    SearchActorsAndDirectorsByPinyinSAXParserJson localSearchActorsAndDirectorsByPinyinSAXParserJson = new SearchActorsAndDirectorsByPinyinSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      VideoRoleList localVideoRoleList = (VideoRoleList)localSearchActorsAndDirectorsByPinyinSAXParserJson.parser(paramSCResponse.getContents());
      if ((localVideoRoleList.actorList == null) && (localVideoRoleList.directorList == null))
        Logger.e("SccmsApiSearchActorsAndDirectorsByPinyinTask", "返回错误的结果");
      Logger.i("SccmsApiSearchActorsAndDirectorsByPinyinTask", " result:" + localVideoRoleList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localVideoRoleList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiSearchActorsAndDirectorsByPinyinTaskListener paramISccmsApiSearchActorsAndDirectorsByPinyinTaskListener)
  {
    this.lsr = paramISccmsApiSearchActorsAndDirectorsByPinyinTaskListener;
  }

  public static abstract interface ISccmsApiSearchActorsAndDirectorsByPinyinTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, VideoRoleList paramVideoRoleList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiSearchActorsAndDirectorsByPinyinTask
 * JD-Core Version:    0.6.2
 */