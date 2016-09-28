package com.starcor.hunan;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.starcor.core.ai.AIDataCacheTask;
import com.starcor.core.domain.LabelStarList;
import com.starcor.core.domain.LabelStarList.LabelStarItemStructure;
import com.starcor.core.domain.SearchActorStarList;
import com.starcor.core.domain.SearchActorStarList.ActorStar;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.uilogic.CommonUiTools;
import com.starcor.hunan.uilogic.FilmListItemTextColorizer;
import com.starcor.hunan.widget.FilmListView;
import com.starcor.hunan.widget.FilmListView.OnClickListener;
import com.starcor.hunan.widget.FilmListView.OnScrollingListener;
import com.starcor.hunan.widget.FilmListView.OnSelectionChangedListener;
import com.starcor.media.player.ApiTaskControl;
import com.starcor.sccms.api.SccmsApiGetHotActorStarListTask.ISccmsApiGetHotActorStarListTaskListener;
import com.starcor.sccms.api.SccmsApiGetLabelStarListTask.ISccmsApiGetLabelStarListTaskListener;
import com.starcor.sccms.api.SccmsApiSearchActorStarListTask.ISccmsApiSearchActorStarListTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.Wrapper.XulGroupAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulPendingInputStream;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class StarActivity extends BaseActivity
{
  public static final int MESSAGE_HIDE_PAGE_AND_SCROLL_BAR = 1;
  private static int PAGE_BIG_TEXT_SIZE = 0;
  private static int PAGE_SMALL_TEXT_SIZE = 0;
  private static final int POSTER_COLUMN_SIZE = 5;
  public static final String TAG = StarActivity.class.getSimpleName();
  public static final int TIMEOUT_HIDE_PAGE_AND_SCROLL_BAR = 1500;
  private String EPGParameter = "";
  private int currentDownLoadPage = 0;
  private StarType currentType = StarType.HOTSTAR;
  private int downloadPageSize = 0;
  private int filmItemCount;
  private FilmListItemTextColorizer filmListItemTextColorizer = new FilmListItemTextColorizer();
  private int filmPageCount;
  private boolean isDownloadingMore = false;
  private boolean isFirstLoad = false;
  private boolean isLoadSuccess = false;
  private boolean isReportFuncLoad = false;
  private String labelId = "";
  private Handler mPageHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
      case 1:
      }
      do
        return;
      while (StarActivity.this.pageText == null);
      StarActivity.this.pageText.setVisibility(4);
    }
  };
  private String markedLabel;
  private int onePageSize = 10;
  private XulView pageDetailsInfo;
  XulPendingInputStream pageFilterStream = new XulPendingInputStream();
  private String pageInfoFormat = "%s页    ";
  private int pageLines;
  private TextView pageText;
  private int posterHeight;
  private int posterWidth;
  private String resultInfoFormat = "共%s个结果";
  private String searchKey = "";
  private long searchStartTime = -1L;
  private XulView searchTextBox;
  private FilmListView starListView;
  private ApiTaskControl taskControl;
  private XulView xulStarListView;

  private void addTaskToGetStarList()
  {
    SccmsApiGetVideoListByLabelTaskListener localSccmsApiGetVideoListByLabelTaskListener = new SccmsApiGetVideoListByLabelTaskListener(null);
    int i = ServerApiManager.i().APISearchActorStarListTask(this.currentDownLoadPage, this.downloadPageSize, this.labelId, this.searchKey, localSccmsApiGetVideoListByLabelTaskListener);
    this.markedLabel = GeneralUtils.MD5(this.currentDownLoadPage + this.downloadPageSize + this.labelId + this.searchKey);
    this.taskControl.clear();
    this.taskControl.addTaskLabel(i, "api", this.markedLabel);
  }

  private void doSearch()
  {
    this.searchStartTime = System.currentTimeMillis();
    showLoaddingDialog();
    isShowHotStarText(false);
    this.starListView.removeAllItems();
    setPageDetailInfo("");
    SccmsApiGetVideoListByLabelTaskListener localSccmsApiGetVideoListByLabelTaskListener = new SccmsApiGetVideoListByLabelTaskListener(null);
    int i = ServerApiManager.i().APISearchActorStarListTask(this.currentDownLoadPage, this.downloadPageSize, this.labelId, this.searchKey, localSccmsApiGetVideoListByLabelTaskListener);
    this.markedLabel = GeneralUtils.MD5(this.currentDownLoadPage + this.downloadPageSize + this.labelId + this.searchKey);
    this.taskControl.clear();
    this.taskControl.addTaskLabel(i, "api", this.markedLabel);
  }

  private void failedProcess()
  {
    this.filmItemCount = 0;
    this.filmPageCount = 0;
    setFilmCountInfo();
    showEmptyTips(true);
    if (this.xulStarListView != null)
    {
      this.xulStarListView.setStyle("display", "none");
      this.xulStarListView.resetRender();
    }
  }

  private void getHotActorStarList()
  {
    showLoaddingDialog();
    isShowHotStarText(true);
    this.starListView.removeAllItems();
    setPageDetailInfo("");
    SccmsApiGetHotActorStarListTaskListener localSccmsApiGetHotActorStarListTaskListener = new SccmsApiGetHotActorStarListTaskListener();
    int i = ServerApiManager.i().APIGetHotActorStarListTask(localSccmsApiGetHotActorStarListTaskListener);
    this.taskControl.clear();
    this.taskControl.addTaskLabel(i, "api", "hotActor");
  }

  private ByteArrayInputStream getStarFilterInfo(ArrayList<LabelStarList.LabelStarItemStructure> paramArrayList)
  {
    XmlSerializer localXmlSerializer;
    StringWriter localStringWriter;
    while (true)
    {
      LabelStarList.LabelStarItemStructure localLabelStarItemStructure2;
      try
      {
        localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        localStringWriter = new StringWriter();
        localXmlSerializer.setOutput(localStringWriter);
        localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer.startTag(null, "data");
        localXmlSerializer.startTag(null, "page_filter");
        Iterator localIterator1 = paramArrayList.iterator();
        if (!localIterator1.hasNext())
          break;
        LabelStarList.LabelStarItemStructure localLabelStarItemStructure1 = (LabelStarList.LabelStarItemStructure)localIterator1.next();
        localXmlSerializer.startTag(null, "group");
        writeNodeValue(localXmlSerializer, "name", localLabelStarItemStructure1.name);
        localXmlSerializer.startTag(null, "item");
        writeNodeValue(localXmlSerializer, "name", "不限");
        writeNodeValue(localXmlSerializer, "action", "internal_apply_filter");
        writeNodeValue(localXmlSerializer, "userdata", "");
        localXmlSerializer.endTag(null, "item");
        Iterator localIterator2 = localLabelStarItemStructure1.l.iterator();
        if (!localIterator2.hasNext())
          break label285;
        localLabelStarItemStructure2 = (LabelStarList.LabelStarItemStructure)localIterator2.next();
        localXmlSerializer.startTag(null, "item");
        writeNodeValue(localXmlSerializer, "name", localLabelStarItemStructure2.name);
        writeNodeValue(localXmlSerializer, "action", "internal_apply_filter");
        if (localLabelStarItemStructure2.id == null)
        {
          str = "";
          writeNodeValue(localXmlSerializer, "userdata", str);
          localXmlSerializer.endTag(null, "item");
          continue;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      String str = localLabelStarItemStructure2.id;
      continue;
      label285: localXmlSerializer.endTag(null, "group");
    }
    localXmlSerializer.endTag(null, "page_filter");
    localXmlSerializer.endTag(null, "data");
    localXmlSerializer.endDocument();
    localXmlSerializer.flush();
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
    return localByteArrayInputStream;
  }

  private void hidePageAndScrollBar(int paramInt)
  {
    if (this.mPageHandler.hasMessages(1))
      this.mPageHandler.removeMessages(1);
    this.mPageHandler.sendEmptyMessageDelayed(1, paramInt);
  }

  private void isShowHotStarText(boolean paramBoolean)
  {
    if (paramBoolean)
      this.currentType = StarType.HOTSTAR;
    XulView localXulView = xulFindViewById("hot_star_label");
    if (localXulView != null)
      if (!paramBoolean)
        break label44;
    label44: for (String str = "block"; ; str = "none")
    {
      localXulView.setStyle("display", str);
      localXulView.resetRender();
      return;
    }
  }

  private void onGetVideoList(SearchActorStarList paramSearchActorStarList)
  {
    if (paramSearchActorStarList == null)
      return;
    this.filmItemCount = paramSearchActorStarList.total_rows;
    this.filmPageCount = ((int)Math.ceil(1.0F * this.filmItemCount / this.onePageSize));
    Iterator localIterator = paramSearchActorStarList.lists.iterator();
    while (localIterator.hasNext())
    {
      SearchActorStarList.ActorStar localActorStar = (SearchActorStarList.ActorStar)localIterator.next();
      String str2 = CommonUiTools.processCompressImageUrl(localActorStar.img_v, localActorStar.img_h, this.posterWidth, this.posterHeight);
      this.starListView.addItem(localActorStar.name, str2, localActorStar);
    }
    setFilmCountInfo();
    String str1;
    if (this.xulStarListView != null)
    {
      XulView localXulView = this.xulStarListView;
      if (this.filmItemCount <= 0)
      {
        str1 = "none";
        localXulView.setStyle("display", str1);
        this.xulStarListView.resetRender();
      }
    }
    else
    {
      if (this.filmItemCount > 0)
        break label175;
    }
    label175: for (boolean bool = true; ; bool = false)
    {
      showEmptyTips(bool);
      return;
      str1 = "block";
      break;
    }
  }

  private void restoreData()
  {
    this.labelId = "";
    this.searchKey = "";
    this.currentDownLoadPage = 0;
    this.filmListItemTextColorizer.setMatcher("");
    XulView localXulView1 = xulFindViewById("page_content_filter");
    if ((localXulView1 instanceof XulArea))
    {
      XulArrayList localXulArrayList = XulPage.findItemsByClass((XulArea)localXulView1, "page_content_filter_group");
      if (localXulArrayList != null)
      {
        Iterator localIterator = localXulArrayList.iterator();
        while (localIterator.hasNext())
        {
          XulGroupAreaWrapper localXulGroupAreaWrapper = XulGroupAreaWrapper.fromXulView((XulView)localIterator.next());
          if (localXulGroupAreaWrapper != null)
          {
            localXulGroupAreaWrapper.setAllUnchecked();
            localXulGroupAreaWrapper.setAllChecked();
          }
        }
      }
    }
    XulView localXulView2 = xulFindViewById("search_text_box");
    if (localXulView2 != null)
    {
      localXulView2.setAttr("text", "");
      localXulView2.resetRender();
    }
  }

  private void setFilmCountInfo()
  {
    String str2;
    String str4;
    SpannableStringBuilder localSpannableStringBuilder;
    if ((this.filmPageCount != 0) && (this.pageLines != 0))
    {
      String str5 = this.pageInfoFormat;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = (1 + this.starListView.getCursorLine() / 2 + "/" + this.filmPageCount);
      str2 = String.format(str5, arrayOfObject3);
      if (this.pageText != null)
      {
        str4 = 1 + this.starListView.getCursorLine() / this.pageLines + "";
        localSpannableStringBuilder = new SpannableStringBuilder(str2);
        if (str4.length() >= str2.length())
          break label337;
        localSpannableStringBuilder.setSpan(new AbsoluteSizeSpan(App.Operation(PAGE_BIG_TEXT_SIZE)), 0, str4.length(), 33);
        localSpannableStringBuilder.setSpan(new ForegroundColorSpan(-436207617), 0, str4.length(), 33);
        localSpannableStringBuilder.setSpan(new AbsoluteSizeSpan(App.Operation(PAGE_SMALL_TEXT_SIZE)), str4.length(), str2.length(), 33);
        localSpannableStringBuilder.setSpan(new ForegroundColorSpan(-1291845633), str4.length(), str2.length(), 33);
      }
    }
    while (true)
    {
      this.pageText.setTypeface(Typeface.DEFAULT);
      this.pageText.setBackgroundResource(2130837609);
      this.pageText.setText(localSpannableStringBuilder);
      String str3 = this.resultInfoFormat;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = String.valueOf(this.filmItemCount);
      setPageDetailInfo(String.format(str3, arrayOfObject2));
      return;
      String str1 = this.resultInfoFormat;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = String.valueOf(this.filmItemCount);
      str2 = String.format(str1, arrayOfObject1);
      break;
      label337: Logger.e(TAG, "str_currentPage.length():" + str4.length() + ">= infoText.length():" + str2.length() + "!");
    }
  }

  private void setPageDetailInfo(String paramString)
  {
    if (this.pageDetailsInfo == null)
      this.pageDetailsInfo = xulFindViewById("page_details_info");
    if (this.pageDetailsInfo == null)
      return;
    this.pageDetailsInfo.setAttr("text", paramString);
    this.pageDetailsInfo.resetRender();
  }

  private void showEmptyTips(boolean paramBoolean)
  {
    XulView localXulView1 = xulFindViewById("film_list_view");
    boolean bool;
    String str2;
    label34: XulView localXulView2;
    String str1;
    if (localXulView1 != null)
    {
      if (!paramBoolean)
      {
        bool = true;
        localXulView1.setEnabled(bool);
        if (paramBoolean)
          break label106;
        str2 = "block";
        localXulView1.setStyle("display", str2);
        localXulView1.resetRender();
      }
    }
    else
    {
      localXulView2 = xulFindViewById("page_details_empty_tips");
      if (localXulView2 != null)
      {
        if (!paramBoolean)
          break label114;
        str1 = "block";
        label68: localXulView2.setStyle("display", str1);
        if (this.currentType != StarType.FILTER)
          break label122;
        xulFireEvent("starEvents:filter");
      }
    }
    while (true)
    {
      localXulView2.resetRender();
      return;
      bool = false;
      break;
      label106: str2 = "none";
      break label34;
      label114: str1 = "none";
      break label68;
      label122: xulFireEvent("starEvents:search");
    }
  }

  private void showErrorDialogAndReport(int paramInt, String paramString1, String paramString2, ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
  {
    if (!this.hasDialog)
    {
      showErrorDialogWithReport(paramInt, paramString1, paramString2, paramServerApiTaskInfo, paramServerApiCommonError);
      this.hasDialog = true;
    }
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
    if (this.currentType == StarType.HOTSTAR)
    {
      if (xulFindViewById("page_content_filter").isVisible())
      {
        restoreData();
        xulFireEvent("appEvents:backPressed");
        return;
      }
      super.onBackPressed();
      return;
    }
    restoreData();
    xulFireEvent("appEvents:backPressed");
    this.isFirstLoad = false;
    getHotActorStarList();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    showLoaddingDialog();
    initXul("StarPage", true);
    xulUpdateTitle("明星库", "");
    this.taskControl = new ApiTaskControl();
    ServerApiManager.i().APIGetLabelStarListTask(new SccmsApiGetLabelStarListTask.ISccmsApiGetLabelStarListTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, LabelStarList paramAnonymousLabelStarList)
      {
        if ((paramAnonymousLabelStarList != null) && (paramAnonymousLabelStarList.l != null) && (paramAnonymousLabelStarList.l.size() > 0))
          StarActivity.this.pageFilterStream.setBaseStream(StarActivity.this.getStarFilterInfo(paramAnonymousLabelStarList.l));
      }
    });
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

  protected void onStop()
  {
    super.onStop();
    reportExit(this.isLoadSuccess, null);
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    if ("jsCmd".equals(paramString2));
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString3);
      while (true)
      {
        try
        {
          if ("text_changed".equals(localJSONObject.optString("cmd")))
          {
            this.isReportFuncLoad = true;
            this.labelId = "";
            this.currentDownLoadPage = 0;
            this.searchTextBox = xulFindViewById("search_text_box");
            this.searchKey = this.searchTextBox.getAttrString("text");
            this.filmListItemTextColorizer.setMatcher(this.searchKey);
            if (TextUtils.isEmpty(this.searchKey))
            {
              this.isFirstLoad = false;
              getHotActorStarList();
            }
          }
          else
          {
            if ((paramString1.equals("click")) && (paramString2.equals("usr_cmd")) && (paramString3.equals("internal_apply_filter")))
              break label221;
            if (paramString2.equals("usr_cmd"))
            {
              if (!paramString3.equals("click_search"))
                break label420;
              restoreData();
              xulRequestFocus(xulFindViewById("keyboard_button_middle"));
              this.isFirstLoad = false;
              getHotActorStarList();
            }
            return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
          }
          this.currentType = StarType.SEARCH;
          doSearch();
          continue;
        }
        catch (JSONException localJSONException1)
        {
        }
        label213: localJSONException1.printStackTrace();
        continue;
        label221: this.labelId = "";
        this.searchKey = "";
        this.currentDownLoadPage = 0;
        this.filmListItemTextColorizer.setMatcher(this.searchKey);
        XulView localXulView = xulFindViewById("page_content_filter");
        if ((localXulView != null) && ((localXulView instanceof XulArea)))
        {
          XulArrayList localXulArrayList = XulPage.findItemsByClass((XulArea)localXulView, "page_content_filter_radio_checked");
          if (localXulArrayList != null)
          {
            Iterator localIterator = localXulArrayList.iterator();
            while (localIterator.hasNext())
            {
              String str = ((XulView)localIterator.next()).getDataString("userdata");
              if (!TextUtils.isEmpty(str))
                if (TextUtils.isEmpty(this.labelId))
                  this.labelId += str;
                else
                  this.labelId = (this.labelId + "," + str);
            }
            this.currentType = StarType.FILTER;
            doSearch();
            continue;
            label420: if (paramString3.equals("click_filter"))
            {
              restoreData();
              this.currentType = StarType.FILTER;
              doSearch();
            }
          }
        }
      }
    }
    catch (JSONException localJSONException2)
    {
      break label213;
    }
  }

  protected InputStream xulGetAppData(XulWorker.DownloadItem paramDownloadItem, String paramString)
  {
    if ("internal/page_filter".equals(paramString))
      return this.pageFilterStream;
    return super.xulGetAppData(paramDownloadItem, paramString);
  }

  protected void xulOnRenderIsReady()
  {
    this.xulStarListView = xulFindViewById("film_list_view");
    this.starListView = ((FilmListView)this.xulStarListView.getExternalView());
    this.starListView.setFocusable(true);
    this.starListView.setCursorAlwaysVisible(false);
    this.starListView.setDrawPlaceHolder(false);
    if (XulRenderContext.getDefTypeFace() != null)
      this.starListView.setTextTypeFace(XulRenderContext.getDefTypeFace());
    this.pageLines = 2;
    this.onePageSize = (5 * this.pageLines);
    this.downloadPageSize = (4 * (5 * this.pageLines));
    this.starListView.setOnSelectionChangedListener(new OnSelectionChangedLsnrOfmovieListView(null));
    this.starListView.setOnItemClickListener(new OnClickLsnrOfMovieListView(null));
    this.posterWidth = App.Operation(145.0F);
    this.posterHeight = App.Operation(200.0F);
    this.starListView.setViewGrid(5, this.pageLines);
    this.starListView.setFocusScalar(1.1F);
    this.starListView.setOnDrawItemListener(null);
    this.starListView.setItemGrid(App.Operation(180.0F), App.Operation(270.0F), App.Operation(5.0F), this.posterWidth, this.posterHeight);
    this.starListView.setItemPadding(App.Operation(13.0F), App.Operation(5.0F), App.Operation(13.0F), App.Operation(10.0F));
    this.starListView.setViewOffset(8, 10);
    this.starListView.setDefaultBkg("xul/default_star.png");
    this.starListView.setItemTextColorizer(this.filmListItemTextColorizer);
    this.starListView.setOnScrollingListener(new FilmListView.OnScrollingListener()
    {
      public void onScrolling(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        if (StarActivity.this.pageText != null)
          StarActivity.this.pageText.setVisibility(0);
        StarActivity.this.hidePageAndScrollBar(1500);
      }
    });
    this.pageInfoFormat = this.xulStarListView.getDataString("page_info_format");
    this.resultInfoFormat = this.xulStarListView.getDataString("result_info_format");
    this.isFirstLoad = true;
    getHotActorStarList();
    this.pageText = new TextView(this);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(App.OperationHeight(500), App.OperationHeight(250));
    localLayoutParams.addRule(12);
    localLayoutParams.addRule(11);
    this.pageText.setLayoutParams(localLayoutParams);
    this.pageText.setGravity(85);
    this.pageText.setPadding(App.OperationHeight(0), App.OperationHeight(0), App.OperationHeight(40), App.OperationHeight(20));
    xulAddView(this.pageText);
    this.pageText.setVisibility(4);
    super.xulOnRenderIsReady();
  }

  private final class OnClickLsnrOfMovieListView
    implements FilmListView.OnClickListener
  {
    private OnClickLsnrOfMovieListView()
    {
    }

    public void onClick(int paramInt, Object paramObject)
    {
      SearchActorStarList.ActorStar localActorStar = (SearchActorStarList.ActorStar)paramObject;
      if (localActorStar != null)
      {
        Intent localIntent = new Intent(StarActivity.this, StarDetailedActivity.class);
        localIntent.putExtra("actor", localActorStar.name);
        localIntent.putExtra("actorID", localActorStar.id);
        localIntent.putExtra("labelID", localActorStar.label_id);
        localIntent.putExtra("labelID_V2", localActorStar.label_id_v2);
        StarActivity.this.startActivity(localIntent);
      }
    }
  }

  private final class OnSelectionChangedLsnrOfmovieListView
    implements FilmListView.OnSelectionChangedListener
  {
    private OnSelectionChangedLsnrOfmovieListView()
    {
    }

    public void onSelectionChanged(int paramInt, Object paramObject)
    {
      StarActivity.this.setFilmCountInfo();
      SearchActorStarList.ActorStar localActorStar = (SearchActorStarList.ActorStar)paramObject;
      JSONObject localJSONObject;
      if (localActorStar != null)
        localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("actor_id", localActorStar.id);
        AIDataCacheTask.i().filterJavaAction("ai_open_one_star", "ai_focus", localJSONObject.toString(), "");
        Logger.i(StarActivity.TAG, "onSelectionChanged position:" + paramInt);
        if (StarActivity.this.currentType == StarActivity.StarType.HOTSTAR)
          return;
      }
      catch (JSONException localJSONException)
      {
        do
          while (true)
            localJSONException.printStackTrace();
        while ((StarActivity.this.isDownloadingMore) || (paramInt + StarActivity.this.downloadPageSize < StarActivity.this.starListView.getItemCount()) || (StarActivity.this.starListView.getItemCount() >= StarActivity.this.filmItemCount));
        StarActivity.access$802(StarActivity.this, true);
        StarActivity.access$1208(StarActivity.this);
        StarActivity.this.addTaskToGetStarList();
      }
    }
  }

  public class SccmsApiGetHotActorStarListTaskListener
    implements SccmsApiGetHotActorStarListTask.ISccmsApiGetHotActorStarListTaskListener
  {
    public SccmsApiGetHotActorStarListTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      if (StarActivity.this.isFirstLoad)
      {
        StarActivity.access$2402(StarActivity.this, false);
        StarActivity.this.reportLoad(StarActivity.this.isLoadSuccess, null);
      }
      if (!StarActivity.this.taskControl.checkTaskValid(paramServerApiTaskInfo.getTaskId(), "hotActor"))
      {
        Logger.i(StarActivity.TAG, "ISccmsApiGetHotActorStarListTaskListener.onError() invalid task");
        return;
      }
      Logger.i(StarActivity.TAG, "SccmsApiGetHotActorStarListTaskListener onError()");
      StarActivity.this.dismissLoaddingDialog();
      StarActivity.this.showErrorDialogAndReport(11, "1002008", "ISccmsApiGetHotActorStarListTaskListener.onError", paramServerApiTaskInfo, paramServerApiCommonError);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, SearchActorStarList paramSearchActorStarList)
    {
      if (!StarActivity.this.taskControl.checkTaskValid(paramServerApiTaskInfo.getTaskId(), "hotActor"))
      {
        if (StarActivity.this.isFirstLoad)
        {
          StarActivity.access$2402(StarActivity.this, false);
          StarActivity.this.reportLoad(StarActivity.this.isLoadSuccess, null);
        }
        Logger.i(StarActivity.TAG, "ISccmsApiGetHotActorStarListTaskListener.onError() invalid task");
        return;
      }
      Logger.i(StarActivity.TAG, "SccmsApiGetHotActorStarListTaskListener onSuccess(), result:" + paramSearchActorStarList.toString());
      StarActivity.this.dismissLoaddingDialog();
      int i;
      if ((paramSearchActorStarList != null) && (paramSearchActorStarList.lists != null))
      {
        i = paramSearchActorStarList.lists.size();
        if (i > 0)
          break label170;
      }
      label170: for (paramSearchActorStarList.total_rows = 0; ; paramSearchActorStarList.total_rows = i)
      {
        StarActivity.this.onGetVideoList(paramSearchActorStarList);
        if (!StarActivity.this.isFirstLoad)
          break;
        StarActivity.access$2402(StarActivity.this, true);
        StarActivity.this.reportLoad(StarActivity.this.isLoadSuccess, null);
        return;
      }
    }
  }

  private final class SccmsApiGetVideoListByLabelTaskListener
    implements SccmsApiSearchActorStarListTask.ISccmsApiSearchActorStarListTaskListener
  {
    private SccmsApiGetVideoListByLabelTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      String str1 = paramServerApiTaskInfo.getHttpRequestUrl();
      StarActivity localStarActivity = StarActivity.this;
      if (str1.length() > 1);
      for (String str2 = paramServerApiTaskInfo.getHttpRequestUrl().split("\\?")[1]; ; str2 = "")
      {
        StarActivity.access$1402(localStarActivity, str2);
        if (StarActivity.this.isReportFuncLoad)
        {
          long l = System.currentTimeMillis() - StarActivity.this.searchStartTime;
          StarActivity.this.reportFuncLoad(6, null, StarActivity.this.searchKey, l, false, 0, StarActivity.this.EPGParameter);
          StarActivity.access$1502(StarActivity.this, false);
        }
        if (StarActivity.this.taskControl.checkTaskValid(paramServerApiTaskInfo.getTaskId(), StarActivity.this.markedLabel))
          break;
        Logger.i(StarActivity.TAG, "ISccmsApiSearchActorStarListTaskListener.onError() invalid task");
        return;
      }
      StarActivity.this.dismissLoaddingDialog();
      if (StarActivity.this.isDownloadingMore)
        StarActivity.access$1210(StarActivity.this);
      while (true)
      {
        StarActivity.access$802(StarActivity.this, false);
        StarActivity.this.showErrorDialogAndReport(11, "1002008", "ISccmsApiSearchActorStarListTaskListener.onError", paramServerApiTaskInfo, paramServerApiCommonError);
        return;
        StarActivity.this.failedProcess();
      }
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, SearchActorStarList paramSearchActorStarList)
    {
      String str1 = paramServerApiTaskInfo.getHttpRequestUrl();
      StarActivity localStarActivity = StarActivity.this;
      if (str1.length() > 1);
      for (String str2 = paramServerApiTaskInfo.getHttpRequestUrl().split("\\?")[1]; ; str2 = "")
      {
        StarActivity.access$1402(localStarActivity, str2);
        Logger.i(StarActivity.TAG, "ISccmsApiSearchActorStarListTaskListener onSuccess(), result:" + paramSearchActorStarList.toString());
        if (StarActivity.this.taskControl.checkTaskValid(paramServerApiTaskInfo.getTaskId(), StarActivity.this.markedLabel))
          break;
        Logger.i(StarActivity.TAG, "ISccmsApiSearchActorStarListTaskListener.onError() invalid task");
        if (StarActivity.this.isReportFuncLoad)
        {
          long l2 = System.currentTimeMillis() - StarActivity.this.searchStartTime;
          StarActivity.this.reportFuncLoad(6, null, StarActivity.this.searchKey, l2, false, paramSearchActorStarList.total_rows, StarActivity.this.EPGParameter);
          StarActivity.access$1502(StarActivity.this, false);
        }
        return;
      }
      StarActivity.this.dismissLoaddingDialog();
      if (StarActivity.this.isReportFuncLoad)
      {
        long l1 = System.currentTimeMillis() - StarActivity.this.searchStartTime;
        StarActivity.this.reportFuncLoad(6, null, StarActivity.this.searchKey, l1, true, paramSearchActorStarList.total_rows, StarActivity.this.EPGParameter);
        StarActivity.access$1502(StarActivity.this, false);
      }
      if ((paramSearchActorStarList != null) && (paramSearchActorStarList.lists != null) && (paramSearchActorStarList.lists.size() <= 0))
        paramSearchActorStarList.total_rows = 0;
      StarActivity.this.onGetVideoList(paramSearchActorStarList);
      StarActivity.access$802(StarActivity.this, false);
    }
  }

  private static enum StarType
  {
    static
    {
      FILTER = new StarType("FILTER", 1);
      HOTSTAR = new StarType("HOTSTAR", 2);
      StarType[] arrayOfStarType = new StarType[3];
      arrayOfStarType[0] = SEARCH;
      arrayOfStarType[1] = FILTER;
      arrayOfStarType[2] = HOTSTAR;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.StarActivity
 * JD-Core Version:    0.6.2
 */