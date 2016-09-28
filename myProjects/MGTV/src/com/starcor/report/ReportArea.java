package com.starcor.report;

import com.starcor.hunan.AdvertActivity;
import com.starcor.hunan.AppStoreWebActivity;
import com.starcor.hunan.ChangeSkinActivity;
import com.starcor.hunan.DetailPageActivity;
import com.starcor.hunan.FilterPageActivity;
import com.starcor.hunan.LiveShowActivity;
import com.starcor.hunan.MorePageActivity;
import com.starcor.hunan.NetworkErrorActivity;
import com.starcor.hunan.NewReplayActivityForV4;
import com.starcor.hunan.PopularMoviePreviewActivity;
import com.starcor.hunan.RankListActivity;
import com.starcor.hunan.ServiceActivity;
import com.starcor.hunan.ServiceActivityV2;
import com.starcor.hunan.SpecialActivityV2;
import com.starcor.hunan.SpecialPlayerActivity;
import com.starcor.hunan.SplashActivity;
import com.starcor.hunan.StarActivity;
import com.starcor.hunan.StarDetailedActivity;
import com.starcor.hunan.msgsys.activity.MessageSystemActivityV3;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.media.player.MplayerPlaybackView;
import com.starcor.media.player.MplayerTimeshiftView;
import com.starcor.media.player.MplayerVODView;
import java.util.HashMap;

public class ReportArea
{
  public static final String ADPAGE = "X";
  public static final String APPSTORE = "Y";
  public static final String BIND_WECHAT = "UB";
  public static final String CAROUSEL_PLAY = "L";
  public static final String CHANGE_PWD = "UA";
  public static final String CONSUMPION_HISTORY = "T";
  public static final String COUPON_CARD = "V";
  public static final String DETAIL = "C";
  public static final String DETAIL_RECOMMEND = "C1";
  public static final String EXTWEB = "KW";
  public static final String FILTER = "FI";
  public static final String LIVESHOW = "J";
  public static final String LOGIN_AND_REGISTRATION = "O";
  public static final String MAIN = "A";
  public static final String MAIN_CHANNEL = "A3";
  public static final String MAIN_LIKE = "A4";
  public static final String MAIN_OTHER_RECOMMEND = "A5";
  public static final String MAIN_PLAY = "A1";
  public static final String MAIN_RECOMMEND = "A2";
  public static final String MEDIA = "E";
  public static final String MEDIA_ALL = "E1";
  public static final String MEDIA_COLLECT = "E3";
  public static final String MEDIA_PLAY = "E2";
  public static final String MEDIA_TRACEPLAY = "E4";
  public static final String MESSAGE_DIALOG = "G2";
  public static final String MESSAGE_LIST = "G1";
  public static String MESSAGE_SYSTEM = "G";
  public static final String MGTV_BROWSER_PAY = "P3";
  public static final String MGTV_MOVIE_COUNPON = "S";
  public static final String MGTV_PURCHASE_MODE = "P4";
  public static final String MORE = "MO";
  public static final String MOVIE_COUPON_HISTORY = "TA";
  public static final String MPLAYER_BUY_PLAY = "I8";
  public static final String MPLAYER_CHANGE_DEFINITION = "I6";
  public static final String MPLAYER_CHANGE_VIDEO = "I3";
  public static final String MPLAYER_DIFFERENT_EPISODIC = "I5";
  public static final String MPLAYER_DRAG_SEEKBAR = "I10";
  public static final String MPLAYER_LOGIN_PLAY = "I7";
  public static final String MPLAYER_QUIT_NEXT = "I2";
  public static final String MPLAYER_QUIT_RECOMMEND = "I1";
  public static final String MPLAYER_SAME_EPISODIC = "I4";
  public static final String NETERROR = "NE";
  public static final String NEW_USER_CENTER = "U";
  public static HashMap PAGE_AREA_MAP = new HashMap();
  public static final String PAYMENT = "P";
  public static final String PAYMENT_SUCCESS = "PP";
  public static final String PAY_MENTORDER_ALIPAY = "P1";
  public static final String PAY_MENTORDER_WECHAT = "P2";
  public static final String PLAY_BACK = "M";
  public static final String PLAY_BACK_LIST = "ML";
  public static final String POPMOVIE = "BB";
  public static final String PURCHASE_SUCCESS = "PP";
  public static final String PURCHASE_VIP = "Q";
  public static final String RANKLIST = "RA";
  public static final String RECHARGE = "R";
  public static final String SEARCH = "D";
  public static final String SEARCH_RECOMMEND = "D1";
  public static final String SEARCH_RESULT = "D2";
  public static final String SERVICE = "W";
  public static final String SKIN = "WA";
  public static final String SPECIAL = "H";
  public static final String SPECIAL_PLAYER = "MH";
  public static final String SPECIAL_PLAYER_FULL_SCREEN = "MH_F";
  public static final String SPECIAL_PLAYER_SMALL_SCREEN = "MH_S";
  public static final String SPECIAL_RECOMMEND = "H1";
  public static final String SPLASH = "SP";
  public static final String STAR_DETAIL = "F";
  public static final String STAR_DETAIL_GUEST = "F2";
  public static final String STAR_DETAIL_WORKS = "F1";
  public static final String STAR_LIBRARY = "SL";
  public static final String TIME_SHIFT = "N";
  public static final String USER_AGREEMENT = "OA";
  public static final String USER_FEEDBACK = "UF";
  public static final String USER_FEEDBACK_DEVICE_INFO = "UF_D";
  public static final String USER_FEEDBACK_SUCCESS = "UF_S";
  public static final String VIDEOLIST = "B";
  public static final String VIDEOLIST_LIKE = "B2";
  public static final String VIDEOLIST_PLAY = "B4";
  public static final String VIDEOLIST_RECOMMEND = "B1";
  public static final String VIDEOLIST_STAR = "B3";
  public static final String VOD = "I";
  public static final String ZFBSQ_FAIL = "P1F";

  public static String getPageName(Class<?> paramClass)
  {
    return (String)PAGE_AREA_MAP.get(paramClass.getSimpleName());
  }

  public static String getValue(String paramString)
  {
    return (String)PAGE_AREA_MAP.get(paramString);
  }

  public static void init()
  {
    PAGE_AREA_MAP.put(ActivityAdapter.getInstance().getMainActivity().getSimpleName(), "A");
    PAGE_AREA_MAP.put(ActivityAdapter.getInstance().getVideoListActivity().getSimpleName(), "B");
    PAGE_AREA_MAP.put(DetailPageActivity.class.getSimpleName(), "C");
    PAGE_AREA_MAP.put(ActivityAdapter.getInstance().getMyMediaActivity().getSimpleName(), "E");
    PAGE_AREA_MAP.put(SpecialActivityV2.class.getSimpleName(), "H");
    PAGE_AREA_MAP.put(SpecialPlayerActivity.class.getSimpleName(), "MH");
    PAGE_AREA_MAP.put(ActivityAdapter.getInstance().getSearchActivity().getSimpleName(), "D");
    PAGE_AREA_MAP.put(StarDetailedActivity.class.getSimpleName(), "F");
    PAGE_AREA_MAP.put(ActivityAdapter.getInstance().getMPlayer().getSimpleName(), "I");
    PAGE_AREA_MAP.put(LiveShowActivity.class.getSimpleName(), "J");
    PAGE_AREA_MAP.put(ActivityAdapter.getInstance().getWebActivity().getSimpleName(), "KW");
    PAGE_AREA_MAP.put(MessageSystemActivityV3.class.getSimpleName(), MESSAGE_SYSTEM);
    PAGE_AREA_MAP.put(ServiceActivityV2.class.getSimpleName(), "W");
    PAGE_AREA_MAP.put(ServiceActivity.class.getSimpleName(), "W");
    PAGE_AREA_MAP.put(ChangeSkinActivity.class.getSimpleName(), "WA");
    PAGE_AREA_MAP.put(AppStoreWebActivity.class.getSimpleName(), "Y");
    PAGE_AREA_MAP.put(PopularMoviePreviewActivity.class.getSimpleName(), "BB");
    PAGE_AREA_MAP.put(AdvertActivity.class.getSimpleName(), "X");
    PAGE_AREA_MAP.put(RankListActivity.class.getSimpleName(), "RA");
    PAGE_AREA_MAP.put(NetworkErrorActivity.class.getSimpleName(), "NE");
    PAGE_AREA_MAP.put(FilterPageActivity.class.getSimpleName(), "FI");
    PAGE_AREA_MAP.put(StarActivity.class.getSimpleName(), "SL");
    PAGE_AREA_MAP.put(MorePageActivity.class.getSimpleName(), "MO");
    PAGE_AREA_MAP.put(NewReplayActivityForV4.class.getSimpleName(), "ML");
    PAGE_AREA_MAP.put(SplashActivity.class.getSimpleName(), "SP");
    PAGE_AREA_MAP.put("UserAgreement", "OA");
    PAGE_AREA_MAP.put("LoginAndRegistration", "O");
    PAGE_AREA_MAP.put("ConsumptionHistory", "T");
    PAGE_AREA_MAP.put("mgtvBrowserPay", "P3");
    PAGE_AREA_MAP.put("Payment", "P");
    PAGE_AREA_MAP.put("PaymentSuccess", "PP");
    PAGE_AREA_MAP.put("MgtvPurchaseMode", "P4");
    PAGE_AREA_MAP.put("PurchaseSuccess", "PP");
    PAGE_AREA_MAP.put("PurchaseVIP", "Q");
    PAGE_AREA_MAP.put("CouponCard", "V");
    PAGE_AREA_MAP.put("mgtvMovieCoupon", "S");
    PAGE_AREA_MAP.put("MovieCouponHistory", "TA");
    PAGE_AREA_MAP.put("Recharge", "R");
    PAGE_AREA_MAP.put("NewUserCenter", "U");
    PAGE_AREA_MAP.put("XiaoMiUserCenter", "U");
    PAGE_AREA_MAP.put("ChangePassword", "UA");
    PAGE_AREA_MAP.put("WebchatBind", "UB");
    PAGE_AREA_MAP.put("AlipayFail", "P1F");
    PAGE_AREA_MAP.put("PayMentorderWechat", "P2");
    PAGE_AREA_MAP.put("PayMentorderAlipaay", "P1");
    PAGE_AREA_MAP.put("main_play", "A1");
    PAGE_AREA_MAP.put("main_recommend", "A2");
    PAGE_AREA_MAP.put("main_channel", "A3");
    PAGE_AREA_MAP.put("main_like", "A4");
    PAGE_AREA_MAP.put("main_other_recommend", "A5");
    PAGE_AREA_MAP.put("videolist_like", "B2");
    PAGE_AREA_MAP.put("videolist_play", "B4");
    PAGE_AREA_MAP.put("videolist_star", "B3");
    PAGE_AREA_MAP.put("videolist_recommend", "B1");
    PAGE_AREA_MAP.put("detail_recommend", "C1");
    PAGE_AREA_MAP.put("search_recommend", "D1");
    PAGE_AREA_MAP.put("search_result", "D2");
    PAGE_AREA_MAP.put("media_all", "E1");
    PAGE_AREA_MAP.put("media_play", "E2");
    PAGE_AREA_MAP.put("media_collect", "E3");
    PAGE_AREA_MAP.put("media_traceplay", "E4");
    PAGE_AREA_MAP.put("star_detail_works", "F1");
    PAGE_AREA_MAP.put("star_detail_guest", "F2");
    PAGE_AREA_MAP.put("special_recommend", "H1");
    PAGE_AREA_MAP.put("mplayer_quit_recommend", "I1");
    PAGE_AREA_MAP.put("mplayer_quit_next", "I2");
    PAGE_AREA_MAP.put("mplayer_change_video", "I3");
    PAGE_AREA_MAP.put("mplayer_same_episodic", "I4");
    PAGE_AREA_MAP.put("mplayer_different_episodic", "I5");
    PAGE_AREA_MAP.put("mplayer_change_definition", "I6");
    PAGE_AREA_MAP.put("mplayer_buy_play", "I8");
    PAGE_AREA_MAP.put("mplayer_login_play", "I7");
    PAGE_AREA_MAP.put("mplayer_drag_seekBar", "I10");
    PAGE_AREA_MAP.put("message_list", "G1");
    PAGE_AREA_MAP.put("message_dialog", "G2");
    PAGE_AREA_MAP.put(MplayerPlaybackView.class.getSimpleName(), "M");
    PAGE_AREA_MAP.put(MplayerVODView.class.getSimpleName(), "I");
    PAGE_AREA_MAP.put(MplayerTimeshiftView.class.getSimpleName(), "L");
    PAGE_AREA_MAP.put("UserFeedbackPage", "UF");
    PAGE_AREA_MAP.put("DeviceInfoPage", "UF_D");
    PAGE_AREA_MAP.put("FeedbackSuccessPage", "UF_S");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.ReportArea
 * JD-Core Version:    0.6.2
 */