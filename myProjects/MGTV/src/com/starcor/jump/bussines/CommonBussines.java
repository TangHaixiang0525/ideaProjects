package com.starcor.jump.bussines;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import com.starcor.bussines.manager.Bussines;
import com.starcor.bussines.manager.BussinesData;
import com.starcor.common.IntentDataManager;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.domain.PurchaseParam;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.HotPolymerizationActivity;
import com.starcor.hunan.LiveShowActivity;
import com.starcor.hunan.LiveShowListActivity;
import com.starcor.hunan.PopularMoviePreviewActivity;
import com.starcor.hunan.RankListActivity;
import com.starcor.hunan.ServiceActivity;
import com.starcor.hunan.SplashActivity;
import com.starcor.hunan.StarActivity;
import com.starcor.hunan.StarDetailedActivity;
import com.starcor.hunan.XULActivity;
import com.starcor.hunan.service.ReservationService;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.hunan.uilogic.ActivityAdapterV4;
import com.starcor.hunan.uilogic.CommonRecevier;
import com.starcor.jump.bussines.behavior.BehaviorData;
import com.starcor.jump.bussines.data.CommonData;
import com.starcor.jump.bussines.simple.ShowFavoriteBussines;
import com.starcor.jump.bussines.simple.ShowPlayListBussines;
import com.starcor.jump.bussines.simple.ShowSearchBussines;
import com.starcor.xul.XulUtils;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public abstract class CommonBussines extends Bussines
{
  private static final String EVENT_ID_CLICK_OPEN = "click_open";
  private static final int SHOW_CUSTOMIZED_TOAST = 0;
  public static final int SPECIAL_JSON_DATA = 10;
  private static final String TAG = CommonBussines.class.getSimpleName();
  private static final int TOKENID;
  protected CommonData _data;
  private boolean isFromOut;
  private ArrayList<Bundle> mainPageMenuActionMap = new ArrayList();

  private int getIntFromString(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return 0;
    try
    {
      int i = Integer.valueOf(paramString).intValue();
      return i;
    }
    catch (Exception localException)
    {
    }
    return 0;
  }

  private ArrayList<Bundle> getMainPageActionMap()
  {
    if ((this.mainPageMenuActionMap == null) || (this.mainPageMenuActionMap.isEmpty()))
      this.mainPageMenuActionMap = parseMenuInfoByMetaData();
    if (this.mainPageMenuActionMap == null)
      return null;
    return this.mainPageMenuActionMap;
  }

  private ArrayList<Bundle> parseMenuInfoByMetaData()
  {
    ArrayList localArrayList = new ArrayList();
    while (true)
    {
      XmlPullParser localXmlPullParser;
      int k;
      Bundle localBundle1;
      int m;
      try
      {
        localXmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
        localXmlPullParser.setInput(new ByteArrayInputStream(GlobalLogic.getInstance().getMetaData()), "UTF-8");
        int i = 1;
        if (i != 0)
        {
          int j = localXmlPullParser.next();
          if (j != 1)
            switch (j)
            {
            case 2:
              String str1 = localXmlPullParser.getName();
              if ((i != 0) && (j != 1) && (!"meta_data".equals(str1)))
              {
                j = localXmlPullParser.next();
                if (j != 2)
                  continue;
                str1 = localXmlPullParser.getName();
                continue;
              }
              if ((j != 2) || (!"meta_data".equals(str1)))
                continue;
              String str2 = localXmlPullParser.getAttributeValue(null, "id");
              if ((!"4.0menu".equals(str2)) && (!"4.0tool".equals(str2)) && (!"4.2recom".equals(str2)) && (!"4.4everyone_is_watching_config".equals(str2)))
                continue;
              if (localXmlPullParser.nextTag() != 2)
                continue;
              if ("page".equals(localXmlPullParser.getName()))
                break label589;
              i = 0;
              continue;
              if (k == 0)
                continue;
              switch (localXmlPullParser.nextTag())
              {
              case 2:
                if (!"item_data".equals(localXmlPullParser.getName()))
                  continue;
                localBundle1 = new Bundle();
                Bundle localBundle2 = new Bundle();
                localBundle1.putBundle("args", localBundle2);
                localBundle1.putString("name", localXmlPullParser.getAttributeValue(null, "name"));
                if (localXmlPullParser.nextTag() != 2)
                  continue;
                if (!"action".equals(localXmlPullParser.getName()))
                  continue;
                localBundle1.putString("action", localXmlPullParser.nextText());
                if (localXmlPullParser.nextTag() != 2)
                  continue;
                if (!"arg_list".equals(localXmlPullParser.getName()))
                  continue;
                m = 1;
                if (m == 0)
                  continue;
                switch (localXmlPullParser.nextTag())
                {
                case 2:
                  if (!"a".equals(localXmlPullParser.getName()))
                    continue;
                  if (localXmlPullParser.nextTag() != 2)
                    continue;
                  if (!"k".equals(localXmlPullParser.getName()))
                    continue;
                  String str4 = localXmlPullParser.nextText();
                  if (localXmlPullParser.nextTag() != 2)
                    continue;
                  if (!"v".equals(localXmlPullParser.getName()))
                    continue;
                  localBundle2.putString(str4, localXmlPullParser.nextText());
                  continue;
                case 3:
                }
                break;
              case 3:
              }
              break;
            }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return localArrayList;
      if ("arg_list".equals(localXmlPullParser.getName()))
      {
        m = 0;
        continue;
        String str3 = localXmlPullParser.getName();
        if ("item_data".equals(str3))
        {
          if (localBundle1 != null)
            localArrayList.add(localBundle1);
        }
        else
        {
          boolean bool = "page".equals(str3);
          if (bool)
          {
            k = 0;
            continue;
            continue;
            label589: k = 1;
            localBundle1 = null;
            continue;
          }
        }
      }
    }
  }

  public BussinesData excute()
  {
    if ((this._data.getActivity() != null) && ((this._data.getActivity() instanceof SplashActivity)))
      this._data.getActivity().finish();
    return super.excute();
  }

  protected Bundle findMenuItem(String[] paramArrayOfString, String paramString1, String paramString2)
  {
    ArrayList localArrayList = getMainPageActionMap();
    Bundle localBundle1;
    if (localArrayList == null)
    {
      localBundle1 = null;
      return localBundle1;
    }
    Iterator localIterator = localArrayList.iterator();
    while (true)
      if (localIterator.hasNext())
      {
        localBundle1 = (Bundle)localIterator.next();
        IntentDataManager.dumpBundleData(localBundle1);
        String str1 = localBundle1.getString("name");
        String str2 = localBundle1.getString("action");
        Bundle localBundle2 = localBundle1.getBundle("args");
        if (paramArrayOfString != null)
        {
          int i = paramArrayOfString.length;
          for (int j = 0; ; j++)
          {
            if (j >= i)
              break label123;
            String str3 = paramArrayOfString[j];
            if ((str3 != null) && (str3.equalsIgnoreCase(str1)))
              break;
          }
        }
        label123: if ((paramString2 != null) && (localBundle2 != null) && (paramString2.equalsIgnoreCase(localBundle2.getString("media_asset_id"))))
          break;
        if ((paramString1 != null) && (paramString1.equals(str2)))
          return localBundle1;
      }
    return null;
  }

  protected String getTimeString(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString2))
      new IllegalArgumentException("pattern is null");
    Logger.e(TAG, "timeStr -->" + paramString1);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString2);
    try
    {
      Date localDate = localSimpleDateFormat.parse(paramString1);
      Logger.i(TAG, "getTimeString:" + localDate.toLocaleString());
      return paramString1;
    }
    catch (Exception localException)
    {
      Logger.i(TAG, "getTimeString: null");
    }
    return null;
  }

  protected boolean innerCommonStart()
  {
    super.innerCommonStart();
    addFlags(276824064);
    this._data = ((CommonData)this._data);
    if (this._data.hasInit())
    {
      if ((this._data.getActivity() == null) || (!(this._data.getActivity() instanceof SplashActivity)))
        break label105;
      this.isFromOut = true;
    }
    while (true)
    {
      if ((this._data.isFromWeiXin) || (this._data.isFromWeb))
        this.isFromOut = false;
      if (this.isFromOut)
        putExtra("cmd_is_from_out", "cmd_is_from_out");
      return true;
      label105: if ((this._data.getReceiver() != null) && ((this._data.getReceiver() instanceof CommonRecevier)))
      {
        this.isFromOut = true;
        putExtra("isbroadcast", true);
      }
    }
  }

  protected boolean isFromOut()
  {
    Logger.i(TAG, "isFromOut=" + this.isFromOut);
    return this.isFromOut;
  }

  protected boolean isSpecialJsonData()
  {
    return this._data.behaviorData.what == 10;
  }

  protected void processForMainActivity()
  {
    setClassForActivity(ActivityAdapter.getInstance().getMainActivity());
    putExtra("MetaDataInfo", GlobalLogic.getInstance().getN3A2MetaGroups());
    Logger.i(TAG, "startMainActivity");
  }

  protected void processForSplashActivity()
  {
    setClassForActivity(SplashActivity.class);
    Logger.i(TAG, "SplashActivity");
  }

  protected void setData(CommonData paramCommonData)
  {
    super.setData(paramCommonData);
    this._data = ((CommonData)this._data);
    this._data.startProcessData();
  }

  public void startActivityByCommand(String paramString, Bundle paramBundle)
  {
    Logger.i(TAG, "startActivityByCommand:" + paramString);
    if (paramBundle == null)
      paramBundle = new Bundle();
    if ("m_system_setting".equals(paramString))
    {
      Logger.i(TAG, "m_system_setting");
      setAction("android.settings.SETTINGS");
      setExcuteType(1);
      Logger.i(TAG, "startActivityByCommand:=m_system_setting");
    }
    do
    {
      do
      {
        String str35;
        do
        {
          return;
          if ("m_open_buy_vip_page".equals(paramString))
          {
            Logger.i(TAG, "m_open_buy_vip_page");
            setClassForActivity(XULActivity.class);
            if (TextUtils.isEmpty(GlobalLogic.getInstance().getWebToken()))
            {
              GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
              GlobalLogic.getInstance().setAutoJumpToDetailedPage(false);
              putExtra("xulPageId", "LoginAndRegistration");
              putExtra("xulIsClose", "false");
              GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(true, "", ""));
              GlobalLogic.getInstance().setAutoJumpToDetailedPage(false);
            }
            while (true)
            {
              Logger.i(TAG, "startActivityByCommand:=m_open_new_buy_vip_page");
              return;
              putExtra("xulPageId", "PurchaseVIP");
              GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
              GlobalLogic.getInstance().setAutoJumpToDetailedPage(false);
            }
          }
          if ("m_open_detail_page".equals(paramString))
          {
            this._data.videoId = paramBundle.getString("video_id");
            String str49 = paramBundle.getString("video_type");
            if (!TextUtils.isEmpty(str49))
              this._data.videoType = Integer.valueOf(str49).intValue();
            String str50 = paramBundle.getString("view_type");
            if (!TextUtils.isEmpty(str50))
              this._data.videoUiStyle = Integer.valueOf(str50).intValue();
            this._data.name = paramBundle.getString("name");
            changeBussines(new ShowDetailBussines());
            return;
          }
          if ("m_open_detail_page_position_index".equals(paramString))
          {
            Logger.i(TAG, "m_open_detail_page");
            this._data.videoId = paramBundle.getString("video_id");
            String str46 = paramBundle.getString("video_type");
            if (!TextUtils.isEmpty(str46))
              this._data.videoType = Integer.valueOf(str46).intValue();
            String str47 = paramBundle.getString("view_type");
            if (!TextUtils.isEmpty(str47))
              this._data.videoUiStyle = Integer.valueOf(str47).intValue();
            String str48 = paramBundle.getString("video_index");
            if (!TextUtils.isEmpty(str48))
              this._data.videoIndex = Integer.valueOf(str48).intValue();
            this._data.name = paramBundle.getString("name");
            changeBussines(new ShowDetailBussines());
            return;
          }
          if ("m_open_tstv_page".equals(paramString))
          {
            Logger.i(TAG, "m_open_tstv_page");
            this._data.packetId = paramBundle.getString("media_asset_id");
            this._data.name = paramBundle.getString("name");
            this._data.categoryId = paramBundle.getString("category_id");
            this._data.videoUiStyle = getIntFromString(paramBundle.getString("ui_style"));
            this._data.videoId = paramBundle.getString("video_id");
            this._data.videoType = getIntFromString(paramBundle.getString("video_type"));
            changeBussines(new PlayTimeShiftBussines());
            return;
          }
          if ("m_open_rollingbroadcase_page".equals(paramString))
          {
            this._data.mediaAssetId = paramBundle.getString("media_asset_id");
            this._data.name = paramBundle.getString("name");
            this._data.categoryId = paramBundle.getString("category_id");
            this._data.videoUiStyle = getIntFromString(paramBundle.getString("ui_style"));
            this._data.videoId = paramBundle.getString("video_id");
            this._data.videoType = getIntFromString(paramBundle.getString("video_type"));
            changeBussines(new PlayTimeShiftBussines());
            return;
          }
          if ("m_open_playbill_page".equals(paramString))
          {
            Logger.i(TAG, "m_open_playbill_page");
            String str38 = paramBundle.getString("media_asset_id");
            String str39 = paramBundle.getString("category_id");
            String str40 = paramBundle.getString("ui_style");
            String str41 = paramBundle.getString("video_id");
            String str42 = paramBundle.getString("video_type");
            String str43 = paramBundle.getString("begin_time");
            String str44 = paramBundle.getString("time_len");
            String str45 = paramBundle.getString("name");
            setClassForActivity(ActivityAdapter.getNewReplayActivity());
            putExtra("recommend_type", "1");
            MetadataInfo localMetadataInfo6 = new MetadataInfo();
            localMetadataInfo6.category_id = str39;
            localMetadataInfo6.packet_id = str38;
            localMetadataInfo6.uiStyle = XulUtils.tryParseInt(str40);
            localMetadataInfo6.video_id = str41;
            localMetadataInfo6.video_type = str42;
            localMetadataInfo6.begin_time = str43;
            localMetadataInfo6.time_len = str44;
            localMetadataInfo6.name = str45;
            putExtra("MetaDataInfo", localMetadataInfo6);
            putExtra("defaultChannel", str41);
            if (!TextUtils.isEmpty(str43))
            {
              putExtra("date", str43.substring(0, 8));
              putExtra("begin_time", str43.substring(8));
            }
            putExtra("recommend_type", "0,0");
            return;
          }
          if (!"m_open_playbill_recom_page".equals(paramString))
            break;
          Logger.i(TAG, "m_open_playbill_recom_page");
          String str30 = paramBundle.getString("media_asset_id");
          String str31 = paramBundle.getString("category_id");
          String str32 = paramBundle.getString("ui_style");
          String str33 = paramBundle.getString("video_id");
          String str34 = paramBundle.getString("video_type");
          str35 = paramBundle.getString("begin_time");
          String str36 = paramBundle.getString("time_len");
          String str37 = paramBundle.getString("name");
          setClassForActivity(ActivityAdapter.getNewReplayActivity());
          putExtra("recommend_type", "0,0");
          MetadataInfo localMetadataInfo5 = new MetadataInfo();
          localMetadataInfo5.category_id = str31;
          localMetadataInfo5.packet_id = str30;
          localMetadataInfo5.uiStyle = XulUtils.tryParseInt(str32);
          localMetadataInfo5.video_id = str33;
          localMetadataInfo5.video_type = str34;
          localMetadataInfo5.begin_time = str35;
          localMetadataInfo5.time_len = str36;
          localMetadataInfo5.name = str37;
          putExtra("MetaDataInfo", localMetadataInfo5);
          putExtra("defaultChannel", str33);
        }
        while (TextUtils.isEmpty(str35));
        putExtra("date", str35.substring(0, 8));
        putExtra("begin_time", str35.substring(8));
        putExtra("FromCommend", true);
        return;
        if ("m_open_player".equals(paramString))
        {
          Logger.i(TAG, "m_open_player");
          this._data.mediaAssetId = paramBundle.getString("media_asset_id");
          this._data.categoryId = paramBundle.getString("category_id");
          this._data.videoId = paramBundle.getString("video_id");
          this._data.videoType = getIntFromString(paramBundle.getString("video_type"));
          this._data.videoIndex = getIntFromString(paramBundle.getString("video_index"));
          this._data.videoBeginTime = paramBundle.getString("begin_time");
          this._data.videoDuration = getIntFromString(paramBundle.getString("time_len"));
          this._data.videoIndexName = paramBundle.getString("film_name");
          this._data.videoIndexName = paramBundle.getString("name");
          this._data.videoChannelIndex = paramBundle.getString("channel_index");
          String str29 = 1 + XulUtils.tryParseInt(paramBundle.getString("idx")) + "";
          this._data.huaweiCid = paramBundle.getString("huawei_cid");
          Logger.i(TAG, "idx=" + str29 + "---->");
          try
          {
            this._data.isSelectTv = Boolean.parseBoolean(paramBundle.getString("is_select_tv"));
            changeBussines(new PlayVideoBussines());
            return;
          }
          catch (Exception localException)
          {
            while (true)
              this._data.isSelectTv = false;
          }
        }
        if ("m_open_asset_page".equals(paramString))
        {
          Logger.i(TAG, "m_open_asset_page");
          String str25 = paramBundle.getString("media_asset_id");
          String str26 = paramBundle.getString("category_id");
          String str27 = paramBundle.getString("ui_style");
          String str28 = paramBundle.getString("name");
          setClassForActivity(ActivityAdapter.getInstance().getVideoListActivity());
          MetadataInfo localMetadataInfo4 = new MetadataInfo();
          localMetadataInfo4.category_id = str26;
          localMetadataInfo4.packet_id = str25;
          localMetadataInfo4.uiStyle = XulUtils.tryParseInt(str27);
          localMetadataInfo4.name = str28;
          putExtra("MetaDataInfo", localMetadataInfo4);
          return;
        }
        if ("m_open_web".equals(paramString))
        {
          Logger.i(TAG, "m_open_web");
          String str20 = paramBundle.getString("media_asset_id");
          String str21 = paramBundle.getString("category_id");
          String str22 = paramBundle.getString("ui_style");
          String str23 = paramBundle.getString("name");
          String str24 = paramBundle.getString("ex_url");
          MetadataInfo localMetadataInfo3 = new MetadataInfo();
          localMetadataInfo3.category_id = str21;
          localMetadataInfo3.packet_id = str20;
          localMetadataInfo3.uiStyle = XulUtils.tryParseInt(str22);
          localMetadataInfo3.url = str24;
          localMetadataInfo3.name = str23;
          localMetadataInfo3.action_type = "web";
          setClassForActivity(ActivityAdapter.getInstance().getWebActivity());
          putExtra("MetaDataInfo", localMetadataInfo3);
          return;
        }
        if ("m_open_app".equals(paramString))
        {
          paramBundle.getString("ex_url");
          paramBundle.getString("data");
          return;
        }
        if ("m_open_collect_page".equals(paramString))
        {
          changeBussines(new ShowFavoriteBussines());
          return;
        }
        if ("m_open_playlist_page".equals(paramString))
        {
          changeBussines(new ShowPlayListBussines());
          return;
        }
        if ("m_open_user_order_page".equals(paramString))
        {
          setClassForActivity(ActivityAdapterV4.getInstance().getMyMediaActivity());
          return;
        }
      }
      while ("m_open_help_page".equals(paramString));
      if ("m_user_manager_page".equals(paramString))
      {
        Logger.i(TAG, "m_user_manager_page");
        String str19 = GlobalLogic.getInstance().getWebToken();
        setClassForActivity(XULActivity.class);
        if (TextUtils.isEmpty(str19))
        {
          putExtra("xulPageId", "LoginAndRegistration");
          putExtra("xulIsClose", "false");
          GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
          GlobalLogic.getInstance().setAutoJumpToDetailedPage(false);
          return;
        }
        putExtra("xulPageId", "NewUserCenter");
        GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
        GlobalLogic.getInstance().setAutoJumpToDetailedPage(false);
        return;
      }
      if ("m_open_search_page".equals(paramString))
      {
        this._data.name = paramBundle.getString("data");
        changeBussines(new ShowSearchBussines());
        return;
      }
      if ("m_open_service_page".equals(paramString))
      {
        Logger.i(TAG, "m_open_service_page");
        setClassForActivity(ServiceActivity.class);
        return;
      }
      if ("m_open_special_page".equals(paramString))
      {
        Logger.i(TAG, "m_open_special_page");
        String str15 = paramBundle.getString("special_id");
        String str16 = paramBundle.getString("category_id");
        String str17 = paramBundle.getString("ui_style");
        String str18 = paramBundle.getString("name");
        MetadataInfo localMetadataInfo2 = new MetadataInfo();
        localMetadataInfo2.category_id = str16;
        localMetadataInfo2.packet_id = str15;
        localMetadataInfo2.uiStyle = XulUtils.tryParseInt(str17);
        localMetadataInfo2.name = str18;
        putExtra("MetaDataInfo", localMetadataInfo2);
        changeBussines(new ShowSpecialBussines());
        return;
      }
      if (("m_open_message_dialog".equals(paramString)) || ("m_open_message_system".equals(paramString)))
      {
        Logger.i(TAG, "action=" + paramString);
        setClassForActivity(ActivityAdapter.getInstance().getMessageSystemActivity());
        return;
      }
      if ("m_open_popstar_list_page".equals(paramString))
      {
        setClassForActivity(StarActivity.class);
        return;
      }
      if ("m_open_playbill_selected_list_page".equals(paramString))
      {
        String str13 = paramBundle.getString("media_asset_id");
        String str14 = paramBundle.getString("name");
        JSONObject localJSONObject2 = new JSONObject();
        try
        {
          localJSONObject2.put("packetId", str13);
          localJSONObject2.put("name", str14);
          localJSONObject2.put("showIcon", AppFuncCfg.FUNCTION_TV_SELECT_SHOW_ICON);
          setClassForActivity(XULActivity.class);
          putExtra("xulPageId", "GetPlaybillSelected");
          putExtra("xulData", localJSONObject2.toString());
          putExtra("packetId", str13);
          return;
        }
        catch (JSONException localJSONException2)
        {
          while (true)
            localJSONException2.printStackTrace();
        }
      }
      if ("m_open_live_hot_page".equals(paramString))
      {
        setClassForActivity(HotPolymerizationActivity.class);
        String str11 = paramBundle.getString("media_asset_id");
        String str12 = paramBundle.getString("category_id");
        putExtra("xulPageId", "HotPolymerization");
        putExtra("xulData", "");
        putExtra("packetId", str11);
        putExtra("categoryId", str12);
        return;
      }
      if ("m_open_exchange_card_page".equals(paramString))
      {
        String str10 = GlobalLogic.getInstance().getWebToken();
        setClassForActivity(XULActivity.class);
        GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
        GlobalLogic.getInstance().setAutoJumpToDetailedPage(false);
        if (TextUtils.isEmpty(str10))
        {
          putExtra("xulIsClose", "false");
          putExtra("xulPageId", "LoginAndRegistration");
          putExtra("xulAutoJumpPage", "CouponCard");
          return;
        }
        putExtra("xulPageId", "CouponCard");
        return;
      }
      if ("m_open_star_detail_page".equals(paramString))
      {
        setClassForActivity(StarDetailedActivity.class);
        String str7 = paramBundle.getString("name");
        String str8 = paramBundle.getString("star_id");
        String str9 = paramBundle.getString("label_id");
        putExtra("actor", str7);
        putExtra("actorID", str8);
        putExtra("labelID", str9);
        return;
      }
      if ("m_open_live_show_home".equalsIgnoreCase(paramString))
      {
        JSONObject localJSONObject1 = new JSONObject();
        try
        {
          localJSONObject1.put("ids", "");
          setClassForActivity(LiveShowActivity.class);
          putExtra("xulPageId", "LiveShow");
          return;
        }
        catch (JSONException localJSONException1)
        {
          while (true)
            localJSONException1.printStackTrace();
        }
      }
      if ("m_open_live_show_list_page".equalsIgnoreCase(paramString))
      {
        String str5 = paramBundle.getString("category_id");
        String str6 = paramBundle.getString("media_asset_id");
        setClassForActivity(LiveShowListActivity.class);
        putExtra("xulPageId", "LiveShowList");
        putExtra("packetId", str6);
        putExtra("categoryId", str5);
        return;
      }
      if ("m_open_live_show_detail_page".equalsIgnoreCase(paramString))
      {
        MetadataInfo localMetadataInfo1 = new MetadataInfo();
        String str1 = paramBundle.getString("special_id");
        String str2 = paramBundle.getString("begin_time");
        if (TextUtils.isEmpty(str2))
          str2 = paramBundle.getString("begin_time1");
        localMetadataInfo1.begin_time = str2;
        localMetadataInfo1.url = paramBundle.getString("link_url");
        long l = ReservationService.time2Reservation(str2);
        if (ReservationService.getinstance().findReservation(l, str1) != null);
        while (true)
        {
          if (localMetadataInfo1.url.indexOf('?') < 0)
            localMetadataInfo1.url += "?";
          localMetadataInfo1.url = (localMetadataInfo1.url + "&live_show_id=" + str1);
          localMetadataInfo1.action_type = "web";
          Logger.i(TAG, "info.url=" + localMetadataInfo1.url);
          setClassForActivity(ActivityAdapter.getInstance().getWebActivity());
          putExtra("MetaDataInfo", localMetadataInfo1);
          return;
        }
      }
      if ("m_open_big_slice_first_look_page".equalsIgnoreCase(paramString))
      {
        setClassForActivity(PopularMoviePreviewActivity.class);
        String str4 = paramBundle.getString("special_id");
        putExtra("xulPageId", "PopularMoviePreview");
        putExtra("xulData", "");
        putExtra("special_package_id", str4);
        return;
      }
    }
    while (!"m_open_video_rank_page".equalsIgnoreCase(paramString));
    setClassForActivity(RankListActivity.class);
    String str3 = paramBundle.getString("media_asset_id");
    putExtra("xulPageId", "RankingList");
    putExtra("xulData", "");
    putExtra("mediaAssetId", str3);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.CommonBussines
 * JD-Core Version:    0.6.2
 */