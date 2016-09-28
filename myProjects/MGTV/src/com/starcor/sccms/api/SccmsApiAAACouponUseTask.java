package com.starcor.sccms.api;

import com.starcor.core.domain.AAACouponUse;
import com.starcor.core.epgapi.params.AAACouponUseParams;
import com.starcor.core.parser.json.AAACouponUseSaxParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAACouponUseTask extends ServerApiTask
{
  private String TAG = "SccmsApiAAACouponUseTask";
  private String key = "";
  private String license = "";
  private ISccmsApiAAACouponUseTaskListener lsr;

  public SccmsApiAAACouponUseTask(String paramString1, String paramString2)
  {
    this.license = paramString1;
    this.key = paramString2;
  }

  public String getTaskName()
  {
    return "N212_A_15";
  }

  public String getUrl()
  {
    AAACouponUseParams localAAACouponUseParams = new AAACouponUseParams(this.license, this.key);
    localAAACouponUseParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAACouponUseParams.toString(), localAAACouponUseParams.getApiName());
    Logger.i(this.TAG, "N212_A_15 url:" + str);
    return str;
  }

  public void perform(SCResponse paramSCResponse)
  {
    if (this.lsr == null)
      return;
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      AAACouponUse localAAACouponUse = (AAACouponUse)new AAACouponUseSaxParserJson().parser(paramSCResponse.getContents());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAACouponUse);
      Logger.i(this.TAG, "N212_A_15 result:" + localAAACouponUse.toString());
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiAAACouponUseTaskListener paramISccmsApiAAACouponUseTaskListener)
  {
    this.lsr = paramISccmsApiAAACouponUseTaskListener;
  }

  public static abstract interface ISccmsApiAAACouponUseTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAACouponUse paramAAACouponUse);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAACouponUseTask
 * JD-Core Version:    0.6.2
 */