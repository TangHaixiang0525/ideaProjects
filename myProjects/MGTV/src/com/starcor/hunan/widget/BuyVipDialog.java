package com.starcor.hunan.widget;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuyVipDialog extends BaseDialog
{
  public static final int TYPE_BUTTON_CANCEL = 4;
  public static final int TYPE_BUTTON_OK = 1;
  private View.OnClickListener clickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      default:
      case 2131165266:
      }
      do
      {
        return;
        Logger.i("ApplicationException", "click ok, dialog dismiss");
        BuyVipDialog.this.dismiss();
      }
      while (BuyVipDialog.this.mNegativeButtonListener == null);
      BuyVipDialog.this.mNegativeButtonListener.onClick(BuyVipDialog.this, -2);
    }
  };
  private int default_height = 376;
  private int default_width = 444;
  private TextView dialogTitle;
  private TextView dialogTitleDesc;
  private Context mContext;
  private DialogInterface.OnClickListener mNegativeButtonListener;
  private DialogInterface.OnClickListener mPositiveButtonListener;
  private TextView messageText;
  private String msg;
  private boolean needFinish;
  private CharSequence title;
  private int type = 5;

  public BuyVipDialog(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    onCreate(null);
  }

  public BuyVipDialog(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
    this.mContext = paramContext;
    onCreate(null);
  }

  public BuyVipDialog(Context paramContext, boolean paramBoolean, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    super(paramContext, paramBoolean, paramOnCancelListener);
    onCreate(null);
  }

  private void initViews()
  {
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131165264);
    localLinearLayout.getLayoutParams().height = App.Operation(195.0F);
    ((LinearLayout)findViewById(2131165258)).getLayoutParams().height = App.Operation(66.0F);
    this.dialogTitle = ((TextView)findViewById(2131165259));
    this.dialogTitle.getPaint().setShadowLayer(3.0F, 1.0F, 1.0F, -16777216);
    this.dialogTitleDesc = ((TextView)findViewById(2131165260));
    this.dialogTitleDesc.getPaint().setShadowLayer(3.0F, 1.0F, 1.0F, -16777216);
    this.dialogTitleDesc.setTextSize(0, App.Operation(20.0F));
    this.dialogTitle.setPadding(App.Operation(40.0F), 0, App.Operation(8.0F), App.Operation(12.0F));
    this.dialogTitleDesc.setPadding(0, 0, 0, App.Operation(14.0F));
    updateTitle();
    this.dialogTitle.setGravity(83);
    this.dialogTitleDesc.setGravity(83);
    this.dialogTitle.setTextSize(0, App.Operation(26.0F));
    this.dialogTitle.getPaint().setFakeBoldText(true);
    ((ImageView)findViewById(2131165263)).getLayoutParams().height = App.Operation(3.0F);
    this.dialogTitle.setTextColor(-1291845633);
    this.dialogTitleDesc.setTextColor(-1291845633);
    this.messageText = ((TextView)findViewById(2131165265));
    this.messageText.setText(this.msg);
    this.messageText.setTextColor(-855638017);
    this.messageText.setTextSize(0, App.Operation(22.0F));
    this.messageText.setLineSpacing(1.0F, 1.2F);
    ((ViewGroup.MarginLayoutParams)this.messageText.getLayoutParams()).leftMargin = App.Operation(5.0F);
    ((ViewGroup.MarginLayoutParams)this.messageText.getLayoutParams()).rightMargin = App.Operation(5.0F);
    ((RelativeLayout.LayoutParams)localLinearLayout.getLayoutParams()).topMargin = App.Operation(28.0F);
    Button localButton = (Button)findViewById(2131165266);
    localButton.setOnClickListener(this.clickListener);
    localButton.setTextColor(-1);
    localButton.setText("开通VIP");
    localButton.setGravity(17);
    localButton.getPaint().setTextSize(App.Operation(22.0F));
    localButton.getLayoutParams().width = App.Operation(140.0F);
    localButton.getLayoutParams().height = App.Operation(44.0F);
    if ((0x1 & this.type) == 0)
      localButton.setVisibility(8);
  }

  private void refreshDialog(String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    int i;
    do
    {
      return;
      i = paramString.length() / 9;
    }
    while (i <= 2);
    this.default_height = ((int)((1.0D + 0.1D * (i - 2) / 2.0D) * this.default_height));
    this.default_width = ((int)((1.0D + 0.1D * (i - 2)) * this.default_width));
  }

  public int getType()
  {
    return this.type;
  }

  public boolean isNeedFinish()
  {
    return this.needFinish;
  }

  public void onBackPressed()
  {
    super.onBackPressed();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.title = "提示 按\"返回键\"取消";
    setContentView(2130903053);
    WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
    localLayoutParams.height = App.OperationHeight(this.default_height);
    localLayoutParams.width = App.OperationHeight(this.default_width);
    getWindow().setAttributes(localLayoutParams);
    initViews();
  }

  public void setMessage(int paramInt)
  {
    this.msg = this.mContext.getResources().getString(paramInt);
    if (this.messageText != null)
      this.messageText.setText(this.msg);
  }

  public void setMessage(String paramString)
  {
    this.msg = paramString;
    if (this.messageText != null)
      this.messageText.setText(paramString);
  }

  public void setNeedFinish(boolean paramBoolean)
  {
    this.needFinish = paramBoolean;
    if (!paramBoolean)
    {
      setOnCancelListener(null);
      return;
    }
    setOnCancelListener(new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramAnonymousDialogInterface)
      {
        Logger.e("CommDialog", "setOnCancelListener onCancel need:" + BuyVipDialog.this.needFinish);
        if (!BuyVipDialog.this.needFinish)
          return;
        try
        {
          ((Activity)BuyVipDialog.this.mContext).finish();
          BuyVipDialog.access$102(BuyVipDialog.this, false);
          return;
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
      }
    });
    Logger.e("CommDialog", "CommDialog setNeedFinish");
  }

  public void setNegativeButton(DialogInterface.OnClickListener paramOnClickListener)
  {
    this.mNegativeButtonListener = paramOnClickListener;
  }

  public void setPositiveButton(DialogInterface.OnClickListener paramOnClickListener)
  {
    this.mPositiveButtonListener = paramOnClickListener;
  }

  public void setTitle(int paramInt)
  {
    this.title = this.mContext.getResources().getString(paramInt);
  }

  public void setTitle(CharSequence paramCharSequence)
  {
    this.title = paramCharSequence;
  }

  public void setType(int paramInt)
  {
    this.type = paramInt;
  }

  public void updateTitle()
  {
    Matcher localMatcher = Pattern.compile("(\\S+)\\s+(\\S+)").matcher(this.title);
    if (localMatcher.matches())
    {
      this.dialogTitle.setText(localMatcher.group(1));
      this.dialogTitleDesc.setText(localMatcher.group(2));
      Logger.i("CommDialog", "updateTitle matcher");
      return;
    }
    Logger.i("CommDialog", "updateTitle");
    this.dialogTitle.setText("提示");
    this.dialogTitleDesc.setText("按\"返回键\"取消");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.BuyVipDialog
 * JD-Core Version:    0.6.2
 */