package com.starcor.hunan.xiaomi;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import com.starcor.hunan.widget.CommDialog;

public class XiaoMiLoginTipDailog
{
  private CommDialog dialog = null;

  public XiaoMiLoginTipDailog(Context paramContext, DialogInterface.OnClickListener paramOnClickListener, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    this.dialog = new CommDialog(paramContext, 2131296258);
    this.dialog.setType(1);
    this.dialog.setTitle("提示");
    this.dialog.setTitle("提示");
    this.dialog.setMessage("尊敬的用户：\n\n您登录的账号发生变化，请重新使用小米账号进行登录。");
    this.dialog.setNegativeButton(paramOnClickListener);
    this.dialog.setButtonOkMsg("重新登录");
    this.dialog.setOnCancelListener(paramOnCancelListener);
  }

  public void show()
  {
    this.dialog.show();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.xiaomi.XiaoMiLoginTipDailog
 * JD-Core Version:    0.6.2
 */