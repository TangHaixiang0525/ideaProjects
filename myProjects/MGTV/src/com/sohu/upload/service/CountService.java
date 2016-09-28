package com.sohu.upload.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import com.sohu.httpserver.HttpServer;
import com.sohu.upload.b.d;
import com.sohu.upload.consts.CountModeEnum;
import java.util.ArrayList;
import java.util.Date;

public class CountService extends Service
{
  private Location a = null;
  private LocationManager b = null;
  private com.sohu.applist.b.a c = null;
  private com.sohu.applist.b.b d = null;
  private int e = com.sohu.upload.consts.a.f;
  private com.sohu.location.a f = new com.sohu.location.a();
  private Handler g = null;
  private CountModeEnum h = CountModeEnum.FIVE_MINUTE;
  private BroadcastReceiver i = new a(this);
  private LocationListener j = new f(this);

  private boolean a()
  {
    while (true)
    {
      int k;
      try
      {
        ArrayList localArrayList = com.sohu.upload.b.c.a();
        if (localArrayList != null)
        {
          k = 0;
          if (k < localArrayList.size())
          {
            String str = ((com.sohu.applist.a.c)localArrayList.get(k)).b();
            if (this.c.b(str))
              this.c.a(str);
            else
              this.c.c(str);
          }
        }
      }
      catch (Throwable localThrowable)
      {
        com.sohu.upload.a.a.b("�쳣:" + localThrowable.getMessage() + ",��ȡ��ǰ������Ϣʧ��");
        return false;
      }
      return true;
      k++;
    }
  }

  private boolean b()
  {
    if (this.a != null)
    {
      double d1 = this.a.getLatitude();
      double d2 = this.a.getLongitude();
      double d3 = this.a.getAccuracy();
      com.sohu.upload.a.a.a("Lat:" + d1 + ",lng:" + d2 + ",acc:" + d3);
      try
      {
        this.d.a(new com.sohu.applist.a.a(String.valueOf(d1), String.valueOf(d2), String.valueOf(d3), com.sohu.upload.b.c.i(), com.sohu.upload.b.c.h(), String.valueOf(System.currentTimeMillis())));
        this.d.c();
        return true;
      }
      catch (Exception localException2)
      {
        com.sohu.upload.a.a.b("�쳣:" + localException2.getMessage() + ",��¼��վ��Ϣʧ��");
        return false;
      }
    }
    try
    {
      this.d.a(new com.sohu.applist.a.a("no", "no", "no", com.sohu.upload.b.c.i(), com.sohu.upload.b.c.h(), String.valueOf(System.currentTimeMillis())));
      return true;
    }
    catch (Exception localException1)
    {
      com.sohu.upload.a.a.b("�쳣:" + localException1.getMessage() + ",��¼��վ��Ϣʧ��");
    }
    return false;
  }

  private void c()
  {
    new e(this).start();
  }

  private void d()
  {
    int k = new Date(System.currentTimeMillis()).getHours();
    if ((k >= 7) && (k < 10))
    {
      this.h = CountModeEnum.FIVE_MINUTE;
      return;
    }
    if ((k >= 10) && (k < 17))
    {
      this.h = CountModeEnum.ONE_HOUR;
      return;
    }
    if ((k >= 17) && (k <= 19))
    {
      this.h = CountModeEnum.FIVE_MINUTE;
      return;
    }
    if ((k > 19) && (k <= 23))
    {
      this.h = CountModeEnum.ONE_HOUR;
      return;
    }
    this.h = CountModeEnum.OTHER;
  }

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onCreate()
  {
    super.onCreate();
    com.sohu.upload.a.a.a("UploadService Start");
    com.sohu.upload.a.b.a(this);
    com.sohu.upload.b.c.a(this);
    com.sohu.upload.b.b.a(this);
    com.sohu.upload.a.a.a("��¼��������...");
    com.sohu.upload.b.b.c(System.currentTimeMillis() + "", System.currentTimeMillis());
    new d(this, "��������...").start();
    try
    {
      this.b = ((LocationManager)getSystemService("location"));
      this.b.requestLocationUpdates("network", 1000L, 1.0F, this.j);
      this.a = this.b.getLastKnownLocation("network");
      if (this.a == null)
        this.a = this.b.getLastKnownLocation("gps");
      if (this.a == null)
        this.a = this.b.getLastKnownLocation("passive");
    }
    catch (Exception localException2)
    {
      try
      {
        this.c = new com.sohu.applist.b.a(getApplicationContext());
        this.d = new com.sohu.applist.b.b(getApplicationContext());
        label183: this.g = new b(this, getMainLooper());
        this.g.sendEmptyMessage(1);
        localHttpServer = new HttpServer();
      }
      catch (Exception localException2)
      {
        try
        {
          HttpServer localHttpServer;
          new d(this, "��������...").start();
          com.sohu.upload.a.a.a("������...");
          Boolean localBoolean = Boolean.valueOf(false);
          for (int k = 0; ; k++)
          {
            if (k < com.sohu.upload.consts.a.e.length)
            {
              this.e = com.sohu.upload.consts.a.e[k];
              if (!com.sohu.upload.b.c.a(this.e))
                localBoolean = Boolean.valueOf(true);
            }
            else
            {
              if (localBoolean.booleanValue())
              {
                com.sohu.upload.a.a.a("����ѡ��˿� port:" + this.e);
                localHttpServer.execute(this, this.e);
              }
              c();
              IntentFilter localIntentFilter = new IntentFilter();
              localIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
              registerReceiver(this.i, localIntentFilter);
              return;
              localException1 = localException1;
              com.sohu.upload.a.a.b("�쳣:" + localException1.getMessage() + ",��ȡ��λʵ��ʧ��");
              break;
              localException2 = localException2;
              com.sohu.upload.a.a.b("�쳣:" + localException2.getMessage() + ",��ʼ��Daoʧ��");
              break label183;
            }
            com.sohu.upload.a.a.a("�˿� port:" + this.e + "��ռ��");
          }
        }
        catch (Exception localException3)
        {
          while (true)
            com.sohu.upload.a.a.b("��������ʱ�����쳣:" + localException3.getMessage());
        }
      }
    }
  }

  public void onDestroy()
  {
    com.sohu.upload.a.a.a("onDestroy");
    unregisterReceiver(this.i);
    Intent localIntent = new Intent();
    localIntent.setClass(this, CountService.class);
    startService(localIntent);
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    return 1;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.upload.service.CountService
 * JD-Core Version:    0.6.2
 */