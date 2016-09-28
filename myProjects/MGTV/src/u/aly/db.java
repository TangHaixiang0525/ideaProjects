package u.aly;

public class db
{
  public final String a;
  public final byte b;
  public final short c;

  public db()
  {
    this("", (byte)0, (short)0);
  }

  public db(String paramString, byte paramByte, short paramShort)
  {
    this.a = paramString;
    this.b = paramByte;
    this.c = paramShort;
  }

  public boolean a(db paramdb)
  {
    return (this.b == paramdb.b) && (this.c == paramdb.c);
  }

  public String toString()
  {
    return "<TField name:'" + this.a + "' type:" + this.b + " field-id:" + this.c + ">";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.db
 * JD-Core Version:    0.6.2
 */