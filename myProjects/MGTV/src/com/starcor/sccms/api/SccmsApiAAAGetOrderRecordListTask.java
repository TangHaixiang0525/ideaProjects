package com.starcor.sccms.api;

import com.starcor.core.domain.AAAOrderRecordList;
import com.starcor.core.epgapi.params.AAAGetOrderRecordListParams;
import com.starcor.core.parser.json.AAAGetOrderRecordListSAXParserJson;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;

public class SccmsApiAAAGetOrderRecordListTask extends ServerApiTask
{
  private int businessId = -1;
  private int cate = -1;
  private String license = "";
  private ISccmsApiAAAGetOrderRecordListTaskListener lsr;
  private int pageIndex = -1;
  private int pageNum = -1;
  private String ticket = "";

  public SccmsApiAAAGetOrderRecordListTask(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.license = paramString1;
    this.ticket = paramString2;
    this.businessId = paramInt1;
    this.pageIndex = paramInt2;
    this.pageNum = paramInt3;
    this.cate = paramInt4;
  }

  public String getTaskName()
  {
    return "N212_A_13";
  }

  public String getUrl()
  {
    AAAGetOrderRecordListParams localAAAGetOrderRecordListParams = new AAAGetOrderRecordListParams(this.license, this.ticket, this.businessId, this.pageIndex, this.pageNum, this.cate);
    localAAAGetOrderRecordListParams.setResultFormat(1);
    String str = webUrlFormatter.i().formatUrl(localAAAGetOrderRecordListParams.toString(), localAAAGetOrderRecordListParams.getApiName());
    Logger.i("SccmsApiAAAGetOrderRecordListTask", "N212_A_13 url:" + str);
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
      AAAOrderRecordList localAAAOrderRecordList = (AAAOrderRecordList)new AAAGetOrderRecordListSAXParserJson().parser(paramSCResponse.getContents());
      Logger.i("SccmsApiAAAGetOrderRecordListTask", "N212_A_13 result:" + localAAAOrderRecordList.toString());
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localAAAOrderRecordList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiAAAGetOrderRecordListTaskListener paramISccmsApiAAAGetOrderRecordListTaskListener)
  {
    this.lsr = paramISccmsApiAAAGetOrderRecordListTaskListener;
  }

  public static abstract interface ISccmsApiAAAGetOrderRecordListTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAOrderRecordList paramAAAOrderRecordList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiAAAGetOrderRecordListTask
 * JD-Core Version:    0.6.2
 */