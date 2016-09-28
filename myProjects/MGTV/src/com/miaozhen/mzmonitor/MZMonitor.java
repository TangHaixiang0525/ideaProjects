package com.miaozhen.mzmonitor;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import java.util.Iterator;
import java.util.List;

public class MZMonitor
{
  private static Handler handler = null;
  private static long nextRetryTime;
  private static HandlerThread thread = null;

  public static void adTrack(Context paramContext, String paramString)
  {
    sendRequest(paramContext, new a(paramString));
  }

  public static void adTrackWithIESId(Context paramContext, String paramString1, String paramString2)
  {
    a locala = new a(paramString1);
    locala.f(paramString2);
    sendRequest(paramContext, locala);
  }

  public static void adTrackWithUserId(Context paramContext, String paramString1, String paramString2)
  {
    a locala = new a(paramString1);
    locala.c(paramString2);
    sendRequest(paramContext, locala);
  }

  public static void eventTrack(Context paramContext, String paramString1, String paramString2)
  {
    a locala = new a(paramString1);
    locala.d(paramString2);
    sendRequest(paramContext, locala);
  }

  @Deprecated
  public static boolean isMZURL(String paramString)
  {
    return true;
  }

  public static void panelTrack(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    a locala = new a(paramString1);
    locala.b(paramString2);
    locala.c(paramString3);
    sendRequest(paramContext, locala);
  }

  @Deprecated
  public static void reSendReport(Context paramContext)
  {
    retryCachedRequests(paramContext);
  }

  @Deprecated
  public static void reportAction(Context paramContext, String paramString)
  {
    adTrack(paramContext, paramString);
  }

  @Deprecated
  public static void reportAction(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    panelTrack(paramContext, paramString1, paramString2, paramString3);
  }

  public static void retryCachedRequests(Context paramContext)
  {
    try
    {
      long l1 = MZUtility.currentUnixTimestamp();
      if (MZDeviceInfo.getDeviceInfo(paramContext).getCurrentNetworkType() != "0")
      {
        if (nextRetryTime == 0L)
          break label43;
        long l2 = nextRetryTime;
        if (l1 >= l2)
          break label43;
      }
      while (true)
      {
        return;
        label43: List localList = b.a(paramContext).d();
        if ((localList != null) && (localList.size() != 0))
        {
          nextRetryTime = l1 + 10000 * localList.size() / 1000;
          Iterator localIterator = localList.iterator();
          while (localIterator.hasNext())
            sendRequest(paramContext, (a)localIterator.next());
        }
      }
    }
    finally
    {
    }
  }

  private static void sendRequest(Context paramContext, a parama)
  {
    startMonitor();
    n localn = new n(paramContext, parama);
    handler.post(localn);
  }

  @Deprecated
  public static void setCustomProfile(Context paramContext, String paramString)
  {
    if (isMZURL(paramString))
      MZSdkProfile.setCustomProfile(paramContext, paramString);
  }

  private static void startMonitor()
  {
    try
    {
      if (thread == null)
      {
        thread = new HandlerThread("MZMonitor");
        thread.start();
        handler = new Handler(thread.getLooper());
      }
      return;
    }
    finally
    {
    }
  }

  public static String version()
  {
    return "a3.0";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.MZMonitor
 * JD-Core Version:    0.6.2
 */