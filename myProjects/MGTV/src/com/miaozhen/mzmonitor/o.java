package com.miaozhen.mzmonitor;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.provider.Settings.Secure;
import android.util.Log;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

class o
  implements ServiceConnection
{
  private static String f = null;
  private static boolean g = false;
  private final Context a;
  private List<ResolveInfo> b;
  private Map<String, Integer> c;
  private final SharedPreferences d;
  private final Random e;

  private o(Context paramContext)
  {
    this.d = paramContext.getSharedPreferences("openudid_prefs", 0);
    this.a = paramContext;
    this.e = new Random();
    this.c = new HashMap();
  }

  public static String a()
  {
    if (!g)
      Log.e("OpenUDID", "Initialisation isn't done");
    return f;
  }

  public static void a(Context paramContext)
  {
    o localo = new o(paramContext);
    f = localo.d.getString("openudid", null);
    if (f == null)
    {
      localo.b = paramContext.getPackageManager().queryIntentServices(new Intent("org.OpenUDID.GETUDID"), 0);
      Log.d("OpenUDID", localo.b.size() + " services matches OpenUDID");
      if (localo.b != null)
        localo.e();
      return;
    }
    Log.d("OpenUDID", "OpenUDID: " + f);
    g = true;
  }

  public static boolean b()
  {
    return g;
  }

  private void c()
  {
    SharedPreferences.Editor localEditor = this.d.edit();
    localEditor.putString("openudid", f);
    localEditor.commit();
  }

  private void d()
  {
    Log.d("OpenUDID", "Generating openUDID");
    f = Settings.Secure.getString(this.a.getContentResolver(), "android_id");
    if ((f == null) || (f.equals("9774d56d682e549c")) || (f.length() < 15))
      f = new BigInteger(64, new SecureRandom()).toString(16);
  }

  private void e()
  {
    if (this.b.size() > 0)
    {
      Log.d("OpenUDID", "Trying service " + ((ResolveInfo)this.b.get(0)).loadLabel(this.a.getPackageManager()));
      ServiceInfo localServiceInfo = ((ResolveInfo)this.b.get(0)).serviceInfo;
      Intent localIntent = new Intent();
      localIntent.setComponent(new ComponentName(localServiceInfo.applicationInfo.packageName, localServiceInfo.name));
      this.a.bindService(localIntent, this, 1);
      this.b.remove(0);
      return;
    }
    f();
    if (f == null)
      d();
    Log.d("OpenUDID", "OpenUDID: " + f);
    c();
    g = true;
  }

  private void f()
  {
    if (!this.c.isEmpty())
    {
      TreeMap localTreeMap = new TreeMap(new p(this, null));
      localTreeMap.putAll(this.c);
      f = (String)localTreeMap.firstKey();
    }
  }

  public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
  {
    try
    {
      Parcel localParcel1 = Parcel.obtain();
      localParcel1.writeInt(this.e.nextInt());
      Parcel localParcel2 = Parcel.obtain();
      paramIBinder.transact(1, Parcel.obtain(), localParcel2, 0);
      String str;
      if (localParcel1.readInt() == localParcel2.readInt())
      {
        str = localParcel2.readString();
        if (str != null)
        {
          Log.d("OpenUDID", "Received " + str);
          if (!this.c.containsKey(str))
            break label146;
          this.c.put(str, Integer.valueOf(1 + ((Integer)this.c.get(str)).intValue()));
        }
      }
      while (true)
      {
        this.a.unbindService(this);
        e();
        return;
        label146: this.c.put(str, Integer.valueOf(1));
      }
    }
    catch (RemoteException localRemoteException)
    {
      while (true)
        Log.e("OpenUDID", "RemoteException: " + localRemoteException.getMessage());
    }
  }

  public void onServiceDisconnected(ComponentName paramComponentName)
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.o
 * JD-Core Version:    0.6.2
 */