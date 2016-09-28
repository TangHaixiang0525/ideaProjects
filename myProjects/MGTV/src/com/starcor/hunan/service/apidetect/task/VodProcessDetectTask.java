package com.starcor.hunan.service.apidetect.task;

import com.starcor.core.domain.CategoryItem;
import com.starcor.core.domain.FilmItem;
import com.starcor.core.domain.FilmListItem;
import com.starcor.core.domain.FilmListPageInfo;
import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.domain.MediaInfo;
import com.starcor.core.domain.MetadataGoup;
import com.starcor.core.domain.PlayInfo;
import com.starcor.core.domain.PlayInfoV2;
import com.starcor.core.domain.UserAuth;
import com.starcor.core.domain.UserAuthV2;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.epgapi.params.GetUserAuthV2Params;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.DialogActivity;
import com.starcor.hunan.DialogActivity.MediaAssetIdType;
import com.starcor.hunan.interfaces.ErrorCallBack;
import com.starcor.hunan.interfaces.SuccessCallBack;
import com.starcor.hunan.service.CollectAndPlayListLogic;
import com.starcor.hunan.service.apidetect.server.ApiDetectServerApiManager;
import com.starcor.hunan.service.apidetect.task.subtask.ApiDetectSccmsN3A2GetEpgDataTask.IApiDetectSccmsApiN3A2GetEpgDataTaskListener;
import com.starcor.sccms.api.SccmsApiGetAssetsInfoByVideoIdTask;
import com.starcor.sccms.api.SccmsApiGetAssetsInfoByVideoIdTask.ISccmsApiGetAssetsInfoByVideoIdTaskListener;
import com.starcor.sccms.api.SccmsApiGetMediaAssetsInfoTask;
import com.starcor.sccms.api.SccmsApiGetMediaAssetsInfoTask.ISccmsApiGetMediaAssetsInfoTaskListener;
import com.starcor.sccms.api.SccmsApiGetRelevantFilmsTask;
import com.starcor.sccms.api.SccmsApiGetRelevantFilmsTask.ISccmsApiGetRelevantFilmsTaskListener;
import com.starcor.sccms.api.SccmsApiGetUserAuthTask.ISccmsApiGetUserAuthTaskListener;
import com.starcor.sccms.api.SccmsApiGetUserAuthV2Task;
import com.starcor.sccms.api.SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoIndexListTask;
import com.starcor.sccms.api.SccmsApiGetVideoIndexListTask.ISccmsApiGetVideoIndexListTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoInfoV2Task;
import com.starcor.sccms.api.SccmsApiGetVideoInfoV2Task.ISccmsApiGetVideoInfoV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoListTask;
import com.starcor.sccms.api.SccmsApiGetVideoListTask.ISccmsApiGetVideoListTaskListener;
import com.starcor.sccms.api.SccmsApiRequestVideoPlayTask.ISccmsApiRequestVideoPlayTaskListener;
import com.starcor.sccms.api.SccmsApiRequestVideoPlayV2Task;
import com.starcor.sccms.api.SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.util.ArrayList;
import java.util.Iterator;

public class VodProcessDetectTask extends ApiProcessDetectTask
{
  public static final String TAG = VodProcessDetectTask.class.getSimpleName();
  private SuccessCallBack<FilmListPageInfo> indexCallback = new SuccessCallBack()
  {
    public void getDataError(String paramAnonymousString, int paramAnonymousInt)
    {
    }

    public void getDataSuccess(FilmListPageInfo paramAnonymousFilmListPageInfo)
    {
      Logger.i(VodProcessDetectTask.TAG, "indexCallback getDataSuccess");
      VodProcessDetectTask.this.setResultBodyByTaskId(VodProcessDetectTask.this.mVODProcessTaskId, paramAnonymousFilmListPageInfo.toString());
      VodProcessDetectTask.this.setResultHeaderByTaskId(VodProcessDetectTask.this.mVODProcessTaskId, SccmsApiGetAssetsInfoByVideoIdTask.class.getSimpleName(), "N3_A_D_11", "请求影片媒资信息", "nns_video_id=" + VodProcessDetectTask.this.videoId);
      int i = ApiDetectServerApiManager.i().APIGetAssetsInfoByVideoId(VodProcessDetectTask.this.videoId, new VodProcessDetectTask.SccmsApiGetAssetsInfoByVideoId(VodProcessDetectTask.this, null));
      VodProcessDetectTask.this.setNextTaskIdByCurrentTaskId(VodProcessDetectTask.this.mVODProcessTaskId, i);
    }
  };
  private CollectAndPlayListLogic mCollectAndPlayListLogic = null;
  private ErrorCallBack mErrorCallBack = new ErrorCallBack()
  {
    public void getDataError(String paramAnonymousString, int paramAnonymousInt)
    {
      Logger.i(VodProcessDetectTask.TAG, "mErrorCallBack getDataError: code=" + paramAnonymousInt + " des=" + paramAnonymousString);
      VodProcessDetectTask.this.setResultBodyByTaskIdOnError(VodProcessDetectTask.this.mVODProcessTaskId, "mErrorCallBack getDataError: code=" + paramAnonymousInt + " des=" + paramAnonymousString + "\n================================================\n\n");
    }
  };
  private VideoInfo mVideoInfo = null;
  private String nns_category_id_vod = "";
  private String nns_packet_id_vod = "";
  private String videoId = "";
  private int videoType = 0;

  protected void runVodProcess()
  {
    setResultHeaderByTaskId(this.mVODProcessTaskId, "SccmsApiN3A2GetEpgDataTask", "N3_A_2", "获取 n3_a_i EPG总入口信息", "无");
    this.mVODProcessTaskId = ApiDetectServerApiManager.i().APIN3A2GetEpgData(new SccmsApiN3A2GetEpgDataTaskListener(null));
    Logger.i(TAG, "runVodProcess mVODProcessTaskId=" + this.mVODProcessTaskId);
  }

  private final class SccmsApiGetAssetsInfoByVideoId
    implements SccmsApiGetAssetsInfoByVideoIdTask.ISccmsApiGetAssetsInfoByVideoIdTaskListener
  {
    private SccmsApiGetAssetsInfoByVideoId()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiGetAssetsInfoByVideoId onError: taskInfo.getTaskId()=" + i);
      VodProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, MediaAssetsInfo paramMediaAssetsInfo)
    {
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiGetAssetsInfoByVideoId onSuccess: taskInfo.getTaskId()=" + paramServerApiTaskInfo.getTaskId());
      VodProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramMediaAssetsInfo.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo));
      VodProcessDetectTask.this.setResultHeaderByTaskId(paramServerApiTaskInfo.getTaskId(), SccmsApiGetRelevantFilmsTask.class.getSimpleName(), "N3_A_D_9", "获取某部影片相关的推荐影片", "videoId=" + VodProcessDetectTask.this.videoId + " videoType=" + VodProcessDetectTask.this.videoType + " packetId=" + VodProcessDetectTask.this.nns_packet_id_vod + " categoryId=" + VodProcessDetectTask.this.nns_category_id_vod + " pageIndex=0 pageSize=6");
      int i = ApiDetectServerApiManager.i().APIGetRelevantFilms(VodProcessDetectTask.this.videoId, VodProcessDetectTask.this.videoType, VodProcessDetectTask.this.nns_packet_id_vod, VodProcessDetectTask.this.nns_category_id_vod, 0, 6, new VodProcessDetectTask.SccmsApiGetRelevantFilms(VodProcessDetectTask.this, null));
      VodProcessDetectTask.this.setNextTaskIdByCurrentTaskId(paramServerApiTaskInfo.getTaskId(), i);
    }
  }

  private class SccmsApiGetMediaAssetsInfoTaskListener
    implements SccmsApiGetMediaAssetsInfoTask.ISccmsApiGetMediaAssetsInfoTaskListener
  {
    private SccmsApiGetMediaAssetsInfoTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiGetMediaAssetsInfoTaskListener onError: taskInfo.getTaskId()=" + i);
      VodProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, MediaAssetsInfo paramMediaAssetsInfo)
    {
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiGetMediaAssetsInfoTaskListener onSuccess: taskInfo.getTaskId()=" + paramServerApiTaskInfo.getTaskId());
      if (paramServerApiTaskInfo.getTaskId() == VodProcessDetectTask.this.mVODProcessTaskId)
      {
        String str1 = "";
        if ((paramMediaAssetsInfo.cList != null) && (paramMediaAssetsInfo.cList.size() > 0))
          str1 = ((CategoryItem)paramMediaAssetsInfo.cList.get(0)).id;
        VodProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramMediaAssetsInfo.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo));
        VodProcessDetectTask.this.setResultHeaderByTaskId(paramServerApiTaskInfo.getTaskId(), SccmsApiGetVideoListTask.class.getSimpleName(), "N3_A_D_2", "获取影片列表", "nns_packet_id=" + paramMediaAssetsInfo.package_id + "nns_category_id=" + str1 + "nns_sort_type=time" + "nns_page_index=0" + "nns_page_size=40");
        VodProcessDetectTask.access$402(VodProcessDetectTask.this, paramMediaAssetsInfo.package_id);
        VodProcessDetectTask.access$502(VodProcessDetectTask.this, str1);
        Logger.i(VodProcessDetectTask.TAG, "SccmsApiGetMediaAssetsInfoTaskListener onSuccess: packet_id=" + paramMediaAssetsInfo.package_id + " category_id=" + str1);
        ApiDetectServerApiManager localApiDetectServerApiManager = ApiDetectServerApiManager.i();
        String str2 = paramMediaAssetsInfo.package_id;
        VodProcessDetectTask.SccmsGetVideoListTaskListener localSccmsGetVideoListTaskListener = new VodProcessDetectTask.SccmsGetVideoListTaskListener(VodProcessDetectTask.this, null);
        int i = localApiDetectServerApiManager.APIGetVideoList(str2, str1, "time", 0, 40, localSccmsGetVideoListTaskListener);
        VodProcessDetectTask.this.setNextTaskIdByCurrentTaskId(paramServerApiTaskInfo.getTaskId(), i);
      }
    }
  }

  private class SccmsApiGetRelevantFilms
    implements SccmsApiGetRelevantFilmsTask.ISccmsApiGetRelevantFilmsTaskListener
  {
    private SccmsApiGetRelevantFilms()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiGetRelevantFilms onError: taskInfo.getTaskId()=" + i);
      VodProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, FilmItem paramFilmItem)
    {
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiGetRelevantFilms onSuccess: taskInfo.getTaskId()=" + paramServerApiTaskInfo.getTaskId());
      VodProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramFilmItem.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo));
      VideoInfo localVideoInfo = VodProcessDetectTask.this.mVideoInfo;
      int i = 0;
      int j = 0;
      if (localVideoInfo != null)
      {
        ArrayList localArrayList1 = VodProcessDetectTask.this.mVideoInfo.indexList;
        i = 0;
        j = 0;
        if (localArrayList1 != null)
        {
          int m = VodProcessDetectTask.this.mVideoInfo.indexList.size();
          i = 0;
          j = 0;
          if (m > 0)
          {
            VideoIndex localVideoIndex = (VideoIndex)VodProcessDetectTask.this.mVideoInfo.indexList.get(0);
            i = 0;
            j = 0;
            if (localVideoIndex != null)
            {
              ArrayList localArrayList2 = localVideoIndex.mediaInfo;
              i = 0;
              j = 0;
              if (localArrayList2 != null)
              {
                int n = localVideoIndex.mediaInfo.size();
                i = 0;
                j = 0;
                if (n > 0)
                {
                  Iterator localIterator = ((VideoIndex)VodProcessDetectTask.this.mVideoInfo.indexList.get(0)).mediaInfo.iterator();
                  while (localIterator.hasNext())
                    if ("hd".equals(((MediaInfo)localIterator.next()).type))
                      i = 1;
                    else
                      j = 1;
                }
              }
            }
          }
        }
      }
      String str1;
      String str2;
      if (paramServerApiTaskInfo.getTaskId() == VodProcessDetectTask.this.mVODProcessTaskId)
      {
        str1 = GlobalLogic.getInstance().getQuality();
        if ((!"std".equals(str1)) || (j == 0))
          break label458;
        str2 = "STD";
      }
      while (true)
      {
        VodProcessDetectTask.this.setResultHeaderByTaskId(paramServerApiTaskInfo.getTaskId(), SccmsApiRequestVideoPlayV2Task.class.getSimpleName(), "N50_A_2", "新3A请求影片播放串", "nns_video_id=" + VodProcessDetectTask.this.videoId + " nns_video_type=" + VodProcessDetectTask.this.videoType);
        int k = ApiDetectServerApiManager.i().ApiRequestVideoPlayV2(VodProcessDetectTask.this.mVideoInfo.videoId, VodProcessDetectTask.this.mVideoInfo.videoType, VodProcessDetectTask.this.mVideoInfo.packageId, VodProcessDetectTask.this.mVideoInfo.categoryId, 0, "", str2, "", "", "", "", new VodProcessDetectTask.SccmsApiRequestVideoPlayV2TaskListener(VodProcessDetectTask.this));
        VodProcessDetectTask.this.setNextTaskIdByCurrentTaskId(paramServerApiTaskInfo.getTaskId(), k);
        return;
        label458: if (("hd".equals(str1)) && (i != 0))
          str2 = "HD";
        else
          str2 = "STD";
      }
    }
  }

  private class SccmsApiGetUserAuthTaskListener
    implements SccmsApiGetUserAuthTask.ISccmsApiGetUserAuthTaskListener
  {
    private SccmsApiGetUserAuthTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiGetUserAuthTaskListener onError: taskInfo.getTaskId()=" + i);
      VodProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserAuth paramUserAuth)
    {
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiGetUserAuthTaskListener onSuccess: taskInfo.getTaskId()=" + paramServerApiTaskInfo.getTaskId());
      VodProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramUserAuth.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo));
      if (paramServerApiTaskInfo.getTaskId() == VodProcessDetectTask.this.mVODProcessTaskId)
      {
        VodProcessDetectTask.this.setResultHeaderByTaskId(paramServerApiTaskInfo.getTaskId(), SccmsApiGetVideoInfoV2Task.class.getSimpleName(), "N3_A_A_14", "请求影片详情", "nns_video_id=" + VodProcessDetectTask.this.videoId + " nns_video_type=" + VodProcessDetectTask.this.videoType);
        int i = ApiDetectServerApiManager.i().APIGetVideoInfoV2(VodProcessDetectTask.this.videoId, VodProcessDetectTask.this.videoType, new VodProcessDetectTask.SccmsApiGetVideoInfoV2TaskListener(VodProcessDetectTask.this, null));
        VodProcessDetectTask.this.setNextTaskIdByCurrentTaskId(paramServerApiTaskInfo.getTaskId(), i);
      }
    }
  }

  private class SccmsApiGetUserAuthV2TaskListener
    implements SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener
  {
    private SccmsApiGetUserAuthV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiGetUserAuthV2TaskListener onError: taskInfo.getTaskId()=" + i);
      VodProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserAuthV2 paramUserAuthV2)
    {
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiGetUserAuthV2TaskListener onSuccess: taskInfo.getTaskId()=" + paramServerApiTaskInfo.getTaskId());
      VodProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramUserAuthV2.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo));
      VodProcessDetectTask.this.setResultHeaderByTaskId(paramServerApiTaskInfo.getTaskId(), SccmsApiGetVideoInfoV2Task.class.getSimpleName(), "N3_A_A_14", "请求影片详情", "nns_video_id=" + VodProcessDetectTask.this.videoId + " nns_video_type=" + VodProcessDetectTask.this.videoType);
      int i = ApiDetectServerApiManager.i().APIGetVideoInfoV2(VodProcessDetectTask.this.videoId, VodProcessDetectTask.this.videoType, new VodProcessDetectTask.SccmsApiGetVideoInfoV2TaskListener(VodProcessDetectTask.this, null));
      VodProcessDetectTask.this.setNextTaskIdByCurrentTaskId(paramServerApiTaskInfo.getTaskId(), i);
    }
  }

  private class SccmsApiGetVideoInfoV2TaskListener
    implements SccmsApiGetVideoInfoV2Task.ISccmsApiGetVideoInfoV2TaskListener
  {
    private SccmsApiGetVideoInfoV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiGetVideoInfoV2TaskListener onError: taskInfo.getTaskId()=" + i);
      VodProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, VideoInfo paramVideoInfo)
    {
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiGetVideoInfoV2TaskListener onSuccess: taskInfo.getTaskId()=" + paramServerApiTaskInfo.getTaskId());
      VodProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramVideoInfo.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo));
      VodProcessDetectTask.access$1002(VodProcessDetectTask.this, paramVideoInfo);
      VodProcessDetectTask.this.setResultHeaderByTaskId(paramServerApiTaskInfo.getTaskId(), SccmsApiGetVideoIndexListTask.class.getSimpleName(), "N3_A_A_12", "请求影片剧集列表信息", "nns_video_id=" + VodProcessDetectTask.this.videoId + " nns_video_type=" + VodProcessDetectTask.this.videoType);
      ServerApiManager.i().APIGetVideoIndexList(VodProcessDetectTask.this.videoId, VodProcessDetectTask.this.videoType, 0, 1000, false, new SccmsApiGetVideoIndexListTask.ISccmsApiGetVideoIndexListTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, FilmListPageInfo paramAnonymousFilmListPageInfo)
        {
        }
      });
    }
  }

  private class SccmsApiN3A2GetEpgDataTaskListener
    implements ApiDetectSccmsN3A2GetEpgDataTask.IApiDetectSccmsApiN3A2GetEpgDataTaskListener
  {
    private SccmsApiN3A2GetEpgDataTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiN3A2GetEpgDataTaskListener onError: taskInfo.getTaskId()=" + i);
      VodProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<MetadataGoup> paramArrayList)
    {
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiN3A2GetEpgDataTaskListener onSuccess: taskInfo.getTaskId()=" + paramServerApiTaskInfo.getTaskId());
      if (paramServerApiTaskInfo.getTaskId() == VodProcessDetectTask.this.mVODProcessTaskId)
      {
        VodProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramArrayList.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo));
        String str = DialogActivity.getMediaAssetIdByType(DialogActivity.MediaAssetIdType.MEDIA_ASSET_ID_TYPE_MOVIE);
        Logger.i(VodProcessDetectTask.TAG, "nns_packet_id=" + str);
        VodProcessDetectTask.this.setResultHeaderByTaskId(paramServerApiTaskInfo.getTaskId(), SccmsApiGetMediaAssetsInfoTask.class.getSimpleName(), "N3_A_D_1", "请求媒资信息", "nns_packet_id=" + str);
        int i = ApiDetectServerApiManager.i().APIGetMediaAssetsInfo(str, new VodProcessDetectTask.SccmsApiGetMediaAssetsInfoTaskListener(VodProcessDetectTask.this, null));
        Logger.i(VodProcessDetectTask.TAG, "SccmsApiN3A2GetEpgDataTaskListener vod onSuccess: nextTaskId=" + i);
        VodProcessDetectTask.this.setNextTaskIdByCurrentTaskId(paramServerApiTaskInfo.getTaskId(), i);
      }
    }
  }

  private class SccmsApiRequestVideoPlayTaskListener
    implements SccmsApiRequestVideoPlayTask.ISccmsApiRequestVideoPlayTaskListener
  {
    private SccmsApiRequestVideoPlayTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiRequestVideoPlayTaskListener onError: taskInfo.getTaskId()=" + i);
      VodProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, PlayInfo paramPlayInfo)
    {
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiRequestVideoPlayTaskListener onSuccess taskId=" + paramServerApiTaskInfo.getTaskId());
      VodProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramPlayInfo.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
      Logger.i(VodProcessDetectTask.TAG, "finish running process: " + VodProcessDetectTask.this.getCurrentApiProcessTypeByTaskId(paramServerApiTaskInfo.getTaskId()));
      VodProcessDetectTask.this.runCallbackByTaskId(paramServerApiTaskInfo.getTaskId());
      Logger.i(VodProcessDetectTask.TAG, VodProcessDetectTask.this.getApiTimeFormatString(false, "" + VodProcessDetectTask.this.getCurrentApiProcessTypeByTaskId(paramServerApiTaskInfo.getTaskId())));
    }
  }

  class SccmsApiRequestVideoPlayV2TaskListener
    implements SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener
  {
    SccmsApiRequestVideoPlayV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiRequestVideoPlayV2TaskListener onError: taskInfo.getTaskId()=" + i);
      VodProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, PlayInfoV2 paramPlayInfoV2)
    {
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiRequestVideoPlayTaskListener onSuccess taskId=" + paramServerApiTaskInfo.getTaskId());
      VodProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramPlayInfoV2.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiRequestVideoPlayTaskListener onSuccess taskId=" + paramServerApiTaskInfo.getTaskId());
      Logger.i(VodProcessDetectTask.TAG, "finish running process: " + VodProcessDetectTask.this.getCurrentApiProcessTypeByTaskId(paramServerApiTaskInfo.getTaskId()));
      VodProcessDetectTask.this.runCallbackByTaskId(paramServerApiTaskInfo.getTaskId());
      Logger.i(VodProcessDetectTask.TAG, VodProcessDetectTask.this.getApiTimeFormatString(false, "" + VodProcessDetectTask.this.getCurrentApiProcessTypeByTaskId(paramServerApiTaskInfo.getTaskId())));
    }
  }

  private class SccmsGetVideoListTaskListener
    implements SccmsApiGetVideoListTask.ISccmsApiGetVideoListTaskListener
  {
    private SccmsGetVideoListTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(VodProcessDetectTask.TAG, "SccmsApiGetVideoLabelTypeTaskListener onError: taskInfo.getTaskId()=" + i);
      VodProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, FilmItem paramFilmItem)
    {
      Logger.i(VodProcessDetectTask.TAG, "SccmsGetVideoListTaskListener onSuccess: taskInfo.getTaskId()=" + paramServerApiTaskInfo.getTaskId());
      VodProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramFilmItem.toString() + VodProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo));
      int j;
      if ((paramFilmItem.filmList != null) && (paramFilmItem.filmList.size() > 0))
        j = paramFilmItem.filmList.size();
      for (int k = 0; ; k++)
        if (k < j)
        {
          FilmListItem localFilmListItem = (FilmListItem)paramFilmItem.filmList.get(k);
          if (localFilmListItem.billing == 0)
          {
            Logger.i(VodProcessDetectTask.TAG, "SccmsGetVideoListTaskListener onSuccess: find item=" + localFilmListItem.toString());
            VodProcessDetectTask.access$002(VodProcessDetectTask.this, localFilmListItem.video_id);
            VodProcessDetectTask.access$702(VodProcessDetectTask.this, localFilmListItem.video_type);
          }
        }
        else
        {
          GlobalLogic.getInstance().getUserId();
          GetUserAuthV2Params localGetUserAuthV2Params = new GetUserAuthV2Params(VodProcessDetectTask.this.videoId, VodProcessDetectTask.this.videoType + "");
          VodProcessDetectTask.this.setResultHeaderByTaskId(paramServerApiTaskInfo.getTaskId(), SccmsApiGetUserAuthV2Task.class.getSimpleName(), "N50_A_1", "新的3A详情鉴权接口", " nns_video_id=" + VodProcessDetectTask.this.videoId + " videoType=" + VodProcessDetectTask.this.videoType + " GetUserAuthV2Params=" + localGetUserAuthV2Params.toString());
          int i = ApiDetectServerApiManager.i().APIGetUserAuthV2(localGetUserAuthV2Params, new VodProcessDetectTask.SccmsApiGetUserAuthV2TaskListener(VodProcessDetectTask.this, null));
          VodProcessDetectTask.this.setNextTaskIdByCurrentTaskId(paramServerApiTaskInfo.getTaskId(), i);
          return;
        }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.apidetect.task.VodProcessDetectTask
 * JD-Core Version:    0.6.2
 */