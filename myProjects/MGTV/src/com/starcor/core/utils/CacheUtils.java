package com.starcor.core.utils;

import android.text.TextUtils;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.hunan.widget.FilmListView;
import java.io.File;

public class CacheUtils
{
  private static final String TAG = CacheUtils.class.getSimpleName();

  public static void clearMetaData()
  {
    deleteFiles(GlobalEnv.getInstance().getConfigPath() + File.separator + "InitMetaData.dat");
  }

  public static void clearPic()
  {
    deleteFiles(GlobalEnv.getInstance().getPicCachePath());
    deleteFiles(FilmListView.getPicCachePath());
  }

  private static void deleteFiles(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    while (true)
    {
      int j;
      try
      {
        File localFile1 = new File(paramString);
        if (localFile1.isDirectory())
        {
          Logger.d(TAG, "deleteFiles isDirectory path:" + paramString);
          File[] arrayOfFile = localFile1.listFiles();
          if (arrayOfFile == null)
            break;
          int i = arrayOfFile.length;
          j = 0;
          if (j >= i)
            break;
          File localFile2 = arrayOfFile[j];
          if (localFile2 == null)
            break label133;
          localFile2.delete();
          break label133;
        }
        Logger.d(TAG, "deleteFiles file path:" + paramString);
        localFile1.delete();
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      label133: j++;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.utils.CacheUtils
 * JD-Core Version:    0.6.2
 */