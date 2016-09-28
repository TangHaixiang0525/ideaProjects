package com.starcor.hunan.service.apidetect.task;

import com.starcor.core.domain.AAAOrderRecordList;
import com.starcor.core.domain.AAAProductList;
import com.starcor.core.domain.UserCenterInfo;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.service.apidetect.server.ApiDetectServerApiManager;
import com.starcor.sccms.api.SccmsApiAAAGetOrderRecordListTask;
import com.starcor.sccms.api.SccmsApiAAAGetOrderRecordListTask.ISccmsApiAAAGetOrderRecordListTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetProductListTask;
import com.starcor.sccms.api.SccmsApiAAAGetProductListTask.ISccmsApiAAAGetProductListTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetUserInfoTask;
import com.starcor.sccms.api.SccmsApiAAAGetUserInfoTask.ISccmsApiGetUserCenterInfoTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;

public class UserCenterProcessDetectTask extends ApiProcessDetectTask
{
  private static final String TAG = UserCenterProcessDetectTask.class.getSimpleName();

  protected void runUserCenterProcess()
  {
    if (GlobalLogic.getInstance().isUserLogined())
    {
      Logger.i(TAG, "runUserCenterProcess user has logined!");
      setResultHeaderByTaskId(this.mUserCenterProcessTaskId, SccmsApiAAAGetUserInfoTask.class.getSimpleName(), "N212_A_12", "新3A请求用户信息的接口", "nns_web_token=" + GlobalLogic.getInstance().getWebToken() + "nns_bussiness_id=1" + "nns_license=" + GlobalEnv.getInstance().getAAALicense());
      this.mUserCenterProcessTaskId = ApiDetectServerApiManager.i().ApiAAAGetUserInfo(GlobalLogic.getInstance().getWebToken(), 1, GlobalEnv.getInstance().getAAALicense(), new SccmsApiGetUserCenterInfoTaskListener(null));
    }
  }

  private class SccmsApiAAAGetOrderRecordListTaskListener
    implements SccmsApiAAAGetOrderRecordListTask.ISccmsApiAAAGetOrderRecordListTaskListener
  {
    private SccmsApiAAAGetOrderRecordListTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(UserCenterProcessDetectTask.TAG, "SccmsApiAAAGetOrderRecordListTaskListener onError: taskInfo.getTaskId()=" + i);
      UserCenterProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + UserCenterProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAOrderRecordList paramAAAOrderRecordList)
    {
      Logger.i(UserCenterProcessDetectTask.TAG, "SccmsApiAAAGetOrderRecordListTaskListener onSuccess: taskInfo.getTaskId()=" + paramServerApiTaskInfo.getTaskId());
      UserCenterProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramAAAOrderRecordList.toString() + UserCenterProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
      Logger.i(UserCenterProcessDetectTask.TAG, "finish running process: " + UserCenterProcessDetectTask.this.getCurrentApiProcessTypeByTaskId(paramServerApiTaskInfo.getTaskId()));
      UserCenterProcessDetectTask.this.runCallbackByTaskId(paramServerApiTaskInfo.getTaskId());
      Logger.i(UserCenterProcessDetectTask.TAG, UserCenterProcessDetectTask.this.getApiTimeFormatString(false, "" + UserCenterProcessDetectTask.this.getCurrentApiProcessTypeByTaskId(paramServerApiTaskInfo.getTaskId())));
    }
  }

  private class SccmsApiAAAGetProductListTaskListener
    implements SccmsApiAAAGetProductListTask.ISccmsApiAAAGetProductListTaskListener
  {
    private SccmsApiAAAGetProductListTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(UserCenterProcessDetectTask.TAG, "SccmsApiAAAGetProductListTaskListener onError: taskInfo.getTaskId()=" + i);
      UserCenterProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + UserCenterProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AAAProductList paramAAAProductList)
    {
      Logger.i(UserCenterProcessDetectTask.TAG, "SccmsApiAAAGetProductListTaskListener onSuccess: taskId=" + paramServerApiTaskInfo.getTaskId());
      UserCenterProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramAAAProductList.toString() + UserCenterProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo));
      String str1 = GlobalLogic.getInstance().getWebToken();
      String str2 = GlobalEnv.getInstance().getAAALicense();
      UserCenterProcessDetectTask.this.setResultHeaderByTaskId(paramServerApiTaskInfo.getTaskId(), SccmsApiAAAGetOrderRecordListTask.class.getSimpleName(), "N212_A_13", "新3A请求VIP产品信息的接口", "nns_license=" + str2 + "nns_ticket=" + str1 + "nns_bussiness_id=1" + "page_index=0" + "page_num=4" + "category_id=2");
      int i = ApiDetectServerApiManager.i().APIAAAGetOrderRecordList(str2, str1, 1, 1, 4, 2, new UserCenterProcessDetectTask.SccmsApiAAAGetOrderRecordListTaskListener(UserCenterProcessDetectTask.this, null));
      UserCenterProcessDetectTask.this.setNextTaskIdByCurrentTaskId(paramServerApiTaskInfo.getTaskId(), i);
    }
  }

  private class SccmsApiGetUserCenterInfoTaskListener
    implements SccmsApiAAAGetUserInfoTask.ISccmsApiGetUserCenterInfoTaskListener
  {
    private SccmsApiGetUserCenterInfoTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(UserCenterProcessDetectTask.TAG, "SccmsApiGetUserCenterInfoTaskListener onError: taskInfo.getTaskId()=" + i);
      UserCenterProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + UserCenterProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserCenterInfo paramUserCenterInfo)
    {
      Logger.i(UserCenterProcessDetectTask.TAG, "SccmsApiGetUserCenterInfoTaskListener onSuccess: taskId=" + paramServerApiTaskInfo.getTaskId());
      UserCenterProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramUserCenterInfo.toString() + UserCenterProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo));
      UserCenterProcessDetectTask.this.setResultHeaderByTaskId(paramServerApiTaskInfo.getTaskId(), SccmsApiAAAGetProductListTask.class.getSimpleName(), "N212_A_4", "新3A请求充值产品信息", "");
      int i = ApiDetectServerApiManager.i().APIAAAGetProductList(GlobalEnv.getInstance().getAAALicense(), GlobalLogic.getInstance().getWebToken(), 1, 1, new UserCenterProcessDetectTask.SccmsApiAAAGetProductListTaskListener(UserCenterProcessDetectTask.this, null));
      UserCenterProcessDetectTask.this.setNextTaskIdByCurrentTaskId(paramServerApiTaskInfo.getTaskId(), i);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.apidetect.task.UserCenterProcessDetectTask
 * JD-Core Version:    0.6.2
 */