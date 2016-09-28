package u.aly;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class a
{
  private final int a = 10;
  private final int b = 20;
  private final String c;
  private List<ax> d;
  private ay e;

  public a(String paramString)
  {
    this.c = paramString;
  }

  private boolean g()
  {
    ay localay = this.e;
    Object localObject;
    if (localay == null)
    {
      localObject = null;
      if (localay != null)
        break label205;
    }
    label205: for (int i = 0; ; i = localay.j())
    {
      String str = a(f());
      boolean bool1 = false;
      if (str != null)
      {
        boolean bool2 = str.equals(localObject);
        bool1 = false;
        if (!bool2)
        {
          if (localay == null)
            localay = new ay();
          localay.a(str);
          localay.a(System.currentTimeMillis());
          localay.a(i + 1);
          ax localax = new ax();
          localax.a(this.c);
          localax.c(str);
          localax.b((String)localObject);
          localax.a(localay.f());
          if (this.d == null)
            this.d = new ArrayList(2);
          this.d.add(localax);
          if (this.d.size() > 10)
            this.d.remove(0);
          this.e = localay;
          bool1 = true;
        }
      }
      return bool1;
      localObject = localay.c();
      break;
    }
  }

  public String a(String paramString)
  {
    if (paramString == null);
    String str;
    do
    {
      return null;
      str = paramString.trim();
    }
    while ((str.length() == 0) || ("0".equals(str)) || ("unknown".equals(str.toLowerCase(Locale.US))));
    return str;
  }

  public void a(List<ax> paramList)
  {
    this.d = paramList;
  }

  public void a(ay paramay)
  {
    this.e = paramay;
  }

  public void a(az paramaz)
  {
    this.e = ((ay)paramaz.d().get(this.c));
    List localList = paramaz.j();
    if ((localList != null) && (localList.size() > 0))
    {
      if (this.d == null)
        this.d = new ArrayList();
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        ax localax = (ax)localIterator.next();
        if (this.c.equals(localax.a))
          this.d.add(localax);
      }
    }
  }

  public boolean a()
  {
    return g();
  }

  public String b()
  {
    return this.c;
  }

  public boolean c()
  {
    return (this.e == null) || (this.e.j() <= 20);
  }

  public ay d()
  {
    return this.e;
  }

  public List<ax> e()
  {
    return this.d;
  }

  public abstract String f();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.a
 * JD-Core Version:    0.6.2
 */