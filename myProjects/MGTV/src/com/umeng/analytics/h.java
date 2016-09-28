package com.umeng.analytics;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Locale;
import u.aly.bq;
import u.aly.br;
import u.aly.cd;

public final class h
{
  private static h a = null;
  private static Context b;
  private static String c;
  private static long e = 0L;
  private static long f = 0L;
  private static final String g = "mobclick_agent_user_";
  private static final String h = "mobclick_agent_online_setting_";
  private static final String i = "mobclick_agent_header_";
  private static final String j = "mobclick_agent_update_";
  private static final String k = "mobclick_agent_state_";
  private static final String l = "mobclick_agent_cached_";
  private a d;

  public h(Context paramContext)
  {
    this.d = new a(paramContext);
    b = paramContext.getApplicationContext();
    c = paramContext.getPackageName();
  }

  public static h a(Context paramContext)
  {
    try
    {
      if (a == null)
        a = new h(paramContext);
      h localh = a;
      return localh;
    }
    finally
    {
    }
  }

  private static boolean a(File paramFile)
  {
    long l1 = paramFile.length();
    return (paramFile.exists()) && (l1 > f);
  }

  private SharedPreferences n()
  {
    return b.getSharedPreferences("mobclick_agent_user_" + c, 0);
  }

  private String o()
  {
    return "mobclick_agent_header_" + c;
  }

  private String p()
  {
    return "mobclick_agent_cached_" + c + bq.c(b);
  }

  public void a(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt <= 3))
    {
      SharedPreferences localSharedPreferences = j();
      if (localSharedPreferences != null)
        localSharedPreferences.edit().putInt("oc_dc", paramInt).commit();
    }
  }

  public void a(int paramInt1, int paramInt2)
  {
    SharedPreferences.Editor localEditor = a(b).j().edit();
    localEditor.putInt("umeng_net_report_policy", paramInt1);
    localEditor.putLong("umeng_net_report_interval", paramInt2);
    localEditor.commit();
  }

  public void a(String paramString1, String paramString2)
  {
    if ((!TextUtils.isEmpty(paramString1)) && (!TextUtils.isEmpty(paramString2)))
    {
      SharedPreferences.Editor localEditor = n().edit();
      localEditor.putString("au_p", paramString1);
      localEditor.putString("au_u", paramString2);
      localEditor.commit();
    }
  }

  public void a(byte[] paramArrayOfByte)
  {
    String str = p();
    try
    {
      cd.a(new File(b.getFilesDir(), str), paramArrayOfByte);
      return;
    }
    catch (Exception localException)
    {
      br.b("MobclickAgent", localException.getMessage());
    }
  }

  public String[] a()
  {
    SharedPreferences localSharedPreferences = n();
    String str1 = localSharedPreferences.getString("au_p", null);
    String str2 = localSharedPreferences.getString("au_u", null);
    String[] arrayOfString = null;
    if (str1 != null)
    {
      arrayOfString = null;
      if (str2 != null)
      {
        arrayOfString = new String[2];
        arrayOfString[0] = str1;
        arrayOfString[1] = str2;
      }
    }
    return arrayOfString;
  }

  public void b()
  {
    n().edit().remove("au_p").remove("au_u").commit();
  }

  public void b(int paramInt)
  {
    if (paramInt > 0)
    {
      SharedPreferences localSharedPreferences = j();
      if (localSharedPreferences != null)
        localSharedPreferences.edit().putInt("oc_lt", paramInt).commit();
    }
  }

  public void b(byte[] paramArrayOfByte)
  {
    this.d.a(paramArrayOfByte);
  }

  public void c(int paramInt)
  {
    SharedPreferences localSharedPreferences = j();
    if (localSharedPreferences != null)
      localSharedPreferences.edit().putInt("oc_ec", paramInt).commit();
  }

  public int[] c()
  {
    SharedPreferences localSharedPreferences = j();
    int[] arrayOfInt = new int[2];
    if (localSharedPreferences.getInt("umeng_net_report_policy", -1) != -1)
    {
      arrayOfInt[0] = localSharedPreferences.getInt("umeng_net_report_policy", 1);
      arrayOfInt[1] = ((int)localSharedPreferences.getLong("umeng_net_report_interval", 0L));
      return arrayOfInt;
    }
    arrayOfInt[0] = localSharedPreferences.getInt("umeng_local_report_policy", 1);
    arrayOfInt[1] = ((int)localSharedPreferences.getLong("umeng_local_report_interval", 0L));
    return arrayOfInt;
  }

  public int d()
  {
    SharedPreferences localSharedPreferences = j();
    int m = 0;
    if (localSharedPreferences != null)
      m = localSharedPreferences.getInt("oc_dc", 0);
    return m;
  }

  public int d(int paramInt)
  {
    SharedPreferences localSharedPreferences = j();
    if (localSharedPreferences != null)
      paramInt = localSharedPreferences.getInt("oc_ec", paramInt);
    return paramInt;
  }

  public int e()
  {
    SharedPreferences localSharedPreferences = j();
    int m = 0;
    if (localSharedPreferences != null)
      m = localSharedPreferences.getInt("oc_lt", 0);
    return m;
  }

  // ERROR //
  public byte[] f()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 160	com/umeng/analytics/h:p	()Ljava/lang/String;
    //   4: astore_1
    //   5: new 76	java/io/File
    //   8: dup
    //   9: getstatic 65	com/umeng/analytics/h:b	Landroid/content/Context;
    //   12: invokevirtual 164	android/content/Context:getFilesDir	()Ljava/io/File;
    //   15: aload_1
    //   16: invokespecial 167	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   19: astore_2
    //   20: aload_2
    //   21: invokestatic 216	com/umeng/analytics/h:a	(Ljava/io/File;)Z
    //   24: ifeq +10 -> 34
    //   27: aload_2
    //   28: invokevirtual 219	java/io/File:delete	()Z
    //   31: pop
    //   32: aconst_null
    //   33: areturn
    //   34: aload_2
    //   35: invokevirtual 84	java/io/File:exists	()Z
    //   38: ifeq -6 -> 32
    //   41: getstatic 65	com/umeng/analytics/h:b	Landroid/content/Context;
    //   44: aload_1
    //   45: invokevirtual 223	android/content/Context:openFileInput	(Ljava/lang/String;)Ljava/io/FileInputStream;
    //   48: astore 7
    //   50: aload 7
    //   52: astore 4
    //   54: aload 4
    //   56: invokestatic 226	u/aly/cd:b	(Ljava/io/InputStream;)[B
    //   59: astore 8
    //   61: aload 4
    //   63: invokestatic 229	u/aly/cd:c	(Ljava/io/InputStream;)V
    //   66: aload 8
    //   68: areturn
    //   69: astore 6
    //   71: aconst_null
    //   72: astore 4
    //   74: aload 6
    //   76: invokevirtual 232	java/lang/Exception:printStackTrace	()V
    //   79: aload 4
    //   81: invokestatic 229	u/aly/cd:c	(Ljava/io/InputStream;)V
    //   84: aconst_null
    //   85: areturn
    //   86: astore_3
    //   87: aconst_null
    //   88: astore 4
    //   90: aload_3
    //   91: astore 5
    //   93: aload 4
    //   95: invokestatic 229	u/aly/cd:c	(Ljava/io/InputStream;)V
    //   98: aload 5
    //   100: athrow
    //   101: astore 5
    //   103: goto -10 -> 93
    //   106: astore 6
    //   108: goto -34 -> 74
    //
    // Exception table:
    //   from	to	target	type
    //   41	50	69	java/lang/Exception
    //   41	50	86	finally
    //   54	61	101	finally
    //   74	79	101	finally
    //   54	61	106	java/lang/Exception
  }

  public void g()
  {
    b.deleteFile(o());
    b.deleteFile(p());
  }

  public boolean h()
  {
    return this.d.a();
  }

  public a i()
  {
    return this.d;
  }

  public SharedPreferences j()
  {
    return b.getSharedPreferences("mobclick_agent_online_setting_" + c, 0);
  }

  public SharedPreferences k()
  {
    return b.getSharedPreferences("mobclick_agent_header_" + c, 0);
  }

  public SharedPreferences l()
  {
    return b.getSharedPreferences("mobclick_agent_update_" + c, 0);
  }

  public SharedPreferences m()
  {
    return b.getSharedPreferences("mobclick_agent_state_" + c, 0);
  }

  public static class a
  {
    private final int a = 10;
    private File b;
    private FilenameFilter c = new FilenameFilter()
    {
      public boolean accept(File paramAnonymousFile, String paramAnonymousString)
      {
        return paramAnonymousString.startsWith("um");
      }
    };

    public a(Context paramContext)
    {
      this(paramContext, ".um");
    }

    public a(Context paramContext, String paramString)
    {
      this.b = new File(paramContext.getFilesDir(), paramString);
      if ((!this.b.exists()) || (!this.b.isDirectory()))
        this.b.mkdir();
    }

    // ERROR //
    public void a(h.b paramb)
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 42	com/umeng/analytics/h$a:b	Ljava/io/File;
      //   4: aload_0
      //   5: getfield 29	com/umeng/analytics/h$a:c	Ljava/io/FilenameFilter;
      //   8: invokevirtual 59	java/io/File:listFiles	(Ljava/io/FilenameFilter;)[Ljava/io/File;
      //   11: astore_2
      //   12: aload_2
      //   13: ifnull +89 -> 102
      //   16: aload_2
      //   17: arraylength
      //   18: ifle +84 -> 102
      //   21: aload_1
      //   22: aload_0
      //   23: getfield 42	com/umeng/analytics/h$a:b	Ljava/io/File;
      //   26: invokeinterface 64 2 0
      //   31: aload_2
      //   32: arraylength
      //   33: istore_3
      //   34: iconst_0
      //   35: istore 4
      //   37: iload 4
      //   39: iload_3
      //   40: if_icmpge +52 -> 92
      //   43: aload_1
      //   44: aload_2
      //   45: iload 4
      //   47: aaload
      //   48: invokeinterface 67 2 0
      //   53: istore 8
      //   55: iload 8
      //   57: ifeq +11 -> 68
      //   60: aload_2
      //   61: iload 4
      //   63: aaload
      //   64: invokevirtual 70	java/io/File:delete	()Z
      //   67: pop
      //   68: iinc 4 1
      //   71: goto -34 -> 37
      //   74: astore 6
      //   76: aload_2
      //   77: iload 4
      //   79: aaload
      //   80: invokevirtual 70	java/io/File:delete	()Z
      //   83: pop
      //   84: goto -16 -> 68
      //   87: astore 5
      //   89: aload 5
      //   91: athrow
      //   92: aload_1
      //   93: aload_0
      //   94: getfield 42	com/umeng/analytics/h$a:b	Ljava/io/File;
      //   97: invokeinterface 72 2 0
      //   102: return
      //
      // Exception table:
      //   from	to	target	type
      //   43	55	74	java/lang/Throwable
      //   43	55	87	finally
    }

    public void a(byte[] paramArrayOfByte)
    {
      int i = 0;
      if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0));
      while (true)
      {
        return;
        Locale localLocale = Locale.US;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Long.valueOf(System.currentTimeMillis());
        String str = String.format(localLocale, "um_cache_%d.env", arrayOfObject);
        File localFile = new File(this.b, str);
        try
        {
          cd.a(localFile, paramArrayOfByte);
          label63: File[] arrayOfFile = this.b.listFiles(this.c);
          if ((arrayOfFile == null) || (arrayOfFile.length < 10))
            continue;
          Arrays.sort(arrayOfFile);
          int j = -10 + arrayOfFile.length;
          while (i < j)
          {
            arrayOfFile[i].delete();
            i++;
          }
        }
        catch (Exception localException)
        {
          break label63;
        }
      }
    }

    public boolean a()
    {
      File[] arrayOfFile = this.b.listFiles();
      return (arrayOfFile != null) && (arrayOfFile.length > 0);
    }

    public void b()
    {
      File[] arrayOfFile = this.b.listFiles(this.c);
      if ((arrayOfFile != null) && (arrayOfFile.length > 0))
      {
        int i = arrayOfFile.length;
        for (int j = 0; j < i; j++)
          arrayOfFile[j].delete();
      }
    }

    public int c()
    {
      File[] arrayOfFile = this.b.listFiles(this.c);
      if ((arrayOfFile != null) && (arrayOfFile.length > 0))
        return arrayOfFile.length;
      return 0;
    }
  }

  public static abstract interface b
  {
    public abstract void a(File paramFile);

    public abstract boolean b(File paramFile);

    public abstract void c(File paramFile);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.h
 * JD-Core Version:    0.6.2
 */