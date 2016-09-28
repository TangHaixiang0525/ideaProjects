package com.starcor.hunan.service;

import android.os.Handler;
import android.util.Log;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.domain.CollectListItem;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.UserCPLLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.interfaces.SuccessCallBack;
import com.starcor.sccms.api.SccmsApiGetCatchVideoRecordTask.ISccmsApiGetCatchVideoRecordTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;

public class DirtyTraceLogic extends Observable
{
  private static final String TAG = "DirtyTraceLogic";
  private static int TASK_INTERVAL = 7200000;
  private static DirtyTraceLogic mLogic;
  private boolean initialized = false;
  private Handler mHandler = new Handler();
  private refreshDirtyTracesTask refreshDirtyTracesTask;
  private List<CollectListItem> tracePlayList = new ArrayList();

  public static DirtyTraceLogic getInstance()
  {
    try
    {
      if (mLogic == null)
        mLogic = new DirtyTraceLogic();
      DirtyTraceLogic localDirtyTraceLogic = mLogic;
      return localDirtyTraceLogic;
    }
    finally
    {
    }
  }

  private void refershDirtyTraces()
  {
    new CollectAndPlayListLogic().getTracePlay(new TracePlayCallBack());
  }

  public void addObserver(Observer paramObserver)
  {
    super.addObserver(paramObserver);
  }

  public void destory()
  {
    deleteObservers();
  }

  public void init()
  {
    if (!AppFuncCfg.FUNCTION_ENABLE_TRACEPLAY)
      return;
    if (this.refreshDirtyTracesTask != null)
      this.refreshDirtyTracesTask.cancel();
    TASK_INTERVAL = GlobalEnv.getInstance().getTerminalGetCatchTimeDelayTime();
    Logger.i("Tag", "init   traceplay!!==" + TASK_INTERVAL);
    this.refreshDirtyTracesTask = new refreshDirtyTracesTask();
    refershDirtyTraces();
    SystemTimeManager.getInstance().addTask(this.refreshDirtyTracesTask, TASK_INTERVAL);
  }

  public void refreshData(List<CollectListItem> paramList)
  {
    this.tracePlayList = paramList;
  }

  class SccmsApiGetCatchVideoRecordTaskListener
    implements SccmsApiGetCatchVideoRecordTask.ISccmsApiGetCatchVideoRecordTaskListener
  {
    SccmsApiGetCatchVideoRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("DirtyTraceLogic", "SccmsApiGetCatchVideoRecordTaskListener.onError() error:" + paramServerApiCommonError);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("DirtyTraceLogic", "SccmsApiGetCatchVideoRecordTaskListener.onSuccess() result:" + paramArrayList);
      DirtyTraceLogic.access$102(DirtyTraceLogic.this, paramArrayList);
      try
      {
        UserCPLLogic.getInstance().refreshTracePlayList(paramArrayList);
        DirtyTraceLogic.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            DirtyTraceLogic.this.setChanged();
            DirtyTraceLogic.this.notifyObservers(DirtyTraceLogic.this.tracePlayList);
            DirtyTraceLogic.this.clearChanged();
            Logger.i("DirtyTraceLogic", "notifyObservers");
          }
        });
        Log.i("DirtyTraceLogic", "catchVideoRecord==" + paramArrayList.toString());
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  class TracePlayCallBack
    implements SuccessCallBack<ArrayList<CollectListItem>>
  {
    TracePlayCallBack()
    {
    }

    public void getDataError(String paramString, int paramInt)
    {
      Logger.i("DirtyTraceLogic", "onError() error:" + paramString);
    }

    public void getDataSuccess(ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("DirtyTraceLogic", "onSuccess() result:" + paramArrayList);
      DirtyTraceLogic.access$102(DirtyTraceLogic.this, paramArrayList);
      try
      {
        UserCPLLogic.getInstance().refreshTracePlayList(paramArrayList);
        DirtyTraceLogic.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            DirtyTraceLogic.this.setChanged();
            DirtyTraceLogic.this.notifyObservers(DirtyTraceLogic.this.tracePlayList);
            DirtyTraceLogic.this.clearChanged();
            Logger.i("DirtyTraceLogic", "notifyObservers");
          }
        });
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  class refreshDirtyTracesTask extends TimerTask
  {
    refreshDirtyTracesTask()
    {
    }

    public void run()
    {
      DirtyTraceLogic.this.refershDirtyTraces();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.DirtyTraceLogic
 * JD-Core Version:    0.6.2
 */