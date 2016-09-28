package com.huawei.playerinterface.decoderConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class FileUtil
{
  private static final String TAG = "HAPlayer_FileUtil";

  private boolean isValidFile(String paramString)
  {
    DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
    FileInputStream localFileInputStream2;
    try
    {
      FileInputStream localFileInputStream1 = new FileInputStream(paramString);
      localFileInputStream2 = localFileInputStream1;
      if (localFileInputStream2 == null)
        return false;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      while (true)
      {
        localFileNotFoundException.printStackTrace();
        localFileInputStream2 = null;
      }
      try
      {
        DocumentBuilder localDocumentBuilder2 = localDocumentBuilderFactory.newDocumentBuilder();
        localDocumentBuilder1 = localDocumentBuilder2;
      }
      catch (ParserConfigurationException localParserConfigurationException)
      {
        try
        {
          while (true)
          {
            Document localDocument2 = localDocumentBuilder1.parse(localFileInputStream2);
            localDocument1 = localDocument2;
            if (localDocument1 != null)
              break;
            try
            {
              localFileInputStream2.close();
              return false;
            }
            catch (IOException localIOException3)
            {
              localIOException3.printStackTrace();
              return false;
            }
            localParserConfigurationException = localParserConfigurationException;
            localParserConfigurationException.printStackTrace();
            DocumentBuilder localDocumentBuilder1 = null;
          }
        }
        catch (SAXException localSAXException)
        {
          while (true)
          {
            localSAXException.printStackTrace();
            localDocument1 = null;
          }
        }
        catch (IOException localIOException1)
        {
          while (true)
          {
            localIOException1.printStackTrace();
            Document localDocument1 = null;
          }
        }
      }
    }
    try
    {
      localFileInputStream2.close();
      return true;
    }
    catch (IOException localIOException2)
    {
      while (true)
        localIOException2.printStackTrace();
    }
  }

  public File createDir(String paramString)
    throws IOException
  {
    File localFile = new File(paramString);
    localFile.mkdir();
    return localFile;
  }

  public File createFile(String paramString)
    throws IOException
  {
    File localFile = new File(paramString);
    localFile.createNewFile();
    return localFile;
  }

  public boolean isExist(String paramString)
  {
    return new File(paramString).exists();
  }

  // ERROR //
  public File writeFile(String paramString1, String paramString2, InputStream paramInputStream)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 5
    //   6: aload_0
    //   7: aload_1
    //   8: invokevirtual 75	com/huawei/playerinterface/decoderConfig/FileUtil:createDir	(Ljava/lang/String;)Ljava/io/File;
    //   11: pop
    //   12: aload_0
    //   13: new 77	java/lang/StringBuilder
    //   16: dup
    //   17: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   20: aload_1
    //   21: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: ldc 84
    //   26: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   29: aload_2
    //   30: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: invokevirtual 88	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   36: invokevirtual 90	com/huawei/playerinterface/decoderConfig/FileUtil:createFile	(Ljava/lang/String;)Ljava/io/File;
    //   39: astore 4
    //   41: new 92	java/lang/StringBuffer
    //   44: dup
    //   45: invokespecial 93	java/lang/StringBuffer:<init>	()V
    //   48: astore 13
    //   50: new 95	java/io/BufferedWriter
    //   53: dup
    //   54: new 97	java/io/FileWriter
    //   57: dup
    //   58: aload 4
    //   60: invokespecial 100	java/io/FileWriter:<init>	(Ljava/io/File;)V
    //   63: invokespecial 103	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
    //   66: astore 14
    //   68: new 105	java/io/BufferedReader
    //   71: dup
    //   72: new 107	java/io/InputStreamReader
    //   75: dup
    //   76: aload_3
    //   77: invokespecial 110	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   80: invokespecial 113	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   83: astore 15
    //   85: aload 15
    //   87: invokevirtual 116	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   90: astore 16
    //   92: aload 16
    //   94: ifnull +78 -> 172
    //   97: aload 13
    //   99: new 77	java/lang/StringBuilder
    //   102: dup
    //   103: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   106: aload 16
    //   108: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: ldc 118
    //   113: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   116: invokevirtual 88	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   119: invokevirtual 121	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   122: pop
    //   123: goto -38 -> 85
    //   126: astore 6
    //   128: aload 14
    //   130: astore 5
    //   132: aload 6
    //   134: invokevirtual 52	java/io/IOException:printStackTrace	()V
    //   137: aload_3
    //   138: invokevirtual 51	java/io/InputStream:close	()V
    //   141: aload 5
    //   143: ifnull +8 -> 151
    //   146: aload 5
    //   148: invokevirtual 122	java/io/BufferedWriter:close	()V
    //   151: aload_0
    //   152: aload 4
    //   154: invokevirtual 125	java/io/File:getPath	()Ljava/lang/String;
    //   157: invokespecial 127	com/huawei/playerinterface/decoderConfig/FileUtil:isValidFile	(Ljava/lang/String;)Z
    //   160: ifne +150 -> 310
    //   163: ldc 8
    //   165: ldc 129
    //   167: invokestatic 135	com/huawei/dmpbase/DmpLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   170: aconst_null
    //   171: areturn
    //   172: aload 14
    //   174: aload 13
    //   176: invokevirtual 136	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   179: invokevirtual 139	java/io/BufferedWriter:write	(Ljava/lang/String;)V
    //   182: aload_3
    //   183: invokevirtual 51	java/io/InputStream:close	()V
    //   186: aload 14
    //   188: ifnull +8 -> 196
    //   191: aload 14
    //   193: invokevirtual 122	java/io/BufferedWriter:close	()V
    //   196: aload_0
    //   197: aload 4
    //   199: invokevirtual 125	java/io/File:getPath	()Ljava/lang/String;
    //   202: invokespecial 127	com/huawei/playerinterface/decoderConfig/FileUtil:isValidFile	(Ljava/lang/String;)Z
    //   205: ifne +32 -> 237
    //   208: ldc 8
    //   210: ldc 129
    //   212: invokestatic 135	com/huawei/dmpbase/DmpLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   215: aconst_null
    //   216: areturn
    //   217: astore 18
    //   219: aload 18
    //   221: invokevirtual 52	java/io/IOException:printStackTrace	()V
    //   224: goto -38 -> 186
    //   227: astore 19
    //   229: aload 19
    //   231: invokevirtual 52	java/io/IOException:printStackTrace	()V
    //   234: goto -38 -> 196
    //   237: aload 4
    //   239: new 58	java/io/File
    //   242: dup
    //   243: new 77	java/lang/StringBuilder
    //   246: dup
    //   247: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   250: aload_1
    //   251: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   254: aload_2
    //   255: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   258: invokevirtual 88	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   261: invokespecial 59	java/io/File:<init>	(Ljava/lang/String;)V
    //   264: invokevirtual 143	java/io/File:renameTo	(Ljava/io/File;)Z
    //   267: ifeq +13 -> 280
    //   270: ldc 8
    //   272: ldc 145
    //   274: invokestatic 148	com/huawei/dmpbase/DmpLog:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   277: aload 4
    //   279: areturn
    //   280: ldc 8
    //   282: ldc 150
    //   284: invokestatic 135	com/huawei/dmpbase/DmpLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   287: goto -10 -> 277
    //   290: astore 10
    //   292: aload 10
    //   294: invokevirtual 52	java/io/IOException:printStackTrace	()V
    //   297: goto -156 -> 141
    //   300: astore 11
    //   302: aload 11
    //   304: invokevirtual 52	java/io/IOException:printStackTrace	()V
    //   307: goto -156 -> 151
    //   310: aload 4
    //   312: new 58	java/io/File
    //   315: dup
    //   316: new 77	java/lang/StringBuilder
    //   319: dup
    //   320: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   323: aload_1
    //   324: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   327: aload_2
    //   328: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   331: invokevirtual 88	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   334: invokespecial 59	java/io/File:<init>	(Ljava/lang/String;)V
    //   337: invokevirtual 143	java/io/File:renameTo	(Ljava/io/File;)Z
    //   340: ifeq +13 -> 353
    //   343: ldc 8
    //   345: ldc 145
    //   347: invokestatic 148	com/huawei/dmpbase/DmpLog:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   350: goto -73 -> 277
    //   353: ldc 8
    //   355: ldc 150
    //   357: invokestatic 135	com/huawei/dmpbase/DmpLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   360: goto -83 -> 277
    //   363: astore 7
    //   365: aload_3
    //   366: invokevirtual 51	java/io/InputStream:close	()V
    //   369: aload 5
    //   371: ifnull +8 -> 379
    //   374: aload 5
    //   376: invokevirtual 122	java/io/BufferedWriter:close	()V
    //   379: aload_0
    //   380: aload 4
    //   382: invokevirtual 125	java/io/File:getPath	()Ljava/lang/String;
    //   385: invokespecial 127	com/huawei/playerinterface/decoderConfig/FileUtil:isValidFile	(Ljava/lang/String;)Z
    //   388: ifne +32 -> 420
    //   391: ldc 8
    //   393: ldc 129
    //   395: invokestatic 135	com/huawei/dmpbase/DmpLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   398: aconst_null
    //   399: areturn
    //   400: astore 8
    //   402: aload 8
    //   404: invokevirtual 52	java/io/IOException:printStackTrace	()V
    //   407: goto -38 -> 369
    //   410: astore 9
    //   412: aload 9
    //   414: invokevirtual 52	java/io/IOException:printStackTrace	()V
    //   417: goto -38 -> 379
    //   420: aload 4
    //   422: new 58	java/io/File
    //   425: dup
    //   426: new 77	java/lang/StringBuilder
    //   429: dup
    //   430: invokespecial 78	java/lang/StringBuilder:<init>	()V
    //   433: aload_1
    //   434: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   437: aload_2
    //   438: invokevirtual 82	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   441: invokevirtual 88	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   444: invokespecial 59	java/io/File:<init>	(Ljava/lang/String;)V
    //   447: invokevirtual 143	java/io/File:renameTo	(Ljava/io/File;)Z
    //   450: ifeq +13 -> 463
    //   453: ldc 8
    //   455: ldc 145
    //   457: invokestatic 148	com/huawei/dmpbase/DmpLog:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   460: aload 7
    //   462: athrow
    //   463: ldc 8
    //   465: ldc 150
    //   467: invokestatic 135	com/huawei/dmpbase/DmpLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   470: goto -10 -> 460
    //   473: astore 7
    //   475: aload 14
    //   477: astore 5
    //   479: goto -114 -> 365
    //   482: astore 6
    //   484: aconst_null
    //   485: astore 5
    //   487: goto -355 -> 132
    //
    // Exception table:
    //   from	to	target	type
    //   68	85	126	java/io/IOException
    //   85	92	126	java/io/IOException
    //   97	123	126	java/io/IOException
    //   172	182	126	java/io/IOException
    //   182	186	217	java/io/IOException
    //   191	196	227	java/io/IOException
    //   137	141	290	java/io/IOException
    //   146	151	300	java/io/IOException
    //   6	68	363	finally
    //   132	137	363	finally
    //   365	369	400	java/io/IOException
    //   374	379	410	java/io/IOException
    //   68	85	473	finally
    //   85	92	473	finally
    //   97	123	473	finally
    //   172	182	473	finally
    //   6	68	482	java/io/IOException
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.decoderConfig.FileUtil
 * JD-Core Version:    0.6.2
 */