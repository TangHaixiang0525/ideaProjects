package com.starcor.jump.bussines;

import android.util.Log;
import com.starcor.core.domain.CollectListItem;
import com.starcor.hunan.interfaces.SuccessCallBack;
import com.starcor.hunan.service.CollectAndPlayListLogic;
import com.starcor.jump.bussines.behavior.BehaviorData;
import com.starcor.jump.bussines.data.CommonData;
import java.util.ArrayList;

public class RecordOperatorBussines extends CommonBussines
{
  public static final int COLLECT_RECORD_ADD = 4;
  public static final int COLLECT_RECORD_ALL_DEL = 6;
  public static final int COLLECT_RECORD_DEL = 5;
  public static final int PLAY_RECORD_ADD = 1;
  public static final int PLAY_RECORD_ALL_DEL = 3;
  public static final int PLAY_RECORD_DEL = 2;
  public static final int REMOVE_ALL = 10;
  private static final String TAG = RecordOperatorBussines.class.getSimpleName();
  public static final int TRACE_RECORD_ADD = 7;
  public static final int TRACE_RECORD_ALL_DEL = 9;
  public static final int TRACE_RECORD_DEL = 8;
  private SuccessCallBack callback = new SuccessCallBack()
  {
    public void getDataError(String paramAnonymousString, int paramAnonymousInt)
    {
      Log.i(RecordOperatorBussines.TAG, "getDataError, error is: " + paramAnonymousString);
      Log.i(RecordOperatorBussines.TAG, "getDataError, http error code is: " + paramAnonymousInt);
    }

    public void getDataSuccess(ArrayList<CollectListItem> paramAnonymousArrayList)
    {
      Log.i(RecordOperatorBussines.TAG, "getDataSuccess");
    }
  };

  private void processDelOperator()
  {
    Log.d(TAG, "processDelOperator: video_id: " + this._data.videoId);
    String str = this._data.videoId;
    CollectAndPlayListLogic localCollectAndPlayListLogic = new CollectAndPlayListLogic();
    switch (this._data.behaviorData.what)
    {
    case 1:
    case 4:
    case 7:
    case 8:
    case 9:
    case 10:
    default:
      return;
    case 2:
      localCollectAndPlayListLogic.delPlayList(this.callback, str);
      return;
    case 3:
      localCollectAndPlayListLogic.delAllPlayList(this.callback);
      return;
    case 5:
      localCollectAndPlayListLogic.delCollect(this.callback, str);
      return;
    case 6:
    }
    localCollectAndPlayListLogic.delAllCollect(this.callback);
  }

  protected void commonStart()
  {
    processDelOperator();
  }

  protected void startFromActivity()
  {
  }

  protected void startFromReciever()
  {
    Log.d(TAG, "startFromReciever");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.RecordOperatorBussines
 * JD-Core Version:    0.6.2
 */