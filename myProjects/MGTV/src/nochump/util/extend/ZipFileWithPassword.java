package nochump.util.extend;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import nochump.util.zip.EncryptZipEntry;
import nochump.util.zip.EncryptZipInput;
import nochump.util.zip.EncryptZipOutput;

public class ZipFileWithPassword
{
  public static int DecryptZipFile(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      unzipFilesWithPassword(FileUtils.readFileByte(paramString1), paramString2 + File.separator, paramString3);
      return 1;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return 0;
  }

  public static int EncryptZipFile(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      File localFile = new File(paramString2);
      if (localFile.exists())
        localFile.delete();
      zipFileWithPassword(paramString1, paramString2, paramString3);
      return 1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }

  public static void unzipFilesWithPassword(byte[] paramArrayOfByte, String paramString1, String paramString2)
    throws IOException
  {
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
    EncryptZipInput localEncryptZipInput = new EncryptZipInput(localByteArrayInputStream, paramString2);
    EncryptZipEntry localEncryptZipEntry;
    while (true)
    {
      localEncryptZipEntry = localEncryptZipInput.getNextEntry();
      if (localEncryptZipEntry == null)
      {
        localEncryptZipInput.close();
        localByteArrayInputStream.close();
        return;
      }
      if (!localEncryptZipEntry.isDirectory())
        break;
      File localFile1 = new File(paramString1 + localEncryptZipEntry.getName());
      if (!localFile1.exists())
        localFile1.mkdirs();
    }
    File localFile2 = new File(paramString1 + localEncryptZipEntry.getName());
    if (!localFile2.getParentFile().exists())
      localFile2.getParentFile().mkdirs();
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte1 = new byte[1024];
    while (true)
    {
      int i = localEncryptZipInput.read(arrayOfByte1);
      if (i <= 0)
      {
        byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
        localByteArrayOutputStream.close();
        FileUtils.writeByteFile(arrayOfByte2, new File(paramString1 + localEncryptZipEntry.getName()));
        break;
      }
      localByteArrayOutputStream.write(arrayOfByte1, 0, i);
    }
  }

  public static void zipFileWithPassword(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      EncryptZipOutput localEncryptZipOutput = new EncryptZipOutput(new BufferedOutputStream(new FileOutputStream(paramString2)), paramString3);
      zipFiles(paramString1, localEncryptZipOutput, "");
      localEncryptZipOutput.close();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public static void zipFiles(String paramString1, EncryptZipOutput paramEncryptZipOutput, String paramString2)
    throws IOException
  {
    int i = 0;
    File localFile = new File(paramString1);
    if (localFile.isDirectory())
    {
      if (localFile.listFiles().length == 0)
      {
        paramEncryptZipOutput.putNextEntry(new EncryptZipEntry(paramString2 + localFile.getName() + "/"));
        paramEncryptZipOutput.closeEntry();
        return;
      }
      if (paramString2 == "");
      for (String str = paramString2 + File.separator; ; str = paramString2 + localFile.getName() + File.separator)
      {
        File[] arrayOfFile = localFile.listFiles();
        int k = arrayOfFile.length;
        while (i < k)
        {
          zipFiles(arrayOfFile[i].getAbsolutePath(), paramEncryptZipOutput, str);
          i++;
        }
        break;
      }
    }
    FileInputStream localFileInputStream = new FileInputStream(localFile);
    paramEncryptZipOutput.putNextEntry(new EncryptZipEntry(paramString2 + localFile.getName()));
    byte[] arrayOfByte = new byte[1024];
    while (true)
    {
      int j = localFileInputStream.read(arrayOfByte);
      if (j <= 0)
      {
        paramEncryptZipOutput.closeEntry();
        localFileInputStream.close();
        return;
      }
      paramEncryptZipOutput.write(arrayOfByte, 0, j);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     nochump.util.extend.ZipFileWithPassword
 * JD-Core Version:    0.6.2
 */