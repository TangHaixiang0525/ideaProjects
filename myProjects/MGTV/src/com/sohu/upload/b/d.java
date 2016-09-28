package com.sohu.upload.b;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;
import com.sohu.upload.a.a;

public class d extends Thread
{
  public static boolean a = false;
  private Context b = null;
  private String c = "";

  public d(Context paramContext, String paramString)
  {
    this.b = paramContext;
    this.c = paramString;
  }

  public void run()
  {
    Looper.prepare();
    try
    {
      if (a)
        Toast.makeText(this.b, this.c, 1).show();
      Looper.loop();
      return;
    }
    catch (Exception localException)
    {
      while (true)
        a.b("��ʾtoast��Ϣ�쳣");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.upload.b.d
 * JD-Core Version:    0.6.2
 */