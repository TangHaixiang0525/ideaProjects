package com.starcor.core.service;

import android.content.Context;
import android.text.TextUtils;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TempFileManager
{
  private static int MAXIMUM = 10485760;
  protected static final String TAG = TempFileManager.class.getSimpleName();
  private static final TempFileManager mTempFileManager = new TempFileManager();
  private List<File> files = new ArrayList();
  private boolean isInit;
  ExecutorService pool = Executors.newSingleThreadExecutor();
  private long usedSize = 0L;

  private void choiceSort()
  {
    File[] arrayOfFile = (File[])this.files.toArray(new File[this.files.size()]);
    if ((arrayOfFile == null) || (arrayOfFile.length <= 0));
    while (true)
    {
      return;
      for (int i = 0; i < arrayOfFile.length; i++)
      {
        int k = i;
        for (int m = i + 1; m < arrayOfFile.length; m++)
          if (arrayOfFile[k].lastModified() > arrayOfFile[m].lastModified())
            k = m;
        if (i != k)
        {
          File localFile = arrayOfFile[k];
          arrayOfFile[k] = arrayOfFile[i];
          arrayOfFile[i] = localFile;
        }
      }
      this.files.clear();
      for (int j = 0; j < arrayOfFile.length; j++)
        this.files.add(arrayOfFile[j]);
    }
  }

  private void clearTemFile()
  {
    if (this.files.size() == 0)
      return;
    int i = -1 + this.files.size();
    label25: if (i >= 0)
    {
      if (this.usedSize + ((File)this.files.get(i)).length() >= MAXIMUM)
        break label89;
      this.usedSize += ((File)this.files.get(i)).length();
    }
    while (true)
    {
      i--;
      break label25;
      break;
      label89: final File localFile = (File)this.files.remove(i);
      this.pool.execute(new Runnable()
      {
        public void run()
        {
          Logger.i(TempFileManager.TAG, "delete a file usedSize:" + TempFileManager.this.usedSize + "/" + TempFileManager.MAXIMUM);
          localFile.delete();
        }
      });
    }
  }

  private void getFiles(File paramFile)
  {
    if ((paramFile == null) || (!paramFile.exists()));
    while (true)
    {
      return;
      if (paramFile.isFile())
      {
        this.files.add(paramFile);
        return;
      }
      File[] arrayOfFile = paramFile.listFiles();
      int i = arrayOfFile.length;
      for (int j = 0; j < i; j++)
        getFiles(arrayOfFile[j]);
    }
  }

  public static TempFileManager getInstance()
  {
    return mTempFileManager;
  }

  public void addTempFile(File paramFile)
  {
  }

  public void destory()
  {
    if (!this.isInit)
    {
      Logger.i(TAG, "destory uninit");
      return;
    }
    this.pool.shutdown();
    this.pool = null;
  }

  public void init(int paramInt)
  {
    this.isInit = true;
    this.files.clear();
    MAXIMUM = 1024 * (paramInt * 1024);
    String str = GlobalEnv.getInstance().getPicCachePath();
    if (!TextUtils.isEmpty(str))
      getFiles(new File(str));
    while (true)
    {
      getFiles(App.getAppContext().getDir("cached_pic", 0));
      choiceSort();
      Logger.i(TAG, "init maximum = " + MAXIMUM + ",files = " + this.files.size());
      clearTemFile();
      return;
      Logger.i(TAG, "init path is null");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.service.TempFileManager
 * JD-Core Version:    0.6.2
 */