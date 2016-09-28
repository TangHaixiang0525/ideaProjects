package u.aly;

import android.os.AsyncTask;

public class bt extends bw
{
  private static final String a = bt.class.getName();

  public bv.a a(bu parambu)
  {
    bv localbv = (bv)a(parambu, bv.class);
    if (localbv == null)
      return bv.a.b;
    return localbv.a;
  }

  public void a(bu parambu, a parama)
  {
    try
    {
      new b(parambu, parama).execute(new Integer[0]);
      return;
    }
    catch (Exception localException)
    {
      do
        br.b(a, "", localException);
      while (parama == null);
      parama.a(bv.a.b);
    }
  }

  public static abstract interface a
  {
    public abstract void a();

    public abstract void a(bv.a parama);
  }

  private class b extends AsyncTask<Integer, Integer, bv.a>
  {
    private bu b;
    private bt.a c;

    public b(bu parama, bt.a arg3)
    {
      this.b = parama;
      Object localObject;
      this.c = localObject;
    }

    protected bv.a a(Integer[] paramArrayOfInteger)
    {
      return bt.this.a(this.b);
    }

    protected void a(bv.a parama)
    {
      if (this.c != null)
        this.c.a(parama);
    }

    protected void onPreExecute()
    {
      if (this.c != null)
        this.c.a();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.bt
 * JD-Core Version:    0.6.2
 */