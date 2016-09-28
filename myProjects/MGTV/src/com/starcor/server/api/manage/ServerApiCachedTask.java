package com.starcor.server.api.manage;

import com.starcor.config.AppFuncCfg;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.httpapi.core.SCResponse;
import java.io.File;
import java.util.Locale;

public abstract class ServerApiCachedTask extends ServerApiTask
{
  public static boolean userLocalDataFlag = true;
  protected String cachePath = GlobalEnv.getInstance().getAPICachePath();
  private File file = new File(this.cachePath, getClass().getSimpleName().toLowerCase(Locale.CHINA) + ".api");

  // ERROR //
  public final byte[] loadLocalApiData()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 71	com/starcor/server/api/manage/ServerApiCachedTask:file	Ljava/io/File;
    //   6: ifnull +111 -> 117
    //   9: aload_0
    //   10: getfield 71	com/starcor/server/api/manage/ServerApiCachedTask:file	Ljava/io/File;
    //   13: invokevirtual 87	java/io/File:exists	()Z
    //   16: istore_3
    //   17: iload_3
    //   18: ifeq +99 -> 117
    //   21: new 89	java/io/FileInputStream
    //   24: dup
    //   25: aload_0
    //   26: getfield 71	com/starcor/server/api/manage/ServerApiCachedTask:file	Ljava/io/File;
    //   29: invokespecial 92	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   32: astore 4
    //   34: new 94	java/io/ObjectInputStream
    //   37: dup
    //   38: aload 4
    //   40: invokespecial 97	java/io/ObjectInputStream:<init>	(Ljava/io/InputStream;)V
    //   43: astore 5
    //   45: aload 5
    //   47: invokevirtual 101	java/io/ObjectInputStream:readObject	()Ljava/lang/Object;
    //   50: astore 10
    //   52: aload 4
    //   54: invokevirtual 104	java/io/FileInputStream:close	()V
    //   57: aload 5
    //   59: invokevirtual 105	java/io/ObjectInputStream:close	()V
    //   62: aload 10
    //   64: checkcast 107	[B
    //   67: checkcast 107	[B
    //   70: astore_2
    //   71: aload_0
    //   72: monitorexit
    //   73: aload_2
    //   74: areturn
    //   75: astore 9
    //   77: aload 9
    //   79: invokevirtual 110	java/io/StreamCorruptedException:printStackTrace	()V
    //   82: goto +35 -> 117
    //   85: astore 8
    //   87: aload 8
    //   89: invokevirtual 111	java/io/FileNotFoundException:printStackTrace	()V
    //   92: goto +25 -> 117
    //   95: astore_1
    //   96: aload_0
    //   97: monitorexit
    //   98: aload_1
    //   99: athrow
    //   100: astore 7
    //   102: aload 7
    //   104: invokevirtual 112	java/io/IOException:printStackTrace	()V
    //   107: goto +10 -> 117
    //   110: astore 6
    //   112: aload 6
    //   114: invokevirtual 113	java/lang/ClassNotFoundException:printStackTrace	()V
    //   117: aconst_null
    //   118: astore_2
    //   119: goto -48 -> 71
    //
    // Exception table:
    //   from	to	target	type
    //   21	71	75	java/io/StreamCorruptedException
    //   21	71	85	java/io/FileNotFoundException
    //   2	17	95	finally
    //   21	71	95	finally
    //   77	82	95	finally
    //   87	92	95	finally
    //   102	107	95	finally
    //   112	117	95	finally
    //   21	71	100	java/io/IOException
    //   21	71	110	java/lang/ClassNotFoundException
  }

  protected byte[] localPerform(SCResponse paramSCResponse)
  {
    return null;
  }

  protected final void serializeApiData(final byte[] paramArrayOfByte)
  {
    try
    {
      boolean bool = AppFuncCfg.FUNCTION_USE_CACHED_DATA;
      if (!bool);
      while (true)
      {
        return;
        new Thread()
        {
          // ERROR //
          public void run()
          {
            // Byte code:
            //   0: aload_0
            //   1: getfield 17	com/starcor/server/api/manage/ServerApiCachedTask$1:this$0	Lcom/starcor/server/api/manage/ServerApiCachedTask;
            //   4: invokestatic 31	com/starcor/server/api/manage/ServerApiCachedTask:access$000	(Lcom/starcor/server/api/manage/ServerApiCachedTask;)Ljava/io/File;
            //   7: ifnonnull +4 -> 11
            //   10: return
            //   11: aload_0
            //   12: getfield 17	com/starcor/server/api/manage/ServerApiCachedTask$1:this$0	Lcom/starcor/server/api/manage/ServerApiCachedTask;
            //   15: invokestatic 31	com/starcor/server/api/manage/ServerApiCachedTask:access$000	(Lcom/starcor/server/api/manage/ServerApiCachedTask;)Ljava/io/File;
            //   18: invokevirtual 37	java/io/File:exists	()Z
            //   21: ifeq +125 -> 146
            //   24: aload_0
            //   25: getfield 17	com/starcor/server/api/manage/ServerApiCachedTask$1:this$0	Lcom/starcor/server/api/manage/ServerApiCachedTask;
            //   28: invokestatic 31	com/starcor/server/api/manage/ServerApiCachedTask:access$000	(Lcom/starcor/server/api/manage/ServerApiCachedTask;)Ljava/io/File;
            //   31: invokevirtual 40	java/io/File:delete	()Z
            //   34: pop
            //   35: aload_0
            //   36: getfield 17	com/starcor/server/api/manage/ServerApiCachedTask$1:this$0	Lcom/starcor/server/api/manage/ServerApiCachedTask;
            //   39: invokestatic 31	com/starcor/server/api/manage/ServerApiCachedTask:access$000	(Lcom/starcor/server/api/manage/ServerApiCachedTask;)Ljava/io/File;
            //   42: invokevirtual 43	java/io/File:createNewFile	()Z
            //   45: pop
            //   46: new 45	java/io/FileOutputStream
            //   49: dup
            //   50: aload_0
            //   51: getfield 17	com/starcor/server/api/manage/ServerApiCachedTask$1:this$0	Lcom/starcor/server/api/manage/ServerApiCachedTask;
            //   54: invokestatic 31	com/starcor/server/api/manage/ServerApiCachedTask:access$000	(Lcom/starcor/server/api/manage/ServerApiCachedTask;)Ljava/io/File;
            //   57: invokespecial 48	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
            //   60: astore_2
            //   61: new 50	java/io/ObjectOutputStream
            //   64: dup
            //   65: aload_2
            //   66: invokespecial 53	java/io/ObjectOutputStream:<init>	(Ljava/io/OutputStream;)V
            //   69: astore_3
            //   70: aload_3
            //   71: aload_0
            //   72: getfield 19	com/starcor/server/api/manage/ServerApiCachedTask$1:val$rep	[B
            //   75: invokevirtual 57	java/io/ObjectOutputStream:writeObject	(Ljava/lang/Object;)V
            //   78: aload_3
            //   79: invokevirtual 60	java/io/ObjectOutputStream:close	()V
            //   82: aload_2
            //   83: invokevirtual 61	java/io/FileOutputStream:close	()V
            //   86: return
            //   87: astore 6
            //   89: aload 6
            //   91: invokevirtual 64	java/io/FileNotFoundException:printStackTrace	()V
            //   94: ldc 66
            //   96: ldc 68
            //   98: invokestatic 74	com/starcor/core/utils/Logger:d	(Ljava/lang/String;Ljava/lang/String;)V
            //   101: aload_0
            //   102: getfield 17	com/starcor/server/api/manage/ServerApiCachedTask$1:this$0	Lcom/starcor/server/api/manage/ServerApiCachedTask;
            //   105: invokestatic 31	com/starcor/server/api/manage/ServerApiCachedTask:access$000	(Lcom/starcor/server/api/manage/ServerApiCachedTask;)Ljava/io/File;
            //   108: ifnull -98 -> 10
            //   111: aload_0
            //   112: getfield 17	com/starcor/server/api/manage/ServerApiCachedTask$1:this$0	Lcom/starcor/server/api/manage/ServerApiCachedTask;
            //   115: invokestatic 31	com/starcor/server/api/manage/ServerApiCachedTask:access$000	(Lcom/starcor/server/api/manage/ServerApiCachedTask;)Ljava/io/File;
            //   118: invokevirtual 37	java/io/File:exists	()Z
            //   121: ifeq -111 -> 10
            //   124: aload_0
            //   125: getfield 17	com/starcor/server/api/manage/ServerApiCachedTask$1:this$0	Lcom/starcor/server/api/manage/ServerApiCachedTask;
            //   128: invokestatic 31	com/starcor/server/api/manage/ServerApiCachedTask:access$000	(Lcom/starcor/server/api/manage/ServerApiCachedTask;)Ljava/io/File;
            //   131: invokevirtual 40	java/io/File:delete	()Z
            //   134: pop
            //   135: return
            //   136: astore 10
            //   138: aload 10
            //   140: invokevirtual 75	java/io/IOException:printStackTrace	()V
            //   143: goto -97 -> 46
            //   146: aload_0
            //   147: getfield 17	com/starcor/server/api/manage/ServerApiCachedTask$1:this$0	Lcom/starcor/server/api/manage/ServerApiCachedTask;
            //   150: invokestatic 31	com/starcor/server/api/manage/ServerApiCachedTask:access$000	(Lcom/starcor/server/api/manage/ServerApiCachedTask;)Ljava/io/File;
            //   153: invokevirtual 79	java/io/File:getParentFile	()Ljava/io/File;
            //   156: invokevirtual 37	java/io/File:exists	()Z
            //   159: ifne +14 -> 173
            //   162: aload_0
            //   163: getfield 17	com/starcor/server/api/manage/ServerApiCachedTask$1:this$0	Lcom/starcor/server/api/manage/ServerApiCachedTask;
            //   166: invokestatic 31	com/starcor/server/api/manage/ServerApiCachedTask:access$000	(Lcom/starcor/server/api/manage/ServerApiCachedTask;)Ljava/io/File;
            //   169: invokevirtual 82	java/io/File:mkdirs	()Z
            //   172: pop
            //   173: aload_0
            //   174: getfield 17	com/starcor/server/api/manage/ServerApiCachedTask$1:this$0	Lcom/starcor/server/api/manage/ServerApiCachedTask;
            //   177: invokestatic 31	com/starcor/server/api/manage/ServerApiCachedTask:access$000	(Lcom/starcor/server/api/manage/ServerApiCachedTask;)Ljava/io/File;
            //   180: invokevirtual 37	java/io/File:exists	()Z
            //   183: ifne -137 -> 46
            //   186: aload_0
            //   187: getfield 17	com/starcor/server/api/manage/ServerApiCachedTask$1:this$0	Lcom/starcor/server/api/manage/ServerApiCachedTask;
            //   190: invokestatic 31	com/starcor/server/api/manage/ServerApiCachedTask:access$000	(Lcom/starcor/server/api/manage/ServerApiCachedTask;)Ljava/io/File;
            //   193: invokevirtual 43	java/io/File:createNewFile	()Z
            //   196: pop
            //   197: goto -151 -> 46
            //   200: astore_1
            //   201: aload_1
            //   202: invokevirtual 75	java/io/IOException:printStackTrace	()V
            //   205: goto -159 -> 46
            //   208: astore 4
            //   210: aload 4
            //   212: invokevirtual 75	java/io/IOException:printStackTrace	()V
            //   215: goto -121 -> 94
            //
            // Exception table:
            //   from	to	target	type
            //   46	86	87	java/io/FileNotFoundException
            //   35	46	136	java/io/IOException
            //   186	197	200	java/io/IOException
            //   46	86	208	java/io/IOException
          }
        }
        .start();
      }
    }
    finally
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.server.api.manage.ServerApiCachedTask
 * JD-Core Version:    0.6.2
 */