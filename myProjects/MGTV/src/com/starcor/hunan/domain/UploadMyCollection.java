package com.starcor.hunan.domain;

import android.util.Log;
import com.starcor.core.domain.CollectListItem;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.interfaces.SuccessCallBack;
import com.starcor.hunan.service.CollectAndPlayListLogic;
import com.starcor.sccms.api.SccmsApiGetCollectRecordTask.ISccmsApiGetCollectRecordTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class UploadMyCollection
  implements MediaDataTypeInterface
{
  private static final String TAG = UploadMyCollection.class.getSimpleName();
  private CollectAndPlayListLogic collectAndPlayListLogic = null;
  private List<CollectListItem> localCombineCollection = null;
  private List<CollectListItem> localLoginCollection = null;
  private List<CollectListItem> localUnloginCollection = null;
  private SuccessCallBack<Void> uploadCollectCallBack = new SuccessCallBack()
  {
    public void getDataError(String paramAnonymousString, int paramAnonymousInt)
    {
      Logger.e(UploadMyCollection.TAG, "uploadCollectCallBack result  getDataError");
    }

    public void getDataSuccess(Void paramAnonymousVoid)
    {
      Logger.i(UploadMyCollection.TAG, "uploadCollectCallBack result  getDataSuccess");
    }
  };

  private void collectionRecordSort()
  {
    ComparatorRecordDate localComparatorRecordDate = new ComparatorRecordDate();
    this.localCombineCollection = deleteRepeatVideoDataRecord(this.localUnloginCollection, this.localLoginCollection);
    if ((this.localCombineCollection != null) && (this.localCombineCollection.size() != 0))
    {
      Collections.sort(this.localCombineCollection, localComparatorRecordDate);
      if (this.collectAndPlayListLogic != null)
        this.collectAndPlayListLogic.uploadCollectToCloud(this.uploadCollectCallBack, this.localCombineCollection);
    }
  }

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
    Logger.i(TAG, "unloginCollection size=" + paramList1.size());
    Logger.i(TAG, "loginCollection size=" + paramList2.size());
    paramList2.addAll(paramList1);
    Logger.i(TAG, "loginCollection size=" + paramList2.size());
    return paramList2;
  }

  private void getLoginCollection()
  {
    ServerApiManager.i().APIGetCollectRecord(new SccmsApiGetCollectionRecordTaskListener());
  }

  public void dealMediaDataAndUploadMediaData()
  {
    getLoginCollection();
  }

  public void getUnLoginList()
  {
    this.localUnloginCollection = ((List)LocalMediaDataManage.getInstance().getAllMediaDataFromFile().get(LocalMediaDataManage.MediaDateType.COLLECTION));
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

  class SccmsApiGetCollectionRecordTaskListener
    implements SccmsApiGetCollectRecordTask.ISccmsApiGetCollectRecordTaskListener
  {
    SccmsApiGetCollectionRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i(UploadMyCollection.TAG, "ISccmsApiGetCollectRecordTaskListener.onError() error:" + paramServerApiCommonError);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i(UploadMyCollection.TAG, "ISccmsApiGetCollectRecordTaskListener.onSuccess() result:" + paramArrayList);
      UploadMyCollection.access$102(UploadMyCollection.this, paramArrayList);
      if (UploadMyCollection.this.localUnloginCollection != null)
        Log.i(UploadMyCollection.TAG, "getPlayList localUnloginCollection=" + UploadMyCollection.this.localUnloginCollection.size());
      if (UploadMyCollection.this.localLoginCollection != null)
        Log.i(UploadMyCollection.TAG, "getPlayList==" + UploadMyCollection.this.localLoginCollection.size());
      try
      {
        UploadMyCollection.this.collectionRecordSort();
        Log.i(UploadMyCollection.TAG, "getPlayList localCombineCollection=" + UploadMyCollection.this.localCombineCollection.size());
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
 * Qualified Name:     com.starcor.hunan.domain.UploadMyCollection
 * JD-Core Version:    0.6.2
 */