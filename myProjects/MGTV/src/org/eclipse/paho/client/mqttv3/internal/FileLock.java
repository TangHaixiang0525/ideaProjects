package org.eclipse.paho.client.mqttv3.internal;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;

public class FileLock
{
  private RandomAccessFile file;
  private Object fileLock;
  private File lockFile;

  public FileLock(File paramFile, String paramString)
    throws Exception
  {
    this.lockFile = new File(paramFile, paramString);
    if (ExceptionHelper.isClassAvailable("java.nio.channels.FileLock"))
      try
      {
        this.file = new RandomAccessFile(this.lockFile, "rw");
        Object localObject = this.file.getClass().getMethod("getChannel", new Class[0]).invoke(this.file, new Object[0]);
        this.fileLock = localObject.getClass().getMethod("tryLock", new Class[0]).invoke(localObject, new Object[0]);
        if (this.fileLock == null)
        {
          release();
          throw new Exception("Problem obtaining file lock");
        }
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        while (true)
          this.fileLock = null;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        while (true)
          this.fileLock = null;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        while (true)
          this.fileLock = null;
      }
  }

  public void release()
  {
    try
    {
      if (this.fileLock != null)
      {
        this.fileLock.getClass().getMethod("release", new Class[0]).invoke(this.fileLock, new Object[0]);
        this.fileLock = null;
      }
      label40: if (this.file != null);
      try
      {
        this.file.close();
        label54: this.file = null;
        if ((this.lockFile != null) && (this.lockFile.exists()))
          this.lockFile.delete();
        this.lockFile = null;
        return;
      }
      catch (IOException localIOException)
      {
        break label54;
      }
    }
    catch (Exception localException)
    {
      break label40;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.FileLock
 * JD-Core Version:    0.6.2
 */