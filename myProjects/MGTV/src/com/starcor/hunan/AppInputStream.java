package com.starcor.hunan;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.config.MgtvVersion;
import com.starcor.core.domain.AAACouponInfoItem;
import com.starcor.core.domain.AAAMovieCouponCount;
import com.starcor.core.domain.AAAMovieCouponInfo;
import com.starcor.core.domain.AAAOrderInfo;
import com.starcor.core.domain.AAAOrderRecordItem;
import com.starcor.core.domain.AAAOrderRecordList;
import com.starcor.core.domain.AAAOrderState;
import com.starcor.core.domain.AAAPayChannelList;
import com.starcor.core.domain.AAAPriceList;
import com.starcor.core.domain.AAAProductItem;
import com.starcor.core.domain.AAAProductList;
import com.starcor.core.domain.AAASmsPay;
import com.starcor.core.domain.AAAVipInfo;
import com.starcor.core.domain.AAAVipItem;
import com.starcor.core.domain.AAAVipList;
import com.starcor.core.domain.AAAWechatPayQrcode;
import com.starcor.core.domain.AdInfos;
import com.starcor.core.domain.Agreements;
import com.starcor.core.domain.Agreements.AgreementInfo;
import com.starcor.core.domain.GetPlaybillSelectedListInfo;
import com.starcor.core.domain.GetPlaybillSelectedListInfo.Item;
import com.starcor.core.domain.ImageAd;
import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.domain.PayAuthorizeStatus;
import com.starcor.core.domain.PublicImage;
import com.starcor.core.domain.PurchaseParam;
import com.starcor.core.domain.UserAuthV2;
import com.starcor.core.domain.UserCenter3rdLoginUrl;
import com.starcor.core.domain.UserCenterInfo;
import com.starcor.core.domain.UserFeedbackAndDeviceInfo;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.domain.WebChatLogin;
import com.starcor.core.epgapi.params.GetUserAuthV2Params;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.AppKiller;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.service.SystemTimeManager;
import com.starcor.hunan.uilogic.CommonUiTools;
import com.starcor.sccms.api.SccmsApiAAABuyProductTask.ISccmsApiAAABuyProductTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetMovieCouponCountTask.ISccmsApiAAAGetMovieCouponCountTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetMovieCouponHistoryTask.ISccmsApiAAAGetMovieCouponHistoryTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetMovieCouponInfoTask.ISccmsApiAAAGetMovieCouponInfoTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetOrderRecordListTask.ISccmsApiAAAGetOrderRecordListTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetOrderStateTask.ISccmsApiAAAGetOrderStateTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetPayAuthorizeStatusTask.ISccmsApiAAAGetPayAuthorizeStatusListener;
import com.starcor.sccms.api.SccmsApiAAAGetPayChannelListTask.ISccmsApiAAAGetPayChannelListTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetPriceListTask.ISccmsApiAAAGetPriceListTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetProductListTask.ISccmsApiAAAGetProductListTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetUserInfoTask.ISccmsApiGetUserCenterInfoTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetVipInfoTask.ISccmsApiAAAGetVipInfoTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetVipListTask.ISccmsApiAAAGetVipListTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetWechatPayQrcodeTask.ISccmsApiAAAGetWechatPayQrcodeTaskListener;
import com.starcor.sccms.api.SccmsApiAAARechargeTask.ISccmsApiAAARechargeTaskListener;
import com.starcor.sccms.api.SccmsApiAAASmsPayTask.ISccmsApiAAASmsPayTaskListener;
import com.starcor.sccms.api.SccmsApiGetAdInfoByAdPosTask.ISccmsApiGetAdInfoByAdPosTaskListener;
import com.starcor.sccms.api.SccmsApiGetAgreementTask.ISccmsApiGetAgreementTaskListener;
import com.starcor.sccms.api.SccmsApiGetInitMetaDataTask.ISccmsApiGetInitMetaDataTaskListener;
import com.starcor.sccms.api.SccmsApiGetMediaAssetsInfoTask.ISccmsApiGetMediaAssetsInfoTaskListener;
import com.starcor.sccms.api.SccmsApiGetPlaybillSelectedList.ISccmsApiGetPlaybillSelectedListTaskListener;
import com.starcor.sccms.api.SccmsApiGetPublicImageTask.ISccmsApiGetPublicImageTaskListener;
import com.starcor.sccms.api.SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterGet3rdLoginUrlTask.ISccmsApiUserCenterGet3rdLoginUrlTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterGetWebChatLoginPicTask.ISccmsApiGetWebChatLoginPicTaskListenter;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.Graphics.XulDrawable;
import com.starcor.xul.XulPendingInputStream;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulWorker;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class AppInputStream
{
  private static final String TAG = "";
  private static AppInputStream appStream = null;
  private final int ORDER_PAGE_SIZE = 4;
  private ByteArrayInputStream infoSystem;
  private DocumentBuilder xmlBuilder;
  private Transformer xmlTransformer;

  private ByteArrayInputStream buildAdStream(AdInfos paramAdInfos)
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "ad");
      if ((paramAdInfos.getIamgeAdList() != null) && (paramAdInfos.getIamgeAdList().getImageUrls() != null))
      {
        paramAdInfos.getIamgeAdList().getImageUrls();
        if (paramAdInfos.getIamgeAdList().getImageUrls().size() > 0)
          writeNodeValue(localXmlSerializer, "url", (String)paramAdInfos.getIamgeAdList().getImageUrls().get(0));
      }
      localXmlSerializer.endTag(null, "ad");
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      String str = localStringWriter.toString();
      Logger.d("", "ad info:" + str);
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
      return localByteArrayInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private ByteArrayInputStream buildAgreement(Agreements paramAgreements, String paramString)
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "useragreement");
      Iterator localIterator = paramAgreements.agreements.iterator();
      while (localIterator.hasNext())
      {
        Agreements.AgreementInfo localAgreementInfo = (Agreements.AgreementInfo)localIterator.next();
        if (localAgreementInfo.action.equals(paramString))
        {
          writeNodeValue(localXmlSerializer, "title", localAgreementInfo.name);
          writeNodeValue(localXmlSerializer, "content", localAgreementInfo.info);
        }
      }
      localXmlSerializer.endTag(null, "useragreement");
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

  private InputStream buildGetPlaybillSelected(GetPlaybillSelectedListInfo paramGetPlaybillSelectedListInfo, String paramString1, String paramString2)
  {
    XmlSerializer localXmlSerializer;
    StringWriter localStringWriter;
    while (true)
    {
      try
      {
        localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        localStringWriter = new StringWriter();
        localXmlSerializer.setOutput(localStringWriter);
        localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer.startTag(null, "data");
        localXmlSerializer.startTag(null, "info");
        writeNodeValue(localXmlSerializer, "item_num", paramGetPlaybillSelectedListInfo.total_rows);
        localXmlSerializer.endTag(null, "info");
        Iterator localIterator1 = getDataResult(paramGetPlaybillSelectedListInfo).iterator();
        if (!localIterator1.hasNext())
          break;
        DayItems localDayItems = (DayItems)localIterator1.next();
        localXmlSerializer.startTag(null, "group");
        String str2 = localDayItems.day.substring(4);
        String str3 = str2.substring(0, 2) + "月" + str2.substring(2) + "日";
        if (localDayItems.isToday)
          str3 = "今日";
        writeNodeValue(localXmlSerializer, "name", str3);
        Iterator localIterator2 = localDayItems.items.iterator();
        if (!localIterator2.hasNext())
          break label488;
        GetPlaybillSelectedListInfo.Item localItem = (GetPlaybillSelectedListInfo.Item)localIterator2.next();
        localXmlSerializer.startTag(null, "item");
        writeNodeValue(localXmlSerializer, "name", localItem.name);
        if (AppFuncCfg.FUNCTION_TV_SELECT_SHOW_ICON)
        {
          str4 = localItem.img_h;
          writeNodeValue(localXmlSerializer, "image", str4);
          localXmlSerializer.startTag(null, "arg_list");
          writeArgsValue(localXmlSerializer, "begin_time", localItem.day + localItem.begin_time);
          writeArgsValue(localXmlSerializer, "category_id", paramString2);
          writeArgsValue(localXmlSerializer, "media_asset_id", paramString1);
          writeArgsValue(localXmlSerializer, "name", localItem.name);
          writeArgsValue(localXmlSerializer, "time_len", localItem.time_len);
          writeArgsValue(localXmlSerializer, "video_id", localItem.id);
          writeArgsValue(localXmlSerializer, "video_type", localItem.video_type);
          writeArgsValue(localXmlSerializer, "huawei_cid", localItem.huawei_cid);
          writeArgsValue(localXmlSerializer, "is_select_tv", "true");
          localXmlSerializer.endTag(null, "arg_list");
          localXmlSerializer.endTag(null, "item");
          continue;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      String str4 = "";
      continue;
      label488: localXmlSerializer.endTag(null, "group");
    }
    localXmlSerializer.endTag(null, "data");
    localXmlSerializer.endDocument();
    localXmlSerializer.flush();
    String str1 = localStringWriter.toString();
    Log.d("lx", "info:" + str1);
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str1.getBytes("UTF-8"));
    return localByteArrayInputStream;
  }

  // ERROR //
  private InputStream buildMediaAssetsInfo(MediaAssetsInfo paramMediaAssetsInfo)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 423	com/starcor/hunan/AppInputStream:xmlBuilder	Ljavax/xml/parsers/DocumentBuilder;
    //   4: ifnonnull +13 -> 17
    //   7: aload_0
    //   8: invokestatic 428	javax/xml/parsers/DocumentBuilderFactory:newInstance	()Ljavax/xml/parsers/DocumentBuilderFactory;
    //   11: invokevirtual 432	javax/xml/parsers/DocumentBuilderFactory:newDocumentBuilder	()Ljavax/xml/parsers/DocumentBuilder;
    //   14: putfield 423	com/starcor/hunan/AppInputStream:xmlBuilder	Ljavax/xml/parsers/DocumentBuilder;
    //   17: aload_0
    //   18: getfield 434	com/starcor/hunan/AppInputStream:xmlTransformer	Ljavax/xml/transform/Transformer;
    //   21: ifnonnull +13 -> 34
    //   24: aload_0
    //   25: invokestatic 439	javax/xml/transform/TransformerFactory:newInstance	()Ljavax/xml/transform/TransformerFactory;
    //   28: invokevirtual 443	javax/xml/transform/TransformerFactory:newTransformer	()Ljavax/xml/transform/Transformer;
    //   31: putfield 434	com/starcor/hunan/AppInputStream:xmlTransformer	Ljavax/xml/transform/Transformer;
    //   34: aload_0
    //   35: getfield 423	com/starcor/hunan/AppInputStream:xmlBuilder	Ljavax/xml/parsers/DocumentBuilder;
    //   38: invokevirtual 449	javax/xml/parsers/DocumentBuilder:newDocument	()Lorg/w3c/dom/Document;
    //   41: astore_2
    //   42: aload_2
    //   43: ldc_w 314
    //   46: invokeinterface 455 2 0
    //   51: astore_3
    //   52: aload_2
    //   53: aload_3
    //   54: invokeinterface 459 2 0
    //   59: pop
    //   60: aload_2
    //   61: ldc_w 461
    //   64: invokeinterface 455 2 0
    //   69: astore 5
    //   71: aload_3
    //   72: aload 5
    //   74: invokeinterface 464 2 0
    //   79: pop
    //   80: aload_1
    //   81: getfield 470	com/starcor/core/domain/MediaAssetsInfo:cList	Ljava/util/ArrayList;
    //   84: invokevirtual 471	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   87: astore 7
    //   89: aload 7
    //   91: invokeinterface 289 1 0
    //   96: ifeq +221 -> 317
    //   99: aload 7
    //   101: invokeinterface 293 1 0
    //   106: checkcast 473	com/starcor/core/domain/CategoryItem
    //   109: astore 13
    //   111: aload_2
    //   112: ldc_w 358
    //   115: invokeinterface 455 2 0
    //   120: astore 14
    //   122: aload 5
    //   124: aload 14
    //   126: invokeinterface 464 2 0
    //   131: pop
    //   132: aload_2
    //   133: ldc_w 351
    //   136: invokeinterface 455 2 0
    //   141: astore 16
    //   143: aload 16
    //   145: aload_2
    //   146: aload 13
    //   148: getfield 474	com/starcor/core/domain/CategoryItem:name	Ljava/lang/String;
    //   151: invokeinterface 478 2 0
    //   156: invokeinterface 464 2 0
    //   161: pop
    //   162: aload 14
    //   164: aload 16
    //   166: invokeinterface 464 2 0
    //   171: pop
    //   172: aload_2
    //   173: ldc_w 479
    //   176: invokeinterface 455 2 0
    //   181: astore 19
    //   183: aload 19
    //   185: aload_2
    //   186: ldc_w 481
    //   189: invokeinterface 478 2 0
    //   194: invokeinterface 464 2 0
    //   199: pop
    //   200: aload 14
    //   202: aload 19
    //   204: invokeinterface 464 2 0
    //   209: pop
    //   210: aload_2
    //   211: ldc_w 483
    //   214: invokeinterface 455 2 0
    //   219: astore 22
    //   221: new 485	org/json/JSONObject
    //   224: dup
    //   225: invokespecial 486	org/json/JSONObject:<init>	()V
    //   228: astore 23
    //   230: aload 23
    //   232: ldc_w 488
    //   235: aload_1
    //   236: getfield 491	com/starcor/core/domain/MediaAssetsInfo:package_id	Ljava/lang/String;
    //   239: invokevirtual 495	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   242: pop
    //   243: aload 23
    //   245: ldc_w 497
    //   248: aload 13
    //   250: getfield 498	com/starcor/core/domain/CategoryItem:id	Ljava/lang/String;
    //   253: invokevirtual 495	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   256: pop
    //   257: aload 22
    //   259: aload_2
    //   260: aload 23
    //   262: invokevirtual 499	org/json/JSONObject:toString	()Ljava/lang/String;
    //   265: invokeinterface 478 2 0
    //   270: invokeinterface 464 2 0
    //   275: pop
    //   276: aload 14
    //   278: aload 22
    //   280: invokeinterface 464 2 0
    //   285: pop
    //   286: goto -197 -> 89
    //   289: astore 30
    //   291: aload 30
    //   293: invokevirtual 500	javax/xml/parsers/ParserConfigurationException:printStackTrace	()V
    //   296: aconst_null
    //   297: areturn
    //   298: astore 29
    //   300: aload 29
    //   302: invokevirtual 501	javax/xml/transform/TransformerConfigurationException:printStackTrace	()V
    //   305: aconst_null
    //   306: areturn
    //   307: astore 24
    //   309: aload 24
    //   311: invokevirtual 502	org/json/JSONException:printStackTrace	()V
    //   314: goto -57 -> 257
    //   317: new 504	java/io/ByteArrayOutputStream
    //   320: dup
    //   321: invokespecial 505	java/io/ByteArrayOutputStream:<init>	()V
    //   324: astore 8
    //   326: new 507	javax/xml/transform/dom/DOMSource
    //   329: dup
    //   330: aload_2
    //   331: invokespecial 510	javax/xml/transform/dom/DOMSource:<init>	(Lorg/w3c/dom/Node;)V
    //   334: astore 9
    //   336: new 512	javax/xml/transform/stream/StreamResult
    //   339: dup
    //   340: aload 8
    //   342: invokespecial 515	javax/xml/transform/stream/StreamResult:<init>	(Ljava/io/OutputStream;)V
    //   345: astore 10
    //   347: aload_0
    //   348: getfield 434	com/starcor/hunan/AppInputStream:xmlTransformer	Ljavax/xml/transform/Transformer;
    //   351: aload 9
    //   353: aload 10
    //   355: invokevirtual 521	javax/xml/transform/Transformer:transform	(Ljavax/xml/transform/Source;Ljavax/xml/transform/Result;)V
    //   358: new 259	java/io/ByteArrayInputStream
    //   361: dup
    //   362: aload 8
    //   364: invokevirtual 525	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   367: invokespecial 266	java/io/ByteArrayInputStream:<init>	([B)V
    //   370: astore 12
    //   372: aload 12
    //   374: areturn
    //   375: astore 11
    //   377: aload 11
    //   379: invokevirtual 526	javax/xml/transform/TransformerException:printStackTrace	()V
    //   382: aconst_null
    //   383: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   7	17	289	javax/xml/parsers/ParserConfigurationException
    //   24	34	298	javax/xml/transform/TransformerConfigurationException
    //   230	257	307	org/json/JSONException
    //   317	372	375	javax/xml/transform/TransformerException
  }

  private InputStream buildMovieCouponCount(AAAMovieCouponCount paramAAAMovieCouponCount)
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "movie_coupon_count");
      if (paramAAAMovieCouponCount != null)
      {
        writeNodeValue(localXmlSerializer, "common_count", paramAAAMovieCouponCount.common_count + "");
        writeNodeValue(localXmlSerializer, "special_count", paramAAAMovieCouponCount.special_count + "");
        writeNodeValue(localXmlSerializer, "all_count", paramAAAMovieCouponCount.special_count + paramAAAMovieCouponCount.common_count + "");
      }
      localXmlSerializer.endTag(null, "movie_coupon_count");
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      String str = localStringWriter.toString();
      Logger.d("movie_coupon_count info:" + str);
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
      return localByteArrayInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private InputStream buildMovieCouponHistory(AAAMovieCouponInfo paramAAAMovieCouponInfo)
  {
    XmlSerializer localXmlSerializer;
    StringWriter localStringWriter;
    try
    {
      localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "movie_coupon_history");
      if (paramAAAMovieCouponInfo != null)
      {
        writeNodeValue(localXmlSerializer, "all_count", paramAAAMovieCouponInfo.all_count + "");
        writeNodeValue(localXmlSerializer, "all_page", String.valueOf((-1 + (5 + paramAAAMovieCouponInfo.all_count)) / 5));
      }
      if ((paramAAAMovieCouponInfo != null) && (paramAAAMovieCouponInfo.coupon_list.size() > 0))
      {
        Iterator localIterator = paramAAAMovieCouponInfo.coupon_list.iterator();
        while (localIterator.hasNext())
        {
          AAACouponInfoItem localAAACouponInfoItem = (AAACouponInfoItem)localIterator.next();
          localXmlSerializer.startTag(null, "item");
          writeNodeValue(localXmlSerializer, "coupon_id", localAAACouponInfoItem.coupon_id + "");
          writeNodeValue(localXmlSerializer, "asset_name", localAAACouponInfoItem.asset_name + "");
          if ((!TextUtils.isEmpty(localAAACouponInfoItem.create_time)) && (localAAACouponInfoItem.create_time.contains("-")))
            localAAACouponInfoItem.create_time = localAAACouponInfoItem.create_time.replace('-', '.');
          if ((!TextUtils.isEmpty(localAAACouponInfoItem.expire_time)) && (localAAACouponInfoItem.expire_time.contains("-")))
            localAAACouponInfoItem.expire_time = localAAACouponInfoItem.expire_time.replace('-', '.');
          writeNodeValue(localXmlSerializer, "create_time", localAAACouponInfoItem.create_time + "");
          writeNodeValue(localXmlSerializer, "expire_time", localAAACouponInfoItem.expire_time + "");
          writeNodeValue(localXmlSerializer, "from", localAAACouponInfoItem.from + "");
          writeNodeValue(localXmlSerializer, "type", localAAACouponInfoItem.type + "");
          writeNodeValue(localXmlSerializer, "status", localAAACouponInfoItem.status + "");
          localXmlSerializer.endTag(null, "item");
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    localXmlSerializer.endTag(null, "movie_coupon_history");
    localXmlSerializer.endDocument();
    localXmlSerializer.flush();
    String str = localStringWriter.toString();
    Logger.d("movie_coupon_history list:" + str);
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
    return localByteArrayInputStream;
  }

  private InputStream buildMovieCouponInfo(AAAMovieCouponInfo paramAAAMovieCouponInfo)
  {
    XmlSerializer localXmlSerializer;
    StringWriter localStringWriter;
    int i;
    try
    {
      localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "movie_coupon_info");
      if (paramAAAMovieCouponInfo != null)
      {
        writeNodeValue(localXmlSerializer, "common_count", paramAAAMovieCouponInfo.common_count + "");
        writeNodeValue(localXmlSerializer, "special_count", paramAAAMovieCouponInfo.special_count + "");
        writeNodeValue(localXmlSerializer, "all_count", paramAAAMovieCouponInfo.special_count + paramAAAMovieCouponInfo.common_count + "");
      }
      i = 0;
      if (paramAAAMovieCouponInfo.coupon_list.size() <= 0)
        break label496;
      Iterator localIterator = paramAAAMovieCouponInfo.coupon_list.iterator();
      while (localIterator.hasNext())
      {
        AAACouponInfoItem localAAACouponInfoItem = (AAACouponInfoItem)localIterator.next();
        localXmlSerializer.startTag(null, "item");
        writeNodeValue(localXmlSerializer, "coupon_id", localAAACouponInfoItem.coupon_id + "");
        writeNodeValue(localXmlSerializer, "asset_name", localAAACouponInfoItem.asset_name + "");
        writeNodeValue(localXmlSerializer, "create_time", localAAACouponInfoItem.create_time + "");
        writeNodeValue(localXmlSerializer, "expire_time", localAAACouponInfoItem.expire_time + "");
        writeNodeValue(localXmlSerializer, "from", localAAACouponInfoItem.from + "");
        if ((localAAACouponInfoItem.from != null) && ("2".equals(localAAACouponInfoItem.from)))
          i++;
        writeNodeValue(localXmlSerializer, "type", localAAACouponInfoItem.type + "");
        writeNodeValue(localXmlSerializer, "stauts", localAAACouponInfoItem.status + "");
        localXmlSerializer.endTag(null, "item");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    writeNodeValue(localXmlSerializer, "presenter_movie_coupon", i + "");
    label496: localXmlSerializer.endTag(null, "movie_coupon_info");
    localXmlSerializer.endDocument();
    localXmlSerializer.flush();
    String str = localStringWriter.toString();
    Logger.d("movie_coupon_info list:" + str);
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
    return localByteArrayInputStream;
  }

  private ByteArrayInputStream buildOrderInfo(AAAOrderInfo paramAAAOrderInfo, boolean paramBoolean)
  {
    while (true)
    {
      XmlSerializer localXmlSerializer;
      try
      {
        localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        StringWriter localStringWriter = new StringWriter();
        localXmlSerializer.setOutput(localStringWriter);
        localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer.startTag(null, "orderinfo");
        writeAccountInfo(localXmlSerializer);
        writeNodeValue(localXmlSerializer, "orderId", paramAAAOrderInfo.orderId + "");
        writeNodeValue(localXmlSerializer, "orderType", paramAAAOrderInfo.orderType);
        writeNodeValue(localXmlSerializer, "orderPrice", paramAAAOrderInfo.orderPrice + "元");
        if (!paramBoolean)
        {
          writeNodeValue(localXmlSerializer, "orderMsg", paramAAAOrderInfo.orderMsg);
          Logger.d("", "result.orderMsg:" + paramAAAOrderInfo.orderMsg);
          if (paramAAAOrderInfo.orderType.equals("qrcode"))
          {
            Object[] arrayOfObject1 = new Object[1];
            arrayOfObject1[0] = paramAAAOrderInfo.orderId;
            String str3 = String.format("file:///.app/orderinfo/%s/QR", arrayOfObject1);
            writeNodeValue(localXmlSerializer, "url", str3);
            XulWorker.addDrawableToCache(str3, XulDrawable.fromBitmap(CommonUiTools.createImage(paramAAAOrderInfo.orderMsg, 500, 500), str3, str3));
            localXmlSerializer.endTag(null, "orderinfo");
            localXmlSerializer.endDocument();
            localXmlSerializer.flush();
            String str2 = localStringWriter.toString();
            Logger.d("orderinfo info:" + str2);
            return new ByteArrayInputStream(str2.getBytes("UTF-8"));
          }
          String str1 = "0";
          if ((paramAAAOrderInfo.orderMsg != null) && (paramAAAOrderInfo.orderMsg.length() > 2))
            str1 = paramAAAOrderInfo.orderMsg.substring(1, -1 + paramAAAOrderInfo.orderMsg.length()).split(",")[0];
          writeNodeValue(localXmlSerializer, "pay", str1 + "芒果币");
          continue;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      writeNodeValue(localXmlSerializer, "orderMsg", paramAAAOrderInfo.orderMsg);
      Logger.d("", "result.orderMsg:" + paramAAAOrderInfo.orderMsg);
      String str4 = paramAAAOrderInfo.orderMsg;
      if (str4 != null)
      {
        JSONObject localJSONObject = new JSONObject(str4);
        if (localJSONObject.has("phone"))
        {
          String str5 = localJSONObject.getString("phone");
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = paramAAAOrderInfo.orderId;
          String str6 = String.format("file:///.app/orderinfo/%s/QR", arrayOfObject2);
          writeNodeValue(localXmlSerializer, "phone", str6);
          XulWorker.addDrawableToCache(str6, XulDrawable.fromBitmap(CommonUiTools.createImage(str5, 500, 500), str6, str6));
        }
        if (localJSONObject.has("pc"))
          writeNodeValue(localXmlSerializer, "pc", localJSONObject.getString("pc"));
      }
    }
  }

  private ByteArrayInputStream buildOrderState(AAAOrderState paramAAAOrderState)
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "orderstate");
      writeNodeValue(localXmlSerializer, "status", paramAAAOrderState.orderStatus);
      if ((!TextUtils.isEmpty(paramAAAOrderState.begin_date)) && (paramAAAOrderState.begin_date.contains("-")))
        paramAAAOrderState.begin_date = paramAAAOrderState.begin_date.replace('-', '.');
      if ((!TextUtils.isEmpty(paramAAAOrderState.end_date)) && (paramAAAOrderState.end_date.contains("-")))
        paramAAAOrderState.end_date = paramAAAOrderState.end_date.replace('-', '.');
      writeNodeValue(localXmlSerializer, "begin_date", paramAAAOrderState.begin_date);
      writeNodeValue(localXmlSerializer, "end_date", paramAAAOrderState.end_date);
      writeNodeValue(localXmlSerializer, "begin_end_days", paramAAAOrderState.begin_date + " - " + paramAAAOrderState.end_date);
      if ((TextUtils.isEmpty(paramAAAOrderState.begin_date)) || (paramAAAOrderState.begin_date.equals("null")))
        writeNodeValue(localXmlSerializer, "begin_end_days", "请在" + paramAAAOrderState.coupon_end_date + "前使用");
      writeNodeValue(localXmlSerializer, "coupon", paramAAAOrderState.coupon + "张");
      if ((!TextUtils.isEmpty(paramAAAOrderState.coupon_end_date)) && (!paramAAAOrderState.coupon_end_date.equals("null")))
        writeNodeValue(localXmlSerializer, "coupon_end_date", "(请在" + paramAAAOrderState.coupon_end_date + "前使用)");
      while (true)
      {
        writeNodeValue(localXmlSerializer, "service_days", paramAAAOrderState.service_days + "天");
        localXmlSerializer.endTag(null, "orderstate");
        localXmlSerializer.endDocument();
        localXmlSerializer.flush();
        String str = localStringWriter.toString();
        Logger.d("orderstate info:" + str);
        return new ByteArrayInputStream(str.getBytes("UTF-8"));
        writeNodeValue(localXmlSerializer, "coupon_end_date", "");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private InputStream buildPayAuthorizeStatus(PayAuthorizeStatus paramPayAuthorizeStatus)
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "alipay_authorize");
      if ("yes".equals(paramPayAuthorizeStatus.type))
        writeNodeValue(localXmlSerializer, "status", "1");
      while (true)
      {
        writeNodeValue(localXmlSerializer, "url", paramPayAuthorizeStatus.qrcode);
        localXmlSerializer.endTag(null, "alipay_authorize");
        localXmlSerializer.endDocument();
        localXmlSerializer.flush();
        Logger.d("info/alipay_status:" + localStringWriter.toString());
        return new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
        writeNodeValue(localXmlSerializer, "status", "0");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  // ERROR //
  private ByteArrayInputStream buildPayChannelListInfo(AAAPayChannelList paramAAAPayChannelList)
  {
    // Byte code:
    //   0: invokestatic 167	org/xmlpull/v1/XmlPullParserFactory:newInstance	()Lorg/xmlpull/v1/XmlPullParserFactory;
    //   3: invokevirtual 171	org/xmlpull/v1/XmlPullParserFactory:newSerializer	()Lorg/xmlpull/v1/XmlSerializer;
    //   6: astore_3
    //   7: new 173	java/io/StringWriter
    //   10: dup
    //   11: invokespecial 174	java/io/StringWriter:<init>	()V
    //   14: astore 4
    //   16: aload_3
    //   17: aload 4
    //   19: invokeinterface 180 2 0
    //   24: aload_3
    //   25: ldc 182
    //   27: iconst_1
    //   28: invokestatic 188	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   31: invokeinterface 192 3 0
    //   36: aload_3
    //   37: aconst_null
    //   38: ldc_w 768
    //   41: invokeinterface 198 3 0
    //   46: pop
    //   47: aload_0
    //   48: aload_3
    //   49: invokespecial 627	com/starcor/hunan/AppInputStream:writeAccountInfo	(Lorg/xmlpull/v1/XmlSerializer;)V
    //   52: aload_1
    //   53: getfield 773	com/starcor/core/domain/AAAPayChannelList:channelList	Ljava/util/ArrayList;
    //   56: invokevirtual 471	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   59: astore 6
    //   61: aload 6
    //   63: invokeinterface 289 1 0
    //   68: ifeq +699 -> 767
    //   71: aload 6
    //   73: invokeinterface 293 1 0
    //   78: checkcast 775	com/starcor/core/domain/AAAPayChannelItem
    //   81: astore 10
    //   83: ldc 8
    //   85: astore 11
    //   87: aload 10
    //   89: getfield 778	com/starcor/core/domain/AAAPayChannelItem:discount	Ljava/lang/String;
    //   92: invokestatic 581	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   95: istore 12
    //   97: iload 12
    //   99: ifne +60 -> 159
    //   102: ldc_w 779
    //   105: aload 10
    //   107: getfield 778	com/starcor/core/domain/AAAPayChannelItem:discount	Ljava/lang/String;
    //   110: invokestatic 784	java/lang/Float:valueOf	(Ljava/lang/String;)Ljava/lang/Float;
    //   113: invokevirtual 788	java/lang/Float:floatValue	()F
    //   116: fmul
    //   117: f2i
    //   118: istore 26
    //   120: iload 26
    //   122: bipush 10
    //   124: if_icmpge +35 -> 159
    //   127: iload 26
    //   129: ifle +30 -> 159
    //   132: new 243	java/lang/StringBuilder
    //   135: dup
    //   136: invokespecial 244	java/lang/StringBuilder:<init>	()V
    //   139: iload 26
    //   141: invokevirtual 537	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   144: ldc_w 790
    //   147: invokevirtual 250	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   150: invokevirtual 251	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   153: astore 27
    //   155: aload 27
    //   157: astore 11
    //   159: getstatic 795	com/starcor/core/define/PayChannelCode:ALIPAY	Ljava/lang/String;
    //   162: aload 10
    //   164: getfield 798	com/starcor/core/domain/AAAPayChannelItem:code	Ljava/lang/String;
    //   167: invokevirtual 801	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   170: ifeq +87 -> 257
    //   173: aload_3
    //   174: aconst_null
    //   175: ldc_w 803
    //   178: invokeinterface 198 3 0
    //   183: pop
    //   184: aload_0
    //   185: aload_3
    //   186: ldc_w 804
    //   189: aload 10
    //   191: getfield 798	com/starcor/core/domain/AAAPayChannelItem:code	Ljava/lang/String;
    //   194: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   197: aload_0
    //   198: aload_3
    //   199: ldc_w 806
    //   202: aload 10
    //   204: getfield 808	com/starcor/core/domain/AAAPayChannelItem:location	Ljava/lang/String;
    //   207: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   210: aload_0
    //   211: aload_3
    //   212: ldc_w 351
    //   215: aload 10
    //   217: getfield 809	com/starcor/core/domain/AAAPayChannelItem:name	Ljava/lang/String;
    //   220: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   223: aload_0
    //   224: aload_3
    //   225: ldc_w 811
    //   228: aload 10
    //   230: getfield 813	com/starcor/core/domain/AAAPayChannelItem:description	Ljava/lang/String;
    //   233: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   236: aload_0
    //   237: aload_3
    //   238: ldc_w 814
    //   241: aload 11
    //   243: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   246: aload_3
    //   247: aconst_null
    //   248: ldc_w 803
    //   251: invokeinterface 231 3 0
    //   256: pop
    //   257: getstatic 817	com/starcor/core/define/PayChannelCode:ALIPAY_AUTH	Ljava/lang/String;
    //   260: aload 10
    //   262: getfield 798	com/starcor/core/domain/AAAPayChannelItem:code	Ljava/lang/String;
    //   265: invokevirtual 801	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   268: ifeq +87 -> 355
    //   271: aload_3
    //   272: aconst_null
    //   273: ldc_w 819
    //   276: invokeinterface 198 3 0
    //   281: pop
    //   282: aload_0
    //   283: aload_3
    //   284: ldc_w 804
    //   287: aload 10
    //   289: getfield 798	com/starcor/core/domain/AAAPayChannelItem:code	Ljava/lang/String;
    //   292: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   295: aload_0
    //   296: aload_3
    //   297: ldc_w 806
    //   300: aload 10
    //   302: getfield 808	com/starcor/core/domain/AAAPayChannelItem:location	Ljava/lang/String;
    //   305: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   308: aload_0
    //   309: aload_3
    //   310: ldc_w 351
    //   313: aload 10
    //   315: getfield 809	com/starcor/core/domain/AAAPayChannelItem:name	Ljava/lang/String;
    //   318: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   321: aload_0
    //   322: aload_3
    //   323: ldc_w 811
    //   326: aload 10
    //   328: getfield 813	com/starcor/core/domain/AAAPayChannelItem:description	Ljava/lang/String;
    //   331: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   334: aload_0
    //   335: aload_3
    //   336: ldc_w 814
    //   339: aload 11
    //   341: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   344: aload_3
    //   345: aconst_null
    //   346: ldc_w 819
    //   349: invokeinterface 231 3 0
    //   354: pop
    //   355: getstatic 822	com/starcor/core/define/PayChannelCode:MGB	Ljava/lang/String;
    //   358: aload 10
    //   360: getfield 798	com/starcor/core/domain/AAAPayChannelItem:code	Ljava/lang/String;
    //   363: invokevirtual 801	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   366: ifeq +87 -> 453
    //   369: aload_3
    //   370: aconst_null
    //   371: ldc_w 824
    //   374: invokeinterface 198 3 0
    //   379: pop
    //   380: aload_0
    //   381: aload_3
    //   382: ldc_w 804
    //   385: aload 10
    //   387: getfield 798	com/starcor/core/domain/AAAPayChannelItem:code	Ljava/lang/String;
    //   390: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   393: aload_0
    //   394: aload_3
    //   395: ldc_w 806
    //   398: aload 10
    //   400: getfield 808	com/starcor/core/domain/AAAPayChannelItem:location	Ljava/lang/String;
    //   403: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   406: aload_0
    //   407: aload_3
    //   408: ldc_w 351
    //   411: aload 10
    //   413: getfield 809	com/starcor/core/domain/AAAPayChannelItem:name	Ljava/lang/String;
    //   416: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   419: aload_0
    //   420: aload_3
    //   421: ldc_w 811
    //   424: aload 10
    //   426: getfield 813	com/starcor/core/domain/AAAPayChannelItem:description	Ljava/lang/String;
    //   429: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   432: aload_0
    //   433: aload_3
    //   434: ldc_w 814
    //   437: aload 11
    //   439: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   442: aload_3
    //   443: aconst_null
    //   444: ldc_w 824
    //   447: invokeinterface 231 3 0
    //   452: pop
    //   453: getstatic 827	com/starcor/core/define/PayChannelCode:WECHAT	Ljava/lang/String;
    //   456: aload 10
    //   458: getfield 798	com/starcor/core/domain/AAAPayChannelItem:code	Ljava/lang/String;
    //   461: invokevirtual 801	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   464: ifeq +87 -> 551
    //   467: aload_3
    //   468: aconst_null
    //   469: ldc_w 829
    //   472: invokeinterface 198 3 0
    //   477: pop
    //   478: aload_0
    //   479: aload_3
    //   480: ldc_w 804
    //   483: aload 10
    //   485: getfield 798	com/starcor/core/domain/AAAPayChannelItem:code	Ljava/lang/String;
    //   488: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   491: aload_0
    //   492: aload_3
    //   493: ldc_w 806
    //   496: aload 10
    //   498: getfield 808	com/starcor/core/domain/AAAPayChannelItem:location	Ljava/lang/String;
    //   501: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   504: aload_0
    //   505: aload_3
    //   506: ldc_w 351
    //   509: aload 10
    //   511: getfield 809	com/starcor/core/domain/AAAPayChannelItem:name	Ljava/lang/String;
    //   514: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   517: aload_0
    //   518: aload_3
    //   519: ldc_w 811
    //   522: aload 10
    //   524: getfield 813	com/starcor/core/domain/AAAPayChannelItem:description	Ljava/lang/String;
    //   527: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   530: aload_0
    //   531: aload_3
    //   532: ldc_w 814
    //   535: aload 11
    //   537: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   540: aload_3
    //   541: aconst_null
    //   542: ldc_w 829
    //   545: invokeinterface 231 3 0
    //   550: pop
    //   551: getstatic 832	com/starcor/core/define/PayChannelCode:BROWSER	Ljava/lang/String;
    //   554: aload 10
    //   556: getfield 798	com/starcor/core/domain/AAAPayChannelItem:code	Ljava/lang/String;
    //   559: invokevirtual 801	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   562: ifeq +87 -> 649
    //   565: aload_3
    //   566: aconst_null
    //   567: ldc_w 834
    //   570: invokeinterface 198 3 0
    //   575: pop
    //   576: aload_0
    //   577: aload_3
    //   578: ldc_w 804
    //   581: aload 10
    //   583: getfield 798	com/starcor/core/domain/AAAPayChannelItem:code	Ljava/lang/String;
    //   586: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   589: aload_0
    //   590: aload_3
    //   591: ldc_w 806
    //   594: aload 10
    //   596: getfield 808	com/starcor/core/domain/AAAPayChannelItem:location	Ljava/lang/String;
    //   599: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   602: aload_0
    //   603: aload_3
    //   604: ldc_w 351
    //   607: aload 10
    //   609: getfield 809	com/starcor/core/domain/AAAPayChannelItem:name	Ljava/lang/String;
    //   612: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   615: aload_0
    //   616: aload_3
    //   617: ldc_w 811
    //   620: aload 10
    //   622: getfield 813	com/starcor/core/domain/AAAPayChannelItem:description	Ljava/lang/String;
    //   625: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   628: aload_0
    //   629: aload_3
    //   630: ldc_w 814
    //   633: aload 11
    //   635: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   638: aload_3
    //   639: aconst_null
    //   640: ldc_w 834
    //   643: invokeinterface 231 3 0
    //   648: pop
    //   649: getstatic 837	com/starcor/core/define/PayChannelCode:VIRTURAL	Ljava/lang/String;
    //   652: aload 10
    //   654: getfield 798	com/starcor/core/domain/AAAPayChannelItem:code	Ljava/lang/String;
    //   657: invokevirtual 801	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   660: ifeq -599 -> 61
    //   663: aload_3
    //   664: aconst_null
    //   665: ldc_w 839
    //   668: invokeinterface 198 3 0
    //   673: pop
    //   674: aload_0
    //   675: aload_3
    //   676: ldc_w 804
    //   679: aload 10
    //   681: getfield 798	com/starcor/core/domain/AAAPayChannelItem:code	Ljava/lang/String;
    //   684: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   687: aload_0
    //   688: aload_3
    //   689: ldc_w 806
    //   692: aload 10
    //   694: getfield 808	com/starcor/core/domain/AAAPayChannelItem:location	Ljava/lang/String;
    //   697: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   700: aload_0
    //   701: aload_3
    //   702: ldc_w 351
    //   705: aload 10
    //   707: getfield 809	com/starcor/core/domain/AAAPayChannelItem:name	Ljava/lang/String;
    //   710: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   713: aload_0
    //   714: aload_3
    //   715: ldc_w 811
    //   718: aload 10
    //   720: getfield 813	com/starcor/core/domain/AAAPayChannelItem:description	Ljava/lang/String;
    //   723: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   726: aload_0
    //   727: aload_3
    //   728: ldc_w 814
    //   731: aload 11
    //   733: invokespecial 228	com/starcor/hunan/AppInputStream:writeNodeValue	(Lorg/xmlpull/v1/XmlSerializer;Ljava/lang/String;Ljava/lang/String;)V
    //   736: aload_3
    //   737: aconst_null
    //   738: ldc_w 839
    //   741: invokeinterface 231 3 0
    //   746: pop
    //   747: goto -686 -> 61
    //   750: astore_2
    //   751: aload_2
    //   752: invokevirtual 269	java/lang/Exception:printStackTrace	()V
    //   755: aconst_null
    //   756: areturn
    //   757: astore 25
    //   759: aload 25
    //   761: invokevirtual 269	java/lang/Exception:printStackTrace	()V
    //   764: goto -605 -> 159
    //   767: aload_3
    //   768: aconst_null
    //   769: ldc_w 768
    //   772: invokeinterface 231 3 0
    //   777: pop
    //   778: aload_3
    //   779: invokeinterface 234 1 0
    //   784: aload_3
    //   785: invokeinterface 237 1 0
    //   790: aload 4
    //   792: invokevirtual 241	java/io/StringWriter:toString	()Ljava/lang/String;
    //   795: astore 8
    //   797: ldc 8
    //   799: new 243	java/lang/StringBuilder
    //   802: dup
    //   803: invokespecial 244	java/lang/StringBuilder:<init>	()V
    //   806: ldc_w 841
    //   809: invokevirtual 250	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   812: aload 8
    //   814: invokevirtual 250	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   817: invokevirtual 251	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   820: invokestatic 257	com/starcor/core/utils/Logger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   823: new 259	java/io/ByteArrayInputStream
    //   826: dup
    //   827: aload 8
    //   829: ldc 182
    //   831: invokevirtual 263	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   834: invokespecial 266	java/io/ByteArrayInputStream:<init>	([B)V
    //   837: astore 9
    //   839: aload 9
    //   841: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   0	61	750	java/lang/Exception
    //   61	83	750	java/lang/Exception
    //   87	97	750	java/lang/Exception
    //   159	257	750	java/lang/Exception
    //   257	355	750	java/lang/Exception
    //   355	453	750	java/lang/Exception
    //   453	551	750	java/lang/Exception
    //   551	649	750	java/lang/Exception
    //   649	747	750	java/lang/Exception
    //   759	764	750	java/lang/Exception
    //   767	839	750	java/lang/Exception
    //   102	120	757	java/lang/Exception
    //   132	155	757	java/lang/Exception
  }

  private ByteArrayInputStream buildPaymentInfo(AAAOrderInfo paramAAAOrderInfo, boolean paramBoolean)
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "payment");
      writeAccountInfo(localXmlSerializer);
      writeNodeValue(localXmlSerializer, "orderId", paramAAAOrderInfo.orderId);
      writeNodeValue(localXmlSerializer, "orderType", paramAAAOrderInfo.orderType);
      if (!paramBoolean)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = paramAAAOrderInfo.orderId;
        String str1 = String.format("file:///.app/payment/%s/QR", arrayOfObject1);
        writeNodeValue(localXmlSerializer, "url", str1);
        Logger.d("", "result.orderMsg:" + paramAAAOrderInfo.orderMsg);
        XulWorker.addDrawableToCache(str1, XulDrawable.fromBitmap(CommonUiTools.createImage(paramAAAOrderInfo.orderMsg, 500, 500), str1, str1));
      }
      while (true)
      {
        localXmlSerializer.endTag(null, "payment");
        localXmlSerializer.endDocument();
        localXmlSerializer.flush();
        String str2 = localStringWriter.toString();
        Logger.d("", "payment info:" + str2);
        return new ByteArrayInputStream(str2.getBytes("UTF-8"));
        String str3 = paramAAAOrderInfo.orderMsg;
        if (str3 != null)
        {
          JSONObject localJSONObject = new JSONObject(str3);
          if (localJSONObject.has("phone"))
          {
            String str4 = localJSONObject.getString("phone");
            Object[] arrayOfObject2 = new Object[1];
            arrayOfObject2[0] = paramAAAOrderInfo.orderId;
            String str5 = String.format("file:///.app/orderinfo/%s/QR", arrayOfObject2);
            writeNodeValue(localXmlSerializer, "phone", str5);
            XulWorker.addDrawableToCache(str5, XulDrawable.fromBitmap(CommonUiTools.createImage(str4, 500, 500), str5, str5));
          }
          if (localJSONObject.has("pc"))
            writeNodeValue(localXmlSerializer, "pc", localJSONObject.getString("pc"));
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private ByteArrayInputStream buildPriceListInfo(AAAPriceList paramAAAPriceList)
  {
    XmlSerializer localXmlSerializer;
    StringWriter localStringWriter;
    try
    {
      localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "pricelist");
      writeAccountInfo(localXmlSerializer);
      Iterator localIterator = paramAAAPriceList.priceList.iterator();
      while (localIterator.hasNext())
      {
        Integer localInteger = (Integer)localIterator.next();
        localXmlSerializer.startTag(null, "price");
        writeNodeValue(localXmlSerializer, "value", localInteger + "");
        localXmlSerializer.endTag(null, "price");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    localXmlSerializer.endTag(null, "pricelist");
    localXmlSerializer.endDocument();
    localXmlSerializer.flush();
    String str = localStringWriter.toString();
    Logger.d("", "priceList info:" + str);
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
    return localByteArrayInputStream;
  }

  private ByteArrayInputStream buildPriceListInfo(ArrayList<String> paramArrayList)
  {
    XmlSerializer localXmlSerializer;
    StringWriter localStringWriter;
    try
    {
      localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "pricelist");
      writeAccountInfo(localXmlSerializer);
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        localXmlSerializer.startTag(null, "price");
        writeNodeValue(localXmlSerializer, "value", str2 + "");
        localXmlSerializer.endTag(null, "price");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return null;
    }
    localXmlSerializer.endTag(null, "pricelist");
    localXmlSerializer.endDocument();
    localXmlSerializer.flush();
    String str1 = localStringWriter.toString();
    Logger.d("", "priceList info:" + str1);
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str1.getBytes("UTF-8"));
    return localByteArrayInputStream;
  }

  private ByteArrayInputStream buildProductList(ArrayList<AAAProductItem> paramArrayList, int paramInt)
  {
    String str1 = "all";
    while (true)
    {
      XmlSerializer localXmlSerializer;
      StringWriter localStringWriter;
      AAAProductItem localAAAProductItem;
      float f;
      List localList;
      int i;
      try
      {
        localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        localStringWriter = new StringWriter();
        localXmlSerializer.setOutput(localStringWriter);
        localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer.startTag(null, "productlist");
        writeAccountInfo(localXmlSerializer);
        if (GlobalLogic.getInstance().getUserInfo() != null)
          writeNodeValue(localXmlSerializer, "balance", GlobalLogic.getInstance().getUserInfo().balance);
        writeNodeValue(localXmlSerializer, "media_asset_type", paramInt + "");
        Iterator localIterator = paramArrayList.iterator();
        if (!localIterator.hasNext())
          break label1010;
        localAAAProductItem = (AAAProductItem)localIterator.next();
        localXmlSerializer.startTag(null, "product");
        if (localAAAProductItem != null)
        {
          writeNodeValue(localXmlSerializer, "button_name", localAAAProductItem.button_name);
          writeNodeValue(localXmlSerializer, "product_type", localAAAProductItem.product_type + "");
          writeNodeValue(localXmlSerializer, "desc", localAAAProductItem.desc + "");
          if (TextUtils.isEmpty(localAAAProductItem.name))
          {
            writeNodeValue(localXmlSerializer, "name", localAAAProductItem.button_name);
            f = localAAAProductItem.price;
            if (!TextUtils.isEmpty(localAAAProductItem.time))
            {
              localAAAProductItem.time += "天";
              writeNodeValue(localXmlSerializer, "time", localAAAProductItem.time);
            }
            if (f - (int)f != 0.0F)
              break label764;
            writeNodeValue(localXmlSerializer, "price", (int)f + "元");
            writeNodeValue(localXmlSerializer, "subtitle", localAAAProductItem.time + "/" + (int)localAAAProductItem.price + "元");
            writeNodeValue(localXmlSerializer, "productPrice", (int)f + "");
            if (localAAAProductItem.price_show - (int)localAAAProductItem.price_show != 0.0F)
              break label873;
            writeNodeValue(localXmlSerializer, "price_show", (int)localAAAProductItem.price_show + "元");
            writeNodeValue(localXmlSerializer, "productId", localAAAProductItem.productId + "");
            if (localAAAProductItem.product_type != 0)
              break label909;
            writeNodeValue(localXmlSerializer, "vipType", "1");
            localList = GlobalLogic.getInstance().getProductInfoByType(localAAAProductItem.productId + "");
            if ((localList == null) && ((localAAAProductItem.product_type == 2) || (localAAAProductItem.product_type == 3)))
              localList = GlobalLogic.getInstance().getProductInfoByType("1999");
            if (localList == null)
              break label1096;
            i = localList.size();
            if (i <= 2)
              break label924;
            writeNodeValue(localXmlSerializer, "productType", (String)localList.get(0));
            writeNodeValue(localXmlSerializer, "productInfo", (String)localList.get(1));
            writeNodeValue(localXmlSerializer, "pictureBkg", (String)localList.get(2));
            writeNodeValue(localXmlSerializer, "beginDate", localAAAProductItem.beginDate);
            writeNodeValue(localXmlSerializer, "endDate", localAAAProductItem.endDate);
          }
        }
        else
        {
          localXmlSerializer.endTag(null, "product");
          continue;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      writeNodeValue(localXmlSerializer, "name", localAAAProductItem.name);
      continue;
      label764: writeNodeValue(localXmlSerializer, "subtitle", localAAAProductItem.time + "/" + localAAAProductItem.price + "元");
      writeNodeValue(localXmlSerializer, "price", f + "元");
      writeNodeValue(localXmlSerializer, "productPrice", f + "");
      continue;
      label873: writeNodeValue(localXmlSerializer, "price_show", localAAAProductItem.price_show + "元");
      continue;
      label909: writeNodeValue(localXmlSerializer, "vipType", "0");
      continue;
      label924: if (i > 1)
      {
        str1 = "part";
        writeNodeValue(localXmlSerializer, "productType", (String)localList.get(0));
        writeNodeValue(localXmlSerializer, "productInfo", (String)localList.get(1));
      }
      else if (i == 1)
      {
        str1 = "part";
        writeNodeValue(localXmlSerializer, "productInfo", (String)localList.get(0));
        continue;
        label1010: writeNodeValue(localXmlSerializer, "showType", str1);
        localXmlSerializer.endTag(null, "productlist");
        localXmlSerializer.endDocument();
        localXmlSerializer.flush();
        String str2 = localStringWriter.toString();
        Logger.d("productlist info:" + str2);
        ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str2.getBytes("UTF-8"));
        return localByteArrayInputStream;
        label1096: str1 = "part";
      }
    }
  }

  private ByteArrayInputStream buildSmsProduct(AAASmsPay paramAAASmsPay)
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "smsproduct");
      List localList;
      int i;
      if (paramAAASmsPay.type.equalsIgnoreCase("yes"))
      {
        localXmlSerializer.startTag(null, "product");
        writeNodeValue(localXmlSerializer, "product_name", paramAAASmsPay.product_name);
        writeNodeValue(localXmlSerializer, "product_id", paramAAASmsPay.product_id);
        writeNodeValue(localXmlSerializer, "product_price", "30天/" + paramAAASmsPay.price + "元");
        writeNodeValue(localXmlSerializer, "channel", paramAAASmsPay.channel);
        writeNodeValue(localXmlSerializer, "vipType", "1");
        localList = GlobalLogic.getInstance().getProductInfoByType("vip");
        if (localList != null)
        {
          i = localList.size();
          Logger.i("len=" + i);
          if (i <= 0)
            break label308;
          writeNodeValue(localXmlSerializer, "productType", (String)localList.get(0));
        }
      }
      while (true)
      {
        localXmlSerializer.endTag(null, "product");
        localXmlSerializer.endTag(null, "smsproduct");
        localXmlSerializer.endDocument();
        localXmlSerializer.flush();
        String str = localStringWriter.toString();
        Logger.d("AAASmsPay info:" + str);
        return new ByteArrayInputStream(str.getBytes("UTF-8"));
        label308: if (i > 1)
        {
          writeNodeValue(localXmlSerializer, "productType", (String)localList.get(0));
          writeNodeValue(localXmlSerializer, "productInfo", (String)localList.get(1));
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private ByteArrayInputStream buildThirdPartInfo(UserCenter3rdLoginUrl paramUserCenter3rdLoginUrl)
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "thirdpart");
      writeNodeValue(localXmlSerializer, "url", "file:///.app/thirdpart/QR");
      writeNodeValue(localXmlSerializer, "rcode", paramUserCenter3rdLoginUrl.rcode);
      XulWorker.addDrawableToCache("file:///.app/thirdpart/QR", XulDrawable.fromBitmap(CommonUiTools.createImage(paramUserCenter3rdLoginUrl.url, 585, 585), "file:///.app/thirdpart/QR", "file:///.app/thirdpart/QR"));
      localXmlSerializer.endTag(null, "thirdpart");
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      String str = localStringWriter.toString();
      Logger.d("", "thirdpart info:" + str);
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
      return localByteArrayInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private ByteArrayInputStream buildUserInfo(UserInfo paramUserInfo, UserCenterInfo paramUserCenterInfo)
  {
    while (true)
    {
      XmlSerializer localXmlSerializer;
      try
      {
        localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
        StringWriter localStringWriter = new StringWriter();
        localXmlSerializer.setOutput(localStringWriter);
        localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
        localXmlSerializer.startTag(null, "user_info");
        String str1 = paramUserInfo.name;
        String str2 = paramUserInfo.loginMode;
        if (paramUserInfo.wechat_type.length() > 0)
        {
          str3 = "微信昵称：" + paramUserInfo.name;
          str2 = "mgtv";
          if ((!TextUtils.isEmpty(str3)) && (str3.length() > 12))
            str3 = str3.substring(0, 10) + "...";
          writeNodeValue(localXmlSerializer, "id", paramUserInfo.user_id);
          writeNodeValue(localXmlSerializer, "account", getUserAccountShow());
          writeNodeValue(localXmlSerializer, "mode", str2);
          writeNodeValue(localXmlSerializer, "nick", str3);
          if (paramUserInfo.rtype.equals("400"))
          {
            writeNodeValue(localXmlSerializer, "mobile", paramUserInfo.mi_mobile);
            writeNodeValue(localXmlSerializer, "avatar", paramUserInfo.avatar);
            writeNodeValue(localXmlSerializer, "avatar_big", "effect:circle:" + paramUserInfo.avatar + "#big");
            writeNodeValue(localXmlSerializer, "wechat_type", paramUserInfo.wechat_type);
            writeNodeValue(localXmlSerializer, "rtype", paramUserInfo.rtype);
            writeNodeValue(localXmlSerializer, "vip_id", paramUserCenterInfo.vipId + "");
            writeNodeValue(localXmlSerializer, "vip_power", paramUserCenterInfo.viPower + "");
            writeNodeValue(localXmlSerializer, "vip_days", paramUserCenterInfo.vipEndDays + "");
            if ((!TextUtils.isEmpty(paramUserCenterInfo.vipEndDate)) && (paramUserCenterInfo.vipEndDate.contains("-")))
              writeNodeValue(localXmlSerializer, "vip_end_date", paramUserCenterInfo.vipEndDate.replace('-', '.'));
            if ((paramUserCenterInfo.vipName.equalsIgnoreCase("null")) || (paramUserCenterInfo.vipName.equals("")))
              paramUserCenterInfo.vipName = "普通会员";
            writeNodeValue(localXmlSerializer, "vip_name", paramUserCenterInfo.vipName);
            if (paramUserCenterInfo.balance - (int)paramUserCenterInfo.balance >= 1.0E-006D)
              break label792;
            writeNodeValue(localXmlSerializer, "balance", String.valueOf((int)paramUserCenterInfo.balance) + "");
            localXmlSerializer.endTag(null, "user_info");
            localXmlSerializer.endDocument();
            localXmlSerializer.flush();
            Logger.d("info/user info:" + localStringWriter.toString());
            return new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
          }
        }
        else
        {
          if (str2.equals("mgtv"))
          {
            str3 = "";
            continue;
          }
          if (paramUserInfo.rtype.equals("100"))
          {
            str3 = "QQ昵称：" + paramUserInfo.name;
            continue;
          }
          if (paramUserInfo.rtype.equals("300"))
          {
            str3 = "微信昵称：" + paramUserInfo.name;
            continue;
          }
          if (paramUserInfo.rtype.equals("400"))
          {
            if (str1.equals(paramUserInfo.mi_userid))
              break label829;
            str3 = paramUserInfo.name;
            continue;
          }
          str3 = "微博昵称：" + paramUserInfo.name;
          continue;
        }
        writeNodeValue(localXmlSerializer, "mobile", paramUserInfo.mobile);
        continue;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      label792: writeNodeValue(localXmlSerializer, "balance", String.valueOf(paramUserCenterInfo.balance) + "");
      continue;
      label829: String str3 = "";
    }
  }

  private ByteArrayInputStream buildVipInfo(AAAVipInfo paramAAAVipInfo)
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "vipinfo");
      writeNodeValue(localXmlSerializer, "name", paramAAAVipInfo.name + "");
      writeNodeValue(localXmlSerializer, "subname", paramAAAVipInfo.subname + "");
      writeNodeValue(localXmlSerializer, "description", paramAAAVipInfo.description + "");
      writeNodeValue(localXmlSerializer, "movie", "VIP电影：" + paramAAAVipInfo.movieNum + "部");
      writeNodeValue(localXmlSerializer, "tvplays", "VIP电视剧：" + paramAAAVipInfo.tvplaysNum + "部");
      writeNodeValue(localXmlSerializer, "anime", "VIP动漫：" + paramAAAVipInfo.animeNum + "部");
      writeNodeValue(localXmlSerializer, "music", "VIP音乐：" + paramAAAVipInfo.musicNum + "部");
      localXmlSerializer.endTag(null, "vipinfo");
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      String str = localStringWriter.toString();
      Logger.d("vipinfo info:" + str);
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
      return localByteArrayInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private ByteArrayInputStream buildVipList(AAAVipList paramAAAVipList)
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "viplist");
      for (int i = 0; i < paramAAAVipList.vipList.size(); i++)
      {
        localXmlSerializer.startTag(null, "vip");
        AAAVipItem localAAAVipItem = (AAAVipItem)paramAAAVipList.vipList.get(i);
        if (localAAAVipItem != null)
        {
          writeNodeValue(localXmlSerializer, "name", localAAAVipItem.name + "");
          writeNodeValue(localXmlSerializer, "vipid", localAAAVipItem.vipId + "");
          writeNodeValue(localXmlSerializer, "location", localAAAVipItem.location + "");
          writeNodeValue(localXmlSerializer, "power", localAAAVipItem.power + "");
        }
        localXmlSerializer.endTag(null, "vip");
      }
      localXmlSerializer.endTag(null, "viplist");
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      String str = localStringWriter.toString();
      Logger.d("viplist info:" + str);
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
      return localByteArrayInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private ByteArrayInputStream buildWebChatLoginInfo(WebChatLogin paramWebChatLogin)
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "webchat");
      long l = SystemTimeManager.getCurrentServerTime();
      writeNodeValue(localXmlSerializer, "url", paramWebChatLogin.url + "#" + l);
      writeNodeValue(localXmlSerializer, "rcode", paramWebChatLogin.rcode);
      writeNodeValue(localXmlSerializer, "scene_id", paramWebChatLogin.scene_id);
      localXmlSerializer.endTag(null, "webchat");
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      String str = localStringWriter.toString();
      Logger.d("", "webchat info:" + str);
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
      return localByteArrayInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private ByteArrayInputStream buildWechatPay(AAAWechatPayQrcode paramAAAWechatPayQrcode)
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "wechatpay");
      writeNodeValue(localXmlSerializer, "url", paramAAAWechatPayQrcode.url);
      localXmlSerializer.endTag(null, "wechatpay");
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      String str = localStringWriter.toString();
      Logger.d("", "WechatPay info:" + str);
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
      return localByteArrayInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private ByteArrayInputStream buildterminalicon(String paramString)
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "termanal_icon");
      Logger.i("", "result==" + paramString);
      writeNodeValue(localXmlSerializer, "img_url", paramString);
      localXmlSerializer.endTag(null, "termanal_icon");
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      Logger.d("info/terminaliconlist:" + localStringWriter.toString());
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
      return localByteArrayInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private InputStream getAd(String paramString)
  {
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    ServerApiManager.i().APIGetAdInfoByAdPos(paramString, new SccmsApiGetAdInfoByAdPosTask.ISccmsApiGetAdInfoByAdPosTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e("", "Load video advert error !!!");
        localXulPendingInputStream.cancel();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AdInfos paramAnonymousAdInfos)
      {
        if (paramAnonymousAdInfos.hasImageAdvert())
        {
          Logger.d("", "There is a image ad !!!");
          localXulPendingInputStream.setBaseStream(AppInputStream.this.buildAdStream(paramAnonymousAdInfos));
          return;
        }
        localXulPendingInputStream.cancel();
      }
    });
    return localXulPendingInputStream;
  }

  private InputStream getAlipayStauts()
  {
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    String str1 = GlobalLogic.getInstance().getWebToken();
    String str2 = GlobalEnv.getInstance().getAAALicense();
    ServerApiManager.i().ApiAAAGetPayAuthorizeStatus(str1, 1, str2, new SccmsApiAAAGetPayAuthorizeStatusTask.ISccmsApiAAAGetPayAuthorizeStatusListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        localXulPendingInputStream.cancel();
        AppInputStream.access$000();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, PayAuthorizeStatus paramAnonymousPayAuthorizeStatus)
      {
        localXulPendingInputStream.setBaseStream(AppInputStream.this.buildPayAuthorizeStatus(paramAnonymousPayAuthorizeStatus));
      }
    });
    return localXulPendingInputStream;
  }

  private List<DayItems> getDataResult(GetPlaybillSelectedListInfo paramGetPlaybillSelectedListInfo)
  {
    ArrayList localArrayList = new ArrayList();
    HashSet localHashSet = new HashSet();
    Iterator localIterator1 = paramGetPlaybillSelectedListInfo.items.iterator();
    while (localIterator1.hasNext())
      localHashSet.add(((GetPlaybillSelectedListInfo.Item)localIterator1.next()).day);
    Iterator localIterator2 = localHashSet.iterator();
    while (localIterator2.hasNext())
      localArrayList.add(new DayItems((String)localIterator2.next()));
    Iterator localIterator3 = paramGetPlaybillSelectedListInfo.items.iterator();
    while (true)
    {
      if (!localIterator3.hasNext())
        break label201;
      GetPlaybillSelectedListInfo.Item localItem = (GetPlaybillSelectedListInfo.Item)localIterator3.next();
      Iterator localIterator4 = localArrayList.iterator();
      if (localIterator4.hasNext())
      {
        DayItems localDayItems = (DayItems)localIterator4.next();
        if (!localDayItems.day.equals(localItem.day))
          break;
        localDayItems.items.add(localItem);
      }
    }
    label201: Collections.sort(localArrayList);
    return localArrayList;
  }

  private ByteArrayInputStream getDeviceInfo()
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "device_info");
      localXmlSerializer.startTag(null, "service");
      writeNodeValue(localXmlSerializer, "servicecode", UserFeedbackAndDeviceInfo.getInstance().getPseudoRandom());
      localXmlSerializer.endTag(null, "service");
      localXmlSerializer.startTag(null, "net");
      writeNodeValue(localXmlSerializer, "mac", UserFeedbackAndDeviceInfo.getInstance().getDeviceMac());
      localXmlSerializer.endTag(null, "net");
      localXmlSerializer.startTag(null, "network");
      writeNodeValue(localXmlSerializer, "ip", UserFeedbackAndDeviceInfo.getInstance().getDeviceIp());
      localXmlSerializer.endTag(null, "network");
      localXmlSerializer.startTag(null, "version");
      writeNodeValue(localXmlSerializer, "version_name", UserFeedbackAndDeviceInfo.getInstance().getAppVersion());
      localXmlSerializer.endTag(null, "version");
      localXmlSerializer.startTag(null, "screen");
      writeNodeValue(localXmlSerializer, "screenpixels", UserFeedbackAndDeviceInfo.getInstance().getScreenPixels());
      localXmlSerializer.endTag(null, "screen");
      localXmlSerializer.startTag(null, "android");
      writeNodeValue(localXmlSerializer, "platform", UserFeedbackAndDeviceInfo.getInstance().getPlatformType());
      localXmlSerializer.endTag(null, "android");
      localXmlSerializer.startTag(null, "androidplateform");
      writeNodeValue(localXmlSerializer, "versioncode", UserFeedbackAndDeviceInfo.getInstance().getPlatformVersion());
      localXmlSerializer.endTag(null, "androidplateform");
      localXmlSerializer.startTag(null, "devicetype");
      writeNodeValue(localXmlSerializer, "model", UserFeedbackAndDeviceInfo.getInstance().getModel());
      localXmlSerializer.endTag(null, "devicetype");
      localXmlSerializer.endTag(null, "device_info");
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

  public static AppInputStream getInstance()
  {
    if (appStream != null)
      return appStream;
    appStream = new AppInputStream();
    return appStream;
  }

  private ByteArrayInputStream getIsVip()
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "vip");
      if (GlobalLogic.getInstance().getUserInfo() != null)
        if (!"1".equals(GlobalLogic.getInstance().getUserInfo().vip_id))
          break label216;
      label216: for (String str2 = "1"; ; str2 = "0")
      {
        writeNodeValue(localXmlSerializer, "isvip", str2);
        String str3 = getUserAccountShow();
        if (!TextUtils.isEmpty(str3))
        {
          if (str3.length() > 5)
            str3 = str3.substring(0, 5) + "...";
          writeNodeValue(localXmlSerializer, "user_name", str3);
        }
        localXmlSerializer.endTag(null, "vip");
        localXmlSerializer.endDocument();
        localXmlSerializer.flush();
        String str1 = localStringWriter.toString();
        Logger.d("vip info:" + str1);
        return new ByteArrayInputStream(str1.getBytes("UTF-8"));
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private InputStream getLoginMode()
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "loginmode");
      writeNodeValue(localXmlSerializer, "mode", GlobalLogic.getInstance().getUserLoginMode());
      writeNodeValue(localXmlSerializer, "username", GlobalLogic.getInstance().getMgtvLoginUserName());
      localXmlSerializer.endTag(null, "loginmode");
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      String str = localStringWriter.toString();
      Logger.d("", "loginmode info:" + str);
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
      return localByteArrayInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private InputStream getLogo()
  {
    String str = GlobalEnv.getInstance().getMainActivityLogo();
    if (!TextUtils.isEmpty(str))
      try
      {
        ByteArrayInputStream localByteArrayInputStream2 = buildterminalicon(str);
        return localByteArrayInputStream2;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    try
    {
      ByteArrayInputStream localByteArrayInputStream1 = buildterminalicon("file:///.assets/mango_logo.png");
      return localByteArrayInputStream1;
    }
    catch (Exception localException1)
    {
      Logger.d("", "tryring: failed open assert xul/");
    }
    return null;
  }

  private InputStream getMediaAssetType()
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "media_asset_type");
      writeNodeValue(localXmlSerializer, "value", GlobalLogic.getInstance().getMediaAssetType() + "");
      PurchaseParam localPurchaseParam = GlobalLogic.getInstance().getPurchaseParam();
      if ((localPurchaseParam != null) && (!TextUtils.isEmpty(localPurchaseParam.videoId)))
        if (!TextUtils.isEmpty(localPurchaseParam.videoName))
          writeNodeValue(localXmlSerializer, "name", localPurchaseParam.videoName);
      while (true)
      {
        writeNodeValue(localXmlSerializer, "video_type", localPurchaseParam.videoType);
        localXmlSerializer.endTag(null, "media_asset_type");
        localXmlSerializer.endDocument();
        localXmlSerializer.flush();
        Logger.d("info/media_asset_type:" + localStringWriter.toString());
        return new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8"));
        writeNodeValue(localXmlSerializer, "name", "");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private InputStream getMovieCouponCount()
  {
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    String str1 = GlobalLogic.getInstance().getWebToken();
    String str2 = GlobalEnv.getInstance().getAAALicense();
    ServerApiManager.i().ApiAAAGetMovieCouponCount(str1, 1, str2, new SccmsApiAAAGetMovieCouponCountTask.ISccmsApiAAAGetMovieCouponCountTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        localXulPendingInputStream.cancel();
        AppInputStream.access$000();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAMovieCouponCount paramAnonymousAAAMovieCouponCount)
      {
        if (paramAnonymousAAAMovieCouponCount == null)
        {
          localXulPendingInputStream.cancel();
          return;
        }
        GlobalLogic.getInstance().saveCouponCount(paramAnonymousAAAMovieCouponCount.common_count, paramAnonymousAAAMovieCouponCount.special_count);
        localXulPendingInputStream.setBaseStream(AppInputStream.this.buildMovieCouponCount(paramAnonymousAAAMovieCouponCount));
      }
    });
    return localXulPendingInputStream;
  }

  private InputStream getMovieCouponDetailInfo()
  {
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    String str1 = GlobalLogic.getInstance().getWebToken();
    String str2 = GlobalEnv.getInstance().getAAALicense();
    ServerApiManager.i().ApiAAAGetMovieCouponInfo(str1, 1, str2, new SccmsApiAAAGetMovieCouponInfoTask.ISccmsApiAAAGetMovieCouponInfoTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        localXulPendingInputStream.cancel();
        AppInputStream.access$000();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAMovieCouponInfo paramAnonymousAAAMovieCouponInfo)
      {
        localXulPendingInputStream.setBaseStream(AppInputStream.this.buildMovieCouponInfo(paramAnonymousAAAMovieCouponInfo));
      }
    });
    return localXulPendingInputStream;
  }

  private InputStream getMovieCouponHistory(int paramInt)
  {
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    String str1 = GlobalLogic.getInstance().getWebToken();
    String str2 = GlobalEnv.getInstance().getAAALicense();
    ServerApiManager.i().ApiAAAGetMovieCouponHistory(str1, 1, str2, paramInt, 5, new SccmsApiAAAGetMovieCouponHistoryTask.ISccmsApiAAAGetMovieCouponHistoryTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        localXulPendingInputStream.cancel();
        AppInputStream.access$000();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAMovieCouponInfo paramAnonymousAAAMovieCouponInfo)
      {
        localXulPendingInputStream.setBaseStream(AppInputStream.this.buildMovieCouponHistory(paramAnonymousAAAMovieCouponInfo));
      }
    });
    return localXulPendingInputStream;
  }

  private InputStream getOrderInfo(String paramString, final boolean paramBoolean)
  {
    String str = "FFFFFFFFFFFF";
    int i = paramString.length();
    int j = "info/orderinfo".length();
    int k = 0;
    if (i > j)
      if (paramBoolean)
        break label159;
    label159: for (str = paramString.substring(15, paramString.lastIndexOf('/')); ; str = paramString.substring(23, paramString.lastIndexOf('/')))
    {
      k = Integer.valueOf(paramString.substring(1 + paramString.lastIndexOf('/'))).intValue();
      Logger.i("getAppData info/orderinfo product_id=" + k);
      Logger.i("getAppData info/orderinfo channel=" + str);
      final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
      ServerApiManager.i().APIAAABuyProduct(0, GlobalEnv.getInstance().getAAALicense(), GlobalLogic.getInstance().getWebToken(), 1, k, str, new SccmsApiAAABuyProductTask.ISccmsApiAAABuyProductTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          localXulPendingInputStream.cancel();
          AppInputStream.access$000();
          Logger.e("APIAAABuyProduct error!!");
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAOrderInfo paramAnonymousAAAOrderInfo)
        {
          GlobalLogic.getInstance().checkTokenStatus(paramAnonymousAAAOrderInfo.err, paramAnonymousAAAOrderInfo.status);
          localXulPendingInputStream.setBaseStream(AppInputStream.this.buildOrderInfo(paramAnonymousAAAOrderInfo, paramBoolean));
        }
      });
      return localXulPendingInputStream;
    }
  }

  private InputStream getOrderState(String paramString)
  {
    Object localObject1 = "";
    Object localObject2 = "";
    String str = "0";
    if (paramString.length() > "info/orderstate".length())
      str = paramString.substring(1 + paramString.lastIndexOf('/'));
    Logger.i("info/orderstate id=" + (String)localObject1);
    if (str.length() < 10)
      localObject2 = str;
    while (true)
    {
      final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
      ServerApiManager.i().APIAAAGetOrderState(GlobalEnv.getInstance().getAAALicense(), GlobalLogic.getInstance().getWebToken(), 1, (String)localObject1, (String)localObject2, new SccmsApiAAAGetOrderStateTask.ISccmsApiAAAGetOrderStateTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          localXulPendingInputStream.cancel();
          AppInputStream.access$000();
          Logger.e("APIAAAGetOrderState error!!");
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAOrderState paramAnonymousAAAOrderState)
        {
          GlobalLogic.getInstance().checkTokenStatus(paramAnonymousAAAOrderState.err, paramAnonymousAAAOrderState.status);
          localXulPendingInputStream.setBaseStream(AppInputStream.this.buildOrderState(paramAnonymousAAAOrderState));
        }
      });
      return localXulPendingInputStream;
      localObject1 = str;
    }
  }

  private InputStream getPayChannelList(String paramString)
  {
    String str = paramString.substring(1 + paramString.lastIndexOf('/'));
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    ServerApiManager.i().APIAAAGetPayChannelList(GlobalEnv.getInstance().getAAALicense(), str, new SccmsApiAAAGetPayChannelListTask.ISccmsApiAAAGetPayChannelListTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        localXulPendingInputStream.cancel();
        Logger.e("APIAAAGetPayChannelList error!!");
        AppInputStream.access$000();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAPayChannelList paramAnonymousAAAPayChannelList)
      {
        GlobalLogic.getInstance().checkTokenStatus(paramAnonymousAAAPayChannelList.err, paramAnonymousAAAPayChannelList.status);
        localXulPendingInputStream.setBaseStream(AppInputStream.this.buildPayChannelListInfo(paramAnonymousAAAPayChannelList));
      }
    });
    return localXulPendingInputStream;
  }

  private InputStream getPaymentInfo(String paramString, final boolean paramBoolean)
  {
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    String str1 = GlobalLogic.getInstance().getWebToken();
    String str2 = GlobalEnv.getInstance().getAAALicense();
    String str3 = "";
    String str4;
    String str5;
    int j;
    if ((!paramBoolean) && (paramString.length() > "info/payment".length()))
    {
      ArrayList localArrayList2 = new ArrayList();
      StringTokenizer localStringTokenizer2 = new StringTokenizer(paramString.substring("info/payment".length()), "/");
      while (localStringTokenizer2.hasMoreTokens())
        localArrayList2.add(localStringTokenizer2.nextToken());
      str4 = (String)localArrayList2.get(0);
      str5 = (String)localArrayList2.get(1);
      int k = localArrayList2.size();
      j = 0;
      if (k > 2)
      {
        str3 = (String)localArrayList2.get(2);
        j = Integer.valueOf((String)localArrayList2.get(3)).intValue();
      }
    }
    while (!str5.equals("0"))
    {
      ServerApiManager.i().APIAAARecharge(str2, str1, 1, j, str4, str3, str5, new SccmsApiAAARechargeTask.ISccmsApiAAARechargeTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          localXulPendingInputStream.cancel();
          AppInputStream.access$000();
          Logger.e("APIAAARecharge error!!");
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAOrderInfo paramAnonymousAAAOrderInfo)
        {
          GlobalLogic.getInstance().checkTokenStatus(paramAnonymousAAAOrderInfo.err, paramAnonymousAAAOrderInfo.status);
          localXulPendingInputStream.setBaseStream(AppInputStream.this.buildPaymentInfo(paramAnonymousAAAOrderInfo, paramBoolean));
        }
      });
      return localXulPendingInputStream;
      ArrayList localArrayList1 = new ArrayList();
      StringTokenizer localStringTokenizer1 = new StringTokenizer(paramString.substring("info/browser_recharge".length()), "/");
      while (localStringTokenizer1.hasMoreTokens())
        localArrayList1.add(localStringTokenizer1.nextToken());
      str4 = (String)localArrayList1.get(0);
      str5 = (String)localArrayList1.get(1);
      int i = localArrayList1.size();
      j = 0;
      if (i > 2)
      {
        str3 = (String)localArrayList1.get(2);
        j = Integer.valueOf((String)localArrayList1.get(3)).intValue();
      }
    }
    return null;
  }

  private InputStream getPriceList(String paramString)
  {
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    if (paramString.length() == "info/pricelist".length())
    {
      ServerApiManager.i().APIAAAGetPriceList(GlobalEnv.getInstance().getAAALicense(), new SccmsApiAAAGetPriceListTask.ISccmsApiAAAGetPriceListTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          localXulPendingInputStream.cancel();
          AppInputStream.access$000();
          Logger.e("APIAAAGetPriceList error!!");
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAPriceList paramAnonymousAAAPriceList)
        {
          GlobalLogic.getInstance().checkTokenStatus(paramAnonymousAAAPriceList.err, paramAnonymousAAAPriceList.status);
          localXulPendingInputStream.setBaseStream(AppInputStream.this.buildPriceListInfo(paramAnonymousAAAPriceList));
        }
      });
      return localXulPendingInputStream;
    }
    ArrayList localArrayList = new ArrayList();
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString.substring("info/pricelist".length()), "/");
    while (localStringTokenizer.hasMoreTokens())
      localArrayList.add(localStringTokenizer.nextToken());
    localXulPendingInputStream.setBaseStream(buildPriceListInfo(localArrayList));
    return localXulPendingInputStream;
  }

  private InputStream getProductList()
  {
    Logger.i("info/productlist vip_id=" + 1);
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    final PurchaseParam localPurchaseParam = GlobalLogic.getInstance().getPurchaseParam();
    if ((localPurchaseParam != null) && (localPurchaseParam.autoJump) && (!TextUtils.isEmpty(localPurchaseParam.videoId)))
    {
      final ArrayList localArrayList = new ArrayList();
      ServerApiManager.i().APIGetPublicImageTask("public_product_image", new SccmsApiGetPublicImageTask.ISccmsApiGetPublicImageTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          AppInputStream.this.getVideoProductList(localPurchaseParam, localXulPendingInputStream);
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, List<PublicImage> paramAnonymousList)
        {
          localArrayList.addAll(paramAnonymousList);
          GlobalLogic.getInstance().setProductInfo(localArrayList);
          AppInputStream.this.getVideoProductList(localPurchaseParam, localXulPendingInputStream);
        }
      });
      return localXulPendingInputStream;
    }
    ServerApiManager.i().APIAAAGetProductList(GlobalEnv.getInstance().getAAALicense(), GlobalLogic.getInstance().getWebToken(), 1, 1, new SccmsApiAAAGetProductListTask.ISccmsApiAAAGetProductListTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        localXulPendingInputStream.cancel();
        AppInputStream.access$000();
        Logger.e("", "APIAAAGetProductList error!!");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAProductList paramAnonymousAAAProductList)
      {
        GlobalLogic.getInstance().checkTokenStatus(paramAnonymousAAAProductList.err, paramAnonymousAAAProductList.status);
        final ArrayList localArrayList1 = paramAnonymousAAAProductList.productList;
        for (int i = 0; i < localArrayList1.size(); i++)
          if (((AAAProductItem)localArrayList1.get(i)).product_type == 0)
            ((AAAProductItem)localArrayList1.get(i)).type = 0;
        final ArrayList localArrayList2 = new ArrayList();
        ServerApiManager.i().APIGetPublicImageTask("public_product_image", new SccmsApiGetPublicImageTask.ISccmsApiGetPublicImageTaskListener()
        {
          public void onError(ServerApiTaskInfo paramAnonymous2ServerApiTaskInfo, ServerApiCommonError paramAnonymous2ServerApiCommonError)
          {
          }

          public void onSuccess(ServerApiTaskInfo paramAnonymous2ServerApiTaskInfo, List<PublicImage> paramAnonymous2List)
          {
            localArrayList2.addAll(paramAnonymous2List);
            GlobalLogic.getInstance().setProductInfo(localArrayList2);
            GlobalLogic.getInstance().setMediaAssetType(0);
            AppInputStream.9.this.val$productlist.setBaseStream(AppInputStream.this.buildProductList(localArrayList1, 0));
          }
        });
      }
    });
    return localXulPendingInputStream;
  }

  private InputStream getRechargeOrPurchaseInfo(String paramString)
  {
    int i = 1;
    int j;
    if (paramString.startsWith("info/purchase"))
    {
      if (paramString.length() > 13)
        i = XulUtils.tryParseInt(paramString.substring(14), 0);
      j = 1;
    }
    while (true)
    {
      if (i < 1)
        i = 1;
      if (j <= 0)
        break;
      final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
      String str1 = GlobalLogic.getInstance().getWebToken();
      String str2 = GlobalEnv.getInstance().getAAALicense();
      final int k = j;
      ServerApiManager.i().APIAAAGetOrderRecordList(str2, str1, 1, i, 4, j, new SccmsApiAAAGetOrderRecordListTask.ISccmsApiAAAGetOrderRecordListTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          localXulPendingInputStream.cancel();
          AppInputStream.access$000();
          Logger.e("APIAAAGetOrderRecordList error!!");
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAOrderRecordList paramAnonymousAAAOrderRecordList)
        {
          GlobalLogic.getInstance().checkTokenStatus(paramAnonymousAAAOrderRecordList.err, paramAnonymousAAAOrderRecordList.status);
          GlobalLogic.getInstance().checkTokenStatus(paramAnonymousAAAOrderRecordList.err, paramAnonymousAAAOrderRecordList.status);
          if (k == 1)
            localXulPendingInputStream.setBaseStream(AppInputStream.this.buildPurchaseInfo(paramAnonymousAAAOrderRecordList));
          while (k != 2)
            return;
          localXulPendingInputStream.setBaseStream(AppInputStream.this.buildRechargeInfo(paramAnonymousAAAOrderRecordList));
        }
      });
      return localXulPendingInputStream;
      boolean bool = paramString.startsWith("info/recharge");
      j = 0;
      if (bool)
      {
        if (paramString.length() > 13)
          i = XulUtils.tryParseInt(paramString.substring(14), 0);
        j = 2;
      }
    }
    return null;
  }

  private InputStream getSkins(String paramString)
  {
    String str = GlobalEnv.getInstance().getMainActivityBackground();
    if (!TextUtils.isEmpty(str))
      try
      {
        FileInputStream localFileInputStream = new FileInputStream(new File(GlobalEnv.getInstance().getPicCachePath(), str));
        return localFileInputStream;
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        localFileNotFoundException.printStackTrace();
      }
    AssetManager localAssetManager = App.getAppContext().getAssets();
    try
    {
      InputStream localInputStream = localAssetManager.open("xul/bkg.jpg");
      return localInputStream;
    }
    catch (IOException localIOException)
    {
      Logger.d("", "tryring: failed open assert xul/" + paramString);
    }
    return null;
  }

  private InputStream getSmsProduct()
  {
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    ServerApiManager.i().APIAAASmsPayTask(GlobalEnv.getInstance().getAAALicense(), new SccmsApiAAASmsPayTask.ISccmsApiAAASmsPayTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        localXulPendingInputStream.cancel();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAASmsPay paramAnonymousAAASmsPay)
      {
        localXulPendingInputStream.setBaseStream(AppInputStream.this.buildSmsProduct(paramAnonymousAAASmsPay));
      }
    });
    return localXulPendingInputStream;
  }

  private ByteArrayInputStream getSystemInfo()
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "system_info");
      localXmlSerializer.startTag(null, "net");
      writeNodeValue(localXmlSerializer, "mac", DeviceInfo.getMac());
      localXmlSerializer.endTag(null, "net");
      localXmlSerializer.startTag(null, "IDs");
      writeNodeValue(localXmlSerializer, "net", GlobalLogic.getInstance().getNetId());
      writeNodeValue(localXmlSerializer, "device", GlobalLogic.getInstance().getDeviceId());
      writeNodeValue(localXmlSerializer, "smartCard", GlobalLogic.getInstance().getSmartCardId());
      writeNodeValue(localXmlSerializer, "license", "010304005");
      localXmlSerializer.endTag(null, "IDs");
      localXmlSerializer.startTag(null, "apk");
      localXmlSerializer.startTag(null, "version");
      writeNodeValue(localXmlSerializer, "text", String.valueOf(DeviceInfo.getMGTVVersion()));
      writeNodeValue(localXmlSerializer, "major", String.valueOf(MgtvVersion.getMajorVersionNum()));
      writeNodeValue(localXmlSerializer, "minor", String.valueOf(MgtvVersion.getMinorVersionNum()));
      writeNodeValue(localXmlSerializer, "patch", String.valueOf(MgtvVersion.getPatchVersionNum()));
      writeNodeValue(localXmlSerializer, "type", String.valueOf(MgtvVersion.getReleaseType()));
      writeNodeValue(localXmlSerializer, "rev", "head");
      writeNodeValue(localXmlSerializer, "factory", String.valueOf(MgtvVersion.getFactoryCode()));
      writeNodeValue(localXmlSerializer, "policy", MgtvVersion.getPolicyNumber());
      localXmlSerializer.endTag(null, "version");
      localXmlSerializer.startTag(null, "number");
      writeNodeValue(localXmlSerializer, "phone", GlobalEnv.getInstance().getPhoneNumber().substring(5) + "  (工作时间：8:00-24:00)");
      localXmlSerializer.endTag(null, "number");
      localXmlSerializer.endTag(null, "apk");
      localXmlSerializer.endTag(null, "system_info");
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

  private InputStream getThirdPartInfo()
  {
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    ServerApiManager.i().APIUserCenterGet3rdLoginUrl(GlobalEnv.getInstance().getAAALicense(), new SccmsApiUserCenterGet3rdLoginUrlTask.ISccmsApiUserCenterGet3rdLoginUrlTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        localXulPendingInputStream.cancel();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserCenter3rdLoginUrl paramAnonymousUserCenter3rdLoginUrl)
      {
        localXulPendingInputStream.setBaseStream(AppInputStream.this.buildThirdPartInfo(paramAnonymousUserCenter3rdLoginUrl));
      }
    });
    return localXulPendingInputStream;
  }

  private String getUserAccountShow()
  {
    String str;
    if (GlobalLogic.getInstance().getUserInfo() == null)
    {
      str = "";
      return str;
    }
    UserInfo localUserInfo = GlobalLogic.getInstance().getUserInfo();
    if (localUserInfo.wechat_type.length() > 0)
      str = localUserInfo.account;
    while (true)
    {
      if (TextUtils.isEmpty(str))
        str = GlobalLogic.getInstance().getUserInfo().account;
      if (str.length() <= 12)
        break;
      return str.substring(0, 11) + "...";
      if (localUserInfo.rtype.length() > 0)
      {
        str = localUserInfo.name;
        if (localUserInfo.rtype.equals("400"))
          str = localUserInfo.mi_userid;
      }
      else
      {
        str = localUserInfo.account;
      }
    }
  }

  private InputStream getUserAgreement(String paramString)
  {
    final String str = paramString.substring(1 + paramString.lastIndexOf('/'));
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    ServerApiManager.i().APIGetAgreements(new SccmsApiGetAgreementTask.ISccmsApiGetAgreementTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        localXulPendingInputStream.cancel();
        AppInputStream.access$000();
        Logger.e("APIGetAgreements error!!");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, Agreements paramAnonymousAgreements)
      {
        localXulPendingInputStream.setBaseStream(AppInputStream.this.buildAgreement(paramAnonymousAgreements, str));
      }
    });
    return localXulPendingInputStream;
  }

  private InputStream getUserType()
  {
    XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "user");
      writeNodeValue(localXmlSerializer, "type", GlobalLogic.getInstance().getUserInfo().loginMode);
      localXmlSerializer.endTag(null, "user");
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      Logger.d("info/user_type info:" + localStringWriter.toString());
      localXulPendingInputStream.setBaseStream(new ByteArrayInputStream(localStringWriter.toString().getBytes("UTF-8")));
      return localXulPendingInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localXulPendingInputStream;
  }

  private void getVideoProductList(PurchaseParam paramPurchaseParam, final XulPendingInputStream paramXulPendingInputStream)
  {
    ArrayList localArrayList = GlobalLogic.getInstance().getProductList();
    if ((localArrayList != null) && (localArrayList.size() > 0))
    {
      Logger.i("list is not null! setBaseStream()");
      paramXulPendingInputStream.setBaseStream(buildProductList(localArrayList, GlobalLogic.getInstance().getMediaAssetType()));
      return;
    }
    Logger.i("list is  null! APIGetUserAuthV2()");
    GetUserAuthV2Params localGetUserAuthV2Params = new GetUserAuthV2Params(paramPurchaseParam.videoId, paramPurchaseParam.videoType, GlobalLogic.getInstance().getQuality());
    ServerApiManager.i().APIGetUserAuthV2(localGetUserAuthV2Params, new SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.i("", "SccmsApiGetUserAuthV2TaskListener.onError() error:" + paramAnonymousServerApiCommonError.toString());
        GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
        paramXulPendingInputStream.cancel();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserAuthV2 paramAnonymousUserAuthV2)
      {
        Logger.i("", "SccmsApiGetUserAuthV2TaskListener.onSuccess() :" + paramAnonymousUserAuthV2.toString());
        if (paramAnonymousUserAuthV2.state != null)
        {
          GlobalLogic.getInstance().setProductList(paramAnonymousUserAuthV2.list);
          ArrayList localArrayList = GlobalLogic.getInstance().getProductList();
          if ((localArrayList != null) && (localArrayList.size() > 0))
          {
            paramXulPendingInputStream.setBaseStream(AppInputStream.this.buildProductList(localArrayList, GlobalLogic.getInstance().getMediaAssetType()));
            return;
          }
          GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
          paramXulPendingInputStream.cancel();
          return;
        }
        GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
        paramXulPendingInputStream.cancel();
      }
    });
  }

  private InputStream getVipInfo()
  {
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    ServerApiManager.i().APIAAAGetVipInfo(GlobalEnv.getInstance().getAAALicense(), 1, new SccmsApiAAAGetVipInfoTask.ISccmsApiAAAGetVipInfoTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        localXulPendingInputStream.cancel();
        AppInputStream.access$000();
        Logger.e("APIAAAGetVipInfo error!!");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAVipInfo paramAnonymousAAAVipInfo)
      {
        GlobalLogic.getInstance().checkTokenStatus(paramAnonymousAAAVipInfo.err, paramAnonymousAAAVipInfo.status);
        localXulPendingInputStream.setBaseStream(AppInputStream.this.buildVipInfo(paramAnonymousAAAVipInfo));
      }
    });
    return localXulPendingInputStream;
  }

  private InputStream getVipList()
  {
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    ServerApiManager.i().APIAAAGetVipList(GlobalEnv.getInstance().getAAALicense(), new SccmsApiAAAGetVipListTask.ISccmsApiAAAGetVipListTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        localXulPendingInputStream.cancel();
        AppInputStream.access$000();
        Logger.e("APIAAAGetVipList error!!");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAVipList paramAnonymousAAAVipList)
      {
        GlobalLogic.getInstance().checkTokenStatus(paramAnonymousAAAVipList.err, paramAnonymousAAAVipList.status);
        localXulPendingInputStream.setBaseStream(AppInputStream.this.buildVipList(paramAnonymousAAAVipList));
      }
    });
    return localXulPendingInputStream;
  }

  private InputStream getWeChatInfo(String paramString)
  {
    Logger.i("info/webchat path=" + paramString);
    int i = "info/webchat".length();
    String str = "login";
    if (paramString.length() > i)
      str = paramString.substring(1 + paramString.lastIndexOf('/'));
    boolean bool = str.equalsIgnoreCase("bind");
    int j = 0;
    if (bool)
    {
      j = 1;
      Logger.i("info/webchat type=" + j);
    }
    Logger.i("info/webchat type=" + j);
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    ServerApiManager.i().APIUserCenterGetWebChatLoginPic(GlobalEnv.getInstance().getAAALicense(), j, new SccmsApiUserCenterGetWebChatLoginPicTask.ISccmsApiGetWebChatLoginPicTaskListenter()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        localXulPendingInputStream.cancel();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, WebChatLogin paramAnonymousWebChatLogin)
      {
        GlobalLogic.getInstance().checkTokenStatus(paramAnonymousWebChatLogin.err, paramAnonymousWebChatLogin.status);
        localXulPendingInputStream.setBaseStream(AppInputStream.this.buildWebChatLoginInfo(paramAnonymousWebChatLogin));
      }
    });
    return localXulPendingInputStream;
  }

  private InputStream getWechatPay()
  {
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    ServerApiManager.i().APIAAAGetWechatPayQrcodeTask(GlobalEnv.getInstance().getAAALicense(), new SccmsApiAAAGetWechatPayQrcodeTask.ISccmsApiAAAGetWechatPayQrcodeTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        localXulPendingInputStream.cancel();
        AppInputStream.access$000();
        Logger.e("APIAAAGetWechatPayQrcodeTask error!!");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAWechatPayQrcode paramAnonymousAAAWechatPayQrcode)
      {
        GlobalLogic.getInstance().checkTokenStatus(paramAnonymousAAAWechatPayQrcode.err, paramAnonymousAAAWechatPayQrcode.status);
        localXulPendingInputStream.setBaseStream(AppInputStream.this.buildWechatPay(paramAnonymousAAAWechatPayQrcode));
      }
    });
    return localXulPendingInputStream;
  }

  private static void requestAAAError()
  {
    Intent localIntent = new Intent("ACTION_SHOW_NETERROR_DIALOG");
    App.getAppContext().sendBroadcast(localIntent);
  }

  private void writeAccountInfo(XmlSerializer paramXmlSerializer)
    throws IOException
  {
    String str = "";
    if (GlobalLogic.getInstance().getUserInfo() != null)
      str = GlobalLogic.getInstance().getUserInfo().avatar;
    writeNodeValue(paramXmlSerializer, "avatar", str);
    writeNodeValue(paramXmlSerializer, "account", getUserAccountShow());
  }

  private void writeArgsValue(XmlSerializer paramXmlSerializer, String paramString1, String paramString2)
    throws IOException
  {
    paramXmlSerializer.startTag(null, "a");
    writeNodeValue(paramXmlSerializer, "k", paramString1);
    writeNodeValue(paramXmlSerializer, "v", paramString2);
    paramXmlSerializer.endTag(null, "a");
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

  public ByteArrayInputStream buildPurchaseInfo(AAAOrderRecordList paramAAAOrderRecordList)
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "purchase");
      writeAccountInfo(localXmlSerializer);
      writeNodeValue(localXmlSerializer, "total", String.valueOf(paramAAAOrderRecordList.total));
      writeNodeValue(localXmlSerializer, "totalPages", String.valueOf((-1 + (4 + paramAAAOrderRecordList.total)) / 4));
      writeNodeValue(localXmlSerializer, "nowPage", String.valueOf(paramAAAOrderRecordList.nowPage));
      for (int i = 0; i < paramAAAOrderRecordList.recordList.size(); i++)
      {
        localXmlSerializer.startTag(null, "order");
        AAAOrderRecordItem localAAAOrderRecordItem = (AAAOrderRecordItem)paramAAAOrderRecordList.recordList.get(i);
        if (localAAAOrderRecordItem != null)
        {
          writeNodeValue(localXmlSerializer, "productName", localAAAOrderRecordItem.productName);
          if ((!TextUtils.isEmpty(localAAAOrderRecordItem.channelName)) && (localAAAOrderRecordItem.channelName.length() > 5))
            localAAAOrderRecordItem.channelName = (localAAAOrderRecordItem.channelName.substring(0, 4) + "...");
          writeNodeValue(localXmlSerializer, "channelName", localAAAOrderRecordItem.channelName);
          writeNodeValue(localXmlSerializer, "beginDate", localAAAOrderRecordItem.beginDate + "");
          writeNodeValue(localXmlSerializer, "payTime", localAAAOrderRecordItem.payTime);
          if ((!TextUtils.isEmpty(localAAAOrderRecordItem.beginDate)) && (!"null".equalsIgnoreCase(localAAAOrderRecordItem.beginDate)))
            writeNodeValue(localXmlSerializer, "validDate", localAAAOrderRecordItem.beginDate + " - " + localAAAOrderRecordItem.endDate);
          writeNodeValue(localXmlSerializer, "price", localAAAOrderRecordItem.price + "元");
        }
        localXmlSerializer.endTag(null, "order");
      }
      localXmlSerializer.endTag(null, "purchase");
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      String str = localStringWriter.toString();
      Logger.d("", "purchase info:" + str);
      GeneralUtils.writeFile(new File(App.getAppContext().getDir("data", 0), "purchase.xml"), str);
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
      return localByteArrayInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public ByteArrayInputStream buildRechargeInfo(AAAOrderRecordList paramAAAOrderRecordList)
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      StringWriter localStringWriter = new StringWriter();
      localXmlSerializer.setOutput(localStringWriter);
      localXmlSerializer.startDocument("UTF-8", Boolean.valueOf(true));
      localXmlSerializer.startTag(null, "recharge");
      writeNodeValue(localXmlSerializer, "total", String.valueOf(paramAAAOrderRecordList.total));
      writeNodeValue(localXmlSerializer, "totalPages", String.valueOf((-1 + (4 + paramAAAOrderRecordList.total)) / 4));
      writeNodeValue(localXmlSerializer, "nowPage", String.valueOf(paramAAAOrderRecordList.nowPage));
      for (int i = 0; i < paramAAAOrderRecordList.recordList.size(); i++)
      {
        localXmlSerializer.startTag(null, "order");
        AAAOrderRecordItem localAAAOrderRecordItem = (AAAOrderRecordItem)paramAAAOrderRecordList.recordList.get(i);
        if (localAAAOrderRecordItem != null)
        {
          if ((!TextUtils.isEmpty(localAAAOrderRecordItem.channelName)) && (localAAAOrderRecordItem.channelName.length() > 5))
            localAAAOrderRecordItem.channelName = (localAAAOrderRecordItem.channelName.substring(0, 4) + "...");
          writeNodeValue(localXmlSerializer, "channelName", localAAAOrderRecordItem.channelName);
          writeNodeValue(localXmlSerializer, "payTime", localAAAOrderRecordItem.payTime);
          writeNodeValue(localXmlSerializer, "price", localAAAOrderRecordItem.price + "元");
          writeNodeValue(localXmlSerializer, "count", localAAAOrderRecordItem.count + "芒果币");
        }
        localXmlSerializer.endTag(null, "order");
      }
      localXmlSerializer.endTag(null, "recharge");
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      String str = localStringWriter.toString();
      Logger.d("", "recharge info:" + str);
      GeneralUtils.writeFile(new File(App.getAppContext().getDir("data", 0), "recharge.xml"), str);
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
      return localByteArrayInputStream;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public InputStream getAppInputStream(String paramString)
  {
    Logger.i("getAppInputStream path = " + paramString);
    if ("info/system".equals(paramString))
    {
      if (this.infoSystem != null)
      {
        this.infoSystem.reset();
        return this.infoSystem;
      }
      this.infoSystem = getSystemInfo();
      return this.infoSystem;
    }
    if ("api/n36".equals(paramString))
    {
      byte[] arrayOfByte = GlobalLogic.getInstance().getMetaData();
      int m;
      if (arrayOfByte != null)
      {
        int n = arrayOfByte.length;
        m = 0;
        if (n > 16);
      }
      else
      {
        m = 1;
      }
      if (m != 0)
      {
        final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
        ServerApiManager.i().APIGetInitMetaData(new SccmsApiGetInitMetaDataTask.ISccmsApiGetInitMetaDataTaskListener()
        {
          public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
          {
            Logger.i("", "APIGetInitMetaData error!");
            localXulPendingInputStream.cancel();
          }

          public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, byte[] paramAnonymousArrayOfByte)
          {
            if (paramAnonymousArrayOfByte != null)
            {
              Logger.i("", "APIGetInitMetaData onSuccess!");
              GlobalLogic.getInstance().setMetaData(paramAnonymousArrayOfByte);
              localXulPendingInputStream.setBaseStream(new ByteArrayInputStream(GlobalLogic.getInstance().getMetaData()));
              return;
            }
            Logger.i("", "APIGetInitMetaData is null!");
            localXulPendingInputStream.cancel();
          }
        });
        return localXulPendingInputStream;
      }
      return new ByteArrayInputStream(arrayOfByte);
    }
    if ("info/user".equals(paramString))
      return getUserInfo();
    if ("info/user_type".equals(paramString))
      return getUserType();
    if ("info/isVip".equals(paramString))
      return getIsVip();
    if (paramString.startsWith("info/webchat"))
      return getWeChatInfo(paramString);
    if ("info/device".equals(paramString))
      return getDeviceInfo();
    if (paramString.startsWith("info/loginmode"))
      return getLoginMode();
    if ("info/thirdpart".equals(paramString))
      return getThirdPartInfo();
    if (paramString.startsWith("info/useragreement"))
      return getUserAgreement(paramString);
    if ("info/vipinfo".equals(paramString))
    {
      Logger.d("info/vipinfo= " + paramString);
      return getVipInfo();
    }
    if ("info/viplist".equals(paramString))
    {
      Logger.d("info/viplist= " + paramString);
      return getVipList();
    }
    if ("info/productlist".equals(paramString))
    {
      Logger.d("info/productlist=" + paramString);
      return getProductList();
    }
    if ("info/smsproduct".equals(paramString))
    {
      Logger.d("info/smsproduct=" + paramString);
      return getSmsProduct();
    }
    if (paramString.startsWith("info/orderinfo/browser"))
      return getOrderInfo(paramString, true);
    if (paramString.startsWith("info/orderinfo"))
      return getOrderInfo(paramString, false);
    if (paramString.startsWith("info/browser_recharge"))
      return getPaymentInfo(paramString, true);
    if (paramString.startsWith("info/orderstate"))
      return getOrderState(paramString);
    if ((paramString.startsWith("info/recharge")) || (paramString.startsWith("info/purchase")))
    {
      Logger.i("path= " + paramString);
      return getRechargeOrPurchaseInfo(paramString);
    }
    if (paramString.startsWith("info/payment"))
      return getPaymentInfo(paramString, false);
    if (paramString.startsWith("info/pricelist"))
      return getPriceList(paramString);
    if (paramString.startsWith("info/paychannellist"))
      return getPayChannelList(paramString);
    if ("info/Ad".equals(paramString))
      return getAd(GlobalEnv.getInstance().getClockAdId());
    if ("skins/background".equals(paramString))
      return getSkins(paramString);
    if ("info/wechat_pay".equals(paramString))
      return getWechatPay();
    if (paramString.startsWith("info/get_media_assets_info"))
      return getGetMediaAssetsInfo(paramString);
    if (paramString.startsWith("info/get_playbill_selected"))
      try
      {
        InputStream localInputStream = getPlaybillSelected(paramString);
        return localInputStream;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    if (paramString.equalsIgnoreCase("info/movie_coupon_count"))
      return getMovieCouponCount();
    if (paramString.startsWith("info/movie_coupon_history"))
    {
      int i = "info/movie_coupon_history".length();
      int j = paramString.length();
      int k = 0;
      if (j > i)
        k = XulUtils.tryParseInt(paramString.substring(i + 1), 0);
      return getMovieCouponHistory(k);
    }
    if (paramString.equalsIgnoreCase("info/movie_coupon_info"))
      return getMovieCouponDetailInfo();
    if ("info/terminaliconlist".equals(paramString))
      return getLogo();
    if ("info/media_asset_type".equals(paramString))
      return getMediaAssetType();
    if ("info/alipay_status".equals(paramString))
      return getAlipayStauts();
    return null;
  }

  public InputStream getGetMediaAssetsInfo(String paramString)
  {
    String str = paramString.substring(1 + paramString.lastIndexOf('/'));
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    ServerApiManager.i().APIGetMediaAssetsInfo(str, new SccmsApiGetMediaAssetsInfoTask.ISccmsApiGetMediaAssetsInfoTaskListener()
    {
      public String inputStream2String(InputStream paramAnonymousInputStream)
        throws IOException
      {
        StringBuffer localStringBuffer = new StringBuffer();
        byte[] arrayOfByte = new byte[4096];
        while (true)
        {
          int i = paramAnonymousInputStream.read(arrayOfByte);
          if (i == -1)
            break;
          localStringBuffer.append(new String(arrayOfByte, 0, i));
        }
        return localStringBuffer.toString();
      }

      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        localXulPendingInputStream.cancel();
        Intent localIntent = new Intent("ACTION_SHOW_ERROR_DIALOG");
        App.getAppContext().sendBroadcast(localIntent);
        Logger.e("", "APIGetMediaAssetsInfo ERROR");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, MediaAssetsInfo paramAnonymousMediaAssetsInfo)
      {
        InputStream localInputStream = AppInputStream.this.buildMediaAssetsInfo(paramAnonymousMediaAssetsInfo);
        try
        {
          String str = inputStream2String(localInputStream);
          Logger.d("", "xxx,  str=" + str);
          localInputStream.reset();
          localXulPendingInputStream.setBaseStream(localInputStream);
          return;
        }
        catch (IOException localIOException)
        {
          while (true)
            localIOException.printStackTrace();
        }
      }
    });
    return localXulPendingInputStream;
  }

  public InputStream getPlaybillSelected(String paramString)
    throws Exception
  {
    JSONObject localJSONObject = new JSONObject(paramString.substring(1 + paramString.lastIndexOf('/')));
    final String str1 = localJSONObject.getString("packetId");
    final String str2 = localJSONObject.getString("categoryId");
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    ServerApiManager.i().APIGetPlaybillSelectedList(str1, str2, 9999, 0, new SccmsApiGetPlaybillSelectedList.ISccmsApiGetPlaybillSelectedListTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        localXulPendingInputStream.cancel();
        Intent localIntent = new Intent("ACTION_SHOW_ERROR_DIALOG");
        App.getAppContext().sendBroadcast(localIntent);
        Logger.e("", "APIGetPlaybillSelectedList ERROR");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, GetPlaybillSelectedListInfo paramAnonymousGetPlaybillSelectedListInfo)
      {
        if ("100".equals(paramAnonymousGetPlaybillSelectedListInfo.state))
        {
          AppKiller.getInstance().killXULActivity();
          return;
        }
        localXulPendingInputStream.setBaseStream(AppInputStream.this.buildGetPlaybillSelected(paramAnonymousGetPlaybillSelectedListInfo, str1, str2));
      }
    });
    return localXulPendingInputStream;
  }

  public InputStream getUserInfo()
  {
    final XulPendingInputStream localXulPendingInputStream = new XulPendingInputStream();
    ServerApiManager.i().ApiAAAGetUserInfo(GlobalLogic.getInstance().getWebToken(), 1, GlobalEnv.getInstance().getAAALicense(), new SccmsApiAAAGetUserInfoTask.ISccmsApiGetUserCenterInfoTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e("", "ApiAAAGetUserInfo error!!");
        localXulPendingInputStream.cancel();
        AppInputStream.access$000();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserCenterInfo paramAnonymousUserCenterInfo)
      {
        Logger.e("", "ApiAAAGetUserInfo success!!");
        GlobalLogic.getInstance().checkTokenStatus(paramAnonymousUserCenterInfo.err, paramAnonymousUserCenterInfo.status);
        UserInfo localUserInfo = GlobalLogic.getInstance().getUserInfo();
        if (localUserInfo == null)
          localUserInfo = new UserInfo();
        if (paramAnonymousUserCenterInfo.status.endsWith("0000"))
        {
          localUserInfo.vip_id = (paramAnonymousUserCenterInfo.vipId + "");
          localUserInfo.vip_end_date = paramAnonymousUserCenterInfo.vipEndDate;
          localUserInfo.vip_power = paramAnonymousUserCenterInfo.viPower;
          localUserInfo.vip_days = String.valueOf(paramAnonymousUserCenterInfo.vipEndDays);
          localUserInfo.balance = String.valueOf(paramAnonymousUserCenterInfo.balance);
        }
        GlobalLogic.getInstance().saveUserInfo();
        ByteArrayInputStream localByteArrayInputStream = AppInputStream.this.buildUserInfo(GlobalLogic.getInstance().getUserInfo(), paramAnonymousUserCenterInfo);
        localXulPendingInputStream.setBaseStream(localByteArrayInputStream);
      }
    });
    return localXulPendingInputStream;
  }

  class DayItems
    implements Comparable<DayItems>
  {
    String day = "";
    boolean isToday;
    List<GetPlaybillSelectedListInfo.Item> items = new ArrayList();

    DayItems(String arg2)
    {
      Object localObject;
      this.day = localObject;
      this.isToday = localObject.equals(SystemTimeManager.getCurrentServerDate());
    }

    public int compareTo(DayItems paramDayItems)
    {
      return paramDayItems.day.compareTo(this.day);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.AppInputStream
 * JD-Core Version:    0.6.2
 */