package u.aly;

import android.content.Context;
import android.telephony.TelephonyManager;

public class e extends a
{
  private static final String a = "imei";
  private Context b;

  public e(Context paramContext)
  {
    super("imei");
    this.b = paramContext;
  }

  public String f()
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)this.b.getSystemService("phone");
    if (localTelephonyManager == null);
    try
    {
      if (bq.a(this.b, "android.permission.READ_PHONE_STATE"))
      {
        String str = localTelephonyManager.getDeviceId();
        return str;
      }
    }
    catch (Exception localException)
    {
      return null;
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.e
 * JD-Core Version:    0.6.2
 */