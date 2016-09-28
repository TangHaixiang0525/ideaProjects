package com.starcor.core.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import com.starcor.hunan.widget.ExitDialog;

public class SupperToast
{
  public static void makeToast(Context paramContext, String paramString, int paramInt)
  {
    if (paramContext == null)
      return;
    try
    {
      ExitDialog localExitDialog = new ExitDialog(paramContext, 2131296258);
      localExitDialog.setMessage(paramString);
      localExitDialog.setPositiveButtonListener(new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
        }
      });
      localExitDialog.show();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.utils.SupperToast
 * JD-Core Version:    0.6.2
 */