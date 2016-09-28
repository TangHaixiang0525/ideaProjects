package com.starcor.hunan.widget;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.domain.Reservation;
import com.starcor.hunan.service.ReservationService;
import com.starcor.hunan.service.SystemTimeManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationDialog extends BaseDialog
{
  private static final String TAG = ReservationDialog.class.getSimpleName();
  private ReservationAdapter adapter;
  private ReservationOnClickListener listener;
  private Context mContext;
  private long time;

  public ReservationDialog(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }

  public ReservationDialog(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
    this.mContext = paramContext;
  }

  public ReservationDialog(Context paramContext, boolean paramBoolean, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    super(paramContext, paramBoolean, paramOnCancelListener);
    this.mContext = paramContext;
  }

  private void initViews()
  {
    WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
    localLayoutParams.width = App.OperationHeight(444);
    localLayoutParams.height = App.OperationHeight(376);
    getWindow().setAttributes(localLayoutParams);
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131165258);
    localLinearLayout.getLayoutParams().height = App.Operation(66.0F);
    localLinearLayout.setGravity(16);
    TextView localTextView1 = (TextView)findViewById(2131165283);
    localTextView1.setGravity(16);
    localTextView1.setTextSize(0, App.OperationHeight(26));
    LinearLayout.LayoutParams localLayoutParams1 = (LinearLayout.LayoutParams)localTextView1.getLayoutParams();
    localLayoutParams1.leftMargin = App.OperationHeight(35);
    localLayoutParams1.gravity = 16;
    ImageView localImageView = (ImageView)findViewById(2131165263);
    localImageView.getLayoutParams().height = App.Operation(1.0F);
    LinearLayout.LayoutParams localLayoutParams2 = (LinearLayout.LayoutParams)localImageView.getLayoutParams();
    localLayoutParams2.leftMargin = App.Operation(32.0F);
    localLayoutParams2.rightMargin = App.Operation(32.0F);
    localTextView1.setText("提示");
    TextView localTextView2 = (TextView)findViewById(2131165284);
    localTextView1.setGravity(16);
    localTextView1.setTextColor(-1291845633);
    localTextView2.setTextColor(-1291845633);
    localTextView2.setTextSize(0, App.Operation(20.0F));
    LinearLayout.LayoutParams localLayoutParams3 = (LinearLayout.LayoutParams)localTextView2.getLayoutParams();
    localLayoutParams3.topMargin = App.Operation(3.0F);
    localLayoutParams3.gravity = 16;
    localTextView2.setText("按\"返回键\"取消");
    TextView localTextView3 = (TextView)findViewById(2131165285);
    localTextView3.setText("您预约的回看节目可以点播了!");
    localTextView3.setTextSize(0, App.OperationHeight(22));
    localTextView3.getLayoutParams().height = App.Operation(54.0F);
    localTextView3.setGravity(17);
    localTextView3.getLayoutParams().width = App.Operation(380.0F);
    ((LinearLayout.LayoutParams)localTextView3.getLayoutParams());
    ListView localListView = (ListView)findViewById(2131165286);
    this.adapter = new ReservationAdapter();
    localListView.setAdapter(this.adapter);
    localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        Reservation localReservation = (Reservation)ReservationDialog.this.adapter.getItem(paramAnonymousInt);
        PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
        localPlayerIntentParams.nns_beginTime = localReservation.getBeginTime();
        localPlayerIntentParams.nns_day = localReservation.getDay();
        localPlayerIntentParams.nns_index = 0;
        localPlayerIntentParams.nns_timeLen = String.valueOf(localReservation.getTimeLen());
        try
        {
          long l1 = new SimpleDateFormat("yyyyMMddHHmmss").parse(localPlayerIntentParams.nns_day + localPlayerIntentParams.nns_beginTime).getTime();
          long l2 = 1000 * Integer.valueOf(localPlayerIntentParams.nns_timeLen).intValue();
          Logger.i(ReservationDialog.TAG, "MplayerControl.updataParams 传入时间 beginTime : " + l1 + " 时长 timeLen : " + l2);
          SystemTimeManager.getInstance();
          localPlayerIntentParams.mode = 3;
          localPlayerIntentParams.nns_videoInfo = new VideoInfo();
          localPlayerIntentParams.nns_videoInfo.videoId = localReservation.getVideoId();
          localPlayerIntentParams.nns_videoInfo.videoType = localReservation.getFilm_type();
          localPlayerIntentParams.nns_videoInfo.categoryId = localReservation.getCategoryId();
          localPlayerIntentParams.nns_videoInfo.packageId = localReservation.getPackageId();
          localPlayerIntentParams.nns_videoInfo.name = localReservation.getName();
          localPlayerIntentParams.nns_videoInfo.film_name = localReservation.getChannel();
          localPlayerIntentParams.nns_videoInfo.huawei_cid = localReservation.getHuawei_cid();
          ReservationService.getinstance().removeReservation(localReservation.get_id());
          if (ReservationDialog.this.listener != null)
            ReservationDialog.this.listener.onReservationClick(localReservation, localPlayerIntentParams);
          ReservationDialog.this.dismiss();
          return;
        }
        catch (Exception localException)
        {
          while (true)
          {
            Logger.d(ReservationDialog.TAG, "onItemClick 模式判断错误！");
            localException.printStackTrace();
          }
        }
      }
    });
    LinearLayout.LayoutParams localLayoutParams4 = (LinearLayout.LayoutParams)localListView.getLayoutParams();
    localLayoutParams4.width = App.OperationHeight(380);
    localLayoutParams4.height = App.OperationHeight(216);
    ImageButton localImageButton = (ImageButton)findViewById(2131165287);
    ((LinearLayout.LayoutParams)localImageButton.getLayoutParams()).topMargin = App.OperationHeight(20);
    localImageButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ReservationDialog.this.dismiss();
      }
    });
  }

  public void addReservation(long paramLong)
  {
    List localList = ReservationService.getinstance().getAndRemoveExpiredReservation(paramLong);
    Logger.i(TAG, "添加预约到对话框:count:" + localList.size());
    if ((localList != null) && (localList.size() > 0))
      this.adapter.addList(localList);
  }

  public String formatTimeStr(String paramString)
  {
    SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("HH:mm");
    try
    {
      String str = localSimpleDateFormat2.format(localSimpleDateFormat1.parse(paramString));
      return str;
    }
    catch (ParseException localParseException)
    {
      localParseException.printStackTrace();
    }
    return null;
  }

  public ReservationOnClickListener getListener()
  {
    return this.listener;
  }

  public long getTime()
  {
    return this.time;
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903062);
    initViews();
  }

  public void setListener(ReservationOnClickListener paramReservationOnClickListener)
  {
    this.listener = paramReservationOnClickListener;
  }

  public void setReservation(long paramLong)
  {
    List localList = ReservationService.getinstance().getAndRemoveExpiredReservation(paramLong);
    if ((localList != null) && (localList.size() > 0))
      this.adapter.setList(localList);
  }

  public void setTime(long paramLong)
  {
    this.time = paramLong;
  }

  class ReservationAdapter extends BaseAdapter
  {
    List<Reservation> list = new ArrayList();

    ReservationAdapter()
    {
    }

    public void addList(List<Reservation> paramList)
    {
      if ((paramList == null) || (paramList.size() <= 0))
        return;
      for (int i = -1 + paramList.size(); i >= 0; i--)
        if (this.list.contains(paramList.get(i)))
          paramList.remove(i);
      this.list.addAll(paramList);
      notifyDataSetChanged();
    }

    public int getCount()
    {
      return this.list.size();
    }

    public Object getItem(int paramInt)
    {
      return this.list.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      ReservationDialog.ReservationView localReservationView;
      String str;
      TextView localTextView;
      if (paramView == null)
      {
        localReservationView = new ReservationDialog.ReservationView(ReservationDialog.this, ReservationDialog.this.getContext());
        localReservationView.setDefaultBackGroundType(paramInt % 2);
        localReservationView.name.setText(((Reservation)this.list.get(paramInt)).getName());
        str = ((Reservation)this.list.get(paramInt)).getChannel();
        localTextView = localReservationView.channel;
        if (!TextUtils.isEmpty(str))
          break label132;
      }
      label132: for (int i = 8; ; i = 0)
      {
        localTextView.setVisibility(i);
        localReservationView.channel.setText(str);
        localReservationView.icon.setBackgroundResource(2130837552);
        return localReservationView;
        localReservationView = (ReservationDialog.ReservationView)paramView;
        break;
      }
    }

    public void setList(List<Reservation> paramList)
    {
      if (paramList != null)
        this.list = paramList;
      while (true)
      {
        notifyDataSetChanged();
        return;
        this.list.clear();
      }
    }
  }

  public static abstract interface ReservationOnClickListener
  {
    public abstract void onReservationClick(Reservation paramReservation, PlayerIntentParams paramPlayerIntentParams);
  }

  class ReservationView extends LinearLayout
  {
    private final int DefaultBackColor = 0;
    public TextView channel;
    public ImageView icon;
    public TextView name;
    private final int selectedBackColor = -13746341;
    private int style;

    public ReservationView(Context arg2)
    {
      super();
      initConentViews();
    }

    public ReservationView(Context paramAttributeSet, AttributeSet arg3)
    {
      super(localAttributeSet);
      initConentViews();
    }

    private void initConentViews()
    {
      new LinearLayout.LayoutParams(-2, -2);
      this.name = new TextView(ReservationDialog.this.mContext);
      this.name.setTextColor(-855638017);
      this.name.setSingleLine();
      this.name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
      this.name.setGravity(16);
      this.name.setDuplicateParentStateEnabled(true);
      LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(-2, -2);
      this.name.setTextSize(0, App.Operation(23.0F));
      localLayoutParams1.height = App.Operation(45.0F);
      localLayoutParams1.gravity = 17;
      localLayoutParams1.weight = 1.0F;
      localLayoutParams1.leftMargin = App.Operation(20.0F);
      addView(this.name, localLayoutParams1);
      LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(-1, -2);
      this.channel = new TextView(ReservationDialog.this.mContext);
      this.channel.setTextColor(-855638017);
      this.channel.setGravity(16);
      this.channel.setTextSize(0, App.Operation(23.0F));
      this.channel.setSingleLine();
      this.channel.setEllipsize(TextUtils.TruncateAt.MARQUEE);
      localLayoutParams2.height = App.Operation(45.0F);
      localLayoutParams2.width = App.Operation(140.0F);
      localLayoutParams2.leftMargin = App.Operation(20.0F);
      localLayoutParams2.gravity = 17;
      addView(this.channel, localLayoutParams2);
      setBackgroundColor(0);
      LinearLayout.LayoutParams localLayoutParams3 = new LinearLayout.LayoutParams(-2, -2);
      this.icon = new ImageView(ReservationDialog.this.mContext);
      this.icon.setBackgroundResource(2130837552);
      this.icon.setVisibility(4);
      localLayoutParams3.height = App.Operation(26.0F);
      localLayoutParams3.rightMargin = App.Operation(20.0F);
      localLayoutParams3.gravity = 17;
      addView(this.icon, localLayoutParams3);
    }

    public void setDefaultBackGroundType(int paramInt)
    {
      this.style = paramInt;
      if (!isSelected())
        setBackgroundColor(0);
    }

    public void setSelected(boolean paramBoolean)
    {
      super.setSelected(paramBoolean);
      if (paramBoolean)
      {
        setBackgroundResource(2130837664);
        this.channel.setTextColor(-1);
        this.name.setTextColor(-1);
        this.name.setShadowLayer(3.0F, 1.0F, 1.0F, -16777216);
        this.channel.setShadowLayer(3.0F, 1.0F, 1.0F, -16777216);
        this.icon.setVisibility(0);
        return;
      }
      setBackgroundColor(0);
      this.channel.setTextColor(-855638017);
      this.name.setTextColor(-855638017);
      this.name.setShadowLayer(3.0F, 1.0F, 1.0F, 0);
      this.channel.setShadowLayer(3.0F, 1.0F, 1.0F, 0);
      this.icon.setVisibility(4);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.ReservationDialog
 * JD-Core Version:    0.6.2
 */