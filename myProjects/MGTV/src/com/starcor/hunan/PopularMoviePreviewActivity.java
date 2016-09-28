package com.starcor.hunan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import com.starcor.core.domain.CommonState;
import com.starcor.core.domain.CommonState.Result;
import com.starcor.core.domain.PlayInfoV2;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.SpecialTopicPkgCntLstInfo;
import com.starcor.core.domain.SpecialTopicPkgCntLstStruct;
import com.starcor.core.domain.SpecialTopicPutInfo;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.hunan.widget.XulExt_SimpleVideoView;
import com.starcor.hunan.widget.XulExt_SimpleVideoView.PlayerEvent;
import com.starcor.media.player.ApiTaskControl;
import com.starcor.sccms.api.SccmsApiCancelVideoWishTask.ISccmsApiCancelVideoWishTaskListener;
import com.starcor.sccms.api.SccmsApiExistsUserWishTask.ISccmsApiExistsUserWishTaskListener;
import com.starcor.sccms.api.SccmsApiGetSpecialTopicPkgContentLstTask.ISccmsApiGetSearchSpecialTopicPkgLstTaskListener;
import com.starcor.sccms.api.SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener;
import com.starcor.sccms.api.SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener;
import com.starcor.sccms.api.SccmsApiSetVideoWishTask.ISccmsApiSetVideoWishTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulPendingInputStream;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class PopularMoviePreviewActivity extends XULActivity
{
  private static final int MESSAGE_RESET_INDICATOR_LEFT = 1000;
  private static final int MESSAGE_RESET_INDICATOR_RIGHT = 2000;
  private static final int MIN_WISH_NUMBER = 10000;
  private static final String TAG = PopularMoviePreviewActivity.class.getSimpleName();
  private static final int TIMEOUT_RESET_INDICATOR = 400;
  private final String NEXT2PLAY = "即将播放“下一部影片名”";
  final String OK = "20002";
  private final String[] TIPS = { "“左右”键切换影片", "“OK”键全屏播放" };
  private int accumulation;
  private XulView addWishButton;
  private boolean bindingFinish = false;
  private String cms_id = "";
  private XulArrayList<XulView> content;
  private int count;
  private String currentCategoryId;
  private String currentId;
  private String currentImgUrl;
  private String currentPacketId;
  private String currentVideoId;
  private String currentVideoIndex;
  private String currentVideoPreviewType;
  private String currentVideoType;
  private ScheduledThreadPoolExecutor executor;
  private boolean hasBeenSet;
  private String import_id = "";
  private String index_cms_id = "";
  private String index_import_id = "";
  private String index_serial_id = "";
  private boolean isError = false;
  private boolean isLoadSuccess = false;
  private String markedLabel;
  private XulPendingInputStream movieDataStream = new XulPendingInputStream();
  private XulView movieDesc;
  private XulView movieDetailArea;
  private XulView movieMainName;
  private XulView movieMainType;
  private Handler pageIndicatorHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if ((PopularMoviePreviewActivity.this.pageIndicatorLeft == null) || (PopularMoviePreviewActivity.this.pageIndicatorRight == null))
        return;
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 1000:
        PopularMoviePreviewActivity.this.pageIndicatorLeft.setAttr("img.1.visible", "false");
        PopularMoviePreviewActivity.this.pageIndicatorLeft.setAttr("img.0.visible", "true");
        PopularMoviePreviewActivity.this.pageIndicatorLeft.resetRender();
        return;
      case 2000:
      }
      PopularMoviePreviewActivity.this.pageIndicatorRight.setAttr("img.1.visible", "false");
      PopularMoviePreviewActivity.this.pageIndicatorRight.setAttr("img.0.visible", "true");
      PopularMoviePreviewActivity.this.pageIndicatorRight.resetRender();
    }
  };
  private XulView pageIndicatorLeft;
  private XulView pageIndicatorRight;
  private XulView playTips;
  private XulView posterArea;
  private SpecialTopicPkgCntLstInfo result;
  private String serial_id = "";
  private ApiTaskControl taskControl = new ApiTaskControl();
  private XulExt_SimpleVideoView videoView;
  private XulView videoViewBorder;
  private VideoInfo videoinfo = new VideoInfo();
  private XulView wishNum;
  private XulView wishNumFirst;
  private XulView wishText;

  private void addWishRequest()
  {
    ServerApiManager.i().APISetVideoWish(this.videoinfo, new SccmsApiSetVideoWishTask.ISccmsApiSetVideoWishTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e(PopularMoviePreviewActivity.TAG, "APISetVideoWish failed!! " + paramAnonymousServerApiCommonError.toString());
        PopularMoviePreviewActivity.this.dismissLoaddingDialog();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, CommonState paramAnonymousCommonState)
      {
        Logger.e(PopularMoviePreviewActivity.TAG, "APISetVideoWish success!! result = " + paramAnonymousCommonState);
        if ((paramAnonymousCommonState != null) && (paramAnonymousCommonState.result != null) && ("0".equals(paramAnonymousCommonState.result.state)))
        {
          PopularMoviePreviewActivity.this.setWishNumber(paramAnonymousCommonState.total);
          Logger.i(PopularMoviePreviewActivity.TAG, "total" + paramAnonymousCommonState.total);
          PopularMoviePreviewActivity.this.addWishButton.addClass("cancel_wish_mode");
          PopularMoviePreviewActivity.this.addWishButton.resetRender();
        }
        PopularMoviePreviewActivity.this.dismissLoaddingDialog();
      }
    });
  }

  private InputStream bindData()
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "data");
      writeNodeValue(localXmlSerializer, "num", String.valueOf(this.result.sTopicPkgCntLstStructs.size()));
      for (int i = 0; i < this.result.sTopicPkgCntLstStructs.size(); i++)
      {
        SpecialTopicPkgCntLstStruct localSpecialTopicPkgCntLstStruct = (SpecialTopicPkgCntLstStruct)this.result.sTopicPkgCntLstStructs.get(i);
        localXmlSerializer.startTag(null, "item");
        writeNodeValue(localXmlSerializer, "img", localSpecialTopicPkgCntLstStruct.img0);
        writeNodeValue(localXmlSerializer, "id", String.valueOf(i + 1));
        String str2 = localSpecialTopicPkgCntLstStruct.name;
        if ((!TextUtils.isEmpty(str2)) && (str2.length() > 10))
          str2 = str2.substring(0, 10) + "...";
        writeNodeValue(localXmlSerializer, "name", str2);
        String str3 = localSpecialTopicPkgCntLstStruct.label_type;
        if (!TextUtils.isEmpty(str3))
        {
          Matcher localMatcher = Pattern.compile("([^/]+)(/[^/]+){0,2}").matcher(str3);
          if (localMatcher.find())
            str3 = localMatcher.group();
        }
        localSpecialTopicPkgCntLstStruct.name = str2;
        localSpecialTopicPkgCntLstStruct.label_type = str3;
        writeNodeValue(localXmlSerializer, "label_type", str3);
        localXmlSerializer.startTag(null, "arg_list");
        writeArgsValue(localXmlSerializer, "id", String.valueOf(i + 1));
        writeArgsValue(localXmlSerializer, "video_id", localSpecialTopicPkgCntLstStruct.video_id);
        writeArgsValue(localXmlSerializer, "video_type", localSpecialTopicPkgCntLstStruct.video_type);
        writeArgsValue(localXmlSerializer, "video_preview_type", localSpecialTopicPkgCntLstStruct.video_preview_type);
        writeArgsValue(localXmlSerializer, "img_bg", localSpecialTopicPkgCntLstStruct.img1);
        writeArgsValue(localXmlSerializer, "type", localSpecialTopicPkgCntLstStruct.type);
        writeArgsValue(localXmlSerializer, "label_type", str3);
        writeArgsValue(localXmlSerializer, "name", localSpecialTopicPkgCntLstStruct.name);
        writeArgsValue(localXmlSerializer, "summary", localSpecialTopicPkgCntLstStruct.summary);
        writeArgsValue(localXmlSerializer, "video_wish", localSpecialTopicPkgCntLstStruct.video_wish);
        writeArgsValue(localXmlSerializer, "is_online", String.valueOf(localSpecialTopicPkgCntLstStruct.is_online));
        writeArgsValue(localXmlSerializer, "packet_id", localSpecialTopicPkgCntLstStruct.packet_id);
        writeArgsValue(localXmlSerializer, "category_id", localSpecialTopicPkgCntLstStruct.category_id);
        writeArgsValue(localXmlSerializer, "video_index", localSpecialTopicPkgCntLstStruct.video_index);
        localXmlSerializer.endTag(null, "arg_list");
        localXmlSerializer.endTag(null, "item");
      }
      localXmlSerializer.endTag(null, "data");
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      String str1 = localStringWriter.toString();
      Log.d(TAG, "data:" + str1);
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str1.getBytes("UTF-8"));
      return localByteArrayInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private void cancelWishRequest()
  {
    ServerApiManager.i().APICancelVideoWish(this.videoinfo, new SccmsApiCancelVideoWishTask.ISccmsApiCancelVideoWishTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e(PopularMoviePreviewActivity.TAG, "APICancelVideoWish failed!! " + paramAnonymousServerApiCommonError.toString());
        PopularMoviePreviewActivity.this.dismissLoaddingDialog();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, CommonState paramAnonymousCommonState)
      {
        Logger.e(PopularMoviePreviewActivity.TAG, "APICancelVideoWish success!! result = " + paramAnonymousCommonState);
        if ((paramAnonymousCommonState != null) && (paramAnonymousCommonState.result != null) && ("0".equals(paramAnonymousCommonState.result.state)))
        {
          PopularMoviePreviewActivity.this.setWishNumber(paramAnonymousCommonState.total);
          Logger.i(PopularMoviePreviewActivity.TAG, "total" + paramAnonymousCommonState.total);
          PopularMoviePreviewActivity.this.addWishButton.removeClass("cancel_wish_mode");
          PopularMoviePreviewActivity.this.addWishButton.resetRender();
        }
        PopularMoviePreviewActivity.this.dismissLoaddingDialog();
      }
    });
  }

  private void checkWish(String paramString)
  {
    ServerApiManager.i().APIApiExistsUserWish(paramString, new SccmsApiExistsUserWishTask.ISccmsApiExistsUserWishTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e(PopularMoviePreviewActivity.TAG, "APIApiExistsUserWish failed!! " + paramAnonymousServerApiCommonError.toString());
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, CommonState paramAnonymousCommonState)
      {
        Logger.e(PopularMoviePreviewActivity.TAG, "APIApiExistsUserWish success!! result = " + paramAnonymousCommonState);
        PopularMoviePreviewActivity.this.setXulViewStyle(PopularMoviePreviewActivity.this.addWishButton, "display", "block");
        if ((paramAnonymousCommonState != null) && (paramAnonymousCommonState.result != null) && ("20002".equals(paramAnonymousCommonState.result.state)))
          PopularMoviePreviewActivity.this.addWishButton.addClass("cancel_wish_mode");
        while (true)
        {
          PopularMoviePreviewActivity.this.addWishButton.resetRender();
          return;
          PopularMoviePreviewActivity.this.addWishButton.removeClass("cancel_wish_mode");
        }
      }
    });
  }

  private Pair<Integer, XulView> findFocusView()
  {
    if (this.content == null)
      this.content = xulFindViewsByClass("poster");
    int i = 0;
    while (i < this.content.size())
      try
      {
        XulView localXulView = (XulView)this.content.get(i);
        if (localXulView.hasFocus())
        {
          Pair localPair = new Pair(Integer.valueOf(i), localXulView);
          return localPair;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        i++;
      }
    return new Pair(Integer.valueOf(-1), null);
  }

  private void findTargetView()
  {
    if (this.content == null)
      this.content = xulFindViewsByClass("poster");
    Iterator localIterator = this.content.iterator();
    while (localIterator.hasNext())
    {
      XulView localXulView = (XulView)localIterator.next();
      if (this.currentId.equals(localXulView.getAttrString("id")))
        xulRequestFocus(localXulView);
    }
  }

  private XulView findViewByClass(String paramString)
  {
    XulArrayList localXulArrayList = xulFindViewsByClass(paramString);
    if ((localXulArrayList != null) && (localXulArrayList.size() > 0))
      return (XulView)localXulArrayList.get(0);
    return null;
  }

  private void firstPlayMovieLogic()
  {
    int i = this.result.sTopicPkgCntLstStructs.size();
    SpecialTopicPkgCntLstStruct localSpecialTopicPkgCntLstStruct;
    for (int j = 0; ; j++)
    {
      localSpecialTopicPkgCntLstStruct = null;
      if (j < i)
      {
        if (((j == 0) && (i == 1)) || ((j == 1) && (i > 1)))
        {
          localSpecialTopicPkgCntLstStruct = (SpecialTopicPkgCntLstStruct)this.result.sTopicPkgCntLstStructs.get(j);
          this.currentId = String.valueOf(j + 1);
        }
      }
      else
      {
        if (localSpecialTopicPkgCntLstStruct != null)
          break;
        return;
      }
    }
    getAccurateMovieInfo(localSpecialTopicPkgCntLstStruct);
    requestAndPlayMovie();
  }

  private void getAccurateMovieInfo(SpecialTopicPkgCntLstStruct paramSpecialTopicPkgCntLstStruct)
  {
    this.currentVideoId = paramSpecialTopicPkgCntLstStruct.video_id;
    this.currentVideoType = paramSpecialTopicPkgCntLstStruct.video_type;
    this.currentPacketId = paramSpecialTopicPkgCntLstStruct.packet_id;
    this.currentCategoryId = paramSpecialTopicPkgCntLstStruct.category_id;
    this.currentVideoIndex = paramSpecialTopicPkgCntLstStruct.video_index;
    this.currentImgUrl = paramSpecialTopicPkgCntLstStruct.img1;
    this.currentVideoPreviewType = paramSpecialTopicPkgCntLstStruct.video_preview_type;
    this.videoinfo.videoId = paramSpecialTopicPkgCntLstStruct.video_id;
    this.videoinfo.packageId = paramSpecialTopicPkgCntLstStruct.packet_id;
    this.videoinfo.categoryId = paramSpecialTopicPkgCntLstStruct.category_id;
    this.videoinfo.videoType = Integer.valueOf(paramSpecialTopicPkgCntLstStruct.video_type).intValue();
    Logger.i("getAccurateMovieInfo videoId=" + this.currentVideoId + " videoIndex=" + this.currentVideoIndex);
  }

  private void getSpecialContent(ArrayList<SpecialTopicPutInfo> paramArrayList)
  {
    ServerApiManager.i().APIGetSpecialTopicPkgContent(((SpecialTopicPutInfo)paramArrayList.get(0)).id, new SccmsApiGetSpecialTopicPkgContentLstTask.ISccmsApiGetSearchSpecialTopicPkgLstTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e(PopularMoviePreviewActivity.TAG, "APIGetSpecialTopicPkgContent failed!! " + paramAnonymousServerApiCommonError.toString());
        PopularMoviePreviewActivity.access$302(PopularMoviePreviewActivity.this, false);
        PopularMoviePreviewActivity.this.reportLoad(PopularMoviePreviewActivity.this.isLoadSuccess, null);
        PopularMoviePreviewActivity.this.dismissLoaddingDialog();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, SpecialTopicPkgCntLstInfo paramAnonymousSpecialTopicPkgCntLstInfo)
      {
        Logger.d(PopularMoviePreviewActivity.TAG, "APIGetSpecialTopicPkgContent success!! result = " + paramAnonymousSpecialTopicPkgCntLstInfo);
        if ((paramAnonymousSpecialTopicPkgCntLstInfo == null) || (paramAnonymousSpecialTopicPkgCntLstInfo.sTopicPkgCntLstStructs == null) || (paramAnonymousSpecialTopicPkgCntLstInfo.sTopicPkgCntLstStructs.size() == 0))
        {
          PopularMoviePreviewActivity.access$302(PopularMoviePreviewActivity.this, false);
          PopularMoviePreviewActivity.this.reportLoad(PopularMoviePreviewActivity.this.isLoadSuccess, null);
          return;
        }
        PopularMoviePreviewActivity.access$502(PopularMoviePreviewActivity.this, paramAnonymousSpecialTopicPkgCntLstInfo);
        PopularMoviePreviewActivity.this.movieDataStream.setBaseStream(PopularMoviePreviewActivity.this.bindData());
        if (PopularMoviePreviewActivity.this.xulIsReady())
        {
          PopularMoviePreviewActivity.this.xulRefreshBinding("movie_data");
          PopularMoviePreviewActivity.this.firstPlayMovieLogic();
        }
        PopularMoviePreviewActivity.this.dismissLoaddingDialog();
        PopularMoviePreviewActivity.access$302(PopularMoviePreviewActivity.this, true);
        PopularMoviePreviewActivity.this.reportLoad(PopularMoviePreviewActivity.this.isLoadSuccess, null);
      }
    });
  }

  private void getSpecialData()
  {
    String str = getIntent().getStringExtra("special_package_id");
    ServerApiManager.i().APIGetSpecialTopicPutData(str, null, "asset", new SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e(PopularMoviePreviewActivity.TAG, "Load special topic data failed!! " + paramAnonymousServerApiCommonError.toString());
        PopularMoviePreviewActivity.access$302(PopularMoviePreviewActivity.this, false);
        PopularMoviePreviewActivity.this.reportLoad(PopularMoviePreviewActivity.this.isLoadSuccess, null);
        PopularMoviePreviewActivity.this.dismissLoaddingDialog();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<SpecialTopicPutInfo> paramAnonymousArrayList)
      {
        Logger.e(PopularMoviePreviewActivity.TAG, "Load special topic data success!!result=" + paramAnonymousArrayList + ",result.size=" + paramAnonymousArrayList.size());
        if ((paramAnonymousArrayList == null) || (paramAnonymousArrayList.size() == 0) || (paramAnonymousArrayList.size() >= 2))
        {
          Logger.e(PopularMoviePreviewActivity.TAG, "Load special topic data failed!! ");
          PopularMoviePreviewActivity.access$302(PopularMoviePreviewActivity.this, false);
          PopularMoviePreviewActivity.this.reportLoad(PopularMoviePreviewActivity.this.isLoadSuccess, null);
          PopularMoviePreviewActivity.this.dismissLoaddingDialog();
        }
        while (paramAnonymousArrayList.get(0) == null)
          return;
        PopularMoviePreviewActivity.this.getSpecialContent(paramAnonymousArrayList);
      }
    });
  }

  private void goPlayMovieLogic(Bundle paramBundle)
  {
    this.currentId = paramBundle.getString("id");
    VideoInfo localVideoInfo1 = this.videoinfo;
    String str1 = paramBundle.getString("video_id");
    this.currentVideoId = str1;
    localVideoInfo1.videoId = str1;
    this.currentVideoType = paramBundle.getString("video_type");
    VideoInfo localVideoInfo2 = this.videoinfo;
    String str2 = paramBundle.getString("packet_id");
    this.currentPacketId = str2;
    localVideoInfo2.packageId = str2;
    VideoInfo localVideoInfo3 = this.videoinfo;
    String str3 = paramBundle.getString("category_id");
    this.currentCategoryId = str3;
    localVideoInfo3.categoryId = str3;
    this.currentVideoIndex = paramBundle.getString("video_index");
    this.currentImgUrl = paramBundle.getString("img_bg");
    this.currentVideoPreviewType = paramBundle.getString("video_preview_type");
    this.videoinfo.videoType = XulUtils.tryParseInt(this.currentVideoType);
    setXulViewStyle(this.posterArea, "display", "none");
    setXulViewStyle(this.movieDetailArea, "display", "block");
    setXulViewStyle(this.playTips, "display", "block");
    setDetailInfo(paramBundle.getString("name"), paramBundle.getString("label_type"), paramBundle.getString("summary"), paramBundle.getString("video_wish"), paramBundle.getString("is_online"), paramBundle.getString("type"), paramBundle.getString("video_id"));
    requestAndPlayMovie();
  }

  private boolean keySetPageIndicator(int paramInt)
  {
    Pair localPair = findFocusView();
    if (((Integer)localPair.first).intValue() != -1)
    {
      if (paramInt == 21)
      {
        if (((Integer)localPair.first).intValue() == 0)
        {
          xulRequestFocus((XulView)this.content.get(-1 + this.content.size()));
          return true;
        }
        this.pageIndicatorLeft.setAttr("img.1.visible", "true");
        this.pageIndicatorLeft.setAttr("img.0.visible", "false");
        this.pageIndicatorLeft.resetRender();
        resetPageIndicator(1000);
        return false;
      }
      if (paramInt == 22)
      {
        if (((Integer)localPair.first).intValue() == -1 + this.content.size())
        {
          xulRequestFocus((XulView)this.content.get(0));
          return true;
        }
        this.pageIndicatorRight.setAttr("img.1.visible", "true");
        this.pageIndicatorRight.setAttr("img.0.visible", "false");
        this.pageIndicatorRight.resetRender();
        resetPageIndicator(2000);
        return false;
      }
    }
    return false;
  }

  private void onEvent2ChangeMedia()
  {
    Logger.i(TAG, "onEvent2ChangeMedia");
    this.videoView.stopMedia();
    this.isError = false;
  }

  private void playNextMovieLogic()
  {
    if (isFinishing())
    {
      Logger.d(TAG, "not running. stop playNextMovieLogic...");
      return;
    }
    int i = this.result.sTopicPkgCntLstStructs.size();
    int j = XulUtils.tryParseInt(this.currentId);
    SpecialTopicPkgCntLstStruct localSpecialTopicPkgCntLstStruct;
    if (j == i)
    {
      localSpecialTopicPkgCntLstStruct = (SpecialTopicPkgCntLstStruct)this.result.sTopicPkgCntLstStructs.get(0);
      this.currentId = String.valueOf(1);
      getAccurateMovieInfo(localSpecialTopicPkgCntLstStruct);
      if (this.posterArea.isVisible())
        break label147;
      setDetailInfo(localSpecialTopicPkgCntLstStruct.name, localSpecialTopicPkgCntLstStruct.label_type, localSpecialTopicPkgCntLstStruct.summary, localSpecialTopicPkgCntLstStruct.video_wish, String.valueOf(localSpecialTopicPkgCntLstStruct.is_online), localSpecialTopicPkgCntLstStruct.type, localSpecialTopicPkgCntLstStruct.video_id);
    }
    while (true)
    {
      requestAndPlayMovie();
      return;
      localSpecialTopicPkgCntLstStruct = (SpecialTopicPkgCntLstStruct)this.result.sTopicPkgCntLstStructs.get(j);
      this.currentId = String.valueOf(j + 1);
      break;
      label147: findTargetView();
    }
  }

  private void regularlyTips()
  {
    this.executor = new ScheduledThreadPoolExecutor(1);
    this.executor.scheduleAtFixedRate(new Runnable()
    {
      public void run()
      {
        if (PopularMoviePreviewActivity.this.playTips.isVisible())
        {
          if (!PopularMoviePreviewActivity.this.videoView.isComeToEnd())
            break label64;
          if (!PopularMoviePreviewActivity.this.hasBeenSet)
          {
            PopularMoviePreviewActivity.this.setXulViewAttr(PopularMoviePreviewActivity.this.playTips, "text", "即将播放“下一部影片名”");
            PopularMoviePreviewActivity.access$1702(PopularMoviePreviewActivity.this, true);
          }
        }
        return;
        label64: PopularMoviePreviewActivity.access$1702(PopularMoviePreviewActivity.this, false);
        if (PopularMoviePreviewActivity.this.accumulation % 20 == 0)
        {
          PopularMoviePreviewActivity.access$1902(PopularMoviePreviewActivity.this, 0);
          PopularMoviePreviewActivity.access$2002(PopularMoviePreviewActivity.this, PopularMoviePreviewActivity.this.count % 2);
          PopularMoviePreviewActivity.this.setXulViewAttr(PopularMoviePreviewActivity.this.playTips, "text", PopularMoviePreviewActivity.this.TIPS[PopularMoviePreviewActivity.this.count]);
          PopularMoviePreviewActivity.access$2008(PopularMoviePreviewActivity.this);
        }
        PopularMoviePreviewActivity.access$1908(PopularMoviePreviewActivity.this);
      }
    }
    , 0L, 500L, TimeUnit.MILLISECONDS);
  }

  private void requestAndPlayMovie()
  {
    if (TextUtils.isEmpty(this.currentImgUrl))
      xulSetBackground("");
    while (true)
    {
      ServerApiManager localServerApiManager = ServerApiManager.i();
      String str1 = this.currentVideoId;
      int i = XulUtils.tryParseInt(this.currentVideoPreviewType);
      String str2 = this.currentPacketId;
      String str3 = this.currentCategoryId;
      int j = XulUtils.tryParseInt(this.currentVideoIndex);
      SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener local10 = new SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          if (!PopularMoviePreviewActivity.this.taskControl.checkTaskValid(paramAnonymousServerApiTaskInfo.getTaskId(), PopularMoviePreviewActivity.this.markedLabel))
          {
            Logger.i(PopularMoviePreviewActivity.TAG, "SccmsApiRequestVideoPlayV2TaskListener.onError() invalid task");
            return;
          }
          PopularMoviePreviewActivity.this.onEvent2ChangeMedia();
          PopularMoviePreviewActivity.this.playNextMovieLogic();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, PlayInfoV2 paramAnonymousPlayInfoV2)
        {
          Logger.i(PopularMoviePreviewActivity.TAG, "SccmsApiRequestVideoPlayV2TaskListener.onSuccess() PlayInfo:" + paramAnonymousPlayInfoV2.toString());
          if (!PopularMoviePreviewActivity.this.taskControl.checkTaskValid(paramAnonymousServerApiTaskInfo.getTaskId(), PopularMoviePreviewActivity.this.markedLabel))
          {
            Logger.i(PopularMoviePreviewActivity.TAG, "SccmsApiRequestVideoPlayV2TaskListener.onError() invalid task");
            return;
          }
          PopularMoviePreviewActivity.access$2402(PopularMoviePreviewActivity.this, paramAnonymousPlayInfoV2.video_id);
          PopularMoviePreviewActivity.access$2502(PopularMoviePreviewActivity.this, paramAnonymousPlayInfoV2.import_id);
          PopularMoviePreviewActivity.access$2602(PopularMoviePreviewActivity.this, paramAnonymousPlayInfoV2.serial_id);
          PopularMoviePreviewActivity.access$2702(PopularMoviePreviewActivity.this, paramAnonymousPlayInfoV2.index_id);
          PopularMoviePreviewActivity.access$2802(PopularMoviePreviewActivity.this, paramAnonymousPlayInfoV2.index_import_id);
          PopularMoviePreviewActivity.access$2902(PopularMoviePreviewActivity.this, paramAnonymousPlayInfoV2.index_serial_id);
          if (TextUtils.isEmpty(paramAnonymousPlayInfoV2.playUrl))
          {
            PopularMoviePreviewActivity.this.onEvent2ChangeMedia();
            PopularMoviePreviewActivity.this.playNextMovieLogic();
            return;
          }
          PopularMoviePreviewActivity.this.videoView.setUrl(paramAnonymousPlayInfoV2.playUrl);
        }
      };
      int k = localServerApiManager.ApiRequestVideoPlayV2(str1, i, "0", str2, str3, j, "", "", "", "", "", "", "", "1", local10);
      this.markedLabel = GeneralUtils.MD5(this.currentVideoId + this.currentVideoPreviewType + this.currentVideoIndex);
      this.taskControl.addTaskLabel(k, "videoPlayV2", this.markedLabel);
      return;
      xulSetBackground("effect:blur:" + this.currentImgUrl);
    }
  }

  private void resetPageIndicator(int paramInt)
  {
    if (this.pageIndicatorHandler.hasMessages(paramInt))
      this.pageIndicatorHandler.removeMessages(paramInt);
    this.pageIndicatorHandler.sendEmptyMessageDelayed(paramInt, 400L);
  }

  private void setDetailInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    setXulViewAttr(this.movieMainName, "text", paramString1);
    setXulViewAttr(this.movieMainType, "text", "类型:" + paramString2);
    setXulViewAttr(this.movieDesc, "text", paramString3);
    setWishNumber(paramString4);
    if ("1".equals(paramString5))
    {
      setXulViewStyle(this.addWishButton, "display", "block");
      setXulViewAttr(this.addWishButton, "button_type", "play");
      this.addWishButton.removeClass("add_wish_mode");
      this.addWishButton.removeClass("cancel_wish_mode");
      this.addWishButton.addClass("play_mode");
      this.addWishButton.resetRender();
    }
    do
    {
      return;
      setXulViewAttr(this.addWishButton, "button_type", "wish");
      setXulViewStyle(this.addWishButton, "display", "none");
      this.addWishButton.addClass("add_wish_mode");
      this.addWishButton.removeClass("play_mode");
      this.addWishButton.resetRender();
    }
    while (!"preview".equals(paramString6));
    checkWish(paramString7);
  }

  private void setWishNumber(String paramString)
  {
    if (XulUtils.tryParseInt(paramString) >= 10000)
    {
      setXulViewStyle(this.wishNumFirst, "display", "block");
      setXulViewStyle(this.wishNum, "display", "block");
      setXulViewStyle(this.wishText, "display", "block");
      setXulViewAttr(this.wishNumFirst, "text", paramString.substring(0, 1));
      setXulViewAttr(this.wishNum, "text", paramString.substring(1, paramString.length()));
      setXulViewStyle(this.wishText, "display", "block");
      return;
    }
    setXulViewStyle(this.wishNumFirst, "display", "none");
    setXulViewStyle(this.wishNum, "display", "none");
    setXulViewStyle(this.wishText, "display", "none");
  }

  private void setXulViewAttr(XulView paramXulView, String paramString1, String paramString2)
  {
    if (paramXulView != null)
    {
      paramXulView.setAttr(paramString1, paramString2);
      paramXulView.resetRender();
    }
  }

  private void setXulViewStyle(XulView paramXulView, String paramString1, String paramString2)
  {
    if (paramXulView != null)
    {
      paramXulView.setStyle(paramString1, paramString2);
      paramXulView.resetRender();
    }
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    boolean bool = true;
    if (!xulIsReady())
      bool = false;
    do
    {
      do
      {
        return bool;
        if (!this.videoView.isFullScreen())
          break;
      }
      while ((paramKeyEvent.getAction() != 0) || (paramKeyEvent.getKeyCode() != 4));
      this.videoView.restoreSize();
      return bool;
      if ((paramKeyEvent.getAction() != 0) || ((paramKeyEvent.getKeyCode() != 21) && (paramKeyEvent.getKeyCode() != 22)))
        break;
      if (this.videoViewBorder.hasFocus())
      {
        setXulViewStyle(this.posterArea, "display", "block");
        setXulViewStyle(this.playTips, "display", "none");
        setXulViewStyle(this.movieDetailArea, "display", "none");
        findTargetView();
        this.videoViewBorder.addClass("no_focus");
        return bool;
      }
    }
    while (keySetPageIndicator(paramKeyEvent.getKeyCode()));
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    showLoaddingDialog();
  }

  protected void onDestroy()
  {
    if (this.executor != null)
      this.executor.shutdown();
    super.onDestroy();
  }

  protected void onRestart()
  {
    super.onRestart();
    reportLoad(this.isLoadSuccess, null);
  }

  protected void onStop()
  {
    super.onStop();
    reportExit(this.isLoadSuccess, null);
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    Logger.i(TAG, "xulDoAction action=" + paramString1 + " type=" + paramString2 + " command" + paramString3 + " userdata=" + paramObject);
    if ("user_cmd".equals(paramString2))
    {
      if ((paramObject instanceof XulDataNode));
      for (Bundle localBundle = xulArgListToBundle((XulDataNode)paramObject); ; localBundle = new Bundle())
      {
        onEvent2ChangeMedia();
        goPlayMovieLogic(localBundle);
        return true;
      }
    }
    if ("video_view_click".equals(paramString2))
    {
      this.videoView.fullScreen();
      return true;
    }
    try
    {
      str = new JSONObject(paramString3).optString("cmd");
      if ("cancelWish".equals(str))
      {
        Logger.i(TAG, "cancelWish");
        showLoaddingDialog();
        cancelWishRequest();
      }
      while (true)
      {
        return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
        if (!"addWish".equals(str))
          break;
        Logger.i(TAG, "addWish");
        showLoaddingDialog();
        addWishRequest();
      }
    }
    catch (JSONException localJSONException)
    {
      while (true)
      {
        String str;
        localJSONException.printStackTrace();
        continue;
        if ("play".equals(str))
        {
          Logger.i(TAG, "play");
          Intent localIntent = new Intent();
          localIntent.setClass(this, ActivityAdapter.getInstance().getMPlayer());
          PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
          VideoInfo localVideoInfo = new VideoInfo();
          localVideoInfo.videoId = this.currentVideoId;
          localVideoInfo.videoType = XulUtils.tryParseInt(this.currentVideoType);
          localPlayerIntentParams.nns_videoInfo = localVideoInfo;
          localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
          localIntent.addFlags(8388608);
          startActivity(localIntent);
        }
        else if ("bindingUpdated".equals(str))
        {
          Logger.i(TAG, "bindingUpdated");
          if (!this.bindingFinish)
          {
            this.bindingFinish = true;
            xulPostDelay(new Runnable()
            {
              public void run()
              {
                XulArrayList localXulArrayList = PopularMoviePreviewActivity.this.xulFindViewsByClass("poster");
                if (localXulArrayList.size() > 1)
                {
                  PopularMoviePreviewActivity.this.xulRequestFocus((XulView)localXulArrayList.get(1));
                  return;
                }
                PopularMoviePreviewActivity.this.xulRequestFocus((XulView)localXulArrayList.get(0));
              }
            }
            , 500L);
          }
        }
      }
    }
  }

  protected InputStream xulGetAppData(XulWorker.DownloadItem paramDownloadItem, String paramString)
  {
    if ("movie_data".equals(paramString))
    {
      if (this.movieDataStream.isReady())
        return this.movieDataStream;
      return null;
    }
    return super.xulGetAppData(paramDownloadItem, paramString);
  }

  protected void xulOnRenderIsReady()
  {
    super.xulOnRenderIsReady();
    getSpecialData();
    this.videoView = ((XulExt_SimpleVideoView)xulFindViewById("simple_video_view").getExternalView());
    this.videoView.setOnPlayerEventListener(new XulExt_SimpleVideoView.PlayerEvent()
    {
      public void onCompletion()
      {
        Logger.i(PopularMoviePreviewActivity.TAG, "this movie has completed");
        PopularMoviePreviewActivity.this.onEvent2ChangeMedia();
        PopularMoviePreviewActivity.this.playNextMovieLogic();
      }

      public void onError()
      {
        Logger.i(PopularMoviePreviewActivity.TAG, "OnErrorListener.onError() playNextMovie");
        PopularMoviePreviewActivity.access$1402(PopularMoviePreviewActivity.this, true);
        PopularMoviePreviewActivity.this.onEvent2ChangeMedia();
        PopularMoviePreviewActivity.this.playNextMovieLogic();
      }

      public void onPrepared()
      {
      }
    });
    this.videoViewBorder = xulFindViewById("video_view_border");
    this.playTips = xulFindViewById("play_tips");
    this.posterArea = xulFindViewById("poster_area");
    this.pageIndicatorLeft = xulFindViewById("page_indicator_left");
    this.pageIndicatorRight = xulFindViewById("page_indicator_right");
    this.movieDetailArea = xulFindViewById("movie_detail_area");
    this.movieMainName = xulFindViewById("movie_main_name");
    this.movieMainType = xulFindViewById("movie_main_type");
    this.wishNumFirst = findViewByClass("wish_num_first_font");
    this.wishNum = findViewByClass("wish_num_font");
    this.wishText = findViewByClass("wish_font");
    this.movieDesc = xulFindViewById("movie_desc");
    this.addWishButton = findViewByClass("add_wish_button");
    regularlyTips();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.PopularMoviePreviewActivity
 * JD-Core Version:    0.6.2
 */