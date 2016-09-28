package com.starcor.media.player;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.starcor.core.domain.ChannelInfoV2;
import com.starcor.core.domain.ChannelItemInfoV2;
import com.starcor.core.domain.PlayBillItem;
import com.starcor.core.domain.PlayBillRecommendItem;
import com.starcor.core.domain.PlayBillStruct;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.domain.MergeLocalReserveAndUpload;
import com.starcor.hunan.domain.Reservation;
import com.starcor.hunan.service.ReservationService;
import com.starcor.hunan.service.SystemTimeManager;
import com.starcor.report.ReportArea;
import com.starcor.report.ReportInfo;
import com.starcor.ui.UITools;
import com.starcor.xul.IXulExternalView;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.Utils.XulMemoryOutputStream;
import com.starcor.xul.Wrapper.XulSliderAreaWrapper;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulRenderContext.IXulRenderHandler;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker;
import com.starcor.xul.XulWorker.DownloadItem;
import com.starcor.xul.XulWorker.XulDownloadOutputBuffer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class MplayerPlaybackEpisodeView extends View
{
  private static final int MSG_REQUEST_PLAY_BILL = 1001;
  private static final String TAG = MplayerPlaybackEpisodeView.class.getSimpleName();
  private static final String[] WEEKS = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
  private ChannelInfoV2 channelListInfo;
  private String currentBeginTime;
  private ChannelItemInfoV2 currentChannelInfo = null;
  private String currentDay;
  private String currentPlayingVideoId = "";
  private String currentPlayingcategoryId = "";
  private String currentProgramName = "";
  private boolean defaultMode = true;
  private XulView defaultView = null;
  private Handler delayHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
      case 1001:
      }
      do
      {
        return;
        String str = (String)paramAnonymousMessage.obj;
        MplayerPlaybackEpisodeView.this.parseChannelData(str);
      }
      while (MplayerPlaybackEpisodeView.this.lsnr == null);
      MplayerPlaybackEpisodeView.this.lsnr.onChannelItemClick(MplayerPlaybackEpisodeView.this.selectedVideoId, MplayerPlaybackEpisodeView.this.selectedCategoryId);
    }
  };
  private boolean isFirstMode = true;
  private boolean isLoading = true;
  private boolean isProgramListBindingFinish = true;
  private OnPlayBillClickListener lsnr;
  private ReservationService mReservationService;
  private String packgeid = "";
  private boolean processDelayData;
  private ArrayList<PlayBillStruct> programList;
  private Rect rect;
  private XulRenderContext renderContext;
  private boolean renderNeed2ProcessBinding;
  private Map<PlayBillItem, Reservation> reservationMap = new HashMap();
  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
  private String selectedCategoryId = "";
  private String selectedProgramId = "";
  private String selectedVideoId = "";

  public MplayerPlaybackEpisodeView(Context paramContext)
  {
    super(paramContext);
  }

  public MplayerPlaybackEpisodeView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public MplayerPlaybackEpisodeView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private void checkHasDelayData()
  {
    Logger.d("allen", "checkHasDelayData()" + this.processDelayData);
    if (this.processDelayData)
      refreshProgramBinding();
  }

  private void checkReservation(PlayBillItem paramPlayBillItem, String paramString)
  {
    if (!isShowCanPlay(paramPlayBillItem.can_play))
    {
      if (TextUtils.isEmpty(paramString))
        paramString = this.currentDay;
      long l = ReservationService.time2Reservation(paramString + paramPlayBillItem.begin, paramPlayBillItem.timeLen + (int)GlobalEnv.getInstance().getReservationDelayNotifyTime() / 1000);
      Reservation localReservation = this.mReservationService.findReservation(l, this.selectedVideoId);
      if (localReservation != null)
        this.reservationMap.put(paramPlayBillItem, localReservation);
    }
  }

  private void dealSelectItem()
  {
    XulArrayList localXulArrayList2;
    if (this.renderContext != null)
    {
      XulArrayList localXulArrayList1 = this.renderContext.findItemsByClass(new String[] { "channel_item" });
      if ((localXulArrayList1 != null) && (localXulArrayList1.size() > 0))
      {
        int i = 0;
        label151: XulView localXulView2;
        if (i < localXulArrayList1.size())
        {
          XulView localXulView3 = (XulView)localXulArrayList1.get(i);
          String str2 = localXulView3.getAttrString("id");
          if ((this.selectedVideoId.equals(str2)) && (this.currentPlayingVideoId.equals(this.selectedVideoId)))
          {
            localXulView3.addClass("channel_label_selected");
            localXulView3.resetRender();
            this.defaultView = localXulView3;
          }
        }
        else
        {
          localXulArrayList2 = this.renderContext.findItemsByClass(new String[] { "program_item" });
          boolean bool = this.currentPlayingVideoId.equals(this.selectedVideoId);
          j = 0;
          if (!bool)
            break label256;
          int m = localXulArrayList2.size();
          j = 0;
          if (i >= m)
            break label256;
          localXulView2 = (XulView)localXulArrayList2.get(i);
          if (localXulView2 != null)
            break label193;
        }
        label193: String str1;
        do
        {
          i++;
          break label151;
          i++;
          break;
          str1 = localXulView2.getAttrString("timeSrc");
        }
        while ((!localXulView2.getAttrString("daySrc").equals(this.currentDay)) || (!str1.equals(this.currentBeginTime)));
        localXulView2.addClass("program_item_selected");
        localXulView2.resetRender();
        resetProgramPos(localXulView2);
        int j = 1;
        label256: if (j != 0);
      }
    }
    for (int k = -1 + localXulArrayList2.size(); ; k--)
      if (k >= 0)
      {
        XulView localXulView1 = (XulView)localXulArrayList2.get(k);
        if (isShowCanPlay(XulUtils.tryParseInt(localXulView1.getDataString("canplay"))))
          resetProgramPos(localXulView1);
      }
      else
      {
        if ((this.isFirstMode) && (this.defaultView != null))
        {
          this.isFirstMode = false;
          this.renderContext.scheduleLayoutFinishedTask(new Runnable()
          {
            public void run()
            {
              MplayerPlaybackEpisodeView.this.requestFocus(MplayerPlaybackEpisodeView.this.defaultView);
            }
          });
        }
        return;
      }
  }

  private ChannelItemInfoV2 findChannelInfoByVodeoId(String paramString1, String paramString2)
  {
    ChannelItemInfoV2 localChannelItemInfoV2;
    if ((TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString2)) || (this.channelListInfo == null) || (this.channelListInfo.channelList == null))
    {
      localChannelItemInfoV2 = null;
      return localChannelItemInfoV2;
    }
    int i = this.channelListInfo.channelList.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        break label107;
      localChannelItemInfoV2 = (ChannelItemInfoV2)this.channelListInfo.channelList.get(j);
      if ((this.selectedVideoId.equals(localChannelItemInfoV2.id)) && (this.selectedCategoryId.equals(localChannelItemInfoV2.categoryId)))
        break;
    }
    label107: return null;
  }

  private String formatDate(String paramString)
  {
    try
    {
      Date localDate = this.sdf.parse(paramString);
      Calendar localCalendar = Calendar.getInstance();
      localCalendar.setTime(localDate);
      int i = localCalendar.get(7);
      String str1 = new SimpleDateFormat("MM月dd日").format(localDate);
      String str2 = WEEKS[(i - 1)];
      String str3 = str1 + "  " + str2;
      return str3;
    }
    catch (ParseException localParseException)
    {
      localParseException.printStackTrace();
    }
    return "01月01日    周一";
  }

  private ByteArrayInputStream getChannelList()
  {
    if ((this.channelListInfo == null) || (this.channelListInfo.channelList == null))
    {
      Logger.e(TAG, "getChannelList null");
      return null;
    }
    Logger.d(TAG, "getChannelList...");
    XmlSerializer localXmlSerializer;
    StringWriter localStringWriter;
    try
    {
      localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "root");
      localXmlSerializer.startTag(null, "channel_list");
      Iterator localIterator = this.channelListInfo.channelList.iterator();
      while (localIterator.hasNext())
      {
        ChannelItemInfoV2 localChannelItemInfoV2 = (ChannelItemInfoV2)localIterator.next();
        localXmlSerializer.startTag(null, "channel");
        writeNodeValue(localXmlSerializer, "name", localChannelItemInfoV2.name);
        writeNodeValue(localXmlSerializer, "id", localChannelItemInfoV2.id);
        writeNodeValue(localXmlSerializer, "category_id", localChannelItemInfoV2.categoryId);
        writeNodeValue(localXmlSerializer, "index", makeIdxString(localChannelItemInfoV2.channelNum));
        localXmlSerializer.endTag(null, "channel");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    localXmlSerializer.endTag(null, "channel_list");
    localXmlSerializer.endTag(null, "root");
    localXmlSerializer.endDocument();
    localXmlSerializer.flush();
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
    return localByteArrayInputStream;
  }

  private InputStream getProgramList()
  {
    if ((this.programList == null) || (this.programList.isEmpty()))
    {
      showDateTips(false);
      try
      {
        XmlSerializer localXmlSerializer1 = XmlPullParserFactory.newInstance().newSerializer();
        XulWorker.XulDownloadOutputBuffer localXulDownloadOutputBuffer = XulWorker.obtainDownloadBuffer(16384);
        localXmlSerializer1.setOutput(localXulDownloadOutputBuffer, "UTF-8");
        localXmlSerializer1.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer1.startTag(null, "root");
        localXmlSerializer1.startTag(null, "program_list");
        localXmlSerializer1.startTag(null, "none");
        writeNodeValue(localXmlSerializer1, "name", "暂无节目单");
        localXmlSerializer1.endTag(null, "none");
        localXmlSerializer1.endTag(null, "program_list");
        localXmlSerializer1.endTag(null, "root");
        localXmlSerializer1.endDocument();
        localXmlSerializer1.flush();
        InputStream localInputStream = localXulDownloadOutputBuffer.toInputStream();
        return localInputStream;
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
        return null;
      }
    }
    showDateTips(true);
    XmlSerializer localXmlSerializer2;
    StringWriter localStringWriter;
    while (true)
    {
      try
      {
        localXmlSerializer2 = XmlPullParserFactory.newInstance().newSerializer();
        localStringWriter = new StringWriter();
        localXmlSerializer2.setOutput(localStringWriter);
        localXmlSerializer2.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer2.startTag(null, "root");
        localXmlSerializer2.startTag(null, "program_list");
        this.reservationMap.clear();
        Iterator localIterator = this.programList.iterator();
        if (!localIterator.hasNext())
          break;
        PlayBillStruct localPlayBillStruct = (PlayBillStruct)localIterator.next();
        if ((localPlayBillStruct.arrPlayBill == null) || (localPlayBillStruct.arrPlayBill.isEmpty()))
          continue;
        int i = localPlayBillStruct.arrPlayBill.size();
        int j = 0;
        if (j < i)
        {
          PlayBillItem localPlayBillItem = (PlayBillItem)localPlayBillStruct.arrPlayBill.get(j);
          checkReservation(localPlayBillItem, localPlayBillStruct.day);
          localXmlSerializer2.startTag(null, "program");
          if (isShowCanPlay(localPlayBillItem.can_play))
            break label607;
          if (!this.reservationMap.containsKey(localPlayBillItem))
            continue;
          localXmlSerializer2.attribute(null, "type", "01");
          writeNodeValue(localXmlSerializer2, "programindex", j + "");
          writeNodeValue(localXmlSerializer2, "name", localPlayBillItem.desc);
          writeNodeValue(localXmlSerializer2, "timeSrc", localPlayBillItem.begin);
          writeNodeValue(localXmlSerializer2, "daySrc", localPlayBillStruct.day);
          writeNodeValue(localXmlSerializer2, "can_play", localPlayBillItem.can_play + "");
          writeNodeValue(localXmlSerializer2, "time", formatTime(localPlayBillItem.begin));
          writeNodeValue(localXmlSerializer2, "timeLen", localPlayBillItem.timeLen + "");
          writeNodeValue(localXmlSerializer2, "day", formatDate(localPlayBillStruct.day));
          localXmlSerializer2.endTag(null, "program");
          j++;
          continue;
        }
        continue;
        localXmlSerializer2.attribute(null, "type", "00");
        continue;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
        return null;
      }
      label607: localXmlSerializer2.attribute(null, "type", "1");
    }
    localXmlSerializer2.endTag(null, "program_list");
    localXmlSerializer2.endTag(null, "root");
    localXmlSerializer2.endDocument();
    localXmlSerializer2.flush();
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
    return localByteArrayInputStream;
  }

  private void initViews()
  {
    this.mReservationService = ReservationService.getinstance();
    this.renderContext = XulManager.createXulRender("MplayerPlaybackEpisodeView", new XulRenderContext.IXulRenderHandler()
    {
      private Handler _mHandler = new Handler();

      public IXulExternalView createExternalView(String paramAnonymousString, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, XulView paramAnonymousXulView)
      {
        return null;
      }

      public InputStream getAppData(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        Log.i(MplayerPlaybackEpisodeView.TAG, "path = " + paramAnonymousString);
        if ("mplayer/programlist".equals(paramAnonymousString))
          return MplayerPlaybackEpisodeView.this.getProgramList();
        if ("mplayer/channellist".equals(paramAnonymousString))
          return MplayerPlaybackEpisodeView.this.getChannelList();
        return null;
      }

      public InputStream getAssets(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        return null;
      }

      public void invalidate(Rect paramAnonymousRect)
      {
        if (paramAnonymousRect == null)
        {
          MplayerPlaybackEpisodeView.this.invalidate();
          return;
        }
        MplayerPlaybackEpisodeView.this.invalidate(paramAnonymousRect);
      }

      public void onDoAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
      {
        Logger.i("MplayerPlaybackEpisodeView", "command：" + paramAnonymousString3);
        Gson localGson;
        if ("program_item_click".equals(paramAnonymousString2))
          localGson = new Gson();
        label427: 
        do
        {
          Command localCommand;
          do
          {
            while (true)
            {
              int i;
              try
              {
                localCommand = (Command)localGson.fromJson(paramAnonymousString3, Command.class);
                i = 0;
                int j = MplayerPlaybackEpisodeView.this.programList.size();
                ArrayList localArrayList = null;
                if (i < j)
                {
                  if (((PlayBillStruct)MplayerPlaybackEpisodeView.this.programList.get(i)).day.equals(localCommand.day))
                    localArrayList = ((PlayBillStruct)MplayerPlaybackEpisodeView.this.programList.get(i)).arrPlayBill;
                }
                else
                {
                  if (MplayerPlaybackEpisodeView.this.isShowCanPlay(Integer.valueOf(localCommand.canplay).intValue()))
                    break;
                  if ((localArrayList != null) && (MplayerPlaybackEpisodeView.this.currentChannelInfo != null))
                    MplayerPlaybackEpisodeView.this.processReservation((PlayBillItem)localArrayList.get(Integer.valueOf(localCommand.programindex).intValue()), MplayerPlaybackEpisodeView.this.currentChannelInfo, localCommand.day);
                  if (!"bindingUpdated".equals(paramAnonymousString3))
                    break label427;
                  MplayerPlaybackEpisodeView.access$1602(MplayerPlaybackEpisodeView.this, true);
                  MplayerPlaybackEpisodeView.this.dealSelectItem();
                  MplayerPlaybackEpisodeView.access$1802(MplayerPlaybackEpisodeView.this, false);
                  return;
                }
              }
              catch (JsonSyntaxException localJsonSyntaxException)
              {
                localJsonSyntaxException.printStackTrace();
                Logger.i(MplayerPlaybackEpisodeView.TAG, "json解析异常");
                return;
              }
              i++;
            }
            MplayerPlaybackEpisodeView.access$202(MplayerPlaybackEpisodeView.this, localCommand.videoId);
            MplayerPlaybackEpisodeView.access$302(MplayerPlaybackEpisodeView.this, localCommand.categoryId);
            MplayerPlaybackEpisodeView.access$1202(MplayerPlaybackEpisodeView.this, localCommand.videoId);
            MplayerPlaybackEpisodeView.access$1302(MplayerPlaybackEpisodeView.this, localCommand.beginTime);
            MplayerPlaybackEpisodeView.access$1402(MplayerPlaybackEpisodeView.this, localCommand.day);
            MplayerPlaybackEpisodeView.access$1502(MplayerPlaybackEpisodeView.this, localCommand.programName);
          }
          while (MplayerPlaybackEpisodeView.this.lsnr == null);
          if (paramAnonymousXulView != null)
          {
            String str = ReportArea.getValue(paramAnonymousXulView.getAttrString("page_area"));
            if (!TextUtils.isEmpty(str))
              ReportInfo.getInstance().setEntranceSrc(str);
          }
          MplayerPlaybackEpisodeView.this.lsnr.onProgramItemClick(localCommand.videoId, localCommand.categoryId, localCommand.day, localCommand.beginTime, localCommand.timeLen, MplayerPlaybackEpisodeView.this.currentProgramName);
          return;
        }
        while (!paramAnonymousString3.startsWith("requestPlayBill"));
        MplayerPlaybackEpisodeView.this.requestPlayBill(paramAnonymousString3);
        MplayerPlaybackEpisodeView.access$1802(MplayerPlaybackEpisodeView.this, true);
      }

      public void onRenderEvent(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, Object paramAnonymousObject)
      {
      }

      public void onRenderIsReady()
      {
        if ((MplayerPlaybackEpisodeView.this.channelListInfo != null) && (MplayerPlaybackEpisodeView.this.channelListInfo.channelList != null) && (MplayerPlaybackEpisodeView.this.channelListInfo.channelList.size() > 0))
          MplayerPlaybackEpisodeView.this.renderContext.refreshBinding("channellist", "file:///.app/mplayer/channellist");
        if (MplayerPlaybackEpisodeView.this.renderNeed2ProcessBinding)
          MplayerPlaybackEpisodeView.this.renderContext.refreshBinding("programlist", "file:///.app/mplayer/programlist");
      }

      public String resolve(XulWorker.DownloadItem paramAnonymousDownloadItem, String paramAnonymousString)
      {
        return null;
      }

      public void uiRun(Runnable paramAnonymousRunnable)
      {
        this._mHandler.post(paramAnonymousRunnable);
      }

      public void uiRun(Runnable paramAnonymousRunnable, int paramAnonymousInt)
      {
        this._mHandler.postDelayed(paramAnonymousRunnable, paramAnonymousInt);
      }

      class Command
      {
        String beginTime;
        String canplay;
        String categoryId;
        String day;
        String programName;
        String programindex;
        String timeLen;
        String videoId;

        Command()
        {
        }

        public String toString()
        {
          return "Command{categoryId='" + this.categoryId + '\'' + ", videoId='" + this.videoId + '\'' + ", day='" + this.day + '\'' + ", beginTime='" + this.beginTime + '\'' + ", timeLen='" + this.timeLen + '\'' + ", programindex='" + this.programindex + '\'' + ", canplay='" + this.canplay + '\'' + ", programName='" + this.programName + '\'' + '}';
        }
      }
    }
    , XulManager.getPageWidth(), XulManager.getPageHeight());
    XulPage localXulPage = this.renderContext.getPage();
    int i = localXulPage.getX();
    int j = localXulPage.getY();
    int k = localXulPage.getWidth();
    int m = localXulPage.getHeight();
    if (i >= 2147483547)
      i = 0;
    if (j >= 2147483547)
      j = 0;
    if (k >= 2147483547)
      k = localXulPage.getPageWidth() - i;
    if (m >= 2147483547)
      m = localXulPage.getPageHeight() - j;
    ((int)(i * localXulPage.getXScalar()));
    ((int)(j * localXulPage.getYScalar()));
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams((int)(k * localXulPage.getXScalar()), (int)(m * localXulPage.getYScalar()));
    localLayoutParams.addRule(15);
    setLayoutParams(localLayoutParams);
  }

  private boolean isShowCanPlay(int paramInt)
  {
    return paramInt == 1;
  }

  private String makeIdxString(int paramInt)
  {
    if (paramInt < 10)
      return "00" + paramInt;
    if (paramInt < 100)
      return "0" + paramInt;
    return String.valueOf(paramInt);
  }

  private void parseChannelData(String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    int i;
    do
    {
      return;
      this.currentChannelInfo = null;
      i = paramString.lastIndexOf("?");
    }
    while ((i < 0) || (i >= -1 + paramString.length()));
    String str = paramString.substring(i + 1);
    this.selectedVideoId = str.substring(8, str.indexOf("&"));
    this.selectedCategoryId = str.substring(12 + str.indexOf("&"));
    this.currentChannelInfo = findChannelInfoByVodeoId(this.selectedVideoId, this.selectedCategoryId);
  }

  private void processReservation(PlayBillItem paramPlayBillItem, ChannelItemInfoV2 paramChannelItemInfoV2, String paramString)
  {
    if ((paramPlayBillItem instanceof PlayBillRecommendItem))
      return;
    if (this.reservationMap == null)
      this.reservationMap = new HashMap();
    Reservation localReservation1 = (Reservation)this.reservationMap.get(paramPlayBillItem);
    if (localReservation1 != null)
    {
      this.mReservationService.removeReservation(localReservation1.get_id());
      this.reservationMap.remove(paramPlayBillItem);
      UITools.ShowCustomToast(getContext(), "取消预约成功");
      return;
    }
    Reservation localReservation2 = new Reservation();
    localReservation2.setBeginTime(paramPlayBillItem.begin);
    localReservation2.setDay(paramString);
    localReservation2.setFilm_type(1);
    localReservation2.setNns_index(0);
    localReservation2.setTimeLen(String.valueOf(paramPlayBillItem.timeLen));
    localReservation2.setVideoId(paramChannelItemInfoV2.id);
    localReservation2.setName(paramPlayBillItem.desc);
    localReservation2.setCategoryId(this.selectedCategoryId);
    localReservation2.setPackageId(this.packgeid);
    localReservation2.setChannel(paramChannelItemInfoV2.name);
    localReservation2.setHuawei_cid(paramPlayBillItem.huawei_cid);
    localReservation2.setReal_max_back_time_len(((PlayBillStruct)this.programList.get(0)).tsLimitMax);
    localReservation2.setReal_min_back_time_len(((PlayBillStruct)this.programList.get(0)).tsLimitMin);
    localReservation2.setReal_default_back_pos(((PlayBillStruct)this.programList.get(0)).tsDefaultPos);
    this.mReservationService.addReservation(localReservation2);
    MergeLocalReserveAndUpload.getInstance().addLocalReplyReserve(paramPlayBillItem, localReservation2);
    this.reservationMap.put(paramPlayBillItem, localReservation2);
    Logger.i(TAG, "预约时间：" + new Date(localReservation2.getReservation_time()).toLocaleString());
    if (localReservation2.getReservation_time() <= SystemTimeManager.getCurrentServerTime())
    {
      this.mReservationService.checkExpiredReservation();
      return;
    }
    UITools.ShowCustomToast(getContext(), "预约成功");
  }

  private void refreshProgramBinding()
  {
    this.renderContext.findItemById("right_content_layer").setEnabled(false);
    this.isProgramListBindingFinish = false;
    this.renderContext.refreshBinding("programlist", "file:///.app/mplayer/programlist");
  }

  private void requestFocus(XulView paramXulView)
  {
    this.renderContext.getLayout().requestFocus(paramXulView);
  }

  private void requestPlayBill(String paramString)
  {
    if (this.delayHandler.hasMessages(1001))
      this.delayHandler.removeMessages(1001);
    Message localMessage = new Message();
    localMessage.what = 1001;
    localMessage.obj = paramString;
    this.delayHandler.sendMessageDelayed(localMessage, 1250L);
  }

  private void resetProgramPos(final XulView paramXulView)
  {
    postInvalidate();
    post(new Runnable()
    {
      public void run()
      {
        Logger.d("allen", "resetProgramPos()");
        XulArea localXulArea = (XulArea)MplayerPlaybackEpisodeView.this.renderContext.findItemById("right_container");
        XulSliderAreaWrapper.fromXulView(localXulArea).makeChildVisible(paramXulView);
        localXulArea.setDynamicFocus(paramXulView);
        XulView localXulView = MplayerPlaybackEpisodeView.this.renderContext.findItemById("date");
        localXulView.setAttr("text", paramXulView.getAttrString("day"));
        localXulView.resetRender();
        MplayerPlaybackEpisodeView.this.renderContext.findItemById("right_content_layer").setEnabled(true);
        MplayerPlaybackEpisodeView.this.checkHasDelayData();
      }
    });
  }

  private void showDateTips(boolean paramBoolean)
  {
    XulView localXulView;
    if (this.renderContext != null)
    {
      localXulView = this.renderContext.findItemById("date_tips");
      if (localXulView != null)
        if (!paramBoolean)
          break label43;
    }
    label43: for (String str = "block"; ; str = "none")
    {
      localXulView.setStyle("display", str);
      localXulView.resetRender();
      return;
    }
  }

  private void writeNodeValue(XmlSerializer paramXmlSerializer, String paramString1, String paramString2)
    throws IOException
  {
    if (paramString2 == null)
      paramString2 = "";
    paramXmlSerializer.startTag(null, paramString1);
    paramXmlSerializer.text(paramString2);
    paramXmlSerializer.endTag(null, paramString1);
  }

  public void bindChannelList(ChannelInfoV2 paramChannelInfoV2)
  {
    if (paramChannelInfoV2 == null)
      Logger.e(TAG, "bindChannelList info null");
    do
    {
      return;
      this.channelListInfo = paramChannelInfoV2;
    }
    while ((this.renderContext == null) || (!this.renderContext.isReady()));
    this.renderContext.refreshBinding("channellist", "file:///.app/mplayer/channellist");
  }

  public void bindProgramList(String paramString, ArrayList<PlayBillStruct> paramArrayList)
  {
    if ((this.selectedVideoId != null) && (this.selectedVideoId.equals(paramString)))
    {
      this.programList = paramArrayList;
      if (this.programList != null)
        break label91;
      if ((this.renderContext == null) || (!this.renderContext.isReady()))
        break label85;
      if (!this.isProgramListBindingFinish)
        break label77;
      this.processDelayData = false;
      refreshProgramBinding();
    }
    while (true)
    {
      this.renderNeed2ProcessBinding = false;
      Logger.d("allen", "bindProgramList()--->内容为空--刷新列表");
      return;
      label77: this.processDelayData = true;
    }
    label85: this.renderNeed2ProcessBinding = true;
    return;
    label91: label124: Iterator localIterator;
    if ((this.renderContext != null) && (this.renderContext.isReady()))
      if (this.isProgramListBindingFinish)
      {
        this.processDelayData = false;
        refreshProgramBinding();
        this.renderNeed2ProcessBinding = false;
        Logger.d("allen", "bindProgramList()--->有数据--刷新列表");
        label137: if ((!this.defaultMode) || (this.lsnr == null))
          break label346;
        this.defaultMode = false;
        localIterator = this.programList.iterator();
      }
    label346: label352: 
    while (true)
    {
      if (!localIterator.hasNext())
        break label354;
      PlayBillStruct localPlayBillStruct = (PlayBillStruct)localIterator.next();
      if (localPlayBillStruct.day.equals(this.currentDay))
      {
        ArrayList localArrayList = localPlayBillStruct.arrPlayBill;
        if ((localArrayList != null) && (!localArrayList.isEmpty()))
        {
          int i = localArrayList.size();
          for (int j = 0; ; j++)
          {
            if (j >= i)
              break label352;
            PlayBillItem localPlayBillItem = (PlayBillItem)localArrayList.get(j);
            if (localPlayBillItem.begin.equals(this.currentBeginTime))
            {
              this.currentChannelInfo = findChannelInfoByVodeoId(this.selectedVideoId, this.selectedCategoryId);
              this.lsnr.onProgramItemClick(this.selectedVideoId, this.selectedCategoryId, this.currentDay, this.currentBeginTime, localPlayBillItem.timeLen + "", this.currentProgramName);
              return;
              this.processDelayData = true;
              break label124;
              this.renderNeed2ProcessBinding = true;
              break label137;
              break;
            }
          }
        }
      }
    }
    label354: this.lsnr.onError("未找到节目");
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if ((this.renderContext != null) && (this.renderContext.onKeyEvent(paramKeyEvent)))
      return true;
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public String findProgramName(String paramString1, String paramString2, String paramString3)
  {
    if ((this.programList == null) || (this.programList.isEmpty()))
      return "";
    Iterator localIterator = this.programList.iterator();
    while (localIterator.hasNext())
    {
      PlayBillStruct localPlayBillStruct = (PlayBillStruct)localIterator.next();
      if (localPlayBillStruct.day.equals(paramString2))
      {
        ArrayList localArrayList = localPlayBillStruct.arrPlayBill;
        if ((localArrayList != null) && (!localArrayList.isEmpty()))
        {
          int i = localArrayList.size();
          for (int j = 0; j < i; j++)
          {
            PlayBillItem localPlayBillItem = (PlayBillItem)localArrayList.get(j);
            if (localPlayBillItem.begin.equals(paramString3))
              return localPlayBillItem.desc;
          }
        }
      }
    }
    return "";
  }

  public String formatTime(String paramString)
  {
    SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("HH:mm");
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("HHmmss");
    try
    {
      String str = localSimpleDateFormat1.format(localSimpleDateFormat2.parse(paramString));
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "00:00";
  }

  public boolean isLoading()
  {
    return this.isLoading;
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    initViews();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    if (this.renderContext != null)
    {
      if (this.rect != null)
        break label58;
      this.rect = new Rect(0, 0, getWidth(), getHeight());
    }
    while (true)
    {
      if (this.renderContext.beginDraw(paramCanvas, this.rect))
        this.renderContext.endDraw();
      return;
      label58: this.rect.set(0, 0, getWidth(), getHeight());
    }
  }

  public void setOnPlayBillClickListener(OnPlayBillClickListener paramOnPlayBillClickListener)
  {
    this.lsnr = paramOnPlayBillClickListener;
  }

  public void setPlayIntentParams(PlayerIntentParams paramPlayerIntentParams)
  {
    this.selectedVideoId = paramPlayerIntentParams.nns_videoInfo.videoId;
    this.currentPlayingVideoId = paramPlayerIntentParams.nns_videoInfo.videoId;
    this.packgeid = paramPlayerIntentParams.nns_videoInfo.packageId;
    this.currentPlayingcategoryId = paramPlayerIntentParams.nns_videoInfo.categoryId;
    this.currentDay = paramPlayerIntentParams.nns_day;
    this.currentBeginTime = paramPlayerIntentParams.nns_beginTime;
    this.selectedCategoryId = paramPlayerIntentParams.nns_videoInfo.categoryId;
    this.currentProgramName = paramPlayerIntentParams.nns_videoInfo.name;
  }

  public void setVisibility(int paramInt)
  {
    XulArrayList localXulArrayList1;
    if ((paramInt != 0) && (this.renderContext != null))
    {
      XulArea localXulArea1 = (XulArea)this.renderContext.findItemById("left_content_layer");
      if (localXulArea1 == null);
      do
      {
        return;
        localXulArrayList1 = XulPage.findItemsByClass(localXulArea1, "channel_item");
      }
      while (localXulArrayList1 == null);
    }
    for (int i = 0; ; i++)
      if (i < localXulArrayList1.size())
      {
        XulView localXulView3 = (XulView)localXulArrayList1.get(i);
        if (this.currentPlayingVideoId.equals(localXulView3.getAttrString("id")))
          requestFocus(localXulView3);
      }
      else
      {
        XulArea localXulArea2 = (XulArea)this.renderContext.findItemById("right_content_layer");
        if (localXulArea2 != null)
        {
          XulArrayList localXulArrayList2 = XulPage.findItemsByClass(localXulArea2, "program_item");
          if (localXulArrayList2 != null)
          {
            Iterator localIterator = localXulArrayList2.iterator();
            while (localIterator.hasNext())
            {
              XulView localXulView1 = (XulView)localIterator.next();
              if (localXulView1.hasClass("program_item_selected"))
              {
                XulSliderAreaWrapper.fromXulView((XulArea)this.renderContext.findItemById("right_container")).makeChildVisible(localXulView1);
                localXulArea2.setDynamicFocus(localXulView1);
                XulView localXulView2 = this.renderContext.findItemById("date");
                localXulView2.setAttr("text", localXulView1.getAttrString("day"));
                localXulView2.resetRender();
              }
            }
          }
        }
        super.setVisibility(paramInt);
        return;
      }
  }

  public static abstract interface OnPlayBillClickListener
  {
    public abstract void onChannelItemClick(String paramString1, String paramString2);

    public abstract void onError(String paramString);

    public abstract void onProgramItemClick(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerPlaybackEpisodeView
 * JD-Core Version:    0.6.2
 */