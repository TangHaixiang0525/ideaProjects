package com.starcor.hunan.uilogic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppVersion;
import com.starcor.core.domain.MetadataGoup;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.domain.Version;
import com.starcor.core.epgapi.GlobalApiTask;
import com.starcor.core.epgapi.params.CheckVersionUpdataParams;
import com.starcor.core.exception.ApplicationException;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.DialogActivity;
import com.starcor.hunan.NetworkErrorActivity;
import com.starcor.hunan.NetworkErrorActivity.PageType;
import com.starcor.hunan.service.SystemTimeManager;
import com.starcor.hunan.widget.ExitDialog;
import com.starcor.message.MessageHandler;
import com.starcor.report.ReportMessage;
import com.starcor.sccms.api.SccmsApiCheckValidByWebTokenTask.ISccmsApiCheckValidByWebTokenTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.service.ErrorCode;
import com.starcor.service.InitService.Error;
import com.starcor.service.ScHunanOTTQuickStartCore;
import com.starcor.service.ScHunanOTTQuickStartCore.InitApiResultListener;
import java.util.ArrayList;

public class InitApiData
{
  public static final int MESSAGE_CHECK_UPDATE = -70002;
  public static final int MESSAGE_GET_META_INFO = -70001;
  public static final int MESSAGE_N1A1 = -69999;
  private static final String TAG = "InitApiData";
  public static boolean isUpdated;
  private static boolean isUpgradeDialogVisible = false;
  public static boolean needUpdate = false;
  private boolean isShowMacError = false;
  private boolean isTerminated = false;
  private Context mContext;
  private Handler mHandler = new Handler(Looper.getMainLooper())
  {
    private void processN1A1Msg(Message paramAnonymousMessage)
    {
      Logger.i("InitApiData", "processN1A1Msg code:" + paramAnonymousMessage.arg1);
      try
      {
        int i = ((Integer)paramAnonymousMessage.obj).intValue();
        bool = false;
        if (i <= 0)
          bool = true;
        Logger.i("InitApiData", "processN1A1Msg isMsgDataError:" + bool);
        if ((paramAnonymousMessage.arg1 != 200) || (bool))
          if (DeviceInfo.isHuaWei())
          {
            if (InitApiData.this.mOnApiOkListener != null)
              InitApiData.this.mOnApiOkListener.onGetApiDataError();
            return;
          }
      }
      catch (Exception localException)
      {
        do
        {
          do
          {
            while (true)
            {
              localException.printStackTrace();
              boolean bool = true;
            }
            if (!AppFuncCfg.FUNCTION_MAIN_SETTING_PAGE)
              break;
            Logger.e("InitApiData", "processN1A1Msg show main config and wait config");
            Intent localIntent = new Intent();
            localIntent.setClass(InitApiData.this.mContext, NetworkErrorActivity.class);
            localIntent.putExtra("page_type", NetworkErrorActivity.PageType.NETWORK_ERROR.ordinal());
            localIntent.addFlags(8388608);
            InitApiData.this.mContext.startActivity(localIntent);
          }
          while (InitApiData.this.mOnApiOkListener == null);
          InitApiData.this.mOnApiOkListener.onNeedFinishActivity();
          return;
          Logger.e("InitApiData", "processN1A1Msg connect epg server error");
          if (InitApiData.this.mOnApiOkListener != null)
            InitApiData.this.mOnApiOkListener.onGetApiDataError();
        }
        while (!InitApiData.this.needShowDialog);
        ApplicationException.showErrorDialogWithReport(InitApiData.this.mContext, 10, "1002005", "processN1A1Msg error");
        return;
      }
      SystemTimeManager.getInstance();
      SystemTimeManager.getInstance().SynchronizedTime();
      if ((AppFuncCfg.FUNCTION_CHECK_UPDATE) && (!InitApiData.isUpdated))
      {
        InitApiData.isUpdated = true;
        CheckVersionUpdataParams localCheckVersionUpdataParams = new CheckVersionUpdataParams(DeviceInfo.getMGTVVersion(), DeviceInfo.getMac(), "", "", GlobalLogic.getInstance().getUserId());
        GeneralUtils.markTime("n2_a");
        GlobalApiTask.getInstance().N22A_CheckVersionUpdata(InitApiData.this.mHandler, -70002, localCheckVersionUpdataParams);
        return;
      }
      InitApiData.this.checkValidByWebToken();
    }

    private void processN3A2Msg(Message paramAnonymousMessage)
    {
      Logger.i("InitApiData", "processN3A2Msg code:" + paramAnonymousMessage.arg1);
      if (paramAnonymousMessage.obj == null)
      {
        if (InitApiData.this.mOnApiOkListener != null)
          InitApiData.this.mOnApiOkListener.onGetApiDataError();
        if (InitApiData.this.needShowDialog)
          ApplicationException.showErrorDialogWithReport(InitApiData.this.mContext, 10, "1002005", "processN3A2Msg msg.obj null");
      }
      do
      {
        return;
        ArrayList localArrayList = (ArrayList)paramAnonymousMessage.obj;
        GlobalLogic.getInstance().setAppInterfaceReady(true);
        GlobalLogic.getInstance().setN3A2MetaGroups(localArrayList);
      }
      while (InitApiData.this.mOnApiOkListener == null);
      Logger.i("InitApiData", "api is   OK");
      InitApiData.this.mOnApiOkListener.onApiOk(1);
      InitApiData.this.notifyApiOkToReportService();
    }

    public void handleMessage(Message paramAnonymousMessage)
    {
      Logger.i("InitApiData", "getmessage:" + paramAnonymousMessage.what);
      if (InitApiData.this.isTerminated)
      {
        Logger.i("InitApiData", "handleMessage - InitApiData terminated!!!");
        return;
      }
      switch (paramAnonymousMessage.what)
      {
      case -70000:
      default:
        return;
      case -70002:
        InitApiData.this.processN22A1CheckUpdateVersion(paramAnonymousMessage);
        return;
      case -69999:
        processN1A1Msg(paramAnonymousMessage);
        return;
      case -70001:
      }
      processN3A2Msg(paramAnonymousMessage);
    }
  };
  private onApiOKListener mOnApiOkListener;
  private boolean needShowDialog;

  public InitApiData(Context paramContext)
  {
    this.mContext = paramContext;
    this.needShowDialog = true;
    getApiData();
  }

  public InitApiData(Context paramContext, boolean paramBoolean)
  {
    this.mContext = paramContext;
    this.needShowDialog = paramBoolean;
    getApiData();
  }

  private void checkValidByWebToken()
  {
    ServerApiManager.i().APICheckValidByWebToken(new SccmsApiCheckValidByWebTokenTask.ISccmsApiCheckValidByWebTokenTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Message localMessage = new Message();
        localMessage.arg1 = paramAnonymousServerApiCommonError.getHttpCode();
        InitApiData.this.processCheckUserInfo(localMessage);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserInfo paramAnonymousUserInfo)
      {
        Message localMessage = new Message();
        localMessage.obj = paramAnonymousUserInfo;
        InitApiData.this.processCheckUserInfo(localMessage);
      }
    });
  }

  private void doNX1()
  {
    Logger.i("InitApiData", "doNX?");
  }

  private void notifyApiOkToReportService()
  {
    Logger.i("ReportService", "notifyApiOkToReportService");
    ReportMessage localReportMessage = new ReportMessage();
    localReportMessage.setReportType(3);
    MessageHandler.instance().doNotify(localReportMessage);
  }

  private void processCheckUserInfo(Message paramMessage)
  {
    Logger.i("InitApiData", "processCheckUserInfo code:" + paramMessage.arg1);
    if ((paramMessage.arg1 == 550) && (!this.isShowMacError))
    {
      this.isShowMacError = true;
      Intent localIntent4 = new Intent();
      localIntent4.setClass(this.mContext, NetworkErrorActivity.class);
      localIntent4.putExtra("page_type", NetworkErrorActivity.PageType.MAC_ERROR.ordinal());
      localIntent4.addFlags(8388608);
      this.mContext.startActivity(localIntent4);
      if (this.mOnApiOkListener != null)
        this.mOnApiOkListener.onNeedFinishActivity();
    }
    do
    {
      do
      {
        do
        {
          do
          {
            return;
            if (paramMessage.arg1 != 552)
              break;
            Intent localIntent1 = new Intent();
            localIntent1.setClass(this.mContext, NetworkErrorActivity.class);
            localIntent1.putExtra("page_type", NetworkErrorActivity.PageType.IP_LIMIT.ordinal());
            localIntent1.addFlags(8388608);
            this.mContext.startActivity(localIntent1);
          }
          while (this.mOnApiOkListener == null);
          this.mOnApiOkListener.onNeedFinishActivity();
          return;
          if (paramMessage.obj != null)
            break label318;
          if (!DeviceInfo.isHuaWei())
            break;
        }
        while (this.mOnApiOkListener == null);
        this.mOnApiOkListener.onGetApiDataError();
        return;
        if (!AppFuncCfg.FUNCTION_MAIN_SETTING_PAGE)
          break;
        Intent localIntent3 = new Intent();
        localIntent3.setClass(this.mContext, NetworkErrorActivity.class);
        localIntent3.addFlags(8388608);
        localIntent3.putExtra("page_type", NetworkErrorActivity.PageType.NETWORK_ERROR.ordinal());
        this.mContext.startActivity(localIntent3);
      }
      while (this.mOnApiOkListener == null);
      this.mOnApiOkListener.onNeedFinishActivity();
      return;
    }
    while (!this.needShowDialog);
    ApplicationException.showErrorDialogWithReport(this.mContext, 10, "1002005", "processCheckUserInfo msg.obj null");
    return;
    label318: UserInfo localUserInfo = (UserInfo)paramMessage.obj;
    if (localUserInfo.status.equals("40001"))
    {
      Intent localIntent2 = new Intent();
      localIntent2.setClass(this.mContext, NetworkErrorActivity.class);
      localIntent2.putExtra("page_type", NetworkErrorActivity.PageType.MAC_ERROR.ordinal());
      localIntent2.addFlags(8388608);
      this.mContext.startActivity(localIntent2);
      return;
    }
    GlobalLogic.getInstance().userLogin(localUserInfo);
    GlobalApiTask.getInstance().N3A2_GetEpg(this.mHandler, -70001, GlobalLogic.getInstance().getWebToken(), GlobalLogic.getInstance().getNnToken(), GlobalLogic.getInstance().getUserId(), GlobalLogic.getInstance().getDeviceId(), DeviceInfo.getMac());
  }

  private void processError(ErrorCode paramErrorCode)
  {
    if (paramErrorCode != null)
    {
      if (InitService.Error.ERROR_SETTING_SERVICE.getErrorCode() != paramErrorCode.getErrorCode())
        break label57;
      Bundle localBundle1 = new Bundle();
      localBundle1.putString("Message", "找不到关键组件:SettingService！");
      if (this.needShowDialog)
        ((DialogActivity)this.mContext).showDialog(10, localBundle1);
    }
    label57: label353: label747: 
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                do
                {
                  do
                  {
                    do
                    {
                      do
                      {
                        do
                        {
                          do
                          {
                            do
                            {
                              do
                              {
                                Bundle localBundle2;
                                do
                                {
                                  return;
                                  if (InitService.Error.ERROR_TASK_SERVICE.getErrorCode() != paramErrorCode.getErrorCode())
                                    break;
                                  localBundle2 = new Bundle();
                                  localBundle2.putString("Message", "找不到关键组件:TaskService！");
                                }
                                while (!this.needShowDialog);
                                ((DialogActivity)this.mContext).showDialog(10, localBundle2);
                                return;
                                if (InitService.Error.ERROR_NETSTATE.getErrorCode() != paramErrorCode.getErrorCode())
                                  break;
                              }
                              while (!AppFuncCfg.FUNCTION_MAIN_SETTING_PAGE);
                              Logger.i("InitApiData", "start --- NetworkErrorActivity");
                              Intent localIntent7 = new Intent();
                              localIntent7.setClass(this.mContext, NetworkErrorActivity.class);
                              localIntent7.putExtra("page_type", NetworkErrorActivity.PageType.NETWORK_ERROR.ordinal());
                              this.mContext.startActivity(localIntent7);
                              return;
                              if (InitService.Error.ERROR_CONNECT_FAILED.getErrorCode() != paramErrorCode.getErrorCode())
                                break label353;
                              if (!DeviceInfo.isHuaWei())
                                break;
                            }
                            while (this.mOnApiOkListener == null);
                            this.mOnApiOkListener.onGetApiDataError();
                            return;
                            if (!AppFuncCfg.FUNCTION_MAIN_SETTING_PAGE)
                              break;
                            Logger.e("InitApiData", "processN1A1Msg show main config and wait config");
                            Intent localIntent6 = new Intent();
                            localIntent6.setClass(this.mContext, NetworkErrorActivity.class);
                            localIntent6.putExtra("page_type", NetworkErrorActivity.PageType.NETWORK_ERROR.ordinal());
                            localIntent6.addFlags(8388608);
                            this.mContext.startActivity(localIntent6);
                          }
                          while (this.mOnApiOkListener == null);
                          this.mOnApiOkListener.onNeedFinishActivity();
                          return;
                          Logger.e("InitApiData", "processN1A1Msg connect epg server error");
                          if (this.mOnApiOkListener != null)
                            this.mOnApiOkListener.onGetApiDataError();
                        }
                        while (!this.needShowDialog);
                        ApplicationException.showErrorDialogWithReport(this.mContext, 10, "1002005", InitService.Error.ERROR_CONNECT_FAILED.getErrorMsg());
                        return;
                        if (InitService.Error.ERROR_MAC.getErrorCode() != paramErrorCode.getErrorCode())
                          break;
                      }
                      while (this.needShowDialog);
                      this.isShowMacError = true;
                      Intent localIntent5 = new Intent();
                      localIntent5.setClass(this.mContext, NetworkErrorActivity.class);
                      localIntent5.putExtra("page_type", NetworkErrorActivity.PageType.MAC_ERROR.ordinal());
                      localIntent5.addFlags(8388608);
                      this.mContext.startActivity(localIntent5);
                    }
                    while (this.mOnApiOkListener == null);
                    this.mOnApiOkListener.onNeedFinishActivity();
                    return;
                    if (InitService.Error.ERROR_LICENSE.getErrorCode() != paramErrorCode.getErrorCode())
                      break;
                    Intent localIntent1 = new Intent();
                    localIntent1.setClass(this.mContext, NetworkErrorActivity.class);
                    localIntent1.putExtra("page_type", NetworkErrorActivity.PageType.LICENSE_ERROR.ordinal());
                    localIntent1.addFlags(8388608);
                    this.mContext.startActivity(localIntent1);
                  }
                  while (this.mOnApiOkListener == null);
                  this.mOnApiOkListener.onNeedFinishActivity();
                  return;
                  if (InitService.Error.ERROR_IP_LIMITED.getErrorCode() != paramErrorCode.getErrorCode())
                    break;
                  Intent localIntent2 = new Intent();
                  localIntent2.setClass(this.mContext, NetworkErrorActivity.class);
                  localIntent2.putExtra("page_type", NetworkErrorActivity.PageType.IP_LIMIT.ordinal());
                  localIntent2.addFlags(8388608);
                  this.mContext.startActivity(localIntent2);
                }
                while (this.mOnApiOkListener == null);
                this.mOnApiOkListener.onNeedFinishActivity();
                return;
                if (InitService.Error.ERROR_GET_USERINFO.getErrorCode() != paramErrorCode.getErrorCode())
                  break label747;
                if (!DeviceInfo.isHuaWei())
                  break;
              }
              while (this.mOnApiOkListener == null);
              this.mOnApiOkListener.onGetApiDataError();
              return;
              if (!AppFuncCfg.FUNCTION_MAIN_SETTING_PAGE)
                break;
              Intent localIntent4 = new Intent();
              localIntent4.setClass(this.mContext, NetworkErrorActivity.class);
              localIntent4.addFlags(8388608);
              localIntent4.putExtra("page_type", NetworkErrorActivity.PageType.NETWORK_ERROR.ordinal());
              this.mContext.startActivity(localIntent4);
            }
            while (this.mOnApiOkListener == null);
            this.mOnApiOkListener.onNeedFinishActivity();
            return;
          }
          while (!this.needShowDialog);
          ApplicationException.showErrorDialogWithReport(this.mContext, 10, "1002005", InitService.Error.ERROR_GET_USERINFO.getErrorMsg());
          return;
          if (InitService.Error.ERROR_GET_EPGDATA.getErrorCode() != paramErrorCode.getErrorCode())
            break;
          if (this.mOnApiOkListener != null)
            this.mOnApiOkListener.onGetApiDataError();
        }
        while (!this.needShowDialog);
        ApplicationException.showErrorDialogWithReport(this.mContext, 10, "1002005", InitService.Error.ERROR_GET_EPGDATA.getErrorMsg());
        return;
        if (InitService.Error.ERROR_HANDLE_OLDVERSION.getErrorCode() == paramErrorCode.getErrorCode())
        {
          Intent localIntent3 = new Intent();
          localIntent3.setClass(this.mContext, NetworkErrorActivity.class);
          localIntent3.putExtra("page_type", NetworkErrorActivity.PageType.MAC_ERROR.ordinal());
          localIntent3.addFlags(8388608);
          this.mContext.startActivity(localIntent3);
          return;
        }
      }
      while (InitService.Error.OTHER_ERROR.getErrorCode() != paramErrorCode.getErrorCode());
      if (this.needShowDialog)
        ApplicationException.showErrorDialogWithReport(this.mContext, 10, "1001001", InitService.Error.OTHER_ERROR.getErrorMsg());
    }
    while (this.mOnApiOkListener == null);
    this.mOnApiOkListener.onGetApiDataError();
  }

  private void processN22A1CheckUpdateVersion(Message paramMessage)
  {
    Logger.i("InitApiData", "processN22A1CheckUpdateVersion code:" + paramMessage.arg1);
    Version localVersion = (Version)paramMessage.obj;
    Logger.i("InitApiData", "processN22A1CheckUpdateVersion version:" + localVersion.toString());
    if (localVersion.appVersion == null)
      Logger.e("InitApiData", "processN22A1CheckUpdateVersion version.appVersion == null");
    do
    {
      return;
      if ((DeviceInfo.getFactory() != 900017001) || (localVersion.appVersion.state != 0))
      {
        Logger.e("InitApiData", "processN22A1CheckUpdateVersion not need update state:" + localVersion.appVersion.state);
        return;
      }
      if (TextUtils.isEmpty(localVersion.appVersion.url))
      {
        Logger.e("InitApiData", "processN22A1CheckUpdateVersion TextUtils.isEmpty(version.appVersion.url )");
        return;
      }
      if (!"force".equalsIgnoreCase(localVersion.appVersion.type))
        break label209;
      if (!startUpgradeActivity(localVersion.appVersion.url))
        break;
    }
    while (this.mOnApiOkListener == null);
    this.mOnApiOkListener.onNeedFinishActivity();
    return;
    Logger.e("InitApiData", "processN22A1CheckUpdateVersion force but startUpgradeActivity error");
    return;
    label209: Logger.i("InitApiData", "processN22A1CheckUpdateVersion manual upgrade");
    showUpgradeChoiceDialog(localVersion);
  }

  private void showUpgradeChoiceDialog(final Version paramVersion)
  {
    if (isUpgradeDialogVisible == true)
      return;
    isUpgradeDialogVisible = true;
    ExitDialog localExitDialog = new ExitDialog(this.mContext, 2131296258);
    localExitDialog.setMessage("已有新版本，是否立即升级?");
    localExitDialog.setPositiveButtonListener(new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        Logger.i("InitApiData", "showUpgradeChoiceDialog.setOnKeyListener back");
        if (!InitApiData.this.startUpgradeActivity(paramVersion.appVersion.url))
        {
          InitApiData.access$902(false);
          paramAnonymousDialogInterface.dismiss();
          Logger.e("InitApiData", "showUpgradeChoiceDialog.onClick startUpgradeActivity");
          InitApiData.this.checkValidByWebToken();
        }
        do
        {
          return;
          Logger.i("InitApiData", "showUpgradeChoiceDialog.onClick startUpgradeActivity");
        }
        while (InitApiData.this.mOnApiOkListener == null);
        InitApiData.this.mOnApiOkListener.onNeedFinishActivity();
      }
    });
    localExitDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        int i = paramAnonymousKeyEvent.getAction();
        boolean bool = false;
        if (i == 0)
        {
          bool = false;
          if (paramAnonymousInt == 4)
          {
            InitApiData.access$902(false);
            paramAnonymousDialogInterface.dismiss();
            Logger.i("InitApiData", "showUpgradeChoiceDialog.setOnKeyListener  back");
            InitApiData.this.checkValidByWebToken();
            bool = true;
          }
        }
        return bool;
      }
    });
    localExitDialog.show();
  }

  private boolean startUpgradeActivity(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
    {
      Logger.i("InitApiData", "startUpgradeActivity url is empty!");
      return false;
    }
    Logger.i("InitApiData", "startUpgradeActivity url:" + paramString);
    try
    {
      Intent localIntent = new Intent("com.starcor.service.intent.action.UPGRADE");
      localIntent.putExtra("url", new String[] { paramString });
      localIntent.setFlags(8388608);
      localIntent.putExtra("upgrade_silently", true);
      localIntent.putExtra("error_start_package", this.mContext.getPackageName());
      this.mContext.startActivity(localIntent);
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Logger.e("InitApiData", "startUpgradeActivity Exception");
    }
    return false;
  }

  public void getApiData()
  {
    ScHunanOTTQuickStartCore.getInstance(this.mContext).startInitApi(new ScHunanOTTQuickStartCore.InitApiResultListener()
    {
      public void hasUpdate(Message paramAnonymousMessage)
      {
        InitApiData.this.processN22A1CheckUpdateVersion(paramAnonymousMessage);
      }

      public void onError(ErrorCode paramAnonymousErrorCode)
      {
        if (InitApiData.this.mOnApiOkListener != null)
          InitApiData.this.processError(paramAnonymousErrorCode);
      }

      public void onSuccess(MetadataGoup paramAnonymousMetadataGoup)
      {
        if (InitApiData.this.mOnApiOkListener != null)
        {
          InitApiData.this.mOnApiOkListener.onApiOk(1);
          InitApiData.this.notifyApiOkToReportService();
        }
      }
    });
  }

  public void setOnApiOkListener(onApiOKListener paramonApiOKListener)
  {
    this.mOnApiOkListener = paramonApiOKListener;
  }

  public void terminate()
  {
    this.isTerminated = true;
    this.mOnApiOkListener = null;
  }

  public static abstract interface onApiOKListener
  {
    public abstract void onApiOk(int paramInt);

    public abstract void onGetApiDataError();

    public abstract void onNeedFinishActivity();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.uilogic.InitApiData
 * JD-Core Version:    0.6.2
 */