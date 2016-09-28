package com.starcor.hunan.xiaomi;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.text.TextUtils;
import com.starcor.core.domain.AuthState;
import com.starcor.core.domain.PurchaseParam;
import com.starcor.core.domain.UserAuthV2;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.epgapi.params.GetUserAuthV2Params;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.logic.UserCPLLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.DetailPageActivity;
import com.starcor.hunan.DialogActivity;
import com.starcor.hunan.MainActivityV2;
import com.starcor.hunan.MyMediaActivityV2;
import com.starcor.hunan.XULActivity;
import com.starcor.sccms.api.SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.xiaomi.mitv.payment.thirdsdk.util.XMAccountUtil;

public class ActivityUserCheckLogic
{
  private static final String TAG = "ActivityUserCheckLogic";
  private static ActivityUserCheckLogic _instance = null;

  private void dismissLoadding(Context paramContext)
  {
    if ((paramContext != null) && ((paramContext instanceof DialogActivity)) && (!((DialogActivity)paramContext).isFinishing()))
      ((DialogActivity)paramContext).dismissLoaddingDialog();
  }

  public static ActivityUserCheckLogic getInstance()
  {
    if (_instance == null)
      _instance = new ActivityUserCheckLogic();
    return _instance;
  }

  private void isNeedBuy(final Context paramContext)
  {
    PurchaseParam localPurchaseParam = GlobalLogic.getInstance().getPurchaseParam();
    if ((localPurchaseParam != null) && (localPurchaseParam.autoJump) && (!TextUtils.isEmpty(localPurchaseParam.videoId)))
      ServerApiManager.i().APIGetUserAuthV2(new GetUserAuthV2Params(localPurchaseParam.videoId, localPurchaseParam.videoType, GlobalLogic.getInstance().getQuality()), new SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          Logger.i("APIGetUserAuthV2 error!");
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, UserAuthV2 paramAnonymousUserAuthV2)
        {
          Logger.i("APIGetUserAuthV2 success!");
          if ((paramAnonymousUserAuthV2.state.state == 0) && ((paramContext instanceof Activity)))
            ((Activity)paramContext).finish();
        }
      });
  }

  private void showDialog(final Context paramContext)
  {
    new XiaoMiLoginTipDailog(paramContext, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        ActivityUserCheckLogic.this.showLoadding(paramContext);
        XiaoMiOAuthManager.getInstance().startLogin(paramContext, "", new XiaoMiOAuthManager.IXiaoMiLoginMgtv()
        {
          public void onError()
          {
            Logger.i("ActivityUserCheckLogic", "小米授权登录失败");
          }

          public void onSuccess()
          {
            ActivityUserCheckLogic.this.dismissLoadding(ActivityUserCheckLogic.1.this.val$context);
            if ((ActivityUserCheckLogic.1.this.val$context instanceof MyMediaActivityV2))
            {
              UserCPLLogic.getInstance().dirtyCollectList();
              UserCPLLogic.getInstance().dirtyPlayRecordList();
              UserCPLLogic.getInstance().dirtyTracePlayList();
              ((MyMediaActivityV2)ActivityUserCheckLogic.1.this.val$context).refreshViews();
            }
            String str;
            do
            {
              do
              {
                do
                {
                  return;
                  if ((ActivityUserCheckLogic.1.this.val$context instanceof DetailPageActivity))
                  {
                    ((DetailPageActivity)ActivityUserCheckLogic.1.this.val$context).getUserReAuth();
                    return;
                  }
                }
                while (!(ActivityUserCheckLogic.1.this.val$context instanceof XULActivity));
                str = ((XULActivity)ActivityUserCheckLogic.1.this.val$context).getIntent().getStringExtra("xulPageId");
              }
              while (TextUtils.isEmpty(str));
              ((DialogActivity)ActivityUserCheckLogic.1.this.val$context).xulFireEvent("appEvents:refreshUser");
            }
            while (!str.equals("PurchaseVIP"));
            ActivityUserCheckLogic.this.isNeedBuy(ActivityUserCheckLogic.1.this.val$context);
          }
        });
      }
    }
    , null).show();
  }

  private void showLoadding(Context paramContext)
  {
    if ((paramContext != null) && ((paramContext instanceof DialogActivity)) && (!((DialogActivity)paramContext).isFinishing()))
      ((DialogActivity)paramContext).showLoaddingDialog();
  }

  public void startXiaoMiUserCheck(Context paramContext)
  {
    Logger.i("startXiaoMiUserCheck  In");
    if (XMAccountUtil.hasAccount(App.getAppContext()))
    {
      Logger.i("ActivityUserCheckLogic", "小米系统已登录");
      String str = XMAccountUtil.getUserId(App.getAppContext());
      if ((GlobalLogic.getInstance().getUserInfo() == null) || (!str.equalsIgnoreCase(GlobalLogic.getInstance().getUserInfo().mi_userid)))
      {
        Logger.i("ActivityUserCheckLogic", "账号不一致弹框提示");
        if (!(paramContext instanceof MainActivityV2))
          showDialog(paramContext);
      }
    }
    do
    {
      return;
      Logger.i("ActivityUserCheckLogic", "账号一致不弹框提示");
      return;
      Logger.i("ActivityUserCheckLogic", "小米系统未登录");
    }
    while (!GlobalLogic.getInstance().isUserLogined());
    GlobalLogic.getInstance().userLogout();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.xiaomi.ActivityUserCheckLogic
 * JD-Core Version:    0.6.2
 */