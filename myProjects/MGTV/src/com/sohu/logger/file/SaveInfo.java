package com.sohu.logger.file;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveInfo extends Thread
{
  public Context context;
  public String jsonString;

  public SaveInfo(Context paramContext, String paramString)
  {
    this.jsonString = paramString;
    this.context = paramContext;
  }

  public SaveInfo(String paramString)
  {
  }

  public void run()
  {
    super.run();
    if (this.jsonString == null);
    while (true)
    {
      return;
      try
      {
        File localFile = new File(FileUtil.getLogDir(this.context) + File.separator + System.currentTimeMillis() + ".log");
        if (FileUtil.makesureCreateFile(localFile))
        {
          FileOutputStream localFileOutputStream = new FileOutputStream(localFile, false);
          localFileOutputStream.write(this.jsonString.getBytes());
          localFileOutputStream.flush();
          localFileOutputStream.close();
          return;
        }
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.file.SaveInfo
 * JD-Core Version:    0.6.2
 */