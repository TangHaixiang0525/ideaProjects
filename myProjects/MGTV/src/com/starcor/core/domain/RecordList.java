package com.starcor.core.domain;

import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.interfaces.SuccessCallBack;
import com.starcor.hunan.service.CollectAndPlayListLogic;
import com.starcor.sccms.api.SccmsApiDelCatchVideoRecordTask.ISccmsApiDelCatchVideoRecordTaskListener;
import com.starcor.sccms.api.SccmsApiDelCollectRecordTask.ISccmsApiDelColllectRecordTaskListener;
import com.starcor.sccms.api.SccmsApiDelPlayRecordTask.ISccmsApiDelPlayRecordTaskListener;
import com.starcor.sccms.api.SccmsApiGetCatchVideoRecordTask.ISccmsApiGetCatchVideoRecordTaskListener;
import com.starcor.sccms.api.SccmsApiGetCollectRecordTask.ISccmsApiGetCollectRecordTaskListener;
import com.starcor.sccms.api.SccmsApiGetPlayRecordTask.ISccmsApiGetPlayRecordTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.util.ArrayList;

public class RecordList
{
  public static final String TAG = "RecordList";
  private static CollectAndPlayListLogic collectLogic = null;
  private static RecordList recordList = null;
  private OnRemovePlayListListener lsr1;
  private OnRemoveCollectListListener lsr2;
  private OnRemoveTracePlayListListener lsr3;

  public static RecordList getInstance()
  {
    if (recordList == null)
    {
      Logger.i("RecordList", "GlobalEventNotify first create");
      recordList = new RecordList();
    }
    return recordList;
  }

  private String getRecordListId(ArrayList<CollectListItem> paramArrayList)
  {
    Object localObject = "";
    if (paramArrayList == null)
      return localObject;
    int i = -1 + paramArrayList.size();
    if (i >= 0)
    {
      String str = ((CollectListItem)paramArrayList.get(i)).id;
      if (TextUtils.isEmpty((CharSequence)localObject));
      for (localObject = str; ; localObject = (String)localObject + "|" + str)
      {
        i--;
        break;
      }
    }
    return localObject;
  }

  public void removeAllCollectionListInfo(OnRemoveCollectListListener paramOnRemoveCollectListListener)
  {
    this.lsr2 = paramOnRemoveCollectListListener;
    if (collectLogic == null)
      collectLogic = new CollectAndPlayListLogic();
    collectLogic.getCollect(new SuccessCallBack()
    {
      public void getDataError(String paramAnonymousString, int paramAnonymousInt)
      {
        if (RecordList.this.lsr2 != null)
          RecordList.this.lsr2.onError();
      }

      public void getDataSuccess(ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        if (((paramAnonymousArrayList == null) || (paramAnonymousArrayList.size() == 0)) && (RecordList.this.lsr2 != null))
        {
          RecordList.this.lsr2.onSuccess();
          return;
        }
        RecordList.collectLogic.delAllCollect(new SuccessCallBack()
        {
          public void getDataError(String paramAnonymous2String, int paramAnonymous2Int)
          {
            if (RecordList.this.lsr2 != null)
              RecordList.this.lsr2.onError();
          }

          public void getDataSuccess(ArrayList<CollectListItem> paramAnonymous2ArrayList)
          {
            if (RecordList.this.lsr2 != null)
              RecordList.this.lsr2.onSuccess();
          }
        });
      }
    });
  }

  public void removeAllPlayListInfo(OnRemovePlayListListener paramOnRemovePlayListListener)
  {
    this.lsr1 = paramOnRemovePlayListListener;
    if (collectLogic == null)
      collectLogic = new CollectAndPlayListLogic();
    collectLogic.getPlayList(new SuccessCallBack()
    {
      public void getDataError(String paramAnonymousString, int paramAnonymousInt)
      {
        if (RecordList.this.lsr1 != null)
          RecordList.this.lsr1.onError();
      }

      public void getDataSuccess(ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        if (((paramAnonymousArrayList == null) || (paramAnonymousArrayList.size() == 0)) && (RecordList.this.lsr1 != null))
        {
          RecordList.this.lsr1.onSuccess();
          return;
        }
        RecordList.collectLogic.delAllPlayList(new SuccessCallBack()
        {
          public void getDataError(String paramAnonymous2String, int paramAnonymous2Int)
          {
            if (RecordList.this.lsr1 != null)
              RecordList.this.lsr1.onError();
          }

          public void getDataSuccess(ArrayList<CollectListItem> paramAnonymous2ArrayList)
          {
            if (RecordList.this.lsr1 != null)
              RecordList.this.lsr1.onSuccess();
          }
        });
      }
    });
  }

  public void removeAllRecordList()
  {
    removeAllCollectionListInfo(null);
    removeAllPlayListInfo(null);
    removeAllTracePlayListInfo(null);
  }

  public void removeAllTracePlayListInfo(OnRemoveTracePlayListListener paramOnRemoveTracePlayListListener)
  {
    this.lsr3 = paramOnRemoveTracePlayListListener;
    if (collectLogic == null)
      collectLogic = new CollectAndPlayListLogic();
    collectLogic.getTracePlay(new SuccessCallBack()
    {
      public void getDataError(String paramAnonymousString, int paramAnonymousInt)
      {
        if (RecordList.this.lsr3 != null)
          RecordList.this.lsr3.onError();
      }

      public void getDataSuccess(ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        if (((paramAnonymousArrayList == null) || (paramAnonymousArrayList.size() == 0)) && (RecordList.this.lsr3 != null))
        {
          RecordList.this.lsr3.onSuccess();
          return;
        }
        RecordList.collectLogic.delAllTracePlay(new SuccessCallBack()
        {
          public void getDataError(String paramAnonymous2String, int paramAnonymous2Int)
          {
            if (RecordList.this.lsr3 != null)
              RecordList.this.lsr3.onError();
          }

          public void getDataSuccess(Void paramAnonymous2Void)
          {
            if (RecordList.this.lsr3 != null)
              RecordList.this.lsr3.onSuccess();
          }
        });
      }
    });
  }

  public static abstract interface OnRemoveCollectListListener
  {
    public abstract void onError();

    public abstract void onSuccess();
  }

  public static abstract interface OnRemovePlayListListener
  {
    public abstract void onError();

    public abstract void onSuccess();
  }

  public static abstract interface OnRemoveTracePlayListListener
  {
    public abstract void onError();

    public abstract void onSuccess();
  }

  class SccmsApiDelAllCatchVideoRecordTaskListener
    implements SccmsApiDelCatchVideoRecordTask.ISccmsApiDelCatchVideoRecordTaskListener
  {
    SccmsApiDelAllCatchVideoRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("RecordList", "SccmsApiDelAllCatchVideoRecordTaskListener.onError() error:" + paramServerApiCommonError);
      if (RecordList.this.lsr3 != null)
        RecordList.this.lsr3.onError();
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, String paramString)
    {
      Logger.i("RecordList", "SccmsApiDelAllCatchVideoRecordTaskListener.onSuccess() result:" + paramString);
      if (RecordList.this.lsr3 != null)
        RecordList.this.lsr3.onSuccess();
    }
  }

  class SccmsApiDelAllCollectRecordTaskListener
    implements SccmsApiDelCollectRecordTask.ISccmsApiDelColllectRecordTaskListener
  {
    SccmsApiDelAllCollectRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("RecordList", "SccmsApiDelAllCollectRecordTaskListener.onError() error:" + paramServerApiCommonError);
      if (RecordList.this.lsr2 != null)
        RecordList.this.lsr2.onError();
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, String paramString)
    {
      Logger.i("RecordList", "SccmsApiDelAllCollectRecordTaskListener.onSuccess() result:" + paramString);
      if (RecordList.this.lsr2 != null)
        RecordList.this.lsr2.onSuccess();
    }
  }

  class SccmsApiDelAllPlayRecordTaskListener
    implements SccmsApiDelPlayRecordTask.ISccmsApiDelPlayRecordTaskListener
  {
    SccmsApiDelAllPlayRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("RecordList", "SccmsApiDelAllPlayRecordTaskListener.onError() error:" + paramServerApiCommonError);
      if (RecordList.this.lsr1 != null)
        RecordList.this.lsr1.onError();
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, String paramString)
    {
      Logger.i("RecordList", "SccmsApiDelAllPlayRecordTaskListener.onSuccess() result:" + paramString);
      if (RecordList.this.lsr1 != null)
        RecordList.this.lsr1.onSuccess();
    }
  }

  class SccmsApiGetCatchVideoRecordTaskListener
    implements SccmsApiGetCatchVideoRecordTask.ISccmsApiGetCatchVideoRecordTaskListener
  {
    SccmsApiGetCatchVideoRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("RecordList", "SccmsApiGetCatchVideoRecordTaskListener.onError() error:" + paramServerApiCommonError);
      if (RecordList.this.lsr3 != null)
        RecordList.this.lsr3.onError();
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("RecordList", "SccmsApiGetCatchVideoRecordTaskListener.onSuccess() result:" + paramArrayList);
      if (((paramArrayList == null) || (paramArrayList.size() == 0)) && (RecordList.this.lsr3 != null))
      {
        RecordList.this.lsr3.onSuccess();
        return;
      }
      ServerApiManager.i().APIDelCatchVideoRecord(RecordList.this.getRecordListId(paramArrayList), new RecordList.SccmsApiDelAllCatchVideoRecordTaskListener(RecordList.this));
    }
  }

  class SccmsApiGetCollectRecordTaskListener
    implements SccmsApiGetCollectRecordTask.ISccmsApiGetCollectRecordTaskListener
  {
    SccmsApiGetCollectRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("RecordList", "SccmsApiGetCollectRecordTaskListener.onError() error:" + paramServerApiCommonError);
      if (RecordList.this.lsr2 != null)
        RecordList.this.lsr2.onError();
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("RecordList", "SccmsApiGetCollectRecordTaskListener.onSuccess() result:" + paramArrayList);
      if (((paramArrayList == null) || (paramArrayList.size() == 0)) && (RecordList.this.lsr2 != null))
      {
        RecordList.this.lsr2.onSuccess();
        return;
      }
      ServerApiManager.i().APIDelCollectRecord(RecordList.this.getRecordListId(paramArrayList), new RecordList.SccmsApiDelAllCollectRecordTaskListener(RecordList.this));
    }
  }

  class SccmsApiGetPlayRecordTaskListener
    implements SccmsApiGetPlayRecordTask.ISccmsApiGetPlayRecordTaskListener
  {
    SccmsApiGetPlayRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("RecordList", "SccmsApiGetPlayRecordTaskListener.onError() error:" + paramServerApiCommonError);
      if (RecordList.this.lsr1 != null)
        RecordList.this.lsr1.onError();
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("RecordList", "SccmsApiGetPlayRecordTaskListener.onSuccess() result:" + paramArrayList);
      if (((paramArrayList == null) || (paramArrayList.size() == 0)) && (RecordList.this.lsr1 != null))
      {
        RecordList.this.lsr1.onSuccess();
        return;
      }
      ServerApiManager.i().APIDelPlayRecord(RecordList.this.getRecordListId(paramArrayList), new RecordList.SccmsApiDelAllPlayRecordTaskListener(RecordList.this));
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.RecordList
 * JD-Core Version:    0.6.2
 */