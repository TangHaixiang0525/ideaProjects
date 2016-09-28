package com.starcor.sccms.api;

import com.starcor.core.utils.Tools;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;

public class SccmsApiGetGUIDTask extends ServerApiTask
{
  private static String TAG = "SccmsApiGetGUIDTask";
  private String host = "http://guid.hunantv.com/ott/distribute.do";
  private ISccmsApiGetGUIDDataTaskListener lsr;
  private String params = "deviceid=" + Tools.getMac();

  public String getTaskName()
  {
    return TAG;
  }

  public String getUrl()
  {
    return this.host + "?" + this.params;
  }

  // ERROR //
  public void perform(com.starcor.httpapi.core.SCResponse paramSCResponse)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 55	com/starcor/sccms/api/SccmsApiGetGUIDTask:lsr	Lcom/starcor/sccms/api/SccmsApiGetGUIDTask$ISccmsApiGetGUIDDataTaskListener;
    //   4: ifnull +7 -> 11
    //   7: aload_1
    //   8: ifnonnull +12 -> 20
    //   11: getstatic 16	com/starcor/sccms/api/SccmsApiGetGUIDTask:TAG	Ljava/lang/String;
    //   14: ldc 57
    //   16: invokestatic 63	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   19: return
    //   20: aconst_null
    //   21: astore_2
    //   22: aconst_null
    //   23: astore_3
    //   24: aload_1
    //   25: invokevirtual 69	com/starcor/httpapi/core/SCResponse:getContents	()[B
    //   28: ifnonnull +109 -> 137
    //   31: getstatic 16	com/starcor/sccms/api/SccmsApiGetGUIDTask:TAG	Ljava/lang/String;
    //   34: ldc 71
    //   36: invokestatic 63	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   39: return
    //   40: astore 5
    //   42: aload 5
    //   44: invokevirtual 74	java/io/UnsupportedEncodingException:printStackTrace	()V
    //   47: aload_0
    //   48: getfield 55	com/starcor/sccms/api/SccmsApiGetGUIDTask:lsr	Lcom/starcor/sccms/api/SccmsApiGetGUIDTask$ISccmsApiGetGUIDDataTaskListener;
    //   51: new 76	com/starcor/server/api/manage/ServerApiTaskInfo
    //   54: dup
    //   55: aload_0
    //   56: invokevirtual 80	com/starcor/sccms/api/SccmsApiGetGUIDTask:getTaskId	()I
    //   59: aload_0
    //   60: invokevirtual 82	com/starcor/sccms/api/SccmsApiGetGUIDTask:getUrl	()Ljava/lang/String;
    //   63: aload_1
    //   64: invokespecial 85	com/starcor/server/api/manage/ServerApiTaskInfo:<init>	(ILjava/lang/String;Lcom/starcor/httpapi/core/SCResponse;)V
    //   67: aload_1
    //   68: aload 5
    //   70: invokevirtual 86	java/io/UnsupportedEncodingException:toString	()Ljava/lang/String;
    //   73: invokestatic 92	com/starcor/server/api/manage/ServerApiTools:buildParseError	(Lcom/starcor/httpapi/core/SCResponse;Ljava/lang/String;)Lcom/starcor/server/api/manage/ServerApiCommonError;
    //   76: invokeinterface 98 3 0
    //   81: getstatic 16	com/starcor/sccms/api/SccmsApiGetGUIDTask:TAG	Ljava/lang/String;
    //   84: new 25	java/lang/StringBuilder
    //   87: dup
    //   88: invokespecial 26	java/lang/StringBuilder:<init>	()V
    //   91: ldc 100
    //   93: invokevirtual 32	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   96: aload_2
    //   97: invokevirtual 32	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   100: invokevirtual 41	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   103: invokestatic 63	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   106: aload_3
    //   107: ifnull +123 -> 230
    //   110: aload_0
    //   111: getfield 55	com/starcor/sccms/api/SccmsApiGetGUIDTask:lsr	Lcom/starcor/sccms/api/SccmsApiGetGUIDTask$ISccmsApiGetGUIDDataTaskListener;
    //   114: new 76	com/starcor/server/api/manage/ServerApiTaskInfo
    //   117: dup
    //   118: aload_0
    //   119: invokevirtual 80	com/starcor/sccms/api/SccmsApiGetGUIDTask:getTaskId	()I
    //   122: aload_0
    //   123: invokevirtual 82	com/starcor/sccms/api/SccmsApiGetGUIDTask:getUrl	()Ljava/lang/String;
    //   126: aload_1
    //   127: invokespecial 85	com/starcor/server/api/manage/ServerApiTaskInfo:<init>	(ILjava/lang/String;Lcom/starcor/httpapi/core/SCResponse;)V
    //   130: aload_3
    //   131: invokeinterface 104 3 0
    //   136: return
    //   137: new 106	java/lang/String
    //   140: dup
    //   141: aload_1
    //   142: invokevirtual 69	com/starcor/httpapi/core/SCResponse:getContents	()[B
    //   145: ldc 108
    //   147: invokespecial 111	java/lang/String:<init>	([BLjava/lang/String;)V
    //   150: astore 6
    //   152: aconst_null
    //   153: astore_3
    //   154: aload 6
    //   156: ifnull +22 -> 178
    //   159: new 113	org/json/JSONObject
    //   162: dup
    //   163: aload 6
    //   165: invokespecial 116	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   168: ldc 118
    //   170: invokevirtual 122	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   173: astore 7
    //   175: aload 7
    //   177: astore_3
    //   178: aload 6
    //   180: astore_2
    //   181: goto -100 -> 81
    //   184: astore 4
    //   186: aload 4
    //   188: invokevirtual 123	org/json/JSONException:printStackTrace	()V
    //   191: aload_0
    //   192: getfield 55	com/starcor/sccms/api/SccmsApiGetGUIDTask:lsr	Lcom/starcor/sccms/api/SccmsApiGetGUIDTask$ISccmsApiGetGUIDDataTaskListener;
    //   195: new 76	com/starcor/server/api/manage/ServerApiTaskInfo
    //   198: dup
    //   199: aload_0
    //   200: invokevirtual 80	com/starcor/sccms/api/SccmsApiGetGUIDTask:getTaskId	()I
    //   203: aload_0
    //   204: invokevirtual 82	com/starcor/sccms/api/SccmsApiGetGUIDTask:getUrl	()Ljava/lang/String;
    //   207: aload_1
    //   208: invokespecial 85	com/starcor/server/api/manage/ServerApiTaskInfo:<init>	(ILjava/lang/String;Lcom/starcor/httpapi/core/SCResponse;)V
    //   211: aload_1
    //   212: aload 4
    //   214: invokevirtual 124	org/json/JSONException:toString	()Ljava/lang/String;
    //   217: invokestatic 92	com/starcor/server/api/manage/ServerApiTools:buildParseError	(Lcom/starcor/httpapi/core/SCResponse;Ljava/lang/String;)Lcom/starcor/server/api/manage/ServerApiCommonError;
    //   220: invokeinterface 98 3 0
    //   225: aconst_null
    //   226: astore_3
    //   227: goto -146 -> 81
    //   230: aload_0
    //   231: getfield 55	com/starcor/sccms/api/SccmsApiGetGUIDTask:lsr	Lcom/starcor/sccms/api/SccmsApiGetGUIDTask$ISccmsApiGetGUIDDataTaskListener;
    //   234: new 76	com/starcor/server/api/manage/ServerApiTaskInfo
    //   237: dup
    //   238: aload_0
    //   239: invokevirtual 80	com/starcor/sccms/api/SccmsApiGetGUIDTask:getTaskId	()I
    //   242: aload_0
    //   243: invokevirtual 82	com/starcor/sccms/api/SccmsApiGetGUIDTask:getUrl	()Ljava/lang/String;
    //   246: aload_1
    //   247: invokespecial 85	com/starcor/server/api/manage/ServerApiTaskInfo:<init>	(ILjava/lang/String;Lcom/starcor/httpapi/core/SCResponse;)V
    //   250: aconst_null
    //   251: invokeinterface 98 3 0
    //   256: return
    //   257: astore 4
    //   259: aload 6
    //   261: astore_2
    //   262: goto -76 -> 186
    //   265: astore 5
    //   267: aload 6
    //   269: astore_2
    //   270: goto -228 -> 42
    //
    // Exception table:
    //   from	to	target	type
    //   24	39	40	java/io/UnsupportedEncodingException
    //   137	152	40	java/io/UnsupportedEncodingException
    //   24	39	184	org/json/JSONException
    //   137	152	184	org/json/JSONException
    //   159	175	257	org/json/JSONException
    //   159	175	265	java/io/UnsupportedEncodingException
  }

  public void setListener(ISccmsApiGetGUIDDataTaskListener paramISccmsApiGetGUIDDataTaskListener)
  {
    this.lsr = paramISccmsApiGetGUIDDataTaskListener;
  }

  public static abstract interface ISccmsApiGetGUIDDataTaskListener
  {
    public abstract void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError);

    public abstract void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, String paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.sccms.api.SccmsApiGetGUIDTask
 * JD-Core Version:    0.6.2
 */