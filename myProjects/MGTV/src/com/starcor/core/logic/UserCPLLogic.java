package com.starcor.core.logic;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.starcor.core.domain.CollectListItem;
import com.starcor.core.domain.MediaInfo;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.event.GlobalEventNotify;
import com.starcor.core.logic.domain.LocalPlayRecordKey;
import com.starcor.core.logic.domain.LocalPlayRecordValue;
import com.starcor.core.logic.domain.LocalPlayStateKey;
import com.starcor.core.utils.IOTools;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.domain.LocalMediaDataManage;
import com.starcor.hunan.domain.LocalMediaDataManage.MediaDateType;
import com.starcor.hunan.service.DirtyTraceLogic;
import com.starcor.hunan.service.SystemTimeManager;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class UserCPLLogic
{
  public static final String GUEST_USERID = "1";
  public static final String GUEST_USERNAME = "guest";
  public static final String TAG = "UserCPLLogic";
  private static UserCPLLogic userCPLLogic = null;
  private List<CollectListItem> collectList = null;
  boolean hasHD = false;
  boolean hasSTD = false;
  private boolean isCollectListDirty = false;
  private boolean isPlayRecordListDirty = false;
  private boolean isTracePlayListDirty = false;
  private String lastPlayMgtvFileId = "";
  private HashMap<LocalPlayRecordKey, LocalPlayRecordValue> localPlayRecordList = null;
  private HashMap<String, TimeshiftPlayRecord> localTimeshiftPlayHistory = null;
  private String mediaQuality;
  private List<CollectListItem> playRecordList = null;
  private HashMap<LocalPlayStateKey, LocalPlayRecordValue> playStateList = new HashMap();
  private List<CollectListItem> tracePlayList = null;

  private boolean addPlayRecord2Local(CollectListItem paramCollectListItem)
  {
    if ((paramCollectListItem == null) || (paramCollectListItem.video_id == null));
    do
    {
      return true;
      Logger.i("UserCPLLogic", "addPlayRecordLocal videoId:" + paramCollectListItem.video_id + ", videoIndex:" + paramCollectListItem.video_index + ", videoName:" + paramCollectListItem.video_name + ", playedTime:" + paramCollectListItem.played_time + ", duration:" + paramCollectListItem.duration);
    }
    while (this.localPlayRecordList == null);
    if (this.localPlayRecordList.size() > 1000)
    {
      Logger.i("UserCPLLogic", "addPlayRecordLocal clear old data");
      this.localPlayRecordList.clear();
    }
    LocalPlayRecordKey localLocalPlayRecordKey = new LocalPlayRecordKey();
    localLocalPlayRecordKey.videoId = paramCollectListItem.video_id;
    localLocalPlayRecordKey.videoIndex = paramCollectListItem.video_index;
    LocalPlayRecordValue localLocalPlayRecordValue = new LocalPlayRecordValue();
    localLocalPlayRecordValue.createTime = SystemTimeManager.getCurrentServerTime();
    localLocalPlayRecordValue.videoName = paramCollectListItem.video_name;
    localLocalPlayRecordValue.playedTime = paramCollectListItem.played_time;
    localLocalPlayRecordValue.duration = paramCollectListItem.duration;
    this.localPlayRecordList.put(localLocalPlayRecordKey, localLocalPlayRecordValue);
    return false;
  }

  public static UserCPLLogic getInstance()
  {
    if (userCPLLogic == null)
    {
      Logger.i("UserCPLLogic", "UserCPLLogic first create");
      userCPLLogic = new UserCPLLogic();
    }
    return userCPLLogic;
  }

  public static boolean isInstanced()
  {
    return userCPLLogic != null;
  }

  private void loadLocalPlayRecordList()
  {
    String str = GlobalLogic.getInstance().getLocalPlayStateConfigPath();
    Logger.i("UserCPLLogic", "loadLocalPlayRecordList fileName=" + str);
    File localFile = new File(str);
    try
    {
      this.localPlayRecordList = ((HashMap)IOTools.readObject(localFile));
      if (this.localPlayRecordList != null)
      {
        Logger.i("UserCPLLogic", "loadLocalPlayRecordList load ok, file:" + str + ", size:" + this.localPlayRecordList.size());
        return;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        Logger.e("UserCPLLogic", "loadLocalPlayRecordList");
        this.localPlayRecordList = null;
      }
      Logger.e("UserCPLLogic", "loadLocalPlayRecordList load null build");
      this.localPlayRecordList = new HashMap();
    }
  }

  private boolean loadLocalTimeshiftPlayHistory()
  {
    Logger.i("UserCPLLogic", "loadLocalTimeshiftPlayHistory");
    String str = GlobalLogic.getInstance().getUserPrivateConfigPath() + File.separator + "LocalTimeshiftHistory.dat";
    File localFile = new File(str);
    try
    {
      this.localTimeshiftPlayHistory = ((HashMap)IOTools.readObject(localFile));
      if (this.localTimeshiftPlayHistory != null)
      {
        Logger.i("UserCPLLogic", "loadLocalTimeshiftPlayHistory load ok, file:" + str + ", size:" + this.localPlayRecordList.size());
        return true;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        Logger.e("UserCPLLogic", "loadLocalTimeshiftPlayHistory");
        this.localTimeshiftPlayHistory = null;
        continue;
        Logger.e("UserCPLLogic", "loadLocalTimeshiftPlayHistory load null build");
        this.localTimeshiftPlayHistory = new HashMap();
      }
    }
  }

  private void loadPlayStateList()
  {
    String str = GlobalEnv.getInstance().getConfigPath() + File.separator + "LocalPlayState.dat";
    Logger.i("UserCPLLogic", "loadPlayStateList fileName=" + str);
    File localFile = new File(str);
    try
    {
      this.playStateList = ((HashMap)IOTools.readObject(localFile));
      if (this.playStateList != null)
      {
        Logger.i("UserCPLLogic", "loadPlayStateList load ok, file:" + str + ", size:" + this.playStateList.size());
        printLocalStateList("loadPlayStateList");
        return;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        Logger.e("UserCPLLogic", "loadPlayStateList null");
        this.playStateList = null;
        continue;
        Logger.e("UserCPLLogic", "loadPlayStateList load null build");
        this.playStateList = new HashMap();
      }
    }
  }

  private void printLocalStateList(String paramString)
  {
    Logger.d("UserCPLLogic", paramString);
    if (this.playStateList != null)
    {
      Iterator localIterator = this.playStateList.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        Logger.d("UserCPLLogic", "key: " + ((LocalPlayStateKey)localEntry.getKey()).toString() + ", value: " + ((LocalPlayRecordValue)localEntry.getValue()).toString());
      }
    }
  }

  private boolean saveLocalPlayRecordList()
  {
    if (this.localPlayRecordList != null)
    {
      String str = GlobalLogic.getInstance().getLocalPlayStateConfigPath();
      File localFile = new File(str);
      Logger.i("UserCPLLogic", "saveLocalPlayRecordList fileName:" + str);
      if (!IOTools.writeObject(localFile, this.localPlayRecordList))
        break label92;
      Logger.i("UserCPLLogic", "saveLocalPlayRecordList size:" + this.localPlayRecordList.size());
    }
    while (true)
    {
      return true;
      label92: Logger.e("UserCPLLogic", "saveLocalPlayRecordList size:" + this.localPlayRecordList.size());
    }
  }

  private boolean saveLocalTimeshiftPlayHistory()
  {
    if (this.localPlayRecordList != null)
    {
      String str = GlobalLogic.getInstance().getUserPrivateConfigPath() + File.separator + "LocalTimeshiftHistory.dat";
      File localFile = new File(str);
      Logger.i("UserCPLLogic", "saveLocalTimeshiftPlayHistory fileName:" + str);
      if (!IOTools.writeObject(localFile, this.localTimeshiftPlayHistory))
        break label116;
      Logger.i("UserCPLLogic", "saveLocalTimeshiftPlayHistory size:" + this.localTimeshiftPlayHistory.size());
    }
    while (true)
    {
      return true;
      label116: Logger.e("UserCPLLogic", "saveLocalTimeshiftPlayHistory size:" + this.localTimeshiftPlayHistory.size());
    }
  }

  private void savePlayStateList()
  {
    String str = GlobalEnv.getInstance().getConfigPath() + File.separator + "LocalPlayState.dat";
    File localFile = new File(str);
    Logger.i("UserCPLLogic", "savePlayStateList fileName:" + str);
    boolean bool = IOTools.writeObject(localFile, this.playStateList);
    Logger.d("UserCPLLogic", "savePlayStateList size:" + this.playStateList.size() + ", ret: " + bool);
  }

  public boolean addCollect(CollectListItem paramCollectListItem, VideoInfo paramVideoInfo, boolean paramBoolean, String paramString)
  {
    if ((paramCollectListItem == null) || (paramCollectListItem.id == null) || (paramCollectListItem.video_id == null) || (paramVideoInfo == null));
    while (this.collectList == null)
      return false;
    this.collectList.add(paramCollectListItem);
    if (paramBoolean)
    {
      Logger.i("UserCPLLogic", "addCollect notify");
      GlobalEventNotify.getInstance().onAddCollect(paramCollectListItem, paramVideoInfo, paramString);
      Intent localIntent = new Intent();
      localIntent.setAction("com.starcor.hunan.logic_event.user_cpl");
      localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.notify_add_collect");
      localIntent.putExtra("CollectId", paramCollectListItem.id);
      localIntent.putExtra("VideoId", paramCollectListItem.video_id);
      localIntent.putExtra("VideoName", paramCollectListItem.video_name);
      LocalBroadcastManager.getInstance(App.getInstance().getApplicationContext()).sendBroadcast(localIntent);
    }
    return true;
  }

  public boolean addCollect(ArrayList<CollectListItem> paramArrayList, VideoInfo paramVideoInfo, boolean paramBoolean, String paramString)
  {
    refreshCollectList(paramArrayList);
    if (paramBoolean)
    {
      CollectListItem localCollectListItem = new CollectListItem();
      if (paramArrayList.size() > 0)
        localCollectListItem = (CollectListItem)paramArrayList.get(0);
      Logger.i("UserCPLLogic", "addCollect notify");
      GlobalEventNotify.getInstance().onAddCollect(localCollectListItem, paramVideoInfo, paramString);
      Intent localIntent = new Intent();
      localIntent.setAction("com.starcor.hunan.logic_event.user_cpl");
      localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.notify_add_collect");
      localIntent.putExtra("CollectId", localCollectListItem.id);
      localIntent.putExtra("VideoId", localCollectListItem.video_id);
      localIntent.putExtra("VideoName", localCollectListItem.video_name);
      LocalBroadcastManager.getInstance(App.getInstance().getApplicationContext()).sendBroadcast(localIntent);
    }
    return true;
  }

  public boolean addPlayRecord(CollectListItem paramCollectListItem, VideoInfo paramVideoInfo, boolean paramBoolean, String paramString)
  {
    if ((paramCollectListItem == null) || (paramCollectListItem.video_id == null))
    {
      Logger.e("UserCPLLogic", "addPlayRecord input arg error");
      return false;
    }
    if (this.playRecordList == null)
    {
      Logger.e("UserCPLLogic", "addPlayRecord list null");
      this.playRecordList = new ArrayList();
      this.isPlayRecordListDirty = true;
    }
    paramCollectListItem.add_local_time = System.currentTimeMillis();
    Logger.i("UserCPLLogic", "addPlayRecord t p: playInfo:" + paramCollectListItem);
    if ((this.playRecordList != null) && (paramCollectListItem.video_id != null));
    for (int i = 0; ; i++)
      if (i < this.playRecordList.size())
      {
        if (((CollectListItem)this.playRecordList.get(i)).video_id.equals(paramCollectListItem.video_id))
        {
          this.playRecordList.remove(i);
          Logger.i("UserCPLLogic", "addPlayRecord t remove:" + paramCollectListItem.video_id);
        }
      }
      else
      {
        this.playRecordList.add(paramCollectListItem);
        if (paramBoolean)
        {
          Logger.i("UserCPLLogic", "addPlayRecord notify");
          GlobalEventNotify.getInstance().onAddPlayRecord(paramCollectListItem, paramVideoInfo, paramString);
          Intent localIntent = new Intent();
          localIntent.setAction("com.starcor.hunan.logic_event.user_cpl");
          localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.notify_add_play_record");
          localIntent.putExtra("PlayRecordId", paramCollectListItem.id);
          localIntent.putExtra("VideoId", paramCollectListItem.video_id);
          localIntent.putExtra("VideoName", paramCollectListItem.video_name);
          LocalBroadcastManager.getInstance(App.getInstance().getApplicationContext()).sendBroadcast(localIntent);
        }
        return true;
      }
  }

  public boolean addPlayRecord(ArrayList<CollectListItem> paramArrayList, VideoInfo paramVideoInfo, boolean paramBoolean)
  {
    refreshPlayRecordList(paramArrayList);
    if (paramBoolean)
    {
      Logger.i("UserCPLLogic", "addPlayRecord notify");
      CollectListItem localCollectListItem = new CollectListItem();
      if (paramArrayList.size() > 0)
        localCollectListItem = (CollectListItem)paramArrayList.get(0);
      GlobalEventNotify.getInstance().onAddPlayRecord(localCollectListItem, paramVideoInfo, this.mediaQuality);
      Intent localIntent = new Intent();
      localIntent.setAction("com.starcor.hunan.logic_event.user_cpl");
      localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.notify_add_play_record");
      localIntent.putExtra("PlayRecordId", localCollectListItem.id);
      localIntent.putExtra("VideoId", localCollectListItem.video_id);
      localIntent.putExtra("VideoName", localCollectListItem.video_name);
      LocalBroadcastManager.getInstance(App.getInstance().getApplicationContext()).sendBroadcast(localIntent);
    }
    return true;
  }

  public boolean addPlayRecordList2Local(ArrayList<CollectListItem> paramArrayList)
  {
    if (paramArrayList == null)
      return false;
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      CollectListItem localCollectListItem = (CollectListItem)localIterator.next();
      getInstance().addPlayRecord2Local(localCollectListItem);
    }
    saveLocalPlayRecordList();
    return true;
  }

  public boolean addPlayRecordLocal(CollectListItem paramCollectListItem)
  {
    if (addPlayRecord2Local(paramCollectListItem))
      return false;
    saveLocalPlayRecordList();
    return true;
  }

  public void addPlayState(CollectListItem paramCollectListItem)
  {
    if ((paramCollectListItem != null) && (!TextUtils.isEmpty(paramCollectListItem.video_id)))
    {
      if (this.playStateList == null)
        break label200;
      if (this.playStateList.size() > 100)
      {
        Logger.w("UserCPLLogic", "addPlayState 内容太多，清除原有记录 size: " + this.playStateList.size());
        this.playStateList.clear();
      }
    }
    while (true)
    {
      LocalPlayStateKey localLocalPlayStateKey = new LocalPlayStateKey();
      localLocalPlayStateKey.videoId = paramCollectListItem.video_id;
      localLocalPlayStateKey.videoIndex = paramCollectListItem.video_index;
      LocalPlayRecordValue localLocalPlayRecordValue = new LocalPlayRecordValue();
      localLocalPlayRecordValue.duration = paramCollectListItem.duration;
      localLocalPlayRecordValue.playedTime = paramCollectListItem.played_time;
      localLocalPlayRecordValue.createTime = SystemTimeManager.getCurrentServerTime();
      if (!TextUtils.isEmpty(paramCollectListItem.video_name))
        localLocalPlayRecordValue.videoName = paramCollectListItem.video_name;
      Logger.d("UserCPLLogic", "addPlayState key: " + localLocalPlayStateKey.toString() + ", value: " + localLocalPlayRecordValue.toString());
      this.playStateList.put(localLocalPlayStateKey, localLocalPlayRecordValue);
      savePlayStateList();
      return;
      label200: this.playStateList = new HashMap();
    }
  }

  public void addTimeshiftPlayRecord(String paramString, long paramLong)
  {
    Logger.i("UserCPLLogic", "addTimeshiftPlayRecord: " + paramString + " duration: " + paramLong);
    if (this.localTimeshiftPlayHistory == null)
    {
      Logger.i("UserCPLLogic", "addTimeshiftPlayRecord failed！: localTimeshiftPlayHistory is null!");
      return;
    }
    if (paramLong < 0L)
    {
      Logger.i("UserCPLLogic", "addTimeshiftPlayRecord failed！: duration is to small(" + paramLong + ") !");
      return;
    }
    synchronized (this.localTimeshiftPlayHistory)
    {
      TimeshiftPlayRecord localTimeshiftPlayRecord = (TimeshiftPlayRecord)this.localTimeshiftPlayHistory.get(paramString);
      if (localTimeshiftPlayRecord == null)
      {
        localTimeshiftPlayRecord = new TimeshiftPlayRecord();
        this.localTimeshiftPlayHistory.put(paramString, localTimeshiftPlayRecord);
      }
      localTimeshiftPlayRecord.videoId = paramString;
      localTimeshiftPlayRecord.lastPlayTime = new Date();
      localTimeshiftPlayRecord.totalPlayedCounter = (1L + localTimeshiftPlayRecord.totalPlayedCounter);
      if (localTimeshiftPlayRecord.totalPlayedTime > 1800L)
        localTimeshiftPlayRecord.totalPlayedTime = (paramLong + localTimeshiftPlayRecord.totalPlayedTime);
      while (paramLong <= 1800L)
      {
        saveLocalTimeshiftPlayHistory();
        return;
      }
      localTimeshiftPlayRecord.totalPlayedTime = (paramLong + localTimeshiftPlayRecord.totalPlayedTime);
    }
  }

  public void addTimeshiftPlayRecord(boolean paramBoolean, String paramString1, String paramString2, long paramLong)
  {
    Logger.i("UserCPLLogic", "addTimeshiftPlayRecord: " + paramString2 + " duration: " + paramLong);
    if (this.localTimeshiftPlayHistory == null)
    {
      Logger.i("UserCPLLogic", "addTimeshiftPlayRecord failed！: localTimeshiftPlayHistory is null!");
      return;
    }
    if (paramLong < 0L)
    {
      Logger.i("UserCPLLogic", "addTimeshiftPlayRecord failed！: duration is to small(" + paramLong + ") !");
      return;
    }
    synchronized (this.localTimeshiftPlayHistory)
    {
      TimeshiftPlayRecord localTimeshiftPlayRecord = (TimeshiftPlayRecord)this.localTimeshiftPlayHistory.get(paramString2);
      if (localTimeshiftPlayRecord == null)
      {
        localTimeshiftPlayRecord = new TimeshiftPlayRecord();
        this.localTimeshiftPlayHistory.put(paramString2, localTimeshiftPlayRecord);
      }
      localTimeshiftPlayRecord.isFromCommonChannel = paramBoolean;
      localTimeshiftPlayRecord.videoId = paramString2;
      localTimeshiftPlayRecord.lastPlayTime = new Date();
      localTimeshiftPlayRecord.totalPlayedCounter = (1L + localTimeshiftPlayRecord.totalPlayedCounter);
      localTimeshiftPlayRecord.categoryId = paramString1;
      if (localTimeshiftPlayRecord.totalPlayedTime > 1800L)
        localTimeshiftPlayRecord.totalPlayedTime = (paramLong + localTimeshiftPlayRecord.totalPlayedTime);
      while (paramLong <= 1800L)
      {
        saveLocalTimeshiftPlayHistory();
        return;
      }
      localTimeshiftPlayRecord.totalPlayedTime = (paramLong + localTimeshiftPlayRecord.totalPlayedTime);
    }
  }

  public boolean addTracePlay(CollectListItem paramCollectListItem, boolean paramBoolean)
  {
    if ((paramCollectListItem == null) || (paramCollectListItem.id == null) || (paramCollectListItem.video_id == null));
    while (this.tracePlayList == null)
      return false;
    this.tracePlayList.add(paramCollectListItem);
    if (paramBoolean)
    {
      Logger.i("UserCPLLogic", "addTracePlayt notify");
      Intent localIntent = new Intent();
      localIntent.setAction("com.starcor.hunan.logic_event.user_cpl");
      localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.notify_add_traceplay");
      localIntent.putExtra("CollectId", paramCollectListItem.id);
      localIntent.putExtra("VideoId", paramCollectListItem.video_id);
      localIntent.putExtra("VideoName", paramCollectListItem.video_name);
      LocalBroadcastManager.getInstance(App.getInstance().getApplicationContext()).sendBroadcast(localIntent);
    }
    return true;
  }

  public boolean addTracePlay(ArrayList<CollectListItem> paramArrayList, boolean paramBoolean)
  {
    refreshTracePlayList(paramArrayList);
    if (paramBoolean)
    {
      Logger.i("UserCPLLogic", "addTracePlay notify");
      CollectListItem localCollectListItem = new CollectListItem();
      if (paramArrayList.size() > 0)
        localCollectListItem = (CollectListItem)paramArrayList.get(0);
      Intent localIntent = new Intent();
      localIntent.setAction("com.starcor.hunan.logic_event.user_cpl");
      localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.notify_add_traceplay");
      localIntent.putExtra("CollectId", localCollectListItem.id);
      localIntent.putExtra("VideoId", localCollectListItem.video_id);
      localIntent.putExtra("VideoName", localCollectListItem.video_name);
      LocalBroadcastManager.getInstance(App.getInstance().getApplicationContext()).sendBroadcast(localIntent);
    }
    return true;
  }

  public void delectCollect(String paramString, boolean paramBoolean)
  {
    if ((this.collectList != null) && (paramString != null))
      Logger.e("loglog collectId=" + paramString);
    for (int i = 0; ; i++)
    {
      if (i < this.collectList.size())
      {
        if (!((CollectListItem)this.collectList.get(i)).id.equals(paramString))
          continue;
        Logger.i("UserCPLLogic", "delectCollect collectId:" + paramString);
        CollectListItem localCollectListItem = (CollectListItem)this.collectList.remove(i);
        if (paramBoolean)
        {
          Logger.i("UserCPLLogic", "delectCollect notify");
          GlobalEventNotify.getInstance().onDelCollect(localCollectListItem);
          Intent localIntent = new Intent();
          localIntent.setAction("com.starcor.hunan.logic_event.user_cpl");
          localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.notify_delete_collect");
          localIntent.putExtra("CollectId", localCollectListItem.id);
          localIntent.putExtra("VideoId", localCollectListItem.video_id);
          LocalBroadcastManager.getInstance(App.getInstance().getApplicationContext()).sendBroadcast(localIntent);
        }
      }
      return;
    }
  }

  public void delectTracePlay(String paramString, boolean paramBoolean)
  {
    if ((this.tracePlayList != null) && (paramString != null));
    for (int i = 0; ; i++)
    {
      if (i < this.tracePlayList.size())
      {
        if (!((CollectListItem)this.tracePlayList.get(i)).id.equals(paramString))
          continue;
        Logger.i("UserCPLLogic", "delectTracePlay collectId:" + paramString);
        CollectListItem localCollectListItem = (CollectListItem)this.tracePlayList.remove(i);
        if (paramBoolean)
        {
          Logger.i("UserCPLLogic", "delectTracePlay notify");
          GlobalEventNotify.getInstance().onDelCollect(localCollectListItem);
          Intent localIntent = new Intent();
          localIntent.setAction("com.starcor.hunan.logic_event.user_cpl");
          localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.notify_delete_traceplay");
          localIntent.putExtra("CollectId", localCollectListItem.id);
          localIntent.putExtra("VideoId", localCollectListItem.video_id);
          LocalBroadcastManager.getInstance(App.getInstance().getApplicationContext()).sendBroadcast(localIntent);
        }
      }
      return;
    }
  }

  public void deleteAllCollect(boolean paramBoolean)
  {
    Logger.i("UserCPLLogic", "deleteAllCollect");
    if (this.collectList != null)
      this.collectList.clear();
    if (paramBoolean)
    {
      Logger.i("UserCPLLogic", "deleteAllCollect notify");
      GlobalEventNotify.getInstance().onDeleteAllCollect();
      Intent localIntent = new Intent();
      localIntent.setAction("com.starcor.hunan.logic_event.user_cpl");
      localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.notify_delete_all_collect");
      LocalBroadcastManager.getInstance(App.getInstance().getApplicationContext()).sendBroadcast(localIntent);
    }
  }

  public void deleteAllCollectFromTCL(String paramString)
  {
  }

  public void deleteAllPlayRecord(boolean paramBoolean)
  {
    Logger.i("UserCPLLogic", "deleteAllCollect");
    if (this.playRecordList != null)
      this.playRecordList.clear();
    if (paramBoolean)
    {
      Logger.i("UserCPLLogic", "deleteAllPlayRecord notify");
      Intent localIntent = new Intent();
      localIntent.setAction("com.starcor.hunan.logic_event.user_cpl");
      localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.notify_delete_all_play_record");
      LocalBroadcastManager.getInstance(App.getInstance().getApplicationContext()).sendBroadcast(localIntent);
      GlobalEventNotify.getInstance().onDeleteAllPlayRecord();
    }
  }

  public void deleteAllTracePlay(boolean paramBoolean)
  {
    Logger.i("UserCPLLogic", "deleteAllTracePlay");
    if (this.tracePlayList != null)
      this.tracePlayList.clear();
    if (paramBoolean)
    {
      Logger.i("UserCPLLogic", "deleteAllTracePlay notify");
      Intent localIntent = new Intent();
      localIntent.setAction("com.starcor.hunan.logic_event.user_cpl");
      localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.notify_delete_all_traceplay");
      LocalBroadcastManager.getInstance(App.getInstance().getApplicationContext()).sendBroadcast(localIntent);
    }
  }

  public void deleteCollectFromTCL(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1 != null) && (paramString2 != null) && (paramString3 == null));
  }

  public void deletePlayRecord(String paramString, boolean paramBoolean)
  {
    if ((this.playRecordList != null) && (paramString != null));
    for (int i = 0; ; i++)
    {
      if (i < this.playRecordList.size())
      {
        if (!((CollectListItem)this.playRecordList.get(i)).id.equals(paramString))
          continue;
        Logger.i("UserCPLLogic", "deletePlayRecord collectId:" + paramString);
        CollectListItem localCollectListItem = (CollectListItem)this.playRecordList.remove(i);
        if (paramBoolean)
        {
          Logger.i("UserCPLLogic", "deletePlayRecord notify");
          GlobalEventNotify.getInstance().onDelPlayRecord(localCollectListItem);
          Intent localIntent = new Intent();
          localIntent.setAction("com.starcor.hunan.logic_event.user_cpl");
          localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.notify_delete_play_record");
          localIntent.putExtra("CollectId", localCollectListItem.id);
          localIntent.putExtra("VideoId", localCollectListItem.video_id);
          LocalBroadcastManager.getInstance(App.getInstance().getApplicationContext()).sendBroadcast(localIntent);
        }
      }
      return;
    }
  }

  public void deletePlayRecordFromOuter()
  {
  }

  public void dirtyCollectList()
  {
    Logger.i("UserCPLLogic", "dirtyCollectList");
    this.isCollectListDirty = true;
  }

  public void dirtyPlayRecordList()
  {
    Logger.i("UserCPLLogic", "dirtyPlayRecordList");
    this.isPlayRecordListDirty = true;
  }

  public void dirtyTracePlayList()
  {
    Logger.i("UserCPLLogic", "dirtyTracePlayList");
    this.isTracePlayListDirty = true;
  }

  public String findCollectIdbyVideoId(String paramString)
  {
    if ((this.collectList != null) && (paramString != null))
    {
      Iterator localIterator = this.collectList.iterator();
      while (localIterator.hasNext())
      {
        CollectListItem localCollectListItem = (CollectListItem)localIterator.next();
        if (paramString.equals(localCollectListItem.video_id))
          return localCollectListItem.id;
      }
    }
    return "";
  }

  public String findPlayRecordIdbyVideoId(String paramString)
  {
    if ((this.playRecordList != null) && (paramString != null))
    {
      Iterator localIterator = this.playRecordList.iterator();
      while (localIterator.hasNext())
      {
        CollectListItem localCollectListItem = (CollectListItem)localIterator.next();
        if (paramString.equals(localCollectListItem.video_id))
          return localCollectListItem.id;
      }
    }
    return "";
  }

  public String findTracePlayIdbyVideoId(String paramString)
  {
    if ((this.tracePlayList != null) && (paramString != null))
    {
      Iterator localIterator = this.tracePlayList.iterator();
      while (localIterator.hasNext())
      {
        CollectListItem localCollectListItem = (CollectListItem)localIterator.next();
        if (paramString.equals(localCollectListItem.video_id))
          return localCollectListItem.id;
      }
    }
    return "";
  }

  public List<CollectListItem> getCollectList()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.collectList != null)
      localArrayList.addAll(this.collectList);
    return localArrayList;
  }

  public int getDurtionInPlayRecordListIncludeLocal(String paramString, int paramInt)
  {
    if (this.localPlayRecordList != null)
    {
      LocalPlayRecordKey localLocalPlayRecordKey = new LocalPlayRecordKey();
      localLocalPlayRecordKey.videoId = paramString;
      localLocalPlayRecordKey.videoIndex = paramInt;
      LocalPlayRecordValue localLocalPlayRecordValue = (LocalPlayRecordValue)this.localPlayRecordList.get(localLocalPlayRecordKey);
      if (localLocalPlayRecordValue != null)
        return localLocalPlayRecordValue.duration;
    }
    if ((this.playRecordList != null) && (paramString != null))
    {
      Iterator localIterator = this.playRecordList.iterator();
      while (localIterator.hasNext())
      {
        CollectListItem localCollectListItem = (CollectListItem)localIterator.next();
        if ((paramString.equals(localCollectListItem.video_id)) && (localCollectListItem.video_index == paramInt))
          return localCollectListItem.duration;
      }
    }
    return 0;
  }

  public String getLastPlayMgtvFileId()
  {
    return this.lastPlayMgtvFileId;
  }

  public int getLastPlayRecord(String paramString)
  {
    int i = -1;
    long l1 = 0L;
    if ((this.playRecordList != null) && (paramString != null))
    {
      Iterator localIterator2 = this.playRecordList.iterator();
      while (localIterator2.hasNext())
      {
        CollectListItem localCollectListItem = (CollectListItem)localIterator2.next();
        if ((paramString.equals(localCollectListItem.video_id)) && (localCollectListItem.add_local_time >= 0L))
        {
          i = localCollectListItem.video_index;
          Logger.i("UserCPLLogic", "getLastPlayRecord : item:" + localCollectListItem);
        }
      }
    }
    if (i != -1)
      return i;
    if (this.localPlayRecordList != null)
    {
      Iterator localIterator1 = this.localPlayRecordList.keySet().iterator();
      while (localIterator1.hasNext())
      {
        LocalPlayRecordKey localLocalPlayRecordKey = (LocalPlayRecordKey)localIterator1.next();
        if (localLocalPlayRecordKey.videoId.equals(paramString))
        {
          long l2 = ((LocalPlayRecordValue)this.localPlayRecordList.get(localLocalPlayRecordKey)).createTime;
          if (l2 > l1)
          {
            i = localLocalPlayRecordKey.videoIndex;
            l1 = l2;
            Logger.d("UserCPLLogic", "GetLastPlayRecord2 creatTime :" + l2 + " index:" + localLocalPlayRecordKey.videoIndex + " video_id:" + paramString + " lastplaytime:" + l1 + "  lastindex:" + i);
          }
          Logger.i("UserCPLLogic", "GetLastPlayRecord2 creatTime :" + l2 + " index:" + localLocalPlayRecordKey.videoIndex + " video_id:" + paramString + " lastplaytime:" + l1 + "  lastindex:" + i);
        }
      }
    }
    return i;
  }

  public HashMap<LocalPlayRecordKey, LocalPlayRecordValue> getLocalPlayRecordList()
  {
    if (this.localPlayRecordList == null)
      loadLocalPlayRecordList();
    return this.localPlayRecordList;
  }

  public String getMediaQuality(VideoInfo paramVideoInfo)
  {
    if ((paramVideoInfo.indexList != null) && (paramVideoInfo.indexList.size() > 0) && (paramVideoInfo.indexList.get(0) != null) && (((VideoIndex)paramVideoInfo.indexList.get(0)).mediaInfo != null))
    {
      ArrayList localArrayList = ((VideoIndex)paramVideoInfo.indexList.get(0)).mediaInfo;
      this.hasHD = false;
      this.hasSTD = false;
      this.mediaQuality = "";
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        MediaInfo localMediaInfo = (MediaInfo)localIterator.next();
        if ("HD".equalsIgnoreCase(localMediaInfo.type))
          this.hasHD = true;
        else if ("STD".equalsIgnoreCase(localMediaInfo.type))
          this.hasSTD = true;
        else if (!"LOW".equalsIgnoreCase(localMediaInfo.type));
      }
    }
    if ((this.hasHD) && (this.hasSTD))
      this.mediaQuality = "HD";
    while (true)
    {
      return this.mediaQuality;
      if ((this.hasHD) && (!this.hasSTD))
        this.mediaQuality = "HD";
      else if ((!this.hasHD) && (this.hasSTD))
        this.mediaQuality = "STD";
      else
        this.mediaQuality = "LOW";
    }
  }

  public List<CollectListItem> getPlayRecordList()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.playRecordList != null)
      localArrayList.addAll(this.playRecordList);
    return localArrayList;
  }

  public int getPlayedTimeInPlayRecordListIncludeLocal(String paramString, int paramInt)
  {
    if (this.localPlayRecordList != null)
    {
      LocalPlayRecordKey localLocalPlayRecordKey = new LocalPlayRecordKey();
      localLocalPlayRecordKey.videoId = paramString;
      localLocalPlayRecordKey.videoIndex = paramInt;
      LocalPlayRecordValue localLocalPlayRecordValue = (LocalPlayRecordValue)this.localPlayRecordList.get(localLocalPlayRecordKey);
      if (localLocalPlayRecordValue != null)
      {
        Logger.i("UserCPLLogic", "getPlayedTimeInPlayRecordListIncludeLocal local videoId:" + paramString + ", videoIndex:" + paramInt + ", playedTime:" + localLocalPlayRecordValue.playedTime);
        return localLocalPlayRecordValue.playedTime;
      }
    }
    if ((this.playRecordList != null) && (paramString != null))
    {
      Iterator localIterator = this.playRecordList.iterator();
      while (localIterator.hasNext())
      {
        CollectListItem localCollectListItem = (CollectListItem)localIterator.next();
        if ((paramString.equals(localCollectListItem.video_id)) && (localCollectListItem.video_index == paramInt))
        {
          Logger.i("UserCPLLogic", "getPlayedTimeInPlayRecordListIncludeLocal playRecordList videoId:" + paramString + ", videoIndex:" + paramInt + ", playedTime:" + localCollectListItem.played_time);
          return localCollectListItem.played_time;
        }
      }
    }
    Logger.i("UserCPLLogic", "getPlayedTimeInPlayRecordListIncludeLocal not found videoId:" + paramString + ", videoIndex:" + paramInt);
    return 0;
  }

  public int getPlayedTimeInPlayStateList(String paramString, int paramInt)
  {
    LocalPlayStateKey localLocalPlayStateKey = new LocalPlayStateKey();
    localLocalPlayStateKey.videoId = paramString;
    localLocalPlayStateKey.videoIndex = paramInt;
    LocalPlayRecordValue localLocalPlayRecordValue = (LocalPlayRecordValue)this.playStateList.get(localLocalPlayStateKey);
    if (localLocalPlayRecordValue != null)
    {
      int i = localLocalPlayRecordValue.playedTime;
      Logger.d("UserCPLLogic", "getPlayedTimeInPlayStateList videoId: " + paramString + ", playedTime: " + i);
      return i;
    }
    Logger.i("UserCPLLogic", "can't find playstate for videoId: " + paramString + ", index: " + paramInt);
    return 0;
  }

  public List<TimeshiftPlayRecord> getTopTimeshiftPlayHistoryRecords(int paramInt, Comparator<TimeshiftPlayRecord> paramComparator)
  {
    Object localObject;
    if (this.localTimeshiftPlayHistory == null)
      localObject = null;
    do
    {
      return localObject;
      if (paramComparator == null)
        paramComparator = new Comparator()
        {
          public int compare(UserCPLLogic.TimeshiftPlayRecord paramAnonymousTimeshiftPlayRecord1, UserCPLLogic.TimeshiftPlayRecord paramAnonymousTimeshiftPlayRecord2)
          {
            if (paramAnonymousTimeshiftPlayRecord1.totalPlayedTime > paramAnonymousTimeshiftPlayRecord2.totalPlayedTime)
              return -1;
            return 1;
          }
        };
      localObject = new ArrayList();
      ((ArrayList)localObject).addAll(this.localTimeshiftPlayHistory.values());
      Collections.sort((List)localObject, paramComparator);
    }
    while (((ArrayList)localObject).size() <= paramInt);
    return ((ArrayList)localObject).subList(0, paramInt);
  }

  public List<CollectListItem> getTracePlayList()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.tracePlayList != null)
      localArrayList.addAll(this.tracePlayList);
    return localArrayList;
  }

  public List<CollectListItem> getUpdatedTracePlayList()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.tracePlayList != null)
      for (int i = 0; i < this.tracePlayList.size(); i++)
        if ((((CollectListItem)this.tracePlayList.get(i)).online == 1) && (((CollectListItem)this.tracePlayList.get(i)).video_index < ((CollectListItem)this.tracePlayList.get(i)).update_index))
          localArrayList.add(this.tracePlayList.get(i));
    return localArrayList;
  }

  public boolean init(Context paramContext)
  {
    Logger.i("UserCPLLogic", "init context:" + paramContext.toString());
    return true;
  }

  public boolean isCollectExists(String paramString)
  {
    if ((this.collectList != null) && (paramString != null))
      for (int i = 0; i < this.collectList.size(); i++)
        if (((CollectListItem)this.collectList.get(i)).video_id.equals(paramString))
        {
          Logger.i("UserCPLLogic", "isCollectExists Yes videoId:" + paramString);
          return true;
        }
    Logger.i("UserCPLLogic", "isCollectExists No videoId:" + paramString);
    return false;
  }

  public boolean isCollectListReady()
  {
    return (this.collectList != null) && (!this.isCollectListDirty);
  }

  public boolean isEpisodeFinished(String paramString, int paramInt)
  {
    Logger.i("UserCPLLogic", "videoId=" + paramString + " videoIndex=" + paramInt);
    if (this.localPlayRecordList != null)
    {
      Iterator localIterator = this.localPlayRecordList.keySet().iterator();
      while (localIterator.hasNext())
      {
        LocalPlayRecordKey localLocalPlayRecordKey = (LocalPlayRecordKey)localIterator.next();
        if ((localLocalPlayRecordKey.videoId.equals(paramString)) && (localLocalPlayRecordKey.videoIndex == paramInt))
        {
          LocalPlayRecordValue localLocalPlayRecordValue = (LocalPlayRecordValue)this.localPlayRecordList.get(localLocalPlayRecordKey);
          if (localLocalPlayRecordValue != null)
          {
            Logger.i("UserCPLLogic", "isEpisodeFinished localPlayRecordList.get(key)=" + localLocalPlayRecordValue);
            int i = localLocalPlayRecordValue.playedTime;
            int j = localLocalPlayRecordValue.duration;
            int k = j - i;
            Logger.i("UserCPLLogic", "remainingTime=" + k);
            if ((k <= 10) || (j == k))
              return true;
          }
        }
      }
    }
    return false;
  }

  public boolean isPlayRecordExists(String paramString)
  {
    if ((this.playRecordList != null) && (paramString != null))
      for (int i = 0; i < this.playRecordList.size(); i++)
        if (((CollectListItem)this.playRecordList.get(i)).video_id.equals(paramString))
        {
          Logger.i("UserCPLLogic", "isPlayRecordExists Yes videoId:" + paramString);
          return true;
        }
    Logger.i("UserCPLLogic", "isPlayRecordExists No videoId:" + paramString);
    return false;
  }

  public boolean isPlayRecordExistsIncludeLocal(String paramString, int paramInt)
  {
    if ((this.playRecordList != null) && (paramString != null))
    {
      Iterator localIterator = this.playRecordList.iterator();
      while (localIterator.hasNext())
      {
        CollectListItem localCollectListItem = (CollectListItem)localIterator.next();
        if ((paramString.equals(localCollectListItem.video_id)) && (localCollectListItem.video_index == paramInt))
        {
          Logger.i("UserCPLLogic", "isPlayRecordExistsIncludeLocal hit1 videoId:" + paramString + ", videoIndex:" + paramInt);
          return true;
        }
      }
    }
    if (this.localPlayRecordList != null)
    {
      LocalPlayRecordKey localLocalPlayRecordKey = new LocalPlayRecordKey();
      localLocalPlayRecordKey.videoId = paramString;
      localLocalPlayRecordKey.videoIndex = paramInt;
      if ((LocalPlayRecordValue)this.localPlayRecordList.get(localLocalPlayRecordKey) != null)
      {
        Logger.i("UserCPLLogic", "isPlayRecordExistsIncludeLocal hit2 videoId:" + paramString + ", videoIndex:" + paramInt);
        return true;
      }
    }
    return false;
  }

  public boolean isPlayRecordListReady()
  {
    return (this.playRecordList != null) && (!this.isPlayRecordListDirty);
  }

  public boolean isSpecialVideoPlayed(String paramString, int paramInt)
  {
    LocalPlayStateKey localLocalPlayStateKey = new LocalPlayStateKey();
    localLocalPlayStateKey.videoId = paramString;
    localLocalPlayStateKey.videoIndex = paramInt;
    return (LocalPlayRecordValue)this.playStateList.get(localLocalPlayStateKey) != null;
  }

  public boolean isTracePlayExists(String paramString)
  {
    if ((this.tracePlayList != null) && (paramString != null))
      for (int i = 0; i < this.tracePlayList.size(); i++)
        if (((CollectListItem)this.tracePlayList.get(i)).video_id.equals(paramString))
        {
          Logger.i("UserCPLLogic", "isTracePlayExists Yes videoId:" + paramString);
          return true;
        }
    Logger.i("UserCPLLogic", "isTracePlayExists No videoId:" + paramString);
    return false;
  }

  public boolean isTracePlayListReady()
  {
    return (this.tracePlayList != null) && (!this.isTracePlayListDirty);
  }

  public void notifyTCLAddPlayRecords(ArrayList<CollectListItem> paramArrayList)
  {
    if (paramArrayList != null)
    {
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext())
      {
        CollectListItem localCollectListItem = (CollectListItem)localIterator.next();
        GlobalEventNotify.getInstance().onAddPlayRecordForTCT(localCollectListItem);
      }
    }
  }

  public void notifyTCLaddCollectRecords(ArrayList<CollectListItem> paramArrayList)
  {
    if (paramArrayList != null)
    {
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext())
      {
        CollectListItem localCollectListItem = (CollectListItem)localIterator.next();
        GlobalEventNotify.getInstance().onAddCollectForTCT(localCollectListItem);
      }
    }
  }

  public void refreshCollectList(List<CollectListItem> paramList)
  {
    if (paramList != null)
    {
      this.collectList = new ArrayList();
      this.collectList.addAll(paramList);
      Logger.i("UserCPLLogic", "refreshCollectList size:" + this.collectList.size());
    }
    while (true)
    {
      this.isCollectListDirty = false;
      return;
      this.collectList = null;
      Logger.e("UserCPLLogic", "refreshCollectList null");
    }
  }

  public void refreshPlayRecordList(List<CollectListItem> paramList)
  {
    if (paramList != null)
    {
      this.playRecordList = new ArrayList();
      this.playRecordList.addAll(paramList);
      Logger.i("UserCPLLogic", "refreshPlayList size:" + this.playRecordList.size());
    }
    while (true)
    {
      this.isPlayRecordListDirty = false;
      return;
      this.playRecordList = null;
      Logger.e("UserCPLLogic", "refreshPlayList");
    }
  }

  public void refreshTracePlayList(List<CollectListItem> paramList)
  {
    if (paramList != null)
    {
      this.tracePlayList = new ArrayList();
      this.tracePlayList.addAll(paramList);
      DirtyTraceLogic.getInstance().refreshData(this.tracePlayList);
      Logger.i("UserCPLLogic", "refreshtracePlayList size:" + this.tracePlayList.size());
    }
    while (true)
    {
      this.isTracePlayListDirty = false;
      return;
      this.tracePlayList = null;
      DirtyTraceLogic.getInstance().refreshData(this.tracePlayList);
      Logger.e("UserCPLLogic", "refreshtracePlayList null");
    }
  }

  public void setLastPlayMgtvFileId(String paramString)
  {
    this.lastPlayMgtvFileId = paramString;
    if (this.lastPlayMgtvFileId == null)
      this.lastPlayMgtvFileId = "";
  }

  public void unInit()
  {
    saveLocalPlayRecordList();
    saveLocalTimeshiftPlayHistory();
  }

  public void userLogin()
  {
    Logger.i("UserCPLLogic", "userLogin");
    if ((this.collectList != null) && (GlobalLogic.getInstance().isGuestAccount))
    {
      Logger.i("UserCPLLogic", "userLogin this.collectList size=" + this.collectList.size());
      LocalMediaDataManage.getInstance().saveMediaDataToLocalFile(LocalMediaDataManage.MediaDateType.COLLECTION, this.collectList);
    }
    if ((this.playRecordList != null) && (GlobalLogic.getInstance().isGuestAccount))
    {
      Logger.i("UserCPLLogic", "userLogin this.playRecordList size=" + this.playRecordList.size());
      LocalMediaDataManage.getInstance().saveMediaDataToLocalFile(LocalMediaDataManage.MediaDateType.PLAYLIST, this.playRecordList);
    }
    if ((this.tracePlayList != null) && (GlobalLogic.getInstance().isGuestAccount))
    {
      Logger.i("UserCPLLogic", "userLogin this.tracePlayList size=" + this.tracePlayList.size());
      LocalMediaDataManage.getInstance().saveMediaDataToLocalFile(LocalMediaDataManage.MediaDateType.CATCH, this.tracePlayList);
    }
    GlobalLogic.getInstance().isGuestAccount = false;
    this.collectList = null;
    this.playRecordList = null;
    this.tracePlayList = null;
    this.localPlayRecordList = null;
    this.localTimeshiftPlayHistory = null;
    loadLocalPlayRecordList();
    loadLocalTimeshiftPlayHistory();
    loadPlayStateList();
  }

  public void userLogout()
  {
    this.collectList = null;
    this.playRecordList = null;
    this.tracePlayList = null;
    this.localPlayRecordList = null;
    this.localTimeshiftPlayHistory = null;
    Logger.i("UserCPLLogic", "userLogout");
    loadLocalPlayRecordList();
    loadLocalTimeshiftPlayHistory();
    loadPlayStateList();
  }

  public static class TimeshiftPlayRecord
    implements Serializable
  {
    public String categoryId = "";
    public boolean isFromCommonChannel = false;
    public Date lastPlayTime = new Date();
    public long totalPlayedCounter = 0L;
    public long totalPlayedTime = 0L;
    public String videoId = "";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.logic.UserCPLLogic
 * JD-Core Version:    0.6.2
 */