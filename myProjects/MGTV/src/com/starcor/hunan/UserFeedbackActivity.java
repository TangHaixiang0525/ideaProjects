package com.starcor.hunan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;
import com.starcor.core.domain.GetUserFeedbackInfo;
import com.starcor.core.domain.GetUserFeedbackInfo.ResultDataInfo;
import com.starcor.core.domain.UserFeedbackAndDeviceInfo;
import com.starcor.core.domain.UserFeedbackInfoList;
import com.starcor.core.domain.UserFeedbackInfoList.UserFeedbackInfo;
import com.starcor.core.epgapi.params.UserFeedbackParams;
import com.starcor.core.parser.json.UserFeedbackInfoParse;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.uilogic.CommonUiTools;
import com.starcor.log.tools.LogCache;
import com.starcor.log.tools.LogCache.UploadCallBack;
import com.starcor.sccms.api.SccmsApiGetUserFeedbackInfoTask.ISccmsApiGetUserFeedbackInfoTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.ui.UITools;
import com.starcor.xul.Graphics.XulDrawable;
import com.starcor.xul.Utils.XulMemoryOutputStream;
import com.starcor.xul.XulPendingInputStream;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker;
import com.starcor.xul.XulWorker.DownloadItem;
import com.starcor.xul.XulWorker.XulDownloadOutputBuffer;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class UserFeedbackActivity extends XULActivity
{
  public static final int MESSAGE_CHECK_USER_FEEDBACK_FAILED = 1;
  public static final int MESSAGE_CHECK_USER_FEEDBACK_SUCCEESS = 0;
  public static final String TAG = UserFeedbackActivity.class.getSimpleName();
  public static final String XUL_PageId = "xulPageId";
  public static final String Xul_Data = "xulData";
  Handler _handler = new Handler(Looper.getMainLooper())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 0:
        Logger.i(UserFeedbackActivity.TAG, "MESSAGE_CHECK_USER_FEEDBACK_SUCCEESS=0");
        UserFeedbackActivity.this.dismissLoaddingDialog();
        UserFeedbackActivity.this.xulRefreshBinding("dimensional_barcode", "file:///.app/api/get_dimensional_barcode");
        return;
      case 1:
      }
      UserFeedbackActivity.this.dismissLoaddingDialog();
      UserFeedbackActivity.this.xulFireEvent("appEvents:qrcodeloadFailed", null);
      Logger.i(UserFeedbackActivity.TAG, "MESSAGE_CHECK_USER_FEEDBACK_FAILED=1");
    }
  };
  private String deviceId = "";
  private String feedbackContentId = "";
  public boolean hasDialog = false;
  private boolean isLoadSuccess = true;
  String pageId = null;
  private Boolean postStatus = Boolean.valueOf(false);
  private GetUserFeedbackInfo resultFeedbackInfo;
  Intent startIntent;
  private String upLoadFilePath = "";
  private String userFeedback = "";
  private String userFeedbackId = "";
  private ArrayList<UserFeedbackInfoList.UserFeedbackInfo> userFeedbackInfoIndex;
  private UserFeedbackParams userFeedbackParams = null;

  private void _init()
  {
    this.startIntent = getIntent();
  }

  private InputStream buildDimensionalBarCodeInfo(String paramString)
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "dimensional_barcode");
      writeNodeValue(localXmlSerializer, "url", "file:///.app/user_feedback/QR");
      XulWorker.addDrawableToCache("file:///.app/user_feedback/QR", XulDrawable.fromBitmap(CommonUiTools.createImage(paramString, App.OperationHeight(500), App.OperationHeight(500)), "file:///.app/user_feedback/QR", "file:///.app/user_feedback/QR"));
      localXmlSerializer.endTag(null, "dimensional_barcode");
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
      return localByteArrayInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private InputStream getDimensionalBarCodeInfo()
  {
    XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    if (this.userFeedbackParams == null)
      this.userFeedbackParams = new UserFeedbackParams(UserFeedbackAndDeviceInfo.getInstance().getDeviceIp(), this.userFeedbackId, this.resultFeedbackInfo.result_data.deviceId, this.resultFeedbackInfo.result_data.id);
    Logger.i(TAG, "url=" + this.userFeedbackParams.buildQrCodeInfo());
    localXulPendingInputStream.setBaseStream(buildDimensionalBarCodeInfo(this.userFeedbackParams.buildQrCodeInfo()));
    return localXulPendingInputStream;
  }

  private String getStartParam(String paramString)
  {
    String str = this._startIntent.getStringExtra(paramString);
    if (str == null)
      str = "";
    return str;
  }

  private InputStream getUserFeedbackInfo()
  {
    while (true)
    {
      int j;
      try
      {
        if ((this.userFeedbackInfoIndex != null) && (this.userFeedbackInfoIndex.size() > 0))
        {
          int i = this.userFeedbackInfoIndex.size();
          XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
          XulWorker.XulDownloadOutputBuffer localXulDownloadOutputBuffer = XulWorker.obtainDownloadBuffer(16384);
          localXmlSerializer.setOutput(localXulDownloadOutputBuffer, "UTF-8");
          localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
          localXmlSerializer.startTag(null, "pages");
          SparseArray localSparseArray = new SparseArray();
          ArrayList localArrayList = null;
          writeNodeValue(localXmlSerializer, "page_size", 1 + i / 12 + "");
          j = 0;
          if (j >= i)
            break label359;
          if (j % 12 == 0)
          {
            localArrayList = new ArrayList();
            localSparseArray.put(j / 12, localArrayList);
          }
          if (localArrayList != null)
          {
            localArrayList.add(this.userFeedbackInfoIndex.get(j));
            break label353;
            if (k < localSparseArray.size())
            {
              localXmlSerializer.startTag(null, "page");
              int m = 0;
              if (m < ((ArrayList)localSparseArray.get(k)).size())
              {
                UserFeedbackInfoList.UserFeedbackInfo localUserFeedbackInfo = (UserFeedbackInfoList.UserFeedbackInfo)((ArrayList)localSparseArray.get(k)).get(m);
                localXmlSerializer.startTag(null, "item");
                writeNodeValue(localXmlSerializer, "feedback_content", localUserFeedbackInfo.content);
                writeNodeValue(localXmlSerializer, "feedback_content_id", localUserFeedbackInfo.title);
                localXmlSerializer.endTag(null, "item");
                m++;
                continue;
              }
              localXmlSerializer.endTag(null, "page");
              k++;
              continue;
            }
            localXmlSerializer.endTag(null, "pages");
            localXmlSerializer.endDocument();
            localXmlSerializer.flush();
            InputStream localInputStream = localXulDownloadOutputBuffer.toInputStream();
            return localInputStream;
          }
        }
        else
        {
          return null;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      label353: j++;
      continue;
      label359: int k = 0;
    }
  }

  private boolean httpPostService(final boolean paramBoolean)
  {
    if (paramBoolean)
      showLoaddingDialog();
    this.upLoadFilePath = App.getInstance().getDir("userfeedback_post_logs", 0).toString();
    Map localMap;
    if ("FeedbackSuccessPage".equals(this.pageId))
    {
      localMap = UserFeedbackAndDeviceInfo.getInstance().getPostDataByType(0);
      localMap.put("questionInfo", this.userFeedbackId);
    }
    while (true)
    {
      String str = UserFeedbackParams.getUserFeedbackReportUrl();
      Logger.i(TAG, "postUrl=" + str.toString() + "  ,userFeedbackInfo=" + localMap.toString() + "   ,upLoadFilePath=" + this.upLoadFilePath.toString() + "  ,userFeedback=" + this.userFeedback);
      LogCache.getInstance().notifyUploadLogs(str, localMap, this.upLoadFilePath, new LogCache.UploadCallBack()
      {
        public void onError()
        {
          Logger.i(UserFeedbackActivity.TAG, "notifyUploadLogs onError");
          if (paramBoolean)
          {
            UserFeedbackActivity.access$302(UserFeedbackActivity.this, Boolean.valueOf(false));
            UserFeedbackActivity.this._handler.sendEmptyMessage(1);
          }
        }

        public void onSuccess(String paramAnonymousString)
        {
          Logger.i(UserFeedbackActivity.TAG, "notifyUploadLogs onSuccess");
          UserFeedbackActivity.access$202(UserFeedbackActivity.this, new UserFeedbackInfoParse().parseFeebbackReturnInfo(paramAnonymousString));
          if (paramBoolean)
          {
            UserFeedbackActivity.access$302(UserFeedbackActivity.this, Boolean.valueOf(true));
            UserFeedbackActivity.this._handler.sendEmptyMessage(0);
          }
        }
      });
      return true;
      boolean bool = "DeviceInfoPage".equals(this.pageId);
      localMap = null;
      if (bool)
        localMap = UserFeedbackAndDeviceInfo.getInstance().getPostDataByType(1);
    }
  }

  private void processLoadUserFeedbackInfo()
  {
    ServerApiManager.i().APIGetUserFeedbackInfoTask(String.valueOf(100), String.valueOf(0), new SccmsApiGetUserFeedbackInfoTask.ISccmsApiGetUserFeedbackInfoTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        UserFeedbackActivity.this.dismissLoaddingDialog();
        UserFeedbackActivity.access$002(UserFeedbackActivity.this, false);
        UserFeedbackActivity.this.reportLoad(UserFeedbackActivity.this.isLoadSuccess, null);
        UserFeedbackActivity.this.showErrorDialogWithReport(11, "1002008", "APIGetUserFeedbackInfoTask error.onError", paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserFeedbackInfoList paramAnonymousUserFeedbackInfoList)
      {
        UserFeedbackActivity.this.dismissLoaddingDialog();
        UserFeedbackActivity.access$002(UserFeedbackActivity.this, true);
        UserFeedbackActivity.access$102(UserFeedbackActivity.this, paramAnonymousUserFeedbackInfoList.lists);
        if ((UserFeedbackActivity.this.userFeedbackInfoIndex != null) && (UserFeedbackActivity.this.userFeedbackInfoIndex.size() != 0))
          UserFeedbackActivity.this.xulRefreshBinding("user_feedback_info", "file:///.app/api/get_user_feedback_info");
        UserFeedbackActivity.this.reportLoad(UserFeedbackActivity.this.isLoadSuccess, null);
      }
    });
  }

  protected void onCreate(Bundle paramBundle)
  {
    _init();
    super.onCreate(paramBundle);
    this.pageId = getStartParam("xulPageId");
    this.userFeedbackId = this.startIntent.getStringExtra("FeedbackContentId");
    this.userFeedback = this.startIntent.getStringExtra("FeedbackContent");
    Logger.d(TAG, "???/// pageId=" + this.pageId);
    initReportPageInfo(this.pageId);
    Logger.i(TAG, "userFeedbackId=" + this.userFeedbackId + "   ,userFeedback=" + this.userFeedback + ", deviceId=" + this.deviceId);
  }

  protected void onDestroy()
  {
    super.onDestroy();
  }

  protected void onRestart()
  {
    Logger.i("onRestart pageId=" + this.pageId);
    super.onRestart();
  }

  protected void onResume()
  {
    super.onResume();
  }

  protected void onStart()
  {
    super.onStart();
  }

  protected void onStop()
  {
    super.onStop();
    reportExit(this.isLoadSuccess, null);
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    Logger.i(TAG, "xulDoAction action=" + paramString1 + " type=" + paramString2 + " command" + paramString3 + " userdata=" + paramObject);
    try
    {
      JSONObject localJSONObject1 = new JSONObject(paramString3);
      localJSONObject2 = localJSONObject1;
      if ("data_empty".equals(paramString2))
      {
        UITools.ShowCustomToast(this.context, "请选择您遇到的问题");
        return true;
      }
    }
    catch (JSONException localJSONException)
    {
      JSONObject localJSONObject2;
      while (true)
      {
        localJSONException.printStackTrace();
        localJSONObject2 = null;
      }
      if ("startFeedbackResultPage".equals(paramString2))
      {
        if (localJSONObject2 != null)
        {
          this.feedbackContentId = localJSONObject2.optString("data_id");
          this.userFeedback = localJSONObject2.optString("data_content");
        }
        Intent localIntent = new Intent(this, UserFeedbackActivity.class);
        localIntent.putExtra("xulPageId", "FeedbackSuccessPage");
        localIntent.putExtra("FeedbackContentId", this.feedbackContentId);
        localIntent.putExtra("FeedbackContent", this.userFeedback);
        localIntent.addFlags(8388608);
        startActivity(localIntent);
        finish();
      }
      if ("startLoading".equals(paramString1))
      {
        showLoaddingDialog();
        return true;
      }
    }
    return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
  }

  protected InputStream xulGetAppData(XulWorker.DownloadItem paramDownloadItem, String paramString)
  {
    Logger.i(TAG, "xulGetAppData path=" + paramString);
    if ("api/get_user_feedback_info".equals(paramString))
      return getUserFeedbackInfo();
    if (("api/get_dimensional_barcode".equals(paramString)) && (this.postStatus.booleanValue()))
      return getDimensionalBarCodeInfo();
    return super.xulGetAppData(paramDownloadItem, paramString);
  }

  protected void xulOnRenderIsReady()
  {
    super.xulOnRenderIsReady();
    if ("FeedbackSuccessPage".equals(this.pageId))
    {
      reportLoad(this.isLoadSuccess, null);
      httpPostService(true);
      return;
    }
    if ("DeviceInfoPage".equals(this.pageId))
    {
      reportLoad(this.isLoadSuccess, null);
      httpPostService(false);
      return;
    }
    processLoadUserFeedbackInfo();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.UserFeedbackActivity
 * JD-Core Version:    0.6.2
 */