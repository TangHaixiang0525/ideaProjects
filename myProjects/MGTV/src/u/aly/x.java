package u.aly;

import android.content.Context;
import android.content.SharedPreferences;

public class x
{
  private static final String a = "umeng_general_config";

  public static SharedPreferences a(Context paramContext)
  {
    return paramContext.getSharedPreferences("umeng_general_config", 0);
  }

  public static SharedPreferences a(Context paramContext, String paramString)
  {
    return paramContext.getSharedPreferences(paramString, 0);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.x
 * JD-Core Version:    0.6.2
 */