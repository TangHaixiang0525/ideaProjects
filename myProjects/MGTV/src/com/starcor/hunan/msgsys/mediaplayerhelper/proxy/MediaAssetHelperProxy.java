package com.starcor.hunan.msgsys.mediaplayerhelper.proxy;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.MediaAssetHelperBase;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.MediaAssetHelperCallback;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenAboutPage;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenAssetPageHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenAssetSearchRangePageHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenBuyVipPageHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenDetailPageHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenExchangeCardPageHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenLiveShowDetailPage;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenMediaPlayerHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenPlaybillPageHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenPlaybillRecommendPageHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenPlaybillSelectedListPageHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenPopStarListPageHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenPopStarSearchPageHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenStarDetailPageHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenTstvHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenUserManagerPageHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenUserPersonalizedRecomPage;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.OpenWebHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.PlayVideoHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.ReserveSpecialTopicHelper;
import com.starcor.hunan.msgsys.mediaplayerhelper.helper.ReserveTurnPlayHelper;
import java.util.Hashtable;

public class MediaAssetHelperProxy
{
  public static final String TAG = MediaAssetHelperProxy.class.getSimpleName();
  private static Hashtable<String, MediaAssetHelperBase> mHelperTaskTable = null;
  private static MediaAssetHelperProxy mInstance = new MediaAssetHelperProxy();
  private String mAction = "";
  private Context mContext = null;
  private Bundle mParamsBundle = null;

  public static MediaAssetHelperProxy getInstance()
  {
    return mInstance;
  }

  public void init(Context paramContext)
  {
    this.mContext = paramContext;
    if (mHelperTaskTable == null)
    {
      Logger.i(TAG, "开始初始化媒资任务列表！");
      mHelperTaskTable = new Hashtable();
      mHelperTaskTable.put("video", new PlayVideoHelper(this.mContext));
      mHelperTaskTable.put("detail", new OpenDetailPageHelper(this.mContext));
      mHelperTaskTable.put("m_open_detail_page", new OpenDetailPageHelper(this.mContext));
      mHelperTaskTable.put("m_open_tstv_page", new OpenTstvHelper(paramContext));
      mHelperTaskTable.put("m_open_player", new OpenMediaPlayerHelper(paramContext));
      mHelperTaskTable.put("m_open_playbill_recom_page", new OpenPlaybillRecommendPageHelper(paramContext));
      mHelperTaskTable.put("m_open_playbill_selected_list_page", new OpenPlaybillSelectedListPageHelper(paramContext));
      mHelperTaskTable.put("m_open_playbill_selected_list_page", new OpenPlaybillSelectedListPageHelper(paramContext));
      mHelperTaskTable.put("m_open_asset_page", new OpenAssetPageHelper(paramContext));
      mHelperTaskTable.put("m_open_asset_search_range_page", new OpenAssetSearchRangePageHelper(paramContext));
      mHelperTaskTable.put("m_open_web", new OpenWebHelper(paramContext));
      mHelperTaskTable.put("m_open_web_by_pop", new OpenWebHelper(paramContext));
      mHelperTaskTable.put("m_open_web_by_right", new OpenWebHelper(paramContext));
      mHelperTaskTable.put("m_open_star_detail_page", new OpenStarDetailPageHelper(paramContext));
      mHelperTaskTable.put("m_open_popstar_search_page", new OpenPopStarSearchPageHelper(paramContext));
      mHelperTaskTable.put("m_open_popstar_list_page", new OpenPopStarListPageHelper(paramContext));
      mHelperTaskTable.put("m_open_user_personalized_recom", new OpenUserPersonalizedRecomPage(paramContext));
      mHelperTaskTable.put("m_about_page", new OpenAboutPage(paramContext));
      mHelperTaskTable.put("m_weixin_weibo_page", new OpenAboutPage(paramContext));
      mHelperTaskTable.put("m_fuwu_xieyi_page", new OpenAboutPage(paramContext));
      mHelperTaskTable.put("m_open_login_page", new OpenUserManagerPageHelper(paramContext));
      mHelperTaskTable.put("m_user_manager_page", new OpenUserManagerPageHelper(paramContext));
      mHelperTaskTable.put("m_open_buy_vip_page", new OpenBuyVipPageHelper(paramContext));
      mHelperTaskTable.put("m_open_exchange_card_page", new OpenExchangeCardPageHelper(paramContext));
      mHelperTaskTable.put("m_open_playbill_page", new OpenPlaybillPageHelper(paramContext));
      mHelperTaskTable.put("reservetopic", new ReserveSpecialTopicHelper(paramContext));
      mHelperTaskTable.put("reserveturnplay", new ReserveTurnPlayHelper(paramContext));
      mHelperTaskTable.put("m_open_live_show_detail_page", new OpenLiveShowDetailPage(paramContext));
    }
  }

  public void startExecuteAction(String paramString, Bundle paramBundle, MediaAssetHelperCallback paramMediaAssetHelperCallback)
  {
    Logger.i(TAG, "startExecuteAction action=" + paramString);
    this.mAction = paramString;
    this.mParamsBundle = paramBundle;
    if ((mHelperTaskTable != null) && (!TextUtils.isEmpty(paramString)))
      if (mHelperTaskTable.containsKey(paramString))
      {
        localMediaAssetHelperBase = (MediaAssetHelperBase)mHelperTaskTable.get(paramString);
        if (localMediaAssetHelperBase != null)
        {
          Logger.i(TAG, "找到符合action的helper==>" + localMediaAssetHelperBase.getHelperName());
          localMediaAssetHelperBase.setParamsBundle(paramBundle);
          localMediaAssetHelperBase.setAction(paramString);
          localMediaAssetHelperBase.setListener(paramMediaAssetHelperCallback);
          localMediaAssetHelperBase.startExecuteAction();
        }
      }
    while (paramMediaAssetHelperCallback == null)
    {
      do
      {
        MediaAssetHelperBase localMediaAssetHelperBase;
        do
          return;
        while (paramMediaAssetHelperCallback == null);
        paramMediaAssetHelperCallback.onError(paramString, "很抱歉，该影片资源已被删除或不存在");
        return;
      }
      while (paramMediaAssetHelperCallback == null);
      paramMediaAssetHelperCallback.onError(paramString, "很抱歉，该影片资源已被删除或不存在");
      return;
    }
    paramMediaAssetHelperCallback.onError(paramString, "很抱歉，该影片资源已被删除或不存在");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.proxy.MediaAssetHelperProxy
 * JD-Core Version:    0.6.2
 */