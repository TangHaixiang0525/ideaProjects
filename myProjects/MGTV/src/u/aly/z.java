package u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.umeng.analytics.AnalyticsConfig;
import java.util.Arrays;
import java.util.List;

public class z
{
  private static final String a = "session_start_time";
  private static final String b = "session_end_time";
  private static final String c = "session_id";
  private static final String f = "activities";
  private static String g = null;
  private final String d = "a_start_time";
  private final String e = "a_end_time";

  private String a(Context paramContext, SharedPreferences paramSharedPreferences)
  {
    l locall = l.a(paramContext);
    String str = b(paramContext);
    aj localaj = a(paramContext);
    SharedPreferences.Editor localEditor = paramSharedPreferences.edit();
    localEditor.putString("session_id", str);
    localEditor.putLong("session_start_time", System.currentTimeMillis());
    localEditor.putLong("session_end_time", 0L);
    localEditor.putLong("a_start_time", System.currentTimeMillis());
    localEditor.putLong("a_end_time", 0L);
    localEditor.commit();
    if (localaj != null)
    {
      locall.a(localaj);
      return str;
    }
    locall.a((aj)null);
    return str;
  }

  private void a(SharedPreferences paramSharedPreferences)
  {
    SharedPreferences.Editor localEditor = paramSharedPreferences.edit();
    localEditor.remove("session_start_time");
    localEditor.remove("session_end_time");
    localEditor.remove("a_start_time");
    localEditor.remove("a_end_time");
    localEditor.putString("activities", "");
    localEditor.commit();
  }

  private boolean b(SharedPreferences paramSharedPreferences)
  {
    long l1 = paramSharedPreferences.getLong("a_start_time", 0L);
    long l2 = paramSharedPreferences.getLong("a_end_time", 0L);
    long l3 = System.currentTimeMillis();
    if ((l1 != 0L) && (l3 - l1 < AnalyticsConfig.kContinueSessionMillis))
      br.b("MobclickAgent", "onResume called before onPause");
    while (l3 - l2 <= AnalyticsConfig.kContinueSessionMillis)
      return false;
    return true;
  }

  public static String g(Context paramContext)
  {
    if (g == null)
      g = x.a(paramContext).getString("session_id", null);
    return g;
  }

  public aj a(Context paramContext)
  {
    SharedPreferences localSharedPreferences = x.a(paramContext);
    String str = localSharedPreferences.getString("session_id", null);
    if (str == null)
      return null;
    long l1 = localSharedPreferences.getLong("session_start_time", 0L);
    long l2 = localSharedPreferences.getLong("session_end_time", 0L);
    long l3 = 0L;
    if (l2 != 0L)
    {
      l3 = l2 - l1;
      if (Math.abs(l3) > 86400000L)
        l3 = 0L;
    }
    aj localaj = new aj();
    localaj.a(str);
    localaj.a(l1);
    localaj.b(l2);
    localaj.c(l3);
    double[] arrayOfDouble = AnalyticsConfig.getLocation();
    be localbe;
    if (arrayOfDouble != null)
    {
      localbe = new be(arrayOfDouble[0], arrayOfDouble[1], System.currentTimeMillis());
      if (!localaj.y())
        break label216;
      localaj.a(localbe);
    }
    while (true)
    {
      bm localbm = ac.a(paramContext);
      if (localbm != null)
        localaj.a(localbm);
      List localList = ad.a(localSharedPreferences);
      if ((localList != null) && (localList.size() > 0))
        localaj.a(localList);
      a(localSharedPreferences);
      return localaj;
      label216: localaj.b(Arrays.asList(new be[] { localbe }));
    }
  }

  public String b(Context paramContext)
  {
    String str1 = bq.f(paramContext);
    String str2 = AnalyticsConfig.getAppkey(paramContext);
    long l = System.currentTimeMillis();
    if (str2 == null)
      throw new RuntimeException("Appkey is null or empty, Please check AndroidManifest.xml");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(l).append(str2).append(str1);
    g = cd.a(localStringBuilder.toString());
    return g;
  }

  public void c(Context paramContext)
  {
    SharedPreferences localSharedPreferences = x.a(paramContext);
    if (localSharedPreferences == null)
      return;
    if (b(localSharedPreferences))
    {
      String str2 = a(paramContext, localSharedPreferences);
      br.a("MobclickAgent", "Start new session: " + str2);
      return;
    }
    String str1 = localSharedPreferences.getString("session_id", null);
    SharedPreferences.Editor localEditor = localSharedPreferences.edit();
    localEditor.putLong("a_start_time", System.currentTimeMillis());
    localEditor.putLong("a_end_time", 0L);
    localEditor.commit();
    br.a("MobclickAgent", "Extend current session: " + str1);
  }

  public void d(Context paramContext)
  {
    SharedPreferences localSharedPreferences = x.a(paramContext);
    if (localSharedPreferences == null)
      return;
    if ((localSharedPreferences.getLong("a_start_time", 0L) == 0L) && (AnalyticsConfig.ACTIVITY_DURATION_OPEN))
    {
      br.b("MobclickAgent", "onPause called before onResume");
      return;
    }
    long l = System.currentTimeMillis();
    SharedPreferences.Editor localEditor = localSharedPreferences.edit();
    localEditor.putLong("a_start_time", 0L);
    localEditor.putLong("a_end_time", l);
    localEditor.putLong("session_end_time", l);
    localEditor.commit();
  }

  public boolean e(Context paramContext)
  {
    SharedPreferences localSharedPreferences = x.a(paramContext);
    boolean bool1 = false;
    if (localSharedPreferences == null);
    l locall;
    aj localaj;
    do
    {
      String str;
      do
      {
        return bool1;
        str = localSharedPreferences.getString("session_id", null);
        bool1 = false;
      }
      while (str == null);
      long l1 = localSharedPreferences.getLong("a_start_time", 0L);
      long l2 = localSharedPreferences.getLong("a_end_time", 0L);
      boolean bool2 = l1 < 0L;
      bool1 = false;
      if (bool2)
      {
        boolean bool3 = l2 < 0L;
        bool1 = false;
        if (!bool3)
        {
          bool1 = true;
          d(paramContext);
        }
      }
      locall = l.a(paramContext);
      localaj = a(paramContext);
    }
    while (localaj == null);
    locall.b(localaj);
    return bool1;
  }

  public void f(Context paramContext)
  {
    SharedPreferences localSharedPreferences = x.a(paramContext);
    if (localSharedPreferences == null)
      return;
    String str = b(paramContext);
    SharedPreferences.Editor localEditor = localSharedPreferences.edit();
    localEditor.putString("session_id", str);
    localEditor.putLong("session_start_time", System.currentTimeMillis());
    localEditor.putLong("session_end_time", 0L);
    localEditor.putLong("a_start_time", System.currentTimeMillis());
    localEditor.putLong("a_end_time", 0L);
    localEditor.commit();
    br.a("MobclickAgent", "Restart session: " + str);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.z
 * JD-Core Version:    0.6.2
 */