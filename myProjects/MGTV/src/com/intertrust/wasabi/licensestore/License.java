package com.intertrust.wasabi.licensestore;

public class License
{
  private String data;
  private int expirationData;
  private int id;
  private int insertionDate;
  private int priority;
  private String tag;

  public License(int paramInt1, String paramString1, int paramInt2, int paramInt3, int paramInt4, String paramString2)
  {
    this.id = paramInt1;
    this.data = paramString1;
    this.expirationData = paramInt2;
    this.priority = paramInt3;
    this.insertionDate = paramInt4;
    this.tag = paramString2;
  }

  private static String snippet(String paramString)
  {
    if (paramString.length() > 100)
      paramString = paramString.substring(0, 20) + "..." + paramString.substring(-20 + paramString.length());
    return paramString;
  }

  public String getData()
  {
    return this.data;
  }

  public int getExpirationData()
  {
    return this.expirationData;
  }

  public int getId()
  {
    return this.id;
  }

  public int getInsertionDate()
  {
    return this.insertionDate;
  }

  public int getPriority()
  {
    return this.priority;
  }

  public String getTag()
  {
    return this.tag;
  }

  public String toString()
  {
    String str1 = "License=\n" + "\tid=" + this.id + "\n";
    String str2 = str1 + "\tdata=" + snippet(this.data);
    String str3 = str2 + "\texpirationData=" + this.expirationData + "\n";
    String str4 = str3 + "\tpriority=" + this.priority + "\n";
    String str5 = str4 + "\tinsertionDate=" + this.insertionDate + "\n";
    return str5 + "\ttag=" + this.tag + "\n";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.licensestore.License
 * JD-Core Version:    0.6.2
 */