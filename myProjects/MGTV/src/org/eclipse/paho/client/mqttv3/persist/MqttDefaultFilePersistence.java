package org.eclipse.paho.client.mqttv3.persist;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.internal.FileLock;
import org.eclipse.paho.client.mqttv3.internal.MqttPersistentData;

public class MqttDefaultFilePersistence
  implements MqttClientPersistence
{
  private static final FilenameFilter FILE_FILTER = new FilenameFilter()
  {
    public boolean accept(File paramAnonymousFile, String paramAnonymousString)
    {
      return paramAnonymousString.endsWith(".msg");
    }
  };
  private static final String LOCK_FILENAME = ".lck";
  private static final String MESSAGE_BACKUP_FILE_EXTENSION = ".bup";
  private static final String MESSAGE_FILE_EXTENSION = ".msg";
  private File clientDir = null;
  private File dataDir;
  private FileLock fileLock = null;

  public MqttDefaultFilePersistence()
  {
    this(System.getProperty("user.dir"));
  }

  public MqttDefaultFilePersistence(String paramString)
  {
    this.dataDir = new File(paramString);
  }

  private void checkIsOpen()
    throws MqttPersistenceException
  {
    if (this.clientDir == null)
      throw new MqttPersistenceException();
  }

  private File[] getFiles()
    throws MqttPersistenceException
  {
    checkIsOpen();
    File[] arrayOfFile = this.clientDir.listFiles(FILE_FILTER);
    if (arrayOfFile == null)
      throw new MqttPersistenceException();
    return arrayOfFile;
  }

  private boolean isSafeChar(char paramChar)
  {
    return (Character.isJavaIdentifierPart(paramChar)) || (paramChar == '-');
  }

  private void restoreBackups(File paramFile)
    throws MqttPersistenceException
  {
    File[] arrayOfFile = paramFile.listFiles(new FileFilter()
    {
      public boolean accept(File paramAnonymousFile)
      {
        return paramAnonymousFile.getName().endsWith(".bup");
      }
    });
    if (arrayOfFile == null)
      throw new MqttPersistenceException();
    for (int i = 0; i < arrayOfFile.length; i++)
    {
      File localFile = new File(paramFile, arrayOfFile[i].getName().substring(0, arrayOfFile[i].getName().length() - ".bup".length()));
      if (!arrayOfFile[i].renameTo(localFile))
      {
        localFile.delete();
        arrayOfFile[i].renameTo(localFile);
      }
    }
  }

  public void clear()
    throws MqttPersistenceException
  {
    checkIsOpen();
    File[] arrayOfFile = getFiles();
    for (int i = 0; i < arrayOfFile.length; i++)
      arrayOfFile[i].delete();
  }

  public void close()
    throws MqttPersistenceException
  {
    try
    {
      if (this.fileLock != null)
        this.fileLock.release();
      if (getFiles().length == 0)
        this.clientDir.delete();
      this.clientDir = null;
      return;
    }
    finally
    {
    }
  }

  public boolean containsKey(String paramString)
    throws MqttPersistenceException
  {
    checkIsOpen();
    return new File(this.clientDir, paramString + ".msg").exists();
  }

  public MqttPersistable get(String paramString)
    throws MqttPersistenceException
  {
    checkIsOpen();
    try
    {
      FileInputStream localFileInputStream = new FileInputStream(new File(this.clientDir, paramString + ".msg"));
      int i = localFileInputStream.available();
      byte[] arrayOfByte = new byte[i];
      int j = 0;
      while (j < i)
        j += localFileInputStream.read(arrayOfByte, j, i - j);
      localFileInputStream.close();
      MqttPersistentData localMqttPersistentData = new MqttPersistentData(paramString, arrayOfByte, 0, arrayOfByte.length, null, 0, 0);
      return localMqttPersistentData;
    }
    catch (IOException localIOException)
    {
      throw new MqttPersistenceException(localIOException);
    }
  }

  public Enumeration keys()
    throws MqttPersistenceException
  {
    checkIsOpen();
    File[] arrayOfFile = getFiles();
    Vector localVector = new Vector(arrayOfFile.length);
    for (int i = 0; i < arrayOfFile.length; i++)
    {
      String str = arrayOfFile[i].getName();
      localVector.addElement(str.substring(0, str.length() - ".msg".length()));
    }
    return localVector.elements();
  }

  public void open(String paramString1, String paramString2)
    throws MqttPersistenceException
  {
    if ((this.dataDir.exists()) && (!this.dataDir.isDirectory()))
      throw new MqttPersistenceException();
    if ((!this.dataDir.exists()) && (!this.dataDir.mkdirs()))
      throw new MqttPersistenceException();
    if (!this.dataDir.canWrite())
      throw new MqttPersistenceException();
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramString1.length(); i++)
    {
      char c2 = paramString1.charAt(i);
      if (isSafeChar(c2))
        localStringBuffer.append(c2);
    }
    localStringBuffer.append("-");
    for (int j = 0; j < paramString2.length(); j++)
    {
      char c1 = paramString2.charAt(j);
      if (isSafeChar(c1))
        localStringBuffer.append(c1);
    }
    try
    {
      if (this.clientDir == null)
      {
        String str = localStringBuffer.toString();
        this.clientDir = new File(this.dataDir, str);
        if (!this.clientDir.exists())
          this.clientDir.mkdir();
      }
      try
      {
        this.fileLock = new FileLock(this.clientDir, ".lck");
        restoreBackups(this.clientDir);
        return;
      }
      catch (Exception localException)
      {
        throw new MqttPersistenceException(32200);
      }
    }
    finally
    {
    }
  }

  public void put(String paramString, MqttPersistable paramMqttPersistable)
    throws MqttPersistenceException
  {
    checkIsOpen();
    File localFile1 = new File(this.clientDir, paramString + ".msg");
    File localFile2 = new File(this.clientDir, paramString + ".msg" + ".bup");
    if ((localFile1.exists()) && (!localFile1.renameTo(localFile2)))
    {
      localFile2.delete();
      localFile1.renameTo(localFile2);
    }
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(localFile1);
      localFileOutputStream.write(paramMqttPersistable.getHeaderBytes(), paramMqttPersistable.getHeaderOffset(), paramMqttPersistable.getHeaderLength());
      if (paramMqttPersistable.getPayloadBytes() != null)
        localFileOutputStream.write(paramMqttPersistable.getPayloadBytes(), paramMqttPersistable.getPayloadOffset(), paramMqttPersistable.getPayloadLength());
      localFileOutputStream.getFD().sync();
      localFileOutputStream.close();
      if (localFile2.exists())
        localFile2.delete();
      return;
    }
    catch (IOException localIOException)
    {
      throw new MqttPersistenceException(localIOException);
    }
    finally
    {
      if ((localFile2.exists()) && (!localFile2.renameTo(localFile1)))
      {
        localFile1.delete();
        localFile2.renameTo(localFile1);
      }
    }
  }

  public void remove(String paramString)
    throws MqttPersistenceException
  {
    checkIsOpen();
    File localFile = new File(this.clientDir, paramString + ".msg");
    if (localFile.exists())
      localFile.delete();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence
 * JD-Core Version:    0.6.2
 */