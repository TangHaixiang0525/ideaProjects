package com.starcor.utils;

import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class EditeFileHelper
{
  private static final String TAG = EditeFileHelper.class.getName();
  private static EditeFileHelper sTools;

  // ERROR //
  private void fileChannelCopy(File paramFile1, File paramFile2)
  {
    // Byte code:
    //   0: getstatic 18	com/starcor/utils/EditeFileHelper:TAG	Ljava/lang/String;
    //   3: ldc 27
    //   5: invokestatic 33	com/starcor/utils/Logger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   8: aconst_null
    //   9: astore_3
    //   10: aconst_null
    //   11: astore 4
    //   13: aconst_null
    //   14: astore 5
    //   16: aconst_null
    //   17: astore 6
    //   19: new 35	java/io/FileInputStream
    //   22: dup
    //   23: aload_1
    //   24: invokespecial 38	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   27: astore 7
    //   29: new 40	java/io/FileOutputStream
    //   32: dup
    //   33: aload_2
    //   34: invokespecial 41	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   37: astore 8
    //   39: aload 7
    //   41: invokevirtual 45	java/io/FileInputStream:getChannel	()Ljava/nio/channels/FileChannel;
    //   44: astore 5
    //   46: aload 8
    //   48: invokevirtual 46	java/io/FileOutputStream:getChannel	()Ljava/nio/channels/FileChannel;
    //   51: astore 6
    //   53: aload 5
    //   55: lconst_0
    //   56: aload 5
    //   58: invokevirtual 52	java/nio/channels/FileChannel:size	()J
    //   61: aload 6
    //   63: invokevirtual 56	java/nio/channels/FileChannel:transferTo	(JJLjava/nio/channels/WritableByteChannel;)J
    //   66: pop2
    //   67: aload 7
    //   69: invokevirtual 59	java/io/FileInputStream:close	()V
    //   72: aload 5
    //   74: invokevirtual 60	java/nio/channels/FileChannel:close	()V
    //   77: aload 8
    //   79: invokevirtual 61	java/io/FileOutputStream:close	()V
    //   82: aload 6
    //   84: invokevirtual 60	java/nio/channels/FileChannel:close	()V
    //   87: return
    //   88: astore 9
    //   90: aload 9
    //   92: invokevirtual 64	java/io/IOException:printStackTrace	()V
    //   95: aload_3
    //   96: invokevirtual 59	java/io/FileInputStream:close	()V
    //   99: aload 5
    //   101: invokevirtual 60	java/nio/channels/FileChannel:close	()V
    //   104: aload 4
    //   106: invokevirtual 61	java/io/FileOutputStream:close	()V
    //   109: aload 6
    //   111: invokevirtual 60	java/nio/channels/FileChannel:close	()V
    //   114: return
    //   115: astore 12
    //   117: aload 12
    //   119: invokevirtual 64	java/io/IOException:printStackTrace	()V
    //   122: return
    //   123: astore 10
    //   125: aload_3
    //   126: invokevirtual 59	java/io/FileInputStream:close	()V
    //   129: aload 5
    //   131: invokevirtual 60	java/nio/channels/FileChannel:close	()V
    //   134: aload 4
    //   136: invokevirtual 61	java/io/FileOutputStream:close	()V
    //   139: aload 6
    //   141: invokevirtual 60	java/nio/channels/FileChannel:close	()V
    //   144: aload 10
    //   146: athrow
    //   147: astore 11
    //   149: aload 11
    //   151: invokevirtual 64	java/io/IOException:printStackTrace	()V
    //   154: goto -10 -> 144
    //   157: astore 15
    //   159: aload 15
    //   161: invokevirtual 64	java/io/IOException:printStackTrace	()V
    //   164: return
    //   165: astore 10
    //   167: aload 7
    //   169: astore_3
    //   170: aconst_null
    //   171: astore 5
    //   173: aconst_null
    //   174: astore 6
    //   176: aconst_null
    //   177: astore 4
    //   179: goto -54 -> 125
    //   182: astore 10
    //   184: aload 8
    //   186: astore 4
    //   188: aload 7
    //   190: astore_3
    //   191: goto -66 -> 125
    //   194: astore 9
    //   196: aload 7
    //   198: astore_3
    //   199: aconst_null
    //   200: astore 5
    //   202: aconst_null
    //   203: astore 6
    //   205: aconst_null
    //   206: astore 4
    //   208: goto -118 -> 90
    //   211: astore 9
    //   213: aload 8
    //   215: astore 4
    //   217: aload 7
    //   219: astore_3
    //   220: goto -130 -> 90
    //
    // Exception table:
    //   from	to	target	type
    //   19	29	88	java/io/IOException
    //   95	114	115	java/io/IOException
    //   19	29	123	finally
    //   90	95	123	finally
    //   125	144	147	java/io/IOException
    //   67	87	157	java/io/IOException
    //   29	39	165	finally
    //   39	67	182	finally
    //   29	39	194	java/io/IOException
    //   39	67	211	java/io/IOException
  }

  public static EditeFileHelper instance()
  {
    if (sTools == null)
      sTools = new EditeFileHelper();
    return sTools;
  }

  private String readAndModify(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    try
    {
      String str = LineMatcher.instance().modifyContents(new FileInputStream(new File(paramString1)), null, paramString2, paramString4, paramString3);
      return str;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
    }
    return null;
  }

  private ArrayList<String> readContent(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    try
    {
      ArrayList localArrayList = LineMatcher.instance().getContents(new FileInputStream(new File(paramString1)), null, paramString2, paramString3, paramString4);
      return localArrayList;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
    }
    return null;
  }

  private String readLine(String paramString1, String paramString2)
  {
    try
    {
      String str = LineMatcher.instance().getSingleLine(new FileInputStream(new File(paramString1)), null, paramString2);
      return str;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }

  // ERROR //
  private void write(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: new 105	java/io/BufferedWriter
    //   5: dup
    //   6: new 107	java/io/FileWriter
    //   9: dup
    //   10: aload_1
    //   11: invokespecial 108	java/io/FileWriter:<init>	(Ljava/lang/String;)V
    //   14: invokespecial 111	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
    //   17: astore 4
    //   19: aload 4
    //   21: aload_2
    //   22: invokevirtual 113	java/io/BufferedWriter:write	(Ljava/lang/String;)V
    //   25: aload 4
    //   27: ifnull +65 -> 92
    //   30: aload 4
    //   32: invokevirtual 114	java/io/BufferedWriter:close	()V
    //   35: return
    //   36: astore 5
    //   38: aload 5
    //   40: invokevirtual 115	java/lang/Exception:printStackTrace	()V
    //   43: aload_3
    //   44: ifnull -9 -> 35
    //   47: aload_3
    //   48: invokevirtual 114	java/io/BufferedWriter:close	()V
    //   51: return
    //   52: astore 8
    //   54: return
    //   55: astore 6
    //   57: aload_3
    //   58: ifnull +7 -> 65
    //   61: aload_3
    //   62: invokevirtual 114	java/io/BufferedWriter:close	()V
    //   65: aload 6
    //   67: athrow
    //   68: astore 7
    //   70: goto -5 -> 65
    //   73: astore 9
    //   75: return
    //   76: astore 6
    //   78: aload 4
    //   80: astore_3
    //   81: goto -24 -> 57
    //   84: astore 5
    //   86: aload 4
    //   88: astore_3
    //   89: goto -51 -> 38
    //   92: return
    //
    // Exception table:
    //   from	to	target	type
    //   2	19	36	java/lang/Exception
    //   47	51	52	java/io/IOException
    //   2	19	55	finally
    //   38	43	55	finally
    //   61	65	68	java/io/IOException
    //   30	35	73	java/io/IOException
    //   19	25	76	finally
    //   19	25	84	java/lang/Exception
  }

  public void copyFile(String paramString1, String paramString2)
  {
    File localFile = new File(paramString1);
    if ((localFile == null) || (!localFile.exists()))
    {
      Logger.d(TAG, "copyFile error! srcFile is null !");
      return;
    }
    fileChannelCopy(localFile, new File(paramString2));
  }

  public void modifyFile(String paramString1, String paramString2, String paramString3)
  {
    modifyFile(paramString1, paramString2, paramString3, null);
  }

  public void modifyFile(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    File localFile = new File(paramString1);
    if ((localFile == null) || (!localFile.exists()))
    {
      Logger.d(TAG, "modifyFile error! file is null !");
      return;
    }
    if (TextUtils.isEmpty(paramString2))
    {
      Logger.d(TAG, "modifyFile error! formate is null !");
      return;
    }
    if (TextUtils.isEmpty(paramString3))
    {
      Logger.d(TAG, "modifyFile error! destContent is null !");
      return;
    }
    write(paramString1, readAndModify(paramString1, paramString2, paramString3, paramString4));
  }

  public ArrayList<String> readFile(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    File localFile = new File(paramString1);
    if ((localFile == null) || (!localFile.exists()))
    {
      Logger.d(TAG, "readFile error! file is null !");
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
    return readContent(paramString1, paramString2, paramString3, paramString4);
  }

  public String readFileString(String paramString1, String paramString2)
  {
    File localFile = new File(paramString1);
    if ((localFile == null) || (!localFile.exists()))
    {
      Logger.d(TAG, "readFileString error! file is null !");
      return null;
    }
    if (TextUtils.isEmpty(paramString2))
    {
      Logger.d(TAG, "readFileString error! formate is null !");
      return null;
    }
    return readLine(paramString1, paramString2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.utils.EditeFileHelper
 * JD-Core Version:    0.6.2
 */