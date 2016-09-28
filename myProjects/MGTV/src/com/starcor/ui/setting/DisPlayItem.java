package com.starcor.ui.setting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DisPlayItem extends LinearLayout
{
  private Context mcontext;
  private TextView tv_content;
  private TextView tv_title;

  public DisPlayItem(Context paramContext)
  {
    super(paramContext);
    this.mcontext = paramContext;
    initView(paramContext);
  }

  public DisPlayItem(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initView(paramContext);
  }

  private void initView(Context paramContext)
  {
    View localView = View.inflate(paramContext, 2130903064, this);
    this.tv_title = ((TextView)localView.findViewById(2131165289));
    this.tv_title.setText("重现率设置");
    this.tv_content = ((TextView)localView.findViewById(2131165290));
    this.tv_content.setText("设置您的电视重现率");
  }

  public void setContent(String paramString)
  {
    this.tv_content.setText(paramString);
  }

  public void setTitle(String paramString)
  {
    this.tv_title.setText(paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.ui.setting.DisPlayItem
 * JD-Core Version:    0.6.2
 */