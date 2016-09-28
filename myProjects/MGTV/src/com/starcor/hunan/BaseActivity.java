package com.starcor.hunan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.PopupWindow;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AppVersion;
import com.starcor.core.domain.FilmListItem;
import com.starcor.core.domain.MovieData;
import com.starcor.core.domain.Version;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.AppKiller;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.hunan.uilogic.ActivityAdapterV4;
import com.starcor.hunan.uilogic.InitApiData;
import com.starcor.hunan.widget.ExitDialog;
import com.starcor.sccms.api.SccmsApiCheckUpdateTask.ISccmsApiCheckUpdateTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class BaseActivity extends DialogActivity
{
  private static final int C1_KEYCODE_SEARCH = 84;
  private static final int C1_KEYCODE_TIMESHEFT = 132;
  private static final int HUAWEI_COLLECT_KEYCODE = 0;
  private static final int MESSAGE_CHECK_UPDATE = 1000;
  private static final String TAG = "BaseActivity";
  public static boolean UPGRADE_CHECK_ENABLED = false;
  private static ArrayList<BaseActivity> activityStack = new ArrayList();
  public static boolean chNetRefreshflag = false;
  private static final int maxActivityStackDepth = 20;
  private ViewStub contentRoot;
  private boolean isRunning = false;
  private boolean isShowUpGradedialog;
  Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      super.handleMessage(paramAnonymousMessage);
      if (!BaseActivity.this.isRunning)
        Logger.w("BaseActivity", "mHandler.handleMessage: activity(" + getClass().getSimpleName() + ") does not running!!!");
      Version localVersion;
      while (true)
      {
        return;
        if ((paramAnonymousMessage.what == 1000) && (BaseActivity.UPGRADE_CHECK_ENABLED) && (paramAnonymousMessage.what == 1000))
          try
          {
            localVersion = (Version)paramAnonymousMessage.obj;
            if ((localVersion != null) && (localVersion.appVersion != null) && (localVersion.appVersion.state == 0))
              if (localVersion.appVersion.type.equals("manual"))
              {
                BaseActivity.this.showUpgradeChoiceDialog(localVersion);
                return;
              }
          }
          catch (Exception localException)
          {
            Logger.e("BaseActivity", "check update exception!!!");
            localException.printStackTrace();
            return;
          }
      }
      BaseActivity.this.startUpgradeActivity(localVersion.appVersion.url);
    }
  };
  protected boolean needEndApp = false;
  private PopupWindow popupWindow;
  private BroadcastReceiver screenReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      if ("android.intent.action.SCREEN_ON".equals(str))
      {
        Logger.i("BaseActivity", "onReceive ACTION_SCREEN_ON   UPGRADE_CHECK_ENABLED:" + BaseActivity.UPGRADE_CHECK_ENABLED + "  isUpdated:" + InitApiData.isUpdated);
        if (DeviceInfo.isHuaWei());
      }
      while (true)
      {
        return;
        if (!BaseActivity.this.isRunning)
          continue;
        if ((BaseActivity.UPGRADE_CHECK_ENABLED) && (!InitApiData.isUpdated) && (GlobalLogic.getInstance().isAppInterfaceReady()));
        try
        {
          ServerApiManager.i().APICheckUpdate(new SccmsApiCheckUpdateTask.ISccmsApiCheckUpdateTaskListener()
          {
            public void onError(ServerApiTaskInfo paramAnonymous2ServerApiTaskInfo, ServerApiCommonError paramAnonymous2ServerApiCommonError)
            {
              Message localMessage = new Message();
              localMessage.arg1 = 1000;
              BaseActivity.this.mHandler.handleMessage(localMessage);
            }

            public void onSuccess(ServerApiTaskInfo paramAnonymous2ServerApiTaskInfo, Version paramAnonymous2Version)
            {
              Message localMessage = new Message();
              localMessage.obj = paramAnonymous2Version;
              localMessage.arg1 = 1000;
              BaseActivity.this.mHandler.handleMessage(localMessage);
            }
          });
          if (!"android.intent.action.SCREEN_OFF".equals(str))
            continue;
          Logger.i("BaseActivity", "onReceive ACTION_SCREEN_OFF");
          InitApiData.isUpdated = false;
          return;
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
      }
    }
  };

  public static int activityStackDepth()
  {
    return activityStack.size();
  }

  public static <T> BaseActivity findActivityByCalss(Class<T> paramClass)
  {
    Iterator localIterator = activityStack.iterator();
    while (localIterator.hasNext())
    {
      BaseActivity localBaseActivity = (BaseActivity)localIterator.next();
      if (localBaseActivity.getClass().equals(paramClass))
        return localBaseActivity;
    }
    return null;
  }

  private void showUpgradeChoiceDialog(final Version paramVersion)
  {
    if (this.isShowUpGradedialog)
      return;
    ExitDialog local3 = new ExitDialog(this, 2131296258)
    {
      public void dismiss()
      {
        BaseActivity.access$302(BaseActivity.this, false);
        super.dismiss();
      }

      public void show()
      {
        BaseActivity.access$302(BaseActivity.this, true);
        super.show();
      }
    };
    local3.setMessage("已有新版本，是否立即升级?");
    local3.setPositiveButtonListener(new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        if (!BaseActivity.this.startUpgradeActivity(paramVersion.appVersion.url))
          paramAnonymousDialogInterface.dismiss();
      }
    });
    local3.show();
  }

  private boolean startUpgradeActivity(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return false;
    try
    {
      Intent localIntent = new Intent("com.starcor.service.intent.action.UPGRADE");
      localIntent.putExtra("url", new String[] { paramString });
      localIntent.setFlags(8388608);
      localIntent.putExtra("upgrade_silently", true);
      localIntent.putExtra("error_start_package", getPackageName());
      startActivity(localIntent);
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public void initPopWindow()
  {
    this.popupWindow = new PopupWindow(getLayoutInflater().inflate(2130903059, null), 200, 150, true);
    this.popupWindow.showAtLocation(this.contentRoot, 17, 100, 100);
  }

  public boolean isActivityAutoFinish()
  {
    return false;
  }

  public boolean isRunning()
  {
    return this.isRunning;
  }

  protected void onCreate(Bundle paramBundle)
  {
    Logger.i("BaseActivity", "onCreate " + getClass().getSimpleName());
    super.onCreate(paramBundle);
    activityStack.add(this);
    if (activityStack.size() > 20)
    {
      ArrayList localArrayList = new ArrayList();
      for (int i = 0; (i < activityStack.size()) && (activityStack.size() - localArrayList.size() > 20); i++)
        if (((BaseActivity)activityStack.get(i)).isActivityAutoFinish())
          localArrayList.add(0, Integer.valueOf(i));
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        Integer localInteger = (Integer)localIterator.next();
        ((BaseActivity)activityStack.remove(localInteger.intValue())).finish();
      }
    }
  }

  protected void onDestroy()
  {
    this.isRunning = false;
    activityStack.remove(this);
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool = true;
    Logger.i("BaseActivity", "onKeyDown  keycode:" + paramInt);
    switch (paramInt)
    {
    default:
      bool = super.onKeyDown(paramInt, paramKeyEvent);
    case 4:
    case 0:
    case 82:
    }
    do
    {
      do
      {
        return bool;
        if (!this.needEndApp)
          break;
      }
      while ((DeviceInfo.isJiuShi()) || (DeviceInfo.isHuaWei()));
      if ((DeviceInfo.isBDYB()) || (DeviceInfo.isTclMango()))
      {
        AppKiller.getInstance().killApp();
        return bool;
      }
      Logger.i("BaseActivity", "onKeyDown  endapp");
      endApp();
      return bool;
      if ((DeviceInfo.isHuaWei()) && (GlobalLogic.getInstance().isAppInterfaceReady()))
      {
        Intent localIntent = new Intent();
        localIntent.putExtra("Cmd", "com.starcor.hunan.cmd.show_collect_list");
        localIntent.setClass(this, ActivityAdapterV4.getInstance().getMyMediaActivity());
        localIntent.setFlags(8388608);
        startActivity(localIntent);
        return bool;
      }
    }
    while (!DeviceInfo.isHuaWei());
    onMenuKeyDown();
    return bool;
  }

  protected void onMenuKeyDown()
  {
  }

  protected void onPause()
  {
    this.isRunning = false;
    try
    {
      unregisterReceiver(this.screenReceiver);
      label13: super.onPause();
      return;
    }
    catch (Exception localException)
    {
      break label13;
    }
  }

  protected void onResume()
  {
    super.onResume();
    this.isRunning = true;
    registerReceiver(this.screenReceiver, new IntentFilter("android.intent.action.SCREEN_ON"));
    registerReceiver(this.screenReceiver, new IntentFilter("android.intent.action.SCREEN_OFF"));
  }

  public void refreshViews()
  {
    Logger.d("刷新了界面");
  }

  public void setContentView(int paramInt)
  {
    super.setContentView(2130903040);
    this.contentRoot = ((ViewStub)findViewById(2131165219));
    this.contentRoot.setLayoutResource(paramInt);
    this.contentRoot.inflate();
  }

  public void showDetailed(FilmListItem paramFilmListItem, int paramInt, View paramView)
  {
    MovieData localMovieData = new MovieData();
    localMovieData.packageId = paramFilmListItem.package_id;
    localMovieData.categoryId = paramFilmListItem.category_id;
    localMovieData.videoId = paramFilmListItem.video_id;
    localMovieData.videoType = paramFilmListItem.video_type;
    localMovieData.name = paramFilmListItem.film_name;
    localMovieData.viewType = paramFilmListItem.viewType;
    startDetailPageActivity(localMovieData);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.BaseActivity
 * JD-Core Version:    0.6.2
 */