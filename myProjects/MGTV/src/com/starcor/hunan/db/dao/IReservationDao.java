package com.starcor.hunan.db.dao;

import com.starcor.hunan.domain.Reservation;
import java.util.List;

public abstract interface IReservationDao
{
  public abstract void addReservation(Reservation paramReservation);

  public abstract void clearnReservation();

  public abstract List<Reservation> findAllExpiredReservation(long paramLong);

  public abstract List<Reservation> findAllNotExpiredReservation(long paramLong);

  public abstract List<Reservation> findAllReservation();

  public abstract Reservation findFirstReservation(long paramLong);

  public abstract Reservation findReservation(long paramLong, String paramString);

  public abstract boolean haveExpiredReservation(long paramLong);

  public abstract void removeReservation(long paramLong);

  public abstract void setReservataionExpired(long paramLong, boolean paramBoolean);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.db.dao.IReservationDao
 * JD-Core Version:    0.6.2
 */