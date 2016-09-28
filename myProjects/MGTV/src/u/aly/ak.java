package u.aly;

import android.content.Context;
import java.util.Arrays;

public class ak
{
  private static final int a = 0;
  private static final int b = 1;
  private static final int c = 2;
  private static final int d = 3;
  private static final long e = 14400000L;
  private static final long f = 28800000L;
  private static final long g = 86400000L;
  private int h = 0;
  private final long i = 60000L;

  public long a()
  {
    switch (this.h)
    {
    default:
      return 0L;
    case 1:
      return 14400000L;
    case 2:
      return 28800000L;
    case 3:
    }
    return 86400000L;
  }

  public bl a(Context paramContext)
  {
    long l = System.currentTimeMillis();
    bl localbl = new bl();
    localbl.a(z.g(paramContext));
    localbl.a(l);
    localbl.b(l + 60000L);
    localbl.c(60000L);
    return localbl;
  }

  public bn a(Context paramContext, bn parambn)
  {
    if (parambn == null)
      parambn = null;
    do
    {
      return parambn;
      if (this.h == 1)
      {
        parambn.a(null);
        return parambn;
      }
      if (this.h == 2)
      {
        bl[] arrayOfbl = new bl[1];
        arrayOfbl[0] = a(paramContext);
        parambn.b(Arrays.asList(arrayOfbl));
        parambn.a(null);
        return parambn;
      }
    }
    while (this.h != 3);
    parambn.b(null);
    parambn.a(null);
    return parambn;
  }

  public void a(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt <= 3))
      this.h = paramInt;
  }

  public long b()
  {
    if (this.h == 0)
      return 0L;
    return 300000L;
  }

  public boolean c()
  {
    return this.h != 0;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.ak
 * JD-Core Version:    0.6.2
 */