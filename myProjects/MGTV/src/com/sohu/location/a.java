package com.sohu.location;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class a
{
  LocationListener a = new b(this);
  LocationListener b = new c(this);
  private LocationManager c;
  private d d;
  private boolean e = false;
  private boolean f = false;

  private void a()
  {
    while (true)
    {
      Location localLocation2;
      try
      {
        this.c.removeUpdates(this.a);
        this.c.removeUpdates(this.b);
        if (!this.e)
          break label185;
        localLocation1 = this.c.getLastKnownLocation("gps");
        boolean bool = this.f;
        localLocation2 = null;
        if (bool)
          localLocation2 = this.c.getLastKnownLocation("network");
        if ((localLocation1 != null) && (localLocation2 != null))
        {
          if (localLocation1.getTime() > localLocation2.getTime())
          {
            this.d.a(localLocation1);
            return;
          }
          this.d.a(localLocation2);
          return;
        }
      }
      catch (Exception localException)
      {
        com.sohu.upload.a.a.b("��ȡ���µ�Location�����ʱ������쳣:" + localException.getMessage());
        return;
      }
      if (localLocation1 != null)
      {
        this.d.a(localLocation1);
        return;
      }
      if (localLocation2 != null)
      {
        this.d.a(localLocation2);
        return;
      }
      this.d.a(this.c.getLastKnownLocation("passive"));
      return;
      label185: Location localLocation1 = null;
    }
  }

  // ERROR //
  public boolean a(android.content.Context paramContext, d paramd)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +5 -> 6
    //   4: iconst_0
    //   5: ireturn
    //   6: aload_0
    //   7: aload_2
    //   8: putfield 37	com/sohu/location/a:d	Lcom/sohu/location/d;
    //   11: aload_0
    //   12: getfield 41	com/sohu/location/a:c	Landroid/location/LocationManager;
    //   15: astore 4
    //   17: aload 4
    //   19: ifnonnull +16 -> 35
    //   22: aload_0
    //   23: aload_1
    //   24: ldc 93
    //   26: invokevirtual 99	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   29: checkcast 43	android/location/LocationManager
    //   32: putfield 41	com/sohu/location/a:c	Landroid/location/LocationManager;
    //   35: aload_0
    //   36: aload_0
    //   37: getfield 41	com/sohu/location/a:c	Landroid/location/LocationManager;
    //   40: ldc 49
    //   42: invokevirtual 103	android/location/LocationManager:isProviderEnabled	(Ljava/lang/String;)Z
    //   45: putfield 20	com/sohu/location/a:e	Z
    //   48: new 68	java/lang/StringBuilder
    //   51: dup
    //   52: invokespecial 69	java/lang/StringBuilder:<init>	()V
    //   55: ldc 105
    //   57: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   60: aload_0
    //   61: getfield 20	com/sohu/location/a:e	Z
    //   64: invokevirtual 108	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   67: invokevirtual 82	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   70: invokestatic 110	com/sohu/upload/a/a:a	(Ljava/lang/String;)V
    //   73: aload_0
    //   74: getfield 41	com/sohu/location/a:c	Landroid/location/LocationManager;
    //   77: ldc 55
    //   79: invokevirtual 103	android/location/LocationManager:isProviderEnabled	(Ljava/lang/String;)Z
    //   82: ifeq +300 -> 382
    //   85: invokestatic 116	com/sohu/upload/b/c:l	()Z
    //   88: ifeq +294 -> 382
    //   91: iconst_1
    //   92: istore 11
    //   94: aload_0
    //   95: iload 11
    //   97: putfield 22	com/sohu/location/a:f	Z
    //   100: new 68	java/lang/StringBuilder
    //   103: dup
    //   104: invokespecial 69	java/lang/StringBuilder:<init>	()V
    //   107: ldc 118
    //   109: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: aload_0
    //   113: getfield 22	com/sohu/location/a:f	Z
    //   116: invokevirtual 108	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   119: invokevirtual 82	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   122: invokestatic 110	com/sohu/upload/a/a:a	(Ljava/lang/String;)V
    //   125: aload_0
    //   126: getfield 20	com/sohu/location/a:e	Z
    //   129: ifne +133 -> 262
    //   132: aload_0
    //   133: getfield 22	com/sohu/location/a:f	Z
    //   136: ifne +126 -> 262
    //   139: iconst_0
    //   140: ireturn
    //   141: astore 12
    //   143: new 68	java/lang/StringBuilder
    //   146: dup
    //   147: invokespecial 69	java/lang/StringBuilder:<init>	()V
    //   150: ldc 120
    //   152: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   155: aload 12
    //   157: invokevirtual 79	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   160: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   163: invokevirtual 82	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   166: invokestatic 87	com/sohu/upload/a/a:b	(Ljava/lang/String;)V
    //   169: goto -134 -> 35
    //   172: astore_3
    //   173: new 68	java/lang/StringBuilder
    //   176: dup
    //   177: invokespecial 69	java/lang/StringBuilder:<init>	()V
    //   180: ldc 122
    //   182: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   185: aload_3
    //   186: invokevirtual 79	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   189: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   192: invokevirtual 82	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   195: invokestatic 87	com/sohu/upload/a/a:b	(Ljava/lang/String;)V
    //   198: iconst_0
    //   199: ireturn
    //   200: astore 5
    //   202: new 68	java/lang/StringBuilder
    //   205: dup
    //   206: invokespecial 69	java/lang/StringBuilder:<init>	()V
    //   209: ldc 124
    //   211: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   214: aload 5
    //   216: invokevirtual 79	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   219: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   222: invokevirtual 82	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   225: invokestatic 87	com/sohu/upload/a/a:b	(Ljava/lang/String;)V
    //   228: goto -180 -> 48
    //   231: astore 6
    //   233: new 68	java/lang/StringBuilder
    //   236: dup
    //   237: invokespecial 69	java/lang/StringBuilder:<init>	()V
    //   240: ldc 126
    //   242: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   245: aload 6
    //   247: invokevirtual 79	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   250: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   253: invokevirtual 82	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   256: invokestatic 87	com/sohu/upload/a/a:b	(Ljava/lang/String;)V
    //   259: goto -159 -> 100
    //   262: aload_0
    //   263: getfield 20	com/sohu/location/a:e	Z
    //   266: istore 7
    //   268: iload 7
    //   270: ifeq +18 -> 288
    //   273: aload_0
    //   274: getfield 41	com/sohu/location/a:c	Landroid/location/LocationManager;
    //   277: ldc 49
    //   279: lconst_0
    //   280: fconst_0
    //   281: aload_0
    //   282: getfield 29	com/sohu/location/a:a	Landroid/location/LocationListener;
    //   285: invokevirtual 130	android/location/LocationManager:requestLocationUpdates	(Ljava/lang/String;JFLandroid/location/LocationListener;)V
    //   288: aload_0
    //   289: getfield 22	com/sohu/location/a:f	Z
    //   292: istore 8
    //   294: iload 8
    //   296: ifeq +18 -> 314
    //   299: aload_0
    //   300: getfield 41	com/sohu/location/a:c	Landroid/location/LocationManager;
    //   303: ldc 55
    //   305: lconst_0
    //   306: fconst_0
    //   307: aload_0
    //   308: getfield 34	com/sohu/location/a:b	Landroid/location/LocationListener;
    //   311: invokevirtual 130	android/location/LocationManager:requestLocationUpdates	(Ljava/lang/String;JFLandroid/location/LocationListener;)V
    //   314: aload_0
    //   315: invokespecial 132	com/sohu/location/a:a	()V
    //   318: iconst_1
    //   319: ireturn
    //   320: astore 10
    //   322: new 68	java/lang/StringBuilder
    //   325: dup
    //   326: invokespecial 69	java/lang/StringBuilder:<init>	()V
    //   329: ldc 134
    //   331: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   334: aload 10
    //   336: invokevirtual 79	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   339: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   342: invokevirtual 82	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   345: invokestatic 87	com/sohu/upload/a/a:b	(Ljava/lang/String;)V
    //   348: goto -60 -> 288
    //   351: astore 9
    //   353: new 68	java/lang/StringBuilder
    //   356: dup
    //   357: invokespecial 69	java/lang/StringBuilder:<init>	()V
    //   360: ldc 136
    //   362: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   365: aload 9
    //   367: invokevirtual 79	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   370: invokevirtual 75	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   373: invokevirtual 82	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   376: invokestatic 87	com/sohu/upload/a/a:b	(Ljava/lang/String;)V
    //   379: goto -65 -> 314
    //   382: iconst_0
    //   383: istore 11
    //   385: goto -291 -> 94
    //
    // Exception table:
    //   from	to	target	type
    //   22	35	141	java/lang/Exception
    //   6	17	172	java/lang/Exception
    //   48	73	172	java/lang/Exception
    //   100	139	172	java/lang/Exception
    //   143	169	172	java/lang/Exception
    //   202	228	172	java/lang/Exception
    //   233	259	172	java/lang/Exception
    //   262	268	172	java/lang/Exception
    //   288	294	172	java/lang/Exception
    //   314	318	172	java/lang/Exception
    //   322	348	172	java/lang/Exception
    //   353	379	172	java/lang/Exception
    //   35	48	200	java/lang/Exception
    //   73	91	231	java/lang/Exception
    //   94	100	231	java/lang/Exception
    //   273	288	320	java/lang/Exception
    //   299	314	351	java/lang/Exception
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.location.a
 * JD-Core Version:    0.6.2
 */