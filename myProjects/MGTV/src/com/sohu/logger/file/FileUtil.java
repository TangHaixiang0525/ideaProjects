package com.sohu.logger.file;

import android.content.Context;
import android.os.Handler;
import java.io.File;

public class FileUtil
{
  public static boolean deleteFile(File paramFile, int paramInt)
  {
    for (int i = 0; ; i++)
    {
      boolean bool = false;
      if (i < paramInt)
      {
        if (paramFile.delete())
          bool = true;
      }
      else
        return bool;
    }
  }

  public static String getLogDir(Context paramContext)
  {
    return paramContext.getFilesDir() + File.separator + "logger";
  }

  public static boolean makesureCreateFile(File paramFile)
  {
    if (paramFile == null)
      return false;
    if (paramFile.exists())
      return true;
    File localFile = paramFile.getParentFile();
    if ((localFile != null) && (!localFile.exists()))
      localFile.mkdirs();
    return paramFile.createNewFile();
  }

  public static void saveInfoToFile(Context paramContext, Handler paramHandler, String paramString)
  {
    if (paramHandler != null)
      paramHandler.post(new SaveInfo(paramContext, paramString));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.file.FileUtil
 * JD-Core Version:    0.6.2
 */