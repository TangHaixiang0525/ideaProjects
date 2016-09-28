package u.aly;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.b;
import com.umeng.analytics.h;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class f
{
  private static final String a = ".imprint";
  private static final byte[] b = "pbl0".getBytes();
  private static f e;
  private w c;
  private ba d = null;
  private Context f;

  f(Context paramContext)
  {
    this.f = paramContext;
  }

  private int a(String paramString)
  {
    ba localba = this.d;
    if ((localba == null) || (!localba.f()))
      return -1;
    bb localbb = (bb)localba.d().get(paramString);
    if ((localbb == null) || (TextUtils.isEmpty(localbb.c())))
      return -1;
    try
    {
      int i = Integer.parseInt(localbb.c().trim());
      return i;
    }
    catch (Exception localException)
    {
    }
    return -1;
  }

  private ba a(ba paramba1, ba paramba2)
  {
    if (paramba2 == null)
      return paramba1;
    Map localMap = paramba1.d();
    Iterator localIterator = paramba2.d().entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (((bb)localEntry.getValue()).e())
        localMap.put(localEntry.getKey(), localEntry.getValue());
      else
        localMap.remove(localEntry.getKey());
    }
    paramba1.a(paramba2.h());
    paramba1.a(a(paramba1));
    return paramba1;
  }

  public static f a(Context paramContext)
  {
    try
    {
      if (e == null)
      {
        e = new f(paramContext);
        e.b();
      }
      f localf = e;
      return localf;
    }
    finally
    {
    }
  }

  private boolean c(ba paramba)
  {
    if (!paramba.k().equals(a(paramba)))
      return false;
    Iterator localIterator = paramba.d().values().iterator();
    while (localIterator.hasNext())
    {
      bb localbb = (bb)localIterator.next();
      byte[] arrayOfByte1 = b.a(localbb.j());
      byte[] arrayOfByte2 = a(localbb);
      for (int i = 0; i < 4; i++)
        if (arrayOfByte1[i] != arrayOfByte2[i])
          return false;
    }
    return true;
  }

  private void e()
  {
    if (this.c == null);
    int k;
    do
    {
      return;
      int i = a("defcon");
      if (i != -1)
      {
        this.c.a(i);
        h.a(this.f).a(i);
      }
      int j = a("latent");
      if (j != -1)
      {
        int m = j * 1000;
        this.c.b(m);
        h.a(this.f).b(m);
      }
      k = a("codex");
    }
    while ((k != 0) && (k != 1) && (k != -1));
    this.c.c(k);
    h.a(this.f).c(k);
  }

  public String a(ba paramba)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = new TreeMap(paramba.d()).entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localStringBuilder.append((String)localEntry.getKey());
      localStringBuilder.append(((bb)localEntry.getValue()).c());
      localStringBuilder.append(((bb)localEntry.getValue()).f());
      localStringBuilder.append(((bb)localEntry.getValue()).j());
    }
    localStringBuilder.append(paramba.b);
    return cd.a(localStringBuilder.toString()).toLowerCase(Locale.US);
  }

  public ba a()
  {
    try
    {
      ba localba = this.d;
      return localba;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void a(w paramw)
  {
    this.c = paramw;
  }

  public byte[] a(bb parambb)
  {
    ByteBuffer localByteBuffer = ByteBuffer.allocate(8);
    localByteBuffer.order(null);
    localByteBuffer.putLong(parambb.f());
    byte[] arrayOfByte1 = localByteBuffer.array();
    byte[] arrayOfByte2 = b;
    byte[] arrayOfByte3 = new byte[4];
    for (int i = 0; i < 4; i++)
      arrayOfByte3[i] = ((byte)(arrayOfByte1[i] ^ arrayOfByte2[i]));
    return arrayOfByte3;
  }

  // ERROR //
  public void b()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 247	java/io/File
    //   5: dup
    //   6: aload_0
    //   7: getfield 38	u/aly/f:f	Landroid/content/Context;
    //   10: invokevirtual 253	android/content/Context:getFilesDir	()Ljava/io/File;
    //   13: ldc 8
    //   15: invokespecial 256	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   18: invokevirtual 259	java/io/File:exists	()Z
    //   21: ifne +4 -> 25
    //   24: return
    //   25: aload_0
    //   26: getfield 38	u/aly/f:f	Landroid/content/Context;
    //   29: ldc 8
    //   31: invokevirtual 263	android/content/Context:openFileInput	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   34: astore 8
    //   36: aload 8
    //   38: astore 4
    //   40: aload 4
    //   42: invokestatic 266	u/aly/cd:b	(Ljava/io/InputStream;)[B
    //   45: astore 9
    //   47: aload 9
    //   49: astore 5
    //   51: aload 4
    //   53: invokestatic 269	u/aly/cd:c	(Ljava/io/InputStream;)V
    //   56: aload 5
    //   58: ifnull -34 -> 24
    //   61: new 43	u/aly/ba
    //   64: dup
    //   65: invokespecial 270	u/aly/ba:<init>	()V
    //   68: astore 6
    //   70: new 272	u/aly/ck
    //   73: dup
    //   74: invokespecial 273	u/aly/ck:<init>	()V
    //   77: aload 6
    //   79: aload 5
    //   81: invokevirtual 276	u/aly/ck:a	(Lu/aly/ch;[B)V
    //   84: aload_0
    //   85: aload 6
    //   87: putfield 36	u/aly/f:d	Lu/aly/ba;
    //   90: return
    //   91: astore 7
    //   93: aload 7
    //   95: invokevirtual 279	java/lang/Exception:printStackTrace	()V
    //   98: return
    //   99: astore_3
    //   100: aconst_null
    //   101: astore 4
    //   103: aload_3
    //   104: invokevirtual 279	java/lang/Exception:printStackTrace	()V
    //   107: aload 4
    //   109: invokestatic 269	u/aly/cd:c	(Ljava/io/InputStream;)V
    //   112: aconst_null
    //   113: astore 5
    //   115: goto -59 -> 56
    //   118: astore_2
    //   119: aload_1
    //   120: invokestatic 269	u/aly/cd:c	(Ljava/io/InputStream;)V
    //   123: aload_2
    //   124: athrow
    //   125: astore_2
    //   126: aload 4
    //   128: astore_1
    //   129: goto -10 -> 119
    //   132: astore_3
    //   133: goto -30 -> 103
    //
    // Exception table:
    //   from	to	target	type
    //   61	90	91	java/lang/Exception
    //   25	36	99	java/lang/Exception
    //   25	36	118	finally
    //   40	47	125	finally
    //   103	107	125	finally
    //   40	47	132	java/lang/Exception
  }

  public void b(ba paramba)
  {
    if (paramba == null);
    while (true)
    {
      return;
      if (!c(paramba))
        continue;
      try
      {
        ba localba = this.d;
        if (localba == null);
        while (true)
        {
          this.d = paramba;
          if (this.d == null)
            break;
          e();
          return;
          paramba = a(localba, paramba);
        }
      }
      finally
      {
      }
    }
  }

  public void c()
  {
    if (this.d == null)
      return;
    try
    {
      byte[] arrayOfByte = new cq().a(this.d);
      cd.a(new File(this.f.getFilesDir(), ".imprint"), arrayOfByte);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public boolean d()
  {
    return new File(this.f.getFilesDir(), ".imprint").delete();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.f
 * JD-Core Version:    0.6.2
 */