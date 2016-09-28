package com.sohu.app.ads.sdk.model;

import java.util.ArrayList;

public class a
{
  private ArrayList<String> a = new ArrayList();
  private String b = "";
  private String c = "";
  private ArrayList<d> d = new ArrayList();
  private ArrayList<d> e = new ArrayList();

  public ArrayList<String> a()
  {
    return this.a;
  }

  public void a(String paramString)
  {
    this.b = paramString;
  }

  public ArrayList<d> b()
  {
    return this.d;
  }

  public void b(String paramString)
  {
    this.c = paramString;
  }

  public ArrayList<d> c()
  {
    return this.e;
  }

  public String d()
  {
    return this.b;
  }

  public String e()
  {
    return this.c;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("& Impression=");
    localStringBuilder.append(this.a);
    localStringBuilder.append("& ClickThrough=");
    localStringBuilder.append(this.b);
    localStringBuilder.append("& StaticResource=");
    localStringBuilder.append(this.c);
    return localStringBuilder.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.model.a
 * JD-Core Version:    0.6.2
 */