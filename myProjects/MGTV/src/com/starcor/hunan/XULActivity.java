package com.starcor.hunan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.PurchaseParam;
import com.starcor.core.exception.ApplicationException;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.AppKiller;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.uilogic.InitApiData;
import com.starcor.hunan.uilogic.InitApiData.onApiOKListener;
import com.starcor.hunan.widget.CommDialog;
import com.starcor.hunan.xiaomi.ActivityUserCheckLogic;
import com.starcor.hunan.xiaomi.XiaoMiOAuthManager;
import com.starcor.hunan.xiaomi.XiaoMiOAuthManager.IXiaoMiLoginMgtv;
import com.starcor.hunan.xul.XULJSCmdHost;
import com.starcor.hunan.xul.XULJSCmdLogic;
import com.starcor.report.ReportArea;
import com.starcor.report.ReportPageInfo;
import com.starcor.report.pay.BuyProductInfo;
import com.starcor.report.pay.PayReportHelper;
import com.starcor.report.pay.RechargeProductInfo;
import com.starcor.report.pay.TvPayInfo;
import com.starcor.report.pay.VodPayInfo;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulView;
import com.starcor.xul.XulWorker.DownloadItem;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class XULActivity extends DialogActivity
{
  public static final String ACTION_SHOW_ERROR_DIALOG = "ACTION_SHOW_ERROR_DIALOG";
  public static final String ACTION_SHOW_LICENSE_DIALOG = "ACTION_SHOW_LICENSE_DIALOG";
  public static final String ACTION_SHOW_NETERROR_DIALOG = "ACTION_SHOW_NETERROR_DIALOG";
  public static final String ACTION_SHOW_TOKEN_DIALOG = "ACTION_SHOW_TOKEN_DIALOG";
  private static final String TAG = XULActivity.class.getSimpleName();
  public static final String XUL_AUTO_JUMP_PAGE = "xulAutoJumpPage";
  public static final String XUL_IS_CLOSE = "xulIsClose";
  public static final String XUL_PageId = "xulPageId";
  public static final String Xul_Data = "xulData";
  private static long _startPayTime = 0L;
  private final String MSG_ERROR = "连接超时，请重试。";
  private final String MSG_LICENSE_ERROR = "终端设备认证异常。";
  XULJSCmdLogic _jsCmdLogic;
  Intent _startIntent;
  private String code = "";
  private boolean isDataReady;
  private boolean isLoadSuccess = true;
  private CommDialog mDialog;
  String pageId = null;
  private String payPageString = "PurchaseVIP|Recharge|MgtvPurchaseMode|Payment|mgtvBrowserPay|SMSPayPage";
  BroadcastReceiver xulReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      Logger.d("xulReceiver  action=" + str1);
      if ("ACTION_SHOW_TOKEN_DIALOG".equals(str1))
      {
        String str2 = paramAnonymousIntent.getStringExtra("type");
        XULActivity.access$002(XULActivity.this, false);
        XULActivity.this.showCommonDialog("ACTION_SHOW_TOKEN_DIALOG", str2);
      }
      do
      {
        do
        {
          return;
          if ("ACTION_SHOW_LICENSE_DIALOG".equals(str1))
          {
            XULActivity.access$002(XULActivity.this, false);
            XULActivity.this.showCommonDialog("ACTION_SHOW_LICENSE_DIALOG", "");
            return;
          }
          if (!"ACTION_SHOW_NETERROR_DIALOG".equals(str1))
            break;
        }
        while (!GlobalLogic.getInstance().isAppInterfaceReady());
        XULActivity.access$002(XULActivity.this, false);
        XULActivity.this.showCommonDialog("ACTION_SHOW_NETERROR_DIALOG", "");
        return;
      }
      while (!"ACTION_SHOW_ERROR_DIALOG".equals(str1));
      XULActivity.access$002(XULActivity.this, false);
      XULActivity.this.showErrorDialog();
    }
  };

  private void _init()
  {
    this._startIntent = getIntent();
    JSONObject localJSONObject;
    String str;
    if ("LoginAndRegistration".equals(this._startIntent.getStringExtra("xulPageId")))
    {
      localJSONObject = new JSONObject();
      str = GlobalLogic.getInstance().getUserLoginMode();
    }
    try
    {
      localJSONObject.put("mode", str);
      this._startIntent.putExtra("xulData", localJSONObject.toString());
      this._jsCmdLogic = new XULJSCmdLogic(this.context, new XULJSCmdHost()
      {
        public void close()
        {
          XULActivity.this.finish();
        }

        public void finish()
        {
          XULActivity.this.finish();
        }

        public JSONObject getJSInitData()
        {
          try
          {
            JSONObject localJSONObject = new JSONObject(XULActivity.this.getStartParam("xulData"));
            return localJSONObject;
          }
          catch (Exception localException)
          {
          }
          return null;
        }

        public void hideProgressInfo()
        {
          XULActivity.this.dismissLoaddingDialog();
        }

        public void showProgressInfo()
        {
          XULActivity.this.showLoaddingDialog();
        }

        public void startActivity(Intent paramAnonymousIntent)
        {
          XULActivity.this.startActivity(paramAnonymousIntent);
        }

        public void xulExecute(String paramAnonymousString, String[] paramAnonymousArrayOfString)
        {
          XULActivity.this.xulExecute(paramAnonymousString, paramAnonymousArrayOfString);
        }

        public XulView xulFindViewById(String paramAnonymousString)
        {
          return XULActivity.this.xulFindViewById(paramAnonymousString);
        }

        public void xulFireEvent(String paramAnonymousString, Object[] paramAnonymousArrayOfObject)
        {
          XULActivity.this.xulFireEvent(paramAnonymousString, paramAnonymousArrayOfObject);
        }

        public void xulPostDelay(Runnable paramAnonymousRunnable, int paramAnonymousInt)
        {
          XULActivity.this.xulPostDelay(paramAnonymousRunnable, paramAnonymousInt);
        }
      });
      return;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  private void dealXiaoMiDailog()
  {
    GlobalLogic.getInstance().userLogout();
    XiaoMiOAuthManager.getInstance().startLogin(this, "XiaoMiUserCenter", new XiaoMiOAuthManager.IXiaoMiLoginMgtv()
    {
      public void onError()
      {
        XULActivity.this.mDialog.dismiss();
        AppKiller.getInstance().killXULActivity();
      }

      public void onSuccess()
      {
        XULActivity.this.mDialog.dismiss();
        AppKiller.getInstance().killXULActivity();
      }
    });
  }

  private static String getPaySource()
  {
    PurchaseParam localPurchaseParam = GlobalLogic.getInstance().getPurchaseParam();
    if ((localPurchaseParam != null) && (!TextUtils.isEmpty(localPurchaseParam.videoId)))
      return localPurchaseParam.getPagename();
    return "U";
  }

  private String getStartParam(String paramString)
  {
    String str = this._startIntent.getStringExtra(paramString);
    if (str == null)
      str = "";
    return str;
  }

  private static TvPayInfo getTvPayInfo()
  {
    PurchaseParam localPurchaseParam = GlobalLogic.getInstance().getPurchaseParam();
    TvPayInfo localTvPayInfo = new TvPayInfo();
    if ((localPurchaseParam != null) && (!TextUtils.isEmpty(localPurchaseParam.videoId)))
    {
      localTvPayInfo.vid = localPurchaseParam.videoId;
      localTvPayInfo.cid = localPurchaseParam.packageId;
      localTvPayInfo.lcid = localPurchaseParam.import_id;
      localTvPayInfo.def = localPurchaseParam.def;
      return localTvPayInfo;
    }
    return null;
  }

  private static VodPayInfo getVodPayInfo()
  {
    PurchaseParam localPurchaseParam = GlobalLogic.getInstance().getPurchaseParam();
    VodPayInfo localVodPayInfo = new VodPayInfo();
    if ((localPurchaseParam != null) && (!TextUtils.isEmpty(localPurchaseParam.videoId)) && (localPurchaseParam.videoType == "0"))
    {
      localVodPayInfo.vid = localPurchaseParam.videoId;
      localVodPayInfo.ovid = localPurchaseParam.index_import_id;
      localVodPayInfo.plid = localPurchaseParam.videoId;
      localVodPayInfo.oplid = localPurchaseParam.import_id;
      return localVodPayInfo;
    }
    return null;
  }

  private void initData()
  {
    String str1 = getStartParam("xulIsClose");
    String str2;
    if (!TextUtils.isEmpty(str1))
      if (str1.equals("true"))
      {
        GlobalLogic.getInstance().setIsCloseXul(true);
        Logger.i(" setIsCloseXul flag=" + str1);
        str2 = getStartParam("xulAutoJumpPage");
        if (TextUtils.isEmpty(str2))
          break label125;
        GlobalLogic.getInstance().setAutoJumpPage(str2);
      }
    while (true)
    {
      Logger.i(" setAutoJumpPage page=" + str2);
      _startPayTime = System.currentTimeMillis();
      return;
      GlobalLogic.getInstance().setIsCloseXul(false);
      break;
      GlobalLogic.getInstance().setIsCloseXul(false);
      break;
      label125: GlobalLogic.getInstance().setAutoJumpPage("");
    }
  }

  private boolean isPayWayPage(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return false;
    return "Payment|mgtvBrowserPay|MgtvPurchaseMode".contains(paramString);
  }

  private static void reportBuy(Context paramContext)
  {
    String str = "U";
    if ((paramContext instanceof DialogActivity))
      str = ((DialogActivity)paramContext).curPageInfo.page;
    PayReportHelper.reportBuy(str, getPaySource(), null, null);
  }

  private void reportLoadPayOr(JSONObject paramJSONObject)
  {
    if (paramJSONObject != null)
    {
      String str = "";
      int i = paramJSONObject.optInt("type");
      long l;
      if (1 == i)
      {
        str = "P2";
        l = System.currentTimeMillis() - _startPayTime;
        if (paramJSONObject.optInt("success") != 1)
          break label99;
      }
      label99: for (boolean bool = true; ; bool = false)
      {
        if (i != 5)
          break label105;
        reportFuncLoad(5, str, "null", l, bool, 0, "null");
        return;
        if ((2 == i) || (5 == i))
        {
          str = "P1";
          break;
        }
        if (3 != i)
          break;
        str = "P3";
        break;
      }
      label105: reportFuncLoad(1, str, "null", l, bool, 0, "null");
      return;
    }
    Logger.e("ReportService", "reportLoadPayOr params null, debug this!");
  }

  public static void reportOrder(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    String str1 = getPaySource();
    String str2 = "U";
    if ((paramContext instanceof DialogActivity))
      str2 = ((DialogActivity)paramContext).curPageInfo.page;
    try
    {
      if ("recharge".equals(paramString1))
      {
        RechargeProductInfo localRechargeProductInfo = new RechargeProductInfo();
        localRechargeProductInfo.c = paramString5;
        localRechargeProductInfo.n = paramString4;
        localRechargeProductInfo.p = "1";
        PayReportHelper.reportOrder(paramString2, str2, str1, getVodPayInfo(), getTvPayInfo(), localRechargeProductInfo);
        return;
      }
      BuyProductInfo localBuyProductInfo = new BuyProductInfo();
      localBuyProductInfo.c = paramString5;
      localBuyProductInfo.pp = paramString4;
      localBuyProductInfo.p = paramString3;
      VodPayInfo localVodPayInfo = getVodPayInfo();
      TvPayInfo localTvPayInfo = getTvPayInfo();
      PayReportHelper.reportOrder(paramString2, str2, str1, localVodPayInfo, localTvPayInfo, localBuyProductInfo);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void reportPayResult(JSONObject paramJSONObject)
  {
    String str1;
    String str2;
    if (paramJSONObject != null)
    {
      str1 = "";
      str2 = paramJSONObject.optString("pay_type");
      if (!"pay_wechat".equals(str2))
        break label59;
      str1 = "P2";
    }
    while (true)
    {
      long l = System.currentTimeMillis() - _startPayTime;
      reportFuncLoad(2, str1, paramJSONObject.optString("product_id"), l, true, 0, "null");
      return;
      label59: if ("pay_alipay".equals(str2))
      {
        str1 = "P1";
      }
      else if ("pay_web".equals(str2))
      {
        str1 = "P3";
      }
      else if ("pay_mango".equals(str2))
      {
        if (1 == paramJSONObject.optInt("start_pay"))
        {
          _startPayTime = System.currentTimeMillis();
          return;
        }
        str1 = "P4";
      }
    }
  }

  public static void reportRecharge(Context paramContext, String paramString)
  {
    String str = "U";
    if ((paramContext instanceof DialogActivity))
      str = ((DialogActivity)paramContext).curPageInfo.page;
    PayReportHelper.reportRecharge(paramString, str, getPaySource(), getVodPayInfo(), getTvPayInfo());
  }

  public static void reportResult(Context paramContext, String paramString1, String paramString2)
  {
    String str = "U";
    if ((paramContext instanceof DialogActivity))
      str = ((DialogActivity)paramContext).curPageInfo.page;
    PayReportHelper.reportResult(paramString1, str, getPaySource(), paramString2);
  }

  private void reportXulActivityPageExit()
  {
    if (!GlobalLogic.getInstance().isUserCenterPage(this))
      return;
    if ((!TextUtils.isEmpty(this.pageId)) && (this.payPageString.contains(this.pageId)))
    {
      PurchaseParam localPurchaseParam = GlobalLogic.getInstance().getPurchaseParam();
      String str1 = "";
      if (localPurchaseParam != null)
      {
        String str3 = localPurchaseParam.packageId;
        String str4 = localPurchaseParam.categoryId;
        String str5 = localPurchaseParam.videoId;
        String str6 = localPurchaseParam.videoType;
        if (!TextUtils.isEmpty(str3 + str4 + str5 + str6))
          str1 = "packetid=" + str3 + "&categoryid=" + str4 + "&videoid=" + str5 + "&videoType=" + str6;
      }
      String str2 = "";
      if (isPayWayPage(this.pageId))
      {
        if (!"Payment".equals(this.pageId))
          break label255;
        if (!"A8BAE67DF25F".equals(this.code))
          break label232;
        str2 = ReportArea.getValue("PayMentorderAlipaay");
      }
      while (true)
      {
        reportExit(this.isLoadSuccess, new String[] { str2, str1 });
        return;
        label232: if ("837D14E0B693".equals(this.code))
        {
          str2 = ReportArea.getValue("PayMentorderWechat");
          continue;
          label255: str2 = ReportArea.getValue(this.pageId);
        }
      }
    }
    reportExit(this.isLoadSuccess, null);
  }

  private void reportXulActivityPageLoad()
  {
    if (!GlobalLogic.getInstance().isUserCenterPage(this))
      return;
    if ((!TextUtils.isEmpty(this.pageId)) && (this.payPageString.contains(this.pageId)))
    {
      PurchaseParam localPurchaseParam = GlobalLogic.getInstance().getPurchaseParam();
      String str1 = "";
      if (localPurchaseParam != null)
      {
        String str3 = localPurchaseParam.packageId;
        String str4 = localPurchaseParam.categoryId;
        String str5 = localPurchaseParam.videoId;
        String str6 = localPurchaseParam.videoType;
        if (!TextUtils.isEmpty(str3 + str4 + str5 + str6))
          str1 = "packetid=" + str3 + "&categoryid=" + str4 + "&videoid=" + str5 + "&videoType=" + str6;
      }
      String str2 = "";
      if (isPayWayPage(this.pageId))
      {
        if (!"Payment".equals(this.pageId))
          break label255;
        if (!"A8BAE67DF25F".equals(this.code))
          break label232;
        str2 = ReportArea.getValue("PayMentorderAlipaay");
      }
      while (true)
      {
        reportLoad(this.isLoadSuccess, new String[] { str2, str1 });
        return;
        label232: if ("837D14E0B693".equals(this.code))
        {
          str2 = ReportArea.getValue("PayMentorderWechat");
          continue;
          label255: str2 = ReportArea.getValue(this.pageId);
        }
      }
    }
    reportLoad(this.isLoadSuccess, null);
  }

  private void showErrorDialog()
  {
    boolean bool = true;
    ApplicationException localApplicationException = new ApplicationException(this, "1002007");
    localApplicationException.setDialogType(11);
    localApplicationException.setReport(bool);
    if (!DeviceInfo.isFactoryCH());
    while (true)
    {
      localApplicationException.setShowDialog(bool);
      localApplicationException.start();
      return;
      bool = false;
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    _init();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("ACTION_SHOW_TOKEN_DIALOG");
    localIntentFilter.addAction("ACTION_SHOW_NETERROR_DIALOG");
    localIntentFilter.addAction("ACTION_SHOW_LICENSE_DIALOG");
    localIntentFilter.addAction("ACTION_SHOW_ERROR_DIALOG");
    registerReceiver(this.xulReceiver, localIntentFilter);
    this.pageId = getStartParam("xulPageId");
    this.currentXulPageId = this.pageId;
    initXul(this.pageId, true);
    Logger.i(TAG, "pageId=" + this.pageId);
    initData();
    if (GlobalLogic.getInstance().isUserCenterPage(this))
      initReportPageInfo(this.pageId);
  }

  protected void onDestroy()
  {
    if (this._jsCmdLogic != null)
      this._jsCmdLogic.destroy();
    if (this.xulReceiver != null)
      unregisterReceiver(this.xulReceiver);
    super.onDestroy();
  }

  protected void onRestart()
  {
    Logger.i("onRestart pageId=" + this.pageId);
    xulFireEvent("appEvents:OnActivityResume");
    super.onRestart();
    reportXulActivityPageLoad();
  }

  protected void onStart()
  {
    super.onStart();
  }

  protected void onStop()
  {
    super.onStop();
    reportXulActivityPageExit();
  }

  public void showCommonDialog(String paramString1, final String paramString2)
  {
    if ((this.mDialog != null) && (this.mDialog.isShowing()))
      return;
    this.mDialog = new CommDialog(this, 2131296258);
    this.mDialog.setCancelable(false);
    this.mDialog.setType(1);
    this.mDialog.setTitle("提示");
    if ((DeviceInfo.isXiaoMi()) && ((this.pageId.equals("PurchaseVIP")) || (this.pageId.equals("XiaoMiUserCenter"))))
      this.mDialog.setTitleAndDesc(" ", " ");
    if (paramString1.equals("ACTION_SHOW_LICENSE_DIALOG"))
    {
      this.mDialog.setButtonOkMsg("确定");
      this.mDialog.setMessage("终端设备认证异常。");
    }
    while (true)
    {
      this.mDialog.setNegativeButton(new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          if ((paramString2.equals("KICKED")) || (paramString2.equals("EXPIRED")))
          {
            if (DeviceInfo.isXiaoMi())
            {
              XULActivity.this.dealXiaoMiDailog();
              return;
            }
            AppKiller.getInstance().killXULActivity();
            Intent localIntent = new Intent(XULActivity.this, XULActivity.class);
            localIntent.putExtra("xulPageId", "LoginAndRegistration");
            localIntent.putExtra("xulIsClose", "false");
            localIntent.addFlags(8388608);
            XULActivity.this.startActivity(localIntent);
          }
          XULActivity.this.mDialog.dismiss();
        }
      });
      this.mDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
      {
        public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          if ((paramAnonymousKeyEvent.getAction() == 0) && (paramAnonymousInt == 4))
          {
            AppKiller.getInstance().killXULActivity();
            if ((paramString2.equals("KICKED")) || (paramString2.equals("EXPIRED")))
            {
              if (DeviceInfo.isXiaoMi())
              {
                GlobalLogic.getInstance().userLogout();
                return true;
              }
              Intent localIntent = new Intent(XULActivity.this, XULActivity.class);
              localIntent.putExtra("xulPageId", "LoginAndRegistration");
              localIntent.putExtra("xulIsClose", "false");
              localIntent.addFlags(8388608);
              XULActivity.this.startActivity(localIntent);
            }
            XULActivity.this.mDialog.dismiss();
            return true;
          }
          return false;
        }
      });
      this.mDialog.show();
      return;
      if (paramString1.equals("ACTION_SHOW_TOKEN_DIALOG"))
      {
        if (paramString2.equals("KICKED"))
        {
          this.mDialog.setButtonOkMsg("重新登录");
          this.mDialog.setMessage("尊敬的用户：\n您的帐号已在其他设备登录，您已被迫下线，如果不是您本人的操作，请尽快修改您的密码。");
          GlobalLogic.getInstance().userLogout();
          continue;
        }
        if (!paramString2.equals("EXPIRED"))
          continue;
        if (!GlobalLogic.getInstance().isUserLogined())
          break;
        this.mDialog.setButtonOkMsg("重新登录");
        this.mDialog.setMessage("尊敬的用户：\n您的账号信息已过期，请重新登录。");
        GlobalLogic.getInstance().userLogout();
        continue;
      }
      if (paramString1.equals("ACTION_SHOW_NETERROR_DIALOG"))
      {
        this.mDialog.setButtonOkMsg("确定");
        this.mDialog.setMessage("连接超时，请重试。");
      }
    }
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    Logger.i(TAG, "xulDoAction action=" + paramString1 + " type=" + paramString2 + " command" + paramString3 + " userdata=" + paramObject);
    if ("jsCmd".equals(paramString2))
      try
      {
        boolean bool = this._jsCmdLogic.doJSCmd(paramXulView, paramString3, paramObject);
        return bool;
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
        return false;
      }
    if ("backward".equals(paramString2))
    {
      finish();
      return true;
    }
    if ("usr_cmd".equals(paramString2))
    {
      if ((paramObject instanceof XulDataNode));
      for (Bundle localBundle = xulArgListToBundle((XulDataNode)paramObject); ; localBundle = new Bundle())
      {
        startActivityByCommand(paramString3, localBundle);
        return true;
      }
    }
    if ("data_report".equals(paramString2))
      while (true)
      {
        JSONObject localJSONObject;
        try
        {
          localJSONObject = new JSONObject(paramString3);
          if ("pay_success".equals(paramString1))
          {
            reportPayResult(localJSONObject);
            return true;
          }
        }
        catch (JSONException localJSONException4)
        {
          localJSONObject = new JSONObject();
          continue;
          if ("pay_view".equals(paramString1))
          {
            try
            {
              this.code = localJSONObject.getString("code");
              if (!"Payment".equals(this.pageId))
                continue;
              if ("A8BAE67DF25F".equals(this.code))
                initReportPageInfo("PayMentorderAlipaay");
            }
            catch (JSONException localJSONException3)
            {
              localJSONException3.printStackTrace();
              continue;
            }
            if (!"837D14E0B693".equals(this.code))
              continue;
            initReportPageInfo("PayMentorderWechat");
            continue;
          }
          if ("load_pay_qr".equals(paramString1))
          {
            reportLoadPayOr(localJSONObject);
            continue;
          }
          if ("zfbsq_click".equals(paramString1))
          {
            reportClick(8, "", "");
            continue;
          }
          if ("buy".equals(paramString1))
          {
            reportBuy(this);
            continue;
          }
          if ("order".equals(paramString1))
          {
            String str5 = "";
            String str6 = "";
            String str7 = "";
            Object localObject1 = "";
            Object localObject2 = "buy";
            try
            {
              str5 = localJSONObject.getString("order_id");
              if (TextUtils.isEmpty(str5))
                return true;
              str6 = localJSONObject.getString("product_id");
              str7 = localJSONObject.getString("product_price");
              String str8 = localJSONObject.getString("channel");
              localObject1 = str8;
              try
              {
                String str9 = localJSONObject.getString("type");
                localObject2 = str9;
                reportOrder(this, (String)localObject2, str5, str6, str7, (String)localObject1);
              }
              catch (Exception localException2)
              {
                localObject2 = "buy";
                continue;
              }
            }
            catch (Exception localException1)
            {
              localException1.printStackTrace();
              continue;
            }
          }
          if ("recharge".equals(paramString1))
            try
            {
              String str4 = localJSONObject.getString("order_id");
              str3 = str4;
              reportRecharge(this, str3);
            }
            catch (JSONException localJSONException2)
            {
              localJSONException2.printStackTrace();
              String str3 = null;
              continue;
            }
          if (!"result".equals(paramString1))
            continue;
        }
        try
        {
          String str2 = localJSONObject.getString("order_id");
          str1 = str2;
          Logger.i("reportResult:order_id=" + str1);
          if (!TextUtils.isEmpty(str1))
            reportResult(this, str1, "1");
        }
        catch (JSONException localJSONException1)
        {
          while (true)
          {
            localJSONException1.printStackTrace();
            String str1 = null;
          }
        }
      }
    if (("bindingNotify".equals(paramString2)) && (GlobalLogic.getInstance().isAppInterfaceReady()) && (!this.isDataReady))
    {
      if ("user_info_ready".equals(paramString3))
        xulFireEvent("appEvents:user_info_ready_ok");
      dismissLoaddingDialog();
      this.isDataReady = true;
    }
    return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
  }

  protected void xulExecute(String paramString, String[] paramArrayOfString)
  {
  }

  protected InputStream xulGetAppData(XulWorker.DownloadItem paramDownloadItem, String paramString)
  {
    Logger.i(TAG, "path=" + paramString);
    return super.xulGetAppData(paramDownloadItem, paramString);
  }

  protected void xulOnRenderIsReady()
  {
    boolean bool1 = TextUtils.isEmpty(this.pageId);
    int i = 0;
    if (!bool1)
      if (!"LoginAndRegistration".equals(this.pageId))
      {
        boolean bool2 = "NewUserCenter".equals(this.pageId);
        i = 0;
        if (!bool2);
      }
      else
      {
        if (GlobalLogic.getInstance().isAppInterfaceReady())
          break label74;
      }
    for (i = 1; i != 0; i = 0)
    {
      App.getInstance().setOnserviceOKListener(new OnServiceOK(null));
      return;
      label74: dismissLoaddingDialog();
    }
    if ((DeviceInfo.isXiaoMi()) && (!TextUtils.isEmpty(this.pageId)) && ((this.pageId.equals("PurchaseVIP")) || (this.pageId.equals("XiaoMiUserCenter"))))
      ActivityUserCheckLogic.getInstance().startXiaoMiUserCheck(this.context);
    this.isDataReady = true;
    reportXulActivityPageLoad();
    super.xulOnRenderIsReady();
  }

  private class OnApiOk
    implements InitApiData.onApiOKListener
  {
    private OnApiOk()
    {
    }

    public void onApiOk(int paramInt)
    {
      if ((paramInt == 1) && (!TextUtils.isEmpty(XULActivity.this.pageId)))
      {
        if (!"LoginAndRegistration".equals(XULActivity.this.pageId))
          break label52;
        XULActivity.this.xulRefreshBinding("webchat");
        XULActivity.this.xulRefreshBinding("thirdpart");
      }
      label52: 
      while (!"NewUserCenter".equals(XULActivity.this.pageId))
        return;
      XULActivity.this.xulRefreshBinding("user_info");
      XULActivity.this.xulRefreshBinding("movie_coupon_count");
    }

    public void onGetApiDataError()
    {
      Logger.i(XULActivity.TAG, "onGetApiDataError");
    }

    public void onNeedFinishActivity()
    {
      XULActivity.this.finish();
    }
  }

  private class OnServiceOK
    implements App.OnServiceOKListener
  {
    private OnServiceOK()
    {
    }

    public void onServiceOK()
    {
      Logger.i(XULActivity.TAG, "onServiceOK");
      new InitApiData(XULActivity.this).setOnApiOkListener(new XULActivity.OnApiOk(XULActivity.this, null));
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.XULActivity
 * JD-Core Version:    0.6.2
 */