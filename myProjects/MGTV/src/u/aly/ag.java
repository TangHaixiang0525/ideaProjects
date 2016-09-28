package u.aly;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

public class ag extends at
  implements q
{
  public ag()
  {
    a(System.currentTimeMillis());
    a(au.a);
  }

  public ag(String paramString)
  {
    this();
    a(paramString);
  }

  public ag(Throwable paramThrowable)
  {
    this();
    a(a(paramThrowable));
  }

  private String a(Throwable paramThrowable)
  {
    String str = null;
    if (paramThrowable == null)
      return null;
    try
    {
      StringWriter localStringWriter = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
      paramThrowable.printStackTrace(localPrintWriter);
      for (Throwable localThrowable = paramThrowable.getCause(); ; localThrowable = localThrowable.getCause())
      {
        str = null;
        if (localThrowable == null)
          break;
        localThrowable.printStackTrace(localPrintWriter);
      }
      str = localStringWriter.toString();
      localPrintWriter.close();
      localStringWriter.close();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return str;
  }

  public ag a(boolean paramBoolean)
  {
    if (paramBoolean);
    for (au localau = au.a; ; localau = au.b)
    {
      a(localau);
      return this;
    }
  }

  public void a(bn parambn, String paramString)
  {
    bc localbc;
    if (parambn.s() > 0)
    {
      Iterator localIterator = parambn.u().iterator();
      do
      {
        if (!localIterator.hasNext())
          break;
        localbc = (bc)localIterator.next();
      }
      while (!paramString.equals(localbc.c()));
    }
    while (true)
    {
      if (localbc == null)
      {
        localbc = new bc();
        localbc.a(paramString);
        parambn.a(localbc);
      }
      localbc.a(this);
      return;
      localbc = null;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.ag
 * JD-Core Version:    0.6.2
 */