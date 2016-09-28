package com.starcor.hunan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import android.view.KeyEvent;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.core.ai.AIDataCacheTask;
import com.starcor.core.domain.AssetsInfo;
import com.starcor.core.domain.AuthState;
import com.starcor.core.domain.CollectListItem;
import com.starcor.core.domain.FilmItem;
import com.starcor.core.domain.FilmListItem;
import com.starcor.core.domain.FilmListPageInfo;
import com.starcor.core.domain.MediaInfo;
import com.starcor.core.domain.MovieData;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.PreviewList;
import com.starcor.core.domain.PreviewList.Item;
import com.starcor.core.domain.PurchaseParam;
import com.starcor.core.domain.StarInfo;
import com.starcor.core.domain.StarInfoCollect;
import com.starcor.core.domain.UserAuthV2;
import com.starcor.core.domain.UserKey;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.epgapi.params.GetUserAuthV2Params;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.logic.UserCPLLogic;
import com.starcor.core.utils.AppKiller;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.Tools;
import com.starcor.hunan.domain.ReportData;
import com.starcor.hunan.interfaces.SuccessCallBack;
import com.starcor.hunan.service.CollectAndPlayListLogic;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.hunan.uilogic.InitApiData;
import com.starcor.hunan.uilogic.InitApiData.onApiOKListener;
import com.starcor.hunan.widget.MovieCouponDialog;
import com.starcor.hunan.widget.MovieCouponDialog.MovieCouponOkDialogListener;
import com.starcor.hunan.widget.MovieCouponDialog.MovieCouponTipDialogListener;
import com.starcor.hunan.widget.TokenDialog;
import com.starcor.hunan.widget.TokenDialog.XiaoMiLoginListener;
import com.starcor.hunan.widget.XULDialog;
import com.starcor.hunan.xiaomi.ActivityUserCheckLogic;
import com.starcor.hunan.xiaomi.XiaoMiOAuthManager;
import com.starcor.hunan.xiaomi.XiaoMiOAuthManager.IXiaoMiLoginMgtv;
import com.starcor.report.Column.RecommendColumn;
import com.starcor.report.ReportInfo;
import com.starcor.report.ReportPageInfo;
import com.starcor.report.pay.PayReportHelper;
import com.starcor.report.pay.VodPayInfo;
import com.starcor.sccms.api.SccmsApiGetAssetsByVideoIdTask.ISccmsApiGetAssetsByVideoIdTaskListener;
import com.starcor.sccms.api.SccmsApiGetCatchVideoRecordV2Task.ISccmsApiGetCatchVideoRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetCollectRecordV2Task.ISccmsApiGetCollectRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetPreviewListTask.ISccmsApiGetPreviewListTaskListener;
import com.starcor.sccms.api.SccmsApiGetRelevantFilmsTask.ISccmsApiGetRelevantFilmsTaskListener;
import com.starcor.sccms.api.SccmsApiGetStarCollectDataTask.ISccmsApiGetStarCollectDataTaskListener;
import com.starcor.sccms.api.SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoIndexListTask.ISccmsApiGetVideoIndexListTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoInfoV3Task.ISccmsApiGetVideoInfoV3TaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.ui.UITools;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.Utils.XulMemoryOutputStream;
import com.starcor.xul.Wrapper.XulMassiveAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker;
import com.starcor.xul.XulWorker.DownloadItem;
import com.starcor.xul.XulWorker.XulDownloadOutputBuffer;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class DetailPageActivity extends XULActivity
{
  private static final String ACTOR = "演员";
  private static final String ADD_COLLECT_TYPE = "0";
  private static final String ADD_TRACE_TYPE = "2";
  private static final String ARTIST = "艺人";
  private static final String DELET_COLLECT_TYPE = "1";
  private static final String DELET_TRACE_TYPE = "3";
  private static final String DIRECTOR = "导演";
  public static final String FOCUS_COLOR = "ffffffff";
  private static final String HOST = "主持人";
  private static final int MIN_AUDIENCE_NUMBER = 10000;
  public static final String NORMAL_COLOR = "7fffffff";
  public static final String NUMBER_EPISODE_UI_STYLE = "0";
  private static final String PAGE_DETAIL = "m_open_detail_page_detail";
  private static final String PAGE_INDEX = "m_open_detail_page_index";
  private static final String PAGE_PREVUE = "m_open_detail_page_prevue";
  private static final String PAGE_RECOM = "m_open_detail_page_recom";
  private static final String PAGE_STAR = "m_open_detail_page_star";
  private static final String TAG = "DetailPageActivity";
  public static final String VISITED_COLOR = "ff5e6281";
  public static final String WATCH_FOCUS_DATE_UI_STYLE = "m1";
  public static final String WATCH_FOCUS_EPISODE_UI_STYLE = "m3";
  public static final String WATCH_FOCUS_UI_STYLE = "m2";
  private static String current_event_type = "";
  private int asset_type = 0;
  private String button_str = "";
  private String button_str_tip = "";
  private XulView buyButton;
  private XulView buyButtonImage;
  private XulView buyButtonValue;
  private int buyIndex = 0;
  private String canPreview = "0";
  private String categoryId;
  private XulView collectCatchButton;
  private CollectAndPlayListLogic collectLogic = new CollectAndPlayListLogic();
  private int coupon_count = 0;
  private XulView detailTip;
  private DisplayStyle displayStyle = null;
  private String episodeDisplayStyle = "";
  private String focusType = "";
  private boolean hasRefreshCategory = false;
  private String import_id = "";
  private ArrayList<RefreshBindingRunnable> inTurnBindingList = new ArrayList();
  private boolean indexOK = false;
  private String indexOrder;
  private boolean isApiOK;
  private boolean isAuth;
  private boolean isClickBuy = false;
  private boolean isClickEpisode;
  private boolean isClickPreview;
  private boolean isEmptyRecommend = false;
  private boolean isExternalGiveEpisode = false;
  private boolean isFromOut = false;
  private boolean isLoadSuccess = false;
  private boolean isShowDialog;
  private boolean isUseCoupon = false;
  private String is_charge = "0";
  private String[] lockChargeListNum = null;
  private String low_price = "0";
  private int medie_type = 1;
  private ArrayList<Menu> menuArrayList = new ArrayList();
  MovieData movieData;
  private boolean needVideoListInfo = false;
  private String packageId;
  private AtomicInteger pageCount = new AtomicInteger(0);
  private XulView playButton;
  private String preImageUrl = "";
  private boolean preLoad = false;
  private PreviewList previewList;
  private String qualityCanPlay;
  private String recommendPageNum = "";
  private HashMap<String, String> recommendReportData = new HashMap();
  private String recommend_type = "-1,-1";
  private FilmItem relevantFilmList;
  private boolean relevantOK;
  private String reportExt = "";
  private String reqid = "";
  private String sale_info = "";
  private StarInfoCollect starInfoCollect;
  private int targetIndex = -1;
  private UserAuthV2 userAuth;
  private int userAuthState;
  private String ver = "";
  private String videoId;
  private ArrayList<VideoIndex> videoIndexes;
  private VideoInfo videoInfo;
  private boolean videoListInterfaceError = false;
  private boolean videoListInterfaceFinish = false;
  private int videoType;
  private int viewType = -1;
  private String vip_id = "-1";

  private void buildRecommendReportData(FilmItem paramFilmItem)
  {
    String str1 = "";
    String str2 = "";
    String str3 = "";
    if ((paramFilmItem.filmList == null) || (paramFilmItem.filmList.size() == 0))
      return;
    Iterator localIterator = paramFilmItem.filmList.iterator();
    while (localIterator.hasNext())
    {
      FilmListItem localFilmListItem = (FilmListItem)localIterator.next();
      if (!TextUtils.isEmpty(localFilmListItem.video_id))
        str1 = str1 + "," + localFilmListItem.video_id;
      if (!TextUtils.isEmpty(localFilmListItem.import_id))
        str2 = str2 + "," + localFilmListItem.import_id;
      if (!TextUtils.isEmpty(localFilmListItem.serial_id))
        str3 = str3 + "," + localFilmListItem.serial_id;
    }
    this.recommendReportData.put("ver", paramFilmItem.artithmeticId);
    this.recommendReportData.put("reqid", paramFilmItem.estimateId);
    this.recommendReportData.put("limit", paramFilmItem.filmList.size() + "");
    HashMap localHashMap1 = this.recommendReportData;
    String str4;
    String str5;
    label303: HashMap localHashMap3;
    if (str1.length() > 1)
    {
      str4 = str1.substring(1);
      localHashMap1.put("rcdata", str4);
      HashMap localHashMap2 = this.recommendReportData;
      if (str2.length() <= 1)
        break label355;
      str5 = str2.substring(1);
      localHashMap2.put("orcdata", str5);
      localHashMap3 = this.recommendReportData;
      if (str3.length() <= 1)
        break label361;
    }
    label355: label361: for (String str6 = str3.substring(1); ; str6 = str3)
    {
      localHashMap3.put("sorcdata", str6);
      return;
      str4 = str1;
      break;
      str5 = str2;
      break label303;
    }
  }

  private void checkApiFinished()
  {
    if ((this.pageCount.get() == 0) && (this.relevantOK) && (this.indexOK))
    {
      if (this.videoInfo != null)
      {
        this.videoInfo.packageId = this.packageId;
        this.videoInfo.categoryId = this.categoryId;
      }
      inTurnRefreshBinding("menu", "file:///.app/api/get_menu");
      Logger.d("DetailPageActivity", "api完成，开始鉴权");
      getUserAuth();
    }
  }

  private void checkInTurnRefreshBinding()
  {
    if (!this.hasRefreshCategory);
    while (true)
    {
      return;
      for (int i = -1 + this.inTurnBindingList.size(); i >= 0; i--)
      {
        RefreshBindingRunnable localRefreshBindingRunnable = (RefreshBindingRunnable)this.inTurnBindingList.get(i);
        localRefreshBindingRunnable.run();
        this.inTurnBindingList.remove(localRefreshBindingRunnable);
        Logger.d("checkInTurnRefreshBinding", "刷新成功 freshBinding " + localRefreshBindingRunnable.bindingId);
      }
    }
  }

  private ByteArrayInputStream getCategory()
  {
    if ((this.displayStyle == null) || (TextUtils.isEmpty(this.episodeDisplayStyle)))
    {
      Logger.d("DetailPageActivity", "displayStyle or episodeDisplayStyle is null");
      return null;
    }
    XmlSerializer localXmlSerializer;
    StringWriter localStringWriter;
    while (true)
    {
      Menu localMenu;
      try
      {
        if (this.menuArrayList.size() == 0)
          break;
        localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        localStringWriter = new StringWriter();
        localXmlSerializer.setOutput(localStringWriter);
        localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer.startTag(null, "data");
        writeNodeValue(localXmlSerializer, "binding", "true");
        Iterator localIterator = this.menuArrayList.iterator();
        if (!localIterator.hasNext())
          break label385;
        localMenu = (Menu)localIterator.next();
        localXmlSerializer.startTag(null, "item");
        if ("m_open_detail_page_detail".equals(localMenu.action))
        {
          localXmlSerializer.attribute("", "type", "A");
          localXmlSerializer.endTag(null, "item");
          continue;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      if ("m_open_detail_page_index".equals(localMenu.action))
      {
        if (this.displayStyle == DisplayStyle.NUMBER_EPISODE)
          localXmlSerializer.attribute("", "type", "B1");
        else if ((this.displayStyle == DisplayStyle.WATCH_FOCUS_DATE) || (this.displayStyle == DisplayStyle.WATCH_FOCUS_EPISODE))
          localXmlSerializer.attribute("", "type", "B3");
        else if (this.displayStyle == DisplayStyle.WATCH_FOCUS)
          localXmlSerializer.attribute("", "type", "B2");
      }
      else if ("m_open_detail_page_prevue".equals(localMenu.action))
        localXmlSerializer.attribute("", "type", "C");
      else if ("m_open_detail_page_star".equals(localMenu.action))
        localXmlSerializer.attribute("", "type", "D");
      else if ("m_open_detail_page_recom".equals(localMenu.action))
        localXmlSerializer.attribute("", "type", "E");
    }
    label385: localXmlSerializer.endTag(null, "data");
    localXmlSerializer.endDocument();
    localXmlSerializer.flush();
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
    return localByteArrayInputStream;
  }

  private String getClipName(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    Pattern localPattern = Pattern.compile("[0-9a-zA-Z]");
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    if (i < arrayOfChar.length)
    {
      String str2 = String.valueOf(arrayOfChar[i]);
      if (localPattern.matcher(str2).find())
        localArrayList.add(new Pair(str2, Float.valueOf(0.5F)));
      while (true)
      {
        i++;
        break;
        localArrayList.add(new Pair(str2, Float.valueOf(1.0F)));
      }
    }
    float f = 0.0F;
    String str1 = "";
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      Pair localPair = (Pair)localIterator.next();
      f += ((Float)localPair.second).floatValue();
      str1 = str1 + (String)localPair.first;
      if (f >= 13.5F)
        paramString = str1 + "...";
    }
    return paramString;
  }

  private ByteArrayInputStream getExtraInfo()
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "info");
      String str = "";
      VideoIndex localVideoIndex;
      if ((this.viewType != 0) || (this.videoInfo.is_show_index == 1))
      {
        if (1 + this.videoInfo.indexCurrent != this.videoInfo.indexCount)
          break label252;
        if (this.displayStyle != DisplayStyle.WATCH_FOCUS_DATE)
        {
          Object[] arrayOfObject3 = new Object[1];
          arrayOfObject3[0] = String.valueOf(this.videoInfo.indexCount);
          str = String.format("共%s集", arrayOfObject3);
        }
        if ((this.displayStyle == DisplayStyle.WATCH_FOCUS_DATE) && (this.videoIndexes != null))
        {
          if (!"desc".equals(this.indexOrder))
            break label316;
          localVideoIndex = (VideoIndex)this.videoIndexes.get(0);
        }
      }
      while (true)
      {
        if (localVideoIndex != null)
        {
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = getTargetDateString(localVideoIndex);
          str = String.format("更新至%s期", arrayOfObject2);
        }
        writeNodeValue(localXmlSerializer, "extra_name", str);
        localXmlSerializer.endTag(null, "info");
        localXmlSerializer.endDocument();
        localXmlSerializer.flush();
        return new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
        label252: if ((1 + this.videoInfo.indexCurrent >= this.videoInfo.indexCount) || (this.displayStyle == DisplayStyle.WATCH_FOCUS_DATE))
          break;
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = String.valueOf(1 + this.videoInfo.indexCurrent);
        str = String.format("更新至%s集", arrayOfObject1);
        break;
        label316: boolean bool = "asc".equals(this.indexOrder);
        localVideoIndex = null;
        if (bool)
        {
          int i = this.videoIndexes.size();
          localVideoIndex = null;
          if (i > 0)
            localVideoIndex = (VideoIndex)this.videoIndexes.get(-1 + this.videoIndexes.size());
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private String getMediaQuality(int paramInt)
  {
    String str1 = GlobalLogic.getInstance().getQuality();
    String str2 = str1;
    ArrayList localArrayList = new ArrayList();
    if (this.videoIndexes != null)
    {
      Iterator localIterator1 = this.videoIndexes.iterator();
      while (localIterator1.hasNext())
      {
        VideoIndex localVideoIndex = (VideoIndex)localIterator1.next();
        if ((localVideoIndex.index == paramInt) && (localVideoIndex.mediaInfo != null) && (localVideoIndex.mediaInfo.size() > 0))
        {
          Iterator localIterator2 = localVideoIndex.mediaInfo.iterator();
          while (localIterator2.hasNext())
          {
            MediaInfo localMediaInfo = (MediaInfo)localIterator2.next();
            if (localMediaInfo.type != null)
              localArrayList.add(localMediaInfo.type.toUpperCase(Locale.CHINA));
          }
          if (!localArrayList.contains(str1.toUpperCase(Locale.CHINA)))
            str2 = GlobalLogic.getInstance().getTargetVideoQuality(localArrayList);
        }
      }
    }
    Logger.i("quality=" + str2);
    return str2;
  }

  private ByteArrayInputStream getMenu()
  {
    XmlSerializer localXmlSerializer;
    StringWriter localStringWriter;
    while (true)
    {
      Menu localMenu;
      try
      {
        if (this.menuArrayList.size() == 0)
          return null;
        Logger.d("allen", "getMenu() menuArrayList.size() " + this.menuArrayList.size());
        localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        localStringWriter = new StringWriter();
        localXmlSerializer.setOutput(localStringWriter);
        localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer.startTag(null, "data");
        writeNodeValue(localXmlSerializer, "state", "bindingUpdated");
        Iterator localIterator = this.menuArrayList.iterator();
        if (!localIterator.hasNext())
          break;
        localMenu = (Menu)localIterator.next();
        localXmlSerializer.startTag(null, "item");
        if ("m_open_detail_page_detail".equals(localMenu.action))
        {
          writeNodeValue(localXmlSerializer, "name", "详情");
          writeNodeValue(localXmlSerializer, "type", "A");
          writeNodeValue(localXmlSerializer, "image", localMenu.image);
          writeNodeValue(localXmlSerializer, "image_focus", localMenu.imageFocus);
          localXmlSerializer.endTag(null, "item");
          continue;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      if ("m_open_detail_page_index".equals(localMenu.action))
      {
        writeNodeValue(localXmlSerializer, "name", "选集");
        writeNodeValue(localXmlSerializer, "type", "B");
      }
      else if ("m_open_detail_page_prevue".equals(localMenu.action))
      {
        writeNodeValue(localXmlSerializer, "name", "片花");
        writeNodeValue(localXmlSerializer, "type", "C");
      }
      else if ("m_open_detail_page_star".equals(localMenu.action))
      {
        writeNodeValue(localXmlSerializer, "name", "明星");
        writeNodeValue(localXmlSerializer, "type", "D");
      }
      else if ("m_open_detail_page_recom".equals(localMenu.action))
      {
        writeNodeValue(localXmlSerializer, "name", "相关");
        writeNodeValue(localXmlSerializer, "type", "E");
      }
    }
    localXmlSerializer.endTag(null, "data");
    localXmlSerializer.endDocument();
    localXmlSerializer.flush();
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
    return localByteArrayInputStream;
  }

  private void getMenuArrayList()
  {
    if (this.menuArrayList.size() > 0);
    do
    {
      return;
      this.menuArrayList = getMenuByViewType();
    }
    while (this.menuArrayList.size() != 0);
    popErrorInfo("menu size zero", null, null);
  }

  private ArrayList<Menu> getMenuByViewType()
  {
    ArrayList localArrayList1 = XulManager.queryGlobalBinding("#api_n36/meta_data[id=4.2detail]");
    ArrayList localArrayList2 = new ArrayList();
    if ((localArrayList1 != null) && (localArrayList1.size() > 0))
      for (XulDataNode localXulDataNode1 = ((XulDataNode)localArrayList1.get(0)).getFirstChild(); localXulDataNode1 != null; localXulDataNode1 = localXulDataNode1.getNext())
      {
        XulDataNode localXulDataNode2 = localXulDataNode1.getAttribute("view_type");
        if ((localXulDataNode2 != null) && (localXulDataNode2.getValue().contains(String.valueOf(this.viewType))))
        {
          XulDataNode localXulDataNode3 = localXulDataNode1.getFirstChild();
          if (localXulDataNode3 == null)
            break;
          Menu localMenu = new Menu(null);
          XulDataNode localXulDataNode4 = localXulDataNode3.getAttribute("img_default");
          if (localXulDataNode4 != null)
            Menu.access$2102(localMenu, localXulDataNode4.getValue());
          XulDataNode localXulDataNode5 = localXulDataNode3.getAttribute("img_focus");
          if (localXulDataNode5 != null)
            Menu.access$2202(localMenu, localXulDataNode5.getValue());
          XulDataNode localXulDataNode6 = localXulDataNode3.getAttribute("name");
          if (localXulDataNode6 != null)
            Menu.access$2402(localMenu, localXulDataNode6.getValue());
          for (XulDataNode localXulDataNode7 = localXulDataNode3.getFirstChild(); ; localXulDataNode7 = localXulDataNode7.getNext())
            if (localXulDataNode7 != null)
            {
              if ("action".equals(localXulDataNode7.getName()))
                Menu.access$1702(localMenu, localXulDataNode7.getValue());
            }
            else
            {
              localArrayList2.add(localMenu);
              localXulDataNode3 = localXulDataNode3.getNext();
              break;
            }
        }
      }
    return localArrayList2;
  }

  private void getNumberVideoEpisode()
  {
    new Runnable()
    {
      public void run()
      {
        XulView localXulView1 = DetailPageActivity.this.xulFindViewById("number_episode");
        if (localXulView1 == null)
        {
          if (DetailPageActivity.this.isFinishing())
            return;
          Logger.e("numberEpisode为空，先post");
          DetailPageActivity.this.xulPost(this);
          return;
        }
        Logger.d("getNumberVideoEpisode");
        XulMassiveAreaWrapper localXulMassiveAreaWrapper = XulMassiveAreaWrapper.fromXulView(localXulView1);
        if ((DetailPageActivity.this.videoIndexes != null) && (DetailPageActivity.this.videoIndexes.size() > 0))
        {
          Logger.d("allen getNumberVideoEpisode()" + DetailPageActivity.this.videoIndexes.size());
          Iterator localIterator = DetailPageActivity.this.videoIndexes.iterator();
          if (localIterator.hasNext())
          {
            VideoIndex localVideoIndex = (VideoIndex)localIterator.next();
            XulDataNode localXulDataNode = XulDataNode.obtainDataNode("item");
            localXulDataNode.setAttribute("name", String.valueOf(1 + localVideoIndex.index));
            localXulDataNode.setAttribute("video_index", String.valueOf(localVideoIndex.index));
            localXulDataNode.setAttribute("isLocked", "false");
            if (DetailPageActivity.this.isItemPlayed(localVideoIndex.index))
            {
              localXulDataNode.setAttribute("text-color", "ff5e6281");
              localXulDataNode.setAttribute("isPlayed", "true");
            }
            while (true)
            {
              localXulMassiveAreaWrapper.addItem(localXulDataNode);
              break;
              localXulDataNode.setAttribute("isPlayed", "false");
              localXulDataNode.setAttribute("text-color", "7fffffff");
            }
          }
          XulView localXulView2 = DetailPageActivity.this.xulFindViewById("number_episode_area");
          localXulView2.setStyle("display", "block");
          localXulView2.resetRender();
          localXulMassiveAreaWrapper.syncContentView();
          DetailPageActivity.this.setNumberEpisodeIndicator((int)Math.ceil(DetailPageActivity.this.videoIndexes.size() / 25.0F), 0);
          return;
        }
        Logger.d("allen getNumberVideoEpisode() null");
      }
    }
    .run();
  }

  private ByteArrayInputStream getPreview()
  {
    while (true)
    {
      int j;
      try
      {
        int i = this.previewList.items.size();
        if ((this.previewList != null) && (i > 0))
        {
          XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
          StringWriter localStringWriter = new StringWriter();
          localXmlSerializer.setOutput(localStringWriter);
          localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
          localXmlSerializer.startTag(null, "episode");
          writeNodeValue(localXmlSerializer, "state", "bindingUpdated");
          SparseArray localSparseArray = new SparseArray();
          ArrayList localArrayList = null;
          j = 0;
          if (j >= i)
            break label408;
          if (j % 6 == 0)
          {
            localArrayList = new ArrayList();
            localSparseArray.put(j / 6, localArrayList);
          }
          if (localArrayList != null)
          {
            localArrayList.add(this.previewList.items.get(j));
            break label402;
            if (k < localSparseArray.size())
            {
              localXmlSerializer.startTag(null, "page");
              int m = 0;
              if (m < ((ArrayList)localSparseArray.get(k)).size())
              {
                PreviewList.Item localItem = (PreviewList.Item)((ArrayList)localSparseArray.get(k)).get(m);
                localXmlSerializer.startTag(null, "item");
                writeNodeValue(localXmlSerializer, "name", localItem.watchFocus);
                writeNodeValue(localXmlSerializer, "video_index", String.valueOf(localItem.videoIndex));
                writeNodeValue(localXmlSerializer, "page_index", String.valueOf(k));
                writeNodeValue(localXmlSerializer, "video_index_id", localItem.id);
                if (UserCPLLogic.getInstance().isPlayRecordExistsIncludeLocal(this.videoId, localItem.videoIndex))
                  writeNodeValue(localXmlSerializer, "view_state", "1");
                localXmlSerializer.endTag(null, "item");
                m++;
                continue;
              }
              localXmlSerializer.endTag(null, "page");
              k++;
              continue;
            }
            localXmlSerializer.endTag(null, "episode");
            localXmlSerializer.endDocument();
            localXmlSerializer.flush();
            ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
            return localByteArrayInputStream;
          }
        }
        else
        {
          return null;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      label402: j++;
      continue;
      label408: int k = 0;
    }
  }

  private void getPreviewList()
  {
    ServerApiManager.i().APIGetPreviewList(new SccmsApiGetPreviewListTaskListener(null), this.videoId, 0, 100);
  }

  private ByteArrayInputStream getRelevantFilm()
  {
    try
    {
      if ((this.relevantFilmList.filmList != null) && (this.relevantFilmList.filmList.size() != 0))
      {
        this.ver = this.relevantFilmList.artithmeticId;
        this.reqid = this.relevantFilmList.estimateId;
        int i = this.relevantFilmList.filmList.size();
        int j = 0;
        XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        StringWriter localStringWriter = new StringWriter();
        localXmlSerializer.setOutput(localStringWriter);
        localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer.startTag(null, "data");
        writeNodeValue(localXmlSerializer, "state", "bindingUpdated");
        Iterator localIterator = this.relevantFilmList.filmList.iterator();
        while (localIterator.hasNext())
        {
          FilmListItem localFilmListItem = (FilmListItem)localIterator.next();
          localXmlSerializer.startTag(null, "item");
          writeNodeValue(localXmlSerializer, "id", String.valueOf(j + 1));
          writeNodeValue(localXmlSerializer, "name", localFilmListItem.film_name);
          writeNodeValue(localXmlSerializer, "url", localFilmListItem.v_img_url);
          writeNodeValue(localXmlSerializer, "img_v", localFilmListItem.v_img_url);
          writeNodeValue(localXmlSerializer, "img_h", localFilmListItem.h_img_url);
          writeNodeValue(localXmlSerializer, "corner_mark", localFilmListItem.corner_mark);
          writeNodeValue(localXmlSerializer, "corner_mark_img", localFilmListItem.corner_mark_img_url);
          writeNodeValue(localXmlSerializer, "video_id", localFilmListItem.video_id);
          writeNodeValue(localXmlSerializer, "video_type", String.valueOf(localFilmListItem.video_type));
          writeNodeValue(localXmlSerializer, "view_type", String.valueOf(localFilmListItem.viewType));
          localXmlSerializer.startTag(null, "arg_list");
          writeArgsValue(localXmlSerializer, "name", localFilmListItem.film_name);
          writeArgsValue(localXmlSerializer, "view_type", String.valueOf(localFilmListItem.viewType));
          writeArgsValue(localXmlSerializer, "index", String.valueOf(j));
          writeArgsValue(localXmlSerializer, "count", String.valueOf(i));
          writeArgsValue(localXmlSerializer, "video_id", localFilmListItem.video_id);
          writeArgsValue(localXmlSerializer, "video_type", String.valueOf(localFilmListItem.video_type));
          writeArgsValue(localXmlSerializer, "package_id", localFilmListItem.package_id);
          writeArgsValue(localXmlSerializer, "category_id", localFilmListItem.category_id);
          writeArgsValue(localXmlSerializer, "serial_id", localFilmListItem.serial_id);
          writeArgsValue(localXmlSerializer, "import_id", localFilmListItem.import_id);
          localXmlSerializer.endTag(null, "arg_list");
          localXmlSerializer.endTag(null, "item");
          j++;
        }
        this.recommendPageNum = String.valueOf(j);
        localXmlSerializer.endTag(null, "data");
        localXmlSerializer.endDocument();
        localXmlSerializer.flush();
        ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
        return localByteArrayInputStream;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    return null;
  }

  private void getRelevantFilms()
  {
    ServerApiManager.i().APIGetRelevantFilms(this.videoId, this.videoType, this.packageId, this.categoryId, 0, 12, new SccmsApiGetRelevantFilms(null));
  }

  private String getReportExt(String paramString)
  {
    if ("D2".equals(paramString))
      return ReportInfo.getInstance().getSearchKey();
    if ("KW".equals(paramString))
      return ReportInfo.getInstance().getWebUrl();
    return "";
  }

  private void getStarCollectData()
  {
    StarInfoCollect localStarInfoCollect = AIDataCacheTask.i().getVideoRelActorList(this.videoId, this.videoType);
    if (localStarInfoCollect != null)
    {
      Logger.d("DetailPageActivity", "存在缓存明星，开始使用");
      processStarInfo(localStarInfoCollect);
      return;
    }
    Logger.e("DetailPageActivity", "不存在缓存明星，刷接口获取");
    ServerApiManager.i().APIGetStarCollectData(this.videoId, String.valueOf(this.videoType), new SccmsApiGetStarCollectDataTaskListener(null));
  }

  private ByteArrayInputStream getStars()
  {
    XmlSerializer localXmlSerializer;
    StringWriter localStringWriter;
    while (true)
    {
      StarInfo localStarInfo;
      String str;
      try
      {
        if ((this.starInfoCollect == null) || (this.starInfoCollect.getStarInfo() == null) || (this.starInfoCollect.getStarInfo().size() == 0))
          break label475;
        localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        localStringWriter = new StringWriter();
        localXmlSerializer.setOutput(localStringWriter);
        localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer.startTag(null, "data");
        writeNodeValue(localXmlSerializer, "state", "bindingUpdated");
        Iterator localIterator = this.starInfoCollect.getStarInfo().iterator();
        if (!localIterator.hasNext())
          break;
        localStarInfo = (StarInfo)localIterator.next();
        localXmlSerializer.startTag(null, "item");
        writeNodeValue(localXmlSerializer, "url", "effect:circle:" + localStarInfo.getImgV());
        writeNodeValue(localXmlSerializer, "name", localStarInfo.getName());
        str = "";
        switch (this.viewType)
        {
        case 3:
        default:
          writeNodeValue(localXmlSerializer, "actor_id", localStarInfo.getId());
          localXmlSerializer.startTag(null, "arg_list");
          writeArgsValue(localXmlSerializer, "actor", localStarInfo.getName());
          writeArgsValue(localXmlSerializer, "actor_id", localStarInfo.getId());
          writeArgsValue(localXmlSerializer, "label_id", localStarInfo.getLabelId());
          writeArgsValue(localXmlSerializer, "label_id_v2", localStarInfo.getLabelIdV2());
          localXmlSerializer.endTag(null, "arg_list");
          writeNodeValue(localXmlSerializer, "role", str);
          localXmlSerializer.endTag(null, "item");
          continue;
        case 0:
        case 1:
        case 2:
        case 4:
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      if ("director".equals(localStarInfo.getStarType()))
      {
        str = "导演";
      }
      else if ("actor".equals(localStarInfo.getStarType()))
      {
        str = "演员";
        continue;
        if ("director".equals(localStarInfo.getStarType()))
        {
          str = "主持人";
          continue;
          if ("actor".equals(localStarInfo.getStarType()))
            str = "艺人";
        }
      }
    }
    localXmlSerializer.endTag(null, "data");
    localXmlSerializer.endDocument();
    localXmlSerializer.flush();
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
    return localByteArrayInputStream;
    label475: return null;
  }

  private String getTargetDateString(VideoIndex paramVideoIndex)
  {
    String str1 = paramVideoIndex.onlineTime;
    if (!TextUtils.isEmpty(str1))
    {
      str1 = str1.split(" ")[0];
      String[] arrayOfString = str1.split("-");
      if ((arrayOfString != null) && (10 == str1.length()))
      {
        String str2 = arrayOfString[1];
        String str3 = arrayOfString[2];
        str1 = str2 + "." + str3;
      }
    }
    return str1;
  }

  private void getUserAuth()
  {
    GetUserAuthV2Params localGetUserAuthV2Params = new GetUserAuthV2Params(this.videoId, String.valueOf(this.videoType), GlobalLogic.getInstance().getQuality());
    ServerApiManager.i().APIGetUserAuthV2(localGetUserAuthV2Params, new SccmsApiGetUserAuthV2TaskListener());
  }

  private InputStream getVideoEpisode()
  {
    while (true)
    {
      XmlSerializer localXmlSerializer;
      XulWorker.XulDownloadOutputBuffer localXulDownloadOutputBuffer;
      int j;
      int k;
      VideoIndex localVideoIndex;
      try
      {
        if ((this.videoIndexes == null) || (this.videoIndexes.size() <= 0))
          break label497;
        int i = this.videoIndexes.size();
        localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        localXulDownloadOutputBuffer = XulWorker.obtainDownloadBuffer(16384);
        localXmlSerializer.setOutput(localXulDownloadOutputBuffer, "UTF-8");
        localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer.startTag(null, "episode");
        writeNodeValue(localXmlSerializer, "state", "bindingUpdated");
        SparseArray localSparseArray = new SparseArray();
        ArrayList localArrayList = null;
        j = 0;
        if (j >= i)
          break label505;
        if (j % 6 == 0)
        {
          localArrayList = new ArrayList();
          localSparseArray.put(j / 6, localArrayList);
        }
        if (localArrayList == null)
          break label499;
        localArrayList.add(this.videoIndexes.get(j));
        break label499;
        if (k >= localSparseArray.size())
          break label464;
        localXmlSerializer.startTag(null, "page");
        int m = 0;
        if (m >= ((ArrayList)localSparseArray.get(k)).size())
          break label447;
        localVideoIndex = (VideoIndex)((ArrayList)localSparseArray.get(k)).get(m);
        localXmlSerializer.startTag(null, "item");
        if (this.displayStyle == DisplayStyle.WATCH_FOCUS)
        {
          writeNodeValue(localXmlSerializer, "name", localVideoIndex.desc.trim());
          writeNodeValue(localXmlSerializer, "page_index", String.valueOf(k));
          writeNodeValue(localXmlSerializer, "video_index", String.valueOf(localVideoIndex.index));
          if (UserCPLLogic.getInstance().isPlayRecordExistsIncludeLocal(this.videoId, localVideoIndex.index))
            writeNodeValue(localXmlSerializer, "view_state", "1");
          localXmlSerializer.endTag(null, "item");
          m++;
          continue;
        }
        if (this.displayStyle == DisplayStyle.WATCH_FOCUS_DATE)
        {
          writeNodeValue(localXmlSerializer, "name", localVideoIndex.desc.trim());
          writeNodeValue(localXmlSerializer, "date", getTargetDateString(localVideoIndex));
          continue;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      if (this.displayStyle == DisplayStyle.WATCH_FOCUS_EPISODE)
      {
        writeNodeValue(localXmlSerializer, "name", localVideoIndex.desc.trim());
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(1 + localVideoIndex.index);
        writeNodeValue(localXmlSerializer, "date", String.format("第%d集", arrayOfObject));
        continue;
        label447: localXmlSerializer.endTag(null, "page");
        k++;
        continue;
        label464: localXmlSerializer.endTag(null, "episode");
        localXmlSerializer.endDocument();
        localXmlSerializer.flush();
        InputStream localInputStream = localXulDownloadOutputBuffer.toInputStream();
        return localInputStream;
        label497: return null;
        label499: j++;
        continue;
        label505: k = 0;
      }
    }
  }

  private VideoIndex getVideoIndex(int paramInt)
  {
    Iterator localIterator = this.videoIndexes.iterator();
    VideoIndex localVideoIndex;
    do
    {
      boolean bool = localIterator.hasNext();
      localObject = null;
      if (!bool)
        break;
      localVideoIndex = (VideoIndex)localIterator.next();
    }
    while (localVideoIndex.index != paramInt);
    Object localObject = localVideoIndex;
    return localObject;
  }

  private void getVideoIndexList()
  {
    if (this.movieData != null)
    {
      if (this.movieData.updateIndex)
      {
        Logger.d("DetailPageActivity", "需要更新剧集");
        ServerApiManager.i().APIGetVideoIndexList(this.videoId, this.videoType, 0, 10000, true, new SccmsApiGetVideoIndexListTaskListener());
      }
    }
    else
      return;
    Logger.d("DetailPageActivity", "不需要立即更新剧集");
    ServerApiManager.i().APIGetVideoIndexList(this.videoId, this.videoType, 0, 10000, false, new SccmsApiGetVideoIndexListTaskListener());
  }

  private ByteArrayInputStream getVideoInfo()
  {
    if (this.videoInfo == null)
    {
      Logger.d("allen", "getVideoInfo() videoInfo == null ");
      return null;
    }
    Logger.d("allen", "getVideoInfo() success");
    while (true)
    {
      XmlSerializer localXmlSerializer;
      try
      {
        localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        StringWriter localStringWriter = new StringWriter();
        localXmlSerializer.setOutput(localStringWriter);
        localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer.startTag(null, "info");
        String str1 = this.videoInfo.imgVUrl;
        if ((TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(this.videoInfo.imgHUrl)))
          str1 = "effect:mirror:" + this.videoInfo.imgHUrl;
        if (TextUtils.isEmpty(this.preImageUrl))
        {
          writeNodeValue(localXmlSerializer, "default_image", "file:///.assets/detail/poster_default.png");
          writeNodeValue(localXmlSerializer, "image", str1);
          if ((this.movieData != null) && (!hasElement(this.movieData.name)))
            setTitleText(getClipName(this.videoInfo.name));
          if (hasElement(this.videoInfo.showTime))
          {
            String str7 = this.videoInfo.showTime;
            if (str7.length() > 4)
              str7 = str7.substring(0, 4);
            writeNodeValue(localXmlSerializer, "show_time", str7);
          }
          writeNodeValue(localXmlSerializer, "state", "bindingUpdated");
          String str2 = this.videoInfo.kind;
          if (hasElement(str2))
          {
            Matcher localMatcher = Pattern.compile("([^/]+)(/[^/]+){0,2}").matcher(str2);
            if (localMatcher.find())
              str2 = localMatcher.group();
          }
          if (str2.length() > 13)
            str2 = str2.substring(0, 12) + "...";
          writeNodeValue(localXmlSerializer, "kind", str2);
          String str3 = this.videoInfo.area;
          if (str3.length() > 6)
            str3 = str3.substring(0, 5) + "...";
          writeNodeValue(localXmlSerializer, "area", str3);
          if (this.videoInfo.user_score < 0.0D)
            this.videoInfo.user_score = 0.0D;
          writeNodeValue(localXmlSerializer, "point", String.valueOf(this.videoInfo.user_score));
          if (this.videoInfo.play_count >= 10000)
            writeNodeValue(localXmlSerializer, "v1", String.valueOf(this.videoInfo.play_count));
          String str4 = Html.fromHtml(this.videoInfo.desc).toString();
          writeNodeValue(localXmlSerializer, "more_desc", str4);
          if (str4.length() > 86)
            str4 = str4.substring(0, 81) + "......";
          writeNodeValue(localXmlSerializer, "desc", str4);
          writeNodeValue(localXmlSerializer, "corner_mark", this.videoInfo.corner_mark_img_url);
          if ((this.viewType == 0) || (1 + this.videoInfo.indexCurrent >= this.videoInfo.indexCount) || (!AppFuncCfg.FUNCTION_ENABLE_TRACEPLAY))
            break label747;
          writeNodeValue(localXmlSerializer, "button_type", "trace");
          if (!UserCPLLogic.getInstance().isTracePlayExists(this.videoId))
            break label797;
          str6 = "取消追剧";
          writeNodeValue(localXmlSerializer, "button_name", str6);
          writeNodeValue(localXmlSerializer, "director", this.videoInfo.director);
          writeNodeValue(localXmlSerializer, "actor", this.videoInfo.actor.replaceAll("/", "，"));
          localXmlSerializer.endTag(null, "info");
          localXmlSerializer.endDocument();
          localXmlSerializer.flush();
          ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
          return localByteArrayInputStream;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      writeNodeValue(localXmlSerializer, "default_image", this.preImageUrl);
      continue;
      label747: writeNodeValue(localXmlSerializer, "button_type", "collect");
      if (UserCPLLogic.getInstance().isCollectExists(this.videoId));
      for (String str5 = "取消收藏"; ; str5 = "收藏")
      {
        writeNodeValue(localXmlSerializer, "button_name", str5);
        break;
      }
      label797: String str6 = "追剧";
    }
  }

  private boolean hasElement(String paramString)
  {
    return (!TextUtils.isEmpty(paramString)) && (!"null".equalsIgnoreCase(paramString));
  }

  private void hideDesc(XulArea paramXulArea, XulPage paramXulPage)
  {
    this.detailTip = paramXulPage.findItemById(paramXulArea, "detail_tip");
    if (this.detailTip == null)
      return;
    this.detailTip.setStyle("display", "none");
    this.detailTip.resetRender();
  }

  private void inTurnRefreshBinding(RefreshBindingRunnable paramRefreshBindingRunnable)
  {
    if ("category".equals(paramRefreshBindingRunnable.bindingId))
      if (!this.hasRefreshCategory);
    while (true)
    {
      return;
      if ((this.isApiOK) && (this.menuArrayList.size() > 0) && (this.displayStyle != null) && (this.episodeDisplayStyle != null))
      {
        Logger.d("inTurnRefreshBinding", "freshCategory");
        paramRefreshBindingRunnable.run();
        return;
      }
      Logger.d("inTurnRefreshBinding", "Category条件不满足");
      return;
      if (!this.hasRefreshCategory)
      {
        this.inTurnBindingList.add(paramRefreshBindingRunnable);
        Logger.d("inTurnRefreshBinding", "没有刷新成功 add");
        return;
      }
      paramRefreshBindingRunnable.run();
      Logger.d("inTurnRefreshBinding", "刷新成功Category freshBinding " + paramRefreshBindingRunnable.bindingId);
      for (int i = -1 + this.inTurnBindingList.size(); i >= 0; i--)
      {
        RefreshBindingRunnable localRefreshBindingRunnable = (RefreshBindingRunnable)this.inTurnBindingList.get(i);
        localRefreshBindingRunnable.run();
        this.inTurnBindingList.remove(localRefreshBindingRunnable);
        Logger.d("inTurnRefreshBinding", "刷新成功Category freshBinding " + localRefreshBindingRunnable.bindingId);
      }
    }
  }

  private void inTurnRefreshBinding(String paramString1, String paramString2)
  {
    Logger.d("inTurnRefreshBinding", paramString1);
    inTurnRefreshBinding(new RefreshBindingRunnable(paramString1, paramString2));
  }

  private boolean isCanPlay(int paramInt)
  {
    boolean bool1 = true;
    if (isItemLocked(paramInt))
    {
      boolean bool2 = "1".equals(this.canPreview);
      bool1 = false;
      if (bool2)
        bool1 = true;
    }
    Logger.d("DetailPageActivity", "isCanPlay(" + paramInt + "): " + bool1);
    return bool1;
  }

  private boolean isItemLocked(int paramInt)
  {
    if (paramInt <= -1)
    {
      Logger.e("DetailPageActivity", "数字剧集解析异常");
      paramInt = 0;
    }
    if (this.lockChargeListNum == null);
    while (true)
    {
      return false;
      if (this.lockChargeListNum.length > 0)
        for (int i = 0; i < this.lockChargeListNum.length; i++)
          if (paramInt + 1 == Integer.parseInt(this.lockChargeListNum[i]))
            return true;
    }
  }

  private boolean isItemPlayed(int paramInt)
  {
    return UserCPLLogic.getInstance().isPlayRecordExistsIncludeLocal(this.videoId, paramInt);
  }

  private void judgeDisplayStyle()
  {
    if ("0".equals(this.episodeDisplayStyle))
      this.displayStyle = DisplayStyle.NUMBER_EPISODE;
    do
    {
      return;
      if ("m1".equals(this.episodeDisplayStyle))
      {
        this.displayStyle = DisplayStyle.WATCH_FOCUS_DATE;
        return;
      }
      if ("m2".equals(this.episodeDisplayStyle))
      {
        this.displayStyle = DisplayStyle.WATCH_FOCUS;
        return;
      }
    }
    while (!"m3".equals(this.episodeDisplayStyle));
    this.displayStyle = DisplayStyle.WATCH_FOCUS_EPISODE;
  }

  private void openVideoFromEpisode(int paramInt, boolean paramBoolean)
  {
    Object localObject = null;
    if (paramInt >= 0)
    {
      Iterator localIterator = this.videoIndexes.iterator();
      VideoIndex localVideoIndex;
      do
      {
        boolean bool = localIterator.hasNext();
        localObject = null;
        if (!bool)
          break;
        localVideoIndex = (VideoIndex)localIterator.next();
      }
      while (localVideoIndex.index != paramInt);
      localObject = localVideoIndex;
    }
    if (localObject == null)
    {
      if (!paramBoolean)
      {
        Logger.e("DetailPageActivity", "没有找到对应的剧集");
        return;
      }
      paramInt = ((VideoIndex)this.videoIndexes.get(0)).index;
    }
    this.isClickEpisode = true;
    this.isClickPreview = false;
    PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
    if (localObject == null)
    {
      localPlayerIntentParams.nns_index = paramInt;
      if (this.displayStyle == DisplayStyle.WATCH_FOCUS_DATE)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = String.valueOf(paramInt + 1);
        localPlayerIntentParams.subfix_title = String.format("%s期", arrayOfObject2);
        localPlayerIntentParams.nns_videoInfo = this.videoInfo;
        localPlayerIntentParams.nns_videoInfo.packageId = this.packageId;
        localPlayerIntentParams.nns_videoInfo.categoryId = this.categoryId;
        localPlayerIntentParams.nns_videoInfo.is_charge = this.is_charge;
        localPlayerIntentParams.nns_videoInfo.is_useCoupon = this.isUseCoupon;
        localPlayerIntentParams.nns_videoInfo.coupon_count = this.coupon_count;
        localPlayerIntentParams.nns_videoInfo.asset_type = this.asset_type;
        localPlayerIntentParams.nns_videoInfo.vip_id = "";
        localPlayerIntentParams.nns_videoInfo.is_recommend = this.recommend_type;
        localPlayerIntentParams.nns_videoInfo.is_trylook = this.canPreview;
        localPlayerIntentParams.nns_videoInfo.indexList = null;
        if ((this.videoIndexes == null) || (this.videoIndexes.size() <= 300))
          break label622;
        Logger.w("DetailPageActivity", "openVideoFromEpisode nns_mediaIndexList 太大，设置为空，让播放器自己加载去！size: " + this.videoIndexes.size());
        localPlayerIntentParams.nns_mediaIndexList = null;
        label336: localPlayerIntentParams.mediaQuality = getMediaQuality(paramInt);
        localPlayerIntentParams.userAuth = this.userAuth;
        localPlayerIntentParams.lockChargeListNum = this.lockChargeListNum;
        if (this.isFromOut)
          localPlayerIntentParams.out_play = (Tools.getOutPlayOriginal() + "-1");
        if (!paramBoolean)
          break label634;
      }
    }
    label622: label634: for (int i = 1; ; i = 2)
    {
      reportClick(i, localPlayerIntentParams.nns_videoInfo.videoId, localPlayerIntentParams.nns_index + "");
      Intent localIntent = new Intent(this.context, ActivityAdapter.getInstance().getMPlayer());
      localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_EXIT_APP, false);
      localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
      this.context.startActivity(localIntent);
      Logger.d("intentDatasubfix_title=" + localPlayerIntentParams.subfix_title + " index=" + localPlayerIntentParams.nns_index + " packageId=" + localPlayerIntentParams.nns_videoInfo.packageId + " quality=" + localPlayerIntentParams.mediaQuality);
      return;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = String.valueOf(paramInt + 1);
      localPlayerIntentParams.subfix_title = String.format("%s集", arrayOfObject1);
      break;
      localPlayerIntentParams.subfix_title = localObject.name;
      localPlayerIntentParams.nns_index = localObject.index;
      break;
      localPlayerIntentParams.nns_mediaIndexList = this.videoIndexes;
      break label336;
    }
  }

  private void openVideoFromPlayButton()
  {
    if ((this.videoIndexes == null) || (this.videoIndexes.size() == 0))
    {
      Logger.d("DetailPageActivity", "影片剧集为空");
      return;
    }
    int i;
    label130: PlayerIntentParams localPlayerIntentParams;
    if (this.targetIndex != -1)
    {
      i = -1 + this.targetIndex;
      if (this.viewType != 0)
        break label620;
      int j = 0;
      if (i >= 0)
      {
        Iterator localIterator = this.videoIndexes.iterator();
        do
        {
          boolean bool = localIterator.hasNext();
          j = 0;
          if (!bool)
            break;
        }
        while (((VideoIndex)localIterator.next()).index != i);
        j = 1;
      }
      if (j == 0)
      {
        if (!"asc".equals(this.indexOrder))
          break label529;
        i = ((VideoIndex)this.videoIndexes.get(0)).index;
      }
      localPlayerIntentParams = new PlayerIntentParams();
      localPlayerIntentParams.nns_index = i;
      if (this.displayStyle != DisplayStyle.WATCH_FOCUS_DATE)
        break label578;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = String.valueOf(i + 1);
      localPlayerIntentParams.subfix_title = String.format("%s期", arrayOfObject2);
      label181: localPlayerIntentParams.nns_videoInfo = this.videoInfo;
      localPlayerIntentParams.nns_videoInfo.packageId = this.packageId;
      localPlayerIntentParams.nns_videoInfo.categoryId = this.categoryId;
      localPlayerIntentParams.nns_videoInfo.is_charge = this.is_charge;
      localPlayerIntentParams.nns_videoInfo.is_useCoupon = this.isUseCoupon;
      localPlayerIntentParams.nns_videoInfo.coupon_count = this.coupon_count;
      localPlayerIntentParams.nns_videoInfo.asset_type = this.asset_type;
      localPlayerIntentParams.nns_videoInfo.vip_id = "";
      localPlayerIntentParams.nns_videoInfo.is_recommend = this.recommend_type;
      localPlayerIntentParams.nns_videoInfo.is_trylook = this.canPreview;
      localPlayerIntentParams.nns_videoInfo.indexList = null;
      if ((this.videoIndexes == null) || (this.videoIndexes.size() <= 300))
        break label609;
      Logger.w("DetailPageActivity", "openVideoFromPlayButton nns_mediaIndexList 太大，设置为空，让播放器自己加载去！size: " + this.videoIndexes.size());
    }
    label529: label578: label609: for (localPlayerIntentParams.nns_mediaIndexList = null; ; localPlayerIntentParams.nns_mediaIndexList = this.videoIndexes)
    {
      localPlayerIntentParams.mediaQuality = getMediaQuality(i);
      localPlayerIntentParams.userAuth = this.userAuth;
      localPlayerIntentParams.lockChargeListNum = this.lockChargeListNum;
      if (this.isFromOut)
        localPlayerIntentParams.out_play = (Tools.getOutPlayOriginal() + "-1");
      reportClick(1, localPlayerIntentParams.nns_videoInfo.videoId, localPlayerIntentParams.nns_index + "");
      Intent localIntent = new Intent(this.context, ActivityAdapter.getInstance().getMPlayer());
      localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_EXIT_APP, false);
      localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
      localIntent.putExtra("cmd_is_from_out", getIntent().getStringExtra("cmd_is_from_out"));
      this.context.startActivity(localIntent);
      return;
      i = UserCPLLogic.getInstance().getLastPlayRecord(this.videoId);
      break;
      if ((!"desc".equals(this.indexOrder)) || (this.videoIndexes.size() <= 0))
        break label130;
      i = ((VideoIndex)this.videoIndexes.get(-1 + this.videoIndexes.size())).index;
      break label130;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = String.valueOf(i + 1);
      localPlayerIntentParams.subfix_title = String.format("%s集", arrayOfObject1);
      break label181;
    }
    label620: openVideoFromEpisode(i, true);
  }

  private void openVideoFromPreview(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
    {
      Logger.e("DetailPageActivity", "没有找到对应的剧集");
      return;
    }
    Object localObject;
    for (int i = 0; ; i++)
    {
      int j = this.previewList.items.size();
      localObject = null;
      if (i < j)
      {
        PreviewList.Item localItem2 = (PreviewList.Item)this.previewList.items.get(i);
        if (paramString.equals(localItem2.id))
          localObject = localItem2;
      }
      else
      {
        if (localObject != null)
          break;
        Logger.e("DetailPageActivity", "没有找到对应的剧集");
        return;
      }
    }
    this.isClickEpisode = false;
    this.isClickPreview = true;
    Intent localIntent = new Intent();
    localIntent.setClass(this, ActivityAdapter.getInstance().getMPlayer());
    PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
    localPlayerIntentParams.nns_index = localObject.videoIndex;
    localPlayerIntentParams.nns_video_index_id = localObject.id;
    VideoInfo localVideoInfo = new VideoInfo();
    localVideoInfo.videoId = localObject.videoId;
    localVideoInfo.name = this.videoInfo.name;
    localVideoInfo.videoType = XulUtils.tryParseInt(localObject.videoType);
    localVideoInfo.index_ui_type = "m2";
    localPlayerIntentParams.nns_videoInfo = localVideoInfo;
    localPlayerIntentParams.nns_video_preview_type = localObject.previewType;
    localPlayerIntentParams.nns_videoInfo.is_useCoupon = this.isUseCoupon;
    localPlayerIntentParams.nns_videoInfo.coupon_count = this.coupon_count;
    localPlayerIntentParams.nns_videoInfo.asset_type = this.asset_type;
    localPlayerIntentParams.userAuth = this.userAuth;
    localPlayerIntentParams.lockChargeListNum = this.lockChargeListNum;
    localPlayerIntentParams.mediaQuality = getMediaQuality(localObject.videoIndex);
    Iterator localIterator;
    if ((this.previewList.items != null) && (this.previewList.items.size() > 0) && (this.previewList.items.size() <= 300))
    {
      localPlayerIntentParams.nns_mediaIndexList = new ArrayList();
      localIterator = this.previewList.items.iterator();
    }
    while (localIterator.hasNext())
    {
      PreviewList.Item localItem1 = (PreviewList.Item)localIterator.next();
      VideoIndex localVideoIndex = new VideoIndex();
      localVideoIndex.id = localItem1.id;
      localVideoIndex.index = localItem1.videoIndex;
      localVideoIndex.name = localItem1.name;
      localVideoIndex.desc = localItem1.watchFocus;
      localVideoIndex.preview_type = localItem1.previewType;
      localPlayerIntentParams.nns_mediaIndexList.add(localVideoIndex);
      continue;
      localPlayerIntentParams.nns_mediaIndexList = null;
    }
    localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
    localIntent.addFlags(8388608);
    startActivity(localIntent);
  }

  private void popErrorInfo(String paramString, ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
  {
    this.isLoadSuccess = false;
    String[] arrayOfString = new String[2];
    arrayOfString[0] = this.videoId;
    arrayOfString[1] = ("packetid=" + this.packageId + "&categoryid=" + this.categoryId + "&videoid=" + this.videoId + "&videoType=" + this.videoType + "&pay=" + "" + "&ext=" + this.reportExt);
    reportLoad(false, arrayOfString);
    showErrorDialogAndReport(11, "1002008", paramString, paramServerApiTaskInfo, paramServerApiCommonError);
  }

  private void processBuyButtonShow()
  {
    XulArea localXulArea1 = (XulArea)xulFindViewById("content_page");
    if (localXulArea1 == null)
      return;
    XulPage localXulPage = localXulArea1.getOwnerPage();
    this.buyButton = localXulPage.findItemById(localXulArea1, "buy_button");
    this.collectCatchButton = localXulPage.findItemById(localXulArea1, "collect_catch_area");
    if ((this.isAuth) && (this.userAuthState == 1))
    {
      if (this.buyButton != null)
      {
        this.buyButton.setStyle("display", "block");
        this.buyButton.resetRender();
      }
      setShowValue(localXulArea1, localXulPage);
      if (this.collectCatchButton != null)
      {
        XulView localXulView2 = localXulPage.findItemById(localXulArea1, "collect_catch_button");
        if (localXulView2 != null)
        {
          localXulView2.setStyle("display", "none");
          localXulView2.resetRender();
        }
        this.collectCatchButton.setStyle("display", "block");
        this.collectCatchButton.setAttr("x", "328");
        this.collectCatchButton.resetRender();
      }
      Logger.i("DetailPageActivity", "开通按钮需显示");
      return;
    }
    if (this.buyButton != null)
    {
      if (!this.buyButton.hasFocus())
        break label309;
      xulRequestFocus(this.playButton);
    }
    while (true)
    {
      this.buyButton.setStyle("display", "none");
      this.buyButton.resetRender();
      hideDesc(localXulArea1, localXulPage);
      if (this.collectCatchButton != null)
      {
        XulView localXulView1 = localXulPage.findItemById(localXulArea1, "collect_catch_button");
        if (localXulView1 != null)
        {
          localXulView1.setStyle("margin-left", "20");
          localXulView1.setStyle("display", "block");
          localXulView1.resetRender();
        }
        this.collectCatchButton.setStyle("display", "block");
        this.collectCatchButton.setAttr("x", "0");
        this.collectCatchButton.resetRender();
      }
      Logger.i("DetailPageActivity", "开通按钮隐藏");
      return;
      label309: XulArea localXulArea2 = (XulArea)localXulPage.findItemById(localXulArea1, "detail_info");
      if (localXulArea2 != null)
        localXulArea2.setDynamicFocus(this.playButton);
    }
  }

  private void processLoadPreLogic()
  {
    this.movieData = ((MovieData)getIntent().getParcelableExtra("movieData"));
    if (this.movieData != null)
    {
      this.videoId = this.movieData.videoId;
      this.videoType = this.movieData.videoType;
      this.targetIndex = this.movieData.videoIndex;
      if (this.targetIndex != -1)
        this.isExternalGiveEpisode = true;
      this.packageId = this.movieData.packageId;
      this.categoryId = this.movieData.categoryId;
      VideoInfo localVideoInfo = AIDataCacheTask.i().getVideoInfo(this.videoId, this.videoType);
      if (localVideoInfo != null)
      {
        Logger.d("DetailPageActivity", "已读到缓存详情信息，开始使用");
        this.videoInfo = localVideoInfo;
        Logger.i("DetailPageActivity", "videoInfo = " + this.videoInfo.toString());
        if (!TextUtils.isEmpty(localVideoInfo.index_ui_type))
          this.episodeDisplayStyle = localVideoInfo.index_ui_type;
        this.viewType = this.videoInfo.view_type;
        judgeDisplayStyle();
        getMenuArrayList();
        Logger.d("DetailPageActivity", "详情获取到的viewType=" + this.viewType + " 剧集列表展示样式=" + this.episodeDisplayStyle);
      }
    }
  }

  private void processLoadRenderReadyLogic()
  {
    if (this.movieData != null)
    {
      String str = this.movieData.name;
      if (!hasElement(str))
        break label68;
      setTitleText(getClipName(str));
    }
    while (this.videoInfo != null)
    {
      inTurnRefreshBinding("category", "file:///.app/api/get_category");
      inTurnRefreshBinding("video_info", "file:///.app/api/get_video_info");
      initExternalData();
      getVideoIndexList();
      return;
      label68: if (this.videoInfo != null)
        setTitleText(getClipName(this.videoInfo.name));
    }
    if (this.movieData.viewType != -1)
    {
      this.viewType = this.movieData.viewType;
      Logger.d("DetailPageActivity", "外部传入的viewType" + this.viewType);
      this.preImageUrl = this.movieData.img_v;
    }
    while (true)
    {
      Logger.e("DetailPageActivity", "未读取到缓存详情信息，开始请求接口");
      ServerApiManager.i().APIGetVideoInfoV3(this.videoId, this.videoType, new SccmsApiGetVideoInfoV3TaskListener());
      break;
      Logger.d("DetailPageActivity", "外部未传viewType");
    }
  }

  private void processLockEpisode()
  {
    if (this.videoIndexes == null)
      Logger.e("DetailPageActivity", "processLockEpisode 剧集为空");
    XulArea localXulArea;
    do
    {
      return;
      localXulArea = (XulArea)xulFindViewById("content_page");
    }
    while (localXulArea == null);
    XulPage localXulPage = localXulArea.getOwnerPage();
    Object localObject;
    if (this.displayStyle == DisplayStyle.WATCH_FOCUS)
    {
      XulView localXulView5 = localXulPage.findItemById(localXulArea, "watch_focus_episode");
      localObject = localXulView5;
      if (localXulView5 == null)
      {
        Logger.e("DetailPageActivity", "processLockEpisode 看点剧集为空");
        return;
      }
      XulArrayList localXulArrayList2 = XulPage.findItemsByClass((XulArea)localXulView5, "watch_focus");
      Logger.d("DetailPageActivity", "episode " + localXulView5 + " episodeItems" + localXulArrayList2);
      if (localXulArrayList2 != null)
      {
        int k = 0;
        if (k < localXulArrayList2.size())
        {
          XulView localXulView6 = (XulView)localXulArrayList2.get(k);
          int m = XulUtils.tryParseInt(localXulView6.getDataString("videoindex"));
          if (m <= -1)
          {
            Logger.e("DetailPageActivity", "看点剧集解析异常");
            m = 0;
          }
          if ((this.lockChargeListNum == null) || (this.lockChargeListNum.length <= 0))
          {
            localXulView6.removeClass("lock_padding_item");
            localXulView6.resetRender();
            Logger.d("DetailPageActivity", "processLockEpisode 不加锁 view" + localXulView6 + " j" + k + " number=" + m);
          }
          while (true)
          {
            k++;
            break;
            if (isItemLocked(m))
            {
              localXulView6.addClass("lock_padding_item");
              localXulView6.resetRender();
              Logger.d("DetailPageActivity", "processLockEpisode 加 加锁类view" + localXulView6 + " j" + k + " number=" + m);
            }
          }
        }
      }
    }
    else
    {
      if (this.displayStyle != DisplayStyle.NUMBER_EPISODE)
        break label378;
      XulView localXulView4 = xulFindViewById("number_episode");
      localObject = localXulView4;
      updateNumberEpisode(0, localXulView4);
    }
    label378: XulArrayList localXulArrayList1;
    do
    {
      DisplayStyle localDisplayStyle1;
      DisplayStyle localDisplayStyle2;
      do
      {
        setEpisodeDisplay((XulView)localObject);
        return;
        if (this.displayStyle == DisplayStyle.WATCH_FOCUS_DATE)
          break;
        localDisplayStyle1 = this.displayStyle;
        localDisplayStyle2 = DisplayStyle.WATCH_FOCUS_EPISODE;
        localObject = null;
      }
      while (localDisplayStyle1 != localDisplayStyle2);
      XulView localXulView1 = localXulPage.findItemById(localXulArea, "watch_focus_date_episode");
      localObject = localXulView1;
      if (localXulView1 == null)
      {
        Logger.e("DetailPageActivity", "processLockEpisode 看点日期剧集为空");
        return;
      }
      localXulArrayList1 = XulPage.findItemsByClass((XulArea)localXulView1, "watch_focus_date_episode_area");
      Logger.d("DetailPageActivity", "episode " + localXulView1 + " episodeItems" + localXulArrayList1);
    }
    while (localXulArrayList1 == null);
    int i = 0;
    label490: XulView localXulView2;
    XulView localXulView3;
    String str2;
    int j;
    if (i < localXulArrayList1.size())
    {
      localXulView2 = (XulView)((XulView)localXulArrayList1.get(i)).findItemsByClass("watch_focus_date_episode_first_item").get(0);
      localXulView3 = ((XulView)localXulArrayList1.get(i)).findItemById("watch_focus_date_lock_item");
      String str1 = localXulView2.getDataString("videoindex");
      str2 = localXulView2.getDataString("date");
      j = XulUtils.tryParseInt(str1);
      if (j <= -1)
      {
        Logger.e("DetailPageActivity", "看点日期剧集解析异常");
        j = 0;
      }
      if ((this.lockChargeListNum != null) && (this.lockChargeListNum.length > 0))
        break label639;
      localXulView2.setAttr("text", str2);
      localXulView3.setStyle("display", "none");
      localXulView2.resetRender();
      localXulView3.resetRender();
    }
    while (true)
    {
      i++;
      break label490;
      break;
      label639: if (isItemLocked(j))
      {
        localXulView2.setAttr("text", str2);
        localXulView3.setStyle("display", "block");
        localXulView3.resetRender();
        localXulView2.resetRender();
      }
    }
  }

  private void processMenuInfo(int paramInt)
  {
    if (this.menuArrayList.size() > 0)
    {
      int i = -1 + this.menuArrayList.size();
      if (i >= 0)
      {
        if ("m_open_detail_page_index".equals(((Menu)this.menuArrayList.get(i)).action))
          if (paramInt != 0);
        while (true)
        {
          i--;
          break;
          this.needVideoListInfo = true;
          processVideoIndexListShow();
          continue;
          if ("m_open_detail_page_star".equals(((Menu)this.menuArrayList.get(i)).action))
          {
            if (paramInt == 0)
              this.pageCount.getAndIncrement();
            else
              getStarCollectData();
          }
          else if ("m_open_detail_page_prevue".equals(((Menu)this.menuArrayList.get(i)).action))
            if (paramInt == 0)
              this.pageCount.getAndIncrement();
            else
              getPreviewList();
        }
      }
    }
  }

  private void processPlayButtonText()
  {
    this.playButton = xulFindViewById("play_video_button");
    if (this.playButton != null)
      while (true)
      {
        XulView localXulView;
        int i;
        try
        {
          localXulView = (XulView)XulPage.findItemsByClass((XulArea)this.playButton, "play_font").get(0);
          i = UserCPLLogic.getInstance().getLastPlayRecord(this.videoId);
          long l = UserCPLLogic.getInstance().getPlayedTimeInPlayRecordListIncludeLocal(this.videoId, i);
          if ((this.viewType == 0) && (this.videoInfo.is_show_index == 0))
          {
            if ((l == 0L) || ("1".equals(this.canPreview)))
            {
              localXulView.setAttr("text", "播放");
              localXulView.resetRender();
              return;
            }
            localXulView.setAttr("text", "继续播放");
            continue;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          return;
        }
        if (this.displayStyle == DisplayStyle.WATCH_FOCUS_DATE)
        {
          if ((i == -1) || (this.videoIndexes == null))
          {
            localXulView.setAttr("text", "播放");
          }
          else
          {
            Iterator localIterator1 = this.videoIndexes.iterator();
            VideoIndex localVideoIndex1;
            do
            {
              boolean bool = localIterator1.hasNext();
              localObject = null;
              if (!bool)
                break;
              localVideoIndex1 = (VideoIndex)localIterator1.next();
            }
            while (localVideoIndex1.index != i);
            Object localObject = localVideoIndex1;
            if (this.isExternalGiveEpisode)
            {
              Iterator localIterator2 = this.videoIndexes.iterator();
              while (localIterator2.hasNext())
              {
                VideoIndex localVideoIndex2 = (VideoIndex)localIterator2.next();
                if (localVideoIndex2.index == this.targetIndex)
                {
                  Object[] arrayOfObject2 = new Object[1];
                  arrayOfObject2[0] = getTargetDateString(localVideoIndex2);
                  localXulView.setAttr("text", String.format("播放第%s期", arrayOfObject2));
                }
              }
              this.isExternalGiveEpisode = false;
            }
            else if (localObject != null)
            {
              Object[] arrayOfObject1 = new Object[1];
              arrayOfObject1[0] = getTargetDateString(localObject);
              localXulView.setAttr("text", String.format("播放第%s期", arrayOfObject1));
            }
            else
            {
              localXulView.setAttr("text", "播放");
            }
          }
        }
        else if (this.isExternalGiveEpisode)
        {
          Object[] arrayOfObject4 = new Object[1];
          arrayOfObject4[0] = Integer.valueOf(this.targetIndex);
          localXulView.setAttr("text", String.format("播放第%d集", arrayOfObject4));
          this.isExternalGiveEpisode = false;
        }
        else if (i == -1)
        {
          localXulView.setAttr("text", "播放");
        }
        else
        {
          Object[] arrayOfObject3 = new Object[1];
          arrayOfObject3[0] = Integer.valueOf(i + 1);
          localXulView.setAttr("text", String.format("播放第%d集", arrayOfObject3));
        }
      }
  }

  private void processStarInfo(StarInfoCollect paramStarInfoCollect)
  {
    this.pageCount.getAndDecrement();
    if (paramStarInfoCollect != null)
      if (("0".equals(paramStarInfoCollect.getState())) && (paramStarInfoCollect.getStarInfo().size() > 0))
      {
        this.starInfoCollect = paramStarInfoCollect;
        inTurnRefreshBinding("star", "file:///.app/api/get_star");
      }
    while (true)
    {
      checkApiFinished();
      return;
      removeMenuInfo("m_open_detail_page_star");
      continue;
      removeMenuInfo("m_open_detail_page_star");
    }
  }

  private void processTokenStatus(int paramInt, String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    do
    {
      do
        return;
      while (!GlobalLogic.getInstance().isUserLogined());
      if (paramString.equals("kicked"))
      {
        showTokenDialog(20001, false);
        return;
      }
    }
    while ((!paramString.equals("expired")) || (paramInt == 0));
    showTokenDialog(20000, false);
  }

  private void processUserAuthLogic(UserAuthV2 paramUserAuthV2)
  {
    if ((paramUserAuthV2 == null) || (paramUserAuthV2.state == null))
      return;
    this.userAuth = paramUserAuthV2;
    this.userAuthState = paramUserAuthV2.state.state;
    String str1 = "normal";
    this.qualityCanPlay = "";
    this.canPreview = "0";
    this.isUseCoupon = false;
    this.asset_type = 2;
    if (paramUserAuthV2.list != null)
    {
      Iterator localIterator = paramUserAuthV2.list.iterator();
      while (localIterator.hasNext())
      {
        UserKey localUserKey = (UserKey)localIterator.next();
        if (localUserKey != null)
          if (localUserKey.key.equals("charge_list"))
          {
            String str2 = localUserKey.value;
            Logger.i("DetailPageActivity", "lockChargeList=" + str2);
            if (!TextUtils.isEmpty(str2))
              this.lockChargeListNum = str2.split(",");
            else
              this.lockChargeListNum = null;
          }
          else if ("token_status".equals(localUserKey.key))
          {
            str1 = localUserKey.value;
          }
          else if (localUserKey.key.equals("preview"))
          {
            if (!TextUtils.isEmpty(localUserKey.value))
              this.canPreview = localUserKey.value;
            Logger.i("DetailPageActivity", "canPreview=" + this.canPreview + ",value=" + localUserKey.value);
          }
          else if ("quality".equals(localUserKey.key))
          {
            this.qualityCanPlay = localUserKey.value;
            if (!TextUtils.isEmpty(this.qualityCanPlay))
              this.qualityCanPlay = this.qualityCanPlay.toUpperCase();
            Logger.i("DetailPageActivity", "3A qualityCanPlay=" + this.qualityCanPlay);
          }
          else if ("import_id".equals(localUserKey.key))
          {
            this.import_id = localUserKey.value;
            Logger.i("DetailPageActivity", "3A import_id=" + this.import_id);
          }
      }
    }
    if (this.userAuthState == 1)
    {
      if (paramUserAuthV2.list == null)
        break label467;
      GlobalLogic.getInstance().setProductList(paramUserAuthV2.list);
      saveMediaInfo(paramUserAuthV2.list);
    }
    while (true)
    {
      this.isAuth = true;
      dismissLoaddingDialog();
      processPlayButtonText();
      processBuyButtonShow();
      processLockEpisode();
      processTokenStatus(this.userAuthState, str1);
      return;
      label467: GlobalLogic.getInstance().setProductList(null);
    }
  }

  private void processVideoIndexList(FilmListPageInfo paramFilmListPageInfo)
  {
    this.videoListInterfaceFinish = true;
    this.videoIndexes = paramFilmListPageInfo.getFilmInfo();
    this.indexOrder = paramFilmListPageInfo.getIndex_order();
    if (!TextUtils.isEmpty(this.indexOrder))
      this.indexOrder = this.indexOrder.toLowerCase();
    StringBuilder localStringBuilder = new StringBuilder().append("videoIndex");
    if (this.videoIndexes == null);
    for (Object localObject = "null"; ; localObject = Integer.valueOf(this.videoIndexes.size()))
    {
      Logger.d("allen", localObject);
      processVideoIndexListShow();
      return;
    }
  }

  private void processVideoIndexListShow()
  {
    if (this.videoInfo == null)
      Logger.e("DetailPageActivity", "影片详情暂未获取成功");
    do
    {
      return;
      if (!this.needVideoListInfo)
      {
        removeMenuInfo("m_open_detail_page_index");
        Logger.e("DetailPageActivity", "不需要影片分集");
        return;
      }
      if (this.videoListInterfaceError)
      {
        Logger.e("DetailPageActivity", "获取影片剧集接口出错");
        removeMenuInfo("m_open_detail_page_index");
        popErrorInfo("获取影片剧集接口出错", null, null);
        return;
      }
    }
    while (!this.videoListInterfaceFinish);
    processPlayButtonText();
    if ((this.videoIndexes == null) || (this.videoIndexes.size() == 0))
    {
      removeMenuInfo("m_open_detail_page_index");
      popErrorInfo("剧集返回数为0或剧集异常", null, null);
      return;
    }
    this.videoInfo.indexList = this.videoIndexes;
    Logger.d("DetailPageActivity", "displayStyle=" + this.displayStyle + ",viewType=" + this.viewType + ",is_show_index=" + this.videoInfo.is_show_index);
    if (this.viewType == 0)
      if (this.videoInfo.is_show_index == 0)
        removeMenuInfo("m_open_detail_page_index");
    while (true)
    {
      this.indexOK = true;
      checkApiFinished();
      return;
      refreshBindingEpisodeData();
      continue;
      refreshBindingEpisodeData();
    }
  }

  private void refreshBindingEpisodeData()
  {
    // Byte code:
    //   0: aload_0
    //   1: iconst_0
    //   2: putfield 233	com/starcor/hunan/DetailPageActivity:videoListInterfaceFinish	Z
    //   5: aload_0
    //   6: ldc_w 1895
    //   9: ldc_w 1897
    //   12: invokespecial 374	com/starcor/hunan/DetailPageActivity:inTurnRefreshBinding	(Ljava/lang/String;Ljava/lang/String;)V
    //   15: aload_0
    //   16: getfield 195	com/starcor/hunan/DetailPageActivity:displayStyle	Lcom/starcor/hunan/DetailPageActivity$DisplayStyle;
    //   19: getstatic 649	com/starcor/hunan/DetailPageActivity$DisplayStyle:WATCH_FOCUS	Lcom/starcor/hunan/DetailPageActivity$DisplayStyle;
    //   22: if_acmpne +14 -> 36
    //   25: aload_0
    //   26: ldc_w 1899
    //   29: ldc_w 1901
    //   32: invokespecial 374	com/starcor/hunan/DetailPageActivity:inTurnRefreshBinding	(Ljava/lang/String;Ljava/lang/String;)V
    //   35: return
    //   36: aload_0
    //   37: getfield 195	com/starcor/hunan/DetailPageActivity:displayStyle	Lcom/starcor/hunan/DetailPageActivity$DisplayStyle;
    //   40: getstatic 636	com/starcor/hunan/DetailPageActivity$DisplayStyle:NUMBER_EPISODE	Lcom/starcor/hunan/DetailPageActivity$DisplayStyle;
    //   43: if_acmpne +21 -> 64
    //   46: aload_0
    //   47: new 1903	com/starcor/hunan/DetailPageActivity$13
    //   50: dup
    //   51: aload_0
    //   52: ldc_w 1905
    //   55: ldc 159
    //   57: invokespecial 1906	com/starcor/hunan/DetailPageActivity$13:<init>	(Lcom/starcor/hunan/DetailPageActivity;Ljava/lang/String;Ljava/lang/String;)V
    //   60: invokespecial 1345	com/starcor/hunan/DetailPageActivity:inTurnRefreshBinding	(Lcom/starcor/hunan/DetailPageActivity$RefreshBindingRunnable;)V
    //   63: return
    //   64: aload_0
    //   65: getfield 195	com/starcor/hunan/DetailPageActivity:displayStyle	Lcom/starcor/hunan/DetailPageActivity$DisplayStyle;
    //   68: getstatic 641	com/starcor/hunan/DetailPageActivity$DisplayStyle:WATCH_FOCUS_DATE	Lcom/starcor/hunan/DetailPageActivity$DisplayStyle;
    //   71: if_acmpeq +13 -> 84
    //   74: aload_0
    //   75: getfield 195	com/starcor/hunan/DetailPageActivity:displayStyle	Lcom/starcor/hunan/DetailPageActivity$DisplayStyle;
    //   78: getstatic 644	com/starcor/hunan/DetailPageActivity$DisplayStyle:WATCH_FOCUS_EPISODE	Lcom/starcor/hunan/DetailPageActivity$DisplayStyle;
    //   81: if_acmpne -46 -> 35
    //   84: aload_0
    //   85: ldc_w 1908
    //   88: ldc_w 1910
    //   91: invokespecial 374	com/starcor/hunan/DetailPageActivity:inTurnRefreshBinding	(Ljava/lang/String;Ljava/lang/String;)V
    //   94: return
  }

  private void refreshPlayedState(ArrayList<Integer> paramArrayList)
  {
    int i = UserCPLLogic.getInstance().getLastPlayRecord(this.videoId);
    paramArrayList.add(0, Integer.valueOf(i));
    xulFireEvent("appEvents:checkPlayedEpisode", paramArrayList.toArray());
    Logger.i("DetailPageActivity", "上次播放的集数是" + (i + 1));
  }

  private void refreshPreviewPlayedState(ArrayList<Integer> paramArrayList)
  {
    int i = UserCPLLogic.getInstance().getLastPlayRecord(this.videoId);
    paramArrayList.add(0, Integer.valueOf(i));
    xulFireEvent("appEvents:checkPreviewPlayedEpisode", paramArrayList.toArray());
    Logger.i("DetailPageActivity", "上次播放的片花集数是" + (i + 1));
  }

  private void refreshTraceOrCollectState()
  {
    if ((this.viewType != 0) && (1 + this.videoInfo.indexCurrent < this.videoInfo.indexCount) && (AppFuncCfg.FUNCTION_ENABLE_TRACEPLAY))
    {
      ServerApiManager.i().APIGetCatchVideoRecordV2(new SccmsApiGetCatchVideoRecordV2Task.ISccmsApiGetCatchVideoRecordV2TaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<CollectListItem> paramAnonymousArrayList)
        {
          UserCPLLogic.getInstance().dirtyTracePlayList();
          UserCPLLogic.getInstance().refreshTracePlayList(paramAnonymousArrayList);
          boolean bool = UserCPLLogic.getInstance().isTracePlayExists(DetailPageActivity.this.videoId);
          if (bool);
          for (String str = "取消追剧"; ; str = "追剧")
          {
            Logger.d("DetailPageActivity", "当前影片追剧状态 isTraced=" + bool + " state=" + str);
            DetailPageActivity.this.xulFireEvent("appEvents:actionSuccess", new String[] { str });
            return;
          }
        }
      });
      return;
    }
    ServerApiManager.i().APIGetCollectRecordV2(new SccmsApiGetCollectRecordV2Task.ISccmsApiGetCollectRecordV2TaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        UserCPLLogic.getInstance().dirtyCollectList();
        UserCPLLogic.getInstance().refreshCollectList(paramAnonymousArrayList);
        boolean bool = UserCPLLogic.getInstance().isCollectExists(DetailPageActivity.this.videoId);
        if (bool);
        for (String str = "取消收藏"; ; str = "收藏")
        {
          Logger.d("DetailPageActivity", "当前影片收藏状态isCollected=" + bool + " state=" + str);
          DetailPageActivity.this.xulFireEvent("appEvents:actionSuccess", new String[] { str });
          return;
        }
      }
    });
  }

  private void removeMenuInfo(String paramString)
  {
    for (int i = -1 + this.menuArrayList.size(); i >= 0; i--)
      if (paramString.equals(((Menu)this.menuArrayList.get(i)).action))
        this.menuArrayList.remove(i);
    Logger.d("allen", "removeMenuInfo() menuArrayList.size() " + this.menuArrayList.size());
  }

  private void reportBuy()
  {
    VodPayInfo localVodPayInfo = new VodPayInfo();
    localVodPayInfo.vid = this.videoInfo.videoId;
    localVodPayInfo.ovid = this.videoInfo.import_id;
    localVodPayInfo.plid = this.videoInfo.videoId;
    localVodPayInfo.oplid = this.videoInfo.import_id;
    if ((this.videoIndexes != null) && (this.videoIndexes.size() > 0));
    for (localVodPayInfo.def = getMediaQuality(((VideoIndex)this.videoIndexes.get(0)).index); ; localVodPayInfo.def = GlobalLogic.getInstance().getQuality())
    {
      localVodPayInfo.cid = this.videoInfo.packageId;
      PayReportHelper.reportBuy(this.curPageInfo.page, this.lastPageInfo.page, localVodPayInfo, null);
      return;
    }
  }

  private void reportShowRecom(String paramString, HashMap<String, String> paramHashMap)
  {
    paramHashMap.put("pos", "");
    paramHashMap.put("page", "0");
    paramHashMap.put("hitid", "");
    paramHashMap.put("ohitid", "");
    paramHashMap.put("sohitid", "");
    reportShowRecommend(paramString, paramHashMap);
  }

  private void restoreClickState()
  {
    this.isClickPreview = false;
    this.isClickEpisode = false;
    this.isClickBuy = false;
  }

  private void saveMediaInfo(ArrayList<UserKey> paramArrayList)
  {
    label11: Iterator localIterator;
    if ((paramArrayList == null) || (paramArrayList.size() == 0))
      return;
    else
      localIterator = paramArrayList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        break label11;
      UserKey localUserKey = (UserKey)localIterator.next();
      if (localUserKey == null)
        break;
      if (localUserKey.key.equals("asset_type"));
      try
      {
        if ((!TextUtils.isEmpty(localUserKey.value)) && (!"asset_type".equals(localUserKey.value)))
        {
          this.asset_type = Integer.valueOf(localUserKey.value).intValue();
          if (!localUserKey.key.equals("coupon"));
        }
      }
      catch (Exception localException1)
      {
        try
        {
          this.coupon_count = Integer.valueOf(localUserKey.value).intValue();
          if (localUserKey.key.equals("low_price"))
            this.low_price = localUserKey.value;
          if ((localUserKey.key.equals("sale_info")) && (!TextUtils.isEmpty(localUserKey.value)) && (!"sale_info".equals(localUserKey.value)))
            this.sale_info = localUserKey.value;
          if (!localUserKey.key.equals("vip_list"))
            continue;
          localObject = localUserKey.value;
        }
        catch (Exception localException1)
        {
          try
          {
            while (true)
            {
              String str = URLDecoder.decode((String)localObject, "utf-8");
              Object localObject = str;
              if ((TextUtils.isEmpty((CharSequence)localObject)) || ("null".equalsIgnoreCase((String)localObject)) || ("[]".equals(localObject)))
                break;
              this.medie_type = 2;
              break;
              this.asset_type = 2;
              continue;
              localException2 = localException2;
              this.asset_type = 2;
              localException2.printStackTrace();
            }
            localException1 = localException1;
            localException1.printStackTrace();
          }
          catch (UnsupportedEncodingException localUnsupportedEncodingException)
          {
            while (true)
              localUnsupportedEncodingException.printStackTrace();
          }
        }
      }
    }
  }

  private void selectTargetIndex(ArrayList<Integer> paramArrayList)
  {
    paramArrayList.add(0, Integer.valueOf(this.targetIndex));
    xulFireEvent("appEvents:checkPlayedEpisode", paramArrayList.toArray());
    Logger.i("DetailPageActivity", "目标集数是" + (1 + this.targetIndex));
  }

  private void setEpisodeDisplay(DisplayStyle paramDisplayStyle)
  {
    XulArea localXulArea = (XulArea)xulFindViewById("content_page");
    if (localXulArea == null)
      return;
    XulPage localXulPage = localXulArea.getOwnerPage();
    Object localObject;
    if (paramDisplayStyle == DisplayStyle.WATCH_FOCUS)
      localObject = localXulPage.findItemById(localXulArea, "watch_focus_episode");
    while (true)
    {
      setEpisodeDisplay((XulView)localObject);
      return;
      if (paramDisplayStyle == DisplayStyle.NUMBER_EPISODE)
      {
        XulView localXulView = xulFindViewById("number_episode");
        localObject = localXulView;
        updateNumberEpisode(0, localXulView);
      }
      else if (paramDisplayStyle != DisplayStyle.WATCH_FOCUS_DATE)
      {
        DisplayStyle localDisplayStyle = DisplayStyle.WATCH_FOCUS_EPISODE;
        localObject = null;
        if (paramDisplayStyle != localDisplayStyle);
      }
      else
      {
        localObject = localXulPage.findItemById(localXulArea, "watch_focus_date_episode");
      }
    }
  }

  private void setEpisodeDisplay(XulView paramXulView)
  {
    if (paramXulView != null)
    {
      paramXulView.setStyle("display", "block");
      paramXulView.resetRender();
    }
  }

  private void setMediaPic()
  {
    String str1 = this.videoInfo.mark;
    if (str1 != null)
    {
      String str2 = str1.toUpperCase();
      if (str2.contains("SD"))
      {
        XulView localXulView2 = xulFindViewById("hd");
        if (localXulView2 != null)
        {
          localXulView2.setStyle("display", "block");
          localXulView2.resetRender();
        }
      }
      if (str2.contains("4K"))
      {
        XulView localXulView1 = xulFindViewById("4k");
        if (localXulView1 != null)
        {
          localXulView1.setStyle("display", "block");
          localXulView1.resetRender();
        }
      }
    }
  }

  private void setNumberEpisodeFocus(final int paramInt)
  {
    XulView localXulView = xulFindViewById("number_episode");
    if (localXulView != null)
    {
      final XulMassiveAreaWrapper localXulMassiveAreaWrapper = XulMassiveAreaWrapper.fromXulView(localXulView);
      int i = paramInt / 25;
      localXulMassiveAreaWrapper.makeChildVisible(localXulView.findParentByType("slider"), i * 25, 0.0F, 0.0F, false, new Runnable()
      {
        public void run()
        {
          XulView localXulView = localXulMassiveAreaWrapper.getItemView(paramInt);
          DetailPageActivity.this.xulRequestFocus(localXulView);
        }
      });
    }
  }

  private void setNumberEpisodeIndicator(int paramInt1, int paramInt2)
  {
    XulView localXulView = xulFindViewById("number_indicator");
    Logger.d("pageCount" + paramInt1 + " currentPage" + paramInt2);
    if (paramInt1 > 0)
    {
      if (paramInt1 == 1)
        localXulView.setStyle("display", "none");
    }
    else
      return;
    if (paramInt1 - 1 == paramInt2)
    {
      localXulView.setStyle("display", "block");
      localXulView.addClass("indicator_up");
      localXulView.removeClass("indicator_down");
    }
    while (true)
    {
      localXulView.resetRender();
      return;
      if (paramInt1 - 1 > paramInt2)
      {
        localXulView.setStyle("display", "block");
        localXulView.addClass("indicator_down");
        localXulView.removeClass("indicator_up");
      }
      else
      {
        localXulView.setStyle("display", "none");
      }
    }
  }

  private void setShowValue(XulArea paramXulArea, XulPage paramXulPage)
  {
    this.isUseCoupon = GlobalLogic.getInstance().isCanUseMovieCoupon(this.coupon_count, this.asset_type);
    this.buyButtonImage = paramXulPage.findItemById(paramXulArea, "buy_button_image");
    this.buyButtonValue = paramXulPage.findItemById(paramXulArea, "buy_button_value");
    this.detailTip = paramXulPage.findItemById("detail_tip");
    if (this.isUseCoupon)
    {
      this.button_str = "用券观看";
      if (this.coupon_count > 0)
        this.button_str_tip = (this.coupon_count + "张观影券可用");
      if (this.asset_type == 1)
      {
        if (this.coupon_count > 0)
          this.button_str_tip = (this.coupon_count + "张观影券可用");
      }
      else
      {
        if (this.buyButtonImage != null)
        {
          if (this.buyButtonImage.hasClass("buy_button"))
            this.buyButtonImage.removeClass("buy_button");
          this.buyButtonImage.addClass("coupon_button");
          this.buyButtonImage.resetRender();
        }
        label192: if (this.detailTip != null)
        {
          this.detailTip.setAttr("text", this.sale_info);
          if (TextUtils.isEmpty(this.sale_info))
            break label483;
          this.detailTip.setStyle("display", "block");
        }
      }
    }
    while (true)
    {
      this.detailTip.resetRender();
      if (this.buyButtonValue != null)
      {
        this.buyButtonValue.setAttr("text", this.button_str);
        this.buyButtonValue.setStyle("display", "block");
        this.buyButtonValue.resetRender();
      }
      return;
      if ((GlobalLogic.getInstance().isVip()) && (this.coupon_count <= 0))
      {
        this.button_str_tip = "本片仅能用券观看";
        break;
      }
      if ((TextUtils.isEmpty(this.low_price)) || (this.low_price.equals("0")))
        break;
      this.button_str_tip = ("用券最低" + this.low_price + "元观看");
      break;
      this.button_str = "开通观看";
      if ((!TextUtils.isEmpty(this.low_price)) && (!this.low_price.equals("0")))
        this.button_str_tip = ("用券最低" + this.low_price + "元观看");
      if (this.buyButtonImage == null)
        break label192;
      if (this.buyButtonImage.hasClass("coupon_button"))
        this.buyButtonImage.removeClass("coupon_button");
      this.buyButtonImage.addClass("buy_button");
      this.buyButtonImage.resetRender();
      break label192;
      label483: this.detailTip.setStyle("display", "none");
    }
  }

  private void setTargetMenuSelect(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      XulArrayList localXulArrayList = xulFindViewsByClass("menu_bar");
      if ((localXulArrayList != null) && (localXulArrayList.size() > 0))
      {
        Iterator localIterator = localXulArrayList.iterator();
        if (localIterator.hasNext())
        {
          XulView localXulView = (XulView)localIterator.next();
          if (paramString.equals(localXulView.getDataString("type")))
            localXulView.addClass("menu_bar_selected");
          while (true)
          {
            localXulView.resetRender();
            break;
            localXulView.removeClass("menu_bar_selected");
          }
        }
      }
    }
  }

  private void setTargetNumberEpisodeFocus(int paramInt)
  {
    for (int i = 0; ; i++)
      if (i < this.videoIndexes.size())
      {
        VideoIndex localVideoIndex = (VideoIndex)this.videoIndexes.get(i);
        if (localVideoIndex.index == paramInt)
        {
          Logger.d("找到目标集数，剧集为" + localVideoIndex.index + " 位置为" + i);
          setNumberEpisodeFocus(i);
        }
      }
      else
      {
        return;
      }
  }

  private void setTitleText(String paramString)
  {
    XulView localXulView = xulFindViewById("main_title");
    if (localXulView != null)
    {
      localXulView.setAttr("text", paramString);
      localXulView.resetRender();
    }
  }

  private void showCouponDialog()
  {
    final MovieCouponDialog localMovieCouponDialog = new MovieCouponDialog(this.context, "观看本片需要使用1张观影券，兑换成功后您可以在观影券有效期内任意观看。", "影片兑换成功，请您在观影券有效期内观看，逾期需重新兑换或付费。", "确定兑换", "立即观看");
    localMovieCouponDialog.setMovieCouponTipDialogListener(new MovieCouponDialog.MovieCouponTipDialogListener()
    {
      public void onCancel()
      {
      }

      public void onOkButtonClick()
      {
        localMovieCouponDialog.startUserCoupon();
      }
    });
    localMovieCouponDialog.setMovieCouponOkDialogListener(new MovieCouponDialog.MovieCouponOkDialogListener()
    {
      public void onCancel()
      {
        DetailPageActivity.access$3302(DetailPageActivity.this, "0");
        DetailPageActivity.this.getUserReAuth();
      }

      public void onOkButtonClick()
      {
        DetailPageActivity.access$3202(DetailPageActivity.this, false);
        DetailPageActivity.access$3302(DetailPageActivity.this, "0");
        DetailPageActivity.this.openVideoFromPlayButton();
      }
    });
    localMovieCouponDialog.showCouponTipDialog(this.import_id);
  }

  private void showErrorDialogAndReport(int paramInt, String paramString1, String paramString2, ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
  {
    if (!this.isShowDialog)
    {
      showErrorDialogWithReport(paramInt, paramString1, paramString2, paramServerApiTaskInfo, paramServerApiCommonError);
      this.isShowDialog = true;
    }
  }

  private void showLockDialog(final int paramInt)
  {
    Logger.i("showLockDialog 剧集index =" + paramInt);
    new XULDialog(this, "lockTipDialog", null)
    {
      protected boolean xulDoAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
      {
        if (paramAnonymousString2.equals("lock_click_ok"))
        {
          DetailPageActivity.this.startLoginOrPurchaseProcess(paramInt);
          dismiss();
        }
        return false;
      }
    }
    .show();
  }

  private void startLoginOrPurchaseProcess(int paramInt)
  {
    restoreClickState();
    this.isClickBuy = true;
    if (paramInt < 0)
      paramInt = 0;
    this.buyIndex = paramInt;
    Intent localIntent = new Intent(this, XULActivity.class);
    String str = GlobalLogic.getInstance().getWebToken();
    PurchaseParam localPurchaseParam = new PurchaseParam(true, this.videoId, "0");
    localPurchaseParam.setImport_id(this.import_id);
    localPurchaseParam.setSerial_id(this.videoInfo.serial_id);
    localPurchaseParam.setMedia_type(this.medie_type);
    localPurchaseParam.setAsset_type(this.asset_type);
    localPurchaseParam.setPackageId(this.packageId);
    localPurchaseParam.setCategoryId(this.categoryId);
    localPurchaseParam.setIndex(paramInt);
    if (this.videoInfo != null)
    {
      localPurchaseParam.setVideoName(this.videoInfo.name);
      localPurchaseParam.setPackageId(this.videoInfo.packageId);
      localPurchaseParam.setCategoryId(this.videoInfo.categoryId);
    }
    localPurchaseParam.setPagename(this.curPageInfo.page);
    Logger.i("详情剧集index=" + paramInt);
    GlobalLogic.getInstance().setAutoJumpToDetailedPage(true);
    if (TextUtils.isEmpty(str))
    {
      GlobalLogic.getInstance().setPurchaseParam(localPurchaseParam);
      if (DeviceInfo.isXiaoMi())
      {
        XiaoMiOAuthManager.getInstance().startLogin(this, "PurchaseVIP", new XiaoMiOAuthManager.IXiaoMiLoginMgtv()
        {
          public void onError()
          {
            DetailPageActivity.this.getUserReAuth();
          }

          public void onSuccess()
          {
            DetailPageActivity.this.getUserReAuth();
          }
        });
        return;
      }
      localIntent.putExtra("xulPageId", "LoginAndRegistration");
    }
    while (true)
    {
      localIntent.addFlags(8388608);
      startActivity(localIntent);
      return;
      if (!this.isUseCoupon)
      {
        localIntent.putExtra("xulPageId", "PurchaseVIP");
        GlobalLogic.getInstance().setPurchaseParam(localPurchaseParam);
      }
      else
      {
        if (this.coupon_count > 0)
        {
          showCouponDialog();
          return;
        }
        localIntent.putExtra("xulPageId", "PurchaseVIP");
        GlobalLogic.getInstance().setPurchaseParam(localPurchaseParam);
      }
    }
  }

  private void updateNumberEpisode(int paramInt, XulView paramXulView)
  {
    if (paramXulView != null)
    {
      XulMassiveAreaWrapper localXulMassiveAreaWrapper = XulMassiveAreaWrapper.fromXulView(paramXulView);
      int i = localXulMassiveAreaWrapper.itemNum();
      int j = 0;
      while (j < i)
      {
        XulDataNode localXulDataNode = localXulMassiveAreaWrapper.getItem(j);
        XulView localXulView1 = localXulMassiveAreaWrapper.getItemView(j);
        if (localXulView1 == null)
        {
          j++;
        }
        else
        {
          XulView localXulView2 = (XulView)localXulView1.findItemsByClass("episode_item").get(0);
          XulView localXulView3 = localXulView1.findItemById("episode_lock_img");
          int k = XulUtils.tryParseInt(localXulDataNode.getAttribute("video_index").getValue());
          if (paramInt == 0)
            if (isItemLocked(k))
            {
              localXulDataNode.setAttribute("isLocked", "true");
              if (localXulView2 != null)
              {
                localXulView2.setAttr("isLocked", "true");
                localXulView3.setAttr("img.0.visible", "true");
              }
            }
          label377: 
          while (true)
          {
            if (localXulView2 != null)
              localXulView2.resetRender();
            if (localXulView3 == null)
              break;
            localXulView3.resetRender();
            break;
            localXulDataNode.setAttribute("isLocked", "false");
            localXulDataNode.setAttribute("name", String.valueOf(k + 1));
            if (localXulView2 != null)
            {
              localXulView2.setAttr("text", String.valueOf(k + 1));
              localXulView2.setAttr("isLocked", "false");
              localXulView3.setAttr("img.0.visible", "false");
              continue;
              if (paramInt == 1)
              {
                boolean bool = isItemPlayed(k);
                localXulDataNode.setAttribute("isPlayed", String.valueOf(bool));
                if (bool)
                {
                  localXulDataNode.setAttribute("text-color", "ff5e6281");
                  if (localXulView2 != null)
                  {
                    localXulView2.setStyle("font-color", "ff5e6281");
                    localXulView2.setAttr("isPlayed", "true");
                  }
                }
                while (true)
                {
                  if ((localXulView2 == null) || (!xulHasFocus(localXulView1)))
                    break label377;
                  localXulView2.setStyle("font-color", "ffffffff");
                  break;
                  localXulDataNode.setAttribute("text-color", "7fffffff");
                  if (localXulView2 != null)
                  {
                    localXulView2.setStyle("font-color", "7fffffff");
                    localXulView2.setAttr("isPlayed", "false");
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  private void videoInfoLogic(VideoInfo paramVideoInfo)
  {
    this.videoInfo = paramVideoInfo;
    if (!TextUtils.isEmpty(paramVideoInfo.index_ui_type))
      this.episodeDisplayStyle = paramVideoInfo.index_ui_type;
    this.viewType = paramVideoInfo.view_type;
    judgeDisplayStyle();
    getMenuArrayList();
    inTurnRefreshBinding("category", "file:///.app/api/get_category");
    Logger.d("DetailPageActivity", "详情之前未刷新category，可以再次刷新");
    initExternalData();
    Logger.d("DetailPageActivity", "详情获取到的viewType=" + this.viewType + " 剧集列表展示样式=" + this.episodeDisplayStyle);
    inTurnRefreshBinding("video_info", "file:///.app/api/get_video_info");
  }

  public void dealKeyEvent(KeyEvent paramKeyEvent)
  {
    Logger.i("DetailPageActivity", "dealKeyEvent " + paramKeyEvent.getKeyCode());
    if ((paramKeyEvent.getKeyCode() == 20) && (this.displayStyle == DisplayStyle.NUMBER_EPISODE))
    {
      XulView localXulView1 = xulGetFocus();
      if (localXulView1 == null);
      XulMassiveAreaWrapper localXulMassiveAreaWrapper;
      int i;
      do
      {
        return;
        XulView localXulView2 = xulFindViewById("number_episode");
        if ((localXulView2 == null) || (!localXulView1.hasClass("episode_item")))
          break;
        localXulMassiveAreaWrapper = XulMassiveAreaWrapper.fromXulView(localXulView2);
        if (localXulMassiveAreaWrapper == null)
          break;
        i = localXulMassiveAreaWrapper.itemNum();
      }
      while (i == 0);
      int j = localXulMassiveAreaWrapper.getItemIdx(localXulView1.findParentByClass("episode_item_area")) / 5;
      if ((i + 4) / 5 == j + 2)
      {
        xulRequestFocus((XulView)localXulMassiveAreaWrapper.getItemView(i - 1).findItemsByClass("episode_item").get(0));
        return;
      }
    }
    super.dealKeyEvent(paramKeyEvent);
  }

  public void getUserReAuth()
  {
    GetUserAuthV2Params localGetUserAuthV2Params = new GetUserAuthV2Params(this.videoId, String.valueOf(this.videoType), GlobalLogic.getInstance().getQuality());
    ServerApiManager.i().APIGetUserAuthV2(localGetUserAuthV2Params, new SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.i("DetailPageActivity", "SccmsApiGetUserAuthV2TaskListener.onError() error:" + paramAnonymousServerApiCommonError.toString());
        DetailPageActivity.access$202(DetailPageActivity.this, false);
        DetailPageActivity localDetailPageActivity = DetailPageActivity.this;
        String[] arrayOfString = new String[2];
        arrayOfString[0] = DetailPageActivity.this.videoId;
        arrayOfString[1] = ("packetid=" + DetailPageActivity.this.packageId + "&categoryid=" + DetailPageActivity.this.categoryId + "&videoid=" + DetailPageActivity.this.videoId + "&videoType=" + DetailPageActivity.this.videoType + "&pay=" + "" + "&ext=" + DetailPageActivity.this.reportExt);
        localDetailPageActivity.reportLoad(false, arrayOfString);
        DetailPageActivity.this.showErrorDialogAndReport(11, "1002008", "SccmsApiGetUserAuthV2TaskListener.onError()", paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserAuthV2 paramAnonymousUserAuthV2)
      {
        Logger.i("DetailPageActivity", "SccmsApiGetUserAuthV2TaskListener.onSuccess() result:" + paramAnonymousUserAuthV2.toString());
        DetailPageActivity.access$202(DetailPageActivity.this, true);
        if (paramAnonymousUserAuthV2.state.state == 0);
        for (String str = "0"; ; str = "1")
        {
          DetailPageActivity localDetailPageActivity = DetailPageActivity.this;
          String[] arrayOfString = new String[2];
          arrayOfString[0] = DetailPageActivity.this.videoId;
          arrayOfString[1] = ("packetid=" + DetailPageActivity.this.packageId + "&categoryid=" + DetailPageActivity.this.categoryId + "&videoid=" + DetailPageActivity.this.videoId + "&videoType=" + DetailPageActivity.this.videoType + "&pay=" + str + "&ext=" + DetailPageActivity.this.reportExt);
          localDetailPageActivity.reportLoad(true, arrayOfString);
          if (!DetailPageActivity.this.isEmptyRecommend)
            DetailPageActivity.this.reportShowRecom(RecommendColumn.DETAIL_RECOMMEND_REGION, DetailPageActivity.this.recommendReportData);
          DetailPageActivity.this.processUserAuthLogic(paramAnonymousUserAuthV2);
          return;
        }
      }
    });
  }

  public void initExternalData()
  {
    setMediaPic();
    processMenuInfo(0);
    processMenuInfo(1);
    if ((TextUtils.isEmpty(this.packageId)) || (TextUtils.isEmpty(this.categoryId)))
    {
      ServerApiManager.i().APIGetAssetsByVideoId(this.videoId, new SccmsApiGetAssetsId(null));
      return;
    }
    getRelevantFilms();
  }

  public void onBackPressed()
  {
    if (this.isFromOut)
    {
      AppKiller.getInstance().killApp();
      return;
    }
    super.onBackPressed();
  }

  protected void onCreate(Bundle paramBundle)
  {
    String str = getIntent().getStringExtra("cmd_is_from_out");
    Logger.d("DetailPageActivity", "DetailPageActivity--->xulOnRenderIsReady, isFromOut = " + str);
    if (!TextUtils.isEmpty(str))
      this.isFromOut = true;
    this.vip_id = "";
    ReportData localReportData = (ReportData)getIntent().getParcelableExtra("reportData");
    if ((localReportData != null) && (localReportData.recommend_type != null))
    {
      this.recommend_type = localReportData.recommend_type;
      Logger.i("debug_videinfo", "recommend_type=" + this.recommend_type);
      if (!GlobalLogic.getInstance().isAppInterfaceReady())
        break label177;
      this.isApiOK = true;
      if (DeviceInfo.isXiaoMi())
        ActivityUserCheckLogic.getInstance().startXiaoMiUserCheck(this.context);
      processLoadPreLogic();
    }
    while (true)
    {
      super.onCreate(paramBundle);
      this.reportExt = getReportExt(this.pageEntraceSrc);
      return;
      this.recommend_type = "-1,-1";
      break;
      label177: showLoaddingDialog();
      App.getInstance().setOnserviceOKListener(new OnServiceOK(null));
    }
  }

  protected void onRestart()
  {
    super.onRestart();
    getUserReAuth();
  }

  protected void onResume()
  {
    super.onResume();
    if (!xulIsReady());
    while (true)
    {
      return;
      if (this.isClickEpisode)
        if (this.videoIndexes != null)
        {
          if (this.displayStyle != DisplayStyle.NUMBER_EPISODE)
            break label78;
          updateNumberEpisode(1, xulFindViewById("number_episode"));
          setTargetNumberEpisodeFocus(UserCPLLogic.getInstance().getLastPlayRecord(this.videoId));
        }
      while (this.videoInfo != null)
      {
        refreshTraceOrCollectState();
        processPlayButtonText();
        return;
        label78: ArrayList localArrayList2 = new ArrayList();
        Iterator localIterator = this.videoIndexes.iterator();
        while (localIterator.hasNext())
        {
          VideoIndex localVideoIndex = (VideoIndex)localIterator.next();
          if (UserCPLLogic.getInstance().isPlayRecordExistsIncludeLocal(this.videoId, localVideoIndex.index))
          {
            localArrayList2.add(Integer.valueOf(localVideoIndex.index));
            Logger.d("DetailPageActivity", "exist video index" + localVideoIndex.index);
          }
        }
        refreshPlayedState(localArrayList2);
        continue;
        if ((this.isClickPreview) && (this.previewList != null))
        {
          ArrayList localArrayList1 = new ArrayList();
          for (int i = 0; i < this.previewList.items.size(); i++)
          {
            PreviewList.Item localItem = (PreviewList.Item)this.previewList.items.get(i);
            if (UserCPLLogic.getInstance().isPlayRecordExistsIncludeLocal(this.videoId, localItem.videoIndex))
            {
              localArrayList1.add(Integer.valueOf(localItem.videoIndex));
              Logger.d("DetailPageActivity", "previewList exist video index" + localItem.videoIndex);
            }
          }
          refreshPreviewPlayedState(localArrayList1);
        }
      }
    }
  }

  protected void onStop()
  {
    super.onStop();
    String str;
    if (this.userAuth == null)
      str = "";
    while (true)
    {
      boolean bool = this.isLoadSuccess;
      String[] arrayOfString = new String[2];
      arrayOfString[0] = this.videoId;
      arrayOfString[1] = ("packetid=" + this.packageId + "&categoryid=" + this.categoryId + "&videoid=" + this.videoId + "&videoType=" + this.videoType + "&pay=" + str + "&ext=" + this.reportExt);
      reportExit(bool, arrayOfString);
      return;
      if (this.userAuth.state.state == 0)
        str = "0";
      else
        str = "1";
    }
  }

  public void showTokenDialog(int paramInt, boolean paramBoolean)
  {
    GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(true, this.videoId, "0"));
    TokenDialog localTokenDialog = new TokenDialog(this.context);
    localTokenDialog.setListener(null);
    localTokenDialog.setType(paramInt);
    localTokenDialog.setIsNeedQuit(paramBoolean);
    if ((paramInt == 20000) || (paramInt == 20001))
      GlobalLogic.getInstance().userLogout();
    if (DeviceInfo.isXiaoMi())
      localTokenDialog.setXiaoMiListener(new TokenDialog.XiaoMiLoginListener()
      {
        public void onSuccess()
        {
          Logger.i(" xiaomi login ok reAuthProcess");
          DetailPageActivity.this.getUserReAuth();
        }
      });
    localTokenDialog.show();
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    Logger.i("DetailPageActivity", "xulDoAction action=" + paramString1 + " type=" + paramString2 + " command" + paramString3 + " userdata=" + paramObject);
    if (("api_n36_ready".equals(paramString3)) && (GlobalLogic.getInstance().isAppInterfaceReady()))
    {
      this.isApiOK = true;
      if (DeviceInfo.isXiaoMi())
        ActivityUserCheckLogic.getInstance().startXiaoMiUserCheck(this.context);
      processLoadPreLogic();
      if (xulIsReady())
        processLoadRenderReadyLogic();
    }
    while (this.videoInfo == null)
    {
      return true;
      if ("categoryBindingReady".equals(paramString3))
      {
        XulView localXulView = xulFindViewById("content_page");
        Logger.d("allen", "content_page" + localXulView);
        if (localXulView != null)
        {
          String str1 = localXulView.getAttrString("categoryBindingState");
          Logger.d("allen", "categoryBindingState" + str1);
          if ("true".equals(str1))
          {
            this.hasRefreshCategory = true;
            xulPost(new Runnable()
            {
              public void run()
              {
                DetailPageActivity.this.checkInTurnRefreshBinding();
              }
            });
          }
          else
          {
            this.hasRefreshCategory = false;
          }
        }
      }
    }
    if ("focus".equals(paramString2))
    {
      this.focusType = paramString3;
      setTargetMenuSelect(paramString3);
      if ("B".equals(paramString3))
      {
        final XulArea localXulArea = paramXulView.findParentById("number_episode");
        if (localXulArea != null)
        {
          xulDoLayout();
          XulMassiveAreaWrapper localXulMassiveAreaWrapper = XulMassiveAreaWrapper.fromXulView(localXulArea);
          int m = localXulMassiveAreaWrapper.getItemIdx(paramXulView.findParentByClass("episode_item_area"));
          if (m < 0);
          int n = m / 25;
          localXulMassiveAreaWrapper.makeChildVisible(localXulArea.findParentByType("slider"), n * 25, 0.0F, 0.0F, false, new Runnable()
          {
            public void run()
            {
              DetailPageActivity.this.updateNumberEpisode(0, localXulArea);
            }
          });
          setNumberEpisodeIndicator((int)Math.ceil(this.videoIndexes.size() / 25.0F), n);
        }
      }
    }
    while (true)
    {
      return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
      if (paramString2.equals("menuBar"))
      {
        if ("bindingUpdated".equals(paramString3))
          xulPostDelay(new Runnable()
          {
            public void run()
            {
              DetailPageActivity.this.setTargetMenuSelect(DetailPageActivity.this.focusType);
            }
          }
          , 50L);
      }
      else
      {
        if (paramString2.equals("buy"))
        {
          reportBuy();
          startLoginOrPurchaseProcess(-1);
          return true;
        }
        if (paramString2.equals("episode_open_video"))
        {
          int k = XulUtils.tryParseInt(paramString3);
          if (isCanPlay(k))
            openVideoFromEpisode(k, false);
          else
            showLockDialog(k);
        }
        else if (paramString2.equals("preview_open_video"))
        {
          openVideoFromPreview(paramString3);
        }
        else if (paramString2.equals("movie_open_video"))
        {
          openVideoFromPlayButton();
        }
        else if (paramString2.equals("detail"))
        {
          if ("bindingUpdated".equals(paramString3))
          {
            processBuyButtonShow();
            processPlayButtonText();
          }
        }
        else
        {
          if ("poster".equals(paramString2))
          {
            Bundle localBundle2;
            label602: int i;
            if ((paramObject instanceof XulDataNode))
            {
              localBundle2 = xulArgListToBundle((XulDataNode)paramObject);
              i = Integer.parseInt(paramXulView.getAttrString("id"));
              if (i % 2 != 0)
                break label912;
            }
            label912: for (int j = 6 + i / 2; ; j = i - i / 2)
            {
              this.recommendReportData.put("pos", String.valueOf(j));
              this.recommendReportData.put("page", "0");
              this.recommendReportData.put("limit", this.recommendPageNum);
              this.recommendReportData.put("hitid", localBundle2.getString("video_id"));
              this.recommendReportData.put("ohitid", localBundle2.getString("import_id"));
              this.recommendReportData.put("sohitid", localBundle2.getString("serial_id"));
              restoreClickState();
              String str3 = localBundle2.getString("video_id");
              String str4 = localBundle2.getString("video_type");
              String str5 = localBundle2.getString("view_type");
              String str6 = localBundle2.getString("package_id");
              String str7 = localBundle2.getString("category_id");
              String str8 = localBundle2.getString("name");
              String str9 = localBundle2.getString("img_v");
              MovieData localMovieData = new MovieData();
              ReportData localReportData = new ReportData();
              localMovieData.videoId = str3;
              localMovieData.videoType = XulUtils.tryParseInt(str4);
              localMovieData.viewType = XulUtils.tryParseInt(str5);
              localMovieData.name = str8;
              localMovieData.img_v = str9;
              localMovieData.packageId = str6;
              localMovieData.categoryId = str7;
              reportClickRecommend(RecommendColumn.DETAIL_RECOMMEND_REGION, this.recommendReportData);
              startDetailPageActivity(localMovieData, localReportData);
              break;
              localBundle2 = new Bundle();
              break label602;
            }
          }
          if (paramString2.equals("open_star_detail"))
          {
            restoreClickState();
            if ((paramObject instanceof XulDataNode));
            for (Bundle localBundle1 = xulArgListToBundle((XulDataNode)paramObject); ; localBundle1 = new Bundle())
            {
              Intent localIntent = new Intent(this, StarDetailedActivity.class);
              localIntent.putExtra("actor", localBundle1.getString("actor"));
              localIntent.putExtra("actorID", localBundle1.getString("actor_id"));
              localIntent.putExtra("labelID", localBundle1.getString("label_id"));
              localIntent.putExtra("labelID_V2", localBundle1.getString("label_id_v2"));
              localIntent.addFlags(8388608);
              startActivity(localIntent);
              break;
            }
          }
          if (paramString2.equals("floatDetail"))
          {
            String str2;
            try
            {
              JSONObject localJSONObject = new JSONObject(paramString3);
              str2 = localJSONObject.optString("cmd");
              if ("deleteTrace".equals(str2))
              {
                Logger.i("DetailPageActivity", "deleteTrace");
                this.collectLogic.delTracePlay(new SuccessCallBack()
                {
                  public void getDataError(String paramAnonymousString, int paramAnonymousInt)
                  {
                    UITools.ShowCustomToast(DetailPageActivity.this.context, "取消追剧失败");
                  }

                  public void getDataSuccess(Void paramAnonymousVoid)
                  {
                    DetailPageActivity.access$2902("3");
                    DetailPageActivity.this.xulFireEvent("appEvents:actionSuccess", new String[] { "追剧" });
                    UITools.ShowCustomToast(DetailPageActivity.this.context, "取消追剧成功");
                  }
                }
                , this.videoInfo.videoId, this.videoInfo);
                return true;
              }
            }
            catch (JSONException localJSONException)
            {
              localJSONException.printStackTrace();
              return true;
            }
            if ("addTrace".equals(str2))
            {
              Logger.i("DetailPageActivity", "addTrace");
              this.collectLogic.addTracePlay(new SuccessCallBack()
              {
                public void getDataError(String paramAnonymousString, int paramAnonymousInt)
                {
                  UITools.ShowCustomToast(DetailPageActivity.this.context, "追剧失败");
                }

                public void getDataSuccess(Void paramAnonymousVoid)
                {
                  DetailPageActivity.access$2902("2");
                  DetailPageActivity.this.xulFireEvent("appEvents:actionSuccess", new String[] { "取消追剧" });
                  UITools.ShowCustomToast(DetailPageActivity.this.context, "追剧成功");
                }
              }
              , this.videoInfo);
              return true;
            }
            if ("deleteCollect".equals(str2))
            {
              Logger.i("DetailPageActivity", "deleteCollect");
              this.collectLogic.delCollect(new SuccessCallBack()
              {
                public void getDataError(String paramAnonymousString, int paramAnonymousInt)
                {
                  UITools.ShowCustomToast(DetailPageActivity.this.context, "取消收藏失败");
                }

                public void getDataSuccess(Void paramAnonymousVoid)
                {
                  DetailPageActivity.access$2902("1");
                  DetailPageActivity.this.xulFireEvent("appEvents:actionSuccess", new String[] { "收藏" });
                  UITools.ShowCustomToast(DetailPageActivity.this.context, "取消收藏成功");
                }
              }
              , this.videoInfo.videoId);
              return true;
            }
            if ("addCollect".equals(str2))
            {
              Logger.i("DetailPageActivity", "addCollect");
              this.collectLogic.addCollect(new SuccessCallBack()
              {
                public void getDataError(String paramAnonymousString, int paramAnonymousInt)
                {
                  UITools.ShowCustomToast(DetailPageActivity.this.context, "收藏失败");
                }

                public void getDataSuccess(Void paramAnonymousVoid)
                {
                  DetailPageActivity.access$2902("0");
                  DetailPageActivity.this.xulFireEvent("appEvents:actionSuccess", new String[] { "取消收藏" });
                  UITools.ShowCustomToast(DetailPageActivity.this.context, "收藏成功");
                }
              }
              , this.videoInfo);
              return true;
            }
          }
          else if (("incrementalBindingFinished".equals(paramString2)) && ("setEpisodeLock".equals(paramString1)))
          {
            processLockEpisode();
          }
        }
      }
    }
  }

  protected InputStream xulGetAppData(XulWorker.DownloadItem paramDownloadItem, String paramString)
  {
    Logger.i("DetailPageActivity", "xulGetAppData path=" + paramString);
    if ("api/get_video_info".equals(paramString))
      return getVideoInfo();
    if ("api/get_extra_info".equals(paramString))
      return getExtraInfo();
    if ("api/get_video_watch_focus".equals(paramString))
      return getVideoEpisode();
    if ("api/get_video_watch_focus_date".equals(paramString))
      return getVideoEpisode();
    if ("api/get_relevant_film".equals(paramString))
      return getRelevantFilm();
    if ("api/get_star".equals(paramString))
      return getStars();
    if ("api/get_menu".equals(paramString))
      return getMenu();
    if ("api/get_preview_film".equals(paramString))
      return getPreview();
    if ("api/get_category".equals(paramString))
      return getCategory();
    return super.xulGetAppData(paramDownloadItem, paramString);
  }

  protected void xulOnRenderIsReady()
  {
    super.xulOnRenderIsReady();
    Logger.d("DetailPageActivity", "xulOnRenderIsReady");
    if (this.isApiOK)
      processLoadRenderReadyLogic();
  }

  public static enum DisplayStyle
  {
    static
    {
      DisplayStyle[] arrayOfDisplayStyle = new DisplayStyle[5];
      arrayOfDisplayStyle[0] = MOVIE;
      arrayOfDisplayStyle[1] = NUMBER_EPISODE;
      arrayOfDisplayStyle[2] = WATCH_FOCUS;
      arrayOfDisplayStyle[3] = WATCH_FOCUS_DATE;
      arrayOfDisplayStyle[4] = WATCH_FOCUS_EPISODE;
    }
  }

  private class Menu
  {
    private String action;
    private String image;
    private String imageFocus;
    private String name;

    private Menu()
    {
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
        Logger.i("DetailPageActivity", "onApiOk");
        DetailPageActivity.this.xulRefreshBinding("api_n36");
      }
    }

    public void onGetApiDataError()
    {
      Logger.i("DetailPageActivity", "onGetApiDataError");
    }

    public void onNeedFinishActivity()
    {
      DetailPageActivity.this.finish();
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
      Logger.i("DetailPageActivity", "onServiceOK");
      new InitApiData(DetailPageActivity.this).setOnApiOkListener(new DetailPageActivity.OnApiOk(DetailPageActivity.this, null));
    }
  }

  class RefreshBindingRunnable
    implements Runnable
  {
    String bindingId;
    String url;

    public RefreshBindingRunnable(String paramString1, String arg3)
    {
      this.bindingId = paramString1;
      Object localObject;
      this.url = localObject;
    }

    public void run()
    {
      DetailPageActivity.this.xulRefreshBinding(this.bindingId, this.url);
    }
  }

  private final class SccmsApiGetAssetsId
    implements SccmsApiGetAssetsByVideoIdTask.ISccmsApiGetAssetsByVideoIdTaskListener
  {
    private SccmsApiGetAssetsId()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      DetailPageActivity.this.popErrorInfo("ISccmsApiGetAssetsByVideoIdTaskListener onError", paramServerApiTaskInfo, paramServerApiCommonError);
      DetailPageActivity.this.removeMenuInfo("m_open_detail_page_recom");
      DetailPageActivity.access$4402(DetailPageActivity.this, true);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, AssetsInfo paramAssetsInfo)
    {
      Logger.i("DetailPageActivity", "SccmsApiGetAssetsInfoByVideoId.onSuccess(), result:" + paramAssetsInfo);
      DetailPageActivity.access$402(DetailPageActivity.this, paramAssetsInfo.packageId);
      DetailPageActivity.access$502(DetailPageActivity.this, paramAssetsInfo.categoryId);
      DetailPageActivity.this.getRelevantFilms();
    }
  }

  private class SccmsApiGetPreviewListTaskListener
    implements SccmsApiGetPreviewListTask.ISccmsApiGetPreviewListTaskListener
  {
    private SccmsApiGetPreviewListTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("DetailPageActivity", "SccmsApiGetPreviewListTaskListener onError");
      DetailPageActivity.this.removeMenuInfo("m_open_detail_page_prevue");
      DetailPageActivity.this.pageCount.getAndDecrement();
      DetailPageActivity.this.checkApiFinished();
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, PreviewList paramPreviewList)
    {
      Logger.i("DetailPageActivity", "SccmsApiGetPreviewListTaskListener onSuccess");
      DetailPageActivity.this.pageCount.getAndDecrement();
      if ((paramPreviewList != null) && (paramPreviewList.items != null) && (paramPreviewList.items.size() > 0))
      {
        DetailPageActivity.access$4102(DetailPageActivity.this, paramPreviewList);
        DetailPageActivity.this.inTurnRefreshBinding("video_preview", "file:///.app/api/get_preview_film");
      }
      while (true)
      {
        DetailPageActivity.this.checkApiFinished();
        return;
        DetailPageActivity.this.removeMenuInfo("m_open_detail_page_prevue");
      }
    }
  }

  private class SccmsApiGetRelevantFilms
    implements SccmsApiGetRelevantFilmsTask.ISccmsApiGetRelevantFilmsTaskListener
  {
    private SccmsApiGetRelevantFilms()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("DetailPageActivity", "SccmsApiGetRelevantFilmsTask.ISccmsApiGetRelevantFilmsTaskListener onError");
      DetailPageActivity.this.removeMenuInfo("m_open_detail_page_recom");
      DetailPageActivity.access$4402(DetailPageActivity.this, true);
      DetailPageActivity.this.checkApiFinished();
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, FilmItem paramFilmItem)
    {
      DetailPageActivity.access$4402(DetailPageActivity.this, true);
      if ((paramFilmItem == null) || (paramFilmItem.filmList.size() == 0))
      {
        DetailPageActivity.this.removeMenuInfo("m_open_detail_page_recom");
        return;
      }
      DetailPageActivity.access$4902(DetailPageActivity.this, paramFilmItem);
      DetailPageActivity.this.buildRecommendReportData(paramFilmItem);
      DetailPageActivity.this.reportShowRecom(RecommendColumn.DETAIL_RECOMMEND_REGION, DetailPageActivity.this.recommendReportData);
      DetailPageActivity.this.inTurnRefreshBinding("related_recommended", "file:///.app/api/get_relevant_film");
      DetailPageActivity.this.checkApiFinished();
    }
  }

  private class SccmsApiGetStarCollectDataTaskListener
    implements SccmsApiGetStarCollectDataTask.ISccmsApiGetStarCollectDataTaskListener
  {
    private SccmsApiGetStarCollectDataTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("DetailPageActivity", "SccmsApiGetStarCollectDataTaskListener onError");
      DetailPageActivity.this.removeMenuInfo("m_open_detail_page_star");
      DetailPageActivity.this.pageCount.getAndDecrement();
      DetailPageActivity.this.checkApiFinished();
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, StarInfoCollect paramStarInfoCollect)
    {
      Logger.i("DetailPageActivity", "SccmsApiGetStarCollectDataTaskListener onSuccess");
      DetailPageActivity.this.processStarInfo(paramStarInfoCollect);
    }
  }

  class SccmsApiGetUserAuthV2TaskListener
    implements SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener
  {
    SccmsApiGetUserAuthV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      DetailPageActivity.this.setEpisodeDisplay(DetailPageActivity.this.displayStyle);
      Logger.i("DetailPageActivity", "SccmsApiGetUserAuthV2TaskList        ener.onError() error:" + paramServerApiCommonError.toString());
      DetailPageActivity.access$202(DetailPageActivity.this, false);
      DetailPageActivity localDetailPageActivity = DetailPageActivity.this;
      String[] arrayOfString = new String[2];
      arrayOfString[0] = DetailPageActivity.this.videoId;
      arrayOfString[1] = ("packetid=" + DetailPageActivity.this.packageId + "&categoryid=" + DetailPageActivity.this.categoryId + "&videoid=" + DetailPageActivity.this.videoId + "&videoType=" + DetailPageActivity.this.videoType + "&pay=" + "" + "&ext=" + DetailPageActivity.this.reportExt);
      localDetailPageActivity.reportLoad(false, arrayOfString);
      DetailPageActivity.this.showErrorDialogWithReport(11, "1002008", "SccmsApiGetUserAuthV2TaskListener.onError()", paramServerApiTaskInfo, paramServerApiCommonError);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserAuthV2 paramUserAuthV2)
    {
      Logger.i("DetailPageActivity", "SccmsApiGetUserAuthV2TaskListener.onSuccess() result:" + paramUserAuthV2.toString());
      DetailPageActivity.access$202(DetailPageActivity.this, true);
      if (paramUserAuthV2.state.state == 0);
      for (String str = "0"; ; str = "1")
      {
        DetailPageActivity localDetailPageActivity = DetailPageActivity.this;
        String[] arrayOfString = new String[2];
        arrayOfString[0] = DetailPageActivity.this.videoId;
        arrayOfString[1] = ("packetid=" + DetailPageActivity.this.packageId + "&categoryid=" + DetailPageActivity.this.categoryId + "&videoid=" + DetailPageActivity.this.videoId + "&videoType=" + DetailPageActivity.this.videoType + "&pay=" + str + "&ext=" + DetailPageActivity.this.reportExt);
        localDetailPageActivity.reportLoad(true, arrayOfString);
        DetailPageActivity.this.processUserAuthLogic(paramUserAuthV2);
        return;
      }
    }
  }

  class SccmsApiGetVideoIndexListTaskListener
    implements SccmsApiGetVideoIndexListTask.ISccmsApiGetVideoIndexListTaskListener
  {
    SccmsApiGetVideoIndexListTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("DetailPageActivity", "SccmsApiGetVideoIndexListTaskListener->onError");
      DetailPageActivity.access$4602(DetailPageActivity.this, true);
      DetailPageActivity.this.processVideoIndexListShow();
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, final FilmListPageInfo paramFilmListPageInfo)
    {
      DetailPageActivity.this.xulPostDelay(new Runnable()
      {
        public void run()
        {
          DetailPageActivity.this.processVideoIndexList(paramFilmListPageInfo);
        }
      }
      , 50L);
    }
  }

  class SccmsApiGetVideoInfoV3TaskListener
    implements SccmsApiGetVideoInfoV3Task.ISccmsApiGetVideoInfoV3TaskListener
  {
    SccmsApiGetVideoInfoV3TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      DetailPageActivity.this.popErrorInfo("SccmsApiGetVideoInfoV3Task.ISccmsApiGetVideoInfoV3TaskListener onError", paramServerApiTaskInfo, paramServerApiCommonError);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, VideoInfo paramVideoInfo)
    {
      if (paramVideoInfo == null)
      {
        DetailPageActivity.this.popErrorInfo("SccmsApiGetVideoInfoV3Task.ISccmsApiGetVideoInfoV3TaskListener onSuccess result null", paramServerApiTaskInfo, null);
        return;
      }
      DetailPageActivity.this.videoInfoLogic(paramVideoInfo);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.DetailPageActivity
 * JD-Core Version:    0.6.2
 */