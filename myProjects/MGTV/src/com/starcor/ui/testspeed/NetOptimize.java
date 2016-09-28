package com.starcor.ui.testspeed;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.starcor.core.service.BitmapService;
import com.starcor.hunan.App;
import com.starcor.hunan.interfaces.ServiceSelectBtnCallBack;
import com.starcor.hunan.widget.HighLightButton;

public class NetOptimize extends LinearLayout
  implements ServiceSelectBtnCallBack
{
  private static final String TAG = "NetOptimize";
  private int flag;
  private Context mContext;
  private OnStartListener mOnstartListener;
  public HighLightButton tv_start;

  public NetOptimize(Context paramContext, int paramInt)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.flag = paramInt;
    initViews();
  }

  public NetOptimize(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    initViews();
  }

  private void initViews()
  {
    setOrientation(1);
    LinearLayout localLinearLayout1 = new LinearLayout(this.mContext);
    localLinearLayout1.setGravity(17);
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(-1, App.Operation(320.0F));
    ImageView localImageView = new ImageView(this.mContext);
    LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(App.Operation(304.0F), App.Operation(304.0F));
    localLayoutParams2.leftMargin = App.Operation(150.0F);
    localLinearLayout1.addView(localImageView, localLayoutParams2);
    localImageView.setImageBitmap(BitmapService.getInstance(this.mContext).getBitmap("netoptimize_logo.png"));
    addView(localLinearLayout1, localLayoutParams1);
    TextView localTextView = new TextView(this.mContext);
    this.tv_start = new HighLightButton(this.mContext);
    this.tv_start.setId(2131165205);
    this.tv_start.setNextFocusUpId(this.tv_start.getId());
    this.tv_start.setTextColor(-7631989);
    this.tv_start.setFocusable(true);
    this.tv_start.setBackgroundResource(2130837507);
    this.tv_start.setGravity(17);
    LinearLayout.LayoutParams localLayoutParams3 = new LinearLayout.LayoutParams(App.Operation(570.0F), App.Operation(60.0F));
    if (this.flag == 0)
    {
      localTextView.setText("为了提高网速测试的准确性，请您在测试前关闭所有正在使用网络的设备或软件。");
      localTextView.setGravity(19);
      localLayoutParams3.leftMargin = App.Operation(150.0F);
      this.tv_start.setText("开始测试");
    }
    while (true)
    {
      localTextView.setTextColor(-7631989);
      localTextView.setTextSize(0, App.Operation(24.0F));
      addView(localTextView, localLayoutParams3);
      LinearLayout localLinearLayout2 = new LinearLayout(this.mContext);
      localLinearLayout2.setGravity(17);
      LinearLayout.LayoutParams localLayoutParams4 = new LinearLayout.LayoutParams(-1, App.Operation(100.0F));
      localLayoutParams4.leftMargin = App.Operation(150.0F);
      addView(localLinearLayout2, localLayoutParams4);
      this.tv_start.setOnFocusChangeListener(new View.OnFocusChangeListener()
      {
        public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
        {
          if (paramAnonymousBoolean)
          {
            paramAnonymousView.setBackgroundResource(2130837508);
            NetOptimize.this.tv_start.setTextColor(-1);
            return;
          }
          paramAnonymousView.setBackgroundResource(2130837507);
          NetOptimize.this.tv_start.setTextColor(-7631989);
        }
      });
      this.tv_start.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          NetOptimize.this.mOnstartListener.onStart();
        }
      });
      LinearLayout.LayoutParams localLayoutParams5 = new LinearLayout.LayoutParams(App.Operation(155.0F), App.Operation(60.0F));
      localLayoutParams5.topMargin = App.Operation(20.0F);
      localLinearLayout2.addView(this.tv_start, localLayoutParams5);
      return;
      if (this.flag == 1)
      {
        localTextView.setText("网络优化为您提供最佳路线，提高播放速度");
        localTextView.setGravity(17);
        localLayoutParams3.leftMargin = App.Operation(150.0F);
        this.tv_start.setText("开始优化");
      }
    }
  }

  public void setNextLeftButton(HighLightButton paramHighLightButton)
  {
    this.tv_start.setNextFocusLeftId(paramHighLightButton.getId());
  }

  public void setOnStartListener(OnStartListener paramOnStartListener)
  {
    this.mOnstartListener = paramOnStartListener;
  }

  public static abstract interface OnStartListener
  {
    public abstract void onStart();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.ui.testspeed.NetOptimize
 * JD-Core Version:    0.6.2
 */