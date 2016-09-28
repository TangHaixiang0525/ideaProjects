package com.starcor.hunan.db.dao;

import com.starcor.core.utils.IOTools;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.domain.Reservation;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationDao
  implements IReservationDao
{
  private static final String DATA_FILE_NAME = "reservation.dat";
  private static final String TAG = ReservationDao.class.getSimpleName();
  private long autoincrement = 1L;
  private boolean isInit = false;
  private String path;
  private List<Reservation> reservations = null;

  private void write2File()
  {
    File localFile = new File(this.path);
    IOTools.writeObject(localFile, this.reservations);
    Logger.i(TAG, "写预约数据文件到磁盘 file.exits:" + localFile.exists());
  }

  public void addReservation(Reservation paramReservation)
  {
    if (!this.isInit)
      Logger.i(TAG, "addReservation Data not init");
    while (paramReservation == null)
      return;
    for (int i = 0; i < this.reservations.size(); i++)
      if (((Reservation)this.reservations.get(i)).equals(paramReservation))
      {
        ((Reservation)this.reservations.get(i)).setNotice(false);
        return;
      }
    long l1 = 1L + this.autoincrement;
    this.autoincrement = l1;
    paramReservation.set_id(l1);
    if (this.reservations.size() == 30)
    {
      long l2 = this.autoincrement;
      int k = -1;
      for (int m = 0; m < this.reservations.size(); m++)
        if (((Reservation)this.reservations.get(m)).get_id() < l2)
        {
          l2 = ((Reservation)this.reservations.get(m)).get_id();
          k = m;
        }
      if (k != -1)
        this.reservations.remove(k);
    }
    for (int j = 0; j < this.reservations.size(); j++)
      if (paramReservation.compareTo((Reservation)this.reservations.get(j)) < 0)
      {
        this.reservations.add(j, paramReservation);
        write2File();
        return;
      }
    this.reservations.add(paramReservation);
    write2File();
  }

  public void clearnReservation()
  {
    this.reservations.clear();
    write2File();
  }

  public List<Reservation> findAllExpiredReservation(long paramLong)
  {
    Object localObject;
    if (!this.isInit)
    {
      Logger.i(TAG, "findAllExpiredReservation Data not init");
      localObject = null;
    }
    while (true)
    {
      return localObject;
      localObject = new ArrayList();
      for (int i = 0; i < this.reservations.size(); i++)
        if ((((Reservation)this.reservations.get(i)).getReservation_time() <= paramLong) && (!((Reservation)this.reservations.get(i)).isNotice()))
          ((List)localObject).add(this.reservations.get(i));
    }
  }

  public List<Reservation> findAllNotExpiredReservation(long paramLong)
  {
    Object localObject;
    if (!this.isInit)
    {
      Logger.i(TAG, "findAllNotExpiredReservation Data not init");
      localObject = null;
    }
    while (true)
    {
      return localObject;
      localObject = new ArrayList();
      for (int i = -1 + this.reservations.size(); (i >= 0) && (((Reservation)this.reservations.get(i)).getReservation_time() > paramLong); i--)
      {
        ((List)localObject).add(this.reservations.get(i));
        Logger.i(TAG, "findReservation film_type:" + ((Reservation)this.reservations.get(i)).getFilm_type());
      }
    }
  }

  public List<Reservation> findAllReservation()
  {
    return this.reservations;
  }

  public Reservation findFirstReservation(long paramLong)
  {
    if (!this.isInit)
    {
      Logger.i(TAG, "findFirstReservation Data not init");
      return null;
    }
    for (int i = 0; i < this.reservations.size(); i++)
      if (((Reservation)this.reservations.get(i)).getReservation_time() > paramLong)
      {
        Logger.i(TAG, "findFirstReservation time:" + new Date(((Reservation)this.reservations.get(i)).getReservation_time()).toLocaleString() + "film_type:" + ((Reservation)this.reservations.get(i)).getFilm_type());
        return (Reservation)this.reservations.get(i);
      }
    return null;
  }

  public Reservation findReservation(long paramLong, String paramString)
  {
    if (!this.isInit)
    {
      Logger.i(TAG, "findReservation Data not init");
      return null;
    }
    for (int i = 0; i < this.reservations.size(); i++)
      if ((((Reservation)this.reservations.get(i)).getReservation_time() == paramLong) && (((Reservation)this.reservations.get(i)).getVideoId().equals(paramString)) && (!((Reservation)this.reservations.get(i)).isNotice()))
      {
        Logger.i(TAG, "findReservation film_type:" + ((Reservation)this.reservations.get(i)).getFilm_type());
        return (Reservation)this.reservations.get(i);
      }
    return null;
  }

  public boolean haveExpiredReservation(long paramLong)
  {
    if (!this.isInit)
    {
      Logger.i(TAG, "haveExpiredReservation Data not init");
      return false;
    }
    for (int i = 0; i < this.reservations.size(); i++)
      if ((((Reservation)this.reservations.get(i)).getReservation_time() <= paramLong) && (!((Reservation)this.reservations.get(i)).isNotice()))
        return true;
    return false;
  }

  public void initData(long paramLong, String paramString)
  {
    if (paramString == null)
      return;
    this.path = (paramString + "reservation.dat");
    this.reservations = null;
    Logger.i(TAG, "file path:" + this.path);
    File localFile = new File(this.path);
    if (localFile.exists());
    while (true)
    {
      try
      {
        this.reservations = ((List)IOTools.readObject(localFile));
        Logger.i(TAG, "read object file ok! ");
        if (this.reservations == null)
          this.reservations = new ArrayList();
        int i = -1 + this.reservations.size();
        if (i < 0)
          break;
        if (((Reservation)this.reservations.get(i)).get_id() > this.autoincrement)
          this.autoincrement = ((Reservation)this.reservations.get(i)).get_id();
        if (((Reservation)this.reservations.get(i)).getExpiredTime() < paramLong)
          this.reservations.remove(i);
        i--;
        continue;
      }
      catch (Exception localException)
      {
        Logger.i(TAG, "init fails data file error");
        continue;
      }
      Logger.i(TAG, "reservation data file is not exists");
    }
    Logger.i(TAG, "init finish,data count:" + this.reservations.size());
    this.isInit = true;
  }

  public void removeReservation(long paramLong)
  {
    if (!this.isInit)
      Logger.i(TAG, "removeReservation Data not init");
    while (true)
    {
      return;
      for (int i = -1 + this.reservations.size(); i >= 0; i--)
        if (((Reservation)this.reservations.get(i)).get_id() == paramLong)
        {
          this.reservations.remove(i);
          write2File();
          return;
        }
    }
  }

  public void setReservataionExpired(long paramLong, boolean paramBoolean)
  {
    if (!this.isInit)
      Logger.i(TAG, "setReservataionExpired Data not init");
    while (true)
    {
      return;
      for (int i = 0; i < this.reservations.size(); i++)
        if (((Reservation)this.reservations.get(i)).get_id() == paramLong)
        {
          ((Reservation)this.reservations.get(i)).setNotice(paramBoolean);
          write2File();
          return;
        }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.db.dao.ReservationDao
 * JD-Core Version:    0.6.2
 */