package u.aly;

public class dj
{
  private static int a = 2147483647;

  public static di a(byte[] paramArrayOfByte, di paramdi)
  {
    if (paramArrayOfByte[0] > 16)
      paramdi = new da.a();
    while ((paramArrayOfByte.length <= 1) || ((0x80 & paramArrayOfByte[1]) == 0))
      return paramdi;
    return new da.a();
  }

  public static void a(int paramInt)
  {
    a = paramInt;
  }

  public static void a(dg paramdg, byte paramByte)
    throws cn
  {
    a(paramdg, paramByte, a);
  }

  public static void a(dg paramdg, byte paramByte, int paramInt)
    throws cn
  {
    int i = 0;
    if (paramInt <= 0)
      throw new cn("Maximum skip depth exceeded");
    switch (paramByte)
    {
    case 5:
    case 7:
    case 9:
    default:
      return;
    case 2:
      paramdg.t();
      return;
    case 3:
      paramdg.u();
      return;
    case 6:
      paramdg.v();
      return;
    case 8:
      paramdg.w();
      return;
    case 10:
      paramdg.x();
      return;
    case 4:
      paramdg.y();
      return;
    case 11:
      paramdg.A();
      return;
    case 12:
      paramdg.j();
      while (true)
      {
        db localdb = paramdg.l();
        if (localdb.b == 0)
        {
          paramdg.k();
          return;
        }
        a(paramdg, localdb.b, paramInt - 1);
        paramdg.m();
      }
    case 13:
      dd localdd = paramdg.n();
      while (i < localdd.c)
      {
        a(paramdg, localdd.a, paramInt - 1);
        a(paramdg, localdd.b, paramInt - 1);
        i++;
      }
      paramdg.o();
      return;
    case 14:
      dk localdk = paramdg.r();
      while (i < localdk.b)
      {
        a(paramdg, localdk.a, paramInt - 1);
        i++;
      }
      paramdg.s();
      return;
    case 15:
    }
    dc localdc = paramdg.p();
    while (i < localdc.b)
    {
      a(paramdg, localdc.a, paramInt - 1);
      i++;
    }
    paramdg.q();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.dj
 * JD-Core Version:    0.6.2
 */