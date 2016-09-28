package com.sohu.app.ads.sdk.a;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.sohu.app.ads.sdk.model.e;
import com.sohu.app.ads.sdk.model.emu.DownloadEmue;
import java.util.ArrayList;

public class b
{
  private static a a;
  private static Object b = new Object();

  public b(Context paramContext)
  {
    a = new a(paramContext);
  }

  public ArrayList<e> a()
  {
    SQLiteDatabase localSQLiteDatabase;
    ArrayList localArrayList;
    synchronized (b)
    {
      localSQLiteDatabase = a.getReadableDatabase();
      localArrayList = new ArrayList();
      localSQLiteDatabase.beginTransaction();
    }
    try
    {
      localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM download_url;", null);
      while (localCursor.moveToNext())
      {
        e locale = new e();
        String str1 = localCursor.getString(localCursor.getColumnIndex("url"));
        String str2 = localCursor.getString(localCursor.getColumnIndex("status"));
        int i = localCursor.getInt(localCursor.getColumnIndex("length"));
        locale.a(str1);
        locale.a(i);
        locale.a(DownloadEmue.valueOf(str2));
        localArrayList.add(locale);
      }
    }
    catch (Exception localException)
    {
      Cursor localCursor;
      com.sohu.app.ads.sdk.c.a.c(localException.toString());
      localSQLiteDatabase.endTransaction();
      while (true)
      {
        localSQLiteDatabase.close();
        return localArrayList;
        localCursor.close();
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
      }
      localObject2 = finally;
      throw localObject2;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  public ArrayList<e> a(DownloadEmue paramDownloadEmue)
  {
    SQLiteDatabase localSQLiteDatabase;
    ArrayList localArrayList;
    synchronized (b)
    {
      localSQLiteDatabase = a.getReadableDatabase();
      localArrayList = new ArrayList();
      localSQLiteDatabase.beginTransaction();
    }
    try
    {
      String[] arrayOfString = new String[1];
      arrayOfString[0] = paramDownloadEmue.toString();
      localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM download_url where status=?;", arrayOfString);
      while (localCursor.moveToNext())
      {
        e locale = new e();
        String str1 = localCursor.getString(localCursor.getColumnIndex("url"));
        String str2 = localCursor.getString(localCursor.getColumnIndex("status"));
        int i = localCursor.getInt(localCursor.getColumnIndex("length"));
        locale.a(str1);
        locale.a(i);
        locale.a(DownloadEmue.valueOf(str2));
        localArrayList.add(locale);
      }
    }
    catch (Exception localException)
    {
      Cursor localCursor;
      com.sohu.app.ads.sdk.c.a.c(localException.toString());
      localSQLiteDatabase.endTransaction();
      while (true)
      {
        localSQLiteDatabase.close();
        return localArrayList;
        localCursor.close();
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
      }
      localObject2 = finally;
      throw localObject2;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  public void a(e parame)
  {
    SQLiteDatabase localSQLiteDatabase;
    synchronized (b)
    {
      localSQLiteDatabase = a.getWritableDatabase();
      localSQLiteDatabase.beginTransaction();
    }
    try
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = parame.a();
      arrayOfObject[1] = parame.b();
      arrayOfObject[2] = Integer.valueOf(parame.c());
      localSQLiteDatabase.execSQL("insert into download_url(url,status,length) values(?,?,?)", arrayOfObject);
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      localSQLiteDatabase.close();
      return;
    }
    catch (Exception localException)
    {
      while (true)
      {
        com.sohu.app.ads.sdk.c.a.c(localException.toString());
        localSQLiteDatabase.endTransaction();
      }
      localObject2 = finally;
      throw localObject2;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  public void a(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase;
    synchronized (b)
    {
      localSQLiteDatabase = a.getWritableDatabase();
      localSQLiteDatabase.beginTransaction();
    }
    try
    {
      localSQLiteDatabase.execSQL("DELETE FROM download_url WHERE url=?;", new String[] { paramString });
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      localSQLiteDatabase.close();
      return;
    }
    catch (Exception localException)
    {
      while (true)
      {
        com.sohu.app.ads.sdk.c.a.c(localException.toString());
        localSQLiteDatabase.endTransaction();
      }
      localObject2 = finally;
      throw localObject2;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  public e b(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase;
    e locale;
    synchronized (b)
    {
      localSQLiteDatabase = a.getReadableDatabase();
      localSQLiteDatabase.beginTransaction();
      locale = new e();
    }
    try
    {
      Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM download_url where url=?;", new String[] { paramString });
      if (localCursor.moveToNext())
      {
        String str1 = localCursor.getString(localCursor.getColumnIndex("url"));
        String str2 = localCursor.getString(localCursor.getColumnIndex("status"));
        int i = localCursor.getInt(localCursor.getColumnIndex("length"));
        locale.a(str1);
        locale.a(i);
        locale.a(DownloadEmue.valueOf(str2));
      }
      localCursor.close();
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      localSQLiteDatabase.close();
      return locale;
    }
    catch (Exception localException)
    {
      while (true)
      {
        com.sohu.app.ads.sdk.c.a.c(localException.toString());
        localSQLiteDatabase.endTransaction();
      }
      localObject2 = finally;
      throw localObject2;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  public void b(e parame)
  {
    SQLiteDatabase localSQLiteDatabase;
    synchronized (b)
    {
      localSQLiteDatabase = a.getWritableDatabase();
      localSQLiteDatabase.beginTransaction();
    }
    try
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = DownloadEmue.valueOf(parame.b().toString());
      arrayOfObject[1] = Integer.valueOf(parame.c());
      arrayOfObject[2] = parame.a();
      localSQLiteDatabase.execSQL("update download_url set status=?,length=? where url=?", arrayOfObject);
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      localSQLiteDatabase.close();
      return;
    }
    catch (Exception localException)
    {
      while (true)
      {
        com.sohu.app.ads.sdk.c.a.c(localException.toString());
        localSQLiteDatabase.endTransaction();
      }
      localObject2 = finally;
      throw localObject2;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  public boolean c(String paramString)
  {
    boolean bool1 = false;
    SQLiteDatabase localSQLiteDatabase;
    synchronized (b)
    {
      localSQLiteDatabase = a.getReadableDatabase();
      localSQLiteDatabase.beginTransaction();
    }
    try
    {
      Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT * FROM download_url where url=?;", new String[] { paramString });
      boolean bool2 = localCursor.moveToNext();
      bool1 = false;
      if (bool2)
        bool1 = true;
      localCursor.close();
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      localSQLiteDatabase.close();
      return bool1;
    }
    catch (Exception localException)
    {
      while (true)
      {
        com.sohu.app.ads.sdk.c.a.c(localException.toString());
        localSQLiteDatabase.endTransaction();
      }
      localObject2 = finally;
      throw localObject2;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.a.b
 * JD-Core Version:    0.6.2
 */