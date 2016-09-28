package com.starcor.sccms.api;

import com.starcor.core.domain.InjectingID;
import com.starcor.core.epgapi.params.ConvertIDParams;
import com.starcor.core.parser.sax.ConvertIDSAXParser;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;

public class SccmsApiConvertIDTask extends ServerApiTask
{
  private ISccmsApiConvertIDTaskListener lsr;
  private String nns_ids;
  private String nns_index;
  private int nns_mode;
  private int nns_type;
  private int nns_video_type;

  public SccmsApiConvertIDTask(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3)
  {
    this.nns_ids = paramString1;
    this.nns_index = paramString2;
    this.nns_mode = paramInt1;
    this.nns_type = paramInt2;
    this.nns_video_type = paramInt3;
  }

  public String getTaskName()
  {
    return "N39_A_8";
  }

  public String getUrl()
  {
    ConvertIDParams localConvertIDParams = new ConvertIDParams(this.nns_ids, this.nns_index, this.nns_mode, this.nns_type, this.nns_video_type);
    localConvertIDParams.setResultFormat(0);
    return webUrlFormatter.i().formatUrl(localConvertIDParams.toString(), localConvertIDParams.getApiName());
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
    ConvertIDSAXParser localConvertIDSAXParser = new ConvertIDSAXParser();
    try
    {
      InjectingID localInjectingID = (InjectingID)localConvertIDSAXParser.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      Logger.i("ConvertIDSAXParser", " result:" + localInjectingID.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localInjectingID);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiConvertIDTaskListener paramISccmsApiConvertIDTaskListener)
  {
    this.lsr = paramISccmsApiConvertIDTaskListener;
  }

  public static abstract interface ISccmsApiConvertIDTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, InjectingID paramInjectingID);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiConvertIDTask
 * JD-Core Version:    0.6.2
 */