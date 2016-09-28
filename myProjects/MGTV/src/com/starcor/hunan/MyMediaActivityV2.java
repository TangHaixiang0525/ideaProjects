package com.starcor.hunan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.CollectListItem;
import com.starcor.core.domain.MovieData;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.logic.UserCPLLogic;
import com.starcor.core.utils.AppKiller;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.interfaces.SuccessCallBack;
import com.starcor.hunan.service.CollectAndPlayListLogic;
import com.starcor.hunan.service.SystemTimeManager;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.hunan.uilogic.InitApiData;
import com.starcor.hunan.uilogic.InitApiData.onApiOKListener;
import com.starcor.hunan.widget.CommDialog;
import com.starcor.hunan.xiaomi.ActivityUserCheckLogic;
import com.starcor.sccms.api.SccmsApiDelCatchVideoRecordV2Task.ISccmsApiDelCatchVideoRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiDelCollectRecordV2Task.ISccmsApiDelColllectRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiDelPlayRecordV2Task.ISccmsApiDelPlayRecordV2TaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.Prop.XulAction;
import com.starcor.xul.Render.XulMassiveRender.DataItemIterator;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.Wrapper.XulGroupAreaWrapper;
import com.starcor.xul.Wrapper.XulMassiveAreaWrapper;
import com.starcor.xul.Wrapper.XulSliderAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class MyMediaActivityV2 extends BaseActivity
{
  private static final String ACTION_ALL = "m_open_all_playbill_list_page";
  private static final String ACTION_CATCHVIDEO = "m_open_catch_page";
  private static final String ACTION_COLLECT = "m_open_collect_page";
  private static final String ACTION_PLAY_RECORD = "m_open_playlist_page";
  public static final int MEDIA_COLLECT = 1;
  public static final int MEDIA_PLAY_RECORD = 2;
  public static final int MEDIA_TRACE_PLAY = 3;
  private static final String TAG = "MyMediaActivityV2";
  String _deleteList = "";
  private List<MediaItem> allItemList = new ArrayList();
  private List<CollectListItem> collectList = null;
  private String current_Action = "";
  private XulView delete_button;
  private boolean isCollectFinished = false;
  private boolean isDrawDeleteButton = false;
  private boolean isFromOut = false;
  private boolean isLoadSuccess = false;
  private boolean isRecordFinished = false;
  private boolean isRunCollect = false;
  private boolean isRunRecord = false;
  private boolean isRunTrace = false;
  private boolean isTraceFinished = false;
  private int itemCount = 0;
  private long lastClickTime = 0L;
  private CollectAndPlayListLogic mCollectLogic;
  private Context mContext;
  private MyMediaRecevier mRecevier;
  private XulView mXulEmptyTips;
  private XulView mXulFilmListView;
  private XulMassiveAreaWrapper mXulFilmListWrapper;
  int oldFocusId = 0;
  String oldFocusVideoID = "";
  private XulView pageContentGroupView;
  private List<CollectListItem> playRecordList = null;
  private int removeId = -1;
  private List<CollectListItem> tracePlayList = null;

  private void addItem(int paramInt1, CollectListItem paramCollectListItem, int paramInt2)
  {
    if (paramCollectListItem == null)
      return;
    XulDataNode localXulDataNode = XulDataNode.obtainDataNode("item");
    localXulDataNode.setAttribute("name", paramCollectListItem.video_name);
    localXulDataNode.setAttribute("ui_style", paramCollectListItem.uiStyle + "");
    localXulDataNode.setAttribute("video_id", paramCollectListItem.video_id);
    localXulDataNode.setAttribute("video_type", paramCollectListItem.video_type + "");
    localXulDataNode.setAttribute("media_asset_id", paramCollectListItem.media_assets_id);
    localXulDataNode.setAttribute("category_id", paramCollectListItem.category_id);
    if (this.current_Action.equals("m_open_all_playbill_list_page"))
      localXulDataNode.setAttribute("page_area", "media_all");
    while (true)
    {
      localXulDataNode.setAttribute("img_v", paramCollectListItem.img_v);
      localXulDataNode.setAttribute("img_h", paramCollectListItem.img_h);
      localXulDataNode.setAttribute("type", paramInt2 + "");
      localXulDataNode.setAttribute("corner_mark", paramCollectListItem.mark);
      localXulDataNode.setAttribute("action", "m_open_detail_Page");
      localXulDataNode.setAttribute("corner_mark_img", paramCollectListItem.corner_mark_img);
      if (paramInt1 <= -1)
        break;
      this.mXulFilmListWrapper.addItem(paramInt1, localXulDataNode);
      return;
      if (this.current_Action.equals("m_open_playlist_page"))
        localXulDataNode.setAttribute("page_area", "media_play");
      else if (this.current_Action.equals("m_open_catch_page"))
        localXulDataNode.setAttribute("page_area", "media_traceplay");
      else
        localXulDataNode.setAttribute("page_area", "media_collect");
    }
    this.mXulFilmListWrapper.addItem(localXulDataNode);
  }

  private void addMassiveView(final String paramString, int paramInt, List<CollectListItem> paramList)
  {
    if (this.mXulFilmListWrapper == null);
    do
    {
      do
        return;
      while ((!"m_open_all_playbill_list_page".equals(this.current_Action)) && ((paramInt != 2) || (!"m_open_playlist_page".equals(this.current_Action))) && ((paramInt != 3) || (!"m_open_catch_page".equals(this.current_Action))) && ((paramInt != 1) || (!"m_open_collect_page".equals(this.current_Action))));
      this.removeId = -1;
      this.mXulFilmListWrapper.eachItem(new XulMassiveRender.DataItemIterator()
      {
        public void onDataItem(int paramAnonymousInt, XulDataNode paramAnonymousXulDataNode)
        {
          if (paramAnonymousXulDataNode == null);
          String str;
          do
          {
            return;
            str = paramAnonymousXulDataNode.getAttribute("video_id").getValue();
          }
          while (!paramString.equals(str));
          Logger.i("addMassiveView should remove idx=" + paramAnonymousInt);
          MyMediaActivityV2.access$902(MyMediaActivityV2.this, paramAnonymousInt);
        }
      });
      if (paramInt != 2)
        break;
    }
    while ((this.removeId == 0) && (findItemByVideoId(this.playRecordList, paramString).video_type == 0));
    while (this.removeId != 0)
    {
      if (this.removeId > -1)
        this.mXulFilmListWrapper.removeItem(this.removeId);
      CollectListItem localCollectListItem = findItemByVideoId(paramList, paramString);
      showEmptyTips(false);
      addItem(0, localCollectListItem, paramInt);
      this.mXulFilmListWrapper.syncContentView();
      if ((this.mXulFilmListWrapper.itemNum() == 1) && (!"m_open_all_playbill_list_page".equals(this.current_Action)))
        updateDeleteButton();
      if (paramInt != 2)
        break;
      setFocus();
      return;
    }
  }

  private void checkRunType()
  {
    if (this.pageContentGroupView == null)
      this.pageContentGroupView = xulFindViewById("page_content_normal_group");
    if (this.pageContentGroupView == null);
    while (true)
    {
      return;
      Iterator localIterator = XulGroupAreaWrapper.fromXulView(this.pageContentGroupView).getAllGroupItems().iterator();
      while (localIterator.hasNext())
      {
        XulView localXulView = (XulView)localIterator.next();
        if ("m_open_collect_page".equals(localXulView.getActionString("click")))
        {
          this.isRunCollect = true;
        }
        else if ("m_open_catch_page".equals(localXulView.getActionString("click")))
        {
          this.isRunTrace = true;
        }
        else if ("m_open_playlist_page".equals(localXulView.getActionString("click")))
        {
          this.isRunRecord = true;
        }
        else if ("m_open_all_playbill_list_page".equals(localXulView.getActionString("click")))
        {
          this.isRunCollect = true;
          this.isRunTrace = true;
          this.isRunRecord = true;
        }
      }
    }
  }

  private void deleteVideoList()
  {
    Logger.i("Mymedia deleteVideoList _deleteList=" + this._deleteList);
    if (!TextUtils.isEmpty(this._deleteList))
    {
      if (!this.current_Action.equals("m_open_collect_page"))
        break label57;
      delCollectByCollectIds(this._deleteList);
    }
    label57: 
    do
    {
      return;
      if (this.current_Action.equals("m_open_playlist_page"))
      {
        delPlayRecordByCollectIds(this._deleteList);
        return;
      }
    }
    while (!this.current_Action.equals("m_open_catch_page"));
    delTracePlayByCollectIds(this._deleteList);
  }

  private void getCollectRecord()
  {
    Logger.i("MyMediaActivityV2", "getCollectRecord!!!");
    if (this.mCollectLogic == null)
      this.mCollectLogic = new CollectAndPlayListLogic();
    this.mCollectLogic.getCollect(new SuccessCallBack()
    {
      public void getDataError(String paramAnonymousString, int paramAnonymousInt)
      {
        MyMediaActivityV2.access$802(MyMediaActivityV2.this, true);
        if ((MyMediaActivityV2.this.current_Action.equals("m_open_all_playbill_list_page")) || (MyMediaActivityV2.this.current_Action.equals("m_open_collect_page")))
          MyMediaActivityV2.this.onCheckApiFinished();
      }

      public void getDataSuccess(ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        MyMediaActivityV2.access$802(MyMediaActivityV2.this, true);
        if ((MyMediaActivityV2.this.current_Action.equals("m_open_all_playbill_list_page")) || (MyMediaActivityV2.this.current_Action.equals("m_open_collect_page")))
          MyMediaActivityV2.this.onCheckApiFinished();
      }
    });
  }

  private String getCurrentAction()
  {
    if (!TextUtils.isEmpty(getIntent().getStringExtra("cmd_is_from_out")))
      this.isFromOut = true;
    Object localObject;
    while (true)
    {
      localObject = "";
      String str1 = getIntent().getStringExtra("Cmd");
      Logger.i("MyMediaActivityV2", "cmd=" + str1);
      if ("com.starcor.hunan.cmd.show_collect_list".equals(str1))
        localObject = "m_open_collect_page";
      try
      {
        while (true)
        {
          XulGroupAreaWrapper localXulGroupAreaWrapper = XulGroupAreaWrapper.fromXulView(xulFindViewById("page_content_normal_group"));
          XulArrayList localXulArrayList = xulFindViewsByClass("mymedia_page_content_item");
          if ((TextUtils.isEmpty((CharSequence)localObject)) && (localXulArrayList.size() > 0))
            localObject = ((XulView)localXulArrayList.get(0)).getAction("click").getStringValue();
          Iterator localIterator = localXulArrayList.iterator();
          XulView localXulView;
          do
          {
            boolean bool = localIterator.hasNext();
            i = 0;
            if (!bool)
              break;
            localXulView = (XulView)localIterator.next();
          }
          while (!((String)localObject).equals(localXulView.getActionString("click")));
          localXulGroupAreaWrapper.setChecked(localXulView);
          xulRequestFocus(localXulView);
          Logger.d("first check action=" + (String)localObject);
          int i = 1;
          if (i == 0)
          {
            String str2 = ((XulView)localXulArrayList.get(0)).getAction("click").getStringValue();
            localObject = str2;
          }
          return localObject;
          this.isFromOut = false;
          break;
          if ("com.starcor.hunan.cmd.show_play_list".equals(str1))
            localObject = "m_open_playlist_page";
          else if ("com.starcor.hunan.cmd.show_traceplay_list".equals(str1))
            localObject = "m_open_catch_page";
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return localObject;
  }

  private CollectListItem getItem(String paramString)
  {
    if (!isListEmpty(this.playRecordList))
    {
      Iterator localIterator3 = this.playRecordList.iterator();
      while (localIterator3.hasNext())
      {
        CollectListItem localCollectListItem3 = (CollectListItem)localIterator3.next();
        if ((localCollectListItem3 != null) && (localCollectListItem3.video_id.equals(paramString)))
          return localCollectListItem3;
      }
    }
    if (!isListEmpty(this.collectList))
    {
      Iterator localIterator2 = this.collectList.iterator();
      while (localIterator2.hasNext())
      {
        CollectListItem localCollectListItem2 = (CollectListItem)localIterator2.next();
        if ((localCollectListItem2 != null) && (localCollectListItem2.video_id.equals(paramString)))
          return localCollectListItem2;
      }
    }
    if (!isListEmpty(this.tracePlayList))
    {
      Iterator localIterator1 = this.tracePlayList.iterator();
      while (localIterator1.hasNext())
      {
        CollectListItem localCollectListItem1 = (CollectListItem)localIterator1.next();
        if ((localCollectListItem1 != null) && (localCollectListItem1.video_id.equals(paramString)))
          return localCollectListItem1;
      }
    }
    return null;
  }

  private void getPlayRecord()
  {
    Logger.i("MyMediaActivityV2", "getPlayRecord!!!");
    if (this.mCollectLogic == null)
      this.mCollectLogic = new CollectAndPlayListLogic();
    this.mCollectLogic.getPlayList(new SuccessCallBack()
    {
      public void getDataError(String paramAnonymousString, int paramAnonymousInt)
      {
        MyMediaActivityV2.access$702(MyMediaActivityV2.this, true);
        if ((MyMediaActivityV2.this.current_Action.equals("m_open_all_playbill_list_page")) || (MyMediaActivityV2.this.current_Action.equals("m_open_playlist_page")))
          MyMediaActivityV2.this.onCheckApiFinished();
      }

      public void getDataSuccess(ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        MyMediaActivityV2.access$702(MyMediaActivityV2.this, true);
        if ((MyMediaActivityV2.this.current_Action.equals("m_open_all_playbill_list_page")) || (MyMediaActivityV2.this.current_Action.equals("m_open_playlist_page")))
          MyMediaActivityV2.this.onCheckApiFinished();
      }
    });
  }

  private List<MediaItem> getTotalItems()
  {
    if ((this.collectList != null) && (this.playRecordList != null))
    {
      int k = -1 + this.collectList.size();
      if (k >= 0)
      {
        CollectListItem localCollectListItem6 = (CollectListItem)this.collectList.get(k);
        for (int m = -1 + this.playRecordList.size(); ; m--)
        {
          if (m >= 0)
          {
            CollectListItem localCollectListItem7 = (CollectListItem)this.playRecordList.get(m);
            if (!localCollectListItem6.video_id.equals(localCollectListItem7.video_id))
              continue;
            if (localCollectListItem6.add_time.compareTo(localCollectListItem7.add_time) < 0)
              break label132;
            this.playRecordList.remove(m);
          }
          while (true)
          {
            k--;
            break;
            label132: this.collectList.remove(k);
          }
        }
      }
    }
    if ((this.tracePlayList != null) && (this.playRecordList != null))
    {
      int i = -1 + this.tracePlayList.size();
      if (i >= 0)
      {
        CollectListItem localCollectListItem4 = (CollectListItem)this.tracePlayList.get(i);
        for (int j = -1 + this.playRecordList.size(); ; j--)
        {
          if (j >= 0)
          {
            CollectListItem localCollectListItem5 = (CollectListItem)this.playRecordList.get(j);
            if (!localCollectListItem4.video_id.equals(localCollectListItem5.video_id))
              continue;
            if (localCollectListItem4.add_time.compareTo(localCollectListItem5.add_time) < 0)
              break label285;
            this.playRecordList.remove(j);
          }
          while (true)
          {
            i--;
            break;
            label285: this.tracePlayList.remove(i);
          }
        }
      }
    }
    this.allItemList.clear();
    if (!isListEmpty(this.tracePlayList))
    {
      Iterator localIterator3 = this.tracePlayList.iterator();
      while (localIterator3.hasNext())
      {
        CollectListItem localCollectListItem3 = (CollectListItem)localIterator3.next();
        MediaItem localMediaItem3 = new MediaItem();
        localMediaItem3.item = localCollectListItem3;
        localMediaItem3.MediaItemType = 3;
        this.allItemList.add(localMediaItem3);
      }
    }
    if (!isListEmpty(this.playRecordList))
    {
      Iterator localIterator2 = this.playRecordList.iterator();
      while (localIterator2.hasNext())
      {
        CollectListItem localCollectListItem2 = (CollectListItem)localIterator2.next();
        MediaItem localMediaItem2 = new MediaItem();
        localMediaItem2.item = localCollectListItem2;
        localMediaItem2.MediaItemType = 2;
        this.allItemList.add(localMediaItem2);
      }
    }
    if (!isListEmpty(this.collectList))
    {
      Iterator localIterator1 = this.collectList.iterator();
      while (localIterator1.hasNext())
      {
        CollectListItem localCollectListItem1 = (CollectListItem)localIterator1.next();
        MediaItem localMediaItem1 = new MediaItem();
        localMediaItem1.item = localCollectListItem1;
        localMediaItem1.MediaItemType = 1;
        this.allItemList.add(localMediaItem1);
      }
    }
    if (!isListEmpty(this.allItemList))
      Collections.sort(this.allItemList, new ComparatorItem(null));
    return this.allItemList;
  }

  private void getTracePlayVideo()
  {
    Logger.i("MyMediaActivityV2", "getTracePlayVideo!!!");
    if (this.mCollectLogic == null)
      this.mCollectLogic = new CollectAndPlayListLogic();
    this.mCollectLogic.getTracePlay(new SuccessCallBack()
    {
      public void getDataError(String paramAnonymousString, int paramAnonymousInt)
      {
        MyMediaActivityV2.access$402(MyMediaActivityV2.this, true);
        if ((MyMediaActivityV2.this.current_Action.equals("m_open_all_playbill_list_page")) || (MyMediaActivityV2.this.current_Action.equals("m_open_catch_page")))
          MyMediaActivityV2.this.onCheckApiFinished();
      }

      public void getDataSuccess(ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        MyMediaActivityV2.access$402(MyMediaActivityV2.this, true);
        if ((MyMediaActivityV2.this.current_Action.equals("m_open_all_playbill_list_page")) || (MyMediaActivityV2.this.current_Action.equals("m_open_catch_page")))
          MyMediaActivityV2.this.onCheckApiFinished();
      }
    });
  }

  private void goToPlayer(CollectListItem paramCollectListItem)
  {
    PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
    localPlayerIntentParams.nns_videoInfo = new VideoInfo();
    localPlayerIntentParams.nns_videoInfo.categoryId = paramCollectListItem.category_id;
    localPlayerIntentParams.nns_videoInfo.videoId = paramCollectListItem.video_id;
    localPlayerIntentParams.nns_videoInfo.videoType = paramCollectListItem.video_type;
    localPlayerIntentParams.nns_videoInfo.name = paramCollectListItem.video_name;
    localPlayerIntentParams.nns_videoInfo.packageId = paramCollectListItem.media_assets_id;
    localPlayerIntentParams.nns_timeLen = paramCollectListItem.ts_time_len;
    localPlayerIntentParams.played_time = paramCollectListItem.played_time;
    localPlayerIntentParams.mediaQuality = paramCollectListItem.quality;
    if (paramCollectListItem.video_type == 1)
    {
      if ((TextUtils.isEmpty(paramCollectListItem.ts_begin)) || (paramCollectListItem.ts_begin.length() <= 8))
        break label231;
      localPlayerIntentParams.nns_day = paramCollectListItem.ts_begin.substring(0, 8);
      localPlayerIntentParams.nns_beginTime = paramCollectListItem.ts_begin.substring(8);
      if (!TextUtils.isEmpty(localPlayerIntentParams.nns_timeLen))
        break label250;
    }
    label231: label250: for (localPlayerIntentParams.mode = 6; ; localPlayerIntentParams.mode = 3)
    {
      Intent localIntent = new Intent().setClass(this.mContext, ActivityAdapter.getInstance().getMPlayer());
      localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_EXIT_APP, false);
      localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
      localIntent.addFlags(8388608);
      startActivity(localIntent);
      return;
      localPlayerIntentParams.nns_day = paramCollectListItem.ts_day;
      localPlayerIntentParams.nns_beginTime = paramCollectListItem.ts_begin;
      break;
    }
  }

  private void initPage()
  {
    XulArea localXulArea = (XulArea)xulFindViewById("right_content");
    XulSliderAreaWrapper.fromXulView(localXulArea).scrollTo(0, false);
    localXulArea.setDynamicFocus(null);
  }

  private void initViews()
  {
    this.delete_button = xulFindViewById("delete_button");
    this.mXulFilmListView = xulFindViewById("film_list_view");
    this.mXulFilmListWrapper = XulMassiveAreaWrapper.fromXulView(this.mXulFilmListView);
    this.mXulEmptyTips = xulFindViewById("page_details_empty_tips");
    registerReceiver();
    this.current_Action = getCurrentAction();
    checkRunType();
    startRunApi();
    updateTitle();
    this.isLoadSuccess = true;
    reportLoad(this.isLoadSuccess, null);
  }

  private boolean isListEmpty(List<?> paramList)
  {
    return (paramList == null) || (paramList.size() == 0);
  }

  private boolean loadMediaInfoByAction(String paramString)
  {
    if (this.current_Action.equals(paramString))
      return false;
    initPage();
    if (!TextUtils.isEmpty(this._deleteList))
      deleteVideoList();
    this.current_Action = paramString;
    onCheckApiFinished();
    return true;
  }

  private boolean loadMediaPage()
  {
    this.isCollectFinished = false;
    this.isRecordFinished = false;
    this.isTraceFinished = false;
    startRunApi();
    return true;
  }

  private void onCheckApiFinished()
  {
    if ("m_open_all_playbill_list_page".equals(this.current_Action))
      if ((this.isRecordFinished) && (this.isTraceFinished) && (this.isCollectFinished))
      {
        this.tracePlayList = UserCPLLogic.getInstance().getTracePlayList();
        this.collectList = UserCPLLogic.getInstance().getCollectList();
        this.playRecordList = UserCPLLogic.getInstance().getPlayRecordList();
        processTotalMediaInfo();
      }
    while (true)
    {
      dismissLoaddingDialog();
      return;
      if ("m_open_catch_page".equals(this.current_Action))
      {
        if (this.isTraceFinished)
        {
          this.tracePlayList = UserCPLLogic.getInstance().getTracePlayList();
          processEachMediaInfo(this.tracePlayList, 3);
        }
      }
      else if ("m_open_collect_page".equals(this.current_Action))
      {
        if (this.isCollectFinished)
        {
          this.collectList = UserCPLLogic.getInstance().getCollectList();
          processEachMediaInfo(this.collectList, 1);
        }
      }
      else if ("m_open_playlist_page".equals(this.current_Action))
      {
        if (this.isRecordFinished)
        {
          this.playRecordList = UserCPLLogic.getInstance().getPlayRecordList();
          processEachMediaInfo(this.playRecordList, 2);
        }
      }
      else
        showEmptyTips(true);
    }
  }

  private void onDeleteButtonClick(String paramString, VideoInfo paramVideoInfo, int paramInt)
  {
    if (this.mCollectLogic == null)
      this.mCollectLogic = new CollectAndPlayListLogic();
    Logger.i("onDeleteButtonClick videoId=" + paramString + ",type=" + paramInt);
    if (paramInt == 1)
      removeItemData(this.collectList, paramString);
    do
    {
      return;
      if (paramInt == 2)
      {
        removeItemData(this.playRecordList, paramString);
        return;
      }
    }
    while (paramInt != 3);
    removeItemData(this.tracePlayList, paramString);
  }

  private void processEachMediaInfo(List<CollectListItem> paramList, int paramInt)
  {
    boolean bool = isListEmpty(paramList);
    int i = 0;
    if (!bool)
    {
      Logger.i("MyMediaActivityV2", "processEachMediaInfo Collect List Size = " + paramList.size());
      i = 0 + paramList.size();
    }
    this.itemCount = i;
    Logger.i("MyMediaActivityV2", "processEachMediaInfo count_result = " + i);
    if (i == 0)
    {
      showEmptyTips(true);
      return;
    }
    showEmptyTips(false);
    showMediaPage(paramList, paramInt);
  }

  private void processTotalMediaInfo()
  {
    this.allItemList = getTotalItems();
    if (!isListEmpty(this.allItemList));
    for (this.itemCount = this.allItemList.size(); this.itemCount == 0; this.itemCount = 0)
    {
      showEmptyTips(true);
      return;
    }
    showEmptyTips(false);
    showTotalMediaPage(this.allItemList);
  }

  private void removeItemData(List<CollectListItem> paramList, String paramString)
  {
    CollectListItem localCollectListItem;
    if (paramList != null)
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        localCollectListItem = (CollectListItem)localIterator.next();
        if ((!TextUtils.isEmpty(localCollectListItem.video_id)) && (localCollectListItem.video_id.equals(paramString)))
        {
          paramList.remove(localCollectListItem);
          Logger.i("MyMediaActivityV2", "delete item:" + localCollectListItem.video_id + "," + paramList.size());
          if (!TextUtils.isEmpty(this._deleteList))
            break label127;
          this._deleteList = localCollectListItem.id;
        }
      }
    }
    return;
    label127: this._deleteList = (this._deleteList + "," + localCollectListItem.id);
  }

  private void removeMassiveView(final String paramString, int paramInt)
  {
    Logger.i("removeMassiveView=" + paramString);
    if (((paramInt == 2) && ("m_open_playlist_page".equals(this.current_Action))) || ((paramInt == 3) && ("m_open_catch_page".equals(this.current_Action))) || ((paramInt != 1) || (!"m_open_collect_page".equals(this.current_Action)) || (this.mXulFilmListWrapper == null)));
    do
    {
      do
      {
        return;
        this.removeId = -1;
        this.mXulFilmListWrapper.eachItem(new XulMassiveRender.DataItemIterator()
        {
          public void onDataItem(int paramAnonymousInt, XulDataNode paramAnonymousXulDataNode)
          {
            if (paramAnonymousXulDataNode == null);
            String str;
            do
            {
              return;
              str = paramAnonymousXulDataNode.getAttribute("video_id").getValue();
            }
            while (!paramString.equals(str));
            Logger.i("removeMassiveView idx=" + paramAnonymousInt);
            MyMediaActivityV2.access$902(MyMediaActivityV2.this, paramAnonymousInt);
          }
        });
      }
      while (this.removeId <= -1);
      this.mXulFilmListWrapper.removeItem(this.removeId);
      this.mXulFilmListWrapper.syncContentView();
    }
    while (this.mXulFilmListWrapper.itemNum() != 0);
    showEmptyTips(true);
  }

  private void setFocus()
  {
    if ((this.mXulFilmListWrapper == null) || (TextUtils.isEmpty(this.oldFocusVideoID)))
      return;
    int i = this.mXulFilmListWrapper.itemNum();
    if (i == 0)
    {
      this.oldFocusId = 0;
      this.oldFocusVideoID = "";
      return;
    }
    int j = this.oldFocusId;
    if (TextUtils.isEmpty(this.oldFocusVideoID))
      j = 0;
    final int[] arrayOfInt = { -1 };
    this.mXulFilmListWrapper.eachItem(new XulMassiveRender.DataItemIterator()
    {
      public void onDataItem(int paramAnonymousInt, XulDataNode paramAnonymousXulDataNode)
      {
        if (paramAnonymousXulDataNode == null);
        String str;
        do
        {
          return;
          str = paramAnonymousXulDataNode.getAttribute("video_id").getValue();
        }
        while (!MyMediaActivityV2.this.oldFocusVideoID.equals(str));
        Logger.i("setFocus idx=" + paramAnonymousInt + ",video_id=" + str);
        arrayOfInt[0] = paramAnonymousInt;
      }
    });
    if (j > i - 1)
      j = i - 1;
    if (j < 0)
      j = 0;
    if (arrayOfInt[0] == -1)
      arrayOfInt[0] = j;
    XulArea localXulArea = (XulArea)xulFindViewById("right_content");
    this.mXulFilmListWrapper.makeChildVisible(localXulArea, arrayOfInt[0], false, new Runnable()
    {
      public void run()
      {
        MyMediaActivityV2.this.xulRequestFocus(((XulArea)MyMediaActivityV2.this.mXulFilmListView).getChild(arrayOfInt[0]));
      }
    });
  }

  private void showEmptyTips(boolean paramBoolean)
  {
    String str2;
    XulView localXulView1;
    if (this.mXulFilmListView != null)
    {
      XulView localXulView2 = this.mXulFilmListView;
      if (!paramBoolean)
      {
        str2 = "block";
        localXulView2.setStyle("display", str2);
        this.mXulFilmListView.resetRender();
      }
    }
    else if (this.mXulEmptyTips != null)
    {
      this.mXulEmptyTips.setAttr("text", "对不起,  该选项暂无内容!");
      localXulView1 = this.mXulEmptyTips;
      if (!paramBoolean)
        break label136;
    }
    label136: for (String str1 = "block"; ; str1 = "none")
    {
      localXulView1.setStyle("display", str1);
      this.mXulEmptyTips.resetRender();
      if (paramBoolean)
      {
        if (this.delete_button != null)
        {
          this.delete_button.setStyle("display", "none");
          this.delete_button.resetRender();
          this.isDrawDeleteButton = false;
        }
        returnFocus();
      }
      return;
      str2 = "none";
      break;
    }
  }

  private void showMediaPage(List<CollectListItem> paramList, int paramInt)
  {
    if ((paramList == null) || (paramList.size() == 0))
    {
      showEmptyTips(true);
      return;
    }
    this.mXulFilmListWrapper.clear();
    updateDeleteButton();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
      addItem(-1, (CollectListItem)localIterator.next(), paramInt);
    this.mXulFilmListWrapper.syncContentView();
  }

  private void showTotalMediaPage(List<MediaItem> paramList)
  {
    if ((paramList == null) || (paramList.size() == 0))
    {
      showEmptyTips(true);
      return;
    }
    this.mXulFilmListWrapper.clear();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      MediaItem localMediaItem = (MediaItem)localIterator.next();
      if ((localMediaItem != null) && (localMediaItem.item != null))
        addItem(-1, localMediaItem.item, localMediaItem.MediaItemType);
    }
    this.mXulFilmListWrapper.syncContentView();
    updateDeleteButton();
  }

  private void startRunApi()
  {
    Logger.i("MyMediaActivityV2", "startRunApi isRunRecord=" + this.isRunRecord + ",isRunCollect=" + this.isRunCollect + ",isRunTrace=" + this.isRunTrace);
    if (this.isRunRecord)
      getPlayRecord();
    if (this.isRunCollect)
      getCollectRecord();
    if (this.isRunTrace)
      getTracePlayVideo();
  }

  private void updateDeleteButton()
  {
    if (this.delete_button != null)
    {
      if (this.current_Action.equals("m_open_all_playbill_list_page"))
        break label53;
      this.delete_button.setStyle("display", "block");
      this.delete_button.resetRender();
    }
    while (true)
    {
      xulFireEvent("appEvents:hidePosterDeleteButton");
      this.isDrawDeleteButton = false;
      return;
      label53: this.delete_button.setStyle("display", "none");
      this.delete_button.resetRender();
    }
  }

  private void updateTitle()
  {
    XulView localXulView = xulFindViewById("title");
    if (localXulView != null)
    {
      localXulView.setAttr("text", "片单");
      localXulView.resetRender();
    }
  }

  public void dealKeyEvent(KeyEvent paramKeyEvent)
  {
    Logger.i("MyMediaActivityV2", "dealKeyEvent " + paramKeyEvent.getKeyCode());
    if (paramKeyEvent.getKeyCode() == 20)
    {
      XulView localXulView = xulGetFocus();
      if (localXulView == null);
      int i;
      do
      {
        return;
        if ((this.mXulFilmListView == null) || (!localXulView.hasClass("poster-item")) || (this.mXulFilmListWrapper == null))
          break;
        i = this.mXulFilmListWrapper.itemNum();
      }
      while (i == 0);
      int j = this.mXulFilmListWrapper.getItemIdx(localXulView) / 6;
      if ((i + 5) / 6 == j + 2)
      {
        xulRequestFocus(((XulArea)this.mXulFilmListView).getChild(i - 1));
        return;
      }
    }
    super.dealKeyEvent(paramKeyEvent);
  }

  public void delCollectByCollectIds(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    if (paramString.contains(","))
    {
      String[] arrayOfString = paramString.split(",");
      for (int i = 0; i < arrayOfString.length; i++)
        UserCPLLogic.getInstance().delectCollect(arrayOfString[i], true);
    }
    UserCPLLogic.getInstance().delectCollect(paramString, true);
    ServerApiManager.i().APIDelCollectRecordV2(paramString, new SccmsApiDelCollectRecordV2Task.ISccmsApiDelColllectRecordV2TaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<CollectListItem> paramAnonymousArrayList)
      {
      }
    });
    this._deleteList = "";
  }

  public void delPlayRecordByCollectIds(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    if (paramString.contains(","))
    {
      String[] arrayOfString = paramString.split(",");
      for (int i = 0; i < arrayOfString.length; i++)
        UserCPLLogic.getInstance().deletePlayRecord(arrayOfString[i], true);
    }
    UserCPLLogic.getInstance().deletePlayRecord(paramString, true);
    ServerApiManager.i().APIDelPlayRecordV2(paramString, new SccmsApiDelPlayRecordV2Task.ISccmsApiDelPlayRecordV2TaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<CollectListItem> paramAnonymousArrayList)
      {
      }
    });
    this._deleteList = "";
  }

  public void delTracePlayByCollectIds(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    if (paramString.contains(","))
    {
      String[] arrayOfString = paramString.split(",");
      for (int i = 0; i < arrayOfString.length; i++)
        UserCPLLogic.getInstance().delectTracePlay(arrayOfString[i], false);
    }
    UserCPLLogic.getInstance().delectTracePlay(paramString, false);
    ServerApiManager.i().APIDelCatchVideoRecordV2(paramString, new VideoInfo(), new SccmsApiDelCatchVideoRecordV2Task.ISccmsApiDelCatchVideoRecordV2TaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<CollectListItem> paramAnonymousArrayList)
      {
      }
    });
    this._deleteList = "";
  }

  public CollectListItem findItemByVideoId(List<CollectListItem> paramList, String paramString)
  {
    if ((paramList != null) && (paramString != null))
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        CollectListItem localCollectListItem = (CollectListItem)localIterator.next();
        if (paramString.equals(localCollectListItem.video_id))
          return localCollectListItem;
      }
    }
    return null;
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    Logger.d("MyMediaActivityV2", "onActivityResult, requestCode=" + paramInt1 + ", resultCode=" + paramInt2);
  }

  public void onBackPressed()
  {
    if (!TextUtils.isEmpty(this._deleteList))
      deleteVideoList();
    if (AppFuncCfg.FUNCTION_GOTO_MAIN_IF_FROM_OUT)
      gotoMainActivityIfFromOut(false);
    super.onBackPressed();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mContext = this;
    this.mCollectLogic = new CollectAndPlayListLogic();
    initXul("MyMediaV2", true);
  }

  protected void onDestroy()
  {
    super.onDestroy();
    unregisterReceiver();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (this.isDrawDeleteButton))
    {
      this.isDrawDeleteButton = false;
      xulFireEvent("appEvents:hidePosterDeleteButton");
      return true;
    }
    if ((paramInt == 4) && (this.isFromOut))
    {
      AppKiller.getInstance().killApp();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  protected void onNewIntent(Intent paramIntent)
  {
    super.onNewIntent(paramIntent);
    if (!TextUtils.isEmpty(paramIntent.getStringExtra("cmd_is_from_out")))
      this.isFromOut = true;
  }

  protected void onPause()
  {
    super.onPause();
    Logger.i("MyMediaActivityV2", "Mymedia onPause");
    if (!TextUtils.isEmpty(this._deleteList))
      deleteVideoList();
  }

  protected void onRestart()
  {
    super.onRestart();
    reportLoad(this.isLoadSuccess, null);
  }

  protected void onResume()
  {
    xulPostDelay(new Runnable()
    {
      public void run()
      {
        MyMediaActivityV2.this.setFocus();
      }
    }
    , 100L);
    super.onResume();
  }

  protected void onStop()
  {
    super.onStop();
    reportExit(this.isLoadSuccess, null);
  }

  public void refreshViews()
  {
    super.refreshViews();
    showLoaddingDialog();
    loadMediaPage();
  }

  public void registerReceiver()
  {
    this.mRecevier = new MyMediaRecevier(null);
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.starcor.hunan.logic_event.user_cpl");
    LocalBroadcastManager.getInstance(this.mContext).registerReceiver(this.mRecevier, localIntentFilter);
  }

  public void returnFocus()
  {
    try
    {
      xulRequestFocus((XulView)XulGroupAreaWrapper.fromXulView(xulFindViewById("page_content_normal_group")).getAllCheckedItems().get(0));
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void unregisterReceiver()
  {
    if (this.mRecevier != null)
    {
      LocalBroadcastManager.getInstance(this.mContext).unregisterReceiver(this.mRecevier);
      this.mRecevier = null;
    }
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    Logger.i("xulDoAction action=" + paramString1 + ",type=" + paramString2 + ",command=" + paramString3);
    if ("delete_click".equals(paramString1))
    {
      if (this.isDrawDeleteButton)
      {
        this.isDrawDeleteButton = false;
        xulFireEvent("appEvents:hidePosterDeleteButton");
      }
      while (true)
      {
        return true;
        this.isDrawDeleteButton = true;
        xulFireEvent("appEvents:showPosterDeleteButton");
      }
    }
    if ("bindingUpdated".equals(paramString1))
    {
      new Handler().post(new Runnable()
      {
        public void run()
        {
          if (DeviceInfo.isXiaoMi())
            ActivityUserCheckLogic.getInstance().startXiaoMiUserCheck(MyMediaActivityV2.this.context);
          MyMediaActivityV2.this.initViews();
        }
      });
      return true;
    }
    label362: label364: Object localObject1;
    String str1;
    if ("click".equals(paramString1))
    {
      if ("onDeleteButtonClick".equals(paramString2))
      {
        String str2;
        int i;
        long l;
        try
        {
          boolean bool2 = TextUtils.isEmpty(paramString3);
          localObject2 = null;
          if (!bool2)
          {
            JSONObject localJSONObject2 = new JSONObject(paramString3);
            localObject2 = localJSONObject2;
          }
          if (localObject2 == null)
            break label362;
          str2 = localObject2.optString("videoId");
          i = localObject2.optInt("type");
          if (TextUtils.isEmpty(str2))
            return true;
        }
        catch (JSONException localJSONException2)
        {
          while (true)
          {
            localJSONException2.printStackTrace();
            Object localObject2 = null;
          }
          l = SystemTimeManager.getCurrentServerTime();
          if ((this.lastClickTime != 0L) && (l - this.lastClickTime < 300L))
            return true;
          if (i != 3)
            break label364;
        }
        CollectListItem localCollectListItem2 = getItem(str2);
        VideoInfo localVideoInfo = new VideoInfo();
        localVideoInfo.videoId = localCollectListItem2.video_id;
        localVideoInfo.packageId = localCollectListItem2.media_assets_id;
        localVideoInfo.categoryId = localCollectListItem2.category_id;
        localVideoInfo.videoType = localCollectListItem2.video_type;
        localVideoInfo.view_type = localCollectListItem2.uiStyle;
        onDeleteButtonClick(str2, localVideoInfo, i);
        while (true)
        {
          removeMassiveView(str2, i);
          this.lastClickTime = l;
          if (this.mXulFilmListWrapper.itemNum() == 0)
            showEmptyTips(true);
          return true;
          onDeleteButtonClick(str2, null, i);
        }
      }
      if ("usr_cmd".equals(paramString2))
      {
        if (loadMediaInfoByAction(paramString3))
          return true;
      }
      else if ("open_poster".equals(paramString2))
      {
        CollectListItem localCollectListItem1;
        try
        {
          boolean bool1 = TextUtils.isEmpty(paramString3);
          localObject1 = null;
          if (!bool1)
          {
            JSONObject localJSONObject1 = new JSONObject(paramString3);
            localObject1 = localJSONObject1;
          }
          if (localObject1 == null)
            break label606;
          str1 = localObject1.optString("videoId");
          Logger.i("open_poster videoId=" + str1);
          this.oldFocusVideoID = str1;
          this.oldFocusId = localObject1.optInt("idx");
          if (TextUtils.isEmpty(str1))
            return true;
        }
        catch (JSONException localJSONException1)
        {
          while (true)
          {
            localJSONException1.printStackTrace();
            localObject1 = null;
          }
          localCollectListItem1 = getItem(str1);
          if (localCollectListItem1.online == 0)
          {
            CommDialog localCommDialog = new CommDialog(this.mContext, 2131296258);
            localCommDialog.setMessage("该影片已下线，请选择其他影片观看");
            localCommDialog.setType(1);
            localCommDialog.setTitle("提示  按“返回”键取消");
            localCommDialog.setNegativeButton(new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
              {
                paramAnonymousDialogInterface.dismiss();
              }
            });
            localCommDialog.show();
            return true;
          }
          if (localCollectListItem1.video_type != 1)
            break label618;
        }
        goToPlayer(localCollectListItem1);
      }
    }
    while (true)
    {
      label606: return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
      label618: MovieData localMovieData = new MovieData();
      localMovieData.videoId = str1;
      localMovieData.packageId = localObject1.optString("media_asset_id");
      localMovieData.categoryId = localObject1.optString("categoryId");
      localMovieData.videoType = localObject1.optInt("videoType");
      localMovieData.viewType = localObject1.optInt("viewType");
      localMovieData.name = localObject1.optString("name");
      localMovieData.img_v = localObject1.optString("imgUrl");
      startDetailPageActivity(localMovieData);
    }
  }

  protected void xulOnRenderIsReady()
  {
    super.xulOnRenderIsReady();
    if (GlobalLogic.getInstance().isAppInterfaceReady())
    {
      if (DeviceInfo.isXiaoMi())
        ActivityUserCheckLogic.getInstance().startXiaoMiUserCheck(this.context);
      initViews();
      return;
    }
    App.getInstance().setOnserviceOKListener(new OnServiceOK(null));
  }

  private class ComparatorItem
    implements Comparator<MyMediaActivityV2.MediaItem>
  {
    private ComparatorItem()
    {
    }

    private int compare(long paramLong1, long paramLong2)
    {
      if (paramLong1 < paramLong2)
        return 1;
      if (paramLong1 == paramLong2)
        return 0;
      return -1;
    }

    public int compare(MyMediaActivityV2.MediaItem paramMediaItem1, MyMediaActivityV2.MediaItem paramMediaItem2)
    {
      if (paramMediaItem1 != null)
        return compare(paramMediaItem1.item.getDateInSeconds(), paramMediaItem2.item.getDateInSeconds());
      return 0;
    }
  }

  class MediaItem
  {
    int MediaItemType;
    CollectListItem item;

    MediaItem()
    {
    }
  }

  private class MyMediaRecevier extends BroadcastReceiver
  {
    private MyMediaRecevier()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      String str1 = paramIntent.getStringExtra("Cmd");
      Logger.i("MyMediaActivityV2", "onReceive  cmdString:" + str1);
      if (TextUtils.isEmpty(str1));
      String str2;
      do
      {
        do
        {
          do
          {
            do
            {
              return;
              str2 = paramIntent.getStringExtra("VideoId");
            }
            while (TextUtils.isEmpty(str2));
            if (str1.equals("com.starcor.hunan.cmd.notify_add_traceplay"))
            {
              MyMediaActivityV2.access$1102(MyMediaActivityV2.this, UserCPLLogic.getInstance().getTracePlayList());
              MyMediaActivityV2.this.addMassiveView(str2, 3, MyMediaActivityV2.this.tracePlayList);
              return;
            }
            if (str1.equals("com.starcor.hunan.cmd.notify_add_collect"))
            {
              MyMediaActivityV2.access$1302(MyMediaActivityV2.this, UserCPLLogic.getInstance().getCollectList());
              MyMediaActivityV2.this.addMassiveView(str2, 1, MyMediaActivityV2.this.collectList);
              return;
            }
            if (str1.equals("com.starcor.hunan.cmd.notify_add_play_record"))
            {
              MyMediaActivityV2.access$1402(MyMediaActivityV2.this, UserCPLLogic.getInstance().getPlayRecordList());
              MyMediaActivityV2.this.addMassiveView(str2, 2, MyMediaActivityV2.this.playRecordList);
              return;
            }
            if (!str1.equals("com.starcor.hunan.cmd.notify_delete_collect"))
              break;
          }
          while (MyMediaActivityV2.this.findItemByVideoId(MyMediaActivityV2.this.collectList, str2) == null);
          MyMediaActivityV2.this.removeMassiveView(str2, 1);
          MyMediaActivityV2.this.removeItemData(MyMediaActivityV2.this.collectList, str2);
          return;
          if (!str1.equals("com.starcor.hunan.cmd.notify_delete_traceplay"))
            break;
        }
        while (MyMediaActivityV2.this.findItemByVideoId(MyMediaActivityV2.this.tracePlayList, str2) == null);
        MyMediaActivityV2.this.removeMassiveView(str2, 3);
        MyMediaActivityV2.this.removeItemData(MyMediaActivityV2.this.tracePlayList, str2);
        return;
      }
      while ((!str1.equals("com.starcor.hunan.cmd.notify_delete_play_record")) || (MyMediaActivityV2.this.findItemByVideoId(MyMediaActivityV2.this.playRecordList, str2) == null));
      MyMediaActivityV2.this.removeMassiveView(str2, 2);
      MyMediaActivityV2.this.removeItemData(MyMediaActivityV2.this.playRecordList, str2);
    }
  }

  private class OnApiOk
    implements InitApiData.onApiOKListener
  {
    private OnApiOk()
    {
    }

    public void onApiOk(int paramInt)
    {
      if (paramInt == 1)
      {
        Logger.i("MyMediaActivityV2", "onApiOk xulRefreshBinding api_n36");
        MyMediaActivityV2.this.xulRefreshBinding("api_n36");
      }
    }

    public void onGetApiDataError()
    {
      Logger.i("MyMediaActivityV2", "onGetApiDataError");
    }

    public void onNeedFinishActivity()
    {
      MyMediaActivityV2.this.finish();
    }
  }

  private class OnServiceOK
    implements App.OnServiceOKListener
  {
    private OnServiceOK()
    {
    }

    public void onServiceOK()
    {
      Logger.i("MyMediaActivityV2", "onServiceOK");
      new InitApiData(MyMediaActivityV2.this).setOnApiOkListener(new MyMediaActivityV2.OnApiOk(MyMediaActivityV2.this, null));
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.MyMediaActivityV2
 * JD-Core Version:    0.6.2
 */