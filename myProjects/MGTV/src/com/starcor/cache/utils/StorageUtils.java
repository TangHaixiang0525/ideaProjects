package com.starcor.cache.utils;

import android.content.Context;
import android.os.Environment;
import com.starcor.utils.Logger;
import java.io.File;
import java.io.IOException;

public final class StorageUtils
{
  private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
  private static final String INDIVIDUAL_DIR_NAME = "uil-images";

  public static File getCacheDirectory(Context paramContext)
  {
    return getCacheDirectory(paramContext, true);
  }

  public static File getCacheDirectory(Context paramContext, boolean paramBoolean)
  {
    try
    {
      String str3 = Environment.getExternalStorageState();
      str1 = str3;
      File localFile = null;
      if (paramBoolean)
      {
        boolean bool1 = "mounted".equals(str1);
        localFile = null;
        if (bool1)
        {
          boolean bool2 = hasExternalStoragePermission(paramContext);
          localFile = null;
          if (bool2)
            localFile = getExternalCacheDir(paramContext);
        }
      }
      if (localFile == null)
        localFile = paramContext.getCacheDir();
      if (localFile == null)
      {
        String str2 = "/data/data/" + paramContext.getPackageName() + "/cache/";
        Logger.d("", String.format("Can't define system cache directory! '%s' will be used.", new Object[] { str2 }));
        localFile = new File(str2);
      }
      return localFile;
    }
    catch (NullPointerException localNullPointerException)
    {
      while (true)
        str1 = "";
    }
    catch (IncompatibleClassChangeError localIncompatibleClassChangeError)
    {
      while (true)
        String str1 = "";
    }
  }

  private static File getExternalCacheDir(Context paramContext)
  {
    File localFile = new File(new File(new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data"), paramContext.getPackageName()), "cache");
    if (!localFile.exists())
    {
      if (!localFile.mkdirs())
      {
        Logger.d("", "Unable to create external cache directory");
        localFile = null;
      }
    }
    else
      return localFile;
    try
    {
      new File(localFile, ".nomedia").createNewFile();
      return localFile;
    }
    catch (IOException localIOException)
    {
      Logger.d("", "Can't create \".nomedia\" file in application external cache directory");
    }
    return localFile;
  }

  public static File getIndividualCacheDirectory(Context paramContext)
  {
    return getIndividualCacheDirectory(paramContext, "uil-images");
  }

  public static File getIndividualCacheDirectory(Context paramContext, String paramString)
  {
    File localFile1 = getCacheDirectory(paramContext);
    File localFile2 = new File(localFile1, paramString);
    if ((!localFile2.exists()) && (!localFile2.mkdir()))
      localFile2 = localFile1;
    return localFile2;
  }

  public static File getOwnCacheDirectory(Context paramContext, String paramString)
  {
    boolean bool1 = "mounted".equals(Environment.getExternalStorageState());
    File localFile = null;
    if (bool1)
    {
      boolean bool2 = hasExternalStoragePermission(paramContext);
      localFile = null;
      if (bool2)
        localFile = new File(Environment.getExternalStorageDirectory(), paramString);
    }
    if ((localFile == null) || ((!localFile.exists()) && (!localFile.mkdirs())))
      localFile = paramContext.getCacheDir();
    return localFile;
  }

  public static File getOwnCacheDirectory(Context paramContext, String paramString, boolean paramBoolean)
  {
    File localFile = null;
    if (paramBoolean)
    {
      boolean bool1 = "mounted".equals(Environment.getExternalStorageState());
      localFile = null;
      if (bool1)
      {
        boolean bool2 = hasExternalStoragePermission(paramContext);
        localFile = null;
        if (bool2)
          localFile = new File(Environment.getExternalStorageDirectory(), paramString);
      }
    }
    if ((localFile == null) || ((!localFile.exists()) && (!localFile.mkdirs())))
      localFile = paramContext.getCacheDir();
    return localFile;
  }

  private static boolean hasExternalStoragePermission(Context paramContext)
  {
    return paramContext.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.cache.utils.StorageUtils
 * JD-Core Version:    0.6.2
 */