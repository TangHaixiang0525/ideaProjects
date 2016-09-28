package com.starcor.server.api.manage;

import android.os.Handler;
import android.os.Looper;
import com.starcor.apitask.IApiTaskListener;
import com.starcor.apitask.impl.ApiTaskCenterEdgeScheduling;
import com.starcor.apitask.impl.ApiTaskGetSpecialInfoByIds;
import com.starcor.core.domain.AAACouponUse;
import com.starcor.core.domain.AAAGetLicense;
import com.starcor.core.domain.AAAOrderInfo;
import com.starcor.core.domain.AAAOrderRecordList;
import com.starcor.core.domain.AAAOrderState;
import com.starcor.core.domain.AAAPayChannelList;
import com.starcor.core.domain.AAAPriceInfo;
import com.starcor.core.domain.AAAPriceList;
import com.starcor.core.domain.AAAProductInfo;
import com.starcor.core.domain.AAAProductList;
import com.starcor.core.domain.AAAVerifyLicense;
import com.starcor.core.domain.AAAVipInfo;
import com.starcor.core.domain.AAAVipList;
import com.starcor.core.domain.AAAWechatPayQrcode;
import com.starcor.core.domain.ActorStarInfoList;
import com.starcor.core.domain.AdInfos;
import com.starcor.core.domain.AdPos;
import com.starcor.core.domain.AdPosByPageIdInfo;
import com.starcor.core.domain.AdPosEntity;
import com.starcor.core.domain.AdvertisementPosInfo;
import com.starcor.core.domain.Agreements;
import com.starcor.core.domain.AppDetail;
import com.starcor.core.domain.AppDownloadUrl;
import com.starcor.core.domain.AppList;
import com.starcor.core.domain.AssetsInfo;
import com.starcor.core.domain.BarrageResponse;
import com.starcor.core.domain.CategoryIndexList;
import com.starcor.core.domain.CategoryPosterList;
import com.starcor.core.domain.ChannelInfoV2;
import com.starcor.core.domain.CityInfoById;
import com.starcor.core.domain.CityStruct;
import com.starcor.core.domain.CollectListItem;
import com.starcor.core.domain.CommonVideoIDInfo;
import com.starcor.core.domain.ConPlayMedia;
import com.starcor.core.domain.DrmReportDecodeCapacityInfo;
import com.starcor.core.domain.FAQStruct;
import com.starcor.core.domain.FilmItem;
import com.starcor.core.domain.FilmListPageInfo;
import com.starcor.core.domain.GetPlaybillSelectedListInfo;
import com.starcor.core.domain.GetSecretKeysInfo;
import com.starcor.core.domain.HotAppList;
import com.starcor.core.domain.HotWordList;
import com.starcor.core.domain.InjectingID;
import com.starcor.core.domain.LabelSort;
import com.starcor.core.domain.LabelSortItem;
import com.starcor.core.domain.LabelStarList;
import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.domain.MessageBoardData;
import com.starcor.core.domain.MessageBoardSendRepond;
import com.starcor.core.domain.MetadataGoup;
import com.starcor.core.domain.MobileCode;
import com.starcor.core.domain.NewDetailedFilmData;
import com.starcor.core.domain.PlayBillItem;
import com.starcor.core.domain.PlayBillRecommendStrut;
import com.starcor.core.domain.PlayBillStruct;
import com.starcor.core.domain.PlayInfo;
import com.starcor.core.domain.PlayInfoV2;
import com.starcor.core.domain.PreInstallList;
import com.starcor.core.domain.PublicImage;
import com.starcor.core.domain.QuitVideoIndexsParams;
import com.starcor.core.domain.ReserveListItem;
import com.starcor.core.domain.SearchActorStarList;
import com.starcor.core.domain.SearchStruct;
import com.starcor.core.domain.Skin;
import com.starcor.core.domain.SpecialTopicPkgCntLstInfo;
import com.starcor.core.domain.SpecialTopicPutInfo;
import com.starcor.core.domain.SpecialTopicTreeInfo;
import com.starcor.core.domain.SpeedStruct;
import com.starcor.core.domain.StarGuestLabelList;
import com.starcor.core.domain.StarInfoCollect;
import com.starcor.core.domain.SystemMessage;
import com.starcor.core.domain.TerminalRealtimeParamList;
import com.starcor.core.domain.UiPackage;
import com.starcor.core.domain.UiUpdatePackage;
import com.starcor.core.domain.UninstallList;
import com.starcor.core.domain.UserAttr;
import com.starcor.core.domain.UserAuth;
import com.starcor.core.domain.UserAuthV2;
import com.starcor.core.domain.UserCenter3rdLoginUrl;
import com.starcor.core.domain.UserCenterBindMobile;
import com.starcor.core.domain.UserCenterChangePwd;
import com.starcor.core.domain.UserCenterCheckReturn;
import com.starcor.core.domain.UserCenterInfo;
import com.starcor.core.domain.UserCenterLogin;
import com.starcor.core.domain.UserCenterLogout;
import com.starcor.core.domain.UserCenterRegister;
import com.starcor.core.domain.UserCenterUnbindWebChat;
import com.starcor.core.domain.UserFeedbackInfoList;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.domain.Version;
import com.starcor.core.domain.VideoIdInfo;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.domain.VideoRoleList;
import com.starcor.core.domain.VideoScoreInfoOnUser;
import com.starcor.core.domain.WeatherList;
import com.starcor.core.domain.WebChatLogin;
import com.starcor.core.epgapi.params.AddCollectParams;
import com.starcor.core.epgapi.params.AddCollectV2Params;
import com.starcor.core.epgapi.params.AddUserRecommendParams;
import com.starcor.core.epgapi.params.GetUserAuthV2Params;
import com.starcor.core.epgapi.params.UploadAllRecordsParams;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCHttpApiEngine;
import com.starcor.httpapi.core.SCHttpApiTask;
import com.starcor.hunan.ads.GetAdInfoTask;
import com.starcor.hunan.ads.GetAdInfoTask.IGetAdInfoListener;
import com.starcor.hunan.ads.GetAdUrlFromMgTask;
import com.starcor.hunan.ads.GetAdUrlFromMgTask.IMGCmsGetAdInfoTaskListener;
import com.starcor.hunan.ads.MgAdInfo;
import com.starcor.hunan.msgsys.data.http.MQTTHttpPostData;
import com.starcor.mgtv.api.MgtvApiCheckNetStateTask;
import com.starcor.mgtv.api.MgtvApiCheckNetStateTask.IMgtvApiCheckNetStateTaskkListener;
import com.starcor.mgtv.api.MgtvApiGetBarrageDataTask;
import com.starcor.mgtv.api.MgtvApiGetBarrageDataTask.IGetBarrageDataTaskListener;
import com.starcor.mgtv.api.MgtvApiGetMQTTClientInfoTask;
import com.starcor.mgtv.api.MgtvApiGetMQTTClientInfoTask.IMgtvApiGetMQTTClientInfoTaskListener;
import com.starcor.mgtv.api.MgtvApiGetMessageBoardDataTask;
import com.starcor.mgtv.api.MgtvApiGetMessageBoardDataTask.IMgtvApiGetMessageBoardDataTaskListener;
import com.starcor.mgtv.api.MgtvApiGetMsgBoardSendRespondTask;
import com.starcor.mgtv.api.MgtvApiGetMsgBoardSendRespondTask.IMgtvApiGetMsgBoardSendRespondTaskListener;
import com.starcor.mgtv.api.MgtvApiGetSystemMessageTask;
import com.starcor.mgtv.api.MgtvApiGetSystemMessageTask.IMgtvApiGetSystemMessageTaskListener;
import com.starcor.sccms.api.SccmsAPIGetNewDetailedDataByVideoIdTask;
import com.starcor.sccms.api.SccmsAPIGetNewDetailedDataByVideoIdTask.IGetNewDetailedDataByVideoIdListener;
import com.starcor.sccms.api.SccmsAPIGetQuitVideoIndexsTask;
import com.starcor.sccms.api.SccmsAPIGetQuitVideoIndexsTask.ISccmsAPIGetQuitVideoIndexsTaskListener;
import com.starcor.sccms.api.SccmsApiAAABuyProductTask;
import com.starcor.sccms.api.SccmsApiAAABuyProductTask.ISccmsApiAAABuyProductTaskListener;
import com.starcor.sccms.api.SccmsApiAAACouponUseTask;
import com.starcor.sccms.api.SccmsApiAAACouponUseTask.ISccmsApiAAACouponUseTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetLicenseTask;
import com.starcor.sccms.api.SccmsApiAAAGetLicenseTask.ISccmsApiGetLicenseTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetMovieCouponCountTask;
import com.starcor.sccms.api.SccmsApiAAAGetMovieCouponCountTask.ISccmsApiAAAGetMovieCouponCountTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetMovieCouponHistoryTask;
import com.starcor.sccms.api.SccmsApiAAAGetMovieCouponHistoryTask.ISccmsApiAAAGetMovieCouponHistoryTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetMovieCouponInfoTask;
import com.starcor.sccms.api.SccmsApiAAAGetMovieCouponInfoTask.ISccmsApiAAAGetMovieCouponInfoTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetOrderRecordListTask;
import com.starcor.sccms.api.SccmsApiAAAGetOrderRecordListTask.ISccmsApiAAAGetOrderRecordListTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetOrderStateTask;
import com.starcor.sccms.api.SccmsApiAAAGetOrderStateTask.ISccmsApiAAAGetOrderStateTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetPayAuthorizeStatusTask;
import com.starcor.sccms.api.SccmsApiAAAGetPayAuthorizeStatusTask.ISccmsApiAAAGetPayAuthorizeStatusListener;
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
import com.starcor.sccms.api.SccmsApiAAAGetWechatPayQrcodeTask;
import com.starcor.sccms.api.SccmsApiAAAGetWechatPayQrcodeTask.ISccmsApiAAAGetWechatPayQrcodeTaskListener;
import com.starcor.sccms.api.SccmsApiAAAQueryPayAuthorizeStatusTask;
import com.starcor.sccms.api.SccmsApiAAAQueryPayAuthorizeStatusTask.ISccmsApiAAAQueryPayAuthorizeStatusTaskListener;
import com.starcor.sccms.api.SccmsApiAAARechargeTask;
import com.starcor.sccms.api.SccmsApiAAARechargeTask.ISccmsApiAAARechargeTaskListener;
import com.starcor.sccms.api.SccmsApiAAASmsPayTask;
import com.starcor.sccms.api.SccmsApiAAASmsPayTask.ISccmsApiAAASmsPayTaskListener;
import com.starcor.sccms.api.SccmsApiAAAUseMovieCouponTask;
import com.starcor.sccms.api.SccmsApiAAAUseMovieCouponTask.ISccmsApiAAAUseMovieCouponTaskListener;
import com.starcor.sccms.api.SccmsApiAAAVerifyLicenseTask;
import com.starcor.sccms.api.SccmsApiAAAVerifyLicenseTask.ISccmsApiVerifyLicenseTaskListener;
import com.starcor.sccms.api.SccmsApiAddCatchVideoRecordTask;
import com.starcor.sccms.api.SccmsApiAddCatchVideoRecordTask.ISccmsApiAddCatchVideoRecordTaskListener;
import com.starcor.sccms.api.SccmsApiAddCatchVideoRecordV2Task;
import com.starcor.sccms.api.SccmsApiAddCatchVideoRecordV2Task.ISccmsApiAddCatchVideoRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiAddCollectRecordTask;
import com.starcor.sccms.api.SccmsApiAddCollectRecordTask.ISccmsApiAddCollectRecordTaskListener;
import com.starcor.sccms.api.SccmsApiAddCollectRecordV2Task;
import com.starcor.sccms.api.SccmsApiAddCollectRecordV2Task.ISccmsApiAddCollectRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiAddMediaRecommendTask;
import com.starcor.sccms.api.SccmsApiAddMediaRecommendTask.ISccmsApiAddMediaRecommendTaskListener;
import com.starcor.sccms.api.SccmsApiAddPlayRecordTask;
import com.starcor.sccms.api.SccmsApiAddPlayRecordTask.ISccmsApiAddPlayRecordTaskListener;
import com.starcor.sccms.api.SccmsApiAddPlayRecordV2Task;
import com.starcor.sccms.api.SccmsApiAddPlayRecordV2Task.ISccmsApiAddPlayRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiBroadcastReserveTask;
import com.starcor.sccms.api.SccmsApiBroadcastReserveTask.ISccmsApiBroadCastReserveTaskListener;
import com.starcor.sccms.api.SccmsApiCancelVideoWishTask;
import com.starcor.sccms.api.SccmsApiCancelVideoWishTask.ISccmsApiCancelVideoWishTaskListener;
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
import com.starcor.sccms.api.SccmsApiDelCatchVideoRecordV2Task;
import com.starcor.sccms.api.SccmsApiDelCatchVideoRecordV2Task.ISccmsApiDelCatchVideoRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiDelCollectRecordTask;
import com.starcor.sccms.api.SccmsApiDelCollectRecordTask.ISccmsApiDelColllectRecordTaskListener;
import com.starcor.sccms.api.SccmsApiDelCollectRecordV2Task;
import com.starcor.sccms.api.SccmsApiDelCollectRecordV2Task.ISccmsApiDelColllectRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiDelPlayRecordTask;
import com.starcor.sccms.api.SccmsApiDelPlayRecordTask.ISccmsApiDelPlayRecordTaskListener;
import com.starcor.sccms.api.SccmsApiDelPlayRecordV2Task;
import com.starcor.sccms.api.SccmsApiDelPlayRecordV2Task.ISccmsApiDelPlayRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiDrmReportDecodeCapacityTask;
import com.starcor.sccms.api.SccmsApiDrmReportDecodeCapacityTask.ISccmsApiReportDecodeCapacityTaskListener;
import com.starcor.sccms.api.SccmsApiExistsUserWishTask;
import com.starcor.sccms.api.SccmsApiExistsUserWishTask.ISccmsApiExistsUserWishTaskListener;
import com.starcor.sccms.api.SccmsApiGetActorStarInfoTask;
import com.starcor.sccms.api.SccmsApiGetActorStarInfoTask.ISccmsApiGetActorStarInfoTaskListener;
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
import com.starcor.sccms.api.SccmsApiGetAssetsByVideoIdTask;
import com.starcor.sccms.api.SccmsApiGetAssetsByVideoIdTask.ISccmsApiGetAssetsByVideoIdTaskListener;
import com.starcor.sccms.api.SccmsApiGetAssetsInfoByVideoIdTask;
import com.starcor.sccms.api.SccmsApiGetAssetsInfoByVideoIdTask.ISccmsApiGetAssetsInfoByVideoIdTaskListener;
import com.starcor.sccms.api.SccmsApiGetCatchVideoRecordTask;
import com.starcor.sccms.api.SccmsApiGetCatchVideoRecordTask.ISccmsApiGetCatchVideoRecordTaskListener;
import com.starcor.sccms.api.SccmsApiGetCatchVideoRecordV2Task;
import com.starcor.sccms.api.SccmsApiGetCatchVideoRecordV2Task.ISccmsApiGetCatchVideoRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetChannelListV2Task;
import com.starcor.sccms.api.SccmsApiGetChannelListV2Task.ISccmsApiGetChannelListV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetCityInfoTask;
import com.starcor.sccms.api.SccmsApiGetCityInfoTask.ISccmsApiGetCityListTaskListener;
import com.starcor.sccms.api.SccmsApiGetCityListTask;
import com.starcor.sccms.api.SccmsApiGetCityListTask.ISccmsApiGetCityListTaskListener;
import com.starcor.sccms.api.SccmsApiGetCollectRecordTask;
import com.starcor.sccms.api.SccmsApiGetCollectRecordTask.ISccmsApiGetCollectRecordTaskListener;
import com.starcor.sccms.api.SccmsApiGetCollectRecordV2Task;
import com.starcor.sccms.api.SccmsApiGetCollectRecordV2Task.ISccmsApiGetCollectRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetCommonVideoIdTask;
import com.starcor.sccms.api.SccmsApiGetCommonVideoIdTask.ISccmsApiGetCommonVideoIdTaskListener;
import com.starcor.sccms.api.SccmsApiGetConPlayMediaTask;
import com.starcor.sccms.api.SccmsApiGetConPlayMediaTask.IGetConPlayMediaTaskListener;
import com.starcor.sccms.api.SccmsApiGetDynamicCategoryItemListTask;
import com.starcor.sccms.api.SccmsApiGetDynamicCategoryItemListTask.ISccmsApiGetDynamicCategoryItemListTaskListener;
import com.starcor.sccms.api.SccmsApiGetEpgIndexTask;
import com.starcor.sccms.api.SccmsApiGetEpgIndexTask.ISccmsApiGetEpgDataTaskListener;
import com.starcor.sccms.api.SccmsApiGetFAQListTask;
import com.starcor.sccms.api.SccmsApiGetFAQListTask.ISccmsApiGetFAQListTaskListener;
import com.starcor.sccms.api.SccmsApiGetFilmListByLabelTask;
import com.starcor.sccms.api.SccmsApiGetFilmListByLabelTask.ISccmsApiGetFilmListByLabelTaskListener;
import com.starcor.sccms.api.SccmsApiGetFilterLabelByTypeTask;
import com.starcor.sccms.api.SccmsApiGetFilterLabelByTypeTask.ISccmsApiGetFilterLabelByTypeTask;
import com.starcor.sccms.api.SccmsApiGetGUIDTask;
import com.starcor.sccms.api.SccmsApiGetGUIDTask.ISccmsApiGetGUIDDataTaskListener;
import com.starcor.sccms.api.SccmsApiGetHotActorStarListTask;
import com.starcor.sccms.api.SccmsApiGetHotActorStarListTask.ISccmsApiGetHotActorStarListTaskListener;
import com.starcor.sccms.api.SccmsApiGetHotAppListTask;
import com.starcor.sccms.api.SccmsApiGetHotAppListTask.ISccmsApiGetHotAppListTaskListener;
import com.starcor.sccms.api.SccmsApiGetHotWordsTask;
import com.starcor.sccms.api.SccmsApiGetHotWordsTask.ISccmsApiGetHotWordsTaskListener;
import com.starcor.sccms.api.SccmsApiGetIndexListByCategoryTask;
import com.starcor.sccms.api.SccmsApiGetIndexListByCategoryTask.ISccmsApiGetIndexListByCategoryListener;
import com.starcor.sccms.api.SccmsApiGetInitMetaDataTask;
import com.starcor.sccms.api.SccmsApiGetInitMetaDataTask.ISccmsApiGetInitMetaDataTaskListener;
import com.starcor.sccms.api.SccmsApiGetLabelStarListTask;
import com.starcor.sccms.api.SccmsApiGetLabelStarListTask.ISccmsApiGetLabelStarListTaskListener;
import com.starcor.sccms.api.SccmsApiGetMediaAssetsBindLabelTask;
import com.starcor.sccms.api.SccmsApiGetMediaAssetsBindLabelTask.ISccmsApiGetMediaAssetsBindLabelTaskListener;
import com.starcor.sccms.api.SccmsApiGetMediaAssetsInfoTask;
import com.starcor.sccms.api.SccmsApiGetMediaAssetsInfoTask.ISccmsApiGetMediaAssetsInfoTaskListener;
import com.starcor.sccms.api.SccmsApiGetMessageTask;
import com.starcor.sccms.api.SccmsApiGetMessageTask.ISccmsApiGetMessageTaskListener;
import com.starcor.sccms.api.SccmsApiGetPlayRecordTask;
import com.starcor.sccms.api.SccmsApiGetPlayRecordTask.ISccmsApiGetPlayRecordTaskListener;
import com.starcor.sccms.api.SccmsApiGetPlayRecordV2Task;
import com.starcor.sccms.api.SccmsApiGetPlayRecordV2Task.ISccmsApiGetPlayRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetPlaybillSelectedList;
import com.starcor.sccms.api.SccmsApiGetPlaybillSelectedList.ISccmsApiGetPlaybillSelectedListTaskListener;
import com.starcor.sccms.api.SccmsApiGetPlaybillTask;
import com.starcor.sccms.api.SccmsApiGetPlaybillTask.ISccmsApiGetPlaybillTaskListener;
import com.starcor.sccms.api.SccmsApiGetPreInstallListTask;
import com.starcor.sccms.api.SccmsApiGetPreInstallListTask.ISccmsApiGetPreInstallListTaskListener;
import com.starcor.sccms.api.SccmsApiGetPreviewListTask;
import com.starcor.sccms.api.SccmsApiGetPreviewListTask.ISccmsApiGetPreviewListTaskListener;
import com.starcor.sccms.api.SccmsApiGetPublicImageTask;
import com.starcor.sccms.api.SccmsApiGetPublicImageTask.ISccmsApiGetPublicImageTaskListener;
import com.starcor.sccms.api.SccmsApiGetRelevantFilmsTask;
import com.starcor.sccms.api.SccmsApiGetRelevantFilmsTask.ISccmsApiGetRelevantFilmsTaskListener;
import com.starcor.sccms.api.SccmsApiGetReplayRecommendDataTask;
import com.starcor.sccms.api.SccmsApiGetReplayRecommendDataTask.ISccmsApiGetReplayRecommendDataTaskListener;
import com.starcor.sccms.api.SccmsApiGetSecretKeysTask;
import com.starcor.sccms.api.SccmsApiGetSecretKeysTask.ISccmsApiGetSecretKeysTaskListener;
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
import com.starcor.sccms.api.SccmsApiGetStarGuestListByLabelTask;
import com.starcor.sccms.api.SccmsApiGetStarGuestListByLabelTask.ISccmsApiGetStarGusetListByLabelTaskListener;
import com.starcor.sccms.api.SccmsApiGetStaticCategoryItemListTask;
import com.starcor.sccms.api.SccmsApiGetStaticCategoryItemListTask.ISccmsApiGetStaticCategoryItemListTaskListener;
import com.starcor.sccms.api.SccmsApiGetTerminalRealtimeParamsTask;
import com.starcor.sccms.api.SccmsApiGetTerminalRealtimeParamsTask.IGetTerminalRealtimeParamsTaskListener;
import com.starcor.sccms.api.SccmsApiGetUiPackageTask;
import com.starcor.sccms.api.SccmsApiGetUiPackageTask.ISccmsApiGetUiPackageTaskListener;
import com.starcor.sccms.api.SccmsApiGetUninstallListTask;
import com.starcor.sccms.api.SccmsApiGetUninstallListTask.ISccmsApiGetUninstallListTaskListener;
import com.starcor.sccms.api.SccmsApiGetUserAttrTask;
import com.starcor.sccms.api.SccmsApiGetUserAttrTask.ISccmsApiGetUserAttrTaskListener;
import com.starcor.sccms.api.SccmsApiGetUserAuthTask;
import com.starcor.sccms.api.SccmsApiGetUserAuthTask.ISccmsApiGetUserAuthTaskListener;
import com.starcor.sccms.api.SccmsApiGetUserAuthV2Task;
import com.starcor.sccms.api.SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetUserFeedbackInfoTask;
import com.starcor.sccms.api.SccmsApiGetUserFeedbackInfoTask.ISccmsApiGetUserFeedbackInfoTaskListener;
import com.starcor.sccms.api.SccmsApiGetUserRecommendTask;
import com.starcor.sccms.api.SccmsApiGetUserRecommendTask.ISccmsApiGetUserRecommendTaskListener;
import com.starcor.sccms.api.SccmsApiGetUserRecommendV2Task;
import com.starcor.sccms.api.SccmsApiGetUserRecommendV2Task.ISccmsApiGetUserRecommendV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoIdByMgtvAssetIdTask;
import com.starcor.sccms.api.SccmsApiGetVideoIdByMgtvAssetIdTask.ISccmsApiGetVideoIdByMgtvAssetIdTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoIndexInfoTask;
import com.starcor.sccms.api.SccmsApiGetVideoIndexInfoTask.ISccmsApiGetVideoIndexInfoTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoIndexListTask;
import com.starcor.sccms.api.SccmsApiGetVideoIndexListTask.ISccmsApiGetVideoIndexListTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoInfoListTask;
import com.starcor.sccms.api.SccmsApiGetVideoInfoListTask.ISccmsApiGetVideoInfoListTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoInfoV2Task;
import com.starcor.sccms.api.SccmsApiGetVideoInfoV2Task.ISccmsApiGetVideoInfoV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoInfoV3Task;
import com.starcor.sccms.api.SccmsApiGetVideoInfoV3Task.ISccmsApiGetVideoInfoV3TaskListener;
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
import com.starcor.sccms.api.SccmsApiLiveShowReserveTask;
import com.starcor.sccms.api.SccmsApiLiveShowReserveTask.ISccmsApiLiveShowReserveTaskListener;
import com.starcor.sccms.api.SccmsApiN3A2GetEpgDataTask;
import com.starcor.sccms.api.SccmsApiN3A2GetEpgDataTask.ISccmsApiN3A2GetEpgDataTaskListener;
import com.starcor.sccms.api.SccmsApiReplayReserveTask;
import com.starcor.sccms.api.SccmsApiReplayReserveTask.ISccmsApiReplayReserveTaskListener;
import com.starcor.sccms.api.SccmsApiReportAdStateTask;
import com.starcor.sccms.api.SccmsApiReportErrorTask;
import com.starcor.sccms.api.SccmsApiReportSpeedTestResultTask;
import com.starcor.sccms.api.SccmsApiReportSpeedTestResultTask.ISccmsApiReportSpeedTestResultTaskListener;
import com.starcor.sccms.api.SccmsApiReportUserBehaviorTask;
import com.starcor.sccms.api.SccmsApiReportUserBehaviorTask.ISccmsApiReportUserBehaviorListener;
import com.starcor.sccms.api.SccmsApiRequestImpressionTask;
import com.starcor.sccms.api.SccmsApiRequestVideoPlayTask;
import com.starcor.sccms.api.SccmsApiRequestVideoPlayTask.ISccmsApiRequestVideoPlayTaskListener;
import com.starcor.sccms.api.SccmsApiRequestVideoPlayV2Task;
import com.starcor.sccms.api.SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener;
import com.starcor.sccms.api.SccmsApiSearchActorStarListTask;
import com.starcor.sccms.api.SccmsApiSearchActorStarListTask.ISccmsApiSearchActorStarListTaskListener;
import com.starcor.sccms.api.SccmsApiSearchActorsAndDirectorsByPinyinTask;
import com.starcor.sccms.api.SccmsApiSearchActorsAndDirectorsByPinyinTask.ISccmsApiSearchActorsAndDirectorsByPinyinTaskListener;
import com.starcor.sccms.api.SccmsApiSearchAppTask;
import com.starcor.sccms.api.SccmsApiSearchAppTask.ISccmsApiSearchAppTaskListener;
import com.starcor.sccms.api.SccmsApiSearchVideoByPinyinTask;
import com.starcor.sccms.api.SccmsApiSearchVideoByPinyinTask.ISccmsApiSearchVideoByPinyinTaskListener;
import com.starcor.sccms.api.SccmsApiSearchVideoWorkSetByPinyinTask;
import com.starcor.sccms.api.SccmsApiSearchVideoWorkSetByPinyinTask.ISccmsApiSearchVideoWorkSetByPinyinTaskListener;
import com.starcor.sccms.api.SccmsApiSetVideoWishTask;
import com.starcor.sccms.api.SccmsApiSetVideoWishTask.ISccmsApiSetVideoWishTaskListener;
import com.starcor.sccms.api.SccmsApiSyncTimeTask;
import com.starcor.sccms.api.SccmsApiSyncTimeTask.ISccmsApiSyncTimeTaskListener;
import com.starcor.sccms.api.SccmsApiUploadAllCatchVideoRecordTask;
import com.starcor.sccms.api.SccmsApiUploadAllCatchVideoRecordTask.ISccmsApiUploadAllCatchVideoRecordTaskListener;
import com.starcor.sccms.api.SccmsApiUploadAllCollectRecordTask;
import com.starcor.sccms.api.SccmsApiUploadAllCollectRecordTask.ISccmsApiUploadAllCollectRecordTaskListener;
import com.starcor.sccms.api.SccmsApiUploadAllPlayRecordTask;
import com.starcor.sccms.api.SccmsApiUploadAllPlayRecordTask.ISccmsApiUploadAllPlayRecordTaskListener;
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
import com.starcor.sccms.api.SccmsApiXiaomiLoginTask;
import com.starcor.sccms.api.SccmsApiXiaomiLoginTask.ISccmsApiXiaomiLoginTaskListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ServerApiManager
{
  private static final String TAG = "ServerApiManager";
  private static ServerApiManager apiManager = null;
  private SCHttpApiEngine httpApiEngine = null;
  private final Handler mHandler = new Handler(Looper.getMainLooper());

  public static ServerApiManager i()
  {
    if (apiManager == null)
    {
      Logger.i("ServerApiManager", "i() first create");
      apiManager = new ServerApiManager();
    }
    return apiManager;
  }

  public static boolean isInstanced()
  {
    return apiManager != null;
  }

  public int APIAAABuyProduct(int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3, String paramString3, final SccmsApiAAABuyProductTask.ISccmsApiAAABuyProductTaskListener paramISccmsApiAAABuyProductTaskListener)
  {
    SccmsApiAAABuyProductTask localSccmsApiAAABuyProductTask = new SccmsApiAAABuyProductTask(paramInt1, paramString1, paramString2, paramInt2, paramInt3, paramString3);
    localSccmsApiAAABuyProductTask.setListener(new SccmsApiAAABuyProductTask.ISccmsApiAAABuyProductTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.128.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AAAOrderInfo paramAnonymousAAAOrderInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.128.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAAAOrderInfo);
          }
        });
      }
    });
    localSccmsApiAAABuyProductTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAAABuyProductTask);
  }

  public int APIAAABuySmsProduct(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, String paramString4, SccmsApiAAABuyProductTask.ISccmsApiAAABuyProductTaskListener paramISccmsApiAAABuyProductTaskListener)
  {
    SccmsApiAAABuyProductTask localSccmsApiAAABuyProductTask = new SccmsApiAAABuyProductTask(paramString1, paramString2, paramInt1, paramInt2, paramString3, paramString4);
    localSccmsApiAAABuyProductTask.setListener(paramISccmsApiAAABuyProductTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAABuyProductTask);
  }

  public int APIAAACouponUseTask(String paramString1, String paramString2, final SccmsApiAAACouponUseTask.ISccmsApiAAACouponUseTaskListener paramISccmsApiAAACouponUseTaskListener)
  {
    SccmsApiAAACouponUseTask localSccmsApiAAACouponUseTask = new SccmsApiAAACouponUseTask(paramString1, paramString2);
    localSccmsApiAAACouponUseTask.setListener(new SccmsApiAAACouponUseTask.ISccmsApiAAACouponUseTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.141.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AAACouponUse paramAnonymousAAACouponUse)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.141.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAAACouponUse);
          }
        });
      }
    });
    localSccmsApiAAACouponUseTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAAACouponUseTask);
  }

  public int APIAAAGetLicense(String paramString, final SccmsApiAAAGetLicenseTask.ISccmsApiGetLicenseTaskListener paramISccmsApiGetLicenseTaskListener)
  {
    SccmsApiAAAGetLicenseTask localSccmsApiAAAGetLicenseTask = new SccmsApiAAAGetLicenseTask(paramString);
    localSccmsApiAAAGetLicenseTask.setListener(new SccmsApiAAAGetLicenseTask.ISccmsApiGetLicenseTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        paramISccmsApiGetLicenseTaskListener.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAGetLicense paramAnonymousAAAGetLicense)
      {
        paramISccmsApiGetLicenseTaskListener.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAAAGetLicense);
      }
    });
    localSccmsApiAAAGetLicenseTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetLicenseTask);
  }

  public int APIAAAGetOrderRecordList(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, final SccmsApiAAAGetOrderRecordListTask.ISccmsApiAAAGetOrderRecordListTaskListener paramISccmsApiAAAGetOrderRecordListTaskListener)
  {
    SccmsApiAAAGetOrderRecordListTask localSccmsApiAAAGetOrderRecordListTask = new SccmsApiAAAGetOrderRecordListTask(paramString1, paramString2, paramInt1, paramInt2, paramInt3, paramInt4);
    localSccmsApiAAAGetOrderRecordListTask.setListener(new SccmsApiAAAGetOrderRecordListTask.ISccmsApiAAAGetOrderRecordListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.133.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AAAOrderRecordList paramAnonymousAAAOrderRecordList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.133.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAAAOrderRecordList);
          }
        });
      }
    });
    localSccmsApiAAAGetOrderRecordListTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetOrderRecordListTask);
  }

  public int APIAAAGetOrderState(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4, final SccmsApiAAAGetOrderStateTask.ISccmsApiAAAGetOrderStateTaskListener paramISccmsApiAAAGetOrderStateTaskListener)
  {
    SccmsApiAAAGetOrderStateTask localSccmsApiAAAGetOrderStateTask = new SccmsApiAAAGetOrderStateTask(paramString1, paramString2, paramInt, paramString3, paramString4);
    localSccmsApiAAAGetOrderStateTask.setListener(new SccmsApiAAAGetOrderStateTask.ISccmsApiAAAGetOrderStateTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.131.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AAAOrderState paramAnonymousAAAOrderState)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.131.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAAAOrderState);
          }
        });
      }
    });
    localSccmsApiAAAGetOrderStateTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetOrderStateTask);
  }

  public int APIAAAGetPayChannelList(String paramString1, String paramString2, final SccmsApiAAAGetPayChannelListTask.ISccmsApiAAAGetPayChannelListTaskListener paramISccmsApiAAAGetPayChannelListTaskListener)
  {
    SccmsApiAAAGetPayChannelListTask localSccmsApiAAAGetPayChannelListTask = new SccmsApiAAAGetPayChannelListTask(paramString1, paramString2);
    localSccmsApiAAAGetPayChannelListTask.setListener(new SccmsApiAAAGetPayChannelListTask.ISccmsApiAAAGetPayChannelListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.125.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AAAPayChannelList paramAnonymousAAAPayChannelList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.125.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAAAPayChannelList);
          }
        });
      }
    });
    localSccmsApiAAAGetPayChannelListTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetPayChannelListTask);
  }

  public int APIAAAGetPriceInfo(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, final SccmsApiAAAGetPriceInfoTask.ISccmsApiAAAGetPriceInfoTaskListener paramISccmsApiAAAGetPriceInfoTaskListener)
  {
    SccmsApiAAAGetPriceInfoTask localSccmsApiAAAGetPriceInfoTask = new SccmsApiAAAGetPriceInfoTask(paramString1, paramString2, paramInt1, paramInt2, paramString3);
    localSccmsApiAAAGetPriceInfoTask.setListener(new SccmsApiAAAGetPriceInfoTask.ISccmsApiAAAGetPriceInfoTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.127.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AAAPriceInfo paramAnonymousAAAPriceInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.127.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAAAPriceInfo);
          }
        });
      }
    });
    localSccmsApiAAAGetPriceInfoTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetPriceInfoTask);
  }

  public int APIAAAGetPriceList(String paramString, final SccmsApiAAAGetPriceListTask.ISccmsApiAAAGetPriceListTaskListener paramISccmsApiAAAGetPriceListTaskListener)
  {
    SccmsApiAAAGetPriceListTask localSccmsApiAAAGetPriceListTask = new SccmsApiAAAGetPriceListTask(paramString);
    localSccmsApiAAAGetPriceListTask.setListener(new SccmsApiAAAGetPriceListTask.ISccmsApiAAAGetPriceListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.124.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AAAPriceList paramAnonymousAAAPriceList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.124.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAAAPriceList);
          }
        });
      }
    });
    localSccmsApiAAAGetPriceListTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetPriceListTask);
  }

  public int APIAAAGetProductInfo(String paramString1, String paramString2, int paramInt1, int paramInt2, final SccmsApiAAAGetProductInfoTask.ISccmsApiAAAGetProductInfoTaskListener paramISccmsApiAAAGetProductInfoTaskListener)
  {
    SccmsApiAAAGetProductInfoTask localSccmsApiAAAGetProductInfoTask = new SccmsApiAAAGetProductInfoTask(paramString1, paramString2, paramInt1, paramInt2);
    localSccmsApiAAAGetProductInfoTask.setListener(new SccmsApiAAAGetProductInfoTask.ISccmsApiAAAGetProductInfoTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.126.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AAAProductInfo paramAnonymousAAAProductInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.126.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAAAProductInfo);
          }
        });
      }
    });
    localSccmsApiAAAGetProductInfoTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetProductInfoTask);
  }

  public int APIAAAGetProductList(String paramString1, String paramString2, int paramInt1, int paramInt2, final SccmsApiAAAGetProductListTask.ISccmsApiAAAGetProductListTaskListener paramISccmsApiAAAGetProductListTaskListener)
  {
    SccmsApiAAAGetProductListTask localSccmsApiAAAGetProductListTask = new SccmsApiAAAGetProductListTask(paramString1, paramString2, paramInt1, paramInt2);
    localSccmsApiAAAGetProductListTask.setListener(new SccmsApiAAAGetProductListTask.ISccmsApiAAAGetProductListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.123.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AAAProductList paramAnonymousAAAProductList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.123.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAAAProductList);
          }
        });
      }
    });
    localSccmsApiAAAGetProductListTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetProductListTask);
  }

  public int APIAAAGetVipInfo(String paramString, int paramInt, final SccmsApiAAAGetVipInfoTask.ISccmsApiAAAGetVipInfoTaskListener paramISccmsApiAAAGetVipInfoTaskListener)
  {
    SccmsApiAAAGetVipInfoTask localSccmsApiAAAGetVipInfoTask = new SccmsApiAAAGetVipInfoTask(paramString, paramInt);
    localSccmsApiAAAGetVipInfoTask.setListener(new SccmsApiAAAGetVipInfoTask.ISccmsApiAAAGetVipInfoTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.122.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AAAVipInfo paramAnonymousAAAVipInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.122.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAAAVipInfo);
          }
        });
      }
    });
    localSccmsApiAAAGetVipInfoTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetVipInfoTask);
  }

  public int APIAAAGetVipList(String paramString, final SccmsApiAAAGetVipListTask.ISccmsApiAAAGetVipListTaskListener paramISccmsApiAAAGetVipListTaskListener)
  {
    SccmsApiAAAGetVipListTask localSccmsApiAAAGetVipListTask = new SccmsApiAAAGetVipListTask(paramString);
    localSccmsApiAAAGetVipListTask.setListener(new SccmsApiAAAGetVipListTask.ISccmsApiAAAGetVipListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.121.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AAAVipList paramAnonymousAAAVipList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.121.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAAAVipList);
          }
        });
      }
    });
    localSccmsApiAAAGetVipListTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetVipListTask);
  }

  public int APIAAAGetWechatPayQrcodeTask(String paramString, final SccmsApiAAAGetWechatPayQrcodeTask.ISccmsApiAAAGetWechatPayQrcodeTaskListener paramISccmsApiAAAGetWechatPayQrcodeTaskListener)
  {
    SccmsApiAAAGetWechatPayQrcodeTask localSccmsApiAAAGetWechatPayQrcodeTask = new SccmsApiAAAGetWechatPayQrcodeTask(paramString);
    localSccmsApiAAAGetWechatPayQrcodeTask.setListener(new SccmsApiAAAGetWechatPayQrcodeTask.ISccmsApiAAAGetWechatPayQrcodeTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.140.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AAAWechatPayQrcode paramAnonymousAAAWechatPayQrcode)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.140.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAAAWechatPayQrcode);
          }
        });
      }
    });
    localSccmsApiAAAGetWechatPayQrcodeTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetWechatPayQrcodeTask);
  }

  public int APIAAARecharge(int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3, String paramString3, String paramString4, String paramString5, final SccmsApiAAARechargeTask.ISccmsApiAAARechargeTaskListener paramISccmsApiAAARechargeTaskListener)
  {
    SccmsApiAAARechargeTask localSccmsApiAAARechargeTask = new SccmsApiAAARechargeTask(paramInt1, paramString1, paramString2, paramInt2, paramInt3, paramString3, paramString4, paramString5);
    localSccmsApiAAARechargeTask.setListener(new SccmsApiAAARechargeTask.ISccmsApiAAARechargeTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.130.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AAAOrderInfo paramAnonymousAAAOrderInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.130.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAAAOrderInfo);
          }
        });
      }
    });
    localSccmsApiAAARechargeTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAAARechargeTask);
  }

  public int APIAAARecharge(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, String paramString4, String paramString5, final SccmsApiAAARechargeTask.ISccmsApiAAARechargeTaskListener paramISccmsApiAAARechargeTaskListener)
  {
    SccmsApiAAARechargeTask localSccmsApiAAARechargeTask = new SccmsApiAAARechargeTask(paramString1, paramString2, paramInt1, paramInt2, paramString3, paramString4, paramString5);
    localSccmsApiAAARechargeTask.setListener(new SccmsApiAAARechargeTask.ISccmsApiAAARechargeTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.129.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AAAOrderInfo paramAnonymousAAAOrderInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.129.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAAAOrderInfo);
          }
        });
      }
    });
    localSccmsApiAAARechargeTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAAARechargeTask);
  }

  public int APIAAASmsPayTask(String paramString, SccmsApiAAASmsPayTask.ISccmsApiAAASmsPayTaskListener paramISccmsApiAAASmsPayTaskListener)
  {
    SccmsApiAAASmsPayTask localSccmsApiAAASmsPayTask = new SccmsApiAAASmsPayTask(paramString);
    localSccmsApiAAASmsPayTask.setListener(paramISccmsApiAAASmsPayTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAASmsPayTask);
  }

  public int APIAAAVerifyLicense(String paramString, final SccmsApiAAAVerifyLicenseTask.ISccmsApiVerifyLicenseTaskListener paramISccmsApiVerifyLicenseTaskListener)
  {
    SccmsApiAAAVerifyLicenseTask localSccmsApiAAAVerifyLicenseTask = new SccmsApiAAAVerifyLicenseTask(paramString);
    localSccmsApiAAAVerifyLicenseTask.setListener(new SccmsApiAAAVerifyLicenseTask.ISccmsApiVerifyLicenseTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.120.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AAAVerifyLicense paramAnonymousAAAVerifyLicense)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.120.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAAAVerifyLicense);
          }
        });
      }
    });
    localSccmsApiAAAVerifyLicenseTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAAAVerifyLicenseTask);
  }

  public int APIAddBroadCastRecord(String paramString, final SccmsApiBroadcastReserveTask.ISccmsApiBroadCastReserveTaskListener paramISccmsApiBroadCastReserveTaskListener)
  {
    SccmsApiBroadcastReserveTask localSccmsApiBroadcastReserveTask = new SccmsApiBroadcastReserveTask(paramString, 1);
    localSccmsApiBroadcastReserveTask.setListener(new SccmsApiBroadcastReserveTask.ISccmsApiBroadCastReserveTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            if (ServerApiManager.59.this.val$lsr != null)
              ServerApiManager.59.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ReserveListItem paramAnonymousReserveListItem)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            if (ServerApiManager.59.this.val$lsr != null)
              ServerApiManager.59.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousReserveListItem);
          }
        });
      }
    });
    localSccmsApiBroadcastReserveTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiBroadcastReserveTask);
  }

  public int APIAddCatchVideoRecord(AddCollectParams paramAddCollectParams, final SccmsApiAddCatchVideoRecordTask.ISccmsApiAddCatchVideoRecordTaskListener paramISccmsApiAddCatchVideoRecordTaskListener)
  {
    SccmsApiAddCatchVideoRecordTask localSccmsApiAddCatchVideoRecordTask = new SccmsApiAddCatchVideoRecordTask(paramAddCollectParams);
    localSccmsApiAddCatchVideoRecordTask.setListener(new SccmsApiAddCatchVideoRecordTask.ISccmsApiAddCatchVideoRecordTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.65.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final String paramAnonymousString)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.65.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousString);
          }
        });
      }
    });
    localSccmsApiAddCatchVideoRecordTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAddCatchVideoRecordTask);
  }

  public int APIAddCatchVideoRecordV2(AddCollectV2Params paramAddCollectV2Params, final SccmsApiAddCatchVideoRecordV2Task.ISccmsApiAddCatchVideoRecordV2TaskListener paramISccmsApiAddCatchVideoRecordV2TaskListener)
  {
    SccmsApiAddCatchVideoRecordV2Task localSccmsApiAddCatchVideoRecordV2Task = new SccmsApiAddCatchVideoRecordV2Task(paramAddCollectV2Params);
    localSccmsApiAddCatchVideoRecordV2Task.setListener(new SccmsApiAddCatchVideoRecordV2Task.ISccmsApiAddCatchVideoRecordV2TaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.74.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.74.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiAddCatchVideoRecordV2Task.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAddCatchVideoRecordV2Task);
  }

  public int APIAddCollectRecord(AddCollectParams paramAddCollectParams, final SccmsApiAddCollectRecordTask.ISccmsApiAddCollectRecordTaskListener paramISccmsApiAddCollectRecordTaskListener)
  {
    SccmsApiAddCollectRecordTask localSccmsApiAddCollectRecordTask = new SccmsApiAddCollectRecordTask(paramAddCollectParams);
    localSccmsApiAddCollectRecordTask.setListener(new SccmsApiAddCollectRecordTask.ISccmsApiAddCollectRecordTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.58.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final String paramAnonymousString)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.58.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousString);
          }
        });
      }
    });
    localSccmsApiAddCollectRecordTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAddCollectRecordTask);
  }

  public int APIAddCollectRecordV2(AddCollectV2Params paramAddCollectV2Params, final SccmsApiAddCollectRecordV2Task.ISccmsApiAddCollectRecordV2TaskListener paramISccmsApiAddCollectRecordV2TaskListener)
  {
    SccmsApiAddCollectRecordV2Task localSccmsApiAddCollectRecordV2Task = new SccmsApiAddCollectRecordV2Task(paramAddCollectV2Params);
    localSccmsApiAddCollectRecordV2Task.setListener(new SccmsApiAddCollectRecordV2Task.ISccmsApiAddCollectRecordV2TaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.73.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.73.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiAddCollectRecordV2Task.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAddCollectRecordV2Task);
  }

  public int APIAddLiveShowRecord(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, final SccmsApiLiveShowReserveTask.ISccmsApiLiveShowReserveTaskListener paramISccmsApiLiveShowReserveTaskListener)
  {
    SccmsApiLiveShowReserveTask localSccmsApiLiveShowReserveTask = new SccmsApiLiveShowReserveTask(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, 1);
    localSccmsApiLiveShowReserveTask.setListener(new SccmsApiLiveShowReserveTask.ISccmsApiLiveShowReserveTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            if (ServerApiManager.61.this.val$lsr != null)
              ServerApiManager.61.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ReserveListItem paramAnonymousReserveListItem)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            if (ServerApiManager.61.this.val$lsr != null)
              ServerApiManager.61.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousReserveListItem);
          }
        });
      }
    });
    localSccmsApiLiveShowReserveTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiLiveShowReserveTask);
  }

  public int APIAddPlayRecord(AddCollectParams paramAddCollectParams, final SccmsApiAddPlayRecordTask.ISccmsApiAddPlayRecordTaskListener paramISccmsApiAddPlayRecordTaskListener)
  {
    SccmsApiAddPlayRecordTask localSccmsApiAddPlayRecordTask = new SccmsApiAddPlayRecordTask(paramAddCollectParams);
    localSccmsApiAddPlayRecordTask.setListener(new SccmsApiAddPlayRecordTask.ISccmsApiAddPlayRecordTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.57.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final String paramAnonymousString)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.57.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousString);
          }
        });
      }
    });
    localSccmsApiAddPlayRecordTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAddPlayRecordTask);
  }

  public int APIAddPlayRecordV2(AddCollectV2Params paramAddCollectV2Params, final SccmsApiAddPlayRecordV2Task.ISccmsApiAddPlayRecordV2TaskListener paramISccmsApiAddPlayRecordV2TaskListener)
  {
    SccmsApiAddPlayRecordV2Task localSccmsApiAddPlayRecordV2Task = new SccmsApiAddPlayRecordV2Task(paramAddCollectV2Params);
    localSccmsApiAddPlayRecordV2Task.setListener(new SccmsApiAddPlayRecordV2Task.ISccmsApiAddPlayRecordV2TaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.72.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.72.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiAddPlayRecordV2Task.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAddPlayRecordV2Task);
  }

  public int APIAddReplyPlayRecord(VideoInfo paramVideoInfo, PlayBillItem paramPlayBillItem, final SccmsApiReplayReserveTask.ISccmsApiReplayReserveTaskListener paramISccmsApiReplayReserveTaskListener)
  {
    SccmsApiReplayReserveTask localSccmsApiReplayReserveTask = new SccmsApiReplayReserveTask(paramVideoInfo, paramPlayBillItem, 1);
    localSccmsApiReplayReserveTask.setListener(new SccmsApiReplayReserveTask.ISccmsApiReplayReserveTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            if (ServerApiManager.63.this.val$lsr != null)
              ServerApiManager.63.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ReserveListItem paramAnonymousReserveListItem)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            if (ServerApiManager.63.this.val$lsr != null)
              ServerApiManager.63.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousReserveListItem);
          }
        });
      }
    });
    localSccmsApiReplayReserveTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiReplayReserveTask);
  }

  public int APIAddUserRecommendRecord(AddUserRecommendParams paramAddUserRecommendParams, SccmsApiAddMediaRecommendTask.ISccmsApiAddMediaRecommendTaskListener paramISccmsApiAddMediaRecommendTaskListener)
  {
    SccmsApiAddMediaRecommendTask localSccmsApiAddMediaRecommendTask = new SccmsApiAddMediaRecommendTask(paramAddUserRecommendParams);
    localSccmsApiAddMediaRecommendTask.setListener(paramISccmsApiAddMediaRecommendTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAddMediaRecommendTask);
  }

  public int APIApiExistsUserWish(String paramString, SccmsApiExistsUserWishTask.ISccmsApiExistsUserWishTaskListener paramISccmsApiExistsUserWishTaskListener)
  {
    SccmsApiExistsUserWishTask localSccmsApiExistsUserWishTask = new SccmsApiExistsUserWishTask(paramString, paramISccmsApiExistsUserWishTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiExistsUserWishTask);
  }

  public int APIApiGetMessage(String paramString, SccmsApiGetMessageTask.ISccmsApiGetMessageTaskListener paramISccmsApiGetMessageTaskListener)
  {
    SccmsApiGetMessageTask localSccmsApiGetMessageTask = new SccmsApiGetMessageTask(paramString);
    localSccmsApiGetMessageTask.setListener(paramISccmsApiGetMessageTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetMessageTask);
  }

  public int APICancelVideoWish(VideoInfo paramVideoInfo, SccmsApiCancelVideoWishTask.ISccmsApiCancelVideoWishTaskListener paramISccmsApiCancelVideoWishTaskListener)
  {
    SccmsApiCancelVideoWishTask localSccmsApiCancelVideoWishTask = new SccmsApiCancelVideoWishTask(paramVideoInfo, paramISccmsApiCancelVideoWishTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiCancelVideoWishTask);
  }

  public int APICenterEdgeScheduling(IApiTaskListener paramIApiTaskListener)
  {
    ApiTaskCenterEdgeScheduling localApiTaskCenterEdgeScheduling = new ApiTaskCenterEdgeScheduling();
    localApiTaskCenterEdgeScheduling.setListener(paramIApiTaskListener);
    return this.httpApiEngine.addTask(localApiTaskCenterEdgeScheduling);
  }

  public int APICheckMgtvNetState(final MgtvApiCheckNetStateTask.IMgtvApiCheckNetStateTaskkListener paramIMgtvApiCheckNetStateTaskkListener)
  {
    MgtvApiCheckNetStateTask localMgtvApiCheckNetStateTask = new MgtvApiCheckNetStateTask();
    localMgtvApiCheckNetStateTask.setListener(new MgtvApiCheckNetStateTask.IMgtvApiCheckNetStateTaskkListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.36.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final String paramAnonymousString)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.36.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousString);
          }
        });
      }
    });
    localMgtvApiCheckNetStateTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localMgtvApiCheckNetStateTask);
  }

  public int APICheckUiPackageUpdate(String paramString1, String paramString2, final SccmsApiCheckUiPackageUpdateTask.ISccmsApiCheckUiPackageUpdateTaskListener paramISccmsApiCheckUiPackageUpdateTaskListener)
  {
    SccmsApiCheckUiPackageUpdateTask localSccmsApiCheckUiPackageUpdateTask = new SccmsApiCheckUiPackageUpdateTask(paramString1, paramString2);
    localSccmsApiCheckUiPackageUpdateTask.setListener(new SccmsApiCheckUiPackageUpdateTask.ISccmsApiCheckUiPackageUpdateTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.38.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UiUpdatePackage paramAnonymousUiUpdatePackage)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.38.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUiUpdatePackage);
          }
        });
      }
    });
    localSccmsApiCheckUiPackageUpdateTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiCheckUiPackageUpdateTask);
  }

  public int APICheckUpdate(final SccmsApiCheckUpdateTask.ISccmsApiCheckUpdateTaskListener paramISccmsApiCheckUpdateTaskListener)
  {
    SccmsApiCheckUpdateTask localSccmsApiCheckUpdateTask = new SccmsApiCheckUpdateTask();
    localSccmsApiCheckUpdateTask.setListener(new SccmsApiCheckUpdateTask.ISccmsApiCheckUpdateTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.43.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final Version paramAnonymousVersion)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.43.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousVersion);
          }
        });
      }
    });
    return this.httpApiEngine.addTask(localSccmsApiCheckUpdateTask);
  }

  public int APICheckValidByWebToken(final SccmsApiCheckValidByWebTokenTask.ISccmsApiCheckValidByWebTokenTaskListener paramISccmsApiCheckValidByWebTokenTaskListener)
  {
    SccmsApiCheckValidByWebTokenTask localSccmsApiCheckValidByWebTokenTask = new SccmsApiCheckValidByWebTokenTask();
    localSccmsApiCheckValidByWebTokenTask.setListener(new SccmsApiCheckValidByWebTokenTask.ISccmsApiCheckValidByWebTokenTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.44.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserInfo paramAnonymousUserInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.44.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserInfo);
          }
        });
      }
    });
    localSccmsApiCheckValidByWebTokenTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiCheckValidByWebTokenTask);
  }

  public int APICheckWebToken(final SccmsApiCheckWebTokenTask.ISccmsApiCheckWebTokenTaskListener paramISccmsApiCheckWebTokenTaskListener)
  {
    SccmsApiCheckWebTokenTask localSccmsApiCheckWebTokenTask = new SccmsApiCheckWebTokenTask();
    localSccmsApiCheckWebTokenTask.setListener(new SccmsApiCheckWebTokenTask.ISccmsApiCheckWebTokenTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.89.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserInfo paramAnonymousUserInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.89.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserInfo);
          }
        });
      }
    });
    localSccmsApiCheckWebTokenTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiCheckWebTokenTask);
  }

  public int APIConvertID(String paramString1, String paramString2, int paramInt1, int paramInt2, int paramInt3, final SccmsApiConvertIDTask.ISccmsApiConvertIDTaskListener paramISccmsApiConvertIDTaskListener)
  {
    SccmsApiConvertIDTask localSccmsApiConvertIDTask = new SccmsApiConvertIDTask(paramString1, paramString2, paramInt1, paramInt2, paramInt3);
    localSccmsApiConvertIDTask.setListener(new SccmsApiConvertIDTask.ISccmsApiConvertIDTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.96.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final InjectingID paramAnonymousInjectingID)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.96.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousInjectingID);
          }
        });
      }
    });
    localSccmsApiConvertIDTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiConvertIDTask);
  }

  public int APIDelBroadCastRecord(String paramString, final SccmsApiBroadcastReserveTask.ISccmsApiBroadCastReserveTaskListener paramISccmsApiBroadCastReserveTaskListener)
  {
    SccmsApiBroadcastReserveTask localSccmsApiBroadcastReserveTask = new SccmsApiBroadcastReserveTask(paramString, 2);
    localSccmsApiBroadcastReserveTask.setListener(new SccmsApiBroadcastReserveTask.ISccmsApiBroadCastReserveTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            if (ServerApiManager.60.this.val$lsr != null)
              ServerApiManager.60.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ReserveListItem paramAnonymousReserveListItem)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            if (ServerApiManager.60.this.val$lsr != null)
              ServerApiManager.60.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousReserveListItem);
          }
        });
      }
    });
    localSccmsApiBroadcastReserveTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiBroadcastReserveTask);
  }

  public int APIDelCatchVideoRecord(String paramString, final SccmsApiDelCatchVideoRecordTask.ISccmsApiDelCatchVideoRecordTaskListener paramISccmsApiDelCatchVideoRecordTaskListener)
  {
    SccmsApiDelCatchVideoRecordTask localSccmsApiDelCatchVideoRecordTask = new SccmsApiDelCatchVideoRecordTask(paramString);
    localSccmsApiDelCatchVideoRecordTask.setListener(new SccmsApiDelCatchVideoRecordTask.ISccmsApiDelCatchVideoRecordTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.71.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final String paramAnonymousString)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.71.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousString);
          }
        });
      }
    });
    localSccmsApiDelCatchVideoRecordTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiDelCatchVideoRecordTask);
  }

  public int APIDelCatchVideoRecordV2(String paramString, VideoInfo paramVideoInfo, final SccmsApiDelCatchVideoRecordV2Task.ISccmsApiDelCatchVideoRecordV2TaskListener paramISccmsApiDelCatchVideoRecordV2TaskListener)
  {
    SccmsApiDelCatchVideoRecordV2Task localSccmsApiDelCatchVideoRecordV2Task = new SccmsApiDelCatchVideoRecordV2Task(paramString, paramVideoInfo);
    localSccmsApiDelCatchVideoRecordV2Task.setListener(new SccmsApiDelCatchVideoRecordV2Task.ISccmsApiDelCatchVideoRecordV2TaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.83.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.83.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiDelCatchVideoRecordV2Task.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiDelCatchVideoRecordV2Task);
  }

  public int APIDelCollectRecord(String paramString, final SccmsApiDelCollectRecordTask.ISccmsApiDelColllectRecordTaskListener paramISccmsApiDelColllectRecordTaskListener)
  {
    SccmsApiDelCollectRecordTask localSccmsApiDelCollectRecordTask = new SccmsApiDelCollectRecordTask(paramString);
    localSccmsApiDelCollectRecordTask.setListener(new SccmsApiDelCollectRecordTask.ISccmsApiDelColllectRecordTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.69.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final String paramAnonymousString)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.69.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousString);
          }
        });
      }
    });
    localSccmsApiDelCollectRecordTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiDelCollectRecordTask);
  }

  public int APIDelCollectRecordV2(String paramString, final SccmsApiDelCollectRecordV2Task.ISccmsApiDelColllectRecordV2TaskListener paramISccmsApiDelColllectRecordV2TaskListener)
  {
    SccmsApiDelCollectRecordV2Task localSccmsApiDelCollectRecordV2Task = new SccmsApiDelCollectRecordV2Task(paramString);
    localSccmsApiDelCollectRecordV2Task.setListener(new SccmsApiDelCollectRecordV2Task.ISccmsApiDelColllectRecordV2TaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.81.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.81.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiDelCollectRecordV2Task.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiDelCollectRecordV2Task);
  }

  public int APIDelLiveShowRecord(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, final SccmsApiLiveShowReserveTask.ISccmsApiLiveShowReserveTaskListener paramISccmsApiLiveShowReserveTaskListener)
  {
    SccmsApiLiveShowReserveTask localSccmsApiLiveShowReserveTask = new SccmsApiLiveShowReserveTask(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, 2);
    localSccmsApiLiveShowReserveTask.setListener(new SccmsApiLiveShowReserveTask.ISccmsApiLiveShowReserveTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            if (ServerApiManager.62.this.val$lsr != null)
              ServerApiManager.62.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ReserveListItem paramAnonymousReserveListItem)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            if (ServerApiManager.62.this.val$lsr != null)
              ServerApiManager.62.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousReserveListItem);
          }
        });
      }
    });
    localSccmsApiLiveShowReserveTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiLiveShowReserveTask);
  }

  public int APIDelPlayRecord(String paramString, final SccmsApiDelPlayRecordTask.ISccmsApiDelPlayRecordTaskListener paramISccmsApiDelPlayRecordTaskListener)
  {
    SccmsApiDelPlayRecordTask localSccmsApiDelPlayRecordTask = new SccmsApiDelPlayRecordTask(paramString);
    localSccmsApiDelPlayRecordTask.setListener(new SccmsApiDelPlayRecordTask.ISccmsApiDelPlayRecordTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.70.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final String paramAnonymousString)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.70.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousString);
          }
        });
      }
    });
    localSccmsApiDelPlayRecordTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiDelPlayRecordTask);
  }

  public int APIDelPlayRecordV2(String paramString, final SccmsApiDelPlayRecordV2Task.ISccmsApiDelPlayRecordV2TaskListener paramISccmsApiDelPlayRecordV2TaskListener)
  {
    SccmsApiDelPlayRecordV2Task localSccmsApiDelPlayRecordV2Task = new SccmsApiDelPlayRecordV2Task(paramString);
    localSccmsApiDelPlayRecordV2Task.setListener(new SccmsApiDelPlayRecordV2Task.ISccmsApiDelPlayRecordV2TaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.82.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.82.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiDelPlayRecordV2Task.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiDelPlayRecordV2Task);
  }

  public int APIDeleteReplyPlayRecord(VideoInfo paramVideoInfo, PlayBillItem paramPlayBillItem, final SccmsApiReplayReserveTask.ISccmsApiReplayReserveTaskListener paramISccmsApiReplayReserveTaskListener)
  {
    SccmsApiReplayReserveTask localSccmsApiReplayReserveTask = new SccmsApiReplayReserveTask(paramVideoInfo, paramPlayBillItem, 2);
    localSccmsApiReplayReserveTask.setListener(new SccmsApiReplayReserveTask.ISccmsApiReplayReserveTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            if (ServerApiManager.64.this.val$lsr != null)
              ServerApiManager.64.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ReserveListItem paramAnonymousReserveListItem)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            if (ServerApiManager.64.this.val$lsr != null)
              ServerApiManager.64.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousReserveListItem);
          }
        });
      }
    });
    localSccmsApiReplayReserveTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiReplayReserveTask);
  }

  public int APIDrmReportDecodeCapacity(final SccmsApiDrmReportDecodeCapacityTask.ISccmsApiReportDecodeCapacityTaskListener paramISccmsApiReportDecodeCapacityTaskListener)
  {
    SccmsApiDrmReportDecodeCapacityTask localSccmsApiDrmReportDecodeCapacityTask = new SccmsApiDrmReportDecodeCapacityTask(new SccmsApiDrmReportDecodeCapacityTask.ISccmsApiReportDecodeCapacityTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        paramISccmsApiReportDecodeCapacityTaskListener.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, DrmReportDecodeCapacityInfo paramAnonymousDrmReportDecodeCapacityInfo)
      {
        paramISccmsApiReportDecodeCapacityTaskListener.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousDrmReportDecodeCapacityInfo);
      }
    });
    localSccmsApiDrmReportDecodeCapacityTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiDrmReportDecodeCapacityTask);
  }

  public int APIGetActorStarInfoTask(String paramString1, String paramString2, final SccmsApiGetActorStarInfoTask.ISccmsApiGetActorStarInfoTaskListener paramISccmsApiGetActorStarInfoTaskListener)
  {
    SccmsApiGetActorStarInfoTask localSccmsApiGetActorStarInfoTask = new SccmsApiGetActorStarInfoTask(paramString1, paramString2);
    localSccmsApiGetActorStarInfoTask.setListener(new SccmsApiGetActorStarInfoTask.ISccmsApiGetActorStarInfoTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.146.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ActorStarInfoList paramAnonymousActorStarInfoList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.146.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousActorStarInfoList);
          }
        });
      }
    });
    localSccmsApiGetActorStarInfoTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetActorStarInfoTask);
  }

  public int APIGetAdInfoByAdPos(String paramString, final SccmsApiGetAdInfoByAdPosTask.ISccmsApiGetAdInfoByAdPosTaskListener paramISccmsApiGetAdInfoByAdPosTaskListener)
  {
    SccmsApiGetAdInfoByAdPosTask localSccmsApiGetAdInfoByAdPosTask = new SccmsApiGetAdInfoByAdPosTask(paramString);
    localSccmsApiGetAdInfoByAdPosTask.setListener(new SccmsApiGetAdInfoByAdPosTask.ISccmsApiGetAdInfoByAdPosTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.94.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AdInfos paramAnonymousAdInfos)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.94.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAdInfos);
          }
        });
      }
    });
    localSccmsApiGetAdInfoByAdPosTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetAdInfoByAdPosTask);
  }

  public int APIGetAdInfoByAdPosList(final SccmsApiGetAdInfoByAdPosListTask.ISccmsApiGetAdInfoByAdPosListTaskListener paramISccmsApiGetAdInfoByAdPosListTaskListener, String[] paramArrayOfString)
  {
    SccmsApiGetAdInfoByAdPosListTask localSccmsApiGetAdInfoByAdPosListTask = new SccmsApiGetAdInfoByAdPosListTask(paramArrayOfString);
    localSccmsApiGetAdInfoByAdPosListTask.setListener(new SccmsApiGetAdInfoByAdPosListTask.ISccmsApiGetAdInfoByAdPosListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.95.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<AdPosEntity> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.95.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiGetAdInfoByAdPosListTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetAdInfoByAdPosListTask);
  }

  public int APIGetAdInfoByVideoId(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, final SccmsApiGetAdInfoByVideoIdTask.ISccmsApiGetAdInfoByVideoIdTaskListener paramISccmsApiGetAdInfoByVideoIdTaskListener)
  {
    SccmsApiGetAdInfoByVideoIdTask localSccmsApiGetAdInfoByVideoIdTask = new SccmsApiGetAdInfoByVideoIdTask(paramString1, paramInt, paramString2, paramString3, paramString4);
    localSccmsApiGetAdInfoByVideoIdTask.setListener(new SccmsApiGetAdInfoByVideoIdTask.ISccmsApiGetAdInfoByVideoIdTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.93.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<AdvertisementPosInfo> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.93.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiGetAdInfoByVideoIdTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetAdInfoByVideoIdTask);
  }

  public int APIGetAdInfoData(String paramString, final SccmsApiGetAdInfoTask.ISccmsApiGetAdInfoTaskListener paramISccmsApiGetAdInfoTaskListener)
  {
    SccmsApiGetAdInfoTask localSccmsApiGetAdInfoTask = new SccmsApiGetAdInfoTask(paramString);
    localSccmsApiGetAdInfoTask.setListener(new SccmsApiGetAdInfoTask.ISccmsApiGetAdInfoTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.28.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<AdPos> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.28.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiGetAdInfoTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetAdInfoTask);
  }

  public int APIGetAdInfoTask(String paramString1, String paramString2, final GetAdInfoTask.IGetAdInfoListener paramIGetAdInfoListener)
  {
    GetAdInfoTask localGetAdInfoTask = new GetAdInfoTask(paramString1, paramString2);
    localGetAdInfoTask.setListener(new GetAdInfoTask.IGetAdInfoListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.144.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final MgAdInfo paramAnonymousMgAdInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.144.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousMgAdInfo);
          }
        });
      }
    });
    localGetAdInfoTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localGetAdInfoTask);
  }

  public int APIGetAdPosByPageId(String paramString, final SccmsApiGetAdPosByPageIdTask.ISccmsApiGetAdPosByPageIdTaskListener paramISccmsApiGetAdPosByPageIdTaskListener)
  {
    SccmsApiGetAdPosByPageIdTask localSccmsApiGetAdPosByPageIdTask = new SccmsApiGetAdPosByPageIdTask(paramString);
    localSccmsApiGetAdPosByPageIdTask.setListener(new SccmsApiGetAdPosByPageIdTask.ISccmsApiGetAdPosByPageIdTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.92.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<AdPosByPageIdInfo> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.92.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiGetAdPosByPageIdTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetAdPosByPageIdTask);
  }

  public ServerApiTask APIGetAdUrlFromMgTask(String paramString1, String paramString2, int paramInt1, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, int paramInt2, int paramInt3, int paramInt4, final GetAdUrlFromMgTask.IMGCmsGetAdInfoTaskListener paramIMGCmsGetAdInfoTaskListener)
  {
    GetAdUrlFromMgTask localGetAdUrlFromMgTask = new GetAdUrlFromMgTask(paramString1, paramString2, paramInt1, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, paramString13, paramInt2, paramInt3, paramInt4);
    localGetAdUrlFromMgTask.setListener(new GetAdUrlFromMgTask.IMGCmsGetAdInfoTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.142.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final String paramAnonymousString)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.142.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousString);
          }
        });
      }
    });
    localGetAdUrlFromMgTask.setIsUiSafe(false);
    this.httpApiEngine.addTask(localGetAdUrlFromMgTask);
    return localGetAdUrlFromMgTask;
  }

  public int APIGetAgreements(final SccmsApiGetAgreementTask.ISccmsApiGetAgreementTaskListener paramISccmsApiGetAgreementTaskListener)
  {
    SccmsApiGetAgreementTask localSccmsApiGetAgreementTask = new SccmsApiGetAgreementTask();
    localSccmsApiGetAgreementTask.setListener(new SccmsApiGetAgreementTask.ISccmsApiGetAgreementTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.119.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final Agreements paramAnonymousAgreements)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.119.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAgreements);
          }
        });
      }
    });
    localSccmsApiGetAgreementTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetAgreementTask);
  }

  public int APIGetAppDownloadUrl(String paramString, final SccmsApiGetAppDownloadUrlTask.ISccmsApiGetAppDownloadUrlTaskListener paramISccmsApiGetAppDownloadUrlTaskListener)
  {
    SccmsApiGetAppDownloadUrlTask localSccmsApiGetAppDownloadUrlTask = new SccmsApiGetAppDownloadUrlTask(paramString);
    localSccmsApiGetAppDownloadUrlTask.setListener(new SccmsApiGetAppDownloadUrlTask.ISccmsApiGetAppDownloadUrlTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.99.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AppDownloadUrl paramAnonymousAppDownloadUrl)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.99.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAppDownloadUrl);
          }
        });
      }
    });
    localSccmsApiGetAppDownloadUrlTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetAppDownloadUrlTask);
  }

  public int APIGetAppInfo(String paramString1, String paramString2, final SccmsApiGetAppInfoTask.ISccmsApiGetAppInfoTaskListener paramISccmsApiGetAppInfoTaskListener)
  {
    SccmsApiGetAppInfoTask localSccmsApiGetAppInfoTask = new SccmsApiGetAppInfoTask(paramString1, paramString2);
    localSccmsApiGetAppInfoTask.setListener(new SccmsApiGetAppInfoTask.ISccmsApiGetAppInfoTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.98.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AppDetail paramAnonymousAppDetail)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.98.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAppDetail);
          }
        });
      }
    });
    localSccmsApiGetAppInfoTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetAppInfoTask);
  }

  public int APIGetAppList(String paramString, int paramInt1, int paramInt2, final SccmsApiGetAppListTask.ISccmsApiGetAppListTaskListener paramISccmsApiGetAppListTaskListener)
  {
    SccmsApiGetAppListTask localSccmsApiGetAppListTask = new SccmsApiGetAppListTask(paramString, paramInt1, paramInt2);
    localSccmsApiGetAppListTask.setListener(new SccmsApiGetAppListTask.ISccmsApiGetAppListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.97.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AppList paramAnonymousAppList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.97.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAppList);
          }
        });
      }
    });
    localSccmsApiGetAppListTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetAppListTask);
  }

  public int APIGetAssetsByVideoId(String paramString, final SccmsApiGetAssetsByVideoIdTask.ISccmsApiGetAssetsByVideoIdTaskListener paramISccmsApiGetAssetsByVideoIdTaskListener)
  {
    SccmsApiGetAssetsByVideoIdTask localSccmsApiGetAssetsByVideoIdTask = new SccmsApiGetAssetsByVideoIdTask(paramString);
    localSccmsApiGetAssetsByVideoIdTask.setListener(new SccmsApiGetAssetsByVideoIdTask.ISccmsApiGetAssetsByVideoIdTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.12.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AssetsInfo paramAnonymousAssetsInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.12.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAssetsInfo);
          }
        });
      }
    });
    localSccmsApiGetAssetsByVideoIdTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetAssetsByVideoIdTask);
  }

  public int APIGetAssetsInfoByVideoId(String paramString, final SccmsApiGetAssetsInfoByVideoIdTask.ISccmsApiGetAssetsInfoByVideoIdTaskListener paramISccmsApiGetAssetsInfoByVideoIdTaskListener)
  {
    SccmsApiGetAssetsInfoByVideoIdTask localSccmsApiGetAssetsInfoByVideoIdTask = new SccmsApiGetAssetsInfoByVideoIdTask(paramString);
    localSccmsApiGetAssetsInfoByVideoIdTask.setListener(new SccmsApiGetAssetsInfoByVideoIdTask.ISccmsApiGetAssetsInfoByVideoIdTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.11.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final MediaAssetsInfo paramAnonymousMediaAssetsInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.11.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousMediaAssetsInfo);
          }
        });
      }
    });
    localSccmsApiGetAssetsInfoByVideoIdTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetAssetsInfoByVideoIdTask);
  }

  public int APIGetBarrageData(String paramString1, String paramString2, String paramString3, final MgtvApiGetBarrageDataTask.IGetBarrageDataTaskListener paramIGetBarrageDataTaskListener)
  {
    MgtvApiGetBarrageDataTask localMgtvApiGetBarrageDataTask = new MgtvApiGetBarrageDataTask(paramString1, paramString2, paramString3);
    if (paramIGetBarrageDataTaskListener != null)
      localMgtvApiGetBarrageDataTask.setListener(new MgtvApiGetBarrageDataTask.IGetBarrageDataTaskListener()
      {
        public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          ServerApiManager.this.mHandler.post(new Runnable()
          {
            public void run()
            {
              ServerApiManager.4.this.val$listener.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
            }
          });
        }

        public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final BarrageResponse paramAnonymousBarrageResponse)
        {
          ServerApiManager.this.mHandler.post(new Runnable()
          {
            public void run()
            {
              ServerApiManager.4.this.val$listener.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousBarrageResponse);
            }
          });
        }
      });
    localMgtvApiGetBarrageDataTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localMgtvApiGetBarrageDataTask);
  }

  public SCHttpApiTask APIGetBootAdUrlFromMgTask(String paramString, final GetAdUrlFromMgTask.IMGCmsGetAdInfoTaskListener paramIMGCmsGetAdInfoTaskListener)
  {
    GetAdUrlFromMgTask localGetAdUrlFromMgTask = new GetAdUrlFromMgTask(paramString);
    localGetAdUrlFromMgTask.setListener(new GetAdUrlFromMgTask.IMGCmsGetAdInfoTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        paramIMGCmsGetAdInfoTaskListener.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, String paramAnonymousString)
      {
        paramIMGCmsGetAdInfoTaskListener.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousString);
      }
    });
    localGetAdUrlFromMgTask.setIsUiSafe(false);
    this.httpApiEngine.addTask(localGetAdUrlFromMgTask);
    return localGetAdUrlFromMgTask;
  }

  public int APIGetCatchVideoRecord(final SccmsApiGetCatchVideoRecordTask.ISccmsApiGetCatchVideoRecordTaskListener paramISccmsApiGetCatchVideoRecordTaskListener)
  {
    SccmsApiGetCatchVideoRecordTask localSccmsApiGetCatchVideoRecordTask = new SccmsApiGetCatchVideoRecordTask();
    localSccmsApiGetCatchVideoRecordTask.setListener(new SccmsApiGetCatchVideoRecordTask.ISccmsApiGetCatchVideoRecordTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.68.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.68.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiGetCatchVideoRecordTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetCatchVideoRecordTask);
  }

  public int APIGetCatchVideoRecordV2(final SccmsApiGetCatchVideoRecordV2Task.ISccmsApiGetCatchVideoRecordV2TaskListener paramISccmsApiGetCatchVideoRecordV2TaskListener)
  {
    SccmsApiGetCatchVideoRecordV2Task localSccmsApiGetCatchVideoRecordV2Task = new SccmsApiGetCatchVideoRecordV2Task();
    localSccmsApiGetCatchVideoRecordV2Task.setListener(new SccmsApiGetCatchVideoRecordV2Task.ISccmsApiGetCatchVideoRecordV2TaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.80.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.80.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiGetCatchVideoRecordV2Task.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetCatchVideoRecordV2Task);
  }

  public int APIGetCatchVideoRecordV2(final SccmsApiGetCatchVideoRecordV2Task.ISccmsApiGetCatchVideoRecordV2TaskListener paramISccmsApiGetCatchVideoRecordV2TaskListener, boolean paramBoolean)
  {
    if (paramBoolean)
      return APIGetCatchVideoRecordV2(paramISccmsApiGetCatchVideoRecordV2TaskListener);
    SccmsApiGetCatchVideoRecordV2Task localSccmsApiGetCatchVideoRecordV2Task = new SccmsApiGetCatchVideoRecordV2Task();
    localSccmsApiGetCatchVideoRecordV2Task.setListener(new SccmsApiGetCatchVideoRecordV2Task.ISccmsApiGetCatchVideoRecordV2TaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        paramISccmsApiGetCatchVideoRecordV2TaskListener.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        paramISccmsApiGetCatchVideoRecordV2TaskListener.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
      }
    });
    localSccmsApiGetCatchVideoRecordV2Task.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetCatchVideoRecordV2Task);
  }

  public int APIGetChannelListV2(String paramString1, String paramString2, SccmsApiGetChannelListV2Task.ISccmsApiGetChannelListV2TaskListener paramISccmsApiGetChannelListV2TaskListener)
  {
    return APIGetChannelListV2(paramString1, paramString2, paramISccmsApiGetChannelListV2TaskListener, false, null).getTaskId();
  }

  public ServerApiTask APIGetChannelListV2(String paramString1, String paramString2, final SccmsApiGetChannelListV2Task.ISccmsApiGetChannelListV2TaskListener paramISccmsApiGetChannelListV2TaskListener, boolean paramBoolean, Long paramLong)
  {
    SccmsApiGetChannelListV2Task localSccmsApiGetChannelListV2Task = new SccmsApiGetChannelListV2Task(paramString1, paramString2);
    localSccmsApiGetChannelListV2Task.setListener(new SccmsApiGetChannelListV2Task.ISccmsApiGetChannelListV2TaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.47.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ChannelInfoV2 paramAnonymousChannelInfoV2)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.47.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousChannelInfoV2);
          }
        });
      }
    });
    localSccmsApiGetChannelListV2Task.setForceUpdate(paramBoolean);
    if (paramLong != null)
      localSccmsApiGetChannelListV2Task.setCacheLife(paramLong.longValue());
    localSccmsApiGetChannelListV2Task.setIsUiSafe(false);
    this.httpApiEngine.addTask(localSccmsApiGetChannelListV2Task);
    return localSccmsApiGetChannelListV2Task;
  }

  public int APIGetCityInfo(String paramString, final SccmsApiGetCityInfoTask.ISccmsApiGetCityListTaskListener paramISccmsApiGetCityListTaskListener)
  {
    SccmsApiGetCityInfoTask localSccmsApiGetCityInfoTask = new SccmsApiGetCityInfoTask(paramString);
    localSccmsApiGetCityInfoTask.setListener(new SccmsApiGetCityInfoTask.ISccmsApiGetCityListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.87.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final CityInfoById paramAnonymousCityInfoById)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.87.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousCityInfoById);
          }
        });
      }
    });
    localSccmsApiGetCityInfoTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetCityInfoTask);
  }

  public int APIGetCityList(String paramString, final SccmsApiGetCityListTask.ISccmsApiGetCityListTaskListener paramISccmsApiGetCityListTaskListener)
  {
    SccmsApiGetCityListTask localSccmsApiGetCityListTask = new SccmsApiGetCityListTask(paramString);
    localSccmsApiGetCityListTask.setListener(new SccmsApiGetCityListTask.ISccmsApiGetCityListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.86.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final CityStruct paramAnonymousCityStruct)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.86.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousCityStruct);
          }
        });
      }
    });
    localSccmsApiGetCityListTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetCityListTask);
  }

  public int APIGetCollectRecord(final SccmsApiGetCollectRecordTask.ISccmsApiGetCollectRecordTaskListener paramISccmsApiGetCollectRecordTaskListener)
  {
    SccmsApiGetCollectRecordTask localSccmsApiGetCollectRecordTask = new SccmsApiGetCollectRecordTask();
    localSccmsApiGetCollectRecordTask.setListener(new SccmsApiGetCollectRecordTask.ISccmsApiGetCollectRecordTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.67.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.67.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiGetCollectRecordTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetCollectRecordTask);
  }

  public int APIGetCollectRecordV2(final SccmsApiGetCollectRecordV2Task.ISccmsApiGetCollectRecordV2TaskListener paramISccmsApiGetCollectRecordV2TaskListener)
  {
    SccmsApiGetCollectRecordV2Task localSccmsApiGetCollectRecordV2Task = new SccmsApiGetCollectRecordV2Task();
    localSccmsApiGetCollectRecordV2Task.setListener(new SccmsApiGetCollectRecordV2Task.ISccmsApiGetCollectRecordV2TaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.78.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.78.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiGetCollectRecordV2Task.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetCollectRecordV2Task);
  }

  public int APIGetCollectRecordV2(final SccmsApiGetCollectRecordV2Task.ISccmsApiGetCollectRecordV2TaskListener paramISccmsApiGetCollectRecordV2TaskListener, boolean paramBoolean)
  {
    if (paramBoolean)
      return APIGetCollectRecordV2(paramISccmsApiGetCollectRecordV2TaskListener);
    SccmsApiGetCollectRecordV2Task localSccmsApiGetCollectRecordV2Task = new SccmsApiGetCollectRecordV2Task();
    localSccmsApiGetCollectRecordV2Task.setListener(new SccmsApiGetCollectRecordV2Task.ISccmsApiGetCollectRecordV2TaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        paramISccmsApiGetCollectRecordV2TaskListener.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        paramISccmsApiGetCollectRecordV2TaskListener.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
      }
    });
    localSccmsApiGetCollectRecordV2Task.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetCollectRecordV2Task);
  }

  public int APIGetCommonVideoId(String paramString1, String paramString2, String paramString3, String paramString4, final SccmsApiGetCommonVideoIdTask.ISccmsApiGetCommonVideoIdTaskListener paramISccmsApiGetCommonVideoIdTaskListener)
  {
    SccmsApiGetCommonVideoIdTask localSccmsApiGetCommonVideoIdTask = new SccmsApiGetCommonVideoIdTask(paramString1, paramString2, paramString3, paramString4);
    localSccmsApiGetCommonVideoIdTask.setListener(new SccmsApiGetCommonVideoIdTask.ISccmsApiGetCommonVideoIdTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.91.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final CommonVideoIDInfo paramAnonymousCommonVideoIDInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.91.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousCommonVideoIDInfo);
          }
        });
      }
    });
    localSccmsApiGetCommonVideoIdTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetCommonVideoIdTask);
  }

  public int APIGetConPlayMediaTask(String paramString1, String paramString2, String[] paramArrayOfString, final SccmsApiGetConPlayMediaTask.IGetConPlayMediaTaskListener paramIGetConPlayMediaTaskListener)
  {
    SccmsApiGetConPlayMediaTask localSccmsApiGetConPlayMediaTask = new SccmsApiGetConPlayMediaTask(paramString1, paramString2, paramArrayOfString);
    localSccmsApiGetConPlayMediaTask.setListener(new SccmsApiGetConPlayMediaTask.IGetConPlayMediaTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.138.this.val$listener.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ConPlayMedia paramAnonymousConPlayMedia)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.138.this.val$listener.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousConPlayMedia);
          }
        });
      }
    });
    return this.httpApiEngine.addTask(localSccmsApiGetConPlayMediaTask);
  }

  public int APIGetEpgIndex(final SccmsApiGetEpgIndexTask.ISccmsApiGetEpgDataTaskListener paramISccmsApiGetEpgDataTaskListener)
  {
    SccmsApiGetEpgIndexTask localSccmsApiGetEpgIndexTask = new SccmsApiGetEpgIndexTask();
    localSccmsApiGetEpgIndexTask.setListener(new SccmsApiGetEpgIndexTask.ISccmsApiGetEpgDataTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        paramISccmsApiGetEpgDataTaskListener.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, Integer paramAnonymousInteger)
      {
        paramISccmsApiGetEpgDataTaskListener.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousInteger);
      }
    });
    localSccmsApiGetEpgIndexTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetEpgIndexTask);
  }

  public int APIGetFAQList(final SccmsApiGetFAQListTask.ISccmsApiGetFAQListTaskListener paramISccmsApiGetFAQListTaskListener)
  {
    SccmsApiGetFAQListTask localSccmsApiGetFAQListTask = new SccmsApiGetFAQListTask();
    localSccmsApiGetFAQListTask.setListener(new SccmsApiGetFAQListTask.ISccmsApiGetFAQListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.85.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<FAQStruct> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.85.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiGetFAQListTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetFAQListTask);
  }

  public int APIGetFilmListByLabel(String paramString1, int paramInt1, int paramInt2, int paramInt3, String paramString2, final SccmsApiGetFilmListByLabelTask.ISccmsApiGetFilmListByLabelTaskListener paramISccmsApiGetFilmListByLabelTaskListener)
  {
    SccmsApiGetFilmListByLabelTask localSccmsApiGetFilmListByLabelTask = new SccmsApiGetFilmListByLabelTask(paramString1, paramInt1, paramInt2, paramInt3, paramString2);
    localSccmsApiGetFilmListByLabelTask.setListener(new SccmsApiGetFilmListByLabelTask.ISccmsApiGetFilmListByLabelTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.6.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final FilmItem paramAnonymousFilmItem)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.6.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousFilmItem);
          }
        });
      }
    });
    localSccmsApiGetFilmListByLabelTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetFilmListByLabelTask);
  }

  public int APIGetFilterLabelByType(String paramString1, String paramString2, int paramInt1, int paramInt2, final SccmsApiGetFilterLabelByTypeTask.ISccmsApiGetFilterLabelByTypeTask paramISccmsApiGetFilterLabelByTypeTask)
  {
    SccmsApiGetFilterLabelByTypeTask localSccmsApiGetFilterLabelByTypeTask = new SccmsApiGetFilterLabelByTypeTask(paramString1, paramString2, paramInt1, paramInt2);
    localSccmsApiGetFilterLabelByTypeTask.setListener(new SccmsApiGetFilterLabelByTypeTask.ISccmsApiGetFilterLabelByTypeTask()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.21.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<LabelSortItem> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.21.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiGetFilterLabelByTypeTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetFilterLabelByTypeTask);
  }

  public ServerApiTask APIGetGUID(SccmsApiGetGUIDTask.ISccmsApiGetGUIDDataTaskListener paramISccmsApiGetGUIDDataTaskListener)
  {
    SccmsApiGetGUIDTask localSccmsApiGetGUIDTask = new SccmsApiGetGUIDTask();
    localSccmsApiGetGUIDTask.setListener(paramISccmsApiGetGUIDDataTaskListener);
    this.httpApiEngine.addTask(localSccmsApiGetGUIDTask);
    return localSccmsApiGetGUIDTask;
  }

  public int APIGetHotActorStarListTask(final SccmsApiGetHotActorStarListTask.ISccmsApiGetHotActorStarListTaskListener paramISccmsApiGetHotActorStarListTaskListener)
  {
    SccmsApiGetHotActorStarListTask localSccmsApiGetHotActorStarListTask = new SccmsApiGetHotActorStarListTask();
    localSccmsApiGetHotActorStarListTask.setListener(new SccmsApiGetHotActorStarListTask.ISccmsApiGetHotActorStarListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            if (ServerApiManager.150.this.val$lsr != null)
              ServerApiManager.150.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final SearchActorStarList paramAnonymousSearchActorStarList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            if (ServerApiManager.150.this.val$lsr != null)
              ServerApiManager.150.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousSearchActorStarList);
          }
        });
      }
    });
    localSccmsApiGetHotActorStarListTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetHotActorStarListTask);
  }

  public int APIGetHotAppList(int paramInt, final SccmsApiGetHotAppListTask.ISccmsApiGetHotAppListTaskListener paramISccmsApiGetHotAppListTaskListener)
  {
    SccmsApiGetHotAppListTask localSccmsApiGetHotAppListTask = new SccmsApiGetHotAppListTask(paramInt);
    localSccmsApiGetHotAppListTask.setListener(new SccmsApiGetHotAppListTask.ISccmsApiGetHotAppListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.101.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final HotAppList paramAnonymousHotAppList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.101.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousHotAppList);
          }
        });
      }
    });
    localSccmsApiGetHotAppListTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetHotAppListTask);
  }

  public int APIGetHotWords(int paramInt1, int paramInt2, final SccmsApiGetHotWordsTask.ISccmsApiGetHotWordsTaskListener paramISccmsApiGetHotWordsTaskListener)
  {
    SccmsApiGetHotWordsTask localSccmsApiGetHotWordsTask = new SccmsApiGetHotWordsTask(paramInt1, paramInt2);
    localSccmsApiGetHotWordsTask.setListener(new SccmsApiGetHotWordsTask.ISccmsApiGetHotWordsTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.7.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final HotWordList paramAnonymousHotWordList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.7.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousHotWordList);
          }
        });
      }
    });
    localSccmsApiGetHotWordsTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetHotWordsTask);
  }

  public int APIGetIndexListByCategory(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, final SccmsApiGetIndexListByCategoryTask.ISccmsApiGetIndexListByCategoryListener paramISccmsApiGetIndexListByCategoryListener)
  {
    SccmsApiGetIndexListByCategoryTask localSccmsApiGetIndexListByCategoryTask = new SccmsApiGetIndexListByCategoryTask(paramString1, paramString2, paramString3, paramInt1, paramInt2);
    localSccmsApiGetIndexListByCategoryTask.setListener(new SccmsApiGetIndexListByCategoryTask.ISccmsApiGetIndexListByCategoryListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.104.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final CategoryIndexList paramAnonymousCategoryIndexList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.104.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousCategoryIndexList);
          }
        });
      }
    });
    localSccmsApiGetIndexListByCategoryTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetIndexListByCategoryTask);
  }

  public int APIGetInitMetaData(final SccmsApiGetInitMetaDataTask.ISccmsApiGetInitMetaDataTaskListener paramISccmsApiGetInitMetaDataTaskListener)
  {
    SccmsApiGetInitMetaDataTask localSccmsApiGetInitMetaDataTask = new SccmsApiGetInitMetaDataTask();
    localSccmsApiGetInitMetaDataTask.setListener(new SccmsApiGetInitMetaDataTask.ISccmsApiGetInitMetaDataTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        paramISccmsApiGetInitMetaDataTaskListener.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, byte[] paramAnonymousArrayOfByte)
      {
        paramISccmsApiGetInitMetaDataTaskListener.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayOfByte);
      }
    });
    localSccmsApiGetInitMetaDataTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetInitMetaDataTask);
  }

  public int APIGetLabelStarListTask(final SccmsApiGetLabelStarListTask.ISccmsApiGetLabelStarListTaskListener paramISccmsApiGetLabelStarListTaskListener)
  {
    SccmsApiGetLabelStarListTask localSccmsApiGetLabelStarListTask = new SccmsApiGetLabelStarListTask();
    localSccmsApiGetLabelStarListTask.setListener(new SccmsApiGetLabelStarListTask.ISccmsApiGetLabelStarListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            if (ServerApiManager.148.this.val$lsr != null)
              ServerApiManager.148.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final LabelStarList paramAnonymousLabelStarList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            if (ServerApiManager.148.this.val$lsr != null)
              ServerApiManager.148.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousLabelStarList);
          }
        });
      }
    });
    localSccmsApiGetLabelStarListTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetLabelStarListTask);
  }

  public int APIGetMediaAssetsBindLabelType(String paramString, SccmsApiGetMediaAssetsBindLabelTask.ISccmsApiGetMediaAssetsBindLabelTaskListener paramISccmsApiGetMediaAssetsBindLabelTaskListener)
  {
    return APIGetMediaAssetsBindLabelType(paramString, paramISccmsApiGetMediaAssetsBindLabelTaskListener, false, null).getTaskId();
  }

  public ServerApiTask APIGetMediaAssetsBindLabelType(String paramString, final SccmsApiGetMediaAssetsBindLabelTask.ISccmsApiGetMediaAssetsBindLabelTaskListener paramISccmsApiGetMediaAssetsBindLabelTaskListener, boolean paramBoolean, Long paramLong)
  {
    SccmsApiGetMediaAssetsBindLabelTask localSccmsApiGetMediaAssetsBindLabelTask = new SccmsApiGetMediaAssetsBindLabelTask(paramString, new SccmsApiGetMediaAssetsBindLabelTask.ISccmsApiGetMediaAssetsBindLabelTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.158.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final byte[] paramAnonymousArrayOfByte)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.158.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayOfByte);
          }
        });
      }
    });
    localSccmsApiGetMediaAssetsBindLabelTask.setForceUpdate(paramBoolean);
    if (paramLong != null)
      localSccmsApiGetMediaAssetsBindLabelTask.setCacheLife(paramLong.longValue());
    localSccmsApiGetMediaAssetsBindLabelTask.setIsUiSafe(false);
    this.httpApiEngine.addTask(localSccmsApiGetMediaAssetsBindLabelTask);
    return localSccmsApiGetMediaAssetsBindLabelTask;
  }

  public int APIGetMediaAssetsInfo(String paramString, SccmsApiGetMediaAssetsInfoTask.ISccmsApiGetMediaAssetsInfoTaskListener paramISccmsApiGetMediaAssetsInfoTaskListener)
  {
    return APIGetMediaAssetsInfo(paramString, paramISccmsApiGetMediaAssetsInfoTaskListener, false, null).getTaskId();
  }

  public ServerApiTask APIGetMediaAssetsInfo(String paramString, final SccmsApiGetMediaAssetsInfoTask.ISccmsApiGetMediaAssetsInfoTaskListener paramISccmsApiGetMediaAssetsInfoTaskListener, boolean paramBoolean, Long paramLong)
  {
    SccmsApiGetMediaAssetsInfoTask localSccmsApiGetMediaAssetsInfoTask = new SccmsApiGetMediaAssetsInfoTask(paramString);
    localSccmsApiGetMediaAssetsInfoTask.setListener(new SccmsApiGetMediaAssetsInfoTask.ISccmsApiGetMediaAssetsInfoTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.15.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final MediaAssetsInfo paramAnonymousMediaAssetsInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.15.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousMediaAssetsInfo);
          }
        });
      }
    });
    localSccmsApiGetMediaAssetsInfoTask.setForceUpdate(paramBoolean);
    if (paramLong != null)
      localSccmsApiGetMediaAssetsInfoTask.setCacheLife(paramLong.longValue());
    localSccmsApiGetMediaAssetsInfoTask.setIsUiSafe(false);
    this.httpApiEngine.addTask(localSccmsApiGetMediaAssetsInfoTask);
    return localSccmsApiGetMediaAssetsInfoTask;
  }

  public int APIGetMessageBoardData(final MgtvApiGetMessageBoardDataTask.IMgtvApiGetMessageBoardDataTaskListener paramIMgtvApiGetMessageBoardDataTaskListener)
  {
    MgtvApiGetMessageBoardDataTask localMgtvApiGetMessageBoardDataTask = new MgtvApiGetMessageBoardDataTask();
    localMgtvApiGetMessageBoardDataTask.setListener(new MgtvApiGetMessageBoardDataTask.IMgtvApiGetMessageBoardDataTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.26.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final MessageBoardData paramAnonymousMessageBoardData)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.26.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousMessageBoardData);
          }
        });
      }
    });
    localMgtvApiGetMessageBoardDataTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localMgtvApiGetMessageBoardDataTask);
  }

  public int APIGetMsgBoardSendRespond(String paramString, final MgtvApiGetMsgBoardSendRespondTask.IMgtvApiGetMsgBoardSendRespondTaskListener paramIMgtvApiGetMsgBoardSendRespondTaskListener)
  {
    MgtvApiGetMsgBoardSendRespondTask localMgtvApiGetMsgBoardSendRespondTask = new MgtvApiGetMsgBoardSendRespondTask(paramString);
    localMgtvApiGetMsgBoardSendRespondTask.setListener(new MgtvApiGetMsgBoardSendRespondTask.IMgtvApiGetMsgBoardSendRespondTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.27.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final MessageBoardSendRepond paramAnonymousMessageBoardSendRepond)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.27.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousMessageBoardSendRepond);
          }
        });
      }
    });
    localMgtvApiGetMsgBoardSendRespondTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localMgtvApiGetMsgBoardSendRespondTask);
  }

  public int APIGetNewDetailedDataByVideoId(String paramString, final SccmsAPIGetNewDetailedDataByVideoIdTask.IGetNewDetailedDataByVideoIdListener paramIGetNewDetailedDataByVideoIdListener)
  {
    SccmsAPIGetNewDetailedDataByVideoIdTask localSccmsAPIGetNewDetailedDataByVideoIdTask = new SccmsAPIGetNewDetailedDataByVideoIdTask(paramString);
    localSccmsAPIGetNewDetailedDataByVideoIdTask.setListener(new SccmsAPIGetNewDetailedDataByVideoIdTask.IGetNewDetailedDataByVideoIdListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.52.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final NewDetailedFilmData paramAnonymousNewDetailedFilmData)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.52.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousNewDetailedFilmData);
          }
        });
      }
    });
    localSccmsAPIGetNewDetailedDataByVideoIdTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsAPIGetNewDetailedDataByVideoIdTask);
  }

  public int APIGetPlayRecord(final SccmsApiGetPlayRecordTask.ISccmsApiGetPlayRecordTaskListener paramISccmsApiGetPlayRecordTaskListener)
  {
    SccmsApiGetPlayRecordTask localSccmsApiGetPlayRecordTask = new SccmsApiGetPlayRecordTask();
    localSccmsApiGetPlayRecordTask.setListener(new SccmsApiGetPlayRecordTask.ISccmsApiGetPlayRecordTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.66.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.66.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiGetPlayRecordTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetPlayRecordTask);
  }

  public int APIGetPlayRecordV2(final SccmsApiGetPlayRecordV2Task.ISccmsApiGetPlayRecordV2TaskListener paramISccmsApiGetPlayRecordV2TaskListener)
  {
    SccmsApiGetPlayRecordV2Task localSccmsApiGetPlayRecordV2Task = new SccmsApiGetPlayRecordV2Task();
    localSccmsApiGetPlayRecordV2Task.setListener(new SccmsApiGetPlayRecordV2Task.ISccmsApiGetPlayRecordV2TaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.76.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.76.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiGetPlayRecordV2Task.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetPlayRecordV2Task);
  }

  public int APIGetPlayRecordV2(final SccmsApiGetPlayRecordV2Task.ISccmsApiGetPlayRecordV2TaskListener paramISccmsApiGetPlayRecordV2TaskListener, boolean paramBoolean)
  {
    if (paramBoolean)
      return APIGetPlayRecordV2(paramISccmsApiGetPlayRecordV2TaskListener);
    SccmsApiGetPlayRecordV2Task localSccmsApiGetPlayRecordV2Task = new SccmsApiGetPlayRecordV2Task();
    localSccmsApiGetPlayRecordV2Task.setListener(new SccmsApiGetPlayRecordV2Task.ISccmsApiGetPlayRecordV2TaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        paramISccmsApiGetPlayRecordV2TaskListener.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        paramISccmsApiGetPlayRecordV2TaskListener.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
      }
    });
    localSccmsApiGetPlayRecordV2Task.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetPlayRecordV2Task);
  }

  public int APIGetPlaybill(String paramString, int paramInt1, int paramInt2, SccmsApiGetPlaybillTask.ISccmsApiGetPlaybillTaskListener paramISccmsApiGetPlaybillTaskListener)
  {
    return APIGetPlaybill(paramString, paramInt1, paramInt2, null, paramISccmsApiGetPlaybillTaskListener);
  }

  public int APIGetPlaybill(String paramString, int paramInt1, int paramInt2, Long paramLong, final SccmsApiGetPlaybillTask.ISccmsApiGetPlaybillTaskListener paramISccmsApiGetPlaybillTaskListener)
  {
    SccmsApiGetPlaybillTask localSccmsApiGetPlaybillTask = new SccmsApiGetPlaybillTask(paramString, paramInt1, paramInt2);
    localSccmsApiGetPlaybillTask.setListener(new SccmsApiGetPlaybillTask.ISccmsApiGetPlaybillTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.53.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<PlayBillStruct> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.53.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    if (paramLong != null)
      localSccmsApiGetPlaybillTask.setCacheLife(paramLong.longValue());
    localSccmsApiGetPlaybillTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetPlaybillTask);
  }

  public int APIGetPlaybillSelectedList(String paramString1, String paramString2, int paramInt1, int paramInt2, final SccmsApiGetPlaybillSelectedList.ISccmsApiGetPlaybillSelectedListTaskListener paramISccmsApiGetPlaybillSelectedListTaskListener)
  {
    SccmsApiGetPlaybillSelectedList localSccmsApiGetPlaybillSelectedList = new SccmsApiGetPlaybillSelectedList(paramString1, paramString2, paramInt1, paramInt2, new SccmsApiGetPlaybillSelectedList.ISccmsApiGetPlaybillSelectedListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.153.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final GetPlaybillSelectedListInfo paramAnonymousGetPlaybillSelectedListInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.153.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousGetPlaybillSelectedListInfo);
          }
        });
      }
    });
    localSccmsApiGetPlaybillSelectedList.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetPlaybillSelectedList);
  }

  public int APIGetPlaybillSelectedList(String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, String paramString4, String paramString5, final SccmsApiGetPlaybillSelectedList.ISccmsApiGetPlaybillSelectedListTaskListener paramISccmsApiGetPlaybillSelectedListTaskListener)
  {
    SccmsApiGetPlaybillSelectedList localSccmsApiGetPlaybillSelectedList = new SccmsApiGetPlaybillSelectedList(paramString1, paramString2, paramInt1, paramInt2, paramString3, paramString4, paramString5, new SccmsApiGetPlaybillSelectedList.ISccmsApiGetPlaybillSelectedListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.156.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final GetPlaybillSelectedListInfo paramAnonymousGetPlaybillSelectedListInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.156.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousGetPlaybillSelectedListInfo);
          }
        });
      }
    });
    localSccmsApiGetPlaybillSelectedList.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetPlaybillSelectedList);
  }

  public int APIGetPreInstallList(final SccmsApiGetPreInstallListTask.ISccmsApiGetPreInstallListTaskListener paramISccmsApiGetPreInstallListTaskListener)
  {
    SccmsApiGetPreInstallListTask localSccmsApiGetPreInstallListTask = new SccmsApiGetPreInstallListTask();
    localSccmsApiGetPreInstallListTask.setListener(new SccmsApiGetPreInstallListTask.ISccmsApiGetPreInstallListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.102.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final PreInstallList paramAnonymousPreInstallList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.102.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousPreInstallList);
          }
        });
      }
    });
    localSccmsApiGetPreInstallListTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetPreInstallListTask);
  }

  public int APIGetPreviewList(SccmsApiGetPreviewListTask.ISccmsApiGetPreviewListTaskListener paramISccmsApiGetPreviewListTaskListener, String paramString, int paramInt1, int paramInt2)
  {
    SccmsApiGetPreviewListTask localSccmsApiGetPreviewListTask = new SccmsApiGetPreviewListTask(paramISccmsApiGetPreviewListTaskListener, paramString, paramInt1, paramInt2);
    return this.httpApiEngine.addTask(localSccmsApiGetPreviewListTask);
  }

  public int APIGetPublicImageTask(String paramString, final SccmsApiGetPublicImageTask.ISccmsApiGetPublicImageTaskListener paramISccmsApiGetPublicImageTaskListener)
  {
    SccmsApiGetPublicImageTask localSccmsApiGetPublicImageTask = new SccmsApiGetPublicImageTask(paramString);
    localSccmsApiGetPublicImageTask.setListener(new SccmsApiGetPublicImageTask.ISccmsApiGetPublicImageTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.139.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final List<PublicImage> paramAnonymousList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.139.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousList);
          }
        });
      }
    });
    localSccmsApiGetPublicImageTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetPublicImageTask);
  }

  public int APIGetQuitVideoIndexs(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, final SccmsAPIGetQuitVideoIndexsTask.ISccmsAPIGetQuitVideoIndexsTaskListener paramISccmsAPIGetQuitVideoIndexsTaskListener)
  {
    SccmsAPIGetQuitVideoIndexsTask localSccmsAPIGetQuitVideoIndexsTask = new SccmsAPIGetQuitVideoIndexsTask(paramString1, paramString2, paramString3, paramInt1, paramInt2);
    localSccmsAPIGetQuitVideoIndexsTask.setListener(new SccmsAPIGetQuitVideoIndexsTask.ISccmsAPIGetQuitVideoIndexsTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.161.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final QuitVideoIndexsParams paramAnonymousQuitVideoIndexsParams)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.161.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousQuitVideoIndexsParams);
          }
        });
      }
    });
    localSccmsAPIGetQuitVideoIndexsTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsAPIGetQuitVideoIndexsTask);
  }

  public int APIGetRelevantFilms(String paramString1, int paramInt1, String paramString2, String paramString3, int paramInt2, int paramInt3, final SccmsApiGetRelevantFilmsTask.ISccmsApiGetRelevantFilmsTaskListener paramISccmsApiGetRelevantFilmsTaskListener)
  {
    SccmsApiGetRelevantFilmsTask localSccmsApiGetRelevantFilmsTask = new SccmsApiGetRelevantFilmsTask(paramString1, paramInt1, paramString2, paramString3, paramInt2, paramInt3);
    localSccmsApiGetRelevantFilmsTask.setListener(new SccmsApiGetRelevantFilmsTask.ISccmsApiGetRelevantFilmsTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.1.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final FilmItem paramAnonymousFilmItem)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.1.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousFilmItem);
          }
        });
      }
    });
    localSccmsApiGetRelevantFilmsTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetRelevantFilmsTask);
  }

  public int APIGetReplayRecommendData(int paramInt1, int paramInt2, SccmsApiGetReplayRecommendDataTask.ISccmsApiGetReplayRecommendDataTaskListener paramISccmsApiGetReplayRecommendDataTaskListener)
  {
    return APIGetReplayRecommendData(paramInt1, paramInt2, paramISccmsApiGetReplayRecommendDataTaskListener, false, null).getTaskId();
  }

  public ServerApiTask APIGetReplayRecommendData(int paramInt1, int paramInt2, final SccmsApiGetReplayRecommendDataTask.ISccmsApiGetReplayRecommendDataTaskListener paramISccmsApiGetReplayRecommendDataTaskListener, boolean paramBoolean, Long paramLong)
  {
    SccmsApiGetReplayRecommendDataTask localSccmsApiGetReplayRecommendDataTask = new SccmsApiGetReplayRecommendDataTask(paramInt1, paramInt2);
    localSccmsApiGetReplayRecommendDataTask.setListener(new SccmsApiGetReplayRecommendDataTask.ISccmsApiGetReplayRecommendDataTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.41.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final List<PlayBillRecommendStrut> paramAnonymousList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.41.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousList);
          }
        });
      }
    });
    localSccmsApiGetReplayRecommendDataTask.setForceUpdate(paramBoolean);
    if (paramLong != null)
      localSccmsApiGetReplayRecommendDataTask.setCacheLife(paramLong.longValue());
    localSccmsApiGetReplayRecommendDataTask.setIsUiSafe(false);
    this.httpApiEngine.addTask(localSccmsApiGetReplayRecommendDataTask);
    return localSccmsApiGetReplayRecommendDataTask;
  }

  public int APIGetSecretKeysTask(final SccmsApiGetSecretKeysTask.ISccmsApiGetSecretKeysTaskListener paramISccmsApiGetSecretKeysTaskListener)
  {
    SccmsApiGetSecretKeysTask localSccmsApiGetSecretKeysTask = new SccmsApiGetSecretKeysTask();
    localSccmsApiGetSecretKeysTask.setListener(new SccmsApiGetSecretKeysTask.ISccmsApiGetSecretKeysTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        paramISccmsApiGetSecretKeysTaskListener.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, GetSecretKeysInfo paramAnonymousGetSecretKeysInfo)
      {
        paramISccmsApiGetSecretKeysTaskListener.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousGetSecretKeysInfo);
      }
    });
    localSccmsApiGetSecretKeysTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetSecretKeysTask);
  }

  public int APIGetSkins(final SccmsApiGetSkinTask.ISccmsApiGetSkinTaskListener paramISccmsApiGetSkinTaskListener)
  {
    SccmsApiGetSkinTask localSccmsApiGetSkinTask = new SccmsApiGetSkinTask();
    localSccmsApiGetSkinTask.setListener(new SccmsApiGetSkinTask.ISccmsApiGetSkinTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.40.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final List<Skin> paramAnonymousList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.40.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousList);
          }
        });
      }
    });
    localSccmsApiGetSkinTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetSkinTask);
  }

  public int APIGetSpecialInfoByIds(IApiTaskListener paramIApiTaskListener, String[] paramArrayOfString)
  {
    ApiTaskGetSpecialInfoByIds localApiTaskGetSpecialInfoByIds = new ApiTaskGetSpecialInfoByIds(paramArrayOfString);
    localApiTaskGetSpecialInfoByIds.setListener(paramIApiTaskListener);
    return this.httpApiEngine.addTask(localApiTaskGetSpecialInfoByIds);
  }

  public int APIGetSpecialTopicColumnTreeData(String paramString, final SccmsApiGetSpecialTopicColumnTreeTask.ISccmsApiGetSpecialTopicColumnTreeTaskListener paramISccmsApiGetSpecialTopicColumnTreeTaskListener)
  {
    SccmsApiGetSpecialTopicColumnTreeTask localSccmsApiGetSpecialTopicColumnTreeTask = new SccmsApiGetSpecialTopicColumnTreeTask(paramString);
    localSccmsApiGetSpecialTopicColumnTreeTask.setListener(new SccmsApiGetSpecialTopicColumnTreeTask.ISccmsApiGetSpecialTopicColumnTreeTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.32.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final SpecialTopicTreeInfo paramAnonymousSpecialTopicTreeInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.32.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousSpecialTopicTreeInfo);
          }
        });
      }
    });
    localSccmsApiGetSpecialTopicColumnTreeTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetSpecialTopicColumnTreeTask);
  }

  public int APIGetSpecialTopicPkgContent(String paramString, SccmsApiGetSpecialTopicPkgContentLstTask.ISccmsApiGetSearchSpecialTopicPkgLstTaskListener paramISccmsApiGetSearchSpecialTopicPkgLstTaskListener)
  {
    return APIGetSpecialTopicPkgContentLstData(paramString, "0", "100", "0", "", paramISccmsApiGetSearchSpecialTopicPkgLstTaskListener);
  }

  public int APIGetSpecialTopicPkgContentLstData(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, final SccmsApiGetSpecialTopicPkgContentLstTask.ISccmsApiGetSearchSpecialTopicPkgLstTaskListener paramISccmsApiGetSearchSpecialTopicPkgLstTaskListener)
  {
    SccmsApiGetSpecialTopicPkgContentLstTask localSccmsApiGetSpecialTopicPkgContentLstTask = new SccmsApiGetSpecialTopicPkgContentLstTask(paramString1, paramString2, paramString3, paramString4, paramString5);
    localSccmsApiGetSpecialTopicPkgContentLstTask.setListener(new SccmsApiGetSpecialTopicPkgContentLstTask.ISccmsApiGetSearchSpecialTopicPkgLstTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.33.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final SpecialTopicPkgCntLstInfo paramAnonymousSpecialTopicPkgCntLstInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.33.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousSpecialTopicPkgCntLstInfo);
          }
        });
      }
    });
    localSccmsApiGetSpecialTopicPkgContentLstTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetSpecialTopicPkgContentLstTask);
  }

  public int APIGetSpecialTopicPutData(String paramString1, String paramString2, final SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener paramISccmsApiGetSpecialTopicPutTaskListener)
  {
    SccmsApiGetSpecialTopicPutTask localSccmsApiGetSpecialTopicPutTask = new SccmsApiGetSpecialTopicPutTask(paramString1, paramString2);
    localSccmsApiGetSpecialTopicPutTask.setListener(new SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.29.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<SpecialTopicPutInfo> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.29.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiGetSpecialTopicPutTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetSpecialTopicPutTask);
  }

  public int APIGetSpecialTopicPutData(String paramString1, String paramString2, String paramString3, final SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener paramISccmsApiGetSpecialTopicPutTaskListener)
  {
    SccmsApiGetSpecialTopicPutTask localSccmsApiGetSpecialTopicPutTask = new SccmsApiGetSpecialTopicPutTask(paramString1, paramString2, paramString3);
    localSccmsApiGetSpecialTopicPutTask.setListener(new SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.30.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<SpecialTopicPutInfo> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.30.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiGetSpecialTopicPutTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetSpecialTopicPutTask);
  }

  public int APIGetSpecialTopicPutData(String paramString1, String paramString2, String paramString3, String paramString4, SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener paramISccmsApiGetSpecialTopicPutTaskListener)
  {
    return APIGetSpecialTopicPutData(paramString1, paramString2, paramString3, paramString4, paramISccmsApiGetSpecialTopicPutTaskListener, false, null).getTaskId();
  }

  public ServerApiTask APIGetSpecialTopicPutData(String paramString1, String paramString2, String paramString3, String paramString4, final SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener paramISccmsApiGetSpecialTopicPutTaskListener, boolean paramBoolean, Long paramLong)
  {
    SccmsApiGetSpecialTopicPutTask localSccmsApiGetSpecialTopicPutTask = new SccmsApiGetSpecialTopicPutTask(paramString1, paramString2, paramString3, paramString4);
    localSccmsApiGetSpecialTopicPutTask.setListener(new SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.31.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<SpecialTopicPutInfo> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.31.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiGetSpecialTopicPutTask.setForceUpdate(paramBoolean);
    if (paramLong != null)
      localSccmsApiGetSpecialTopicPutTask.setCacheLife(paramLong.longValue());
    localSccmsApiGetSpecialTopicPutTask.setIsUiSafe(false);
    this.httpApiEngine.addTask(localSccmsApiGetSpecialTopicPutTask);
    return localSccmsApiGetSpecialTopicPutTask;
  }

  public int APIGetSpeedTestMissionInfo(final SccmsApiGetSpeedTestMissionInfoTask.ISccmsApiGetSpeedTestMissionInfoTaskListener paramISccmsApiGetSpeedTestMissionInfoTaskListener)
  {
    SccmsApiGetSpeedTestMissionInfoTask localSccmsApiGetSpeedTestMissionInfoTask = new SccmsApiGetSpeedTestMissionInfoTask();
    localSccmsApiGetSpeedTestMissionInfoTask.setListener(new SccmsApiGetSpeedTestMissionInfoTask.ISccmsApiGetSpeedTestMissionInfoTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.34.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<SpeedStruct> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.34.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiGetSpeedTestMissionInfoTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetSpeedTestMissionInfoTask);
  }

  public int APIGetStarCollectData(String paramString1, String paramString2, final SccmsApiGetStarCollectDataTask.ISccmsApiGetStarCollectDataTaskListener paramISccmsApiGetStarCollectDataTaskListener)
  {
    SccmsApiGetStarCollectDataTask localSccmsApiGetStarCollectDataTask = new SccmsApiGetStarCollectDataTask(paramString1, paramString2);
    localSccmsApiGetStarCollectDataTask.setListener(new SccmsApiGetStarCollectDataTask.ISccmsApiGetStarCollectDataTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.50.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final StarInfoCollect paramAnonymousStarInfoCollect)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.50.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousStarInfoCollect);
          }
        });
      }
    });
    localSccmsApiGetStarCollectDataTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetStarCollectDataTask);
  }

  public int APIGetStarCollectData(List<String> paramList, final SccmsApiGetStarCollectDataTask.ISccmsApiGetStarCollectDataTaskListener paramISccmsApiGetStarCollectDataTaskListener)
  {
    SccmsApiGetStarCollectDataTask localSccmsApiGetStarCollectDataTask = new SccmsApiGetStarCollectDataTask(paramList);
    localSccmsApiGetStarCollectDataTask.setListener(new SccmsApiGetStarCollectDataTask.ISccmsApiGetStarCollectDataTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.51.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final StarInfoCollect paramAnonymousStarInfoCollect)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.51.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousStarInfoCollect);
          }
        });
      }
    });
    localSccmsApiGetStarCollectDataTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetStarCollectDataTask);
  }

  public int APIGetStarGuestListByLabelTask(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, String paramString4, final SccmsApiGetStarGuestListByLabelTask.ISccmsApiGetStarGusetListByLabelTaskListener paramISccmsApiGetStarGusetListByLabelTaskListener)
  {
    SccmsApiGetStarGuestListByLabelTask localSccmsApiGetStarGuestListByLabelTask = new SccmsApiGetStarGuestListByLabelTask(paramString1, paramString2, paramString3, paramInt1, paramInt2, paramString4);
    localSccmsApiGetStarGuestListByLabelTask.setListener(new SccmsApiGetStarGuestListByLabelTask.ISccmsApiGetStarGusetListByLabelTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.147.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final StarGuestLabelList paramAnonymousStarGuestLabelList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.147.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousStarGuestLabelList);
          }
        });
      }
    });
    localSccmsApiGetStarGuestListByLabelTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetStarGuestListByLabelTask);
  }

  public int APIGetSystemMessage(String paramString1, String paramString2, String paramString3, String paramString4, final MgtvApiGetSystemMessageTask.IMgtvApiGetSystemMessageTaskListener paramIMgtvApiGetSystemMessageTaskListener)
  {
    MgtvApiGetSystemMessageTask localMgtvApiGetSystemMessageTask = new MgtvApiGetSystemMessageTask(paramString1, paramString2, paramString3, paramString4);
    localMgtvApiGetSystemMessageTask.setListener(new MgtvApiGetSystemMessageTask.IMgtvApiGetSystemMessageTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.2.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final SystemMessage paramAnonymousSystemMessage)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.2.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousSystemMessage);
          }
        });
      }
    });
    localMgtvApiGetSystemMessageTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localMgtvApiGetSystemMessageTask);
  }

  public int APIGetSystemMessageV2(String paramString1, String paramString2, String paramString3, final MgtvApiGetMQTTClientInfoTask.IMgtvApiGetMQTTClientInfoTaskListener paramIMgtvApiGetMQTTClientInfoTaskListener)
  {
    MgtvApiGetMQTTClientInfoTask localMgtvApiGetMQTTClientInfoTask = new MgtvApiGetMQTTClientInfoTask(paramString1, paramString2, paramString3);
    localMgtvApiGetMQTTClientInfoTask.setListener(new MgtvApiGetMQTTClientInfoTask.IMgtvApiGetMQTTClientInfoTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.3.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final MQTTHttpPostData paramAnonymousMQTTHttpPostData)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.3.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousMQTTHttpPostData);
          }
        });
      }
    });
    localMgtvApiGetMQTTClientInfoTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localMgtvApiGetMQTTClientInfoTask);
  }

  public int APIGetTerminalRealtimeParamsTask(final SccmsApiGetTerminalRealtimeParamsTask.IGetTerminalRealtimeParamsTaskListener paramIGetTerminalRealtimeParamsTaskListener)
  {
    SccmsApiGetTerminalRealtimeParamsTask localSccmsApiGetTerminalRealtimeParamsTask = new SccmsApiGetTerminalRealtimeParamsTask();
    localSccmsApiGetTerminalRealtimeParamsTask.setListener(new SccmsApiGetTerminalRealtimeParamsTask.IGetTerminalRealtimeParamsTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.137.this.val$listener.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final TerminalRealtimeParamList paramAnonymousTerminalRealtimeParamList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.137.this.val$listener.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousTerminalRealtimeParamList);
          }
        });
      }
    });
    localSccmsApiGetTerminalRealtimeParamsTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetTerminalRealtimeParamsTask);
  }

  public int APIGetUiPackage(String paramString1, String paramString2, final SccmsApiGetUiPackageTask.ISccmsApiGetUiPackageTaskListener paramISccmsApiGetUiPackageTaskListener)
  {
    SccmsApiGetUiPackageTask localSccmsApiGetUiPackageTask = new SccmsApiGetUiPackageTask(paramString1, paramString2);
    localSccmsApiGetUiPackageTask.setListener(new SccmsApiGetUiPackageTask.ISccmsApiGetUiPackageTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.37.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UiPackage paramAnonymousUiPackage)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.37.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUiPackage);
          }
        });
      }
    });
    localSccmsApiGetUiPackageTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetUiPackageTask);
  }

  public int APIGetUninstallList(final SccmsApiGetUninstallListTask.ISccmsApiGetUninstallListTaskListener paramISccmsApiGetUninstallListTaskListener)
  {
    SccmsApiGetUninstallListTask localSccmsApiGetUninstallListTask = new SccmsApiGetUninstallListTask();
    localSccmsApiGetUninstallListTask.setListener(new SccmsApiGetUninstallListTask.ISccmsApiGetUninstallListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.103.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UninstallList paramAnonymousUninstallList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.103.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUninstallList);
          }
        });
      }
    });
    localSccmsApiGetUninstallListTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetUninstallListTask);
  }

  public int APIGetUserAttr(final SccmsApiGetUserAttrTask.ISccmsApiGetUserAttrTaskListener paramISccmsApiGetUserAttrTaskListener)
  {
    SccmsApiGetUserAttrTask localSccmsApiGetUserAttrTask = new SccmsApiGetUserAttrTask();
    localSccmsApiGetUserAttrTask.setListener(new SccmsApiGetUserAttrTask.ISccmsApiGetUserAttrTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.155.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserAttr paramAnonymousUserAttr)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.155.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserAttr);
          }
        });
      }
    });
    localSccmsApiGetUserAttrTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetUserAttrTask);
  }

  public int APIGetUserAuth(String paramString1, String paramString2, String paramString3, final SccmsApiGetUserAuthTask.ISccmsApiGetUserAuthTaskListener paramISccmsApiGetUserAuthTaskListener)
  {
    SccmsApiGetUserAuthTask localSccmsApiGetUserAuthTask = new SccmsApiGetUserAuthTask(paramString1, paramString2, paramString3);
    localSccmsApiGetUserAuthTask.setListener(new SccmsApiGetUserAuthTask.ISccmsApiGetUserAuthTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.48.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserAuth paramAnonymousUserAuth)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.48.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserAuth);
          }
        });
      }
    });
    localSccmsApiGetUserAuthTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetUserAuthTask);
  }

  public int APIGetUserAuthV2(GetUserAuthV2Params paramGetUserAuthV2Params, final SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener paramISccmsApiGetUserAuthV2TaskListener)
  {
    SccmsApiGetUserAuthV2Task localSccmsApiGetUserAuthV2Task = new SccmsApiGetUserAuthV2Task(paramGetUserAuthV2Params);
    localSccmsApiGetUserAuthV2Task.setListener(new SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.49.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserAuthV2 paramAnonymousUserAuthV2)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.49.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserAuthV2);
          }
        });
      }
    });
    localSccmsApiGetUserAuthV2Task.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetUserAuthV2Task);
  }

  public int APIGetUserFeedbackInfoTask(String paramString1, String paramString2, final SccmsApiGetUserFeedbackInfoTask.ISccmsApiGetUserFeedbackInfoTaskListener paramISccmsApiGetUserFeedbackInfoTaskListener)
  {
    SccmsApiGetUserFeedbackInfoTask localSccmsApiGetUserFeedbackInfoTask = new SccmsApiGetUserFeedbackInfoTask(paramString1, paramString2);
    localSccmsApiGetUserFeedbackInfoTask.setListener(new SccmsApiGetUserFeedbackInfoTask.ISccmsApiGetUserFeedbackInfoTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.145.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserFeedbackInfoList paramAnonymousUserFeedbackInfoList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.145.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserFeedbackInfoList);
          }
        });
      }
    });
    localSccmsApiGetUserFeedbackInfoTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetUserFeedbackInfoTask);
  }

  public int APIGetUserRecommendRecord(SccmsApiGetUserRecommendTask.ISccmsApiGetUserRecommendTaskListener paramISccmsApiGetUserRecommendTaskListener)
  {
    SccmsApiGetUserRecommendTask localSccmsApiGetUserRecommendTask = new SccmsApiGetUserRecommendTask();
    localSccmsApiGetUserRecommendTask.setListener(paramISccmsApiGetUserRecommendTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetUserRecommendTask);
  }

  public int APIGetUserlike(String paramString, int paramInt, SccmsApiGetUserRecommendV2Task.ISccmsApiGetUserRecommendV2TaskListener paramISccmsApiGetUserRecommendV2TaskListener)
  {
    SccmsApiGetUserRecommendV2Task localSccmsApiGetUserRecommendV2Task = new SccmsApiGetUserRecommendV2Task(paramString, paramInt);
    localSccmsApiGetUserRecommendV2Task.setListener(paramISccmsApiGetUserRecommendV2TaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetUserRecommendV2Task);
  }

  public int APIGetVideoIdByMgtvAssetId(String paramString1, String paramString2, String paramString3, final SccmsApiGetVideoIdByMgtvAssetIdTask.ISccmsApiGetVideoIdByMgtvAssetIdTaskListener paramISccmsApiGetVideoIdByMgtvAssetIdTaskListener)
  {
    SccmsApiGetVideoIdByMgtvAssetIdTask localSccmsApiGetVideoIdByMgtvAssetIdTask = new SccmsApiGetVideoIdByMgtvAssetIdTask(paramString1, paramString2, paramString3);
    localSccmsApiGetVideoIdByMgtvAssetIdTask.setListener(new SccmsApiGetVideoIdByMgtvAssetIdTask.ISccmsApiGetVideoIdByMgtvAssetIdTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.14.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final VideoIdInfo paramAnonymousVideoIdInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.14.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousVideoIdInfo);
          }
        });
      }
    });
    localSccmsApiGetVideoIdByMgtvAssetIdTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetVideoIdByMgtvAssetIdTask);
  }

  public int APIGetVideoIndexInfoTask(String paramString, int paramInt1, int paramInt2, final SccmsApiGetVideoIndexInfoTask.ISccmsApiGetVideoIndexInfoTaskListener paramISccmsApiGetVideoIndexInfoTaskListener)
  {
    SccmsApiGetVideoIndexInfoTask localSccmsApiGetVideoIndexInfoTask = new SccmsApiGetVideoIndexInfoTask(paramString, paramInt1, paramInt2);
    localSccmsApiGetVideoIndexInfoTask.setListener(new SccmsApiGetVideoIndexInfoTask.ISccmsApiGetVideoIndexInfoTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.136.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final VideoIndex paramAnonymousVideoIndex)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.136.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousVideoIndex);
          }
        });
      }
    });
    localSccmsApiGetVideoIndexInfoTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetVideoIndexInfoTask);
  }

  public int APIGetVideoIndexList(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, final SccmsApiGetVideoIndexListTask.ISccmsApiGetVideoIndexListTaskListener paramISccmsApiGetVideoIndexListTaskListener)
  {
    SccmsApiGetVideoIndexListTask localSccmsApiGetVideoIndexListTask = new SccmsApiGetVideoIndexListTask(paramString, paramInt1, paramInt2, paramInt3, paramBoolean);
    localSccmsApiGetVideoIndexListTask.setListener(new SccmsApiGetVideoIndexListTask.ISccmsApiGetVideoIndexListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.90.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final FilmListPageInfo paramAnonymousFilmListPageInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.90.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousFilmListPageInfo);
          }
        });
      }
    });
    localSccmsApiGetVideoIndexListTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetVideoIndexListTask);
  }

  public int APIGetVideoInfoList(SccmsApiGetVideoInfoListTask.ISccmsApiGetVideoInfoListTaskListener paramISccmsApiGetVideoInfoListTaskListener, String paramString1, String paramString2)
  {
    SccmsApiGetVideoInfoListTask localSccmsApiGetVideoInfoListTask = new SccmsApiGetVideoInfoListTask(paramString1, paramString2);
    localSccmsApiGetVideoInfoListTask.setListener(paramISccmsApiGetVideoInfoListTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiGetVideoInfoListTask);
  }

  public int APIGetVideoInfoV2(String paramString, int paramInt, final SccmsApiGetVideoInfoV2Task.ISccmsApiGetVideoInfoV2TaskListener paramISccmsApiGetVideoInfoV2TaskListener)
  {
    SccmsApiGetVideoInfoV2Task localSccmsApiGetVideoInfoV2Task = new SccmsApiGetVideoInfoV2Task(paramString, paramInt);
    localSccmsApiGetVideoInfoV2Task.setListener(new SccmsApiGetVideoInfoV2Task.ISccmsApiGetVideoInfoV2TaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.5.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final VideoInfo paramAnonymousVideoInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.5.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousVideoInfo);
          }
        });
      }
    });
    localSccmsApiGetVideoInfoV2Task.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetVideoInfoV2Task);
  }

  public int APIGetVideoInfoV3(String paramString, int paramInt, final SccmsApiGetVideoInfoV3Task.ISccmsApiGetVideoInfoV3TaskListener paramISccmsApiGetVideoInfoV3TaskListener)
  {
    SccmsApiGetVideoInfoV3Task localSccmsApiGetVideoInfoV3Task = new SccmsApiGetVideoInfoV3Task(paramString, paramInt);
    localSccmsApiGetVideoInfoV3Task.setListener(new SccmsApiGetVideoInfoV3Task.ISccmsApiGetVideoInfoV3TaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.160.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final VideoInfo paramAnonymousVideoInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.160.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousVideoInfo);
          }
        });
      }
    });
    localSccmsApiGetVideoInfoV3Task.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetVideoInfoV3Task);
  }

  public int APIGetVideoLabelType(String paramString, SccmsApiGetVideoLabelTypeTask.ISccmsApiGetVideoLabelTypeTaskListener paramISccmsApiGetVideoLabelTypeTaskListener)
  {
    return APIGetVideoLabelType(paramString, paramISccmsApiGetVideoLabelTypeTaskListener, false, null).getTaskId();
  }

  public ServerApiTask APIGetVideoLabelType(String paramString, final SccmsApiGetVideoLabelTypeTask.ISccmsApiGetVideoLabelTypeTaskListener paramISccmsApiGetVideoLabelTypeTaskListener, boolean paramBoolean, Long paramLong)
  {
    SccmsApiGetVideoLabelTypeTask localSccmsApiGetVideoLabelTypeTask = new SccmsApiGetVideoLabelTypeTask(paramString);
    localSccmsApiGetVideoLabelTypeTask.setListener(new SccmsApiGetVideoLabelTypeTask.ISccmsApiGetVideoLabelTypeTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.20.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final List<LabelSort> paramAnonymousList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.20.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousList);
          }
        });
      }
    });
    localSccmsApiGetVideoLabelTypeTask.setForceUpdate(paramBoolean);
    if (paramLong != null)
      localSccmsApiGetVideoLabelTypeTask.setCacheLife(paramLong.longValue());
    localSccmsApiGetVideoLabelTypeTask.setIsUiSafe(false);
    this.httpApiEngine.addTask(localSccmsApiGetVideoLabelTypeTask);
    return localSccmsApiGetVideoLabelTypeTask;
  }

  public int APIGetVideoList(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, SccmsApiGetVideoListTask.ISccmsApiGetVideoListTaskListener paramISccmsApiGetVideoListTaskListener)
  {
    return APIGetVideoList(paramString1, paramString2, paramString3, paramInt1, paramInt2, paramISccmsApiGetVideoListTaskListener, false, null).getTaskId();
  }

  public ServerApiTask APIGetVideoList(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, final SccmsApiGetVideoListTask.ISccmsApiGetVideoListTaskListener paramISccmsApiGetVideoListTaskListener, boolean paramBoolean, Long paramLong)
  {
    SccmsApiGetVideoListTask localSccmsApiGetVideoListTask = new SccmsApiGetVideoListTask(paramString1, paramString2, paramString3, paramInt1, paramInt2);
    localSccmsApiGetVideoListTask.setListener(new SccmsApiGetVideoListTask.ISccmsApiGetVideoListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.18.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final FilmItem paramAnonymousFilmItem)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.18.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousFilmItem);
          }
        });
      }
    });
    localSccmsApiGetVideoListTask.setForceUpdate(paramBoolean);
    if (paramLong != null)
      localSccmsApiGetVideoListTask.setCacheLife(paramLong.longValue());
    localSccmsApiGetVideoListTask.setIsUiSafe(false);
    this.httpApiEngine.addTask(localSccmsApiGetVideoListTask);
    return localSccmsApiGetVideoListTask;
  }

  public int APIGetVideoListByLabel(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, String paramString4, final SccmsApiGetVideoListByLabelTask.ISccmsApiGetVideoListByLabelTaskListener paramISccmsApiGetVideoListByLabelTaskListener)
  {
    SccmsApiGetVideoListByLabelTask localSccmsApiGetVideoListByLabelTask = new SccmsApiGetVideoListByLabelTask(paramString1, paramString2, paramString3, paramInt1, paramInt2, paramString4);
    localSccmsApiGetVideoListByLabelTask.setListener(new SccmsApiGetVideoListByLabelTask.ISccmsApiGetVideoListByLabelTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.19.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final FilmItem paramAnonymousFilmItem)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.19.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousFilmItem);
          }
        });
      }
    });
    localSccmsApiGetVideoListByLabelTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetVideoListByLabelTask);
  }

  public int APIGetVideoScoreByUserId(String paramString1, String paramString2, final SccmsApiGetVideoScoreByUserIdTask.ISccmsApiGetVideoScoreByUserIdTaskListener paramISccmsApiGetVideoScoreByUserIdTaskListener)
  {
    SccmsApiGetVideoScoreByUserIdTask localSccmsApiGetVideoScoreByUserIdTask = new SccmsApiGetVideoScoreByUserIdTask(paramString1, paramString2);
    localSccmsApiGetVideoScoreByUserIdTask.setListener(new SccmsApiGetVideoScoreByUserIdTask.ISccmsApiGetVideoScoreByUserIdTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.13.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final VideoScoreInfoOnUser paramAnonymousVideoScoreInfoOnUser)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.13.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousVideoScoreInfoOnUser);
          }
        });
      }
    });
    localSccmsApiGetVideoScoreByUserIdTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetVideoScoreByUserIdTask);
  }

  public int APIGetWeatherInfo(String paramString, final SccmsApiGetWeatherInfoTask.ISccmsApiGetWeatherInfoTaskListener paramISccmsApiGetWeatherInfoTaskListener)
  {
    SccmsApiGetWeatherInfoTask localSccmsApiGetWeatherInfoTask = new SccmsApiGetWeatherInfoTask(paramString);
    localSccmsApiGetWeatherInfoTask.setListener(new SccmsApiGetWeatherInfoTask.ISccmsApiGetWeatherInfoTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.84.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final WeatherList paramAnonymousWeatherList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.84.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousWeatherList);
          }
        });
      }
    });
    localSccmsApiGetWeatherInfoTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiGetWeatherInfoTask);
  }

  public int APIN3A2GetEpgData(final SccmsApiN3A2GetEpgDataTask.ISccmsApiN3A2GetEpgDataTaskListener paramISccmsApiN3A2GetEpgDataTaskListener)
  {
    SccmsApiN3A2GetEpgDataTask localSccmsApiN3A2GetEpgDataTask = new SccmsApiN3A2GetEpgDataTask();
    localSccmsApiN3A2GetEpgDataTask.setListener(new SccmsApiN3A2GetEpgDataTask.ISccmsApiN3A2GetEpgDataTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        paramISccmsApiN3A2GetEpgDataTaskListener.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<MetadataGoup> paramAnonymousArrayList)
      {
        paramISccmsApiN3A2GetEpgDataTaskListener.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
      }
    });
    localSccmsApiN3A2GetEpgDataTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiN3A2GetEpgDataTask);
  }

  public int APIReportAdImpression(String paramString)
  {
    SccmsApiRequestImpressionTask localSccmsApiRequestImpressionTask = new SccmsApiRequestImpressionTask(paramString);
    return this.httpApiEngine.addTask(localSccmsApiRequestImpressionTask);
  }

  public int APIReportAdState(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    SccmsApiReportAdStateTask localSccmsApiReportAdStateTask = new SccmsApiReportAdStateTask(paramString1, paramString2, paramString3, paramString4);
    localSccmsApiReportAdStateTask.setListener(null);
    return this.httpApiEngine.addTask(localSccmsApiReportAdStateTask);
  }

  public int APIReportErrorTask(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    SccmsApiReportErrorTask localSccmsApiReportErrorTask = new SccmsApiReportErrorTask(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8);
    return this.httpApiEngine.addTask(localSccmsApiReportErrorTask);
  }

  public int APIReportSpeedTestResult(String paramString, final SccmsApiReportSpeedTestResultTask.ISccmsApiReportSpeedTestResultTaskListener paramISccmsApiReportSpeedTestResultTaskListener)
  {
    SccmsApiReportSpeedTestResultTask localSccmsApiReportSpeedTestResultTask = new SccmsApiReportSpeedTestResultTask(paramString);
    localSccmsApiReportSpeedTestResultTask.setListener(new SccmsApiReportSpeedTestResultTask.ISccmsApiReportSpeedTestResultTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.35.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.35.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo);
          }
        });
      }
    });
    localSccmsApiReportSpeedTestResultTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiReportSpeedTestResultTask);
  }

  public int APIReportUserBehavior(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, final SccmsApiReportUserBehaviorTask.ISccmsApiReportUserBehaviorListener paramISccmsApiReportUserBehaviorListener)
  {
    SccmsApiReportUserBehaviorTask localSccmsApiReportUserBehaviorTask = new SccmsApiReportUserBehaviorTask(paramInt1, paramInt2, paramString1, paramString2, paramString3, paramString4, paramString5, new SccmsApiReportUserBehaviorTask.ISccmsApiReportUserBehaviorListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.157.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserAttr paramAnonymousUserAttr)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.157.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserAttr);
          }
        });
      }
    });
    localSccmsApiReportUserBehaviorTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiReportUserBehaviorTask);
  }

  public int APISearchActorStarListTask(int paramInt1, int paramInt2, String paramString1, String paramString2, final SccmsApiSearchActorStarListTask.ISccmsApiSearchActorStarListTaskListener paramISccmsApiSearchActorStarListTaskListener)
  {
    SccmsApiSearchActorStarListTask localSccmsApiSearchActorStarListTask = new SccmsApiSearchActorStarListTask(paramInt1, paramInt2, paramString1, paramString2);
    localSccmsApiSearchActorStarListTask.setListener(new SccmsApiSearchActorStarListTask.ISccmsApiSearchActorStarListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.149.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final SearchActorStarList paramAnonymousSearchActorStarList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.149.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousSearchActorStarList);
          }
        });
      }
    });
    localSccmsApiSearchActorStarListTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiSearchActorStarListTask);
  }

  public int APISearchActorsAndDirectorsByPinyin(String paramString, final SccmsApiSearchActorsAndDirectorsByPinyinTask.ISccmsApiSearchActorsAndDirectorsByPinyinTaskListener paramISccmsApiSearchActorsAndDirectorsByPinyinTaskListener)
  {
    SccmsApiSearchActorsAndDirectorsByPinyinTask localSccmsApiSearchActorsAndDirectorsByPinyinTask = new SccmsApiSearchActorsAndDirectorsByPinyinTask(paramString);
    localSccmsApiSearchActorsAndDirectorsByPinyinTask.setListener(new SccmsApiSearchActorsAndDirectorsByPinyinTask.ISccmsApiSearchActorsAndDirectorsByPinyinTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.8.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final VideoRoleList paramAnonymousVideoRoleList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.8.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousVideoRoleList);
          }
        });
      }
    });
    localSccmsApiSearchActorsAndDirectorsByPinyinTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiSearchActorsAndDirectorsByPinyinTask);
  }

  public int APISearchApp(int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, final SccmsApiSearchAppTask.ISccmsApiSearchAppTaskListener paramISccmsApiSearchAppTaskListener)
  {
    SccmsApiSearchAppTask localSccmsApiSearchAppTask = new SccmsApiSearchAppTask(paramInt1, paramInt2, paramInt3, paramString1, paramString2);
    localSccmsApiSearchAppTask.setListener(new SccmsApiSearchAppTask.ISccmsApiSearchAppTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.100.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final AppList paramAnonymousAppList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.100.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousAppList);
          }
        });
      }
    });
    localSccmsApiSearchAppTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiSearchAppTask);
  }

  public int APISearchStarListTask(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, final SccmsApiSearchVideoByPinyinTask.ISccmsApiSearchVideoByPinyinTaskListener paramISccmsApiSearchVideoByPinyinTaskListener)
  {
    SccmsApiSearchVideoByPinyinTask localSccmsApiSearchVideoByPinyinTask = new SccmsApiSearchVideoByPinyinTask(paramString1, paramInt1, paramInt2, paramString2, paramString3);
    localSccmsApiSearchVideoByPinyinTask.setListener(new SccmsApiSearchVideoByPinyinTask.ISccmsApiSearchVideoByPinyinTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.152.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final SearchStruct paramAnonymousSearchStruct)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.152.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousSearchStruct);
          }
        });
      }
    });
    localSccmsApiSearchVideoByPinyinTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiSearchVideoByPinyinTask);
  }

  public int APISearchStarWorkSetTask(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, int paramInt3, final SccmsApiSearchVideoWorkSetByPinyinTask.ISccmsApiSearchVideoWorkSetByPinyinTaskListener paramISccmsApiSearchVideoWorkSetByPinyinTaskListener)
  {
    SccmsApiSearchVideoWorkSetByPinyinTask localSccmsApiSearchVideoWorkSetByPinyinTask = new SccmsApiSearchVideoWorkSetByPinyinTask(paramString1, paramInt1, paramInt2, paramString2, paramString3, paramInt3);
    localSccmsApiSearchVideoWorkSetByPinyinTask.setListener(new SccmsApiSearchVideoWorkSetByPinyinTask.ISccmsApiSearchVideoWorkSetByPinyinTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.151.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final SearchStruct paramAnonymousSearchStruct)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.151.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousSearchStruct);
          }
        });
      }
    });
    localSccmsApiSearchVideoWorkSetByPinyinTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiSearchVideoWorkSetByPinyinTask);
  }

  public int APISearchVideoByChinese(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, final SccmsApiSearchVideoByPinyinTask.ISccmsApiSearchVideoByPinyinTaskListener paramISccmsApiSearchVideoByPinyinTaskListener)
  {
    SccmsApiSearchVideoByPinyinTask localSccmsApiSearchVideoByPinyinTask = new SccmsApiSearchVideoByPinyinTask(paramString1, paramString2, paramString3, "name_likechar|pinyin_likechar", paramInt1, paramInt2);
    localSccmsApiSearchVideoByPinyinTask.setListener(new SccmsApiSearchVideoByPinyinTask.ISccmsApiSearchVideoByPinyinTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.9.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final SearchStruct paramAnonymousSearchStruct)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.9.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousSearchStruct);
          }
        });
      }
    });
    localSccmsApiSearchVideoByPinyinTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiSearchVideoByPinyinTask);
  }

  public int APISearchVideoByPinyin(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, SccmsApiSearchVideoByPinyinTask.ISccmsApiSearchVideoByPinyinTaskListener paramISccmsApiSearchVideoByPinyinTaskListener)
  {
    return APISearchVideoByPinyin(paramString1, paramString2, paramString3, "pinyin_likechar", paramInt1, paramInt2, paramISccmsApiSearchVideoByPinyinTaskListener);
  }

  public int APISearchVideoByPinyin(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, final SccmsApiSearchVideoByPinyinTask.ISccmsApiSearchVideoByPinyinTaskListener paramISccmsApiSearchVideoByPinyinTaskListener)
  {
    SccmsApiSearchVideoByPinyinTask localSccmsApiSearchVideoByPinyinTask = new SccmsApiSearchVideoByPinyinTask(paramString1, paramString2, paramString3, paramString4, paramInt1, paramInt2);
    localSccmsApiSearchVideoByPinyinTask.setListener(new SccmsApiSearchVideoByPinyinTask.ISccmsApiSearchVideoByPinyinTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.10.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final SearchStruct paramAnonymousSearchStruct)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.10.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousSearchStruct);
          }
        });
      }
    });
    localSccmsApiSearchVideoByPinyinTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiSearchVideoByPinyinTask);
  }

  public int APISetVideoWish(VideoInfo paramVideoInfo, SccmsApiSetVideoWishTask.ISccmsApiSetVideoWishTaskListener paramISccmsApiSetVideoWishTaskListener)
  {
    SccmsApiSetVideoWishTask localSccmsApiSetVideoWishTask = new SccmsApiSetVideoWishTask(paramVideoInfo, paramISccmsApiSetVideoWishTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiSetVideoWishTask);
  }

  public int APISyncServerTime(final SccmsApiSyncTimeTask.ISccmsApiSyncTimeTaskListener paramISccmsApiSyncTimeTaskListener)
  {
    SccmsApiSyncTimeTask localSccmsApiSyncTimeTask = new SccmsApiSyncTimeTask();
    localSccmsApiSyncTimeTask.setListener(new SccmsApiSyncTimeTask.ISccmsApiSyncTimeTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.46.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final String paramAnonymousString)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.46.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousString);
          }
        });
      }
    });
    localSccmsApiSyncTimeTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiSyncTimeTask);
  }

  public int APIUploadAllCatchVideoRecordsToCloud(UploadAllRecordsParams paramUploadAllRecordsParams, final SccmsApiUploadAllCatchVideoRecordTask.ISccmsApiUploadAllCatchVideoRecordTaskListener paramISccmsApiUploadAllCatchVideoRecordTaskListener)
  {
    SccmsApiUploadAllCatchVideoRecordTask localSccmsApiUploadAllCatchVideoRecordTask = new SccmsApiUploadAllCatchVideoRecordTask(paramUploadAllRecordsParams);
    localSccmsApiUploadAllCatchVideoRecordTask.setListener(new SccmsApiUploadAllCatchVideoRecordTask.ISccmsApiUploadAllCatchVideoRecordTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.56.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.56.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiUploadAllCatchVideoRecordTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiUploadAllCatchVideoRecordTask);
  }

  public int APIUploadAllCollectRecordsToCloud(UploadAllRecordsParams paramUploadAllRecordsParams, final SccmsApiUploadAllCollectRecordTask.ISccmsApiUploadAllCollectRecordTaskListener paramISccmsApiUploadAllCollectRecordTaskListener)
  {
    SccmsApiUploadAllCollectRecordTask localSccmsApiUploadAllCollectRecordTask = new SccmsApiUploadAllCollectRecordTask(paramUploadAllRecordsParams);
    localSccmsApiUploadAllCollectRecordTask.setListener(new SccmsApiUploadAllCollectRecordTask.ISccmsApiUploadAllCollectRecordTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.55.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.55.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiUploadAllCollectRecordTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiUploadAllCollectRecordTask);
  }

  public int APIUploadAllPlayRecordsToCloud(UploadAllRecordsParams paramUploadAllRecordsParams, final SccmsApiUploadAllPlayRecordTask.ISccmsApiUploadAllPlayRecordTaskListener paramISccmsApiUploadAllPlayRecordTaskListener)
  {
    SccmsApiUploadAllPlayRecordTask localSccmsApiUploadAllPlayRecordTask = new SccmsApiUploadAllPlayRecordTask(paramUploadAllRecordsParams);
    localSccmsApiUploadAllPlayRecordTask.setListener(new SccmsApiUploadAllPlayRecordTask.ISccmsApiUploadAllPlayRecordTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.54.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.54.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousArrayList);
          }
        });
      }
    });
    localSccmsApiUploadAllPlayRecordTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiUploadAllPlayRecordTask);
  }

  public int APIUserCenterBindMobile(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, final SccmsApiUserCenterBindMobileTask.ISccmsApiBindMobileTaskListener paramISccmsApiBindMobileTaskListener)
  {
    SccmsApiUserCenterBindMobileTask localSccmsApiUserCenterBindMobileTask = new SccmsApiUserCenterBindMobileTask(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6);
    localSccmsApiUserCenterBindMobileTask.setListener(new SccmsApiUserCenterBindMobileTask.ISccmsApiBindMobileTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.115.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserCenterBindMobile paramAnonymousUserCenterBindMobile)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.115.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserCenterBindMobile);
          }
        });
      }
    });
    localSccmsApiUserCenterBindMobileTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterBindMobileTask);
  }

  public int APIUserCenterChangPwd(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, final SccmsApiUserCenterChangePwdTask.ISccmsApiUserCenterChangePwdTaskListener paramISccmsApiUserCenterChangePwdTaskListener)
  {
    SccmsApiUserCenterChangePwdTask localSccmsApiUserCenterChangePwdTask = new SccmsApiUserCenterChangePwdTask(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6);
    localSccmsApiUserCenterChangePwdTask.setListener(new SccmsApiUserCenterChangePwdTask.ISccmsApiUserCenterChangePwdTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.110.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserCenterChangePwd paramAnonymousUserCenterChangePwd)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.110.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserCenterChangePwd);
          }
        });
      }
    });
    localSccmsApiUserCenterChangePwdTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterChangePwdTask);
  }

  public int APIUserCenterCheckReturn(String paramString1, String paramString2, final SccmsApiUserCenterCheckReturnTask.ISccmsApiCheckUserCenterReturnTaskListener paramISccmsApiCheckUserCenterReturnTaskListener)
  {
    SccmsApiUserCenterCheckReturnTask localSccmsApiUserCenterCheckReturnTask = new SccmsApiUserCenterCheckReturnTask(paramString1, paramString2);
    localSccmsApiUserCenterCheckReturnTask.setListener(new SccmsApiUserCenterCheckReturnTask.ISccmsApiCheckUserCenterReturnTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.112.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserCenterCheckReturn paramAnonymousUserCenterCheckReturn)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.112.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserCenterCheckReturn);
          }
        });
      }
    });
    localSccmsApiUserCenterCheckReturnTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterCheckReturnTask);
  }

  public int APIUserCenterGet3rdLoginUrl(String paramString, final SccmsApiUserCenterGet3rdLoginUrlTask.ISccmsApiUserCenterGet3rdLoginUrlTaskListener paramISccmsApiUserCenterGet3rdLoginUrlTaskListener)
  {
    SccmsApiUserCenterGet3rdLoginUrlTask localSccmsApiUserCenterGet3rdLoginUrlTask = new SccmsApiUserCenterGet3rdLoginUrlTask(paramString);
    localSccmsApiUserCenterGet3rdLoginUrlTask.setListener(new SccmsApiUserCenterGet3rdLoginUrlTask.ISccmsApiUserCenterGet3rdLoginUrlTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.106.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserCenter3rdLoginUrl paramAnonymousUserCenter3rdLoginUrl)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.106.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserCenter3rdLoginUrl);
          }
        });
      }
    });
    localSccmsApiUserCenterGet3rdLoginUrlTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterGet3rdLoginUrlTask);
  }

  public int APIUserCenterGetMobileCode(String paramString1, String paramString2, final SccmsApiUserCenterGetMobileCodeTask.ISccmsApiGetMobileMsgAuthTaskListener paramISccmsApiGetMobileMsgAuthTaskListener)
  {
    SccmsApiUserCenterGetMobileCodeTask localSccmsApiUserCenterGetMobileCodeTask = new SccmsApiUserCenterGetMobileCodeTask(paramString1, paramString2);
    localSccmsApiUserCenterGetMobileCodeTask.setListener(new SccmsApiUserCenterGetMobileCodeTask.ISccmsApiGetMobileMsgAuthTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.108.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final MobileCode paramAnonymousMobileCode)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.108.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousMobileCode);
          }
        });
      }
    });
    localSccmsApiUserCenterGetMobileCodeTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterGetMobileCodeTask);
  }

  public int APIUserCenterGetMobileCodeByUserName(String paramString1, String paramString2, final SccmsApiUserCenterGetMobileCodeByUserNameTask.ISccmsApiGetMobileCodeByUserNameTaskListener paramISccmsApiGetMobileCodeByUserNameTaskListener)
  {
    SccmsApiUserCenterGetMobileCodeByUserNameTask localSccmsApiUserCenterGetMobileCodeByUserNameTask = new SccmsApiUserCenterGetMobileCodeByUserNameTask(paramString1, paramString2);
    localSccmsApiUserCenterGetMobileCodeByUserNameTask.setListener(new SccmsApiUserCenterGetMobileCodeByUserNameTask.ISccmsApiGetMobileCodeByUserNameTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.113.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final MobileCode paramAnonymousMobileCode)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.113.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousMobileCode);
          }
        });
      }
    });
    localSccmsApiUserCenterGetMobileCodeByUserNameTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterGetMobileCodeByUserNameTask);
  }

  public int APIUserCenterGetWebChatLoginPic(String paramString, int paramInt, final SccmsApiUserCenterGetWebChatLoginPicTask.ISccmsApiGetWebChatLoginPicTaskListenter paramISccmsApiGetWebChatLoginPicTaskListenter)
  {
    SccmsApiUserCenterGetWebChatLoginPicTask localSccmsApiUserCenterGetWebChatLoginPicTask = new SccmsApiUserCenterGetWebChatLoginPicTask(paramString, paramInt);
    localSccmsApiUserCenterGetWebChatLoginPicTask.setListener(new SccmsApiUserCenterGetWebChatLoginPicTask.ISccmsApiGetWebChatLoginPicTaskListenter()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.111.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final WebChatLogin paramAnonymousWebChatLogin)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.111.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousWebChatLogin);
          }
        });
      }
    });
    localSccmsApiUserCenterGetWebChatLoginPicTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterGetWebChatLoginPicTask);
  }

  public int APIUserCenterLogin(String paramString1, String paramString2, String paramString3, final SccmsApiUserCenterLoginTask.ISccmsApiUserCenterLoginTaskListener paramISccmsApiUserCenterLoginTaskListener)
  {
    SccmsApiUserCenterLoginTask localSccmsApiUserCenterLoginTask = new SccmsApiUserCenterLoginTask(paramString1, paramString2, paramString3);
    localSccmsApiUserCenterLoginTask.setListener(new SccmsApiUserCenterLoginTask.ISccmsApiUserCenterLoginTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.105.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserCenterLogin paramAnonymousUserCenterLogin)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.105.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserCenterLogin);
          }
        });
      }
    });
    localSccmsApiUserCenterLoginTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterLoginTask);
  }

  public int APIUserCenterLogout(String paramString1, String paramString2, final SccmsApiUserCenterLogoutTask.ISccmsApiUserCenterLogoutTaskListener paramISccmsApiUserCenterLogoutTaskListener)
  {
    SccmsApiUserCenterLogoutTask localSccmsApiUserCenterLogoutTask = new SccmsApiUserCenterLogoutTask(paramString1, paramString2);
    localSccmsApiUserCenterLogoutTask.setListener(new SccmsApiUserCenterLogoutTask.ISccmsApiUserCenterLogoutTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.107.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserCenterLogout paramAnonymousUserCenterLogout)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.107.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserCenterLogout);
          }
        });
      }
    });
    localSccmsApiUserCenterLogoutTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterLogoutTask);
  }

  public int APIUserCenterRegistUser(String paramString1, String paramString2, String paramString3, String paramString4, final SccmsApiUserCenterRegisterTask.ISccmsApiUserCenterRegisterTaskListener paramISccmsApiUserCenterRegisterTaskListener)
  {
    SccmsApiUserCenterRegisterTask localSccmsApiUserCenterRegisterTask = new SccmsApiUserCenterRegisterTask(paramString1, paramString2, paramString3, paramString4);
    localSccmsApiUserCenterRegisterTask.setListener(new SccmsApiUserCenterRegisterTask.ISccmsApiUserCenterRegisterTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.109.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserCenterRegister paramAnonymousUserCenterRegister)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.109.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserCenterRegister);
          }
        });
      }
    });
    localSccmsApiUserCenterRegisterTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterRegisterTask);
  }

  public int APIUserCenterResetPwd(String paramString1, String paramString2, String paramString3, String paramString4, final SccmsApiUserCenterResetPwdTask.ISccmsApiUserCenterResetPwdTaskListener paramISccmsApiUserCenterResetPwdTaskListener)
  {
    SccmsApiUserCenterResetPwdTask localSccmsApiUserCenterResetPwdTask = new SccmsApiUserCenterResetPwdTask(paramString1, paramString2, paramString3, paramString4);
    localSccmsApiUserCenterResetPwdTask.setListener(new SccmsApiUserCenterResetPwdTask.ISccmsApiUserCenterResetPwdTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.114.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserCenterChangePwd paramAnonymousUserCenterChangePwd)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.114.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserCenterChangePwd);
          }
        });
      }
    });
    localSccmsApiUserCenterResetPwdTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterResetPwdTask);
  }

  public int APIUserCenterUnBindMobile(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, final SccmsApiUserCenterUnBindMobileTask.ISccmsApiUnBindMobileTaskListener paramISccmsApiUnBindMobileTaskListener)
  {
    SccmsApiUserCenterUnBindMobileTask localSccmsApiUserCenterUnBindMobileTask = new SccmsApiUserCenterUnBindMobileTask(paramString1, paramString2, paramString3, paramString4, paramString5);
    localSccmsApiUserCenterUnBindMobileTask.setListener(new SccmsApiUserCenterUnBindMobileTask.ISccmsApiUnBindMobileTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.116.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserCenterBindMobile paramAnonymousUserCenterBindMobile)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.116.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserCenterBindMobile);
          }
        });
      }
    });
    localSccmsApiUserCenterUnBindMobileTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterUnBindMobileTask);
  }

  public int APIUserCenterUnbindWebchatTask(String paramString1, String paramString2, final SccmsApiUserCenterUnbindWebchatTask.ISccmsApiUserCenterUnbindWebchatTaskListener paramISccmsApiUserCenterUnbindWebchatTaskListener)
  {
    SccmsApiUserCenterUnbindWebchatTask localSccmsApiUserCenterUnbindWebchatTask = new SccmsApiUserCenterUnbindWebchatTask(paramString1, paramString2);
    localSccmsApiUserCenterUnbindWebchatTask.setListener(new SccmsApiUserCenterUnbindWebchatTask.ISccmsApiUserCenterUnbindWebchatTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.134.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserCenterUnbindWebChat paramAnonymousUserCenterUnbindWebChat)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.134.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserCenterUnbindWebChat);
          }
        });
      }
    });
    localSccmsApiUserCenterUnbindWebchatTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterUnbindWebchatTask);
  }

  public int APIUserCenterVerifyToken(String paramString1, String paramString2, final SccmsApiUserCenterVerifyTokenTask.ISccmsApiUserCenterVerifyTokenTaskListener paramISccmsApiUserCenterVerifyTokenTaskListener)
  {
    SccmsApiUserCenterVerifyTokenTask localSccmsApiUserCenterVerifyTokenTask = new SccmsApiUserCenterVerifyTokenTask(paramString1, paramString2);
    localSccmsApiUserCenterVerifyTokenTask.setListener(new SccmsApiUserCenterVerifyTokenTask.ISccmsApiUserCenterVerifyTokenTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.117.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserCenterLogin paramAnonymousUserCenterLogin)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.117.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserCenterLogin);
          }
        });
      }
    });
    localSccmsApiUserCenterVerifyTokenTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiUserCenterVerifyTokenTask);
  }

  public int APIUserLogin(String paramString1, String paramString2, final SccmsApiUserLoginTask.ISccmsApiUserLoginTaskListener paramISccmsApiUserLoginTaskListener)
  {
    SccmsApiUserLoginTask localSccmsApiUserLoginTask = new SccmsApiUserLoginTask(paramString1, paramString2);
    localSccmsApiUserLoginTask.setListener(new SccmsApiUserLoginTask.ISccmsApiUserLoginTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.88.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserInfo paramAnonymousUserInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.88.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserInfo);
          }
        });
      }
    });
    localSccmsApiUserLoginTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiUserLoginTask);
  }

  public int APIXiaoMiUserLogin(String paramString1, String paramString2, final SccmsApiXiaomiLoginTask.ISccmsApiXiaomiLoginTaskListener paramISccmsApiXiaomiLoginTaskListener)
  {
    SccmsApiXiaomiLoginTask localSccmsApiXiaomiLoginTask = new SccmsApiXiaomiLoginTask(paramString1, paramString2);
    localSccmsApiXiaomiLoginTask.setListener(new SccmsApiXiaomiLoginTask.ISccmsApiXiaomiLoginTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.159.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserCenterLogin paramAnonymousUserCenterLogin)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.159.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserCenterLogin);
          }
        });
      }
    });
    localSccmsApiXiaomiLoginTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiXiaomiLoginTask);
  }

  public int ApiAAAGetMovieCouponCount(String paramString1, int paramInt, String paramString2, SccmsApiAAAGetMovieCouponCountTask.ISccmsApiAAAGetMovieCouponCountTaskListener paramISccmsApiAAAGetMovieCouponCountTaskListener)
  {
    SccmsApiAAAGetMovieCouponCountTask localSccmsApiAAAGetMovieCouponCountTask = new SccmsApiAAAGetMovieCouponCountTask(paramString1, paramInt, paramString2);
    localSccmsApiAAAGetMovieCouponCountTask.setListener(paramISccmsApiAAAGetMovieCouponCountTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetMovieCouponCountTask);
  }

  public int ApiAAAGetMovieCouponHistory(String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3, SccmsApiAAAGetMovieCouponHistoryTask.ISccmsApiAAAGetMovieCouponHistoryTaskListener paramISccmsApiAAAGetMovieCouponHistoryTaskListener)
  {
    SccmsApiAAAGetMovieCouponHistoryTask localSccmsApiAAAGetMovieCouponHistoryTask = new SccmsApiAAAGetMovieCouponHistoryTask(paramString1, paramInt1, paramString2, paramInt2, paramInt3);
    localSccmsApiAAAGetMovieCouponHistoryTask.setListener(paramISccmsApiAAAGetMovieCouponHistoryTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetMovieCouponHistoryTask);
  }

  public int ApiAAAGetMovieCouponInfo(String paramString1, int paramInt, String paramString2, SccmsApiAAAGetMovieCouponInfoTask.ISccmsApiAAAGetMovieCouponInfoTaskListener paramISccmsApiAAAGetMovieCouponInfoTaskListener)
  {
    SccmsApiAAAGetMovieCouponInfoTask localSccmsApiAAAGetMovieCouponInfoTask = new SccmsApiAAAGetMovieCouponInfoTask(paramString1, paramInt, paramString2);
    localSccmsApiAAAGetMovieCouponInfoTask.setListener(paramISccmsApiAAAGetMovieCouponInfoTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetMovieCouponInfoTask);
  }

  public int ApiAAAGetPayAuthorizeStatus(String paramString1, int paramInt, String paramString2, SccmsApiAAAGetPayAuthorizeStatusTask.ISccmsApiAAAGetPayAuthorizeStatusListener paramISccmsApiAAAGetPayAuthorizeStatusListener)
  {
    SccmsApiAAAGetPayAuthorizeStatusTask localSccmsApiAAAGetPayAuthorizeStatusTask = new SccmsApiAAAGetPayAuthorizeStatusTask(paramString1, paramInt, paramString2);
    localSccmsApiAAAGetPayAuthorizeStatusTask.setListener(paramISccmsApiAAAGetPayAuthorizeStatusListener);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetPayAuthorizeStatusTask);
  }

  public int ApiAAAGetUserInfo(String paramString1, int paramInt, String paramString2, final SccmsApiAAAGetUserInfoTask.ISccmsApiGetUserCenterInfoTaskListener paramISccmsApiGetUserCenterInfoTaskListener)
  {
    SccmsApiAAAGetUserInfoTask localSccmsApiAAAGetUserInfoTask = new SccmsApiAAAGetUserInfoTask(paramString1, paramInt, paramString2);
    localSccmsApiAAAGetUserInfoTask.setListener(new SccmsApiAAAGetUserInfoTask.ISccmsApiGetUserCenterInfoTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.132.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final UserCenterInfo paramAnonymousUserCenterInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.132.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousUserCenterInfo);
          }
        });
      }
    });
    localSccmsApiAAAGetUserInfoTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiAAAGetUserInfoTask);
  }

  public int ApiAAAQueryPayAuthorizeStatus(String paramString1, int paramInt, String paramString2, SccmsApiAAAQueryPayAuthorizeStatusTask.ISccmsApiAAAQueryPayAuthorizeStatusTaskListener paramISccmsApiAAAQueryPayAuthorizeStatusTaskListener)
  {
    SccmsApiAAAQueryPayAuthorizeStatusTask localSccmsApiAAAQueryPayAuthorizeStatusTask = new SccmsApiAAAQueryPayAuthorizeStatusTask(paramString1, paramInt, paramString2);
    localSccmsApiAAAQueryPayAuthorizeStatusTask.setListener(paramISccmsApiAAAQueryPayAuthorizeStatusTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAAQueryPayAuthorizeStatusTask);
  }

  public int ApiAAAUseMovieCoupon(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, SccmsApiAAAUseMovieCouponTask.ISccmsApiAAAUseMovieCouponTaskListener paramISccmsApiAAAUseMovieCouponTaskListener)
  {
    SccmsApiAAAUseMovieCouponTask localSccmsApiAAAUseMovieCouponTask = new SccmsApiAAAUseMovieCouponTask(paramString1, paramInt, paramString2, paramString3, paramString4);
    localSccmsApiAAAUseMovieCouponTask.setListener(paramISccmsApiAAAUseMovieCouponTaskListener);
    return this.httpApiEngine.addTask(localSccmsApiAAAUseMovieCouponTask);
  }

  public ServerApiTask ApiGetDynamicCategoryItemList(String paramString, int[] paramArrayOfInt, final SccmsApiGetDynamicCategoryItemListTask.ISccmsApiGetDynamicCategoryItemListTaskListener paramISccmsApiGetDynamicCategoryItemListTaskListener)
  {
    SccmsApiGetDynamicCategoryItemListTask localSccmsApiGetDynamicCategoryItemListTask = new SccmsApiGetDynamicCategoryItemListTask(paramString, paramArrayOfInt);
    localSccmsApiGetDynamicCategoryItemListTask.setListener(new SccmsApiGetDynamicCategoryItemListTask.ISccmsApiGetDynamicCategoryItemListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.17.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final LinkedHashMap<String, CategoryPosterList> paramAnonymousLinkedHashMap)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.17.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousLinkedHashMap);
          }
        });
      }
    });
    localSccmsApiGetDynamicCategoryItemListTask.setIsUiSafe(false);
    this.httpApiEngine.addTask(localSccmsApiGetDynamicCategoryItemListTask);
    return localSccmsApiGetDynamicCategoryItemListTask;
  }

  public ServerApiTask ApiGetStaticCategoryItemList(String paramString, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt, SccmsApiGetStaticCategoryItemListTask.ISccmsApiGetStaticCategoryItemListTaskListener paramISccmsApiGetStaticCategoryItemListTaskListener)
  {
    return ApiGetStaticCategoryItemList(paramString, paramInt1, paramInt2, paramInt3, paramArrayOfInt, false, Long.valueOf(1800000L), paramISccmsApiGetStaticCategoryItemListTaskListener);
  }

  public ServerApiTask ApiGetStaticCategoryItemList(String paramString, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt, boolean paramBoolean, Long paramLong, final SccmsApiGetStaticCategoryItemListTask.ISccmsApiGetStaticCategoryItemListTaskListener paramISccmsApiGetStaticCategoryItemListTaskListener)
  {
    SccmsApiGetStaticCategoryItemListTask localSccmsApiGetStaticCategoryItemListTask = new SccmsApiGetStaticCategoryItemListTask(paramString, paramInt1, paramInt2, paramInt3, paramArrayOfInt);
    localSccmsApiGetStaticCategoryItemListTask.setListener(new SccmsApiGetStaticCategoryItemListTask.ISccmsApiGetStaticCategoryItemListTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.16.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final LinkedHashMap<String, CategoryPosterList> paramAnonymousLinkedHashMap, final MediaAssetsInfo paramAnonymousMediaAssetsInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.16.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousLinkedHashMap, paramAnonymousMediaAssetsInfo);
          }
        });
      }
    });
    localSccmsApiGetStaticCategoryItemListTask.setForceUpdate(paramBoolean);
    if (paramLong != null)
      localSccmsApiGetStaticCategoryItemListTask.setCacheLife(paramLong.longValue());
    localSccmsApiGetStaticCategoryItemListTask.setIsUiSafe(false);
    this.httpApiEngine.addTask(localSccmsApiGetStaticCategoryItemListTask);
    return localSccmsApiGetStaticCategoryItemListTask;
  }

  public int ApiRequestVideoPlay(String paramString1, int paramInt1, String paramString2, String paramString3, int paramInt2, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, final SccmsApiRequestVideoPlayTask.ISccmsApiRequestVideoPlayTaskListener paramISccmsApiRequestVideoPlayTaskListener)
  {
    SccmsApiRequestVideoPlayTask localSccmsApiRequestVideoPlayTask = new SccmsApiRequestVideoPlayTask(paramString1, paramInt1, paramString2, paramString3, paramInt2, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9);
    localSccmsApiRequestVideoPlayTask.setListener(new SccmsApiRequestVideoPlayTask.ISccmsApiRequestVideoPlayTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.22.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final PlayInfo paramAnonymousPlayInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.22.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousPlayInfo);
          }
        });
      }
    });
    localSccmsApiRequestVideoPlayTask.setIsUiSafe(true);
    return this.httpApiEngine.addTask(localSccmsApiRequestVideoPlayTask);
  }

  public int ApiRequestVideoPlay(String paramString1, int paramInt1, String paramString2, String paramString3, int paramInt2, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, final SccmsApiRequestVideoPlayTask.ISccmsApiRequestVideoPlayTaskListener paramISccmsApiRequestVideoPlayTaskListener)
  {
    SccmsApiRequestVideoPlayTask localSccmsApiRequestVideoPlayTask = new SccmsApiRequestVideoPlayTask(paramString1, paramInt1, paramString2, paramString3, paramInt2, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10);
    localSccmsApiRequestVideoPlayTask.setListener(new SccmsApiRequestVideoPlayTask.ISccmsApiRequestVideoPlayTaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.23.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final PlayInfo paramAnonymousPlayInfo)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.23.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousPlayInfo);
          }
        });
      }
    });
    localSccmsApiRequestVideoPlayTask.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiRequestVideoPlayTask);
  }

  public int ApiRequestVideoPlayV2(String paramString1, int paramInt1, String paramString2, String paramString3, int paramInt2, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, final SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener paramISccmsApiRequestVideoPlayV2TaskListener)
  {
    SccmsApiRequestVideoPlayV2Task localSccmsApiRequestVideoPlayV2Task = new SccmsApiRequestVideoPlayV2Task(paramString1, paramInt1, paramString2, paramString3, paramInt2, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12);
    localSccmsApiRequestVideoPlayV2Task.setListener(new SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.25.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final PlayInfoV2 paramAnonymousPlayInfoV2)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.25.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousPlayInfoV2);
          }
        });
      }
    });
    localSccmsApiRequestVideoPlayV2Task.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiRequestVideoPlayV2Task);
  }

  public int ApiRequestVideoPlayV2(String paramString1, int paramInt1, String paramString2, String paramString3, String paramString4, int paramInt2, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener paramISccmsApiRequestVideoPlayV2TaskListener)
  {
    return ApiRequestVideoPlayV2(paramString1, paramInt1, paramString2, paramString3, paramString4, paramInt2, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, "", "", paramISccmsApiRequestVideoPlayV2TaskListener);
  }

  public int ApiRequestVideoPlayV2(String paramString1, int paramInt1, String paramString2, String paramString3, String paramString4, int paramInt2, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, final SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener paramISccmsApiRequestVideoPlayV2TaskListener)
  {
    SccmsApiRequestVideoPlayV2Task localSccmsApiRequestVideoPlayV2Task = new SccmsApiRequestVideoPlayV2Task(paramString1, paramInt1, paramString2, paramString3, paramString4, paramInt2, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, paramString13, paramString14);
    localSccmsApiRequestVideoPlayV2Task.setListener(new SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener()
    {
      public void onError(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.24.this.val$lsr.onError(paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
          }
        });
      }

      public void onSuccess(final ServerApiTaskInfo paramAnonymousServerApiTaskInfo, final PlayInfoV2 paramAnonymousPlayInfoV2)
      {
        ServerApiManager.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            ServerApiManager.24.this.val$lsr.onSuccess(paramAnonymousServerApiTaskInfo, paramAnonymousPlayInfoV2);
          }
        });
      }
    });
    localSccmsApiRequestVideoPlayV2Task.setIsUiSafe(false);
    return this.httpApiEngine.addTask(localSccmsApiRequestVideoPlayV2Task);
  }

  public SCHttpApiEngine engine()
  {
    return this.httpApiEngine;
  }

  public boolean init()
  {
    this.httpApiEngine = new SCHttpApiEngine();
    this.httpApiEngine.init(true);
    return true;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.server.api.manage.ServerApiManager
 * JD-Core Version:    0.6.2
 */