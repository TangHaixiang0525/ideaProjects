package com.starcor.report.pay;

import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.message.MessageHandler;
import com.starcor.mgtv.boss.WebUrlBuilder;
import com.starcor.report.Column.PublicColumn;
import com.starcor.report.ReportMessage;
import com.starcor.report.ReportUtil;
import org.json.JSONObject;

public class PayReportHelper
{
  private static String TAG = PayReportHelper.class.getSimpleName();

  public static JSONObject buildVideoInfo(VodPayInfo paramVodPayInfo, TvPayInfo paramTvPayInfo)
  {
    JSONObject localJSONObject = new JSONObject();
    if (paramVodPayInfo != null);
    try
    {
      if (!TextUtils.isEmpty(paramVodPayInfo.vid))
      {
        localJSONObject.put("vid", PublicColumn.getNullIfEmpty(paramVodPayInfo.vid));
        localJSONObject.put("ovid", PublicColumn.getNullIfEmpty(paramVodPayInfo.ovid));
        localJSONObject.put("plid", PublicColumn.getNullIfEmpty(paramVodPayInfo.plid));
        localJSONObject.put("oplid", PublicColumn.getNullIfEmpty(paramVodPayInfo.oplid));
        localJSONObject.put("cid", PublicColumn.getNullIfEmpty(paramVodPayInfo.cid));
        localJSONObject.put("def", PublicColumn.getNullIfEmpty(paramVodPayInfo.def));
        return localJSONObject;
      }
      if ((paramTvPayInfo != null) && (!TextUtils.isEmpty(paramTvPayInfo.vid)))
      {
        localJSONObject.put("vid", PublicColumn.getNullIfEmpty(paramTvPayInfo.vid));
        localJSONObject.put("cid", PublicColumn.getNullIfEmpty(paramTvPayInfo.cid));
        localJSONObject.put("lcid", PublicColumn.getNullIfEmpty(paramTvPayInfo.lcid));
        localJSONObject.put("def", PublicColumn.getNullIfEmpty(paramTvPayInfo.def));
        return localJSONObject;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return localJSONObject;
    }
    localJSONObject.put("vid", "null");
    localJSONObject.put("ovid", "null");
    localJSONObject.put("plid", "null");
    localJSONObject.put("oplid", "null");
    localJSONObject.put("cid", "null");
    localJSONObject.put("lcid", "null");
    localJSONObject.put("def", "null");
    return localJSONObject;
  }

  public static void reportBuy(String paramString1, String paramString2, VodPayInfo paramVodPayInfo, TvPayInfo paramTvPayInfo)
  {
    BuyColumn.Builder localBuilder = new BuyColumn.Builder();
    if (paramVodPayInfo != null)
      localBuilder = localBuilder.addVodInfo(paramVodPayInfo);
    while (true)
    {
      BuyColumn localBuyColumn = localBuilder.addSource(paramString2).build();
      localBuyColumn.addPageName(paramString1);
      reportData("购买事件:" + paramString1, localBuyColumn);
      return;
      if (paramTvPayInfo != null)
        localBuilder = localBuilder.addTVInfo(paramTvPayInfo);
    }
  }

  public static void reportData(String paramString, JSONObject paramJSONObject)
  {
    ReportMessage localReportMessage = new ReportMessage();
    localReportMessage.mExtData = ReportUtil.formatUrl(WebUrlBuilder.getPayReportCmsUrl(), paramJSONObject);
    localReportMessage.setReportType(1);
    if (paramString == null)
      paramString = "";
    localReportMessage.setDesc(paramString);
    MessageHandler.instance().doNotify(localReportMessage);
    Logger.i(TAG, "PayReportHelper-->" + localReportMessage.mExtData);
  }

  public static void reportOrder(String paramString1, String paramString2, String paramString3, VodPayInfo paramVodPayInfo, TvPayInfo paramTvPayInfo, BuyProductInfo paramBuyProductInfo)
  {
    OrderColumn.Builder localBuilder = new OrderColumn.Builder();
    if (paramVodPayInfo != null)
      localBuilder = localBuilder.addVodInfo(paramVodPayInfo);
    while (true)
    {
      OrderColumn localOrderColumn = localBuilder.addSource(paramString3).addBuyInfo(paramBuyProductInfo).addOrderId(paramString1).build();
      localOrderColumn.addPageName(paramString2);
      reportData("充值下单事件:" + paramString2, localOrderColumn);
      return;
      if (paramTvPayInfo != null)
        localBuilder = localBuilder.addTVInfo(paramTvPayInfo);
    }
  }

  public static void reportOrder(String paramString1, String paramString2, String paramString3, VodPayInfo paramVodPayInfo, TvPayInfo paramTvPayInfo, RechargeProductInfo paramRechargeProductInfo)
  {
    OrderColumn.Builder localBuilder = new OrderColumn.Builder();
    if (paramVodPayInfo != null)
      localBuilder = localBuilder.addVodInfo(paramVodPayInfo);
    while (true)
    {
      OrderColumn localOrderColumn = localBuilder.addSource(paramString3).addRechargeInfo(paramRechargeProductInfo).addOrderId(paramString1).build();
      localOrderColumn.addPageName(paramString2);
      reportData("购买下单:" + paramString2, localOrderColumn);
      return;
      if (paramTvPayInfo != null)
        localBuilder = localBuilder.addTVInfo(paramTvPayInfo);
    }
  }

  public static void reportRecharge(String paramString1, String paramString2, String paramString3, VodPayInfo paramVodPayInfo, TvPayInfo paramTvPayInfo)
  {
    RechargeColumn.Builder localBuilder = new RechargeColumn.Builder();
    if (paramVodPayInfo != null)
      localBuilder = localBuilder.addVodInfo(paramVodPayInfo);
    while (true)
    {
      RechargeColumn localRechargeColumn = localBuilder.addSource(paramString3).addOrderId(paramString1).build();
      localRechargeColumn.addPageName(paramString2);
      reportData("芒果币充值入口事件:" + paramString2, localRechargeColumn);
      return;
      if (paramTvPayInfo != null)
        localBuilder = localBuilder.addTVInfo(paramTvPayInfo);
    }
  }

  public static void reportResult(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    ResultColumn localResultColumn = new ResultColumn.Builder().addSource(paramString3).addOrderId(paramString1).addResult(paramString4).build();
    localResultColumn.addPageName(paramString2);
    reportData("结果上报事件:" + paramString2, localResultColumn);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.pay.PayReportHelper
 * JD-Core Version:    0.6.2
 */