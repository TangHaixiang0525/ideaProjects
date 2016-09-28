package com.sohu.app.ads.sdk.model.emu;

public enum DownloadEmue
{
  static
  {
    DOWNLOADING = new DownloadEmue("DOWNLOADING", 1);
    FAILED = new DownloadEmue("FAILED", 2);
    SUCESS = new DownloadEmue("SUCESS", 3);
    DownloadEmue[] arrayOfDownloadEmue = new DownloadEmue[4];
    arrayOfDownloadEmue[0] = UNSTART;
    arrayOfDownloadEmue[1] = DOWNLOADING;
    arrayOfDownloadEmue[2] = FAILED;
    arrayOfDownloadEmue[3] = SUCESS;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.model.emu.DownloadEmue
 * JD-Core Version:    0.6.2
 */