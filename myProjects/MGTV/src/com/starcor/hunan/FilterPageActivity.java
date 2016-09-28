package com.starcor.hunan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.starcor.core.domain.FilmItem;
import com.starcor.core.domain.FilmListItem;
import com.starcor.core.domain.MovieData;
import com.starcor.core.utils.Logger;
import com.starcor.report.ReportArea;
import com.starcor.report.ReportInfo;
import com.starcor.sccms.api.SccmsApiGetMediaAssetsBindLabelTask.ISccmsApiGetMediaAssetsBindLabelTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoListByLabelTask.ISccmsApiGetVideoListByLabelTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoListTask.ISccmsApiGetVideoListTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.Wrapper.XulMassiveAreaWrapper;
import com.starcor.xul.Wrapper.XulSliderAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulPendingInputStream;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class FilterPageActivity extends DialogActivity
{
  private static final int DOWNLOAD_PAGE_SIZE = 40;
  public static final String SORT_TYPE_CLICK = "click";
  public static final String SORT_TYPE_TIME = "time";
  private static final String TAG = "FilterPageActivity";
  private String EPGParameter = "";
  private int count = 0;
  private XulPendingInputStream filterLabelPendingStream = new XulPendingInputStream();
  private Map<String, String> filterTypeAndIdMap = new LinkedHashMap();
  private boolean isLoadSuccess = false;
  private int mCurrentDownloadPage = 0;
  private ArrayList<Integer> mDownloadedPageIndexList = new ArrayList();
  private String mFilterResultId = "";
  private String mFilterStr;
  private boolean mIsFirstGetVideoList = false;
  private boolean mIsReportFuncLoad = false;
  private String mPackageId;
  private String mSortType = "time";
  private int mTotalPage;
  private XulView mXulFilmListView;
  private XulMassiveAreaWrapper mXulFilmListWrapper;
  private boolean needReportFocus = true;
  private long searchStartTime = -1L;

  private void addTaskToGetVideoList(int paramInt)
  {
    this.mCurrentDownloadPage = (paramInt / 40);
    Logger.d("FilterPageActivity", "addTaskToGetVideoList(" + paramInt + ") mCurrentDownloadPage = " + this.mCurrentDownloadPage + " mTotalPage = " + this.mTotalPage);
    if (TextUtils.isEmpty(this.mFilterStr))
    {
      this.searchStartTime = System.currentTimeMillis();
      SccmsApiGetVideoListTaskListener localSccmsApiGetVideoListTaskListener = new SccmsApiGetVideoListTaskListener(this.mFilterStr);
      if (!this.mDownloadedPageIndexList.contains(Integer.valueOf(this.mCurrentDownloadPage)))
        ServerApiManager.i().APIGetVideoList(this.mPackageId, "", this.mSortType, this.mCurrentDownloadPage, 40, localSccmsApiGetVideoListTaskListener);
      if ((1 + this.mCurrentDownloadPage < this.mTotalPage) && (!this.mDownloadedPageIndexList.contains(Integer.valueOf(1 + this.mCurrentDownloadPage))))
        ServerApiManager.i().APIGetVideoList(this.mPackageId, "", this.mSortType, 1 + this.mCurrentDownloadPage, 40, localSccmsApiGetVideoListTaskListener);
      if ((-1 + this.mCurrentDownloadPage > 0) && (!this.mDownloadedPageIndexList.contains(Integer.valueOf(-1 + this.mCurrentDownloadPage))))
        ServerApiManager.i().APIGetVideoList(this.mPackageId, "", this.mSortType, -1 + this.mCurrentDownloadPage, 40, localSccmsApiGetVideoListTaskListener);
    }
    SccmsApiGetVideoListByLabelTaskListener localSccmsApiGetVideoListByLabelTaskListener;
    do
    {
      return;
      this.searchStartTime = System.currentTimeMillis();
      localSccmsApiGetVideoListByLabelTaskListener = new SccmsApiGetVideoListByLabelTaskListener(this.mFilterStr);
      if (!this.mDownloadedPageIndexList.contains(Integer.valueOf(this.mCurrentDownloadPage)))
        ServerApiManager.i().APIGetVideoListByLabel(this.mPackageId, "", this.mSortType, this.mCurrentDownloadPage, 40, this.mFilterStr, localSccmsApiGetVideoListByLabelTaskListener);
      if ((1 + this.mCurrentDownloadPage < this.mTotalPage) && (!this.mDownloadedPageIndexList.contains(Integer.valueOf(1 + this.mCurrentDownloadPage))))
        ServerApiManager.i().APIGetVideoListByLabel(this.mPackageId, "", this.mSortType, 1 + this.mCurrentDownloadPage, 40, this.mFilterStr, localSccmsApiGetVideoListByLabelTaskListener);
    }
    while ((-1 + this.mCurrentDownloadPage <= 0) || (this.mDownloadedPageIndexList.contains(Integer.valueOf(-1 + this.mCurrentDownloadPage))));
    ServerApiManager.i().APIGetVideoListByLabel(this.mPackageId, "", this.mSortType, -1 + this.mCurrentDownloadPage, 40, this.mFilterStr, localSccmsApiGetVideoListByLabelTaskListener);
  }

  private void getFilterLabel()
  {
    ServerApiManager.i().APIGetMediaAssetsBindLabelType(this.mPackageId, new SccmsApiGetMediaAssetsBindLabelTask.ISccmsApiGetMediaAssetsBindLabelTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        FilterPageActivity.this.processError();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, byte[] paramAnonymousArrayOfByte)
      {
        FilterPageActivity.this.filterLabelPendingStream.setBaseStream(new ByteArrayInputStream(paramAnonymousArrayOfByte));
      }
    });
  }

  private void onGetVideoList(FilmItem paramFilmItem, String paramString)
  {
    if ((!TextUtils.isEmpty(this.mFilterStr)) && (!this.mFilterStr.equals(paramString)));
    do
    {
      do
      {
        return;
        this.count = paramFilmItem.film_num;
        this.mTotalPage = paramFilmItem.page_total;
      }
      while (paramFilmItem.filmList == null);
      if ((this.mXulFilmListView == null) || (this.mXulFilmListWrapper == null))
      {
        this.mXulFilmListView = xulFindViewById("film_list_view");
        this.mXulFilmListWrapper = XulMassiveAreaWrapper.fromXulView(this.mXulFilmListView);
      }
    }
    while (this.mXulFilmListWrapper == null);
    if ((this.mCurrentDownloadPage == 0) && (this.mXulFilmListWrapper.itemNum() == 0))
    {
      Iterator localIterator = paramFilmItem.filmList.iterator();
      while (localIterator.hasNext())
      {
        FilmListItem localFilmListItem2 = (FilmListItem)localIterator.next();
        XulDataNode localXulDataNode3 = XulDataNode.obtainDataNode("item");
        localXulDataNode3.setAttribute("name", localFilmListItem2.film_name);
        localXulDataNode3.setAttribute("ui_style", localFilmListItem2.uiStyle + "");
        localXulDataNode3.setAttribute("video_id", localFilmListItem2.video_id);
        localXulDataNode3.setAttribute("video_type", localFilmListItem2.video_type + "");
        localXulDataNode3.setAttribute("media_assets_id", localFilmListItem2.package_id);
        localXulDataNode3.setAttribute("category_id", localFilmListItem2.category_id);
        localXulDataNode3.setAttribute("img_v", localFilmListItem2.v_img_url);
        localXulDataNode3.setAttribute("img_h", localFilmListItem2.h_img_url);
        localXulDataNode3.setAttribute("corner_mark", localFilmListItem2.corner_mark);
        localXulDataNode3.setAttribute("corner_mark_img", localFilmListItem2.corner_mark_img_url);
        localXulDataNode3.setAttribute("view_type", localFilmListItem2.viewType + "");
        localXulDataNode3.setAttribute("abstract", localFilmListItem2.updateInfo);
        localXulDataNode3.setAttribute("isPlaceHolder", "false");
        this.mXulFilmListWrapper.addItem(localXulDataNode3);
      }
      for (int k = 0; k < this.count - paramFilmItem.filmList.size(); k++)
      {
        XulDataNode localXulDataNode2 = XulDataNode.obtainDataNode("item");
        localXulDataNode2.setAttribute("img_v", "");
        localXulDataNode2.setAttribute("isPlaceHolder", "true");
        this.mXulFilmListWrapper.addItem(localXulDataNode2);
      }
      this.mXulFilmListWrapper.syncContentView();
      XulArea localXulArea = (XulArea)xulFindViewById("film_list_view_slider");
      if (localXulArea != null)
        localXulArea.setDynamicFocus(null);
      if (paramFilmItem.film_num > 0)
        break label852;
    }
    label852: for (boolean bool = true; ; bool = false)
    {
      showEmptyTips(bool);
      return;
      ArrayList localArrayList = new ArrayList();
      for (int i = 0; i < paramFilmItem.filmList.size(); i++)
      {
        FilmListItem localFilmListItem1 = (FilmListItem)paramFilmItem.filmList.get(i);
        XulDataNode localXulDataNode1 = XulDataNode.obtainDataNode("item");
        localXulDataNode1.setAttribute("name", localFilmListItem1.film_name);
        localXulDataNode1.setAttribute("ui_style", localFilmListItem1.uiStyle + "");
        localXulDataNode1.setAttribute("video_id", localFilmListItem1.video_id);
        localXulDataNode1.setAttribute("video_type", localFilmListItem1.video_type + "");
        localXulDataNode1.setAttribute("media_assets_id", localFilmListItem1.package_id);
        localXulDataNode1.setAttribute("category_id", localFilmListItem1.category_id);
        localXulDataNode1.setAttribute("img_v", localFilmListItem1.v_img_url);
        localXulDataNode1.setAttribute("img_h", localFilmListItem1.h_img_url);
        localXulDataNode1.setAttribute("corner_mark", localFilmListItem1.corner_mark);
        localXulDataNode1.setAttribute("corner_mark_img", localFilmListItem1.corner_mark_img_url);
        localXulDataNode1.setAttribute("view_type", localFilmListItem1.viewType + "");
        localXulDataNode1.setAttribute("abstract", localFilmListItem1.updateInfo);
        localXulDataNode1.setAttribute("isPlaceHolder", "false");
        localArrayList.add(localXulDataNode1);
      }
      if (!this.mDownloadedPageIndexList.contains(Integer.valueOf(paramFilmItem.page_index)))
      {
        int j = 40 * paramFilmItem.page_index;
        this.mXulFilmListWrapper.updateItems(j, localArrayList);
        break;
      }
      this.mDownloadedPageIndexList.add(Integer.valueOf(paramFilmItem.page_index));
      break;
    }
  }

  private void processError()
  {
    Logger.e("FilterPageActivity", "processError!!!!!");
  }

  private void showEmptyTips(boolean paramBoolean)
  {
    boolean bool;
    String str2;
    label42: XulView localXulView1;
    if (this.mXulFilmListView != null)
    {
      XulView localXulView2 = this.mXulFilmListView;
      if (!paramBoolean)
      {
        bool = true;
        localXulView2.setEnabled(bool);
        XulView localXulView3 = this.mXulFilmListView;
        if (paramBoolean)
          break label108;
        str2 = "block";
        localXulView3.setStyle("display", str2);
        this.mXulFilmListView.resetRender();
      }
    }
    else
    {
      localXulView1 = xulFindViewById("page_details_empty_tips");
      if (localXulView1 != null)
      {
        localXulView1.setAttr("text", "对不起,  该选项暂无内容!");
        if (!paramBoolean)
          break label116;
      }
    }
    label108: label116: for (String str1 = "block"; ; str1 = "none")
    {
      localXulView1.setStyle("display", str1);
      localXulView1.resetRender();
      return;
      bool = false;
      break;
      str2 = "none";
      break label42;
    }
  }

  public void dealKeyEvent(KeyEvent paramKeyEvent)
  {
    Logger.i("FilterPageActivity", "dealKeyEvent " + paramKeyEvent.getKeyCode());
    if (paramKeyEvent.getKeyCode() == 20)
    {
      XulView localXulView = xulGetFocus();
      if (localXulView == null);
      do
      {
        return;
        if ((this.mXulFilmListView == null) || (!localXulView.hasClass("poster-item")))
          break;
      }
      while (this.count == 0);
      int i = this.mXulFilmListWrapper.getItemIdx(localXulView);
      if ((this.count % 5 > 0) && (i + 1 > -5 + (this.count - this.count % 5)) && (i + 1 <= this.count - this.count % 5))
      {
        xulRequestFocus(((XulArea)this.mXulFilmListView).getLastChild());
        return;
      }
    }
    super.dealKeyEvent(paramKeyEvent);
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 0)
    {
      super.dispatchKeyEvent(paramKeyEvent);
      XulArea localXulArea;
      if ((paramKeyEvent.getKeyCode() == 22) || (paramKeyEvent.getKeyCode() == 21))
      {
        localXulArea = xulGetFocus().findParentById("film_list_view");
        if ((localXulArea == null) || (!this.needReportFocus))
          break label87;
        this.needReportFocus = false;
        arrayOfString = new String[2];
        arrayOfString[0] = "null";
        arrayOfString[1] = this.mFilterResultId;
        reportFocus(2, arrayOfString);
      }
      label87: 
      while (localXulArea != null)
      {
        String[] arrayOfString;
        return true;
      }
      this.needReportFocus = true;
      return true;
    }
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Logger.i("FilterPageActivity", "FilterPageActivity onCreate");
    initXul("FilterPage", true);
    this.mPackageId = getIntent().getStringExtra("packageId");
    getFilterLabel();
    String str = ReportArea.getValue(FilterPageActivity.class.getSimpleName());
    ReportInfo.getInstance().setEntranceSrc(str);
  }

  protected void onDestroy()
  {
    this.filterTypeAndIdMap.clear();
    super.onDestroy();
  }

  protected void onRestart()
  {
    super.onRestart();
    boolean bool = this.isLoadSuccess;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.mPackageId;
    reportLoad(bool, arrayOfString);
    String str = ReportArea.getValue(FilterPageActivity.class.getSimpleName());
    ReportInfo.getInstance().setEntranceSrc(str);
  }

  protected void onStop()
  {
    super.onStop();
    reportExit(this.isLoadSuccess, new String[0]);
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    Logger.d("FilterPageActivity", "xulDoAction!! action:" + paramString1 + " type:" + paramString2 + " cmd:" + paramString3 + " data:" + paramObject);
    JSONObject localJSONObject2;
    try
    {
      JSONObject localJSONObject1 = new JSONObject(paramString3);
      localJSONObject2 = localJSONObject1;
      if (!"click".equals(paramString1))
        break label672;
      if ("apply_filter".equals(paramString2))
      {
        arrayOfString = localJSONObject2.optString("filter").split(",");
        if (arrayOfString.length == 1)
        {
          this.filterTypeAndIdMap.remove(arrayOfString[0]);
          str1 = "";
          Iterator localIterator = this.filterTypeAndIdMap.entrySet().iterator();
          while (true)
          {
            if (!localIterator.hasNext())
              break label298;
            localEntry = (Map.Entry)localIterator.next();
            if (TextUtils.isEmpty(str1))
              break;
            String str2 = str1 + ",";
            str1 = str2 + (String)localEntry.getValue();
          }
        }
      }
    }
    catch (JSONException localJSONException)
    {
      String str1;
      while (true)
      {
        String[] arrayOfString;
        Map.Entry localEntry;
        Logger.e("filter json covert error");
        localJSONObject2 = null;
        continue;
        this.filterTypeAndIdMap.remove(arrayOfString[0]);
        this.filterTypeAndIdMap.put(arrayOfString[0], arrayOfString[1]);
        continue;
        str1 = (String)localEntry.getValue();
      }
      label298: this.mFilterStr = str1;
      this.mFilterResultId = str1.replaceAll(",", "&");
      if (this.mXulFilmListView != null)
        ((XulArea)this.mXulFilmListView).removeAllChildren();
      if (this.mXulFilmListWrapper != null)
        this.mXulFilmListWrapper.clear();
      XulSliderAreaWrapper.fromXulView((XulArea)xulFindViewById("film_list_view_slider")).scrollTo(0, false);
      showLoaddingDialog();
      this.mTotalPage = 0;
      this.mDownloadedPageIndexList.clear();
      this.mIsFirstGetVideoList = false;
      this.mIsReportFuncLoad = true;
      addTaskToGetVideoList(0);
      return true;
    }
    if ("switch_newest_hottest".equals(paramString2))
    {
      if ("switch_newest".equals(paramString3))
        this.mSortType = "time";
      while (true)
      {
        if (this.mXulFilmListView != null)
          ((XulArea)this.mXulFilmListView).removeAllChildren();
        if (this.mXulFilmListWrapper != null)
          this.mXulFilmListWrapper.clear();
        XulSliderAreaWrapper.fromXulView((XulArea)xulFindViewById("film_list_view_slider")).scrollTo(0, false);
        showLoaddingDialog();
        this.mTotalPage = 0;
        this.mIsFirstGetVideoList = false;
        this.mIsReportFuncLoad = true;
        addTaskToGetVideoList(0);
        return true;
        if ("switch_hottest".equals(paramString3))
          this.mSortType = "click";
      }
    }
    if ("open_detail_page".equals(paramString2))
    {
      if (localJSONObject2 != null)
      {
        if (localJSONObject2.optBoolean("isPlaceHolder", true))
        {
          Logger.w("FilterPageActivity", "This is a place holder, please click after poster updated.");
          return false;
        }
        MovieData localMovieData = new MovieData();
        localMovieData.packageId = localJSONObject2.optString("media_assets_id");
        localMovieData.categoryId = localJSONObject2.optString("category_id");
        localMovieData.videoId = localJSONObject2.optString("videoId");
        localMovieData.videoType = localJSONObject2.optInt("videoType");
        localMovieData.viewType = localJSONObject2.optInt("viewType");
        localMovieData.name = localJSONObject2.optString("name");
        localMovieData.img_v = localJSONObject2.optString("imgUrl");
        startDetailPageActivity(localMovieData);
      }
      return true;
      label672: if ("scrollStopped".equals(paramString1))
      {
        if ("load_more_data".equals(paramString2))
        {
          XulView localXulView = xulGetFocus();
          int i = this.mXulFilmListWrapper.getItemIdx(localXulView);
          if (i > -1)
          {
            this.mIsReportFuncLoad = false;
            this.mIsFirstGetVideoList = false;
            addTaskToGetVideoList(i);
          }
        }
        return true;
      }
    }
    return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
  }

  protected InputStream xulGetAppData(XulWorker.DownloadItem paramDownloadItem, String paramString)
  {
    if ("internal/page_filter".equals(paramString))
      return this.filterLabelPendingStream;
    return super.xulGetAppData(paramDownloadItem, paramString);
  }

  protected void xulOnRenderIsReady()
  {
    this.mXulFilmListView = xulFindViewById("film_list_view");
    this.mXulFilmListWrapper = XulMassiveAreaWrapper.fromXulView(this.mXulFilmListView);
    this.mIsFirstGetVideoList = true;
    this.mIsReportFuncLoad = true;
    addTaskToGetVideoList(0);
  }

  private final class SccmsApiGetVideoListByLabelTaskListener
    implements SccmsApiGetVideoListByLabelTask.ISccmsApiGetVideoListByLabelTaskListener
  {
    private String filterStr = "";

    public SccmsApiGetVideoListByLabelTaskListener(String arg2)
    {
      Object localObject;
      this.filterStr = localObject;
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      FilterPageActivity localFilterPageActivity;
      if (FilterPageActivity.this.mIsReportFuncLoad)
      {
        String str1 = paramServerApiTaskInfo.getHttpRequestUrl();
        localFilterPageActivity = FilterPageActivity.this;
        if (str1.length() <= 1)
          break label92;
      }
      label92: for (String str2 = paramServerApiTaskInfo.getHttpRequestUrl().split("\\?")[1]; ; str2 = "")
      {
        FilterPageActivity.access$602(localFilterPageActivity, str2);
        long l = System.currentTimeMillis() - FilterPageActivity.this.searchStartTime;
        FilterPageActivity.this.reportFuncLoad(7, null, FilterPageActivity.this.mFilterResultId, l, false, 0, FilterPageActivity.this.EPGParameter);
        return;
      }
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, FilmItem paramFilmItem)
    {
      int i;
      FilterPageActivity localFilterPageActivity;
      if (FilterPageActivity.this.mIsReportFuncLoad)
      {
        i = 0;
        if (paramFilmItem != null)
        {
          ArrayList localArrayList = paramFilmItem.filmList;
          i = 0;
          if (localArrayList != null)
            i = paramFilmItem.filmList.size();
        }
        String str1 = paramServerApiTaskInfo.getHttpRequestUrl();
        localFilterPageActivity = FilterPageActivity.this;
        if (str1.length() <= 1)
          break label140;
      }
      label140: for (String str2 = paramServerApiTaskInfo.getHttpRequestUrl().split("\\?")[1]; ; str2 = "")
      {
        FilterPageActivity.access$602(localFilterPageActivity, str2);
        long l = System.currentTimeMillis() - FilterPageActivity.this.searchStartTime;
        FilterPageActivity.this.reportFuncLoad(7, null, FilterPageActivity.this.mFilterResultId, l, true, i, FilterPageActivity.this.EPGParameter);
        FilterPageActivity.this.onGetVideoList(paramFilmItem, this.filterStr);
        FilterPageActivity.this.dismissLoaddingDialog();
        return;
      }
    }
  }

  private final class SccmsApiGetVideoListTaskListener
    implements SccmsApiGetVideoListTask.ISccmsApiGetVideoListTaskListener
  {
    private String filterStr = "";

    public SccmsApiGetVideoListTaskListener(String arg2)
    {
      Object localObject;
      this.filterStr = localObject;
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      if (FilterPageActivity.this.mIsFirstGetVideoList)
      {
        FilterPageActivity.access$302(FilterPageActivity.this, false);
        FilterPageActivity localFilterPageActivity2 = FilterPageActivity.this;
        boolean bool = FilterPageActivity.this.isLoadSuccess;
        String[] arrayOfString = new String[1];
        arrayOfString[0] = FilterPageActivity.this.mPackageId;
        localFilterPageActivity2.reportLoad(bool, arrayOfString);
      }
      FilterPageActivity localFilterPageActivity1;
      if (FilterPageActivity.this.mIsReportFuncLoad)
      {
        String str1 = paramServerApiTaskInfo.getHttpRequestUrl();
        localFilterPageActivity1 = FilterPageActivity.this;
        if (str1.length() <= 1)
          break label152;
      }
      label152: for (String str2 = paramServerApiTaskInfo.getHttpRequestUrl().split("\\?")[1]; ; str2 = "")
      {
        FilterPageActivity.access$602(localFilterPageActivity1, str2);
        long l = System.currentTimeMillis() - FilterPageActivity.this.searchStartTime;
        FilterPageActivity.this.reportFuncLoad(7, null, FilterPageActivity.this.mFilterResultId, l, false, 0, FilterPageActivity.this.EPGParameter);
        return;
      }
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, FilmItem paramFilmItem)
    {
      FilterPageActivity.this.onGetVideoList(paramFilmItem, this.filterStr);
      FilterPageActivity.this.dismissLoaddingDialog();
      if (FilterPageActivity.this.mIsFirstGetVideoList)
      {
        FilterPageActivity.access$302(FilterPageActivity.this, true);
        FilterPageActivity localFilterPageActivity2 = FilterPageActivity.this;
        boolean bool = FilterPageActivity.this.isLoadSuccess;
        String[] arrayOfString = new String[1];
        arrayOfString[0] = FilterPageActivity.this.mPackageId;
        localFilterPageActivity2.reportLoad(bool, arrayOfString);
      }
      int i;
      FilterPageActivity localFilterPageActivity1;
      if (FilterPageActivity.this.mIsReportFuncLoad)
      {
        i = 0;
        if (paramFilmItem != null)
        {
          ArrayList localArrayList = paramFilmItem.filmList;
          i = 0;
          if (localArrayList != null)
            i = paramFilmItem.filmList.size();
        }
        String str1 = paramServerApiTaskInfo.getHttpRequestUrl();
        localFilterPageActivity1 = FilterPageActivity.this;
        if (str1.length() <= 1)
          break label200;
      }
      label200: for (String str2 = paramServerApiTaskInfo.getHttpRequestUrl().split("\\?")[1]; ; str2 = "")
      {
        FilterPageActivity.access$602(localFilterPageActivity1, str2);
        long l = System.currentTimeMillis() - FilterPageActivity.this.searchStartTime;
        FilterPageActivity.this.reportFuncLoad(7, null, FilterPageActivity.this.mFilterResultId, l, true, i, FilterPageActivity.this.EPGParameter);
        return;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.FilterPageActivity
 * JD-Core Version:    0.6.2
 */