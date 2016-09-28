package com.starcor.hunan.xiaomi;

import android.content.Context;
import android.text.TextUtils;
import com.starcor.core.domain.UserCenterLogin;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.sccms.api.SccmsApiUserCenterVerifyTokenTask.ISccmsApiUserCenterVerifyTokenTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.xiaomi.mitv.payment.thirdsdk.util.XMAccountUtil;

public class XiaoMiUserCheck
{
  private static final String TAG = XiaoMiUserCheck.class.getSimpleName();
  IXiaoMiAccountVerifyListener lsr;

  private void VerifyToken()
  {
    Logger.i("VerifyToken --------");
    ServerApiManager.i().APIUserCenterVerifyToken(GlobalLogic.getInstance().getWebToken(), GlobalEnv.getInstance().getAAALicense(), new SccmsApiUserCenterVerifyTokenTask.ISccmsApiUserCenterVerifyTokenTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        XiaoMiUserCheck.this.onCheckTokenError();
        Logger.i("VerifyToken --------APIUserCenterVerifyToken onError");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserCenterLogin paramAnonymousUserCenterLogin)
      {
        Logger.i("VerifyToken  -------APIUserCenterVerifyToken onSuccess");
        if (paramAnonymousUserCenterLogin.err == 0)
          XiaoMiUserCheck.this.onCheckTokenSuccess(paramAnonymousUserCenterLogin);
        do
        {
          return;
          Logger.i("VerifyToken  -------APIUserCenterVerifyToken onSuccess result.status=" + paramAnonymousUserCenterLogin.status);
        }
        while ((GlobalLogic.getInstance().checkTokenStatus(paramAnonymousUserCenterLogin.err, paramAnonymousUserCenterLogin.status)) || (XiaoMiUserCheck.this.lsr == null));
        XiaoMiUserCheck.this.lsr.onUserCheckFinish(XiaoMiUserCheck.Status.FAILED);
      }
    });
  }

  private void onCheckTokenError()
  {
    this.lsr.onUserCheckFinish(Status.NETWORK_ERROR);
  }

  private void onCheckTokenSuccess(UserCenterLogin paramUserCenterLogin)
  {
    UserInfo localUserInfo = GlobalLogic.getInstance().getUserInfo();
    localUserInfo.loginMode = "xiaomi";
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
    localUserInfo.mi_mac_key = paramUserCenterLogin.mi_mac_key;
    localUserInfo.mi_userid = paramUserCenterLogin.mi_userid;
    localUserInfo.mi_email = paramUserCenterLogin.mi_email;
    localUserInfo.mi_mobile = paramUserCenterLogin.mi_mobile;
    localUserInfo.mi_access_token = paramUserCenterLogin.mi_access_token;
    if (!TextUtils.isEmpty(paramUserCenterLogin.ticket))
    {
      GlobalLogic.getInstance().userLogin(localUserInfo);
      if (!XMAccountUtil.hasAccount(App.getAppContext()))
        break label307;
      str = XMAccountUtil.getUserId(App.getAppContext());
      Logger.i("onCheckTokenSuccess userid=" + str + ",getUserId=" + GlobalLogic.getInstance().getUserInfo().mi_userid);
      if (str.equalsIgnoreCase(GlobalLogic.getInstance().getUserInfo().mi_userid))
        break label287;
      if (this.lsr == null)
        break label278;
      this.lsr.onUserCheckFinish(Status.FAILED);
    }
    label278: label287: label307: 
    while (this.lsr == null)
    {
      do
      {
        String str;
        return;
        GlobalLogic.getInstance().userLogout();
        if (this.lsr == null)
          break;
        this.lsr.onUserCheckFinish(Status.FAILED);
        return;
        Logger.i(TAG, "no lister");
        return;
      }
      while (this.lsr == null);
      this.lsr.onUserCheckFinish(Status.SUCCESS);
      return;
    }
    this.lsr.onUserCheckFinish(Status.NONE);
  }

  public void setListener(IXiaoMiAccountVerifyListener paramIXiaoMiAccountVerifyListener)
  {
    this.lsr = paramIXiaoMiAccountVerifyListener;
  }

  public void start(Context paramContext)
  {
    if (!XMAccountUtil.hasAccount(paramContext))
      return;
    VerifyToken();
  }

  public void start(IXiaoMiAccountVerifyListener paramIXiaoMiAccountVerifyListener)
  {
    if (paramIXiaoMiAccountVerifyListener != null)
      setListener(paramIXiaoMiAccountVerifyListener);
    if (!GlobalLogic.getInstance().isUserLogined())
    {
      Logger.i("startXiaoMiAccountVerify 未登录，直接不处理--------");
      paramIXiaoMiAccountVerifyListener.onUserCheckFinish(Status.NONE);
      return;
    }
    VerifyToken();
  }

  public static abstract interface IXiaoMiAccountVerifyListener
  {
    public abstract void onUserCheckFinish(XiaoMiUserCheck.Status paramStatus);
  }

  public static enum Status
  {
    static
    {
      FAILED = new Status("FAILED", 1);
      SUCCESS = new Status("SUCCESS", 2);
      NETWORK_ERROR = new Status("NETWORK_ERROR", 3);
      Status[] arrayOfStatus = new Status[4];
      arrayOfStatus[0] = NONE;
      arrayOfStatus[1] = FAILED;
      arrayOfStatus[2] = SUCCESS;
      arrayOfStatus[3] = NETWORK_ERROR;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.xiaomi.XiaoMiUserCheck
 * JD-Core Version:    0.6.2
 */