package com.starcor.hunan.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.text.TextUtils;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.PurchaseParam;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.DialogActivity;
import com.starcor.hunan.XULActivity;
import com.starcor.hunan.xiaomi.XiaoMiOAuthManager;
import com.starcor.hunan.xiaomi.XiaoMiOAuthManager.IXiaoMiLoginMgtv;
import com.starcor.report.ReportPageInfo;

public class TokenDialog
{
  public static final String MSG_TOKEN_EXPIRED = "尊敬的用户：\n您的账号信息已过期，请重新登录。";
  public static final String MSG_TOKEN_KICKED = "尊敬的用户：\n您的帐号已在其他设备登录，您已被迫下线，如果不是您本人的操作，请尽快修改您的密码。";
  public static final int TYPE_AUTH_FAIL = 20003;
  public static final int TYPE_NO_PROGRAM = 20004;
  public static final int TYPE_PREVIEW = 2009;
  public static final int TYPE_PROGRAM_EXPIRED = 20006;
  public static final int TYPE_PROGRAM_OFFLINE = 2008;
  public static final int TYPE_TOKEN_EXPIRED = 20000;
  public static final int TYPE_TOKEN_KICKED = 20001;
  public static final int TYPE_UNDER_TIME = 20005;
  public static final int TYPE_UNKONW_ERROR = 20007;
  private final String MSG_AUTH_FAIL = "尊敬的用户：\n该节目为付费内容，请开通服务后观看。";
  private final String MSG_NO_PROGRAM = "未找到节目，请稍后再试。";
  private final String MSG_PREVIEW = "尊敬的用户：\n您已试看结束，请开通服务后继续观看！";
  private final String MSG_PROGRAM_EXPIRED = "非常抱歉，节目已过期，请选择其他节目收看";
  private final String MSG_PROGRAM_OFFLINE = "非常抱歉，节目已下线，请选择其他节目收看";
  private final String MSG_UNDER_TIME = "节目未到播出时间，请到时收看。";
  private final String MSG_UNKONW_ERROR = "未知异常，请稍候再试。";
  boolean isNeedQuit = false;
  boolean isShowDialog = false;
  private TokenDialogListener lsr = null;
  private Context mContext = null;
  private CommDialog mDialog = null;
  protected String msg = "";
  private int type = 20000;
  PurchaseParam videoInfo;
  private XiaoMiLoginListener xiaomilsr = null;

  public TokenDialog(Context paramContext)
  {
    this.mContext = paramContext;
  }

  private void dealXiaoMiDialog(String paramString)
  {
    XiaoMiOAuthManager.getInstance().startLogin(this.mContext, paramString, new XiaoMiOAuthManager.IXiaoMiLoginMgtv()
    {
      public void onError()
      {
        TokenDialog.this.mDialog.dismiss();
        if (TokenDialog.this.lsr != null)
          TokenDialog.this.lsr.onCancel(TokenDialog.this.type);
      }

      public void onSuccess()
      {
        TokenDialog.this.mDialog.dismiss();
        if (TokenDialog.this.lsr != null)
          TokenDialog.this.lsr.onCancel(TokenDialog.this.type);
        if (TokenDialog.this.xiaomilsr != null)
          TokenDialog.this.xiaomilsr.onSuccess();
      }
    });
  }

  private int getButtonType(int paramInt)
  {
    int i = 1;
    if ((paramInt == 20004) || (paramInt == 20005) || (paramInt == 20006) || (paramInt == 2008) || (paramInt == 20007))
      i = 17;
    return i;
  }

  public void dismiss()
  {
    if (this.mDialog != null)
    {
      this.mDialog.dismiss();
      this.isShowDialog = false;
    }
  }

  public String getDialogMsg()
  {
    return this.msg;
  }

  public void setIsNeedQuit(boolean paramBoolean)
  {
    this.isNeedQuit = paramBoolean;
  }

  public void setListener(TokenDialogListener paramTokenDialogListener)
  {
    this.lsr = paramTokenDialogListener;
  }

  public void setPurchaseInfo(PurchaseParam paramPurchaseParam)
  {
    this.videoInfo = paramPurchaseParam;
    Logger.i("setPurchaseInfo videoinfo " + this.videoInfo.toString());
    if ((this.mContext != null) && ((this.mContext instanceof DialogActivity)))
      this.videoInfo.setPagename(((DialogActivity)this.mContext).curPageInfo.page);
  }

  public void setType(int paramInt)
  {
    this.type = paramInt;
  }

  public void setXiaoMiListener(XiaoMiLoginListener paramXiaoMiLoginListener)
  {
    this.xiaomilsr = paramXiaoMiLoginListener;
  }

  public void show()
  {
    if ((this.isShowDialog) || (this.mContext == null))
      return;
    this.mDialog = new CommDialog(this.mContext, 2131296258);
    if (this.type == 20000)
    {
      this.msg = "尊敬的用户：\n您的账号信息已过期，请重新登录。";
      this.mDialog.setButtonOkMsg("重新登录");
      GlobalLogic.getInstance().userLogout();
    }
    while (true)
    {
      int i = getButtonType(this.type);
      this.mDialog.setMessage(this.msg);
      this.mDialog.setType(i);
      this.mDialog.setTitle("提示");
      this.mDialog.setNegativeButton(new OnNegativeClickListener(null));
      this.mDialog.setPositiveButton(new OnPositiveClickListener(null));
      this.mDialog.setOnDismissListener(new OnDialogDismissListener(null));
      this.mDialog.show();
      this.isShowDialog = true;
      return;
      if (this.type == 20001)
      {
        this.msg = "尊敬的用户：\n您的帐号已在其他设备登录，您已被迫下线，如果不是您本人的操作，请尽快修改您的密码。";
        this.mDialog.setButtonOkMsg("重新登录");
        GlobalLogic.getInstance().userLogout();
      }
      else if (this.type == 20003)
      {
        this.msg = "尊敬的用户：\n该节目为付费内容，请开通服务后观看。";
        this.mDialog.setButtonOkMsg("确定");
      }
      else if (this.type == 20004)
      {
        this.msg = "未找到节目，请稍后再试。";
        this.mDialog.setButtonOkMsg("确定");
      }
      else if (this.type == 20005)
      {
        this.msg = "节目未到播出时间，请到时收看。";
        this.mDialog.setButtonOkMsg("确定");
      }
      else if (this.type == 20006)
      {
        this.msg = "非常抱歉，节目已过期，请选择其他节目收看";
        this.mDialog.setButtonOkMsg("确定");
      }
      else if (this.type == 2008)
      {
        this.msg = "非常抱歉，节目已下线，请选择其他节目收看";
        this.mDialog.setButtonOkMsg("确定");
      }
      else if (this.type == 20007)
      {
        this.msg = "未知异常，请稍候再试。";
        this.mDialog.setButtonOkMsg("确定");
      }
      else if (this.type == 2009)
      {
        this.msg = "尊敬的用户：\n您已试看结束，请开通服务后继续观看！";
        this.mDialog.setButtonOkMsg("开通观看");
      }
    }
  }

  private class OnDialogDismissListener
    implements DialogInterface.OnDismissListener
  {
    private OnDialogDismissListener()
    {
    }

    public void onDismiss(DialogInterface paramDialogInterface)
    {
      TokenDialog.this.isShowDialog = false;
      if (DeviceInfo.isXiaoMi());
      while ((TokenDialog.this.lsr == null) || (!TokenDialog.this.isNeedQuit))
        return;
      TokenDialog.this.lsr.onCancel(TokenDialog.this.type);
    }
  }

  private class OnNegativeClickListener
    implements DialogInterface.OnClickListener
  {
    private OnNegativeClickListener()
    {
    }

    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      if (TokenDialog.this.lsr != null)
        TokenDialog.this.lsr.onPositiveClick(TokenDialog.this.type);
      if ((TokenDialog.this.type == 20000) || (TokenDialog.this.type == 20001))
      {
        if (DeviceInfo.isXiaoMi())
        {
          TokenDialog.this.dealXiaoMiDialog("");
          return;
        }
        Intent localIntent1 = new Intent(TokenDialog.this.mContext, XULActivity.class);
        localIntent1.putExtra("xulIsClose", "true");
        localIntent1.addFlags(8388608);
        localIntent1.putExtra("xulPageId", "LoginAndRegistration");
        TokenDialog.this.mContext.startActivity(localIntent1);
        TokenDialog.this.mDialog.dismiss();
      }
      while (true)
      {
        TokenDialog.this.isShowDialog = false;
        return;
        if ((TokenDialog.this.type == 20003) || (TokenDialog.this.type == 2009))
        {
          Intent localIntent2 = new Intent(TokenDialog.this.mContext, XULActivity.class);
          String str = GlobalLogic.getInstance().getWebToken();
          GlobalLogic.getInstance().setPurchaseParam(TokenDialog.this.videoInfo);
          GlobalLogic.getInstance().setAutoJumpToDetailedPage(true);
          if (TextUtils.isEmpty(str))
          {
            if (DeviceInfo.isXiaoMi())
            {
              TokenDialog.this.dealXiaoMiDialog("PurchaseVIP");
              return;
            }
            localIntent2.putExtra("xulPageId", "LoginAndRegistration");
          }
          while (true)
          {
            localIntent2.addFlags(8388608);
            TokenDialog.this.mContext.startActivity(localIntent2);
            TokenDialog.this.mDialog.dismiss();
            break;
            localIntent2.putExtra("xulPageId", "PurchaseVIP");
            if ((DeviceInfo.isXiaoMi()) && (TokenDialog.this.lsr != null))
              TokenDialog.this.lsr.onCancel(TokenDialog.this.type);
          }
        }
        TokenDialog.this.mDialog.dismiss();
      }
    }
  }

  private class OnPositiveClickListener
    implements DialogInterface.OnClickListener
  {
    private OnPositiveClickListener()
    {
    }

    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      TokenDialog.this.mDialog.dismiss();
      TokenDialog.this.isShowDialog = false;
    }
  }

  public static abstract interface TokenDialogListener
  {
    public abstract void onCancel(int paramInt);

    public abstract void onPositiveClick(int paramInt);
  }

  public static abstract interface XiaoMiLoginListener
  {
    public abstract void onSuccess();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.TokenDialog
 * JD-Core Version:    0.6.2
 */