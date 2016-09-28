package com.sohu.httpserver;

import android.content.Context;
import com.sohu.upload.b.c;

public class HttpServer
{
  private static String a = "";
  private static Context b = null;

  public void execute(Context paramContext, int paramInt)
  {
    b = paramContext;
    com.sohu.upload.a.a.b("�����߳��Ƿ����:" + c.e("thread_requestlistener"));
    if (c.e("thread_requestlistener"))
      return;
    a locala = new a(paramInt);
    locala.setName("thread_requestlistener");
    locala.setDaemon(false);
    locala.start();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.httpserver.HttpServer
 * JD-Core Version:    0.6.2
 */