package com.starcor.hunan.uilogic;

import android.content.Context;
import com.starcor.hunan.NewReplayActivityForV4;
import com.starcor.hunan.widget.WebDialogBase;

public abstract class ActivityAdapter
{
  public static final int CATEGORY_TYPE_APP = 4;
  public static final int CATEGORY_TYPE_DJSB = 7;
  public static final int CATEGORY_TYPE_MOIVE = 0;
  public static final int CATEGORY_TYPE_PLAYER = 2;
  public static final int CATEGORY_TYPE_SITCOM = 1;
  public static final int CATEGORY_TYPE_TIMESHIFT = 5;
  public static final int CATEGORY_TYPE_VIEW = 6;
  public static final int CATEGORY_TYPE_WEB = 3;
  public static final int ORIENTATION_HORIZONTAL = 1;
  public static final int ORIENTATION_VERTICAL = 0;
  public static final String STR_APP_CENTER_EXT_TITLE = "Apps";
  public static final String STR_APP_CENTER_TITLE = "应用中心";
  public static final String STR_CITY_YOU_CHOSEN = "您已经选择：";
  public static final String STR_DATE_FORMAT = "MM月dd日";
  public static final String STR_DETAILED_UPDATE_INFO_FORMAT = "更新至%s集";
  public static final String STR_DIALOG_BACK = "返回";
  public static final String STR_DIALOG_LOADING = "加载中...";
  public static final String STR_DIALOG_USERFEEDBACK = "问题反馈";
  public static final String STR_DIALOG_USER_OFFLINE = "用户离线，请退出重新登录!";
  public static final String STR_EMPTY_HOTWORD_PAGE = "亲,请输入关键字试试吧!";
  public static final String STR_EMPTY_PAGE = "对不起,  该选项暂无内容!";
  public static final String STR_EMPTY_SEARCH_PAGE = "对不起, 没有与搜索项符合的结果!";
  public static final String STR_EXCEPTION_APPLICATION_AAA_REUEST_VIDEO_ERROR = "播放未授权，请到用户中心开通VIP服务";
  public static final String STR_EXCEPTION_APPLICATION_BILL_COLLECTION_ERROR = "无法收藏，请稍后再试。";
  public static final String STR_EXCEPTION_APPLICATION_COLLECTION_DELETE_ERROR = "无法删除，请稍后再试。";
  public static final String STR_EXCEPTION_APPLICATION_EPG_CONTENT_LIST_ERROR = "数据请求超时，请检查网络后再试。";
  public static final String STR_EXCEPTION_APPLICATION_EPG_MAIN_ERROR = "数据请求超时，请检查网络后再试。";
  public static final String STR_EXCEPTION_APPLICATION_EPG_SERVER_ERROR = "网络连接超时，请检查网络后再试。";
  public static final String STR_EXCEPTION_APPLICATION_FJYD_TOKEN_ERROR = "对不起！账号登录失败，请重启机顶盒后再试。";
  public static final String STR_EXCEPTION_APPLICATION_FJYD_TOKEN_INVALID = "用户信息过期，正在更新，请稍后再试。";
  public static final String STR_EXCEPTION_APPLICATION_LOGIN_FAIL_ERROR = "网络连接超时，请检查网络后再试。";
  public static final String STR_EXCEPTION_APPLICATION_MAC_AUTH_ERROR = "未通过MAC认证，请稍后再试。";
  public static final String STR_EXCEPTION_APPLICATION_PASSWORD_ERROR = "用户名或密码有误，请重新输入。";
  public static final String STR_EXCEPTION_APPLICATION_PLAY_VIDEO_AUTH = "未通过权限认证，请稍后再试。";
  public static final String STR_EXCEPTION_APPLICATION_PROGRAM_TIME_ERROR = "节目未到播出时间，请到时收看。";
  public static final String STR_EXCEPTION_APPLICATION_PROGRAM_TIME_OUT_ERROR = "非常抱歉，节目已过期，请选择其他节目收看。";
  public static final String STR_EXCEPTION_APPLICATION_RUNTIME_ERROR = "系统正忙，请重启设备后再试。";
  public static final String STR_EXCEPTION_APPLICATION_SEARCH_ERROR = "请求服务器超时，请稍候再试。";
  public static final String STR_EXCEPTION_APPLICATION_UNKNOWN_ERROR = "应用运行异常，请重启设备后再试。";
  public static final String STR_EXCEPTION_APPLICATION_VIDEO_BUFFER_TIMEOUT = "缓冲超时，请检查您的接入带宽。";
  public static final String STR_EXCEPTION_APPLICATION_VIDEO_CONNECT_ERROR = "网络连接超时，请稍候再试。";
  public static final String STR_EXCEPTION_APPLICATION_VIDEO_DESC_DELETED = "非常抱歉，节目已下线，请选择其他节目收看";
  public static final String STR_EXCEPTION_APPLICATION_VIDEO_INFO_ERROR = "数据请求超时，请检查网络后再试。";
  public static final String STR_EXCEPTION_APPLICATION_VIDEO_INVALID_PARAMETER = "未知异常，请稍候再试。";
  public static final String STR_EXCEPTION_APPLICATION_VIDEO_NOT_FOUND_ERROR = "未找到节目，请稍后再试。";
  public static final String STR_EXCEPTION_APPLICATION_VIDEO_PLAYER_ERROR = "未知异常，请稍候再试。";
  public static final String STR_EXCEPTION_APPLICATION_VIDEO_PLUG_ERROR = "未知异常，请重启设备后再试。";
  public static final String STR_EXCEPTION_APPLICATION_VIDEO_STATE_UNSUPPORTED = "未知异常，请稍候再试。";
  public static final String STR_EXCEPTION_APPLICATION_VIDEO_UNEXCEPETD_TERMINATE = "未知异常，请稍候再试。";
  public static final String STR_EXCEPTION_APPLICATION_VIDEO_UNKNOWN_ERROR = "未知异常，请稍候再试。";
  public static final String STR_EXCEPTION_APPLICATION_VIDEO_URL_ERROR = "未找到节目，请稍后再试。";
  public static final String STR_EXCEPTION_APPLICATION_WEATHER_ERROR = "无";
  public static final String STR_EXCEPTION_ERRORCODE_NAME = "状态码：";
  public static final String STR_EXCEPTION_SYSTEM_NETWROK_ERROR = "无法连接到网络，请检查网络后再试。";
  public static final String STR_EXCEPTION_SYSTEM_UNKNOWN_ERROR = "错误";
  public static final String STR_EXCEPTION_SYSTEM_WIRELESS_NETWORK_ERROR = "无法连接到网络，请检查网络后再试。";
  public static final String STR_EXIT_APP_MANGO = "是否要退出芒果TV?";
  public static final String STR_LOGIN_BEFORE_RESERVATION = "系统已记录预约请求，将在您登录后提交！";
  public static final String STR_MEDIA_ASSET_NOT_EXISTS = "很抱歉，该影片资源已被删除或不存在";
  public static final String STR_MEDIA_RECOMMEND_TITLE = "观众热荐";
  public static final String STR_MESSAGEVIEW_MYMSG = "我的消息(%1$s)";
  public static final String STR_MESSAGEVIEW_MYMSG_WITHOUT_NUM = "我的消息";
  public static final String STR_MESSAGEVIEW_SET_ALL_READ = "全部标为已读";
  public static final String STR_MESSAGEVIEW_SET_ALL_UNREAD = "全部标为未读";
  public static final String STR_MESSAGEVIEW_SYSMSG = "系统消息(%1$s)";
  public static final String STR_MESSAGEVIEW_SYSMSG_WITHOUT_NUM = "系统消息";
  public static final String STR_MESSAGEVIEW_TOTAL_MSG = "共%1$s条消息";
  public static final String STR_MPLAYER_AUTO_CONTINUOS_PLAY = "自动为您续播 ： ";
  public static final String STR_MPLAYER_BUY = "购买";
  public static final String STR_MPLAYER_BUY_TIPS = "该频道为VIP频道，如需观看请您购买服务";
  public static final String STR_MPLAYER_CHANNEL_NOT_EXIST = "该频道不存在";
  public static final String STR_MPLAYER_EXIT_NOTICE = "按\"返回键\"取消";
  public static final String STR_MPLAYER_EXIT_PLAY = "按\"返回键\"退出播放";
  public static final String STR_MPLAYER_KEY_MENU = "\"菜单键\"";
  public static final String STR_MPLAYER_MENU_VALUE_AUDIO_TRACK = "音轨";
  public static final String STR_MPLAYER_NOT_SUPPORT_TIMESHIFT = "该频道不支持时移播放";
  public static final String STR_MPLAYER_PLAY_FROM_HEAD = " 按\"返回\"键从头播放";
  public static final String STR_MPLAYER_PLAY_INSTANTE = "即将为您播放：";
  public static final String STR_MPLAYER_PLAY_NEXT_EPI = "即将为您播放下一集";
  public static final String STR_MPLAYER_PLAY_PLAYBACK_OPERATION_TIP = "上键：选择节目单；左右：快退快进";
  public static final String STR_MPLAYER_PLAY_TIMESHIFT_OPERATION_TIP = "OK键：频道列表";
  public static final String STR_MPLAYER_PRESS = "点";
  public static final String STR_MPLAYER_QUALITY_HD = "高清";
  public static final String STR_MPLAYER_QUALITY_SD = "超清";
  public static final String STR_MPLAYER_QUALITY_STD = "标清";
  public static final String STR_MPLAYER_RECOMMEND_QUIT_NOTICE = "按\"返回\"键退出";
  public static final String STR_MPLAYER_RECOMMEND_TITLE = "推荐影片";
  public static final String STR_MPLAYER_SHOW_MENU = "呼出菜单";
  public static final String STR_MPLAYER_SYSTEM_EXCEPTION = "系统出现异常";
  public static final String STR_MYMEDIA_ISONLINE = "该影片已下线，请选择其他影片观看";
  public static final String STR_MYMEDIA_ITEM_DELETE = "删除";
  public static final String STR_MYMEDIA_SET_SUCESS = "设置成功";
  public static final String STR_NETOPT_TEXT = "网络优化";
  public static final String STR_NEWDETAILED_BUY_VIP = "开通VIP";
  public static final String STR_NEWDETAILED_COLLECT = "收藏";
  public static final String STR_NEWDETAILED_COLLECT_FAILED = "收藏失败";
  public static final String STR_NEWDETAILED_COLLECT_SUCCESS = "收藏成功";
  public static final String STR_NEWDETAILED_SUBINDEX_FORMAT = "%s集";
  public static final String STR_NEWDETAILED_SUBINDEX_PERIOD_FORMAT = "%s期";
  public static final String STR_NEWDETAILED_TOTAL_EPISODE_INFO_FORMAT = "共%s集";
  public static final String STR_NEWDETAILED_TRACEPLAY = "追剧";
  public static final String STR_NEWDETAILED_TRACEPLAY_FAILED = "追剧失败";
  public static final String STR_NEWDETAILED_TRACEPLAY_SUCCESS = "追剧成功";
  public static final String STR_NEWDETAILED_UNCOLLECT = "取消收藏";
  public static final String STR_NEWDETAILED_UNCOLLECT_FAILED = "取消收藏失败";
  public static final String STR_NEWDETAILED_UNCOLLECT_SUCCESS = "取消收藏成功";
  public static final String STR_NEWDETAILED_UNTRACEPLAY = "取消追剧";
  public static final String STR_NEWDETAILED_UNTRACEPLAY_FAILED = "取消追剧失败";
  public static final String STR_NEWDETAILED_UNTRACEPLAY_SUCCESS = "取消追剧成功";
  public static final String STR_NEWDETAILED_UPDATE_INFO_FORMAT = "更新至%s集";
  public static final String STR_NEWDETAILED_UPDATE_INFO__PERIOD_FORMAT = "更新至%s期";
  public static final String STR_OPTING_NETWORK = "正在进行网络优化，请稍后...";
  public static final String STR_QRCODE_DESC = "关注芒果互联网电视官方微信和官方微博";
  public static final String STR_QRCODE_UPDATE_DESC = "获取每日更新信息";
  public static final String STR_RECURRENCE_RATE = "重现率设置";
  public static final String STR_REPLAY_CANCEL_RESERVATION = "取消预约成功";
  public static final String STR_REPLAY_EXT_TITLE = "Replay";
  public static final String STR_REPLAY_LIST_EMPTY_TIPS = "暂无节目单，敬请期待！";
  public static final String STR_REPLAY_LIST_VIP_TIPS = "该节目是VIP节目";
  public static final String STR_REPLAY_RECOMMOND = "回看推荐";
  public static final String STR_REPLAY_RESERVATION_SUCCESS = "预约成功";
  public static final String STR_RESERVATION = "预约";
  public static final String STR_RESERVATION_AGAIN = "您已预约成功，请勿重复操作！";
  public static final String STR_RESERVATION_FAIL = "预约失败！请重试！";
  public static final String STR_RESERVATION_REMIND = "您预约的回看节目可以点播了!";
  public static final String STR_RESERVATION_SUCCESS = "预约成功";
  public static final String STR_SCAN_QRCODE = "扫描二维码";
  public static final String STR_SERVICE_EXT_TITLE = "Service";
  public static final String STR_SERVICE_TITLE = "服务";
  public static final String STR_SETTING_EXT_TITLE = "Settings";
  public static final String STR_SETTING_TITLE = "设置";
  public static final String STR_SET_RECURRENCE_RATE = "设置您的电视重现率";
  public static final String STR_START_APP_ERROR = "该应用不存在或已损坏!";
  public static final String STR_THE_PROGRAM_IS_COMING_SOON = "节目还未到播出时间。";
  public static final String STR_THE_PROGRAM_IS_DEPLOYING = "此节目正在部署。";
  public static final String STR_TODAY = "今日";
  public static final String STR_TRACEPLAY_UPDATE = "您追剧的节目有更新:";
  public static final String STR_UPGRADE_ASK_TO_EXCUTE = "检查到系统更新,是否开始更新系统?";
  public static final String STR_UPGRADE_BUTTON_CONFIRM = "确定";
  public static final String STR_UPGRADE_DOING = "检查到系统更新,开始更新系统...";
  public static final String STR_UPGRADE_ERROR_INFO_FAILED = "内部错误, 更新失败!";
  public static final String STR_UPGRADE_ERROR_INFO_MAKE_FILE_FAILED = "内部错误(无法创建临时文件), 更新失败!";
  public static final String STR_UPGRADE_ERROR_PARA_BAD_ADDR = "启动参数错误!更新地址有误!";
  public static final String STR_UPGRADE_ERROR_PARA_MISSING_PARA = "启动参数错误!缺少启动参数!";
  public static final String STR_UPGRADE_ERROR_PARA_NO_ADDR = "启动参数错误!没有更新地址!";
  public static final String STR_UPGRADE_FAILURE = "更新地址为空!!, 更新失败!";
  public static final String STR_UPGRADE_NEW_VER_NOTICE = "已有新版本，是否立即升级?";
  public static final String STR_UPGRADE_SERVICE_TITLE = "软件更新";
  public static final String STR_UPGRADE_STATUS_DOWNLOADING = "文件下载中...";
  public static final String STR_UPGRADE_STATUS_DOWNLOAD_ERROR = "更新失败 - 文件下载错误!";
  public static final String STR_UPGRADE_STATUS_PREPARING = "文件下载完成,准备安装...";
  public static final String STR_UPGRADE_STATUS_RETRY_DOWNLOAD = "文件下载失败!! 正在重试...";
  public static final String STR_UPGRADE_TITLE = "升级";
  public static final String STR_UPGRADE_TITLE_NOTICE = "提示";
  public static final String STR_USER_CENTER_EXT_TITLE = "UserCenter";
  public static final String STR_USER_CENTER_TITLE = "用户中心";
  public static final String STR_VIDEOLIST_TIPS_NETWORK_ERR = "    网络异常,请稍后再试";
  public static final String STR_VIPLIST_HOT = "最热";
  public static final String STR_VIPLIST_LOADING = "加载中...";
  public static final String STR_VIPLIST_LOAD_FAILED = "加载失败，请稍后再试。";
  public static final String STR_VIPLIST_NEW = "最新";
  public static final String STR_VIPLIST_PAGE_NUM = "%s页    ";
  public static final String STR_VIPLIST_TOTAL_RESULT = "共%s个结果";
  public static final String STR_WEEK_FRI = "周五";
  public static final String STR_WEEK_MON = "周一";
  public static final String STR_WEEK_SAT = "周六";
  public static final String STR_WEEK_SUN = "周日";
  public static final String STR_WEEK_THU = "周四";
  public static final String STR_WEEK_TUE = "周二";
  public static final String STR_WEEK_WED = "周三";
  private static ActivityAdapter _adapterInstance;
  public String MPlayer_INTENT_EXITAPP_FLAG;
  public String MPlayer_INTENT_EXIT_APP;
  public String MPlayer_INTENT_FLAG;

  public static ActivityAdapter getInstance()
  {
    if (_adapterInstance == null)
      _adapterInstance = new ActivityAdapterV4();
    return _adapterInstance;
  }

  public static Class<?> getNewReplayActivity()
  {
    return NewReplayActivityForV4.class;
  }

  public abstract void MainActivity_putPosterToCache(String paramString, Object paramObject);

  public abstract WebDialogBase createWebDialog(Context paramContext, int paramInt);

  public abstract Class<?> getMPlayer();

  public abstract Class<?> getMainActivity();

  public abstract Class<?> getMessageSystemActivity();

  public abstract Class<?> getMyMediaActivity();

  public abstract Class<?> getSearchActivity();

  public abstract Class<?> getVideoListActivity();

  public abstract Class<?> getWebActivity();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.uilogic.ActivityAdapter
 * JD-Core Version:    0.6.2
 */