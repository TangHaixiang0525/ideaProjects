package u.aly;

import android.content.Context;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.h;
import com.umeng.analytics.h.a;
import com.umeng.analytics.h.b;
import java.io.File;

public class y
{
  private static final int a = 1;
  private static final int b = 2;
  private static final int c = 3;
  private d d;
  private f e;
  private final int f = 1;
  private Context g;
  private aa h;
  private t i;
  private bn j;
  private boolean k = false;
  private boolean l;

  public y(Context paramContext, aa paramaa)
  {
    this.d = d.a(paramContext);
    this.e = f.a(paramContext);
    this.g = paramContext;
    this.h = paramaa;
    this.i = new t(paramContext);
    this.i.a(this.h);
  }

  private int a(byte[] paramArrayOfByte)
  {
    bj localbj = new bj();
    ck localck = new ck(new cz.a());
    try
    {
      localck.a(localbj, paramArrayOfByte);
      if (localbj.a == 1)
      {
        this.e.b(localbj.j());
        this.e.c();
      }
      br.a("MobclickAgent", "send log:" + localbj.f());
      if (localbj.a == 1)
        return 2;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
    return 3;
  }

  private void b()
  {
    h.a(this.g).i().a(new h.b()
    {
      public void a(File paramAnonymousFile)
      {
      }

      // ERROR //
      public boolean b(File paramAnonymousFile)
      {
        // Byte code:
        //   0: new 25	java/io/FileInputStream
        //   3: dup
        //   4: aload_1
        //   5: invokespecial 27	java/io/FileInputStream:<init>	(Ljava/io/File;)V
        //   8: astore_2
        //   9: aload_2
        //   10: invokestatic 32	u/aly/cd:b	(Ljava/io/InputStream;)[B
        //   13: astore 5
        //   15: aload_2
        //   16: invokestatic 36	u/aly/cd:c	(Ljava/io/InputStream;)V
        //   19: aload_0
        //   20: getfield 17	u/aly/y$1:a	Lu/aly/y;
        //   23: invokestatic 39	u/aly/y:a	(Lu/aly/y;)Lu/aly/t;
        //   26: aload 5
        //   28: invokevirtual 44	u/aly/t:a	([B)[B
        //   31: astore 6
        //   33: aload 6
        //   35: ifnonnull +53 -> 88
        //   38: iconst_1
        //   39: istore 8
        //   41: iload 8
        //   43: iconst_2
        //   44: if_icmpne +26 -> 70
        //   47: aload_0
        //   48: getfield 17	u/aly/y$1:a	Lu/aly/y;
        //   51: invokestatic 47	u/aly/y:b	(Lu/aly/y;)Lu/aly/aa;
        //   54: invokevirtual 53	u/aly/aa:m	()Z
        //   57: ifeq +13 -> 70
        //   60: aload_0
        //   61: getfield 17	u/aly/y$1:a	Lu/aly/y;
        //   64: invokestatic 47	u/aly/y:b	(Lu/aly/y;)Lu/aly/aa;
        //   67: invokevirtual 56	u/aly/aa:l	()V
        //   70: aload_0
        //   71: getfield 17	u/aly/y$1:a	Lu/aly/y;
        //   74: invokestatic 59	u/aly/y:c	(Lu/aly/y;)Z
        //   77: ifeq +29 -> 106
        //   80: iconst_1
        //   81: ireturn
        //   82: aload_2
        //   83: invokestatic 36	u/aly/cd:c	(Ljava/io/InputStream;)V
        //   86: aload_3
        //   87: athrow
        //   88: aload_0
        //   89: getfield 17	u/aly/y$1:a	Lu/aly/y;
        //   92: aload 6
        //   94: invokestatic 62	u/aly/y:a	(Lu/aly/y;[B)I
        //   97: istore 7
        //   99: iload 7
        //   101: istore 8
        //   103: goto -62 -> 41
        //   106: iload 8
        //   108: iconst_1
        //   109: if_icmpne +9 -> 118
        //   112: iconst_0
        //   113: ireturn
        //   114: astore_3
        //   115: goto -33 -> 82
        //   118: iconst_1
        //   119: ireturn
        //   120: astore_3
        //   121: aconst_null
        //   122: astore_2
        //   123: goto -41 -> 82
        //   126: astore 4
        //   128: iconst_0
        //   129: ireturn
        //
        // Exception table:
        //   from	to	target	type
        //   9	15	114	finally
        //   0	9	120	finally
        //   15	33	126	java/lang/Exception
        //   47	70	126	java/lang/Exception
        //   70	80	126	java/lang/Exception
        //   82	88	126	java/lang/Exception
        //   88	99	126	java/lang/Exception
      }

      public void c(File paramAnonymousFile)
      {
        y.b(y.this).k();
      }
    });
  }

  private byte[] b(bn parambn)
  {
    if (parambn == null)
      return null;
    try
    {
      byte[] arrayOfByte = new cq().a(parambn);
      if (br.a)
        br.c("MobclickAgent", parambn.toString());
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      br.b("MobclickAgent", "Fail to serialize log ...", localException);
    }
    return null;
  }

  private void c()
  {
    this.d.a();
    bn localbn = this.j;
    localbn.a(this.d.b());
    byte[] arrayOfByte1 = b(localbn);
    if (arrayOfByte1 == null)
    {
      br.e("MobclickAgent", "message is null");
      return;
    }
    c localc;
    label66: byte[] arrayOfByte2;
    byte[] arrayOfByte3;
    if (!this.k)
    {
      localc = c.a(this.g, AnalyticsConfig.getAppkey(this.g), arrayOfByte1);
      arrayOfByte2 = localc.c();
      h.a(this.g).g();
      arrayOfByte3 = this.i.a(arrayOfByte2);
      if (arrayOfByte3 != null)
        break label180;
    }
    label180: for (int m = 1; ; m = a(arrayOfByte3))
      switch (m)
      {
      default:
        return;
      case 1:
        if (!this.l)
          h.a(this.g).b(arrayOfByte2);
        br.b("MobclickAgent", "connection error");
        return;
        localc = c.b(this.g, AnalyticsConfig.getAppkey(this.g), arrayOfByte1);
        break label66;
      case 2:
      case 3:
      }
    if (this.h.m())
      this.h.l();
    this.d.d();
    this.h.k();
    return;
    this.h.k();
  }

  public void a()
  {
    if (this.j != null)
    {
      c();
      return;
    }
    b();
  }

  public void a(bn parambn)
  {
    this.j = parambn;
  }

  public void a(w paramw)
  {
    this.e.a(paramw);
  }

  public void a(boolean paramBoolean)
  {
    this.k = paramBoolean;
  }

  public void b(boolean paramBoolean)
  {
    this.l = paramBoolean;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.y
 * JD-Core Version:    0.6.2
 */