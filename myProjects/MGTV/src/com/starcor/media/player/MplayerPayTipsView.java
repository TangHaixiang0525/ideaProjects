package com.starcor.media.player;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;

public class MplayerPayTipsView extends RelativeLayout
{
  private static final String TAG = MplayerPayTipsView.class.getSimpleName();
  private Context mContext;
  private TextView mTextView;
  private LinearLayout mView;
  private String tip = "此为付费节目，按“确认/OK”键进行支付操作";

  public MplayerPayTipsView(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    initView();
  }

  private void initView()
  {
    Logger.i(TAG, "initView");
    LayoutInflater.from(this.mContext).inflate(2130903073, this);
    this.mView = ((LinearLayout)findViewById(2131165323));
    this.mTextView = ((TextView)findViewById(2131165324));
    this.mTextView.setTextSize(0, App.Operation(21.0F));
    this.mTextView.setText(this.tip);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, -1);
    this.mView.setLayoutParams(localLayoutParams);
    this.mView.setGravity(17);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerPayTipsView
 * JD-Core Version:    0.6.2
 */