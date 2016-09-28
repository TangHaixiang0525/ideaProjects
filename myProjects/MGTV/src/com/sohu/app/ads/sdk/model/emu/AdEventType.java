package com.sohu.app.ads.sdk.model.emu;

public enum AdEventType
{
  static
  {
    PAUSED = new AdEventType("PAUSED", 2);
    RESUMED = new AdEventType("RESUMED", 3);
    END = new AdEventType("END", 4);
    ALL_ADS_COMPLETED = new AdEventType("ALL_ADS_COMPLETED", 5);
    PLAYTIMEOUT = new AdEventType("PLAYTIMEOUT", 6);
    ERROR = new AdEventType("ERROR", 7);
    CLICKED = new AdEventType("CLICKED", 8);
    BUFFERCOMPLETE = new AdEventType("BUFFERCOMPLETE", 9);
    AdEventType[] arrayOfAdEventType = new AdEventType[10];
    arrayOfAdEventType[0] = LOADED;
    arrayOfAdEventType[1] = STARTED;
    arrayOfAdEventType[2] = PAUSED;
    arrayOfAdEventType[3] = RESUMED;
    arrayOfAdEventType[4] = END;
    arrayOfAdEventType[5] = ALL_ADS_COMPLETED;
    arrayOfAdEventType[6] = PLAYTIMEOUT;
    arrayOfAdEventType[7] = ERROR;
    arrayOfAdEventType[8] = CLICKED;
    arrayOfAdEventType[9] = BUFFERCOMPLETE;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.model.emu.AdEventType
 * JD-Core Version:    0.6.2
 */