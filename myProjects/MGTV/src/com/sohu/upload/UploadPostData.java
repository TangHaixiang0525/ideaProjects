package com.sohu.upload;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import com.sohu.upload.service.CountService;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;

public final class UploadPostData
{
  public static void deleteAppInfo(Context paramContext, String paramString)
  {
    new com.sohu.applist.b.a(paramContext).d(paramString);
  }

  public static void deleteBaseStation(Context paramContext, int paramInt)
  {
    new com.sohu.applist.b.b(paramContext).a(paramInt);
  }

  public static com.sohu.applist.a.b getAppListAndGPS(Context paramContext)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    com.sohu.upload.b.c.a(paramContext);
    System.currentTimeMillis();
    com.sohu.applist.a.c localc = new com.sohu.applist.b.a(paramContext).b();
    if ((localc != null) && (localc.b() != null))
    {
      localStringBuilder.append("&mz1=");
      localStringBuilder.append(com.sohu.applist.c.a.b.a(localc.b()));
      localStringBuilder.append("&n=");
      localStringBuilder.append(localc.a());
    }
    com.sohu.applist.a.a locala = new com.sohu.applist.b.b(paramContext).b();
    if ((locala != null) && (locala.a() != 0))
    {
      localStringBuilder.append("&mz2=");
      localStringBuilder.append(com.sohu.upload.b.c.c(com.sohu.upload.b.c.a(locala).toString()));
    }
    return new com.sohu.applist.a.b(localStringBuilder.toString().replaceAll("\\n|//n|//r", ""), localc.b(), locala.a());
  }

  public static void init(Context paramContext, String paramString)
  {
    com.sohu.upload.a.b.a(paramContext);
    com.sohu.upload.b.c.a(paramContext);
    com.sohu.upload.b.c.b = paramString;
    com.sohu.upload.b.b.a(paramContext);
    if (new File(Environment.getExternalStorageDirectory(), "debug10.txt").exists());
    for (com.sohu.upload.consts.a.a = true; ; com.sohu.upload.consts.a.a = false)
    {
      new a().start();
      paramContext.startService(new Intent(paramContext, CountService.class));
      return;
    }
  }

  public static void reportAppListInfo(Context paramContext)
  {
    String str2;
    String str5;
    try
    {
      String str1 = com.sohu.upload.b.b.a("postback", "0");
      if (com.sohu.upload.consts.a.a)
        str1 = "1";
      com.sohu.upload.a.a.a("postback�Ŀ���ֵ��:" + str1);
      if ("0".equals(str1))
        return;
      str2 = com.sohu.upload.b.b.a("applist", "-11");
      if (com.sohu.upload.consts.a.a)
        str2 = "-1";
      if ("-1".equals(str2))
        break label221;
      str5 = com.sohu.upload.b.c.d();
      com.sohu.upload.a.a.a("applist��ֵ��:" + str2 + ",�û���imei:" + str5);
      if ("".equals(str5))
      {
        com.sohu.upload.a.a.a("imei��ֵΪ��");
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      com.sohu.upload.a.a.b("�쳣:" + localThrowable.getMessage() + "�ϱ�ʧ��");
      return;
    }
    try
    {
      int i = str2.length();
      if (str2.equals(str5.substring(str5.length() - i)))
        break label226;
      com.sohu.upload.a.a.a("applist��imei��ֵ����ͬ,�������ϱ�");
      return;
    }
    catch (Exception localException)
    {
      com.sohu.upload.a.a.b("�ж�applist��imei��ֵ�����쳣:" + localException.getMessage());
      return;
    }
    label221: com.sohu.upload.a.a.a("applistֵΪ-1,����Ҫ�ȶ�imei��ֵ�����ϱ�");
    label226: com.sohu.upload.b.c.a(paramContext);
    ArrayList localArrayList1 = new com.sohu.applist.b.a(paramContext).a();
    ArrayList localArrayList2 = new com.sohu.applist.b.b(paramContext).a();
    if ((localArrayList2 == null) || (localArrayList2.size() <= 0))
    {
      com.sohu.upload.a.a.b("��վ��ϢΪ�գ��������ϱ�");
      return;
    }
    if ((localArrayList1 == null) || (localArrayList1.size() <= 0))
    {
      com.sohu.upload.a.a.b("Ӧ���б���ϢΪ�գ��������ϱ�");
      return;
    }
    JSONObject localJSONObject = com.sohu.upload.b.c.a(localArrayList1, localArrayList2);
    com.sohu.upload.a.a.a("����֮������:" + localJSONObject.toString());
    long l = System.currentTimeMillis();
    String str3 = com.sohu.upload.b.c.a(localJSONObject.toString(), l);
    HashMap localHashMap = new HashMap();
    localHashMap.put("encrypt_content", str3);
    String str4 = com.sohu.upload.consts.a.b + "?currenttime=" + l;
    if (com.sohu.upload.b.c.l())
    {
      if (com.sohu.upload.b.a.a(str4, localHashMap))
      {
        com.sohu.upload.a.a.a("�ϱ��ɹ�");
        com.sohu.upload.b.b.d("upload_time", System.currentTimeMillis());
        com.sohu.upload.b.b.b(System.currentTimeMillis() + "", System.currentTimeMillis());
        return;
      }
      com.sohu.upload.b.b.b("Error:" + System.currentTimeMillis(), System.currentTimeMillis());
      com.sohu.upload.a.a.b("�ϱ�ʧ��");
      return;
    }
    com.sohu.upload.a.a.a("Wifi����ʧ��...�������ϱ�...");
  }

  public void closeLog(boolean paramBoolean)
  {
    com.sohu.upload.consts.a.a = paramBoolean;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.upload.UploadPostData
 * JD-Core Version:    0.6.2
 */