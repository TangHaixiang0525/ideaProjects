package com.starcor.hunan.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.starcor.core.domain.CityItem;
import com.starcor.core.domain.CityStruct;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.ui.UITools;
import java.util.ArrayList;

public class CityItemView extends LinearLayout
{
  private String cityName;
  private ImageView iv_arrow;
  private LinearLayout ll_content;
  private CityClickListener mCityClickListener;
  private Context mContext;
  private CityItemView mLastView;
  private CityItemView nextView;
  private ScrollView sc_view;
  public HighLightTextView selectView;
  private View selectedView;
  private LinearLayout set_list_lin;
  private TextView title;
  private HighLightTextView tv_city;
  private TextView tv_city_title;

  public CityItemView(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.nextView = null;
    initView();
  }

  public CityItemView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    this.nextView = null;
    initView();
  }

  private void initView()
  {
    View localView = View.inflate(this.mContext, 2130903054, this);
    this.title = ((TextView)localView.findViewById(2131165268));
    this.title.setGravity(17);
    this.title.setTextSize(0, App.OperationHeight(26));
    this.iv_arrow = ((ImageView)localView.findViewById(2131165271));
    UITools.setViewMargin(this.iv_arrow, App.OperationHeight(15), 0, App.OperationHeight(15), 0);
    this.iv_arrow.setVisibility(4);
    this.sc_view = ((ScrollView)localView.findViewById(2131165269));
    this.sc_view.getLayoutParams().height = App.OperationHeight(380);
    this.sc_view.getLayoutParams().width = App.OperationHeight(170);
    this.ll_content = ((LinearLayout)localView.findViewById(2131165270));
    this.ll_content.getLayoutParams().height = App.OperationHeight(380);
    this.ll_content.getLayoutParams().width = App.OperationHeight(170);
    this.set_list_lin = ((LinearLayout)findViewById(2131165267));
    this.tv_city_title = ((TextView)findViewById(2131165268));
    this.tv_city_title.setBackgroundColor(134217727);
    this.set_list_lin.setBackgroundResource(2130837652);
  }

  public void changeView()
  {
    if (hasNext())
    {
      Logger.d(this.nextView.hasNext() + "" + getId() + "---" + this.nextView.getId());
      this.nextView.setVisibility(4);
      hiddenArrow();
      this.nextView.changeView();
    }
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if ((hasFocus()) && (paramKeyEvent.getAction() == 0))
      switch (paramKeyEvent.getKeyCode())
      {
      default:
      case 21:
      }
    do
      return super.dispatchKeyEvent(paramKeyEvent);
    while (this.mLastView == null);
    this.mLastView.requestFocus();
    return true;
  }

  public String getCityName()
  {
    return this.cityName;
  }

  public CityItemView getNext()
  {
    return this.nextView;
  }

  public boolean hasNext()
  {
    return this.nextView != null;
  }

  public void hiddenArrow()
  {
    this.iv_arrow.setVisibility(4);
  }

  public void resetName()
  {
    if (hasNext())
    {
      this.nextView.cityName = null;
      this.nextView.resetName();
    }
  }

  public void setCityName(String paramString)
  {
    this.cityName = paramString;
  }

  public void setData(final CityStruct paramCityStruct)
  {
    this.ll_content.removeAllViews();
    for (int i = 0; i < paramCityStruct.getCity_item().size(); i++)
    {
      final int j = i;
      this.tv_city = new HighLightTextView(this.mContext);
      this.tv_city.setGravity(17);
      this.tv_city.setFocusable(true);
      this.tv_city.setText(((CityItem)paramCityStruct.getCity_item().get(i)).getName());
      this.tv_city.setId(Integer.parseInt(((CityItem)paramCityStruct.getCity_item().get(i)).getId()));
      if (i == 0)
        this.tv_city.setNextFocusUpId(this.tv_city.getId());
      this.tv_city.setOnFocusChangeListener(new View.OnFocusChangeListener()
      {
        public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
        {
          if (paramAnonymousBoolean)
          {
            CityItemView.access$002(CityItemView.this, paramAnonymousView);
            if (CityItemView.this.mLastView != null)
              paramAnonymousView.setNextFocusLeftId(CityItemView.access$100(CityItemView.this).selectedView.getId());
          }
          paramAnonymousView.setSelected(paramAnonymousBoolean);
        }
      });
      this.tv_city.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if (CityItemView.this.nextView != null)
            CityItemView.this.nextView.setVisibility(8);
          if (CityItemView.this.selectView != null)
            CityItemView.this.selectView.setHightLight(false);
          CityItemView.this.selectView = ((HighLightTextView)paramAnonymousView);
          CityItemView.this.selectView.setHightLight(true);
          String str = ((CityItem)paramCityStruct.getCity_item().get(j)).getId();
          CityItemView.this.setCityName(((CityItem)paramCityStruct.getCity_item().get(j)).getName());
          CityItemView.this.resetName();
          CityItemView.this.mCityClickListener.onCityClick(str);
          CityPadView.lastView = CityItemView.this;
          CityPadView.setLastId(str);
          Logger.i("serviceActivity地区设置", "CityItemView onItemClick(),cityName:" + ((CityItem)paramCityStruct.getCity_item().get(j)).getName());
        }
      });
      this.ll_content.addView(this.tv_city);
    }
    if (this.nextView != null)
      this.nextView.setVisibility(0);
    this.tv_city.setNextFocusDownId(this.tv_city.getId());
  }

  public void setLastView(CityItemView paramCityItemView)
  {
    this.mLastView = paramCityItemView;
  }

  public void setNext(CityItemView paramCityItemView)
  {
    this.nextView = paramCityItemView;
  }

  public void setOnItemListener(CityClickListener paramCityClickListener)
  {
    this.mCityClickListener = paramCityClickListener;
  }

  public void setTitle(String paramString)
  {
    this.title.setText(paramString);
  }

  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
    if ((paramInt != 0) && (this.nextView != null))
      this.nextView.setVisibility(paramInt);
  }

  public void showArrow()
  {
    this.iv_arrow.setVisibility(0);
  }

  public static abstract interface CityClickListener
  {
    public abstract void onCityClick(String paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.CityItemView
 * JD-Core Version:    0.6.2
 */