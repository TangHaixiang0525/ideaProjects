package com.sohu.app.ads.sdk.res;

public enum AdType
{
  static
  {
    OAD = new AdType("OAD", 1);
    PAD = new AdType("PAD", 2);
    AdType[] arrayOfAdType = new AdType[3];
    arrayOfAdType[0] = STARTIMG;
    arrayOfAdType[1] = OAD;
    arrayOfAdType[2] = PAD;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.res.AdType
 * JD-Core Version:    0.6.2
 */