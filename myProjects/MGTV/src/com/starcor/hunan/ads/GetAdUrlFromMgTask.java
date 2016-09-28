package com.starcor.hunan.ads;

import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.mgtv.boss.WebUrlBuilder;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.server.api.manage.ServerApiTools;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class GetAdUrlFromMgTask extends ServerApiTask
{
  private static final String TAG = GetAdInfoTask.class.getSimpleName();
  private String aid = "";
  private String clipType;
  private String hid;
  private String hname;
  private int init;
  private int isPay;
  private int isPreview;
  private String keyword;
  private IMGCmsGetAdInfoTaskListener lsnr;
  private String name;
  private String onDate;
  private String pId = "";
  private String rid;
  private String subType;
  private String title;
  private ADTYPE type = ADTYPE.VIDEO_AD;
  private String url;
  private String videoId;
  private int vtt;

  public GetAdUrlFromMgTask(String paramString)
  {
    this.pId = paramString;
    this.type = ADTYPE.BOOT_AD;
  }

  public GetAdUrlFromMgTask(String paramString1, String paramString2, int paramInt1, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, int paramInt2, int paramInt3, int paramInt4)
  {
    this.videoId = paramString3;
    this.hid = paramString5;
    this.rid = paramString7;
    this.url = paramString8;
    this.title = paramString9;
    this.keyword = paramString10;
    this.onDate = paramString11;
    this.subType = paramString12;
    this.clipType = paramString13;
    this.vtt = paramInt2;
    this.pId = paramString1;
    this.aid = paramString2;
    this.init = paramInt1;
    this.name = paramString4;
    this.hname = paramString6;
    this.isPay = paramInt3;
    this.isPreview = paramInt4;
  }

  public int getConnectTimeOutMs()
  {
    return 3000;
  }

  public List<NameValuePair> getPostForm()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new BasicNameValuePair("p", BuildRequestParams.buildPPramasJsonString(this.pId, this.aid, this.init)));
    if (this.type == ADTYPE.VIDEO_AD)
    {
      BasicNameValuePair localBasicNameValuePair = new BasicNameValuePair("v", BuildRequestParams.buildVideoJsonInfo(this.videoId, this.name, this.hid, this.hname, this.rid, this.url, this.title, this.keyword, this.onDate, this.subType, this.clipType, this.vtt, this.isPay, this.isPreview));
      localArrayList.add(localBasicNameValuePair);
      localArrayList.add(new BasicNameValuePair("_type_object", "p,v"));
    }
    while (true)
    {
      Logger.i(TAG, "nameValuePairArrayList-->" + localArrayList.toString());
      return localArrayList;
      localArrayList.add(new BasicNameValuePair("_type_object", "p"));
    }
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
    return "GetAdUrlFromMgTask";
  }

  public String getUrl()
  {
    if (this.type == ADTYPE.VIDEO_AD)
      return WebUrlBuilder.getAdInfoUrl();
    return GlobalEnv.getInstance().getBootAdUrl();
  }

  public void perform(SCResponse paramSCResponse)
  {
    if (ServerApiTools.isSCResponseError(paramSCResponse))
    {
      this.lsnr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
      return;
    }
    try
    {
      JSONObject localJSONObject = new JSONObject(new String(paramSCResponse.getContents()));
      this.lsnr.onSuccess(new ServerApiTaskInfo(getTaskId(), paramSCResponse), localJSONObject.toString());
      return;
    }
    catch (Exception localException)
    {
      this.lsnr.onError(new ServerApiTaskInfo(getTaskId(), paramSCResponse), ServerApiTools.buildCommonError(paramSCResponse));
    }
  }

  public void setListener(IMGCmsGetAdInfoTaskListener paramIMGCmsGetAdInfoTaskListener)
  {
    this.lsnr = paramIMGCmsGetAdInfoTaskListener;
  }

  static enum ADTYPE
  {
    static
    {
      BOOT_AD = new ADTYPE("BOOT_AD", 1);
      ADTYPE[] arrayOfADTYPE = new ADTYPE[2];
      arrayOfADTYPE[0] = VIDEO_AD;
      arrayOfADTYPE[1] = BOOT_AD;
    }
  }

  public static abstract interface IMGCmsGetAdInfoTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, String paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.ads.GetAdUrlFromMgTask
 * JD-Core Version:    0.6.2
 */