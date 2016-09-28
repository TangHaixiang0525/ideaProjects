package com.starcor.hunan.widget;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.SupperToast;
import com.starcor.hunan.App;
import com.starcor.hunan.domain.Reservation;
import com.starcor.hunan.service.ReservationService;
import com.starcor.hunan.service.SystemTimeManager;
import com.starcor.hunan.uilogic.ActivityAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservationItem extends LinearLayout
{
  private static final String TAG = "UserOderItem";
  public static boolean mIsPlayBack = false;
  public HighLightTextView deleteText;
  public int id = 100;
  private boolean isSelect;
  private ImageView iv_sel;
  private LinearLayout layout;
  private LinearLayout ll_name;
  private Context mContext;
  private Reservation mReservation;
  public TextView nameText;
  private IRemoveItemListener removeItemListener;
  private HighLightTextView timeText;
  public HighLightTextView tvName;

  public ReservationItem(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    initViews();
  }

  private void initViews()
  {
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(App.OperationWidth(60), App.OperationHeight(60));
    this.layout = new LinearLayout(this.mContext);
    this.layout.setGravity(3);
    this.layout.setVisibility(4);
    this.layout.setFocusable(false);
    addView(this.layout, localLayoutParams1);
    this.iv_sel = new ImageView(this.mContext);
    LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(App.OperationHeight(20), App.OperationHeight(20));
    this.iv_sel.setFocusable(true);
    this.iv_sel.setId(10001);
    this.iv_sel.setBackgroundResource(2130837645);
    this.iv_sel.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
        {
          if (!ReservationItem.this.isSelect)
          {
            ReservationItem.this.iv_sel.setBackgroundResource(2130837647);
            return;
          }
          ReservationItem.this.iv_sel.setBackgroundResource(2130837646);
          return;
        }
        if (!ReservationItem.this.isSelect)
        {
          ReservationItem.this.iv_sel.setBackgroundResource(2130837645);
          return;
        }
        ReservationItem.this.iv_sel.setBackgroundResource(2130837648);
      }
    });
    this.layout.addView(this.iv_sel, localLayoutParams2);
    this.iv_sel.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (!ReservationItem.this.isSelect)
        {
          ReservationItem.this.iv_sel.setBackgroundResource(2130837646);
          ReservationItem.access$002(ReservationItem.this, true);
          return;
        }
        ReservationItem.this.iv_sel.setBackgroundResource(2130837647);
        ReservationItem.access$002(ReservationItem.this, false);
      }
    });
    this.nameText = new TextView(this.mContext);
    this.nameText.setGravity(19);
    this.nameText.setTextSize(0, App.OperationHeight(20));
    this.nameText.getPaint().setFlags(8);
    this.nameText.setDuplicateParentStateEnabled(true);
    this.nameText.setSingleLine(true);
    this.nameText.setTextColor(-2697514);
    this.nameText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
    this.nameText.setId(1000 + this.id);
    this.nameText.setTextSize(0, App.OperationHeight(24));
    this.ll_name = new LinearLayout(this.mContext);
    this.ll_name.setOrientation(0);
    this.ll_name.setFocusable(true);
    this.ll_name.setId(11000);
    this.timeText = new HighLightTextView(this.mContext);
    this.timeText.setFocusable(false);
    this.timeText.setGravity(17);
    this.timeText.setTextSize(0, App.OperationHeight(20));
    this.ll_name.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
        {
          ReservationItem.this.nameText.setSelected(true);
          paramAnonymousView.setBackgroundResource(2130837631);
          ReservationItem.this.nameText.setTextColor(-1);
          ReservationItem.this.timeText.setTextColor(-1);
          return;
        }
        ReservationItem.this.nameText.setSelected(false);
        paramAnonymousView.setBackgroundDrawable(null);
        ReservationItem.this.nameText.setTextColor(-2697514);
        ReservationItem.this.setTimeColor(ReservationItem.this.mReservation);
      }
    });
    this.ll_name.setOnClickListener(new NameTextOnclickListener(null));
    FrameLayout.LayoutParams localLayoutParams3 = new FrameLayout.LayoutParams(App.OperationWidth(180), App.OperationHeight(60));
    this.ll_name.setGravity(17);
    this.ll_name.addView(this.timeText, localLayoutParams3);
    LinearLayout.LayoutParams localLayoutParams4 = new LinearLayout.LayoutParams(App.OperationWidth(210), App.OperationHeight(60));
    this.ll_name.addView(this.nameText, localLayoutParams4);
    this.nameText.setMaxWidth(App.OperationWidth(210));
    LinearLayout.LayoutParams localLayoutParams5 = new LinearLayout.LayoutParams(App.OperationWidth(470), App.OperationHeight(60));
    addView(this.ll_name, localLayoutParams5);
    this.tvName = new HighLightTextView(this.mContext);
    this.tvName.setGravity(17);
    this.tvName.setTextSize(0, App.OperationHeight(20));
    LinearLayout.LayoutParams localLayoutParams6 = new LinearLayout.LayoutParams(App.OperationWidth(240), App.OperationHeight(60));
    localLayoutParams6.rightMargin = App.Operation(30.0F);
    addView(this.tvName, localLayoutParams6);
    FrameLayout.LayoutParams localLayoutParams7 = new FrameLayout.LayoutParams(App.OperationWidth(120), App.OperationHeight(50));
    this.deleteText = new HighLightTextView(this.mContext);
    this.deleteText.setFocusable(true);
    this.deleteText.setGravity(17);
    this.deleteText.setTextSize(0, App.OperationHeight(20));
    this.deleteText.setText("删除");
    HighLightTextView localHighLightTextView = this.deleteText;
    int i = this.id;
    this.id = (i + 1);
    localHighLightTextView.setId(i);
    this.deleteText.setNextFocusRightId(this.deleteText.getId());
    this.deleteText.setBackgroundResource(2130837507);
    this.deleteText.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
        {
          ReservationItem.this.deleteText.setBackgroundResource(2130837508);
          return;
        }
        ReservationItem.this.deleteText.setBackgroundResource(2130837507);
      }
    });
    this.deleteText.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (ReservationItem.this.removeItemListener != null)
        {
          ReservationItem.mIsPlayBack = false;
          ReservationItem.this.removeItemListener.remove(ReservationItem.this);
        }
      }
    });
    addView(this.deleteText, localLayoutParams7);
  }

  private void setTimeColor(Reservation paramReservation)
  {
    if (ReservationService.checkReservationState(paramReservation) == 1)
    {
      this.timeText.setTextColor(-16677439);
      this.timeText.setDefaultColor(-16677439);
    }
    while (true)
    {
      if (this.nameText != null)
      {
        this.nameText.setText(paramReservation.getName());
        this.nameText.setTag(Long.valueOf(paramReservation.get_id()));
      }
      return;
      this.timeText.setTextColor(-4289653);
      this.timeText.setDefaultColor(-4289653);
    }
  }

  public void disSelect()
  {
    this.isSelect = false;
    this.iv_sel.setBackgroundResource(2130837645);
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 0)
      switch (paramKeyEvent.getKeyCode())
      {
      default:
      case 21:
      }
    do
      return super.dispatchKeyEvent(paramKeyEvent);
    while (!this.deleteText.hasFocus());
    this.ll_name.requestFocus();
    return true;
  }

  public TextView getNameText()
  {
    return this.nameText;
  }

  public boolean isSelect()
  {
    return this.isSelect;
  }

  public void requestFocusOnNameLayout()
  {
    this.ll_name.requestFocus();
  }

  public void setCollectItem(Reservation paramReservation)
  {
    this.mReservation = paramReservation;
    setTimeColor(paramReservation);
    if (this.timeText != null);
    try
    {
      long l = GeneralUtils.getTimeInMillis(paramReservation.getDay() + paramReservation.getBeginTime());
      this.timeText.setText(new SimpleDateFormat("MM-dd HH:mm").format(new Date(l)));
      if (this.tvName != null)
        this.tvName.setText(paramReservation.getChannel());
      return;
    }
    catch (Exception localException)
    {
      while (true)
        this.timeText.setText("????-??-??");
    }
  }

  public void setFristFocus()
  {
    this.ll_name.setNextFocusUpId(2001);
    this.deleteText.setNextFocusUpId(2001);
    this.deleteText.setNextFocusRightId(this.deleteText.getId());
  }

  public void setLastFocus(int paramInt)
  {
    if (paramInt == 1)
    {
      this.ll_name.setNextFocusDownId(2003);
      this.deleteText.setNextFocusDownId(2003);
      this.iv_sel.setNextFocusDownId(2003);
      return;
    }
    if (paramInt == 2)
    {
      this.ll_name.setNextFocusDownId(2002);
      this.deleteText.setNextFocusDownId(2002);
      this.iv_sel.setNextFocusDownId(2002);
      return;
    }
    this.ll_name.setNextFocusDownId(this.ll_name.getId());
    this.deleteText.setNextFocusDownId(this.deleteText.getId());
    this.iv_sel.setNextFocusDownId(this.iv_sel.getId());
  }

  public void setSelect()
  {
    this.isSelect = true;
    this.iv_sel.setBackgroundResource(2130837648);
  }

  public void setSelection()
  {
    this.deleteText.requestFocus();
  }

  public void setURemoveItemListener(IRemoveItemListener paramIRemoveItemListener)
  {
    this.removeItemListener = paramIRemoveItemListener;
  }

  public static abstract interface IRemoveItemListener
  {
    public abstract void remove(View paramView);
  }

  private class NameTextOnclickListener
    implements View.OnClickListener
  {
    private NameTextOnclickListener()
    {
    }

    public void onClick(View paramView)
    {
      int i = ReservationService.checkReservationState(ReservationItem.this.mReservation);
      if (3 == i)
        SupperToast.makeToast(ReservationItem.this.mContext, "节目已过期", 0);
      while (true)
      {
        return;
        if (2 == i)
        {
          SupperToast.makeToast(ReservationItem.this.mContext, "节目未到播放时间", 0);
          return;
        }
        ReservationService.getinstance().removeReservation(ReservationItem.this.mReservation.get_id());
        PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
        localPlayerIntentParams.nns_beginTime = ReservationItem.this.mReservation.getBeginTime();
        localPlayerIntentParams.nns_day = ReservationItem.this.mReservation.getDay();
        localPlayerIntentParams.nns_index = 0;
        localPlayerIntentParams.nns_timeLen = String.valueOf(ReservationItem.this.mReservation.getTimeLen());
        localPlayerIntentParams.nns_videoInfo = new VideoInfo();
        localPlayerIntentParams.nns_videoInfo.videoId = ReservationItem.this.mReservation.getVideoId();
        localPlayerIntentParams.nns_videoInfo.huawei_cid = ReservationItem.this.mReservation.getHuawei_cid();
        localPlayerIntentParams.nns_videoInfo.categoryId = ReservationItem.this.mReservation.getCategoryId();
        localPlayerIntentParams.nns_videoInfo.packageId = ReservationItem.this.mReservation.getPackageId();
        localPlayerIntentParams.nns_videoInfo.videoType = ReservationItem.this.mReservation.getFilm_type();
        localPlayerIntentParams.nns_videoInfo.name = ReservationItem.this.mReservation.getName();
        localPlayerIntentParams.nns_videoInfo.film_name = ReservationItem.this.mReservation.getChannel();
        try
        {
          long l1 = new SimpleDateFormat("yyyyMMddHHmmss").parse(localPlayerIntentParams.nns_day + localPlayerIntentParams.nns_beginTime).getTime();
          long l2 = 1000 * Integer.valueOf(localPlayerIntentParams.nns_timeLen).intValue();
          SystemTimeManager.getInstance();
          if (l1 + l2 <= SystemTimeManager.getCurrentServerTime())
          {
            localPlayerIntentParams.mode = 3;
            Logger.i("UserOderItem", "onItemClick MODE_TSTV_VOD模式");
          }
          while (true)
          {
            Intent localIntent = new Intent(ReservationItem.this.mContext, ActivityAdapter.getInstance().getMPlayer());
            localIntent.putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
            localIntent.addFlags(8388608);
            ReservationItem.this.mContext.startActivity(localIntent);
            if (ReservationItem.this.removeItemListener == null)
              break;
            ReservationItem.mIsPlayBack = true;
            ReservationItem.this.removeItemListener.remove(ReservationItem.this);
            return;
            localPlayerIntentParams.real_default_back_pos = ReservationItem.this.mReservation.getReal_default_back_pos();
            localPlayerIntentParams.real_max_back_time_len = ReservationItem.this.mReservation.getReal_max_back_time_len();
            localPlayerIntentParams.real_min_back_time_len = ReservationItem.this.mReservation.getReal_min_back_time_len();
            localPlayerIntentParams.mode = 5;
            Logger.i("UserOderItem", "onItemClick MODE_TSTV_REAL模式");
          }
        }
        catch (ParseException localParseException)
        {
          while (true)
            localParseException.printStackTrace();
        }
      }
    }
  }

  public static abstract interface OrderDetailedListener
  {
    public abstract void showDetailed();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.ReservationItem
 * JD-Core Version:    0.6.2
 */