package com.starcor.hunan;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.TextView;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.SpeedStruct;
import com.starcor.core.http.BitmapCache;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.utils.FileDownSpeed;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.SupperToast;
import com.starcor.hunan.opendownload.logupload.LogCacheManger;
import com.starcor.hunan.opendownload.logupload.LogCacheManger.ErrorType;
import com.starcor.hunan.util.LogSwitchSpecialKeyOperation;
import com.starcor.hunan.util.SpecialKeyOperation;
import com.starcor.hunan.widget.CityPadView;
import com.starcor.hunan.widget.CommDialog;
import com.starcor.hunan.widget.CommonProblemView;
import com.starcor.hunan.widget.DeviceNameView;
import com.starcor.hunan.widget.HighLightButton;
import com.starcor.hunan.widget.MicroBlogView;
import com.starcor.hunan.widget.SpeedLayout;
import com.starcor.hunan.widget.XulExt_SphereFlowView;
import com.starcor.hunan.widget.uilog.DispatcherEventLayout;
import com.starcor.ui.testspeed.NetOptimize;
import com.starcor.ui.testspeed.NetOptimize.OnStartListener;
import com.starcor.ui.testspeed.TestSpeedDrawView;
import com.starcor.ui.testspeed.TestSpeedView;
import com.starcor.ui.testspeed.TestSpeedView.OnBackListener;
import com.starcor.ui.testspeed.TestSpeedingView;
import com.starcor.ui.testspeed.TestSpeedingView.onTestFinishListener;
import com.starcor.xul.Prop.XulAction;
import com.starcor.xul.Utils.XulArrayList;
import com.starcor.xul.Wrapper.XulGroupAreaWrapper;
import com.starcor.xul.XulView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ServiceActivity extends BaseActivity
{
  private static final int MSG_SPEED_DATA_OK = 257;
  private static final int MSG_UPDATA_OK = 259;
  private static final int MSG_UPDATA_UI = 258;
  private static final String TAG = "ServiceActivity";
  private static final String cachePicPath = App.getAppContext().getDir("cached_pic", 0).toString();
  private static final String picPath = GlobalEnv.getInstance().getPicCachePath();
  private String _lastPageCommand = "";
  private CityPadView cityPad;
  private CommonProblemView commonProblemView = null;
  private DeviceNameView deviceView;
  private DispatcherEventLayout dispatcherEventLayout;
  private boolean isLoadSuccess = false;
  private boolean isNetworkSpeedingUp = false;
  private boolean isSafeCityDate;
  public boolean isShouldSafeCityData;
  private boolean isStartCleanCacheAnimation = false;
  private SpecialKeyOperation logSwitchOperation;
  private Context mContext;
  private final Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
      case 257:
      case 258:
      case 259:
      }
      while (true)
      {
        super.handleMessage(paramAnonymousMessage);
        return;
        if (paramAnonymousMessage.obj != null)
        {
          ArrayList localArrayList = (ArrayList)paramAnonymousMessage.obj;
          if (localArrayList.size() > 0)
          {
            ServiceActivity.access$002(ServiceActivity.this, 0);
            ServiceActivity.access$102(ServiceActivity.this, 0);
            Iterator localIterator = localArrayList.iterator();
            while (localIterator.hasNext())
            {
              SpeedStruct localSpeedStruct = (SpeedStruct)localIterator.next();
              Logger.d(" ---> 值验证 = " + localSpeedStruct.toString());
              FileDownSpeed.getInstance().add(localSpeedStruct);
              ServiceActivity.access$012(ServiceActivity.this, localSpeedStruct.time);
            }
            ServiceActivity.this.tvInfo.setText("正在进行网络优化，请稍后...");
            FileDownSpeed.getInstance().doTask();
            sendEmptyMessage(258);
          }
          else
          {
            ServiceActivity.this.tvInfo.setText("网络优化失败...");
          }
        }
        else
        {
          ServiceActivity.this.tvInfo.setText("网络优化失败...");
          continue;
          ServiceActivity.this.updateSpeedPad();
          if (ServiceActivity.this.isNetworkSpeedingUp)
          {
            if (ServiceActivity.this.hasNetWork())
            {
              sendEmptyMessageDelayed(258, 1000L);
            }
            else
            {
              ServiceActivity.this.showDialog("网络连接超时，请检查网络后再试。");
              ServiceActivity.this.tvInfo.setText("网络优化失败...");
              ServiceActivity.this.tvProgress.setText(0 + "%");
              ServiceActivity.this.mSeekBar.setProgress(0);
              ServiceActivity.access$402(ServiceActivity.this, false);
            }
          }
          else
          {
            removeMessages(258);
            continue;
            ServiceActivity.access$402(ServiceActivity.this, false);
          }
        }
      }
    }
  };
  private SeekBar mSeekBar;
  private View microBlogView;
  private boolean needUpdateSpeedPad = true;
  private NetOptimize nop;
  private LinearLayout rightContent;
  private LinearLayout speedLayout;
  private XulExt_SphereFlowView sphereFlowView;
  private int targetTime = 0;
  private TestSpeedView tsView;
  private TextView tvInfo;
  private TextView tvProgress;
  private int updataTime = 0;

  private void cleanCacheFile()
  {
    try
    {
      deleteFolderFile(picPath, false);
      try
      {
        label8: deleteFolderFile(cachePicPath, false);
        return;
      }
      catch (IOException localIOException2)
      {
      }
    }
    catch (IOException localIOException1)
    {
      break label8;
    }
  }

  public static long getFolderSize(File paramFile)
    throws Exception
  {
    long l = 0L;
    File[] arrayOfFile = paramFile.listFiles();
    int i = 0;
    if (i < arrayOfFile.length)
    {
      if (arrayOfFile[i].isDirectory());
      for (l += getFolderSize(arrayOfFile[i]); ; l += arrayOfFile[i].length())
      {
        i++;
        break;
      }
    }
    return l;
  }

  private int getcacheFileSize()
  {
    try
    {
      long l2 = getFolderSize(new File(picPath));
      f1 = (float)l2 / 1048576.0F;
      try
      {
        long l1 = getFolderSize(new File(cachePicPath));
        f2 = (float)l1 / 1048576.0F;
        return (int)(f1 + f2);
      }
      catch (Exception localException2)
      {
        while (true)
          float f2 = 0.0F;
      }
    }
    catch (Exception localException1)
    {
      while (true)
        float f1 = 0.0F;
    }
  }

  private boolean hasNetWork()
  {
    return ((ConnectivityManager)this.context.getSystemService("connectivity")).getActiveNetworkInfo() != null;
  }

  private void initViews()
  {
    if ((AppFuncCfg.isMgVersion2) && (AppFuncCfg.FUNCTION_MICRO_BLOG));
    this.rightContent = ((LinearLayout)findViewById(2131165225));
    this.rightContent.setPadding(App.Operation(100.0F), App.Operation(0.0F), 0, 0);
  }

  private void setCacheTips(int paramInt)
  {
    XulView localXulView1 = xulFindViewById("cache_size");
    localXulView1.setAttr("text", String.valueOf(paramInt));
    XulView localXulView2;
    if (paramInt < 10)
    {
      localXulView1.setStyle("padding-left", String.valueOf(60));
      localXulView1.resetRender();
      localXulView2 = xulFindViewById("memory_tips");
      if (paramInt > 50)
        break label88;
    }
    label88: for (String str = "内存占用不严重，请定期清理"; ; str = "内存占用严重，请立即清理")
    {
      localXulView2.setAttr("text", str);
      localXulView2.resetRender();
      return;
      localXulView1.setStyle("padding-left", String.valueOf(0));
      break;
    }
  }

  private void setDetailsFocusable(boolean paramBoolean)
  {
    XulView localXulView = xulFindViewById("custom_content_view");
    if (localXulView != null)
      localXulView.setEnabled(paramBoolean);
  }

  private void showAbout()
  {
    setDetailsFocusable(false);
    try
    {
      XulView localXulView = xulFindViewById("page_detail_about");
      localXulView.setStyle("display", "block");
      localXulView.resetRender();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void showChangePic()
  {
    this.rightContent.setPadding(App.Operation(105.0F), App.Operation(60.0F), App.Operation(30.0F), App.Operation(30.0F));
    this.microBlogView = new MicroBlogView(this.mContext);
    this.rightContent.addView(this.microBlogView);
  }

  private void showCitySettingView()
  {
    if (hasNetWork())
      setDetailsFocusable(true);
    while (true)
    {
      this.rightContent.setPadding(App.Operation(100.0F), App.Operation(40.0F), 0, 0);
      Logger.d("地区设置");
      if (this.cityPad == null)
      {
        Logger.d("新建地区界面");
        this.cityPad = new CityPadView(this.mContext);
      }
      ViewGroup.LayoutParams localLayoutParams = new ViewGroup.LayoutParams(-1, -1);
      this.rightContent.addView(this.cityPad, localLayoutParams);
      return;
      setDetailsFocusable(false);
    }
  }

  private void showCleanDialog()
  {
    CommDialog localCommDialog = new CommDialog(this.context, 2131296258);
    localCommDialog.setType(1);
    localCommDialog.setMessage("是否确认清除?");
    localCommDialog.setTitle("按\"返回\"键退出");
    localCommDialog.setNegativeButton(new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        ServiceActivity.this.cleanCacheFile();
        ServiceActivity.this.startCleanCacheAnimation();
      }
    });
    localCommDialog.show();
  }

  private void showCommonProblemView()
  {
    if (hasNetWork())
    {
      setDetailsFocusable(true);
      this.rightContent.setPadding(0, App.Operation(87.0F), 0, App.Operation(30.0F));
      if (this.commonProblemView != null)
        break label75;
      this.commonProblemView = new CommonProblemView(this.mContext);
    }
    while (true)
    {
      this.rightContent.addView(this.commonProblemView);
      return;
      setDetailsFocusable(false);
      break;
      label75: this.commonProblemView.moveToTop();
    }
  }

  private void showDeviceNameView()
  {
    setDetailsFocusable(true);
    this.rightContent.setPadding(App.Operation(100.0F), App.Operation(107.0F), 0, 0);
    this.deviceView = new DeviceNameView(this.mContext);
    this.rightContent.addView(this.deviceView);
  }

  private void showDialog(String paramString)
  {
    CommDialog localCommDialog = new CommDialog(this, 2131296258);
    localCommDialog.setMessage(paramString);
    localCommDialog.setType(1);
    localCommDialog.setTitle("提示  按“返回”键取消");
    localCommDialog.setNegativeButton(new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        if (ServiceActivity.this.isSafeCityDate)
          GlobalEnv.getInstance().setCityId(ServiceActivity.this.cityPad.getLastId());
        paramAnonymousDialogInterface.dismiss();
      }
    });
    localCommDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        if ((paramAnonymousKeyEvent.getAction() == 0) && (paramAnonymousInt == 4))
        {
          paramAnonymousDialogInterface.dismiss();
          return true;
        }
        return false;
      }
    });
    localCommDialog.show();
  }

  private void showMicroBlogView()
  {
    setDetailsFocusable(false);
    this.rightContent.setPadding(App.Operation(105.0F), App.Operation(107.0F), App.Operation(30.0F), App.Operation(30.0F));
    this.microBlogView = new MicroBlogView(this.mContext);
    this.rightContent.addView(this.microBlogView);
  }

  private void showNetTestView()
  {
    setDetailsFocusable(true);
    Logger.d("网络测速");
    this.rightContent.requestFocus();
    this.rightContent.setPadding(App.OperationHeight(100), App.OperationHeight(87), 0, 0);
    this.nop = new NetOptimize(this, 0);
    this.rightContent.addView(this.nop);
    this.nop.setOnStartListener(new NetOptimize.OnStartListener()
    {
      public void onStart()
      {
        ServiceActivity.this.rightContent.removeAllViews();
        LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -1);
        ServiceActivity.access$1502(ServiceActivity.this, new TestSpeedView(ServiceActivity.this.mContext));
        ServiceActivity.this.tsView.setId(222);
        ServiceActivity.this.tsView.startTest();
        ServiceActivity.this.tsView.requestFocus();
        ServiceActivity.this.rightContent.setPadding(0, App.OperationHeight(40), 0, 0);
        ServiceActivity.this.rightContent.addView(ServiceActivity.this.tsView, localLayoutParams);
        ServiceActivity.this.tsView.setOnBackListener(new TestSpeedView.OnBackListener()
        {
          public void onback()
          {
            ServiceActivity.this.rightContent.setPadding(App.OperationHeight(100), App.OperationHeight(40), 0, 0);
            ServiceActivity.this.rightContent.removeAllViews();
            new LinearLayout.LayoutParams(-1, -1);
            ServiceActivity.this.rightContent.addView(ServiceActivity.this.nop);
            ServiceActivity.this.nop.requestFocus();
          }
        });
      }
    });
  }

  private void showNetworkSpeedUpView()
  {
    setDetailsFocusable(true);
    Logger.d("网络优化");
    this.rightContent.requestFocus();
    this.rightContent.setPadding(App.Operation(100.0F), App.Operation(40.0F), 0, 0);
    Logger.d("新建优化界面");
    initSpeedPad();
    this.speedLayout.requestFocus();
    sendMessage2TestSpeed();
    this.tvInfo.setVisibility(0);
  }

  private void showStorageDiagnosisView()
  {
    setDetailsFocusable(false);
    try
    {
      XulView localXulView = xulFindViewById("page_storage_diagnosis");
      localXulView.setStyle("display", "block");
      localXulView.resetRender();
      this.sphereFlowView = ((XulExt_SphereFlowView)xulFindViewById("sphere_flow_view").getExternalView());
      i = getcacheFileSize();
      XulExt_SphereFlowView localXulExt_SphereFlowView = this.sphereFlowView;
      if (i >= 100)
      {
        f = 1.0F;
        localXulExt_SphereFlowView.setPercent(f, true);
        setCacheTips(i);
        return;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        int i;
        localException.printStackTrace();
        continue;
        float f = i / 100.0F;
      }
    }
  }

  private void showTreatyView()
  {
    setDetailsFocusable(false);
    try
    {
      XulView localXulView = xulFindViewById("page_detail_license");
      localXulView.setStyle("display", "block");
      localXulView.resetRender();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void startCleanCacheAnimation()
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        if (ServiceActivity.this.sphereFlowView == null)
          return;
        float f = ServiceActivity.this.sphereFlowView.getPercent();
        while (f >= 0.0F)
        {
          ServiceActivity.this.sphereFlowView.setPercent(f, false);
          f -= 0.01F;
          try
          {
            Thread.sleep(40L);
          }
          catch (InterruptedException localInterruptedException)
          {
            localInterruptedException.printStackTrace();
          }
        }
        ServiceActivity.this.sphereFlowView.setPercent(0.0F, true);
        ServiceActivity.access$1202(ServiceActivity.this, false);
        ServiceActivity.this.setCacheTips(0);
      }
    }).start();
  }

  private boolean switchPageDetailsByCommand(String paramString)
  {
    if (this.isNetworkSpeedingUp)
      SupperToast.makeToast(this.mContext, "请等待优化完成", 1);
    do
    {
      do
      {
        return false;
        if ("m_select_skin".equals(paramString))
        {
          this._lastPageCommand = paramString;
          startActivity(new Intent(this, ChangeSkinActivity.class));
          return true;
        }
        if ("m_open_user_backfeed_page".equals(paramString))
        {
          this._lastPageCommand = paramString;
          Intent localIntent = new Intent(this, UserFeedbackActivity.class);
          localIntent.putExtra("xulPageId", "UserFeedbackPage");
          localIntent.putExtra("xulData", "");
          localIntent.addFlags(8388608);
          startActivity(localIntent);
          return true;
        }
      }
      while (this._lastPageCommand.equals(paramString));
      this._lastPageCommand = paramString;
      this.rightContent.removeAllViews();
      XulArrayList localXulArrayList = xulFindViewsByClass("page_detail_content");
      if (localXulArrayList != null)
      {
        int i = 0;
        while (true)
          if (i < localXulArrayList.size())
            try
            {
              XulView localXulView = (XulView)localXulArrayList.get(i);
              localXulView.setStyle("display", "none");
              localXulView.resetRender();
              i++;
            }
            catch (Exception localException)
            {
              while (true)
                localException.printStackTrace();
            }
      }
      this.isShouldSafeCityData = false;
      if ("m_weixin_weibo_page".equals(paramString))
      {
        showMicroBlogView();
        return true;
      }
      if ("m_fuwu_xieyi_page".equals(paramString))
      {
        showTreatyView();
        return true;
      }
      if ("m_net_test_page".equals(paramString))
      {
        if (!AppFuncCfg.isMgVersion2)
        {
          showNetworkSpeedUpView();
          return true;
        }
        testSpeedDraw();
        return true;
      }
      if ("m_set_area_page".equals(paramString))
      {
        showCitySettingView();
        this.isShouldSafeCityData = false;
        return true;
      }
      if ("m_faq_page".equals(paramString))
      {
        showCommonProblemView();
        return true;
      }
      if ("m_about_page".equals(paramString))
      {
        showAbout();
        return true;
      }
      if ("m_network_check".equals(paramString))
      {
        showNetTestView();
        return true;
      }
      if ("m_network_check_save".equals(paramString))
      {
        showStorageDiagnosisView();
        return true;
      }
    }
    while (!"m_terminal_device_name".equals(paramString));
    showDeviceNameView();
    return true;
  }

  private boolean switchSelectedPageContent(String paramString)
  {
    try
    {
      XulGroupAreaWrapper localXulGroupAreaWrapper = XulGroupAreaWrapper.fromXulView(xulFindViewById("page_content_normal_group"));
      Iterator localIterator = localXulGroupAreaWrapper.getAllGroupItems().iterator();
      while (localIterator.hasNext())
      {
        XulView localXulView = (XulView)localIterator.next();
        if (paramString.equals(localXulView.getActionString("click")))
        {
          localXulGroupAreaWrapper.setChecked(localXulView);
          xulRequestFocus(localXulView);
        }
      }
      switchPageDetailsByCommand(paramString);
      return true;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private void updateSpeedPad()
  {
    if (this.needUpdateSpeedPad)
    {
      if (this.updataTime < this.targetTime)
      {
        this.isNetworkSpeedingUp = true;
        this.updataTime = (1 + this.updataTime);
        int i = this.updataTime * this.mSeekBar.getMax() / this.targetTime;
        this.tvProgress.setText(i + "%");
        this.mSeekBar.setProgress(i);
      }
    }
    else
      return;
    this.isNetworkSpeedingUp = false;
    this.tvInfo.setText("网络优化已完成！");
  }

  public void deleteFolderFile(String paramString, boolean paramBoolean)
    throws IOException
  {
    File localFile;
    if (!TextUtils.isEmpty(paramString))
    {
      localFile = new File(paramString);
      if (localFile.isDirectory())
      {
        File[] arrayOfFile = localFile.listFiles();
        for (int i = 0; i < arrayOfFile.length; i++)
          deleteFolderFile(arrayOfFile[i].getAbsolutePath(), true);
      }
      if (paramBoolean)
      {
        if (localFile.isDirectory())
          break label76;
        localFile.delete();
      }
    }
    label76: 
    while (localFile.listFiles().length != 0)
      return;
    localFile.delete();
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if (this.logSwitchOperation != null)
      this.logSwitchOperation.dispatchKeyEvent(paramKeyEvent);
    if (this.dispatcherEventLayout != null)
      this.dispatcherEventLayout.dispatchKeyEvent(paramKeyEvent);
    if ((paramKeyEvent.getAction() == 0) && (paramKeyEvent.getKeyCode() == 4) && (this.isShouldSafeCityData))
    {
      if ((this.cityPad != null) && (this.cityPad.isLastCity()))
      {
        this.isSafeCityDate = true;
        showDialog("城市数据保存成功");
        returnFocus();
      }
      while (true)
      {
        this.isShouldSafeCityData = false;
        return true;
        this.isSafeCityDate = false;
        showDialog("您没有选到最后一级城市，数据未保存");
      }
    }
    return super.dispatchKeyEvent(paramKeyEvent);
  }

  public void initSpeedPad()
  {
    this.speedLayout = new SpeedLayout(this.mContext);
    this.rightContent.addView(this.speedLayout);
    ((LinearLayout.LayoutParams)this.speedLayout.getLayoutParams()).gravity = 16;
    this.mSeekBar = ((SeekBar)this.speedLayout.findViewById(2131165338));
    View localView = this.speedLayout.findViewById(2131165337);
    localView.getLayoutParams().width = App.Operation(799.0F);
    localView.getLayoutParams().height = App.Operation(36.0F);
    localView.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapCache.getInstance(this.mContext).getBitmapFromCache("service_layout_seekbar_bg.png")));
    ViewGroup.LayoutParams localLayoutParams = this.mSeekBar.getLayoutParams();
    localLayoutParams.width = App.Operation(757.0F);
    localLayoutParams.height = App.Operation(36.0F);
    this.tvProgress = ((TextView)findViewById(2131165336));
    this.tvProgress.setTextSize(0, App.Operation(22.0F));
    this.tvInfo = ((TextView)findViewById(2131165339));
    this.tvInfo.setTextSize(0, App.Operation(22.0F));
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    showLoaddingDialog();
    initXul("ServicePage", true);
    xulUpdateTitle("服务", "Service");
    if (DeviceInfo.isHMD())
      this.logSwitchOperation = new LogSwitchSpecialKeyOperation(this);
    this.dispatcherEventLayout = new DispatcherEventLayout(this);
  }

  protected void onRestart()
  {
    super.onRestart();
    reportLoad(this.isLoadSuccess, null);
  }

  protected void onResume()
  {
    if (this.cityPad != null)
      this.cityPad.initViewControl();
    super.onResume();
  }

  protected void onStop()
  {
    super.onStop();
    reportExit(this.isLoadSuccess, null);
  }

  public void returnFocus()
  {
    try
    {
      xulRequestFocus((XulView)XulGroupAreaWrapper.fromXulView(xulFindViewById("page_content_normal_group")).getAllCheckedItems().get(0));
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void sendMessage2TestSpeed()
  {
    if (!hasNetWork())
    {
      showDialog("网络连接超时，请检查网络后再试。");
      return;
    }
    new Thread()
    {
      // ERROR //
      public void run()
      {
        // Byte code:
        //   0: new 24	com/starcor/core/parser/json/SpeedSAXParserJson
        //   3: dup
        //   4: invokespecial 25	com/starcor/core/parser/json/SpeedSAXParserJson:<init>	()V
        //   7: astore_1
        //   8: new 27	org/apache/http/client/methods/HttpPost
        //   11: dup
        //   12: invokespecial 28	org/apache/http/client/methods/HttpPost:<init>	()V
        //   15: astore_2
        //   16: new 30	org/apache/http/entity/mime/MultipartEntity
        //   19: dup
        //   20: invokespecial 31	org/apache/http/entity/mime/MultipartEntity:<init>	()V
        //   23: astore_3
        //   24: aload_3
        //   25: ldc 33
        //   27: new 35	org/apache/http/entity/mime/content/StringBody
        //   30: dup
        //   31: invokestatic 41	com/starcor/core/logic/UserCPLLogic:getInstance	()Lcom/starcor/core/logic/UserCPLLogic;
        //   34: invokevirtual 45	com/starcor/core/logic/UserCPLLogic:getLastPlayMgtvFileId	()Ljava/lang/String;
        //   37: invokespecial 48	org/apache/http/entity/mime/content/StringBody:<init>	(Ljava/lang/String;)V
        //   40: invokevirtual 52	org/apache/http/entity/mime/MultipartEntity:addPart	(Ljava/lang/String;Lorg/apache/http/entity/mime/content/ContentBody;)V
        //   43: aload_2
        //   44: invokestatic 57	com/starcor/mgtv/boss/WebUrlBuilder:getSpeedUrl	()Ljava/lang/String;
        //   47: invokestatic 63	java/net/URI:create	(Ljava/lang/String;)Ljava/net/URI;
        //   50: invokevirtual 67	org/apache/http/client/methods/HttpPost:setURI	(Ljava/net/URI;)V
        //   53: aload_2
        //   54: aload_3
        //   55: invokevirtual 71	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
        //   58: new 73	org/apache/http/impl/client/DefaultHttpClient
        //   61: dup
        //   62: invokespecial 74	org/apache/http/impl/client/DefaultHttpClient:<init>	()V
        //   65: aload_2
        //   66: invokevirtual 78	org/apache/http/impl/client/DefaultHttpClient:execute	(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
        //   69: astore 6
        //   71: aload 6
        //   73: invokeinterface 84 1 0
        //   78: invokeinterface 90 1 0
        //   83: istore 7
        //   85: iload 7
        //   87: sipush 200
        //   90: if_icmpne +64 -> 154
        //   93: aload 6
        //   95: invokeinterface 94 1 0
        //   100: invokeinterface 100 1 0
        //   105: astore 8
        //   107: aload_0
        //   108: getfield 15	com/starcor/hunan/ServiceActivity$6:this$0	Lcom/starcor/hunan/ServiceActivity;
        //   111: invokestatic 104	com/starcor/hunan/ServiceActivity:access$1900	(Lcom/starcor/hunan/ServiceActivity;)Landroid/os/Handler;
        //   114: invokevirtual 110	android/os/Handler:obtainMessage	()Landroid/os/Message;
        //   117: astore 9
        //   119: aload 9
        //   121: aload_1
        //   122: aload 8
        //   124: invokevirtual 114	com/starcor/core/parser/json/SpeedSAXParserJson:parser	(Ljava/io/InputStream;)Ljava/lang/Object;
        //   127: putfield 120	android/os/Message:obj	Ljava/lang/Object;
        //   130: aload 9
        //   132: sipush 257
        //   135: putfield 124	android/os/Message:what	I
        //   138: aload 9
        //   140: invokevirtual 127	android/os/Message:sendToTarget	()V
        //   143: return
        //   144: astore 4
        //   146: aload 4
        //   148: invokevirtual 130	java/io/UnsupportedEncodingException:printStackTrace	()V
        //   151: goto -108 -> 43
        //   154: ldc 132
        //   156: new 134	java/lang/StringBuilder
        //   159: dup
        //   160: invokespecial 135	java/lang/StringBuilder:<init>	()V
        //   163: ldc 137
        //   165: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   168: iload 7
        //   170: invokevirtual 144	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
        //   173: ldc 146
        //   175: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   178: invokestatic 57	com/starcor/mgtv/boss/WebUrlBuilder:getSpeedUrl	()Ljava/lang/String;
        //   181: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   184: invokevirtual 149	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   187: invokestatic 155	com/starcor/core/utils/Logger:e	(Ljava/lang/String;Ljava/lang/String;)V
        //   190: return
        //   191: astore 5
        //   193: aload 5
        //   195: invokevirtual 156	java/lang/Exception:printStackTrace	()V
        //   198: ldc 132
        //   200: new 134	java/lang/StringBuilder
        //   203: dup
        //   204: invokespecial 135	java/lang/StringBuilder:<init>	()V
        //   207: ldc 158
        //   209: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   212: invokestatic 57	com/starcor/mgtv/boss/WebUrlBuilder:getSpeedUrl	()Ljava/lang/String;
        //   215: invokevirtual 141	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   218: invokevirtual 149	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   221: invokestatic 155	com/starcor/core/utils/Logger:e	(Ljava/lang/String;Ljava/lang/String;)V
        //   224: return
        //
        // Exception table:
        //   from	to	target	type
        //   24	43	144	java/io/UnsupportedEncodingException
        //   53	85	191	java/lang/Exception
        //   93	143	191	java/lang/Exception
        //   154	190	191	java/lang/Exception
      }
    }
    .start();
  }

  public void testSpeedDraw()
  {
    setDetailsFocusable(true);
    this.rightContent.setPadding(App.Operation(100.0F), App.Operation(87.0F), 0, 0);
    this.nop = new NetOptimize(this.context, 1);
    this.rightContent.addView(this.nop);
    this.nop.setOnStartListener(new NetOptimize.OnStartListener()
    {
      private TestSpeedingView tsiv;

      public void onStart()
      {
        ServiceActivity.this.rightContent.removeAllViews();
        this.tsiv = new TestSpeedingView(ServiceActivity.this.mContext);
        ServiceActivity.this.rightContent.setPadding(App.Operation(50.0F), App.Operation(40.0F), App.Operation(50.0F), 0);
        LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -1);
        ServiceActivity.this.rightContent.addView(this.tsiv, localLayoutParams);
        this.tsiv.startTest();
        this.tsiv.setOnFinishListener(new TestSpeedingView.onTestFinishListener()
        {
          public void onTestFinish(ArrayList<Integer> paramAnonymous2ArrayList1, ArrayList<Integer> paramAnonymous2ArrayList2, int paramAnonymous2Int)
          {
            LogCacheManger.getInstance().notifyWriteFile(LogCacheManger.ErrorType.TEST_SPEED, "");
            ServiceActivity.this.rightContent.removeAllViews();
            TestSpeedDrawView localTestSpeedDrawView = new TestSpeedDrawView(ServiceActivity.this.mContext, paramAnonymous2ArrayList1, paramAnonymous2ArrayList2, paramAnonymous2Int);
            localTestSpeedDrawView.setPadding(0, 0, 0, App.Operation(10.0F));
            ServiceActivity.this.rightContent.setPadding(App.Operation(100.0F), App.Operation(20.0F), 0, App.Operation(10.0F));
            localTestSpeedDrawView.setOnButtonClickListener(new View.OnClickListener()
            {
              public void onClick(View paramAnonymous3View)
              {
                ServiceActivity.this.rightContent.removeAllViews();
                LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -1);
                ServiceActivity.this.rightContent.setPadding(App.Operation(50.0F), App.Operation(40.0F), App.Operation(50.0F), 0);
                ServiceActivity.this.rightContent.addView(ServiceActivity.5.this.tsiv, localLayoutParams);
                ServiceActivity.5.this.tsiv.reTest();
              }
            });
            LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -1);
            ServiceActivity.this.rightContent.addView(localTestSpeedDrawView, localLayoutParams);
          }

          public void onTestStop()
          {
            ServiceActivity.this.rightContent.removeAllViews();
            ServiceActivity.this.rightContent.setPadding(App.Operation(100.0F), App.Operation(40.0F), 0, 0);
            new LinearLayout.LayoutParams(-1, -1);
            ServiceActivity.this.rightContent.addView(ServiceActivity.this.nop);
            ServiceActivity.this.nop.tv_start.requestFocus();
          }
        });
      }
    });
  }

  protected boolean xulDoAction(XulView paramXulView, String paramString1, String paramString2, String paramString3, Object paramObject)
  {
    boolean bool = true;
    if (!xulIsReady())
      bool = false;
    do
    {
      return bool;
      if (!"immediately_clean".equals(paramString2))
        break;
    }
    while (this.isStartCleanCacheAnimation);
    showCleanDialog();
    return bool;
    switchPageDetailsByCommand(paramString3);
    return super.xulDoAction(paramXulView, paramString1, paramString2, paramString3, paramObject);
  }

  protected void xulOnRenderIsReady()
  {
    super.xulOnRenderIsReady();
    setContentView(2130903043);
    this.mContext = this;
    App.getInstance().getTaskService();
    initViews();
    String str1 = getIntent().getStringExtra("action_args");
    String str2 = getIntent().getStringExtra("showNetworkSpeedUpView");
    if (!TextUtils.isEmpty(str1))
      str2 = getIntent().getStringExtra("action");
    try
    {
      String str3 = ((XulView)XulGroupAreaWrapper.fromXulView(xulFindViewById("page_content_normal_group")).getAllCheckedItems().get(0)).getAction("click").getStringValue();
      if (!TextUtils.isEmpty(str2))
        switchSelectedPageContent(str2);
      while (true)
      {
        this.isLoadSuccess = true;
        reportLoad(this.isLoadSuccess, null);
        dismissLoaddingDialog();
        return;
        switchSelectedPageContent(str3);
      }
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.ServiceActivity
 * JD-Core Version:    0.6.2
 */