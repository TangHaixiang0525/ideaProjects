package com.starcor.hunan;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.starcor.core.ai.AIDataCacheTask;
import com.starcor.core.domain.ActorStarInfoList;
import com.starcor.core.domain.ActorStarInfoList.ActorStarInfo;
import com.starcor.core.domain.MovieData;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.SearchListItem;
import com.starcor.core.domain.SearchStruct;
import com.starcor.core.domain.StarGuestLabelList;
import com.starcor.core.domain.StarGuestLabelList.StarGuest;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.hunan.uilogic.CommonUiTools;
import com.starcor.hunan.widget.FilmListView;
import com.starcor.hunan.widget.FilmListView.ListViewDrawingPhase;
import com.starcor.hunan.widget.FilmListView.OnClickListener;
import com.starcor.hunan.widget.FilmListView.OnDrawItemListener;
import com.starcor.hunan.widget.FilmListView.OnScrollingListener;
import com.starcor.hunan.widget.FilmListView.OnSelectionChangedListener;
import com.starcor.media.player.ApiTaskControl;
import com.starcor.sccms.api.SccmsApiGetActorStarInfoTask.ISccmsApiGetActorStarInfoTaskListener;
import com.starcor.sccms.api.SccmsApiGetStarGuestListByLabelTask.ISccmsApiGetStarGusetListByLabelTaskListener;
import com.starcor.sccms.api.SccmsApiSearchVideoWorkSetByPinyinTask.ISccmsApiSearchVideoWorkSetByPinyinTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulPendingInputStream;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulUtils;
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

public class StarDetailedActivity extends BaseActivity
{
  private static final int MAX_TEXT_NUM = 24;
  public static final int MESSAGE_HIDE_PAGE_AND_SCROLL_BAR = 1;
  private static int PAGE_BIG_TEXT_SIZE = 0;
  private static int PAGE_SMALL_TEXT_SIZE = 0;
  private static final int POSTER_COLUMN_SIZE = 4;
  private static final int POSTER_DEFAULT_BG_MASK = 1593835520;
  private static final int POSTER_FOCUS_BG_COLOR = -2147483648;
  private static final int POSTER_SELECTER_SELECTED_BG_MASK = -872415232;
  private static final int STAR_ENTRY_DETAIL = 1;
  private static final int STAR_ENTRY_LIST = 2;
  private static final int STAR_ENTRY_SEARCH_RESULT = 4;
  private static final int STAR_ENTRY_STAR_GALLERY = 3;
  public static final String TAG = StarDetailedActivity.class.getSimpleName();
  public static final int TIMEOUT_HIDE_PAGE_AND_SCROLL_BAR = 1500;
  private String _searchRangePackgeId = "";
  private String actor;
  private String actorID;
  XulPendingInputStream actorInfoStream = new XulPendingInputStream();
  private int currentDownLoadPage = 0;
  private StarType currentType = StarType.WORKS;
  private int downloadPageSize = 0;
  private int filmItemCount;
  private int filmPageCount;
  private boolean isDownloadingMore = false;
  private boolean isLoadSuccess = false;
  private String labelID;
  private String labelID_V2;
  private Paint mDateTextBgPaint = null;
  private TextPaint mDateTextPaint = null;
  private TextPaint mDescPaint = null;
  private DrawItemCallback mDrawItemCallback;
  private int mLabelIdSearchType = 0;
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
      while (StarDetailedActivity.this.pageText == null);
      StarDetailedActivity.this.pageText.setVisibility(4);
    }
  };
  private Paint mSelectedFramBgPaint = null;
  private int onePageSize = 8;
  private XulView pageDetailsInfo;
  private String pageInfoFormat = "%s页    ";
  private int pageLine = 2;
  private int pageLines;
  private TextView pageText;
  private int posterHeight = 0;
  private int posterWidth = 0;
  private String resultInfoFormat = "共%s个结果";
  private String starGuests = "starGuests";
  private FilmListView starListView;
  private String starWorks = "starWorks";
  private ApiTaskControl taskControl;
  private XulView xulStarListView;

  private void addTaskToGetStarList()
  {
    if (this.currentType == StarType.WORKS)
    {
      SccmsApiSearchVideoWorkSetByPinyinTaskListener localSccmsApiSearchVideoWorkSetByPinyinTaskListener = new SccmsApiSearchVideoWorkSetByPinyinTaskListener(null);
      int i = ServerApiManager.i().APISearchStarWorkSetTask(this._searchRangePackgeId, this.currentDownLoadPage, this.downloadPageSize, "", this.labelID_V2, this.mLabelIdSearchType, localSccmsApiSearchVideoWorkSetByPinyinTaskListener);
      this.taskControl.clear();
      this.taskControl.addTaskLabel(i, "api", this.starWorks);
      return;
    }
    SccmsApiGetStarGuestListByLabelTaskListener localSccmsApiGetStarGuestListByLabelTaskListener = new SccmsApiGetStarGuestListByLabelTaskListener(null);
    int j = ServerApiManager.i().APIGetStarGuestListByLabelTask("", "", this._searchRangePackgeId, this.currentDownLoadPage, this.downloadPageSize, this.labelID, localSccmsApiGetStarGuestListByLabelTaskListener);
    this.taskControl.clear();
    this.taskControl.addTaskLabel(j, "api", this.starGuests);
  }

  private boolean existStarData(ActorStarInfoList.ActorStarInfo paramActorStarInfo)
  {
    return (!judgeEmpty(paramActorStarInfo.nation)) || (!judgeEmpty(paramActorStarInfo.constellation)) || (!judgeEmpty(paramActorStarInfo.blood_type)) || (!judgeEmpty(paramActorStarInfo.height)) || (!judgeEmpty(paramActorStarInfo.weight)) || (!judgeEmpty(paramActorStarInfo.area)) || (!judgeEmpty(paramActorStarInfo.birthday)) || (!judgeEmpty(paramActorStarInfo.works));
  }

  private String getDateText(String paramString)
  {
    String str2;
    if (TextUtils.isEmpty(paramString))
    {
      Logger.i(TAG, "getDateText TextUtils.isEmpty(date) = true");
      str2 = "";
    }
    String str1;
    String[] arrayOfString;
    do
    {
      return str2;
      str1 = paramString.split(" ")[0];
      arrayOfString = str1.split("-");
      if (arrayOfString == null)
        return "";
      str2 = "";
    }
    while (10 != str1.length());
    arrayOfString[0];
    String str3 = arrayOfString[1];
    String str4 = arrayOfString[2];
    String str5 = str3.substring(0, 1);
    String str6 = str3.substring(1, 2);
    if ("0".equals(str5))
      str2 = str2 + str6;
    while (true)
    {
      return str2 + "月" + str4;
      if ("1".equals(str5))
        str2 = str2 + str5 + str6;
    }
  }

  private ByteArrayInputStream getStarInfo(ActorStarInfoList.ActorStarInfo paramActorStarInfo)
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "info");
      writeNodeValue(localXmlSerializer, "image", paramActorStarInfo.img_v);
      writeNodeValue(localXmlSerializer, "nation", paramActorStarInfo.nation);
      writeNodeValue(localXmlSerializer, "astrology", paramActorStarInfo.constellation);
      writeNodeValue(localXmlSerializer, "blood_type", paramActorStarInfo.blood_type);
      writeNodeValue(localXmlSerializer, "height", paramActorStarInfo.height);
      writeNodeValue(localXmlSerializer, "weight", paramActorStarInfo.weight);
      writeNodeValue(localXmlSerializer, "home", paramActorStarInfo.area);
      writeNodeValue(localXmlSerializer, "birthday", paramActorStarInfo.birthday);
      writeNodeValue(localXmlSerializer, "works", paramActorStarInfo.works);
      localXmlSerializer.endTag(null, "info");
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
      return localByteArrayInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private void hidePageAndScrollBar(int paramInt)
  {
    if (this.mPageHandler.hasMessages(1))
      this.mPageHandler.removeMessages(1);
    this.mPageHandler.sendEmptyMessageDelayed(1, paramInt);
  }

  private void initPaint()
  {
    if (this.mDescPaint == null)
    {
      this.mDescPaint = new TextPaint();
      this.mDescPaint.setAntiAlias(true);
      this.mDescPaint.setTextSize(App.Operation(18.0F));
      this.mDescPaint.setFakeBoldText(true);
      this.mDescPaint.setShadowLayer(2.5F, 0.5F, 0.5F, -16777216);
      this.mDescPaint.setColor(-1);
    }
    if (this.mDateTextPaint == null)
    {
      this.mDateTextPaint = new TextPaint();
      this.mDateTextPaint.setAntiAlias(true);
      this.mDateTextPaint.setColor(-1);
      this.mDateTextPaint.setFakeBoldText(true);
      this.mDateTextPaint.setTextSkewX(-0.25F);
      this.mDateTextPaint.setShadowLayer(3.5F, 0.5F, 0.5F, -16777216);
    }
    if (this.mSelectedFramBgPaint == null)
    {
      this.mSelectedFramBgPaint = new Paint();
      this.mSelectedFramBgPaint.setAntiAlias(true);
    }
    if (this.mDateTextBgPaint == null)
    {
      this.mDateTextBgPaint = new Paint();
      this.mDateTextBgPaint.setAntiAlias(true);
      this.mDateTextBgPaint.setColor(-2147483648);
    }
    if (this.mDrawItemCallback == null)
      this.mDrawItemCallback = new DrawItemCallback(null);
  }

  private boolean judgeEmpty(String paramString)
  {
    return ("null".equals(paramString)) || (TextUtils.isEmpty(paramString));
  }

  private void loadStarInfoList(StarType paramStarType)
  {
    this.currentType = paramStarType;
    showLoaddingDialog();
    this.starListView.removeAllItems();
    this.starListView.setViewOffset(8, 10);
    if (paramStarType == StarType.WORKS)
    {
      this.starListView.setFocusScalar(1.1F);
      this.pageLines = 2;
      this.mLabelIdSearchType = 1;
      this.posterWidth = App.Operation(145.0F);
      this.posterHeight = App.Operation(200.0F);
      this.starListView.setViewGrid(5, this.pageLines);
      this.onePageSize = (5 * this.pageLines);
      this.downloadPageSize = (4 * (5 * this.pageLines));
      this.starListView.setOnDrawItemListener(null);
      this.starListView.setItemGrid(App.Operation(180.0F), App.Operation(270.0F), App.Operation(5.0F), this.posterWidth, this.posterHeight);
      this.starListView.setItemPadding(App.Operation(13.0F), App.Operation(5.0F), App.Operation(13.0F), App.Operation(10.0F));
      SccmsApiSearchVideoWorkSetByPinyinTaskListener localSccmsApiSearchVideoWorkSetByPinyinTaskListener = new SccmsApiSearchVideoWorkSetByPinyinTaskListener(null);
      int j = ServerApiManager.i().APISearchStarWorkSetTask(this._searchRangePackgeId, this.currentDownLoadPage, this.downloadPageSize, "", this.labelID_V2, this.mLabelIdSearchType, localSccmsApiSearchVideoWorkSetByPinyinTaskListener);
      this.taskControl.clear();
      this.taskControl.addTaskLabel(j, "api", this.starWorks);
      return;
    }
    this.pageLines = 2;
    this.starListView.setFocusScalar(1.0F);
    this.posterWidth = App.Operation(193.0F);
    this.posterHeight = App.Operation(147.0F);
    this.starListView.setViewGrid(4, this.pageLines);
    this.onePageSize = (4 * this.pageLines);
    this.downloadPageSize = (4 * (4 * this.pageLines));
    this.starListView.setOnDrawItemListener(this.mDrawItemCallback);
    this.starListView.setItemGrid(App.Operation(225.0F), App.Operation(218.0F), App.Operation(8.0F), this.posterWidth, this.posterHeight);
    this.starListView.setItemPadding(App.Operation(19.0F), App.Operation(8.0F), App.Operation(19.0F), App.Operation(8.0F));
    SccmsApiGetStarGuestListByLabelTaskListener localSccmsApiGetStarGuestListByLabelTaskListener = new SccmsApiGetStarGuestListByLabelTaskListener(null);
    int i = ServerApiManager.i().APIGetStarGuestListByLabelTask("", "", this._searchRangePackgeId, this.currentDownLoadPage, this.downloadPageSize, this.labelID, localSccmsApiGetStarGuestListByLabelTaskListener);
    this.taskControl.clear();
    this.taskControl.addTaskLabel(i, "api", this.starGuests);
  }

  private void onGetVideoList(SearchStruct paramSearchStruct)
  {
    if (paramSearchStruct == null)
      return;
    this.filmItemCount = paramSearchStruct.item_num;
    this.filmPageCount = ((int)Math.ceil(1.0F * this.filmItemCount / this.onePageSize));
    Iterator localIterator = paramSearchStruct.sItemList.iterator();
    while (localIterator.hasNext())
    {
      SearchListItem localSearchListItem = (SearchListItem)localIterator.next();
      String str2 = CommonUiTools.processCompressImageUrl(localSearchListItem.img_v, localSearchListItem.img_h, this.posterWidth, this.posterHeight);
      int i = this.starListView.addItem(localSearchListItem.name, str2, localSearchListItem);
      if (!TextUtils.isEmpty(localSearchListItem.corner_mark_img_url))
        this.starListView.setItemCoverUrl(i, localSearchListItem.corner_mark_img_url, App.Operation(-4.0F), App.Operation(-2.0F));
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
        break label213;
    }
    label213: for (boolean bool = true; ; bool = false)
    {
      showEmptyTips(bool);
      return;
      str1 = "block";
      break;
    }
  }

  private void onGetVideoList(StarGuestLabelList paramStarGuestLabelList)
  {
    if (paramStarGuestLabelList == null)
      return;
    this.filmItemCount = paramStarGuestLabelList.total_rows;
    this.filmPageCount = ((int)Math.ceil(1.0F * this.filmItemCount / this.onePageSize));
    Iterator localIterator = paramStarGuestLabelList.lists.iterator();
    while (localIterator.hasNext())
    {
      StarGuestLabelList.StarGuest localStarGuest = (StarGuestLabelList.StarGuest)localIterator.next();
      String str2 = CommonUiTools.processCompressImageUrl(localStarGuest.img_v, localStarGuest.img_h, this.posterWidth, this.posterHeight);
      this.starListView.addItem(localStarGuest.name, str2, localStarGuest);
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

  private void setFilmCountInfo()
  {
    String str2;
    String str5;
    SpannableStringBuilder localSpannableStringBuilder;
    if ((this.filmPageCount != 0) && (this.pageLines != 0))
    {
      String str6 = this.pageInfoFormat;
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = (1 + this.starListView.getCursorLine() / this.pageLine + "/" + this.filmPageCount);
      str2 = String.format(str6, arrayOfObject3);
      if (this.pageText != null)
      {
        str5 = 1 + this.starListView.getCursorLine() / this.pageLines + "";
        localSpannableStringBuilder = new SpannableStringBuilder(str2);
        if ((str5.length() >= str2.length()) || (localSpannableStringBuilder == null))
          break label368;
        localSpannableStringBuilder.setSpan(new AbsoluteSizeSpan(App.Operation(PAGE_BIG_TEXT_SIZE)), 0, str5.length(), 33);
        localSpannableStringBuilder.setSpan(new ForegroundColorSpan(-436207617), 0, str5.length(), 33);
        localSpannableStringBuilder.setSpan(new AbsoluteSizeSpan(App.Operation(PAGE_SMALL_TEXT_SIZE)), str5.length(), str2.length(), 33);
        localSpannableStringBuilder.setSpan(new ForegroundColorSpan(-1291845633), str5.length(), str2.length(), 33);
      }
    }
    String str4;
    while (true)
    {
      this.pageText.setTypeface(Typeface.DEFAULT);
      this.pageText.setBackgroundResource(2130837609);
      this.pageText.setText(localSpannableStringBuilder);
      String str3 = this.resultInfoFormat;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = String.valueOf(this.filmItemCount);
      str4 = String.format(str3, arrayOfObject2);
      if (this.pageDetailsInfo == null)
        this.pageDetailsInfo = xulFindViewById("page_details_info");
      if (this.pageDetailsInfo != null)
        break label420;
      return;
      String str1 = this.resultInfoFormat;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = String.valueOf(this.filmItemCount);
      str2 = String.format(str1, arrayOfObject1);
      break;
      label368: Logger.e(TAG, "str_currentPage.length():" + str5.length() + ">= infoText.length():" + str2.length() + "!");
    }
    label420: this.pageDetailsInfo.setAttr("text", str4);
    this.pageDetailsInfo.resetRender();
  }

  private void showEmptyTips(boolean paramBoolean)
  {
    XulView localXulView1 = xulFindViewById("film_list_view");
    boolean bool;
    String str2;
    label34: XulView localXulView2;
    if (localXulView1 != null)
    {
      if (!paramBoolean)
      {
        bool = true;
        localXulView1.setEnabled(bool);
        if (paramBoolean)
          break label98;
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
        localXulView2.setAttr("text", "对不起,  该选项暂无内容!");
        if (!paramBoolean)
          break label106;
      }
    }
    label98: label106: for (String str1 = "block"; ; str1 = "none")
    {
      localXulView2.setStyle("display", str1);
      localXulView2.resetRender();
      return;
      bool = false;
      break;
      str2 = "none";
      break label34;
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

  private void showStarDetailEmptyTips(boolean paramBoolean)
  {
    XulView localXulView1 = xulFindViewById("star_detail_info");
    boolean bool;
    String str2;
    label34: XulView localXulView2;
    if (localXulView1 != null)
    {
      if (!paramBoolean)
      {
        bool = true;
        localXulView1.setEnabled(bool);
        if (paramBoolean)
          break label88;
        str2 = "block";
        localXulView1.setStyle("display", str2);
        localXulView1.resetRender();
      }
    }
    else
    {
      localXulView2 = xulFindViewById("star_detail_empty_tip");
      if (localXulView2 != null)
        if (!paramBoolean)
          break label96;
    }
    label88: label96: for (String str1 = "block"; ; str1 = "none")
    {
      localXulView2.setStyle("display", str1);
      localXulView2.resetRender();
      return;
      bool = false;
      break;
      str2 = "none";
      break label34;
    }
  }

  private boolean switchRangeBtnByCommand(String paramString, Bundle paramBundle)
  {
    if ("m_open_asset_search_range_page".equals(paramString))
    {
      this._searchRangePackgeId = paramBundle.getString("search_range");
      this.currentDownLoadPage = 0;
      loadStarInfoList(StarType.WORKS);
    }
    if ("m_open_index_search_range_page".equals(paramString))
    {
      this._searchRangePackgeId = paramBundle.getString("search_range");
      this.currentDownLoadPage = 0;
      loadStarInfoList(StarType.GUEST);
    }
    return true;
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    showLoaddingDialog();
    initXul("StarDetailedPage", true);
    this.actor = getIntent().getStringExtra("actor");
    this.actorID = getIntent().getStringExtra("actorID");
    this.labelID = getIntent().getStringExtra("labelID");
    this.labelID_V2 = getIntent().getStringExtra("labelID_V2");
    Logger.i(TAG, "labelID_V2=" + this.labelID_V2 + ",labelID =" + this.labelID);
    xulUpdateTitle(this.actor, "");
    this.taskControl = new ApiTaskControl();
    ServerApiManager.i().APIGetActorStarInfoTask(this.actorID, "", new SccmsApiGetActorStarInfoTask.ISccmsApiGetActorStarInfoTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        StarDetailedActivity.this.showStarDetailEmptyTips(true);
        StarDetailedActivity.access$502(StarDetailedActivity.this, false);
        StarDetailedActivity localStarDetailedActivity = StarDetailedActivity.this;
        boolean bool = StarDetailedActivity.this.isLoadSuccess;
        String[] arrayOfString = new String[1];
        arrayOfString[0] = StarDetailedActivity.this.actorID;
        localStarDetailedActivity.reportLoad(bool, arrayOfString);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ActorStarInfoList paramAnonymousActorStarInfoList)
      {
        boolean bool2;
        if ((paramAnonymousActorStarInfoList != null) && (paramAnonymousActorStarInfoList.lists != null) && (paramAnonymousActorStarInfoList.lists.size() > 0))
        {
          StarDetailedActivity.this.actorInfoStream.setBaseStream(StarDetailedActivity.this.getStarInfo((ActorStarInfoList.ActorStarInfo)paramAnonymousActorStarInfoList.lists.get(0)));
          StarDetailedActivity localStarDetailedActivity2 = StarDetailedActivity.this;
          if (!StarDetailedActivity.this.existStarData((ActorStarInfoList.ActorStarInfo)paramAnonymousActorStarInfoList.lists.get(0)))
          {
            bool2 = true;
            localStarDetailedActivity2.showStarDetailEmptyTips(bool2);
            if (TextUtils.isEmpty(StarDetailedActivity.this.labelID))
            {
              StarDetailedActivity.access$902(StarDetailedActivity.this, ((ActorStarInfoList.ActorStarInfo)paramAnonymousActorStarInfoList.lists.get(0)).label_id);
              Logger.i(StarDetailedActivity.TAG, "labelID =" + StarDetailedActivity.this.labelID);
            }
          }
        }
        while (true)
        {
          StarDetailedActivity.access$502(StarDetailedActivity.this, true);
          StarDetailedActivity localStarDetailedActivity1 = StarDetailedActivity.this;
          boolean bool1 = StarDetailedActivity.this.isLoadSuccess;
          String[] arrayOfString = new String[1];
          arrayOfString[0] = StarDetailedActivity.this.actorID;
          localStarDetailedActivity1.reportLoad(bool1, arrayOfString);
          return;
          bool2 = false;
          break;
          StarDetailedActivity.this.showStarDetailEmptyTips(true);
        }
      }
    });
  }

  protected void onRestart()
  {
    super.onRestart();
    boolean bool = this.isLoadSuccess;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.actorID;
    reportLoad(bool, arrayOfString);
  }

  protected void onResume()
  {
    super.onResume();
  }

  protected void onStop()
  {
    super.onStop();
    boolean bool = this.isLoadSuccess;
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.actorID;
    reportExit(bool, arrayOfString);
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    Bundle localBundle;
    if ((paramObject instanceof XulDataNode))
    {
      localBundle = xulArgListToBundle((XulDataNode)paramObject);
      switchRangeBtnByCommand(paramString3, localBundle);
      if (!"m_open_asset_search_range_page".equals(paramString3))
        break label72;
      paramXulView.setAttr("page_area", "star_detail_works");
    }
    while (true)
    {
      return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
      localBundle = new Bundle();
      break;
      label72: if ("m_open_index_search_range_page".equals(paramString3))
        paramXulView.setAttr("page_area", "star_detail_guest");
    }
  }

  protected InputStream xulGetAppData(XulWorker.DownloadItem paramDownloadItem, String paramString)
  {
    if ("api/get_actor_info".equals(paramString))
      return this.actorInfoStream;
    return super.xulGetAppData(paramDownloadItem, paramString);
  }

  protected void xulOnRenderIsReady()
  {
    this.xulStarListView = xulFindViewById("film_list_view");
    initPaint();
    this.starListView = ((FilmListView)this.xulStarListView.getExternalView());
    this.starListView.set_xScalar(getXulRenderContext().getXScalar());
    this.starListView.set_yScalar(getXulRenderContext().getYScalar());
    this.starListView.setFocusable(true);
    this.starListView.setCursorAlwaysVisible(false);
    this.starListView.setDrawPlaceHolder(false);
    if (XulRenderContext.getDefTypeFace() != null)
      this.starListView.setTextTypeFace(XulRenderContext.getDefTypeFace());
    this.starListView.setOnSelectionChangedListener(new OnSelectionChangedLsnrOfmovieListView(null));
    this.starListView.setOnItemClickListener(new OnClickLsnrOfMovieListView(null));
    this.starListView.setOnScrollingListener(new FilmListView.OnScrollingListener()
    {
      public void onScrolling(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        if (StarDetailedActivity.this.pageText != null)
          StarDetailedActivity.this.pageText.setVisibility(0);
        StarDetailedActivity.this.hidePageAndScrollBar(1500);
      }
    });
    this.pageInfoFormat = this.xulStarListView.getDataString("page_info_format");
    this.resultInfoFormat = this.xulStarListView.getDataString("result_info_format");
    xulFireEvent("starEvents:label_click");
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

  private class DrawItemCallback
    implements FilmListView.OnDrawItemListener
  {
    float[] _textWidthBuf = new float[32];
    Paint borderPaint = new Paint(1);
    float roundRadius = App.Operation(5.0F);
    float selectBorderSize = App.Operation(4.0F);
    RectF tmpRcF = new RectF();

    private DrawItemCallback()
    {
      this.borderPaint.setStyle(Paint.Style.STROKE);
    }

    public void onDrawItem(int paramInt, Object paramObject, Canvas paramCanvas, Rect paramRect1, Rect paramRect2, FilmListView.ListViewDrawingPhase paramListViewDrawingPhase)
    {
      if (FilmListView.ListViewDrawingPhase.ITEM_POST_DRAW_PHASE.equals(paramListViewDrawingPhase))
      {
        StarGuestLabelList.StarGuest localStarGuest = (StarGuestLabelList.StarGuest)paramObject;
        String str1 = localStarGuest.info;
        String str2 = localStarGuest.release_time;
        String str3 = StarDetailedActivity.this.getDateText(str2);
        int i = StarDetailedActivity.this.starListView.getSelectedItem();
        int j = 8;
        boolean bool1 = TextUtils.isEmpty(str1);
        int k = 0;
        int m = 0;
        if (!bool1)
        {
          k = str1.length() / 8;
          m = str1.length() % 8;
        }
        if (m > 0)
          k++;
        if (k > 3)
          k = 3;
        if (paramRect2 != null)
        {
          int n = paramRect2.left + App.Operation(12.0F);
          int i1 = paramRect2.bottom - App.Operation(10.0F);
          int i2 = paramRect2.bottom - App.Operation(36.0F);
          if ((i == paramInt) && (StarDetailedActivity.this.starListView.isFocused()))
          {
            n = paramRect2.right - App.Operation(120.0F);
            StarDetailedActivity.this.mDateTextBgPaint.setColor(-872415232);
            this.tmpRcF.set(paramRect2);
            paramCanvas.drawRoundRect(this.tmpRcF, this.roundRadius, this.roundRadius, StarDetailedActivity.this.mDateTextBgPaint);
            this.borderPaint.setColor(-15362577);
            this.borderPaint.setStrokeWidth(this.selectBorderSize);
            paramCanvas.drawRoundRect(this.tmpRcF, this.roundRadius, this.roundRadius, this.borderPaint);
            boolean bool2 = TextUtils.isEmpty(str1);
            int i5 = 0;
            if (!bool2)
              i5 = -1 + str1.length();
            int i6 = 0;
            int i7 = i5;
            int i8 = 0;
            if (i7 >= 24)
            {
              i5 = 22;
              i8 = 1;
            }
            int i9 = 0;
            int i10 = 1;
            int i11 = App.Operation(20.0F);
            while ((i10 <= k) && (i6 <= i5))
            {
              i9 = 0;
              while ((i6 <= i5) && (i6 < j))
              {
                int i15 = i6 + 1;
                paramCanvas.drawText(str1.substring(i6, i15), i11 + paramRect2.left + i9 * i11, paramRect2.top + App.Operation(40.0F) + (i10 - 1) * App.Operation(24.0F), StarDetailedActivity.this.mDescPaint);
                i9++;
                i6++;
              }
              j += 8;
              i10++;
            }
            if (i8 != 0)
              paramCanvas.drawText("...", i11 + paramRect2.left + i9 * App.Operation(21.0F) - App.Operation(3.0F), paramRect2.top + App.Operation(40.0F) + (i10 - 2) * App.Operation(24.0F), StarDetailedActivity.this.mDescPaint);
            if (!TextUtils.isEmpty(str3))
            {
              float f2 = 0.0F;
              int i12 = str3.indexOf('月');
              StarDetailedActivity.this.mDateTextPaint.setTextSize(App.Operation(48.0F));
              StarDetailedActivity.this.mDateTextPaint.getTextWidths(str3, 0, i12, this._textWidthBuf);
              if (i12 == 1);
              for (int i13 = paramRect2.left + App.Operation(130.0F) - App.Operation(12.0F); ; i13 = (int)(paramRect2.left + App.Operation(130.0F) - this._textWidthBuf[0] - App.Operation(12.0F)))
              {
                paramCanvas.drawText(str3, 0, i12, i13, i1, StarDetailedActivity.this.mDateTextPaint);
                for (int i14 = 0; i14 < i12; i14++)
                  f2 += this._textWidthBuf[i14];
              }
              StarDetailedActivity.this.mDateTextPaint.setTextSize(App.Operation(16.0F));
              paramCanvas.drawText(str3, i12, str3.length(), f2 + i13, i1 - App.Operation(4.0F), StarDetailedActivity.this.mDateTextPaint);
            }
          }
          while ((!TextUtils.isEmpty(str3)) && (i != paramInt))
          {
            float f1 = 0.0F;
            int i3 = str3.indexOf('月');
            StarDetailedActivity.this.mDateTextPaint.setTextSize(App.Operation(48.0F));
            StarDetailedActivity.this.mDateTextPaint.getTextWidths(str3, 0, i3, this._textWidthBuf);
            paramCanvas.drawText(str3, 0, i3, n, i1, StarDetailedActivity.this.mDateTextPaint);
            int i4 = 0;
            while (true)
              if (i4 < i3)
              {
                f1 += this._textWidthBuf[i4];
                i4++;
                continue;
                this.tmpRcF.set(paramRect2);
                StarDetailedActivity.this.mDateTextBgPaint.setColor(-2147483648);
                paramCanvas.save();
                paramCanvas.clipRect(paramRect2.left, i2, paramRect2.right, paramRect2.bottom);
                paramCanvas.drawRoundRect(this.tmpRcF, this.roundRadius, this.roundRadius, StarDetailedActivity.this.mDateTextBgPaint);
                paramCanvas.restore();
                break;
              }
            StarDetailedActivity.this.mDateTextPaint.setTextSize(App.Operation(16.0F));
            paramCanvas.drawText(str3, i3, str3.length(), f1 + n, i1 - App.Operation(4.0F), StarDetailedActivity.this.mDateTextPaint);
          }
        }
      }
    }
  }

  private final class OnClickLsnrOfMovieListView
    implements FilmListView.OnClickListener
  {
    private OnClickLsnrOfMovieListView()
    {
    }

    public void onClick(int paramInt, Object paramObject)
    {
      if (StarDetailedActivity.this.currentType == StarDetailedActivity.StarType.WORKS)
      {
        StarDetailedActivity.this.isStarDetailWorks = true;
        SearchListItem localSearchListItem = (SearchListItem)paramObject;
        if (localSearchListItem != null)
        {
          MovieData localMovieData = new MovieData();
          localMovieData.packageId = localSearchListItem.package_id;
          localMovieData.categoryId = localSearchListItem.category_id;
          localMovieData.videoId = localSearchListItem.video_id;
          localMovieData.videoType = XulUtils.tryParseInt(localSearchListItem.video_id);
          localMovieData.name = localSearchListItem.name;
          StarDetailedActivity.this.startDetailPageActivity(localMovieData);
        }
      }
      StarGuestLabelList.StarGuest localStarGuest;
      do
      {
        return;
        StarDetailedActivity.this.isStarDetailGust = true;
        localStarGuest = (StarGuestLabelList.StarGuest)paramObject;
      }
      while (localStarGuest == null);
      Intent localIntent = new Intent();
      localIntent.setClass(StarDetailedActivity.this, ActivityAdapter.getInstance().getMPlayer());
      PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
      localPlayerIntentParams.subfix_title = localStarGuest.name;
      VideoInfo localVideoInfo = new VideoInfo();
      localVideoInfo.videoId = localStarGuest.video_id;
      try
      {
        localVideoInfo.videoType = Integer.parseInt(localStarGuest.video_type);
        localPlayerIntentParams.nns_videoInfo = localVideoInfo;
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        try
        {
          localPlayerIntentParams.nns_index = Integer.parseInt(localStarGuest.video_index);
          localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
          localIntent.addFlags(8388608);
          StarDetailedActivity.this.startActivity(localIntent);
          return;
          localNumberFormatException1 = localNumberFormatException1;
          localVideoInfo.videoType = 0;
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          while (true)
            localPlayerIntentParams.nns_index = 0;
        }
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
      StarDetailedActivity.this.setFilmCountInfo();
      Logger.i(StarDetailedActivity.TAG, "onSelectionChanged position:" + paramInt);
      SearchListItem localSearchListItem;
      JSONObject localJSONObject2;
      if (StarDetailedActivity.this.currentType == StarDetailedActivity.StarType.WORKS)
      {
        localSearchListItem = (SearchListItem)paramObject;
        if (localSearchListItem != null)
          localJSONObject2 = new JSONObject();
      }
      while (true)
      {
        try
        {
          localJSONObject2.put("video_id", localSearchListItem.video_id);
          localJSONObject2.put("video_type", XulUtils.tryParseInt(localSearchListItem.video_id));
          localJSONObject2.put("media_asset_id", localSearchListItem.package_id);
          localJSONObject2.put("category_id", localSearchListItem.category_id);
          AIDataCacheTask.i().filterJavaAction("ai_open_video_detail", "ai_focus", localJSONObject2.toString(), "");
          if ((!StarDetailedActivity.this.isDownloadingMore) && (paramInt + StarDetailedActivity.this.downloadPageSize >= StarDetailedActivity.this.starListView.getItemCount()) && (StarDetailedActivity.this.starListView.getItemCount() < StarDetailedActivity.this.filmItemCount))
          {
            StarDetailedActivity.access$1502(StarDetailedActivity.this, true);
            StarDetailedActivity.access$1608(StarDetailedActivity.this);
            StarDetailedActivity.this.addTaskToGetStarList();
          }
          return;
        }
        catch (JSONException localJSONException)
        {
          localJSONException.printStackTrace();
          continue;
        }
        if ((StarGuestLabelList.StarGuest)paramObject != null)
        {
          JSONObject localJSONObject1 = new JSONObject();
          AIDataCacheTask.i().filterJavaAction("ai_null", "ai_focus", localJSONObject1.toString(), "");
        }
      }
    }
  }

  private final class SccmsApiGetStarGuestListByLabelTaskListener
    implements SccmsApiGetStarGuestListByLabelTask.ISccmsApiGetStarGusetListByLabelTaskListener
  {
    private SccmsApiGetStarGuestListByLabelTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      if (!StarDetailedActivity.this.taskControl.checkTaskValid(paramServerApiTaskInfo.getTaskId(), StarDetailedActivity.this.starGuests))
      {
        Logger.i(StarDetailedActivity.TAG, "ISccmsApiGetStarGusetListByLabelTaskListener.onError() invalid task");
        return;
      }
      StarDetailedActivity.this.dismissLoaddingDialog();
      if (StarDetailedActivity.this.isDownloadingMore)
        StarDetailedActivity.access$1610(StarDetailedActivity.this);
      while (true)
      {
        StarDetailedActivity.access$1502(StarDetailedActivity.this, false);
        StarDetailedActivity.this.showErrorDialogAndReport(11, "1002008", "ISccmsApiGetStarGusetListByLabelTaskListener.onError", paramServerApiTaskInfo, paramServerApiCommonError);
        return;
        StarDetailedActivity.access$1702(StarDetailedActivity.this, 0);
        StarDetailedActivity.access$1802(StarDetailedActivity.this, 0);
        StarDetailedActivity.this.setFilmCountInfo();
        StarDetailedActivity.this.showEmptyTips(true);
        if (StarDetailedActivity.this.xulStarListView != null)
        {
          StarDetailedActivity.this.xulStarListView.setStyle("display", "none");
          StarDetailedActivity.this.xulStarListView.resetRender();
        }
      }
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, StarGuestLabelList paramStarGuestLabelList)
    {
      if (!StarDetailedActivity.this.taskControl.checkTaskValid(paramServerApiTaskInfo.getTaskId(), StarDetailedActivity.this.starGuests))
      {
        Logger.i(StarDetailedActivity.TAG, "ISccmsApiGetStarGusetListByLabelTaskListener.onError() invalid task");
        return;
      }
      StarDetailedActivity.this.dismissLoaddingDialog();
      Logger.i(StarDetailedActivity.TAG, "SccmsApiGetVideoListByLabelTaskListener onSuccess(), result:" + paramStarGuestLabelList.toString());
      if ((paramStarGuestLabelList != null) && (paramStarGuestLabelList.lists != null) && (paramStarGuestLabelList.lists.size() <= 0))
        paramStarGuestLabelList.total_rows = 0;
      StarDetailedActivity.this.onGetVideoList(paramStarGuestLabelList);
      StarDetailedActivity.access$1502(StarDetailedActivity.this, false);
    }
  }

  private final class SccmsApiSearchVideoWorkSetByPinyinTaskListener
    implements SccmsApiSearchVideoWorkSetByPinyinTask.ISccmsApiSearchVideoWorkSetByPinyinTaskListener
  {
    private SccmsApiSearchVideoWorkSetByPinyinTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      if (!StarDetailedActivity.this.taskControl.checkTaskValid(paramServerApiTaskInfo.getTaskId(), StarDetailedActivity.this.starWorks))
      {
        Logger.i(StarDetailedActivity.TAG, "ISccmsApiSearchVideoByPinyinTaskListener.onError() invalid task");
        return;
      }
      StarDetailedActivity.this.dismissLoaddingDialog();
      if (StarDetailedActivity.this.isDownloadingMore)
        StarDetailedActivity.access$1610(StarDetailedActivity.this);
      while (true)
      {
        StarDetailedActivity.access$1502(StarDetailedActivity.this, false);
        StarDetailedActivity.this.showErrorDialogAndReport(11, "1002008", "ISccmsApiSearchVideoByPinyinTaskListener.onError", paramServerApiTaskInfo, paramServerApiCommonError);
        return;
        StarDetailedActivity.access$1702(StarDetailedActivity.this, 0);
        StarDetailedActivity.access$1802(StarDetailedActivity.this, 0);
        StarDetailedActivity.this.setFilmCountInfo();
        StarDetailedActivity.this.showEmptyTips(true);
        if (StarDetailedActivity.this.xulStarListView != null)
        {
          StarDetailedActivity.this.xulStarListView.setStyle("display", "none");
          StarDetailedActivity.this.xulStarListView.resetRender();
        }
      }
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, SearchStruct paramSearchStruct)
    {
      if (!StarDetailedActivity.this.taskControl.checkTaskValid(paramServerApiTaskInfo.getTaskId(), StarDetailedActivity.this.starWorks))
      {
        Logger.i(StarDetailedActivity.TAG, "ISccmsApiSearchVideoByPinyinTaskListener.onError() invalid task");
        return;
      }
      StarDetailedActivity.this.dismissLoaddingDialog();
      Logger.i(StarDetailedActivity.TAG, "SccmsApiGetVideoListByLabelTaskListener onSuccess(), result:" + paramSearchStruct.toString());
      if ((paramSearchStruct != null) && (paramSearchStruct.sItemList != null) && (paramSearchStruct.sItemList.size() <= 0))
        paramSearchStruct.item_num = 0;
      StarDetailedActivity.this.onGetVideoList(paramSearchStruct);
      StarDetailedActivity.access$1502(StarDetailedActivity.this, false);
    }
  }

  private static enum StarType
  {
    static
    {
      GUEST = new StarType("GUEST", 1);
      StarType[] arrayOfStarType = new StarType[2];
      arrayOfStarType[0] = WORKS;
      arrayOfStarType[1] = GUEST;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.StarDetailedActivity
 * JD-Core Version:    0.6.2
 */