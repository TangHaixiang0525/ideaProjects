package com.starcor.ui.testspeed;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.starcor.core.net.NetSpeedTest.DownloadSpeed;
import com.starcor.core.net.NetSpeedTest.ScDownloadSpeed;
import com.starcor.core.net.NetworkAnalyze;
import com.starcor.core.net.NetworkAnalyze.NetworkAnalyzeListener;
import com.starcor.core.service.BitmapService;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.interfaces.ServiceSelectBtnCallBack;
import com.starcor.hunan.widget.HighLightButton;
import java.util.ArrayList;

public class TestSpeedView extends LinearLayout
  implements ServiceSelectBtnCallBack
{
  private static final int MSG_STOP = 6;
  private static final int MSG_TOGETWAY_ERROR = 2;
  private static final int MSG_TOGETWAY_OK = 1;
  private static final int MSG_TOSERVER_ERROR = 4;
  private static final int MSG_TOSERVER_OK = 3;
  private static final int MSG_TOSERVER_START = 5;
  private static final String TAG = "TestSpeedView";
  private static final String TAG_RESET = "re_test";
  private static final String TAG_SET_NETWORK = "set_network";
  private static final String TAG_STOP_TEST = "stop_test";
  private ConnectView cView;
  private ConnectView cView1;
  private boolean canreTest = false;
  private Context mContext;
  private Handler mHandler = new Handler(Looper.getMainLooper())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      case 5:
      default:
      case 2:
      case 1:
      case 4:
      case 3:
      case 6:
      }
      do
      {
        return;
        Logger.i("TestSpeedView", "handleMessage MSG_TOGETWAY_ERROR");
        TestSpeedView.access$002(TestSpeedView.this, true);
        TestSpeedView.this.cView.stopConnect(false);
        TestSpeedView.this.tv_content.setVisibility(8);
        TestSpeedView.this.tv_content1.setVisibility(0);
        TestSpeedView.this.cView1.initView();
        TestSpeedView.this.tv_content1.setText("无法连接到网关，请检查以下内容：\n\n1.确认网络连接正确且工作正常。\n2.如果网关连接正确，请点击\"设置网络\"，查看网络。");
        TestSpeedView.this.tv_stop.setText("设置网络");
        TestSpeedView.this.tv_stop.setTag("set_network");
        return;
        Logger.i("TestSpeedView", "handleMessage MSG_TOGETWAY_OK");
        TestSpeedView.this.cView.stopConnect(true);
        return;
        TestSpeedView.access$002(TestSpeedView.this, true);
        Logger.i("TestSpeedView", "handleMessage MSG_TOSERVER_ERROR");
        TestSpeedView.this.cView1.stopConnect(false);
        TestSpeedView.this.tv_content.setVisibility(8);
        TestSpeedView.this.tv_content1.setVisibility(0);
        TestSpeedView.this.tv_content1.setText("无法连接到芒果服务器，请拨打客服电话 0731-88332525\n点击\"重新测试\"，可重新测试。");
        TestSpeedView.this.changeToTest();
        return;
        TestSpeedView.this.cView1.stopConnect(true);
        TestSpeedView.this.tv_content.setVisibility(0);
        TestSpeedView.this.tv_content1.setVisibility(8);
        if (TestSpeedView.this.speed > 4000)
          TestSpeedView.this.tv_content.setText("平均下载速度是" + TestSpeedView.this.speed + "Kbps，可以观看高清内容。");
        while (true)
        {
          TestSpeedView.access$002(TestSpeedView.this, true);
          TestSpeedView.this.changeToTest();
          return;
          if (TestSpeedView.this.speed > 2000)
            TestSpeedView.this.tv_content.setText("平均下载速度是" + TestSpeedView.this.speed + "Kbps，可以观看标清内容。");
          else
            TestSpeedView.this.tv_content.setText("平均下载速度是" + TestSpeedView.this.speed + "Kbps，网速较慢，建议与当地运营商联系。");
        }
      }
      while (TestSpeedView.this.mOnBackListener == null);
      TestSpeedView.this.mOnBackListener.onback();
    }
  };
  private OnBackListener mOnBackListener;
  private NetworkAnalyze network;
  private int speed = 0;
  private TextView tv_content;
  private TextView tv_content1;
  private HighLightButton tv_stop;

  public TestSpeedView(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    initViews();
  }

  private void changeToTest()
  {
    this.tv_stop.setText("重新测试");
    this.tv_stop.setTag("re_test");
  }

  private LinearLayout getViewLayout(String paramString1, String paramString2, LinearLayout.LayoutParams paramLayoutParams)
  {
    LinearLayout localLinearLayout = new LinearLayout(this.mContext);
    localLinearLayout.setGravity(17);
    localLinearLayout.setOrientation(1);
    ImageView localImageView = new ImageView(this.mContext);
    localImageView.setImageBitmap(BitmapService.getInstance(this.mContext).getBitmap(paramString1));
    localLinearLayout.addView(localImageView, paramLayoutParams);
    TextView localTextView = new TextView(this.mContext);
    localTextView.setText(paramString2);
    localTextView.setTextSize(0, App.Operation(24.0F));
    localTextView.setTextColor(-1);
    localTextView.setGravity(17);
    localLinearLayout.addView(localTextView, new LinearLayout.LayoutParams(App.Operation(131.0F), App.Operation(62.0F)));
    return localLinearLayout;
  }

  private void initPicView()
  {
    LinearLayout localLinearLayout1 = new LinearLayout(this.mContext);
    localLinearLayout1.setOrientation(0);
    localLinearLayout1.setGravity(17);
    addView(localLinearLayout1, new LinearLayout.LayoutParams(-1, App.Operation(200.0F)));
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(App.Operation(131.0F), App.Operation(62.0F));
    localLayoutParams1.bottomMargin = App.Operation(10.0F);
    LinearLayout localLinearLayout2 = getViewLayout("terminal.png", "终端", localLayoutParams1);
    LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(App.Operation(131.0F), App.Operation(100.0F));
    localLayoutParams2.leftMargin = App.Operation(20.0F);
    localLayoutParams2.topMargin = App.Operation(43.0F);
    localLinearLayout1.addView(localLinearLayout2, localLayoutParams2);
    this.cView = new ConnectView(this.mContext);
    LinearLayout.LayoutParams localLayoutParams3 = new LinearLayout.LayoutParams(App.Operation(100.0F), App.Operation(62.0F));
    localLayoutParams3.leftMargin = App.Operation(35.0F);
    localLayoutParams3.topMargin = App.Operation(15.0F);
    localLinearLayout1.addView(this.cView, localLayoutParams3);
    this.network = NetworkAnalyze.getInstance();
    this.network.setNetworkAnalyzeListener(new NetworkAnalyze.NetworkAnalyzeListener()
    {
      public void analyzeState(int paramAnonymousInt1, int paramAnonymousInt2, ArrayList<NetSpeedTest.DownloadSpeed> paramAnonymousArrayList, ArrayList<NetSpeedTest.ScDownloadSpeed> paramAnonymousArrayList1)
      {
        if (paramAnonymousInt1 == 1)
          switch (paramAnonymousInt2)
          {
          default:
          case 2:
          case 4:
          case 3:
          }
        while (true)
        {
          if (paramAnonymousInt2 == 5)
            TestSpeedView.this.mHandler.sendEmptyMessage(6);
          return;
          TestSpeedView.this.mHandler.sendEmptyMessage(2);
          continue;
          TestSpeedView.this.mHandler.sendEmptyMessage(1);
          continue;
          if (paramAnonymousInt1 == 2)
            switch (paramAnonymousInt2)
            {
            default:
              break;
            case 1:
              TestSpeedView.this.cView1.startconnect();
              break;
            case 2:
            case 4:
              TestSpeedView.this.mHandler.sendEmptyMessage(4);
              break;
            case 3:
              TestSpeedView.access$702(TestSpeedView.this, 0);
              for (int i = 0; i < paramAnonymousArrayList.size(); i++)
                if (TestSpeedView.this.speed < ((NetSpeedTest.DownloadSpeed)paramAnonymousArrayList.get(i)).speed)
                  TestSpeedView.access$702(TestSpeedView.this, ((NetSpeedTest.DownloadSpeed)paramAnonymousArrayList.get(i)).speed);
              TestSpeedView.this.mHandler.sendEmptyMessage(3);
            }
        }
      }

      public void optimizeTimeLength(int paramAnonymousInt1, int paramAnonymousInt2)
      {
      }
    });
    LinearLayout localLinearLayout3 = getViewLayout("gateway.png", "网关", new LinearLayout.LayoutParams(App.Operation(131.0F), App.Operation(62.0F)));
    LinearLayout.LayoutParams localLayoutParams4 = new LinearLayout.LayoutParams(App.Operation(131.0F), App.Operation(100.0F));
    localLayoutParams4.leftMargin = App.Operation(20.0F);
    localLayoutParams4.topMargin = App.Operation(50.0F);
    localLinearLayout1.addView(localLinearLayout3, localLayoutParams4);
    this.cView1 = new ConnectView(this.mContext);
    LinearLayout.LayoutParams localLayoutParams5 = new LinearLayout.LayoutParams(App.Operation(100.0F), App.Operation(62.0F));
    localLayoutParams5.leftMargin = App.Operation(35.0F);
    localLayoutParams5.topMargin = App.Operation(15.0F);
    localLinearLayout1.addView(this.cView1, localLayoutParams5);
    LinearLayout localLinearLayout4 = getViewLayout("server.png", "服务器", new LinearLayout.LayoutParams(App.Operation(64.0F), App.Operation(117.0F)));
    LinearLayout.LayoutParams localLayoutParams6 = new LinearLayout.LayoutParams(App.Operation(131.0F), App.Operation(170.0F));
    localLayoutParams6.leftMargin = App.Operation(20.0F);
    localLayoutParams6.topMargin = App.Operation(25.0F);
    localLinearLayout1.addView(localLinearLayout4, localLayoutParams6);
  }

  private void initViews()
  {
    setOrientation(1);
    addView(new LinearLayout(this.mContext), new LinearLayout.LayoutParams(-1, App.Operation(20.0F)));
    initPicView();
    LinearLayout localLinearLayout1 = new LinearLayout(this.mContext);
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(-1, App.Operation(2.0F));
    localLayoutParams1.setMargins(App.Operation(160.0F), App.Operation(16.0F), App.Operation(160.0F), 0);
    localLinearLayout1.setBackgroundColor(-6447715);
    addView(localLinearLayout1, localLayoutParams1);
    LinearLayout localLinearLayout2 = new LinearLayout(this.mContext);
    localLinearLayout2.setOrientation(1);
    addView(localLinearLayout2, new LinearLayout.LayoutParams(-1, App.Operation(250.0F)));
    localLinearLayout2.setGravity(17);
    this.tv_content = new TextView(this.mContext);
    this.tv_content.setTextSize(0, App.Operation(24.0F));
    this.tv_content.setGravity(17);
    this.tv_content.setTextColor(-4671304);
    this.tv_content.setText("正在测试，请稍后......");
    LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(-1, App.Operation(150.0F));
    localLayoutParams2.leftMargin = App.Operation(10.0F);
    localLinearLayout2.addView(this.tv_content, localLayoutParams2);
    this.tv_content1 = new TextView(this.mContext);
    this.tv_content1.setTextSize(0, App.Operation(24.0F));
    this.tv_content1.setGravity(16);
    this.tv_content1.setTextColor(-4671304);
    this.tv_content1.setVisibility(8);
    LinearLayout.LayoutParams localLayoutParams3 = new LinearLayout.LayoutParams(-1, App.Operation(150.0F));
    localLayoutParams3.topMargin = App.Operation(-10.0F);
    localLayoutParams3.leftMargin = App.Operation(150.0F);
    localLinearLayout2.addView(this.tv_content1, localLayoutParams3);
    this.tv_stop = new HighLightButton(this.mContext);
    this.tv_stop.setText("停止测试");
    this.tv_stop.setTag("stop_test");
    this.tv_stop.setId(2131165205);
    this.tv_stop.setFocusable(true);
    this.tv_stop.setBackgroundResource(2130837507);
    this.tv_stop.setGravity(17);
    this.tv_stop.setTextColor(-7631989);
    this.tv_stop.setNextFocusUpId(this.tv_stop.getId());
    this.tv_stop.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
        {
          paramAnonymousView.setBackgroundResource(2130837508);
          TestSpeedView.this.tv_stop.setTextColor(-1);
          return;
        }
        paramAnonymousView.setBackgroundResource(2130837507);
        TestSpeedView.this.tv_stop.setTextColor(-7631989);
      }
    });
    LinearLayout.LayoutParams localLayoutParams4 = new LinearLayout.LayoutParams(App.Operation(155.0F), App.Operation(65.0F));
    localLayoutParams4.leftMargin = App.Operation(10.0F);
    localLinearLayout2.addView(this.tv_stop, localLayoutParams4);
    this.tv_stop.setOnKeyListener(new View.OnKeyListener()
    {
      public boolean onKey(View paramAnonymousView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if ((paramAnonymousKeyEvent.getAction() == 0) && (paramAnonymousInt == 4))
        {
          Logger.i("TestSpeedView", "onKeyDown back canreTest:" + TestSpeedView.this.canreTest);
          if (!TestSpeedView.this.canreTest);
        }
        do
        {
          do
          {
            return false;
            TestSpeedView.this.network.stopNetworkSpeedTest();
            TestSpeedView.this.tv_stop.setText("停止中....");
            TestSpeedView.this.tv_content.setText("停止中，请稍后......");
            TestSpeedView.this.cView.initView();
            TestSpeedView.this.cView1.initView();
            return true;
          }
          while ((paramAnonymousKeyEvent.getAction() != 0) || (paramAnonymousInt != 21));
          Logger.i("TestSpeedView", "onKey left canreTest:" + TestSpeedView.this.canreTest);
        }
        while (TestSpeedView.this.canreTest);
        return true;
      }
    });
    this.tv_stop.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        String str = (String)paramAnonymousView.getTag();
        Logger.i("TestSpeedView", "onClick tag:" + str);
        if ("re_test".equals(str))
        {
          TestSpeedView.this.cView.resetView();
          TestSpeedView.this.cView1.resetView();
          TestSpeedView.this.cView.startconnect();
          TestSpeedView.this.network.startNetworkSpeedTest();
          TestSpeedView.this.tv_stop.setText("停止测试");
          TestSpeedView.this.tv_stop.setTag("stop_test");
          ((LinearLayout.LayoutParams)TestSpeedView.this.tv_content.getLayoutParams()).leftMargin = App.Operation(0.0F);
          TestSpeedView.this.tv_content.setVisibility(0);
          TestSpeedView.this.tv_content1.setVisibility(8);
          TestSpeedView.this.tv_content.setText("正在测试，请稍后......");
        }
        do
        {
          return;
          if ("stop_test".equals(str))
          {
            TestSpeedView.this.cView.resetView();
            TestSpeedView.this.cView1.resetView();
            TestSpeedView.this.network.stopNetworkSpeedTest();
            TestSpeedView.access$002(TestSpeedView.this, false);
            TestSpeedView.this.tv_content.setText("停止中，请稍后......");
            TestSpeedView.this.tv_stop.setText("停止中....");
            TestSpeedView.this.mHandler.postDelayed(new Runnable()
            {
              public void run()
              {
                TestSpeedView.this.mOnBackListener.onback();
              }
            }
            , 1000L);
            return;
          }
        }
        while (!"set_network".equals(str));
        TestSpeedView.this.mContext.startActivity(new Intent("android.settings.SETTINGS"));
        TestSpeedView.this.mHandler.postDelayed(new Runnable()
        {
          public void run()
          {
            TestSpeedView.this.mOnBackListener.onback();
          }
        }
        , 1000L);
      }
    });
  }

  public void setNextLeftButton(HighLightButton paramHighLightButton)
  {
    Logger.i("TestSpeedView", "set focuse" + paramHighLightButton.getText() + "," + paramHighLightButton.getId());
    this.tv_stop.setNextFocusLeftId(paramHighLightButton.getId());
  }

  public void setOnBackListener(OnBackListener paramOnBackListener)
  {
    this.mOnBackListener = paramOnBackListener;
  }

  public void startTest()
  {
    this.network.startNetworkSpeedTest();
    this.cView.startconnect();
    this.tv_stop.setText("停止测试");
  }

  public static abstract interface OnBackListener
  {
    public abstract void onback();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.ui.testspeed.TestSpeedView
 * JD-Core Version:    0.6.2
 */