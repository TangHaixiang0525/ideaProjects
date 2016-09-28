package com.sohu.applist.b;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class a
{
  private c a;

  public a(Context paramContext)
  {
    this.a = new c(paramContext);
  }

  // ERROR //
  public java.util.ArrayList<com.sohu.applist.a.c> a()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 22
    //   4: monitorenter
    //   5: aload_0
    //   6: getfield 17	com/sohu/applist/b/a:a	Lcom/sohu/applist/b/c;
    //   9: invokevirtual 26	com/sohu/applist/b/c:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   12: astore_3
    //   13: new 28	java/util/ArrayList
    //   16: dup
    //   17: invokespecial 29	java/util/ArrayList:<init>	()V
    //   20: astore 4
    //   22: aload_3
    //   23: invokevirtual 34	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   26: aload_3
    //   27: ldc 36
    //   29: iconst_0
    //   30: anewarray 38	java/lang/String
    //   33: invokevirtual 42	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   36: astore 7
    //   38: aload 7
    //   40: invokeinterface 48 1 0
    //   45: ifeq +103 -> 148
    //   48: aload 4
    //   50: new 50	com/sohu/applist/a/c
    //   53: dup
    //   54: aload 7
    //   56: aload 7
    //   58: ldc 51
    //   60: invokeinterface 55 2 0
    //   65: invokeinterface 59 2 0
    //   70: invokestatic 65	com/sohu/upload/b/c:d	(Ljava/lang/String;)Ljava/lang/String;
    //   73: aload 7
    //   75: aload 7
    //   77: ldc 67
    //   79: invokeinterface 55 2 0
    //   84: invokeinterface 71 2 0
    //   89: invokespecial 74	com/sohu/applist/a/c:<init>	(Ljava/lang/String;I)V
    //   92: invokevirtual 78	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   95: pop
    //   96: goto -58 -> 38
    //   99: astore 6
    //   101: new 80	java/lang/StringBuilder
    //   104: dup
    //   105: invokespecial 81	java/lang/StringBuilder:<init>	()V
    //   108: ldc 83
    //   110: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   113: aload 6
    //   115: invokevirtual 91	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   118: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: ldc 93
    //   123: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   129: invokestatic 101	com/sohu/upload/a/a:b	(Ljava/lang/String;)V
    //   132: aload_3
    //   133: invokevirtual 104	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   136: aload_3
    //   137: invokevirtual 107	android/database/sqlite/SQLiteDatabase:close	()V
    //   140: ldc 22
    //   142: monitorexit
    //   143: aload_0
    //   144: monitorexit
    //   145: aload 4
    //   147: areturn
    //   148: aload 7
    //   150: invokeinterface 108 1 0
    //   155: aload_3
    //   156: invokevirtual 111	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   159: aload_3
    //   160: invokevirtual 104	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   163: goto -27 -> 136
    //   166: astore_2
    //   167: ldc 22
    //   169: monitorexit
    //   170: aload_2
    //   171: athrow
    //   172: astore_1
    //   173: aload_0
    //   174: monitorexit
    //   175: aload_1
    //   176: athrow
    //   177: astore 5
    //   179: aload_3
    //   180: invokevirtual 104	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   183: aload 5
    //   185: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   26	38	99	java/lang/Exception
    //   38	96	99	java/lang/Exception
    //   148	159	99	java/lang/Exception
    //   5	26	166	finally
    //   132	136	166	finally
    //   136	143	166	finally
    //   159	163	166	finally
    //   167	170	166	finally
    //   179	186	166	finally
    //   2	5	172	finally
    //   170	172	172	finally
    //   26	38	177	finally
    //   38	96	177	finally
    //   101	132	177	finally
    //   148	159	177	finally
  }

  // ERROR //
  public void a(String paramString)
  {
    // Byte code:
    //   0: ldc 22
    //   2: monitorenter
    //   3: aload_0
    //   4: getfield 17	com/sohu/applist/b/a:a	Lcom/sohu/applist/b/c;
    //   7: invokevirtual 114	com/sohu/applist/b/c:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   10: astore_3
    //   11: aload_1
    //   12: invokestatic 117	com/sohu/upload/b/c:c	(Ljava/lang/String;)Ljava/lang/String;
    //   15: astore 4
    //   17: aload_3
    //   18: invokevirtual 34	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   21: aload_3
    //   22: ldc 119
    //   24: iconst_1
    //   25: anewarray 4	java/lang/Object
    //   28: dup
    //   29: iconst_0
    //   30: aload 4
    //   32: aastore
    //   33: invokevirtual 123	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   36: aload_3
    //   37: invokevirtual 111	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   40: aload_3
    //   41: invokevirtual 104	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   44: aload_3
    //   45: invokevirtual 107	android/database/sqlite/SQLiteDatabase:close	()V
    //   48: ldc 22
    //   50: monitorexit
    //   51: return
    //   52: astore 6
    //   54: new 80	java/lang/StringBuilder
    //   57: dup
    //   58: invokespecial 81	java/lang/StringBuilder:<init>	()V
    //   61: ldc 83
    //   63: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: aload 6
    //   68: invokevirtual 91	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   71: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   74: ldc 125
    //   76: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   82: invokestatic 101	com/sohu/upload/a/a:b	(Ljava/lang/String;)V
    //   85: aload_3
    //   86: invokevirtual 104	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   89: goto -45 -> 44
    //   92: astore_2
    //   93: ldc 22
    //   95: monitorexit
    //   96: aload_2
    //   97: athrow
    //   98: astore 5
    //   100: aload_3
    //   101: invokevirtual 104	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   104: aload 5
    //   106: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   21	40	52	java/lang/Exception
    //   3	21	92	finally
    //   40	44	92	finally
    //   44	51	92	finally
    //   85	89	92	finally
    //   93	96	92	finally
    //   100	107	92	finally
    //   21	40	98	finally
    //   54	85	98	finally
  }

  // ERROR //
  public com.sohu.applist.a.c b()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 22
    //   4: monitorenter
    //   5: aload_0
    //   6: getfield 17	com/sohu/applist/b/a:a	Lcom/sohu/applist/b/c;
    //   9: invokevirtual 26	com/sohu/applist/b/c:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   12: astore_3
    //   13: new 50	com/sohu/applist/a/c
    //   16: dup
    //   17: invokespecial 127	com/sohu/applist/a/c:<init>	()V
    //   20: astore 4
    //   22: aload_3
    //   23: invokevirtual 34	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   26: aload_3
    //   27: ldc 129
    //   29: iconst_0
    //   30: anewarray 38	java/lang/String
    //   33: invokevirtual 42	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   36: astore 7
    //   38: aload 7
    //   40: invokeinterface 48 1 0
    //   45: ifeq +100 -> 145
    //   48: aload 4
    //   50: aload 7
    //   52: aload 7
    //   54: ldc 51
    //   56: invokeinterface 55 2 0
    //   61: invokeinterface 59 2 0
    //   66: invokestatic 65	com/sohu/upload/b/c:d	(Ljava/lang/String;)Ljava/lang/String;
    //   69: invokevirtual 131	com/sohu/applist/a/c:a	(Ljava/lang/String;)V
    //   72: aload 4
    //   74: aload 7
    //   76: aload 7
    //   78: ldc 67
    //   80: invokeinterface 55 2 0
    //   85: invokeinterface 71 2 0
    //   90: invokevirtual 134	com/sohu/applist/a/c:d	(I)V
    //   93: goto -55 -> 38
    //   96: astore 6
    //   98: new 80	java/lang/StringBuilder
    //   101: dup
    //   102: invokespecial 81	java/lang/StringBuilder:<init>	()V
    //   105: ldc 83
    //   107: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   110: aload 6
    //   112: invokevirtual 91	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   115: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   118: ldc 93
    //   120: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   123: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   126: invokestatic 101	com/sohu/upload/a/a:b	(Ljava/lang/String;)V
    //   129: aload_3
    //   130: invokevirtual 104	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   133: aload_3
    //   134: invokevirtual 107	android/database/sqlite/SQLiteDatabase:close	()V
    //   137: ldc 22
    //   139: monitorexit
    //   140: aload_0
    //   141: monitorexit
    //   142: aload 4
    //   144: areturn
    //   145: aload 7
    //   147: invokeinterface 108 1 0
    //   152: aload_3
    //   153: invokevirtual 111	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   156: aload_3
    //   157: invokevirtual 104	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   160: goto -27 -> 133
    //   163: astore_2
    //   164: ldc 22
    //   166: monitorexit
    //   167: aload_2
    //   168: athrow
    //   169: astore_1
    //   170: aload_0
    //   171: monitorexit
    //   172: aload_1
    //   173: athrow
    //   174: astore 5
    //   176: aload_3
    //   177: invokevirtual 104	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   180: aload 5
    //   182: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   26	38	96	java/lang/Exception
    //   38	93	96	java/lang/Exception
    //   145	156	96	java/lang/Exception
    //   5	26	163	finally
    //   129	133	163	finally
    //   133	140	163	finally
    //   156	160	163	finally
    //   164	167	163	finally
    //   176	183	163	finally
    //   2	5	169	finally
    //   167	169	169	finally
    //   26	38	174	finally
    //   38	93	174	finally
    //   98	129	174	finally
    //   145	156	174	finally
  }

  public boolean b(String paramString)
  {
    boolean bool = true;
    try
    {
      try
      {
        SQLiteDatabase localSQLiteDatabase = this.a.getReadableDatabase();
        String str = com.sohu.upload.b.c.c(paramString);
        localSQLiteDatabase.beginTransaction();
        try
        {
          Cursor localCursor = localSQLiteDatabase.rawQuery("select a from mz_a where a=?", new String[] { str });
          if (localCursor.moveToNext());
          while (true)
          {
            localCursor.close();
            localSQLiteDatabase.setTransactionSuccessful();
            localSQLiteDatabase.endTransaction();
            localSQLiteDatabase.close();
            return bool;
            bool = false;
          }
        }
        catch (Exception localException)
        {
          while (true)
          {
            com.sohu.upload.a.a.b("�쳣:" + localException.getMessage());
            localSQLiteDatabase.endTransaction();
            bool = false;
          }
        }
        finally
        {
          localSQLiteDatabase.endTransaction();
        }
      }
      finally
      {
      }
    }
    finally
    {
    }
  }

  // ERROR //
  public void c(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 22
    //   4: monitorenter
    //   5: aload_0
    //   6: getfield 17	com/sohu/applist/b/a:a	Lcom/sohu/applist/b/c;
    //   9: invokevirtual 114	com/sohu/applist/b/c:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   12: astore 4
    //   14: aload 4
    //   16: invokevirtual 34	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   19: aload_1
    //   20: invokestatic 117	com/sohu/upload/b/c:c	(Ljava/lang/String;)Ljava/lang/String;
    //   23: astore 5
    //   25: aload 4
    //   27: ldc 139
    //   29: iconst_1
    //   30: anewarray 4	java/lang/Object
    //   33: dup
    //   34: iconst_0
    //   35: aload 5
    //   37: aastore
    //   38: invokevirtual 123	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   41: aload 4
    //   43: invokevirtual 111	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   46: aload 4
    //   48: invokevirtual 104	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   51: aload 4
    //   53: invokevirtual 107	android/database/sqlite/SQLiteDatabase:close	()V
    //   56: ldc 22
    //   58: monitorexit
    //   59: aload_0
    //   60: monitorexit
    //   61: return
    //   62: astore 7
    //   64: new 80	java/lang/StringBuilder
    //   67: dup
    //   68: invokespecial 81	java/lang/StringBuilder:<init>	()V
    //   71: ldc 83
    //   73: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   76: aload 7
    //   78: invokevirtual 91	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   81: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   84: ldc 141
    //   86: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   89: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   92: invokestatic 101	com/sohu/upload/a/a:b	(Ljava/lang/String;)V
    //   95: aload 4
    //   97: invokevirtual 104	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   100: goto -49 -> 51
    //   103: astore_3
    //   104: ldc 22
    //   106: monitorexit
    //   107: aload_3
    //   108: athrow
    //   109: astore_2
    //   110: aload_0
    //   111: monitorexit
    //   112: aload_2
    //   113: athrow
    //   114: astore 6
    //   116: aload 4
    //   118: invokevirtual 104	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   121: aload 6
    //   123: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   25	46	62	java/lang/Exception
    //   5	25	103	finally
    //   46	51	103	finally
    //   51	59	103	finally
    //   95	100	103	finally
    //   104	107	103	finally
    //   116	124	103	finally
    //   2	5	109	finally
    //   107	109	109	finally
    //   25	46	114	finally
    //   64	95	114	finally
  }

  // ERROR //
  public void d(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 22
    //   4: monitorenter
    //   5: aload_0
    //   6: getfield 17	com/sohu/applist/b/a:a	Lcom/sohu/applist/b/c;
    //   9: invokevirtual 114	com/sohu/applist/b/c:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   12: astore 4
    //   14: aload 4
    //   16: invokevirtual 34	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   19: new 80	java/lang/StringBuilder
    //   22: dup
    //   23: invokespecial 81	java/lang/StringBuilder:<init>	()V
    //   26: ldc 143
    //   28: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   31: aload_1
    //   32: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   38: invokestatic 101	com/sohu/upload/a/a:b	(Ljava/lang/String;)V
    //   41: aload_1
    //   42: invokestatic 117	com/sohu/upload/b/c:c	(Ljava/lang/String;)Ljava/lang/String;
    //   45: astore 5
    //   47: aload 4
    //   49: ldc 145
    //   51: ldc 147
    //   53: iconst_1
    //   54: anewarray 38	java/lang/String
    //   57: dup
    //   58: iconst_0
    //   59: aload 5
    //   61: aastore
    //   62: invokevirtual 151	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   65: pop
    //   66: aload 4
    //   68: invokevirtual 111	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   71: aload 4
    //   73: invokevirtual 104	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   76: aload 4
    //   78: invokevirtual 107	android/database/sqlite/SQLiteDatabase:close	()V
    //   81: ldc 22
    //   83: monitorexit
    //   84: aload_0
    //   85: monitorexit
    //   86: return
    //   87: astore 7
    //   89: new 80	java/lang/StringBuilder
    //   92: dup
    //   93: invokespecial 81	java/lang/StringBuilder:<init>	()V
    //   96: ldc 83
    //   98: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   101: aload 7
    //   103: invokevirtual 91	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   106: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   109: ldc 153
    //   111: invokevirtual 87	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   114: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   117: invokestatic 101	com/sohu/upload/a/a:b	(Ljava/lang/String;)V
    //   120: aload 4
    //   122: invokevirtual 104	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   125: goto -49 -> 76
    //   128: astore_3
    //   129: ldc 22
    //   131: monitorexit
    //   132: aload_3
    //   133: athrow
    //   134: astore_2
    //   135: aload_0
    //   136: monitorexit
    //   137: aload_2
    //   138: athrow
    //   139: astore 6
    //   141: aload 4
    //   143: invokevirtual 104	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   146: aload 6
    //   148: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   47	71	87	java/lang/Exception
    //   5	47	128	finally
    //   71	76	128	finally
    //   76	84	128	finally
    //   120	125	128	finally
    //   129	132	128	finally
    //   141	149	128	finally
    //   2	5	134	finally
    //   132	134	134	finally
    //   47	71	139	finally
    //   89	120	139	finally
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.applist.b.a
 * JD-Core Version:    0.6.2
 */