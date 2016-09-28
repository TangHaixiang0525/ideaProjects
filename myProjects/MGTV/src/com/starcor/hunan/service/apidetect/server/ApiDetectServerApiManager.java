package com.starcor.hunan.service.apidetect.server;

import com.starcor.core.epgapi.params.AddCollectParams;
import com.starcor.core.epgapi.params.GetUserAuthV2Params;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.service.apidetect.http.ApiDetectSCHttpApiEngine;
import com.starcor.hunan.service.apidetect.task.subtask.ApiDetectSccmsApiGetEpgIndexTask;
import com.starcor.hunan.service.apidetect.task.subtask.ApiDetectSccmsApiGetEpgIndexTask.IApiDetectSccmsApiGetEpgDataTaskListener;
import com.starcor.hunan.service.apidetect.task.subtask.ApiDetectSccmsN3A2GetEpgDataTask;
import com.starcor.hunan.service.apidetect.task.subtask.ApiDetectSccmsN3A2GetEpgDataTask.IApiDetectSccmsApiN3A2GetEpgDataTaskListener;
import com.starcor.mgtv.api.MgtvApiCheckNetStateTask;
import com.starcor.mgtv.api.MgtvApiCheckNetStateTask.IMgtvApiCheckNetStateTaskkListener;
import com.starcor.mgtv.api.MgtvApiGetMQTTClientInfoTask;
import com.starcor.mgtv.api.MgtvApiGetMQTTClientInfoTask.IMgtvApiGetMQTTClientInfoTaskListener;
import com.starcor.mgtv.api.MgtvApiGetMessageBoardDataTask;
import com.starcor.mgtv.api.MgtvApiGetMessageBoardDataTask.IMgtvApiGetMessageBoardDataTaskListener;
import com.starcor.mgtv.api.MgtvApiGetMsgBoardSendRespondTask;
import com.starcor.mgtv.api.MgtvApiGetMsgBoardSendRespondTask.IMgtvApiGetMsgBoardSendRespondTaskListener;
import com.starcor.mgtv.api.MgtvApiGetSystemMessageTask;
import com.starcor.mgtv.api.MgtvApiGetSystemMessageTask.IMgtvApiGetSystemMessageTaskListener;
import com.starcor.sccms.api.SccmsApiAAABuyProductTask;
import com.starcor.sccms.api.SccmsApiAAABuyProductTask.ISccmsApiAAABuyProductTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetLicenseTask;
import com.starcor.sccms.api.SccmsApiAAAGetLicenseTask.ISccmsApiGetLicenseTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetOrderRecordListTask;
import com.starcor.sccms.api.SccmsApiAAAGetOrderRecordListTask.ISccmsApiAAAGetOrderRecordListTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetOrderStateTask;
import com.starcor.sccms.api.SccmsApiAAAGetOrderStateTask.ISccmsApiAAAGetOrderStateTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetPayChannelListTask;
import com.starcor.sccms.api.SccmsApiAAAGetPayChannelListTask.ISccmsApiAAAGetPayChannelListTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetPriceInfoTask;
import com.starcor.sccms.api.SccmsApiAAAGetPriceInfoTask.ISccmsApiAAAGetPriceInfoTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetPriceListTask;
import com.starcor.sccms.api.SccmsApiAAAGetPriceListTask.ISccmsApiAAAGetPriceListTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetProductInfoTask;
import com.starcor.sccms.api.SccmsApiAAAGetProductInfoTask.ISccmsApiAAAGetProductInfoTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetProductListTask;
import com.starcor.sccms.api.SccmsApiAAAGetProductListTask.ISccmsApiAAAGetProductListTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetUserInfoTask;
import com.starcor.sccms.api.SccmsApiAAAGetUserInfoTask.ISccmsApiGetUserCenterInfoTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetVipInfoTask;
import com.starcor.sccms.api.SccmsApiAAAGetVipInfoTask.ISccmsApiAAAGetVipInfoTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetVipListTask;
import com.starcor.sccms.api.SccmsApiAAAGetVipListTask.ISccmsApiAAAGetVipListTaskListener;
import com.starcor.sccms.api.SccmsApiAAARechargeTask;
import com.starcor.sccms.api.SccmsApiAAARechargeTask.ISccmsApiAAARechargeTaskListener;
import com.starcor.sccms.api.SccmsApiAAAVerifyLicenseTask;
import com.starcor.sccms.api.SccmsApiAAAVerifyLicenseTask.ISccmsApiVerifyLicenseTaskListener;
import com.starcor.sccms.api.SccmsApiAddCatchVideoRecordTask;
import com.starcor.sccms.api.SccmsApiAddCatchVideoRecordTask.ISccmsApiAddCatchVideoRecordTaskListener;
import com.starcor.sccms.api.SccmsApiAddCollectRecordTask;
import com.starcor.sccms.api.SccmsApiAddCollectRecordTask.ISccmsApiAddCollectRecordTaskListener;
import com.starcor.sccms.api.SccmsApiAddPlayRecordTask;
import com.starcor.sccms.api.SccmsApiAddPlayRecordTask.ISccmsApiAddPlayRecordTaskListener;
import com.starcor.sccms.api.SccmsApiCheckUiPackageUpdateTask;
import com.starcor.sccms.api.SccmsApiCheckUiPackageUpdateTask.ISccmsApiCheckUiPackageUpdateTaskListener;
import com.starcor.sccms.api.SccmsApiCheckUpdateTask;
import com.starcor.sccms.api.SccmsApiCheckUpdateTask.ISccmsApiCheckUpdateTaskListener;
import com.starcor.sccms.api.SccmsApiCheckValidByWebTokenTask;
import com.starcor.sccms.api.SccmsApiCheckValidByWebTokenTask.ISccmsApiCheckValidByWebTokenTaskListener;
import com.starcor.sccms.api.SccmsApiCheckWebTokenTask;
import com.starcor.sccms.api.SccmsApiCheckWebTokenTask.ISccmsApiCheckWebTokenTaskListener;
import com.starcor.sccms.api.SccmsApiConvertIDTask;
import com.starcor.sccms.api.SccmsApiConvertIDTask.ISccmsApiConvertIDTaskListener;
import com.starcor.sccms.api.SccmsApiDelCatchVideoRecordTask;
import com.starcor.sccms.api.SccmsApiDelCatchVideoRecordTask.ISccmsApiDelCatchVideoRecordTaskListener;
import com.starcor.sccms.api.SccmsApiDelCollectRecordTask;
import com.starcor.sccms.api.SccmsApiDelCollectRecordTask.ISccmsApiDelColllectRecordTaskListener;
import com.starcor.sccms.api.SccmsApiDelPlayRecordTask;
import com.starcor.sccms.api.SccmsApiDelPlayRecordTask.ISccmsApiDelPlayRecordTaskListener;
import com.starcor.sccms.api.SccmsApiGetAdInfoByAdPosListTask;
import com.starcor.sccms.api.SccmsApiGetAdInfoByAdPosListTask.ISccmsApiGetAdInfoByAdPosListTaskListener;
import com.starcor.sccms.api.SccmsApiGetAdInfoByAdPosTask;
import com.starcor.sccms.api.SccmsApiGetAdInfoByAdPosTask.ISccmsApiGetAdInfoByAdPosTaskListener;
import com.starcor.sccms.api.SccmsApiGetAdInfoByVideoIdTask;
import com.starcor.sccms.api.SccmsApiGetAdInfoByVideoIdTask.ISccmsApiGetAdInfoByVideoIdTaskListener;
import com.starcor.sccms.api.SccmsApiGetAdInfoTask;
import com.starcor.sccms.api.SccmsApiGetAdInfoTask.ISccmsApiGetAdInfoTaskListener;
import com.starcor.sccms.api.SccmsApiGetAdPosByPageIdTask;
import com.starcor.sccms.api.SccmsApiGetAdPosByPageIdTask.ISccmsApiGetAdPosByPageIdTaskListener;
import com.starcor.sccms.api.SccmsApiGetAgreementTask;
import com.starcor.sccms.api.SccmsApiGetAgreementTask.ISccmsApiGetAgreementTaskListener;
import com.starcor.sccms.api.SccmsApiGetAppDownloadUrlTask;
import com.starcor.sccms.api.SccmsApiGetAppDownloadUrlTask.ISccmsApiGetAppDownloadUrlTaskListener;
import com.starcor.sccms.api.SccmsApiGetAppInfoTask;
import com.starcor.sccms.api.SccmsApiGetAppInfoTask.ISccmsApiGetAppInfoTaskListener;
import com.starcor.sccms.api.SccmsApiGetAppListTask;
import com.starcor.sccms.api.SccmsApiGetAppListTask.ISccmsApiGetAppListTaskListener;
import com.starcor.sccms.api.SccmsApiGetAssetsInfoByVideoIdTask;
import com.starcor.sccms.api.SccmsApiGetAssetsInfoByVideoIdTask.ISccmsApiGetAssetsInfoByVideoIdTaskListener;
import com.starcor.sccms.api.SccmsApiGetCatchVideoRecordTask;
import com.starcor.sccms.api.SccmsApiGetCatchVideoRecordTask.ISccmsApiGetCatchVideoRecordTaskListener;
import com.starcor.sccms.api.SccmsApiGetChannelListV2Task;
import com.starcor.sccms.api.SccmsApiGetChannelListV2Task.ISccmsApiGetChannelListV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetCityInfoTask;
import com.starcor.sccms.api.SccmsApiGetCityInfoTask.ISccmsApiGetCityListTaskListener;
import com.starcor.sccms.api.SccmsApiGetCityListTask;
import com.starcor.sccms.api.SccmsApiGetCityListTask.ISccmsApiGetCityListTaskListener;
import com.starcor.sccms.api.SccmsApiGetCollectRecordTask;
import com.starcor.sccms.api.SccmsApiGetCollectRecordTask.ISccmsApiGetCollectRecordTaskListener;
import com.starcor.sccms.api.SccmsApiGetCommonVideoIdTask;
import com.starcor.sccms.api.SccmsApiGetCommonVideoIdTask.ISccmsApiGetCommonVideoIdTaskListener;
import com.starcor.sccms.api.SccmsApiGetFAQListTask;
import com.starcor.sccms.api.SccmsApiGetFAQListTask.ISccmsApiGetFAQListTaskListener;
import com.starcor.sccms.api.SccmsApiGetFilmListByLabelTask;
import com.starcor.sccms.api.SccmsApiGetFilmListByLabelTask.ISccmsApiGetFilmListByLabelTaskListener;
import com.starcor.sccms.api.SccmsApiGetHotAppListTask;
import com.starcor.sccms.api.SccmsApiGetHotAppListTask.ISccmsApiGetHotAppListTaskListener;
import com.starcor.sccms.api.SccmsApiGetHotWordsTask;
import com.starcor.sccms.api.SccmsApiGetHotWordsTask.ISccmsApiGetHotWordsTaskListener;
import com.starcor.sccms.api.SccmsApiGetIndexListByCategoryTask;
import com.starcor.sccms.api.SccmsApiGetIndexListByCategoryTask.ISccmsApiGetIndexListByCategoryListener;
import com.starcor.sccms.api.SccmsApiGetInitMetaDataTask;
import com.starcor.sccms.api.SccmsApiGetInitMetaDataTask.ISccmsApiGetInitMetaDataTaskListener;
import com.starcor.sccms.api.SccmsApiGetMediaAssetsInfoTask;
import com.starcor.sccms.api.SccmsApiGetMediaAssetsInfoTask.ISccmsApiGetMediaAssetsInfoTaskListener;
import com.starcor.sccms.api.SccmsApiGetPlayRecordTask;
import com.starcor.sccms.api.SccmsApiGetPlayRecordTask.ISccmsApiGetPlayRecordTaskListener;
import com.starcor.sccms.api.SccmsApiGetPlaybillTask;
import com.starcor.sccms.api.SccmsApiGetPlaybillTask.ISccmsApiGetPlaybillTaskListener;
import com.starcor.sccms.api.SccmsApiGetPreInstallListTask;
import com.starcor.sccms.api.SccmsApiGetPreInstallListTask.ISccmsApiGetPreInstallListTaskListener;
import com.starcor.sccms.api.SccmsApiGetRelevantFilmsTask;
import com.starcor.sccms.api.SccmsApiGetRelevantFilmsTask.ISccmsApiGetRelevantFilmsTaskListener;
import com.starcor.sccms.api.SccmsApiGetReplayRecommendDataTask;
import com.starcor.sccms.api.SccmsApiGetReplayRecommendDataTask.ISccmsApiGetReplayRecommendDataTaskListener;
import com.starcor.sccms.api.SccmsApiGetSkinTask;
import com.starcor.sccms.api.SccmsApiGetSkinTask.ISccmsApiGetSkinTaskListener;
import com.starcor.sccms.api.SccmsApiGetSpecialTopicColumnTreeTask;
import com.starcor.sccms.api.SccmsApiGetSpecialTopicColumnTreeTask.ISccmsApiGetSpecialTopicColumnTreeTaskListener;
import com.starcor.sccms.api.SccmsApiGetSpecialTopicPkgContentLstTask;
import com.starcor.sccms.api.SccmsApiGetSpecialTopicPkgContentLstTask.ISccmsApiGetSearchSpecialTopicPkgLstTaskListener;
import com.starcor.sccms.api.SccmsApiGetSpecialTopicPutTask;
import com.starcor.sccms.api.SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener;
import com.starcor.sccms.api.SccmsApiGetSpeedTestMissionInfoTask;
import com.starcor.sccms.api.SccmsApiGetSpeedTestMissionInfoTask.ISccmsApiGetSpeedTestMissionInfoTaskListener;
import com.starcor.sccms.api.SccmsApiGetStarCollectDataTask;
import com.starcor.sccms.api.SccmsApiGetStarCollectDataTask.ISccmsApiGetStarCollectDataTaskListener;
import com.starcor.sccms.api.SccmsApiGetUiPackageTask;
import com.starcor.sccms.api.SccmsApiGetUiPackageTask.ISccmsApiGetUiPackageTaskListener;
import com.starcor.sccms.api.SccmsApiGetUninstallListTask;
import com.starcor.sccms.api.SccmsApiGetUninstallListTask.ISccmsApiGetUninstallListTaskListener;
import com.starcor.sccms.api.SccmsApiGetUserAuthTask;
import com.starcor.sccms.api.SccmsApiGetUserAuthTask.ISccmsApiGetUserAuthTaskListener;
import com.starcor.sccms.api.SccmsApiGetUserAuthV2Task;
import com.starcor.sccms.api.SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoIdByMgtvAssetIdTask;
import com.starcor.sccms.api.SccmsApiGetVideoIdByMgtvAssetIdTask.ISccmsApiGetVideoIdByMgtvAssetIdTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoIndexListTask;
import com.starcor.sccms.api.SccmsApiGetVideoIndexListTask.ISccmsApiGetVideoIndexListTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoInfoV2Task;
import com.starcor.sccms.api.SccmsApiGetVideoInfoV2Task.ISccmsApiGetVideoInfoV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoLabelTypeTask;
import com.starcor.sccms.api.SccmsApiGetVideoLabelTypeTask.ISccmsApiGetVideoLabelTypeTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoListByLabelTask;
import com.starcor.sccms.api.SccmsApiGetVideoListByLabelTask.ISccmsApiGetVideoListByLabelTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoListTask;
import com.starcor.sccms.api.SccmsApiGetVideoListTask.ISccmsApiGetVideoListTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoScoreByUserIdTask;
import com.starcor.sccms.api.SccmsApiGetVideoScoreByUserIdTask.ISccmsApiGetVideoScoreByUserIdTaskListener;
import com.starcor.sccms.api.SccmsApiGetWeatherInfoTask;
import com.starcor.sccms.api.SccmsApiGetWeatherInfoTask.ISccmsApiGetWeatherInfoTaskListener;
import com.starcor.sccms.api.SccmsApiReportAdStateTask;
import com.starcor.sccms.api.SccmsApiReportSpeedTestResultTask;
import com.starcor.sccms.api.SccmsApiReportSpeedTestResultTask.ISccmsApiReportSpeedTestResultTaskListener;
import com.starcor.sccms.api.SccmsApiRequestVideoPlayTask;
import com.starcor.sccms.api.SccmsApiRequestVideoPlayTask.ISccmsApiRequestVideoPlayTaskListener;
import com.starcor.sccms.api.SccmsApiRequestVideoPlayV2Task;
import com.starcor.sccms.api.SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener;
import com.starcor.sccms.api.SccmsApiSearchActorsAndDirectorsByPinyinTask;
import com.starcor.sccms.api.SccmsApiSearchActorsAndDirectorsByPinyinTask.ISccmsApiSearchActorsAndDirectorsByPinyinTaskListener;
import com.starcor.sccms.api.SccmsApiSearchAppTask;
import com.starcor.sccms.api.SccmsApiSearchAppTask.ISccmsApiSearchAppTaskListener;
import com.starcor.sccms.api.SccmsApiSearchVideoByPinyinTask;
import com.starcor.sccms.api.SccmsApiSearchVideoByPinyinTask.ISccmsApiSearchVideoByPinyinTaskListener;
import com.starcor.sccms.api.SccmsApiSyncTimeTask;
import com.starcor.sccms.api.SccmsApiSyncTimeTask.ISccmsApiSyncTimeTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterBindMobileTask;
import com.starcor.sccms.api.SccmsApiUserCenterBindMobileTask.ISccmsApiBindMobileTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterChangePwdTask;
import com.starcor.sccms.api.SccmsApiUserCenterChangePwdTask.ISccmsApiUserCenterChangePwdTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterCheckReturnTask;
import com.starcor.sccms.api.SccmsApiUserCenterCheckReturnTask.ISccmsApiCheckUserCenterReturnTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterGet3rdLoginUrlTask;
import com.starcor.sccms.api.SccmsApiUserCenterGet3rdLoginUrlTask.ISccmsApiUserCenterGet3rdLoginUrlTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterGetMobileCodeByUserNameTask;
import com.starcor.sccms.api.SccmsApiUserCenterGetMobileCodeByUserNameTask.ISccmsApiGetMobileCodeByUserNameTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterGetMobileCodeTask;
import com.starcor.sccms.api.SccmsApiUserCenterGetMobileCodeTask.ISccmsApiGetMobileMsgAuthTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterGetWebChatLoginPicTask;
import com.starcor.sccms.api.SccmsApiUserCenterGetWebChatLoginPicTask.ISccmsApiGetWebChatLoginPicTaskListenter;
import com.starcor.sccms.api.SccmsApiUserCenterLoginTask;
import com.starcor.sccms.api.SccmsApiUserCenterLoginTask.ISccmsApiUserCenterLoginTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterLogoutTask;
import com.starcor.sccms.api.SccmsApiUserCenterLogoutTask.ISccmsApiUserCenterLogoutTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterRegisterTask;
import com.starcor.sccms.api.SccmsApiUserCenterRegisterTask.ISccmsApiUserCenterRegisterTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterResetPwdTask;
import com.starcor.sccms.api.SccmsApiUserCenterResetPwdTask.ISccmsApiUserCenterResetPwdTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterUnBindMobileTask;
import com.starcor.sccms.api.SccmsApiUserCenterUnBindMobileTask.ISccmsApiUnBindMobileTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterUnbindWebchatTask;
import com.starcor.sccms.api.SccmsApiUserCenterUnbindWebchatTask.ISccmsApiUserCenterUnbindWebchatTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterVerifyTokenTask;
import com.starcor.sccms.api.SccmsApiUserCenterVerifyTokenTask.ISccmsApiUserCenterVerifyTokenTaskListener;
import com.starcor.sccms.api.SccmsApiUserLoginTask;
import com.starcor.sccms.api.SccmsApiUserLoginTask.ISccmsApiUserLoginTaskListener;
import java.util.List;

public class ApiDetectServerApiManager
{
  private static final String TAG = ApiDetectServerApiManager.class.getSimpleName();
  private static ApiDetectServerApiManager apiManager = null;
  private ApiDetectSCHttpApiEngine httpApiEngine = null;

  public static ApiDetectServerApiManager i()
  {
    if (apiManager == null)
    {
      Logger.i(TAG, "i() first create");
      apiManager = new ApiDetectServerApiManager();
    }
    return apiManager;
  }

  public static boolean isInstanced()
  {
    return apiManager != null;
  }

  public int APIAAABuyProduct(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, SccmsApiAAABuyProductTask.ISccmsApiAAABuyProductTaskListener paramISccmsApiAAABuyProductTaskListener)
  {
    SccmsApiAAABuyProductTask localSccmsApiAAABuyProductTask = new SccmsApiAAABuyProductTask(0, paramString1, paramString2, paramInt1, paramInt2, paramString3);
    localSccmsApiAAABuyProductTask.setListener(paramISccmsApiAAABuyProductTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAABuyProductTask);
  }

  public int APIAAAGetLicense(String paramString, SccmsApiAAAGetLicenseTask.ISccmsApiGetLicenseTaskListener paramISccmsApiGetLicenseTaskListener)
  {
    SccmsApiAAAGetLicenseTask localSccmsApiAAAGetLicenseTask = new SccmsApiAAAGetLicenseTask(paramString);
    localSccmsApiAAAGetLicenseTask.setListener(paramISccmsApiGetLicenseTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetLicenseTask);
  }

  public int APIAAAGetOrderRecordList(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, SccmsApiAAAGetOrderRecordListTask.ISccmsApiAAAGetOrderRecordListTaskListener paramISccmsApiAAAGetOrderRecordListTaskListener)
  {
    SccmsApiAAAGetOrderRecordListTask localSccmsApiAAAGetOrderRecordListTask = new SccmsApiAAAGetOrderRecordListTask(paramString1, paramString2, paramInt1, paramInt2, paramInt3, paramInt4);
    localSccmsApiAAAGetOrderRecordListTask.setListener(paramISccmsApiAAAGetOrderRecordListTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetOrderRecordListTask);
  }

  public int APIAAAGetOrderState(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4, SccmsApiAAAGetOrderStateTask.ISccmsApiAAAGetOrderStateTaskListener paramISccmsApiAAAGetOrderStateTaskListener)
  {
    SccmsApiAAAGetOrderStateTask localSccmsApiAAAGetOrderStateTask = new SccmsApiAAAGetOrderStateTask(paramString1, paramString2, paramInt, paramString3, paramString4);
    localSccmsApiAAAGetOrderStateTask.setListener(paramISccmsApiAAAGetOrderStateTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetOrderStateTask);
  }

  public int APIAAAGetPayChannelList(String paramString1, String paramString2, SccmsApiAAAGetPayChannelListTask.ISccmsApiAAAGetPayChannelListTaskListener paramISccmsApiAAAGetPayChannelListTaskListener)
  {
    SccmsApiAAAGetPayChannelListTask localSccmsApiAAAGetPayChannelListTask = new SccmsApiAAAGetPayChannelListTask(paramString1, paramString2);
    localSccmsApiAAAGetPayChannelListTask.setListener(paramISccmsApiAAAGetPayChannelListTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetPayChannelListTask);
  }

  public int APIAAAGetPriceInfo(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, SccmsApiAAAGetPriceInfoTask.ISccmsApiAAAGetPriceInfoTaskListener paramISccmsApiAAAGetPriceInfoTaskListener)
  {
    SccmsApiAAAGetPriceInfoTask localSccmsApiAAAGetPriceInfoTask = new SccmsApiAAAGetPriceInfoTask(paramString1, paramString2, paramInt1, paramInt2, paramString3);
    localSccmsApiAAAGetPriceInfoTask.setListener(paramISccmsApiAAAGetPriceInfoTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetPriceInfoTask);
  }

  public int APIAAAGetPriceList(String paramString, SccmsApiAAAGetPriceListTask.ISccmsApiAAAGetPriceListTaskListener paramISccmsApiAAAGetPriceListTaskListener)
  {
    SccmsApiAAAGetPriceListTask localSccmsApiAAAGetPriceListTask = new SccmsApiAAAGetPriceListTask(paramString);
    localSccmsApiAAAGetPriceListTask.setListener(paramISccmsApiAAAGetPriceListTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetPriceListTask);
  }

  public int APIAAAGetProductInfo(String paramString1, String paramString2, int paramInt1, int paramInt2, SccmsApiAAAGetProductInfoTask.ISccmsApiAAAGetProductInfoTaskListener paramISccmsApiAAAGetProductInfoTaskListener)
  {
    SccmsApiAAAGetProductInfoTask localSccmsApiAAAGetProductInfoTask = new SccmsApiAAAGetProductInfoTask(paramString1, paramString2, paramInt1, paramInt2);
    localSccmsApiAAAGetProductInfoTask.setListener(paramISccmsApiAAAGetProductInfoTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetProductInfoTask);
  }

  public int APIAAAGetProductList(String paramString1, String paramString2, int paramInt1, int paramInt2, SccmsApiAAAGetProductListTask.ISccmsApiAAAGetProductListTaskListener paramISccmsApiAAAGetProductListTaskListener)
  {
    SccmsApiAAAGetProductListTask localSccmsApiAAAGetProductListTask = new SccmsApiAAAGetProductListTask(paramString1, paramString2, paramInt1, paramInt2);
    localSccmsApiAAAGetProductListTask.setListener(paramISccmsApiAAAGetProductListTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetProductListTask);
  }

  public int APIAAAGetVipInfo(String paramString, int paramInt, SccmsApiAAAGetVipInfoTask.ISccmsApiAAAGetVipInfoTaskListener paramISccmsApiAAAGetVipInfoTaskListener)
  {
    SccmsApiAAAGetVipInfoTask localSccmsApiAAAGetVipInfoTask = new SccmsApiAAAGetVipInfoTask(paramString, paramInt);
    localSccmsApiAAAGetVipInfoTask.setListener(paramISccmsApiAAAGetVipInfoTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetVipInfoTask);
  }

  public int APIAAAGetVipList(String paramString, SccmsApiAAAGetVipListTask.ISccmsApiAAAGetVipListTaskListener paramISccmsApiAAAGetVipListTaskListener)
  {
    SccmsApiAAAGetVipListTask localSccmsApiAAAGetVipListTask = new SccmsApiAAAGetVipListTask(paramString);
    localSccmsApiAAAGetVipListTask.setListener(paramISccmsApiAAAGetVipListTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetVipListTask);
  }

  public int APIAAARecharge(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, String paramString4, String paramString5, SccmsApiAAARechargeTask.ISccmsApiAAARechargeTaskListener paramISccmsApiAAARechargeTaskListener)
  {
    SccmsApiAAARechargeTask localSccmsApiAAARechargeTask = new SccmsApiAAARechargeTask(paramString1, paramString2, paramInt1, paramInt2, paramString3, paramString4, paramString5);
    localSccmsApiAAARechargeTask.setListener(paramISccmsApiAAARechargeTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAARechargeTask);
  }

  public int APIAAAVerifyLicense(String paramString, SccmsApiAAAVerifyLicenseTask.ISccmsApiVerifyLicenseTaskListener paramISccmsApiVerifyLicenseTaskListener)
  {
    SccmsApiAAAVerifyLicenseTask localSccmsApiAAAVerifyLicenseTask = new SccmsApiAAAVerifyLicenseTask(paramString);
    localSccmsApiAAAVerifyLicenseTask.setListener(paramISccmsApiVerifyLicenseTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAAVerifyLicenseTask);
  }

  public int APIAddCatchVideoRecord(AddCollectParams paramAddCollectParams, SccmsApiAddCatchVideoRecordTask.ISccmsApiAddCatchVideoRecordTaskListener paramISccmsApiAddCatchVideoRecordTaskListener)
  {
    SccmsApiAddCatchVideoRecordTask localSccmsApiAddCatchVideoRecordTask = new SccmsApiAddCatchVideoRecordTask(paramAddCollectParams);
    localSccmsApiAddCatchVideoRecordTask.setListener(paramISccmsApiAddCatchVideoRecordTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAddCatchVideoRecordTask);
  }

  public int APIAddCollectRecord(AddCollectParams paramAddCollectParams, SccmsApiAddCollectRecordTask.ISccmsApiAddCollectRecordTaskListener paramISccmsApiAddCollectRecordTaskListener)
  {
    SccmsApiAddCollectRecordTask localSccmsApiAddCollectRecordTask = new SccmsApiAddCollectRecordTask(paramAddCollectParams);
    localSccmsApiAddCollectRecordTask.setListener(paramISccmsApiAddCollectRecordTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAddCollectRecordTask);
  }

  public int APIAddPlayRecord(AddCollectParams paramAddCollectParams, SccmsApiAddPlayRecordTask.ISccmsApiAddPlayRecordTaskListener paramISccmsApiAddPlayRecordTaskListener)
  {
    SccmsApiAddPlayRecordTask localSccmsApiAddPlayRecordTask = new SccmsApiAddPlayRecordTask(paramAddCollectParams);
    localSccmsApiAddPlayRecordTask.setListener(paramISccmsApiAddPlayRecordTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAddPlayRecordTask);
  }

  public int APICheckMgtvNetState(MgtvApiCheckNetStateTask.IMgtvApiCheckNetStateTaskkListener paramIMgtvApiCheckNetStateTaskkListener)
  {
    MgtvApiCheckNetStateTask localMgtvApiCheckNetStateTask = new MgtvApiCheckNetStateTask();
    localMgtvApiCheckNetStateTask.setListener(paramIMgtvApiCheckNetStateTaskkListener);
    return this.httpApiEngine.addTask(localMgtvApiCheckNetStateTask);
  }

  public int APICheckUiPackageUpdate(String paramString1, String paramString2, SccmsApiCheckUiPackageUpdateTask.ISccmsApiCheckUiPackageUpdateTaskListener paramISccmsApiCheckUiPackageUpdateTaskListener)
  {
    SccmsApiCheckUiPackageUpdateTask localSccmsApiCheckUiPackageUpdateTask = new SccmsApiCheckUiPackageUpdateTask(paramString1, paramString2);
    localSccmsApiCheckUiPackageUpdateTask.setListener(paramISccmsApiCheckUiPackageUpdateTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiCheckUiPackageUpdateTask);
  }

  public int APICheckUpdate(SccmsApiCheckUpdateTask.ISccmsApiCheckUpdateTaskListener paramISccmsApiCheckUpdateTaskListener)
  {
    SccmsApiCheckUpdateTask localSccmsApiCheckUpdateTask = new SccmsApiCheckUpdateTask();
    localSccmsApiCheckUpdateTask.setListener(paramISccmsApiCheckUpdateTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiCheckUpdateTask);
  }

  public int APICheckValidByWebToken(SccmsApiCheckValidByWebTokenTask.ISccmsApiCheckValidByWebTokenTaskListener paramISccmsApiCheckValidByWebTokenTaskListener)
  {
    SccmsApiCheckValidByWebTokenTask localSccmsApiCheckValidByWebTokenTask = new SccmsApiCheckValidByWebTokenTask();
    localSccmsApiCheckValidByWebTokenTask.setListener(paramISccmsApiCheckValidByWebTokenTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiCheckValidByWebTokenTask);
  }

  public int APICheckWebToken(SccmsApiCheckWebTokenTask.ISccmsApiCheckWebTokenTaskListener paramISccmsApiCheckWebTokenTaskListener)
  {
    SccmsApiCheckWebTokenTask localSccmsApiCheckWebTokenTask = new SccmsApiCheckWebTokenTask();
    localSccmsApiCheckWebTokenTask.setListener(paramISccmsApiCheckWebTokenTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiCheckWebTokenTask);
  }

  public int APIConvertID(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, SccmsApiConvertIDTask.ISccmsApiConvertIDTaskListener paramISccmsApiConvertIDTaskListener)
  {
    SccmsApiConvertIDTask localSccmsApiConvertIDTask = new SccmsApiConvertIDTask(paramString1, paramString2, paramInt1, paramInt2, paramInt3);
    localSccmsApiConvertIDTask.setListener(paramISccmsApiConvertIDTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiConvertIDTask);
  }

  public int APIDelCatchVideoRecord(String paramString, SccmsApiDelCatchVideoRecordTask.ISccmsApiDelCatchVideoRecordTaskListener paramISccmsApiDelCatchVideoRecordTaskListener)
  {
    SccmsApiDelCatchVideoRecordTask localSccmsApiDelCatchVideoRecordTask = new SccmsApiDelCatchVideoRecordTask(paramString);
    localSccmsApiDelCatchVideoRecordTask.setListener(paramISccmsApiDelCatchVideoRecordTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiDelCatchVideoRecordTask);
  }

  public int APIDelCollectRecord(String paramString, SccmsApiDelCollectRecordTask.ISccmsApiDelColllectRecordTaskListener paramISccmsApiDelColllectRecordTaskListener)
  {
    SccmsApiDelCollectRecordTask localSccmsApiDelCollectRecordTask = new SccmsApiDelCollectRecordTask(paramString);
    localSccmsApiDelCollectRecordTask.setListener(paramISccmsApiDelColllectRecordTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiDelCollectRecordTask);
  }

  public int APIDelPlayRecord(String paramString, SccmsApiDelPlayRecordTask.ISccmsApiDelPlayRecordTaskListener paramISccmsApiDelPlayRecordTaskListener)
  {
    SccmsApiDelPlayRecordTask localSccmsApiDelPlayRecordTask = new SccmsApiDelPlayRecordTask(paramString);
    localSccmsApiDelPlayRecordTask.setListener(paramISccmsApiDelPlayRecordTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiDelPlayRecordTask);
  }

  public int APIGetAdInfoByAdPos(String paramString, SccmsApiGetAdInfoByAdPosTask.ISccmsApiGetAdInfoByAdPosTaskListener paramISccmsApiGetAdInfoByAdPosTaskListener)
  {
    SccmsApiGetAdInfoByAdPosTask localSccmsApiGetAdInfoByAdPosTask = new SccmsApiGetAdInfoByAdPosTask(paramString);
    localSccmsApiGetAdInfoByAdPosTask.setListener(paramISccmsApiGetAdInfoByAdPosTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetAdInfoByAdPosTask);
  }

  public int APIGetAdInfoByAdPosList(SccmsApiGetAdInfoByAdPosListTask.ISccmsApiGetAdInfoByAdPosListTaskListener paramISccmsApiGetAdInfoByAdPosListTaskListener, String[] paramArrayOfString)
  {
    SccmsApiGetAdInfoByAdPosListTask localSccmsApiGetAdInfoByAdPosListTask = new SccmsApiGetAdInfoByAdPosListTask(paramArrayOfString);
    localSccmsApiGetAdInfoByAdPosListTask.setListener(paramISccmsApiGetAdInfoByAdPosListTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetAdInfoByAdPosListTask);
  }

  public int APIGetAdInfoByVideoId(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, SccmsApiGetAdInfoByVideoIdTask.ISccmsApiGetAdInfoByVideoIdTaskListener paramISccmsApiGetAdInfoByVideoIdTaskListener)
  {
    SccmsApiGetAdInfoByVideoIdTask localSccmsApiGetAdInfoByVideoIdTask = new SccmsApiGetAdInfoByVideoIdTask(paramString1, paramInt, paramString2, paramString3, paramString4);
    localSccmsApiGetAdInfoByVideoIdTask.setListener(paramISccmsApiGetAdInfoByVideoIdTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetAdInfoByVideoIdTask);
  }

  public int APIGetAdInfoData(String paramString, SccmsApiGetAdInfoTask.ISccmsApiGetAdInfoTaskListener paramISccmsApiGetAdInfoTaskListener)
  {
    SccmsApiGetAdInfoTask localSccmsApiGetAdInfoTask = new SccmsApiGetAdInfoTask(paramString);
    localSccmsApiGetAdInfoTask.setListener(paramISccmsApiGetAdInfoTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetAdInfoTask);
  }

  public int APIGetAdPosByPageId(String paramString, SccmsApiGetAdPosByPageIdTask.ISccmsApiGetAdPosByPageIdTaskListener paramISccmsApiGetAdPosByPageIdTaskListener)
  {
    SccmsApiGetAdPosByPageIdTask localSccmsApiGetAdPosByPageIdTask = new SccmsApiGetAdPosByPageIdTask(paramString);
    localSccmsApiGetAdPosByPageIdTask.setListener(paramISccmsApiGetAdPosByPageIdTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetAdPosByPageIdTask);
  }

  public int APIGetAgreements(SccmsApiGetAgreementTask.ISccmsApiGetAgreementTaskListener paramISccmsApiGetAgreementTaskListener)
  {
    SccmsApiGetAgreementTask localSccmsApiGetAgreementTask = new SccmsApiGetAgreementTask();
    localSccmsApiGetAgreementTask.setListener(paramISccmsApiGetAgreementTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetAgreementTask);
  }

  public int APIGetAppDownloadUrl(String paramString, SccmsApiGetAppDownloadUrlTask.ISccmsApiGetAppDownloadUrlTaskListener paramISccmsApiGetAppDownloadUrlTaskListener)
  {
    SccmsApiGetAppDownloadUrlTask localSccmsApiGetAppDownloadUrlTask = new SccmsApiGetAppDownloadUrlTask(paramString);
    localSccmsApiGetAppDownloadUrlTask.setListener(paramISccmsApiGetAppDownloadUrlTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetAppDownloadUrlTask);
  }

  public int APIGetAppInfo(String paramString1, String paramString2, SccmsApiGetAppInfoTask.ISccmsApiGetAppInfoTaskListener paramISccmsApiGetAppInfoTaskListener)
  {
    SccmsApiGetAppInfoTask localSccmsApiGetAppInfoTask = new SccmsApiGetAppInfoTask(paramString1, paramString2);
    localSccmsApiGetAppInfoTask.setListener(paramISccmsApiGetAppInfoTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetAppInfoTask);
  }

  public int APIGetAppList(String paramString, int paramInt1, int paramInt2, SccmsApiGetAppListTask.ISccmsApiGetAppListTaskListener paramISccmsApiGetAppListTaskListener)
  {
    SccmsApiGetAppListTask localSccmsApiGetAppListTask = new SccmsApiGetAppListTask(paramString, paramInt1, paramInt2);
    localSccmsApiGetAppListTask.setListener(paramISccmsApiGetAppListTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetAppListTask);
  }

  public int APIGetAssetsInfoByVideoId(String paramString, SccmsApiGetAssetsInfoByVideoIdTask.ISccmsApiGetAssetsInfoByVideoIdTaskListener paramISccmsApiGetAssetsInfoByVideoIdTaskListener)
  {
    SccmsApiGetAssetsInfoByVideoIdTask localSccmsApiGetAssetsInfoByVideoIdTask = new SccmsApiGetAssetsInfoByVideoIdTask(paramString);
    localSccmsApiGetAssetsInfoByVideoIdTask.setListener(paramISccmsApiGetAssetsInfoByVideoIdTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetAssetsInfoByVideoIdTask);
  }

  public int APIGetCatchVideoRecord(SccmsApiGetCatchVideoRecordTask.ISccmsApiGetCatchVideoRecordTaskListener paramISccmsApiGetCatchVideoRecordTaskListener)
  {
    SccmsApiGetCatchVideoRecordTask localSccmsApiGetCatchVideoRecordTask = new SccmsApiGetCatchVideoRecordTask();
    localSccmsApiGetCatchVideoRecordTask.setListener(paramISccmsApiGetCatchVideoRecordTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetCatchVideoRecordTask);
  }

  public int APIGetChannelListV2(String paramString1, String paramString2, SccmsApiGetChannelListV2Task.ISccmsApiGetChannelListV2TaskListener paramISccmsApiGetChannelListV2TaskListener)
  {
    SccmsApiGetChannelListV2Task localSccmsApiGetChannelListV2Task = new SccmsApiGetChannelListV2Task(paramString1, paramString2);
    localSccmsApiGetChannelListV2Task.setListener(paramISccmsApiGetChannelListV2TaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetChannelListV2Task);
  }

  public int APIGetCityInfo(String paramString, SccmsApiGetCityInfoTask.ISccmsApiGetCityListTaskListener paramISccmsApiGetCityListTaskListener)
  {
    SccmsApiGetCityInfoTask localSccmsApiGetCityInfoTask = new SccmsApiGetCityInfoTask(paramString);
    localSccmsApiGetCityInfoTask.setListener(paramISccmsApiGetCityListTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetCityInfoTask);
  }

  public int APIGetCityList(String paramString, SccmsApiGetCityListTask.ISccmsApiGetCityListTaskListener paramISccmsApiGetCityListTaskListener)
  {
    SccmsApiGetCityListTask localSccmsApiGetCityListTask = new SccmsApiGetCityListTask(paramString);
    localSccmsApiGetCityListTask.setListener(paramISccmsApiGetCityListTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetCityListTask);
  }

  public int APIGetCollectRecord(SccmsApiGetCollectRecordTask.ISccmsApiGetCollectRecordTaskListener paramISccmsApiGetCollectRecordTaskListener)
  {
    SccmsApiGetCollectRecordTask localSccmsApiGetCollectRecordTask = new SccmsApiGetCollectRecordTask();
    localSccmsApiGetCollectRecordTask.setListener(paramISccmsApiGetCollectRecordTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetCollectRecordTask);
  }

  public int APIGetCommonVideoId(String paramString1, String paramString2, String paramString3, String paramString4, SccmsApiGetCommonVideoIdTask.ISccmsApiGetCommonVideoIdTaskListener paramISccmsApiGetCommonVideoIdTaskListener)
  {
    SccmsApiGetCommonVideoIdTask localSccmsApiGetCommonVideoIdTask = new SccmsApiGetCommonVideoIdTask(paramString1, paramString2, paramString3, paramString4);
    localSccmsApiGetCommonVideoIdTask.setListener(paramISccmsApiGetCommonVideoIdTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetCommonVideoIdTask);
  }

  public int APIGetEpgIndex(ApiDetectSccmsApiGetEpgIndexTask.IApiDetectSccmsApiGetEpgDataTaskListener paramIApiDetectSccmsApiGetEpgDataTaskListener)
  {
    ApiDetectSccmsApiGetEpgIndexTask localApiDetectSccmsApiGetEpgIndexTask = new ApiDetectSccmsApiGetEpgIndexTask();
    localApiDetectSccmsApiGetEpgIndexTask.setListener(paramIApiDetectSccmsApiGetEpgDataTaskListener);
    return this.httpApiEngine.addTask(localApiDetectSccmsApiGetEpgIndexTask);
  }

  public int APIGetFAQList(SccmsApiGetFAQListTask.ISccmsApiGetFAQListTaskListener paramISccmsApiGetFAQListTaskListener)
  {
    SccmsApiGetFAQListTask localSccmsApiGetFAQListTask = new SccmsApiGetFAQListTask();
    localSccmsApiGetFAQListTask.setListener(paramISccmsApiGetFAQListTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetFAQListTask);
  }

  public int APIGetFilmListByLabel(String paramString1, int paramInt1, int paramInt2, int paramInt3, String paramString2, SccmsApiGetFilmListByLabelTask.ISccmsApiGetFilmListByLabelTaskListener paramISccmsApiGetFilmListByLabelTaskListener)
  {
    SccmsApiGetFilmListByLabelTask localSccmsApiGetFilmListByLabelTask = new SccmsApiGetFilmListByLabelTask(paramString1, paramInt1, paramInt2, paramInt3, paramString2);
    localSccmsApiGetFilmListByLabelTask.setListener(paramISccmsApiGetFilmListByLabelTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetFilmListByLabelTask);
  }

  public int APIGetHotAppList(int paramInt, SccmsApiGetHotAppListTask.ISccmsApiGetHotAppListTaskListener paramISccmsApiGetHotAppListTaskListener)
  {
    SccmsApiGetHotAppListTask localSccmsApiGetHotAppListTask = new SccmsApiGetHotAppListTask(paramInt);
    localSccmsApiGetHotAppListTask.setListener(paramISccmsApiGetHotAppListTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetHotAppListTask);
  }

  public int APIGetHotWords(int paramInt1, int paramInt2, SccmsApiGetHotWordsTask.ISccmsApiGetHotWordsTaskListener paramISccmsApiGetHotWordsTaskListener)
  {
    SccmsApiGetHotWordsTask localSccmsApiGetHotWordsTask = new SccmsApiGetHotWordsTask(paramInt1, paramInt2);
    localSccmsApiGetHotWordsTask.setListener(paramISccmsApiGetHotWordsTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetHotWordsTask);
  }

  public int APIGetIndexListByCategory(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, SccmsApiGetIndexListByCategoryTask.ISccmsApiGetIndexListByCategoryListener paramISccmsApiGetIndexListByCategoryListener)
  {
    SccmsApiGetIndexListByCategoryTask localSccmsApiGetIndexListByCategoryTask = new SccmsApiGetIndexListByCategoryTask(paramString1, paramString2, paramString3, paramInt1, paramInt2);
    localSccmsApiGetIndexListByCategoryTask.setListener(paramISccmsApiGetIndexListByCategoryListener);
    return this.httpApiEngine.addTask(localSccmsApiGetIndexListByCategoryTask);
  }

  public int APIGetInitMetaData(SccmsApiGetInitMetaDataTask.ISccmsApiGetInitMetaDataTaskListener paramISccmsApiGetInitMetaDataTaskListener)
  {
    SccmsApiGetInitMetaDataTask localSccmsApiGetInitMetaDataTask = new SccmsApiGetInitMetaDataTask();
    localSccmsApiGetInitMetaDataTask.setListener(paramISccmsApiGetInitMetaDataTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetInitMetaDataTask);
  }

  public int APIGetMediaAssetsInfo(String paramString, SccmsApiGetMediaAssetsInfoTask.ISccmsApiGetMediaAssetsInfoTaskListener paramISccmsApiGetMediaAssetsInfoTaskListener)
  {
    SccmsApiGetMediaAssetsInfoTask localSccmsApiGetMediaAssetsInfoTask = new SccmsApiGetMediaAssetsInfoTask(paramString);
    localSccmsApiGetMediaAssetsInfoTask.setListener(paramISccmsApiGetMediaAssetsInfoTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetMediaAssetsInfoTask);
  }

  public int APIGetMessageBoardData(MgtvApiGetMessageBoardDataTask.IMgtvApiGetMessageBoardDataTaskListener paramIMgtvApiGetMessageBoardDataTaskListener)
  {
    MgtvApiGetMessageBoardDataTask localMgtvApiGetMessageBoardDataTask = new MgtvApiGetMessageBoardDataTask();
    localMgtvApiGetMessageBoardDataTask.setListener(paramIMgtvApiGetMessageBoardDataTaskListener);
    return this.httpApiEngine.addTask(localMgtvApiGetMessageBoardDataTask);
  }

  public int APIGetMsgBoardSendRespond(String paramString, MgtvApiGetMsgBoardSendRespondTask.IMgtvApiGetMsgBoardSendRespondTaskListener paramIMgtvApiGetMsgBoardSendRespondTaskListener)
  {
    MgtvApiGetMsgBoardSendRespondTask localMgtvApiGetMsgBoardSendRespondTask = new MgtvApiGetMsgBoardSendRespondTask(paramString);
    localMgtvApiGetMsgBoardSendRespondTask.setListener(paramIMgtvApiGetMsgBoardSendRespondTaskListener);
    return this.httpApiEngine.addTask(localMgtvApiGetMsgBoardSendRespondTask);
  }

  public int APIGetPlayRecord(SccmsApiGetPlayRecordTask.ISccmsApiGetPlayRecordTaskListener paramISccmsApiGetPlayRecordTaskListener)
  {
    SccmsApiGetPlayRecordTask localSccmsApiGetPlayRecordTask = new SccmsApiGetPlayRecordTask();
    localSccmsApiGetPlayRecordTask.setListener(paramISccmsApiGetPlayRecordTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetPlayRecordTask);
  }

  public int APIGetPlaybill(String paramString, int paramInt1, int paramInt2, SccmsApiGetPlaybillTask.ISccmsApiGetPlaybillTaskListener paramISccmsApiGetPlaybillTaskListener)
  {
    SccmsApiGetPlaybillTask localSccmsApiGetPlaybillTask = new SccmsApiGetPlaybillTask(paramString, paramInt1, paramInt2);
    localSccmsApiGetPlaybillTask.setListener(paramISccmsApiGetPlaybillTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetPlaybillTask);
  }

  public int APIGetPreInstallList(SccmsApiGetPreInstallListTask.ISccmsApiGetPreInstallListTaskListener paramISccmsApiGetPreInstallListTaskListener)
  {
    SccmsApiGetPreInstallListTask localSccmsApiGetPreInstallListTask = new SccmsApiGetPreInstallListTask();
    localSccmsApiGetPreInstallListTask.setListener(paramISccmsApiGetPreInstallListTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetPreInstallListTask);
  }

  public int APIGetRelevantFilms(String paramString1, int paramInt1, String paramString2, String paramString3, int paramInt2, int paramInt3, SccmsApiGetRelevantFilmsTask.ISccmsApiGetRelevantFilmsTaskListener paramISccmsApiGetRelevantFilmsTaskListener)
  {
    SccmsApiGetRelevantFilmsTask localSccmsApiGetRelevantFilmsTask = new SccmsApiGetRelevantFilmsTask(paramString1, paramInt1, paramString2, paramString3, paramInt2, paramInt3);
    localSccmsApiGetRelevantFilmsTask.setListener(paramISccmsApiGetRelevantFilmsTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetRelevantFilmsTask);
  }

  public int APIGetReplayRecommendData(int paramInt1, int paramInt2, SccmsApiGetReplayRecommendDataTask.ISccmsApiGetReplayRecommendDataTaskListener paramISccmsApiGetReplayRecommendDataTaskListener)
  {
    SccmsApiGetReplayRecommendDataTask localSccmsApiGetReplayRecommendDataTask = new SccmsApiGetReplayRecommendDataTask(paramInt1, paramInt2);
    localSccmsApiGetReplayRecommendDataTask.setListener(paramISccmsApiGetReplayRecommendDataTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetReplayRecommendDataTask);
  }

  public int APIGetSkins(SccmsApiGetSkinTask.ISccmsApiGetSkinTaskListener paramISccmsApiGetSkinTaskListener)
  {
    SccmsApiGetSkinTask localSccmsApiGetSkinTask = new SccmsApiGetSkinTask();
    localSccmsApiGetSkinTask.setListener(paramISccmsApiGetSkinTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetSkinTask);
  }

  public int APIGetSpecialTopicColumnTreeData(String paramString, SccmsApiGetSpecialTopicColumnTreeTask.ISccmsApiGetSpecialTopicColumnTreeTaskListener paramISccmsApiGetSpecialTopicColumnTreeTaskListener)
  {
    SccmsApiGetSpecialTopicColumnTreeTask localSccmsApiGetSpecialTopicColumnTreeTask = new SccmsApiGetSpecialTopicColumnTreeTask(paramString);
    localSccmsApiGetSpecialTopicColumnTreeTask.setListener(paramISccmsApiGetSpecialTopicColumnTreeTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetSpecialTopicColumnTreeTask);
  }

  public int APIGetSpecialTopicPkgContent(String paramString, SccmsApiGetSpecialTopicPkgContentLstTask.ISccmsApiGetSearchSpecialTopicPkgLstTaskListener paramISccmsApiGetSearchSpecialTopicPkgLstTaskListener)
  {
    return APIGetSpecialTopicPkgContentLstData(paramString, "0", "100", "0", "", paramISccmsApiGetSearchSpecialTopicPkgLstTaskListener);
  }

  public int APIGetSpecialTopicPkgContentLstData(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, SccmsApiGetSpecialTopicPkgContentLstTask.ISccmsApiGetSearchSpecialTopicPkgLstTaskListener paramISccmsApiGetSearchSpecialTopicPkgLstTaskListener)
  {
    SccmsApiGetSpecialTopicPkgContentLstTask localSccmsApiGetSpecialTopicPkgContentLstTask = new SccmsApiGetSpecialTopicPkgContentLstTask(paramString1, paramString2, paramString3, paramString4, paramString5);
    localSccmsApiGetSpecialTopicPkgContentLstTask.setListener(paramISccmsApiGetSearchSpecialTopicPkgLstTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetSpecialTopicPkgContentLstTask);
  }

  public int APIGetSpecialTopicPutData(String paramString1, String paramString2, SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener paramISccmsApiGetSpecialTopicPutTaskListener)
  {
    SccmsApiGetSpecialTopicPutTask localSccmsApiGetSpecialTopicPutTask = new SccmsApiGetSpecialTopicPutTask(paramString1, paramString2);
    localSccmsApiGetSpecialTopicPutTask.setListener(paramISccmsApiGetSpecialTopicPutTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetSpecialTopicPutTask);
  }

  public int APIGetSpecialTopicPutData(String paramString1, String paramString2, String paramString3, SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener paramISccmsApiGetSpecialTopicPutTaskListener)
  {
    SccmsApiGetSpecialTopicPutTask localSccmsApiGetSpecialTopicPutTask = new SccmsApiGetSpecialTopicPutTask(paramString1, paramString2, paramString3);
    localSccmsApiGetSpecialTopicPutTask.setListener(paramISccmsApiGetSpecialTopicPutTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetSpecialTopicPutTask);
  }

  public int APIGetSpeedTestMissionInfo(SccmsApiGetSpeedTestMissionInfoTask.ISccmsApiGetSpeedTestMissionInfoTaskListener paramISccmsApiGetSpeedTestMissionInfoTaskListener)
  {
    SccmsApiGetSpeedTestMissionInfoTask localSccmsApiGetSpeedTestMissionInfoTask = new SccmsApiGetSpeedTestMissionInfoTask();
    localSccmsApiGetSpeedTestMissionInfoTask.setListener(paramISccmsApiGetSpeedTestMissionInfoTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetSpeedTestMissionInfoTask);
  }

  public int APIGetStarCollectData(List<String> paramList, SccmsApiGetStarCollectDataTask.ISccmsApiGetStarCollectDataTaskListener paramISccmsApiGetStarCollectDataTaskListener)
  {
    SccmsApiGetStarCollectDataTask localSccmsApiGetStarCollectDataTask = new SccmsApiGetStarCollectDataTask(paramList);
    localSccmsApiGetStarCollectDataTask.setListener(paramISccmsApiGetStarCollectDataTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetStarCollectDataTask);
  }

  public int APIGetSystemMessage(String paramString1, String paramString2, String paramString3, String paramString4, MgtvApiGetSystemMessageTask.IMgtvApiGetSystemMessageTaskListener paramIMgtvApiGetSystemMessageTaskListener)
  {
    MgtvApiGetSystemMessageTask localMgtvApiGetSystemMessageTask = new MgtvApiGetSystemMessageTask(paramString1, paramString2, paramString3, paramString4);
    localMgtvApiGetSystemMessageTask.setListener(paramIMgtvApiGetSystemMessageTaskListener);
    return this.httpApiEngine.addTask(localMgtvApiGetSystemMessageTask);
  }

  public int APIGetSystemMessageV2(String paramString1, String paramString2, String paramString3, MgtvApiGetMQTTClientInfoTask.IMgtvApiGetMQTTClientInfoTaskListener paramIMgtvApiGetMQTTClientInfoTaskListener)
  {
    MgtvApiGetMQTTClientInfoTask localMgtvApiGetMQTTClientInfoTask = new MgtvApiGetMQTTClientInfoTask(paramString1, paramString2, paramString3);
    localMgtvApiGetMQTTClientInfoTask.setListener(paramIMgtvApiGetMQTTClientInfoTaskListener);
    return this.httpApiEngine.addTask(localMgtvApiGetMQTTClientInfoTask);
  }

  public int APIGetUiPackage(String paramString1, String paramString2, SccmsApiGetUiPackageTask.ISccmsApiGetUiPackageTaskListener paramISccmsApiGetUiPackageTaskListener)
  {
    SccmsApiGetUiPackageTask localSccmsApiGetUiPackageTask = new SccmsApiGetUiPackageTask(paramString1, paramString2);
    localSccmsApiGetUiPackageTask.setListener(paramISccmsApiGetUiPackageTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetUiPackageTask);
  }

  public int APIGetUninstallList(SccmsApiGetUninstallListTask.ISccmsApiGetUninstallListTaskListener paramISccmsApiGetUninstallListTaskListener)
  {
    SccmsApiGetUninstallListTask localSccmsApiGetUninstallListTask = new SccmsApiGetUninstallListTask();
    localSccmsApiGetUninstallListTask.setListener(paramISccmsApiGetUninstallListTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetUninstallListTask);
  }

  public int APIGetUserAuth(String paramString1, String paramString2, String paramString3, SccmsApiGetUserAuthTask.ISccmsApiGetUserAuthTaskListener paramISccmsApiGetUserAuthTaskListener)
  {
    SccmsApiGetUserAuthTask localSccmsApiGetUserAuthTask = new SccmsApiGetUserAuthTask(paramString1, paramString2, paramString3);
    localSccmsApiGetUserAuthTask.setListener(paramISccmsApiGetUserAuthTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetUserAuthTask);
  }

  public int APIGetUserAuthV2(GetUserAuthV2Params paramGetUserAuthV2Params, SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener paramISccmsApiGetUserAuthV2TaskListener)
  {
    SccmsApiGetUserAuthV2Task localSccmsApiGetUserAuthV2Task = new SccmsApiGetUserAuthV2Task(paramGetUserAuthV2Params);
    localSccmsApiGetUserAuthV2Task.setListener(paramISccmsApiGetUserAuthV2TaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetUserAuthV2Task);
  }

  public int APIGetVideoIdByMgtvAssetId(String paramString1, String paramString2, String paramString3, SccmsApiGetVideoIdByMgtvAssetIdTask.ISccmsApiGetVideoIdByMgtvAssetIdTaskListener paramISccmsApiGetVideoIdByMgtvAssetIdTaskListener)
  {
    SccmsApiGetVideoIdByMgtvAssetIdTask localSccmsApiGetVideoIdByMgtvAssetIdTask = new SccmsApiGetVideoIdByMgtvAssetIdTask(paramString1, paramString2, paramString3);
    localSccmsApiGetVideoIdByMgtvAssetIdTask.setListener(paramISccmsApiGetVideoIdByMgtvAssetIdTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetVideoIdByMgtvAssetIdTask);
  }

  public int APIGetVideoIndexList(String paramString, int paramInt1, int paramInt2, int paramInt3, SccmsApiGetVideoIndexListTask.ISccmsApiGetVideoIndexListTaskListener paramISccmsApiGetVideoIndexListTaskListener)
  {
    SccmsApiGetVideoIndexListTask localSccmsApiGetVideoIndexListTask = new SccmsApiGetVideoIndexListTask(paramString, paramInt1, paramInt2, paramInt3);
    localSccmsApiGetVideoIndexListTask.setListener(paramISccmsApiGetVideoIndexListTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetVideoIndexListTask);
  }

  public int APIGetVideoInfoV2(String paramString, int paramInt, SccmsApiGetVideoInfoV2Task.ISccmsApiGetVideoInfoV2TaskListener paramISccmsApiGetVideoInfoV2TaskListener)
  {
    SccmsApiGetVideoInfoV2Task localSccmsApiGetVideoInfoV2Task = new SccmsApiGetVideoInfoV2Task(paramString, paramInt);
    localSccmsApiGetVideoInfoV2Task.setListener(paramISccmsApiGetVideoInfoV2TaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetVideoInfoV2Task);
  }

  public int APIGetVideoLabelType(String paramString, SccmsApiGetVideoLabelTypeTask.ISccmsApiGetVideoLabelTypeTaskListener paramISccmsApiGetVideoLabelTypeTaskListener)
  {
    SccmsApiGetVideoLabelTypeTask localSccmsApiGetVideoLabelTypeTask = new SccmsApiGetVideoLabelTypeTask(paramString);
    localSccmsApiGetVideoLabelTypeTask.setListener(paramISccmsApiGetVideoLabelTypeTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetVideoLabelTypeTask);
  }

  public int APIGetVideoList(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, SccmsApiGetVideoListTask.ISccmsApiGetVideoListTaskListener paramISccmsApiGetVideoListTaskListener)
  {
    SccmsApiGetVideoListTask localSccmsApiGetVideoListTask = new SccmsApiGetVideoListTask(paramString1, paramString2, paramString3, paramInt1, paramInt2);
    localSccmsApiGetVideoListTask.setListener(paramISccmsApiGetVideoListTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetVideoListTask);
  }

  public int APIGetVideoListByLabel(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, String paramString4, SccmsApiGetVideoListByLabelTask.ISccmsApiGetVideoListByLabelTaskListener paramISccmsApiGetVideoListByLabelTaskListener)
  {
    SccmsApiGetVideoListByLabelTask localSccmsApiGetVideoListByLabelTask = new SccmsApiGetVideoListByLabelTask(paramString1, paramString2, paramString3, paramInt1, paramInt2, paramString4);
    localSccmsApiGetVideoListByLabelTask.setListener(paramISccmsApiGetVideoListByLabelTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetVideoListByLabelTask);
  }

  public int APIGetVideoScoreByUserId(String paramString1, String paramString2, SccmsApiGetVideoScoreByUserIdTask.ISccmsApiGetVideoScoreByUserIdTaskListener paramISccmsApiGetVideoScoreByUserIdTaskListener)
  {
    SccmsApiGetVideoScoreByUserIdTask localSccmsApiGetVideoScoreByUserIdTask = new SccmsApiGetVideoScoreByUserIdTask(paramString1, paramString2);
    localSccmsApiGetVideoScoreByUserIdTask.setListener(paramISccmsApiGetVideoScoreByUserIdTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetVideoScoreByUserIdTask);
  }

  public int APIGetWeatherInfo(String paramString, SccmsApiGetWeatherInfoTask.ISccmsApiGetWeatherInfoTaskListener paramISccmsApiGetWeatherInfoTaskListener)
  {
    SccmsApiGetWeatherInfoTask localSccmsApiGetWeatherInfoTask = new SccmsApiGetWeatherInfoTask(paramString);
    localSccmsApiGetWeatherInfoTask.setListener(paramISccmsApiGetWeatherInfoTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetWeatherInfoTask);
  }

  public int APIN3A2GetEpgData(ApiDetectSccmsN3A2GetEpgDataTask.IApiDetectSccmsApiN3A2GetEpgDataTaskListener paramIApiDetectSccmsApiN3A2GetEpgDataTaskListener)
  {
    ApiDetectSccmsN3A2GetEpgDataTask localApiDetectSccmsN3A2GetEpgDataTask = new ApiDetectSccmsN3A2GetEpgDataTask();
    localApiDetectSccmsN3A2GetEpgDataTask.setListener(paramIApiDetectSccmsApiN3A2GetEpgDataTaskListener);
    return this.httpApiEngine.addTask(localApiDetectSccmsN3A2GetEpgDataTask);
  }

  public int APIReportAdState(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    SccmsApiReportAdStateTask localSccmsApiReportAdStateTask = new SccmsApiReportAdStateTask(paramString1, paramString2, paramString3, paramString4);
    localSccmsApiReportAdStateTask.setListener(null);
    return this.httpApiEngine.addTask(localSccmsApiReportAdStateTask);
  }

  public int APIReportSpeedTestResult(String paramString, SccmsApiReportSpeedTestResultTask.ISccmsApiReportSpeedTestResultTaskListener paramISccmsApiReportSpeedTestResultTaskListener)
  {
    SccmsApiReportSpeedTestResultTask localSccmsApiReportSpeedTestResultTask = new SccmsApiReportSpeedTestResultTask(paramString);
    localSccmsApiReportSpeedTestResultTask.setListener(paramISccmsApiReportSpeedTestResultTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiReportSpeedTestResultTask);
  }

  public int APISearchActorsAndDirectorsByPinyin(String paramString, SccmsApiSearchActorsAndDirectorsByPinyinTask.ISccmsApiSearchActorsAndDirectorsByPinyinTaskListener paramISccmsApiSearchActorsAndDirectorsByPinyinTaskListener)
  {
    SccmsApiSearchActorsAndDirectorsByPinyinTask localSccmsApiSearchActorsAndDirectorsByPinyinTask = new SccmsApiSearchActorsAndDirectorsByPinyinTask(paramString);
    localSccmsApiSearchActorsAndDirectorsByPinyinTask.setListener(paramISccmsApiSearchActorsAndDirectorsByPinyinTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiSearchActorsAndDirectorsByPinyinTask);
  }

  public int APISearchApp(int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, SccmsApiSearchAppTask.ISccmsApiSearchAppTaskListener paramISccmsApiSearchAppTaskListener)
  {
    SccmsApiSearchAppTask localSccmsApiSearchAppTask = new SccmsApiSearchAppTask(paramInt1, paramInt2, paramInt3, paramString1, paramString2);
    localSccmsApiSearchAppTask.setListener(paramISccmsApiSearchAppTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiSearchAppTask);
  }

  public int APISearchVideoByChinese(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, SccmsApiSearchVideoByPinyinTask.ISccmsApiSearchVideoByPinyinTaskListener paramISccmsApiSearchVideoByPinyinTaskListener)
  {
    SccmsApiSearchVideoByPinyinTask localSccmsApiSearchVideoByPinyinTask = new SccmsApiSearchVideoByPinyinTask(paramString1, paramString2, paramString3, "name_likechar|pinyin_likechar", paramInt1, paramInt2);
    localSccmsApiSearchVideoByPinyinTask.setListener(paramISccmsApiSearchVideoByPinyinTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiSearchVideoByPinyinTask);
  }

  public int APISearchVideoByPinyin(String paramString1, String paramString2, int paramInt1, int paramInt2, SccmsApiSearchVideoByPinyinTask.ISccmsApiSearchVideoByPinyinTaskListener paramISccmsApiSearchVideoByPinyinTaskListener)
  {
    return APISearchVideoByPinyin(paramString1, paramString2, "pinyin_likechar", paramInt1, paramInt2, paramISccmsApiSearchVideoByPinyinTaskListener);
  }

  public int APISearchVideoByPinyin(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, SccmsApiSearchVideoByPinyinTask.ISccmsApiSearchVideoByPinyinTaskListener paramISccmsApiSearchVideoByPinyinTaskListener)
  {
    SccmsApiSearchVideoByPinyinTask localSccmsApiSearchVideoByPinyinTask = new SccmsApiSearchVideoByPinyinTask(paramString1, paramString2, paramInt1, paramInt2);
    localSccmsApiSearchVideoByPinyinTask.setListener(paramISccmsApiSearchVideoByPinyinTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiSearchVideoByPinyinTask);
  }

  public int APISyncServerTime(SccmsApiSyncTimeTask.ISccmsApiSyncTimeTaskListener paramISccmsApiSyncTimeTaskListener)
  {
    SccmsApiSyncTimeTask localSccmsApiSyncTimeTask = new SccmsApiSyncTimeTask();
    localSccmsApiSyncTimeTask.setListener(paramISccmsApiSyncTimeTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiSyncTimeTask);
  }

  public int APIUserCenterBindMobile(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, SccmsApiUserCenterBindMobileTask.ISccmsApiBindMobileTaskListener paramISccmsApiBindMobileTaskListener)
  {
    SccmsApiUserCenterBindMobileTask localSccmsApiUserCenterBindMobileTask = new SccmsApiUserCenterBindMobileTask(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6);
    localSccmsApiUserCenterBindMobileTask.setListener(paramISccmsApiBindMobileTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterBindMobileTask);
  }

  public int APIUserCenterChangPwd(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, SccmsApiUserCenterChangePwdTask.ISccmsApiUserCenterChangePwdTaskListener paramISccmsApiUserCenterChangePwdTaskListener)
  {
    SccmsApiUserCenterChangePwdTask localSccmsApiUserCenterChangePwdTask = new SccmsApiUserCenterChangePwdTask(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6);
    localSccmsApiUserCenterChangePwdTask.setListener(paramISccmsApiUserCenterChangePwdTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterChangePwdTask);
  }

  public int APIUserCenterCheckReturn(String paramString1, String paramString2, SccmsApiUserCenterCheckReturnTask.ISccmsApiCheckUserCenterReturnTaskListener paramISccmsApiCheckUserCenterReturnTaskListener)
  {
    SccmsApiUserCenterCheckReturnTask localSccmsApiUserCenterCheckReturnTask = new SccmsApiUserCenterCheckReturnTask(paramString1, paramString2);
    localSccmsApiUserCenterCheckReturnTask.setListener(paramISccmsApiCheckUserCenterReturnTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterCheckReturnTask);
  }

  public int APIUserCenterGet3rdLoginUrl(String paramString, SccmsApiUserCenterGet3rdLoginUrlTask.ISccmsApiUserCenterGet3rdLoginUrlTaskListener paramISccmsApiUserCenterGet3rdLoginUrlTaskListener)
  {
    SccmsApiUserCenterGet3rdLoginUrlTask localSccmsApiUserCenterGet3rdLoginUrlTask = new SccmsApiUserCenterGet3rdLoginUrlTask(paramString);
    localSccmsApiUserCenterGet3rdLoginUrlTask.setListener(paramISccmsApiUserCenterGet3rdLoginUrlTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterGet3rdLoginUrlTask);
  }

  public int APIUserCenterGetMobileCode(String paramString1, String paramString2, SccmsApiUserCenterGetMobileCodeTask.ISccmsApiGetMobileMsgAuthTaskListener paramISccmsApiGetMobileMsgAuthTaskListener)
  {
    SccmsApiUserCenterGetMobileCodeTask localSccmsApiUserCenterGetMobileCodeTask = new SccmsApiUserCenterGetMobileCodeTask(paramString1, paramString2);
    localSccmsApiUserCenterGetMobileCodeTask.setListener(paramISccmsApiGetMobileMsgAuthTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterGetMobileCodeTask);
  }

  public int APIUserCenterGetMobileCodeByUserName(String paramString1, String paramString2, SccmsApiUserCenterGetMobileCodeByUserNameTask.ISccmsApiGetMobileCodeByUserNameTaskListener paramISccmsApiGetMobileCodeByUserNameTaskListener)
  {
    SccmsApiUserCenterGetMobileCodeByUserNameTask localSccmsApiUserCenterGetMobileCodeByUserNameTask = new SccmsApiUserCenterGetMobileCodeByUserNameTask(paramString1, paramString2);
    localSccmsApiUserCenterGetMobileCodeByUserNameTask.setListener(paramISccmsApiGetMobileCodeByUserNameTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterGetMobileCodeByUserNameTask);
  }

  public int APIUserCenterGetWebChatLoginPic(String paramString, int paramInt, SccmsApiUserCenterGetWebChatLoginPicTask.ISccmsApiGetWebChatLoginPicTaskListenter paramISccmsApiGetWebChatLoginPicTaskListenter)
  {
    SccmsApiUserCenterGetWebChatLoginPicTask localSccmsApiUserCenterGetWebChatLoginPicTask = new SccmsApiUserCenterGetWebChatLoginPicTask(paramString, paramInt);
    localSccmsApiUserCenterGetWebChatLoginPicTask.setListener(paramISccmsApiGetWebChatLoginPicTaskListenter);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterGetWebChatLoginPicTask);
  }

  public int APIUserCenterLogin(String paramString1, String paramString2, String paramString3, SccmsApiUserCenterLoginTask.ISccmsApiUserCenterLoginTaskListener paramISccmsApiUserCenterLoginTaskListener)
  {
    SccmsApiUserCenterLoginTask localSccmsApiUserCenterLoginTask = new SccmsApiUserCenterLoginTask(paramString1, paramString2, paramString3);
    localSccmsApiUserCenterLoginTask.setListener(paramISccmsApiUserCenterLoginTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterLoginTask);
  }

  public int APIUserCenterLogout(String paramString1, String paramString2, SccmsApiUserCenterLogoutTask.ISccmsApiUserCenterLogoutTaskListener paramISccmsApiUserCenterLogoutTaskListener)
  {
    SccmsApiUserCenterLogoutTask localSccmsApiUserCenterLogoutTask = new SccmsApiUserCenterLogoutTask(paramString1, paramString2);
    localSccmsApiUserCenterLogoutTask.setListener(paramISccmsApiUserCenterLogoutTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterLogoutTask);
  }

  public int APIUserCenterRegistUser(String paramString1, String paramString2, String paramString3, String paramString4, SccmsApiUserCenterRegisterTask.ISccmsApiUserCenterRegisterTaskListener paramISccmsApiUserCenterRegisterTaskListener)
  {
    SccmsApiUserCenterRegisterTask localSccmsApiUserCenterRegisterTask = new SccmsApiUserCenterRegisterTask(paramString1, paramString2, paramString3, paramString4);
    localSccmsApiUserCenterRegisterTask.setListener(paramISccmsApiUserCenterRegisterTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterRegisterTask);
  }

  public int APIUserCenterResetPwd(String paramString1, String paramString2, String paramString3, String paramString4, SccmsApiUserCenterResetPwdTask.ISccmsApiUserCenterResetPwdTaskListener paramISccmsApiUserCenterResetPwdTaskListener)
  {
    SccmsApiUserCenterResetPwdTask localSccmsApiUserCenterResetPwdTask = new SccmsApiUserCenterResetPwdTask(paramString1, paramString2, paramString3, paramString4);
    localSccmsApiUserCenterResetPwdTask.setListener(paramISccmsApiUserCenterResetPwdTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterResetPwdTask);
  }

  public int APIUserCenterUnBindMobile(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, SccmsApiUserCenterUnBindMobileTask.ISccmsApiUnBindMobileTaskListener paramISccmsApiUnBindMobileTaskListener)
  {
    SccmsApiUserCenterUnBindMobileTask localSccmsApiUserCenterUnBindMobileTask = new SccmsApiUserCenterUnBindMobileTask(paramString1, paramString2, paramString3, paramString4, paramString5);
    localSccmsApiUserCenterUnBindMobileTask.setListener(paramISccmsApiUnBindMobileTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterUnBindMobileTask);
  }

  public int APIUserCenterUnbindWebchatTask(String paramString1, String paramString2, SccmsApiUserCenterUnbindWebchatTask.ISccmsApiUserCenterUnbindWebchatTaskListener paramISccmsApiUserCenterUnbindWebchatTaskListener)
  {
    SccmsApiUserCenterUnbindWebchatTask localSccmsApiUserCenterUnbindWebchatTask = new SccmsApiUserCenterUnbindWebchatTask(paramString1, paramString2);
    localSccmsApiUserCenterUnbindWebchatTask.setListener(paramISccmsApiUserCenterUnbindWebchatTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterUnbindWebchatTask);
  }

  public int APIUserCenterVerifyToken(String paramString1, String paramString2, SccmsApiUserCenterVerifyTokenTask.ISccmsApiUserCenterVerifyTokenTaskListener paramISccmsApiUserCenterVerifyTokenTaskListener)
  {
    SccmsApiUserCenterVerifyTokenTask localSccmsApiUserCenterVerifyTokenTask = new SccmsApiUserCenterVerifyTokenTask(paramString1, paramString2);
    localSccmsApiUserCenterVerifyTokenTask.setListener(paramISccmsApiUserCenterVerifyTokenTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterVerifyTokenTask);
  }

  public int APIUserLogin(String paramString1, String paramString2, SccmsApiUserLoginTask.ISccmsApiUserLoginTaskListener paramISccmsApiUserLoginTaskListener)
  {
    SccmsApiUserLoginTask localSccmsApiUserLoginTask = new SccmsApiUserLoginTask(paramString1, paramString2);
    localSccmsApiUserLoginTask.setListener(paramISccmsApiUserLoginTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiUserLoginTask);
  }

  public int ApiAAAGetUserInfo(String paramString1, int paramInt, String paramString2, SccmsApiAAAGetUserInfoTask.ISccmsApiGetUserCenterInfoTaskListener paramISccmsApiGetUserCenterInfoTaskListener)
  {
    SccmsApiAAAGetUserInfoTask localSccmsApiAAAGetUserInfoTask = new SccmsApiAAAGetUserInfoTask(paramString1, paramInt, paramString2);
    localSccmsApiAAAGetUserInfoTask.setListener(paramISccmsApiGetUserCenterInfoTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetUserInfoTask);
  }

  public int ApiRequestVideoPlay(String paramString1, int paramInt1, String paramString2, String paramString3, int paramInt2, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, SccmsApiRequestVideoPlayTask.ISccmsApiRequestVideoPlayTaskListener paramISccmsApiRequestVideoPlayTaskListener)
  {
    SccmsApiRequestVideoPlayTask localSccmsApiRequestVideoPlayTask = new SccmsApiRequestVideoPlayTask(paramString1, paramInt1, paramString2, paramString3, paramInt2, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9);
    localSccmsApiRequestVideoPlayTask.setListener(paramISccmsApiRequestVideoPlayTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiRequestVideoPlayTask);
  }

  public int ApiRequestVideoPlay(String paramString1, int paramInt1, String paramString2, String paramString3, int paramInt2, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, SccmsApiRequestVideoPlayTask.ISccmsApiRequestVideoPlayTaskListener paramISccmsApiRequestVideoPlayTaskListener)
  {
    SccmsApiRequestVideoPlayTask localSccmsApiRequestVideoPlayTask = new SccmsApiRequestVideoPlayTask(paramString1, paramInt1, paramString2, paramString3, paramInt2, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10);
    localSccmsApiRequestVideoPlayTask.setListener(paramISccmsApiRequestVideoPlayTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiRequestVideoPlayTask);
  }

  public int ApiRequestVideoPlayV2(String paramString1, int paramInt1, String paramString2, String paramString3, int paramInt2, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener paramISccmsApiRequestVideoPlayV2TaskListener)
  {
    SccmsApiRequestVideoPlayV2Task localSccmsApiRequestVideoPlayV2Task = new SccmsApiRequestVideoPlayV2Task(paramString1, paramInt1, "", paramString2, paramString3, paramInt2, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9, "", "");
    localSccmsApiRequestVideoPlayV2Task.setListener(paramISccmsApiRequestVideoPlayV2TaskListener);
    return this.httpApiEngine.addTask(localSccmsApiRequestVideoPlayV2Task);
  }

  public int ApiRequestVideoPlayV2(String paramString1, int paramInt1, String paramString2, String paramString3, int paramInt2, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener paramISccmsApiRequestVideoPlayV2TaskListener)
  {
    SccmsApiRequestVideoPlayV2Task localSccmsApiRequestVideoPlayV2Task = new SccmsApiRequestVideoPlayV2Task(paramString1, paramInt1, paramString2, paramString3, paramInt2, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, "", "");
    localSccmsApiRequestVideoPlayV2Task.setListener(paramISccmsApiRequestVideoPlayV2TaskListener);
    return this.httpApiEngine.addTask(localSccmsApiRequestVideoPlayV2Task);
  }

  public boolean init()
  {
    this.httpApiEngine = new ApiDetectSCHttpApiEngine();
    this.httpApiEngine.init(true);
    return true;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.apidetect.server.ApiDetectServerApiManager
 * JD-Core Version:    0.6.2
 */