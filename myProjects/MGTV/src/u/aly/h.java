package u.aly;

import android.os.Build;
import android.os.Build.VERSION;

public class h extends a
{
  private static final String a = "serial";

  public h()
  {
    super("serial");
  }

  public String f()
  {
    if (Build.VERSION.SDK_INT >= 9)
      return Build.SERIAL;
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.h
 * JD-Core Version:    0.6.2
 */