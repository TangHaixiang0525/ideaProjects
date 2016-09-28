package com.starcor.sccms.api;

import com.starcor.config.DeviceInfo;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.hunan.service.SystemTimeManager;
import com.starcor.mgtv.boss.WebUrlBuilder;
import com.starcor.server.api.manage.ServerApiTask;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class SccmsApiReportErrorTask extends ServerApiTask
{
  private static final String TAG = SccmsApiReportErrorTask.class.getSimpleName();
  private String at;
  private String cid;
  private String ctid;
  private String err;
  private String perr;
  private String sid;
  private String url;
  private String videoId;

  public SccmsApiReportErrorTask(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    this.err = paramString1;
    this.perr = paramString2;
    this.url = paramString3;
    this.sid = paramString4;
    this.at = paramString5;
    this.cid = paramString6;
    this.ctid = paramString7;
    this.videoId = paramString8;
  }

  public int getConnectTimeOutMs()
  {
    return 3000;
  }

  public List<NameValuePair> getPostForm()
  {
    String str = new SimpleDateFormat("yyyyMMddHHmmss").format(Long.valueOf(SystemTimeManager.getCurrentServerTime()));
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new BasicNameValuePair("ua", DeviceInfo.getMGTVVersion()));
    localArrayList.add(new BasicNameValuePair("time", str));
    localArrayList.add(new BasicNameValuePair("guid", DeviceInfo.getMac().replace("-", "")));
    localArrayList.add(new BasicNameValuePair("bid", "7.0.1"));
    localArrayList.add(new BasicNameValuePair("uid", GlobalLogic.getInstance().getUserId()));
    localArrayList.add(new BasicNameValuePair("net", "5"));
    localArrayList.add(new BasicNameValuePair("rd", String.valueOf(new Random().nextInt(1000))));
    localArrayList.add(new BasicNameValuePair("isdebug", "1"));
    localArrayList.add(new BasicNameValuePair("ra", ""));
    localArrayList.add(new BasicNameValuePair("act", "error"));
    localArrayList.add(new BasicNameValuePair("err", String.valueOf(this.err)));
    localArrayList.add(new BasicNameValuePair("perr", String.valueOf(this.perr)));
    localArrayList.add(new BasicNameValuePair("url", this.url));
    localArrayList.add(new BasicNameValuePair("sid", String.valueOf(this.sid)));
    localArrayList.add(new BasicNameValuePair("at", String.valueOf(this.at)));
    localArrayList.add(new BasicNameValuePair("cid", String.valueOf(this.cid)));
    localArrayList.add(new BasicNameValuePair("ctid", String.valueOf(this.ctid)));
    localArrayList.add(new BasicNameValuePair("vid", this.videoId));
    Logger.d(TAG, "nameValuePairArrayList -- > " + localArrayList.toString());
    return localArrayList;
  }

  public int getRequestMethod()
  {
    return 1;
  }

  public int getRetryTimes()
  {
    return 1;
  }

  public String getTaskName()
  {
    return "SccmsApiReportErrorTask";
  }

  public String getUrl()
  {
    return WebUrlBuilder.getAdInfoReportErrorUrl();
  }

  public void perform(SCResponse paramSCResponse)
  {
    super.perform(paramSCResponse);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiReportErrorTask
 * JD-Core Version:    0.6.2
 */