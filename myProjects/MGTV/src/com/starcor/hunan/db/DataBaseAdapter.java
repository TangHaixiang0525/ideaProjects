package com.starcor.hunan.db;

import com.starcor.hunan.newUserCenter.MovieCouponsColums;

public class DataBaseAdapter
{
  public static final String[] mContentType = { "vnd.android.cursor.dir/vnd.com.starcor.hunan.reservation", "vnd.android.cursor.dir/vnd.com.starcor.hunan.config", "vnd.android.cursor.dir/vnd.com.starcor.hunan.public_topic_message", "vnd.android.cursor.dir/vnd.com.starcor.hunan.private_topic_message", "vnd.android.cursor.dir/vnd.com.starcor.hunan.admin_topic_message", "vnd.android.cursor.dir/vnd.com.starcor.hunan.server_message", "vnd.android.cursor.dir/vnd.com.starcor.hunan.msg_sys_v3", "vnd.android.cursor.dir/vnd.com.starcor.hunan.user_movie_coupons", "vnd.android.cursor.dir/vnd.com.starcor.hunan.report_message" };
  public static final DataBaseColumns[] mDataBaseColumns;

  static
  {
    DataBaseColumns[] arrayOfDataBaseColumns = new DataBaseColumns[9];
    arrayOfDataBaseColumns[0] = new ReservationColums();
    arrayOfDataBaseColumns[1] = new ConfigColums();
    arrayOfDataBaseColumns[2] = new PublicTopicMessageColumns();
    arrayOfDataBaseColumns[3] = new PrivateTopicMessageColumns();
    arrayOfDataBaseColumns[4] = new AdminTopicMessageColumns();
    arrayOfDataBaseColumns[5] = new ServerMessageColumns();
    arrayOfDataBaseColumns[6] = new MessageSystemV3Columns();
    arrayOfDataBaseColumns[7] = new MovieCouponsColums();
    arrayOfDataBaseColumns[8] = new ReportMessageColumns();
    mDataBaseColumns = arrayOfDataBaseColumns;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.db.DataBaseAdapter
 * JD-Core Version:    0.6.2
 */