package com.starcor.hunan.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import com.starcor.core.domain.AAAUseMovieCouponResult;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.DialogActivity;
import com.starcor.sccms.api.SccmsApiAAAUseMovieCouponTask.ISccmsApiAAAUseMovieCouponTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.XulView;
import org.json.JSONObject;

public class MovieCouponDialog
{
  private String import_id = "";
  private Context mContext = null;
  private XULDialog mDialog = null;
  private String okButton = "";
  private MovieCouponOkDialogListener okLsr = null;
  private String okMsg = "";
  private String tipButton = "";
  private MovieCouponTipDialogListener tipLsr = null;
  private String tipMsg = "";
  private String type = "0";

  public MovieCouponDialog(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.tipMsg = paramString1;
    this.okMsg = paramString2;
    this.tipButton = paramString3;
    this.okButton = paramString4;
    this.mContext = paramContext;
  }

  private void showCouponOkDialog()
  {
    this.mDialog = new XULDialog(this.mContext, "commonDialog", null)
    {
      protected boolean xulDoAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
      {
        if (paramAnonymousString2.equals("button_ok"))
        {
          if (MovieCouponDialog.this.okLsr != null)
            MovieCouponDialog.this.okLsr.onOkButtonClick();
          if (MovieCouponDialog.this.mDialog != null)
            MovieCouponDialog.this.mDialog.dismiss();
        }
        while (true)
        {
          return false;
          if (paramAnonymousString2.equals("button_cancel"))
          {
            if (MovieCouponDialog.this.okLsr != null)
              MovieCouponDialog.this.okLsr.onCancel();
            if (MovieCouponDialog.this.mDialog != null)
              MovieCouponDialog.this.mDialog.dismiss();
          }
        }
      }
    };
    this.mDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if ((paramAnonymousKeyEvent.getAction() == 0) && (paramAnonymousKeyEvent.getKeyCode() == 4))
        {
          if (MovieCouponDialog.this.okLsr != null)
            MovieCouponDialog.this.okLsr.onCancel();
          if (MovieCouponDialog.this.mDialog != null)
            MovieCouponDialog.this.mDialog.dismiss();
          return true;
        }
        return false;
      }
    });
    XULDialog localXULDialog = this.mDialog;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.okMsg;
    arrayOfObject[1] = this.okButton;
    localXULDialog.xulFireEvent("appEvents:displayMovieCouponExchangeResult", arrayOfObject);
    this.mDialog.show();
  }

  public void dissmissDialog()
  {
    if (this.mDialog != null)
      this.mDialog.dismiss();
  }

  public void setCouponType(String paramString)
  {
    this.type = paramString;
  }

  public void setMovieCouponOkDialogListener(MovieCouponOkDialogListener paramMovieCouponOkDialogListener)
  {
    this.okLsr = paramMovieCouponOkDialogListener;
  }

  public void setMovieCouponTipDialogListener(MovieCouponTipDialogListener paramMovieCouponTipDialogListener)
  {
    this.tipLsr = paramMovieCouponTipDialogListener;
  }

  public void showCouponTipDialog(String paramString)
  {
    this.import_id = paramString;
    this.mDialog = new XULDialog(this.mContext, "commonDialog", null)
    {
      protected boolean xulDoAction(XulView paramAnonymousXulView, String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Object paramAnonymousObject)
      {
        if (paramAnonymousString2.equals("button_ok"))
          if (MovieCouponDialog.this.tipLsr != null)
            MovieCouponDialog.this.tipLsr.onOkButtonClick();
        while (true)
        {
          return false;
          if (paramAnonymousString2.equals("button_cancel"))
          {
            if (MovieCouponDialog.this.tipLsr != null)
              MovieCouponDialog.this.tipLsr.onCancel();
            if (MovieCouponDialog.this.mDialog != null)
              MovieCouponDialog.this.mDialog.dismiss();
          }
        }
      }
    };
    this.mDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if ((paramAnonymousKeyEvent.getAction() == 0) && (paramAnonymousKeyEvent.getKeyCode() == 4))
        {
          if (MovieCouponDialog.this.tipLsr != null)
            MovieCouponDialog.this.tipLsr.onCancel();
          if (MovieCouponDialog.this.mDialog != null)
            MovieCouponDialog.this.mDialog.dismiss();
          return true;
        }
        return false;
      }
    });
    XULDialog localXULDialog = this.mDialog;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.tipMsg;
    arrayOfObject[1] = this.tipButton;
    localXULDialog.xulFireEvent("appEvents:initCouponTipDialog", arrayOfObject);
    this.mDialog.show();
  }

  public void startUserCoupon()
  {
    if ((this.mContext instanceof DialogActivity))
      ((DialogActivity)this.mContext).showLoaddingDialog();
    ServerApiManager.i().ApiAAAUseMovieCoupon(GlobalLogic.getInstance().getWebToken(), 1, GlobalEnv.getInstance().getAAALicense(), this.import_id, this.type, new SccmsApiAAAUseMovieCouponTask.ISccmsApiAAAUseMovieCouponTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e("观影券使用失败！");
        if ((MovieCouponDialog.this.mContext instanceof DialogActivity))
          ((DialogActivity)MovieCouponDialog.this.mContext).dismissLoaddingDialog();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AAAUseMovieCouponResult paramAnonymousAAAUseMovieCouponResult)
      {
        if ((MovieCouponDialog.this.mContext instanceof DialogActivity))
          ((DialogActivity)MovieCouponDialog.this.mContext).dismissLoaddingDialog();
        if (paramAnonymousAAAUseMovieCouponResult.err != 0)
        {
          Logger.e("观影券使用失败！result.error=" + paramAnonymousAAAUseMovieCouponResult.toString());
          return;
        }
        if (MovieCouponDialog.this.mDialog != null)
          MovieCouponDialog.this.mDialog.dismiss();
        MovieCouponDialog.this.showCouponOkDialog();
      }
    });
  }

  public static abstract interface MovieCouponOkDialogListener
  {
    public abstract void onCancel();

    public abstract void onOkButtonClick();
  }

  public static abstract interface MovieCouponTipDialogListener
  {
    public abstract void onCancel();

    public abstract void onOkButtonClick();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.MovieCouponDialog
 * JD-Core Version:    0.6.2
 */