package com.starcor.settings.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.starcor.settings.R.id;
import com.starcor.settings.R.layout;

public class CommDialog extends Dialog
{
  public static final int TYPE_BUTTON_CANCEL = 4;
  public static final int TYPE_BUTTON_OK = 1;
  private View.OnClickListener clickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if (paramAnonymousView.getId() == R.id.ib_ok)
      {
        CommDialog.this.dismiss();
        if (CommDialog.this.mNegativeButtonListener != null)
          CommDialog.this.mNegativeButtonListener.onClick(CommDialog.this, -2);
      }
      do
      {
        do
          return;
        while (paramAnonymousView.getId() != R.id.ib_cancel);
        CommDialog.this.dismiss();
      }
      while (CommDialog.this.mPositiveButtonListener == null);
      CommDialog.this.mPositiveButtonListener.onClick(CommDialog.this, -1);
    }
  };
  private int default_height = 280;
  private int default_width = 400;
  private TextView dialogTitle;
  private Context mContext;
  private DialogInterface.OnClickListener mNegativeButtonListener;
  private DialogInterface.OnClickListener mPositiveButtonListener;
  private TextView messageText;
  private String msg;
  private CharSequence title = "鎻愮ず:";
  private int type = 5;

  public CommDialog(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }

  public CommDialog(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
    this.mContext = paramContext;
  }

  public CommDialog(Context paramContext, boolean paramBoolean, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    super(paramContext, paramBoolean, paramOnCancelListener);
  }

  private void initViews()
  {
    this.dialogTitle = ((TextView)findViewById(R.id.dialog_title));
    this.dialogTitle.setText(this.title);
    this.dialogTitle.getLayoutParams().height = Tools.Operation(getContext(), 53.0F);
    this.dialogTitle.setGravity(19);
    this.dialogTitle.setTextSize(0, Tools.Operation(getContext(), 24.0F));
    this.dialogTitle.setPadding(Tools.Operation(getContext(), 30.0F), 0, 0, 0);
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)findViewById(R.id.iv_icon).getLayoutParams();
    localLayoutParams.topMargin = Tools.Operation(getContext(), 10.0F);
    localLayoutParams.width = Tools.Operation(getContext(), 25.0F);
    localLayoutParams.height = Tools.Operation(getContext(), 25.0F);
    this.messageText = ((TextView)findViewById(R.id.txt_message));
    this.messageText.setText(this.msg);
    this.messageText.setTextSize(0, Tools.Operation(getContext(), 22.0F));
    ((RelativeLayout.LayoutParams)this.messageText.getLayoutParams()).topMargin = Tools.Operation(getContext(), 15.0F);
    View localView1 = findViewById(R.id.ib_ok);
    localView1.setOnClickListener(this.clickListener);
    localView1.getLayoutParams().width = Tools.Operation(getContext(), 62.0F);
    localView1.getLayoutParams().height = Tools.Operation(getContext(), 37.0F);
    final View localView2 = findViewById(R.id.ib_cancel);
    localView2.setOnClickListener(this.clickListener);
    localView2.getLayoutParams().width = Tools.Operation(getContext(), 62.0F);
    localView2.getLayoutParams().height = Tools.Operation(getContext(), 37.0F);
    if ((0x4 & this.type) == 0)
    {
      localView2.setVisibility(8);
      return;
    }
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        localView2.requestFocusFromTouch();
      }
    }
    , 20L);
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

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.comm_dialog);
    WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
    localLayoutParams.height = Tools.Operation(getContext(), this.default_height);
    localLayoutParams.width = Tools.Operation(getContext(), this.default_width);
    getWindow().setAttributes(localLayoutParams);
    initViews();
  }

  public void setMessage(int paramInt)
  {
    this.msg = this.mContext.getResources().getString(paramInt);
    if (this.messageText != null)
      this.messageText.setText(this.msg);
    refreshDialog(this.msg);
  }

  public void setMessage(String paramString)
  {
    this.msg = paramString;
    if (this.messageText != null)
      this.messageText.setText(paramString);
    refreshDialog(paramString);
  }

  public void setNegativeButton(DialogInterface.OnClickListener paramOnClickListener)
  {
    this.mNegativeButtonListener = paramOnClickListener;
  }

  public void setPositiveButton(int paramInt, DialogInterface.OnClickListener paramOnClickListener)
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
 * Qualified Name:     com.starcor.settings.utils.CommDialog
 * JD-Core Version:    0.6.2
 */