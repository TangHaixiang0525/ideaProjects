package com.starcor.hunan.xul;

import android.content.Intent;
import com.starcor.xul.XulView;
import org.json.JSONObject;

public abstract class XULJSCmdHost
{
  public abstract void close();

  public abstract void finish();

  public abstract JSONObject getJSInitData();

  public abstract void hideProgressInfo();

  public abstract void showProgressInfo();

  public abstract void startActivity(Intent paramIntent);

  public void xulExecute(String paramString, String[] paramArrayOfString)
  {
  }

  public abstract XulView xulFindViewById(String paramString);

  public void xulFireEvent(String paramString)
  {
    xulFireEvent(paramString, null);
  }

  public abstract void xulFireEvent(String paramString, Object[] paramArrayOfObject);

  public abstract void xulPostDelay(Runnable paramRunnable, int paramInt);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.xul.XULJSCmdHost
 * JD-Core Version:    0.6.2
 */