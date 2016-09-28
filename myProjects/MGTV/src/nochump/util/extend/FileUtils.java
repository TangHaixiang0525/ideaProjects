package nochump.util.extend;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Calendar;

public class FileUtils
{
  public static int CopyFile(String paramString1, String paramString2)
  {
    try
    {
      FileInputStream localFileInputStream = new FileInputStream(paramString1);
      FileOutputStream localFileOutputStream = new FileOutputStream(paramString2);
      byte[] arrayOfByte = new byte[1024];
      while (true)
      {
        int i = localFileInputStream.read(arrayOfByte);
        if (i <= 0)
        {
          localFileInputStream.close();
          localFileOutputStream.close();
          return 1;
        }
        localFileOutputStream.write(arrayOfByte, 0, i);
      }
    }
    catch (Exception localException)
    {
    }
    return 0;
  }

  public static int copy(String paramString1, String paramString2)
  {
    File localFile1 = new File(paramString1);
    if (!localFile1.exists())
      return 0;
    File[] arrayOfFile = localFile1.listFiles();
    File localFile2 = new File(paramString2);
    if (!localFile2.exists())
      localFile2.mkdirs();
    int i = 0;
    if (i >= arrayOfFile.length)
      return 1;
    if (arrayOfFile[i].isDirectory())
      copy(arrayOfFile[i].getPath() + "/", paramString2 + arrayOfFile[i].getName() + "/");
    while (true)
    {
      i++;
      break;
      CopyFile(arrayOfFile[i].getPath(), paramString2 + arrayOfFile[i].getName());
    }
  }

  // ERROR //
  private static void copy(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException
  {
    // Byte code:
    //   0: sipush 4096
    //   3: newarray byte
    //   5: astore 8
    //   7: aload_0
    //   8: aload 8
    //   10: invokevirtual 26	java/io/InputStream:read	([B)I
    //   13: istore 9
    //   15: iload 9
    //   17: iconst_m1
    //   18: if_icmpne +24 -> 42
    //   21: aload_1
    //   22: invokevirtual 89	java/io/OutputStream:flush	()V
    //   25: aload_0
    //   26: ifnull +7 -> 33
    //   29: aload_0
    //   30: invokevirtual 29	java/io/InputStream:close	()V
    //   33: aload_1
    //   34: ifnull +7 -> 41
    //   37: aload_1
    //   38: invokevirtual 32	java/io/OutputStream:close	()V
    //   41: return
    //   42: aload_1
    //   43: aload 8
    //   45: iconst_0
    //   46: iload 9
    //   48: invokevirtual 36	java/io/OutputStream:write	([BII)V
    //   51: goto -44 -> 7
    //   54: astore 5
    //   56: aload_0
    //   57: ifnull +7 -> 64
    //   60: aload_0
    //   61: invokevirtual 29	java/io/InputStream:close	()V
    //   64: aload_1
    //   65: ifnull -24 -> 41
    //   68: aload_1
    //   69: invokevirtual 32	java/io/OutputStream:close	()V
    //   72: return
    //   73: astore 6
    //   75: return
    //   76: astore_2
    //   77: aload_0
    //   78: ifnull +7 -> 85
    //   81: aload_0
    //   82: invokevirtual 29	java/io/InputStream:close	()V
    //   85: aload_1
    //   86: ifnull +7 -> 93
    //   89: aload_1
    //   90: invokevirtual 32	java/io/OutputStream:close	()V
    //   93: aload_2
    //   94: athrow
    //   95: astore 7
    //   97: goto -33 -> 64
    //   100: astore 4
    //   102: goto -17 -> 85
    //   105: astore_3
    //   106: goto -13 -> 93
    //   109: astore 11
    //   111: goto -78 -> 33
    //   114: astore 10
    //   116: return
    //
    // Exception table:
    //   from	to	target	type
    //   0	7	54	java/io/IOException
    //   7	15	54	java/io/IOException
    //   21	25	54	java/io/IOException
    //   42	51	54	java/io/IOException
    //   68	72	73	java/io/IOException
    //   0	7	76	finally
    //   7	15	76	finally
    //   21	25	76	finally
    //   42	51	76	finally
    //   60	64	95	java/io/IOException
    //   81	85	100	java/io/IOException
    //   89	93	105	java/io/IOException
    //   29	33	109	java/io/IOException
    //   37	41	114	java/io/IOException
  }

  public static String getCurrentTime()
  {
    Calendar localCalendar = Calendar.getInstance();
    int i = localCalendar.get(1);
    int j = 1 + localCalendar.get(2);
    int k = localCalendar.get(5);
    int m = localCalendar.get(11);
    int n = localCalendar.get(12);
    int i1 = localCalendar.get(13);
    int i2 = localCalendar.get(14);
    StringBuffer localStringBuffer1 = new StringBuffer().append(i);
    Object localObject1;
    Object localObject2;
    label126: Object localObject3;
    label161: Object localObject4;
    label196: Object localObject5;
    label231: StringBuffer localStringBuffer6;
    Object localObject6;
    if (j < 10)
    {
      localObject1 = "0" + j;
      StringBuffer localStringBuffer2 = localStringBuffer1.append(localObject1);
      if (k >= 10)
        break label293;
      localObject2 = "0" + k;
      StringBuffer localStringBuffer3 = localStringBuffer2.append(localObject2);
      if (m >= 10)
        break label302;
      localObject3 = "0" + m;
      StringBuffer localStringBuffer4 = localStringBuffer3.append(localObject3);
      if (n >= 10)
        break label312;
      localObject4 = "0" + n;
      StringBuffer localStringBuffer5 = localStringBuffer4.append(localObject4);
      if (i1 >= 10)
        break label322;
      localObject5 = "0" + i1;
      localStringBuffer6 = localStringBuffer5.append(localObject5);
      if (i2 >= 100)
        break label354;
      if (i2 >= 10)
        break label332;
      localObject6 = "00" + i2;
    }
    while (true)
    {
      return localObject6;
      localObject1 = Integer.valueOf(j);
      break;
      label293: localObject2 = Integer.valueOf(k);
      break label126;
      label302: localObject3 = Integer.valueOf(m);
      break label161;
      label312: localObject4 = Integer.valueOf(n);
      break label196;
      label322: localObject5 = Integer.valueOf(i1);
      break label231;
      label332: localObject6 = "0" + i2;
      continue;
      label354: localObject6 = Integer.valueOf(i2);
    }
  }

  public static byte[] getFileByte(InputStream paramInputStream)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(4096);
    try
    {
      copy(paramInputStream, localByteArrayOutputStream);
      return localByteArrayOutputStream.toByteArray();
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  public static File[] getFileList(String paramString)
  {
    File localFile = new File(paramString);
    String[] arrayOfString = localFile.list();
    int i = arrayOfString.length;
    for (int j = 0; ; j++)
    {
      if (j >= i)
        return localFile.listFiles();
      String str = arrayOfString[j];
      System.out.println(str);
    }
  }

  public static void moveFile(String paramString1, String paramString2, String paramString3)
  {
    int i;
    int j;
    try
    {
      File localFile1 = new File(paramString2);
      if (!localFile1.exists())
        localFile1.mkdirs();
      File[] arrayOfFile = new File(paramString1).listFiles();
      i = arrayOfFile.length;
      j = 0;
      break label176;
      File localFile2 = arrayOfFile[j];
      if (localFile2.isDirectory())
      {
        moveFile(localFile2.getAbsolutePath(), paramString2 + File.separator + localFile2.getName(), paramString3);
        localFile2.delete();
      }
      else
      {
        File localFile3 = new File(paramString2 + File.separator + localFile2.getName());
        if (localFile3.exists())
          moveFileToErrDir(localFile3, paramString3);
        localFile2.renameTo(localFile3);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    label176: 
    while (j >= i)
    {
      return;
      j++;
    }
  }

  private static void moveFileToErrDir(File paramFile, String paramString)
  {
    int i = 0;
    for (String str = paramString + File.separator + "rnError" + paramFile.getName(); ; str = paramString + File.separator + i + "rnError" + paramFile.getName())
    {
      if (!new File(str).exists())
      {
        paramFile.renameTo(new File(str));
        return;
      }
      i++;
    }
  }

  // ERROR //
  public static byte[] readFileByte(File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aconst_null
    //   3: astore_2
    //   4: aconst_null
    //   5: astore_3
    //   6: new 14	java/io/FileInputStream
    //   9: dup
    //   10: aload_0
    //   11: invokespecial 187	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   14: astore 4
    //   16: aload 4
    //   18: invokevirtual 191	java/io/FileInputStream:getChannel	()Ljava/nio/channels/FileChannel;
    //   21: astore_2
    //   22: aload_2
    //   23: invokevirtual 197	java/nio/channels/FileChannel:size	()J
    //   26: l2i
    //   27: newarray byte
    //   29: astore_3
    //   30: aload_2
    //   31: aload_3
    //   32: invokestatic 203	java/nio/ByteBuffer:wrap	([B)Ljava/nio/ByteBuffer;
    //   35: invokevirtual 206	java/nio/channels/FileChannel:read	(Ljava/nio/ByteBuffer;)I
    //   38: pop
    //   39: aload_2
    //   40: ifnull +7 -> 47
    //   43: aload_2
    //   44: invokevirtual 207	java/nio/channels/FileChannel:close	()V
    //   47: aload 4
    //   49: ifnull +156 -> 205
    //   52: aload 4
    //   54: invokevirtual 208	java/io/FileInputStream:close	()V
    //   57: aload_3
    //   58: areturn
    //   59: astore 5
    //   61: aload 5
    //   63: invokevirtual 209	java/io/FileNotFoundException:printStackTrace	()V
    //   66: aload_2
    //   67: ifnull +7 -> 74
    //   70: aload_2
    //   71: invokevirtual 207	java/nio/channels/FileChannel:close	()V
    //   74: aload_1
    //   75: ifnull -18 -> 57
    //   78: aload_1
    //   79: invokevirtual 208	java/io/FileInputStream:close	()V
    //   82: aload_3
    //   83: areturn
    //   84: astore 9
    //   86: aload 9
    //   88: invokevirtual 138	java/io/IOException:printStackTrace	()V
    //   91: aload_3
    //   92: areturn
    //   93: astore 10
    //   95: aload 10
    //   97: invokevirtual 138	java/io/IOException:printStackTrace	()V
    //   100: goto -26 -> 74
    //   103: astore 11
    //   105: aload 11
    //   107: invokevirtual 138	java/io/IOException:printStackTrace	()V
    //   110: aload_2
    //   111: ifnull +7 -> 118
    //   114: aload_2
    //   115: invokevirtual 207	java/nio/channels/FileChannel:close	()V
    //   118: aload_1
    //   119: ifnull -62 -> 57
    //   122: aload_1
    //   123: invokevirtual 208	java/io/FileInputStream:close	()V
    //   126: aload_3
    //   127: areturn
    //   128: astore 12
    //   130: aload 12
    //   132: invokevirtual 138	java/io/IOException:printStackTrace	()V
    //   135: aload_3
    //   136: areturn
    //   137: astore 13
    //   139: aload 13
    //   141: invokevirtual 138	java/io/IOException:printStackTrace	()V
    //   144: goto -26 -> 118
    //   147: astore 6
    //   149: aload_2
    //   150: ifnull +7 -> 157
    //   153: aload_2
    //   154: invokevirtual 207	java/nio/channels/FileChannel:close	()V
    //   157: aload_1
    //   158: ifnull +7 -> 165
    //   161: aload_1
    //   162: invokevirtual 208	java/io/FileInputStream:close	()V
    //   165: aload 6
    //   167: athrow
    //   168: astore 8
    //   170: aload 8
    //   172: invokevirtual 138	java/io/IOException:printStackTrace	()V
    //   175: goto -18 -> 157
    //   178: astore 7
    //   180: aload 7
    //   182: invokevirtual 138	java/io/IOException:printStackTrace	()V
    //   185: goto -20 -> 165
    //   188: astore 16
    //   190: aload 16
    //   192: invokevirtual 138	java/io/IOException:printStackTrace	()V
    //   195: goto -148 -> 47
    //   198: astore 15
    //   200: aload 15
    //   202: invokevirtual 138	java/io/IOException:printStackTrace	()V
    //   205: aload_3
    //   206: areturn
    //   207: astore 6
    //   209: aload 4
    //   211: astore_1
    //   212: goto -63 -> 149
    //   215: astore 11
    //   217: aload 4
    //   219: astore_1
    //   220: goto -115 -> 105
    //   223: astore 5
    //   225: aload 4
    //   227: astore_1
    //   228: goto -167 -> 61
    //
    // Exception table:
    //   from	to	target	type
    //   6	16	59	java/io/FileNotFoundException
    //   78	82	84	java/io/IOException
    //   70	74	93	java/io/IOException
    //   6	16	103	java/io/IOException
    //   122	126	128	java/io/IOException
    //   114	118	137	java/io/IOException
    //   6	16	147	finally
    //   61	66	147	finally
    //   105	110	147	finally
    //   153	157	168	java/io/IOException
    //   161	165	178	java/io/IOException
    //   43	47	188	java/io/IOException
    //   52	57	198	java/io/IOException
    //   16	39	207	finally
    //   16	39	215	java/io/IOException
    //   16	39	223	java/io/FileNotFoundException
  }

  public static byte[] readFileByte(String paramString)
    throws IOException
  {
    if ((paramString == null) || (paramString.equals("")))
      throw new NullPointerException("无效的文件路径");
    File localFile = new File(paramString);
    long l = localFile.length();
    byte[] arrayOfByte = new byte[(int)l];
    BufferedInputStream localBufferedInputStream = new BufferedInputStream(new FileInputStream(localFile));
    if (localBufferedInputStream.read(arrayOfByte) != l)
      throw new IOException("读取文件不正确");
    localBufferedInputStream.close();
    return arrayOfByte;
  }

  // ERROR //
  public static String writeByteFile(byte[] paramArrayOfByte, File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 19	java/io/FileOutputStream
    //   5: dup
    //   6: aload_1
    //   7: invokespecial 237	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   10: astore_3
    //   11: aload_3
    //   12: aload_0
    //   13: invokevirtual 240	java/io/FileOutputStream:write	([B)V
    //   16: aload_3
    //   17: ifnull +96 -> 113
    //   20: aload_3
    //   21: invokevirtual 241	java/io/FileOutputStream:close	()V
    //   24: ldc 243
    //   26: areturn
    //   27: astore 4
    //   29: aload 4
    //   31: invokevirtual 209	java/io/FileNotFoundException:printStackTrace	()V
    //   34: aload_2
    //   35: ifnull -11 -> 24
    //   38: aload_2
    //   39: invokevirtual 241	java/io/FileOutputStream:close	()V
    //   42: goto -18 -> 24
    //   45: astore 7
    //   47: aload 7
    //   49: invokevirtual 138	java/io/IOException:printStackTrace	()V
    //   52: goto -28 -> 24
    //   55: astore 8
    //   57: aload 8
    //   59: invokevirtual 138	java/io/IOException:printStackTrace	()V
    //   62: aload_2
    //   63: ifnull -39 -> 24
    //   66: aload_2
    //   67: invokevirtual 241	java/io/FileOutputStream:close	()V
    //   70: goto -46 -> 24
    //   73: astore 9
    //   75: aload 9
    //   77: invokevirtual 138	java/io/IOException:printStackTrace	()V
    //   80: goto -56 -> 24
    //   83: astore 5
    //   85: aload_2
    //   86: ifnull +7 -> 93
    //   89: aload_2
    //   90: invokevirtual 241	java/io/FileOutputStream:close	()V
    //   93: aload 5
    //   95: athrow
    //   96: astore 6
    //   98: aload 6
    //   100: invokevirtual 138	java/io/IOException:printStackTrace	()V
    //   103: goto -10 -> 93
    //   106: astore 10
    //   108: aload 10
    //   110: invokevirtual 138	java/io/IOException:printStackTrace	()V
    //   113: goto -89 -> 24
    //   116: astore 5
    //   118: aload_3
    //   119: astore_2
    //   120: goto -35 -> 85
    //   123: astore 8
    //   125: aload_3
    //   126: astore_2
    //   127: goto -70 -> 57
    //   130: astore 4
    //   132: aload_3
    //   133: astore_2
    //   134: goto -105 -> 29
    //
    // Exception table:
    //   from	to	target	type
    //   2	11	27	java/io/FileNotFoundException
    //   38	42	45	java/io/IOException
    //   2	11	55	java/io/IOException
    //   66	70	73	java/io/IOException
    //   2	11	83	finally
    //   29	34	83	finally
    //   57	62	83	finally
    //   89	93	96	java/io/IOException
    //   20	24	106	java/io/IOException
    //   11	16	116	finally
    //   11	16	123	java/io/IOException
    //   11	16	130	java/io/FileNotFoundException
  }

  private static void writeInputStreamToFile(InputStream paramInputStream, OutputStream paramOutputStream)
    throws Exception
  {
    byte[] arrayOfByte = new byte[8192];
    while (true)
    {
      int i = paramInputStream.read(arrayOfByte);
      if (i <= 0)
        return;
      paramOutputStream.write(arrayOfByte, 0, i);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     nochump.util.extend.FileUtils
 * JD-Core Version:    0.6.2
 */