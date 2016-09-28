package com.starcor.log.tools;

import android.os.Environment;
import com.starcor.hunan.App;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ContentManager
{
  private static final int LOG_DISK_SIZE = 6291456;
  private static final int LOG_MEMEORY_SIZE = 1048576;
  private static final String TAG = ContentManager.class.getSimpleName();
  private String log_disk_Path = App.getInstance().getDir("log_tmp_cache", 0).toString();
  private String log_disk_file_name = "apkTmp.log";
  private Lock mAddContentLock = new ReentrantLock();
  private StringBuilder mContents = new StringBuilder();
  private int mCurrentFileSize;
  private ContentProcessListener mListener;
  private List<String> mReadToSaveContents = new ArrayList();
  private Lock mSaveContentLock = new ReentrantLock();
  private List<String> mTmpContents = new ArrayList();

  private void addContentsToSave()
  {
    this.mSaveContentLock.lock();
    try
    {
      this.mTmpContents.add(this.mContents.toString());
      return;
    }
    finally
    {
      this.mSaveContentLock.unlock();
    }
  }

  private File getDiskCacheFile(String paramString)
    throws IOException
  {
    File localFile1 = new File(this.log_disk_Path);
    if (!localFile1.exists())
      localFile1.mkdir();
    File localFile2 = new File(this.log_disk_Path, paramString);
    if (!localFile2.exists())
      localFile2.createNewFile();
    return localFile2;
  }

  private int getFileIndex(String paramString)
  {
    return Integer.valueOf(paramString.split("_")[0]).intValue();
  }

  private String getInnerSDCardPath()
  {
    File localFile = new File(Environment.getExternalStorageDirectory().getPath(), "log_disk_cache");
    if (!localFile.exists())
      localFile.mkdir();
    return localFile.getAbsolutePath();
  }

  // ERROR //
  private boolean string2File(String paramString, File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: new 161	java/io/BufferedReader
    //   5: dup
    //   6: new 163	java/io/StringReader
    //   9: dup
    //   10: aload_1
    //   11: invokespecial 164	java/io/StringReader:<init>	(Ljava/lang/String;)V
    //   14: invokespecial 167	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   17: astore 4
    //   19: new 169	java/io/BufferedWriter
    //   22: dup
    //   23: new 171	java/io/FileWriter
    //   26: dup
    //   27: aload_2
    //   28: invokespecial 174	java/io/FileWriter:<init>	(Ljava/io/File;)V
    //   31: invokespecial 177	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
    //   34: astore 5
    //   36: sipush 1024
    //   39: newarray char
    //   41: astore 10
    //   43: aload 4
    //   45: aload 10
    //   47: invokevirtual 181	java/io/BufferedReader:read	([C)I
    //   50: istore 11
    //   52: iload 11
    //   54: iconst_m1
    //   55: if_icmpeq +36 -> 91
    //   58: aload 5
    //   60: aload 10
    //   62: iconst_0
    //   63: iload 11
    //   65: invokevirtual 185	java/io/BufferedWriter:write	([CII)V
    //   68: goto -25 -> 43
    //   71: astore 8
    //   73: aload 4
    //   75: astore_3
    //   76: aload 8
    //   78: invokevirtual 188	java/io/IOException:printStackTrace	()V
    //   81: aload_3
    //   82: ifnull +7 -> 89
    //   85: aload_3
    //   86: invokevirtual 191	java/io/BufferedReader:close	()V
    //   89: iconst_0
    //   90: ireturn
    //   91: aload 5
    //   93: invokevirtual 194	java/io/BufferedWriter:flush	()V
    //   96: aload 4
    //   98: invokevirtual 191	java/io/BufferedReader:close	()V
    //   101: aload 5
    //   103: invokevirtual 195	java/io/BufferedWriter:close	()V
    //   106: aload 4
    //   108: ifnull +8 -> 116
    //   111: aload 4
    //   113: invokevirtual 191	java/io/BufferedReader:close	()V
    //   116: iconst_1
    //   117: ireturn
    //   118: astore 12
    //   120: aload 12
    //   122: invokevirtual 188	java/io/IOException:printStackTrace	()V
    //   125: goto -9 -> 116
    //   128: astore 9
    //   130: aload 9
    //   132: invokevirtual 188	java/io/IOException:printStackTrace	()V
    //   135: goto -46 -> 89
    //   138: astore 6
    //   140: aload_3
    //   141: ifnull +7 -> 148
    //   144: aload_3
    //   145: invokevirtual 191	java/io/BufferedReader:close	()V
    //   148: aload 6
    //   150: athrow
    //   151: astore 7
    //   153: aload 7
    //   155: invokevirtual 188	java/io/IOException:printStackTrace	()V
    //   158: goto -10 -> 148
    //   161: astore 6
    //   163: aload 4
    //   165: astore_3
    //   166: goto -26 -> 140
    //   169: astore 6
    //   171: aload 4
    //   173: astore_3
    //   174: goto -34 -> 140
    //   177: astore 8
    //   179: aconst_null
    //   180: astore_3
    //   181: goto -105 -> 76
    //   184: astore 8
    //   186: aload 4
    //   188: astore_3
    //   189: goto -113 -> 76
    //
    // Exception table:
    //   from	to	target	type
    //   36	43	71	java/io/IOException
    //   43	52	71	java/io/IOException
    //   58	68	71	java/io/IOException
    //   91	106	71	java/io/IOException
    //   111	116	118	java/io/IOException
    //   85	89	128	java/io/IOException
    //   2	19	138	finally
    //   76	81	138	finally
    //   144	148	151	java/io/IOException
    //   19	36	161	finally
    //   36	43	169	finally
    //   43	52	169	finally
    //   58	68	169	finally
    //   91	106	169	finally
    //   2	19	177	java/io/IOException
    //   19	36	184	java/io/IOException
  }

  public void addContent(String paramString)
  {
    this.mAddContentLock.lock();
    try
    {
      this.mContents.append(paramString);
      this.mContents.append("\n");
      if (this.mContents.length() >= 1048576)
      {
        addContentsToSave();
        this.mContents = new StringBuilder();
        if (this.mListener != null)
          this.mListener.onContentLimit();
      }
      return;
    }
    finally
    {
      this.mAddContentLock.unlock();
    }
  }

  public List<File> getOrderedDiskCacheFiles()
  {
    File localFile1 = new File(this.log_disk_Path);
    ArrayList localArrayList = new ArrayList();
    if (!localFile1.exists())
    {
      localFile1.mkdir();
      return localArrayList;
    }
    for (File localFile2 : localFile1.listFiles())
      if ((localFile2.isFile()) && (localFile2.getName().endsWith("_" + this.log_disk_file_name)))
      {
        this.mCurrentFileSize = ((int)(this.mCurrentFileSize + localFile2.length()));
        localArrayList.add(localFile2);
      }
    Collections.sort(localArrayList, new Comparator()
    {
      public int compare(File paramAnonymousFile1, File paramAnonymousFile2)
      {
        return ContentManager.this.getFileIndex(paramAnonymousFile1.getName()) - ContentManager.this.getFileIndex(paramAnonymousFile2.getName());
      }
    });
    return localArrayList;
  }

  // ERROR //
  public void saveAllContents()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 71	com/starcor/log/tools/ContentManager:mAddContentLock	Ljava/util/concurrent/locks/Lock;
    //   4: invokeinterface 95 1 0
    //   9: aload_0
    //   10: getfield 66	com/starcor/log/tools/ContentManager:mContents	Ljava/lang/StringBuilder;
    //   13: invokevirtual 205	java/lang/StringBuilder:length	()I
    //   16: ifle +18 -> 34
    //   19: aload_0
    //   20: invokespecial 207	com/starcor/log/tools/ContentManager:addContentsToSave	()V
    //   23: aload_0
    //   24: new 63	java/lang/StringBuilder
    //   27: dup
    //   28: invokespecial 64	java/lang/StringBuilder:<init>	()V
    //   31: putfield 66	com/starcor/log/tools/ContentManager:mContents	Ljava/lang/StringBuilder;
    //   34: aload_0
    //   35: getfield 71	com/starcor/log/tools/ContentManager:mAddContentLock	Ljava/util/concurrent/locks/Lock;
    //   38: invokeinterface 105 1 0
    //   43: aload_0
    //   44: invokevirtual 250	com/starcor/log/tools/ContentManager:saveContents	()V
    //   47: return
    //   48: astore_1
    //   49: aload_0
    //   50: getfield 71	com/starcor/log/tools/ContentManager:mAddContentLock	Ljava/util/concurrent/locks/Lock;
    //   53: invokeinterface 105 1 0
    //   58: aload_1
    //   59: athrow
    //   60: astore_3
    //   61: aload_3
    //   62: invokevirtual 188	java/io/IOException:printStackTrace	()V
    //   65: return
    //   66: astore_2
    //   67: aload_2
    //   68: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   9	34	48	finally
    //   43	47	60	java/io/IOException
    //   43	47	66	finally
    //   61	65	66	finally
  }

  public void saveContents()
    throws IOException
  {
    this.mCurrentFileSize = 0;
    this.mSaveContentLock.lock();
    List localList;
    ArrayList localArrayList;
    try
    {
      this.mReadToSaveContents.addAll(this.mTmpContents);
      this.mTmpContents.clear();
      this.mSaveContentLock.unlock();
      localList = getOrderedDiskCacheFiles();
      int i = localList.size();
      j = 0;
      if (i > 0)
        j = 1 + getFileIndex(((File)localList.get(-1 + localList.size())).getName());
      if (j < 10)
        break label226;
      localArrayList = new ArrayList();
      int k = 0;
      if (k < localList.size())
      {
        File localFile3 = getDiskCacheFile(k + "_" + this.log_disk_file_name);
        ((File)localList.get(k)).renameTo(localFile3);
        localArrayList.add(localFile3);
        k++;
      }
    }
    finally
    {
      this.mSaveContentLock.unlock();
    }
    localList.addAll(localArrayList);
    int j = localList.size();
    label226: Iterator localIterator = this.mReadToSaveContents.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      StringBuilder localStringBuilder = new StringBuilder();
      int m = j + 1;
      File localFile2 = getDiskCacheFile(j + "_" + this.log_disk_file_name);
      localList.add(localFile2);
      string2File(str, localFile2);
      this.mCurrentFileSize += str.length();
      j = m;
    }
    this.mReadToSaveContents.clear();
    while ((this.mCurrentFileSize >= 6291456) || (localList.size() > 6))
    {
      File localFile1 = (File)localList.remove(0);
      this.mCurrentFileSize = ((int)(this.mCurrentFileSize - localFile1.length()));
      localFile1.delete();
    }
  }

  public void setContentListener(ContentProcessListener paramContentProcessListener)
  {
    this.mListener = paramContentProcessListener;
  }

  public static abstract interface ContentProcessListener
  {
    public abstract void onContentLimit();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.log.tools.ContentManager
 * JD-Core Version:    0.6.2
 */