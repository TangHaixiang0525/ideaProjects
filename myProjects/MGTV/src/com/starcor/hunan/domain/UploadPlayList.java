package com.starcor.hunan.domain;

import android.util.Log;
import com.starcor.core.domain.CollectListItem;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.interfaces.SuccessCallBack;
import com.starcor.hunan.service.CollectAndPlayListLogic;
import com.starcor.sccms.api.SccmsApiGetPlayRecordV2Task.ISccmsApiGetPlayRecordV2TaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class UploadPlayList
  implements MediaDataTypeInterface
{
  private static final String TAG = UploadPlayList.class.getSimpleName();
  private CollectAndPlayListLogic collectAndPlayListLogic = null;
  private List<CollectListItem> localCombinePlayList = null;
  private List<CollectListItem> localLogicPlayList = null;
  private List<CollectListItem> localUnlogicPlayList = null;
  private SuccessCallBack<Void> uploadPlayListCallBack = new SuccessCallBack()
  {
    public void getDataError(String paramAnonymousString, int paramAnonymousInt)
    {
      Logger.e(UploadPlayList.TAG, "uploadPlayListCallBack result getDataError");
    }

    public void getDataSuccess(Void paramAnonymousVoid)
    {
      Logger.i(UploadPlayList.TAG, "uploadPlayListCallBack result getDataSuccess");
    }
  };

  private List<CollectListItem> deleteRepeatVideoDataRecord(List<CollectListItem> paramList1, List<CollectListItem> paramList2)
  {
    if ((paramList1 == null) || ((paramList1 != null) && (paramList1.size() == 0)));
    label146: 
    do
    {
      return paramList2;
      if ((paramList2 == null) || ((paramList2 != null) && (paramList2.size() == 0)))
        return paramList1;
      Iterator localIterator1 = paramList2.iterator();
      while (true)
      {
        if (!localIterator1.hasNext())
          break label146;
        CollectListItem localCollectListItem1 = (CollectListItem)localIterator1.next();
        Iterator localIterator2 = paramList1.iterator();
        if (localIterator2.hasNext())
        {
          CollectListItem localCollectListItem2 = (CollectListItem)localIterator2.next();
          if (!localCollectListItem1.video_id.equals(localCollectListItem2.video_id))
            break;
          if (localCollectListItem1.add_time.compareTo(localCollectListItem2.add_time) >= 0)
            localIterator2.remove();
          else
            localIterator1.remove();
        }
      }
      if (!paramList2.iterator().hasNext())
        return paramList1;
    }
    while (!paramList1.iterator().hasNext());
    Logger.i(TAG, "UnloginPlayList size=" + paramList1.size());
    Logger.i(TAG, "LoginPlayList size=" + paramList2.size());
    paramList2.addAll(paramList1);
    Logger.i(TAG, "LoginPlayList size=" + paramList2.size());
    return paramList2;
  }

  private void getLoginPlayList()
  {
    ServerApiManager.i().APIGetPlayRecordV2(new SccmsApiGetPlayRecordTaskListener());
  }

  private void playListSort()
  {
    ComparatorRecordDate localComparatorRecordDate = new ComparatorRecordDate();
    this.localCombinePlayList = deleteRepeatVideoDataRecord(this.localUnlogicPlayList, this.localLogicPlayList);
    if ((this.localCombinePlayList != null) && (this.localCombinePlayList.size() != 0))
    {
      Collections.sort(this.localCombinePlayList, localComparatorRecordDate);
      if (this.collectAndPlayListLogic != null)
        this.collectAndPlayListLogic.uploadPlayListToCloud(this.uploadPlayListCallBack, this.localCombinePlayList);
    }
  }

  public void dealMediaDataAndUploadMediaData()
  {
    getLoginPlayList();
  }

  public void getUnLoginList()
  {
    this.localUnlogicPlayList = ((List)LocalMediaDataManage.getInstance().getAllMediaDataFromFile().get(LocalMediaDataManage.MediaDateType.PLAYLIST));
  }

  public void setCollectAndPlayListLogicObject(CollectAndPlayListLogic paramCollectAndPlayListLogic)
  {
    this.collectAndPlayListLogic = paramCollectAndPlayListLogic;
  }

  public class ComparatorRecordDate
    implements Comparator<CollectListItem>
  {
    public ComparatorRecordDate()
    {
    }

    public int compare(CollectListItem paramCollectListItem1, CollectListItem paramCollectListItem2)
    {
      if ((paramCollectListItem1 == null) || (paramCollectListItem1.add_time == null))
        return -1;
      if ((paramCollectListItem2 == null) || (paramCollectListItem1.add_time == null))
        return 1;
      return paramCollectListItem1.add_time.compareTo(paramCollectListItem2.add_time);
    }
  }

  class SccmsApiGetPlayRecordTaskListener
    implements SccmsApiGetPlayRecordV2Task.ISccmsApiGetPlayRecordV2TaskListener
  {
    SccmsApiGetPlayRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i(UploadPlayList.TAG, "SccmsApiGetPlayRecordTaskListener.onError() error:" + paramServerApiCommonError);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i(UploadPlayList.TAG, "SccmsApiGetPlayRecordTaskListener.onSuccess() result:" + paramArrayList);
      UploadPlayList.access$102(UploadPlayList.this, paramArrayList);
      if (UploadPlayList.this.localUnlogicPlayList == null)
        return;
      if (UploadPlayList.this.localUnlogicPlayList != null)
        Log.i(UploadPlayList.TAG, "getPlayList localLogicPlayList=" + UploadPlayList.this.localUnlogicPlayList.size());
      if (UploadPlayList.this.localLogicPlayList != null)
        Log.i(UploadPlayList.TAG, "getPlayList==" + UploadPlayList.this.localLogicPlayList.size());
      try
      {
        UploadPlayList.this.playListSort();
        Log.i(UploadPlayList.TAG, "getPlayList localCombinePlayList=" + UploadPlayList.this.localCombinePlayList.size());
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.domain.UploadPlayList
 * JD-Core Version:    0.6.2
 */