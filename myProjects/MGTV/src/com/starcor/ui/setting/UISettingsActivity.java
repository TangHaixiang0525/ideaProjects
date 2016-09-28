package com.starcor.ui.setting;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout.LayoutParams;
import com.starcor.config.DeviceInfo;
import com.starcor.core.http.BitmapCache;
import com.starcor.hunan.App;
import com.starcor.hunan.BaseActivity;
import com.starcor.hunan.widget.HighLightButton;

public class UISettingsActivity extends BaseActivity
  implements View.OnClickListener
{
  private static final String TAG = "UISettingsActivity";
  private HighLightButton btnDisplay;
  private HighLightButton btnNetWork;
  private HighLightButton clickView;
  private DisPlayItem disPlayItem;
  private LinearLayout leftContent;
  private LinearLayout rightContent;

  private void addDisPlayView()
  {
    this.disPlayItem = new DisPlayItem(this);
    this.disPlayItem.setClickable(true);
    this.rightContent.addView(this.disPlayItem);
  }

  private void initViews()
  {
    this.leftContent = ((LinearLayout)findViewById(2131165248));
    ViewGroup.LayoutParams localLayoutParams = this.leftContent.getLayoutParams();
    localLayoutParams.width = App.OperationHeight(230);
    localLayoutParams.height = -1;
    this.leftContent.setOrientation(1);
    this.leftContent.setBackgroundColor(getResources().getColor(2131099652));
    this.leftContent.setPadding(0, App.OperationHeight(150), 0, 0);
    this.btnDisplay = new HighLightButton(this);
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(-1, App.OperationHeight(47));
    localLayoutParams1.topMargin = App.OperationHeight(18);
    this.btnDisplay.setText("显示设置");
    this.btnDisplay.setId(2131165202);
    this.btnDisplay.setSelectedFalg(true);
    this.btnDisplay.setOnClickListener(this);
    this.btnDisplay.setNextFocusUpId(this.btnDisplay.getId());
    this.btnDisplay.setNextFocusUpId(this.btnDisplay.getId());
    this.leftContent.addView(this.btnDisplay, localLayoutParams1);
    this.btnNetWork = new HighLightButton(this);
    this.btnNetWork.setText("网络设置");
    this.btnNetWork.setId(2131165201);
    this.btnNetWork.setOnClickListener(this);
    this.btnNetWork.setNextFocusDownId(this.btnNetWork.getId());
    this.leftContent.addView(this.btnNetWork, localLayoutParams1);
    this.rightContent = ((LinearLayout)findViewById(2131165225));
    this.rightContent.setPadding(App.OperationHeight(100), App.OperationHeight(100), 0, 0);
    this.rightContent.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapCache.getInstance(this).getBitmapFromCache("movelist_bg.jpg")));
    RelativeLayout.LayoutParams localLayoutParams2 = (RelativeLayout.LayoutParams)((ImageView)findViewById(2131165249)).getLayoutParams();
    localLayoutParams2.leftMargin = App.OperationHeight(221);
    localLayoutParams2.width = App.OperationHeight(36);
    localLayoutParams2.height = App.OperationHeight(649);
    localLayoutParams2.addRule(12);
    addDisPlayView();
    this.clickView = this.btnDisplay;
  }

  private void startDisPlayActivity()
  {
    Intent localIntent = new Intent("com.starcor.setting.intent.action.SHOW_DISPLAY_REPRODUCE_SETTING");
    localIntent.setFlags(268435456);
    try
    {
      startActivity(localIntent);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void startNetWorkSettingActivity()
  {
    Intent localIntent = new Intent("android.settings.WIRELESS_SETTINGS");
    localIntent.setFlags(268435456);
    try
    {
      this.context.startActivity(localIntent);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void click(View paramView)
  {
    if (DeviceInfo.getFactory() == 900013001)
      startDisPlayActivity();
  }

  public void onClick(View paramView)
  {
    if (this.clickView != null)
      this.clickView.setSelectedFalg(false);
    this.clickView = ((HighLightButton)paramView);
    this.clickView.setSelectedFalg(true);
    if (paramView.equals(this.btnDisplay))
    {
      this.rightContent.removeAllViews();
      addDisPlayView();
    }
    if ((paramView.equals(this.btnNetWork)) && (DeviceInfo.getFactory() == 900013001))
      startNetWorkSettingActivity();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    initXul("CommonPage");
    setContentView(2130903048);
    xulUpdateTitle("设置", "Settings");
    initViews();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.ui.setting.UISettingsActivity
 * JD-Core Version:    0.6.2
 */