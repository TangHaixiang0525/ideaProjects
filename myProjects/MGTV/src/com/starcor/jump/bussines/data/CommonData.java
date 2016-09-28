package com.starcor.jump.bussines.data;

import android.text.TextUtils;
import com.starcor.bussines.data.type.JsonDataType;
import com.starcor.bussines.data.type.JsonDataType.KeyToValue;
import com.starcor.bussines.manager.BussinesData;
import com.starcor.common.IntentDataManager;
import com.starcor.jump.bussines.behavior.BehaviorData;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public class CommonData extends BussinesData
  implements Serializable
{
  public static final int DATA_TYPE_COMMON = 1;
  public static final int DATA_TYPE_INTENT_JSON = 2;
  public String action;
  public BehaviorData behaviorData;
  public String categoryId = "";
  public String cmdInfo = "";
  public String commonCmd;
  public boolean huanApp;
  public String huaweiCid;
  public boolean isFromMango;
  public boolean isFromWeb;
  public boolean isFromWeiXin;
  public boolean isSelectTv;
  public String jsonAction;
  public String mediaAssetId;
  public String name;
  public int notClearTask = 0;
  public String packetId;
  public String srcApp = "";
  public String tclCmd;
  public String userKey = "";
  public String videoBeginTime = "";
  public String videoChannelIndex;
  public int videoDuration = 0;
  public String videoId = "";
  public int videoIndex = 0;
  public String videoIndexName = "";
  public int videoPlayDirect = 1;
  public int videoPlayedTime = 0;
  public int videoType = 0;
  public int videoUiStyle = 0;

  public void cloneData(BussinesData paramBussinesData)
  {
    if ((paramBussinesData == null) || (!(paramBussinesData instanceof CommonData)))
      return;
    super.cloneData(paramBussinesData);
    CommonData localCommonData = (CommonData)paramBussinesData;
    this.action = localCommonData.action;
    this.videoUiStyle = localCommonData.videoUiStyle;
    this.videoId = localCommonData.videoId;
    this.videoType = localCommonData.videoType;
    this.categoryId = localCommonData.categoryId;
    this.packetId = localCommonData.packetId;
    this.videoPlayDirect = localCommonData.videoPlayDirect;
    this.videoIndex = localCommonData.videoIndex;
    this.videoPlayedTime = localCommonData.videoPlayedTime;
    this.videoIndexName = localCommonData.videoIndexName;
    this.videoBeginTime = localCommonData.videoBeginTime;
    this.videoDuration = localCommonData.videoDuration;
    this.notClearTask = localCommonData.notClearTask;
    this.srcApp = localCommonData.srcApp;
    this.cmdInfo = localCommonData.cmdInfo;
    this.userKey = localCommonData.userKey;
    this.isFromMango = localCommonData.isFromMango;
    this.isFromWeiXin = localCommonData.isFromWeiXin;
    this.huanApp = localCommonData.huanApp;
    this.name = localCommonData.name;
    this.mediaAssetId = localCommonData.mediaAssetId;
    this.isFromWeb = localCommonData.isFromWeb;
    this.behaviorData = localCommonData.behaviorData;
  }

  public BehaviorData getBehaviorData()
  {
    return this.behaviorData;
  }

  protected void preProcessData()
  {
    this.action = processAction(this.action);
    this.tclCmd = this.manager.getStringValue("cmdstring");
    this.commonCmd = this.manager.getStringValue("cmd_ex");
    if (!TextUtils.isEmpty(this.manager.getStringValue("data")))
    {
      setDataType(2);
      JsonDataType localJsonDataType = new JsonDataType();
      if (!localJsonDataType.parseData(this.manager.getStringValue("data")))
        return;
      Iterator localIterator = localJsonDataType.mArgList.iterator();
      while (localIterator.hasNext())
      {
        JsonDataType.KeyToValue localKeyToValue = (JsonDataType.KeyToValue)localIterator.next();
        this.manager.putStringValue(localKeyToValue.k, localKeyToValue.v);
      }
      this.jsonAction = localJsonDataType.action;
      this.manager.clearIntent();
      return;
    }
    setDataType(1);
  }

  protected void processWithData()
  {
    this.isFromWeiXin = processBooleanValue("isFromWeiXin", this.isFromWeiXin, false);
    this.isFromMango = processBooleanValue("isFromMango", this.isFromMango, false);
    this.videoId = processStringValue("video_id", this.videoId);
    this.videoUiStyle = processIntValue("video_ui_style", this.videoUiStyle, 0);
    this.videoType = processIntValue("video_type", this.videoType, 0);
    this.videoIndexName = processStringValue("video_index_name", this.videoIndexName);
    this.categoryId = processStringValue("category_id", this.categoryId);
    this.videoPlayedTime = processIntValue("playedTime", this.videoPlayedTime, 0);
    if (this.videoPlayedTime == 0)
      this.videoPlayedTime = processIntValue("played_time", this.videoPlayedTime, 0);
    this.videoIndex = processIntValue("video_index", this.videoIndex);
    this.videoBeginTime = processStringValue("begin_time", this.videoBeginTime, "0");
    if ("0".equals(this.videoBeginTime))
      this.videoBeginTime = processStringValue("video_begin_time", this.videoBeginTime, "0");
    this.packetId = processStringValue("packet_id", this.packetId);
    this.videoDuration = processIntValue("video_duration", this.videoDuration);
    this.videoPlayDirect = processIntValue("play_video_direct", this.videoPlayDirect, -1);
    this.notClearTask = processIntValue("__not_clear_task", this.notClearTask);
    this.srcApp = processStringValue("srcApp", this.srcApp);
    this.cmdInfo = processStringValue("cmdInfo", this.cmdInfo);
    this.userKey = processStringValue("userKey", this.userKey);
    this.huanApp = processBooleanValue("huanApp", this.huanApp);
    this.isFromWeb = processBooleanValue("flag_action_from_mgtv", this.isFromWeb, false);
    if (getDataType() == 2)
      processWithJsonData();
  }

  protected void processWithJsonData()
  {
    this.videoId = processStringValue("video_id", this.videoId);
    this.videoUiStyle = processIntValue("ui_style", this.videoUiStyle, 0);
    this.videoType = processIntValue("video_type", this.videoType, 0);
    this.videoIndexName = processStringValue("video_index_name", this.videoIndexName);
    this.categoryId = processStringValue("category_id", this.categoryId);
    this.videoIndex = processIntValue("video_index", this.videoIndex);
    this.packetId = processStringValue("special_id", this.packetId);
    this.videoPlayDirect = processIntValue("auto_play", this.videoPlayDirect);
    this.cmdInfo = processStringValue("info", this.cmdInfo);
    if (TextUtils.isEmpty(this.cmdInfo))
      this.cmdInfo = processStringValue("cmdinfo", this.cmdInfo);
    this.name = processStringValue("name", this.name);
    this.mediaAssetId = processStringValue("media_asset_id", this.mediaAssetId);
  }

  public void setBehaviorData(BehaviorData paramBehaviorData)
  {
    this.behaviorData = paramBehaviorData;
  }

  public void startProcessData()
  {
    if (!hasInit())
      processData();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.data.CommonData
 * JD-Core Version:    0.6.2
 */