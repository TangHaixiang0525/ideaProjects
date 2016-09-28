package com.starcor.hunan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import com.starcor.core.domain.HotWord;
import com.starcor.core.domain.HotWordList;
import com.starcor.core.domain.MovieData;
import com.starcor.core.domain.SearchActorStarList;
import com.starcor.core.domain.SearchActorStarList.ActorStar;
import com.starcor.core.domain.SearchListItem;
import com.starcor.core.domain.SearchStruct;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.AppKiller;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.PinYinUtil;
import com.starcor.hunan.uilogic.ColorizedFilmNameGenerator;
import com.starcor.hunan.uilogic.InitApiData;
import com.starcor.hunan.uilogic.InitApiData.onApiOKListener;
import com.starcor.report.ReportInfo;
import com.starcor.sccms.api.SccmsApiGetHotWordsTask.ISccmsApiGetHotWordsTaskListener;
import com.starcor.sccms.api.SccmsApiSearchActorStarListTask.ISccmsApiSearchActorStarListTaskListener;
import com.starcor.sccms.api.SccmsApiSearchVideoByPinyinTask.ISccmsApiSearchVideoByPinyinTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.service.DelayInvokeFilter;
import com.starcor.xul.Prop.XulAttr;
import com.starcor.xul.Prop.XulData;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.Wrapper.XulGroupAreaWrapper;
import com.starcor.xul.Wrapper.XulMassiveAreaWrapper;
import com.starcor.xul.Wrapper.XulSliderAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulView;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;

public class TimeSearchActivityV4 extends BaseActivity
{
  private static final int DOWNLOAD_PAGE_SIZE = 40;
  private static final String KEY_ACTION = "actions";
  private static final String KEY_CATEGORY_ID = "category_id";
  private static final String KEY_MEDIA_ASSET_NAME = "media_asset_name";
  private static final String KEY_SEARCH_RANGE = "search_range";
  private static final String POSTER_TYPE_ACTOR = "TYPE_ACTOR";
  private static final String POSTER_TYPE_FILM = "TYPE_FILM";
  private static final int SEARCH_KEY_MAX_SIZE = 18;
  private static final String TAG = "TimeSearchActivityV4";
  private String EPGParameter = "";
  private boolean isLoadSuccess = false;
  private boolean isReportFuncLoad = false;
  private ColorizedFilmNameGenerator mColorizedFilmNameGenerator = new ColorizedFilmNameGenerator();
  private int mCurrentDownloadPage = 0;
  private int mCurrentResultCount = 0;
  private ReturnType mCurrentType = null;
  private DelayInvokeFilter mDelayFilter;
  private ArrayList<Integer> mDownloadedPageIndexList = new ArrayList();
  private boolean mIsDownloadingMore = false;
  private boolean mIsFromOut;
  private String mLastBtnCommand = "";
  private String mLastRangeBtn;
  private String mLastSearchStr = "";
  private int mOnePageSize = 10;
  private String mPacketId;
  private int mPageColumn;
  private int mPageLines;
  private String mSearchRangeName = "";
  private String mSearchRangePackgeId = "";
  private int mSearchResultCount = 0;
  private String mSearchStr = "";
  private int mTotalPage = 0;
  private XulView mXulContentLayer;
  private XulView mXulEmptyView;
  private XulView mXulFilmListView;
  private XulMassiveAreaWrapper mXulFilmListWrapper;
  private XulView mXulHotwordContentView;
  private XulView mXulHotwordEmptyView;
  private XulMassiveAreaWrapper mXulHotwordListWrapper;
  private XulView mXulHotwordView;
  private XulSliderAreaWrapper mXulHotwordsViewWrapper;
  private XulView mXulLoadingImageView;
  private XulView mXulLoadingView;
  private XulView mXulSearchBox;
  private XulView mXulSearchRadio;
  private XulView mXulSearchResultView;
  private XulSliderAreaWrapper mXulSearchResultWrapper;
  private boolean needReportQuery = true;
  boolean needResetFocus = false;
  private long searchStartTime = -1L;

  private void autoDoSearch(Intent paramIntent)
  {
    String str1 = paramIntent.getStringExtra("search_key");
    paramIntent.getStringExtra("media_asset_name");
    String str2 = paramIntent.getStringExtra("search_range");
    String str3 = paramIntent.getStringExtra("category_id");
    String str4 = paramIntent.getStringExtra("actions");
    Logger.i("TimeSearchActivityV4", "key=" + str1 + "/" + "search_range=" + str2 + "/" + "category_id=" + str3 + "/" + "action=" + str4);
    if (TextUtils.isEmpty(str1));
    label126: String str5;
    do
    {
      return;
      XulArrayList localXulArrayList = xulFindViewsByClass("category_btn");
      int i = 0;
      if (i < localXulArrayList.size())
      {
        XulView localXulView = (XulView)localXulArrayList.get(i);
        if (localXulView == null);
        while (true)
        {
          i++;
          break label126;
          String str7 = xulArgListToBundle((XulDataNode)localXulView.getData("userdata").getValue()).getString("search_range");
          if ((str2 != null) && (str2.equals(str7)))
          {
            XulGroupAreaWrapper localXulGroupAreaWrapper = XulGroupAreaWrapper.fromXulView(this.mXulSearchRadio);
            ArrayList localArrayList = localXulGroupAreaWrapper.getAllGroupItems();
            if ((localArrayList == null) || (localArrayList.size() <= 0))
              break;
            localXulGroupAreaWrapper.setChecked(localXulView);
            localXulView.resetRender();
            xulRequestFocus((XulView)localXulArrayList.get(i));
          }
        }
      }
      str5 = str1.trim();
    }
    while (str5.length() == 0);
    String str6 = PinYinUtil.getFirstSpell(str5);
    if (str6.length() > 18);
    for (this.mSearchStr = ((String)str6.subSequence(0, 18)); ; this.mSearchStr = str6)
    {
      this.mLastSearchStr = this.mSearchStr;
      this.mSearchRangePackgeId = str2;
      Runnable local1 = new Runnable()
      {
        public void run()
        {
          TimeSearchActivityV4 localTimeSearchActivityV4 = TimeSearchActivityV4.this;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = TimeSearchActivityV4.this.mLastSearchStr.toUpperCase();
          localTimeSearchActivityV4.xulFireEvent("appEvents:airControlTextInput", arrayOfObject);
        }
      };
      xulPostDelay(local1, 200L);
      return;
    }
  }

  private void clearSearchKey()
  {
    displayHotwordList(true);
    if (this.mDelayFilter != null)
      this.mDelayFilter.destory();
    displayEmptyNotice(false);
    xulFindViewById("search_range_firstbtn");
    xulFireEvent("click");
    this.mXulFilmListWrapper.clear();
    this.mDownloadedPageIndexList.clear();
    this.mXulSearchResultWrapper.scrollTo(0, false);
  }

  private void displayEmptyHotWrod(boolean paramBoolean)
  {
    XulView localXulView;
    if (this.mXulEmptyView != null)
    {
      localXulView = this.mXulEmptyView;
      if (!paramBoolean)
        break label41;
    }
    label41: for (String str = "none"; ; str = "block")
    {
      localXulView.setStyle("display", str);
      displayHotwordList(paramBoolean);
      this.mXulEmptyView.resetRender();
      return;
    }
  }

  private void displayEmptyNotice(boolean paramBoolean)
  {
    XulView localXulView;
    if ((this.mXulEmptyView != null) && (this.mXulFilmListView != null))
    {
      this.mXulEmptyView.setAttr("text", "对不起, 没有与搜索项符合的结果!");
      if (!paramBoolean)
        break label120;
      if (this.mXulFilmListWrapper.itemNum() <= 0)
      {
        localXulView = xulFindViewById("search_range_firstbtn");
        if (!this.mXulFilmListView.hasFocus())
          break label105;
        xulRequestFocus(localXulView);
        this.mXulEmptyView.setStyle("display", "block");
      }
      this.mXulContentLayer.setStyle("display", "none");
    }
    while (true)
    {
      this.mXulEmptyView.resetRender();
      this.mXulContentLayer.resetRender();
      return;
      label105: ((XulArea)this.mXulSearchRadio).setDynamicFocus(localXulView);
      break;
      label120: this.mXulEmptyView.setStyle("display", "none");
      this.mXulContentLayer.setStyle("display", "block");
    }
  }

  private void displayHotWordEmpty(boolean paramBoolean)
  {
    if ((this.mXulHotwordContentView != null) && (this.mXulHotwordEmptyView != null))
    {
      this.mXulHotwordEmptyView.setAttr("text", "亲,请输入关键字试试吧!");
      if (!paramBoolean)
        break label72;
      this.mXulHotwordContentView.setStyle("display", "none");
      this.mXulHotwordEmptyView.setStyle("display", "block");
    }
    while (true)
    {
      this.mXulHotwordContentView.resetRender();
      this.mXulHotwordEmptyView.resetRender();
      return;
      label72: this.mXulHotwordContentView.setStyle("display", "block");
      this.mXulHotwordEmptyView.setStyle("display", "none");
    }
  }

  private void displayHotwordList(boolean paramBoolean)
  {
    if ((this.mXulHotwordView != null) && (this.mXulSearchResultView != null))
    {
      if (!paramBoolean)
        break label105;
      this.mXulHotwordView.setStyle("display", "block");
      this.mXulSearchBox.setAttr("text", "");
      this.mXulSearchResultView.setStyle("display", "none");
      this.mCurrentType = ReturnType.DEFAULT;
      this.mXulEmptyView.setStyle("display", "none");
    }
    while (true)
    {
      this.mXulHotwordView.resetRender();
      this.mXulSearchResultView.resetRender();
      this.mXulEmptyView.resetRender();
      this.mXulSearchBox.resetRender();
      return;
      label105: if (this.mXulHotwordView.hasFocus())
        xulRequestFocus(xulFindViewById("search_range_firstbtn"));
      this.mXulHotwordView.setStyle("display", "none");
      this.mXulSearchResultView.setStyle("display", "block");
    }
  }

  private void displayLoadingView(boolean paramBoolean)
  {
    XulView localXulView;
    if ((this.mXulLoadingView != null) && (this.mXulLoadingImageView != null))
    {
      Logger.d("TimeSearchActivityV4", "displayLoadingView: " + paramBoolean);
      if (!paramBoolean)
        break label102;
      if ((!this.mXulLoadingView.isVisible()) && (this.mXulLoadingImageView.addClass("rotate_ani")))
        this.mXulLoadingImageView.resetRender();
      localXulView = this.mXulLoadingView;
      if (!paramBoolean)
        break label135;
    }
    label135: for (String str = "block"; ; str = "none")
    {
      localXulView.setStyle("display", str);
      this.mXulLoadingView.resetRender();
      return;
      label102: if ((!this.mXulLoadingView.isVisible()) || (!this.mXulLoadingImageView.removeClass("rotate_ani")))
        break;
      this.mXulLoadingImageView.resetRender();
      break;
    }
  }

  private void doSearch(String paramString)
  {
    doSearch(paramString, null, this.mSearchRangePackgeId);
  }

  private void doSearch(String paramString1, String paramString2, String paramString3)
  {
    if (this.mDelayFilter != null)
      if (this.mDelayFilter.filter(this, new Object[] { paramString1, paramString2, paramString3 }))
        return;
    immediateSearch(paramString1, paramString2, paramString3);
  }

  private void doSearchstar(String paramString1, String paramString2)
  {
    this.searchStartTime = System.currentTimeMillis();
    displayLoadingView(true);
    displayHotwordList(false);
    if (this.mXulFilmListWrapper != null)
    {
      this.mXulFilmListWrapper.clear();
      this.mDownloadedPageIndexList.clear();
      this.mXulSearchResultWrapper.scrollTo(0, false);
    }
    this.mLastSearchStr = paramString1;
    this.mCurrentResultCount = 0;
    this.mCurrentDownloadPage = 0;
    SccmsApiGetVideoListByLabelTaskListener localSccmsApiGetVideoListByLabelTaskListener = new SccmsApiGetVideoListByLabelTaskListener(paramString1, paramString2);
    ServerApiManager.i().APISearchActorStarListTask(0, 40, null, paramString1, localSccmsApiGetVideoListByLabelTaskListener);
  }

  private void getData()
  {
    if (!TextUtils.isEmpty(getIntent().getStringExtra("cmd_is_from_out")))
      this.mIsFromOut = true;
    initviews();
    autoDoSearch(getIntent());
  }

  private void immediateSearch(String paramString1, String paramString2, String paramString3)
  {
    Logger.d("TimeSearchActivityV4", "immediateSearch: " + paramString1);
    this.searchStartTime = System.currentTimeMillis();
    displayLoadingView(true);
    this.mXulFilmListWrapper.clear();
    this.mDownloadedPageIndexList.clear();
    displayHotwordList(false);
    this.mXulSearchResultWrapper.scrollTo(0, false);
    this.mLastSearchStr = paramString1;
    String str = paramString1.trim();
    this.mCurrentResultCount = 0;
    this.mCurrentDownloadPage = 0;
    if (this.mCurrentType == ReturnType.HOTWROD)
    {
      ServerApiManager.i().APISearchVideoByChinese(paramString2, paramString3, str, 0, 40, new MISccmsApiSearchVideoByPinyinTaskListener(str, paramString3));
      return;
    }
    ServerApiManager.i().APISearchVideoByPinyin(paramString2, paramString3, str, 0, 40, new MISccmsApiSearchVideoByPinyinTaskListener(str, paramString3));
  }

  private void initviews()
  {
    this.mPageLines = 2;
    this.mPageColumn = 5;
    this.mXulSearchBox = xulFindViewById("search_text_box");
    this.mXulFilmListView = xulFindViewById("film_list_view");
    this.mXulHotwordView = xulFindViewById("hotwords_view");
    this.mXulSearchResultView = xulFindViewById("search_result_view");
    this.mXulEmptyView = xulFindViewById("search_empty_tips");
    this.mXulHotwordEmptyView = xulFindViewById("hotword_empty_tips");
    this.mXulHotwordContentView = xulFindViewById("hotwords_content");
    this.mXulContentLayer = xulFindViewById("content_layer");
    this.mXulSearchRadio = xulFindViewById("search_category_buttons");
    this.mXulLoadingView = xulFindViewById("loading_panel");
    this.mXulLoadingImageView = xulFindViewById("loading_img_view");
    this.mXulSearchResultWrapper = XulSliderAreaWrapper.fromXulView(this.mXulSearchResultView);
    this.mXulFilmListWrapper = XulMassiveAreaWrapper.fromXulView(this.mXulFilmListView);
    this.mOnePageSize = (this.mPageColumn * this.mPageLines);
    this.mXulHotwordsViewWrapper = XulSliderAreaWrapper.fromXulView(this.mXulHotwordContentView);
    this.mXulHotwordListWrapper = XulMassiveAreaWrapper.fromXulView(xulFindViewById("hotword_list_view"));
    this.mOnePageSize = (this.mPageColumn * this.mPageLines);
    XulView localXulView = xulFindViewById("search_tip");
    if (localXulView != null)
    {
      localXulView.setAttr("text", "支持影片、节目、影人首字母输入");
      localXulView.resetRender();
    }
    ServerApiManager.i().APIGetHotWords(0, 10, new MISccmsApiGetHotWordsTaskListener());
  }

  private void onGetsearchStar(SearchActorStarList paramSearchActorStarList)
  {
    if ((paramSearchActorStarList == null) || (paramSearchActorStarList.lists.size() <= 0))
    {
      if (this.isReportFuncLoad)
      {
        long l1 = System.currentTimeMillis() - this.searchStartTime;
        reportFuncLoad(0, null, this.mSearchStr, l1, true, 0, this.EPGParameter);
        this.isReportFuncLoad = false;
      }
      displayEmptyNotice(true);
      if (this.needResetFocus)
        xulRequestFocus(xulFindViewById("search_range_firstbtn"));
    }
    while (true)
    {
      return;
      this.mSearchResultCount = paramSearchActorStarList.total_rows;
      this.mTotalPage = paramSearchActorStarList.total_page;
      this.mCurrentResultCount = (paramSearchActorStarList.lists.size() + this.mCurrentResultCount);
      displayEmptyNotice(false);
      if ((this.mCurrentDownloadPage == 0) && (this.mXulFilmListWrapper.itemNum() == 0))
      {
        Iterator localIterator = paramSearchActorStarList.lists.iterator();
        while (localIterator.hasNext())
        {
          SearchActorStarList.ActorStar localActorStar2 = (SearchActorStarList.ActorStar)localIterator.next();
          XulDataNode localXulDataNode3 = XulDataNode.obtainDataNode("item");
          String str2 = this.mColorizedFilmNameGenerator.getSpannedLabelText(localActorStar2.name);
          localXulDataNode3.setAttribute("poster_type", "TYPE_ACTOR");
          localXulDataNode3.setAttribute("name", localActorStar2.name);
          localXulDataNode3.setAttribute("colorized_name", str2);
          localXulDataNode3.setAttribute("actor_id", localActorStar2.id);
          localXulDataNode3.setAttribute("label_id", localActorStar2.label_id);
          localXulDataNode3.setAttribute("label_id_v2", localActorStar2.label_id_v2);
          localXulDataNode3.setAttribute("img_v", localActorStar2.img_v);
          localXulDataNode3.setAttribute("img_h", localActorStar2.img_h);
          localXulDataNode3.setAttribute("isPlaceHolder", "false");
          this.mXulFilmListWrapper.addItem(localXulDataNode3);
        }
        for (int m = 0; ; m++)
        {
          int n = this.mSearchResultCount - paramSearchActorStarList.lists.size();
          if (m >= n)
            break;
          XulDataNode localXulDataNode2 = XulDataNode.obtainDataNode("item");
          localXulDataNode2.setAttribute("img_v", "");
          localXulDataNode2.setAttribute("isPlaceHolder", "true");
          this.mXulFilmListWrapper.addItem(localXulDataNode2);
        }
        this.mXulFilmListWrapper.syncContentView();
        XulView localXulView = ((XulArea)this.mXulFilmListView).getChild(0);
        ((XulArea)this.mXulContentLayer).setDynamicFocus(localXulView);
        ((XulArea)this.mXulSearchResultView).setDynamicFocus(localXulView);
        if (this.needResetFocus)
          xulRequestFocus(localXulView);
      }
      while (this.isReportFuncLoad)
      {
        long l2 = System.currentTimeMillis() - this.searchStartTime;
        reportFuncLoad(0, null, this.mSearchStr, l2, true, paramSearchActorStarList.lists.size(), this.EPGParameter);
        this.isReportFuncLoad = false;
        return;
        ArrayList localArrayList = new ArrayList();
        for (int i = 0; ; i++)
        {
          int j = paramSearchActorStarList.lists.size();
          if (i >= j)
            break;
          SearchActorStarList.ActorStar localActorStar1 = (SearchActorStarList.ActorStar)paramSearchActorStarList.lists.get(i);
          XulDataNode localXulDataNode1 = XulDataNode.obtainDataNode("item");
          String str1 = this.mColorizedFilmNameGenerator.getSpannedLabelText(localActorStar1.name);
          localXulDataNode1.setAttribute("poster_type", "TYPE_ACTOR");
          localXulDataNode1.setAttribute("name", localActorStar1.name);
          localXulDataNode1.setAttribute("colorized_name", str1);
          localXulDataNode1.setAttribute("actor_id", localActorStar1.id);
          localXulDataNode1.setAttribute("label_id", localActorStar1.label_id);
          localXulDataNode1.setAttribute("label_id_v2", localActorStar1.label_id_v2);
          localXulDataNode1.setAttribute("img_v", localActorStar1.img_v);
          localXulDataNode1.setAttribute("img_h", localActorStar1.img_h);
          localXulDataNode1.setAttribute("isPlaceHolder", "false");
          localArrayList.add(localXulDataNode1);
        }
        if (!this.mDownloadedPageIndexList.contains(Integer.valueOf(paramSearchActorStarList.cur_page)))
        {
          int k = 40 * paramSearchActorStarList.cur_page;
          this.mXulFilmListWrapper.updateItems(k, localArrayList);
        }
        else
        {
          this.mDownloadedPageIndexList.add(Integer.valueOf(paramSearchActorStarList.cur_page));
        }
      }
    }
  }

  private void resetSearchRangeCheckedBtn()
  {
    this.mPacketId = "";
    XulGroupAreaWrapper localXulGroupAreaWrapper = XulGroupAreaWrapper.fromXulView(this.mXulSearchRadio);
    ArrayList localArrayList = localXulGroupAreaWrapper.getAllGroupItems();
    if ((localArrayList == null) || (localArrayList.size() <= 0));
    XulView localXulView;
    do
    {
      return;
      localXulView = (XulView)localArrayList.get(0);
    }
    while (localXulView == null);
    localXulGroupAreaWrapper.setChecked(localXulView);
    localXulView.resetRender();
  }

  private void showErrorDialogAndReport(String paramString, ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
  {
    showErrorDialogWithReport(11, "1002012", paramString, paramServerApiTaskInfo, paramServerApiCommonError);
  }

  private boolean switchRangeBtnByCommand(String paramString, Bundle paramBundle)
  {
    if (this.mSearchStr.length() == 0);
    do
    {
      return true;
      if ("m_open_asset_search_range_page".equals(paramString))
      {
        this.isReportFuncLoad = true;
        this.mLastBtnCommand = paramString;
        this.mSearchRangePackgeId = paramBundle.getString("search_range");
        immediateSearch(this.mSearchStr, null, this.mSearchRangePackgeId);
      }
    }
    while (!"m_open_popstar_search_page".equals(paramString));
    this.isReportFuncLoad = true;
    this.mLastBtnCommand = paramString;
    this.mSearchRangePackgeId = paramBundle.getString("search_range");
    doSearchstar(this.mSearchStr, this.mSearchRangePackgeId);
    return true;
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    int i = 1;
    switch (paramKeyEvent.getAction())
    {
    default:
      i = super.dispatchKeyEvent(paramKeyEvent);
    case 1:
    case 0:
    }
    XulArea localXulArea1;
    XulArea localXulArea2;
    do
    {
      do
      {
        return i;
        if ((paramKeyEvent.getKeyCode() == 4) && (this.mXulLoadingView.isVisible()))
        {
          Logger.d("TimeSearchActivityV4", "mXulLoadingView.isVisible() hide this when back key pressed.");
          displayLoadingView(false);
          return i;
        }
        if (this.mDelayFilter != null)
          this.mDelayFilter.delayAgain();
        return super.dispatchKeyEvent(paramKeyEvent);
        super.dispatchKeyEvent(paramKeyEvent);
      }
      while ((paramKeyEvent.getKeyCode() != 22) && (paramKeyEvent.getKeyCode() != 21));
      XulView localXulView = xulGetFocus();
      localXulArea1 = localXulView.findParentById("film_list_view");
      localXulArea2 = localXulView.findParentById("search_category_slider");
      if (localXulArea2 != null)
        this.needReportQuery = false;
      if ((localXulArea1 != null) && (this.needReportQuery))
      {
        String[] arrayOfString = new String[2];
        arrayOfString[0] = "null";
        arrayOfString[i] = this.mSearchStr;
        reportFocus(i, arrayOfString);
        this.needReportQuery = false;
        return i;
      }
    }
    while ((localXulArea1 != null) || (localXulArea2 != null));
    this.needReportQuery = i;
    return i;
  }

  public boolean onAirControlTextInput(String paramString)
  {
    if (super.onAirControlTextInput(paramString))
      return true;
    xulFireEvent("appEvents:airControlTextInput", new Object[] { paramString });
    return true;
  }

  public void onBackPressed()
  {
    this.mXulFilmListWrapper.clear();
    this.mDownloadedPageIndexList.clear();
    if (this.mCurrentType == ReturnType.KEYBOARD)
    {
      this.mXulSearchBox.setAttr("text", "");
      this.mXulSearchBox.resetRender();
      this.mXulHotwordsViewWrapper.scrollTo(0, false);
      if (this.mDelayFilter != null)
        this.mDelayFilter.destory();
      displayHotwordList(true);
      this.mCurrentType = ReturnType.DEFAULT;
      resetSearchRangeCheckedBtn();
      xulFireEvent("appEvents:backPressed");
      return;
    }
    if (this.mCurrentType == ReturnType.HOTWROD)
    {
      displayHotwordList(true);
      this.mCurrentType = ReturnType.DEFAULT;
      if (this.mDelayFilter != null)
        this.mDelayFilter.destory();
      resetSearchRangeCheckedBtn();
      xulFireEvent("appEvents:backPressed");
      return;
    }
    if (this.mCurrentType == ReturnType.DEFAULT)
      super.onBackPressed();
    if (this.mIsFromOut)
    {
      AppKiller.getInstance().killApp();
      return;
    }
    super.onBackPressed();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    initXul("SearchPage", true);
    try
    {
      this.mDelayFilter = new DelayInvokeFilter(getClass().getDeclaredMethod("doSearch", new Class[] { String.class, String.class, String.class }));
      this.mDelayFilter.setDelayTime(400L);
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localNoSuchMethodException.printStackTrace();
    }
  }

  protected void onDestroy()
  {
    if (this.mDelayFilter != null)
      this.mDelayFilter.destory();
    super.onDestroy();
  }

  public void onGetHotWordList(HotWordList paramHotWordList)
  {
    Logger.i("TimeSearchActivityV4", "onGetHotWordList.");
    if (paramHotWordList == null)
      return;
    if (paramHotWordList.hotWordList.size() == 0)
    {
      displayHotWordEmpty(true);
      return;
    }
    displayEmptyHotWrod(true);
    this.mXulHotwordListWrapper.clear();
    this.mDownloadedPageIndexList.clear();
    this.mXulHotwordsViewWrapper.scrollTo(0, false);
    Iterator localIterator = paramHotWordList.hotWordList.iterator();
    while (localIterator.hasNext())
    {
      HotWord localHotWord = (HotWord)localIterator.next();
      XulDataNode localXulDataNode = XulDataNode.obtainDataNode("item");
      localXulDataNode.setAttribute("poster_type", "TYPE_FILM");
      localXulDataNode.setAttribute("name", localHotWord.name);
      localXulDataNode.setAttribute("video_id", localHotWord.video_id);
      localXulDataNode.setAttribute("img_v", localHotWord.image0);
      localXulDataNode.setAttribute("img_h", localHotWord.image1);
      localXulDataNode.setAttribute("pinyin", localHotWord.pinyin);
      localXulDataNode.setAttribute("corner_mark_img", localHotWord.cornermark);
      localXulDataNode.setAttribute("isPlaceHolder", "false");
      this.mXulHotwordListWrapper.addItem(localXulDataNode);
    }
    this.mXulHotwordListWrapper.syncContentView();
  }

  protected void onNewIntent(Intent paramIntent)
  {
    autoDoSearch(paramIntent);
    super.onNewIntent(paramIntent);
  }

  protected void onRestart()
  {
    super.onRestart();
    reportLoad(this.isLoadSuccess, null);
  }

  protected void onResume()
  {
    super.onResume();
  }

  protected void onStart()
  {
    super.onStart();
  }

  protected void onStop()
  {
    super.onStop();
    reportExit(this.isLoadSuccess, null);
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    Logger.i("TimeSearchActivityV4", "xulDoAction!! action:" + paramString1 + " type:" + paramString2 + " cmd:" + paramString3 + " data:" + paramObject);
    if (!xulIsReady())
    {
      Logger.i("TimeSearchActivityV4", "!xulIsReady()");
      return false;
    }
    XulArrayList localXulArrayList = xulFindViewsByClass("search_range_btn_selected");
    if (localXulArrayList == null)
      return false;
    XulData localXulData = ((XulView)localXulArrayList.get(0)).getData("userdata");
    Bundle localBundle1 = xulArgListToBundle((XulDataNode)localXulData.getValue());
    this.mSearchRangePackgeId = localBundle1.getString("search_range");
    this.mSearchRangeName = localBundle1.getString("name");
    Logger.i("TimeSearchActivityV4", "xulDoAction!! focus Item = " + xulGetFocus().toString());
    if (this.mXulSearchResultView.hasFocus())
      this.needResetFocus = true;
    try
    {
      localJSONObject1 = new JSONObject(paramString3);
    }
    catch (Exception localException2)
    {
      try
      {
        localJSONObject1.optString("cmd");
        for (localJSONObject2 = localJSONObject1; ; localJSONObject2 = null)
        {
          Bundle localBundle2;
          if ((paramObject instanceof XulDataNode))
            localBundle2 = xulArgListToBundle((XulDataNode)paramObject);
          while (true)
            if ("click".equals(paramString1))
            {
              if (localJSONObject2 == null)
                break label971;
              if ("open_detail_page".equals(paramString2))
              {
                String str = localJSONObject2.optString("poster_type");
                Logger.i("mCurrentType=" + this.mCurrentType);
                if (localJSONObject2.optBoolean("isPlaceHolder", true))
                {
                  Logger.w("TimeSearchActivityV4", "This is a place holder, please click after poster updated.");
                  return false;
                  this.needResetFocus = false;
                  break;
                  localBundle2 = new Bundle();
                  continue;
                }
                if (str.equals("TYPE_FILM"))
                {
                  MovieData localMovieData = new MovieData();
                  localMovieData.packageId = localJSONObject2.optString("media_asset_id");
                  localMovieData.categoryId = localJSONObject2.optString("category_id");
                  localMovieData.videoId = localJSONObject2.optString("videoId");
                  localMovieData.videoType = localJSONObject2.optInt("videoType");
                  localMovieData.viewType = localJSONObject2.optInt("viewType");
                  localMovieData.name = localJSONObject2.optString("name");
                  localMovieData.img_v = localJSONObject2.optString("imgUrl");
                  startDetailPageActivity(localMovieData);
                  ReportInfo.getInstance().setSearchKey(this.mSearchStr);
                }
                while (true)
                {
                  return true;
                  if (str.equals("TYPE_ACTOR"))
                  {
                    Intent localIntent = new Intent(this, StarDetailedActivity.class);
                    localIntent.putExtra("actor", localJSONObject2.optString("name"));
                    localIntent.putExtra("actorID", localJSONObject2.optString("actor_id"));
                    localIntent.putExtra("labelID", localJSONObject2.optString("label_id"));
                    localIntent.putExtra("labelID_V2", localJSONObject2.optString("label_id_v2"));
                    startActivity(localIntent);
                  }
                }
              }
              if (!"hotword_dosearch".equals(paramString2))
                break label971;
              if (localJSONObject2 != null)
              {
                displayHotwordList(false);
                this.mCurrentType = ReturnType.HOTWROD;
                this.mSearchStr = localJSONObject2.optString("name");
                this.isReportFuncLoad = true;
                this.mTotalPage = 0;
                doSearch(this.mSearchStr);
              }
              return true;
            }
          if ("scrollStopped".equals(paramString1))
          {
            if ("load_more_data".equals(paramString2))
            {
              XulView localXulView = xulGetFocus();
              int i = this.mXulFilmListWrapper.getItemIdx(localXulView);
              if (i > -1)
              {
                this.mCurrentDownloadPage = (i / 40);
                Logger.d("TimeSearchActivityV4", "loadMoreData(" + i + ") mCurrentDownloadPage = " + this.mCurrentDownloadPage + " mTotalPage = " + this.mTotalPage);
                if (!"m_open_popstar_search_page".equals(this.mLastBtnCommand))
                  break label843;
                SccmsApiGetVideoListByLabelTaskListener localSccmsApiGetVideoListByLabelTaskListener = new SccmsApiGetVideoListByLabelTaskListener(this.mSearchStr, this.mSearchRangePackgeId);
                ServerApiManager.i().APISearchActorStarListTask(this.mCurrentDownloadPage, 40, null, this.mSearchStr, localSccmsApiGetVideoListByLabelTaskListener);
                if (1 + this.mCurrentDownloadPage < this.mTotalPage)
                  ServerApiManager.i().APISearchActorStarListTask(1 + this.mCurrentDownloadPage, 40, null, this.mSearchStr, localSccmsApiGetVideoListByLabelTaskListener);
                if (-1 + this.mCurrentDownloadPage > 0)
                  ServerApiManager.i().APISearchActorStarListTask(-1 + this.mCurrentDownloadPage, 40, null, this.mSearchStr, localSccmsApiGetVideoListByLabelTaskListener);
              }
            }
            while (true)
            {
              return true;
              label843: MISccmsApiSearchVideoByPinyinTaskListener localMISccmsApiSearchVideoByPinyinTaskListener = new MISccmsApiSearchVideoByPinyinTaskListener(this.mSearchStr, this.mSearchRangePackgeId);
              ServerApiManager.i().APISearchVideoByPinyin(this.mPacketId, this.mSearchRangePackgeId, this.mSearchStr, this.mCurrentDownloadPage, 40, localMISccmsApiSearchVideoByPinyinTaskListener);
              if (1 + this.mCurrentDownloadPage < this.mTotalPage)
                ServerApiManager.i().APISearchVideoByPinyin(this.mPacketId, this.mSearchRangePackgeId, this.mSearchStr, 1 + this.mCurrentDownloadPage, 40, localMISccmsApiSearchVideoByPinyinTaskListener);
              if (-1 + this.mCurrentDownloadPage > 0)
                ServerApiManager.i().APISearchVideoByPinyin(this.mPacketId, this.mSearchRangePackgeId, this.mSearchStr, -1 + this.mCurrentDownloadPage, 40, localMISccmsApiSearchVideoByPinyinTaskListener);
            }
          }
          label971: if (localXulArrayList.size() <= 0)
          {
            displayEmptyHotWrod(false);
            return true;
          }
          if (localXulData == null)
          {
            displayEmptyHotWrod(false);
            return true;
          }
          if ("doSearch".equals(paramString3))
          {
            this.mTotalPage = 0;
            this.mCurrentType = ReturnType.KEYBOARD;
            this.mSearchStr = this.mXulSearchBox.getAttrString("text");
            if (this.mSearchStr.length() == 0)
            {
              resetSearchRangeCheckedBtn();
              xulFireEvent("appEvents:backPressed");
              clearSearchKey();
              return true;
            }
            XulAttr localXulAttr = ((XulView)localXulArrayList.get(0)).getAttr("rangename");
            if (localXulAttr == null)
            {
              displayEmptyHotWrod(false);
              displayEmptyNotice(true);
              return true;
            }
            this.mLastRangeBtn = localXulAttr.getStringValue();
            this.mLastBtnCommand = this.mLastRangeBtn;
            this.isReportFuncLoad = true;
            if ("m_open_popstar_search_page".equalsIgnoreCase(this.mLastRangeBtn))
            {
              doSearchstar(this.mSearchStr, this.mSearchRangePackgeId);
              return false;
            }
            doSearch(this.mSearchStr, null, this.mSearchRangePackgeId);
            return false;
          }
          switchRangeBtnByCommand(paramString3, localBundle2);
          return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
          localException2 = localException2;
        }
      }
      catch (Exception localException1)
      {
        while (true)
        {
          JSONObject localJSONObject1;
          JSONObject localJSONObject2 = localJSONObject1;
        }
      }
    }
  }

  protected void xulOnRenderIsReady()
  {
    if (GlobalLogic.getInstance().isAppInterfaceReady())
      getData();
    while (true)
    {
      super.xulOnRenderIsReady();
      return;
      showLoaddingDialog();
      App.getInstance().setOnserviceOKListener(new OnServiceOK(null));
    }
  }

  class MISccmsApiGetHotWordsTaskListener
    implements SccmsApiGetHotWordsTask.ISccmsApiGetHotWordsTaskListener
  {
    MISccmsApiGetHotWordsTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Log.e("TimeSearchActivityV4", " MISccmsApiGetHotWordsTaskListener  error.");
      TimeSearchActivityV4.access$202(TimeSearchActivityV4.this, false);
      TimeSearchActivityV4.this.reportLoad(TimeSearchActivityV4.this.isLoadSuccess, null);
      TimeSearchActivityV4.this.showErrorDialogAndReport("ISccmsApiGetHotWordsTaskListener.onError", paramServerApiTaskInfo, paramServerApiCommonError);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, HotWordList paramHotWordList)
    {
      TimeSearchActivityV4.this.onGetHotWordList(paramHotWordList);
      TimeSearchActivityV4.access$202(TimeSearchActivityV4.this, true);
      TimeSearchActivityV4.this.reportLoad(TimeSearchActivityV4.this.isLoadSuccess, null);
    }
  }

  class MISccmsApiSearchVideoByPinyinTaskListener
    implements SccmsApiSearchVideoByPinyinTask.ISccmsApiSearchVideoByPinyinTaskListener
  {
    private String searchRangePackgeId = "";
    private String searchStr = "";

    public MISccmsApiSearchVideoByPinyinTaskListener(String paramString1, String arg3)
    {
      this.searchStr = paramString1;
      Object localObject;
      this.searchRangePackgeId = localObject;
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      String str1 = paramServerApiTaskInfo.getHttpRequestUrl();
      TimeSearchActivityV4 localTimeSearchActivityV4 = TimeSearchActivityV4.this;
      if (str1.length() > 1);
      for (String str2 = paramServerApiTaskInfo.getHttpRequestUrl().split("\\?")[1]; ; str2 = "")
      {
        TimeSearchActivityV4.access$402(localTimeSearchActivityV4, str2);
        TimeSearchActivityV4.this.displayLoadingView(false);
        if (TimeSearchActivityV4.this.isReportFuncLoad)
        {
          long l = System.currentTimeMillis() - TimeSearchActivityV4.this.searchStartTime;
          TimeSearchActivityV4.this.reportFuncLoad(0, null, TimeSearchActivityV4.this.mSearchStr, l, false, 0, TimeSearchActivityV4.this.EPGParameter);
          TimeSearchActivityV4.access$602(TimeSearchActivityV4.this, false);
        }
        TimeSearchActivityV4.this.showErrorDialogAndReport("ISccmsApiSearchVideoByPinyinTaskListener.onError", paramServerApiTaskInfo, paramServerApiCommonError);
        return;
      }
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, SearchStruct paramSearchStruct)
    {
      if ((!TextUtils.isEmpty(TimeSearchActivityV4.this.mSearchStr)) && (!TimeSearchActivityV4.this.mSearchStr.equals(this.searchStr)))
        break label30;
      label30: 
      while ((!TextUtils.isEmpty(TimeSearchActivityV4.this.mSearchRangePackgeId)) && (!TimeSearchActivityV4.this.mSearchRangePackgeId.equals(this.searchRangePackgeId)))
        return;
      String str1 = paramServerApiTaskInfo.getHttpRequestUrl();
      TimeSearchActivityV4 localTimeSearchActivityV4 = TimeSearchActivityV4.this;
      if (str1.length() > 1);
      for (String str2 = paramServerApiTaskInfo.getHttpRequestUrl().split("\\?")[1]; ; str2 = "")
      {
        TimeSearchActivityV4.access$402(localTimeSearchActivityV4, str2);
        TimeSearchActivityV4.this.displayLoadingView(false);
        if ((paramSearchStruct != null) && (paramSearchStruct.sItemList != null) && (paramSearchStruct.sItemList.size() != 0))
          break label216;
        long l1 = System.currentTimeMillis() - TimeSearchActivityV4.this.searchStartTime;
        TimeSearchActivityV4.this.reportFuncLoad(0, null, TimeSearchActivityV4.this.mSearchStr, l1, true, 0, TimeSearchActivityV4.this.EPGParameter);
        TimeSearchActivityV4.this.displayEmptyNotice(true);
        if (!TimeSearchActivityV4.this.needResetFocus)
          break;
        XulView localXulView1 = TimeSearchActivityV4.this.xulFindViewById("search_range_firstbtn");
        TimeSearchActivityV4.this.xulRequestFocus(localXulView1);
        return;
      }
      label216: TimeSearchActivityV4.this.mColorizedFilmNameGenerator.setMatcher(TimeSearchActivityV4.this.mSearchStr);
      TimeSearchActivityV4.access$1202(TimeSearchActivityV4.this, paramSearchStruct.item_num);
      TimeSearchActivityV4.access$1302(TimeSearchActivityV4.this, paramSearchStruct.asset_pages_count);
      if ((TimeSearchActivityV4.this.mCurrentDownloadPage == 0) && (TimeSearchActivityV4.this.mXulFilmListWrapper.itemNum() == 0))
      {
        Iterator localIterator = paramSearchStruct.sItemList.iterator();
        while (localIterator.hasNext())
        {
          SearchListItem localSearchListItem2 = (SearchListItem)localIterator.next();
          XulDataNode localXulDataNode3 = XulDataNode.obtainDataNode("item");
          String str3 = TimeSearchActivityV4.this.mColorizedFilmNameGenerator.getSpannedLabelText(localSearchListItem2.name);
          localXulDataNode3.setAttribute("poster_type", "TYPE_FILM");
          localXulDataNode3.setAttribute("name", localSearchListItem2.name);
          localXulDataNode3.setAttribute("colorized_name", str3);
          localXulDataNode3.setAttribute("ui_style", localSearchListItem2.ui_style + "");
          localXulDataNode3.setAttribute("video_id", localSearchListItem2.video_id);
          localXulDataNode3.setAttribute("video_type", localSearchListItem2.video_type + "");
          localXulDataNode3.setAttribute("media_asset_id", localSearchListItem2.package_id);
          localXulDataNode3.setAttribute("category_id", localSearchListItem2.category_id);
          localXulDataNode3.setAttribute("img_v", localSearchListItem2.img_v);
          localXulDataNode3.setAttribute("img_h", localSearchListItem2.img_h);
          localXulDataNode3.setAttribute("corner_mark", localSearchListItem2.corner_mark);
          localXulDataNode3.setAttribute("corner_mark_img", localSearchListItem2.corner_mark_img_url);
          localXulDataNode3.setAttribute("import_id", localSearchListItem2.import_id);
          localXulDataNode3.setAttribute("serial_id", localSearchListItem2.serial_id);
          localXulDataNode3.setAttribute("isPlaceHolder", "false");
          TimeSearchActivityV4.this.mXulFilmListWrapper.addItem(localXulDataNode3);
        }
        for (int m = 0; ; m++)
        {
          int n = TimeSearchActivityV4.this.mSearchResultCount - paramSearchStruct.sItemList.size();
          if (m >= n)
            break;
          XulDataNode localXulDataNode2 = XulDataNode.obtainDataNode("item");
          localXulDataNode2.setAttribute("img_v", "");
          localXulDataNode2.setAttribute("isPlaceHolder", "true");
          TimeSearchActivityV4.this.mXulFilmListWrapper.addItem(localXulDataNode2);
        }
        TimeSearchActivityV4.this.mXulFilmListWrapper.syncContentView();
        XulView localXulView2 = ((XulArea)TimeSearchActivityV4.this.mXulFilmListView).getChild(0);
        ((XulArea)TimeSearchActivityV4.this.mXulContentLayer).setDynamicFocus(localXulView2);
        ((XulArea)TimeSearchActivityV4.this.mXulSearchResultView).setDynamicFocus(localXulView2);
        if (TimeSearchActivityV4.this.needResetFocus)
        {
          Logger.i("TimeSearchActivityV4", "set focus to the fisrt view.");
          TimeSearchActivityV4.this.xulRequestFocus(localXulView2);
        }
      }
      while (true)
      {
        TimeSearchActivityV4.access$2012(TimeSearchActivityV4.this, paramSearchStruct.sItemList.size());
        if (TimeSearchActivityV4.this.isReportFuncLoad)
        {
          long l2 = System.currentTimeMillis() - TimeSearchActivityV4.this.searchStartTime;
          TimeSearchActivityV4.this.reportFuncLoad(0, null, TimeSearchActivityV4.this.mSearchStr, l2, true, paramSearchStruct.sItemList.size(), TimeSearchActivityV4.this.EPGParameter);
          TimeSearchActivityV4.access$602(TimeSearchActivityV4.this, false);
        }
        TimeSearchActivityV4.this.displayEmptyNotice(false);
        return;
        ArrayList localArrayList = new ArrayList();
        for (int i = 0; ; i++)
        {
          int j = paramSearchStruct.sItemList.size();
          if (i >= j)
            break;
          SearchListItem localSearchListItem1 = (SearchListItem)paramSearchStruct.sItemList.get(i);
          XulDataNode localXulDataNode1 = XulDataNode.obtainDataNode("item");
          localXulDataNode1.setAttribute("poster_type", "TYPE_FILM");
          localXulDataNode1.setAttribute("name", localSearchListItem1.name);
          localXulDataNode1.setAttribute("colorized_name", TimeSearchActivityV4.this.mColorizedFilmNameGenerator.getSpannedLabelText(localSearchListItem1.name));
          localXulDataNode1.setAttribute("ui_style", localSearchListItem1.ui_style + "");
          localXulDataNode1.setAttribute("video_id", localSearchListItem1.video_id);
          localXulDataNode1.setAttribute("video_type", localSearchListItem1.video_type + "");
          localXulDataNode1.setAttribute("media_asset_id", localSearchListItem1.package_id);
          localXulDataNode1.setAttribute("category_id", localSearchListItem1.category_id);
          localXulDataNode1.setAttribute("img_v", localSearchListItem1.img_v);
          localXulDataNode1.setAttribute("img_h", localSearchListItem1.img_h);
          localXulDataNode1.setAttribute("corner_mark", localSearchListItem1.corner_mark);
          localXulDataNode1.setAttribute("corner_mark_img", localSearchListItem1.corner_mark_img_url);
          localXulDataNode1.setAttribute("import_id", localSearchListItem1.import_id);
          localXulDataNode1.setAttribute("serial_id", localSearchListItem1.serial_id);
          localXulDataNode1.setAttribute("isPlaceHolder", "false");
          localArrayList.add(localXulDataNode1);
        }
        if (!TimeSearchActivityV4.this.mDownloadedPageIndexList.contains(Integer.valueOf(paramSearchStruct.cur_page)))
        {
          int k = 40 * paramSearchStruct.cur_page;
          TimeSearchActivityV4.this.mXulFilmListWrapper.updateItems(k, localArrayList);
        }
        else
        {
          TimeSearchActivityV4.this.mDownloadedPageIndexList.add(Integer.valueOf(paramSearchStruct.cur_page));
        }
      }
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
        TimeSearchActivityV4.this.xulRefreshBinding("api_n36");
        TimeSearchActivityV4.this.getData();
        TimeSearchActivityV4.this.dismissLoaddingDialog();
      }
    }

    public void onGetApiDataError()
    {
    }

    public void onNeedFinishActivity()
    {
      TimeSearchActivityV4.this.finish();
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
      new InitApiData(TimeSearchActivityV4.this).setOnApiOkListener(new TimeSearchActivityV4.OnApiOk(TimeSearchActivityV4.this, null));
    }
  }

  private static enum ReturnType
  {
    static
    {
      HOTWROD = new ReturnType("HOTWROD", 1);
      DEFAULT = new ReturnType("DEFAULT", 2);
      ReturnType[] arrayOfReturnType = new ReturnType[3];
      arrayOfReturnType[0] = KEYBOARD;
      arrayOfReturnType[1] = HOTWROD;
      arrayOfReturnType[2] = DEFAULT;
    }
  }

  class SccmsApiGetVideoListByLabelTaskListener
    implements SccmsApiSearchActorStarListTask.ISccmsApiSearchActorStarListTaskListener
  {
    private String searchRangePackgeId = "";
    private String searchStr = "";

    public SccmsApiGetVideoListByLabelTaskListener(String paramString1, String arg3)
    {
      this.searchStr = paramString1;
      Object localObject;
      this.searchRangePackgeId = localObject;
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      TimeSearchActivityV4.this.displayLoadingView(false);
      TimeSearchActivityV4 localTimeSearchActivityV4;
      if (TimeSearchActivityV4.this.isReportFuncLoad)
      {
        String str1 = paramServerApiTaskInfo.getHttpRequestUrl();
        localTimeSearchActivityV4 = TimeSearchActivityV4.this;
        if (str1.length() <= 1)
          break label119;
      }
      label119: for (String str2 = paramServerApiTaskInfo.getHttpRequestUrl().split("\\?")[1]; ; str2 = "")
      {
        TimeSearchActivityV4.access$402(localTimeSearchActivityV4, str2);
        long l = System.currentTimeMillis() - TimeSearchActivityV4.this.searchStartTime;
        TimeSearchActivityV4.this.reportFuncLoad(0, null, TimeSearchActivityV4.this.mSearchStr, l, false, 0, TimeSearchActivityV4.this.EPGParameter);
        TimeSearchActivityV4.access$602(TimeSearchActivityV4.this, false);
        TimeSearchActivityV4.this.showErrorDialogAndReport("ISccmsApiSearchActorStarListTaskListener.onError", paramServerApiTaskInfo, paramServerApiCommonError);
        return;
      }
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, SearchActorStarList paramSearchActorStarList)
    {
      if ((!TextUtils.isEmpty(TimeSearchActivityV4.this.mSearchStr)) && (!TimeSearchActivityV4.this.mSearchStr.equals(this.searchStr)));
      while ((!TextUtils.isEmpty(TimeSearchActivityV4.this.mSearchRangePackgeId)) && (!TimeSearchActivityV4.this.mSearchRangePackgeId.equals(this.searchRangePackgeId)))
        return;
      String str1 = paramServerApiTaskInfo.getHttpRequestUrl();
      TimeSearchActivityV4 localTimeSearchActivityV4 = TimeSearchActivityV4.this;
      if (str1.length() > 1);
      for (String str2 = paramServerApiTaskInfo.getHttpRequestUrl().split("\\?")[1]; ; str2 = "")
      {
        TimeSearchActivityV4.access$402(localTimeSearchActivityV4, str2);
        TimeSearchActivityV4.this.mColorizedFilmNameGenerator.setMatcher(TimeSearchActivityV4.this.mSearchStr);
        TimeSearchActivityV4.this.displayLoadingView(false);
        TimeSearchActivityV4.this.onGetsearchStar(paramSearchActorStarList);
        return;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.TimeSearchActivityV4
 * JD-Core Version:    0.6.2
 */