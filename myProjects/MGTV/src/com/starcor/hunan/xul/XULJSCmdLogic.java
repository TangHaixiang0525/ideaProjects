package com.starcor.hunan.xul;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import com.starcor.config.MgtvUrl;
import com.starcor.core.define.PayChannelCode;
import com.starcor.core.domain.AAACouponUse;
import com.starcor.core.domain.AAAOrderInfo;
import com.starcor.core.domain.AAAOrderRecordList;
import com.starcor.core.domain.AAAOrderState;
import com.starcor.core.domain.AAAUseMovieCouponResult;
import com.starcor.core.domain.AuthState;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.domain.MobileCode;
import com.starcor.core.domain.PayAuthorizeStatus;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.PurchaseParam;
import com.starcor.core.domain.UserAuthV2;
import com.starcor.core.domain.UserCenterChangePwd;
import com.starcor.core.domain.UserCenterCheckReturn;
import com.starcor.core.domain.UserCenterInfo;
import com.starcor.core.domain.UserCenterLogin;
import com.starcor.core.domain.UserCenterLogout;
import com.starcor.core.domain.UserCenterRegister;
import com.starcor.core.domain.UserCenterUnbindWebChat;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.domain.UserKey;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.epgapi.params.GetUserAuthV2Params;
import com.starcor.core.event.GlobalEventNotify;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.AppKiller;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.AppInputStream;
import com.starcor.hunan.XULActivity;
import com.starcor.hunan.domain.MergeLocalReserveAndUpload;
import com.starcor.hunan.domain.UploadMediaDataTask;
import com.starcor.hunan.newUserCenter.MovieCouponsDataManager;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.hunan.widget.XULDialog;
import com.starcor.hunan.xiaomi.XiaoMiLoginTipDailog;
import com.starcor.hunan.xiaomi.XiaoMiOAuthManager;
import com.starcor.hunan.xiaomi.XiaoMiOAuthManager.IXiaoMiLoginMgtv;
import com.starcor.hunan.xiaomi.XiaoMiPayResult;
import com.starcor.hunan.xiaomi.XiaoMiUserCheck;
import com.starcor.hunan.xiaomi.XiaoMiUserCheck.IXiaoMiAccountVerifyListener;
import com.starcor.hunan.xiaomi.XiaoMiUserCheck.Status;
import com.starcor.report.ReportInfo;
import com.starcor.sccms.api.SccmsApiAAABuyProductTask.ISccmsApiAAABuyProductTaskListener;
import com.starcor.sccms.api.SccmsApiAAACouponUseTask.ISccmsApiAAACouponUseTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetOrderRecordListTask.ISccmsApiAAAGetOrderRecordListTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetOrderStateTask.ISccmsApiAAAGetOrderStateTaskListener;
import com.starcor.sccms.api.SccmsApiAAAGetUserInfoTask.ISccmsApiGetUserCenterInfoTaskListener;
import com.starcor.sccms.api.SccmsApiAAAQueryPayAuthorizeStatusTask.ISccmsApiAAAQueryPayAuthorizeStatusTaskListener;
import com.starcor.sccms.api.SccmsApiAAARechargeTask.ISccmsApiAAARechargeTaskListener;
import com.starcor.sccms.api.SccmsApiAAAUseMovieCouponTask.ISccmsApiAAAUseMovieCouponTaskListener;
import com.starcor.sccms.api.SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterChangePwdTask.ISccmsApiUserCenterChangePwdTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterCheckReturnTask.ISccmsApiCheckUserCenterReturnTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterGetMobileCodeByUserNameTask.ISccmsApiGetMobileCodeByUserNameTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterGetMobileCodeTask.ISccmsApiGetMobileMsgAuthTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterLoginTask.ISccmsApiUserCenterLoginTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterLogoutTask.ISccmsApiUserCenterLogoutTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterRegisterTask.ISccmsApiUserCenterRegisterTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterResetPwdTask.ISccmsApiUserCenterResetPwdTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterUnbindWebchatTask.ISccmsApiUserCenterUnbindWebchatTaskListener;
import com.starcor.sccms.api.SccmsApiUserCenterVerifyTokenTask.ISccmsApiUserCenterVerifyTokenTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import com.xiaomi.mitv.payment.thirdsdk.MiTVPaymentThirdManager;
import com.xiaomi.mitv.payment.thirdsdk.MiTVPaymentThirdManager.CallBackListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class XULJSCmdLogic<T extends XULJSCmdHost>
{
  public static final String BROADCAST_NAME = "name";
  public static final String BROADCAST_PARAMS = "params";
  public static final int FEEDBACK_LOGIN = 0;
  public static final int FEEDBACK_REG = 2;
  public static final int FEEDBACK_RESET_PWD = 1;
  private static final int MSG_DELAY = 4;
  private static final int MSG_KILL_XUL = 1;
  private static final int MSG_REFRESH_BUTTON = 2;
  private static final int MSG_RESET_BUTTON = 3;
  private static final String TAG = XULJSCmdLogic.class.getSimpleName();
  static final long WEBCHAT_LOGIN_TIMEOUT = 1800000L;
  public static final String XUL_ACTION_BROADCAST = "com.starcor.xul.ACTION_BROADCAST";
  private static final IntentFilter XUL_BROADCAST_RECEIVER_FILTER = new IntentFilter("com.starcor.xul.ACTION_BROADCAST");
  private Context _context;
  private BroadcastReceiver _xulBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if ("com.starcor.xul.ACTION_BROADCAST".equals(paramAnonymousIntent.getAction()))
        XULJSCmdLogic.this.handleBroadcast(paramAnonymousIntent.getExtras());
    }
  };
  private T _xulPage;
  private XulView mCaptchaButton;
  private XULJSCmdLogic<T>.StatusChecker mCheckAlipayAuthorize;
  Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
      case 1:
      case 2:
      case 3:
        do
        {
          return;
          XULJSCmdLogic.this.killXULActivity();
          GlobalLogic.getInstance().setProductList(null);
          return;
          if (XULJSCmdLogic.this.time > 0)
          {
            XULJSCmdLogic.this.mCaptchaButton.setAttr("text", "重新获取(" + XULJSCmdLogic.access$210(XULJSCmdLogic.this) + ")");
            XULJSCmdLogic.this.mHandler.sendEmptyMessageDelayed(2, 1000L);
          }
          while (true)
          {
            XULJSCmdLogic.this.mCaptchaButton.resetRender();
            return;
            XULJSCmdLogic.this.mCaptchaButton.setAttr("text", "重新获取");
            XULJSCmdLogic.this.mCaptchaButton.setEnabled(true);
          }
        }
        while (XULJSCmdLogic.this.mCaptchaButton == null);
        XULJSCmdLogic.this.mCaptchaButton.setAttr("text", "获取验证码");
        XULJSCmdLogic.this.mCaptchaButton.setEnabled(true);
        XULJSCmdLogic.this.mCaptchaButton.resetRender();
        return;
      case 4:
      }
      XULJSCmdLogic.this.xulFireEvent("appEvents:delaySuccess");
    }
  };
  private XULJSCmdLogic<T>.StatusChecker mRechargeChecker;
  private XULJSCmdLogic<T>.StatusChecker mSmsPayChecker;
  private XULJSCmdLogic<T>.StatusChecker mThirdPartLoginChecker;
  private XULJSCmdLogic<T>.StatusChecker mWebChatBindChecker;
  private XULJSCmdLogic<T>.StatusChecker mWebChatLoginChecker;
  private MovieCouponsDataManager movieCouponsDataManager = null;
  private int time = 60;

  public XULJSCmdLogic(Context paramContext, T paramT)
  {
    this._context = paramContext;
    this._xulPage = paramT;
    this._context.registerReceiver(this._xulBroadcastReceiver, XUL_BROADCAST_RECEIVER_FILTER);
  }

  private void aliPayBuyProduct(final int paramInt, final String paramString1, final String paramString2)
  {
    xulShowProgress();
    ServerApiManager.i().APIAAABuyProduct(1, GlobalEnv.getInstance().getAAALicense(), GlobalLogic.getInstance().getWebToken(), 1, paramInt, paramString2, new SccmsApiAAABuyProductTask.ISccmsApiAAABuyProductTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e("APIAAABuyProduct error!!");
        XULJSCmdLogic.this.xulHideProgress();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAOrderInfo paramAnonymousAAAOrderInfo)
      {
        GlobalLogic.getInstance().checkTokenStatus(paramAnonymousAAAOrderInfo.err, paramAnonymousAAAOrderInfo.status);
        Logger.i("APIAAABuyProduct onSuccess!!");
        XULJSCmdLogic.this.xulHideProgress();
        String str1 = "";
        if ((paramAnonymousAAAOrderInfo != null) && (paramAnonymousAAAOrderInfo.err == 0))
          str1 = paramAnonymousAAAOrderInfo.orderId;
        XULActivity.reportOrder(XULJSCmdLogic.this._context, "buy", str1, String.valueOf(paramInt), paramString1, paramString2);
        if (TextUtils.isEmpty(str1))
        {
          XULJSCmdLogic.this.xulFireEvent("appEvents:rechargeFail");
          XULActivity.reportResult(XULJSCmdLogic.this._context, str1, "0");
          return;
        }
        if ("success".equals(paramAnonymousAAAOrderInfo.orderMsg))
        {
          final String str2 = str1;
          XULJSCmdLogic.this.xulPostDelay(new Runnable()
          {
            public void run()
            {
              XULJSCmdLogic localXULJSCmdLogic = XULJSCmdLogic.this;
              String[] arrayOfString = new String[1];
              arrayOfString[0] = str2;
              localXULJSCmdLogic.xulFireEvent("appEvents:rechargeSuccess", arrayOfString);
            }
          }
          , 500);
          return;
        }
        XULActivity.reportResult(XULJSCmdLogic.this._context, str1, "0");
        XULJSCmdLogic.this.xulFireEvent("appEvents:rechargeFail", new String[] { str1 });
      }
    });
  }

  private void aliPayQueryOrder(JSONObject paramJSONObject)
  {
    String str1 = paramJSONObject.optString("order_id", "");
    if (TextUtils.isEmpty(str1))
    {
      String str2 = paramJSONObject.optString("amount", "0");
      int i = paramJSONObject.optInt("product_id", -1);
      if (i != -1)
      {
        Logger.i(TAG, "aliPayBuyProduct!");
        aliPayBuyProduct(i, str2, PayChannelCode.ALIPAY);
        return;
      }
      Logger.i(TAG, "alipayRecharge!");
      alipayRecharge(String.valueOf(str2), "", 0, PayChannelCode.ALIPAY);
      return;
    }
    Logger.i(TAG, "queryOrderResult!");
    queryOrderResult(str1);
  }

  private void alipayRecharge(final String paramString1, final String paramString2, final int paramInt, final String paramString3)
  {
    xulShowProgress();
    ServerApiManager.i().APIAAARecharge(1, GlobalEnv.getInstance().getAAALicense(), GlobalLogic.getInstance().getWebToken(), 1, paramInt, paramString3, paramString2, paramString1, new SccmsApiAAARechargeTask.ISccmsApiAAARechargeTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e(XULJSCmdLogic.TAG, "APIAAARecharge error!!");
        XULJSCmdLogic.this.xulHideProgress();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAOrderInfo paramAnonymousAAAOrderInfo)
      {
        GlobalLogic.getInstance().checkTokenStatus(paramAnonymousAAAOrderInfo.err, paramAnonymousAAAOrderInfo.status);
        Logger.i(XULJSCmdLogic.TAG, "APIAAARecharge onSuccess!!");
        XULJSCmdLogic.this.xulHideProgress();
        String str = paramString2;
        if ((paramAnonymousAAAOrderInfo != null) && (paramAnonymousAAAOrderInfo.err == 0))
          str = paramAnonymousAAAOrderInfo.orderId;
        if (TextUtils.isEmpty(paramString2))
          XULActivity.reportOrder(XULJSCmdLogic.this._context, "recharge", str, String.valueOf(paramInt), paramString1, paramString3);
        if (TextUtils.isEmpty(str))
        {
          XULActivity.reportResult(XULJSCmdLogic.this._context, str, "0");
          XULJSCmdLogic.this.xulFireEvent("appEvents:rechargeFail");
          return;
        }
        if ("success".equals(paramAnonymousAAAOrderInfo.orderMsg))
        {
          XULJSCmdLogic.this.xulFireEvent("appEvents:rechargeSuccess", new String[] { str });
          return;
        }
        XULActivity.reportResult(XULJSCmdLogic.this._context, str, "0");
        XULJSCmdLogic.this.xulFireEvent("appEvents:rechargeFail", new String[] { str });
      }
    });
  }

  private boolean checkCaptchaParams(String paramString)
  {
    if (xulFindViewById("feedback_label") == null)
      return false;
    if (!isPhoneNumber(paramString.trim()))
    {
      showFeedback(1, "请输入11位手机号");
      return false;
    }
    return true;
  }

  private boolean checkLoginParams(String paramString1, String paramString2)
  {
    if (xulFindViewById("feedback_label") == null)
      return false;
    String str1 = paramString1.trim();
    String str2 = paramString2.trim();
    if ((str1 == null) || (str1.length() <= 0))
    {
      showFeedback(0, "帐号不能为空");
      return false;
    }
    if ((str2 == null) || (str2.length() <= 0))
    {
      showFeedback(0, "密码不能为空");
      return false;
    }
    return true;
  }

  private boolean checkResetRegParams(String paramString1, String paramString2, String paramString3)
  {
    if (xulFindViewById("feedback_label") == null)
      return false;
    String str1 = paramString1.trim();
    String str2 = paramString2.trim();
    String str3 = paramString3.trim();
    if (!isPhoneNumber(str1))
    {
      showFeedback(1, "请输入11位手机号");
      return false;
    }
    if ((str2 == null) || (str2.length() <= 0))
    {
      showFeedback(1, "密码不能为空");
      return false;
    }
    if ((str3 == null) || (str3.length() <= 0))
    {
      showFeedback(1, "验证码不能为空");
      return false;
    }
    return true;
  }

  private void closeUserCenter()
  {
    Logger.i("mgtvCloseUserCenter " + GlobalLogic.getInstance().getAutoJumpToDetailedPage());
    if (GlobalLogic.getInstance().getAutoJumpToDetailedPage())
    {
      GlobalLogic.getInstance().setAutoJumpToDetailedPage(false);
      this.mHandler.sendEmptyMessageDelayed(1, 1500L);
    }
  }

  private void closeXULPage()
  {
    this._xulPage.close();
  }

  private void doInit(XulView paramXulView)
  {
    JSONObject localJSONObject = xulGetJSInitData();
    if (localJSONObject != null)
      paramXulView.getOwnerPage().setAttr("__init_data__", localJSONObject.toString());
  }

  private void doLogout()
  {
    Logger.d(TAG, " doLogout in");
    ServerApiManager.i().APIUserCenterLogout(GlobalLogic.getInstance().getWebToken(), GlobalEnv.getInstance().getAAALicense(), new SccmsApiUserCenterLogoutTask.ISccmsApiUserCenterLogoutTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.d(XULJSCmdLogic.TAG, " doLogout error");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserCenterLogout paramAnonymousUserCenterLogout)
      {
        GlobalLogic.getInstance().checkTokenStatus(paramAnonymousUserCenterLogout.err.intValue(), paramAnonymousUserCenterLogout.status);
        Logger.d(XULJSCmdLogic.TAG, " doLogout success");
      }
    });
    GlobalLogic.getInstance().userLogout();
    GlobalEventNotify.getInstance().sendUserLoginAction(App.getAppContext(), "logout");
  }

  private void enableButtonDelay(int paramInt)
  {
    resetFeedback();
    this.time = paramInt;
    if ((this.mCaptchaButton != null) && (this.mHandler != null))
      this.mHandler.sendEmptyMessageDelayed(2, 1000L);
  }

  private void execute(String paramString, String[] paramArrayOfString)
  {
    this._xulPage.xulExecute(paramString, paramArrayOfString);
  }

  private void finish()
  {
    this._xulPage.finish();
  }

  private void getXiaomiOrderInfo(final int paramInt, final String paramString1, final String paramString2, final Long paramLong)
  {
    xulShowProgress();
    ServerApiManager.i().APIAAABuyProduct(0, GlobalEnv.getInstance().getAAALicense(), GlobalLogic.getInstance().getWebToken(), 1, paramInt, "A25DA1F7F25F", new SccmsApiAAABuyProductTask.ISccmsApiAAABuyProductTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.d(XULJSCmdLogic.TAG, " getXiaomiOrderInfo APIAAABuyProduct error");
        XULJSCmdLogic.this.xulHideProgress();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAOrderInfo paramAnonymousAAAOrderInfo)
      {
        XULJSCmdLogic.this.xulHideProgress();
        if (paramAnonymousAAAOrderInfo == null)
          return;
        GlobalLogic.getInstance().checkTokenStatus(paramAnonymousAAAOrderInfo.err, paramAnonymousAAAOrderInfo.status);
        Logger.d(XULJSCmdLogic.TAG, " getXiaomiOrderInfo APIAAABuyProduct success");
        if (!TextUtils.isEmpty(paramAnonymousAAAOrderInfo.orderId))
        {
          XULJSCmdLogic.this.xiaomiPay(paramAnonymousAAAOrderInfo.orderId, paramString1, paramString2, paramInt + "", paramLong);
          return;
        }
        Logger.e(XULJSCmdLogic.TAG, " getXiaomiOrderInfo APIAAABuyProduct fail orderId is null");
      }
    });
  }

  private void handleBroadcast(Bundle paramBundle)
  {
    try
    {
      String str = paramBundle.getString("name");
      String[] arrayOfString = paramBundle.getStringArray("params");
      xulFireEvent("appEvents:Broadcast:" + str, arrayOfString);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void isNeedBuy(final int paramInt, final String paramString1, final String paramString2, final Long paramLong)
  {
    Logger.i("isNeedBuy()");
    PurchaseParam localPurchaseParam = GlobalLogic.getInstance().getPurchaseParam();
    if ((localPurchaseParam != null) && (localPurchaseParam.autoJump) && (!TextUtils.isEmpty(localPurchaseParam.videoId)))
    {
      ServerApiManager.i().APIGetUserAuthV2(new GetUserAuthV2Params(localPurchaseParam.videoId, localPurchaseParam.videoType, GlobalLogic.getInstance().getQuality()), new SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          Logger.e("APIGetUserAuthV2 error!");
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserAuthV2 paramAnonymousUserAuthV2)
        {
          Logger.i("APIGetUserAuthV2 success!");
          if (paramAnonymousUserAuthV2.state.state == 0)
          {
            XULJSCmdLogic.this.finish();
            return;
          }
          XULJSCmdLogic.this.getXiaomiOrderInfo(paramInt, paramString1, paramString2, paramLong);
        }
      });
      return;
    }
    getXiaomiOrderInfo(paramInt, paramString1, paramString2, paramLong);
  }

  private boolean isPhoneNumber(String paramString)
  {
    if ((paramString == null) || (paramString.length() != 11))
      return false;
    for (int i = 0; ; i++)
    {
      if (i >= paramString.length())
        break label42;
      if (!Character.isDigit(paramString.charAt(i)))
        break;
    }
    label42: return true;
  }

  private void killXULActivity()
  {
    Logger.d(TAG, "killXULActivity()");
    if (GlobalLogic.getInstance().isUserLogined())
    {
      ServerApiManager.i().ApiAAAGetUserInfo(GlobalLogic.getInstance().getWebToken(), 1, GlobalEnv.getInstance().getAAALicense(), new SccmsApiAAAGetUserInfoTask.ISccmsApiGetUserCenterInfoTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          AppKiller.getInstance().killXULActivity();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserCenterInfo paramAnonymousUserCenterInfo)
        {
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
          App.getAppContext().sendBroadcast(new Intent("get_user_info_success"));
          AppKiller.getInstance().killXULActivity();
        }
      });
      return;
    }
    AppKiller.getInstance().killXULActivity();
  }

  private void mgtvDoChangePwd(String paramString1, String paramString2)
  {
    Logger.i(" mgtvDoChangePwd");
    String str1 = GlobalLogic.getInstance().getUserInfo().account;
    String str2 = paramString2.trim();
    String str3 = paramString1.trim();
    String str4 = GlobalLogic.getInstance().getWebToken();
    String str5 = GlobalEnv.getInstance().getAAALicense();
    Logger.i(" mgtvDoChangePwd name=" + str1);
    Logger.i(" mgtvDoChangePwd oldpwd=" + str3);
    Logger.i(" mgtvDoChangePwd newpwd=" + str2);
    if ((TextUtils.isEmpty(str3)) || (TextUtils.isEmpty(str2)))
    {
      xulFireEvent("appEvents:changpwdError", new String[] { "密码不能为空" });
      xulHideProgress();
      return;
    }
    ServerApiManager.i().APIUserCenterChangPwd(str1, paramString2, str3, str2, str4, str5, new SccmsApiUserCenterChangePwdTask.ISccmsApiUserCenterChangePwdTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.i(" mgtvDoChangePwd fail");
        XULJSCmdLogic.this.xulFireEvent("appEvents:changpwdError", new String[] { "更改密码失败" });
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserCenterChangePwd paramAnonymousUserCenterChangePwd)
      {
        Logger.i(" mgtvDoChangePwd success result=" + paramAnonymousUserCenterChangePwd.toString());
        GlobalLogic.getInstance().checkTokenStatus(paramAnonymousUserCenterChangePwd.err, paramAnonymousUserCenterChangePwd.status);
        if ((paramAnonymousUserCenterChangePwd != null) && (paramAnonymousUserCenterChangePwd.status != null))
        {
          if (paramAnonymousUserCenterChangePwd.status.equals("00000"))
          {
            XULJSCmdLogic.this.xulFireEvent("appEvents:changpwdSuccess", new String[] { "更改密码成功" });
            new Handler().postDelayed(new Runnable()
            {
              public void run()
              {
                XULJSCmdLogic.this.xulFireEvent("appEvents:changpwdSuccessDismissDialog", new String[] { "" });
              }
            }
            , 1000L);
            return;
          }
          String str = paramAnonymousUserCenterChangePwd.msg;
          XULJSCmdLogic.this.xulFireEvent("appEvents:changpwdError", new String[] { str });
          return;
        }
        XULJSCmdLogic.this.xulFireEvent("appEvents:changpwdError", new String[] { "更改密码失败" });
      }
    });
  }

  private void mgtvDoLogin(final String paramString1, String paramString2, final String paramString3, final String paramString4)
  {
    Logger.d(TAG, "login: " + paramString1 + ", " + paramString2);
    if (!checkLoginParams(paramString1, paramString2))
      return;
    xulShowProgress();
    ServerApiManager.i().APIUserCenterLogin(paramString1, paramString2, GlobalEnv.getInstance().getAAALicense(), new SccmsApiUserCenterLoginTask.ISccmsApiUserCenterLoginTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.d(XULJSCmdLogic.TAG, "APIUserCenterLogin onError!");
        GlobalLogic.getInstance().setMgtvLoginUserName(paramString1);
        XULJSCmdLogic.this.xulFireEvent("appEvents:loginError");
        XULJSCmdLogic.this.showFeedback(0, "连接超时，请稍后重试");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserCenterLogin paramAnonymousUserCenterLogin)
      {
        Logger.d(XULJSCmdLogic.TAG, "APIUserCenterLogin onSuccess!");
        GlobalLogic.getInstance().setMgtvLoginUserName(paramString1);
        if (paramAnonymousUserCenterLogin.err != 0)
        {
          Logger.d(XULJSCmdLogic.TAG, "login failed!");
          XULJSCmdLogic.this.showFeedback(0, paramAnonymousUserCenterLogin.msg);
          XULJSCmdLogic.this.xulFireEvent("appEvents:loginFailed");
          return;
        }
        XULJSCmdLogic.this.resetFeedback();
        Logger.d(XULJSCmdLogic.TAG, "login success!");
        UserInfo localUserInfo = new UserInfo();
        localUserInfo.loginMode = "mgtv";
        localUserInfo.user_id = paramAnonymousUserCenterLogin.uid;
        localUserInfo.name = paramAnonymousUserCenterLogin.nickname;
        localUserInfo.account = paramAnonymousUserCenterLogin.loginaccount;
        localUserInfo.state = paramAnonymousUserCenterLogin.state;
        localUserInfo.mobile = paramAnonymousUserCenterLogin.mobile;
        localUserInfo.status = paramAnonymousUserCenterLogin.status;
        localUserInfo.web_token = paramAnonymousUserCenterLogin.ticket;
        Logger.d(XULJSCmdLogic.TAG, "login success! result.ticket=" + paramAnonymousUserCenterLogin.ticket);
        localUserInfo.rtype = paramAnonymousUserCenterLogin.rtype;
        localUserInfo.avatar = paramAnonymousUserCenterLogin.avatar;
        localUserInfo.wechat_type = paramAnonymousUserCenterLogin.wechat_type;
        localUserInfo.email = paramAnonymousUserCenterLogin.email;
        GlobalLogic.getInstance().userLogin(localUserInfo);
        new UploadMediaDataTask().executeSync();
        MergeLocalReserveAndUpload.getInstance().mergeReservesAndUpload();
        PurchaseParam localPurchaseParam = GlobalLogic.getInstance().getPurchaseParam();
        if ((localPurchaseParam != null) && (localPurchaseParam.autoJump))
          if (!TextUtils.isEmpty(localPurchaseParam.videoId))
            ServerApiManager.i().APIGetUserAuthV2(new GetUserAuthV2Params(localPurchaseParam.videoId, localPurchaseParam.videoType, GlobalLogic.getInstance().getQuality()), new XULJSCmdLogic.SccmsApiGetUserAuthV2TaskListener(XULJSCmdLogic.this));
        while (true)
        {
          GlobalEventNotify.getInstance().sendUserLoginAction(App.getAppContext(), "login");
          return;
          GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
          XULJSCmdLogic.this.xulFireEvent("appEvents:autoJumpToPurchaseVIP");
          continue;
          if ((!TextUtils.isEmpty(GlobalLogic.getInstance().getAutoJumpPage())) && (GlobalLogic.getInstance().getAutoJumpPage().equals("CouponCard")))
          {
            XULJSCmdLogic.this.xulFireEvent("appEvents:autoJumpToCouponCard");
          }
          else if (GlobalLogic.getInstance().getIsCloseXul())
          {
            XULJSCmdLogic.this.killXULActivity();
          }
          else
          {
            XULJSCmdLogic.this.xulFireEvent("appEvents:loginSuccess");
            new HashMap();
            if (XULJSCmdLogic.this.movieCouponsDataManager == null)
              XULJSCmdLogic.access$1102(XULJSCmdLogic.this, new MovieCouponsDataManager(XULJSCmdLogic.this._context));
            HashMap localHashMap = XULJSCmdLogic.this.movieCouponsDataManager.getMovieCouponsInfo(paramString1);
            if (!paramString1.equals(localHashMap.get("user_id")))
              XULJSCmdLogic.this.addMovieCouponInfo(paramString1, paramString3, paramString4);
          }
        }
      }
    });
  }

  private void mgtvGetOrderState(String paramString)
  {
    ServerApiManager.i().APIAAAGetOrderState(GlobalEnv.getInstance().getAAALicense(), GlobalLogic.getInstance().getWebToken(), 1, paramString, "", new SccmsApiAAAGetOrderStateTask.ISccmsApiAAAGetOrderStateTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAOrderState paramAnonymousAAAOrderState)
      {
        GlobalLogic.getInstance().checkTokenStatus(paramAnonymousAAAOrderState.err, paramAnonymousAAAOrderState.status);
      }
    });
  }

  private void mgtvRegisterAccount(final String paramString1, final String paramString2, String paramString3)
  {
    if (!checkResetRegParams(paramString1, paramString2, paramString3))
      return;
    ServerApiManager.i().APIUserCenterRegistUser(paramString1, paramString2, paramString3, GlobalEnv.getInstance().getAAALicense(), new SccmsApiUserCenterRegisterTask.ISccmsApiUserCenterRegisterTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        String str = XULJSCmdLogic.TAG;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramString1;
        Logger.e(str, String.format("APIUserCenterRegisterUser error! %s", arrayOfObject));
        XULJSCmdLogic.this.xulFireEvent("appEvents:registerAccountError");
        XULJSCmdLogic.this.showFeedback(2, "连接超时，请稍后重试");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserCenterRegister paramAnonymousUserCenterRegister)
      {
        if (paramAnonymousUserCenterRegister.err != 0)
        {
          String str2 = XULJSCmdLogic.TAG;
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = paramString1;
          Logger.d(str2, String.format("APIUserCenterRegisterUser failed! %s", arrayOfObject2));
          XULJSCmdLogic.this.showFeedback(2, paramAnonymousUserCenterRegister.msg);
          XULJSCmdLogic.this.xulFireEvent("appEvents:registerAccountFailed");
          return;
        }
        XULJSCmdLogic.this.resetFeedback();
        String str1 = XULJSCmdLogic.TAG;
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = paramString1;
        Logger.d(str1, String.format("APIUserCenterRegisterUser success! %s", arrayOfObject1));
        XULJSCmdLogic.this.mgtvDoLogin(paramString1, paramString2, "0", "block");
        XULJSCmdLogic.this.xulFireEvent("appEvents:registerAccountSuccess");
      }
    });
  }

  private void mgtvResetPwd(String paramString1, String paramString2, String paramString3)
  {
    if (!checkResetRegParams(paramString1, paramString2, paramString3))
      return;
    ServerApiManager.i().APIUserCenterResetPwd(paramString1, paramString2, paramString3, GlobalEnv.getInstance().getAAALicense(), new SccmsApiUserCenterResetPwdTask.ISccmsApiUserCenterResetPwdTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        XULJSCmdLogic.this.showFeedback(1, "连接超时，请稍后重试");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserCenterChangePwd paramAnonymousUserCenterChangePwd)
      {
        if (paramAnonymousUserCenterChangePwd.err != 0)
        {
          XULJSCmdLogic.this.showFeedback(1, paramAnonymousUserCenterChangePwd.msg);
          return;
        }
        XULJSCmdLogic.this.showFeedback(1, "找回密码成功");
      }
    });
  }

  private void mgtvSmsPay(int paramInt, String paramString1, String paramString2)
  {
    Logger.i(TAG, "mgtvSmsPay product_id=" + paramInt + ",mobile=" + paramString1 + ",channel=" + paramString2);
    String str = paramString1.trim().replaceAll("\\s*", "");
    if (!isPhoneNumber(str))
    {
      xulFireEvent("appEvents:mgtvSmsPayFail", new String[] { "请输入11位手机号" });
      return;
    }
    ServerApiManager.i().APIAAABuySmsProduct(GlobalEnv.getInstance().getAAALicense(), GlobalLogic.getInstance().getWebToken(), 1, paramInt, str, paramString2, new SccmsApiAAABuyProductTask.ISccmsApiAAABuyProductTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e("APIAAABuySmsProduct error!!");
        XULJSCmdLogic.this.xulHideProgress();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAOrderInfo paramAnonymousAAAOrderInfo)
      {
        GlobalLogic.getInstance().checkTokenStatus(paramAnonymousAAAOrderInfo.err, paramAnonymousAAAOrderInfo.status);
        if (paramAnonymousAAAOrderInfo.err == 0)
        {
          if (TextUtils.isEmpty(paramAnonymousAAAOrderInfo.mobileType))
          {
            XULJSCmdLogic.this.xulFireEvent("appEvents:mgtvSmsPayFail", new String[] { "请核对您的手机号码是否正确" });
            return;
          }
          XULJSCmdLogic localXULJSCmdLogic2 = XULJSCmdLogic.this;
          String[] arrayOfString2 = new String[3];
          arrayOfString2[0] = paramAnonymousAAAOrderInfo.orderId;
          arrayOfString2[1] = paramAnonymousAAAOrderInfo.orderType;
          arrayOfString2[2] = paramAnonymousAAAOrderInfo.mobileType;
          localXULJSCmdLogic2.xulFireEvent("appEvents:mgtvSmsPay", arrayOfString2);
          return;
        }
        XULJSCmdLogic localXULJSCmdLogic1 = XULJSCmdLogic.this;
        String[] arrayOfString1 = new String[1];
        arrayOfString1[0] = paramAnonymousAAAOrderInfo.msg;
        localXULJSCmdLogic1.xulFireEvent("appEvents:mgtvSmsPayFail", arrayOfString1);
      }
    });
  }

  private void mgtvUnbindWebchat()
  {
    Logger.d(TAG, " mgtvUnbindWebchat in");
    ServerApiManager.i().APIUserCenterUnbindWebchatTask(GlobalEnv.getInstance().getAAALicense(), GlobalLogic.getInstance().getWebToken(), new SccmsApiUserCenterUnbindWebchatTask.ISccmsApiUserCenterUnbindWebchatTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        XULJSCmdLogic.this.xulFireEvent("appEvents:unbindWebchatFailed");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserCenterUnbindWebChat paramAnonymousUserCenterUnbindWebChat)
      {
        if (paramAnonymousUserCenterUnbindWebChat.operation.equals("success"))
        {
          ServerApiManager.i().APIUserCenterVerifyToken(GlobalLogic.getInstance().getWebToken(), GlobalEnv.getInstance().getAAALicense(), new SccmsApiUserCenterVerifyTokenTask.ISccmsApiUserCenterVerifyTokenTaskListener()
          {
            public void onError(ServerApiTaskInfo paramAnonymous2ServerApiTaskInfo, ServerApiCommonError paramAnonymous2ServerApiCommonError)
            {
              Logger.i("APIUserCenterVerifyToken onError");
            }

            public void onSuccess(ServerApiTaskInfo paramAnonymous2ServerApiTaskInfo, UserCenterLogin paramAnonymous2UserCenterLogin)
            {
              GlobalLogic.getInstance().checkTokenStatus(paramAnonymous2UserCenterLogin.err, paramAnonymous2UserCenterLogin.status);
              if (paramAnonymous2UserCenterLogin.err == 0)
              {
                UserInfo localUserInfo = new UserInfo();
                localUserInfo.loginMode = "mgtv";
                localUserInfo.user_id = String.valueOf(paramAnonymous2UserCenterLogin.uid);
                localUserInfo.expires_in = paramAnonymous2UserCenterLogin.expire;
                localUserInfo.name = paramAnonymous2UserCenterLogin.nickname;
                localUserInfo.account = paramAnonymous2UserCenterLogin.loginaccount;
                localUserInfo.mobile = paramAnonymous2UserCenterLogin.mobile;
                localUserInfo.status = paramAnonymous2UserCenterLogin.status;
                localUserInfo.web_token = paramAnonymous2UserCenterLogin.ticket;
                localUserInfo.avatar = paramAnonymous2UserCenterLogin.avatar;
                localUserInfo.wechat_type = paramAnonymous2UserCenterLogin.wechat_type;
                localUserInfo.rtype = paramAnonymous2UserCenterLogin.rtype;
                localUserInfo.email = paramAnonymous2UserCenterLogin.email;
                if (!TextUtils.isEmpty(paramAnonymous2UserCenterLogin.ticket))
                  GlobalLogic.getInstance().userLogin(localUserInfo);
                XULJSCmdLogic.this.xulFireEvent("appEvents:unbindWebchatSucessed");
                return;
              }
              Logger.i("APIUserCenterVerifyToken failed");
              XULJSCmdLogic.this.xulFireEvent("appEvents:unbindWebchatFailed");
            }
          });
          return;
        }
        Logger.i("!!! APIUserCenterVerifyToken failed");
        XULJSCmdLogic.this.xulFireEvent("appEvents:unbindWebchatFailed");
      }
    });
  }

  private void mgtvUserCouponCard(String paramString)
  {
    String str1 = GlobalEnv.getInstance().getAAALicense();
    if (TextUtils.isEmpty(paramString))
    {
      xulFireEvent("appEvents:mgtvUserCouponCardFail", new String[] { "兑换卡密码不能为空" });
      return;
    }
    String str2 = paramString.trim().replaceAll("\\s*", "");
    if ((!TextUtils.isDigitsOnly(str2)) || (str2.length() != 16))
    {
      xulFireEvent("appEvents:mgtvUserCouponCardFail", new String[] { "无效的兑换密码! 请您正确输入16位数字兑换密码，不要键入其他字符。" });
      return;
    }
    ServerApiManager.i().APIAAACouponUseTask(str1, str2, new SccmsApiAAACouponUseTask.ISccmsApiAAACouponUseTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        XULJSCmdLogic.this.xulFireEvent("appEvents:mgtvUserCouponCardFail", new String[] { "兑换卡使用失败" });
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAACouponUse paramAnonymousAAACouponUse)
      {
        GlobalLogic.getInstance().checkTokenStatus(paramAnonymousAAACouponUse.err, paramAnonymousAAACouponUse.status);
        if (paramAnonymousAAACouponUse.err == 0)
        {
          if ((!TextUtils.isEmpty(paramAnonymousAAACouponUse.start_date)) && (paramAnonymousAAACouponUse.start_date.contains("-")))
            paramAnonymousAAACouponUse.start_date = paramAnonymousAAACouponUse.start_date.replace('-', '.');
          if ((!TextUtils.isEmpty(paramAnonymousAAACouponUse.end_date)) && (paramAnonymousAAACouponUse.end_date.contains("-")))
            paramAnonymousAAACouponUse.end_date = paramAnonymousAAACouponUse.end_date.replace('-', '.');
          XULJSCmdLogic localXULJSCmdLogic2 = XULJSCmdLogic.this;
          String[] arrayOfString2 = new String[3];
          arrayOfString2[0] = paramAnonymousAAACouponUse.days;
          arrayOfString2[1] = paramAnonymousAAACouponUse.start_date;
          arrayOfString2[2] = paramAnonymousAAACouponUse.end_date;
          localXULJSCmdLogic2.xulFireEvent("appEvents:mgtvUserCouponCardSuccess", arrayOfString2);
          return;
        }
        if (!TextUtils.isEmpty(paramAnonymousAAACouponUse.msg))
        {
          XULJSCmdLogic localXULJSCmdLogic1 = XULJSCmdLogic.this;
          String[] arrayOfString1 = new String[1];
          arrayOfString1[0] = paramAnonymousAAACouponUse.msg;
          localXULJSCmdLogic1.xulFireEvent("appEvents:mgtvUserCouponCardFail", arrayOfString1);
          return;
        }
        XULJSCmdLogic.this.xulFireEvent("appEvents:mgtvUserCouponCardFail", new String[] { "兑换卡使用失败" });
      }
    });
  }

  private void mgtvWechatPay()
  {
    Intent localIntent = new Intent(this._context, ActivityAdapter.getInstance().getWebActivity());
    localIntent.putExtra("Cmd", "Cmd");
    MetadataInfo localMetadataInfo = new MetadataInfo();
    String str = MgtvUrl.getWechatUrl();
    Logger.i("mUrl=" + str);
    localMetadataInfo.url = str;
    localMetadataInfo.action_type = "web";
    localIntent.putExtra("MetaDataInfo", localMetadataInfo);
    localIntent.setFlags(8388608);
    startActivity(localIntent);
  }

  private void queryOrderResult(final String paramString)
  {
    xulShowProgress();
    ServerApiManager.i().APIAAAGetOrderState(GlobalEnv.getInstance().getAAALicense(), GlobalLogic.getInstance().getWebToken(), 1, paramString, "", new SccmsApiAAAGetOrderStateTask.ISccmsApiAAAGetOrderStateTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e(XULJSCmdLogic.TAG, "APIAAAGetOrderState error!");
        XULJSCmdLogic.this.xulHideProgress();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAOrderState paramAnonymousAAAOrderState)
      {
        XULJSCmdLogic.this.xulHideProgress();
        if (paramAnonymousAAAOrderState == null)
        {
          Logger.e(XULJSCmdLogic.TAG, "APIAAAGetOrderState failed! result null");
          return;
        }
        if ((paramAnonymousAAAOrderState.err == 0) && (paramAnonymousAAAOrderState.orderStatus.equals("FINISH")))
        {
          Logger.d(XULJSCmdLogic.TAG, "APIAAAGetOrderState success!");
          XULJSCmdLogic localXULJSCmdLogic = XULJSCmdLogic.this;
          String[] arrayOfString = new String[1];
          arrayOfString[0] = paramString;
          localXULJSCmdLogic.xulFireEvent("appEvents:getPaySuccess", arrayOfString);
          return;
        }
        Logger.e(XULJSCmdLogic.TAG, "APIAAAGetOrderState failed!");
      }
    });
  }

  private void requestCaptcha(String paramString)
  {
    this.mCaptchaButton = xulFindViewById("reg_captcha");
    if (!checkCaptchaParams(paramString))
    {
      this.mHandler.sendEmptyMessage(3);
      return;
    }
    enableButtonDelay(60);
    ServerApiManager.i().APIUserCenterGetMobileCode(paramString, GlobalEnv.getInstance().getAAALicense(), new SccmsApiUserCenterGetMobileCodeTask.ISccmsApiGetMobileMsgAuthTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e(XULJSCmdLogic.TAG, "获取验证码失败   onError: " + paramAnonymousServerApiCommonError.toString());
        XULJSCmdLogic.this.showFeedback(2, "连接超时，请稍后重试");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, MobileCode paramAnonymousMobileCode)
      {
        if (paramAnonymousMobileCode.err != 0)
        {
          XULJSCmdLogic.this.showFeedback(2, paramAnonymousMobileCode.msg);
          XULJSCmdLogic.this.mHandler.removeMessages(2);
          XULJSCmdLogic.this.mHandler.sendEmptyMessage(3);
        }
      }
    });
  }

  private void requestCaptchaByName(String paramString)
  {
    this.mCaptchaButton = xulFindViewById("pwd_captcha");
    if (!checkCaptchaParams(paramString))
    {
      this.mHandler.sendEmptyMessage(3);
      return;
    }
    enableButtonDelay(60);
    ServerApiManager.i().APIUserCenterGetMobileCodeByUserName(paramString, GlobalEnv.getInstance().getAAALicense(), new SccmsApiUserCenterGetMobileCodeByUserNameTask.ISccmsApiGetMobileCodeByUserNameTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e(XULJSCmdLogic.TAG, "获取验证码失败   onError: " + paramAnonymousServerApiCommonError.toString());
        XULJSCmdLogic.this.showFeedback(1, "连接超时，请稍后重试");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, MobileCode paramAnonymousMobileCode)
      {
        if (paramAnonymousMobileCode.err != 0)
        {
          XULJSCmdLogic.this.showFeedback(1, paramAnonymousMobileCode.msg);
          XULJSCmdLogic.this.mHandler.removeMessages(2);
          XULJSCmdLogic.this.mHandler.sendEmptyMessage(3);
        }
      }
    });
  }

  private void requestConsumptionList(int paramInt1, final int paramInt2)
  {
    String str1 = GlobalLogic.getInstance().getWebToken();
    String str2 = GlobalEnv.getInstance().getAAALicense();
    if (paramInt1 < 1)
      paramInt1 = 1;
    ServerApiManager localServerApiManager = ServerApiManager.i();
    SccmsApiAAAGetOrderRecordListTask.ISccmsApiAAAGetOrderRecordListTaskListener local5 = new SccmsApiAAAGetOrderRecordListTask.ISccmsApiAAAGetOrderRecordListTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAOrderRecordList paramAnonymousAAAOrderRecordList)
      {
        GlobalLogic.getInstance().checkTokenStatus(paramAnonymousAAAOrderRecordList.err, paramAnonymousAAAOrderRecordList.status);
        if (paramInt2 == 1)
          AppInputStream.getInstance().buildPurchaseInfo(paramAnonymousAAAOrderRecordList);
        while (true)
        {
          XULJSCmdLogic.this.xulFireEvent("appEvents:requestConsumptionListSuccess");
          return;
          if (paramInt2 == 2)
            AppInputStream.getInstance().buildRechargeInfo(paramAnonymousAAAOrderRecordList);
        }
      }
    };
    localServerApiManager.APIAAAGetOrderRecordList(str2, str1, 1, paramInt1, 4, paramInt2, local5);
  }

  private void resetFeedback()
  {
    XulView localXulView = xulFindViewById("feedback_label");
    if (localXulView != null)
    {
      localXulView.setAttr("text", "");
      localXulView.setStyle("display", "none");
      localXulView.resetRender();
    }
  }

  private void showFeedback(int paramInt, String paramString)
  {
    XulView localXulView = xulFindViewById("feedback_label");
    if (localXulView == null)
      return;
    int i = 523;
    switch (paramInt)
    {
    default:
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      localXulView.setAttr("y", i + "");
      localXulView.setAttr("text", paramString);
      localXulView.setStyle("font-color", "FFFF0000");
      localXulView.setStyle("display", "block");
      localXulView.resetRender();
      return;
      i = 523;
      continue;
      i = 640;
    }
  }

  private void showXULDialog(String paramString, JSONObject paramJSONObject)
  {
    XULDialog localXULDialog = new XULDialog(this._context, paramString, paramJSONObject);
    if (paramString.equals("WebchatBind"))
      localXULDialog.setIsDealBackKey(true);
    while (true)
    {
      localXULDialog.show();
      return;
      localXULDialog.setIsDealBackKey(false);
    }
  }

  private void startActivity(Intent paramIntent)
  {
    this._xulPage.startActivity(paramIntent);
  }

  private void startCheckAlipayAuthorizeResult()
  {
    this.mCheckAlipayAuthorize = new StatusChecker("CHECKER_ALIPAY_AUTHORIZE", "");
    this.mCheckAlipayAuthorize.run();
  }

  private void startCheckRechargeResult(String paramString)
  {
    this.mRechargeChecker = new StatusChecker("CHECKER_TYPE_RECHARGE", paramString);
    this.mRechargeChecker.run();
  }

  private void startCheckShortCodeResult(String paramString)
  {
    this.mRechargeChecker = new StatusChecker("CHECKER_TYPE_SHORT_CODE", paramString);
    this.mRechargeChecker.run();
  }

  private void startCheckSmsPayResult(String paramString)
  {
    this.mSmsPayChecker = new StatusChecker("CHECKER_TYPE_SMS_PAY", paramString);
    this.mSmsPayChecker.run();
  }

  private void startCheckThirdPartLoginResult(String paramString)
  {
    this.mThirdPartLoginChecker = new StatusChecker("CHECKER_TYPE_THIRD_PART_LOGIN", paramString);
    this.mThirdPartLoginChecker.run();
  }

  private void startCheckWebChatBindResult(String paramString)
  {
    this.mWebChatBindChecker = new StatusChecker("CHECKER_TYPE_WEBCHAT_BIND", paramString);
    this.mWebChatBindChecker.run();
  }

  private void startCheckWebChatLoginResult(String paramString)
  {
    this.mWebChatLoginChecker = new StatusChecker("CHECKER_TYPE_WEBCHAT_LOGIN", paramString);
    this.mWebChatLoginChecker.run();
  }

  private void startCheckXiaomiAccount(final int paramInt, final String paramString1, final String paramString2, final Long paramLong)
  {
    new XiaoMiUserCheck().start(new XiaoMiUserCheck.IXiaoMiAccountVerifyListener()
    {
      public void onUserCheckFinish(final XiaoMiUserCheck.Status paramAnonymousStatus)
      {
        Logger.i("startCheckXiaomiAccount onUserCheckFinish " + paramAnonymousStatus);
        XULJSCmdLogic.this.xulHideProgress();
        if ((paramAnonymousStatus == XiaoMiUserCheck.Status.FAILED) || (paramAnonymousStatus == XiaoMiUserCheck.Status.NONE))
          new XiaoMiLoginTipDailog(XULJSCmdLogic.this._context, new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
            {
              Logger.i("startCheckXiaomiAccount onClick");
              XiaoMiOAuthManager.getInstance().startLogin(XULJSCmdLogic.this._context, "", new XiaoMiOAuthManager.IXiaoMiLoginMgtv()
              {
                public void onError()
                {
                  Logger.i(" XiaoMiLoginTipDailog  startLogin onError()");
                }

                public void onSuccess()
                {
                  Logger.i(" XiaoMiLoginTipDailog  startLogin onSuccess()");
                  XULJSCmdLogic.this.xulFireEvent("appEvents:refreshUser");
                  if (XULJSCmdLogic.17.this.val$product_id != -1)
                    XULJSCmdLogic.this.isNeedBuy(XULJSCmdLogic.17.this.val$product_id, XULJSCmdLogic.17.this.val$product_name, XULJSCmdLogic.17.this.val$type, XULJSCmdLogic.17.this.val$price);
                }
              });
            }
          }
          , new DialogInterface.OnCancelListener()
          {
            public void onCancel(DialogInterface paramAnonymous2DialogInterface)
            {
              if (!GlobalLogic.getInstance().isUserLogined())
                Logger.i("user not login!!");
              while (paramAnonymousStatus != XiaoMiUserCheck.Status.NONE)
                return;
              Logger.i("user not login 系统未登录!!");
            }
          }).show();
        while (paramAnonymousStatus != XiaoMiUserCheck.Status.SUCCESS)
          return;
        XULJSCmdLogic.this.getXiaomiOrderInfo(paramInt, paramString1, paramString2, paramLong);
      }
    });
  }

  private void startPlay()
  {
    PurchaseParam localPurchaseParam = GlobalLogic.getInstance().getPurchaseParam();
    if ((localPurchaseParam == null) || (TextUtils.isEmpty(localPurchaseParam.videoId)))
      return;
    Logger.i(TAG, "videoId:" + localPurchaseParam.videoId + ",videoName:" + localPurchaseParam.videoName + ",specialId:" + localPurchaseParam.specialId);
    if (!TextUtils.isEmpty(localPurchaseParam.specialId))
    {
      closeUserCenter();
      return;
    }
    PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
    localPlayerIntentParams.nns_videoInfo = new VideoInfo();
    localPlayerIntentParams.nns_videoInfo.videoId = localPurchaseParam.videoId;
    localPlayerIntentParams.nns_videoInfo.videoType = Integer.valueOf(localPurchaseParam.videoType).intValue();
    localPlayerIntentParams.nns_index = localPurchaseParam.videoIndex;
    localPlayerIntentParams.played_time = localPurchaseParam.videoPlayedTime;
    if (localPlayerIntentParams.nns_index < 0)
      localPlayerIntentParams.nns_index = 0;
    localPlayerIntentParams.nns_videoInfo.categoryId = localPurchaseParam.categoryId;
    localPlayerIntentParams.nns_videoInfo.packageId = localPurchaseParam.packageId;
    localPlayerIntentParams.nns_videoInfo.is_trylook = "0";
    localPlayerIntentParams.nns_videoInfo.name = localPurchaseParam.videoName;
    Logger.i("startPlay videoType=" + localPlayerIntentParams.nns_videoInfo.videoType + "mode=" + localPurchaseParam.mode + "nns_day=" + localPurchaseParam.nns_day + "," + localPurchaseParam.nns_begin + "," + localPurchaseParam.time_len);
    if (1 == localPlayerIntentParams.nns_videoInfo.videoType)
    {
      localPlayerIntentParams.mode = 6;
      if (2 == localPurchaseParam.mode)
      {
        localPlayerIntentParams.mode = 2;
        localPlayerIntentParams.nns_day = localPurchaseParam.nns_day;
        localPlayerIntentParams.nns_beginTime = localPurchaseParam.nns_begin;
        localPlayerIntentParams.nns_timeLen = localPurchaseParam.time_len;
      }
    }
    while (true)
    {
      Intent localIntent = new Intent().setClass(this._context, ActivityAdapter.getInstance().getMPlayer());
      localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_EXIT_APP, false);
      localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
      localIntent.addFlags(8388608);
      Logger.i(TAG, "startActivity getMPlayer info=" + localPlayerIntentParams.toString());
      startActivity(localIntent);
      closeUserCenter();
      return;
      localPlayerIntentParams.mode = 2;
    }
  }

  private void startXULActivity(String paramString, JSONObject paramJSONObject)
  {
    Intent localIntent = new Intent(this._context, XULActivity.class);
    localIntent.putExtra("xulPageId", paramString);
    if (paramJSONObject != null)
      localIntent.putExtra("xulData", paramJSONObject.toString());
    if ("NewUserCenter".equals(paramString))
    {
      GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
      GlobalLogic.getInstance().setAutoJumpToDetailedPage(false);
    }
    localIntent.addFlags(8388608);
    startActivity(localIntent);
  }

  private void stopCheckAlipayAuthorizeResult()
  {
    if (this.mCheckAlipayAuthorize != null)
    {
      this.mCheckAlipayAuthorize.stop();
      this.mCheckAlipayAuthorize = null;
    }
  }

  private void stopCheckRechargeResult()
  {
    if (this.mRechargeChecker != null)
    {
      this.mRechargeChecker.stop();
      this.mRechargeChecker = null;
    }
  }

  private void stopCheckSmsPayResult()
  {
    if (this.mSmsPayChecker != null)
    {
      this.mSmsPayChecker.stop();
      this.mSmsPayChecker = null;
    }
  }

  private void stopCheckThirdPartLoginResult()
  {
    if (this.mThirdPartLoginChecker != null)
    {
      this.mThirdPartLoginChecker.stop();
      this.mThirdPartLoginChecker = null;
    }
  }

  private void stopCheckWebChatBindResult()
  {
    if (this.mWebChatBindChecker != null)
    {
      this.mWebChatBindChecker.stop();
      this.mWebChatBindChecker = null;
    }
  }

  private void stopCheckWebChatLoginResult()
  {
    if (this.mWebChatLoginChecker != null)
    {
      this.mWebChatLoginChecker.stop();
      this.mWebChatLoginChecker = null;
    }
  }

  private void userMovieCoupon()
  {
    PurchaseParam localPurchaseParam = GlobalLogic.getInstance().getPurchaseParam();
    if (localPurchaseParam == null)
    {
      Logger.i(TAG, "userMovieCoupon param is null");
      return;
    }
    if (TextUtils.isEmpty(localPurchaseParam.videoId))
    {
      Logger.i(TAG, "userMovieCoupon videoId is null");
      return;
    }
    if (TextUtils.isEmpty(localPurchaseParam.import_id))
    {
      Logger.i(TAG, "userMovieCoupon import_id is null");
      return;
    }
    String str1 = localPurchaseParam.import_id;
    String str2 = localPurchaseParam.videoType;
    Logger.i(TAG, "userMovieCoupon import_id=" + str1 + ",type" + str2);
    ServerApiManager.i().ApiAAAUseMovieCoupon(GlobalLogic.getInstance().getWebToken(), 1, GlobalEnv.getInstance().getAAALicense(), str1, str2, new SccmsApiAAAUseMovieCouponTask.ISccmsApiAAAUseMovieCouponTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e(XULJSCmdLogic.TAG, "观影券使用失败！");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAUseMovieCouponResult paramAnonymousAAAUseMovieCouponResult)
      {
        if (paramAnonymousAAAUseMovieCouponResult.err != 0)
        {
          Logger.i(XULJSCmdLogic.TAG, "观影券使用失败！result.error=" + paramAnonymousAAAUseMovieCouponResult.toString());
          return;
        }
        Logger.i(XULJSCmdLogic.TAG, "开始播放视频");
        ReportInfo.getInstance().setEntranceSrc("I8");
        XULJSCmdLogic.this.startPlay();
      }
    });
  }

  private void xiaomiPay(final String paramString1, final String paramString2, final String paramString3, final String paramString4, Long paramLong)
  {
    MiTVPaymentThirdManager localMiTVPaymentThirdManager = new MiTVPaymentThirdManager(this._context);
    String str1 = GlobalLogic.getInstance().getUserInfo().mi_access_token;
    String str2 = GlobalLogic.getInstance().getUserInfo().mi_mac_key;
    Long localLong = XiaoMiOAuthManager.clientId;
    if (str1 != null);
    try
    {
      Logger.d(TAG, "orderId=" + paramString1 + ",ProductName=" + paramString2 + ",price=" + paramLong + "accessToken = " + str1 + "mackey = " + str2 + ",mClientId=" + localLong);
      localMiTVPaymentThirdManager.CreateOrderAndPay(paramString1, paramString2, paramLong.longValue(), str1, str2, localLong.longValue(), "芒果TV " + paramString2, "", new MiTVPaymentThirdManager.CallBackListener()
      {
        public void HandleResult(String paramAnonymousString)
        {
          Logger.i("CreateOrderAndPay result=" + paramAnonymousString);
          XiaoMiPayResult localXiaoMiPayResult = XULJSCmdLogic.this.getXiaoMiPayResult(paramAnonymousString);
          if (localXiaoMiPayResult.code == 0)
          {
            XULJSCmdLogic localXULJSCmdLogic = XULJSCmdLogic.this;
            String[] arrayOfString = new String[4];
            arrayOfString[0] = paramString1;
            arrayOfString[1] = paramString2;
            arrayOfString[2] = paramString3;
            arrayOfString[3] = paramString4;
            localXULJSCmdLogic.xulFireEvent("appEvents:xiaomiPurchaseSuccess", arrayOfString);
            Logger.i("CreateOrderAndPay Success result=" + paramAnonymousString);
            Logger.i("CreateOrderAndPay Success result=" + localXiaoMiPayResult.toString());
            return;
          }
          Logger.e("CreateOrderAndPay Error result=" + paramAnonymousString);
        }
      });
      return;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
  }

  private XulView xulFindViewById(String paramString)
  {
    return this._xulPage.xulFindViewById(paramString);
  }

  private void xulFireEvent(String paramString)
  {
    try
    {
      this._xulPage.xulFireEvent(paramString);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void xulFireEvent(String paramString, Object[] paramArrayOfObject)
  {
    try
    {
      this._xulPage.xulFireEvent(paramString, paramArrayOfObject);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private JSONObject xulGetJSInitData()
  {
    return this._xulPage.getJSInitData();
  }

  private void xulHideProgress()
  {
    this._xulPage.hideProgressInfo();
  }

  private void xulPostDelay(Runnable paramRunnable, int paramInt)
  {
    this._xulPage.xulPostDelay(paramRunnable, paramInt);
  }

  private void xulSendBroadcast(String paramString, String[] paramArrayOfString)
  {
    try
    {
      Intent localIntent = new Intent("com.starcor.xul.ACTION_BROADCAST");
      localIntent.setPackage(this._context.getPackageName());
      localIntent.putExtra("name", paramString);
      if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
        localIntent.putExtra("params", paramArrayOfString);
      this._context.sendBroadcast(localIntent);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void xulShowProgress()
  {
    this._xulPage.showProgressInfo();
  }

  public void addMovieCouponInfo(String paramString1, String paramString2, String paramString3)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("user_id", paramString1);
    localHashMap.put("presenter_movie_coupon", paramString2);
    localHashMap.put("movie_couponRed_dot_state", paramString3);
    if (this.movieCouponsDataManager == null)
      this.movieCouponsDataManager = new MovieCouponsDataManager(this._context);
    this.movieCouponsDataManager.addMovieCouponsInfo(localHashMap);
  }

  public void destroy()
  {
    if (this.mWebChatLoginChecker != null)
      stopCheckWebChatLoginResult();
    if (this.mThirdPartLoginChecker != null)
      stopCheckThirdPartLoginResult();
    if (this.mRechargeChecker != null)
      stopCheckRechargeResult();
    if (this._xulBroadcastReceiver != null)
    {
      this._context.unregisterReceiver(this._xulBroadcastReceiver);
      this._xulBroadcastReceiver = null;
    }
  }

  public boolean doJSCmd(XulView paramXulView, String paramString, Object paramObject)
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject(paramString);
    String str1 = localJSONObject1.optString("cmd");
    if ("mgtvLogin".equals(str1))
    {
      mgtvDoLogin(localJSONObject1.optString("user"), localJSONObject1.optString("pwd"), localJSONObject1.optString("presenter_movie_coupon"), localJSONObject1.optString("movie_couponRed_dot_state"));
      return true;
    }
    if ("mgtvUnbindWebchat".equals(str1))
    {
      mgtvUnbindWebchat();
      return true;
    }
    if ("logout".equals(str1))
    {
      doLogout();
      return true;
    }
    if ("startXULActivity".equals(str1))
    {
      String str18 = localJSONObject1.optString("page");
      if (localJSONObject1.has("data"));
      for (JSONObject localJSONObject4 = localJSONObject1.optJSONObject("data"); ; localJSONObject4 = null)
      {
        startXULActivity(str18, localJSONObject4);
        return true;
      }
    }
    if ("sendXULBroadcast".equals(str1))
    {
      String str17 = localJSONObject1.optString("name");
      JSONArray localJSONArray2;
      if (localJSONObject1.has("data"))
      {
        localJSONArray2 = localJSONObject1.optJSONArray("data");
        if ((localJSONArray2 != null) && (localJSONArray2.length() != 0))
          break label233;
        xulSendBroadcast(str17, new String[0]);
      }
      while (true)
      {
        return true;
        localJSONArray2 = null;
        break;
        label233: String[] arrayOfString3 = new String[localJSONArray2.length()];
        for (int i6 = 0; ; i6++)
        {
          int i7 = localJSONArray2.length();
          if (i6 >= i7)
            break;
          arrayOfString3[i6] = String.valueOf(localJSONArray2.get(i6));
        }
        xulSendBroadcast(str17, arrayOfString3);
      }
    }
    if ("showXULDialog".equals(str1))
    {
      String str16 = localJSONObject1.optString("page");
      if (localJSONObject1.has("data"));
      for (JSONObject localJSONObject3 = localJSONObject1.optJSONObject("data"); ; localJSONObject3 = null)
      {
        showXULDialog(str16, localJSONObject3);
        return true;
      }
    }
    if ("closeXULPage".equals(str1))
    {
      closeXULPage();
      return true;
    }
    if ("finishActivity".equals(str1))
    {
      finish();
      return true;
    }
    if ("dismissXULDialog".equals(str1))
    {
      execute("xul-close-dialog", null);
      return true;
    }
    if ("startLoading".equals(str1))
    {
      xulShowProgress();
      return true;
    }
    if ("stopLoading".equals(str1))
    {
      xulHideProgress();
      return true;
    }
    if ("startCheckWebChatLoginResult".equals(str1))
    {
      String str15 = localJSONObject1.optString("rcode");
      if (TextUtils.isEmpty(str15))
        return true;
      startCheckWebChatLoginResult(str15);
      return true;
    }
    if ("startCheckWebChatBindResult".equals(str1))
    {
      String str14 = localJSONObject1.optString("rcode");
      if (TextUtils.isEmpty(str14))
        return true;
      startCheckWebChatBindResult(str14);
      return true;
    }
    if ("stopCheckWebChatBindResult".equals(str1))
    {
      stopCheckWebChatBindResult();
      return true;
    }
    if ("stopCheckWebChatLoginResult".equals(str1))
    {
      stopCheckWebChatLoginResult();
      return true;
    }
    if ("startCheckThirdPartLoginResult".equals(str1))
    {
      String str13 = localJSONObject1.optString("rcode");
      if (TextUtils.isEmpty(str13))
        return true;
      startCheckThirdPartLoginResult(str13);
      return true;
    }
    if ("stopCheckThirdPartLoginResult".equals(str1))
    {
      stopCheckThirdPartLoginResult();
      return true;
    }
    if ("startCheckRechargeResult".equals(str1))
    {
      String str12 = localJSONObject1.optString("rcode");
      if (!TextUtils.isEmpty(str12))
        startCheckRechargeResult(str12);
      while (true)
      {
        return true;
        startCheckShortCodeResult(localJSONObject1.optString("short_code"));
      }
    }
    if ("stopCheckRechargeResult".equals(str1))
    {
      stopCheckRechargeResult();
      return true;
    }
    if ("startCheckAlipayAuthorizeResult".equals(str1))
    {
      Logger.d(TAG, "startCheckAlipayAuthorizeResult");
      startCheckAlipayAuthorizeResult();
      return true;
    }
    if ("stopCheckAlipayAuthorizeResult".equals(str1))
    {
      Logger.d(TAG, "stopCheckAlipayAuthorizeResult");
      stopCheckAlipayAuthorizeResult();
      return true;
    }
    if ("startCheckXiaomiAccount".equalsIgnoreCase(str1))
    {
      if (localJSONObject1.has("data"));
      for (JSONObject localJSONObject2 = localJSONObject1.optJSONObject("data"); ; localJSONObject2 = null)
      {
        int i5 = -1;
        String str10 = "";
        String str11 = "";
        Long localLong = Long.valueOf(0L);
        if (localJSONObject2 != null)
        {
          i5 = Integer.parseInt(localJSONObject2.getString("product_id"));
          str10 = localJSONObject2.getString("product_name");
          str11 = localJSONObject2.getString("product_type");
          localLong = Long.valueOf(localJSONObject2.getLong("product_price"));
          Logger.i("startCheckXiaomiAccount product_id=" + i5 + ",product_name" + ",price" + localLong);
        }
        xulShowProgress();
        startCheckXiaomiAccount(i5, str10, str11, localLong);
        return true;
      }
    }
    if ("aliPayBuyProduct".equals(str1))
    {
      int i4 = localJSONObject1.optInt("product_id", -1);
      String str9 = localJSONObject1.optString("amount", "0");
      if (i4 == -1)
      {
        Logger.e(TAG, "product_id is null");
        return false;
      }
      aliPayBuyProduct(i4, str9, PayChannelCode.ALIPAY);
      return true;
    }
    if ("alipayRecharge".equals(str1))
    {
      int i3 = localJSONObject1.optInt("product_id", 1);
      alipayRecharge(localJSONObject1.optString("amount", "0"), localJSONObject1.optString("order_id", ""), i3, PayChannelCode.ALIPAY);
      return true;
    }
    if ("queryOrderResult".equals(str1))
    {
      aliPayQueryOrder(localJSONObject1);
      return true;
    }
    if ("registerAccount".equals(str1))
    {
      mgtvRegisterAccount(localJSONObject1.optString("name", ""), localJSONObject1.optString("pwd", ""), localJSONObject1.optString("mobile", ""));
      return true;
    }
    if ("__init__".equals(str1))
    {
      doInit(paramXulView);
      return true;
    }
    if ("mgtvRequestCaptcha".equals(str1))
    {
      requestCaptcha(localJSONObject1.optString("user"));
      return true;
    }
    if ("mgtvRequestCaptchaByName".equals(str1))
    {
      requestCaptchaByName(localJSONObject1.optString("user"));
      return true;
    }
    if ("mgtvFindPwd".equals(str1))
    {
      mgtvResetPwd(localJSONObject1.optString("name"), localJSONObject1.optString("pwd"), localJSONObject1.optString("captcha"));
      return true;
    }
    if ("mgtvRequestConsumptionList".equals(str1))
    {
      requestConsumptionList(Integer.valueOf(localJSONObject1.optString("pageIndex")).intValue(), Integer.valueOf(localJSONObject1.optString("cate")).intValue());
      return true;
    }
    if ("mgtvChangPassword".equals(str1))
    {
      String str8 = localJSONObject1.optString("newpwd");
      mgtvDoChangePwd(localJSONObject1.optString("oldpwd"), str8);
      return true;
    }
    if ("mgtvGetOrderState".equals(str1))
    {
      mgtvGetOrderState(localJSONObject1.optString("order_id"));
      return true;
    }
    if ("mgtvCloseUserCenter".equals(str1))
    {
      closeUserCenter();
      return true;
    }
    if ("mgtvUserCouponCard".equals(str1))
    {
      Logger.i(" mgtvUserCouponCard");
      mgtvUserCouponCard(localJSONObject1.optString("key"));
      return true;
    }
    if ("mgtvWechatPay".equals(str1))
    {
      Logger.i(" mgtvWechatPay");
      mgtvWechatPay();
      return true;
    }
    if ("mgtvSmsPay".equals(str1))
    {
      Logger.i(" mgtvSmsPay");
      String str5 = localJSONObject1.optString("product_id");
      String str6 = localJSONObject1.optString("mobile");
      int i1 = -1;
      if (!TextUtils.isEmpty(str5));
      try
      {
        int i2 = Integer.parseInt(str5);
        i1 = i2;
        String str7 = localJSONObject1.optString("channel");
        mgtvSmsPay(i1, str6, str7);
        return true;
      }
      catch (Exception localException)
      {
        while (true)
        {
          localException.printStackTrace();
          i1 = -1;
        }
      }
    }
    if ("startCheckSmsPayResult".equals(str1))
    {
      Logger.i(" startCheckSmsPayResult");
      startCheckSmsPayResult(localJSONObject1.optString("order_id"));
      return true;
    }
    if ("stopCheckSmsPayResult".equals(str1))
    {
      Logger.i(" stopCheckSmsPayResult");
      stopCheckSmsPayResult();
      return true;
    }
    if ((!TextUtils.isEmpty(str1)) && (str1.startsWith("xul-")))
    {
      JSONArray localJSONArray1 = localJSONObject1.optJSONArray("args");
      String[] arrayOfString2 = null;
      if (localJSONArray1 != null)
      {
        arrayOfString2 = new String[localJSONArray1.length()];
        for (int m = 0; ; m++)
        {
          int n = localJSONArray1.length();
          if (m >= n)
            break;
          arrayOfString2[m] = localJSONArray1.optString(m, null);
        }
      }
      execute("xul-close-dialog", arrayOfString2);
      return true;
    }
    if ("sendKeyEvent_keyCodeUp".equalsIgnoreCase(str1));
    do
    {
      try
      {
        String[] arrayOfString1 = new String[3];
        arrayOfString1[0] = "input";
        arrayOfString1[1] = "keyevent";
        arrayOfString1[2] = String.valueOf(19);
        new ProcessBuilder(arrayOfString1).start();
        Logger.w(TAG, "unsupported jsCmd: " + str1);
        return false;
      }
      catch (IOException localIOException)
      {
        while (true)
          localIOException.printStackTrace();
      }
      if ("loadImageSuccess".equalsIgnoreCase(str1))
      {
        localJSONObject1.optInt("time");
        localJSONObject1.optInt("type");
        return true;
      }
      if ("loadImageFailed".equalsIgnoreCase(str1))
      {
        int j = localJSONObject1.optInt("time");
        int k = localJSONObject1.optInt("type");
        Logger.i("onLoadImageFailed type=" + k + ",time=" + j);
        return true;
      }
      if ("isDisplayMovieCouponHint".equalsIgnoreCase(str1))
      {
        queryMovieCouponInfo(localJSONObject1.optString("user_id"));
        return true;
      }
      if ("addMovieCouponHintInfo".equalsIgnoreCase(str1))
      {
        String str4 = localJSONObject1.optString("presenter_movie_coupon");
        addMovieCouponInfo(localJSONObject1.optString("user_id"), str4, localJSONObject1.optString("movie_couponRed_dot_state"));
        return true;
      }
      if ("deleteMovieCouponHintInfo".equalsIgnoreCase(str1))
      {
        String str3 = localJSONObject1.optString("user_id");
        this.movieCouponsDataManager = new MovieCouponsDataManager(this._context);
        this.movieCouponsDataManager.deleteMovieCouponsInfo(str3);
        return true;
      }
      if ("updateMovieCouponHintInfo".equalsIgnoreCase(str1))
      {
        String str2 = localJSONObject1.optString("presenter_movie_coupon");
        updateMovieCouponInfo(localJSONObject1.optString("user_id"), str2, localJSONObject1.optString("movie_couponRed_dot_state"));
        return true;
      }
      if ("onMovieCouponClick".equalsIgnoreCase(str1))
      {
        Logger.i(TAG, "onMovieCouponClick in! ");
        userMovieCoupon();
        return true;
      }
      if ("onMoviePlayClick".equalsIgnoreCase(str1))
      {
        Logger.i(TAG, "onMoviePlayClick in! ");
        ReportInfo.getInstance().setEntranceSrc("I8");
        startPlay();
        return true;
      }
    }
    while (!"delay".equalsIgnoreCase(str1));
    int i = localJSONObject1.optInt("time");
    if (this.mHandler != null)
      this.mHandler.sendEmptyMessageDelayed(4, i);
    return true;
  }

  public JSONObject getInit()
  {
    return xulGetJSInitData();
  }

  public XiaoMiPayResult getXiaoMiPayResult(String paramString)
  {
    XiaoMiPayResult localXiaoMiPayResult = new XiaoMiPayResult();
    try
    {
      JSONObject localJSONObject1 = new JSONObject(paramString);
      if (localJSONObject1.has("dueTime"))
        localXiaoMiPayResult.dueTime = localJSONObject1.getString("dueTime");
      if (localJSONObject1.has("createTime"))
        localXiaoMiPayResult.createTime = localJSONObject1.getString("createTime");
      if (localJSONObject1.has("xiaomiId"))
        localXiaoMiPayResult.xiaomiId = localJSONObject1.getString("xiaomiId");
      if (localJSONObject1.has("orderId"))
        localXiaoMiPayResult.orderId = localJSONObject1.getString("orderId");
      if (localJSONObject1.has("customerOrderId"))
        localXiaoMiPayResult.customerOrderId = localJSONObject1.getString("customerOrderId");
      if (localJSONObject1.has("result"))
      {
        String str = localJSONObject1.getString("result");
        Logger.i("result = " + str);
        JSONObject localJSONObject3 = new JSONObject(str);
        if (localJSONObject3.has("status"))
          localXiaoMiPayResult.status = localJSONObject3.getInt("status");
        if (localJSONObject3.has("code"))
          localXiaoMiPayResult.code = localJSONObject3.getInt("code");
        if (localJSONObject3.has("msg"))
          localXiaoMiPayResult.status_msg = localJSONObject3.getString("msg");
      }
      if (localJSONObject1.has("accessToken"))
      {
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("accessToken"));
        if (localJSONObject2.has("payAccessToken"))
          localXiaoMiPayResult.payAccessToken = localJSONObject2.getString("payAccessToken");
        if (localJSONObject2.has("expireDate"))
          localXiaoMiPayResult.expireDate = localJSONObject2.getString("expireDate");
      }
      Logger.i(TAG, "payResult = " + localXiaoMiPayResult.toString());
      return localXiaoMiPayResult;
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
  }

  public void queryMovieCouponInfo(String paramString)
  {
    new HashMap();
    if (this.movieCouponsDataManager == null)
      this.movieCouponsDataManager = new MovieCouponsDataManager(this._context);
    HashMap localHashMap = this.movieCouponsDataManager.getMovieCouponsInfo(paramString);
    xulFireEvent("appEvents:Broadcast:updateDisplayMovieCouponHint", new String[] { (String)localHashMap.get("presenter_movie_coupon"), (String)localHashMap.get("movie_couponRed_dot_state") });
  }

  public void updateMovieCouponInfo(String paramString1, String paramString2, String paramString3)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("user_id", paramString1);
    localHashMap.put("presenter_movie_coupon", paramString2);
    localHashMap.put("movie_couponRed_dot_state", paramString3);
    if (this.movieCouponsDataManager == null)
      this.movieCouponsDataManager = new MovieCouponsDataManager(this._context);
    this.movieCouponsDataManager.UpdateMovieCouponsInfo(paramString1, localHashMap);
  }

  class SccmsApiGetUserAuthV2TaskListener
    implements SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener
  {
    SccmsApiGetUserAuthV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
      Logger.i(XULJSCmdLogic.TAG, "SccmsApiGetUserAuthV2TaskListener.onError() error:" + paramServerApiCommonError.toString());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserAuthV2 paramUserAuthV2)
    {
      Logger.i(XULJSCmdLogic.TAG, "SccmsApiGetUserAuthV2TaskListener.onSuccess() :" + paramUserAuthV2.toString());
      PurchaseParam localPurchaseParam = GlobalLogic.getInstance().getPurchaseParam();
      if (paramUserAuthV2.state != null);
      switch (paramUserAuthV2.state.state)
      {
      default:
        return;
      case 0:
        Logger.i(XULJSCmdLogic.TAG, "SccmsApiGetUserAuthV2TaskListener.onSuccess() param.videoType" + localPurchaseParam.videoType);
        if ((!TextUtils.isEmpty(localPurchaseParam.videoType)) && ("1".equals(localPurchaseParam.videoType)))
        {
          ReportInfo.getInstance().setEntranceSrc("I7");
          XULJSCmdLogic.this.startPlay();
          GlobalLogic.getInstance().setProductList(null);
          GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
          return;
        }
        GlobalLogic.getInstance().setLoginedVideoId(localPurchaseParam.videoId);
        XULJSCmdLogic.this.killXULActivity();
        GlobalLogic.getInstance().setProductList(null);
        GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
        return;
      case 1:
      }
      if ((!TextUtils.isEmpty(localPurchaseParam.videoType)) && ("1".equals(localPurchaseParam.videoType)))
      {
        if (GlobalLogic.getInstance().isChannelCanUseCoupon(paramUserAuthV2.list))
        {
          ReportInfo.getInstance().setEntranceSrc("I7");
          XULJSCmdLogic.this.startPlay();
          GlobalLogic.getInstance().setProductList(null);
          GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
          return;
        }
        GlobalLogic.getInstance().setProductList(paramUserAuthV2.list);
        XULJSCmdLogic.this.xulFireEvent("appEvents:autoJumpToPurchaseVIP");
        return;
      }
      int i = 2;
      ArrayList localArrayList = paramUserAuthV2.list;
      int j = 0;
      if (localArrayList != null)
      {
        Iterator localIterator = paramUserAuthV2.list.iterator();
        while (localIterator.hasNext())
        {
          UserKey localUserKey = (UserKey)localIterator.next();
          if (localUserKey != null)
          {
            if (localUserKey.key.equals("asset_type"));
            try
            {
              int m = Integer.valueOf(localUserKey.value).intValue();
              i = m;
              if (!localUserKey.key.equals("coupon"));
            }
            catch (Exception localException2)
            {
              try
              {
                int k = Integer.valueOf(localUserKey.value).intValue();
                j = k;
                continue;
                localException2 = localException2;
                i = 2;
                localException2.printStackTrace();
              }
              catch (Exception localException1)
              {
                localException1.printStackTrace();
              }
            }
          }
        }
      }
      if (((i == 1) || (i == 3)) && (j > 0))
      {
        XULJSCmdLogic.this.killXULActivity();
        GlobalLogic.getInstance().setProductList(null);
        GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
        return;
      }
      GlobalLogic.getInstance().setProductList(paramUserAuthV2.list);
      XULJSCmdLogic.this.xulFireEvent("appEvents:autoJumpToPurchaseVIP");
    }
  }

  class StatusChecker
    implements Runnable
  {
    public static final String CHECKER_ALIPAY_AUTHORIZE = "CHECKER_ALIPAY_AUTHORIZE";
    public static final String CHECKER_TYPE_RECHARGE = "CHECKER_TYPE_RECHARGE";
    public static final String CHECKER_TYPE_SHORT_CODE = "CHECKER_TYPE_SHORT_CODE";
    public static final String CHECKER_TYPE_SMS_PAY = "CHECKER_TYPE_SMS_PAY";
    public static final String CHECKER_TYPE_THIRD_PART_LOGIN = "CHECKER_TYPE_THIRD_PART_LOGIN";
    public static final String CHECKER_TYPE_WEBCHAT_BIND = "CHECKER_TYPE_WEBCHAT_BIND";
    public static final String CHECKER_TYPE_WEBCHAT_LOGIN = "CHECKER_TYPE_WEBCHAT_LOGIN";
    int _apiTaskId = -1;
    long _beginTime;
    String _rcode;
    XULJSCmdLogic<T>.StatusChecker _statusChecker;
    String _type;

    StatusChecker(String paramString1, String arg3)
    {
      this._type = paramString1;
      Object localObject;
      this._rcode = localObject;
      this._beginTime = XulUtils.timestamp();
      this._statusChecker = this;
    }

    private void onCheckLoginSuccess(UserCenterCheckReturn paramUserCenterCheckReturn)
    {
      UserInfo localUserInfo = new UserInfo();
      if (this._type.equals("CHECKER_TYPE_WEBCHAT_LOGIN"))
      {
        localUserInfo.loginMode = "webchat";
        localUserInfo.user_id = String.valueOf(paramUserCenterCheckReturn.uid);
        localUserInfo.expires_in = paramUserCenterCheckReturn.expire;
        localUserInfo.name = paramUserCenterCheckReturn.nickname;
        localUserInfo.account = paramUserCenterCheckReturn.loginaccount;
        localUserInfo.mobile = paramUserCenterCheckReturn.mobile;
        localUserInfo.status = paramUserCenterCheckReturn.status;
        localUserInfo.web_token = paramUserCenterCheckReturn.ticket;
        localUserInfo.avatar = paramUserCenterCheckReturn.avatar;
        localUserInfo.wechat_type = paramUserCenterCheckReturn.wechat_type;
        localUserInfo.rtype = paramUserCenterCheckReturn.rtype;
        localUserInfo.email = paramUserCenterCheckReturn.email;
        if (TextUtils.isEmpty(paramUserCenterCheckReturn.ticket))
          break label297;
        GlobalLogic.getInstance().userLogin(localUserInfo);
        Logger.i("user UserCenterCheckReturn ticket=" + paramUserCenterCheckReturn.ticket);
        label159: new UploadMediaDataTask().executeSync();
        MergeLocalReserveAndUpload.getInstance().mergeReservesAndUpload();
        PurchaseParam localPurchaseParam = GlobalLogic.getInstance().getPurchaseParam();
        if ((localPurchaseParam == null) || (!localPurchaseParam.autoJump))
          break label339;
        if (TextUtils.isEmpty(localPurchaseParam.videoId))
          break label306;
        ServerApiManager.i().APIGetUserAuthV2(new GetUserAuthV2Params(localPurchaseParam.videoId, localPurchaseParam.videoType, GlobalLogic.getInstance().getQuality()), new XULJSCmdLogic.SccmsApiGetUserAuthV2TaskListener(XULJSCmdLogic.this));
      }
      while (true)
      {
        GlobalEventNotify.getInstance().sendUserLoginAction(App.getAppContext(), "login");
        return;
        if (this._type.equals("CHECKER_TYPE_THIRD_PART_LOGIN"))
        {
          localUserInfo.loginMode = "other";
          break;
        }
        if (!this._type.equals("CHECKER_TYPE_WEBCHAT_BIND"))
          break;
        localUserInfo.loginMode = "webchat";
        break;
        label297: Logger.i("user UserCenterCheckReturn ticket is null");
        break label159;
        label306: GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
        XULJSCmdLogic.this.xulFireEvent("appEvents:autoJumpToPurchaseVIP");
        continue;
        label339: if ((!TextUtils.isEmpty(GlobalLogic.getInstance().getAutoJumpPage())) && (GlobalLogic.getInstance().getAutoJumpPage().equals("CouponCard")))
        {
          XULJSCmdLogic.this.xulFireEvent("appEvents:autoJumpToCouponCard");
        }
        else if (GlobalLogic.getInstance().getIsCloseXul())
        {
          XULJSCmdLogic.this.killXULActivity();
        }
        else if (this._type.equals("CHECKER_TYPE_WEBCHAT_LOGIN"))
        {
          XULJSCmdLogic.this.xulFireEvent("appEvents:webchatLoginSuccess");
          new HashMap();
          if (XULJSCmdLogic.this.movieCouponsDataManager == null)
            XULJSCmdLogic.access$1102(XULJSCmdLogic.this, new MovieCouponsDataManager(XULJSCmdLogic.this._context));
          HashMap localHashMap2 = XULJSCmdLogic.this.movieCouponsDataManager.getMovieCouponsInfo(localUserInfo.account);
          if (!localUserInfo.account.equals(localHashMap2.get("user_id")))
            XULJSCmdLogic.this.addMovieCouponInfo(localUserInfo.account, "0", "none");
        }
        else if (this._type.equals("CHECKER_TYPE_THIRD_PART_LOGIN"))
        {
          XULJSCmdLogic.this.xulFireEvent("appEvents:thirdpartLoginSuccess");
          new HashMap();
          if (XULJSCmdLogic.this.movieCouponsDataManager == null)
            XULJSCmdLogic.access$1102(XULJSCmdLogic.this, new MovieCouponsDataManager(XULJSCmdLogic.this._context));
          HashMap localHashMap1 = XULJSCmdLogic.this.movieCouponsDataManager.getMovieCouponsInfo(localUserInfo.account);
          if (!localUserInfo.account.equals(localHashMap1.get("user_id")))
            XULJSCmdLogic.this.addMovieCouponInfo(localUserInfo.account, "0", "none");
        }
      }
    }

    private void onCheckRechargeSuccess(AAAOrderState paramAAAOrderState, String paramString)
    {
      Logger.i(XULJSCmdLogic.TAG, "Recharge status : " + paramAAAOrderState.orderStatus);
      XULJSCmdLogic.this.xulFireEvent("appEvents:rechargeSuccess", new String[] { paramString });
    }

    private void onCheckVerfyTokenSuccess(UserCenterLogin paramUserCenterLogin)
    {
      UserInfo localUserInfo = new UserInfo();
      if (this._type.equals("CHECKER_TYPE_WEBCHAT_BIND"))
        localUserInfo.loginMode = "webchat";
      localUserInfo.user_id = String.valueOf(paramUserCenterLogin.uid);
      localUserInfo.expires_in = paramUserCenterLogin.expire;
      localUserInfo.name = paramUserCenterLogin.nickname;
      localUserInfo.account = paramUserCenterLogin.loginaccount;
      localUserInfo.mobile = paramUserCenterLogin.mobile;
      localUserInfo.status = paramUserCenterLogin.status;
      localUserInfo.web_token = paramUserCenterLogin.ticket;
      localUserInfo.avatar = paramUserCenterLogin.avatar;
      localUserInfo.wechat_type = paramUserCenterLogin.wechat_type;
      localUserInfo.rtype = paramUserCenterLogin.rtype;
      localUserInfo.email = paramUserCenterLogin.email;
      if (!TextUtils.isEmpty(paramUserCenterLogin.ticket))
        GlobalLogic.getInstance().userLogin(localUserInfo);
      if (this._type.equals("CHECKER_TYPE_WEBCHAT_BIND"))
        XULJSCmdLogic.this.xulFireEvent("appEvents:webchatBindSuccess");
    }

    private void onTimeout()
    {
      if (this._type.equals("CHECKER_TYPE_WEBCHAT_LOGIN"))
        XULJSCmdLogic.this.xulFireEvent("appEvents:webchatLoginTimeout");
      do
      {
        return;
        if (this._type.equals("CHECKER_TYPE_THIRD_PART_LOGIN"))
        {
          XULJSCmdLogic.this.xulFireEvent("appEvents:thirdpartLoginTimeout");
          return;
        }
        if ((this._type.equals("CHECKER_TYPE_RECHARGE")) || (this._type.equals("CHECKER_TYPE_SHORT_CODE")))
        {
          XULJSCmdLogic.this.xulFireEvent("appEvents:rechargeTimeout");
          return;
        }
        if (this._type.equals("CHECKER_TYPE_WEBCHAT_BIND"))
        {
          XULJSCmdLogic.this.xulFireEvent("appEvents:webchatBindTimeout");
          return;
        }
      }
      while (!this._type.equals("CHECKER_TYPE_SMS_PAY"));
      XULJSCmdLogic.this.xulFireEvent("appEvents:SmsPayTimeout");
    }

    private void userCenterVerifyToken()
    {
      ServerApiManager.i().APIUserCenterVerifyToken(GlobalLogic.getInstance().getWebToken(), GlobalEnv.getInstance().getAAALicense(), new SccmsApiUserCenterVerifyTokenTask.ISccmsApiUserCenterVerifyTokenTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          Logger.i("APIUserCenterVerifyToken onError");
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserCenterLogin paramAnonymousUserCenterLogin)
        {
          GlobalLogic.getInstance().checkTokenStatus(paramAnonymousUserCenterLogin.err, paramAnonymousUserCenterLogin.status);
          if (paramAnonymousUserCenterLogin.err == 0)
          {
            XULJSCmdLogic.StatusChecker.this.onCheckVerfyTokenSuccess(paramAnonymousUserCenterLogin);
            return;
          }
          Logger.i("APIUserCenterVerifyToken failed");
          XULJSCmdLogic.this.xulFireEvent("appEvents:webchatBindFail");
        }
      });
    }

    public void run()
    {
      if (this._statusChecker != this)
        return;
      if (XulUtils.timestamp() - this._beginTime > 1800000L)
      {
        this._statusChecker = null;
        onTimeout();
      }
      String str1;
      String str2;
      if (this._apiTaskId == -1)
      {
        str1 = GlobalLogic.getInstance().getWebToken();
        str2 = GlobalEnv.getInstance().getAAALicense();
        if ((!this._type.equals("CHECKER_TYPE_WEBCHAT_LOGIN")) && (!this._type.equals("CHECKER_TYPE_THIRD_PART_LOGIN")) && (!this._type.equals("CHECKER_TYPE_WEBCHAT_BIND")))
          break label126;
        this._apiTaskId = ServerApiManager.i().APIUserCenterCheckReturn(this._rcode, str2, new SccmsApiUserCenterCheckReturnTask.ISccmsApiCheckUserCenterReturnTaskListener()
        {
          public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
          {
            XULJSCmdLogic.StatusChecker.this._apiTaskId = -1;
            Logger.e(XULJSCmdLogic.TAG, "APIUserCenterCheckReturn error!");
          }

          public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserCenterCheckReturn paramAnonymousUserCenterCheckReturn)
          {
            Logger.d(XULJSCmdLogic.TAG, "login result = " + paramAnonymousUserCenterCheckReturn.toString());
            if (paramAnonymousUserCenterCheckReturn.err != 0)
            {
              XULJSCmdLogic.StatusChecker.this._apiTaskId = -1;
              Logger.e(XULJSCmdLogic.TAG, "APIUserCenterCheckReturn failed!");
              if ((XULJSCmdLogic.StatusChecker.this._type.equals("CHECKER_TYPE_WEBCHAT_BIND")) && (paramAnonymousUserCenterCheckReturn.status.equals("19004")))
              {
                XULJSCmdLogic.this.xulFireEvent("appEvents:webchatBindFail");
                Logger.e(XULJSCmdLogic.TAG, "APIUserCenterCheckReturn failed! appEvents:webchatBindFail");
              }
              return;
            }
            Logger.e(XULJSCmdLogic.TAG, "APIUserCenterCheckReturn success!");
            if (XULJSCmdLogic.StatusChecker.this._type.equals("CHECKER_TYPE_WEBCHAT_BIND"))
            {
              XULJSCmdLogic.this.stopCheckWebChatBindResult();
              XULJSCmdLogic.StatusChecker.this.userCenterVerifyToken();
              return;
            }
            if (XULJSCmdLogic.StatusChecker.this._type.equals("CHECKER_TYPE_THIRD_PART_LOGIN"))
              XULJSCmdLogic.this.stopCheckThirdPartLoginResult();
            while (true)
            {
              XULJSCmdLogic.StatusChecker.this.onCheckLoginSuccess(paramAnonymousUserCenterCheckReturn);
              return;
              if (XULJSCmdLogic.StatusChecker.this._type.equals("CHECKER_TYPE_WEBCHAT_LOGIN"))
                XULJSCmdLogic.this.stopCheckWebChatLoginResult();
            }
          }
        });
      }
      while (true)
      {
        XULJSCmdLogic.this.xulPostDelay(this, 3000);
        return;
        label126: if ((this._type.equals("CHECKER_TYPE_RECHARGE")) || (this._type.equals("CHECKER_TYPE_SMS_PAY")))
          this._apiTaskId = ServerApiManager.i().APIAAAGetOrderState(str2, str1, 1, this._rcode, "", new SccmsApiAAAGetOrderStateTask.ISccmsApiAAAGetOrderStateTaskListener()
          {
            public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
            {
              XULJSCmdLogic.StatusChecker.this._apiTaskId = -1;
              Logger.e(XULJSCmdLogic.TAG, "APIAAAGetOrderState error!");
            }

            public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAOrderState paramAnonymousAAAOrderState)
            {
              if (paramAnonymousAAAOrderState.err != 0)
              {
                XULJSCmdLogic.StatusChecker.this._apiTaskId = -1;
                Logger.e(XULJSCmdLogic.TAG, "APIAAAGetOrderState failed!");
                return;
              }
              if (paramAnonymousAAAOrderState.orderStatus.equals("FINISH"))
              {
                Logger.d(XULJSCmdLogic.TAG, "APIAAAGetOrderState success!");
                if (XULJSCmdLogic.StatusChecker.this._type.equals("CHECKER_TYPE_SMS_PAY"))
                {
                  XULJSCmdLogic.this.xulFireEvent("appEvents:SmsPaySuccess");
                  return;
                }
                XULJSCmdLogic.StatusChecker.this.onCheckRechargeSuccess(paramAnonymousAAAOrderState, XULJSCmdLogic.StatusChecker.this._rcode);
                return;
              }
              XULJSCmdLogic.StatusChecker.this._apiTaskId = -1;
            }
          });
        else if (this._type.equals("CHECKER_TYPE_SHORT_CODE"))
          this._apiTaskId = ServerApiManager.i().APIAAAGetOrderState(str2, str1, 1, "", this._rcode, new SccmsApiAAAGetOrderStateTask.ISccmsApiAAAGetOrderStateTaskListener()
          {
            public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
            {
              XULJSCmdLogic.StatusChecker.this._apiTaskId = -1;
              Logger.e(XULJSCmdLogic.TAG, "APIAAAGetOrderState error!");
            }

            public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAOrderState paramAnonymousAAAOrderState)
            {
              if (paramAnonymousAAAOrderState.err != 0)
              {
                XULJSCmdLogic.StatusChecker.this._apiTaskId = -1;
                Logger.e(XULJSCmdLogic.TAG, "APIAAAGetOrderState failed!");
                return;
              }
              if (paramAnonymousAAAOrderState.orderStatus.equals("FINISH"))
              {
                Logger.d(XULJSCmdLogic.TAG, "APIAAAGetOrderState success!");
                XULJSCmdLogic.StatusChecker.this.onCheckRechargeSuccess(paramAnonymousAAAOrderState, "");
                return;
              }
              XULJSCmdLogic.StatusChecker.this._apiTaskId = -1;
            }
          });
        else if (this._type.equals("CHECKER_ALIPAY_AUTHORIZE"))
          this._apiTaskId = ServerApiManager.i().ApiAAAQueryPayAuthorizeStatus(str1, 1, str2, new SccmsApiAAAQueryPayAuthorizeStatusTask.ISccmsApiAAAQueryPayAuthorizeStatusTaskListener()
          {
            public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
            {
              XULJSCmdLogic.StatusChecker.this._apiTaskId = -1;
              Logger.e(XULJSCmdLogic.TAG, "ApiAAAQueryPayAuthorizeStatus error!");
            }

            public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, PayAuthorizeStatus paramAnonymousPayAuthorizeStatus)
            {
              if (paramAnonymousPayAuthorizeStatus.err != 0)
              {
                XULJSCmdLogic.StatusChecker.this._apiTaskId = -1;
                Logger.e(XULJSCmdLogic.TAG, "ApiAAAQueryPayAuthorizeStatus failed!");
                return;
              }
              if (paramAnonymousPayAuthorizeStatus.type.equals("yes"))
              {
                Logger.d(XULJSCmdLogic.TAG, "ApiAAAQueryPayAuthorizeStatus success!");
                XULJSCmdLogic.this.xulFireEvent("appEvents:authorizeSuccess");
                return;
              }
              XULJSCmdLogic.StatusChecker.this._apiTaskId = -1;
            }
          });
      }
    }

    public void stop()
    {
      this._statusChecker = null;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.xul.XULJSCmdLogic
 * JD-Core Version:    0.6.2
 */