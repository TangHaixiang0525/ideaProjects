package com.starcor.hunan.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.db.ReportMessageColumns;
import com.starcor.hunan.domain.ReportMessageEntity;
import java.util.ArrayList;
import java.util.List;

public class ReportMessageDao
  implements IReportMessageDao
{
  private DBProvider mDBProvider;

  public ReportMessageDao(Context paramContext)
  {
    this.mDBProvider = new DBProvider(paramContext);
  }

  public boolean deleteReportMessage(ReportMessageEntity paramReportMessageEntity)
  {
    if (paramReportMessageEntity != null)
    {
      String[] arrayOfString = new String[1];
      arrayOfString[0] = String.valueOf(paramReportMessageEntity.id);
      this.mDBProvider.delete(ReportMessageColumns.CONTENT_URI, "_id=?", arrayOfString);
      return true;
    }
    return false;
  }

  public int getMessageCount()
  {
    int i = -1;
    Cursor localCursor = null;
    String[] arrayOfString = { "_id" };
    try
    {
      localCursor = this.mDBProvider.query(ReportMessageColumns.CONTENT_URI, arrayOfString, null, null, null);
      if (localCursor != null)
      {
        int j = localCursor.getCount();
        i = j;
      }
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return i;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  public boolean insertReportMessage(ReportMessageEntity paramReportMessageEntity)
  {
    if (paramReportMessageEntity != null)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("msgId", Integer.valueOf(paramReportMessageEntity.msgId));
      localContentValues.put("desc", paramReportMessageEntity.desc);
      localContentValues.put("content", paramReportMessageEntity.content);
      if (paramReportMessageEntity.isLiveReport);
      for (int i = 1; ; i = 0)
      {
        localContentValues.put("isLiveReport", Integer.valueOf(i));
        if (this.mDBProvider.insert(ReportMessageColumns.CONTENT_URI, localContentValues) == null)
          break;
        return true;
      }
      return false;
    }
    return false;
  }

  public boolean insertReportMessages(List<ReportMessageEntity> paramList)
  {
    if ((paramList != null) && (paramList.size() > 0));
    return false;
  }

  public ReportMessageEntity queryFirst()
  {
    List localList = queryFirst(1);
    if (localList.size() > 0)
      return (ReportMessageEntity)localList.get(0);
    return null;
  }

  public List<ReportMessageEntity> queryFirst(int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramInt <= 0)
      paramInt = 1;
    Cursor localCursor = null;
    try
    {
      localCursor = this.mDBProvider.query(ReportMessageColumns.CONTENT_URI, null, null, null, null, String.valueOf(paramInt));
      if ((localCursor != null) && (localCursor.moveToFirst()))
        while (true)
        {
          if (localCursor.isAfterLast())
            break label213;
          ReportMessageEntity localReportMessageEntity = new ReportMessageEntity();
          localReportMessageEntity.id = localCursor.getInt(localCursor.getColumnIndexOrThrow("_id"));
          localReportMessageEntity.msgId = localCursor.getInt(localCursor.getColumnIndexOrThrow("msgId"));
          localReportMessageEntity.desc = localCursor.getString(localCursor.getColumnIndexOrThrow("desc"));
          localReportMessageEntity.content = localCursor.getString(localCursor.getColumnIndexOrThrow("content"));
          if (localCursor.getInt(localCursor.getColumnIndexOrThrow("isLiveReport")) <= 0)
            break;
          bool = true;
          localReportMessageEntity.isLiveReport = bool;
          localArrayList.add(localReportMessageEntity);
          localCursor.moveToNext();
        }
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        return localArrayList;
        boolean bool = false;
      }
      label213: return localArrayList;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.db.dao.ReportMessageDao
 * JD-Core Version:    0.6.2
 */