package u.aly;

import org.json.JSONObject;

public abstract class bx
{
  protected static String b = "POST";
  protected static String c = "GET";
  protected String d;

  public bx(String paramString)
  {
    this.d = paramString;
  }

  public abstract JSONObject a();

  public void a(String paramString)
  {
    this.d = paramString;
  }

  public abstract String b();

  protected String c()
  {
    return b;
  }

  public String d()
  {
    return this.d;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.bx
 * JD-Core Version:    0.6.2
 */