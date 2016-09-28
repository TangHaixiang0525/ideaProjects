package com.starcor.hunan.widget;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
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
import com.starcor.core.domain.CollectListItem;
import com.starcor.core.domain.MovieData;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.DetailPageActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TracePlayDialog extends BaseDialog
{
  private static final String TAG = TracePlayDialog.class.getSimpleName();
  private TracePlayAdapter adapter;
  List<CollectListItem> list = new ArrayList();
  private TracePlayOnClickListener listener;
  private Context mContext;
  private long time;

  public TracePlayDialog(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }

  public TracePlayDialog(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
    this.mContext = paramContext;
  }

  public TracePlayDialog(Context paramContext, boolean paramBoolean, DialogInterface.OnCancelListener paramOnCancelListener)
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
    localTextView1.setText("提示");
    LinearLayout.LayoutParams localLayoutParams1 = (LinearLayout.LayoutParams)localTextView1.getLayoutParams();
    localLayoutParams1.leftMargin = App.OperationHeight(35);
    localLayoutParams1.gravity = 16;
    ImageView localImageView = (ImageView)findViewById(2131165263);
    localImageView.getLayoutParams().height = App.Operation(1.0F);
    LinearLayout.LayoutParams localLayoutParams2 = (LinearLayout.LayoutParams)localImageView.getLayoutParams();
    localLayoutParams2.leftMargin = App.Operation(32.0F);
    localLayoutParams2.rightMargin = App.Operation(32.0F);
    TextView localTextView2 = (TextView)findViewById(2131165284);
    localTextView2.setText("按\"返回键\"取消");
    localTextView1.setGravity(16);
    localTextView1.setTextColor(-1291845633);
    localTextView2.setTextColor(-1291845633);
    localTextView2.setTextSize(0, App.Operation(20.0F));
    LinearLayout.LayoutParams localLayoutParams3 = (LinearLayout.LayoutParams)localTextView2.getLayoutParams();
    localLayoutParams3.topMargin = App.Operation(3.0F);
    localLayoutParams3.gravity = 16;
    TextView localTextView3 = (TextView)findViewById(2131165285);
    localTextView3.setText("您追剧的节目有更新:");
    localTextView3.setPadding(App.Operation(20.0F), 0, 0, 0);
    localTextView3.setTextSize(0, App.OperationHeight(22));
    localTextView3.getLayoutParams().height = App.Operation(54.0F);
    localTextView3.setGravity(16);
    localTextView3.getLayoutParams().width = App.Operation(380.0F);
    ((LinearLayout.LayoutParams)localTextView3.getLayoutParams());
    ListView localListView = (ListView)findViewById(2131165286);
    this.adapter = new TracePlayAdapter();
    localListView.setAdapter(this.adapter);
    localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        CollectListItem localCollectListItem = (CollectListItem)TracePlayDialog.this.adapter.getItem(paramAnonymousInt);
        TracePlayDialog.this.dismiss();
        Intent localIntent = new Intent();
        localIntent.setClass(TracePlayDialog.this.mContext, DetailPageActivity.class);
        MovieData localMovieData = new MovieData();
        localMovieData.videoId = localCollectListItem.video_id;
        localMovieData.videoType = localCollectListItem.video_type;
        localMovieData.name = localCollectListItem.video_name;
        localIntent.putExtra("movieData", localMovieData);
        localIntent.putExtra("xulPageId", "DetailPage");
        localIntent.putExtra("xulData", "");
        localIntent.setFlags(8388608);
        TracePlayDialog.this.mContext.startActivity(localIntent);
        if (TracePlayDialog.this.listener != null)
          TracePlayDialog.this.listener.ontracepalyClick();
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
        TracePlayDialog.this.dismiss();
      }
    });
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

  public TracePlayOnClickListener getListener()
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
    setContentView(2130903063);
    initViews();
    Logger.i(TAG, "TracePlayDialog   onCreate!!");
  }

  public void setListener(TracePlayOnClickListener paramTracePlayOnClickListener)
  {
    this.listener = paramTracePlayOnClickListener;
  }

  public void setTime(long paramLong)
  {
    this.time = paramLong;
  }

  public void setTracePlayList(List<CollectListItem> paramList)
  {
    Logger.i(TAG, "setTracePlayList");
    if (paramList == null)
    {
      Logger.i(TAG, "setTracePlayList plist==null");
      this.list.clear();
    }
    Logger.i(TAG, "setTracePlayList plist!=null");
    this.list = paramList;
    if (this.adapter != null)
    {
      this.adapter.notifyDataSetChanged();
      Logger.i(TAG, "setTracePlayList adapter.notifyDataSetChanged();");
    }
  }

  class TracePlayAdapter extends BaseAdapter
  {
    TracePlayAdapter()
    {
    }

    public int getCount()
    {
      return TracePlayDialog.this.list.size();
    }

    public Object getItem(int paramInt)
    {
      return TracePlayDialog.this.list.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null);
      for (TracePlayDialog.TracePlayView localTracePlayView = new TracePlayDialog.TracePlayView(TracePlayDialog.this, TracePlayDialog.this.getContext()); ; localTracePlayView = (TracePlayDialog.TracePlayView)paramView)
      {
        localTracePlayView.setDefaultBackGroundType(paramInt % 2);
        Logger.i(TracePlayDialog.TAG, "list. =" + TracePlayDialog.this.list.toString());
        Logger.i(TracePlayDialog.TAG, "list.get(position).video_name=" + ((CollectListItem)TracePlayDialog.this.list.get(paramInt)).video_name);
        localTracePlayView.name.setText(((CollectListItem)TracePlayDialog.this.list.get(paramInt)).video_name);
        TextView localTextView = localTracePlayView.index;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = String.valueOf(1 + ((CollectListItem)TracePlayDialog.this.list.get(paramInt)).video_index);
        localTextView.setText(String.format("更新至%s集", arrayOfObject));
        localTracePlayView.icon.setBackgroundResource(2130837552);
        return localTracePlayView;
      }
    }
  }

  public static abstract interface TracePlayOnClickListener
  {
    public abstract void ontracepalyClick();
  }

  class TracePlayView extends LinearLayout
  {
    private final int DefaultBackColor = 0;
    public ImageView icon;
    public TextView index;
    public TextView name;
    private final int selectedBackColor = -13746341;
    private int style;

    public TracePlayView(Context arg2)
    {
      super();
      initConentViews();
    }

    public TracePlayView(Context paramAttributeSet, AttributeSet arg3)
    {
      super(localAttributeSet);
      initConentViews();
    }

    private void initConentViews()
    {
      new LinearLayout.LayoutParams(-2, -2);
      this.name = new TextView(TracePlayDialog.this.mContext);
      this.name.setTextColor(-855638017);
      this.name.setSingleLine(true);
      this.name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
      this.name.setMarqueeRepeatLimit(-1);
      this.name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
      this.name.setGravity(16);
      this.name.setDuplicateParentStateEnabled(true);
      LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(-2, -2);
      this.name.setTextSize(0, App.Operation(23.0F));
      localLayoutParams1.height = App.Operation(45.0F);
      localLayoutParams1.gravity = 17;
      localLayoutParams1.leftMargin = App.Operation(20.0F);
      localLayoutParams1.weight = 1.0F;
      addView(this.name, localLayoutParams1);
      LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(-1, -2);
      this.index = new TextView(TracePlayDialog.this.mContext);
      this.index.setTextColor(-855638017);
      this.index.setEllipsize(TextUtils.TruncateAt.MARQUEE);
      this.index.setSingleLine(true);
      this.index.setMarqueeRepeatLimit(-1);
      this.index.setGravity(16);
      this.index.setTextSize(0, App.Operation(23.0F));
      localLayoutParams2.height = App.Operation(45.0F);
      localLayoutParams2.width = App.Operation(140.0F);
      localLayoutParams2.leftMargin = App.Operation(20.0F);
      localLayoutParams2.gravity = 17;
      addView(this.index, localLayoutParams2);
      setBackgroundColor(0);
      LinearLayout.LayoutParams localLayoutParams3 = new LinearLayout.LayoutParams(-2, -2);
      this.icon = new ImageView(TracePlayDialog.this.mContext);
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
        this.index.setTextColor(-1);
        this.name.setTextColor(-1);
        this.name.setShadowLayer(3.0F, 1.0F, 1.0F, -16777216);
        this.index.setShadowLayer(3.0F, 1.0F, 1.0F, -16777216);
        this.icon.setVisibility(0);
        return;
      }
      setBackgroundColor(0);
      this.index.setTextColor(-855638017);
      this.name.setTextColor(-855638017);
      this.name.setShadowLayer(3.0F, 1.0F, 1.0F, 0);
      this.index.setShadowLayer(3.0F, 1.0F, 1.0F, 0);
      this.icon.setVisibility(4);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.TracePlayDialog
 * JD-Core Version:    0.6.2
 */