package com.starcor.core.utils;

import android.text.TextUtils;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOTools
{
  private static final String TAG = "IOTools";

  public static boolean deletePathFilesOldFile(String paramString, long paramLong)
  {
    if (TextUtils.isEmpty(paramString))
      return false;
    try
    {
      File[] arrayOfFile = new File(paramString).listFiles();
      if (arrayOfFile == null)
        return true;
      Logger.i("IOTools", "deletePathFilesOldFile path:" + paramString + ", count:" + arrayOfFile.length);
      int i = arrayOfFile.length;
      for (int j = 0; j < i; j++)
      {
        File localFile = arrayOfFile[j];
        if ((localFile != null) && (localFile.lastModified() < paramLong))
        {
          Logger.i("IOTools", "deletePathFilesOldFile file:" + localFile.getAbsolutePath());
          localFile.delete();
        }
      }
      return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public static boolean deletePathFilesifMore(String paramString, long paramLong)
  {
    if (TextUtils.isEmpty(paramString))
      return false;
    try
    {
      File[] arrayOfFile = new File(paramString).listFiles();
      if (arrayOfFile == null)
        return true;
      Logger.i("IOTools", "deletePathFilesifMore path:" + paramString + ", count:" + arrayOfFile.length);
      if (arrayOfFile.length < paramLong)
        return true;
      int i = arrayOfFile.length;
      for (int j = 0; j < i; j++)
      {
        File localFile = arrayOfFile[j];
        if (localFile != null)
        {
          Logger.i("IOTools", "deletePathFilesifMore file:" + localFile.getAbsolutePath());
          localFile.delete();
        }
      }
      return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public static Object readObject(File paramFile)
  {
    try
    {
      ObjectInputStream localObjectInputStream = new ObjectInputStream(new FileInputStream(paramFile));
      Object localObject = localObjectInputStream.readObject();
      localObjectInputStream.close();
      return localObject;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static boolean writeObject(File paramFile, Object paramObject)
  {
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
      ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localFileOutputStream);
      localObjectOutputStream.writeObject(paramObject);
      localObjectOutputStream.flush();
      localFileOutputStream.getFD().sync();
      localObjectOutputStream.close();
      return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.utils.IOTools
 * JD-Core Version:    0.6.2
 */