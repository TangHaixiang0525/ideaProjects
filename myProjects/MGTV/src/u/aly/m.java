package u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class m
{
  private final String a = "umeng_event_snapshot";
  private boolean b = false;
  private SharedPreferences c;
  private Map<String, ArrayList<ae>> d = new HashMap();

  public m(Context paramContext)
  {
    this.c = x.a(paramContext, "umeng_event_snapshot");
  }

  private void c(String paramString)
  {
    boolean bool = this.d.containsKey(paramString);
    String str = null;
    if (bool)
    {
      ArrayList localArrayList = (ArrayList)this.d.get(paramString);
      while (localArrayList.size() > 4)
        localArrayList.remove(0);
      str = u.a(localArrayList);
    }
    this.c.edit().putString(paramString, str).commit();
  }

  private boolean d(String paramString)
  {
    if (this.d.containsKey(paramString))
      return true;
    String str = this.c.getString(paramString, null);
    if (str != null)
    {
      ArrayList localArrayList = (ArrayList)u.a(str);
      if (localArrayList != null)
      {
        this.d.put(paramString, localArrayList);
        return true;
      }
    }
    return false;
  }

  public int a(String paramString)
  {
    if (this.d.containsKey(paramString))
      return ((ArrayList)this.d.get(paramString)).size();
    return 0;
  }

  public void a(String paramString, ae paramae)
  {
    if (this.b)
      d(paramString);
    if (this.d.containsKey(paramString))
      ((ArrayList)this.d.get(paramString)).add(paramae);
    while (true)
    {
      if (this.b)
        c(paramString);
      return;
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(paramae);
      this.d.put(paramString, localArrayList);
    }
  }

  public void a(boolean paramBoolean)
  {
    this.b = paramBoolean;
  }

  public ae b(String paramString)
  {
    if (this.b)
      d(paramString);
    ArrayList localArrayList;
    if (this.d.containsKey(paramString))
    {
      localArrayList = (ArrayList)this.d.get(paramString);
      if (localArrayList.size() <= 0);
    }
    for (ae localae = (ae)localArrayList.remove(-1 + localArrayList.size()); ; localae = null)
    {
      if (this.b)
        c(paramString);
      return localae;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.m
 * JD-Core Version:    0.6.2
 */