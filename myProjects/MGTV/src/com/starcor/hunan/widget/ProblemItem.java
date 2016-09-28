package com.starcor.hunan.widget;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.starcor.core.domain.FAQItem;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;

public class ProblemItem extends LinearLayout
{
  private static final String TAG = "ProblemItem";
  protected static int titleID = 0;
  private TextView answerTextView = null;
  private Context context = null;
  private LinearLayout descLayout = null;
  private boolean isLast = false;
  private View.OnClickListener onClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      int[] arrayOfInt = new int[2];
      boolean bool = ((Boolean)paramAnonymousView.getTag()).booleanValue();
      ProblemItem.this.questionTextView.getLocationOnScreen(arrayOfInt);
      int i = arrayOfInt[1];
      if (ProblemItem.this.descLayout == null);
      do
      {
        return;
        if (ProblemItem.this.descLayout.getVisibility() != 8)
          break;
        ProblemItem.this.updateFocusStatus(bool, true);
        ProblemItem.this.descLayout.setVisibility(0);
      }
      while (App.SCREEN_HEIGHT - i >= App.Operation(330.0F));
      ProblemItem.this.parentScrollView.smoothScrollBy(0, App.Operation(330.0F) - (App.SCREEN_HEIGHT - i));
      return;
      ProblemItem.this.updateFocusStatus(bool, false);
      ProblemItem.this.descLayout.setVisibility(8);
    }
  };
  private View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener()
  {
    public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
    {
      if (paramAnonymousBoolean)
      {
        ProblemItem.this.descLayout.setBackgroundResource(2130837520);
        return;
      }
      ProblemItem.this.descLayout.setBackgroundResource(2130837519);
    }
  };
  private ScrollView parentScrollView = null;
  private TextView questionTextView = null;
  public ScrollView scrollView = null;

  public ProblemItem(Context paramContext, ScrollView paramScrollView, boolean paramBoolean)
  {
    super(paramContext);
    this.context = paramContext;
    this.parentScrollView = paramScrollView;
    this.isLast = paramBoolean;
    initSelf();
    initSub();
  }

  private void initSelf()
  {
    setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
    setOrientation(1);
  }

  private void initSub()
  {
    this.questionTextView = new TextView(this.context);
    this.questionTextView.setId(100 + titleID);
    titleID = 1 + titleID;
    this.questionTextView.setNextFocusRightId(this.questionTextView.getId());
    this.questionTextView.setTextColor(-1);
    this.questionTextView.getPaint().setTextSize(App.Operation(20.0F));
    this.questionTextView.setSingleLine(true);
    this.questionTextView.setFocusable(true);
    this.questionTextView.setClickable(true);
    this.questionTextView.setOnClickListener(this.onClickListener);
    this.questionTextView.setBackgroundResource(2130837642);
    this.questionTextView.setGravity(16);
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(-2, -2);
    localLayoutParams1.leftMargin = App.OperationHeight(140);
    localLayoutParams1.rightMargin = App.OperationHeight(137);
    this.questionTextView.setLayoutParams(localLayoutParams1);
    addView(this.questionTextView);
    this.questionTextView.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
      {
        Logger.e("item", "hasFocus: " + paramAnonymousBoolean);
      }
    });
    this.descLayout = new LinearLayout(this.context);
    this.descLayout.setBackgroundResource(2130837519);
    LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(-1, App.OperationHeight(250));
    addView(this.descLayout, localLayoutParams2);
    this.scrollView = new ScrollView(this.context);
    this.scrollView.setOnFocusChangeListener(this.onFocusChangeListener);
    LinearLayout.LayoutParams localLayoutParams3 = new LinearLayout.LayoutParams(App.OperationHeight(763), -1);
    localLayoutParams3.leftMargin = App.OperationHeight(150);
    localLayoutParams3.rightMargin = App.OperationHeight(140);
    this.descLayout.addView(this.scrollView, localLayoutParams3);
    this.answerTextView = new TextView(this.context);
    this.answerTextView.getPaint().setTextSize(App.Operation(20.0F));
    this.answerTextView.setLineSpacing(4.0F, 1.2F);
    this.answerTextView.setTextColor(getResources().getColor(2131099651));
    this.answerTextView.setSingleLine(false);
    LinearLayout.LayoutParams localLayoutParams4 = new LinearLayout.LayoutParams(-1, -1);
    this.scrollView.addView(this.answerTextView, localLayoutParams4);
    this.descLayout.setVisibility(8);
    this.questionTextView.setTag(Boolean.valueOf(this.isLast));
    this.questionTextView.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
      {
        if ((paramAnonymousBoolean) && (ProblemItem.this.descLayout != null) && (ProblemItem.this.descLayout.getVisibility() == 0))
        {
          int[] arrayOfInt = new int[2];
          ProblemItem.this.descLayout.getLocationOnScreen(arrayOfInt);
          int i = arrayOfInt[1];
          if (App.SCREEN_HEIGHT - i < App.Operation(250.0F))
            ProblemItem.this.parentScrollView.smoothScrollBy(0, App.Operation(280.0F) - (App.SCREEN_HEIGHT - i));
        }
      }
    });
    updateFocusStatus(this.isLast, false);
  }

  private void updateFocusStatus(boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((paramBoolean1) && (paramBoolean2))
    {
      this.scrollView.setId(10000);
      this.questionTextView.setNextFocusDownId(this.scrollView.getId());
      this.scrollView.setNextFocusDownId(this.scrollView.getId());
    }
    while (!paramBoolean1)
      return;
    this.questionTextView.setNextFocusDownId(this.questionTextView.getId());
  }

  public void SetProblemData(FAQItem paramFAQItem, boolean paramBoolean)
  {
    if (paramFAQItem == null);
    while ((this.questionTextView == null) || (this.answerTextView == null))
      return;
    this.questionTextView.setText(paramFAQItem.question + "");
    this.questionTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
    this.answerTextView.setText(paramFAQItem.answer + "");
  }

  public TextView getTitleView()
  {
    return this.questionTextView;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.ProblemItem
 * JD-Core Version:    0.6.2
 */