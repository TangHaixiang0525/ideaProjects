package com.sohu.logger;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.google.gson.Gson;
import com.sohu.logger.bean.LogItem;
import com.sohu.logger.bean.UserBehaviorStatisticsItem;
import com.sohu.logger.common.AppConstants;
import com.sohu.logger.common.DeviceConstants;
import com.sohu.logger.file.FileUtil;
import com.sohu.logger.file.GetLogFromFile;
import com.sohu.logger.model.MyCrashHandler;
import com.sohu.logger.model.SohuPlayerLogger;
import com.sohu.logger.model.SohuPlayerLogger.ISohuPlayerLogger;
import com.sohu.logger.model.UserDataLogger;
import com.sohu.logger.util.CommonUtil;
import com.sohu.logger.util.NetUtils;

public class SohuLoggerAgent
{
  public static final String TAG = "SohuLoggerAgent";
  private static SohuLoggerAgent sohuAgentEntity = new SohuLoggerAgent();
  private int INTEVAL = 3600000;
  private String duration = null;
  private long end = 0L;
  private String end_millis = null;
  private Handler handler;
  private Handler mHandler;
  private SohuPlayerLogger playerLogger;
  private long start = 0L;
  private String start_millis = null;

  private SohuLoggerAgent()
  {
    HandlerThread localHandlerThread1 = new HandlerThread("UmsAgent");
    localHandlerThread1.start();
    this.handler = new Handler(localHandlerThread1.getLooper());
    HandlerThread localHandlerThread2 = new HandlerThread("UmsAgentFile");
    localHandlerThread2.start();
    this.mHandler = new Handler(localHandlerThread2.getLooper());
  }

  private String convertToJson(LogItem paramLogItem)
  {
    return new Gson().toJson(paramLogItem);
  }

  public static SohuLoggerAgent getInstance()
  {
    return sohuAgentEntity;
  }

  private void postErrorInfo(Context paramContext, String paramString)
  {
  }

  private boolean postEventInfo(Context paramContext, LogItem paramLogItem, String paramString1, String paramString2)
  {
    if ((paramLogItem.needSendRealtime()) && (CommonUtil.isNetworkAvailable(paramContext)));
    try
    {
      if ((!NetUtils.Post(paramContext, paramLogItem.getRetryNum(), paramString2)) && (paramLogItem.isStored()))
      {
        FileUtil.saveInfoToFile(paramContext, this.handler, paramString1);
        return false;
        if (paramLogItem.isStored())
        {
          FileUtil.saveInfoToFile(paramContext, this.handler, paramString1);
          return false;
        }
      }
    }
    catch (Exception localException)
    {
    }
    return true;
  }

  private void sendLog(final Context paramContext, final LogItem paramLogItem, final String paramString1, final String paramString2)
  {
    Runnable local4 = new Runnable()
    {
      public void run()
      {
        SohuLoggerAgent.this.postEventInfo(paramContext, paramLogItem, paramString1, paramString2);
      }
    };
    this.handler.post(local4);
  }

  public void bitrateChange()
  {
    if (this.playerLogger != null)
      this.playerLogger.bitrateChange();
  }

  public void exit()
  {
    UserDataLogger.getInstance().setExit(true);
  }

  public void init(final Context paramContext, int paramInt1, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, int paramInt2)
  {
    if ((AppConstants.getInstance().getmProjectType() != 0) && (paramInt1 == 0))
      return;
    AppConstants.getInstance().setmProjectType(paramInt1);
    AppConstants.getInstance().setmProType(paramString6);
    AppConstants.getInstance().setmApkType(paramString7);
    AppConstants.getInstance().setPoid(paramString1);
    AppConstants.getInstance().setClientType(paramString2);
    AppConstants.getInstance().setPlatform(paramString3);
    AppConstants.getInstance().setPartnerNo(paramString4);
    AppConstants.getInstance().setSver(paramString5);
    UserDataLogger.getInstance().initInstance(paramContext.getApplicationContext());
    DeviceConstants.initInstance(paramContext);
    this.INTEVAL = (paramInt2 * 1000);
    Thread localThread = new Thread(new Runnable()
    {
      public void run()
      {
        new GetLogFromFile(paramContext).sendLog();
        SohuLoggerAgent.this.mHandler.postDelayed(this, SohuLoggerAgent.this.INTEVAL);
      }
    });
    this.mHandler.removeCallbacks(null);
    this.mHandler.post(localThread);
  }

  public void onAppStart(Context paramContext)
  {
    UserDataLogger.getInstance().resetForegroundActivityCountWhenNeed();
    UserDataLogger.getInstance().sendAppStartLog();
  }

  public void onError(final Context paramContext)
  {
    Thread localThread = new Thread(new Runnable()
    {
      public void run()
      {
        MyCrashHandler localMyCrashHandler = MyCrashHandler.getInstance();
        localMyCrashHandler.init(paramContext.getApplicationContext());
        Thread.setDefaultUncaughtExceptionHandler(localMyCrashHandler);
      }
    });
    this.handler.post(localThread);
  }

  public void onError(final Context paramContext, final String paramString)
  {
    Runnable local3 = new Runnable()
    {
      public void run()
      {
        SohuLoggerAgent.this.postErrorInfo(paramContext, paramString);
      }
    };
    this.handler.post(local3);
  }

  public void onEvent(Context paramContext, LogItem paramLogItem)
  {
    if (paramLogItem == null)
      return;
    paramLogItem.fillGlobleAppParams();
    paramLogItem.fillRealTimeRarams();
    paramLogItem.fillParams();
    sendLog(paramContext, paramLogItem, convertToJson(paramLogItem), paramLogItem.toUrl());
  }

  public void onLogComplete()
  {
    if (this.playerLogger != null)
    {
      this.playerLogger.complete();
      this.playerLogger = null;
    }
  }

  public void onLogPause()
  {
    if (this.playerLogger != null)
      this.playerLogger.pause();
  }

  public void onLogSetUrl(Context paramContext, boolean paramBoolean, SohuPlayerLogger.ISohuPlayerLogger paramISohuPlayerLogger)
  {
    if (this.playerLogger != null)
    {
      this.playerLogger.reset();
      this.playerLogger = null;
    }
    this.playerLogger = new SohuPlayerLogger(paramContext, paramBoolean, paramISohuPlayerLogger);
    this.playerLogger.setUrl();
  }

  public void onLogStart()
  {
    if (this.playerLogger != null)
      this.playerLogger.start();
  }

  public void onLogStop()
  {
    if ((this.playerLogger != null) && (this.playerLogger.stop()))
      this.playerLogger = null;
  }

  public void onPause(Activity paramActivity)
  {
    UserDataLogger.getInstance().onActivityStateChanged(paramActivity, false);
    this.end_millis = CommonUtil.getTime();
    this.end = Long.valueOf(System.currentTimeMillis()).longValue();
    this.duration = (this.end - this.start + "");
  }

  public void onResume(Activity paramActivity)
  {
    UserDataLogger.getInstance().setExit(false);
    UserDataLogger.getInstance().onActivityStateChanged(paramActivity, true);
    this.start_millis = CommonUtil.getTime();
    this.start = Long.valueOf(System.currentTimeMillis()).longValue();
  }

  public void onUserBehavior(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    UserBehaviorStatisticsItem localUserBehaviorStatisticsItem = new UserBehaviorStatisticsItem();
    localUserBehaviorStatisticsItem.setParamsMapItem("type", paramString1);
    localUserBehaviorStatisticsItem.setParamsMapItem("stype", paramString2);
    localUserBehaviorStatisticsItem.setParamsMapItem("expand1", paramString3);
    localUserBehaviorStatisticsItem.setParamsMapItem("expand2", paramString4);
    localUserBehaviorStatisticsItem.setParamsMapItem("expand3", paramString5);
    localUserBehaviorStatisticsItem.setParamsMapItem("reserve1", paramString6);
    localUserBehaviorStatisticsItem.setParamsMapItem("reserve2", paramString7);
    localUserBehaviorStatisticsItem.setParamsMapItem("source_path", paramString8);
    onEvent(paramContext, localUserBehaviorStatisticsItem);
  }

  public void postEventInfo(final Context paramContext, final String paramString)
  {
    Runnable local5 = new Runnable()
    {
      // ERROR //
      public void run()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 23	com/sohu/logger/SohuLoggerAgent$5:val$context	Landroid/content/Context;
        //   4: invokevirtual 39	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
        //   7: astore_1
        //   8: aload_1
        //   9: aload_0
        //   10: getfield 25	com/sohu/logger/SohuLoggerAgent$5:val$apk_pg_name	Ljava/lang/String;
        //   13: iconst_0
        //   14: invokevirtual 45	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
        //   17: astore_3
        //   18: aload_3
        //   19: getfield 51	android/content/pm/PackageInfo:applicationInfo	Landroid/content/pm/ApplicationInfo;
        //   22: aload_1
        //   23: invokevirtual 57	android/content/pm/ApplicationInfo:loadLabel	(Landroid/content/pm/PackageManager;)Ljava/lang/CharSequence;
        //   26: invokeinterface 63 1 0
        //   31: astore 4
        //   33: aload_3
        //   34: getfield 67	android/content/pm/PackageInfo:versionCode	I
        //   37: invokestatic 73	java/lang/String:valueOf	(I)Ljava/lang/String;
        //   40: astore 5
        //   42: new 75	com/sohu/logger/bean/AppItem
        //   45: dup
        //   46: invokespecial 76	com/sohu/logger/bean/AppItem:<init>	()V
        //   49: astore 6
        //   51: aload 6
        //   53: aload_0
        //   54: getfield 23	com/sohu/logger/SohuLoggerAgent$5:val$context	Landroid/content/Context;
        //   57: invokestatic 82	com/sohu/logger/common/DeviceConstants:getUid	(Landroid/content/Context;)Ljava/lang/String;
        //   60: invokevirtual 86	com/sohu/logger/bean/AppItem:setUid	(Ljava/lang/String;)V
        //   63: aload 6
        //   65: invokestatic 92	com/sohu/logger/common/AppConstants:getInstance	()Lcom/sohu/logger/common/AppConstants;
        //   68: invokevirtual 95	com/sohu/logger/common/AppConstants:getPoid	()Ljava/lang/String;
        //   71: invokevirtual 98	com/sohu/logger/bean/AppItem:setPro	(Ljava/lang/String;)V
        //   74: aload 6
        //   76: invokestatic 92	com/sohu/logger/common/AppConstants:getInstance	()Lcom/sohu/logger/common/AppConstants;
        //   79: invokevirtual 101	com/sohu/logger/common/AppConstants:getmProType	()Ljava/lang/String;
        //   82: invokevirtual 104	com/sohu/logger/bean/AppItem:setPro_type	(Ljava/lang/String;)V
        //   85: aload 6
        //   87: invokestatic 92	com/sohu/logger/common/AppConstants:getInstance	()Lcom/sohu/logger/common/AppConstants;
        //   90: invokevirtual 107	com/sohu/logger/common/AppConstants:getPartnerNo	()Ljava/lang/String;
        //   93: invokevirtual 110	com/sohu/logger/bean/AppItem:setChannel_id	(Ljava/lang/String;)V
        //   96: aload 6
        //   98: invokestatic 92	com/sohu/logger/common/AppConstants:getInstance	()Lcom/sohu/logger/common/AppConstants;
        //   101: invokevirtual 113	com/sohu/logger/common/AppConstants:getPlatform	()Ljava/lang/String;
        //   104: invokevirtual 116	com/sohu/logger/bean/AppItem:setPlatform	(Ljava/lang/String;)V
        //   107: aload 6
        //   109: ldc 118
        //   111: invokevirtual 121	com/sohu/logger/bean/AppItem:setPro_form	(Ljava/lang/String;)V
        //   114: aload 6
        //   116: invokestatic 92	com/sohu/logger/common/AppConstants:getInstance	()Lcom/sohu/logger/common/AppConstants;
        //   119: invokevirtual 124	com/sohu/logger/common/AppConstants:getmApkType	()Ljava/lang/String;
        //   122: invokevirtual 127	com/sohu/logger/bean/AppItem:setApk_type	(Ljava/lang/String;)V
        //   125: new 129	java/util/ArrayList
        //   128: dup
        //   129: invokespecial 130	java/util/ArrayList:<init>	()V
        //   132: astore 7
        //   134: new 132	com/sohu/logger/bean/AppInfo
        //   137: dup
        //   138: invokespecial 133	com/sohu/logger/bean/AppInfo:<init>	()V
        //   141: astore 8
        //   143: aload 8
        //   145: invokestatic 139	java/lang/System:currentTimeMillis	()J
        //   148: invokestatic 144	java/lang/Long:toString	(J)Ljava/lang/String;
        //   151: invokevirtual 147	com/sohu/logger/bean/AppInfo:setTs	(Ljava/lang/String;)V
        //   154: aload 8
        //   156: ldc 149
        //   158: invokevirtual 152	com/sohu/logger/bean/AppInfo:setIsonline	(Ljava/lang/String;)V
        //   161: aload 8
        //   163: invokestatic 92	com/sohu/logger/common/AppConstants:getInstance	()Lcom/sohu/logger/common/AppConstants;
        //   166: invokevirtual 155	com/sohu/logger/common/AppConstants:getSver	()Ljava/lang/String;
        //   169: invokevirtual 158	com/sohu/logger/bean/AppInfo:setCv	(Ljava/lang/String;)V
        //   172: aload 8
        //   174: invokestatic 163	com/sohu/logger/model/UserDataLogger:getInstance	()Lcom/sohu/logger/model/UserDataLogger;
        //   177: invokevirtual 166	com/sohu/logger/model/UserDataLogger:getStartId	()Ljava/lang/String;
        //   180: invokevirtual 169	com/sohu/logger/bean/AppInfo:setStartid	(Ljava/lang/String;)V
        //   183: aload 8
        //   185: invokestatic 174	com/sohu/logger/util/LoggerUtil:getNetworkType	()Ljava/lang/String;
        //   188: invokevirtual 177	com/sohu/logger/bean/AppInfo:setWebtype	(Ljava/lang/String;)V
        //   191: aload 8
        //   193: ldc 179
        //   195: invokevirtual 182	com/sohu/logger/bean/AppInfo:setUrl	(Ljava/lang/String;)V
        //   198: aload 8
        //   200: aload 4
        //   202: invokevirtual 185	com/sohu/logger/bean/AppInfo:setApk_name	(Ljava/lang/String;)V
        //   205: aload 8
        //   207: aload_0
        //   208: getfield 25	com/sohu/logger/SohuLoggerAgent$5:val$apk_pg_name	Ljava/lang/String;
        //   211: invokevirtual 188	com/sohu/logger/bean/AppInfo:setApk_pg_name	(Ljava/lang/String;)V
        //   214: aload 8
        //   216: aload 5
        //   218: invokevirtual 191	com/sohu/logger/bean/AppInfo:setApk_cv	(Ljava/lang/String;)V
        //   221: aload 7
        //   223: aload 8
        //   225: invokeinterface 197 2 0
        //   230: pop
        //   231: aload 6
        //   233: aload 7
        //   235: invokevirtual 201	com/sohu/logger/bean/AppItem:setMcclist	(Ljava/util/List;)V
        //   238: new 203	com/google/gson/Gson
        //   241: dup
        //   242: invokespecial 204	com/google/gson/Gson:<init>	()V
        //   245: astore 10
        //   247: invokestatic 207	com/sohu/logger/util/LoggerUtil:getFoxPadAPPURL	()Ljava/lang/String;
        //   250: aload 10
        //   252: aload 6
        //   254: invokevirtual 211	com/google/gson/Gson:toJson	(Ljava/lang/Object;)Ljava/lang/String;
        //   257: invokestatic 217	com/sohu/logger/util/LoggerHttpUtils:sendPost2	(Ljava/lang/String;Ljava/lang/String;)Z
        //   260: pop
        //   261: return
        //   262: astore_2
        //   263: aload_2
        //   264: invokevirtual 220	java/lang/Exception:printStackTrace	()V
        //   267: return
        //   268: astore 11
        //   270: aload 11
        //   272: invokevirtual 221	java/io/IOException:printStackTrace	()V
        //   275: return
        //
        // Exception table:
        //   from	to	target	type
        //   8	42	262	java/lang/Exception
        //   247	261	268	java/io/IOException
      }
    };
    this.handler.post(local5);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.SohuLoggerAgent
 * JD-Core Version:    0.6.2
 */