package com.starcor.hunan.opendownload.logupload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import nochump.util.zip.EncryptZipEntry;
import nochump.util.zip.EncryptZipOutput;

public class ZIPFileUtils
{
  private boolean isClose = true;
  private boolean isReady;
  private EncryptZipOutput out;
  private String password;
  private String zipPath;

  public ZIPFileUtils(String paramString1, String paramString2)
  {
    this.zipPath = paramString1;
    this.password = paramString2;
  }

  private EncryptZipOutput startZip()
  {
    try
    {
      localEncryptZipOutput = new EncryptZipOutput(new BufferedOutputStream(new FileOutputStream(this.zipPath)), this.password);
      this.isReady = true;
      this.isClose = false;
      return localEncryptZipOutput;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      while (true)
      {
        localFileNotFoundException.printStackTrace();
        EncryptZipOutput localEncryptZipOutput = null;
      }
    }
    finally
    {
    }
  }

  public void closeZip()
  {
    try
    {
      this.out.close();
      this.isClose = true;
      this.isReady = false;
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public boolean putEntry(String paramString)
  {
    try
    {
      File localFile = new File(paramString);
      if ((localFile.exists()) && (localFile.isFile()))
      {
        boolean bool2 = putEntry(localFile.getName(), new FileInputStream(localFile));
        bool1 = bool2;
        return bool1;
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      while (true)
      {
        localFileNotFoundException.printStackTrace();
        boolean bool1 = false;
      }
    }
    finally
    {
    }
  }

  public boolean putEntry(String paramString, InputStream paramInputStream)
  {
    boolean bool = false;
    try
    {
      if (!this.isReady)
        this.out = startZip();
      while (true)
      {
        try
        {
          EncryptZipEntry localEncryptZipEntry = new EncryptZipEntry(paramString);
          this.out.putNextEntry(localEncryptZipEntry);
          byte[] arrayOfByte = new byte[2048];
          int i = paramInputStream.read(arrayOfByte);
          if (i > 0)
          {
            this.out.write(arrayOfByte, 0, i);
            continue;
          }
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
          return bool;
        }
        this.out.closeEntry();
        paramInputStream.close();
        bool = true;
      }
    }
    finally
    {
    }
  }

  public void reSetZipFile(String paramString1, String paramString2)
  {
    if (!this.isClose)
      throw new IllegalStateException("还有未完成的ZIP文件。");
    this.zipPath = paramString1;
    this.password = paramString2;
    this.out = startZip();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.opendownload.logupload.ZIPFileUtils
 * JD-Core Version:    0.6.2
 */