package com.starcor.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Message;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.domain.AAAGetLicense;
import com.starcor.core.domain.BootAdData;
import com.starcor.core.domain.BootAdData.Data;
import com.starcor.core.domain.CollectListItem;
import com.starcor.core.domain.DrmReportDecodeCapacityInfo;
import com.starcor.core.domain.GetSecretKeysInfo;
import com.starcor.core.domain.MetadataGoup;
import com.starcor.core.domain.PlayBillRecommendStrut;
import com.starcor.core.domain.PublicImage;
import com.starcor.core.domain.RecordList;
import com.starcor.core.domain.RecordList.OnRemoveCollectListListener;
import com.starcor.core.domain.RecordList.OnRemovePlayListListener;
import com.starcor.core.domain.RecordList.OnRemoveTracePlayListListener;
import com.starcor.core.domain.UserCenterLogin;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.epgapi.params.AddCollectV2Params;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.logic.UserCPLLogic;
import com.starcor.core.utils.DeviceIdentifier;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCHttpApiTask;
import com.starcor.hunan.App;
import com.starcor.hunan.SplashActivity;
import com.starcor.hunan.ads.GetAdUrlFromMgTask.IMGCmsGetAdInfoTaskListener;
import com.starcor.hunan.domain.LocalMediaDataManage;
import com.starcor.hunan.domain.LocalMediaDataManage.MediaDateType;
import com.starcor.hunan.opendownload.encrypt.EncryptLogic;
import com.starcor.hunan.service.SystemTimeManager;
import com.starcor.media.player.MplayerAdPlayerView;
import com.starcor.media.player.MplayerAdPlayerView.ErrorParams;
import com.starcor.sccms.api.SccmsApiAAAGetLicenseTask.ISccmsApiGetLicenseTaskListener;
import com.starcor.sccms.api.SccmsApiAddPlayRecordV2Task.ISccmsApiAddPlayRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiDrmReportDecodeCapacityTask.ISccmsApiReportDecodeCapacityTaskListener;
import com.starcor.sccms.api.SccmsApiGetCatchVideoRecordV2Task.ISccmsApiGetCatchVideoRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetCollectRecordV2Task.ISccmsApiGetCollectRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetEpgIndexTask.ISccmsApiGetEpgDataTaskListener;
import com.starcor.sccms.api.SccmsApiGetGUIDTask.ISccmsApiGetGUIDDataTaskListener;
import com.starcor.sccms.api.SccmsApiGetInitMetaDataTask.ISccmsApiGetInitMetaDataTaskListener;
import com.starcor.sccms.api.SccmsApiGetPlayRecordV2Task.ISccmsApiGetPlayRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetPublicImageTask.ISccmsApiGetPublicImageTaskListener;
import com.starcor.sccms.api.SccmsApiGetReplayRecommendDataTask.ISccmsApiGetReplayRecommendDataTaskListener;
import com.starcor.sccms.api.SccmsApiGetSecretKeysTask.ISccmsApiGetSecretKeysTaskListener;
import com.starcor.sccms.api.SccmsApiN3A2GetEpgDataTask.ISccmsApiN3A2GetEpgDataTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterVerifyTokenTask.ISccmsApiUserCenterVerifyTokenTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.XulWorker;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.util.EntityUtils;

public class InitApiTask
{
  private static final String TAG = InitApiTask.class.getSimpleName();
  private static ArrayList<InitApiResultListener> lsrs = new ArrayList();
  TaskScheduler scheduler = new TaskScheduler()
  {
    public void onTaskError(TaskScheduler.Task paramAnonymousTask)
    {
      if ((paramAnonymousTask instanceof InitApiTask.APITask))
        InitApiTask.handleAPITaskError((InitApiTask.APITask)paramAnonymousTask);
    }
  };
  private TaskInfo[] taskMap;

  public InitApiTask()
  {
    TaskInfo[] arrayOfTaskInfo = new TaskInfo[18];
    arrayOfTaskInfo[0] = new TaskInfo(new InitAppTask(this.scheduler), new String[0]);
    APIGetSecretKeysTask localAPIGetSecretKeysTask = new APIGetSecretKeysTask(this.scheduler);
    String[] arrayOfString1 = new String[1];
    arrayOfString1[0] = InitAppTask.TASK_NAME;
    arrayOfTaskInfo[1] = new TaskInfo(localAPIGetSecretKeysTask, arrayOfString1);
    APIN1A1Task localAPIN1A1Task = new APIN1A1Task(this.scheduler);
    String[] arrayOfString2 = new String[2];
    arrayOfString2[0] = APIGetSecretKeysTask.TASK_NAME;
    arrayOfString2[1] = InitAppTask.TASK_NAME;
    arrayOfTaskInfo[2] = new TaskInfo(localAPIN1A1Task, arrayOfString2);
    APICheckUserInfoTask localAPICheckUserInfoTask = new APICheckUserInfoTask(this.scheduler);
    String[] arrayOfString3 = new String[1];
    arrayOfString3[0] = APIN1A1Task.TASK_NAME;
    arrayOfTaskInfo[3] = new TaskInfo(localAPICheckUserInfoTask, arrayOfString3);
    APIN3A2GetEpgDataTask localAPIN3A2GetEpgDataTask = new APIN3A2GetEpgDataTask(this.scheduler);
    String[] arrayOfString4 = new String[1];
    arrayOfString4[0] = APIN1A1Task.TASK_NAME;
    arrayOfTaskInfo[4] = new TaskInfo(localAPIN3A2GetEpgDataTask, arrayOfString4);
    APIGetInitMetaDataTask localAPIGetInitMetaDataTask = new APIGetInitMetaDataTask(this.scheduler);
    String[] arrayOfString5 = new String[1];
    arrayOfString5[0] = APIN1A1Task.TASK_NAME;
    arrayOfTaskInfo[5] = new TaskInfo(localAPIGetInitMetaDataTask, arrayOfString5);
    UserCenterVerifyTokenTask localUserCenterVerifyTokenTask = new UserCenterVerifyTokenTask(this.scheduler);
    String[] arrayOfString6 = new String[1];
    arrayOfString6[0] = APIN1A1Task.TASK_NAME;
    arrayOfTaskInfo[6] = new TaskInfo(localUserCenterVerifyTokenTask, arrayOfString6);
    APICheckPlayListTask localAPICheckPlayListTask = new APICheckPlayListTask(this.scheduler);
    String[] arrayOfString7 = new String[2];
    arrayOfString7[0] = APIN1A1Task.TASK_NAME;
    arrayOfString7[1] = UserCenterVerifyTokenTask.TASK_NAME;
    arrayOfTaskInfo[7] = new TaskInfo(localAPICheckPlayListTask, arrayOfString7);
    APIDelAllRecords localAPIDelAllRecords = new APIDelAllRecords(this.scheduler);
    String[] arrayOfString8 = new String[2];
    arrayOfString8[0] = APIN1A1Task.TASK_NAME;
    arrayOfString8[1] = APICheckPlayListTask.TASK_NAME;
    arrayOfTaskInfo[8] = new TaskInfo(localAPIDelAllRecords, arrayOfString8);
    GETGUIDTask localGETGUIDTask = new GETGUIDTask(this.scheduler);
    String[] arrayOfString9 = new String[1];
    arrayOfString9[0] = APIN1A1Task.TASK_NAME;
    arrayOfTaskInfo[9] = new TaskInfo(localGETGUIDTask, arrayOfString9);
    StartMainActivityTask localStartMainActivityTask = new StartMainActivityTask(this.scheduler);
    String[] arrayOfString10 = new String[6];
    arrayOfString10[0] = APIGetInitMetaDataTask.TASK_NAME;
    arrayOfString10[1] = APICheckUserInfoTask.TASK_NAME;
    arrayOfString10[2] = APIN3A2GetEpgDataTask.TASK_NAME;
    arrayOfString10[3] = APICheckPlayListTask.TASK_NAME;
    arrayOfString10[4] = APIDelAllRecords.TASK_NAME;
    arrayOfString10[5] = GETGUIDTask.TASK_NAME;
    arrayOfTaskInfo[10] = new TaskInfo(localStartMainActivityTask, arrayOfString10);
    APIGetPlayListTask localAPIGetPlayListTask = new APIGetPlayListTask(this.scheduler);
    String[] arrayOfString11 = new String[2];
    arrayOfString11[0] = StartMainActivityTask.TASK_NAME;
    arrayOfString11[1] = APIDelAllRecords.TASK_NAME;
    arrayOfTaskInfo[11] = new TaskInfo(localAPIGetPlayListTask, arrayOfString11);
    APIGetCollectListTask localAPIGetCollectListTask = new APIGetCollectListTask(this.scheduler);
    String[] arrayOfString12 = new String[2];
    arrayOfString12[0] = StartMainActivityTask.TASK_NAME;
    arrayOfString12[1] = APIDelAllRecords.TASK_NAME;
    arrayOfTaskInfo[12] = new TaskInfo(localAPIGetCollectListTask, arrayOfString12);
    APIGetAfterPlayListTask localAPIGetAfterPlayListTask = new APIGetAfterPlayListTask(this.scheduler);
    String[] arrayOfString13 = new String[2];
    arrayOfString13[0] = StartMainActivityTask.TASK_NAME;
    arrayOfString13[1] = APIDelAllRecords.TASK_NAME;
    arrayOfTaskInfo[13] = new TaskInfo(localAPIGetAfterPlayListTask, arrayOfString13);
    APIGetRecommendListTask localAPIGetRecommendListTask = new APIGetRecommendListTask(this.scheduler);
    String[] arrayOfString14 = new String[1];
    arrayOfString14[0] = StartMainActivityTask.TASK_NAME;
    arrayOfTaskInfo[14] = new TaskInfo(localAPIGetRecommendListTask, arrayOfString14);
    APIDrmReportDecodeCapacityTask localAPIDrmReportDecodeCapacityTask = new APIDrmReportDecodeCapacityTask(this.scheduler);
    String[] arrayOfString15 = new String[1];
    arrayOfString15[0] = StartMainActivityTask.TASK_NAME;
    arrayOfTaskInfo[15] = new TaskInfo(localAPIDrmReportDecodeCapacityTask, arrayOfString15);
    APIGetTerminalIconTask localAPIGetTerminalIconTask = new APIGetTerminalIconTask(this.scheduler);
    String[] arrayOfString16 = new String[1];
    arrayOfString16[0] = APIN1A1Task.TASK_NAME;
    arrayOfTaskInfo[16] = new TaskInfo(localAPIGetTerminalIconTask, arrayOfString16);
    APIGetBootAdImageTask localAPIGetBootAdImageTask = new APIGetBootAdImageTask(this.scheduler);
    String[] arrayOfString17 = new String[1];
    arrayOfString17[0] = StartMainActivityTask.TASK_NAME;
    arrayOfTaskInfo[17] = new TaskInfo(localAPIGetBootAdImageTask, arrayOfString17);
    this.taskMap = arrayOfTaskInfo;
    for (int i = 0; i < this.taskMap.length; i++)
    {
      TaskInfo localTaskInfo2 = this.taskMap[i];
      this.scheduler.addTask(localTaskInfo2.task);
    }
    for (int j = 0; j < this.taskMap.length; j++)
    {
      TaskInfo localTaskInfo1 = this.taskMap[j];
      this.scheduler.addTaskDependencis(localTaskInfo1.task, localTaskInfo1.dependencies);
    }
  }

  private static void handleAPITaskCompleted()
  {
    if (lsrs == null)
      return;
    Iterator localIterator = lsrs.iterator();
    while (localIterator.hasNext())
    {
      InitApiResultListener localInitApiResultListener = (InitApiResultListener)localIterator.next();
      if (localInitApiResultListener != null)
        localInitApiResultListener.onSuccess(null);
    }
    Logger.i(TAG, "启动完成");
    lsrs.clear();
  }

  private static void handleAPITaskError(APITask paramAPITask)
  {
    if ((paramAPITask != null) && (lsrs != null))
    {
      Iterator localIterator = lsrs.iterator();
      while (localIterator.hasNext())
      {
        InitApiResultListener localInitApiResultListener = (InitApiResultListener)localIterator.next();
        if (localInitApiResultListener != null)
          localInitApiResultListener.onError(paramAPITask.errorCode());
      }
      lsrs.clear();
    }
    Logger.i(TAG, "API出现错误");
  }

  public void setTaskListener(InitApiResultListener paramInitApiResultListener)
  {
    if (paramInitApiResultListener == null)
    {
      Logger.e(TAG, "InitApiResultListener is null, do Nothing !!!");
      return;
    }
    lsrs.add(paramInitApiResultListener);
  }

  public void startTask()
  {
    new Thread()
    {
      public void run()
      {
        InitApiTask.this.scheduler.runTask();
      }
    }
    .start();
  }

  private static class APICheckPlayListTask extends InitApiTask.APITask
  {
    public static final String TASK_NAME = APICheckPlayListTask.class.getSimpleName();

    public APICheckPlayListTask(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    public void start()
    {
      super.start();
      String str1 = GlobalLogic.getInstance().getLatestVideoInfo("video_id");
      if (TextUtils.isEmpty(str1))
      {
        Logger.d(InitApiTask.TAG, "APICheckPlayListTask return");
        this.taskId = 2147483646;
        notifyFinish();
        return;
      }
      String str2 = GlobalLogic.getInstance().getLatestVideoInfo("video_name");
      int i = 0;
      int j = 0;
      try
      {
        i = Integer.valueOf(GlobalLogic.getInstance().getLatestVideoInfo("video_index")).intValue();
        j = Integer.valueOf(GlobalLogic.getInstance().getLatestVideoInfo("played_time")).intValue();
        int m = Integer.valueOf(GlobalLogic.getInstance().getLatestVideoInfo("uiStyle")).intValue();
        k = m;
        AddCollectV2Params localAddCollectV2Params = new AddCollectV2Params(2, GlobalLogic.getInstance().getLatestVideoInfo("video_id"), str2, 0, i, j, GlobalLogic.getInstance().getLatestVideoInfo("ts_day"), GlobalLogic.getInstance().getLatestVideoInfo("ts_begin"), GlobalLogic.getInstance().getLatestVideoInfo("ts_time_len"), GlobalLogic.getInstance().getLatestVideoInfo("media_assets_id"), GlobalLogic.getInstance().getLatestVideoInfo("category_id"), k);
        localAddCollectV2Params.getVideo_online_time().setValue(GlobalLogic.getInstance().getLatestVideoInfo("current_index_release_time"));
        CollectListItem localCollectListItem = new CollectListItem();
        localCollectListItem.video_id = str1;
        localCollectListItem.video_index = i;
        localCollectListItem.video_name = str2;
        localCollectListItem.played_time = j;
        localCollectListItem.duration = Integer.parseInt(GlobalLogic.getInstance().getLatestVideoInfo("ts_time_len"));
        UserCPLLogic.getInstance().addPlayRecordLocal(localCollectListItem);
        Logger.d(InitApiTask.TAG, "APICheckPlayListTask");
        this.taskId = ServerApiManager.i().APIAddPlayRecordV2(localAddCollectV2Params, new SccmsApiAddPlayRecordV2Task.ISccmsApiAddPlayRecordV2TaskListener()
        {
          public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
          {
            InitApiTask.APICheckPlayListTask.this.notifyFinish();
          }

          public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<CollectListItem> paramAnonymousArrayList)
          {
            Logger.d(InitApiTask.TAG, "APICheckPlayListTask onSuccess");
            GlobalLogic.getInstance().clearLatestVideoInfo();
            InitApiTask.APICheckPlayListTask.this.notifyFinish();
          }
        });
        return;
      }
      catch (Exception localException)
      {
        while (true)
        {
          Logger.d(InitApiTask.TAG, "APICheckPlayListTask Integer");
          int k = 0;
        }
      }
    }
  }

  private static class APICheckUserInfoTask extends InitApiTask.APITask
  {
    public static final String TASK_NAME = APICheckUserInfoTask.class.getSimpleName();

    public APICheckUserInfoTask(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    private void getLisences()
    {
      String str = DeviceIdentifier.getAAADeviceId(App.getAppContext().getApplicationContext());
      Logger.i(InitApiTask.TAG, "deviceId:" + str);
      this.taskId = ServerApiManager.i().APIAAAGetLicense(str, new SccmsApiGetLicenseTaskListener(null));
    }

    public void start()
    {
      super.start();
      getLisences();
    }

    private class SccmsApiGetLicenseTaskListener
      implements SccmsApiAAAGetLicenseTask.ISccmsApiGetLicenseTaskListener
    {
      private SccmsApiGetLicenseTaskListener()
      {
      }

      public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
      {
        Logger.i(InitApiTask.TAG, "SccmsApiGetLicenseTaskListener.onError() error");
        InitApiTask.APICheckUserInfoTask.this.setErrorCode(ScHunanOTTQuickStartCore.Error.ERROR_LICENSE);
        InitApiTask.APICheckUserInfoTask.this.notifyError();
      }

      public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAGetLicense paramAAAGetLicense)
      {
        Logger.i(InitApiTask.TAG, "SccmsApiGetLicenseTaskListener.onSuccess()");
        if (TextUtils.isEmpty(paramAAAGetLicense.license))
        {
          InitApiTask.APICheckUserInfoTask.this.setErrorCode(ScHunanOTTQuickStartCore.Error.ERROR_LICENSE);
          InitApiTask.APICheckUserInfoTask.this.notifyError();
          Logger.e(InitApiTask.TAG, "SccmsApiGetLicenseTaskListener.onSuccess() result.license:" + paramAAAGetLicense.license);
          return;
        }
        GlobalEnv.getInstance().setAAALicense(paramAAAGetLicense.license);
        GlobalEnv.getInstance().setAAANetIp(paramAAAGetLicense.ip);
        GlobalLogic.getInstance().setNetId(paramAAAGetLicense.netId);
        InitApiTask.APICheckUserInfoTask.this.notifyFinish();
      }
    }
  }

  private static class APIDelAllRecords extends InitApiTask.APITask
  {
    public static final String TASK_NAME = APIDelAllRecords.class.getSimpleName();
    private SharedPreferences sharedata;

    public APIDelAllRecords(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    private void delCollectAndTrace()
    {
      RecordList.getInstance().removeAllCollectionListInfo(new RecordList.OnRemoveCollectListListener()
      {
        public void onError()
        {
          RecordList.getInstance().removeAllTracePlayListInfo(new RecordList.OnRemoveTracePlayListListener()
          {
            public void onError()
            {
              SharedPreferences.Editor localEditor = InitApiTask.APIDelAllRecords.this.sharedata.edit();
              localEditor.putBoolean("existFlag", true);
              localEditor.commit();
              InitApiTask.APIDelAllRecords.this.notifyFinish();
            }

            public void onSuccess()
            {
              SharedPreferences.Editor localEditor = InitApiTask.APIDelAllRecords.this.sharedata.edit();
              localEditor.putBoolean("existFlag", true);
              localEditor.commit();
              InitApiTask.APIDelAllRecords.this.notifyFinish();
            }
          });
        }

        public void onSuccess()
        {
          Logger.i(InitApiTask.TAG, "removeAllCollectionListInfo onSuccess");
          RecordList.getInstance().removeAllTracePlayListInfo(new RecordList.OnRemoveTracePlayListListener()
          {
            public void onError()
            {
              SharedPreferences.Editor localEditor = InitApiTask.APIDelAllRecords.this.sharedata.edit();
              localEditor.putBoolean("existFlag", true);
              localEditor.commit();
              InitApiTask.APIDelAllRecords.this.notifyFinish();
            }

            public void onSuccess()
            {
              Logger.i(InitApiTask.TAG, "removeAllTracePlayListInfo onSuccess");
              SharedPreferences.Editor localEditor = InitApiTask.APIDelAllRecords.this.sharedata.edit();
              localEditor.putBoolean("existFlag", true);
              localEditor.commit();
              InitApiTask.APIDelAllRecords.this.notifyFinish();
            }
          });
        }
      });
    }

    public void start()
    {
      super.start();
      this.taskId = 200;
      this.sharedata = App.getAppContext().getSharedPreferences("factoryset", 0);
      boolean bool = this.sharedata.getBoolean("existFlag", false);
      if ((AppFuncCfg.FUNCTION_RESTORE_FACTOR_SETTINGS_CLEAN_DATA) && (!bool) && (!GlobalLogic.getInstance().isUserLogined()))
      {
        RecordList.getInstance().removeAllPlayListInfo(new RecordList.OnRemovePlayListListener()
        {
          public void onError()
          {
            Logger.i(InitApiTask.TAG, "removeAllPlayListInfo onError");
            InitApiTask.APIDelAllRecords.this.delCollectAndTrace();
          }

          public void onSuccess()
          {
            Logger.i(InitApiTask.TAG, "removeAllPlayListInfo onSuccess");
            InitApiTask.APIDelAllRecords.this.delCollectAndTrace();
          }
        });
        return;
      }
      Logger.i(InitApiTask.TAG, "existFlag " + bool);
      notifyFinish();
    }
  }

  private static class APIDrmReportDecodeCapacityTask extends InitApiTask.APITask
  {
    public static final String TASK_NAME = APIDrmReportDecodeCapacityTask.class.getSimpleName();

    public APIDrmReportDecodeCapacityTask(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    public void start()
    {
      super.start();
      this.taskId = ServerApiManager.i().APIDrmReportDecodeCapacity(new SccmsApiDrmReportDecodeCapacityTask.ISccmsApiReportDecodeCapacityTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          Logger.e(InitApiTask.TAG, "APIDrmReportDecodeCapacity onError()");
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, DrmReportDecodeCapacityInfo paramAnonymousDrmReportDecodeCapacityInfo)
        {
          Logger.i(InitApiTask.TAG, "APIDrmReportDecodeCapacity onSuccess()");
        }
      });
    }
  }

  private static class APIGetAfterPlayListTask extends InitApiTask.APITask
  {
    public static final String TASK_NAME = APIGetAfterPlayListTask.class.getSimpleName();

    public APIGetAfterPlayListTask(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    public void start()
    {
      super.start();
      this.taskId = ServerApiManager.i().APIGetCatchVideoRecordV2(new SccmsApiGetCatchVideoRecordV2Task.ISccmsApiGetCatchVideoRecordV2TaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          Logger.i(InitApiTask.TAG, "SccmsApiGetCatchVideoRecordTaskListener.onError()");
          InitApiTask.APIGetAfterPlayListTask.this.notifyFinish();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<CollectListItem> paramAnonymousArrayList)
        {
          if (!GlobalLogic.getInstance().isUserLogined())
            LocalMediaDataManage.getInstance().saveMediaDataToLocalFile(LocalMediaDataManage.MediaDateType.CATCH, paramAnonymousArrayList);
          try
          {
            UserCPLLogic.getInstance().refreshTracePlayList(paramAnonymousArrayList);
            label27: InitApiTask.APIGetAfterPlayListTask.this.notifyFinish();
            return;
          }
          catch (Exception localException)
          {
            break label27;
          }
        }
      }
      , false);
    }
  }

  private static class APIGetBootAdImageTask extends InitApiTask.APITask
  {
    public static final String TASK_NAME = APIGetBootAdImageTask.class.getSimpleName();
    private SCHttpApiTask task;

    public APIGetBootAdImageTask(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    public void start()
    {
      super.start();
      this.task = ServerApiManager.i().APIGetBootAdUrlFromMgTask(GlobalEnv.getInstance().getBootAdPosId(), new GetAdUrlFromMgTask.IMGCmsGetAdInfoTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          Logger.i(InitApiTask.TAG, "boot ad err: failed to get ad info");
          MplayerAdPlayerView.ErrorParams localErrorParams = new MplayerAdPlayerView.ErrorParams();
          localErrorParams.ad_error_code = "102";
          localErrorParams.ad_perror = (paramAnonymousServerApiCommonError.getHttpCode() + "");
          localErrorParams.ad_pos_type = "7";
          localErrorParams.slot_ad_id = GlobalEnv.getInstance().getBootAdPosId();
          localErrorParams.card_id = "";
          localErrorParams.ad_type = "pic";
          localErrorParams.video_id = "";
          try
          {
            localErrorParams.request_params = EntityUtils.toString(new UrlEncodedFormEntity(InitApiTask.APIGetBootAdImageTask.this.task.getPostForm(), "UTF-8"), "UTF-8");
            localErrorParams.request_url = GlobalEnv.getInstance().getBootAdUrl();
            localErrorParams.response = (paramAnonymousServerApiCommonError.getHttpCode() + "");
            localErrorParams.errorMessage = paramAnonymousServerApiCommonError.getHttpReason();
            localErrorParams.errorDesc = paramAnonymousServerApiCommonError.getHttpReason();
            MplayerAdPlayerView.reportAdError(localErrorParams);
            return;
          }
          catch (UnsupportedEncodingException localUnsupportedEncodingException)
          {
            while (true)
              localUnsupportedEncodingException.printStackTrace();
          }
          catch (IOException localIOException)
          {
            while (true)
              localIOException.printStackTrace();
          }
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, String paramAnonymousString)
        {
          BootAdData localBootAdData = (BootAdData)new Gson().fromJson(paramAnonymousString, BootAdData.class);
          if ((localBootAdData == null) || (localBootAdData.data == null))
            return;
          Logger.i(InitApiTask.TAG, "boot ad data = " + localBootAdData.toString());
          GlobalEnv.getInstance().setBootImageAdDuration(localBootAdData.data.duration);
          GlobalEnv.getInstance().setBootImageAd(localBootAdData.data.url);
          GlobalEnv.getInstance().setBootImageAdImpression(localBootAdData.data.impression);
        }
      });
    }
  }

  private static class APIGetCollectListTask extends InitApiTask.APITask
  {
    public static final String TASK_NAME = APIGetCollectListTask.class.getSimpleName();

    public APIGetCollectListTask(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    public void start()
    {
      super.start();
      this.taskId = ServerApiManager.i().APIGetCollectRecordV2(new SccmsApiGetCollectRecordV2Task.ISccmsApiGetCollectRecordV2TaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          InitApiTask.APIGetCollectListTask.this.notifyFinish();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<CollectListItem> paramAnonymousArrayList)
        {
          if ((paramAnonymousArrayList == null) || (paramAnonymousArrayList.size() <= 0))
            return;
          if (!GlobalLogic.getInstance().isUserLogined())
            LocalMediaDataManage.getInstance().saveMediaDataToLocalFile(LocalMediaDataManage.MediaDateType.COLLECTION, paramAnonymousArrayList);
          try
          {
            UserCPLLogic.getInstance().refreshCollectList(paramAnonymousArrayList);
            label39: InitApiTask.APIGetCollectListTask.this.notifyFinish();
            return;
          }
          catch (Exception localException)
          {
            break label39;
          }
        }
      }
      , false);
    }
  }

  private static class APIGetInitMetaDataTask extends InitApiTask.APITask
  {
    public static final String TASK_NAME = APIGetInitMetaDataTask.class.getSimpleName();

    public APIGetInitMetaDataTask(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    public void start()
    {
      super.start();
      this.taskId = ServerApiManager.i().APIGetInitMetaData(new SccmsApiGetInitMetaDataTask.ISccmsApiGetInitMetaDataTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          Logger.d(InitApiTask.TAG, "APIGetInitMetaData onError");
          InitApiTask.APIGetInitMetaDataTask.this.setErrorCode(ScHunanOTTQuickStartCore.Error.ERROR_GET_EPGDATA);
          InitApiTask.APIGetInitMetaDataTask.this.notifyError();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, byte[] paramAnonymousArrayOfByte)
        {
          Logger.d(InitApiTask.TAG, "APIGetInitMetaData onSuccess");
          GlobalLogic.getInstance().setMetaData(paramAnonymousArrayOfByte);
          InitApiTask.APIGetInitMetaDataTask.this.notifyFinish();
        }
      });
    }
  }

  private static class APIGetPlayListTask extends InitApiTask.APITask
  {
    public static final String TASK_NAME = APIGetPlayListTask.class.getSimpleName();

    public APIGetPlayListTask(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    public void start()
    {
      super.start();
      this.taskId = ServerApiManager.i().APIGetPlayRecordV2(new SccmsApiGetPlayRecordV2Task.ISccmsApiGetPlayRecordV2TaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          Logger.i(InitApiTask.TAG, "SccmsApiGetPlayRecordV2TaskListener.onError()");
          InitApiTask.APIGetPlayListTask.this.notifyFinish();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<CollectListItem> paramAnonymousArrayList)
        {
          if (!GlobalLogic.getInstance().isUserLogined())
            LocalMediaDataManage.getInstance().saveMediaDataToLocalFile(LocalMediaDataManage.MediaDateType.PLAYLIST, paramAnonymousArrayList);
          try
          {
            UserCPLLogic.getInstance().refreshPlayRecordList(paramAnonymousArrayList);
            label27: InitApiTask.APIGetPlayListTask.this.notifyFinish();
            return;
          }
          catch (Exception localException)
          {
            break label27;
          }
        }
      }
      , false);
    }
  }

  private static class APIGetRecommendListTask extends InitApiTask.APITask
  {
    public static final String TASK_NAME = APIGetRecommendListTask.class.getSimpleName();

    public APIGetRecommendListTask(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    public void start()
    {
      super.start();
      this.taskId = ServerApiManager.i().APIGetReplayRecommendData(6, 0, new SccmsApiGetReplayRecommendDataTask.ISccmsApiGetReplayRecommendDataTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          InitApiTask.APIGetRecommendListTask.this.notifyFinish();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, List<PlayBillRecommendStrut> paramAnonymousList)
        {
          GlobalLogic.getInstance().setReplayRecommendList(paramAnonymousList);
          InitApiTask.APIGetRecommendListTask.this.notifyFinish();
        }
      });
    }
  }

  private static class APIGetSecretKeysTask extends InitApiTask.APITask
  {
    public static final String TASK_NAME = APIGetSecretKeysTask.class.getSimpleName();

    public APIGetSecretKeysTask(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    public void processResult(GetSecretKeysInfo paramGetSecretKeysInfo)
    {
      EncryptLogic.addEncryptData(paramGetSecretKeysInfo);
    }

    public void start()
    {
      super.start();
      this.taskId = ServerApiManager.i().APIGetSecretKeysTask(new SccmsApiGetSecretKeysTask.ISccmsApiGetSecretKeysTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          Logger.e(InitApiTask.TAG, "APIGetSecretKeysTask onError");
          InitApiTask.APIGetSecretKeysTask.this.notifyFinish();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, GetSecretKeysInfo paramAnonymousGetSecretKeysInfo)
        {
          Logger.d(InitApiTask.TAG, "APIGetSecretKeysTask onSuccess");
          InitApiTask.APIGetSecretKeysTask.this.processResult(paramAnonymousGetSecretKeysInfo);
          InitApiTask.APIGetSecretKeysTask.this.notifyFinish();
        }
      });
    }
  }

  private static class APIGetTerminalIconTask extends InitApiTask.APITask
  {
    public static final String TASK_NAME = APIGetTerminalIconTask.class.getSimpleName();

    public APIGetTerminalIconTask(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    public void start()
    {
      super.start();
      this.taskId = ServerApiManager.i().APIGetPublicImageTask("public_terminal_icon", new SccmsApiGetPublicImageTask.ISccmsApiGetPublicImageTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          InitApiTask.APIGetTerminalIconTask.this.notifyFinish();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, List<PublicImage> paramAnonymousList)
        {
          if (paramAnonymousList == null)
            return;
          String str1 = "";
          int i = paramAnonymousList.size();
          int j = 0;
          while (true)
          {
            PublicImage localPublicImage;
            String str2;
            if (j < i)
            {
              localPublicImage = (PublicImage)paramAnonymousList.get(j);
              if (localPublicImage == null)
                break label118;
              str2 = localPublicImage.name;
              if (!TextUtils.isEmpty(str2));
            }
            else
            {
              GlobalEnv.getInstance().setMainActivityLogo(str1);
              XulWorker.removeDrawableCachePermanently("file:///.app/info/terminaliconlist");
              return;
            }
            if ("homepage_logo".equals(str2));
            try
            {
              str1 = (String)localPublicImage.url_list.get(0);
              label97: if ("tips_player_cache_07".equals(str2))
                GlobalLogic.getInstance().saveLoadingImageUrl(localPublicImage.url_list);
              while (true)
              {
                label118: j++;
                break;
                if ("bullet_screen_live_icon".equals(str2))
                  GlobalLogic.getInstance().saveDownLoadImageUrl(localPublicImage.name, localPublicImage.url_list);
                else if ("bullet_screen_vod_icon".equals(str2))
                  GlobalLogic.getInstance().saveDownLoadImageUrl(localPublicImage.name, localPublicImage.url_list);
                else if ("bullet_screen_QR_code_icon".equals(str2))
                  GlobalLogic.getInstance().saveDownLoadImageUrl(localPublicImage.name, localPublicImage.url_list);
              }
            }
            catch (Exception localException)
            {
              break label97;
            }
          }
        }
      });
    }
  }

  private static class APIN1A1Task extends InitApiTask.APITask
  {
    public static final String TASK_NAME = APIN1A1Task.class.getSimpleName();

    public APIN1A1Task(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    private void processResult(Message paramMessage)
    {
      Logger.i(InitApiTask.TAG, "processN1A1Msg code:" + paramMessage.arg1);
      try
      {
        int i = ((Integer)paramMessage.obj).intValue();
        bool = false;
        if (i <= 0)
          bool = true;
        Logger.i(InitApiTask.TAG, "processN1A1Msg isMsgDataError:" + bool);
        if ((paramMessage.arg1 != 200) || (bool))
        {
          setErrorCode(ScHunanOTTQuickStartCore.Error.ERROR_CONNECT_FAILED);
          notifyError();
          return;
        }
      }
      catch (Exception localException)
      {
        while (true)
          boolean bool = true;
        notifyFinish();
      }
    }

    public void start()
    {
      super.start();
      this.taskId = ServerApiManager.i().APIGetEpgIndex(new SccmsApiGetEpgIndexTask.ISccmsApiGetEpgDataTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          Logger.e(InitApiTask.TAG, "getUrls, APIGetEpgIndex onError");
          Message localMessage = new Message();
          localMessage.arg1 = paramAnonymousServerApiCommonError.getHttpCode();
          InitApiTask.APIN1A1Task.this.processResult(localMessage);
          SystemTimeManager.getInstance().SynchronizedTime();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, Integer paramAnonymousInteger)
        {
          Logger.d(InitApiTask.TAG, "getUrls, APIGetEpgIndex onSuccess");
          Message localMessage = new Message();
          localMessage.arg1 = 200;
          localMessage.obj = paramAnonymousInteger;
          InitApiTask.APIN1A1Task.this.processResult(localMessage);
          SystemTimeManager.getInstance().SynchronizedTime();
        }
      });
    }
  }

  private static class APIN3A2GetEpgDataTask extends InitApiTask.APITask
  {
    public static final String TASK_NAME = APIN3A2GetEpgDataTask.class.getSimpleName();

    public APIN3A2GetEpgDataTask(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    private void processResult(Message paramMessage)
    {
      if (paramMessage.obj == null)
      {
        setErrorCode(ScHunanOTTQuickStartCore.Error.ERROR_GET_EPGDATA);
        notifyError();
        return;
      }
      try
      {
        ArrayList localArrayList = (ArrayList)paramMessage.obj;
        GlobalLogic.getInstance().setN3A2MetaGroups(localArrayList);
        notifyFinish();
        return;
      }
      catch (Exception localException)
      {
        setErrorCode(ScHunanOTTQuickStartCore.Error.ERROR_GET_EPGDATA);
        notifyError();
      }
    }

    public void start()
    {
      super.start();
      this.taskId = ServerApiManager.i().APIN3A2GetEpgData(new SccmsApiN3A2GetEpgDataTask.ISccmsApiN3A2GetEpgDataTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          Logger.i(InitApiTask.TAG, "APIN3A2GetEpgData onError");
          InitApiTask.APIN3A2GetEpgDataTask.this.setErrorCode(ScHunanOTTQuickStartCore.Error.ERROR_GET_EPGDATA);
          InitApiTask.APIN3A2GetEpgDataTask.this.notifyError();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<MetadataGoup> paramAnonymousArrayList)
        {
          Logger.i(InitApiTask.TAG, "APIN3A2GetEpgData onSuccess");
          Message localMessage = new Message();
          localMessage.obj = paramAnonymousArrayList;
          InitApiTask.APIN3A2GetEpgDataTask.this.processResult(localMessage);
        }
      });
    }
  }

  private static abstract class APITask extends TaskScheduler.Task
  {
    private ErrorCode errorCode;
    public int taskId = -1;

    public APITask(String paramString, TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    public ErrorCode errorCode()
    {
      return this.errorCode;
    }

    public void notifyError()
    {
      if (this.taskId == -1)
      {
        Logger.e(InitApiTask.TAG, "notifyError taskId == -1 taskName = " + taskId());
        return;
      }
      super.notifyError();
    }

    public void notifyFinish()
    {
      if (this.taskId == -1)
      {
        Logger.e(InitApiTask.TAG, "notifyError taskId == -1 taskName = " + taskId());
        return;
      }
      super.notifyFinish();
    }

    public void setErrorCode(ErrorCode paramErrorCode)
    {
      this.errorCode = paramErrorCode;
    }

    public void start()
    {
      notifyRunning();
    }

    public void stop()
    {
      this.taskId = -1;
    }
  }

  private static class GETGUIDTask extends InitApiTask.APITask
  {
    public static final String TASK_NAME = GETGUIDTask.class.getSimpleName();

    public GETGUIDTask(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    private void processResult(String paramString)
    {
      Logger.i(TASK_NAME, "TASK_NAME guid=" + paramString);
      GlobalLogic.getInstance().setGuid(paramString);
    }

    public void start()
    {
      super.start();
      if (!"".equals(GlobalLogic.getInstance().getGuid()))
      {
        Logger.i(TASK_NAME, "guid not null");
        this.taskId = 2147483547;
        notifyFinish();
        return;
      }
      ServerApiTask localServerApiTask = ServerApiManager.i().APIGetGUID(new SccmsApiGetGUIDTask.ISccmsApiGetGUIDDataTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          Logger.i(InitApiTask.GETGUIDTask.TASK_NAME, "guid not notifyFinish");
          InitApiTask.GETGUIDTask.this.notifyFinish();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, String paramAnonymousString)
        {
          InitApiTask.GETGUIDTask.this.processResult(paramAnonymousString);
          InitApiTask.GETGUIDTask.this.notifyFinish();
        }
      });
      this.taskId = localServerApiTask.getTaskId();
      localServerApiTask.setTaskPriority(0);
    }
  }

  public static abstract interface InitApiResultListener
  {
    public abstract void onError(ErrorCode paramErrorCode);

    public abstract void onSuccess(MetadataGoup paramMetadataGoup);
  }

  private static class InitAppTask extends TaskScheduler.Task
  {
    public static final String TASK_NAME = InitAppTask.class.getSimpleName();

    public InitAppTask(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    public void start()
    {
      notifyRunning();
      try
      {
        new byte[16777216];
        label9: System.gc();
        SplashActivity.initPreLoadMainPage();
        App.getInstance().initApp();
        notifyFinish();
        return;
      }
      catch (Exception localException)
      {
        break label9;
      }
    }

    public void stop()
    {
    }
  }

  private static class StartMainActivityTask extends TaskScheduler.Task
  {
    public static final String TASK_NAME = StartMainActivityTask.class.getSimpleName();

    public StartMainActivityTask(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    public void start()
    {
      notifyRunning();
      GlobalLogic.getInstance().setAppInterfaceReady(true);
      InitApiTask.access$300();
      notifyFinish();
    }

    public void stop()
    {
    }
  }

  static class TaskInfo
  {
    String[] dependencies;
    TaskScheduler.Task task;

    TaskInfo(TaskScheduler.Task paramTask, String[] paramArrayOfString)
    {
      this.task = paramTask;
      this.dependencies = paramArrayOfString;
    }
  }

  private static class UserCenterVerifyTokenTask extends InitApiTask.APITask
  {
    public static final String TASK_NAME = UserCenterVerifyTokenTask.class.getSimpleName();

    public UserCenterVerifyTokenTask(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    private void onCheckVerfyTokenSuccess(UserCenterLogin paramUserCenterLogin)
    {
      UserInfo localUserInfo = GlobalLogic.getInstance().getUserInfo();
      if (localUserInfo == null)
        localUserInfo = new UserInfo();
      localUserInfo.user_id = String.valueOf(paramUserCenterLogin.uid);
      localUserInfo.expires_in = paramUserCenterLogin.expire;
      localUserInfo.name = paramUserCenterLogin.nickname;
      localUserInfo.account = paramUserCenterLogin.loginaccount;
      localUserInfo.mobile = paramUserCenterLogin.mobile;
      localUserInfo.status = paramUserCenterLogin.status;
      localUserInfo.web_token = paramUserCenterLogin.ticket;
      localUserInfo.avatar = paramUserCenterLogin.avatar;
      localUserInfo.wechat_type = paramUserCenterLogin.wechat_type;
      localUserInfo.rtype = paramUserCenterLogin.rtype;
      localUserInfo.email = paramUserCenterLogin.email;
      if (!TextUtils.isEmpty(paramUserCenterLogin.ticket))
      {
        GlobalLogic.getInstance().userLogin(localUserInfo);
        return;
      }
      GlobalLogic.getInstance().userLogout();
    }

    private void userCenterVerifyToken()
    {
      if (!GlobalLogic.getInstance().isUserLogined())
      {
        Logger.i("user not login!!!");
        GlobalLogic.getInstance().userLogout();
        this.taskId = 2147483647;
        notifyFinish();
        return;
      }
      this.taskId = ServerApiManager.i().APIUserCenterVerifyToken(GlobalLogic.getInstance().getWebToken(), GlobalEnv.getInstance().getAAALicense(), new SccmsApiUserCenterVerifyTokenTask.ISccmsApiUserCenterVerifyTokenTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          Logger.i("APIUserCenterVerifyToken onError");
          GlobalLogic.getInstance().userLogout();
          InitApiTask.UserCenterVerifyTokenTask.this.notifyFinish();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserCenterLogin paramAnonymousUserCenterLogin)
        {
          Logger.i("APIUserCenterVerifyToken onSuccess");
          GlobalLogic.getInstance().setVerifyTokenInfo(paramAnonymousUserCenterLogin);
          if (paramAnonymousUserCenterLogin.err == 0)
            InitApiTask.UserCenterVerifyTokenTask.this.onCheckVerfyTokenSuccess(paramAnonymousUserCenterLogin);
          while (true)
          {
            InitApiTask.UserCenterVerifyTokenTask.this.notifyFinish();
            return;
            GlobalLogic.getInstance().userLogout();
          }
        }
      });
    }

    public void start()
    {
      userCenterVerifyToken();
    }

    public void stop()
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.service.InitApiTask
 * JD-Core Version:    0.6.2
 */