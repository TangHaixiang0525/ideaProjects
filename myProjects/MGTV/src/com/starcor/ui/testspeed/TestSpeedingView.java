package com.starcor.ui.testspeed;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.TextView;
import com.starcor.core.http.BitmapCache;
import com.starcor.core.net.NetSpeedTest.DownloadSpeed;
import com.starcor.core.net.NetSpeedTest.ScDownloadSpeed;
import com.starcor.core.net.NetTools;
import com.starcor.core.net.NetworkAnalyze;
import com.starcor.core.net.NetworkAnalyze.NetworkAnalyzeListener;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.interfaces.ServiceSelectBtnCallBack;
import com.starcor.hunan.widget.HighLightButton;
import java.util.ArrayList;

public class TestSpeedingView extends LinearLayout
  implements ServiceSelectBtnCallBack
{
  private static final int MSG_STOP = 6;
  private static final int MSG_TOGETWAY_ERROR = 2;
  private static final int MSG_TOSERVER_ERROR = 4;
  private static final int MSG_TOSERVER_OK = 3;
  private static final int MSG_TOSERVER_START = 5;
  private static final int MSG_UPDATE_SEEKBAR = 99;
  private static final int MSG_UPDATE_SEEKBAR1 = 7;
  private static final int MSG_UPDATE_SEEKBAR2 = 8;
  private static final String TAG = "TestSpeedingView";
  private static final int TAG_RETEST = 10;
  private static final int TAG_STOP_TEST = 9;
  private Context mContext;
  private Handler mHandler = new Handler()
  {
    private int caculateProgress()
    {
      float f = 90.0F / TestSpeedingView.this.mTimeLen;
      int i = (int)(f + TestSpeedingView.this.point);
      TestSpeedingView.access$702(TestSpeedingView.this, f + TestSpeedingView.this.point - i);
      Logger.i("TestSpeedingView", "integer=" + i + ",point=" + TestSpeedingView.this.point);
      return i;
    }

    private void toDrawView()
    {
      TestSpeedingView.access$902(TestSpeedingView.this, 2);
      TestSpeedingView.this.mOnTestFinishListener.onTestFinish(TestSpeedingView.this.mSpeeds, TestSpeedingView.this.mshitTimeSpeeds, TestSpeedingView.this.mServerCount);
    }

    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
      case 99:
      case 2:
      case 4:
      case 3:
      case 6:
        do
        {
          return;
          if (!NetTools.isNetworkConnected(TestSpeedingView.this.mContext))
          {
            TestSpeedingView.this.TestError();
            return;
          }
          int k = TestSpeedingView.this.seekBar.getProgress();
          Logger.i("TestSpeedingView", "handleMessage progress:" + k);
          if (k < 10)
          {
            int m = k + 1;
            TestSpeedingView.this.seekBar.setProgress(m);
            TestSpeedingView.this.tv_progress.setText(m + "%");
            return;
          }
          TestSpeedingView.access$402(TestSpeedingView.this, false);
          return;
          TestSpeedingView.this.TestError();
          return;
          TestSpeedingView.access$502(TestSpeedingView.this, false);
          if (TestSpeedingView.this.seekBar.getProgress() < 100)
          {
            int j = 5 + TestSpeedingView.this.seekBar.getProgress();
            if (j > 100)
              j = 100;
            TestSpeedingView.this.seekBar.setProgress(j);
            TestSpeedingView.this.tv_progress.setText(j + "%");
            sendEmptyMessageDelayed(3, 1000L);
            return;
          }
          toDrawView();
          return;
        }
        while (TestSpeedingView.this.mOnTestFinishListener == null);
        TestSpeedingView.this.mOnTestFinishListener.onTestStop();
        TestSpeedingView.this.startTestUI();
        return;
      case 7:
        TestSpeedingView.access$402(TestSpeedingView.this, false);
        TestSpeedingView.this.seekBar.setProgress(10);
        TestSpeedingView.this.tv_progress.setText("10%");
        TestSpeedingView.access$702(TestSpeedingView.this, 0.0F);
        return;
      case 8:
      }
      if (!NetTools.isNetworkConnected(TestSpeedingView.this.mContext))
      {
        TestSpeedingView.this.TestError();
        return;
      }
      int i = TestSpeedingView.this.seekBar.getProgress() + caculateProgress();
      if (i < 100)
      {
        TestSpeedingView.this.seekBar.setProgress(i);
        TestSpeedingView.this.tv_progress.setText(i + "%");
        return;
      }
      TestSpeedingView.access$502(TestSpeedingView.this, false);
    }
  };
  private onTestFinishListener mOnTestFinishListener;
  private View mSeekBarLayout;
  private int mServerCount;
  private ArrayList<Integer> mSpeeds;
  private int mTimeLen;
  private ArrayList<Integer> mshitTimeSpeeds;
  private boolean needUpdate = true;
  private boolean needUpdate2 = true;
  private NetworkAnalyze netWork;
  private float point;
  private SeekBar seekBar;
  private TextView tv_info;
  private TextView tv_progress;
  private HighLightButton tv_retest;

  public TestSpeedingView(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    initViews();
  }

  private void TestError()
  {
    this.needUpdate = false;
    this.needUpdate2 = false;
    this.seekBar.setVisibility(8);
    this.mSeekBarLayout.setVisibility(8);
    this.tv_progress.setVisibility(4);
    this.tv_info.setText("无法进行网络优化，请检查网络连接后再试。");
    this.tv_retest.setText("重新优化");
    this.tv_retest.setTag(Integer.valueOf(10));
  }

  private void initViews()
  {
    View localView = View.inflate(this.mContext, 2130903079, this);
    this.seekBar = ((SeekBar)localView.findViewById(2131165338));
    this.tv_progress = ((TextView)localView.findViewById(2131165336));
    this.tv_progress.setTextSize(0, App.OperationHeight(22));
    this.tv_progress.setGravity(17);
    this.mSeekBarLayout = localView.findViewById(2131165337);
    this.mSeekBarLayout.getLayoutParams().width = App.Operation(799.0F);
    this.mSeekBarLayout.getLayoutParams().height = App.Operation(36.0F);
    this.mSeekBarLayout.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapCache.getInstance(this.mContext).getBitmapFromCache("service_layout_seekbar_bg.png")));
    ViewGroup.LayoutParams localLayoutParams = this.seekBar.getLayoutParams();
    localLayoutParams.width = App.Operation(757.0F);
    localLayoutParams.height = App.Operation(36.0F);
    ((LinearLayout.LayoutParams)this.tv_progress.getLayoutParams()).bottomMargin = App.Operation(20.0F);
    this.tv_info = ((TextView)localView.findViewById(2131165339));
    this.tv_info.setText("正在进行网络优化，请稍后...");
    this.tv_info.setTextSize(0, App.OperationHeight(22));
    ((LinearLayout.LayoutParams)this.tv_info.getLayoutParams()).topMargin = App.Operation(20.0F);
    this.tv_info.setGravity(17);
    this.tv_info.setTextSize(0, App.OperationFolat(24.0F));
    LinearLayout localLinearLayout = (LinearLayout)localView.findViewById(2131165335);
    this.tv_retest = new HighLightButton(this.mContext);
    this.tv_retest.setText("停止优化");
    this.tv_retest.setId(2131165205);
    this.tv_retest.setFocusable(true);
    this.tv_retest.setBackgroundResource(2130837507);
    this.tv_retest.setGravity(17);
    this.tv_retest.setTextColor(-7631989);
    this.tv_retest.setTag(Integer.valueOf(9));
    this.tv_retest.setNextFocusUpId(this.tv_retest.getId());
    this.tv_retest.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
        {
          paramAnonymousView.setBackgroundResource(2130837508);
          TestSpeedingView.this.tv_retest.setTextColor(-1);
          return;
        }
        paramAnonymousView.setBackgroundResource(2130837507);
        TestSpeedingView.this.tv_retest.setTextColor(-7631989);
      }
    });
    this.tv_retest.setOnKeyListener(new View.OnKeyListener()
    {
      public boolean onKey(View paramAnonymousView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        int i;
        if (paramAnonymousInt == 66)
        {
          i = 1;
          if (paramAnonymousInt != 23)
            break label34;
        }
        label34: for (int j = 1; ; j = 0)
        {
          if ((j | i) == 0)
            break label40;
          return false;
          i = 0;
          break;
        }
        label40: if (paramAnonymousInt == 21)
        {
          int m = ((Integer)TestSpeedingView.this.tv_retest.getTag()).intValue();
          Logger.i("TestSpeedingView", "onKey back tag: " + m);
          if (10 == m)
          {
            Logger.i("TestSpeedingView", "onkey back page");
            return false;
          }
          return true;
        }
        if (paramAnonymousInt == 4)
        {
          int k = ((Integer)TestSpeedingView.this.tv_retest.getTag()).intValue();
          Logger.i("TestSpeedingView", "onKey back tag: " + k);
          if (10 == k)
          {
            Logger.i("TestSpeedingView", "onkey back page");
            return false;
          }
          if (TestSpeedingView.this.netWork != null)
          {
            TestSpeedingView.this.tv_retest.setTag(Integer.valueOf(10));
            TestSpeedingView.this.netWork.stopNetworkOptimize();
            TestSpeedingView.this.stopTestUI();
          }
          return true;
        }
        return true;
      }
    });
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(App.Operation(155.0F), App.Operation(65.0F));
    localLayoutParams1.topMargin = App.Operation(20.0F);
    localLinearLayout.addView(this.tv_retest, localLayoutParams1);
    this.tv_retest.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        TestSpeedingView.this.processButton(paramAnonymousView);
      }
    });
    this.tv_retest.requestFocus();
    this.mSpeeds = new ArrayList();
    this.mshitTimeSpeeds = new ArrayList();
  }

  private void processButton(View paramView)
  {
    if (9 == ((Integer)paramView.getTag()).intValue())
    {
      paramView.setTag(Integer.valueOf(10));
      if (this.netWork != null)
      {
        this.netWork.stopNetworkOptimize();
        stopTestUI();
      }
    }
    while (!NetTools.isNetworkConnected(this.mContext))
      return;
    paramView.setTag(Integer.valueOf(9));
    reTest();
  }

  public void reTest()
  {
    this.needUpdate = true;
    this.needUpdate2 = true;
    this.seekBar.setVisibility(0);
    this.mSeekBarLayout.setVisibility(0);
    this.tv_progress.setVisibility(0);
    this.tv_info.setText("正在进行网络优化，请稍后......");
    this.tv_retest.setText("停止优化");
    this.tv_retest.requestFocus();
    this.seekBar.setProgress(0);
    this.tv_progress.setText("0%");
    new MyTimer().start();
    this.netWork.startNetworkOptimize();
  }

  public void setNextLeftButton(HighLightButton paramHighLightButton)
  {
    this.tv_retest.setNextFocusLeftId(paramHighLightButton.getId());
  }

  public void setOnFinishListener(onTestFinishListener paramonTestFinishListener)
  {
    this.mOnTestFinishListener = paramonTestFinishListener;
  }

  public void startTest()
  {
    Logger.i("TestSpeedingView", "startTest ");
    new MyTimer().start();
    this.netWork = NetworkAnalyze.getInstance();
    this.netWork.startNetworkOptimize();
    this.netWork.setNetworkAnalyzeListener(new NetworkAnalyze.NetworkAnalyzeListener()
    {
      public void analyzeState(int paramAnonymousInt1, int paramAnonymousInt2, ArrayList<NetSpeedTest.DownloadSpeed> paramAnonymousArrayList, ArrayList<NetSpeedTest.ScDownloadSpeed> paramAnonymousArrayList1)
      {
        Logger.i("TestSpeedingView", "analyzeState networkPath:" + paramAnonymousInt1 + "  analyzeState:" + paramAnonymousInt2);
        if (paramAnonymousInt1 == 1)
          switch (paramAnonymousInt2)
          {
          case 3:
          default:
          case 2:
          case 4:
          }
        while (true)
        {
          if (paramAnonymousInt2 == 5)
            TestSpeedingView.this.mHandler.sendEmptyMessage(6);
          return;
          TestSpeedingView.this.mHandler.sendEmptyMessage(2);
          continue;
          if (paramAnonymousInt1 == 2)
            switch (paramAnonymousInt2)
            {
            case 1:
            default:
              break;
            case 2:
            case 4:
              TestSpeedingView.this.mHandler.sendEmptyMessage(4);
              break;
            case 3:
              TestSpeedingView.this.mSpeeds.clear();
              for (int i = 0; (paramAnonymousArrayList != null) && (i < paramAnonymousArrayList.size()); i++)
              {
                int m = ((NetSpeedTest.DownloadSpeed)paramAnonymousArrayList.get(i)).speed;
                if (m < 0)
                  m = 0;
                TestSpeedingView.this.mSpeeds.add(Integer.valueOf(m));
                Logger.i("TestSpeedingView", "analyzeState 点播速度 mgtvSpeeds i:" + ((NetSpeedTest.DownloadSpeed)paramAnonymousArrayList.get(i)).speed);
              }
              TestSpeedingView.this.mshitTimeSpeeds.clear();
              for (int j = 0; (paramAnonymousArrayList1 != null) && (j < paramAnonymousArrayList1.size()); j++)
              {
                int k = ((NetSpeedTest.ScDownloadSpeed)paramAnonymousArrayList1.get(j)).kbsp;
                if (k < 0)
                  k = 0;
                TestSpeedingView.this.mshitTimeSpeeds.add(Integer.valueOf(k));
                Logger.i("TestSpeedingView", "analyzeState  回看速度 scSpeeds i:" + ((NetSpeedTest.ScDownloadSpeed)paramAnonymousArrayList1.get(j)).kbsp);
              }
              TestSpeedingView.this.mHandler.sendEmptyMessage(3);
            }
        }
      }

      public void optimizeTimeLength(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        TestSpeedingView.access$802(TestSpeedingView.this, paramAnonymousInt2);
        TestSpeedingView.this.mHandler.sendEmptyMessage(7);
        new TestSpeedingView.MyTimer2(TestSpeedingView.this).start();
      }
    });
  }

  public void startTestUI()
  {
    this.tv_retest.setText("停止优化");
    this.tv_info.setText("正在进行网络优化，请稍后...");
    this.needUpdate = true;
    this.needUpdate2 = true;
  }

  public void stopTestUI()
  {
    this.tv_retest.setText("停止中...");
    this.tv_info.setText("停止中，请稍后...");
    this.needUpdate = false;
    this.needUpdate2 = false;
  }

  class MyTimer extends Thread
  {
    MyTimer()
    {
    }

    public void run()
    {
      while (TestSpeedingView.this.needUpdate)
      {
        TestSpeedingView.this.mHandler.sendEmptyMessage(99);
        try
        {
          sleep(1000L);
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
        }
      }
    }
  }

  class MyTimer2 extends Thread
  {
    MyTimer2()
    {
    }

    public void run()
    {
      while (TestSpeedingView.this.needUpdate2)
      {
        TestSpeedingView.this.mHandler.sendEmptyMessage(8);
        try
        {
          sleep(1000L);
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
        }
      }
    }
  }

  public static abstract interface onTestFinishListener
  {
    public abstract void onTestFinish(ArrayList<Integer> paramArrayList1, ArrayList<Integer> paramArrayList2, int paramInt);

    public abstract void onTestStop();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.ui.testspeed.TestSpeedingView
 * JD-Core Version:    0.6.2
 */