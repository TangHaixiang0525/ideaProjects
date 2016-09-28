package com.starcor.sccms.api;

import com.starcor.core.domain.CollectListItem;
import com.starcor.core.epgapi.params.UploadAllRecordsParams;
import com.starcor.core.parser.sax.GetCollectListV2SAXParse;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.webUrlFormatter;
import com.starcor.server.api.manage.ServerApiCachedTask;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class SccmsApiUploadAllCollectRecordTask extends ServerApiCachedTask
{
  ISccmsApiUploadAllCollectRecordTaskListener lsr;
  UploadAllRecordsParams para = null;

  public SccmsApiUploadAllCollectRecordTask(UploadAllRecordsParams paramUploadAllRecordsParams)
  {
    this.para = paramUploadAllRecordsParams;
    this.para.setResultFormat(0);
  }

  public List<NameValuePair> getPostForm()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new BasicNameValuePair("nns_sync_data", this.para.getAllRecordsList()));
    return localArrayList;
  }

  public int getRequestMethod()
  {
    return 1;
  }

  public String getTaskName()
  {
    return "N40_A_10";
  }

  public String getUrl()
  {
    return webUrlFormatter.i().formatUrl(this.para.toString(), this.para.getApiName());
  }

  public void perform(SCResponse paramSCResponse)
  {
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      Logger.i("SccmsApiUploadAllCollectRecordTask", " result = failure");
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    GetCollectListV2SAXParse localGetCollectListV2SAXParse = new GetCollectListV2SAXParse();
    try
    {
      ArrayList localArrayList = (ArrayList)localGetCollectListV2SAXParse.parser(new ByteArrayInputStream(paramSCResponse.getContents()));
      this.lsr.onSuccess(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), localArrayList);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      this.lsr.onError(new ServerApiTaskInfo(getTaskId(), getUrl(), paramSCResponse), ServerApiTools.buildParseError(paramSCResponse, localException.toString()));
    }
  }

  public void setListener(ISccmsApiUploadAllCollectRecordTaskListener paramISccmsApiUploadAllCollectRecordTaskListener)
  {
    this.lsr = paramISccmsApiUploadAllCollectRecordTaskListener;
  }

  public static abstract interface ISccmsApiUploadAllCollectRecordTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiUploadAllCollectRecordTask
 * JD-Core Version:    0.6.2
 */