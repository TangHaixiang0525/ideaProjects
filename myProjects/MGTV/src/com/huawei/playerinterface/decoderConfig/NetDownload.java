package com.huawei.playerinterface.decoderConfig;

import com.huawei.dmpbase.DmpLog;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetDownload
{
  private static final String TAG = "HAPlayer_NetDownload";
  private URL url = null;

  private InputStream getFromUrl(String paramString)
    throws IOException
  {
    this.url = new URL(paramString);
    return ((HttpURLConnection)this.url.openConnection()).getInputStream();
  }

  public int download(String paramString1, String paramString2, String paramString3)
    throws IOException
  {
    FileUtil localFileUtil = new FileUtil();
    try
    {
      InputStream localInputStream2 = getFromUrl(paramString1);
      localInputStream1 = localInputStream2;
      if (localInputStream1 == null)
      {
        DmpLog.e("HAPlayer_NetDownload", "connect " + paramString1 + "fail");
        return -1;
      }
    }
    catch (IOException localIOException)
    {
      InputStream localInputStream1;
      do
        while (true)
        {
          localIOException.printStackTrace();
          localInputStream1 = null;
        }
      while (localFileUtil.writeFile(paramString2, paramString3, localInputStream1) == null);
    }
    return 0;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.decoderConfig.NetDownload
 * JD-Core Version:    0.6.2
 */