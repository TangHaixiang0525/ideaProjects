package com.miaozhen.mzmonitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

class b
{
  private static b b = null;
  private Context a;

  protected b(Context paramContext)
  {
    this.a = paramContext;
  }

  public static b a(Context paramContext)
  {
    try
    {
      if (b == null)
        b = new b(paramContext.getApplicationContext());
      b localb = b;
      return localb;
    }
    finally
    {
    }
  }

  public void a()
  {
    c localc = new c(this, this.a, "mzmonitor", null, 6);
    SQLiteDatabase localSQLiteDatabase = localc.getReadableDatabase();
    Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM mzcaches ORDER BY timestamp ASC LIMIT 1", null);
    if (localCursor.moveToNext())
    {
      String[] arrayOfString = new String[2];
      arrayOfString[0] = localCursor.getString(localCursor.getColumnIndex("cacheId"));
      arrayOfString[1] = localCursor.getString(localCursor.getColumnIndex("url"));
      localSQLiteDatabase.execSQL("DELETE FROM mzcaches WHERE cacheId = ? AND url = ?", arrayOfString);
    }
    localCursor.close();
    localc.close();
  }

  public void a(a parama)
  {
    try
    {
      if (b())
        a();
      c localc = new c(this, this.a, "mzmonitor", null, 6);
      SQLiteDatabase localSQLiteDatabase = localc.getWritableDatabase();
      parama.a(1 + parama.h());
      localSQLiteDatabase.insert("mzcaches", null, parama.j());
      Log.d("insert Cache", parama.toString());
      localc.close();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  // ERROR //
  public void a(a parama, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iload_2
    //   3: ifeq +19 -> 22
    //   6: aload_0
    //   7: aload_1
    //   8: invokevirtual 132	com/miaozhen/mzmonitor/b:d	(Lcom/miaozhen/mzmonitor/a;)Z
    //   11: ifeq +8 -> 19
    //   14: aload_0
    //   15: aload_1
    //   16: invokevirtual 135	com/miaozhen/mzmonitor/b:c	(Lcom/miaozhen/mzmonitor/a;)V
    //   19: aload_0
    //   20: monitorexit
    //   21: return
    //   22: aload_0
    //   23: aload_1
    //   24: invokevirtual 132	com/miaozhen/mzmonitor/b:d	(Lcom/miaozhen/mzmonitor/a;)Z
    //   27: ifeq +32 -> 59
    //   30: aload_0
    //   31: aload_1
    //   32: invokevirtual 138	com/miaozhen/mzmonitor/b:f	(Lcom/miaozhen/mzmonitor/a;)Z
    //   35: ifeq +11 -> 46
    //   38: aload_0
    //   39: aload_1
    //   40: invokevirtual 135	com/miaozhen/mzmonitor/b:c	(Lcom/miaozhen/mzmonitor/a;)V
    //   43: goto -24 -> 19
    //   46: aload_0
    //   47: aload_1
    //   48: invokevirtual 140	com/miaozhen/mzmonitor/b:b	(Lcom/miaozhen/mzmonitor/a;)V
    //   51: goto -32 -> 19
    //   54: astore_3
    //   55: aload_0
    //   56: monitorexit
    //   57: aload_3
    //   58: athrow
    //   59: aload_0
    //   60: aload_1
    //   61: invokevirtual 142	com/miaozhen/mzmonitor/b:a	(Lcom/miaozhen/mzmonitor/a;)V
    //   64: goto -45 -> 19
    //   67: astore 4
    //   69: goto -50 -> 19
    //
    // Exception table:
    //   from	to	target	type
    //   6	19	54	finally
    //   22	43	54	finally
    //   46	51	54	finally
    //   59	64	54	finally
    //   6	19	67	java/lang/Exception
    //   22	43	67	java/lang/Exception
    //   46	51	67	java/lang/Exception
    //   59	64	67	java/lang/Exception
  }

  public void b(a parama)
  {
    c localc = new c(this, this.a, "mzmonitor", null, 6);
    SQLiteDatabase localSQLiteDatabase = localc.getWritableDatabase();
    parama.a(1 + parama.h());
    ContentValues localContentValues = parama.j();
    String[] arrayOfString = new String[2];
    arrayOfString[0] = parama.e();
    arrayOfString[1] = parama.a();
    localSQLiteDatabase.update("mzcaches", localContentValues, "cacheId = ? AND url = ?", arrayOfString);
    localc.close();
  }

  public boolean b()
  {
    return c() >= MZSdkProfile.getMaxLogItems(this.a);
  }

  // ERROR //
  public int c()
  {
    // Byte code:
    //   0: new 29	com/miaozhen/mzmonitor/c
    //   3: dup
    //   4: aload_0
    //   5: aload_0
    //   6: getfield 18	com/miaozhen/mzmonitor/b:a	Landroid/content/Context;
    //   9: ldc 31
    //   11: aconst_null
    //   12: bipush 6
    //   14: invokespecial 34	com/miaozhen/mzmonitor/c:<init>	(Lcom/miaozhen/mzmonitor/b;Landroid/content/Context;Ljava/lang/String;Landroid/database/sqlite/SQLiteDatabase$CursorFactory;I)V
    //   17: astore_1
    //   18: aload_1
    //   19: invokevirtual 38	com/miaozhen/mzmonitor/c:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   22: ldc 163
    //   24: aconst_null
    //   25: invokevirtual 46	android/database/sqlite/SQLiteDatabase:rawQuery	(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;
    //   28: astore 5
    //   30: aload 5
    //   32: invokeinterface 166 1 0
    //   37: pop
    //   38: aload 5
    //   40: iconst_0
    //   41: invokeinterface 170 2 0
    //   46: istore 7
    //   48: aload 5
    //   50: invokeinterface 86 1 0
    //   55: aload_1
    //   56: invokevirtual 87	com/miaozhen/mzmonitor/c:close	()V
    //   59: iload 7
    //   61: ireturn
    //   62: astore_2
    //   63: aload_2
    //   64: astore_3
    //   65: iconst_0
    //   66: istore 4
    //   68: aload_3
    //   69: invokevirtual 128	java/lang/Exception:printStackTrace	()V
    //   72: iload 4
    //   74: ireturn
    //   75: astore 8
    //   77: iload 7
    //   79: istore 4
    //   81: aload 8
    //   83: astore_3
    //   84: goto -16 -> 68
    //
    // Exception table:
    //   from	to	target	type
    //   0	48	62	java/lang/Exception
    //   48	59	75	java/lang/Exception
  }

  public void c(a parama)
  {
    c localc = new c(this, this.a, "mzmonitor", null, 6);
    SQLiteDatabase localSQLiteDatabase = localc.getWritableDatabase();
    String[] arrayOfString = new String[2];
    arrayOfString[0] = parama.e();
    arrayOfString[1] = parama.a();
    localSQLiteDatabase.delete("mzcaches", "cacheId = ? AND url = ?", arrayOfString);
    localc.close();
  }

  public List<a> d()
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      c localc = new c(this, this.a, "mzmonitor", null, 6);
      Cursor localCursor = localc.getReadableDatabase().query("mzcaches", new String[] { "cacheId", "url", "timestamp", "times" }, null, null, null, null, null);
      if (localCursor.getCount() > 0);
      while (true)
      {
        if (!localCursor.moveToNext())
        {
          localCursor.close();
          localc.close();
          return localArrayList;
        }
        a locala = new a();
        locala.e(localCursor.getString(localCursor.getColumnIndex("cacheId")));
        locala.a(localCursor.getString(localCursor.getColumnIndex("url")));
        locala.a(localCursor.getLong(localCursor.getColumnIndex("timestamp")));
        locala.a(localCursor.getShort(localCursor.getColumnIndex("times")));
        localArrayList.add(locala);
      }
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public boolean d(a parama)
  {
    c localc = new c(this, this.a, "mzmonitor", null, 6);
    SQLiteDatabase localSQLiteDatabase = localc.getReadableDatabase();
    String[] arrayOfString = new String[2];
    arrayOfString[0] = parama.e();
    arrayOfString[1] = parama.a();
    Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM mzcaches WHERE cacheId = ? and url = ?", arrayOfString);
    if (localCursor.moveToNext());
    for (boolean bool = true; ; bool = false)
    {
      localCursor.close();
      localc.close();
      return bool;
    }
  }

  public boolean e(a parama)
  {
    long l1 = parama.g();
    long l2 = MZSdkProfile.getLogExpiresIn(this.a);
    return MZUtility.currentUnixTimestamp() - l1 > l2;
  }

  public boolean f(a parama)
  {
    if (parama.h() >= MZSdkProfile.getMaxLogRetryTimes(this.a))
      return true;
    return e(parama);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.b
 * JD-Core Version:    0.6.2
 */