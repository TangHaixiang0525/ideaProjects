package com.starcor.hunan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.starcor.core.domain.CollectListItem;
import com.starcor.core.domain.FilmItem;
import com.starcor.core.domain.FilmListItem;
import com.starcor.core.domain.GetPlaybillSelectedListInfo;
import com.starcor.core.domain.GetPlaybillSelectedListInfo.Item;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.domain.MovieData;
import com.starcor.core.domain.SpecialTopicPutInfo;
import com.starcor.core.domain.UserRecommendV2Item;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.report.ReportArea;
import com.starcor.report.ReportInfo;
import com.starcor.sccms.api.SccmsApiGetPlaybillSelectedList.ISccmsApiGetPlaybillSelectedListTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.Wrapper.XulMassiveAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class MorePageActivity extends DialogActivity
{
  private static final int DOWNLOAD_PAGE_SIZE = 36;
  public static final int SPECIAL_TYPE_LIVESHOW = 2;
  public static final int SPECIAL_TYPE_NORMAL = 0;
  public static final int SPECIAL_TYPE_PMP = 4;
  public static final int SPECIAL_TYPE_TEMPLATE = 3;
  public static final int SPECIAL_TYPE_WEB = 1;
  private static final String TAG = MorePageActivity.class.getSimpleName();
  private int count = 0;
  private int currentDownLoadPage = 0;
  private boolean isDownloadingMore = false;
  boolean isInSpecialProgram = false;
  boolean isLoadLikeList = false;
  boolean isLoadPlayList = false;
  private boolean isLoadSuccess = false;
  private String mCategoryId;
  private String mCategoryName;
  private String mCategoryType;
  private String mPackageId;
  private String mTotalVideo;
  private XulView mXulEmptyTips;
  private XulView mXulFilmListView;
  private XulMassiveAreaWrapper mXulFilmListWrapper;
  private ArrayList<CollectListItem> playListOfVideoListPage;
  private ArrayList<UserRecommendV2Item> userRecommendV2Items;

  private void loadData()
  {
    if (String.valueOf(9).equals(this.mCategoryType))
    {
      this.playListOfVideoListPage = GlobalLogic.getInstance().getPlaylistByMediaAssets();
      return;
    }
    if (String.valueOf(8).equals(this.mCategoryType))
    {
      this.userRecommendV2Items = GlobalLogic.getInstance().getLikeListMediaAssets();
      return;
    }
    SccmsApiGetPlaybillSelectedListTaskListener localSccmsApiGetPlaybillSelectedListTaskListener = new SccmsApiGetPlaybillSelectedListTaskListener(null);
    ServerApiManager.i().APIGetPlaybillSelectedList(this.mPackageId, this.mCategoryId, 36, this.currentDownLoadPage, localSccmsApiGetPlaybillSelectedListTaskListener);
  }

  private void onGetGuessLikeVideoList(ArrayList<UserRecommendV2Item> paramArrayList)
  {
    boolean bool;
    if (this.count <= 0)
    {
      bool = true;
      showEmptyTips(bool);
      if ((paramArrayList != null) && (!paramArrayList.isEmpty()))
        break label31;
    }
    label31: 
    do
    {
      return;
      bool = false;
      break;
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext())
      {
        UserRecommendV2Item localUserRecommendV2Item = (UserRecommendV2Item)localIterator.next();
        XulDataNode localXulDataNode = XulDataNode.obtainDataNode("item");
        localXulDataNode.setAttribute("name", localUserRecommendV2Item.name);
        localXulDataNode.setAttribute("ui_style", localUserRecommendV2Item.ui_style + "");
        localXulDataNode.setAttribute("video_id", localUserRecommendV2Item.video_id);
        localXulDataNode.setAttribute("video_type", localUserRecommendV2Item.video_type + "");
        localXulDataNode.setAttribute("media_asset_id", localUserRecommendV2Item.media_assets_id);
        localXulDataNode.setAttribute("category_id", localUserRecommendV2Item.category_id);
        localXulDataNode.setAttribute("img_v", localUserRecommendV2Item.img_v);
        localXulDataNode.setAttribute("img_h", localUserRecommendV2Item.img_h);
        localXulDataNode.setAttribute("corner_mark", localUserRecommendV2Item.corner_mark);
        localXulDataNode.setAttribute("corner_mark_img", localUserRecommendV2Item.corner_mark_img);
        localXulDataNode.setAttribute("view_type", localUserRecommendV2Item.view_type + "");
        localXulDataNode.setAttribute("abstract", localUserRecommendV2Item.desc);
        this.mXulFilmListWrapper.addItem(localXulDataNode);
      }
      this.mXulFilmListWrapper.syncContentView();
    }
    while (this.currentDownLoadPage != 0);
    xulRequestFocus(((XulArea)this.mXulFilmListView).getChild(0));
  }

  private void onGetPlayVideoList(ArrayList<CollectListItem> paramArrayList)
  {
    boolean bool;
    if (this.count <= 0)
    {
      bool = true;
      showEmptyTips(bool);
      if ((paramArrayList != null) && (!paramArrayList.isEmpty()))
        break label31;
    }
    label31: 
    do
    {
      return;
      bool = false;
      break;
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext())
      {
        CollectListItem localCollectListItem = (CollectListItem)localIterator.next();
        XulDataNode localXulDataNode = XulDataNode.obtainDataNode("item");
        localXulDataNode.setAttribute("name", localCollectListItem.video_name);
        localXulDataNode.setAttribute("ui_style", localCollectListItem.uiStyle + "");
        localXulDataNode.setAttribute("video_id", localCollectListItem.video_id);
        localXulDataNode.setAttribute("video_type", localCollectListItem.video_type + "");
        localXulDataNode.setAttribute("media_asset_id", localCollectListItem.media_assets_id);
        localXulDataNode.setAttribute("category_id", localCollectListItem.category_id);
        localXulDataNode.setAttribute("img_v", localCollectListItem.img_v);
        localXulDataNode.setAttribute("img_h", localCollectListItem.img_h);
        localXulDataNode.setAttribute("corner_mark", localCollectListItem.mark);
        localXulDataNode.setAttribute("corner_mark_img", localCollectListItem.corner_mark_img);
        localXulDataNode.setAttribute("abstract", localCollectListItem.info);
        this.mXulFilmListWrapper.addItem(localXulDataNode);
      }
      this.mXulFilmListWrapper.syncContentView();
    }
    while (this.currentDownLoadPage != 0);
    xulRequestFocus(((XulArea)this.mXulFilmListView).getChild(0));
  }

  private void onGetVideoList(FilmItem paramFilmItem)
  {
    boolean bool;
    if (this.count <= 0)
    {
      bool = true;
      showEmptyTips(bool);
      if (paramFilmItem.filmList != null)
        break label27;
    }
    label27: 
    do
    {
      return;
      bool = false;
      break;
      Iterator localIterator = paramFilmItem.filmList.iterator();
      while (localIterator.hasNext())
      {
        FilmListItem localFilmListItem = (FilmListItem)localIterator.next();
        XulDataNode localXulDataNode = XulDataNode.obtainDataNode("item");
        localXulDataNode.setAttribute("name", localFilmListItem.film_name);
        localXulDataNode.setAttribute("ui_style", localFilmListItem.uiStyle + "");
        localXulDataNode.setAttribute("video_id", localFilmListItem.video_id);
        localXulDataNode.setAttribute("video_type", localFilmListItem.video_type + "");
        localXulDataNode.setAttribute("media_asset_id", localFilmListItem.package_id);
        localXulDataNode.setAttribute("category_id", localFilmListItem.category_id);
        localXulDataNode.setAttribute("img_v", localFilmListItem.v_img_url);
        localXulDataNode.setAttribute("img_h", localFilmListItem.h_img_url);
        localXulDataNode.setAttribute("corner_mark", localFilmListItem.corner_mark);
        localXulDataNode.setAttribute("corner_mark_img", localFilmListItem.corner_mark_img_url);
        localXulDataNode.setAttribute("view_type", localFilmListItem.viewType + "");
        localXulDataNode.setAttribute("abstract", localFilmListItem.updateInfo);
        this.mXulFilmListWrapper.addItem(localXulDataNode);
      }
      this.mXulFilmListWrapper.syncContentView();
    }
    while (this.currentDownLoadPage != 0);
    xulRequestFocus(((XulArea)this.mXulFilmListView).getChild(0));
  }

  private void onGetVideoList(GetPlaybillSelectedListInfo paramGetPlaybillSelectedListInfo)
  {
    if ((paramGetPlaybillSelectedListInfo == null) || (paramGetPlaybillSelectedListInfo.items == null));
    do
    {
      return;
      if (this.count <= 0)
        this.count = paramGetPlaybillSelectedListInfo.items.size();
      if (this.count <= 0);
      for (boolean bool = true; ; bool = false)
      {
        showEmptyTips(bool);
        Iterator localIterator1 = paramGetPlaybillSelectedListInfo.items.iterator();
        while (localIterator1.hasNext())
        {
          GetPlaybillSelectedListInfo.Item localItem = (GetPlaybillSelectedListInfo.Item)localIterator1.next();
          XulDataNode localXulDataNode = XulDataNode.obtainDataNode("item");
          localXulDataNode.setAttribute("name", localItem.name);
          localXulDataNode.setAttribute("ui_style", localItem.ui_style + "");
          localXulDataNode.setAttribute("video_id", localItem.video_id);
          localXulDataNode.setAttribute("video_type", localItem.video_type + "");
          localXulDataNode.setAttribute("media_asset_id", localItem.media_assets_id);
          localXulDataNode.setAttribute("category_id", localItem.category_id);
          localXulDataNode.setAttribute("category_type", this.mCategoryType);
          localXulDataNode.setAttribute("img_v", localItem.img_v);
          localXulDataNode.setAttribute("img_h", localItem.img_h);
          localXulDataNode.setAttribute("corner_mark", localItem.mark);
          localXulDataNode.setAttribute("corner_mark_img", localItem.mark_img);
          localXulDataNode.setAttribute("view_type", localItem.type + "");
          localXulDataNode.setAttribute("actor_id", localItem.id + "");
          localXulDataNode.setAttribute("label_id", localItem.label_id + "");
          localXulDataNode.setAttribute("id", localItem.special_id);
          localXulDataNode.setAttribute("poster", localItem.poster);
          localXulDataNode.setAttribute("type", localItem.type);
          localXulDataNode.setAttribute("url", localItem.url);
          localXulDataNode.setAttribute("play_type", localItem.play_type);
          localXulDataNode.setAttribute("summary", localItem.summary);
          localXulDataNode.setAttribute("online_mode", localItem.online_mode);
          localXulDataNode.setAttribute("mark_img", localItem.mark_img);
          this.mXulFilmListWrapper.addItem(localXulDataNode);
        }
      }
      this.mXulFilmListWrapper.syncContentView();
      this.mXulFilmListView.getOwnerPage();
      XulArrayList localXulArrayList = XulPage.findItemsByClass((XulArea)this.mXulFilmListView, "poster-item-image");
      if ((String.valueOf(10).equals(this.mCategoryType)) && (localXulArrayList != null) && (!localXulArrayList.isEmpty()))
      {
        Iterator localIterator3 = localXulArrayList.iterator();
        while (localIterator3.hasNext())
        {
          XulView localXulView2 = (XulView)localIterator3.next();
          localXulView2.getParent().addClass("poster-item-star");
          localXulView2.setAttr("img.1", "file:///.assets/videolist/default_star.png");
          localXulView2.setAttr("img.2", "");
          localXulView2.resetRender();
        }
      }
      if ((String.valueOf(2).equals(this.mCategoryType)) && (localXulArrayList != null) && (!localXulArrayList.isEmpty()))
      {
        Iterator localIterator2 = localXulArrayList.iterator();
        while (localIterator2.hasNext())
        {
          XulView localXulView1 = (XulView)localIterator2.next();
          localXulView1.getParent().removeClass("poster-item");
          localXulView1.getParent().addClass("special-poster-item");
          localXulView1.setAttr("width", "516");
          localXulView1.setAttr("img.3.width", "516");
          localXulView1.setAttr("img.4.width", "516");
          localXulView1.setAttr("img.5.width", "516");
          localXulView1.resetRender();
        }
      }
    }
    while (this.currentDownLoadPage != 0);
    xulRequestFocus(((XulArea)this.mXulFilmListView).getChild(0));
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

  private void showLikeListOrPlayList()
  {
    if (String.valueOf(9).equals(this.mCategoryType))
      if (!this.isLoadPlayList)
      {
        onGetPlayVideoList(this.playListOfVideoListPage);
        this.isLoadPlayList = true;
      }
    while ((!String.valueOf(8).equals(this.mCategoryType)) || (this.isLoadLikeList))
      return;
    onGetGuessLikeVideoList(this.userRecommendV2Items);
    this.isLoadLikeList = true;
  }

  private void startSpecialActivity(JSONObject paramJSONObject)
  {
    Intent localIntent;
    switch (paramJSONObject.optInt("type", 0))
    {
    case 3:
    default:
      SpecialTopicPutInfo localSpecialTopicPutInfo = new SpecialTopicPutInfo();
      localSpecialTopicPutInfo.id = paramJSONObject.optString("id");
      localSpecialTopicPutInfo.name = paramJSONObject.optString("name");
      localSpecialTopicPutInfo.poster = paramJSONObject.optString("poster");
      localSpecialTopicPutInfo.play_type = paramJSONObject.optString("play_type");
      localSpecialTopicPutInfo.summary = paramJSONObject.optString("summary");
      localSpecialTopicPutInfo.online_mode = paramJSONObject.optString("online_mode");
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
      localMetadataInfo.packet_id = this.mPackageId;
      localMetadataInfo.category_id = paramJSONObject.optString("categoryId");
      localMetadataInfo.url = paramJSONObject.optString("url");
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

  private void startStarDetailedActivity(String paramString1, String paramString2, String paramString3)
  {
    Intent localIntent = new Intent(this, StarDetailedActivity.class);
    localIntent.putExtra("actor", paramString1);
    localIntent.putExtra("actorID", paramString2);
    localIntent.putExtra("labelID", paramString3);
    localIntent.addFlags(8388608);
    startActivity(localIntent);
  }

  private void updateTitle()
  {
    XulView localXulView = xulFindViewById("title");
    if (localXulView != null)
    {
      localXulView.setAttr("text", this.mCategoryName);
      localXulView.resetRender();
    }
  }

  public void dealKeyEvent(KeyEvent paramKeyEvent)
  {
    Logger.i(TAG, "dealKeyEvent " + paramKeyEvent.getKeyCode());
    if (paramKeyEvent.getKeyCode() == 20)
    {
      XulView localXulView = xulGetFocus();
      if (localXulView == null);
      do
      {
        do
        {
          return;
          if (!this.isInSpecialProgram)
            break;
          if ((this.mXulFilmListView == null) || (!localXulView.hasClass("special-poster-item")))
            break label249;
        }
        while (this.count == 0);
        int j = this.mXulFilmListWrapper.getItemIdx(localXulView);
        if ((this.count % 3 <= 0) || (j + 1 <= -3 + (this.count - this.count % 3)) || (j + 1 > this.count - this.count % 3))
          break;
        xulRequestFocus(((XulArea)this.mXulFilmListView).getLastChild());
        return;
        if ((this.mXulFilmListView == null) || (!localXulView.hasClass("poster-item")))
          break;
      }
      while (this.count == 0);
      int i = this.mXulFilmListWrapper.getItemIdx(localXulView);
      if ((this.count % 6 > 0) && (i + 1 > -6 + (this.count - this.count % 6)) && (i + 1 <= this.count - this.count % 6))
      {
        xulRequestFocus(((XulArea)this.mXulFilmListView).getLastChild());
        return;
      }
    }
    label249: super.dealKeyEvent(paramKeyEvent);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    initXul("MorePage", true);
    Intent localIntent = getIntent();
    this.mPackageId = localIntent.getStringExtra("packageId");
    this.mCategoryId = localIntent.getStringExtra("categoryId");
    this.mCategoryName = localIntent.getStringExtra("categoryName");
    this.mTotalVideo = localIntent.getStringExtra("totalVideo");
    this.mCategoryType = localIntent.getStringExtra("categoryType");
    if (!TextUtils.isEmpty(this.mTotalVideo));
    for (this.count = Integer.parseInt(this.mTotalVideo); ; this.count = 0)
    {
      loadData();
      String str = ReportArea.getValue(MorePageActivity.class.getSimpleName());
      ReportInfo.getInstance().setEntranceSrc(str);
      return;
    }
  }

  protected void onDestroy()
  {
    Logger.i(TAG, "onDestroy()");
    super.onDestroy();
  }

  protected void onRestart()
  {
    super.onRestart();
    reportLoad(this.isLoadSuccess, null);
    String str = ReportArea.getValue(MorePageActivity.class.getSimpleName());
    ReportInfo.getInstance().setEntranceSrc(str);
  }

  protected void onStop()
  {
    super.onStop();
    reportExit(this.isLoadSuccess, null);
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    Logger.d(TAG, "xulDoAction!! action:" + paramString1 + " type:" + paramString2 + " cmd:" + paramString3 + " data:" + paramObject);
    try
    {
      JSONObject localJSONObject1 = new JSONObject(paramString3);
      localJSONObject2 = localJSONObject1;
      if ("focus".equals(paramString1))
      {
        if (!"load_more_data".equals(paramString2))
          break label360;
        int i = localJSONObject2.optInt("idx");
        if ((this.count >= 36) && (!this.isDownloadingMore) && (i + 36 >= this.mXulFilmListWrapper.itemNum()))
        {
          this.isDownloadingMore = true;
          this.currentDownLoadPage = (1 + this.currentDownLoadPage);
          loadData();
        }
        return true;
      }
    }
    catch (JSONException localJSONException)
    {
      JSONObject localJSONObject2;
      do
      {
        do
        {
          do
          {
            while (true)
            {
              localJSONException.printStackTrace();
              localJSONObject2 = null;
            }
            if (!"click".equals(paramString1))
              break label360;
            if (!"open_detail_page".equals(paramString2))
              break;
          }
          while (localJSONObject2 == null);
          MovieData localMovieData = new MovieData();
          localMovieData.packageId = this.mPackageId;
          localMovieData.categoryId = this.mCategoryId;
          localMovieData.videoId = localJSONObject2.optString("videoId");
          localMovieData.videoType = localJSONObject2.optInt("videoType");
          localMovieData.viewType = localJSONObject2.optInt("viewType");
          localMovieData.name = localJSONObject2.optString("name");
          localMovieData.img_v = localJSONObject2.optString("imgUrl");
          startDetailPageActivity(localMovieData);
          return true;
          if (!"open_special_page".equals(paramString2))
            break;
        }
        while (localJSONObject2 == null);
        startSpecialActivity(localJSONObject2);
        return true;
        if (!"open_star_detail".equals(paramString2))
          break;
      }
      while (localJSONObject2 == null);
      startStarDetailedActivity(localJSONObject2.optString("actor_name"), localJSONObject2.optString("actor_id"), localJSONObject2.optString("label_id"));
      return true;
    }
    label360: return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
  }

  protected void xulOnRenderIsReady()
  {
    updateTitle();
    this.mXulFilmListView = xulFindViewById("film_list_view");
    this.mXulFilmListWrapper = XulMassiveAreaWrapper.fromXulView(this.mXulFilmListView);
    this.mXulEmptyTips = xulFindViewById("page_details_empty_tips");
    showLikeListOrPlayList();
    super.xulOnRenderIsReady();
  }

  public void xulOnRenderShow()
  {
    if (xulIsReady())
      showLikeListOrPlayList();
  }

  private final class SccmsApiGetPlaybillSelectedListTaskListener
    implements SccmsApiGetPlaybillSelectedList.ISccmsApiGetPlaybillSelectedListTaskListener
  {
    private SccmsApiGetPlaybillSelectedListTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      MorePageActivity.this.dismissLoaddingDialog();
      if (MorePageActivity.this.isDownloadingMore)
        MorePageActivity.access$210(MorePageActivity.this);
      while (true)
      {
        MorePageActivity.access$102(MorePageActivity.this, false);
        return;
        MorePageActivity.this.showEmptyTips(true);
        MorePageActivity.access$402(MorePageActivity.this, false);
        MorePageActivity.this.reportLoad(MorePageActivity.this.isLoadSuccess, null);
      }
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, GetPlaybillSelectedListInfo paramGetPlaybillSelectedListInfo)
    {
      if (paramGetPlaybillSelectedListInfo == null)
      {
        if (!MorePageActivity.this.isDownloadingMore)
        {
          MorePageActivity.access$402(MorePageActivity.this, true);
          MorePageActivity.this.reportLoad(MorePageActivity.this.isLoadSuccess, null);
        }
        Logger.e(MorePageActivity.TAG, "SccmsApiGetPlaybillSelectedListTaskListener onSuccess(), but result = null !!!");
        return;
      }
      MorePageActivity.this.onGetVideoList(paramGetPlaybillSelectedListInfo);
      if (!MorePageActivity.this.isDownloadingMore)
      {
        MorePageActivity.access$402(MorePageActivity.this, true);
        MorePageActivity.this.reportLoad(MorePageActivity.this.isLoadSuccess, null);
      }
      MorePageActivity.access$102(MorePageActivity.this, false);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.MorePageActivity
 * JD-Core Version:    0.6.2
 */