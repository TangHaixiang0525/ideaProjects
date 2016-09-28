package com.starcor.jump.bussines;

import android.text.TextUtils;
import android.util.Log;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.domain.MetadataGoup;
import com.starcor.core.domain.MetadataGroupPageIndex;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.jump.bussines.data.CommonData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class PlayTimeShiftBussines extends CommonBussines
{
  private static final String TAG = PlayTimeShiftBussines.class.getSimpleName();

  protected void commonStart()
  {
    putExtra("Cmd", "com.starcor.hunan.cmd.show_timeshift_list");
    if (!AppFuncCfg.isMgVersion2)
    {
      Logger.d(TAG, "ExternalRequest,processPlayTimeShift version error");
      processForMainActivity();
      return;
    }
    String str = "timeshift";
    if (!TextUtils.isEmpty(this._data.packetId))
      str = this._data.packetId;
    PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
    localPlayerIntentParams.nns_index = 0;
    localPlayerIntentParams.nns_videoInfo = new VideoInfo();
    localPlayerIntentParams.nns_videoInfo.videoType = 1;
    localPlayerIntentParams.nns_videoInfo.packageId = str;
    localPlayerIntentParams.nns_videoInfo.categoryId = this._data.categoryId;
    localPlayerIntentParams.mode = 6;
    localPlayerIntentParams.autoPlay = 0;
    setClassForActivity(ActivityAdapter.getInstance().getMPlayer());
    putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
    putExtra("videoType", 1);
    MetadataInfo localMetadataInfo1 = new MetadataInfo();
    localMetadataInfo1.video_id = this._data.videoId;
    localMetadataInfo1.video_type = String.valueOf(this._data.videoType);
    localMetadataInfo1.category_id = this._data.categoryId;
    localMetadataInfo1.packet_id = this._data.packetId;
    localMetadataInfo1.name = this._data.name;
    localMetadataInfo1.uiStyle = this._data.videoUiStyle;
    putExtra("MetaDataInfo", localMetadataInfo1);
    ArrayList localArrayList = GlobalLogic.getInstance().getN3A2MetaGroups();
    MetadataGoup localMetadataGoup;
    if (localArrayList != null)
    {
      Iterator localIterator1 = localArrayList.iterator();
      while (localIterator1.hasNext())
      {
        localMetadataGoup = (MetadataGoup)localIterator1.next();
        if ("menu".equals(localMetadataGoup.type))
          if (localMetadataGoup.meta_index_list != null)
            break label320;
      }
    }
    while (true)
    {
      Logger.d(TAG, "ExternalRequest,processPlayTimeShift playerInfo:" + localPlayerIntentParams.toString());
      return;
      label320: Iterator localIterator2 = localMetadataGoup.meta_index_list.iterator();
      while (true)
      {
        label329: if (!localIterator2.hasNext())
          break label470;
        MetadataGroupPageIndex localMetadataGroupPageIndex = (MetadataGroupPageIndex)localIterator2.next();
        if (localMetadataGroupPageIndex.meta_item_list == null)
          break;
        Collections.sort(localMetadataGroupPageIndex.meta_item_list, new MetadataInfo());
        Iterator localIterator3 = localMetadataGroupPageIndex.meta_item_list.iterator();
        if (localIterator3.hasNext())
        {
          MetadataInfo localMetadataInfo2 = (MetadataInfo)localIterator3.next();
          Logger.e(TAG, "action type:" + localMetadataInfo2.action_type + " - " + localMetadataInfo2.toString());
          if (!str.equalsIgnoreCase(localMetadataInfo2.action_type))
            break label329;
          putExtra("MetaDataInfo", localMetadataInfo2);
        }
      }
      label470: break;
      Logger.e(TAG, "no metadata!!!");
    }
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
 * Qualified Name:     com.starcor.jump.bussines.PlayTimeShiftBussines
 * JD-Core Version:    0.6.2
 */