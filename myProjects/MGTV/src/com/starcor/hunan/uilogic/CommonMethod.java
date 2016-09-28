package com.starcor.hunan.uilogic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.MetadataGoup;
import com.starcor.core.domain.MetadataGroupPageIndex;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.domain.MovieData;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.DetailPageActivity;
import com.starcor.hunan.SplashActivity;
import com.starcor.xul.XulUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class CommonMethod
{
  private static final String TAG = "CommonMethod";

  public static Intent createCategoryIntent(String paramString1, String paramString2)
  {
    Logger.i("CommonMethod", "startCategoryActivityById  category_id:" + paramString1 + "  packet_id:" + paramString2);
    Context localContext = App.getAppContext();
    ArrayList localArrayList = GlobalLogic.getInstance().getN3A2MetaGroups();
    Intent localIntent = new Intent();
    Iterator localIterator1 = localArrayList.iterator();
    break label141;
    label59: MetadataGoup localMetadataGoup;
    do
    {
      if (!localIterator1.hasNext())
        break;
      localMetadataGoup = (MetadataGoup)localIterator1.next();
    }
    while (!"menu".equals(localMetadataGoup.type));
    Logger.i("CommonMethod", "toCategory  category type:" + localMetadataGoup.type + "  category:" + paramString1);
    Iterator localIterator2 = localMetadataGoup.meta_index_list.iterator();
    label141: label169: MetadataInfo localMetadataInfo;
    do
    {
      break label169;
      if (!localIterator2.hasNext())
        break label59;
      Iterator localIterator3 = ((MetadataGroupPageIndex)localIterator2.next()).meta_item_list.iterator();
      if (!localIterator3.hasNext())
        break;
      localMetadataInfo = (MetadataInfo)localIterator3.next();
      Logger.i("CommonMethod", "toCategoryActivity category:" + paramString1 + "info.name:" + localMetadataInfo.name);
    }
    while (!paramString2.equals(localMetadataInfo.packet_id));
    if ("movie".equalsIgnoreCase(localMetadataInfo.action_type))
    {
      localIntent.putExtra("Type", 0);
      localIntent.setClass(localContext, ActivityAdapter.getInstance().getVideoListActivity());
    }
    while (true)
    {
      if (!TextUtils.isEmpty(paramString1))
        localIntent.putExtra("category_id", paramString1);
      localIntent.putExtra("MetaDataInfo", localMetadataInfo);
      localIntent.addFlags(8388608);
      return localIntent;
      if ("sitcom".equalsIgnoreCase(localMetadataInfo.action_type))
      {
        localIntent.putExtra("Type", 1);
        localIntent.setClass(localContext, ActivityAdapter.getInstance().getVideoListActivity());
      }
      else if ("web".equalsIgnoreCase(localMetadataInfo.action_type))
      {
        localIntent.setClass(localContext, ActivityAdapter.getInstance().getWebActivity());
        Logger.i("CommonMethod", "startNext Web id:" + paramString2);
      }
      else if ("player".equals(localMetadataInfo.action_type))
      {
        Logger.i("CommonMethod", "toCategory 进入回看");
        localIntent.setClass(localContext, ActivityAdapter.getNewReplayActivity());
      }
      else
      {
        if (!"timeshift".equals(localMetadataInfo.action_type))
          break;
        PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
        localPlayerIntentParams.nns_index = 0;
        localPlayerIntentParams.nns_videoInfo = new VideoInfo();
        localPlayerIntentParams.nns_videoInfo.videoType = 1;
        localPlayerIntentParams.nns_videoInfo.packageId = localMetadataInfo.packet_id;
        localPlayerIntentParams.mode = 6;
        localIntent.setClass(localContext, ActivityAdapter.getInstance().getMPlayer());
        localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
      }
    }
    return null;
    return null;
  }

  public static MetadataInfo getMetaDataInfoByName(String paramString, Intent paramIntent)
  {
    Iterator localIterator1 = GlobalLogic.getInstance().getN3A2MetaGroups().iterator();
    break label217;
    label10: MetadataGoup localMetadataGoup;
    label78: 
    do
    {
      if (!localIterator1.hasNext())
        break label282;
      localMetadataGoup = (MetadataGoup)localIterator1.next();
      if ("menu".equals(localMetadataGoup.type))
      {
        Iterator localIterator4 = localMetadataGoup.meta_index_list.iterator();
        MetadataInfo localMetadataInfo2;
        do
        {
          break label78;
          if (!localIterator4.hasNext())
            break label10;
          Iterator localIterator5 = ((MetadataGroupPageIndex)localIterator4.next()).meta_item_list.iterator();
          if (!localIterator5.hasNext())
            break;
          localMetadataInfo2 = (MetadataInfo)localIterator5.next();
        }
        while (!paramString.equals(localMetadataInfo2.name));
        if ("movie".equalsIgnoreCase(localMetadataInfo2.action_type))
          paramIntent.putExtra("Type", 0);
        while (true)
        {
          return localMetadataInfo2;
          if ("sitcom".equalsIgnoreCase(localMetadataInfo2.action_type))
            paramIntent.putExtra("Type", 1);
        }
      }
    }
    while (!"tclcategory".equals(localMetadataGoup.type));
    Logger.i("CommonMethod", "toCategory  tclcategory type:" + localMetadataGoup.type + "  category:" + paramString);
    Iterator localIterator2 = localMetadataGoup.meta_index_list.iterator();
    label217: label245: MetadataInfo localMetadataInfo1;
    do
    {
      break label245;
      if (!localIterator2.hasNext())
        break label10;
      Iterator localIterator3 = ((MetadataGroupPageIndex)localIterator2.next()).meta_item_list.iterator();
      if (!localIterator3.hasNext())
        break;
      localMetadataInfo1 = (MetadataInfo)localIterator3.next();
    }
    while (!paramString.equals(localMetadataInfo1.name));
    return localMetadataInfo1;
    label282: return null;
  }

  public static String getTimeString(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString2))
      new IllegalArgumentException("pattern is null");
    Logger.e("CommonMethod", "timeStr -->" + paramString1);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString2);
    try
    {
      Date localDate = localSimpleDateFormat.parse(paramString1);
      Logger.i("CommonMethod", "getTimeString:" + localDate.toLocaleString());
      return paramString1;
    }
    catch (Exception localException)
    {
      Logger.i("CommonMethod", "getTimeString: null");
    }
    return null;
  }

  public static void playVideo(int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2, int paramInt4, String paramString3, String paramString4, int paramInt5)
  {
    playVideo(paramInt1, paramString1, paramInt2, paramInt3, paramString2, paramInt4, paramString3, paramString4, paramInt5, false);
  }

  public static void playVideo(int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2, int paramInt4, String paramString3, String paramString4, int paramInt5, boolean paramBoolean)
  {
    playVideo(paramInt1, paramString1, paramInt2, paramInt3, paramString2, paramInt4, paramString3, paramString4, paramInt5, paramBoolean, false, false);
  }

  public static void playVideo(int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2, int paramInt4, String paramString3, String paramString4, int paramInt5, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    Context localContext = App.getAppContext();
    Intent localIntent = new Intent();
    localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.play_video");
    localIntent.putExtra("cmd_is_from_out", "cmd_is_from_out");
    localIntent.addFlags(268435456);
    localIntent.putExtra("isFromWeiXin", paramBoolean3);
    Logger.i("CommonMethod", "isFromWeiXin=" + paramBoolean3);
    if ((GeneralUtils.isVersion4()) && (!DeviceInfo.isHMD()) && (!paramBoolean2))
      localIntent.addFlags(32768);
    if (paramInt3 == 0)
    {
      Logger.i("CommonMethod", "playVideo  playedTime:" + paramInt2 + "  videoType:" + paramInt3);
      localIntent.putExtra("playedTime", paramInt2);
      MovieData localMovieData = new MovieData();
      localMovieData.videoId = paramString1;
      localMovieData.videoType = paramInt3;
      try
      {
        int j = Integer.parseInt(paramString2);
        i = j;
        localMovieData.videoIndex = i;
        localMovieData.name = paramString3;
        localIntent.putExtra("movieData", localMovieData);
        localIntent.putExtra("video_index_name", paramString3);
        localIntent.putExtra("video_begin_time", paramString4);
        localIntent.putExtra("duration", paramInt5);
        localIntent.putExtra("huanApp", paramBoolean1);
        Logger.i("CommonMethod", "playVideo moive");
        localIntent.putExtra("xulPageId", "DetailPage");
        localIntent.putExtra("xulData", "");
        localIntent.setClass(localContext, DetailPageActivity.class);
        if (paramInt1 == 1)
        {
          Logger.i("CommonMethod", "playVideo replay");
          PlayerIntentParams localPlayerIntentParams2 = new PlayerIntentParams();
          localPlayerIntentParams2.nns_index = Integer.parseInt(paramString2);
          localPlayerIntentParams2.nns_timeLen = (paramInt5 + "");
          localPlayerIntentParams2.played_time = paramInt2;
          localPlayerIntentParams2.nns_videoInfo = new VideoInfo();
          localPlayerIntentParams2.nns_videoInfo.videoId = paramString1;
          localPlayerIntentParams2.nns_videoInfo.videoType = 0;
          localPlayerIntentParams2.nns_videoInfo.name = paramString3;
          localPlayerIntentParams2.nns_videoInfo.film_name = paramString3;
          localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams2);
          localIntent.setClass(localContext, ActivityAdapter.getInstance().getMPlayer());
          localIntent.addFlags(8388608);
          localContext.startActivity(localIntent);
          return;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        while (true)
          int i = -1;
        localIntent.addFlags(8388608);
        localContext.startActivity(localIntent);
        return;
      }
    }
    Logger.i("CommonMethod", "playVideo replay");
    PlayerIntentParams localPlayerIntentParams1 = new PlayerIntentParams();
    String str = getTimeString(paramString4, "yyyyMMddhhmmss");
    if (str != null)
    {
      Logger.i("CommonMethod", "begin_time:" + str);
      localPlayerIntentParams1.nns_day = str.substring(0, 8);
      localPlayerIntentParams1.nns_beginTime = str.substring(8, 14);
      localPlayerIntentParams1.nns_index = 0;
      localPlayerIntentParams1.nns_timeLen = (paramInt5 + "");
      localPlayerIntentParams1.nns_videoInfo = new VideoInfo();
      localPlayerIntentParams1.nns_videoInfo.videoId = paramString1;
      localPlayerIntentParams1.nns_videoInfo.videoType = paramInt3;
      localPlayerIntentParams1.nns_videoInfo.name = paramString3;
      localPlayerIntentParams1.nns_videoInfo.film_name = paramString3;
      if ((str != null) || (paramInt5 > 0))
        break label722;
      localPlayerIntentParams1.mode = 4;
    }
    while (true)
    {
      localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams1);
      localIntent.setClass(localContext, ActivityAdapter.getInstance().getMPlayer());
      localIntent.addFlags(8388608);
      localContext.startActivity(localIntent);
      return;
      Logger.i("CommonMethod", "begin_time is null");
      break;
      label722: if (paramInt5 <= 0)
      {
        localPlayerIntentParams1.mode = 6;
      }
      else
      {
        localPlayerIntentParams1.mode = 3;
        Logger.i("CommonMethod", "PlayerIntentParams.MODE_VOD nns_beginTime:" + paramString4);
      }
    }
  }

  public static void playVideo(String paramString1, int paramInt1, int paramInt2, String paramString2, int paramInt3, String paramString3, String paramString4, int paramInt4, boolean paramBoolean)
  {
    Context localContext = App.getAppContext();
    Intent localIntent = new Intent();
    localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.play_video");
    localIntent.putExtra("cmd_is_from_out", "cmd_is_from_out");
    localIntent.addFlags(268435456);
    localIntent.putExtra("isFromWeiXin", paramBoolean);
    Logger.i("CommonMethod", "isFromWeiXin=" + paramBoolean);
    if ((GeneralUtils.isVersion4()) && (!DeviceInfo.isHMD()))
      localIntent.addFlags(32768);
    if (paramInt2 == 0)
    {
      Logger.i("CommonMethod", "playVideo  playedTime:" + paramInt1 + "  videoType:" + paramInt2);
      localIntent.putExtra("playedTime", paramInt1);
      localIntent.putExtra("video_index_name", paramString3);
      localIntent.putExtra("video_begin_time", "video_begin_time");
      localIntent.putExtra("duration", "duration");
      Logger.i("CommonMethod", "playVideo moive");
      localIntent.putExtra("xulPageId", "DetailPage");
      localIntent.putExtra("xulData", "");
      MovieData localMovieData = new MovieData();
      localMovieData.videoId = paramString1;
      localMovieData.videoType = paramInt2;
      localMovieData.name = paramString3;
      try
      {
        int j = Integer.parseInt(paramString2);
        i = j;
        localMovieData.videoIndex = i;
        localIntent.putExtra("movieData", localMovieData);
        localIntent.setClass(localContext, DetailPageActivity.class);
        localIntent.addFlags(8388608);
        localContext.startActivity(localIntent);
        return;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        while (true)
          int i = -1;
      }
    }
    Logger.i("CommonMethod", "playVideo replay");
    PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
    String str = getTimeString(paramString4, "yyyyMMddhhmmss");
    if (str != null)
    {
      Logger.i("CommonMethod", "begin_time:" + str);
      localPlayerIntentParams.nns_day = str.substring(0, 8);
      localPlayerIntentParams.nns_beginTime = str.substring(8, 14);
      localPlayerIntentParams.nns_index = 0;
      localPlayerIntentParams.nns_timeLen = (paramInt4 + "");
      localPlayerIntentParams.nns_videoInfo = new VideoInfo();
      localPlayerIntentParams.nns_videoInfo.videoId = paramString1;
      localPlayerIntentParams.nns_videoInfo.videoType = paramInt2;
      localPlayerIntentParams.nns_videoInfo.name = paramString3;
      localPlayerIntentParams.nns_videoInfo.film_name = paramString3;
      if ((str != null) || (paramInt4 > 0))
        break label548;
      localPlayerIntentParams.mode = 4;
    }
    while (true)
    {
      localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
      localIntent.setClass(localContext, ActivityAdapter.getInstance().getMPlayer());
      localIntent.addFlags(8388608);
      localContext.startActivity(localIntent);
      return;
      Logger.i("CommonMethod", "begin_time is null");
      break;
      label548: if (paramInt4 <= 0)
      {
        localPlayerIntentParams.mode = 5;
      }
      else
      {
        localPlayerIntentParams.mode = 2;
        Logger.i("CommonMethod", "PlayerIntentParams.MODE_VOD nns_beginTime:" + paramString4);
      }
    }
  }

  public static void showVideoDetail(String paramString1, String paramString2, int paramInt, String paramString3)
  {
    Context localContext = App.getAppContext();
    Intent localIntent = new Intent();
    localIntent.putExtra("videoId", paramString1);
    if (paramString3 != null)
      localIntent.putExtra("identifierType", paramString3);
    Logger.d("CommonMethod", "showVideoDetail video_id-->" + paramString1 + ",identifierType=" + paramString3);
    localIntent.putExtra("videoType", XulUtils.tryParseInt(paramString2));
    localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.show_video_info");
    localIntent.addFlags(268435456);
    localIntent.putExtra("cmd_is_from_out", "cmd_is_from_out");
    if (AppFuncCfg.FUNCTION_ENABLE_FLOATING_DETAIL_PAGE)
    {
      localIntent.putExtra("floatingDialog", true);
      localIntent.setClass(localContext, SplashActivity.class);
      Logger.d("CommonMethod", "get floatingDialog" + localIntent.getBooleanExtra("floatingDialog", false));
    }
    while (true)
    {
      if ((GeneralUtils.isVersion4()) && (!DeviceInfo.isHMD()))
        localIntent.addFlags(32768);
      localIntent.addFlags(8388608);
      localContext.startActivity(localIntent);
      return;
      MovieData localMovieData = new MovieData();
      localMovieData.videoId = paramString1;
      localMovieData.videoType = XulUtils.tryParseInt(paramString2);
      localIntent.putExtra("movieData", localMovieData);
      if (!GlobalLogic.getInstance().isAppInterfaceReady())
      {
        localIntent.setClass(localContext, SplashActivity.class);
        localIntent.putExtra("cmd_ex", "show_video_detail");
        localIntent.putExtra("video_id", paramString1);
        localIntent.putExtra("video_type", XulUtils.tryParseInt(paramString2));
        localIntent.putExtra("isbroadcast", true);
        localIntent.putExtra("floatingDialog", false);
      }
      else
      {
        localIntent.setClass(localContext, DetailPageActivity.class);
        localIntent.putExtra("xulPageId", "DetailPage");
        localIntent.putExtra("xulData", "");
      }
    }
  }

  public static boolean startActivityByClass(Class<? extends Activity> paramClass)
  {
    Intent localIntent = new Intent();
    localIntent.setClass(App.getAppContext(), paramClass);
    localIntent.addFlags(8388608);
    try
    {
      App.getAppContext().startActivity(localIntent);
      return true;
    }
    catch (Exception localException)
    {
      localException = localException;
      localException.printStackTrace();
      return false;
    }
    finally
    {
    }
    return false;
  }

  public static boolean startCategoryActivity(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    Context localContext = App.getAppContext();
    Intent localIntent = new Intent();
    localIntent.addFlags(268435456);
    localIntent.putExtra("cmd_is_from_out", "cmd_is_from_out");
    localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.common.show_category");
    localIntent.putExtra("category_id", paramString1);
    localIntent.putExtra("packet_id", paramString2);
    localIntent.putExtra("video_id", paramString3);
    localIntent.putExtra("date", paramString4);
    localIntent.putExtra("begin_time", paramString5);
    localIntent.setClass(localContext, SplashActivity.class);
    localIntent.addFlags(8388608);
    Logger.i("CommonMethod", "processCategory:" + CommonRecevier.buildIntentExtraToString(localIntent));
    try
    {
      localContext.startActivity(localIntent);
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public static void startCategoryActivityById(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    Context localContext = App.getAppContext();
    Intent localIntent = createCategoryIntent(paramString1, paramString2);
    if (localIntent != null)
    {
      localIntent.putExtra("video_id", paramString3);
      localIntent.putExtra("date", paramString4);
      localIntent.putExtra("begin_time", paramString5);
      localIntent.putExtra("cmd_is_from_out", "cmd_is_from_out");
      localIntent.addFlags(335544320);
      Logger.i("CommonMethod", "startCategoryActivityById:" + CommonRecevier.buildIntentExtraToString(localIntent));
      try
      {
        localContext.startActivity(localIntent);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
    }
    Logger.e("CommonMethod", "没有找到您想进入的接口，即将进入主界面");
  }

  public static boolean startCategoryActivityByName(String paramString)
  {
    Context localContext = App.getAppContext();
    Intent localIntent = new Intent();
    if (GlobalLogic.getInstance().isAppInterfaceReady())
    {
      Logger.i("CommonMethod", "processCategory---+ isAppInterfaceReady category:" + paramString);
      return toCategoryActivity(paramString);
    }
    Logger.i("CommonMethod", "processCategory---+  category:" + paramString);
    localIntent.addFlags(268435456);
    localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.show_category");
    localIntent.putExtra("category", paramString);
    localIntent.setClass(localContext, SplashActivity.class);
    localIntent.addFlags(8388608);
    localContext.startActivity(localIntent);
    return true;
  }

  public static void startMainActivity()
  {
    Logger.i("CommonMethod", "toMainActivity");
    Intent localIntent = new Intent();
    localIntent.addFlags(268435456);
    if (GlobalLogic.getInstance().isAppInterfaceReady())
    {
      localIntent.putExtra("MetaDataInfo", GlobalLogic.getInstance().getN3A2MetaGroups());
      localIntent.setClass(App.getInstance().getApplicationContext(), ActivityAdapter.getInstance().getMainActivity());
    }
    while (true)
    {
      localIntent.addFlags(8388608);
      try
      {
        App.getInstance().getApplicationContext().startActivity(localIntent);
        return;
        localIntent.setClass(App.getInstance().getApplicationContext(), SplashActivity.class);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public static void startTimeShiftActivity(String paramString)
  {
    Context localContext = App.getAppContext();
    PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
    localPlayerIntentParams.nns_index = 0;
    localPlayerIntentParams.nns_videoInfo = new VideoInfo();
    localPlayerIntentParams.nns_videoInfo.videoType = 1;
    localPlayerIntentParams.nns_videoInfo.packageId = paramString;
    localPlayerIntentParams.mode = 6;
    Logger.d("CommonMethod", "playerInfo-->" + localPlayerIntentParams.toString());
    Intent localIntent = new Intent(localContext, SplashActivity.class);
    localIntent.putExtra("cmd_is_from_out", "cmd_is_from_out");
    localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.show_timeshift_list");
    localIntent.addFlags(268435456);
    localIntent.addFlags(8388608);
    try
    {
      localContext.startActivity(localIntent);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private static boolean toCategoryActivity(String paramString)
  {
    Context localContext = App.getAppContext();
    ArrayList localArrayList = GlobalLogic.getInstance().getN3A2MetaGroups();
    Intent localIntent = new Intent();
    Iterator localIterator1 = localArrayList.iterator();
    break label107;
    label25: MetadataGoup localMetadataGoup;
    do
    {
      if (!localIterator1.hasNext())
        break;
      localMetadataGoup = (MetadataGoup)localIterator1.next();
    }
    while (!"menu".equals(localMetadataGoup.type));
    Logger.i("CommonMethod", "toCategory  category type:" + localMetadataGoup.type + "  category:" + paramString);
    Iterator localIterator2 = localMetadataGoup.meta_index_list.iterator();
    label107: MetadataInfo localMetadataInfo;
    label135: 
    do
    {
      break label135;
      if (!localIterator2.hasNext())
        break label25;
      Iterator localIterator3 = ((MetadataGroupPageIndex)localIterator2.next()).meta_item_list.iterator();
      if (!localIterator3.hasNext())
        break;
      localMetadataInfo = (MetadataInfo)localIterator3.next();
      Logger.i("CommonMethod", "toCategoryActivity category:" + paramString + "info.name:" + localMetadataInfo.name);
    }
    while (!paramString.equals(localMetadataInfo.name));
    if ("movie".equalsIgnoreCase(localMetadataInfo.action_type))
    {
      localIntent.putExtra("Type", 0);
      localIntent.setClass(localContext, ActivityAdapter.getInstance().getVideoListActivity());
    }
    while (true)
    {
      localIntent.putExtra("MetaDataInfo", localMetadataInfo);
      localIntent.addFlags(335544320);
      localIntent.addFlags(8388608);
      localContext.startActivity(localIntent);
      Logger.i("CommonMethod", "toCategoryActivity category:" + paramString + "info.name:" + localMetadataInfo.name);
      return true;
      if ("sitcom".equalsIgnoreCase(localMetadataInfo.action_type))
      {
        localIntent.putExtra("Type", 1);
        localIntent.setClass(localContext, ActivityAdapter.getInstance().getVideoListActivity());
      }
      else if ("web".equalsIgnoreCase(localMetadataInfo.action_type))
      {
        localIntent.setClass(localContext, ActivityAdapter.getInstance().getWebActivity());
      }
      else if ("player".equals(localMetadataInfo.action_type))
      {
        Logger.i("CommonMethod", "toCategory 进入回看");
        localIntent.setClass(localContext, ActivityAdapter.getNewReplayActivity());
      }
      else if ("timeshift".equals(localMetadataInfo.action_type))
      {
        PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
        localPlayerIntentParams.nns_index = 0;
        localPlayerIntentParams.nns_videoInfo = new VideoInfo();
        localPlayerIntentParams.nns_videoInfo.videoType = 1;
        localPlayerIntentParams.nns_videoInfo.packageId = localMetadataInfo.packet_id;
        localPlayerIntentParams.mode = 6;
        localIntent.setClass(localContext, ActivityAdapter.getInstance().getMPlayer());
        localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
      }
    }
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.uilogic.CommonMethod
 * JD-Core Version:    0.6.2
 */