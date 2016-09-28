package com.sohu.logger.util;

import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.sohu.logger.common.AppConstants;
import com.sohu.logger.log.OutputLog;
import com.sohu.logger.model.UserDataLogger;
import java.io.File;

public class LoggerUtil
{
  public static final String CHANGHONG = "18340732";
  public static final String CHAR_WHITE_SPACE = "";
  public static final String FOXPAD_APKTYPE = "apk_type";
  public static final String FOXPAD_CHANNEL_ID = "channel_id";
  public static final String FOXPAD_DISK_SPACE_TOTAL = "disk_space_total";
  public static final String FOXPAD_DISK_SPACE_USE = "disk_space_use";
  public static final String FOXPAD_ISONLINE = "isonline";
  public static final String FOXPAD_PLATFORM = "platform";
  public static final String FOXPAD_PROCOMPANY = "pro_company";
  public static final String FOXPAD_PROFORM = "pro_form";
  public static final String FOXPAD_PROTYPE = "pro_type";
  public static final String FOXPAD_SD_SPACE_TOTAL = "sd_space_total";
  public static final String FOXPAD_SD_SPACE_USE = "sd_space_use";
  public static final String FOXPAD_SOURCEID = "sourceid";
  public static final String FOXPAD_SOURCE_PATH = "source_path";
  public static final String OPERATING_SYSTEM_ANDROID = "2";
  public static final String PARAM_ACTION_ID = "url";
  public static final String PARAM_ACTION_PROP = "type";
  public static final String PARAM_ACTION_VALUE = "value";
  public static final String PARAM_ALBUM_ID = "playlistid";
  public static final String PARAM_AREA = "area";
  public static final String PARAM_CATEGORY_ID = "cateid";
  public static final String PARAM_CHANNEL_ID = "channeled";
  public static final String PARAM_CLIENT_VERSION = "cv";
  public static final String PARAM_CRT_AB = "ab";
  public static final String PARAM_CRT_ALG = "alg";
  public static final String PARAM_CRT_CID = "cid";
  public static final String PARAM_CRT_FORMWORK = "formwork";
  public static final String PARAM_CRT_INDEX = "index";
  public static final String PARAM_CRT_PID = "pid";
  public static final String PARAM_CRT_REC = "rec";
  public static final String PARAM_CRT_SID = "sid";
  public static final String PARAM_CRT_TYPE = "type";
  public static final String PARAM_CRT_UUID = "uuid";
  public static final String PARAM_CRT_V = "v";
  public static final String PARAM_CRT_VID = "vid";
  public static final String PARAM_DEVICE_ID = "uid";
  public static final String PARAM_ENTER_ID = "enterid";
  public static final String PARAM_EXPAND_1 = "expand1";
  public static final String PARAM_EXPAND_2 = "expand2";
  public static final String PARAM_EXPAND_3 = "expand3";
  public static final String PARAM_EXTRA_INFO = "memo";
  public static final String PARAM_FIRST_TYPE = "type";
  public static final String PARAM_GLOBLE_CATEGORY_CODE = "catecode";
  public static final String PARAM_HAS_SIM = "sim";
  public static final String PARAM_INFO_ACTION = "action";
  public static final String PARAM_INFO_CATECODE = "catecode";
  public static final String PARAM_INFO_CATEGORY_ID = "categoryId";
  public static final String PARAM_INFO_DURATION = "duration";
  public static final String PARAM_INFO_KTVID = "ktvld";
  public static final String PARAM_INFO_O = "o";
  public static final String PARAM_INFO_ONLINE = "online";
  public static final String PARAM_INFO_OS = "os";
  public static final String PARAM_INFO_PLAT = "plat";
  public static final String PARAM_INFO_PLAYLIST_ID = "playlistId";
  public static final String PARAM_INFO_POSITION = "position";
  public static final String PARAM_INFO_R = "r";
  public static final String PARAM_INFO_T = "t";
  public static final String PARAM_INFO_TYPE = "type";
  public static final String PARAM_INFO_UID = "uid";
  public static final String PARAM_INFO_VIDEO_ID = "videoId";
  public static final String PARAM_IS_EDIT = "isedit";
  public static final String PARAM_IS_FEE = "isfee";
  public static final String PARAM_IS_NEW_USER = "newuser";
  public static final String PARAM_IS_SOHU = "issohu";
  public static final String PARAM_LANGUAGE = "language";
  public static final String PARAM_LC_ID = "lcid";
  public static final String PARAM_LIVE_PLAY_TYPE = "ltype";
  public static final String PARAM_LOCATION = "loc";
  public static final String PARAM_LS_ID = "lsid";
  public static final String PARAM_LTYPE = "ltype";
  public static final String PARAM_MANUFACTURER = "mfo";
  public static final String PARAM_MODEL = "mfov";
  public static final String PARAM_MSG = "msg";
  public static final String PARAM_NETWORK_TYPE = "webtype";
  public static final String PARAM_OPERATING_SYSTEM = "mos";
  public static final String PARAM_OPERATING_SYSTEM_VERSION = "mosv";
  public static final String PARAM_OTT_CATE_ID = "ottcateid";
  public static final String PARAM_PARENT_ACTION_ID = "preid";
  public static final String PARAM_PARTNER_NO = "channelid";
  public static final String PARAM_PASSPORT = "passport";
  public static final String PARAM_PLATFORM = "mtype";
  public static final String PARAM_PLAYER_TYPE = "playmode";
  public static final String PARAM_PLAY_ID = "playid";
  public static final String PARAM_PLAY_TIME = "playtime";
  public static final String PARAM_PQ_ALBUM_ID = "sid";
  public static final String PARAM_PQ_BUFFER_NUM = "buffernm";
  public static final String PARAM_PQ_CATECODE = "catecode";
  public static final String PARAM_PQ_CATON_TIME = "ct";
  public static final String PARAM_PQ_CDNFILE = "cdnFile";
  public static final String PARAM_PQ_CDNID = "cdnid";
  public static final String PARAM_PQ_CDNIP = "cdnip";
  public static final String PARAM_PQ_CLIENTIP = "clientip";
  public static final String PARAM_PQ_CLIENT_VERSION = "sver";
  public static final String PARAM_PQ_CODE = "code";
  public static final String PARAM_PQ_CTTIME = "cttime";
  public static final String PARAM_PQ_DUFILE = "duFile";
  public static final String PARAM_PQ_DURATION = "duration";
  public static final String PARAM_PQ_ERROR = "error";
  public static final String PARAM_PQ_HTTPCODE = "httpcode";
  public static final String PARAM_PQ_ISP2P = "isp2p";
  public static final String PARAM_PQ_MODEL = "pn";
  public static final String PARAM_PQ_NETWORK_TYPE = "net";
  public static final String PARAM_PQ_OPERATING_SYSTEM = "os";
  public static final String PARAM_PQ_OPERATING_SYSTEM_VERSION = "sysver";
  public static final String PARAM_PQ_OTHER = "other";
  public static final String PARAM_PQ_PLATFORM = "plat";
  public static final String PARAM_PQ_PRODUCT_ID = "poid";
  public static final String PARAM_PQ_TIP = "tip";
  public static final String PARAM_PQ_VTYPE = "vtype";
  public static final String PARAM_PRODUCTION_COMPANY = "company";
  public static final String PARAM_PRODUCT_ID = "pro";
  public static final String PARAM_RESERVE_1 = "reserve1";
  public static final String PARAM_RESERVE_2 = "reserve2";
  public static final String PARAM_SCREEN_TYPE = "screen";
  public static final String PARAM_SECOND_TYPE = "stype";
  public static final String PARAM_START_ID = "startid";
  public static final String PARAM_START_TIME = "time";
  public static final String PARAM_SUBCATE_ID = "subcateid";
  public static final String PARAM_TS = "ts";
  public static final String PARAM_TV_ID = "tvid";
  public static final String PARAM_USER_ID = "user_id";
  public static final String PARAM_VIDEO_DEFINITION = "version";
  public static final String PARAM_VIDEO_DURATION = "td";
  public static final String PARAM_VIDEO_ID = "vid";
  public static final String PARAM_VIDEO_TYPE = "type";
  public static final String PARAM_WATCH_TYPE = "wtype";
  public static final String PENGBOSHI = "866";
  public static String PLAY_QUALITY_PREFIX;
  public static String PLAY_QUALITY_URL;
  public static final String RECOMMENDED_URL = "http://ctr.hd.sohu.com/ctr.gif?";
  public static final String TCL = "18340450";
  public static final String TEST_PLAY_QUALITY_URL = "http://stat.m.tv.sohu.com/mobiledata_test/caton/video/?";
  public static String TEST_USER_ACTION_URL = "http://dev.stat.ott.hd.sohu.com/mcc/mc.gif?";
  public static String TEST_USER_CLICK_BEHAVIOR_URL;
  public static String TEST_USER_PLAY_INFO = "http://dev.stat.ott.hd.sohu.com/count/count.gif?";
  public static String TEST_VIDEO_PLAY_URL;
  public static String USER_ACTION_URL;
  public static String USER_CLICK_BEHAVIOR_URL;
  public static String USER_PLAY_INFO;
  public static String USER_PLAY_PREFIX;
  public static String VIDEO_PLAY_PREFIX;
  public static String VIDEO_PLAY_URL;
  public static final String XIAOMI_CNTV = "800";
  public static final String YINHE = "863";
  public static boolean isTestEnv = false;

  static
  {
    VIDEO_PLAY_URL = "http://pv.ott.hd.sohu.com/mvv/mvv.gif?";
    USER_ACTION_URL = "http://pv.ott.hd.sohu.com/mcc/mc.gif?";
    PLAY_QUALITY_URL = "http://qc.hd.sohu.com.cn/caton/video/?";
    TEST_VIDEO_PLAY_URL = "http://dev.stat.ott.hd.sohu.com/mvv/mvv.gif?";
    TEST_USER_ACTION_URL = "http://dev.stat.ott.hd.sohu.com/mcc/mc.gif?";
    USER_PLAY_INFO = "http://count.ott.hd.sohu.com/count/count.gif?";
    TEST_USER_PLAY_INFO = "http://dev.stat.ott.hd.sohu.com/count/count.gif?";
    USER_CLICK_BEHAVIOR_URL = "http://pv.ott.hd.sohu.com/mcc/action.gif?";
    TEST_USER_CLICK_BEHAVIOR_URL = "http://dev.stat.ott.hd.sohu.com/mcc/action.gif?";
    VIDEO_PLAY_PREFIX = "";
    PLAY_QUALITY_PREFIX = "";
    USER_PLAY_PREFIX = "";
    String str = AppConstants.getInstance().getPartnerNo();
    if ((str.equals("863")) || (str.equals("866")) || (str.equals("18340450")) || (str.equals("18340732")))
    {
      VIDEO_PLAY_PREFIX = "http://pv.ott.hd.ptsh.gitv.tv";
      PLAY_QUALITY_PREFIX = "http://qc.hd.ptsh.gitv.tv";
      USER_PLAY_PREFIX = "http://count.ott.hd.ptsh.gitv.tv";
    }
    while (true)
    {
      if (TextUtils.isEmpty(VIDEO_PLAY_PREFIX))
        VIDEO_PLAY_PREFIX = "http://pv.ott.hd.sohu.com";
      if (TextUtils.isEmpty(PLAY_QUALITY_PREFIX))
        PLAY_QUALITY_PREFIX = "http://qc.hd.sohu.com.cn";
      if (TextUtils.isEmpty(USER_PLAY_PREFIX))
        USER_PLAY_PREFIX = "http://count.ott.hd.sohu.com";
      if (AppConstants.getInstance().getmProjectType() != 1)
        break;
      VIDEO_PLAY_URL = VIDEO_PLAY_PREFIX + "/pad/mvv.gif?";
      USER_ACTION_URL = VIDEO_PLAY_PREFIX + "/pad/launcher/mcc.gif?";
      USER_CLICK_BEHAVIOR_URL = VIDEO_PLAY_PREFIX + "/pad/action.gif?";
      PLAY_QUALITY_URL = PLAY_QUALITY_PREFIX + "/caton/video/?";
      USER_PLAY_INFO = USER_PLAY_PREFIX + "/pad/count.gif?";
      OutputLog.e("Sohuplayer", "VIDEO_PLAY_URL = " + VIDEO_PLAY_URL);
      TEST_USER_CLICK_BEHAVIOR_URL = "http://dev.stat.ott.hd.sohu.com/pad/action.gif?";
      TEST_VIDEO_PLAY_URL = "http://dev.stat.ott.hd.sohu.com/pad/mvv.gif?";
      TEST_USER_ACTION_URL = "http://dev.stat.ott.hd.sohu.com/pad/launcher/mcc.gif?";
      TEST_USER_PLAY_INFO = "http://dev.stat.ott.hd.sohu.com/pad/count.gif?";
      return;
      if (str.equals("800"))
      {
        VIDEO_PLAY_PREFIX = "http://pv.ott.hd.sh.t001.ottcn.com";
        PLAY_QUALITY_PREFIX = "http://qc.hd.sh.t001.ottcn.com";
        USER_PLAY_PREFIX = "http://count.ott.hd.sh.t001.ottcn.com";
      }
      else
      {
        VIDEO_PLAY_PREFIX = "http://pv.ott.hd.sohu.com";
        PLAY_QUALITY_PREFIX = "http://qc.hd.sohu.com.cn";
        USER_PLAY_PREFIX = "http://count.ott.hd.sohu.com";
      }
    }
    VIDEO_PLAY_URL = VIDEO_PLAY_PREFIX + "/mvv/mvv.gif?";
    USER_ACTION_URL = VIDEO_PLAY_PREFIX + "/mcc/mc.gif?";
    USER_CLICK_BEHAVIOR_URL = VIDEO_PLAY_PREFIX + "/mcc/action.gif?";
    PLAY_QUALITY_URL = PLAY_QUALITY_PREFIX + "/caton/video/?";
    USER_PLAY_INFO = USER_PLAY_PREFIX + "/count/count.gif?";
    OutputLog.e("Sohuplayer", "VIDEO_PLAY_URL = " + VIDEO_PLAY_URL);
    TEST_VIDEO_PLAY_URL = "http://dev.stat.ott.hd.sohu.com/mvv/mvv.gif?";
  }

  public static String buildUrlByType(int paramInt)
  {
    if (isTestEnv())
      switch (paramInt)
      {
      default:
      case 1:
      case 0:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      }
    while (true)
    {
      return "";
      return TEST_VIDEO_PLAY_URL;
      return TEST_USER_ACTION_URL;
      return "http://stat.m.tv.sohu.com/mobiledata_test/caton/video/?";
      return TEST_USER_CLICK_BEHAVIOR_URL;
      return TEST_USER_PLAY_INFO;
      return "http://ctr.hd.sohu.com/ctr.gif?";
      switch (paramInt)
      {
      default:
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      }
    }
    return USER_ACTION_URL;
    return VIDEO_PLAY_URL;
    return PLAY_QUALITY_URL;
    return USER_CLICK_BEHAVIOR_URL;
    return USER_PLAY_INFO;
    return "http://ctr.hd.sohu.com/ctr.gif?";
  }

  public static boolean externalMemoryAvailable()
  {
    return Environment.getExternalStorageState().equals("mounted");
  }

  public static long getAvailableExternalMemorySize()
  {
    if (externalMemoryAvailable())
    {
      StatFs localStatFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
      return localStatFs.getBlockSize() * localStatFs.getAvailableBlocks() / 1048576L;
    }
    return -1L;
  }

  public static long getAvailableInternalMemorySize()
  {
    StatFs localStatFs = new StatFs(Environment.getDataDirectory().getPath());
    return localStatFs.getBlockSize() * localStatFs.getAvailableBlocks() / 1048576L;
  }

  public static String getEnterId()
  {
    String str = UserDataLogger.getInstance().getEnterId();
    if (TextUtils.isEmpty(str))
      str = "0";
    return str;
  }

  public static String getFoxPadAPPURL()
  {
    if (isTestEnv())
      return "http://dev.stat.ott.hd.sohu.com/pad/external/mcc.gif";
    return "http://pv.ott.hd.sohu.com/pad/external/mcc.gif";
  }

  public static String getNetworkType()
  {
    String str = NetUtils.getNetworkStringByType(NetUtils.getNetworkType(UserDataLogger.getInstance().getContext()));
    if ("None".equals(str))
      str = "";
    return str;
  }

  public static String getNetworkWebType()
  {
    String str = NetUtils.getNetworkStringByType(NetUtils.getNetworkType(UserDataLogger.getInstance().getContext()));
    if ("None".equals(str))
      str = "Unknown";
    return str;
  }

  public static String getPassport()
  {
    return null;
  }

  public static String getSimState()
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)UserDataLogger.getInstance().getSystemService("phone");
    if (localTelephonyManager.getSimState() == 0)
      return "-1";
    if (localTelephonyManager.getSimState() == 1)
      return "0";
    return "1";
  }

  public static String getSourceId(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      paramString = "1";
    while ((!paramString.equals("-2")) && (!paramString.equals("-1")) && (!paramString.equals("0")))
      return paramString;
    return "1";
  }

  public static String getStartId()
  {
    return UserDataLogger.getInstance().getStartId();
  }

  public static long getTotalExternalMemorySize()
  {
    if (externalMemoryAvailable())
    {
      StatFs localStatFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
      return localStatFs.getBlockSize() * localStatFs.getBlockCount() / 1048576L;
    }
    return -1L;
  }

  public static long getTotalInternalMemorySize()
  {
    StatFs localStatFs = new StatFs(Environment.getDataDirectory().getPath());
    return localStatFs.getBlockSize() * localStatFs.getBlockCount() / 1048576L;
  }

  public static String isNewUser()
  {
    if (UserDataLogger.getInstance().isNewUser())
      return "1";
    return "0";
  }

  public static boolean isTestEnv()
  {
    return isTestEnv;
  }

  public static void setTestEnv(boolean paramBoolean)
  {
    isTestEnv = paramBoolean;
  }

  public static class ActionId
  {
    public static final int APP_MAXIMIZE = 2002;
    public static final int APP_MAXIMIZE_WHEN_PLAY = 2003;
    public static final int APP_MINIMIZE = 2001;
    public static final int APP_RUNTIME_HEART_BEAT = 2004;
    public static final int APP_START = 1002;
    public static final int APP_START_AD = 1004;
    public static final int APP_VOICE_NEXT = 21003;
    public static final int APP_VOICE_START = 21000;
    public static final int APP_VOICE_TRY = 21001;
    public static final int APP_VOICE_WANT = 21002;
  }

  public static class ActionProp
  {
    public static final int ACTIVE = 1;
    public static final int PASSIVE;
  }

  public static class ChannelId
  {
    public static final String CACHED = "10006";
    public static final String COLLECT = "10003";
    public static final String DEFAUT = "";
    public static final String HOMEPAGE = "0";
    public static final String PLAY_HISTORY = "10001";
    public static final String PUSH = "10005";
    public static final String SEARCH = "10000";
    public static final String SUBSCRIBE = "10002";
    public static final String UNDETERMINED = "";
    public static final String UPLOAD = "10004";
    public static final String VOICE_OF_CHINA = "10007";
  }

  public static class EnterId
  {
    public static final String DEFAULT = "0";
    public static final String FLOAT_WINDOW = "3";
    public static final String PUSH = "1";
    public static final String WIDGET = "2";
  }

  public static class FoxPadActionId
  {
    public static final String ABOUT_FOXPAD = "110308";
    public static final String ATTENTION = "110304";
    public static final String BOOT = "23";
    public static final String ENTERTAINMENT_BROADCAST = "1102";
    public static final String EXPOSURE = "100001";
    public static final String FILE_MANAGER = "1104";
    public static final String FILMCHANNEL = "1101";
    public static final String FILMCHANNEL_CLASSIFY = "110101";
    public static final String FOXCHAT = "1003";
    public static final String FOXCHAT_HISTORY = "100301";
    public static final String FOXPLAY = "1001";
    public static final String GUIDE = "22";
    public static final String LOGIN = "25";
    public static final String MALL = "110303";
    public static final String MESSAGE = "33";
    public static final String MESSAGE_CENTER = "110307";
    public static final String MOVIECALENDAR = "1006";
    public static final String MY = "1103";
    public static final String MY_LOGIN = "110301";
    public static final String MY_ORDER = "110305";
    public static final String NAVIGATION = "32";
    public static final String NETWORKSETUP = "24";
    public static final String NEWS = "1005";
    public static final String OFF_LINE = "110306";
    public static final String PLAYFULLSCREEN = "31";
    public static final String PLAYSMALLSCREEN = "30";
    public static final String PLAY_HISTORY = "110302";
    public static final String RANK = "1004";
    public static final String RECOMMEND = "1002";
    public static final String SCREENONE = "10";
    public static final String SCREENTHREE = "12";
    public static final String SCREENTWO = "11";
    public static final String SOHU_HELPER = "110309";
    public static final String UPGRADE_FAIL = "29";
    public static final String UPGRADE_PROGRESS = "27";
    public static final String UPGRADE_SUCCESS = "28";
    public static final String UPGRADE_WINDOW = "26";
  }

  public static class FoxPadVideoOriginId
  {
    public static final String ATTENTION = "10-2";
    public static final String CACHE = "10-3";
    public static final String DEFAULT = "1";
    public static final String ENTERTAINMENT_BROADCAST = "9";
    public static final String FILMCHANNEL = "8";
    public static final String FOXCHAT = "4";
    public static final String FOXPLAYER = "2";
    public static final String INDIVIDUALITY_RECOMMEND = "3";
    public static final String MOVIECALENDAR = "5";
    public static final String MY = "10";
    public static final String NEWS = "7";
    public static final String PLAYHISTORY = "10-1";
    public static final String RANK = "6";
  }

  public static class HasSim
  {
    public static final int NO = 0;
    public static final int UNKNOWN = -1;
    public static final int YES = 1;
  }

  public static class IsEdit
  {
    public static final String NO = "0";
    public static final String YES = "1";
  }

  public static class IsOnline
  {
    public static final String OFF = "off";
    public static final String ON = "on";
  }

  public static class LivePlayType
  {
    public static final int DEFAULT = 0;
    public static final int ON_DEMAND = 0;
    public static final int SINGLE_LIVE = 2;
    public static final int TV_LIVE = 1;
    public static final int VIDEO_SHOW = 3;
  }

  public static class Msg
  {
    public static final String BREAKOFF = "breakoff";
    public static final String CLIENT_CLOSE = "cclose";
    public static final String ERROR = "error";
    public static final String HEART_BEAT = "caltime";
    public static final String PLAY_COUNT = "playCount";
    public static final String VIDEO_CLOSE = "vclose";
    public static final String VIDEO_ENDS = "videoends";
    public static final String VIDEO_START = "videoStart";
  }

  public static class PlayTime
  {
    public static final String DEFAULT = "0";
  }

  public static class PlayerType
  {
    public static final int OTHER = 2;
    public static final int SOHU = 1;
    public static final int SYSTEM;
  }

  public static class ScreenType
  {
    public static final int FULL = 1;
    public static final int SMALL;
  }

  public static class VideoDefinition
  {
    public static final int DEFAULT = 0;
    public static final int FLUENCY = 0;
    public static final int HIGH = 1;
    public static final int ORIGINAL = 31;
    public static final int SUPER = 21;
  }

  public static class VideoOriginId
  {
    public static final String ACTION_OPEN = "21";
    public static final String CHANNEL_PAGE = "4";
    public static final String CHAT_WALL = "18";
    public static final String DEFAULT = "0";
    public static final String DLNA = "8";
    public static final String DLNA_URL = "-2";
    public static final String ENTERTAINMENT_BROADCAST = "16";
    public static final String FOXPLAYER = "17";
    public static final String INDIVIDUALITY_RECOMMEND = "22";
    public static final String INDIVIDUALITY_RECOMMEND_COLLECTION = "22-2";
    public static final String INDIVIDUALITY_RECOMMEND_PLAYHISTORY = "22-3";
    public static final String LIVE = "14";
    public static final String LOCAL_URL = "-1";
    public static final String MESSAGE_CENTER = "19";
    public static final String MY_COLLECT = "2";
    public static final String NEWS = "15";
    public static final String PLAYBACKLIVE = "13";
    public static final String PLAY_HISTORY = "3";
    public static final String RANK = "12";
    public static final String RECOMMAND_PAGE = "1";
    public static final String RELATED_RECOMMEND = "7";
    public static final String REPLAYER_ERROR = "11";
    public static final String REPLAYER_USER = "10";
    public static final String RESEARCH_RESULT_PAGE = "5";
    public static final String SLOT_MACHINE = "20";
    public static final String VIDEO_DETAIL = "23";
    public static final String VIDEO_JAR = "25";
  }

  public static class VideoStreamType
  {
    public static final String TYPE_M3U8 = "m3u8";
    public static final String TYPE_MP4 = "mp4";
  }

  public static class VideoType
  {
    public static final String KTV = "ktv";
    public static final String LONG_VIDEO = "vrs";
    public static final String UGC = "my";
    public static final String VIDEO_NEWS = "vms";
  }

  public static class WatchType
  {
    public static final int CACHED = 2;
    public static final int ONLINE = 1;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.util.LoggerUtil
 * JD-Core Version:    0.6.2
 */