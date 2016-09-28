package com.starcor.hunan.widget;

import android.content.Context;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.starcor.core.domain.FAQItem;
import com.starcor.core.domain.FAQStruct;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import java.util.List;

public class ProblemCateItem extends LinearLayout
{
  private static final String TAG = ProblemCateItem.class.getSimpleName();
  private TextView caTextView = null;
  private Context context = null;
  private ScrollView parentScrollView = null;

  public ProblemCateItem(Context paramContext, ScrollView paramScrollView)
  {
    super(paramContext);
    this.context = paramContext;
    this.parentScrollView = paramScrollView;
    initSelf();
    initSub();
  }

  private void initSelf()
  {
    setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
    setOrientation(1);
  }

  private void initSub()
  {
    this.caTextView = new TextView(this.context);
    this.caTextView.setTextSize(0, App.Operation(24.0F));
    this.caTextView.setTextColor(-1);
    this.caTextView.getPaint().setFakeBoldText(true);
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(-2, -2);
    localLayoutParams1.leftMargin = App.OperationHeight(140);
    localLayoutParams1.rightMargin = App.OperationHeight(137);
    addView(this.caTextView, localLayoutParams1);
    ImageView localImageView = new ImageView(this.context);
    localImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    localImageView.setImageResource(2130837521);
    LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(-1, App.OperationHeight(2));
    localLayoutParams2.topMargin = App.OperationHeight(10);
    localLayoutParams2.leftMargin = App.OperationHeight(140);
    localLayoutParams2.rightMargin = App.OperationHeight(137);
    addView(localImageView, localLayoutParams2);
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    Logger.i(TAG, "dispatchKeyEvent refresh");
    postInvalidate();
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public void setCateProblemData(FAQStruct paramFAQStruct, boolean paramBoolean)
  {
    if (paramFAQStruct == null);
    List localList;
    do
    {
      return;
      this.caTextView.setText(paramFAQStruct.name + "");
      localList = paramFAQStruct.mItems;
    }
    while (localList == null);
    int i = localList.size();
    int j = 0;
    int k = 0;
    label57: LinearLayout.LayoutParams localLayoutParams;
    boolean bool1;
    label126: ProblemItem localProblemItem;
    FAQItem localFAQItem;
    if (k < i)
    {
      localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
      if (k > 0)
        localLayoutParams.topMargin = App.OperationHeight(5);
      if (k == i - 1)
        j = 1;
      Context localContext = this.context;
      ScrollView localScrollView = this.parentScrollView;
      if ((j == 0) || (!paramBoolean))
        break label204;
      bool1 = true;
      localProblemItem = new ProblemItem(localContext, localScrollView, bool1);
      localProblemItem.setPadding(0, App.Operation(2.0F), 0, App.Operation(2.0F));
      localFAQItem = (FAQItem)localList.get(k);
      if ((j == 0) || (!paramBoolean))
        break label210;
    }
    label204: label210: for (boolean bool2 = true; ; bool2 = false)
    {
      localProblemItem.SetProblemData(localFAQItem, bool2);
      addView(localProblemItem, localLayoutParams);
      k++;
      break label57;
      break;
      bool1 = false;
      break label126;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.ProblemCateItem
 * JD-Core Version:    0.6.2
 */