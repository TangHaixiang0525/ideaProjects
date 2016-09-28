package com.starcor.sccms.api;

import com.starcor.core.domain.SpecialTopicPkgCntLstInfo;
import com.starcor.core.epgapi.params.GetSpecialTopicPkgContentLstPaeams;
import com.starcor.core.parser.json.GetSpecialTopicPkgContentLstSAXParserJson;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiGetSpecialTopicPkgContentLstTask extends ServerApiTask
{
  private String include_category;
  ISccmsApiGetSearchSpecialTopicPkgLstTaskListener lsr = null;
  private String page_index;
  private String page_size;
  private String sort_type;
  private String special_id;

  public SccmsApiGetSpecialTopicPkgContentLstTask(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.special_id = paramString1;
    this.page_index = paramString2;
    this.page_size = paramString3;
    this.sort_type = paramString4;
    this.include_category = paramString5;
    setCacheLife(1800000L);
  }

  public String getTaskName()
  {
    return "N24_A_2";
  }

  public String getUrl()
  {
    GetSpecialTopicPkgContentLstPaeams localGetSpecialTopicPkgContentLstPaeams = new GetSpecialTopicPkgContentLstPaeams(this.special_id, this.page_index, this.page_size, this.sort_type, this.include_category);
    localGetSpecialTopicPkgContentLstPaeams.setResultFormat(1);
    return webUrlFormatter.i().formatUrl(localGetSpecialTopicPkgContentLstPaeams.toString(), localGetSpecialTopicPkgContentLstPaeams.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    GetSpecialTopicPkgContentLstSAXParserJson localGetSpecialTopicPkgContentLstSAXParserJson = new GetSpecialTopicPkgContentLstSAXParserJson();
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      SpecialTopicPkgCntLstInfo localSpecialTopicPkgCntLstInfo = (SpecialTopicPkgCntLstInfo)localGetSpecialTopicPkgContentLstSAXParserJson.parser(paramSCResponse.getContents());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localSpecialTopicPkgCntLstInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiGetSearchSpecialTopicPkgLstTaskListener paramISccmsApiGetSearchSpecialTopicPkgLstTaskListener)
  {
    this.lsr = paramISccmsApiGetSearchSpecialTopicPkgLstTaskListener;
  }

  public static abstract interface ISccmsApiGetSearchSpecialTopicPkgLstTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, SpecialTopicPkgCntLstInfo paramSpecialTopicPkgCntLstInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetSpecialTopicPkgContentLstTask
 * JD-Core Version:    0.6.2
 */