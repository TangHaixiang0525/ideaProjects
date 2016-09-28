package com.starcor.hunan.widget;

import android.content.Context;
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

public class ExitDialog extends BaseDialog
{
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
        ExitDialog.this.dismiss();
      }
      while (ExitDialog.this.mPositiveButtonListener == null);
      ExitDialog.this.mPositiveButtonListener.onClick(ExitDialog.this, -1);
    }
  };
  private Context context;
  private int default_height = 376;
  private int default_width = 444;
  private TextView dialogTitle;
  private TextView dialogTitleDesc;
  private DialogInterface.OnClickListener mPositiveButtonListener;
  private TextView messageText;
  private String msg;

  public ExitDialog(Context paramContext)
  {
    super(paramContext);
    this.context = paramContext;
  }

  public ExitDialog(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
    this.context = paramContext;
  }

  public ExitDialog(Context paramContext, boolean paramBoolean, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    super(paramContext, paramBoolean, paramOnCancelListener);
    this.context = paramContext;
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

  public void initViews()
  {
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131165264);
    localLinearLayout.getLayoutParams().height = App.Operation(195.0F);
    ((LinearLayout)findViewById(2131165258)).getLayoutParams().height = App.Operation(66.0F);
    this.dialogTitle = ((TextView)findViewById(2131165259));
    this.dialogTitle.setText("提示");
    this.dialogTitle.getPaint().setShadowLayer(3.0F, 1.0F, 1.0F, -16777216);
    this.dialogTitleDesc = ((TextView)findViewById(2131165260));
    this.dialogTitleDesc.getPaint().setShadowLayer(3.0F, 1.0F, 1.0F, -16777216);
    this.dialogTitleDesc.setTextSize(0, App.Operation(20.0F));
    this.dialogTitle.setPadding(App.Operation(40.0F), 0, App.Operation(8.0F), App.Operation(12.0F));
    this.dialogTitleDesc.setPadding(0, 0, 0, App.Operation(14.0F));
    this.dialogTitleDesc.setText("按\"返回键\"取消");
    this.dialogTitle.setGravity(83);
    this.dialogTitleDesc.setGravity(83);
    this.dialogTitle.setTextSize(0, App.Operation(26.0F));
    this.dialogTitle.setText("提示");
    ((ImageView)findViewById(2131165263)).getLayoutParams().height = App.Operation(3.0F);
    this.dialogTitle.setTextColor(-1291845633);
    this.dialogTitle.getPaint().setFakeBoldText(true);
    this.dialogTitleDesc.setTextColor(-1291845633);
    this.dialogTitleDesc.setText("按\"返回键\"取消");
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
    localButton.setText("确定");
    localButton.setGravity(17);
    localButton.getPaint().setTextSize(App.Operation(22.0F));
    localButton.getLayoutParams().width = App.Operation(140.0F);
    localButton.getLayoutParams().height = App.Operation(44.0F);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903061);
    WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
    localLayoutParams.height = App.OperationHeight(this.default_height);
    localLayoutParams.width = App.OperationHeight(this.default_width);
    getWindow().setAttributes(localLayoutParams);
    initViews();
  }

  public void setMessage(int paramInt)
  {
    this.msg = this.context.getResources().getString(paramInt);
    if (this.messageText != null)
      this.messageText.setText(this.msg);
  }

  public void setMessage(String paramString)
  {
    this.msg = paramString;
    if (this.messageText != null)
      this.messageText.setText(paramString);
  }

  public void setPositiveButtonListener(DialogInterface.OnClickListener paramOnClickListener)
  {
    this.mPositiveButtonListener = paramOnClickListener;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.ExitDialog
 * JD-Core Version:    0.6.2
 */