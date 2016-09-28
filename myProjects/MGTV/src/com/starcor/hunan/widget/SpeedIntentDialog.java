package com.starcor.hunan.widget;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextPaint;
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
import com.starcor.config.AppFuncCfg;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.MainActivityV2;
import com.starcor.hunan.ServiceActivity;
import com.starcor.hunan.UserFeedbackActivity;

public class SpeedIntentDialog extends BaseDialog
{
  private static final String TAG = SpeedIntentDialog.class.getSimpleName();
  public static final int TYPE_USER_FEEDBACK = 1;
  private int default_height = 376;
  private int default_width = 444;
  private TextView dialogTitle;
  private TextView dialogTitleDesc;
  private Context mContext;
  private DialogInterface.OnClickListener mPositiveButtonListener;
  private TextView messageText;
  private String msg;
  private View.OnClickListener onClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      default:
      case 2131165266:
        do
        {
          return;
          SpeedIntentDialog.this.dismiss();
        }
        while (SpeedIntentDialog.this.mPositiveButtonListener == null);
        SpeedIntentDialog.this.mPositiveButtonListener.onClick(SpeedIntentDialog.this, -1);
        return;
      case 2131165334:
      }
      if (AppFuncCfg.FUNCTION_ENABLE_NETWORK_BUTTON)
      {
        SpeedIntentDialog.this.goToNetwork();
        return;
      }
      SpeedIntentDialog.this.goToUserFeedback();
    }
  };
  private CharSequence title = "提示  按“返回”键取消";
  private int type = 0;

  public SpeedIntentDialog(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
    this.mContext = paramContext;
  }

  private void goToNetwork()
  {
    Intent localIntent = new Intent();
    localIntent.setClass(this.mContext, ServiceActivity.class);
    localIntent.putExtra("showNetworkSpeedUpView", "m_net_test_page");
    this.mContext.startActivity(localIntent);
    if ((this.mContext instanceof MainActivityV2))
    {
      dismiss();
      return;
    }
    ((Activity)this.mContext).finish();
  }

  private void goToUserFeedback()
  {
    Intent localIntent = new Intent(this.mContext, UserFeedbackActivity.class);
    localIntent.putExtra("xulPageId", "UserFeedbackPage");
    localIntent.putExtra("xulData", "");
    localIntent.addFlags(8388608);
    this.mContext.startActivity(localIntent);
    if ((this.mContext instanceof MainActivityV2))
    {
      dismiss();
      return;
    }
    ((Activity)this.mContext).finish();
  }

  private void initViews()
  {
    Logger.i(TAG, "SpeedIntentDialog   initview");
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131165264);
    localLinearLayout.getLayoutParams().height = App.Operation(195.0F);
    ((LinearLayout)findViewById(2131165258)).getLayoutParams().height = App.Operation(66.0F);
    this.dialogTitle = ((TextView)findViewById(2131165259));
    this.dialogTitle.setText("提示");
    this.dialogTitleDesc = ((TextView)findViewById(2131165260));
    this.dialogTitleDesc.setTextSize(0, App.Operation(20.0F));
    this.dialogTitleDesc.setTextColor(-1291845633);
    this.dialogTitleDesc.setText("按\"返回键\"取消");
    this.dialogTitle.setTextColor(-1291845633);
    this.dialogTitle.setGravity(83);
    this.dialogTitle.setTextSize(0, App.Operation(26.0F));
    this.dialogTitle.getPaint().setFakeBoldText(true);
    this.dialogTitle.getPaint().setShadowLayer(3.0F, 1.0F, 1.0F, -16777216);
    this.dialogTitleDesc.getPaint().setShadowLayer(3.0F, 1.0F, 1.0F, -16777216);
    this.dialogTitle.setPadding(App.Operation(40.0F), 0, App.Operation(8.0F), App.Operation(12.0F));
    this.dialogTitleDesc.setPadding(0, 0, 0, App.Operation(14.0F));
    this.dialogTitleDesc.setGravity(83);
    ((ImageView)findViewById(2131165263)).getLayoutParams().height = App.Operation(1.0F);
    ((RelativeLayout.LayoutParams)localLinearLayout.getLayoutParams()).topMargin = App.Operation(28.0F);
    this.messageText = ((TextView)findViewById(2131165265));
    this.messageText.setText(this.msg);
    this.messageText.setTextColor(-855638017);
    this.messageText.setTextSize(0, App.Operation(22.0F));
    this.messageText.setLineSpacing(1.0F, 1.2F);
    ((ViewGroup.MarginLayoutParams)this.messageText.getLayoutParams()).leftMargin = App.Operation(5.0F);
    ((ViewGroup.MarginLayoutParams)this.messageText.getLayoutParams()).rightMargin = App.Operation(5.0F);
    Button localButton1 = (Button)findViewById(2131165266);
    localButton1.setOnClickListener(this.onClickListener);
    localButton1.setTextColor(-1);
    localButton1.setText("确定");
    localButton1.setText("确定");
    localButton1.setGravity(17);
    localButton1.getPaint().setTextSize(App.Operation(22.0F));
    localButton1.getLayoutParams().width = App.Operation(140.0F);
    localButton1.getLayoutParams().height = App.Operation(44.0F);
    Button localButton2 = (Button)findViewById(2131165334);
    localButton2.setTextColor(-1);
    localButton2.setGravity(17);
    localButton2.getPaint().setTextSize(App.Operation(22.0F));
    localButton2.getLayoutParams().width = App.Operation(140.0F);
    localButton2.getLayoutParams().height = App.Operation(44.0F);
    localButton2.setOnClickListener(this.onClickListener);
    if (AppFuncCfg.FUNCTION_ENABLE_NETWORK_BUTTON)
      localButton2.setText("网络优化");
    do
    {
      return;
      localButton2.setText("问题反馈");
    }
    while ((0x1 & this.type) != 0);
    localButton2.setVisibility(8);
  }

  public int getType()
  {
    return this.type;
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903078);
    WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
    localLayoutParams.height = App.OperationHeight(this.default_height);
    localLayoutParams.width = App.OperationHeight(this.default_width);
    getWindow().setAttributes(localLayoutParams);
    initViews();
    Logger.i(TAG, "SpeedIntentDialog   oncreat");
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

  public void setPositiveButtonListener(DialogInterface.OnClickListener paramOnClickListener)
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
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.SpeedIntentDialog
 * JD-Core Version:    0.6.2
 */