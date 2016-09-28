package com.starcor.hunan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.starcor.core.ai.AIDataCacheTask;
import com.starcor.core.domain.CategoryPoster;
import com.starcor.core.domain.CategoryPosterList;
import com.starcor.core.domain.GetPlaybillSelectedListInfo;
import com.starcor.core.domain.GetPlaybillSelectedListInfo.Item;
import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.domain.MovieData;
import com.starcor.core.domain.SpecialTopicPutInfo;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.hunan.uilogic.InitApiData;
import com.starcor.hunan.uilogic.InitApiData.onApiOKListener;
import com.starcor.report.Column.RecommendColumn;
import com.starcor.sccms.api.SccmsApiGetDynamicCategoryItemListTask.ISccmsApiGetDynamicCategoryItemListTaskListener;
import com.starcor.sccms.api.SccmsApiGetPlaybillSelectedList.ISccmsApiGetPlaybillSelectedListTaskListener;
import com.starcor.sccms.api.SccmsApiGetStaticCategoryItemListTask.ISccmsApiGetStaticCategoryItemListTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.Wrapper.XulMassiveAreaWrapper;
import com.starcor.xul.Wrapper.XulSliderAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class VideoListActivityV2 extends DialogActivity
{
  private static final int DOWNLOAD_PAGE_SIZE = 36;
  public static final int MAX_CATEGORY = 30;
  private static final int MESSAGE_RESET_INDICATOR_DOWN = 1002;
  private static final int MESSAGE_RESET_INDICATOR_UP = 1001;
  private static final int MESSAGE_SWITCH_CATEGORY = 1003;
  public static final int[] REFRESH_DYNAMIC_CATEGORY_TYPE = { 9 };
  public static final int[] REQUEST_DYNAMIC_CATEGORY_TYPE;
  public static final int[] REQUEST_STATIC_CATEGORY_TYPE = { 0, 1, 8, 9, 10, 2, 6 };
  private static final int RESET_INDICATOR_DELAY = 400;
  public static final int SPECIAL_SIZE_PER_CATEGORY = 50;
  public static final int SPECIAL_TYPE_LIVESHOW = 2;
  public static final int SPECIAL_TYPE_NORMAL = 0;
  public static final int SPECIAL_TYPE_PMP = 4;
  public static final int SPECIAL_TYPE_TEMPLATE = 3;
  public static final int SPECIAL_TYPE_WEB = 1;
  private static final int SWITCH_CATEGORY_DELAY = 300;
  private static final String TAG = "VideoListActivityV2";
  public static final int VIDEO_SIZE_PER_CATEGORY = 12;
  private ByteArrayInputStream mCategoryListInputStream = null;
  private LinkedHashMap<String, CategoryPosterList> mCategoryPosterDynamicMap = new LinkedHashMap();
  private LinkedHashMap<String, CategoryPosterList> mCategoryPosterListMap = new LinkedHashMap();
  private CategoryPosterList mCurrentCPList = null;
  private String mCurrentCategoryId = "";
  private int mCurrentDownloadPage = 0;
  private int mCurrentPage = 1;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
      case 19:
      case 20:
      case 1001:
      case 1002:
        while (true)
        {
          if (VideoListActivityV2.this.mXulPageUpIndicatorView != null)
            VideoListActivityV2.this.mXulPageUpIndicatorView.resetRender();
          if (VideoListActivityV2.this.mXulPageDownIndicatorView != null)
            VideoListActivityV2.this.mXulPageDownIndicatorView.resetRender();
          return;
          if (VideoListActivityV2.this.mXulPageUpIndicatorView != null)
          {
            VideoListActivityV2.this.mXulPageUpIndicatorView.setAttr("img.1.visible", "true");
            VideoListActivityV2.this.mXulPageUpIndicatorView.setAttr("img.0.visible", "false");
            VideoListActivityV2.this.resetPageIndicator(1001);
            continue;
            if (VideoListActivityV2.this.mXulPageDownIndicatorView != null)
            {
              VideoListActivityV2.this.mXulPageDownIndicatorView.setAttr("img.1.visible", "true");
              VideoListActivityV2.this.mXulPageDownIndicatorView.setAttr("img.0.visible", "false");
              VideoListActivityV2.this.resetPageIndicator(1002);
              continue;
              if (VideoListActivityV2.this.mXulPageUpIndicatorView != null)
              {
                VideoListActivityV2.this.mXulPageUpIndicatorView.setAttr("img.1.visible", "false");
                VideoListActivityV2.this.mXulPageUpIndicatorView.setAttr("img.0.visible", "true");
                continue;
                if (VideoListActivityV2.this.mXulPageDownIndicatorView != null)
                {
                  VideoListActivityV2.this.mXulPageDownIndicatorView.setAttr("img.1.visible", "false");
                  VideoListActivityV2.this.mXulPageDownIndicatorView.setAttr("img.0.visible", "true");
                }
              }
            }
          }
        }
      case 1003:
      }
      if (VideoListActivityV2.this.mHandler.hasMessages(1003))
      {
        VideoListActivityV2.this.mHandler.removeMessages(1003);
        VideoListActivityV2.this.mHandler.sendEmptyMessageDelayed(1003, 300L);
        return;
      }
      VideoListActivityV2.this.switchCategory(VideoListActivityV2.this.mCurrentCategoryId);
    }
  };
  private boolean mIsGetFocusFirstPlayRecord = true;
  private boolean mIsLoadSuccess = false;
  private boolean mIsNeedRefreshPlayRecord = false;
  private HashMap<String, String> mLikeListReportData = new HashMap();
  private String mPacketId = "";
  private int mTotalPage = -1;
  private XulView mXulEmptyTips;
  private XulView mXulPageDownIndicatorView;
  private XulView mXulPageUpIndicatorView;
  private XulArea mXulVideoListSlider;
  private XulView mXulVideoListView;
  private XulMassiveAreaWrapper mXulVideoListWrapper;

  static
  {
    REQUEST_DYNAMIC_CATEGORY_TYPE = new int[] { 8, 9 };
  }

  private XulView getCurrentCategoryView()
  {
    XulArrayList localXulArrayList = xulFindViewsByClass("category_action");
    Object localObject = (XulView)localXulArrayList.get(0);
    Iterator localIterator = localXulArrayList.iterator();
    while (localIterator.hasNext())
    {
      XulView localXulView = (XulView)localIterator.next();
      if (this.mCurrentCategoryId.equals(localXulView.getDataString("categoryId")))
        localObject = localXulView;
    }
    return localObject;
  }

  private void initData(Intent paramIntent)
  {
    if (!TextUtils.isEmpty(getIntent().getStringExtra("cmd_is_from_out")))
      this.mPacketId = getIntent().getStringExtra("packet_id");
    MetadataInfo localMetadataInfo;
    for (this.mCurrentCategoryId = getIntent().getStringExtra("category_id"); TextUtils.isEmpty(this.mPacketId); this.mCurrentCategoryId = localMetadataInfo.category_id)
    {
      Logger.e("VideoListActivityV2", "packet_id is null, show ErrorDialog!");
      return;
      localMetadataInfo = (MetadataInfo)paramIntent.getSerializableExtra("MetaDataInfo");
      this.mPacketId = localMetadataInfo.packet_id;
    }
    if (this.mCurrentCategoryId.length() != 7)
      this.mCurrentCategoryId = "";
    SccmsApiGetStaticCategoryItemListTaskListener localSccmsApiGetStaticCategoryItemListTaskListener = new SccmsApiGetStaticCategoryItemListTaskListener(null);
    ServerApiManager.i().ApiGetStaticCategoryItemList(this.mPacketId, 12, 50, 30, REQUEST_STATIC_CATEGORY_TYPE, localSccmsApiGetStaticCategoryItemListTaskListener);
    SccmsApiGetDynamicCategoryItemListTaskListener localSccmsApiGetDynamicCategoryItemListTaskListener = new SccmsApiGetDynamicCategoryItemListTaskListener(null);
    ServerApiManager.i().ApiGetDynamicCategoryItemList(this.mPacketId, REQUEST_DYNAMIC_CATEGORY_TYPE, localSccmsApiGetDynamicCategoryItemListTaskListener);
  }

  private void loadMoreData(int paramInt)
  {
    this.mCurrentDownloadPage = (paramInt / 36);
    Logger.d("VideoListActivityV2", "loadMoreData(" + paramInt + ") mCurrentDownloadPage = " + this.mCurrentDownloadPage);
    SccmsApiGetPlaybillSelectedListTaskListener localSccmsApiGetPlaybillSelectedListTaskListener = new SccmsApiGetPlaybillSelectedListTaskListener(null);
    ServerApiManager.i().APIGetPlaybillSelectedList(this.mPacketId, this.mCurrentCategoryId, 36, this.mCurrentDownloadPage, localSccmsApiGetPlaybillSelectedListTaskListener);
    if (1 + this.mCurrentDownloadPage < this.mTotalPage)
      ServerApiManager.i().APIGetPlaybillSelectedList(this.mPacketId, this.mCurrentCategoryId, 36, 1 + this.mCurrentDownloadPage, localSccmsApiGetPlaybillSelectedListTaskListener);
    if (-1 + this.mCurrentDownloadPage > 0)
      ServerApiManager.i().APIGetPlaybillSelectedList(this.mPacketId, this.mCurrentCategoryId, 36, -1 + this.mCurrentDownloadPage, localSccmsApiGetPlaybillSelectedListTaskListener);
  }

  private boolean needVideoUpdate(int paramInt)
  {
    XulDataNode localXulDataNode = this.mXulVideoListWrapper.getItem(paramInt);
    return (localXulDataNode != null) && ("true".equals(localXulDataNode.getAttribute("isPlaceHolder")));
  }

  private void processError(String paramString1, String paramString2, ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
  {
    Logger.e("VideoListActivityV2", "processError!!!!!");
    this.mIsLoadSuccess = false;
    boolean bool = this.mIsLoadSuccess;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.mPacketId;
    reportLoad(bool, arrayOfString);
    showErrorDialogWithReport(11, paramString1, paramString2, paramServerApiTaskInfo, paramServerApiCommonError);
  }

  private void resetPageIndicator(int paramInt)
  {
    if (this.mHandler.hasMessages(paramInt))
      this.mHandler.removeMessages(paramInt);
    this.mHandler.sendEmptyMessageDelayed(paramInt, 400L);
  }

  private void setPageInfo(int paramInt)
  {
    if (this.mCurrentCPList == null)
    {
      this.mTotalPage = 0;
      this.mCurrentPage = 0;
    }
    while (true)
    {
      XulView localXulView1 = xulFindViewById("page_details_info");
      if (localXulView1 != null)
      {
        localXulView1.setAttr("text", String.valueOf(this.mCurrentPage));
        localXulView1.resetRender();
      }
      XulView localXulView2 = xulFindViewById("page_details_info_total");
      if (localXulView2 != null)
      {
        localXulView2.setAttr("text", " / " + this.mTotalPage);
        localXulView2.resetRender();
      }
      return;
      int i = this.mCurrentCPList.specialCount;
      if (this.mCurrentCPList.posterList.size() > 0)
        this.mTotalPage = (1 + (-1 + this.mCurrentCPList.videoCount + 2 * this.mCurrentCPList.specialCount) / 12);
      while (true)
        if (paramInt == 0)
        {
          if (this.mTotalPage == 0)
          {
            this.mCurrentPage = 0;
            break;
            this.mTotalPage = 0;
            continue;
          }
          this.mCurrentPage = 1;
          break;
        }
      if (this.mCurrentCPList.categoryType == 2)
        this.mCurrentPage = (1 + paramInt / 6);
      else if ("special".equals(this.mXulVideoListWrapper.getItemView(paramInt).getDataString("posterType")))
        this.mCurrentPage = (1 + paramInt / 6);
      else
        this.mCurrentPage = (1 + (paramInt - i + i * 2) / 12);
    }
  }

  private void showEmptyTips(boolean paramBoolean)
  {
    String str2;
    XulView localXulView1;
    if (this.mXulVideoListView != null)
    {
      XulView localXulView2 = this.mXulVideoListView;
      if (!paramBoolean)
      {
        str2 = "block";
        localXulView2.setStyle("display", str2);
        this.mXulVideoListView.resetRender();
      }
    }
    else if (this.mXulEmptyTips != null)
    {
      this.mXulEmptyTips.setAttr("text", "对不起,  该选项暂无内容!");
      localXulView1 = this.mXulEmptyTips;
      if (!paramBoolean)
        break label96;
    }
    label96: for (String str1 = "block"; ; str1 = "none")
    {
      localXulView1.setStyle("display", str1);
      this.mXulEmptyTips.resetRender();
      return;
      str2 = "none";
      break;
    }
  }

  private void startDetailPageActivity(JSONObject paramJSONObject)
  {
    if (paramJSONObject.optBoolean("isPlaceHolder", true))
    {
      Logger.w("VideoListActivityV2", "This is a place holder, please click after poster updated.");
      return;
    }
    MovieData localMovieData = new MovieData();
    localMovieData.packageId = this.mPacketId;
    localMovieData.categoryId = this.mCurrentCategoryId;
    localMovieData.videoId = paramJSONObject.optString("id");
    localMovieData.videoType = paramJSONObject.optInt("videoType");
    localMovieData.viewType = paramJSONObject.optInt("viewType");
    localMovieData.name = paramJSONObject.optString("name");
    String str = paramJSONObject.optString("imgUrl", "");
    if ((str.equals("scale:")) || (str.equals("sift:img_v,img_h")))
      str = "";
    localMovieData.img_v = str;
    localMovieData.indexUIStyle = paramJSONObject.optString("indexUiStyle");
    startDetailPageActivity(localMovieData);
  }

  private void startFilterPageActivity()
  {
    Intent localIntent = new Intent(this, FilterPageActivity.class);
    localIntent.putExtra("packageId", this.mPacketId);
    localIntent.addFlags(8388608);
    startActivity(localIntent);
  }

  private void startSearchPageActivity()
  {
    Intent localIntent = new Intent(this, ActivityAdapter.getInstance().getSearchActivity());
    localIntent.addFlags(8388608);
    startActivity(localIntent);
  }

  private void startSpecialActivity(JSONObject paramJSONObject)
  {
    Intent localIntent;
    switch (paramJSONObject.optInt("specialType", 0))
    {
    case 3:
    default:
      SpecialTopicPutInfo localSpecialTopicPutInfo = new SpecialTopicPutInfo();
      localSpecialTopicPutInfo.id = paramJSONObject.optString("id");
      localSpecialTopicPutInfo.name = paramJSONObject.optString("name");
      localSpecialTopicPutInfo.poster = paramJSONObject.optString("specialBkg");
      localSpecialTopicPutInfo.play_type = paramJSONObject.optString("specialPlayType");
      localSpecialTopicPutInfo.summary = paramJSONObject.optString("specialSummary");
      localSpecialTopicPutInfo.online_mode = paramJSONObject.optString("specialOnlineMode");
      localIntent = new Intent(this, SpecialActivityV2.class);
      localIntent.putExtra("special_info", localSpecialTopicPutInfo);
    case 1:
    case 2:
    case 4:
    }
    while (true)
    {
      localIntent.addFlags(8388608);
      startActivity(localIntent);
      return;
      MetadataInfo localMetadataInfo = new MetadataInfo();
      localMetadataInfo.packet_id = this.mPacketId;
      localMetadataInfo.category_id = paramJSONObject.optString("categoryId");
      localMetadataInfo.url = paramJSONObject.optString("specialUrl");
      localMetadataInfo.name = paramJSONObject.optString("name");
      localMetadataInfo.action_type = "web";
      localIntent = new Intent(this, ActivityAdapter.getInstance().getWebActivity());
      localIntent.putExtra("MetaDataInfo", localMetadataInfo);
      continue;
      localIntent = new Intent(this, LiveShowActivity.class);
      localIntent.putExtra("xulPageId", "LiveShow");
      continue;
      localIntent = new Intent(this, PopularMoviePreviewActivity.class);
      localIntent.putExtra("xulPageId", "PopularMoviePreview");
      localIntent.putExtra("xulData", "");
      localIntent.putExtra("special_package_id", paramJSONObject.optString("id"));
    }
  }

  private void startSpecialPlayerActivity(JSONObject paramJSONObject)
  {
    Intent localIntent = new Intent(this, SpecialPlayerActivity.class);
    String str1 = paramJSONObject.optString("id");
    String str2 = paramJSONObject.optString("specialBkg");
    localIntent.putExtra("special_id", str1);
    localIntent.putExtra("poster", str2);
    localIntent.addFlags(8388608);
    startActivity(localIntent);
  }

  private void startStarDetailedActivity(JSONObject paramJSONObject)
  {
    Intent localIntent = new Intent(this, StarDetailedActivity.class);
    localIntent.putExtra("actor", paramJSONObject.optString("name"));
    localIntent.putExtra("actorID", paramJSONObject.optString("id"));
    localIntent.putExtra("labelID_V2", paramJSONObject.optString("starLabelId"));
    localIntent.addFlags(8388608);
    startActivity(localIntent);
  }

  private void switchCategory(String paramString)
  {
    Logger.i("VideoListActivityV2", "switchCategory, categoryId = " + paramString);
    if ((this.mXulVideoListWrapper == null) || (this.mCategoryPosterListMap == null) || (this.mCategoryPosterListMap.isEmpty()))
      Logger.e("VideoListActivityV2", "switchCategory, return");
    label169: label311: label872: 
    do
    {
      CategoryPosterList localCategoryPosterList;
      int i;
      do
      {
        return;
        this.mCurrentCategoryId = paramString;
        this.mCurrentDownloadPage = 0;
        XulSliderAreaWrapper.fromXulView(this.mXulVideoListSlider).scrollTo(0, false);
        this.mXulVideoListSlider.setDynamicFocus(null);
        this.mXulVideoListSlider.setEnabled(true);
        this.mXulVideoListWrapper.clear();
        localCategoryPosterList = null;
        Iterator localIterator = this.mCategoryPosterListMap.entrySet().iterator();
        boolean bool = localIterator.hasNext();
        i = 0;
        if (bool)
        {
          localCategoryPosterList = (CategoryPosterList)((Map.Entry)localIterator.next()).getValue();
          if (!TextUtils.isEmpty(paramString))
            break;
          i = 1;
        }
        if (i == 0)
          localCategoryPosterList = (CategoryPosterList)((Map.Entry)this.mCategoryPosterListMap.entrySet().iterator().next()).getValue();
        if ((this.mCategoryPosterDynamicMap != null) && (("G000000".equals(paramString)) || ("P000000".equals(paramString))))
          localCategoryPosterList = (CategoryPosterList)this.mCategoryPosterDynamicMap.get(paramString);
        Logger.i("VideoListActivityV2", "switchCategory, CategoryPosterList = " + localCategoryPosterList);
      }
      while (localCategoryPosterList == null);
      this.mCurrentCPList = localCategoryPosterList;
      setPageInfo(0);
      String str1 = "";
      String str2 = "";
      String str3 = "";
      int j = localCategoryPosterList.posterList.size();
      int k = 0;
      if (k < j)
      {
        CategoryPoster localCategoryPoster = (CategoryPoster)localCategoryPosterList.posterList.get(k);
        XulDataNode localXulDataNode2 = XulDataNode.obtainDataNode("item");
        localXulDataNode2.setAttribute("id", localCategoryPoster.id);
        localXulDataNode2.setAttribute("name", localCategoryPoster.name);
        localXulDataNode2.setAttribute("packetId", this.mPacketId);
        localXulDataNode2.setAttribute("categoryId", localCategoryPosterList.categoryId);
        localXulDataNode2.setAttribute("posterType", localCategoryPoster.posterType);
        localXulDataNode2.setAttribute("videoType", String.valueOf(localCategoryPoster.videoType));
        localXulDataNode2.setAttribute("viewType", String.valueOf(localCategoryPoster.viewType));
        localXulDataNode2.setAttribute("uiStyle", localCategoryPoster.uiStyle);
        localXulDataNode2.setAttribute("indexUiStyle", localCategoryPoster.indexUiStyle);
        localXulDataNode2.setAttribute("img_v", localCategoryPoster.img_v);
        localXulDataNode2.setAttribute("img_h", localCategoryPoster.img_h);
        localXulDataNode2.setAttribute("corner_mark_img", localCategoryPoster.cornerMarkImg);
        localXulDataNode2.setAttribute("abstract", localCategoryPoster.abstractInfo);
        localXulDataNode2.setAttribute("categoryType", String.valueOf(localCategoryPosterList.categoryType));
        localXulDataNode2.setAttribute("asset_import_id", localCategoryPoster.importId);
        localXulDataNode2.setAttribute("series_id", localCategoryPoster.seriesId);
        if (!TextUtils.isEmpty(localCategoryPoster.definition))
        {
          if (localCategoryPoster.definition.contains("recom"))
            localXulDataNode2.setAttribute("recom", "荐");
          localXulDataNode2.setAttribute("corner_mark", localCategoryPoster.definition);
        }
        localXulDataNode2.setAttribute("isPlaceHolder", "false");
        if ((k < 3) && (localCategoryPosterList.categoryType == 6))
          localXulDataNode2.setAttribute("top10info", "top10_" + (k + 1));
        if ("special".equals(localCategoryPoster.posterType))
        {
          localXulDataNode2.setAttribute("specialType", String.valueOf(localCategoryPoster.specialType));
          localXulDataNode2.setAttribute("specialBkg", localCategoryPoster.specialBkg);
          localXulDataNode2.setAttribute("specialOnlineMode", localCategoryPoster.specialOnlineMode);
          localXulDataNode2.setAttribute("specialUrl", localCategoryPoster.specialUrl);
          localXulDataNode2.setAttribute("specialSummary", localCategoryPoster.specialSummary);
          localXulDataNode2.setAttribute("specialPlayType", localCategoryPoster.specialPlayType);
        }
        if (10 == localCategoryPosterList.categoryType)
          localXulDataNode2.setAttribute("starLabelId", localCategoryPoster.starLabelId);
        int i1;
        if (9 == localCategoryPosterList.categoryType)
        {
          int n = localCategoryPoster.timeLength;
          i1 = 0;
          if (n != 0)
            i1 = 100 * localCategoryPoster.playedTime / localCategoryPoster.timeLength;
          if (localCategoryPoster.playedTime == 0)
            i1 = 100;
          if (i1 <= 100)
            break label1085;
          i1 = 100;
          if (i1 == 100)
            break label1096;
          localXulDataNode2.setAttribute("corner_mark", "播放至" + i1 + "%");
        }
        while (true)
        {
          if (8 == localCategoryPosterList.categoryType)
          {
            if (!TextUtils.isEmpty(localCategoryPoster.id))
              str1 = str1 + "," + localCategoryPoster.id;
            if (!TextUtils.isEmpty(localCategoryPoster.importId))
              str2 = str2 + "," + localCategoryPoster.importId;
            if (!TextUtils.isEmpty(localCategoryPoster.seriesId))
              str3 = str3 + "," + localCategoryPoster.seriesId;
          }
          this.mXulVideoListWrapper.addItem(localXulDataNode2);
          k++;
          break label311;
          if (!localCategoryPosterList.categoryId.equals(paramString))
            break;
          i = 1;
          break label169;
          if (i1 > 0)
            break label872;
          i1 = 1;
          break label872;
          localXulDataNode2.setAttribute("corner_mark", "");
        }
      }
      for (int m = 0; m < localCategoryPosterList.videoCount - j; m++)
      {
        XulDataNode localXulDataNode1 = XulDataNode.obtainDataNode("item");
        localXulDataNode1.setAttribute("img_v", "");
        localXulDataNode1.setAttribute("isPlaceHolder", "true");
        this.mXulVideoListWrapper.addItem(localXulDataNode1);
      }
      this.mXulVideoListWrapper.syncContentView();
      if (8 == localCategoryPosterList.categoryType)
      {
        this.mLikeListReportData.put("pos", "");
        this.mLikeListReportData.put("page", String.valueOf(this.mCurrentPage));
        this.mLikeListReportData.put("hitid", "");
        this.mLikeListReportData.put("ohitid", "");
        this.mLikeListReportData.put("sohitid", "");
        this.mLikeListReportData.put("ver", localCategoryPosterList.artithmeticId);
        this.mLikeListReportData.put("reqid", localCategoryPosterList.estimateId);
        this.mLikeListReportData.put("limit", "12");
        HashMap localHashMap1 = this.mLikeListReportData;
        if (str1.length() > 1)
          str1 = str1.substring(1);
        localHashMap1.put("rcdata", str1);
        HashMap localHashMap2 = this.mLikeListReportData;
        if (str2.length() > 1)
          str2 = str2.substring(1);
        localHashMap2.put("orcdata", str2);
        HashMap localHashMap3 = this.mLikeListReportData;
        if (str3.length() > 1)
          str3 = str3.substring(1);
        localHashMap3.put("sorcdata", str3);
        reportShowRecommend(RecommendColumn.VIDEOLIST_LIKE_REGION, this.mLikeListReportData);
      }
      if (j == 0)
      {
        showEmptyTips(true);
        return;
      }
      showEmptyTips(false);
    }
    while ((this.mCurrentCategoryId.equals("G000000")) || (this.mCurrentCategoryId.equals("P000000")));
    label1085: label1096: JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("media_asset_id", this.mPacketId);
      localJSONObject.put("category_id", this.mCurrentCategoryId);
      localJSONObject.put("page_index", 0);
      localJSONObject.put("page_size", 36);
      AIDataCacheTask.i().filterJavaAction("ai_switch_main_media_asset_category", "ai_focus", localJSONObject.toString(), "");
      return;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        Logger.w("VideoListActivityV2", "JSONException", localJSONException);
    }
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 0)
      switch (paramKeyEvent.getKeyCode())
      {
      default:
      case 19:
      case 20:
      }
    while (true)
    {
      return super.dispatchKeyEvent(paramKeyEvent);
      if ((this.mHandler != null) && (this.mXulVideoListView.hasFocus()))
      {
        this.mHandler.sendEmptyMessage(19);
        continue;
        if ((this.mHandler != null) && (this.mXulVideoListView.hasFocus()))
          this.mHandler.sendEmptyMessage(20);
      }
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Logger.i("VideoListActivityV2", "VideoListActivityV2 onCreate");
    initXul("VideoListPageV2", true);
  }

  protected void onProgressDialogDismissWithBackPressed()
  {
    Logger.d("VideoListActivityV2", "onProgressDialogDismissWithBackPressed mIsLoadSuccess = " + this.mIsLoadSuccess);
    if (!this.mIsLoadSuccess)
      finish();
  }

  protected void onRestart()
  {
    super.onRestart();
    boolean bool = this.mIsLoadSuccess;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.mPacketId;
    reportLoad(bool, arrayOfString);
    if ((this.mCurrentCPList != null) && (this.mCurrentCPList.categoryType == 8))
    {
      this.mLikeListReportData.put("pos", "");
      this.mLikeListReportData.put("page", String.valueOf(this.mCurrentPage));
      this.mLikeListReportData.put("hitid", "");
      this.mLikeListReportData.put("ohitid", "");
      this.mLikeListReportData.put("sohitid", "");
      reportShowRecommend(RecommendColumn.VIDEOLIST_LIKE_REGION, this.mLikeListReportData);
    }
    this.mIsNeedRefreshPlayRecord = true;
    SccmsApiGetDynamicCategoryItemListTaskListener localSccmsApiGetDynamicCategoryItemListTaskListener = new SccmsApiGetDynamicCategoryItemListTaskListener(null);
    ServerApiManager.i().ApiGetDynamicCategoryItemList(this.mPacketId, REFRESH_DYNAMIC_CATEGORY_TYPE, localSccmsApiGetDynamicCategoryItemListTaskListener);
  }

  protected void onStop()
  {
    super.onStop();
    this.mIsNeedRefreshPlayRecord = false;
    boolean bool = this.mIsLoadSuccess;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.mPacketId;
    reportExit(bool, arrayOfString);
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    Logger.d("VideoListActivityV2", "xulDoAction!! action:" + paramString1 + " type:" + paramString2 + " cmd:" + paramString3 + " data:" + paramObject);
    try
    {
      JSONObject localJSONObject1 = new JSONObject(paramString3);
      localJSONObject2 = localJSONObject1;
      if ("focus".equals(paramString1))
      {
        if (!"setPageInfo".equals(paramString2))
          break label198;
        if (localJSONObject2 != null)
          setPageInfo(localJSONObject2.optInt("idx"));
        return true;
      }
    }
    catch (JSONException localJSONException)
    {
      JSONObject localJSONObject2;
      while (true)
      {
        Logger.w("VideoListActivityV2", "xulDoAction command warning!");
        localJSONObject2 = null;
      }
      if ("click".equals(paramString1))
        if ("switchCategory".equals(paramString2))
        {
          this.mCurrentCategoryId = localJSONObject2.optString("categoryId");
          this.mXulVideoListSlider.setEnabled(false);
          if ("P000000".equals(this.mCurrentCategoryId))
            this.mIsGetFocusFirstPlayRecord = false;
          this.mHandler.sendEmptyMessageDelayed(1003, 300L);
        }
      label198: label374: 
      do
      {
        do
        {
          do
          {
            return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
            if ("open_detail_page".equals(paramString2))
            {
              int j;
              if (localJSONObject2 != null)
              {
                startDetailPageActivity(localJSONObject2);
                if (this.mCurrentCPList.categoryType == 8)
                {
                  this.mLikeListReportData.put("page", String.valueOf(this.mCurrentPage));
                  j = 1 + localJSONObject2.optInt("idx");
                  if (j % 12 <= 0)
                    break label374;
                }
              }
              for (int k = j % 12; ; k = 12)
              {
                this.mLikeListReportData.put("pos", String.valueOf(k));
                this.mLikeListReportData.put("hitid", localJSONObject2.optString("id"));
                this.mLikeListReportData.put("ohitid", localJSONObject2.optString("importId"));
                this.mLikeListReportData.put("sohitid", localJSONObject2.optString("seriesId"));
                reportClickRecommend(RecommendColumn.VIDEOLIST_LIKE_REGION, this.mLikeListReportData);
                return true;
              }
            }
            if ("open_search_page".equals(paramString2))
            {
              startSearchPageActivity();
              return true;
            }
            if ("open_filter_page".equals(paramString2))
            {
              startFilterPageActivity();
              return true;
            }
            if ("open_special_page".equals(paramString2))
            {
              if (localJSONObject2 != null)
              {
                if (!"5".equals(localJSONObject2.optString("specialType")))
                  break label453;
                startSpecialPlayerActivity(localJSONObject2);
              }
              while (true)
              {
                return true;
                startSpecialActivity(localJSONObject2);
              }
            }
          }
          while (!"open_star_detail_page".equals(paramString2));
          if (localJSONObject2 != null)
            startStarDetailedActivity(localJSONObject2);
          return true;
        }
        while ("processError".equals(paramString1));
        if ("startLoading".equals(paramString1))
        {
          showLoaddingDialog();
          return true;
        }
        if ("stopLoading".equals(paramString1))
        {
          dismissLoaddingDialog();
          return true;
        }
        if ("category_binding_update".equals(paramString1))
        {
          getXulRenderContext().scheduleLayoutFinishedTask(new Runnable()
          {
            public void run()
            {
              VideoListActivityV2.this.xulRequestFocus(VideoListActivityV2.this.getCurrentCategoryView());
            }
          });
          return true;
        }
        if ("playrecord_binding_finish".equals(paramString1))
        {
          if ((this.mIsNeedRefreshPlayRecord) && (this.mCurrentCategoryId.equals("P000000")))
          {
            this.mIsNeedRefreshPlayRecord = false;
            getXulRenderContext().scheduleLayoutFinishedTask(new Runnable()
            {
              public void run()
              {
                if ((VideoListActivityV2.this.mXulVideoListWrapper.itemNum() > 0) && (VideoListActivityV2.this.mIsGetFocusFirstPlayRecord))
                {
                  VideoListActivityV2.this.xulRequestFocus(((XulArea)VideoListActivityV2.this.mXulVideoListView).getChild(0));
                  return;
                }
                VideoListActivityV2.this.xulRequestFocus(VideoListActivityV2.this.getCurrentCategoryView());
              }
            });
          }
          return true;
        }
      }
      while (!"scrollStopped".equals(paramString1));
      label453: if ("load_more_data".equals(paramString2))
      {
        XulView localXulView = xulGetFocus();
        int i = this.mXulVideoListWrapper.getItemIdx(localXulView);
        if (i > -1)
          loadMoreData(i);
      }
    }
    return true;
  }

  protected InputStream xulGetAppData(XulWorker.DownloadItem paramDownloadItem, String paramString)
  {
    if ("info/category_list".equals(paramString))
      return this.mCategoryListInputStream;
    return null;
  }

  protected void xulOnRenderIsReady()
  {
    Logger.i("VideoListActivityV2", "VideoListActivityV2 xulOnRenderIsReady");
    this.mXulVideoListView = xulFindViewById("video_list");
    this.mXulVideoListWrapper = XulMassiveAreaWrapper.fromXulView(this.mXulVideoListView);
    this.mXulVideoListSlider = ((XulArea)xulFindViewById("video_list_slider"));
    this.mXulEmptyTips = xulFindViewById("page_details_empty_tips");
    this.mXulPageUpIndicatorView = xulFindViewById("page_indicator_up");
    this.mXulPageDownIndicatorView = xulFindViewById("page_indicator_down");
    if (!GlobalLogic.getInstance().isAppInterfaceReady())
      App.getInstance().setOnserviceOKListener(new OnServiceOK(null));
    while (true)
    {
      super.xulOnRenderIsReady();
      return;
      initData(getIntent());
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
        Logger.i("VideoListActivityV2", "OnApiOk");
        VideoListActivityV2.this.initData(VideoListActivityV2.this.getIntent());
      }
    }

    public void onGetApiDataError()
    {
      Logger.e("VideoListActivityV2", "onGetApiDataError");
    }

    public void onNeedFinishActivity()
    {
      VideoListActivityV2.this.finish();
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
      Logger.i("VideoListActivityV2", "onServiceOK");
      new InitApiData(VideoListActivityV2.this).setOnApiOkListener(new VideoListActivityV2.OnApiOk(VideoListActivityV2.this, null));
    }
  }

  private final class SccmsApiGetDynamicCategoryItemListTaskListener
    implements SccmsApiGetDynamicCategoryItemListTask.ISccmsApiGetDynamicCategoryItemListTaskListener
  {
    private SccmsApiGetDynamicCategoryItemListTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.e("n39_a_26 SccmsApiGetDynamicCategoryItemListTaskListener.onError");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, LinkedHashMap<String, CategoryPosterList> paramLinkedHashMap)
    {
      if (!VideoListActivityV2.this.mIsNeedRefreshPlayRecord)
      {
        VideoListActivityV2.this.mCategoryPosterDynamicMap.putAll(paramLinkedHashMap);
        return;
      }
      CategoryPosterList localCategoryPosterList1 = (CategoryPosterList)VideoListActivityV2.this.mCategoryPosterDynamicMap.get("P000000");
      CategoryPosterList localCategoryPosterList2 = (CategoryPosterList)paramLinkedHashMap.get("P000000");
      CategoryPoster localCategoryPoster1 = null;
      if (localCategoryPosterList1 != null)
      {
        int j = localCategoryPosterList1.posterList.size();
        localCategoryPoster1 = null;
        if (j > 0)
          localCategoryPoster1 = (CategoryPoster)localCategoryPosterList1.posterList.get(0);
      }
      CategoryPoster localCategoryPoster2 = null;
      if (localCategoryPosterList2 != null)
      {
        int i = localCategoryPosterList2.posterList.size();
        localCategoryPoster2 = null;
        if (i > 0)
          localCategoryPoster2 = (CategoryPoster)localCategoryPosterList2.posterList.get(0);
      }
      if (localCategoryPoster1 != null)
        VideoListActivityV2.access$1402(VideoListActivityV2.this, localCategoryPoster1.isPlayRecordChanged(localCategoryPoster2));
      while (true)
      {
        VideoListActivityV2.this.mCategoryPosterDynamicMap.putAll(paramLinkedHashMap);
        if ((!VideoListActivityV2.this.mIsNeedRefreshPlayRecord) || (!VideoListActivityV2.this.mCurrentCategoryId.equals("P000000")))
          break;
        VideoListActivityV2.access$602(VideoListActivityV2.this, true);
        VideoListActivityV2.this.switchCategory(VideoListActivityV2.this.mCurrentCategoryId);
        return;
        if (localCategoryPoster2 != null)
          VideoListActivityV2.access$1402(VideoListActivityV2.this, true);
        else
          VideoListActivityV2.access$1402(VideoListActivityV2.this, false);
      }
    }
  }

  private final class SccmsApiGetPlaybillSelectedListTaskListener
    implements SccmsApiGetPlaybillSelectedList.ISccmsApiGetPlaybillSelectedListTaskListener
  {
    private SccmsApiGetPlaybillSelectedListTaskListener()
    {
    }

    private void onGetVideoList(GetPlaybillSelectedListInfo paramGetPlaybillSelectedListInfo)
    {
      if ((paramGetPlaybillSelectedListInfo == null) || (paramGetPlaybillSelectedListInfo.items == null))
        return;
      int i;
      ArrayList localArrayList;
      label30: GetPlaybillSelectedListInfo.Item localItem;
      XulDataNode localXulDataNode;
      if (paramGetPlaybillSelectedListInfo.pageIndex == 0)
      {
        i = 12;
        localArrayList = new ArrayList();
        if (i >= paramGetPlaybillSelectedListInfo.items.size())
          break label382;
        localItem = (GetPlaybillSelectedListInfo.Item)paramGetPlaybillSelectedListInfo.items.get(i);
        localXulDataNode = XulDataNode.obtainDataNode("item");
        if (!"special".equals(localItem.type))
          break label366;
        localXulDataNode.setAttribute("id", localItem.special_id);
      }
      while (true)
      {
        localXulDataNode.setAttribute("name", localItem.name);
        localXulDataNode.setAttribute("packetId", VideoListActivityV2.this.mPacketId);
        localXulDataNode.setAttribute("categoryId", localItem.category_id);
        localXulDataNode.setAttribute("uiStyle", String.valueOf(localItem.ui_style));
        localXulDataNode.setAttribute("img_v", localItem.img_v);
        localXulDataNode.setAttribute("img_h", localItem.img_h);
        localXulDataNode.setAttribute("corner_mark_img", localItem.mark_img);
        localXulDataNode.setAttribute("abstract", localItem.abstractInfo);
        if (!TextUtils.isEmpty(localItem.mark))
        {
          if (localItem.mark.contains("recom"))
            localXulDataNode.setAttribute("recom", "荐");
          localXulDataNode.setAttribute("corner_mark", localItem.mark);
        }
        localXulDataNode.setAttribute("specialType", localItem.type);
        localXulDataNode.setAttribute("specialBkg", localItem.poster);
        localXulDataNode.setAttribute("specialOnlineMode", localItem.online_mode);
        localXulDataNode.setAttribute("specialUrl", localItem.url);
        localXulDataNode.setAttribute("specialSummary", localItem.summary);
        localXulDataNode.setAttribute("specialPlayType", localItem.play_type);
        localXulDataNode.setAttribute("isPlaceHolder", "false");
        localXulDataNode.setAttribute("starLabelId", localItem.label_id);
        localArrayList.add(localXulDataNode);
        i++;
        break label30;
        i = 0;
        break;
        label366: localXulDataNode.setAttribute("id", localItem.video_id);
      }
      label382: if (paramGetPlaybillSelectedListInfo.pageIndex == 0);
      for (int j = 12 + VideoListActivityV2.this.mCurrentCPList.specialCount; ; j = 36 * paramGetPlaybillSelectedListInfo.pageIndex)
      {
        VideoListActivityV2.this.mXulVideoListWrapper.updateItems(j, localArrayList);
        return;
      }
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      VideoListActivityV2.this.dismissLoaddingDialog();
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, GetPlaybillSelectedListInfo paramGetPlaybillSelectedListInfo)
    {
      if (paramGetPlaybillSelectedListInfo == null)
      {
        Logger.e("VideoListActivityV2", "SccmsApiGetPlaybillSelectedListTaskListener onSuccess(), but result = null !!!");
        return;
      }
      onGetVideoList(paramGetPlaybillSelectedListInfo);
    }
  }

  private final class SccmsApiGetStaticCategoryItemListTaskListener
    implements SccmsApiGetStaticCategoryItemListTask.ISccmsApiGetStaticCategoryItemListTaskListener
  {
    private SccmsApiGetStaticCategoryItemListTaskListener()
    {
    }

    private void buildCategoryList(LinkedHashMap<String, CategoryPosterList> paramLinkedHashMap)
    {
      XmlSerializer localXmlSerializer;
      StringWriter localStringWriter;
      try
      {
        localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        localStringWriter = new StringWriter();
        localXmlSerializer.setOutput(localStringWriter);
        localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer.startTag(null, "category_list");
        VideoListActivityV2.this.writeNodeValue(localXmlSerializer, "packetId", VideoListActivityV2.this.mPacketId);
        Iterator localIterator = paramLinkedHashMap.entrySet().iterator();
        while (localIterator.hasNext())
        {
          localXmlSerializer.startTag(null, "category");
          CategoryPosterList localCategoryPosterList = (CategoryPosterList)((Map.Entry)localIterator.next()).getValue();
          VideoListActivityV2.this.writeNodeValue(localXmlSerializer, "categoryId", localCategoryPosterList.categoryId);
          VideoListActivityV2.this.writeNodeValue(localXmlSerializer, "categoryName", localCategoryPosterList.categoryName);
          VideoListActivityV2.this.writeNodeValue(localXmlSerializer, "categoryType", String.valueOf(localCategoryPosterList.categoryType));
          localXmlSerializer.endTag(null, "category");
        }
      }
      catch (Exception localException)
      {
        VideoListActivityV2.access$1302(VideoListActivityV2.this, null);
        Logger.e("VideoListActivityV2", "build category list error!", localException);
        return;
      }
      localXmlSerializer.endTag(null, "category_list");
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      String str = localStringWriter.toString();
      VideoListActivityV2.access$1302(VideoListActivityV2.this, new ByteArrayInputStream(str.getBytes("UTF-8")));
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.e("n39_a_22 SccmsApiGetStaticCategoryItemListTaskListener.onError");
      VideoListActivityV2.this.processError("1002005", "ISccmsApiGetStaticCategoryItemListTaskListener.onError", paramServerApiTaskInfo, paramServerApiCommonError);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, LinkedHashMap<String, CategoryPosterList> paramLinkedHashMap, MediaAssetsInfo paramMediaAssetsInfo)
    {
      if ((paramLinkedHashMap == null) || (paramLinkedHashMap.size() == 0))
      {
        VideoListActivityV2.this.processError("1002005", "n39_a_22 return is empty", paramServerApiTaskInfo, null);
        return;
      }
      VideoListActivityV2.access$1002(VideoListActivityV2.this, true);
      VideoListActivityV2 localVideoListActivityV2 = VideoListActivityV2.this;
      boolean bool = VideoListActivityV2.this.mIsLoadSuccess;
      String[] arrayOfString = new String[1];
      arrayOfString[0] = VideoListActivityV2.this.mPacketId;
      localVideoListActivityV2.reportLoad(bool, arrayOfString);
      VideoListActivityV2.this.mCategoryPosterListMap.putAll(paramLinkedHashMap);
      buildCategoryList(VideoListActivityV2.this.mCategoryPosterListMap);
      VideoListActivityV2.this.xulRefreshBinding("category_list", "file:///.app/info/category_list");
      VideoListActivityV2.this.dismissLoaddingDialog();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.VideoListActivityV2
 * JD-Core Version:    0.6.2
 */