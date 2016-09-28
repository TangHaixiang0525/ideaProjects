package com.starcor.xulapp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.os.Looper;
import java.io.File;

public class XulSystemUtil
{
  public static final String NULL_OBJECT_STRING = "<null>";
  private static final String[] types = { "int", "java.lang.String", "boolean", "char", "float", "double", "long", "short", "byte" };

  public static boolean deleteDir(File paramFile)
  {
    if (paramFile.isDirectory())
    {
      String[] arrayOfString = paramFile.list();
      if (arrayOfString != null)
        for (int i = 0; i < arrayOfString.length; i++)
          if (!deleteDir(new File(paramFile, arrayOfString[i])))
            return false;
    }
    return paramFile.delete();
  }

  public static int getAppVersion(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      int i = localPackageManager.getPackageInfo(paramContext.getPackageName(), 0).versionCode;
      return i;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      localNameNotFoundException.printStackTrace();
    }
    return 0;
  }

  public static String getCurrentVersion(Context paramContext)
  {
    return "test-version";
  }

  public static File getDiskCacheDir(Context paramContext, String paramString)
  {
    File localFile = new File(getDiskCacheDir(paramContext), paramString);
    if (!localFile.exists())
      localFile.mkdirs();
    return localFile;
  }

  public static String getDiskCacheDir(Context paramContext)
  {
    if ((Environment.getExternalStorageState().equals("mounted")) && (!Environment.isExternalStorageRemovable()))
    {
      String str = getExternalCacheDir(paramContext);
      if (str == null)
        str = getInternalCacheDir(paramContext);
      return str;
    }
    return getInternalCacheDir(paramContext);
  }

  private static String getExternalCacheDir(Context paramContext)
  {
    File localFile = paramContext.getExternalCacheDir();
    if (localFile == null)
      return null;
    if (!localFile.exists())
      localFile.mkdirs();
    return localFile.getPath();
  }

  private static String getInternalCacheDir(Context paramContext)
  {
    File localFile = paramContext.getCacheDir();
    if (!localFile.exists())
      localFile.mkdirs();
    return localFile.getPath();
  }

  public static StackTraceElement getStackTrace()
  {
    return Thread.currentThread().getStackTrace()[4];
  }

  public static boolean isMainThread()
  {
    return Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId();
  }

  // ERROR //
  public static <T> String objectToString(T paramT)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +6 -> 7
    //   4: ldc 8
    //   6: areturn
    //   7: aload_0
    //   8: invokevirtual 164	java/lang/Object:toString	()Ljava/lang/String;
    //   11: new 166	java/lang/StringBuilder
    //   14: dup
    //   15: invokespecial 167	java/lang/StringBuilder:<init>	()V
    //   18: aload_0
    //   19: invokevirtual 171	java/lang/Object:getClass	()Ljava/lang/Class;
    //   22: invokevirtual 176	java/lang/Class:getName	()Ljava/lang/String;
    //   25: invokevirtual 180	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   28: ldc 182
    //   30: invokevirtual 180	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: invokevirtual 183	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   36: invokevirtual 187	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   39: ifeq +362 -> 401
    //   42: new 166	java/lang/StringBuilder
    //   45: dup
    //   46: new 166	java/lang/StringBuilder
    //   49: dup
    //   50: invokespecial 167	java/lang/StringBuilder:<init>	()V
    //   53: aload_0
    //   54: invokevirtual 171	java/lang/Object:getClass	()Ljava/lang/Class;
    //   57: invokevirtual 190	java/lang/Class:getSimpleName	()Ljava/lang/String;
    //   60: invokevirtual 180	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   63: ldc 192
    //   65: invokevirtual 180	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   68: invokevirtual 183	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   71: invokespecial 195	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   74: astore_1
    //   75: aload_0
    //   76: invokevirtual 171	java/lang/Object:getClass	()Ljava/lang/Class;
    //   79: invokevirtual 199	java/lang/Class:getDeclaredFields	()[Ljava/lang/reflect/Field;
    //   82: astore_2
    //   83: aload_2
    //   84: arraylength
    //   85: istore_3
    //   86: iconst_0
    //   87: istore 4
    //   89: iload 4
    //   91: iload_3
    //   92: if_icmpge +286 -> 378
    //   95: aload_2
    //   96: iload 4
    //   98: aaload
    //   99: astore 5
    //   101: aload 5
    //   103: iconst_1
    //   104: invokevirtual 205	java/lang/reflect/Field:setAccessible	(Z)V
    //   107: getstatic 34	com/starcor/xulapp/utils/XulSystemUtil:types	[Ljava/lang/String;
    //   110: astore 6
    //   112: aload 6
    //   114: arraylength
    //   115: istore 7
    //   117: iconst_0
    //   118: istore 8
    //   120: iconst_0
    //   121: istore 9
    //   123: iload 8
    //   125: iload 7
    //   127: if_icmpge +79 -> 206
    //   130: aload 6
    //   132: iload 8
    //   134: aaload
    //   135: astore 12
    //   137: aload 5
    //   139: invokevirtual 208	java/lang/reflect/Field:getType	()Ljava/lang/Class;
    //   142: invokevirtual 176	java/lang/Class:getName	()Ljava/lang/String;
    //   145: aload 12
    //   147: invokevirtual 211	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   150: ifeq +222 -> 372
    //   153: iconst_1
    //   154: istore 9
    //   156: aload 5
    //   158: aload_0
    //   159: invokevirtual 215	java/lang/reflect/Field:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   162: astore 21
    //   164: iconst_2
    //   165: anewarray 4	java/lang/Object
    //   168: astore 22
    //   170: aload 22
    //   172: iconst_0
    //   173: aload 5
    //   175: invokevirtual 216	java/lang/reflect/Field:getName	()Ljava/lang/String;
    //   178: aastore
    //   179: aload 21
    //   181: ifnonnull +69 -> 250
    //   184: ldc 218
    //   186: astore 23
    //   188: aload 22
    //   190: iconst_1
    //   191: aload 23
    //   193: aastore
    //   194: aload_1
    //   195: ldc 220
    //   197: aload 22
    //   199: invokestatic 224	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   202: invokevirtual 180	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   205: pop
    //   206: iload 9
    //   208: ifne +36 -> 244
    //   211: iconst_2
    //   212: anewarray 4	java/lang/Object
    //   215: astore 10
    //   217: aload 10
    //   219: iconst_0
    //   220: aload 5
    //   222: invokevirtual 216	java/lang/reflect/Field:getName	()Ljava/lang/String;
    //   225: aastore
    //   226: aload 10
    //   228: iconst_1
    //   229: ldc 226
    //   231: aastore
    //   232: aload_1
    //   233: ldc 220
    //   235: aload 10
    //   237: invokestatic 224	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   240: invokevirtual 180	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   243: pop
    //   244: iinc 4 1
    //   247: goto -158 -> 89
    //   250: aload 21
    //   252: invokevirtual 164	java/lang/Object:toString	()Ljava/lang/String;
    //   255: astore 23
    //   257: goto -69 -> 188
    //   260: astore 17
    //   262: iconst_2
    //   263: anewarray 4	java/lang/Object
    //   266: astore 18
    //   268: aload 18
    //   270: iconst_0
    //   271: aload 5
    //   273: invokevirtual 216	java/lang/reflect/Field:getName	()Ljava/lang/String;
    //   276: aastore
    //   277: aload 17
    //   279: ifnonnull +28 -> 307
    //   282: ldc 218
    //   284: astore 19
    //   286: aload 18
    //   288: iconst_1
    //   289: aload 19
    //   291: aastore
    //   292: aload_1
    //   293: ldc 220
    //   295: aload 18
    //   297: invokestatic 224	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   300: invokevirtual 180	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   303: pop
    //   304: goto -98 -> 206
    //   307: aload 17
    //   309: invokevirtual 164	java/lang/Object:toString	()Ljava/lang/String;
    //   312: astore 19
    //   314: goto -28 -> 286
    //   317: astore 13
    //   319: iconst_2
    //   320: anewarray 4	java/lang/Object
    //   323: astore 14
    //   325: aload 14
    //   327: iconst_0
    //   328: aload 5
    //   330: invokevirtual 216	java/lang/reflect/Field:getName	()Ljava/lang/String;
    //   333: aastore
    //   334: iconst_0
    //   335: ifne +28 -> 363
    //   338: ldc 218
    //   340: astore 15
    //   342: aload 14
    //   344: iconst_1
    //   345: aload 15
    //   347: aastore
    //   348: aload_1
    //   349: ldc 220
    //   351: aload 14
    //   353: invokestatic 224	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   356: invokevirtual 180	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   359: pop
    //   360: goto -154 -> 206
    //   363: aconst_null
    //   364: invokevirtual 164	java/lang/Object:toString	()Ljava/lang/String;
    //   367: astore 15
    //   369: goto -27 -> 342
    //   372: iinc 8 1
    //   375: goto -255 -> 120
    //   378: aload_1
    //   379: bipush 254
    //   381: aload_1
    //   382: invokevirtual 230	java/lang/StringBuilder:length	()I
    //   385: iadd
    //   386: iconst_m1
    //   387: aload_1
    //   388: invokevirtual 230	java/lang/StringBuilder:length	()I
    //   391: iadd
    //   392: ldc 232
    //   394: invokevirtual 236	java/lang/StringBuilder:replace	(IILjava/lang/String;)Ljava/lang/StringBuilder;
    //   397: invokevirtual 183	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   400: areturn
    //   401: aload_0
    //   402: invokevirtual 164	java/lang/Object:toString	()Ljava/lang/String;
    //   405: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   156	164	260	java/lang/IllegalAccessException
    //   156	164	317	finally
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xulapp.utils.XulSystemUtil
 * JD-Core Version:    0.6.2
 */