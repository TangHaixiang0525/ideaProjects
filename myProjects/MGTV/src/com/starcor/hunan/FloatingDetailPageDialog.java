package com.starcor.hunan;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Window;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.CollectListItem;
import com.starcor.core.domain.FilmListPageInfo;
import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.domain.MediaInfo;
import com.starcor.core.domain.NewDetailedFilmData;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.exception.ApplicationException;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.logic.UserCPLLogic;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.Tools;
import com.starcor.hunan.interfaces.SuccessCallBack;
import com.starcor.hunan.service.CollectAndPlayListLogic;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.hunan.widget.XULDialog;
import com.starcor.report.ReportArea;
import com.starcor.sccms.api.SccmsAPIGetNewDetailedDataByVideoIdTask.IGetNewDetailedDataByVideoIdListener;
import com.starcor.sccms.api.SccmsApiGetAssetsInfoByVideoIdTask.ISccmsApiGetAssetsInfoByVideoIdTaskListener;
import com.starcor.sccms.api.SccmsApiGetCatchVideoRecordV2Task.ISccmsApiGetCatchVideoRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetCollectRecordV2Task.ISccmsApiGetCollectRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoIndexListTask.ISccmsApiGetVideoIndexListTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoInfoV2Task.ISccmsApiGetVideoInfoV2TaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.ui.UITools;
import com.starcor.xul.XulPendingInputStream;
import com.starcor.xul.XulView;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class FloatingDetailPageDialog
{
  private static final int MIN_AUDIENCE_NUMBER = 10000;
  private static final String NUMBER_EPISODE_UI_STYLE = "0";
  private static final String TAG = "FloatingDetailPageDialog";
  private final String ACTOR = "演员：";
  private final String ARTIST = "艺人：";
  private final String DIRECTOR = "导演：";
  private final String HOST = "主持人：";
  private boolean audienceNumberOK;
  private XulPendingInputStream audienceNumberStream = new XulPendingInputStream();
  private CollectAndPlayListLogic collectLogic;
  private Context context;
  private XULDialog dialog;
  private DisplayStyle displayStyle = DisplayStyle.MOVIE;
  private String episodeDisplayStyle = "0";
  private boolean isFromOut;
  private boolean isShowDialog;
  private OnDismissListener onDismissListener = null;
  private String outPlayOriginal = "";
  private String packageId;
  private boolean packageIdOK;
  private int targetIndex = -1;
  private int uiStyle;
  private boolean videoEpisodeOK;
  private XulPendingInputStream videoEpisodeStream = new XulPendingInputStream();
  private String videoId;
  private ArrayList<VideoIndex> videoIndexes;
  private VideoInfo videoInfo;
  private XulPendingInputStream videoInfoStream = new XulPendingInputStream();
  private int videoType;
  private XulPendingInputStream videoWatchFocusEpisodeStream = new XulPendingInputStream();

  public FloatingDetailPageDialog(String paramString, int paramInt1, int paramInt2, Context paramContext)
  {
    this(paramString, paramInt1, paramContext);
    this.targetIndex = paramInt2;
  }

  public FloatingDetailPageDialog(String paramString, int paramInt, Context paramContext)
  {
    this.context = paramContext;
    this.videoId = paramString;
    this.videoType = paramInt;
    this.collectLogic = new CollectAndPlayListLogic();
    initDetailData();
    showLoaddingDialog();
  }

  public FloatingDetailPageDialog(String paramString, int paramInt, boolean paramBoolean, Context paramContext)
  {
    this(paramString, paramInt, paramContext);
    this.isFromOut = paramBoolean;
  }

  private void dismissLoaddingDialog()
  {
    ((DialogActivity)this.context).dismissLoaddingDialog();
  }

  private ByteArrayInputStream getAudienceNumber(int paramInt)
  {
    try
    {
      String str = String.valueOf(paramInt);
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "audienceNumber");
      writeNodeValue(localXmlSerializer, "k", "人看过");
      writeNodeValue(localXmlSerializer, "v1", str.substring(0, 1));
      writeNodeValue(localXmlSerializer, "v2", str.substring(1, str.length()));
      localXmlSerializer.endTag(null, "audienceNumber");
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

  private String[] getExactInfo()
  {
    String[] arrayOfString = { "", "" };
    switch (this.uiStyle)
    {
    default:
      if (!TextUtils.isEmpty(this.videoInfo.director))
        arrayOfString[0] = ("导演：" + this.videoInfo.director);
      if (!TextUtils.isEmpty(this.videoInfo.actor))
        arrayOfString[1] = ("演员：" + this.videoInfo.actor);
      break;
    case 4:
    case 6:
    case 3:
    case 5:
    }
    while (true)
    {
      Logger.d("FloatingDetailPageDialog", "需要显示的info[0]" + arrayOfString[0] + " info[1]" + arrayOfString[1]);
      return arrayOfString;
      if (!TextUtils.isEmpty(this.videoInfo.director))
      {
        arrayOfString[0] = ("主持人：" + this.videoInfo.director);
        continue;
        if (!TextUtils.isEmpty(this.videoInfo.actor))
          arrayOfString[1] = ("艺人：" + this.videoInfo.actor);
      }
    }
  }

  private String getMediaQuality(int paramInt)
  {
    String str1 = GlobalLogic.getInstance().getQuality();
    String str2 = str1;
    ArrayList localArrayList = new ArrayList();
    if ((this.videoInfo != null) && (this.videoInfo.indexList != null))
    {
      Iterator localIterator1 = this.videoInfo.indexList.iterator();
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
            str2 = ((MediaInfo)localVideoIndex.mediaInfo.get(0)).type;
        }
      }
    }
    Logger.i("quality=" + str2);
    return str2;
  }

  private ByteArrayInputStream getVideoEpisode()
  {
    XmlSerializer localXmlSerializer;
    StringWriter localStringWriter;
    while (true)
    {
      VideoIndex localVideoIndex;
      try
      {
        if ((this.videoIndexes == null) || (this.videoIndexes.size() <= 0))
          break label270;
        localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        localStringWriter = new StringWriter();
        localXmlSerializer.setOutput(localStringWriter);
        localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer.startTag(null, "episode");
        Iterator localIterator = this.videoIndexes.iterator();
        if (!localIterator.hasNext())
          break;
        localVideoIndex = (VideoIndex)localIterator.next();
        localXmlSerializer.startTag(null, "item");
        if (this.displayStyle == DisplayStyle.WATCH_FOCUS_EPISODE)
        {
          writeNodeValue(localXmlSerializer, "name", localVideoIndex.desc);
          writeNodeValue(localXmlSerializer, "video_index", String.valueOf(localVideoIndex.index));
          if (UserCPLLogic.getInstance().isPlayRecordExistsIncludeLocal(this.videoId, localVideoIndex.index))
            writeNodeValue(localXmlSerializer, "view_state", "1");
          localXmlSerializer.endTag(null, "item");
          continue;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      if (this.displayStyle == DisplayStyle.EPISODE)
        writeNodeValue(localXmlSerializer, "name", String.valueOf(1 + localVideoIndex.index));
    }
    localXmlSerializer.endTag(null, "episode");
    localXmlSerializer.endDocument();
    localXmlSerializer.flush();
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
    return localByteArrayInputStream;
    label270: return null;
  }

  private ByteArrayInputStream getVideoInfo()
  {
    if (this.videoInfo == null)
      return null;
    XmlSerializer localXmlSerializer;
    while (true)
    {
      try
      {
        localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        StringWriter localStringWriter = new StringWriter();
        localXmlSerializer.setOutput(localStringWriter);
        localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer.startTag(null, "info");
        String str1 = this.videoInfo.imgVUrl;
        if (TextUtils.isEmpty(str1))
          str1 = "effect:mirror:" + this.videoInfo.imgHUrl;
        writeNodeValue(localXmlSerializer, "image", str1);
        String str2 = this.videoInfo.name;
        if ((hasElement(str2)) && (str2.length() > 8))
          str2 = str2.substring(0, 7) + "...";
        if (hasElement(this.videoInfo.showTime))
        {
          String str6 = this.videoInfo.showTime;
          if (str6.length() > 4)
            str6 = str6.substring(0, 4);
          str2 = str2 + "(" + str6 + ")";
        }
        writeNodeValue(localXmlSerializer, "name", str2);
        String str3 = this.videoInfo.kind;
        if (hasElement(str3))
        {
          Matcher localMatcher = Pattern.compile("([^/]+)(/[^/]+){0,2}").matcher(str3);
          if (localMatcher.find())
            str3 = localMatcher.group();
        }
        writeNodeValue(localXmlSerializer, "kind", str3);
        String[] arrayOfString = getExactInfo();
        writeNodeValue(localXmlSerializer, "director", arrayOfString[0]);
        writeNodeValue(localXmlSerializer, "actor", arrayOfString[1]);
        writeNodeValue(localXmlSerializer, "desc", this.videoInfo.desc);
        if ((1 == this.uiStyle) || (1 + this.videoInfo.indexCurrent >= this.videoInfo.indexCount) || (!AppFuncCfg.FUNCTION_ENABLE_TRACEPLAY))
          break;
        writeNodeValue(localXmlSerializer, "button_type", "trace");
        if (UserCPLLogic.getInstance().isTracePlayExists(this.videoId))
        {
          str5 = "取消追剧";
          writeNodeValue(localXmlSerializer, "button_name", str5);
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
      String str5 = "追剧";
    }
    writeNodeValue(localXmlSerializer, "button_type", "collect");
    if (UserCPLLogic.getInstance().isCollectExists(this.videoId));
    for (String str4 = "取消收藏"; ; str4 = "收藏")
    {
      writeNodeValue(localXmlSerializer, "button_name", str4);
      break;
    }
  }

  private boolean hasElement(String paramString)
  {
    return (!TextUtils.isEmpty(paramString)) && (!"null".equalsIgnoreCase(paramString));
  }

  private void initDetailData()
  {
    ServerApiManager.i().APIGetVideoInfoV2(this.videoId, this.videoType, new SccmsApiGetVideoInfoV2TaskListener());
  }

  private void judgeDisplayStyle()
  {
    if ((this.uiStyle == 0) || (this.uiStyle == 1))
    {
      this.displayStyle = DisplayStyle.MOVIE;
      return;
    }
    if ("0".equals(this.episodeDisplayStyle))
    {
      this.displayStyle = DisplayStyle.EPISODE;
      return;
    }
    this.displayStyle = DisplayStyle.WATCH_FOCUS_EPISODE;
  }

  private boolean onDoAction(XulView paramXulView, String paramString1, String paramString2, Object paramObject)
    throws JSONException
  {
    if ((this.videoInfo == null) || (this.collectLogic == null))
      return false;
    String str = new JSONObject(paramString2).optString("cmd");
    if ("deleteTrace".equals(str))
    {
      Logger.i("FloatingDetailPageDialog", "deleteTrace");
      showLoaddingDialog();
      this.collectLogic.delTracePlay(new SuccessCallBack()
      {
        public void getDataError(String paramAnonymousString, int paramAnonymousInt)
        {
          FloatingDetailPageDialog.this.dismissLoaddingDialog();
          UITools.ShowCustomToast(FloatingDetailPageDialog.this.context, "取消追剧失败");
        }

        public void getDataSuccess(Void paramAnonymousVoid)
        {
          FloatingDetailPageDialog.this.xulFireEvent("appEvents:actionSuccess", new Object[] { "追剧" });
          FloatingDetailPageDialog.this.dismissLoaddingDialog();
          UITools.ShowCustomToast(FloatingDetailPageDialog.this.context, "取消追剧成功");
        }
      }
      , this.videoInfo.videoId, this.videoInfo);
      return true;
    }
    if ("addTrace".equals(str))
    {
      Logger.i("FloatingDetailPageDialog", "addTrace");
      showLoaddingDialog();
      this.collectLogic.addTracePlay(new SuccessCallBack()
      {
        public void getDataError(String paramAnonymousString, int paramAnonymousInt)
        {
          FloatingDetailPageDialog.this.dismissLoaddingDialog();
          UITools.ShowCustomToast(FloatingDetailPageDialog.this.context, "追剧失败");
        }

        public void getDataSuccess(Void paramAnonymousVoid)
        {
          FloatingDetailPageDialog.this.xulFireEvent("appEvents:actionSuccess", new Object[] { "取消追剧" });
          FloatingDetailPageDialog.this.dismissLoaddingDialog();
          UITools.ShowCustomToast(FloatingDetailPageDialog.this.context, "追剧成功");
        }
      }
      , this.videoInfo);
      return true;
    }
    if ("deleteCollect".equals(str))
    {
      Logger.i("FloatingDetailPageDialog", "deleteCollect");
      showLoaddingDialog();
      this.collectLogic.delCollect(new SuccessCallBack()
      {
        public void getDataError(String paramAnonymousString, int paramAnonymousInt)
        {
          FloatingDetailPageDialog.this.dismissLoaddingDialog();
          UITools.ShowCustomToast(FloatingDetailPageDialog.this.context, "取消收藏失败");
        }

        public void getDataSuccess(Void paramAnonymousVoid)
        {
          FloatingDetailPageDialog.this.xulFireEvent("appEvents:actionSuccess", new Object[] { "收藏" });
          FloatingDetailPageDialog.this.dismissLoaddingDialog();
          UITools.ShowCustomToast(FloatingDetailPageDialog.this.context, "取消收藏成功");
        }
      }
      , this.videoInfo.videoId);
      return true;
    }
    if ("addCollect".equals(str))
    {
      Logger.i("FloatingDetailPageDialog", "addCollect");
      showLoaddingDialog();
      this.collectLogic.addCollect(new SuccessCallBack()
      {
        public void getDataError(String paramAnonymousString, int paramAnonymousInt)
        {
          FloatingDetailPageDialog.this.dismissLoaddingDialog();
          UITools.ShowCustomToast(FloatingDetailPageDialog.this.context, "收藏失败");
        }

        public void getDataSuccess(Void paramAnonymousVoid)
        {
          FloatingDetailPageDialog.this.xulFireEvent("appEvents:actionSuccess", new Object[] { "取消收藏" });
          FloatingDetailPageDialog.this.dismissLoaddingDialog();
          UITools.ShowCustomToast(FloatingDetailPageDialog.this.context, "收藏成功");
        }
      }
      , this.videoInfo);
      return true;
    }
    return false;
  }

  private void openVideoFromEpisode(int paramInt)
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
    if (localObject == null)
    {
      Logger.e("FloatingDetailPageDialog", "没有找到对应的剧集");
      return;
    }
    PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
    localPlayerIntentParams.subfix_title = localObject.name;
    localPlayerIntentParams.nns_index = localObject.index;
    localPlayerIntentParams.nns_videoInfo = this.videoInfo;
    localPlayerIntentParams.nns_videoInfo.packageId = this.packageId;
    localPlayerIntentParams.nns_mediaIndexList = this.videoIndexes;
    if ("1".equals(this.outPlayOriginal))
      localPlayerIntentParams.out_play = (Tools.getOutPlayOriginal() + "-" + getOutPlayOriginal());
    localPlayerIntentParams.mediaQuality = getMediaQuality(paramInt);
    Intent localIntent = new Intent(this.context, ActivityAdapter.getInstance().getMPlayer());
    localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_EXIT_APP, false);
    localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
    this.context.startActivity(localIntent);
    Logger.d("intentDatasubfix_title=" + localPlayerIntentParams.subfix_title + " index=" + localPlayerIntentParams.nns_index + " packageId=" + localPlayerIntentParams.nns_videoInfo.packageId + " quality=" + localPlayerIntentParams.mediaQuality);
  }

  private void openVideoFromMovie()
  {
    int i = UserCPLLogic.getInstance().getLastPlayRecord(this.videoId);
    if (i < 0)
      i = 0;
    boolean bool1 = UserCPLLogic.getInstance().isEpisodeFinished(this.videoId, i);
    Logger.i("FloatingDetailPageDialog", "isEpisodeFinished=" + bool1);
    if (bool1)
    {
      int j = i + 1;
      Logger.i("FloatingDetailPageDialog", "nextIndex=" + j);
      Iterator localIterator = this.videoIndexes.iterator();
      do
      {
        boolean bool2 = localIterator.hasNext();
        k = 0;
        if (!bool2)
          break;
      }
      while (((VideoIndex)localIterator.next()).index != j);
      i = j;
      int k = 1;
      if (k == 0)
        i = 0;
    }
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = String.valueOf(i + 1);
    String str = String.format("%s集", arrayOfObject);
    PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
    localPlayerIntentParams.nns_index = i;
    localPlayerIntentParams.subfix_title = str;
    localPlayerIntentParams.nns_videoInfo = this.videoInfo;
    localPlayerIntentParams.nns_videoInfo.packageId = this.packageId;
    localPlayerIntentParams.nns_mediaIndexList = this.videoIndexes;
    if ("1".equals(this.outPlayOriginal))
      localPlayerIntentParams.out_play = (Tools.getOutPlayOriginal() + "-" + getOutPlayOriginal());
    localPlayerIntentParams.mediaQuality = getMediaQuality(i);
    Intent localIntent = new Intent(this.context, ActivityAdapter.getInstance().getMPlayer());
    localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_EXIT_APP, false);
    localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
    this.context.startActivity(localIntent);
    Logger.d("intentDatasubfix_title=" + localPlayerIntentParams.subfix_title + " index=" + localPlayerIntentParams.nns_index + " packageId=" + localPlayerIntentParams.nns_videoInfo.packageId + " quality=" + localPlayerIntentParams.mediaQuality);
  }

  private void refreshPlayedState(ArrayList<Integer> paramArrayList)
  {
    int i = UserCPLLogic.getInstance().getLastPlayRecord(this.videoId);
    paramArrayList.add(0, Integer.valueOf(i));
    xulFireEvent("appEvents:checkPlayedEpisode", paramArrayList.toArray());
    Logger.i("FloatingDetailPageDialog", "上次播放的集数是" + (i + 1));
  }

  private void refreshTraceOrCollectState()
  {
    showLoaddingDialog();
    if ((1 != this.uiStyle) && (1 + this.videoInfo.indexCurrent < this.videoInfo.indexCount) && (AppFuncCfg.FUNCTION_ENABLE_TRACEPLAY))
    {
      ServerApiManager.i().APIGetCatchVideoRecordV2(new SccmsApiGetCatchVideoRecordV2Task.ISccmsApiGetCatchVideoRecordV2TaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          FloatingDetailPageDialog.this.dismissLoaddingDialog();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<CollectListItem> paramAnonymousArrayList)
        {
          FloatingDetailPageDialog.this.dismissLoaddingDialog();
          UserCPLLogic.getInstance().dirtyTracePlayList();
          UserCPLLogic.getInstance().refreshTracePlayList(paramAnonymousArrayList);
          boolean bool = UserCPLLogic.getInstance().isTracePlayExists(FloatingDetailPageDialog.this.videoId);
          if (bool);
          for (String str = "取消追剧"; ; str = "追剧")
          {
            Logger.d("FloatingDetailPageDialog", "当前影片追剧状态 isTraced=" + bool + " state=" + str);
            FloatingDetailPageDialog.this.xulFireEvent("appEvents:actionSuccess", new Object[] { str });
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
        FloatingDetailPageDialog.this.dismissLoaddingDialog();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<CollectListItem> paramAnonymousArrayList)
      {
        FloatingDetailPageDialog.this.dismissLoaddingDialog();
        UserCPLLogic.getInstance().dirtyCollectList();
        UserCPLLogic.getInstance().refreshCollectList(paramAnonymousArrayList);
        boolean bool = UserCPLLogic.getInstance().isCollectExists(FloatingDetailPageDialog.this.videoId);
        if (bool);
        for (String str = "取消收藏"; ; str = "收藏")
        {
          Logger.d("FloatingDetailPageDialog", "当前影片收藏状态 isCollected=" + bool + " state=" + str);
          FloatingDetailPageDialog.this.xulFireEvent("appEvents:actionSuccess", new Object[] { str });
          return;
        }
      }
    });
  }

  private void selectTargetIndex(ArrayList<Integer> paramArrayList)
  {
    paramArrayList.add(0, Integer.valueOf(this.targetIndex));
    xulFireEvent("appEvents:checkPlayedEpisode", paramArrayList.toArray());
    Logger.i("FloatingDetailPageDialog", "目标集数是" + (1 + this.targetIndex));
  }

  private void showDialog()
  {
    if ((this.videoEpisodeOK) && (this.audienceNumberOK) && (this.packageIdOK))
    {
      this.dialog = new XULDialog(this.context, "FloatingDetailPage", null)
      {
        public boolean dispatchKeyEvent(KeyEvent paramAnonymousKeyEvent)
        {
          if ((DeviceInfo.isTCL_RT2995()) && (FloatingDetailPageDialog.this.isFromOut) && (isShowing()) && (paramAnonymousKeyEvent.getAction() == 0) && (paramAnonymousKeyEvent.getKeyCode() == 4))
          {
            Logger.i("FloatingDetailPageDialog", "exit FloatingDetailPageDialog");
            dismiss();
            FloatingDetailPageDialog.this.onDismissListener.onDismiss();
          }
          return super.dispatchKeyEvent(paramAnonymousKeyEvent);
        }

        protected boolean xulDoAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
        {
          Logger.i("FloatingDetailPageDialog", "xulDoAction action=" + paramAnonymousString1 + "type=" + paramAnonymousString2 + "command=" + paramAnonymousString3 + "userdata=" + paramAnonymousObject);
          if (paramAnonymousString2.equals("floatDetail"));
          while (true)
          {
            boolean bool2;
            try
            {
              boolean bool3 = FloatingDetailPageDialog.this.onDoAction(paramAnonymousXulView, paramAnonymousString1, paramAnonymousString3, paramAnonymousObject);
              bool2 = bool3;
              return bool2;
            }
            catch (Exception localException)
            {
              localException.printStackTrace();
              return false;
            }
            if (paramAnonymousString2.equals("episode_open_video"));
            try
            {
              int j = Integer.parseInt(paramAnonymousString3);
              i = j;
              FloatingDetailPageDialog.this.openVideoFromEpisode(i);
              boolean bool1 = paramAnonymousString2.equals("movie_open_video");
              bool2 = false;
              if (!bool1)
                continue;
              FloatingDetailPageDialog.this.openVideoFromMovie();
              return false;
            }
            catch (NumberFormatException localNumberFormatException)
            {
              while (true)
              {
                localNumberFormatException.printStackTrace();
                int i = 0;
              }
            }
          }
        }

        protected InputStream xulGetAppData(String paramAnonymousString)
        {
          XulPendingInputStream localXulPendingInputStream;
          if ("api/get_video_info".equals(paramAnonymousString))
          {
            boolean bool4 = FloatingDetailPageDialog.this.videoInfoStream.isReady();
            localXulPendingInputStream = null;
            if (bool4)
              localXulPendingInputStream = FloatingDetailPageDialog.this.videoInfoStream;
          }
          boolean bool1;
          do
          {
            boolean bool2;
            do
            {
              boolean bool3;
              do
              {
                return localXulPendingInputStream;
                if (!"api/get_video_episode".equals(paramAnonymousString))
                  break;
                bool3 = FloatingDetailPageDialog.this.videoEpisodeStream.isReady();
                localXulPendingInputStream = null;
              }
              while (!bool3);
              return FloatingDetailPageDialog.this.videoEpisodeStream;
              if (!"api/get_audience_number".equals(paramAnonymousString))
                break;
              bool2 = FloatingDetailPageDialog.this.audienceNumberStream.isReady();
              localXulPendingInputStream = null;
            }
            while (!bool2);
            return FloatingDetailPageDialog.this.audienceNumberStream;
            if (!"api/get_video_watch_focus_episode".equals(paramAnonymousString))
              break;
            bool1 = FloatingDetailPageDialog.this.videoWatchFocusEpisodeStream.isReady();
            localXulPendingInputStream = null;
          }
          while (!bool1);
          return FloatingDetailPageDialog.this.videoWatchFocusEpisodeStream;
          return super.xulGetAppData(paramAnonymousString);
        }

        protected void xulOnRenderIsReady()
        {
          if (FloatingDetailPageDialog.this.targetIndex == -1)
            FloatingDetailPageDialog.this.refreshPlayedState(new ArrayList());
          while (true)
          {
            super.xulOnRenderIsReady();
            return;
            FloatingDetailPageDialog.this.selectTargetIndex(new ArrayList());
          }
        }
      };
      dismissLoaddingDialog();
      showTargetView();
      this.dialog.getWindow().getAttributes().windowAnimations = 2131296260;
      this.dialog.show();
    }
  }

  private void showErrorDialogWithReport(int paramInt, String paramString1, String paramString2, ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
  {
    if (!this.isShowDialog)
    {
      ApplicationException.showErrorDialogWithReport(this.context, paramInt, paramString1, paramString2, paramServerApiTaskInfo, paramServerApiCommonError, ReportArea.getPageName(FloatingDetailPageDialog.class));
      this.isShowDialog = true;
    }
  }

  private void showLoaddingDialog()
  {
    ((DialogActivity)this.context).showLoaddingDialog();
  }

  private void showTargetView()
  {
    if (this.dialog == null);
    XulView localXulView3;
    do
    {
      XulView localXulView4;
      do
      {
        XulView localXulView2;
        do
        {
          return;
          if ((this.uiStyle != 0) && (this.uiStyle != 1))
            break;
          XulView localXulView1 = this.dialog.xulFindViewById("desc_label");
          localXulView1.setAttr("height", "200");
          localXulView1.resetRender();
          localXulView2 = this.dialog.xulFindViewById("play_button");
        }
        while (localXulView2 == null);
        localXulView2.setStyle("display", "block");
        localXulView2.resetRender();
        return;
        if (!"0".equals(this.episodeDisplayStyle))
          break;
        localXulView4 = this.dialog.xulFindViewById("episode_grid");
      }
      while (localXulView4 == null);
      localXulView4.setStyle("display", "block");
      localXulView4.resetRender();
      return;
      localXulView3 = this.dialog.xulFindViewById("watch_focus_episode_grid");
    }
    while (localXulView3 == null);
    localXulView3.setStyle("display", "block");
    localXulView3.resetRender();
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

  private boolean xulFireEvent(String paramString, Object[] paramArrayOfObject)
  {
    if (this.dialog == null)
      return false;
    return this.dialog.xulFireEvent(paramString, paramArrayOfObject);
  }

  public String getOutPlayOriginal()
  {
    return this.outPlayOriginal;
  }

  public void initExternalData()
  {
    ServerApiManager.i().APIGetAssetsInfoByVideoId(this.videoId, new SccmsApiGetMediaAssetsInfoJudgeVideoPacketTypeByVideoId(null));
    ServerApiManager.i().APIGetVideoIndexList(this.videoId, this.videoType, 0, 10000, false, new SccmsApiGetVideoIndexListTaskListener());
    ServerApiManager.i().APIGetNewDetailedDataByVideoId(this.videoId, new SccmsApiGetNewDetailedDataByVideoIdListener());
  }

  protected void onResume()
  {
    if ((this.videoIndexes == null) || (this.dialog == null) || (!this.dialog.isShowing()))
      return;
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.videoIndexes.iterator();
    while (localIterator.hasNext())
    {
      VideoIndex localVideoIndex = (VideoIndex)localIterator.next();
      if (UserCPLLogic.getInstance().isPlayRecordExistsIncludeLocal(this.videoId, localVideoIndex.index))
      {
        localArrayList.add(Integer.valueOf(localVideoIndex.index));
        Logger.d("FloatingDetailPageDialog", "exist video index" + localVideoIndex.index);
      }
    }
    refreshPlayedState(localArrayList);
    refreshTraceOrCollectState();
  }

  public void setOnDismissListener(OnDismissListener paramOnDismissListener)
  {
    this.onDismissListener = paramOnDismissListener;
  }

  public void setOutPlayOriginal(String paramString)
  {
    this.outPlayOriginal = paramString;
  }

  private static enum DisplayStyle
  {
    static
    {
      EPISODE = new DisplayStyle("EPISODE", 1);
      WATCH_FOCUS_EPISODE = new DisplayStyle("WATCH_FOCUS_EPISODE", 2);
      DisplayStyle[] arrayOfDisplayStyle = new DisplayStyle[3];
      arrayOfDisplayStyle[0] = MOVIE;
      arrayOfDisplayStyle[1] = EPISODE;
      arrayOfDisplayStyle[2] = WATCH_FOCUS_EPISODE;
    }
  }

  public static abstract interface OnDismissListener
  {
    public abstract void onDismiss();
  }

  private final class SccmsApiGetMediaAssetsInfoJudgeVideoPacketTypeByVideoId
    implements SccmsApiGetAssetsInfoByVideoIdTask.ISccmsApiGetAssetsInfoByVideoIdTaskListener
  {
    private SccmsApiGetMediaAssetsInfoJudgeVideoPacketTypeByVideoId()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      FloatingDetailPageDialog.this.showErrorDialogWithReport(11, "1002008", "ISccmsApiGetAssetsInfoByVideoIdTaskListener.onError", paramServerApiTaskInfo, paramServerApiCommonError);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, MediaAssetsInfo paramMediaAssetsInfo)
    {
      Logger.i("FloatingDetailPageDialog", "SccmsApiGetAssetsInfoByVideoId.onSuccess(), result:" + paramMediaAssetsInfo);
      if (paramMediaAssetsInfo == null)
      {
        FloatingDetailPageDialog.this.showErrorDialogWithReport(11, "1002008", "ISccmsApiGetAssetsInfoByVideoIdTaskListener.onSuccess result null", paramServerApiTaskInfo, null);
        return;
      }
      FloatingDetailPageDialog.access$2202(FloatingDetailPageDialog.this, paramMediaAssetsInfo.package_id);
      FloatingDetailPageDialog.access$2302(FloatingDetailPageDialog.this, true);
      FloatingDetailPageDialog.this.showDialog();
    }
  }

  class SccmsApiGetNewDetailedDataByVideoIdListener
    implements SccmsAPIGetNewDetailedDataByVideoIdTask.IGetNewDetailedDataByVideoIdListener
  {
    SccmsApiGetNewDetailedDataByVideoIdListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      FloatingDetailPageDialog.this.showErrorDialogWithReport(11, "1002008", "IGetNewDetailedDataByVideoIdListener.onError", paramServerApiTaskInfo, paramServerApiCommonError);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, NewDetailedFilmData paramNewDetailedFilmData)
    {
      if (paramNewDetailedFilmData != null)
      {
        if (!TextUtils.isEmpty(paramNewDetailedFilmData.getTotalClicks()));
        try
        {
          int j = Integer.valueOf(paramNewDetailedFilmData.getTotalClicks()).intValue();
          i = j;
          if (i >= 10000)
            FloatingDetailPageDialog.this.audienceNumberStream.setBaseStream(FloatingDetailPageDialog.this.getAudienceNumber(i));
          FloatingDetailPageDialog.access$3102(FloatingDetailPageDialog.this, true);
          FloatingDetailPageDialog.this.showDialog();
          return;
        }
        catch (NumberFormatException localNumberFormatException)
        {
          while (true)
          {
            localNumberFormatException.printStackTrace();
            int i = 0;
          }
        }
      }
      FloatingDetailPageDialog.this.showErrorDialogWithReport(11, "1002008", "IGetNewDetailedDataByVideoIdListener.onSuccess result null", paramServerApiTaskInfo, null);
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
      FloatingDetailPageDialog.this.showErrorDialogWithReport(11, "1002008", "ISccmsApiGetVideoIndexListTaskListener.onError", paramServerApiTaskInfo, paramServerApiCommonError);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, FilmListPageInfo paramFilmListPageInfo)
    {
      if ((paramFilmListPageInfo == null) || (paramFilmListPageInfo.getFilmInfo() == null))
        return;
      FloatingDetailPageDialog.access$2502(FloatingDetailPageDialog.this, paramFilmListPageInfo.getFilmInfo());
      FloatingDetailPageDialog.this.judgeDisplayStyle();
      if (FloatingDetailPageDialog.this.displayStyle == FloatingDetailPageDialog.DisplayStyle.WATCH_FOCUS_EPISODE)
        FloatingDetailPageDialog.this.videoWatchFocusEpisodeStream.setBaseStream(FloatingDetailPageDialog.this.getVideoEpisode());
      while (true)
      {
        FloatingDetailPageDialog.access$2902(FloatingDetailPageDialog.this, true);
        FloatingDetailPageDialog.this.showDialog();
        return;
        if (FloatingDetailPageDialog.this.displayStyle == FloatingDetailPageDialog.DisplayStyle.EPISODE)
          FloatingDetailPageDialog.this.videoEpisodeStream.setBaseStream(FloatingDetailPageDialog.this.getVideoEpisode());
      }
    }
  }

  class SccmsApiGetVideoInfoV2TaskListener
    implements SccmsApiGetVideoInfoV2Task.ISccmsApiGetVideoInfoV2TaskListener
  {
    SccmsApiGetVideoInfoV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      FloatingDetailPageDialog.this.dismissLoaddingDialog();
      FloatingDetailPageDialog.this.showErrorDialogWithReport(11, "1002008", "ISccmsApiGetVideoInfoV2TaskListener.onError", paramServerApiTaskInfo, paramServerApiCommonError);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, VideoInfo paramVideoInfo)
    {
      FloatingDetailPageDialog.this.dismissLoaddingDialog();
      if (paramVideoInfo == null)
        return;
      FloatingDetailPageDialog.access$1802(FloatingDetailPageDialog.this, paramVideoInfo);
      FloatingDetailPageDialog.access$1902(FloatingDetailPageDialog.this, paramVideoInfo.uiStyle);
      FloatingDetailPageDialog.this.videoInfoStream.setBaseStream(FloatingDetailPageDialog.this.getVideoInfo());
      if (!TextUtils.isEmpty(paramVideoInfo.index_ui_type))
        FloatingDetailPageDialog.access$2102(FloatingDetailPageDialog.this, paramVideoInfo.index_ui_type);
      Logger.d("FloatingDetailPageDialog", "详情获取到的uiStyle=" + FloatingDetailPageDialog.this.uiStyle + " 剧集列表展示样式=" + FloatingDetailPageDialog.this.episodeDisplayStyle);
      FloatingDetailPageDialog.this.initExternalData();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.FloatingDetailPageDialog
 * JD-Core Version:    0.6.2
 */