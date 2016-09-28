package com.starcor.core.utils;

import android.app.Activity;
import android.app.Service;
import com.starcor.OTTTV;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.service.TempFileManager;
import com.starcor.hunan.XULActivity;
import com.starcor.message.MessageHandler;
import com.starcor.report.Column.Column;
import com.starcor.report.ReportInfo;
import com.starcor.report.ReportMessage;
import com.starcor.service.BroadCastDispather;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONObject;

public class AppKiller
{
  private static AppKiller INSTANCE;
  private static boolean isAppKilled = false;
  private List<Activity> activitys = new CopyOnWriteArrayList();
  private List<Service> services = new CopyOnWriteArrayList();

  public static AppKiller getInstance()
  {
    if (INSTANCE == null)
      INSTANCE = new AppKiller();
    return INSTANCE;
  }

  public static void reportExitApp()
  {
    ReportMessage localReportMessage = new ReportMessage();
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("act", "quit");
      localJSONObject.put("bid", "3.4.0");
      localReportMessage.mExtData = new Column().buildJsonData(localJSONObject);
      localReportMessage.setDesc("退出上报");
      MessageHandler.instance().doNotify(localReportMessage);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Logger.w("ReportService", "json exception!", localException);
    }
  }

  public void addActivity(Activity paramActivity)
  {
    if (!this.activitys.contains(paramActivity))
    {
      this.activitys.add(paramActivity);
      isAppKilled = false;
    }
    Logger.i("exit_test", "add after: " + paramActivity.getClass().getName());
  }

  public void addServices(Service paramService)
  {
    if (!this.services.contains(paramService))
      this.services.add(paramService);
  }

  public void delActivity(Activity paramActivity)
  {
    this.activitys.remove(paramActivity);
  }

  public void killApp()
  {
    if (isAppKilled)
      return;
    Logger.i("appkiller", "killApp");
    isAppKilled = true;
    ReportInfo.getInstance().clearReportInfo();
    reportExitApp();
    GlobalLogic.getInstance().setAppInterfaceReady(false);
    GlobalLogic.getInstance().setLiveChannelBulletScreenCount(0);
    GlobalLogic.getInstance().setVodBulletScreenCount(0);
    quitAllActivity();
    Iterator localIterator = this.services.iterator();
    while (localIterator.hasNext())
      ((Service)localIterator.next()).stopSelf();
    OTTTV.shutdown();
    BroadCastDispather.getInstance().destory();
    TempFileManager.getInstance().destory();
    INSTANCE = null;
  }

  public void killXULActivity()
  {
    Iterator localIterator = this.activitys.iterator();
    while (localIterator.hasNext())
    {
      Activity localActivity = (Activity)localIterator.next();
      if ((localActivity != null) && (!localActivity.isFinishing()) && ((localActivity instanceof XULActivity)))
      {
        Logger.i("exit_test", "acty name: " + localActivity.getClass().getName());
        if (GlobalLogic.getInstance().isUserCenterPage(localActivity))
          localActivity.finish();
      }
    }
  }

  public void quitAllActivity()
  {
    Iterator localIterator = this.activitys.iterator();
    while (localIterator.hasNext())
    {
      Activity localActivity = (Activity)localIterator.next();
      if ((localActivity != null) && (!localActivity.isFinishing()))
      {
        Logger.i("exit_test", "acty name: " + localActivity.getClass().getName());
        Logger.i("exit_test", "list size: " + this.activitys.size());
        localActivity.finish();
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.utils.AppKiller
 * JD-Core Version:    0.6.2
 */