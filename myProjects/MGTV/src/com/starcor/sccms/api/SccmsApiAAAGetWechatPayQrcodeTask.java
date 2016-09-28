package com.starcor.sccms.api;

import com.starcor.core.domain.AAAWechatPayQrcode;
import com.starcor.core.epgapi.params.AAAGetWechatPayQrodeParams;
import com.starcor.core.parser.json.AAAWeChatPayQrcodeSaxParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAAGetWechatPayQrcodeTask extends ServerApiTask
{
  private String TAG = "SccmsApiAAAGetWechatPayQrcodeTask";
  private String license = "";
  private ISccmsApiAAAGetWechatPayQrcodeTaskListener lsr;

  public SccmsApiAAAGetWechatPayQrcodeTask(String paramString)
  {
    this.license = paramString;
  }

  public String getTaskName()
  {
    return "N212_A_14";
  }

  public String getUrl()
  {
    AAAGetWechatPayQrodeParams localAAAGetWechatPayQrodeParams = new AAAGetWechatPayQrodeParams(this.license);
    localAAAGetWechatPayQrodeParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAAGetWechatPayQrodeParams.toString(), localAAAGetWechatPayQrodeParams.getApiName());
    Logger.i(this.TAG, "N212_A_14 url:" + str);
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
      AAAWechatPayQrcode localAAAWechatPayQrcode = (AAAWechatPayQrcode)new AAAWeChatPayQrcodeSaxParserJson().parser(paramSCResponse.getContents());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAAWechatPayQrcode);
      Logger.i(this.TAG, "N212_A_14 result:" + localAAAWechatPayQrcode.toString());
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiAAAGetWechatPayQrcodeTaskListener paramISccmsApiAAAGetWechatPayQrcodeTaskListener)
  {
    this.lsr = paramISccmsApiAAAGetWechatPayQrcodeTaskListener;
  }

  public static abstract interface ISccmsApiAAAGetWechatPayQrcodeTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAWechatPayQrcode paramAAAWechatPayQrcode);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAAGetWechatPayQrcodeTask
 * JD-Core Version:    0.6.2
 */