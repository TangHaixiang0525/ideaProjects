package com.miaozhen.mzmonitor;

import android.content.ContentValues;

class a
{
  private String a;
  private String b;
  private String c;
  private String d;
  private String e;
  private String f;
  private long g;
  private int h;

  public a()
  {
  }

  public a(String paramString)
  {
    this.b = paramString;
    this.g = System.currentTimeMillis();
    this.h = 0;
    this.a = MZUtility.MD5(paramString + this.g + MZUtility.getRandomNum());
  }

  public String a()
  {
    return this.b;
  }

  public void a(int paramInt)
  {
    this.h = paramInt;
  }

  public void a(long paramLong)
  {
    this.g = paramLong;
  }

  public void a(String paramString)
  {
    this.b = paramString;
  }

  public String b()
  {
    return this.c;
  }

  public void b(String paramString)
  {
    this.c = paramString;
  }

  public String c()
  {
    return this.d;
  }

  public void c(String paramString)
  {
    this.d = paramString;
  }

  public String d()
  {
    return this.e;
  }

  public void d(String paramString)
  {
    this.e = paramString;
  }

  public String e()
  {
    return this.a;
  }

  public void e(String paramString)
  {
    this.a = paramString;
  }

  public long f()
  {
    return this.g;
  }

  public void f(String paramString)
  {
    this.f = paramString;
  }

  public long g()
  {
    return this.g / 1000L;
  }

  public int h()
  {
    return this.h;
  }

  public String i()
  {
    return this.f;
  }

  public ContentValues j()
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("cacheId", this.a);
    localContentValues.put("url", this.b);
    localContentValues.put("timestamp", Long.valueOf(this.g));
    localContentValues.put("times", Integer.valueOf(this.h));
    return localContentValues;
  }

  public String toString()
  {
    return "cacheId: " + this.a + ", url: " + this.b + ", eventType:" + this.e + ", userId: " + this.d + ", panelId: " + this.c + ", timestamp: " + this.g + ", times: " + this.h;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.a
 * JD-Core Version:    0.6.2
 */