package com.starcor.utils;

import android.text.TextUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SvnReader
{
  private static final String TAG = SvnReader.class.getName();
  private static SvnReader sTools;

  public static SvnReader instance()
  {
    if (sTools == null)
      sTools = new SvnReader();
    return sTools;
  }

  private ArrayList<String> readContents(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    ArrayList localArrayList = null;
    Process localProcess = null;
    String str = "svn cat " + paramString1;
    try
    {
      localProcess = Runtime.getRuntime().exec(str);
      InputStream localInputStream1 = localProcess.getInputStream();
      InputStream localInputStream2 = localProcess.getErrorStream();
      localArrayList = LineMatcher.instance().getContents(localInputStream1, localInputStream2, paramString2, paramString3, paramString4);
      int i = localProcess.exitValue();
      if (i == 0);
      while (true)
        return localArrayList;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      return localArrayList;
    }
    finally
    {
      if (localProcess != null)
        localProcess.destroy();
    }
  }

  private String readString(String paramString1, String paramString2)
  {
    Process localProcess = null;
    String str1 = "svn cat " + paramString1;
    String str2 = null;
    try
    {
      localProcess = Runtime.getRuntime().exec(str1);
      InputStream localInputStream1 = localProcess.getInputStream();
      InputStream localInputStream2 = localProcess.getErrorStream();
      str2 = LineMatcher.instance().getSingleLine(localInputStream1, localInputStream2, paramString2);
      int i = localProcess.exitValue();
      if (i == 0);
      while (true)
        return str2;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      return str2;
    }
    finally
    {
      if (localProcess != null)
        localProcess.destroy();
    }
  }

  private boolean waitFor(Process paramProcess, int paramInt)
  {
    long l = paramInt;
    try
    {
      Thread.sleep(l);
      paramProcess.exitValue();
      return true;
    }
    catch (InterruptedException localInterruptedException)
    {
      return false;
    }
    catch (IllegalThreadStateException localIllegalThreadStateException)
    {
      label16: break label16;
    }
  }

  public ArrayList<String> readFile(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if (TextUtils.isEmpty(paramString1))
    {
      Logger.d(TAG, "readFile error! url is null !");
      return null;
    }
    if (TextUtils.isEmpty(paramString2))
    {
      Logger.d(TAG, "readFile error! beginFormate is null !");
      return null;
    }
    if (TextUtils.isEmpty(paramString3))
    {
      Logger.d(TAG, "readFile error! endFormate is null !");
      return null;
    }
    if (TextUtils.isEmpty(paramString4))
    {
      Logger.d(TAG, "readFile error! contentFormate is null !");
      return null;
    }
    return readContents(paramString1, paramString2, paramString3, paramString4);
  }

  public String readFileString(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1))
    {
      Logger.d(TAG, "readFile error! url is null !");
      return null;
    }
    if (TextUtils.isEmpty(paramString2))
    {
      Logger.d(TAG, "readFileString error! formate is null !");
      return null;
    }
    return readString(paramString1, paramString2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.utils.SvnReader
 * JD-Core Version:    0.6.2
 */