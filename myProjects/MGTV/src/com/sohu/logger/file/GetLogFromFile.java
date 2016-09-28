package com.sohu.logger.file;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sohu.logger.bean.LogItem;
import com.sohu.logger.log.OutputLog;
import com.sohu.logger.util.NetUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class GetLogFromFile
{
  public Context context;

  public GetLogFromFile(Context paramContext)
  {
    this.context = paramContext;
  }

  private File getFileToSend()
  {
    File localFile = new File(FileUtil.getLogDir(this.context));
    boolean bool = localFile.exists();
    Object localObject1 = null;
    if (!bool);
    File[] arrayOfFile;
    int i;
    do
    {
      do
      {
        return localObject1;
        arrayOfFile = localFile.listFiles();
        localObject1 = null;
      }
      while (arrayOfFile == null);
      i = arrayOfFile.length;
      localObject1 = null;
    }
    while (i == 0);
    long l1 = 0L;
    int j = arrayOfFile.length;
    int k = 0;
    label64: Object localObject2;
    String str;
    long l2;
    if (k < j)
    {
      localObject2 = arrayOfFile[k];
      try
      {
        str = ((File)localObject2).getName();
        if (TextUtils.isEmpty(str))
          throw new RuntimeException("unexpected empty filename");
      }
      catch (Exception localException)
      {
        ((File)localObject2).delete();
        localObject2 = localObject1;
        l2 = l1;
      }
    }
    while (true)
    {
      k++;
      l1 = l2;
      localObject1 = localObject2;
      break label64;
      break;
      int m = str.lastIndexOf(".");
      if (m <= 0)
        throw new RuntimeException("unexpected filename, no suffix");
      long l3 = Long.parseLong(str.substring(0, m));
      l2 = l3;
      if (l2 <= l1)
      {
        localObject2 = localObject1;
        l2 = l1;
      }
    }
  }

  public LogItem readLogItemsFromFile(File paramFile)
  {
    Gson localGson = new Gson();
    FileReader localFileReader = new FileReader(paramFile);
    try
    {
      LogItem localLogItem = (LogItem)localGson.fromJson(localFileReader, new TypeToken()
      {
      }
      .getType());
      return localLogItem;
    }
    finally
    {
      localFileReader.close();
    }
  }

  public void sendLog()
  {
    while (true)
    {
      File localFile = getFileToSend();
      if (localFile == null)
      {
        label9: OutputLog.d("SohuLoggerAgent", "send file end.......................");
        return;
      }
      LogItem localLogItem;
      try
      {
        if (!localFile.exists())
          break label9;
        OutputLog.d("SohuLoggerAgent", "                       send file:  " + localFile.getName());
        localLogItem = readLogItemsFromFile(localFile);
        if (!localLogItem.isDeleted())
          break label87;
        Thread.sleep(120000L);
        FileUtil.deleteFile(localFile, 3);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      continue;
      label87: localLogItem.setParamsMapItem("isonline", "off");
      if ((NetUtils.Post(this.context, localLogItem.getRetryNum(), localLogItem.toUrl())) && (!FileUtil.deleteFile(localFile, 3)))
      {
        localLogItem.setDeleted(true);
        if (FileUtil.makesureCreateFile(localFile))
        {
          FileOutputStream localFileOutputStream = new FileOutputStream(localFile, false);
          localFileOutputStream.write(new Gson().toJson(localLogItem).getBytes());
          localFileOutputStream.flush();
          localFileOutputStream.close();
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.file.GetLogFromFile
 * JD-Core Version:    0.6.2
 */