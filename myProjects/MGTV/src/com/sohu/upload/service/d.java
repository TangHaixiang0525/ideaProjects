package com.sohu.upload.service;

import com.sohu.upload.UploadPostData;
import java.util.Date;

class d extends Thread
{
  d(b paramb)
  {
  }

  public void run()
  {
    String str = "";
    switch (g.a[CountService.e(this.a.a).ordinal()])
    {
    default:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      com.sohu.upload.a.a.a("��ǰ�ļ�¼ģʽ��:" + str);
      com.sohu.upload.a.a.a("��ǰ��interval:" + com.sohu.upload.consts.a.c / 60000L);
      long l1 = com.sohu.upload.b.b.e(str, 0L);
      if (System.currentTimeMillis() - l1 > com.sohu.upload.consts.a.c)
      {
        com.sohu.upload.a.a.a("��ǰ��¼ģʽ��:" + CountService.e(this.a.a));
        com.sohu.upload.a.a.a("��ʼ��¼..." + new Date(System.currentTimeMillis()));
        boolean bool1 = CountService.f(this.a.a);
        boolean bool2 = CountService.g(this.a.a);
        if ((bool1) && (bool2))
        {
          com.sohu.upload.a.a.a("��¼�ɹ�..");
          com.sohu.upload.b.b.d(str, System.currentTimeMillis());
          com.sohu.upload.b.b.a(str + System.currentTimeMillis(), System.currentTimeMillis());
        }
      }
      long l2 = com.sohu.upload.b.b.e("upload_time", 0L);
      com.sohu.upload.a.a.a("�ϴ��ϱ���ʱ����:" + l2);
      com.sohu.upload.a.a.a("�ϱ��ļ��ʱ��:" + com.sohu.upload.consts.a.d);
      if (System.currentTimeMillis() - l2 > com.sohu.upload.consts.a.d)
      {
        com.sohu.upload.a.a.a("��ʼ�ϱ�..." + new Date(System.currentTimeMillis()));
        UploadPostData.reportAppListInfo(this.a.a.getApplicationContext());
      }
      return;
      str = "record_time_five_minute";
      com.sohu.upload.consts.a.c = 240000L;
      continue;
      str = "record_time_one_hour";
      com.sohu.upload.consts.a.c = 3600000L;
      continue;
      str = "record_time_other";
      com.sohu.upload.consts.a.c = 86400000L;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.upload.service.d
 * JD-Core Version:    0.6.2
 */