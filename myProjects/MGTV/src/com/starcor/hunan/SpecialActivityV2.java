package com.starcor.hunan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.FilmListItem;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.domain.MovieData;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.SpecialTopicPkgCntLstInfo;
import com.starcor.core.domain.SpecialTopicPkgCntLstStruct;
import com.starcor.core.domain.SpecialTopicPutInfo;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.exception.ApplicationException;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.DomainUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.domain.ReportData;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.hunan.uilogic.InitApiData;
import com.starcor.hunan.uilogic.InitApiData.onApiOKListener;
import com.starcor.media.player.ApiTaskControl;
import com.starcor.sccms.api.SccmsApiGetSpecialTopicPkgContentLstTask.ISccmsApiGetSearchSpecialTopicPkgLstTaskListener;
import com.starcor.sccms.api.SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoIndexInfoTask.ISccmsApiGetVideoIndexInfoTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoInfoV2Task.ISccmsApiGetVideoInfoV2TaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.Wrapper.XulSliderAreaWrapper;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class SpecialActivityV2 extends XULActivity
{
  public static final String INTENT_SPECIAL_INFO = "special_info";
  private static final String TAG = SpecialActivityV2.class.getSimpleName();
  private static String import_id = "";
  private static String serial_id = "";
  private String actor = "演员:";
  private String artist = "艺人:";
  private XulView detailInfo;
  private String director = "导演:";
  private XulView extraInfo;
  private String host = "主持人:";
  private boolean isLoadSuccess = false;
  private int loadingCounter = 0;
  private SpecialTopicPkgCntLstInfo mSpecialInfo;
  MetadataInfo metadataInfo;
  private int posterCount;
  private String rcdata = "";
  private String recommendPageLimit = "1";
  private String recommendPageNum = "";
  private String reqid = "";
  private String space = "    ";
  private SpecialTopicPkgCntLstStruct specialItem;
  SpecialTopicPutInfo specialTopicInfo;
  private ApiTaskControl taskControl;
  private XulView title;
  private String ver = "";

  private void cleanData()
  {
    setXulViewText(this.title, "");
    setXulViewText(this.extraInfo, "");
    setXulViewText(this.detailInfo, "");
    import_id = "";
    serial_id = "";
  }

  private InputStream getMovieData()
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "data");
      for (int i = 0; i < this.mSpecialInfo.sTopicPkgCntLstStructs.size(); i++)
      {
        SpecialTopicPkgCntLstStruct localSpecialTopicPkgCntLstStruct = (SpecialTopicPkgCntLstStruct)this.mSpecialInfo.sTopicPkgCntLstStructs.get(i);
        localXmlSerializer.startTag(null, "item");
        writeNodeValue(localXmlSerializer, "img", localSpecialTopicPkgCntLstStruct.img2);
        writeNodeValue(localXmlSerializer, "img_v", localSpecialTopicPkgCntLstStruct.imgv);
        writeNodeValue(localXmlSerializer, "img_h", localSpecialTopicPkgCntLstStruct.imgh);
        writeNodeValue(localXmlSerializer, "index", String.valueOf(i));
        writeNodeValue(localXmlSerializer, "name", localSpecialTopicPkgCntLstStruct.name);
        writeNodeValue(localXmlSerializer, "mark_img", localSpecialTopicPkgCntLstStruct.mark_img);
        localXmlSerializer.endTag(null, "item");
      }
      localXmlSerializer.endTag(null, "data");
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

  private void getSpecialItems(String paramString)
  {
    ServerApiManager.i().APIGetSpecialTopicPkgContent(paramString, new GetSpecialTopicPkgContentListener());
  }

  private void gotoDetailedPage(SpecialTopicPkgCntLstStruct paramSpecialTopicPkgCntLstStruct)
  {
    if (paramSpecialTopicPkgCntLstStruct == null);
    do
    {
      return;
      if ("1".equalsIgnoreCase(paramSpecialTopicPkgCntLstStruct.video_type))
      {
        showTimeShift(paramSpecialTopicPkgCntLstStruct.video_id, paramSpecialTopicPkgCntLstStruct.category_id, paramSpecialTopicPkgCntLstStruct.name);
        return;
      }
      if ("2".equalsIgnoreCase(paramSpecialTopicPkgCntLstStruct.video_type))
      {
        showReplay(paramSpecialTopicPkgCntLstStruct.video_id, "000", "000");
        return;
      }
    }
    while (!"0".equalsIgnoreCase(paramSpecialTopicPkgCntLstStruct.video_type));
    if (!"-1".equalsIgnoreCase(paramSpecialTopicPkgCntLstStruct.video_index))
    {
      playVideo(paramSpecialTopicPkgCntLstStruct);
      return;
    }
    FilmListItem localFilmListItem = DomainUtils.specialTopicPkgCntLstStruct2FilmListItem(paramSpecialTopicPkgCntLstStruct);
    localFilmListItem.specialid = this.specialTopicInfo.id;
    showDetailedV3(localFilmListItem, 0);
  }

  private void initView()
  {
    this.title = xulFindViewById("title");
    this.extraInfo = xulFindViewById("extraInfo");
    this.detailInfo = xulFindViewById("detailInfo");
    loadData();
  }

  private void loadData()
  {
    if ("com.starcor.hunan.cmd.show_special_top".equals(getIntent().getStringExtra("Cmd")))
    {
      String str1 = getIntent().getStringExtra("subjectId");
      if (TextUtils.isEmpty(str1))
      {
        Logger.e(TAG, "processSpecialTop subjectId not found");
        reportPageLoad(false);
        return;
      }
      String str2 = getIntent().getStringExtra("subjectName");
      if (this.specialTopicInfo == null)
        this.specialTopicInfo = new SpecialTopicPutInfo();
      this.specialTopicInfo.id = str1;
      this.specialTopicInfo.name = str2;
      this.specialTopicInfo.play_type = "auto";
      ServerApiManager.i().APIGetSpecialTopicPutData(this.specialTopicInfo.id, null, "asset", new SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          Logger.e(SpecialActivityV2.TAG, "Load special topic data failed!! " + paramAnonymousServerApiCommonError.toString());
          SpecialActivityV2.this.showErrorDialog("1002007");
          SpecialActivityV2.this.reportPageLoad(false);
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<SpecialTopicPutInfo> paramAnonymousArrayList)
        {
          Logger.e(SpecialActivityV2.TAG, "Load special topic data success!!result=" + paramAnonymousArrayList);
          if ((paramAnonymousArrayList == null) || (paramAnonymousArrayList.size() == 0) || (paramAnonymousArrayList.size() >= 2))
          {
            SpecialActivityV2.this.reportPageLoad(false);
            SpecialActivityV2.this.showErrorDialog("1002007");
            return;
          }
          SpecialActivityV2.this.specialTopicInfo = ((SpecialTopicPutInfo)paramAnonymousArrayList.get(0));
          SpecialActivityV2.this.setBackgroundBg(SpecialActivityV2.this.specialTopicInfo.poster);
          SpecialActivityV2.this.getSpecialItems(SpecialActivityV2.this.specialTopicInfo.id);
        }
      });
    }
    while (true)
    {
      this.taskControl = new ApiTaskControl();
      return;
      this.metadataInfo = ((MetadataInfo)getIntent().getSerializableExtra("MetaDataInfo"));
      if (this.metadataInfo != null)
      {
        ServerApiManager.i().APIGetSpecialTopicPutData(this.metadataInfo.packet_id, null, "asset", new SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener()
        {
          public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
          {
            Logger.e(SpecialActivityV2.TAG, "Load special topic data failed!! " + paramAnonymousServerApiCommonError.toString());
            SpecialActivityV2.this.showErrorDialog("1002007");
            SpecialActivityV2.this.reportPageLoad(false);
          }

          public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<SpecialTopicPutInfo> paramAnonymousArrayList)
          {
            if ((paramAnonymousArrayList == null) || (paramAnonymousArrayList.size() == 0) || (paramAnonymousArrayList.size() >= 2))
            {
              SpecialActivityV2.this.showErrorDialog("1002007");
              SpecialActivityV2.this.reportPageLoad(false);
              return;
            }
            SpecialActivityV2.this.specialTopicInfo = ((SpecialTopicPutInfo)paramAnonymousArrayList.get(0));
            SpecialActivityV2.this.setBackgroundBg(SpecialActivityV2.this.specialTopicInfo.poster);
            SpecialActivityV2.this.getSpecialItems(SpecialActivityV2.this.specialTopicInfo.id);
          }
        });
      }
      else
      {
        this.specialTopicInfo = ((SpecialTopicPutInfo)getIntent().getSerializableExtra("special_info"));
        setBackgroundBg(this.specialTopicInfo.poster);
        getSpecialItems(this.specialTopicInfo.id);
      }
    }
  }

  private boolean needChangeImage(int paramInt)
  {
    switch (paramInt)
    {
    case 1:
    case 2:
    case 4:
    default:
      return false;
    case 3:
    case 5:
    case 6:
    }
    return true;
  }

  private void playVideo(SpecialTopicPkgCntLstStruct paramSpecialTopicPkgCntLstStruct)
  {
    PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
    try
    {
      localPlayerIntentParams.nns_index = Integer.valueOf(paramSpecialTopicPkgCntLstStruct.video_index).intValue();
      localPlayerIntentParams.nns_videoInfo = new VideoInfo();
      localPlayerIntentParams.nns_videoInfo.videoType = 0;
      localPlayerIntentParams.nns_videoInfo.videoId = paramSpecialTopicPkgCntLstStruct.video_id;
      localPlayerIntentParams.nns_videoInfo.film_name = paramSpecialTopicPkgCntLstStruct.name;
      localPlayerIntentParams.nns_videoInfo.name = paramSpecialTopicPkgCntLstStruct.name;
      localPlayerIntentParams.is_special = true;
      localPlayerIntentParams.autoPlay = 0;
      localPlayerIntentParams.mode = 2;
      Intent localIntent = new Intent().setClass(this, ActivityAdapter.getInstance().getMPlayer());
      localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_EXIT_APP, false);
      localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
      Logger.i(TAG, "onItemClick to mPlayer");
      localIntent.addFlags(8388608);
      startActivity(localIntent);
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      showDetailedV3(DomainUtils.specialTopicPkgCntLstStruct2FilmListItem(paramSpecialTopicPkgCntLstStruct), 0);
    }
  }

  private void processSpecialItems(SpecialTopicPkgCntLstInfo paramSpecialTopicPkgCntLstInfo)
  {
    if ((paramSpecialTopicPkgCntLstInfo == null) || (paramSpecialTopicPkgCntLstInfo.sTopicPkgCntLstStructs == null))
    {
      Logger.e(TAG, "processSpecialItems result || result.sTopicPkgCntLstStructs==null");
      showErrorDialog("1002007");
      reportPageLoad(false);
      return;
    }
    this.mSpecialInfo = paramSpecialTopicPkgCntLstInfo;
    this.posterCount = this.mSpecialInfo.sTopicPkgCntLstStructs.size();
    xulRefreshBinding("movie_data", "file:///.app/api/get_movie_data");
    reportPageLoad(true);
  }

  private void reportPageExit()
  {
    reportExit(this.isLoadSuccess, null);
  }

  private void reportPageLoad()
  {
    reportPageLoad(this.isLoadSuccess);
  }

  private void reportPageLoad(boolean paramBoolean)
  {
    this.isLoadSuccess = paramBoolean;
    reportLoad(paramBoolean, null);
  }

  private void setBackgroundBg(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
    {
      Logger.e(TAG, "默认背景图为空");
      showErrorDialog("1002007", false);
      return;
    }
    XulView localXulView = xulFindViewById("background_image");
    localXulView.setAttr("img.1", paramString);
    localXulView.resetRender();
  }

  private void setExtraInfo(VideoIndex paramVideoIndex)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (paramVideoIndex.director != null)
      localStringBuilder.append(this.director + paramVideoIndex.director + this.space);
    if (paramVideoIndex.actor != null)
      localStringBuilder.append(this.actor + paramVideoIndex.actor + this.space);
    setXulViewText(this.extraInfo, localStringBuilder.toString());
    import_id = paramVideoIndex.import_id;
    serial_id = paramVideoIndex.serial_id;
  }

  private void setExtraInfo(VideoInfo paramVideoInfo)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (paramVideoInfo.kind != null)
      localStringBuilder.append(paramVideoInfo.kind + this.space);
    switch (paramVideoInfo.uiStyle)
    {
    default:
      if (paramVideoInfo.director != null)
        localStringBuilder.append(this.director + paramVideoInfo.director + this.space);
      if (paramVideoInfo.actor != null)
        localStringBuilder.append(this.actor + paramVideoInfo.actor + this.space);
      break;
    case 4:
    case 6:
    case 3:
    case 5:
    }
    while (true)
    {
      setXulViewText(this.extraInfo, localStringBuilder.toString());
      import_id = paramVideoInfo.import_id;
      serial_id = paramVideoInfo.serial_id;
      return;
      if (paramVideoInfo.director != null)
      {
        localStringBuilder.append(this.host + paramVideoInfo.director + this.space);
        continue;
        if (paramVideoInfo.actor != null)
          localStringBuilder.append(this.artist + paramVideoInfo.actor + this.space);
      }
    }
  }

  private void setXulViewText(XulView paramXulView, String paramString)
  {
    if (paramXulView != null)
    {
      paramXulView.setAttr("text", paramString);
      paramXulView.resetRender();
    }
  }

  private void showErrorDialog(String paramString)
  {
    showErrorDialog(paramString, true);
  }

  private void showErrorDialog(String paramString, boolean paramBoolean)
  {
    boolean bool = true;
    ApplicationException localApplicationException = new ApplicationException(this, paramString);
    localApplicationException.setCurPageName(getCurPageName());
    localApplicationException.setDialogType(11);
    localApplicationException.setReport(bool);
    if ((paramBoolean) && (!DeviceInfo.isFactoryCH()));
    while (true)
    {
      localApplicationException.setShowDialog(bool);
      localApplicationException.start();
      return;
      bool = false;
    }
  }

  private void startBackgroundAnimation(int paramInt)
  {
    XulSliderAreaWrapper localXulSliderAreaWrapper = XulSliderAreaWrapper.fromXulView(xulFindViewById("bg_slider"));
    localXulSliderAreaWrapper.scrollTo(paramInt * localXulSliderAreaWrapper.getScrollRange() / this.posterCount);
  }

  private void startRequestData()
  {
    Logger.i(TAG, "startRequestData" + this.specialItem.video_index);
    cleanData();
    if (TextUtils.isEmpty(this.specialItem.video_index))
    {
      stopLoading();
      Logger.e(TAG, "specialItem.video_index  is empty !!!!");
      showErrorDialog("1002007");
    }
    while (!"0".equals(this.specialItem.video_type))
      return;
    if ("-1".equals(this.specialItem.video_index));
    for (int i = ServerApiManager.i().APIGetVideoInfoV2(this.specialItem.video_id, Integer.parseInt(this.specialItem.video_type), new MISccmsApiGetVideoInfoV2TaskListener()); ; i = ServerApiManager.i().APIGetVideoIndexInfoTask(this.specialItem.video_id, Integer.parseInt(this.specialItem.video_type), Integer.parseInt(this.specialItem.video_index), new MISccmsApiGetVideoIndexInfoTaskListener()))
    {
      this.taskControl.clear();
      this.taskControl.addTaskLabel(i, "api", this.specialItem.video_id);
      return;
    }
  }

  public void onBackPressed()
  {
    if (AppFuncCfg.FUNCTION_GOTO_MAIN_IF_FROM_OUT)
      gotoMainActivityIfFromOut(false);
    super.onBackPressed();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    initXul("Special", true);
    startLoading();
  }

  protected void onRestart()
  {
    super.onRestart();
    reportPageLoad();
  }

  protected void onStop()
  {
    super.onStop();
    reportPageExit();
  }

  public void showDetailedV3(FilmListItem paramFilmListItem, int paramInt)
  {
    MovieData localMovieData = new MovieData();
    ReportData localReportData = new ReportData();
    localMovieData.videoId = paramFilmListItem.video_id;
    localMovieData.videoType = paramFilmListItem.video_type;
    localMovieData.packageId = paramFilmListItem.package_id;
    localMovieData.categoryId = paramFilmListItem.category_id;
    localMovieData.viewType = paramFilmListItem.viewType;
    localMovieData.name = paramFilmListItem.film_name;
    localReportData.isfrom_special = "1";
    localReportData.special_id = paramFilmListItem.specialid;
    startDetailPageActivity(localMovieData, localReportData);
  }

  public void showReplay(String paramString1, String paramString2, String paramString3)
  {
    Logger.e(TAG, "SpecialActivityV2 to Replay is unvailable !!!");
  }

  public void showTimeShift(String paramString1, String paramString2, String paramString3)
  {
    Intent localIntent = new Intent(this, ActivityAdapter.getInstance().getMPlayer());
    PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
    localPlayerIntentParams.nns_index = 0;
    localPlayerIntentParams.nns_videoInfo = new VideoInfo();
    localPlayerIntentParams.nns_videoInfo.videoType = 1;
    localPlayerIntentParams.nns_videoInfo.categoryId = paramString2;
    localPlayerIntentParams.mode = 6;
    localPlayerIntentParams.is_special = true;
    localPlayerIntentParams.autoPlay = 0;
    MetadataInfo localMetadataInfo = new MetadataInfo();
    localMetadataInfo.video_id = paramString1;
    localMetadataInfo.video_type = "1";
    localMetadataInfo.category_id = paramString2;
    localMetadataInfo.name = paramString3;
    localIntent.putExtra("MetaDataInfo", localMetadataInfo);
    localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
    try
    {
      startActivity(localIntent);
      return;
    }
    catch (Exception localException)
    {
      Logger.e(TAG, "SpecialActivityV2 to TimeShift is unvailable !!!");
    }
  }

  void startLoading()
  {
    if (isFinishing());
    int i;
    do
    {
      return;
      i = this.loadingCounter;
      this.loadingCounter = (i + 1);
    }
    while ((i != 0) || (this.hasDialog));
    showDialog(5, null);
    this.hasDialog = true;
  }

  void stopLoading()
  {
    if (this.loadingCounter == 0);
    int i;
    do
    {
      return;
      i = -1 + this.loadingCounter;
      this.loadingCounter = i;
    }
    while (i != 0);
    this.hasDialog = false;
    try
    {
      dismissDialog(5);
      return;
    }
    catch (Exception localException)
    {
      Logger.e(TAG, "stopLoading dismissDilog Error:" + localException.getMessage());
    }
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    Logger.i(TAG, "xulDoAction action=" + paramString1 + " type=" + paramString2 + " command" + paramString3 + " userdata=" + paramObject);
    SpecialTopicPkgCntLstStruct localSpecialTopicPkgCntLstStruct;
    if (("poster_focus".equals(paramString2)) || ("poster_click".equals(paramString2)))
    {
      int i = XulUtils.tryParseInt(paramString3);
      localSpecialTopicPkgCntLstStruct = (SpecialTopicPkgCntLstStruct)this.mSpecialInfo.sTopicPkgCntLstStructs.get(i);
      if (!"poster_focus".equals(paramString2))
        break label140;
      startBackgroundAnimation(i);
      this.specialItem = localSpecialTopicPkgCntLstStruct;
      startRequestData();
    }
    while (true)
    {
      return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
      label140: gotoDetailedPage(localSpecialTopicPkgCntLstStruct);
    }
  }

  protected InputStream xulGetAppData(XulWorker.DownloadItem paramDownloadItem, String paramString)
  {
    Logger.d(TAG, "xulGetAppData path=" + paramString);
    if ("api/get_movie_data".equals(paramString))
      return getMovieData();
    return super.xulGetAppData(paramDownloadItem, paramString);
  }

  protected void xulOnRenderIsReady()
  {
    super.xulOnRenderIsReady();
    if (GlobalLogic.getInstance().isAppInterfaceReady())
    {
      Logger.d(TAG, "xulOnRenderIsReady: api ok");
      initView();
      return;
    }
    Logger.d(TAG, "xulOnRenderIsReady: api not ok");
    App.getInstance().setOnserviceOKListener(new OnServiceOK(null));
  }

  class GetSpecialTopicPkgContentListener
    implements SccmsApiGetSpecialTopicPkgContentLstTask.ISccmsApiGetSearchSpecialTopicPkgLstTaskListener
  {
    GetSpecialTopicPkgContentListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.e(SpecialActivityV2.TAG, "APIGetSpecialTopicPkgContent failed!! ");
      SpecialActivityV2.this.stopLoading();
      SpecialActivityV2.this.showErrorDialog("1002007");
      SpecialActivityV2.this.reportPageLoad(false);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, SpecialTopicPkgCntLstInfo paramSpecialTopicPkgCntLstInfo)
    {
      SpecialActivityV2.this.stopLoading();
      SpecialActivityV2.this.processSpecialItems(paramSpecialTopicPkgCntLstInfo);
    }
  }

  class MISccmsApiGetVideoIndexInfoTaskListener
    implements SccmsApiGetVideoIndexInfoTask.ISccmsApiGetVideoIndexInfoTaskListener
  {
    MISccmsApiGetVideoIndexInfoTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      if (!SpecialActivityV2.this.taskControl.checkTaskValid(paramServerApiTaskInfo.getTaskId(), SpecialActivityV2.this.specialItem.video_id))
      {
        Logger.i(SpecialActivityV2.TAG, "ISccmsApiGetVideoIndexInfoTaskListener.onError() invalid task");
        return;
      }
      SpecialActivityV2.this.showErrorDialog("1002008");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, VideoIndex paramVideoIndex)
    {
      if (paramVideoIndex == null)
      {
        Logger.i(SpecialActivityV2.TAG, "infoCallback onSuccess result==null");
        return;
      }
      if (!SpecialActivityV2.this.taskControl.checkTaskValid(paramServerApiTaskInfo.getTaskId(), SpecialActivityV2.this.specialItem.video_id))
      {
        Logger.i(SpecialActivityV2.TAG, "ISccmsApiGetVideoIndexInfoTaskListener.onSuccess() invalid task");
        return;
      }
      SpecialActivityV2.this.setXulViewText(SpecialActivityV2.this.title, paramVideoIndex.name);
      SpecialActivityV2.this.setExtraInfo(paramVideoIndex);
      SpecialActivityV2.this.setXulViewText(SpecialActivityV2.this.detailInfo, paramVideoIndex.desc);
    }
  }

  class MISccmsApiGetVideoInfoV2TaskListener
    implements SccmsApiGetVideoInfoV2Task.ISccmsApiGetVideoInfoV2TaskListener
  {
    MISccmsApiGetVideoInfoV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      if (!SpecialActivityV2.this.taskControl.checkTaskValid(paramServerApiTaskInfo.getTaskId(), SpecialActivityV2.this.specialItem.video_id))
      {
        Logger.i(SpecialActivityV2.TAG, "ISccmsApiGetVideoInfoV2TaskListener.onError() invalid task");
        return;
      }
      SpecialActivityV2.this.showErrorDialog("1002008");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, VideoInfo paramVideoInfo)
    {
      if (paramVideoInfo == null)
      {
        Logger.i(SpecialActivityV2.TAG, "infoCallback onSuccess result==null");
        return;
      }
      if (!SpecialActivityV2.this.taskControl.checkTaskValid(paramServerApiTaskInfo.getTaskId(), SpecialActivityV2.this.specialItem.video_id))
      {
        Logger.i(SpecialActivityV2.TAG, "ISccmsApiGetVideoInfoV2TaskListener.onSuccess() invalid task");
        return;
      }
      SpecialActivityV2.this.setXulViewText(SpecialActivityV2.this.title, paramVideoInfo.name);
      SpecialActivityV2.this.setExtraInfo(paramVideoInfo);
      SpecialActivityV2.this.setXulViewText(SpecialActivityV2.this.detailInfo, paramVideoInfo.desc);
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
        SpecialActivityV2.this.xulRefreshBinding("api_n36");
        SpecialActivityV2.this.initView();
      }
    }

    public void onGetApiDataError()
    {
      Logger.e(SpecialActivityV2.TAG, "onGetApiDataError...");
    }

    public void onNeedFinishActivity()
    {
      SpecialActivityV2.this.finish();
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
      Logger.i(SpecialActivityV2.TAG, "onServiceOK");
      new InitApiData(SpecialActivityV2.this).setOnApiOkListener(new SpecialActivityV2.OnApiOk(SpecialActivityV2.this, null));
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.SpecialActivityV2
 * JD-Core Version:    0.6.2
 */