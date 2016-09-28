package com.sohu.app.ads.sdk.f;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.DisplayMetrics;
import com.sohu.app.ads.sdk.c.a;
import com.sohu.app.ads.sdk.model.AdsResponse;
import com.sohu.app.ads.sdk.model.emu.AdEventType;
import com.sohu.mobile.a.a.b;
import com.sohu.mobile.a.a.c;
import java.io.File;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.UUID;

public class d
{
  private static Context a;

  public static int a(float paramFloat)
  {
    try
    {
      int i = Integer.valueOf(new BigDecimal(paramFloat).setScale(0, 4).toString()).intValue();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }

  public static void a(Context paramContext)
  {
    a = paramContext;
  }

  public static void a(AdEventType paramAdEventType, AdsResponse paramAdsResponse)
  {
    int i;
    int j;
    try
    {
      i = paramAdsResponse.getAdSequence();
      j = paramAdsResponse.isOfflineAd();
      switch (e.a[paramAdEventType.ordinal()])
      {
      case 1:
        a.a("记录:sequence=" + i + "是空广告");
        return;
      case 2:
      case 3:
      case 4:
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    if (i == 1)
    {
      if (j == 1)
      {
        a.a("记录:离线前贴片1");
        b.a().a("offlineOad1");
        return;
      }
      if (j == 0)
      {
        a.a("记录:在线前贴片1");
        b.a().a("onlineOad1");
      }
    }
    else if (i == 2)
    {
      if (j == 1)
      {
        a.a("记录:离线前贴片2");
        b.a().a("offlineOad2");
        return;
      }
      if (j == 0)
      {
        a.a("记录:在线前贴片2");
        b.a().a("onlineOad2");
      }
    }
    else if (i == 3)
    {
      if (j == 1)
      {
        a.a("记录:离线前贴片3");
        b.a().a("offlineOad3");
        return;
      }
      if (j == 0)
      {
        a.a("记录:在线前贴片3");
        b.a().a("onlineOad3");
      }
    }
    else if (i == 4)
    {
      if (j == 1)
      {
        a.a("记录:离线前贴片4");
        b.a().a("offlineOad4");
        return;
      }
      if (j == 0)
      {
        a.a("记录:在线前贴片4");
        b.a().a("onlineOad4");
        return;
        if (i == 1)
        {
          if (j == 0)
          {
            a.c("记录:在线前贴片1播放出错");
            b.a().a("onlineOad1Error");
          }
        }
        else if (i == 2)
        {
          if (j == 0)
          {
            a.c("记录:在线前贴片2播放出错");
            b.a().a("onlineOad2Error");
          }
        }
        else if (i == 3)
        {
          if (j == 0)
          {
            a.c("记录:在线前贴片3播放出错");
            b.a().a("onlineOad3Error");
          }
        }
        else if ((i == 4) && (j == 0))
        {
          a.c("记录:在线前贴片4播放出错");
          b.a().a("onlineOad4Error");
          return;
          if (i == 1)
          {
            if (j == 0)
            {
              a.b("记录:在线前贴片1播放超时");
              b.a().a("onlineOad1Timeout");
            }
          }
          else if (i == 2)
          {
            if (j == 0)
            {
              a.b("记录:在线前贴片2播放超时");
              b.a().a("onlineOad2Timeout");
            }
          }
          else if (i == 3)
          {
            if (j == 0)
            {
              a.b("记录:在线前贴片3播放超时");
              b.a().a("onlineOad3Timeout");
            }
          }
          else if ((i == 4) && (j == 0))
          {
            a.b("记录:在线前贴片4播放超时");
            b.a().a("onlineOad4Timeout");
          }
        }
      }
    }
  }

  public static void a(File paramFile, int paramInt)
  {
    int i = 0;
    if (paramFile != null)
      try
      {
        if (paramFile.isDirectory())
        {
          File[] arrayOfFile = paramFile.listFiles();
          int j = arrayOfFile.length;
          if (j > paramInt)
          {
            long[] arrayOfLong = new long[j];
            for (int k = 0; k < j; k++)
              arrayOfLong[k] = arrayOfFile[k].lastModified();
            long l = a(arrayOfLong)[(paramInt - 1)];
            int m = arrayOfFile.length;
            while (i < m)
            {
              File localFile = arrayOfFile[i];
              if (localFile.lastModified() <= l)
                localFile.delete();
              i++;
            }
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
  }

  public static boolean a()
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)a.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo != null)
      {
        boolean bool = localNetworkInfo.isConnected();
        if (bool)
          return true;
      }
      return false;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public static boolean a(String paramString)
  {
    return (paramString != null) && (!"".equals(paramString));
  }

  public static int[] a(int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = new int[4];
    if ((paramInt2 <= 40) && (paramInt2 >= 0));
    while (true)
    {
      int i;
      int j;
      double d;
      int m;
      try
      {
        DisplayMetrics localDisplayMetrics = a.getResources().getDisplayMetrics();
        i = localDisplayMetrics.widthPixels;
        j = localDisplayMetrics.heightPixels;
        d = localDisplayMetrics.density;
        a.a("density==" + d + "heightPixels==" + j + "widthPixels==" + i);
        int k = a.getResources().getConfiguration().orientation;
        m = 0;
        if (k == 2)
        {
          if (d == 1.0D)
          {
            arrayOfInt[0] = (56 * (i / 100));
            arrayOfInt[1] = (60 * (j / 100));
            arrayOfInt[2] = (25 * (i / 100));
            arrayOfInt[3] = (paramInt2 * (j / 100));
          }
        }
        else
        {
          if (m >= arrayOfInt.length)
            break label274;
          a.a("viewSize[" + m + "]=" + arrayOfInt[m]);
          m++;
          continue;
        }
        if (d != 1.5D)
          break label276;
        arrayOfInt[0] = (56 * (i / 100));
        arrayOfInt[1] = (60 * (j / 100));
        arrayOfInt[2] = (25 * (i / 100));
        arrayOfInt[3] = (paramInt2 * (j / 100));
        m = 0;
        continue;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      label274: return arrayOfInt;
      label276: if (d == 2.0D)
      {
        arrayOfInt[0] = (56 * (i / 100));
        arrayOfInt[1] = (60 * (j / 100));
        arrayOfInt[2] = (25 * (i / 100));
        arrayOfInt[3] = (paramInt2 * (j / 100));
        m = 0;
      }
      else
      {
        arrayOfInt[0] = (56 * (i / 100));
        arrayOfInt[1] = (60 * (j / 100));
        arrayOfInt[2] = (25 * (i / 100));
        arrayOfInt[3] = (paramInt2 * (j / 100));
        m = 0;
        continue;
        paramInt2 = 21;
      }
    }
  }

  public static long[] a(long[] paramArrayOfLong)
  {
    int i = 1;
    try
    {
      while (i < paramArrayOfLong.length)
      {
        long l = paramArrayOfLong[i];
        for (int j = i; (j > 0) && (paramArrayOfLong[(j - 1)] < l); j--)
          paramArrayOfLong[j] = paramArrayOfLong[(j - 1)];
        paramArrayOfLong[j] = l;
        i++;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramArrayOfLong;
  }

  // ERROR //
  public static String b()
  {
    // Byte code:
    //   0: getstatic 40	com/sohu/app/ads/sdk/f/d:a	Landroid/content/Context;
    //   3: ldc_w 259
    //   6: invokevirtual 187	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   9: checkcast 261	android/telephony/TelephonyManager
    //   12: astore_3
    //   13: aload_3
    //   14: ifnull +54 -> 68
    //   17: aload_3
    //   18: invokevirtual 264	android/telephony/TelephonyManager:getDeviceId	()Ljava/lang/String;
    //   21: astore 4
    //   23: aload 4
    //   25: astore_1
    //   26: aload_1
    //   27: invokestatic 270	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   30: ifeq +20 -> 50
    //   33: getstatic 40	com/sohu/app/ads/sdk/f/d:a	Landroid/content/Context;
    //   36: invokevirtual 274	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   39: ldc_w 276
    //   42: invokestatic 282	android/provider/Settings$Secure:getString	(Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;
    //   45: astore 5
    //   47: aload 5
    //   49: astore_1
    //   50: aload_1
    //   51: areturn
    //   52: astore_0
    //   53: ldc 201
    //   55: astore_1
    //   56: aload_0
    //   57: astore_2
    //   58: aload_2
    //   59: invokevirtual 37	java/lang/Exception:printStackTrace	()V
    //   62: aload_1
    //   63: areturn
    //   64: astore_2
    //   65: goto -7 -> 58
    //   68: ldc 201
    //   70: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   0	13	52	java/lang/Exception
    //   17	23	52	java/lang/Exception
    //   26	47	64	java/lang/Exception
  }

  public static String b(String paramString)
  {
    int i = 0;
    char[] arrayOfChar1 = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
    try
    {
      byte[] arrayOfByte1 = paramString.getBytes();
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(arrayOfByte1);
      byte[] arrayOfByte2 = localMessageDigest.digest();
      int j = arrayOfByte2.length;
      char[] arrayOfChar2 = new char[j * 2];
      int k = 0;
      while (i < j)
      {
        int m = arrayOfByte2[i];
        int n = k + 1;
        arrayOfChar2[k] = arrayOfChar1[(0xF & m >>> 4)];
        k = n + 1;
        arrayOfChar2[n] = arrayOfChar1[(m & 0xF)];
        i++;
      }
      String str = new String(arrayOfChar2);
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  // ERROR //
  public static String c()
  {
    // Byte code:
    //   0: getstatic 40	com/sohu/app/ads/sdk/f/d:a	Landroid/content/Context;
    //   3: ldc_w 259
    //   6: invokevirtual 187	android/content/Context:getSystemService	(Ljava/lang/String;)Ljava/lang/Object;
    //   9: checkcast 261	android/telephony/TelephonyManager
    //   12: astore_3
    //   13: aload_3
    //   14: ifnull +41 -> 55
    //   17: aload_3
    //   18: invokevirtual 324	android/telephony/TelephonyManager:getSubscriberId	()Ljava/lang/String;
    //   21: astore 4
    //   23: aload 4
    //   25: astore_1
    //   26: aload_1
    //   27: invokestatic 270	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   30: ifeq +7 -> 37
    //   33: ldc_w 326
    //   36: astore_1
    //   37: aload_1
    //   38: areturn
    //   39: astore_0
    //   40: ldc 201
    //   42: astore_1
    //   43: aload_0
    //   44: astore_2
    //   45: aload_2
    //   46: invokevirtual 37	java/lang/Exception:printStackTrace	()V
    //   49: aload_1
    //   50: areturn
    //   51: astore_2
    //   52: goto -7 -> 45
    //   55: ldc 201
    //   57: astore_1
    //   58: goto -32 -> 26
    //
    // Exception table:
    //   from	to	target	type
    //   0	13	39	java/lang/Exception
    //   17	23	39	java/lang/Exception
    //   26	33	51	java/lang/Exception
  }

  public static String c(String paramString)
  {
    try
    {
      if (a(paramString))
      {
        String str1 = paramString.substring(paramString.lastIndexOf("."));
        String str2 = b(paramString);
        String str3 = str2 + str1;
        return str3;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }

  public static int d()
  {
    try
    {
      TelephonyManager localTelephonyManager = (TelephonyManager)a.getSystemService("phone");
      switch (localTelephonyManager.getPhoneType())
      {
      case 1:
        GsmCellLocation localGsmCellLocation = (GsmCellLocation)localTelephonyManager.getCellLocation();
        if (localGsmCellLocation != null)
          return localGsmCellLocation.getLac();
        break;
      case 2:
        CdmaCellLocation localCdmaCellLocation = (CdmaCellLocation)localTelephonyManager.getCellLocation();
        if (localCdmaCellLocation != null)
        {
          int i = localCdmaCellLocation.getBaseStationId();
          return i;
        }
        break;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public static boolean d(String paramString)
  {
    return (paramString != null) && (!"".equals(paramString)) && (paramString.startsWith("http://"));
  }

  public static boolean e()
  {
    try
    {
      if ("mounted".equals(Environment.getExternalStorageState()))
      {
        boolean bool = Environment.getExternalStorageDirectory().canWrite();
        if (bool)
          return true;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public static String f()
  {
    try
    {
      DisplayMetrics localDisplayMetrics = a.getResources().getDisplayMetrics();
      int i = localDisplayMetrics.widthPixels;
      int j = localDisplayMetrics.heightPixels;
      String str = i + "*" + j;
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }

  public static String g()
  {
    try
    {
      String str = String.valueOf(a.getResources().getDisplayMetrics().density);
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }

  public static File h()
  {
    try
    {
      File localFile = a.getApplicationContext().getExternalFilesDir("JPGCACHE");
      return localFile;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static File i()
  {
    try
    {
      File localFile = a.getExternalFilesDir("OADCACHE");
      return localFile;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static String j()
  {
    String str1 = UUID.randomUUID().toString();
    String str2 = String.valueOf(System.currentTimeMillis());
    return str1 + str2;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.f.d
 * JD-Core Version:    0.6.2
 */