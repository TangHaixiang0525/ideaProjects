package com.starcor.hunan.db.dao;

import com.starcor.hunan.domain.ReportMessageEntity;
import java.util.List;

public abstract interface IReportMessageDao
{
  public abstract boolean deleteReportMessage(ReportMessageEntity paramReportMessageEntity);

  public abstract int getMessageCount();

  public abstract boolean insertReportMessage(ReportMessageEntity paramReportMessageEntity);

  public abstract boolean insertReportMessages(List<ReportMessageEntity> paramList);

  public abstract ReportMessageEntity queryFirst();

  public abstract List<ReportMessageEntity> queryFirst(int paramInt);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.db.dao.IReportMessageDao
 * JD-Core Version:    0.6.2
 */