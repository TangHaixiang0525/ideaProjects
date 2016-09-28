package com.starcor.hunan.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import com.starcor.core.exception.ApplicationException;
import com.starcor.core.utils.Logger;

public class MplayerDialog
{
  private static final String TAG = MplayerDialog.class.getSimpleName();
  int button_type = 1;
  private CommDialog dialog = null;
  private MplayerDialogListener lsr = null;
  private String msg = "";
  private int type = 0;

  public MplayerDialog(Context paramContext)
  {
    this.dialog = new CommDialog(paramContext, 2131296258);
  }

  public String getDialogMsg()
  {
    return this.msg;
  }

  public void setErrorCode(String paramString)
  {
    this.dialog.setType(0x10 | this.button_type);
    this.msg = ApplicationException.getErrorDiscrib(paramString);
    this.dialog.setMessage(this.msg);
    this.dialog.setNegativeButton(new onButtonListener(null));
    this.dialog.setOnDismissListener(new onCancelListener(null));
    Logger.i(TAG, "errCode:" + paramString);
  }

  public void setListener(MplayerDialogListener paramMplayerDialogListener)
  {
    this.lsr = paramMplayerDialogListener;
  }

  public void setType(int paramInt)
  {
    this.type = paramInt;
    if (this.button_type == 1)
      this.dialog.setButtonOkMsg("确定");
    String str;
    if (this.type == 2)
      str = "1003002";
    while (true)
    {
      this.dialog.setType(0x10 | this.button_type);
      this.msg = ApplicationException.getErrorDiscrib(str);
      this.dialog.setMessage(this.msg);
      this.dialog.setNegativeButton(new onButtonListener(null));
      Logger.i(TAG, "errCode:" + str);
      return;
      if (this.type == 5)
        str = "1003301";
      else if (this.type == 14)
        str = "1002300";
      else if (this.type == 3)
        str = "1003300";
      else
        str = "1003001";
    }
  }

  public void show()
  {
    if (this.dialog.isShowing())
      return;
    this.dialog.show();
  }

  public static abstract interface MplayerDialogListener
  {
    public abstract void onCancel(int paramInt);
  }

  private class onButtonListener
    implements DialogInterface.OnClickListener
  {
    private onButtonListener()
    {
    }

    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      paramDialogInterface.dismiss();
    }
  }

  private class onCancelListener
    implements DialogInterface.OnDismissListener
  {
    private onCancelListener()
    {
    }

    public void onDismiss(DialogInterface paramDialogInterface)
    {
      if (MplayerDialog.this.lsr != null)
        MplayerDialog.this.lsr.onCancel(MplayerDialog.this.type);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.MplayerDialog
 * JD-Core Version:    0.6.2
 */