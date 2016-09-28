package com.starcor.sccms.api;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.MetadataGoup;
import com.starcor.core.epgapi.params.IniEPGURLParams;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.parser.sax.IniEPGUrlSAXParser;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class SccmsApiN3A2GetEpgDataTask extends ServerApiTask
{
  private ISccmsApiN3A2GetEpgDataTaskListener lsr;

  public String getTaskName()
  {
    return "N3_A_2";
  }

  public String getUrl()
  {
    IniEPGURLParams localIniEPGURLParams = new IniEPGURLParams(GlobalLogic.getInstance().getNnToken(), GlobalLogic.getInstance().getWebToken(), GlobalLogic.getInstance().getDeviceId(), GlobalLogic.getInstance().getUserId(), DeviceInfo.getMac());
    localIniEPGURLParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localIniEPGURLParams.toString(), localIniEPGURLParams.getApiName());
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
      ArrayList localArrayList = (ArrayList)new IniEPGUrlSAXParser().parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiN3A2GetEpgDataTaskListener paramISccmsApiN3A2GetEpgDataTaskListener)
  {
    this.lsr = paramISccmsApiN3A2GetEpgDataTaskListener;
  }

  public static abstract interface ISccmsApiN3A2GetEpgDataTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<MetadataGoup> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiN3A2GetEpgDataTask
 * JD-Core Version:    0.6.2
 */