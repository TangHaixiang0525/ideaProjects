package com.starcor.player;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.starcor.core.domain.AuthState;
import com.starcor.core.domain.FilmListItem;
import com.starcor.core.domain.FilmListPageInfo;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.SpecialTopicPkgCntLstStruct;
import com.starcor.core.domain.UserAuth;
import com.starcor.core.domain.UserAuthV2;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.epgapi.params.GetUserAuthV2Params;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.DomainUtils;
import com.starcor.core.utils.Logger;
import com.starcor.sccms.api.SccmsApiGetUserAuthTask.ISccmsApiGetUserAuthTaskListener;
import com.starcor.sccms.api.SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoIndexListTask.ISccmsApiGetVideoIndexListTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoInfoV2Task.ISccmsApiGetVideoInfoV2TaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.util.ArrayList;
import java.util.List;

public class SpecialContinuousPlayingLogic
{
  private static final int MSG_GET_USER_AUTH = 1;
  protected static final int PAGE_SIZE = 10000;
  private static final String TAG = SpecialContinuousPlayingLogic.class.getSimpleName();
  private boolean checkAuthFinish = false;
  private int currentCheckAuthTaskId = -1;
  private FilmListItem currentFilmItem = null;
  private int currentGetVideoInfoTaskId = -1;
  private int currentGetVideoListTaskId = -1;
  private final Handler mHandler = new Handler(Looper.getMainLooper())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
      case 1:
      }
      while (true)
      {
        SpecialContinuousPlayingLogic.this.checkPushPlayParam();
        return;
        SpecialContinuousPlayingLogic.access$002(SpecialContinuousPlayingLogic.this, SpecialContinuousPlayingLogic.this.porcessCheckAuthMsg(paramAnonymousMessage));
      }
    }
  };
  private PlayerIntentParams nextParams;
  private boolean notifyStart = false;
  private RequestNextPlayParamsCallBack requestCallBack;
  private List<SpecialTopicPkgCntLstStruct> specialItemList = new ArrayList();
  private boolean videoInfoFinish = false;
  private boolean videoListFinish = false;

  public SpecialContinuousPlayingLogic(List<SpecialTopicPkgCntLstStruct> paramList)
  {
    if (paramList == null)
      return;
    this.specialItemList = paramList;
    Logger.i(TAG, "init() specialItemList size:" + paramList.size());
  }

  private void checkPushPlayParam()
  {
    if ((this.checkAuthFinish) && (this.videoListFinish) && (this.videoInfoFinish) && (this.requestCallBack != null))
      this.requestCallBack.pushPlayParams(this.nextParams);
  }

  private void clearnLastTask()
  {
    this.currentFilmItem = null;
    this.nextParams = null;
    this.currentCheckAuthTaskId = -1;
    this.currentGetVideoListTaskId = -1;
    this.currentGetVideoInfoTaskId = -1;
    this.checkAuthFinish = false;
    this.videoListFinish = false;
    this.notifyStart = false;
    Logger.i(TAG, "special clearnLastTask");
  }

  private void createPlayerParams(String paramString, List<VideoIndex> paramList, VideoInfo paramVideoInfo)
  {
    if (this.nextParams == null)
    {
      this.nextParams = new PlayerIntentParams();
      this.nextParams.nns_index = 0;
      this.nextParams.mode = 2;
    }
    if (paramString == null)
    {
      Long localLong = Long.valueOf(GlobalEnv.getInstance().getFreePlayTimePercent());
      this.nextParams.buyUrl = paramString;
      this.nextParams.freePlayTime = localLong.longValue();
      Logger.i(TAG, "special createPlayerParams UserAuth");
    }
    if (paramList != null)
    {
      this.nextParams.nns_mediaIndexList = paramList;
      if (paramList.size() > 0)
        this.nextParams.subfix_title = ((VideoIndex)paramList.get(0)).name;
      Logger.i(TAG, "special createPlayerParams videoIndexList");
    }
    if (paramVideoInfo != null)
    {
      this.nextParams.nns_videoInfo = paramVideoInfo;
      Logger.i(TAG, "special createPlayerParams videoInfo");
    }
  }

  private FilmListItem getNextItem(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return null;
    if (this.specialItemList.size() == 0)
      return null;
    for (int i = -1 + this.specialItemList.size(); ; i--)
      if (i >= 0)
      {
        if (paramString.equals(((SpecialTopicPkgCntLstStruct)this.specialItemList.get(i)).video_id))
        {
          Logger.i(TAG, "special remove name:" + ((SpecialTopicPkgCntLstStruct)this.specialItemList.get(i)).name);
          this.specialItemList.remove(i);
        }
      }
      else
      {
        if (this.specialItemList.size() <= 0)
          break;
        return DomainUtils.specialTopicPkgCntLstStruct2FilmListItem((SpecialTopicPkgCntLstStruct)this.specialItemList.get(0));
      }
    return null;
  }

  private void porcessGetVideoIndexList(FilmListPageInfo paramFilmListPageInfo)
  {
    try
    {
      createPlayerParams(null, paramFilmListPageInfo.getFilmInfo(), null);
      return;
    }
    catch (Exception localException)
    {
      Logger.i(TAG, "special porcessGetVideoIndexList FilmListInfo exception");
      processDataError();
    }
  }

  private void processDataError()
  {
    clearnLastTask();
    if (this.requestCallBack != null)
    {
      this.requestCallBack.pushPlayParams(null);
      Logger.i(TAG, "special processDataError");
    }
  }

  private void processGetVideoInfo(VideoInfo paramVideoInfo)
  {
    try
    {
      createPlayerParams(null, null, paramVideoInfo);
      return;
    }
    catch (Exception localException)
    {
      Logger.i(TAG, "special porcessGetVideoIndexList VideoInfo exception");
      processDataError();
    }
  }

  public PlayerIntentParams getNextPlayParams()
  {
    return this.nextParams;
  }

  public void getNextPlayParams(RequestNextPlayParamsCallBack paramRequestNextPlayParamsCallBack)
  {
    if (!this.notifyStart)
      Logger.e(TAG, "special 未执行notifyStart,就请求下一影片数据.");
    if ((this.nextParams != null) && (paramRequestNextPlayParamsCallBack != null))
    {
      paramRequestNextPlayParamsCallBack.pushPlayParams(this.nextParams);
      clearnLastTask();
      Logger.i(TAG, "special getNextPlayParams nextParams:" + this.nextParams.toString());
      return;
    }
    Logger.i(TAG, "special getNextPlayParams nextParams is null");
    this.requestCallBack = paramRequestNextPlayParamsCallBack;
  }

  public void notifyQuitPlay()
  {
    Logger.i(TAG, "special notifyQuitPlay");
    clearnLastTask();
  }

  public void notifyStartPlay(String paramString)
  {
    this.notifyStart = true;
    clearnLastTask();
    FilmListItem localFilmListItem = getNextItem(paramString);
    if (this.currentFilmItem == localFilmListItem)
    {
      Logger.i(TAG, "special notifyStartPlay 任务已经执行过");
      return;
    }
    this.currentFilmItem = localFilmListItem;
    if (this.currentFilmItem == null)
    {
      Logger.i(TAG, "special notifyStartPlay 没有可以续播的媒体!");
      return;
    }
    GlobalLogic.getInstance().getUserId();
    this.currentCheckAuthTaskId = ServerApiManager.i().APIGetUserAuthV2(new GetUserAuthV2Params(this.currentFilmItem.video_id, String.valueOf(this.currentFilmItem.video_type)), new SccmsApiGetUserAuthV2TaskListener());
    this.currentGetVideoListTaskId = ServerApiManager.i().APIGetVideoIndexList(this.currentFilmItem.video_id, this.currentFilmItem.video_type, 0, 10000, false, new SccmsApiGetVideoIndexListTask.ISccmsApiGetVideoIndexListTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.i(SpecialContinuousPlayingLogic.TAG, "special porcessGetVideoIndexList failed! " + paramAnonymousServerApiCommonError.toString());
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, FilmListPageInfo paramAnonymousFilmListPageInfo)
      {
        if (SpecialContinuousPlayingLogic.this.currentGetVideoListTaskId != paramAnonymousServerApiTaskInfo.getTaskId())
        {
          Logger.i(SpecialContinuousPlayingLogic.TAG, "special porcessGetVideoIndexList currentGetVideoListTaskId expired");
          return;
        }
        SpecialContinuousPlayingLogic.this.porcessGetVideoIndexList(paramAnonymousFilmListPageInfo);
        SpecialContinuousPlayingLogic.access$502(SpecialContinuousPlayingLogic.this, true);
        SpecialContinuousPlayingLogic.this.checkPushPlayParam();
      }
    });
    this.currentGetVideoInfoTaskId = ServerApiManager.i().APIGetVideoInfoV2(this.currentFilmItem.video_id, this.currentFilmItem.video_type, new SccmsApiGetVideoInfoV2Task.ISccmsApiGetVideoInfoV2TaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.i(SpecialContinuousPlayingLogic.TAG, "special processGetVideoInfoMsg failed! " + paramAnonymousServerApiCommonError.toString());
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, VideoInfo paramAnonymousVideoInfo)
      {
        if (SpecialContinuousPlayingLogic.this.currentGetVideoInfoTaskId != paramAnonymousServerApiTaskInfo.getTaskId())
        {
          Logger.i(SpecialContinuousPlayingLogic.TAG, "special processGetVideoInfoMsg currentGetVideoInfoTaskId expired");
          return;
        }
        SpecialContinuousPlayingLogic.this.processGetVideoInfo(paramAnonymousVideoInfo);
        SpecialContinuousPlayingLogic.access$802(SpecialContinuousPlayingLogic.this, true);
        SpecialContinuousPlayingLogic.this.checkPushPlayParam();
      }
    });
    Logger.i(TAG, "special notifyStartPlay apiRequest videoName:" + this.currentFilmItem.film_name);
  }

  public boolean porcessCheckAuthMsg(Message paramMessage)
  {
    int i = 1;
    if (this.currentCheckAuthTaskId != paramMessage.arg2)
    {
      Logger.i(TAG, "special porcessCheckAuthMsg currentCheckAuthTaskId expired");
      i = 0;
    }
    UserAuth localUserAuth;
    String str;
    do
    {
      return i;
      if (paramMessage.obj == null)
      {
        Logger.i(TAG, "special porcessCheckAuthMsg msg.obj is null");
        processDataError();
        return i;
      }
      try
      {
        localUserAuth = (UserAuth)paramMessage.obj;
        if (localUserAuth == null)
        {
          Logger.i(TAG, "special porcessCheckAuthMsg userAuth is null");
          processDataError();
          return i;
        }
      }
      catch (Exception localException)
      {
        Logger.i(TAG, "special porcessCheckAuthMsg exception");
        processDataError();
        return i;
      }
      str = localUserAuth.getOrder_url();
    }
    while (localUserAuth.getState() != i);
    if (str == null)
    {
      Logger.i(TAG, "special porcessCheckAuthMsg buyUrl is null");
      str = "";
    }
    Logger.i(TAG, "special porcessCheckAuthMsg do not have permission");
    createPlayerParams(str, null, null);
    return i;
  }

  protected boolean porcessGetVideoIndexListMsg(Message paramMessage)
  {
    if (this.currentGetVideoListTaskId != paramMessage.arg2)
    {
      Logger.i(TAG, "special porcessGetVideoIndexList currentGetVideoListTaskId expired");
      return false;
    }
    try
    {
      porcessGetVideoIndexList((FilmListPageInfo)paramMessage.obj);
      return true;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  protected boolean processGetVideoInfoMsg(Message paramMessage)
  {
    if (this.currentGetVideoInfoTaskId != paramMessage.arg2)
    {
      Logger.i(TAG, "special processGetVideoInfoMsg currentGetVideoInfoTaskId expired");
      return false;
    }
    try
    {
      processGetVideoInfo((VideoInfo)paramMessage.obj);
      return true;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public static abstract interface RequestNextPlayParamsCallBack
  {
    public abstract void pushPlayParams(PlayerIntentParams paramPlayerIntentParams);
  }

  class SccmsApiGetUserAuthTaskListener
    implements SccmsApiGetUserAuthTask.ISccmsApiGetUserAuthTaskListener
  {
    SccmsApiGetUserAuthTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i(SpecialContinuousPlayingLogic.TAG, "debug SccmsApiGetUserAuthTaskListener.onError() error:" + paramServerApiCommonError);
      SpecialContinuousPlayingLogic.this.checkPushPlayParam();
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserAuth paramUserAuth)
    {
      if (paramUserAuth == null)
      {
        Logger.i(SpecialContinuousPlayingLogic.TAG, "special porcessCheckAuthMsg msg.obj is null");
        SpecialContinuousPlayingLogic.this.processDataError();
        SpecialContinuousPlayingLogic.access$002(SpecialContinuousPlayingLogic.this, true);
        return;
      }
      Logger.i(SpecialContinuousPlayingLogic.TAG, "debug SccmsApiGetUserAuthTaskListener.onSuccess() result:" + paramUserAuth.toString());
      if (SpecialContinuousPlayingLogic.this.currentCheckAuthTaskId != paramServerApiTaskInfo.getTaskId())
      {
        Logger.i(SpecialContinuousPlayingLogic.TAG, "special porcessCheckAuthMsg currentCheckAuthTaskId expired");
        SpecialContinuousPlayingLogic.access$002(SpecialContinuousPlayingLogic.this, false);
      }
      String str = paramUserAuth.getOrder_url();
      if (paramUserAuth.getState() == 1)
      {
        if (str == null)
        {
          Logger.i(SpecialContinuousPlayingLogic.TAG, "special porcessCheckAuthMsg buyUrl is null");
          str = "";
        }
        Logger.i(SpecialContinuousPlayingLogic.TAG, "special porcessCheckAuthMsg do not have permission");
        SpecialContinuousPlayingLogic.this.createPlayerParams(str, null, null);
      }
      SpecialContinuousPlayingLogic.access$002(SpecialContinuousPlayingLogic.this, true);
      SpecialContinuousPlayingLogic.this.checkPushPlayParam();
    }
  }

  class SccmsApiGetUserAuthV2TaskListener
    implements SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener
  {
    SccmsApiGetUserAuthV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i(SpecialContinuousPlayingLogic.TAG, "debug SccmsApiGetUserAuthTaskListener.onError() error:" + paramServerApiCommonError);
      SpecialContinuousPlayingLogic.this.checkPushPlayParam();
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserAuthV2 paramUserAuthV2)
    {
      if ((paramUserAuthV2 == null) || (paramUserAuthV2.state == null))
      {
        Logger.i(SpecialContinuousPlayingLogic.TAG, "special porcessCheckAuthMsg msg.obj is null");
        SpecialContinuousPlayingLogic.this.processDataError();
        SpecialContinuousPlayingLogic.access$002(SpecialContinuousPlayingLogic.this, true);
        return;
      }
      Logger.i(SpecialContinuousPlayingLogic.TAG, "debug SccmsApiGetUserAuthTaskListener.onSuccess() result:" + paramUserAuthV2.toString());
      if (SpecialContinuousPlayingLogic.this.currentCheckAuthTaskId != paramServerApiTaskInfo.getTaskId())
      {
        Logger.i(SpecialContinuousPlayingLogic.TAG, "special porcessCheckAuthMsg currentCheckAuthTaskId expired");
        SpecialContinuousPlayingLogic.access$002(SpecialContinuousPlayingLogic.this, false);
      }
      if (paramUserAuthV2.state.state == 1);
      SpecialContinuousPlayingLogic.access$002(SpecialContinuousPlayingLogic.this, true);
      SpecialContinuousPlayingLogic.this.checkPushPlayParam();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.player.SpecialContinuousPlayingLogic
 * JD-Core Version:    0.6.2
 */