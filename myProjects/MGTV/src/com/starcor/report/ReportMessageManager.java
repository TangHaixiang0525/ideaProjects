package com.starcor.report;

import android.content.Context;
import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.db.dao.IReportMessageDao;
import com.starcor.hunan.db.dao.ReportMessageDao;
import com.starcor.hunan.domain.ReportMessageEntity;
import java.util.List;

public class ReportMessageManager
{
  private static final int MESSAGE_MAX_COUNT = 1000;
  private static final int REMOVE_COUNT = 1;
  private static final String TAG = ReportMessageManager.class.getSimpleName();
  private static ReportMessageManager instance = null;
  private Context mContext;
  private IReportMessageDao messageDao;

  private ReportMessageManager(Context paramContext)
  {
    this.mContext = paramContext;
    this.messageDao = new ReportMessageDao(this.mContext);
  }

  private String checkEmpty(String paramString)
  {
    String str = paramString;
    if (TextUtils.isEmpty(str))
      str = "";
    return str;
  }

  public static ReportMessageManager getInstance(Context paramContext)
  {
    if (instance == null)
      instance = new ReportMessageManager(paramContext);
    return instance;
  }

  public boolean deleteReportMessageEntity(ReportMessageEntity paramReportMessageEntity)
  {
    try
    {
      boolean bool = this.messageDao.deleteReportMessage(paramReportMessageEntity);
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public ReportMessageEntity peek()
  {
    try
    {
      ReportMessageEntity localReportMessageEntity = this.messageDao.queryFirst();
      return localReportMessageEntity;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void report(ReportMessageEntity paramReportMessageEntity)
  {
    try
    {
      if (this.messageDao.getMessageCount() >= 1000)
      {
        Logger.w(TAG, "message tables is full. remove some data. tableSize: 1000, removeCount: 1");
        List localList = this.messageDao.queryFirst(1);
        for (int i = 0; i < localList.size(); i++)
        {
          this.messageDao.deleteReportMessage((ReportMessageEntity)localList.get(i));
          String str2 = checkEmpty(((ReportMessageEntity)localList.get(i)).desc);
          Logger.w(TAG, "remove message(" + ((ReportMessageEntity)localList.get(i)).msgId + ":" + str2 + ") from db.");
        }
      }
      this.messageDao.insertReportMessage(paramReportMessageEntity);
      String str1 = checkEmpty(paramReportMessageEntity.desc);
      Logger.d(TAG, "save message(" + paramReportMessageEntity.msgId + ":" + str1 + ") to db.");
      return;
    }
    finally
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.ReportMessageManager
 * JD-Core Version:    0.6.2
 */