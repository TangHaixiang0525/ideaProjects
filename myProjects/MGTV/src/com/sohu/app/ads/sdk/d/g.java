package com.sohu.app.ads.sdk.d;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;

public abstract class g<T> extends AsyncTask<T, Integer, T>
  implements DialogInterface.OnCancelListener
{
  private Dialog a;
  private boolean b = true;
  private boolean c = true;

  public g(Dialog paramDialog, boolean paramBoolean)
  {
    this.a = paramDialog;
    this.c = paramBoolean;
  }

  protected abstract T a(T[] paramArrayOfT);

  protected abstract String a(T paramT);

  public void a()
  {
    if ((this.a != null) && (this.a.isShowing()))
    {
      this.a.dismiss();
      this.a = null;
    }
  }

  protected void a(Integer[] paramArrayOfInteger)
  {
  }

  protected void b()
  {
  }

  protected T doInBackground(T[] paramArrayOfT)
  {
    return a(paramArrayOfT);
  }

  public void onCancel(DialogInterface paramDialogInterface)
  {
    if (this.b)
    {
      a();
      cancel(true);
      b();
    }
  }

  protected void onPostExecute(T paramT)
  {
    a();
    a(paramT);
  }

  protected void onPreExecute()
  {
    if ((this.a != null) && (this.c))
      this.a.show();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.d.g
 * JD-Core Version:    0.6.2
 */