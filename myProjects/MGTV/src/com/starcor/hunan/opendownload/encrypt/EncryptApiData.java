package com.starcor.hunan.opendownload.encrypt;

public class EncryptApiData
{
  public String apiName = "";
  public String decryptType = "0";
  public String decryptTypes = "";
  public String encryptType = "0";
  public String encryptTypes = "";

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    EncryptApiData localEncryptApiData;
    do
    {
      return true;
      if (!(paramObject instanceof EncryptApiData))
        return false;
      localEncryptApiData = (EncryptApiData)paramObject;
      if (this.apiName == null)
        break;
    }
    while (this.apiName.equals(localEncryptApiData.apiName));
    while (true)
    {
      return false;
      if (localEncryptApiData.apiName == null)
        break;
    }
  }

  public int hashCode()
  {
    int i;
    int k;
    label35: int n;
    label59: int i1;
    if (this.apiName != null)
    {
      i = this.apiName.hashCode();
      int j = i * 31;
      if (this.encryptTypes == null)
        break label129;
      k = this.encryptTypes.hashCode();
      int m = 31 * (j + k);
      if (this.encryptType == null)
        break label134;
      n = this.encryptType.hashCode();
      i1 = 31 * (m + n);
      if (this.decryptTypes == null)
        break label140;
    }
    label129: label134: label140: for (int i2 = this.decryptTypes.hashCode(); ; i2 = 0)
    {
      int i3 = 31 * (i1 + i2);
      String str = this.decryptType;
      int i4 = 0;
      if (str != null)
        i4 = this.decryptType.hashCode();
      return i3 + i4;
      i = 0;
      break;
      k = 0;
      break label35;
      n = 0;
      break label59;
    }
  }

  public String toString()
  {
    return "EncryptApiData{apiName='" + this.apiName + '\'' + ", encryptTypes='" + this.encryptTypes + '\'' + ", e_Type='" + this.encryptType + '\'' + ", decryptTypes='" + this.decryptTypes + '\'' + ", d_Type='" + this.decryptType + '\'' + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.opendownload.encrypt.EncryptApiData
 * JD-Core Version:    0.6.2
 */