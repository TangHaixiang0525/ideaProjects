package com.starcor.hunan.widget;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.starcor.hunan.App;
import com.starcor.hunan.interfaces.ServiceSelectBtnCallBack;

public class MicroBlogView extends LinearLayout
  implements ServiceSelectBtnCallBack
{
  private ImageView imgTwoDimensionLeft;
  private ImageView imgTwoDimensionRight;
  private Context mContext;
  private TextView tvNoteFirst;
  private TextView tvNoteSecondLeft;
  private TextView tvNoteSecondRight;
  private TextView tvNoteThirdLeft;
  private TextView tvNoteThirdRight;
  public WebView webView = null;

  public MicroBlogView(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    init(paramContext);
  }

  void init(Context paramContext)
  {
    View.inflate(this.mContext, 2130903072, this);
    this.tvNoteFirst = ((TextView)findViewById(2131165314));
    this.tvNoteFirst.setText("扫描二维码");
    this.tvNoteFirst.setTextColor(-3158065);
    this.tvNoteFirst.setTextSize(0, App.Operation(28.0F));
    ((LinearLayout.LayoutParams)this.tvNoteFirst.getLayoutParams()).bottomMargin = App.Operation(10.0F);
    this.tvNoteSecondLeft = ((TextView)findViewById(2131165315));
    this.tvNoteSecondLeft.setText("关注芒果互联网电视官方微信和官方微博");
    this.tvNoteSecondLeft.setTextColor(-26368);
    this.tvNoteSecondLeft.setTextSize(0, App.Operation(28.0F));
    this.tvNoteSecondRight = ((TextView)findViewById(2131165316));
    this.tvNoteSecondRight.setText("获取每日更新信息");
    ((LinearLayout.LayoutParams)this.tvNoteSecondRight.getLayoutParams()).leftMargin = App.Operation(20.0F);
    this.tvNoteSecondRight.setTextColor(-5131855);
    this.tvNoteSecondRight.setTextSize(0, App.Operation(24.0F));
    ((LinearLayout.LayoutParams)findViewById(2131165317).getLayoutParams()).topMargin = App.Operation(20.0F);
    ((LinearLayout.LayoutParams)findViewById(2131165317).getLayoutParams()).bottomMargin = App.Operation(10.0F);
    this.imgTwoDimensionLeft = ((ImageView)findViewById(2131165318));
    ((LinearLayout.LayoutParams)this.imgTwoDimensionLeft.getLayoutParams()).width = App.Operation(350.0F);
    ((LinearLayout.LayoutParams)this.imgTwoDimensionLeft.getLayoutParams()).height = App.Operation(350.0F);
    this.imgTwoDimensionRight = ((ImageView)findViewById(2131165319));
    ((LinearLayout.LayoutParams)this.imgTwoDimensionRight.getLayoutParams()).leftMargin = App.Operation(85.0F);
    ((LinearLayout.LayoutParams)this.imgTwoDimensionRight.getLayoutParams()).width = App.Operation(350.0F);
    ((LinearLayout.LayoutParams)this.imgTwoDimensionRight.getLayoutParams()).height = App.Operation(350.0F);
    ((LinearLayout.LayoutParams)findViewById(2131165320).getLayoutParams()).bottomMargin = App.Operation(50.0F);
    this.tvNoteThirdLeft = ((TextView)findViewById(2131165321));
    this.tvNoteThirdLeft.setTextColor(-5131855);
    this.tvNoteThirdLeft.setTextSize(0, App.Operation(24.0F));
    ((LinearLayout.LayoutParams)this.tvNoteThirdLeft.getLayoutParams()).leftMargin = App.Operation(40.0F);
    this.tvNoteThirdRight = ((TextView)findViewById(2131165322));
    this.tvNoteThirdLeft.setTextColor(-5131855);
    this.tvNoteThirdRight.setTextSize(0, App.Operation(24.0F));
    ((LinearLayout.LayoutParams)this.tvNoteThirdRight.getLayoutParams()).leftMargin = App.Operation(115.0F);
  }

  public void setNextLeftButton(HighLightButton paramHighLightButton)
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.MicroBlogView
 * JD-Core Version:    0.6.2
 */