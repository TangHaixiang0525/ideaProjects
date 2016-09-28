package com.starcor.core.domain;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.zip.ZipInputStream;

public class UiLayoutItem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String CDATA;
  public String ex_url;
  public String flag;
  public String format;
  public String id;
  public String local_url;
  public String md5;
  public String modify_time;
  public String name;
  public String type;

  private static byte[] unZipInputStream(InputStream paramInputStream)
    throws IOException
  {
    ZipInputStream localZipInputStream = new ZipInputStream(new BufferedInputStream(paramInputStream));
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      if (localZipInputStream.getNextEntry() != null)
      {
        byte[] arrayOfByte2 = new byte[1024];
        while (true)
        {
          int i = localZipInputStream.read(arrayOfByte2);
          if (i == -1)
            break;
          localByteArrayOutputStream.write(arrayOfByte2, 0, i);
        }
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      if (localByteArrayOutputStream != null)
        localByteArrayOutputStream.close();
      Object localObject2 = null;
      if (localZipInputStream != null)
        localZipInputStream.close();
      return localObject2;
      byte[] arrayOfByte1 = localByteArrayOutputStream.toByteArray();
      localObject2 = arrayOfByte1;
      return localObject2;
    }
    finally
    {
      if (localByteArrayOutputStream != null)
        localByteArrayOutputStream.close();
      if (localZipInputStream != null)
        localZipInputStream.close();
    }
  }

  public boolean equals(Object paramObject)
  {
    try
    {
      UiLayoutItem localUiLayoutItem = (UiLayoutItem)paramObject;
      boolean bool1 = this.id.equals(localUiLayoutItem.id);
      boolean bool2 = false;
      if (bool1)
        bool2 = true;
      return bool2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public String getCDataByString()
  {
    if ("zip".equalsIgnoreCase(this.format))
    {
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(this.CDATA.getBytes());
      try
      {
        str = new String(unZipInputStream(localByteArrayInputStream));
        return str;
      }
      catch (IOException localIOException)
      {
        while (true)
        {
          localIOException.printStackTrace();
          String str = null;
        }
      }
    }
    return this.CDATA;
  }

  public String toString()
  {
    return "UiLayoutItem:[id: " + this.id + ", name:" + this.name + ", modify_time:" + this.modify_time + ", flag:" + this.flag + ", type:" + this.type + ",ex_url:" + this.ex_url + ", format:" + this.format + ", md5:" + this.md5 + ", local_url:" + this.local_url + ", CDATA:" + this.CDATA + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.UiLayoutItem
 * JD-Core Version:    0.6.2
 */