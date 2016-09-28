package com.sohu.upload.a;

import android.content.Context;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class b
{
  private static File a = null;
  private static ArrayList<File> b = new ArrayList();
  private static Context c;

  private static File a(String paramString)
  {
    int i = 0;
    try
    {
      while (i < b.size())
      {
        File localFile = (File)b.get(i);
        if (localFile != null)
        {
          boolean bool = paramString.equals(localFile.getName());
          if (bool)
            return localFile;
        }
        i++;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private static String a()
  {
    return new SimpleDateFormat("yyyy-MM-dd").format(Long.valueOf(System.currentTimeMillis()));
  }

  public static void a(Context paramContext)
  {
    c = paramContext;
  }

  public static void a(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      String str1 = a() + ".txt";
      a = a(str1);
      if (a == null)
      {
        a = new File(c.getExternalFilesDir("LOG"), str1);
        if (b.size() > 20)
        {
          File localFile = (File)b.get(0);
          if ((localFile != null) && (localFile.exists()) && (localFile.delete()))
            b.remove(0);
        }
        b.add(a);
      }
      if (!a.exists())
        a.createNewFile();
      String str2 = new Date().toLocaleString();
      FileWriter localFileWriter = new FileWriter(a, true);
      localFileWriter.write(str2 + " " + paramString1 + "/" + paramString2 + ": " + paramString3);
      localFileWriter.write("\r\n");
      localFileWriter.close();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.upload.a.b
 * JD-Core Version:    0.6.2
 */