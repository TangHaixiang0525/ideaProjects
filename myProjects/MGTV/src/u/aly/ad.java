package u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ad
{
  private static final String a = "activities";
  private final Map<String, Long> b = new HashMap();
  private final ArrayList<ab> c = new ArrayList();

  public static List<bg> a(SharedPreferences paramSharedPreferences)
  {
    String str1 = paramSharedPreferences.getString("activities", "");
    if ("".equals(str1))
      return null;
    ArrayList localArrayList = new ArrayList();
    while (true)
    {
      int i;
      try
      {
        String[] arrayOfString = str1.split(";");
        i = 0;
        if (i < arrayOfString.length)
        {
          String str2 = arrayOfString[i];
          if (TextUtils.isEmpty(str2))
            break label98;
          localArrayList.add(new ai(str2));
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      if (localArrayList.size() <= 0)
        break;
      return localArrayList;
      label98: i++;
    }
  }

  public void a()
  {
    Object localObject1 = null;
    long l2;
    for (long l1 = 0L; ; l1 = l2)
    {
      synchronized (this.b)
      {
        Iterator localIterator = this.b.entrySet().iterator();
        if (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          if (((Long)localEntry.getValue()).longValue() > l1)
          {
            long l3 = ((Long)localEntry.getValue()).longValue();
            localObject3 = (String)localEntry.getKey();
            l2 = l3;
            break label130;
          }
        }
        else
        {
          if (localObject1 != null)
            b((String)localObject1);
          return;
        }
      }
      l2 = l1;
      Object localObject3 = localObject1;
      label130: localObject1 = localObject3;
    }
  }

  public void a(Context paramContext)
  {
    SharedPreferences localSharedPreferences = x.a(paramContext);
    SharedPreferences.Editor localEditor = localSharedPreferences.edit();
    if (this.c.size() > 0)
    {
      String str = localSharedPreferences.getString("activities", "");
      StringBuilder localStringBuilder = new StringBuilder();
      if (!TextUtils.isEmpty(str))
      {
        localStringBuilder.append(str);
        localStringBuilder.append(";");
      }
      synchronized (this.c)
      {
        Iterator localIterator = this.c.iterator();
        if (localIterator.hasNext())
        {
          ab localab = (ab)localIterator.next();
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = localab.a;
          arrayOfObject[1] = Long.valueOf(localab.b);
          localStringBuilder.append(String.format("[\"%s\",%d]", arrayOfObject));
          localStringBuilder.append(";");
        }
      }
      this.c.clear();
      localStringBuilder.deleteCharAt(-1 + localStringBuilder.length());
      localEditor.remove("activities");
      localEditor.putString("activities", localStringBuilder.toString());
    }
    localEditor.commit();
  }

  public void a(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
      synchronized (this.b)
      {
        this.b.put(paramString, Long.valueOf(System.currentTimeMillis()));
        return;
      }
  }

  public void b(String paramString)
  {
    if (!TextUtils.isEmpty(paramString));
    Long localLong;
    synchronized (this.b)
    {
      localLong = (Long)this.b.remove(paramString);
      if (localLong == null)
      {
        br.e("MobclickAgent", String.format("please call 'onPageStart(%s)' before onPageEnd", new Object[] { paramString }));
        return;
      }
    }
    long l = System.currentTimeMillis() - localLong.longValue();
    synchronized (this.c)
    {
      this.c.add(new ab(paramString, l));
      return;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.ad
 * JD-Core Version:    0.6.2
 */