package com.sohu.mobile.tracing.plugin.a;

import android.content.Context;

public class b
{
  private static a a;
  private static Object b = new Object();

  public b(Context paramContext)
  {
    a = new a(paramContext);
  }

  // ERROR //
  public void a()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: getstatic 15	com/sohu/mobile/tracing/plugin/a/b:b	Ljava/lang/Object;
    //   5: astore_2
    //   6: aload_2
    //   7: monitorenter
    //   8: getstatic 22	com/sohu/mobile/tracing/plugin/a/b:a	Lcom/sohu/mobile/tracing/plugin/a/a;
    //   11: invokevirtual 28	com/sohu/mobile/tracing/plugin/a/a:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   14: astore 4
    //   16: aload 4
    //   18: invokevirtual 33	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   21: aload 4
    //   23: ldc 35
    //   25: invokevirtual 39	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;)V
    //   28: aload 4
    //   30: invokevirtual 42	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   33: aload 4
    //   35: invokevirtual 45	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   38: aload 4
    //   40: invokevirtual 48	android/database/sqlite/SQLiteDatabase:close	()V
    //   43: aload_2
    //   44: monitorexit
    //   45: aload_0
    //   46: monitorexit
    //   47: return
    //   48: astore 6
    //   50: aload 6
    //   52: invokevirtual 52	java/lang/Exception:toString	()Ljava/lang/String;
    //   55: invokestatic 56	com/sohu/mobile/tracing/plugin/d/b:b	(Ljava/lang/String;)V
    //   58: aload 4
    //   60: invokevirtual 45	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   63: goto -25 -> 38
    //   66: astore_3
    //   67: aload_2
    //   68: monitorexit
    //   69: aload_3
    //   70: athrow
    //   71: astore_1
    //   72: aload_0
    //   73: monitorexit
    //   74: aload_1
    //   75: athrow
    //   76: astore 5
    //   78: aload 4
    //   80: invokevirtual 45	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   83: aload 5
    //   85: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   21	33	48	java/lang/Exception
    //   8	21	66	finally
    //   33	38	66	finally
    //   38	45	66	finally
    //   58	63	66	finally
    //   67	69	66	finally
    //   78	86	66	finally
    //   2	8	71	finally
    //   69	71	71	finally
    //   21	33	76	finally
    //   50	58	76	finally
  }

  // ERROR //
  public void a(int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: getstatic 15	com/sohu/mobile/tracing/plugin/a/b:b	Ljava/lang/Object;
    //   5: astore_3
    //   6: aload_3
    //   7: monitorenter
    //   8: getstatic 22	com/sohu/mobile/tracing/plugin/a/b:a	Lcom/sohu/mobile/tracing/plugin/a/a;
    //   11: invokevirtual 28	com/sohu/mobile/tracing/plugin/a/a:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   14: astore 5
    //   16: aload 5
    //   18: invokevirtual 33	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   21: iconst_1
    //   22: anewarray 59	java/lang/String
    //   25: astore 8
    //   27: aload 8
    //   29: iconst_0
    //   30: iload_1
    //   31: invokestatic 63	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   34: aastore
    //   35: aload 5
    //   37: ldc 65
    //   39: aload 8
    //   41: invokevirtual 68	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   44: aload 5
    //   46: invokevirtual 42	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   49: aload 5
    //   51: invokevirtual 45	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   54: aload 5
    //   56: invokevirtual 48	android/database/sqlite/SQLiteDatabase:close	()V
    //   59: aload_3
    //   60: monitorexit
    //   61: aload_0
    //   62: monitorexit
    //   63: return
    //   64: astore 7
    //   66: aload 7
    //   68: invokevirtual 52	java/lang/Exception:toString	()Ljava/lang/String;
    //   71: invokestatic 56	com/sohu/mobile/tracing/plugin/d/b:b	(Ljava/lang/String;)V
    //   74: aload 5
    //   76: invokevirtual 45	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   79: goto -25 -> 54
    //   82: astore 4
    //   84: aload_3
    //   85: monitorexit
    //   86: aload 4
    //   88: athrow
    //   89: astore_2
    //   90: aload_0
    //   91: monitorexit
    //   92: aload_2
    //   93: athrow
    //   94: astore 6
    //   96: aload 5
    //   98: invokevirtual 45	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   101: aload 6
    //   103: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   21	49	64	java/lang/Exception
    //   8	21	82	finally
    //   49	54	82	finally
    //   54	61	82	finally
    //   74	79	82	finally
    //   84	86	82	finally
    //   96	104	82	finally
    //   2	8	89	finally
    //   86	89	89	finally
    //   21	49	94	finally
    //   66	74	94	finally
  }

  // ERROR //
  public void a(com.sohu.mobile.tracing.plugin.b.a parama)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: getstatic 15	com/sohu/mobile/tracing/plugin/a/b:b	Ljava/lang/Object;
    //   5: astore_3
    //   6: aload_3
    //   7: monitorenter
    //   8: getstatic 22	com/sohu/mobile/tracing/plugin/a/b:a	Lcom/sohu/mobile/tracing/plugin/a/a;
    //   11: invokevirtual 28	com/sohu/mobile/tracing/plugin/a/a:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   14: astore 5
    //   16: aload 5
    //   18: invokevirtual 33	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   21: iconst_5
    //   22: anewarray 4	java/lang/Object
    //   25: astore 8
    //   27: aload 8
    //   29: iconst_0
    //   30: aload_1
    //   31: invokevirtual 75	com/sohu/mobile/tracing/plugin/b/a:d	()Lcom/sohu/mobile/tracing/plugin/expose/Plugin_ExposeAdBoby;
    //   34: aastore
    //   35: aload 8
    //   37: iconst_1
    //   38: aload_1
    //   39: invokevirtual 78	com/sohu/mobile/tracing/plugin/b/a:c	()Ljava/lang/String;
    //   42: aastore
    //   43: aload 8
    //   45: iconst_2
    //   46: aload_1
    //   47: invokevirtual 82	com/sohu/mobile/tracing/plugin/b/a:e	()Lcom/sohu/mobile/tracing/plugin/expose/Plugin_VastTag;
    //   50: aastore
    //   51: aload 8
    //   53: iconst_3
    //   54: aload_1
    //   55: invokevirtual 86	com/sohu/mobile/tracing/plugin/b/a:f	()Lcom/sohu/mobile/tracing/plugin/expose/Plugin_ExposeAction;
    //   58: aastore
    //   59: aload 8
    //   61: iconst_4
    //   62: aload_1
    //   63: invokevirtual 89	com/sohu/mobile/tracing/plugin/b/a:b	()I
    //   66: invokestatic 94	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   69: aastore
    //   70: aload 5
    //   72: ldc 96
    //   74: aload 8
    //   76: invokevirtual 68	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   79: aload 5
    //   81: invokevirtual 42	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   84: aload 5
    //   86: invokevirtual 45	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   89: aload 5
    //   91: invokevirtual 48	android/database/sqlite/SQLiteDatabase:close	()V
    //   94: aload_3
    //   95: monitorexit
    //   96: aload_0
    //   97: monitorexit
    //   98: return
    //   99: astore 7
    //   101: aload 7
    //   103: invokevirtual 52	java/lang/Exception:toString	()Ljava/lang/String;
    //   106: invokestatic 56	com/sohu/mobile/tracing/plugin/d/b:b	(Ljava/lang/String;)V
    //   109: aload 5
    //   111: invokevirtual 45	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   114: goto -25 -> 89
    //   117: astore 4
    //   119: aload_3
    //   120: monitorexit
    //   121: aload 4
    //   123: athrow
    //   124: astore_2
    //   125: aload_0
    //   126: monitorexit
    //   127: aload_2
    //   128: athrow
    //   129: astore 6
    //   131: aload 5
    //   133: invokevirtual 45	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   136: aload 6
    //   138: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   21	84	99	java/lang/Exception
    //   8	21	117	finally
    //   84	89	117	finally
    //   89	96	117	finally
    //   109	114	117	finally
    //   119	121	117	finally
    //   131	139	117	finally
    //   2	8	124	finally
    //   121	124	124	finally
    //   21	84	129	finally
    //   101	109	129	finally
  }

  // ERROR //
  public java.util.ArrayList<com.sohu.mobile.tracing.plugin.b.a> b()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: getstatic 15	com/sohu/mobile/tracing/plugin/a/b:b	Ljava/lang/Object;
    //   5: astore_2
    //   6: aload_2
    //   7: monitorenter
    //   8: getstatic 22	com/sohu/mobile/tracing/plugin/a/b:a	Lcom/sohu/mobile/tracing/plugin/a/a;
    //   11: invokevirtual 100	com/sohu/mobile/tracing/plugin/a/a:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   14: astore 4
    //   16: new 102	java/util/ArrayList
    //   19: dup
    //   20: invokespecial 103	java/util/ArrayList:<init>	()V
    //   23: astore 5
    //   25: aload 4
    //   27: invokevirtual 33	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   30: aload 4
    //   32: ldc 105
    //   34: aconst_null
    //   35: invokevirtual 109	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   38: astore 8
    //   40: aload 8
    //   42: invokeinterface 115 1 0
    //   47: ifeq +177 -> 224
    //   50: new 71	com/sohu/mobile/tracing/plugin/b/a
    //   53: dup
    //   54: invokespecial 116	com/sohu/mobile/tracing/plugin/b/a:<init>	()V
    //   57: astore 9
    //   59: aload 9
    //   61: aload 8
    //   63: iconst_0
    //   64: invokeinterface 120 2 0
    //   69: invokevirtual 122	com/sohu/mobile/tracing/plugin/b/a:a	(I)V
    //   72: aload 9
    //   74: aload 8
    //   76: aload 8
    //   78: ldc 124
    //   80: invokeinterface 128 2 0
    //   85: invokeinterface 131 2 0
    //   90: invokevirtual 133	com/sohu/mobile/tracing/plugin/b/a:a	(Ljava/lang/String;)V
    //   93: aload 9
    //   95: aload 8
    //   97: aload 8
    //   99: ldc 135
    //   101: invokeinterface 128 2 0
    //   106: invokeinterface 131 2 0
    //   111: invokestatic 140	com/sohu/mobile/tracing/plugin/expose/Plugin_VastTag:valueOf	(Ljava/lang/String;)Lcom/sohu/mobile/tracing/plugin/expose/Plugin_VastTag;
    //   114: invokevirtual 143	com/sohu/mobile/tracing/plugin/b/a:a	(Lcom/sohu/mobile/tracing/plugin/expose/Plugin_VastTag;)V
    //   117: aload 9
    //   119: aload 8
    //   121: aload 8
    //   123: ldc 145
    //   125: invokeinterface 128 2 0
    //   130: invokeinterface 131 2 0
    //   135: invokestatic 150	com/sohu/mobile/tracing/plugin/expose/Plugin_ExposeAdBoby:valueOf	(Ljava/lang/String;)Lcom/sohu/mobile/tracing/plugin/expose/Plugin_ExposeAdBoby;
    //   138: invokevirtual 153	com/sohu/mobile/tracing/plugin/b/a:a	(Lcom/sohu/mobile/tracing/plugin/expose/Plugin_ExposeAdBoby;)V
    //   141: aload 9
    //   143: aload 8
    //   145: aload 8
    //   147: ldc 155
    //   149: invokeinterface 128 2 0
    //   154: invokeinterface 131 2 0
    //   159: invokestatic 160	com/sohu/mobile/tracing/plugin/expose/Plugin_ExposeAction:valueOf	(Ljava/lang/String;)Lcom/sohu/mobile/tracing/plugin/expose/Plugin_ExposeAction;
    //   162: invokevirtual 163	com/sohu/mobile/tracing/plugin/b/a:a	(Lcom/sohu/mobile/tracing/plugin/expose/Plugin_ExposeAction;)V
    //   165: aload 9
    //   167: aload 8
    //   169: aload 8
    //   171: ldc 165
    //   173: invokeinterface 128 2 0
    //   178: invokeinterface 120 2 0
    //   183: invokevirtual 167	com/sohu/mobile/tracing/plugin/b/a:b	(I)V
    //   186: aload 5
    //   188: aload 9
    //   190: invokevirtual 171	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   193: pop
    //   194: goto -154 -> 40
    //   197: astore 7
    //   199: aload 7
    //   201: invokevirtual 52	java/lang/Exception:toString	()Ljava/lang/String;
    //   204: invokestatic 56	com/sohu/mobile/tracing/plugin/d/b:b	(Ljava/lang/String;)V
    //   207: aload 4
    //   209: invokevirtual 45	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   212: aload 4
    //   214: invokevirtual 48	android/database/sqlite/SQLiteDatabase:close	()V
    //   217: aload_2
    //   218: monitorexit
    //   219: aload_0
    //   220: monitorexit
    //   221: aload 5
    //   223: areturn
    //   224: aload 8
    //   226: invokeinterface 172 1 0
    //   231: aload 4
    //   233: invokevirtual 42	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   236: aload 4
    //   238: invokevirtual 45	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   241: goto -29 -> 212
    //   244: astore_3
    //   245: aload_2
    //   246: monitorexit
    //   247: aload_3
    //   248: athrow
    //   249: astore_1
    //   250: aload_0
    //   251: monitorexit
    //   252: aload_1
    //   253: athrow
    //   254: astore 6
    //   256: aload 4
    //   258: invokevirtual 45	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   261: aload 6
    //   263: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   30	40	197	java/lang/Exception
    //   40	194	197	java/lang/Exception
    //   224	236	197	java/lang/Exception
    //   8	30	244	finally
    //   207	212	244	finally
    //   212	219	244	finally
    //   236	241	244	finally
    //   245	247	244	finally
    //   256	264	244	finally
    //   2	8	249	finally
    //   247	249	249	finally
    //   30	40	254	finally
    //   40	194	254	finally
    //   199	207	254	finally
    //   224	236	254	finally
  }

  // ERROR //
  public void b(int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: getstatic 15	com/sohu/mobile/tracing/plugin/a/b:b	Ljava/lang/Object;
    //   5: astore_3
    //   6: aload_3
    //   7: monitorenter
    //   8: getstatic 22	com/sohu/mobile/tracing/plugin/a/b:a	Lcom/sohu/mobile/tracing/plugin/a/a;
    //   11: invokevirtual 28	com/sohu/mobile/tracing/plugin/a/a:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   14: astore 5
    //   16: aload 5
    //   18: invokevirtual 33	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   21: iconst_1
    //   22: anewarray 59	java/lang/String
    //   25: astore 8
    //   27: aload 8
    //   29: iconst_0
    //   30: iload_1
    //   31: invokestatic 63	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   34: aastore
    //   35: aload 5
    //   37: ldc 174
    //   39: aload 8
    //   41: invokevirtual 68	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   44: aload 5
    //   46: invokevirtual 42	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   49: aload 5
    //   51: invokevirtual 45	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   54: aload 5
    //   56: invokevirtual 48	android/database/sqlite/SQLiteDatabase:close	()V
    //   59: aload_3
    //   60: monitorexit
    //   61: aload_0
    //   62: monitorexit
    //   63: return
    //   64: astore 7
    //   66: aload 7
    //   68: invokevirtual 52	java/lang/Exception:toString	()Ljava/lang/String;
    //   71: invokestatic 56	com/sohu/mobile/tracing/plugin/d/b:b	(Ljava/lang/String;)V
    //   74: aload 5
    //   76: invokevirtual 45	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   79: goto -25 -> 54
    //   82: astore 4
    //   84: aload_3
    //   85: monitorexit
    //   86: aload 4
    //   88: athrow
    //   89: astore_2
    //   90: aload_0
    //   91: monitorexit
    //   92: aload_2
    //   93: athrow
    //   94: astore 6
    //   96: aload 5
    //   98: invokevirtual 45	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   101: aload 6
    //   103: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   21	49	64	java/lang/Exception
    //   8	21	82	finally
    //   49	54	82	finally
    //   54	61	82	finally
    //   74	79	82	finally
    //   84	86	82	finally
    //   96	104	82	finally
    //   2	8	89	finally
    //   86	89	89	finally
    //   21	49	94	finally
    //   66	74	94	finally
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mobile.tracing.plugin.a.b
 * JD-Core Version:    0.6.2
 */